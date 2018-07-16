// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:53
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.impexp;

import com.cedar.cp.api.impexp.ImpExpHdrRef;
import com.cedar.cp.dto.base.EntityRefImpl;
import com.cedar.cp.dto.impexp.ImpExpHdrPK;
import java.io.Serializable;

public class ImpExpHdrRefImpl extends EntityRefImpl implements ImpExpHdrRef, Serializable {

   public ImpExpHdrRefImpl(ImpExpHdrPK key, String narrative) {
      super(key, narrative);
   }

   public ImpExpHdrPK getImpExpHdrPK() {
      return (ImpExpHdrPK)this.mKey;
   }
}
