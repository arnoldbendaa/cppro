// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:18
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.impl.report.type;

import com.cedar.cp.api.report.type.ReportType;
import com.cedar.cp.dto.report.type.ReportTypeImpl;
import com.cedar.cp.dto.report.type.ReportTypePK;
import com.cedar.cp.impl.report.type.ReportTypeEditorSessionImpl;
import java.util.Collections;
import java.util.List;

public class ReportTypeAdapter implements ReportType {

   private ReportTypeImpl mEditorData;
   private ReportTypeEditorSessionImpl mEditorSessionImpl;


   public ReportTypeAdapter(ReportTypeEditorSessionImpl e, ReportTypeImpl editorData) {
      this.mEditorData = editorData;
      this.mEditorSessionImpl = e;
   }

   public void setPrimaryKey(Object key) {
      this.mEditorData.setPrimaryKey(key);
   }

   protected ReportTypeEditorSessionImpl getEditorSessionImpl() {
      return this.mEditorSessionImpl;
   }

   protected ReportTypeImpl getEditorData() {
      return this.mEditorData;
   }

   public Object getPrimaryKey() {
      return this.mEditorData.getPrimaryKey();
   }

   void setPrimaryKey(ReportTypePK paramKey) {
      this.mEditorData.setPrimaryKey(paramKey);
   }

   public String getVisId() {
      return this.mEditorData.getVisId();
   }

   public String getDescription() {
      return this.mEditorData.getDescription();
   }

   public int getType() {
      return this.mEditorData.getType();
   }

   public void setVisId(String p) {
      this.mEditorData.setVisId(p);
   }

   public void setDescription(String p) {
      this.mEditorData.setDescription(p);
   }

   public void setType(int p) {
      this.mEditorData.setType(p);
   }

   public List getReportParams() {
      if(this.mEditorData.getReportParams() == null) {
         this.mEditorData.setReportParams(Collections.EMPTY_LIST);
      }

      return this.mEditorData.getReportParams();
   }
}
