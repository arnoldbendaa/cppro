// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:58
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.model.act;

import com.cedar.cp.api.model.ModelRef;
import com.cedar.cp.api.model.act.BudgetActivityLinkRef;
import com.cedar.cp.api.model.act.BudgetActivityRef;
import com.cedar.cp.api.user.UserRef;
import com.cedar.cp.dto.base.AbstractELO;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class ActivitiesForCycleandElementELO extends AbstractELO implements Serializable {

   private static final String[] mEntity = new String[]{"BudgetActivity", "Model", "BudgetActivityLink", "User", "BudgetActivityLink"};
   private transient BudgetActivityRef mBudgetActivityEntityRef;
   private transient ModelRef mModelEntityRef;
   private transient UserRef mUserEntityRef;
   private transient BudgetActivityLinkRef mBudgetActivityLinkEntityRef;
   private transient int mBudgetActivityId;
   private transient Timestamp mCreated;
   private transient int mActivityType;
   private transient String mDescription;
   private transient String mUserId;
   private transient int mOwnerId;
   private transient String mFullName;


   public ActivitiesForCycleandElementELO() {
      super(new String[]{"BudgetActivity", "Model", "User", "BudgetActivityLink", "BudgetActivityId", "Created", "ActivityType", "Description", "UserId", "OwnerId", "FullName"});
   }

   public void add(BudgetActivityRef eRefBudgetActivity, ModelRef eRefModel, UserRef eRefUser, BudgetActivityLinkRef eRefBudgetActivityLink, int col1, Timestamp col2, int col3, String col4, String col5, int col6, String col7) {
      ArrayList l = new ArrayList();
      l.add(eRefBudgetActivity);
      l.add(eRefModel);
      l.add(eRefUser);
      l.add(eRefBudgetActivityLink);
      l.add(new Integer(col1));
      l.add(col2);
      l.add(new Integer(col3));
      l.add(col4);
      l.add(col5);
      l.add(new Integer(col6));
      l.add(col7);
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
      this.mBudgetActivityEntityRef = (BudgetActivityRef)l.get(index);
      this.mModelEntityRef = (ModelRef)l.get(var4++);
      this.mUserEntityRef = (UserRef)l.get(var4++);
      this.mBudgetActivityLinkEntityRef = (BudgetActivityLinkRef)l.get(var4++);
      this.mBudgetActivityId = ((Integer)l.get(var4++)).intValue();
      this.mCreated = (Timestamp)l.get(var4++);
      this.mActivityType = ((Integer)l.get(var4++)).intValue();
      this.mDescription = (String)l.get(var4++);
      this.mUserId = (String)l.get(var4++);
      this.mOwnerId = ((Integer)l.get(var4++)).intValue();
      this.mFullName = (String)l.get(var4++);
   }

   public BudgetActivityRef getBudgetActivityEntityRef() {
      return this.mBudgetActivityEntityRef;
   }

   public ModelRef getModelEntityRef() {
      return this.mModelEntityRef;
   }

   public UserRef getUserEntityRef() {
      return this.mUserEntityRef;
   }

   public BudgetActivityLinkRef getBudgetActivityLinkEntityRef() {
      return this.mBudgetActivityLinkEntityRef;
   }

   public int getBudgetActivityId() {
      return this.mBudgetActivityId;
   }

   public Timestamp getCreated() {
      return this.mCreated;
   }

   public int getActivityType() {
      return this.mActivityType;
   }

   public String getDescription() {
      return this.mDescription;
   }

   public String getUserId() {
      return this.mUserId;
   }

   public int getOwnerId() {
      return this.mOwnerId;
   }

   public String getFullName() {
      return this.mFullName;
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