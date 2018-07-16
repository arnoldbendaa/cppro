// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:06:10
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.udeflookup;

import com.cedar.cp.api.udeflookup.UdefLookupColumnDefRef;
import com.cedar.cp.dto.base.EntityRefImpl;
import com.cedar.cp.dto.udeflookup.UdefLookupColumnDefCK;
import com.cedar.cp.dto.udeflookup.UdefLookupColumnDefPK;
import java.io.Serializable;

public class UdefLookupColumnDefRefImpl extends EntityRefImpl implements UdefLookupColumnDefRef, Serializable {

   public UdefLookupColumnDefRefImpl(UdefLookupColumnDefCK key, String narrative) {
      super(key, narrative);
   }

   public UdefLookupColumnDefRefImpl(UdefLookupColumnDefPK key, String narrative) {
      super(key, narrative);
   }

   public UdefLookupColumnDefPK getUdefLookupColumnDefPK() {
      return this.mKey instanceof UdefLookupColumnDefCK?((UdefLookupColumnDefCK)this.mKey).getUdefLookupColumnDefPK():(UdefLookupColumnDefPK)this.mKey;
   }
}
