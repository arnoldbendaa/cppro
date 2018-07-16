package com.cedar.cp.impl.importtask;

import com.cedar.cp.api.importtask.ImportTaskEditorSession;
import com.cedar.cp.api.base.CPConnection;
import com.cedar.cp.api.base.CPException;
import com.cedar.cp.api.base.EntityList;
import com.cedar.cp.api.base.EntityRef;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.ejb.api.importtask.ImportTaskEditorSessionServer;
import com.cedar.cp.impl.importtask.ImportTaskEditorSessionImpl;
import com.cedar.cp.api.importtask.ImportTasksProcess;
import com.cedar.cp.impl.base.BusinessProcessImpl;
import com.cedar.cp.util.Log;
import com.cedar.cp.util.Timer;

public class ImportTasksProcessImpl extends BusinessProcessImpl implements ImportTasksProcess {

   private Log mLog = new Log(this.getClass());

	public ImportTasksProcessImpl(CPConnection connection) {
		super(connection);
	}

	public void deleteObject(Object primaryKey) throws ValidationException {
		Timer timer = this.mLog.isDebugEnabled() ? new Timer(this.mLog) : null;
		ImportTaskEditorSessionServer es = new ImportTaskEditorSessionServer(this.getConnection());

		try {
			es.delete(primaryKey);
		} catch (ValidationException var5) {
			throw var5;
		} catch (CPException var6) {
			throw new RuntimeException("can\'t delete " + primaryKey, var6);
		}

		if (timer != null) {
			timer.logDebug("deleteObject", primaryKey);
		}
	}

	public ImportTaskEditorSession getImportTaskEditorSession(Object key) throws ValidationException {
		ImportTaskEditorSessionImpl sess = new ImportTaskEditorSessionImpl(this, key);
		this.mActiveSessions.add(sess);
		return sess;
	}

	public EntityList getAllImportTasks() {
		try {
			return this.getConnection().getListHelper().getAllImportTasks();
		} catch (Exception var2) {
			var2.printStackTrace();
			throw new RuntimeException("can\'t get AllImportTasks", var2);
		}
	}

	public String getProcessName() {
		String ret = "Processing ImportTask";
		return ret;
	}

	protected int getProcessID() {
		return 36;
	}

	public int issueImportTask(EntityRef ref, int userId, String externalSystemVisId) throws ValidationException {
		ImportTaskEditorSessionServer server = new ImportTaskEditorSessionServer(this.getConnection());
		return server.issueImportTask(ref, userId, externalSystemVisId);
	}

	public int issueTestTask(Integer testId, int userId) throws ValidationException {
		ImportTaskEditorSessionServer server = new ImportTaskEditorSessionServer(this.getConnection());
		return server.issueTestTask(testId, userId);
	}

	public int issueTestRollbackTask() throws ValidationException {
		ImportTaskEditorSessionServer server = new ImportTaskEditorSessionServer(this.getConnection());
		return server.issueTestRollbackTask();
	}

}
