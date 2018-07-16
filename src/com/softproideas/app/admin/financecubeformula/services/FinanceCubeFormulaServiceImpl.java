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
package com.softproideas.app.admin.financecubeformula.services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cedar.cp.api.base.CPException;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.dto.cubeformula.AllCubeFormulasELO;
import com.cedar.cp.dto.cubeformula.CubeFormulaCK;
import com.cedar.cp.dto.cubeformula.CubeFormulaEditorSessionSSO;
import com.cedar.cp.dto.cubeformula.CubeFormulaImpl;
import com.cedar.cp.dto.cubeformula.CubeFormulaPK;
import com.cedar.cp.dto.datatype.AllDataTypesForModelELO;
import com.cedar.cp.dto.dimension.CalendarForModelELO;
import com.cedar.cp.dto.dimension.DimensionPK;
import com.cedar.cp.dto.dimension.DimensionRefImpl;
import com.cedar.cp.dto.dimension.HierarchyCK;
import com.cedar.cp.dto.dimension.HierarchyEditorSessionSSO;
import com.cedar.cp.dto.dimension.HierarchyImpl;
import com.cedar.cp.dto.dimension.HierarchyRefImpl;
import com.cedar.cp.dto.dimension.HierarcyDetailsFromDimIdELO;
import com.cedar.cp.dto.dimension.calendar.CalendarEditorSessionSSO;
import com.cedar.cp.dto.dimension.calendar.CalendarImpl;
import com.cedar.cp.dto.model.FinanceCubePK;
import com.cedar.cp.dto.model.HierarchiesForModelELO;
import com.cedar.cp.dto.model.ModelDimensionsELO;
import com.cedar.cp.dto.model.ModelPK;
import com.cedar.cp.ejb.api.cubeformula.CubeFormulaEditorSessionServer;
import com.cedar.cp.ejb.api.dimension.HierarchyEditorSessionServer;
import com.cedar.cp.ejb.api.dimension.calendar.CalendarEditorSessionServer;
import com.softproideas.app.admin.financecubeformula.mapper.FinanceCubeFormulaMapper;
import com.softproideas.app.admin.financecubeformula.model.FinanceCubeFormulaDTO;
import com.softproideas.app.admin.financecubeformula.model.FinanceCubeFormulaDetailsDTO;
import com.softproideas.app.admin.financecubeformula.util.TypeComparator;
import com.softproideas.app.admin.financecubeformula.util.ValidatorFinanceCubeFormula;
import com.softproideas.app.admin.financecubes.model.DimensionDTO;
import com.softproideas.app.admin.hierarchies.mapper.HierarchiesMapper;
import com.softproideas.app.admin.hierarchies.model.HierarchyNodeLazyDTO;
import com.softproideas.app.core.dimension.model.DimensionWithHierarchiesCoreDTO;
import com.softproideas.app.core.hierarchy.mapper.HierarchyCoreMapper;
import com.softproideas.app.core.hierarchy.model.HierarchyCoreDTO;
import com.softproideas.common.exceptions.ServiceException;
import com.softproideas.commons.context.CPContextHolder;
import com.softproideas.commons.model.ResponseMessage;
import com.softproideas.commons.model.error.ValidationError;

@Service("financeCubeFormulaService")
public class FinanceCubeFormulaServiceImpl implements FinanceCubeFormulaService {
    private static Logger logger = LoggerFactory.getLogger(FinanceCubeFormulaServiceImpl.class);

    @Autowired
    CPContextHolder cpContextHolder;

    /**
     * Get list of all Finance Cubes Formula.
     */
    @Override
    public List<FinanceCubeFormulaDTO> browseFinanceCubesFormula() {
        AllCubeFormulasELO allCubeFormulasELO = cpContextHolder.getListSessionServer().getAllCubeFormulas();
        return FinanceCubeFormulaMapper.mapAllCubeFormulasELO(allCubeFormulasELO);
    }

