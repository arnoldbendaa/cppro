package com.cedar.cp.api.base;

import java.util.Collection;

import com.cedar.cp.api.admin.tidytask.TidyTasksProcess;
import com.cedar.cp.api.authenticationpolicy.AuthenticationPolicysProcess;
import com.cedar.cp.api.budgetinstruction.BudgetInstructionsProcess;
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
import com.cedar.cp.api.model.globalmapping2.GlobalMappedModels2Process;
import com.cedar.cp.api.model.mapping.MappedModelsProcess;
import com.cedar.cp.api.model.ra.ResponsibilityAreasProcess;
import com.cedar.cp.api.model.recharge.RechargeGroupsProcess;
import com.cedar.cp.api.model.recharge.RechargesProcess;
import com.cedar.cp.api.model.udwp.WeightingDeploymentsProcess;
import com.cedar.cp.api.model.udwp.WeightingProfilesProcess;
import com.cedar.cp.api.model.virement.VirementCategorysProcess;
import com.cedar.cp.api.model.virement.VirementRequestsProcess;
import com.cedar.cp.api.notes.NotesProcess;
import com.cedar.cp.api.passwordhistory.PasswordHistorysProcess;
import com.cedar.cp.api.performance.PerformanceProcess;
import com.cedar.cp.api.perftest.PerfTestsProcess;
import com.cedar.cp.api.perftestrun.PerfTestRunResultsProcess;
import com.cedar.cp.api.perftestrun.PerfTestRunsProcess;
import com.cedar.cp.api.recalculate.RecalculateBatchTaskProcess;
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
import com.cedar.cp.api.xmlform.XmlFormsProcess;
import com.cedar.cp.api.xmlform.convert.ExcelIOProcess;
import com.cedar.cp.api.xmlform.rebuild.FormRebuildsProcess;
import com.cedar.cp.api.xmlreport.XmlReportsProcess;
import com.cedar.cp.api.xmlreportfolder.XmlReportFoldersProcess;

public interface CPConnection {

	public static enum ConnectionContext {
		INTERACTIVE_GUI,
		INTERACTIVE_WEB,
		SERVER_TASK,
		SERVER_SESSION,
		CHANGE_MANAGEMENT;
	}

	String sKEY = "FCConnectionKey";
	String INVALID_USER_OR_PASSWORD_MESSAGE = "Invalid username or password";

	EntityKeyFactory getEntityKeyFactory();

	boolean isRemoteConnection();

	UserContext getUserContext();

	ServerContext getServerContext();

	ClientCache getClientCache();

	ListHelper getListHelper();

	JmsConnectionHelper getJmsConnectionHelper() throws CPException;

	ServerEntityHelper getServerEntityHelper();

	JmsConnection getJmsConnection(int var1, String var2) throws CPException;

	void addNetworkActivityListener(NetworkActivityListener var1);

	void removeNetworkActivityListener(NetworkActivityListener var1);

	void fireNetworkActivity(boolean var1);

	Collection getActiveProcesses();

	void startEntityEventWatcher();

	void close();

	ModelsProcess getModelsProcess();

	FinanceCubesProcess getFinanceCubesProcess();

	BudgetCyclesProcess getBudgetCyclesProcess();

	ResponsibilityAreasProcess getResponsibilityAreasProcess();

	BudgetUsersProcess getBudgetUsersProcess();

	DataTypesProcess getDataTypesProcess();

	CurrencysProcess getCurrencysProcess();

	DimensionsProcess getDimensionsProcess();

	HierarchysProcess getHierarchysProcess();

	UsersProcess getUsersProcess();

	RolesProcess getRolesProcess();

	BudgetInstructionsProcess getBudgetInstructionsProcess();

	SystemPropertysProcess getSystemPropertysProcess();

	XmlFormsProcess getXmlFormsProcess();

	XmlReportsProcess getXmlReportsProcess();

	XmlReportFoldersProcess getXmlReportFoldersProcess();

