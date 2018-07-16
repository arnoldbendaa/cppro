// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:52
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.extsys;

import com.cedar.cp.api.extsys.ExtSysDimElementRef;
import com.cedar.cp.dto.base.EntityRefImpl;
import com.cedar.cp.dto.extsys.ExtSysDimElementCK;
import com.cedar.cp.dto.extsys.ExtSysDimElementPK;
import java.io.Serializable;

public class ExtSysDimElementRefImpl extends EntityRefImpl implements ExtSysDimElementRef, Serializable {

   public ExtSysDimElementRefImpl(ExtSysDimElementCK key, String narrative) {
      super(key, narrative);
   }

   public ExtSysDimElementRefImpl(ExtSysDimElementPK key, String narrative) {
      super(key, narrative);
   }

   public ExtSysDimElementPK getExtSysDimElementPK() {
      return this.mKey instanceof ExtSysDimElementCK?((ExtSysDimElementCK)this.mKey).getExtSysDimElementPK():(ExtSysDimElementPK)this.mKey;
   }
}
