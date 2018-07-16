// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:04:18
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.api.extsys;

import com.cedar.cp.api.extsys.ExtSysConnectorManager;
import java.lang.reflect.Method;

public class ExtSysConnectorManagerAccessor {

   public static ExtSysConnectorManager getExtSysConnectorManager() {
      try {
         Class t = Class.forName(ExtSysConnectorManager.sExtSysConnectorManagerImplClass, false, Thread.currentThread().getContextClassLoader());
         Method m = t.getMethod("getInstance", new Class[0]);
         
         Object result=m.invoke((Object)null, new Object[0]);
         if(result instanceof ExtSysConnectorManager){
        	 System.out.println("ExtSysConnectorManagerAccessor.getExtSysConnectorManager() - Instance of com.cedar.cp.api.extsys.ExtSysConnectorManager");
         }else{
        	 System.out.println("ExtSysConnectorManagerAccessor.getExtSysConnectorManager() - Not instance of com.cedar.cp.api.extsys.ExtSysConnectorManager");
         }
         
         return (ExtSysConnectorManager) result;
         //return (ExtSysConnectorManager)m.invoke((Object)null, new Object[0]);
      } catch (Throwable var2) {
         var2.printStackTrace();
         return null;
      }
   }
}
