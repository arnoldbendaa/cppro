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
package com.softproideas.app.admin.modelmappings.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.softproideas.app.admin.datatypes.model.DataTypeDetailsDTO;
import com.softproideas.app.admin.dimensions.account.service.AccountService;
import com.softproideas.app.admin.dimensions.business.service.BusinessService;
import com.softproideas.app.admin.dimensions.calendar.model.CalendarDetailsDTO;
import com.softproideas.app.admin.dimensions.calendar.service.CalendarService;
import com.softproideas.app.admin.dimensions.model.DimensionDetailsDTO;
import com.softproideas.app.admin.hierarchies.model.HierarchyDetailsDTO;
import com.softproideas.app.admin.hierarchies.service.HierarchiesService;
import com.softproideas.app.admin.modelmappings.model.CompanyDTO;
import com.softproideas.app.admin.modelmappings.model.DataTypesTreeNodeDTO;
import com.softproideas.app.admin.modelmappings.model.DimensionElementNodeDTO;
import com.softproideas.app.admin.modelmappings.model.DimensionRequestDTO;
import com.softproideas.app.admin.modelmappings.model.ExternalRequestDTO;
import com.softproideas.app.admin.modelmappings.model.LedgerDTO;
import com.softproideas.app.admin.modelmappings.model.LedgersRequestDTO;
import com.softproideas.app.admin.modelmappings.model.ModelSuggestedAndDimensionsDTO;
import com.softproideas.app.admin.modelmappings.model.details.MappedModelDTO;
import com.softproideas.app.admin.modelmappings.model.details.MappedModelDetailsDTO;
import com.softproideas.app.admin.modelmappings.service.ModelMappingsService;
import com.softproideas.app.admin.models.model.ModelDetailsDTO;
import com.softproideas.app.admin.models.service.ModelsService;
import com.softproideas.app.core.model.model.ModelCoreDTO;
import com.softproideas.commons.model.ResponseMessage;

/**
 * Controller handles requests related to model mappings.
 * Contains CRUD (create, read, update and delete) operations
 * and import task.
 *
 * @author Jarosław Kaczmarski
 * @email jarok@softproideas.com
 * <p>2014 All rights reserved to IT Services Jacek Kurasiewicz</p>
 */
@RequestMapping(value = "/modelMappings")
@Controller
public class ModelMappingsController {

    @Autowired
    ModelMappingsService modelMappingsService;
    @Autowired
    ModelsService modelsService;
    @Autowired
    AccountService accountService;
    @Autowired
    BusinessService businessService;
    @Autowired
    CalendarService calendarService;
    @Autowired
    HierarchiesService hierarchiesService;

    /**
     * GET /modelMappings
     * 
     * Method returns list of all mapped models.
     */
    @ResponseBody
    @RequestMapping(method = RequestMethod.GET)
    public List<MappedModelDTO> browseMappedModels() throws Exception {
        List<MappedModelDTO> mappedModels = modelMappingsService.browseMappedModels();
        return mappedModels;
    }

    /**
     * Delete model mapping from database
     */
    @ResponseBody
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public ResponseMessage delete(@Valid @RequestBody MappedModelDTO mappedModel) throws Exception {
        ResponseMessage responseMessage = null;
        if (mappedModel.isGlobal()) {
            responseMessage = modelMappingsService.deleteGlobal(mappedModel.getMappedModelId());
        } else {
            responseMessage = modelMappingsService.delete(mappedModel.getMappedModelId());
        }
        return responseMessage;
    }

    @ResponseBody
    @RequestMapping(value = "/importSafe", method = RequestMethod.POST)
    public int importSafe(@Valid @RequestBody MappedModelDTO mappedModel) throws Exception {
        int taskId = 0;
        if (mappedModel.isGlobal()) {
            taskId = modelMappingsService.issueModelImportTaskGlobal(true, mappedModel.getMappedModelId());
        } else {
            taskId = modelMappingsService.issueModelImportTask(true, mappedModel.getMappedModelId());
        }
        return taskId;
    }

    @ResponseBody
    @RequestMapping(value = "/importNotSafe", method = RequestMethod.POST)
    public int importNotSafe(@Valid @RequestBody MappedModelDTO mappedModel) throws Exception {
        int taskId = 0;
        if (mappedModel.isGlobal()) {
            taskId = modelMappingsService.issueModelImportTaskGlobal(false, mappedModel.getMappedModelId());
        } else {
            taskId = modelMappingsService.issueModelImportTask(false, mappedModel.getMappedModelId());
        }
        return taskId;
    }

