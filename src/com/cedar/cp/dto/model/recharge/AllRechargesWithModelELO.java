// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:06:02
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.model.recharge;

import com.cedar.cp.api.model.ModelRef;
import com.cedar.cp.api.model.recharge.RechargeRef;
import com.cedar.cp.dto.base.AbstractELO;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class AllRechargesWithModelELO extends AbstractELO implements Serializable {

   private static final String[] mEntity = new String[]{"Recharge", "Model", "RechargeCells"};
   private transient RechargeRef mRechargeEntityRef;
   private transient ModelRef mModelEntityRef;
   private transient int mModelId;
   private transient int mRechargeId;
   private transient String mDescription;


   public AllRechargesWithModelELO() {
      super(new String[]{"Recharge", "Model", "ModelId", "RechargeId", "Description"});
   }

   public void add(RechargeRef eRefRecharge, ModelRef eRefModel, int col1, int col2, String col3) {
      ArrayList l = new ArrayList();
      l.add(eRefRecharge);
      l.add(eRefModel);
      l.add(new Integer(col1));
      l.add(new Integer(col2));
      l.add(col3);
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
      this.mRechargeEntityRef = (RechargeRef)l.get(index);
      this.mModelEntityRef = (ModelRef)l.get(var4++);
      this.mModelId = ((Integer)l.get(var4++)).intValue();
      this.mRechargeId = ((Integer)l.get(var4++)).intValue();
      this.mDescription = (String)l.get(var4++);
   }

   public RechargeRef getRechargeEntityRef() {
      return this.mRechargeEntityRef;
   }

   public ModelRef getModelEntityRef() {
      return this.mModelEntityRef;
   }

   public int getModelId() {
      return this.mModelId;
   }

   public int getRechargeId() {
      return this.mRechargeId;
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
