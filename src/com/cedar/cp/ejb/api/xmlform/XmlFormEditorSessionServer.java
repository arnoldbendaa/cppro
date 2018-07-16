// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:06:22
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.api.xmlform;

import com.cedar.cp.api.base.CPConnection;
import com.cedar.cp.api.base.CPException;
import com.cedar.cp.api.base.EntityList;
import com.cedar.cp.api.base.EntityRef;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.xmlform.FormDeploymentData;
import com.cedar.cp.api.xmlform.XmlFormWizardParameters;
import com.cedar.cp.dto.xmlform.XmlFormEditorSessionCSO;
import com.cedar.cp.dto.xmlform.XmlFormEditorSessionSSO;
import com.cedar.cp.dto.xmlform.XmlFormImpl;
import com.cedar.cp.dto.xmlform.XmlFormPK;
import com.cedar.cp.ejb.api.base.AbstractSession;
import com.cedar.cp.ejb.api.xmlform.XmlFormEditorSessionHome;
import com.cedar.cp.ejb.api.xmlform.XmlFormEditorSessionLocal;
import com.cedar.cp.ejb.api.xmlform.XmlFormEditorSessionLocalHome;
import com.cedar.cp.ejb.api.xmlform.XmlFormEditorSessionRemote;
import com.cedar.cp.util.Log;
import com.cedar.cp.util.Timer;

import java.rmi.RemoteException;
import java.util.List;
import java.util.Map;

import javax.ejb.CreateException;
import javax.naming.Context;

public class XmlFormEditorSessionServer extends AbstractSession {

	private static final String REMOTE_JNDI_NAME = "ejb/XmlFormEditorSessionRemoteHome";
	private static final String LOCAL_JNDI_NAME = "ejb/XmlFormEditorSessionLocalHome";
	protected XmlFormEditorSessionRemote mRemote;
	protected XmlFormEditorSessionLocal mLocal;
	private Log mLog = new Log(this.getClass());

	public XmlFormEditorSessionServer(CPConnection conn_) {
		super(conn_);
	}

	public XmlFormEditorSessionServer(Context context_, boolean remote) {
		super(context_, remote);
	}

	private XmlFormEditorSessionRemote getRemote() throws CreateException, RemoteException, CPException {
		if (this.mRemote == null) {
			String jndiName = this.getRemoteJNDIName();

			try {
				XmlFormEditorSessionHome e = (XmlFormEditorSessionHome) this.getHome(jndiName, XmlFormEditorSessionHome.class);
				this.mRemote = e.create();
			} catch (CreateException var3) {
				this.removeFromCache(jndiName);
				var3.printStackTrace();
				throw new CPException("getRemote " + jndiName + " CreateException", var3);
			} catch (RemoteException var4) {
				this.removeFromCache(jndiName);
				var4.printStackTrace();
				throw new CPException("getRemote " + jndiName + " RemoteException", var4);
			}
		}

		return this.mRemote;
	}

