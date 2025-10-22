package com.example.invoice_lookup.service;

import com.example.invoice_lookup.model.InvoiceUpload;
import com.example.invoice_lookup.model.ProjectInfo;
import com.example.invoice_lookup.model.ThirdPartyApiRequest;
import com.example.invoice_lookup.repository.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;


import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;


@Service
public class InvoiceUploadService {
    @PersistenceContext
    private EntityManager entityManager;
    private final InvoiceUploadRepository repository;
    private final TaxDetailsRepository taxDetailsRepository;
    private final GoodsDetailsRepository goodsDetailsRepository;
    private final MedicalFoliosRepository medicalFoliosRepository;
    private static final Logger logger = LoggerFactory.getLogger(InvoiceUploadService.class);


    // ✅ Constructor supports old + new repositories
    public InvoiceUploadService(
            InvoiceUploadRepository repository,
            TaxDetailsRepository taxDetailsRepository,
            GoodsDetailsRepository goodsDetailsRepository,
            MedicalFoliosRepository medicalFoliosRepository
    ) {
        this.repository = repository;
        this.taxDetailsRepository = taxDetailsRepository;
        this.goodsDetailsRepository = goodsDetailsRepository;
        this.medicalFoliosRepository = medicalFoliosRepository;
    }

    // ✅ Original method untouched
    public Optional<InvoiceUpload> getByDebitNo(Integer debitNo) {
        return repository.findByDebitNo(debitNo);
    }

    // ✅ Original method untouched
    public void resetTransaction(Integer debitNo) {
        Optional<InvoiceUpload> recordOpt = repository.findByDebitNo(debitNo);
        recordOpt.ifPresent(record -> {
            record.setProcessStatus(0);
            record.setUpdateComments(null);
            repository.save(record);
        });
    }

    // ✅ New method added safely


    @Transactional
    public void clearFolios(List<Integer> folioNumbers) {
        logger.info("Starting clearFolios for folioNumbers: {}", folioNumbers);

        List<Integer> debitNos = folioNumbers.stream()
                .map(folio -> Integer.parseInt("100" + folio))
                .collect(Collectors.toList());

        logger.info("Mapped folioNumbers {} to debitNos {}", folioNumbers, debitNos);

        repository.deleteByDebitNos(debitNos);
        logger.info("Deleted from InvoiceUpload where debitNo in {}", debitNos);



        int taxCount = taxDetailsRepository.deleteByDebitNos(debitNos);
        logger.info("Deleted {} rows from TaxDetails", taxCount);

        int goodsCount = goodsDetailsRepository.deleteByDebitNos(debitNos);
        logger.info("Deleted {} rows from GoodsDetails", goodsCount);

        int medicalCount = medicalFoliosRepository.deleteByFolioNos(folioNumbers);
        logger.info("Deleted {} rows from MedicalFolios", medicalCount);
        logger.info("clearFolios completed successfully");
    }


    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    public Map<String, Object> processVatProject(Integer debitNo) {
        Map<String, Object> response = new HashMap<>();

        try {
            // 1. Get buyerTin and goodsCategoryId from database
            Optional<String> buyerTinOpt = repository.findBuyerTinByDebitNo(debitNo);
            Optional<String> goodsCategoryIdOpt = goodsDetailsRepository.findGoodsCategoryIdByDebitNo(debitNo);

            if (!buyerTinOpt.isPresent() || !goodsCategoryIdOpt.isPresent()) {
                response.put("status", "error");
                response.put("message", "Required data not found for debitNo: " + debitNo);
                return response;
            }

            String buyerTin = buyerTinOpt.get();
            String goodsCategoryId = goodsCategoryIdOpt.get();

            // 2. Create the content JSON and encode to base64
            Map<String, String> contentData = new HashMap<>();
            contentData.put("tin", buyerTin);
            contentData.put("commodityCategoryCode", goodsCategoryId);

            String contentJson = objectMapper.writeValueAsString(contentData);
            String base64Content = Base64.getEncoder().encodeToString(contentJson.getBytes());

            // 3. Create the third-party API request
            ThirdPartyApiRequest apiRequest = createThirdPartyRequest(base64Content);

            // 4. Call the third-party API
            String apiUrl = "http://10.9.0.60:9880/efristcs/ws/tcsapp/getInformation";
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<ThirdPartyApiRequest> entity = new HttpEntity<>(apiRequest, headers);
            ResponseEntity<ThirdPartyApiRequest> apiResponse = restTemplate.exchange(
                    apiUrl, HttpMethod.POST, entity, ThirdPartyApiRequest.class);

            if (apiResponse.getBody() == null || apiResponse.getBody().getData() == null) {
                response.put("status", "error");
                response.put("message", "Invalid response from third-party API");
                return response;
            }

            // 5. Decode the response content
            String responseContent = apiResponse.getBody().getData().getContent();
            String decodedContent = new String(Base64.getDecoder().decode(responseContent));
            ProjectInfo projectInfo = objectMapper.readValue(decodedContent, ProjectInfo.class);

            // 6. Find matching project
            String projectId = null;
            String projectName = null;

            if (projectInfo.getDeemedAndExemptProjectList() != null) {
                for (ProjectInfo.DeemedAndExemptProject project : projectInfo.getDeemedAndExemptProjectList()) {
                    if (goodsCategoryId.equals(project.getCommodityCategoryCode())) {
                        projectId = project.getProjectId();
                        projectName = project.getProjectName();
                        break;
                    }
                }
            }

            if (projectId == null || projectName == null) {
                response.put("status", "error");
                response.put("message", "No matching project found for commodity category: " + goodsCategoryId);
                return response;
            }

            // 7. Update GoodsDetails table
            goodsDetailsRepository.updateVatProjectInfo(debitNo, projectId, projectName);

            // 8. Reset transaction
            resetTransaction(debitNo);

            // 9. Get updated record to return invoice info
            Optional<InvoiceUpload> updatedRecordOpt = repository.findByDebitNo(debitNo);
            if (updatedRecordOpt.isPresent()) {
                InvoiceUpload updatedRecord = updatedRecordOpt.get();
                response.put("status", "success");
                response.put("message", "VAT project information updated successfully");
                response.put("projectId", projectId);
                response.put("projectName", projectName);
                response.put("invoiceNo", updatedRecord.getInvoiceNo());
                response.put("updateComments", updatedRecord.getUpdateComments());
            } else {
                response.put("status", "success");
                response.put("message", "VAT project information updated successfully");
                response.put("projectId", projectId);
                response.put("projectName", projectName);
            }

        } catch (Exception e) {
            logger.error("Error processing VAT project for debitNo: " + debitNo, e);
            response.put("status", "error");
            response.put("message", "Failed to process VAT project: " + e.getMessage());
        }

        return response;
    }

