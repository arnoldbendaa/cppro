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
package com.softproideas.app.admin.dataeditor.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.poi.ss.usermodel.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cedar.cp.api.base.CPException;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.dto.dimension.AllDimensionElementsForModelELO;
import com.cedar.cp.dto.model.HierarchiesForModelELO;
import com.cedar.cp.ejb.impl.base.ListSEJB;
import com.cedar.cp.ejb.impl.dataeditor.DataEditorEditorSessionSEJB;
import com.softproideas.app.admin.dataeditor.mapper.DataEditorDimensionElementMapper;
import com.softproideas.app.admin.dataeditor.mapper.DataEditorMapper;
import com.softproideas.app.admin.dataeditor.model.DataEditorImportedData;
import com.softproideas.app.admin.dataeditor.model.DataEditorRow;
import com.softproideas.app.admin.dataeditor.model.DataEditorSearchOption;
import com.softproideas.app.admin.dataeditor.model.DimensionDataForModelDTO;
import com.softproideas.app.admin.dataeditor.util.DataEditorUtil;
import com.softproideas.app.admin.financecubes.services.FinanceCubesService;
import com.softproideas.app.admin.financecubes.util.FinanceCubesUtil;
import com.softproideas.app.core.dimensionElement.model.DimensionElementCoreDTO;
import com.softproideas.app.core.financecube.model.FinanceCubeModelCoreDTO;
import com.softproideas.common.exceptions.ServiceException;
import com.softproideas.commons.context.CPContextHolder;
import com.softproideas.commons.model.ResponseMessage;
import com.softproideas.commons.model.error.ValidationError;

@Service("dataEditorService")
public class DataEditorServiceImpl implements DataEditorService {

    private static Logger logger = LoggerFactory.getLogger(DataEditorServiceImpl.class);

    @Autowired
    CPContextHolder cpContextHolder;
    ListSEJB server = new ListSEJB();
    DataEditorEditorSessionSEJB editorEjb = new DataEditorEditorSessionSEJB();
    @Autowired
    FinanceCubesService financeCubesService;

    /*
     * (non-Javadoc)
     * @see com.softproideas.app.admin.dataeditor.service.DataEditorService#browseDimensionElements(java.util.List)
     */
    @Override
    public HashMap<Integer, DimensionDataForModelDTO> browseDimensionElements(List<Integer> modelIds) throws ServiceException {
//        AllDimensionElementsForModelELO elo = cpContextHolder.getListSessionServer().getAllDimensionElementsForModels(modelIds);
        AllDimensionElementsForModelELO elo = server.getAllDimensionElementsForModels(modelIds);

        return DataEditorDimensionElementMapper.mapAllDimensionElementsForModelELOToDTO(elo);
    }

    /*
     * (non-Javadoc)
     * @see com.softproideas.app.admin.dataeditor.service.DataEditorService#displayDataForSearchOption(com.softproideas.app.admin.dataeditor.model.DataEditorSearchOption)
     */
    @Override
    public List<DataEditorRow> displayDataForSearchOption(DataEditorSearchOption dataEditorSearchOption) throws ServiceException {
        List<FinanceCubeModelCoreDTO> financeCubes = financeCubesService.browseFinanceCubes();

        List<Integer> fcIds = dataEditorSearchOption.getFinanceCubeIds();

        List<String> costCentersTest = dataEditorSearchOption.getCostCenters();
        Object[] costCenters = costCentersTest.toArray(new String[costCentersTest.size()]);

        List<String> expenseCodesTest = dataEditorSearchOption.getExpenseCodes();
        Object[] expenseCodes = expenseCodesTest.toArray(new String[expenseCodesTest.size()]);

        List<String> dataTypes = dataEditorSearchOption.getDataTypes();

        int fromYear = dataEditorSearchOption.getFromYear();
        int fromPeriod = dataEditorSearchOption.getFromPeriod();
        int toYear = dataEditorSearchOption.getToYear();
        int toPeriod = dataEditorSearchOption.getToPeriod();

        List<Object[]> dataRows = null;
        try {
//            dataRows = cpContextHolder.getDataEditorEditorSessionServer().getDataEditorData(fcIds, costCenters, expenseCodes, dataTypes, fromYear, fromPeriod, toYear, toPeriod);
            dataRows = editorEjb.getDataEditorData(fcIds, costCenters, expenseCodes, dataTypes, fromYear, fromPeriod, toYear, toPeriod);
        } catch (CPException e) {
            logger.error(e.getMessage());
            throw new ServiceException(e.getMessage());
        }

        return DataEditorMapper.mapDataEditorDataToDataEditorRows(dataRows, financeCubes);
    }

