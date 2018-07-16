/*     */ package com.cedar.cp.ejb.impl.model;
/*     */ 
/*     */ 
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.sql.DataSource;

import com.cedar.cp.dto.model.AllFinanceCubesWebForModelELO;
import com.cedar.cp.ejb.base.cube.CellCalc;
import com.cedar.cp.ejb.base.cube.CellCalcData;
import com.cedar.cp.ejb.impl.base.AbstractDAO;
import com.cedar.cp.util.TemplateParser;
import com.cedar.cp.util.Timer;


/*     */ 
/*     */ public class CellCalculationDataDAO extends AbstractDAO
/*     */ {
/*     */   private static final String SELECT_KEY_SEQ_FOR_UPDATE_SQL = "select seq_num from cell_calculation_data_seq for update";
/*     */   private static final String UPDATE_KEY_SEQ_SQL = "update cell_calculation_data_seq set seq_num = seq_num + ?";
/*     */   private static final String DELETE_ROWS_SQL = "delete from cell_calculation_data where cell_calc_short_id = ?";
/*     */   private static final String INSERT_ROWS_SQL = "insert into cell_calculation_data ( finance_cube_id, cell_calc_short_id, row_id, col_id, numeric_value, string_value ) values ( ?, ?, ?, ?, ?, ? )";
/* 235 */   private static String REMOVE_CELL_CALCULATION_DATA_SQL = "delete cell_calculation_data \nwhere finance_cube_id = ? and \n      cell_calc_short_id in ( select short_id \n\t\t\t\t\t\t\t   from cft{financeCubeId} \n\t\t\t\t\t\t\t   where dim{dimensionIndex} not in ( select dimension_element_id \n\t\t\t\t\t\t\t\t\t\t\t\t   \t\t\t\t  from dimension_element \n\t\t\t\t\t\t\t\t\t\t\t\t   \t\t\t\t  where dimension_id = ? ) )";
/*     */ 
/* 245 */   private static String REMOVE_CELL_CALC_CFT_DATA_SQL = "delete \nfrom cft{financeCubeId} \nwhere dim{dimensionIndex} not in ( select dimension_element_id \n\t\t\t\t\t\t\t\t\tfrom dimension_element \n\t\t\t\t\t\t\t\t\twhere dimension_id = ? )";
/*     */ 
/*     */   public CellCalculationDataDAO()
/*     */   {
/*     */   }
/*     */ 
/*     */   public CellCalculationDataDAO(Connection connection)
/*     */   {
/*  25 */     super(connection);
/*     */   }
/*     */ 
/*     */   public CellCalculationDataDAO(DataSource ds)
/*     */   {
/*  30 */     super(ds);
/*     */   }
/*     */ 
/*     */   public String getEntityName()
/*     */   {
/*  35 */     return "CellCalculationDataDAO";
/*     */   }
/*     */ 
/*     */   public int assignKeys(int numKeys)
/*     */     throws SQLException
/*     */   {
/*  55 */     PreparedStatement stmt = null;
/*     */     try
/*     */     {
/*  59 */       stmt = getConnection().prepareStatement("select seq_num from cell_calculation_data_seq for update");
/*     */ 
/*  61 */       ResultSet rs = stmt.executeQuery();
/*     */ 
/*  63 */       if (!rs.next()) {
/*  64 */         throw new IllegalStateException("Unable to select seq_num from cell_calucation_data_seq for update");
/*     */       }
/*  66 */       int startKey = rs.getInt("seq_num");
/*     */ 
/*  68 */       closeResultSet(rs);
/*  69 */       closeStatement(stmt);
/*     */ 
/*  71 */       stmt = getConnection().prepareStatement("update cell_calculation_data_seq set seq_num = seq_num + ?");
/*     */ 
/*  73 */       stmt.setInt(1, numKeys);
/*     */ 
/*  75 */       if (stmt.executeUpdate() != 1) {
/*  76 */         throw new IllegalStateException("Unable to update seq_num in cell_calulation_data_seq");
/*     */       }
/*  78 */       int i = startKey;
/*     */       return i;
/*     */     }
/*     */     finally
/*     */     {
/*  82 */       closeStatement(stmt);
/*  83 */       closeConnection(); } //throw localObject;
/*     */   }
/*     */ 
/*     */   public void persistCellCalculationDataRows(int financeCubeId, List cellCalcs, int newCCDKey)
/*     */     throws SQLException
/*     */   {
/*  99 */     List deleteList = new ArrayList();
/*     */ 
/* 102 */     Iterator cIter = cellCalcs.iterator();
/* 103 */     while (cIter.hasNext())
/*     */     {
/* 105 */       CellCalc cellCalc = (CellCalc)cIter.next();
/* 106 */       if (cellCalc.isNewKey())
/* 107 */         cellCalc.setShortId(newCCDKey++);
/*     */       else {
/* 109 */         deleteList.add(cellCalc);
/*     */       }
/*     */     }
/*     */ 
/* 113 */     if (!deleteList.isEmpty()) {
/* 114 */       eraseCellCalculationDataRows(deleteList);
/*     */     }
/*     */ 
/* 118 */     Set inserts = new HashSet();
/* 119 */     inserts.addAll(cellCalcs);
/* 120 */     insertCellCalcualtionDataRows(financeCubeId, inserts);
/*     */   }
/*     */ 
/*     */   private void eraseCellCalculationDataRows(List items)
/*     */     throws SQLException
/*     */   {
/* 134 */     PreparedStatement stmt = null;
/*     */     try
/*     */     {
/* 138 */       stmt = getConnection().prepareStatement("delete from cell_calculation_data where cell_calc_short_id = ?");
/*     */ 
/* 140 */       Iterator iter = items.iterator();
/* 141 */       while (iter.hasNext())
/*     */       {
/* 143 */         CellCalc cc = (CellCalc)iter.next();
/* 144 */         stmt.setInt(1, cc.getShortId());
/* 145 */         stmt.addBatch();
/*     */       }
/*     */ 
/* 148 */       stmt.executeBatch();
/*     */     }
/*     */     finally
/*     */     {
/* 152 */       closeStatement(stmt);
/* 153 */       closeConnection();
/*     */     }
/*     */   }
/*     */ 
/*     */   private void insertCellCalcualtionDataRows(int financeCubeId, Set cellCalcDataRows)
/*     */     throws SQLException
/*     */   {
/* 172 */     PreparedStatement stmt = null;
/*     */     try
/*     */     {
/* 176 */       stmt = getConnection().prepareStatement("insert into cell_calculation_data ( finance_cube_id, cell_calc_short_id, row_id, col_id, numeric_value, string_value ) values ( ?, ?, ?, ?, ?, ? )");
/*     */ 
/* 178 */       Iterator iter = cellCalcDataRows.iterator();
/* 179 */       while (iter.hasNext())
/*     */       {
/* 181 */         CellCalc cc = (CellCalc)iter.next();
/*     */ 
/* 183 */         Iterator ccdIter = cc.getCellCalcData().iterator();
/* 184 */         while (ccdIter.hasNext())
/*     */         {
/* 186 */           CellCalcData ccd = (CellCalcData)ccdIter.next();
/*     */ 
/* 188 */           stmt.setInt(1, financeCubeId);
/* 189 */           stmt.setInt(2, cc.getShortId());
/* 190 */           stmt.setInt(3, ccd.getRowId());
/* 191 */           stmt.setString(4, ccd.getColId());
/* 192 */           if (ccd.getNumericValue() != null)
/* 193 */             stmt.setBigDecimal(5, ccd.getNumericValue());
/*     */           else
/* 195 */             stmt.setNull(5, 2);
/* 196 */           if (ccd.getStringValue() != null)
/* 197 */             stmt.setString(6, ccd.getStringValue());
/*     */           else
/* 199 */             stmt.setNull(6, 12);
/* 200 */           stmt.addBatch();
/*     */         }
/*     */       }
/*     */ 
/* 204 */       stmt.executeBatch();
/*     */     }
/*     */     finally
/*     */     {
/* 208 */       closeStatement(stmt);
/* 209 */       closeConnection();
/*     */     }
/*     */   }
/*     */ 
			private String expandSQLTemplate(int financeCubeId, int dimensionIndex, String sql) {
				TemplateParser1 parser = new TemplateParser1(sql, financeCubeId, dimensionIndex);
			    return parser.parse();
			 }
			
			public static class TemplateParser1 extends TemplateParser {

				   // $FF: synthetic field
				   final int financeCubeId;
				   // $FF: synthetic field
				   final int dimensionIndex;

				   TemplateParser1(String sql, int financeCubeId, int dimensionIndex) {
				      super(sql);
				      this.financeCubeId = financeCubeId;
				      this.dimensionIndex = dimensionIndex;
				   }

				   public String parseToken(String token) {
				      StringBuffer sb = new StringBuffer();
				      if(token.equals("financeCubeId")) {
				         sb.append(String.valueOf(this.financeCubeId));
				      } else {
				         if(!token.equals("dimensionIndex")) {
				            throw new IllegalStateException("Unexpected token:" + token);
				         }

				         sb.append(String.valueOf(this.dimensionIndex));
				      }

				      return sb.toString();
				   }
				}
			

/*     */ 
/*     */   public void dimensionElementsRemoved(int financeCubeId, int dimensionIndex, int dimensionId)
/*     */     throws SQLException
/*     */   {
/* 263 */     PreparedStatement ps = null;
/*     */     try
/*     */     {
/* 267 */       ps = getConnection().prepareStatement(expandSQLTemplate(financeCubeId, dimensionIndex, REMOVE_CELL_CALCULATION_DATA_SQL));
/*     */ 
/* 270 */       ps.setInt(1, financeCubeId);
/* 271 */       ps.setInt(2, dimensionId);
/* 272 */       ps.executeUpdate();
/* 273 */       closeStatement(ps);
/* 274 */       ps = null;
/*     */ 
/* 277 */       ps = getConnection().prepareStatement(expandSQLTemplate(financeCubeId, dimensionIndex, REMOVE_CELL_CALC_CFT_DATA_SQL));
/*     */ 
/* 280 */       ps.setInt(1, dimensionId);
/* 281 */       ps.executeUpdate();
/*     */     }
/*     */     finally
/*     */     {
/* 285 */       closeStatement(ps);
/* 286 */       closeConnection();
/*     */     }
/*     */   }
/*     */ 
/*     */   public void deleteAllNonReferencedCellData(int fcId, int cellCalcId)
/*     */   {
/* 299 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/* 300 */     PreparedStatement stmt = null;
/*     */     try
/*     */     {
/* 303 */       StringBuffer sql = new StringBuffer();
/* 304 */       sql.append("delete from cell_calculation_data");
/* 305 */       sql.append(" where finance_cube_id = ? ");
/* 306 */       sql.append("   and cell_calc_short_id not in (select short_id from CFT" + fcId + ")");
/*     */ 
/* 308 */       String sqlToExecute = sql.toString();
/* 309 */       stmt = getConnection().prepareStatement(sqlToExecute);
/* 310 */       stmt.setInt(1, fcId);
/* 311 */       stmt.executeUpdate();
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/* 315 */       System.err.println(sqle);
/* 316 */       sqle.printStackTrace();
/* 317 */       throw new RuntimeException(getEntityName() + " deleteCellCalculationValues", sqle);
/*     */     }
/*     */     finally
/*     */     {
/* 321 */       closeStatement(stmt);
/* 322 */       closeConnection();
/*     */     }
/* 324 */     if (timer != null)
/* 325 */       timer.logDebug("deleteCellCalculationValues", "");
/*     */   }
/*     */ 
/*     */   public void deleteCellCalculationValues(int fcId, int cellCalcId)
/*     */   {
/* 330 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/* 331 */     PreparedStatement stmt1 = null;
/* 332 */     PreparedStatement stmt2 = null;
/*     */     try
/*     */     {
/* 335 */       StringBuffer sql = new StringBuffer();
/* 336 */       sql.append("delete from cell_calculation_data ");
/* 337 */       sql.append(" where cell_calc_short_id in ");
/* 338 */       sql.append("       (select short_id from CFT" + fcId);
/* 339 */       sql.append("         where cell_calc_id = ?)");
/* 340 */       stmt1 = getConnection().prepareStatement(sql.toString());
/* 341 */       stmt1.setInt(1, cellCalcId);
/* 342 */       stmt1.executeUpdate();
/*     */ 
/* 344 */       sql.setLength(0);
/* 345 */       sql.append("delete from CFT" + fcId);
/* 346 */       sql.append(" where cell_calc_id = ?");
/* 347 */       stmt2 = getConnection().prepareStatement(sql.toString());
/* 348 */       stmt2.setInt(1, cellCalcId);
/* 349 */       stmt2.executeUpdate();
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/* 353 */       System.err.println(sqle);
/* 354 */       sqle.printStackTrace();
/* 355 */       throw new RuntimeException(getEntityName() + " deleteCellCalculationValues", sqle);
/*     */     }
/*     */     finally
/*     */     {
/* 359 */       closeStatement(stmt1);
/* 360 */       closeStatement(stmt2);
/* 361 */       closeConnection();
/*     */     }
/* 363 */     if (timer != null)
/* 364 */       timer.logDebug("deleteCellCalculationValues", "");
/*     */   }
/*     */ 
/*     */   public void deleteCFTCell(int fcId, int shortId, int[] cellAddress, String dataType)
/*     */   {
/* 369 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/* 370 */     PreparedStatement stmt1 = null;
/* 371 */     PreparedStatement stmt2 = null;
/*     */     try
/*     */     {
/* 374 */       StringBuffer sql = new StringBuffer();
/* 375 */       sql.append("delete from CFT" + fcId);
/* 376 */       sql.append("\n where short_id = ? ");
/* 377 */       sql.append("\n   and data_type = ? ");
/* 378 */       for (int i = 0; i < cellAddress.length; i++)
/* 379 */         sql.append("\n   and dim" + i + " = ? ");
/* 380 */       stmt1 = getConnection().prepareStatement(sql.toString());
/*     */ 
/* 382 */       int index = 1;
/* 383 */       stmt1.setInt(index++, shortId);
/* 384 */       stmt1.setString(index++, dataType);
/* 385 */       for (int i = 0; i < cellAddress.length; i++) {
/* 386 */         stmt1.setInt(index++, cellAddress[i]);
/*     */       }
/* 388 */       stmt1.executeUpdate();
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/* 392 */       System.err.println(sqle);
/* 393 */       sqle.printStackTrace();
/* 394 */       throw new RuntimeException(getEntityName() + " deleteCFTCell", sqle);
/*     */     }
/*     */     finally
/*     */     {
/* 398 */       closeStatement(stmt1);
/* 399 */       closeStatement(stmt2);
/* 400 */       closeConnection();
/*     */     }
/* 402 */     if (timer != null)
/* 403 */       timer.logDebug("deleteCFTCell", "");
/*     */   }
/*     */ 
/*     */   public void deleteAllCellCalcValues(int modelId, int cellCalcId)
/*     */   {
/* 414 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/* 415 */     FinanceCubeDAO fcDAO = new FinanceCubeDAO();
/* 416 */     AllFinanceCubesWebForModelELO cubes = fcDAO.getAllFinanceCubesWebForModel(modelId);
/* 417 */     for (int i = 0; i < cubes.getNumRows(); i++)
/*     */     {
/* 419 */       Integer fcId = (Integer)cubes.getValueAt(i, "FinanceCubeId");
/* 420 */       deleteCellCalculationValues(fcId.intValue(), cellCalcId);
/*     */     }
/* 422 */     if (timer != null)
/* 423 */       timer.logDebug("deleteAllCellCalcValues", "");
/*     */   }
/*     */ 
/*     */   public List<Object[]> getCFTCellsForShortId(int financeId, int shortId, int nDimensions)
/*     */   {
/* 428 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/* 429 */     PreparedStatement stmt = null;
/* 430 */     ResultSet resultSet = null;
/* 431 */     List results = new ArrayList();
/* 432 */     StringBuffer sql = new StringBuffer();
/*     */     try
/*     */     {
/* 435 */       sql.append("select ");
/* 436 */       for (int i = 0; i < nDimensions; i++)
/* 437 */         sql.append(" dim" + i + ",");
/* 438 */       sql.append("data_type from CFT" + financeId);
/* 439 */       sql.append("\n where short_id = ? ");
/*     */ 
/* 441 */       stmt = getConnection().prepareStatement(sql.toString());
/*     */ 
/* 444 */       int index = 1;
/* 445 */       stmt.setInt(index++, shortId);
/*     */ 
/* 447 */       resultSet = stmt.executeQuery();
/* 448 */       while (resultSet.next())
/*     */       {
/* 451 */         Object[] line = new Object[nDimensions + 1];
/* 452 */         index = 1;
/* 453 */         for (int i = 0; i < nDimensions; i++)
/* 454 */           line[i] = Integer.valueOf(resultSet.getInt(index++));
/* 455 */         line[nDimensions] = resultSet.getString(index++);
/* 456 */         results.add(line);
/*     */       }
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/* 461 */       System.err.println(sql);
/* 462 */       System.err.println(sqle);
/* 463 */       sqle.printStackTrace();
/* 464 */       throw new RuntimeException(getEntityName() + " getCFTCellsForShortId", sqle);
/*     */     }
/*     */     finally
/*     */     {
/* 468 */       closeResultSet(resultSet);
/* 469 */       closeStatement(stmt);
/* 470 */       closeConnection();
/*     */     }
/* 472 */     if (timer != null) {
/* 473 */       timer.logDebug("getCFTCellsForShortId", "");
/*     */     }
/* 475 */     return results;
/*     */   }
/*     */ 
/*     */   public List<Integer> getCellCalcShortIds(int financeId, int cellCalcId)
/*     */   {
/* 485 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/* 486 */     PreparedStatement stmt = null;
/* 487 */     ResultSet resultSet = null;
/* 488 */     List results = new ArrayList();
/* 489 */     StringBuffer sql = new StringBuffer();
/*     */     try
/*     */     {
/* 492 */       sql.append("select distinct short_id from CFT" + financeId);
/* 493 */       sql.append("\n where cell_calc_id = ? ");
/*     */ 
/* 495 */       stmt = getConnection().prepareStatement(sql.toString());
/*     */ 
/* 498 */       int index = 1;
/* 499 */       stmt.setInt(index++, cellCalcId);
/*     */ 
/* 501 */       resultSet = stmt.executeQuery();
/* 502 */       while (resultSet.next())
/*     */       {
/* 504 */         results.add(Integer.valueOf(resultSet.getInt(1)));
/*     */       }
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/* 509 */       System.err.println(sql);
/* 510 */       System.err.println(sqle);
/* 511 */       sqle.printStackTrace();
/* 512 */       throw new RuntimeException(getEntityName() + " getCellCalcShortIdsForRA", sqle);
/*     */     }
/*     */     finally
/*     */     {
/* 516 */       closeResultSet(resultSet);
/* 517 */       closeStatement(stmt);
/* 518 */       closeConnection();
/*     */     }
/* 520 */     if (timer != null) {
/* 521 */       timer.logDebug("getCellCalcShortIdsForRA", "");
/*     */     }
/* 523 */     return results;
/*     */   }
/*     */ 
/*     */   public void deleteCellCalculatorData(int financeId, int cellCalcShortId)
/*     */   {
/* 533 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/* 534 */     PreparedStatement stmt = null;
/*     */     try
/*     */     {
/* 537 */       String deleteSQL = "delete from cell_calculation_data where finance_cube_id = ? and       cell_calc_short_id = ?";
/*     */ 
/* 543 */       stmt = getConnection().prepareStatement(deleteSQL);
/*     */ 
/* 546 */       int index = 1;
/* 547 */       stmt.setInt(index++, financeId);
/* 548 */       stmt.setInt(index++, cellCalcShortId);
/*     */ 
/* 550 */       stmt.executeUpdate();
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/* 554 */       sqle.printStackTrace();
/* 555 */       throw new RuntimeException(getEntityName() + " deleteCellCalculatorData", sqle);
/*     */     }
/*     */     finally
/*     */     {
/* 559 */       closeStatement(stmt);
/* 560 */       closeConnection();
/*     */     }
/* 562 */     if (timer != null)
/* 563 */       timer.logDebug("deleteCellCalculatorData", "");
/*     */   }
/*     */ 
/*     */   public int peekNextCellCalcInstanceNumber(int financeCubeId)
/*     */   {
/* 573 */     PreparedStatement stmt = null;
/* 574 */     ResultSet rs = null;
/*     */     try
/*     */     {
/* 577 */       String selectSQL = "select seq_num from cell_calculation_data_seq";
/* 578 */       stmt = getConnection().prepareStatement(selectSQL);
/* 579 */       rs = stmt.executeQuery();
/* 580 */       if (rs.next()) {
/* 581 */         int i = rs.getInt("seq_num");
/*     */         return i;
/*     */       }
/* 582 */       throw new IllegalStateException("Failed to peek at next cell calculation instance number");
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/* 586 */       sqle.printStackTrace();
/* 587 */       throw new RuntimeException(getEntityName() + " peekNextCellCalcInstanceNumber", sqle);
/*     */     }
/*     */     finally
/*     */     {
/* 591 */       closeResultSet(rs);
/* 592 */       closeStatement(stmt);
/* 593 */       closeConnection(); } //throw localObject;
/*     */   }
/*     */ }

/* Location:           /home/oracle/coa/cp.ear/cp.ear_orginal/cp-server.jar
 * Qualified Name:     com.cedar.cp.ejb.impl.model.CellCalculationDataDAO
 * JD-Core Version:    0.6.0
 */