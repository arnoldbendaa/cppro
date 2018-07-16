// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:54
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.model;

import com.cedar.cp.api.model.ModelRef;
import com.cedar.cp.api.model.SecurityAccRngRelRef;
import com.cedar.cp.api.model.SecurityAccessDefRef;
import com.cedar.cp.dto.base.AbstractELO;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class AllAccessDefsUsingRangeELO extends AbstractELO implements Serializable {

   private static final String[] mEntity = new String[]{"SecurityAccRngRel", "SecurityAccessDef", "Model"};
   private transient SecurityAccRngRelRef mSecurityAccRngRelEntityRef;
   private transient SecurityAccessDefRef mSecurityAccessDefEntityRef;
   private transient ModelRef mModelEntityRef;
   private transient int mSecurityAccessDefId;


   public AllAccessDefsUsingRangeELO() {
      super(new String[]{"SecurityAccRngRel", "SecurityAccessDef", "Model", "SecurityAccessDefId"});
   }

   public void add(SecurityAccRngRelRef eRefSecurityAccRngRel, SecurityAccessDefRef eRefSecurityAccessDef, ModelRef eRefModel, int col1) {
      ArrayList l = new ArrayList();
      l.add(eRefSecurityAccRngRel);
      l.add(eRefSecurityAccessDef);
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
      this.mSecurityAccRngRelEntityRef = (SecurityAccRngRelRef)l.get(index);
      this.mSecurityAccessDefEntityRef = (SecurityAccessDefRef)l.get(var4++);
      this.mModelEntityRef = (ModelRef)l.get(var4++);
      this.mSecurityAccessDefId = ((Integer)l.get(var4++)).intValue();
   }

   public SecurityAccRngRelRef getSecurityAccRngRelEntityRef() {
      return this.mSecurityAccRngRelEntityRef;
   }

   public SecurityAccessDefRef getSecurityAccessDefEntityRef() {
      return this.mSecurityAccessDefEntityRef;
   }

   public ModelRef getModelEntityRef() {
      return this.mModelEntityRef;
   }

   public int getSecurityAccessDefId() {
      return this.mSecurityAccessDefId;
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