	private XmlFormEditorSessionLocal getLocal() throws CPException {
		if (this.mLocal == null) {
			try {
				XmlFormEditorSessionLocalHome e = (XmlFormEditorSessionLocalHome) this.getLocalHome(this.getLocalJNDIName());
				this.mLocal = e.create();
			} catch (CreateException var2) {
				throw new CPException("can\'t create local session for " + this.getLocalJNDIName(), var2);
			}
		}

		return this.mLocal;
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

	public XmlFormEditorSessionSSO getNewItemData() throws ValidationException, CPException {
		Timer timer = this.mLog.isDebugEnabled() ? new Timer(this.mLog) : null;

		try {
			XmlFormEditorSessionSSO e = null;
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

	public XmlFormEditorSessionSSO getItemData(Object key) throws CPException, ValidationException {
		Timer timer = this.mLog.isDebugEnabled() ? new Timer(this.mLog) : null;

		try {
			XmlFormEditorSessionSSO e = null;
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

	public XmlFormPK insert(XmlFormImpl editorData) throws CPException, ValidationException {
		Timer timer = this.mLog.isDebugEnabled() ? new Timer(this.mLog) : null;

		try {
			XmlFormPK e = null;
			if (this.isRemoteConnection()) {
				e = this.getRemote().insert(new XmlFormEditorSessionCSO(this.getUserId(), editorData));
			} else {
				e = this.getLocal().insert(new XmlFormEditorSessionCSO(this.getUserId(), editorData));
			}

			if (timer != null) {
				timer.logDebug("insert", e);
			}

			return e;
		} catch (Exception var4) {
			throw this.unravelException(var4);
		}
	}

	public XmlFormPK copy(XmlFormImpl editorData) throws CPException, ValidationException {
		Timer timer = this.mLog.isDebugEnabled() ? new Timer(this.mLog) : null;

		try {
			XmlFormPK e = null;
			if (this.isRemoteConnection()) {
				e = this.getRemote().copy(new XmlFormEditorSessionCSO(this.getUserId(), editorData));
			} else {
				e = this.getLocal().copy(new XmlFormEditorSessionCSO(this.getUserId(), editorData));
			}

			if (timer != null) {
				timer.logDebug("insert", e);
			}

			return e;
		} catch (Exception var4) {
			throw this.unravelException(var4);
		}
	}

	public void update(XmlFormImpl editorData) throws CPException, ValidationException {
		Timer timer = this.mLog.isDebugEnabled() ? new Timer(this.mLog) : null;

		try {
			if (this.isRemoteConnection()) {
				this.getRemote().update(new XmlFormEditorSessionCSO(this.getUserId(), editorData));
			} else {
				this.getLocal().update(new XmlFormEditorSessionCSO(this.getUserId(), editorData));
			}

			if (timer != null) {
				timer.logDebug("update", editorData.getPrimaryKey());
			}

		} catch (Exception var4) {
			throw this.unravelException(var4);
		}
	}

	public Map invokeOnServer(List inputs) throws ValidationException, CPException {
		try {
			return this.isRemoteConnection() ? this.getRemote().invokeOnServer(inputs) : this.getLocal().invokeOnServer(inputs);
		} catch (Exception var3) {
			throw this.unravelException(var3);
		}
	}

	public XmlFormWizardParameters getFinanceXMLFormWizardData(int financeCubeId) throws ValidationException {
		try {
			return this.isRemoteConnection() ? this.getRemote().getFinanceXMLFormWizardData(financeCubeId, this.getUserId()) : this.getLocal().getFinanceXMLFormWizardData(financeCubeId, this.getUserId());
		} catch (Exception var3) {
			throw this.unravelException(var3);
		}
	}

	public void deleteFormAndProfiles(Object primaryKey_) throws ValidationException, CPException {
		Timer timer = this.mLog.isDebugEnabled() ? new Timer(this.mLog) : null;

		try {
			if (this.isRemoteConnection()) {
				this.getRemote().deleteFormAndProfiles(this.getUserId(), primaryKey_);
			} else {
				this.getLocal().deleteFormAndProfiles(this.getUserId(), primaryKey_);
			}

			if (timer != null) {
				timer.logDebug("delete", primaryKey_.toString());
			}

		} catch (Exception var4) {
			throw this.unravelException(var4);
		}
	}

	public void deleteFormProfiles(Object primaryKey_, String subject, String messageText) throws ValidationException, CPException {
		Timer timer = mLog.isDebugEnabled() ? new Timer(mLog) : null;
		try {
			if (isRemoteConnection())
				getRemote().deleteFormProfiles(getUserId(), primaryKey_, subject, messageText);
			else {
				getLocal().deleteFormProfiles(getUserId(), primaryKey_, subject, messageText);
			}
			if (timer != null)
				timer.logDebug("delete", primaryKey_.toString());
		} catch (Exception e) {
			throw unravelException(e);
		}
	}
	
    public void deleteFormProfiles(Object primaryKey_, String subject, String messageText, Boolean mobile) throws ValidationException, CPException {
        Timer timer = mLog.isDebugEnabled() ? new Timer(mLog) : null;
        try {
            if (isRemoteConnection())
                getRemote().deleteFormProfiles(getUserId(), primaryKey_, subject, messageText, mobile);
            else {
                getLocal().deleteFormProfiles(getUserId(), primaryKey_, subject, messageText, mobile);
            }
            if (timer != null)
                timer.logDebug("delete", primaryKey_.toString());
        } catch (Exception e) {
            throw unravelException(e);
        }
    }

	public int processFormDeployment(FormDeploymentData data) throws ValidationException, CPException {
		try {
			return this.isRemoteConnection() ? this.getRemote().processFormDeployment(this.getUserId(), data) : this.getLocal().processFormDeployment(this.getUserId(), data);
		} catch (Exception var3) {
			throw this.unravelException(var3);
		}
	}

	public int[] issueCellCalcRebuildTask(List<EntityRef> rebuildList) throws ValidationException {
		try {
			return this.isRemoteConnection() ? this.getRemote().issueCellCalcRebuildTask(this.getUserId(), rebuildList) : this.getLocal().issueCellCalcRebuildTask(this.getUserId(), rebuildList);
		} catch (Exception var3) {
			throw this.unravelException(var3);
		}
	}

	public Object[] getExcelFile(Object pk) throws ValidationException, CPException {
		try {
			if (isRemoteConnection())
				return getRemote().getExcelFile(pk);
			else {
				return getLocal().getExcelFile(pk);
			}
		} catch (Exception e) {
			throw unravelException(e);
		}
	}

	public boolean saveJsonForm(Object pk, String json, int versionNumber, int userId) throws ValidationException, CPException {
		try {
			if (isRemoteConnection())
				return getRemote().saveJsonForm(pk, json, versionNumber, userId);
			else {
				return getLocal().saveJsonForm(pk, json, versionNumber, userId);
			}
		} catch (Exception e) {
			throw unravelException(e);
		}
	}

	public String getRemoteJNDIName() {
		return "ejb/XmlFormEditorSessionRemoteHome";
	}

	public String getLocalJNDIName() {
		return "ejb/XmlFormEditorSessionLocalHome";
	}

}
