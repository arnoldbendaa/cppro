// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 18:23:03
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.tc.apps.metadataimpexp.util.digester.xmlform;

import com.cedar.cp.tc.apps.metadataimpexp.util.digester.CommonElement;

public class InputColumnValue extends CommonElement {

   private String mName = "";
   private String mValue = "";


   public String getName() {
      return this.mName;
   }

   public void setName(String name) {
      this.mName = name;
      this.mElementName = name;
   }

   public String getValue() {
      return this.mValue;
   }

   public void setValue(String value) {
      this.mValue = value;
   }
}
