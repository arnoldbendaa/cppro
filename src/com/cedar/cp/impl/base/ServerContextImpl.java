// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:09
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.impl.base;

import com.cedar.cp.api.base.ServerContext;
import javax.naming.Context;

public class ServerContextImpl implements ServerContext {

   private Context mContext;


   public ServerContextImpl(Context context_) {
      this.mContext = context_;
   }

   public Object getContext() {
      return this.mContext;
   }
}
