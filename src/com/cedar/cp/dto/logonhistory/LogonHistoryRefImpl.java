// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:53
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.logonhistory;

import com.cedar.cp.api.logonhistory.LogonHistoryRef;
import com.cedar.cp.dto.base.EntityRefImpl;
import com.cedar.cp.dto.logonhistory.LogonHistoryPK;
import java.io.Serializable;

public class LogonHistoryRefImpl extends EntityRefImpl implements LogonHistoryRef, Serializable {

   public LogonHistoryRefImpl(LogonHistoryPK key, String narrative) {
      super(key, narrative);
   }

   public LogonHistoryPK getLogonHistoryPK() {
      return (LogonHistoryPK)this.mKey;
   }
}
