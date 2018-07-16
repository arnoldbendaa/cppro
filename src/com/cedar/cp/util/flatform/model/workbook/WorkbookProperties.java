package com.cedar.cp.util.flatform.model.workbook;
//containsInputMapping
//checkFormulaMapping
//WorkbookProperties.DIMENSION_$_VISID.toString()
//WorkbookProperties.DIMENSION_$_VISID.toString().replace("$", String.valueOf(sadf))
public enum WorkbookProperties {
    //@formatter:off
    MODEL_ID("MODEL_ID", "%modelId%"),
    MODEL_VISID("MODEL_VISID", "MODEL_VISID"),
    DIM_$("DIM_$", "%dim$%"),
    DIM_0("DIM_0", "%dim0%"),
    DIM_1("DIM_1", "%dim1%"),
    DIM_2("DIM_2", "%dim2%"),
    DIM_3("DIM_3", "%dim3%"),
    DIMENSION_$_VISID("DIMENSION_$_VISID", "%dim$Identifier%"),
    DIMENSION_0_VISID("DIMENSION_0_VISID", "%dim0Identifier%"),
    DIMENSION_1_VISID("DIMENSION_1_VISID", "%dim1Identifier%"),
    DIMENSION_2_VISID("DIMENSION_2_VISID", "%dim2Identifier%"),
    DIMENSION_3_VISID("DIMENSION_3_VISID", "%dim3Identifier%"),
    DIMENSION_$_HIERARCHY_VISID("DIMENSION_$_HIERARCHY_VISID", "DIMENSION_$_HIERARCHY_VISID"),
    DIMENSION_0_HIERARCHY_VISID("DIMENSION_0_HIERARCHY_VISID", "DIMENSION_0_HIERARCHY_VISID"),
    DIMENSION_1_HIERARCHY_VISID("DIMENSION_1_HIERARCHY_VISID", "DIMENSION_1_HIERARCHY_VISID"),
    DIMENSION_2_HIERARCHY_VISID("DIMENSION_2_HIERARCHY_VISID", "DIMENSION_2_HIERARCHY_VISID"),
    DIMENSION_3_HIERARCHY_VISID("DIMENSION_3_HIERARCHY_VISID", "DIMENSION_3_HIERARCHY_VISID"),
    DIMENSION_$_DESCRIPTION("DIMENSION_$_DESCRIPTION", "%dim$Description%"),
    DIMENSION_0_DESCRIPTION("DIMENSION_0_DESCRIPTION", "%dim0Description%"),
    DIMENSION_1_DESCRIPTION("DIMENSION_1_DESCRIPTION", "%dim1Description%"),
    DIMENSION_2_DESCRIPTION("DIMENSION_2_DESCRIPTION", "%dim2Description%"),
    DIMENSION_3_DESCRIPTION("DIMENSION_3_DESCRIPTION", "%dim3Description%"),
    DIMENSION_2_VISID_FROM("DIMENSION_2_VISID_FROM", "DIMENSION_2_VISID_FROM"),
    DIMENSION_2_VISID_TO("DIMENSION_2_VISID_TO", "DIMENSION_2_VISID_TO"),
    FINANCE_CUBE_ID("FINANCE_CUBE_ID", "FINANCE_CUBE_ID"),
    FINANCE_CUBE_VISID("FINANCE_CUBE_VISID", "FINANCE_CUBE_VISID"),
    EXCLUDE_DATA_TYPES("EXCLUDE_DATA_TYPES", "EXCLUDE_DATA_TYPES"),
    INVERT_NUMBERS_VALUE("INVERT_NUMBERS_VALUE", "INVERT_NUMBERS_VALUE"),
    BUDGET_CYCLE_PERIOD_ID("BUDGET_CYCLE_PERIOD_ID", "BUDGET_CYCLE_PERIOD_ID"),
    DATA_TYPE("DATA_TYPE", "%dataType%"),
    CALENDAR_INFO("CalendarInfo.", "CalendarInfo."),
    PROTECTED("PROTECTED", "PROTECTED"),
    UNDEFINED("UNDEFINED", "UNDEFINED");
    //@formatter:on
    
    private String name;
    private String oldName;

    private WorkbookProperties(String name, String oldName) {
        this.name = name;
        this.oldName = oldName;
    }

    public String toString() {
        return this.name;
    }

    public String toOldString() {
        return this.oldName;
    }
    
    public static WorkbookProperties getProperty(String name){
        if(name == null || name.trim().isEmpty()){
            return WorkbookProperties.UNDEFINED;
        }
        for(WorkbookProperties property: WorkbookProperties.values()){
            if(property.name.equals(name) || property.oldName.equals(name)){
                return property;
            }
        }
        return WorkbookProperties.UNDEFINED;
    }
}
