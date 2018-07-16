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
import java.util.List;
import java.util.Map;

import com.cedar.cp.api.base.EntityList;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.facades.ExtractDataDTO;
import com.cedar.cp.api.facades.ExtractDataDTOImpl;
import com.cedar.cp.util.flatform.model.workbook.WorkbookProperties;
import com.softproideas.util.validation.MappingArguments;
import com.softproideas.util.validation.MappingValidator;

public class CPFunctionsCellData extends CPFunctionsData {

    private List<String[]> cellAddresses = new ArrayList<String[]>();
    private List<String> cellLinks = new ArrayList<String>();
    private Map<String, String> workbookProperties;
    private String[] hierarchyVisIds;
    private CPFunctionDao dataEntryDAO;
    private String modelVisId;

    private String[] nullElementVisIds;
    private int[] structureIds;

    private int modelId;
    private String financeCubeVisId;
    private int financeCubeId;

    public CPFunctionsCellData(CPFunctionDao dataEntryDAO, String modelVisId, String financeCubeVisId, Map<String, String> workbookProperties, String[] hierarchyVisIds) {
        this.dataEntryDAO = dataEntryDAO;
        this.modelVisId = modelVisId;
        this.financeCubeVisId = financeCubeVisId;
        this.workbookProperties = workbookProperties;
        this.hierarchyVisIds = hierarchyVisIds;
    }

    @Override
    public void add(MappingValidator mv, String id) throws ValidationException {
        String[] prepareGetCellKey = prepareGetCellKey(mv);
        cellAddresses.add(prepareGetCellKey);
        cellLinks.add(id);
    }

    @Override
    public void add(String expression, String id) throws ValidationException {
        MappingValidator mv = null;
        mv = new MappingValidator(expression);
        add(mv, id);
    }

    @Override
    public Map<String, String> submit() throws ValidationException {
        Map<String, String> results = new HashMap<String, String>();
        if (cellAddresses.size() > 0) {
            lookupModelAndFinanceCube();
            processStructures(hierarchyVisIds);
            // log.info("cellAddresses", "start");
            int[] ranges = splitList(cellAddresses.size(), 1000);
            for (int i = 0; i < ranges.length - 1; i++) {
                List<String[]> temp = cellAddresses.subList(ranges[i], ranges[i + 1]);
                List<String> cellLinksPart = cellLinks.subList(ranges[i], ranges[i + 1]);
                ExtractDataDTOImpl DTO = new ExtractDataDTOImpl();
                DTO.setCellKeys(temp);
                DTO = (ExtractDataDTOImpl) prepareCellQuerry(DTO);
                EntityList cellValues = null;
                cellValues = DTO.getCellData();
                results.putAll(buildOAResult(cellValues, cellLinksPart));
            }
            // log.info("cellAddresses", "end");

        }
        return results;
    }

    private ExtractDataDTO prepareCellQuerry(ExtractDataDTOImpl extractDTO) throws ValidationException {
        extractDTO.setModelId(this.modelId);
        extractDTO.setFinanceCubeId(this.financeCubeId);
        extractDTO.setNumDims(this.nullElementVisIds.length);
        extractDTO.setHierarchyIds(this.structureIds);
        extractDTO.setHierarchyVisIds(this.hierarchyVisIds);
        // if (this.mCompany != null) {
        // extractDTO.setCompany(this.mCompany);
        // }

        return dataEntryDAO.getExtractData(extractDTO);
    }

    private Map<String, String> buildOAResult(EntityList cellValues, List<String> cellLinks) {
        Map<String, String> results = new HashMap<String, String>();
        Object val;
        for (int i = 0; cellValues != null && i < cellValues.getNumRows(); ++i) {
            int pIndex = ((Integer) cellValues.getValueAt(i, "P_INDEX")).intValue();
            val = cellValues.getValueAt(i, "VAL");
            if (val == null)
                val = "null";
            results.put(cellLinks.get(pIndex), val.toString());
        }

        return results;
    }

