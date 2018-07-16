// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:06:07
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.report.destination.internal;

import com.cedar.cp.api.report.destination.internal.InternalDestinationRef;
import com.cedar.cp.dto.base.EntityRefImpl;
import com.cedar.cp.dto.report.destination.internal.InternalDestinationPK;
import java.io.Serializable;

public class InternalDestinationRefImpl extends EntityRefImpl implements InternalDestinationRef, Serializable {

   public InternalDestinationRefImpl(InternalDestinationPK key, String narrative) {
      super(key, narrative);
   }

   public InternalDestinationPK getInternalDestinationPK() {
      return (InternalDestinationPK)this.mKey;
   }
}
