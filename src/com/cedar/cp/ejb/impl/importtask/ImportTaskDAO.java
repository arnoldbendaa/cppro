package com.cedar.cp.ejb.impl.importtask;

import com.cedar.cp.api.importtask.ImportTaskRef;
import com.cedar.cp.api.base.DuplicateNameValidationException;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.base.VersionValidationException;
import com.cedar.cp.dto.admin.tidytask.TidyTaskPK;
import com.cedar.cp.dto.extsys.ExternalSystemPK;
import com.cedar.cp.dto.extsys.ExternalSystemRefImpl;
import com.cedar.cp.dto.importtask.AllImportTasksELO;
import com.cedar.cp.dto.importtask.ImportTaskCK;
import com.cedar.cp.dto.importtask.ImportTaskLinkCK;
import com.cedar.cp.dto.importtask.ImportTaskLinkPK;
import com.cedar.cp.dto.importtask.ImportTaskPK;
import com.cedar.cp.dto.importtask.ImportTaskRefImpl;
import com.cedar.cp.ejb.impl.base.AbstractDAO;
import com.cedar.cp.util.Log;
import com.cedar.cp.util.Timer;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.Set;
import javax.sql.DataSource;

public class ImportTaskDAO extends AbstractDAO
{
	Log _log = new Log(getClass());
	private static String[][] SQL_DELETE_CHILDREN = { { "TIDY_TASK_LINK", "delete from TIDY_TASK_LINK where     TIDY_TASK_LINK.TIDY_TASK_ID = ? " } };
	private static String[][] SQL_DELETE_CHILDRENS_DEPENDANTS = new String[0][];
	private static String SQL_DELETE_DEPENDANT_CRITERIA = "and TIDY_TASK.TIDY_TASK_ID = ?)";

	protected ImportTaskLinkDAO mImportTaskLinkDAO;
	protected ImportTaskEVO mDetails;

	public ImportTaskDAO(Connection connection) {
		super(connection);
	}

	public ImportTaskDAO() {
	}

	public ImportTaskDAO(DataSource ds) {
		super(ds);
	}

	protected ImportTaskPK getPK() {
		return mDetails.getPK();
	}

	public void setDetails(ImportTaskEVO details) {
		mDetails = details.deepClone();
	}

	public ImportTaskEVO setAndGetDetails(ImportTaskEVO details, String dependants) {
		setDetails(details);
		generateKeys();
		getDependants(mDetails, dependants);
		return mDetails.deepClone();
	}

	public ImportTaskPK create() throws DuplicateNameValidationException, ValidationException {
		doCreate();
		return mDetails.getPK();
	}

	public void load(ImportTaskPK pk) throws ValidationException {
		doLoad(pk);
	}

	public void store() throws DuplicateNameValidationException, VersionValidationException, ValidationException {
		doStore();
	}

	public void remove() {
		doRemove();
	}

	protected void doCreate() throws DuplicateNameValidationException, ValidationException {
	}

	public ImportTaskPK findByPrimaryKey(ImportTaskPK pk_) throws ValidationException {
		Timer timer = _log.isDebugEnabled() ? new Timer(_log) : null;
		if (exists(pk_)) {
			if (timer != null) {
				timer.logDebug("findByPrimaryKey", pk_);
			}
			return pk_;
		}

		throw new ValidationException(pk_ + " not found");
	}

	protected boolean exists(ImportTaskPK pk) {
		PreparedStatement stmt = null;
		ResultSet resultSet = null;
		boolean returnValue = false;
		try {
			stmt = getConnection().prepareStatement("select IMPORT_TASK_ID from IMPORT_TASK where IMPORT_TASK_ID = ?");

			int col = 1;
			stmt.setInt(col++, pk.getImportTaskId());

			resultSet = stmt.executeQuery();

			if (!resultSet.next())
				returnValue = false;
			else
				returnValue = true;
		} catch (SQLException sqle) {
			throw handleSQLException(pk, "select IMPORT_TASK_ID from IMPORT_TASK where IMPORT_TASK_ID = ?", sqle);
		} finally {
			closeResultSet(resultSet);
			closeStatement(stmt);
			closeConnection();
		}
		return returnValue;
	}

