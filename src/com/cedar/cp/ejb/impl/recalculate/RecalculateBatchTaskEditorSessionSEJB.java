package com.cedar.cp.ejb.impl.recalculate;

import java.util.ArrayList;
import java.util.List;
import com.cedar.cp.api.base.EntityRef;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.recalculate.RecalculateBatchTaskAssignment;
import com.cedar.cp.api.user.DataEntryProfileRef;
import com.cedar.cp.dto.base.EntityEventMessage;
import com.cedar.cp.dto.base.PrimaryKey;
import com.cedar.cp.dto.dimension.StructureElementPK;
import com.cedar.cp.dto.model.ModelPK;
import com.cedar.cp.dto.recalculate.RecalculateBatchTaskEditorSessionCSO;
import com.cedar.cp.dto.recalculate.RecalculateBatchTaskEditorSessionSSO;
import com.cedar.cp.dto.recalculate.RecalculateBatchTaskImpl;
import com.cedar.cp.dto.recalculate.RecalculateBatchTaskPK;
import com.cedar.cp.dto.recalculate.RecalculateBatchTaskRequest;
import com.cedar.cp.dto.test.TestRollbackTaskRequest;
import com.cedar.cp.dto.test.TestTaskRequest;
import com.cedar.cp.ejb.api.recalculate.RecalculateBatchTaskEditorSessionServer;
import com.cedar.cp.ejb.impl.base.AbstractSession;
import com.cedar.cp.ejb.impl.recalculate.RecalculateBatchTaskAccessor;
import com.cedar.cp.ejb.impl.recalculate.RecalculateBatchTaskEVO;
import com.cedar.cp.util.Log;
import com.cedar.cp.util.Timer;
import com.cedar.cp.util.common.JmsConnectionImpl;
import com.cedar.cp.util.task.TaskMessageFactory;
import javax.ejb.EJBException;
import javax.ejb.SessionContext;
import javax.naming.InitialContext;

public class RecalculateBatchTaskEditorSessionSEJB extends AbstractSession {

	private transient Log mLog = new Log(this.getClass());
	private transient SessionContext mSessionContext;
	private transient RecalculateBatchTaskAccessor mRecalculateBatchTaskAccessor;
	private RecalculateBatchTaskEditorSessionSSO mSSO;
	private RecalculateBatchTaskPK mThisTableKey;
	private RecalculateBatchTaskEVO mRecalculateBatchTaskEVO;

	public RecalculateBatchTaskEditorSessionSSO getItemData(int userId, Object paramKey) throws ValidationException, EJBException {
		this.setUserId(userId);
		if (this.mLog.isDebugEnabled()) {
			this.mLog.debug("getItemData", paramKey);
		}

		Timer timer = this.mLog.isInfoEnabled() ? new Timer(this.mLog) : null;
		this.mThisTableKey = (RecalculateBatchTaskPK) paramKey;

		RecalculateBatchTaskEditorSessionSSO e;
		try {
			this.mRecalculateBatchTaskEVO = this.getRecalculateBatchTaskAccessor().getDetails(this.mThisTableKey, "<0><1>");
			this.makeItemData();
			e = this.mSSO;
		} catch (ValidationException var10) {
			throw var10;
		} catch (EJBException var11) {
			if (var11.getCause() instanceof ValidationException) {
				throw (ValidationException) var11.getCause();
			}

			throw var11;
		} catch (Exception var12) {
			var12.printStackTrace();
			throw new EJBException(var12.getMessage(), var12);
		} finally {
			this.setUserId(0);
			if (timer != null) {
				timer.logInfo("getItemData", this.mThisTableKey);
			}

		}

		return e;
	}

	private void makeItemData() throws Exception {
		this.mSSO = new RecalculateBatchTaskEditorSessionSSO();
		RecalculateBatchTaskImpl editorData = this.buildRecalculateBatchTaskEditData(this.mThisTableKey);
		this.completeGetItemData(editorData);
		this.mSSO.setEditorData(editorData);
	}

	private void completeGetItemData(RecalculateBatchTaskImpl editorData) throws Exception {
	}

