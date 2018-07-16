// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:06:02
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.model.recharge;

import com.cedar.cp.api.model.recharge.RechargeGroupRef;
import com.cedar.cp.dto.base.AbstractELO;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class AllRechargeGroupsELO extends AbstractELO implements Serializable {

   private static final String[] mEntity = new String[]{"RechargeGroup", "RechargeGroupRel"};
   private transient RechargeGroupRef mRechargeGroupEntityRef;
   private transient int mRechargeGroupId;
   private transient String mDescription;


   public AllRechargeGroupsELO() {
      super(new String[]{"RechargeGroup", "RechargeGroupId", "Description"});
   }

   public void add(RechargeGroupRef eRefRechargeGroup, int col1, String col2) {
      ArrayList l = new ArrayList();
      l.add(eRefRechargeGroup);
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
      this.mRechargeGroupEntityRef = (RechargeGroupRef)l.get(index);
      this.mRechargeGroupId = ((Integer)l.get(var4++)).intValue();
      this.mDescription = (String)l.get(var4++);
   }

   public RechargeGroupRef getRechargeGroupEntityRef() {
      return this.mRechargeGroupEntityRef;
   }

   public int getRechargeGroupId() {
      return this.mRechargeGroupId;
   }

   public String getDescription() {
      return this.mDescription;
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
