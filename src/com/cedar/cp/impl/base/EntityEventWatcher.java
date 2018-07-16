// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:04
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.impl.base;

import com.cedar.cp.api.base.CPConnection;
import com.cedar.cp.api.base.CPException;
import com.cedar.cp.api.base.ClientCache;
import com.cedar.cp.api.base.EntityList;
import com.cedar.cp.api.base.JmsConnection;
import com.cedar.cp.dto.base.EntityEventMessage;
import com.cedar.cp.impl.base.EntityEventWatcher$1;
import com.cedar.cp.util.Log;
import com.cedar.cp.util.Timer;
import java.util.Iterator;

public class EntityEventWatcher {

   private Log mLog = new Log(this.getClass());
   private CPConnection mConnection;
   private JmsConnection mJmsConnection;
   private Thread mWatcher;
   private boolean mKeepRunning;
   private Boolean mInit = Boolean.valueOf(false);


   public EntityEventWatcher(CPConnection conn) throws CPException {
      this.mConnection = conn;
   }

   private void init() {
      Timer timer = this.mLog.isDebugEnabled()?new Timer(this.mLog):null;

      try {
         this.mJmsConnection = this.mConnection.getJmsConnection(4, "entityEventTopic");
         this.mJmsConnection.createSession();
         System.out.println("jms has been started");
      } catch (CPException var3) {
         System.out.println("jms has failed to start");
         this.mLog.error("failed to start jms", var3);
         return;
      }

      if(timer != null) {
         timer.logDebug("created session for entityEventTopic");
      }

   }

   public void start() {
      if(this.mWatcher == null) {
         this.mWatcher = new EntityEventWatcher$1(this);
         this.mKeepRunning = true;
         this.mWatcher.start();
      } else {
         this.mLog.debug("Already running");
      }

   }

   public void stop() {
      Boolean var1 = this.mInit;
      synchronized(this.mInit) {
         this.mKeepRunning = false;
         if(this.mWatcher != null && this.mWatcher.isAlive()) {
            this.mWatcher.interrupt();
         }

         if(this.mJmsConnection != null) {
            this.mJmsConnection.closeConnection();
         }

      }
   }

   private void checkCache(EntityEventMessage em) {
      try {
         ClientCache e = this.mConnection.getClientCache();
         Iterator i = e.getKeySetIterator();

         while(i.hasNext()) {
            Object key = i.next();
            Object o = e.get(key);
            if(o instanceof EntityList) {
               EntityList el = (EntityList)o;
               if(el.includesEntity(em.getEntityName())) {
                  i.remove();
               }
            }
         }
      } catch (Throwable var7) {
         var7.printStackTrace();
      }

   }

   // $FF: synthetic method
   static Boolean accessMethod000(EntityEventWatcher x0) {
      return x0.mInit;
   }

   // $FF: synthetic method
   static boolean accessMethod100(EntityEventWatcher x0) {
      return x0.mKeepRunning;
   }

   // $FF: synthetic method
   static void accessMethod200(EntityEventWatcher x0) {
      x0.init();
   }

   // $FF: synthetic method
   static JmsConnection accessMethod300(EntityEventWatcher x0) {
      return x0.mJmsConnection;
   }

   // $FF: synthetic method
   static void accessMethod400(EntityEventWatcher x0, EntityEventMessage x1) {
      x0.checkCache(x1);
   }
}
