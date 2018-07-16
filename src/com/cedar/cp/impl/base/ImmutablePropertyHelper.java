// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:05
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.impl.base;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import javax.naming.InitialContext;

public class ImmutablePropertyHelper {

   private static ImmutablePropertyHelper sInstance;
   private Properties mProperties = new Properties();


   private static ImmutablePropertyHelper getInstance() {
      if(sInstance == null) {
         sInstance = new ImmutablePropertyHelper();
      }

      return sInstance;
   }

   public static Map getProperties(String[] propKeys, InitialContext context) {
      return new HashMap();
   }
}
