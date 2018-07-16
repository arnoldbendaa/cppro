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
package com.softproideas.app.admin.models.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cedar.cp.api.base.CPConnection;
import com.cedar.cp.api.base.CPException;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.datatype.DataTypeRef;
import com.cedar.cp.api.dimension.HierarchyRef;
import com.cedar.cp.api.model.ModelRef;
import com.cedar.cp.dto.datatype.AllDataTypesELO;
import com.cedar.cp.dto.datatype.DataTypePK;
import com.cedar.cp.dto.datatype.DataTypeRefImpl;
import com.cedar.cp.dto.dimension.AvailableDimensionsELO;
import com.cedar.cp.dto.model.AllFinanceCubesELO;
import com.cedar.cp.dto.model.AllModelsELO;
import com.cedar.cp.dto.model.ModelEditorSessionCSO;
import com.cedar.cp.dto.model.ModelEditorSessionSSO;
import com.cedar.cp.dto.model.ModelImpl;
import com.cedar.cp.dto.model.ModelPK;
import com.cedar.cp.dto.model.ModelRefImpl;
import com.cedar.cp.ejb.api.model.ModelEditorSessionServer;
import com.cedar.cp.ejb.api.model.globalmapping2.GlobalMappedModel2EditorSessionServer;
import com.cedar.cp.ejb.impl.model.ModelEditorSessionSEJB;
import com.cedar.cp.impl.model.ModelEditorSessionImpl;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.softproideas.app.admin.models.mapper.ModelsMapper;
import com.softproideas.app.admin.models.model.FinanceCubeModelDTO;
import com.softproideas.app.admin.models.model.ModelDetailsDTO;
import com.softproideas.app.admin.models.validate.ValidatorModels;
import com.softproideas.app.core.datatype.model.DataTypeCoreDTO;
import com.softproideas.app.core.model.model.ModelCoreDTO;
import com.softproideas.common.exceptions.ServiceException;
import com.softproideas.commons.context.CPContextHolder;
import com.softproideas.commons.model.ResponseMessage;
import com.softproideas.commons.model.error.ValidationError;

@Service("modelsService")
public class ModelsServiceImpl implements ModelsService {

    private static Logger logger = LoggerFactory.getLogger(ModelsServiceImpl.class);

    @Autowired
    CPContextHolder cpContextHolder;

//    ModelEditorSessionSEJB server = new ModelEditorSessionSEJB();
    /*
     * (non-Javadoc)
     * @see com.softproideas.app.admin.models.service.ModelsService#fetchFilterModelsFinanceCubeForLoggedUser()
     */
    @Override
    public List<FinanceCubeModelDTO> browseFilterModelsFinanceCubeForLoggedUser() throws ServiceException {
        // Retrieves all finance cubes with related models of current user
        // TODO: Maybe cpContextHolder.getListSessionServer().getAllModelsForLoggedUser() ?
        // It lists all models, but getAllFinanceCubesForLoggedUser() lists only models which has FinanceCubes -
        // so if we create Model, but not FC, filter is incomplete.
        AllFinanceCubesELO allFinanceCubesELO = cpContextHolder.getListSessionServer().getAllFinanceCubesForLoggedUser();
        List<FinanceCubeModelDTO> fcModelsDTO = ModelsMapper.mapAllFinanseCubesELOWithModel(allFinanceCubesELO);
        return fcModelsDTO;
    }

    /*
     * (non-Javadoc)
     * @see com.softproideas.app.admin.models.service.ModelsService#fetchModels()
     */
    @Override
    public List<ModelCoreDTO> browseModels() throws ServiceException {
        AllModelsELO allModels = cpContextHolder.getListSessionServer().getAllModels();
        List<ModelCoreDTO> modelsDTO = ModelsMapper.mapAllModelsELOToModelsDTO(allModels);
        return modelsDTO;
    }

    /*
     * (non-Javadoc)
     * @see com.softproideas.app.admin.models.service.ModelsService#fetchModel(int)
     */
    @Override
    public ModelDetailsDTO fetchModel(int modelId) throws ServiceException {
        ModelDetailsDTO modelDetailsDTO;
        ModelEditorSessionServer server = cpContextHolder.getModelEditorSessionServer();
        AvailableDimensionsELO dimensionList = cpContextHolder.getListSessionServer().getAvailableDimensions();
        HierarchyRef[] hierarchy = server.getAvailableBudgetHierarchyRefs();
        if (modelId != -1) {
            // if model egzist
            ModelImpl modelImpl = getItemData(modelId, ModelEditorSessionImpl.server);
            modelDetailsDTO = ModelsMapper.mapModelImpl(modelImpl, dimensionList, hierarchy);
        } else {
            // create empty model with list available reference for Dimensions and Hierarchies.
            modelDetailsDTO = new ModelDetailsDTO();
            ModelsMapper.mapAvailableDimensionAndHierarchyAndSetProperties(modelDetailsDTO, dimensionList, hierarchy);
        }
        return modelDetailsDTO;
    }

