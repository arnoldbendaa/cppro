// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:48
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.dataEntry;

import com.cedar.cp.api.task.TaskRequest;
import com.cedar.cp.dto.base.AbstractTaskRequest;
import com.cedar.cp.dto.model.ModelPK;
import java.util.ArrayList;
import java.util.List;

public class RechargeTaskRequest extends AbstractTaskRequest implements TaskRequest {

   private int mModelId;
   private String mModelVisId;
   private int mFinanceCubeId;
   private String mFinanceCubeVisId;
   private int mCalendarStructureElementId;
   private boolean mReportOnly;
   private boolean mReportTargetCells;
   private boolean mReportSourceCells;
   private int[] mRechargeIds;


   public RechargeTaskRequest(ModelPK pk) {
      this.addExclusiveAccess(pk.toString());
   }

   public List toDisplay() {
      ArrayList l = new ArrayList();
      l.add("FinanceCube=" + this.getFinanceCubeVisId());
      return l;
   }

   public String getService() {
      return "com.cedar.cp.ejb.base.async.RechargeTask";
   }

   public void setModelId(int modelId) {
      this.mModelId = modelId;
   }

   public void setModelVisId(String visId) {
      this.mModelVisId = visId;
   }

   public void setFinanceCubeId(int financeCubeId) {
      this.mFinanceCubeId = financeCubeId;
   }

   public void setFinanceCubeVisId(String visId) {
      this.mFinanceCubeVisId = visId;
   }

   public void setCalendarStructureElementId(int calenderStructureElementId) {
      this.mCalendarStructureElementId = calenderStructureElementId;
   }

   public void setReportOnly(boolean report) {
      this.mReportOnly = report;
   }

   public void setReportSourceCells(boolean source) {
      this.mReportSourceCells = source;
   }

   public void setReportTargetCells(boolean target) {
      this.mReportTargetCells = target;
   }

   public void setRechargeIds(int[] rechargeIds) {
      this.mRechargeIds = rechargeIds;
   }

   public int getModelId() {
      return this.mModelId;
   }

   public String getModelVisId() {
      return this.mModelVisId;
   }

   public int getFinanceCubeId() {
      return this.mFinanceCubeId;
   }

   public int getCalendarStructureElementId() {
      return this.mCalendarStructureElementId;
   }

   public String getFinanceCubeVisId() {
      return this.mFinanceCubeVisId;
   }

   public boolean isReportOnly() {
      return this.mReportOnly;
   }

   public boolean isReportTargetCells() {
      return this.mReportTargetCells;
   }

   public boolean isReportSourceCells() {
      return this.mReportSourceCells;
   }

   public int[] getRechargeIds() {
      return this.mRechargeIds;
   }
}
