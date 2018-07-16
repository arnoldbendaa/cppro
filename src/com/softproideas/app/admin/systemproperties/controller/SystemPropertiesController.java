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
package com.softproideas.app.admin.systemproperties.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.softproideas.app.admin.systemproperties.model.SystemPropertyDTO;
import com.softproideas.app.admin.systemproperties.model.SystemPropertyDetailsDTO;
import com.softproideas.app.admin.systemproperties.services.SystemPropertiesService;
import com.softproideas.commons.model.ResponseMessage;

@RequestMapping(value = "/systemProperties")
@Controller
public class SystemPropertiesController {

    @Autowired
    SystemPropertiesService systemPropertiesService;

    /**
     * Get all system properties from database.
     */
    @ResponseBody
    @RequestMapping(method = RequestMethod.GET)
    public List<SystemPropertyDTO> browseSystemProperties() throws Exception {
        return systemPropertiesService.browseSystemProperties();
    }

    /**
     * Get details for selected system property from database.
     */
    @ResponseBody
    @RequestMapping(value = "/{systemPropertyId}", method = RequestMethod.GET)
    public SystemPropertyDetailsDTO getSystemPropertiesDetails(@PathVariable int systemPropertyId) throws Exception {
        return systemPropertiesService.fetchSystemPropertyDetails(systemPropertyId);
    }

    /**
     * Update details for selected system property database.
     */
    @ResponseBody
    @RequestMapping(value = "/{systemPropertyId}", method = RequestMethod.PUT)
    public ResponseMessage saveSystemPropertiesDetails(@PathVariable int systemPropertyId, @Valid @RequestBody SystemPropertyDetailsDTO systemPropertyDetailsDTO) throws Exception {
        ResponseMessage responseMessage = systemPropertiesService.saveSystemPropertyDetails(systemPropertyId, systemPropertyDetailsDTO);
        return responseMessage;
    }

}
