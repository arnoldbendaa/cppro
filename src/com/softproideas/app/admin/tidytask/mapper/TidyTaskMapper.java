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
package com.softproideas.app.admin.tidytask.mapper;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.cedar.cp.api.admin.tidytask.TidyTask;
import com.cedar.cp.api.admin.tidytask.TidyTaskRef;
import com.cedar.cp.dto.admin.tidytask.AllTidyTasksELO;
import com.cedar.cp.dto.admin.tidytask.TidyTaskPK;
import com.softproideas.app.admin.tidytask.model.TidyTaskCommandDTO;
import com.softproideas.app.admin.tidytask.model.TidyTaskDTO;

/**
 * <p>Class is responsible for maps different object related to tidy task to data transfer object (and vice-versa)</p>
 * 
 * @author Łukasz Puła
 * @email lukasz.pula@softproideas.com
 * <p>2014 All rights reserved to Softpro Ideas Group</p>
 */
public class TidyTaskMapper {

    /**
     * Maps list of all available tidy task {@link AllTidyTasksELO} to list of transfer data object related to tidy tasks.
     */
    public static List<TidyTaskDTO> mapAllTidyTasksELO(AllTidyTasksELO elo) {
        List<TidyTaskDTO> tidyTaskDTOList = new ArrayList<TidyTaskDTO>();

        for (@SuppressWarnings("unchecked")
        Iterator<AllTidyTasksELO> it = elo.iterator(); it.hasNext();) {
            AllTidyTasksELO row = it.next();

            TidyTaskDTO taskDTO = new TidyTaskDTO();
            TidyTaskRef tidyTaskRef = row.getTidyTaskEntityRef();

            int id = ((TidyTaskPK) tidyTaskRef.getPrimaryKey()).getTidyTaskId();
            taskDTO.setTidyTaskId(id);

            String name = tidyTaskRef.getNarrative();
            taskDTO.setTidyTaskVisId(name);

            String description = row.getDescription();
            taskDTO.setTidyTaskDescription(description);

            tidyTaskDTOList.add(taskDTO);
        }
        return tidyTaskDTOList;
    }

    /**
     * Maps all tidy tasks details (actually labels of sql's, procedures) stored in {@link TidyTask} to list of transfer data object for commands.
     */
    public static List<TidyTaskCommandDTO> mapToTidyTaskCommandsDTO(TidyTask tidy) {
        List<TidyTaskCommandDTO> commandsDTO = new ArrayList<TidyTaskCommandDTO>();

        for (Object command: tidy.getTaskList()) {
            @SuppressWarnings("unchecked")
            List<String> commandConverted = (ArrayList<String>) command;

            TidyTaskCommandDTO commandDTO = new TidyTaskCommandDTO();
            commandDTO.setName((String) commandConverted.get(0));
            commandDTO.setCommand((String) commandConverted.get(1));

            commandsDTO.add(commandDTO);
        }
        return commandsDTO;
    }

    /**
     * Maps {@link TidyTask}  object to transfer data object (method used during asking (browseTidyTask) about details of tidy task)
     */
    public static TidyTaskDTO mapToTidyTask(TidyTask tidy) {
        TidyTaskDTO tidyTaskDTO = new TidyTaskDTO();
        int id = ((TidyTaskPK) tidy.getPrimaryKey()).getTidyTaskId();
        tidyTaskDTO.setTidyTaskId(id);
        tidyTaskDTO.setTidyTaskVisId(tidy.getVisId());
        tidyTaskDTO.setTidyTaskDescription(tidy.getDescription());

        List<TidyTaskCommandDTO> commandsDTO = TidyTaskMapper.mapToTidyTaskCommandsDTO(tidy);
        tidyTaskDTO.setCommands(commandsDTO);

        return tidyTaskDTO;
    }
}
