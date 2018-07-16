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
import javax.jms.Topic;
import javax.jms.TopicConnection;
import javax.jms.TopicConnectionFactory;
import javax.jms.TopicSession;
import javax.naming.Context;
import javax.naming.NamingException;

abstract class JmsConnectionImpl$AbstractJmsTopic implements JmsConnectionImpl$JmsConnectionObject {

   String mName;
   Topic mTopic;
   TopicConnectionFactory mTopicConnectionFactory;
   TopicConnection mTopicConnection;
   TopicSession mTopicSession;
   // $FF: synthetic field
   final JmsConnectionImpl this$0;


   public JmsConnectionImpl$AbstractJmsTopic(JmsConnectionImpl var1, Context context, String name) throws CPException {
      this.this$0 = var1;

      try {
         this.mTopic = (Topic)context.lookup("jms/cp/" + name);
         this.mTopicConnectionFactory = (TopicConnectionFactory)context.lookup("ConnectionFactory");
         this.mTopicConnection = this.mTopicConnectionFactory.createTopicConnection();
      } catch (NamingException var5) {
         var5.printStackTrace();
         throw new CPException("can\'t create topic " + name, var5);
      } catch (JMSException var6) {
         var6.printStackTrace();
         throw new CPException("can\'t create topic " + name, var6);
      }
   }

   public void createSession() throws CPException {
      try {
         this.mTopicSession = this.mTopicConnection.createTopicSession(false, 1);
      } catch (JMSException var2) {
         var2.printStackTrace();
         throw new CPException("can\'t create session for topic" + this.mName, var2);
      }
   }

   public void closeSession() throws CPException {
      try {
         this.mTopicSession.close();
         this.mTopicSession = null;
      } catch (JMSException var2) {
         var2.printStackTrace();
         throw new CPException("can\'t close session for topic " + this.mName, var2);
      }
   }

   public void closeConnection() throws CPException {
      try {
         if(this.mTopicSession != null) {
            this.closeSession();
         }

         if(this.mTopicConnection != null) {
            this.mTopicConnection.close();
            this.mTopicConnection = null;
         }

      } catch (JMSException var2) {
         var2.printStackTrace();
         throw new CPException("can\'t close connection for topic " + this.mName, var2);
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
