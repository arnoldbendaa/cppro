// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:06:12
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.xmlform.rebuild;

import com.cedar.cp.api.model.ModelRef;
import com.cedar.cp.api.xmlform.rebuild.FormRebuildRef;
import com.cedar.cp.dto.base.AbstractELO;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class AllBudgetCyclesInRebuildsELO extends AbstractELO implements Serializable {

   private static final String[] mEntity = new String[]{"FormRebuild", "Model"};
   private transient FormRebuildRef mFormRebuildEntityRef;
   private transient ModelRef mModelEntityRef;
   private transient int mBudgetCycleId;


   public AllBudgetCyclesInRebuildsELO() {
      super(new String[]{"FormRebuild", "Model", "BudgetCycleId"});
   }

   public void add(FormRebuildRef eRefFormRebuild, ModelRef eRefModel, int col1) {
      ArrayList l = new ArrayList();
      l.add(eRefFormRebuild);
      l.add(eRefModel);
      l.add(new Integer(col1));
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
      this.mFormRebuildEntityRef = (FormRebuildRef)l.get(index);
      this.mModelEntityRef = (ModelRef)l.get(var4++);
      this.mBudgetCycleId = ((Integer)l.get(var4++)).intValue();
   }

   public FormRebuildRef getFormRebuildEntityRef() {
      return this.mFormRebuildEntityRef;
   }

   public ModelRef getModelEntityRef() {
      return this.mModelEntityRef;
   }

   public int getBudgetCycleId() {
      return this.mBudgetCycleId;
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
