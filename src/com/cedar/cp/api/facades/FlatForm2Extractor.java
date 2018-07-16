package com.cedar.cp.api.facades;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.poi.hssf.util.CellReference;

import com.cedar.cp.api.base.CPConnection;
import com.cedar.cp.api.base.EntityList;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.datatype.DataTypeRef;
import com.cedar.cp.util.GeneralUtils;
import com.cedar.cp.util.flatform.model.Cell;
import com.cedar.cp.util.flatform.model.Workbook;
import com.cedar.cp.util.flatform.model.Worksheet;
import com.cedar.cp.util.flatform.model.workbook.WorkbookProperties;
import com.cedar.cp.util.sparse.LinkedListSparse2DArray;
import com.cedar.cp.util.sparse.LinkedListSparse2DArray.CellLink;
import com.cedar.cp.util.spreadsheet.DimensionUtil;
import com.softproideas.util.validation.GlobalMappedMappingValidator;
import com.softproideas.util.validation.MappingArguments;
import com.softproideas.util.validation.MappingFunction;
import com.softproideas.util.validation.MappingValidator;

/**
 * Class responsible for get and assignment of values ​​to cells in <code>Workbook</code>.
 * 
 * @author Jarosław Kaczmarski
 * @email jarok@softproideas.com
 * 2014-2015 All rights reserved to Softpro Ideas Group
 */
public class FlatForm2Extractor {

    private CPFunctionsEvaluator mEvaluator;
    private Workbook mWorkbook;
    private boolean mInvertNumber;
    private String[] mExcludedDataTypes;
    private EntityList mDataTypes;

    public static final String MAPPING_FUNCTIONS_PATTERN = MappingFunction.getFullMappingsPattern();

    private enum MappingType {
        SIMPLE_MAPPINGS, SIMPLE_FORMULA_MAPPINGS, 
        SIMPLE_MAPPINGS_WITH_CELL_REFERENCE, 
        SIMPLE_FORMULA_MAPPINGS_WITH_CELL_REFERENCE, 
        GLOBAL_MAPPINGS, GLOBAL_FORMULA_MAPPINGS, 
        GLOBAL_MAPPINGS_WITH_CELL_REFERENCE, 
        GLOBAL_FORMULA_MAPPINGS_WITH_CELL_REFERENCE
    }

    public FlatForm2Extractor(CPConnection connection, Workbook workbook) {
        mWorkbook = workbook;
        mEvaluator = new CPFunctionsEvaluatorImpl(connection);
        setInvertNumberProperties();
        this.mDataTypes = connection.getDataTypesProcess().getAllDataTypes();
    }

    public void extract(String costCentre, Map<String, String> params) throws Exception {
        long startTime = startLogTime();

        mEvaluator.setCostCenter(costCentre);
        mEvaluator.setParameters(params);

        Map<MappingType, Boolean> analysis = analyzeWorkbook();

        // 1. extract workbook
        if (analysis.get(MappingType.SIMPLE_MAPPINGS)) {
            extractSimpleMappings();
        }
        if (analysis.get(MappingType.SIMPLE_FORMULA_MAPPINGS)) {
            extractSimpleFormulaMappings();
        }

        // 2. extract again workbook if have cell reference
        if (analysis.get(MappingType.SIMPLE_MAPPINGS_WITH_CELL_REFERENCE) || analysis.get(MappingType.SIMPLE_FORMULA_MAPPINGS_WITH_CELL_REFERENCE)) {
            fillSimpleMappingReferences();
            if (analysis.get(MappingType.SIMPLE_MAPPINGS_WITH_CELL_REFERENCE)) {
                extractSimpleMappings();
            }
            if (analysis.get(MappingType.SIMPLE_FORMULA_MAPPINGS_WITH_CELL_REFERENCE)) {
                extractSimpleFormulaMappings();
            }
        }

        // 3. extract workbook (global)
        if (analysis.get(MappingType.GLOBAL_MAPPINGS)) {
            extractGlobalMappings();
        }
        if (analysis.get(MappingType.GLOBAL_FORMULA_MAPPINGS)) {
            extractGlobalFormulaMappings();
        }

        // 4. extract again workbook (global) if have cell reference
        if (analysis.get(MappingType.GLOBAL_MAPPINGS_WITH_CELL_REFERENCE) || analysis.get(MappingType.GLOBAL_FORMULA_MAPPINGS_WITH_CELL_REFERENCE)) {
            fillGlobalMappingReferences();
            if (analysis.get(MappingType.GLOBAL_MAPPINGS_WITH_CELL_REFERENCE)) {
                extractGlobalMappings();
            }
            if (analysis.get(MappingType.GLOBAL_FORMULA_MAPPINGS_WITH_CELL_REFERENCE)) {
                extractGlobalFormulaMappings();
            }
        }

        endLogTime(startTime);
    }

