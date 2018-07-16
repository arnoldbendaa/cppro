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
package com.softproideas.app.core.datatype.mapper;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.cedar.cp.api.datatype.DataTypeRef;
import com.cedar.cp.dto.datatype.AllDataTypesELO;
import com.cedar.cp.dto.datatype.DataTypePK;
import com.softproideas.app.core.datatype.model.DataTypeCoreDTO;

public class DataTypeCoreMapper {

    /**
     * Maps object of {@link DataTypeRef} to core data transfer object of {@link DataTypeCoreDTO} .
     */
    public static DataTypeCoreDTO mapDataTypeRefToDataTypeCoreDTO(DataTypeRef dataTypeRef) {
        DataTypeCoreDTO dataType = new DataTypeCoreDTO();
        DataTypePK dataTypePK = (DataTypePK) dataTypeRef.getPrimaryKey();
        dataType.setDataTypeId(dataTypePK.getDataTypeId());
        dataType.setDataTypeVisId(dataTypeRef.getNarrative());
        return dataType;
    }

    @SuppressWarnings("unchecked")
    public static List<DataTypeCoreDTO> mapAllDataTypesELO(AllDataTypesELO list) {
        List<DataTypeCoreDTO> dataTypesDTOList = new ArrayList<DataTypeCoreDTO>();

        for (Iterator<AllDataTypesELO> it = list.iterator(); it.hasNext();) {
            AllDataTypesELO row = it.next();
            DataTypeCoreDTO dataTypesDTO = mapDataTypeRefToDataTypeCoreDTO((DataTypeRef) row.getDataTypeEntityRef());
            dataTypesDTOList.add(dataTypesDTO);
        }

        return dataTypesDTOList;
    }
}
