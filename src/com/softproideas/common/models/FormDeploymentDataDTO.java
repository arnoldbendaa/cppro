package com.softproideas.common.models;

import java.io.Serializable;
import java.util.List;



/**
 * com.cedar.cp.dto.xmlform.FormDeploymentDataImpl
 */
public class FormDeploymentDataDTO implements Serializable{

    private Integer flatFormId;
    private String identifier;
    private String description;
    private String dataType;
    private Integer financeCubeId;
    private Integer modelId;
    private Integer budgetCycleId;
    private Boolean mobile;
    private Integer profileId;
    private List<StructureElementCoreDTO> structureElements;
    private int mailType;
    private String mailContent;

    
    public Integer getFlatFormId() {
        return flatFormId;
    }

    public void setFlatFormId(Integer flatFormId) {
        this.flatFormId = flatFormId;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getFinanceCubeId() {
        return financeCubeId;
    }

    public void setFinanceCubeId(Integer financeCubeId) {
        this.financeCubeId = financeCubeId;
    }

    public Integer getModelId() {
        return modelId;
    }

    public void setModelId(Integer modelId) {
        this.modelId = modelId;
    }

    public Integer getBudgetCycleId() {
        return budgetCycleId;
    }

    public void setBudgetCycleId(Integer budgetCycleId) {
        this.budgetCycleId = budgetCycleId;
    }

    

    public int getMailType() {
        return mailType;
    }

    public void setMailType(int mailType) {
        this.mailType = mailType;
    }

    public String getMailContent() {
        return mailContent;
    }

    public void setMailContent(String mailContent) {
        this.mailContent = mailContent;
    }

    public Boolean getMobile() {
        return mobile;
    }

    public void setMobile(Boolean mobile) {
        this.mobile = mobile;
    }

    public Integer getProfileId() {
        return profileId;
    }

    public void setProfileId(Integer profileId) {
        this.profileId = profileId;
    }

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public List<StructureElementCoreDTO> getStructureElements() {
        return structureElements;
    }

    public void setStructureElements(List<StructureElementCoreDTO> structureElements) {
        this.structureElements = structureElements;
    }

}
