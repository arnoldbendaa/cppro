package com.softproideas.api.cpfunctionsevaluator;
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

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.poi.hssf.util.CellReference;

import com.cedar.cp.util.GeneralUtils;
import com.cedar.cp.util.flatform.model.workbook.CellDTO;
import com.cedar.cp.util.flatform.model.workbook.CellExtendedDTO;
import com.cedar.cp.util.flatform.model.workbook.WorkbookDTO;
import com.cedar.cp.util.flatform.model.workbook.WorkbookProperties;
import com.cedar.cp.util.flatform.model.workbook.WorksheetDTO;
import com.softproideas.util.validation.GlobalMappedMappingValidator;
import com.softproideas.util.validation.MappingArguments;
import com.softproideas.util.validation.MappingFunction;
import com.softproideas.util.validation.MappingValidator;

//import com.softproideas.commons.util.DimensionUtil;

public class FlatFormExtractorUtil {

    public static final String SHEET_REFERENCE_PATTERN = "#[^#]*#[ABCDEFGHIJKLMNOPQRSTUVWXYZ]*#\\d+";
    public static final String CELL_REFERENCE_PATTERN = "#[ABCDEFGHIJKLMNOPQRSTUVWXYZ]*#\\d+";

    public static final String MAPPING_FUNCTIONS_PATTERN = MappingFunction.getFullMappingsPattern();

//    public enum MappingType {
//        MAPPING, REFERENCE, UNDEFINED, INVALID
//    }

//    public static void addIndex(MappingType type, int sheetIndex, int cellIndex, Map<MappingType, Map<Integer, List<Integer>>> processingList) {
//        if (processingList.containsKey(type) == false) {
//            processingList.put(type, new HashMap<Integer, List<Integer>>());
//        }
//        if (processingList.get(type).containsKey(sheetIndex) == false) {
//            processingList.get(type).put(sheetIndex, new ArrayList<Integer>());
//        }
//        processingList.get(type).get(sheetIndex).add(cellIndex);
//    }

    public static String getDataType(String inputMapping, WorkbookDTO workbook) {
        // String dataType = DimensionUtil.getKeyFromMapping(inputMapping, "dt").toLowerCase();
        String dataType = getKeyFromMapping(inputMapping, "dt").toLowerCase();
        if (GeneralUtils.isEmptyOrNull(dataType)) {
            dataType = workbook.getProperties().get(WorkbookProperties.DATA_TYPE.toString());
        }
        return dataType;
    }

    // replaces references with empty string if no references supplied
    public static String replaceCellReferenceWithValue(String mapping, String cellReferenceValue, String cellReference) {
        if (cellReference == null || cellReference.trim().isEmpty() || (cellReference.toUpperCase().matches(SHEET_REFERENCE_PATTERN) == false && cellReference.toUpperCase().matches(CELL_REFERENCE_PATTERN) == false)) {
            return "#Reference Error";
        }
        if (cellReferenceValue == null) {
            cellReferenceValue = "";
        } else if (cellReferenceValue.toUpperCase().matches(SHEET_REFERENCE_PATTERN) == true || cellReferenceValue.toUpperCase().matches(CELL_REFERENCE_PATTERN) == true) {
            return "#Nested References";
        }
        return mapping.replace(cellReference, cellReferenceValue);
    }

    public static String invertValueIfNecessary(String mapping, String value, WorkbookDTO workbook, CellExtendedDTO cell) {
        if (GeneralUtils.isEmptyOrNull(value)) {
            return value;
        }
        // skip if "null" or 0
        if (value.equals("null") || value.equals("0")) {
            return value;
        }
        if (mapping == null || 
            mapping.contains(MappingFunction.GET_CURRENCY_LOOKUP.toString()) || 
            mapping.contains(MappingFunction.GET_PARAMETER_LOOKUP.toString()) ||
            mapping.contains(MappingFunction.GET_VIS_ID.toString())
                ) {
            return value;
        }
        if (mapping != null && (
                mapping.contains(MappingFunction.GET_CUM_BASE_VAL.toString()) || 
                mapping.contains(MappingFunction.GET_CUM_QUANTITY.toString()) ||
                mapping.contains(MappingFunction.GET_QUANTITY.toString()) ||
                mapping.contains(MappingFunction.GET_BASE_VAL.toString())
                ) && (value == null || value.isEmpty())
                    ) {
                return "0";
        }
        String dataType = getKeyFromMapping(mapping, "dt").toLowerCase();
        // skip if input mapping without data type ("dt")
        if (GeneralUtils.isEmptyOrNull(dataType)) {
            dataType = workbook.getProperties().get(WorkbookProperties.DATA_TYPE.toString());
            if (GeneralUtils.isEmptyOrNull(dataType)) {
                return value;
            }
        }
        if (isNumeric(value) && invertNumber(workbook) && !isExcludeDataType(workbook, dataType)) {
            Double d = Double.parseDouble(value);
            d = d * (-1);
            value = d.toString();
            if (value.substring(value.length() - 2).equals(".0")) {
                value = value.substring(0, value.length() - 2);
            }
            cell.setInvertedValue(true);
        }

        return value;
    }

