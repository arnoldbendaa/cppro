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
package com.softproideas.app.lookuptable.currency.util;

import java.util.Arrays;
import java.util.List;
import com.softproideas.app.lookuptable.currency.model.LookupCurrencyDTO;
import com.softproideas.commons.model.error.FieldError;
import com.softproideas.commons.model.error.ValidationError;

/**
 * 
 * <p>Class is responsible for validating fields in objects of class {@link LookupCurrencyDTO}</p>
 *
 * @author Łukasz Puła
 * @email lukasz.pula@softproideas.com
 * <p>2015 All rights reserved to IT Services Jacek Kurasiewicz</p>
 */
public class LookupCurrencyValidator {

    /**
     * Checks if currency lookup is correct
     */
    public static ValidationError validateCurrency(LookupCurrencyDTO currency) {
        ValidationError error = new ValidationError();
        validateYear(error, currency.getYear());
        validatePeriod(error, currency.getPeriod());
        validateCompany(error, currency.getCompany());
        validateCurrencyString(error, currency.getCurrency());
        return error;
    }

    public static String validateCurrencies(List<LookupCurrencyDTO> currencies) {
        ValidationError error = null;
        String errorMessage = "";

        for (LookupCurrencyDTO currency: currencies) {
            error = LookupCurrencyValidator.validateCurrency(currency);
            List<FieldError> fieldErrors = error.getFieldErrors();
            if (!fieldErrors.isEmpty()) {
                errorMessage += "Fields [";
                for (FieldError fieldError: fieldErrors) {
                    errorMessage += fieldError.getFieldName().toUpperCase();
                    errorMessage += " - " + fieldError.getFieldMessage();
                }
                errorMessage += "] are not valid in " + currency.getCurrency() + ". ";
            }
        }

        if (!errorMessage.isEmpty()) {
            return errorMessage;
        }

        return errorMessage;
    }

    private static void validateYear(ValidationError error, int year) {
        String fieldName = "year";
        if (year < 1970 || year > 2999) {
            error.addFieldError(fieldName, "Year should be between years 1970 and 3000.");
        }
    }

    private static void validatePeriod(ValidationError error, int period) {
        String fieldName = "period";
        
        int[] intArray = { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15 };
        boolean isInArray = false;
        for (int i: intArray) {
            if (i == period) {
                isInArray = true;
                break;
            }
        }
        
        if (!isInArray) {
            error.addFieldError(fieldName, "Periods should be stored in " + Arrays.toString(intArray) + ".");
        }
    }

    private static void validateCompany(ValidationError error, int company) {
        String fieldName = "company";
        if (company == 0) {
            error.addFieldError(fieldName, "Company is required.");
        }
    }

    private static void validateCurrencyString(ValidationError error, String currency) {
        String fieldName = "currency";
        if (currency == null || currency.trim().length() == 0) {
            error.addFieldError(fieldName, "Please supply a currency.");
        } else if (currency.length() > 4) {
            error.addFieldError(fieldName, "Length (" + currency.length() + ") of currency must not exceed 4 characters.");
        }
    }

}
