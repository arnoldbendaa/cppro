// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 18:23:03
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.tc.apps.metadataimpexp.util.digester.lookuptable;

import com.cedar.cp.tc.apps.metadataimpexp.util.digester.CommonElement;
import com.cedar.cp.tc.apps.metadataimpexp.util.digester.lookuptable.ImpExpLookupTable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ImpExpLookupTables extends CommonElement {

   private List mLookupTables = new ArrayList();


   public void addLookupTable(ImpExpLookupTable lookupTable) {
      this.mLookupTables.add(lookupTable);
   }

   public List getLookupTables() {
      return this.mLookupTables;
   }

   public String toXML() {
      StringBuffer strBuf = new StringBuffer();
      strBuf.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
      strBuf.append("<lookupTables>");
      Iterator iterator = this.mLookupTables.iterator();

      while(iterator.hasNext()) {
         ImpExpLookupTable lookupTable = (ImpExpLookupTable)iterator.next();
         strBuf.append(lookupTable.toXML());
      }

      strBuf.append("</lookupTables>");
      return strBuf.toString();
   }
}
