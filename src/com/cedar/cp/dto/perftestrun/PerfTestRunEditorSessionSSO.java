// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:06:05
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.perftestrun;

import com.cedar.cp.dto.perftestrun.PerfTestRunImpl;
import java.io.Serializable;

public class PerfTestRunEditorSessionSSO implements Serializable {

   private PerfTestRunImpl mEditorData;


   public PerfTestRunEditorSessionSSO() {}

   public PerfTestRunEditorSessionSSO(PerfTestRunImpl paramEditorData) {
      this.mEditorData = paramEditorData;
   }

   public void setEditorData(PerfTestRunImpl paramEditorData) {
      this.mEditorData = paramEditorData;
   }

   public PerfTestRunImpl getEditorData() {
      return this.mEditorData;
   }
}
