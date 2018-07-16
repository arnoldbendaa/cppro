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
package com.softproideas.app.admin.financecubes.services;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cedar.cp.api.base.CPException;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.dto.dimension.AllDimensionsForModelELO;
import com.cedar.cp.dto.model.AllFinanceCubesELO;
import com.cedar.cp.dto.model.FinanceCubeCK;
import com.cedar.cp.dto.model.FinanceCubeEditorSessionCSO;
import com.cedar.cp.dto.model.FinanceCubeEditorSessionSSO;
import com.cedar.cp.dto.model.FinanceCubeImpl;
import com.cedar.cp.dto.model.FinanceCubePK;
import com.cedar.cp.dto.model.ModelPK;
import com.cedar.cp.ejb.api.model.FinanceCubeEditorSessionServer;
import com.cedar.cp.ejb.impl.model.FinanceCubeEditorSessionSEJB;
import com.softproideas.app.admin.financecubes.mapper.FinanceCubesMapper;
import com.softproideas.app.admin.financecubes.model.DimensionDTO;
import com.softproideas.app.admin.financecubes.model.FinanceCubeDetailsDTO;
import com.softproideas.app.admin.financecubes.util.ValidatorFinanceCubes;
import com.softproideas.app.core.financecube.model.FinanceCubeModelCoreDTO;
import com.softproideas.common.exceptions.ServiceException;
import com.softproideas.commons.context.CPContextHolder;
import com.softproideas.commons.model.ResponseMessage;
import com.softproideas.commons.model.error.ValidationError;

@Service("financeCubesService")
public class FinanceCubesServiceImpl implements FinanceCubesService {
    private static Logger logger = LoggerFactory.getLogger(FinanceCubesServiceImpl.class);

    @Autowired
    CPContextHolder cpContextHolder;
    FinanceCubeEditorSessionSEJB server = new FinanceCubeEditorSessionSEJB();

    /**
     * Get list of all Finance Cubes.
     */
    @Override
    public List<FinanceCubeModelCoreDTO> browseFinanceCubes() {
        AllFinanceCubesELO allFinanceCubesELO = cpContextHolder.getListSessionServer().getAllFinanceCubesForLoggedUser();
        return FinanceCubesMapper.mapAllFinanseCubesELO(allFinanceCubesELO);
    }

    /**
     * Get details for selected Finance Cube from getItemData(int modelId, int financeCubeId).
     */
    @Override
    public FinanceCubeDetailsDTO getFinanceCubeDetails(int modelId, int financeCubeId) throws ServiceException {
        FinanceCubeImpl financeCubeImpl = getItemData(modelId, financeCubeId);
        FinanceCubeDetailsDTO financeCubeDetailsDTO = FinanceCubesMapper.mapFinanceCubeImpl(financeCubeImpl, modelId, financeCubeId);
        return financeCubeDetailsDTO;
    }

    /**
     * Get details for selected Finance Cube from database.
     */
    private FinanceCubeImpl getItemData(int modelId, int financeCubeId) throws ServiceException {
        try {
//            FinanceCubeEditorSessionServer server = cpContextHolder.getFinanceCubeEditorSessionServer();
            FinanceCubePK financeCubePK = new FinanceCubePK(financeCubeId);
            ModelPK modelPK = new ModelPK(modelId);
            FinanceCubeCK financeCubeCK = new FinanceCubeCK(modelPK, financeCubePK);
            FinanceCubeEditorSessionSSO financeCubeEditorSessionSSO;
            financeCubeEditorSessionSSO = server.getItemData(cpContextHolder.getUserId(),financeCubeCK);
            FinanceCubeImpl financeCubeImpl = financeCubeEditorSessionSSO.getEditorData();
            return financeCubeImpl;
        } catch (CPException e) {
            logger.error("Error during get Finance Cube!", e);
            throw new ServiceException("Error during get Finance Cube!", e);
        } catch (ValidationException e) {
            logger.error("Validation error during get Finance Cube!", e);
            throw new ServiceException("Validation error during get Finance Cube!", e);
        }
    }