    public static boolean isExcludeDataType(WorkbookDTO workbook, String dataType) {
        Map<String, String> props = workbook.getProperties();
        if (props != null) {
            String value = props.get(WorkbookProperties.EXCLUDE_DATA_TYPES.toString());
            if (value != null && value.toLowerCase().contains(dataType.toLowerCase())) {
                return true;
            }
        }
        return false;
    }

    public static boolean invertNumber(WorkbookDTO workbook) {
        Map<String, String> props = workbook.getProperties();
        if (props != null) {
            String value = props.get(WorkbookProperties.INVERT_NUMBERS_VALUE.toString());
            if ("true".equals(value)) {
                return true;
            }
        }
        return false;
    }

    public static String getCellReference(String mapping) {
        // #Sheet1#A#1
        Pattern pattern = Pattern.compile(SHEET_REFERENCE_PATTERN);
        Matcher matcher = pattern.matcher(mapping.toUpperCase());
        if (matcher.find()) {
            return matcher.group();
        }
        // #A#1
        pattern = Pattern.compile(CELL_REFERENCE_PATTERN);
        matcher = pattern.matcher(mapping.toUpperCase());
        if (matcher.find()) {
            return matcher.group();
        }

        return "";
    }

    public static boolean containsCellReference(String mapping) {
        if (GeneralUtils.isEmptyOrNull(mapping)) {
            return false;
        }
        // #Sheet1#A#1
        Pattern pattern = Pattern.compile(SHEET_REFERENCE_PATTERN);
        Matcher matcher = pattern.matcher(mapping.toUpperCase());
        if (matcher.find()) {
            return true;
        }
        // #A#1
        pattern = Pattern.compile(CELL_REFERENCE_PATTERN);
        matcher = pattern.matcher(mapping.toUpperCase());
        if (matcher.find()) {
            return true;
        }
        return false;
    }

    public static String fillSimpleMappingReferences(String mapping, WorksheetDTO worksheet, WorkbookDTO workbook) {
        String cellReference = FlatFormExtractorUtil.getCellReference(mapping);
        if (!GeneralUtils.isEmptyOrNull(cellReference)) {
            String cellReferenceValue = findValueForCellReference(cellReference, worksheet, workbook);
            return FlatFormExtractorUtil.replaceCellReferenceWithValue(mapping, cellReferenceValue, cellReference);
        }
        return "#Reference error";
    }

    public static String findValueForCellReference(String cellReference, WorksheetDTO worksheet, WorkbookDTO workbook) {
        String value = "";

        String[] tempTab = cellReference.split("#");
        if (tempTab.length == 4) { // #Sheet1#A#1
            String sheetName = tempTab[1];
            for (WorksheetDTO worksheet1: workbook.getWorksheets()) {
                if (worksheet1.getName().equals(sheetName)) {
                    int rowNum = Integer.valueOf(tempTab[3]) - 1;
                    int colNum = CellReference.convertColStringToIndex(tempTab[2]);
                    CellExtendedDTO cell = getCell(worksheet1, colNum, rowNum);
                    if (cell != null) {
                        if (cell.getText() != null) {
                            value = cell.getText();
                        } else if (cell.getDate() != null) {
                            value = cell.getDate();
                        } else if (cell.getValue() != null) {
                            value = cell.getValue().toString();
                            if (value.substring(value.length() - 2).equals(".0")) {
                                value = value.substring(0, value.length() - 2);
                            }
                        }
                    }
                    value = cell.getText();
                }
            }

        } else if (tempTab.length == 3) { // #A#1
            int rowNum = Integer.valueOf(tempTab[2]) - 1;
            int colNum = CellReference.convertColStringToIndex(tempTab[1]);
            CellExtendedDTO cell = getCell(worksheet, colNum, rowNum);
            if (cell != null) {
                if (cell.getText() != null) {
                    value = cell.getText();
                } else if (cell.getDate() != null) {
                    value = cell.getDate();
                } else if (cell.getValue() != null) {
                    value = cell.getValue().toString();
                    if (value.substring(value.length() - 2).equals(".0")) {
                        value = value.substring(0, value.length() - 2);
                    }
                }
            }
        }
        return value;
    }

