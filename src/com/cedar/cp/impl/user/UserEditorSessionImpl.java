package com.cedar.cp.impl.user;

import com.cedar.cp.api.base.CPException;
import com.cedar.cp.api.base.EntityList;
import com.cedar.cp.api.base.EntityRef;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.role.RoleRef;
import com.cedar.cp.api.user.UserEditor;
import com.cedar.cp.api.user.UserEditorSession;
import com.cedar.cp.dto.role.AllRolesELO;
import com.cedar.cp.dto.user.UserEditorSessionCSO;
import com.cedar.cp.dto.user.UserEditorSessionSSO;
import com.cedar.cp.dto.user.UserImpl;
import com.cedar.cp.ejb.api.user.UserEditorSessionServer;
import com.cedar.cp.ejb.impl.user.UserEditorSessionSEJB;
import com.cedar.cp.impl.base.BusinessSessionImpl;
import com.cedar.cp.impl.user.UserEditorImpl;
import com.cedar.cp.impl.user.UsersProcessImpl;
import com.cedar.cp.util.Log;
import com.softproideas.commons.context.CPContextHolder;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

public class UserEditorSessionImpl extends BusinessSessionImpl implements UserEditorSession {

	protected UserEditorSessionSSO mServerSessionData;
	protected UserImpl mEditorData;
	protected UserEditorImpl mClientEditor;
	private Log mLog = new Log(this.getClass());
	UserEditorSessionSEJB server = new UserEditorSessionSEJB();
	int userId=0;
    @Autowired
    CPContextHolder cpContextHolder;

	public UserEditorSessionImpl(UsersProcessImpl process, Object key) throws ValidationException {
		super(process);
//if(cpContextHolder==null)
//	cpContextHolder.init(process.getContext());
		userId = process.getConnection().getUserContext().getUserId();
		try {
			if (key == null) {
//				this.mServerSessionData = this.getSessionServer().getNewItemData();
				this.mServerSessionData = server.getNewItemData(userId);
			} else {
//				this.mServerSessionData = this.getSessionServer().getItemData(key);
				this.mServerSessionData = server.getItemData(userId, key);
			}
		} catch (ValidationException var4) {
			throw var4;
		} catch (Exception var5) {
			throw new RuntimeException("Can\'t get User", var5);
		}
		this.mEditorData = this.mServerSessionData.getEditorData();
	}

	protected UserEditorSessionServer getSessionServer() throws CPException {
		return new UserEditorSessionServer(this.getConnection());
	}

	public UserEditor getUserEditor() {
		if (this.mClientEditor == null) {
			this.mClientEditor = new UserEditorImpl(this, this.mServerSessionData, this.mEditorData);
			this.mActiveEditors.add(this.mClientEditor);
		}

		return this.mClientEditor;
	}

	public Object getPrimaryKey() {
		return this.mEditorData.getPrimaryKey();
	}

	public Object persistModifications(boolean cloneOnSave) throws CPException, ValidationException {
		if (this.mClientEditor != null) {
			this.mClientEditor.saveModifications();
		}

		if (this.mEditorData.getPrimaryKey() == null) {
			this.mEditorData.setPrimaryKey(server.insert(new UserEditorSessionCSO(userId, this.mEditorData)));
//			this.mEditorData.setPrimaryKey(this.getSessionServer().insert(this.mEditorData));
		} else if (cloneOnSave) {
			this.mEditorData.setPrimaryKey(server.copy(new UserEditorSessionCSO(userId, this.mEditorData)));
//			this.mEditorData.setPrimaryKey(this.getSessionServer().copy(this.mEditorData));
		} else {
//			this.getSessionServer().update(this.mEditorData);
			server.update(new UserEditorSessionCSO(userId, this.mEditorData));
		}

		return this.mEditorData.getPrimaryKey();
	}

	public void terminate() {
	}

	public int getMinimumPasswordSize() {
		return this.getConnection().getUserContext().getMinimumPasswordSize();
	}

	public boolean canChangePassword() {
		return this.getConnection().getUserContext().userCanChangePassword();
	}

	public EntityList getAvailableRoles() {
		EntityList all = this.getConnection().getListHelper().getAllRoles();
		List selected = this.mEditorData.getRoles();
		AllRolesELO avail = new AllRolesELO();
		String description = "";

		for (int i = 0; i < all.getNumRows(); ++i) {
			RoleRef allER = (RoleRef) all.getValueAt(i, "Role");
			description = all.getValueAt(i, "Description").toString();
			if (!selected.contains(allER)) {
				avail.add(allER, allER.getNarrative(), description);
			}
		}

		return avail;
	}
	
	public EntityList getHiddenRoles() {
		return this.getConnection().getListHelper().getAllHiddenRoles();
	}

}
