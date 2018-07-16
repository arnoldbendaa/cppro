// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:27:24
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.impl.sysinit;

import com.cedar.cp.api.base.EntityList;
import com.cedar.cp.dto.model.FinanceCubeRefImpl;
import com.cedar.cp.dto.model.ModelRefImpl;
import com.cedar.cp.ejb.impl.model.FinanceCubeDAO;
import com.cedar.cp.ejb.impl.model.FinanceCubeDDLDAO;
import com.cedar.cp.ejb.impl.model.ModelDAO;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SystemInit {

   private static final String USAGE = "usage: SystemInit=jdbc:oracle:thin:@HOST:1521:SIDusername=usr password=passwrd  financeCubeIds=1,2,3 NumDims=3,3,3";
   private String mConnectionURL = "jdbc:oracle:thin:@localhost:1521:cp";
   private String nUsername = "dev";
   private String mPassword = "dev";


   public static void main(String[] args) {
      try {
         new SystemInit(args);
      } catch (Exception var2) {
         var2.printStackTrace();
         System.exit(-1);
      }

      System.exit(0);
   }

   void processArgs(String[] args) {
      for(int i = 0; i < args.length; ++i) {
         String arg = args[i];
         if(arg != null && arg.charAt(0) == 45 && arg.indexOf("=") != -1) {
            String param = arg.substring(1, arg.indexOf("="));
            String value = arg.substring(arg.indexOf("=") + 1);
            if(param.equalsIgnoreCase("dbConnection")) {
               this.mConnectionURL = value;
            } else if(param.equalsIgnoreCase("username")) {
               this.nUsername = value;
            } else if(param.equalsIgnoreCase("password")) {
               this.mPassword = value;
            } else {
               System.out.println("Unexpected param[" + arg + "] ignored.");
               System.out.println("usage: SystemInit=jdbc:oracle:thin:@HOST:1521:SIDusername=usr password=passwrd  financeCubeIds=1,2,3 NumDims=3,3,3");
               System.exit(-1);
            }
         } else {
            System.out.println("Unexpected param[" + arg + "] ignored.");
            System.out.println("usage: SystemInit=jdbc:oracle:thin:@HOST:1521:SIDusername=usr password=passwrd  financeCubeIds=1,2,3 NumDims=3,3,3");
            System.exit(-1);
         }
      }

   }

   private Connection connect() throws Exception {
      Class.forName("oracle.jdbc.driver.OracleDriver");
      Connection cnx = DriverManager.getConnection(this.mConnectionURL, this.nUsername, this.mPassword);
      cnx.setAutoCommit(false);
      return cnx;
   }

   private EntityList queryFinanceCubes(Connection connection) throws SQLException {
      FinanceCubeDAO fcDAO = new FinanceCubeDAO(connection);
      return fcDAO.getAllFinanceCubes();
   }

   private EntityList getModelDimensions(Connection connection, int modelId) {
      ModelDAO modelDAO = new ModelDAO(connection);
      return modelDAO.getModelDimensions(modelId);
   }

   /*     */   private void initialiseFinanceCubes(Connection connection)
   /*     */     throws Exception
   /*     */   {
   /* 102 */     EntityList queryFinanceCubes = queryFinanceCubes(connection);
   /*     */ 
   /* 104 */     for (int row = 0; row < queryFinanceCubes.getNumRows(); row++)
   /*     */     {
   /* 106 */       FinanceCubeRefImpl fcRef = (FinanceCubeRefImpl)queryFinanceCubes.getValueAt(row, "FinanceCube");
   /* 107 */       ModelRefImpl modelRef = (ModelRefImpl)queryFinanceCubes.getValueAt(row, "Model");
   /*     */ 
   /* 109 */       int financeCubeId = fcRef.getFinanceCubePK().getFinanceCubeId();
   /* 110 */       EntityList modelDimensions = getModelDimensions(connection, modelRef.getModelPK().getModelId());
   /*     */ 
   /* 112 */       String numbertable = "NFT" + financeCubeId;
   /* 113 */       String stringtable = "SFT" + financeCubeId;
   /*     */ 
   /* 115 */       FinanceCubeDDLDAO dao = new FinanceCubeDDLDAO(connection, financeCubeId, modelDimensions.getNumRows());
   /*     */ 
   /* 119 */       System.out.println("Creating tables for finance cube:" + fcRef.getNarrative());
   /*     */ 
   /* 121 */       dao.createTablesAndViews();
   /*     */     }
   /*     */   }

   public SystemInit(String[] args) throws Exception {
      this.processArgs(args);
      Connection connection = null;

      try {
         connection = this.connect();
         this.initialiseFinanceCubes(connection);
      } finally {
         try {
            if(connection != null) {
               connection.close();
            }
         } catch (SQLException var9) {
            System.err.println("Failed to close connection:" + var9.getMessage());
         }

      }

   }
}
