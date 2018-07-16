// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:17
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.impl.perftestrun;

import com.cedar.cp.api.perftestrun.PerfTestRun;
import com.cedar.cp.dto.perftestrun.PerfTestRunImpl;
import com.cedar.cp.dto.perftestrun.PerfTestRunPK;
import com.cedar.cp.impl.perftestrun.PerfTestRunEditorSessionImpl;
import java.sql.Timestamp;

public class PerfTestRunAdapter implements PerfTestRun {

   private PerfTestRunImpl mEditorData;
   private PerfTestRunEditorSessionImpl mEditorSessionImpl;


   public PerfTestRunAdapter(PerfTestRunEditorSessionImpl e, PerfTestRunImpl editorData) {
      this.mEditorData = editorData;
      this.mEditorSessionImpl = e;
   }

   public void setPrimaryKey(Object key) {
      this.mEditorData.setPrimaryKey(key);
   }

   protected PerfTestRunEditorSessionImpl getEditorSessionImpl() {
      return this.mEditorSessionImpl;
   }

   protected PerfTestRunImpl getEditorData() {
      return this.mEditorData;
   }

   public Object getPrimaryKey() {
      return this.mEditorData.getPrimaryKey();
   }

   void setPrimaryKey(PerfTestRunPK paramKey) {
      this.mEditorData.setPrimaryKey(paramKey);
   }

   public String getVisId() {
      return this.mEditorData.getVisId();
   }

   public String getDescription() {
      return this.mEditorData.getDescription();
   }

   public boolean isShipped() {
      return this.mEditorData.isShipped();
   }

   public Timestamp getWhenRan() {
      return this.mEditorData.getWhenRan();
   }

   public void setVisId(String p) {
      this.mEditorData.setVisId(p);
   }

   public void setDescription(String p) {
      this.mEditorData.setDescription(p);
   }

   public void setShipped(boolean p) {
      this.mEditorData.setShipped(p);
   }

   public void setWhenRan(Timestamp p) {
      this.mEditorData.setWhenRan(p);
   }
}
