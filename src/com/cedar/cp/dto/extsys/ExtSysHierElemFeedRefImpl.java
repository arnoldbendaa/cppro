// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:52
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.extsys;

import com.cedar.cp.api.extsys.ExtSysHierElemFeedRef;
import com.cedar.cp.dto.base.EntityRefImpl;
import com.cedar.cp.dto.extsys.ExtSysHierElemFeedCK;
import com.cedar.cp.dto.extsys.ExtSysHierElemFeedPK;
import java.io.Serializable;

public class ExtSysHierElemFeedRefImpl extends EntityRefImpl implements ExtSysHierElemFeedRef, Serializable {

   public ExtSysHierElemFeedRefImpl(ExtSysHierElemFeedCK key, String narrative) {
      super(key, narrative);
   }

   public ExtSysHierElemFeedRefImpl(ExtSysHierElemFeedPK key, String narrative) {
      super(key, narrative);
   }

   public ExtSysHierElemFeedPK getExtSysHierElemFeedPK() {
      return this.mKey instanceof ExtSysHierElemFeedCK?((ExtSysHierElemFeedCK)this.mKey).getExtSysHierElemFeedPK():(ExtSysHierElemFeedPK)this.mKey;
   }
}
