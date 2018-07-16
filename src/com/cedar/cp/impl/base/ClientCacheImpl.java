// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:04
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.impl.base;

import com.cedar.cp.api.base.ClientCache;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class ClientCacheImpl implements ClientCache {

   private Map mCache = Collections.synchronizedMap(new HashMap());


   public Object get(Object key_) {
      return this.mCache.get(key_);
   }

   public Object put(Object key_, Object o_) {
      return this.mCache.put(key_, o_);
   }

   public void clear() {
      this.mCache.clear();
   }

   public void remove(Object key) {
      this.mCache.remove(key);
   }

   public Iterator getKeySetIterator() {
      return this.mCache.keySet().iterator();
   }
}
