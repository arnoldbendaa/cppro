package com.cedar.cp.ejb.impl.recalculate;

import com.cedar.cp.api.base.DuplicateNameValidationException;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.base.VersionValidationException;
import com.cedar.cp.api.recalculate.RecalculateBatchTaskAssignment;
import com.cedar.cp.api.recalculate.RecalculateBatchTaskRef;
import com.cedar.cp.api.user.DataEntryProfileRef;
import com.cedar.cp.api.xmlform.XmlFormRef;
import com.cedar.cp.dto.dimension.StructureElementPK;
import com.cedar.cp.dto.extsys.ExternalSystemPK;
import com.cedar.cp.dto.extsys.ExternalSystemRefImpl;
import com.cedar.cp.dto.model.BudgetCyclePK;
import com.cedar.cp.dto.model.BudgetCycleRefImpl;
import com.cedar.cp.dto.model.ModelPK;
import com.cedar.cp.dto.model.ModelRefImpl;
import com.cedar.cp.dto.recalculate.*;
import com.cedar.cp.dto.user.DataEntryProfileCK;
import com.cedar.cp.dto.user.DataEntryProfilePK;
import com.cedar.cp.dto.user.DataEntryProfileRefImpl;
import com.cedar.cp.dto.xmlform.XmlFormPK;
import com.cedar.cp.dto.xmlform.XmlFormRefImpl;
import com.cedar.cp.ejb.impl.base.AbstractDAO;
import com.cedar.cp.ejb.impl.dataentry.DataEntryDAO;
import com.cedar.cp.ejb.impl.user.DataEntryProfileDAO;
import com.cedar.cp.util.Log;
import com.cedar.cp.util.Timer;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import javax.sql.DataSource;

public class RecalculateBatchTaskDAO extends AbstractDAO {
	Log _log = new Log(getClass());

	protected RecalculateBatchTaskFormDAO mRecalculateBatchTaskFormDAO;
	protected RecalculateBatchTaskRespAreaDAO mRecalculateBatchTaskRespAreaDAO;
	protected RecalculateBatchTaskEVO mDetails;

	public RecalculateBatchTaskDAO(Connection connection) {
		super(connection);
	}

	public RecalculateBatchTaskDAO() {
	}

	public RecalculateBatchTaskDAO(DataSource ds) {
		super(ds);
	}

	protected RecalculateBatchTaskPK getPK() {
		return mDetails.getPK();
	}

	public void setDetails(RecalculateBatchTaskEVO details) {
		mDetails = details.deepClone();
	}

	public RecalculateBatchTaskEVO setAndGetDetails(RecalculateBatchTaskEVO details, String dependants) {
		setDetails(details);
//		getDependants(mDetails, dependants);
		return mDetails.deepClone();
	}

	public RecalculateBatchTaskPK create() throws DuplicateNameValidationException, ValidationException {
		doCreate();
		return mDetails.getPK();
	}

	public void load(RecalculateBatchTaskPK pk) throws ValidationException {
		doLoad(pk);
	}

	public void store() throws DuplicateNameValidationException, VersionValidationException, ValidationException {
		doStore();
	}

	public void remove(RecalculateBatchTaskPK pk) {
		doRemove(pk);
		deleteDependants(pk);
	}

	protected void doCreate() throws DuplicateNameValidationException, ValidationException {
		{
			Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
//			this.mDetails.postCreateInit();

			PreparedStatement stmt = null;
			try {

				stmt = getConnection().prepareStatement("insert into RECALCULATE_BATCH  values ( ?, ?, ?, ?)");

				int col = 1;
				col = putEvoKeysToJdbc(this.mDetails, stmt, col);
				col = putEvoDataToJdbc(this.mDetails, stmt, col);

				int resultCount = stmt.executeUpdate();
				if (resultCount != 1) {
					throw new RuntimeException(getEntityName() + " insert failed (" + this.mDetails.getPK() + "): resultCount=" + resultCount);
				}

				this.mDetails.reset();
			} catch (SQLException sqle) {
				throw handleSQLException(this.mDetails.getPK(), "insert into RECALCULATE_BATCH  values ( ?, ?, ?, ?)", sqle);
			} finally {
				closeStatement(stmt);
				closeConnection();

				if (timer != null) {
					timer.logDebug("doCreate", this.mDetails.toString());
				}
			}

			try {
//				getRecalculateBatchTaskFormDAO().update(this.mDetails.getRecalculateBatchTaskFormsMap());
//				getRecalculateBatchTaskRespAreaDAO().update(this.mDetails.getRecalculateBatchTaskRespAreaMap());

			} catch (Exception e) {
				throw new RuntimeException("unexpected exception", e);
			}
		}
	}