	private RecalculateBatchTaskImpl buildRecalculateBatchTaskEditData(Object thisKey) throws Exception {
		RecalculateBatchTaskImpl editorData = new RecalculateBatchTaskImpl(thisKey);
		editorData.setModelId(mRecalculateBatchTaskEVO.getModelId());
		editorData.setRecalculateBatchTaskForms(mRecalculateBatchTaskEVO.getRecalculateBatchTaskForms());
		editorData.setBudgetCycleId(mRecalculateBatchTaskEVO.getBudgetCycleId());
		editorData.setRecalculateBatchTaskRespArea(mRecalculateBatchTaskEVO.getRecalculateBatchTaskRespArea());
		editorData.setUserId(mRecalculateBatchTaskEVO.getUserId());
		editorData.setDescription(mRecalculateBatchTaskEVO.getDescription());
		editorData.setIdentifier(mRecalculateBatchTaskEVO.getIdentifier());
		return editorData;
	}

	public RecalculateBatchTaskEditorSessionSSO getNewItemData(int userId) throws EJBException {
		this.mLog.debug("getNewItemData");
		this.setUserId(userId);
		Timer timer = this.mLog.isInfoEnabled() ? new Timer(this.mLog) : null;

		RecalculateBatchTaskEditorSessionSSO var4;
		try {
			this.mSSO = new RecalculateBatchTaskEditorSessionSSO();
			RecalculateBatchTaskImpl e = new RecalculateBatchTaskImpl((Object) null);
			this.completeGetNewItemData(e);
			this.mSSO.setEditorData(e);
			var4 = this.mSSO;
		} catch (EJBException e) {
			throw e;
		} catch (Exception e) {
			e.printStackTrace();
			throw new EJBException(e.getMessage(), e);
		} finally {
			this.setUserId(0);
			if (timer != null) {
				timer.logInfo("getNewItemData", "");
			}

		}

		return var4;
	}

	private void completeGetNewItemData(RecalculateBatchTaskImpl editorData) throws Exception {
	}

	public RecalculateBatchTaskPK insert(RecalculateBatchTaskEditorSessionCSO cso) throws ValidationException, EJBException {
		this.mLog.debug("insert");
		Timer timer = this.mLog.isInfoEnabled() ? new Timer(this.mLog) : null;
		this.setUserId(cso.getUserId());
		RecalculateBatchTaskImpl editorData = cso.getEditorData();

		RecalculateBatchTaskPK pk = null;
		try {
			if (validateInsert(editorData)) {
				pk = new RecalculateBatchTaskPK(completeInsertSetup(editorData));
				insertIntoAdditionalTables(pk, editorData);
			}
		} catch (ValidationException var10) {
			throw new EJBException(var10);
		} catch (EJBException var11) {
			throw var11;
		} catch (Exception var12) {
			var12.printStackTrace();
			throw new EJBException(var12.getMessage(), var12);
		} finally {
			this.setUserId(0);
			if (timer != null) {
				timer.logInfo("insert", "");
			}

		}

		return pk;
	}

	private void updateRecalculateBatchTask(RecalculateBatchTaskPK pk, RecalculateBatchTaskImpl editorData) {
		RecalculateBatchTaskDAO dao = new RecalculateBatchTaskDAO();
		int modelId = ((ModelPK) editorData.getRecalculateBatchTaskAssignments().get(0).getOwningModelRef().getPrimaryKey()).getModelId();
		dao.update(pk.getRecalculateBatchTaskId(), editorData.getBudgetCycleId(), modelId, getUserId(), editorData.getIdentifier(), editorData.getDescription());
		//dao.update(pk.getRecalculateBatchTaskId(), editorData.getBudgetCycleId(), editorData.getModelId(), getUserId(), editorData.getIdentifier(), editorData.getDescription());
	}