    public static CellExtendedDTO getCell(WorksheetDTO worksheet, int column, int row) {
        for (CellDTO cell: worksheet.getCells()) {
            if (cell.getColumn() == column && cell.getRow() == row) {
                return (CellExtendedDTO)cell;
            }
        }
        return null;
    }

    public static boolean isGlobal(String modelVisId) {
        if (modelVisId != null && modelVisId.toUpperCase().startsWith("GL")) {
            return true;
        }
        return false;
    }

    public static String getCompanyFromGlobalMapping(String mapping) {
        String company = "";
        MappingValidator mv = new GlobalMappedMappingValidator(mapping);
        if (mv.isValid() == true && mv.getFunction().equals(MappingFunction.GET_CELL)) {
            LinkedHashMap<String, String> args = (LinkedHashMap<String, String>) mv.getListOfArguments();
            if (args.containsKey(MappingArguments.COMPANY.toString())) {
                company = args.get(MappingArguments.COMPANY.toString());
            } else if (args.containsKey(MappingArguments.CMPY.toString())) {
                company = args.get(MappingArguments.CMPY.toString());
            }
        }
        return company;
    }

    public static Set<String> getCompaniesFromGlobalFormulaMapping(String mapping) {
        Set<String> companies = new HashSet<String>();

        Pattern pattern = Pattern.compile(MAPPING_FUNCTIONS_PATTERN);
        Matcher matcher = pattern.matcher(mapping);
        while (matcher.find()) {
            String company = getCompanyFromGlobalMapping(matcher.group());
            if (!company.isEmpty()) {
                companies.add(company);
            }
        }
        return companies;
    }

    public static Set<String> getCompaniesToCompleteGlobalInputMapping(WorksheetDTO worksheet, List<Integer> cellIndexes) {
        Set<String> companiesToComplete = new HashSet<String>();

        List<CellDTO> cells = (List<CellDTO>) worksheet.getCells();
        for (Integer cellIndex: cellIndexes) {
            CellDTO cell = cells.get(cellIndex);
            if (cell != null) {
                String company = getCompanyFromGlobalMapping(cell.getInputMapping());
                companiesToComplete.add(company);
            }
        }

        return companiesToComplete;
    }

    public static Set<String> getCompaniesToCompleteGlobalFormulaMapping(WorksheetDTO worksheet, List<Integer> cellIndexes) {
        Set<String> companiesToComplete = new HashSet<String>();
        List<CellDTO> cells = (List<CellDTO>) worksheet.getCells();
        for (Integer cellIndex: cellIndexes) {
            CellDTO cell = cells.get(cellIndex);
            Set<String> companies = getCompaniesFromGlobalFormulaMapping(((CellExtendedDTO) cell).getFormula());
            companiesToComplete.addAll(companies);
        }
        return companiesToComplete;
    }

    /**
     * Check if the given string contains valid input mapping string
     */
    public static boolean containsInputMapping(String expression) {
        return MappingFunction.containsAnyMappingFunction(expression);
    }

    public static String getHierarchyVisIdPropertyName(int dimIndex) {
        return WorkbookProperties.DIMENSION_$_HIERARCHY_VISID.toString().replace("$", String.valueOf(dimIndex));
    }

    public static List<String> getInputMappingsFromFormulaExpression(String formula) {
        List<String> inputMappings = new ArrayList<String>();
        String formulaMapping = formula;
        Pattern pattern = Pattern.compile(MAPPING_FUNCTIONS_PATTERN);
        Matcher matcher = pattern.matcher(formulaMapping);
        while (matcher.find()) {
            inputMappings.add(matcher.group());
        }

        return inputMappings;
    }

