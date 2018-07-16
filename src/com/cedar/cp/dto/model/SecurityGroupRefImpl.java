// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:58
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.model;

import com.cedar.cp.api.model.SecurityGroupRef;
import com.cedar.cp.dto.base.EntityRefImpl;
import com.cedar.cp.dto.model.SecurityGroupCK;
import com.cedar.cp.dto.model.SecurityGroupPK;
import java.io.Serializable;

public class SecurityGroupRefImpl extends EntityRefImpl implements SecurityGroupRef, Serializable {

   public SecurityGroupRefImpl(SecurityGroupCK key, String narrative) {
      super(key, narrative);
   }

   public SecurityGroupRefImpl(SecurityGroupPK key, String narrative) {
      super(key, narrative);
   }

   public SecurityGroupPK getSecurityGroupPK() {
      return this.mKey instanceof SecurityGroupCK?((SecurityGroupCK)this.mKey).getSecurityGroupPK():(SecurityGroupPK)this.mKey;
   }
}