    private Map<MappingType, Boolean> analyzeWorkbook() {
        Map<MappingType, Boolean> analysis = new HashMap<MappingType, Boolean>();

        analysis.put(MappingType.SIMPLE_MAPPINGS, false);
        analysis.put(MappingType.SIMPLE_FORMULA_MAPPINGS, false);
        analysis.put(MappingType.SIMPLE_MAPPINGS_WITH_CELL_REFERENCE, false);
        analysis.put(MappingType.SIMPLE_FORMULA_MAPPINGS_WITH_CELL_REFERENCE, false);

        analysis.put(MappingType.GLOBAL_MAPPINGS, false);
        analysis.put(MappingType.GLOBAL_FORMULA_MAPPINGS, false);
        analysis.put(MappingType.GLOBAL_MAPPINGS_WITH_CELL_REFERENCE, false);
        analysis.put(MappingType.GLOBAL_FORMULA_MAPPINGS_WITH_CELL_REFERENCE, false);

        for (Worksheet worksheet: mWorkbook.getWorksheets()) { // iterate worksheets
            boolean isGlobal = false;
            String modelVisId = worksheet.getProperties().get(WorkbookProperties.MODEL_VISID.toString());
            if (modelVisId.toUpperCase().startsWith("GL")) {
                isGlobal = true;
            }
            Iterator<LinkedListSparse2DArray.CellLink<Cell>> iter = worksheet.iterator();
            while (iter.hasNext()) { // iterate cells
                LinkedListSparse2DArray.CellLink<Cell> link = iter.next();
                Cell cell = link.getData();
                if (cell != null) {
                    if(cell.getInputMapping() != null && cell.getInputMapping().trim() != ""){
                        String inputMapping = cell.getInputMapping();
                        String[] errors = this.validateCell(inputMapping, isGlobal);
                        if(errors.length > 0){
                            mWorkbook.setValid(false);
                            worksheet.setValid(false);
                            String[][] errmsgs = new String[2][];
                            errmsgs[0] = errors;
                            errmsgs[1] = new String[0];
                            cell.setValidationMessages(errmsgs);
                            cell.setInputMapping("");
                            cell.setText("#MAPPING!");
                            continue;
                        }
                        if (isGlobal) { // global
                            if (containsCellReference(inputMapping)) { // cell reference
                                analysis.put(MappingType.GLOBAL_MAPPINGS_WITH_CELL_REFERENCE, true);
                            } else {
                                analysis.put(MappingType.GLOBAL_MAPPINGS, true);
                            }
                            //validation here
                        } else {
                            if (containsCellReference(inputMapping)) {
                                analysis.put(MappingType.SIMPLE_MAPPINGS_WITH_CELL_REFERENCE, true);
                            } else {
                                analysis.put(MappingType.SIMPLE_MAPPINGS, true);
                            }
                            //and here
                        }
                    }
                    else if (containsFormulaMapping(cell)) {
                        String text = cell.getFormulaText();
                        List<String> inputMappings = getInputMappingsFromFormulaExpression(cell);
                        for(String mapping: inputMappings){
                            String[] errors = this.validateCell(mapping, isGlobal);
                            if(errors.length > 0){
                                mWorkbook.setValid(false);
                                worksheet.setValid(false);
                                String[][] errmsgs = new String[2][];
                                errmsgs[0] = errors;
                                errmsgs[1] = new String[0];
                                cell.setValidationMessages(errmsgs);
                                cell.setFormula(null);
                                cell.setFormulaText("#FORMULA!");
                                break;
                            }
                        }
                        if (isGlobal) {
                            if (containsCellReference(text)) {
                                analysis.put(MappingType.GLOBAL_FORMULA_MAPPINGS_WITH_CELL_REFERENCE, true);
                            } else {
                                analysis.put(MappingType.GLOBAL_FORMULA_MAPPINGS, true);
                            }
                        } else {
                            if (containsCellReference(text)) {
                                analysis.put(MappingType.SIMPLE_FORMULA_MAPPINGS_WITH_CELL_REFERENCE, true);
                            } else {
                                analysis.put(MappingType.SIMPLE_FORMULA_MAPPINGS, true);
                            }
                        }
                    }
                    
//                    if (containsInputMapping(cell)) {
//                        String inputMapping = cell.getInputMapping();
//                        if (containsCompany(inputMapping)) { // global
//                            if (containsCellReference(inputMapping)) { // cell reference
//                                analysis.put(MappingType.GLOBAL_MAPPINGS_WITH_CELL_REFERENCE, true);
//                            } else {
//                                analysis.put(MappingType.GLOBAL_MAPPINGS, true);
//                            }
//                        } else {
//                            if (containsCellReference(inputMapping)) {
//                                analysis.put(MappingType.SIMPLE_MAPPINGS_WITH_CELL_REFERENCE, true);
//                            } else {
//                                analysis.put(MappingType.SIMPLE_MAPPINGS, true);
//                            }
//                        }
//                    } else if (containsFormulaMapping(cell)) {
//                        String text = cell.getFormulaText();
//                        if (containsCompany(text)) {
//                            if (containsCellReference(text)) {
//                                analysis.put(MappingType.GLOBAL_FORMULA_MAPPINGS_WITH_CELL_REFERENCE, true);
//                            } else {
//                                analysis.put(MappingType.GLOBAL_FORMULA_MAPPINGS, true);
//                            }
//                        } else {
//                            if (containsCellReference(text)) {
//                                analysis.put(MappingType.SIMPLE_FORMULA_MAPPINGS_WITH_CELL_REFERENCE, true);
//                            } else {
//                                analysis.put(MappingType.SIMPLE_FORMULA_MAPPINGS, true);
//                            }
//                        }
//                    }
                }
            }
        }
        return analysis;
    }