    public static boolean isNumeric(String string) {
        if (GeneralUtils.isEmptyOrNull(string)) {
            return false;
        }
        try {
            double d = Double.parseDouble(string);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }

    /**
     * Check if the given cell have Formula Input Mapping.
     * Formula Input Mapping = formula with Input Mappings.
     */
    public static boolean containsFormulaMapping(String formula) {
        if (GeneralUtils.isEmptyOrNull(formula)) {
            return false;
        }
        if (formula.startsWith("=") && containsInputMapping(formula)) {
            return true;
        }
        return false;
    }

    public static String[] validateCell(String mapping, boolean isGlobal) {
        if (mapping != null && !mapping.isEmpty()) {
            MappingValidator mv = null;
            if (isGlobal) {
                mv = new GlobalMappedMappingValidator(mapping);
            } else {
                mv = new MappingValidator(mapping);
            }
            return mv.getValidationErrors();
        }
        return new String[0];
    }

    /**
     * Manages fields depends on type (formula, text, number) stored in cell.getText().
     */
    public static void setFormattedValue(String value, CellExtendedDTO cellDTO) {
        if (value != null && !value.isEmpty() && !value.equals("null")) {
            if (isFormula(value)) {
                cellDTO.setFormula(value);
                cellDTO.setText(null);
            } else if (isDouble(value)) {
                cellDTO.setValue(Double.parseDouble(value));
                cellDTO.setText(null);
            } else if (isDate(value)) {
                cellDTO.setDate(parseToProperDateFormat(value));
                cellDTO.setText(null);
            } else {
                cellDTO.setText(value);
                cellDTO.setFormula(null);
            }
        }
    }

    public static Boolean isFormula(String value) {
        return value.startsWith("=");
    }

    public static Boolean isDouble(String value) {
        try {
            Double.parseDouble(value);
            return true;
        } catch (NumberFormatException e) {
        }
        return false;
    }

    public static Boolean isDate(String value) {
        return parseToProperDateFormat(value) != null;
    }

    public static String parseToProperDateFormat(String value) {
        DateFormat dateFormatSended = new SimpleDateFormat("dd/MM/yyyy");

        DateFormat dateFormat1 = new SimpleDateFormat("dd-MMM-yy");
        try {
            Date date = dateFormat1.parse(value);
            return dateFormatSended.format(date);
        } catch (ParseException e) {
        }

        DateFormat dateFormat2 = new SimpleDateFormat("MM/dd/yyyy");
        try {
            Date date = dateFormat2.parse(value);
            return dateFormatSended.format(date);
        } catch (ParseException e) {
        }

        return null;
    }

    public static String getKeyFromMapping(String mapping, String key) {
        String checkedMappingString = checkMapping(mapping, 0);

        // cut by comma
        if (!checkedMappingString.isEmpty()) {
            StringTokenizer stringTokenizer = new StringTokenizer(checkedMappingString, "(,)");
            stringTokenizer.nextToken();

            // found tokens
            while (stringTokenizer.hasMoreTokens()) {
                String dimToken = stringTokenizer.nextToken();
                // find key
                if (dimToken.startsWith(key)) {
                    // get value in quotes
                    StringTokenizer stringTokenizer2 = new StringTokenizer(dimToken, "\"");
                    stringTokenizer2.nextToken();
                    if (stringTokenizer2.hasMoreTokens())
                        return stringTokenizer2.nextToken();
                }
            }
        }
        return "";
    }

    public static String checkMapping(String mapping, int typeMapping) {
        if (mapping != null && (typeMapping >= 0) && (typeMapping <= 2)) {
            boolean isInput = false;
            boolean isOutput = false;

            // if special case, return without change
            if (mapping.startsWith("cedar.cp.financeCube(") || mapping.startsWith("cedar.cp.dim0Identifier()") || mapping.startsWith("cedar.cp.dim0Description()") || mapping.startsWith("cedar.cp.dim1Identifier()") || mapping.startsWith("cedar.cp.dim1Description()") || mapping.startsWith("cedar.cp.dim2Identifier()") || mapping.startsWith("cedar.cp.dim2Description()") || mapping.startsWith("cedar.cp.param(") || mapping.startsWith("cedar.cp.structures(") || mapping.startsWith("cedar.cp.getVisId(")
                    || mapping.startsWith("cedar.cp.getDescription(") || mapping.startsWith("cedar.cp.getLabel(") || mapping.startsWith("cedar.cp.formLink(")) {
                return mapping;
            }

            // find input mapping
            if ((typeMapping == 0) || (typeMapping == 2)) {
                Pattern patternInputMapping = Pattern.compile("^cedar\\.cp\\.(getCell|getGlob|cell|getBaseVal|getQuantity|getCumBaseVal|getCumQuantity|getCurrencyLookup|getParameterLookup|getAuctionLookup)\\(.*\\)$");
                Matcher matcher = patternInputMapping.matcher(mapping);
                isInput = matcher.find();
            }

            // find output mapping
            if ((typeMapping == 1) || (typeMapping == 2)) {
                Pattern patternInputMapping = Pattern.compile("^cedar\\.cp\\.(putCell|post)\\(.*\\)$");
                Matcher matcher = patternInputMapping.matcher(mapping);
                isOutput = matcher.find();
            }

            // fix special characters
            if (isInput || isOutput) {
                mapping = mapping.replaceAll("(\"|&quot;)", ""); // delete " or &quot;
                mapping = mapping.replaceAll(";", ","); // replace ; to ,
                mapping = mapping.replaceAll("=", "=\""); // add " after =
                mapping = mapping.replaceAll(",", "\","); // add " before ,
                mapping = mapping.replaceAll("\\)$", "\"\\)"); // add " before last )
                return mapping;
            }
            // no mapping
            return "";
        }
        // not valid
        return "";
    }
}
