// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:08:01
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.impl.base;

import com.cedar.cp.ejb.base.common.cache.DAGContext;
import java.io.Serializable;

public abstract class AbstractDAG implements Serializable {

   private DAGContext mContext;
   private boolean mDirty;


   public AbstractDAG(DAGContext context, boolean dirty) {
      this.mContext = context;
      this.mDirty = dirty;
   }

   public boolean isDirty() {
      return this.mDirty;
   }

   public void setDirty(boolean dirty) {
      this.mDirty = dirty;
   }

   public void setDirty() {
      this.mDirty = true;
   }

   public DAGContext getContext() {
      return this.mContext;
   }

   public void removeFromCache() {
      this.mContext.getCache().remove(this);
   }
}
