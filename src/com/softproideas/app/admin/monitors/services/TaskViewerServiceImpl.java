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
package com.softproideas.app.admin.monitors.services;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cedar.cp.api.base.CPException;
import com.cedar.cp.api.base.EntityList;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.dto.task.TaskDetails;
import com.cedar.cp.dto.task.TaskPK;
import com.cedar.cp.ejb.api.task.TaskProcessServer;
import com.cedar.cp.ejb.impl.task.TaskProcessSEJB;
import com.softproideas.app.admin.monitors.mapper.TaskViewerMapper;
import com.softproideas.app.admin.monitors.model.TaskDTO;
import com.softproideas.app.admin.monitors.model.TaskDetailsDTO;
import com.softproideas.common.exceptions.ServiceException;
import com.softproideas.commons.context.CPContextHolder;
import com.softproideas.commons.model.ResponseMessage;
import com.softproideas.commons.model.error.ValidationError;

@Service("taskViewerService")
public class TaskViewerServiceImpl implements TaskViewerService {

    private static Logger logger = LoggerFactory.getLogger(TaskViewerServiceImpl.class);

    @Autowired
    CPContextHolder cpContextHolder;
    public static TaskProcessSEJB server = new TaskProcessSEJB();

    @Override
    public List<TaskDTO> browseTasks(int page, int offset) throws ServiceException {
        EntityList taskList = null;
        try {
//            taskList = cpContextHolder.getTaskProcessServer().getPageTasks(page, offset);
            taskList = server.getPageTasks(page, offset);
        } catch (CPException e) {
            logger.error("Error during get Task Viewer list!", e);
            throw new ServiceException("Error during get Task Viewer list!", e);
        }
        return TaskViewerMapper.mapTaskEntityList(taskList);
    }

    /**
     * Get list of all Tasks.
     */
    @Override
    public List<TaskDTO> browseTasks() throws ServiceException {
        EntityList taskList = null;
        try {
            taskList = cpContextHolder.getTaskProcessServer().getAllTasks();
        } catch (CPException e) {
            logger.error("Error during get Task Viewer list!", e);
            throw new ServiceException("Error during get Task Viewer list!", e);
        } catch (ValidationException e) {
            logger.error("Error during get Task Viewer list!", e);
            throw new ServiceException("Error during get Task Viewer list!", e);
        }
        return TaskViewerMapper.mapTaskEntityList(taskList);
    }

    /**
     * Get details for selected Task details from getItemData(int taskViewerElementId).
     */
    @Override
    public TaskDetailsDTO fetchTask(int taskId) throws ServiceException {
        TaskDetails taskDetails = getItemData(taskId);
        TaskDetailsDTO taskDetailsDTO = TaskViewerMapper.mapTaskDetails(taskDetails);
        taskDetailsDTO.setTaskEvents(this.getTaskEvents(taskId));
        return taskDetailsDTO;
    }

    /**
     * Get details for selected Task from database.
     */
    private TaskDetails getItemData(int taskId) throws ServiceException {
        try {
//            TaskProcessServer server = cpContextHolder.getTaskProcessServer();
            TaskPK taskPK = new TaskPK(taskId);
            TaskDetails taskDetails = server.getTask(taskPK);
            return taskDetails;
        } catch (CPException e) {
            logger.error("Error during get Task!", e);
            throw new ServiceException("Error during get Task!", e);
        } catch (ValidationException e) {
            logger.error("Validation error during get Task!", e);
            throw new ServiceException("Validation error during get Task!", e);
        }
    }