    /*** Global Mappings ***/

    private void extractGlobalMappings() throws Exception {
        for (Worksheet worksheet: mWorkbook.getWorksheets()) {
            extractGlobalMappings(worksheet);
        }
    }
    private void extractGlobalMappings(Worksheet worksheet) throws Exception {
        setProperties(worksheet);
        List<String> companiesToComplete = getCompaniesToCompleteGlobalInputMapping(worksheet);
        for (String company: companiesToComplete) {
            mEvaluator.setCompany(company);
            addBatchGlobalExpressions(worksheet, company);
            submitBatchGlobalExpressions(worksheet, company);
        }
    }
    private void addBatchGlobalExpressions(Worksheet worksheet, String company) throws Exception {
        Iterator<CellLink<Cell>> iter = worksheet.iterator();
        while (iter.hasNext()) {
            LinkedListSparse2DArray.CellLink<Cell> link = (LinkedListSparse2DArray.CellLink<Cell>) iter.next();
            Cell cell = link.getData();
            if (cell != null) {
                if (cell.isToEvaluate() && containsInputMapping(cell) && !containsCellReference(cell.getInputMapping()) && containsCompany(cell.getInputMapping())) {
                    if (getCompanyFromMapping(cell.getInputMapping()).equals(company)) {
                        cell.setText("");
                        mEvaluator.addBatchExpression(cell.getInputMapping(), cell);
                        cell.setToEvaluate(false);
                    }
                }
            }
        }
    }
    private void submitBatchGlobalExpressions(Worksheet worksheet, String company) throws Exception {
        Map results = mEvaluator.submitBatch();
        if (results != null) {
            for (Object entry1: results.entrySet().toArray()) {
                Map.Entry entry = (Map.Entry) entry1;
                Cell cell = (Cell) entry.getKey();
                if (containsInputMapping(cell) && !containsCellReference(cell.getInputMapping()) && containsCompany(cell.getInputMapping())) { // TODO: czy potrzebne?
                    if (getCompanyFromMapping(cell.getInputMapping()).equals(company)) {
                        String value = String.valueOf(entry.getValue());
                        value = invertValueIfNecessary(cell, value);
                        cell.setPostValue(value);
                    }
                }
            }
        }
    }

    /*** Global Formula Mappings ***/

    private void extractGlobalFormulaMappings() throws Exception {
        for (Worksheet worksheet: mWorkbook.getWorksheets()) {
            extractGlobalFormulaMappings(worksheet);
        }
    }
    private void extractGlobalFormulaMappings(Worksheet worksheet) throws Exception {
        setProperties(worksheet);
        List<String> companiesToComplete = getCompaniesToCompleteGlobalFormulaMapping(worksheet);

        for (String company: companiesToComplete) {
            mEvaluator.setCompany(company);
            boolean isCompleted = false;
            while (!isCompleted) {
                isCompleted = addBatchGlobalFormulaExpressions(worksheet, company);
                submitBatchGlobalFormulaExpressions(worksheet, company);
            }
        }
    }
    private boolean addBatchGlobalFormulaExpressions(Worksheet worksheet, String company) throws Exception {
        boolean isCompleted = true;
        Iterator iter = worksheet.iterator();
        while (iter.hasNext()) {
            LinkedListSparse2DArray.CellLink link = (LinkedListSparse2DArray.CellLink) iter.next();
            Cell cell = (Cell) link.getData();
            if (cell != null) {
                if (cell.isToEvaluate() && containsFormulaMapping(cell) && !containsCellReference(cell.getFormulaText()) && containsCompany(cell.getFormulaText())) {
                    String inputMapping = getInputMappingFromFormulaExpression(cell);
                    if (!GeneralUtils.isEmptyOrNull(inputMapping)) {
                        if (getCompanyFromMapping(inputMapping).equals(company)) {
                            isCompleted = false;
                            mEvaluator.addBatchExpression(inputMapping, cell);
                        }
                    }
                }
            }
        }
        return isCompleted;
    }
    private void submitBatchGlobalFormulaExpressions(Worksheet worksheet, String company) throws Exception {
        Map results = mEvaluator.submitBatch();
        if (results != null) {
            if (results.size() > 0) {
                for (Object entry1: results.entrySet().toArray()) {
                    Map.Entry entry = (Map.Entry) entry1;
                    Cell cell = (Cell) entry.getKey();
                    if (containsFormulaMapping(cell) && !containsCellReference(cell.getCellText()) && containsCompany(cell.getCellText())) { // TODO: czy potrzebne?
                        String value = String.valueOf(entry.getValue());
                        value = invertValueIfNecessary(cell, value);
                        value = zeroValueIfNecessary(cell, value);
//                        if (isNumeric(value)) {
                            String formulaMapping = setValueToFormulaExpression(value, cell);
                            cell.setFormulaText(formulaMapping);
//                        }
                        
//                        else {
//                            
//                            if (GeneralUtils.isEmptyOrNull(value)) {
//                                cell.setFormulaText(null);
//                            } else {
//                                cell.setFormulaText("=\"" + value + "\"");
//                            }
//                        }
                        if (!containsFormulaMapping(cell) && !containsCellReference(cell.getCellText())) {
                            cell.setToEvaluate(false);
                        }
                    }
                }
            } else {
                Iterator iter = worksheet.iterator();
                while (iter.hasNext()) {
                    LinkedListSparse2DArray.CellLink link = (LinkedListSparse2DArray.CellLink) iter.next();
                    Cell cell = (Cell) link.getData();
                    if (cell != null) {
                        if (cell.isToEvaluate() && containsFormulaMapping(cell) && !containsCellReference(cell.getCellText()) && containsCompany(cell.getCellText())) {
                            String inputMapping = getInputMappingFromFormulaExpression(cell);
                            if (getCompanyFromMapping(inputMapping).equals(company)) {
                                String value = null;
                                value = zeroValueIfNecessary(cell, value);
//                                if (isNumeric(value)) {
                                    String formulaMapping = setValueToFormulaExpression(value, cell);
                                    cell.setFormulaText(formulaMapping);
//                                } else {
//                                    if (GeneralUtils.isEmptyOrNull(value)) {
//                                        cell.setFormulaText(null);
//                                    } else {
//                                        cell.setFormulaText("=\"" + value + "\"");
//                                    }
//                                }
                                if (!containsFormulaMapping(cell) && !containsCellReference(cell.getCellText())) {
                                    cell.setToEvaluate(false);
                                }
                            }
                        }
                    }
                }
            }
        }
    }
    
