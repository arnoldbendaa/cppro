// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:04:17
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.api.base;

import java.util.Iterator;

public interface ClientCache {

   Object get(Object var1);

   Object put(Object var1, Object var2);

   void clear();

   void remove(Object var1);

   Iterator getKeySetIterator();
}
