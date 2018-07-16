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
package com.softproideas.api.cpfunctionsevaluator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.cedar.cp.api.base.CPConnection;
import com.cedar.cp.api.base.EntityList;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.datatype.DataTypeRef;
import com.cedar.cp.util.GeneralUtils;
import com.cedar.cp.util.flatform.model.workbook.CellDTO;
import com.cedar.cp.util.flatform.model.workbook.CellExtendedDTO;
import com.cedar.cp.util.flatform.model.workbook.WorkbookDTO;
import com.cedar.cp.util.flatform.model.workbook.WorkbookProperties;
import com.cedar.cp.util.flatform.model.workbook.WorksheetDTO;
import com.google.gson.Gson;
import com.softproideas.util.validation.GlobalMappedMappingValidator;
import com.softproideas.util.validation.MappingValidator;

import cppro.utils.MyLog;

//import com.softproideas.commons.util.DimensionUtil;

public class FlatFormExtractorPrototype {

    public enum MappingType {
        MAPPING, REFERENCE, UNDEFINED, INVALID
    }

    public class Indexes {
        private int worksheetIndex;
        private int cellIndex;
        private String mapping;
        private int formulaIndex;

        public Indexes(int worksheetIndex, int cellIndex, int formulaIndex, String mapping) {
            super();
            this.worksheetIndex = worksheetIndex;
            this.cellIndex = cellIndex;
            this.mapping = mapping;
            this.formulaIndex = formulaIndex;
        }

        public Indexes(int worksheetIndex, int cellIndex, String mapping) {
            super();
            this.worksheetIndex = worksheetIndex;
            this.cellIndex = cellIndex;
            this.mapping = mapping;
            this.formulaIndex = -1;
        }

        public int getWorksheetIndex() {
            return worksheetIndex;
        }

        public int getCellIndex() {
            return cellIndex;
        }

        public String getMapping() {
            return mapping;
        }

        public void setMapping(String mapping) {
            this.mapping = mapping;
        }

        public int getFormulaIndex() {
            return formulaIndex;
        }

        public String toString() {
            return worksheetIndex + ":" + cellIndex + ":" + formulaIndex;
        }
    }

    private Set<String> numericDataTypes;

    // @Autowired
    public FlatFormExtractorPrototype(CPConnection connection) {
        numericDataTypes = new HashSet<String>();
        if (connection != null) {
            EntityList mDataTypes = connection.getDataTypesProcess().getAllDataTypes();
            if (mDataTypes != null && mDataTypes.getNumRows() > 0) {
                for (int i = 0; i < mDataTypes.getNumRows(); i++) {
                    DataTypeRef ref = (DataTypeRef) mDataTypes.getValueAt(i, "DataType");
                    String narrative = (String) ref.getNarrative();
                    Integer measureClass = (Integer) mDataTypes.getValueAt(i, "MeasureClass");
                    if (measureClass == null || measureClass != 0) {
                        numericDataTypes.add(narrative.toUpperCase());
                    }
                }
            }
        }
    }

    public void getValuesForMappings(WorkbookDTO workbook, CPConnection connection) throws Exception {
        long startTime = startLogTime();

        Map<MappingType, List<Indexes>> processingList;
        processingList = analyzeWorkbook(workbook);
        CPFunctionsEvaluator[] evaluators = null;
        // 1. extract workbook
        if (processingList.containsKey(MappingType.MAPPING)) {
            evaluators = extractMappingsByModelVisId(workbook, processingList.get(MappingType.MAPPING), connection);
        }

        HashMap<String, Object> results = new HashMap<String, Object>();
        for (CPFunctionsEvaluator e: evaluators) {
            HashMap<String, Object> temp = e.submitBatch();
            results.putAll(temp);
        }
        processResults(workbook, results, processingList.get(MappingType.MAPPING));

        results.clear();

        // 2. extract again workbook if have cell reference
        if (processingList.containsKey(MappingType.REFERENCE)) {
            fillSimpleMappingReferences(workbook, processingList.get(MappingType.REFERENCE));
            evaluators = extractMappingsByModelVisId(workbook, processingList.get(MappingType.REFERENCE), connection);
        }
        for (CPFunctionsEvaluator e: evaluators) {
            HashMap<String, Object> temp = e.submitBatch();
            results.putAll(temp);
        }
        processResults(workbook, results, processingList.get(MappingType.REFERENCE));
   	 	

        endLogTime(startTime);
    }

