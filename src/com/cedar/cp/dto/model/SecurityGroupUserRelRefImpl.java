// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:58
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.model;

import com.cedar.cp.api.model.SecurityGroupUserRelRef;
import com.cedar.cp.dto.base.EntityRefImpl;
import com.cedar.cp.dto.model.SecurityGroupUserRelCK;
import com.cedar.cp.dto.model.SecurityGroupUserRelPK;
import java.io.Serializable;

public class SecurityGroupUserRelRefImpl extends EntityRefImpl implements SecurityGroupUserRelRef, Serializable {

   public SecurityGroupUserRelRefImpl(SecurityGroupUserRelCK key, String narrative) {
      super(key, narrative);
   }

   public SecurityGroupUserRelRefImpl(SecurityGroupUserRelPK key, String narrative) {
      super(key, narrative);
   }

   public SecurityGroupUserRelPK getSecurityGroupUserRelPK() {
      return this.mKey instanceof SecurityGroupUserRelCK?((SecurityGroupUserRelCK)this.mKey).getSecurityGroupUserRelPK():(SecurityGroupUserRelPK)this.mKey;
   }
}