	private ImportTaskEVO getEvoFromJdbc(ResultSet resultSet_) throws SQLException {
		int col = 1;
		ImportTaskEVO evo = new ImportTaskEVO(resultSet_.getInt(col++), resultSet_.getString(col++), resultSet_.getString(col++), resultSet_.getInt(col++));
		return evo;
	}

	private int putEvoKeysToJdbc(ImportTaskEVO evo_, PreparedStatement stmt_, int startCol_) throws SQLException {
		int col = startCol_;
		stmt_.setInt(col++, evo_.getImportTaskId());
		return col;
	}

	private int putEvoDataToJdbc(ImportTaskEVO evo_, PreparedStatement stmt_, int startCol_) throws SQLException {
		int col = startCol_;
		stmt_.setString(col++, evo_.getVisId());
		stmt_.setString(col++, evo_.getDescription());
		stmt_.setInt(col++, evo_.getVersionNum());
		stmt_.setInt(col++, evo_.getUpdatedByUserId());
		stmt_.setTimestamp(col++, evo_.getUpdatedTime());
		stmt_.setTimestamp(col++, evo_.getCreatedTime());
		return col;
	}

	protected void doLoad(ImportTaskPK pk) throws ValidationException {
		Timer timer = _log.isDebugEnabled() ? new Timer(_log) : null;

		PreparedStatement stmt = null;
		ResultSet resultSet = null;
		try {
			stmt = getConnection().prepareStatement("select IMPORT_TASK.IMPORT_TASK_ID, IMPORT_TASK.VIS_ID, IMPORT_TASK.DESCRIPTION, IMPORT_TASK.EXTERNAL_SYSTEM_ID from IMPORT_TASK  where IMPORT_TASK_ID = ?");

			int col = 1;
			stmt.setInt(col++, pk.getImportTaskId());

			resultSet = stmt.executeQuery();

			if (!resultSet.next()) {
				throw new ValidationException(getEntityName() + " select of " + pk + " not found");
			}

			mDetails = getEvoFromJdbc(resultSet);
			if (mDetails.isModified())
				_log.info("doLoad", mDetails);
		} catch (SQLException sqle) {
			throw handleSQLException(pk, "select IMPORT_TASK.IMPORT_TASK_ID, IMPORT_TASK.VIS_ID, IMPORT_TASK.DESCRIPTION, IMPORT_TASK.EXTERNAL_SYSTEM_ID from IMPORT_TASK  where IMPORT_TASK_ID = ?", sqle);
		} finally {
			closeResultSet(resultSet);
			closeStatement(stmt);
			closeConnection();

			if (timer != null)
				timer.logDebug("doLoad", pk);
		}
	}

	public int reserveIds(int insertCount) {
		Timer timer = _log.isDebugEnabled() ? new Timer(_log) : null;

		PreparedStatement stmt = null;
		ResultSet resultSet = null;
		String sqlString = null;
		try {
			sqlString = "update IMPORT_TASK_SEQ set SEQ_NUM = SEQ_NUM + ?";
			stmt = getConnection().prepareStatement("update IMPORT_TASK_SEQ set SEQ_NUM = SEQ_NUM + ?");
			stmt.setInt(1, insertCount);

			int resultCount = stmt.executeUpdate();
			if (resultCount != 1) {
				throw new RuntimeException(getEntityName() + " reserveIds: update failed: resultCount=" + resultCount);
			}
			closeStatement(stmt);

			sqlString = "select SEQ_NUM from IMPORT_TASK_SEQ";
			stmt = getConnection().prepareStatement("select SEQ_NUM from IMPORT_TASK_SEQ");
			resultSet = stmt.executeQuery();
			if (!resultSet.next())
				throw new RuntimeException(getEntityName() + " reserveIds: select failed");
			int latestKey = resultSet.getInt(1);

			return latestKey - insertCount;
		} catch (SQLException sqle) {
			throw handleSQLException(sqlString, sqle);
		} finally {
			closeResultSet(resultSet);
			closeStatement(stmt);
			closeConnection();

			if (timer != null)
				timer.logDebug("reserveIds", "keys=" + insertCount);
		}
	}

