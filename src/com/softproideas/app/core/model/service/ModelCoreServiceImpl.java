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
package com.softproideas.app.core.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cedar.cp.api.base.CPException;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.dto.model.AllModelsELO;
import com.cedar.cp.dto.model.ModelEditorSessionSSO;
import com.cedar.cp.dto.model.ModelImpl;
import com.cedar.cp.dto.model.ModelPK;
import com.cedar.cp.ejb.api.model.ModelEditorSessionServer;
import com.cedar.cp.utc.common.CPContext;
import com.softproideas.app.core.model.mapper.ModelCoreMapper;
import com.softproideas.app.core.model.model.ModelCoreDTO;
import com.softproideas.app.core.model.model.ModelCoreWithGlobalDTO;
import com.softproideas.common.exceptions.ServiceException;
import com.softproideas.commons.context.CPContextHolder;

@Service("modelCoreService")
public class ModelCoreServiceImpl implements ModelCoreService {

    @Autowired
    CPContextHolder cpContextHolder;
    int userId;
    public void Init(int userId,CPContext context){
    	this.userId = userId;
    	cpContextHolder = new CPContextHolder();
    	cpContextHolder.init(context);
    }

    /* (non-Javadoc)
     * 
     * @see com.softproideas.app.core.model.service.ModelCoreService#browseModelsForLoggedUser() */
    @Override
    public List<ModelCoreWithGlobalDTO> browseModelsForLoggedUser() throws ServiceException {
        AllModelsELO allModelsForLoggedUser = cpContextHolder.getListSessionServer().getAllModelsForLoggedUser();
        List<ModelCoreWithGlobalDTO> modelsDTO = ModelCoreMapper.mapAllModelsELOToModelsWithGlobalDTO(allModelsForLoggedUser);
        return modelsDTO;
    }

    @Override
    public ModelCoreDTO fetchModelCore(int modelId) throws ServiceException {
        ModelEditorSessionServer server = cpContextHolder.getModelEditorSessionServer();
        ModelImpl modelImpl = getItemData(modelId, server);
        ModelCoreDTO modelCoreDTO = ModelCoreMapper.mapModelImpl(modelImpl);
        return modelCoreDTO;
    }

    @Override
    public ModelCoreDTO fetchModelCore(String modelVisId) throws ServiceException {
        AllModelsELO allModels = cpContextHolder.getListSessionServer().getAllModels();
        ModelCoreDTO modelCoreDTO = ModelCoreMapper.mapAllModels(allModels, modelVisId);
        return modelCoreDTO;
    }

    /**
     * Get details for selected Model from database.
     */
    private ModelImpl getItemData(int modelId, ModelEditorSessionServer server) throws ServiceException {
        try {
            ModelPK modelPK = new ModelPK(modelId);
            ModelEditorSessionSSO modelEditorSessionSSO;
            modelEditorSessionSSO = server.getItemData(modelPK);
            ModelImpl modelImpl = modelEditorSessionSSO.getEditorData();
            return modelImpl;
        } catch (CPException e) {
            throw new ServiceException("Error during get Model!", e);
        } catch (ValidationException e) {
            throw new ServiceException("Validation error during get Model!", e);
        }
    }
}
