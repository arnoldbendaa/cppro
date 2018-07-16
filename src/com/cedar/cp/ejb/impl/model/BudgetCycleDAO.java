package com.cedar.cp.ejb.impl.model;

import com.cedar.cp.api.base.DuplicateNameValidationException;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.base.VersionValidationException;
import com.cedar.cp.dto.model.AllBudgetCyclesELO;
import com.cedar.cp.dto.model.AllBudgetCyclesWebDetailedELO;
import com.cedar.cp.dto.model.AllBudgetCyclesWebELO;
import com.cedar.cp.dto.model.BudgetCycleCK;
import com.cedar.cp.dto.model.BudgetCycleDetailedForIdELO;
import com.cedar.cp.dto.model.BudgetCycleIntegrityELO;
import com.cedar.cp.dto.model.BudgetCycleLinkCK;
import com.cedar.cp.dto.model.BudgetCycleLinkPK;
import com.cedar.cp.dto.model.BudgetCyclePK;
import com.cedar.cp.dto.model.BudgetCycleRefImpl;
import com.cedar.cp.dto.model.BudgetCyclesForModelELO;
import com.cedar.cp.dto.model.BudgetCyclesForModelWithStateELO;
import com.cedar.cp.dto.model.BudgetStateCK;
import com.cedar.cp.dto.model.BudgetStatePK;
import com.cedar.cp.dto.model.BudgetTransferBudgetCyclesELO;
import com.cedar.cp.dto.model.FinanceCubePK;
import com.cedar.cp.dto.model.FinanceCubeRefImpl;
import com.cedar.cp.dto.model.LevelDateCK;
import com.cedar.cp.dto.model.LevelDatePK;
import com.cedar.cp.dto.model.ModelCK;
import com.cedar.cp.dto.model.ModelPK;
import com.cedar.cp.dto.model.ModelRefImpl;
import com.cedar.cp.dto.xmlform.XmlFormPK;
import com.cedar.cp.dto.xmlform.XmlFormRefImpl;
import com.cedar.cp.ejb.impl.base.AbstractDAO;
import com.cedar.cp.util.Log;
import com.cedar.cp.util.Timer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Set;

import javax.sql.DataSource;

@SuppressWarnings("serial")
public class BudgetCycleDAO extends AbstractDAO {
    Log _log = new Log(getClass());
    // private static final String SQL_SELECT_COLUMNS = "select BUDGET_CYCLE.BUDGET_CYCLE_ID,BUDGET_CYCLE.MODEL_ID,BUDGET_CYCLE.VIS_ID,BUDGET_CYCLE.DESCRIPTION,BUDGET_CYCLE.TYPE,BUDGET_CYCLE.XML_FORM_ID,BUDGET_CYCLE.XML_FORM_DATA_TYPE,BUDGET_CYCLE.PERIOD_ID,BUDGET_CYCLE.PLANNED_END_DATE,BUDGET_CYCLE.START_DATE,BUDGET_CYCLE.END_DATE,BUDGET_CYCLE.STATUS,BUDGET_CYCLE.VERSION_NUM,BUDGET_CYCLE.UPDATED_BY_USER_ID,BUDGET_CYCLE.UPDATED_TIME,BUDGET_CYCLE.CREATED_TIME";
    protected static final String SQL_LOAD = " from BUDGET_CYCLE where    BUDGET_CYCLE_ID = ? ";
    protected static final String SQL_CREATE = "insert into BUDGET_CYCLE ( BUDGET_CYCLE_ID,MODEL_ID,VIS_ID,DESCRIPTION,TYPE,XML_FORM_ID,XML_FORM_DATA_TYPE,PERIOD_ID,PLANNED_END_DATE,START_DATE,END_DATE,STATUS,VERSION_NUM,UPDATED_BY_USER_ID,UPDATED_TIME,CREATED_TIME,CATEGORY) values ( ?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
    protected static final String SQL_DUPLICATE_VALUE_CHECK_BUDGETCYCLENAME = "select count(*) from BUDGET_CYCLE where    MODEL_ID = ? AND VIS_ID = ? and not(    BUDGET_CYCLE_ID = ? )";
    protected static final String SQL_STORE = "update BUDGET_CYCLE set MODEL_ID = ?,VIS_ID = ?,DESCRIPTION = ?,TYPE = ?,XML_FORM_ID = ?,XML_FORM_DATA_TYPE = ?,PERIOD_ID = ?,PLANNED_END_DATE = ?,START_DATE = ?,END_DATE = ?,STATUS = ?,VERSION_NUM = ?,UPDATED_BY_USER_ID = ?,UPDATED_TIME = ?,CREATED_TIME = ?,CATEGORY = ? where    BUDGET_CYCLE_ID = ? AND VERSION_NUM = ?";
    protected static final String SQL_CHECK_VERSION_NUM = "select VERSION_NUM from BUDGET_CYCLE where BUDGET_CYCLE_ID = ?";
    // private static String SQL_SUBSELECT_1 = "(select s1.vis_id||','||s2.vis_id col from structure_element s1 left JOIN structure_element s2 on s1.parent_id = s2.structure_element_id where s1.structure_element_id = BUDGET_CYCLE.PERIOD_ID)";
    // private static String SQL_SUBSELECT_2 = "(select s1.vis_id||','||s2.vis_id col from structure_element s1 left JOIN structure_element s2 on s1.parent_id = s2.structure_element_id where s1.structure_element_id = BUDGET_CYCLE.PERIOD_ID_TO)";

