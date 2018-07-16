package com.cedar.cp.impl.budgetlocation;

import java.util.Iterator;
import java.util.List;
import com.cedar.cp.api.base.CPException;
import com.cedar.cp.api.base.EntityList;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.budgetlocation.BudgetUser;
import com.cedar.cp.api.budgetlocation.UserModelSecurityEditor;
import com.cedar.cp.api.budgetlocation.UserModelSecurityEditorSession;
import com.cedar.cp.api.user.UserRef;
import com.cedar.cp.dto.budgetlocation.UserModelSecurityEditorSessionSSO;
import com.cedar.cp.dto.budgetlocation.UserModelSecurityImpl;
import com.cedar.cp.dto.user.AllNonDisabledUsersELO;
import com.cedar.cp.ejb.api.budgetlocation.UserModelSecurityEditorSessionServer;
import com.cedar.cp.impl.base.BusinessSessionImpl;
import com.cedar.cp.util.Log;

public class UserModelSecurityEditorSessionImpl extends BusinessSessionImpl implements UserModelSecurityEditorSession {

	protected UserModelSecurityEditorSessionSSO mServerSessionData;
	protected UserModelSecurityImpl mEditorData;
	protected UserModelSecurityEditorImpl mClientEditor;
	private Log mLog = new Log(getClass());

	public UserModelSecurityEditorSessionImpl(UserModelSecurityProcessImpl process, Object key) throws ValidationException {
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

	protected UserModelSecurityEditorSessionServer getSessionServer() throws CPException {
		return new UserModelSecurityEditorSessionServer(getConnection());
	}

	public UserModelSecurityEditor getUserModelSecurityEditor() {
		if (mClientEditor == null) {
			mClientEditor = new UserModelSecurityEditorImpl(this, mServerSessionData, mEditorData);
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

	public void doImport(List<String[]> impValues) throws ValidationException {
		getSessionServer().doImport(impValues);
	}

	public void terminate() {
	}
}