// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:15
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.impl.model.mapping.extsys;

import com.cedar.cp.impl.base.CPConnectionImpl;
import com.cedar.cp.impl.model.mapping.extsys.AbstractExtSysProxy;
import java.lang.reflect.Proxy;

public class genProxy extends AbstractExtSysProxy {

   public Object newInstance(CPConnectionImpl connection, Object obj) {
      return Proxy.newProxyInstance(obj.getClass().getClassLoader(), obj.getClass().getInterfaces(), new genProxy(connection, obj));
   }

   protected genProxy(CPConnectionImpl connection, Object target) {
      super(connection, target);
   }
}