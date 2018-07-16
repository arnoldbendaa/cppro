// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:06:00
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.model.cc.imp.dyn;

import com.cedar.cp.api.model.cc.imp.dyn.ImportGridCellRef;
import com.cedar.cp.dto.base.EntityRefImpl;
import com.cedar.cp.dto.model.cc.imp.dyn.ImportGridCellPK;
import java.io.Serializable;

public class ImportGridCellRefImpl extends EntityRefImpl implements ImportGridCellRef, Serializable {

   public ImportGridCellRefImpl(ImportGridCellPK key, String narrative) {
      super(key, narrative);
   }

   public ImportGridCellPK getImportGridCellPK() {
      return (ImportGridCellPK)this.mKey;
   }
}
