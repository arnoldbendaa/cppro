// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:55
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.model;

import com.cedar.cp.api.model.ModelRef;
import com.cedar.cp.api.model.SecurityGroupRef;
import com.cedar.cp.dto.base.AbstractELO;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class AllSecurityGroupsELO extends AbstractELO implements Serializable {

   private static final String[] mEntity = new String[]{"SecurityGroup", "Model", "SecurityGroupUserRel"};
   private transient SecurityGroupRef mSecurityGroupEntityRef;
   private transient ModelRef mModelEntityRef;
   private transient String mDescription;


   public AllSecurityGroupsELO() {
      super(new String[]{"SecurityGroup", "Model", "Description"});
   }

   public void add(SecurityGroupRef eRefSecurityGroup, ModelRef eRefModel, String col1) {
      ArrayList l = new ArrayList();
      l.add(eRefSecurityGroup);
      l.add(eRefModel);
      l.add(col1);
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
      this.mSecurityGroupEntityRef = (SecurityGroupRef)l.get(index);
      this.mModelEntityRef = (ModelRef)l.get(var4++);
      this.mDescription = (String)l.get(var4++);
   }

   public SecurityGroupRef getSecurityGroupEntityRef() {
      return this.mSecurityGroupEntityRef;
   }

   public ModelRef getModelEntityRef() {
      return this.mModelEntityRef;
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
