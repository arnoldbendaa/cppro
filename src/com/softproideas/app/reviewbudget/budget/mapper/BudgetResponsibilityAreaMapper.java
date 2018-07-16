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
package com.softproideas.app.reviewbudget.budget.mapper;

import java.util.List;

import com.cedar.cp.dto.user.AllUsersAssignmentsELO;
import com.softproideas.app.reviewbudget.budget.model.BudgetResponsibilityAreasDTO;
import com.softproideas.app.reviewbudget.budget.model.BudgetResponsibilityAreasDTO.Assignment;

/**
 * Maps responisibility areas to DTO
 * 
 * @author Szymon Walczak
 * @email szymon.walczak@softproideas.com
 * 2014 All rights reserved to Softpro Ideas Group
 */
public class BudgetResponsibilityAreaMapper {

    /**
     * @param modelVisId "5/1", "200/1" etc.
     * @param allUsersAssignmentsELO all assignments
     * @return mapped DTO for given model
     */
    public static BudgetResponsibilityAreasDTO mapAllUsersAssignmentsELO(String modelVisId, AllUsersAssignmentsELO allUsersAssignmentsELO) {
        BudgetResponsibilityAreasDTO dto = new BudgetResponsibilityAreasDTO();
        dto.setModelVisId(modelVisId);
        List<Assignment> list = dto.getRespAreas();

        for (int i = 0; i < allUsersAssignmentsELO.getNumRows(); ++i) {
            String model = (String) allUsersAssignmentsELO.getValueAt(i, "Model_VisId");

            if (model.equalsIgnoreCase(modelVisId)) {
                Assignment assignment = dto.new Assignment();
                assignment.setElementName((String) allUsersAssignmentsELO.getValueAt(i, "Element_VisId"));
                assignment.setElementDescription((String) allUsersAssignmentsELO.getValueAt(i, "Element_Description"));
                assignment.setUserName((String) allUsersAssignmentsELO.getValueAt(i, "UserId"));
                assignment.setUserFullName((String) allUsersAssignmentsELO.getValueAt(i, "FullName"));
                assignment.setReadWrite((String) allUsersAssignmentsELO.getValueAt(i, "Read_Write"));
                list.add(assignment);
            }
        }
        return dto;
    }

}