    protected static String SQL_ALL_BUDGET_CYCLES = "select 0       ,MODEL.MODEL_ID      ,MODEL.VIS_ID      ,BUDGET_CYCLE.BUDGET_CYCLE_ID      ,BUDGET_CYCLE.VIS_ID      ,BUDGET_CYCLE.DESCRIPTION      ,BUDGET_CYCLE.STATUS, PERIOD_ID, PERIOD_ID_TO, PERIOD_FROM_VISID, PERIOD_TO_VISID, BUDGET_CYCLE.CATEGORY from BUDGET_CYCLE, MODEL where 1=1   and BUDGET_CYCLE.MODEL_ID = MODEL.MODEL_ID  order by MODEL.VIS_ID, BUDGET_CYCLE.VIS_ID, CATEGORY";
    protected static String SQL_ALL_BUDGET_CYCLES_WEB = "select 0       ,MODEL.MODEL_ID      ,MODEL.VIS_ID      ,BUDGET_CYCLE.BUDGET_CYCLE_ID      ,BUDGET_CYCLE.VIS_ID      ,BUDGET_CYCLE.BUDGET_CYCLE_ID      ,BUDGET_CYCLE.DESCRIPTION from BUDGET_CYCLE    ,MODEL where 1=1   and BUDGET_CYCLE.MODEL_ID = MODEL.MODEL_ID  order by MODEL.VIS_ID, BUDGET_CYCLE.VIS_ID";
    protected static String SQL_ALL_BUDGET_CYCLES_FOR_USER = "select 0       ,MODEL.MODEL_ID      ,MODEL.VIS_ID      ,BUDGET_CYCLE.BUDGET_CYCLE_ID      ,BUDGET_CYCLE.VIS_ID      ,BUDGET_CYCLE.DESCRIPTION      ,BUDGET_CYCLE.STATUS, PERIOD_ID, PERIOD_ID_TO, PERIOD_FROM_VISID, PERIOD_TO_VISID, BUDGET_CYCLE.CATEGORY from BUDGET_CYCLE, MODEL where model.model_id in (select distinct model_id from budget_user where user_id = ?) and BUDGET_CYCLE.MODEL_ID = MODEL.MODEL_ID  order by MODEL.VIS_ID, BUDGET_CYCLE.VIS_ID";
    protected static String SQL_ALL_BUDGET_CYCLES_WEB_DETAILED = "select 0       ,MODEL.MODEL_ID      ,MODEL.VIS_ID      ,BUDGET_CYCLE.BUDGET_CYCLE_ID      ,BUDGET_CYCLE.VIS_ID      ,MODEL.MODEL_ID      ,BUDGET_CYCLE.BUDGET_CYCLE_ID      ,BUDGET_CYCLE.DESCRIPTION      ,BUDGET_CYCLE.STATUS from BUDGET_CYCLE    ,MODEL where 1=1   and BUDGET_CYCLE.MODEL_ID = MODEL.MODEL_ID  order by MODEL.VIS_ID, BUDGET_CYCLE.VIS_ID";
    protected static String SQL_BUDGET_CYCLES_FOR_MODEL = "select 0       ,MODEL.MODEL_ID      ,MODEL.VIS_ID      ,BUDGET_CYCLE.BUDGET_CYCLE_ID      ,BUDGET_CYCLE.VIS_ID      ,BUDGET_CYCLE.DESCRIPTION      ,BUDGET_CYCLE.PLANNED_END_DATE from BUDGET_CYCLE    ,MODEL where 1=1   and BUDGET_CYCLE.MODEL_ID = MODEL.MODEL_ID  and  BUDGET_CYCLE.MODEL_ID = ? order by BUDGET_CYCLE.VIS_ID";
    protected static String SQL_BUDGET_CYCLES_FOR_MODEL_WITH_STATE = "select 0       ,MODEL.MODEL_ID      ,MODEL.VIS_ID      ,BUDGET_CYCLE.BUDGET_CYCLE_ID      ,BUDGET_CYCLE.VIS_ID      ,BUDGET_CYCLE.BUDGET_CYCLE_ID      ,BUDGET_CYCLE.VIS_ID      ,BUDGET_CYCLE.DESCRIPTION from BUDGET_CYCLE    ,MODEL where 1=1   and BUDGET_CYCLE.MODEL_ID = MODEL.MODEL_ID  and  BUDGET_CYCLE.MODEL_ID = ? and BUDGET_CYCLE.STATUS = ? order by BUDGET_CYCLE.VIS_ID";
    protected static String SQL_BUDGET_CYCLE_INTEGRITY = "select 0       ,MODEL.MODEL_ID      ,MODEL.VIS_ID      ,BUDGET_CYCLE.BUDGET_CYCLE_ID      ,BUDGET_CYCLE.VIS_ID      ,BUDGET_CYCLE.XML_FORM_ID      ,BUDGET_CYCLE.XML_FORM_DATA_TYPE from BUDGET_CYCLE    ,MODEL where 1=1   and BUDGET_CYCLE.MODEL_ID = MODEL.MODEL_ID ";
    protected static String SQL_BUDGET_CYCLE_DETAILED_FOR_ID = "select 0       ,MODEL.MODEL_ID      ,MODEL.VIS_ID      ,BUDGET_CYCLE.BUDGET_CYCLE_ID      ,BUDGET_CYCLE.VIS_ID      ,MODEL.MODEL_ID      ,BUDGET_CYCLE.BUDGET_CYCLE_ID      ,BUDGET_CYCLE.DESCRIPTION      ,BUDGET_CYCLE.STATUS      ,BUDGET_CYCLE.TYPE      ,BUDGET_CYCLE.PLANNED_END_DATE      ,BUDGET_CYCLE.START_DATE      ,BUDGET_CYCLE.END_DATE      ,BUDGET_CYCLE.XML_FORM_ID,       XML_FORM.VIS_ID,     XML_FORM.DESCRIPTION,      BUDGET_CYCLE.XML_FORM_DATA_TYPE    from BUDGET_CYCLE, XML_FORM, MODEL where 1=1 and BUDGET_CYCLE.XML_FORM_ID=XML_FORM.XML_FORM_ID and BUDGET_CYCLE.MODEL_ID = MODEL.MODEL_ID  and  BUDGET_CYCLE.BUDGET_CYCLE_ID = ?";
    protected static String SQL_BUDGET_CYCLE_XML_FORMS_FOR_ID = "select 0       ,MODEL.MODEL_ID      ,MODEL.VIS_ID      ,BUDGET_CYCLE.BUDGET_CYCLE_ID      ,BUDGET_CYCLE.VIS_ID      ,MODEL.MODEL_ID      ,BUDGET_CYCLE.BUDGET_CYCLE_ID      ,BUDGET_CYCLE.DESCRIPTION      ,BUDGET_CYCLE.STATUS      ,BUDGET_CYCLE.TYPE      ,BUDGET_CYCLE.PLANNED_END_DATE      ,BUDGET_CYCLE.START_DATE      ,BUDGET_CYCLE.END_DATE      ,BUDGET_CYCLE_LINK.XML_FORM_ID,      XML_FORM.VIS_ID,     XML_FORM.DESCRIPTION,     BUDGET_CYCLE_LINK.XML_FORM_DATA_TYPE      from      BUDGET_CYCLE, BUDGET_CYCLE_LINK, XML_FORM, MODEL, XML_FORM_USER_LINK      where    1=1    and     BUDGET_CYCLE_LINK.XML_FORM_ID = XML_FORM.XML_FORM_ID    and     BUDGET_CYCLE.MODEL_ID = MODEL.MODEL_ID    and    BUDGET_CYCLE_LINK.BUDGET_CYCLE_ID = BUDGET_CYCLE.BUDGET_CYCLE_ID  and  BUDGET_CYCLE.BUDGET_CYCLE_ID = ? and XML_FORM_USER_LINK.XML_FORM_ID = XML_FORM.XML_FORM_ID and XML_FORM_USER_LINK.USER_ID = ?";
    protected static String SQL_BUDGET_TRANSFER_BUDGET_CYCLES = "select 0       ,MODEL.MODEL_ID      ,MODEL.VIS_ID      ,BUDGET_CYCLE.BUDGET_CYCLE_ID      ,BUDGET_CYCLE.VIS_ID      ,FINANCE_CUBE.FINANCE_CUBE_ID      ,FINANCE_CUBE.VIS_ID      ,MODEL.MODEL_ID      ,FINANCE_CUBE.FINANCE_CUBE_ID      ,BUDGET_CYCLE.BUDGET_CYCLE_ID      ,BUDGET_CYCLE.DESCRIPTION      ,BUDGET_CYCLE.PERIOD_ID from BUDGET_CYCLE    ,MODEL    ,FINANCE_CUBE where 1=1   and BUDGET_CYCLE.MODEL_ID = MODEL.MODEL_ID  and  FINANCE_CUBE.MODEL_ID = MODEL.MODEL_ID and MODEL.MODEL_ID = BUDGET_CYCLE.MODEL_ID and BUDGET_CYCLE.STATUS = 1 and MODEL.VIREMENT_ENTRY_ENABLED = 'Y'";
    protected static final String SQL_DELETE_BATCH = "delete from BUDGET_CYCLE where    BUDGET_CYCLE_ID = ? ";
    private static String[][] SQL_DELETE_CHILDREN = { { "BUDGET_STATE", "delete from BUDGET_STATE where     BUDGET_STATE.BUDGET_CYCLE_ID = ? " }, { "LEVEL_DATE", "delete from LEVEL_DATE where     LEVEL_DATE.BUDGET_CYCLE_ID = ? " } };

    private static String[][] SQL_DELETE_CHILDRENS_DEPENDANTS = { {
            "BUDGET_STATE_HISTORY",
            "delete from BUDGET_STATE_HISTORY BudgetStateHistory where exists (select * from BUDGET_STATE_HISTORY,BUDGET_STATE,BUDGET_CYCLE where     BUDGET_STATE_HISTORY.BUDGET_CYCLE_ID = BUDGET_STATE.BUDGET_CYCLE_ID and BUDGET_STATE_HISTORY.STRUCTURE_ELEMENT_ID = BUDGET_STATE.STRUCTURE_ELEMENT_ID and BUDGET_STATE.BUDGET_CYCLE_ID = BUDGET_CYCLE.BUDGET_CYCLE_ID and BudgetStateHistory.BUDGET_CYCLE_ID = BUDGET_STATE_HISTORY.BUDGET_CYCLE_ID and BudgetStateHistory.STRUCTURE_ELEMENT_ID = BUDGET_STATE_HISTORY.STRUCTURE_ELEMENT_ID " } };

    private static String SQL_DELETE_DEPENDANT_CRITERIA = "and BUDGET_CYCLE.BUDGET_CYCLE_ID = ?)";
    public static final String SQL_BULK_GET_ALL = " from BUDGET_CYCLE where 1=1 and BUDGET_CYCLE.MODEL_ID = ? order by  BUDGET_CYCLE.BUDGET_CYCLE_ID";
    protected static final String SQL_GET_ALL = " from BUDGET_CYCLE where    MODEL_ID = ? ";
    // private static final String DEPTH_OF_LEVEL_DATES = "select max(depth) from level_date where budget_cycle_id =  ?";
    // private static final String DELETE_LEVEL_DATA = "delete from level_date where budget_cycle_id = ?";
    // private static final String BATCH_LEVEL_INSERT = "insert into level_date (BUDGET_CYCLE_ID, DEPTH, PLANNED_END_DATE, VERSION_NUM, UPDATED_BY_USER_ID, UPDATED_TIME, CREATED_TIME) values(?, ?, ?, 0, 0, sysdate, sysdate) ";
    // private static final String OPEN_BUDGET_CYCLE = "select 1 from dual where (     select min(s.position)       from budget_cycle b, structure_element s      where b.model_id = ?        and b.status = 1        and b.period_id = s.structure_element_id  ) <= (     select s.position from structure_element s where s.structure_element_id = ?  )";
    protected BudgetStateDAO mBudgetStateDAO;
    protected LevelDateDAO mLevelDateDAO;
    protected BudgetCycleLinkDAO mBudgetCycleLinkDAO;
    protected BudgetCycleEVO mDetails;

    public BudgetCycleDAO(Connection connection) {
        super(connection);
    }

    public BudgetCycleDAO() {
    }

    public BudgetCycleDAO(DataSource ds) {
        super(ds);
    }

    protected BudgetCyclePK getPK() {
        return this.mDetails.getPK();
    }

    public void setDetails(BudgetCycleEVO details) {
        this.mDetails = details.deepClone();
    }

    private BudgetCycleEVO getEvoFromJdbc(ResultSet resultSet_) throws SQLException {
        int col = 1;

        BudgetCycleEVO evo = new BudgetCycleEVO(
                resultSet_.getInt(col++), resultSet_.getInt(col++), // BUDGET_CYCLE_ID, MODEL_ID
                resultSet_.getString(col++), resultSet_.getString(col++), // VIS_ID, DESCRIPTION
                resultSet_.getInt(col++), resultSet_.getInt(col++), // TYPE, XML_FORM_ID
                resultSet_.getString(col++), resultSet_.getInt(col++), // XML_FORM_DATA_TYPE, PERIOD_ID,
                resultSet_.getInt(col++), resultSet_.getString(col++), // PERIOD_ID_TO, PERIOD_FROM_VISID,
                resultSet_.getString(col++), resultSet_.getTimestamp(col++), // PERIOD_TO_VISID, PLANNED_END_DATE,
                resultSet_.getTimestamp(col++), resultSet_.getTimestamp(col++), // START_DATE, END_DATE,
                resultSet_.getInt(col++), resultSet_.getInt(col++), // STATUS, VERSION_NUM,
                null, null, // UPDATED_BY_USER_ID, UPDATED_TIME,
                null, resultSet_.getString(col + 3)); // CREATED_TIME, CATEGORY

        evo.setUpdatedByUserId(resultSet_.getInt(col++));
        evo.setUpdatedTime(resultSet_.getTimestamp(col++));
        evo.setCreatedTime(resultSet_.getTimestamp(col++));
        return evo;
    }

