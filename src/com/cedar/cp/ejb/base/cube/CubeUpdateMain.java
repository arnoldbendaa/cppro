// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:07:03
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.base.cube;

import com.cedar.cp.ejb.base.cube.CubeUpdateEngine;
import com.cedar.cp.ejb.base.cube.CubeUpdateMain$1;
import java.io.File;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class CubeUpdateMain {

   private static final String USAGE = "usage: dbConnection=jdbc:oracle:thin:@HOST:1521:SIDusername=usr password=passwrd [-file=filename]|[-dir=dirname]";
   private String mConnectionURL = "jdbc:oracle:thin:@localhost:1521:cp";
   private String nUsername = "dev";
   private String mPassword = "dev";
   private String mDir;
   private String mFilename;
   private CubeUpdateEngine mEngine;


   public static void main(String[] args) {
      new CubeUpdateMain(args);
   }

   public CubeUpdateMain(String[] args) {
      for(int c = 0; c < args.length; ++c) {
         String e = args[c];
         if(e != null && e.charAt(0) == 45 && e.indexOf("=") != -1) {
            String param = e.substring(1, e.indexOf("="));
            String eEnd = e.substring(e.indexOf("=") + 1);
            if(param.equalsIgnoreCase("dbConnection")) {
               this.mConnectionURL = eEnd;
            } else if(param.equalsIgnoreCase("username")) {
               this.nUsername = eEnd;
            } else if(param.equalsIgnoreCase("password")) {
               this.mPassword = eEnd;
            } else if(param.equalsIgnoreCase("file")) {
               this.mFilename = eEnd;
            } else if(param.equalsIgnoreCase("dir")) {
               this.mDir = eEnd;
            } else {
               System.out.println("Unexpected param[" + e + "] ignored.");
               System.out.println("usage: dbConnection=jdbc:oracle:thin:@HOST:1521:SIDusername=usr password=passwrd [-file=filename]|[-dir=dirname]");
               System.exit(-1);
            }
         } else {
            System.out.println("Unexpected param[" + e + "] ignored.");
            System.out.println("usage: dbConnection=jdbc:oracle:thin:@HOST:1521:SIDusername=usr password=passwrd [-file=filename]|[-dir=dirname]");
            System.exit(-1);
         }
      }

      long var38;
      Connection var37;
      if(this.mDir != null) {
         var37 = null;

         try {
            var38 = System.currentTimeMillis();
            File var39 = new File(this.mDir);
            File[] files = var39.listFiles(new CubeUpdateMain$1(this));
            var37 = this.connect();
            this.mEngine = new CubeUpdateEngine(var37);

            for(int eEnd1 = 0; eEnd1 < files.length; ++eEnd1) {
               this.runBatch(var37, files[eEnd1]);
            }

            long var41 = System.currentTimeMillis();
            System.out.println("Total elapsed:" + (var41 - var38) + " milliseconds");
         } catch (Exception var35) {
            var35.printStackTrace();
         } finally {
            try {
               if(var37 != null) {
                  var37.close();
               }
            } catch (SQLException var31) {
               var31.printStackTrace();
            }

         }
      }

      if(this.mFilename != null) {
         var37 = null;

         try {
            var38 = System.currentTimeMillis();
            var37 = this.connect();
            this.mEngine = new CubeUpdateEngine(var37);
            this.runBatch(var37, new File(this.mFilename));
            long var40 = System.currentTimeMillis();
            System.out.println("Batch elapsed:" + (var40 - var38) + " milliseconds");
         } catch (Exception var33) {
            var33.printStackTrace();
         } finally {
            try {
               if(var37 != null) {
                  var37.close();
               }
            } catch (SQLException var32) {
               var32.printStackTrace();
            }

         }
      }

   }

   private Connection connect() throws Exception {
      Class.forName("oracle.jdbc.driver.OracleDriver");
      Connection cnx = DriverManager.getConnection(this.mConnectionURL, this.nUsername, this.mPassword);
      cnx.setAutoCommit(false);
      return cnx;
   }

   private void runBatch(Connection c, File f) throws Exception {
      this.mEngine.updateCube(new FileReader(f));
      c.commit();
   }
}
