// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:06:05
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.perftestrun;

import com.cedar.cp.api.perftestrun.PerfTestRunRef;
import com.cedar.cp.dto.base.EntityRefImpl;
import com.cedar.cp.dto.perftestrun.PerfTestRunPK;
import java.io.Serializable;

public class PerfTestRunRefImpl extends EntityRefImpl implements PerfTestRunRef, Serializable {

   public PerfTestRunRefImpl(PerfTestRunPK key, String narrative) {
      super(key, narrative);
   }

   public PerfTestRunPK getPerfTestRunPK() {
      return (PerfTestRunPK)this.mKey;
   }
}
