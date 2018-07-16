// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:04:21
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.api.xmlform.rebuild;

import java.io.Serializable;

public class RebuildWrapper implements Serializable {

   private int mFormType;
   private String mXMLData;
   private String mSelectionHeading;


   public int getFormType() {
      return this.mFormType;
   }

   public void setFormType(int formType) {
      this.mFormType = formType;
   }

   public String getXMLData() {
      return this.mXMLData;
   }

   public void setXMLData(String XMLData) {
      this.mXMLData = XMLData;
   }

   public String getSelectionHeading() {
      return this.mSelectionHeading;
   }

   public void setSelectionHeading(String selectionHeading) {
      this.mSelectionHeading = selectionHeading;
   }
}
