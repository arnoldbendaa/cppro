// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:06:07
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.report.mappingtemplate;

import com.cedar.cp.dto.report.mappingtemplate.ReportMappingTemplateImpl;
import java.io.Serializable;

public class ReportMappingTemplateEditorSessionCSO implements Serializable {

   private int mUserId;
   private ReportMappingTemplateImpl mEditorData;


   public ReportMappingTemplateEditorSessionCSO(int userId, ReportMappingTemplateImpl editorData) {
      this.mUserId = userId;
      this.mEditorData = editorData;
   }

   public ReportMappingTemplateImpl getEditorData() {
      return this.mEditorData;
   }

   public int getUserId() {
      return this.mUserId;
   }
}
