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
package com.softproideas.app.admin.models.controller;

import java.util.HashMap;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.softproideas.app.admin.models.model.FinanceCubeModelDTO;
import com.softproideas.app.admin.models.model.ModelDetailsDTO;
import com.softproideas.app.admin.models.service.ModelsService;
import com.softproideas.app.core.model.model.ModelCoreWithGlobalDTO;
import com.softproideas.app.core.model.service.ModelCoreService;
import com.softproideas.commons.model.ResponseMessage;

@RequestMapping(value = "/models")
@Controller
public class ModelsController {

    @Autowired
    ModelsService modelsService;
    
    @Autowired
    ModelCoreService modelCoreService;    

    /**
     * Get list of all Models.
     */
    @ResponseBody
    @RequestMapping(method = RequestMethod.GET)
    public List<ModelCoreWithGlobalDTO> browseModels() throws Exception {
        return modelCoreService.browseModelsForLoggedUser();
    }

    /**
     * Update details for selected Model from database.
     */
    @ResponseBody
    @RequestMapping(method = RequestMethod.PUT)
    public ResponseMessage updateModelsDetails(@Valid @RequestBody ModelDetailsDTO modelDetailsDTO) throws Exception {
        ResponseMessage responseMessage = modelsService.saveModelDetails(modelDetailsDTO);
        return responseMessage;
    }

    /**
     * Create Model from database.
     */
    @ResponseBody
    @RequestMapping(method = RequestMethod.POST)
    public ResponseMessage createModelsDetails(@Valid @RequestBody ModelDetailsDTO modelDetailsDTO) throws Exception {
        ResponseMessage responseMessage = modelsService.insertModelDetails(modelDetailsDTO);
        return responseMessage;
    }

    /**
     * Get details for selected Model.
     */
    @ResponseBody
    @RequestMapping(value = "/{modelId}", method = RequestMethod.GET)
    public ModelDetailsDTO fetchModel(@PathVariable int modelId) throws Exception {
        return modelsService.fetchModel(modelId);
    }

    /**
     * Delete selected Model from database.
     */
    @ResponseBody
    @RequestMapping(value = "/{modelId}", method = RequestMethod.DELETE)
    public ResponseMessage deleteModel(@PathVariable int modelId) throws Exception {
        ResponseMessage responseMessage = modelsService.deleteModel(modelId);
        return responseMessage;
    }

    @SuppressWarnings("rawtypes")
    @ResponseBody
    @RequestMapping(value = "/import/{modelId}", method = RequestMethod.GET)
    public HashMap<String, List> fetchAllModelsForGlobalMappedModel(@PathVariable int modelId) throws Exception {
        return modelsService.fetchDataForImport(modelId);
    }

    @SuppressWarnings("rawtypes")
    @ResponseBody
    @RequestMapping(value = "/import/{modelId}", method = RequestMethod.POST)
    public ResponseMessage sendDataToImport(@PathVariable int modelId, @Valid @RequestBody HashMap<String, List> dataToImport) throws Exception {
        ResponseMessage responseMessage = modelsService.sendDataForImport(dataToImport, modelId);
        return responseMessage;
    }

    /**
     * GET /models
     * 
     * Method returns list of all models
     */
    @ResponseBody
    @RequestMapping(value = "/financeCubeModels", method = RequestMethod.GET)
    public List<FinanceCubeModelDTO> fetchModels() throws Exception {
        return modelsService.browseFilterModelsFinanceCubeForLoggedUser();
    }

}
