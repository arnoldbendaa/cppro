// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:53
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.extsys;

import com.cedar.cp.api.extsys.ExternalSystemRef;
import com.cedar.cp.dto.base.EntityRefImpl;
import com.cedar.cp.dto.extsys.ExternalSystemPK;
import java.io.Serializable;

public class ExternalSystemRefImpl extends EntityRefImpl implements ExternalSystemRef, Serializable {

   public ExternalSystemRefImpl(ExternalSystemPK key, String narrative) {
      super(key, narrative);
   }

   public ExternalSystemPK getExternalSystemPK() {
      return (ExternalSystemPK)this.mKey;
   }
}