    /*** Simple Mappings ***/

    private void extractSimpleMappings() throws Exception {
        for (Worksheet worksheet: mWorkbook.getWorksheets()) {
            extractSimpleMappings(worksheet);
        }
    }
    private void extractSimpleMappings(Worksheet worksheet) throws Exception {
        setProperties(worksheet);
        addBatchSimpleExpressions(worksheet);
        submitBatchSimpleExpressions();
    }
    private void addBatchSimpleExpressions(Worksheet worksheet) throws Exception {
        Iterator iter = worksheet.iterator();
        while (iter.hasNext()) {
            LinkedListSparse2DArray.CellLink link = (LinkedListSparse2DArray.CellLink) iter.next();
            Cell cell = (Cell) link.getData();
            if (cell != null) {
                if (cell.isToEvaluate() && containsInputMapping(cell) && !containsCellReference(cell.getInputMapping()) && !containsCompany(cell.getInputMapping())) {
                    cell.setText("");
                    mEvaluator.addBatchExpression(cell.getInputMapping(), cell);
                    cell.setToEvaluate(false);
                }
            }
        }
    }
    private void submitBatchSimpleExpressions() throws Exception {
        Map results = mEvaluator.submitBatch();
        if (results != null) {
            for (Object entry1: results.entrySet().toArray()) {
                Map.Entry entry = (Map.Entry) entry1;
                Cell cell = (Cell) entry.getKey();

                if (containsInputMapping(cell) && !containsCellReference(cell.getInputMapping()) && !containsCompany(cell.getInputMapping())) { // TODO: czy potrzebne?
                    String value = String.valueOf(entry.getValue());
                    value = invertValueIfNecessary(cell, value);
                    cell.setPostValue(value);
                }
            }
        }
    }

    /*** Simple Formula Mappings ***/
    
