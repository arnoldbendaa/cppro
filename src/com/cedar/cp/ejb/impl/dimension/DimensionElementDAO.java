package com.cedar.cp.ejb.impl.dimension;

import com.cedar.cp.api.base.DuplicateNameValidationException;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.base.VersionValidationException;
import com.cedar.cp.dto.dimension.AllDimensionElementsELO;
import com.cedar.cp.dto.dimension.AllDimensionElementsForDimensionELO;
import com.cedar.cp.dto.dimension.AllDimensionElementsForModelELO;
import com.cedar.cp.dto.dimension.DimensionCK;
import com.cedar.cp.dto.dimension.DimensionElementCK;
import com.cedar.cp.dto.dimension.DimensionElementPK;
import com.cedar.cp.dto.dimension.DimensionElementRefImpl;
import com.cedar.cp.dto.dimension.DimensionPK;
import com.cedar.cp.dto.dimension.DimensionRefImpl;
import com.cedar.cp.ejb.impl.base.AbstractDAO;
import com.cedar.cp.util.Log;
import com.cedar.cp.util.Timer;

import cppro.utils.MyLog;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

import javax.sql.DataSource;

public class DimensionElementDAO extends AbstractDAO {
    Log _log = new Log(getClass());
    private static final String SQL_SELECT_COLUMNS = "select DIMENSION_ELEMENT.DIMENSION_ELEMENT_ID,DIMENSION_ELEMENT.DIMENSION_ID,DIMENSION_ELEMENT.VIS_ID,DIMENSION_ELEMENT.DESCRIPTION,DIMENSION_ELEMENT.CREDIT_DEBIT,DIMENSION_ELEMENT.DISABLED,DIMENSION_ELEMENT.NOT_PLANNABLE,DIMENSION_ELEMENT.AUG_CREDIT_DEBIT";
    protected static final String SQL_LOAD = " from DIMENSION_ELEMENT where    DIMENSION_ELEMENT_ID = ? ";
    protected static final String SQL_CREATE = "insert into DIMENSION_ELEMENT ( DIMENSION_ELEMENT_ID,DIMENSION_ID,VIS_ID,DESCRIPTION,CREDIT_DEBIT,DISABLED,NOT_PLANNABLE,AUG_CREDIT_DEBIT) values ( ?,?,?,?,?,?,?,?)";
    protected static final String SQL_STORE = "update DIMENSION_ELEMENT set DIMENSION_ID = ?,VIS_ID = ?,DESCRIPTION = ?,CREDIT_DEBIT = ?,DISABLED = ?,NOT_PLANNABLE = ?,AUG_CREDIT_DEBIT = ? where    DIMENSION_ELEMENT_ID = ? ";
    protected static String SQL_ALL_DIMENSION_ELEMENTS = "select 0       ,DIMENSION.DIMENSION_ID      ,DIMENSION.VIS_ID      ,DIMENSION.TYPE      ,DIMENSION_ELEMENT.DIMENSION_ELEMENT_ID      ,DIMENSION_ELEMENT.VIS_ID      ,DIMENSION_ELEMENT.CREDIT_DEBIT from DIMENSION_ELEMENT    ,DIMENSION where 1=1   and DIMENSION_ELEMENT.DIMENSION_ID = DIMENSION.DIMENSION_ID ";

