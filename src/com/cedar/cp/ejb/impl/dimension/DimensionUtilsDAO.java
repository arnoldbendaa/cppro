/*    */ package com.cedar.cp.ejb.impl.dimension;
/*    */ 
/*    */ import com.cedar.cp.ejb.impl.base.AbstractDAO;
/*    */ import com.cedar.cp.util.Log;
/*    */ import com.cedar.cp.util.Timer;
/*    */ import java.sql.CallableStatement;
/*    */ import java.sql.Connection;
/*    */ import java.sql.SQLException;
/*    */ import javax.sql.DataSource;
/*    */ 
/*    */ public class DimensionUtilsDAO extends AbstractDAO
/*    */ {
/*    */   private static final String INIT_DIMENSION_CALL_SQL = "{ call DIMENSION_UTILS.INIT_DIMENSION( ?, ? ) }";
/*    */   private static final String ANALYSE_STATS_CALL_SQL = "{ call DIMENSION_UTILS.ANALYSE_STATS() }";
/*    */ 
/*    */   public DimensionUtilsDAO()
/*    */   {
/*    */   }
/*    */ 
/*    */   public DimensionUtilsDAO(Connection connection)
/*    */   {
/* 22 */     super(connection);
/*    */   }
/*    */ 
/*    */   public DimensionUtilsDAO(DataSource ds)
/*    */   {
/* 27 */     super(ds);
/*    */   }
/*    */ 
/*    */   public void initDimensionUtils(int taskId, int dimensionId)
/*    */     throws SQLException
/*    */   {
/* 42 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/* 43 */     CallableStatement cs = null;
/*    */     try
/*    */     {
/* 47 */       cs = getConnection().prepareCall("{ call DIMENSION_UTILS.INIT_DIMENSION( ?, ? ) }");
/* 48 */       cs.setInt(1, taskId);
/* 49 */       cs.setInt(2, dimensionId);
/* 50 */       cs.execute();
/*    */     }
/*    */     finally
/*    */     {
/* 54 */       closeStatement(cs);
/* 55 */       closeConnection();
/*    */     }
/*    */ 
/* 58 */     if (timer != null)
/* 59 */       timer.logDebug("DimensionUtils.initDimension for dimensionId:", String.valueOf(dimensionId));
/*    */   }
/*    */ 
/*    */   public void analyseStatsDimensionUtils()
/*    */     throws SQLException
/*    */   {
/* 69 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/* 70 */     CallableStatement cs = null;
/*    */     try
/*    */     {
/* 74 */       cs = getConnection().prepareCall("{ call DIMENSION_UTILS.ANALYSE_STATS() }");
/* 75 */       cs.execute();
/*    */     }
/*    */     finally
/*    */     {
/* 79 */       closeStatement(cs);
/* 80 */       closeConnection();
/*    */     }
/*    */ 
/* 83 */     if (timer != null)
/* 84 */       timer.logDebug("DimensionUtils.analyseStats");
/*    */   }
/*    */ 
/*    */   public String getEntityName()
/*    */   {
/* 90 */     return "DimensionUtilsDAO";
/*    */   }
/*    */ }

/* Location:           /home/oracle/coa/cp.ear/cp.ear_orginal/cp-server.jar
 * Qualified Name:     com.cedar.cp.ejb.impl.dimension.DimensionUtilsDAO
 * JD-Core Version:    0.6.0
 */