    private void extractSimpleFormulaMappings() throws Exception {
        for (Worksheet worksheet: mWorkbook.getWorksheets()) {
            extractSimpleFormulaMappings(worksheet);
        }
    }
    private void extractSimpleFormulaMappings(Worksheet worksheet) throws Exception {
        setProperties(worksheet);
        boolean isCompleted = false;
        while (!isCompleted) {
            isCompleted = addBatchSimpleFormulaExpressions(worksheet);
            submitBatchSimpleFormulaExpressions(worksheet);
        }
    }
    private boolean addBatchSimpleFormulaExpressions(Worksheet worksheet) throws Exception {
        boolean isCompleted = true;
        Iterator iter = worksheet.iterator();
        while (iter.hasNext()) {
            LinkedListSparse2DArray.CellLink link = (LinkedListSparse2DArray.CellLink) iter.next();
            Cell cell = (Cell) link.getData();
            if (cell != null) {
                if (cell.isToEvaluate() && containsFormulaMapping(cell) && !containsCellReference(cell.getFormulaText()) && !containsCompany(cell.getFormulaText())) {
                    String inputMapping = getInputMappingFromFormulaExpression(cell);
                    if (!GeneralUtils.isEmptyOrNull(inputMapping)) {
                        isCompleted = false;
                        mEvaluator.addBatchExpression(inputMapping, cell);
                    }
                }
            }
        }
        return isCompleted;
    }
    private void submitBatchSimpleFormulaExpressions(Worksheet worksheet) throws Exception {
        Map results = mEvaluator.submitBatch();
        if (results != null) {
            if (results.size() > 0) {
                for (Object entry1: results.entrySet().toArray()) {
                    Map.Entry entry = (Map.Entry) entry1;
                    Cell cell = (Cell) entry.getKey();

                    if (containsFormulaMapping(cell) && !containsCellReference(cell.getFormulaText()) && !containsCompany(cell.getFormulaText())) { // TODO: czy potrzebne?
                        // set input mapping, because invertVauleIfNecessary operate on IM
                        String inputMapping = getInputMappingFromFormulaExpression(cell);
                        cell.setInputMapping(inputMapping);
                        
                        String value = String.valueOf(entry.getValue());
                        value = invertValueIfNecessary(cell, value);
                        value = zeroValueIfNecessary(cell, value);
//                        if (isNumeric(value)) {
                            String formulaMapping = setValueToFormulaExpression(value, cell);
                            cell.setFormulaText(formulaMapping);
//                        } else {
//                            if (GeneralUtils.isEmptyOrNull(value)) {
//                                cell.setFormulaText(null);
//                            } else {
//                                cell.setFormulaText("=\"" + value + "\"");
//                            }
//                        }
                        if (!containsFormulaMapping(cell) && !containsCellReference(cell.getFormulaText())) {
                            cell.setToEvaluate(false);
                        }
                        // clear input mapping
                        cell.setInputMapping(null);
                    }
                }
            } else {
                Iterator iter = worksheet.iterator();
                while (iter.hasNext()) {
                    LinkedListSparse2DArray.CellLink link = (LinkedListSparse2DArray.CellLink) iter.next();
                    Cell cell = (Cell) link.getData();
                    if (cell != null) {
                        if (cell.isToEvaluate() && containsFormulaMapping(cell) && !containsCellReference(cell.getFormulaText()) && !containsCompany(cell.getFormulaText())) {
                            String inputMapping = getInputMappingFromFormulaExpression(cell);
                            cell.setInputMapping(inputMapping);
                            String value = null;
                            value = zeroValueIfNecessary(cell, value);
//                            if (isNumeric(value)) {
                                String formulaMapping = setValueToFormulaExpression(value, cell);
                                cell.setFormulaText(formulaMapping);
//                            } else {
//                                if (GeneralUtils.isEmptyOrNull(value)) {
//                                    cell.setFormulaText(null);
//                                } else {
//                                    cell.setFormulaText("=\"" + value + "\"");
//                                }
//                            }
                            if (!containsFormulaMapping(cell) && !containsCellReference(cell.getFormulaText())) {
                                cell.setToEvaluate(false);
                            }
                            cell.setInputMapping(null);
                        }
                    }
                }
            }
        }
    }

    /*** Fill Simple Mappings ***/

    private boolean fillSimpleMappingReferences() {
        boolean haveCellReference = false;
        for (Worksheet worksheet: mWorkbook.getWorksheets()) {
            Iterator iter = worksheet.iterator();
            while (iter.hasNext()) {
                LinkedListSparse2DArray.CellLink link = (LinkedListSparse2DArray.CellLink) iter.next();
                Cell cell = (Cell) link.getData();
                if (cell != null) {
                    if (fillSimpleMappingReferences(cell, worksheet)) {
                        haveCellReference = true;
                    }
                }
            }
        }
        return haveCellReference;
    }
    private boolean fillSimpleMappingReferences(Cell cell, Worksheet worksheet) {
        boolean haveCellReference = false;
        if (containsInputMapping(cell) && containsCellReference(cell.getInputMapping()) && !containsCompany(cell.getInputMapping())) {
            String inputMapping = cell.getInputMapping();
            String cellReference = getCellReference(inputMapping);
            if (!GeneralUtils.isEmptyOrNull(cellReference)) {
                haveCellReference = true;
                String cellReferenceValue = findValueForCellReference(cellReference, worksheet);
                inputMapping = replaceCellReferenceWithValue(inputMapping, cellReferenceValue);
                cell.setInputMapping(inputMapping);
            }
        }
        if (containsFormulaMapping(cell) && containsCellReference(cell.getFormulaText()) && !containsCompany(cell.getFormulaText())) {
            String text = cell.getFormulaText();
            String cellReference = getCellReference(text);
            if (!GeneralUtils.isEmptyOrNull(cellReference)) {
                haveCellReference = true;
                String cellReferenceValue = findValueForCellReference(cellReference, worksheet);
                text = replaceCellReferenceWithValue(text, cellReferenceValue);
                cell.setText(text);
            }
        }
        return haveCellReference;
    }

    /*** Fill Global Mappings ***/

