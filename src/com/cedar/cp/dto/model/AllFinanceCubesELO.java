// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:54
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.model;

import com.cedar.cp.api.model.FinanceCubeRef;
import com.cedar.cp.api.model.ModelRef;
import com.cedar.cp.dto.base.AbstractELO;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class AllFinanceCubesELO extends AbstractELO implements Serializable {

   private static final String[] mEntity = new String[]{"FinanceCube", "Model", "FinanceCubeDataType", "BudgetLimit", "RollUpRule", "CubeFormula", "FormulaDeploymentLine", "FormulaDeploymentEntry", "FormulaDeploymentDt", "CubeFormulaPackage", "BudgetInstructionAssignment"};
   private transient FinanceCubeRef mFinanceCubeEntityRef;
   private transient ModelRef mModelEntityRef;
   private transient String mDescription;
   private transient Integer mLockedByTaskId;
   private transient boolean mHasData;
   private transient Boolean mCol4;
   private transient Boolean mCol5;


   public AllFinanceCubesELO() {
      super(new String[]{"FinanceCube", "Model", "Description", "LockedByTaskId", "HasData", "col4", "col5"});
   }

   public void add(FinanceCubeRef eRefFinanceCube, ModelRef eRefModel, String col1, Integer col2, boolean col3, Boolean col4, Boolean col5) {
      ArrayList l = new ArrayList();
      l.add(eRefFinanceCube);
      l.add(eRefModel);
      l.add(col1);
      l.add(col2);
      l.add(new Boolean(col3));
      l.add(col4);
      l.add(col5);
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
      this.mDescription = (String)l.get(var4++);
      this.mLockedByTaskId = (Integer)l.get(var4++);
      this.mHasData = ((Boolean)l.get(var4++)).booleanValue();
      this.mCol4 = (Boolean)l.get(var4++);
      this.mCol5 = (Boolean)l.get(var4++);
   }

   public FinanceCubeRef getFinanceCubeEntityRef() {
      return this.mFinanceCubeEntityRef;
   }

   public ModelRef getModelEntityRef() {
      return this.mModelEntityRef;
   }

   public String getDescription() {
      return this.mDescription;
   }

   public Integer getLockedByTaskId() {
      return this.mLockedByTaskId;
   }

   public boolean getHasData() {
      return this.mHasData;
   }

   public Boolean getCol4() {
      return this.mCol4;
   }

   public Boolean getCol5() {
      return this.mCol5;
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
