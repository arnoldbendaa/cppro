// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:28:52
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.utc.struts.admin.report.reportdefinition.dto;

import com.cedar.cp.api.base.EntityList;
import com.cedar.cp.api.base.EntityRef;

public class ReportDefinitionDTO {

   private EntityRef modelRef = null;
   public int mModelId;
   public String modelVisId;
   public int mRAStructureElementId;
   public String mRAVisId;
   public String mRADescription;
   public String mRAParam;
   private String mVisId = null;
   private String mDescription = null;
   private int mReportType = 1;
   private boolean mIsPublic = false;


   public int getModelId() {
      return this.mModelId;
   }

   public void setModelId(int modelId) {
      this.mModelId = modelId;
   }

   public String getVisId() {
      return this.mVisId;
   }

   public void setVisId(String visId) {
      this.mVisId = visId;
   }

   public String getDescription() {
      return this.mDescription;
   }

   public void setDescription(String description) {
      this.mDescription = description;
   }

   public int getReportType() {
      return this.mReportType;
   }

   public void setReportType(int reportType) {
      this.mReportType = reportType;
   }

   public boolean isPublic() {
      return this.mIsPublic;
   }

   public void setPublic(boolean isPublic) {
      this.mIsPublic = isPublic;
   }

   public String getModelVisId() {
      return this.modelRef != null?this.modelRef.getDisplayText():this.modelVisId;
   }

   public EntityRef getModelRef() {
      return this.modelRef;
   }

   public void setModelRef(EntityRef modelRef) {
      this.modelRef = modelRef;
   }

   public void setRootRa(EntityList data) {
      this.setRAStructureElementId(((Integer)data.getValueAt(0, "StructureElementId")).intValue());
      this.setRAVisId(data.getValueAt(0, "VisId").toString());
      this.setRADescription(data.getValueAt(0, "Description").toString());
   }

   public int getRAStructureElementId() {
      return this.mRAStructureElementId;
   }

   public void setRAStructureElementId(int RAStructureElementId) {
      this.mRAStructureElementId = RAStructureElementId;
   }

   public String getRAVisId() {
      return this.mRAVisId;
   }

   public void setRAVisId(String RAVisId) {
      this.mRAVisId = RAVisId;
   }

   public String getRADescription() {
      return this.mRADescription;
   }

   public void setRADescription(String RADescription) {
      this.mRADescription = RADescription;
   }

   public String getRAParam() {
      return this.mRAParam;
   }

   public void setRAParam(String RAParam) {
      this.mRAParam = RAParam;
   }
}
