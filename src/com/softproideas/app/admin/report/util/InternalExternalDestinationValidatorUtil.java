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
package com.softproideas.app.admin.report.util;

import com.softproideas.app.admin.report.externaldestinations.model.ExternalDestinationDetailsDTO;
import com.softproideas.app.admin.report.internaldestinations.model.InternalDestinationDetailsDTO;
import com.softproideas.commons.model.error.ValidationError;

public class InternalExternalDestinationValidatorUtil {

    /**
     * General validation method for Internal Destination details. It calls other methods for various fields.
     * @param newReport     new data from html form
     */
    public static ValidationError validateInternalDestinationDetails(InternalDestinationDetailsDTO newReport) {
        ValidationError error = new ValidationError();
        validateVisId(error, newReport.getReportVisId());
        validateDescription(error, newReport.getReportDescription());
        Integer messageTypeIndex = newReport.getMessageType().getIndex();
        validateMessageType(error, messageTypeIndex);
        validateUsersSize(error, newReport.getUsers().size(), "internal");
        return error;
    }

    /**
     * General validation method for External Destination details. It calls other methods for various fields.
     * @param newReport     new data from html form
     */
    public static ValidationError validateExternalDestinationDetails(ExternalDestinationDetailsDTO newReport) {
        ValidationError error = new ValidationError();
        validateVisId(error, newReport.getReportVisId());
        validateDescription(error, newReport.getReportDescription());
        validateUsersSize(error, newReport.getUsers().size(), "external");
        // TODO: validate email here?
        return error;
    }

    public static void validateVisId(ValidationError error, String newVisId) {
        String fieldName = "reportVisId";
        if (newVisId != null && newVisId.length() > 20) {
            error.addFieldError(fieldName, "Length (" + newVisId.length() + ") of VisId must not exceed 20 on a Report");
        } else if (newVisId == null || newVisId.trim().length() == 0) {
            error.addFieldError(fieldName, "An Identifier must be supplied");
        }
    }

    public static void validateDescription(ValidationError error, String newDescription) {
        String fieldName = "reportDescription";
        if (newDescription != null && newDescription.length() > 128) {
            error.addFieldError(fieldName, "Length (" + newDescription.length() + ") of Description must not exceed 128 on a Report");
        } else if (newDescription == null || newDescription.trim().length() == 0) {
            error.addFieldError(fieldName, "A Description must be supplied");
        }
    }

    public static void validateMessageType(ValidationError error, int newMessageType) {
        String fieldName = "messageType";
        int nmt = newMessageType;
        if (nmt != 0 && nmt != 1 && nmt != 2) {
            error.addFieldError(fieldName, "Invalid Message Type.");
        }
    }

    private static void validateUsersSize(ValidationError error, int size, String type) {
        String fieldName = "users";
        if (size == 0) {
            if (type.equals("internal")) {
                error.addFieldError(fieldName, "The entity must have at least one user selected.");
            } else if (type.equals("external")) {
                error.addFieldError(fieldName, "User list can not be empty.");
            }
        }
    }

}