    private String[] prepareGetCellKey(MappingValidator mv) throws ValidationException {
        Map<String, String> parsedArgMap = mv.getListOfArguments();

        String dataType = parsedArgMap.get("dt");
        if (dataType == null) {
            dataType = workbookProperties.get(WorkbookProperties.DATA_TYPE.toString());
        }

        String valueType = parsedArgMap.get("type");
        if (valueType == null) {
            valueType = "M";
        }
        String costCenterFromContext = workbookProperties.get(WorkbookProperties.DIMENSION_0_VISID.toString());
        String costCenter = fillCostCenterFromContextIfIsEmpty(parsedArgMap.get(MappingArguments.DIM0.toString()), costCenterFromContext);

        String expenseCodeFromContext = workbookProperties.get(WorkbookProperties.DIMENSION_1_VISID.toString());
        String expenseCode = fillExpenseCodeFromContextIfIsEmpty(parsedArgMap.get(MappingArguments.DIM1.toString()), expenseCodeFromContext);

        String dim2Context = WorkbookProperties.DIMENSION_2_VISID.toString();
        String dateFromContext = workbookProperties.get(dim2Context);
        String[] date = DateUtil.fillDateFromContextIfIsEmpty(mv, dateFromContext);
        if (date[0] == null || costCenter == null || expenseCode == null || dataType == null) {
            throw new ValidationException("null p exception");
        }
        if (date[1] == null || !date[1].contains("pen")) {
            date = DateUtil.calculateDate(1, 12, parsedArgMap, date);
        }
        // create cell key (valueType,dim0,dim1,dim2,dataType)
        String[] cellKey = new String[5];
        cellKey[0] = valueType;
        cellKey[1] = costCenter;
        cellKey[2] = expenseCode;
        cellKey[3] = DateUtil.dateToString(date);
        cellKey[4] = dataType;

        return cellKey;
    }

    private void lookupModelAndFinanceCube() throws ValidationException {
        if(modelVisId == null){
            throw new ValidationException("No model vis. ID. defined");
        }
        EntityList modelList = dataEntryDAO.getAllModelsWeb();
        this.modelId = 0;

        for (int fcList = 0; fcList < modelList.getNumRows(); ++fcList) {
            if (this.modelVisId.equals(modelList.getValueAt(fcList, "VisId"))) {
                this.modelId = ((Integer) modelList.getValueAt(fcList, "ModelId")).intValue();
                break;
            }
        }

        if (this.modelId < 1) {
            throw new ValidationException("Unknown model \'" + this.modelVisId + "\'");
        } else {
            EntityList var4 = dataEntryDAO.getAllFinanceCubesWebForModel(this.modelId);
            for (int i = 0; i < var4.getNumRows(); ++i) {
                if (this.financeCubeVisId.equals(var4.getValueAt(i, "VisId"))) {
                    this.financeCubeId = ((Integer) var4.getValueAt(i, "FinanceCubeId")).intValue();
                    break;
                }
            }
            if (this.financeCubeId < 1) {
                throw new ValidationException("Unknown finance cube \'" + this.financeCubeVisId + "\'");
            }
        }
    }

    private void processStructures(String[] structures) throws ValidationException {
        if (structures == null) {
            throw new ValidationException("No hierarchies in worksheets are set for model vis. ID. " + modelVisId);
        }
        EntityList modelDims = dataEntryDAO.getAllDimensionsForModel(this.modelId);
        if (modelDims.getNumRows() != structures.length) {
            throw new ValidationException("Incorrect number of hierarchy names");
        } else {
            this.structureIds = new int[structures.length];
            this.nullElementVisIds = new String[structures.length];

            for (int i = 0; i < structures.length; ++i) {
                int dimId = ((Integer) modelDims.getValueAt(i, "DimensionId")).intValue();
                this.nullElementVisIds[i] = (String) modelDims.getValueAt(i, "col5");
                EntityList hierList = dataEntryDAO.getHierarcyDetailsFromDimId(dimId);
                boolean found = false;

                for (int h = 0; !found && h < hierList.getNumRows(); ++h) {
                    if (structures[i].equals(hierList.getValueAt(h, "VisId"))) {
                        this.structureIds[i] = ((Integer) hierList.getValueAt(h, "HierarchyId")).intValue();
                        found = true;
                    }
                }
                if (!found) {
                    throw new ValidationException("Unknown structure \'" + structures[i] + "\'");
                }
            }
        }
    }
}
