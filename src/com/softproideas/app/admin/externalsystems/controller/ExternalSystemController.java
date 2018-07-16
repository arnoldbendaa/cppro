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
package com.softproideas.app.admin.externalsystems.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.softproideas.app.admin.externalsystems.model.ExternalSystemDTO;
import com.softproideas.app.admin.externalsystems.model.ExternalSystemDetailsDTO;
import com.softproideas.app.admin.externalsystems.model.SubSystemConfigurationDTO;
import com.softproideas.app.admin.externalsystems.service.ExternalSystemService;
import com.softproideas.commons.model.ResponseMessage;

/**
 * Controller handles requests related to external systems.
 * Contains CRUD (create, read, update and delete) operations.
 *
 * @author Jarosław Kaczmarski
 * @email jarok@softproideas.com
 * <p>2014 All rights reserved to IT Services Jacek Kurasiewicz</p>
 */
@RequestMapping(value = "/externalSystems")
@Controller
public class ExternalSystemController {

    @Autowired
    ExternalSystemService externalSystemService;

    /**
     * GET /externalSystems
     * 
     * Method return list of all external systems.
     */
    @ResponseBody
    @RequestMapping(method = RequestMethod.GET)
    public List<ExternalSystemDTO> browseExternalSystems() throws Exception {
        List<ExternalSystemDTO> externalSystems = externalSystemService.browseExternalSystems();
        return externalSystems;
    }

    /**
     * POST /externalSystems
     * 
     * Method insert new external system to database.
     */
    @ResponseBody
    @RequestMapping(method = RequestMethod.POST)
    public ResponseMessage insert(@RequestBody ExternalSystemDetailsDTO externalSystemDetails) throws Exception {
        return externalSystemService.save(externalSystemDetails);
    }

    /**
     * GET /externalSystems/{externalSystemId}
     * 
     * Method return external system details for specified externalSystemId.
     */
    @ResponseBody
    @RequestMapping(value = "/{externalSystemId}", method = RequestMethod.GET)
    public ExternalSystemDetailsDTO fetchExternalSystemDetails(@PathVariable int externalSystemId) throws Exception {
        ExternalSystemDetailsDTO externalSystem = externalSystemService.fetchExternalSystemDetails(externalSystemId);
        return externalSystem;
    }

    /**
     * GET /externalSystems/subSystemConfiguration/{subSystemId}
     * 
     * Method return sub system configuration details for specified subSystemId.
     */
    @ResponseBody
    @RequestMapping(value = "/subSystemConfiguration/{subSystemId}", method = RequestMethod.GET)
    public SubSystemConfigurationDTO fetchSubSystemConfiguration(@PathVariable int subSystemId) throws Exception {
        SubSystemConfigurationDTO subSystemConfiguration = externalSystemService.fetchSubSystemConfiguration(subSystemId);
        return subSystemConfiguration;
    }

    /**
     * PUT /externalSystems/{externalSystemId}
     * 
     * Method update existing external system with given external system details.
     */
    @ResponseBody
    @RequestMapping(value = "/{externalSystemId}", method = RequestMethod.PUT)
    public ResponseMessage update(@PathVariable int externalSystemId, @RequestBody ExternalSystemDetailsDTO externalSystemDetails) throws Exception {
        return externalSystemService.save(externalSystemDetails);
    }

    /**
     * DELETE /externalSystems/{externalSystemId}
     * 
     * Method delete external system with specified externalSystemId from database.
     */
    @ResponseBody
    @RequestMapping(value = "/{externalSystemId}", method = RequestMethod.DELETE)
    public ResponseMessage delete(@PathVariable int externalSystemId) throws Exception {
        return externalSystemService.delete(externalSystemId);
    }
}
