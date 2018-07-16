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

import java.util.HashMap;
import java.util.List;

import com.cedar.cp.api.base.CPException;
import com.cedar.cp.api.base.ValidationException;
import com.softproideas.app.admin.models.model.FinanceCubeModelDTO;
import com.softproideas.app.admin.models.model.ModelDetailsDTO;
import com.softproideas.app.core.model.model.ModelCoreDTO;
import com.softproideas.common.exceptions.ServiceException;
import com.softproideas.commons.model.ResponseMessage;

public interface ModelsService {

    List<ModelCoreDTO> browseModels() throws ServiceException;

    /**
     * Fetches all finance cubes with its models to use in filter.
     */
    List<FinanceCubeModelDTO> browseFilterModelsFinanceCubeForLoggedUser() throws ServiceException;

    /**
     * Get details for selected Model from getItemData(int modelId) with list available reference for Dimensions and Hierarchies
     * or empty model with this list.
     */
    ModelDetailsDTO fetchModel(int modelId) throws ServiceException, CPException, ValidationException;

    /**
     * Validate and save details for selected Model at update(ModelImpl modelImpl).
     */
    ResponseMessage saveModelDetails(ModelDetailsDTO modelDetailsDTO) throws ServiceException;

    /**
     * Create Model in database.
     */
    ResponseMessage insertModelDetails(ModelDetailsDTO modelDetailsDTO) throws ServiceException, CPException, ValidationException;

    /**
     * Delete selected Model.
     */
    ResponseMessage deleteModel(int modelId) throws ServiceException;

    /**
     * TODO: comment me
     */
    @SuppressWarnings("rawtypes")
    HashMap<String, List> fetchDataForImport(int modelId);

    /**
     * TODO: comment me
     */
    @SuppressWarnings("rawtypes")
    ResponseMessage sendDataForImport(HashMap<String, List> selectedData, int modelId) throws ServiceException, CPException, ValidationException;

}
