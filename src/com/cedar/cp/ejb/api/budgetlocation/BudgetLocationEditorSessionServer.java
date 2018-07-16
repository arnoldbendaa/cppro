package com.cedar.cp.ejb.api.budgetlocation;

import com.cedar.cp.api.base.CPConnection;
import com.cedar.cp.api.base.CPException;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.dto.budgetlocation.BudgetLocationEditorSessionCSO;
import com.cedar.cp.dto.budgetlocation.BudgetLocationEditorSessionSSO;
import com.cedar.cp.dto.budgetlocation.BudgetLocationImpl;
import com.cedar.cp.ejb.api.base.AbstractSession;
import com.cedar.cp.ejb.api.budgetlocation.BudgetLocationEditorSessionHome;
import com.cedar.cp.ejb.api.budgetlocation.BudgetLocationEditorSessionLocal;
import com.cedar.cp.ejb.api.budgetlocation.BudgetLocationEditorSessionLocalHome;
import com.cedar.cp.ejb.api.budgetlocation.BudgetLocationEditorSessionRemote;
import com.cedar.cp.ejb.impl.budgetlocation.BudgetLocationEditorSessionSEJB;
import com.cedar.cp.util.Log;
import com.cedar.cp.util.Timer;
import java.rmi.RemoteException;
import java.util.List;

import javax.ejb.CreateException;
import javax.naming.Context;

public class BudgetLocationEditorSessionServer extends AbstractSession {

	private static final String REMOTE_JNDI_NAME = "ejb/BudgetLocationEditorSessionRemoteHome";
	private static final String LOCAL_JNDI_NAME = "ejb/BudgetLocationEditorSessionLocalHome";
	protected BudgetLocationEditorSessionRemote mRemote;
	protected BudgetLocationEditorSessionLocal mLocal;
	private Log mLog = new Log(this.getClass());
	public static BudgetLocationEditorSessionSEJB server = new BudgetLocationEditorSessionSEJB();

	public BudgetLocationEditorSessionServer(CPConnection conn_) {
		super(conn_);
	}

	public BudgetLocationEditorSessionServer(Context context_, boolean remote) {
		super(context_, remote);
	}

	private BudgetLocationEditorSessionSEJB getRemote() throws CreateException, RemoteException, CPException {
//		if (this.mRemote == null) {
//			String jndiName = this.getRemoteJNDIName();
//
//			try {
//				BudgetLocationEditorSessionHome e = (BudgetLocationEditorSessionHome) this.getHome(jndiName, BudgetLocationEditorSessionHome.class);
//				this.mRemote = e.create();
//			} catch (CreateException var3) {
//				this.removeFromCache(jndiName);
//				var3.printStackTrace();
//				throw new CPException("getRemote " + jndiName + " CreateException", var3);
//			} catch (RemoteException var4) {
//				this.removeFromCache(jndiName);
//				var4.printStackTrace();
//				throw new CPException("getRemote " + jndiName + " RemoteException", var4);
//			}
//		}
//
//		return this.mRemote;
		return this.server;
	}

	private BudgetLocationEditorSessionSEJB getLocal() throws CPException {
//		if (this.mLocal == null) {
//			try {
//				BudgetLocationEditorSessionLocalHome e = (BudgetLocationEditorSessionLocalHome) this.getLocalHome(this.getLocalJNDIName());
//				this.mLocal = e.create();
//			} catch (CreateException var2) {
//				throw new CPException("can\'t create local session for " + this.getLocalJNDIName(), var2);
//			}
//		}
//
//		return this.mLocal;
		return this.server;
	}

	public void removeSession() throws CPException {
	}

	public BudgetLocationEditorSessionSSO getItemData(Object primaryKey_) throws ValidationException {
		Timer timer = this.mLog.isDebugEnabled() ? new Timer(this.mLog) : null;

		try {
			BudgetLocationEditorSessionSSO e = null;
			if (this.isRemoteConnection()) {
				e = this.getRemote().getItemData(primaryKey_);
			} else {
				e = this.getLocal().getItemData(primaryKey_);
			}

			if (timer != null) {
				timer.logDebug("getItemData", primaryKey_.toString());
			}

			return e;
		} catch (Exception var4) {
			throw this.unravelException(var4);
		}
	}

	public void delete(Object primaryKey_) throws ValidationException {
		Timer timer = this.mLog.isDebugEnabled() ? new Timer(this.mLog) : null;

		try {
			if (this.isRemoteConnection()) {
				this.getRemote().delete(primaryKey_);
			} else {
				this.getLocal().delete(primaryKey_);
			}

			if (timer != null) {
				timer.logDebug("delete", primaryKey_.toString());
			}

		} catch (Exception var4) {
			throw this.unravelException(var4);
		}
	}

	public void update(BudgetLocationImpl editorData) throws ValidationException {
		Timer timer = this.mLog.isDebugEnabled() ? new Timer(this.mLog) : null;

		try {
			if (this.isRemoteConnection()) {
				this.getRemote().update(new BudgetLocationEditorSessionCSO(getUserId(), editorData));
			} else {
				this.getLocal().update(new BudgetLocationEditorSessionCSO(getUserId(), editorData));
			}

			if (timer != null) {
				timer.logDebug("update", editorData.getPrimaryKey());
			}

		} catch (Exception var4) {
			var4.printStackTrace();
			throw this.unravelException(var4);
		}
	}

	public void copyAssignments(int userId, Object fromUserKey, List<Object> toUserKeys) throws ValidationException {
		Timer timer = mLog.isDebugEnabled() ? new Timer(mLog) : null;
		try {
			if (isRemoteConnection())
				getRemote().copyAssignments(userId, fromUserKey, toUserKeys);
			else {
				getLocal().copyAssignments(userId, fromUserKey, toUserKeys);
			}
			if (timer != null)
				timer.logDebug("copyAssignments");
		} catch (Exception e) {
			throw unravelException(e);
		}
	}

	public String getRemoteJNDIName() {
		return "ejb/BudgetLocationEditorSessionRemoteHome";
	}

	public String getLocalJNDIName() {
		return "ejb/BudgetLocationEditorSessionLocalHome";
	}
}
