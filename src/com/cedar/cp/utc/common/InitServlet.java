// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:35:00
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.utc.common;

import com.cedar.cp.api.base.CPException;
import com.cedar.cp.utc.common.InitServlet$1;
import com.cedar.cp.util.Log;
import com.cedar.cp.util.task.NewTaskMessage;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.StringTokenizer;
import javax.jms.JMSException;
import javax.jms.ObjectMessage;
import javax.jms.Queue;
import javax.jms.QueueConnection;
import javax.jms.QueueConnectionFactory;
import javax.jms.QueueSender;
import javax.jms.QueueSession;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.omg.CORBA.ORB;

public class InitServlet extends HttpServlet {

   private static Properties sServerProps = new Properties();
   protected Log mLog = new Log(this.getClass());


   public void init() throws ServletException {
      super.init();
   }

   protected void doGet(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws ServletException, IOException {
      String userIdStr = httpServletRequest.getParameter("userId");
      String taskIdStr = httpServletRequest.getParameter("taskId");
      String originalSendTimeStr = httpServletRequest.getParameter("originalSendTime");
      String waitTimeStr = httpServletRequest.getParameter("waitTime");
      String queueJNDIName = httpServletRequest.getParameter("queueJNDIName");
      String webConnectionUrl = httpServletRequest.getParameter("webConnectionUrl");
      if(userIdStr == null) {
         throw new ServletException("Parameter userId must be supplied");
      } else if(taskIdStr == null) {
         throw new ServletException("Parameter taskId must be supplied");
      } else if(originalSendTimeStr == null) {
         throw new ServletException("Parameter originalSendTime must be supplied");
      } else if(waitTimeStr == null) {
         throw new ServletException("Parameter waitTime must be supplied");
      } else if(queueJNDIName == null) {
         throw new ServletException("Parameter queueJNDIName must be supplied");
      } else if(webConnectionUrl == null) {
         throw new ServletException("Parameter webConnectionUrl must be supplied");
      } else {
         String serverStr = "localhost";
         String applicationServer = "orion";
         String portStr = "23791";
         StringTokenizer st = new StringTokenizer(webConnectionUrl, ":", false);

         for(int server = 0; st.hasMoreTokens(); ++server) {
            String port = st.nextToken();
            if(server == 2) {
               serverStr = port;
            } else if(server == 3) {
               portStr = port;
            } else if(server == 1) {
               applicationServer = port;
            }
         }

         int var22 = Integer.parseInt(portStr);
         int userId = Integer.parseInt(userIdStr);
         int taskId = Integer.parseInt(taskIdStr);
         int waitTime = Integer.parseInt(waitTimeStr);
         long originalSendTime = Long.parseLong(originalSendTimeStr);
         InitServlet$1 t = new InitServlet$1(this, userId, taskId, waitTime, originalSendTime, queueJNDIName, applicationServer, serverStr, var22);
         t.start();
      }
   }

   private void resendTaskMgrJMSMessage(int userId, int taskId, int waitTime, long originalSendTime, String queueJNDIName, String appServer, String server, int port) {
      try {
         Thread.sleep((long)waitTime);
      } catch (InterruptedException var20) {
         var20.printStackTrace();
      }

      try {
         InitialContext jmse = this.createInitialContext(appServer, server, port);
         Queue queue = (Queue)jmse.lookup(queueJNDIName);
         QueueConnectionFactory factory = (QueueConnectionFactory)jmse.lookup("jms/cp/ConnectionFactory");
         QueueConnection queueConnection = factory.createQueueConnection();
         QueueSession queueSession = queueConnection.createQueueSession(false, 1);
         QueueSender queueSender = queueSession.createSender(queue);
         NewTaskMessage ntm = new NewTaskMessage(taskId, userId, originalSendTime);
         ObjectMessage om = queueSession.createObjectMessage(ntm);
         queueSender.send(om);
         queueSender.close();
         queueSession.close();
         queueConnection.close();
      } catch (NamingException var18) {
         var18.printStackTrace();
         throw new CPException("Unable to send TaskMgr JMS retry message ", var18);
      } catch (JMSException var19) {
         var19.printStackTrace();
         throw new CPException("Unable to send TaskMgr JMS retry message ", var19);
      }
   }

   private InitialContext createInitialContext(String appServer, String server, int port) throws NamingException {
      String authenticationType = "simple";
      String location = server + ":" + port;
      Properties env = new Properties();
      String contextAPI = this.queryInitialContextFactor(appServer);
      if(contextAPI == null) {
         throw new CPException("Unable to determine InitailContextFactory for appServer:" + appServer);
      } else {
         if(contextAPI.compareTo("J2EE") != 0) {
            env.put("java.naming.factory.initial", contextAPI);
         }

         if(contextAPI.startsWith("org.jnp.interfaces")) {
            env.put("java.naming.security.principal", "admin");
            env.put("java.naming.security.credentials", "password");
            env.put("java.naming.provider.url", location);
         } else if(contextAPI.startsWith("com.evermind")) {
            location = "ormi://" + location + "/cp";
            env.put("java.naming.security.principal", "admin");
            env.put("java.naming.security.credentials", "password");
            env.put("java.naming.provider.url", location);
         } else if(contextAPI.startsWith("com.ibm.websphere")) {
            location = "iiop://" + server + ":" + port;

            try {
               Properties e = new Properties();
               Object args = null;
               e.put("org.omg.CORBA.ORBClass", "com.ibm.rmi.iiop.ORB");
               e.put("com.ibm.CORBA.BootstrapHost", server);
               e.put("com.ibm.CORBA.requestTimeout", "30");
               ORB.init((String[])args, e);
               env.put("java.naming.security.principal", "admin");
               env.put("java.naming.security.credentials", "password");
               env.put("java.naming.provider.url", location);
            } catch (Exception var10) {
               this.mLog.error("EXCEPTION msg = " + var10.getMessage(), var10);
               throw new CPException(var10.getMessage());
            }
         }

         env.put("java.naming.security.authentication", authenticationType);
         return new InitialContext(env);
      }
   }

   private String queryInitialContextFactor(String appServer) {
      Properties var2 = sServerProps;
      synchronized(sServerProps) {
         if(sServerProps.isEmpty()) {
            try {
               InputStream e = this.getClass().getResourceAsStream("initialContextFactory.properties");
               if(e == null) {
                  throw new CPException("Unable to load initialContextFactory.properties");
               }

               sServerProps.load(e);
            } catch (IOException var5) {
               throw new CPException("Failed to load appServer.properties", var5);
            }
         }

         return sServerProps.getProperty(appServer);
      }
   }

   // $FF: synthetic method
   static void access$000(InitServlet x0, int x1, int x2, int x3, long x4, String x5, String x6, String x7, int x8) {
      x0.resendTaskMgrJMSMessage(x1, x2, x3, x4, x5, x6, x7, x8);
   }

}