    private int putEvoKeysToJdbc(BudgetCycleEVO evo_, PreparedStatement stmt_, int startCol_) throws SQLException {
        int col = startCol_;
        stmt_.setInt(col++, evo_.getBudgetCycleId());
        return col;
    }

    private int putEvoDataToJdbc(BudgetCycleEVO evo_, PreparedStatement stmt_, int startCol_) throws SQLException {
        int col = startCol_;
        stmt_.setInt(col++, evo_.getModelId());
        stmt_.setString(col++, evo_.getVisId());
        stmt_.setString(col++, evo_.getDescription());
        stmt_.setInt(col++, evo_.getType());
        stmt_.setInt(col++, evo_.getXmlFormId());
        stmt_.setString(col++, evo_.getXmlFormDataType());
        stmt_.setInt(col++, evo_.getPeriodId());
        stmt_.setInt(col++, evo_.getPeriodIdTo());
        stmt_.setString(col++, evo_.getPeriodFromVisId());
        stmt_.setString(col++, evo_.getPeriodToVisId());
        stmt_.setTimestamp(col++, evo_.getPlannedEndDate());
        stmt_.setTimestamp(col++, evo_.getStartDate());
        stmt_.setTimestamp(col++, evo_.getEndDate());
        stmt_.setInt(col++, evo_.getStatus());
        stmt_.setInt(col++, evo_.getVersionNum());
        stmt_.setInt(col++, evo_.getUpdatedByUserId());
        stmt_.setTimestamp(col++, evo_.getUpdatedTime());
        stmt_.setTimestamp(col++, evo_.getCreatedTime());
        stmt_.setString(col++, evo_.getCategory());
        return col;
    }

    protected void doLoad(BudgetCyclePK pk) throws ValidationException {
        Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;

        PreparedStatement stmt = null;
        ResultSet resultSet = null;
        
        String sqlString = "select "
                + "BUDGET_CYCLE_ID, MODEL_ID, VIS_ID, DESCRIPTION, TYPE, XML_FORM_ID, XML_FORM_DATA_TYPE, PERIOD_ID, PERIOD_ID_TO, "
                + "PERIOD_FROM_VISID, PERIOD_TO_VISID, "
                + "PLANNED_END_DATE, START_DATE, END_DATE, STATUS, VERSION_NUM, UPDATED_BY_USER_ID, UPDATED_TIME, CREATED_TIME, CATEGORY from BUDGET_CYCLE where BUDGET_CYCLE_ID = ?"; 
        try {
            stmt = getConnection().prepareStatement(sqlString);
            
            int col = 1;
            stmt.setInt(col++, pk.getBudgetCycleId());
            resultSet = stmt.executeQuery();

            if (!resultSet.next()) {
                throw new ValidationException(getEntityName() + " select of " + pk + " not found");
            }

            this.mDetails = getEvoFromJdbc(resultSet);
            if (this.mDetails.isModified())
                this._log.info("doLoad", this.mDetails);
        } catch (SQLException sqle) {
            throw handleSQLException(pk, sqlString, sqle);
        } finally {
            closeResultSet(resultSet);
            closeStatement(stmt);
            closeConnection();

            if (timer != null)
                timer.logDebug("doLoad", pk);
        }
    }

    protected void doCreate() throws DuplicateNameValidationException, ValidationException {
        Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
        this.mDetails.postCreateInit();

        PreparedStatement stmt = null;
        
        String sqlString = "insert into BUDGET_CYCLE "
                + "(BUDGET_CYCLE_ID, MODEL_ID, VIS_ID, DESCRIPTION, TYPE, XML_FORM_ID, XML_FORM_DATA_TYPE, "
                + "PERIOD_ID, PERIOD_ID_TO, PERIOD_FROM_VISID, PERIOD_TO_VISID, PLANNED_END_DATE, "
                + "START_DATE, END_DATE, STATUS, VERSION_NUM, UPDATED_BY_USER_ID, UPDATED_TIME, CREATED_TIME, CATEGORY) "
                + "values ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            duplicateValueCheckBudgetCycleName();

            this.mDetails.setCreatedTime(new Timestamp(new java.util.Date().getTime()));
            this.mDetails.setUpdatedTime(new Timestamp(new java.util.Date().getTime()));
            stmt = getConnection().prepareStatement(sqlString);

            int col = 1;
            col = putEvoKeysToJdbc(this.mDetails, stmt, col);
            col = putEvoDataToJdbc(this.mDetails, stmt, col);

            int resultCount = stmt.executeUpdate();
            if (resultCount != 1) {
                throw new RuntimeException(getEntityName() + " insert failed (" + this.mDetails.getPK() + "): resultCount=" + resultCount);
            }

            this.mDetails.reset();
        } catch (SQLException sqle) {
            throw handleSQLException(this.mDetails.getPK(), sqlString, sqle);
        } finally {
            closeStatement(stmt);
            closeConnection();

            if (timer != null) {
                timer.logDebug("doCreate", this.mDetails.toString());
            }
        }

