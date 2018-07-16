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

public class FinanceCubeDimensionsAndHierachiesELO extends AbstractELO implements Serializable {

   private static final String[] mEntity = new String[]{"FinanceCube", "Model", "FinanceCubeDataType", "BudgetLimit", "RollUpRule", "CubeFormula", "FormulaDeploymentLine", "FormulaDeploymentEntry", "FormulaDeploymentDt", "CubeFormulaPackage", "ModelDimensionRel", "Dimension", "Hierarchy", "BudgetInstructionAssignment"};
   private transient FinanceCubeRef mFinanceCubeEntityRef;
   private transient ModelRef mModelEntityRef;
   private transient ModelDimensionRelRef mModelDimensionRelEntityRef;
   private transient DimensionRef mDimensionEntityRef;
   private transient HierarchyRef mHierarchyEntityRef;
   private transient int mCol1;
   private transient String mCol2;


   public FinanceCubeDimensionsAndHierachiesELO() {
      super(new String[]{"FinanceCube", "Model", "ModelDimensionRel", "Dimension", "Hierarchy", "col1", "col2"});
   }

   public void add(FinanceCubeRef eRefFinanceCube, ModelRef eRefModel, ModelDimensionRelRef eRefModelDimensionRel, DimensionRef eRefDimension, HierarchyRef eRefHierarchy, int col1, String col2) {
      ArrayList l = new ArrayList();
      l.add(eRefFinanceCube);
      l.add(eRefModel);
      l.add(eRefModelDimensionRel);
      l.add(eRefDimension);
      l.add(eRefHierarchy);
      l.add(new Integer(col1));
      l.add(col2);
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
      this.mCol1 = ((Integer)l.get(var4++)).intValue();
      this.mCol2 = (String)l.get(var4++);
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

   public int getCol1() {
      return this.mCol1;
   }

   public String getCol2() {
      return this.mCol2;
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
