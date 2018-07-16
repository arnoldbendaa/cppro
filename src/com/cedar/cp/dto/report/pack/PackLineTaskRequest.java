// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:06:08
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.report.pack;

import com.cedar.cp.api.base.EntityList;
import com.cedar.cp.api.base.EntityRef;
import com.cedar.cp.api.report.pack.ReportPackOption;
import com.cedar.cp.api.task.TaskRequest;
import com.cedar.cp.dto.base.AbstractTaskRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class PackLineTaskRequest extends AbstractTaskRequest implements TaskRequest {

   private List<EntityList> mRespArea;
   private int mReportType;
   private EntityRef mDistribution;
   private boolean mGroup;
   private boolean mSelf;
   private ReportPackOption mOption;
   private String mModelId;
   private int mFormId;
   private String mDataType;
   private Map<Integer, Integer> mSelectionCriteria;
   private int mDepth;
   private List mContextDims;
   private int mDeploymentId;
   private String mColumnSettings;
   private byte[] mTemplate;


   public List toDisplay() {
      ArrayList l = new ArrayList();
      if(this.getReportType() == 0) {
         l.add("Invalid Report Type");
      }

      if(this.getReportType() == 1) {
         l.add("Form Report");
         l.add("model=" + this.getModelId() + " form=" + this.getFormId() + " respArea=" + this.getRespArea().size() + " criteria=" + this.mSelectionCriteria + " dataType=" + this.getDataType() + " depth=" + this.getDepth());
      }

      if(this.getReportType() == 2) {
         l.add("Mapping Report Type");
         l.add("model=" + this.getModelId() + " respArea=" + this.getRespArea().size());
      }

      if(this.getReportType() == 3) {
         l.add("Cell Calc Report Type");
         l.add("model=" + this.getModelId() + " respArea=" + this.getRespArea().size());
      }

      return l;
   }

   public String getService() {
      return "com.cedar.cp.ejb.base.async.reportpack.PackLineTask";
   }

   public int getReportType() {
      return this.mReportType;
   }

   public void setReportType(int reportType) {
      this.mReportType = reportType;
   }

   public List<EntityList> getRespArea() {
      return this.mRespArea;
   }

   public void setRespArea(List<EntityList> respArea) {
      this.mRespArea = respArea;
   }

   public Map<Integer, Integer> getSelectionCriteria() {
      return this.mSelectionCriteria;
   }

   public void setSelectionCriteria(Map<Integer, Integer> selectionCriteria) {
      this.mSelectionCriteria = selectionCriteria;
   }

   public String getModelId() {
      return this.mModelId;
   }

   public void setModelId(String modelId) {
      this.mModelId = modelId;
   }

   public int getFormId() {
      return this.mFormId;
   }

   public void setFormId(int formId) {
      this.mFormId = formId;
   }

   public String getDataType() {
      return this.mDataType;
   }

   public void setDataType(String dataType) {
      this.mDataType = dataType;
   }

   public int getDepth() {
      return this.mDepth;
   }

   public void setDepth(int depth) {
      this.mDepth = depth;
   }

   public byte[] getTemplate() {
      return this.mTemplate;
   }

   public void setTemplate(byte[] template) {
      this.mTemplate = template;
   }

   public EntityRef getDistribution() {
      return this.mDistribution;
   }

   public void setDistribution(EntityRef distribution) {
      this.mDistribution = distribution;
   }

   public boolean isSelf() {
      return this.mSelf;
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

   public ReportPackOption getOption() {
      return this.mOption;
   }

   public void setOption(ReportPackOption option) {
      this.mOption = option;
   }

   public List getContextDims() {
      return this.mContextDims;
   }

   public void setContextDims(List contextDims) {
      this.mContextDims = contextDims;
   }

   public int getDeploymentId() {
      return this.mDeploymentId;
   }

   public void setDeploymentId(int deploymentId) {
      this.mDeploymentId = deploymentId;
   }

   public String getColumnSettings() {
      return this.mColumnSettings;
   }

   public void setColumnSettings(String columnSettings) {
      this.mColumnSettings = columnSettings;
   }
}
