// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:06:23
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.util.common;

import com.cedar.cp.api.base.CPConnection;
import com.cedar.cp.api.base.CPException;
import com.cedar.cp.api.base.JmsConnection;
import com.cedar.cp.api.base.JmsConnectionHelper;
import com.cedar.cp.util.Log;
import com.cedar.cp.util.Timer;

public class JmsConnectionHelperImpl implements JmsConnectionHelper {

   private CPConnection mConnection;
   private Log mLog = new Log(this.getClass());


   public JmsConnectionHelperImpl(CPConnection conn) {
      this.mConnection = conn;
   }

   public JmsConnection getJmsConnection(int type, String name) {
      String cacheKey = "jmsConnection " + type + " " + name;
      JmsConnection jms = (JmsConnection)this.mConnection.getClientCache().get(cacheKey);
      if(jms == null) {
         Timer timer = this.mLog.isDebugEnabled()?new Timer(this.mLog):null;

         try {
            jms = this.mConnection.getJmsConnection(type, name);
         } catch (CPException var7) {
            var7.printStackTrace();
            throw new RuntimeException("can\'t get jms connection " + name);
         }

         this.mConnection.getClientCache().put(cacheKey, jms);
         if(timer != null) {
            timer.logDebug("getJmsConnection", "type=" + type + " name=" + name);
         }
      } else {
         this.mLog.debug("getJmsConnection", "obtained type=" + type + " name=" + " from cache");
      }

      return jms;
   }
}