	public RecalculateBatchTaskPK findByPrimaryKey(RecalculateBatchTaskPK pk_) throws ValidationException {
		Timer timer = _log.isDebugEnabled() ? new Timer(_log) : null;
		if (exists(pk_)) {
			if (timer != null) {
				timer.logDebug("findByPrimaryKey", pk_);
			}
			return pk_;
		}

		throw new ValidationException(pk_ + " not found");
	}

	protected boolean exists(RecalculateBatchTaskPK pk) {
		PreparedStatement stmt = null;
		ResultSet resultSet = null;
		boolean returnValue = false;
		try {
			stmt = getConnection().prepareStatement("select RECALCULATE_BATCH_ID from RECALCULATE_BATCH where RECALCULATE_BATCH_ID = ?");

			int col = 1;
			stmt.setInt(col++, pk.getRecalculateBatchTaskId());

			resultSet = stmt.executeQuery();

			if (!resultSet.next())
				returnValue = false;
			else
				returnValue = true;
		} catch (SQLException sqle) {
			throw handleSQLException(pk, "select RECALCULATE_BATCH_ID from RECALCULATE_BATCH where RECALCULATE_BATCH_ID = ?", sqle);
		} finally {
			closeResultSet(resultSet);
			closeStatement(stmt);
			closeConnection();
		}
		return returnValue;
	}

	private RecalculateBatchTaskEVO getEvoFromJdbc(ResultSet resultSet_) throws SQLException {
		int col = 1;
		RecalculateBatchTaskEVO evo = new RecalculateBatchTaskEVO(resultSet_.getInt(col++), resultSet_.getInt(col++), resultSet_.getInt(col++), resultSet_.getInt(col++), resultSet_.getString(col++), resultSet_.getString(col++));
		return evo;
	}

	private int putEvoKeysToJdbc(RecalculateBatchTaskEVO evo_, PreparedStatement stmt_, int startCol_) throws SQLException {
		int col = startCol_;
		stmt_.setInt(col++, evo_.getRecalculateBatchTaskId());
		return col;
	}

	private int putEvoDataToJdbc(RecalculateBatchTaskEVO evo_, PreparedStatement stmt_, int startCol_) throws SQLException {
		int col = startCol_;
		stmt_.setInt(col++, evo_.getModelId());
		stmt_.setInt(col++, evo_.getBudgetCycleId());
		stmt_.setInt(col++, evo_.getUserId());
		return col;
	}

	protected void doLoad(RecalculateBatchTaskPK pk) throws ValidationException {
		Timer timer = _log.isDebugEnabled() ? new Timer(_log) : null;

		PreparedStatement stmt = null;
		ResultSet resultSet = null;
		try {
			stmt = getConnection().prepareStatement("select * from RECALCULATE_BATCH where RECALCULATE_BATCH_ID = ?");

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
			throw handleSQLException(pk, "select * from RECALCULATE_BATCH where RECALCULATE_BATCH_ID = ?", sqle);
		} finally {
			closeResultSet(resultSet);
			closeStatement(stmt);
			closeConnection();

			if (timer != null)
				timer.logDebug("doLoad", pk);
		}
	}

	public int reserveIds(int insertCount) {

		return 0;
	}

	protected void doStore() throws DuplicateNameValidationException, VersionValidationException, ValidationException {
	}

