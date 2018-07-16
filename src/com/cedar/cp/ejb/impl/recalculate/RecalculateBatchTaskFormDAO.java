package com.cedar.cp.ejb.impl.recalculate;

import com.cedar.cp.api.base.DuplicateNameValidationException;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.base.VersionValidationException;
import com.cedar.cp.dto.recalculate.RecalculateBatchTaskCK;
import com.cedar.cp.dto.recalculate.RecalculateBatchTaskFormCK;
import com.cedar.cp.dto.recalculate.RecalculateBatchTaskFormPK;
import com.cedar.cp.dto.recalculate.RecalculateBatchTaskPK;
import com.cedar.cp.ejb.impl.base.AbstractDAO;
import com.cedar.cp.util.Log;
import com.cedar.cp.util.Timer;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.Map;
import javax.sql.DataSource;

public class RecalculateBatchTaskFormDAO extends AbstractDAO {
	Log _log = new Log(getClass());
	protected RecalculateBatchTaskFormEVO mDetails;

	public RecalculateBatchTaskFormDAO(Connection connection) {
		super(connection);
	}

	public RecalculateBatchTaskFormDAO() {
	}

	public RecalculateBatchTaskFormDAO(DataSource ds) {
		super(ds);
	}

	protected RecalculateBatchTaskFormPK getPK() {
		return mDetails.getPK();
	}

	public void setDetails(RecalculateBatchTaskFormEVO details) {
		mDetails = details.deepClone();
	}

	private RecalculateBatchTaskFormEVO getEvoFromJdbc(ResultSet resultSet_) throws SQLException {
		int col = 1;
		RecalculateBatchTaskFormEVO evo = new RecalculateBatchTaskFormEVO(resultSet_.getInt(col++), resultSet_.getInt(col++));
		return evo;
	}

	private int putEvoKeysToJdbc(RecalculateBatchTaskFormEVO evo_, PreparedStatement stmt_, int startCol_) throws SQLException {
		int col = startCol_;
		stmt_.setInt(col++, evo_.getRecalculateBatchTaskId());
		stmt_.setInt(col++, evo_.getRecalculateBatchTaskFormId());
		return col;
	}

	private int putEvoDataToJdbc(RecalculateBatchTaskFormEVO evo_, PreparedStatement stmt_, int startCol_) throws SQLException {
		return startCol_;
	}

	protected void doLoad(RecalculateBatchTaskFormPK pk) throws ValidationException {
		Timer timer = _log.isDebugEnabled() ? new Timer(_log) : null;

		PreparedStatement stmt = null;
		ResultSet resultSet = null;
		try {
			stmt = getConnection().prepareStatement("select * from RECALCULATE_BATCH_FORM where RECALCULATE_BATCH_ID = ?");

			int col = 1;
			stmt.setInt(col++, pk.getRecalculateBatchTaskId());

			resultSet = stmt.executeQuery();

			if (!resultSet.next()) {
				throw new ValidationException(getEntityName() + " select of " + pk + " not found");
			}

			mDetails = getEvoFromJdbc(resultSet);
			if (mDetails.isModified())
				_log.info("doLoad", mDetails);
		} catch (SQLException sqle) {
			throw handleSQLException(pk, "select * from RECALCULATE_BATCH_FORM where RECALCULATE_BATCH_ID = ?", sqle);
		} finally {
			closeResultSet(resultSet);
			closeStatement(stmt);
			closeConnection();

			if (timer != null)
				timer.logDebug("doLoad", pk);
		}
	}