    @ResponseBody
    @RequestMapping(method = RequestMethod.POST)
    public ResponseMessage saveMappedModelDetails(@RequestBody MappedModelDetailsDTO mappedModelDetails, HttpServletRequest request) throws Exception {
        HttpSession session = request.getSession();
        ResponseMessage responseMessage = null;
        if (mappedModelDetails.isGlobal()) {
            responseMessage = modelMappingsService.saveMappedModelDetailsGlobal(mappedModelDetails, session);
        } else {
            responseMessage = modelMappingsService.saveMappedModelDetails(mappedModelDetails, session);
        }
        
        
        return responseMessage;
    }

    @ResponseBody
    @RequestMapping(method = RequestMethod.PUT)
    public ResponseMessage updateMappedModelDetails(@RequestBody MappedModelDetailsDTO mappedModelDetails, HttpServletRequest request) throws Exception {
        HttpSession session = request.getSession();
        ResponseMessage responseMessage = null;
        if (mappedModelDetails.isGlobal()) {
            responseMessage = modelMappingsService.updateMappedModelDetailsGlobal(mappedModelDetails, session);
        } else {
            responseMessage = modelMappingsService.updateMappedModelDetails(mappedModelDetails, session);
        }
        return responseMessage;
    }

    /**
     * GET /modelMappings/{mappedModelId}
     * 
     * Method returns mapped model details for specified mappedModelId.
     */
    @ResponseBody
    @RequestMapping(value = "/{mappedModelId}", method = RequestMethod.GET)
    public MappedModelDetailsDTO fetchMappedModelDetails(@PathVariable int mappedModelId) throws Exception {
        MappedModelDetailsDTO mappedModelDetails = modelMappingsService.fetchMappedModelDetails(mappedModelId);
        return mappedModelDetails;
    }

    /**
     * POST /modelMappings/externalCompanies
     * 
     * Method returns external companies for given externam system.
     */
    @ResponseBody
    @RequestMapping(value = "/externalCompanies", method = RequestMethod.POST)
    public List<CompanyDTO> browseExternalCompanies(@RequestBody ExternalRequestDTO externalRequest) throws Exception {
        List<CompanyDTO> companies = modelMappingsService.browseExternalCompanies(externalRequest);
        return companies;
    }

    /**
    * POST /modelMappings/externalCompanies
    *
    * Method returns external ledgers for given selected companies.
    */
    @ResponseBody
    @RequestMapping(value = "/externalLedgers", method = RequestMethod.POST)
    public List<LedgerDTO> browseExternalLedgers(@RequestBody LedgersRequestDTO ledgerRequest) throws Exception {
        List<LedgerDTO> ledgers = modelMappingsService.browseExternalLedgersGlobal(ledgerRequest);
        return ledgers;
    }

    /**
     * POST /modelMappings/externalDimensions
     * 
     * Method returns external companies for given external system.
     */
    @ResponseBody
    @RequestMapping(value = "/externalDimensions", method = RequestMethod.POST)
    public ModelSuggestedAndDimensionsDTO fetchModelSuggestedAndDimensions(@RequestBody DimensionRequestDTO dimensionRequest, HttpServletRequest request) throws Exception {
        HttpSession session = request.getSession();
        ModelSuggestedAndDimensionsDTO modelAndDims = dimensionRequest.isGlobal() ? modelMappingsService.fetchModelSuggestedAndDimensionsGlobal(dimensionRequest, session) : modelMappingsService.fetchModelSuggestedAndDimensions(dimensionRequest, session);
        return modelAndDims;
    }

    /**
     * POST /modelMappings/externalElementsGlobal
     * 
     * Method returns external companies for given external system.
     */
    @ResponseBody
    @RequestMapping(value = "/externalElementsGlobal/{dimension}/{selectedHierarchies}", method = RequestMethod.GET)
    public List<DimensionElementNodeDTO> fetchDimensionElementsGlobal(@PathVariable String dimension, @PathVariable String selectedHierarchies, @RequestParam(value = "id", required = true) String id, HttpServletRequest request) throws Exception {
        HttpSession session = request.getSession();
        if (id.startsWith("1")) {
            List<DimensionElementNodeDTO> dimElement = modelMappingsService.fetchDimensionElementsGlobal(session, dimension, selectedHierarchies);
            return dimElement;
        } else if (id.startsWith("dimension")) {
            List<DimensionElementNodeDTO> dimElement = modelMappingsService.fetchExternalHierarchyGlobal(session, dimension, selectedHierarchies);
            return dimElement;
        } else if (id.startsWith("groupId")) {
            List<DimensionElementNodeDTO> dimElement = modelMappingsService.fetchExternalGroupGlobal(session, dimension, selectedHierarchies, id);
            return dimElement;
        } else if (id.startsWith("hierarchyId")) {
            List<DimensionElementNodeDTO> dimElement = modelMappingsService.fetchExternalGroupGlobal(session, dimension, selectedHierarchies, id);
            return dimElement;
        }
        return new ArrayList<DimensionElementNodeDTO>();
    }

