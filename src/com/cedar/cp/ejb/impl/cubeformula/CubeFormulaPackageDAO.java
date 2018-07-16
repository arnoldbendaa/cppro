/*     */ package com.cedar.cp.ejb.impl.cubeformula;
/*     */ 
/*     */ import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.Map;

import javax.sql.DataSource;

import com.cedar.cp.api.base.DuplicateNameValidationException;
import com.cedar.cp.api.base.EntityList;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.base.VersionValidationException;
import com.cedar.cp.dto.base.EntityListImpl;
import com.cedar.cp.dto.cubeformula.AllPackagesForFinanceCubeELO;
import com.cedar.cp.dto.cubeformula.CubeFormulaPackageCK;
import com.cedar.cp.dto.cubeformula.CubeFormulaPackagePK;
import com.cedar.cp.dto.cubeformula.CubeFormulaPackageRefImpl;
import com.cedar.cp.dto.model.FinanceCubeCK;
import com.cedar.cp.dto.model.FinanceCubePK;
import com.cedar.cp.dto.model.FinanceCubeRefImpl;
import com.cedar.cp.dto.model.ModelCK;
import com.cedar.cp.dto.model.ModelPK;
import com.cedar.cp.dto.model.ModelRefImpl;
import com.cedar.cp.ejb.impl.base.AbstractDAO;
import com.cedar.cp.ejb.impl.model.FinanceCubeEVO;
import com.cedar.cp.util.Log;
import com.cedar.cp.util.Timer;
import com.cedar.cp.util.common.JdbcUtils;
/*     */ 
/*     */ public class CubeFormulaPackageDAO extends AbstractDAO
/*     */ {
/*  36 */   Log _log = new Log(getClass());
/*     */   private static final String SQL_SELECT_COLUMNS = "select CUBE_FORMULA_PACKAGE.CUBE_FORMULA_PACKAGE_ID,CUBE_FORMULA_PACKAGE.FINANCE_CUBE_ID,CUBE_FORMULA_PACKAGE.PACKAGE_GROUP_INDEX,CUBE_FORMULA_PACKAGE.LINE_COUNT";
/*     */   protected static final String SQL_LOAD = " from CUBE_FORMULA_PACKAGE where    CUBE_FORMULA_PACKAGE_ID = ? ";
/*     */   protected static final String SQL_CREATE = "insert into CUBE_FORMULA_PACKAGE ( CUBE_FORMULA_PACKAGE_ID,FINANCE_CUBE_ID,PACKAGE_GROUP_INDEX,LINE_COUNT) values ( ?,?,?,?)";
/*     */   protected static final String SQL_STORE = "update CUBE_FORMULA_PACKAGE set FINANCE_CUBE_ID = ?,PACKAGE_GROUP_INDEX = ?,LINE_COUNT = ? where    CUBE_FORMULA_PACKAGE_ID = ? ";
/* 296 */   protected static String SQL_ALL_PACKAGES_FOR_FINANCE_CUBE = "select 0       ,MODEL.MODEL_ID      ,MODEL.VIS_ID      ,FINANCE_CUBE.FINANCE_CUBE_ID      ,FINANCE_CUBE.VIS_ID      ,CUBE_FORMULA_PACKAGE.CUBE_FORMULA_PACKAGE_ID      ,CUBE_FORMULA_PACKAGE.PACKAGE_GROUP_INDEX from CUBE_FORMULA_PACKAGE    ,MODEL    ,FINANCE_CUBE where 1=1   and CUBE_FORMULA_PACKAGE.FINANCE_CUBE_ID = FINANCE_CUBE.FINANCE_CUBE_ID   and FINANCE_CUBE.MODEL_ID = MODEL.MODEL_ID  and  CUBE_FORMULA_PACKAGE.FINANCE_CUBE_ID = ?";
/*     */   protected static final String SQL_DELETE_BATCH = "delete from CUBE_FORMULA_PACKAGE where    CUBE_FORMULA_PACKAGE_ID = ? ";
/*     */   public static final String SQL_BULK_GET_ALL = " from CUBE_FORMULA_PACKAGE,FINANCE_CUBE where 1=1 and CUBE_FORMULA_PACKAGE.FINANCE_CUBE_ID = FINANCE_CUBE.FINANCE_CUBE_ID and FINANCE_CUBE.MODEL_ID = ? order by  CUBE_FORMULA_PACKAGE.FINANCE_CUBE_ID ,CUBE_FORMULA_PACKAGE.CUBE_FORMULA_PACKAGE_ID";
/*     */   protected static final String SQL_GET_ALL = " from CUBE_FORMULA_PACKAGE where    FINANCE_CUBE_ID = ? ";
/* 854 */   JdbcUtils.ColType[] sQUERY_ACTIVE_PACKAGES_COL_INFO = { new JdbcUtils.ColType("cube_formula_package_id", 0), new JdbcUtils.ColType("package_group_index", 0) };
/*     */   protected CubeFormulaPackageEVO mDetails;
/*     */ 
/*     */   public CubeFormulaPackageDAO(Connection connection)
/*     */   {
/*  43 */     super(connection);
/*     */   }
/*     */ 
/*     */   public CubeFormulaPackageDAO()
/*     */   {
/*     */   }
/*     */ 
/*     */   public CubeFormulaPackageDAO(DataSource ds)
/*     */   {
/*  59 */     super(ds);
/*     */   }
/*     */ 
/*     */   protected CubeFormulaPackagePK getPK()
/*     */   {
/*  67 */     return this.mDetails.getPK();
/*     */   }
/*     */ 
/*     */   public void setDetails(CubeFormulaPackageEVO details)
/*     */   {
/*  76 */     this.mDetails = details.deepClone();
/*     */   }
/*     */ 
/*     */   private CubeFormulaPackageEVO getEvoFromJdbc(ResultSet resultSet_)
/*     */     throws SQLException
/*     */   {
/*  91 */     int col = 1;
/*  92 */     CubeFormulaPackageEVO evo = new CubeFormulaPackageEVO(resultSet_.getInt(col++), resultSet_.getInt(col++), resultSet_.getInt(col++), resultSet_.getInt(col++));
/*     */ 
/*  99 */     return evo;
/*     */   }
/*     */ 
/*     */   private int putEvoKeysToJdbc(CubeFormulaPackageEVO evo_, PreparedStatement stmt_, int startCol_) throws SQLException
/*     */   {
/* 104 */     int col = startCol_;
/* 105 */     stmt_.setInt(col++, evo_.getCubeFormulaPackageId());
/* 106 */     return col;
/*     */   }
/*     */ 
/*     */   private int putEvoDataToJdbc(CubeFormulaPackageEVO evo_, PreparedStatement stmt_, int startCol_) throws SQLException
/*     */   {
/* 111 */     int col = startCol_;
/* 112 */     stmt_.setInt(col++, evo_.getFinanceCubeId());
/* 113 */     stmt_.setInt(col++, evo_.getPackageGroupIndex());
/* 114 */     stmt_.setInt(col++, evo_.getLineCount());
/* 115 */     return col;
/*     */   }
/*     */ 
/*     */   protected void doLoad(CubeFormulaPackagePK pk)
/*     */     throws ValidationException
/*     */   {
/* 131 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*     */ 
/* 133 */     PreparedStatement stmt = null;
/* 134 */     ResultSet resultSet = null;
/*     */     try
/*     */     {
/* 138 */       stmt = getConnection().prepareStatement("select CUBE_FORMULA_PACKAGE.CUBE_FORMULA_PACKAGE_ID,CUBE_FORMULA_PACKAGE.FINANCE_CUBE_ID,CUBE_FORMULA_PACKAGE.PACKAGE_GROUP_INDEX,CUBE_FORMULA_PACKAGE.LINE_COUNT from CUBE_FORMULA_PACKAGE where    CUBE_FORMULA_PACKAGE_ID = ? ");
/*     */ 
/* 141 */       int col = 1;
/* 142 */       stmt.setInt(col++, pk.getCubeFormulaPackageId());
/*     */ 
/* 144 */       resultSet = stmt.executeQuery();
/*     */ 
/* 146 */       if (!resultSet.next()) {
/* 147 */         throw new ValidationException(getEntityName() + " select of " + pk + " not found");
/*     */       }
/*     */ 
/* 150 */       this.mDetails = getEvoFromJdbc(resultSet);
/* 151 */       if (this.mDetails.isModified())
/* 152 */         this._log.info("doLoad", this.mDetails);
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/* 156 */       throw handleSQLException(pk, "select CUBE_FORMULA_PACKAGE.CUBE_FORMULA_PACKAGE_ID,CUBE_FORMULA_PACKAGE.FINANCE_CUBE_ID,CUBE_FORMULA_PACKAGE.PACKAGE_GROUP_INDEX,CUBE_FORMULA_PACKAGE.LINE_COUNT from CUBE_FORMULA_PACKAGE where    CUBE_FORMULA_PACKAGE_ID = ? ", sqle);
/*     */     }
/*     */     finally
/*     */     {
/* 160 */       closeResultSet(resultSet);
/* 161 */       closeStatement(stmt);
/* 162 */       closeConnection();
/*     */ 
/* 164 */       if (timer != null)
/* 165 */         timer.logDebug("doLoad", pk);
/*     */     }
/*     */   }
/*     */ 
/*     */   protected void doCreate()
/*     */     throws DuplicateNameValidationException, ValidationException
/*     */   {
/* 190 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/* 191 */     PreparedStatement stmt = null;
/*     */     try
/*     */     {
/* 195 */       stmt = getConnection().prepareStatement("insert into CUBE_FORMULA_PACKAGE ( CUBE_FORMULA_PACKAGE_ID,FINANCE_CUBE_ID,PACKAGE_GROUP_INDEX,LINE_COUNT) values ( ?,?,?,?)");
/*     */ 
/* 198 */       int col = 1;
/* 199 */       col = putEvoKeysToJdbc(this.mDetails, stmt, col);
/* 200 */       col = putEvoDataToJdbc(this.mDetails, stmt, col);
/*     */ 
/* 203 */       int resultCount = stmt.executeUpdate();
/* 204 */       if (resultCount != 1)
/*     */       {
/* 206 */         throw new RuntimeException(getEntityName() + " insert failed (" + this.mDetails.getPK() + "): resultCount=" + resultCount);
/*     */       }
/*     */ 
/* 209 */       this.mDetails.reset();
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/* 213 */       throw handleSQLException(this.mDetails.getPK(), "insert into CUBE_FORMULA_PACKAGE ( CUBE_FORMULA_PACKAGE_ID,FINANCE_CUBE_ID,PACKAGE_GROUP_INDEX,LINE_COUNT) values ( ?,?,?,?)", sqle);
/*     */     }
/*     */     finally
/*     */     {
/* 217 */       closeStatement(stmt);
/* 218 */       closeConnection();
/*     */ 
/* 220 */       if (timer != null)
/* 221 */         timer.logDebug("doCreate", this.mDetails.toString());
/*     */     }
/*     */   }
/*     */ 
/*     */   protected void doStore()
/*     */     throws DuplicateNameValidationException, VersionValidationException, ValidationException
/*     */   {
/* 243 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*     */ 
/* 247 */     PreparedStatement stmt = null;
/*     */ 
/* 249 */     boolean mainChanged = this.mDetails.isModified();
/* 250 */     boolean dependantChanged = false;
/*     */     try
/*     */     {
/* 253 */       if ((mainChanged) || (dependantChanged))
/*     */       {
/* 255 */         stmt = getConnection().prepareStatement("update CUBE_FORMULA_PACKAGE set FINANCE_CUBE_ID = ?,PACKAGE_GROUP_INDEX = ?,LINE_COUNT = ? where    CUBE_FORMULA_PACKAGE_ID = ? ");
/*     */ 
/* 258 */         int col = 1;
/* 259 */         col = putEvoDataToJdbc(this.mDetails, stmt, col);
/* 260 */         col = putEvoKeysToJdbc(this.mDetails, stmt, col);
/*     */ 
/* 263 */         int resultCount = stmt.executeUpdate();
/*     */ 
/* 266 */         if (resultCount != 1) {
/* 267 */           throw new RuntimeException(getEntityName() + " update failed (" + getPK() + "): resultCount=" + resultCount);
/*     */         }
/*     */ 
/* 270 */         this.mDetails.reset();
/*     */       }
/*     */ 
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/* 279 */       throw handleSQLException(getPK(), "update CUBE_FORMULA_PACKAGE set FINANCE_CUBE_ID = ?,PACKAGE_GROUP_INDEX = ?,LINE_COUNT = ? where    CUBE_FORMULA_PACKAGE_ID = ? ", sqle);
/*     */     }
/*     */     finally
/*     */     {
/* 283 */       closeStatement(stmt);
/* 284 */       closeConnection();
/*     */ 
/* 286 */       if ((timer != null) && (
/* 287 */         (mainChanged) || (dependantChanged)))
/* 288 */         timer.logDebug("store", this.mDetails.getPK() + "(" + mainChanged + "," + dependantChanged + ")");
/*     */     }
/*     */   }
/*     */ 
/*     */   public AllPackagesForFinanceCubeELO getAllPackagesForFinanceCube(int param1)
/*     */   {
/* 332 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/* 333 */     PreparedStatement stmt = null;
/* 334 */     ResultSet resultSet = null;
/* 335 */     AllPackagesForFinanceCubeELO results = new AllPackagesForFinanceCubeELO();
/*     */     try
/*     */     {
/* 338 */       stmt = getConnection().prepareStatement(SQL_ALL_PACKAGES_FOR_FINANCE_CUBE);
/* 339 */       int col = 1;
/* 340 */       stmt.setInt(col++, param1);
/* 341 */       resultSet = stmt.executeQuery();
/* 342 */       while (resultSet.next())
/*     */       {
/* 344 */         col = 2;
/*     */ 
/* 347 */         ModelPK pkModel = new ModelPK(resultSet.getInt(col++));
/*     */ 
/* 350 */         String textModel = resultSet.getString(col++);
/*     */ 
/* 352 */         FinanceCubePK pkFinanceCube = new FinanceCubePK(resultSet.getInt(col++));
/*     */ 
/* 355 */         String textFinanceCube = resultSet.getString(col++);
/*     */ 
/* 358 */         CubeFormulaPackagePK pkCubeFormulaPackage = new CubeFormulaPackagePK(resultSet.getInt(col++));
/*     */ 
/* 361 */         String textCubeFormulaPackage = "";
/*     */ 
/* 366 */         FinanceCubeCK ckFinanceCube = new FinanceCubeCK(pkModel, pkFinanceCube);
/*     */ 
/* 372 */         CubeFormulaPackageCK ckCubeFormulaPackage = new CubeFormulaPackageCK(pkModel, pkFinanceCube, pkCubeFormulaPackage);
/*     */ 
/* 379 */         ModelRefImpl erModel = new ModelRefImpl(pkModel, textModel);
/*     */ 
/* 385 */         FinanceCubeRefImpl erFinanceCube = new FinanceCubeRefImpl(ckFinanceCube, textFinanceCube);
/*     */ 
/* 391 */         CubeFormulaPackageRefImpl erCubeFormulaPackage = new CubeFormulaPackageRefImpl(ckCubeFormulaPackage, textCubeFormulaPackage);
/*     */ 
/* 396 */         int col1 = resultSet.getInt(col++);
/*     */ 
/* 399 */         results.add(erCubeFormulaPackage, erFinanceCube, erModel, col1);
/*     */       }
/*     */ 
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/* 409 */       throw handleSQLException(SQL_ALL_PACKAGES_FOR_FINANCE_CUBE, sqle);
/*     */     }
/*     */     finally
/*     */     {
/* 413 */       closeResultSet(resultSet);
/* 414 */       closeStatement(stmt);
/* 415 */       closeConnection();
/*     */     }
/*     */ 
/* 418 */     if (timer != null) {
/* 419 */       timer.logDebug("getAllPackagesForFinanceCube", " FinanceCubeId=" + param1 + " items=" + results.size());
/*     */     }
/*     */ 
/* 424 */     return results;
/*     */   }
/*     */ 
/*     */   public boolean update(Map items)
/*     */     throws DuplicateNameValidationException, VersionValidationException, ValidationException
/*     */   {
/* 441 */     if (items == null) {
/* 442 */       return false;
/*     */     }
/* 444 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/* 445 */     PreparedStatement deleteStmt = null;
/*     */ 
/* 447 */     boolean somethingChanged = false;
/*     */     try
/*     */     {
/* 452 */       Iterator iter2 = new ArrayList(items.values()).iterator();
/* 453 */       while (iter2.hasNext())
/*     */       {
/* 455 */         this.mDetails = ((CubeFormulaPackageEVO)iter2.next());
/*     */ 
/* 458 */         if (!this.mDetails.deletePending())
/*     */           continue;
/* 460 */         somethingChanged = true;
/*     */ 
/* 463 */         if (deleteStmt == null) {
/* 464 */           deleteStmt = getConnection().prepareStatement("delete from CUBE_FORMULA_PACKAGE where    CUBE_FORMULA_PACKAGE_ID = ? ");
/*     */         }
/*     */ 
/* 467 */         int col = 1;
/* 468 */         deleteStmt.setInt(col++, this.mDetails.getCubeFormulaPackageId());
/*     */ 
/* 470 */         if (this._log.isDebugEnabled()) {
/* 471 */           this._log.debug("update", "CubeFormulaPackage deleting CubeFormulaPackageId=" + this.mDetails.getCubeFormulaPackageId());
/*     */         }
/*     */ 
/* 476 */         deleteStmt.addBatch();
/*     */ 
/* 479 */         items.remove(this.mDetails.getPK());
/*     */       }
/*     */ 
/* 484 */       if (deleteStmt != null)
/*     */       {
/* 486 */         Timer timer2 = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*     */ 
/* 488 */         deleteStmt.executeBatch();
/*     */ 
/* 490 */         if (timer2 != null) {
/* 491 */           timer2.logDebug("update", "delete batch");
/*     */         }
/*     */       }
/*     */ 
/* 495 */       Iterator iter1 = items.values().iterator();
/* 496 */       while (iter1.hasNext())
/*     */       {
/* 498 */         this.mDetails = ((CubeFormulaPackageEVO)iter1.next());
/*     */ 
/* 500 */         if (this.mDetails.insertPending())
/*     */         {
/* 502 */           somethingChanged = true;
/* 503 */           doCreate(); continue;
/*     */         }
/*     */ 
/* 506 */         if (!this.mDetails.isModified())
/*     */           continue;
/* 508 */         somethingChanged = true;
/* 509 */         doStore();
/*     */       }
/*     */ 
/* 520 */       boolean bool1 = somethingChanged;
/*     */       return bool1;
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/* 524 */       throw handleSQLException("delete from CUBE_FORMULA_PACKAGE where    CUBE_FORMULA_PACKAGE_ID = ? ", sqle);
/*     */     }
/*     */     finally
/*     */     {
/* 528 */       if (deleteStmt != null)
/*     */       {
/* 530 */         closeStatement(deleteStmt);
/* 531 */         closeConnection();
/*     */       }
/*     */ 
/* 534 */       this.mDetails = null;
/*     */ 
/* 536 */       if ((somethingChanged) && 
/* 537 */         (timer != null))
/* 538 */         timer.logDebug("update", "collection"); 
/* 538 */     }
/*     */   }
/*     */ 
/*     */   public void bulkGetAll(ModelPK entityPK, Collection owners, String dependants)
/*     */   {
/* 561 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*     */ 
/* 563 */     PreparedStatement stmt = null;
/* 564 */     ResultSet resultSet = null;
/*     */ 
/* 566 */     int itemCount = 0;
/*     */ 
/* 568 */     FinanceCubeEVO owningEVO = null;
/* 569 */     Iterator ownersIter = owners.iterator();
/* 570 */     while (ownersIter.hasNext())
/*     */     {
/* 572 */       owningEVO = (FinanceCubeEVO)ownersIter.next();
/* 573 */       owningEVO.setCubeFormulaPackagesAllItemsLoaded(true);
/*     */     }
/* 575 */     ownersIter = owners.iterator();
/* 576 */     owningEVO = (FinanceCubeEVO)ownersIter.next();
/* 577 */     Collection theseItems = null;
/*     */     try
/*     */     {
/* 581 */       stmt = getConnection().prepareStatement("select CUBE_FORMULA_PACKAGE.CUBE_FORMULA_PACKAGE_ID,CUBE_FORMULA_PACKAGE.FINANCE_CUBE_ID,CUBE_FORMULA_PACKAGE.PACKAGE_GROUP_INDEX,CUBE_FORMULA_PACKAGE.LINE_COUNT from CUBE_FORMULA_PACKAGE,FINANCE_CUBE where 1=1 and CUBE_FORMULA_PACKAGE.FINANCE_CUBE_ID = FINANCE_CUBE.FINANCE_CUBE_ID and FINANCE_CUBE.MODEL_ID = ? order by  CUBE_FORMULA_PACKAGE.FINANCE_CUBE_ID ,CUBE_FORMULA_PACKAGE.CUBE_FORMULA_PACKAGE_ID");
/*     */ 
/* 583 */       int col = 1;
/* 584 */       stmt.setInt(col++, entityPK.getModelId());
/*     */ 
/* 586 */       resultSet = stmt.executeQuery();
/*     */ 
/* 589 */       while (resultSet.next())
/*     */       {
/* 591 */         itemCount++;
/* 592 */         this.mDetails = getEvoFromJdbc(resultSet);
/*     */ 
/* 597 */         while (this.mDetails.getFinanceCubeId() != owningEVO.getFinanceCubeId())
/*     */         {
/* 601 */           if (!ownersIter.hasNext())
/*     */           {
/* 603 */             this._log.debug("bulkGetAll", "can't find owning [FinanceCubeId=" + this.mDetails.getFinanceCubeId() + "] for " + this.mDetails.getPK());
/*     */ 
/* 607 */             this._log.debug("bulkGetAll", "loaded owners are:");
/* 608 */             ownersIter = owners.iterator();
/* 609 */             while (ownersIter.hasNext())
/*     */             {
/* 611 */               owningEVO = (FinanceCubeEVO)ownersIter.next();
/* 612 */               this._log.debug("bulkGetAll", "    " + owningEVO.toString());
/*     */             }
/* 614 */             throw new IllegalStateException("can't find owner");
/*     */           }
/* 616 */           owningEVO = (FinanceCubeEVO)ownersIter.next();
/*     */         }
/* 618 */         if (owningEVO.getCubeFormulaPackages() == null)
/*     */         {
/* 620 */           theseItems = new ArrayList();
/* 621 */           owningEVO.setCubeFormulaPackages(theseItems);
/* 622 */           owningEVO.setCubeFormulaPackagesAllItemsLoaded(true);
/*     */         }
/* 624 */         theseItems.add(this.mDetails);
/*     */       }
/*     */ 
/* 627 */       if (timer != null) {
/* 628 */         timer.logDebug("bulkGetAll", "items=" + itemCount);
/*     */       }
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/* 633 */       throw handleSQLException("select CUBE_FORMULA_PACKAGE.CUBE_FORMULA_PACKAGE_ID,CUBE_FORMULA_PACKAGE.FINANCE_CUBE_ID,CUBE_FORMULA_PACKAGE.PACKAGE_GROUP_INDEX,CUBE_FORMULA_PACKAGE.LINE_COUNT from CUBE_FORMULA_PACKAGE,FINANCE_CUBE where 1=1 and CUBE_FORMULA_PACKAGE.FINANCE_CUBE_ID = FINANCE_CUBE.FINANCE_CUBE_ID and FINANCE_CUBE.MODEL_ID = ? order by  CUBE_FORMULA_PACKAGE.FINANCE_CUBE_ID ,CUBE_FORMULA_PACKAGE.CUBE_FORMULA_PACKAGE_ID", sqle);
/*     */     }
/*     */     finally
/*     */     {
/* 637 */       closeResultSet(resultSet);
/* 638 */       closeStatement(stmt);
/* 639 */       closeConnection();
/*     */ 
/* 641 */       this.mDetails = null;
/*     */     }
/*     */   }
/*     */ 
/*     */   public Collection getAll(int selectFinanceCubeId, String dependants, Collection currentList)
/*     */   {
/* 666 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/* 667 */     PreparedStatement stmt = null;
/* 668 */     ResultSet resultSet = null;
/*     */ 
/* 670 */     ArrayList items = new ArrayList();
/*     */     try
/*     */     {
/* 674 */       stmt = getConnection().prepareStatement("select CUBE_FORMULA_PACKAGE.CUBE_FORMULA_PACKAGE_ID,CUBE_FORMULA_PACKAGE.FINANCE_CUBE_ID,CUBE_FORMULA_PACKAGE.PACKAGE_GROUP_INDEX,CUBE_FORMULA_PACKAGE.LINE_COUNT from CUBE_FORMULA_PACKAGE where    FINANCE_CUBE_ID = ? ");
/*     */ 
/* 676 */       int col = 1;
/* 677 */       stmt.setInt(col++, selectFinanceCubeId);
/*     */ 
/* 679 */       resultSet = stmt.executeQuery();
/*     */ 
/* 681 */       while (resultSet.next())
/*     */       {
/* 683 */         this.mDetails = getEvoFromJdbc(resultSet);
/*     */ 
/* 686 */         items.add(this.mDetails);
/*     */       }
/*     */ 
/* 689 */       if (currentList != null)
/*     */       {
/* 692 */         ListIterator iter = items.listIterator();
/* 693 */         CubeFormulaPackageEVO currentEVO = null;
/* 694 */         CubeFormulaPackageEVO newEVO = null;
/* 695 */         while (iter.hasNext())
/*     */         {
/* 697 */           newEVO = (CubeFormulaPackageEVO)iter.next();
/* 698 */           Iterator iter2 = currentList.iterator();
/* 699 */           while (iter2.hasNext())
/*     */           {
/* 701 */             currentEVO = (CubeFormulaPackageEVO)iter2.next();
/* 702 */             if (!currentEVO.getPK().equals(newEVO.getPK()))
/*     */               continue;
/* 704 */             iter.set(currentEVO);
/*     */           }
/*     */ 
/*     */         }
/*     */ 
/* 710 */         Iterator iter2 = currentList.iterator();
/* 711 */         while (iter2.hasNext())
/*     */         {
/* 713 */           currentEVO = (CubeFormulaPackageEVO)iter2.next();
/* 714 */           if (currentEVO.insertPending()) {
/* 715 */             items.add(currentEVO);
/*     */           }
/*     */         }
/*     */       }
/* 719 */       this.mDetails = null;
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/* 723 */       throw handleSQLException("select CUBE_FORMULA_PACKAGE.CUBE_FORMULA_PACKAGE_ID,CUBE_FORMULA_PACKAGE.FINANCE_CUBE_ID,CUBE_FORMULA_PACKAGE.PACKAGE_GROUP_INDEX,CUBE_FORMULA_PACKAGE.LINE_COUNT from CUBE_FORMULA_PACKAGE where    FINANCE_CUBE_ID = ? ", sqle);
/*     */     }
/*     */     finally
/*     */     {
/* 727 */       closeResultSet(resultSet);
/* 728 */       closeStatement(stmt);
/* 729 */       closeConnection();
/*     */ 
/* 731 */       if (timer != null) {
/* 732 */         timer.logDebug("getAll", " FinanceCubeId=" + selectFinanceCubeId + " items=" + items.size());
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 737 */     return items;
/*     */   }
/*     */ 
/*     */   public CubeFormulaPackageEVO getDetails(ModelCK paramCK, String dependants)
/*     */     throws ValidationException
/*     */   {
/* 751 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*     */ 
/* 754 */     if (this.mDetails == null) {
/* 755 */       doLoad(((CubeFormulaPackageCK)paramCK).getCubeFormulaPackagePK());
/*     */     }
/* 757 */     else if (!this.mDetails.getPK().equals(((CubeFormulaPackageCK)paramCK).getCubeFormulaPackagePK())) {
/* 758 */       doLoad(((CubeFormulaPackageCK)paramCK).getCubeFormulaPackagePK());
/*     */     }
/*     */ 
/* 761 */     CubeFormulaPackageEVO details = new CubeFormulaPackageEVO();
/* 762 */     details = this.mDetails.deepClone();
/*     */ 
/* 764 */     if (timer != null) {
/* 765 */       timer.logDebug("getDetails", paramCK + " " + dependants);
/*     */     }
/* 767 */     return details;
/*     */   }
/*     */ 
/*     */   public CubeFormulaPackageEVO getDetails(ModelCK paramCK, CubeFormulaPackageEVO paramEVO, String dependants)
/*     */     throws ValidationException
/*     */   {
/* 773 */     CubeFormulaPackageEVO savedEVO = this.mDetails;
/* 774 */     this.mDetails = paramEVO;
/* 775 */     CubeFormulaPackageEVO newEVO = getDetails(paramCK, dependants);
/* 776 */     this.mDetails = savedEVO;
/* 777 */     return newEVO;
/*     */   }
/*     */ 
/*     */   public CubeFormulaPackageEVO getDetails(String dependants)
/*     */     throws ValidationException
/*     */   {
/* 783 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*     */ 
/* 787 */     CubeFormulaPackageEVO details = this.mDetails.deepClone();
/*     */ 
/* 789 */     if (timer != null) {
/* 790 */       timer.logDebug("getDetails", this.mDetails.getPK() + " " + dependants);
/*     */     }
/* 792 */     return details;
/*     */   }
/*     */ 
/*     */   public String getEntityName()
/*     */   {
/* 797 */     return "CubeFormulaPackage";
/*     */   }
/*     */ 
/*     */   public CubeFormulaPackageRefImpl getRef(CubeFormulaPackagePK paramCubeFormulaPackagePK)
/*     */   {
/* 802 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/* 803 */     PreparedStatement stmt = null;
/* 804 */     ResultSet resultSet = null;
/*     */     try
/*     */     {
/* 807 */       stmt = getConnection().prepareStatement("select 0,MODEL.MODEL_ID,FINANCE_CUBE.FINANCE_CUBE_ID from CUBE_FORMULA_PACKAGE,MODEL,FINANCE_CUBE where 1=1 and CUBE_FORMULA_PACKAGE.CUBE_FORMULA_PACKAGE_ID = ? and CUBE_FORMULA_PACKAGE.FINANCE_CUBE_ID = FINANCE_CUBE.FINANCE_CUBE_ID and FINANCE_CUBE.FINANCE_CUBE_ID = MODEL.FINANCE_CUBE_ID");
/* 808 */       int col = 1;
/* 809 */       stmt.setInt(col++, paramCubeFormulaPackagePK.getCubeFormulaPackageId());
/*     */ 
/* 811 */       resultSet = stmt.executeQuery();
/*     */ 
/* 813 */       if (!resultSet.next()) {
/* 814 */         throw new RuntimeException(getEntityName() + " getRef " + paramCubeFormulaPackagePK + " not found");
/*     */       }
/* 816 */       col = 2;
/* 817 */       ModelPK newModelPK = new ModelPK(resultSet.getInt(col++));
/*     */ 
/* 821 */       FinanceCubePK newFinanceCubePK = new FinanceCubePK(resultSet.getInt(col++));
/*     */ 
/* 825 */       String textCubeFormulaPackage = "";
/* 826 */       CubeFormulaPackageCK ckCubeFormulaPackage = new CubeFormulaPackageCK(newModelPK, newFinanceCubePK, paramCubeFormulaPackagePK);
/*     */ 
/* 832 */       CubeFormulaPackageRefImpl localCubeFormulaPackageRefImpl = new CubeFormulaPackageRefImpl(ckCubeFormulaPackage, textCubeFormulaPackage);
/*     */       return localCubeFormulaPackageRefImpl;
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/* 837 */       throw handleSQLException(paramCubeFormulaPackagePK, "select 0,MODEL.MODEL_ID,FINANCE_CUBE.FINANCE_CUBE_ID from CUBE_FORMULA_PACKAGE,MODEL,FINANCE_CUBE where 1=1 and CUBE_FORMULA_PACKAGE.CUBE_FORMULA_PACKAGE_ID = ? and CUBE_FORMULA_PACKAGE.FINANCE_CUBE_ID = FINANCE_CUBE.FINANCE_CUBE_ID and FINANCE_CUBE.FINANCE_CUBE_ID = MODEL.FINANCE_CUBE_ID", sqle);
/*     */     }
/*     */     finally
/*     */     {
/* 841 */       closeResultSet(resultSet);
/* 842 */       closeStatement(stmt);
/* 843 */       closeConnection();
/*     */ 
/* 845 */       if (timer != null)
/* 846 */         timer.logDebug("getRef", paramCubeFormulaPackagePK); 
/* 846 */     }
/*     */   }
/*     */ 
/*     */   public EntityList queryActivePackages(int financeCubeId)
/*     */   {
/* 867 */     PreparedStatement ps = null;
/* 868 */     ResultSet rs = null;
/*     */     try
/*     */     {
/* 871 */       ps = getConnection().prepareStatement("select cube_formula_package_id, package_group_index from cube_formula_package where finance_cube_id = ?");
/*     */ 
/* 876 */       ps.setInt(1, financeCubeId);
/*     */ 
/* 878 */       rs = ps.executeQuery();
/*     */ 
/* 880 */       EntityListImpl localEntityListImpl = JdbcUtils.extractToEntityListImpl(this.sQUERY_ACTIVE_PACKAGES_COL_INFO, rs);
/*     */       return localEntityListImpl;
/*     */     }
/*     */     catch (SQLException e)
/*     */     {
/* 884 */       throw handleSQLException("queryActivePackages", e);
/*     */     }
/*     */     finally
/*     */     {
/* 888 */       closeResultSet(rs);
/* 889 */       closeStatement(ps);
/* 890 */       closeConnection(); } //throw localObject;
/*     */   }
/*     */ 
/*     */   public int queryLowestLineCountPackageForFinanceCube(int financeCubeId)
/*     */   {
/* 903 */     PreparedStatement ps = null;
/* 904 */     ResultSet rs = null;
/*     */       int e;
				try {
				    ps = this.getConnection().prepareStatement("select cube_formula_package_id from cube_formula_package where finance_cube_id = ?order by line_count asc");
				    ps.setInt(1, financeCubeId);
				    rs = ps.executeQuery();
				    if(!rs.next()) {
				       byte e1 = -1;
				       return e1;
				    }
				
				    e = rs.getInt("cube_formula_package_id");
				    return e;
/*     */     }
/*     */     catch (SQLException e1)
/*     */     {
/* 924 */       throw handleSQLException("queryLowestLineCountPackageForFinanceCube", e1);
/*     */     }
/*     */     finally
/*     */     {
/* 928 */       closeResultSet(rs);
/* 929 */       closeStatement(ps);
/* 930 */       closeConnection(); } //throw localObject;
/*     */   }
/*     */ 
/*     */   public void delete(int financeCubeId)
/*     */   {
/* 940 */     PreparedStatement ps = null;
/*     */     try
/*     */     {
/* 943 */       ps = getConnection().prepareStatement("delete from cube_formula_package where finance_cube_id = ?");
/*     */ 
/* 945 */       ps.setInt(1, financeCubeId);
/* 946 */       ps.executeUpdate();
/*     */     }
/*     */     catch (SQLException e)
/*     */     {
/* 950 */       throw handleSQLException("delete", e);
/*     */     }
/*     */     finally
/*     */     {
/* 954 */       closeStatement(ps);
/* 955 */       closeConnection();
/*     */     }
/*     */   }
/*     */ }

/* Location:           /home/oracle/coa/cp.ear/cp.ear_orginal/cp-server.jar
 * Qualified Name:     com.cedar.cp.ejb.impl.cubeformula.CubeFormulaPackageDAO
 * JD-Core Version:    0.6.0
 */