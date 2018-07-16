// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:52
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.extsys;

import com.cedar.cp.api.extsys.ExtSysCalElementRef;
import com.cedar.cp.dto.base.EntityRefImpl;
import com.cedar.cp.dto.extsys.ExtSysCalElementCK;
import com.cedar.cp.dto.extsys.ExtSysCalElementPK;
import java.io.Serializable;

public class ExtSysCalElementRefImpl extends EntityRefImpl implements ExtSysCalElementRef, Serializable {

   public ExtSysCalElementRefImpl(ExtSysCalElementCK key, String narrative) {
      super(key, narrative);
   }

   public ExtSysCalElementRefImpl(ExtSysCalElementPK key, String narrative) {
      super(key, narrative);
   }

   public ExtSysCalElementPK getExtSysCalElementPK() {
      return this.mKey instanceof ExtSysCalElementCK?((ExtSysCalElementCK)this.mKey).getExtSysCalElementPK():(ExtSysCalElementPK)this.mKey;
   }
}
