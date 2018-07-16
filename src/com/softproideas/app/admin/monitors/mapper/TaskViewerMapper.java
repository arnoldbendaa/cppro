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
package com.softproideas.app.admin.monitors.mapper;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.cedar.cp.api.base.EntityList;
import com.cedar.cp.dto.task.TaskDetails;
import com.softproideas.app.admin.monitors.model.TaskDTO;
import com.softproideas.app.admin.monitors.model.TaskDetailsDTO;

public class TaskViewerMapper {

    private static final String[] sTaskStatusLiterals = new String[] { "Created", "Received", "Despatched", "Running", "Failed", "Complete", "Waiting for task", "Commiting checkpoint", "Commiting completion", "Complete (exceptions)", "Unsafe deleted" };

    public static List<TaskDTO> mapTaskEntityList(EntityList list) {
        List<TaskDTO> listDTO = new ArrayList<TaskDTO>();
        Timestamp timestamp = null;

        for (int i = 0; i < list.getNumRows(); i++) {
            TaskDTO taskDTO = new TaskDTO();
            EntityList row = list.getRowData(i);

            taskDTO.setTaskId((Integer) row.getValueAt(0, "TaskId")); // TaskId i Task przechowuje poszukiwaną tutaj przez nas wartość, ale z TaskId można otrzymać ją łatwiej, więc teraz używamy tego pola.

            taskDTO.setUserName((String) row.getValueAt(0, "UserName")); // Username i UserId przechowuje poszukiwaną tutaj przez nas wartość, ale z Username można otrzymać ją łatwiej, więc teraz używamy tego pola.

            taskDTO.setTaskName((String) row.getValueAt(0, "TaskName"));
            String status = sTaskStatusLiterals[(Integer) row.getValueAt(0, "Status")];
            taskDTO.setStatus(status);

            timestamp = (Timestamp) row.getValueAt(0, "CreateDate");
            taskDTO.setCreateDate((String) timestamp.toString().substring(0, 19));

            timestamp = (Timestamp) row.getValueAt(0, "EndDate");
            if (timestamp != null) {
                taskDTO.setEndDate((String) timestamp.toString().substring(0, 19));
            } else {
                taskDTO.setEndDate(" ");
            }

            taskDTO.setStep((String) row.getValueAt(0, "Step"));

            listDTO.add(taskDTO);
        }

        return listDTO;
    }

    public static TaskDetailsDTO mapTaskDetails(TaskDetails taskDetails) {
        TaskDetailsDTO taskDetailsDTO = new TaskDetailsDTO();
        Timestamp timestamp = null;

        taskDetailsDTO.setTaskId(taskDetails.getTaskId());
        taskDetailsDTO.setTaskName(taskDetails.getTaskName());
        taskDetailsDTO.setStep(taskDetails.getStep());
        taskDetailsDTO.setOriginalTaskId(taskDetails.getOriginalTaskId());
        taskDetailsDTO.setStatus(sTaskStatusLiterals[taskDetails.getStatus()]);
        taskDetailsDTO.setCreateDate(taskDetails.getCreateDate().toString());
        timestamp = (Timestamp) taskDetails.getEndDate();

        if (timestamp != null) {
            taskDetailsDTO.setEndDate((String) timestamp.toString());
        } else {
            taskDetailsDTO.setEndDate(" ");
        }

        return taskDetailsDTO;
    }
}