    /**
     * Get details for selected Model from database.
     */
    private ModelImpl getItemData(int modelId, ModelEditorSessionSEJB server) throws ServiceException {
        try {
            ModelPK modelPK = new ModelPK(modelId);
            ModelEditorSessionSSO modelEditorSessionSSO;
            modelEditorSessionSSO = server.getItemData(cpContextHolder.getUserId(),modelPK);
            ModelImpl modelImpl = modelEditorSessionSSO.getEditorData();
            return modelImpl;
        } catch (CPException e) {
            logger.error("Error during get Model!", e);
            throw new ServiceException("Error during get Model!", e);
        } catch (ValidationException e) {
            logger.error("Validation error during get Model!", e);
            throw new ServiceException("Validation error during get Model!", e);
        }
    }

    /*
     * (non-Javadoc)
     * @see com.softproideas.app.admin.models.service.ModelsService#saveModelDetails(com.softproideas.app.admin.models.model.ModelDetailsDTO)
     */
    @Override
    public ResponseMessage saveModelDetails(ModelDetailsDTO modelDetailsDTO) throws ServiceException {
        String operation = "edit";
//        ModelEditorSessionServer server = cpContextHolder.getModelEditorSessionServer();
        ModelImpl modelImpl = getItemData(modelDetailsDTO.getModelId(), ModelEditorSessionImpl.server);
        ValidationError error = ValidatorModels.validateModelDetails(modelDetailsDTO);

        if (error.getFieldErrors().isEmpty()) {
            modelImpl = ModelsMapper.mapModelDTO(modelDetailsDTO, modelImpl);
            return update(modelImpl, ModelEditorSessionImpl.server, operation);
        } else {
            error.setMessage("Error during Model " + operation + " operation!");
            return error;
        }
    }

    /**
     * Save details Model's at database.
     */
    private ResponseMessage update(ModelImpl modelImpl, ModelEditorSessionSEJB server, String operation) throws ServiceException {
        try {
            if (operation == "edit") {
                server.update(new ModelEditorSessionCSO(cpContextHolder.getUserId(), modelImpl));
            } else {
                server.insert(new ModelEditorSessionCSO(cpContextHolder.getUserId(), modelImpl));
            }
            ResponseMessage success = new ResponseMessage(true);
            return success;
        } catch (CPException e) {
            logger.error("Error during Model " + operation + " operation!", e);
            throw new ServiceException("Error during Model " + operation + " operation!", e);
        } catch (ValidationException e) {
            logger.error("Error during Model " + operation + " operation!");
            ValidationError error = new ValidationError(e.getMessage());
            return error;
        }
    }

    /*
     * (non-Javadoc)
     * @see com.softproideas.app.admin.models.service.ModelsService#insertModelDetails(com.softproideas.app.admin.models.model.ModelDetailsDTO)
     */
    @Override
    public ResponseMessage insertModelDetails(ModelDetailsDTO modelDetailsDTO) throws ServiceException, CPException, ValidationException {
        String operation = "create";
//        ModelEditorSessionServer server = cpContextHolder.getModelEditorSessionServer();
        ModelImpl modelImpl = ModelEditorSessionImpl.server.getNewItemData(cpContextHolder.getUserId()).getEditorData();
        ValidationError error = ValidatorModels.validateModelDetails(modelDetailsDTO);

        if (error.getFieldErrors().isEmpty()) {
            modelImpl = ModelsMapper.mapModelDTO(modelDetailsDTO, modelImpl);
            return update(modelImpl, ModelEditorSessionImpl.server, operation);
        } else {
            error.setMessage("Error during Model " + operation + " operation!");
            return error;
        }
    }

