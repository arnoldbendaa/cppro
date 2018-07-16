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
package com.softproideas.app.admin.formdashboard.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.softproideas.app.admin.formdashboard.service.FormDashboardService;
import com.softproideas.app.admin.profiles.model.ProfileDTO;
import com.softproideas.common.models.FormDashboardDTO;

/**
 * @author Jakub Piskorz
 * @email jakub.piskorz@softproideas.com
 * <p>2015 All rights reserved to IT Services Jacek Kurasiewicz</p>
 */

@RequestMapping(value = "/formdashboard")
@Controller
public class FormDashboardController {
    
    private static Logger logger = LoggerFactory.getLogger(FormDashboardController.class);
    
    @Autowired
    private FormDashboardService formDashboardService;
    
    
    
    /**
     * Get  hierarchy from database.
     */
    @ResponseBody
    @RequestMapping(value = "/hierarchy", method = RequestMethod.GET)
    public List<FormDashboardDTO> browseDashboardHierarchies() throws Exception {
        try {
            List<FormDashboardDTO> hierarchies = formDashboardService.browseDashboardHierarchies();
            return hierarchies;
        } catch (Exception e) {
            logger.error("Error during fetching profiles!", e);
            throw new Exception("Error during fetching profiles!", e);
        }
    }
    
    /**
     * Get  free form from database.
     */
    @ResponseBody
    @RequestMapping(value = "/freeform", method = RequestMethod.GET)
    public List<FormDashboardDTO> browseDashboardFreeForms() throws Exception {
        try {
            List<FormDashboardDTO> freeForms = formDashboardService.browseDashboardFreeForms();
            return freeForms;
        } catch (Exception e) {
            logger.error("Error during fetching free Forms!", e);
            throw new Exception("Error during fetching free Forms!", e);
        }
    }

}
