// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:07:00
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.base.async.admin.tidy;

import com.cedar.cp.ejb.base.async.admin.tidy.TidyTask;
import com.cedar.cp.ejb.impl.base.AbstractDAO;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;

class TidyTask$TidyDBDAO extends AbstractDAO {

   // $FF: synthetic field
   final TidyTask this$0;


   TidyTask$TidyDBDAO(TidyTask var1) {
      this.this$0 = var1;
   }

   public Integer issueUpdate(String cmd) throws Exception {
      this.this$0.getTaskReport().addTaskSection("Update");
      this.this$0.getTaskReport().addTaskMessage("sql cmd : " + cmd);
      Statement stmt = null;
      boolean result = false;

      Integer e;
      try {
         stmt = this.getConnection().createStatement();
         int result1 = stmt.executeUpdate(cmd);
         this.this$0.getTaskReport().addTaskMessage("command updated : " + result1 + " rows");
         e = Integer.valueOf(result1);
      } catch (Exception var8) {
         this.this$0.getTaskReport().addTaskMessage("Error during execution " + var8.getMessage());
         throw var8;
      } finally {
         this.closeStatement(stmt);
         this.closeConnection();
         this.this$0.getTaskReport().addEndTaskSection();
         this.this$0.getTaskReport().flushText();
      }

      return e;
   }

   public void issueQuery(String cmd) throws Exception {
      this.this$0.getTaskReport().addTaskSection("Query");
      this.this$0.getTaskReport().addTaskMessage("sql cmd : " + cmd);
      this.this$0.getTaskReport().addEndTaskSection();
      Statement stmt = null;
      ResultSet result = null;

      try {
         this.this$0.getTaskReport().addMatrixSection("Results");
         stmt = this.getConnection().createStatement();
         result = stmt.executeQuery(cmd);
         this.processResultSet(result);
         this.this$0.getTaskReport().addEndMatrixSection();
      } catch (Exception var8) {
         this.this$0.getTaskReport().addEndMatrixSection();
         this.this$0.getTaskReport().addTaskSection("Error");
         this.this$0.getTaskReport().addTaskMessage("Error during execution " + var8.getMessage());
         this.this$0.getTaskReport().addEndTaskSection();
         throw var8;
      } finally {
         this.closeResultSet(result);
         this.closeStatement(stmt);
         this.closeConnection();
         this.this$0.getTaskReport().flushText();
      }

   }

   public void issueReportPackage(String cmd) throws Exception {
      this.this$0.getTaskReport().addTaskSection("Report Package");
      this.this$0.getTaskReport().addTaskMessage("sql cmd : " + cmd);
      this.this$0.getTaskReport().addEndTaskSection();
      CallableStatement stmt = null;
      ResultSet result = null;

      try {
         this.this$0.getTaskReport().addMatrixSection("Results");
         stmt = this.getConnection().prepareCall(cmd);
         stmt.registerOutParameter(1, -10);
         stmt.execute();
         result = (ResultSet)stmt.getObject(1);
         this.processResultSet(result);
         this.this$0.getTaskReport().addEndMatrixSection();
      } catch (Exception var8) {
         this.this$0.getTaskReport().addEndMatrixSection();
         this.this$0.getTaskReport().addTaskSection("Error");
         this.this$0.getTaskReport().addTaskMessage("Error during execution " + var8.getMessage());
         this.this$0.getTaskReport().addEndTaskSection();
         throw var8;
      } finally {
         this.closeResultSet(result);
         this.closeStatement(stmt);
         this.closeConnection();
         this.this$0.getTaskReport().flushText();
      }

   }

   public void issueUpdatePackage(String cmd) throws Exception {
      CallableStatement stmt = null;

      try {
         stmt = this.getConnection().prepareCall(cmd);
         stmt.setInt(1, this.this$0.getTaskId());
         stmt.execute();
      } catch (Exception var7) {
         throw var7;
      } finally {
         this.closeStatement(stmt);
         this.closeConnection();
      }

   }

   private void processResultSet(ResultSet result) throws Exception {
      ResultSetMetaData metadata = result.getMetaData();
      int columnCount = metadata.getColumnCount();
      Class[] columnClasses = new Class[columnCount];
      String[] columnNames = new String[columnCount];

      int i;
      for(i = 1; i <= columnCount; ++i) {
         String o = metadata.getColumnClassName(i);
         columnClasses[i - 1] = Class.forName(o);
         columnNames[i - 1] = metadata.getColumnName(i);
      }

      this.this$0.getTaskReport().addRow();

      for(i = 0; i < columnNames.length; ++i) {
         this.this$0.getTaskReport().addCellHeading(columnNames[i], "left");
      }

      this.this$0.getTaskReport().addEndRow();

      while(result.next()) {
         this.this$0.getTaskReport().addRow();

         for(i = 0; i < columnCount; ++i) {
            Object var8 = result.getObject(i + 1);
            if(var8 == null) {
               this.this$0.getTaskReport().addCellText("");
            } else {
               this.this$0.getTaskReport().addCellText(var8.toString());
            }
         }

         this.this$0.getTaskReport().addEndRow();
      }

   }

   public String getEntityName() {
      return "TidyTask";
   }
}
