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
package com.softproideas.app.admin.recalculatebatches.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.softproideas.app.admin.recalculatebatches.model.RecalculateBatchDTO;
import com.softproideas.app.admin.recalculatebatches.model.RecalculateBatchDetailsDTO;
import com.softproideas.app.admin.recalculatebatches.model.RecalculateBatchProfileDTO;
import com.softproideas.app.admin.recalculatebatches.service.RecalculateBatchesService;
import com.softproideas.commons.model.ResponseMessage;
import com.softproideas.commons.model.tree.NodeLazyDTO;

/**
 * <p>Spring MVC controller responsible for handling requests from web browser based user interface.
 * Controller is available in our admin panel at the url <em>/adminPanel/#/batch-recalculate/</em>.</p> 
 * 
 * @author Łukasz Puła
 * @email lukasz.pula@softproideas.com
 * <p>2014 All rights reserved to Softpro Ideas Group</p>
 */
@RequestMapping(value = "/recalculateBatches")
@Controller
public class RecalculateBatchesController {

    @Autowired
    RecalculateBatchesService recalculateBatchesService;

    /**
     * Retrieves all recalculate batches for logged user
     */
    @ResponseBody
    @RequestMapping(method = RequestMethod.GET)
    public List<RecalculateBatchDTO> browseAllRecalculateBatchTasks() throws Exception {
        return recalculateBatchesService.browseAllRecalculateBatchTasks();
    }

    /**
     * Method inserts new recalculate batch to database. 
     * If any validation error occurs, in {@link ResponseMessage} we have list of fields we are incorrect. 
     */
    @ResponseBody
    @RequestMapping(method = RequestMethod.POST)
    public ResponseMessage insert(@RequestBody RecalculateBatchDetailsDTO recalculateBatch) throws Exception {
        return recalculateBatchesService.save(recalculateBatch);
    }

    /**
     * Method updates recalculate batch in database. 
     * If any validation error occurs, in {@link ResponseMessage} we have list of fields we are incorrect. 
     */
    @ResponseBody
    @RequestMapping(method = RequestMethod.PUT)
    public ResponseMessage update(@RequestBody RecalculateBatchDetailsDTO recalculateBatch) throws Exception {
        return recalculateBatchesService.save(recalculateBatch);
    }

    /**
     * Retrieves all details for recalculate.
     */
    @ResponseBody
    @RequestMapping(value = "/{recalculateBatchId}", method = RequestMethod.GET)
    public RecalculateBatchDetailsDTO fetchRecalculateBatch(@PathVariable int recalculateBatchId) throws Exception {
        return recalculateBatchesService.fetchRecalculateBatch(recalculateBatchId);
    }

    /**
     * Retrieves information about responsibility areas for recalculate batch. ModelId is necessary for retrieving root element.
     * Id string contains information related to structureId and structureElementId and it required to retrieve information about children for
     * element with strutureId and structureElementId
     * @param id        merged strutureId and structureElementId
     * @return list of responsibility areas
     */
    @ResponseBody
    @RequestMapping(value = "/{recalculateBatchId}/models/{modelId}/responsibilityAreas/", method = RequestMethod.GET)
    public List<NodeLazyDTO> browseResponsiblityAreaChildren(@PathVariable int recalculateBatchId, @PathVariable int modelId, @RequestParam(value = "id", required = true) String id) throws Exception {
        if (id.equals("1")) { // when we ask for root
            return recalculateBatchesService.browseResponsibilityAreaRoot(recalculateBatchId, modelId);
        } else { // when we ask for children of structureElement with id
            String[] ids = id.split("/");
            return recalculateBatchesService.browseResponsibilityAreaChildren(recalculateBatchId, Integer.parseInt(ids[0]), Integer.parseInt(ids[1]));
        }
    }

    /**
     * Retrieves information about profiles to recalculate which are saved in 'recalculate batch'. 
     * List depends on modelId and budgetCycleId.
     */
    @ResponseBody
    @RequestMapping(value = "/{recalculateBatchId}/models/{modelId}/budgetCycles/{budgetCycleId}/profiles", method = RequestMethod.GET)
    public List<RecalculateBatchProfileDTO> browseRecalculateBatchProfiles(@PathVariable int recalculateBatchId, @PathVariable int modelId, @PathVariable int budgetCycleId) throws Exception {
        return recalculateBatchesService.browseRecalculateBatchProfiles(recalculateBatchId, modelId, budgetCycleId);
    }

    /**
     * Method deletes recalculate batch.
     */
    @ResponseBody
    @RequestMapping(value = "/{recalculateBatchId}", method = RequestMethod.DELETE)
    public ResponseMessage delete(@PathVariable int recalculateBatchId) throws Exception {
        return recalculateBatchesService.delete(recalculateBatchId);
    }

    /**
     * Invokes task which recalculate all profiles selected in recalculate batch.
     * @return id of invoked task
     */
    @ResponseBody
    @RequestMapping(value = "/submit", method = RequestMethod.POST)
    public int submitBatchRecalculate(@Valid @RequestBody RecalculateBatchDetailsDTO recalculateBatch) throws Exception {
        return recalculateBatchesService.issueRecalculateBatchTask(recalculateBatch);
    }
}