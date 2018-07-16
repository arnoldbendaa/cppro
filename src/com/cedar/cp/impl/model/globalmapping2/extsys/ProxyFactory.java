package com.cedar.cp.impl.model.globalmapping2.extsys;

import com.cedar.cp.api.model.globalmapping2.extsys.ExternalSystem;
import com.cedar.cp.dto.model.globalmapping2.extsys.ExternalSystemImpl;
import com.cedar.cp.impl.base.CPConnectionImpl;
import com.cedar.cp.impl.model.globalmapping2.extsys.OAProxy;
import com.cedar.cp.impl.model.globalmapping2.extsys.e5Proxy;
import com.cedar.cp.impl.model.globalmapping2.extsys.eFinProxy;
import com.cedar.cp.impl.model.globalmapping2.extsys.genProxy;

public class ProxyFactory {

   public static ExternalSystem newProxy(CPConnectionImpl connection, ExternalSystemImpl extSys) {
      switch(extSys.getSystemType()) {
      case 3:
         return (ExternalSystem)(new eFinProxy(connection, extSys)).newInstance(connection, extSys);
      case 5:
         return (ExternalSystem)(new e5Proxy(connection, extSys)).newInstance(connection, extSys);
      case 10:
         return (ExternalSystem)(new OAProxy(connection, extSys)).newInstance(connection, extSys);
      case 20:
         return (ExternalSystem)(new genProxy(connection, extSys)).newInstance(connection, extSys);
      default:
         return extSys;
      }
   }
}