    protected static String SQL_ALL_DIMENSION_ELEMENTS_FOR_DIMENSION = "select 0       ,DIMENSION.DIMENSION_ID      ,DIMENSION.VIS_ID      ,DIMENSION.TYPE      ,DIMENSION_ELEMENT.DIMENSION_ELEMENT_ID      ,DIMENSION_ELEMENT.VIS_ID      ,DIMENSION_ELEMENT.CREDIT_DEBIT      ,DIMENSION_ELEMENT.DESCRIPTION from DIMENSION_ELEMENT    ,DIMENSION where 1=1   and DIMENSION_ELEMENT.DIMENSION_ID = DIMENSION.DIMENSION_ID  and  DIMENSION_ELEMENT.DIMENSION_ID = ? order by DIMENSION_ELEMENT.VIS_ID";
    protected static final String SQL_DELETE_BATCH = "delete from DIMENSION_ELEMENT where    DIMENSION_ELEMENT_ID = ? ";
    public static final String SQL_BULK_GET_ALL = " from DIMENSION_ELEMENT where 1=1 and DIMENSION_ELEMENT.DIMENSION_ID = ? order by  DIMENSION_ELEMENT.DIMENSION_ELEMENT_ID";
    protected static final String SQL_GET_ALL = " from DIMENSION_ELEMENT where    DIMENSION_ID = ? ";
    protected static final String SQL_DIMENSION_ELEMENT_ROLLUP_PREFIX = "select DIMENSION_ID, COUNT(*) from DIMENSION_ELEMENT  where DATA_ROLLUP_OTHER_DIMENSIONS = ' '  and DIMENSION_ID in ( ";
    protected static final String SQL_DIMENSION_ELEMENT_ROLLUP_POSTFIX = " ) group by DIMENSION_ID ";
    protected static final String SQL_ALL_DIMENSION_ELEMENT_COUNTS = "select DIMENSION_ID, count(*)-1 from DIMENSION_ELEMENT  group by DIMENSION_ID";
    private static final String DIMENSION_ELEMENT_EXIST = "select 1\n  from dimension_element d\n where d.dimension_id = ? and \n       d.dimension_element_id = ?";
    private static final String LOAD_NULL_ELEMENT_SQL = "select DIMENSION_ELEMENT.DIMENSION_ELEMENT_ID,DIMENSION_ELEMENT.DIMENSION_ID,DIMENSION_ELEMENT.VIS_ID,DIMENSION_ELEMENT.DESCRIPTION,DIMENSION_ELEMENT.CREDIT_DEBIT,DIMENSION_ELEMENT.DISABLED,DIMENSION_ELEMENT.NOT_PLANNABLE,DIMENSION_ELEMENT.AUG_CREDIT_DEBIT\nfrom dimension, dimension_element\nwhere dimension.dimension_id = ? and \n      dimension_element.dimension_id = dimension.dimension_id and\n      dimension.null_element_id = dimension_element.dimension_element_id";
    protected DimensionElementEVO mDetails;

    public DimensionElementDAO(Connection connection) {
        super(connection);
    }

    public DimensionElementDAO() {
    }

    public DimensionElementDAO(DataSource ds) {
        super(ds);
    }

    protected DimensionElementPK getPK() {
        return this.mDetails.getPK();
    }

    public void setDetails(DimensionElementEVO details) {
        this.mDetails = details.deepClone();
    }

    private DimensionElementEVO getEvoFromJdbc(ResultSet resultSet_) throws SQLException {
        int col = 1;
        DimensionElementEVO evo = new DimensionElementEVO(resultSet_.getInt(col++), resultSet_.getInt(col++), resultSet_.getString(col++), resultSet_.getString(col++), resultSet_.getInt(col++), resultSet_.getString(col++).equals("Y"), resultSet_.getString(col++).equals("Y"), getWrappedIntegerFromJdbc(resultSet_, col++));

        return evo;
    }

    private int putEvoKeysToJdbc(DimensionElementEVO evo_, PreparedStatement stmt_, int startCol_) throws SQLException {
        int col = startCol_;
        stmt_.setInt(col++, evo_.getDimensionElementId());
        return col;
    }

    private int putEvoDataToJdbc(DimensionElementEVO evo_, PreparedStatement stmt_, int startCol_) throws SQLException {
        int col = startCol_;
        stmt_.setInt(col++, evo_.getDimensionId());
        stmt_.setString(col++, evo_.getVisId());
        stmt_.setString(col++, evo_.getDescription());
        stmt_.setInt(col++, evo_.getCreditDebit());
        if (evo_.getDisabled())
            stmt_.setString(col++, "Y");
        else
            stmt_.setString(col++, " ");
        if (evo_.getNotPlannable())
            stmt_.setString(col++, "Y");
        else
            stmt_.setString(col++, " ");
        setWrappedPrimitiveToJdbc(stmt_, col++, evo_.getAugCreditDebit());
        return col;
    }

