// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:55
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.model;

import com.cedar.cp.api.model.BudgetUserRef;
import com.cedar.cp.api.model.ModelRef;
import com.cedar.cp.api.user.UserRef;
import com.cedar.cp.dto.base.AbstractELO;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class BudgetUsersForNodeELO extends AbstractELO implements Serializable {

   private static final String[] mEntity = new String[]{"BudgetUser", "Model", "User"};
   private transient BudgetUserRef mBudgetUserEntityRef;
   private transient ModelRef mModelEntityRef;
   private transient UserRef mUserEntityRef;
   private transient int mUserId;
   private transient String mName;
   private transient String mFullName;
   private transient String mEMailAddress;


   public BudgetUsersForNodeELO() {
      super(new String[]{"BudgetUser", "Model", "User", "UserId", "Name", "FullName", "EMailAddress"});
   }

   public void add(BudgetUserRef eRefBudgetUser, ModelRef eRefModel, UserRef eRefUser, int col1, String col2, String col3, String col4) {
      ArrayList l = new ArrayList();
      l.add(eRefBudgetUser);
      l.add(eRefModel);
      l.add(eRefUser);
      l.add(new Integer(col1));
      l.add(col2);
      l.add(col3);
      l.add(col4);
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
      this.mUserEntityRef = (UserRef)l.get(var4++);
      this.mUserId = ((Integer)l.get(var4++)).intValue();
      this.mName = (String)l.get(var4++);
      this.mFullName = (String)l.get(var4++);
      this.mEMailAddress = (String)l.get(var4++);
   }

   public BudgetUserRef getBudgetUserEntityRef() {
      return this.mBudgetUserEntityRef;
   }

   public ModelRef getModelEntityRef() {
      return this.mModelEntityRef;
   }

   public UserRef getUserEntityRef() {
      return this.mUserEntityRef;
   }

   public int getUserId() {
      return this.mUserId;
   }

   public String getName() {
      return this.mName;
   }

   public String getFullName() {
      return this.mFullName;
   }

   public String getEMailAddress() {
      return this.mEMailAddress;
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
