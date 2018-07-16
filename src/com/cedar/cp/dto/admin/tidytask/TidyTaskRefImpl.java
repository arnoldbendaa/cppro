// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:46
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.admin.tidytask;

import com.cedar.cp.api.admin.tidytask.TidyTaskRef;
import com.cedar.cp.dto.admin.tidytask.TidyTaskPK;
import com.cedar.cp.dto.base.EntityRefImpl;
import java.io.Serializable;

public class TidyTaskRefImpl extends EntityRefImpl implements TidyTaskRef, Serializable {

   public TidyTaskRefImpl(TidyTaskPK key, String narrative) {
      super(key, narrative);
   }

   public TidyTaskPK getTidyTaskPK() {
      return (TidyTaskPK)this.mKey;
   }
}
