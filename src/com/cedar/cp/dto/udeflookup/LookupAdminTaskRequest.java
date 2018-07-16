// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:06:10
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.udeflookup;

import com.cedar.cp.dto.base.AbstractTaskRequest;
import java.util.ArrayList;
import java.util.List;

public class LookupAdminTaskRequest extends AbstractTaskRequest {

   public static int CREATE_LOOKUP_TABLES = 0;
   public static int DROP_LOOKUP_TABLES = 1;
   public static int ALTER_LOOKUP_TABLES = 2;
   private static String[] sActionText = new String[]{"create", "drop", "alter"};
   private int mLookupId;
   private int mAction;
   private String mGenTableName;
   private int mPrevTimeLvl;
   private int mPrevYearMonthStart;
   private boolean mPrevTimeRange;


   public LookupAdminTaskRequest(int lookupId, String genTablename, int action, int prevTimeLvl, int prevYearMonthStart, boolean prevTimeRange) {
      this.mLookupId = lookupId;
      this.mAction = action;
      this.mGenTableName = genTablename;
      this.mPrevTimeLvl = prevTimeLvl;
      this.mPrevYearMonthStart = prevYearMonthStart;
      this.mPrevTimeRange = prevTimeRange;
   }

   public int getAction() {
      return this.mAction;
   }

   public int getLookupId() {
      return this.mLookupId;
   }

   public String getGenTableName() {
      return this.mGenTableName;
   }

   public int getPrevTimeLvl() {
      return this.mPrevTimeLvl;
   }

   public int getPrevYearMonthStart() {
      return this.mPrevYearMonthStart;
   }

   public boolean getPrevTimeRange() {
      return this.mPrevTimeRange;
   }

   public String getService() {
      return "com.cedar.cp.ejb.base.async.lookup.LookupTableAdminTask";
   }

   public List toDisplay() {
      ArrayList l = new ArrayList();
      l.add("Lookup Admin - " + sActionText[this.mAction]);
      return l;
   }

}
