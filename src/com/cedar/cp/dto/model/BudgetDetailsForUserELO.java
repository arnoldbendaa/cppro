// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:55
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.model;

import com.cedar.cp.api.model.ModelRef;
import com.cedar.cp.dto.base.AbstractELO;
import com.cedar.cp.dto.budgetinstruction.AllBudgetInstructionsForModelELO;
import com.cedar.cp.dto.model.BudgetDetailsForUserLevel2ELO;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class BudgetDetailsForUserELO extends AbstractELO implements Serializable {

   private transient Integer mModelId;
   private transient ModelRef mModelEntityRef;
   private transient BudgetDetailsForUserLevel2ELO mChildren;
   private transient AllBudgetInstructionsForModelELO mBudgetInstructions;


   public BudgetDetailsForUserELO() {
      super(new String[]{"ModelId", "Model", "BudgetCycles"});
   }

   public void add(int modelId, ModelRef eRefModel, BudgetDetailsForUserLevel2ELO budgetCycles) {
      ArrayList l = new ArrayList();
      l.add(Integer.valueOf(modelId));
      l.add(eRefModel);
      l.add(budgetCycles);
      this.mCollection.add(l);
   }

   public void next() {
      if(this.mIterator == null) {
         this.mCurrRowIndex = -1;
         this.reset();
      }

      ++this.mCurrRowIndex;
      List l = (List)this.mIterator.next();
      byte index = 0;
      int var4 = index + 1;
      this.mModelId = (Integer)l.get(index);
      this.mModelEntityRef = (ModelRef)l.get(var4++);
      this.mChildren = (BudgetDetailsForUserLevel2ELO)l.get(var4++);
   }

   public Integer getModelId() {
      return this.mModelId;
   }

   public ModelRef getModelEntityRef() {
      return this.mModelEntityRef;
   }

   public BudgetDetailsForUserLevel2ELO getBudgetCycles() {
      return this.mChildren;
   }
}
