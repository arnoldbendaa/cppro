// Decompiled by:       Fernflower v0.8.6
// Date:                11.08.2012 20:22:04
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.util;

import com.cedar.cp.util.CPInstallProperties$1;
import com.cedar.cp.util.CPInstallProperties$BooleanProperty;
import com.cedar.cp.util.CPInstallProperties$CPAdminPasswordProperty;
import com.cedar.cp.util.CPInstallProperties$CPDao;
import com.cedar.cp.util.CPInstallProperties$CPRuntimePasswordProperty;
import com.cedar.cp.util.CPInstallProperties$DirectoryNameProperty;
import com.cedar.cp.util.CPInstallProperties$DriverShim;
import com.cedar.cp.util.CPInstallProperties$E5PasswordProperty;
import com.cedar.cp.util.CPInstallProperties$E5ServiceProperty;
import com.cedar.cp.util.CPInstallProperties$EfinPasswordProperty;
import com.cedar.cp.util.CPInstallProperties$FileNameProperty;
import com.cedar.cp.util.CPInstallProperties$HostNameProperty;
import com.cedar.cp.util.CPInstallProperties$InstallProperty;
import com.cedar.cp.util.CPInstallProperties$NumberProperty;
import com.cedar.cp.util.CPInstallProperties$OAPasswordProperty;
import com.cedar.cp.util.CPInstallProperties$OraclePasswordProperty;
import com.cedar.cp.util.CPInstallProperties$OracleServiceProperty;
import com.cedar.cp.util.CPInstallProperties$SqlQueryList;
import com.cedar.cp.util.CPInstallProperties$StringProperty;
import com.cedar.cp.util.Log;
import com.cedar.cp.util.Timer;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.URL;
import java.net.URLClassLoader;
import java.net.UnknownHostException;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class CPInstallProperties {

   public static final String[] PROPERTY_GROUP_NAMES = new String[]{"app server", "mail", "cp database", "e5", "eFinancials", "OpenAccounts"};
   public static final int PROPERTY_GROUP_APPSERVER = 0;
   public static final int PROPERTY_GROUP_MAIL = 1;
   public static final int PROPERTY_GROUP_CP = 2;
   public static final int PROPERTY_GROUP_E5 = 3;
   public static final int PROPERTY_GROUP_EFIN = 4;
   public static final int PROPERTY_GROUP_OA = 5;
   public static final String CP_APPSERVER_TYPE = "appserver.type";
   public static final String CP_APPSERVER_HOST = "appserver.host";
   public static final String CP_APPSERVER_HOME = "appserver.home";
   public static final String CP_APPSERVER_MAX_MEM = "appserver.max.mem";
   public static final String CP_APPSERVER_SERVICE = "appserver.service";
   public static final String CP_APPSERVER_SERVICENAME = "appserver.serviceName";
   public static final String CP_APPSERVER_WEB_CONTEXT = "appserver.web.context";
   public static final String CP_APPSERVER_WEB_SECURE = "appserver.web.secure";
   public static final String CP_APPSERVER_SECURE_PASSWORD = "appserver.secure.password";
   public static final String CP_APPSERVER_SECURE_KEYSTORE = "appserver.secure.keystore";
   public static final String CP_APPSERVER_WEB_PORT = "appserver.web.port";
   public static final String CP_E5_LINK = "cp.e5.link";
   public static final String CP_EFIN_LINK = "cp.efin.link";
   public static final String CP_OA_LINK = "cp.oa.link";
   public static final String CP_MAIL_HOST = "cp.mail.host";
   public static final String CP_MAIL_USER = "cp.mail.user";
   public static final String CP_MAIL_PASSWORD = "cp.mail.password";
   public static final String CP_DB_SERVICE = "cp.db.service";
   public static final String CP_DB_HOST = "cp.db.host";
   public static final String CP_DB_PORT = "cp.db.port";
   public static final String CP_DB_SID = "cp.db.sid";
   public static final String CP_DB_ADMIN_USER = "cp.db.admin.user";
   public static final String CP_DB_ADMIN_PASSWORD = "cp.db.admin.password";
   public static final String CP_DB_RUNTIME_USER_REQUIRED = "cp.db.runtime.user.required";
   public static final String CP_DB_RUNTIME_USER = "cp.db.runtime.user";
   public static final String CP_DB_RUNTIME_PASSWORD = "cp.db.runtime.password";
   public static final String E5_DB_TYPE = "e5.db.type";
   public static final String E5_DB_SERVICE = "e5.db.service";
   public static final String E5_DB_USER = "e5.db.user";
   public static final String E5_DB_PASSWORD = "e5.db.password";
   public static final String E5_DB_SCHEMA = "e5.db.schema";
   public static final String E5_DB_URL = "e5.db.url";
   public static final String EFIN_DB_SERVICE = "efin.db.service";
   public static final String EFIN_DB_USER = "efin.db.user";
   public static final String EFIN_DB_PASSWORD = "efin.db.password";
   public static final String EFIN_DB_SCHEMA = "efin.db.schema";
   public static final String EFIN_DB_VALIDATE = "efin.db.validate";
   public static final String OA_DB_TYPE = "oa.db.type";
   public static final String OA_DB_USER = "oa.db.user";
   public static final String OA_DB_PASSWORD = "oa.db.password";
   public static final String OA_DB_URL = "oa.db.url";
   public static final String OA_DB_JDBC_DRIVER = "oa.db.jdbc.driver";
   public static final String OA_DB_PROGRESS_HOME = "oa.db.progress.home";
   private CPInstallProperties$CPDao mCpDao = null;
   private boolean mPropertiesValid = true;
   private boolean mIsNewInstall = false;
   private Map<String, CPInstallProperties$InstallProperty> mProps = new HashMap();
   private String mOSCommandOutput;
   private transient Log mLog = new Log(this.getClass());


   public void setPropertiesValid(boolean b) {
      this.mPropertiesValid = b;
   }

   public boolean isPropertiesValid() {
      return this.mPropertiesValid;
   }

   public void setIsNewInstall(boolean b) {
      this.mIsNewInstall = b;
   }

   public boolean isNewInstall() {
      return this.mIsNewInstall;
   }

   protected CPInstallProperties$CPDao getDao() {
      return this.mCpDao;
   }

   public void createProperties() {
      this.mProps.put("appserver.type", new CPInstallProperties$StringProperty(this, "type", new String[]{"jboss", "orion"}));
      this.mProps.put("appserver.host", new CPInstallProperties$HostNameProperty(this));
      this.mProps.put("appserver.home", new CPInstallProperties$DirectoryNameProperty(this, "home"));
      this.mProps.put("appserver.max.mem", new CPInstallProperties$NumberProperty(this, "max memory"));
      this.mProps.put("appserver.service", new CPInstallProperties$BooleanProperty(this, "service"));
      this.mProps.put("appserver.serviceName", new CPInstallProperties$StringProperty(this, "service name"));
      this.mProps.put("appserver.web.port", new CPInstallProperties$NumberProperty(this, "web.port"));
      this.mProps.put("appserver.web.context", new CPInstallProperties$StringProperty(this, "web context"));
      this.mProps.put("appserver.web.secure", new CPInstallProperties$BooleanProperty(this, "secure"));
      this.mProps.put("appserver.secure.keystore", new CPInstallProperties$FileNameProperty(this, "secure keystore"));
      this.mProps.put("appserver.secure.password", new CPInstallProperties$StringProperty(this, "secure password"));
      this.mProps.put("cp.e5.link", new CPInstallProperties$BooleanProperty(this, "e5 link"));
      this.mProps.put("cp.efin.link", new CPInstallProperties$BooleanProperty(this, "eFinancials link"));
      this.mProps.put("cp.oa.link", new CPInstallProperties$BooleanProperty(this, "OpenAcounts link"));
      this.mProps.put("cp.mail.host", new CPInstallProperties$HostNameProperty(this));
      this.mProps.put("cp.mail.user", new CPInstallProperties$StringProperty(this, "user"));
      this.mProps.put("cp.mail.password", new CPInstallProperties$StringProperty(this, "password"));
      this.mProps.put("cp.db.service", new CPInstallProperties$OracleServiceProperty(this));
      this.mProps.put("cp.db.host", new CPInstallProperties$HostNameProperty(this));
      this.mProps.put("cp.db.port", new CPInstallProperties$NumberProperty(this, "port"));
      this.mProps.put("cp.db.sid", new CPInstallProperties$StringProperty(this, "sid"));
      this.mProps.put("cp.db.admin.user", new CPInstallProperties$StringProperty(this, "admin user"));
      this.mProps.put("cp.db.admin.password", new CPInstallProperties$CPAdminPasswordProperty(this, "admin password"));
      this.mProps.put("cp.db.runtime.user.required", new CPInstallProperties$BooleanProperty(this, "runtime user required"));
      this.mProps.put("cp.db.runtime.user", new CPInstallProperties$StringProperty(this, "runtime user"));
      this.mProps.put("cp.db.runtime.password", new CPInstallProperties$CPRuntimePasswordProperty(this, "runtime password"));
      this.mProps.put("efin.db.service", new CPInstallProperties$OracleServiceProperty(this));
      this.mProps.put("efin.db.user", new CPInstallProperties$StringProperty(this, "user"));
      this.mProps.put("efin.db.password", new CPInstallProperties$EfinPasswordProperty(this, "password"));
      this.mProps.put("efin.db.schema", new CPInstallProperties$StringProperty(this, "schema"));
      this.mProps.put("efin.db.validate", new CPInstallProperties$StringProperty(this, "validate"));
      this.mProps.put("e5.db.type", new CPInstallProperties$StringProperty(this, "type", new String[]{"oracle", "db2"}));
      this.mProps.put("e5.db.service", new CPInstallProperties$E5ServiceProperty(this));
      this.mProps.put("e5.db.user", new CPInstallProperties$StringProperty(this, "user"));
      this.mProps.put("e5.db.password", new CPInstallProperties$E5PasswordProperty(this));
      this.mProps.put("e5.db.schema", new CPInstallProperties$StringProperty(this, "schema"));
      this.mProps.put("e5.db.url", new CPInstallProperties$StringProperty(this, "url"));
      this.mProps.put("oa.db.type", new CPInstallProperties$StringProperty(this, "type", new String[]{"openedge", "progress", "oracle", "sqlserver"}));
      this.mProps.put("oa.db.url", new CPInstallProperties$StringProperty(this, "url"));
      this.mProps.put("oa.db.jdbc.driver", new CPInstallProperties$StringProperty(this, "driver"));
      this.mProps.put("oa.db.user", new CPInstallProperties$StringProperty(this, "user"));
      this.mProps.put("oa.db.password", new CPInstallProperties$OAPasswordProperty(this));
      this.mProps.put("oa.db.progress.home", new CPInstallProperties$DirectoryNameProperty(this, "progress home"));
   }

   private URLClassLoader getClassLoader(String[] jarNameList, CPInstallProperties$InstallProperty ip) {
      String jarNameString = "";
      URL[] jarUrlList = new URL[jarNameList.length];
      boolean errorFound = false;

      for(int i = 0; i < jarNameList.length; ++i) {
         File f = null;
         if(this.getPropertyValue("appserver.type").equalsIgnoreCase("jboss")) {
            f = new File(this.getPropertyValue("appserver.home") + "/server/cp/lib/" + jarNameList[i]);
         } else {
            f = new File(this.getPropertyValue("appserver.home") + "/lib/" + jarNameList[i]);
         }

         if(f.exists() && f.isFile()) {
            try {
               jarUrlList[i] = new URL("file:///" + f.getCanonicalPath());
               jarNameString = jarNameString + (jarNameString.length() == 0?"":",") + jarNameList[i];
            } catch (IOException var9) {
               errorFound = true;
               var9.printStackTrace();
            }
         } else {
            errorFound = true;
            ip.addFeedback("jdbc jar " + jarNameList[i] + " not found");
         }
      }

      ip.addFeedback("jdbc jars: " + jarNameString);
      return errorFound?null:new URLClassLoader(jarUrlList);
   }

   private Driver getDriverClass(String driverName, URLClassLoader loader, CPInstallProperties$InstallProperty ip) {
      if(loader == null) {
         return null;
      } else {
         try {
            Driver e = (Driver)Class.forName(driverName, true, loader).newInstance();
            ip.addFeedback("loaded: " + driverName);
            return e;
         } catch (InstantiationException var5) {
            ip.addFeedback("instantiationException: " + driverName);
         } catch (IllegalAccessException var6) {
            ip.addFeedback("illegalAccessException: " + driverName);
         } catch (ClassNotFoundException var7) {
            ip.addFeedback("classNotFoundException: " + driverName);
         }

         return null;
      }
   }

   private InetAddress getHostInetAddress(String hostName) {
      if(hostName.startsWith("//")) {
         hostName = hostName.substring(2);
      }

      try {
         return InetAddress.getByName(hostName);
      } catch (UnknownHostException var3) {
         return null;
      }
   }

   private boolean isOracleVersionSupported(String version) {
      return version.startsWith("8.")?false:(version.startsWith("9.")?false:version.compareTo("10.2.0.4.") > 0);
   }

   private void getSqlAccessTime(CPInstallProperties$CPDao dao, String dbType, CPInstallProperties$SqlQueryList tableList, CPInstallProperties$InstallProperty ip) {
      Timer t = new Timer();
      int numTables = 0;

      for(int i = 0; i < tableList.getNumRows(); ++i) {
         tableList.setCurrentRow(i);
         if(dao.checkIfTableHasRows(dbType, (String)tableList.getColumn("TABLE_NAME"))) {
            ++numTables;
         }
      }

      ip.addFeedback("[" + t.getElapsed() + "] table timings (" + numTables + " tables)");
   }

   private void getSqlAccessTime(CPInstallProperties$CPDao dao, String dbType, String[] tableList, CPInstallProperties$InstallProperty ip) {
      Timer t = new Timer();
      int numTables = 0;
      String[] arr$ = tableList;
      int len$ = tableList.length;

      for(int i$ = 0; i$ < len$; ++i$) {
         String tableName = arr$[i$];
         if(dao.checkIfTableHasRows(dbType, tableName)) {
            ++numTables;
         }
      }

      ip.addFeedback("[" + t.getElapsed() + "] table timings (" + numTables + " tables)");
   }

   private Connection getJdbcConnection(String[] jars, String driverName, String url, String username, String password, CPInstallProperties$InstallProperty ip) {
      Connection conn = null;

      try {
         URLClassLoader ucl = this.getClassLoader(jars, ip);
         Driver e = this.getDriverClass(driverName, ucl, ip);
         if(e != null) {
            DriverManager.registerDriver(new CPInstallProperties$DriverShim(this, e));
            conn = DriverManager.getConnection(url, username, password);
            ip.addFeedback("jdbc: connected");
         }
      } catch (SQLException var10) {
         if(var10.getErrorCode() == 1017) {
            ip.addFeedback("jdbc: not connected: invalid username/password: " + username + "/" + "password");
         } else if(var10.getErrorCode() == 12505) {
            ip.addFeedback("jdbc: not connected: sid not found");
         } else {
            ip.addFeedback("jdbc: not connected: " + (var10.getErrorCode() != 0?"sqlCode=" + var10.getErrorCode() + " ":"") + var10.getMessage());
         }

         conn = null;
      }

      return conn;
   }

   public void checkSchema(CPInstallProperties$OraclePasswordProperty ip, String password, String tableName) {
      String userName = this.getPropertyValue(ip.mUserNameProperty);
      String serviceName = this.getPropertyValue(ip.mServiceNameProperty);
      String schemaName = this.getPropertyValue(ip.mSchemaNameProperty);

      try {
         PrintWriter stdout = new PrintWriter(new FileWriter("temp.sql"));
         stdout.println("whenever sqlerror exit failure");
         stdout.println("set head off");
         stdout.println("select count(*) from " + schemaName + "." + tableName + ";");
         stdout.println("exit");
         stdout.flush();
         stdout.close();
      } catch (IOException var10) {
         var10.printStackTrace();
         return;
      }

      String stdout1 = this.runOSCommand(new String[]{"sqlplus", "-L", userName + "/" + password + "@" + serviceName, "@temp.sql"}, ip);
      if(stdout1 == null) {
         this.setPropertiesValid(false);
      } else {
         Scanner scanner = new Scanner(stdout1);

         String token;
         do {
            if(!scanner.hasNext()) {
               ip.addFeedback("schema <" + schemaName + "> checked");
               return;
            }

            token = scanner.next();
            if("ORA-00942:".equals(token)) {
               ip.addFeedback("invalid schema name - " + schemaName + "." + tableName + " not found");
               this.setPropertiesValid(false);
               return;
            }

            if("ORA-00903".equals(token)) {
               ip.addFeedback("invalid schema name - " + schemaName);
               this.setPropertiesValid(false);
               return;
            }

            if("ORA-01017:".equals(token)) {
               ip.addFeedback("sqlplus: invalid username/password");
               this.setPropertiesValid(false);
               return;
            }
         } while(!"timed out".equals(token));

         ip.addFeedback("sqlplus: timed out");
         this.setPropertiesValid(false);
      }
   }

   public CPInstallProperties$CPDao getAppServerDAO() {
      this.mCpDao = new CPInstallProperties$CPDao(this);
      return this.mCpDao;
   }

   private String runOSCommand(String[] cmd, CPInstallProperties$InstallProperty ip) {
      CPInstallProperties$1 r = new CPInstallProperties$1(this, cmd, ip);
      r.start();
      int i = 0;

      while(true) {
         try {
            if(i == 0) {
               r.join(5000L);
            } else {
               r.join(1000L);
            }
         } catch (InterruptedException var7) {
            var7.printStackTrace();
         }

         if(!r.isAlive()) {
            return this.mOSCommandOutput;
         }

         if(i == 10) {
            r.interrupt();
            this.mOSCommandOutput = this.mOSCommandOutput + " timed out";
         }

         ++i;
      }
   }

   protected CPInstallProperties$InstallProperty getProperty(String name) {
      return (CPInstallProperties$InstallProperty)this.mProps.get(name);
   }

   protected String getPropertyValue(String name) {
      return this.getProperty(name).getValue();
   }

   protected void removeProperty(String name) {
      this.mProps.remove(name);
   }

   protected Map<String, CPInstallProperties$InstallProperty> getProperties() {
      return this.mProps;
   }

   private String rpad(String s, int size) {
      StringBuffer sb = new StringBuffer(size);
      sb.append(s);

      while(sb.length() < size) {
         sb.append(' ');
      }

      return sb.toString();
   }

   private String fill(char fillChar, int len) {
      StringBuffer sb = new StringBuffer(len);

      for(int i = 0; i < len; ++i) {
         sb.append(fillChar);
      }

      return sb.toString();
   }

   // $FF: synthetic method
   static InetAddress accessMethod000(CPInstallProperties x0, String x1) {
      return x0.getHostInetAddress(x1);
   }

   // $FF: synthetic method
   static String accessMethod100(CPInstallProperties x0, String[] x1, CPInstallProperties$InstallProperty x2) {
      return x0.runOSCommand(x1, x2);
   }

   // $FF: synthetic method
   static boolean accessMethod200(CPInstallProperties x0, String x1) {
      return x0.isOracleVersionSupported(x1);
   }

   // $FF: synthetic method
   static Connection accessMethod300(CPInstallProperties x0, String[] x1, String x2, String x3, String x4, String x5, CPInstallProperties$InstallProperty x6) {
      return x0.getJdbcConnection(x1, x2, x3, x4, x5, x6);
   }

   // $FF: synthetic method
   static CPInstallProperties$CPDao accessMethod402(CPInstallProperties x0, CPInstallProperties$CPDao x1) {
      return x0.mCpDao = x1;
   }

   // $FF: synthetic method
   static CPInstallProperties$CPDao accessMethod400(CPInstallProperties x0) {
      return x0.mCpDao;
   }

   // $FF: synthetic method
   static void accessMethod500(CPInstallProperties x0, CPInstallProperties$CPDao x1, String x2, String[] x3, CPInstallProperties$InstallProperty x4) {
      x0.getSqlAccessTime(x1, x2, x3, x4);
   }

   // $FF: synthetic method
   static String accessMethod600(CPInstallProperties x0, String x1, int x2) {
      return x0.rpad(x1, x2);
   }

   // $FF: synthetic method
   static Map accessMethod700(CPInstallProperties x0) {
      return x0.mProps;
   }

   // $FF: synthetic method
   static void accessMethod800(CPInstallProperties x0, CPInstallProperties$CPDao x1, String x2, CPInstallProperties$SqlQueryList x3, CPInstallProperties$InstallProperty x4) {
      x0.getSqlAccessTime(x1, x2, x3, x4);
   }

   // $FF: synthetic method
   static Log accessMethod900(CPInstallProperties x0) {
      return x0.mLog;
   }

   // $FF: synthetic method
   static String accessMethod1002(CPInstallProperties x0, String x1) {
      return x0.mOSCommandOutput = x1;
   }

}
