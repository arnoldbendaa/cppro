// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:52
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.extsys;

import com.cedar.cp.api.extsys.ExtSysHierElementRef;
import com.cedar.cp.dto.base.EntityRefImpl;
import com.cedar.cp.dto.extsys.ExtSysHierElementCK;
import com.cedar.cp.dto.extsys.ExtSysHierElementPK;
import java.io.Serializable;

public class ExtSysHierElementRefImpl extends EntityRefImpl implements ExtSysHierElementRef, Serializable {

   public ExtSysHierElementRefImpl(ExtSysHierElementCK key, String narrative) {
      super(key, narrative);
   }

   public ExtSysHierElementRefImpl(ExtSysHierElementPK key, String narrative) {
      super(key, narrative);
   }

   public ExtSysHierElementPK getExtSysHierElementPK() {
      return this.mKey instanceof ExtSysHierElementCK?((ExtSysHierElementCK)this.mKey).getExtSysHierElementPK():(ExtSysHierElementPK)this.mKey;
   }
}