        try {
            getBudgetStateDAO().update(this.mDetails.getBudgetCycleStatesMap());
            getLevelDateDAO().update(this.mDetails.getBudgetCycleLevlDatesMap());
            getBudgetCycleLinkDAO().update(this.mDetails.getBudgetCycleLinksMap());
        } catch (Exception e) {
            throw new RuntimeException("unexpected exception", e);
        }
    }

    protected void duplicateValueCheckBudgetCycleName() throws DuplicateNameValidationException {
        Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
        PreparedStatement stmt = null;
        ResultSet resultSet = null;
        
        String sqlString = "select count(*) from BUDGET_CYCLE where MODEL_ID = ? AND VIS_ID = ? and not( BUDGET_CYCLE_ID = ? )";
        try {
            stmt = getConnection().prepareStatement(sqlString);

            int col = 1;
            stmt.setInt(col++, this.mDetails.getModelId());
            stmt.setString(col++, this.mDetails.getVisId());
            col = putEvoKeysToJdbc(this.mDetails, stmt, col);

            resultSet = stmt.executeQuery();

            if (!resultSet.next()) {
                throw new RuntimeException(getEntityName() + " select of " + getPK() + " not found");
            }

            col = 1;
            int count = resultSet.getInt(col++);
            if (count > 0) {
                throw new DuplicateNameValidationException(getEntityName() + " " + getPK() + " BudgetCycleName");
            }

        } catch (SQLException sqle) {
            throw handleSQLException(getPK(), sqlString, sqle);
        } finally {
            closeResultSet(resultSet);
            closeStatement(stmt);
            closeConnection();

            if (timer != null)
                timer.logDebug("duplicateValueCheckBudgetCycleName", "");
        }
    }

    protected void doStore() throws DuplicateNameValidationException, VersionValidationException, ValidationException {
        Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;

        PreparedStatement stmt = null;

        boolean mainChanged = this.mDetails.isModified();
        boolean dependantChanged = false;
        try {
            if (mainChanged) {
                duplicateValueCheckBudgetCycleName();
            }
            if (getBudgetStateDAO().update(this.mDetails.getBudgetCycleStatesMap())) {
                dependantChanged = true;
            }

            if (getLevelDateDAO().update(this.mDetails.getBudgetCycleLevlDatesMap())) {
                dependantChanged = true;
            }
            if (getBudgetCycleLinkDAO().update(this.mDetails.getBudgetCycleLinksMap())) {
                dependantChanged = true;
            }
            if ((mainChanged) || (dependantChanged)) {
                this.mDetails.setVersionNum(this.mDetails.getVersionNum() + 1);

                this.mDetails.setUpdatedTime(new Timestamp(new java.util.Date().getTime()));
                stmt = getConnection().prepareStatement("update BUDGET_CYCLE set "
                        + "MODEL_ID = ?, VIS_ID = ?, DESCRIPTION = ?, TYPE = ?, XML_FORM_ID = ?, XML_FORM_DATA_TYPE = ?, "
                        + "PERIOD_ID = ?, PERIOD_ID_TO = ?, PERIOD_FROM_VISID = ?, PERIOD_TO_VISID = ?,"
                        + "PLANNED_END_DATE = ?, START_DATE = ?, END_DATE = ?, STATUS = ?, "
                        + "VERSION_NUM = ?, UPDATED_BY_USER_ID = ?, "
                        + "UPDATED_TIME = ?, CREATED_TIME = ?, CATEGORY = ? where BUDGET_CYCLE_ID = ? AND VERSION_NUM = ?");

                int col = 1;
                col = putEvoDataToJdbc(this.mDetails, stmt, col);
                col = putEvoKeysToJdbc(this.mDetails, stmt, col);

                stmt.setInt(col++, this.mDetails.getVersionNum() - 1);
                int resultCount = stmt.executeUpdate();
                if (resultCount == 0) {
                    checkVersionNum();
                }
                if (resultCount != 1) {
                    throw new RuntimeException(getEntityName() + " update failed (" + getPK() + "): resultCount=" + resultCount);
                }

                this.mDetails.reset();
            }

        } catch (SQLException sqle) {
            throw handleSQLException(getPK(), "update BUDGET_CYCLE set MODEL_ID = ?, VIS_ID = ?, DESCRIPTION = ?, TYPE = ?, XML_FORM_ID = ?, XML_FORM_DATA_TYPE = ?, PERIOD_ID = ?, PERIOD_ID_TO = ?, PLANNED_END_DATE = ?, START_DATE = ?, END_DATE = ?, STATUS = ?, VERSION_NUM = ?, UPDATED_BY_USER_ID = ?, UPDATED_TIME = ?, CREATED_TIME = ?, CATEGORY = ? where BUDGET_CYCLE_ID = ? AND VERSION_NUM = ?", sqle);
        } finally {
            closeStatement(stmt);
            closeConnection();

            if ((timer != null) && ((mainChanged) || (dependantChanged)))
                timer.logDebug("store", this.mDetails.getPK() + "(" + mainChanged + "," + dependantChanged + ")");
        }
    }

    private void checkVersionNum() throws VersionValidationException {
        Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
        PreparedStatement stmt = null;
        ResultSet resultSet = null;
        try {
            stmt = getConnection().prepareStatement("select VERSION_NUM from BUDGET_CYCLE where BUDGET_CYCLE_ID = ?");

            int col = 1;
            stmt.setInt(col++, this.mDetails.getBudgetCycleId());

            resultSet = stmt.executeQuery();

            if (!resultSet.next()) {
                throw new RuntimeException(getEntityName() + " checkVersionNum: select of " + getPK() + " not found");
            }

            col = 1;
            int dbVersionNumber = resultSet.getInt(col++);
            if (this.mDetails.getVersionNum() - 1 != dbVersionNumber) {
                throw new VersionValidationException(getEntityName() + " " + getPK() + " expected:" + (this.mDetails.getVersionNum() - 1) + " found:" + dbVersionNumber);
            }

        } catch (SQLException sqle) {
            throw handleSQLException(getPK(), "select VERSION_NUM from BUDGET_CYCLE where BUDGET_CYCLE_ID = ?", sqle);
        } finally {
            closeStatement(stmt);
            closeResultSet(resultSet);

            if (timer != null)
                timer.logDebug("checkVersionNum", this.mDetails.getPK());
        }
    }

    public AllBudgetCyclesELO getAllBudgetCycles() {
        Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
        PreparedStatement stmt = null;
        ResultSet resultSet = null;
        AllBudgetCyclesELO results = new AllBudgetCyclesELO();
        try {
            stmt = getConnection().prepareStatement(SQL_ALL_BUDGET_CYCLES);
            int col = 1;
            resultSet = stmt.executeQuery();
            while (resultSet.next()) {
                col = 2;

                ModelPK pkModel = new ModelPK(resultSet.getInt(col++));
                String textModel = resultSet.getString(col++);
                BudgetCyclePK pkBudgetCycle = new BudgetCyclePK(resultSet.getInt(col++));
                String textBudgetCycle = resultSet.getString(col++);
                BudgetCycleCK ckBudgetCycle = new BudgetCycleCK(pkModel, pkBudgetCycle);
                ModelRefImpl erModel = new ModelRefImpl(pkModel, textModel);
                BudgetCycleRefImpl erBudgetCycle = new BudgetCycleRefImpl(ckBudgetCycle, textBudgetCycle);
                String col1 = resultSet.getString(col++);
                int col2 = resultSet.getInt(col++);
                int periodFrom = resultSet.getInt(col++);
                int periodTo = resultSet.getInt(col++);
                String periodFromVisId = resultSet.getString(col++);
                String periodToVisId = resultSet.getString(col++);
                String category = resultSet.getString(col++);

                results.add(erBudgetCycle, erModel, col1, col2, periodFrom, periodTo, periodFromVisId, periodToVisId, category);
            }

        } catch (SQLException sqle) {
            throw handleSQLException(SQL_ALL_BUDGET_CYCLES, sqle);
        } finally {
            closeResultSet(resultSet);
            closeStatement(stmt);
            closeConnection();
        }

        if (timer != null) {
            timer.logDebug("getAllBudgetCycles", " items=" + results.size());
        }

        return results;
    }

    public AllBudgetCyclesELO getAllBudgetCyclesForUser(int userId) {
        Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
        PreparedStatement stmt = null;
        ResultSet resultSet = null;
        AllBudgetCyclesELO results = new AllBudgetCyclesELO();
        try {
            stmt = getConnection().prepareStatement(SQL_ALL_BUDGET_CYCLES_FOR_USER);
            int col = 1;
            stmt.setInt(col++, userId);
            resultSet = stmt.executeQuery();
            while (resultSet.next()) {
                col = 2;
                ModelPK pkModel = new ModelPK(resultSet.getInt(col++));
                String textModel = resultSet.getString(col++);
                BudgetCyclePK pkBudgetCycle = new BudgetCyclePK(resultSet.getInt(col++));
                String textBudgetCycle = resultSet.getString(col++);
                BudgetCycleCK ckBudgetCycle = new BudgetCycleCK(pkModel, pkBudgetCycle);
                ModelRefImpl erModel = new ModelRefImpl(pkModel, textModel);
                BudgetCycleRefImpl erBudgetCycle = new BudgetCycleRefImpl(ckBudgetCycle, textBudgetCycle);
                String col1 = resultSet.getString(col++);
                int col2 = resultSet.getInt(col++);
                int periodFrom = resultSet.getInt(col++);
                int periodTo = resultSet.getInt(col++);
                String periodFromVisId = resultSet.getString(col++);
                String periodToVisId = resultSet.getString(col++);
                String category = resultSet.getString(col);
                results.add(erBudgetCycle, erModel, col1, col2, periodFrom, periodTo, periodFromVisId, periodToVisId, category);
            }

        } catch (SQLException sqle) {
            throw handleSQLException(SQL_ALL_BUDGET_CYCLES_FOR_USER, sqle);
        } finally {
            closeResultSet(resultSet);
            closeStatement(stmt);
            closeConnection();
        }

        if (timer != null) {
            timer.logDebug("getAllBudgetCycles", " items=" + results.size());
        }

        return results;
    }

    public AllBudgetCyclesWebELO getAllBudgetCyclesWeb() {
        Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
        PreparedStatement stmt = null;
        ResultSet resultSet = null;
        AllBudgetCyclesWebELO results = new AllBudgetCyclesWebELO();
        try {
            stmt = getConnection().prepareStatement(SQL_ALL_BUDGET_CYCLES_WEB);
            int col = 1;
            resultSet = stmt.executeQuery();
            while (resultSet.next()) {
                col = 2;
                
                ModelPK pkModel = new ModelPK(resultSet.getInt(col++));
                String textModel = resultSet.getString(col++);
                BudgetCyclePK pkBudgetCycle = new BudgetCyclePK(resultSet.getInt(col++));
                String textBudgetCycle = resultSet.getString(col++);
                BudgetCycleCK ckBudgetCycle = new BudgetCycleCK(pkModel, pkBudgetCycle);
                ModelRefImpl erModel = new ModelRefImpl(pkModel, textModel);
                BudgetCycleRefImpl erBudgetCycle = new BudgetCycleRefImpl(ckBudgetCycle, textBudgetCycle);
                
                int col1 = resultSet.getInt(col++);
                String col2 = resultSet.getString(col++);

                results.add(erBudgetCycle, erModel, col1, col2);
            }

        } catch (SQLException sqle) {
            throw handleSQLException(SQL_ALL_BUDGET_CYCLES_WEB, sqle);
        } finally {
            closeResultSet(resultSet);
            closeStatement(stmt);
            closeConnection();
        }

        if (timer != null) {
            timer.logDebug("getAllBudgetCyclesWeb", " items=" + results.size());
        }

        return results;
    }

    public AllBudgetCyclesWebDetailedELO getAllBudgetCyclesWebDetailed() {
        Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
        PreparedStatement stmt = null;
        ResultSet resultSet = null;
        AllBudgetCyclesWebDetailedELO results = new AllBudgetCyclesWebDetailedELO();
        try {
            stmt = getConnection().prepareStatement(SQL_ALL_BUDGET_CYCLES_WEB_DETAILED);
            int col = 1;
            resultSet = stmt.executeQuery();
            while (resultSet.next()) {
                col = 2;

                ModelPK pkModel = new ModelPK(resultSet.getInt(col++));
                String textModel = resultSet.getString(col++);
                BudgetCyclePK pkBudgetCycle = new BudgetCyclePK(resultSet.getInt(col++));
                String textBudgetCycle = resultSet.getString(col++);
                BudgetCycleCK ckBudgetCycle = new BudgetCycleCK(pkModel, pkBudgetCycle);
                ModelRefImpl erModel = new ModelRefImpl(pkModel, textModel);
                BudgetCycleRefImpl erBudgetCycle = new BudgetCycleRefImpl(ckBudgetCycle, textBudgetCycle);

                int col1 = resultSet.getInt(col++);
                int col2 = resultSet.getInt(col++);
                String col3 = resultSet.getString(col++);
                int col4 = resultSet.getInt(col++);

                results.add(erBudgetCycle, erModel, col1, col2, col3, col4);
            }

        } catch (SQLException sqle) {
            throw handleSQLException(SQL_ALL_BUDGET_CYCLES_WEB_DETAILED, sqle);
        } finally {
            closeResultSet(resultSet);
            closeStatement(stmt);
            closeConnection();
        }

        if (timer != null) {
            timer.logDebug("getAllBudgetCyclesWebDetailed", " items=" + results.size());
        }

        return results;
    }

    public BudgetCyclesForModelELO getBudgetCyclesForModel(int param1) {
        Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
        PreparedStatement stmt = null;
        ResultSet resultSet = null;
        BudgetCyclesForModelELO results = new BudgetCyclesForModelELO();
        try {
            stmt = getConnection().prepareStatement(SQL_BUDGET_CYCLES_FOR_MODEL);
            int col = 1;
            stmt.setInt(col++, param1);
            resultSet = stmt.executeQuery();
            while (resultSet.next()) {
                col = 2;

                ModelPK pkModel = new ModelPK(resultSet.getInt(col++));
                String textModel = resultSet.getString(col++);
                BudgetCyclePK pkBudgetCycle = new BudgetCyclePK(resultSet.getInt(col++));
                String textBudgetCycle = resultSet.getString(col++);
                BudgetCycleCK ckBudgetCycle = new BudgetCycleCK(pkModel, pkBudgetCycle);
                ModelRefImpl erModel = new ModelRefImpl(pkModel, textModel);
                BudgetCycleRefImpl erBudgetCycle = new BudgetCycleRefImpl(ckBudgetCycle, textBudgetCycle);
                String col1 = resultSet.getString(col++);
                Timestamp col2 = resultSet.getTimestamp(col++);

                results.add(erBudgetCycle, erModel, col1, col2);
            }

        } catch (SQLException sqle) {
            throw handleSQLException(SQL_BUDGET_CYCLES_FOR_MODEL, sqle);
        } finally {
            closeResultSet(resultSet);
            closeStatement(stmt);
            closeConnection();
        }

        if (timer != null) {
            timer.logDebug("getBudgetCyclesForModel", " ModelId=" + param1 + " items=" + results.size());
        }

        return results;
    }

    public BudgetCyclesForModelWithStateELO getBudgetCyclesForModelWithState(int param1, int param2) {
        Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
        PreparedStatement stmt = null;
        ResultSet resultSet = null;
        BudgetCyclesForModelWithStateELO results = new BudgetCyclesForModelWithStateELO();
        try {
            stmt = getConnection().prepareStatement(SQL_BUDGET_CYCLES_FOR_MODEL_WITH_STATE);
            int col = 1;
            stmt.setInt(col++, param1);
            stmt.setInt(col++, param2);
            resultSet = stmt.executeQuery();
            while (resultSet.next()) {
                col = 2;

                ModelPK pkModel = new ModelPK(resultSet.getInt(col++));
                String textModel = resultSet.getString(col++);
                BudgetCyclePK pkBudgetCycle = new BudgetCyclePK(resultSet.getInt(col++));
                String textBudgetCycle = resultSet.getString(col++);
                BudgetCycleCK ckBudgetCycle = new BudgetCycleCK(pkModel, pkBudgetCycle);
                ModelRefImpl erModel = new ModelRefImpl(pkModel, textModel);
                BudgetCycleRefImpl erBudgetCycle = new BudgetCycleRefImpl(ckBudgetCycle, textBudgetCycle);

                int col1 = resultSet.getInt(col++);
                String col2 = resultSet.getString(col++);
                String col3 = resultSet.getString(col++);

                results.add(erBudgetCycle, erModel, col1, col2, col3);
            }

        } catch (SQLException sqle) {
            throw handleSQLException(SQL_BUDGET_CYCLES_FOR_MODEL_WITH_STATE, sqle);
        } finally {
            closeResultSet(resultSet);
            closeStatement(stmt);
            closeConnection();
        }

        if (timer != null) {
            timer.logDebug("getBudgetCyclesForModelWithState", " ModelId=" + param1 + ",Status=" + param2 + " items=" + results.size());
        }

        return results;
    }

    public BudgetCycleIntegrityELO getBudgetCycleIntegrity() {
        Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
        PreparedStatement stmt = null;
        ResultSet resultSet = null;
        BudgetCycleIntegrityELO results = new BudgetCycleIntegrityELO();
        try {
            stmt = getConnection().prepareStatement(SQL_BUDGET_CYCLE_INTEGRITY);
            int col = 1;
            resultSet = stmt.executeQuery();
            while (resultSet.next()) {
                col = 2;

                ModelPK pkModel = new ModelPK(resultSet.getInt(col++));
                String textModel = resultSet.getString(col++);
                BudgetCyclePK pkBudgetCycle = new BudgetCyclePK(resultSet.getInt(col++));
                String textBudgetCycle = resultSet.getString(col++);
                BudgetCycleCK ckBudgetCycle = new BudgetCycleCK(pkModel, pkBudgetCycle);
                ModelRefImpl erModel = new ModelRefImpl(pkModel, textModel);
                BudgetCycleRefImpl erBudgetCycle = new BudgetCycleRefImpl(ckBudgetCycle, textBudgetCycle);
                
                int col1 = resultSet.getInt(col++);
                String col2 = resultSet.getString(col++);

                results.add(erBudgetCycle, erModel, col1, col2);
            }

        } catch (SQLException sqle) {
            throw handleSQLException(SQL_BUDGET_CYCLE_INTEGRITY, sqle);
        } finally {
            closeResultSet(resultSet);
            closeStatement(stmt);
            closeConnection();
        }

        if (timer != null) {
            timer.logDebug("getBudgetCycleIntegrity", " items=" + results.size());
        }

        return results;
    }

    public BudgetCycleDetailedForIdELO getBudgetCycleDetailedForId(int param1) {
        Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
        PreparedStatement stmt = null;
        ResultSet resultSet = null;
        BudgetCycleDetailedForIdELO results = new BudgetCycleDetailedForIdELO();
        try {
            stmt = getConnection().prepareStatement(SQL_BUDGET_CYCLE_DETAILED_FOR_ID);
            int col = 1;
            stmt.setInt(col++, param1);
            resultSet = stmt.executeQuery();
            while (resultSet.next()) {
                col = 2;

                ModelPK pkModel = new ModelPK(resultSet.getInt(col++));
                String textModel = resultSet.getString(col++);
                BudgetCyclePK pkBudgetCycle = new BudgetCyclePK(resultSet.getInt(col++));
                String textBudgetCycle = resultSet.getString(col++);
                BudgetCycleCK ckBudgetCycle = new BudgetCycleCK(pkModel, pkBudgetCycle);
                ModelRefImpl erModel = new ModelRefImpl(pkModel, textModel);
                BudgetCycleRefImpl erBudgetCycle = new BudgetCycleRefImpl(ckBudgetCycle, textBudgetCycle);

                int col1 = resultSet.getInt(col++);
                int col2 = resultSet.getInt(col++);
                String col3 = resultSet.getString(col++);
                int col4 = resultSet.getInt(col++);
                int col5 = resultSet.getInt(col++);
                Timestamp col6 = resultSet.getTimestamp(col++);
                Timestamp col7 = resultSet.getTimestamp(col++);
                Timestamp col8 = resultSet.getTimestamp(col++);
                int xmlFormId = resultSet.getInt(col++);
                XmlFormPK pkXmlForm = new XmlFormPK(xmlFormId);
                String xmlFormVisId = resultSet.getString(col++);
                XmlFormRefImpl xmlFormRef = new XmlFormRefImpl(pkXmlForm, xmlFormVisId);
                String xmlFormDescription = resultSet.getString(col++);
                String xmlFormDataType = resultSet.getString(col++);

                results.add(erBudgetCycle, erModel, col1, col2, col3, col4, col5, col6, col7, col8, xmlFormRef, xmlFormDescription, xmlFormDataType);
            }

        } catch (SQLException sqle) {
            throw handleSQLException(SQL_BUDGET_CYCLE_DETAILED_FOR_ID, sqle);
        } finally {
            closeResultSet(resultSet);
            closeStatement(stmt);
            closeConnection();
        }

        if (timer != null) {
            timer.logDebug("getBudgetCycleDetailedForId", " BudgetCycleId=" + param1 + " items=" + results.size());
        }

        return results;
    }

    public BudgetCycleDetailedForIdELO getBudgetCycleXmlFormsForId(int param1, int userId) {
        Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
        PreparedStatement stmt = null;
        ResultSet resultSet = null;
        BudgetCycleDetailedForIdELO results = new BudgetCycleDetailedForIdELO();
        try {
            stmt = getConnection().prepareStatement(SQL_BUDGET_CYCLE_XML_FORMS_FOR_ID);
            int col = 1;
            stmt.setInt(col++, param1);
            stmt.setInt(col++, userId);
            resultSet = stmt.executeQuery();
            while (resultSet.next()) {
                col = 2;
                ModelPK pkModel = new ModelPK(resultSet.getInt(col++));
                String textModel = resultSet.getString(col++);
                BudgetCyclePK pkBudgetCycle = new BudgetCyclePK(resultSet.getInt(col++));
                String textBudgetCycle = resultSet.getString(col++);
                BudgetCycleCK ckBudgetCycle = new BudgetCycleCK(pkModel, pkBudgetCycle);
                ModelRefImpl erModel = new ModelRefImpl(pkModel, textModel);
                BudgetCycleRefImpl erBudgetCycle = new BudgetCycleRefImpl(ckBudgetCycle, textBudgetCycle);
                int col1 = resultSet.getInt(col++);
                int col2 = resultSet.getInt(col++);
                String col3 = resultSet.getString(col++);
                int col4 = resultSet.getInt(col++);
                int col5 = resultSet.getInt(col++);
                Timestamp col6 = resultSet.getTimestamp(col++);
                Timestamp col7 = resultSet.getTimestamp(col++);
                Timestamp col8 = resultSet.getTimestamp(col++);
                int xmlFormId = resultSet.getInt(col++);
                XmlFormPK pkXmlForm = new XmlFormPK(xmlFormId);
                String xmlFormVisId = resultSet.getString(col++);
                XmlFormRefImpl xmlFormRef = new XmlFormRefImpl(pkXmlForm, xmlFormVisId);
                String xmlFormDescription = resultSet.getString(col++);
                String xmlFormDataType = resultSet.getString(col++);

                results.add(erBudgetCycle, erModel, col1, col2, col3, col4, col5, col6, col7, col8, xmlFormRef, xmlFormDescription, xmlFormDataType);
            }
        } catch (SQLException sqle) {
            throw handleSQLException(SQL_BUDGET_CYCLE_XML_FORMS_FOR_ID, sqle);
        } finally {
            closeResultSet(resultSet);
            closeStatement(stmt);
            closeConnection();
        }
        if (timer != null) {
            timer.logDebug("getBudgetCycleDetailedForId", " BudgetCycleId=" + param1 + " items=" + results.size());
        }

        return results;
    }

    public BudgetTransferBudgetCyclesELO getBudgetTransferBudgetCycles() {
        Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
        PreparedStatement stmt = null;
        ResultSet resultSet = null;
        BudgetTransferBudgetCyclesELO results = new BudgetTransferBudgetCyclesELO();
        try {
            stmt = getConnection().prepareStatement(SQL_BUDGET_TRANSFER_BUDGET_CYCLES);
            int col = 1;
            resultSet = stmt.executeQuery();
            while (resultSet.next()) {
                col = 2;

                ModelPK pkModel = new ModelPK(resultSet.getInt(col++));
                String textModel = resultSet.getString(col++);
                BudgetCyclePK pkBudgetCycle = new BudgetCyclePK(resultSet.getInt(col++));
                String textBudgetCycle = resultSet.getString(col++);
                FinanceCubePK pkFinanceCube = new FinanceCubePK(resultSet.getInt(col++));
                String textFinanceCube = resultSet.getString(col++);
                BudgetCycleCK ckBudgetCycle = new BudgetCycleCK(pkModel, pkBudgetCycle);
                ModelRefImpl erModel = new ModelRefImpl(pkModel, textModel);
                BudgetCycleRefImpl erBudgetCycle = new BudgetCycleRefImpl(ckBudgetCycle, textBudgetCycle);
                FinanceCubeRefImpl erFinanceCube = new FinanceCubeRefImpl(pkFinanceCube, textFinanceCube);

                int col1 = resultSet.getInt(col++);
                int col2 = resultSet.getInt(col++);
                int col3 = resultSet.getInt(col++);
                String col4 = resultSet.getString(col++);
                int col5 = resultSet.getInt(col++);

                results.add(erBudgetCycle, erModel, erFinanceCube, col1, col2, col3, col4, col5);
            }

        } catch (SQLException sqle) {
            throw handleSQLException(SQL_BUDGET_TRANSFER_BUDGET_CYCLES, sqle);
        } finally {
            closeResultSet(resultSet);
            closeStatement(stmt);
            closeConnection();
        }

        if (timer != null) {
            timer.logDebug("getBudgetTransferBudgetCycles", " items=" + results.size());
        }

        return results;
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    public boolean update(Map items) throws DuplicateNameValidationException, VersionValidationException, ValidationException {
        if (items == null) {
            return false;
        }
        Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
        PreparedStatement deleteStmt = null;

        boolean somethingChanged = false;
        try {
            Iterator iter3 = new ArrayList(items.values()).iterator();
            while (iter3.hasNext()) {
                this.mDetails = ((BudgetCycleEVO) iter3.next());
                if (!this.mDetails.deletePending())
                    continue;
                somethingChanged = true;

                deleteDependants(this.mDetails.getPK());
            }

            Iterator iter2 = new ArrayList(items.values()).iterator();
            while (iter2.hasNext()) {
                this.mDetails = ((BudgetCycleEVO) iter2.next());

                if (!this.mDetails.deletePending())
                    continue;
                somethingChanged = true;

                if (deleteStmt == null) {
                    deleteStmt = getConnection().prepareStatement("delete from BUDGET_CYCLE where    BUDGET_CYCLE_ID = ? ");
                }

                int col = 1;
                deleteStmt.setInt(col++, this.mDetails.getBudgetCycleId());

                if (this._log.isDebugEnabled()) {
                    this._log.debug("update", "BudgetCycle deleting BudgetCycleId=" + this.mDetails.getBudgetCycleId());
                }

                deleteStmt.addBatch();

                items.remove(this.mDetails.getPK());
            }

            if (deleteStmt != null) {
                Timer timer2 = this._log.isDebugEnabled() ? new Timer(this._log) : null;

                deleteStmt.executeBatch();

                if (timer2 != null) {
                    timer2.logDebug("update", "delete batch");
                }
            }

            Iterator iter1 = items.values().iterator();
            while (iter1.hasNext()) {
                this.mDetails = ((BudgetCycleEVO) iter1.next());

                if (this.mDetails.insertPending()) {
                    somethingChanged = true;
                    doCreate();
                    continue;
                }

                if (this.mDetails.isModified()) {
                    somethingChanged = true;
                    doStore();
                    continue;
                }

                if (this.mDetails.deletePending()) {
                    continue;
                }

                if (getBudgetStateDAO().update(this.mDetails.getBudgetCycleStatesMap())) {
                    somethingChanged = true;
                }

                if (getLevelDateDAO().update(this.mDetails.getBudgetCycleLevlDatesMap())) {
                    somethingChanged = true;
                }

                if (getBudgetCycleLinkDAO().update(this.mDetails.getBudgetCycleLinksMap())) {
                    somethingChanged = true;
                }

            }

            boolean bool1 = somethingChanged;
            return bool1;
        } catch (SQLException sqle) {
            throw handleSQLException("delete from BUDGET_CYCLE where    BUDGET_CYCLE_ID = ? ", sqle);
        } finally {
            if (deleteStmt != null) {
                closeStatement(deleteStmt);
                closeConnection();
            }

            this.mDetails = null;

            if ((somethingChanged) && (timer != null))
                timer.logDebug("update", "collection");
        }
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    private void deleteDependants(BudgetCyclePK pk) {
        Set emptyStrings = Collections.emptySet();
        deleteDependants(pk, emptyStrings);
    }

    private void deleteDependants(BudgetCyclePK pk, Set<String> exclusionTables) {
        for (int i = SQL_DELETE_CHILDRENS_DEPENDANTS.length - 1; i > -1; i--) {
            if (exclusionTables.contains(SQL_DELETE_CHILDRENS_DEPENDANTS[i][0]))
                continue;
            Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;

            PreparedStatement stmt = null;

            int resultCount = 0;
            String s = null;
            try {
                s = SQL_DELETE_CHILDRENS_DEPENDANTS[i][1] + SQL_DELETE_DEPENDANT_CRITERIA;

                if (this._log.isDebugEnabled()) {
                    this._log.debug("deleteDependants", s);
                }
                stmt = getConnection().prepareStatement(s);

                int col = 1;
                stmt.setInt(col++, pk.getBudgetCycleId());

                resultCount = stmt.executeUpdate();
            } catch (SQLException sqle) {
                throw handleSQLException(pk, s, sqle);
            } finally {
                closeStatement(stmt);
                closeConnection();

                if (timer != null) {
                    timer.logDebug("deleteDependants", "A[" + i + "] count=" + resultCount);
                }
            }
        }
        for (int i = SQL_DELETE_CHILDREN.length - 1; i > -1; i--) {
            if (exclusionTables.contains(SQL_DELETE_CHILDREN[i][0]))
                continue;
            Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;

            PreparedStatement stmt = null;

            int resultCount = 0;
            String s = null;
            try {
                s = SQL_DELETE_CHILDREN[i][1];

                if (this._log.isDebugEnabled()) {
                    this._log.debug("deleteDependants", s);
                }
                stmt = getConnection().prepareStatement(s);

                int col = 1;
                stmt.setInt(col++, pk.getBudgetCycleId());

                resultCount = stmt.executeUpdate();
            } catch (SQLException sqle) {
                throw handleSQLException(pk, s, sqle);
            } finally {
                closeStatement(stmt);
                closeConnection();

                if (timer != null)
                    timer.logDebug("deleteDependants", "B[" + i + "] count=" + resultCount);
            }
        }
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    public void bulkGetAll(ModelPK entityPK, ModelEVO owningEVO, String dependants) {
        Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;

        PreparedStatement stmt = null;
        ResultSet resultSet = null;

        int itemCount = 0;

        Collection theseItems = new ArrayList();
        owningEVO.setBudgetCycles(theseItems);
        owningEVO.setBudgetCyclesAllItemsLoaded(true);
        try {
            stmt = getConnection()
                    .prepareStatement(
                            "select BUDGET_CYCLE.BUDGET_CYCLE_ID,BUDGET_CYCLE.MODEL_ID,BUDGET_CYCLE.VIS_ID,BUDGET_CYCLE.DESCRIPTION,BUDGET_CYCLE.TYPE,BUDGET_CYCLE.XML_FORM_ID,BUDGET_CYCLE.XML_FORM_DATA_TYPE,BUDGET_CYCLE.PERIOD_ID,BUDGET_CYCLE.PERIOD_ID_TO,BUDGET_CYCLE.PLANNED_END_DATE,BUDGET_CYCLE.START_DATE,BUDGET_CYCLE.END_DATE,BUDGET_CYCLE.STATUS,BUDGET_CYCLE.VERSION_NUM,BUDGET_CYCLE.UPDATED_BY_USER_ID,BUDGET_CYCLE.UPDATED_TIME,BUDGET_CYCLE.CREATED_TIME,BUDGET_CYCLE.CATEGORY from BUDGET_CYCLE where 1=1 and BUDGET_CYCLE.MODEL_ID = ? order by  BUDGET_CYCLE.BUDGET_CYCLE_ID");

            int col = 1;
            stmt.setInt(col++, entityPK.getModelId());

            resultSet = stmt.executeQuery();

            while (resultSet.next()) {
                itemCount++;
                this.mDetails = getEvoFromJdbc(resultSet);

                theseItems.add(this.mDetails);
            }

            if (timer != null) {
                timer.logDebug("bulkGetAll", "items=" + itemCount);
            }

            if ((itemCount > 0) && (dependants.indexOf("<12>") > -1)) {
                getBudgetStateDAO().bulkGetAll(entityPK, theseItems, dependants);
            }
            if ((itemCount > 0) && (dependants.indexOf("<14>") > -1)) {
                getLevelDateDAO().bulkGetAll(entityPK, theseItems, dependants);
            }
            if ((itemCount > 0) && (dependants.indexOf("<15>") > -1)) {
                getBudgetCycleLinkDAO().bulkGetAll(entityPK, theseItems, dependants);
            }
        } catch (SQLException sqle) {
            throw handleSQLException(
                    "select BUDGET_CYCLE.BUDGET_CYCLE_ID,BUDGET_CYCLE.MODEL_ID,BUDGET_CYCLE.VIS_ID,BUDGET_CYCLE.DESCRIPTION,BUDGET_CYCLE.TYPE,BUDGET_CYCLE.XML_FORM_ID,BUDGET_CYCLE.XML_FORM_DATA_TYPE,BUDGET_CYCLE.PERIOD_ID,BUDGET_CYCLE.PERIOD_ID_TO,BUDGET_CYCLE.PLANNED_END_DATE,BUDGET_CYCLE.START_DATE,BUDGET_CYCLE.END_DATE,BUDGET_CYCLE.STATUS,BUDGET_CYCLE.VERSION_NUM,BUDGET_CYCLE.UPDATED_BY_USER_ID,BUDGET_CYCLE.UPDATED_TIME,BUDGET_CYCLE.CREATED_TIME from BUDGET_CYCLE where 1=1 and BUDGET_CYCLE.MODEL_ID = ? order by  BUDGET_CYCLE.BUDGET_CYCLE_ID",
                    sqle);
        } finally {
            closeResultSet(resultSet);
            closeStatement(stmt);
            closeConnection();

            this.mDetails = null;
        }
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    public Collection getAll(int selectModelId, String dependants, Collection currentList) {
        Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
        PreparedStatement stmt = null;
        ResultSet resultSet = null;

        ArrayList items = new ArrayList();
        try {
            stmt = getConnection()
                    .prepareStatement(
                            "select BUDGET_CYCLE_ID, MODEL_ID, VIS_ID, DESCRIPTION, "
                            + "TYPE, XML_FORM_ID, XML_FORM_DATA_TYPE, PERIOD_ID, PERIOD_ID_TO, PERIOD_FROM_VISID, PERIOD_TO_VISID, "
                            + "PLANNED_END_DATE, START_DATE, END_DATE, STATUS, "
                            + "VERSION_NUM, UPDATED_BY_USER_ID, UPDATED_TIME, CREATED_TIME, CATEGORY from BUDGET_CYCLE where MODEL_ID = ? ");

            int col = 1;
            stmt.setInt(col++, selectModelId);

            resultSet = stmt.executeQuery();

            while (resultSet.next()) {
                this.mDetails = getEvoFromJdbc(resultSet);

                getDependants(this.mDetails, dependants);

                items.add(this.mDetails);
            }

            if (currentList != null) {
                ListIterator iter = items.listIterator();
                BudgetCycleEVO currentEVO = null;
                BudgetCycleEVO newEVO = null;
                while (iter.hasNext()) {
                    newEVO = (BudgetCycleEVO) iter.next();
                    Iterator iter2 = currentList.iterator();
                    while (iter2.hasNext()) {
                        currentEVO = (BudgetCycleEVO) iter2.next();
                        if (!currentEVO.getPK().equals(newEVO.getPK()))
                            continue;
                        iter.set(currentEVO);
                    }

                }

                Iterator iter2 = currentList.iterator();
                while (iter2.hasNext()) {
                    currentEVO = (BudgetCycleEVO) iter2.next();
                    if (currentEVO.insertPending()) {
                        items.add(currentEVO);
                    }
                }
            }
            this.mDetails = null;
        } catch (SQLException sqle) {
            throw handleSQLException(
                    "select BUDGET_CYCLE.BUDGET_CYCLE_ID, BUDGET_CYCLE.MODEL_ID, BUDGET_CYCLE.VIS_ID, BUDGET_CYCLE.DESCRIPTION, BUDGET_CYCLE.TYPE, BUDGET_CYCLE.XML_FORM_ID, BUDGET_CYCLE.XML_FORM_DATA_TYPE, BUDGET_CYCLE.PERIOD_ID, BUDGET_CYCLE.PERIOD_ID_TO, BUDGET_CYCLE.PLANNED_END_DATE, BUDGET_CYCLE.START_DATE, BUDGET_CYCLE.END_DATE, BUDGET_CYCLE.STATUS, BUDGET_CYCLE.VERSION_NUM, BUDGET_CYCLE.UPDATED_BY_USER_ID, BUDGET_CYCLE.UPDATED_TIME, BUDGET_CYCLE.CREATED_TIME, BUDGET_CYCLE.CATEGORY from BUDGET_CYCLE where MODEL_ID = ? ",
                    sqle);
        } finally {
            closeResultSet(resultSet);
            closeStatement(stmt);
            closeConnection();

            if (timer != null) {
                timer.logDebug("getAll", " ModelId=" + selectModelId + " items=" + items.size());
            }

        }

        return items;
    }

    @SuppressWarnings("unchecked")
    public BudgetCycleEVO getDetails(ModelCK paramCK, String dependants) throws ValidationException {
        Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;

        if (this.mDetails == null) {
            doLoad(((BudgetCycleCK) paramCK).getBudgetCyclePK());
        } else if (!this.mDetails.getPK().equals(((BudgetCycleCK) paramCK).getBudgetCyclePK())) {
            doLoad(((BudgetCycleCK) paramCK).getBudgetCyclePK());
        }

        if ((dependants.indexOf("<12>") > -1) && (!this.mDetails.isBudgetCycleStatesAllItemsLoaded())) {
            this.mDetails.setBudgetCycleStates(getBudgetStateDAO().getAll(this.mDetails.getBudgetCycleId(), dependants, this.mDetails.getBudgetCycleStates()));

            this.mDetails.setBudgetCycleStatesAllItemsLoaded(true);
        }

        if ((dependants.indexOf("<14>") > -1) && (!this.mDetails.isBudgetCycleLevlDatesAllItemsLoaded())) {
            this.mDetails.setBudgetCycleLevlDates(getLevelDateDAO().getAll(this.mDetails.getBudgetCycleId(), dependants, this.mDetails.getBudgetCycleLevlDates()));

            this.mDetails.setBudgetCycleLevlDatesAllItemsLoaded(true);
        }

        if ((dependants.indexOf("<15>") > -1) && (!this.mDetails.isBudgetCycleLinksAllItemsLoaded())) {
            this.mDetails.setBudgetCycleLinks(getBudgetCycleLinkDAO().getAll(this.mDetails.getBudgetCycleId(), dependants, this.mDetails.getBudgetCycleLinks()));
            this.mDetails.setBudgetCycleLinksAllItemsLoaded(true);
        }

        if ((paramCK instanceof BudgetStateCK)) {
            if (this.mDetails.getBudgetCycleStates() == null) {
                this.mDetails.loadBudgetCycleStatesItem(getBudgetStateDAO().getDetails(paramCK, dependants));
            } else {
                BudgetStatePK pk = ((BudgetStateCK) paramCK).getBudgetStatePK();
                BudgetStateEVO evo = this.mDetails.getBudgetCycleStatesItem(pk);
                if (evo == null)
                    this.mDetails.loadBudgetCycleStatesItem(getBudgetStateDAO().getDetails(paramCK, dependants));
                else {
                    getBudgetStateDAO().getDetails(paramCK, evo, dependants);
                }
            }
        } else if ((paramCK instanceof LevelDateCK)) {
            if (this.mDetails.getBudgetCycleLevlDates() == null) {
                this.mDetails.loadBudgetCycleLevlDatesItem(getLevelDateDAO().getDetails(paramCK, dependants));
            } else {
                LevelDatePK pk = ((LevelDateCK) paramCK).getLevelDatePK();
                LevelDateEVO evo = this.mDetails.getBudgetCycleLevlDatesItem(pk);
                if (evo == null) {
                    this.mDetails.loadBudgetCycleLevlDatesItem(getLevelDateDAO().getDetails(paramCK, dependants));
                }
            }
        } else if ((paramCK instanceof BudgetCycleLinkCK)) {
            if (this.mDetails.getBudgetCycleLinks() == null) {
                this.mDetails.loadBudgetCycleLinkItem(getBudgetCycleLinkDAO().getDetails(paramCK, dependants));
            } else {
                BudgetCycleLinkPK pk = ((BudgetCycleLinkCK) paramCK).getBudgetCycleLinkPK();
                BudgetCycleLinkEVO evo = this.mDetails.getBudgetCycleLinkItem(pk);
                if (evo == null) {
                    this.mDetails.loadBudgetCycleLinkItem(getBudgetCycleLinkDAO().getDetails(paramCK, dependants));
                }
            }
        }

        BudgetCycleEVO details = new BudgetCycleEVO();
        details = this.mDetails.deepClone();

        if (timer != null) {
            timer.logDebug("getDetails", paramCK + " " + dependants);
        }
        return details;
    }

    public BudgetCycleEVO getDetails(ModelCK paramCK, BudgetCycleEVO paramEVO, String dependants) throws ValidationException {
        BudgetCycleEVO savedEVO = this.mDetails;
        this.mDetails = paramEVO;
        BudgetCycleEVO newEVO = getDetails(paramCK, dependants);
        this.mDetails = savedEVO;
        return newEVO;
    }

    public BudgetCycleEVO getDetails(String dependants) throws ValidationException {
        Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;

        getDependants(this.mDetails, dependants);

        BudgetCycleEVO details = this.mDetails.deepClone();

        if (timer != null) {
            timer.logDebug("getDetails", this.mDetails.getPK() + " " + dependants);
        }
        return details;
    }

    protected BudgetStateDAO getBudgetStateDAO() {
        if (this.mBudgetStateDAO == null) {
            if (this.mDataSource != null)
                this.mBudgetStateDAO = new BudgetStateDAO(this.mDataSource);
            else {
                this.mBudgetStateDAO = new BudgetStateDAO(getConnection());
            }
        }
        return this.mBudgetStateDAO;
    }

    protected LevelDateDAO getLevelDateDAO() {
        if (this.mLevelDateDAO == null) {
            if (this.mDataSource != null)
                this.mLevelDateDAO = new LevelDateDAO(this.mDataSource);
            else {
                this.mLevelDateDAO = new LevelDateDAO(getConnection());
            }
        }
        return this.mLevelDateDAO;
    }

    protected BudgetCycleLinkDAO getBudgetCycleLinkDAO() {
        if (this.mBudgetCycleLinkDAO == null) {
            if (this.mDataSource != null)
                this.mBudgetCycleLinkDAO = new BudgetCycleLinkDAO(this.mDataSource);
            else {
                this.mBudgetCycleLinkDAO = new BudgetCycleLinkDAO(getConnection());
            }
        }
        return this.mBudgetCycleLinkDAO;
    }

    public String getEntityName() {
        return "BudgetCycle";
    }

    public BudgetCycleRefImpl getRef(BudgetCyclePK paramBudgetCyclePK) {
        Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
        PreparedStatement stmt = null;
        ResultSet resultSet = null;
        try {
            stmt = getConnection().prepareStatement("select 0,MODEL.MODEL_ID,BUDGET_CYCLE.VIS_ID from BUDGET_CYCLE,MODEL where 1=1 and BUDGET_CYCLE.BUDGET_CYCLE_ID = ? and BUDGET_CYCLE.MODEL_ID = MODEL.MODEL_ID");
            int col = 1;
            stmt.setInt(col++, paramBudgetCyclePK.getBudgetCycleId());

            resultSet = stmt.executeQuery();

            if (!resultSet.next()) {
                throw new RuntimeException(getEntityName() + " getRef " + paramBudgetCyclePK + " not found");
            }
            col = 2;
            ModelPK newModelPK = new ModelPK(resultSet.getInt(col++));

            String textBudgetCycle = resultSet.getString(col++);
            BudgetCycleCK ckBudgetCycle = new BudgetCycleCK(newModelPK, paramBudgetCyclePK);

            BudgetCycleRefImpl localBudgetCycleRefImpl = new BudgetCycleRefImpl(ckBudgetCycle, textBudgetCycle);
            return localBudgetCycleRefImpl;
        } catch (SQLException sqle) {
            throw handleSQLException(paramBudgetCyclePK, "select 0,MODEL.MODEL_ID,BUDGET_CYCLE.VIS_ID from BUDGET_CYCLE,MODEL where 1=1 and BUDGET_CYCLE.BUDGET_CYCLE_ID = ? and BUDGET_CYCLE.MODEL_ID = MODEL.MODEL_ID", sqle);
        } finally {
            closeResultSet(resultSet);
            closeStatement(stmt);
            closeConnection();

            if (timer != null)
                timer.logDebug("getRef", paramBudgetCyclePK);
        }
    }

    @SuppressWarnings("rawtypes")
    public void getDependants(Collection c, String dependants) {
        if (c == null)
            return;
        Iterator iter = c.iterator();
        while (iter.hasNext()) {
            BudgetCycleEVO evo = (BudgetCycleEVO) iter.next();
            getDependants(evo, dependants);
        }
    }

    @SuppressWarnings("unchecked")
    public void getDependants(BudgetCycleEVO evo, String dependants) {
        if (evo.insertPending()) {
            return;
        }
        if (evo.getBudgetCycleId() < 1) {
            return;
        }

        if ((dependants.indexOf("<12>") > -1) || (dependants.indexOf("<13>") > -1)) {
            if (!evo.isBudgetCycleStatesAllItemsLoaded()) {
                evo.setBudgetCycleStates(getBudgetStateDAO().getAll(evo.getBudgetCycleId(), dependants, evo.getBudgetCycleStates()));

                evo.setBudgetCycleStatesAllItemsLoaded(true);
            }
            getBudgetStateDAO().getDependants(evo.getBudgetCycleStates(), dependants);
        }

        if (dependants.indexOf("<14>") > -1) {
            if (!evo.isBudgetCycleLevlDatesAllItemsLoaded()) {
                evo.setBudgetCycleLevlDates(getLevelDateDAO().getAll(evo.getBudgetCycleId(), dependants, evo.getBudgetCycleLevlDates()));

                evo.setBudgetCycleLevlDatesAllItemsLoaded(true);
            }
        }

        if (dependants.indexOf("<15>") > -1) {
            if (!evo.isBudgetCycleLinksAllItemsLoaded()) {
                evo.setBudgetCycleLinks(getBudgetCycleLinkDAO().getAll(evo.getBudgetCycleId(), dependants, evo.getBudgetCycleLinks()));
                evo.setBudgetCycleStatesAllItemsLoaded(true);
            }
        }
    }

    public int getDepthOfLevelDates(int budgetCycleId) {
        Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        int result = 0;
        try {
            stmt = getConnection().prepareStatement("select max(depth) from level_date where budget_cycle_id =  ?");
            stmt.setInt(1, budgetCycleId);

            rs = stmt.executeQuery();

            if (rs.next())
                result = rs.getInt(1);
        } catch (SQLException sqle) {
            throw handleSQLException("select max(depth) from level_date where budget_cycle_id =  ?", sqle);
        } finally {
            closeResultSet(rs);
            closeStatement(stmt);
            closeConnection();
        }

        if (timer != null) {
            timer.logDebug("getDepthOfLevelDates");
        }
        return result;
    }

    public void insertNewLevelDates(int bcId, List<java.util.Date> dates) {
        Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            stmt = getConnection().prepareStatement("delete from level_date where budget_cycle_id = ?");
            stmt.setInt(1, bcId);

            stmt.executeUpdate();
        } catch (SQLException sqle) {
            throw handleSQLException("delete from level_date where budget_cycle_id = ?", sqle);
        } finally {
            closeResultSet(rs);
            closeStatement(stmt);
            closeConnection();
        }

        try {
            stmt = getConnection().prepareStatement("insert into level_date (BUDGET_CYCLE_ID, DEPTH, PLANNED_END_DATE, VERSION_NUM, UPDATED_BY_USER_ID, UPDATED_TIME, CREATED_TIME) values(?, ?, ?, 0, 0, sysdate, sysdate) ");
            int depth = 0;
            for (java.util.Date d: dates) {
                int col = 1;
                stmt.setInt(col++, bcId);
                stmt.setInt(col++, depth++);
                stmt.setDate(col, new java.sql.Date(d.getTime()));

                stmt.addBatch();
            }
            stmt.executeBatch();
        } catch (SQLException e) {
            throw handleSQLException("batchInsert failed", e);
        } finally {
            closeStatement(stmt);
            closeConnection();
        }

        if (timer != null)
            timer.logDebug("insertNewLevelDates");
    }

    public boolean isCalendarIdWithinOpenBudgetCycle(int modelId, int calendarId) {
        boolean result = false;

        Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            stmt = getConnection().prepareStatement("select 1 from dual where (     select min(s.position)       from budget_cycle b, structure_element s      where b.model_id = ?        and b.status = 1        and b.period_id = s.structure_element_id  ) <= (     select s.position from structure_element s where s.structure_element_id = ?  )");
            stmt.setInt(1, modelId);
            stmt.setInt(2, calendarId);

            rs = stmt.executeQuery();

            result = rs.next();
        } catch (SQLException sqle) {
            throw handleSQLException("select max(depth) from level_date where budget_cycle_id =  ?", sqle);
        } finally {
            closeResultSet(rs);
            closeStatement(stmt);
            closeConnection();
        }

        if (timer != null) {
            timer.logDebug("isCalendarIdWithinOpenBudgetCycle");
        }
        return result;
    }

    public List<Integer> getXmlForms() {
        PreparedStatement stmt = null;
        ResultSet resultSet = null;
        List<Integer> results = new ArrayList<Integer>();
        try {
            stmt = getConnection().prepareStatement("select XML_FORM_ID from budget_cycle_link order by xml_form_id");
            resultSet = stmt.executeQuery();
            while (resultSet.next()) {
                results.add(resultSet.getInt(1));
            }

        } catch (SQLException sqle) {
            System.err.println(sqle);
            sqle.printStackTrace();
            throw new RuntimeException(new StringBuilder().append(getEntityName()).append(" getXmlForms").toString(), sqle);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(new StringBuilder().append(getEntityName()).append(" getXmlForms").toString(), e);
        } finally {
            closeResultSet(resultSet);
            closeStatement(stmt);
            closeConnection();
        }
        return results;
    }

    public void updatePeriods(int bcId, int periodId, int fromTo) {
        PreparedStatement stmt = null;
        String sql = "UPDATE BUDGET_CYCLE SET ";

        // sql += fromTo == 0 ? "PERIOD_ID" : "PERIOD_ID_TO";

        if (fromTo == 0) {
            sql += "PERIOD_ID = ?, ";
            sql += "PERIOD_FROM_VISID = ";
        } else {
            sql += "PERIOD_ID_TO = ?, ";
            sql += "PERIOD_TO_VISID = ";
        }
        sql += "(SELECT " + "CASE " + "WHEN s2.parent_id = 0 " + "THEN '/' || s1.vis_id " + "ELSE '/' || s2.vis_id || '/' ||s1.vis_id " + "END AS col " + "FROM structure_element s1 " + "LEFT JOIN structure_element s2 ON s1.parent_id = s2.structure_element_id " + "WHERE s1.structure_element_id = ?) ";
        sql += " WHERE BUDGET_CYCLE_ID = ?";

        try {
            stmt = getConnection().prepareStatement(sql);
            stmt.setInt(1, periodId);
            stmt.setInt(2, periodId);
            stmt.setInt(3, bcId);
            stmt.executeQuery();
        } catch (SQLException sqle) {
            throw handleSQLException(sql, sqle);
        } finally {
            closeStatement(stmt);
            closeConnection();
        }
        
        //TODO po update w ogole nie aktualizuje się BudgetCycleEVO mDetails;
        BudgetCyclePK budgetCyclePK = new BudgetCyclePK(bcId);
        try {
            doLoad(budgetCyclePK);
        } catch (ValidationException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}

/* Location: /home/oracle/coa/cp.ear/cp.ear_orginal/cp-server.jar
 * Qualified Name: com.cedar.cp.ejb.impl.model.BudgetCycleDAO
 * JD-Core Version: 0.6.0 */