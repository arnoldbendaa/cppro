// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:06:13
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.xmlform.rebuild;

import com.cedar.cp.api.task.TaskRequest;
import com.cedar.cp.dto.base.AbstractTaskRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class FormRebuildTaskRequest extends AbstractTaskRequest implements TaskRequest {

   private List<Integer> mRespArea;
   private Map<Integer, Integer> mSelectionCriteria;
   private int mModelId;
   private int mBudgetCycleId;
   private int mFormId;
   private String mDataType;


   public List toDisplay() {
      ArrayList l = new ArrayList();
      l.add("model=" + this.getModelId() + " cycle=" + this.getBudgetCycleId() + " form=" + this.getFormId() + " respArea=" + this.getRespArea().size() + " criteria=" + this.mSelectionCriteria + " dataType=" + this.getDataType());
      return l;
   }

   public String getService() {
      return "com.cedar.cp.ejb.base.async.formrebuild.RebuildTask";
   }

   public List<Integer> getRespArea() {
      return this.mRespArea;
   }

   public void setRespArea(List<Integer> respArea) {
      this.mRespArea = respArea;
   }

   public Map<Integer, Integer> getSelectionCriteria() {
      return this.mSelectionCriteria;
   }

   public void setSelectionCriteria(Map<Integer, Integer> selectionCriteria) {
      this.mSelectionCriteria = selectionCriteria;
   }

   public int getModelId() {
      return this.mModelId;
   }

   public void setModelId(int modelId) {
      this.mModelId = modelId;
   }

   public int getBudgetCycleId() {
      return this.mBudgetCycleId;
   }

   public void setBudgetCycleId(int budgetCycleId) {
      this.mBudgetCycleId = budgetCycleId;
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
}
