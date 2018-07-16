// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:53
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.extsys;

import com.cedar.cp.api.extsys.ExtSysPropertyRef;
import com.cedar.cp.dto.base.EntityRefImpl;
import com.cedar.cp.dto.extsys.ExtSysPropertyCK;
import com.cedar.cp.dto.extsys.ExtSysPropertyPK;
import java.io.Serializable;

public class ExtSysPropertyRefImpl extends EntityRefImpl implements ExtSysPropertyRef, Serializable {

   public ExtSysPropertyRefImpl(ExtSysPropertyCK key, String narrative) {
      super(key, narrative);
   }

   public ExtSysPropertyRefImpl(ExtSysPropertyPK key, String narrative) {
      super(key, narrative);
   }

   public ExtSysPropertyPK getExtSysPropertyPK() {
      return this.mKey instanceof ExtSysPropertyCK?((ExtSysPropertyCK)this.mKey).getExtSysPropertyPK():(ExtSysPropertyPK)this.mKey;
   }
}
