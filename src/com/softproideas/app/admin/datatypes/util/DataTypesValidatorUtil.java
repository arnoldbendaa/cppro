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
package com.softproideas.app.admin.datatypes.util;

import java.util.Iterator;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.datatype.DataTypeRef;
import com.cedar.cp.dto.datatype.AllDataTypesELO;
import com.cedar.cp.dto.datatype.DataTypeImpl;
import com.cedar.cp.dto.datatype.VirtualExprParser;
import com.cedar.cp.util.i18nUtils;
import com.softproideas.app.admin.datatypes.model.DataTypeDetailsDTO;
import com.softproideas.commons.model.error.ValidationError;

public class DataTypesValidatorUtil {

    /**
     * General validation method for Data Type details. It calls other methods for various fields.
     * @param oldDataType       from database
     * @param newDataType       new data from html form
     */
    public static ValidationError validateDataTypeDetails(DataTypeImpl oldDataType, DataTypeDetailsDTO newDataType, AllDataTypesELO allDataTypesList) {
        ValidationError error = new ValidationError();
        validateVisId(error, newDataType.getDataTypeVisId());
        validateDescription(error, newDataType.getDataTypeDescription());
        validateSubType(error, oldDataType.isDeployed(), oldDataType.getSubType(), newDataType.getSubType());
        if (Integer.valueOf(newDataType.getSubType()) == DataTypesUtil.getSubTypeNumber("Measure")) {
            validateMeasureClass(error, oldDataType.isDeployed(), oldDataType.getMeasureClass(), newDataType.getMeasureClass());
        }
        validateAvailableForImport(error, newDataType.getSubType(), newDataType.getMeasureClass(), newDataType.getAvailableForImport());
        validateAvailableForExport(error, newDataType.getSubType(), newDataType.getMeasureClass(), newDataType.getAvailableForExport());
        validateReadOnlyFlag(error, newDataType.getSubType(), newDataType.getReadOnlyFlag());
        if (Integer.valueOf(newDataType.getSubType()) == DataTypesUtil.getSubTypeNumber("Virtual")) {
            validateFormulaExpr(error, newDataType.getFormulaExpr(), allDataTypesList);
        }
        if (Integer.valueOf(newDataType.getSubType()) == DataTypesUtil.getSubTypeNumber("Measure")) {
            if (newDataType.getMeasureClass() == 0) { // String
                validateMeasureLength(error, oldDataType.isDeployed(), oldDataType.getMeasureLength(), newDataType.getMeasureLength());
                validateMeasureValidation(error, newDataType.getMeasureValidation());
            } else if (newDataType.getMeasureClass() == 1) { // Numeric
                validateMeasureScale(error, oldDataType.isDeployed(), oldDataType.getMeasureScale(), newDataType.getMeasureScale());
            }
        }
        return error;
    }

    private static void validateVisId(ValidationError error, String newVisId) {
        String fieldName = "dataTypeVisId";
        if (newVisId == null || newVisId.trim().length() == 0) {
            error.addFieldError(fieldName, "Please supply an Identifier.");
        } else if (newVisId != null && newVisId.length() > 2) {
            error.addFieldError(fieldName, "Length (" + newVisId.length() + ") of Identifier must not exceed 2 on a DataType");
        } else if (newVisId.length() != 2) {
            error.addFieldError(fieldName, "VisId (Identifier) must be 2 characters");
        }
    }

    private static void validateDescription(ValidationError error, String newDescription) {
        String fieldName = "dataTypeDescription";
        if (newDescription == null || newDescription.trim().length() == 0) {
            error.addFieldError(fieldName, "Please supply a Description");
        } else if (newDescription != null && newDescription.length() > 128) {
            error.addFieldError(fieldName, "Length (" + newDescription.length() + ") of Description must not exceed 128 on a DataType");
        }
    }

    private static void validateSubType(ValidationError error, boolean isOldDataTypeDeployed, Integer oldSubType, Integer newSubType) {
        String fieldName = "subType";
        if (isOldDataTypeDeployed && oldSubType != newSubType && (oldSubType == 3 || newSubType == 3)) {
            error.addFieldError(fieldName, "A datatype may not be change from/to virtual type once deployed.");
        } else if (isOldDataTypeDeployed && oldSubType != newSubType && (oldSubType == 4 || newSubType == 4)) {
            error.addFieldError(fieldName, "A datatype may not be changed from/to a measure type once deployed.");
        } else if (newSubType != 0 && newSubType != 1 && newSubType != 2 && newSubType != 3 && newSubType != 4) {
            error.addFieldError(fieldName, "Invalid sub type:" + newSubType);
        }
    }

    private static void validateMeasureClass(ValidationError error, boolean isOldDataTypeDeployed, Integer oldMeasureClass, Integer newMeasureClass) {
        String fieldName = "measureClass";
        if (isOldDataTypeDeployed) {
            if ((oldMeasureClass != null && !oldMeasureClass.equals(newMeasureClass)) || (oldMeasureClass == null && newMeasureClass != null)) {
                error.addFieldError(fieldName, "A measure cannot change its class once it has been deployed to a finance cube");
            }
        } else if (newMeasureClass < 0 || newMeasureClass > 5) {
            error.addFieldError(fieldName, "Unexpected measure class:" + newMeasureClass);
        }
    }

