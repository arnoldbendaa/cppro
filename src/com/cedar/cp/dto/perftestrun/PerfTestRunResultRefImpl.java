// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:06:05
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.perftestrun;

import com.cedar.cp.api.perftestrun.PerfTestRunResultRef;
import com.cedar.cp.dto.base.EntityRefImpl;
import com.cedar.cp.dto.perftestrun.PerfTestRunResultCK;
import com.cedar.cp.dto.perftestrun.PerfTestRunResultPK;
import java.io.Serializable;

public class PerfTestRunResultRefImpl extends EntityRefImpl implements PerfTestRunResultRef, Serializable {

   public PerfTestRunResultRefImpl(PerfTestRunResultCK key, String narrative) {
      super(key, narrative);
   }

   public PerfTestRunResultRefImpl(PerfTestRunResultPK key, String narrative) {
      super(key, narrative);
   }

   public PerfTestRunResultPK getPerfTestRunResultPK() {
      return this.mKey instanceof PerfTestRunResultCK?((PerfTestRunResultCK)this.mKey).getPerfTestRunResultPK():(PerfTestRunResultPK)this.mKey;
   }
}
