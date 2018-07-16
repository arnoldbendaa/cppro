/*     */ package com.cedar.cp.ejb.impl.model;
/*     */ 
/*     */ import com.cedar.cp.ejb.base.common.util.SystemPropertyHelper;
/*     */ import com.cedar.cp.ejb.impl.base.AbstractDAO;
/*     */ import com.cedar.cp.ejb.impl.task.TaskDAO;
/*     */ import com.cedar.cp.util.Log;
/*     */ import com.cedar.cp.util.SqlBuilder;
/*     */ import com.cedar.cp.util.Timer;
/*     */ import java.sql.Connection;
/*     */ import java.sql.PreparedStatement;
/*     */ import java.sql.ResultSet;
/*     */ import java.sql.SQLException;
/*     */ 
/*     */ public class FinanceCubeDDLDAO extends AbstractDAO
/*     */ {
/*     */   private Integer mTaskId;
/*     */   private int mFinanceCubeId;
/*     */   private int mNumDims;
/*     */   private String mSchemaName;
/* 192 */   private Log mLog = new Log(getClass());
/*     */   private TaskDAO mTaskDAO;
/*     */ 
/*     */   public FinanceCubeDDLDAO(Connection conn, int financeCubeId, int numDims)
/*     */   {
/*  21 */     super(conn);
/*  22 */     this.mFinanceCubeId = financeCubeId;
/*  23 */     this.mNumDims = numDims;
/*  24 */     this.mTaskId = null;
/*     */   }
/*     */ 
/*     */   public FinanceCubeDDLDAO(int financeCubeId, int numDims)
/*     */   {
/*  30 */     this.mFinanceCubeId = financeCubeId;
/*  31 */     this.mNumDims = numDims;
/*  32 */     this.mTaskId = null;
/*     */   }
/*     */ 
/*     */   public FinanceCubeDDLDAO(Integer taskId, int financeCubeId, int numDims)
/*     */   {
/*  39 */     this.mTaskId = taskId;
/*  40 */     this.mFinanceCubeId = financeCubeId;
/*  41 */     this.mNumDims = numDims;
/*     */   }
/*     */ 
/*     */   public void createTablesAndViews()
/*     */     throws Exception
/*     */   {
/*  47 */     maintainCubeObject("PFT", " ");
/*  48 */     maintainCubeObject("DFT", " ");
/*  49 */     maintainCubeObject("AFT", " ");
/*  50 */     maintainCubeObject("CFT", " ");
/*  51 */     maintainCubeObject("SFT", " ");
/*  52 */     maintainCubeObject("UFT", " ");
/*  53 */     maintainCubeObject("TX1_", " ");
/*  54 */     maintainCubeObject("TX2_", " ");
/*  55 */     maintainCubeObject("VIEWS", " ");
/*  56 */     maintainCubeObject("FORMULA", " ");
/*     */   }
/*     */ 
/*     */   public void dropTablesAndViews()
/*     */     throws Exception
/*     */   {
/*  62 */     maintainCubeObject("PFT", "Y");
/*  63 */     maintainCubeObject("DFT", "Y");
/*  64 */     maintainCubeObject("AFT", "Y");
/*  65 */     maintainCubeObject("CFT", "Y");
/*  66 */     maintainCubeObject("SFT", "Y");
/*  67 */     maintainCubeObject("UFT", "Y");
/*  68 */     maintainCubeObject("TX1_", "Y");
/*  69 */     maintainCubeObject("TX2_", "Y");
/*  70 */     maintainCubeObject("VIEWS", "Y");
/*  71 */     maintainCubeObject("FORMULA", "Y");
/*     */   }
/*     */ 
/*     */   public boolean cubeExists() throws SQLException
/*     */   {
/*  76 */     Timer t = new Timer(this._log);
/*  77 */     PreparedStatement ps = null;
/*  78 */     ResultSet rs = null;
/*     */     try
/*     */     {
/*  81 */       ps = getConnection().prepareStatement("select MODEL_ID from FINANCE_CUBE where FINANCE_CUBE_ID=?");
/*  82 */       ps.setInt(1, getFinanceCubeId());
/*  83 */       rs = ps.executeQuery();
/*  84 */       boolean bool = rs.next();
/*     */       return bool;
/*     */     }
/*     */     finally
/*     */     {
/*  88 */       closeResultSet(rs);
/*  89 */       closeStatement(ps);
/*  90 */       closeConnection(); } //throw localObject;
/*     */   }
/*     */ 
/*     */   public boolean isCarryBalance(int dataTypeId)
/*     */     throws SQLException
/*     */   {
/* 100 */     SqlBuilder sql = new SqlBuilder(new String[] { "select  1 from dual", "where   exists", "        (", "        select  1", "        from    DFT${financeCubeId}", "        where   DATA_TYPE = (select  VIS_ID", "                             from    DATA_TYPE", "                             where   DATA_TYPE_ID = ?", "                            )", "        )" });
/*     */ 
/* 112 */     sql.substitute(new String[] { "${financeCubeId}", String.valueOf(getFinanceCubeId()) });
/*     */ 
/* 115 */     Timer t = new Timer(this._log);
/* 116 */     PreparedStatement ps = null;
/* 117 */     ResultSet rs = null;
/*     */     try
/*     */     {
/* 120 */       ps = getConnection().prepareStatement(sql.toString());
/* 121 */       ps.setInt(1, dataTypeId);
/* 122 */       rs = ps.executeQuery();
/* 123 */       boolean isCarryBalance = rs.next();
/* 124 */       t.logDebug("isCarryBalance", "=" + isCarryBalance + " [timer=" + t.getElapsed() + "]");
/* 125 */       boolean bool1 = isCarryBalance;
/*     */       return bool1;
/*     */     }
/*     */     finally
/*     */     {
/* 129 */       closeResultSet(rs);
/* 130 */       closeStatement(ps);
/* 131 */       closeConnection(); } //throw localObject;
/*     */   }
/*     */ 

	public void maintainCubeObject(String name, String drop) throws Exception {
		PreparedStatement stmt = null;
		try {
			Connection conn = getConnection();

			stmt = conn.prepareStatement("begin cube_utils.maintainCubeObject(?,?,?,?,?); end;");
			stmt.setString(1, name);
			stmt.setInt(2, this.mTaskId.intValue());
			stmt.setInt(3, this.mFinanceCubeId);
			stmt.setInt(4, this.mNumDims);
			stmt.setString(5, drop);
			stmt.executeUpdate();
		} finally {
			closeStatement(stmt);
			closeConnection();
		}
	}
	
	public void maintainCubeObjectGlobal(String name, String drop) throws Exception {
		PreparedStatement stmt = null;
		try {
			Connection conn = getConnection();

			stmt = conn.prepareStatement("begin cube_utils.maintainCubeObjectGlobal(?,?,?,?,?); end;");
			stmt.setString(1, name);
			stmt.setInt(2, this.mTaskId.intValue());
			stmt.setInt(3, this.mFinanceCubeId);
			stmt.setInt(4, this.mNumDims);
			stmt.setString(5, drop);
			stmt.executeUpdate();
		} finally {
			closeStatement(stmt);
			closeConnection();
		}
	}
	
/*     */ 
/*     */   public String getEntityName()
/*     */   {
/* 158 */     return "FinanceCubeDDL";
/*     */   }
/*     */ 
/*     */   public int getNumDims()
/*     */   {
/* 163 */     return this.mNumDims;
/*     */   }
/*     */ 
/*     */   public void setNumDims(int numDims)
/*     */   {
/* 168 */     this.mNumDims = numDims;
/*     */   }
/*     */ 
/*     */   public int getFinanceCubeId()
/*     */   {
/* 173 */     return this.mFinanceCubeId;
/*     */   }
/*     */ 
/*     */   public void setFinanceCubeId(int financeCubeId)
/*     */   {
/* 178 */     this.mFinanceCubeId = financeCubeId;
/*     */   }
/*     */ 
/*     */   public String getSchemaName()
/*     */   {
/* 183 */     if (this.mSchemaName == null)
/* 184 */       this.mSchemaName = SystemPropertyHelper.queryStringSystemProperty(getConnection(), "SYS: CPSCHEMA", "<noSchemaProperty!>");
/* 185 */     return this.mSchemaName;
/*     */   }
/*     */ }

/* Location:           /home/oracle/coa/cp.ear/cp.ear_orginal/cp-server.jar
 * Qualified Name:     com.cedar.cp.ejb.impl.model.FinanceCubeDDLDAO
 * JD-Core Version:    0.6.0
 */