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
package com.softproideas.app.admin.monitors.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.softproideas.app.admin.monitors.model.TaskDTO;
import com.softproideas.app.admin.monitors.model.TaskDetailsDTO;
import com.softproideas.app.admin.monitors.services.TaskViewerService;
import com.softproideas.commons.model.ResponseMessage;

@RequestMapping(value = "/taskViewer")
@Controller
public class TaskViewerController {

    @Autowired
    TaskViewerService taskViewerService;

    @ResponseBody
        @RequestMapping(value = "/tasks/{page}/{offset}", method = RequestMethod.GET)
        public List<TaskDTO> browseTasks(@PathVariable int page, @PathVariable int offset) throws Exception {
            return taskViewerService.browseTasks(page, offset);
        }
    
    /**
     * Get list of all Tasks.
     */
    @ResponseBody
    @RequestMapping(method = RequestMethod.GET)
    public List<TaskDTO> browseTasks() throws Exception {
        return taskViewerService.browseTasks();
    }

    /**
     * Get details for selected Task.
     */
    @ResponseBody
    @RequestMapping(value = "/{taskId}", method = RequestMethod.GET)
    public TaskDetailsDTO getTaskDetails(@PathVariable int taskId) throws Exception {
        return taskViewerService.fetchTask(taskId);
    }

    /**
     * Delete selected Task.
     */
    @ResponseBody
    @RequestMapping(value = "/delete/{taskId}", method = RequestMethod.DELETE)
    public ResponseMessage deleteTask(@PathVariable int taskId) throws Exception {
        return taskViewerService.deleteTask(taskId);
    }

    /**
     * Restart selected Task.
     */
    @ResponseBody
    @RequestMapping(value = "/restart/{taskId}", method = RequestMethod.PUT)
    public ResponseMessage restartTask(@PathVariable int taskId) throws Exception {
        return taskViewerService.restartTask(taskId);
    }

    /**
     * Mark selected task as failed (unsafe).
     */
    @ResponseBody
    @RequestMapping(value = "/mark/{taskId}", method = RequestMethod.PUT)
    public ResponseMessage failTask(@PathVariable int taskId) throws Exception {
        return taskViewerService.failTaskViewerElement(taskId);
    }

    /**
     * Delete selected Task (unsafe).
     */
    @ResponseBody
    @RequestMapping(value = "/unsafeDelete/{taskId}", method = RequestMethod.PUT)
    public ResponseMessage unsafeDeleteTask(@PathVariable int taskId) throws Exception {
        return taskViewerService.unsafeDeleteTask(taskId);
    }

    /**
     * Wake up despatcher.
     */
    @ResponseBody
    @RequestMapping(value = "/wake", method = RequestMethod.PUT)
    public ResponseMessage wakeUpDespatcherTask() throws Exception {
        return taskViewerService.wakeUpDespatcherTask();
    }

}
