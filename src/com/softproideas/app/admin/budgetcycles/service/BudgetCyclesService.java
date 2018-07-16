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
package com.softproideas.app.admin.budgetcycles.service;

import java.util.List;

import com.softproideas.app.admin.budgetcycles.model.BudgetCycleDTO;
import com.softproideas.app.admin.budgetcycles.model.BudgetCycleDetailsDTO;
import com.softproideas.app.admin.budgetcycles.model.BudgetCycleStructureLevelEndDatesDTO;
import com.softproideas.app.core.flatform.model.FlatFormExtendedCoreDTO;
import com.softproideas.common.exceptions.ServiceException;
import com.softproideas.commons.model.ResponseMessage;

/**
 * <p>{@link BudgetCyclesService} defines methods operating on budget cycle elements.</p>
 * 
 * @author Łukasz Puła
 * @email lukasz.pula@softproideas.com
 * <p>2014 All rights reserved to Softpro Ideas Group</p>
 */
public interface BudgetCyclesService {

    /**
     * Retrieves all budget cycles for logged user
     */
    List<BudgetCycleDTO> browseBudgetCycles();

    /**
     * Retrieves all details for budget cycle. ModelId is necessary to fetch details for budget cycle.
     */
    BudgetCycleDetailsDTO fetchBudgetCycle(int budgetCycleId, int modelId) throws ServiceException;

    /**
     * Retrieves all budget cycles for specified model
     * @return list of {@link BudgetCycleDTO} related to model
     */
    List<BudgetCycleDTO> browseBudgetCyclesForModel(int modelId);

    /**
     * Method saves (insert or update) budget cycle to database. 
     * If any validation error occurs, in {@link ResponseMessage} we have list of fields we are incorrect. 
     */
    ResponseMessage save(BudgetCycleDetailsDTO budgetCycle) throws ServiceException;

    /**
     * Method delete budget cycle. ModelId is necessary to identify correct budget cycle. 
     */
    ResponseMessage delete(int budgetCycleId, int modelId) throws ServiceException;

    /**
     * Method updates (only periods - period FROM and period TO) budget cycle in database. 
     */
    ResponseMessage updatePeriodForBudgetCycle(BudgetCycleDTO budgetCycle) throws ServiceException;
    

    /**
     * Retrieves all structure level dates for specified model and planned end date stored in budgetCycleStructureLevelEndDatesDTO
     * @param budgetCycleStructureLevelEndDatesDTO - {@link BudgetCycleStructureLevelEndDatesDTO}
     * @return list of timestamps
     */
    List<Long> browseStructureLevelDatesForModel(BudgetCycleStructureLevelEndDatesDTO budgetCycleStructureLevelEndDatesDTO);
    
    // TODO issueBudgetStateRebuildTask not implemented
    int issueBudgetStateRebuildTask(BudgetCycleDTO budgetCycle) throws ServiceException;

    // TODO issueBudgetStateTask not implemented
    int issueBudgetStateTask(BudgetCycleDTO budgetCycle) throws ServiceException;

    // TODO browseXmlFormsForModel should be moved to different module
    /**
     * Retrieves all xmlForm for specified model.
     */
    List<FlatFormExtendedCoreDTO> browseXmlFormsForModel(int modelId);
}
