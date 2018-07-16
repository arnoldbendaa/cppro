// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:04:17
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.api.dataEntry;

import com.cedar.cp.util.xmlform.FormConfig;
import com.cedar.cp.util.xmlform.inputs.FormDataInputModel;
import java.io.Serializable;
import java.util.Map;

public class CellCalculationDetails implements Serializable {

   private FormConfig mFormConfig;
   private FormDataInputModel mPreviousData;
   private Map mSummaryData;


   public FormConfig getFormConfig() {
      return this.mFormConfig;
   }

   public void setFormConfig(FormConfig formConfig) {
      this.mFormConfig = formConfig;
   }

   public FormDataInputModel getPreviousData() {
      return this.mPreviousData;
   }

   public void setPreviousData(FormDataInputModel previousData) {
      this.mPreviousData = previousData;
   }

   public Map getSummaryData() {
      return this.mSummaryData;
   }

   public void setSummaryData(Map summaryData) {
      this.mSummaryData = summaryData;
   }
}
