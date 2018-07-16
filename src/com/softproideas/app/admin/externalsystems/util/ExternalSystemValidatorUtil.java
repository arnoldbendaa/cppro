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
package com.softproideas.app.admin.externalsystems.util;

import com.softproideas.app.admin.externalsystems.model.ExternalSystemDetailsDTO;
import com.softproideas.commons.model.error.ValidationError;

/**
 * <p>TODO: Comment me</p>
 *
 * @author Jarosław Kaczmarski
 * @email jarok@softproideas.com
 * <p>2014 All rights reserved to IT Services Jacek Kurasiewicz</p>
 */
public class ExternalSystemValidatorUtil {

    public static ValidationError validateExternalSystemDetails(ExternalSystemDetailsDTO externalSystemDetails) {
        ValidationError error = new ValidationError();
        validateVisId(error, externalSystemDetails.getExternalSystemVisId());
        validateDescription(error, externalSystemDetails.getExternalSystemDescription());
        validateLocation(error, externalSystemDetails.getLocation());
        validateConnectorClass(error, externalSystemDetails.getConnectorClass());
        return error;
    }

    private static void validateConnectorClass(ValidationError error, String connectorClass) {
        String fieldName = "connectorClass";
        if (connectorClass != null && connectorClass.length() > 512) {
            error.addFieldError(fieldName, "Length (" + connectorClass.length() + ") of connector class must not exceed 512 on a ExternalSystem");
        }
    }

    private static void validateLocation(ValidationError error, String location) {
        String fieldName = "location";
        if (location == null || location.trim().length() == 0) {
            error.addFieldError(fieldName, "Please supply a location.");
        } else if (location != null && location.length() > 128) {
            error.addFieldError(fieldName, "Length (" + location.length() + ") of location must not exceed 128 on a ExternalSystem");
        }
    }

    private static void validateDescription(ValidationError error, String externalSystemDescription) {
        String fieldName = "externalSystemDescription";
        if (externalSystemDescription == null || externalSystemDescription.trim().length() == 0) {
            error.addFieldError(fieldName, "Please supply a description.");
        } else if (externalSystemDescription != null && externalSystemDescription.length() > 128) {
            error.addFieldError(fieldName, "Length (" + externalSystemDescription.length() + ") of description must not exceed 128 on a ExternalSystem");
        }
    }

    private static void validateVisId(ValidationError error, String externalSystemVisId) {
        String fieldName = "externalSystemVisId";
        if (externalSystemVisId == null || externalSystemVisId.trim().length() == 0) {
            error.addFieldError(fieldName, "Please supply a name.");
        } else if (externalSystemVisId != null && externalSystemVisId.length() > 29) {
            error.addFieldError(fieldName, "Length (" + externalSystemVisId.length() + ") of name must not exceed 29 on a ExternalSystem");
        }
    }

}