    /**
     * POST /modelMappings/externalElements
     * 
     * Method returns external companies for given external system.
     */
    @ResponseBody
    @RequestMapping(value = "/externalElements/{dimension}/{selectedHierarchies}", method = RequestMethod.GET)
    public List<DimensionElementNodeDTO> fetchDimensionElements(@PathVariable String dimension, @PathVariable String selectedHierarchies, @RequestParam(value = "id", required = true) String id, HttpServletRequest request) throws Exception {
        HttpSession session = request.getSession();
        if (id.startsWith("1")) {
            List<DimensionElementNodeDTO> dimElement = modelMappingsService.fetchDimensionElements(session, dimension, selectedHierarchies);
            return dimElement;
        } else if (id.startsWith("dimension")) {
            List<DimensionElementNodeDTO> dimElement = modelMappingsService.fetchExternalHierarchy(session, dimension, selectedHierarchies);
            return dimElement;
        } else if (id.startsWith("groupId")) {
            List<DimensionElementNodeDTO> dimElement = modelMappingsService.fetchExternalGroup(session, dimension, selectedHierarchies, id);
            return dimElement;
        } else if (id.startsWith("hierarchyId")) {
            List<DimensionElementNodeDTO> dimElement = modelMappingsService.fetchExternalGroup(session, dimension, selectedHierarchies, id);
            return dimElement;
        }
        return new ArrayList<DimensionElementNodeDTO>();
    }

    @ResponseBody
    @RequestMapping(value = "/dataTypes", method = RequestMethod.GET)
    public List<DataTypeDetailsDTO> fetchDataTypesDetails() throws Exception {
        List<DataTypeDetailsDTO> modelAndDims = modelMappingsService.fetchDataTypes();
        return modelAndDims;
    }

    @ResponseBody
    @RequestMapping(value = "/externalDataTypesGlobal/{firstYear}", method = RequestMethod.GET)
    public List<DataTypesTreeNodeDTO> fetchDataTypesTreeNodeGlobal(@PathVariable String firstYear, @RequestParam(value = "id", required = true) String id, HttpServletRequest request) throws Exception {
        HttpSession session = request.getSession();
        if (id.startsWith("1")) {
            List<DataTypesTreeNodeDTO> dataType = modelMappingsService.fetchDataTypesGlobal(session, firstYear);
            return dataType;
        } else if (id.startsWith("dataTypeLevel1")) {
            List<DataTypesTreeNodeDTO> dataType = modelMappingsService.fetchDataTypeLevel1Global(session, id);
            return dataType;
        } else if (id.startsWith("dataType")) {
            List<DataTypesTreeNodeDTO> dataType = modelMappingsService.fetchChildrenGlobal(session, id);
            return dataType;
        }
        return new ArrayList<DataTypesTreeNodeDTO>();
    }

    @ResponseBody
    @RequestMapping(value = "/externalDataTypes/{firstYear}", method = RequestMethod.GET)
    public List<DataTypesTreeNodeDTO> fetchDataTypesTreeNode(@PathVariable String firstYear, @RequestParam(value = "id", required = true) String id, HttpServletRequest request) throws Exception {
        HttpSession session = request.getSession();
        if (id.startsWith("1")) {
            List<DataTypesTreeNodeDTO> dataType = modelMappingsService.fetchDataTypes(session, firstYear);
            return dataType;
        } else if (id.startsWith("dataTypeLevel1")) {
            List<DataTypesTreeNodeDTO> dataType = modelMappingsService.fetchDataTypeLevel1(session, id);
            return dataType;
        } else if (id.startsWith("dataType")) {
            List<DataTypesTreeNodeDTO> dataType = modelMappingsService.fetchChildren(session, id);
            return dataType;
        }
        return new ArrayList<DataTypesTreeNodeDTO>();
    }
    @ResponseBody
    @RequestMapping(value = "/getTaskStatus/{taskId}", method = RequestMethod.GET)
    public String getTaskStatus(@PathVariable String taskId){
    	String result = modelMappingsService.getTaskStatus(taskId);
		return result;    	
    }
}
