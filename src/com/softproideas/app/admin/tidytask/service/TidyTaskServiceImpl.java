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
package com.softproideas.app.admin.tidytask.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cedar.cp.api.admin.tidytask.TidyTaskRef;
import com.cedar.cp.api.base.CPException;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.dto.admin.tidytask.AllTidyTasksELO;
import com.cedar.cp.dto.admin.tidytask.TidyTaskEditorSessionSSO;
import com.cedar.cp.dto.admin.tidytask.TidyTaskImpl;
import com.cedar.cp.dto.admin.tidytask.TidyTaskPK;
import com.cedar.cp.dto.admin.tidytask.TidyTaskRefImpl;
import com.cedar.cp.ejb.api.admin.tidytask.TidyTaskEditorSessionServer;
import com.softproideas.app.admin.tidytask.mapper.TidyTaskMapper;
import com.softproideas.app.admin.tidytask.model.TidyTaskDTO;
import com.softproideas.common.exceptions.ServiceException;
import com.softproideas.commons.context.CPContextHolder;

/**
 * 
 * @author Łukasz Puła
 * @email lukasz.pula@softproideas.com
 * <p>2014 All rights reserved to Softpro Ideas Group</p>
 */
@Service("tidyTaskService")
public class TidyTaskServiceImpl implements TidyTaskService {

    private static Logger logger = LoggerFactory.getLogger(TidyTaskServiceImpl.class);

    @Autowired
    CPContextHolder cpContextHolder;

    /*
     * (non-Javadoc)
     * @see com.softproideas.app.admin.tidytask.service.TidyTaskService#browseTidyTasks()
     */
    @Override
    public List<TidyTaskDTO> browseTidyTasks() {
        AllTidyTasksELO allTidyTasksELO = cpContextHolder.getListSessionServer().getAllTidyTasks();
        List<TidyTaskDTO> tidyTaskDTOList = TidyTaskMapper.mapAllTidyTasksELO(allTidyTasksELO);
        return tidyTaskDTOList;
    }

    /*
     * (non-Javadoc)
     * @see com.softproideas.app.admin.tidytask.service.TidyTaskService#fetchTidyTask(int)
     */
    @Override
    public TidyTaskDTO fetchTidyTask(int tidyTaskId) throws ServiceException {
        TidyTaskEditorSessionServer server = cpContextHolder.getTidyTaskEditorSessionServer();

        TidyTaskImpl tidyTask = getItemData(server, tidyTaskId);
        TidyTaskDTO tidyTaskDTO = TidyTaskMapper.mapToTidyTask(tidyTask);
        return tidyTaskDTO;
    }

    /**
     * Returns details of tidy task with tidyTaskId. Details are stored in {@link TidyTaskImpl} class.
     */
    private TidyTaskImpl getItemData(TidyTaskEditorSessionServer server, int tidyTaskId) throws ServiceException {
        try {
            TidyTaskPK tidyTaskPK = new TidyTaskPK(tidyTaskId);
            TidyTaskEditorSessionSSO sso = server.getItemData(tidyTaskPK);
            TidyTaskImpl tidyTaskImpl = sso.getEditorData();
            return tidyTaskImpl;
        } catch (CPException e) {
            logger.error("Error during browsing tidy task with id =" + tidyTaskId + "!");
            throw new ServiceException("Error during browsing tidy task with id =" + tidyTaskId + "!");
        } catch (ValidationException e) {
            logger.error("Validation error during browsing tidy task with id =" + tidyTaskId + "!");
            throw new ServiceException("Validation error during browsing tidy task with id =" + tidyTaskId + "!");
        }
    }

    /*
     * (non-Javadoc)
     * @see com.softproideas.app.admin.tidytask.service.TidyTaskService#submit(com.softproideas.app.admin.tidytask.model.TidyTaskDTO)
     */
    @Override
    public int submit(TidyTaskDTO tidyTask) throws ServiceException {
        TidyTaskPK tidyTaskPK = new TidyTaskPK(tidyTask.getTidyTaskId());
        TidyTaskRef tidyTaskRef = new TidyTaskRefImpl(tidyTaskPK, tidyTask.getTidyTaskVisId());
        try {
            TidyTaskEditorSessionServer server = cpContextHolder.getTidyTaskEditorSessionServer();
            int taskId = server.issueTidyTask(tidyTaskRef, cpContextHolder.getUserId());
            return taskId;
        } catch (ValidationException e) {
            logger.error("Error during submitting tidy task with id =" + tidyTask.getTidyTaskId() + "!", e);
            throw new ServiceException("Error during submitting tidy task with id =" + tidyTask.getTidyTaskId() + "!", e);
        }
    }

}
