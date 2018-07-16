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
package com.softproideas.app.admin.tidytask.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.softproideas.app.admin.tidytask.model.TidyTaskDTO;
import com.softproideas.app.admin.tidytask.service.TidyTaskService;

/**
 * <p>Spring MVC controller responsible for handling requests from web browser based user interface.
 * Controller is available in our admin panel at the url <em>/adminPanel/#/admin/database/</em>.</p>
 * 
 * @author Łukasz Puła
 * @email lukasz.pula@softproideas.com
 * <p>2014 All rights reserved to Softpro Ideas Group</p>
 */
@RequestMapping(value = "/tidyTasks")
@Controller
public class TidyTaskController {

    @Autowired
    TidyTaskService tidyTaskService;

    /**
     * Retrieves all available tidy task
     */
    @ResponseBody
    @RequestMapping(method = RequestMethod.GET)
    public List<TidyTaskDTO> browseTidyTasks() {
        return tidyTaskService.browseTidyTasks();
    }

    /**
     * Retrieves all details for specified tidy task.
     */
    @ResponseBody
    @RequestMapping(value = "/{tidyTaskId}", method = RequestMethod.GET)
    public TidyTaskDTO fetchTidyTask(@PathVariable int tidyTaskId) throws Exception {
        return tidyTaskService.fetchTidyTask(tidyTaskId);
    }

    /**
     * Submits task with specific commands stored in details of this task.
     * @param tidyTask - task to submit
     * @return index (not id) of submitted task 
     */
    @ResponseBody
    @RequestMapping(value = "/submit", method = RequestMethod.POST)
    public int submitTidyTask(@Valid @RequestBody TidyTaskDTO tidyTask) throws Exception {
        return tidyTaskService.submit(tidyTask);
    }

}