	protected void doRemove(RecalculateBatchTaskPK pk) {
		Timer timer = _log.isDebugEnabled() ? new Timer(_log) : null;

		PreparedStatement stmt = null;
		try {
			stmt = getConnection().prepareStatement("delete from RECALCULATE_BATCH where RECALCULATE_BATCH_ID = ? ");

			int col = 1;
			stmt.setInt(col++, pk.getRecalculateBatchTaskId());

			int resultCount = stmt.executeUpdate();

			if (resultCount != 1) {
				throw new RuntimeException(getEntityName() + " delete failed (" + getPK() + "): resultCount=" + resultCount);
			}

		} catch (SQLException sqle) {
			throw handleSQLException(getPK(), "delete from RECALCULATE_BATCH where RECALCULATE_BATCH_ID = ? ", sqle);
		} finally {
			closeStatement(stmt);
			closeConnection();

			if (timer != null)
				timer.logDebug("remove", pk);
		}
	}

	public AllRecalculateBatchTasksELO getAllRecalculateBatchTasks() {
		Timer timer = _log.isDebugEnabled() ? new Timer(_log) : null;
		PreparedStatement stmt = null;
		ResultSet resultSet = null;
		PreparedStatement stmt1 = null;
		AllRecalculateBatchTasksELO results = new AllRecalculateBatchTasksELO(true);
		try {
			stmt = getConnection().prepareStatement("select recalculate_batch_id, recalculate_batch.model_id, model.vis_id, model.description, recalculate_batch.identifier, recalculate_batch.description, user_id from recalculate_batch join model on recalculate_batch.model_id = model.model_id");
			int col;
			resultSet = stmt.executeQuery();
			while (resultSet.next()) {
				col = 1;

				// import_task_id
				RecalculateBatchTaskPK pkRecalculateBatchTask = new RecalculateBatchTaskPK(resultSet.getInt(col++));
				RecalculateBatchTaskRefImpl erRecalculateBatchTask = new RecalculateBatchTaskRefImpl(pkRecalculateBatchTask, "RecalculateBatchTask");

				// model
				ModelPK modelPk = new ModelPK(resultSet.getInt(col++));
				ModelRefImpl model = new ModelRefImpl(modelPk, resultSet.getString(col++) + " - " + resultSet.getString(col++));

				String identifier = resultSet.getString(col++);
				String description = resultSet.getString(col++);
				int userId = resultSet.getInt(col++);
				
				results.add(erRecalculateBatchTask, model, identifier, description, userId);
			}

		} catch (SQLException sqle) {
			throw handleSQLException("select recalculate_batch_id, recalculate_batch.model_id, model.vis_id, model.description, budget_cycle_id from recalculate_batch join model on recalculate_batch.model_id = model.model_id", sqle);
		} finally {
			closeResultSet(resultSet);
			closeStatement(stmt);
			closeStatement(stmt1);
			closeConnection();
		}

		if (timer != null) {
			timer.logDebug("getAllRecalculateBatchTasks", " items=" + results.size());
		}

		return results;
	}
	
//	public AllRecalculateBatchTasksELO getRecalculateBatchTasksForUser(int userId) {
//		Timer timer = _log.isDebugEnabled() ? new Timer(_log) : null;
//		PreparedStatement stmt = null;
//		ResultSet resultSet = null;
//		PreparedStatement stmt1 = null;
//		AllRecalculateBatchTasksELO results = new AllRecalculateBatchTasksELO(true);
//		try {
//			stmt = getConnection().prepareStatement("select distinct recalculate_batch_id, recalculate_batch.model_id, model.vis_id, model.description, recalculate_batch.identifier, recalculate_batch.description, recalculate_batch.user_id from recalculate_batch join model on recalculate_batch.model_id = model.model_id where model.model_id in (select distinct model_id from budget_user where user_id = ?)");
//			int col;
//			resultSet = stmt.executeQuery();
//			while (resultSet.next()) {
//				col = 1;
//
//				// import_task_id
//				RecalculateBatchTaskPK pkRecalculateBatchTask = new RecalculateBatchTaskPK(resultSet.getInt(col++));
//				RecalculateBatchTaskRefImpl erRecalculateBatchTask = new RecalculateBatchTaskRefImpl(pkRecalculateBatchTask, "RecalculateBatchTask");
//
//				// model
//				ModelPK modelPk = new ModelPK(resultSet.getInt(col++));
//				ModelRefImpl model = new ModelRefImpl(modelPk, resultSet.getString(col++) + " - " + resultSet.getString(col++));
//
//				String identifier = resultSet.getString(col++);
//				String description = resultSet.getString(col++);
//				int userId = resultSet.getInt(col++);
//				
//				results.add(erRecalculateBatchTask, model, identifier, description, userId);
//			}
//
//		} catch (SQLException sqle) {
//			throw handleSQLException("select recalculate_batch_id, recalculate_batch.model_id, model.vis_id, model.description, budget_cycle_id from recalculate_batch join model on recalculate_batch.model_id = model.model_id", sqle);
//		} finally {
//			closeResultSet(resultSet);
//			closeStatement(stmt);
//			closeStatement(stmt1);
//			closeConnection();
//		}
//
//		if (timer != null) {
//			timer.logDebug("getAllRecalculateBatchTasks", " items=" + results.size());
//		}
//
//		return results;
//	}
	
