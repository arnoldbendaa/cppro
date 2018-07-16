package com.softproideas.util.validation;

public enum MappingArguments{
    TYPE("type"),
    DATA_TYPE("dt"),
    DIM0("dim0"),
    DIM1("dim1"),
    DIM2("dim2"),
    YEAR("year"),
    PERIOD("period"),
    CMPY("cmpy"),
    COMPANY("company"),
    CURRENCY("curr"),
    DATE("date"),
    COST_CENTER("costc"),
    FIELD("field"),
    UNDEFINED("undefined"),
    MODEL("model"),
    DIM3("dim3");
    
    private String name;
    private MappingArguments(String name){
        this.name = name;
    }
    public static MappingArguments getArgument(String name){
        for(MappingArguments ma:MappingArguments.values()){
            if(ma.name.equals(name)){
                return ma;
            }
        }
        return UNDEFINED;
    }
    public String toString(){
        return name;
    }
}
