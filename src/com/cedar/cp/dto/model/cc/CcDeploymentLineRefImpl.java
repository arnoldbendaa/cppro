// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:06:00
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.model.cc;

import com.cedar.cp.api.model.cc.CcDeploymentLineRef;
import com.cedar.cp.dto.base.EntityRefImpl;
import com.cedar.cp.dto.model.cc.CcDeploymentLineCK;
import com.cedar.cp.dto.model.cc.CcDeploymentLinePK;
import java.io.Serializable;

public class CcDeploymentLineRefImpl extends EntityRefImpl implements CcDeploymentLineRef, Serializable {

   public CcDeploymentLineRefImpl(CcDeploymentLineCK key, String narrative) {
      super(key, narrative);
   }

   public CcDeploymentLineRefImpl(CcDeploymentLinePK key, String narrative) {
      super(key, narrative);
   }

   public CcDeploymentLinePK getCcDeploymentLinePK() {
      return this.mKey instanceof CcDeploymentLineCK?((CcDeploymentLineCK)this.mKey).getCcDeploymentLinePK():(CcDeploymentLinePK)this.mKey;
   }
}
