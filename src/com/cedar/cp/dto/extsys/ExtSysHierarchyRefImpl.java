// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:52
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.extsys;

import com.cedar.cp.api.extsys.ExtSysHierarchyRef;
import com.cedar.cp.dto.base.EntityRefImpl;
import com.cedar.cp.dto.extsys.ExtSysHierarchyCK;
import com.cedar.cp.dto.extsys.ExtSysHierarchyPK;
import java.io.Serializable;

public class ExtSysHierarchyRefImpl extends EntityRefImpl implements ExtSysHierarchyRef, Serializable {

   public ExtSysHierarchyRefImpl(ExtSysHierarchyCK key, String narrative) {
      super(key, narrative);
   }

   public ExtSysHierarchyRefImpl(ExtSysHierarchyPK key, String narrative) {
      super(key, narrative);
   }

   public ExtSysHierarchyPK getExtSysHierarchyPK() {
      return this.mKey instanceof ExtSysHierarchyCK?((ExtSysHierarchyCK)this.mKey).getExtSysHierarchyPK():(ExtSysHierarchyPK)this.mKey;
   }
}
