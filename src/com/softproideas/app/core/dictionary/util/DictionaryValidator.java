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
package com.softproideas.app.core.dictionary.util;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.softproideas.common.models.DictionaryDTO;
import com.softproideas.commons.model.error.FieldError;
import com.softproideas.commons.model.error.ValidationError;

/**
 * 
 * <p>Class is responsible for validating fields in objects of class {@link DictionaryDTO}</p>
 *
 * @author Łukasz Puła
 * @email lukasz.pula@softproideas.com
 * <p>2015 All rights reserved to IT Services Jacek Kurasiewicz</p>
 */
public class DictionaryValidator {

    /**
     * Checks if dictionary is correct
     */
    public static ValidationError validateDictionary(DictionaryDTO dictionary) {
        ValidationError error = new ValidationError();
        validateValue(error, dictionary.getValue());
        validateType(error, dictionary.getType());
        validateDescription(error, dictionary.getDescription());
        return error;
    }

    public static String validateDictionaries(List<DictionaryDTO> dictionaries) {
        ValidationError error = null;
        String errorMessage = "";

        for (DictionaryDTO dictionary: dictionaries) {
            error = DictionaryValidator.validateDictionary(dictionary);
            List<FieldError> fieldErrors = error.getFieldErrors();
            if (!fieldErrors.isEmpty()) {
                errorMessage += "Fields [";
                for (FieldError fieldError: fieldErrors) {
                    errorMessage += fieldError.getFieldName().toUpperCase();
                    errorMessage += " - " + fieldError.getFieldMessage();
                }
                errorMessage += "] are not valid in " + dictionary.getValue() + ". ";
            }
        }
        if (!errorMessage.isEmpty()) {
            return errorMessage;
        }

        error = validateDictionariesDuplicates(dictionaries);
        if (!error.getFieldErrors().isEmpty()) {
            errorMessage = "Duplicates of values are not allowed.";
        }
        return errorMessage;
    }

    private static ValidationError validateDictionariesDuplicates(List<DictionaryDTO> dictionaries) {
        String fieldName = "isDictionaryDuplicated";

        ValidationError error = new ValidationError();
        boolean isDictionaryDuplicated = false;

        Set<String> keys = new HashSet<String>();
        for (DictionaryDTO dictionary: dictionaries) {
            if (keys.add(dictionary.getKey()) == false) {
                isDictionaryDuplicated = true;
                break;
            }
        }

        if (isDictionaryDuplicated) {
            error.addFieldError(fieldName, "Duplicates of values are not allowed.");
        }
        return error;
    }

    private static void validateValue(ValidationError error, String value) {
        String fieldName = "value";
        if (value == null || value.trim().length() == 0) {
            error.addFieldError(fieldName, "Please supply an value.");
        } else if (value.length() > 50) {
            error.addFieldError(fieldName, "Length (" + value.length() + ") of value must not exceed 50 characters.");
        }
    }

    private static void validateType(ValidationError error, String type) {
        String fieldName = "type";
        if (type == null || type.trim().length() == 0) {
            error.addFieldError(fieldName, "Please supply an type.");
        } else if (type.length() > 36) {
            error.addFieldError(fieldName, "Length (" + type.length() + ") of type must not exceed 36 characters.");
        }
    }

    private static void validateDescription(ValidationError error, String description) {
        String fieldName = "description";
        if (description != null && description.length() > 120) {
            error.addFieldError(fieldName, "Length (" + description.length() + ") of description must not exceed 120 characters.");
        }
    }

}
