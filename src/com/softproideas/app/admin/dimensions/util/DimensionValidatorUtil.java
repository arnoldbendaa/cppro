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
package com.softproideas.app.admin.dimensions.util;

import java.util.List;

import com.cedar.cp.dto.dimension.DimensionImpl;
import com.cedar.cp.dto.dimension.calendar.CalendarImpl;
import com.softproideas.app.admin.dimensions.calendar.model.CalendarDetailsDTO;
import com.softproideas.app.admin.dimensions.calendar.model.CalendarElementDTO;
import com.softproideas.app.admin.dimensions.model.DimensionDetailsDTO;
import com.softproideas.app.admin.dimensions.model.DimensionElementDTO;
import com.softproideas.commons.model.error.ValidationError;

public class DimensionValidatorUtil {

    /**
     * General validation method for Dimension Account and Business details. It calls other methods for various fields.
     * @param oldDimension      from database
     * @param newDimension      new data from html form
     */
    public static ValidationError validateDimensionDetails(DimensionImpl oldDimension, DimensionDetailsDTO newDimension) {
        ValidationError error = new ValidationError();
        validateType(error, newDimension.getType());
        validateVisId(error, newDimension.getDimensionVisId(), null);
        validateDescription(error, newDimension.getDimensionDescription(), null);
        validateDimensionElements(error, oldDimension, newDimension);
        return error;
    }

    /**
     * General validation method for Dimension Calendar details. It calls other methods for various fields.
     * @param dimensionImpl     from database
     * @param calendar          new data from html form
     */
    public static ValidationError validateCalendarDetails(CalendarImpl dimensionImpl, CalendarDetailsDTO calendar) {
        ValidationError error = new ValidationError();
        validateType(error, calendar.getType());
        validateVisId(error, calendar.getDimensionVisId(), null);
        validateDescription(error, calendar.getDimensionDescription(), null);
        validateYears(error, calendar.getYears());
        // DimensionValidatorUtil.validateDimensionElements(error, dimensionImpl, calendar);
        return error;
    }

    private static void validateYears(ValidationError error, List<CalendarElementDTO> years) {
        String fieldName = "years";
        int currentYear = 0; 
        for (CalendarElementDTO year: years) {
            if (currentYear < year.getYear()) {
                currentYear = year.getYear();
            } else {
                error.addFieldError(fieldName, "Years you have set are not valid. Years should be listed chronologically.");
                break;
            }
        }
    }

    protected static void validateType(ValidationError error, int newType) {
        String fieldName = "type";
        if (newType != 1 && newType != 2 && newType != 3) {
            error.addFieldError(fieldName, "Invalid type");
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

    protected static void validateDescription(ValidationError error, String newDescription, Integer dimensionElementId) {
        String fieldName = "dimensionDescription";
        if (dimensionElementId != null) {
            fieldName = "dimensionElements." + dimensionElementId + ".dimensionElementDescription";
        }
        if (newDescription != null && newDescription.length() > 128) {
            error.addFieldError(fieldName, "Length (" + newDescription.length() + ") of Description must not exceed 128 on a Dimension");
        } else if (newDescription == null || newDescription.trim().length() == 0) {
            error.addFieldError(fieldName, "A Description must be supplied");
        }
    }

    protected static void validateDimensionElements(ValidationError error, DimensionImpl oldDimension, DimensionDetailsDTO newDimension) {
        List<DimensionElementDTO> dimensionElementsDTOList = newDimension.getDimensionElements();
        for (DimensionElementDTO dimensionElement: dimensionElementsDTOList) {
            String operationOnDAO = dimensionElement.getOperation();
            if (operationOnDAO != null) {
                if (operationOnDAO.equals("insert") || operationOnDAO.equals("update")) {
                    validateVisId(error, dimensionElement.getDimensionElementVisId(), dimensionElement.getDimensionElementId());
                    validateDescription(error, dimensionElement.getDimensionElementDescription(), dimensionElement.getDimensionElementId());
                }
            }
        }
    }

}
