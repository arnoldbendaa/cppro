// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:07:02
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.base.common.cache;

import com.cedar.cp.ejb.impl.base.AbstractDAG;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class DAGCache implements Serializable {

   private Map mMap = new HashMap();


   public AbstractDAG get(Class clsKey, Object objKey) {
      Map clsMap = (Map)this.mMap.get(clsKey);
      return clsMap != null?(AbstractDAG)clsMap.get(objKey):null;
   }

   public AbstractDAG put(Object key, AbstractDAG obj) {
      Class cls = obj.getClass();
      Object clsMap = (Map)this.mMap.get(cls);
      if(clsMap == null) {
         clsMap = new HashMap();
         this.mMap.put(cls, clsMap);
      }

      return (AbstractDAG)((Map)clsMap).put(key, obj);
   }

   public void remove(AbstractDAG obj) {
      Class cls = obj.getClass();
      Map clsMap = (Map)this.mMap.get(cls);
      if(clsMap != null) {
         clsMap.remove(obj);
      }

   }
}