    private Map<MappingType, List<Indexes>> analyzeWorkbook(WorkbookDTO workbook) {
        System.out.println("Analizing workbook");
        long start = System.currentTimeMillis();
        Map<MappingType, List<Indexes>> processingList = new HashMap<MappingType, List<Indexes>>();
        for (MappingType m: MappingType.values()) {
            processingList.put(m, new ArrayList<Indexes>());
        }
        List<WorksheetDTO> worksheets = workbook.getWorksheets();
        int cellsCount = 0;
        for (int worksheetIndex = 0; worksheetIndex < worksheets.size(); worksheetIndex++) { // iterate worksheets
            WorksheetDTO worksheet = worksheets.get(worksheetIndex);
            cellsCount += worksheet.getCells().size();
            Map<MappingType, List<Indexes>> temp = analizeWorksheet(worksheet, worksheetIndex);
            for (MappingType type: temp.keySet()) {
                processingList.get(type).addAll(temp.get(type));
            }
        }
        long end = System.currentTimeMillis();
        double time = (end - start) / 1000.0;
        System.out.println("Analized " + worksheets.size() + " worksheets");
        System.out.println("Analized " + cellsCount + " cells");
        System.out.println("Workbook Anilized in " + time + " sec");
        return processingList;
    }

    private Map<MappingType, List<Indexes>> analizeWorksheet(WorksheetDTO worksheet, int worksheetIndex) {
        List<CellDTO> cells = (List<CellDTO>) worksheet.getCells();
        Map<MappingType, List<Indexes>> processingList = new HashMap<MappingType, List<Indexes>>();
        for (MappingType m: MappingType.values()) {
            processingList.put(m, new ArrayList<Indexes>());
        }
        for (int cellIndex = 0; cellIndex < cells.size(); cellIndex++) { // iterate cells
            if (cells != null) {
                CellExtendedDTO cell = (CellExtendedDTO) cells.get(cellIndex);
                if (cell == null) {
                    processingList.get(MappingType.UNDEFINED).add(new Indexes(worksheetIndex, cellIndex, null));
                    // return MappingType.UNDEFINED;
                } else if (cell.getInputMapping() != null && cell.getInputMapping().trim() != "") {
                    String inputMapping = cell.getInputMapping();
                    if (FlatFormExtractorUtil.containsCellReference(inputMapping)) {
                        processingList.get(MappingType.REFERENCE).add(new Indexes(worksheetIndex, cellIndex, inputMapping));
                        // return MappingType.REFERENCE;
                    } else {
                        processingList.get(MappingType.MAPPING).add(new Indexes(worksheetIndex, cellIndex, inputMapping));
                        // return MappingType.MAPPING;
                    }
                } else if (FlatFormExtractorUtil.containsFormulaMapping(cell.getFormula()) || FlatFormExtractorUtil.containsFormulaMapping(cell.getText())) {
                    if (FlatFormExtractorUtil.containsFormulaMapping(cell.getFormula()) == false && FlatFormExtractorUtil.containsFormulaMapping(cell.getText())) {
                        cell.setFormula(cell.getText());
                        cell.setText(null);
                    }
                    List<String> inputMappings = FlatFormExtractorUtil.getInputMappingsFromFormulaExpression(cell.getFormula());
                    for (int i = 0; i < inputMappings.size(); i++) {
                        if (FlatFormExtractorUtil.containsCellReference(cell.getFormula())) {// czy moze byc referencja poza mappingiem?
                            processingList.get(MappingType.REFERENCE).add(new Indexes(worksheetIndex, cellIndex, i, inputMappings.get(i)));
                        } else {
                            processingList.get(MappingType.MAPPING).add(new Indexes(worksheetIndex, cellIndex, i, inputMappings.get(i)));
                        }
                    }
                } else if (cell.getText() != null && cell.getText().trim().isEmpty() == false) {
                    FlatFormExtractorUtil.setFormattedValue(cell.getText(), cell);
                }
                processingList.get(MappingType.UNDEFINED).add(new Indexes(worksheetIndex, cellIndex, null));
            }
        }
        return processingList;
    }

