// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:06:06
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.report.definition;

import com.cedar.cp.dto.report.definition.ReportDefinitionImpl;
import java.io.Serializable;

public class ReportDefinitionEditorSessionCSO implements Serializable {

   private int mUserId;
   private ReportDefinitionImpl mEditorData;


   public ReportDefinitionEditorSessionCSO(int userId, ReportDefinitionImpl editorData) {
      this.mUserId = userId;
      this.mEditorData = editorData;
   }

   public ReportDefinitionImpl getEditorData() {
      return this.mEditorData;
   }

   public int getUserId() {
      return this.mUserId;
   }
}
