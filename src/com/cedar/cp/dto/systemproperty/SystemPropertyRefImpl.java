// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:06:09
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.systemproperty;

import com.cedar.cp.api.systemproperty.SystemPropertyRef;
import com.cedar.cp.dto.base.EntityRefImpl;
import com.cedar.cp.dto.systemproperty.SystemPropertyPK;
import java.io.Serializable;

public class SystemPropertyRefImpl extends EntityRefImpl implements SystemPropertyRef, Serializable {

   public SystemPropertyRefImpl(SystemPropertyPK key, String narrative) {
      super(key, narrative);
   }

   public SystemPropertyPK getSystemPropertyPK() {
      return (SystemPropertyPK)this.mKey;
   }
}
