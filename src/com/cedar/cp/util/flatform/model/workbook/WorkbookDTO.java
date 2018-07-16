package com.cedar.cp.util.flatform.model.workbook;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WorkbookDTO implements Cloneable {
    private Map<String, String> properties;
    private List<WorksheetDTO> worksheets;
    private String validationMessage;
    private boolean isValid = true;

    public Map<String, String> getProperties() {
        if (properties == null) {
            properties = new HashMap<String, String>();
        }
        return properties;
    }

    public void setProperties(Map<String, String> properties) {
        this.properties = properties;
    }

    public List<WorksheetDTO> getWorksheets() {
        if (worksheets == null) {
            worksheets = new ArrayList<WorksheetDTO>();
        }
        return worksheets;
    }

    public void setWorksheets(List<WorksheetDTO> worksheets) {
        this.worksheets = worksheets;
    }
    
    @Override
    public Object clone() throws CloneNotSupportedException {
        WorkbookDTO cloned = (WorkbookDTO) super.clone();
        if (properties != null) {
            cloned.setProperties(new HashMap<String, String>(properties));
        }
        cloned.setWorksheets(cloneWorksheetList());
        return cloned;
    }
    
    private List<WorksheetDTO> cloneWorksheetList() throws CloneNotSupportedException {
        List<WorksheetDTO> clone = new ArrayList<WorksheetDTO>();
        for (WorksheetDTO item: this.worksheets) {
            clone.add((WorksheetDTO) item.clone());
        }
        return clone;
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
}
