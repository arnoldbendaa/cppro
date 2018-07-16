// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:04:20
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.api.report.pack;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

public class ReportPackProjection implements Serializable {

   private String mIdentifier;
   private String mDescription;
   private List mData;


   public String getIdentifier() {
      return this.mIdentifier;
   }

   public void setIdentifier(String identifier) {
      this.mIdentifier = identifier;
   }

   public String getDescription() {
      return this.mDescription;
   }

   public void setDescription(String description) {
      this.mDescription = description;
   }

   public List getData() {
      return this.mData == null?Collections.EMPTY_LIST:this.mData;
   }

   public void setData(List data) {
      this.mData = data;
   }
}