    private boolean fillGlobalMappingReferences() {
        boolean haveCellReference = false;
        for (Worksheet worksheet: mWorkbook.getWorksheets()) {
            Iterator iter = worksheet.iterator();
            while (iter.hasNext()) {
                LinkedListSparse2DArray.CellLink link = (LinkedListSparse2DArray.CellLink) iter.next();
                Cell cell = (Cell) link.getData();
                if (cell != null) {
                    if (fillGlobalMappingReferences(cell, worksheet)) {
                        haveCellReference = true;
                    }
                }
            }
        }
        return haveCellReference;
    }
    private boolean fillGlobalMappingReferences(Cell cell, Worksheet worksheet) {
        boolean haveCellReference = false;
        if (containsInputMapping(cell) && containsCellReference(cell.getInputMapping()) && containsCompany(cell.getInputMapping())) {
            String inputMapping = cell.getInputMapping();
            String cellReference = getCellReference(inputMapping);
            if (!GeneralUtils.isEmptyOrNull(cellReference)) {
                haveCellReference = true;
                String cellReferenceValue = findValueForCellReference(cellReference, worksheet);
                inputMapping = replaceCellReferenceWithValue(inputMapping, cellReferenceValue);
                cell.setInputMapping(inputMapping);
            }
        }
        if (containsFormulaMapping(cell) && containsCellReference(cell.getFormulaText()) && containsCompany(cell.getFormulaText())) {
            String text = cell.getFormulaText();
            String cellReference = getCellReference(text);
            if (!GeneralUtils.isEmptyOrNull(cellReference)) {
                haveCellReference = true;
                String cellReferenceValue = findValueForCellReference(cellReference, worksheet);
                text = replaceCellReferenceWithValue(text, cellReferenceValue);
                cell.setText(text);
            }
        }
        return haveCellReference;
    }

    /*** Others ***/

    private String replaceCellReferenceWithValue(String mapping, String cellReferenceValue) {
        if (cellReferenceValue == null) {
            cellReferenceValue = "";
        }

        // #Sheet1#A#1
        Pattern pattern = Pattern.compile("#[^#]*#[ABCDEFGHIJKLMNOPQRSTUVWXYZ]*#[0123456789]*");
        Matcher matcher = pattern.matcher(mapping);
        while (matcher.find()) {
            mapping = mapping.replace(matcher.group(), cellReferenceValue);
        }
        // #A#1
        pattern = Pattern.compile("#[ABCDEFGHIJKLMNOPQRSTUVWXYZ]*#[0123456789]*");
        matcher = pattern.matcher(mapping);
        while (matcher.find()) {
            mapping = mapping.replace(matcher.group(), cellReferenceValue);
        }
        return mapping;
    }

    private String findValueForCellReference(String cellReference, Worksheet worksheet) {
        String value = "";

        String[] tempTab = cellReference.split("#");
        if (tempTab.length == 4) { // #Sheet1#A#1
            String sheetName = tempTab[1];
            String cellAddress = tempTab[2] + tempTab[3];
            CellReference cellRef = new CellReference(cellAddress);
            int rowNum = cellRef.getRow();
            int colNum = cellRef.getCol();

            for (Worksheet worksheet1: mWorkbook.getWorksheets()) {
                if (worksheet1.getName().equals(sheetName)) {
                    Cell cell = worksheet1.get(rowNum, colNum);
                    value = cell.getText();
                }
            }

        } else if (tempTab.length == 3) { // #A#1
            String cellAddress = tempTab[1] + tempTab[2];
            CellReference cellRef = new CellReference(cellAddress);
            int rowNum = cellRef.getRow();
            int colNum = cellRef.getCol();
            Cell cell = worksheet.get(rowNum, colNum);
            value = cell.getText();
        }
        return value;
    }

    private String setValueToFormulaExpression(String value, Cell cell) {
        String formulaMapping = cell.getFormulaText();

        Pattern pattern = Pattern.compile(MAPPING_FUNCTIONS_PATTERN);
        Matcher matcher = pattern.matcher(formulaMapping);
        while (matcher.find()) {
            formulaMapping = formulaMapping.replace(matcher.group(), value);
            break;
        }
        return formulaMapping;
    }

    private String zeroValueIfNecessary(Cell cell, String value) {
        if (GeneralUtils.isEmptyOrNull(value) || value.equals("null")) {
            String inputMapping = cell.getInputMapping();
            if (GeneralUtils.isEmptyOrNull(inputMapping)) {
                inputMapping = getInputMappingFromFormulaExpression(cell);
            }
            String dataType = DimensionUtil.getKeyFromMapping(inputMapping, "dt").toLowerCase();
            if (!GeneralUtils.isEmptyOrNull(dataType) && !isStringDataType(dataType)) {
                value = "0";
            }
        }
        return value;
    }