	private int completeInsertSetup(RecalculateBatchTaskImpl editorData) throws Exception {
		RecalculateBatchTaskDAO dao = new RecalculateBatchTaskDAO();
		if (editorData.getPrimaryKey() == null) {
			int modelId = ((ModelPK) editorData.getRecalculateBatchTaskAssignments().get(0).getOwningModelRef().getPrimaryKey()).getModelId();
			return dao.insert(editorData.getBudgetCycleId(), modelId, getUserId(), editorData.getIdentifier(), editorData.getDescription());
		}
		return 0;
	}

	private void insertIntoAdditionalTables(RecalculateBatchTaskPK pk, RecalculateBatchTaskImpl editorData) throws Exception {
		RecalculateBatchTaskDAO dao = new RecalculateBatchTaskDAO();
		dao.insertIntoAdditionalTables(editorData.getRecalculateBatchTaskAssignments(), editorData.getSelectedRecalculateBatchTaskForms(), pk.getRecalculateBatchTaskId());
	}

	private boolean validateInsert(RecalculateBatchTaskImpl editorData) throws ValidationException {
		RecalculateBatchTaskDAO dao = new RecalculateBatchTaskDAO();
		return dao.validate(editorData.getRecalculateBatchTaskAssignments(), editorData.getSelectedRecalculateBatchTaskForms());
	}

	public RecalculateBatchTaskPK copy(RecalculateBatchTaskEditorSessionCSO cso) throws ValidationException, EJBException {
		return null;
	}

	public void update(RecalculateBatchTaskEditorSessionCSO cso) throws ValidationException, EJBException {
		this.mLog.debug("update");
		Timer timer = this.mLog.isInfoEnabled() ? new Timer(this.mLog) : null;
		this.setUserId(cso.getUserId());
		RecalculateBatchTaskImpl editorData = cso.getEditorData();
		this.mThisTableKey = (RecalculateBatchTaskPK) editorData.getPrimaryKey();

		try {
			this.updateRecalculateBatchTask(mThisTableKey, editorData);
			this.updateAdditionalTables(mThisTableKey, editorData);
		} catch (ValidationException e) {
			throw new EJBException(e);
		} catch (EJBException e) {
			throw e;
		} catch (Exception e) {
			e.printStackTrace();
			throw new EJBException(e.getMessage(), e);
		} finally {
			this.setUserId(0);
			if (timer != null) {
				timer.logInfo("update", this.mThisTableKey);
			}
		}
	}

	private void updateAdditionalTables(RecalculateBatchTaskPK pk, RecalculateBatchTaskImpl editorData) throws Exception {
		
		List<DataEntryProfileRef> existingXmls = editorData.getRecalculateBatchTaskForms();
		List<DataEntryProfileRef> selectedXmls = editorData.getSelectedRecalculateBatchTaskForms();
		List<DataEntryProfileRef> deleteXmls = new ArrayList<DataEntryProfileRef>();
		List<DataEntryProfileRef> insertXmls = new ArrayList<DataEntryProfileRef>();
		deleteXmls.addAll(existingXmls);
		deleteXmls.removeAll(selectedXmls);
		insertXmls.addAll(selectedXmls);
		insertXmls.removeAll(existingXmls);
		
		List<Integer> existingRA = editorData.getRecalculateBatchTaskRespArea();
		List<RecalculateBatchTaskAssignment> newRA = editorData.getRecalculateBatchTaskAssignments();
		List<Integer> deleteRA = new ArrayList<Integer>();
		List<Integer> insertRA = new ArrayList<Integer>();
		deleteRA.addAll(existingRA);
		for (RecalculateBatchTaskAssignment rbta : newRA) {
			StructureElementPK structPk = (StructureElementPK) rbta.getOwningBudgetLocationRef().getPrimaryKey();
			deleteRA.remove((Object)structPk.getStructureElementId());
			insertRA.add(structPk.getStructureElementId());
		}
		insertRA.removeAll(existingRA);
		RecalculateBatchTaskDAO dao = new RecalculateBatchTaskDAO();
		dao.updateAdditionalTables(insertRA, deleteRA, insertXmls, deleteXmls, pk.getRecalculateBatchTaskId());
		
	}

