package com.example.invoice_lookup.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ThirdPartyApiRequest {
    private Data data;
    private GlobalInfo globalInfo;
    private ReturnStateInfo returnStateInfo;

    // Getters and setters
    public Data getData() { return data; }
    public void setData(Data data) { this.data = data; }
    public GlobalInfo getGlobalInfo() { return globalInfo; }
    public void setGlobalInfo(GlobalInfo globalInfo) { this.globalInfo = globalInfo; }
    public ReturnStateInfo getReturnStateInfo() { return returnStateInfo; }
    public void setReturnStateInfo(ReturnStateInfo returnStateInfo) { this.returnStateInfo = returnStateInfo; }

    public static class Data {
        private String content;
        private String signature;
        private DataDescription dataDescription;

        // Getters and setters
        public String getContent() { return content; }
        public void setContent(String content) { this.content = content; }
        public String getSignature() { return signature; }
        public void setSignature(String signature) { this.signature = signature; }
        public DataDescription getDataDescription() { return dataDescription; }
        public void setDataDescription(DataDescription dataDescription) { this.dataDescription = dataDescription; }
    }

    public static class DataDescription {
        private String codeType;
        private String encryptCode;
        private String zipCode;

        // Getters and setters
        public String getCodeType() { return codeType; }
        public void setCodeType(String codeType) { this.codeType = codeType; }
        public String getEncryptCode() { return encryptCode; }
        public void setEncryptCode(String encryptCode) { this.encryptCode = encryptCode; }
        public String getZipCode() { return zipCode; }
        public void setZipCode(String zipCode) { this.zipCode = zipCode; }
    }

    public static class GlobalInfo {
        private String appId;
        private String version;
        private String dataExchangeId;
        private String interfaceCode;
        private String requestCode;
        private String requestTime;
        private String responseCode;
        private String userName;
        private String deviceMAC;
        private String deviceNo;
        private String tin;
        private String brn;
        private String taxpayerID;
        private String longitude;
        private String latitude;
        private ExtendField extendField;

        // Getters and setters
        public String getAppId() { return appId; }
        public void setAppId(String appId) { this.appId = appId; }
        public String getVersion() { return version; }
        public void setVersion(String version) { this.version = version; }
        public String getDataExchangeId() { return dataExchangeId; }
        public void setDataExchangeId(String dataExchangeId) { this.dataExchangeId = dataExchangeId; }
        public String getInterfaceCode() { return interfaceCode; }
        public void setInterfaceCode(String interfaceCode) { this.interfaceCode = interfaceCode; }
        public String getRequestCode() { return requestCode; }
        public void setRequestCode(String requestCode) { this.requestCode = requestCode; }
        public String getRequestTime() { return requestTime; }
        public void setRequestTime(String requestTime) { this.requestTime = requestTime; }
        public String getResponseCode() { return responseCode; }
        public void setResponseCode(String responseCode) { this.responseCode = responseCode; }
        public String getUserName() { return userName; }
        public void setUserName(String userName) { this.userName = userName; }
        public String getDeviceMAC() { return deviceMAC; }
        public void setDeviceMAC(String deviceMAC) { this.deviceMAC = deviceMAC; }
        public String getDeviceNo() { return deviceNo; }
        public void setDeviceNo(String deviceNo) { this.deviceNo = deviceNo; }
        public String getTin() { return tin; }
        public void setTin(String tin) { this.tin = tin; }
        public String getBrn() { return brn; }
        public void setBrn(String brn) { this.brn = brn; }
        public String getTaxpayerID() { return taxpayerID; }
        public void setTaxpayerID(String taxpayerID) { this.taxpayerID = taxpayerID; }
        public String getLongitude() { return longitude; }
        public void setLongitude(String longitude) { this.longitude = longitude; }
        public String getLatitude() { return latitude; }
        public void setLatitude(String latitude) { this.latitude = latitude; }
        public ExtendField getExtendField() { return extendField; }
        public void setExtendField(ExtendField extendField) { this.extendField = extendField; }
    }

    public static class ExtendField {
        private String responseDateFormat;
        private String responseTimeFormat;

        // Getters and setters
        public String getResponseDateFormat() { return responseDateFormat; }
        public void setResponseDateFormat(String responseDateFormat) { this.responseDateFormat = responseDateFormat; }
        public String getResponseTimeFormat() { return responseTimeFormat; }
        public void setResponseTimeFormat(String responseTimeFormat) { this.responseTimeFormat = responseTimeFormat; }
    }

    public static class ReturnStateInfo {
        private String returnCode;
        private String returnMessage;

        // Getters and setters
        public String getReturnCode() { return returnCode; }
        public void setReturnCode(String returnCode) { this.returnCode = returnCode; }
        public String getReturnMessage() { return returnMessage; }
        public void setReturnMessage(String returnMessage) { this.returnMessage = returnMessage; }
    }
}
