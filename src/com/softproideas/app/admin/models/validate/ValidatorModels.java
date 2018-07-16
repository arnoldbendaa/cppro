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
package com.softproideas.app.admin.models.validate;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.softproideas.app.admin.financecubes.model.DimensionDTO;
import com.softproideas.app.admin.financecubes.services.FinanceCubesServiceImpl;
import com.softproideas.app.admin.models.model.HierarchyDTO;
import com.softproideas.app.admin.models.model.ModelDetailsDTO;
import com.softproideas.commons.model.Property;
import com.softproideas.commons.model.error.ValidationError;

public class ValidatorModels {
    private static Logger logger = LoggerFactory.getLogger(FinanceCubesServiceImpl.class);

    /**
     * Vaildate whole model
     */
    public static ValidationError validateModelDetails(ModelDetailsDTO modelDetailsDTO) {
        ValidationError error = new ValidationError();
        validateVisId(error, modelDetailsDTO.getModelVisId().length());
        validateDescription(error, modelDetailsDTO.getModelDescription().length());
        validateProperties(error, modelDetailsDTO.getProperties());
        validateReferences(error, modelDetailsDTO.getBudgetHierarchy(), modelDetailsDTO.getBusiness().get(0), modelDetailsDTO.getAccount(), modelDetailsDTO.getCalendar());
        return error;
    }

    /**
     * Vaildate modelVisId
     */
    private static void validateVisId(ValidationError error, int length) {
        String fieldName = "identifier";
        if (length == 0) {
            logger.error("Length (" + length + ") of VisId can't be 0");
            error.addFieldError(fieldName, "Length (" + length + ") of VisId can't be 0");
        } else if (length > 50) {
            logger.error("Length (" + length + ") of VisId must not exceed 50 on a Model");
            error.addFieldError(fieldName, "Length (" + length + ") of VisId must not exceed 50 on a Model");
        }
    }

    /**
     * Vaildate descriptionVisId
     */
    private static void validateDescription(ValidationError error, int length) {
        String fieldName = "description";
        if (length > 128) {
            logger.error("Length (" + length + ") of Description must not exceed 128 on a Model");
            error.addFieldError(fieldName, "Length (" + length + ") of Description must not exceed 128 on a Model");
        }
    }

    /**
     * Vaildate properties
     */
    private static void validateProperties(ValidationError error, List<Property> properties) {
        String fieldName = "properties";
        int length;
        Set<String> names = new HashSet<String>();
        for (Property listElement: properties) {
            if (listElement.getName() != null) {
                if (listElement.getValue() != null) {
                    length = listElement.getName().length();
                    if (names.contains(listElement.getName())) {
                        logger.error("Names of properties are duplicated. Duplicated name: " + listElement.getName() + ".");
                        error.addFieldError(fieldName, "Names of properties are duplicated. Duplicated name: " + listElement.getName() + ".");
                        break;
                    } else if (length == 0) {
                        logger.error("Name of property is empty.");
                        error.addFieldError(fieldName, "Name of property is empty.");
                        break;
                    } else if (length > 64) {
                        logger.error("Name of property must not exceed 64 on a Model.");
                        error.addFieldError(fieldName, "Name of property must not exceed 64 on a Model.");
                        break;
                    } else if (listElement.getValue().length() > 2048) {
                        logger.error("Value of property must not exceed 2048 on a Model. Model name: " + listElement.getName() + ".");
                        error.addFieldError(fieldName, "Value of property must not exceed 2048 on a Model. Model name: " + listElement.getName() + ".");
                        break;
                    } else {
                        names.add(listElement.getName());
                    }
                } else {
                    logger.error("Value of property is empty.");
                    error.addFieldError(fieldName, "Value of property is empty.");
                    break;
                }
            } else {
                logger.error("Name of property is empty.");
                error.addFieldError(fieldName, "Name of property is empty.");
                break;
            }
        }
    }

    /**
     * Vaildate references Dimensions and Hierarchies
     */
    private static void validateReferences(ValidationError error, HierarchyDTO hierarchy, DimensionDTO business, DimensionDTO account, DimensionDTO calendar) {
        String fieldName = "references";
        if (hierarchy == null) {
            logger.error("Hierarchy is not selected");
            error.addFieldError(fieldName, "Hierarchy is not selected");
        } else if (business == null) {
            logger.error("Business is not selected");
            error.addFieldError(fieldName, "Business is not selected");
        } else if (account == null) {
            logger.error("Account is not selected");
            error.addFieldError(fieldName, "Account is not selected");
        } else if (calendar == null) {
            logger.error("Calendar is not selected");
            error.addFieldError(fieldName, "Calendar is not selected");
        } else if (hierarchy.getDimensionId() != business.getDimensionId()) {
            logger.error("Hierarchy have wrong Business Dimension Id");
            error.addFieldError(fieldName, "Hierarchy have wrong Business Dimension Id");
        }
    }

}
