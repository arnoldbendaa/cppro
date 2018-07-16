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
package com.softproideas.app.core.budgetcycle.mapper;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.cedar.cp.dto.model.BudgetCycleCK;
import com.cedar.cp.dto.model.BudgetCyclesForModelELO;
import com.softproideas.app.core.budgetcycle.model.BudgetCycleCoreDTO;

/**
 * <p>Class is responsible for maps different object related to budget cycle to data transfer object (and vice-versa)</p>
 * 
 * @author Łukasz Puła
 * @email lukasz.pula@softproideas.com
 * <p>2014 All rights reserved to Softpro Ideas Group</p>
 */
public class BudgetCyclesCoreMapper {


    @SuppressWarnings("unchecked")
    public static List<BudgetCycleCoreDTO> mapBudgetCyclesForModelELOToDTO(BudgetCyclesForModelELO budgetCycles) {
        List<BudgetCycleCoreDTO> budgetCyclesDTO = new ArrayList<BudgetCycleCoreDTO>();

        for (Iterator<BudgetCyclesForModelELO> it = budgetCycles.iterator(); it.hasNext();) {
            BudgetCyclesForModelELO row = it.next();
            BudgetCycleCoreDTO budgetCycleDTO = new BudgetCycleCoreDTO();

            BudgetCycleCK budgetCycleCK = (BudgetCycleCK) row.getBudgetCycleEntityRef().getPrimaryKey();
            budgetCycleDTO.setBudgetCycleId(budgetCycleCK.getBudgetCyclePK().getBudgetCycleId());
            budgetCycleDTO.setBudgetCycleVisId(row.getBudgetCycleEntityRef().getNarrative());
            
            budgetCycleDTO.setBudgetCycleDescription(row.getDescription());
            budgetCyclesDTO.add(budgetCycleDTO);
        }
        return budgetCyclesDTO;
    }

}
