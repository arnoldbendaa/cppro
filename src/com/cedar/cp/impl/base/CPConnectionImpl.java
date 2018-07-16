package com.cedar.cp.impl.base;

import com.cedar.cp.api.recalculate.RecalculateBatchTaskProcess;
import com.cedar.cp.api.admin.tidytask.TidyTasksProcess;
import com.cedar.cp.api.authenticationpolicy.AuthenticationPolicysProcess;
import com.cedar.cp.api.base.AdminOnlyException;
import com.cedar.cp.api.base.BusinessProcess;
import com.cedar.cp.api.base.CPConnection;
import com.cedar.cp.api.base.CPException;
import com.cedar.cp.api.base.ClientCache;
import com.cedar.cp.api.base.ConnectionFactoryDetails;
import com.cedar.cp.api.base.EntityKeyFactory;
import com.cedar.cp.api.base.EntityList;
import com.cedar.cp.api.base.ErrorPolicyHandler;
import com.cedar.cp.api.base.InvalidCredentialsException;
import com.cedar.cp.api.base.JmsConnection;
import com.cedar.cp.api.base.JmsConnectionHelper;
import com.cedar.cp.api.base.ListHelper;
import com.cedar.cp.api.base.NetworkActivityListener;
import com.cedar.cp.api.base.ServerContext;
import com.cedar.cp.api.base.ServerEntityHelper;
import com.cedar.cp.api.base.UserContext;
import com.cedar.cp.api.base.UserDisabledException;
import com.cedar.cp.api.base.UserLicenseException;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.budgetinstruction.BudgetInstructionsProcess;
import com.cedar.cp.api.budgetlocation.BudgetLocationsProcess;
import com.cedar.cp.api.budgetlocation.UserModelSecurityProcess;
import com.cedar.cp.api.cm.ChangeMgmtsProcess;
import com.cedar.cp.api.cubeformula.CubeFormulasProcess;
import com.cedar.cp.api.currency.CurrencysProcess;
import com.cedar.cp.api.dataEntry.DataEntryProcess;
import com.cedar.cp.api.datatype.DataTypesProcess;
import com.cedar.cp.api.dimension.DimensionsProcess;
import com.cedar.cp.api.dimension.HierarchysProcess;
import com.cedar.cp.api.dimension.SecurityRangesProcess;
import com.cedar.cp.api.extendedattachment.ExtendedAttachmentsProcess;
import com.cedar.cp.api.extsys.ExtSysE5DB2PushProcess;
import com.cedar.cp.api.extsys.ExternalSystemsProcess;
import com.cedar.cp.api.impexp.ImpExpHdrsProcess;
import com.cedar.cp.api.importtask.ImportTasksProcess;
import com.cedar.cp.api.logon.AuthenticationResult;
import com.cedar.cp.api.logonhistory.LogonHistorysProcess;
import com.cedar.cp.api.masterquestion.MasterQuestionsProcess;
import com.cedar.cp.api.message.MessagesProcess;
import com.cedar.cp.api.model.BudgetCyclesProcess;
import com.cedar.cp.api.model.BudgetUsersProcess;
import com.cedar.cp.api.model.CellCalcsProcess;
import com.cedar.cp.api.model.FinanceCubesProcess;
import com.cedar.cp.api.model.ModelsProcess;
import com.cedar.cp.api.model.SecurityAccessDefsProcess;
import com.cedar.cp.api.model.SecurityGroupsProcess;
import com.cedar.cp.api.model.amm.AmmModelsProcess;
import com.cedar.cp.api.model.budgetlimit.BudgetLimitsProcess;
import com.cedar.cp.api.model.cc.CcDeploymentsProcess;
import com.cedar.cp.api.model.mapping.MappedModelsProcess;
import com.cedar.cp.api.model.globalmapping2.GlobalMappedModels2Process;
import com.cedar.cp.api.model.ra.ResponsibilityAreasProcess;
import com.cedar.cp.api.model.recharge.RechargeGroupsProcess;
import com.cedar.cp.api.model.recharge.RechargesProcess;
import com.cedar.cp.api.model.udwp.WeightingDeploymentsProcess;
import com.cedar.cp.api.model.udwp.WeightingProfilesProcess;
import com.cedar.cp.api.model.virement.VirementCategorysProcess;
import com.cedar.cp.api.model.virement.VirementRequestsProcess;
import com.cedar.cp.api.passwordhistory.PasswordHistorysProcess;
import com.cedar.cp.api.performance.PerformanceProcess;
import com.cedar.cp.api.perftest.PerfTestsProcess;
import com.cedar.cp.api.perftestrun.PerfTestRunResultsProcess;
import com.cedar.cp.api.perftestrun.PerfTestRunsProcess;
import com.cedar.cp.api.report.ReportsProcess;
import com.cedar.cp.api.report.definition.ReportDefinitionsProcess;
import com.cedar.cp.api.report.destination.external.ExternalDestinationsProcess;
import com.cedar.cp.api.report.destination.internal.InternalDestinationsProcess;
import com.cedar.cp.api.report.distribution.DistributionsProcess;
import com.cedar.cp.api.report.mappingtemplate.ReportMappingTemplatesProcess;
import com.cedar.cp.api.report.pack.ReportPacksProcess;
import com.cedar.cp.api.report.template.ReportTemplatesProcess;
import com.cedar.cp.api.report.type.ReportTypesProcess;
import com.cedar.cp.api.reset.ChallengeQuestionsProcess;
import com.cedar.cp.api.reset.UserResetLinksProcess;
import com.cedar.cp.api.role.RolesProcess;
import com.cedar.cp.api.sqlmon.SqlMonitorProcess;
import com.cedar.cp.api.systemproperty.SystemPropertysProcess;
import com.cedar.cp.api.task.TaskViewerProcess;
import com.cedar.cp.api.task.group.TaskGroupsProcess;
import com.cedar.cp.api.udeflookup.UdefLookupsProcess;
import com.cedar.cp.api.user.DataEntryProfilesProcess;
import com.cedar.cp.api.user.UsersProcess;
import com.cedar.cp.api.user.loggedinusers.LoggedInUsersProcess;
import com.cedar.cp.api.notes.NotesProcess;
import com.cedar.cp.impl.notes.NotesProcessImpl;
import com.cedar.cp.api.xmlform.XmlFormsProcess;
import com.cedar.cp.api.xmlform.convert.ExcelIOProcess;
import com.cedar.cp.api.xmlform.rebuild.FormRebuildsProcess;
import com.cedar.cp.api.xmlreport.XmlReportsProcess;
import com.cedar.cp.api.xmlreportfolder.XmlReportFoldersProcess;
import com.cedar.cp.ejb.api.logon.LogonServer;
import com.cedar.cp.impl.admin.tidytask.TidyTasksProcessImpl;
import com.cedar.cp.impl.authenticationpolicy.AuthenticationPolicysProcessImpl;
import com.cedar.cp.impl.base.ClientCacheImpl;
import com.cedar.cp.impl.base.EntityEventWatcher;
import com.cedar.cp.impl.base.EntityKeyFactoryImpl;
import com.cedar.cp.impl.base.ListHelperImpl;
import com.cedar.cp.impl.base.ServerContextImpl;
import com.cedar.cp.impl.base.UserContextImpl;
import com.cedar.cp.impl.budgetinstruction.BudgetInstructionsProcessImpl;
import com.cedar.cp.impl.budgetlocation.BudgetLocationsProcessImpl;
import com.cedar.cp.impl.budgetlocation.UserModelSecurityProcessImpl;
import com.cedar.cp.impl.cm.ChangeMgmtsProcessImpl;
import com.cedar.cp.impl.cubeformula.CubeFormulasProcessImpl;
import com.cedar.cp.impl.dataEntry.DataEntryProcessImpl;
import com.cedar.cp.impl.dataeditor.DataEditorProcessImpl;
import com.cedar.cp.api.dataeditor.DataEditorProcess;
import com.cedar.cp.impl.datatype.DataTypesProcessImpl;
import com.cedar.cp.impl.dimension.DimensionsProcessImpl;
import com.cedar.cp.impl.dimension.HierarchysProcessImpl;
import com.cedar.cp.impl.dimension.SecurityRangesProcessImpl;
import com.cedar.cp.impl.extendedattachment.ExtendedAttachmentsProcessImpl;
import com.cedar.cp.impl.extsys.ExtSysE5DB2PushProcessImpl;
import com.cedar.cp.impl.extsys.ExternalSystemsProcessImpl;
import com.cedar.cp.impl.impexp.ImpExpHdrsProcessImpl;
import com.cedar.cp.impl.importtask.ImportTasksProcessImpl;
import com.cedar.cp.impl.logonhistory.LogonHistorysProcessImpl;
import com.cedar.cp.impl.masterquestion.MasterQuestionsProcessImpl;
import com.cedar.cp.impl.message.MessagesProcessImpl;
import com.cedar.cp.impl.model.BudgetCyclesProcessImpl;
import com.cedar.cp.impl.model.BudgetUsersProcessImpl;
import com.cedar.cp.impl.model.CellCalcsProcessImpl;
import com.cedar.cp.impl.model.FinanceCubesProcessImpl;
import com.cedar.cp.impl.model.ModelsProcessImpl;
import com.cedar.cp.impl.model.SecurityAccessDefsProcessImpl;
import com.cedar.cp.impl.model.SecurityGroupsProcessImpl;
import com.cedar.cp.impl.model.amm.AmmModelsProcessImpl;
import com.cedar.cp.impl.model.budgetlimit.BudgetLimitsProcessImpl;
import com.cedar.cp.impl.model.cc.CcDeploymentsProcessImpl;
import com.cedar.cp.impl.model.globalmapping2.GlobalMappedModels2ProcessImpl;
import com.cedar.cp.impl.model.ra.ResponsibilityAreasProcessImpl;
import com.cedar.cp.impl.model.recharge.RechargeGroupsProcessImpl;
import com.cedar.cp.impl.model.recharge.RechargesProcessImpl;
import com.cedar.cp.impl.model.udwp.WeightingDeploymentsProcessImpl;
import com.cedar.cp.impl.model.udwp.WeightingProfilesProcessImpl;
import com.cedar.cp.impl.model.virement.VirementCategorysProcessImpl;
import com.cedar.cp.impl.model.virement.VirementRequestsProcessImpl;
import com.cedar.cp.impl.passwordhistory.PasswordHistorysProcessImpl;
import com.cedar.cp.impl.performance.PerformanceProcessImpl;
import com.cedar.cp.impl.perftest.PerfTestsProcessImpl;
import com.cedar.cp.impl.perftestrun.PerfTestRunResultsProcessImpl;
import com.cedar.cp.impl.perftestrun.PerfTestRunsProcessImpl;
import com.cedar.cp.impl.recalculate.RecalculateBatchTaskProcessImpl;
import com.cedar.cp.impl.report.ReportsProcessImpl;
import com.cedar.cp.impl.report.definition.ReportDefinitionsProcessImpl;
import com.cedar.cp.impl.report.destination.external.ExternalDestinationsProcessImpl;
import com.cedar.cp.impl.report.destination.internal.InternalDestinationsProcessImpl;
import com.cedar.cp.impl.report.distribution.DistributionsProcessImpl;
import com.cedar.cp.impl.report.mappingtemplate.ReportMappingTemplatesProcessImpl;
import com.cedar.cp.impl.report.pack.ReportPacksProcessImpl;
import com.cedar.cp.impl.report.template.ReportTemplatesProcessImpl;
import com.cedar.cp.impl.report.type.ReportTypesProcessImpl;
import com.cedar.cp.impl.reset.ChallengeQuestionsProcessImpl;
import com.cedar.cp.impl.reset.UserResetLinksProcessImpl;
import com.cedar.cp.impl.role.RolesProcessImpl;
import com.cedar.cp.impl.sqlmon.SqlMonitorProcessImpl;
import com.cedar.cp.impl.systemproperty.SystemPropertysProcessImpl;
import com.cedar.cp.impl.task.TaskViewerProcessImpl;
import com.cedar.cp.impl.task.group.TaskGroupsProcessImpl;
import com.cedar.cp.impl.udeflookup.UdefLookupsProcessImpl;
import com.cedar.cp.impl.user.DataEntryProfilesProcessImpl;
import com.cedar.cp.impl.user.UsersProcessImpl;
import com.cedar.cp.impl.user.loggedinusers.LoggedInUsersProcessImpl;
import com.cedar.cp.impl.xmlform.XmlFormsProcessImpl;
import com.cedar.cp.impl.xmlform.convert.ExcelIOProcessImpl;
import com.cedar.cp.impl.xmlform.rebuild.FormRebuildsProcessImpl;
import com.cedar.cp.impl.xmlreport.XmlReportsProcessImpl;
import com.cedar.cp.impl.xmlreportfolder.XmlReportFoldersProcessImpl;
import com.cedar.cp.util.Log;
import com.cedar.cp.util.Timer;
import com.cedar.cp.util.common.JmsConnectionHelperImpl;
import com.cedar.cp.util.common.JmsConnectionImpl;
import com.coa.idm.UserRepository;
import com.coa.portal.client.PortalPrincipal;
import edu.umich.auth.cosign.CosignPrincipal;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import jcifs.smb.NtlmPasswordAuthentication;
import org.omg.CORBA.ORB;

