package com.cedar.cp.util.flatform.model.workbook;

import java.util.List;
import java.util.Map;

public class WorksheetExtendedDTO{

    private String name;
    private Map<String, String> properties;
    private List<CellExtendedDTO> cells;
    private boolean isValid = true;
    
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public Map<String, String> getProperties() {
        return properties;
    }
    public void setProperties(Map<String, String> properties) {
        this.properties = properties;
    }
    public List<CellExtendedDTO> getCells() {
        return cells;
    }
    public void setCells(List<CellExtendedDTO> cells) {
        this.cells = cells;
    }
    public boolean isValid() {
        return isValid;
    }
    public void setValid(boolean isValid) {
        this.isValid = isValid;
    }
    
    public WorksheetDTO getWorksheetDTO(){
        WorksheetDTO worksheet = new WorksheetDTO();
        worksheet.setCells(cells);
        worksheet.setName(name);
        worksheet.setProperties(properties);
        worksheet.setValid(isValid);
        return worksheet;
    }
}
