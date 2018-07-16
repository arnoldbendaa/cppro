/*      */ package com.cedar.cp.ejb.impl.datatype;
/*      */ 
/*      */ import com.cedar.cp.api.base.DuplicateNameValidationException;
/*      */ import com.cedar.cp.api.base.ValidationException;
/*      */ import com.cedar.cp.api.base.VersionValidationException;
/*      */ import com.cedar.cp.api.datatype.DataTypeRef;
/*      */ import com.cedar.cp.dto.dataEntry.AvailableStructureElementELO;
/*      */ import com.cedar.cp.dto.datatype.AllDataTypeForFinanceCubeELO;
/*      */ import com.cedar.cp.dto.datatype.AllDataTypesELO;
/*      */ import com.cedar.cp.dto.datatype.AllDataTypesForModelELO;
/*      */ import com.cedar.cp.dto.datatype.AllDataTypesWebELO;
/*      */ import com.cedar.cp.dto.datatype.DataTypeCK;
/*      */ import com.cedar.cp.dto.datatype.DataTypeDependenciesELO;
/*      */ import com.cedar.cp.dto.datatype.DataTypeDetailsForVisIDELO;
/*      */ import com.cedar.cp.dto.datatype.DataTypePK;
/*      */ import com.cedar.cp.dto.datatype.DataTypeRefImpl;
/*      */ import com.cedar.cp.dto.datatype.DataTypeRelCK;
/*      */ import com.cedar.cp.dto.datatype.DataTypeRelPK;
/*      */ import com.cedar.cp.dto.datatype.DataTypeRelRefImpl;
/*      */ import com.cedar.cp.dto.datatype.DataTypesByTypeELO;
/*      */ import com.cedar.cp.dto.datatype.DataTypesByTypeWriteableELO;
/*      */ import com.cedar.cp.dto.datatype.DataTypesForImpExpELO;
/*      */ import com.cedar.cp.dto.datatype.PickerDataTypesFinCubeELO;
/*      */ import com.cedar.cp.dto.model.FinanceCubeDataTypePK;
/*      */ import com.cedar.cp.dto.model.FinanceCubeDataTypeRefImpl;
/*      */ import com.cedar.cp.dto.model.FinanceCubePK;
/*      */ import com.cedar.cp.dto.model.FinanceCubeRefImpl;
/*      */ import com.cedar.cp.ejb.impl.base.AbstractDAO;
/*      */ import com.cedar.cp.ejb.impl.base.SqlExecutor;
/*      */ import com.cedar.cp.util.Log;
/*      */ import com.cedar.cp.util.SqlBuilder;
/*      */ import com.cedar.cp.util.Timer;
/*      */ import com.cedar.cp.util.xmlreport.Constraint;
/*      */ import java.io.PrintStream;
/*      */ import java.sql.Connection;
/*      */ import java.sql.PreparedStatement;
/*      */ import java.sql.ResultSet;
/*      */ import java.sql.SQLException;
/*      */ import java.sql.Timestamp;
/*      */ import java.text.MessageFormat;
/*      */ import java.util.Collection;
/*      */ import java.util.Collections;
/*      */ import java.util.Date;
/*      */ import java.util.HashMap;
/*      */ import java.util.Iterator;
/*      */ import java.util.List;
/*      */ import java.util.Map;
/*      */ import java.util.Set;
/*      */ import javax.sql.DataSource;
/*      */ 
/*      */ public class DataTypeDAO extends AbstractDAO
/*      */ {
/*   48 */   Log _log = new Log(getClass());
/*      */   protected static final String SQL_FIND_BY_PRIMARY_KEY = "select DATA_TYPE_ID from DATA_TYPE where    DATA_TYPE_ID = ? ";
/*      */   private static final String SQL_SELECT_COLUMNS = "select DATA_TYPE.DATA_TYPE_ID,DATA_TYPE.VIS_ID,DATA_TYPE.DESCRIPTION,DATA_TYPE.READ_ONLY_FLAG,DATA_TYPE.AVAILABLE_FOR_IMPORT,DATA_TYPE.AVAILABLE_FOR_EXPORT,DATA_TYPE.SUB_TYPE,DATA_TYPE.FORMULA_EXPR,DATA_TYPE.MEASURE_CLASS,DATA_TYPE.MEASURE_LENGTH,DATA_TYPE.MEASURE_SCALE,DATA_TYPE.MEASURE_VALIDATION,DATA_TYPE.VERSION_NUM,DATA_TYPE.UPDATED_BY_USER_ID,DATA_TYPE.UPDATED_TIME,DATA_TYPE.CREATED_TIME";
/*      */   protected static final String SQL_LOAD = " from DATA_TYPE where    DATA_TYPE_ID = ? ";
/*      */   protected static final String SQL_CREATE = "insert into DATA_TYPE ( DATA_TYPE_ID,VIS_ID,DESCRIPTION,READ_ONLY_FLAG,AVAILABLE_FOR_IMPORT,AVAILABLE_FOR_EXPORT,SUB_TYPE,FORMULA_EXPR,MEASURE_CLASS,MEASURE_LENGTH,MEASURE_SCALE,MEASURE_VALIDATION,VERSION_NUM,UPDATED_BY_USER_ID,UPDATED_TIME,CREATED_TIME) values ( ?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
/*      */   protected static final String SQL_UPDATE_SEQ_NUM = "update DATA_TYPE_SEQ set SEQ_NUM = SEQ_NUM + ?";
/*      */   protected static final String SQL_SELECT_SEQ_NUM = "select SEQ_NUM from DATA_TYPE_SEQ";
/*      */   protected static final String SQL_DUPLICATE_VALUE_CHECK_VISID = "select count(*) from DATA_TYPE where    VIS_ID = ? and not(    DATA_TYPE_ID = ? )";
/*      */   protected static final String SQL_STORE = "update DATA_TYPE set VIS_ID = ?,DESCRIPTION = ?,READ_ONLY_FLAG = ?,AVAILABLE_FOR_IMPORT = ?,AVAILABLE_FOR_EXPORT = ?,SUB_TYPE = ?,FORMULA_EXPR = ?,MEASURE_CLASS = ?,MEASURE_LENGTH = ?,MEASURE_SCALE = ?,MEASURE_VALIDATION = ?,VERSION_NUM = ?,UPDATED_BY_USER_ID = ?,UPDATED_TIME = ?,CREATED_TIME = ? where    DATA_TYPE_ID = ? AND VERSION_NUM = ?";
/*      */   protected static final String SQL_CHECK_VERSION_NUM = "select VERSION_NUM from DATA_TYPE where DATA_TYPE_ID = ?";
/*      */   protected static final String SQL_REMOVE = "delete from DATA_TYPE where    DATA_TYPE_ID = ? ";
/*  778 */   protected static String SQL_ALL_DATA_TYPES = "select 0       ,DATA_TYPE.DATA_TYPE_ID      ,DATA_TYPE.VIS_ID      ,DATA_TYPE.DESCRIPTION      ,DATA_TYPE.SUB_TYPE      ,DATA_TYPE.MEASURE_CLASS      ,DATA_TYPE.MEASURE_LENGTH  ,DATA_TYPE.MEASURE_SCALE    ,DATA_TYPE.DESCRIPTION      ,DATA_TYPE.SUB_TYPE      ,DATA_TYPE.MEASURE_CLASS from DATA_TYPE where 1=1  order by DATA_TYPE.VIS_ID";
/*      */ 
/*  876 */   protected static String SQL_ALL_DATA_TYPES_WEB = "select 0       ,DATA_TYPE.DATA_TYPE_ID      ,DATA_TYPE.VIS_ID      ,DATA_TYPE.DESCRIPTION      ,DATA_TYPE.SUB_TYPE      ,DATA_TYPE.MEASURE_CLASS      ,DATA_TYPE.MEASURE_LENGTH      ,DATA_TYPE.DATA_TYPE_ID      ,DATA_TYPE.VIS_ID      ,DATA_TYPE.DESCRIPTION from DATA_TYPE where 1=1  order by DATA_TYPE.VIS_ID";
/*      */ 
/*  974 */   protected static String SQL_ALL_DATA_TYPE_FOR_FINANCE_CUBE = "select 0       ,DATA_TYPE.DATA_TYPE_ID      ,DATA_TYPE.VIS_ID      ,DATA_TYPE.DESCRIPTION      ,DATA_TYPE.SUB_TYPE      ,DATA_TYPE.MEASURE_CLASS      ,DATA_TYPE.MEASURE_LENGTH      ,FINANCE_CUBE_DATA_TYPE.FINANCE_CUBE_ID      ,FINANCE_CUBE_DATA_TYPE.DATA_TYPE_ID      ,DATA_TYPE.READ_ONLY_FLAG      ,DATA_TYPE.SUB_TYPE      ,DATA_TYPE.MEASURE_CLASS      ,DATA_TYPE.MEASURE_SCALE      ,DATA_TYPE.MEASURE_LENGTH from DATA_TYPE    ,FINANCE_CUBE_DATA_TYPE where 1=1  and  FINANCE_CUBE_DATA_TYPE.FINANCE_CUBE_ID = ? and FINANCE_CUBE_DATA_TYPE.DATA_TYPE_ID = DATA_TYPE.DATA_TYPE_ID";
/*      */ 
/* 1101 */   protected static String SQL_ALL_DATA_TYPES_FOR_MODEL = "select distinct 0       ,DATA_TYPE.DATA_TYPE_ID      ,DATA_TYPE.VIS_ID      ,DATA_TYPE.DESCRIPTION      ,DATA_TYPE.SUB_TYPE      ,DATA_TYPE.MEASURE_CLASS      ,DATA_TYPE.MEASURE_LENGTH      ,FINANCE_CUBE.FINANCE_CUBE_ID      ,FINANCE_CUBE.VIS_ID      ,FINANCE_CUBE_DATA_TYPE.FINANCE_CUBE_ID      ,FINANCE_CUBE_DATA_TYPE.DATA_TYPE_ID      ,DATA_TYPE.SUB_TYPE      ,DATA_TYPE.MEASURE_CLASS from DATA_TYPE    ,FINANCE_CUBE    ,FINANCE_CUBE_DATA_TYPE where 1=1  and  FINANCE_CUBE.MODEL_ID = ? and FINANCE_CUBE.FINANCE_CUBE_ID = FINANCE_CUBE_DATA_TYPE.FINANCE_CUBE_ID and FINANCE_CUBE_DATA_TYPE.DATA_TYPE_ID = DATA_TYPE.DATA_TYPE_ID and ( DATA_TYPE.SUB_TYPE = 0 or DATA_TYPE.SUB_TYPE = 4 ) order by DATA_TYPE.VIS_ID";
/*      */ 
/* 1234 */   protected static String SQL_DATA_TYPES_BY_TYPE = "select 0       ,DATA_TYPE.DATA_TYPE_ID      ,DATA_TYPE.VIS_ID      ,DATA_TYPE.DESCRIPTION      ,DATA_TYPE.SUB_TYPE      ,DATA_TYPE.MEASURE_CLASS      ,DATA_TYPE.MEASURE_LENGTH      ,DATA_TYPE.DESCRIPTION      ,DATA_TYPE.SUB_TYPE      ,DATA_TYPE.DATA_TYPE_ID      ,DATA_TYPE.VIS_ID from DATA_TYPE where 1=1  and  DATA_TYPE.SUB_TYPE = ? order by DATA_TYPE.VIS_ID";
/*      */ 
/* 1339 */   protected static String SQL_DATA_TYPES_BY_TYPE_WRITEABLE = "select 0       ,DATA_TYPE.DATA_TYPE_ID      ,DATA_TYPE.VIS_ID      ,DATA_TYPE.DESCRIPTION      ,DATA_TYPE.SUB_TYPE      ,DATA_TYPE.MEASURE_CLASS      ,DATA_TYPE.MEASURE_LENGTH      ,DATA_TYPE.DESCRIPTION      ,DATA_TYPE.SUB_TYPE      ,DATA_TYPE.DATA_TYPE_ID      ,DATA_TYPE.VIS_ID from DATA_TYPE where 1=1  and  DATA_TYPE.SUB_TYPE = ? and DATA_TYPE.READ_ONLY_FLAG <> 'Y' order by DATA_TYPE.VIS_ID";
/*      */ 
/* 1444 */   protected static String SQL_DATA_TYPE_DEPENDENCIES = "select 0       ,DATA_TYPE.DATA_TYPE_ID      ,DATA_TYPE.VIS_ID      ,DATA_TYPE.DESCRIPTION      ,DATA_TYPE.SUB_TYPE      ,DATA_TYPE.MEASURE_CLASS      ,DATA_TYPE.MEASURE_LENGTH      ,DATA_TYPE_REL.DATA_TYPE_ID      ,DATA_TYPE_REL.REF_DATA_TYPE_ID      ,DATA_TYPE.DATA_TYPE_ID      ,DATA_TYPE.VIS_ID      ,DATA_TYPE.DESCRIPTION from DATA_TYPE    ,DATA_TYPE_REL where 1=1  and  DATA_TYPE_REL.DATA_TYPE_ID = ? and DATA_TYPE_REL.REF_DATA_TYPE_ID = DATA_TYPE.DATA_TYPE_ID";
/*      */ 
/* 1563 */   protected static String SQL_DATA_TYPES_FOR_IMP_EXP = "select 0       ,DATA_TYPE.DATA_TYPE_ID      ,DATA_TYPE.VIS_ID      ,DATA_TYPE.DESCRIPTION      ,DATA_TYPE.SUB_TYPE      ,DATA_TYPE.MEASURE_CLASS      ,DATA_TYPE.MEASURE_LENGTH      ,DATA_TYPE.DATA_TYPE_ID      ,DATA_TYPE.VIS_ID      ,DATA_TYPE.DESCRIPTION      ,DATA_TYPE.READ_ONLY_FLAG      ,DATA_TYPE.AVAILABLE_FOR_IMPORT      ,DATA_TYPE.AVAILABLE_FOR_EXPORT      ,DATA_TYPE.SUB_TYPE      ,DATA_TYPE.FORMULA_EXPR from DATA_TYPE where 1=1  and  AVAILABLE_FOR_IMPORT = 'Y' or AVAILABLE_FOR_EXPORT = 'Y'";
/*      */ 
/* 1682 */   protected static String SQL_DATA_TYPE_DETAILS_FOR_VIS_I_D = "select 0       ,DATA_TYPE.DATA_TYPE_ID      ,DATA_TYPE.VIS_ID      ,DATA_TYPE.DESCRIPTION      ,DATA_TYPE.SUB_TYPE      ,DATA_TYPE.MEASURE_CLASS      ,DATA_TYPE.MEASURE_LENGTH      ,DATA_TYPE.DATA_TYPE_ID      ,DATA_TYPE.VIS_ID      ,DATA_TYPE.DESCRIPTION from DATA_TYPE where 1=1  and  VIS_ID = ?";
/*      */ 
/* 1783 */   private static String[][] SQL_DELETE_CHILDREN = { { "DATA_TYPE_REL", "delete from DATA_TYPE_REL where     DATA_TYPE_REL.DATA_TYPE_ID = ? " } };
/*      */ 
/* 1792 */   private static String[][] SQL_DELETE_CHILDRENS_DEPENDANTS = new String[0][];
/*      */ 
/* 1796 */   private static String SQL_DELETE_DEPENDANT_CRITERIA = "and DATA_TYPE.DATA_TYPE_ID = ?)";
/*      */   private static final String SQL_CHECK_IF_VALID = "select VERSION_NUM from DATA_TYPE where   DATA_TYPE_ID = ?";
/*      */   public static final String SQL_GET_DATA_TYPE_REL_REF = "select 0,DATA_TYPE.DATA_TYPE_ID from DATA_TYPE_REL,DATA_TYPE where 1=1 and DATA_TYPE_REL.DATA_TYPE_ID = ? and DATA_TYPE_REL.REF_DATA_TYPE_ID = ? and DATA_TYPE_REL.DATA_TYPE_ID = DATA_TYPE.DATA_TYPE_ID";
/*      */   private static final String VALIDATE_DELETE_SQL = "select dt.vis_id from data_type dt, data_type_rel dtr where dt.data_type_id = dtr.data_type_id and       dtr.ref_data_type_id = ? ";
/* 2288 */   private static String VALIDATE_DATA_TYPE_REFS_SQL = "select fc.finance_cube_id, fc.vis_id, ref_dt.ref_data_type_id, ref_dt.ref_data_type_vis_id \nfrom finance_cube fc, \n     ( select fcdt.finance_cube_id, fcdt.data_type_id \n       from finance_cube_data_type fcdt \n       where fcdt.data_type_id = ? ) dep_fc, \n     ( select dtr.data_type_id, dtr.ref_data_type_id, dt.vis_id as ref_data_type_vis_id \n       from data_type_rel dtr, data_type dt \n       where dtr.ref_data_type_id = dt.data_type_id ) ref_dt \nwhere fc.finance_cube_id = dep_fc.finance_cube_id and \n      ref_dt.data_type_id = dep_fc.data_type_id and \n      ref_dt.ref_data_type_id not in ( select fcdt.data_type_id \n                                       from finance_cube_data_type fcdt \n                                       where fcdt.finance_cube_id = fc.finance_cube_id )";
/*      */   protected DataTypeRelDAO mDataTypeRelDAO;
/*      */   protected DataTypeEVO mDetails;
/*      */ 
/*      */   public DataTypeDAO(Connection connection)
/*      */   {
/*   55 */     super(connection);
/*      */   }
/*      */ 
/*      */   public DataTypeDAO()
/*      */   {
/*      */   }
/*      */ 
/*      */   public DataTypeDAO(DataSource ds)
/*      */   {
/*   71 */     super(ds);
/*      */   }
/*      */ 
/*      */   protected DataTypePK getPK()
/*      */   {
/*   79 */     return this.mDetails.getPK();
/*      */   }
/*      */ 
/*      */   public void setDetails(DataTypeEVO details)
/*      */   {
/*   88 */     this.mDetails = details.deepClone();
/*      */   }
/*      */ 
/*      */   public DataTypeEVO setAndGetDetails(DataTypeEVO details, String dependants)
/*      */   {
/*   99 */     setDetails(details);
/*  100 */     generateKeys();
/*  101 */     getDependants(this.mDetails, dependants);
/*  102 */     return this.mDetails.deepClone();
/*      */   }
/*      */ 
/*      */   public DataTypePK create()
/*      */     throws DuplicateNameValidationException, ValidationException
/*      */   {
/*  111 */     doCreate();
/*      */ 
/*  113 */     return this.mDetails.getPK();
/*      */   }
/*      */ 
/*      */   public void load(DataTypePK pk)
/*      */     throws ValidationException
/*      */   {
/*  123 */     doLoad(pk);
/*      */   }
/*      */ 
/*      */   public void store()
/*      */     throws DuplicateNameValidationException, VersionValidationException, ValidationException
/*      */   {
/*  132 */     doStore();
/*      */   }
/*      */ 
/*      */   public void remove()
/*      */   {
/*  141 */     doRemove();
/*      */   }
/*      */ 
/*      */   public DataTypePK findByPrimaryKey(DataTypePK pk_)
/*      */     throws ValidationException
/*      */   {
/*  150 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*  151 */     if (exists(pk_))
/*      */     {
/*  153 */       if (timer != null) {
/*  154 */         timer.logDebug("findByPrimaryKey", pk_);
/*      */       }
/*  156 */       return pk_;
/*      */     }
/*      */ 
/*  159 */     throw new ValidationException(pk_ + " not found");
/*      */   }
/*      */ 
/*      */   protected boolean exists(DataTypePK pk)
/*      */   {
/*  177 */     PreparedStatement stmt = null;
/*  178 */     ResultSet resultSet = null;
/*  179 */     boolean returnValue = false;
/*      */     try
/*      */     {
/*  183 */       stmt = getConnection().prepareStatement("select DATA_TYPE_ID from DATA_TYPE where    DATA_TYPE_ID = ? ");
/*      */ 
/*  185 */       int col = 1;
/*  186 */       stmt.setShort(col++, pk.getDataTypeId());
/*      */ 
/*  188 */       resultSet = stmt.executeQuery();
/*      */ 
/*  190 */       if (!resultSet.next())
/*  191 */         returnValue = false;
/*      */       else
/*  193 */         returnValue = true;
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  197 */       throw handleSQLException(pk, "select DATA_TYPE_ID from DATA_TYPE where    DATA_TYPE_ID = ? ", sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  201 */       closeResultSet(resultSet);
/*  202 */       closeStatement(stmt);
/*  203 */       closeConnection();
/*      */     }
/*  205 */     return returnValue;
/*      */   }
/*      */ 
/*      */   private DataTypeEVO getEvoFromJdbc(ResultSet resultSet_)
/*      */     throws SQLException
/*      */   {
/*  232 */     int col = 1;
/*  233 */     DataTypeEVO evo = new DataTypeEVO(resultSet_.getShort(col++), resultSet_.getString(col++), resultSet_.getString(col++), resultSet_.getString(col++).equals("Y"), resultSet_.getString(col++).equals("Y"), resultSet_.getString(col++).equals("Y"), resultSet_.getInt(col++), resultSet_.getString(col++), getWrappedIntegerFromJdbc(resultSet_, col++), getWrappedIntegerFromJdbc(resultSet_, col++), getWrappedIntegerFromJdbc(resultSet_, col++), resultSet_.getString(col++), resultSet_.getInt(col++), null);
/*      */ 
/*  250 */     evo.setUpdatedByUserId(resultSet_.getInt(col++));
/*  251 */     evo.setUpdatedTime(resultSet_.getTimestamp(col++));
/*  252 */     evo.setCreatedTime(resultSet_.getTimestamp(col++));
/*  253 */     return evo;
/*      */   }
/*      */ 
/*      */   private int putEvoKeysToJdbc(DataTypeEVO evo_, PreparedStatement stmt_, int startCol_) throws SQLException
/*      */   {
/*  258 */     int col = startCol_;
/*  259 */     stmt_.setShort(col++, evo_.getDataTypeId());
/*  260 */     return col;
/*      */   }
/*      */ 
/*      */   private int putEvoDataToJdbc(DataTypeEVO evo_, PreparedStatement stmt_, int startCol_) throws SQLException
/*      */   {
/*  265 */     int col = startCol_;
/*  266 */     stmt_.setString(col++, evo_.getVisId());
/*  267 */     stmt_.setString(col++, evo_.getDescription());
/*  268 */     if (evo_.getReadOnlyFlag())
/*  269 */       stmt_.setString(col++, "Y");
/*      */     else
/*  271 */       stmt_.setString(col++, " ");
/*  272 */     if (evo_.getAvailableForImport())
/*  273 */       stmt_.setString(col++, "Y");
/*      */     else
/*  275 */       stmt_.setString(col++, " ");
/*  276 */     if (evo_.getAvailableForExport())
/*  277 */       stmt_.setString(col++, "Y");
/*      */     else
/*  279 */       stmt_.setString(col++, " ");
/*  280 */     stmt_.setInt(col++, evo_.getSubType());
/*  281 */     stmt_.setString(col++, evo_.getFormulaExpr());
/*  282 */     setWrappedPrimitiveToJdbc(stmt_, col++, evo_.getMeasureClass());
/*  283 */     setWrappedPrimitiveToJdbc(stmt_, col++, evo_.getMeasureLength());
/*  284 */     setWrappedPrimitiveToJdbc(stmt_, col++, evo_.getMeasureScale());
/*  285 */     stmt_.setString(col++, evo_.getMeasureValidation());
/*  286 */     stmt_.setInt(col++, evo_.getVersionNum());
/*  287 */     stmt_.setInt(col++, evo_.getUpdatedByUserId());
/*  288 */     stmt_.setTimestamp(col++, evo_.getUpdatedTime());
/*  289 */     stmt_.setTimestamp(col++, evo_.getCreatedTime());
/*  290 */     return col;
/*      */   }
/*      */ 
/*      */   protected void doLoad(DataTypePK pk)
/*      */     throws ValidationException
/*      */   {
/*  306 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*      */ 
/*  308 */     PreparedStatement stmt = null;
/*  309 */     ResultSet resultSet = null;
/*      */     try
/*      */     {
/*  313 */       stmt = getConnection().prepareStatement("select DATA_TYPE.DATA_TYPE_ID,DATA_TYPE.VIS_ID,DATA_TYPE.DESCRIPTION,DATA_TYPE.READ_ONLY_FLAG,DATA_TYPE.AVAILABLE_FOR_IMPORT,DATA_TYPE.AVAILABLE_FOR_EXPORT,DATA_TYPE.SUB_TYPE,DATA_TYPE.FORMULA_EXPR,DATA_TYPE.MEASURE_CLASS,DATA_TYPE.MEASURE_LENGTH,DATA_TYPE.MEASURE_SCALE,DATA_TYPE.MEASURE_VALIDATION,DATA_TYPE.VERSION_NUM,DATA_TYPE.UPDATED_BY_USER_ID,DATA_TYPE.UPDATED_TIME,DATA_TYPE.CREATED_TIME from DATA_TYPE where    DATA_TYPE_ID = ? ");
/*      */ 
/*  316 */       int col = 1;
/*  317 */       stmt.setShort(col++, pk.getDataTypeId());
/*      */ 
/*  319 */       resultSet = stmt.executeQuery();
/*      */ 
/*  321 */       if (!resultSet.next()) {
/*  322 */         throw new ValidationException(getEntityName() + " select of " + pk + " not found");
/*      */       }
/*      */ 
/*  325 */       this.mDetails = getEvoFromJdbc(resultSet);
/*  326 */       if (this.mDetails.isModified())
/*  327 */         this._log.info("doLoad", this.mDetails);
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  331 */       throw handleSQLException(pk, "select DATA_TYPE.DATA_TYPE_ID,DATA_TYPE.VIS_ID,DATA_TYPE.DESCRIPTION,DATA_TYPE.READ_ONLY_FLAG,DATA_TYPE.AVAILABLE_FOR_IMPORT,DATA_TYPE.AVAILABLE_FOR_EXPORT,DATA_TYPE.SUB_TYPE,DATA_TYPE.FORMULA_EXPR,DATA_TYPE.MEASURE_CLASS,DATA_TYPE.MEASURE_LENGTH,DATA_TYPE.MEASURE_SCALE,DATA_TYPE.MEASURE_VALIDATION,DATA_TYPE.VERSION_NUM,DATA_TYPE.UPDATED_BY_USER_ID,DATA_TYPE.UPDATED_TIME,DATA_TYPE.CREATED_TIME from DATA_TYPE where    DATA_TYPE_ID = ? ", sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  335 */       closeResultSet(resultSet);
/*  336 */       closeStatement(stmt);
/*  337 */       closeConnection();
/*      */ 
/*  339 */       if (timer != null)
/*  340 */         timer.logDebug("doLoad", pk);
/*      */     }
/*      */   }
/*      */ 
/*      */   protected void doCreate()
/*      */     throws DuplicateNameValidationException, ValidationException
/*      */   {
/*  389 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*  390 */     generateKeys();
/*      */ 
/*  392 */     this.mDetails.postCreateInit();
/*      */ 
/*  394 */     PreparedStatement stmt = null;
/*      */     try
/*      */     {
/*  399 */       duplicateValueCheckVisId();
/*      */ 
/*  401 */       this.mDetails.setCreatedTime(new Timestamp(new Date().getTime()));
/*  402 */       this.mDetails.setUpdatedTime(new Timestamp(new Date().getTime()));
/*  403 */       stmt = getConnection().prepareStatement("insert into DATA_TYPE ( DATA_TYPE_ID,VIS_ID,DESCRIPTION,READ_ONLY_FLAG,AVAILABLE_FOR_IMPORT,AVAILABLE_FOR_EXPORT,SUB_TYPE,FORMULA_EXPR,MEASURE_CLASS,MEASURE_LENGTH,MEASURE_SCALE,MEASURE_VALIDATION,VERSION_NUM,UPDATED_BY_USER_ID,UPDATED_TIME,CREATED_TIME) values ( ?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
/*      */ 
/*  406 */       int col = 1;
/*  407 */       col = putEvoKeysToJdbc(this.mDetails, stmt, col);
/*  408 */       col = putEvoDataToJdbc(this.mDetails, stmt, col);
/*      */ 
/*  411 */       int resultCount = stmt.executeUpdate();
/*  412 */       if (resultCount != 1)
/*      */       {
/*  414 */         throw new RuntimeException(getEntityName() + " insert failed (" + this.mDetails.getPK() + "): resultCount=" + resultCount);
/*      */       }
/*      */ 
/*  417 */       this.mDetails.reset();
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  421 */       throw handleSQLException(this.mDetails.getPK(), "insert into DATA_TYPE ( DATA_TYPE_ID,VIS_ID,DESCRIPTION,READ_ONLY_FLAG,AVAILABLE_FOR_IMPORT,AVAILABLE_FOR_EXPORT,SUB_TYPE,FORMULA_EXPR,MEASURE_CLASS,MEASURE_LENGTH,MEASURE_SCALE,MEASURE_VALIDATION,VERSION_NUM,UPDATED_BY_USER_ID,UPDATED_TIME,CREATED_TIME) values ( ?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)", sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  425 */       closeStatement(stmt);
/*  426 */       closeConnection();
/*      */ 
/*  428 */       if (timer != null) {
/*  429 */         timer.logDebug("doCreate", this.mDetails.toString());
/*      */       }
/*      */     }
/*      */ 
/*      */     try
/*      */     {
/*  435 */       getDataTypeRelDAO().update(this.mDetails.getDataTypeDependenciesMap());
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/*  441 */       throw new RuntimeException("unexpected exception", e);
/*      */     }
/*      */   }
/*      */ 
/*      */   public int reserveIds(int insertCount)
/*      */   {
/*  461 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*      */ 
/*  463 */     PreparedStatement stmt = null;
/*  464 */     ResultSet resultSet = null;
/*  465 */     String sqlString = null;
/*      */     try
/*      */     {
/*  470 */       sqlString = "update DATA_TYPE_SEQ set SEQ_NUM = SEQ_NUM + ?";
/*  471 */       stmt = getConnection().prepareStatement("update DATA_TYPE_SEQ set SEQ_NUM = SEQ_NUM + ?");
/*  472 */       stmt.setInt(1, insertCount);
/*      */ 
/*  474 */       int resultCount = stmt.executeUpdate();
/*  475 */       if (resultCount != 1) {
/*  476 */         throw new RuntimeException(getEntityName() + " reserveIds: update failed: resultCount=" + resultCount);
/*      */       }
/*  478 */       closeStatement(stmt);
/*      */ 
/*  481 */       sqlString = "select SEQ_NUM from DATA_TYPE_SEQ";
/*  482 */       stmt = getConnection().prepareStatement("select SEQ_NUM from DATA_TYPE_SEQ");
/*  483 */       resultSet = stmt.executeQuery();
/*  484 */       if (!resultSet.next())
/*  485 */         throw new RuntimeException(getEntityName() + " reserveIds: select failed");
/*  486 */       int latestKey = resultSet.getInt(1);
/*      */ 
/*  488 */       int i = latestKey - insertCount;
/*      */       return i;
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  492 */       throw handleSQLException(sqlString, sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  496 */       closeResultSet(resultSet);
/*  497 */       closeStatement(stmt);
/*  498 */       closeConnection();
/*      */ 
/*  500 */       if (timer != null)
/*  501 */         timer.logDebug("reserveIds", "keys=" + insertCount); 
/*  501 */     }
/*      */   }
/*      */ 
/*      */   public DataTypePK generateKeys()
/*      */   {
/*  511 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*      */ 
/*  513 */     int insertCount = this.mDetails.getInsertCount(0);
/*      */ 
/*  516 */     if (insertCount == 0) {
/*  517 */       return this.mDetails.getPK();
/*      */     }
/*  519 */     this.mDetails.assignNextKey(reserveIds(insertCount));
/*      */ 
/*  521 */     return this.mDetails.getPK();
/*      */   }
/*      */ 
/*      */   protected void duplicateValueCheckVisId()
/*      */     throws DuplicateNameValidationException
/*      */   {
/*  534 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*  535 */     PreparedStatement stmt = null;
/*  536 */     ResultSet resultSet = null;
/*      */     try
/*      */     {
/*  540 */       stmt = getConnection().prepareStatement("select count(*) from DATA_TYPE where    VIS_ID = ? and not(    DATA_TYPE_ID = ? )");
/*      */ 
/*  543 */       int col = 1;
/*  544 */       stmt.setString(col++, this.mDetails.getVisId());
/*  545 */       col = putEvoKeysToJdbc(this.mDetails, stmt, col);
/*      */ 
/*  548 */       resultSet = stmt.executeQuery();
/*      */ 
/*  550 */       if (!resultSet.next()) {
/*  551 */         throw new RuntimeException(getEntityName() + " select of " + getPK() + " not found");
/*      */       }
/*      */ 
/*  555 */       col = 1;
/*  556 */       int count = resultSet.getInt(col++);
/*  557 */       if (count > 0) {
/*  558 */         throw new DuplicateNameValidationException("Duplicate name (" + this.mDetails.getVisId() + "). Please re-name and retry");
/*      */       }
/*      */ 
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  564 */       throw handleSQLException(getPK(), "select count(*) from DATA_TYPE where    VIS_ID = ? and not(    DATA_TYPE_ID = ? )", sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  568 */       closeResultSet(resultSet);
/*  569 */       closeStatement(stmt);
/*  570 */       closeConnection();
/*      */ 
/*  572 */       if (timer != null)
/*  573 */         timer.logDebug("duplicateValueCheckVisId", "");
/*      */     }
/*      */   }
/*      */ 
/*      */   protected void doStore()
/*      */     throws DuplicateNameValidationException, VersionValidationException, ValidationException
/*      */   {
/*  607 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*      */ 
/*  609 */     generateKeys();
/*      */ 
/*  614 */     PreparedStatement stmt = null;
/*      */ 
/*  616 */     boolean mainChanged = this.mDetails.isModified();
/*  617 */     boolean dependantChanged = false;
/*      */     try
/*      */     {
/*  621 */       if (mainChanged) {
/*  622 */         duplicateValueCheckVisId();
/*      */       }
/*  624 */       if (getDataTypeRelDAO().update(this.mDetails.getDataTypeDependenciesMap())) {
/*  625 */         dependantChanged = true;
/*      */       }
/*  627 */       if ((mainChanged) || (dependantChanged))
/*      */       {
/*  630 */         this.mDetails.setVersionNum(this.mDetails.getVersionNum() + 1);
/*      */ 
/*  633 */         this.mDetails.setUpdatedTime(new Timestamp(new Date().getTime()));
/*  634 */         stmt = getConnection().prepareStatement("update DATA_TYPE set VIS_ID = ?,DESCRIPTION = ?,READ_ONLY_FLAG = ?,AVAILABLE_FOR_IMPORT = ?,AVAILABLE_FOR_EXPORT = ?,SUB_TYPE = ?,FORMULA_EXPR = ?,MEASURE_CLASS = ?,MEASURE_LENGTH = ?,MEASURE_SCALE = ?,MEASURE_VALIDATION = ?,VERSION_NUM = ?,UPDATED_BY_USER_ID = ?,UPDATED_TIME = ?,CREATED_TIME = ? where    DATA_TYPE_ID = ? AND VERSION_NUM = ?");
/*      */ 
/*  637 */         int col = 1;
/*  638 */         col = putEvoDataToJdbc(this.mDetails, stmt, col);
/*  639 */         col = putEvoKeysToJdbc(this.mDetails, stmt, col);
/*      */ 
/*  641 */         stmt.setInt(col++, this.mDetails.getVersionNum() - 1);
/*      */ 
/*  644 */         int resultCount = stmt.executeUpdate();
/*      */ 
/*  646 */         if (resultCount == 0) {
/*  647 */           checkVersionNum();
/*      */         }
/*  649 */         if (resultCount != 1) {
/*  650 */           throw new RuntimeException(getEntityName() + " update failed (" + getPK() + "): resultCount=" + resultCount);
/*      */         }
/*      */ 
/*  653 */         this.mDetails.reset();
/*      */ 
/*  656 */         validateAmendedDataType(this.mDetails.getDataTypeId());
/*      */       }
/*      */ 
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  663 */       throw handleSQLException(getPK(), "update DATA_TYPE set VIS_ID = ?,DESCRIPTION = ?,READ_ONLY_FLAG = ?,AVAILABLE_FOR_IMPORT = ?,AVAILABLE_FOR_EXPORT = ?,SUB_TYPE = ?,FORMULA_EXPR = ?,MEASURE_CLASS = ?,MEASURE_LENGTH = ?,MEASURE_SCALE = ?,MEASURE_VALIDATION = ?,VERSION_NUM = ?,UPDATED_BY_USER_ID = ?,UPDATED_TIME = ?,CREATED_TIME = ? where    DATA_TYPE_ID = ? AND VERSION_NUM = ?", sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  667 */       closeStatement(stmt);
/*  668 */       closeConnection();
/*      */ 
/*  670 */       if ((timer != null) && (
/*  671 */         (mainChanged) || (dependantChanged)))
/*  672 */         timer.logDebug("store", this.mDetails.getPK() + "(" + mainChanged + "," + dependantChanged + ")");
/*      */     }
/*      */   }
/*      */ 
/*      */   private void checkVersionNum()
/*      */     throws VersionValidationException
/*      */   {
/*  684 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*  685 */     PreparedStatement stmt = null;
/*  686 */     ResultSet resultSet = null;
/*      */     try
/*      */     {
/*  690 */       stmt = getConnection().prepareStatement("select VERSION_NUM from DATA_TYPE where DATA_TYPE_ID = ?");
/*      */ 
/*  693 */       int col = 1;
/*  694 */       stmt.setShort(col++, this.mDetails.getDataTypeId());
/*      */ 
/*  697 */       resultSet = stmt.executeQuery();
/*      */ 
/*  699 */       if (!resultSet.next()) {
/*  700 */         throw new RuntimeException(getEntityName() + " checkVersionNum: select of " + getPK() + " not found");
/*      */       }
/*      */ 
/*  703 */       col = 1;
/*  704 */       int dbVersionNumber = resultSet.getInt(col++);
/*  705 */       if (this.mDetails.getVersionNum() - 1 != dbVersionNumber) {
/*  706 */         throw new VersionValidationException(getEntityName() + " " + getPK() + " expected:" + (this.mDetails.getVersionNum() - 1) + " found:" + dbVersionNumber);
/*      */       }
/*      */ 
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  712 */       throw handleSQLException(getPK(), "select VERSION_NUM from DATA_TYPE where DATA_TYPE_ID = ?", sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  716 */       closeStatement(stmt);
/*  717 */       closeResultSet(resultSet);
/*      */ 
/*  719 */       if (timer != null)
/*  720 */         timer.logDebug("checkVersionNum", this.mDetails.getPK());
/*      */     }
/*      */   }
/*      */ 
/*      */   protected void doRemove()
/*      */   {
/*  737 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*  738 */     deleteDependants(this.mDetails.getPK());
/*      */ 
/*  743 */     PreparedStatement stmt = null;
/*      */     try
/*      */     {
/*  748 */       stmt = getConnection().prepareStatement("delete from DATA_TYPE where    DATA_TYPE_ID = ? ");
/*      */ 
/*  751 */       int col = 1;
/*  752 */       stmt.setShort(col++, this.mDetails.getDataTypeId());
/*      */ 
/*  754 */       int resultCount = stmt.executeUpdate();
/*      */ 
/*  756 */       if (resultCount != 1) {
/*  757 */         throw new RuntimeException(getEntityName() + " delete failed (" + getPK() + "): resultCount=" + resultCount);
/*      */       }
/*      */ 
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  763 */       throw handleSQLException(getPK(), "delete from DATA_TYPE where    DATA_TYPE_ID = ? ", sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  767 */       closeStatement(stmt);
/*  768 */       closeConnection();
/*      */ 
/*  770 */       if (timer != null)
/*  771 */         timer.logDebug("remove", this.mDetails.getPK());
/*      */     }
/*      */   }
/*      */ 
/*      */   public AllDataTypesELO getAllDataTypes()
/*      */   {
/*  807 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*  808 */     PreparedStatement stmt = null;
/*  809 */     ResultSet resultSet = null;
/*  810 */     AllDataTypesELO results = new AllDataTypesELO();
/*      */     try
/*      */     {
/*  813 */       stmt = getConnection().prepareStatement(SQL_ALL_DATA_TYPES);
/*  814 */       int col = 1;
/*  815 */       resultSet = stmt.executeQuery();
/*  816 */       while (resultSet.next())
/*      */       {
/*  818 */         col = 2;
/*      */ 
/*  821 */         DataTypePK pkDataType = new DataTypePK(resultSet.getShort(col++));
/*      */ 
/*  824 */         String textDataType = resultSet.getString(col++);
/*  825 */         String erDataTypeDescription = resultSet.getString(col++);
/*  826 */         int erDataTypeSubType = resultSet.getInt(col++);
/*  827 */         Integer erDataTypeMeasureClass = Integer.valueOf(resultSet.getInt(col++));
/*  828 */         Integer erDataTypeMeasureLength = Integer.valueOf(resultSet.getInt(col++));
/*      */ 		   Integer erDataTypeMeasureScale = Integer.valueOf(resultSet.getInt(col++));
/*  832 */         DataTypeRefImpl erDataType = new DataTypeRefImpl(pkDataType, textDataType, erDataTypeDescription, erDataTypeSubType, erDataTypeMeasureClass, erDataTypeMeasureLength, erDataTypeMeasureScale);
/*      */ 
/*  841 */         String col1 = resultSet.getString(col++);
/*  842 */         int col2 = resultSet.getInt(col++);
/*  843 */         Integer col3 = getWrappedIntegerFromJdbc(resultSet, col++);
/*      */ 
/*  846 */         results.add(erDataType, col1, col2, col3);
/*      */       }
/*      */ 
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  856 */       throw handleSQLException(SQL_ALL_DATA_TYPES, sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  860 */       closeResultSet(resultSet);
/*  861 */       closeStatement(stmt);
/*  862 */       closeConnection();
/*      */     }
/*      */ 
/*  865 */     if (timer != null) {
/*  866 */       timer.logDebug("getAllDataTypes", " items=" + results.size());
/*      */     }
/*      */ 
/*  870 */     return results;
/*      */   }
/*      */ 
/*      */   public AllDataTypesWebELO getAllDataTypesWeb()
/*      */   {
/*  905 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*  906 */     PreparedStatement stmt = null;
/*  907 */     ResultSet resultSet = null;
/*  908 */     AllDataTypesWebELO results = new AllDataTypesWebELO();
/*      */     try
/*      */     {
/*  911 */       stmt = getConnection().prepareStatement(SQL_ALL_DATA_TYPES_WEB);
/*  912 */       int col = 1;
/*  913 */       resultSet = stmt.executeQuery();
/*  914 */       while (resultSet.next())
/*      */       {
/*  916 */         col = 2;
/*      */ 
/*  919 */         DataTypePK pkDataType = new DataTypePK(resultSet.getShort(col++));
/*      */ 
/*  922 */         String textDataType = resultSet.getString(col++);
/*  923 */         String erDataTypeDescription = resultSet.getString(col++);
/*  924 */         int erDataTypeSubType = resultSet.getInt(col++);
/*  925 */         Integer erDataTypeMeasureClass = Integer.valueOf(resultSet.getInt(col++));
/*  926 */         Integer erDataTypeMeasureLength = Integer.valueOf(resultSet.getInt(col++));
/*      */ 
/*  930 */         DataTypeRefImpl erDataType = new DataTypeRefImpl(pkDataType, textDataType, erDataTypeDescription, erDataTypeSubType, erDataTypeMeasureClass, erDataTypeMeasureLength);
/*      */ 
/*  939 */         short col1 = resultSet.getShort(col++);
/*  940 */         String col2 = resultSet.getString(col++);
/*  941 */         String col3 = resultSet.getString(col++);
/*      */ 
/*  944 */         results.add(erDataType, col1, col2, col3);
/*      */       }
/*      */ 
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  954 */       throw handleSQLException(SQL_ALL_DATA_TYPES_WEB, sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  958 */       closeResultSet(resultSet);
/*  959 */       closeStatement(stmt);
/*  960 */       closeConnection();
/*      */     }
/*      */ 
/*  963 */     if (timer != null) {
/*  964 */       timer.logDebug("getAllDataTypesWeb", " items=" + results.size());
/*      */     }
/*      */ 
/*  968 */     return results;
/*      */   }
/*      */ 
/*      */   public AllDataTypeForFinanceCubeELO getAllDataTypeForFinanceCube(int param1)
/*      */   {
/* 1011 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/* 1012 */     PreparedStatement stmt = null;
/* 1013 */     ResultSet resultSet = null;
/* 1014 */     AllDataTypeForFinanceCubeELO results = new AllDataTypeForFinanceCubeELO();
/*      */     try
/*      */     {
/* 1017 */       stmt = getConnection().prepareStatement(SQL_ALL_DATA_TYPE_FOR_FINANCE_CUBE);
/* 1018 */       int col = 1;
/* 1019 */       stmt.setInt(col++, param1);
/* 1020 */       resultSet = stmt.executeQuery();
/* 1021 */       while (resultSet.next())
/*      */       {
/* 1023 */         col = 2;
/*      */ 
/* 1026 */         DataTypePK pkDataType = new DataTypePK(resultSet.getShort(col++));
/*      */ 
/* 1029 */         String textDataType = resultSet.getString(col++);
/* 1030 */         String erDataTypeDescription = resultSet.getString(col++);
/* 1031 */         int erDataTypeSubType = resultSet.getInt(col++);
/* 1032 */         Integer erDataTypeMeasureClass = Integer.valueOf(resultSet.getInt(col++));
/* 1033 */         Integer erDataTypeMeasureLength = Integer.valueOf(resultSet.getInt(col++));
/*      */ 
/* 1036 */         FinanceCubeDataTypePK pkFinanceCubeDataType = new FinanceCubeDataTypePK(resultSet.getInt(col++), resultSet.getShort(col++));
/*      */ 
/* 1040 */         String textFinanceCubeDataType = "";
/*      */ 
/* 1043 */         DataTypeRefImpl erDataType = new DataTypeRefImpl(pkDataType, textDataType, erDataTypeDescription, erDataTypeSubType, erDataTypeMeasureClass, erDataTypeMeasureLength);
/*      */ 
/* 1053 */         FinanceCubeDataTypeRefImpl erFinanceCubeDataType = new FinanceCubeDataTypeRefImpl(pkFinanceCubeDataType, textFinanceCubeDataType);
/*      */ 
/* 1058 */         String col1 = resultSet.getString(col++);
/* 1059 */         if (resultSet.wasNull())
/* 1060 */           col1 = "";
/* 1061 */         int col2 = resultSet.getInt(col++);
/* 1062 */         Integer col3 = getWrappedIntegerFromJdbc(resultSet, col++);
/* 1063 */         Integer col4 = getWrappedIntegerFromJdbc(resultSet, col++);
/* 1064 */         Integer col5 = getWrappedIntegerFromJdbc(resultSet, col++);
/*      */ 
/* 1067 */         results.add(erDataType, erFinanceCubeDataType, col1.equals("Y"), col2, col3, col4, col5);
/*      */       }
/*      */ 
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/* 1080 */       throw handleSQLException(SQL_ALL_DATA_TYPE_FOR_FINANCE_CUBE, sqle);
/*      */     }
/*      */     finally
/*      */     {
/* 1084 */       closeResultSet(resultSet);
/* 1085 */       closeStatement(stmt);
/* 1086 */       closeConnection();
/*      */     }
/*      */ 
/* 1089 */     if (timer != null) {
/* 1090 */       timer.logDebug("getAllDataTypeForFinanceCube", " FinanceCubeId=" + param1 + " items=" + results.size());
/*      */     }
/*      */ 
/* 1095 */     return results;
/*      */   }
/*      */ 
/*      */   public AllDataTypesForModelELO getAllDataTypesForModel(int param1)
/*      */   {
/* 1140 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/* 1141 */     PreparedStatement stmt = null;
/* 1142 */     ResultSet resultSet = null;
/* 1143 */     AllDataTypesForModelELO results = new AllDataTypesForModelELO();
/*      */     try
/*      */     {
/* 1146 */       stmt = getConnection().prepareStatement(SQL_ALL_DATA_TYPES_FOR_MODEL);
/* 1147 */       int col = 1;
/* 1148 */       stmt.setInt(col++, param1);
/* 1149 */       resultSet = stmt.executeQuery();
/* 1150 */       while (resultSet.next())
/*      */       {
/* 1152 */         col = 2;
/*      */ 
/* 1155 */         DataTypePK pkDataType = new DataTypePK(resultSet.getShort(col++));
/*      */ 
/* 1158 */         String textDataType = resultSet.getString(col++);
/* 1159 */         String erDataTypeDescription = resultSet.getString(col++);
/* 1160 */         int erDataTypeSubType = resultSet.getInt(col++);
/* 1161 */         Integer erDataTypeMeasureClass = Integer.valueOf(resultSet.getInt(col++));
/* 1162 */         Integer erDataTypeMeasureLength = Integer.valueOf(resultSet.getInt(col++));
/*      */ 
/* 1165 */         FinanceCubePK pkFinanceCube = new FinanceCubePK(resultSet.getInt(col++));
/*      */ 
/* 1168 */         String textFinanceCube = resultSet.getString(col++);
/*      */ 
/* 1170 */         FinanceCubeDataTypePK pkFinanceCubeDataType = new FinanceCubeDataTypePK(resultSet.getInt(col++), resultSet.getShort(col++));
/*      */ 
/* 1174 */         String textFinanceCubeDataType = "";
/*      */ 
/* 1177 */         DataTypeRefImpl erDataType = new DataTypeRefImpl(pkDataType, textDataType, erDataTypeDescription, erDataTypeSubType, erDataTypeMeasureClass, erDataTypeMeasureLength);
/*      */ 
/* 1187 */         FinanceCubeRefImpl erFinanceCube = new FinanceCubeRefImpl(pkFinanceCube, textFinanceCube);
/*      */ 
/* 1193 */         FinanceCubeDataTypeRefImpl erFinanceCubeDataType = new FinanceCubeDataTypeRefImpl(pkFinanceCubeDataType, textFinanceCubeDataType);
/*      */ 
/* 1198 */         int col1 = resultSet.getInt(col++);
/* 1199 */         Integer col2 = getWrappedIntegerFromJdbc(resultSet, col++);
/*      */ 
/* 1202 */         results.add(erDataType, erFinanceCube, erFinanceCubeDataType, col1, col2);
/*      */       }
/*      */ 
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/* 1213 */       throw handleSQLException(SQL_ALL_DATA_TYPES_FOR_MODEL, sqle);
/*      */     }
/*      */     finally
/*      */     {
/* 1217 */       closeResultSet(resultSet);
/* 1218 */       closeStatement(stmt);
/* 1219 */       closeConnection();
/*      */     }
/*      */ 
/* 1222 */     if (timer != null) {
/* 1223 */       timer.logDebug("getAllDataTypesForModel", " ModelId=" + param1 + " items=" + results.size());
/*      */     }
/*      */ 
/* 1228 */     return results;
/*      */   }
/*      */ 
/*      */   public DataTypesByTypeELO getDataTypesByType(int param1)
/*      */   {
/* 1266 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/* 1267 */     PreparedStatement stmt = null;
/* 1268 */     ResultSet resultSet = null;
/* 1269 */     DataTypesByTypeELO results = new DataTypesByTypeELO();
/*      */     try
/*      */     {
/* 1272 */       stmt = getConnection().prepareStatement(SQL_DATA_TYPES_BY_TYPE);
/* 1273 */       int col = 1;
/* 1274 */       stmt.setInt(col++, param1);
/* 1275 */       resultSet = stmt.executeQuery();
/* 1276 */       while (resultSet.next())
/*      */       {
/* 1278 */         col = 2;
/*      */ 
/* 1281 */         DataTypePK pkDataType = new DataTypePK(resultSet.getShort(col++));
/*      */ 
/* 1284 */         String textDataType = resultSet.getString(col++);
/* 1285 */         String erDataTypeDescription = resultSet.getString(col++);
/* 1286 */         int erDataTypeSubType = resultSet.getInt(col++);
/* 1287 */         Integer erDataTypeMeasureClass = Integer.valueOf(resultSet.getInt(col++));
/* 1288 */         Integer erDataTypeMeasureLength = Integer.valueOf(resultSet.getInt(col++));
/*      */ 
/* 1292 */         DataTypeRefImpl erDataType = new DataTypeRefImpl(pkDataType, textDataType, erDataTypeDescription, erDataTypeSubType, erDataTypeMeasureClass, erDataTypeMeasureLength);
/*      */ 
/* 1301 */         String col1 = resultSet.getString(col++);
/* 1302 */         int col2 = resultSet.getInt(col++);
/* 1303 */         short col3 = resultSet.getShort(col++);
/* 1304 */         String col4 = resultSet.getString(col++);
/*      */ 
/* 1307 */         results.add(erDataType, col1, col2, col3, col4);
/*      */       }
/*      */ 
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/* 1318 */       throw handleSQLException(SQL_DATA_TYPES_BY_TYPE, sqle);
/*      */     }
/*      */     finally
/*      */     {
/* 1322 */       closeResultSet(resultSet);
/* 1323 */       closeStatement(stmt);
/* 1324 */       closeConnection();
/*      */     }
/*      */ 
/* 1327 */     if (timer != null) {
/* 1328 */       timer.logDebug("getDataTypesByType", " SubType=" + param1 + " items=" + results.size());
/*      */     }
/*      */ 
/* 1333 */     return results;
/*      */   }
/*      */ 
/*      */   public DataTypesByTypeWriteableELO getDataTypesByTypeWriteable(int param1)
/*      */   {
/* 1371 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/* 1372 */     PreparedStatement stmt = null;
/* 1373 */     ResultSet resultSet = null;
/* 1374 */     DataTypesByTypeWriteableELO results = new DataTypesByTypeWriteableELO();
/*      */     try
/*      */     {
/* 1377 */       stmt = getConnection().prepareStatement(SQL_DATA_TYPES_BY_TYPE_WRITEABLE);
/* 1378 */       int col = 1;
/* 1379 */       stmt.setInt(col++, param1);
/* 1380 */       resultSet = stmt.executeQuery();
/* 1381 */       while (resultSet.next())
/*      */       {
/* 1383 */         col = 2;
/*      */ 
/* 1386 */         DataTypePK pkDataType = new DataTypePK(resultSet.getShort(col++));
/*      */ 
/* 1389 */         String textDataType = resultSet.getString(col++);
/* 1390 */         String erDataTypeDescription = resultSet.getString(col++);
/* 1391 */         int erDataTypeSubType = resultSet.getInt(col++);
/* 1392 */         Integer erDataTypeMeasureClass = Integer.valueOf(resultSet.getInt(col++));
/* 1393 */         Integer erDataTypeMeasureLength = Integer.valueOf(resultSet.getInt(col++));
/*      */ 
/* 1397 */         DataTypeRefImpl erDataType = new DataTypeRefImpl(pkDataType, textDataType, erDataTypeDescription, erDataTypeSubType, erDataTypeMeasureClass, erDataTypeMeasureLength);
/*      */ 
/* 1406 */         String col1 = resultSet.getString(col++);
/* 1407 */         int col2 = resultSet.getInt(col++);
/* 1408 */         short col3 = resultSet.getShort(col++);
/* 1409 */         String col4 = resultSet.getString(col++);
/*      */ 
/* 1412 */         results.add(erDataType, col1, col2, col3, col4);
/*      */       }
/*      */ 
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/* 1423 */       throw handleSQLException(SQL_DATA_TYPES_BY_TYPE_WRITEABLE, sqle);
/*      */     }
/*      */     finally
/*      */     {
/* 1427 */       closeResultSet(resultSet);
/* 1428 */       closeStatement(stmt);
/* 1429 */       closeConnection();
/*      */     }
/*      */ 
/* 1432 */     if (timer != null) {
/* 1433 */       timer.logDebug("getDataTypesByTypeWriteable", " SubType=" + param1 + " items=" + results.size());
/*      */     }
/*      */ 
/* 1438 */     return results;
/*      */   }
/*      */ 
/*      */   public DataTypeDependenciesELO getDataTypeDependencies(short param1)
/*      */   {
/* 1479 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/* 1480 */     PreparedStatement stmt = null;
/* 1481 */     ResultSet resultSet = null;
/* 1482 */     DataTypeDependenciesELO results = new DataTypeDependenciesELO();
/*      */     try
/*      */     {
/* 1485 */       stmt = getConnection().prepareStatement(SQL_DATA_TYPE_DEPENDENCIES);
/* 1486 */       int col = 1;
/* 1487 */       stmt.setShort(col++, param1);
/* 1488 */       resultSet = stmt.executeQuery();
/* 1489 */       while (resultSet.next())
/*      */       {
/* 1491 */         col = 2;
/*      */ 
/* 1494 */         DataTypePK pkDataType = new DataTypePK(resultSet.getShort(col++));
/*      */ 
/* 1497 */         String textDataType = resultSet.getString(col++);
/* 1498 */         String erDataTypeDescription = resultSet.getString(col++);
/* 1499 */         int erDataTypeSubType = resultSet.getInt(col++);
/* 1500 */         Integer erDataTypeMeasureClass = Integer.valueOf(resultSet.getInt(col++));
/* 1501 */         Integer erDataTypeMeasureLength = Integer.valueOf(resultSet.getInt(col++));
/*      */ 
/* 1504 */         DataTypeRelPK pkDataTypeRel = new DataTypeRelPK(resultSet.getShort(col++), resultSet.getShort(col++));
/*      */ 
/* 1508 */         String textDataTypeRel = "";
/*      */ 
/* 1511 */         DataTypeRefImpl erDataType = new DataTypeRefImpl(pkDataType, textDataType, erDataTypeDescription, erDataTypeSubType, erDataTypeMeasureClass, erDataTypeMeasureLength);
/*      */ 
/* 1521 */         DataTypeRelRefImpl erDataTypeRel = new DataTypeRelRefImpl(pkDataTypeRel, textDataTypeRel);
/*      */ 
/* 1526 */         short col1 = resultSet.getShort(col++);
/* 1527 */         String col2 = resultSet.getString(col++);
/* 1528 */         String col3 = resultSet.getString(col++);
/*      */ 
/* 1531 */         results.add(erDataType, erDataTypeRel, col1, col2, col3);
/*      */       }
/*      */ 
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/* 1542 */       throw handleSQLException(SQL_DATA_TYPE_DEPENDENCIES, sqle);
/*      */     }
/*      */     finally
/*      */     {
/* 1546 */       closeResultSet(resultSet);
/* 1547 */       closeStatement(stmt);
/* 1548 */       closeConnection();
/*      */     }
/*      */ 
/* 1551 */     if (timer != null) {
/* 1552 */       timer.logDebug("getDataTypeDependencies", " DataTypeId=" + param1 + " items=" + results.size());
/*      */     }
/*      */ 
/* 1557 */     return results;
/*      */   }
/*      */ 
/*      */   public DataTypesForImpExpELO getDataTypesForImpExp()
/*      */   {
/* 1597 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/* 1598 */     PreparedStatement stmt = null;
/* 1599 */     ResultSet resultSet = null;
/* 1600 */     DataTypesForImpExpELO results = new DataTypesForImpExpELO();
/*      */     try
/*      */     {
/* 1603 */       stmt = getConnection().prepareStatement(SQL_DATA_TYPES_FOR_IMP_EXP);
/* 1604 */       int col = 1;
/* 1605 */       resultSet = stmt.executeQuery();
/* 1606 */       while (resultSet.next())
/*      */       {
/* 1608 */         col = 2;
/*      */ 
/* 1611 */         DataTypePK pkDataType = new DataTypePK(resultSet.getShort(col++));
/*      */ 
/* 1614 */         String textDataType = resultSet.getString(col++);
/* 1615 */         String erDataTypeDescription = resultSet.getString(col++);
/* 1616 */         int erDataTypeSubType = resultSet.getInt(col++);
/* 1617 */         Integer erDataTypeMeasureClass = Integer.valueOf(resultSet.getInt(col++));
/* 1618 */         Integer erDataTypeMeasureLength = Integer.valueOf(resultSet.getInt(col++));
/*      */ 
/* 1622 */         DataTypeRefImpl erDataType = new DataTypeRefImpl(pkDataType, textDataType, erDataTypeDescription, erDataTypeSubType, erDataTypeMeasureClass, erDataTypeMeasureLength);
/*      */ 
/* 1631 */         short col1 = resultSet.getShort(col++);
/* 1632 */         String col2 = resultSet.getString(col++);
/* 1633 */         String col3 = resultSet.getString(col++);
/* 1634 */         String col4 = resultSet.getString(col++);
/* 1635 */         if (resultSet.wasNull())
/* 1636 */           col4 = "";
/* 1637 */         String col5 = resultSet.getString(col++);
/* 1638 */         if (resultSet.wasNull())
/* 1639 */           col5 = "";
/* 1640 */         String col6 = resultSet.getString(col++);
/* 1641 */         if (resultSet.wasNull())
/* 1642 */           col6 = "";
/* 1643 */         int col7 = resultSet.getInt(col++);
/* 1644 */         String col8 = resultSet.getString(col++);
/*      */ 
/* 1647 */         results.add(erDataType, col1, col2, col3, col4.equals("Y"), col5.equals("Y"), col6.equals("Y"), col7, col8);
/*      */       }
/*      */ 
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/* 1662 */       throw handleSQLException(SQL_DATA_TYPES_FOR_IMP_EXP, sqle);
/*      */     }
/*      */     finally
/*      */     {
/* 1666 */       closeResultSet(resultSet);
/* 1667 */       closeStatement(stmt);
/* 1668 */       closeConnection();
/*      */     }
/*      */ 
/* 1671 */     if (timer != null) {
/* 1672 */       timer.logDebug("getDataTypesForImpExp", " items=" + results.size());
/*      */     }
/*      */ 
/* 1676 */     return results;
/*      */   }
/*      */ 
/*      */   public DataTypeDetailsForVisIDELO getDataTypeDetailsForVisID(String param1)
/*      */   {
/* 1713 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/* 1714 */     PreparedStatement stmt = null;
/* 1715 */     ResultSet resultSet = null;
/* 1716 */     DataTypeDetailsForVisIDELO results = new DataTypeDetailsForVisIDELO();
/*      */     try
/*      */     {
/* 1719 */       stmt = getConnection().prepareStatement(SQL_DATA_TYPE_DETAILS_FOR_VIS_I_D);
/* 1720 */       int col = 1;
/* 1721 */       stmt.setString(col++, param1);
/* 1722 */       resultSet = stmt.executeQuery();
/* 1723 */       while (resultSet.next())
/*      */       {
/* 1725 */         col = 2;
/*      */ 
/* 1728 */         DataTypePK pkDataType = new DataTypePK(resultSet.getShort(col++));
/*      */ 
/* 1731 */         String textDataType = resultSet.getString(col++);
/* 1732 */         String erDataTypeDescription = resultSet.getString(col++);
/* 1733 */         int erDataTypeSubType = resultSet.getInt(col++);
/* 1734 */         Integer erDataTypeMeasureClass = Integer.valueOf(resultSet.getInt(col++));
/* 1735 */         Integer erDataTypeMeasureLength = Integer.valueOf(resultSet.getInt(col++));
/*      */ 
/* 1739 */         DataTypeRefImpl erDataType = new DataTypeRefImpl(pkDataType, textDataType, erDataTypeDescription, erDataTypeSubType, erDataTypeMeasureClass, erDataTypeMeasureLength);
/*      */ 
/* 1748 */         short col1 = resultSet.getShort(col++);
/* 1749 */         String col2 = resultSet.getString(col++);
/* 1750 */         String col3 = resultSet.getString(col++);
/*      */ 
/* 1753 */         results.add(erDataType, col1, col2, col3);
/*      */       }
/*      */ 
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/* 1763 */       throw handleSQLException(SQL_DATA_TYPE_DETAILS_FOR_VIS_I_D, sqle);
/*      */     }
/*      */     finally
/*      */     {
/* 1767 */       closeResultSet(resultSet);
/* 1768 */       closeStatement(stmt);
/* 1769 */       closeConnection();
/*      */     }
/*      */ 
/* 1772 */     if (timer != null) {
/* 1773 */       timer.logDebug("getDataTypeDetailsForVisID", " VisId=" + param1 + " items=" + results.size());
/*      */     }
/*      */ 
/* 1778 */     return results;
/*      */   }
/*      */ 
/*      */   private void deleteDependants(DataTypePK pk)
/*      */   {
/* 1805 */     Set emptyStrings = Collections.emptySet();
/* 1806 */     deleteDependants(pk, emptyStrings);
/*      */   }
/*      */ 
/*      */   private void deleteDependants(DataTypePK pk, Set<String> exclusionTables)
/*      */   {
/* 1812 */     for (int i = SQL_DELETE_CHILDRENS_DEPENDANTS.length - 1; i > -1; i--)
/*      */     {
/* 1814 */       if (exclusionTables.contains(SQL_DELETE_CHILDRENS_DEPENDANTS[i][0]))
/*      */         continue;
/* 1816 */       Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*      */ 
/* 1818 */       PreparedStatement stmt = null;
/*      */ 
/* 1820 */       int resultCount = 0;
/* 1821 */       String s = null;
/*      */       try
/*      */       {
/* 1824 */         s = SQL_DELETE_CHILDRENS_DEPENDANTS[i][1] + SQL_DELETE_DEPENDANT_CRITERIA;
/*      */ 
/* 1826 */         if (this._log.isDebugEnabled()) {
/* 1827 */           this._log.debug("deleteDependants", s);
/*      */         }
/* 1829 */         stmt = getConnection().prepareStatement(s);
/*      */ 
/* 1832 */         int col = 1;
/* 1833 */         stmt.setShort(col++, pk.getDataTypeId());
/*      */ 
/* 1836 */         resultCount = stmt.executeUpdate();
/*      */       }
/*      */       catch (SQLException sqle)
/*      */       {
/* 1840 */         throw handleSQLException(pk, s, sqle);
/*      */       }
/*      */       finally
/*      */       {
/* 1844 */         closeStatement(stmt);
/* 1845 */         closeConnection();
/*      */ 
/* 1847 */         if (timer != null) {
/* 1848 */           timer.logDebug("deleteDependants", "A[" + i + "] count=" + resultCount);
/*      */         }
/*      */       }
/*      */     }
/* 1852 */     for (int i = SQL_DELETE_CHILDREN.length - 1; i > -1; i--)
/*      */     {
/* 1854 */       if (exclusionTables.contains(SQL_DELETE_CHILDREN[i][0]))
/*      */         continue;
/* 1856 */       Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*      */ 
/* 1858 */       PreparedStatement stmt = null;
/*      */ 
/* 1860 */       int resultCount = 0;
/* 1861 */       String s = null;
/*      */       try
/*      */       {
/* 1864 */         s = SQL_DELETE_CHILDREN[i][1];
/*      */ 
/* 1866 */         if (this._log.isDebugEnabled()) {
/* 1867 */           this._log.debug("deleteDependants", s);
/*      */         }
/* 1869 */         stmt = getConnection().prepareStatement(s);
/*      */ 
/* 1872 */         int col = 1;
/* 1873 */         stmt.setShort(col++, pk.getDataTypeId());
/*      */ 
/* 1876 */         resultCount = stmt.executeUpdate();
/*      */       }
/*      */       catch (SQLException sqle)
/*      */       {
/* 1880 */         throw handleSQLException(pk, s, sqle);
/*      */       }
/*      */       finally
/*      */       {
/* 1884 */         closeStatement(stmt);
/* 1885 */         closeConnection();
/*      */ 
/* 1887 */         if (timer != null)
/* 1888 */           timer.logDebug("deleteDependants", "B[" + i + "] count=" + resultCount);
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   public DataTypeEVO getDetails(DataTypePK pk, String dependants)
/*      */     throws ValidationException
/*      */   {
/* 1907 */     return getDetails(new DataTypeCK(pk), dependants);
/*      */   }
/*      */ 
/*      */   public DataTypeEVO getDetails(DataTypeCK paramCK, String dependants)
/*      */     throws ValidationException
/*      */   {
/* 1924 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*      */ 
/* 1927 */     if (this.mDetails == null) {
/* 1928 */       doLoad(paramCK.getDataTypePK());
/*      */     }
/* 1930 */     else if (!this.mDetails.getPK().equals(paramCK.getDataTypePK())) {
/* 1931 */       doLoad(paramCK.getDataTypePK());
/*      */     }
/* 1933 */     else if (!checkIfValid())
/*      */     {
/* 1935 */       this._log.info("getDetails", "[ALERT] DataTypeEVO " + this.mDetails.getPK() + " no longer valid - reloading");
/*      */ 
/* 1937 */       doLoad(paramCK.getDataTypePK());
/*      */     }
/*      */ 
/* 1947 */     if ((dependants.indexOf("<0>") > -1) && (!this.mDetails.isDataTypeDependenciesAllItemsLoaded()))
/*      */     {
/* 1952 */       this.mDetails.setDataTypeDependencies(getDataTypeRelDAO().getAll(this.mDetails.getDataTypeId(), dependants, this.mDetails.getDataTypeDependencies()));
/*      */ 
/* 1959 */       this.mDetails.setDataTypeDependenciesAllItemsLoaded(true);
/*      */     }
/*      */ 
/* 1962 */     if ((paramCK instanceof DataTypeRelCK))
/*      */     {
/* 1964 */       if (this.mDetails.getDataTypeDependencies() == null) {
/* 1965 */         this.mDetails.loadDataTypeDependenciesItem(getDataTypeRelDAO().getDetails(paramCK, dependants));
/*      */       }
/*      */       else {
/* 1968 */         DataTypeRelPK pk = ((DataTypeRelCK)paramCK).getDataTypeRelPK();
/* 1969 */         DataTypeRelEVO evo = this.mDetails.getDataTypeDependenciesItem(pk);
/* 1970 */         if (evo == null) {
/* 1971 */           this.mDetails.loadDataTypeDependenciesItem(getDataTypeRelDAO().getDetails(paramCK, dependants));
/*      */         }
/*      */       }
/*      */     }
/*      */ 
/* 1976 */     DataTypeEVO details = new DataTypeEVO();
/* 1977 */     details = this.mDetails.deepClone();
/*      */ 
/* 1979 */     if (timer != null) {
/* 1980 */       timer.logDebug("getDetails", paramCK + " " + dependants);
/*      */     }
/* 1982 */     return details;
/*      */   }
/*      */ 
/*      */   private boolean checkIfValid()
/*      */   {
/* 1992 */     boolean stillValid = false;
/* 1993 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/* 1994 */     PreparedStatement stmt = null;
/* 1995 */     ResultSet resultSet = null;
/*      */     try
/*      */     {
/* 1998 */       stmt = getConnection().prepareStatement("select VERSION_NUM from DATA_TYPE where   DATA_TYPE_ID = ?");
/* 1999 */       int col = 1;
/* 2000 */       stmt.setShort(col++, this.mDetails.getDataTypeId());
/*      */ 
/* 2002 */       resultSet = stmt.executeQuery();
/*      */ 
/* 2004 */       if (!resultSet.next()) {
/* 2005 */         throw new RuntimeException(getEntityName() + " checkIfValid " + this.mDetails.getPK() + " not found");
/*      */       }
/* 2007 */       col = 1;
/* 2008 */       int dbVersionNum = resultSet.getInt(col++);
/*      */ 
/* 2010 */       if (dbVersionNum == this.mDetails.getVersionNum())
/* 2011 */         stillValid = true;
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/* 2015 */       throw handleSQLException(this.mDetails.getPK(), "select VERSION_NUM from DATA_TYPE where   DATA_TYPE_ID = ?", sqle);
/*      */     }
/*      */     finally
/*      */     {
/* 2019 */       closeResultSet(resultSet);
/* 2020 */       closeStatement(stmt);
/* 2021 */       closeConnection();
/*      */ 
/* 2023 */       if (timer != null) {
/* 2024 */         timer.logDebug("checkIfValid", this.mDetails.getPK());
/*      */       }
/*      */     }
/* 2027 */     return stillValid;
/*      */   }
/*      */ 
/*      */   public DataTypeEVO getDetails(String dependants)
/*      */     throws ValidationException
/*      */   {
/* 2033 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*      */ 
/* 2035 */     if (!checkIfValid())
/*      */     {
/* 2037 */       this._log.info("getDetails", "DataType " + this.mDetails.getPK() + " no longer valid - reloading");
/* 2038 */       doLoad(this.mDetails.getPK());
/*      */     }
/*      */ 
/* 2042 */     getDependants(this.mDetails, dependants);
/*      */ 
/* 2045 */     DataTypeEVO details = this.mDetails.deepClone();
/*      */ 
/* 2047 */     if (timer != null) {
/* 2048 */       timer.logDebug("getDetails", this.mDetails.getPK() + " " + dependants);
/*      */     }
/* 2050 */     return details;
/*      */   }
/*      */ 
/*      */   protected DataTypeRelDAO getDataTypeRelDAO()
/*      */   {
/* 2059 */     if (this.mDataTypeRelDAO == null)
/*      */     {
/* 2061 */       if (this.mDataSource != null)
/* 2062 */         this.mDataTypeRelDAO = new DataTypeRelDAO(this.mDataSource);
/*      */       else {
/* 2064 */         this.mDataTypeRelDAO = new DataTypeRelDAO(getConnection());
/*      */       }
/*      */     }
/* 2067 */     return this.mDataTypeRelDAO;
/*      */   }
/*      */ 
/*      */   public String getEntityName()
/*      */   {
/* 2072 */     return "DataType";
/*      */   }
/*      */ 
/*      */   public DataTypeRef getRef(DataTypePK paramDataTypePK)
/*      */     throws ValidationException
/*      */   {
/* 2078 */     DataTypeEVO evo = getDetails(paramDataTypePK, "");
/* 2079 */     return evo.getEntityRef();
/*      */   }
/*      */ 
/*      */   public void getDependants(Collection c, String dependants)
/*      */   {
/* 2104 */     if (c == null)
/* 2105 */       return;
/* 2106 */     Iterator iter = c.iterator();
/* 2107 */     while (iter.hasNext())
/*      */     {
/* 2109 */       DataTypeEVO evo = (DataTypeEVO)iter.next();
/* 2110 */       getDependants(evo, dependants);
/*      */     }
/*      */   }
/*      */ 
/*      */   public void getDependants(DataTypeEVO evo, String dependants)
/*      */   {
/* 2124 */     if (evo.getDataTypeId() < 1) {
/* 2125 */       return;
/*      */     }
/*      */ 
/* 2133 */     if (dependants.indexOf("<0>") > -1)
/*      */     {
/* 2136 */       if (!evo.isDataTypeDependenciesAllItemsLoaded())
/*      */       {
/* 2138 */         evo.setDataTypeDependencies(getDataTypeRelDAO().getAll(evo.getDataTypeId(), dependants, evo.getDataTypeDependencies()));
/*      */ 
/* 2145 */         evo.setDataTypeDependenciesAllItemsLoaded(true);
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   public AvailableStructureElementELO getDataTypesForConstraints(String financeCubeVisId, List constraints)
/*      */   {
/* 2156 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/* 2157 */     PreparedStatement stmt = null;
/* 2158 */     ResultSet resultSet = null;
/*      */ 
/* 2160 */     AvailableStructureElementELO results = new AvailableStructureElementELO();
/*      */     try
/*      */     {
/* 2165 */       StringBuffer sql = new StringBuffer();
/* 2166 */       sql.append("select dt.data_type_id, dt.vis_id, dt.description");
/* 2167 */       sql.append("  from data_type dt,");
/* 2168 */       sql.append("       finance_cube_data_type fcdt,");
/* 2169 */       sql.append("       finance_cube fc");
/* 2170 */       sql.append(" where fc.vis_id = ?");
/* 2171 */       sql.append("   and fcdt.finance_cube_id = fc.finance_cube_id");
/* 2172 */       sql.append("   and fcdt.data_type_id = dt.data_type_id and ( 1 = 0 ");
/*      */ 
/* 2175 */       for (Iterator iter = constraints.iterator(); iter.hasNext(); )
/*      */       {
/* 2177 */         Constraint c = (Constraint)iter.next();
/* 2178 */         sql.append(c.getSqlPredicate("dt.vis_id"));
/*      */       }
/*      */ 
/* 2181 */       sql.append(" ) order by dt.vis_id");
/* 2182 */       if (this._log.isDebugEnabled())
/* 2183 */         this._log.debug("getDataTypesForConstraints", sql.toString());
/* 2184 */       stmt = getConnection().prepareStatement(sql.toString());
/*      */ 
/* 2187 */       int index = 1;
/* 2188 */       stmt.setString(index++, financeCubeVisId);
/* 2189 */       if (this._log.isDebugEnabled())
/* 2190 */         this._log.debug("getDataTypesForConstraints", "Bind variable " + financeCubeVisId);
/* 2191 */       for (Iterator iter = constraints.iterator(); iter.hasNext(); )
/*      */       {
/* 2193 */         Constraint c = (Constraint)iter.next();
/* 2194 */         String[] bindVariables = c.getBindVariables();
/* 2195 */         for (int j = 0; j < bindVariables.length; j++)
/*      */         {
/* 2197 */           stmt.setString(index++, bindVariables[j]);
/* 2198 */           if (this._log.isDebugEnabled()) {
/* 2199 */             this._log.debug("getDataTypesForConstraints", "Bind variable " + bindVariables[j]);
/*      */           }
/*      */         }
/*      */       }
/* 2203 */       resultSet = stmt.executeQuery();
/*      */ 
/* 2205 */       while (resultSet.next())
/*      */       {
/* 2207 */         index = 1;
/* 2208 */         short id = resultSet.getShort(index++);
/* 2209 */         results.add(new Integer(1), new DataTypePK(id), new Integer(id), resultSet.getString(index++), resultSet.getString(index++), " ", " ", " ", "Y", new Integer(0));
/*      */       }
/*      */ 
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/* 2225 */       System.err.println(sqle);
/* 2226 */       sqle.printStackTrace();
/* 2227 */       throw new RuntimeException(getEntityName() + " getDataTypesForConstraints", sqle);
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/* 2231 */       e.printStackTrace();
/* 2232 */       throw new RuntimeException(getEntityName() + " getDataTypesForConstraints", e);
/*      */     }
/*      */     finally
/*      */     {
/* 2236 */       closeResultSet(resultSet);
/* 2237 */       closeStatement(stmt);
/* 2238 */       closeConnection();
/*      */     }
/*      */ 
/* 2241 */     if (timer != null) {
/* 2242 */       timer.logDebug("getDataTypesForConstraints", "");
/*      */     }
/* 2244 */     return results;
/*      */   }
/*      */ 
/*      */   public void validateDataTypeDelete(short id)
/*      */     throws ValidationException
/*      */   {
/* 2261 */     PreparedStatement ps = null;
/* 2262 */     ResultSet rs = null;
/*      */     try
/*      */     {
/* 2265 */       ps = getConnection().prepareStatement("select dt.vis_id from data_type dt, data_type_rel dtr where dt.data_type_id = dtr.data_type_id and       dtr.ref_data_type_id = ? ");
/*      */ 
/* 2267 */       ps.setShort(1, id);
/*      */ 
/* 2269 */       rs = ps.executeQuery();
/*      */ 
/* 2271 */       if (rs.next())
/* 2272 */         throw new ValidationException("Unable to delete. This data type is referenced by data type " + rs.getString("vis_id"));
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/* 2276 */       System.err.println(sqle);
/* 2277 */       sqle.printStackTrace();
/* 2278 */       throw new RuntimeException(getEntityName() + " validateDataTypeDelete", sqle);
/*      */     }
/*      */     finally
/*      */     {
/* 2282 */       closeResultSet(rs);
/* 2283 */       closeStatement(ps);
/* 2284 */       closeConnection();
/*      */     }
/*      */   }
/*      */ 
/*      */   private void validateAmendedDataType(int dataTypeId)
/*      */     throws ValidationException
/*      */   {
/* 2313 */     PreparedStatement ps = null;
/* 2314 */     ResultSet rs = null;
/*      */     try
/*      */     {
/* 2317 */       ps = getConnection().prepareStatement(VALIDATE_DATA_TYPE_REFS_SQL);
/* 2318 */       ps.setInt(1, dataTypeId);
/* 2319 */       rs = ps.executeQuery();
/* 2320 */       if (rs.next())
/*      */       {
/* 2322 */         String fcCube = rs.getString("vis_id");
/* 2323 */         String refDataType = rs.getString("ref_data_type_vis_id");
/* 2324 */         String msg = MessageFormat.format("The data type is deployed to finance cube {0} which does not have the dependent data type {1} deployed to it.", new Object[] { fcCube, refDataType });
/*      */ 
/* 2327 */         throw new ValidationException(msg);
/*      */       }
/*      */     }
/*      */     catch (SQLException e)
/*      */     {
/* 2332 */       e.printStackTrace();
/* 2333 */       throw handleSQLException("validateAmendedDataType", e);
/*      */     }
/*      */     finally
/*      */     {
/* 2337 */       closeResultSet(rs);
/* 2338 */       closeStatement(ps);
/* 2339 */       closeConnection();
/*      */     }
/*      */   }
/*      */ 
/*      */   public Map<String, DataTypeEVO> getAllForFinanceCube(int financeCubeId)
/*      */   {
/* 2350 */     Map map = new HashMap();
/* 2351 */     ResultSet rs = null;
/* 2352 */     PreparedStatement stmt = null;
/*      */ 
/* 2354 */     String sql = "select DATA_TYPE.DATA_TYPE_ID,DATA_TYPE.VIS_ID,DATA_TYPE.DESCRIPTION,DATA_TYPE.READ_ONLY_FLAG,DATA_TYPE.AVAILABLE_FOR_IMPORT,DATA_TYPE.AVAILABLE_FOR_EXPORT,DATA_TYPE.SUB_TYPE,DATA_TYPE.FORMULA_EXPR,DATA_TYPE.MEASURE_CLASS,DATA_TYPE.MEASURE_LENGTH,DATA_TYPE.MEASURE_SCALE,DATA_TYPE.MEASURE_VALIDATION,DATA_TYPE.VERSION_NUM,DATA_TYPE.UPDATED_BY_USER_ID,DATA_TYPE.UPDATED_TIME,DATA_TYPE.CREATED_TIME\nfrom   FINANCE_CUBE_DATA_TYPE fcdt\njoin   DATA_TYPE on (DATA_TYPE.DATA_TYPE_ID = fcdt.DATA_TYPE_ID)\nwhere  FINANCE_CUBE_ID = ?";
/*      */     try
/*      */     {
/* 2362 */       stmt = getConnection().prepareStatement(sql);
/* 2363 */       stmt.setInt(1, financeCubeId);
/* 2364 */       rs = stmt.executeQuery();
/* 2365 */       while (rs.next())
/*      */       {
/* 2367 */         DataTypeEVO dtEvo = getEvoFromJdbc(rs);
/* 2368 */         map.put(dtEvo.getVisId(), dtEvo);
/*      */       }
/*      */     }
/*      */     catch (SQLException e)
/*      */     {
/* 2373 */       this._log.debug(sql);
/* 2374 */       e.printStackTrace();
/* 2375 */       throw handleSQLException("getAllForFinanceCube", e);
/*      */     }
/*      */     finally
/*      */     {
/* 2379 */       closeResultSet(rs);
/* 2380 */       closeStatement(stmt);
/* 2381 */       closeConnection();
/*      */     }
/*      */ 
/* 2384 */     return map;
/*      */   }
/*      */ 
/*      */   public Map<String, DataTypeEVO> getEvosAndRollups(int financeCubeId)
/*      */   {
/* 2394 */     Timer timer = new Timer(this._log);
/* 2395 */     Map map = new HashMap();
/* 2396 */     SqlBuilder sqlb = new SqlBuilder(new String[] { "with getRollups_init as", "(", "select  DATA_TYPE_ID", "        ,DIMENSION_SEQ_NUM", "        ,nvl(ROLL_UP,decode(SUB_TYPE,0,'Y',' ')) ROLL_UP", "from    FINANCE_CUBE_DATA_TYPE", "join    DATA_TYPE using (DATA_TYPE_ID)", "join    FINANCE_CUBE using (FINANCE_CUBE_ID)", "join    MODEL_DIMENSION_REL using (MODEL_ID)", "left", "join    ROLL_UP_RULE using (FINANCE_CUBE_ID, DIMENSION_ID, DATA_TYPE_ID)", "where   FINANCE_CUBE_ID = <financeCubeId>", ")", ",getRollups as", "(", "select  DATA_TYPE_ID", "        ,${rollupColumns}", "        as ROLL_UPS", "from    (", "        select  distinct DATA_TYPE_ID", "        from    getRollups_init", "        ) m", ")", "${selectDTColumns}", "       ,ROLL_UPS", "from   DATA_TYPE", "       ,getRollups ru", "where  ru.DATA_TYPE_ID = DATA_TYPE.DATA_TYPE_ID" });
/*      */ 
/* 2427 */     sqlb.substituteRepeatingLines("${rollupColumns}", 10, "||", new String[] { "${separator}", "(select  ROLL_UP from getRollups_init t ", " where   t.DATA_TYPE_ID = m.DATA_TYPE_ID and DIMENSION_SEQ_NUM = ${index})" });
/*      */ 
/* 2433 */     sqlb.substitute(new String[] { "${selectDTColumns}", "select DATA_TYPE.DATA_TYPE_ID,DATA_TYPE.VIS_ID,DATA_TYPE.DESCRIPTION,DATA_TYPE.READ_ONLY_FLAG,DATA_TYPE.AVAILABLE_FOR_IMPORT,DATA_TYPE.AVAILABLE_FOR_EXPORT,DATA_TYPE.SUB_TYPE,DATA_TYPE.FORMULA_EXPR,DATA_TYPE.MEASURE_CLASS,DATA_TYPE.MEASURE_LENGTH,DATA_TYPE.MEASURE_SCALE,DATA_TYPE.MEASURE_VALIDATION,DATA_TYPE.VERSION_NUM,DATA_TYPE.UPDATED_BY_USER_ID,DATA_TYPE.UPDATED_TIME,DATA_TYPE.CREATED_TIME" });
/*      */ 
/* 2435 */     SqlExecutor sqle = new SqlExecutor("getEvosAndRollups", getDataSource(), sqlb, this._log);
/*      */ 
/* 2437 */     sqle.addBindVariable("<financeCubeId>", Integer.valueOf(financeCubeId));
/* 2438 */     ResultSet rs = sqle.getResultSet();
/*      */     try
/*      */     {
/* 2442 */       while (rs.next())
/*      */       {
/* 2444 */         DataTypeEVO dtEvo = getEvoFromJdbc(rs);
/* 2445 */         String rollups = rs.getString("ROLL_UPS");
/* 2446 */         dtEvo.setRollups(rollups);
/* 2447 */         if (this._log.isDebugEnabled())
/* 2448 */           this._log.debug("getEvosAndROllups", "dataType=" + dtEvo.getVisId());
/* 2449 */         map.put(dtEvo.getVisId(), dtEvo);
/*      */       }
/*      */     }
/*      */     catch (SQLException e)
/*      */     {
/* 2454 */       e.printStackTrace();
/* 2455 */       throw handleSQLException("getEvosAndRollups", e);
/*      */     }
/*      */     finally
/*      */     {
/* 2459 */       sqle.close();
/* 2460 */       timer.logInfo("getEvosAndRollups", "rows=" + map.size());
/*      */     }
/*      */ 
/* 2463 */     return map;
/*      */   }
/*      */ 
/*      */   public PickerDataTypesFinCubeELO getPickerDataTypesWeb(int[] subTypes, boolean writeable)
/*      */   {
/* 2469 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*      */ 
/* 2471 */     StringBuffer sb = new StringBuffer();
/* 2472 */     sb.append("select DATA_TYPE.DATA_TYPE_ID ,DATA_TYPE.VIS_ID ,DATA_TYPE.DESCRIPTION ,DATA_TYPE.SUB_TYPE ,DATA_TYPE.MEASURE_CLASS ,DATA_TYPE.MEASURE_LENGTH");
/* 2473 */     sb.append(" ,DATA_TYPE.DATA_TYPE_ID ,DATA_TYPE.VIS_ID ,DATA_TYPE.DESCRIPTION ,DATA_TYPE.SUB_TYPE \n");
/* 2474 */     sb.append("from DATA_TYPE \n");
/* 2475 */     sb.append("where 1=1");
/* 2476 */     if (writeable)
/* 2477 */       sb.append(" and DATA_TYPE.READ_ONLY_FLAG <> 'Y' ");
/* 2478 */     if (subTypes != null)
/*      */     {
/* 2480 */       sb.append(" and DATA_TYPE.SUB_TYPE in ( ");
/* 2481 */       for (int i = 0; i < subTypes.length; i++)
/*      */       {
/* 2483 */         if (i != 0)
/* 2484 */           sb.append(",");
/* 2485 */         sb.append(" ? ");
/*      */       }
/* 2487 */       sb.append(")");
/*      */     }
/* 2489 */     sb.append(" order by DATA_TYPE.SUB_TYPE ,DATA_TYPE.VIS_ID");
/*      */ 
/* 2491 */     PreparedStatement stmt = null;
/* 2492 */     ResultSet resultSet = null;
/* 2493 */     PickerDataTypesFinCubeELO results = new PickerDataTypesFinCubeELO();
/*      */     try
/*      */     {
/* 2496 */       stmt = getConnection().prepareStatement(sb.toString());
/* 2497 */       int col = 1;
/* 2498 */       if (subTypes != null)
/*      */       {
/* 2500 */         for (int id : subTypes)
/* 2501 */           stmt.setInt(col++, id);
/*      */       }
/* 2503 */       resultSet = stmt.executeQuery();
/* 2504 */       while (resultSet.next())
/*      */       {
/* 2506 */         col = 1;
/*      */ 
/* 2508 */         DataTypePK pkDataType = new DataTypePK(resultSet.getShort(col++));
/*      */ 
/* 2511 */         String textDataType = resultSet.getString(col++);
/* 2512 */         String erDataTypeDescription = resultSet.getString(col++);
/* 2513 */         int erDataTypeSubType = resultSet.getInt(col++);
/* 2514 */         Integer erDataTypeMeasureClass = Integer.valueOf(resultSet.getInt(col++));
/* 2515 */         Integer erMesureLenght = Integer.valueOf(resultSet.getInt(col++));
/*      */ 
/* 2519 */         DataTypeRefImpl erDataType = new DataTypeRefImpl(pkDataType, textDataType, erDataTypeDescription, erDataTypeSubType, erDataTypeMeasureClass, erMesureLenght);
/*      */ 
/* 2528 */         short col1 = resultSet.getShort(col++);
/* 2529 */         String col2 = resultSet.getString(col++);
/* 2530 */         String col3 = resultSet.getString(col++);
/* 2531 */         int subtype = resultSet.getInt(col++);
/*      */ 
/* 2534 */         results.add(erDataType, null, col1, col2, col3, subtype);
/*      */       }
/*      */ 
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/* 2546 */       throw handleSQLException(sb.toString(), sqle);
/*      */     }
/*      */     finally
/*      */     {
/* 2550 */       closeResultSet(resultSet);
/* 2551 */       closeStatement(stmt);
/* 2552 */       closeConnection();
/*      */     }
/*      */ 
/* 2555 */     if (timer != null) {
/* 2556 */       timer.logDebug("getPickerDataTypesWeb", " items=" + results.size());
/*      */     }
/*      */ 
/* 2560 */     return results;
/*      */   }
/*      */ 
/*      */   public PickerDataTypesFinCubeELO getPickerDataTypesWeb(int fininceCubeId, int[] subTypes, boolean writeable)
/*      */   {
/* 2565 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*      */ 
/* 2567 */     StringBuffer sb = new StringBuffer();
/* 2568 */     sb.append("select DATA_TYPE.DATA_TYPE_ID ,DATA_TYPE.VIS_ID ,DATA_TYPE.DESCRIPTION ,DATA_TYPE.SUB_TYPE ,DATA_TYPE.MEASURE_CLASS ,DATA_TYPE.MEASURE_LENGTH");
/* 2569 */     sb.append(" ,FINANCE_CUBE_DATA_TYPE.FINANCE_CUBE_ID ,FINANCE_CUBE_DATA_TYPE.DATA_TYPE_ID");
/* 2570 */     sb.append(" ,DATA_TYPE.DATA_TYPE_ID ,DATA_TYPE.VIS_ID ,DATA_TYPE.DESCRIPTION ,DATA_TYPE.SUB_TYPE \n");
/* 2571 */     sb.append("from DATA_TYPE ,FINANCE_CUBE_DATA_TYPE \n");
/* 2572 */     sb.append("where FINANCE_CUBE_DATA_TYPE.FINANCE_CUBE_ID = ? and FINANCE_CUBE_DATA_TYPE.DATA_TYPE_ID = DATA_TYPE.DATA_TYPE_ID");
/* 2573 */     if (writeable)
/* 2574 */       sb.append(" and DATA_TYPE.READ_ONLY_FLAG <> 'Y' ");
/* 2575 */     if (subTypes != null)
/*      */     {
/* 2577 */       sb.append(" and DATA_TYPE.SUB_TYPE in ( ");
/* 2578 */       for (int i = 0; i < subTypes.length; i++)
/*      */       {
/* 2580 */         if (i != 0)
/* 2581 */           sb.append(",");
/* 2582 */         sb.append(" ? ");
/*      */       }
/* 2584 */       sb.append(")");
/*      */     }
/* 2586 */     sb.append(" order by DATA_TYPE.SUB_TYPE ,DATA_TYPE.VIS_ID");
/*      */ 
/* 2588 */     PreparedStatement stmt = null;
/* 2589 */     ResultSet resultSet = null;
/* 2590 */     PickerDataTypesFinCubeELO results = new PickerDataTypesFinCubeELO();
/*      */     try
/*      */     {
/* 2593 */       stmt = getConnection().prepareStatement(sb.toString());
/* 2594 */       int col = 1;
/* 2595 */       stmt.setInt(col++, fininceCubeId);
/* 2596 */       if (subTypes != null)
/*      */       {
/* 2598 */         for (int id : subTypes)
/* 2599 */           stmt.setInt(col++, id);
/*      */       }
/* 2601 */       resultSet = stmt.executeQuery();
/* 2602 */       while (resultSet.next())
/*      */       {
/* 2604 */         col = 1;
/*      */ 
/* 2606 */         DataTypePK pkDataType = new DataTypePK(resultSet.getShort(col++));
/*      */ 
/* 2609 */         String textDataType = resultSet.getString(col++);
/* 2610 */         String erDataTypeDescription = resultSet.getString(col++);
/* 2611 */         int erDataTypeSubType = resultSet.getInt(col++);
/* 2612 */         Integer erDataTypeMeasureClass = Integer.valueOf(resultSet.getInt(col++));
/* 2613 */         Integer erMesureLenght = Integer.valueOf(resultSet.getInt(col++));
/*      */ 
/* 2616 */         FinanceCubeDataTypePK pkFinanceCubeDataType = new FinanceCubeDataTypePK(resultSet.getInt(col++), resultSet.getShort(col++));
/*      */ 
/* 2620 */         String textFinanceCubeDataType = "";
/*      */ 
/* 2623 */         DataTypeRefImpl erDataType = new DataTypeRefImpl(pkDataType, textDataType, erDataTypeDescription, erDataTypeSubType, erDataTypeMeasureClass, erMesureLenght);
/*      */ 
/* 2633 */         FinanceCubeDataTypeRefImpl erFinanceCubeDataType = new FinanceCubeDataTypeRefImpl(pkFinanceCubeDataType, textFinanceCubeDataType);
/*      */ 
/* 2638 */         short col1 = resultSet.getShort(col++);
/* 2639 */         String col2 = resultSet.getString(col++);
/* 2640 */         String col3 = resultSet.getString(col++);
/* 2641 */         int subtype = resultSet.getInt(col++);
/*      */ 
/* 2644 */         results.add(erDataType, erFinanceCubeDataType, col1, col2, col3, subtype);
/*      */       }
/*      */ 
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/* 2656 */       throw handleSQLException(sb.toString(), sqle);
/*      */     }
/*      */     finally
/*      */     {
/* 2660 */       closeResultSet(resultSet);
/* 2661 */       closeStatement(stmt);
/* 2662 */       closeConnection();
/*      */     }
/*      */ 
/* 2665 */     if (timer != null) {
/* 2666 */       timer.logDebug("getPickerDataTypesWeb", " items=" + results.size());
/*      */     }
/*      */ 
/* 2670 */     return results;
/*      */   }
/*      */ 
/*      */   public Map<String, DataTypeEVO> getDataTypeEVOMap(Set<String> dataTypes)
/*      */   {
/* 2681 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*      */ 
/* 2683 */     Map results = new HashMap();
/*      */ 
/* 2685 */     SqlBuilder sqlBuilder = new SqlBuilder(new String[] { "select DATA_TYPE.DATA_TYPE_ID,DATA_TYPE.VIS_ID,DATA_TYPE.DESCRIPTION,DATA_TYPE.READ_ONLY_FLAG,DATA_TYPE.AVAILABLE_FOR_IMPORT,DATA_TYPE.AVAILABLE_FOR_EXPORT,DATA_TYPE.SUB_TYPE,DATA_TYPE.FORMULA_EXPR,DATA_TYPE.MEASURE_CLASS,DATA_TYPE.MEASURE_LENGTH,DATA_TYPE.MEASURE_SCALE,DATA_TYPE.MEASURE_VALIDATION,DATA_TYPE.VERSION_NUM,DATA_TYPE.UPDATED_BY_USER_ID,DATA_TYPE.UPDATED_TIME,DATA_TYPE.CREATED_TIME", "from data_type ", "where vis_id in (${dataTypeBindList})" });
/*      */ 
/* 2692 */     sqlBuilder.substitute(new String[] { "${dataTypeBindList}", SqlBuilder.repeatString("${separator} ${dataType${index}}", ",", dataTypes.size()) });
/*      */ 
/* 2695 */     SqlExecutor sqlExecutor = null;
/* 2696 */     ResultSet resultSet = null;
/*      */     try
/*      */     {
/* 2699 */       sqlExecutor = new SqlExecutor("getDataTypeEVOMap", getDataSource(), sqlBuilder, this._log);
/*      */ 
/* 2701 */       int index = 0;
/* 2702 */       for (String dataType : dataTypes)
/*      */       {
/* 2704 */         sqlExecutor.addBindVariable("${dataType" + index + "}", dataType);
/* 2705 */         index++;
/*      */       }
/*      */ 
/* 2708 */       resultSet = sqlExecutor.getResultSet();
/* 2709 */       while (resultSet.next())
/*      */       {
/* 2711 */         DataTypeEVO dataTypeEVO = getEvoFromJdbc(resultSet);
/* 2712 */         results.put(dataTypeEVO.getVisId(), dataTypeEVO);
/*      */       }
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/* 2717 */       throw handleSQLException(sqlBuilder.toString(), sqle);
/*      */     }
/*      */     finally
/*      */     {
/* 2721 */       if (sqlExecutor != null) {
/* 2722 */         sqlExecutor.close();
/*      */       }
/*      */     }
/* 2725 */     if (timer != null) {
/* 2726 */       timer.logDebug("getDataTypeEVOMap", " items=" + results.size());
/*      */     }
/*      */ 
/* 2730 */     return results;
/*      */   }
/*      */ }

/* Location:           /home/oracle/coa/cp.ear/cp.ear_orginal/cp-server.jar
 * Qualified Name:     com.cedar.cp.ejb.impl.datatype.DataTypeDAO
 * JD-Core Version:    0.6.0
 */