// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:06:18
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.api.model;

import com.cedar.cp.api.base.CPConnection;
import com.cedar.cp.api.base.CPException;
import com.cedar.cp.api.base.EntityList;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.dto.model.BudgetCycleCK;
import com.cedar.cp.dto.model.BudgetCycleEditorSessionCSO;
import com.cedar.cp.dto.model.BudgetCycleEditorSessionSSO;
import com.cedar.cp.dto.model.BudgetCycleImpl;
import com.cedar.cp.ejb.api.base.AbstractSession;
import com.cedar.cp.ejb.api.model.BudgetCycleEditorSessionHome;
import com.cedar.cp.ejb.api.model.BudgetCycleEditorSessionLocal;
import com.cedar.cp.ejb.api.model.BudgetCycleEditorSessionLocalHome;
import com.cedar.cp.ejb.api.model.BudgetCycleEditorSessionRemote;
import com.cedar.cp.ejb.impl.model.BudgetCycleEditorSessionSEJB;
import com.cedar.cp.util.Log;
import com.cedar.cp.util.Timer;
import java.rmi.RemoteException;
import javax.ejb.CreateException;
import javax.naming.Context;

public class BudgetCycleEditorSessionServer extends AbstractSession {

	private static final String REMOTE_JNDI_NAME = "ejb/BudgetCycleEditorSessionRemoteHome";
	private static final String LOCAL_JNDI_NAME = "ejb/BudgetCycleEditorSessionLocalHome";
	protected BudgetCycleEditorSessionRemote mRemote;
	protected BudgetCycleEditorSessionLocal mLocal;
	private Log mLog = new Log(this.getClass());
	public static BudgetCycleEditorSessionSEJB server = new BudgetCycleEditorSessionSEJB();

	public BudgetCycleEditorSessionServer(CPConnection conn_) {
		super(conn_);
	}

	public BudgetCycleEditorSessionServer(Context context_, boolean remote) {
		super(context_, remote);
	}

	private BudgetCycleEditorSessionSEJB getRemote() throws CreateException, RemoteException, CPException {
//		if (this.mRemote == null) {
//			String jndiName = this.getRemoteJNDIName();
//
//			try {
//				BudgetCycleEditorSessionHome e = (BudgetCycleEditorSessionHome) this.getHome(jndiName, BudgetCycleEditorSessionHome.class);
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

		return this.server;
	}

	private BudgetCycleEditorSessionSEJB getLocal() throws CPException {
//		if (this.mLocal == null) {
//			try {
//				BudgetCycleEditorSessionLocalHome e = (BudgetCycleEditorSessionLocalHome) this.getLocalHome(this.getLocalJNDIName());
//				this.mLocal = e.create();
//			} catch (CreateException var2) {
//				throw new CPException("can\'t create local session for " + this.getLocalJNDIName(), var2);
//			}
//		}

		return this.server;
	}

	public void removeSession() throws CPException {
	}

	public void delete(Object primaryKey_) throws ValidationException, CPException {
		Timer timer = this.mLog.isDebugEnabled() ? new Timer(this.mLog) : null;

		try {
			if (this.isRemoteConnection()) {
				this.getRemote().delete(this.getUserId(), primaryKey_);
			} else {
				this.getLocal().delete(this.getUserId(), primaryKey_);
			}

			if (timer != null) {
				timer.logDebug("delete", primaryKey_.toString());
			}

		} catch (Exception var4) {
			throw this.unravelException(var4);
		}
	}

	public EntityList getAvailableModels() throws CPException {
		Timer timer = this.mLog.isDebugEnabled() ? new Timer(this.mLog) : null;
		// EntityList ret = this.getConnection().getListHelper().getAllModels();
		EntityList ret = this.getConnection().getListHelper().getAllModelsForLoggedUser();
		if (timer != null) {
			timer.logDebug("getModelList", "");
		}

		return ret;
	}

	public EntityList getOwnershipRefs(Object pk_) throws CPException {
		Timer timer = this.mLog.isDebugEnabled() ? new Timer(this.mLog) : null;

		try {
			EntityList e = null;
			if (this.isRemoteConnection()) {
				e = this.getRemote().getOwnershipData(this.getUserId(), pk_);
			} else {
				e = this.getLocal().getOwnershipData(this.getUserId(), pk_);
			}

			if (timer != null) {
				timer.logDebug("getOwnershipRefs", pk_ != null ? pk_.toString() : "null");
			}

			return e;
		} catch (Exception var4) {
			throw new CPException("unable to getOwnershipRefs(" + pk_ + ") from server " + var4.getMessage(), var4);
		}
	}

