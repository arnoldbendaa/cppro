// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:53
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.impexp;

import com.cedar.cp.api.impexp.ImpExpRowRef;
import com.cedar.cp.dto.base.EntityRefImpl;
import com.cedar.cp.dto.impexp.ImpExpRowCK;
import com.cedar.cp.dto.impexp.ImpExpRowPK;
import java.io.Serializable;

public class ImpExpRowRefImpl extends EntityRefImpl implements ImpExpRowRef, Serializable {

   public ImpExpRowRefImpl(ImpExpRowCK key, String narrative) {
      super(key, narrative);
   }

   public ImpExpRowRefImpl(ImpExpRowPK key, String narrative) {
      super(key, narrative);
   }

   public ImpExpRowPK getImpExpRowPK() {
      return this.mKey instanceof ImpExpRowCK?((ImpExpRowCK)this.mKey).getImpExpRowPK():(ImpExpRowPK)this.mKey;
   }
}
