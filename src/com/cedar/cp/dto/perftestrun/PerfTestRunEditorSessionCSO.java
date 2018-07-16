// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:06:05
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.perftestrun;

import com.cedar.cp.dto.perftestrun.PerfTestRunImpl;
import java.io.Serializable;

public class PerfTestRunEditorSessionCSO implements Serializable {

   private int mUserId;
   private PerfTestRunImpl mEditorData;


   public PerfTestRunEditorSessionCSO(int userId, PerfTestRunImpl editorData) {
      this.mUserId = userId;
      this.mEditorData = editorData;
   }

   public PerfTestRunImpl getEditorData() {
      return this.mEditorData;
   }

   public int getUserId() {
      return this.mUserId;
   }
}
