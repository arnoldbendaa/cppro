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
package com.softproideas.app.lookuptable.currency.mapper;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.softproideas.app.lookuptable.currency.model.LookupCurrencyDTO;

/**
 * 
 * <p>Class is responsible for maps different object related to currency to data transfer object (and vice-versa)</p>
 *
 * @author Łukasz Puła
 * @email lukasz.pula@softproideas.com
 * <p>2015 All rights reserved to IT Services Jacek Kurasiewicz</p>
 */
public class LookupCurrencyMapper {

    public static List<LookupCurrencyDTO> mapCurrencies(List<Map<String, Object>> rows) {
        List<LookupCurrencyDTO> list = new ArrayList<LookupCurrencyDTO>();
        for (Map<String, Object> row: rows) {
            LookupCurrencyDTO lookupCurrency = mapCurrency(row);
            list.add(lookupCurrency);
        }
        return list;
    }

    public static LookupCurrencyDTO mapCurrency(Map<String, Object> row) {
        LookupCurrencyDTO lookupCurrency = new LookupCurrencyDTO();
        
        String uuid = (String) row.get("CURRENCY_UUID");
        lookupCurrency.setCurrencyUUID(UUID.fromString(uuid));
        
        BigDecimal year = (BigDecimal) row.get("YEAR");
        lookupCurrency.setYear(year.intValue());
        
        BigDecimal period = (BigDecimal) row.get("PERIOD");
        lookupCurrency.setPeriod(period.intValue());
        
        BigDecimal company = (BigDecimal) row.get("COMPANY");
        lookupCurrency.setCompany(company.intValue());
        
        String currency = (String) row.get("CURRENCY");
        lookupCurrency.setCurrency(currency);
        
        String fieldValue = (String) row.get("FIELD_VALUE");
        lookupCurrency.setFieldValue(fieldValue);
        
        return lookupCurrency;
    }


}
