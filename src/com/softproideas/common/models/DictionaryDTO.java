package com.softproideas.common.models;

public class DictionaryDTO implements Comparable<DictionaryDTO>{
    private String key;
    private String type;
    private String value;
    private String description;
    private int rowIndex;
    private DictionaryPropertiesDTO dictionaryProperties;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getRowIndex() {
        return rowIndex;
    }

    public void setRowIndex(int rowIndex) {
        this.rowIndex = rowIndex;
    }

    public DictionaryPropertiesDTO getDictionaryProperties() {
        return dictionaryProperties;
    }

    public void setDictionaryProperties(DictionaryPropertiesDTO dictionaryProperties) {
        this.dictionaryProperties = dictionaryProperties;
    }
    
    @Override
    public int compareTo(DictionaryDTO o) {
        
        return this.rowIndex - o.rowIndex;
    }
}