	protected void doCreate() throws DuplicateNameValidationException, ValidationException {
		Timer timer = _log.isDebugEnabled() ? new Timer(_log) : null;
		PreparedStatement stmt = null;
		try {
			stmt = getConnection().prepareStatement("insert into RECALCULATE_BATCH_FORM values ( ?,? )");

			int col = 1;
			col = putEvoKeysToJdbc(mDetails, stmt, col);
			col = putEvoDataToJdbc(mDetails, stmt, col);

			int resultCount = stmt.executeUpdate();
			if (resultCount != 1) {
				throw new RuntimeException(getEntityName() + " insert failed (" + mDetails.getPK() + "): resultCount=" + resultCount);
			}

			mDetails.reset();
		} catch (SQLException sqle) {
			throw handleSQLException(mDetails.getPK(), "insert into RECALCULATE_BATCH_FORM values ( ?,? )", sqle);
		} finally {
			closeStatement(stmt);
			closeConnection();

			if (timer != null)
				timer.logDebug("doCreate", mDetails.toString());
		}
	}

	protected void doStore() throws DuplicateNameValidationException, VersionValidationException, ValidationException {
		
	}

	public boolean update(Map items) throws DuplicateNameValidationException, VersionValidationException, ValidationException {
		if (items == null) {
			return false;
		}
		Timer timer = _log.isDebugEnabled() ? new Timer(_log) : null;
		PreparedStatement deleteStmt = null;

		boolean somethingChanged = false;
		try {
			Iterator iter2 = new ArrayList(items.values()).iterator();
			while (iter2.hasNext()) {
				mDetails = ((RecalculateBatchTaskFormEVO) iter2.next());

				if (mDetails.deletePending()) {
					somethingChanged = true;

					if (deleteStmt == null) {
						deleteStmt = getConnection().prepareStatement("delete from RECALCULATE_BATCH_FORM where RECALCULATE_BATCH_ID = ? AND RECALCULATE_BATCH_FORM_ID = ? ");
					}

					int col = 1;
					deleteStmt.setInt(col++, mDetails.getRecalculateBatchTaskId());
					deleteStmt.setInt(col++, mDetails.getRecalculateBatchTaskFormId());

					if (_log.isDebugEnabled()) {
						_log.debug("update", "RecalculateBatchTaskForm deleting RecalculateBatchTaskId=" + mDetails.getRecalculateBatchTaskId() + ",RecalculateBatchTaskFormId=" + mDetails.getRecalculateBatchTaskFormId());
					}

					deleteStmt.addBatch();

					items.remove(mDetails.getPK());
				}

			}

			if (deleteStmt != null) {
				Timer timer2 = _log.isDebugEnabled() ? new Timer(_log) : null;

				deleteStmt.executeBatch();

				if (timer2 != null) {
					timer2.logDebug("update", "delete batch");
				}
			}

			Iterator iter1 = items.values().iterator();
			while (iter1.hasNext()) {
				mDetails = ((RecalculateBatchTaskFormEVO) iter1.next());

				if (mDetails.insertPending()) {
					somethingChanged = true;
					doCreate();
				} else if (mDetails.isModified()) {
					somethingChanged = true;
					doStore();
				}

			}

			return somethingChanged;
		} catch (SQLException sqle) {
			throw handleSQLException("delete from RECALCULATE_BATCH_FORM where RECALCULATE_BATCH_ID = ? AND RECALCULATE_BATCH_FORM_ID = ? ", sqle);
		} finally {
			if (deleteStmt != null) {
				closeStatement(deleteStmt);
				closeConnection();
			}

			mDetails = null;

			if ((somethingChanged) && (timer != null))
				timer.logDebug("update", "collection");
		}
	}
	
	public void remove(RecalculateBatchTaskPK recalculateBatchTaskPk) {	
		try {
			PreparedStatement deleteStmt = getConnection().prepareStatement("delete from RECALCULATE_BATCH_FORM where RECALCULATE_BATCH_ID = ? ");
			deleteStmt.setInt(1, recalculateBatchTaskPk.getRecalculateBatchTaskId());
			deleteStmt.execute();
			closeStatement(deleteStmt);
		} catch (SQLException e) {
			throw handleSQLException("delete from RECALCULATE_BATCH_FORM where RECALCULATE_BATCH_ID = ? ", e);
		} finally {			
			closeConnection();
		}
		
	}

