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
package com.softproideas.app.admin.recalculatebatches.service;

import java.util.List;

import com.softproideas.app.admin.recalculatebatches.model.RecalculateBatchDTO;
import com.softproideas.app.admin.recalculatebatches.model.RecalculateBatchDetailsDTO;
import com.softproideas.app.admin.recalculatebatches.model.RecalculateBatchProfileDTO;
import com.softproideas.common.exceptions.ServiceException;
import com.softproideas.commons.model.ResponseMessage;
import com.softproideas.commons.model.tree.NodeLazyDTO;

/**
 * <p> {@link RecalculateBatchesService} defines methods operating on recalculate batch elements.</p>
 * 
 * @author Łukasz Puła
 * @email lukasz.pula@softproideas.com
 * <p>2014 All rights reserved to Softpro Ideas Group</p>
 */
public interface RecalculateBatchesService {

    /**
     * Retrieves all recalculate batches for logged user
     */
    List<RecalculateBatchDTO> browseAllRecalculateBatchTasks() throws ServiceException;

    /**
     * Retrieves all details for recalculate.
     */
    RecalculateBatchDetailsDTO fetchRecalculateBatch(int recalculateBatchId) throws ServiceException;

    /**
     * Method saves (insert or update) recalculate batch to database. 
     * If any validation error occurs, in {@link ResponseMessage} we have list of fields we are incorrect. 
     */
    ResponseMessage save(RecalculateBatchDetailsDTO recalculateBatch) throws ServiceException;

    /**
     * Method deletes recalculate batch.
     */
    ResponseMessage delete(int recalculateBatchId) throws ServiceException;

    /**
     * Invokes task which recalculate all profiles selected in recalculate batch.
     * @return id of invoked task
     */
    int issueRecalculateBatchTask(RecalculateBatchDetailsDTO recalculateBatch) throws ServiceException;

    /**
     * Retrieves information about responsibility areas for recalculate batch 
     * for element with structureId and structureElementId
     * @return list of responsibility areas
     */
    List<NodeLazyDTO> browseResponsibilityAreaChildren(int recalculateBatchId, int structureId, int structureElementId) throws ServiceException;

    /**
     * Retrieves information about responsibility areas for recalculate batch 
     * for root element with modelId
     * @return list of responsibility areas
     */
    List<NodeLazyDTO> browseResponsibilityAreaRoot(int recalculateBatchId, int modelId) throws ServiceException;

    /**
     * Retrieves information about profiles to recalculate which are saved in 'recalculate batch'. 
     * List depends on modelId and budgetCycleId.
     */
    List<RecalculateBatchProfileDTO> browseRecalculateBatchProfiles(int recalculateBatchId, int modelId, int budgetCycleId) throws ServiceException;
}