	public ImportTaskPK generateKeys() {
		Timer timer = _log.isDebugEnabled() ? new Timer(_log) : null;

		int insertCount = mDetails.getInsertCount(0);

		if (insertCount == 0) {
			return mDetails.getPK();
		}
		mDetails.assignNextKey(reserveIds(insertCount));

		return mDetails.getPK();
	}

	protected void doStore() throws DuplicateNameValidationException, VersionValidationException, ValidationException {
		Timer timer = _log.isDebugEnabled() ? new Timer(_log) : null;

		generateKeys();

		PreparedStatement stmt = null;

		boolean mainChanged = mDetails.isModified();
		boolean dependantChanged = false;
		try {
			if (getImportTaskLinkDAO().update(mDetails.getImportTasksEventsMap())) {
				dependantChanged = true;
			}
			if ((mainChanged) || (dependantChanged)) {
				mDetails.setVersionNum(mDetails.getVersionNum() + 1);

				mDetails.setUpdatedTime(new Timestamp(new Date().getTime()));
				stmt = getConnection().prepareStatement("update IMPORT_TASK set VIS_ID = ?,DESCRIPTION = ?, EXTERNAL_SYSTEM = ? where IMPORT_TASK_ID = ?");

				int col = 1;
				col = putEvoDataToJdbc(mDetails, stmt, col);
				col = putEvoKeysToJdbc(mDetails, stmt, col);

				stmt.setInt(col++, mDetails.getVersionNum() - 1);

				int resultCount = stmt.executeUpdate();

				if (resultCount != 1) {
					throw new RuntimeException(getEntityName() + " update failed (" + getPK() + "): resultCount=" + resultCount);
				}

				mDetails.reset();
			}

		} catch (SQLException sqle) {
			throw handleSQLException(getPK(), "update IMPORT_TASK set VIS_ID = ?,DESCRIPTION = ?, EXTERNAL_SYSTEM = ? where IMPORT_TASK_ID = ?", sqle);
		} finally {
			closeStatement(stmt);
			closeConnection();

			if ((timer != null) && (
					(mainChanged) || (dependantChanged)))
				timer.logDebug("store", mDetails.getPK() + "(" + mainChanged + "," + dependantChanged + ")");
		}
	}

	private void checkVersionNum() throws VersionValidationException {
	}

	protected void doRemove() {
		Timer timer = _log.isDebugEnabled() ? new Timer(_log) : null;
		deleteDependants(mDetails.getPK());

		PreparedStatement stmt = null;
		try
		{
			stmt = getConnection().prepareStatement("delete from IMPORT_TASK where IMPORT_TASK_ID = ? ");

			int col = 1;
			stmt.setInt(col++, mDetails.getImportTaskId());

			int resultCount = stmt.executeUpdate();

			if (resultCount != 1) {
				throw new RuntimeException(getEntityName() + " delete failed (" + getPK() + "): resultCount=" + resultCount);
			}

		} catch (SQLException sqle)
		{
			throw handleSQLException(getPK(), "delete from IMPORT_TASK where    IMPORT_TASK_ID = ? ", sqle);
		} finally
		{
			closeStatement(stmt);
			closeConnection();

			if (timer != null)
				timer.logDebug("remove", mDetails.getPK());
		}
	}

