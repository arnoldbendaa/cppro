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
package com.softproideas.app.admin.hierarchies.util;

import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.dto.dimension.DimensionImpl;
import com.softproideas.app.admin.dimensions.model.DimensionDetailsDTO;
import com.softproideas.app.admin.hierarchies.model.HierarchyDTO;
import com.softproideas.commons.model.error.ValidationError;

/**
 * 
 * <p>Object store share functions validate hierarchies</p>
 *
 * <p>2015 All rights reserved to IT Services Jacek Kurasiewicz</p>
 */
public class HierarchyValidatorUtil {

    //TODO: sprawdzić, czy jest gdzieś używane.
    public static ValidationError validateHierarchyAccountDetails(DimensionImpl oldDimension, DimensionDetailsDTO newDimension) {
        ValidationError error = new ValidationError();
        validateVisId(error, newDimension.getDimensionVisId(), null);
        return error;
    }

    private static void validateVisId(ValidationError error, String newVisId) {
        String fieldName = "identifier";
        if (newVisId != null && newVisId.length() > 100) {
            error.addFieldError(fieldName, "Length (" + newVisId.length() + ") of Identifier must not exceed 20 on a Hierarchy - " + newVisId);
        } else if (newVisId == null || newVisId.trim().length() == 0) {
            error.addFieldError(fieldName, "An Identifier must be supplied");
        }
    }

    protected static void validateVisId(ValidationError error, String newVisId, Integer dimensionElementId) {
        String fieldName = "dimensionVisId";
        if (dimensionElementId != null) {
            fieldName = "dimensionElements." + dimensionElementId + ".dimensionElementVisId";
        }
        if (newVisId != null && newVisId.length() > 100) {
            error.addFieldError(fieldName, "Length (" + newVisId.length() + ") of Identifier must not exceed 100 on a Dimension");
        } else if (newVisId == null || newVisId.trim().length() == 0) {
            error.addFieldError(fieldName, "An Identifier must be supplied");
        }
    }

    private static void validateDescription(ValidationError error, String newDescription) {
        String fieldName = "description";
        if (newDescription != null && newDescription.length() > 128) {
            error.addFieldError(fieldName, "Length (" + newDescription.length() + ") of Description must not exceed 128 on a Hierarchy - " + newDescription);
        }
    }

    public static ValidationError validateHierarchyDetails(HierarchyDTO newHierarchy) {
        ValidationError error = new ValidationError();
        validateVisId(error, newHierarchy.getHierarchyVisId());
        validateDescription(error, newHierarchy.getHierarchyDescription());
        return error;
    }

    public static void validateEvents(String newVisId, String newDescription) throws ValidationException {
        if(newVisId.length() == 0) {
            throw new ValidationException("An Identifier must be supplied");
        } else if(newVisId.length() > 100) {
            throw new ValidationException("Length (" + newVisId.length() + ") of Identifier Hierarchy Element must not exceed 100 on a Hierarchy - " + newVisId);
        } else if(newDescription.length() > 64) {
            throw new ValidationException("Length (" + newDescription.length() + ") of Description Hierarchy Element must not exceed 20 on a Hierarchy - " + newDescription);
        }
    }
}
