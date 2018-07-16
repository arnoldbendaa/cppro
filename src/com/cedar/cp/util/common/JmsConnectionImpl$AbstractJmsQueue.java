// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:06:23
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.util.common;

import com.cedar.cp.api.base.CPException;
import com.cedar.cp.util.common.JmsConnectionImpl;
import com.cedar.cp.util.common.JmsConnectionImpl$JmsConnectionObject;
import java.io.Serializable;
import javax.jms.JMSException;
import javax.jms.Queue;
import javax.jms.QueueConnection;
import javax.jms.QueueConnectionFactory;
import javax.jms.QueueSession;
import javax.naming.Context;
import javax.naming.NamingException;

abstract class JmsConnectionImpl$AbstractJmsQueue implements JmsConnectionImpl$JmsConnectionObject {

   protected String mName;
   protected Queue mQueue;
   protected QueueConnectionFactory mQueueConnectionFactory;
   protected QueueConnection mQueueConnection;
   protected QueueSession mQueueSession;
   // $FF: synthetic field
   final JmsConnectionImpl this$0;


   public JmsConnectionImpl$AbstractJmsQueue(JmsConnectionImpl var1, Context context, String name) throws CPException {
      this.this$0 = var1;

      try {
         this.mName = name;
         this.mQueue = (Queue)context.lookup("jms/cp/" + name);
         this.mQueueConnectionFactory = (QueueConnectionFactory)context.lookup("jms/cp/ConnectionFactory");
         this.mQueueConnection = this.mQueueConnectionFactory.createQueueConnection();
      } catch (NamingException var5) {
         throw new CPException("can\'t create queue " + name, var5);
      } catch (JMSException var6) {
         var6.printStackTrace();
         throw new CPException("can\'t create queue " + name, var6);
      }
   }

   public void createSession() throws CPException {
      try {
         this.mQueueSession = this.mQueueConnection.createQueueSession(false, 1);
      } catch (JMSException var2) {
         throw new CPException("can\'t create session", var2);
      }
   }

   public void closeSession() throws CPException {
      try {
         this.mQueueSession.close();
         this.mQueueSession = null;
      } catch (JMSException var2) {
         throw new CPException("can\'t close session for queue " + this.mName, var2);
      }
   }

   public void closeConnection() throws CPException {
      try {
         if(this.mQueueSession != null) {
            this.closeSession();
         }

         this.mQueueConnection.close();
         this.mQueueConnection = null;
      } catch (JMSException var2) {
         throw new CPException("can\'t close session for queue " + this.mName, var2);
      }
   }

   public void send(Serializable o) throws CPException {
      throw new IllegalStateException("can\'t send on this connection");
   }

   public void send(Serializable o, long startTime) throws CPException {
      throw new IllegalStateException("can\'t send on this connection");
   }

   public Object receive() throws CPException {
      throw new IllegalStateException("can\'t receive on this connection");
   }
}