    /**
     * Set modelVisId, financeCubeVisId nad hierarchyVisIds to Evaluator
     */
    private void setProperties(Worksheet worksheet) throws ValidationException {
        String modelVisId = worksheet.getProperties() != null ? (String) worksheet.getProperties().get(WorkbookProperties.MODEL_VISID.toString()) : null;
        String financeCubeVisId = worksheet.getProperties() != null ? (String) worksheet.getProperties().get(WorkbookProperties.FINANCE_CUBE_VISID.toString()) : null;
        if ((modelVisId != null) && (financeCubeVisId != null)) {
            int dimensionCount = mEvaluator.setModelAndFinanceCube(modelVisId, financeCubeVisId);
            String[] hierarchyVisIds = new String[dimensionCount];
            for (int dimIndex = 0; dimIndex < dimensionCount; dimIndex++) {
                String visId = (String) worksheet.getProperties().get(getHierarchyVisIdPropertyName(dimIndex));
                if (visId == null) {
                    throw new ValidationException("Dimension " + dimIndex + " hierarchy vis id not defined");
                }
                hierarchyVisIds[dimIndex] = visId;
            }
            mEvaluator.setHierarchies(hierarchyVisIds);
        }
    }

    private String invertValueIfNecessary(Cell cell, String value) {
        // skip if null or empty
        if (GeneralUtils.isEmptyOrNull(value)) {
            return value;
        }
        // skip if "null" or 0
        if (value.equals("null") || value.equals("0")) {
            return value;
        }

        String inputMapping = cell.getInputMapping();
        if (GeneralUtils.isEmptyOrNull(inputMapping)) {
            inputMapping = getInputMappingFromFormulaExpression(cell);
        }
        String dataType = DimensionUtil.getKeyFromMapping(inputMapping, "dt").toLowerCase();
        // skip if input mapping without data type ("dt")
        if (GeneralUtils.isEmptyOrNull(dataType)) {
            return value;
        }

        if (mInvertNumber && !isExcludeDataType(dataType)) {
            try {
                Double d = Double.parseDouble(value);
                d = d * (-1);
                value = d.toString();
                if (value.substring(value.length() - 2).equals(".0")) {
                    value = value.substring(0, value.length() - 2);
                }
                cell.setInvertedValue(true);
            } catch (NumberFormatException e) {
                // do nothing!
            }
        }

        return value;
    }

    private boolean isExcludeDataType(String dataType) {
        boolean excludeDataType = false;

        if ((mExcludedDataTypes != null) && (mExcludedDataTypes.length > 0)) {
            for (int i = 0; i < mExcludedDataTypes.length; i++) {
                if (mExcludedDataTypes[i].toLowerCase().equals(dataType.toLowerCase())) {
                    excludeDataType = true;
                    break;
                }
            }
        }
        return excludeDataType;
    }

    /**
     * Return cell address from mapping string (ex: cedar.cp.getCell(dim0=#A#1) -> return #A#1)
     */
    private String getCellReference(String mapping) {
        String cellReference = "";

        // #Sheet1#A#1
        Pattern pattern = Pattern.compile("#[^#]*#[ABCDEFGHIJKLMNOPQRSTUVWXYZ]*#[0123456789]*");
        Matcher matcher = pattern.matcher(mapping);
        while (matcher.find()) {
            cellReference = matcher.group();
        }
        // #A#1
        pattern = Pattern.compile("#[ABCDEFGHIJKLMNOPQRSTUVWXYZ]*#[0123456789]*");
        matcher = pattern.matcher(mapping);
        while (matcher.find()) {
            cellReference = matcher.group();
        }

        return cellReference;
    }

    private boolean containsCellReference(String mapping) {
        if (GeneralUtils.isEmptyOrNull(mapping)) {
            return false;
        }
        // #Sheet1#A#1
        Pattern pattern = Pattern.compile("#[^#]*#[ABCDEFGHIJKLMNOPQRSTUVWXYZ]*#[0123456789]*");
        Matcher matcher = pattern.matcher(mapping);
        while (matcher.find()) {
            return true;
        }
        // #A#1
        pattern = Pattern.compile("#[ABCDEFGHIJKLMNOPQRSTUVWXYZ]*#[0123456789]*");
        matcher = pattern.matcher(mapping);
        while (matcher.find()) {
            return true;
        }
        return false;
    }

    private boolean containsCompany(String mapping) {
        if (GeneralUtils.isEmptyOrNull(mapping)) {
            return false;
        }
        if (mapping.contains("cedar.cp.getCell(")) {
            int positionStartCmpy = mapping.indexOf("cmpy=");
            if (positionStartCmpy != -1) {
                return true;
            }
            int positionStartCompany = mapping.indexOf("company=");
            if (positionStartCompany != -1) {
                return true;
            }
        }

        return false;
    }

