/*     */ package com.cedar.cp.ejb.impl.extsys;
/*     */ 
/*     */ import com.cedar.cp.ejb.impl.base.AbstractDAO;
/*     */ import com.cedar.cp.util.Log;
/*     */ import com.cedar.cp.util.Timer;
/*     */ import java.sql.BatchUpdateException;
/*     */ import java.sql.Connection;
/*     */ import java.sql.PreparedStatement;
/*     */ import java.sql.ResultSet;
/*     */ import java.sql.ResultSetMetaData;
/*     */ import java.sql.SQLException;
/*     */ import java.text.SimpleDateFormat;
/*     */ import java.util.Date;
/*     */ import javax.sql.DataSource;
/*     */ 
/*     */ public class E5DB2AccessorDAO extends AbstractDAO
/*     */ {
/*  25 */   Log _log = new Log(getClass());
/*     */ 
/* 109 */   private static int BATCH_SIZE = 1000;
/*     */ 
/*     */   public E5DB2AccessorDAO(DataSource ds)
/*     */   {
/*  33 */     super(ds);
/*     */   }
/*     */ 
/*     */   private String getTime()
/*     */   {
/*  38 */     SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
/*  39 */     return sdf.format(new Date()).toString();
/*     */   }
/*     */ 
/*     */   public String copyExportList(ResultSet resultSet) throws SQLException
/*     */   {
/*  44 */     PreparedStatement stmt = null;
/*  45 */     Timer timer = new Timer(this._log);
/*  46 */     String info = null;
/*     */ 
/*  49 */     String sql = "delete from TSECPIDS";
/*     */     try
/*     */     {
/*  53 */       stmt = getConnection().prepareStatement(sql);
/*  54 */       int n = stmt.executeUpdate();
/*     */     }
/*     */     catch (SQLException e)
/*     */     {
///*     */       int n;
/*  58 */       this._log.debug(sql);
/*  59 */       e.printStackTrace();
/*  60 */       throw new RuntimeException("copyExportList", e);
/*     */     }
/*     */     finally
/*     */     {
/*  64 */       closeStatement(stmt);
/*  65 */       timer.logInfo("copyExportList", "deleted previous content");
/*  66 */       timer.reset();
/*     */     }
/*  68 */     info = "TSECPIDS deleted, timer=" + timer.getElapsed() + "\n";
/*     */ 
/*  70 */     info = info + copyResultSet(resultSet, "TSECPIDS");
/*     */ 
/*  72 */     return info;
/*     */   }
/*     */ 
/*     */   public String tidyCubeExports() throws SQLException
/*     */   {
/*  77 */     PreparedStatement stmt = null;
/*  78 */     Timer timer = new Timer(this._log);
/*     */ 
/*  80 */     String info = "";
/*     */ 
/*  83 */     String sql = "delete from TSECPDAT where FINANCE_CUBE_ID not in (select distinct FINANCE_CUBE_ID from TSECPIDS)";
/*     */ 
/*  86 */     int rowCount = 0;
/*     */     try
/*     */     {
/*  89 */       this._log.debug(sql);
/*  90 */       stmt = getConnection().prepareStatement(sql);
/*  91 */       rowCount = stmt.executeUpdate();
/*  92 */       info = "TSECPDAT tidied, rows=" + rowCount + ", timer=" + timer.getElapsed() + "\n";
/*     */     }
/*     */     catch (SQLException e)
/*     */     {
/*  96 */       this._log.debug(sql);
/*  97 */       throw e;
/*     */     }
/*     */     finally
/*     */     {
/* 101 */       closeStatement(stmt);
/* 102 */       timer.logInfo("tidyCubeExports", "TSECPDAT tidied - rows=" + rowCount);
/* 103 */       timer.reset();
/*     */     }
/*     */ 
/* 106 */     return info;
/*     */   }
/*     */ 
/*     */   public String copyExportView(int financeCubeId, ResultSet resultSet)
/*     */     throws SQLException
/*     */   {
/* 112 */     PreparedStatement stmt = null;
/* 113 */     Timer timer = new Timer(this._log);
/*     */ 
/* 115 */     String info = null;
/*     */ 
/* 118 */     String sql = "delete from TSECPDAT where FINANCE_CUBE_ID = ?";
/*     */ 
/* 121 */     int rowCount = 0;
/*     */     try
/*     */     {
/* 124 */       this._log.debug(sql);
/* 125 */       stmt = getConnection().prepareStatement(sql);
/* 126 */       stmt.setInt(1, financeCubeId);
/* 127 */       rowCount = stmt.executeUpdate();
/*     */     }
/*     */     catch (SQLException e)
/*     */     {
/* 131 */       this._log.debug(sql);
/* 132 */       throw e;
/*     */     }
/*     */     finally
/*     */     {
/* 136 */       closeStatement(stmt);
/* 137 */       timer.logInfo("copyExportView", "deleted previous content - rows=" + rowCount);
/* 138 */       timer.reset();
/*     */     }
/* 140 */     info = "TSECPDAT deleted, rows=" + rowCount + ", timer=" + timer.getElapsed() + "\n";
/*     */ 
/* 143 */     info = info + copyResultSet(resultSet, "TSECPDAT");
/*     */ 
/* 145 */     return info;
/*     */   }
/*     */ 
/*     */   public String copyResultSet(ResultSet resultSet, String targetTableName) throws SQLException {
/* 149 */     PreparedStatement stmt = null;
/* 150 */     Timer timer = new Timer(this._log);
/* 151 */     String sql = null;
/* 152 */     String info = null;
/*     */ 
/* 155 */     StringBuffer sqlbuild = new StringBuffer();
/* 156 */     sqlbuild.append("insert into " + targetTableName + " (");
/* 157 */     for (int i = 0; i < resultSet.getMetaData().getColumnCount(); i++)
/*     */     {
/* 159 */       if (i > 0)
/* 160 */         sqlbuild.append(",");
/* 161 */       sqlbuild.append(resultSet.getMetaData().getColumnName(i + 1));
/*     */     }
/* 163 */     sqlbuild.append(") values (");
/* 164 */     for (int i = 0; i < resultSet.getMetaData().getColumnCount(); i++)
/*     */     {
/* 166 */       if (i > 0)
/* 167 */         sqlbuild.append(",");
/* 168 */       sqlbuild.append("?");
/*     */     }
/* 170 */     sqlbuild.append(")");
/* 171 */     sql = sqlbuild.toString();
/*     */ 
/* 173 */     this._log.debug(sql);
/* 174 */     int rowCount = 0;
/* 175 */     int batchCount = 0;
/*     */     try
/*     */     {
/* 179 */       stmt = getConnection().prepareStatement(sql);
/*     */ 
/* 181 */       while (resultSet.next())
/*     */       {
/* 183 */         rowCount++;
/* 184 */         batchCount++;
/*     */ 
/* 186 */         for (int i = 0; i < resultSet.getMetaData().getColumnCount(); i++)
/*     */         {
/* 188 */           if (resultSet.getObject(i + 1) == null)
/* 189 */             stmt.setNull(i + 1, resultSet.getMetaData().getColumnType(i + 1));
/*     */           else
/* 191 */             stmt.setObject(i + 1, resultSet.getObject(i + 1));
/*     */         }
/* 193 */         stmt.addBatch();
/*     */ 
/* 195 */         if (batchCount != BATCH_SIZE)
/*     */           continue;
/* 197 */         stmt.executeBatch();
/* 198 */         stmt.clearBatch();
/* 199 */         this._log.info("copyResultSet", "batch=" + batchCount);
/* 200 */         batchCount = 0;
/*     */       }
/*     */ 
/* 204 */       if (batchCount != 0)
/*     */       {
/* 206 */         stmt.executeBatch();
/* 207 */         stmt.clearBatch();
/* 208 */         this._log.info("copyResultSet", "batch=" + batchCount);
/*     */       }
/*     */     }
/*     */     catch (BatchUpdateException e)
/*     */     {
/* 213 */       this._log.error("copyResultSet", e.getMessage());
/* 214 */       throw e;
/*     */     }
/*     */     catch (SQLException e)
/*     */     {
/* 218 */       this._log.debug(sql);
/* 219 */       throw e;
/*     */     }
/*     */     finally
/*     */     {
/* 223 */       closeResultSet(resultSet);
/* 224 */       closeStatement(stmt);
/* 225 */       closeConnection();
/* 226 */       timer.logInfo("copyResultSet", targetTableName + " rows=" + rowCount);
/*     */     }
/* 228 */     info = targetTableName + " inserted, rows=" + rowCount + ", timer=" + timer.getElapsed();
/*     */ 
/* 230 */     return info;
/*     */   }
/*     */ 
/*     */   public String getEntityName()
/*     */   {
/* 235 */     return "OaAccessorDAO";
/*     */   }
/*     */ }

/* Location:           /home/oracle/coa/cp.ear/cp.ear_orginal/cp-server.jar
 * Qualified Name:     com.cedar.cp.ejb.impl.extsys.E5DB2AccessorDAO
 * JD-Core Version:    0.6.0
 */