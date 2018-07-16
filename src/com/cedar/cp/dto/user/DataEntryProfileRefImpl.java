// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:06:11
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.user;

import com.cedar.cp.api.user.DataEntryProfileRef;
import com.cedar.cp.dto.base.EntityRefImpl;
import com.cedar.cp.dto.user.DataEntryProfileCK;
import com.cedar.cp.dto.user.DataEntryProfilePK;
import java.io.Serializable;

public class DataEntryProfileRefImpl extends EntityRefImpl implements DataEntryProfileRef, Serializable {

   public DataEntryProfileRefImpl(DataEntryProfileCK key, String narrative) {
      super(key, narrative);
   }

   public DataEntryProfileRefImpl(DataEntryProfilePK key, String narrative) {
      super(key, narrative);
   }

   public DataEntryProfilePK getDataEntryProfilePK() {
      return this.mKey instanceof DataEntryProfileCK?((DataEntryProfileCK)this.mKey).getDataEntryProfilePK():(DataEntryProfilePK)this.mKey;
   }
}
