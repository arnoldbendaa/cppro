// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:18
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.impl.report.pack;

import com.cedar.cp.api.base.EntityList;
import com.cedar.cp.api.report.pack.ReportPack;
import com.cedar.cp.dto.report.pack.ReportPackImpl;
import com.cedar.cp.dto.report.pack.ReportPackPK;
import com.cedar.cp.impl.report.pack.ReportPackEditorSessionImpl;
import java.util.List;

public class ReportPackAdapter implements ReportPack {

   private ReportPackImpl mEditorData;
   private ReportPackEditorSessionImpl mEditorSessionImpl;


   public ReportPackAdapter(ReportPackEditorSessionImpl e, ReportPackImpl editorData) {
      this.mEditorData = editorData;
      this.mEditorSessionImpl = e;
   }

   public void setPrimaryKey(Object key) {
      this.mEditorData.setPrimaryKey(key);
   }

   protected ReportPackEditorSessionImpl getEditorSessionImpl() {
      return this.mEditorSessionImpl;
   }

   protected ReportPackImpl getEditorData() {
      return this.mEditorData;
   }

   public Object getPrimaryKey() {
      return this.mEditorData.getPrimaryKey();
   }

   void setPrimaryKey(ReportPackPK paramKey) {
      this.mEditorData.setPrimaryKey(paramKey);
   }

   public String getVisId() {
      return this.mEditorData.getVisId();
   }

   public String getDescription() {
      return this.mEditorData.getDescription();
   }

   public boolean isGroupAttachment() {
      return this.mEditorData.isGroupAttachment();
   }

   public String getParamExample() {
      return this.mEditorData.getParamExample();
   }

   public void setVisId(String p) {
      this.mEditorData.setVisId(p);
   }

   public void setDescription(String p) {
      this.mEditorData.setDescription(p);
   }

   public void setGroupAttachment(boolean p) {
      this.mEditorData.setGroupAttachment(p);
   }

   public void setParamExample(String p) {
      this.mEditorData.setParamExample(p);
   }

   public List getSelection() {
      return this.mEditorData.getSelection();
   }

   public EntityList getAvailableDefinitions() {
      return this.mEditorSessionImpl.getConnection().getListHelper().getAllReportDefinitionsForLoggedUser();
   }

   public EntityList getAvailableDistributions() {
      return this.mEditorSessionImpl.getConnection().getListHelper().getAllDistributions();
   }
}
