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
package com.softproideas.app.admin.budgetinstructions.util;

import java.util.List;

import com.cedar.cp.dto.budgetinstruction.BudgetInstructionImpl;
import com.softproideas.app.admin.budgetinstructions.model.BudgetInstructionAssignmentsNodeDTO;
import com.softproideas.app.admin.budgetinstructions.model.BudgetInstructionDetailsDTO;
import com.softproideas.commons.model.error.ValidationError;

public class BudgetInstructionsValidatorUtil {

    public static ValidationError validateUserDetails(BudgetInstructionImpl oldBudgetInstructions, BudgetInstructionDetailsDTO newBudgetInstruction) {
        ValidationError error = new ValidationError();
        validateDocumentRef(error, newBudgetInstruction.getBudgetInstructionDocumentRef());
        validateVisId(error, newBudgetInstruction.getBudgetInstructionVisId());
        validateAssignments(error, newBudgetInstruction.getAssignments());
        return error;
    }

    private static void validateAssignments(ValidationError error, List<BudgetInstructionAssignmentsNodeDTO> list) {
        String fieldName = "budgetInstructionAssignment";
        if (list.size() < 1) {
            error.addFieldError(fieldName, "Budget Instruction requires at least one assignment");
        }
    }

    public static void validateVisId(ValidationError error, String newVisId) {
        String fieldName = "budgetInstructionVisId";
        if (newVisId != null && newVisId.length() > 20) {
            error.addFieldError(fieldName, "Length (" + newVisId.length() + ") of VisId must not exceed 20 on a BudgetInstruction");
        }
    }

    public static void validateDocumentRef(ValidationError error, String newDocumentRef) {
        String fieldName = "budgetInstructionDocumentRef";
        if (newDocumentRef != null && newDocumentRef.length() > 255) {
            error.addFieldError(fieldName, "Length (" + newDocumentRef.length() + ") of DocumentRef must not exceed 255 on a BudgetInstruction");
        }
    }

    // private static void validateDocument(ValidationError error , MultipartFile multipartFile){
    // String fieldName = "budgetInstructionDocument";
    // if(multipartFile == null) {
    // error.addFieldError(fieldName, "Choose file");
    // }
    // }

}