    private CPFunctionsEvaluator[] extractMappingsByModelVisId(WorkbookDTO workbook, List<Indexes> processingList, CPConnection connection) throws Exception {
        Map<String, CPFunctionsEvaluator> evaluatorsForModels = new HashMap<String, CPFunctionsEvaluator>();
        for (Indexes indexes: processingList) {
            WorksheetDTO worksheet = workbook.getWorksheets().get(indexes.getWorksheetIndex());
            Map<String, String> worksheetProperties = worksheet.getProperties();
            String worksheetModelVisId = worksheetProperties.get(WorkbookProperties.MODEL_VISID.toString());
            // String worksheetModelVisId = getProperties(indexes, mv, workbook);
            if (worksheetModelVisId == null) {
                throw new ValidationException("No modelVisId set fot worksheet " + worksheet.getName());
            }

            String inputMapping = indexes.getMapping();
            MappingValidator mv = null;
            if (FlatFormExtractorUtil.isGlobal(worksheetModelVisId)) {
                mv = new GlobalMappedMappingValidator(inputMapping);
            } else {
                mv = new MappingValidator(inputMapping);
            }
            String[] errors = mv.getValidationErrors();
            if (errors.length > 0) {
                CellExtendedDTO cell = (CellExtendedDTO) worksheet.getCells().get(indexes.getCellIndex());
                workbook.setValid(false);
                worksheet.setValid(false);
                String[][] errmsgs = new String[2][];
                errmsgs[0] = errors;
                errmsgs[1] = new String[0];
                cell.setValidationMessages(errmsgs);
                if (cell.getInputMapping() != null && cell.getInputMapping().trim() != "") {
                    cell.setInputMapping(null);
                    cell.setText("#MAPPING!");
                } else {
                    cell.setFormula(null);
                    cell.setText("#FORMULA!");
                }
                continue;
            }
            String modelVisId = getModelVisId(indexes, mv, workbook);
            if (evaluatorsForModels.containsKey(modelVisId) == false) {
                Map<String, String> properties = getProperties(indexes, mv, workbook);
                CPFunctionsEvaluator evaluator = CPFunctionsEvaluatorFactory.getCPFunctionsEvaluator(connection);
                setProperties(properties, workbook.getProperties(), evaluator);
                evaluator.addBatchExpression(inputMapping, indexes.toString());
                evaluatorsForModels.put(modelVisId, evaluator);
            } else {
                evaluatorsForModels.get(modelVisId).addBatchExpression(inputMapping, indexes.toString());
            }
        }
        return evaluatorsForModels.values().toArray(new CPFunctionsEvaluator[0]);
    }

    protected String getModelVisId(Indexes indexes, MappingValidator mv, WorkbookDTO workbook) {
        return workbook.getWorksheets().get(indexes.getWorksheetIndex()).getProperties().get(WorkbookProperties.MODEL_VISID.toString());
    }

    protected Map<String, String> getProperties(Indexes indexes, MappingValidator mv, WorkbookDTO workbook) {
        return workbook.getWorksheets().get(indexes.getWorksheetIndex()).getProperties();
    }