	public AllRecalculateBatchTasksELO getRecalculateBatchTask(int id) {
		Timer timer = _log.isDebugEnabled() ? new Timer(_log) : null;
		PreparedStatement stmt = null;
		ResultSet resultSet = null;
		
		PreparedStatement stmt1 = null;
		ResultSet resultSet1 = null;
		
		PreparedStatement stmt2 = null;
		ResultSet resultSet2 = null;
		
		AllRecalculateBatchTasksELO results = new AllRecalculateBatchTasksELO();
		try {
			stmt = getConnection().prepareStatement("select recalculate_batch_id, recalculate_batch.model_id, model.vis_id, model.description, budget_cycle_id, recalculate_batch.identifier, recalculate_batch.description, user_id from recalculate_batch join model on recalculate_batch.model_id = model.model_id where recalculate_batch_id = ?");
			int col;
			stmt.setInt(1, id);
			resultSet = stmt.executeQuery();

			stmt1 = getConnection().prepareStatement("select dep.data_entry_profile_id, dep.vis_id, dep.description from recalculate_batch_form rbf join data_entry_profile dep on rbf.data_entry_profile_id = dep.data_entry_profile_id where recalculate_batch_id = ?");
			stmt1.setInt(1, id);
			resultSet1 = stmt1.executeQuery();
			
			stmt2 = getConnection().prepareStatement("select rbra.resp_area_id from recalculate_batch_resp_area rbra where recalculate_batch_id = ?");
			stmt2.setInt(1, id);
			resultSet2 = stmt2.executeQuery();
			
			List<DataEntryProfileRef> xmlList = new ArrayList<DataEntryProfileRef>();
			
			while (resultSet1.next()) {
				DataEntryProfilePK dataEntryProfilePk = new DataEntryProfilePK(resultSet1.getInt(1));
				DataEntryProfileRefImpl dataEntryRef = new DataEntryProfileRefImpl(dataEntryProfilePk, resultSet1.getString(2));
				xmlList.add(dataEntryRef);
			}
			
			List<Integer> respArea = new ArrayList<Integer>();
			while (resultSet2.next()) {
				respArea.add(resultSet2.getInt(1));
			}
			
			while (resultSet.next()) {
				col = 1;

				// import_task_id
				RecalculateBatchTaskPK pkRecalculateBatchTask = new RecalculateBatchTaskPK(resultSet.getInt(col++));
				RecalculateBatchTaskRefImpl erRecalculateBatchTask = new RecalculateBatchTaskRefImpl(pkRecalculateBatchTask, "RecalculateBatchTask");

				// model
				ModelPK modelPk = new ModelPK(resultSet.getInt(col++));
				ModelRefImpl model = new ModelRefImpl(modelPk, resultSet.getString(col++) + " - " + resultSet.getString(col++));

				// budget cycle
				BudgetCyclePK bcPk = new BudgetCyclePK(resultSet.getInt(col++));
				BudgetCycleRefImpl budgetCycle = new BudgetCycleRefImpl(bcPk, "BudgetCycle");
				
				String identifier = resultSet.getString(col++);
				String description = resultSet.getString(col++);
				int userId = resultSet.getInt(col++);

				results.add(erRecalculateBatchTask, model, budgetCycle, xmlList, respArea, identifier, description, userId);
			}

		} catch (SQLException sqle) {
			throw handleSQLException("select recalculate_batch_id, recalculate_batch.model_id, model.vis_id, model.description, budget_cycle_id, recalculate_batch.identifier, recalculate_batch.description, user_id from recalculate_batch join model on recalculate_batch.model_id = model.model_id where recalculate_batch_id = ?", sqle);
		} finally {
			closeResultSet(resultSet);
			closeStatement(stmt);
			closeStatement(stmt1);
			closeConnection();
		}

		if (timer != null) {
			timer.logDebug("getAllRecalculateBatchTasks", " items=" + results.size());
		}

		return results;
	}

