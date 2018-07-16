package com.cedar.cp.util.flatform.model.workbook;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WorksheetDTO implements Cloneable {
    private String name;
    private Map<String, String> properties;
    private List<? extends CellDTO> cells;

    private boolean isValid = true;

    public WorksheetDTO(){
        properties = new HashMap<String, String>();
    }
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

    public List<? extends CellDTO> getCells() {
        if(cells == null){
            cells = new ArrayList<CellDTO>();
        }
        return cells;
    }

    public void setCells(List<? extends CellDTO> cells) {
        this.cells = cells;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        WorksheetDTO cloned = (WorksheetDTO) super.clone();
        cloned.setCells(cloneCellList());
        if (properties != null) {
            cloned.setProperties(new HashMap<String, String>(properties));
        }
        return cloned;
    }

    private List<CellDTO> cloneCellList() throws CloneNotSupportedException {
        List<CellDTO> clone = new ArrayList<CellDTO>();
        for (CellDTO item: this.cells) {
            clone.add((CellDTO) item.clone());
        }
        return clone;
    }

    public boolean isValid() {
        return isValid;
    }

    public void setValid(boolean isValid) {
        this.isValid = isValid;
    }
}
