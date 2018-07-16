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
package com.softproideas.app.admin.forms.flatforms.util;

import java.util.List;

import com.softproideas.common.models.FormDeploymentDataDTO;
import com.softproideas.common.models.StructureElementCoreDTO;
import com.softproideas.commons.model.error.ValidationError;

public class FormDeploymentValidator {

    public static ValidationError validateFormDeployment(FormDeploymentDataDTO formDeploymentData) {
        ValidationError error = new ValidationError();
        validateIdentifier(error, formDeploymentData.getIdentifier());
        validateDescription(error, formDeploymentData.getDescription());
        validateBudgetCycle(error, formDeploymentData.getBudgetCycleId());
        validateBusinessElement(error, formDeploymentData.getStructureElements());
        validateMailType(error, formDeploymentData.getMailType());
        validateMailContent(error, formDeploymentData.getMailContent());
        return error;
    }

    private static void validateIdentifier(ValidationError error, String identifier) {
        String fieldName = "visId";
        if (identifier == null || identifier.trim().length() == 0) {
            error.addFieldError(fieldName, "Please supply an identifier.");
        } else if (identifier.length() > 120) {
            error.addFieldError(fieldName, "Length (" + identifier.length() + ") of identifier must not exceed 120 characters.");
        }
    }

    private static void validateDescription(ValidationError error, String description) {
        String fieldName = "description";
        
        if (description == null || description.trim().length() == 0) {
            error.addFieldError(fieldName, "Please supply a description.");
        } else if (description.length() > 128) {
            error.addFieldError(fieldName, "Length (" + description.length() + ") of description must not exceed 128 characters.");
        }
    }

    private static void validateBudgetCycle(ValidationError error, Integer budgetCycleId) {
        String fieldName = "budgetCycle";
        if (budgetCycleId <= 0) {
            error.addFieldError(fieldName, "Please select budget cycle.");
        }
    }

    private static void validateBusinessElement(ValidationError error, List<StructureElementCoreDTO> businessElements) {
        String fieldName = "businessDimension";
        if (businessElements == null || businessElements.size() <= 0) {
            error.addFieldError(fieldName, "Please select business location deployment.");
        }
    }

    private static void validateMailType(ValidationError error, int mailType) {
        String fieldName = "messageType";
        if (mailType != 0 && mailType != 1 && mailType != 2) {
            error.addFieldError(fieldName, "Please select mail type.");
        }
    }

    private static void validateMailContent(ValidationError error, String mailContent) {
        String fieldName = "messageText";        
        if (mailContent == null || mailContent.trim().length() == 0) {
            error.addFieldError(fieldName, "Please supply a message text.");
        } else if (mailContent.length() > 1024) {
            error.addFieldError(fieldName, "Length (" + mailContent.length() + ") of message text must not exceed 1024 characters.");
        }
    }
}