	public void deleteDependants(RecalculateBatchTaskPK pk) {
		PreparedStatement stmt = null;
		PreparedStatement stmt1 = null;

		try {
			stmt = this.getConnection().prepareCall("delete from RECALCULATE_BATCH_RESP_AREA where recalculate_batch_id = ?");
			stmt.setInt(1, pk.getRecalculateBatchTaskId());
			stmt.execute();

			stmt1 = this.getConnection().prepareCall("delete from RECALCULATE_BATCH_FORM where recalculate_batch_id = ?");
			stmt1.setInt(1, pk.getRecalculateBatchTaskId());
			stmt1.execute();
		} catch (SQLException e) {
			System.err.println(e);
		} finally {
			closeStatement(stmt);
			closeStatement(stmt1);
			closeConnection();
		}
	}

	public RecalculateBatchTaskEVO getDetails(RecalculateBatchTaskPK pk, String dependants) throws ValidationException {
		return getDetails(new RecalculateBatchTaskCK(pk), dependants);
	}

	public RecalculateBatchTaskEVO getDetails(RecalculateBatchTaskCK paramCK, String dependants) throws ValidationException {
		
		if(mDetails==null)
			doLoad(paramCK.getRecalculateBatchTaskPK());
		return getDetails(dependants);
//		return null;
	}

	private boolean checkIfValid() {
		return true;
	}

	public RecalculateBatchTaskEVO getDetails(String dependants) throws ValidationException {
		Timer timer = _log.isDebugEnabled() ? new Timer(_log) : null;

		if (!checkIfValid()) {
			_log.info("getDetails", "RecalculateBatchTask " + mDetails.getPK() + " no longer valid - reloading");
			doLoad(mDetails.getPK());
		}

		AllRecalculateBatchTasksELO elo = getRecalculateBatchTask(mDetails.getRecalculateBatchTaskId());
		elo.next();
		mDetails.setBudgetCycleId(elo.getBudgetCycle().getBudgetCyclePK().getBudgetCycleId());
		mDetails.setModelId(elo.getModel().getModelPK().getModelId());
		mDetails.setRecalculateBatchTaskForms(elo.getDataEntryProfileRefs());
		mDetails.setRecalculateBatchTaskRespArea(elo.getRespArea());
		mDetails.setUserId(elo.getUserId());
		mDetails.setIdentifier(elo.getIdentifier());
		mDetails.setDescription(elo.getDescription());
		RecalculateBatchTaskEVO details = new RecalculateBatchTaskEVO();
		details = mDetails.deepClone();

		if (timer != null) {
			timer.logDebug("getDetails", mDetails.getPK() + " " + dependants);
		}
		return details;
	}

	protected RecalculateBatchTaskFormDAO getRecalculateBatchTaskFormDAO() {
		if (mRecalculateBatchTaskFormDAO == null) {
			if (mDataSource != null)
				mRecalculateBatchTaskFormDAO = new RecalculateBatchTaskFormDAO(mDataSource);
			else {
				mRecalculateBatchTaskFormDAO = new RecalculateBatchTaskFormDAO(getConnection());
			}
		}
		return mRecalculateBatchTaskFormDAO;
	}

	protected RecalculateBatchTaskRespAreaDAO getRecalculateBatchTaskRespAreaDAO() {
		if (mRecalculateBatchTaskRespAreaDAO == null) {
			if (mDataSource != null)
				mRecalculateBatchTaskRespAreaDAO = new RecalculateBatchTaskRespAreaDAO(mDataSource);
			else {
				mRecalculateBatchTaskRespAreaDAO = new RecalculateBatchTaskRespAreaDAO(getConnection());
			}
		}
		return mRecalculateBatchTaskRespAreaDAO;
	}

	public String getEntityName() {
		return "RecalculateBatchTask";
	}

