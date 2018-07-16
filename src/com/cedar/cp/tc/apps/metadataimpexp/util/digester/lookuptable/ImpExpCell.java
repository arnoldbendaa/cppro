// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 18:23:03
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.tc.apps.metadataimpexp.util.digester.lookuptable;

import com.cedar.cp.tc.apps.metadataimpexp.util.digester.CommonElement;

public class ImpExpCell extends CommonElement {

   private String mRefColumnName;
   private String mValue;


   public String getRefColumnName() {
      return this.mRefColumnName;
   }

   public void setRefColumnName(String refColumnName) {
      this.mRefColumnName = refColumnName;
   }

   public String getValue() {
      return this.mValue;
   }

   public void setValue(String value) {
      this.mValue = value;
   }

   public String toXML() {
      StringBuffer strBuffer = new StringBuffer();
      strBuffer.append("<cell>");
      strBuffer.append("<refColumnName>").append(this.mRefColumnName).append("</refColumnName>");
      strBuffer.append("<value>").append(this.mValue).append("</value>");
      strBuffer.append("</cell>");
      return strBuffer.toString();
   }
}
