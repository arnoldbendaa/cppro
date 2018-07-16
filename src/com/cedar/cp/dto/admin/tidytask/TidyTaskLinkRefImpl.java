// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:46
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.admin.tidytask;

import com.cedar.cp.api.admin.tidytask.TidyTaskLinkRef;
import com.cedar.cp.dto.admin.tidytask.TidyTaskLinkCK;
import com.cedar.cp.dto.admin.tidytask.TidyTaskLinkPK;
import com.cedar.cp.dto.base.EntityRefImpl;
import java.io.Serializable;

public class TidyTaskLinkRefImpl extends EntityRefImpl implements TidyTaskLinkRef, Serializable {

   public TidyTaskLinkRefImpl(TidyTaskLinkCK key, String narrative) {
      super(key, narrative);
   }

   public TidyTaskLinkRefImpl(TidyTaskLinkPK key, String narrative) {
      super(key, narrative);
   }

   public TidyTaskLinkPK getTidyTaskLinkPK() {
      return this.mKey instanceof TidyTaskLinkCK?((TidyTaskLinkCK)this.mKey).getTidyTaskLinkPK():(TidyTaskLinkPK)this.mKey;
   }
}