    private ThirdPartyApiRequest createThirdPartyRequest(String base64Content) {
        ThirdPartyApiRequest request = new ThirdPartyApiRequest();

        // Create data section
        ThirdPartyApiRequest.Data data = new ThirdPartyApiRequest.Data();
        data.setContent(base64Content);
        data.setSignature("LU6ZyT2UZnpoFoFijAH19WX/l+XpMdebGY84HpWoOEiO3oXOR+Gg70WVrlYzkx7jfc66gW1BAMskzPSoGp1rPxt+hgU8gsLm7uWR5KJwVQztvPhCpLqCCQNyaWB1QLkAkSlhpw12s/rlFJQjIJpnGQGvZ7zC91SeVeGo6q+H5WCCWRZAMqWDczHqNrmiQtTsIzFBzHH+mAqHw6ho4547k3g9MNmWPYa+iD2yHFsZl3ChdVfTjlvwevxYD6i4l2bL7PPQsO44P1/l1RNby7EAQZMcl/+j3q9bjr/uIIT3cte3hEF5SOZP6ESEoubjCtO/MVGMgZC4/0a52iCJwhoTUw==");

        ThirdPartyApiRequest.DataDescription dataDescription = new ThirdPartyApiRequest.DataDescription();
        dataDescription.setCodeType("0");
        dataDescription.setEncryptCode("1");
        dataDescription.setZipCode("0");
        data.setDataDescription(dataDescription);
        request.setData(data);

        // Create global info section
        ThirdPartyApiRequest.GlobalInfo globalInfo = new ThirdPartyApiRequest.GlobalInfo();
        globalInfo.setAppId("AP01");
        globalInfo.setVersion("1.1.20191201");
        globalInfo.setDataExchangeId("9230489223014123");
        globalInfo.setInterfaceCode("T137");
        globalInfo.setRequestCode("TP");
        globalInfo.setRequestTime(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        globalInfo.setResponseCode("TA");
        globalInfo.setUserName("admin");
        globalInfo.setDeviceMAC("FFFFFFFFFFFF");
        globalInfo.setDeviceNo("TCSd60bf02428368770");
        globalInfo.setTin("1006732250");
        globalInfo.setBrn("");
        globalInfo.setTaxpayerID("1");
        globalInfo.setLongitude("116.397128");
        globalInfo.setLatitude("39.916527");

        ThirdPartyApiRequest.ExtendField extendField = new ThirdPartyApiRequest.ExtendField();
        extendField.setResponseDateFormat("dd/MM/yyyy");
        extendField.setResponseTimeFormat("dd/MM/yyyy HH:mm:ss");
        globalInfo.setExtendField(extendField);
        request.setGlobalInfo(globalInfo);

        // Create return state info section
        ThirdPartyApiRequest.ReturnStateInfo returnStateInfo = new ThirdPartyApiRequest.ReturnStateInfo();
        returnStateInfo.setReturnCode("");
        returnStateInfo.setReturnMessage("");
        request.setReturnStateInfo(returnStateInfo);

        return request;
    }

}
