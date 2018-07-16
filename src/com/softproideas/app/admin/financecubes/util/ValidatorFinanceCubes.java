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
package com.softproideas.app.admin.financecubes.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cedar.cp.dto.datatype.DataTypeRefImpl;
import com.cedar.cp.dto.model.FinanceCubeImpl;
import com.softproideas.app.admin.financecubes.mapper.FinanceCubesMapper;
import com.softproideas.app.admin.financecubes.model.DataTypeWithTimestampDTO;
import com.softproideas.app.admin.financecubes.model.FinanceCubeDetailsDTO;
import com.softproideas.app.admin.financecubes.services.FinanceCubesServiceImpl;
import com.softproideas.commons.model.error.ValidationError;

public class ValidatorFinanceCubes {
    private static Logger logger = LoggerFactory.getLogger(FinanceCubesServiceImpl.class);

    /**
     * Methods validates whole Finance Cube
     */
    public static ValidationError validateFinanceCubeDetails(FinanceCubeDetailsDTO financeCubeDetailsDTO, FinanceCubeImpl financeCubeImpl) {
        ValidationError error = new ValidationError();
        validateChangeManagement(error, financeCubeDetailsDTO.isInsideChangeManagement(), financeCubeDetailsDTO.isChangeManagementOutstanding());
        validateVisId(error, financeCubeDetailsDTO.getFinanceCubeVisId().length());
        validateDescription(error, financeCubeDetailsDTO.getFinanceCubeDescription().length());
        validateDataTypesSize(error, financeCubeDetailsDTO.getDataTypes());
        validateBudgetTransferDataTypes(error, financeCubeDetailsDTO.getDataTypes(), financeCubeImpl);
        validateMappedDataTypes(error, financeCubeDetailsDTO.getDataTypes(), financeCubeImpl);
        return error;
    }

    /**
     * Method validates if object of FinanceCubesDTO can be changed
     */
    private static void validateChangeManagement(ValidationError error, boolean isInsideChangeManagement, boolean isChangeManagementOutstanding) {
        String fieldName = "change";
        if (!isInsideChangeManagement && isChangeManagementOutstanding) {
            logger.error("Change managemt requests are pending for this model.");
            error.addFieldError(fieldName, "Change managemt requests are pending for this model.");
        }
    }

    /**
     * Method validates if visId are correct
     */
    private static void validateVisId(ValidationError error, int length) {
        String fieldName = "identifier";
        if (length == 0) {
            logger.error("Length (" + length + ") of VisId can't be 0");
            error.addFieldError(fieldName, "Length (" + length + ") of VisId can't be 0");
        } else if (length > 100) {
            logger.error("Length (" + length + ") of VisId must not exceed 100 on a FinanceCube");
            error.addFieldError(fieldName, "Length (" + length + ") of VisId must not exceed 100 on a FinanceCube");
        }
    }

    /**
     * Method validates if description are correct
     */
    private static void validateDescription(ValidationError error, int length) {
        String fieldName = "description";
        if (length > 128) {
            logger.error("Length (" + length + ") of Description must not exceed 128 on a FinanceCube");
            error.addFieldError(fieldName, "Length (" + length + ") of Description must not exceed 128 on a FinanceCube");
        }
    }

    /**
     * Method validates if list Data Types are not empty
     */
    private static void validateDataTypesSize(ValidationError error, List<DataTypeWithTimestampDTO> dataType) {
        String fieldName = "dataTypesSize";
        int size = dataType.size();
        if (size == 0) {
            logger.error("FinaceCube must contain at least one datatype");
            error.addFieldError(fieldName, "FinaceCube must contain at least one datatype");
        }
    }

    /**
     * Method validates if budget transfer Data Types are not deleted
     */
    private static void validateBudgetTransferDataTypes(ValidationError error, List<DataTypeWithTimestampDTO> dataTypes, FinanceCubeImpl financeCubeImpl) {
        String fieldName = "dataTypesBudgetTransfer";
        List<DataTypeWithTimestampDTO> listDataType = FinanceCubesMapper.mapDataTypeHashMap(financeCubeImpl.getSelectedDataTypeRefs());
        List<DataTypeWithTimestampDTO> list = new ArrayList<DataTypeWithTimestampDTO>();
        boolean isAtTheList;
        int id;
        for (DataTypeWithTimestampDTO listDataTypeElement: listDataType) {
            if (listDataTypeElement.getSubType() == 1 || listDataTypeElement.getSubType() == 2) {
                list.add(listDataTypeElement);
            }
        }
        for (DataTypeWithTimestampDTO listIdNotRemovedElement: list) {
            id = listIdNotRemovedElement.getDataTypeId();
            isAtTheList = false;
            for (DataTypeWithTimestampDTO listDataTypeElement: dataTypes) {
                if (listDataTypeElement.getDataTypeId() == id) {
                    isAtTheList = true;
                    break;
                }
            }
            if (isAtTheList == false) {
                logger.error("Budget transfer data types may not be removed");
                error.addFieldError(fieldName, "Budget transfer data type = " + listIdNotRemovedElement.getDataTypeVisId() + " may not be removed");
                return;
            }
        }
    }

    /**
     * Method validates if Data Types used in mapped model are not deleted
     */
    @SuppressWarnings("unchecked")
    private static void validateMappedDataTypes(ValidationError error, List<DataTypeWithTimestampDTO> dataTypes, FinanceCubeImpl financeCubeImpl) {
        String fieldName = "dataTypesMapped";
        Set<DataTypeRefImpl> dataTypesRef = financeCubeImpl.getMappedDataTypes();
        List<DataTypeRefImpl> list = new ArrayList<DataTypeRefImpl>();

        for (DataTypeRefImpl listDataTypeElement: dataTypesRef) {
            list.add(listDataTypeElement);
        }
        boolean isAtTheList;
        int id;
        for (DataTypeRefImpl listIdNotRemovedElement: list) {
            id = listIdNotRemovedElement.getDataTypePK().getDataTypeId();
            isAtTheList = false;
            for (DataTypeWithTimestampDTO listDataTypeElement: dataTypes) {
                if (listDataTypeElement.getDataTypeId() == id) {
                    isAtTheList = true;
                    break;
                }
            }
            if (isAtTheList == false) {
                logger.error("Data Type " + listIdNotRemovedElement.getNarrative() + " is used in a mapped model.");
                error.addFieldError(fieldName, "Data Type " + listIdNotRemovedElement.getNarrative() + " is used in a mapped model.");
                return;
            }
        }
    }
}
