// Decompiled by: Fernflower v0.8.6
// Date: 12.08.2012 13:06:18
// Copyright: 2008-2012, Stiver
// Home page: http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.api.extsys;

import com.cedar.cp.api.base.CPConnection;
import com.cedar.cp.api.base.CPException;
import com.cedar.cp.api.base.EntityList;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.extsys.ExternalSystemRef;
import com.cedar.cp.api.extsys.RemoteFileSystemResource;
import com.cedar.cp.api.model.FinanceCubeRef;
import com.cedar.cp.dto.extsys.ExternalSystemEditorSessionCSO;
import com.cedar.cp.dto.extsys.ExternalSystemEditorSessionSSO;
import com.cedar.cp.dto.extsys.ExternalSystemImpl;
import com.cedar.cp.dto.extsys.ExternalSystemPK;
import com.cedar.cp.ejb.api.base.AbstractSession;
import com.cedar.cp.ejb.api.extsys.ExternalSystemEditorSessionHome;
import com.cedar.cp.ejb.api.extsys.ExternalSystemEditorSessionLocal;
import com.cedar.cp.ejb.api.extsys.ExternalSystemEditorSessionLocalHome;
import com.cedar.cp.ejb.api.extsys.ExternalSystemEditorSessionRemote;
import com.cedar.cp.ejb.impl.extsys.ExternalSystemEditorSessionSEJB;
import com.cedar.cp.util.Log;
import com.cedar.cp.util.Timer;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.List;
import javax.ejb.CreateException;
import javax.naming.Context;

public class ExternalSystemEditorSessionServer extends AbstractSession {

	private static final String REMOTE_JNDI_NAME = "ejb/ExternalSystemEditorSessionRemoteHome";
	private static final String LOCAL_JNDI_NAME = "ejb/ExternalSystemEditorSessionLocalHome";
	protected ExternalSystemEditorSessionRemote mRemote;
	protected ExternalSystemEditorSessionLocal mLocal;
	private Log mLog = new Log(this.getClass());
	ExternalSystemEditorSessionSEJB server = new ExternalSystemEditorSessionSEJB();

	public ExternalSystemEditorSessionServer(CPConnection conn_) {
		super(conn_);
	}

	public ExternalSystemEditorSessionServer(Context context_, boolean remote) {
		super(context_, remote);
	}