    /**
     * Validate and save details for selected Finance Cube at update(FinanceCubeImpl financeCubeImpl).
     */
    @Override
    public ResponseMessage saveFinanceCubeDetails(FinanceCubeDetailsDTO financeCubeDetailsDTO) throws ServiceException {
        String operation = "edit";
//        FinanceCubeEditorSessionServer server = cpContextHolder.getFinanceCubeEditorSessionServer();
        FinanceCubeImpl financeCubeImpl = getItemData(financeCubeDetailsDTO.getModel().getModelId(), financeCubeDetailsDTO.getFinanceCubeId());
        ValidationError error = ValidatorFinanceCubes.validateFinanceCubeDetails(financeCubeDetailsDTO, financeCubeImpl);

        if (error.getFieldErrors().isEmpty()) {
            financeCubeImpl = FinanceCubesMapper.mapFinanceCubeDTO(financeCubeDetailsDTO, financeCubeImpl);
            return update(financeCubeImpl, server, operation);
        } else {
            error.setMessage("Error during Finance Cube " + operation + " operation!");
            return error;
        }
    }

    /**
     * Save details Finance Cube's at database.
     */
    private ResponseMessage update(FinanceCubeImpl financeCubeImpl, FinanceCubeEditorSessionSEJB server, String operation) throws ServiceException {
        try {
            if (operation == "edit") {
                server.update(new FinanceCubeEditorSessionCSO(cpContextHolder.getUserId(), financeCubeImpl));
            } else {
                server.insert(new FinanceCubeEditorSessionCSO(cpContextHolder.getUserId(), financeCubeImpl));
            }
            ResponseMessage success = new ResponseMessage(true);
            return success;
        } catch (CPException e) {
            logger.error("Error during Finance Cube " + operation + " operation!", e);
            throw new ServiceException("Error during Finance Cube " + operation + " operation!", e);
        } catch (ValidationException e) {
            logger.error("Error during Finance Cube " + operation + " operation!");
            ValidationError error = new ValidationError(e.getMessage());
            return error;
        }
    }

    /**
     * Create Finance Cube in database.
     */
    @Override
    public ResponseMessage insertFinanceCubeDetails(FinanceCubeDetailsDTO financeCubeDetailsDTO) throws ServiceException, CPException, ValidationException {
        String operation = "create";
//        FinanceCubeEditorSessionServer server = cpContextHolder.getFinanceCubeEditorSessionServer();
        FinanceCubeImpl financeCubeImpl = server.getNewItemData(cpContextHolder.getUserId()).getEditorData();
        ValidationError error = ValidatorFinanceCubes.validateFinanceCubeDetails(financeCubeDetailsDTO, financeCubeImpl);

        if (error.getFieldErrors().isEmpty()) {
            financeCubeImpl = FinanceCubesMapper.mapFinanceCubeDTO_WithModel(financeCubeDetailsDTO, financeCubeImpl);
            return update(financeCubeImpl, server, operation);
        } else {
            error.setMessage("Error during Finance Cube " + operation + " operation!");
            return error;
        }
    }

    /**
     * Get list of Model's Dimensions.
     */
    public List<DimensionDTO> browseDimensionsForModel(int modelId) {
        AllDimensionsForModelELO elo = cpContextHolder.getListSessionServer().getAllDimensionsForModel(modelId);
        List<DimensionDTO> list = FinanceCubesMapper.mapAllDimensionsForModel(elo);
        return list;
    }

    /**
     * Delete selected FinanceCube.
     */
    @Override
    public ResponseMessage deleteFinanceCube(int modelId, int financeCubeId) throws ServiceException {
        FinanceCubePK financeCubePK = new FinanceCubePK(financeCubeId);
        ModelPK modelPK = new ModelPK(modelId);
        FinanceCubeCK financeCubeCK = new FinanceCubeCK(modelPK, financeCubePK);
        try {
//            cpContextHolder.getFinanceCubeEditorSessionServer().delete(financeCubeCK);
        	server.delete(cpContextHolder.getUserId(),financeCubeCK);
            ResponseMessage responseMessage = new ResponseMessage(true);
            return responseMessage;
        } catch (CPException e) {
            logger.error("Error during delete Finance Cube!", e);
            throw new ServiceException("Error during delete Finance Cube!", e);
        } catch (ValidationException e) {
            logger.error("Validation error during delete Finance Cube!", e);
            ValidationError error = new ValidationError(e.getMessage());
            return error;
        }
    }

}
