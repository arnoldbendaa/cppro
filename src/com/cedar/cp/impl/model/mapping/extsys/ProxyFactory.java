// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:15
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.impl.model.mapping.extsys;

import com.cedar.cp.api.model.mapping.extsys.ExternalSystem;
import com.cedar.cp.dto.model.mapping.extsys.ExternalSystemImpl;
import com.cedar.cp.impl.base.CPConnectionImpl;
import com.cedar.cp.impl.model.mapping.extsys.OAProxy;
import com.cedar.cp.impl.model.mapping.extsys.e5Proxy;
import com.cedar.cp.impl.model.mapping.extsys.eFinProxy;
import com.cedar.cp.impl.model.mapping.extsys.genProxy;

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
