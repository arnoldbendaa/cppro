/*     */ package com.cedar.cp.ejb.impl.model;
/*     */ 
/*     */ import com.cedar.cp.dto.cubeformula.CubeFormulaPK;
/*     */ import com.cedar.cp.dto.cubeformula.CubeFormulaRefImpl;
/*     */ import com.cedar.cp.ejb.impl.base.AbstractDAO;
/*     */ import com.cedar.cp.util.Log;
/*     */ import com.cedar.cp.util.Timer;
/*     */ import java.sql.CallableStatement;
/*     */ import java.sql.Connection;
/*     */ import java.sql.PreparedStatement;
/*     */ import java.sql.ResultSet;
/*     */ import java.sql.SQLException;
/*     */ import java.util.List;
/*     */ import oracle.jdbc.OracleConnection;
/*     */ import oracle.sql.ARRAY;
/*     */ import oracle.sql.ArrayDescriptor;
/*     */ import oracle.sql.DatumWithConnection;
/*     */ import oracle.sql.STRUCT;
/*     */ 
/*     */ public class CubeUpdateDAO extends AbstractDAO
/*     */ {
/*     */   public String getEntityName()
/*     */   {
/*  28 */     return "CubeUpdateDAO";
/*     */   }
/*     */ 
/*     */   public void rebuildFormula(int financeCubeId, List<CubeFormulaRefImpl> cubeFormula, String deltaTableName, String workTableName, boolean tidyWorkTables)
/*     */   {
/*  56 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*  57 */     CallableStatement stmt = null;
/*  58 */     PreparedStatement pstat = null;
/*  59 */     ResultSet resultSet = null;
/*     */     try
/*     */     {
/*  63 */       pstat = getConnection().prepareStatement("select empty_clob() from dual");
/*  64 */       resultSet = pstat.executeQuery();
/*  65 */       resultSet.next();
/*  66 */       DatumWithConnection datum = (DatumWithConnection)resultSet.getClob(1);
/*  67 */       OracleConnection oconn = datum.getOracleConnection();
/*     */ 
/*  70 */       ArrayDescriptor TYPE_tableOfNumber = new ArrayDescriptor("TYPE_TABLE_OF_NUMBER", oconn);
/*     */ 
/*  73 */       STRUCT[] formulaTableObjs = new STRUCT[cubeFormula.size()];
/*  74 */       Object[] ids = new Object[cubeFormula.size()];
/*  75 */       for (int i = 0; i < cubeFormula.size(); i++)
/*     */       {
/*  77 */         ids[i] = Integer.valueOf(((CubeFormulaRefImpl)cubeFormula.get(i)).getCubeFormulaPK().getCubeFormulaId());
/*     */       }
/*     */ 
/*  80 */       ARRAY formulaIdTable = new ARRAY(TYPE_tableOfNumber, oconn, ids);
/*     */ 
/*  82 */       stmt = getConnection().prepareCall("begin cube_update.rebuild_formula(?,?,?,?,?); end;");
/*     */ 
/*  84 */       int paramNo = 1;
/*  85 */       stmt.setInt(paramNo++, financeCubeId);
/*  86 */       stmt.setString(paramNo++, deltaTableName);
/*  87 */       stmt.setString(paramNo++, workTableName);
/*  88 */       stmt.setObject(paramNo++, formulaIdTable);
/*  89 */       stmt.setString(paramNo, tidyWorkTables ? "Y" : "N");
/*     */ 
/*  91 */       stmt.execute();
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/*  96 */       throw handleSQLException("rebuildFormula", sqle);
/*     */     }
/*     */     finally
/*     */     {
/* 100 */       closeResultSet(resultSet);
/* 101 */       closeStatement(pstat);
/* 102 */       closeStatement(stmt);
/* 103 */       closeConnection();
/*     */     }
/*     */ 
/* 106 */     if (timer != null)
/* 107 */       timer.logDebug("rebuildFormula");
/*     */   }
/*     */ }

/* Location:           /home/oracle/coa/cp.ear/cp.ear_orginal/cp-server.jar
 * Qualified Name:     com.cedar.cp.ejb.impl.model.CubeUpdateDAO
 * JD-Core Version:    0.6.0
 */