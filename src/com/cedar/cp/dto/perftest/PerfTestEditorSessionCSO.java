// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:06:05
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.perftest;

import com.cedar.cp.dto.perftest.PerfTestImpl;
import java.io.Serializable;

public class PerfTestEditorSessionCSO implements Serializable {

   private int mUserId;
   private PerfTestImpl mEditorData;


   public PerfTestEditorSessionCSO(int userId, PerfTestImpl editorData) {
      this.mUserId = userId;
      this.mEditorData = editorData;
   }

   public PerfTestImpl getEditorData() {
      return this.mEditorData;
   }

   public int getUserId() {
      return this.mUserId;
   }
}
