// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:06:05
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.performance;

import com.cedar.cp.util.performance.GenericPerformanceType;
import com.cedar.cp.util.performance.PerformanceDataImpl;
import com.cedar.cp.util.performance.PerformanceType;
import java.util.Arrays;

public class DataReviewPerformanceType extends GenericPerformanceType {

   public static final String TYPE = "DataEntry";
   public static final String DESCRIPTION = "Timings to extract the review data";
   public static final String LOAD_CRITERIA = "Load Criteria";
   public static final String LOAD_FORM_DATA = "Load Form Data";
   public static final String LOAD_DATA_TYPES = "Load Data Types";
   public static final String LOAD_BUDGET_LIMIT_VIOLATIONS = "Load Budget Limit Violations";
   public static final String LOAD_EDIT_DETAILS = "Load Edit Details";
   public static final String[] sIdNames = new String[]{"Load Criteria", "Load Form Data", "Load Data Types", "Load Budget Limit Violations", "Load Edit Details"};


   DataReviewPerformanceType() {
      super("DataEntry", Arrays.asList(sIdNames), "Timings to extract the review data");
   }

   public static GenericPerformanceType getInstance() {
      Object p = PerformanceDataImpl.getInstance().getPerformanceType("DataEntry");
      if(p == null) {
         p = new DataReviewPerformanceType();
         PerformanceDataImpl.getInstance().registerPerformanceType("DataEntry", (PerformanceType)p);
      }

      return (GenericPerformanceType)p;
   }

}
