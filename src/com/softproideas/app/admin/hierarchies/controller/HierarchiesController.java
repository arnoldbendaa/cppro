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
/**
 * 
 */
package com.softproideas.app.admin.hierarchies.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.softproideas.app.admin.hierarchies.model.HierarchyDTO;
import com.softproideas.app.admin.hierarchies.model.HierarchyDetailsDTO;
import com.softproideas.app.admin.hierarchies.model.HierarchyNodeLazyDTO;
import com.softproideas.app.admin.hierarchies.service.HierarchiesService;
import com.softproideas.app.admin.structuredisplayname.service.StructureDisplayNameService;
import com.softproideas.app.core.dimension.model.DimensionCoreDTO;
import com.softproideas.commons.model.ResponseMessage;

/**
 * Controller handles requests related to model mappings.
 * Contains CRUD (create, read, update and delete) operations,
 * fetch all available dimensions for create new hierarchy
 * and browse Node
 *
 * @author Szymon Zberaz, Paweł Marzęda
 * @email pawma@softproideas.com
 * <p>2015 All rights reserved to IT Services Jacek Kurasiewicz</p>
 */
@RequestMapping(value = "/hierarchies")
@Controller
public class HierarchiesController {

    @Autowired
    HierarchiesService hierarchiesService;
    
    @Autowired
    StructureDisplayNameService structureDisplayNameService;

    /**
     * Get all Hierarchies from database
     */
    @ResponseBody
    @RequestMapping(value = "/{type}", method = RequestMethod.GET)
    public List<HierarchyDTO> browseHierarchyAccount(@PathVariable boolean type) throws Exception {
        return hierarchiesService.browseHierarchy(type);
    }

    /**
     * Save hierarchies details
     */
    @ResponseBody
    @RequestMapping(value = "/{type}", method = RequestMethod.PUT)
    public ResponseMessage updateAccount(@PathVariable boolean type, @RequestBody HierarchyDetailsDTO hierarchy) throws Exception {
        ResponseMessage responseMessage = new ResponseMessage(true);
        if(type == false) {
            if(!hierarchy.getDisplayNameEvents().isEmpty()) { // Display Name Events is incompatible with old events and is support by different module.
                responseMessage = structureDisplayNameService.saveDisplayNames(hierarchy.getDisplayNameEvents());
            }
            if(responseMessage.isSuccess()) {
                responseMessage = hierarchiesService.save(hierarchy, type);
            }
        } else {
            responseMessage = hierarchiesService.save(hierarchy, type);
        }
        return responseMessage;
    }

    @ResponseBody
    @RequestMapping(value = "/{type}", method = RequestMethod.POST)
    public ResponseMessage createAccount(@PathVariable boolean type, @RequestBody HierarchyDetailsDTO hierarchy) throws Exception {

        ResponseMessage responseMessage = hierarchiesService.save(hierarchy, type);
        return responseMessage;
    }

    /**
     * Get all hierarchies details from database
     */
    @ResponseBody
    @RequestMapping(value = "/{type}/{dimensionId}/{hierarchyId}", method = RequestMethod.GET)
    public HierarchyDetailsDTO fetchHierarchyAccountDetails(@PathVariable boolean type, @PathVariable Integer dimensionId, @PathVariable Integer hierarchyId) throws Exception {
        return hierarchiesService.fetchHierarchyDetails(dimensionId, hierarchyId, type);
    }

    /**
    * Delete user from database
    */
    @ResponseBody
    @RequestMapping(value = "/{type}/{dimensionId}/{hierarchyId}", method = RequestMethod.DELETE)
    public ResponseMessage delete(@PathVariable boolean type, @PathVariable Integer dimensionId, @PathVariable Integer hierarchyId) throws Exception {
        ResponseMessage responseMessage = hierarchiesService.delete(dimensionId, hierarchyId);
        return responseMessage;
    }

    /**
     * Get Node for hierarchies details
     */
    @ResponseBody
    @RequestMapping(value = "/{dimensionId}/{hierarchyId}/hierarchy/{type}", method = RequestMethod.GET)
    public List<HierarchyNodeLazyDTO> browseHierarchyNode(@PathVariable Integer dimensionId, @PathVariable Integer hierarchyId, @PathVariable boolean type, @RequestParam(value = "id", required = true) String id) throws Exception {
        List<HierarchyNodeLazyDTO> listStructureElement = null;
        if (id.equals("1")) {// when we ask for root
            listStructureElement = hierarchiesService.browseHierarchysNode(dimensionId, hierarchyId, type);
            if (type == false) { // when type is business root have Display Name and now this property is included.
                listStructureElement = structureDisplayNameService.includeDisplayNames(listStructureElement, hierarchyId, 0);
            }
            return listStructureElement;
        } else if (id.startsWith("hierarchyElement")) {
            String[] hierarchyParam = id.split("/");
            Integer hierarchyElementId = Integer.parseInt(hierarchyParam[1]);
            listStructureElement = hierarchiesService.browseRoot(dimensionId, hierarchyId, hierarchyElementId, type);
            if (type == false) { // when type is business some elements have Display Name and now this property is included.
                listStructureElement = structureDisplayNameService.includeDisplayNames(listStructureElement, hierarchyId, hierarchyElementId);
            }
            return listStructureElement;
        }

        return browseHierarchyNode(dimensionId, hierarchyId, type, id);

    }

    /**
     * Get all available hierarchies for create new hierarchies
     */
    @ResponseBody
    @RequestMapping(value = "/availableDimensionForInsert/{type}", method = RequestMethod.GET)
    public List<DimensionCoreDTO> fetchAvailableDimension(@PathVariable boolean type) throws Exception {
        return hierarchiesService.fetchAvailableDimension(type);
    }
}