    /*
     * (non-Javadoc)
     * @see com.softproideas.app.admin.dataeditor.service.DataEditorService#save(java.util.List)
     */
    @Override
    public ResponseMessage save(List<DataEditorRow> editedRows) throws ServiceException {
        ResponseMessage message = null;

        HashMap<Integer, List<DataEditorRow>> map = DataEditorUtil.divideRowsByModel(editedRows);
        String xml = "";

        for (Integer key: map.keySet()) {
            List<DataEditorRow> rows = map.get(key);
            HierarchiesForModelELO hierarchies = cpContextHolder.getListSessionServer().getHierarchiesForModel(key);
            xml = DataEditorUtil.generateXmlForSave(rows, hierarchies);
            message = new ResponseMessage(true);

            // System.out.println(xml);
            try {
                cpContextHolder.getDataEditorEditorSessionServer().saveData(xml, cpContextHolder.getUserId());
            } catch (CPException e) {
                String info = "Error during saving data from data editor - CPException!";
                logger.error(info);
                throw new ServiceException(info);
            } catch (ValidationException e) {
                logger.error("Error during saving data from data editor - ValidationException!");
                ValidationError error = new ValidationError(e.getMessage());
                return error;
            }
        }
        return message;
    }

    /*
     * (non-Javadoc)
     * @see com.softproideas.app.admin.dataeditor.service.DataEditorService#upload(org.apache.poi.ss.usermodel.Workbook)
     */
    @Override
    public DataEditorImportedData upload(Workbook workbook) throws ServiceException {
        DataEditorImportedData importedData = new DataEditorImportedData();

        List<DataEditorRow> rowsDTO = DataEditorMapper.mapWorkbookToDataEditorRows(workbook);
        List<Integer> models = manageFinanceCubeAndModelIds(rowsDTO);
        HashMap<Integer, DimensionDataForModelDTO> map = browseDimensionElements(models);

        manageDimensionIdsInDataEditorRows(rowsDTO, map);

        importedData.setMap(map);
        importedData.setRows(rowsDTO);
        return importedData;
    }

    /**
     * Complete our rows data with finance cubes ids and model ids.
     * @return list of modelIds
     */
    private List<Integer> manageFinanceCubeAndModelIds(List<DataEditorRow> rowsDTO) {
        List<FinanceCubeModelCoreDTO> financeCubes = financeCubesService.browseFinanceCubes();
        List<Integer> models = new ArrayList<Integer>();
        for (DataEditorRow rowDTO: rowsDTO) {
            FinanceCubeModelCoreDTO financeCube = FinanceCubesUtil.findFinanceCubeByVisId(financeCubes, rowDTO.getFinanceCubeVisId());
            int modelId = financeCube.getModel().getModelId();
            rowDTO.setFinanceCubeId(financeCube.getFinanceCubeId());
            rowDTO.setModelId(modelId);
            if (!models.contains(modelId)) {
                models.add(modelId);
            }
        }
        return models;
    }

    /**
     *  Complete our rows data with dimension element ids.
     */
    private void manageDimensionIdsInDataEditorRows(List<DataEditorRow> rowsDTO, HashMap<Integer, DimensionDataForModelDTO> map) {
        for (DataEditorRow rowDTO: rowsDTO) {
            int modelId = rowDTO.getModelId();
            DimensionDataForModelDTO dimensionData = map.get(modelId);

            for (DimensionElementCoreDTO dimensionElementCoreDTO: dimensionData.getCostCenters()) {
                if (rowDTO.getCostCenter().equals(dimensionElementCoreDTO.getDimensionElementVisId())) {
                    rowDTO.setDim0(dimensionElementCoreDTO.getDimensionElementId());
                    break;
                }
            }

            for (DimensionElementCoreDTO dimensionElementCoreDTO: dimensionData.getExpenseCodes()) {
                if (rowDTO.getExpenseCode().equals(dimensionElementCoreDTO.getDimensionElementVisId())) {
                    rowDTO.setDim1(dimensionElementCoreDTO.getDimensionElementId());
                    break;
                }
            }

            String id = "" + rowDTO.getFinanceCubeId() + "," + rowDTO.getDim0() + "," + rowDTO.getDim1() + "," + rowDTO.getYear() + "," + rowDTO.getPeriod() + "," + rowDTO.getDataType();
            rowDTO.setDataEditorRowId(id);
        }
    }
}
