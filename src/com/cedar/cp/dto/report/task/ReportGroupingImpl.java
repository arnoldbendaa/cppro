// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:06:08
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.report.task;

import com.cedar.cp.api.base.CPFileWrapper;
import com.cedar.cp.api.report.task.ReportGrouping;
import com.cedar.cp.dto.report.task.ReportGroupingPK;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ReportGroupingImpl implements ReportGrouping, Serializable, Cloneable {

   private List<CPFileWrapper> mFiles;
   private Object mPrimaryKey;
   private int mParentTaskId;
   private int mTaskId;
   private int mDistributionType;
   private int mMessageType;
   private String mMessageId;


   public ReportGroupingImpl(Object paramKey) {
      this.mPrimaryKey = paramKey;
      this.mParentTaskId = 0;
      this.mTaskId = 0;
      this.mDistributionType = 0;
      this.mMessageType = 0;
      this.mMessageId = "";
   }

   public Object getPrimaryKey() {
      return this.mPrimaryKey;
   }

   public void setPrimaryKey(Object paramKey) {
      this.mPrimaryKey = (ReportGroupingPK)paramKey;
   }

   public int getParentTaskId() {
      return this.mParentTaskId;
   }

   public int getTaskId() {
      return this.mTaskId;
   }

   public int getDistributionType() {
      return this.mDistributionType;
   }

   public int getMessageType() {
      return this.mMessageType;
   }

   public String getMessageId() {
      return this.mMessageId;
   }

   public void setParentTaskId(int paramParentTaskId) {
      this.mParentTaskId = paramParentTaskId;
   }

   public void setTaskId(int paramTaskId) {
      this.mTaskId = paramTaskId;
   }

   public void setDistributionType(int paramDistributionType) {
      this.mDistributionType = paramDistributionType;
   }

   public void setMessageType(int paramMessageType) {
      this.mMessageType = paramMessageType;
   }

   public void setMessageId(String paramMessageId) {
      this.mMessageId = paramMessageId;
   }

   public List<CPFileWrapper> getFiles() {
      return this.mFiles == null?Collections.EMPTY_LIST:this.mFiles;
   }

   public void setFiles(List<CPFileWrapper> files) {
      this.mFiles = files;
   }

   public void addFile(CPFileWrapper file) {
      if(this.mFiles == null) {
         this.mFiles = new ArrayList();
      }

      this.mFiles.add(file);
   }
}
