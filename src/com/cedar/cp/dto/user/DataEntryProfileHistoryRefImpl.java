// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:06:11
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.user;

import com.cedar.cp.api.user.DataEntryProfileHistoryRef;
import com.cedar.cp.dto.base.EntityRefImpl;
import com.cedar.cp.dto.user.DataEntryProfileHistoryCK;
import com.cedar.cp.dto.user.DataEntryProfileHistoryPK;
import java.io.Serializable;

public class DataEntryProfileHistoryRefImpl extends EntityRefImpl implements DataEntryProfileHistoryRef, Serializable {

   public DataEntryProfileHistoryRefImpl(DataEntryProfileHistoryCK key, String narrative) {
      super(key, narrative);
   }

   public DataEntryProfileHistoryRefImpl(DataEntryProfileHistoryPK key, String narrative) {
      super(key, narrative);
   }

   public DataEntryProfileHistoryPK getDataEntryProfileHistoryPK() {
      return this.mKey instanceof DataEntryProfileHistoryCK?((DataEntryProfileHistoryCK)this.mKey).getDataEntryProfileHistoryPK():(DataEntryProfileHistoryPK)this.mKey;
   }
}
