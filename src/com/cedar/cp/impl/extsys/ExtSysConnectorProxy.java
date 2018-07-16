// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:12
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.impl.extsys;

import com.cedar.cp.api.extsys.ExtSysConnector;
import java.lang.reflect.Method;
import java.util.Properties;
import javax.swing.table.TableModel;

public class ExtSysConnectorProxy implements ExtSysConnector {

   private Object mTarget;


   public ExtSysConnectorProxy(Object target) {
      this.mTarget = target;
   }

   public void startup(Properties properties) throws Exception {
      Method method = this.findMethod(this.mTarget, "startup");
      if(method == null) {
         throw new Exception("Unable to locate method \'void startUp( Properties props ) throws Exception\' in connector.");
      } else {
         method.invoke(this.mTarget, new Object[]{properties});
      }
   }

   public TableModel queryDrillback(String company, String ledger, String[] dimVisIds, String[] elementVisIds, String[] nullElementVisIds, Integer year, String[] periods, Integer highestPeriod, String valueType, String currency, String currencyType, StringBuilder headerBuffer, Properties modelProperties) throws Exception {
      Method method = this.findMethod(this.mTarget, "queryDrillback");
      if(method == null) {
         throw new Exception("Unable to locate method \'TableModel queryDrillback( ... ) throws Exception\' in connector.");
      } else {
         return (TableModel)method.invoke(this.mTarget, new Object[]{company, ledger, dimVisIds, elementVisIds, nullElementVisIds, year, periods, highestPeriod, valueType, currency, currencyType, headerBuffer, modelProperties});
      }
   }

   public void closedown() throws Exception {
      Method method = this.findMethod(this.mTarget, "closedown");
      if(method == null) {
         throw new Exception("Unable to locate method \'void closedown() throws Exception\' in connector.");
      } else {
         method.invoke(this.mTarget, new Object[0]);
      }
   }

   private Method findMethod(Object target, String name) {
      Method[] methods = target.getClass().getMethods();
      Method[] arr$ = methods;
      int len$ = methods.length;

      for(int i$ = 0; i$ < len$; ++i$) {
         Method method = arr$[i$];
         if(method.getName().equals(name)) {
            return method;
         }
      }

      return null;
   }
}