	private ExternalSystemEditorSessionSEJB getRemote() throws CreateException, RemoteException, CPException {
//		if (this.mRemote == null) {
//			String jndiName = this.getRemoteJNDIName();
//
//			try {
//				ExternalSystemEditorSessionHome e = (ExternalSystemEditorSessionHome) this.getHome(jndiName, ExternalSystemEditorSessionHome.class);
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

	private ExternalSystemEditorSessionSEJB getLocal() throws CPException {
//		if (this.mLocal == null) {
//			try {
//				ExternalSystemEditorSessionLocalHome e = (ExternalSystemEditorSessionLocalHome) this.getLocalHome(this.getLocalJNDIName());
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

	public ExternalSystemEditorSessionSSO getNewItemData() throws ValidationException, CPException {
		Timer timer = this.mLog.isDebugEnabled() ? new Timer(this.mLog) : null;

		try {
			ExternalSystemEditorSessionSSO e = null;
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

	public ExternalSystemEditorSessionSSO getItemData(Object key) throws CPException, ValidationException {
		Timer timer = this.mLog.isDebugEnabled() ? new Timer(this.mLog) : null;

		try {
			ExternalSystemEditorSessionSSO e = null;
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

	public ExternalSystemPK insert(ExternalSystemImpl editorData) throws CPException, ValidationException {
		Timer timer = this.mLog.isDebugEnabled() ? new Timer(this.mLog) : null;

		try {
			ExternalSystemPK e = null;
			if (this.isRemoteConnection()) {
				e = this.getRemote().insert(new ExternalSystemEditorSessionCSO(this.getUserId(), editorData));
			} else {
				e = this.getLocal().insert(new ExternalSystemEditorSessionCSO(this.getUserId(), editorData));
			}

			if (timer != null) {
				timer.logDebug("insert", e);
			}

			return e;
		} catch (Exception var4) {
			throw this.unravelException(var4);
		}
	}

	public ExternalSystemPK copy(ExternalSystemImpl editorData) throws CPException, ValidationException {
		Timer timer = this.mLog.isDebugEnabled() ? new Timer(this.mLog) : null;

		try {
			ExternalSystemPK e = null;
			if (this.isRemoteConnection()) {
				e = this.getRemote().copy(new ExternalSystemEditorSessionCSO(this.getUserId(), editorData));
			} else {
				e = this.getLocal().copy(new ExternalSystemEditorSessionCSO(this.getUserId(), editorData));
			}

			if (timer != null) {
				timer.logDebug("insert", e);
			}

			return e;
		} catch (Exception var4) {
			throw this.unravelException(var4);
		}
	}

	public void update(ExternalSystemImpl editorData) throws CPException, ValidationException {
		Timer timer = this.mLog.isDebugEnabled() ? new Timer(this.mLog) : null;

		try {
			if (this.isRemoteConnection()) {
				this.getRemote().update(new ExternalSystemEditorSessionCSO(this.getUserId(), editorData));
			} else {
				this.getLocal().update(new ExternalSystemEditorSessionCSO(this.getUserId(), editorData));
			}

			if (timer != null) {
				timer.logDebug("update", editorData.getPrimaryKey());
			}

		} catch (Exception var4) {
			throw this.unravelException(var4);
		}
	}

	public EntityList getCompanies(int systemId, int systemType) throws ValidationException {
		try {
			return this.isRemoteConnection() ? this.getRemote().getCompanies(systemId, systemType) : this.getLocal().getCompanies(systemId, systemType);
		} catch (Exception var4) {
			throw this.unravelException(var4);
		}
	}

	public EntityList getFinanceCalendarYears(int systemId, int systemType, String company) throws ValidationException {
		try {
			return this.isRemoteConnection() ? this.getRemote().getFinanceCalendarYears(systemId, systemType, company) : this.getLocal().getFinanceCalendarYears(systemId, systemType, company);
		} catch (Exception var5) {
			throw this.unravelException(var5);
		}
	}

	public EntityList getGlobalFinanceCalendarYears(int systemId, int systemType, List<String> companies) throws ValidationException {
		try {
			return this.isRemoteConnection() ? this.getRemote().getGlobalFinanceCalendarYears(systemId, systemType, companies) : this.getLocal().getGlobalFinanceCalendarYears(systemId, systemType, companies);
		} catch (Exception var5) {
			throw this.unravelException(var5);
		}
	}

	public EntityList getFinancePeriods(int systemId, int systemType, String companyCode, int year) throws ValidationException {
		try {
			return this.isRemoteConnection() ? this.getRemote().getFinancePeriods(systemId, systemType, companyCode, year) : this.getLocal().getFinancePeriods(systemId, systemType, companyCode, year);
		} catch (Exception var6) {
			throw this.unravelException(var6);
		}
	}

	public EntityList getFinanceLedgers(int systemId, int systemType, String company) throws ValidationException {
		try {
			return this.isRemoteConnection() ? this.getRemote().getFinanceLedgers(systemId, systemType, company) : this.getLocal().getFinanceLedgers(systemId, systemType, company);
		} catch (Exception var5) {
			throw this.unravelException(var5);
		}
	}

	public EntityList getFinanceDimensions(int systemId, int systemType, String company, String ledger) throws ValidationException {
		try {
			return this.isRemoteConnection() ? this.getRemote().getFinanceDimensions(systemId, systemType, company, ledger) : this.getLocal().getFinanceDimensions(systemId, systemType, company, ledger);
		} catch (Exception var6) {
			throw this.unravelException(var6);
		}
	}

	public EntityList getGlobalFinanceDimensions(int systemId, int systemType, List<String> companies, String ledger) throws ValidationException {
		try {
			return this.isRemoteConnection() ? this.getRemote().getGlobalFinanceDimensions(systemId, systemType, companies, ledger) : this.getLocal().getGlobalFinanceDimensions(systemId, systemType, companies, ledger);
		} catch (Exception e) {
			throw this.unravelException(e);
		}
	}

	public EntityList getFinanceValueTypes(int systemId, int systemType, String company, String ledger, String dimCodes, int startYear, int cursorNum) throws ValidationException {
		try {
			return this.isRemoteConnection() ? this.getRemote().getFinanceValueTypes(systemId, systemType, company, ledger, dimCodes, startYear, cursorNum) : this.getLocal().getFinanceValueTypes(systemId, systemType, company, ledger, dimCodes, startYear, cursorNum);
		} catch (Exception var9) {
			throw this.unravelException(var9);
		}
	}

	public EntityList getGlobalFinanceValueTypes(int systemId, int systemType, List<String> companies, String ledger, String dimCodes, int startYear, int cursorNum) throws ValidationException {
		try {
			return this.isRemoteConnection() ? this.getRemote().getGlobalFinanceValueTypes(systemId, systemType, companies, ledger, dimCodes, startYear, cursorNum) : this.getLocal().getGlobalFinanceValueTypes(systemId, systemType, companies, ledger, dimCodes, startYear, cursorNum);
		} catch (Exception var9) {
			throw this.unravelException(var9);
		}
	}

	public EntityList getFinanceHierarchies(int systemId, int systemType, String company, String ledger, String extSysDimType, String dimCode) throws ValidationException {
		try {
			return this.isRemoteConnection() ? this.getRemote().getFinanceHierarchies(systemId, systemType, company, ledger, extSysDimType, dimCode) : this.getLocal().getFinanceHierarchies(systemId, systemType, company, ledger, extSysDimType, dimCode);
		} catch (Exception var8) {
			throw this.unravelException(var8);
		}
	}

	public EntityList getGlobalFinanceHierarchies(int systemId, int systemType, List<String> companies, String ledger, String extSysDimType, String dimCode) throws ValidationException {
		try {
			return this.isRemoteConnection() ? this.getRemote().getGlobalFinanceHierarchies(systemId, systemType, companies, ledger, extSysDimType, dimCode) : this.getLocal().getGlobalFinanceHierarchies(systemId, systemType, companies, ledger, extSysDimType, dimCode);
		} catch (Exception var8) {
			throw this.unravelException(var8);
		}
	}

	public EntityList getFinanceDimElementGroups(int systemId, int systemType, String company, String ledger, String extSysDimType, String dimCode, int parentType, String parent, String accType) throws ValidationException {
		try {
			return this.isRemoteConnection() ? this.getRemote().getFinanceDimElementGroups(systemId, systemType, company, ledger, extSysDimType, dimCode, parentType, parent, accType) : this.getLocal().getFinanceDimElementGroups(systemId, systemType, company, ledger, extSysDimType, dimCode, parentType, parent, accType);
		} catch (Exception var11) {
			throw this.unravelException(var11);
		}
	}

	public EntityList getGlobalFinanceDimElementGroups(int systemId, int systemType, List<String> companies, String ledger, String extSysDimType, String dimCode, int parentType, String parent, String accType) throws ValidationException {
		try {
			return this.isRemoteConnection() ? this.getRemote().getGlobalFinanceDimElementGroups(systemId, systemType, companies, ledger, extSysDimType, dimCode, parentType, parent, accType) : this.getLocal().getGlobalFinanceDimElementGroups(systemId, systemType, companies, ledger, extSysDimType, dimCode, parentType, parent, accType);
		} catch (Exception e) {
			throw this.unravelException(e);
		}
	}

	public EntityList getFinanceHierarchyElems(int systemId, int systemType, String company, String ledger, String extSysDimType, String dimCode, String hierName, String hierType, String parent) throws ValidationException {
		try {
			return this.isRemoteConnection() ? this.getRemote().getFinanceHierarchyElems(systemId, systemType, company, ledger, extSysDimType, dimCode, hierName, hierType, parent) : this.getLocal().getFinanceHierarchyElems(systemId, systemType, company, ledger, extSysDimType, dimCode, hierName, hierType, parent);
		} catch (Exception var11) {
			throw this.unravelException(var11);
		}
	}

	public EntityList getGlobalFinanceHierarchyElems(int systemId, int systemType, List<String> companies, String ledger, String extSysDimType, String dimCode, String hierName, String hierType, String parent) throws ValidationException {
		try {
			return this.isRemoteConnection() ? this.getRemote().getGlobalFinanceHierarchyElems(systemId, systemType, companies, ledger, extSysDimType, dimCode, hierName, hierType, parent) : this.getLocal().getGlobalFinanceHierarchyElems(systemId, systemType, companies, ledger, extSysDimType, dimCode, hierName, hierType, parent);
		} catch (Exception e) {
			throw this.unravelException(e);
		}
	}

	public String getSuggestedModelVisId(int systemId, int systemType, String company) throws ValidationException {
		try {
			return this.isRemoteConnection() ? this.getRemote().getSuggestedModelVisId(systemId, systemType, company) : this.getLocal().getSuggestedModelVisId(systemId, systemType, company);
		} catch (Exception var5) {
			throw this.unravelException(var5);
		}
	}

	public int issueExternalSystemImportTask(int userId, ExternalSystemRef externalSystemRef, String sourceURL, int issuingTaskId) throws ValidationException {
		try {
			return this.isRemoteConnection() ? this.getRemote().issueExternalSystemImportTask(userId, externalSystemRef, sourceURL, issuingTaskId) : this.getLocal().issueExternalSystemImportTask(userId, externalSystemRef, sourceURL, issuingTaskId);
		} catch (Exception var6) {
			throw this.unravelException(var6);
		}
	}

	public URL initiateTransfer(URL clientURL, ExternalSystemRef externalSystemRef, byte[] data) throws ValidationException {
		try {
			return this.isRemoteConnection() ? this.getRemote().initiateTransfer(clientURL, externalSystemRef, data) : this.getLocal().initiateTransfer(clientURL, externalSystemRef, data);
		} catch (Exception var5) {
			throw this.unravelException(var5);
		}
	}

	public void appendToFile(URL url, byte[] data) throws ValidationException {
		try {
			if (this.isRemoteConnection()) {
				this.getRemote().appendToFile(url, data);
			} else {
				this.getLocal().appendToFile(url, data);
			}

		} catch (Exception var4) {
			throw this.unravelException(var4);
		}
	}

	public List<RemoteFileSystemResource> queryRemoteFileSystem(String dir) throws ValidationException {
		try {
			return this.isRemoteConnection() ? this.getRemote().queryRemoteFileSystem(dir) : this.getLocal().queryRemoteFileSystem(dir);
		} catch (Exception var3) {
			throw this.unravelException(var3);
		}
	}

	public EntityList queryDataForPushSubmission() throws ValidationException {
		try {
			return this.isRemoteConnection() ? this.getRemote().queryDataForPushSubmission() : this.getLocal().queryDataForPushSubmission();
		} catch (Exception var2) {
			throw this.unravelException(var2);
		}
	}

	public EntityList queryAllXactSubsystems(Object externalSystemPK) throws ValidationException {
		try {
			return this.isRemoteConnection() ? this.getRemote().queryAllXactSubsystems(externalSystemPK) : this.getLocal().queryAllXactSubsystems(externalSystemPK);
		} catch (Exception var3) {
			throw this.unravelException(var3);
		}
	}

	public EntityList queryAllXactAvailableColumns(int subsystemId) throws ValidationException {
		try {
			return this.isRemoteConnection() ? this.getRemote().queryAllXactAvailableColumns(subsystemId) : this.getLocal().queryAllXactAvailableColumns(subsystemId);
		} catch (Exception var3) {
			throw this.unravelException(var3);
		}
	}

	public EntityList queryXactColumnSelection(int subsystemId) throws ValidationException {
		try {
			return this.isRemoteConnection() ? this.getRemote().queryXactColumnSelection(subsystemId) : this.getLocal().queryXactColumnSelection(subsystemId);
		} catch (Exception var3) {
			throw this.unravelException(var3);
		}
	}

	public int issuePushTask(List<FinanceCubeRef> financeCubes) throws ValidationException {
		try {
			return this.isRemoteConnection() ? this.getRemote().issueExtSysE5DB2PushTask(this.getUserId(), financeCubes) : this.getLocal().issueExtSysE5DB2PushTask(this.getUserId(), financeCubes);
		} catch (Exception var3) {
			throw this.unravelException(var3);
		}
	}

	public String getRemoteJNDIName() {
		return "ejb/ExternalSystemEditorSessionRemoteHome";
	}

	public String getLocalJNDIName() {
		return "ejb/ExternalSystemEditorSessionLocalHome";
	}
}
