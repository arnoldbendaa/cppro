// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:06:05
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.perftest;

import com.cedar.cp.api.perftest.PerfTest;
import com.cedar.cp.dto.perftest.PerfTestPK;
import java.io.Serializable;

public class PerfTestImpl implements PerfTest, Serializable, Cloneable {

   private Object mPrimaryKey;
   private String mVisId;
   private String mDescription;
   private String mClassName;


   public PerfTestImpl(Object paramKey) {
      this.mPrimaryKey = paramKey;
      this.mVisId = "";
      this.mDescription = "";
      this.mClassName = "";
   }

   public Object getPrimaryKey() {
      return this.mPrimaryKey;
   }

   public void setPrimaryKey(Object paramKey) {
      this.mPrimaryKey = (PerfTestPK)paramKey;
   }

   public String getVisId() {
      return this.mVisId;
   }

   public String getDescription() {
      return this.mDescription;
   }

   public String getClassName() {
      return this.mClassName;
   }

   public void setVisId(String paramVisId) {
      this.mVisId = paramVisId;
   }

   public void setDescription(String paramDescription) {
      this.mDescription = paramDescription;
   }

   public void setClassName(String paramClassName) {
      this.mClassName = paramClassName;
   }
}
