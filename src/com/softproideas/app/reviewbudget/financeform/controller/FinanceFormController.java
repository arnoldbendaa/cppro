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
package com.softproideas.app.reviewbudget.financeform.controller;

import java.util.HashMap;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.softproideas.app.reviewbudget.budget.model.ReviewBudgetDTO;
import com.softproideas.app.reviewbudget.budget.service.BudgetService;

@Controller
public class FinanceFormController {

    @Autowired
    BudgetService budgetService;

    @RequestMapping(value = "/financeforms/fetch", method = RequestMethod.POST)
    public ReviewBudgetDTO fetchFinanceForms(
            @RequestParam(value = "topNodeId", required = true) int topNodeId, 
            @RequestParam(value = "modelId", required = true) int modelId, 
            @RequestParam(value = "budgetCycleId", required = true) int budgetCycleId, 
            @RequestParam(value = "dataEntryProfileId", required = true) int dataEntryProfileId, 
            @RequestParam(value = "dim0", required = false) int dim0, 
            @RequestParam(value = "dim1", required = false) int dim1,
            @RequestParam(value = "dim2", required = false) int dim2, 
            @RequestParam(value = "dataType", required = false) String dataType, 
            HttpServletResponse response) throws Exception {

        HashMap<Integer, Integer> selectionsMap = new HashMap<Integer, Integer>();
        if ((dim0 != 0) && (dim1 != 0) && (dim2 != 0)) {
            selectionsMap.put(new Integer(0), new Integer(dim0));
            selectionsMap.put(new Integer(1), new Integer(dim1));
            selectionsMap.put(new Integer(2), new Integer(dim2));
        }

        return budgetService.fetchReviewBudget(topNodeId, modelId, budgetCycleId, dataEntryProfileId, selectionsMap, dataType);
    }

}
