package com.softproideas.app.core.workbook.model;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.cedar.cp.util.flatform.model.workbook.WorkbookProperties;

public class FlatFormExtractorMapper {

    public static Map<String, String> map(List<Map<String, Object>> result) {
        Map<String, String> properties = new HashMap<String, String>();
        for (Map<String, Object> record: result) {
            String DIM_ID = String.valueOf(((BigDecimal) record.get("DIM_ID")).intValue());

            String FINANCE_CUBE_ID = String.valueOf(((BigDecimal) record.get("FINANCE_CUBE_ID")).intValue());
            properties.put(WorkbookProperties.FINANCE_CUBE_ID.toString(), FINANCE_CUBE_ID);

            String DIM_VIS_ID = (String) record.get("DIM_VIS_ID");
            properties.put(WorkbookProperties.DIMENSION_$_VISID.toString().replace("$", DIM_ID), DIM_VIS_ID);

            String HIER_VIS_ID = (String) record.get("HIER_VIS_ID");
            properties.put(WorkbookProperties.DIMENSION_$_HIERARCHY_VISID.toString().replace("$", DIM_ID), HIER_VIS_ID);

            String FC_VIS_ID = (String) record.get("FC_VIS_ID");
            properties.put(WorkbookProperties.FINANCE_CUBE_VISID.toString(), FC_VIS_ID);

            String MODEL_ID = String.valueOf(((BigDecimal) record.get("MODEL_ID")).intValue());
            properties.put(WorkbookProperties.MODEL_ID.toString(), MODEL_ID);

            String MODEL_VIS_ID = (String) record.get("MODEL_VIS_ID");
            properties.put(WorkbookProperties.MODEL_VISID.toString(), MODEL_VIS_ID);
        }
        return properties;
    }
}
