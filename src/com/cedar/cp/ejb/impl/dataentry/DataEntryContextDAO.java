/*     */ package com.cedar.cp.ejb.impl.dataentry;
/*     */ 
/*     */ import com.cedar.cp.ejb.impl.base.AbstractDAO;
/*     */ import com.cedar.cp.util.performance.PerformanceDatumImpl;
/*     */ import com.cedar.cp.util.xmlform.CalendarInfo;
/*     */ import com.cedar.cp.util.xmlform.FinanceCubeInput;
/*     */ import com.cedar.cp.util.xmlform.FormContext;
/*     */ import com.cedar.cp.util.xmlform.RowInput;
/*     */ import com.cedar.cp.util.xmlform.inputs.FormInputModel;
/*     */ import java.io.PrintStream;
/*     */ import java.sql.Connection;
/*     */ import java.sql.PreparedStatement;
/*     */ import java.sql.ResultSet;
/*     */ import java.sql.SQLException;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ 
/*     */ public class DataEntryContextDAO extends AbstractDAO
/*     */   implements FormContext
/*     */ {
/*  26 */   protected static String SQL_FINANCE_CUBE_FROM_VISID = "select finance_cube_id   from finance_cube  where vis_id = ?";
/*     */   private Map mContextVariables;
/*  34 */   private ContextInputFactory mWrappedFactory = new ContextInputFactory(this);
/*     */ 
/*     */   public DataEntryContextDAO(Connection connection, Map contextVariables)
/*     */   {
/*  38 */     super(connection);
/*  39 */     this.mContextVariables = contextVariables;
/*     */   }
/*     */ 
/*     */   public Map getContextVariables()
/*     */   {
/*  44 */     return this.mContextVariables;
/*     */   }
/*     */ 
/*     */   public void setContextVariables(Map contextVariables)
/*     */   {
/*  49 */     this.mContextVariables = contextVariables;
/*     */   }
/*     */ 
/*     */   public DataEntryContextDAO(Map contextVariables)
/*     */   {
/*  54 */     this.mContextVariables = contextVariables;
/*     */   }
/*     */ 
/*     */   public String getEntityName()
/*     */   {
/*  59 */     return "DataEntryContext";
/*     */   }
/*     */ 
/*     */   public Connection getSqlConnection()
/*     */   {
/*  64 */     return getConnection();
/*     */   }
/*     */ 
/*     */   public void closeSqlConnection()
/*     */   {
/*  69 */     closeConnection();
/*     */   }
/*     */ 
/*     */   public FormInputModel getFormInputModel(PerformanceDatumImpl perf, RowInput config)
/*     */   {
/*  74 */     return this.mWrappedFactory.getFormInputModel(perf, config);
/*     */   }
/*     */ 
/*     */   public Map getLookupInputs(PerformanceDatumImpl perf, List inputs)
/*     */     throws SQLException
/*     */   {
/*  84 */     return this.mWrappedFactory.getLookupInputs(perf, inputs, this.mContextVariables);
/*     */   }
/*     */ 
/*     */   public void putVariable(Object key, Object value)
/*     */   {
/*  89 */     this.mContextVariables.put(key, value);
/*     */   }
/*     */ 
/*     */   public Map getVariables()
/*     */   {
/*  94 */     return this.mContextVariables;
/*     */   }
/*     */ 
/*     */   public int lookupFinanceCubeFromVisId(String visId)
/*     */   {
/*  99 */     int result = -1;
/*     */ 
/* 101 */     PreparedStatement stmt = null;
/* 102 */     ResultSet resultSet = null;
/*     */     try
/*     */     {
/* 105 */       stmt = getConnection().prepareStatement(SQL_FINANCE_CUBE_FROM_VISID);
/* 106 */       int col1 = 1;
/* 107 */       stmt.setString(col1++, visId);
/* 108 */       stmt.executeQuery();
/* 109 */       if (resultSet.next())
/* 110 */         result = resultSet.getInt(1);
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/* 114 */       System.err.println(sqle);
/* 115 */       sqle.printStackTrace();
/* 116 */       throw new RuntimeException(getEntityName() + " lookupFinanceCubeFromVisId", sqle);
/*     */     }
/*     */     finally
/*     */     {
/* 120 */       closeResultSet(resultSet);
/* 121 */       closeStatement(stmt);
/* 122 */       closeConnection();
/*     */     }
/*     */ 
/* 125 */     return result;
/*     */   }
/*     */ 
/*     */   public FormInputModel getFinanceFormDataRows(int userId, FinanceCubeInput config, int budgetCycleId, String contextDataType, int xAxisIndex, int[] structureElementIds, int childrenDepth, boolean secondaryStructure, CalendarInfo calInfo, Map dataTypes, Map securityAccessDetails)
/*     */     throws SQLException
/*     */   {
/* 143 */     return null;
/*     */   }
/*     */ }

/* Location:           /home/oracle/coa/cp.ear/cp.ear_orginal/cp-server.jar
 * Qualified Name:     com.cedar.cp.ejb.impl.dataentry.DataEntryContextDAO
 * JD-Core Version:    0.6.0
 */