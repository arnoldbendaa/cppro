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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.cedar.cp.api.base.EntityList;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.facades.ExtractDataDTO;
import com.cedar.cp.api.facades.ExtractDataDTOImpl;
import com.cedar.cp.util.flatform.model.workbook.WorkbookProperties;
import com.softproideas.util.validation.MappingArguments;
import com.softproideas.util.validation.MappingValidator;

public class CPFunctionDescriptionData extends CPFunctionsData {
    private Map<String, String> workbookProperties;
    // private String[] hierarchyVisIds;
    // private CPFunctionDao dataEntryDAO;
    // private String modelVisId;
    //
    // private List<ElementQueryDetails> getCellDescAddresses;
    // private String[] nullElementVisIds;
    // private int[] structureIds;

    ExtractDataDTOImpl extractDTO;

    // private int modelId;
    // private String financeCubeVisId;
    // private int financeCubeId;

    private Map<String, String> dummyResults;

    public CPFunctionDescriptionData(CPFunctionDao dataEntryDAO, String modelVisId, Map<String, String> workbookProperties, String[] hierarchyVisIds) {
        // this.dataEntryDAO = dataEntryDAO;
        // this.modelVisId = modelVisId;
        this.workbookProperties = workbookProperties;
        // this.hierarchyVisIds = hierarchyVisIds;
        dummyResults = new HashMap<String, String>();
    }

    @Override
    public void add(MappingValidator mv, String id) throws ValidationException {
        String visId = processGetVisIdsExpression(mv.getListOfArguments());
        if (visId != null) {
            dummyResults.put(id, visId);
        }
    }

    @Override
    public void add(String expression, String id) throws ValidationException {
        MappingValidator mv = null;
        mv = new MappingValidator(expression);
        add(mv, id);
    }

    @Override
    public Map<String, String> submit() throws ValidationException {
        return dummyResults;
    }

    // private ExtractDataDTO prepareCellQuerry(ExtractDataDTOImpl extractDTO) throws ValidationException {
    // extractDTO.setModelId(this.modelId);
    // extractDTO.setFinanceCubeId(this.financeCubeId);
    // extractDTO.setNumDims(this.nullElementVisIds.length);
    // extractDTO.setHierarchyIds(this.structureIds);
    // extractDTO.setHierarchyVisIds(this.hierarchyVisIds);
    // // if (this.mCompany != null) {
    // // extractDTO.setCompany(this.mCompany);
    // // }
    //
    // return dataEntryDAO.getExtractData(extractDTO);
    // }
    //
    // private Map<String, String> buildDescription(List<ElementQueryDetails> getCellDescAddresses, ExtractDataDTO extractDataDTO) {
    // Map<String, String> results = new HashMap<String, String>();
    // for (int i = 0; i < getCellDescAddresses.size(); ++i) {
    // ElementQueryDetails eqd = (ElementQueryDetails) getCellDescAddresses.get(i);
    // String hierarchyVisId = this.hierarchyVisIds[eqd.mDimensionIndex];
    // String hierarchyElementVisId = eqd.mVisualId;
    // String hierarchyElementDescription = extractDataDTO.getElementDescriptionLookup(hierarchyVisId, hierarchyElementVisId);
    // if (eqd.mLabelAndDescr) {
    // results.put(eqd.mLink, hierarchyElementVisId + ' ' + hierarchyElementDescription);
    // } else {
    // results.put(eqd.mLink, hierarchyElementDescription);
    // }
    // }
    // return results;
    // }
    //
    // private ElementQueryDetails prepareGetCellDimVisuals(Map<String, String> arguments) throws ValidationException {
    // String visId = null;
    // if (arguments.size() != 1) {
    // throw new ValidationException("Expected single parameter to getDescr() method found " + arguments.size());
    // } else {
    // ElementQueryDetails var11 = new ElementQueryDetails();
    // for (String s: arguments.keySet()) {
    // visId = arguments.get(s);
    // var11.dim = s;
    // }
    //
    // var11.mDimensionIndex = Character.getNumericValue(var11.getDimIndex());
    // var11.mVisualId = visId;
    // return var11;
    // }
    // }
    //
    // private String processGetLabelExpression(Map<String, String> arguments) throws Exception {
    // StringBuilder builder = new StringBuilder();
    // String visId = this.processGetVisIdsExpression(arguments);
    // String desc = this.processDescriptionExpression(arguments);
    // if (visId != null && desc != null) {
    // builder.append(visId).append("-").append(desc);
    // }
    //
    // return builder.toString();
    // }

