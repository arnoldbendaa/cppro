// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:06:06
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.report.destination.external;

import com.cedar.cp.api.report.destination.external.ExternalDestinationRef;
import com.cedar.cp.dto.base.EntityRefImpl;
import com.cedar.cp.dto.report.destination.external.ExternalDestinationPK;
import java.io.Serializable;

public class ExternalDestinationRefImpl extends EntityRefImpl implements ExternalDestinationRef, Serializable {

   public ExternalDestinationRefImpl(ExternalDestinationPK key, String narrative) {
      super(key, narrative);
   }

   public ExternalDestinationPK getExternalDestinationPK() {
      return (ExternalDestinationPK)this.mKey;
   }
}
