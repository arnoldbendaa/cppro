// Decompiled by:       Fernflower v0.8.6
// Date:                11.08.2012 20:22:03
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.util;

import com.cedar.cp.util.CPInstallProperties;
import com.cedar.cp.util.CPInstallProperties$InstallProperty;
import com.cedar.cp.util.CPInstallProperties$SqlQueryList;
import com.cedar.cp.util.SqlBuilder;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Iterator;
import java.util.Map;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class CPInstallProperties$CPDao {

   protected transient DataSource mDataSource;
   protected transient Connection mConnection;
   protected transient long mTimer;
   // $FF: synthetic field
   final CPInstallProperties this$0;


   public CPInstallProperties$CPDao(CPInstallProperties var1) {
      this.this$0 = var1;
      String lookupName = "java:comp/env/jdbc/fc";

      try {
         InitialContext ne = new InitialContext();
         this.mDataSource = (DataSource)ne.lookup(lookupName);
      } catch (NamingException var4) {
         throw new RuntimeException("error looking up DataSource " + lookupName + ": " + var4.getMessage(), var4);
      }
   }

   public CPInstallProperties$CPDao(CPInstallProperties var1, Connection connection) {
      this.this$0 = var1;
      this.mConnection = connection;
      this.mDataSource = null;
   }

   public CPInstallProperties$CPDao(CPInstallProperties var1, String lookupName) {
      this.this$0 = var1;
      this.mDataSource = null;

      try {
         InitialContext ne = new InitialContext();
         this.mDataSource = (DataSource)ne.lookup(lookupName);
      } catch (NamingException var4) {
         throw new RuntimeException("error looking up DataSource " + lookupName + ": " + var4.getMessage(), var4);
      }
   }

   public DataSource getDataSource() {
      return this.mDataSource;
   }

   public Connection getConnection() {
      if(this.mConnection != null) {
         return this.mConnection;
      } else {
         try {
            if(this.mDataSource != null) {
               this.mConnection = this.mDataSource.getConnection();
            }
         } catch (SQLException var2) {
            throw new RuntimeException("SQL Exception while getting DB connection", var2);
         }

         return this.mConnection;
      }
   }

   protected void closeConnection() {
      try {
         if(this.mConnection != null && !this.mConnection.isClosed() && this.mDataSource != null) {
            this.mConnection.close();
            this.mConnection = null;
         }
      } catch (Exception var2) {
         System.err.println("Exception when closing connection " + var2.getMessage());
      }

   }

   protected void closeResultSet(ResultSet result) {
      try {
         if(result != null) {
            result.close();
         }
      } catch (Exception var3) {
         System.err.println("Exception when closing result set " + var3.getMessage());
      }

   }

   protected void closeStatement(Statement stmt) {
      try {
         if(stmt != null) {
            stmt.close();
         }
      } catch (Exception var3) {
         System.err.println("Exception when closing statement " + var3.getMessage());
      }

   }

   protected CPInstallProperties$SqlQueryList query(SqlBuilder sqlb) {
      CPInstallProperties$SqlQueryList results = new CPInstallProperties$SqlQueryList(this.this$0);
      ResultSet resultSet = null;
      PreparedStatement stat = null;

      try {
         stat = this.getConnection().prepareStatement(sqlb.toString());
         resultSet = stat.executeQuery();
         String[] e = new String[resultSet.getMetaData().getColumnCount()];

         int i;
         for(i = 0; i < e.length; ++i) {
            e[i] = resultSet.getMetaData().getColumnName(i + 1);
         }

         results.setColumnNames(e);

         while(resultSet != null && resultSet.next()) {
            results.addRow();

            for(i = 0; i < e.length; ++i) {
               results.addColumn(i, resultSet.getObject(i + 1));
            }
         }

         if(results.getNumRows() > 0) {
            results.setCurrentRow(0);
         }
      } catch (SQLException var10) {
         results.setSqlCode(var10.getErrorCode());
         results.setSqlMessage(var10.getMessage());
      } finally {
         this.closeResultSet(resultSet);
         this.closeStatement(stat);
         this.closeConnection();
      }

      return results;
   }

   protected boolean checkCPToFmsLink(String fms, String link, String userName, String password, String serviceName, String dbType, CPInstallProperties$InstallProperty ip) {
      SqlBuilder sqlb = new SqlBuilder(new String[]{"drop database link ${link}"});
      sqlb.substitute(new String[]{"${link}", link.toUpperCase()});
      PreparedStatement stat = null;

      try {
         stat = this.getConnection().prepareStatement(sqlb.toString());
         stat.execute();
      } catch (SQLException var21) {
         ;
      } finally {
         this.closeStatement(stat);
         this.closeConnection();
      }

      sqlb = new SqlBuilder(new String[]{"create database link ${link}", "    connect to ${user} identified by ${password} using \'${service}\'"});
      sqlb.substitute(new String[]{"${link}", link.toUpperCase()});
      sqlb.substitute(new String[]{"${user}", userName});
      sqlb.substitute(new String[]{"${password}", password});
      sqlb.substitute(new String[]{"${service}", serviceName});
      stat = null;

      label135: {
         boolean var11;
         try {
            stat = this.getConnection().prepareStatement(sqlb.toString());
            stat.execute();
            break label135;
         } catch (SQLException var23) {
            ip.addFeedback("unable to create link <" + link + ">:");
            ip.addFeedback("    " + var23.getMessage());
            var11 = false;
         } finally {
            this.closeStatement(stat);
            this.closeConnection();
         }

         return var11;
      }

      ip.addFeedback("created link <" + link + "> from CP to " + fms);
      sqlb = new SqlBuilder(new String[]{"select  1 from ${dummyTable}@${linkName}"});
      sqlb.substitute(new String[]{"${linkName}", link});
      if("e5database".equalsIgnoreCase(link) && "db2".equalsIgnoreCase(this.this$0.getPropertyValue("e5.db.type"))) {
         sqlb.substitute(new String[]{"${dummyTable}", "SYSIBM.SYSDUMMY"});
      } else {
         sqlb.substitute(new String[]{"${dummyTable}", "dual"});
      }

      CPInstallProperties$SqlQueryList sqlq = this.query(sqlb);
      if(sqlq.getSqlCode() != 0) {
         ip.addFeedback(fms + " link <" + link + "> " + sqlb.toString() + sqlq.getSqlMessage());
         return false;
      } else {
         CPInstallProperties.accessMethod800(this.this$0, CPInstallProperties.accessMethod400(this.this$0), dbType, CPInstallProperties.accessMethod400(this.this$0).getLinkedTables(link), ip);
         return true;
      }
   }

   protected void checkFmsLinkToCP(String fms, String viaLink, String linkBack, String localExportList, String remoteExportList, CPInstallProperties$InstallProperty ip) {
      ip.addFeedback(fms + ": checking links back to CP");
      SqlBuilder sqlb = new SqlBuilder(new String[]{"select  USERNAME, HOST", "from    USER_DB_LINKS@${viaLink}", "where   DB_LINK = \'${linkBack}\'", "or      DB_LINK like \'${linkBack}.%\'"});
      sqlb.substitute(new String[]{"${viaLink}", viaLink.toUpperCase()});
      sqlb.substitute(new String[]{"${linkBack}", linkBack.toUpperCase()});
      CPInstallProperties$SqlQueryList sqlq = this.query(sqlb);
      if(sqlq.getSqlCode() == 12545) {
         ip.addFeedback("    can\'t check link <" + linkBack + "> - no permission to access USER_DB_LINKS");
      } else if(sqlq.getSqlCode() != 0) {
         ip.addFeedback("    can\'t check link <" + linkBack + ">: " + sqlb.toString() + " - " + sqlq.getSqlMessage());
      } else {
         if(sqlq.getNumRows() == 0) {
            ip.addFeedback("    link <" + linkBack + "> not found");
            return;
         }

         String remoteList = (String)sqlq.getColumn("USERNAME");
         String localList = (String)sqlq.getColumn("HOST");
         if(!remoteList.equalsIgnoreCase(this.this$0.getPropertyValue("cp.db.admin.user"))) {
            ip.addFeedback("    link <" + linkBack + "> points to different user <" + remoteList + ">");
            return;
         }

         if(!localList.equalsIgnoreCase(this.this$0.getPropertyValue("cp.db.service"))) {
            ip.addFeedback("    link <" + linkBack + "> points to different service <" + localList + ">");
            return;
         }

         ip.addFeedback("    link <" + linkBack + "> points to current CP instance");
      }

      sqlb = new SqlBuilder(new String[]{"select TABLE_OWNER, TABLE_NAME from USER_SYNONYMS@${viaLink}", "where  SYNONYM_NAME = \'${remoteExportList}\'"});
      sqlb.substitute(new String[]{"${viaLink}", viaLink.toUpperCase()});
      sqlb.substitute(new String[]{"${remoteExportList}", remoteExportList.toUpperCase()});
      sqlq = this.query(sqlb);
      if(sqlq.getSqlCode() == 12545) {
         ip.addFeedback("    can\'t check synonym <" + remoteExportList + "> - no permission to access USER_SYNONYMS");
      } else if(sqlq.getSqlCode() != 0) {
         ip.addFeedback("    can\'t check synonym <" + remoteExportList + ">: " + sqlb.toString() + " - " + sqlq.getSqlMessage());
      } else if(sqlq.getNumRows() == 0) {
         ip.addFeedback("    synonym <" + remoteExportList + "> not found");
      } else {
         if(!sqlq.getColumn("TABLE_OWNER").equals(this.this$0.getPropertyValue("cp.db.admin.user").toUpperCase())) {
            ip.addFeedback("    synonym <" + remoteExportList + "> points to different user <" + sqlq.getColumn("TABLE_OWNER") + ">");
            return;
         }

         if(!sqlq.getColumn("TABLE_NAME").equals(localExportList.toUpperCase())) {
            ip.addFeedback("    synonym <" + remoteExportList + "> points to different table <" + sqlq.getColumn("TABLE_NAME") + ">");
            return;
         }

         ip.addFeedback("    synonym <" + remoteExportList + "> points to current CP instance of <" + localExportList + ">");
      }

      sqlb = new SqlBuilder(new String[]{"select  *", "from    ${remoteExportList}@${viaLink}"});
      sqlb.substitute(new String[]{"${viaLink}", viaLink.toUpperCase()});
      sqlb.substitute(new String[]{"${remoteExportList}", remoteExportList.toUpperCase()});
      CPInstallProperties$SqlQueryList var15 = this.query(sqlb);
      if(var15.getSqlCode() != 0) {
         ip.addFeedback("    link <" + linkBack + "> is not valid");
         ip.addFeedback("         " + var15.getSqlMessage());
      } else {
         ip.addFeedback(fms + ": comparing export list with CP version");
         sqlb = new SqlBuilder(new String[]{"select  *", "from    ${localExportList}"});
         sqlb.substitute(new String[]{"${localExportList}", localExportList.toUpperCase()});
         CPInstallProperties$SqlQueryList var16 = this.query(sqlb);
         if(var16.getNumRows() != var15.getNumRows()) {
            ip.addFeedback("    CP and fms export lists number of rows differ");
         } else if(var16.getNumColumns() != var15.getNumColumns()) {
            ip.addFeedback("    CP and fms export lists number of columns differ");
         } else {
            for(int row = 0; row < var16.getNumRows(); ++row) {
               var16.setCurrentRow(row);
               var15.setCurrentRow(row);

               for(int col = 0; col < var16.getNumColumns(); ++col) {
                  Object localColValue = var16.getColumn(col) == null?"":var16.getColumn(col);
                  Object remoteColValue = var15.getColumn(var16.getColumnName(col)) == null?"":var15.getColumn(var16.getColumnName(col));
                  if(!localColValue.equals(remoteColValue)) {
                     ip.addFeedback("    CP and fms export lists column values differ");
                     return;
                  }
               }
            }

            ip.addFeedback("    export lists matched - rows/cols=" + var16.getNumRows() + "/" + var16.getNumColumns());
         }
      }
   }

   protected boolean checkEfinLinkToCP(CPInstallProperties$InstallProperty ip) {
      this.checkFmsLinkToCP("efin", "efindatabase", "cpdatabase", "export_list_for_efin", "export_list_for_efin", ip);
      ip.addFeedback("efin: checking s_valid_coa package");
      SqlBuilder sqlb = new SqlBuilder(new String[]{"select STATUS from USER_OBJECTS@EFINDATABASE", "where  OBJECT_NAME = \'S_VALID_COA\'", "and    OBJECT_TYPE = \'PACKAGE BODY\'"});
      CPInstallProperties$SqlQueryList sqlq = this.query(sqlb);
      if(sqlq.getSqlCode() != 0) {
         ip.addFeedback("efin: can\'t check s_valid_coa: " + sqlb.toString() + " - " + sqlq.getSqlMessage());
         return false;
      } else if(sqlq.getNumRows() == 0) {
         ip.addFeedback("    package s_valid_coa not found");
         ip.addFeedback("      - the CP \'Add Accounts\' feature will be affected");
         return false;
      } else {
         String status = (String)sqlq.getColumn("STATUS");
         ip.addFeedback("    package s_valid_coa found - status " + status);
         if(!"VALID".equalsIgnoreCase(status)) {
            ip.addFeedback("      - the CP \'Add Accounts\' feature will be affected");
            return false;
         } else {
            return true;
         }
      }
   }

   protected CPInstallProperties$SqlQueryList getLinkedTables(String databaseLink) {
      SqlBuilder sqlb = null;
      if("efindatabase".equalsIgnoreCase(databaseLink)) {
         sqlb = new SqlBuilder(new String[]{"select  SYNONYM_NAME as TABLE_NAME", "from    USER_SYNONYMS", "join    USER_TABLES@" + databaseLink, "        using (TABLE_NAME)", "where   DB_LINK like \'" + databaseLink.toUpperCase() + "%\'", "order by 1"});
      } else {
         sqlb = new SqlBuilder(new String[]{"select  SYNONYM_NAME as TABLE_NAME", "from    USER_SYNONYMS", "where   DB_LINK like \'" + databaseLink.toUpperCase() + "%\'", "order by 1"});
      }

      return this.query(sqlb);
   }

   protected boolean checkIfTableHasRows(String dbType, String table) {
      SqlBuilder sqlb = new SqlBuilder(new String[]{"select  1 from ${dummyTable}", "where   exists", "        (", "        select  1 from ${table}", "        )"});
      sqlb.substitute(new String[]{"${dummyTable}", "oracle".equalsIgnoreCase(dbType)?"dual":("progress".equalsIgnoreCase(dbType)?"sysprogress.syscalctable":("openedge".equalsIgnoreCase(dbType)?"sysprogress.syscalctable":"dual"))});
      sqlb.substitute(new String[]{"${table}", table});
      CPInstallProperties$SqlQueryList sqlq = this.query(sqlb);
      return sqlq.getSqlCode() == 0;
   }

   protected void checkE5onDB2Objects(CPInstallProperties$InstallProperty ip) {
      ResultSet resultSet = null;
      PreparedStatement stat = null;
      String sql = "select * from TSECPIDS where 1 = 0";

      try {
         stat = this.getConnection().prepareStatement(sql);
         resultSet = stat.executeQuery();
         ip.addFeedback("table found: TSECPIDS");
      } catch (SQLException var23) {
         if(var23.getSQLState().equals("42704")) {
            ip.addFeedback("table TSECPIDS not found");
         } else {
            ip.addFeedback("table TSECPIDS " + var23.getMessage());
         }
      } catch (Exception var24) {
         ip.addFeedback("table TSECPIDS " + var24.getMessage());
      } finally {
         this.closeResultSet(resultSet);
         this.closeStatement(stat);
         this.closeConnection();
      }

      sql = "select * from TSECPDAT where 1 = 0";

      try {
         stat = this.getConnection().prepareStatement(sql);
         resultSet = stat.executeQuery();
         ip.addFeedback("table found: TSECPDAT");
      } catch (SQLException var20) {
         if(var20.getSQLState().equals("42704")) {
            ip.addFeedback("table TSECPIDS not found");
         } else {
            ip.addFeedback("table TSECPIDS " + var20.getMessage());
         }
      } catch (Exception var21) {
         ip.addFeedback("table TSECPIDS " + var21.getMessage());
      } finally {
         this.closeResultSet(resultSet);
         this.closeStatement(stat);
         this.closeConnection();
      }

   }

   public void logEvent(int taskId, int eventType, String eventText) {
      CallableStatement stmt = null;
      byte col = 1;

      try {
         stmt = this.getConnection().prepareCall("begin cp_utils.logTaskEvent(?,?,?); end;");
         int var13 = col + 1;
         stmt.setInt(col, taskId);
         stmt.setInt(var13++, eventType);
         stmt.setString(var13++, eventText);
         stmt.execute();
      } catch (SQLException var10) {
         var10.printStackTrace();
      } finally {
         this.closeStatement(stmt);
         this.closeConnection();
      }

   }

   public void execute(String s) throws SQLException {
      Statement stmt = null;

      try {
         stmt = this.getConnection().createStatement();
         stmt.execute(s);
      } finally {
         this.closeStatement(stmt);
         this.closeConnection();
      }

   }

   public void insertInstallProps(Map<String, CPInstallProperties$InstallProperty> props) {
      PreparedStatement stmt = null;

      try {
         stmt = this.getConnection().prepareStatement("insert into INSTALL_PROPS values(?,?)");
         Iterator e = props.keySet().iterator();

         while(e.hasNext()) {
            String s = (String)e.next();
            stmt.setString(1, s);
            stmt.setString(2, ((CPInstallProperties$InstallProperty)props.get(s)).getValue());
            stmt.addBatch();
         }

         stmt.executeBatch();
      } catch (SQLException var8) {
         CPInstallProperties.accessMethod900(this.this$0).debug("unable to insert INSTALL_PROPS:");
         CPInstallProperties.accessMethod900(this.this$0).error("    " + var8.getMessage(), "");
      } finally {
         this.closeStatement(stmt);
         this.closeConnection();
      }

   }

   protected CPInstallProperties$SqlQueryList loadProperties() {
      SqlBuilder sqlb = new SqlBuilder(new String[]{"select  NAME, VALUE", "from    INSTALL_PROPS", "order   by NAME"});
      return this.query(sqlb);
   }
}