    /**
     * Get details for selected Finance Cube Formula from getItemData(int modelId, int financeCubeId).
     * @throws ValidationException 
     * @throws CPException 
     */
    @Override
    public FinanceCubeFormulaDetailsDTO getFinanceCubeFormulaDetails(int modelId, int financeCubeId, int financeCubeFormulaId) throws ServiceException, CPException, ValidationException {
        CubeFormulaImpl financeCubeFormulaImpl = getItemData(modelId, financeCubeId, financeCubeFormulaId);
        CalendarForModelELO calendarForModelELO = cpContextHolder.getListSessionServer().getCalendarForModel(modelId);
        CalendarImpl calendarImpl = getItemDataCalendar(calendarForModelELO);

        FinanceCubeFormulaDetailsDTO financeCubeFormulaDetailsDTO = FinanceCubeFormulaMapper.mapFinanceCubeFormulaImpl(financeCubeFormulaImpl, modelId, financeCubeId, financeCubeFormulaId, calendarImpl);
        return financeCubeFormulaDetailsDTO;
    }

    /**
     * Get details for selected Finance Cube Formula from database.
     */
    private CubeFormulaImpl getItemData(int modelId, int financeCubeId, int financeCubeFormulaId) throws ServiceException {
        try {
            CubeFormulaEditorSessionServer server = cpContextHolder.getCubeFormulaEditorSessionServer();
            CubeFormulaEditorSessionSSO cubeFormulaEditorSessionSSO = null;

            // ModelPK
            ModelPK modelPK = new ModelPK(modelId);
            // FinanceCubePK
            FinanceCubePK financeCubePK = new FinanceCubePK(financeCubeId);
            // CubeFormulaPK
            CubeFormulaPK cubeFormulaPk = new CubeFormulaPK(financeCubeFormulaId);
            // CubeCK
            CubeFormulaCK cubeFormulaCK = new CubeFormulaCK(modelPK, financeCubePK, cubeFormulaPk);
            if (financeCubeFormulaId != -1) {
                // SSO
                cubeFormulaEditorSessionSSO = server.getItemData(cubeFormulaCK);
            } else {
                cubeFormulaEditorSessionSSO = server.getNewItemData();

            }

            CubeFormulaImpl cubeFormulaImpl = cubeFormulaEditorSessionSSO.getEditorData();

            return cubeFormulaImpl;

        } catch (CPException e) {
            logger.error("Error during get Finance Cube!", e);
            throw new ServiceException("Error during get Finance Cube!", e);
        } catch (ValidationException e) {
            logger.error("Validation error during get Finance Cube!", e);
            throw new ServiceException("Validation error during get Finance Cube!", e);
        }
    }

    /**
     * Validate and save details for selected Finance Cube Formula and update(CubeFormulaImpl cubeFormulaImpl).
     */
    @Override
    public ResponseMessage saveFinanceCubFormulaDetails(FinanceCubeFormulaDetailsDTO financeCubeFormulaDetailsDTO) throws ServiceException {
        ResponseMessage message = null;
        CubeFormulaImpl financeCubeFormulaImpl = null;
        CubeFormulaEditorSessionServer server = cpContextHolder.getCubeFormulaEditorSessionServer();

        int financeCubeId = financeCubeFormulaDetailsDTO.getFinanceCube().getFinanceCubeId();
        int modelId = financeCubeFormulaDetailsDTO.getModel().getModelId();
        int financeCubeFormulaId = financeCubeFormulaDetailsDTO.getFinanceCubeFormulaId();

        financeCubeFormulaImpl = getItemData(modelId, financeCubeId, financeCubeFormulaId);
        ValidationError error = ValidatorFinanceCubeFormula.validateFinanceCubeDetails(financeCubeFormulaDetailsDTO, financeCubeFormulaImpl);
        if (error.getFieldErrors().isEmpty()) {
            financeCubeFormulaImpl = FinanceCubeFormulaMapper.mapCubeFormulaDetailsToImpl(financeCubeFormulaDetailsDTO, financeCubeFormulaImpl);
            message = save(financeCubeFormulaImpl, server);
        } else {
            error.setMessage("Error during saving Finance Cube Formula .");
            message = error;
        }
        return message;
    }

