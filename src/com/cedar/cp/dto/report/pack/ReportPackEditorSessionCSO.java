// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:06:08
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.report.pack;

import com.cedar.cp.dto.report.pack.ReportPackImpl;
import java.io.Serializable;

public class ReportPackEditorSessionCSO implements Serializable {

   private int mUserId;
   private ReportPackImpl mEditorData;


   public ReportPackEditorSessionCSO(int userId, ReportPackImpl editorData) {
      this.mUserId = userId;
      this.mEditorData = editorData;
   }

   public ReportPackImpl getEditorData() {
      return this.mEditorData;
   }

   public int getUserId() {
      return this.mUserId;
   }
}
