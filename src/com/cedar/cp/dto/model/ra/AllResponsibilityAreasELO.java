// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:06:02
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.model.ra;

import com.cedar.cp.api.model.BudgetUserRef;
import com.cedar.cp.api.model.ModelRef;
import com.cedar.cp.api.model.ra.ResponsibilityAreaRef;
import com.cedar.cp.api.user.UserRef;
import com.cedar.cp.dto.base.AbstractELO;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class AllResponsibilityAreasELO extends AbstractELO implements Serializable {

   private static final String[] mEntity = new String[]{"ResponsibilityArea", "Model", "User", "BudgetUser", "StructureElement"};
   private transient ResponsibilityAreaRef mResponsibilityAreaEntityRef;
   private transient ModelRef mModelEntityRef;
   private transient UserRef mUserEntityRef;
   private transient BudgetUserRef mBudgetUserEntityRef;


   public AllResponsibilityAreasELO() {
      super(new String[]{"ResponsibilityArea", "Model", "User", "BudgetUser"});
   }

   public void add(ResponsibilityAreaRef eRefResponsibilityArea, ModelRef eRefModel, UserRef eRefUser, BudgetUserRef eRefBudgetUser) {
      ArrayList l = new ArrayList();
      l.add(eRefResponsibilityArea);
      l.add(eRefModel);
      l.add(eRefUser);
      l.add(eRefBudgetUser);
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
      this.mResponsibilityAreaEntityRef = (ResponsibilityAreaRef)l.get(index);
      this.mModelEntityRef = (ModelRef)l.get(var4++);
      this.mUserEntityRef = (UserRef)l.get(var4++);
      this.mBudgetUserEntityRef = (BudgetUserRef)l.get(var4++);
   }

   public ResponsibilityAreaRef getResponsibilityAreaEntityRef() {
      return this.mResponsibilityAreaEntityRef;
   }

   public ModelRef getModelEntityRef() {
      return this.mModelEntityRef;
   }

   public UserRef getUserEntityRef() {
      return this.mUserEntityRef;
   }

   public BudgetUserRef getBudgetUserEntityRef() {
      return this.mBudgetUserEntityRef;
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
