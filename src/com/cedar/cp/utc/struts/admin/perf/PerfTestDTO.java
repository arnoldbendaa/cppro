// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:28:51
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.utc.struts.admin.perf;

import java.util.List;

public class PerfTestDTO {

   private String mTestName;
   private String mTestDescription;
   private List mTestResults;


   public String getTestName() {
      return this.mTestName;
   }

   public void setTestName(String testName) {
      this.mTestName = testName;
   }

   public String getTestDescription() {
      return this.mTestDescription;
   }

   public void setTestDescription(String testDescription) {
      this.mTestDescription = testDescription;
   }

   public List getTestResults() {
      return this.mTestResults;
   }

   public void setTestResults(List testResults) {
      this.mTestResults = testResults;
   }
}
