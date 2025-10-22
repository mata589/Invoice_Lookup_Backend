package com.example.invoice_lookup.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public class ProjectInfo {
    private List<CommodityCategory> commodityCategory;
    private String taxpayerType;
    private List<DeemedAndExemptProject> deemedAndExemptProjectList;

    // Getters and setters
    public List<CommodityCategory> getCommodityCategory() { return commodityCategory; }
    public void setCommodityCategory(List<CommodityCategory> commodityCategory) { this.commodityCategory = commodityCategory; }
    public String getTaxpayerType() { return taxpayerType; }
    public void setTaxpayerType(String taxpayerType) { this.taxpayerType = taxpayerType; }
    public List<DeemedAndExemptProject> getDeemedAndExemptProjectList() { return deemedAndExemptProjectList; }
    public void setDeemedAndExemptProjectList(List<DeemedAndExemptProject> deemedAndExemptProjectList) { this.deemedAndExemptProjectList = deemedAndExemptProjectList; }

    public static class CommodityCategory {
        private String commodityCategoryTaxpayerType;
        private String commodityCategoryCode;

        // Getters and setters
        public String getCommodityCategoryTaxpayerType() { return commodityCategoryTaxpayerType; }
        public void setCommodityCategoryTaxpayerType(String commodityCategoryTaxpayerType) { this.commodityCategoryTaxpayerType = commodityCategoryTaxpayerType; }
        public String getCommodityCategoryCode() { return commodityCategoryCode; }
        public void setCommodityCategoryCode(String commodityCategoryCode) { this.commodityCategoryCode = commodityCategoryCode; }
    }

    public static class DeemedAndExemptProject {
        private String commodityCategoryCode;
        private String currentAmount;
        private String dateFormat;
        private String deemedExemptCode;
        private String nowTime;
        private int pageIndex;
        private int pageNo;
        private int pageSize;
        private String projectId;
        private String projectName;
        private String serviceMark;
        private String timeFormat;

        // Getters and setters
        public String getCommodityCategoryCode() { return commodityCategoryCode; }
        public void setCommodityCategoryCode(String commodityCategoryCode) { this.commodityCategoryCode = commodityCategoryCode; }
        public String getCurrentAmount() { return currentAmount; }
        public void setCurrentAmount(String currentAmount) { this.currentAmount = currentAmount; }
        public String getDateFormat() { return dateFormat; }
        public void setDateFormat(String dateFormat) { this.dateFormat = dateFormat; }
        public String getDeemedExemptCode() { return deemedExemptCode; }
        public void setDeemedExemptCode(String deemedExemptCode) { this.deemedExemptCode = deemedExemptCode; }
        public String getNowTime() { return nowTime; }
        public void setNowTime(String nowTime) { this.nowTime = nowTime; }
        public int getPageIndex() { return pageIndex; }
        public void setPageIndex(int pageIndex) { this.pageIndex = pageIndex; }
        public int getPageNo() { return pageNo; }
        public void setPageNo(int pageNo) { this.pageNo = pageNo; }
        public int getPageSize() { return pageSize; }
        public void setPageSize(int pageSize) { this.pageSize = pageSize; }
        public String getProjectId() { return projectId; }
        public void setProjectId(String projectId) { this.projectId = projectId; }
        public String getProjectName() { return projectName; }
        public void setProjectName(String projectName) { this.projectName = projectName; }
        public String getServiceMark() { return serviceMark; }
        public void setServiceMark(String serviceMark) { this.serviceMark = serviceMark; }
        public String getTimeFormat() { return timeFormat; }
        public void setTimeFormat(String timeFormat) { this.timeFormat = timeFormat; }
    }
}