    /**
     * Save details Finance Cube's Formula at database.
     */
    private ResponseMessage save(CubeFormulaImpl financeCubeFormulaImpl, CubeFormulaEditorSessionServer server) throws ServiceException {
        try {
            if (financeCubeFormulaImpl.getPrimaryKey() != null) {
                server.update(financeCubeFormulaImpl);
            } else {
                server.insert(financeCubeFormulaImpl);
            }
            ResponseMessage success = new ResponseMessage(true);
            return success;
        } catch (CPException e) {
            logger.error("Error during update finance cube formula with key =" + financeCubeFormulaImpl.getPrimaryKey() + "!");
            throw new ServiceException("Error during update finance cube formula with key =" + financeCubeFormulaImpl.getPrimaryKey() + "!");
        } catch (ValidationException e) {
            logger.error("Error during save Finance Cube Formula ");
            ValidationError error = new ValidationError(e.getMessage());
            return error;
        }
    }

    /**
    * Return compiled string for formula
    */

    @Override
    public String getFinanceCubeFormulaComplie(int modelId, int financeCubeId, int financeCubeFormulaId, String formulaText, int formulaType) throws ServiceException {
        try {
            CubeFormulaEditorSessionServer server = cpContextHolder.getCubeFormulaEditorSessionServer();
            server.testCompilerFormula(financeCubeId, financeCubeFormulaId, formulaText, formulaType);
            return "Test compilation successful.";
        } catch (ValidationException e) {
            return e.getMessage();
        }
    }

