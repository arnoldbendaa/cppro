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
package com.softproideas.app.admin.recalculatebatches.util;

import java.util.List;

import com.cedar.cp.api.base.EntityList;
import com.cedar.cp.dto.recalculate.RecalculateBatchTaskImpl;
import com.softproideas.app.admin.budgetcycles.model.BudgetCycleDTO;
import com.softproideas.app.admin.budgetcycles.util.BudgetCyclesUtil;

/**
 * <p>Class is responsible for retrieving, comparing some fields or properties in objects related to recalculate batch.</p>
 * 
 * @author Łukasz Puła
 * @email lukasz.pula@softproideas.com
 * <p>2014 All rights reserved to Softpro Ideas Group</p>
 */
public class RecalculateBatchesUtil {

    /**
     * Manages model identifier. If modelId (equals -1) is not defined, we get data from {@link RecalculateBatchTaskImpl} 
     */
    public static int manageModelId(RecalculateBatchTaskImpl impl, int modelId) {
        return (modelId != -1) ? modelId : impl.getModelId();
    }

    /**
     * Manage budgetCycleId for recalculate batch. First of all we check if budget cycle is defined. If not, we take id from {@link RecalculateBatchTaskImpl}.
     * If user has selected budget cycle, but it is not stored in available collection {@link EntityList}, we retrieve first budget cycle (if is exists).
     */
    public static int manageBudgetCycleId(RecalculateBatchTaskImpl impl, EntityList budgetCycles, int budgetCycleId) {
        int budgetCycleIdTemp = -1;
        if (budgetCycleId != -1) { // user has selected budget cycle
            budgetCycleIdTemp = budgetCycleId;
        } else { // user hasn't selected budget cycle
            if (BudgetCyclesUtil.checkIfBudgetCycleIsStoredInBudgetCollections(budgetCycles, impl.getBudgetCycleId())) {
                budgetCycleIdTemp = impl.getBudgetCycleId();
            } else if (budgetCycles.getNumRows() > 0) { // budget cycle isn't stored in budget cycle collection that's why we try to get first element of budget cycle collection
                budgetCycleIdTemp = BudgetCyclesUtil.manageFirstBudgetCycleIdFromList(budgetCycles);
            }
        }
        return budgetCycleIdTemp;
    }

    /**
     * Manage budgetCycleId for recalculate batch. First of all we check if budget cycle is defined. If not, we take id from {@link RecalculateBatchTaskImpl}.
     * If user has selected budget cycle, but it is not stored in available collection {@link BudgetCycleDTO}, we retrieve first budget cycle (if is exists).
     */
    public static int manageBudgetCycleId(RecalculateBatchTaskImpl impl, List<BudgetCycleDTO> budgetCycles, int budgetCycleId) {
        int budgetCycleIdTemp = -1;
        if (budgetCycleId != -1) { // user has selected budget cycle
            budgetCycleIdTemp = budgetCycleId;
        } else { // user hasn't selected budget cycle
            if (BudgetCyclesUtil.checkIfBudgetCycleIsStoredInBudgetCollections(budgetCycles, impl.getBudgetCycleId())) {
                budgetCycleIdTemp = impl.getBudgetCycleId();
            } else if (budgetCycles.size() > 0) { // budget cycle isn't stored in budget cycle collection that's why we try to get first element of budget cycle collection
                budgetCycleIdTemp = budgetCycles.get(0).getBudgetCycleId();
            }
        }
        return budgetCycleIdTemp;
    }
}
