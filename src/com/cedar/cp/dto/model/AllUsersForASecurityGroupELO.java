// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:55
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.model;

import com.cedar.cp.api.model.ModelRef;
import com.cedar.cp.api.model.SecurityGroupRef;
import com.cedar.cp.api.model.SecurityGroupUserRelRef;
import com.cedar.cp.dto.base.AbstractELO;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class AllUsersForASecurityGroupELO extends AbstractELO implements Serializable {

   private static final String[] mEntity = new String[]{"SecurityGroupUserRel", "SecurityGroup", "Model"};
   private transient SecurityGroupUserRelRef mSecurityGroupUserRelEntityRef;
   private transient SecurityGroupRef mSecurityGroupEntityRef;
   private transient ModelRef mModelEntityRef;
   private transient int mSecurityGroupId;
   private transient int mUserId;


   public AllUsersForASecurityGroupELO() {
      super(new String[]{"SecurityGroupUserRel", "SecurityGroup", "Model", "SecurityGroupId", "UserId"});
   }

   public void add(SecurityGroupUserRelRef eRefSecurityGroupUserRel, SecurityGroupRef eRefSecurityGroup, ModelRef eRefModel, int col1, int col2) {
      ArrayList l = new ArrayList();
      l.add(eRefSecurityGroupUserRel);
      l.add(eRefSecurityGroup);
      l.add(eRefModel);
      l.add(new Integer(col1));
      l.add(new Integer(col2));
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
      this.mSecurityGroupUserRelEntityRef = (SecurityGroupUserRelRef)l.get(index);
      this.mSecurityGroupEntityRef = (SecurityGroupRef)l.get(var4++);
      this.mModelEntityRef = (ModelRef)l.get(var4++);
      this.mSecurityGroupId = ((Integer)l.get(var4++)).intValue();
      this.mUserId = ((Integer)l.get(var4++)).intValue();
   }

   public SecurityGroupUserRelRef getSecurityGroupUserRelEntityRef() {
      return this.mSecurityGroupUserRelEntityRef;
   }

   public SecurityGroupRef getSecurityGroupEntityRef() {
      return this.mSecurityGroupEntityRef;
   }

   public ModelRef getModelEntityRef() {
      return this.mModelEntityRef;
   }

   public int getSecurityGroupId() {
      return this.mSecurityGroupId;
   }

   public int getUserId() {
      return this.mUserId;
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
