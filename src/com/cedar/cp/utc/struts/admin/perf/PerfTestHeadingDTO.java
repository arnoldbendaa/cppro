// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:28:51
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.utc.struts.admin.perf;


public class PerfTestHeadingDTO {

   private boolean mShipped;
   private String mHeading;
   private String mDescription;


   public boolean isShipped() {
      return this.mShipped;
   }

   public void setShipped(boolean shipped) {
      this.mShipped = shipped;
   }

   public String getHeading() {
      return this.mHeading;
   }

   public void setHeading(String heading) {
      this.mHeading = heading;
   }

   public String getDescription() {
      return this.mDescription;
   }

   public void setDescription(String description) {
      this.mDescription = description;
   }
}
