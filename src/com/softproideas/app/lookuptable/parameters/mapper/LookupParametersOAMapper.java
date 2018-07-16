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

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.softproideas.app.lookuptable.parameters.model.LookupDimensionOADTO;
import com.softproideas.app.lookuptable.parameters.model.LookupHierachyOADTO;

public class LookupParametersOAMapper {

    public static List<LookupHierachyOADTO> mapHierarchies(List<Map<String, Object>> rows) {
        List<LookupHierachyOADTO> list = new ArrayList<LookupHierachyOADTO>();
        for (Map<String, Object> row: rows) {
            LookupHierachyOADTO hierachy = mapHierachy(row);
            list.add(hierachy);
        }
        return list;
    }

    private static LookupHierachyOADTO mapHierachy(Map<String, Object> row) {
        LookupHierachyOADTO hierarchy = new LookupHierachyOADTO();

        String visId = (String) row.get("VIS_ID");
        hierarchy.setVisId(visId);
        
        String description = (String) row.get("DESCRIPTION");
        hierarchy.setDescription(description);        
        
        return hierarchy;
    }

    public static List<LookupDimensionOADTO> mapDimensions(List<Map<String, Object>> rows) {
        List<LookupDimensionOADTO> list = new ArrayList<LookupDimensionOADTO>();
        for (Map<String, Object> row: rows) {
            LookupDimensionOADTO hierachy = mapDimension(row);
            list.add(hierachy);
        }
        return list;
    }

    private static LookupDimensionOADTO mapDimension(Map<String, Object> row) {
        LookupDimensionOADTO dimension = new LookupDimensionOADTO();

        String group = (String) row.get("HIER_ID");
        dimension.setGroup(group);

        String parent = (String) row.get("PARENT");
        dimension.setParent(parent);
        
        String visId = (String) row.get("ELEM");
        dimension.setVisId(visId);
        
        String description = (String) row.get("DESCR");
        dimension.setDescription(description);
        
        String leaf = (String) row.get("IS_LEAF");
        dimension.setLeaf(leaf.equals("Y"));
        
        Integer status = (Integer) row.get("STATUS");
        dimension.setStatus(status.intValue());
        return dimension;
    }

}
