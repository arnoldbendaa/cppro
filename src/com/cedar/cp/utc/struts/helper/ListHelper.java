// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:28:55
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.utc.struts.helper;

import com.cedar.cp.utc.struts.topdown.LimitsDTO;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class ListHelper {

   public static List getListOfLimitsFromList(List passedLimits) {
      LimitsDTO imp = null;
      List innerList = null;
      boolean innserSize = false;
      BigDecimal bd = null;
      int size = passedLimits.size();
      ArrayList returnList = new ArrayList(size);

      for(int i = 0; i < size; ++i) {
         imp = new LimitsDTO();
         innerList = (List)passedLimits.get(i);
         int var9 = innerList.size();
         imp.setRespAreaId((Integer)innerList.get(1));

         for(int j = 2; j < var9; ++j) {
            if(j <= var9 - 4) {
               imp.addStructureElement((String)innerList.get(j));
            } else if(j == var9 - 3) {
               imp.setMinValue((BigDecimal)innerList.get(j));
            } else if(j == var9 - 2) {
               imp.setMaxValue((BigDecimal)innerList.get(j));
            } else if(j == var9 - 1) {
               bd = (BigDecimal)innerList.get(j);
               if(bd != null) {
                  imp.setCurrentValue(bd.doubleValue());
               }
            }
         }

         returnList.add(imp);
      }

      return returnList;
   }
}
