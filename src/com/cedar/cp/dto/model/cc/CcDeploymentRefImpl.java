// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:06:00
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.model.cc;

import com.cedar.cp.api.model.cc.CcDeploymentRef;
import com.cedar.cp.dto.base.EntityRefImpl;
import com.cedar.cp.dto.model.cc.CcDeploymentCK;
import com.cedar.cp.dto.model.cc.CcDeploymentPK;
import java.io.Serializable;

public class CcDeploymentRefImpl extends EntityRefImpl implements CcDeploymentRef, Serializable {

   public CcDeploymentRefImpl(CcDeploymentCK key, String narrative) {
      super(key, narrative);
   }

   public CcDeploymentRefImpl(CcDeploymentPK key, String narrative) {
      super(key, narrative);
   }

   public CcDeploymentPK getCcDeploymentPK() {
      return this.mKey instanceof CcDeploymentCK?((CcDeploymentCK)this.mKey).getCcDeploymentPK():(CcDeploymentPK)this.mKey;
   }
}
