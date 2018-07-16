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
package com.softproideas.app.core.dictionary.mapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.softproideas.common.models.DictionaryDTO;
import com.softproideas.common.models.DictionaryPropertiesDTO;

/**
 * 
 * <p>Class is responsible for maps different object related to dictionary to data transfer object (and vice-versa)</p>
 *
 * @author Łukasz Puła
 * @email lukasz.pula@softproideas.com
 * <p>2015 All rights reserved to IT Services Jacek Kurasiewicz</p>
 */
public class DictionaryOAMapper {

    public static List<DictionaryDTO> mapCompanies(List<Map<String, Object>> rows) {
        List<DictionaryDTO> list = new ArrayList<DictionaryDTO>();
        for (Map<String, Object> row: rows) {
            DictionaryDTO dictionary = mapCompany(row);
            list.add(dictionary);
        }
        return list;
    }

    public static DictionaryDTO mapCompany(Map<String, Object> row) {
        DictionaryDTO dictionary = new DictionaryDTO();
        Integer company = (Integer) row.get("COMPANY");
        dictionary.setValue(company.toString());
        
        String description = (String) row.get("DESCRIPTION");
        dictionary.setDescription(description);
        
        String currency = (String) row.get("PROPERTIES");
        DictionaryPropertiesDTO dpDTO= new DictionaryPropertiesDTO();
        dpDTO.setCurrency(currency);
        dpDTO.setPrecision(4);//default value of precision, in OA is not available
        dictionary.setDictionaryProperties(dpDTO);

        return dictionary;
    }
    
   

}
