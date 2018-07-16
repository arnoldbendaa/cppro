// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:06:07
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.report.destination.internal;

import com.cedar.cp.api.report.destination.internal.InternalDestinationUsersRef;
import com.cedar.cp.dto.base.EntityRefImpl;
import com.cedar.cp.dto.report.destination.internal.InternalDestinationUsersCK;
import com.cedar.cp.dto.report.destination.internal.InternalDestinationUsersPK;
import java.io.Serializable;

public class InternalDestinationUsersRefImpl extends EntityRefImpl implements InternalDestinationUsersRef, Serializable {

   public InternalDestinationUsersRefImpl(InternalDestinationUsersCK key, String narrative) {
      super(key, narrative);
   }

   public InternalDestinationUsersRefImpl(InternalDestinationUsersPK key, String narrative) {
      super(key, narrative);
   }

   public InternalDestinationUsersPK getInternalDestinationUsersPK() {
      return this.mKey instanceof InternalDestinationUsersCK?((InternalDestinationUsersCK)this.mKey).getInternalDestinationUsersPK():(InternalDestinationUsersPK)this.mKey;
   }
}