    private String getCompanyFromMapping(String mapping) {
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
    
    private List<String> getCompaniesFromMapping(String mapping) {
        List<String> companies = new ArrayList<String>();

        Pattern pattern = Pattern.compile(MAPPING_FUNCTIONS_PATTERN);
        Matcher matcher = pattern.matcher(mapping);
        while (matcher.find()) {
            String company = getCompanyFromMapping(matcher.group());
            if (!company.isEmpty()) {
                companies.add(company);
            }
        }
        return companies;
    }

    private void setInvertNumberProperties() {
        mExcludedDataTypes = new String[0];
        mInvertNumber = false;

        if (mWorkbook.getProperties() != null) {
            for (String key: mWorkbook.getProperties().keySet()) {
                String property = mWorkbook.getProperty(key);
                if (key.equals(WorkbookProperties.EXCLUDE_DATA_TYPES.toString())) {
                    mExcludedDataTypes = property.split(",");
                } else if (key.equals(WorkbookProperties.INVERT_NUMBERS_VALUE.toString())) {
                    mInvertNumber = property.equals("true") ? true : false;
                }
            }
        }
    }

    /**
     * Check if the given string contains valid input mapping string
     */
    private boolean containsInputMapping(String string) {
       return MappingFunction.containsAnyMappingFunction(string);
    }

    private String getHierarchyVisIdPropertyName(int dimIndex) {
        return WorkbookProperties.DIMENSION_$_HIERARCHY_VISID.toString().replace("$", String.valueOf(dimIndex));
    }

    private String getInputMappingFromFormulaExpression(Cell cell) {
        List<String> inputMappings =getInputMappingsFromFormulaExpression(cell);
        return inputMappings.get(0);
    }

    private List<String> getInputMappingsFromFormulaExpression(Cell cell) {
        List<String> inputMappings = new ArrayList<String>();
        String formulaMapping = cell.getFormulaText();
        Pattern pattern = Pattern.compile(MAPPING_FUNCTIONS_PATTERN);
        Matcher matcher = pattern.matcher(formulaMapping);
        while (matcher.find()) {
            inputMappings.add(matcher.group());
        }

        return inputMappings;
    }
    
    private boolean isStringDataType(String dataType) {
        if (mDataTypes.getNumRows() > 0) {
            for (int i = 0; i < mDataTypes.getNumRows(); i++) {
                DataTypeRef ref = (DataTypeRef) mDataTypes.getValueAt(i, "DataType");
                String narrative = (String) ref.getNarrative();
                if (dataType.equalsIgnoreCase(narrative)) {
                    Integer measureClass = (Integer) mDataTypes.getValueAt(i, "MeasureClass");
                    if (measureClass != null && measureClass == 0) {
                        return true;
                    } else {
                        return false;
                    }
                }
            }
        }
        return false;
    }

    private boolean isNumeric(String string) {
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
     * Check is the given cell have Simple Input Mapping.
     * Simple Input Mapping = not Global Input Mapping.
     */
    private boolean containsInputMapping(Cell cell) {
        String inputMapping = cell.getInputMapping();
        if (GeneralUtils.isEmptyOrNull(inputMapping)) {
            return false;
        }
        if (containsInputMapping(inputMapping)) {
            return true;
        }
        return false;
    }

    /**
     * Check if the given cell have Formula Input Mapping.
     * Formula Input Mapping = formula with Input Mappings.
     */
    private boolean containsFormulaMapping(Cell cell) {
        String cellText = cell.getFormulaText();
        if (GeneralUtils.isEmptyOrNull(cellText)) {
            return false;
        }
        if (cellText.startsWith("=") && containsInputMapping(cellText)) {
            return true;
        }
        return false;
    }

    private List<String> getCompaniesToCompleteGlobalInputMapping(Worksheet worksheet) {
        List<String> companiesToComplete = new ArrayList<String>();

        Iterator iter = worksheet.iterator();
        while (iter.hasNext()) {
            LinkedListSparse2DArray.CellLink link = (LinkedListSparse2DArray.CellLink) iter.next();
            Cell cell = (Cell) link.getData();
            if (cell != null) {
                if (cell.isToEvaluate() && containsInputMapping(cell) && !containsCellReference(cell.getInputMapping()) && containsCompany(cell.getInputMapping())) {
                    String company = getCompanyFromMapping(cell.getInputMapping());
                    companiesToComplete.add(company);
                }
            }
        }

        return companiesToComplete;
    }
    private List<String> getCompaniesToCompleteGlobalFormulaMapping(Worksheet worksheet) {
        List<String> companiesToComplete = new ArrayList<String>();

        Iterator iter = worksheet.iterator();
        while (iter.hasNext()) {
            LinkedListSparse2DArray.CellLink link = (LinkedListSparse2DArray.CellLink) iter.next();
            Cell cell = (Cell) link.getData();
            if (cell != null) {
                if (cell.isToEvaluate() && containsFormulaMapping(cell) && !containsCellReference(cell.getFormulaText()) && containsCompany(cell.getFormulaText())) {
                    List<String> companies = getCompaniesFromMapping(cell.getFormulaText());
                    companiesToComplete.addAll(companies);
                }
            }
        }

        return companiesToComplete;
    }
    

    private long startLogTime() {
        long startTime = System.nanoTime();
        return startTime;
    }

    private void endLogTime(long startTime) {
        long endTime = startLogTime();
        long duration = endTime - startTime;
        double seconds = (double) duration / 1000000000.0;
        System.out.println("== Execution in " + seconds + " sec");
    }
    
    private String[] validateCell(String mapping, boolean isGlobal) {
        if (mapping != null && !mapping.isEmpty()) {
            MappingValidator mv = null;
            if (isGlobal) {
                mv = new GlobalMappedMappingValidator(mapping);
            } else {
                mv = new MappingValidator(mapping);
            }
            return mv.getValidationErrors();
        }
        else{
            return new String[0];
        }
    }

}