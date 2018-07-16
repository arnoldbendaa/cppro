// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:59
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.model.cc;

import com.cedar.cp.api.model.cc.CcDeploymentEntryRef;
import com.cedar.cp.dto.base.EntityRefImpl;
import com.cedar.cp.dto.model.cc.CcDeploymentEntryCK;
import com.cedar.cp.dto.model.cc.CcDeploymentEntryPK;
import java.io.Serializable;

public class CcDeploymentEntryRefImpl extends EntityRefImpl implements CcDeploymentEntryRef, Serializable {

   public CcDeploymentEntryRefImpl(CcDeploymentEntryCK key, String narrative) {
      super(key, narrative);
   }

   public CcDeploymentEntryRefImpl(CcDeploymentEntryPK key, String narrative) {
      super(key, narrative);
   }

   public CcDeploymentEntryPK getCcDeploymentEntryPK() {
      return this.mKey instanceof CcDeploymentEntryCK?((CcDeploymentEntryCK)this.mKey).getCcDeploymentEntryPK():(CcDeploymentEntryPK)this.mKey;
   }
}
