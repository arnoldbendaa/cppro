// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:34:59
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.utc.common;

import com.cedar.cp.api.base.CPConnection;
import com.cedar.cp.utc.common.CPContext;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class CPContextCache {

   private static Map<String, CPContext> sContexts = new ConcurrentHashMap();
   private static Map<CPContext, String> sIds = new ConcurrentHashMap();
   private static CPConnection conn;


   public static String getCPContextId(CPContext context) {
	  conn = context.getCPConnection();
      return context.getCPConnection().getListHelper().getCPContextId(context);
   }

   public static CPContext getCPContext(String id) {
      return id == null?null:(CPContext)conn.getListHelper().getCPContext(id);
   }

   public static void remove(CPContext context) {
      if(context != null) {
    	  conn.getListHelper().removeContext(context);
      }
   }

   public static Map getContextSnapShot() {
      return conn.getListHelper().getContextSnapShot();
   }

}
