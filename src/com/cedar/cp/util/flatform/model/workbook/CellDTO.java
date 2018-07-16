package com.cedar.cp.util.flatform.model.workbook;

import java.util.ArrayList;

public class CellDTO implements Cloneable {
    private int row;
    private int column;
    private String[][] validationMessages;
    private String inputMapping;
    private String outputMapping;
    private String text;
    private ArrayList<String> tags;

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getColumn() {
        return column;
    }

    public void setColumn(int column) {
        this.column = column;
    }

    public String getInputMapping() {
        return inputMapping;
    }

    public void setInputMapping(String inputMapping) {
        this.inputMapping = inputMapping;
    }

    public String getOutputMapping() {
        return outputMapping;
    }

    public void setOutputMapping(String outputMapping) {
        this.outputMapping = outputMapping;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    public ArrayList<String> getTags() {
        return tags;
    }

    public void setTags(ArrayList<String> tags) {
        this.tags = tags;
    }
    
    public void addTag(String tag) {
        if (this.tags == null) {
            this.tags = new ArrayList<String>();
        }
        if (!this.tags.contains(tag)) {
            tags.add(tag);
        }
    }

    public String[][] getValidationMessages() {
        return validationMessages;
    }

    public void setValidationMessages(String[][] validationMessages) {
        this.validationMessages = validationMessages;
    }
    @Override
    public String toString(){
        return column + ":" + row;
    }
    
}
