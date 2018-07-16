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
package com.softproideas.app.lookuptable.project.mapper;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.softproideas.app.lookuptable.project.model.LookupProjectDTO;

public class LookupProjectMapper {
    
    public static List<LookupProjectDTO> mapProjects(List<Map<String, Object>> rows) {
        List<LookupProjectDTO> list = new ArrayList<LookupProjectDTO>();
        for (Map<String, Object> row: rows) {
            LookupProjectDTO project = mapProject(row);
            list.add(project);
        }
        return list;
    }
    
    private static Integer bigDecimalToInteger(BigDecimal bigDecimal) {
        Integer result;
        result = bigDecimal == null ? null : bigDecimal.intValue();
        return result;
    }
    
    private static Double bigDecimalToDouble(BigDecimal bigDecimal) {
        Double result;
        result = bigDecimal == null ? null : bigDecimal.doubleValue();
        return result;
    }
    
    private static LookupProjectDTO mapProject(Map<String, Object> row) {
        LookupProjectDTO projectDTO = new LookupProjectDTO();        

        BigDecimal baseVal = (BigDecimal) row.get("baseval");
        projectDTO.setBaseVal(bigDecimalToDouble(baseVal));
        
        BigDecimal company = (BigDecimal) row.get("company");
        projectDTO.setCompany(bigDecimalToInteger(company));
        
        String costcentre = (String) row.get("costcentre");
        projectDTO.setCostcentre(costcentre);
        
        BigDecimal cumBaseVal = (BigDecimal) row.get("cumbaseval");
        projectDTO.setCumBaseVal(bigDecimalToDouble(cumBaseVal));
        
        String expensecode = (String) row.get("expensecode");
        projectDTO.setExpensecode(expensecode);
        
        BigDecimal period = (BigDecimal) row.get("period");
        projectDTO.setPeriod(bigDecimalToInteger(period));
        
        String project = (String) row.get("project");        
        projectDTO.setProject(project);
        
        BigDecimal qty = (BigDecimal) row.get("qty");
        projectDTO.setQty(bigDecimalToDouble(qty));
        
        BigDecimal yearno = (BigDecimal) row.get("yearno");
        projectDTO.setYearno(bigDecimalToInteger(yearno));
        
        BigDecimal cumQty = (BigDecimal) row.get("cumqty");
        projectDTO.setCumQty(bigDecimalToDouble(cumQty));
        
        return projectDTO;
    }

}
