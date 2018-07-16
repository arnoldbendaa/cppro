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
package com.cedar.cp.util.flatform.model.workbook.editor;

import java.io.StringReader;

import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;

import com.cedar.cp.util.GeneralUtils;
import com.cedar.cp.util.flatform.reader.XMLReader;
import com.softproideas.util.validation.MappingFunction;

public class FormUtil {

    /**
     * Check if the given formula string contains valid input mapping string
     * 
     * @param string
     *            string to check
     * @return true if the given string contains input mapping string, false otherwise
     */
    public static boolean checkFormulaMapping(String string) {
        return MappingFunction.containsAnyMappingFunction(string);
    }

    /**
     * Check if the given string contains input/output/formula/text
     * 
     * @param string
     *            string to check
     * @return true if the given string contains input/output/formula/text
     */
    public static boolean checkImportString(String string) {
        if (!GeneralUtils.isEmptyOrNull(string)) {
            if (string.toLowerCase().contains("formula{{")) {
                return true;
            } else if (string.toLowerCase().contains("text{{")) {
                return true;
            } else if (string.toLowerCase().contains("input{{")) {
                return true;
            } else if (string.toLowerCase().contains("output{{")) {
                return true;
            } else if (string.toLowerCase().contains("in{{")) {
                return true;
            } else if (string.toLowerCase().contains("out{{")) {
                return true;
            } else if (string.toLowerCase().contains("f{{")) {
                return true;
            } else if (string.toLowerCase().contains("t{{")) {
                return true;
            } else if (string.toLowerCase().contains("i{{")) {
                return true;
            } else if (string.toLowerCase().contains("o{{")) {
                return true;
            }
        }
        return false;
    }

    @Deprecated
    public static void setErrorCellFormat(Cell cell) {
        CellStyle style = cell.getCellStyle();
        style.setTopBorderColor(HSSFColor.RED.index);
        style.setBorderTop(CellStyle.BORDER_THICK);
        style.setRightBorderColor(HSSFColor.RED.index);
        style.setBorderRight(CellStyle.BORDER_THICK);
        style.setBottomBorderColor(HSSFColor.RED.index);
        style.setBorderBottom(CellStyle.BORDER_THICK);
        style.setLeftBorderColor(HSSFColor.RED.index);
        style.setBorderLeft(CellStyle.BORDER_THICK);
    }

    public static com.cedar.cp.util.flatform.model.Workbook loadFromXML(String xml) throws Exception {
        XMLReader reader = new XMLReader();
        reader.init();
        StringReader sr = new StringReader(xml);
        reader.parseConfigFile(sr);
        com.cedar.cp.util.flatform.model.Workbook workbook = reader.getWorkbook();
        // workbook.setResourceHandler(this.mFlatFormApplication);
        return workbook;
    }

    /* Insert values in workbook cells (to workbook test).
     * copied from /cp-tc/src/main/java/com/cedar/cp/tc/apps/xmlform/xcellform/XcellFormDesignPanelController.java */
//    public static Map<String, String> buildTestContextualParameters(com.cedar.cp.util.flatform.model.Workbook workbook, WorkbookDTO workbookDTO, CPConnection connection) throws ValidationException {
//        HashMap map = new HashMap();
//        List paramList = getTestParams(workbook, workbookDTO, connection);
//        Iterator i$ = paramList.iterator();
//        String modelVisId = (workbook).getProperty(WorkbookProperties.MODEL_VISID.toString());
//        EntityRef[] dimensionRefs = modelVisId != null ? initDimRefs(workbookDTO.getProperties().get(WorkbookProperties.MODEL_VISID.toString()), connection) : new EntityRef[0];
//
//        while (i$.hasNext()) {
//            Pair pair = (Pair) i$.next();
//            String key = (String) pair.getChild1();
//            if (key.length() == WorkbookProperties.DIMENSION_$_VISID.toString().length()) {
//                int dimStrIndex = WorkbookProperties.DIMENSION_$_VISID.toString().indexOf("$");
//                Character index = Character.valueOf(key.charAt(dimStrIndex));
//                String visContext = WorkbookProperties.DIMENSION_$_VISID.toString().replace("$", String.valueOf(index.charValue()));
//                String visId = (String) pair.getChild2();
//                map.put(visContext, visId);
//                int i = Integer.parseInt(index.toString());
//                int dimensionId = connection.getDimensionsProcess().getDimensionId((DimensionRef) dimensionRefs[i]);
//                EntityList entityList = connection.getListHelper().getStructureElementByVisId(visId, dimensionId);
//                String description = "";
//                if (entityList.getNumRows() > 0) {
//                    description = String.valueOf(entityList.getValueAt(0, "Description"));
//                } else {
//                    CalendarInfo descContext = connection.getDataEntryProcess().getCalendarInfoForModel(modelVisId);
//                    CalendarElementNode elementNode = descContext.findElement(visId);
//                    if (elementNode != null) {
//                        description = elementNode.getDescription();
//                    }
//                }
//
//                String descContext1 = WorkbookProperties.DIMENSION_$_DESCRIPTION.toString().replace("$", String.valueOf(index.charValue()));
//                map.put(descContext1, description);
//            } else if (key.equalsIgnoreCase(WorkbookProperties.DATA_TYPE.toString())) {
//                map.put(WorkbookProperties.DATA_TYPE.toString(), pair.getChild2());
//            }
//        }
//
//        return map;
//    }

    /* Prepare workbook test properties.
     * copied from /cp-tc/src/main/java/com/cedar/cp/tc/apps/xmlform/xcellform/XcellFormDesignPanelController.java */
//    private static List<Pair<String, String>> getTestParams(com.cedar.cp.util.flatform.model.Workbook workbook, WorkbookDTO workbookDTO, CPConnection connection) {
//        ArrayList props = new ArrayList();
//        if (workbook != null && workbookDTO.getProperties() != null) {
//            Iterator i$ = workbookDTO.getProperties().entrySet().iterator();
//            while (i$.hasNext()) {
//                Entry entry = (Entry) i$.next();
//                props.add(new Pair(entry.getKey(), entry.getValue()));
//                if (((String) entry.getKey()).indexOf(WorkbookProperties.MODEL_VISID.toString()) >= 0) {
//                    String modelVisId = (String) entry.getValue();
//                }
//            }
//        }
//        return props;
//    }

    /* Prepare dimension refs for workbook test.
     * copied from /cp-tc/src/main/java/com/cedar/cp/tc/apps/xmlform/xcellform/XcellFormDesignPanelController.java */
//    private static EntityRef[] initDimRefs(String modelVisId, CPConnection connection) {
//        EntityList modelList = connection.getModelsProcess().getAllModelsForLoggedUser();
//        ModelRef model = null;
//
//        for (int eList = 0; eList < modelList.getNumRows(); ++eList) {
//            EntityList dims = modelList.getRowData(eList);
//            ModelRef i = (ModelRef) dims.getValueAt(0, "Model");
//            if (i.getNarrative().equals(modelVisId)) {
//                model = i;
//                break;
//            }
//        }
//
//        EntityList dimensionsForModel = connection.getModelsProcess().getAllDimensionsForModel(model);
//        DimensionRef[] dimensionRefs = new DimensionRef[dimensionsForModel.getNumRows()];
//
//        for (int i = 0; i < dimensionsForModel.getNumRows(); ++i) {
//            dimensionRefs[i] = (DimensionRef) dimensionsForModel.getValueAt(i, "Dimension");
//        }
//
//        return dimensionRefs;
//    }

}