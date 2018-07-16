package com.cedar.cp.impl.recalculate;

import com.cedar.cp.api.recalculate.RecalculateBatchTaskEditorSession;
import com.cedar.cp.api.base.CPConnection;
import com.cedar.cp.api.base.CPException;
import com.cedar.cp.api.base.EntityList;
import com.cedar.cp.api.base.EntityRef;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.ejb.api.recalculate.RecalculateBatchTaskEditorSessionServer;
import com.cedar.cp.impl.base.BusinessProcessImpl;
import com.cedar.cp.impl.recalculate.RecalculateBatchTaskEditorSessionImpl;
import com.cedar.cp.api.recalculate.RecalculateBatchTaskProcess;
import com.cedar.cp.util.Log;
import com.cedar.cp.util.Timer;

public class RecalculateBatchTaskProcessImpl extends BusinessProcessImpl implements RecalculateBatchTaskProcess {

   private Log mLog = new Log(this.getClass());

	public RecalculateBatchTaskProcessImpl(CPConnection connection) {
		super(connection);
	}

	public void deleteObject(Object primaryKey) throws ValidationException {
		Timer timer = this.mLog.isDebugEnabled() ? new Timer(this.mLog) : null;
		RecalculateBatchTaskEditorSessionServer es = new RecalculateBatchTaskEditorSessionServer(this.getConnection());

		try {
			es.delete(primaryKey);
		} catch (ValidationException e) {
			throw e;
		} catch (CPException e) {
			throw new RuntimeException("can\'t delete " + primaryKey, e);
		}

		if (timer != null) {
			timer.logDebug("deleteObject", primaryKey);
		}
	}

	public RecalculateBatchTaskEditorSession getRecalculateBatchTaskEditorSession(Object key) throws ValidationException {
		RecalculateBatchTaskEditorSessionImpl sess = new RecalculateBatchTaskEditorSessionImpl(this, key);
		this.mActiveSessions.add(sess);
		return sess;
	}

	public EntityList getAllRecalculateBatchTasks() {
		try {
			return this.getConnection().getListHelper().getAllRecalculateBatchTasks();
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("can\'t get AllRecalculateBatchTasks", e);
		}	
	}

	public String getProcessName() {
		String ret = "Processing RecalculateBatchTask";
		return ret;
	}

	protected int getProcessID() {
		return 105;
	}

	public int issueRecalculateBatchTask(EntityRef ref, int userId) throws ValidationException {
		RecalculateBatchTaskEditorSessionServer server = new RecalculateBatchTaskEditorSessionServer(this.getConnection());
		return server.issueRecalculateBatchTask(ref, userId);
	}

	public int issueTestTask(Integer testId, int userId) throws ValidationException {
		RecalculateBatchTaskEditorSessionServer server = new RecalculateBatchTaskEditorSessionServer(this.getConnection());
		return server.issueTestTask(testId, userId);
	}

	public int issueTestRollbackTask() throws ValidationException {
		RecalculateBatchTaskEditorSessionServer server = new RecalculateBatchTaskEditorSessionServer(this.getConnection());
		return server.issueTestRollbackTask();
	}

}
