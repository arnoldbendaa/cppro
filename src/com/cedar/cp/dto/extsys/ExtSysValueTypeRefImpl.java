// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:53
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.extsys;

import com.cedar.cp.api.extsys.ExtSysValueTypeRef;
import com.cedar.cp.dto.base.EntityRefImpl;
import com.cedar.cp.dto.extsys.ExtSysValueTypeCK;
import com.cedar.cp.dto.extsys.ExtSysValueTypePK;
import java.io.Serializable;

public class ExtSysValueTypeRefImpl extends EntityRefImpl implements ExtSysValueTypeRef, Serializable {

   public ExtSysValueTypeRefImpl(ExtSysValueTypeCK key, String narrative) {
      super(key, narrative);
   }

   public ExtSysValueTypeRefImpl(ExtSysValueTypePK key, String narrative) {
      super(key, narrative);
   }

   public ExtSysValueTypePK getExtSysValueTypePK() {
      return this.mKey instanceof ExtSysValueTypeCK?((ExtSysValueTypeCK)this.mKey).getExtSysValueTypePK():(ExtSysValueTypePK)this.mKey;
   }
}
