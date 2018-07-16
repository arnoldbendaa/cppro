// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:56
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.model;

import com.cedar.cp.api.model.CellCalcAssocRef;
import com.cedar.cp.dto.base.EntityRefImpl;
import com.cedar.cp.dto.model.CellCalcAssocCK;
import com.cedar.cp.dto.model.CellCalcAssocPK;
import java.io.Serializable;

public class CellCalcAssocRefImpl extends EntityRefImpl implements CellCalcAssocRef, Serializable {

   public CellCalcAssocRefImpl(CellCalcAssocCK key, String narrative) {
      super(key, narrative);
   }

   public CellCalcAssocRefImpl(CellCalcAssocPK key, String narrative) {
      super(key, narrative);
   }

   public CellCalcAssocPK getCellCalcAssocPK() {
      return this.mKey instanceof CellCalcAssocCK?((CellCalcAssocCK)this.mKey).getCellCalcAssocPK():(CellCalcAssocPK)this.mKey;
   }
}
