// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:28:57
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.utc.struts.report;

import com.cedar.cp.utc.common.CPForm;
import java.util.List;

public class ReportForm extends CPForm {

   private List mReports;
   private boolean mFlatLayout;


   public boolean isFlatLayout() {
      return this.mFlatLayout;
   }

   public void setFlatLayout(boolean flatLayout) {
      this.mFlatLayout = flatLayout;
   }

   public List getReports() {
      return this.mReports;
   }

   public void setReports(List reports) {
      this.mReports = reports;
   }
}
