package com.cedar.cp.ejb.impl.recalculate;

import com.cedar.cp.api.base.DuplicateNameValidationException;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.base.VersionValidationException;
import com.cedar.cp.dto.recalculate.RecalculateBatchTaskCK;
import com.cedar.cp.dto.recalculate.RecalculateBatchTaskPK;
import com.cedar.cp.dto.recalculate.RecalculateBatchTaskRespAreaCK;
import com.cedar.cp.dto.recalculate.RecalculateBatchTaskRespAreaPK;
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
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import javax.sql.DataSource;

public class RecalculateBatchTaskRespAreaDAO extends AbstractDAO {

	private static final long serialVersionUID = 2674567238102291982L;
	Log _log = new Log(getClass());
	protected RecalculateBatchTaskRespAreaEVO mDetails;

	public RecalculateBatchTaskRespAreaDAO(Connection connection) {
		super(connection);
	}

	public RecalculateBatchTaskRespAreaDAO() {
	}

	public RecalculateBatchTaskRespAreaDAO(DataSource ds) {
		super(ds);
	}

	protected RecalculateBatchTaskRespAreaPK getPK() {
		return mDetails.getPK();
	}

	public void setDetails(RecalculateBatchTaskRespAreaEVO details) {
		mDetails = details.deepClone();
	}

	private RecalculateBatchTaskRespAreaEVO getEvoFromJdbc(ResultSet resultSet_) throws SQLException {
		int col = 1;
		RecalculateBatchTaskRespAreaEVO evo = new RecalculateBatchTaskRespAreaEVO(resultSet_.getInt(col++), resultSet_.getInt(col++));
		return evo;
	}

	private int putEvoKeysToJdbc(RecalculateBatchTaskRespAreaEVO evo_, PreparedStatement stmt_, int startCol_) throws SQLException {
		int col = startCol_;
		stmt_.setInt(col++, evo_.getRecalculateBatchTaskId());
		stmt_.setInt(col++, evo_.getRecalculateBatchTaskRespAreaId());
		return col;
	}

	private int putEvoDataToJdbc(RecalculateBatchTaskRespAreaEVO evo_, PreparedStatement stmt_, int startCol_) throws SQLException {
		return startCol_;
	}

