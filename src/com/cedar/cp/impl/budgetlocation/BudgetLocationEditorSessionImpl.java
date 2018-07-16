package com.cedar.cp.impl.budgetlocation;

import java.util.Iterator;
import java.util.List;

import com.cedar.cp.api.base.CPException;
import com.cedar.cp.api.base.EntityList;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.budgetlocation.BudgetLocationEditor;
import com.cedar.cp.api.budgetlocation.BudgetLocationEditorSession;
import com.cedar.cp.api.budgetlocation.BudgetUser;
import com.cedar.cp.api.user.UserRef;
import com.cedar.cp.dto.budgetlocation.BudgetLocationEditorSessionSSO;
import com.cedar.cp.dto.budgetlocation.BudgetLocationImpl;
import com.cedar.cp.dto.user.AllNonDisabledUsersELO;
import com.cedar.cp.ejb.api.budgetlocation.BudgetLocationEditorSessionServer;
import com.cedar.cp.impl.base.BusinessSessionImpl;
import com.cedar.cp.util.Log;

public class BudgetLocationEditorSessionImpl extends BusinessSessionImpl implements BudgetLocationEditorSession {

	protected BudgetLocationEditorSessionSSO mServerSessionData;
	protected BudgetLocationImpl mEditorData;
	protected BudgetLocationEditorImpl mClientEditor;
	private Log mLog = new Log(getClass());

	public BudgetLocationEditorSessionImpl(BudgetLocationsProcessImpl process, Object key) throws ValidationException {
		super(process);
		try {
			mServerSessionData = getSessionServer().getItemData(key);
		} catch (ValidationException e) {
			throw e;
		} catch (CPException e) {
			throw new CPException(e.getMessage(), e);
		} catch (Exception e) {
			throw new RuntimeException("Can't get Budget Location", e);
		}

		mEditorData = mServerSessionData.getEditorData();
	}

	protected BudgetLocationEditorSessionServer getSessionServer() throws CPException {
		return new BudgetLocationEditorSessionServer(getConnection());
	}

	public BudgetLocationEditor getBudgetLocationEditor() {
		if (mClientEditor == null) {
			mClientEditor = new BudgetLocationEditorImpl(this, mServerSessionData, mEditorData);
			mActiveEditors.add(mClientEditor);
		}

		return mClientEditor;
	}

	public EntityList getAvailableUsers(List assignedUsers) {
		EntityList all = getConnection().getListHelper().getAllNonDisabledUsers();

		AllNonDisabledUsersELO avail = new AllNonDisabledUsersELO();
		for (int i = 0; i < all.getNumRows(); i++) {
			UserRef allRef = (UserRef) all.getValueAt(i, "User");

			Iterator iter = assignedUsers.iterator();
			boolean found = false;
			while (iter.hasNext()) {
				BudgetUser bu = (BudgetUser) iter.next();
				if (bu.getUserRef().getPrimaryKey().equals(allRef.getPrimaryKey())) {
					found = true;
					break;
				}
			}

			if (!found) {
				avail.add(allRef, (String) all.getValueAt(i, "FullName"), "");
			}

		}

		return avail;
	}

	public Object getPrimaryKey() {
		return mEditorData.getPrimaryKey();
	}

	public Object persistModifications(boolean cloneOnSave) throws CPException, ValidationException {
		mClientEditor.saveModifications();

		getSessionServer().update(mEditorData);

		return null;
	}

	public void terminate() {
	}
}