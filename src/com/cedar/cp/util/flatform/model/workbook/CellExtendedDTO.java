package com.cedar.cp.util.flatform.model.workbook;

public class CellExtendedDTO extends CellDTO {
    private String formula;
    private Double value;
    private String date;
    private boolean invertedValue;
   
    public String getFormula() {
        return formula;
    }

    public void setFormula(String formula) {
        this.formula = formula;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public boolean isInvertedValue() {
        return invertedValue;
    }

    public void setInvertedValue(boolean invertedValue) {
        this.invertedValue = invertedValue;
    }

}