public class CPConnectionImpl implements CPConnection {

	private ErrorPolicyHandler mPolicyHandler;
	private static Properties sServerProps = new Properties();
	private EntityEventWatcher mEntityEventWatcher;
	private boolean mRemoteConnection;
	private UserContextImpl mUserContext;
	private Map mActiveProcesses;
	private Set mNetworkListeners = new HashSet();
	private Hashtable mEnv = new Hashtable();
	private ServerContext mServerContext;
	private ClientCache mClientCache = new ClientCacheImpl();
	private ListHelper mListHelper = new ListHelperImpl(this);
	private ConnectionContext mConnectionContext;
	private JmsConnectionHelper mJmsConnectionHelper = new JmsConnectionHelperImpl(this);
	private ServerEntityHelper mServerEntityHelper;
	private EntityKeyFactory mEntityKeyFactory = new EntityKeyFactoryImpl();
	private Log mLog = new Log(this.getClass());
	private boolean mCluster;

	public CPConnectionImpl(ConnectionFactoryDetails cfd, String jndiURL) throws CPException, InvalidCredentialsException, UserLicenseException, AdminOnlyException, UserDisabledException, ValidationException {
		Timer t = mLog.isDebugEnabled() ? new Timer(mLog) : null;

		// setConnectionProperties(cfd, jndiURL);

		mRemoteConnection = cfd.isRemote();
		mConnectionContext = cfd.getConnectionContext();

		getServerContext();
		if (t != null) {
			t.logDebug("CPConnectionImpl getServletContext");
		}
		LogonServer remote = null;
		try {
			remote = new LogonServer(this);

			boolean trustAuthentication = (cfd.getConnectionContext() != null) && (cfd.getConnectionContext() != CPConnection.ConnectionContext.INTERACTIVE_GUI) && (cfd.getConnectionContext() != CPConnection.ConnectionContext.INTERACTIVE_WEB);

			if (t != null)
				t.logDebug("CPConnectionImpl new LogonServer");
			AuthenticationResult authResult = null;
			authResult = remote.authenticateUser(cfd.getAuthObject(), trustAuthentication);
			if (t != null) {
				t.logDebug("CPConnectionImpl authenticateUser");
			}
			mCluster = authResult.isCluster();
			if ((authResult.getResult() <= 4) || (authResult.getResult() == 8)) {
				Collection securityStrings = authResult.getUserSecurityStrings();

				int count = 0;
				int[] activities = new int[UserContext.MAX_PROCESS_ID];
				for (int i = 0; i < UserContext.MAX_PROCESS_ID; i++) {
					activities[i] = -1;
					Iterator iter = securityStrings.iterator();
					while (iter.hasNext()) {
						String sString = (String) iter.next();
						if (sString.startsWith(UserContext.sProcessSecurityPrefixes[i])) {
							activities[count] = i;
							count++;
							break;
						}
					}
				}
				mUserContext = new UserContextImpl(this, cfd.getUserId(), authResult.getFullUserName(), activities, authResult);
				mUserContext.setPassword(cfd.getPassword());
			} else {
				mUserContext = new UserContextImpl(this, cfd.getUserId(), cfd.getUserId(), null, authResult);
				mUserContext.setPassword(cfd.getPassword());
			}
			if (t != null) {
				t.logDebug("CPConnectionImpl UserContextImpl");
			}

			mActiveProcesses = new HashMap();

			setUserPreferences();

			if (t != null) {
				t.logDebug("CPConnectionImpl userPreferences");
			}
			if (t != null)
				t.logDebug("CPConnectionImpl");
		} finally {
			if (remote != null)
				remote.removeSession();
		}
	}

