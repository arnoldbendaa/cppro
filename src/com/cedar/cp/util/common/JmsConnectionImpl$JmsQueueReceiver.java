// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:06:23
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.util.common;

import com.cedar.cp.api.base.CPException;
import com.cedar.cp.util.common.JmsConnectionImpl;
import com.cedar.cp.util.common.JmsConnectionImpl$AbstractJmsQueue;
import com.cedar.cp.util.common.JmsConnectionImpl$JmsConnectionObject;
import javax.jms.JMSException;
import javax.jms.ObjectMessage;
import javax.jms.QueueReceiver;
import javax.naming.Context;

class JmsConnectionImpl$JmsQueueReceiver extends JmsConnectionImpl$AbstractJmsQueue implements JmsConnectionImpl$JmsConnectionObject {

   QueueReceiver mQueueReceiver;
   // $FF: synthetic field
   final JmsConnectionImpl this$0;


   public JmsConnectionImpl$JmsQueueReceiver(JmsConnectionImpl var1, Context context, String name) throws CPException {
      super(var1, context, name);
      this.this$0 = var1;
   }

   public void createSession() throws CPException {
      try {
         super.createSession();
         this.mQueueReceiver = this.mQueueSession.createReceiver(this.mQueue);
      } catch (JMSException var2) {
         throw new CPException("can\'t create session", var2);
      }
   }

   public Object receive() throws CPException {
      ObjectMessage om = null;

      try {
         om = (ObjectMessage)this.mQueueReceiver.receive();
         return om.getObject();
      } catch (JMSException var3) {
         throw new CPException("can\'t receive from queue " + this.mName, var3);
      }
   }
}
