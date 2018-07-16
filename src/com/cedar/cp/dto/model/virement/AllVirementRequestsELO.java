// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:06:03
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.model.virement;

import com.cedar.cp.api.model.ModelRef;
import com.cedar.cp.api.model.virement.VirementRequestRef;
import com.cedar.cp.dto.base.AbstractELO;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class AllVirementRequestsELO extends AbstractELO implements Serializable {

   private static final String[] mEntity = new String[]{"VirementRequest", "Model", "VirementRequestGroup", "VirementRequestLine", "VirementLineSpread", "VirementAuthPoint", "VirementAuthorisers", "VirementAuthPointLink", "User"};
   private transient VirementRequestRef mVirementRequestEntityRef;
   private transient ModelRef mModelEntityRef;
   private transient int mRequestId;
   private transient int mUserId;
   private transient int mModelId;
   private transient Timestamp mCreatedTime;
   private transient int mBudgetActivityId;


   public AllVirementRequestsELO() {
      super(new String[]{"VirementRequest", "Model", "RequestId", "UserId", "ModelId", "CreatedTime", "BudgetActivityId"});
   }

   public void add(VirementRequestRef eRefVirementRequest, ModelRef eRefModel, int col1, int col2, int col3, Timestamp col4, int col5) {
      ArrayList l = new ArrayList();
      l.add(eRefVirementRequest);
      l.add(eRefModel);
      l.add(new Integer(col1));
      l.add(new Integer(col2));
      l.add(new Integer(col3));
      l.add(col4);
      l.add(new Integer(col5));
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
      this.mVirementRequestEntityRef = (VirementRequestRef)l.get(index);
      this.mModelEntityRef = (ModelRef)l.get(var4++);
      this.mRequestId = ((Integer)l.get(var4++)).intValue();
      this.mUserId = ((Integer)l.get(var4++)).intValue();
      this.mModelId = ((Integer)l.get(var4++)).intValue();
      this.mCreatedTime = (Timestamp)l.get(var4++);
      this.mBudgetActivityId = ((Integer)l.get(var4++)).intValue();
   }

   public VirementRequestRef getVirementRequestEntityRef() {
      return this.mVirementRequestEntityRef;
   }

   public ModelRef getModelEntityRef() {
      return this.mModelEntityRef;
   }

   public int getRequestId() {
      return this.mRequestId;
   }

   public int getUserId() {
      return this.mUserId;
   }

   public int getModelId() {
      return this.mModelId;
   }

   public Timestamp getCreatedTime() {
      return this.mCreatedTime;
   }

   public int getBudgetActivityId() {
      return this.mBudgetActivityId;
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
