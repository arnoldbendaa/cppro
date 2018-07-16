// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:06:08
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.report.tran;

import com.cedar.cp.api.report.tran.CubePendingTranRef;
import com.cedar.cp.dto.base.EntityRefImpl;
import com.cedar.cp.dto.report.tran.CubePendingTranCK;
import com.cedar.cp.dto.report.tran.CubePendingTranPK;
import java.io.Serializable;

public class CubePendingTranRefImpl extends EntityRefImpl implements CubePendingTranRef, Serializable {

   public CubePendingTranRefImpl(CubePendingTranCK key, String narrative) {
      super(key, narrative);
   }

   public CubePendingTranRefImpl(CubePendingTranPK key, String narrative) {
      super(key, narrative);
   }

   public CubePendingTranPK getCubePendingTranPK() {
      return this.mKey instanceof CubePendingTranCK?((CubePendingTranCK)this.mKey).getCubePendingTranPK():(CubePendingTranPK)this.mKey;
   }
}
