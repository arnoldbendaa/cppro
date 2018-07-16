// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:07:01
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.base.async.extsys;

import com.cedar.cp.api.model.FinanceCubeRef;
import com.cedar.cp.api.task.TaskCheckpoint;
import com.cedar.cp.dto.extsys.ExtSysE5DB2PushTaskRequest;
import com.cedar.cp.dto.model.FinanceCubeCK;
import com.cedar.cp.dto.model.FinanceCubePK;
import com.cedar.cp.ejb.base.async.AbstractTask;
import com.cedar.cp.ejb.base.async.extsys.ExtSysE5DB2PushTask$ExtSysE5DB2PushCheckpoint;
import com.cedar.cp.ejb.impl.extsys.E5DB2AccessorDAO;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class ExtSysE5DB2PushTask extends AbstractTask {

   private E5DB2AccessorDAO mE5DB2AccessorDAO;


   public int getReportType() {
      return 11;
   }

   public ExtSysE5DB2PushTask$ExtSysE5DB2PushCheckpoint getCheckpoint() {
      return (ExtSysE5DB2PushTask$ExtSysE5DB2PushCheckpoint)super.getCheckpoint();
   }

   public String getEntityName() {
      return "ExtSysE5DB2PushTask";
   }

   public void runUnitOfWork(InitialContext context) throws Exception {
      if(this.getCheckpoint() == null) {
         this.firstTime();
      } else {
         this.pushCube();
      }

   }

   private void firstTime() throws Exception {
      this.copyExportList();
      this.setCheckpoint(new ExtSysE5DB2PushTask$ExtSysE5DB2PushCheckpoint());
      this.getCheckpoint().setFinanceCubes(this.getRequest().getFinanceCubes());
   }

   private void copyExportList() throws SQLException {
      String sql = "select * from EXPORT_LIST_FOR_E5 order by FINANCE_CUBE_ID";
      if(this._log.isDebugEnabled()) {
         this._log.debug(sql);
      }

      PreparedStatement stmt = null;
      ResultSet resultSet = null;

      try {
         stmt = this.getConnection().prepareStatement(sql);
         stmt.setFetchSize(500);
         resultSet = stmt.executeQuery();
         String sqle = this.getE5DB2AccessorDAO().copyExportList(resultSet);
         String[] info = sqle.split("\n");

         for(int i = 0; i < info.length; ++i) {
            this.log(info[i]);
         }
      } catch (SQLException var10) {
         this._log.debug("copyExportList", sql);
         System.err.println(var10);
         var10.printStackTrace();
         throw var10;
      } finally {
         this.closeResultSet(resultSet);
         this.closeStatement(stmt);
         this.closeConnection();
      }

   }

   private void pushCube() throws SQLException {
      if(this.getCheckpointNumber() > this.getRequest().getFinanceCubes().size()) {
         this.tidyCubeExports();
         this.setCheckpoint((TaskCheckpoint)null);
      } else {
         this.copyExportView((FinanceCubeRef)this.getRequest().getFinanceCubes().get(this.getCheckpointNumber() - 1));
      }

   }

   private void tidyCubeExports() throws SQLException {
      try {
         String sqle = this.getE5DB2AccessorDAO().tidyCubeExports();
         String[] info = sqle.split("\n");

         for(int i = 0; i < info.length; ++i) {
            this.log(info[i]);
         }

      } catch (SQLException var4) {
         this._log.debug("tidyCubeExports");
         System.err.println(var4);
         var4.printStackTrace();
         throw var4;
      }
   }

   private void copyExportView(FinanceCubeRef cubeRef) throws SQLException {
      int financeCubeId;
      if(cubeRef.getPrimaryKey() instanceof FinanceCubePK) {
         financeCubeId = ((FinanceCubePK)cubeRef.getPrimaryKey()).getFinanceCubeId();
      } else {
         financeCubeId = ((FinanceCubeCK)cubeRef.getPrimaryKey()).getFinanceCubePK().getFinanceCubeId();
      }

      String sql = "select * from XFT" + financeCubeId;
      if(this._log.isDebugEnabled()) {
         this._log.debug(sql);
      }

      PreparedStatement stmt = null;
      ResultSet resultSet = null;

      try {
         stmt = this.getConnection().prepareStatement(sql);
         stmt.setFetchSize(500);
         resultSet = stmt.executeQuery();
         String sqle = this.getE5DB2AccessorDAO().copyExportView(financeCubeId, resultSet);
         String[] info = sqle.split("\n");

         for(int i = 0; i < info.length; ++i) {
            this.log(info[i]);
         }
      } catch (SQLException var12) {
         this._log.debug("copyExportList", sql);
         System.err.println(var12);
         var12.printStackTrace();
         throw var12;
      } finally {
         this.closeResultSet(resultSet);
         this.closeStatement(stmt);
         this.closeConnection();
      }

   }

   public ExtSysE5DB2PushTaskRequest getRequest() {
      return (ExtSysE5DB2PushTaskRequest)super.getRequest();
   }

   private E5DB2AccessorDAO getE5DB2AccessorDAO() {
      if(this.mE5DB2AccessorDAO == null) {
         String lookupName = "java:comp/env/jdbc/db2";
         DataSource dataSource = null;

         try {
            InitialContext ne = new InitialContext();
            dataSource = (DataSource)ne.lookup(lookupName);
         } catch (NamingException var4) {
            throw new RuntimeException("error looking up DataSource " + lookupName + ": " + var4.getMessage(), var4);
         }

         this.mE5DB2AccessorDAO = new E5DB2AccessorDAO(dataSource);
      }

      return this.mE5DB2AccessorDAO;
   }
}
