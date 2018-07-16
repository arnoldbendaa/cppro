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

import com.cedar.cp.dto.recalculate.RecalculateBatchTaskImpl;
import com.softproideas.app.admin.recalculatebatches.model.RecalculateBatchDetailsDTO;
import com.softproideas.app.admin.recalculatebatches.model.RecalculateBatchProfileDTO;
import com.softproideas.commons.model.error.ValidationError;

/**
 * <p>Class is responsible for validating fields in objects of classes {@link RecalculateBatchDetailsDTO}</p>
 * 
 * @author Łukasz Puła
 * @email lukasz.pula@softproideas.com
 * <p>2014 All rights reserved to Softpro Ideas Group</p>
 */
public class RecalculateBatchesValidatorUtil {

    /**
     * Methods validates whole data transfer object of {@link RecalculateBatchDetailsDTO}
     */
    public static ValidationError validateRecalculateBatchDetails(RecalculateBatchTaskImpl recalculateBatchTaskImpl, RecalculateBatchDetailsDTO recalculateBatch) {
        ValidationError error = new ValidationError();
        validateIdentifier(error, recalculateBatch.getRecalculateBatchVisId());
        validateDescription(error, recalculateBatch.getRecalculateBatchDescription());
        validateProfiles(error, recalculateBatch.getProfiles());
        validateResponsibilityAreas(error, recalculateBatch.getResponsibilityAreas());
        return error;
    }

    /**
     * Method validates if identifier is available and correct
     */
    private static void validateIdentifier(ValidationError error, String name) {
        String fieldName = "recalculateBatchVisId";
        if (name == null || name.trim().length() == 0) {
            error.addFieldError(fieldName, "Please supply an identifier.");
        } else if (name.length() > 128) {
            error.addFieldError(fieldName, "Length (" + name.length() + ") of identifier must not exceed 128 characters.");
        }
    }

    /**
     * Method validates if description is correct
     */
    private static void validateDescription(ValidationError error, String description) {
        String fieldName = "recalculateBatchDescription";

        if (description != null && description.length() > 128) {
            error.addFieldError(fieldName, "Length (" + description.length() + ") of description must not exceed 128 characters.");
        }
    }

    /**
     * Method validates if profiles are available and at least one is selected.
     */
    private static void validateProfiles(ValidationError error, List<RecalculateBatchProfileDTO> profiles) {
        String fieldName = "profiles";

        if (profiles == null || profiles.size() < 1) {
            error.addFieldError(fieldName, "There is no profiles.");
            return;
        }

        boolean hasSelectedProfile = false;
        for (RecalculateBatchProfileDTO profile: profiles) {
            if (profile.isSelected()) {
                hasSelectedProfile = true;
                break;
            }
        }

        if (!hasSelectedProfile) {
            error.addFieldError(fieldName, "You must choose at least one XML Form!");
        }
    }

    /**
     * Method validates if responsibility areas are available and at least one is selected.
     */
    private static void validateResponsibilityAreas(ValidationError error, List<Integer> responsibilityAreas) {
        String fieldName = "responsibilityAreas";
        if (responsibilityAreas == null || responsibilityAreas.size() < 1) {
            error.addFieldError(fieldName, "Recalculate batch task requires at least one assignment.");
        }
    }
}