	public void delete(int userId, Object paramKey) throws ValidationException, EJBException {
		if (this.mLog.isDebugEnabled()) {
			this.mLog.debug("delete", paramKey);
		}

		Timer timer = this.mLog.isInfoEnabled() ? new Timer(this.mLog) : null;
		this.setUserId(userId);
		this.mThisTableKey = (RecalculateBatchTaskPK) paramKey;

		try {
			this.deleteDataFromOtherTables(this.mThisTableKey);
			RecalculateBatchTaskDAO dao = new RecalculateBatchTaskDAO();
			dao.doRemove(this.mThisTableKey);
		} catch (EJBException e) {
			throw e;
		} catch (Exception e) {
			e.printStackTrace();
			throw new EJBException(e.getMessage(), e);
		} finally {
			this.setUserId(0);
			if (timer != null) {
				timer.logInfo("delete", this.mThisTableKey);
			}

		}

	}

	private void deleteDataFromOtherTables(RecalculateBatchTaskPK pk) throws Exception {
		RecalculateBatchTaskDAO dao = new RecalculateBatchTaskDAO();
		dao.deleteDependants(pk);
	}

	public void ejbCreate() throws EJBException {
	}

	public void ejbRemove() {
	}

	public void setSessionContext(SessionContext context) {
		this.mSessionContext = context;
	}

	public void ejbActivate() {
	}

	public void ejbPassivate() {
	}

	private RecalculateBatchTaskAccessor getRecalculateBatchTaskAccessor() throws Exception {
		if (this.mRecalculateBatchTaskAccessor == null) {
			this.mRecalculateBatchTaskAccessor = new RecalculateBatchTaskAccessor(this.getInitialContext());
		}

		return this.mRecalculateBatchTaskAccessor;
	}

	private void sendEntityEventMessage(String tableName, PrimaryKey pk, int changeType) {
		try {
			JmsConnectionImpl e = new JmsConnectionImpl(this.getInitialContext(), 3, "entityEventTopic");
			e.createSession();
			EntityEventMessage em = new EntityEventMessage(tableName, pk, changeType, this.getClass().getName());
			this.mLog.debug("update", "sending event message: " + em.toString());
			e.send(em);
			e.closeSession();
			e.closeConnection();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public int issueRecalculateBatchTask(EntityRef ref, int userId, int issuingTaskId) throws EJBException {
		try {
			RecalculateBatchTaskEditorSessionServer recalBatchTashEditorSessionServer = new RecalculateBatchTaskEditorSessionServer(this.getInitialContext(), true);
			RecalculateBatchTaskImpl recalBatchTaskImpl = recalBatchTashEditorSessionServer.getItemData(ref.getPrimaryKey()).getEditorData();
			RecalculateBatchTaskRequest request = new RecalculateBatchTaskRequest(recalBatchTaskImpl.getIdentifier(), recalBatchTaskImpl.getUserId(), recalBatchTaskImpl.getModelId(), recalBatchTaskImpl.getBudgetCycleId(), recalBatchTaskImpl.getRecalculateBatchTaskForms(), recalBatchTaskImpl.getRecalculateBatchTaskRespArea());
			int taskId = TaskMessageFactory.issueNewTask(new InitialContext(), false, request, userId, issuingTaskId);
			this.mLog.debug("RecalculateBatchTask", "taskId=" + taskId);
			return taskId;
		} catch (Exception e) {
			e.printStackTrace();
			throw new EJBException(e);
		}
	}

	public int issueTestTask(Integer testid, int userId) throws EJBException {
		try {
			new RecalculateBatchTaskEditorSessionServer(this.getInitialContext(), true);
			TestTaskRequest request = new TestTaskRequest();
			return TaskMessageFactory.issueNewTask(new InitialContext(), false, request, userId, 0);
		} catch (Exception e) {
			e.printStackTrace();
			throw new EJBException(e);
		}
	}

	public int issueTestRollbackTask(int userId) throws EJBException {
		try {
			TestRollbackTaskRequest e = new TestRollbackTaskRequest();
			return TaskMessageFactory.issueNewTask(new InitialContext(), false, e, userId, 0);
		} catch (Exception e) {
			e.printStackTrace();
			throw new EJBException(e);
		}
	}
}
