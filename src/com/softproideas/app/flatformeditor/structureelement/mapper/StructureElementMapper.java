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
package com.softproideas.app.flatformeditor.structureelement.mapper;

import java.util.ArrayList;

import com.cedar.cp.util.Pair;
import com.softproideas.app.flatformeditor.structureelement.model.DateStructureElementDTO;

/**
 * <p>TODO: Comment me</p>
 *
 * @author Paweł Marzęda
 * @email pawma@softproideas.com
 * <p>2015 All rights reserved to IT Services Jacek Kurasiewicz</p>
 */
public class StructureElementMapper {

    /**
     * @param list
     * @return
     */
    @SuppressWarnings("rawtypes")
    public static ArrayList<DateStructureElementDTO> mapPairList(ArrayList<Pair> list) {
        ArrayList<DateStructureElementDTO> resultList = new ArrayList<DateStructureElementDTO>();
        for(Pair pair: list){
            DateStructureElementDTO dataStructureElementDTO = new DateStructureElementDTO();
            String yearAndMonthText = (String) pair.getChild1();
            String [] yearAndMonthTable = yearAndMonthText.split("/");
            if(yearAndMonthTable.length > 2) {
                dataStructureElementDTO.setStructureElementVisId(yearAndMonthTable[2]);
                dataStructureElementDTO.setParentVisId(yearAndMonthTable[1]);
            } else {
                dataStructureElementDTO.setStructureElementVisId(yearAndMonthTable[1]);
            }
            dataStructureElementDTO.setStructureElementDescription((String) pair.getChild2());
            resultList.add(dataStructureElementDTO);
        }
        return resultList;
    }
    
}
