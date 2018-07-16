// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 18:23:03
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.tc.apps.metadataimpexp.util.digester.lookuptable;

import com.cedar.cp.tc.apps.metadataimpexp.util.digester.CommonElement;
import com.cedar.cp.tc.apps.metadataimpexp.util.digester.lookuptable.ImpExpCell;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ImpExpRow extends CommonElement {

   private List mCells = new ArrayList();


   public void addCell(ImpExpCell cell) {
      this.mCells.add(cell);
   }

   public String toXML() {
      StringBuffer strBuf = new StringBuffer();
      strBuf.append("<row>");
      Iterator iterator = this.mCells.iterator();

      while(iterator.hasNext()) {
         ImpExpCell cell = (ImpExpCell)iterator.next();
         strBuf.append(cell.toXML());
      }

      strBuf.append("</row>");
      return strBuf.toString();
   }
}