    private static void validateAvailableForImport(ValidationError error, Integer newSubType, Integer newMeasureClass, boolean newAvailableForImport) {
        String fieldName = "availableForImport";
        if (newSubType == 3 && newAvailableForImport) {
            error.addFieldError(fieldName, "Virtual Data Types cannot be imported.");
        } else if (newSubType == 4 && newAvailableForImport && newMeasureClass != 1) {
            error.addFieldError(fieldName, "Non-numeric measures are not available for import via the normal FMS Import/Export module.");
        }
    }

    private static void validateAvailableForExport(ValidationError error, Integer newSubType, Integer newMeasureClass, boolean newAvailableForExport) {
        String fieldName = "availableForExport";
        if (newSubType == 4 && newAvailableForExport && newMeasureClass != 1) {
            error.addFieldError(fieldName, "Non-numeric measures are not available for export via the normal FMS Import/Export module.");
        }
    }

    private static void validateReadOnlyFlag(ValidationError error, Integer newSubType, boolean newReadOnlyFlag) {
        String fieldName = "readOnlyFlag";
        if (newSubType == 3 && !newReadOnlyFlag) {
            error.addFieldError(fieldName, "Virtual Data Types must be read only.");
        }
    }

    @SuppressWarnings("rawtypes")
    private static void validateFormulaExpr(ValidationError error, String newFormulaExpr, AllDataTypesELO allDataTypesList) {
        String fieldName = "formulaExpr";
        if (newFormulaExpr == null || newFormulaExpr.trim().length() == 0) {
            error.addFieldError(fieldName, "A formula expression must be supplied for virtual data types.");
        } else if (newFormulaExpr != null && newFormulaExpr.length() > 2000) {
            error.addFieldError(fieldName, "Length (" + newFormulaExpr.length() + ") of FormulaExpr must not exceed 2000 on a DataType");
        } else {
            if (newFormulaExpr != null && newFormulaExpr.trim().length() > 0) {
                try {
                    VirtualExprParser parser = new VirtualExprParser(newFormulaExpr);
                    Iterator i = parser.getIDs().iterator();
                    while (i.hasNext()) {
                        String token = (String) i.next();
                        if (token.length() != 2) {
                            error.addFieldError(fieldName, "Data type ref must be two characters in length");
                        }
                        validateDataTypeRef(error, token, allDataTypesList);
                    }
                } catch (ValidationException e) {
                    error.addFieldError(fieldName, e.getMessage());
                }
            }

        }
    }

    private static void validateDataTypeRef(ValidationError error, String name, AllDataTypesELO allDataTypesList) {
        String fieldName = "formulaExpr";
        for (int i = 0; i < allDataTypesList.getNumRows(); i++) {
            DataTypeRef dtf = (DataTypeRef) allDataTypesList.getValueAt(i, "DataType");
            if (name.equals(dtf.getNarrative())) {
                if (dtf.getSubType() == 3) {
                    error.addFieldError(fieldName, "A virtual data type may only refer to non-virtual data types. " + name + " is a virtual data type.");
                }
                return;
            }
        }
        error.addFieldError(fieldName, "Unknown data type reference: " + name);
    }

    private static void validateMeasureLength(ValidationError error, boolean isOldDataTypeDeployed, Integer oldMeasureLength, Integer newMeasureLength) {
        String fieldName = "measureLength";
        if (isOldDataTypeDeployed) {
            if ((oldMeasureLength != null && !oldMeasureLength.equals(newMeasureLength)) || (oldMeasureLength == null && newMeasureLength != null)) {
                error.addFieldError(fieldName, "A measure data type cannot change its length once deployed.");
            }
        } else if (newMeasureLength == null) {
            error.addFieldError(fieldName, "Please supply a measure length value.");
        }
    }

    public static void validateMeasureValidation(ValidationError error, String newMeasureValidation) {
        String fieldName = "measureValidation";
        if (newMeasureValidation != null && newMeasureValidation.length() > 2048) {
            error.addFieldError(fieldName, "Length (" + newMeasureValidation.length() + ") of MeasureValidation must not exceed 2048 on a DataType");
        } else {
            if (newMeasureValidation != null && newMeasureValidation.trim().length() > 0) {
                try {
                    Pattern.compile(newMeasureValidation);
                } catch (PatternSyntaxException e) {
                    error.addFieldError(fieldName, "Invalid regular expression:" + e.getMessage());
                }
            }

        }
    }

    private static void validateMeasureScale(ValidationError error, boolean isOldDataTypeDeployed, Integer oldMeasureScale, Integer newMeasureScale) {
        String fieldName = "measureScale";
        String message = "Please supply a numeric value (from 0 to 14) to define the number of decimal places.";
        if (isOldDataTypeDeployed) {
            if ((oldMeasureScale != null && !oldMeasureScale.equals(newMeasureScale)) || (oldMeasureScale == null && newMeasureScale != null)) {
                error.addFieldError(fieldName, "A measure data type cannot change its scale once deployed.");
            }
        } else if (newMeasureScale == null) {
            error.addFieldError(fieldName, message);
        } else {
            try {
                if (!i18nUtils.getDataTypeScaleMapping().getValues().contains(newMeasureScale)) {
                    error.addFieldError(fieldName, "Scale new value is invalid. " + message);
                }
            } catch (NumberFormatException e) {
                error.addFieldError(fieldName, "You must specify a number: " + e.getMessage() + " " + message);
            }
        }
    }

}