    /*
     * (non-Javadoc)
     * @see com.softproideas.app.admin.models.service.ModelsService#deleteModel(int)
     */
    @Override
    public ResponseMessage deleteModel(int modelId) throws ServiceException {
        ModelPK modelPK = new ModelPK(modelId);
        try {
//            cpContextHolder.getModelEditorSessionServer().delete(modelPK);
        	ModelEditorSessionImpl.server.delete(modelId, modelPK);
            ResponseMessage responseMessage = new ResponseMessage(true);
            return responseMessage;
        } catch (CPException e) {
            logger.error("Error during delete Model!", e);
            throw new ServiceException("Error during delete Model!", e);
        } catch (ValidationException e) {
            logger.error("Validation error during delete Model!", e);
            ValidationError error = new ValidationError(e.getMessage());
            return error;
        }
    }

    /*
     * (non-Javadoc)
     * @see com.softproideas.app.admin.models.service.ModelsService#fetchDataForImport(int)
     */
    @SuppressWarnings("rawtypes")
    @Override
    public HashMap<String, List> fetchDataForImport(int modelId) {
        HashMap<String, List> modelsAndDataTypesMap = new HashMap<String, List>();
        AllModelsELO models = cpContextHolder.getListSessionServer().getAllModelsForGlobalMappedModel(modelId);
        List<ModelCoreDTO> modelsDTO = ModelsMapper.mapAllModelsELOToModelsDTO(models);
        AllDataTypesELO dataTypes = cpContextHolder.getListSessionServer().getAllDataTypes();
        List<DataTypeCoreDTO> dataTypesDTO = ModelsMapper.mapAllDataTypesELOToDataTypesDTO(dataTypes);
        modelsAndDataTypesMap.put("models", modelsDTO);
        modelsAndDataTypesMap.put("dataTypes", dataTypesDTO);
        return modelsAndDataTypesMap;
    }

    /*
     * (non-Javadoc)
     * @see com.softproideas.app.admin.models.service.ModelsService#sendDataForImport(java.util.HashMap, int)
     */
    @SuppressWarnings("rawtypes")
    @Override
    public ResponseMessage sendDataForImport(HashMap<String, List> selectedData, int modelId) throws ServiceException, CPException, ValidationException {
        String errorMsg = "Error during import data.";
        CPConnection connection = cpContextHolder.getCPConnection();
        // check if model is global mapped model
        GlobalMappedModel2EditorSessionServer globalServer = new GlobalMappedModel2EditorSessionServer(connection);
        if (!globalServer.isGlobalMappedModel(modelId)) {
            logger.error("Validation " + errorMsg);
            ValidationError error = new ValidationError("Validation " + errorMsg);
            return error;
        }
        // get selected models and data types
        ObjectMapper mapper = new ObjectMapper();
        List<ModelCoreDTO> selectedModels = mapper.convertValue(selectedData.get("models"), new TypeReference<List<ModelCoreDTO>>() {
        });
        List<DataTypeCoreDTO> selectedDataTypes = mapper.convertValue(selectedData.get("dataTypes"), new TypeReference<List<DataTypeCoreDTO>>() {
        });
        // prepare models to do import
        List<ModelRef> modelsToSend = new ArrayList<ModelRef>();
        for (Iterator<ModelCoreDTO> modelIterator = selectedModels.iterator(); modelIterator.hasNext();) {
            ModelCoreDTO selectedModel = modelIterator.next();
            ModelPK modelPK = new ModelPK(selectedModel.getModelId());
            ModelRefImpl selectedModelRefImpl = new ModelRefImpl(modelPK, selectedModel.getModelVisId());
            modelsToSend.add(selectedModelRefImpl);
        }
        // prepare data types to do import
        List<DataTypeRef> dataTypesToSend = new ArrayList<DataTypeRef>();
        for (Iterator<DataTypeCoreDTO> dataTypeIterator = selectedDataTypes.iterator(); dataTypeIterator.hasNext();) {
            DataTypeCoreDTO selectedDataType = dataTypeIterator.next();
            DataTypePK dataTypePK = new DataTypePK((short) selectedDataType.getDataTypeId());
            DataTypeRefImpl selectedDataTypeRefImpl = new DataTypeRefImpl(dataTypePK, selectedDataType.getDataTypeVisId(), null, -1, null, null);
            dataTypesToSend.add(selectedDataTypeRefImpl);
        }

        try {
            ModelEditorSessionServer server = new ModelEditorSessionServer(connection);
            server.issueImportDataModelTask(modelsToSend, dataTypesToSend, modelId, connection.getUserContext().getUserId());
            ResponseMessage success = new ResponseMessage(true);
            return success;
        } catch (ValidationException e) {
            logger.error("Validation " + errorMsg);
            ValidationError error = new ValidationError(e.getMessage());
            return error;
        } catch (CPException e) {
            logger.error(errorMsg);
            throw new ServiceException(errorMsg);
        }
    }

}
