// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:06:23
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.util.common;

import com.cedar.cp.api.base.CPException;
import com.cedar.cp.util.common.JmsConnectionImpl;
import com.cedar.cp.util.common.JmsConnectionImpl$AbstractJmsQueue;
import com.cedar.cp.util.common.JmsConnectionImpl$JmsConnectionObject;
import java.io.Serializable;
import javax.jms.JMSException;
import javax.jms.ObjectMessage;
import javax.jms.QueueSender;
import javax.naming.Context;

class JmsConnectionImpl$JmsQueueSender extends JmsConnectionImpl$AbstractJmsQueue implements JmsConnectionImpl$JmsConnectionObject {

   private QueueSender mQueueSender;
   // $FF: synthetic field
   final JmsConnectionImpl this$0;


   public JmsConnectionImpl$JmsQueueSender(JmsConnectionImpl var1, Context context, String name) throws CPException {
      super(var1, context, name);
      this.this$0 = var1;
   }

   public void createSession() throws CPException {
      try {
         super.createSession();
         this.mQueueSender = this.mQueueSession.createSender(this.mQueue);
      } catch (JMSException var2) {
         throw new CPException("can\'t create session", var2);
      }
   }

   public void send(Serializable o) throws CPException {
      ObjectMessage om = null;

      try {
         om = this.mQueueSession.createObjectMessage(o);
         om.setJMSDeliveryMode(1);
         this.mQueueSender.send(om);
      } catch (JMSException var4) {
         throw new CPException("can\'t send to queue " + this.mName, var4);
      }
   }

   public void send(Serializable o, long startTime) throws CPException {
      ObjectMessage om = null;

      try {
         om = this.mQueueSession.createObjectMessage(o);
         om.setJMSDeliveryMode(1);
         om.setLongProperty("JMS_JBOSS_SCHEDULED_DELIVERY", startTime);
         this.mQueueSender.send(om);
      } catch (JMSException var6) {
         throw new CPException("can\'t send to queue " + this.mName, var6);
      }
   }
}