	public CPConnectionImpl(String appServer, String host, int iPort, String user, String password, boolean remoteConnection, boolean useRMIHttpTunnel, ConnectionContext connectionContext) throws CPException, InvalidCredentialsException, UserLicenseException, UserDisabledException, ValidationException {
		Timer t = this.mLog.isDebugEnabled() ? new Timer(this.mLog) : null;
		this.setConnectionProperties(appServer, host, iPort, user, password, useRMIHttpTunnel);
		this.mRemoteConnection = remoteConnection;
		this.mConnectionContext = connectionContext;
		this.getServerContext();
		if (t != null) {
			t.logDebug("CPConnectionImpl getServletContext");
		}

		LogonServer remote = null;

		try {
			remote = new LogonServer(this);
			boolean trustAuthentication = connectionContext != null && connectionContext != ConnectionContext.INTERACTIVE_GUI && connectionContext != ConnectionContext.INTERACTIVE_WEB;
			if (t != null) {
				t.logDebug("CPConnectionImpl new LogonServer");
			}

			AuthenticationResult authResult = null;
			authResult = remote.authenticateUser(user, password, trustAuthentication);
			if (t != null) {
				t.logDebug("CPConnectionImpl authenticateUser");
			}

			if (authResult.getResult() > 4 && authResult.getResult() != 8) {
				this.mUserContext = new UserContextImpl(this, user, user, (int[]) null, authResult);
				this.mUserContext.setPassword(password);
			} else {
				Set securityStrings = authResult.getUserSecurityStrings();
				int count = 0;
				int[] activities = new int[UserContext.MAX_PROCESS_ID];
				for (int i = 0; i < UserContext.MAX_PROCESS_ID; ++i) {
					activities[i] = -1;
					Iterator iter = securityStrings.iterator();

					while (iter.hasNext()) {
						String sString = (String) iter.next();
						if (sString.startsWith(UserContext.sProcessSecurityPrefixes[i])) {
							activities[count] = i;
							++count;
							break;
						}
					}
				}

				this.mUserContext = new UserContextImpl(this, user, authResult.getFullUserName(), activities, authResult);
				this.mUserContext.setPassword(password);
			}

			if (t != null) {
				t.logDebug("CPConnectionImpl UserContextImpl");
			}

			this.mActiveProcesses = new HashMap();
			this.setUserPreferences();
			if (t != null) {
				t.logDebug("CPConnectionImpl userPreferences");
			}

			if (t != null) {
				t.logDebug("CPConnectionImpl");
			}
		} finally {
//			if (remote != null) {
//				remote.removeSession();
//			}

		}

	}

	private void setUserPreferences() {
		UsersProcess process = this.getUsersProcess();
		EntityList preferences = process.getUserPreferencesForUser(this.mUserContext.getUserId());
		HashMap userPreferences = new HashMap();

		for (int i = 0; i < preferences.getNumRows(); ++i) {
			Object key = preferences.getValueAt(i, "PrefName");
			Object value = preferences.getValueAt(i, "PrefValue");
			userPreferences.put(key, value);
		}

		this.mUserContext.setUserPreferenceValues(userPreferences);
	}

