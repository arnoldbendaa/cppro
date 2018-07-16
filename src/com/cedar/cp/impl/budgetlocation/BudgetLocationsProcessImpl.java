package com.cedar.cp.impl.budgetlocation;

import com.cedar.cp.api.base.CPConnection;
import com.cedar.cp.api.base.EntityList;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.budgetlocation.BudgetLocationEditorSession;
import com.cedar.cp.api.budgetlocation.BudgetLocationsProcess;
import com.cedar.cp.impl.base.BusinessProcessImpl;
import com.cedar.cp.util.Log;

public class BudgetLocationsProcessImpl extends BusinessProcessImpl implements BudgetLocationsProcess {

	private Log mLog = new Log(getClass());

	public BudgetLocationsProcessImpl(CPConnection connection) {
		super(connection);
	}

	public void deleteObject(Object primaryKey) throws ValidationException {
	}

	public BudgetLocationEditorSession getBudgetLocationEditorSession(Object key) throws ValidationException {
		BudgetLocationEditorSessionImpl sess = new BudgetLocationEditorSessionImpl(this, key);
		mActiveSessions.add(sess);
		return sess;
	}

	public EntityList getModelUserSecurity() {
		try {
			return getConnection().getListHelper().getModelUserSecurity();
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("can't get getModelUserSecurity", e);
		}
	}

	public String getProcessName() {
		String ret = "Processing Budget Location";

		return ret;
	}

	protected int getProcessID() {
		return 15;
	}
}