	public RecalculateBatchTaskRef getRef(RecalculateBatchTaskPK paramRecalculateBatchTaskPK) throws ValidationException {
		RecalculateBatchTaskEVO evo = getDetails(paramRecalculateBatchTaskPK, "");
		return evo.getEntityRef();
	}

	public int insert(int budgetCycleId, int modelId, int userId, String identifier, String description) {

		PreparedStatement stmt = null;
		PreparedStatement stmt1 = null;
		PreparedStatement stmt2 = null;
		ResultSet resultSet = null;
		Integer seq = null;
		
		try {
			stmt = this.getConnection().prepareCall("select SEQ_NUM from RECALCULATE_BATCH_SEQ");
			resultSet = stmt.executeQuery();
			resultSet.next();
			seq = resultSet.getInt(1);

			stmt1 = this.getConnection().prepareCall("insert into RECALCULATE_BATCH (recalculate_batch_id, model_id, budget_cycle_id, user_id, identifier, description) values (?, ?, ?, ?, ?, ?)");
			stmt1.setInt(1, ++seq);
			stmt1.setInt(2, modelId);
			stmt1.setInt(3, budgetCycleId);
			stmt1.setInt(4, userId);
			stmt1.setString(5, identifier);
			stmt1.setString(6, description);

			stmt1.executeQuery();

			stmt2 = this.getConnection().prepareCall("update RECALCULATE_BATCH_SEQ set SEQ_NUM = ?");
			stmt2.setInt(1, seq);
			stmt2.executeQuery();

		} catch (SQLException e) {
			System.err.println(e);
		} finally {
			closeResultSet(resultSet);
			closeStatement(stmt);
			closeStatement(stmt1);
			closeStatement(stmt2);
			closeConnection();

		}
		return seq;
	}

	public boolean validate(List<RecalculateBatchTaskAssignment> recalculateBatchTaskAssignments, List<DataEntryProfileRef> recalculateBatchTaskForms) {
		for (RecalculateBatchTaskAssignment data : recalculateBatchTaskAssignments) {
			Integer structureId = ((StructureElementPK) data.getOwningBudgetLocationRef().getPrimaryKey()).getStructureElementId();
			if (structureId == null) {
				return false; // error
			}
		}
		if (recalculateBatchTaskAssignments.size() == 0) {
			return false; // error
		}
		for (DataEntryProfileRef data : recalculateBatchTaskForms) {
			Integer depId = ((DataEntryProfileCK) data.getPrimaryKey()).getDataEntryProfilePK().getDataEntryProfileId();
			if (depId == null) {
				return false; // error
			}
		}
		if (recalculateBatchTaskForms.size() == 0) {
			return false; // error
		}
		return true;

	}
	
	public void update(int id, int budgetCycleId, int modelId, int userId, String identifier, String description) {

		PreparedStatement stmt1 = null;
		
		try {
			stmt1 = this.getConnection().prepareCall("update RECALCULATE_BATCH set model_id = ?, budget_cycle_id = ?, user_id = ?, identifier = ?, description = ? where recalculate_batch_id = ?");
			stmt1.setInt(1, modelId);
			stmt1.setInt(2, budgetCycleId);
			stmt1.setInt(3, userId);
			stmt1.setString(4, identifier);
			stmt1.setString(5, description);
			stmt1.setInt(6, id);

			stmt1.executeQuery();


		} catch (SQLException e) {
			System.err.println(e);
		} finally {
			closeStatement(stmt1);
			closeConnection();

		}
	}

