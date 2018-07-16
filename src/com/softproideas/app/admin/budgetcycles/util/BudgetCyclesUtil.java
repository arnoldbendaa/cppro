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
package com.softproideas.app.admin.budgetcycles.util;

import java.util.List;

import com.cedar.cp.api.base.EntityList;
import com.cedar.cp.api.model.BudgetCycleRef;
import com.cedar.cp.dto.model.BudgetCycleCK;
import com.softproideas.app.admin.budgetcycles.model.BudgetCycleDTO;

/**
 * <p>Class is responsible for retrieving, comparing some fields or properties in objects related to budget cycle.</p>
 * 
 * @author Łukasz Puła
 * @email lukasz.pula@softproideas.com
 * <p>2014 All rights reserved to Softpro Ideas Group</p>
 */
public class BudgetCyclesUtil {

    /**
     * Retrieves year and month from periodVisId.
     */
    public static int[] getYearAndMonthFromPeriod(String periodVisId) {
        int[] yearAndMonth = new int[2];
        String[] tmp = periodVisId.split("/");
        if (tmp.length == 2) { // only year
            yearAndMonth[0] = Integer.parseInt(tmp[1]);
            yearAndMonth[1] = 0;
        } else if (tmp.length == 3) {
            yearAndMonth[0] = Integer.parseInt(tmp[1]);
            yearAndMonth[1] = Integer.parseInt(tmp[2]);
        }
        return yearAndMonth;
    }

    /**
     * Checks if budget cycle with budgetCycleId is stored in collection of budget cycles.
     */
    public static boolean checkIfBudgetCycleIsStoredInBudgetCollections(List<BudgetCycleDTO> list, int budgetCycleId) {
        boolean ifBudgetCycleExists = false;
        for (BudgetCycleDTO budgetCycleDTO: list) {
            if (budgetCycleDTO.getBudgetCycleId() == budgetCycleId) {
                ifBudgetCycleExists = true;
                break;
            }
        }
        return ifBudgetCycleExists;
    }

    /**
     * Checks if budget cycle with budgetCycleId is stored in collection {@link EntityList} of budget cycles.
     */
    public static boolean checkIfBudgetCycleIsStoredInBudgetCollections(EntityList list, int budgetCycleId) {
        boolean ifBudgetCycleExists = false;
        for (int i = 0; i < list.getNumRows(); i++) {
            int id = manageBudgetCycleId(list.getValueAt(i, "BudgetCycle"));
            if (id == budgetCycleId) {
                ifBudgetCycleExists = true;
                break;
            }
        }
        return ifBudgetCycleExists;
    }

    public static int manageFirstBudgetCycleIdFromList(EntityList list) {
        return manageBudgetCycleId(list.getValueAt(0, "BudgetCycle"));
    }

    public static int manageBudgetCycleId(Object obj) {
        if (obj instanceof BudgetCycleRef) {
            BudgetCycleRef budgetCycleRef = (BudgetCycleRef) obj;
            BudgetCycleCK budgetCycleCK = (BudgetCycleCK) budgetCycleRef.getPrimaryKey();
            return budgetCycleCK.getBudgetCyclePK().getBudgetCycleId();
        }
        return -1;
    }
}
