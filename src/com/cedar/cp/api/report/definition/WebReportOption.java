// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:04:20
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.api.report.definition;

import com.cedar.cp.api.report.pack.ReportPackOption;
import java.io.Serializable;

public class WebReportOption implements Serializable {

   private String mReportVisId;
   private int mDepth;
   private boolean mSelf;
   private boolean mGroup;
   private int mModelId;
   private int mCCId;
   private String mCCVisId;
   private String mParam;
   private String mMessage;


   public String getReportVisId() {
      return this.mReportVisId;
   }

   public void setReportVisId(String reportVisId) {
      this.mReportVisId = reportVisId;
   }

   public int getDepth() {
      return this.mDepth;
   }

   public void setDepth(int depth) {
      this.mDepth = depth;
   }

   public void setDepth(String depth) {
      try {
         this.mDepth = Integer.parseInt(depth);
      } catch (NumberFormatException var3) {
         if("All".equalsIgnoreCase(depth)) {
            this.setDepth(99);
         } else {
            this.setDepth(-1);
         }
      }

   }

   public boolean isSelf() {
      return this.mSelf;
   }

   public void setSelf(String option) {
      if(option == null) {
         this.mSelf = true;
      }

      this.mSelf = "self".equals(option);
   }

   public void setSelf(boolean self) {
      this.mSelf = self;
   }

   public boolean isGroup() {
      return this.mGroup;
   }

   public void setGroup(boolean group) {
      this.mGroup = group;
   }

   public void setGroup(String group) {
      if(group == null) {
         this.mGroup = true;
      }

      this.mGroup = "group".equals(group);
   }

   public int getModelId() {
      return this.mModelId;
   }

   public void setModelId(String modelId) {
      this.setModelId(Integer.parseInt(modelId));
   }

   public void setModelId(int modelId) {
      this.mModelId = modelId;
   }

   public int getCCId() {
      return this.mCCId;
   }

   public void setCCId(String CCId) {
      this.setCCId(Integer.parseInt(CCId));
   }

   public void setCCId(int CCId) {
      this.mCCId = CCId;
   }

   public String getCCVisId() {
      return this.mCCVisId;
   }

   public void setCCVisId(String CCVisId) {
      this.mCCVisId = CCVisId;
   }

   public String getParam() {
      return this.mParam != null && this.mParam.length() > 0?this.mParam:null;
   }

   public void setParam(String param) {
      if(param != null && param.length() > 0) {
         this.mParam = param;
      }

   }

   public String getMessage() {
      return this.mMessage;
   }

   public void setMessage(String message) {
      if(message != null && message.length() > 0) {
         this.mMessage = message;
      }

   }

   public ReportPackOption getReportOption() {
      return this.getParam() == null?null:new ReportPackOption(this.getMessage(), this.getParam());
   }
}
