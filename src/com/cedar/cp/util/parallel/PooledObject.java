// Decompiled by:       Fernflower v0.8.6
// Date:                11.08.2012 20:22:28
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.util.parallel;

import com.cedar.cp.util.parallel.AbstractPool;

public interface PooledObject {

   AbstractPool getPool();

   void setPool(AbstractPool var1);

   void returnedToPool();

   void takenFromPool();
}