	public AllImportTasksELO getAllImportTasks() {
		Timer timer = _log.isDebugEnabled() ? new Timer(_log) : null;
		PreparedStatement stmt = null;
		ResultSet resultSet = null;
		AllImportTasksELO results = new AllImportTasksELO();
		try {
			stmt = getConnection().prepareStatement("select IMPORT_TASK.IMPORT_TASK_ID, IMPORT_TASK.VIS_ID, EXTERNAL_SYSTEM.EXTERNAL_SYSTEM_ID, EXTERNAL_SYSTEM.VIS_ID, IMPORT_TASK.DESCRIPTION from IMPORT_TASK, EXTERNAL_SYSTEM  where EXTERNAL_SYSTEM.EXTERNAL_SYSTEM_ID = IMPORT_TASK.EXTERNAL_SYSTEM_ID");
			int col;
			resultSet = stmt.executeQuery();
			while (resultSet.next()) {
				col = 1;

				// import_task_id
				ImportTaskPK pkImportTask = new ImportTaskPK(resultSet.getInt(col++));
				String textImportTask = resultSet.getString(col++);
				ImportTaskRefImpl erImportTask = new ImportTaskRefImpl(pkImportTask, textImportTask);

				// external_system_id
				ExternalSystemPK pkExternalSystem = new ExternalSystemPK(resultSet.getInt(col++));
				String textExternalSystem = resultSet.getString(col++);
				ExternalSystemRefImpl erExternalSystem = new ExternalSystemRefImpl(pkExternalSystem, textExternalSystem);

				// description
				String description = resultSet.getString(col++);

				results.add(erImportTask, erExternalSystem, description);
			}

		} catch (SQLException sqle) {
			throw handleSQLException("select IMPORT_TASK.IMPORT_TASK_ID, IMPORT_TASK.VIS_ID, EXTERNAL_SYSTEM.EXTERNAL_SYSTEM_ID, EXTERNAL_SYSTEM.VIS_ID, IMPORT_TASK.DESCRIPTION from IMPORT_TASK, EXTERNAL_SYSTEM  where EXTERNAL_SYSTEM.EXTERNAL_SYSTEM_ID = IMPORT_TASK.EXTERNAL_SYSTEM_ID", sqle);
		} finally {
			closeResultSet(resultSet);
			closeStatement(stmt);
			closeConnection();
		}

		if (timer != null) {
			timer.logDebug("getAllImportTasks", " items=" + results.size());
		}

		return results;
	}

	private void deleteDependants(ImportTaskPK pk) {
		Set emptyStrings = Collections.emptySet();
		deleteDependants(pk, emptyStrings);
	}

	private void deleteDependants(ImportTaskPK pk, Set<String> exclusionTables) {
		for (int i = SQL_DELETE_CHILDRENS_DEPENDANTS.length - 1; i > -1; i--) {
			if (!exclusionTables.contains(SQL_DELETE_CHILDRENS_DEPENDANTS[i][0])) {
				Timer timer = _log.isDebugEnabled() ? new Timer(_log) : null;

				PreparedStatement stmt = null;

				int resultCount = 0;
				String s = null;
				try
				{
					s = SQL_DELETE_CHILDRENS_DEPENDANTS[i][1] + SQL_DELETE_DEPENDANT_CRITERIA;

					if (_log.isDebugEnabled()) {
						_log.debug("deleteDependants", s);
					}
					stmt = getConnection().prepareStatement(s);

					int col = 1;
					stmt.setInt(col++, pk.getImportTaskId());

					resultCount = stmt.executeUpdate();
				} catch (SQLException sqle)
				{
					throw handleSQLException(pk, s, sqle);
				} finally
				{
					closeStatement(stmt);
					closeConnection();

					if (timer != null)
						timer.logDebug("deleteDependants", "A[" + i + "] count=" + resultCount);
				}
			}
		}
		for (int i = SQL_DELETE_CHILDREN.length - 1; i > -1; i--)
		{
			if (!exclusionTables.contains(SQL_DELETE_CHILDREN[i][0]))
			{
				Timer timer = _log.isDebugEnabled() ? new Timer(_log) : null;

				PreparedStatement stmt = null;

				int resultCount = 0;
				String s = null;
				try
				{
					s = SQL_DELETE_CHILDREN[i][1];

					if (_log.isDebugEnabled()) {
						_log.debug("deleteDependants", s);
					}
					stmt = getConnection().prepareStatement(s);

					int col = 1;
					stmt.setInt(col++, pk.getImportTaskId());

					resultCount = stmt.executeUpdate();
				} catch (SQLException sqle)
				{
					throw handleSQLException(pk, s, sqle);
				} finally
				{
					closeStatement(stmt);
					closeConnection();

					if (timer != null)
						timer.logDebug("deleteDependants", "B[" + i + "] count=" + resultCount);
				}
			}
		}
	}

