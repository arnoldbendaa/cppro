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
/**
 * 
 */
package com.softproideas.app.flatformeditor.datatypes.mapper;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.cedar.cp.api.datatype.DataTypeRef;
import com.cedar.cp.dto.datatype.DataTypeRefImpl;
import com.cedar.cp.dto.model.FinanceCubeImpl;
import com.softproideas.app.admin.financecubes.model.DataTypeWithTimestampDTO;
import com.softproideas.app.core.datatype.model.DataTypeCoreDTO;

/**
 * <p>TODO: Comment me</p>
 *
 * @author Paweł Marzęda
 * @email pawma@softproideas.com
 * <p>2014 All rights reserved to IT Services Jacek Kurasiewicz</p>
 */
public class DataTypesMapper {

    /**
     * @param financeCubeImpl
     * @param modelId
     * @param financeCubeId
     * @return
     */
    public static List<DataTypeCoreDTO> mapDataTypesFromFinanceCubeImpl(FinanceCubeImpl financeCubeImpl) {
        HashMap<DataTypeRef, Timestamp> dataTypeHashMap = (HashMap<DataTypeRef, Timestamp>) financeCubeImpl.getSelectedDataTypeRefs();
        List<DataTypeCoreDTO> listDataTypeDTO = new ArrayList<DataTypeCoreDTO>();

        for (DataTypeRef iterator: dataTypeHashMap.keySet()) {
            DataTypeCoreDTO dataTypeWithTimestampDTO = new DataTypeWithTimestampDTO();

            dataTypeWithTimestampDTO.setDataTypeId(((DataTypeRefImpl) iterator).getDataTypePK().getDataTypeId());
            dataTypeWithTimestampDTO.setDataTypeVisId(iterator.getNarrative());
            dataTypeWithTimestampDTO.setDataTypeDescription(iterator.getDescription());

            listDataTypeDTO.add(dataTypeWithTimestampDTO);
        }
        return listDataTypeDTO;
    }

}
