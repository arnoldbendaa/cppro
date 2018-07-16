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
package com.softproideas.app.admin.importtasks.service;

import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.importtask.ImportTaskRef;
import com.cedar.cp.dto.importtask.AllImportTasksELO;
import com.cedar.cp.dto.importtask.ImportTaskPK;
import com.cedar.cp.dto.importtask.ImportTaskRefImpl;
import com.softproideas.app.admin.importtasks.mapper.ImportMapper;
import com.softproideas.app.admin.importtasks.model.ImportDTO;
import com.softproideas.commons.context.CPContextHolder;
import com.softproideas.commons.model.ResponseMessage;
import com.softproideas.commons.model.error.ValidationError;

@Service("importService")
public class ImportServiceImpl implements ImportService {

    private static Logger logger = LoggerFactory.getLogger(ImportServiceImpl.class);

    @Autowired
    CPContextHolder cpContextHolder;

    /**
     * Get list of all Imports.
     */
    @Override
    public List<ImportDTO> browseImports() {
        AllImportTasksELO allImportTasksELO = cpContextHolder.getListSessionServer().getAllImportTasks();
        return ImportMapper.mapAllImportTasksELO(allImportTasksELO);
    }

    /**
     * Submit Import Task.
     */
    @Override
    public ResponseMessage submitTask(String importTaksVisId, int importTaskId, String externalSystemVisId) {
        ImportTaskPK importTaskPK = new ImportTaskPK(importTaskId);
        ImportTaskRef importTaskRef = new ImportTaskRefImpl(importTaskPK, importTaksVisId);
        try {
            String d = externalSystemVisId;
            int taskId = cpContextHolder.getImportTasksProcess().issueImportTask(importTaskRef, cpContextHolder.getUserId(), externalSystemVisId);
            
            ResponseMessage responseMessage = new ResponseMessage(true);
            HashMap taskMap = new HashMap();
            taskMap.put("taskId", taskId);
            responseMessage.setData(taskMap);
            return responseMessage;
        } catch (ValidationException e) {
            logger.error("Validation error during submit Task!", e);
            ValidationError error = new ValidationError(e.getMessage());
            return error;
        }
    }

}