	public CPConnectionImpl(String appServer, String host, int iPort, CosignPrincipal cosignPrincipal, boolean remoteConnection, boolean useRMIHttpTunnel) throws CPException, InvalidCredentialsException, UserLicenseException, UserDisabledException, ValidationException {
		Timer t = this.mLog.isDebugEnabled() ? new Timer(this.mLog) : null;
		String user = cosignPrincipal.getName();
		this.setConnectionProperties(appServer, host, iPort, user, (String) null, useRMIHttpTunnel);
		this.mRemoteConnection = remoteConnection;
		this.getServerContext();
		LogonServer remote = null;

		try {
			remote = new LogonServer(this);
			AuthenticationResult authResult = null;
			authResult = remote.authenticateUser(cosignPrincipal);
			if (authResult.getResult() > 4 && authResult.getResult() != 8) {
				this.mUserContext = new UserContextImpl(this, user, user, (int[]) null, authResult);
				this.mUserContext.setPassword(authResult.getPassword());
			} else {
				Set securityStrings = authResult.getUserSecurityStrings();
				int count = 0;
				int[] activities = new int[UserContext.MAX_PROCESS_ID];
				for (int i = 0; i < UserContext.MAX_PROCESS_ID; ++i) {
					activities[i] = -1;
					Iterator iter = securityStrings.iterator();

					while (iter.hasNext()) {
						String sString = (String) iter.next();
						if (sString.startsWith(UserContext.sProcessSecurityPrefixes[i])) {
							activities[count] = i;
							++count;
							break;
						}
					}
				}

				this.mUserContext = new UserContextImpl(this, user, authResult.getFullUserName(), activities, authResult);
				this.mUserContext.setPassword(authResult.getPassword());
			}

			this.mActiveProcesses = new HashMap();
			this.setUserPreferences();
			if (t != null) {
				t.logDebug("CPConnectionImpl");
			}
		} finally {
			if (remote != null) {
				remote.removeSession();
			}

		}

	}

	public CPConnectionImpl(String appServer, String host, int iPort, UserRepository ssoPrincipal, boolean remoteConnection, boolean useRMIHttpTunnel, ConnectionContext connectionContext) throws CPException, InvalidCredentialsException, UserLicenseException, UserDisabledException, ValidationException {
		Timer t = this.mLog.isDebugEnabled() ? new Timer(this.mLog) : null;
		String user = ssoPrincipal.getLogonIdentity();
		this.setConnectionProperties(appServer, host, iPort, user, (String) null, useRMIHttpTunnel);
		this.mRemoteConnection = remoteConnection;
		this.getServerContext();
		LogonServer remote = null;

		try {
			remote = new LogonServer(this);
			AuthenticationResult authResult = null;

			try {
				authResult = remote.authenticateUser(ssoPrincipal);
				if (!connectionContext.equals(ConnectionContext.INTERACTIVE_GUI)) {
					remote.syncCPUserDetails(ssoPrincipal);
				}
			} catch (InvalidCredentialsException var21) {
				authResult = remote.createUser(ssoPrincipal);
			}

			if (authResult.getResult() > 4 && authResult.getResult() != 8) {
				this.mUserContext = new UserContextImpl(this, authResult.getUserName(), authResult.getFullUserName(), (int[]) null, authResult);
				this.mUserContext.setPassword(authResult.getPassword());
			} else {
				Set securityStrings = authResult.getUserSecurityStrings();
				int count = 0;
				int[] activities = new int[UserContext.MAX_PROCESS_ID];
				for (int i = 0; i < UserContext.MAX_PROCESS_ID; ++i) {
					activities[i] = -1;
					Iterator iter = securityStrings.iterator();

					while (iter.hasNext()) {
						String sString = (String) iter.next();
						if (sString.startsWith(UserContext.sProcessSecurityPrefixes[i])) {
							activities[count] = i;
							++count;
							break;
						}
					}
				}

				this.mUserContext = new UserContextImpl(this, authResult.getUserName(), authResult.getFullUserName(), activities, authResult);
				this.mUserContext.setPassword(authResult.getPassword());
			}

			this.mActiveProcesses = new HashMap();
			this.setUserPreferences();
			if (t != null) {
				t.logDebug("CPConnectionImpl");
			}
		} finally {
			if (remote != null) {
				remote.removeSession();
			}

		}

	}

	public CPConnectionImpl(String appServer, String host, int iPort, PortalPrincipal portalPrincipal, boolean remoteConnection, boolean useRMIHttpTunnel) throws CPException, InvalidCredentialsException, UserLicenseException, UserDisabledException, ValidationException {
		Timer t = this.mLog.isDebugEnabled() ? new Timer(this.mLog) : null;
		String user = portalPrincipal.getName();
		this.setConnectionProperties(appServer, host, iPort, user, (String) null, useRMIHttpTunnel);
		this.mRemoteConnection = remoteConnection;
		this.getServerContext();
		LogonServer remote = null;

		try {
			remote = new LogonServer(this);
			AuthenticationResult authResult = null;
			authResult = remote.authenticateUser(portalPrincipal);
			if (authResult.getResult() > 4 && authResult.getResult() != 8) {
				this.mUserContext = new UserContextImpl(this, user, user, (int[]) null, authResult);
				this.mUserContext.setPassword(authResult.getPassword());
			} else {
				Set securityStrings = authResult.getUserSecurityStrings();
				int count = 0;
				int[] activities = new int[UserContext.MAX_PROCESS_ID];
				for (int i = 0; i < UserContext.MAX_PROCESS_ID; ++i) {
					activities[i] = -1;
					Iterator iter = securityStrings.iterator();

					while (iter.hasNext()) {
						String sString = (String) iter.next();
						if (sString.startsWith(UserContext.sProcessSecurityPrefixes[i])) {
							activities[count] = i;
							++count;
							break;
						}
					}
				}

				this.mUserContext = new UserContextImpl(this, user, authResult.getFullUserName(), activities, authResult);
				this.mUserContext.setPassword(authResult.getPassword());
			}

			this.mActiveProcesses = new HashMap();
			this.setUserPreferences();
			if (t != null) {
				t.logDebug("CPConnectionImpl");
			}
		} finally {
			if (remote != null) {
				remote.removeSession();
			}

		}

	}