	public BudgetCycleEditorSessionSSO getNewItemData() throws ValidationException, CPException {
		Timer timer = this.mLog.isDebugEnabled() ? new Timer(this.mLog) : null;

		try {
			BudgetCycleEditorSessionSSO e = null;
			if (this.isRemoteConnection()) {
				e = this.getRemote().getNewItemData(this.getUserId());
			} else {
				e = this.getLocal().getNewItemData(this.getUserId());
			}

			if (timer != null) {
				timer.logDebug("getNewItemData", "");
			}

			return e;
		} catch (Exception var3) {
			throw this.unravelException(var3);
		}
	}

	public BudgetCycleEditorSessionSSO getItemData(Object key) throws CPException, ValidationException {
		Timer timer = this.mLog.isDebugEnabled() ? new Timer(this.mLog) : null;

		try {
			BudgetCycleEditorSessionSSO e = null;
			if (this.isRemoteConnection()) {
				e = this.getRemote().getItemData(this.getUserId(), key);
			} else {
				e = this.getLocal().getItemData(this.getUserId(), key);
			}

			if (timer != null) {
				timer.logDebug("getItemData", key.toString());
			}

			return e;
		} catch (Exception var4) {
			throw this.unravelException(var4);
		}
	}

	public BudgetCycleCK insert(BudgetCycleImpl editorData) throws CPException, ValidationException {
		Timer timer = this.mLog.isDebugEnabled() ? new Timer(this.mLog) : null;

		try {
			BudgetCycleCK e = null;
			if (this.isRemoteConnection()) {
				e = this.getRemote().insert(new BudgetCycleEditorSessionCSO(this.getUserId(), editorData));
			} else {
				e = this.getLocal().insert(new BudgetCycleEditorSessionCSO(this.getUserId(), editorData));
			}

			if (timer != null) {
				timer.logDebug("insert", e);
			}

			return e;
		} catch (Exception var4) {
			throw this.unravelException(var4);
		}
	}

	public BudgetCycleCK copy(BudgetCycleImpl editorData) throws CPException, ValidationException {
		Timer timer = this.mLog.isDebugEnabled() ? new Timer(this.mLog) : null;

		try {
			BudgetCycleCK e = null;
			if (this.isRemoteConnection()) {
				e = this.getRemote().copy(new BudgetCycleEditorSessionCSO(this.getUserId(), editorData));
			} else {
				e = this.getLocal().copy(new BudgetCycleEditorSessionCSO(this.getUserId(), editorData));
			}

			if (timer != null) {
				timer.logDebug("insert", e);
			}

			return e;
		} catch (Exception var4) {
			throw this.unravelException(var4);
		}
	}

	public void update(BudgetCycleImpl editorData) throws CPException, ValidationException {
		Timer timer = this.mLog.isDebugEnabled() ? new Timer(this.mLog) : null;

		try {
			if (this.isRemoteConnection()) {
				this.getRemote().update(new BudgetCycleEditorSessionCSO(this.getUserId(), editorData));
			} else {
				this.getLocal().update(new BudgetCycleEditorSessionCSO(this.getUserId(), editorData));
			}

			if (timer != null) {
				timer.logDebug("update", editorData.getPrimaryKey());
			}

		} catch (Exception var4) {
			throw this.unravelException(var4);
		}
	}

	public void clearCache(Object key) throws CPException, ValidationException {
		Timer timer = this.mLog.isDebugEnabled() ? new Timer(this.mLog) : null;
		try {
			if (this.isRemoteConnection()) {
				this.getRemote().clearCache(this.getUserId(), key);
			} else {
				this.getLocal().clearCache(this.getUserId(), key);
			}

			if (timer != null) {
				timer.logDebug("clearCache", key.toString());
			}
		} catch (Exception var4) {
			throw this.unravelException(var4);
		}
	}

	public String getRemoteJNDIName() {
		return "ejb/BudgetCycleEditorSessionRemoteHome";
	}

	public String getLocalJNDIName() {
		return "ejb/BudgetCycleEditorSessionLocalHome";
	}
}
