// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:56
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.model;

import com.cedar.cp.api.model.CellCalcRef;
import com.cedar.cp.dto.base.EntityRefImpl;
import com.cedar.cp.dto.model.CellCalcCK;
import com.cedar.cp.dto.model.CellCalcPK;
import java.io.Serializable;

public class CellCalcRefImpl extends EntityRefImpl implements CellCalcRef, Serializable {

   public CellCalcRefImpl(CellCalcCK key, String narrative) {
      super(key, narrative);
   }

   public CellCalcRefImpl(CellCalcPK key, String narrative) {
      super(key, narrative);
   }

   public CellCalcPK getCellCalcPK() {
      return this.mKey instanceof CellCalcCK?((CellCalcCK)this.mKey).getCellCalcPK():(CellCalcPK)this.mKey;
   }
}
