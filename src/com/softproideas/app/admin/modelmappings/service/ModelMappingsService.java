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
package com.softproideas.app.admin.modelmappings.service;

import java.util.List;

import javax.servlet.http.HttpSession;

import com.softproideas.app.admin.datatypes.model.DataTypeDetailsDTO;
import com.softproideas.app.admin.modelmappings.model.CompanyDTO;
import com.softproideas.app.admin.modelmappings.model.DataTypesTreeNodeDTO;
import com.softproideas.app.admin.modelmappings.model.DimensionRequestDTO;
import com.softproideas.app.admin.modelmappings.model.ExternalRequestDTO;
import com.softproideas.app.admin.modelmappings.model.LedgerDTO;
import com.softproideas.app.admin.modelmappings.model.LedgersRequestDTO;
import com.softproideas.app.admin.modelmappings.model.DimensionElementNodeDTO;
import com.softproideas.app.admin.modelmappings.model.ModelSuggestedAndDimensionsDTO;
import com.softproideas.app.admin.modelmappings.model.details.MappedCalendarDTO;
import com.softproideas.app.admin.modelmappings.model.details.MappedModelDTO;
import com.softproideas.app.admin.modelmappings.model.details.MappedModelDetailsDTO;
import com.softproideas.common.exceptions.ServiceException;
import com.softproideas.commons.model.ResponseMessage;

/**
 * <p>This service is aimed to take care of model mapping CRUD operations and import task.</p>
 *
 * @author Jarosław Kaczmarski
 * @email jarok@softproideas.com
 * <p>2014 All rights reserved to IT Services Jacek Kurasiewicz</p>
 */
public interface ModelMappingsService {

    List<MappedModelDTO> browseMappedModels() throws ServiceException;
    
    ResponseMessage deleteGlobal(int mappedModelId) throws ServiceException;
    ResponseMessage delete(int mappedModelId) throws ServiceException;
    
    int issueModelImportTask(boolean safeMode, int mappedModelId) throws ServiceException;
    int issueModelImportTaskGlobal(boolean safeMode, int mappedModelId) throws ServiceException;
    
    MappedModelDetailsDTO fetchMappedModelDetails(int mappedModelId) throws ServiceException;

    List<CompanyDTO> browseExternalCompanies(ExternalRequestDTO externalRequest) throws ServiceException;

    List<LedgerDTO> browseExternalLedgersGlobal(LedgersRequestDTO ledgerRequest) throws ServiceException;

    List<LedgerDTO> browseExternalLedgers(LedgersRequestDTO ledgerRequest) throws ServiceException;

    ModelSuggestedAndDimensionsDTO fetchModelSuggestedAndDimensions(DimensionRequestDTO dimensionRequest, HttpSession session) throws ServiceException;
    ModelSuggestedAndDimensionsDTO fetchModelSuggestedAndDimensionsGlobal(DimensionRequestDTO dimensionRequest, HttpSession session) throws ServiceException;

    List<DimensionElementNodeDTO> fetchDimensionElementsGlobal(HttpSession session, String dimension, String selectedHierarchies) throws ServiceException;
    List<DimensionElementNodeDTO> fetchDimensionElements(HttpSession session, String dimension, String selectedHierarchies) throws ServiceException;

    List<DimensionElementNodeDTO> fetchExternalHierarchyGlobal(HttpSession session, String dimension, String selectedHierarchies) throws ServiceException;
    List<DimensionElementNodeDTO> fetchExternalHierarchy(HttpSession session, String dimension, String selectedHierarchies) throws ServiceException;

    List<DimensionElementNodeDTO> fetchExternalGroupGlobal(HttpSession session, String dimension, String selectedHierarchies, String groupId) throws ServiceException;
    List<DimensionElementNodeDTO> fetchExternalGroup(HttpSession session, String dimension, String selectedHierarchies, String groupId) throws ServiceException;

    List<DataTypesTreeNodeDTO> fetchDataTypesGlobal(HttpSession session, String firstYear) throws ServiceException;
    List<DataTypesTreeNodeDTO> fetchDataTypes(HttpSession session, String firstYear) throws ServiceException;

    List<DataTypesTreeNodeDTO> fetchDataTypeLevel1Global(HttpSession session, String id) throws ServiceException;
    List<DataTypesTreeNodeDTO> fetchDataTypeLevel1(HttpSession session, String id) throws ServiceException;
    
    List<DataTypesTreeNodeDTO> fetchChildrenGlobal(HttpSession session, String id) throws ServiceException;
    List<DataTypesTreeNodeDTO> fetchChildren(HttpSession session, String id) throws ServiceException;
    
    List<DataTypeDetailsDTO> fetchDataTypes() throws ServiceException;

    ResponseMessage saveMappedModelDetailsGlobal(MappedModelDetailsDTO mappedModelDetails, HttpSession session) throws ServiceException;
    ResponseMessage saveMappedModelDetails(MappedModelDetailsDTO mappedModelDetails, HttpSession session) throws ServiceException;
    
    ResponseMessage updateMappedModelDetailsGlobal(MappedModelDetailsDTO mappedModelDetails, HttpSession session) throws ServiceException;
    ResponseMessage updateMappedModelDetails(MappedModelDetailsDTO mappedModelDetails, HttpSession session) throws ServiceException;
    String getTaskStatus(String taskId);


    

}