	DataEntryProfilesProcess getDataEntryProfilesProcess();

	UdefLookupsProcess getUdefLookupsProcess();

	SecurityRangesProcess getSecurityRangesProcess();

	SecurityAccessDefsProcess getSecurityAccessDefsProcess();

	SecurityGroupsProcess getSecurityGroupsProcess();

	CcDeploymentsProcess getCcDeploymentsProcess();

	CellCalcsProcess getCellCalcsProcess();

	ChangeMgmtsProcess getChangeMgmtsProcess();

	ImpExpHdrsProcess getImpExpHdrsProcess();

	ReportsProcess getReportsProcess();

	VirementCategorysProcess getVirementCategorysProcess();

	BudgetLimitsProcess getBudgetLimitsProcess();

	PerfTestsProcess getPerfTestsProcess();

	PerfTestRunsProcess getPerfTestRunsProcess();

	PerfTestRunResultsProcess getPerfTestRunResultsProcess();

	MessagesProcess getMessagesProcess();

	RechargesProcess getRechargesProcess();

	RechargeGroupsProcess getRechargeGroupsProcess();

	VirementRequestsProcess getVirementRequestsProcess();

	ReportTypesProcess getReportTypesProcess();

	ReportDefinitionsProcess getReportDefinitionsProcess();

	ReportTemplatesProcess getReportTemplatesProcess();

	ReportMappingTemplatesProcess getReportMappingTemplatesProcess();

	ExternalDestinationsProcess getExternalDestinationsProcess();

	InternalDestinationsProcess getInternalDestinationsProcess();

	DistributionsProcess getDistributionsProcess();

	ReportPacksProcess getReportPacksProcess();

	WeightingProfilesProcess getWeightingProfilesProcess();

	WeightingDeploymentsProcess getWeightingDeploymentsProcess();

	TidyTasksProcess getTidyTasksProcess();

	ImportTasksProcess getImportTasksProcess();

	MappedModelsProcess getMappedModelsProcess();

	GlobalMappedModels2Process getGlobalMappedModels2Process();

	ExtendedAttachmentsProcess getExtendedAttachmentsProcess();

	ExternalSystemsProcess getExternalSystemsProcess();

	AmmModelsProcess getAmmModelsProcess();

	TaskGroupsProcess getTaskGroupsProcess();

	AuthenticationPolicysProcess getAuthenticationPolicysProcess();

	RecalculateBatchTaskProcess getRecalculateBatchTaskProcess();

	LogonHistorysProcess getLogonHistorysProcess();

	PasswordHistorysProcess getPasswordHistorysProcess();

	FormRebuildsProcess getFormRebuildsProcess();

	CubeFormulasProcess getCubeFormulasProcess();

	TaskViewerProcess getTaskViewerProcess();

	SqlMonitorProcess getSqlMonitorProcess();

	AuthenticationResult changePassword(String var1, String var2);

	void add2LogonHistory(int var1, String var2);

	void disableUser(String var1);

	void setErrorPolicyHandler(ErrorPolicyHandler var1);

	ErrorPolicyHandler getErrorPolicyHandler();

	DataEntryProcess getDataEntryProcess();

	PerformanceProcess getPerformanceProcess();

	BusinessProcess getBudgetLocationsProcess();

	ExtSysE5DB2PushProcess getExtSysE5DB2PushProcess();

	ConnectionContext getConnectionContext();

	ChallengeQuestionsProcess getChallengeQuestionsProcess();

	MasterQuestionsProcess getMasterQuestionsProcess();

	UserResetLinksProcess getUserResetLinksProcess();

	boolean isCluster();

	BusinessProcess getUserModelSecurityProcess();
	
	LoggedInUsersProcess getLoggedInUsersProcess();
	
	NotesProcess getNotesProcess();

	BusinessProcess getDataEditorProcess();
	
	ExcelIOProcess getExcelIOProcess();
}
