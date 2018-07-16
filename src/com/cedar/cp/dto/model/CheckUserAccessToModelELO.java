// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:56
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.model;

import com.cedar.cp.api.model.BudgetUserRef;
import com.cedar.cp.api.model.ModelRef;
import com.cedar.cp.dto.base.AbstractELO;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class CheckUserAccessToModelELO extends AbstractELO implements Serializable {

   private static final String[] mEntity = new String[]{"BudgetUser", "Model"};
   private transient BudgetUserRef mBudgetUserEntityRef;
   private transient ModelRef mModelEntityRef;
   private transient boolean mReadOnly;


   public CheckUserAccessToModelELO() {
      super(new String[]{"BudgetUser", "Model", "ReadOnly"});
   }

   public void add(BudgetUserRef eRefBudgetUser, ModelRef eRefModel, boolean col1) {
      ArrayList l = new ArrayList();
      l.add(eRefBudgetUser);
      l.add(eRefModel);
      l.add(new Boolean(col1));
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
      this.mBudgetUserEntityRef = (BudgetUserRef)l.get(index);
      this.mModelEntityRef = (ModelRef)l.get(var4++);
      this.mReadOnly = ((Boolean)l.get(var4++)).booleanValue();
   }

   public BudgetUserRef getBudgetUserEntityRef() {
      return this.mBudgetUserEntityRef;
   }

   public ModelRef getModelEntityRef() {
      return this.mModelEntityRef;
   }

   public boolean getReadOnly() {
      return this.mReadOnly;
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