	public CPConnectionImpl(String appServer, String host, int iPort, NtlmPasswordAuthentication ntlmPrincipal, boolean remoteConnection, boolean useRMIHttpTunnel) throws CPException, InvalidCredentialsException, UserLicenseException, UserDisabledException, ValidationException {
		Timer t = this.mLog.isDebugEnabled() ? new Timer(this.mLog) : null;
		String user = ntlmPrincipal.getUsername();
		this.setConnectionProperties(appServer, host, iPort, user, (String) null, useRMIHttpTunnel);
		this.mRemoteConnection = remoteConnection;
		this.getServerContext();
		LogonServer remote = null;

		try {
			remote = new LogonServer(this);
			AuthenticationResult authResult = null;
			authResult = remote.authenticateUser(ntlmPrincipal);
			if (authResult.getResult() > 4 && authResult.getResult() != 8) {
				this.mUserContext = new UserContextImpl(this, user, user, (int[]) null, authResult);
				this.mUserContext.setPassword(authResult.getPassword());
			} else {
				Set securityStrings = authResult.getUserSecurityStrings();
				int count = 0;
				int[] activities = new int[UserContext.MAX_PROCESS_ID];
				for (int i = 0; i < UserContext.MAX_PROCESS_ID; ++i) {
					activities[i] = -1;
					Iterator iter = securityStrings.iterator();

					while (iter.hasNext()) {
						String sString = (String) iter.next();
						if (sString.startsWith(UserContext.sProcessSecurityPrefixes[i])) {
							activities[count] = i;
							++count;
							break;
						}
					}
				}

				this.mUserContext = new UserContextImpl(this, user, authResult.getFullUserName(), activities, authResult);
				this.mUserContext.setPassword(authResult.getPassword());
			}

			this.mActiveProcesses = new HashMap();
			this.setUserPreferences();
			if (t != null) {
				t.logDebug("CPConnectionImpl");
			}
		} finally {
			if (remote != null) {
				remote.removeSession();
			}

		}

	}

	private String queryInitialContextFactor(String appServer) {
		Properties var2 = sServerProps;
		synchronized (sServerProps) {
			if (sServerProps.isEmpty()) {
				try {
					InputStream e = this.getClass().getResourceAsStream("initialContextFactory.properties");

					if (e == null) {
						throw new CPException("Unable to load initialContextFactory.properties");
					}

					sServerProps.load(e);
				} catch (IOException var5) {
					throw new CPException("Failed to load appServer.properties", var5);
				}
			}

			return sServerProps.getProperty(appServer);
		}
	}

	public void setConnectionProperties(String appServer, String host, int iPort, String user, String password, boolean useRmiHttpTunnel) throws CPException {
		Timer t = this.mLog.isDebugEnabled() ? new Timer(this.mLog) : null;
		String contextAPI = "";
		String authenticationType = "simple";
		String location = host + ":" + iPort;
		new Properties();
		contextAPI = this.queryInitialContextFactor(appServer);
		if (contextAPI == null) {
			throw new CPException("Unable to determine InitailContextFactory for appServer:" + appServer);
		} else {
			if (contextAPI.compareTo("J2EE") != 0) {
				this.mEnv.put("java.naming.factory.initial", contextAPI);

			}

			if (contextAPI.startsWith("org.jnp.interfaces")) {
				if (useRmiHttpTunnel) {
					this.mEnv.put("java.naming.factory.initial", "org.jboss.naming.HttpNamingContextFactory");
					location = "http://" + host + ":" + (iPort - 1) + "/invoker/JNDIFactory";
				}

				this.mEnv.put("java.naming.security.principal", user);
				if (password != null) {
					this.mEnv.put("java.naming.security.credentials", password);
				} else {
					this.mEnv.put("java.naming.security.credentials", "password");
				}

				this.mEnv.put("java.naming.provider.url", location);
			} else if (contextAPI.startsWith("com.evermind")) {
				if (useRmiHttpTunnel) {
					location = "http:ormi://" + location + "/cp";
				} else {
					location = "ormi://" + location + "/cp";
				}

				this.mEnv.put("java.naming.security.principal", "admin");
				this.mEnv.put("java.naming.security.credentials", "password");
				this.mEnv.put("java.naming.provider.url", location);
			} else if (contextAPI.startsWith("com.ibm.websphere")) {
				location = "iiop://" + host + ":" + iPort;

				try {
					Properties e = new Properties();
					Object args = null;
					e.put("org.omg.CORBA.ORBClass", "com.ibm.rmi.iiop.ORB");
					e.put("com.ibm.CORBA.BootstrapHost", host);
					e.put("com.ibm.CORBA.requestTimeout", "30");
					ORB.init((String[]) args, e);
					this.mEnv.put("java.naming.security.principal", user);
					this.mEnv.put("java.naming.security.credentials", password);
					this.mEnv.put("java.naming.provider.url", location);
				} catch (Exception var14) {
					this.mLog.error("EXCEPTION msg = " + var14.getMessage(), var14);
					throw new CPException(var14.getMessage());
				}
			}

			this.mEnv.put("java.naming.security.authentication", authenticationType);
			if (t != null) {
				t.logDebug("setConnectionProperties");
			}

		}
	}

	public boolean isRemoteConnection() {
		return this.mRemoteConnection;
	}

	public void addNetworkActivityListener(NetworkActivityListener listener) {
		this.mNetworkListeners.add(listener);
	}

	public void removeNetworkActivityListener(NetworkActivityListener listener) {
		this.mNetworkListeners.remove(listener);
	}

	public UserContext getUserContext() {
		return this.mUserContext;
	}

	public ServerContext getServerContext() {
		Timer t = this.mLog.isDebugEnabled() ? new Timer(this.mLog) : null;
		if (this.mServerContext != null) {
			return this.mServerContext;
		} else {
			try {
				final Properties env = new Properties();
//				env.put(Context.INITIAL_CONTEXT_FACTORY, org.jboss.naming.remote.client.InitialContextFactory.class.getName());
				
//				env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
//				env.put(Context.PROVIDER_URL, "ldap://localhost:8080");
//				env.put("java.naming.security.authentication", "simple");
//				env.put("java.naming.security.principal","uid=fc,ou=system");
//				env.put("java.naming.security.credentials","1234");
//				InitialContext e = new InitialContext(env);

//				InitialContext e = new InitialContext(this.mEnv);
				InitialContext e = new InitialContext();

				this.mServerContext = new ServerContextImpl(e);
				if (t != null) {
					t.logDebug("getServerContext");
				}

				return this.mServerContext;
			} catch (NamingException var3) {
				throw new RuntimeException("unable to get context", var3);
			}
		}
	}

	public ClientCache getClientCache() {
		return this.mClientCache;
	}

	public ListHelper getListHelper() {
		return this.mListHelper;
	}

	public JmsConnectionHelper getJmsConnectionHelper() {
		return this.mJmsConnectionHelper;
	}

	public ServerEntityHelper getServerEntityHelper() {
		return this.mServerEntityHelper;
	}

	public EntityKeyFactory getEntityKeyFactory() {
		return this.mEntityKeyFactory;
	}

