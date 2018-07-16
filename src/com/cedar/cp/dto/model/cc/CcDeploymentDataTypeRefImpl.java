// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:59
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.model.cc;

import com.cedar.cp.api.model.cc.CcDeploymentDataTypeRef;
import com.cedar.cp.dto.base.EntityRefImpl;
import com.cedar.cp.dto.model.cc.CcDeploymentDataTypeCK;
import com.cedar.cp.dto.model.cc.CcDeploymentDataTypePK;
import java.io.Serializable;

public class CcDeploymentDataTypeRefImpl extends EntityRefImpl implements CcDeploymentDataTypeRef, Serializable {

   public CcDeploymentDataTypeRefImpl(CcDeploymentDataTypeCK key, String narrative) {
      super(key, narrative);
   }

   public CcDeploymentDataTypeRefImpl(CcDeploymentDataTypePK key, String narrative) {
      super(key, narrative);
   }

   public CcDeploymentDataTypePK getCcDeploymentDataTypePK() {
      return this.mKey instanceof CcDeploymentDataTypeCK?((CcDeploymentDataTypeCK)this.mKey).getCcDeploymentDataTypePK():(CcDeploymentDataTypePK)this.mKey;
   }
}
