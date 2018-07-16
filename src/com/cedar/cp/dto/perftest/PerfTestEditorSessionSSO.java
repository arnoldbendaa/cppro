// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:06:05
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.perftest;

import com.cedar.cp.dto.perftest.PerfTestImpl;
import java.io.Serializable;

public class PerfTestEditorSessionSSO implements Serializable {

   private PerfTestImpl mEditorData;


   public PerfTestEditorSessionSSO() {}

   public PerfTestEditorSessionSSO(PerfTestImpl paramEditorData) {
      this.mEditorData = paramEditorData;
   }

   public void setEditorData(PerfTestImpl paramEditorData) {
      this.mEditorData = paramEditorData;
   }

   public PerfTestImpl getEditorData() {
      return this.mEditorData;
   }
}
