package com.cedar.cp.util.flatform.model.workbook;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class WorkbookExtendedDTO{
    private Map<String, String> properties;
    private String validationMessage;
    private boolean isValid = true;    
    private List<WorksheetExtendedDTO> worksheets;
    
    public Map<String, String> getProperties() {
        return properties;
    }
    public void setProperties(Map<String, String> properties) {
        this.properties = properties;
    }
    public String getValidationMessage() {
        return validationMessage;
    }
    public void setValidationMessage(String validationMessage) {
        this.validationMessage = validationMessage;
    }
    public boolean isValid() {
        return isValid;
    }
    public void setValid(boolean isValid) {
        this.isValid = isValid;
    }
    public List<WorksheetExtendedDTO> getWorksheets() {
        return worksheets;
    }
    public void setWorksheets(List<WorksheetExtendedDTO> worksheets) {
        this.worksheets = worksheets;
    }
    
    public WorkbookDTO getWorkbookDTO(){
        WorkbookDTO workbook = new WorkbookDTO();
        List<WorksheetDTO> worksheetsDTO = new ArrayList<WorksheetDTO>();
        for(WorksheetExtendedDTO worksheet: worksheets){
            worksheetsDTO.add(worksheet.getWorksheetDTO());
        }
        workbook.setProperties(properties);
        workbook.setValid(isValid);
        workbook.setValidationMessage(validationMessage);
        workbook.setWorksheets(worksheetsDTO);
        
        return workbook;
    }
    
}
