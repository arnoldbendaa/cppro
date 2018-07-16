// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:56
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.model;

import com.cedar.cp.api.dimension.DimensionRef;
import com.cedar.cp.api.dimension.HierarchyRef;
import com.cedar.cp.api.model.FinanceCubeRef;
import com.cedar.cp.api.model.ModelDimensionRelRef;
import com.cedar.cp.api.model.ModelRef;
import com.cedar.cp.dto.base.AbstractELO;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class FinanceCubeAllDimensionsAndHierachiesELO extends AbstractELO implements Serializable {

   private static final String[] mEntity = new String[]{"FinanceCube", "Model", "FinanceCubeDataType", "BudgetLimit", "RollUpRule", "CubeFormula", "FormulaDeploymentLine", "FormulaDeploymentEntry", "FormulaDeploymentDt", "CubeFormulaPackage", "ModelDimensionRel", "Dimension", "Hierarchy", "BudgetInstructionAssignment"};
   private transient FinanceCubeRef mFinanceCubeEntityRef;
   private transient ModelRef mModelEntityRef;
   private transient ModelDimensionRelRef mModelDimensionRelEntityRef;
   private transient DimensionRef mDimensionEntityRef;
   private transient HierarchyRef mHierarchyEntityRef;


   public FinanceCubeAllDimensionsAndHierachiesELO() {
      super(new String[]{"FinanceCube", "Model", "ModelDimensionRel", "Dimension", "Hierarchy"});
   }

   public void add(FinanceCubeRef eRefFinanceCube, ModelRef eRefModel, ModelDimensionRelRef eRefModelDimensionRel, DimensionRef eRefDimension, HierarchyRef eRefHierarchy) {
      ArrayList l = new ArrayList();
      l.add(eRefFinanceCube);
      l.add(eRefModel);
      l.add(eRefModelDimensionRel);
      l.add(eRefDimension);
      l.add(eRefHierarchy);
      this.mCollection.add(l);
   }

   public void next() {
      if(this.mIterator == null) {
         this.reset();
      }

      ++this.mCurrRowIndex;
      List l = (List)this.mIterator.next();
      byte index = 0;
      int var4 = index + 1;
      this.mFinanceCubeEntityRef = (FinanceCubeRef)l.get(index);
      this.mModelEntityRef = (ModelRef)l.get(var4++);
      this.mModelDimensionRelEntityRef = (ModelDimensionRelRef)l.get(var4++);
      this.mDimensionEntityRef = (DimensionRef)l.get(var4++);
      this.mHierarchyEntityRef = (HierarchyRef)l.get(var4++);
   }

   public FinanceCubeRef getFinanceCubeEntityRef() {
      return this.mFinanceCubeEntityRef;
   }

   public ModelRef getModelEntityRef() {
      return this.mModelEntityRef;
   }

   public ModelDimensionRelRef getModelDimensionRelEntityRef() {
      return this.mModelDimensionRelEntityRef;
   }

   public DimensionRef getDimensionEntityRef() {
      return this.mDimensionEntityRef;
   }

   public HierarchyRef getHierarchyEntityRef() {
      return this.mHierarchyEntityRef;
   }

   public boolean includesEntity(String name) {
      for(int i = 0; i < mEntity.length; ++i) {
         if(name.equals(mEntity[i])) {
            return true;
         }
      }

      return false;
   }

}
