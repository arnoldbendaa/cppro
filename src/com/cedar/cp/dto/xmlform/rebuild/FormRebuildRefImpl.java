// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:06:13
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.xmlform.rebuild;

import com.cedar.cp.api.xmlform.rebuild.FormRebuildRef;
import com.cedar.cp.dto.base.EntityRefImpl;
import com.cedar.cp.dto.xmlform.rebuild.FormRebuildCK;
import com.cedar.cp.dto.xmlform.rebuild.FormRebuildPK;
import java.io.Serializable;

public class FormRebuildRefImpl extends EntityRefImpl implements FormRebuildRef, Serializable {

   public FormRebuildRefImpl(FormRebuildCK key, String narrative) {
      super(key, narrative);
   }

   public FormRebuildRefImpl(FormRebuildPK key, String narrative) {
      super(key, narrative);
   }

   public FormRebuildPK getFormRebuildPK() {
      return this.mKey instanceof FormRebuildCK?((FormRebuildCK)this.mKey).getFormRebuildPK():(FormRebuildPK)this.mKey;
   }
}
