package com.softproideas.util.validation;

public enum MappingFunction {
    FINANCE_CUBE("financeCube"),
    DIM0_IDENTIFIER("dim0Identifier"),
    DIM0_DESCRIPTION("dim0Description"),
    DIM1_IDENTIFIER("dim1Identifier"),
    DIM1_DESCRIPTION("dim1Description"),
    DIM2_IDENTIFIER("dim2Identifier"),
    DIM2_DESCRIPTION("dim2Description"),
    CELL("cell"),
    PARAM("param"),
    STRUCTURES("structures"),
    GET_CELL("getCell"),
    PUT_CELL("putCell"),
    GET_VIS_ID("getVisId"),
    GET_DESCRIPTION("getDescription"),
    GET_LABEL("getLabel"),
    FORM_LINK("formLink"),
    FORM_FULL_LINK("formFullLink"),
    GET_BASE_VAL("getBaseVal"),
    GET_QUANTITY("getQuantity"),
    GET_CUM_BASE_VAL("getCumBaseVal"),
    GET_CUM_QUANTITY("getCumQuantity"),
    GET_CURRENCY_LOOKUP("getCurrencyLookup"),
    GET_PARAMETER_LOOKUP("getParameterLookup"),
    GET_AUCTION_LOOKUP("getAuctionLookup"),
    GET_GLOB("getGlob"),
    UNDEFINED("undefined");
    
    private final static String MAPPING_PATTERN = createMappingsPattern();
    private String function;
    private MappingFunction(String function){
        this.function = function;
    }
    public static MappingFunction getMapping(String function){
        for(MappingFunction mf:MappingFunction.values()){
            if(mf.function.equals(function)){
                return mf;
            }
        }
        return UNDEFINED;
    }
    public String toString(){
        return function;
    }
    
    public static String getMappingsPattern(){
        return MAPPING_PATTERN;
    }
    
    public static String getFullMappingsPattern(){
        return "cedar\\.cp\\.(" + MAPPING_PATTERN + ")\\([^)]*\\)";
    }
    
    private static String createMappingsPattern(){
        StringBuilder sb = new StringBuilder();
        //sb.append("^cedar\\.cp\\.(");
        MappingFunction[] values = MappingFunction.values();
        for(int i = 0; i < values.length; i++){
            sb.append(values[i].toString());
            if(i < values.length -1){
                sb.append("|");
            }
        }
        //sb.append(")\\([^)]*\\)$");
        return sb.toString();
    }
    public static boolean containsAnyMappingFunction(String expression){
        if(expression == null || expression.trim().isEmpty()){
            return false;
        }
        for(MappingFunction mf:MappingFunction.values()){
            if(expression.contains(mf.toString())){
                return true;
            }
        }
        return false;
    }
}