// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:26:40
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.impl.model.cc.imp;

import com.cedar.cp.api.model.cc.RuntimeCcDeployment;
import com.cedar.cp.dto.dimension.DimensionRefImpl;
import com.cedar.cp.dto.dimension.StructureElementRefImpl;
import com.cedar.cp.dto.model.BudgetCycleRefImpl;
import com.cedar.cp.dto.model.FinanceCubeRefImpl;
import com.cedar.cp.dto.model.ModelRefImpl;
import com.cedar.cp.ejb.impl.model.cc.imp.CellCalcRowUpdate;
import com.cedar.cp.ejb.impl.model.cc.imp.CellCalcUpdateType;
import com.cedar.cp.util.xmlform.CalendarInfo;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class CellCalcImport implements Serializable {

   private ModelRefImpl mModelRef;
   private FinanceCubeRefImpl mFinanceCubeRef;
   private BudgetCycleRefImpl mBudgetCycleRef;
   private CalendarInfo mCalendarInfo;
   private DimensionRefImpl[] mDimensionRefs;
   private StructureElementRefImpl[] StructureElementRefs;
   private int[] mAddressPositions;
   private RuntimeCcDeployment mRuntimeCcDeployment;
   private int mShortId;
   private String mModel;
   private String mFinanceCube;
   private String mBudgetCycle;
   private CellCalcUpdateType mUpdateType;
   private String mDataType;
   private List<String> mAddress = new ArrayList();
   private List<CellCalcRowUpdate> mRowUpdates = new ArrayList();


   public CellCalcUpdateType getUpdateType() {
      return this.mUpdateType;
   }

   public void setUpdateType(CellCalcUpdateType updateType) {
      this.mUpdateType = updateType;
   }

   public String getModel() {
      return this.mModel;
   }

   public void setModel(String model) {
      this.mModel = model;
   }

   public String getFinanceCube() {
      return this.mFinanceCube;
   }

   public void setFinanceCube(String financeCube) {
      this.mFinanceCube = financeCube;
   }

   public String getBudgetCycle() {
      return this.mBudgetCycle;
   }

   public void setBudgetCycle(String budgetCycle) {
      this.mBudgetCycle = budgetCycle;
   }

   public String getDataType() {
      return this.mDataType;
   }

   public void setDataType(String dataType) {
      this.mDataType = dataType;
   }

   public String getFormattedAddress() {
      StringBuilder sb = new StringBuilder();
      Iterator i$ = this.getAddress().iterator();

      while(i$.hasNext()) {
         String s = (String)i$.next();
         sb.append(s);
         sb.append(", ");
      }

      sb.append(this.getDataType());
      return sb.toString();
   }

   public List<String> getAddress() {
      return this.mAddress;
   }

   public void setAddress(List<String> address) {
      this.mAddress = address;
   }

   public List<CellCalcRowUpdate> getRowUpdates() {
      return this.mRowUpdates;
   }

   public void setRowUpdates(List<CellCalcRowUpdate> rowUpdates) {
      this.mRowUpdates = rowUpdates;
   }

   public ModelRefImpl getModelRef() {
      return this.mModelRef;
   }

   public void setModelRef(ModelRefImpl modelRef) {
      this.mModelRef = modelRef;
   }

   public FinanceCubeRefImpl getFinanceCubeRef() {
      return this.mFinanceCubeRef;
   }

   public void setFinanceCubeRef(FinanceCubeRefImpl financeCubeRef) {
      this.mFinanceCubeRef = financeCubeRef;
   }

   public BudgetCycleRefImpl getBudgetCycleRef() {
      return this.mBudgetCycleRef;
   }

   public void setBudgetCycleRef(BudgetCycleRefImpl budgetCycleRef) {
      this.mBudgetCycleRef = budgetCycleRef;
   }

   public CalendarInfo getCalendarInfo() {
      return this.mCalendarInfo;
   }

   public void setCalendarInfo(CalendarInfo calendarInfo) {
      this.mCalendarInfo = calendarInfo;
   }

   public DimensionRefImpl[] getDimensionRefs() {
      return this.mDimensionRefs;
   }

   public void setDimensionRefs(DimensionRefImpl[] dimensionRefs) {
      this.mDimensionRefs = dimensionRefs;
   }

   public StructureElementRefImpl[] getStructureElementRefs() {
      return this.StructureElementRefs;
   }

   public int[] getAddressIds() {
      return this.getAddressIds(this.getStructureElementRefs().length);
   }

   public int[] getAddressIds(int size) {
      StructureElementRefImpl[] refs = this.getStructureElementRefs();
      int[] result = new int[Math.max(refs.length, size)];

      for(int i = 0; i < result.length; ++i) {
         result[i] = refs[i].getStructureElementPK().getStructureElementId();
      }

      return result;
   }

   public int[] getAddressPositions(int size) {
      StructureElementRefImpl[] refs = this.getStructureElementRefs();
      int[] result = new int[Math.max(refs.length, size)];

      for(int i = 0; i < result.length; ++i) {
         result[i] = this.mAddressPositions[i];
      }

      return result;
   }

   public StructureElementRefImpl[] getStructureElementRefs(int size) {
      StructureElementRefImpl[] refs = this.getStructureElementRefs();
      StructureElementRefImpl[] result = new StructureElementRefImpl[Math.max(refs.length, size)];

      for(int i = 0; i < result.length; ++i) {
         result[i] = refs[i];
      }

      return result;
   }

   public void setStructureElementRefs(StructureElementRefImpl[] structureElementRefs) {
      this.StructureElementRefs = structureElementRefs;
   }

   public int getShortId() {
      return this.mShortId;
   }

   public void setShortId(int shortId) {
      this.mShortId = shortId;
   }

   public RuntimeCcDeployment getRuntimeCcDeployment() {
      return this.mRuntimeCcDeployment;
   }

   public void setRuntimeCcDeployment(RuntimeCcDeployment runtimeCcDeployment) {
      this.mRuntimeCcDeployment = runtimeCcDeployment;
   }

   public int[] getAddressPositions() {
      return this.mAddressPositions;
   }

   public void setAddressPositions(int[] addressPositions) {
      this.mAddressPositions = addressPositions;
   }
}