	public ImportTaskEVO getDetails(ImportTaskPK pk, String dependants)
			throws ValidationException
	{
		return getDetails(new ImportTaskCK(pk), dependants);
	}

	public ImportTaskEVO getDetails(ImportTaskCK paramCK, String dependants)
			throws ValidationException
	{
		Timer timer = _log.isDebugEnabled() ? new Timer(_log) : null;

		if (mDetails == null) {
			doLoad(paramCK.getImportTaskPK());
		}
		else if (!mDetails.getPK().equals(paramCK.getImportTaskPK())) {
			doLoad(paramCK.getImportTaskPK());
		}
		else if (!checkIfValid())
		{
			_log.info("getDetails", "[ALERT] ImportTaskEVO " + mDetails.getPK() + " no longer valid - reloading");

			doLoad(paramCK.getImportTaskPK());
		}

		if ((dependants.indexOf("<0>") > -1) && (!mDetails.isImportTasksEventsAllItemsLoaded()))
		{
			mDetails.setImportTasksEvents(getImportTaskLinkDAO().getAll(mDetails.getImportTaskId(), dependants, mDetails.getImportTasksEvents()));

			mDetails.setImportTasksEventsAllItemsLoaded(true);
		}

		if ((paramCK instanceof ImportTaskLinkCK))
		{
			if (mDetails.getImportTasksEvents() == null) {
				mDetails.loadImportTasksEventsItem(getImportTaskLinkDAO().getDetails(paramCK, dependants));
			}
			else {
				ImportTaskLinkPK pk = ((ImportTaskLinkCK) paramCK).getImportTaskLinkPK();
				ImportTaskLinkEVO evo = mDetails.getImportTasksEventsItem(pk);
				if (evo == null) {
					mDetails.loadImportTasksEventsItem(getImportTaskLinkDAO().getDetails(paramCK, dependants));
				}
			}
		}

		ImportTaskEVO details = new ImportTaskEVO();
		details = mDetails.deepClone();

		if (timer != null) {
			timer.logDebug("getDetails", paramCK + " " + dependants);
		}
		return details;
	}

	private boolean checkIfValid() {
		return true;
	}

	public ImportTaskEVO getDetails(String dependants) throws ValidationException {
		Timer timer = _log.isDebugEnabled() ? new Timer(_log) : null;

		if (!checkIfValid()) {
			_log.info("getDetails", "ImportTask " + mDetails.getPK() + " no longer valid - reloading");
			doLoad(mDetails.getPK());
		}

		getDependants(mDetails, dependants);

		ImportTaskEVO details = mDetails.deepClone();

		if (timer != null) {
			timer.logDebug("getDetails", mDetails.getPK() + " " + dependants);
		}
		return details;
	}

	protected ImportTaskLinkDAO getImportTaskLinkDAO() {
		if (mImportTaskLinkDAO == null) {
			if (mDataSource != null)
				mImportTaskLinkDAO = new ImportTaskLinkDAO(mDataSource);
			else {
				mImportTaskLinkDAO = new ImportTaskLinkDAO(getConnection());
			}
		}
		return mImportTaskLinkDAO;
	}

	public String getEntityName() {
		return "ImportTask";
	}

	public ImportTaskRef getRef(ImportTaskPK paramImportTaskPK) throws ValidationException {
		ImportTaskEVO evo = getDetails(paramImportTaskPK, "");
		return evo.getEntityRef();
	}

	public void getDependants(Collection c, String dependants) {
		if (c == null)
			return;
		Iterator iter = c.iterator();
		while (iter.hasNext()) {
			ImportTaskEVO evo = (ImportTaskEVO) iter.next();
			getDependants(evo, dependants);
		}
	}

	public void getDependants(ImportTaskEVO evo, String dependants) {
		if (evo.getImportTaskId() < 1) {
			return;
		}

		if (dependants.indexOf("<0>") > -1) {
			if (!evo.isImportTasksEventsAllItemsLoaded()) {
				evo.setImportTasksEvents(getImportTaskLinkDAO().getAll(evo.getImportTaskId(), dependants, evo.getImportTasksEvents()));

				evo.setImportTasksEventsAllItemsLoaded(true);
			}
		}
	}
}