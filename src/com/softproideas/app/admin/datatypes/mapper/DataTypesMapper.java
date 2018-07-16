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
package com.softproideas.app.admin.datatypes.mapper;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.cedar.cp.api.datatype.DataTypeRef;
import com.cedar.cp.dto.datatype.AllDataTypesELO;
import com.cedar.cp.dto.datatype.DataTypeImpl;
import com.cedar.cp.dto.datatype.DataTypePK;
import com.softproideas.app.admin.datatypes.model.DataTypeDTO;
import com.softproideas.app.admin.datatypes.model.DataTypeDetailsDTO;
import com.softproideas.app.admin.datatypes.util.DataTypesUtil;

public class DataTypesMapper {

    @SuppressWarnings("unchecked")
    public static List<DataTypeDTO> mapAllDataTypesELO(AllDataTypesELO list) {
        List<DataTypeDTO> dataTypesDTOList = new ArrayList<DataTypeDTO>();

        for (Iterator<AllDataTypesELO> it = list.iterator(); it.hasNext();) {
            AllDataTypesELO row = it.next();
            DataTypeDTO dataTypesDTO = new DataTypeDTO();

            DataTypeRef ref = (DataTypeRef) row.getDataTypeEntityRef();
            dataTypesDTO.setDataTypeId(((DataTypePK) ref.getPrimaryKey()).getDataTypeId());
            dataTypesDTO.setDataTypeDescription((String) ref.getDescription());

            Integer subType = row.getSubType();
            dataTypesDTO.setSubType(subType);
            dataTypesDTO.setSubTypeName(DataTypesUtil.getSubTypeName(subType));// dataTypesDTO.setSubTypeName((String) mSubTypeMapping.getLiteral(subType));

            Integer measureClass = row.getMeasureClass();
            dataTypesDTO.setMeasureClass(measureClass);
            dataTypesDTO.setMeasureClassName(DataTypesUtil.getMeasureClassName(measureClass));// dataTypesDTO.setMeasureClassName((String) mMeasureMapping.getLiteral(measureClass));

            dataTypesDTO.setDataTypeVisId((String) ref.getNarrative());

            dataTypesDTOList.add(dataTypesDTO);
        }

        return dataTypesDTOList;
    }

    public static DataTypeDetailsDTO mapDataType(DataTypeImpl dataType) {
        DataTypeDetailsDTO dataTypeDetailsDTO = new DataTypeDetailsDTO();

        // dataTypeDetailsDTO.setDataType(dataType.getDataTypeRef()); // [TODO] remove, debug only
        dataTypeDetailsDTO.setDataTypeId(((DataTypePK) dataType.getPrimaryKey()).getDataTypeId());
        // dataTypeDetailsDTO.setDataTypePK((DataTypePK) dataType.getPrimaryKey());
        // cp-tc label "Identifier":
        dataTypeDetailsDTO.setDataTypeVisId(dataType.getDataTypeRef().getNarrative());
        // cp-tc label "Description":
        dataTypeDetailsDTO.setDataTypeDescription(dataType.getDescription());
        // cp-tc label "Type":
        Integer subType = dataType.getDataTypeRef().getSubType();
        dataTypeDetailsDTO.setSubType(subType);
        dataTypeDetailsDTO.setSubTypeName(DataTypesUtil.getSubTypeName(subType));
        // cp-tc label "Import / Export Settings":
        dataTypeDetailsDTO.setAvailableForImport(dataType.isAvailableForImport());
        dataTypeDetailsDTO.setAvailableForExport(dataType.isAvailableForExport());
        // cp-tc label "Update Settings":
        dataTypeDetailsDTO.setReadOnlyFlag(dataType.isReadOnlyFlag());
        // cp-tc label "Data Class" (for subType "Measure"):
        Integer measureClass = dataType.getMeasureClass();
        dataTypeDetailsDTO.setMeasureClass(measureClass);
        dataTypeDetailsDTO.setMeasureClassName(DataTypesUtil.getMeasureClassName(measureClass));
        // cp-tc label "Data Class Details" (for subType "Measure"):
        dataTypeDetailsDTO.setMeasureLength(dataType.getMeasureLength());
        dataTypeDetailsDTO.setMeasureValidation(dataType.getMeasureValidation());
        dataTypeDetailsDTO.setMeasureScale(dataType.getMeasureScale());
        // cp-tc label "Formula" (for subType "Virtual"):
        dataTypeDetailsDTO.setFormulaExpr(dataType.getFormulaExpr());
        // others
        dataTypeDetailsDTO.setVersionNum(dataType.getVersionNum());

        return dataTypeDetailsDTO;
    }

    public static DataTypeImpl mapDataTypeDetailsDTOToDataTypeDetailsImpl(DataTypeImpl impl, DataTypeDetailsDTO dataType, int userId) {
        // override some fields values
        impl.setVisId(dataType.getDataTypeVisId());
        impl.setAvailableForExport(dataType.getAvailableForExport());
        impl.setAvailableForImport(dataType.getAvailableForImport());
        impl.setDescription(dataType.getDataTypeDescription());
        impl.setFormulaExpr(dataType.getFormulaExpr());
        impl.setMeasureClass(dataType.getMeasureClass());
        impl.setMeasureLength(dataType.getMeasureLength());
        impl.setMeasureScale(dataType.getMeasureScale());
        impl.setMeasureValidation(dataType.getMeasureValidation());
        impl.setReadOnlyFlag(dataType.getReadOnlyFlag());
        impl.setSubType(dataType.getSubType());
        impl.setVersionNum(dataType.getVersionNum());
        return impl;
    }

}