	public Collection getAll(int selectRecalculateBatchTaskId, String dependants, Collection currentList) {
		Timer timer = _log.isDebugEnabled() ? new Timer(_log) : null;
		PreparedStatement stmt = null;
		ResultSet resultSet = null;

		ArrayList items = new ArrayList();
		try {
			stmt = getConnection().prepareStatement("select * from RECALCULATE_BATCH_FORM where RECALCULATE_BATCH_ID = ?");

			int col = 1;
			stmt.setInt(col++, selectRecalculateBatchTaskId);

			resultSet = stmt.executeQuery();

			while (resultSet.next()) {
				mDetails = getEvoFromJdbc(resultSet);

				items.add(mDetails);
			}

			if (currentList != null) {
				ListIterator iter = items.listIterator();
				RecalculateBatchTaskFormEVO currentEVO = null;
				RecalculateBatchTaskFormEVO newEVO = null;
				while (iter.hasNext()) {
					newEVO = (RecalculateBatchTaskFormEVO) iter.next();
					Iterator iter2 = currentList.iterator();
					while (iter2.hasNext()) {
						currentEVO = (RecalculateBatchTaskFormEVO) iter2.next();
						if (currentEVO.getPK().equals(newEVO.getPK())) {
							iter.set(currentEVO);
						}
					}

				}

				Iterator iter2 = currentList.iterator();
				while (iter2.hasNext()) {
					currentEVO = (RecalculateBatchTaskFormEVO) iter2.next();
					if (currentEVO.insertPending()) {
						items.add(currentEVO);
					}
				}
			}
			mDetails = null;
		} catch (SQLException sqle) {
			throw handleSQLException("select * from RECALCULATE_BATCH_FORM where RECALCULATE_BATCH_ID = ?", sqle);
		} finally {
			closeResultSet(resultSet);
			closeStatement(stmt);
			closeConnection();

			if (timer != null) {
				timer.logDebug("getAll", " RecalculateBatchTaskId=" + selectRecalculateBatchTaskId + " items=" + items.size());
			}

		}

		return items;
	}

	public RecalculateBatchTaskFormEVO getDetails(RecalculateBatchTaskCK paramCK, String dependants) throws ValidationException {
		Timer timer = _log.isDebugEnabled() ? new Timer(_log) : null;

		if (mDetails == null) {
			doLoad(((RecalculateBatchTaskFormCK) paramCK).getRecalculateBatchTaskFormPK());
		} else if (!mDetails.getPK().equals(((RecalculateBatchTaskFormCK) paramCK).getRecalculateBatchTaskFormPK())) {
			doLoad(((RecalculateBatchTaskFormCK) paramCK).getRecalculateBatchTaskFormPK());
		}

		RecalculateBatchTaskFormEVO details = new RecalculateBatchTaskFormEVO();
		details = mDetails.deepClone();

		if (timer != null) {
			timer.logDebug("getDetails", paramCK + " " + dependants);
		}
		return details;
	}

	public RecalculateBatchTaskFormEVO getDetails(RecalculateBatchTaskCK paramCK, RecalculateBatchTaskFormEVO paramEVO, String dependants) throws ValidationException {
		RecalculateBatchTaskFormEVO savedEVO = mDetails;
		mDetails = paramEVO;
		RecalculateBatchTaskFormEVO newEVO = getDetails(paramCK, dependants);
		mDetails = savedEVO;
		return newEVO;
	}

	public RecalculateBatchTaskFormEVO getDetails(String dependants) throws ValidationException {
		Timer timer = _log.isDebugEnabled() ? new Timer(_log) : null;

		RecalculateBatchTaskFormEVO details = mDetails.deepClone();

		if (timer != null) {
			timer.logDebug("getDetails", mDetails.getPK() + " " + dependants);
		}
		return details;
	}

	public String getEntityName() {
		return "RecalculateBatchTaskForm";
	}
}