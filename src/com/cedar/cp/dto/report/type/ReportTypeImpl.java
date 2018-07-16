// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:06:08
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.report.type;

import com.cedar.cp.api.report.type.ReportType;
import com.cedar.cp.dto.report.type.ReportTypePK;
import java.io.Serializable;
import java.util.List;

public class ReportTypeImpl implements ReportType, Serializable, Cloneable {

   private List mReportParams;
   private Object mPrimaryKey;
   private String mVisId;
   private String mDescription;
   private int mType;
   private int mVersionNum;


   public ReportTypeImpl(Object paramKey) {
      this.mPrimaryKey = paramKey;
      this.mVisId = "";
      this.mDescription = "";
      this.mType = 0;
   }

   public Object getPrimaryKey() {
      return this.mPrimaryKey;
   }

   public void setPrimaryKey(Object paramKey) {
      this.mPrimaryKey = (ReportTypePK)paramKey;
   }

   public String getVisId() {
      return this.mVisId;
   }

   public String getDescription() {
      return this.mDescription;
   }

   public int getType() {
      return this.mType;
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

   public void setType(int paramType) {
      this.mType = paramType;
   }

   public List getReportParams() {
      return this.mReportParams;
   }

   public void setReportParams(List reportParams) {
      this.mReportParams = reportParams;
   }
}
