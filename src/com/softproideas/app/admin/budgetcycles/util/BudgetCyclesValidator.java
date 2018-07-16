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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.softproideas.app.admin.budgetcycles.model.BudgetCycleDTO;
import com.softproideas.app.admin.budgetcycles.model.BudgetCycleDetailsDTO;
import com.softproideas.app.admin.budgetcycles.model.BudgetCycleXMLFormDTO;
import com.softproideas.commons.model.error.ValidationError;

/**
 * <p>Class is responsible for validating fields in objects of classes {@link BudgetCycleDetailsDTO}, {@link BudgetCycleDTO}</p>
 * 
 * @author Łukasz Puła
 * @email lukasz.pula@softproideas.com
 * <p>2014 All rights reserved to Softpro Ideas Group</p>
 */
public class BudgetCyclesValidator {

    /**
     * Checks if periods (only periods) in budget cycle are correct
     */
    public static ValidationError validateBudgetCycle(BudgetCycleDTO budgetCycle) {
        ValidationError error = new ValidationError();
        validatePeriods(error, budgetCycle);
        return error;
    }

    /**
     * Methods validates whole data transfer object of {@link BudgetCycleDetailsDTO}
     */
    public static ValidationError validateBudgetCycleDetails(BudgetCycleDetailsDTO budgetCycle) {
        ValidationError error = new ValidationError();
        validateIdentifier(error, budgetCycle.getBudgetCycleVisId());
        validateDescription(error, budgetCycle.getBudgetCycleDescription());
        validatePeriods(error, budgetCycle);
        validatePlannedEndDate(error, budgetCycle);
        validateStructuredLevelEndDates(error, budgetCycle);
        validateXMLForms(error, budgetCycle);
        return error;
    }

    /**
     * Method validates if list of {@link BudgetCycleXMLFormDTO} are correct
     */
    private static void validateXMLForms(ValidationError error, BudgetCycleDetailsDTO budgetCycle) {
        String fieldName = "xmlForms";

        List<BudgetCycleXMLFormDTO> xmlForms = budgetCycle.getXmlForms();
        if (xmlForms == null || xmlForms.size() == 0 || budgetCycle.getDefaultXmlFormId() <= 0) {
            error.addFieldError(fieldName, "No default xml form is selected.");
            return;
        }
        boolean isStoredInList = false;
        String dataType = "";
        for (BudgetCycleXMLFormDTO budgetCycleXMLFormDTO: xmlForms) {
            if (budgetCycleXMLFormDTO.getFlatFormId() == budgetCycle.getDefaultXmlFormId()) {
                isStoredInList = true;
                dataType = budgetCycleXMLFormDTO.getDataType();
                break;
            }
        }
        if (isStoredInList == false) {
            error.addFieldError(fieldName, "Selected default xml form is not stored in xml form list.");
            return;
        }

        if (budgetCycle.getDefaultXmlFormDataType() == null || budgetCycle.getDefaultXmlFormDataType().length() == 0) {
            error.addFieldError(fieldName, "The default xml form must have defined data type!");
            return;
        }

        if (!dataType.equals(budgetCycle.getDefaultXmlFormDataType())) {
            error.addFieldError(fieldName, "Data type not match to data type in selected default xml form.");
            return;
        }

        if (budgetCycle.getDefaultXmlFormDataType().length() > 2) {
            error.addFieldError(fieldName, "Length (" + budgetCycle.getDefaultXmlFormDataType().length() + ") of data type must not exceed 2!");
        }
    }

    /**
     * Method validates if planned end date is correct
     */
    private static void validatePlannedEndDate(ValidationError error, BudgetCycleDetailsDTO budgetCycle) {
        String fieldName = "plannedEndDate";
        if (budgetCycle.getPlannedEndDate() == null) {
            error.addFieldError(fieldName, "The planned end date must be selected.");
            return;
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try {
            date = sdf.parse(budgetCycle.getPlannedEndDate());
        } catch (ParseException e) {
            error.addFieldError(fieldName, "The planned end date has wrong format.");
            return;
        }

        boolean isNew = budgetCycle.getBudgetCycleId() == -1;
        if (date != null && isNew && date.before(new Date())) {
            error.addFieldError(fieldName, "The planned end date must be after today.");
        }
    }
    
    /**
     * Method validates if structure level end dates are correct.
     */
    private static void validateStructuredLevelEndDates(ValidationError error, BudgetCycleDetailsDTO budgetCycle) {
        String fieldName = "plannedEndDate";
        if (budgetCycle.getLevelDates() == null) {
            error.addFieldError(fieldName, "The structure level end dates are invalid.");
            return;
        }

        if (budgetCycle.getLevelDates().size() != 0) {
            long lastDate = budgetCycle.getLevelDates().get(0);

            for (long levelDate: budgetCycle.getLevelDates()) {
                if (lastDate >= levelDate) {
                    lastDate = levelDate;
                } else {
                    error.addFieldError(fieldName, "Dates you have set are not valid. Date should be listed in reverse chronology.");
                    return;
                }
            }
        } else {
            // level dates should be fixed during saving whole budget cycle
        }
    }

    /**
     * Method validates if period (period FROM, period TO) are correct
     */
    private static void validatePeriods(ValidationError error, BudgetCycleDTO budgetCycle) {
        String fieldName = "periodFrom";
        if (budgetCycle.getPeriodFromId() == 0) {
            error.addFieldError(fieldName, "The period FROM must be selected.");
        }
        fieldName = "periodTo";
        if (budgetCycle.getPeriodToId() == 0) {
            error.addFieldError(fieldName, "The period TO must be selected.");
        }
        fieldName = "periods";
        if (isPeriodToBeforePeriodFrom(budgetCycle.getPeriodFromVisId(), budgetCycle.getPeriodToVisId())) {
            error.addFieldError(fieldName, "Period FROM cannot be further than period TO.");
        }
    }

    private static boolean isPeriodToBeforePeriodFrom(String periodFrom, String periodTo) {
        int[] from = BudgetCyclesUtil.getYearAndMonthFromPeriod(periodFrom);
        int[] to = BudgetCyclesUtil.getYearAndMonthFromPeriod(periodTo);
        return (from[0] > to[0] || (from[0] == to[0] && from[1] > to[1]));
    }

    /**
     * Method validates if identifier is available and correct
     */
    private static void validateIdentifier(ValidationError error, String name) {
        String fieldName = "budgetCycleVisId";
        if (name == null || name.trim().length() == 0) {
            error.addFieldError(fieldName, "Please supply an identifier.");
        } else if (name.length() > 20) {
            error.addFieldError(fieldName, "Length (" + name.length() + ") of identifier must not exceed 20 characters.");
        }
    }

    /**
     * Method validates if identifier is correct
     */
    private static void validateDescription(ValidationError error, String description) {
        String fieldName = "budgetCycleDescription";

        if (description != null && description.length() > 128) {
            error.addFieldError(fieldName, "Length (" + description.length() + ") of description must not exceed 128 characters.");
        }
    }
}