    // private String processDescriptionExpression(Map<String, String> parsedArgMap) throws ValidationException {
    // String visId = processGetVisIdsExpression(parsedArgMap);
    // if (visId == null || visId.trim().isEmpty()) {
    // return null;
    // }
    // return visId;
    // }

    private String processGetVisIdsExpression(Map<String, String> parsedArgMap) throws ValidationException {
        if (parsedArgMap.containsKey(MappingArguments.DIM0.toString())) {
            String costCenterFromContext = workbookProperties.get(WorkbookProperties.DIMENSION_0_VISID.toString());
            String costCenter = fillCostCenterFromContextIfIsEmpty(parsedArgMap.get(MappingArguments.DIM0.toString()), costCenterFromContext);
            return costCenter;
        } else if (parsedArgMap.containsKey(MappingArguments.DIM1.toString())) {
            String expenseCodeFromContext = workbookProperties.get(WorkbookProperties.DIMENSION_1_VISID.toString());
            String expenseCode = fillExpenseCodeFromContextIfIsEmpty(parsedArgMap.get(MappingArguments.DIM1.toString()), expenseCodeFromContext);
            return expenseCode;
        } else if (parsedArgMap.containsKey(MappingArguments.DIM2.toString())) {
            String dim2visID = parsedArgMap.get(MappingArguments.DIM2.toString());
            String dim2Context = WorkbookProperties.DIMENSION_2_VISID.toString();
            String dateFromContext = workbookProperties.get(dim2Context);
            String[] date = DateUtil.fillDateFromContextIfIsEmpty(dim2visID, dateFromContext);
            if (date[0] == null) {
                throw new ValidationException("null p exception");
            } else if (date[1] == null || !date[1].contains("pen")) {
                date = DateUtil.calculateDate(1, 12, parsedArgMap, date);
            }
            return DateUtil.dateToString(date);
        }
        return null;
    }

    // private void lookupModelAndFinanceCube() throws ValidationException {
    // EntityList modelList = dataEntryDAO.getAllModelsWeb();
    // this.modelId = 0;
    //
    // for (int fcList = 0; fcList < modelList.getNumRows(); ++fcList) {
    // if (this.modelVisId.equals(modelList.getValueAt(fcList, "VisId"))) {
    // this.modelId = ((Integer) modelList.getValueAt(fcList, "ModelId")).intValue();
    // break;
    // }
    // }
    //
    // if (this.modelId < 1) {
    // throw new ValidationException("Unknown model \'" + this.modelVisId + "\'");
    // } else {
    // EntityList var4 = dataEntryDAO.getAllFinanceCubesWebForModel(this.modelId);
    // for (int i = 0; i < var4.getNumRows(); ++i) {
    // if (this.financeCubeVisId.equals(var4.getValueAt(i, "VisId"))) {
    // this.financeCubeId = ((Integer) var4.getValueAt(i, "FinanceCubeId")).intValue();
    // break;
    // }
    // }
    // if (this.financeCubeId < 1) {
    // throw new ValidationException("Unknown finance cube \'" + this.financeCubeVisId + "\'");
    // }
    // }
    // }
    //
    // private void processStructures(String[] structures) throws ValidationException {
    // if (this.modelId == 0) {
    // throw new ValidationException("Must call cedar.cp.financeCube(xxx) first");
    // } else {
    // EntityList modelDims = dataEntryDAO.getAllDimensionsForModel(this.modelId);
    // if (modelDims.getNumRows() != structures.length) {
    // throw new ValidationException("Incorrect number of hierarchy names");
    // } else {
    // this.structureIds = new int[structures.length];
    // this.nullElementVisIds = new String[structures.length];
    //
    // for (int i = 0; i < structures.length; ++i) {
    // int dimId = ((Integer) modelDims.getValueAt(i, "DimensionId")).intValue();
    // this.nullElementVisIds[i] = (String) modelDims.getValueAt(i, "col5");
    // EntityList hierList = dataEntryDAO.getHierarcyDetailsFromDimId(dimId);
    // boolean found = false;
    //
    // for (int h = 0; !found && h < hierList.getNumRows(); ++h) {
    // if (structures[i].equals(hierList.getValueAt(h, "VisId"))) {
    // this.structureIds[i] = ((Integer) hierList.getValueAt(h, "HierarchyId")).intValue();
    // found = true;
    // }
    // }
    // if (!found) {
    // throw new ValidationException("Unknown structure \'" + structures[i] + "\'");
    // }
    // }
    // }
    // }
    // }
}
