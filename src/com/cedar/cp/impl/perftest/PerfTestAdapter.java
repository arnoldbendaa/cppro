// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:17
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.impl.perftest;

import com.cedar.cp.api.perftest.PerfTest;
import com.cedar.cp.dto.perftest.PerfTestImpl;
import com.cedar.cp.dto.perftest.PerfTestPK;
import com.cedar.cp.impl.perftest.PerfTestEditorSessionImpl;

public class PerfTestAdapter implements PerfTest {

   private PerfTestImpl mEditorData;
   private PerfTestEditorSessionImpl mEditorSessionImpl;


   public PerfTestAdapter(PerfTestEditorSessionImpl e, PerfTestImpl editorData) {
      this.mEditorData = editorData;
      this.mEditorSessionImpl = e;
   }

   public void setPrimaryKey(Object key) {
      this.mEditorData.setPrimaryKey(key);
   }

   protected PerfTestEditorSessionImpl getEditorSessionImpl() {
      return this.mEditorSessionImpl;
   }

   protected PerfTestImpl getEditorData() {
      return this.mEditorData;
   }

   public Object getPrimaryKey() {
      return this.mEditorData.getPrimaryKey();
   }

   void setPrimaryKey(PerfTestPK paramKey) {
      this.mEditorData.setPrimaryKey(paramKey);
   }

   public String getVisId() {
      return this.mEditorData.getVisId();
   }

   public String getDescription() {
      return this.mEditorData.getDescription();
   }

   public String getClassName() {
      return this.mEditorData.getClassName();
   }

   public void setVisId(String p) {
      this.mEditorData.setVisId(p);
   }

   public void setDescription(String p) {
      this.mEditorData.setDescription(p);
   }

   public void setClassName(String p) {
      this.mEditorData.setClassName(p);
   }
}
