/*      */ package com.cedar.cp.ejb.base.cube.formula;
/*      */ 
/*      */ import java.io.IOException;
import java.io.LineNumberReader;
import java.io.StringReader;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.StringTokenizer;

import javax.sql.DataSource;

import oracle.jdbc.OracleConnection;
import oracle.sql.ARRAY;
import oracle.sql.ArrayDescriptor;
import oracle.sql.DatumWithConnection;

import org.antlr.stringtemplate.StringTemplate;
import org.antlr.stringtemplate.StringTemplateGroup;

import com.cedar.cp.api.base.EntityList;
import com.cedar.cp.api.base.SqlExecutorException;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.cubeformula.CubeFormulaDeploymentValidationException;
import com.cedar.cp.dto.base.EntityListImpl;
import com.cedar.cp.ejb.base.cube.RtCubeUtilsDAO;
//import com.cedar.cp.ejb.base.cube.formula.parser.CubeCellRangeRef;
//import com.cedar.cp.ejb.base.cube.formula.parser.CubeCellRef;
//import com.cedar.cp.ejb.base.cube.formula.parser.Lookup;
import com.cedar.cp.ejb.impl.base.AbstractDAO;
import com.cedar.cp.ejb.impl.base.SqlExecutor;
import com.cedar.cp.ejb.impl.model.ModelDAO;
import com.cedar.cp.util.Log;
import com.cedar.cp.util.SqlBuilder;
import com.cedar.cp.util.common.JdbcUtils;
/*      */ 
/*      */ public class FormulaDAO extends AbstractDAO
/*      */ {
/*  232 */   private JdbcUtils.ColType[] sQUERY_ACTIVE_FORMULA_FOR_FINANCE_CUBE_COLINFO = { new JdbcUtils.ColType("cube_formula_id", 0), new JdbcUtils.ColType("formula_text", 1), new JdbcUtils.ColType("formula_type", 0) };
/*      */   private static final String INSERT_FORMULA_DEPLOYMENT_ROWS_SQL = "insert into fdl<financeCubeId> fdl (formula_deployment_line_id, formula_id)\nselect formula_deployment_line_id, cube_formula_id\nfrom formula_deployment_line \nwhere cube_formula_id = ?";
/*      */   private static final String DEFINE_PACKAGE_CALL_SQL = "{? = call cp_utils.createPackage(?,?,?,?,?)}";
/*      */   private static final String DROP_PACKAGE_CALL_STATEMENT_SQL = "{ call cp_utils.dropPackage( ? ) }";
/* 1422 */   private JdbcUtils.ColType[] sQUERY_CUBE_FORMULA_FOR_SOURCE_GROUP_COLINFO = { new JdbcUtils.ColType("formula_id", 0), new JdbcUtils.ColType("formula_text", 1), new JdbcUtils.ColType("formula_type", 0) };
/*      */   private static final String sQUERY_CUBE_FORMULAE_FOR_SOURCE_GROUP = "select fr.formula_id, cf.formula_text, cf.formula_type\nfrom for${financeCubeId} fr\njoin cube_formula cf on ( cf.cube_formula_id = fr.formula_id and cf.finance_cube_id = ? )\nwhere formula_package_index = ?";
/*      */   private ModelDAO mModelDAO;
/*      */   private RtCubeUtilsDAO mRtCubeUtilsDAO;
/*      */   private StringTemplateGroup mFormulaDAOSTG;
/* 1797 */   private Log mLog = new Log(FormulaDAO.class);
/*      */ 
/*      */   public FormulaDAO()
/*      */   {
/*      */   }
/*      */ 
/*      */   public FormulaDAO(Connection connection)
/*      */   {
/*   39 */     super(connection);
/*      */   }
/*      */ 
/*      */   public FormulaDAO(DataSource ds)
/*      */   {
/*   44 */     super(ds);
/*      */   }
/*      */ 
/*      */   public String getEntityName()
/*      */   {
/*   49 */     return "FormulaDAO";
/*      */   }
/*      */ 
/*      */   private int queryNextFormulaId(int financeCubeId)
/*      */     throws Exception
/*      */   {
/*   61 */     PreparedStatement ps = null;
/*   62 */     ResultSet rs = null;
/*      */     try
/*      */     {
/*   66 */       ps = getConnection().prepareStatement(new StringBuilder().append("select max(formula_id) from for").append(financeCubeId).toString());
/*      */ 
/*   68 */       rs = ps.executeQuery();
/*      */ 
/*   70 */       if (rs.next()) {
/*   71 */         int i = rs.getInt(1) + 1;
/*      */         return i;
/*      */       }
/*   73 */       throw new Exception("Unable to query next formulaId");
/*      */     }
/*      */     catch (SQLException e)
/*      */     {
/*   77 */       throw handleSQLException("queryNextFormulaId", e);
/*      */     }
/*      */     finally
/*      */     {
/*   81 */       closeResultSet(rs);
/*   82 */       closeStatement(ps);
/*   83 */       closeConnection(); } //throw localObject;
/*      */   }
/*      */ 
/*      */   public Set<String> queryDeploymentDataTypes(int formulaId)
/*      */   {
/*   95 */     PreparedStatement ps = null;
/*   96 */     ResultSet rs = null;
/*      */     try
/*      */     {
/*  100 */       ps = getConnection().prepareStatement("select distinct data_type.vis_id\nfrom formula_deployment_line fdl\njoin formula_deployment_dt fddt using( formula_deployment_line_id )\njoin data_type using( data_type_id )\nwhere cube_formula_id = ?");
/*      */ 
/*  107 */       ps.setInt(1, formulaId);
/*      */ 
/*  109 */       rs = ps.executeQuery();
/*      */ 
/*  111 */       Set deployedDataTypes = new HashSet();
/*  112 */       while (rs.next())
/*  113 */         deployedDataTypes.add(rs.getString("vis_id"));
/*  114 */       Set localSet1 = deployedDataTypes;
/*      */       return localSet1;
/*      */     }
/*      */     catch (SQLException e)
/*      */     {
/*  118 */       throw handleSQLException("queryDeploymentDataTypes", e);
/*      */     }
/*      */     finally
/*      */     {
/*  122 */       closeResultSet(rs);
/*  123 */       closeStatement(ps);
/*  124 */       closeConnection(); } //throw localObject;
/*      */   }
/*      */ 
/*      */   private void insertFormula(int financeCubeId, int formulaId, int formulaType, int packageIndex, int packageId)
/*      */     throws Exception
/*      */   {
/*  141 */     this.mLog.info("insertFormula", new StringBuilder().append("formulaId=").append(formulaId).toString());
/*  142 */     PreparedStatement ps = null;
/*      */     try
/*      */     {
/*  146 */       ps = getConnection().prepareStatement(new StringBuilder().append("insert into for").append(financeCubeId).append(" (formula_id,formula_type,formula_package_index,formula_package_id) ").append(" values(?,?,?,?)").toString());
/*      */ 
/*  149 */       ps.setInt(1, formulaId);
/*  150 */       ps.setInt(2, formulaType);
/*  151 */       ps.setInt(3, packageIndex);
/*  152 */       ps.setInt(4, packageId);
/*      */ 
/*  154 */       ps.executeUpdate();
/*      */     }
/*      */     catch (SQLException e)
/*      */     {
/*  158 */       throw handleSQLException("queryNextFormulaId", e);
/*      */     }
/*      */     finally
/*      */     {
/*  162 */       closeStatement(ps);
/*  163 */       closeConnection();
/*      */     }
/*      */   }
/*      */ 
/*      */   public void updateFormula(int financeCubeId, int formulaId, int packageIndex, int packageId)
/*      */   {
/*  177 */     this.mLog.info("updateFormula", new StringBuilder().append("formulaId=").append(formulaId).toString());
/*  178 */     PreparedStatement ps = null;
/*      */     try
/*      */     {
/*  182 */       ps = getConnection().prepareStatement(new StringBuilder().append("update for").append(financeCubeId).append(" ").append("set formula_package_index = ?, formula_package_id = ? ").append("where formula_id = ?").toString());
/*      */ 
/*  185 */       ps.setInt(1, packageIndex);
/*  186 */       ps.setInt(2, packageId);
/*  187 */       ps.setInt(3, formulaId);
/*  188 */       ps.executeUpdate();
/*      */     }
/*      */     catch (SQLException e)
/*      */     {
/*  192 */       throw handleSQLException("updateFormula", e);
/*      */     }
/*      */     finally
/*      */     {
/*  196 */       closeStatement(ps);
/*  197 */       closeConnection();
/*      */     }
/*      */   }
/*      */ 
/*      */   public List<Integer> queryDeployedFormula(int financeCubeId)
/*      */   {
/*  209 */     PreparedStatement ps = null;
/*  210 */     ResultSet rs = null;
/*      */     try
/*      */     {
/*  213 */       ps = getConnection().prepareStatement(new StringBuilder().append("select formula_id from for").append(financeCubeId).toString());
/*  214 */       rs = ps.executeQuery();
/*  215 */       List result = new ArrayList();
/*  216 */       while (rs.next())
/*  217 */         result.add(Integer.valueOf(rs.getInt("formula_id")));
/*  218 */       List localList1 = result;
/*      */       return localList1;
/*      */     }
/*      */     catch (SQLException e)
/*      */     {
/*  222 */       throw handleSQLException("queryDeployedFormula", e);
/*      */     }
/*      */     finally
/*      */     {
/*  226 */       closeResultSet(rs);
/*  227 */       closeStatement(ps);
/*  228 */       closeConnection(); } //throw localObject;
/*      */   }
/*      */ 
/*      */   public EntityList queryActiveFormula(int financeCubeId)
/*      */   {
/*  247 */     PreparedStatement ps = null;
/*  248 */     ResultSet rs = null;
/*      */     try
/*      */     {
/*  251 */       ps = getConnection().prepareStatement("select cube_formula_id, formula_text, formula_type from cube_formula where finance_cube_id = ? and deployment_ind = 'Y'");
/*      */ 
/*  255 */       ps.setInt(1, financeCubeId);
/*  256 */       rs = ps.executeQuery();
/*  257 */       EntityListImpl localEntityListImpl = JdbcUtils.extractToEntityListImpl(this.sQUERY_ACTIVE_FORMULA_FOR_FINANCE_CUBE_COLINFO, rs);
/*      */       return localEntityListImpl;
/*      */     }
/*      */     catch (SQLException e)
/*      */     {
/*  261 */       throw handleSQLException("updateFormula", e);
/*      */     }
/*      */     finally
/*      */     {
/*  265 */       closeResultSet(rs);
/*  266 */       closeStatement(ps);
/*  267 */       closeConnection(); } //throw localObject;
/*      */   }
/*      */ 
/*      */   public Set<Integer> queryFormulaForPackageId(int financeCubeId, int packageId)
/*      */   {
/*  279 */     PreparedStatement ps = null;
/*  280 */     ResultSet rs = null;
/*      */     try
/*      */     {
/*  283 */       ps = getConnection().prepareStatement(new StringBuilder().append("select formula_id from for").append(financeCubeId).append(" where formula_package_id = ?").toString());
/*  284 */       ps.setInt(1, packageId);
/*  285 */       rs = ps.executeQuery();
/*  286 */       Set formula = new HashSet();
/*  287 */       if (rs.next())
/*  288 */         formula.add(Integer.valueOf(rs.getInt("formula_id")));
/*  289 */       Set localSet1 = formula;
/*      */       return localSet1;
/*      */     }
/*      */     catch (SQLException e)
/*      */     {
/*  293 */       throw handleSQLException("updateFormula", e);
/*      */     }
/*      */     finally
/*      */     {
/*  297 */       closeResultSet(rs);
/*  298 */       closeStatement(ps);
/*  299 */       closeConnection(); } //throw localObject;
/*      */   }
/*      */ 
/*      */   public int queryFormulaPackageId(int financeCubeId, int cubeFormulaId)
/*      */   {
/*  312 */     PreparedStatement ps = null;
/*  313 */     ResultSet rs = null;
/*      */     try
/*      */     {
/*  316 */       ps = getConnection().prepareStatement(new StringBuilder().append("select formula_package_id from for").append(financeCubeId).append(" where formula_id = ?").toString());
/*  317 */       ps.setInt(1, cubeFormulaId);
/*  318 */       rs = ps.executeQuery();
/*      */ 
/*  320 */       if (rs.next()) {
/*  321 */         return rs.getInt("formula_package_id");
/*      */       }
/*  323 */       int i = -1;
/*      */       return i;
/*      */     }
/*      */     catch (SQLException e)
/*      */     {
/*  327 */       throw handleSQLException("updateFormula", e);
/*      */     }
/*      */     finally
/*      */     {
/*  331 */       closeResultSet(rs);
/*  332 */       closeStatement(ps);
/*  333 */       closeConnection(); } //throw localObject;
/*      */   }
/*      */ 
/*      */   private void deleteFormulaDeploymentLines(int financeCubeId, int formulaId)
/*      */   {
/*  349 */     PreparedStatement ps = null;
/*      */     try
/*      */     {
/*  352 */       ps = getConnection().prepareStatement(new StringBuilder().append("delete from fdl").append(financeCubeId).append(" where formula_id = ?").toString());
/*  353 */       ps.setInt(1, formulaId);
/*  354 */       ps.executeUpdate();
/*      */     }
/*      */     catch (SQLException e)
/*      */     {
/*  358 */       throw handleSQLException("insertDeployment", e);
/*      */     }
/*      */     finally
/*      */     {
/*  362 */       closeStatement(ps);
/*  363 */       closeConnection();
/*      */     }
/*      */   }
/*      */ 
/*      */   private void insertFormulaDeploymentLines(int financeCubeId, int formulaId)
/*      */   {
/*  380 */     PreparedStatement ps = null;
/*      */     try
/*      */     {
/*  383 */       StringTemplate st = new StringTemplate("insert into fdl<financeCubeId> fdl (formula_deployment_line_id, formula_id)\nselect formula_deployment_line_id, cube_formula_id\nfrom formula_deployment_line \nwhere cube_formula_id = ?");
/*  384 */       st.reset();
/*  385 */       st.setAttribute("financeCubeId", financeCubeId);
/*  386 */       ps = getConnection().prepareStatement(st.toString());
/*  387 */       ps.setInt(1, formulaId);
/*  388 */       ps.executeUpdate();
/*      */     }
/*      */     catch (SQLException e)
/*      */     {
/*  392 */       throw handleSQLException("insertFormulaDeploymentLines", e);
/*      */     }
/*      */     finally
/*      */     {
/*  396 */       closeStatement(ps);
/*  397 */       closeConnection();
/*      */     }
/*      */   }
/*      */ 
/*      */   private void deleteFormulaDeploymentLineEntrys(int financeCubeId, int formulaId)
/*      */   {
/*  408 */     PreparedStatement ps = null;
/*      */     try
/*      */     {
/*  411 */       StringTemplate st = new StringTemplate("delete from fdle<financeCubeId> where formula_id = ?");
/*  412 */       st.reset();
/*  413 */       st.setAttribute("financeCubeId", financeCubeId);
/*  414 */       ps = getConnection().prepareStatement(st.toString());
/*  415 */       ps.setInt(1, formulaId);
/*  416 */       ps.executeUpdate();
/*      */     }
/*      */     catch (SQLException e)
/*      */     {
/*  420 */       throw handleSQLException("insertDeployment", e);
/*      */     }
/*      */     finally
/*      */     {
/*  424 */       closeStatement(ps);
/*  425 */       closeConnection();
/*      */     }
/*      */   }
/*      */ 
/*      */   private void insertFormulaDeployments(int financeCubeId, int formulaId)
/*      */     throws ValidationException
/*      */   {
/*  438 */     SqlExecutor executor = null;
/*      */     try
/*      */     {
/*  443 */       SqlBuilder sqlBuilder = new SqlBuilder(new String[] { "insert into fdl${financeCubeId}( formula_id, formula_deployment_line_id )", "select cube_formula_id, formula_deployment_line_id", "from formula_deployment_line fdl", "where cube_formula_id = <cubeFormulaId>" });
/*      */ 
/*  449 */       sqlBuilder.substitute(new String[] { "${financeCubeId}", String.valueOf(financeCubeId) });
/*      */ 
/*  451 */       executor = new SqlExecutor("insert runtime deployment data types", getConnection(), sqlBuilder, this.mLog);
/*      */ 
/*  453 */       executor.addBindVariable("<cubeFormulaId>", Integer.valueOf(formulaId));
/*      */ 
/*  455 */       executor.executeUpdate();
/*      */     }
/*      */     finally
/*      */     {
/*  459 */       if (executor != null)
/*  460 */         executor.close();
/*  461 */       closeConnection();
/*      */     }
/*      */ 
/*      */     try
/*      */     {
/*  472 */       StringTemplate insertDeploymentLinesTemplate = getTemplate("insert_deployment_lines");
/*  473 */       insertDeploymentLinesTemplate.reset();
/*  474 */       insertDeploymentLinesTemplate.setAttribute("financeCubeId", financeCubeId);
/*  475 */       SqlBuilder sqlBuilder = new SqlBuilder(queryLines(insertDeploymentLinesTemplate.toString()));
/*  476 */       executor = new SqlExecutor("insertFormulaDeployments", getConnection(), sqlBuilder, this.mLog);
/*  477 */       executor.addBindVariable("<cubeFormulaId>", Integer.valueOf(formulaId));
/*  478 */       executor.executeUpdate();
/*      */     }
/*      */     finally
/*      */     {
/*  482 */       if (executor != null)
/*  483 */         executor.close();
/*  484 */       closeConnection();
/*      */     }
/*      */ 
/*      */     try
/*      */     {
/*  490 */       SqlBuilder sqlBuilder = new SqlBuilder(new String[] { "insert into fdld${financeCubeId}", "select formula_deployment_line_id, dt.vis_id as data_type", "from formula_deployment_line fdl", "join formula_deployment_dt fdd using( formula_deployment_line_id )", "join data_type dt using( data_type_id )", "where cube_formula_id = <cubeFormulaId>" });
/*      */ 
/*  498 */       sqlBuilder.substitute(new String[] { "${financeCubeId}", String.valueOf(financeCubeId) });
/*      */ 
/*  500 */       executor = new SqlExecutor("insert runtime deployment data types", getConnection(), sqlBuilder, this.mLog);
/*      */ 
/*  502 */       executor.addBindVariable("<cubeFormulaId>", Integer.valueOf(formulaId));
/*      */ 
/*  504 */       executor.executeUpdate();
/*      */     }
/*      */     finally
/*      */     {
/*  508 */       if (executor != null)
/*  509 */         executor.close();
/*  510 */       closeConnection();
/*      */     }
/*      */   }
/*      */ 
/*      */   private void deleteRuntimeDeployments(int financeCubeId, int formulaId)
/*      */   {
/*  522 */     PreparedStatement ps = null;
/*      */     try
/*      */     {
/*  526 */       ps = getConnection().prepareStatement(new StringBuilder().append("delete from fdle").append(financeCubeId).append(" ").append("where formula_deployment_line_id in ").append("( select formula_deployment_line_id from fdl").append(financeCubeId).append(" where formula_id = ? )").toString());
/*      */ 
/*  530 */       ps.setInt(1, formulaId);
/*  531 */       ps.executeUpdate();
/*      */     }
/*      */     catch (SQLException e)
/*      */     {
/*  535 */       throw handleSQLException("deleteRuntimeDeployments(ii)", e);
/*      */     }
/*      */     finally
/*      */     {
/*  539 */       closeStatement(ps);
/*  540 */       closeConnection();
/*      */     }
/*      */ 
/*      */     try
/*      */     {
/*  545 */       ps = getConnection().prepareStatement(new StringBuilder().append("delete from fdld").append(financeCubeId).append(" ").append("where formula_deployment_line_id in ").append("( select formula_deployment_line_id from fdl").append(financeCubeId).append(" where formula_id = ? )").toString());
/*      */ 
/*  549 */       ps.setInt(1, formulaId);
/*  550 */       ps.executeUpdate();
/*      */     }
/*      */     catch (SQLException e)
/*      */     {
/*  554 */       throw handleSQLException("deleteRuntimeDeployments(iii)", e);
/*      */     }
/*      */     finally
/*      */     {
/*  558 */       closeStatement(ps);
/*  559 */       closeConnection();
/*      */     }
/*      */ 
/*      */     try
/*      */     {
/*  564 */       ps = getConnection().prepareStatement(new StringBuilder().append("delete from fdl").append(financeCubeId).append(" where formula_id = ?").toString());
/*  565 */       ps.setInt(1, formulaId);
/*  566 */       ps.executeUpdate();
/*      */     }
/*      */     catch (SQLException e)
/*      */     {
/*  570 */       throw handleSQLException("deleteRuntimeDeployments(i)", e);
/*      */     }
/*      */     finally
/*      */     {
/*  574 */       closeStatement(ps);
/*  575 */       closeConnection();
/*      */     }
/*      */   }
/*      */ 
///*      */   private void insertCellReferences(int financeCubeId, List<CubeCellRef> cellRefs, int numDims, int formulaId, int calHierId)
///*      */     throws Exception
///*      */   {
///*  584 */     int id = 0;
///*      */ 
///*  607 */     PreparedStatement ps = null;
///*      */     try
///*      */     {
/////*  612 */       for (CubeCellRef cubeCellRef : cellRefs)
/////*      */       {
/////*  614 */         CubeCellRef.ElementReference[] elementReference = cubeCellRef.getElementReference();
/////*      */ 
/////*  616 */         for (int i = 0; i < elementReference.length; i++)
/////*      */         {
/////*  618 */           if ((elementReference[i] == null) || (elementReference[i].getElementName() == null))
/////*      */             continue;
/////*  620 */           if (i == elementReference.length - 1)
/////*  621 */             elementReference[i].setElementIds(queryCalendarElementId(calHierId, elementReference[i].getElementName()));
/////*      */           else {
/////*  623 */             elementReference[i].setElementIds(queryDimensionElementId(financeCubeId, elementReference[i].getElementName()));
/////*      */           }
/////*      */         }
/////*      */ 
/////*      */       }
///*      */ 
///*  629 */       ps = getConnection().prepareStatement(new StringBuilder().append("insert into fcr").append(financeCubeId).append(" values( ?, ?, ").append(repeat("?, ?", numDims, ",")).append(", ?, ?, ?, ? )").toString());
///*      */ 
///*  631 */       for (CubeCellRef cubeCellRef : cellRefs)
///*      */       {
///*  633 */         int paramNo = 1;
///*  634 */         ps.setInt(paramNo++, id);
///*  635 */         ps.setInt(paramNo++, formulaId);
///*      */ 
///*  637 */         cubeCellRef.setFormulaId(formulaId);
///*  638 */         cubeCellRef.setId(id);
///*      */ 
///*  640 */         CubeCellRef.ElementReference[] elementReference = cubeCellRef.getElementReference();
///*      */ 
///*  642 */         for (int i = 0; i < elementReference.length; i++)
///*      */         {
///*  644 */           if ((elementReference[i] != null) && (elementReference[i].getElementName() != null))
///*      */           {
///*  646 */             int[] ids = elementReference[i].getElementIds();
///*  647 */             if (elementReference[i].isDateElement())
///*      */             {
///*  649 */               ps.setInt(paramNo++, ids[1]);
///*  650 */               ps.setInt(paramNo++, ids[2]);
///*      */             }
///*      */             else
///*      */             {
///*  654 */               ps.setInt(paramNo++, ids[0]);
///*  655 */               ps.setInt(paramNo++, ids[1]);
///*      */             }
///*      */           }
///*      */           else
///*      */           {
///*  660 */             ps.setNull(paramNo++, 4);
///*  661 */             ps.setNull(paramNo++, 4);
///*      */           }
///*      */         }
///*      */ 
///*  665 */         if (cubeCellRef.getDataType() != null)
///*  666 */           ps.setString(paramNo++, cubeCellRef.getDataType());
///*      */         else {
///*  668 */           ps.setNull(paramNo++, 12);
///*      */         }
///*  670 */         ps.setString(paramNo++, cubeCellRef.isYtd() ? "Y" : "N");
///*  671 */         ps.setInt(paramNo++, cubeCellRef.getYearOffset());
///*  672 */         ps.setInt(paramNo++, cubeCellRef.getMonthOffset());
///*      */ 
///*  674 */         ps.addBatch();
///*      */ 
///*  676 */         id++;
///*      */       }
///*      */ 
///*  679 */       int[] resultCodes = ps.executeBatch();
///*      */ 
///*  681 */       for (int code : resultCodes) {
///*  682 */         if (code == -3)
///*  683 */           throw new SQLException(new StringBuilder().append("Batch operation failed:").append(code).toString());
///*      */       }
///*      */     }
///*      */     catch (SQLException e)
///*      */     {
///*  688 */       throw handleSQLException("insertCellReferences", e);
///*      */     }
///*      */     finally
///*      */     {
///*  692 */       closeStatement(ps);
///*  693 */       closeConnection();
///*      */     }
///*      */   }
/*      */ 
/*      */   private void deleteCellReferences(int financeCubeId, int formulaId)
/*      */   {
/*  700 */     PreparedStatement ps = null;
/*      */     try
/*      */     {
/*  703 */       ps = getConnection().prepareStatement(new StringBuilder().append("delete from fcr").append(financeCubeId).append(" where formula_id = ?").toString());
/*  704 */       ps.setInt(1, formulaId);
/*  705 */       ps.executeUpdate();
/*      */     }
/*      */     catch (SQLException e)
/*      */     {
/*  709 */       throw handleSQLException("deleteCellReferences", e);
/*      */     }
/*      */     finally
/*      */     {
/*  713 */       closeStatement(ps);
/*  714 */       closeConnection();
/*      */     }
/*      */   }
/*      */ 
///*      */   private void insertCellRangeReferences(int financeCubeId, List<CubeCellRangeRef> cellRangeRefs, int numDims, int formulaId)
///*      */     throws Exception
///*      */   {
///*  731 */     int id = 0;
///*      */ 
///*  733 */     if (cellRangeRefs.isEmpty()) {
///*  734 */       return;
///*      */     }
///*      */ 
///*  757 */     PreparedStatement ps = null;
///*      */     try
///*      */     {
///*  760 */       for (CubeCellRangeRef cubeCellRangeRef : cellRangeRefs)
///*      */       {
///*  762 */         cubeCellRangeRef.setFormulaId(formulaId);
///*  763 */         cubeCellRangeRef.setId(id);
///*      */ 
///*  765 */         CubeCellRangeRef.ElementReference[] seReference = cubeCellRangeRef.getElementReference();
///*      */ 
///*  768 */         for (int i = 0; i < seReference.length; i++)
///*      */         {
///*  770 */           if ((seReference[i] == null) || (seReference[i].getStructure() == null))
///*      */             continue;
///*      */           int[] ids;
///*  774 */           if (i == seReference.length - 1) {
///*  775 */             ids = queryCalendarElementId(financeCubeId, seReference[i].getStructure(), seReference[i].getStartElement());
///*      */           }
///*      */           else {
///*  778 */             ids = queryStructureElementId(financeCubeId, seReference[i].getStructure(), seReference[i].getStartElement());
///*      */           }
///*      */ 
///*  781 */           seReference[i].setStructureId(ids[0]);
///*  782 */           seReference[i].setStartElementIds(ids);
///*      */ 
///*  784 */           if (seReference[i].getEndElement() == null)
///*      */             continue;
///*  786 */           if (i == seReference.length - 1) {
///*  787 */             ids = queryCalendarElementId(financeCubeId, seReference[i].getStructure(), seReference[i].getEndElement());
///*      */           }
///*      */           else {
///*  790 */             ids = queryStructureElementId(financeCubeId, seReference[i].getStructure(), seReference[i].getEndElement());
///*      */           }
///*      */ 
///*  793 */           seReference[i].setEndElementIds(ids);
///*      */         }
///*      */ 
///*      */       }
///*      */ 
///*  799 */       ps = getConnection().prepareStatement(new StringBuilder().append("insert into frr").append(financeCubeId).append(" values( ?, ?, ").append(repeat("?, ?, ?, ?", numDims, ",")).append(", ?, ?, ?, ?, ? )").toString());
///*      */ 
///*  802 */       for (CubeCellRangeRef cubeCellRangeRef : cellRangeRefs)
///*      */       {
///*  804 */         int paramNo = 1;
///*  805 */         ps.setInt(paramNo++, id);
///*  806 */         ps.setInt(paramNo++, formulaId);
///*      */ 
///*  808 */         cubeCellRangeRef.setFormulaId(formulaId);
///*  809 */         cubeCellRangeRef.setId(id);
///*      */ 
///*  811 */         CubeCellRangeRef.ElementReference[] seReference = cubeCellRangeRef.getElementReference();
///*      */ 
///*  813 */         for (int i = 0; i < seReference.length; i++)
///*      */         {
///*  815 */           if ((seReference[i] != null) && (seReference[i].getStructure() != null))
///*      */           {
///*  817 */             int calStartLeafPosition = 0;
///*  818 */             int[] ids = seReference[i].getStartElementIds();
///*      */ 
///*  820 */             ps.setInt(paramNo++, ids[0]);
///*      */ 
///*  822 */             ps.setInt(paramNo++, ids[1]);
///*  823 */             ps.setInt(paramNo++, ids[2]);
///*      */ 
///*  825 */             int endPos = seReference[i].getEndElement() != null ? seReference[i].getEndElementIds()[3] : ids[3];
///*      */ 
///*  827 */             ps.setInt(paramNo++, endPos);
///*      */ 
///*  829 */             if (i == seReference.length - 1)
///*      */             {
///*  831 */               calStartLeafPosition = ids[4];
///*  832 */               ps.setInt(paramNo++, calStartLeafPosition);
///*      */             }
///*      */           }
///*      */           else
///*      */           {
///*  837 */             ps.setNull(paramNo++, 4);
///*  838 */             ps.setNull(paramNo++, 4);
///*  839 */             ps.setNull(paramNo++, 4);
///*  840 */             ps.setNull(paramNo++, 4);
///*  841 */             if (i == seReference.length - 1) {
///*  842 */               ps.setNull(paramNo++, 4);
///*      */             }
///*      */           }
///*      */         }
///*      */ 
///*  847 */         if (cubeCellRangeRef.getDataType() != null)
///*  848 */           ps.setString(paramNo++, cubeCellRangeRef.getDataType());
///*      */         else {
///*  850 */           ps.setNull(paramNo++, 12);
///*      */         }
///*  852 */         ps.setString(paramNo++, cubeCellRangeRef.isYtd() ? "Y" : "N");
///*  853 */         ps.setInt(paramNo++, cubeCellRangeRef.getYearOffset());
///*  854 */         ps.setInt(paramNo++, cubeCellRangeRef.getMonthOffset());
///*      */ 
///*  856 */         ps.addBatch();
///*      */ 
///*  858 */         id++;
///*      */       }
///*      */ 
///*  861 */       int[] resultCodes = ps.executeBatch();
///*      */ 
///*  863 */       for (int resultCode : resultCodes)
///*  864 */         if (resultCode == -3)
///*  865 */           throw new SQLException(new StringBuilder().append("Batch operation faild:").append(resultCode).toString());
///*      */     }
///*      */     catch (SQLException e)
///*      */     {
///*  869 */       throw handleSQLException("insertCellRangeReferences", e);
///*      */     }
///*      */     finally
///*      */     {
///*  873 */       closeStatement(ps);
///*  874 */       closeConnection();
///*      */     }
///*      */   }
///*      */ 
/*      */   private void deleteCellRangeReferences(int financeCubeId, int formulaId)
/*      */   {
/*  881 */     PreparedStatement ps = null;
/*      */     try
/*      */     {
/*  884 */       ps = getConnection().prepareStatement(new StringBuilder().append("delete from frr").append(financeCubeId).append(" where formula_id = ?").toString());
/*  885 */       ps.setInt(1, formulaId);
/*  886 */       ps.executeUpdate();
/*      */     }
/*      */     catch (SQLException e)
/*      */     {
/*  890 */       throw handleSQLException("deleteCellRangeReferences", e);
/*      */     }
/*      */     finally
/*      */     {
/*  894 */       closeStatement(ps);
/*  895 */       closeConnection();
/*      */     }
/*      */   }
/*      */ 
/*      */   public void removeFormula(int financeCubeId, int formulaId)
/*      */     throws ValidationException
/*      */   {
/*  907 */     deleteFormula(financeCubeId, formulaId);
/*  908 */     deleteRuntimeDeployments(financeCubeId, formulaId);
/*  909 */     deleteCellReferences(financeCubeId, formulaId);
/*  910 */     deleteCellRangeReferences(financeCubeId, formulaId);
/*  911 */     deleteLookupTableReferences(financeCubeId, formulaId);
/*      */ 
/*  913 */     updateRtCubeDeployments(-1, financeCubeId, formulaId, RtCubeUtilsDAO.RT_CUBE_CUBE_FORMULA_TYPE);
/*      */   }
/*      */ 
///*      */   public void persistFormula(int modelId, int financeCubeId, int formulaId, int formulaType, int numDims, int calHierId, int packageIndex, List<CubeCellRef> cellRefs, List<CubeCellRangeRef> cellRangeRefs, List<Lookup> lookupRefs)
///*      */     throws ValidationException, Exception
///*      */   {
///*  938 */     int packageId = queryFormulaPackageId(financeCubeId, formulaId);
///*  939 */     deleteFormula(financeCubeId, formulaId);
///*  940 */     deleteRuntimeDeployments(financeCubeId, formulaId);
///*  941 */     deleteCellReferences(financeCubeId, formulaId);
///*  942 */     deleteCellRangeReferences(financeCubeId, formulaId);
///*  943 */     deleteLookupTableReferences(financeCubeId, formulaId);
///*      */ 
///*  946 */     insertFormula(financeCubeId, formulaId, formulaType, packageIndex, packageId);
///*      */ 
///*  949 */     insertFormulaDeployments(financeCubeId, formulaId);
///*      */ 
///*  952 */     insertCellReferences(financeCubeId, cellRefs, numDims, formulaId, calHierId);
///*      */ 
///*  955 */     insertCellRangeReferences(financeCubeId, cellRangeRefs, numDims, formulaId);
///*      */ 
///*  958 */     insertLookupTableReferences(financeCubeId, lookupRefs, formulaId);
///*      */ 
///*  961 */     updateRtCubeDeployments(-1, financeCubeId, formulaId, RtCubeUtilsDAO.RT_CUBE_CUBE_FORMULA_TYPE);
///*      */ 
///*  963 */     EntityList overlaps = getRtCubeUtilsDAO().queryDeploymentIntersections(modelId, formulaId);
///*  964 */     if (overlaps.getNumRows() > 0)
///*  965 */       throw new CubeFormulaDeploymentValidationException(overlaps);
///*      */   }
///*      */ 
///*      */   private void insertLookupTableReferences(int financeCubeId, List<Lookup> lookupRefs, int formulaId)
///*      */   {
///*  976 */     if (lookupRefs.isEmpty()) {
///*  977 */       return;
///*      */     }
///*  979 */     PreparedStatement ps = null;
///*      */     try
///*      */     {
///*  984 */       ps = getConnection().prepareStatement(new StringBuilder().append("insert into flr").append(financeCubeId).append("( lookup_reference_id, formula_id, lookup_table )").append(" values ( ?, ?, ? )").toString());
///*      */ 
///*  988 */       for (int i = 0; i < lookupRefs.size(); i++)
///*      */       {
///*  990 */         Lookup lookup = (Lookup)lookupRefs.get(i);
///*  991 */         ps.setInt(1, i);
///*  992 */         ps.setInt(2, formulaId);
///*  993 */         ps.setString(3, lookup.getSqlTableName());
///*  994 */         ps.addBatch();
///*      */       }
///*      */ 
///*  997 */       ps.executeBatch();
///*      */     }
///*      */     catch (SQLException e)
///*      */     {
///* 1002 */       throw handleSQLException("deleteFormula", e);
///*      */     }
///*      */     finally
///*      */     {
///* 1006 */       closeStatement(ps);
///* 1007 */       closeConnection();
///*      */     }
///*      */   }
/*      */ 
/*      */   private void deleteLookupTableReferences(int financeCubeId, int formulaId)
/*      */   {
/* 1018 */     PreparedStatement ps = null;
/*      */     try
/*      */     {
/* 1021 */       ps = getConnection().prepareStatement(new StringBuilder().append("delete from flr").append(financeCubeId).append(" where formula_id = ?").toString());
/* 1022 */       ps.setInt(1, formulaId);
/* 1023 */       ps.executeUpdate();
/*      */     }
/*      */     catch (SQLException e)
/*      */     {
/* 1027 */       throw handleSQLException("deleteFormula", e);
/*      */     }
/*      */     finally
/*      */     {
/* 1031 */       closeStatement(ps);
/* 1032 */       closeConnection();
/*      */     }
/*      */   }
/*      */ 
/*      */   private void deleteFormula(int financeCubeId, int formulaId)
/*      */   {
/* 1038 */     this.mLog.info("deleteFormula", new StringBuilder().append("formulaId=").append(formulaId).toString());
/* 1039 */     PreparedStatement ps = null;
/*      */     try
/*      */     {
/* 1042 */       ps = getConnection().prepareStatement(new StringBuilder().append("delete from for").append(financeCubeId).append(" where formula_id = ?").toString());
/* 1043 */       ps.setInt(1, formulaId);
/* 1044 */       ps.executeUpdate();
/*      */     }
/*      */     catch (SQLException e)
/*      */     {
/* 1048 */       throw handleSQLException("deleteFormula", e);
/*      */     }
/*      */     finally
/*      */     {
/* 1052 */       closeStatement(ps);
/* 1053 */       closeConnection();
/*      */     }
/*      */   }
/*      */ 
/*      */   private void executeSQLStatements(String sql, Object[] args)
/*      */     throws Exception
/*      */   {
/* 1062 */     for (int i = 0; i < args.length; i += 2) {
/* 1063 */       sql = sql.replace(String.valueOf(args[i]), String.valueOf(args[(i + 1)]));
/*      */     }
/*      */     try
/*      */     {
/* 1067 */       StringTokenizer st = new StringTokenizer(sql, ";", false);
/* 1068 */       while (st.hasMoreTokens())
/*      */       {
/* 1070 */         String statement = st.nextToken();
/*      */ 
/* 1072 */         PreparedStatement ps = null;
/*      */         try
/*      */         {
/* 1075 */           ps = getConnection().prepareStatement(statement);
/* 1076 */           ps.executeUpdate();
/*      */         }
/*      */         finally
/*      */         {
/* 1080 */           closeStatement(ps);
/*      */         }
/*      */       }
/*      */     }
/*      */     finally
/*      */     {
/* 1086 */       closeConnection();
/*      */     }
/*      */   }
/*      */ 
/*      */   public void definePackage(String packageName, String packageSQL, boolean isHeader)
/*      */     throws ValidationException
/*      */   {
/* 1111 */     CallableStatement cs = null;
/* 1112 */     PreparedStatement ps = null;
/* 1113 */     ResultSet rs = null;
/*      */     try
/*      */     {
/* 1116 */       this.mLog.info("definePackage", new StringBuilder().append("SQL=\n").append(packageSQL).toString());
/*      */ 
/* 1123 */       ps = getConnection().prepareStatement("select empty_clob() from dual");
/* 1124 */       rs = ps.executeQuery();
/* 1125 */       rs.next();
/* 1126 */       DatumWithConnection datum = (DatumWithConnection)rs.getClob(1);
/* 1127 */       OracleConnection oconn = datum.getOracleConnection();
/*      */ 
/* 1129 */       List lines = new ArrayList();
/*      */       try
/*      */       {
/* 1132 */         StringReader sr = new StringReader(packageSQL);
/* 1133 */         LineNumberReader lnr = new LineNumberReader(sr);
/* 1134 */         String line = null;
/* 1135 */         while ((line = lnr.readLine()) != null)
/* 1136 */           lines.add(line);
/*      */       }
/*      */       catch (IOException e)
/*      */       {
/* 1140 */         throw new IllegalStateException("IOException reading from a string buffer!", e);
/*      */       }
/*      */ 
/* 1143 */       ArrayDescriptor lineListDescriptor = ArrayDescriptor.createDescriptor("TYPE_LINELIST", oconn);
/* 1144 */       ARRAY linesArray = new ARRAY(lineListDescriptor, oconn, lines.toArray());
/*      */ 
/* 1146 */       cs = getConnection().prepareCall("{? = call cp_utils.createPackage(?,?,?,?,?)}");
/*      */ 
/* 1148 */       cs.registerOutParameter(1, 4);
/* 1149 */       cs.setString(2, packageName.toUpperCase());
/* 1150 */       cs.setObject(3, linesArray);
/* 1151 */       cs.setString(4, isHeader ? "Y" : "N");
/* 1152 */       cs.setString(5, "Y");
/* 1153 */       cs.registerOutParameter(6, 12);
/*      */ 
/* 1155 */       cs.executeUpdate();
/*      */ 
/* 1157 */       int result = cs.getInt(1);
/*      */ 
/* 1159 */       if (result != 0)
/* 1160 */         throw new ValidationException(new StringBuilder().append("Failed to define pl/sql package ").append(isHeader ? "header " : "body ").append(" : ").append(packageName).append("\nmsg:").append(cs.getString(6)).toString());
/*      */     }
/*      */     catch (SQLException e)
/*      */     {
/* 1164 */       throw handleSQLException("definePackage", e);
/*      */     }
/*      */     finally
/*      */     {
/* 1168 */       closeResultSet(rs);
/* 1169 */       closeStatement(ps);
/* 1170 */       closeStatement(cs);
/* 1171 */       closeConnection();
/*      */     }
/*      */   }
/*      */ 
/*      */   public void dropPackage(String packageName)
/*      */     throws ValidationException
/*      */   {
/* 1190 */     CallableStatement cs = null;
/*      */     try
/*      */     {
/* 1193 */       cs = getConnection().prepareCall("{ call cp_utils.dropPackage( ? ) }");
/* 1194 */       cs.setString(1, packageName.toUpperCase());
/* 1195 */       cs.execute();
/*      */     }
/*      */     catch (SQLException e)
/*      */     {
/* 1199 */       throw handleSQLException("dropPackage", e);
/*      */     }
/*      */     finally
/*      */     {
/* 1203 */       closeStatement(cs);
/* 1204 */       closeConnection();
/*      */     }
/*      */   }
/*      */ 
/*      */   private String repeat(String s, int count, String sep)
/*      */   {
/* 1210 */     StringBuilder sb = new StringBuilder();
/*      */ 
/* 1212 */     for (int i = 0; i < count; i++)
/*      */     {
/* 1214 */       if (i != 0)
/* 1215 */         sb.append(sep);
/* 1216 */       sb.append(s);
/*      */     }
/*      */ 
/* 1219 */     return sb.toString();
/*      */   }
/*      */ 
/*      */   private int[] queryCalendarElementId(int financeCubeId, String structure, String element)
/*      */     throws Exception
/*      */   {
/* 1233 */     PreparedStatement ps = null;
/* 1234 */     ResultSet rs = null;
/*      */     try
/*      */     {
/* 1237 */       ps = getConnection().prepareStatement("with param as ( select ? as finance_cube_id, ? as calHier, ? as calElement from dual )\n,hier as\n(\n  select hierarchy_id\n  from finance_cube fc\n  join model_dimension_rel using( model_id )\n  join dimension d using( dimension_id )\n  join hierarchy h using( dimension_id )\n  join param p on ( p.finance_cube_id = fc.finance_cube_id and p.calHier = h.vis_id )\n)\n, cal_elem as (\n  select hierarchy_id structure_id , calendar_utils.findElement(hierarchy_id,calElement) structure_element_id\n  from hier, param\n)\nselect structure_id, structure_element_id, se.position, se.end_position, cp.start_leaf_position\nfrom cal_elem ce\njoin structure_element se using( structure_id, structure_element_id )join cal_pos cp using( structure_id, structure_element_id )\n");
/*      */ 
/* 1257 */       ps.setInt(1, financeCubeId);
/* 1258 */       ps.setString(2, structure);
/* 1259 */       ps.setString(3, element);
/* 1260 */       rs = ps.executeQuery();
/* 1261 */       if (rs.next()) {
/* 1262 */         int[] arrayOfInt = { rs.getInt("structure_id"), rs.getInt("structure_element_id"), rs.getInt("position"), rs.getInt("end_position"), rs.getInt("start_leaf_position") };
/*      */         return arrayOfInt;
/*      */       }
/* 1265 */       throw new ValidationException(new StringBuilder().append("Unable to locate structure element [").append(element).append("] for structure [").append(structure).append("]").toString());
/*      */     }
/*      */     catch (SQLException e)
/*      */     {
/* 1270 */       throw handleSQLException("queryCalendarElementId", e);
/*      */     }
/*      */     finally
/*      */     {
/* 1274 */       closeResultSet(rs);
/* 1275 */       closeStatement(ps);
/* 1276 */       closeConnection(); } //throw localObject;
/*      */   }
/*      */ 
/*      */   private int[] queryCalendarElementId(int calHierId, String element)
/*      */     throws Exception
/*      */   {
/* 1291 */     PreparedStatement ps = null;
/* 1292 */     ResultSet rs = null;
/*      */     try
/*      */     {
/* 1295 */       ps = getConnection().prepareStatement("select structure_id, structure_element_id, position, end_position, start_leaf_position\nfrom cal_pos \nwhere structure_id = ? and structure_element_id = calendar_utils.findElement(?,?)");
/*      */ 
/* 1299 */       ps.setInt(1, calHierId);
/* 1300 */       ps.setInt(2, calHierId);
/* 1301 */       ps.setString(3, element);
/* 1302 */       rs = ps.executeQuery();
/* 1303 */       if (rs.next())
/*      */       {
/* 1305 */         int structure_id = rs.getInt("structure_id");
/* 1306 */         int structure_element_id = rs.getInt("structure_element_id");
/* 1307 */         int position = rs.getInt("position");
/* 1308 */         int end_position = rs.getInt("end_position");
/* 1309 */         int start_leaf_position = rs.getInt("start_leaf_position");
/* 1310 */         if (position != start_leaf_position)
/* 1311 */           throw new ValidationException(new StringBuilder().append("Calendar element reference [").append(element).append("] is not a leaf node.").toString());
/* 1312 */         int[] arrayOfInt = { structure_id, structure_element_id, position, end_position, start_leaf_position };
/*      */         return arrayOfInt;
/*      */       }
/* 1317 */       throw new ValidationException(new StringBuilder().append("Unable to locate structure element [").append(element).append("] for calendar hier [").append(calHierId).append("]").toString());
/*      */     }
/*      */     catch (SQLException e)
/*      */     {
/* 1322 */       throw handleSQLException("queryCalendarElementId", e);
/*      */     }
/*      */     finally
/*      */     {
/* 1326 */       closeResultSet(rs);
/* 1327 */       closeStatement(ps);
/* 1328 */       closeConnection(); } //throw localObject;
/*      */   }
/*      */ 
/*      */   private int[] queryStructureElementId(int financeCubeId, String structure, String element)
/*      */     throws Exception
/*      */   {
/* 1344 */     PreparedStatement ps = null;
/* 1345 */     ResultSet rs = null;
/*      */     try
/*      */     {
/* 1348 */       ps = getConnection().prepareStatement("select se.structure_id, se.structure_element_id, se.position, se.end_position\nfrom finance_cube fc \njoin model_dimension_rel using( model_id )\njoin dimension d using( dimension_id )\njoin hierarchy h using( dimension_id )\njoin structure_element se on ( se.structure_id = h.hierarchy_id )\nwhere fc.finance_cube_id = ? and h.vis_id = ? and se.vis_id = ?");
/*      */ 
/* 1356 */       ps.setInt(1, financeCubeId);
/* 1357 */       ps.setString(2, structure);
/* 1358 */       ps.setString(3, element);
/* 1359 */       rs = ps.executeQuery();
/* 1360 */       if (rs.next()) {
/* 1361 */         int[] arrayOfInt = { rs.getInt("structure_id"), rs.getInt("structure_element_id"), rs.getInt("position"), rs.getInt("end_position") };
/*      */         return arrayOfInt;
/*      */       }
/* 1364 */       throw new ValidationException(new StringBuilder().append("Unable to locate structure element [").append(element).append("] for structure [").append(structure).append("]").toString());
/*      */     }
/*      */     catch (SQLException e)
/*      */     {
/* 1369 */       throw handleSQLException("queryStructureElementId", e);
/*      */     }
/*      */     finally
/*      */     {
/* 1373 */       closeStatement(ps);
/* 1374 */       closeResultSet(rs);
/* 1375 */       closeConnection(); } //throw localObject;
/*      */   }
/*      */ 
/*      */   private int[] queryDimensionElementId(int financeCubeId, String dimensionElementVisId)
/*      */     throws Exception
/*      */   {
/* 1389 */     PreparedStatement ps = null;
/* 1390 */     ResultSet rs = null;
/*      */     try
/*      */     {
/* 1393 */       ps = getConnection().prepareStatement("select se.structure_id, se.structure_element_id, se.position, se.end_position\nfrom finance_cube fc \njoin model_dimension_rel using( model_id )\njoin dimension d using( dimension_id )\njoin hierarchy h using( dimension_id )\njoin structure_element se on ( se.structure_id = h.hierarchy_id )\nwhere fc.finance_cube_id = ? and se.vis_id = ?");
/*      */ 
/* 1401 */       ps.setInt(1, financeCubeId);
/* 1402 */       ps.setString(2, dimensionElementVisId);
/* 1403 */       rs = ps.executeQuery();
/* 1404 */       if (rs.next()) {
/* 1405 */         int[] arrayOfInt = { rs.getInt("structure_element_id"), rs.getInt("position"), rs.getInt("end_position") };
/*      */         return arrayOfInt;
/*      */       }
/* 1407 */       throw new ValidationException(new StringBuilder().append("Unable to locate dimension element [").append(dimensionElementVisId).append("] for fcid:").append(financeCubeId).toString());
/*      */     }
/*      */     catch (SQLException e)
/*      */     {
/* 1412 */       throw handleSQLException("queryDimensionElementId", e);
/*      */     }
/*      */     finally
/*      */     {
/* 1416 */       closeStatement(ps);
/* 1417 */       closeResultSet(rs);
/* 1418 */       closeConnection(); } //throw localObject;
/*      */   }
/*      */ 
/*      */   public EntityList queryFormulaeForSourceGroup(int financeCubeId, int sourceGroupIndex)
/*      */   {
/* 1443 */     PreparedStatement ps = null;
/* 1444 */     ResultSet rs = null;
/*      */     try
/*      */     {
/* 1447 */       ps = getConnection().prepareStatement("select fr.formula_id, cf.formula_text, cf.formula_type\nfrom for${financeCubeId} fr\njoin cube_formula cf on ( cf.cube_formula_id = fr.formula_id and cf.finance_cube_id = ? )\nwhere formula_package_index = ?".replaceAll("\\$\\{financeCubeId\\}", String.valueOf(financeCubeId)));
/*      */ 
/* 1449 */       ps.setInt(1, financeCubeId);
/* 1450 */       ps.setInt(2, sourceGroupIndex);
/*      */ 
/* 1452 */       rs = ps.executeQuery();
/*      */ 
/* 1454 */       EntityListImpl localEntityListImpl = JdbcUtils.extractToEntityListImpl(this.sQUERY_CUBE_FORMULA_FOR_SOURCE_GROUP_COLINFO, rs);
/*      */       return localEntityListImpl;
/*      */     }
/*      */     catch (SQLException e)
/*      */     {
/* 1458 */       throw handleSQLException("queryFormulaeForSourceGroup", e);
/*      */     }
/*      */     finally
/*      */     {
/* 1462 */       closeResultSet(rs);
/* 1463 */       closeStatement(ps);
/* 1464 */       closeConnection(); } //throw localObject;
/*      */   }
/*      */ 
/*      */   public void checkForCyclicFormulaDeployments(int financeCubeId, int numDims, int calHierId)
/*      */     throws ValidationException
/*      */   {
/* 1478 */     SqlBuilder builder = null;
/* 1479 */     SqlExecutor executor = null;
/*      */     try
/*      */     {
/* 1482 */       StringTemplate st = getTemplate("loop_detection_query");
/* 1483 */       st.reset();
/* 1484 */       int[] dims = new int[numDims];
/* 1485 */       for (int i = 0; i < numDims; i++)
/* 1486 */         dims[i] = i;
/* 1487 */       int[] odims = new int[numDims - 1];
/* 1488 */       for (int i = 0; i < numDims - 1; i++)
/* 1489 */         odims[i] = i;
/* 1490 */       st.setAttribute("financeCubeId", financeCubeId);
/* 1491 */       st.setAttribute("dims", dims);
/* 1492 */       st.setAttribute("odims", odims);
/* 1493 */       st.setAttribute("calHierId", calHierId);
/* 1494 */       builder = new SqlBuilder(new String[] { st.toString() });
/* 1495 */       executor = new SqlExecutor("checkForCyclicFormula", getDataSource(), builder, this._log);
/* 1496 */       executor.getResultSet();
/*      */     }
/*      */     catch (SqlExecutorException e)
/*      */     {
/* 1500 */       if ((e.isSQLException()) && (e.getSqlCode().intValue() == 1436))
/* 1501 */         throw new ValidationException("This would cause a potential cyclic formula definition.");
/* 1502 */       if (e.isSQLException())
/* 1503 */         throw handleSQLException("formula loop detection", (SQLException)e.getCause());
/* 1504 */       throw e;
/*      */     }
/*      */     finally
/*      */     {
/* 1508 */       if (executor != null)
/* 1509 */         executor.close();
/*      */     }
/*      */   }
/*      */ 
/*      */   private void updateRtCubeDeployments(int modelId, int financeCubeId, int ownerId, int ownerType)
/*      */   {
/* 1515 */     new RtCubeUtilsDAO(this).updateRtCubeDeployments(modelId, financeCubeId, ownerId, ownerType);
/*      */   }
/*      */ 
/*      */   public List<Integer> queryFinanceCubePackageIndexes(int financeCubeId)
/*      */   {
/* 1525 */     PreparedStatement ps = null;
/* 1526 */     ResultSet rs = null;
/*      */     try
/*      */     {
/* 1529 */       ps = getConnection().prepareStatement(new StringBuilder().append("select distinct formula_package_index from for").append(financeCubeId).toString());
/* 1530 */       rs = ps.executeQuery();
/*      */ 
/* 1532 */       List result = new ArrayList();
/* 1533 */       if (rs.next())
/* 1534 */         result.add(Integer.valueOf(rs.getInt("formula_package_index")));
/* 1535 */       List localList1 = result;
/*      */       return localList1;
/*      */     }
/*      */     catch (SQLException e)
/*      */     {
/* 1539 */       throw handleSQLException("queryFinanceCubePackageIndexes", e);
/*      */     }
/*      */     finally
/*      */     {
/* 1543 */       closeResultSet(rs);
/* 1544 */       closeStatement(ps);
/* 1545 */       closeConnection(); } //throw localObject;
/*      */   }
/*      */ 
/*      */   private String[] queryLines(String sqlQuery)
/*      */   {
/* 1551 */     List lines = new ArrayList();
/* 1552 */     StringTokenizer st = new StringTokenizer(sqlQuery, "\n\r", false);
/* 1553 */     while (st.hasMoreTokens())
/*      */     {
/* 1555 */       lines.add(st.nextToken());
/*      */     }
/* 1557 */     return (String[])lines.toArray(new String[0]);
/*      */   }
/*      */ 
/*      */   public EntityList queryInvalidRuntimeDeployments(int financeCubeId)
/*      */   {
/* 1567 */     StringTemplate st = getTemplate("query_invalid_runtime_deployments");
/*      */ 
/* 1569 */     st.setAttribute("financeCubeId", financeCubeId);
/*      */ 
/* 1571 */     SqlBuilder builder = new SqlBuilder(new String[] { st.toString() });
/* 1572 */     SqlExecutor executor = null;
/* 1573 */     ResultSet rs = null;
/*      */     try
/*      */     {
/* 1576 */       executor = new SqlExecutor("queryInvalidRuntimeDeployments", getDataSource(), builder, this._log);
/*      */ 
/* 1578 */       rs = executor.getResultSet();
/*      */ 
/* 1580 */       EntityListImpl localEntityListImpl = JdbcUtils.extractToEntityListImpl(new JdbcUtils.ColType[] { new JdbcUtils.CubeFormulaRefColType("cubeFormula", "cube_formula_id", "vis_id") }, rs);
/*      */       return localEntityListImpl;
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/* 1588 */       throw handleSQLException("queryInvalidFormula", sqle);
/*      */     }
/*      */     finally
/*      */     {
/* 1592 */       closeResultSet(rs);
/* 1593 */       if (executor != null)
/* 1594 */         executor.close(); 
/* 1594 */     }
/*      */   }
/*      */ 
/*      */   public EntityList queryInvalidRuntimeCellReferences(int financeCubeId, int numDims)
/*      */   {
/* 1606 */     StringTemplate st = getTemplate("query_invalid_runtime_cell_references");
/* 1607 */     int[] dims = new int[numDims];
/* 1608 */     for (int i = 0; i < numDims; i++)
/* 1609 */       dims[i] = i;
/* 1610 */     st.setAttribute("dims", dims);
/* 1611 */     st.setAttribute("financeCubeId", financeCubeId);
/*      */ 
/* 1613 */     SqlBuilder builder = new SqlBuilder(new String[] { st.toString() });
/* 1614 */     SqlExecutor executor = null;
/* 1615 */     ResultSet rs = null;
/*      */     try
/*      */     {
/* 1618 */       executor = new SqlExecutor("queryInvalidRuntimeCellReferences", getDataSource(), builder, this._log);
/*      */ 
/* 1620 */       rs = executor.getResultSet();
/*      */ 
/* 1622 */       EntityListImpl localEntityListImpl = JdbcUtils.extractToEntityListImpl(new JdbcUtils.ColType[] { new JdbcUtils.CubeFormulaRefColType("cubeFormula", "cube_formula_id", "vis_id") }, rs);
/*      */       return localEntityListImpl;
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/* 1630 */       throw handleSQLException("queryInvalidRuntimeCellReferences", sqle);
/*      */     }
/*      */     finally
/*      */     {
/* 1634 */       closeResultSet(rs);
/* 1635 */       if (executor != null)
/* 1636 */         executor.close(); 
/* 1636 */     }
/*      */   }
/*      */ 
/*      */   public EntityList queryInvalidRuntimeCellRangeReferences(int financeCubeId, int numDims)
/*      */   {
/* 1648 */     StringTemplate st = getTemplate("query_invalid_runtime_cell_range_references");
/* 1649 */     int[] dims = new int[numDims];
/* 1650 */     for (int i = 0; i < numDims; i++)
/* 1651 */       dims[i] = i;
/* 1652 */     st.setAttribute("dims", dims);
/* 1653 */     st.setAttribute("financeCubeId", financeCubeId);
/*      */ 
/* 1655 */     SqlBuilder builder = new SqlBuilder(new String[] { st.toString() });
/* 1656 */     SqlExecutor executor = null;
/* 1657 */     ResultSet rs = null;
/*      */     try
/*      */     {
/* 1660 */       executor = new SqlExecutor("queryInvalidRuntimeCellRangeReferences", getDataSource(), builder, this._log);
/*      */ 
/* 1663 */       rs = executor.getResultSet();
/*      */ 
/* 1665 */       EntityListImpl localEntityListImpl = JdbcUtils.extractToEntityListImpl(new JdbcUtils.ColType[] { new JdbcUtils.CubeFormulaRefColType("cubeFormula", "cube_formula_id", "vis_id") }, rs);
/*      */       return localEntityListImpl;
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/* 1673 */       throw handleSQLException("queryInvalidRuntimeCellRangeReferences", sqle);
/*      */     }
/*      */     finally
/*      */     {
/* 1677 */       closeResultSet(rs);
/* 1678 */       if (executor != null)
/* 1679 */         executor.close(); 
/* 1679 */     }
/*      */   }
/*      */ 
/*      */   public int updateRuntimeDeploymentPositions(int financeCubeId)
/*      */   {
/* 1692 */     StringTemplate st = getTemplate("update_runtime_deployment_positions");
/* 1693 */     st.setAttribute("financeCubeId", financeCubeId);
/* 1694 */     SqlBuilder builder = new SqlBuilder(new String[] { st.toString() });
/* 1695 */     SqlExecutor executor = null;
/*      */     try
/*      */     {
/* 1698 */       executor = new SqlExecutor("updateRuntimeDeploymentPositions", getDataSource(), builder, this._log);
/*      */ 
/* 1700 */       int i = executor.executeUpdate();
/*      */       return i;
/*      */     }
/*      */     finally
/*      */     {
/* 1704 */       if (executor != null)
/* 1705 */         executor.close(); 
/* 1705 */     }
/*      */   }
/*      */ 
/*      */   public int updateRuntimeRangeReferencesPositions(int financeCubeId, int numDims)
/*      */   {
/* 1718 */     StringTemplate st = getTemplate("update_runtime_range_ref_positions");
/* 1719 */     st.setAttribute("financeCubeId", financeCubeId);
/* 1720 */     int[] odims = new int[numDims - 1];
/* 1721 */     for (int i = 0; i < odims.length; i++)
/* 1722 */       odims[i] = i;
/* 1723 */     st.setAttribute("odims", odims);
/* 1724 */     SqlBuilder builder = new SqlBuilder(new String[] { st.toString() });
/* 1725 */     SqlExecutor executor = null;
/*      */     try
/*      */     {
/* 1728 */       executor = new SqlExecutor("updateRuntimeRangeReferencesPositions", getDataSource(), builder, this._log);
/*      */ 
/* 1730 */       int i = executor.executeUpdate();
/*      */       return i;
/*      */     }
/*      */     finally
/*      */     {
/* 1734 */       if (executor != null)
/* 1735 */         executor.close(); 
/* 1735 */     }
/*      */   }
/*      */ 
/*      */   public int updateRuntimeCellReferencePositions(int financeCubeId, int numDims)
/*      */   {
/* 1748 */     StringTemplate st = getTemplate("update_runtime_cell_ref_positions");
/* 1749 */     st.setAttribute("financeCubeId", financeCubeId);
/* 1750 */     int[] dims = new int[numDims];
/* 1751 */     for (int i = 0; i < dims.length; i++)
/* 1752 */       dims[i] = i;
/* 1753 */     st.setAttribute("dims", dims);
/* 1754 */     SqlBuilder builder = new SqlBuilder(new String[] { st.toString() });
/* 1755 */     SqlExecutor executor = null;
/*      */     try
/*      */     {
/* 1758 */       executor = new SqlExecutor("updateRuntimeCellReferencePositions", getDataSource(), builder, this._log);
/*      */ 
/* 1760 */       int i = executor.executeUpdate();
/*      */       return i;
/*      */     }
/*      */     finally
/*      */     {
/* 1764 */       if (executor != null)
/* 1765 */         executor.close(); 
/* 1765 */     }
/*      */   }
/*      */ 
/*      */   private ModelDAO getModelDAO()
/*      */   {
/* 1772 */     if (this.mModelDAO == null)
/*      */     {
/* 1774 */       if (this.mDataSource == null)
/* 1775 */         this.mModelDAO = new ModelDAO(getConnection());
/*      */       else
/* 1777 */         this.mModelDAO = new ModelDAO();
/*      */     }
/* 1779 */     return this.mModelDAO;
/*      */   }
/*      */ 
/*      */   private RtCubeUtilsDAO getRtCubeUtilsDAO()
/*      */   {
/* 1784 */     if (this.mRtCubeUtilsDAO == null)
/*      */     {
/* 1786 */       if (this.mDataSource == null)
/* 1787 */         this.mRtCubeUtilsDAO = new RtCubeUtilsDAO(getConnection());
/*      */       else
/* 1789 */         this.mRtCubeUtilsDAO = new RtCubeUtilsDAO();
/*      */     }
/* 1791 */     return this.mRtCubeUtilsDAO;
/*      */   }
/*      */ }

/* Location:           /home/oracle/coa/cp.ear/cp.ear_orginal/cp-server.jar
 * Qualified Name:     com.cedar.cp.ejb.base.cube.formula.FormulaDAO
 * JD-Core Version:    0.6.0
 */