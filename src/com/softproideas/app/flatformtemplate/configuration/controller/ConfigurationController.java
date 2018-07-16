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
package com.softproideas.app.flatformtemplate.configuration.controller;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.softproideas.app.flatformtemplate.configuration.model.ConfigurationDetailsDTO;
import com.softproideas.app.flatformtemplate.configuration.model.ConfigurationTreeDTO;
import com.softproideas.app.flatformtemplate.configuration.model.MoveEvent;
import com.softproideas.app.flatformtemplate.configuration.service.ConfigurationService;
import com.softproideas.commons.model.ResponseMessage;

@RequestMapping(value = "/configurations")
@Controller
public class ConfigurationController {
    
    @Autowired
    ConfigurationService configurationService;

    /**
     * Get tree Configurations and its directories.
     */
    @ResponseBody
    @RequestMapping(method = RequestMethod.GET)
    public ConfigurationTreeDTO browseConfigurations() throws Exception {
        return configurationService.browseConfigurations(false);
    }

    /**
     * Get tree Configurations and its directories for generate.
     */
    @ResponseBody
    @RequestMapping(value = "/generate", method = RequestMethod.GET)
    public ConfigurationTreeDTO browseConfigurationsForGenerate() throws Exception {
        return configurationService.browseConfigurations(true);
    }

    /**
     * Get details of Configuration.
     */
    @ResponseBody
    @RequestMapping(value = "/{configurationUUID}", method = RequestMethod.GET)
    public ConfigurationDetailsDTO fetchConfiguration(@PathVariable UUID configurationUUID) throws Exception {
        return configurationService.fetchConfiguration(configurationUUID);
    }

    /**
     * Insert Configuration.
     */
    @ResponseBody
    @RequestMapping(method = RequestMethod.POST)
    public ConfigurationTreeDTO insertConfiguration(@RequestBody ConfigurationTreeDTO configuration) throws Exception {
        ConfigurationTreeDTO returnedconfiguration = configurationService.insertConfiguration(configuration);
        return returnedconfiguration;
    }

    /**
     * Update details for selected Configuration.
     */
    @ResponseBody
    @RequestMapping(value = "/{configurationUUID}", method = RequestMethod.PUT)
    public ResponseMessage updateConfiguration(@PathVariable UUID configurationUUID, @RequestBody ConfigurationDetailsDTO configuration) throws Exception {
        ResponseMessage responseMessage = configurationService.updateConfiguration(configuration);
        return responseMessage;
    }
    
    @ResponseBody
    @RequestMapping(value = "/index", method = RequestMethod.PUT)
    public ResponseMessage updateConfigurationIndex(@RequestBody MoveEvent moveEvent) throws Exception {
        ResponseMessage responseMessage = configurationService.updateConfigurationIndex(moveEvent);
        return responseMessage;
    }

    /**
     * Rename Configuration or Configuration's directory.
     */
    @ResponseBody
    @RequestMapping(value = "/{configurationUUID}/rename", method = RequestMethod.PUT)
    public ResponseMessage updateConfigurationName(@PathVariable UUID configurationUUID, @RequestBody ConfigurationTreeDTO configuration) throws Exception {
        ResponseMessage responseMessage = configurationService.updateConfigurationName(configurationUUID, configuration.getConfigurationVisId(), configuration.getVersionNum());
        return responseMessage;
    }

    /**
     * Delete selected Configuration or Configuration's directory.
     */
    @ResponseBody
    @RequestMapping(value = "/{configurationUUID}", method = RequestMethod.DELETE)
    public ResponseMessage deleteConfiguration(@PathVariable UUID configurationUUID) throws Exception {
        ResponseMessage responseMessage = configurationService.deleteConfiguration(configurationUUID);
        return responseMessage;
    }

}