    /**
     * Delete Finance Cube Formula from database.
     */
    @Override
    public ResponseMessage delete(int modelId, int financeCubeId, int financeCubeFormulaId) throws ServiceException {
        try {
            ModelPK modelPK = new ModelPK(modelId);
            FinanceCubePK financeCubePK = new FinanceCubePK(financeCubeId);
            CubeFormulaPK financeCubeFormulaPK = new CubeFormulaPK(financeCubeFormulaId);
            CubeFormulaCK cubeFormulaCK = new CubeFormulaCK(modelPK, financeCubePK, financeCubeFormulaPK);
            cpContextHolder.getCubeFormulaEditorSessionServer().delete(cubeFormulaCK);
            ResponseMessage responseMessage = new ResponseMessage(true);
            return responseMessage;
        } catch (ValidationException e) {
            ValidationError error = new ValidationError(e.getMessage());
            return error;
        } catch (CPException e) {
            logger.error("Error during validate finance cube formula with key =" + financeCubeFormulaId + "!");
            throw new ServiceException("Error during validate finance cube formula with key =" + financeCubeFormulaId + "!", e);
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<DimensionWithHierarchiesCoreDTO> fetchModelDimensionsWithHierarchies(int modelId) {
        List<DimensionWithHierarchiesCoreDTO> modelDimensionWithHierarchiesCoreDTO = new ArrayList<DimensionWithHierarchiesCoreDTO>();
        ModelDimensionsELO modelDimensionsELO = cpContextHolder.getListSessionServer().getModelDimensions(modelId);
        // map and add dimension to list
        for (Iterator<ModelDimensionsELO> dimensionsIterator = modelDimensionsELO.iterator(); dimensionsIterator.hasNext();) {
            ModelDimensionsELO dimension = dimensionsIterator.next();
            DimensionWithHierarchiesCoreDTO dimensionDTO = new DimensionWithHierarchiesCoreDTO();
            int dimensionId = ((DimensionPK) dimension.getDimensionEntityRef().getPrimaryKey()).getDimensionId();
            dimensionDTO.setDimensionId(dimensionId);
            dimensionDTO.setDimensionVisId(dimension.getDimensionEntityRef().getNarrative());
            dimensionDTO.setDimensionDescription(dimension.getDescription());
            dimensionDTO.setType(dimension.getDimensionType());
            // map and add hierarchy to dimension
            HierarcyDetailsFromDimIdELO hierarcyDetails = cpContextHolder.getListSessionServer().getHierarcyDetailsFromDimId(dimensionId);
            List<HierarchyCoreDTO> hirarchyList = new ArrayList<HierarchyCoreDTO>();
            for (Iterator<HierarcyDetailsFromDimIdELO> hierarchiesIterator = hierarcyDetails.iterator(); hierarchiesIterator.hasNext();) {
                HierarcyDetailsFromDimIdELO hierarchy = hierarchiesIterator.next();
                HierarchyCoreDTO hierarchyDTO = HierarchyCoreMapper.mapHierarchyRefImplToHierarchyCoreDTO((HierarchyRefImpl) hierarchy.getHierarchyEntityRef());
                hierarchyDTO.setHierarchyDescription(hierarchy.getDescription());
                hirarchyList.add(hierarchyDTO);
            }

            dimensionDTO.setHierarchies(hirarchyList);
            modelDimensionWithHierarchiesCoreDTO.add(dimensionDTO);
        }
        return modelDimensionWithHierarchiesCoreDTO;
    }

    /**
     * Returns data type tree
     */
    @Override
    public List<HierarchyNodeLazyDTO> browseDataTypesNode(Integer modelId) throws ServiceException {
        AllDataTypesForModelELO dataTypesELO = cpContextHolder.getListSessionServer().getAllDataTypesForModel(modelId);
        List<HierarchyNodeLazyDTO> dataTypes = FinanceCubeFormulaMapper.getDataTypesNode(dataTypesELO);
        return dataTypes;
    }

    /**
     * Create Data Type root {Data Types}
     */
    @Override
    public List<HierarchyNodeLazyDTO> dataTypesRoot(Integer modelId, String disableLevel) throws ServiceException {
        List<HierarchyNodeLazyDTO> dataTypeRootName = FinanceCubeFormulaMapper.dataTypeRoot(modelId, disableLevel);
        return dataTypeRootName;
    }

    /**
     * Get calendar dimension from CalendarForModelELO
     */
    @Override
    public List<HierarchyNodeLazyDTO> calendarDimension(Integer modelId, String disableLevel) throws ServiceException {
        CalendarForModelELO calendarForModelELO = cpContextHolder.getListSessionServer().getCalendarForModel(modelId);
        List<HierarchyNodeLazyDTO> calendarDimension = FinanceCubeFormulaMapper.calendarDimension(calendarForModelELO, disableLevel);
        return calendarDimension;
    }

    /**
     * Create tree root for calendar
     */
    @Override
    public List<HierarchyNodeLazyDTO> calendarRoot(Integer modelId, int calendarId, String disableLevel) throws ServiceException, CPException, ValidationException {
        CalendarForModelELO calendarForModelELO = cpContextHolder.getListSessionServer().getCalendarForModel(modelId);
        CalendarImpl calendarImpl = getItemDataCalendar(calendarForModelELO);
        List<HierarchyNodeLazyDTO> calendars = FinanceCubeFormulaMapper.getCalendarNode(calendarImpl, disableLevel);
        return calendars;
    }

    /**
     * Returns calendar tree
     */
    @Override
    public List<HierarchyNodeLazyDTO> browseCalendarNode(Integer modelId, int hierarchyElementId) throws ServiceException, CPException, ValidationException {
        CalendarForModelELO calendarForModelELO = cpContextHolder.getListSessionServer().getCalendarForModel(modelId);
        CalendarImpl calendarImpl = getItemDataCalendar(calendarForModelELO);
        List<HierarchyNodeLazyDTO> calendarsNode = FinanceCubeFormulaMapper.browseCalendarNode(calendarImpl, hierarchyElementId);
        return calendarsNode;
    }

    /**
     * Get details for Calendar
     */
    @SuppressWarnings("unchecked")
    private CalendarImpl getItemDataCalendar(CalendarForModelELO calendarForModelELO) throws CPException, ValidationException {
        CalendarImpl calendarImpl = null;
        for (Iterator<CalendarForModelELO> it = calendarForModelELO.iterator(); it.hasNext();) {
            CalendarForModelELO row = it.next();
            DimensionRefImpl dimensionRefImpl = (DimensionRefImpl) row.getDimensionEntityRef();
            HierarchyRefImpl hierarchyRefImpl = (HierarchyRefImpl) row.getHierarchyEntityRef();
            HierarchyCK hierarchyCK = new HierarchyCK(dimensionRefImpl.getDimensionPK(), hierarchyRefImpl.getHierarchyPK());
            CalendarEditorSessionServer server = cpContextHolder.getCalendarEditorSessionServer();
            CalendarEditorSessionSSO sso = server.getItemData(hierarchyCK);
            calendarImpl = sso.getEditorData();
        }

        return calendarImpl;
    }

    /**
     * Get hierarchy dimension
     */
    @Override
    public List<HierarchyNodeLazyDTO> hierarchyDimensionCc(Integer modelId, String disableLevel) throws ServiceException, CPException, ValidationException {
        HierarchiesForModelELO hierarchiesForModelELO = cpContextHolder.getListSessionServer().getHierarchiesForModel(modelId);
        List<HierarchyNodeLazyDTO> hierarchyDimensionCC = FinanceCubeFormulaMapper.hierarchyDimension(hierarchiesForModelELO, 2, disableLevel);
        return hierarchyDimensionCC;
    }

    /**
     * Get details from hierarchyImpl
     */
    @SuppressWarnings("unchecked")
    private HierarchyImpl getItemDataHierarchy(HierarchiesForModelELO hierarchiesForModelELO, int type) throws CPException, ValidationException {
        HierarchyImpl hierarchyImpl = null;
        for (Iterator<HierarchiesForModelELO> it = hierarchiesForModelELO.iterator(); it.hasNext();) {
            HierarchiesForModelELO row = it.next();
            DimensionRefImpl dimensionRefImpl = (DimensionRefImpl) row.getDimensionEntityRef();
            if (type == dimensionRefImpl.getType()) {
                HierarchyRefImpl hierarchyRefImpl = (HierarchyRefImpl) row.getHierarchyEntityRef();
                HierarchyCK hierarchyCK = new HierarchyCK(dimensionRefImpl.getDimensionPK(), hierarchyRefImpl.getHierarchyPK());
                HierarchyEditorSessionServer server = cpContextHolder.getHierarchyEditorSessionServer();
                HierarchyEditorSessionSSO sso = server.getItemData(hierarchyCK);
                hierarchyImpl = sso.getEditorData();
            }
        }
        return hierarchyImpl;
    }

    /**
     * Create tree root for hierarchy
     */
    @Override
    public List<HierarchyNodeLazyDTO> hierarchyRootCc(Integer modelId, Integer hierarchyId, String disableLevel) throws ServiceException, CPException, ValidationException {
        HierarchiesForModelELO hierarchiesForModelELO = cpContextHolder.getListSessionServer().getHierarchiesForModel(modelId);
        HierarchyImpl hierarchyImpl = getItemDataHierarchy(hierarchiesForModelELO, 2);
        List<HierarchyNodeLazyDTO> hierarchyRootCc = HierarchiesMapper.mapHierarchiesForModelELO(hierarchyImpl);
        return hierarchyRootCc;
    }

    /**
     * Returns hierarchy tree
     */
    @Override
    public List<HierarchyNodeLazyDTO> browseHierarchyCcNode(Integer modelId, int hierarchyElementId) throws ServiceException, CPException, ValidationException {
        HierarchiesForModelELO hierarchiesForModelELO = cpContextHolder.getListSessionServer().getHierarchiesForModel(modelId);
        HierarchyImpl hierarchyImpl = getItemDataHierarchy(hierarchiesForModelELO, 2);
        List<HierarchyNodeLazyDTO> hierarchyCcNode = HierarchiesMapper.mapImmediateChildrenELO(hierarchyImpl.getRoot(), hierarchyElementId);
        return hierarchyCcNode;
    }

    /**
     * Get hierarchy dimension
     */
    @Override
    public List<HierarchyNodeLazyDTO> hierarchyDimensionExp(Integer modelId, String disableLevel) throws ServiceException {
        HierarchiesForModelELO hierarchiesForModelELO = cpContextHolder.getListSessionServer().getHierarchiesForModel(modelId);
        List<HierarchyNodeLazyDTO> hierarchyDimensionExp = FinanceCubeFormulaMapper.hierarchyDimension(hierarchiesForModelELO, 1, disableLevel);
        return hierarchyDimensionExp;

    }

    /**
     * Create tree root for hierarchy
     */
    @Override
    public List<HierarchyNodeLazyDTO> hierarchyRootExp(Integer modelId, Integer hierarchyId) throws ServiceException, CPException, ValidationException {
        HierarchiesForModelELO hierarchiesForModelELO = cpContextHolder.getListSessionServer().getHierarchiesForModel(modelId);
        HierarchyImpl hierarchyImpl = getItemDataHierarchy(hierarchiesForModelELO, 1);
        List<HierarchyNodeLazyDTO> hierarchyRootExp = HierarchiesMapper.mapHierarchiesForModelELO(hierarchyImpl);
        return hierarchyRootExp;
    }

    /**
     * Returns hierarchy tree
     */
    @Override
    public List<HierarchyNodeLazyDTO> browseHierarchyExpNode(Integer modelId, int hierarchyElementId) throws ServiceException, CPException, ValidationException {
        HierarchiesForModelELO hierarchiesForModelELO = cpContextHolder.getListSessionServer().getHierarchiesForModel(modelId);
        HierarchyImpl hierarchyImpl = getItemDataHierarchy(hierarchiesForModelELO, 1);
        List<HierarchyNodeLazyDTO> hierarchyExpNode = HierarchiesMapper.mapImmediateChildrenELO(hierarchyImpl.getRoot(), hierarchyElementId);
        return hierarchyExpNode;
    }

    /**
     * Get table headers for table in  Cube Formula details
     */
    @SuppressWarnings("unchecked")
    @Override
    public DimensionDTO[] getTableHeader(int modelId) throws ServiceException {
        ModelDimensionsELO modelDimensionsELO = cpContextHolder.getListSessionServer().getModelDimensions(modelId);
        DimensionDTO[] dimensionDTO = new DimensionDTO[modelDimensionsELO.size()];
        int i = 0;
        for (Iterator<ModelDimensionsELO> dimensionsIterator = modelDimensionsELO.iterator(); dimensionsIterator.hasNext();) {
            ModelDimensionsELO dimension = dimensionsIterator.next();
            dimensionDTO[i] = new DimensionDTO();
            int dimensionId = ((DimensionPK) dimension.getDimensionEntityRef().getPrimaryKey()).getDimensionId();
            dimensionDTO[i].setDimensionId(dimensionId);
            dimensionDTO[i].setDimensionVisId(dimension.getDimensionEntityRef().getNarrative());
            dimensionDTO[i].setType(dimension.getDimensionType());
            i++;
        }
        Arrays.sort(dimensionDTO, new TypeComparator());
        return dimensionDTO;
    }

}
