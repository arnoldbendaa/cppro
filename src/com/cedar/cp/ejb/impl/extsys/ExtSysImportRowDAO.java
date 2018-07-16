/*     */ package com.cedar.cp.ejb.impl.extsys;
/*     */ 
/*     */ import com.cedar.cp.api.base.EntityList;
/*     */ import com.cedar.cp.dto.base.EntityListImpl;
/*     */ import com.cedar.cp.ejb.impl.base.AbstractDAO;
/*     */ import com.cedar.cp.ejb.impl.base.SqlExecutor;
/*     */ import com.cedar.cp.util.SqlBuilder;
/*     */ import com.cedar.cp.util.common.JdbcUtils;
/*     */ import com.cedar.cp.util.common.JdbcUtils.ColType;
/*     */ import java.io.PrintWriter;
/*     */ import java.sql.ResultSet;
/*     */ import java.sql.SQLException;
/*     */ 
/*     */ public class ExtSysImportRowDAO extends AbstractDAO
/*     */ {
/*     */   public String getEntityName()
/*     */   {
/*  17 */     return "ExtSysImportRow";
/*     */   }
/*     */ 
/*     */   public int checkConstraintViolations(int externalSystemId, int maxToReport, PrintWriter log)
/*     */   {
/*  29 */     int violationsFound = 0;
/*     */ 
/*  31 */     if (violationsFound < maxToReport) {
/*  32 */       violationsFound += checkCurrencyConstraintViolations(externalSystemId, maxToReport - violationsFound, log);
/*     */     }
/*     */ 
/*  36 */     if (violationsFound < maxToReport) {
/*  37 */       violationsFound += checkValueTypeConstraintViolations(externalSystemId, maxToReport - violationsFound, log);
/*     */     }
/*     */ 
/*  41 */     EntityList dimDetails = queryDimCounts(externalSystemId);
/*     */ 
/*  43 */     for (int i = 0; i < dimDetails.getNumRows(); i++)
/*     */     {
/*  45 */       String companyVisId = (String)dimDetails.getValueAt(i, "company_vis_id");
/*  46 */       String ledgerVisId = (String)dimDetails.getValueAt(i, "ledger_vis_id");
/*  47 */       int dimCount = ((Integer)dimDetails.getValueAt(i, "dim_count")).intValue();
/*     */ 
/*  49 */       if (violationsFound < maxToReport)
/*     */       {
/*  51 */         for (int dimIndex = 0; (dimIndex < dimCount) && (violationsFound < maxToReport); dimIndex++)
/*     */         {
/*  53 */           violationsFound += checkDimElementConstraintViolations(externalSystemId, companyVisId, ledgerVisId, maxToReport - violationsFound, log, dimIndex);
/*     */         }
/*     */ 
/*     */       }
/*     */ 
/*  62 */       if (violationsFound >= maxToReport)
/*     */         continue;
/*  64 */       violationsFound += checkCalendarYearAndElementConstraintViolations(externalSystemId, maxToReport - violationsFound, log, dimCount);
/*     */     }
/*     */ 
/*  74 */     return violationsFound;
/*     */   }
/*     */ 
/*     */   public EntityList queryDimCounts(int externalSystemId)
/*     */   {
/*  84 */     ResultSet rs = null;
/*  85 */     SqlExecutor sqlExecutor = null;
/*     */     try
/*     */     {
/*  89 */       SqlBuilder sqlBuilder = new SqlBuilder(new String[] { "select company_vis_id, ledger_vis_id, count(*) as dim_count", "from ext_sys_dimension", "where external_system_id = <externalSystemId>", "group by company_vis_id, ledger_vis_id" });
/*     */ 
/*  94 */       sqlExecutor = new SqlExecutor("queryDimCounts", getDataSource(), sqlBuilder, this._log);
/*     */ 
/*  96 */       sqlExecutor.addBindVariable("<externalSystemId>", Integer.valueOf(externalSystemId));
/*     */ 
/*  98 */       JdbcUtils.ColType[] resultSetInfo = { new JdbcUtils.ColType("company_vis_id", 1), new JdbcUtils.ColType("ledger_vis_id", 1), new JdbcUtils.ColType("dim_count", 0) };
/*     */ 
/* 106 */       rs = sqlExecutor.getResultSet();
/*     */ 
/* 108 */       EntityListImpl localEntityListImpl = JdbcUtils.extractToEntityListImpl(resultSetInfo, rs);
/*     */       return localEntityListImpl;
/*     */     }
/*     */     catch (SQLException e)
/*     */     {
/* 112 */       throw handleSQLException("queryDimCounts", e);
/*     */     }
/*     */     finally
/*     */     {
/* 116 */       closeResultSet(rs);
/* 117 */       closeConnection(); } //throw localObject;
/*     */   }
/*     */ 
/*     */   private int checkDimElementConstraintViolations(int externalSystemId, String company, String ledger, int maxToReport, PrintWriter log, int dimIndex)
/*     */   {
/* 128 */     SqlExecutor sqlExecutor = null;
/* 129 */     ResultSet rs = null;
/*     */     try
/*     */     {
/* 133 */       SqlBuilder sqlBuilder = new SqlBuilder(new String[] { "with dim_refs as", "(", "  select distinct", "         external_system_id,", "         company_vis_id,", "         ledger_vis_id,", "         ir.vis_id${dimIndex} as dim_element_vis_id", "  from ext_sys_import_row ir", "  where ir.external_system_id = <externalSystemId> and", "        ir.company_vis_id = <companyVisId> and", "        ir.ledger_vis_id = <ledgerVisId> and ", "        ir.vis_id${dimIndex} is not null", ")", ",dim_elements as ", "(", "  select external_system_id, ", "         company_vis_id, ", "         ledger_vis_id, ", "         dimension_vis_id, ", "         dim_element_vis_id", "  from ext_sys_dimension d", "  join ext_sys_dim_element using", "  (", "    external_system_id, company_vis_id, ledger_vis_id, dimension_vis_id", "  )", "  where external_system_id = <externalSystemId> and", "        company_vis_id = <companyVisId> and ", "        ledger_vis_id = <ledgerVisId> and ", "        d.import_column_index = <dimIndex>", ")", "select r.external_system_id, r.company_vis_id, ", "       r.ledger_vis_id, r.dim_element_vis_id ", "from dim_refs r", "left join dim_elements de on", "( ", "  r.external_system_id = de.external_system_id and ", "  r.company_vis_id = de.company_vis_id and ", "  r.ledger_vis_id = de.ledger_vis_id and", "  r.dim_element_vis_id = de.dim_element_vis_id ", ")", "where de.dim_element_vis_id is null" });
/*     */ 
/* 176 */       sqlBuilder.substitute(new String[] { "${dimIndex}", String.valueOf(dimIndex) });
/*     */ 
/* 178 */       sqlExecutor = new SqlExecutor("checkDimElementConstraintViolations", getDataSource(), sqlBuilder, this._log);
/*     */ 
/* 180 */       sqlExecutor.addBindVariable("<externalSystemId>", Integer.valueOf(externalSystemId));
/* 181 */       sqlExecutor.addBindVariable("<companyVisId>", company);
/* 182 */       sqlExecutor.addBindVariable("<ledgerVisId>", ledger);
/* 183 */       sqlExecutor.addBindVariable("<dimIndex>", Integer.valueOf(dimIndex));
/*     */ 
/* 185 */       rs = sqlExecutor.getResultSet();
/*     */ 
/* 187 */       int count = 0;
/* 188 */       while ((rs.next()) && (count < maxToReport))
/*     */       {
/* 190 */         log.print("Found undefined dimension element reference in import row:  company:[" + rs.getString("COMPANY_VIS_ID") + "]" + " ledger:[" + rs.getString("LEDGER_VIS_ID") + "]" + " dimIndex: [" + dimIndex + "] " + " element:[" + rs.getString("DIM_ELEMENT_VIS_ID") + "]");
/*     */ 
/* 195 */         count++;
/*     */       }
/*     */ 
/* 198 */       int i = count;
/*     */       return i;
/*     */     }
/*     */     catch (SQLException e)
/*     */     {
/* 202 */       throw handleSQLException("checkDimElementConstraintViolations", e);
/*     */     }
/*     */     finally
/*     */     {
/* 206 */       closeResultSet(rs);
/* 207 */       sqlExecutor.close(); } //throw localObject;
/*     */   }
/*     */ 
/*     */   private int checkCurrencyConstraintViolations(int externalSystemId, int maxToReport, PrintWriter log)
/*     */   {
/* 216 */     SqlExecutor sqlExecutor = null;
/* 217 */     ResultSet rs = null;
/*     */     try
/*     */     {
/* 221 */       SqlBuilder sqlBuilder = new SqlBuilder(new String[] { "select distinct external_system_id,", "                company_vis_id,", "                ledger_vis_id,", "                currency_vis_id", "from ext_sys_import_row", "where external_system_id = <externalSystemId>", "and ( external_system_id,", "      company_vis_id,", "      ledger_vis_id,", "      currency_vis_id )", "not in", "(", "  select distinct external_system_id,", "                  company_vis_id,", "                  ledger_vis_id,", "                  currency_vis_id", "  from ext_sys_currency", "  where external_system_id = <externalSystemId>", ")" });
/*     */ 
/* 242 */       sqlExecutor = new SqlExecutor("checkValueTypeConstraintViolations", getDataSource(), sqlBuilder, this._log);
/*     */ 
/* 244 */       sqlExecutor.addBindVariable("<externalSystemId>", Integer.valueOf(externalSystemId));
/*     */ 
/* 246 */       rs = sqlExecutor.getResultSet();
/*     */ 
/* 248 */       int count = 0;
/* 249 */       while ((rs.next()) && (count < maxToReport))
/*     */       {
/* 251 */         log.print("Found undefined currency in import row:  company:[" + rs.getString("COMPANY_VIS_ID") + "] " + " ledger:[" + rs.getString("LEDGER_VIS_ID") + "]" + " currency:[" + rs.getString("CURRENCY_VIS_ID") + "]");
/*     */ 
/* 255 */         count++;
/*     */       }
/*     */ 
/* 258 */       int i = count;
/*     */       return i;
/*     */     }
/*     */     catch (SQLException e)
/*     */     {
/* 262 */       throw handleSQLException("checkValueTypeConstraintViolations", e);
/*     */     }
/*     */     finally
/*     */     {
/* 266 */       closeResultSet(rs);
/* 267 */       sqlExecutor.close(); } //throw localObject;
/*     */   }
/*     */ 
/*     */   private int checkValueTypeConstraintViolations(int externalSystemId, int maxToReport, PrintWriter log)
/*     */   {
/* 276 */     SqlExecutor sqlExecutor = null;
/* 277 */     ResultSet rs = null;
/*     */     try
/*     */     {
/* 281 */       SqlBuilder sqlBuilder = new SqlBuilder(new String[] { "select distinct external_system_id,", "                company_vis_id,", "                ledger_vis_id,", "                value_type_vis_id", "from ext_sys_import_row", "where external_system_id = <externalSystemId>", "and ( external_system_id,", "      company_vis_id,", "      ledger_vis_id,", "      value_type_vis_id )", "not in", "(", "  select distinct external_system_id,", "                  company_vis_id,", "                  ledger_vis_id,", "                  value_type_vis_id", "  from ext_sys_value_type", "  where external_system_id = <externalSystemId>", ")" });
/*     */ 
/* 302 */       sqlExecutor = new SqlExecutor("checkValueTypeConstraintViolations", getDataSource(), sqlBuilder, this._log);
/*     */ 
/* 304 */       sqlExecutor.addBindVariable("<externalSystemId>", Integer.valueOf(externalSystemId));
/*     */ 
/* 306 */       rs = sqlExecutor.getResultSet();
/*     */ 
/* 308 */       int count = 0;
/* 309 */       while ((rs.next()) && (count < maxToReport))
/*     */       {
/* 311 */         log.print("Found undefined value type in import row:  company:[" + rs.getString("COMPANY_VIS_ID") + "] " + " ledger:[" + rs.getString("LEDGER_VIS_ID") + "]" + " value type:[" + rs.getString("VALUE_TYPE_VIS_ID") + "]");
/*     */ 
/* 315 */         count++;
/*     */       }
/*     */ 
/* 318 */       int i = count;
/*     */       return i;
/*     */     }
/*     */     catch (SQLException e)
/*     */     {
/* 322 */       throw handleSQLException("checkValueTypeConstraintViolations", e);
/*     */     }
/*     */     finally
/*     */     {
/* 326 */       closeResultSet(rs);
/* 327 */       sqlExecutor.close(); } //throw localObject;
/*     */   }
/*     */ 
/*     */   private int checkCalendarYearAndElementConstraintViolations(int externalSystemId, int maxToReport, PrintWriter log, int calIndex)
/*     */   {
/* 338 */     SqlExecutor sqlExecutor = null;
/* 339 */     ResultSet rs = null;
/*     */     try
/*     */     {
/* 343 */       SqlBuilder sqlBuilder = new SqlBuilder(new String[] { "with calendar_element_refs as", "(", "  select distinct", "         external_system_id,", "         company_vis_id,", "         ledger_vis_id,", "         calendar_year_vis_id, ", "         ir.vis_id${calIndex} as cal_element_vis_id", "  from ext_sys_import_row ir", "  where ir.external_system_id = <externalSystemId>", ")", ",calendar_elements as ", "(", "  select external_system_id, ", "         company_vis_id, ", "         calendar_year_vis_id,", "         cal_element_vis_id", "  from ext_sys_cal_element d", "  where external_system_id = <externalSystemId>", ")", "select r.external_system_id, r.company_vis_id, r.ledger_vis_id, r.calendar_year_vis_id, r.cal_element_vis_id", "from calendar_element_refs r", "left join calendar_elements cy on", "( ", "  r.external_system_id = cy.external_system_id and ", "  r.company_vis_id = cy.company_vis_id and ", "  r.calendar_year_vis_id = cy.calendar_year_vis_id and", "  r.cal_element_vis_id = cy.cal_element_vis_id", ")", "where cy.calendar_year_vis_id is null" });
/*     */ 
/* 375 */       sqlBuilder.substitute(new String[] { "${calIndex}", String.valueOf(calIndex) });
/*     */ 
/* 377 */       sqlExecutor = new SqlExecutor("checkCalendarYearAndElementConstraintViolations", getDataSource(), sqlBuilder, this._log);
/*     */ 
/* 379 */       sqlExecutor.addBindVariable("<externalSystemId>", Integer.valueOf(externalSystemId));
/*     */ 
/* 381 */       rs = sqlExecutor.getResultSet();
/*     */ 
/* 383 */       int count = 0;
/* 384 */       while ((rs.next()) && (count < maxToReport))
/*     */       {
/* 386 */         log.print("Found undefined calendar year or element in import row: company:[" + rs.getString("COMPANY_VIS_ID") + "] " + " ledger:[" + rs.getString("LEDGER_VIS_ID") + "] " + " calendar year:[" + rs.getString("CALENDAR_YEAR_VIS_ID") + "]" + " element:[" + rs.getString("CAL_ELEMENT_VIS_ID") + "]");
/*     */ 
/* 391 */         count++;
/*     */       }
/*     */ 
/* 394 */       int i = count;
/*     */       return i;
/*     */     }
/*     */     catch (SQLException e)
/*     */     {
/* 398 */       throw handleSQLException("checkCalendarYearAndElementConstraintViolations", e);
/*     */     }
/*     */     finally
/*     */     {
/* 402 */       closeResultSet(rs);
/* 403 */       sqlExecutor.close(); } //throw localObject;
/*     */   }
/*     */ }

/* Location:           /home/oracle/coa/cp.ear/cp.ear_orginal/cp-server.jar
 * Qualified Name:     com.cedar.cp.ejb.impl.extsys.ExtSysImportRowDAO
 * JD-Core Version:    0.6.0
 */