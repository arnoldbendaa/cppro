// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 18:23:03
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.tc.apps.metadataimpexp.util.digester.xmlform;

import com.cedar.cp.tc.apps.metadataimpexp.util.digester.CommonElement;

public class Subset extends CommonElement {

   private String mColumn;
   private String mValue;


   public String getColumn() {
      return this.mColumn;
   }

   public void setColumn(String column) {
      this.mColumn = column;
   }

   public String getValue() {
      return this.mValue;
   }

   public void setValue(String value) {
      this.mValue = value;
   }
}
