// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:56
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.model;

import com.cedar.cp.api.model.BudgetCycleRef;
import com.cedar.cp.api.model.BudgetStateRef;
import com.cedar.cp.api.model.ModelRef;
import com.cedar.cp.dto.base.AbstractELO;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class CycleStateDetailsELO extends AbstractELO implements Serializable {

   private static final String[] mEntity = new String[]{"BudgetState", "BudgetCycle", "Model", "BudgetStateHistory"};
   private transient BudgetStateRef mBudgetStateEntityRef;
   private transient BudgetCycleRef mBudgetCycleEntityRef;
   private transient ModelRef mModelEntityRef;
   private transient int mStructureElementId;
   private transient int mState;
   private transient boolean mSubmitable;
   private transient boolean mRejectable;


   public CycleStateDetailsELO() {
      super(new String[]{"BudgetState", "BudgetCycle", "Model", "StructureElementId", "State", "Submitable", "Rejectable"});
   }

   public void add(BudgetStateRef eRefBudgetState, BudgetCycleRef eRefBudgetCycle, ModelRef eRefModel, int col1, int col2, boolean col3, boolean col4) {
      ArrayList l = new ArrayList();
      l.add(eRefBudgetState);
      l.add(eRefBudgetCycle);
      l.add(eRefModel);
      l.add(new Integer(col1));
      l.add(new Integer(col2));
      l.add(new Boolean(col3));
      l.add(new Boolean(col4));
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
      this.mBudgetStateEntityRef = (BudgetStateRef)l.get(index);
      this.mBudgetCycleEntityRef = (BudgetCycleRef)l.get(var4++);
      this.mModelEntityRef = (ModelRef)l.get(var4++);
      this.mStructureElementId = ((Integer)l.get(var4++)).intValue();
      this.mState = ((Integer)l.get(var4++)).intValue();
      this.mSubmitable = ((Boolean)l.get(var4++)).booleanValue();
      this.mRejectable = ((Boolean)l.get(var4++)).booleanValue();
   }

   public BudgetStateRef getBudgetStateEntityRef() {
      return this.mBudgetStateEntityRef;
   }

   public BudgetCycleRef getBudgetCycleEntityRef() {
      return this.mBudgetCycleEntityRef;
   }

   public ModelRef getModelEntityRef() {
      return this.mModelEntityRef;
   }

   public int getStructureElementId() {
      return this.mStructureElementId;
   }

   public int getState() {
      return this.mState;
   }

   public boolean getSubmitable() {
      return this.mSubmitable;
   }

   public boolean getRejectable() {
      return this.mRejectable;
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