	public JmsConnection getJmsConnection(int type, String name) throws CPException {
		return new JmsConnectionImpl((Context) this.getServerContext().getContext(), type, name);
	}

	public Collection getActiveProcesses() {
		return this.mActiveProcesses.values();
	}

	public void fireNetworkActivity(boolean state) {
		Iterator iter = this.mNetworkListeners.iterator();

		while (iter.hasNext()) {
			NetworkActivityListener listener = (NetworkActivityListener) iter.next();
			listener.networkActivity(state);
		}

	}

	public void startEntityEventWatcher() {
		try {
			this.mEntityEventWatcher = new EntityEventWatcher(this);
			this.mEntityEventWatcher.start();
		} catch (Exception var2) {
			var2.printStackTrace();
		}

	}

	public void removeProcess(BusinessProcess bp) {
		this.mActiveProcesses.remove(bp);
	}

	public void close() {
		if (this.mEntityEventWatcher != null) {
			this.mEntityEventWatcher.stop();
		}

	}

	public ConnectionContext getConnectionContext() {
		return this.mConnectionContext;
	}

	public void add2LogonHistory(int state, String username) {
		try {
			LogonServer e = new LogonServer(this);
			e.add2LogonHistory(state, username);
			e.removeSession();
		} catch (Exception var4) {
			this.mLog.debug("**** unable to change password ****");
			throw new RuntimeException("Unable to change password" + var4.getMessage());
		}
	}

	public BusinessProcess getBusinessProcess(int processId) {
		Integer key = new Integer(processId);
		BusinessProcess bp = (BusinessProcess) this.mActiveProcesses.get(key);
		if (bp == null) {
			bp = this.newBusinessProcess(processId);
			this.mActiveProcesses.put(key, bp);
		}

		return bp;
	}

	private BusinessProcess newBusinessProcess(int processId) {
		Object bp = null;

		try {
			switch (processId) {
			case 3:
				bp = new BudgetInstructionsProcessImpl(this);
				break;
			case 4:
			case 5:
			case 7:
			case 13:
			case 14:
			case 22:
			case 26:
			case 28:
			case 29:
			case 30:
			case 31:
			case 32:
			case 33:
			case 34:
			case 35:
			case 36:
				bp = new ImportTasksProcessImpl(this);
				break;
			case 37:
			case 38:
			case 39:
			case 57:
			case 59:
			case 68:
			default:
				throw new IllegalArgumentException("unexpected process number:" + processId);
			case 6:
				bp = new CellCalcsProcessImpl(this);
				break;
			case 8:
			case 40:
			case 41:
			case 42:
				bp = new HierarchysProcessImpl(this);
				break;
			case 9:
			case 10:
			case 11:
				bp = new DimensionsProcessImpl(this);
				break;
			case 12:
				bp = new FinanceCubesProcessImpl(this);
				break;
			case 15:
				bp = new BudgetLocationsProcessImpl(this);
				break;
			case 16:
				bp = new BudgetCyclesProcessImpl(this);
				break;
			case 17:
				bp = new BudgetUsersProcessImpl(this);
				break;
			case 18:
				bp = new BudgetUsersProcessImpl(this);
				break;
			case 19:
				bp = new DataTypesProcessImpl(this);
				break;
			case 20:
				bp = new ModelsProcessImpl(this);
				break;
			case 21:
				bp = new TaskViewerProcessImpl(this);
				break;
			case 23:
				bp = new TaskViewerProcessImpl(this);
				break;
			case 24:
				bp = new SqlMonitorProcessImpl(this);
				break;
			case 25:
				bp = new ResponsibilityAreasProcessImpl(this);
				break;
			case 27:
				bp = new UsersProcessImpl(this);
				break;
			case 43:
				bp = new SystemPropertysProcessImpl(this);
				break;
			case 44:
			case 45:
			case 46:
			case 47:
			case 48:
			case 49:
			case 99:
			case 98:
				bp = new XmlFormsProcessImpl(this);
				break;
			case 50:
				bp = new SecurityGroupsProcessImpl(this);
				break;
			case 51:
				bp = new SecurityAccessDefsProcessImpl(this);
				break;
			case 52:
				bp = new SecurityRangesProcessImpl(this);
				break;
			case 53:
				bp = new DataEntryProcessImpl(this);
				break;
			case 54:
				bp = new ChangeMgmtsProcessImpl(this);
				break;
			case 55:
				bp = new ImpExpHdrsProcessImpl(this);
				break;
			case 56:
				bp = new PerformanceProcessImpl(this);
				break;
			case 58:
				bp = new XmlReportsProcessImpl(this);
				break;
			case 60:
				bp = new ReportsProcessImpl(this);
				break;
			case 61:
				bp = new VirementCategorysProcessImpl(this);
				break;
			case 62:
				bp = new PerfTestsProcessImpl(this);
				break;
			case 63:
				bp = new PerfTestRunsProcessImpl(this);
				break;
			case 64:
				bp = new PerfTestRunResultsProcessImpl(this);
				break;
			case 65:
				bp = new BudgetLimitsProcessImpl(this);
				break;
			case 66:
				bp = new MessagesProcessImpl(this);
				break;
			case 67:
				bp = new XmlReportFoldersProcessImpl(this);
				break;
			case 69:
				bp = new RechargesProcessImpl(this);
				break;
			case 70:
				bp = new RechargeGroupsProcessImpl(this);
				break;
			case 71:
				bp = new RolesProcessImpl(this);
				break;
			case 72:
				bp = new DataEntryProfilesProcessImpl(this);
				break;
			case 73:
				bp = new VirementRequestsProcessImpl(this);
				break;
			case 74:
				bp = new ReportTypesProcessImpl(this);
				break;
			case 75:
				bp = new ReportDefinitionsProcessImpl(this);
				break;
			case 76:
				bp = new ReportTemplatesProcessImpl(this);
				break;
			case 77:
				bp = new ExternalDestinationsProcessImpl(this);
				break;
			case 78:
				bp = new InternalDestinationsProcessImpl(this);
				break;
			case 79:
				bp = new DistributionsProcessImpl(this);
				break;
			case 80:
				bp = new ReportPacksProcessImpl(this);
				break;
			case 81:
				bp = new WeightingProfilesProcessImpl(this);
				break;
			case 82:
				bp = new WeightingDeploymentsProcessImpl(this);
				break;
			case 83:
				bp = new TidyTasksProcessImpl(this);
				break;
			case 84:
				bp = new ReportMappingTemplatesProcessImpl(this);
				break;
			case 85:
				bp = new com.cedar.cp.impl.model.mapping.MappedModelsProcessImpl(this);
				break;
			case 86:
				bp = new ExternalSystemsProcessImpl(this);
				break;
			case 87:
				bp = new ExtendedAttachmentsProcessImpl(this);
				break;
			case 88:
				bp = new ExtSysE5DB2PushProcessImpl(this);
				break;
			case 89:
				bp = new UdefLookupsProcessImpl(this);
				break;
			case 90:
				bp = new CcDeploymentsProcessImpl(this);
				break;
			case 91:
				bp = new AmmModelsProcessImpl(this);
				break;
			case 92:
				bp = new AuthenticationPolicysProcessImpl(this);
				break;
			case 93:
				bp = new LogonHistorysProcessImpl(this);
				break;
			case 94:
				bp = new PasswordHistorysProcessImpl(this);
				break;
			case 95:
				bp = new TaskGroupsProcessImpl(this);
				break;
			case 96:
				bp = new FormRebuildsProcessImpl(this);
				break;
			case 97:
				bp = new CubeFormulasProcessImpl(this);
				break;
			case 100:
				bp = new GlobalMappedModels2ProcessImpl(this);
				break;
			case 101:
				bp = new UserModelSecurityProcessImpl(this);
				break;
			case 102:
				bp = new MasterQuestionsProcessImpl(this);
				break;
			case 103:
				bp = new ChallengeQuestionsProcessImpl(this);
				break;
			case 104:
				bp = new UserResetLinksProcessImpl(this);
				break;
			case 105:
				bp = new RecalculateBatchTaskProcessImpl(this);
				break;
			case 106:
				bp = new LoggedInUsersProcessImpl(this);
				break;
			case 107:
				bp = new NotesProcessImpl(this);
				break;
			case 108:
				bp = new DataEditorProcessImpl(this);
				break;
			case 109:
				bp = new ExcelIOProcessImpl(this);
				break;
			}

			return (BusinessProcess) bp;
		} catch (Exception var4) {
			throw new RuntimeException("can\'t get BusinessProcess", var4);
		}
	}

