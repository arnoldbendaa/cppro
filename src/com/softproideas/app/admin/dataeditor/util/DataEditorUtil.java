/*******************************************************************************
 * Copyright ©2015. IT Services Jacek Kurasiewicz, Warsaw, Poland. All Rights
 * Reserved.
 *
 * Republication, redistribution, granting a license to other parties, using,
 * copying, modifying this software and its documentation is prohibited without the
 * prior written consent of IT Services Jacek Kurasiewicz.
 * Contact The Office of IT Services Jacek Kurasiewicz, ul. Koszykowa 60/62 lok.
 * 43, 00-673 Warszawa, jk@softpro.pl, +48 512-25-67-67, for commercial licensing
 * opportunities.
 *
 * IN NO EVENT SHALL IT SERVICES JACEK KURASIEWICZ BE LIABLE TO ANY PARTY FOR
 * DIRECT, INDIRECT, SPECIAL, INCIDENTAL, OR CONSEQUENTIAL DAMAGES, INCLUDING LOST
 * PROFITS, ARISING OUT OF THE USE OF THIS SOFTWARE AND ITS DOCUMENTATION, EVEN IF
 * IT SERVICES JACEK KURASIEWICZ HAS BEEN ADVISED OF THE POSSIBILITY OF SUCH
 * DAMAGE.
 *
 * IT SERVICES JACEK KURASIEWICZ SPECIFICALLY DISCLAIMS ANY WARRANTIES, INCLUDING,
 * BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A
 * PARTICULAR PURPOSE. THE SOFTWARE AND ACCOMPANYING DOCUMENTATION, IF ANY,
 * PROVIDED HEREUNDER IS PROVIDED "AS IS". IT SERVICES JACEK KURASIEWICZ HAS NO
 * OBLIGATION TO PROVIDE MAINTENANCE, SUPPORT, UPDATES, ENHANCEMENTS, OR
 * MODIFICATIONS.
 *******************************************************************************/
package com.softproideas.app.admin.dataeditor.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.cedar.cp.dto.model.HierarchiesForModelELO;
import com.cedar.cp.util.flatform.model.workbook.WorkbookProperties;
import com.softproideas.app.admin.dataeditor.model.DataEditorRow;

/**
 * <p>Class contains some useful function which is necessary to work with data editor module</p>
 * 
 * @author Łukasz Puła
 * @email lukasz.pula@softproideas.com
 * <p>2014 All rights reserved to Softpro Ideas Group</p>
 */
public class DataEditorUtil {

