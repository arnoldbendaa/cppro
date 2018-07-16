// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:06:23
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.util.common;

import com.cedar.cp.api.base.CPException;
import com.cedar.cp.util.common.JmsConnectionImpl;
import com.cedar.cp.util.common.JmsConnectionImpl$AbstractJmsTopic;
import com.cedar.cp.util.common.JmsConnectionImpl$JmsConnectionObject;
import java.io.Serializable;
import javax.jms.JMSException;
import javax.jms.ObjectMessage;
import javax.jms.TopicPublisher;
import javax.naming.Context;

class JmsConnectionImpl$JmsTopicPublisher extends JmsConnectionImpl$AbstractJmsTopic implements JmsConnectionImpl$JmsConnectionObject {

   private TopicPublisher mTopicPublisher;
   // $FF: synthetic field
   final JmsConnectionImpl this$0;


   public JmsConnectionImpl$JmsTopicPublisher(JmsConnectionImpl var1, Context context, String name) throws CPException {
      super(var1, context, name);
      this.this$0 = var1;
   }

   public void createSession() throws CPException {
      try {
         super.createSession();
         this.mTopicPublisher = this.mTopicSession.createPublisher(this.mTopic);
      } catch (JMSException var2) {
         var2.printStackTrace();
         throw new CPException("can\'t create session", var2);
      }
   }

   public void send(Serializable o) throws CPException {
      ObjectMessage om = null;

      try {
         om = this.mTopicSession.createObjectMessage(o);
         om.setJMSDeliveryMode(1);
         this.mTopicPublisher.publish(om);
      } catch (Exception var4) {
         if(var4.getMessage().indexOf("reset by peer") == -1 && var4.getMessage().indexOf("Can not get connection to server") == -1) {
            var4.printStackTrace();
            throw new CPException("can\'t send to topic " + this.mName, var4);
         }

         JmsConnectionImpl.accessMethod000(this.this$0).info("send", var4.getMessage());
      }

   }
}
