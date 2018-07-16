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
package com.softproideas.app.reviewbudget.budget.service;

import java.util.Map;

import com.cedar.cp.api.model.ReviewBudgetDetails;
import com.cedar.cp.utc.common.CPContext;
import com.softproideas.app.reviewbudget.budget.model.BudgetResponsibilityAreasDTO;
import com.softproideas.app.reviewbudget.budget.model.ReviewBudgetDTO;
import com.softproideas.app.reviewbudget.budget.model.WorkbookToUpdateDTO;
import com.softproideas.common.exceptions.ServiceException;

public interface BudgetService {

    /**
     * Return workbook with data, excel in JSON format and context variables (ReviewBudgetDTO).
     * @throws ServiceException 
     */
    ReviewBudgetDTO fetchReviewBudget(int topNodeId, int modelId, int budgetCycleId, int dataEntryProfileId, Map<Integer, Integer> selectionsMap, String dataType) throws ServiceException;
    
    /**
     * Updates data from workbook in database after clicking recalculate button in excel view.
     */
    String updateWorkbookData(WorkbookToUpdateDTO workbookToUpdate, int modelId, int topNodeId) throws ServiceException;

    /**
     * Return budget responsibility areas associated with the specified modelId.
     */
    BudgetResponsibilityAreasDTO fetchUserBudgetResponsibilityAreas(int modelId) throws ServiceException;

    /**
     * Returns ReviewBudgetDetails
     */
    ReviewBudgetDTO fetchReviewBudgetDetails(int topNodeId, int modelId, int budgetCycleId, int dataEntryProfileId, Map<Integer, Integer> selectionsMap, String dataType) throws ServiceException;

	public void Init(int userId, CPContext t);
}
