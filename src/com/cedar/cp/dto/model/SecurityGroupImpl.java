// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:58
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.model;

import com.cedar.cp.api.model.ModelRef;
import com.cedar.cp.api.model.SecurityAccessDefRef;
import com.cedar.cp.api.model.SecurityGroup;
import com.cedar.cp.dto.model.SecurityGroupCK;
import com.cedar.cp.dto.model.SecurityGroupPK;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class SecurityGroupImpl implements SecurityGroup, Serializable, Cloneable {

   private List mUsers;
   private SecurityAccessDefRef mSecurityAccessDef;
   private Object mPrimaryKey;
   private String mVisId;
   private String mDescription;
   private int mVersionNum;
   private ModelRef mModelRef;


   public SecurityGroupImpl(Object paramKey) {
      this.mPrimaryKey = paramKey;
      this.mVisId = "";
      this.mDescription = "";
      this.mUsers = new ArrayList();
   }

   public Object getPrimaryKey() {
      return this.mPrimaryKey;
   }

   public void setPrimaryKey(Object paramKey) {
      this.mPrimaryKey = (SecurityGroupPK)paramKey;
   }

   public void setPrimaryKey(SecurityGroupCK paramKey) {
      this.mPrimaryKey = paramKey;
   }

   public String getVisId() {
      return this.mVisId;
   }

   public String getDescription() {
      return this.mDescription;
   }

   public ModelRef getModelRef() {
      return this.mModelRef;
   }

   public void setModelRef(ModelRef ref) {
      this.mModelRef = ref;
   }

   public void setVersionNum(int p) {
      this.mVersionNum = p;
   }

   public int getVersionNum() {
      return this.mVersionNum;
   }

   public void setVisId(String paramVisId) {
      this.mVisId = paramVisId;
   }

   public void setDescription(String paramDescription) {
      this.mDescription = paramDescription;
   }

   public List getUsers() {
      return this.mUsers;
   }

   public void setUsers(List users) {
      this.mUsers = users;
   }

   public SecurityAccessDefRef getSecurityAccessDef() {
      return this.mSecurityAccessDef;
   }

   public void setSecurityAccessDef(SecurityAccessDefRef securityAccessDef) {
      this.mSecurityAccessDef = securityAccessDef;
   }
}
