package com.softproideas.common.models;

import java.util.UUID;

public class FormDashboardDTO {

    private UUID dashboardUUID;
    private String sUUID;
    private String dashboardName;
    private String formName;
    private String lastUpdate;
    private Integer formId;
    private Integer modelId;
    private String dashboardType;

    public UUID getDashboardUUID() {
        return dashboardUUID;
    }

    public void setDashboardUUID(UUID dashboardUUID) {
        this.dashboardUUID = dashboardUUID;
    }

    public String getDashboardName() {
        return dashboardName;
    }

    public void setDashboardName(String dashboardName) {
        this.dashboardName = dashboardName;
    }

    public String getFormName() {
        return formName;
    }

    public void setFormName(String formName) {
        this.formName = formName;
    }

    public String getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(String lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public Integer getFormId() {
        return formId;
    }

    public void setFormId(Integer formId) {
        this.formId = formId;
    }

    public Integer getModelId() {
        return modelId;
    }

    public void setModelId(Integer modelId) {
        this.modelId = modelId;
    }

    public String getsUUID() {
        return sUUID;
    }

    public void setsUUID(String sUUID) {
        this.sUUID = sUUID;
    }

    public String getDashboardType() {
        return dashboardType;
    }

    public void setDashboardType(String dashboardType) {
        this.dashboardType = dashboardType;
    }

}
