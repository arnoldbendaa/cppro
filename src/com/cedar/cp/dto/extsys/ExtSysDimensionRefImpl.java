// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:52
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.extsys;

import com.cedar.cp.api.extsys.ExtSysDimensionRef;
import com.cedar.cp.dto.base.EntityRefImpl;
import com.cedar.cp.dto.extsys.ExtSysDimensionCK;
import com.cedar.cp.dto.extsys.ExtSysDimensionPK;
import java.io.Serializable;

public class ExtSysDimensionRefImpl extends EntityRefImpl implements ExtSysDimensionRef, Serializable {

   public ExtSysDimensionRefImpl(ExtSysDimensionCK key, String narrative) {
      super(key, narrative);
   }

   public ExtSysDimensionRefImpl(ExtSysDimensionPK key, String narrative) {
      super(key, narrative);
   }

   public ExtSysDimensionPK getExtSysDimensionPK() {
      return this.mKey instanceof ExtSysDimensionCK?((ExtSysDimensionCK)this.mKey).getExtSysDimensionPK():(ExtSysDimensionPK)this.mKey;
   }
}
