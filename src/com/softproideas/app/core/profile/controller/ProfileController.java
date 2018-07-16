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
package com.softproideas.app.core.profile.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.softproideas.app.core.form.model.FormDTO;
import com.softproideas.app.core.profile.model.ProfileDTO;
import com.softproideas.app.core.profile.service.ProfileService;

/**
 * Class manages (CRUD methods) profiles which are available for user.
 * 
 * @author Łukasz Puła
 * @email lukasz.pula@softproideas.com
 * 2014 All rights reserved to Softpro Ideas Group
 */
@Controller
public class ProfileController {

    @Autowired
    ProfileService profileService;

    /**
     * GET /profiles/model/{modelId}/budgetCycle/{budgetCycleId}/topNode/{topNodeId}
     * 
     * Method returns profiles for Excel document. If user hasn't got any profiles, profiles will be created according to xcell forms
     */
    @RequestMapping(value = "/profiles/model/{modelId}/budgetCycle/{budgetCycleId}/topNode/{topNodeId}", method = RequestMethod.GET)
    public @ResponseBody
    List<ProfileDTO> fetchProfiles( //
            @PathVariable int modelId, //
            @PathVariable int budgetCycleId, //
            @PathVariable int topNodeId //
    ) throws Exception {
        return profileService.browseProfiles(modelId, budgetCycleId, topNodeId);
    }

    /**
     * GET /formlist/{modelId}/{budgetCycleId}
     * 
     * Method returns list of xcell forms available for modelId and budgetCycleId
     */
    @RequestMapping(value = "/formlist/{modelId}/{budgetCycleId}", method = RequestMethod.GET)
    public @ResponseBody
    List<FormDTO> fetchFormList( //
            @PathVariable int modelId, //
            @PathVariable int budgetCycleId //
    ) throws Exception {
        return profileService.browseFormList(modelId, budgetCycleId);
    }

}