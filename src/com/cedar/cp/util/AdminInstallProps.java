// Decompiled by:       Fernflower v0.8.6
// Date:                11.08.2012 20:22:03
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.util;

import com.cedar.cp.util.CPInstallProperties;
import com.cedar.cp.util.CPInstallProperties$CPDao;
import com.cedar.cp.util.CPInstallProperties$InstallProperty;
import com.cedar.cp.util.CPInstallProperties$SqlQueryList;
import com.cedar.cp.util.Log;
import java.util.Iterator;

public class AdminInstallProps {

   private CPInstallProperties mCpIp = new CPInstallProperties();
   private int mTaskId;
   private transient Log mLog = new Log(this.getClass());


   public void run() {
      this.loadInstallProps();
      this.maintainProps();
   }

   public void setTaskId(Integer taskId) {
      this.mTaskId = taskId.intValue();
   }

   public int getTaskId() {
      return this.mTaskId;
   }

   private CPInstallProperties$CPDao getDao() {
      return this.mCpIp.getAppServerDAO();
   }

   private void logEvent(String text) {
      this.getDao().logEvent(this.getTaskId(), 1, text);
   }

   private void maintainProps() {
      int i = 0;

      while(true) {
         CPInstallProperties var10001 = this.mCpIp;
         if(i >= CPInstallProperties.PROPERTY_GROUP_NAMES.length) {
            return;
         }

         switch(i) {
         case 0:
            this.checkAppServer();
            break;
         case 1:
            this.checkMail();
            break;
         case 2:
            this.checkCPDatabase();
            break;
         case 3:
            this.checkE5Database();
            break;
         case 4:
            this.checkEFinancialsDatabase();
            break;
         case 5:
            this.checkOpenAccountsDatabase();
         }

         ++i;
      }
   }

   private void doGroupHeading(int groupIndex) {
      this.logEvent(" ");
      this.logEvent(this.fill('-', CPInstallProperties.PROPERTY_GROUP_NAMES[groupIndex].length()));
      this.logEvent(CPInstallProperties.PROPERTY_GROUP_NAMES[groupIndex]);
      this.logEvent(this.fill('-', CPInstallProperties.PROPERTY_GROUP_NAMES[groupIndex].length()));
   }

   private void checkAppServer() {
      this.doGroupHeading(0);
      this.doProperty("appserver.type");
      this.doProperty("appserver.host");
      this.doProperty("appserver.home");
      this.doProperty("appserver.max.mem");
      this.doProperty("appserver.service");
      if("true".equals(this.getPropertyValue("appserver.service"))) {
         this.doProperty("appserver.serviceName");
      }

      this.doProperty("appserver.web.context");
      this.doProperty("appserver.web.secure");
      if("true".equals(this.getPropertyValue("appserver.web.secure"))) {
         this.doProperty("appserver.secure.keystore");
         this.doProperty("appserver.secure.password");
      }

      this.doProperty("appserver.web.port");
      this.doProperty("cp.e5.link");
      this.doProperty("cp.efin.link");
      this.doProperty("cp.oa.link");
   }

   private void doProperty(String propertyName) {
      CPInstallProperties$InstallProperty ip = this.getProperty(propertyName);
      if(ip == null) {
         this.logEvent(this.fill(' ', 2) + propertyName + " not found");
      } else {
         this.logEvent(this.fill(' ', 2) + ip.getPromptText() + "=" + ip.getValue());
         ip.checkProperty(ip.getValue());
         Iterator i$ = ip.getFeedback().iterator();

         while(i$.hasNext()) {
            String s = (String)i$.next();
            this.logEvent(this.fill(' ', 4) + s);
         }

      }
   }

   private void checkMail() {
      this.doGroupHeading(1);
      this.doProperty("cp.mail.host");
      this.doProperty("cp.mail.user");
      this.doProperty("cp.mail.password");
   }

   private void checkCPDatabase() {
      this.doGroupHeading(2);
      this.doProperty("cp.db.service");
      this.doProperty("cp.db.host");
      this.doProperty("cp.db.port");
      this.doProperty("cp.db.sid");
      this.doProperty("cp.db.admin.user");
      this.doProperty("cp.db.admin.password");
      this.doProperty("cp.db.runtime.user.required");
      if(this.getPropertyValue("cp.db.runtime.user.required").equalsIgnoreCase("true")) {
         this.doProperty("cp.db.runtime.user");
         this.doProperty("cp.db.runtime.password");
      }

   }

   private void checkE5Database() {
      if(!"false".equalsIgnoreCase(this.getPropertyValue("cp.e5.link"))) {
         this.doGroupHeading(3);
         this.doProperty("e5.db.type");
         this.doProperty("e5.db.service");
         if(this.getPropertyValue("e5.db.type").equalsIgnoreCase("e5")) {
            this.doProperty("e5.db.url");
         }

         this.doProperty("e5.db.schema");
         this.doProperty("e5.db.user");
         this.doProperty("e5.db.password");
      }
   }

   private void checkEFinancialsDatabase() {
      if(!"false".equalsIgnoreCase(this.getPropertyValue("cp.efin.link"))) {
         this.doGroupHeading(4);
         this.doProperty("efin.db.service");
         this.doProperty("efin.db.schema");
         this.doProperty("efin.db.user");
         this.doProperty("efin.db.password");
      }
   }

   private void checkOpenAccountsDatabase() {
      if(!"false".equalsIgnoreCase(this.getPropertyValue("cp.oa.link"))) {
         this.doGroupHeading(5);
         this.doProperty("oa.db.type");
         this.doProperty("oa.db.url");
         if(this.getPropertyValue("oa.db.type").equalsIgnoreCase("progress")) {
            this.doProperty("oa.db.progress.home");
         }

         this.doProperty("oa.db.user");
         this.doProperty("oa.db.password");
      }
   }

   private void loadInstallProps() {
      this.mCpIp.createProperties();
      CPInstallProperties$SqlQueryList sqlq = this.getDao().loadProperties();

      for(int i = 0; i < sqlq.getNumRows(); ++i) {
         sqlq.setCurrentRow(i);
         CPInstallProperties$InstallProperty ip = this.getProperty((String)sqlq.getColumn("NAME"));
         if(ip == null) {
            this.mLog.debug("can\'t find " + sqlq.getColumn("NAME"));
         } else {
            ip.setValue((String)sqlq.getColumn("VALUE"));
         }
      }

   }

   private CPInstallProperties$InstallProperty getProperty(String name) {
      return this.mCpIp.getProperty(name);
   }

   private String getPropertyValue(String name) {
      return this.mCpIp.getPropertyValue(name);
   }

   private String fill(char fillChar, int len) {
      StringBuffer sb = new StringBuffer(len);

      for(int i = 0; i < len; ++i) {
         sb.append(fillChar);
      }

      return sb.toString();
   }
}