    //TODO change HierarchiesForModelELO to our hierarchy DTO from our application
    /**
     * Build/generate xml string (which is necessary to saving data) from data editor rows, and completes xml with hierarchies info. 
     * Hierarchies are important to setting workbook properties in worksheets.
     * @return xml string
     */
    public static String generateXmlForSave(List<DataEditorRow> rows, HierarchiesForModelELO hierarchies) {
        int modelId = rows.get(0).getModelId();
        int financeCubeId = rows.get(0).getFinanceCubeId();
        String xml = "<WorkbookUpdate>\n" +
                "<UserId>1</UserId>\n" +
                "<Parameters>\n" + // these are mandatory, but never used, because of full data in cells
                "   <Parameter name=\"FormType\" value=\"6\" />\n" +
                "   <Parameter name=\""+ WorkbookProperties.DIMENSION_0_VISID.toString() + "\" value=\"BS-ANT-\" />\n" +
                "   <Parameter name=\"" + WorkbookProperties.DIMENSION_1_VISID.toString() + "\" value=\"5/1-exp-PL-GRP\" />\n" +
                "   <Parameter name=\"" + WorkbookProperties.DATA_TYPE.toString() + "\" value=\"AV\" />\n" +
                "   <Parameter name=\"" + WorkbookProperties.DIMENSION_2_VISID.toString() + "\" value=\"/2013/10\" />\n" +
                "   <Parameter name=\"" + WorkbookProperties.MODEL_ID.toString() + "\" value=\"" + modelId + "\" />\n" +
                "</Parameters>\n" +
                "<Worksheet name=\" \" >\n" +
                "   <Properties>\n" +
                "       <Property name=\"" + WorkbookProperties.DIMENSION_0_HIERARCHY_VISID.toString() + "\" value=\"" + hierarchies.getDataAsArray()[0][3].toString() + "\" />\n" + // -cc-Group
                "       <Property name=\"" + WorkbookProperties.DIMENSION_1_VISID.toString() + "\" value=\"" + hierarchies.getDataAsArray()[1][1].toString() + "\" />\n" + // -exp
                "       <Property name=\"" + WorkbookProperties.DIMENSION_0_VISID.toString() + "\" value=\"" + hierarchies.getDataAsArray()[0][1].toString() + "\" />\n" + // -cc
                "       <Property name=\"" + WorkbookProperties.MODEL_VISID.toString() + "\" value=\"" + hierarchies.getDataAsArray()[0][0].toString() + "\" />\n" + // just model
                "       <Property name=\"" + WorkbookProperties.DIMENSION_2_VISID.toString() + "\" value=\"" + hierarchies.getDataAsArray()[2][1].toString() + "\" />\n" + // -Cal
                "       <Property name=\"" + WorkbookProperties.DIMENSION_2_HIERARCHY_VISID.toString() + "\" value=\"" + hierarchies.getDataAsArray()[2][1].toString() + "\" />\n" + // -Cal
                "       <Property name=\"" + WorkbookProperties.DIMENSION_1_HIERARCHY_VISID.toString() + "\" value=\"" + hierarchies.getDataAsArray()[1][3].toString() + "\" />\n" + // -exp-PL-GRP
                "       <Property name=\"" + WorkbookProperties.FINANCE_CUBE_VISID.toString() + "\" value=\" \" />\n" +
                "       <Property name=\"" + WorkbookProperties.FINANCE_CUBE_ID.toString() + "\" value=\"FinanceCubeCK|ModelPK|" + modelId + "|FinanceCubePK|" + financeCubeId + "\" />\n" +
                "   </Properties>\n" +
                "   <Cells>\n";
//        String xml = "<WorkbookUpdate>\n"
//                + "    <UserId>1</UserId>\n"
//                + "    <Parameters>\n"
//                + "        <Parameter name=\"FormType\" value=\"6\" />\n"
//                + "        <Parameter name=\"%dim0Identifier%\" value=\"BS-ANT-\" />\n"
//                + "        <Parameter name=\"%dim1Identifier%\" value=\"5/1-exp-PL-GRP\" />\n"
//                + "        <Parameter name=\"%dataType%\" value=\"AV\" />\n"
//                + "        <Parameter name=\"%dim2Identifier%\" value=\"/2013/10\" />\n"
//                + "        <Parameter name=\"%modelId%\" value=\"" + modelId + "\" />\n"
//                + "    </Parameters>\n"
//                + "    <Worksheet name=\" \" >\n"
//                + "    <Properties>\n"
//                + "        <Property name=\"DIMENSION_0_HIERARCHY_VISID\" value=\"" + hierarchies.getDataAsArray()[0][3].toString() + "\" />\n"
//                + "        <Property name=\"DIMENSION_1_VISID\" value=\"" + hierarchies.getDataAsArray()[1][1].toString() + "\" />\n"
//                + "        <Property name=\"DIMENSION_0_VISID\" value=\"" + hierarchies.getDataAsArray()[0][1].toString() + "\" />\n"
//                + "        <Property name=\"MODEL_VISID\" value=\"" + hierarchies.getDataAsArray()[0][0].toString() + "\" />\n"
//                + "        <Property name=\"DIMENSION_2_VISID\" value=\"" + hierarchies.getDataAsArray()[2][1].toString() + "\" />\n"
//                + "        <Property name=\"DIMENSION_2_HIERARCHY_VISID\" value=\"" + hierarchies.getDataAsArray()[2][1].toString() + "\" />\n"
//                + "        <Property name=\"DIMENSION_1_HIERARCHY_VISID\" value=\"" + hierarchies.getDataAsArray()[1][3].toString() + "\" />\n"
//                + "        <Property name=\"FINANCE_CUBE_VISID\" value=\" \" />\n"
//                + "        <Property name=\"FINANCE_CUBE_ID\" value=\"FinanceCubeCK|ModelPK|" + modelId + "|FinanceCubePK|" + financeCubeId + "\" />\n"
//                + "    </Properties>\n"
//                + "    <Cells>\n";

                for (DataEditorRow dataEditorRow: rows) {
                    String addr = "dim0="+dataEditorRow.getCostCenter()+",dim1="+dataEditorRow.getExpenseCode();
                    addr += ",dim2=/"+dataEditorRow.getYear()+"/"+dataEditorRow.getPeriod()+",dt="+dataEditorRow.getDataType();
                    
                    Object value = dataEditorRow.getValue();
                    if (value == null) {
                        xml += "        <Cell addr=\"" + addr + "\" value=\"\" putCell=\"true\"/>\n";
                    } else {
                        try {
                            Double doubleValue = Double.parseDouble(value.toString());
                            xml += "        <Cell addr=\"" + addr + "\" value=\"" + doubleValue + "\" putCell=\"true\"/>\n";
                        } catch (NumberFormatException e) {
                            xml += "        <Cell addr=\"" + addr + "\" value=\"" + value.toString() + "\" putCell=\"true\"/>\n";
                        }
                    }
                }

        xml += "    </Cells>\n"
            + "    </Worksheet>\n"
            + "</WorkbookUpdate>";
        return xml;
    }

    /**
     * Divide list of rows to map aggregated by model id.
     * @return map - aggregated (by modelId) map of {@link DataEditorRow}
     */
    public static HashMap<Integer, List<DataEditorRow>> divideRowsByModel(List<DataEditorRow> rows) {
        HashMap<Integer, List<DataEditorRow>> map = new HashMap<Integer, List<DataEditorRow>>();
        int currentModelId = 0;
        for (DataEditorRow row: rows) {
            if (currentModelId != row.getModelId()) {
                currentModelId = row.getModelId();
                List<DataEditorRow> newRows = new ArrayList<DataEditorRow>();
                map.put(currentModelId, newRows);
            }
            List<DataEditorRow> filteredRows = map.get(currentModelId);
            filteredRows.add(row);
        }
        return map;
    }
}