	public ModelsProcess getModelsProcess() {
		return (ModelsProcess) this.getBusinessProcess(20);
	}

	public FinanceCubesProcess getFinanceCubesProcess() {
		return (FinanceCubesProcess) this.getBusinessProcess(12);
	}

	public RechargesProcess getRechargesProcess() {
		return (RechargesProcess) this.getBusinessProcess(69);
	}

	public RechargeGroupsProcess getRechargeGroupsProcess() {
		return (RechargeGroupsProcess) this.getBusinessProcess(70);
	}

	public DataTypesProcess getDataTypesProcess() {
		return (DataTypesProcess) this.getBusinessProcess(19);
	}

	public SystemPropertysProcess getSystemPropertysProcess() {
		return (SystemPropertysProcess) this.getBusinessProcess(43);
	}

	public DimensionsProcess getDimensionsProcess() {
		return (DimensionsProcess) this.getBusinessProcess(10);
	}

	public UsersProcess getUsersProcess() {
		return (UsersProcess) this.getBusinessProcess(27);
	}

	public BudgetInstructionsProcess getBudgetInstructionsProcess() {
		return (BudgetInstructionsProcess) this.getBusinessProcess(3);
	}

	public TaskViewerProcess getTaskViewerProcess() {
		return (TaskViewerProcess) this.getBusinessProcess(21);
	}

	public SqlMonitorProcess getSqlMonitorProcess() {
		return (SqlMonitorProcess) this.getBusinessProcess(24);
	}

	public HierarchysProcess getHierarchysProcess() {
		return (HierarchysProcess) this.getBusinessProcess(41);
	}

	public BudgetCyclesProcess getBudgetCyclesProcess() {
		return (BudgetCyclesProcess) this.getBusinessProcess(16);
	}

	public BudgetUsersProcess getBudgetUsersProcess() {
		return (BudgetUsersProcess) this.getBusinessProcess(17);
	}

	public XmlFormsProcess getXmlFormsProcess() {
		return (XmlFormsProcess) this.getBusinessProcess(44);
	}

	public XmlReportsProcess getXmlReportsProcess() {
		return (XmlReportsProcess) this.getBusinessProcess(58);
	}

	public XmlReportFoldersProcess getXmlReportFoldersProcess() {
		return (XmlReportFoldersProcess) this.getBusinessProcess(67);
	}

	public DataEntryProfilesProcess getDataEntryProfilesProcess() {
		return (DataEntryProfilesProcess) this.getBusinessProcess(72);
	}

	public CurrencysProcess getCurrencysProcess() {
		return (CurrencysProcess) this.getBusinessProcess(18);
	}

	public SecurityGroupsProcess getSecurityGroupsProcess() {
		return (SecurityGroupsProcess) this.getBusinessProcess(50);
	}

	public SecurityAccessDefsProcess getSecurityAccessDefsProcess() {
		return (SecurityAccessDefsProcess) this.getBusinessProcess(51);
	}

	public SecurityRangesProcess getSecurityRangesProcess() {
		return (SecurityRangesProcess) this.getBusinessProcess(52);
	}

	public DataEntryProcess getDataEntryProcess() {
		return (DataEntryProcess) this.getBusinessProcess(53);
	}

	public PerformanceProcess getPerformanceProcess() {
		return (PerformanceProcess) this.getBusinessProcess(56);
	}

	public BusinessProcess getBudgetLocationsProcess() {
		return (BudgetLocationsProcess) this.getBusinessProcess(15);
	}

	public CellCalcsProcess getCellCalcsProcess() {
		return (CellCalcsProcess) this.getBusinessProcess(6);
	}

	public ChangeMgmtsProcess getChangeMgmtsProcess() {
		return (ChangeMgmtsProcess) this.getBusinessProcess(54);
	}

	public ImpExpHdrsProcess getImpExpHdrsProcess() {
		return (ImpExpHdrsProcess) this.getBusinessProcess(55);
	}

	public BudgetLimitsProcess getBudgetLimitsProcess() {
		return (BudgetLimitsProcess) this.getBusinessProcess(65);
	}

	public MessagesProcess getMessagesProcess() {
		return (MessagesProcess) this.getBusinessProcess(66);
	}

	public ReportsProcess getReportsProcess() {
		return (ReportsProcess) this.getBusinessProcess(60);
	}

	public VirementCategorysProcess getVirementCategorysProcess() {
		return (VirementCategorysProcess) this.getBusinessProcess(61);
	}

	public PerfTestsProcess getPerfTestsProcess() {
		return (PerfTestsProcess) this.getBusinessProcess(62);
	}

	public PerfTestRunsProcess getPerfTestRunsProcess() {
		return (PerfTestRunsProcess) this.getBusinessProcess(63);
	}

