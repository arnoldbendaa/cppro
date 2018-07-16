// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:06:05
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.perftest;

import com.cedar.cp.api.perftest.PerfTestRef;
import com.cedar.cp.dto.base.EntityRefImpl;
import com.cedar.cp.dto.perftest.PerfTestPK;
import java.io.Serializable;

public class PerfTestRefImpl extends EntityRefImpl implements PerfTestRef, Serializable {

   public PerfTestRefImpl(PerfTestPK key, String narrative) {
      super(key, narrative);
   }

   public PerfTestPK getPerfTestPK() {
      return (PerfTestPK)this.mKey;
   }
}
