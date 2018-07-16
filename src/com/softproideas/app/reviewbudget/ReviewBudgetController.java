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
package com.softproideas.app.reviewbudget;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.softproideas.commons.context.CPContextHolder;

/**
 * Main controller for Review Budget application.
 * 
 * @author Szymon Walczak
 * @email szymon.walczak@softproideas.com
 * 2014 All rights reserved to Softpro Ideas Group
 */
@Controller
public class ReviewBudgetController {

    @Autowired
    CPContextHolder cpContextHolder;

    /**
     * POST /
     * 
     * Return reviewBudget main page and set the required data for javascript.
     */
    @RequestMapping(value = "/", method = RequestMethod.POST)
    public ModelAndView main( //
            @RequestParam(value = "selectedStructureElementId", required = true) int selectedStructureElementId, //
            @RequestParam(value = "modelId", required = true) int modelId, //
            @RequestParam(value = "budgetCycleId", required = true) int budgetCycleId, //
            @RequestParam(value = "submitModelName", required = true) String submitModelName, //
            @RequestParam(value = "submitCycleName", required = true) String submitCycleName, //
            @RequestParam(value = "profileRef", required = false) String profileRef //
    ) throws Exception {

        // Remove "^(.*) - " from budget cycle name
        submitCycleName = submitCycleName.replaceAll("^(.*) - ", "");

        ModelAndView mainView = new ModelAndView("main");
        mainView.addObject("topNodeId", selectedStructureElementId);
        mainView.addObject("modelId", modelId);
        mainView.addObject("budgetCycleId", budgetCycleId);
        mainView.addObject("submitModelName", submitModelName);
        mainView.addObject("submitCycleName", submitCycleName);
        mainView.addObject("userId", cpContextHolder.getUserId());
        mainView.addObject("userName", cpContextHolder.getUserName());
        mainView.addObject("isAdmin", cpContextHolder.isAdmin());
        mainView.addObject("areButtonsVisible", cpContextHolder.areButtonsVisible());
        mainView.addObject("roadMapAvailable", cpContextHolder.getRoadMapAvailable());

        if (profileRef != null && !profileRef.isEmpty()) {
            Integer dataEntryProfileId = Integer.parseInt(profileRef.substring(profileRef.lastIndexOf('|') + 1));
             mainView.addObject("dataEntryProfileId", dataEntryProfileId);
        }
        return mainView;
    }

}