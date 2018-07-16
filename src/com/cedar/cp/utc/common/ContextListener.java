// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:35:00
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.utc.common;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Enumeration;

import javax.jms.JMSException;
import javax.jms.ObjectMessage;
import javax.jms.Queue;
import javax.jms.QueueConnection;
import javax.jms.QueueConnectionFactory;
import javax.jms.QueueSender;
import javax.jms.QueueSession;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import com.cedar.cp.api.extsys.ExtSysConnectorManagerAccessor;
import com.cedar.cp.util.Log;
import com.cedar.cp.util.db.DBAccessor;

public class ContextListener implements ServletContextListener {
	
   protected final Log mLog = new Log(this.getClass());


   public void contextInitialized(ServletContextEvent event) {
      Enumeration names = event.getServletContext().getInitParameterNames();

      while(names.hasMoreElements()) {
         String systemProperties = names.nextElement().toString();
         String e = event.getServletContext().getInitParameter(systemProperties);
         this.mLog.debug("Context param " + systemProperties + " is set to " + e);
      }

      CPSystemProperties systemProperties1 = new CPSystemProperties();
      event.getServletContext().setAttribute("cpSystemProperties", systemProperties1);
      this.initExternalSystemConnectors();

      try {
         InitialContext e1 = new InitialContext();
         Queue mQueue = (Queue)e1.lookup("jms/cp/taskDespatcherQueue");
         QueueConnectionFactory mQueueConnectionFactory = (QueueConnectionFactory)e1.lookup("jms/cp/ConnectionFactory");
         QueueConnection mQueueConnection = mQueueConnectionFactory.createQueueConnection();
         QueueSession mQueueSession = mQueueConnection.createQueueSession(false, 1);
         QueueSender mQueueSender = mQueueSession.createSender(mQueue);
         ObjectMessage om = mQueueSession.createObjectMessage(this.getClass().getName());
         mQueueSender.send(om);
         mQueueSession.close();
         mQueueConnection.close();
         this.mLog.debug("contextInitialized", "sent wake-up call to task despatcher");
      } catch (JMSException var11) {
         var11.printStackTrace();
      } catch (NamingException var12) {
         var12.printStackTrace();
      }

      System.getProperties().put("java.awt.headless", "true");
   }

   public void contextDestroyed(ServletContextEvent event) {
      this.destroyExternalSystemConnectors();
   }

   private void initExternalSystemConnectors() {
      ExtSysConnectorDBHelper helper = new ExtSysConnectorDBHelper();

      try {
         TableModel tm = helper.getExtSysConnectors();
         ExtSysConnectorManagerAccessor.getExtSysConnectorManager().startUp(tm);
      } finally {
         helper.close();
      }

   }

   private void destroyExternalSystemConnectors() {
      ExtSysConnectorManagerAccessor.getExtSysConnectorManager().shutdown();
   }
}

class ExtSysConnectorDBHelper extends DBAccessor {

	private TableModel mExtSysConnectors;

	public ExtSysConnectorDBHelper() {
		this.queryExternalSystemProperties();
	}

	private void queryExternalSystemProperties() {
		StringBuilder sb = new StringBuilder();
		sb.append("select external_system_id, es.vis_id, es.description, es.connector_class, \n");
		sb.append("       esp.property_name, esp.property_value \n");
		sb.append("from external_system es \n");
		sb.append("left join ext_sys_property esp using (external_system_id) \n");
		sb.append("where es.system_type = 20 \n");
		sb.append("  and es.enabled = \'Y\' \n");
		sb.append("  and trim(es.connector_class) is not null \n");
		sb.append("order by external_system_id, esp.property_name");
		this.executeQuery(sb.toString());
	}

	protected void processResultSet(ResultSet rs) throws SQLException {
		ArrayList rows = new ArrayList();

		while (rs.next()) {
			int result = rs.getInt("external_system_id");
			String visId = rs.getString("vis_id");
			String description = rs.getString("description");
			String connectorClass = rs.getString("connector_class");
			String property_name = rs.getString("property_name");
			String property_value = rs.getString("property_value");
			rows.add(new Object[] { Integer.valueOf(result), visId,
					description, connectorClass, property_name, property_value });
		}

		DefaultTableModel result1 = new DefaultTableModel(
				(Object[][]) rows.toArray(new Object[0][]), new String[] {
						"external_system_id", "vis_id", "description",
						"connector_class", "property_name", "property_value" });
		this.mExtSysConnectors = result1;
	}

	public TableModel getExtSysConnectors() {
		return this.mExtSysConnectors;
	}
}