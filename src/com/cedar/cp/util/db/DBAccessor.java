// Decompiled by:       Fernflower v0.8.6
// Date:                11.08.2012 20:22:09
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.util.db;

import com.cedar.cp.util.Log;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public abstract class DBAccessor {

   public static final String sJNDI_NAME = "java:jboss/jdbc/fc";
   protected Log mLog;
   protected Connection mCnx;
   protected String mLookupName;


   protected abstract void processResultSet(ResultSet var1) throws SQLException;

   protected DBAccessor(String lookupName) {
      this.mLog = new Log(this.getClass());
      this.mLookupName = lookupName;
      this.getConnection();
   }

   protected DBAccessor() {
      this(sJNDI_NAME);
   }

   public void finalize() {
      try {
         this.close();
         super.finalize();
      } catch (Throwable var2) {
         ;
      }

   }

   protected void handleException(Exception e) {}

   public void close() {
      if(this.mCnx != null) {
         try {
            this.mCnx.close();
         } catch (SQLException var5) {
            ;
         } finally {
            this.mCnx = null;
         }
      }

   }

   protected void executePreparedQuery(String statement, Object[] variables) {
      ResultSet result = null;
      PreparedStatement stat = null;

      try {
         stat = this.mCnx.prepareStatement(statement);

         for(int e2 = 0; e2 < variables.length; ++e2) {
            stat.setObject(e2 + 1, variables[e2]);
         }

         result = stat.executeQuery();
         this.processResultSet(result);
      } catch (Exception var14) {
         var14.printStackTrace();
         this.handleException(var14);
      } finally {
         try {
            if(result != null) {
               result.close();
            }

            if(stat != null) {
               stat.close();
            }
         } catch (Exception var13) {
            var13.printStackTrace();
         }

      }

   }

	protected void executeQuery(String query) {
		ResultSet result = null;
		Statement stat = null;

		try	{
			stat = mCnx.createStatement();
			result = stat.executeQuery(query);
			processResultSet(result);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try	{
				if (result != null)
					result.close();
				if (stat != null)
					stat.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
	}

   protected void executeUpdate(String query, List params) {
      PreparedStatement stat = null;

      try {
         stat = this.mCnx.prepareStatement(query);
         int e2 = params.size();

         for(int i = 0; i < e2; ++i) {
            Object o = params.get(i);
            if(o instanceof Integer) {
               Integer value = (Integer)o;
               stat.setInt(i + 1, value.intValue());
            } else if(o instanceof String) {
               String var19 = (String)o;
               stat.setString(i + 1, var19);
            } else if ((o instanceof java.util.Date)) {
                long time = ((java.util.Date)o).getTime();
                java.sql.Date value = new java.sql.Date(time);
                stat.setDate(i + 1, value);
              }
         }

         int result = stat.executeUpdate();
         this.updateResult(query, result);
      } catch (Exception var17) {
         var17.printStackTrace();
      } finally {
         try {
            if(stat != null) {
               stat.close();
            }
         } catch (Exception var16) {
            var16.printStackTrace();
         }

      }

   }

   public void updateResult(String query, int result) {
      this.mLog.debug("updateResult", "query : " + query + " returned result " + result);
   }

   protected void getConnection() {
      try {
         InitialContext sqle = new InitialContext();
         DataSource ds = (DataSource)sqle.lookup(this.mLookupName);
         this.mCnx = ds.getConnection();
      } catch (NamingException var3) {
         this.mLog.debug("Unable to get DataSource on initialisation");
         var3.printStackTrace();
         throw new IllegalStateException(var3);
      } catch (SQLException var4) {
         this.mLog.debug("Unable to get connection on initialisation");
         var4.printStackTrace();
         throw new IllegalStateException(var4);
      }
   }

   public byte[] blobToByteArray(Blob blob) throws SQLException {
      InputStream is = null;

      byte[] var4;
      try {
         is = blob.getBinaryStream();
         byte[] e = new byte[(int)blob.length()];
         is.read(e);
         var4 = e;
      } catch (IOException var13) {
         throw new SQLException(var13.getMessage());
      } finally {
         try {
            is.close();
         } catch (IOException var12) {
            ;
         }

      }

      return var4;
   }
}
