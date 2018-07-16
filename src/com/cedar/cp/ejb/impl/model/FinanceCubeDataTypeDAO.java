/*      */ package com.cedar.cp.ejb.impl.model;
/*      */ 
/*      */ import com.cedar.cp.api.base.DuplicateNameValidationException;
/*      */ import com.cedar.cp.api.base.ValidationException;
/*      */ import com.cedar.cp.api.base.VersionValidationException;
/*      */ import com.cedar.cp.dto.datatype.DataTypePK;
/*      */ import com.cedar.cp.dto.datatype.DataTypeRefImpl;
/*      */ import com.cedar.cp.dto.model.AllAttachedDataTypesForFinanceCubeELO;
/*      */ import com.cedar.cp.dto.model.AllDataTypesForFinanceCubeELO;
/*      */ import com.cedar.cp.dto.model.AllFinanceCubeDataTypesELO;
/*      */ import com.cedar.cp.dto.model.AllFinanceCubesForDataTypeELO;
/*      */ import com.cedar.cp.dto.model.FinanceCubeCK;
/*      */ import com.cedar.cp.dto.model.FinanceCubeDataTypeCK;
/*      */ import com.cedar.cp.dto.model.FinanceCubeDataTypePK;
/*      */ import com.cedar.cp.dto.model.FinanceCubeDataTypeRefImpl;
/*      */ import com.cedar.cp.dto.model.FinanceCubePK;
/*      */ import com.cedar.cp.dto.model.FinanceCubeRefImpl;
/*      */ import com.cedar.cp.dto.model.ImportableFinanceCubeDataTypesELO;
/*      */ import com.cedar.cp.dto.model.ModelCK;
/*      */ import com.cedar.cp.dto.model.ModelPK;
/*      */ import com.cedar.cp.dto.model.ModelRefImpl;
/*      */ import com.cedar.cp.ejb.impl.base.AbstractDAO;
/*      */ import com.cedar.cp.util.Log;
/*      */ import com.cedar.cp.util.Timer;
/*      */ import java.sql.Connection;
/*      */ import java.sql.PreparedStatement;
/*      */ import java.sql.ResultSet;
/*      */ import java.sql.SQLException;
/*      */ import java.sql.Timestamp;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Collection;
/*      */ import java.util.Date;
/*      */ import java.util.Iterator;
/*      */ import java.util.ListIterator;
/*      */ import java.util.Map;
/*      */ import javax.sql.DataSource;
/*      */ 
/*      */ public class FinanceCubeDataTypeDAO extends AbstractDAO
/*      */ {
/*   44 */   Log _log = new Log(getClass());
/*      */   private static final String SQL_SELECT_COLUMNS = "select FINANCE_CUBE_DATA_TYPE.FINANCE_CUBE_ID,FINANCE_CUBE_DATA_TYPE.DATA_TYPE_ID,FINANCE_CUBE_DATA_TYPE.CUBE_LAST_UPDATED_TIME,FINANCE_CUBE_DATA_TYPE.UPDATED_BY_USER_ID,FINANCE_CUBE_DATA_TYPE.UPDATED_TIME,FINANCE_CUBE_DATA_TYPE.CREATED_TIME";
/*      */   protected static final String SQL_LOAD = " from FINANCE_CUBE_DATA_TYPE where    FINANCE_CUBE_ID = ? AND DATA_TYPE_ID = ? ";
/*      */   protected static final String SQL_CREATE = "insert into FINANCE_CUBE_DATA_TYPE ( FINANCE_CUBE_ID,DATA_TYPE_ID,CUBE_LAST_UPDATED_TIME,UPDATED_BY_USER_ID,UPDATED_TIME,CREATED_TIME) values ( ?,?,?,?,?,?)";
/*      */   protected static final String SQL_STORE = "update FINANCE_CUBE_DATA_TYPE set CUBE_LAST_UPDATED_TIME = ?,UPDATED_BY_USER_ID = ?,UPDATED_TIME = ?,CREATED_TIME = ? where    FINANCE_CUBE_ID = ? AND DATA_TYPE_ID = ? ";
/*  323 */   protected static String SQL_ALL_FINANCE_CUBE_DATA_TYPES = "select 0       ,MODEL.MODEL_ID      ,MODEL.VIS_ID      ,FINANCE_CUBE.FINANCE_CUBE_ID      ,FINANCE_CUBE.VIS_ID      ,FINANCE_CUBE_DATA_TYPE.FINANCE_CUBE_ID      ,FINANCE_CUBE_DATA_TYPE.DATA_TYPE_ID      ,FINANCE_CUBE_DATA_TYPE.FINANCE_CUBE_ID      ,FINANCE_CUBE_DATA_TYPE.DATA_TYPE_ID from FINANCE_CUBE_DATA_TYPE    ,MODEL    ,FINANCE_CUBE where 1=1   and FINANCE_CUBE_DATA_TYPE.FINANCE_CUBE_ID = FINANCE_CUBE.FINANCE_CUBE_ID   and FINANCE_CUBE.MODEL_ID = MODEL.MODEL_ID  order by FINANCE_CUBE_DATA_TYPE.DATA_TYPE_ID";
/*      */ 
/*  458 */   protected static String SQL_IMPORTABLE_FINANCE_CUBE_DATA_TYPES = "select 0       ,MODEL.MODEL_ID      ,MODEL.VIS_ID      ,FINANCE_CUBE.FINANCE_CUBE_ID      ,FINANCE_CUBE.VIS_ID      ,FINANCE_CUBE_DATA_TYPE.FINANCE_CUBE_ID      ,FINANCE_CUBE_DATA_TYPE.DATA_TYPE_ID      ,DATA_TYPE.DATA_TYPE_ID      ,DATA_TYPE.VIS_ID      ,DATA_TYPE.DESCRIPTION      ,DATA_TYPE.SUB_TYPE      ,DATA_TYPE.MEASURE_CLASS      ,DATA_TYPE.MEASURE_LENGTH      ,DATA_TYPE.AVAILABLE_FOR_IMPORT      ,DATA_TYPE.AVAILABLE_FOR_EXPORT from FINANCE_CUBE_DATA_TYPE    ,MODEL    ,FINANCE_CUBE    ,DATA_TYPE where 1=1   and FINANCE_CUBE_DATA_TYPE.FINANCE_CUBE_ID = FINANCE_CUBE.FINANCE_CUBE_ID   and FINANCE_CUBE.MODEL_ID = MODEL.MODEL_ID  and  FINANCE_CUBE.FINANCE_CUBE_ID = FINANCE_CUBE_DATA_TYPE.FINANCE_CUBE_ID and DATA_TYPE.DATA_TYPE_ID = FINANCE_CUBE_DATA_TYPE.DATA_TYPE_ID and ( DATA_TYPE.AVAILABLE_FOR_IMPORT = 'Y' or DATA_TYPE.AVAILABLE_FOR_EXPORT = 'Y') order by FINANCE_CUBE.VIS_ID, DATA_TYPE.VIS_ID";
/*      */ 
/*  625 */   protected static String SQL_ALL_ATTACHED_DATA_TYPES_FOR_FINANCE_CUBE = "select distinct 0       ,DATA_TYPE.DATA_TYPE_ID      ,DATA_TYPE.VIS_ID      ,DATA_TYPE.DESCRIPTION      ,DATA_TYPE.READ_ONLY_FLAG      ,DATA_TYPE.SUB_TYPE      ,DATA_TYPE.FORMULA_EXPR from FINANCE_CUBE_DATA_TYPE    ,MODEL    ,FINANCE_CUBE    ,DATA_TYPE where 1=1   and FINANCE_CUBE_DATA_TYPE.FINANCE_CUBE_ID = FINANCE_CUBE.FINANCE_CUBE_ID   and FINANCE_CUBE.MODEL_ID = MODEL.MODEL_ID  and  FINANCE_CUBE_DATA_TYPE.FINANCE_CUBE_ID = ? and FINANCE_CUBE_DATA_TYPE.DATA_TYPE_ID = DATA_TYPE.DATA_TYPE_ID order by DATA_TYPE.VIS_ID";
/*      */ 
/*  708 */   protected static String SQL_ALL_DATA_TYPES_FOR_FINANCE_CUBE = "select 0       ,MODEL.MODEL_ID      ,MODEL.VIS_ID      ,FINANCE_CUBE.FINANCE_CUBE_ID      ,FINANCE_CUBE.VIS_ID      ,FINANCE_CUBE_DATA_TYPE.FINANCE_CUBE_ID      ,FINANCE_CUBE_DATA_TYPE.DATA_TYPE_ID      ,DATA_TYPE.DATA_TYPE_ID      ,DATA_TYPE.VIS_ID      ,DATA_TYPE.DESCRIPTION      ,DATA_TYPE.SUB_TYPE      ,DATA_TYPE.MEASURE_CLASS      ,DATA_TYPE.MEASURE_LENGTH      ,DATA_TYPE.DESCRIPTION      ,DATA_TYPE.READ_ONLY_FLAG      ,DATA_TYPE.SUB_TYPE from FINANCE_CUBE_DATA_TYPE    ,MODEL    ,FINANCE_CUBE    ,DATA_TYPE where 1=1   and FINANCE_CUBE_DATA_TYPE.FINANCE_CUBE_ID = FINANCE_CUBE.FINANCE_CUBE_ID   and FINANCE_CUBE.MODEL_ID = MODEL.MODEL_ID  and  FINANCE_CUBE_DATA_TYPE.FINANCE_CUBE_ID = ? and FINANCE_CUBE_DATA_TYPE.DATA_TYPE_ID = DATA_TYPE.DATA_TYPE_ID order by DATA_TYPE.SUB_TYPE, DATA_TYPE.VIS_ID";
/*      */ 
/*  880 */   protected static String SQL_ALL_FINANCE_CUBES_FOR_DATA_TYPE = "select 0       ,MODEL.MODEL_ID      ,MODEL.VIS_ID      ,FINANCE_CUBE.FINANCE_CUBE_ID      ,FINANCE_CUBE.VIS_ID      ,FINANCE_CUBE_DATA_TYPE.FINANCE_CUBE_ID      ,FINANCE_CUBE_DATA_TYPE.DATA_TYPE_ID      ,FINANCE_CUBE_DATA_TYPE.FINANCE_CUBE_ID from FINANCE_CUBE_DATA_TYPE    ,MODEL    ,FINANCE_CUBE where 1=1   and FINANCE_CUBE_DATA_TYPE.FINANCE_CUBE_ID = FINANCE_CUBE.FINANCE_CUBE_ID   and FINANCE_CUBE.MODEL_ID = MODEL.MODEL_ID  and  FINANCE_CUBE_DATA_TYPE.DATA_TYPE_ID = ?";
/*      */   protected static final String SQL_DELETE_BATCH = "delete from FINANCE_CUBE_DATA_TYPE where    FINANCE_CUBE_ID = ? AND DATA_TYPE_ID = ? ";
/*      */   public static final String SQL_BULK_GET_ALL = " from FINANCE_CUBE_DATA_TYPE,FINANCE_CUBE where 1=1 and FINANCE_CUBE_DATA_TYPE.FINANCE_CUBE_ID = FINANCE_CUBE.FINANCE_CUBE_ID and FINANCE_CUBE.MODEL_ID = ? order by  FINANCE_CUBE_DATA_TYPE.FINANCE_CUBE_ID ,FINANCE_CUBE_DATA_TYPE.FINANCE_CUBE_ID ,FINANCE_CUBE_DATA_TYPE.DATA_TYPE_ID";
/*      */   protected static final String SQL_GET_ALL = " from FINANCE_CUBE_DATA_TYPE where    FINANCE_CUBE_ID = ? ";
/*      */   protected FinanceCubeDataTypeEVO mDetails;
/*      */ 
/*      */   public FinanceCubeDataTypeDAO(Connection connection)
/*      */   {
/*   51 */     super(connection);
/*      */   }
/*      */ 
/*      */   public FinanceCubeDataTypeDAO()
/*      */   {
/*      */   }
/*      */ 
/*      */   public FinanceCubeDataTypeDAO(DataSource ds)
/*      */   {
/*   67 */     super(ds);
/*      */   }
/*      */ 
/*      */   protected FinanceCubeDataTypePK getPK()
/*      */   {
/*   75 */     return this.mDetails.getPK();
/*      */   }
/*      */ 
/*      */   public void setDetails(FinanceCubeDataTypeEVO details)
/*      */   {
/*   84 */     this.mDetails = details.deepClone();
/*      */   }
/*      */ 
/*      */   private FinanceCubeDataTypeEVO getEvoFromJdbc(ResultSet resultSet_)
/*      */     throws SQLException
/*      */   {
/*  101 */     int col = 1;
/*  102 */     FinanceCubeDataTypeEVO evo = new FinanceCubeDataTypeEVO(resultSet_.getInt(col++), resultSet_.getShort(col++), resultSet_.getTimestamp(col++));
/*      */ 
/*  108 */     evo.setUpdatedByUserId(resultSet_.getInt(col++));
/*  109 */     evo.setUpdatedTime(resultSet_.getTimestamp(col++));
/*  110 */     evo.setCreatedTime(resultSet_.getTimestamp(col++));
/*  111 */     return evo;
/*      */   }
/*      */ 
/*      */   private int putEvoKeysToJdbc(FinanceCubeDataTypeEVO evo_, PreparedStatement stmt_, int startCol_) throws SQLException
/*      */   {
/*  116 */     int col = startCol_;
/*  117 */     stmt_.setInt(col++, evo_.getFinanceCubeId());
/*  118 */     stmt_.setShort(col++, evo_.getDataTypeId());
/*  119 */     return col;
/*      */   }
/*      */ 
/*      */   private int putEvoDataToJdbc(FinanceCubeDataTypeEVO evo_, PreparedStatement stmt_, int startCol_) throws SQLException
/*      */   {
/*  124 */     int col = startCol_;
/*  125 */     stmt_.setTimestamp(col++, evo_.getCubeLastUpdatedTime());
/*  126 */     stmt_.setInt(col++, evo_.getUpdatedByUserId());
/*  127 */     stmt_.setTimestamp(col++, evo_.getUpdatedTime());
/*  128 */     stmt_.setTimestamp(col++, evo_.getCreatedTime());
/*  129 */     return col;
/*      */   }
/*      */ 
/*      */   protected void doLoad(FinanceCubeDataTypePK pk)
/*      */     throws ValidationException
/*      */   {
/*  146 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*      */ 
/*  148 */     PreparedStatement stmt = null;
/*  149 */     ResultSet resultSet = null;
/*      */     try
/*      */     {
/*  153 */       stmt = getConnection().prepareStatement("select FINANCE_CUBE_DATA_TYPE.FINANCE_CUBE_ID,FINANCE_CUBE_DATA_TYPE.DATA_TYPE_ID,FINANCE_CUBE_DATA_TYPE.CUBE_LAST_UPDATED_TIME,FINANCE_CUBE_DATA_TYPE.UPDATED_BY_USER_ID,FINANCE_CUBE_DATA_TYPE.UPDATED_TIME,FINANCE_CUBE_DATA_TYPE.CREATED_TIME from FINANCE_CUBE_DATA_TYPE where    FINANCE_CUBE_ID = ? AND DATA_TYPE_ID = ? ");
/*      */ 
/*  156 */       int col = 1;
/*  157 */       stmt.setInt(col++, pk.getFinanceCubeId());
/*  158 */       stmt.setShort(col++, pk.getDataTypeId());
/*      */ 
/*  160 */       resultSet = stmt.executeQuery();
/*      */ 
/*  162 */       if (!resultSet.next()) {
/*  163 */         throw new ValidationException(getEntityName() + " select of " + pk + " not found");
/*      */       }
/*      */ 
/*  166 */       this.mDetails = getEvoFromJdbc(resultSet);
/*  167 */       if (this.mDetails.isModified())
/*  168 */         this._log.info("doLoad", this.mDetails);
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  172 */       throw handleSQLException(pk, "select FINANCE_CUBE_DATA_TYPE.FINANCE_CUBE_ID,FINANCE_CUBE_DATA_TYPE.DATA_TYPE_ID,FINANCE_CUBE_DATA_TYPE.CUBE_LAST_UPDATED_TIME,FINANCE_CUBE_DATA_TYPE.UPDATED_BY_USER_ID,FINANCE_CUBE_DATA_TYPE.UPDATED_TIME,FINANCE_CUBE_DATA_TYPE.CREATED_TIME from FINANCE_CUBE_DATA_TYPE where    FINANCE_CUBE_ID = ? AND DATA_TYPE_ID = ? ", sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  176 */       closeResultSet(resultSet);
/*  177 */       closeStatement(stmt);
/*  178 */       closeConnection();
/*      */ 
/*  180 */       if (timer != null)
/*  181 */         timer.logDebug("doLoad", pk);
/*      */     }
/*      */   }
/*      */ 
/*      */   protected void doCreate()
/*      */     throws DuplicateNameValidationException, ValidationException
/*      */   {
/*  210 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*  211 */     PreparedStatement stmt = null;
/*      */     try
/*      */     {
/*  216 */       this.mDetails.setCreatedTime(new Timestamp(new Date().getTime()));
/*  217 */       this.mDetails.setUpdatedTime(new Timestamp(new Date().getTime()));
/*  218 */       stmt = getConnection().prepareStatement("insert into FINANCE_CUBE_DATA_TYPE ( FINANCE_CUBE_ID,DATA_TYPE_ID,CUBE_LAST_UPDATED_TIME,UPDATED_BY_USER_ID,UPDATED_TIME,CREATED_TIME) values ( ?,?,?,?,?,?)");
/*      */ 
/*  221 */       int col = 1;
/*  222 */       col = putEvoKeysToJdbc(this.mDetails, stmt, col);
/*  223 */       col = putEvoDataToJdbc(this.mDetails, stmt, col);
/*      */ 
/*  226 */       int resultCount = stmt.executeUpdate();
/*  227 */       if (resultCount != 1)
/*      */       {
/*  229 */         throw new RuntimeException(getEntityName() + " insert failed (" + this.mDetails.getPK() + "): resultCount=" + resultCount);
/*      */       }
/*      */ 
/*  232 */       this.mDetails.reset();
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  236 */       throw handleSQLException(this.mDetails.getPK(), "insert into FINANCE_CUBE_DATA_TYPE ( FINANCE_CUBE_ID,DATA_TYPE_ID,CUBE_LAST_UPDATED_TIME,UPDATED_BY_USER_ID,UPDATED_TIME,CREATED_TIME) values ( ?,?,?,?,?,?)", sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  240 */       closeStatement(stmt);
/*  241 */       closeConnection();
/*      */ 
/*  243 */       if (timer != null)
/*  244 */         timer.logDebug("doCreate", this.mDetails.toString());
/*      */     }
/*      */   }
/*      */ 
/*      */   protected void doStore()
/*      */     throws DuplicateNameValidationException, VersionValidationException, ValidationException
/*      */   {
/*  268 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*      */ 
/*  272 */     PreparedStatement stmt = null;
/*      */ 
/*  274 */     boolean mainChanged = this.mDetails.isModified();
/*  275 */     boolean dependantChanged = false;
/*      */     try
/*      */     {
/*  278 */       if ((mainChanged) || (dependantChanged))
/*      */       {
/*  281 */         this.mDetails.setUpdatedTime(new Timestamp(new Date().getTime()));
/*  282 */         stmt = getConnection().prepareStatement("update FINANCE_CUBE_DATA_TYPE set CUBE_LAST_UPDATED_TIME = ?,UPDATED_BY_USER_ID = ?,UPDATED_TIME = ?,CREATED_TIME = ? where    FINANCE_CUBE_ID = ? AND DATA_TYPE_ID = ? ");
/*      */ 
/*  285 */         int col = 1;
/*  286 */         col = putEvoDataToJdbc(this.mDetails, stmt, col);
/*  287 */         col = putEvoKeysToJdbc(this.mDetails, stmt, col);
/*      */ 
/*  290 */         int resultCount = stmt.executeUpdate();
/*      */ 
/*  293 */         if (resultCount != 1) {
/*  294 */           throw new RuntimeException(getEntityName() + " update failed (" + getPK() + "): resultCount=" + resultCount);
/*      */         }
/*      */ 
/*  297 */         this.mDetails.reset();
/*      */       }
/*      */ 
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  306 */       throw handleSQLException(getPK(), "update FINANCE_CUBE_DATA_TYPE set CUBE_LAST_UPDATED_TIME = ?,UPDATED_BY_USER_ID = ?,UPDATED_TIME = ?,CREATED_TIME = ? where    FINANCE_CUBE_ID = ? AND DATA_TYPE_ID = ? ", sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  310 */       closeStatement(stmt);
/*  311 */       closeConnection();
/*      */ 
/*  313 */       if ((timer != null) && (
/*  314 */         (mainChanged) || (dependantChanged)))
/*  315 */         timer.logDebug("store", this.mDetails.getPK() + "(" + mainChanged + "," + dependantChanged + ")");
/*      */     }
/*      */   }
/*      */ 
/*      */   public AllFinanceCubeDataTypesELO getAllFinanceCubeDataTypes()
/*      */   {
/*  359 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*  360 */     PreparedStatement stmt = null;
/*  361 */     ResultSet resultSet = null;
/*  362 */     AllFinanceCubeDataTypesELO results = new AllFinanceCubeDataTypesELO();
/*      */     try
/*      */     {
/*  365 */       stmt = getConnection().prepareStatement(SQL_ALL_FINANCE_CUBE_DATA_TYPES);
/*  366 */       int col = 1;
/*  367 */       resultSet = stmt.executeQuery();
/*  368 */       while (resultSet.next())
/*      */       {
/*  370 */         col = 2;
/*      */ 
/*  373 */         ModelPK pkModel = new ModelPK(resultSet.getInt(col++));
/*      */ 
/*  376 */         String textModel = resultSet.getString(col++);
/*      */ 
/*  378 */         FinanceCubePK pkFinanceCube = new FinanceCubePK(resultSet.getInt(col++));
/*      */ 
/*  381 */         String textFinanceCube = resultSet.getString(col++);
/*      */ 
/*  384 */         FinanceCubeDataTypePK pkFinanceCubeDataType = new FinanceCubeDataTypePK(resultSet.getInt(col++), resultSet.getShort(col++));
/*      */ 
/*  388 */         String textFinanceCubeDataType = "";
/*      */ 
/*  393 */         FinanceCubeCK ckFinanceCube = new FinanceCubeCK(pkModel, pkFinanceCube);
/*      */ 
/*  399 */         FinanceCubeDataTypeCK ckFinanceCubeDataType = new FinanceCubeDataTypeCK(pkModel, pkFinanceCube, pkFinanceCubeDataType);
/*      */ 
/*  406 */         ModelRefImpl erModel = new ModelRefImpl(pkModel, textModel);
/*      */ 
/*  412 */         FinanceCubeRefImpl erFinanceCube = new FinanceCubeRefImpl(ckFinanceCube, textFinanceCube);
/*      */ 
/*  418 */         FinanceCubeDataTypeRefImpl erFinanceCubeDataType = new FinanceCubeDataTypeRefImpl(ckFinanceCubeDataType, textFinanceCubeDataType);
/*      */ 
/*  423 */         int col1 = resultSet.getInt(col++);
/*  424 */         short col2 = resultSet.getShort(col++);
/*      */ 
/*  427 */         results.add(erFinanceCubeDataType, erFinanceCube, erModel, col1, col2);
/*      */       }
/*      */ 
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  438 */       throw handleSQLException(SQL_ALL_FINANCE_CUBE_DATA_TYPES, sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  442 */       closeResultSet(resultSet);
/*  443 */       closeStatement(stmt);
/*  444 */       closeConnection();
/*      */     }
/*      */ 
/*  447 */     if (timer != null) {
/*  448 */       timer.logDebug("getAllFinanceCubeDataTypes", " items=" + results.size());
/*      */     }
/*      */ 
/*  452 */     return results;
/*      */   }
/*      */ 
/*      */   public ImportableFinanceCubeDataTypesELO getImportableFinanceCubeDataTypes()
/*      */   {
/*  502 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*  503 */     PreparedStatement stmt = null;
/*  504 */     ResultSet resultSet = null;
/*  505 */     ImportableFinanceCubeDataTypesELO results = new ImportableFinanceCubeDataTypesELO();
/*      */     try
/*      */     {
/*  508 */       stmt = getConnection().prepareStatement(SQL_IMPORTABLE_FINANCE_CUBE_DATA_TYPES);
/*  509 */       int col = 1;
/*  510 */       resultSet = stmt.executeQuery();
/*  511 */       while (resultSet.next())
/*      */       {
/*  513 */         col = 2;
/*      */ 
/*  516 */         ModelPK pkModel = new ModelPK(resultSet.getInt(col++));
/*      */ 
/*  519 */         String textModel = resultSet.getString(col++);
/*      */ 
/*  521 */         FinanceCubePK pkFinanceCube = new FinanceCubePK(resultSet.getInt(col++));
/*      */ 
/*  524 */         String textFinanceCube = resultSet.getString(col++);
/*      */ 
/*  527 */         FinanceCubeDataTypePK pkFinanceCubeDataType = new FinanceCubeDataTypePK(resultSet.getInt(col++), resultSet.getShort(col++));
/*      */ 
/*  531 */         String textFinanceCubeDataType = "";
/*      */ 
/*  534 */         DataTypePK pkDataType = new DataTypePK(resultSet.getShort(col++));
/*      */ 
/*  537 */         String textDataType = resultSet.getString(col++);
/*  538 */         String erDataTypeDescription = resultSet.getString(col++);
/*  539 */         int erDataTypeSubType = resultSet.getInt(col++);
/*  540 */         Integer erDataTypeMeasureClass = Integer.valueOf(resultSet.getInt(col++));
/*  541 */         Integer erDataTypeMeasureLength = Integer.valueOf(resultSet.getInt(col++));
/*      */ 
/*  545 */         FinanceCubeCK ckFinanceCube = new FinanceCubeCK(pkModel, pkFinanceCube);
/*      */ 
/*  551 */         FinanceCubeDataTypeCK ckFinanceCubeDataType = new FinanceCubeDataTypeCK(pkModel, pkFinanceCube, pkFinanceCubeDataType);
/*      */ 
/*  558 */         ModelRefImpl erModel = new ModelRefImpl(pkModel, textModel);
/*      */ 
/*  564 */         FinanceCubeRefImpl erFinanceCube = new FinanceCubeRefImpl(ckFinanceCube, textFinanceCube);
/*      */ 
/*  570 */         FinanceCubeDataTypeRefImpl erFinanceCubeDataType = new FinanceCubeDataTypeRefImpl(ckFinanceCubeDataType, textFinanceCubeDataType);
/*      */ 
/*  576 */         DataTypeRefImpl erDataType = new DataTypeRefImpl(pkDataType, textDataType, erDataTypeDescription, erDataTypeSubType, erDataTypeMeasureClass, erDataTypeMeasureLength);
/*      */ 
/*  585 */         String col1 = resultSet.getString(col++);
/*  586 */         if (resultSet.wasNull())
/*  587 */           col1 = "";
/*  588 */         String col2 = resultSet.getString(col++);
/*  589 */         if (resultSet.wasNull()) {
/*  590 */           col2 = "";
/*      */         }
/*      */ 
/*  593 */         results.add(erFinanceCubeDataType, erFinanceCube, erModel, erDataType, col1.equals("Y"), col2.equals("Y"));
/*      */       }
/*      */ 
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  605 */       throw handleSQLException(SQL_IMPORTABLE_FINANCE_CUBE_DATA_TYPES, sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  609 */       closeResultSet(resultSet);
/*  610 */       closeStatement(stmt);
/*  611 */       closeConnection();
/*      */     }
/*      */ 
/*  614 */     if (timer != null) {
/*  615 */       timer.logDebug("getImportableFinanceCubeDataTypes", " items=" + results.size());
/*      */     }
/*      */ 
/*  619 */     return results;
/*      */   }
/*      */ 
/*      */   public AllAttachedDataTypesForFinanceCubeELO getAllAttachedDataTypesForFinanceCube(int param1)
/*      */   {
/*  660 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*  661 */     PreparedStatement stmt = null;
/*  662 */     ResultSet resultSet = null;
/*  663 */     AllAttachedDataTypesForFinanceCubeELO results = new AllAttachedDataTypesForFinanceCubeELO();
/*      */     try
/*      */     {
/*  666 */       stmt = getConnection().prepareStatement(SQL_ALL_ATTACHED_DATA_TYPES_FOR_FINANCE_CUBE);
/*  667 */       int col = 1;
/*  668 */       stmt.setInt(col++, param1);
/*  669 */       resultSet = stmt.executeQuery();
/*  670 */       while (resultSet.next())
/*      */       {
/*  672 */         col = 2;
/*      */ 
/*  675 */         results.add(resultSet.getShort(col++), resultSet.getString(col++), resultSet.getString(col++), resultSet.getString(col++).equals("Y"), resultSet.getInt(col++), resultSet.getString(col++));
/*      */       }
/*      */ 
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  687 */       throw handleSQLException(SQL_ALL_ATTACHED_DATA_TYPES_FOR_FINANCE_CUBE, sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  691 */       closeResultSet(resultSet);
/*  692 */       closeStatement(stmt);
/*  693 */       closeConnection();
/*      */     }
/*      */ 
/*  696 */     if (timer != null) {
/*  697 */       timer.logDebug("getAllAttachedDataTypesForFinanceCube", " FinanceCubeId=" + param1 + " items=" + results.size());
/*      */     }
/*      */ 
/*  702 */     return results;
/*      */   }
/*      */ 
/*      */   public AllDataTypesForFinanceCubeELO getAllDataTypesForFinanceCube(int param1)
/*      */   {
/*  755 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*  756 */     PreparedStatement stmt = null;
/*  757 */     ResultSet resultSet = null;
/*  758 */     AllDataTypesForFinanceCubeELO results = new AllDataTypesForFinanceCubeELO();
/*      */     try
/*      */     {
/*  761 */       stmt = getConnection().prepareStatement(SQL_ALL_DATA_TYPES_FOR_FINANCE_CUBE);
/*  762 */       int col = 1;
/*  763 */       stmt.setInt(col++, param1);
/*  764 */       resultSet = stmt.executeQuery();
/*  765 */       while (resultSet.next())
/*      */       {
/*  767 */         col = 2;
/*      */ 
/*  770 */         ModelPK pkModel = new ModelPK(resultSet.getInt(col++));
/*      */ 
/*  773 */         String textModel = resultSet.getString(col++);
/*      */ 
/*  775 */         FinanceCubePK pkFinanceCube = new FinanceCubePK(resultSet.getInt(col++));
/*      */ 
/*  778 */         String textFinanceCube = resultSet.getString(col++);
/*      */ 
/*  781 */         FinanceCubeDataTypePK pkFinanceCubeDataType = new FinanceCubeDataTypePK(resultSet.getInt(col++), resultSet.getShort(col++));
/*      */ 
/*  785 */         String textFinanceCubeDataType = "";
/*      */ 
/*  788 */         DataTypePK pkDataType = new DataTypePK(resultSet.getShort(col++));
/*      */ 
/*  791 */         String textDataType = resultSet.getString(col++);
/*  792 */         String erDataTypeDescription = resultSet.getString(col++);
/*  793 */         int erDataTypeSubType = resultSet.getInt(col++);
/*  794 */         Integer erDataTypeMeasureClass = Integer.valueOf(resultSet.getInt(col++));
/*  795 */         Integer erDataTypeMeasureLength = Integer.valueOf(resultSet.getInt(col++));
/*      */ 
/*  799 */         FinanceCubeCK ckFinanceCube = new FinanceCubeCK(pkModel, pkFinanceCube);
/*      */ 
/*  805 */         FinanceCubeDataTypeCK ckFinanceCubeDataType = new FinanceCubeDataTypeCK(pkModel, pkFinanceCube, pkFinanceCubeDataType);
/*      */ 
/*  812 */         ModelRefImpl erModel = new ModelRefImpl(pkModel, textModel);
/*      */ 
/*  818 */         FinanceCubeRefImpl erFinanceCube = new FinanceCubeRefImpl(ckFinanceCube, textFinanceCube);
/*      */ 
/*  824 */         FinanceCubeDataTypeRefImpl erFinanceCubeDataType = new FinanceCubeDataTypeRefImpl(ckFinanceCubeDataType, textFinanceCubeDataType);
/*      */ 
/*  830 */         DataTypeRefImpl erDataType = new DataTypeRefImpl(pkDataType, textDataType, erDataTypeDescription, erDataTypeSubType, erDataTypeMeasureClass, erDataTypeMeasureLength);
/*      */ 
/*  839 */         String col1 = resultSet.getString(col++);
/*  840 */         String col2 = resultSet.getString(col++);
/*  841 */         if (resultSet.wasNull())
/*  842 */           col2 = "";
/*  843 */         int col3 = resultSet.getInt(col++);
/*      */ 
/*  846 */         results.add(erFinanceCubeDataType, erFinanceCube, erModel, erDataType, col1, col2.equals("Y"), col3);
/*      */       }
/*      */ 
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  859 */       throw handleSQLException(SQL_ALL_DATA_TYPES_FOR_FINANCE_CUBE, sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  863 */       closeResultSet(resultSet);
/*  864 */       closeStatement(stmt);
/*  865 */       closeConnection();
/*      */     }
/*      */ 
/*  868 */     if (timer != null) {
/*  869 */       timer.logDebug("getAllDataTypesForFinanceCube", " FinanceCubeId=" + param1 + " items=" + results.size());
/*      */     }
/*      */ 
/*  874 */     return results;
/*      */   }
/*      */ 
/*      */   public AllFinanceCubesForDataTypeELO getAllFinanceCubesForDataType(short param1)
/*      */   {
/*  917 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*  918 */     PreparedStatement stmt = null;
/*  919 */     ResultSet resultSet = null;
/*  920 */     AllFinanceCubesForDataTypeELO results = new AllFinanceCubesForDataTypeELO();
/*      */     try
/*      */     {
/*  923 */       stmt = getConnection().prepareStatement(SQL_ALL_FINANCE_CUBES_FOR_DATA_TYPE);
/*  924 */       int col = 1;
/*  925 */       stmt.setShort(col++, param1);
/*  926 */       resultSet = stmt.executeQuery();
/*  927 */       while (resultSet.next())
/*      */       {
/*  929 */         col = 2;
/*      */ 
/*  932 */         ModelPK pkModel = new ModelPK(resultSet.getInt(col++));
/*      */ 
/*  935 */         String textModel = resultSet.getString(col++);
/*      */ 
/*  937 */         FinanceCubePK pkFinanceCube = new FinanceCubePK(resultSet.getInt(col++));
/*      */ 
/*  940 */         String textFinanceCube = resultSet.getString(col++);
/*      */ 
/*  943 */         FinanceCubeDataTypePK pkFinanceCubeDataType = new FinanceCubeDataTypePK(resultSet.getInt(col++), resultSet.getShort(col++));
/*      */ 
/*  947 */         String textFinanceCubeDataType = "";
/*      */ 
/*  952 */         FinanceCubeCK ckFinanceCube = new FinanceCubeCK(pkModel, pkFinanceCube);
/*      */ 
/*  958 */         FinanceCubeDataTypeCK ckFinanceCubeDataType = new FinanceCubeDataTypeCK(pkModel, pkFinanceCube, pkFinanceCubeDataType);
/*      */ 
/*  965 */         ModelRefImpl erModel = new ModelRefImpl(pkModel, textModel);
/*      */ 
/*  971 */         FinanceCubeRefImpl erFinanceCube = new FinanceCubeRefImpl(ckFinanceCube, textFinanceCube);
/*      */ 
/*  977 */         FinanceCubeDataTypeRefImpl erFinanceCubeDataType = new FinanceCubeDataTypeRefImpl(ckFinanceCubeDataType, textFinanceCubeDataType);
/*      */ 
/*  982 */         int col1 = resultSet.getInt(col++);
/*      */ 
/*  985 */         results.add(erFinanceCubeDataType, erFinanceCube, erModel, col1);
/*      */       }
/*      */ 
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  995 */       throw handleSQLException(SQL_ALL_FINANCE_CUBES_FOR_DATA_TYPE, sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  999 */       closeResultSet(resultSet);
/* 1000 */       closeStatement(stmt);
/* 1001 */       closeConnection();
/*      */     }
/*      */ 
/* 1004 */     if (timer != null) {
/* 1005 */       timer.logDebug("getAllFinanceCubesForDataType", " DataTypeId=" + param1 + " items=" + results.size());
/*      */     }
/*      */ 
/* 1010 */     return results;
/*      */   }
/*      */ 
/*      */   public boolean update(Map items)
/*      */     throws DuplicateNameValidationException, VersionValidationException, ValidationException
/*      */   {
/* 1028 */     if (items == null) {
/* 1029 */       return false;
/*      */     }
/* 1031 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/* 1032 */     PreparedStatement deleteStmt = null;
/*      */ 
/* 1034 */     boolean somethingChanged = false;
/*      */     try
/*      */     {
/* 1039 */       Iterator iter2 = new ArrayList(items.values()).iterator();
/* 1040 */       while (iter2.hasNext())
/*      */       {
/* 1042 */         this.mDetails = ((FinanceCubeDataTypeEVO)iter2.next());
/*      */ 
/* 1045 */         if (!this.mDetails.deletePending())
/*      */           continue;
/* 1047 */         somethingChanged = true;
/*      */ 
/* 1050 */         if (deleteStmt == null) {
/* 1051 */           deleteStmt = getConnection().prepareStatement("delete from FINANCE_CUBE_DATA_TYPE where    FINANCE_CUBE_ID = ? AND DATA_TYPE_ID = ? ");
/*      */         }
/*      */ 
/* 1054 */         int col = 1;
/* 1055 */         deleteStmt.setInt(col++, this.mDetails.getFinanceCubeId());
/* 1056 */         deleteStmt.setShort(col++, this.mDetails.getDataTypeId());
/*      */ 
/* 1058 */         if (this._log.isDebugEnabled()) {
/* 1059 */           this._log.debug("update", "FinanceCubeDataType deleting FinanceCubeId=" + this.mDetails.getFinanceCubeId() + ",DataTypeId=" + this.mDetails.getDataTypeId());
/*      */         }
/*      */ 
/* 1065 */         deleteStmt.addBatch();
/*      */ 
/* 1068 */         items.remove(this.mDetails.getPK());
/*      */       }
/*      */ 
/* 1073 */       if (deleteStmt != null)
/*      */       {
/* 1075 */         Timer timer2 = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*      */ 
/* 1077 */         deleteStmt.executeBatch();
/*      */ 
/* 1079 */         if (timer2 != null) {
/* 1080 */           timer2.logDebug("update", "delete batch");
/*      */         }
/*      */       }
/*      */ 
/* 1084 */       Iterator iter1 = items.values().iterator();
/* 1085 */       while (iter1.hasNext())
/*      */       {
/* 1087 */         this.mDetails = ((FinanceCubeDataTypeEVO)iter1.next());
/*      */ 
/* 1089 */         if (this.mDetails.insertPending())
/*      */         {
/* 1091 */           somethingChanged = true;
/* 1092 */           doCreate(); continue;
/*      */         }
/*      */ 
/* 1095 */         if (!this.mDetails.isModified())
/*      */           continue;
/* 1097 */         somethingChanged = true;
/* 1098 */         doStore();
/*      */       }
/*      */ 
/* 1109 */       boolean bool1 = somethingChanged;
/*      */       return bool1;
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/* 1113 */       throw handleSQLException("delete from FINANCE_CUBE_DATA_TYPE where    FINANCE_CUBE_ID = ? AND DATA_TYPE_ID = ? ", sqle);
/*      */     }
/*      */     finally
/*      */     {
/* 1117 */       if (deleteStmt != null)
/*      */       {
/* 1119 */         closeStatement(deleteStmt);
/* 1120 */         closeConnection();
/*      */       }
/*      */ 
/* 1123 */       this.mDetails = null;
/*      */ 
/* 1125 */       if ((somethingChanged) && 
/* 1126 */         (timer != null))
/* 1127 */         timer.logDebug("update", "collection"); 
/* 1127 */     }
/*      */   }
/*      */ 
/*      */   public void bulkGetAll(ModelPK entityPK, Collection owners, String dependants)
/*      */   {
/* 1151 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*      */ 
/* 1153 */     PreparedStatement stmt = null;
/* 1154 */     ResultSet resultSet = null;
/*      */ 
/* 1156 */     int itemCount = 0;
/*      */ 
/* 1158 */     FinanceCubeEVO owningEVO = null;
/* 1159 */     Iterator ownersIter = owners.iterator();
/* 1160 */     while (ownersIter.hasNext())
/*      */     {
/* 1162 */       owningEVO = (FinanceCubeEVO)ownersIter.next();
/* 1163 */       owningEVO.setFinanceCubeDataTypesAllItemsLoaded(true);
/*      */     }
/* 1165 */     ownersIter = owners.iterator();
/* 1166 */     owningEVO = (FinanceCubeEVO)ownersIter.next();
/* 1167 */     Collection theseItems = null;
/*      */     try
/*      */     {
/* 1171 */       stmt = getConnection().prepareStatement("select FINANCE_CUBE_DATA_TYPE.FINANCE_CUBE_ID,FINANCE_CUBE_DATA_TYPE.DATA_TYPE_ID,FINANCE_CUBE_DATA_TYPE.CUBE_LAST_UPDATED_TIME,FINANCE_CUBE_DATA_TYPE.UPDATED_BY_USER_ID,FINANCE_CUBE_DATA_TYPE.UPDATED_TIME,FINANCE_CUBE_DATA_TYPE.CREATED_TIME from FINANCE_CUBE_DATA_TYPE,FINANCE_CUBE where 1=1 and FINANCE_CUBE_DATA_TYPE.FINANCE_CUBE_ID = FINANCE_CUBE.FINANCE_CUBE_ID and FINANCE_CUBE.MODEL_ID = ? order by  FINANCE_CUBE_DATA_TYPE.FINANCE_CUBE_ID ,FINANCE_CUBE_DATA_TYPE.FINANCE_CUBE_ID ,FINANCE_CUBE_DATA_TYPE.DATA_TYPE_ID");
/*      */ 
/* 1173 */       int col = 1;
/* 1174 */       stmt.setInt(col++, entityPK.getModelId());
/*      */ 
/* 1176 */       resultSet = stmt.executeQuery();
/*      */ 
/* 1179 */       while (resultSet.next())
/*      */       {
/* 1181 */         itemCount++;
/* 1182 */         this.mDetails = getEvoFromJdbc(resultSet);
/*      */ 
/* 1187 */         while (this.mDetails.getFinanceCubeId() != owningEVO.getFinanceCubeId())
/*      */         {
/* 1191 */           if (!ownersIter.hasNext())
/*      */           {
/* 1193 */             this._log.debug("bulkGetAll", "can't find owning [FinanceCubeId=" + this.mDetails.getFinanceCubeId() + "] for " + this.mDetails.getPK());
/*      */ 
/* 1197 */             this._log.debug("bulkGetAll", "loaded owners are:");
/* 1198 */             ownersIter = owners.iterator();
/* 1199 */             while (ownersIter.hasNext())
/*      */             {
/* 1201 */               owningEVO = (FinanceCubeEVO)ownersIter.next();
/* 1202 */               this._log.debug("bulkGetAll", "    " + owningEVO.toString());
/*      */             }
/* 1204 */             throw new IllegalStateException("can't find owner");
/*      */           }
/* 1206 */           owningEVO = (FinanceCubeEVO)ownersIter.next();
/*      */         }
/* 1208 */         if (owningEVO.getFinanceCubeDataTypes() == null)
/*      */         {
/* 1210 */           theseItems = new ArrayList();
/* 1211 */           owningEVO.setFinanceCubeDataTypes(theseItems);
/* 1212 */           owningEVO.setFinanceCubeDataTypesAllItemsLoaded(true);
/*      */         }
/* 1214 */         theseItems.add(this.mDetails);
/*      */       }
/*      */ 
/* 1217 */       if (timer != null) {
/* 1218 */         timer.logDebug("bulkGetAll", "items=" + itemCount);
/*      */       }
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/* 1223 */       throw handleSQLException("select FINANCE_CUBE_DATA_TYPE.FINANCE_CUBE_ID,FINANCE_CUBE_DATA_TYPE.DATA_TYPE_ID,FINANCE_CUBE_DATA_TYPE.CUBE_LAST_UPDATED_TIME,FINANCE_CUBE_DATA_TYPE.UPDATED_BY_USER_ID,FINANCE_CUBE_DATA_TYPE.UPDATED_TIME,FINANCE_CUBE_DATA_TYPE.CREATED_TIME from FINANCE_CUBE_DATA_TYPE,FINANCE_CUBE where 1=1 and FINANCE_CUBE_DATA_TYPE.FINANCE_CUBE_ID = FINANCE_CUBE.FINANCE_CUBE_ID and FINANCE_CUBE.MODEL_ID = ? order by  FINANCE_CUBE_DATA_TYPE.FINANCE_CUBE_ID ,FINANCE_CUBE_DATA_TYPE.FINANCE_CUBE_ID ,FINANCE_CUBE_DATA_TYPE.DATA_TYPE_ID", sqle);
/*      */     }
/*      */     finally
/*      */     {
/* 1227 */       closeResultSet(resultSet);
/* 1228 */       closeStatement(stmt);
/* 1229 */       closeConnection();
/*      */ 
/* 1231 */       this.mDetails = null;
/*      */     }
/*      */   }
/*      */ 
/*      */   public Collection getAll(int selectFinanceCubeId, String dependants, Collection currentList)
/*      */   {
/* 1256 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/* 1257 */     PreparedStatement stmt = null;
/* 1258 */     ResultSet resultSet = null;
/*      */ 
/* 1260 */     ArrayList items = new ArrayList();
/*      */     try
/*      */     {
/* 1264 */       stmt = getConnection().prepareStatement("select FINANCE_CUBE_DATA_TYPE.FINANCE_CUBE_ID,FINANCE_CUBE_DATA_TYPE.DATA_TYPE_ID,FINANCE_CUBE_DATA_TYPE.CUBE_LAST_UPDATED_TIME,FINANCE_CUBE_DATA_TYPE.UPDATED_BY_USER_ID,FINANCE_CUBE_DATA_TYPE.UPDATED_TIME,FINANCE_CUBE_DATA_TYPE.CREATED_TIME from FINANCE_CUBE_DATA_TYPE where    FINANCE_CUBE_ID = ? ");
/*      */ 
/* 1266 */       int col = 1;
/* 1267 */       stmt.setInt(col++, selectFinanceCubeId);
/*      */ 
/* 1269 */       resultSet = stmt.executeQuery();
/*      */ 
/* 1271 */       while (resultSet.next())
/*      */       {
/* 1273 */         this.mDetails = getEvoFromJdbc(resultSet);
/*      */ 
/* 1276 */         items.add(this.mDetails);
/*      */       }
/*      */ 
/* 1279 */       if (currentList != null)
/*      */       {
/* 1282 */         ListIterator iter = items.listIterator();
/* 1283 */         FinanceCubeDataTypeEVO currentEVO = null;
/* 1284 */         FinanceCubeDataTypeEVO newEVO = null;
/* 1285 */         while (iter.hasNext())
/*      */         {
/* 1287 */           newEVO = (FinanceCubeDataTypeEVO)iter.next();
/* 1288 */           Iterator iter2 = currentList.iterator();
/* 1289 */           while (iter2.hasNext())
/*      */           {
/* 1291 */             currentEVO = (FinanceCubeDataTypeEVO)iter2.next();
/* 1292 */             if (!currentEVO.getPK().equals(newEVO.getPK()))
/*      */               continue;
/* 1294 */             iter.set(currentEVO);
/*      */           }
/*      */ 
/*      */         }
/*      */ 
/* 1300 */         Iterator iter2 = currentList.iterator();
/* 1301 */         while (iter2.hasNext())
/*      */         {
/* 1303 */           currentEVO = (FinanceCubeDataTypeEVO)iter2.next();
/* 1304 */           if (currentEVO.insertPending()) {
/* 1305 */             items.add(currentEVO);
/*      */           }
/*      */         }
/*      */       }
/* 1309 */       this.mDetails = null;
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/* 1313 */       throw handleSQLException("select FINANCE_CUBE_DATA_TYPE.FINANCE_CUBE_ID,FINANCE_CUBE_DATA_TYPE.DATA_TYPE_ID,FINANCE_CUBE_DATA_TYPE.CUBE_LAST_UPDATED_TIME,FINANCE_CUBE_DATA_TYPE.UPDATED_BY_USER_ID,FINANCE_CUBE_DATA_TYPE.UPDATED_TIME,FINANCE_CUBE_DATA_TYPE.CREATED_TIME from FINANCE_CUBE_DATA_TYPE where    FINANCE_CUBE_ID = ? ", sqle);
/*      */     }
/*      */     finally
/*      */     {
/* 1317 */       closeResultSet(resultSet);
/* 1318 */       closeStatement(stmt);
/* 1319 */       closeConnection();
/*      */ 
/* 1321 */       if (timer != null) {
/* 1322 */         timer.logDebug("getAll", " FinanceCubeId=" + selectFinanceCubeId + " items=" + items.size());
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/* 1327 */     return items;
/*      */   }
/*      */ 
/*      */   public FinanceCubeDataTypeEVO getDetails(ModelCK paramCK, String dependants)
/*      */     throws ValidationException
/*      */   {
/* 1341 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*      */ 
/* 1344 */     if (this.mDetails == null) {
/* 1345 */       doLoad(((FinanceCubeDataTypeCK)paramCK).getFinanceCubeDataTypePK());
/*      */     }
/* 1347 */     else if (!this.mDetails.getPK().equals(((FinanceCubeDataTypeCK)paramCK).getFinanceCubeDataTypePK())) {
/* 1348 */       doLoad(((FinanceCubeDataTypeCK)paramCK).getFinanceCubeDataTypePK());
/*      */     }
/*      */ 
/* 1351 */     FinanceCubeDataTypeEVO details = new FinanceCubeDataTypeEVO();
/* 1352 */     details = this.mDetails.deepClone();
/*      */ 
/* 1354 */     if (timer != null) {
/* 1355 */       timer.logDebug("getDetails", paramCK + " " + dependants);
/*      */     }
/* 1357 */     return details;
/*      */   }
/*      */ 
/*      */   public FinanceCubeDataTypeEVO getDetails(ModelCK paramCK, FinanceCubeDataTypeEVO paramEVO, String dependants)
/*      */     throws ValidationException
/*      */   {
/* 1363 */     FinanceCubeDataTypeEVO savedEVO = this.mDetails;
/* 1364 */     this.mDetails = paramEVO;
/* 1365 */     FinanceCubeDataTypeEVO newEVO = getDetails(paramCK, dependants);
/* 1366 */     this.mDetails = savedEVO;
/* 1367 */     return newEVO;
/*      */   }
/*      */ 
/*      */   public FinanceCubeDataTypeEVO getDetails(String dependants)
/*      */     throws ValidationException
/*      */   {
/* 1373 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*      */ 
/* 1377 */     FinanceCubeDataTypeEVO details = this.mDetails.deepClone();
/*      */ 
/* 1379 */     if (timer != null) {
/* 1380 */       timer.logDebug("getDetails", this.mDetails.getPK() + " " + dependants);
/*      */     }
/* 1382 */     return details;
/*      */   }
/*      */ 
/*      */   public String getEntityName()
/*      */   {
/* 1387 */     return "FinanceCubeDataType";
/*      */   }
/*      */ 
/*      */   public FinanceCubeDataTypeRefImpl getRef(FinanceCubeDataTypePK paramFinanceCubeDataTypePK)
/*      */   {
/* 1392 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/* 1393 */     PreparedStatement stmt = null;
/* 1394 */     ResultSet resultSet = null;
/*      */     try
/*      */     {
/* 1397 */       stmt = getConnection().prepareStatement("select 0,MODEL.MODEL_ID,FINANCE_CUBE.FINANCE_CUBE_ID from FINANCE_CUBE_DATA_TYPE,MODEL,FINANCE_CUBE where 1=1 and FINANCE_CUBE_DATA_TYPE.FINANCE_CUBE_ID = ? and FINANCE_CUBE_DATA_TYPE.DATA_TYPE_ID = ? and FINANCE_CUBE_DATA_TYPE.FINANCE_CUBE_ID = FINANCE_CUBE.FINANCE_CUBE_ID and FINANCE_CUBE.FINANCE_CUBE_ID = MODEL.FINANCE_CUBE_ID");
/* 1398 */       int col = 1;
/* 1399 */       stmt.setInt(col++, paramFinanceCubeDataTypePK.getFinanceCubeId());
/* 1400 */       stmt.setShort(col++, paramFinanceCubeDataTypePK.getDataTypeId());
/*      */ 
/* 1402 */       resultSet = stmt.executeQuery();
/*      */ 
/* 1404 */       if (!resultSet.next()) {
/* 1405 */         throw new RuntimeException(getEntityName() + " getRef " + paramFinanceCubeDataTypePK + " not found");
/*      */       }
/* 1407 */       col = 2;
/* 1408 */       ModelPK newModelPK = new ModelPK(resultSet.getInt(col++));
/*      */ 
/* 1412 */       FinanceCubePK newFinanceCubePK = new FinanceCubePK(resultSet.getInt(col++));
/*      */ 
/* 1416 */       String textFinanceCubeDataType = "";
/* 1417 */       FinanceCubeDataTypeCK ckFinanceCubeDataType = new FinanceCubeDataTypeCK(newModelPK, newFinanceCubePK, paramFinanceCubeDataTypePK);
/*      */ 
/* 1423 */       FinanceCubeDataTypeRefImpl localFinanceCubeDataTypeRefImpl = new FinanceCubeDataTypeRefImpl(ckFinanceCubeDataType, textFinanceCubeDataType);
/*      */       return localFinanceCubeDataTypeRefImpl;
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/* 1428 */       throw handleSQLException(paramFinanceCubeDataTypePK, "select 0,MODEL.MODEL_ID,FINANCE_CUBE.FINANCE_CUBE_ID from FINANCE_CUBE_DATA_TYPE,MODEL,FINANCE_CUBE where 1=1 and FINANCE_CUBE_DATA_TYPE.FINANCE_CUBE_ID = ? and FINANCE_CUBE_DATA_TYPE.DATA_TYPE_ID = ? and FINANCE_CUBE_DATA_TYPE.FINANCE_CUBE_ID = FINANCE_CUBE.FINANCE_CUBE_ID and FINANCE_CUBE.FINANCE_CUBE_ID = MODEL.FINANCE_CUBE_ID", sqle);
/*      */     }
/*      */     finally
/*      */     {
/* 1432 */       closeResultSet(resultSet);
/* 1433 */       closeStatement(stmt);
/* 1434 */       closeConnection();
/*      */ 
/* 1436 */       if (timer != null)
/* 1437 */         timer.logDebug("getRef", paramFinanceCubeDataTypePK); 
/* 1437 */     }
/*      */   }
/*      */ }

/* Location:           /home/oracle/coa/cp.ear/cp.ear_orginal/cp-server.jar
 * Qualified Name:     com.cedar.cp.ejb.impl.model.FinanceCubeDataTypeDAO
 * JD-Core Version:    0.6.0
 */