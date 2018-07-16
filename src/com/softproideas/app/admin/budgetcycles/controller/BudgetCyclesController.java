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
package com.softproideas.app.admin.budgetcycles.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.softproideas.app.admin.budgetcycles.model.BudgetCycleDTO;
import com.softproideas.app.admin.budgetcycles.model.BudgetCycleDetailsDTO;
import com.softproideas.app.admin.budgetcycles.model.BudgetCycleStructureLevelEndDatesDTO;
import com.softproideas.app.admin.budgetcycles.service.BudgetCyclesService;
import com.softproideas.app.core.flatform.model.FlatFormExtendedCoreDTO;
import com.softproideas.commons.model.ResponseMessage;

//TODO plannedEndDate levels aren't finished

/**
 * <p>Spring MVC controller responsible for handling requests from web browser based user interface.
 * Controller is available in our admin panel at the url <em>/adminPanel/#/budget-cycles/</em>.</p>
 * 
 * @author Łukasz Puła
 * @email lukasz.pula@softproideas.com
 * <p>2014 All rights reserved to Softpro Ideas Group</p>
 */
@RequestMapping(value = "/budgetCycles")
@Controller
public class BudgetCyclesController {

    @Autowired
    BudgetCyclesService budgetCyclesService;

    /**
     * Retrieves all budget cycles for logged user
     */
    @ResponseBody
    @RequestMapping(method = RequestMethod.GET)
    public List<BudgetCycleDTO> browseBudgetCycles() throws Exception {
        return budgetCyclesService.browseBudgetCycles();
    }

    /**
     * Method inserts new budget cycle to database. 
     * If any validation error occurs, in {@link ResponseMessage} we have list of fields we are incorrect. 
     */
    @ResponseBody
    @RequestMapping(method = RequestMethod.POST)
    public ResponseMessage insert(@RequestBody BudgetCycleDetailsDTO budgetCycle) throws Exception {
        return budgetCyclesService.save(budgetCycle);
    }

    /**
     * Method updates budget cycle in database. 
     * If any validation error occurs, in {@link ResponseMessage} we have list of fields we are incorrect. 
     */
    @ResponseBody
    @RequestMapping(method = RequestMethod.PUT)
    public ResponseMessage update(@RequestBody BudgetCycleDetailsDTO budgetCycle) throws Exception {
        return budgetCyclesService.save(budgetCycle);
    }

    /**
     * Retrieves all details for budget cycle. ModelId is necessary to fetch details for budget cycle.
     */
    @ResponseBody
    @RequestMapping(value = "/{budgetCycleId}/models/{modelId}", method = RequestMethod.GET)
    public BudgetCycleDetailsDTO fetchBudgetCycle(@PathVariable int budgetCycleId, @PathVariable int modelId) throws Exception {
        return budgetCyclesService.fetchBudgetCycle(budgetCycleId, modelId);
    }

    /**
     * Retrieves all budget cycles for specified model
     * @return list of {@link BudgetCycleDTO} related to model
     */
    @ResponseBody
    @RequestMapping(value = "/models/{modelId}", method = RequestMethod.GET)
    public List<BudgetCycleDTO> browseBudgetCyclesForModel(@PathVariable int modelId) {
        return budgetCyclesService.browseBudgetCyclesForModel(modelId);
    }

    /**
     * Method delete budget cycle. ModelId is necessary to identify correct budget cycle. 
     */
    @ResponseBody
    @RequestMapping(value = "/{budgetCycleId}/models/{modelId}", method = RequestMethod.DELETE)
    public ResponseMessage delete(@PathVariable int budgetCycleId, @PathVariable int modelId) throws Exception {
        return budgetCyclesService.delete(budgetCycleId, modelId);
    }

    /**
     * Retrieves all xmlForm for specified model.
     */
    @ResponseBody
    @RequestMapping(value = "/xmlforms/{modelId}", method = RequestMethod.GET)
    public List<FlatFormExtendedCoreDTO> browseXmlFormsForModel(@PathVariable int modelId) {
        return budgetCyclesService.browseXmlFormsForModel(modelId);
    }
    
    /**
     * Retrieves all structure level dates for specified model.
     */
    @ResponseBody
    @RequestMapping(value = "/structureLevelDates/", method = RequestMethod.POST)
    public List<Long> browseStructureLevelDatesForModel(@RequestBody BudgetCycleStructureLevelEndDatesDTO budgetCycleStructureLevelEndDatesDTO) {
        return budgetCyclesService.browseStructureLevelDatesForModel(budgetCycleStructureLevelEndDatesDTO);
    }   

    /**
     * Method updates (only periods - period FROM and period TO) budget cycle in database. 
     */
    @ResponseBody
    @RequestMapping(value = "/period/update", method = RequestMethod.POST)
    public ResponseMessage updatePeriodsForBudgetCycle(@RequestBody BudgetCycleDTO budgetCycle) throws Exception {
        return budgetCyclesService.updatePeriodForBudgetCycle(budgetCycle);
    }

    @ResponseBody
    @RequestMapping(value = "/submit/rebuild", method = RequestMethod.POST)
    public int submitBudgetStateRebuild(@Valid @RequestBody BudgetCycleDTO budgetCycle) throws Exception {
        return budgetCyclesService.issueBudgetStateRebuildTask(budgetCycle);
    }

    @ResponseBody
    @RequestMapping(value = "/submit", method = RequestMethod.POST)
    public int submitBudgetState(@Valid @RequestBody BudgetCycleDTO budgetCycle) throws Exception {
        return budgetCyclesService.issueBudgetStateTask(budgetCycle);
    }
}