    protected void doLoad(DimensionElementPK pk) throws ValidationException {
        Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;

        PreparedStatement stmt = null;
        ResultSet resultSet = null;
        try {
            stmt = getConnection().prepareStatement("select DIMENSION_ELEMENT.DIMENSION_ELEMENT_ID,DIMENSION_ELEMENT.DIMENSION_ID,DIMENSION_ELEMENT.VIS_ID,DIMENSION_ELEMENT.DESCRIPTION,DIMENSION_ELEMENT.CREDIT_DEBIT,DIMENSION_ELEMENT.DISABLED,DIMENSION_ELEMENT.NOT_PLANNABLE,DIMENSION_ELEMENT.AUG_CREDIT_DEBIT from DIMENSION_ELEMENT where    DIMENSION_ELEMENT_ID = ? ");

            int col = 1;
            stmt.setInt(col++, pk.getDimensionElementId());

            resultSet = stmt.executeQuery();

            if (!resultSet.next()) {
                throw new ValidationException(getEntityName() + " select of " + pk + " not found");
            }

            this.mDetails = getEvoFromJdbc(resultSet);
            if (this.mDetails.isModified())
                this._log.info("doLoad", this.mDetails);
        } catch (SQLException sqle) {
            throw handleSQLException(pk, "select DIMENSION_ELEMENT.DIMENSION_ELEMENT_ID,DIMENSION_ELEMENT.DIMENSION_ID,DIMENSION_ELEMENT.VIS_ID,DIMENSION_ELEMENT.DESCRIPTION,DIMENSION_ELEMENT.CREDIT_DEBIT,DIMENSION_ELEMENT.DISABLED,DIMENSION_ELEMENT.NOT_PLANNABLE,DIMENSION_ELEMENT.AUG_CREDIT_DEBIT from DIMENSION_ELEMENT where    DIMENSION_ELEMENT_ID = ? ", sqle);
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
        PreparedStatement stmt = null;
        try {
            stmt = getConnection().prepareStatement("insert into DIMENSION_ELEMENT ( DIMENSION_ELEMENT_ID,DIMENSION_ID,VIS_ID,DESCRIPTION,CREDIT_DEBIT,DISABLED,NOT_PLANNABLE,AUG_CREDIT_DEBIT) values ( ?,?,?,?,?,?,?,?)");

            int col = 1;
            col = putEvoKeysToJdbc(this.mDetails, stmt, col);
            col = putEvoDataToJdbc(this.mDetails, stmt, col);

            int resultCount = stmt.executeUpdate();
            if (resultCount != 1) {
                throw new RuntimeException(getEntityName() + " insert failed (" + this.mDetails.getPK() + "): resultCount=" + resultCount);
            }

            this.mDetails.reset();
        } catch (SQLException sqle) {
            throw handleSQLException(this.mDetails.getPK(), "insert into DIMENSION_ELEMENT ( DIMENSION_ELEMENT_ID,DIMENSION_ID,VIS_ID,DESCRIPTION,CREDIT_DEBIT,DISABLED,NOT_PLANNABLE,AUG_CREDIT_DEBIT) values ( ?,?,?,?,?,?,?,?)", sqle);
        } finally {
            closeStatement(stmt);
            closeConnection();

            if (timer != null)
                timer.logDebug("doCreate", this.mDetails.toString());
        }
    }

    protected void doStore() throws DuplicateNameValidationException, VersionValidationException, ValidationException {
        Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;

        PreparedStatement stmt = null;

        boolean mainChanged = this.mDetails.isModified();
        boolean dependantChanged = false;
        try {
            if ((mainChanged) || (dependantChanged)) {
                stmt = getConnection().prepareStatement("update DIMENSION_ELEMENT set DIMENSION_ID = ?,VIS_ID = ?,DESCRIPTION = ?,CREDIT_DEBIT = ?,DISABLED = ?,NOT_PLANNABLE = ?,AUG_CREDIT_DEBIT = ? where    DIMENSION_ELEMENT_ID = ? ");

                int col = 1;
                col = putEvoDataToJdbc(this.mDetails, stmt, col);
                col = putEvoKeysToJdbc(this.mDetails, stmt, col);

                int resultCount = stmt.executeUpdate();

                if (resultCount != 1) {
                    throw new RuntimeException(getEntityName() + " update failed (" + getPK() + "): resultCount=" + resultCount);
                }

                this.mDetails.reset();
            }

        } catch (SQLException sqle) {
            throw handleSQLException(getPK(), "update DIMENSION_ELEMENT set DIMENSION_ID = ?,VIS_ID = ?,DESCRIPTION = ?,CREDIT_DEBIT = ?,DISABLED = ?,NOT_PLANNABLE = ?,AUG_CREDIT_DEBIT = ? where    DIMENSION_ELEMENT_ID = ? ", sqle);
        } finally {
            closeStatement(stmt);
            closeConnection();

            if ((timer != null) && ((mainChanged) || (dependantChanged)))
                timer.logDebug("store", this.mDetails.getPK() + "(" + mainChanged + "," + dependantChanged + ")");
        }
    }

    public AllDimensionElementsELO getAllDimensionElements() {
        Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
        PreparedStatement stmt = null;
        ResultSet resultSet = null;
        AllDimensionElementsELO results = new AllDimensionElementsELO();
        try {
            stmt = getConnection().prepareStatement(SQL_ALL_DIMENSION_ELEMENTS);
            int col = 1;
            resultSet = stmt.executeQuery();
            while (resultSet.next()) {
                col = 2;

                DimensionPK pkDimension = new DimensionPK(resultSet.getInt(col++));

                String textDimension = resultSet.getString(col++);
                int erDimensionType = resultSet.getInt(col++);

                DimensionElementPK pkDimensionElement = new DimensionElementPK(resultSet.getInt(col++));

                String textDimensionElement = resultSet.getString(col++);
                int erDimensionElementCreditDebit = resultSet.getInt(col++);

                DimensionElementCK ckDimensionElement = new DimensionElementCK(pkDimension, pkDimensionElement);

                DimensionRefImpl erDimension = new DimensionRefImpl(pkDimension, textDimension, erDimensionType);

                DimensionElementRefImpl erDimensionElement = new DimensionElementRefImpl(ckDimensionElement, textDimensionElement, erDimensionElementCreditDebit);

                results.add(erDimensionElement, erDimension);
            }

        } catch (SQLException sqle) {
            throw handleSQLException(SQL_ALL_DIMENSION_ELEMENTS, sqle);
        } finally {
            closeResultSet(resultSet);
            closeStatement(stmt);
            closeConnection();
        }

        if (timer != null) {
            timer.logDebug("getAllDimensionElements", " items=" + results.size());
        }

        return results;
    }

    public AllDimensionElementsForDimensionELO getAllDimensionElementsForDimension(int param1) {
        Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
        PreparedStatement stmt = null;
        ResultSet resultSet = null;
        AllDimensionElementsForDimensionELO results = new AllDimensionElementsForDimensionELO();
        try {
            stmt = getConnection().prepareStatement(SQL_ALL_DIMENSION_ELEMENTS_FOR_DIMENSION);
            int col = 1;
            stmt.setInt(col++, param1);
            resultSet = stmt.executeQuery();
            while (resultSet.next()) {
                col = 2;

                DimensionPK pkDimension = new DimensionPK(resultSet.getInt(col++));

                String textDimension = resultSet.getString(col++);
                int erDimensionType = resultSet.getInt(col++);

                DimensionElementPK pkDimensionElement = new DimensionElementPK(resultSet.getInt(col++));

                String textDimensionElement = resultSet.getString(col++);
                int erDimensionElementCreditDebit = resultSet.getInt(col++);

                DimensionElementCK ckDimensionElement = new DimensionElementCK(pkDimension, pkDimensionElement);

                DimensionRefImpl erDimension = new DimensionRefImpl(pkDimension, textDimension, erDimensionType);

                DimensionElementRefImpl erDimensionElement = new DimensionElementRefImpl(ckDimensionElement, textDimensionElement, erDimensionElementCreditDebit);

                String col1 = resultSet.getString(col++);

                results.add(erDimensionElement, erDimension, col1);
            }

        } catch (SQLException sqle) {
            throw handleSQLException(SQL_ALL_DIMENSION_ELEMENTS_FOR_DIMENSION, sqle);
        } finally {
            closeResultSet(resultSet);
            closeStatement(stmt);
            closeConnection();
        }

        if (timer != null) {
            timer.logDebug("getAllDimensionElementsForDimension", " DimensionId=" + param1 + " items=" + results.size());
        }

        return results;
    }
    
    public AllDimensionElementsForModelELO getAllDimensionElementsForModels(List<Integer> modelIds) {
        Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
        AllDimensionElementsForModelELO results = new AllDimensionElementsForModelELO();
        
        if (modelIds == null || modelIds.size() <= 0) {
            return results;
        }
        
        String models = "";
        for (int i = 0; i < modelIds.size(); i++) {
            models += modelIds.get(i);
            if (i != (modelIds.size() - 1)) {
                models += ",";
            }            
        }
        
        String sql = "SELECT mdr.MODEL_ID, de.DIMENSION_ELEMENT_ID, de.VIS_ID, d.TYPE FROM dimension_element de, dimension d, model_dimension_rel mdr "
                + "WHERE de.DIMENSION_ID = mdr.DIMENSION_ID AND d.DIMENSION_ID = de.DIMENSION_ID "
                + "AND mdr.MODEL_ID IN ("+ models +") ORDER BY mdr.MODEl_ID, d.TYPE, de.VIS_ID";
        
        PreparedStatement stmt = null;
        ResultSet resultSet = null;
        
        try {
            stmt = getConnection().prepareStatement(sql);
            resultSet = stmt.executeQuery();
            
            while (resultSet.next()) {
                int col = 1;
                int modelId = resultSet.getInt(col++);
                int dimensionElementId = resultSet.getInt(col++);
                String dimensionElementVisId = resultSet.getString(col++);
                int dimensionType = resultSet.getInt(col++);
                results.add(modelId, dimensionElementId, dimensionElementVisId, dimensionType);
            }

        } catch (SQLException ex) {
            throw handleSQLException(sql, ex);
        } finally {
            closeResultSet(resultSet);
            closeStatement(stmt);
            closeConnection();
        }

        if (timer != null) {
            timer.logDebug("getAllDimensionElementsForModels", " Models = " + modelIds);
        }

        return results;
    }

    public boolean update(Map items) throws DuplicateNameValidationException, VersionValidationException, ValidationException {
        if (items == null) {
            return false;
        }
        Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
        PreparedStatement deleteStmt = null;

        boolean somethingChanged = false;
        try {
            Iterator iter2 = new ArrayList(items.values()).iterator();
            while (iter2.hasNext()) {
                this.mDetails = ((DimensionElementEVO) iter2.next());

                if (!this.mDetails.deletePending())
                    continue;
                somethingChanged = true;

                if (deleteStmt == null) {
                    deleteStmt = getConnection().prepareStatement("delete from DIMENSION_ELEMENT where    DIMENSION_ELEMENT_ID = ? ");
                }

                int col = 1;
                deleteStmt.setInt(col++, this.mDetails.getDimensionElementId());

                if (this._log.isDebugEnabled()) {
                    this._log.debug("update", "DimensionElement deleting DimensionElementId=" + this.mDetails.getDimensionElementId());
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
                this.mDetails = ((DimensionElementEVO) iter1.next());

                if (this.mDetails.insertPending()) {
                    somethingChanged = true;
                    doCreate();
                    continue;
                }

                if (!this.mDetails.isModified())
                    continue;
                somethingChanged = true;
                doStore();
            }

            boolean bool1 = somethingChanged;
            return bool1;
        } catch (SQLException sqle) {
            throw handleSQLException("delete from DIMENSION_ELEMENT where    DIMENSION_ELEMENT_ID = ? ", sqle);
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

    public void bulkGetAll(DimensionPK entityPK, DimensionEVO owningEVO, String dependants) {
        Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;

        PreparedStatement stmt = null;
        ResultSet resultSet = null;

        int itemCount = 0;

        Collection theseItems = new ArrayList();
        owningEVO.setElements(theseItems);
        owningEVO.setElementsAllItemsLoaded(true);
        try {
            stmt = getConnection().prepareStatement("select DIMENSION_ELEMENT.DIMENSION_ELEMENT_ID,DIMENSION_ELEMENT.DIMENSION_ID,DIMENSION_ELEMENT.VIS_ID,DIMENSION_ELEMENT.DESCRIPTION,DIMENSION_ELEMENT.CREDIT_DEBIT,DIMENSION_ELEMENT.DISABLED,DIMENSION_ELEMENT.NOT_PLANNABLE,DIMENSION_ELEMENT.AUG_CREDIT_DEBIT from DIMENSION_ELEMENT where 1=1 and DIMENSION_ELEMENT.DIMENSION_ID = ? order by  DIMENSION_ELEMENT.DIMENSION_ELEMENT_ID");

            int col = 1;
            stmt.setInt(col++, entityPK.getDimensionId());

            resultSet = stmt.executeQuery();

            resultSet.setFetchSize(5000);

            while (resultSet.next()) {
                itemCount++;
                this.mDetails = getEvoFromJdbc(resultSet);

                theseItems.add(this.mDetails);
            }

            if (timer != null) {
                timer.logDebug("bulkGetAll", "items=" + itemCount);
            }
        } catch (SQLException sqle) {
            throw handleSQLException("select DIMENSION_ELEMENT.DIMENSION_ELEMENT_ID,DIMENSION_ELEMENT.DIMENSION_ID,DIMENSION_ELEMENT.VIS_ID,DIMENSION_ELEMENT.DESCRIPTION,DIMENSION_ELEMENT.CREDIT_DEBIT,DIMENSION_ELEMENT.DISABLED,DIMENSION_ELEMENT.NOT_PLANNABLE,DIMENSION_ELEMENT.AUG_CREDIT_DEBIT from DIMENSION_ELEMENT where 1=1 and DIMENSION_ELEMENT.DIMENSION_ID = ? order by  DIMENSION_ELEMENT.DIMENSION_ELEMENT_ID", sqle);
        } finally {
            closeResultSet(resultSet);
            closeStatement(stmt);
            closeConnection();

            this.mDetails = null;
        }
    }

    public Collection getAll(int selectDimensionId, String dependants, Collection currentList) {
        Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
        PreparedStatement stmt = null;
        ResultSet resultSet = null;
        ArrayList items = new ArrayList();
        try {
            stmt = getConnection().prepareStatement("select DIMENSION_ELEMENT.DIMENSION_ELEMENT_ID,DIMENSION_ELEMENT.DIMENSION_ID,DIMENSION_ELEMENT.VIS_ID,DIMENSION_ELEMENT.DESCRIPTION,DIMENSION_ELEMENT.CREDIT_DEBIT,DIMENSION_ELEMENT.DISABLED,DIMENSION_ELEMENT.NOT_PLANNABLE,DIMENSION_ELEMENT.AUG_CREDIT_DEBIT from DIMENSION_ELEMENT where    DIMENSION_ID = ? ");

            int col = 1;
            stmt.setInt(col++, selectDimensionId);

            resultSet = stmt.executeQuery();

            while (resultSet.next()) {
                this.mDetails = getEvoFromJdbc(resultSet);

                items.add(this.mDetails);
            }

            if (currentList != null) {
                ListIterator iter = items.listIterator();
                DimensionElementEVO currentEVO = null;
                DimensionElementEVO newEVO = null;
                while (iter.hasNext()) {
                    newEVO = (DimensionElementEVO) iter.next();
                    Iterator iter2 = currentList.iterator();
                    while (iter2.hasNext()) {
                        currentEVO = (DimensionElementEVO) iter2.next();
                        if (!currentEVO.getPK().equals(newEVO.getPK()))
                            continue;
                        iter.set(currentEVO);
                    }

                }

                Iterator iter2 = currentList.iterator();
                while (iter2.hasNext()) {
                    currentEVO = (DimensionElementEVO) iter2.next();
                    if (currentEVO.insertPending()) {
                        items.add(currentEVO);
                    }
                }
            }
            this.mDetails = null;
        } catch (SQLException sqle) {
            throw handleSQLException("select DIMENSION_ELEMENT.DIMENSION_ELEMENT_ID,DIMENSION_ELEMENT.DIMENSION_ID,DIMENSION_ELEMENT.VIS_ID,DIMENSION_ELEMENT.DESCRIPTION,DIMENSION_ELEMENT.CREDIT_DEBIT,DIMENSION_ELEMENT.DISABLED,DIMENSION_ELEMENT.NOT_PLANNABLE,DIMENSION_ELEMENT.AUG_CREDIT_DEBIT from DIMENSION_ELEMENT where    DIMENSION_ID = ? ", sqle);
        } finally {
            closeResultSet(resultSet);
            closeStatement(stmt);
            closeConnection();

            if (timer != null) {
                timer.logDebug("getAll", " DimensionId=" + selectDimensionId + " items=" + items.size());
            }

        }

        return items;
    }

    public DimensionElementEVO getDetails(DimensionCK paramCK, String dependants) throws ValidationException {
        Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;

        if (this.mDetails == null) {
            doLoad(((DimensionElementCK) paramCK).getDimensionElementPK());
        } else if (!this.mDetails.getPK().equals(((DimensionElementCK) paramCK).getDimensionElementPK())) {
            doLoad(((DimensionElementCK) paramCK).getDimensionElementPK());
        }

        DimensionElementEVO details = new DimensionElementEVO();
        details = this.mDetails.deepClone();

        if (timer != null) {
            timer.logDebug("getDetails", paramCK + " " + dependants);
        }
        return details;
    }

    public DimensionElementEVO getDetails(DimensionCK paramCK, DimensionElementEVO paramEVO, String dependants) throws ValidationException {
        DimensionElementEVO savedEVO = this.mDetails;
        this.mDetails = paramEVO;
        DimensionElementEVO newEVO = getDetails(paramCK, dependants);
        this.mDetails = savedEVO;
        return newEVO;
    }

    public DimensionElementEVO getDetails(String dependants) throws ValidationException {
        Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;

        DimensionElementEVO details = this.mDetails.deepClone();

        if (timer != null) {
            timer.logDebug("getDetails", this.mDetails.getPK() + " " + dependants);
        }
        return details;
    }

    public String getEntityName() {
        return "DimensionElement";
    }

    public DimensionElementRefImpl getRef(DimensionElementPK paramDimensionElementPK) {
        Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
        PreparedStatement stmt = null;
        ResultSet resultSet = null;
        try {
            stmt = getConnection().prepareStatement("select 0,DIMENSION.DIMENSION_ID,DIMENSION_ELEMENT.VIS_ID,DIMENSION_ELEMENT.CREDIT_DEBIT from DIMENSION_ELEMENT,DIMENSION where 1=1 and DIMENSION_ELEMENT.DIMENSION_ELEMENT_ID = ? and DIMENSION_ELEMENT.DIMENSION_ID = DIMENSION.DIMENSION_ID");
            int col = 1;
            stmt.setInt(col++, paramDimensionElementPK.getDimensionElementId());

            resultSet = stmt.executeQuery();

            if (!resultSet.next()) {
                throw new RuntimeException(getEntityName() + " getRef " + paramDimensionElementPK + " not found");
            }
            col = 2;
            DimensionPK newDimensionPK = new DimensionPK(resultSet.getInt(col++));

            String textDimensionElement = resultSet.getString(col++);
            int erDimensionElementCreditDebit = resultSet.getInt(col++);
            DimensionElementCK ckDimensionElement = new DimensionElementCK(newDimensionPK, paramDimensionElementPK);

            DimensionElementRefImpl localDimensionElementRefImpl = new DimensionElementRefImpl(ckDimensionElement, textDimensionElement, erDimensionElementCreditDebit);
            return localDimensionElementRefImpl;
        } catch (SQLException sqle) {
            throw handleSQLException(paramDimensionElementPK, "select 0,DIMENSION.DIMENSION_ID,DIMENSION_ELEMENT.VIS_ID,DIMENSION_ELEMENT.CREDIT_DEBIT from DIMENSION_ELEMENT,DIMENSION where 1=1 and DIMENSION_ELEMENT.DIMENSION_ELEMENT_ID = ? and DIMENSION_ELEMENT.DIMENSION_ID = DIMENSION.DIMENSION_ID", sqle);
        } finally {
            closeResultSet(resultSet);
            closeStatement(stmt);
            closeConnection();

            if (timer != null)
                timer.logDebug("getRef", paramDimensionElementPK);
        }
    }

    public HashMap getDimRefreshMap(int[] dimIds) {
        PreparedStatement stmt = null;
        ResultSet resultSet = null;
        int dimCount = dimIds.length;

        StringBuffer sb = new StringBuffer();

        HashMap results = new HashMap();

        for (int i = 0; i < dimCount - 1; i++)
            sb.append(" ?,");
        sb.append(" ?");

        String fullSQLStatement = "select DIMENSION_ID, COUNT(*) from DIMENSION_ELEMENT  where DATA_ROLLUP_OTHER_DIMENSIONS = ' '  and DIMENSION_ID in ( " + sb.toString() + " ) group by DIMENSION_ID ";
        try {
            stmt = getConnection().prepareStatement(fullSQLStatement);
            int col = 1;

            for (int i = 0; i < dimIds.length; col++) {
                stmt.setInt(col, dimIds[i]);

                results.put(new Integer(dimIds[i]), new Boolean(false));

                i++;
            }

            resultSet = stmt.executeQuery();
            while (resultSet.next()) {
                col = 1;
                int dim = resultSet.getInt(col++);
                int count = resultSet.getInt(col++);

                results.put(new Integer(dim), new Boolean(count > 0));
            }
        } catch (SQLException sqle) {
            throw new RuntimeException(getEntityName() + " DimensionElementRollupCount", sqle);
        } finally {
            closeStatement(stmt);
            closeResultSet(resultSet);
            closeConnection();
        }

        return results;
    }

    public HashMap getAllDimensionElementCounts() {
        PreparedStatement stmt = null;
        ResultSet resultSet = null;
        HashMap hm = new HashMap();
        try {
            stmt = getConnection().prepareStatement("select DIMENSION_ID, count(*)-1 from DIMENSION_ELEMENT  group by DIMENSION_ID");

            resultSet = stmt.executeQuery();
            while (resultSet.next()) {
                int col = 1;
                int key = resultSet.getInt(col++);
                int count = resultSet.getInt(col++);
                hm.put(new DimensionPK(key), new Integer(count));
            }
        } catch (SQLException sqle) {
            throw new RuntimeException(getEntityName() + " AllDimensionElementCounts", sqle);
        } finally {
            closeStatement(stmt);
            closeResultSet(resultSet);
            closeConnection();
        }

        return hm;
    }

    public boolean doesDimensionElementExist(int dimensionId, int dimensionElementId) {
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = getConnection().prepareStatement("select 1\n  from dimension_element d\n where d.dimension_id = ? and \n       d.dimension_element_id = ?");
            ps.setInt(1, dimensionId);
            ps.setInt(2, dimensionElementId);
            rs = ps.executeQuery();

            boolean bool = rs.next();
            return bool;
        } catch (SQLException e) {
            throw handleSQLException("queryDimensionOwners", e);
        } finally {
            closeResultSet(rs);
            closeStatement(ps);
            closeConnection();
        } // throw localObject;
    }

    public DimensionElementEVO getNullElementEVO(int dimensionId) {
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = getConnection().prepareStatement(
                    "select DIMENSION_ELEMENT.DIMENSION_ELEMENT_ID,DIMENSION_ELEMENT.DIMENSION_ID,DIMENSION_ELEMENT.VIS_ID,DIMENSION_ELEMENT.DESCRIPTION,DIMENSION_ELEMENT.CREDIT_DEBIT,DIMENSION_ELEMENT.DISABLED,DIMENSION_ELEMENT.NOT_PLANNABLE,DIMENSION_ELEMENT.AUG_CREDIT_DEBIT\nfrom dimension, dimension_element\nwhere dimension.dimension_id = ? and \n      dimension_element.dimension_id = dimension.dimension_id and\n      dimension.null_element_id = dimension_element.dimension_element_id");
            ps.setInt(1, dimensionId);
            rs = ps.executeQuery();

            /* 1118 */DimensionElementEVO localDimensionElementEVO = null;
            if (rs.next()) {
                localDimensionElementEVO = getEvoFromJdbc(rs);
                return localDimensionElementEVO;
            }
            return localDimensionElementEVO;
        } catch (SQLException e) {
            throw handleSQLException("Failed to query dimensions' null element details", e);
        } finally {
            closeResultSet(rs);
            closeStatement(ps);
            closeConnection();
        } // throw localObject;
    }
}