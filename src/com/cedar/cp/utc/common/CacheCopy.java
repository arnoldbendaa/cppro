// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:35:00
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.utc.common;

import com.cedar.cp.api.base.ClientCache;
import com.cedar.cp.util.xmlform.CalendarInfo;
import com.cedar.cp.util.xmlform.inputs.LookupData;
import java.util.Iterator;
import java.util.Map;

public class CacheCopy {

   public static void copyClientToContext(ClientCache clientCache, Map contextVariables) {
      Iterator i = clientCache.getKeySetIterator();

      while(i.hasNext()) {
         Object key = i.next();
         Object o = clientCache.get(key);
         if((o instanceof LookupData || o instanceof CalendarInfo) && contextVariables.get(key) == null) {
            contextVariables.put(key, o);
            System.out.println(key + " reused");
         }
      }

   }

   public static void copyContextToClient(Map contextVariables, ClientCache clientCache) {
      Iterator i$ = contextVariables.keySet().iterator();

      while(i$.hasNext()) {
         Object key = i$.next();
         Object o = contextVariables.get(key);
         if((o instanceof LookupData || o instanceof CalendarInfo) && clientCache.get(key) == null) {
            ;
         }
      }

   }
}