    /**
     * Get selected Task's task events from database and transform to browser version.
     */
    private String getTaskEvents(int taskId) throws ServiceException {
        String taskEvents = "";
        EntityList taskEventsList = null;
        try {
//            taskEventsList = cpContextHolder.getTaskProcessServer().getTaskEvents(taskId);
            taskEventsList = server.getTaskEvents(taskId);
        } catch (CPException e) {
            logger.error("Error during get Task Events!", e);
            throw new ServiceException("Error during get Task Events!", e);
        }
        for (int i = 0; i < taskEventsList.getNumRows(); i++) {
            String text = ((String) taskEventsList.getValueAt(i, "EVENT_TEXT"));
            String time = ((Timestamp) taskEventsList.getValueAt(i, "EVENT_TIME")).toString();
            time = time.substring(0, 19);
            if (text != null) {
                text = text.replaceAll("<", "&lt;").replaceAll(">", "&gt;");
                int eventType = ((BigDecimal) taskEventsList.getValueAt(i, "EVENT_TYPE")).intValue();
                String textStyled = text;
                String withMargin = null;
                switch (eventType) {
                    case 0:
                        textStyled = "<u>" + text + "</u>";
                        withMargin = "withMargin";
                        break;
                    case 1:
                        break;
                    case 2:
                        break;
                    case 3:
                        textStyled = "<u>" + text + "</u>";
                        withMargin = "withMargin";
                        break;
                }
                taskEvents = taskEvents + "<p class='" + withMargin + "'><span class='time'>" + time + "</span> " + textStyled + " </p> ";
            } else {
                taskEvents = taskEvents + "<p>" + time + " </p> ";
            }
        }
        return taskEvents;
    }

    /**
     * Delete selected task.
     */
    @Override
    public ResponseMessage deleteTask(int taskId) throws ServiceException {
        TaskPK taskPK = new TaskPK(taskId);
        try {
            cpContextHolder.getTaskProcessServer().delete(taskPK);
            ResponseMessage responseMessage = new ResponseMessage(true);
            return responseMessage;
        } catch (CPException e) {
            logger.error("Error during delete Task!", e);
            throw new ServiceException("Error during delete Task!", e);
        } catch (ValidationException e) {
            logger.error("Validation error during delete Task!", e);
            ValidationError error = new ValidationError(e.getMessage());
            return error;
        }
    }

    /**
     * Restart selected Task.
     */
    @Override
    public ResponseMessage restartTask(int taskId) throws ServiceException {
        try {
            cpContextHolder.getTaskProcessServer().restartTask(taskId);
            ResponseMessage responseMessage = new ResponseMessage(true);
            return responseMessage;
        } catch (CPException e) {
            logger.error("Error during restart Task!", e);
            throw new ServiceException("Error during restart Task!", e);
        } catch (ValidationException e) {
            logger.error("Validation error during restart Task!", e);
            ValidationError error = new ValidationError(e.getMessage());
            return error;
        }
    }

    /**
     * Wake up despatcher.
     */
    @Override
    public ResponseMessage wakeUpDespatcherTask() throws ServiceException {
        try {
            cpContextHolder.getTaskProcessServer().wakeUpDespatcher();
            ResponseMessage responseMessage = new ResponseMessage(true);
            return responseMessage;
        } catch (CPException e) {
            logger.error("Error during wake up Task despatcher!", e);
            throw new ServiceException("Error during wake up Task despatcher!", e);
        } catch (ValidationException e) {
            logger.error("Validation error during wake up Task despatcher!", e);
            ValidationError error = new ValidationError(e.getMessage());
            return error;
        }
    }

    /**
     * Delete selected task (unsafe).
     */
    @Override
    public ResponseMessage unsafeDeleteTask(int taskId) throws ServiceException {
        TaskPK taskPK = new TaskPK(taskId);
        try {
            int userId = cpContextHolder.getCPContext().getIntUserId();
            cpContextHolder.getTaskProcessServer().unsafeDeleteTask(userId, taskPK);
            ResponseMessage responseMessage = new ResponseMessage(true);
            return responseMessage;
        } catch (CPException e) {
            logger.error("Error during unsafe delete task!", e);
            throw new ServiceException("Error during unsafe delete task!", e);
        } catch (ValidationException e) {
            logger.error("Validation error during unsafe delete task!", e);
            ValidationError error = new ValidationError(e.getMessage());
            return error;
        }
    }

    /**
     * Mark selected task as failed (unsafe).
     */
    @Override
    public ResponseMessage failTaskViewerElement(int taskId) throws ServiceException {
        TaskPK taskPK = new TaskPK(taskId);
        try {
            cpContextHolder.getTaskProcessServer().failTask(taskPK);
            ResponseMessage responseMessage = new ResponseMessage(true);
            return responseMessage;
        } catch (CPException e) {
            logger.error("Error during mark task as failed!", e);
            throw new ServiceException("Error during mark task as failed!", e);
        } catch (ValidationException e) {
            logger.error("Validation error during mark task as failed!", e);
            ValidationError error = new ValidationError(e.getMessage());
            return error;
        }
    }

}
