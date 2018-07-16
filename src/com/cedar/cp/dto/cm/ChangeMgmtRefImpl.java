// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:47
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.cm;

import com.cedar.cp.api.cm.ChangeMgmtRef;
import com.cedar.cp.dto.base.EntityRefImpl;
import com.cedar.cp.dto.cm.ChangeMgmtPK;
import java.io.Serializable;

public class ChangeMgmtRefImpl extends EntityRefImpl implements ChangeMgmtRef, Serializable {

   public ChangeMgmtRefImpl(ChangeMgmtPK key, String narrative) {
      super(key, narrative);
   }

   public ChangeMgmtPK getChangeMgmtPK() {
      return (ChangeMgmtPK)this.mKey;
   }
}
