/*     */ package com.cedar.cp.ejb.impl.model.cc.imp.dyn;
/*     */ 
/*     */ import com.cedar.cp.api.base.DuplicateNameValidationException;
/*     */ import com.cedar.cp.api.base.ValidationException;
/*     */ import com.cedar.cp.api.base.VersionValidationException;
/*     */ import com.cedar.cp.api.model.cc.imp.dyn.ImportGridCellRef;
/*     */ import com.cedar.cp.dto.model.cc.imp.dyn.ImportGridCellCK;
/*     */ import com.cedar.cp.dto.model.cc.imp.dyn.ImportGridCellPK;
/*     */ import com.cedar.cp.ejb.impl.base.AbstractDAO;
/*     */ import com.cedar.cp.ejb.impl.base.SqlExecutor;
/*     */ import com.cedar.cp.util.Log;
/*     */ import com.cedar.cp.util.Pair;
/*     */ import com.cedar.cp.util.SqlBuilder;
/*     */ import com.cedar.cp.util.Timer;
/*     */ import java.sql.Connection;
/*     */ import java.sql.PreparedStatement;
/*     */ import java.sql.ResultSet;
/*     */ import java.sql.SQLException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import javax.sql.DataSource;
import org.antlr.stringtemplate.StringTemplate;
/*     */ 
/*     */ public class ImportGridCellDAO extends AbstractDAO
/*     */ {
/*  39 */   Log _log = new Log(getClass());
/*     */   protected static final String SQL_FIND_BY_PRIMARY_KEY = "select GRID_ID,ROW_NUMBER,COLUMN_NUMBER from IMPORT_GRID_CELL where    GRID_ID = ? AND ROW_NUMBER = ? AND COLUMN_NUMBER = ? ";
/*     */   private static final String SQL_SELECT_COLUMNS = "select IMPORT_GRID_CELL.GRID_ID,IMPORT_GRID_CELL.ROW_NUMBER,IMPORT_GRID_CELL.COLUMN_NUMBER,IMPORT_GRID_CELL.USER_DATA";
/*     */   protected static final String SQL_LOAD = " from IMPORT_GRID_CELL where    GRID_ID = ? AND ROW_NUMBER = ? AND COLUMN_NUMBER = ? ";
/*     */   protected static final String SQL_CREATE = "insert into IMPORT_GRID_CELL ( GRID_ID,ROW_NUMBER,COLUMN_NUMBER,USER_DATA) values ( ?,?,?,?)";
/*     */   protected static final String SQL_STORE = "update IMPORT_GRID_CELL set USER_DATA = ? where    GRID_ID = ? AND ROW_NUMBER = ? AND COLUMN_NUMBER = ? ";
/*     */   protected static final String SQL_REMOVE = "delete from IMPORT_GRID_CELL where    GRID_ID = ? AND ROW_NUMBER = ? AND COLUMN_NUMBER = ? ";
/*     */   protected ImportGridCellEVO mDetails;
/*     */ 
/*     */   public ImportGridCellDAO(Connection connection)
/*     */   {
/*  46 */     super(connection);
/*     */   }
/*     */ 
/*     */   public ImportGridCellDAO()
/*     */   {
/*     */   }
/*     */ 
/*     */   public ImportGridCellDAO(DataSource ds)
/*     */   {
/*  62 */     super(ds);
/*     */   }
/*     */ 
/*     */   protected ImportGridCellPK getPK()
/*     */   {
/*  70 */     return this.mDetails.getPK();
/*     */   }
/*     */ 
/*     */   public void setDetails(ImportGridCellEVO details)
/*     */   {
/*  79 */     this.mDetails = details.deepClone();
/*     */   }
/*     */ 
/*     */   public ImportGridCellPK create()
/*     */     throws DuplicateNameValidationException, ValidationException
/*     */   {
/*  88 */     doCreate();
/*     */ 
/*  90 */     return this.mDetails.getPK();
/*     */   }
/*     */ 
/*     */   public void load(ImportGridCellPK pk)
/*     */     throws ValidationException
/*     */   {
/* 100 */     doLoad(pk);
/*     */   }
/*     */ 
/*     */   public void store()
/*     */     throws DuplicateNameValidationException, VersionValidationException, ValidationException
/*     */   {
/* 109 */     doStore();
/*     */   }
/*     */ 
/*     */   public void remove()
/*     */   {
/* 118 */     doRemove();
/*     */   }
/*     */ 
/*     */   public ImportGridCellPK findByPrimaryKey(ImportGridCellPK pk_)
/*     */     throws ValidationException
/*     */   {
/* 127 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/* 128 */     if (exists(pk_))
/*     */     {
/* 130 */       if (timer != null) {
/* 131 */         timer.logDebug("findByPrimaryKey", pk_);
/*     */       }
/* 133 */       return pk_;
/*     */     }
/*     */ 
/* 136 */     throw new ValidationException(pk_ + " not found");
/*     */   }
/*     */ 
/*     */   protected boolean exists(ImportGridCellPK pk)
/*     */   {
/* 158 */     PreparedStatement stmt = null;
/* 159 */     ResultSet resultSet = null;
/* 160 */     boolean returnValue = false;
/*     */     try
/*     */     {
/* 164 */       stmt = getConnection().prepareStatement("select GRID_ID,ROW_NUMBER,COLUMN_NUMBER from IMPORT_GRID_CELL where    GRID_ID = ? AND ROW_NUMBER = ? AND COLUMN_NUMBER = ? ");
/*     */ 
/* 166 */       int col = 1;
/* 167 */       stmt.setInt(col++, pk.getGridId());
/* 168 */       stmt.setInt(col++, pk.getRowNumber());
/* 169 */       stmt.setInt(col++, pk.getColumnNumber());
/*     */ 
/* 171 */       resultSet = stmt.executeQuery();
/*     */ 
/* 173 */       if (!resultSet.next())
/* 174 */         returnValue = false;
/*     */       else
/* 176 */         returnValue = true;
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/* 180 */       throw handleSQLException(pk, "select GRID_ID,ROW_NUMBER,COLUMN_NUMBER from IMPORT_GRID_CELL where    GRID_ID = ? AND ROW_NUMBER = ? AND COLUMN_NUMBER = ? ", sqle);
/*     */     }
/*     */     finally
/*     */     {
/* 184 */       closeResultSet(resultSet);
/* 185 */       closeStatement(stmt);
/* 186 */       closeConnection();
/*     */     }
/* 188 */     return returnValue;
/*     */   }
/*     */ 
/*     */   private ImportGridCellEVO getEvoFromJdbc(ResultSet resultSet_)
/*     */     throws SQLException
/*     */   {
/* 203 */     int col = 1;
/* 204 */     ImportGridCellEVO evo = new ImportGridCellEVO(resultSet_.getInt(col++), resultSet_.getInt(col++), resultSet_.getInt(col++), resultSet_.getString(col++));
/*     */ 
/* 211 */     return evo;
/*     */   }
/*     */ 
/*     */   private int putEvoKeysToJdbc(ImportGridCellEVO evo_, PreparedStatement stmt_, int startCol_) throws SQLException
/*     */   {
/* 216 */     int col = startCol_;
/* 217 */     stmt_.setInt(col++, evo_.getGridId());
/* 218 */     stmt_.setInt(col++, evo_.getRowNumber());
/* 219 */     stmt_.setInt(col++, evo_.getColumnNumber());
/* 220 */     return col;
/*     */   }
/*     */ 
/*     */   private int putEvoDataToJdbc(ImportGridCellEVO evo_, PreparedStatement stmt_, int startCol_) throws SQLException
/*     */   {
/* 225 */     int col = startCol_;
/* 226 */     stmt_.setString(col++, evo_.getUserData());
/* 227 */     return col;
/*     */   }
/*     */ 
/*     */   protected void doLoad(ImportGridCellPK pk)
/*     */     throws ValidationException
/*     */   {
/* 245 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*     */ 
/* 247 */     PreparedStatement stmt = null;
/* 248 */     ResultSet resultSet = null;
/*     */     try
/*     */     {
/* 252 */       stmt = getConnection().prepareStatement("select IMPORT_GRID_CELL.GRID_ID,IMPORT_GRID_CELL.ROW_NUMBER,IMPORT_GRID_CELL.COLUMN_NUMBER,IMPORT_GRID_CELL.USER_DATA from IMPORT_GRID_CELL where    GRID_ID = ? AND ROW_NUMBER = ? AND COLUMN_NUMBER = ? ");
/*     */ 
/* 255 */       int col = 1;
/* 256 */       stmt.setInt(col++, pk.getGridId());
/* 257 */       stmt.setInt(col++, pk.getRowNumber());
/* 258 */       stmt.setInt(col++, pk.getColumnNumber());
/*     */ 
/* 260 */       resultSet = stmt.executeQuery();
/*     */ 
/* 262 */       if (!resultSet.next()) {
/* 263 */         throw new ValidationException(getEntityName() + " select of " + pk + " not found");
/*     */       }
/*     */ 
/* 266 */       this.mDetails = getEvoFromJdbc(resultSet);
/* 267 */       if (this.mDetails.isModified())
/* 268 */         this._log.info("doLoad", this.mDetails);
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/* 272 */       throw handleSQLException(pk, "select IMPORT_GRID_CELL.GRID_ID,IMPORT_GRID_CELL.ROW_NUMBER,IMPORT_GRID_CELL.COLUMN_NUMBER,IMPORT_GRID_CELL.USER_DATA from IMPORT_GRID_CELL where    GRID_ID = ? AND ROW_NUMBER = ? AND COLUMN_NUMBER = ? ", sqle);
/*     */     }
/*     */     finally
/*     */     {
/* 276 */       closeResultSet(resultSet);
/* 277 */       closeStatement(stmt);
/* 278 */       closeConnection();
/*     */ 
/* 280 */       if (timer != null)
/* 281 */         timer.logDebug("doLoad", pk);
/*     */     }
/*     */   }
/*     */ 
/*     */   protected void doCreate()
/*     */     throws DuplicateNameValidationException, ValidationException
/*     */   {
/* 306 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/* 307 */     PreparedStatement stmt = null;
/*     */     try
/*     */     {
/* 311 */       stmt = getConnection().prepareStatement("insert into IMPORT_GRID_CELL ( GRID_ID,ROW_NUMBER,COLUMN_NUMBER,USER_DATA) values ( ?,?,?,?)");
/*     */ 
/* 314 */       int col = 1;
/* 315 */       col = putEvoKeysToJdbc(this.mDetails, stmt, col);
/* 316 */       col = putEvoDataToJdbc(this.mDetails, stmt, col);
/*     */ 
/* 319 */       int resultCount = stmt.executeUpdate();
/* 320 */       if (resultCount != 1)
/*     */       {
/* 322 */         throw new RuntimeException(getEntityName() + " insert failed (" + this.mDetails.getPK() + "): resultCount=" + resultCount);
/*     */       }
/*     */ 
/* 325 */       this.mDetails.reset();
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/* 329 */       throw handleSQLException(this.mDetails.getPK(), "insert into IMPORT_GRID_CELL ( GRID_ID,ROW_NUMBER,COLUMN_NUMBER,USER_DATA) values ( ?,?,?,?)", sqle);
/*     */     }
/*     */     finally
/*     */     {
/* 333 */       closeStatement(stmt);
/* 334 */       closeConnection();
/*     */ 
/* 336 */       if (timer != null)
/* 337 */         timer.logDebug("doCreate", this.mDetails.toString());
/*     */     }
/*     */   }
/*     */ 
/*     */   protected void doStore()
/*     */     throws DuplicateNameValidationException, VersionValidationException, ValidationException
/*     */   {
/* 359 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*     */ 
/* 363 */     PreparedStatement stmt = null;
/*     */ 
/* 365 */     boolean mainChanged = this.mDetails.isModified();
/* 366 */     boolean dependantChanged = false;
/*     */     try
/*     */     {
/* 369 */       if ((mainChanged) || (dependantChanged))
/*     */       {
/* 371 */         stmt = getConnection().prepareStatement("update IMPORT_GRID_CELL set USER_DATA = ? where    GRID_ID = ? AND ROW_NUMBER = ? AND COLUMN_NUMBER = ? ");
/*     */ 
/* 374 */         int col = 1;
/* 375 */         col = putEvoDataToJdbc(this.mDetails, stmt, col);
/* 376 */         col = putEvoKeysToJdbc(this.mDetails, stmt, col);
/*     */ 
/* 379 */         int resultCount = stmt.executeUpdate();
/*     */ 
/* 382 */         if (resultCount != 1) {
/* 383 */           throw new RuntimeException(getEntityName() + " update failed (" + getPK() + "): resultCount=" + resultCount);
/*     */         }
/*     */ 
/* 386 */         this.mDetails.reset();
/*     */       }
/*     */ 
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/* 395 */       throw handleSQLException(getPK(), "update IMPORT_GRID_CELL set USER_DATA = ? where    GRID_ID = ? AND ROW_NUMBER = ? AND COLUMN_NUMBER = ? ", sqle);
/*     */     }
/*     */     finally
/*     */     {
/* 399 */       closeStatement(stmt);
/* 400 */       closeConnection();
/*     */ 
/* 402 */       if ((timer != null) && (
/* 403 */         (mainChanged) || (dependantChanged)))
/* 404 */         timer.logDebug("store", this.mDetails.getPK() + "(" + mainChanged + "," + dependantChanged + ")");
/*     */     }
/*     */   }
/*     */ 
/*     */   protected void doRemove()
/*     */   {
/* 424 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*     */ 
/* 429 */     PreparedStatement stmt = null;
/*     */     try
/*     */     {
/* 434 */       stmt = getConnection().prepareStatement("delete from IMPORT_GRID_CELL where    GRID_ID = ? AND ROW_NUMBER = ? AND COLUMN_NUMBER = ? ");
/*     */ 
/* 437 */       int col = 1;
/* 438 */       stmt.setInt(col++, this.mDetails.getGridId());
/* 439 */       stmt.setInt(col++, this.mDetails.getRowNumber());
/* 440 */       stmt.setInt(col++, this.mDetails.getColumnNumber());
/*     */ 
/* 442 */       int resultCount = stmt.executeUpdate();
/*     */ 
/* 444 */       if (resultCount != 1) {
/* 445 */         throw new RuntimeException(getEntityName() + " delete failed (" + getPK() + "): resultCount=" + resultCount);
/*     */       }
/*     */ 
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/* 451 */       throw handleSQLException(getPK(), "delete from IMPORT_GRID_CELL where    GRID_ID = ? AND ROW_NUMBER = ? AND COLUMN_NUMBER = ? ", sqle);
/*     */     }
/*     */     finally
/*     */     {
/* 455 */       closeStatement(stmt);
/* 456 */       closeConnection();
/*     */ 
/* 458 */       if (timer != null)
/* 459 */         timer.logDebug("remove", this.mDetails.getPK());
/*     */     }
/*     */   }
/*     */ 
/*     */   public ImportGridCellEVO getDetails(ImportGridCellPK pk, String dependants)
/*     */     throws ValidationException
/*     */   {
/* 478 */     return getDetails(new ImportGridCellCK(pk), dependants);
/*     */   }
/*     */ 
/*     */   public ImportGridCellEVO getDetails(ImportGridCellCK paramCK, String dependants)
/*     */     throws ValidationException
/*     */   {
/* 492 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*     */ 
/* 495 */     if (this.mDetails == null) {
/* 496 */       doLoad(paramCK.getImportGridCellPK());
/*     */     }
/* 498 */     else if (!this.mDetails.getPK().equals(paramCK.getImportGridCellPK())) {
/* 499 */       doLoad(paramCK.getImportGridCellPK());
/*     */     }
/*     */ 
/* 502 */     ImportGridCellEVO details = new ImportGridCellEVO();
/* 503 */     details = this.mDetails.deepClone();
/*     */ 
/* 505 */     if (timer != null) {
/* 506 */       timer.logDebug("getDetails", paramCK + " " + dependants);
/*     */     }
/* 508 */     return details;
/*     */   }
/*     */ 
/*     */   public ImportGridCellEVO getDetails(String dependants)
/*     */     throws ValidationException
/*     */   {
/* 514 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*     */ 
/* 518 */     ImportGridCellEVO details = this.mDetails.deepClone();
/*     */ 
/* 520 */     if (timer != null) {
/* 521 */       timer.logDebug("getDetails", this.mDetails.getPK() + " " + dependants);
/*     */     }
/* 523 */     return details;
/*     */   }
/*     */ 
/*     */   public String getEntityName()
/*     */   {
/* 528 */     return "ImportGridCell";
/*     */   }
/*     */ 
/*     */   public ImportGridCellRef getRef(ImportGridCellPK paramImportGridCellPK)
/*     */     throws ValidationException
/*     */   {
/* 534 */     ImportGridCellEVO evo = getDetails(paramImportGridCellPK, "");
/* 535 */     return evo.getEntityRef("");
/*     */   }
/*     */ 

		public Map<String, String> readFirstRowOfImport(int gridId) {
		    PreparedStatement ps = null;
		    ResultSet rs = null;
		
		    try {
		       ps = this.getConnection().prepareStatement("select row_number, column_number, user_data from import_grid_cell where grid_id = ? and row_number < 2 order by row_number, column_number");
		       ps.setInt(1, gridId);
		       rs = ps.executeQuery();
		       ArrayList sqlex = new ArrayList();
		
		       while(rs.next()) {
		          int result = rs.getInt("row_number");
		          int i$ = rs.getInt("column_number");
		          String entry = rs.getString("user_data");
		          if(result <= 1) {
		             sqlex.add(new Pair(entry, (Object)null));
		          } else {
		             ((Pair)sqlex.get(i$)).setChild2(entry);
		          }
		       }
		
		       HashMap result1 = new HashMap();
		       Iterator i$2 = sqlex.iterator();
		
		       while(i$2.hasNext()) {
		          Pair entry1 = (Pair)i$2.next();
		          result1.put(entry1.getChild1(), entry1.getChild2());
		       }
		
		       HashMap i$1 = result1;
		       return i$1;
		    } catch (SQLException var11) {
		       throw this.handleSQLException("readFirstRowsOfImport", var11);
		    } finally {
		       this.closeResultSet(rs);
		       this.closeStatement(ps);
		       this.closeConnection();
		    }
		 }

/*     */ 
/*     */   public List<Map<String, String>> loadBatch(int numDims, int gridId, int startRow, int endRow, Integer[] colSeqOrder, Integer dtColumn, String[] columnNames, boolean includeOriginalRow)
/*     */   {
/* 611 */     StringTemplate st = getTemplate("queryRowBatch");
/*     */ 
/* 613 */     st.setAttribute("dims", colSeqOrder);
/* 614 */     st.setAttribute("dataType", dtColumn);
/*     */ 
/* 616 */     SqlBuilder builder = new SqlBuilder(new String[] { st.toString() });
/* 617 */     SqlExecutor executor = null;
/* 618 */     ResultSet rs = null;
/*     */     try
/*     */     {
/* 621 */       executor = new SqlExecutor("loadBatch", getDataSource(), builder, this._log);
/*     */ 
/* 623 */       executor.addBindVariable("${gridId}", Integer.valueOf(gridId));
/* 624 */       executor.addBindVariable("${startRow}", new Integer(startRow));
/* 625 */       executor.addBindVariable("${endRow}", new Integer(endRow));
/*     */ 
/* 627 */       rs = executor.getResultSet();
/*     */ 
/* 629 */       List result = new ArrayList();
/*     */ 
/* 631 */       Map currentMap = new HashMap();
/* 632 */       int currentRow = -1;
/*     */ 
/* 637 */       while (rs.next())
/*     */       {
/* 639 */         int row_number = rs.getInt("row_number");
/* 640 */         int column_number = rs.getInt("column_number");
/* 641 */         String user_data = rs.getString("user_data");
/*     */ 
/* 643 */         if (row_number != currentRow)
/*     */         {
/* 645 */           if (currentRow != -1)
/*     */           {
/* 647 */             Map newMap = new HashMap();
/* 648 */             newMap.putAll(currentMap);
/* 649 */             if (includeOriginalRow)
/* 650 */               newMap.put("__row_number__", String.valueOf(currentRow));
/* 651 */             result.add(newMap);
/* 652 */             currentMap.clear();
/*     */           }
/* 654 */           currentRow = row_number;
/*     */         }
/*     */ 
/* 657 */         if (((0 <= column_number ? 1 : 0) & (column_number < columnNames.length ? 1 : 0)) == 0)
/*     */           continue;
/* 659 */         currentMap.put(columnNames[column_number], user_data);
/*     */       }
/*     */ 
/* 663 */       if (!currentMap.isEmpty())
/*     */       {
/* 665 */         if (includeOriginalRow)
/* 666 */           currentMap.put("__row_number__", String.valueOf(currentRow));
/* 667 */         result.add(currentMap);
/*     */       }
/*     */ 
/*     */       return result;
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/* 674 */       throw handleSQLException("loadBatch", sqle);
/*     */     }
/*     */     finally
/*     */     {
/* 678 */       closeResultSet(rs);
/* 679 */       if (executor != null)
/* 680 */         executor.close(); 
/* 680 */     }
/*     */   }
/*     */ 
/*     */   public void applyUpdates(int gridId, List<Object[]> updates, int batchSize)
/*     */   {
/* 692 */     PreparedStatement ps = null;
/*     */     try
/*     */     {
/* 695 */       ps = getConnection().prepareStatement("update import_grid_cell set user_data = ? where grid_id = ? and row_number = ? and column_number = ?");
/*     */ 
/* 698 */       int rowCount = updates.size();
/* 699 */       int currentBatchSize = 0;
/* 700 */       for (int i = 0; i < rowCount; i++)
/*     */       {
/* 702 */         Object[] row = (Object[])updates.get(i);
/* 703 */         Integer rowNumber = (Integer)row[0];
/* 704 */         Integer columnNumber = (Integer)row[1];
/* 705 */         String userData = (String)row[2];
/*     */ 
/* 707 */         int paramNo = 1;
/* 708 */         ps.setString(paramNo++, userData);
/* 709 */         ps.setInt(paramNo++, gridId);
/* 710 */         ps.setInt(paramNo++, rowNumber.intValue());
/* 711 */         ps.setInt(paramNo++, columnNumber.intValue());
/*     */ 
/* 713 */         ps.addBatch();
/*     */ 
/* 715 */         currentBatchSize++;
/*     */ 
/* 717 */         if (currentBatchSize < batchSize)
/*     */           continue;
/* 719 */         ps.executeBatch();
/* 720 */         currentBatchSize = 0;
/*     */       }
/*     */ 
/* 724 */       if (currentBatchSize > 0)
/* 725 */         ps.executeBatch();
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/* 729 */       throw handleSQLException("applyUpdates", sqle);
/*     */     }
/*     */     finally
/*     */     {
/* 733 */       closeStatement(ps);
/* 734 */       closeConnection();
/*     */     }
/*     */   }
/*     */ }

/* Location:           /home/oracle/coa/cp.ear/cp.ear_orginal/cp-server.jar
 * Qualified Name:     com.cedar.cp.ejb.impl.model.cc.imp.dyn.ImportGridCellDAO
 * JD-Core Version:    0.6.0
 */