	public PerfTestRunResultsProcess getPerfTestRunResultsProcess() {
		return (PerfTestRunResultsProcess) this.getBusinessProcess(64);
	}

	public RolesProcess getRolesProcess() {
		return (RolesProcess) this.getBusinessProcess(71);
	}

	public VirementRequestsProcess getVirementRequestsProcess() {
		return (VirementRequestsProcess) this.getBusinessProcess(73);
	}

	public ResponsibilityAreasProcess getResponsibilityAreasProcess() {
		return (ResponsibilityAreasProcess) this.getBusinessProcess(25);
	}

	public ReportTypesProcess getReportTypesProcess() {
		return (ReportTypesProcess) this.getBusinessProcess(74);
	}

	public ReportDefinitionsProcess getReportDefinitionsProcess() {
		return (ReportDefinitionsProcess) this.getBusinessProcess(75);
	}

	public ReportTemplatesProcess getReportTemplatesProcess() {
		return (ReportTemplatesProcess) this.getBusinessProcess(76);
	}

	public ExternalDestinationsProcess getExternalDestinationsProcess() {
		return (ExternalDestinationsProcess) this.getBusinessProcess(77);
	}

	public InternalDestinationsProcess getInternalDestinationsProcess() {
		return (InternalDestinationsProcess) this.getBusinessProcess(78);
	}

	public DistributionsProcess getDistributionsProcess() {
		return (DistributionsProcess) this.getBusinessProcess(79);
	}

	public ReportPacksProcess getReportPacksProcess() {
		return (ReportPacksProcess) this.getBusinessProcess(80);
	}

	public WeightingProfilesProcess getWeightingProfilesProcess() {
		return (WeightingProfilesProcess) this.getBusinessProcess(81);
	}

	public WeightingDeploymentsProcess getWeightingDeploymentsProcess() {
		return (WeightingDeploymentsProcess) this.getBusinessProcess(82);
	}

	public TidyTasksProcess getTidyTasksProcess() {
		return (TidyTasksProcess) this.getBusinessProcess(83);
	}

	public ImportTasksProcess getImportTasksProcess() {
		return (ImportTasksProcess) this.getBusinessProcess(36);
	}

	public ReportMappingTemplatesProcess getReportMappingTemplatesProcess() {
		return (ReportMappingTemplatesProcess) this.getBusinessProcess(84);
	}

	public MappedModelsProcess getMappedModelsProcess() {
		return (com.cedar.cp.api.model.mapping.MappedModelsProcess) this.getBusinessProcess(85);
	}

	public UserModelSecurityProcess getUserModelSecurityProcess() {
		return (UserModelSecurityProcess) this.getBusinessProcess(101);
	}

	public GlobalMappedModels2Process getGlobalMappedModels2Process() {
		return (GlobalMappedModels2Process) this.getBusinessProcess(100);
	}

	public ExternalSystemsProcess getExternalSystemsProcess() {
		return (ExternalSystemsProcess) this.getBusinessProcess(86);
	}

	public ExtendedAttachmentsProcess getExtendedAttachmentsProcess() {
		return (ExtendedAttachmentsProcess) this.getBusinessProcess(87);
	}

	public ExtSysE5DB2PushProcess getExtSysE5DB2PushProcess() {
		return (ExtSysE5DB2PushProcess) this.getBusinessProcess(88);
	}

	public UdefLookupsProcess getUdefLookupsProcess() {
		return (UdefLookupsProcess) this.getBusinessProcess(89);
	}

	public CcDeploymentsProcess getCcDeploymentsProcess() {
		return (CcDeploymentsProcess) this.getBusinessProcess(90);
	}

	public AmmModelsProcess getAmmModelsProcess() {
		return (AmmModelsProcess) this.getBusinessProcess(91);
	}

	public AuthenticationPolicysProcess getAuthenticationPolicysProcess() {
		return (AuthenticationPolicysProcess) this.getBusinessProcess(92);
	}

	public LogonHistorysProcess getLogonHistorysProcess() {
		return (LogonHistorysProcess) this.getBusinessProcess(93);
	}

	public PasswordHistorysProcess getPasswordHistorysProcess() {
		return (PasswordHistorysProcess) this.getBusinessProcess(94);
	}

	public TaskGroupsProcess getTaskGroupsProcess() {
		return (TaskGroupsProcess) this.getBusinessProcess(95);
	}

	public FormRebuildsProcess getFormRebuildsProcess() {
		return (FormRebuildsProcess) this.getBusinessProcess(96);
	}

	public CubeFormulasProcess getCubeFormulasProcess() {
		return (CubeFormulasProcess) this.getBusinessProcess(97);
	}

	public AuthenticationResult changePassword(String logonId, String newPassword) {
		AuthenticationResult result = null;

		try {
			LogonServer e = new LogonServer(this);
			result = e.changePassword(logonId, newPassword);
			e.removeSession();
			return result;
		} catch (Exception var5) {
			this.mLog.debug("**** unable to change password ****");
			throw new RuntimeException("Unable to change password" + var5.getMessage());
		}
	}

	public void disableUser(String logonId) {
		try {
			LogonServer e = new LogonServer(this);
			e.disableUser(logonId);
			e.removeSession();
		} catch (Exception var3) {
			this.mLog.debug("**** unable to disable user ****");
			throw new RuntimeException("Unable to disable user." + var3.getMessage());
		}
	}

	public void setErrorPolicyHandler(ErrorPolicyHandler handler) {
		this.mPolicyHandler = handler;
	}

	public ErrorPolicyHandler getErrorPolicyHandler() {
		return this.mPolicyHandler;
	}

	public ChallengeQuestionsProcess getChallengeQuestionsProcess() {
		return (ChallengeQuestionsProcess) getBusinessProcess(103);
	}

	public MasterQuestionsProcess getMasterQuestionsProcess() {
		return (MasterQuestionsProcess) getBusinessProcess(102);
	}

	public UserResetLinksProcess getUserResetLinksProcess() {
		return (UserResetLinksProcess) getBusinessProcess(104);
	}

	public RecalculateBatchTaskProcess getRecalculateBatchTaskProcess() {
		return (RecalculateBatchTaskProcess) getBusinessProcess(105);
	}
	
	public LoggedInUsersProcess getLoggedInUsersProcess() {
		return (LoggedInUsersProcess) getBusinessProcess(106);
	}

	public NotesProcess getNotesProcess() {
		return (NotesProcess) getBusinessProcess(107);
	}
	
	public DataEditorProcess getDataEditorProcess() {
		return (DataEditorProcess) getBusinessProcess(108);
	}
	
	public ExcelIOProcess getExcelIOProcess() {
		return (ExcelIOProcess) getBusinessProcess(109);
	}
	
	public boolean isCluster() {
		return mCluster;
	}

}
