// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:06:10
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.udeflookup;

import com.cedar.cp.api.udeflookup.UdefLookupRef;
import com.cedar.cp.dto.base.EntityRefImpl;
import com.cedar.cp.dto.udeflookup.UdefLookupPK;
import java.io.Serializable;

public class UdefLookupRefImpl extends EntityRefImpl implements UdefLookupRef, Serializable {

   public UdefLookupRefImpl(UdefLookupPK key, String narrative) {
      super(key, narrative);
   }

   public UdefLookupPK getUdefLookupPK() {
      return (UdefLookupPK)this.mKey;
   }
}
