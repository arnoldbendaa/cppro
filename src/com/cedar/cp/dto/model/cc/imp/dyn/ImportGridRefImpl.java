// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:06:00
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.model.cc.imp.dyn;

import com.cedar.cp.api.model.cc.imp.dyn.ImportGridRef;
import com.cedar.cp.dto.base.EntityRefImpl;
import com.cedar.cp.dto.model.cc.imp.dyn.ImportGridCK;
import com.cedar.cp.dto.model.cc.imp.dyn.ImportGridPK;
import java.io.Serializable;

public class ImportGridRefImpl extends EntityRefImpl implements ImportGridRef, Serializable {

   public ImportGridRefImpl(ImportGridCK key, String narrative) {
      super(key, narrative);
   }

   public ImportGridRefImpl(ImportGridPK key, String narrative) {
      super(key, narrative);
   }

   public ImportGridPK getImportGridPK() {
      return this.mKey instanceof ImportGridCK?((ImportGridCK)this.mKey).getImportGridPK():(ImportGridPK)this.mKey;
   }
}
