package com.cedar.cp.impl.model.globalmapping2.extsys;

import com.cedar.cp.impl.base.CPConnectionImpl;
import com.cedar.cp.impl.model.globalmapping2.extsys.AbstractExtSysProxy;
import java.lang.reflect.Proxy;

public class e5Proxy extends AbstractExtSysProxy {

   public Object newInstance(CPConnectionImpl connection, Object obj) {
      return Proxy.newProxyInstance(obj.getClass().getClassLoader(), obj.getClass().getInterfaces(), new e5Proxy(connection, obj));
   }

   protected e5Proxy(CPConnectionImpl connection, Object target) {
      super(connection, target);
   }
}
