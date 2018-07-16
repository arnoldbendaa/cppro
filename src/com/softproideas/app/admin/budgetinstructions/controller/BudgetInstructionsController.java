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
package com.softproideas.app.admin.budgetinstructions.controller;

import java.beans.PropertyEditorSupport;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.softproideas.app.admin.budgetinstructions.model.BudgetInstructionAssignmentsNodeDTO;
import com.softproideas.app.admin.budgetinstructions.model.BudgetInstructionDTO;
import com.softproideas.app.admin.budgetinstructions.model.BudgetInstructionDetailsDTO;
import com.softproideas.app.admin.budgetinstructions.service.BudgetInstructionsService;
import com.softproideas.commons.model.ResponseMessage;
import com.softproideas.commons.model.tree.NodeLazyDTO;

@RequestMapping(value = "/budgetInstructions")
@Controller
public class BudgetInstructionsController {

    @Autowired
    BudgetInstructionsService budgetInstructionsService;

    /**
     * Get all budget instruction from database.
     */
    @ResponseBody
    @RequestMapping(method = RequestMethod.GET)
    public List<BudgetInstructionDTO> browseBudgetInstructions() throws Exception {
        return budgetInstructionsService.browseBudgetInstructions();
    }

    /**
     * Get all details.
     */
    @ResponseBody
    @RequestMapping(value = "/{budgetInstructionsId}", method = RequestMethod.GET)
    public BudgetInstructionDetailsDTO fetchBudgetInstructionDetailsDTO(@PathVariable int budgetInstructionsId) throws Exception {
        return budgetInstructionsService.fetchBudgetInstructionDetails(budgetInstructionsId);
    }

    /**
     * Save budget instruction details.
     */
    @ResponseBody
    @RequestMapping(value = "/{budgetInstructionsId}", method = RequestMethod.POST)
    public ResponseMessage save(@PathVariable int budgetInstructionsId, @Valid @ModelAttribute("upload") BudgetInstructionDetailsDTO budgetInstruction) throws Exception {
        ResponseMessage responseMessage = budgetInstructionsService.save(budgetInstruction);
        return responseMessage;
    }

    /**
     * Delete budget instruction.
     */
    @ResponseBody
    @RequestMapping(value = "/{budgetInstructionsId}", method = RequestMethod.DELETE)
    public ResponseMessage delete(@PathVariable int budgetInstructionsId) throws Exception {
        ResponseMessage responseMessage = budgetInstructionsService.delete(budgetInstructionsId);
        return responseMessage;
    }

    /**
     * Show lazy node, for budget instruction details (Assignment).
     */
    @ResponseBody
    @RequestMapping(value = "/{budgetInstructionsId}/assignments", method = RequestMethod.GET)
    public List<NodeLazyDTO> browseBudgetInstructionAssignmentsNode(@PathVariable int budgetInstructionsId, @RequestParam(value = "id", required = true) String id) throws Exception {
        if (id.equals("1")) {// when we ask for root
            return budgetInstructionsService.browseSecurityModelRoot(budgetInstructionsId);
        } else if (id.startsWith("modelId")) {
            String[] modelParams = id.split("/");
            Integer modelId = Integer.parseInt(modelParams[1]);
            return budgetInstructionsService.browseRoot(budgetInstructionsId, modelId);
        } else if (id.startsWith("budgetCycles")) {
            String[] modelParams = id.split("/");
            Integer modelId = Integer.parseInt(modelParams[1]);
            return budgetInstructionsService.browseBudgetCycles(budgetInstructionsId, modelId);
        } else if (id.startsWith("respAreas")) {
            String[] modelParams = id.split("/");
            Integer modelId = Integer.parseInt(modelParams[1]);
            return budgetInstructionsService.browseResponsibilityAreasModels(budgetInstructionsId, modelId);
        } else if (id.startsWith("structureId")) {
            String[] structureParams = id.split("/");
            Integer structureId = Integer.parseInt(structureParams[1]);
            Integer structureElementId = Integer.parseInt(structureParams[3]);
            return budgetInstructionsService.browseSecurityStructureElement(budgetInstructionsId, structureId, structureElementId);
        }
        return browseBudgetInstructionAssignmentsNode(budgetInstructionsId, id);

    }

    /**
     * Help with parsing json assignments list.
     */
    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(List.class, "assignments", new PropertyEditorSupport() {
            @Override
            public void setAsText(String text) {
                ObjectMapper mapper = new ObjectMapper();
                try {
                    List<BudgetInstructionAssignmentsNodeDTO> list = mapper.readValue(text, TypeFactory.defaultInstance().constructCollectionType(List.class, BudgetInstructionAssignmentsNodeDTO.class));
                    setValue(list);
                } catch (Exception e) {
                   e.printStackTrace();
                }
            }
        });
    }
}