	public void insertIntoAdditionalTables(List<RecalculateBatchTaskAssignment> recalculateBatchTaskAssignments, List<DataEntryProfileRef> recalculateBatchTaskForms, int rbId) {
		PreparedStatement stmt = null;
		PreparedStatement stmt1 = null;

		try {
			stmt = this.getConnection().prepareCall("insert into RECALCULATE_BATCH_RESP_AREA values (?, ?)");
			for (RecalculateBatchTaskAssignment data : recalculateBatchTaskAssignments) {
				stmt.setInt(1, rbId);
				stmt.setInt(2, ((StructureElementPK) data.getOwningBudgetLocationRef().getPrimaryKey()).getStructureElementId());
				stmt.addBatch();
			}

			stmt1 = this.getConnection().prepareCall("insert into RECALCULATE_BATCH_FORM values (?, ?)");
			for (DataEntryProfileRef data : recalculateBatchTaskForms) {
				stmt1.setInt(1, rbId);
				stmt1.setInt(2, ((DataEntryProfileCK) data.getPrimaryKey()).getDataEntryProfilePK().getDataEntryProfileId());
				stmt1.addBatch();
			}

			int[] resp = stmt.executeBatch();
			for (int i = 0; i < resp.length; ++i) {
				if (resp[i] != 1 && resp[i] != -2) {
					throw new IllegalStateException("Failed to insert resposibility area [" + resp[i] + "]");
				}
			}
			int[] forms = stmt1.executeBatch();
			for (int i = 0; i < forms.length; ++i) {
				if (forms[i] != 1 && forms[i] != -2) {
					throw new IllegalStateException("Failed to insert xml form [" + forms[i] + "]");
				}
			}

		} catch (SQLException e) {
			System.err.println(e);
		} finally {
			closeStatement(stmt);
			closeStatement(stmt1);
			closeConnection();
		}
	}
	
	public void updateAdditionalTables(List<Integer> insertRA, List<Integer> deleteRA, List<DataEntryProfileRef> insertForms, List<DataEntryProfileRef> deleteForms, int rbId) {
		PreparedStatement stmt = null;
		PreparedStatement stmt1 = null;
		PreparedStatement stmt2 = null;
		PreparedStatement stmt3 = null;

		try {
			stmt = this.getConnection().prepareCall("insert into RECALCULATE_BATCH_RESP_AREA values (?, ?)");
			for (int respAreaId : insertRA) {
				stmt.setInt(1, rbId);
				stmt.setInt(2, respAreaId);
				stmt.addBatch();
			}

			stmt1 = this.getConnection().prepareCall("insert into RECALCULATE_BATCH_FORM values (?, ?)");
			for (DataEntryProfileRef data : insertForms) {
				stmt1.setInt(1, rbId);
				stmt1.setInt(2, ((DataEntryProfileCK) data.getPrimaryKey()).getDataEntryProfilePK().getDataEntryProfileId());
				stmt1.addBatch();
			}
			
			stmt2 = this.getConnection().prepareCall("delete from RECALCULATE_BATCH_RESP_AREA where recalculate_batch_id = ? and resp_area_id = ?");
			for (int respAreaId : deleteRA) {
				stmt2.setInt(1, rbId);
				stmt2.setInt(2, respAreaId);
				stmt2.addBatch();
			}

			stmt3 = this.getConnection().prepareCall("delete from RECALCULATE_BATCH_FORM where recalculate_batch_id = ? and data_entry_profile_id = ?");
			for (DataEntryProfileRef data : deleteForms) {
				stmt3.setInt(1, rbId);
				stmt3.setInt(2, ((DataEntryProfilePK) data.getPrimaryKey()).getDataEntryProfileId());
				stmt3.addBatch();
			}

			int[] resp = stmt.executeBatch();
			for (int i = 0; i < resp.length; ++i) {
				if (resp[i] != 1 && resp[i] != -2) {
					throw new IllegalStateException("Failed to insert resposibility area [" + resp[i] + "]");
				}
			}
			int[] forms = stmt1.executeBatch();
			for (int i = 0; i < forms.length; ++i) {
				if (forms[i] != 1 && forms[i] != -2) {
					throw new IllegalStateException("Failed to insert xml form [" + forms[i] + "]");
				}
			}
			
			int[] delResp = stmt2.executeBatch();
			for (int i = 0; i < delResp.length; ++i) {
				if (delResp[i] != 1 && delResp[i] != -2) {
					throw new IllegalStateException("Failed to delete resposibility area [" + delResp[i] + "]");
				}
			}
			int[] delForms = stmt3.executeBatch();
			for (int i = 0; i < delForms.length; ++i) {
				if (delForms[i] != 1 && delForms[i] != -2) {
					throw new IllegalStateException("Failed to delete xml form [" + delForms[i] + "]");
				}
			}

		} catch (SQLException e) {
			System.err.println(e);
		} finally {
			closeStatement(stmt);
			closeStatement(stmt1);
			closeStatement(stmt2);
			closeStatement(stmt3);
			closeConnection();
		}
	}
}