    private void processResults(WorkbookDTO workbook, HashMap<String, Object> results, List<Indexes> indexes) {
        for (Indexes in: indexes) {
            int sheetIndex = in.getWorksheetIndex();
            int cellIndex = in.getCellIndex();
            int mappingIndex = in.getFormulaIndex();
            CellExtendedDTO cell = (CellExtendedDTO) workbook.getWorksheets().get(sheetIndex).getCells().get(cellIndex);
            String mapping = in.getMapping();
            if (FlatFormExtractorUtil.containsFormulaMapping(cell.getFormula())) {
                String formula = cell.getFormula();
                String value = null;
                if (results.containsKey(in.toString())) {
                    Object result = results.get(in.toString());
                    if (result != null) {
                        value = String.valueOf(result);
                        value = FlatFormExtractorUtil.invertValueIfNecessary(mapping, value, workbook, cell);
                    }
                }
                value = zeroValueIfNecessary(mapping, value, workbook);
                if (FlatFormExtractorUtil.isNumeric(value)) {
                    formula = formula.replace(mapping, value);
                } else {
                    System.out.println("Sheet : " + workbook.getWorksheets().get(sheetIndex).getName() + " ,  Cell: column " + cell.getColumn() + " , row " + cell.getRow());
                    System.out.println(formula);
                    formula = formula.replace(mapping, value);
                    System.out.println(formula);
                    // /formula = "=\"" + value + "\"";
                }
                // formula = formula.replace(mappingsSubmitted.get(s), value);
                FlatFormExtractorUtil.setFormattedValue(formula, cell);
            } else if (FlatFormExtractorUtil.containsInputMapping(cell.getInputMapping())) {
                if (results != null && results.containsKey(in.toString())) {
                    Object result = results.get(in.toString());
                    String value = null;
                    if (result != null) {
                        value = String.valueOf(result);
                        value = FlatFormExtractorUtil.invertValueIfNecessary(mapping, value, workbook, cell);
                        FlatFormExtractorUtil.setFormattedValue(value, cell);
                    } else {/* NO RESULT */
                    }
                } else {/* NO RESULTS */
                }
            }
        }
    }

    private String zeroValueIfNecessary(String inputMapping, String value, WorkbookDTO workbook) {
        if (GeneralUtils.isEmptyOrNull(value) || value.equals("null")) {
            // String dataType = DimensionUtil.getKeyFromMapping(inputMapping, "dt").toUpperCase();
            String dataType = FlatFormExtractorUtil.getKeyFromMapping(inputMapping, "dt").toUpperCase();
            if (GeneralUtils.isEmptyOrNull(dataType)) {
                dataType = workbook.getProperties().get(WorkbookProperties.DATA_TYPE.toString());
            }
            if (dataType != null && numericDataTypes.contains(dataType)) {
                value = "0";
            } else {
                value = "";
            }
        }
        return value;
    }

    private void fillSimpleMappingReferences(WorkbookDTO workbook, List<Indexes> indexes) {
        for (Indexes in: indexes) {
            WorksheetDTO worksheet = workbook.getWorksheets().get(in.getWorksheetIndex());
            String mapping = in.getMapping();
            mapping = FlatFormExtractorUtil.fillSimpleMappingReferences(mapping, worksheet, workbook);
            CellExtendedDTO cell = (CellExtendedDTO) worksheet.getCells().get(in.getCellIndex());
            if (cell.getInputMapping() != null && cell.getInputMapping().trim() != "") {
                String inputMapping = cell.getInputMapping();
                inputMapping = inputMapping.replace(in.getMapping(), mapping);
                cell.setInputMapping(inputMapping);
            } else {
                String formula = cell.getFormula();
                formula = formula.replace(in.getMapping(), mapping);
                cell.setFormula(formula);
            }
            in.setMapping(mapping);
        }
    }

    /**
     * Set modelVisId, financeCubeVisId nad hierarchyVisIds to Evaluator
     */
    private void setProperties(Map<String, String> worksheetProperties, Map<String, String> workbookProperties, CPFunctionsEvaluator evaluator) throws ValidationException {
        evaluator.setParameters(workbookProperties);
        String modelVisId = worksheetProperties != null ? worksheetProperties.get(WorkbookProperties.MODEL_VISID.toString()) : null;
        String financeCubeVisId = worksheetProperties != null ? worksheetProperties.get(WorkbookProperties.FINANCE_CUBE_VISID.toString()) : null;
        if ((modelVisId != null) && (financeCubeVisId != null)) {
            int dimensionCount = evaluator.setModelAndFinanceCube(modelVisId, financeCubeVisId);
            String[] hierarchyVisIds = new String[dimensionCount];
            for (int dimIndex = 0; dimIndex < dimensionCount; dimIndex++) {
                String visId = worksheetProperties.get(FlatFormExtractorUtil.getHierarchyVisIdPropertyName(dimIndex));
                if (visId == null) {
                    throw new ValidationException("Dimension " + dimIndex + " hierarchy vis id not defined");
                }
                hierarchyVisIds[dimIndex] = visId;
            }
            evaluator.setHierarchies(hierarchyVisIds);
        }
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
}