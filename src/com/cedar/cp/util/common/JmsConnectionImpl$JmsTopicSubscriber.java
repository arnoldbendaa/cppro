// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:06:23
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.util.common;

import com.cedar.cp.api.base.CPException;
import com.cedar.cp.util.common.JmsConnectionImpl;
import com.cedar.cp.util.common.JmsConnectionImpl$AbstractJmsTopic;
import com.cedar.cp.util.common.JmsConnectionImpl$JmsConnectionObject;
import javax.jms.JMSException;
import javax.jms.ObjectMessage;
import javax.jms.TopicSubscriber;
import javax.naming.Context;

class JmsConnectionImpl$JmsTopicSubscriber extends JmsConnectionImpl$AbstractJmsTopic implements JmsConnectionImpl$JmsConnectionObject {

   TopicSubscriber mTopicSubscriber;
   // $FF: synthetic field
   final JmsConnectionImpl this$0;


   public JmsConnectionImpl$JmsTopicSubscriber(JmsConnectionImpl var1, Context context, String name) throws CPException {
      super(var1, context, name);
      this.this$0 = var1;
   }

   public void createSession() throws CPException {
      try {
         super.createSession();
         this.mTopicSubscriber = this.mTopicSession.createSubscriber(this.mTopic);
         this.mTopicConnection.start();
      } catch (JMSException var2) {
         var2.printStackTrace();
         throw new CPException("can\'t create session", var2);
      }
   }

   public Object receive() throws CPException {
      ObjectMessage om = null;

      try {
         om = (ObjectMessage)this.mTopicSubscriber.receive();
         if(om != null) {
            JmsConnectionImpl.accessMethod000(this.this$0).debug("receive", om.getObject().toString());
            return om.getObject();
         } else {
            return null;
         }
      } catch (JMSException var3) {
         if(var3.getCause() != null && var3.getCause() instanceof InterruptedException) {
            throw new CPException("receive stopped by InterruptedException", var3);
         } else if(var3.getMessage() != null && var3.getMessage().indexOf("Interrupt") > -1) {
            throw new CPException("receive stopped by InterruptedException", var3);
         } else {
            var3.printStackTrace();
            throw new CPException("can\'t receive from topic " + this.mName, var3);
         }
      }
   }
}
