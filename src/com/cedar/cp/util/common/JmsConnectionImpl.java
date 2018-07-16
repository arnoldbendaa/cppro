// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:06:23
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.util.common;

import com.cedar.cp.api.base.CPException;
import com.cedar.cp.api.base.JmsConnection;
import com.cedar.cp.util.Log;
import com.cedar.cp.util.common.JmsConnectionImpl$JmsConnectionObject;
import com.cedar.cp.util.common.JmsConnectionImpl$JmsQueueReceiver;
import com.cedar.cp.util.common.JmsConnectionImpl$JmsQueueSender;
import com.cedar.cp.util.common.JmsConnectionImpl$JmsTopicPublisher;
import com.cedar.cp.util.common.JmsConnectionImpl$JmsTopicSubscriber;
import java.io.Serializable;
import javax.naming.Context;

public class JmsConnectionImpl implements JmsConnection {

   private Log mLog = new Log(this.getClass());
   private JmsConnectionImpl$JmsConnectionObject mJmsConnectionObject;


   public JmsConnectionImpl(Context context, int type, String name) throws CPException {
      try {
         switch(type) {
         case 1:
            this.mJmsConnectionObject = new JmsConnectionImpl$JmsQueueSender(this, context, name);
            break;
         case 2:
            this.mJmsConnectionObject = new JmsConnectionImpl$JmsQueueReceiver(this, context, name);
            break;
         case 3:
            this.mJmsConnectionObject = new JmsConnectionImpl$JmsTopicPublisher(this, context, name);
            break;
         case 4:
            this.mJmsConnectionObject = new JmsConnectionImpl$JmsTopicSubscriber(this, context, name);
            break;
         default:
            throw new IllegalArgumentException("type is not valid");
         }

      } catch (CPException var5) {
         var5.printStackTrace();
         throw var5;
      }
   }

   public void createSession() throws CPException {
      this.mJmsConnectionObject.createSession();
   }

   public void closeSession() throws CPException {
      this.mJmsConnectionObject.closeSession();
   }

   public void closeConnection() throws CPException {
      this.mJmsConnectionObject.closeConnection();
   }

   public void send(Serializable message) throws CPException {
      this.mJmsConnectionObject.send(message);
   }

   public void send(Serializable message, long startTime) throws CPException {
      this.mJmsConnectionObject.send(message, startTime);
   }

   public Object receive() throws CPException {
      return this.mJmsConnectionObject.receive();
   }

   private void handleConnectionException(String methodName_, String message_) {
      byte sleepSeconds = 10;
      this.mLog.warn(methodName_, message_);
      this.mLog.warn(methodName_, "retrying in " + sleepSeconds);

      try {
         Thread.currentThread();
         Thread.sleep((long)(sleepSeconds * 1000));
      } catch (Exception var5) {
         ;
      }

   }

   // $FF: synthetic method
   static Log accessMethod000(JmsConnectionImpl x0) {
      return x0.mLog;
   }
}
