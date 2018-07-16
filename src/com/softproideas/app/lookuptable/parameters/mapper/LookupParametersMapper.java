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
package com.softproideas.app.lookuptable.parameters.mapper;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.softproideas.app.lookuptable.parameters.model.LookupParameterDTO;
import com.softproideas.common.enums.LookupParameterStatus;

public class LookupParametersMapper {

    public static List<LookupParameterDTO> mapParameters(List<Map<String, Object>> rows) {
        List<LookupParameterDTO> list = new ArrayList<LookupParameterDTO>();
        for (Map<String, Object> row: rows) {
            LookupParameterDTO lookupParameter = mapParameter(row);
            list.add(lookupParameter);
        }
        return list;
    }

    private static LookupParameterDTO mapParameter(Map<String, Object> row) {
        LookupParameterDTO lookupParameter = new LookupParameterDTO();

        String uuid = (String) row.get("PARAMETER_UUID");
        lookupParameter.setParameterUUID(UUID.fromString(uuid));
        
        String costcentre = (String) row.get("COSTCENTRE");
        lookupParameter.setCostCentre(costcentre);
        
        BigDecimal status = (BigDecimal) row.get("STATUS");
        lookupParameter.setStatus(LookupParameterStatus.valueOf(status.intValue()));
        
        BigDecimal company = (BigDecimal) row.get("COMPANY");
        lookupParameter.setCompany(company.intValue());
        
        String fieldName = (String) row.get("FIELD_NAME");
        lookupParameter.setFieldName(fieldName);    
        
        String fieldValue = (String) row.get("FIELD_VALUE");
        lookupParameter.setFieldValue(fieldValue);
        
        BigDecimal rowIndex = (BigDecimal) row.get("ROW_INDEX");
        lookupParameter.setRowIndex(rowIndex.intValue());
        
        return lookupParameter;
    }

}
