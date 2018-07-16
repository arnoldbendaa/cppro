// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:06:05
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.perftestrun;

import com.cedar.cp.api.perftestrun.PerfTestRun;
import com.cedar.cp.api.perftestrun.PerfTestRunResult;
import com.cedar.cp.dto.perftestrun.PerfTestRunPK;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class PerfTestRunImpl implements PerfTestRun, Serializable, Cloneable {

   private List mRunResults;
   private Object mPrimaryKey;
   private String mVisId;
   private String mDescription;
   private boolean mShipped;
   private Timestamp mWhenRan;


   public PerfTestRunImpl(Object paramKey) {
      this.mPrimaryKey = paramKey;
      this.mVisId = "";
      this.mDescription = "";
      this.mShipped = false;
      this.mWhenRan = null;
      this.mRunResults = new ArrayList();
   }

   public Object getPrimaryKey() {
      return this.mPrimaryKey;
   }

   public void setPrimaryKey(Object paramKey) {
      this.mPrimaryKey = (PerfTestRunPK)paramKey;
   }

   public String getVisId() {
      return this.mVisId;
   }

   public String getDescription() {
      return this.mDescription;
   }

   public boolean isShipped() {
      return this.mShipped;
   }

   public Timestamp getWhenRan() {
      return this.mWhenRan;
   }

   public void setVisId(String paramVisId) {
      this.mVisId = paramVisId;
   }

   public void setDescription(String paramDescription) {
      this.mDescription = paramDescription;
   }

   public void setShipped(boolean paramShipped) {
      this.mShipped = paramShipped;
   }

   public void setWhenRan(Timestamp paramWhenRan) {
      this.mWhenRan = paramWhenRan;
   }

   public void addRunResult(PerfTestRunResult runResult) {
      this.mRunResults.add(runResult);
   }

   public List getRunResults() {
      return this.mRunResults;
   }
}
