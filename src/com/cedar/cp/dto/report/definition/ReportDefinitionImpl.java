// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:06:06
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.report.definition;

import com.cedar.cp.api.base.EntityList;
import com.cedar.cp.api.report.definition.ReportDefinition;
import com.cedar.cp.dto.report.definition.ReportDefinitionPK;
import java.io.Serializable;
import java.util.List;

public class ReportDefinitionImpl implements ReportDefinition, Serializable, Cloneable {

   private List mParams;
   private EntityList mTypes;
   private EntityList mTypeParams;
   private String mReportTypeVisId;
   private Object mPrimaryKey;
   private String mVisId;
   private String mDescription;
   private int mReportTypeId;
   private boolean mIsPublic;
   private int mVersionNum;


   public ReportDefinitionImpl(Object paramKey) {
      this.mPrimaryKey = paramKey;
      this.mVisId = "";
      this.mDescription = "";
      this.mReportTypeId = 0;
      this.mIsPublic = false;
   }

   public Object getPrimaryKey() {
      return this.mPrimaryKey;
   }

   public void setPrimaryKey(Object paramKey) {
      this.mPrimaryKey = (ReportDefinitionPK)paramKey;
   }

   public String getVisId() {
      return this.mVisId;
   }

   public String getDescription() {
      return this.mDescription;
   }

   public int getReportTypeId() {
      return this.mReportTypeId;
   }

   public boolean isIsPublic() {
      return this.mIsPublic;
   }

   public void setVersionNum(int p) {
      this.mVersionNum = p;
   }

   public int getVersionNum() {
      return this.mVersionNum;
   }

   public void setVisId(String paramVisId) {
      this.mVisId = paramVisId;
   }

   public void setDescription(String paramDescription) {
      this.mDescription = paramDescription;
   }

   public void setReportTypeId(int paramReportTypeId) {
      this.mReportTypeId = paramReportTypeId;
   }

   public void setIsPublic(boolean paramIsPublic) {
      this.mIsPublic = paramIsPublic;
   }

   public List getReportParams() {
      return this.mParams;
   }

   public void setReportParams(List params) {
      this.mParams = params;
   }

   public EntityList getReportTypes() {
      return this.mTypes;
   }

   public void setTypes(EntityList types) {
      this.mTypes = types;
   }

   public String getReportTypeVisId() {
      return this.mReportTypeVisId;
   }

   public EntityList getReportTypeParams() {
      return this.mTypeParams;
   }

   public void setTypeParams(EntityList typeParams) {
      this.mTypeParams = typeParams;
   }

   public void setReportTypeVisId(String reportTypeVisId) {
      this.mReportTypeVisId = reportTypeVisId;
   }
}
