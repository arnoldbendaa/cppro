// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:06:07
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.report.destination.external;

import com.cedar.cp.api.report.destination.external.ExternalDestinationUsersRef;
import com.cedar.cp.dto.base.EntityRefImpl;
import com.cedar.cp.dto.report.destination.external.ExternalDestinationUsersCK;
import com.cedar.cp.dto.report.destination.external.ExternalDestinationUsersPK;
import java.io.Serializable;

public class ExternalDestinationUsersRefImpl extends EntityRefImpl implements ExternalDestinationUsersRef, Serializable {

   public ExternalDestinationUsersRefImpl(ExternalDestinationUsersCK key, String narrative) {
      super(key, narrative);
   }

   public ExternalDestinationUsersRefImpl(ExternalDestinationUsersPK key, String narrative) {
      super(key, narrative);
   }

   public ExternalDestinationUsersPK getExternalDestinationUsersPK() {
      return this.mKey instanceof ExternalDestinationUsersCK?((ExternalDestinationUsersCK)this.mKey).getExternalDestinationUsersPK():(ExternalDestinationUsersPK)this.mKey;
   }
}