	protected void doLoad(RecalculateBatchTaskRespAreaPK pk) throws ValidationException {
		Timer timer = _log.isDebugEnabled() ? new Timer(_log) : null;

		PreparedStatement stmt = null;
		ResultSet resultSet = null;
		try {
			stmt = getConnection().prepareStatement("select * from RECALCULATE_BATCH_RESP_AREA where RECALCULATE_BATCH_ID = ?");

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
			throw handleSQLException(pk, "select * from RECALCULATE_BATCH_RESP_AREA where RECALCULATE_BATCH_ID = ?", sqle);
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
			stmt = getConnection().prepareStatement("insert into RECALCULATE_BATCH_RESP_AREA values ( ?,? )");

			int col = 1;
			col = putEvoKeysToJdbc(mDetails, stmt, col);
			col = putEvoDataToJdbc(mDetails, stmt, col);

			int resultCount = stmt.executeUpdate();
			if (resultCount != 1) {
				throw new RuntimeException(getEntityName() + " insert failed (" + mDetails.getPK() + "): resultCount=" + resultCount);
			}

			mDetails.reset();
		} catch (SQLException sqle) {
			throw handleSQLException(mDetails.getPK(), "insert into RECALCULATE_BATCH_RESP_AREA values ( ?,? )", sqle);
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
				mDetails = ((RecalculateBatchTaskRespAreaEVO) iter2.next());

				if (mDetails.deletePending()) {
					somethingChanged = true;

					if (deleteStmt == null) {
						deleteStmt = getConnection().prepareStatement("delete from RECALCULATE_BATCH_RESP_AREA where RECALCULATE_BATCH_ID = ? AND RECALCULATE_BATCH_RESP_AREA_ID = ? ");
					}

					int col = 1;
					deleteStmt.setInt(col++, mDetails.getRecalculateBatchTaskId());
					deleteStmt.setInt(col++, mDetails.getRecalculateBatchTaskRespAreaId());

					if (_log.isDebugEnabled()) {
						_log.debug("update", "RecalculateBatchTaskRespArea deleting RecalculateBatchTaskId=" + mDetails.getRecalculateBatchTaskId() + ",RecalculateBatchTaskRespAreaId=" + mDetails.getRecalculateBatchTaskRespAreaId());
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
				mDetails = ((RecalculateBatchTaskRespAreaEVO) iter1.next());

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
			throw handleSQLException("delete from RECALCULATE_BATCH_RESP_AREA where RECALCULATE_BATCH_ID = ? AND RECALCULATE_BATCH_RESP_AREA_ID = ? ", sqle);
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
			PreparedStatement deleteStmt = getConnection().prepareStatement("delete from RECALCULATE_BATCH_RESP_AREA where RECALCULATE_BATCH_ID = ? ");
			deleteStmt.setInt(1, recalculateBatchTaskPk.getRecalculateBatchTaskId());
			deleteStmt.execute();
			closeStatement(deleteStmt);
		} catch (SQLException e) {
			throw handleSQLException("delete from RECALCULATE_BATCH_RESP_AREA where RECALCULATE_BATCH_ID = ? ", e);
		} finally {			
			closeConnection();
		}
	}
	
	public void bulkGetAll(RecalculateBatchTaskPK entityPK, RecalculateBatchTaskEVO owningEVO, String dependants) {
		Timer timer = _log.isDebugEnabled() ? new Timer(_log) : null;

		PreparedStatement stmt = null;
		ResultSet resultSet = null;

		int itemCount = 0;

		List theseItems = new ArrayList();
		owningEVO.setRecalculateBatchTaskRespArea(theseItems);
		try {
			stmt = getConnection().prepareStatement("select * from RECALCULATE_BATCH_RESP_AREA where RECALCULATE_BATCH_ID = ? order by RECALCULATE_BATCH_RESP_AREA_ID");

			int col = 1;
			stmt.setInt(col++, entityPK.getRecalculateBatchTaskId());

			resultSet = stmt.executeQuery();

			while (resultSet.next()) {
				itemCount++;
				mDetails = getEvoFromJdbc(resultSet);

				theseItems.add(mDetails);
			}

			if (timer != null) {
				timer.logDebug("bulkGetAll", "items=" + itemCount);
			}
		} catch (SQLException sqle) {
			throw handleSQLException("select * from RECALCULATE_BATCH_RESP_AREA where RECALCULATE_BATCH_ID = ? order by RECALCULATE_BATCH_RESP_AREA_ID", sqle);
		} finally {
			closeResultSet(resultSet);
			closeStatement(stmt);
			closeConnection();

			mDetails = null;
		}
	}

	public Collection getAll(int selectRecalculateBatchTaskId, String dependants, Collection currentList) {
		Timer timer = _log.isDebugEnabled() ? new Timer(_log) : null;
		PreparedStatement stmt = null;
		ResultSet resultSet = null;

		ArrayList items = new ArrayList();
		try {
			stmt = getConnection().prepareStatement("select * from RECALCULATE_BATCH_RESP_AREA where RECALCULATE_BATCH_ID = ?");

			int col = 1;
			stmt.setInt(col++, selectRecalculateBatchTaskId);

			resultSet = stmt.executeQuery();

			while (resultSet.next()) {
				mDetails = getEvoFromJdbc(resultSet);

				items.add(mDetails);
			}

			if (currentList != null) {
				ListIterator iter = items.listIterator();
				RecalculateBatchTaskRespAreaEVO currentEVO = null;
				RecalculateBatchTaskRespAreaEVO newEVO = null;
				while (iter.hasNext()) {
					newEVO = (RecalculateBatchTaskRespAreaEVO) iter.next();
					Iterator iter2 = currentList.iterator();
					while (iter2.hasNext()) {
						currentEVO = (RecalculateBatchTaskRespAreaEVO) iter2.next();
						if (currentEVO.getPK().equals(newEVO.getPK())) {
							iter.set(currentEVO);
						}
					}

				}

				Iterator iter2 = currentList.iterator();
				while (iter2.hasNext()) {
					currentEVO = (RecalculateBatchTaskRespAreaEVO) iter2.next();
					if (currentEVO.insertPending()) {
						items.add(currentEVO);
					}
				}
			}
			mDetails = null;
		} catch (SQLException sqle) {
			throw handleSQLException("select * from RECALCULATE_BATCH_RESP_AREA where RECALCULATE_BATCH_ID = ?", sqle);
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

	public RecalculateBatchTaskRespAreaEVO getDetails(RecalculateBatchTaskCK paramCK, String dependants) throws ValidationException {
		Timer timer = _log.isDebugEnabled() ? new Timer(_log) : null;

		if (mDetails == null) {
			doLoad(((RecalculateBatchTaskRespAreaCK) paramCK).getRecalculateBatchTaskRespAreaPK());
		} else if (!mDetails.getPK().equals(((RecalculateBatchTaskRespAreaCK) paramCK).getRecalculateBatchTaskRespAreaPK())) {
			doLoad(((RecalculateBatchTaskRespAreaCK) paramCK).getRecalculateBatchTaskRespAreaPK());
		}

		RecalculateBatchTaskRespAreaEVO details = new RecalculateBatchTaskRespAreaEVO();
		details = mDetails.deepClone();

		if (timer != null) {
			timer.logDebug("getDetails", paramCK + " " + dependants);
		}
		return details;
	}

	public RecalculateBatchTaskRespAreaEVO getDetails(RecalculateBatchTaskCK paramCK, RecalculateBatchTaskRespAreaEVO paramEVO, String dependants) throws ValidationException {
		RecalculateBatchTaskRespAreaEVO savedEVO = mDetails;
		mDetails = paramEVO;
		RecalculateBatchTaskRespAreaEVO newEVO = getDetails(paramCK, dependants);
		mDetails = savedEVO;
		return newEVO;
	}

	public RecalculateBatchTaskRespAreaEVO getDetails(String dependants) throws ValidationException {
		Timer timer = _log.isDebugEnabled() ? new Timer(_log) : null;

		RecalculateBatchTaskRespAreaEVO details = mDetails.deepClone();

		if (timer != null) {
			timer.logDebug("getDetails", mDetails.getPK() + " " + dependants);
		}
		return details;
	}

	public String getEntityName() {
		return "RecalculateBatchTaskRespArea";
	}
}