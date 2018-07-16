// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:53
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.extsys;

import com.cedar.cp.api.extsys.ExtSysLedgerRef;
import com.cedar.cp.dto.base.EntityRefImpl;
import com.cedar.cp.dto.extsys.ExtSysLedgerCK;
import com.cedar.cp.dto.extsys.ExtSysLedgerPK;
import java.io.Serializable;

public class ExtSysLedgerRefImpl extends EntityRefImpl implements ExtSysLedgerRef, Serializable {

   public ExtSysLedgerRefImpl(ExtSysLedgerCK key, String narrative) {
      super(key, narrative);
   }

   public ExtSysLedgerRefImpl(ExtSysLedgerPK key, String narrative) {
      super(key, narrative);
   }

   public ExtSysLedgerPK getExtSysLedgerPK() {
      return this.mKey instanceof ExtSysLedgerCK?((ExtSysLedgerCK)this.mKey).getExtSysLedgerPK():(ExtSysLedgerPK)this.mKey;
   }
}
