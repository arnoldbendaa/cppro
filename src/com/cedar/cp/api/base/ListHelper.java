package com.cedar.cp.api.base;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import com.cedar.cp.api.admin.tidytask.TidyTaskRef;
import com.cedar.cp.api.authenticationpolicy.AuthenticationPolicyRef;
import com.cedar.cp.api.base.EntityList;
import com.cedar.cp.api.base.EntityRef;
import com.cedar.cp.api.budgetinstruction.BudgetInstructionRef;
import com.cedar.cp.api.budgetlocation.UserModelElementAssignment;
import com.cedar.cp.api.cm.ChangeMgmtRef;
import com.cedar.cp.api.cubeformula.CubeFormulaRef;
import com.cedar.cp.api.currency.CurrencyRef;
import com.cedar.cp.api.datatype.DataTypeRef;
import com.cedar.cp.api.dimension.DimensionElementRef;
import com.cedar.cp.api.dimension.DimensionRef;
import com.cedar.cp.api.dimension.HierarchyRef;
import com.cedar.cp.api.dimension.SecurityRangeRef;
import com.cedar.cp.api.dimension.StructureElementRef;
import com.cedar.cp.api.extendedattachment.ExtendedAttachmentRef;
import com.cedar.cp.api.extsys.ExternalSystemRef;
import com.cedar.cp.api.impexp.ImpExpHdrRef;
import com.cedar.cp.api.logonhistory.LogonHistoryRef;
import com.cedar.cp.api.masterquestion.MasterQuestionRef;
import com.cedar.cp.api.message.MessageRef;
import com.cedar.cp.api.model.BudgetCycleRef;
import com.cedar.cp.api.model.BudgetUserRef;
import com.cedar.cp.api.model.CellCalcRef;
import com.cedar.cp.api.model.FinanceCubeRef;
import com.cedar.cp.api.model.ModelRef;
import com.cedar.cp.api.model.SecurityAccessDefRef;
import com.cedar.cp.api.model.SecurityGroupRef;
import com.cedar.cp.api.model.amm.AmmModelRef;
import com.cedar.cp.api.model.budgetlimit.BudgetLimitRef;
import com.cedar.cp.api.model.cc.CcDeploymentRef;
import com.cedar.cp.api.model.ra.ResponsibilityAreaRef;
import com.cedar.cp.api.model.recharge.RechargeGroupRef;
import com.cedar.cp.api.model.recharge.RechargeRef;
import com.cedar.cp.api.model.udwp.WeightingDeploymentRef;
import com.cedar.cp.api.model.udwp.WeightingProfileRef;
import com.cedar.cp.api.model.virement.VirementCategoryRef;
import com.cedar.cp.api.model.virement.VirementRequestRef;
import com.cedar.cp.api.passwordhistory.PasswordHistoryRef;
import com.cedar.cp.api.perftest.PerfTestRef;
import com.cedar.cp.api.perftestrun.PerfTestRunRef;
import com.cedar.cp.api.perftestrun.PerfTestRunResultRef;
import com.cedar.cp.api.report.ReportRef;
import com.cedar.cp.api.report.definition.ReportDefinitionRef;
import com.cedar.cp.api.report.destination.external.ExternalDestinationRef;
import com.cedar.cp.api.report.destination.internal.InternalDestinationRef;
import com.cedar.cp.api.report.distribution.DistributionRef;
import com.cedar.cp.api.report.mappingtemplate.ReportMappingTemplateRef;
import com.cedar.cp.api.report.pack.ReportPackRef;
import com.cedar.cp.api.report.template.ReportTemplateRef;
import com.cedar.cp.api.report.type.ReportTypeRef;
import com.cedar.cp.api.reset.ChallengeQuestionRef;
import com.cedar.cp.api.reset.UserResetLinkRef;
import com.cedar.cp.api.role.RoleRef;
import com.cedar.cp.api.systemproperty.SystemPropertyRef;
import com.cedar.cp.api.task.group.TaskGroupRef;
import com.cedar.cp.api.udeflookup.UdefLookupRef;
import com.cedar.cp.api.user.DataEntryProfileRef;
import com.cedar.cp.api.user.UserRef;
import com.cedar.cp.api.xmlform.XmlFormRef;
import com.cedar.cp.api.xmlform.rebuild.FormRebuildRef;
import com.cedar.cp.api.xmlreport.XmlReportRef;
import com.cedar.cp.api.xmlreportfolder.XmlReportFolderRef;

public interface ListHelper {

	EntityList getAllModels();

	EntityList getAllModelsWeb();

	EntityList getAllModelsWebForUser(int var1);
	
	EntityList getAllModelsForLoggedUser();

    EntityList getAllModelsForGlobalMappedModel(int modelId);
    
    Map<String, String> getPropertiesForModelVisId(String modelVisId);
    
    Map<String, String> getPropertiesForModelId(int modelId);

	EntityList getAllModelsWithActiveCycleForUser(int var1);

	EntityList getAllBudgetHierarchies();

	EntityList getModelForDimension(int var1);

	EntityList getModelDimensions(int var1);

	EntityList getModelDimensionseExcludeCall(int var1);

	EntityList getModelDetailsWeb(int var1);

	EntityList getAllRootsForModel(int var1);

	EntityList getBudgetHierarchyRootNodeForModel(int var1);

	EntityList getBudgetCyclesToFixState(int var1);

	EntityList getMaxDepthForBudgetHierarchy(int var1);

	EntityList getCalendarSpecForModel(int var1);

	EntityList getHierarchiesForModel(int var1);

	EntityList getAllUsersForASecurityGroup(int var1);

	EntityList getAllModelBusinessDimensions();

	EntityList getAllModelBusAndAccDimensions();

	EntityList getBudgetDimensionIdForModel(int var1, int var2);

	EntityList getDimensionIdForModelDimType(int var1, int var2);

	EntityList getAllFinanceCubes();
	
	EntityList getAllFinanceCubesForLoggedUser();
	
	EntityList getAllCubeFormulasForLoggedUser();

	EntityList getAllSimpleFinanceCubes();

	EntityList getAllDataTypesAttachedToFinanceCubeForModel(int var1);

	EntityList getFinanceCubesForModel(int var1);

	EntityList getFinanceCubeDimensionsAndHierachies(int var1);

	EntityList getFinanceCubeAllDimensionsAndHierachies(int var1);

	EntityList getAllFinanceCubesWeb();

	EntityList getAllFinanceCubesWebForModel(int var1);

	EntityList getAllFinanceCubesWebForUser(int var1);

	EntityList getFinanceCubeDetails(int var1);

	EntityList getFinanceCubesUsingDataType(short var1);

	EntityList getAllFinanceCubeDataTypes();

	EntityList getImportableFinanceCubeDataTypes();

	EntityList getAllAttachedDataTypesForFinanceCube(int var1);

	EntityList getAllDataTypesForFinanceCube(int var1);

	EntityList getAllFinanceCubesForDataType(short var1);

	EntityList getAllBudgetCycles();

	EntityList getAllBudgetCyclesWeb();
	
	EntityList getAllBudgetCyclesForLoggedUser();

	EntityList getAllBudgetCyclesWebDetailed();

	EntityList getBudgetCyclesForModel(int var1);

	EntityList getBudgetCyclesForModelWithState(int var1, int var2);

	EntityList getBudgetCycleIntegrity();

	EntityList getBudgetCycleDetailedForId(int var1);

	EntityList getBudgetCycleXmlFormsForId(int var1, int userId);

	EntityList getBudgetTransferBudgetCycles();

	EntityList getCheckIfHasState(int var1, int var2);

	EntityList getCycleStateDetails(int var1);

	EntityList getAllResponsibilityAreas();

	EntityList getAllBudgetUsers();

	EntityList getCheckUserAccessToModel(int var1, int var2);

	EntityList getCheckUserAccess(int var1, int var2);

	EntityList getCheckUser(int var1);

	EntityList getBudgetUsersForNode(int var1, int var2);

	EntityList getNodesForUserAndCycle(int var1, int var2);

	EntityList getNodesForUserAndModel(int var1, int var2);

	EntityList getUsersForModelAndElement(int var1, int var2);

	EntityList getAllDataTypes();

	EntityList getAllDataTypesWeb();

	EntityList getAllDataTypeForFinanceCube(int var1);

	EntityList getAllDataTypesForModel(int var1);

	EntityList getDataTypesByType(int var1);

	EntityList getDataTypesByTypeWriteable(int var1);

	EntityList getDataTypeDependencies(short var1);

	EntityList getDataTypesForImpExp();

	EntityList getDataTypeDetailsForVisID(String var1);

	EntityList getAllCurrencys();

	EntityList getAllStructureElements(int var1);

	EntityList getAllLeafStructureElements(int var1);

	EntityList getLeafPlannableBudgetLocationsForModel(int var1);

	EntityList getStructureElementParents(int var1, int var2);

	EntityList getStructureElementParentsReversed(int var1, int var2);

	EntityList getStructureElementParentsFromVisId(String var1, int var2);

	EntityList getImmediateChildren(int var1, int var2);

	EntityList getMassStateImmediateChildren(int var1, int var2);

	EntityList getStructureElementValues(int var1, int var2);

	EntityList getCheckLeaf(int var1);

	EntityList getStructureElement(int var1);

	EntityList getStructureElementForIds(int var1, int var2);

	EntityList getStructureElementByVisId(String var1, int var2);

	EntityList getRespAreaImmediateChildren(int var1, int var2);

	EntityList getAllDisabledLeaf(int var1);

	EntityList getAllNotPlannable(int var1);

	EntityList getAllDisabledLeafandNotPlannable(int var1);

	EntityList getLeavesForParent(int var1, int var2, int var3, int var4, int var5);

	EntityList getChildrenForParent(int var1, int var2, int var3, int var4, int var5);

	EntityList getReportLeavesForParent(int var1, int var2, int var3, int var4, int var5);

	EntityList getReportChildrenForParentToRelativeDepth(int var1, int var2, int var3, int var4, int var5, int var6, int var7, int var8);

	EntityList getBudgetHierarchyElement(int var1);

	EntityList getBudgetLocationElementForModel(int var1, int var2);

	EntityList getAllDimensions();
	
	EntityList getDimensionsForLoggedUser();

	EntityList getAvailableDimensions();

	EntityList getImportableDimensions();

	EntityList getAllDimensionsForModel(int var1);

	EntityList getAllDimensionElements();

	EntityList getAllDimensionElementsForDimension(int var1);

	EntityList getAllHierarchys();
	
	EntityList getHierarchiesForLoggedUser();

	EntityList getSelectedHierarchys();

	EntityList getImportableHierarchies(int var1);

	EntityList getHierarcyDetailsFromDimId(int var1);

	EntityList getHierarcyDetailsIncRootNodeFromDimId(int var1);

	EntityList getCalendarForModel(int var1);

	EntityList getCalendarForModelVisId(String var1);

	EntityList getCalendarForFinanceCube(int var1);

	EntityList getHierarachyElement(int var1);

	EntityList getAugHierarachyElement(int var1);

	EntityList getAllFeedsForDimensionElement(int var1);

	EntityList getAllUsers();
	
	EntityList getAllRevisions();
	
	EntityList getDashboardForms(Integer userId, boolean isAdmin);

	EntityList getSecurityStringsForUser(int var1);

	EntityList getAllUsersExport();

	EntityList getAllUserAttributes();

	EntityList getAllNonDisabledUsers();

	EntityList getUserMessageAttributes();

	EntityList getUserMessageAttributesForId(int var1);

	EntityList getUserMessageAttributesForName(String var1);

	EntityList getFinanceSystemUserName(int var1);

	EntityList getUsersWithSecurityString(String var1);

	EntityList getAllRolesForUsers();

	EntityList getAllRoles();
	
	EntityList getAllHiddenRoles();

	EntityList getAllRolesForUser(int var1);

	EntityList getAllHiddenRolesForUser(int var1);

	EntityList getAllSecurityRoles();

	EntityList getAllSecurityRolesForRole(int var1);

	EntityList getUserPreferencesForUser(int var1);

	EntityList getAllBudgetInstructions();

	EntityList getAllBudgetInstructionsWeb();

	EntityList getAllBudgetInstructionsForModel(int var1);

	EntityList getAllBudgetInstructionsForCycle(int var1);

	EntityList getAllBudgetInstructionsForLocation(int var1);

	EntityList getAllBudgetInstructionAssignments();

	EntityList getAllWebTasks();

	EntityList getAllWebTasksForUser(String var1);

	EntityList getWebTasksDetails(int var1);

	EntityList getAllSystemPropertys();

	EntityList getAllSystemPropertysUncached();

	EntityList getAllMailProps();

	EntityList getSystemProperty(String var1);

	EntityList getWebSystemProperty(String var1);

	EntityList getAllXmlForms();
	
	EntityList getAllXmlFormsForLoggedUser();

	EntityList getAllFinXmlForms();

	EntityList getAllFFXmlForms();
	
	EntityList getAllFFXmlFormsForLoggedUser();
	
	EntityList getAllXcellXmlForms();
	
	EntityList getXcellXmlFormsForUser(int userId);
	
	EntityList getAllXcellXmlFormsForLoggedUser();

	EntityList getAllMassVirementXmlForms();

	EntityList getAllFinanceXmlForms();

	EntityList getAllFinanceAndFlatForms();

	EntityList getAllFinanceXmlFormsForModel(int var1);

	EntityList getAllFinanceAndFlatFormsForModel(int var1);

	EntityList getAllFinanceXmlFormsAndDataTypesForModel(int var1);

	EntityList getAllXmlFormsForModel(int modelId);

	EntityList getAllFixedXMLForms();

	EntityList getAllDynamicXMLForms();

	EntityList getAllFlatXMLForms();

	EntityList getXMLFormDefinition(int var1);

	EntityList getXMLFormCellPickerInfo(int var1);

	EntityList getAllXMLFormUserLink();

	EntityList getCheckXMLFormUserLink(int var1);

	EntityList getAllFormNotesForBudgetLocation(int var1);

	EntityList getAllFormNotesForFormAndBudgetLocation(int var1, int var2);

	EntityList getAllXmlReports();

	EntityList getAllPublicXmlReports();

	EntityList getAllXmlReportsForUser(int var1);

	EntityList getXmlReportsForFolder(int var1);

	EntityList getSingleXmlReport(int var1, String var2);

	EntityList getAllXmlReportFolders();

	EntityList getDecendentFolders(int var1);

	EntityList getReportFolderWithId(int var1);

	EntityList getAllDataEntryProfiles();

	EntityList getAllDataEntryProfilesForUser(int var1, int var2, int budgetCycleId);

	EntityList getAllUsersForDataEntryProfilesForModel(int var1);

	EntityList getAllDataEntryProfilesForForm(int var1);

	EntityList getDefaultDataEntryProfile(int var1, int var2, int var3, int var4);

	EntityList getAllDataEntryProfileHistorys();

	EntityList getAllUdefLookups();

	EntityList getAllSecurityRanges();

	EntityList getAllSecurityRangesForModel(int var1);

	EntityList getAllSecurityAccessDefs();

	EntityList getAllSecurityAccessDefsForModel(int var1);

	EntityList getAllAccessDefsUsingRange(int var1);

	EntityList getAllSecurityGroups();

	EntityList getAllSecurityGroupsUsingAccessDef(int var1);

	EntityList getAllSecurityGroupsForUser(int var1);

	EntityList getAllCcDeployments();

	EntityList getCcDeploymentsForLookupTable(String var1);

	EntityList getCcDeploymentsForXmlForm(int var1);

	EntityList getCcDeploymentsForModel(int var1);

	EntityList getCcDeploymentCellPickerInfo(int var1);

	EntityList getCcDeploymentXMLFormType(int var1);

	EntityList getAllCellCalcs();

	EntityList getCellCalcIntegrity();

	EntityList getAllCellCalcAssocs();

	EntityList getAllChangeMgmts();

	EntityList getAllChangeMgmtsForModel(int var1);

	EntityList getAllChangeMgmtsForModelWithXML(int var1);

	EntityList getAllImpExpHdrs();

	EntityList getAllReports();

	EntityList getAllReportsForUser(int var1);

	EntityList getAllReportsForAdmin();

	EntityList getWebReportDetails(int var1);

	EntityList getAllVirementCategorys();

	EntityList getLocationsForCategory(int var1);

	EntityList getAccountsForCategory(int var1);

	EntityList getAllBudgetLimits();

	EntityList getAllPerfTests();

	EntityList getAllPerfTestRuns();

	EntityList getAllPerfTestRunResults();

	EntityList getAllMessages();

	EntityList getInBoxForUser(String var1);

	EntityList getUnreadInBoxForUser(String var1);

	EntityList getSentItemsForUser(String var1);

	EntityList getMessageForId(long var1, String var3);

	EntityList getMessageForIdSentItem(long var1, String var3);

	EntityList getMessageCount(long var1);

	EntityList getAttatchmentForMessage(long var1);

	EntityList getMessageFromUser(long var1);

	EntityList getMessageToUser(long var1);

	EntityList getAllMessagesToUser(long var1);

	EntityList getAllRecharges();

	EntityList getAllRechargesWithModel(int var1);

	EntityList getSingleRecharge(int var1);

	EntityList getAllRechargeGroups();

	EntityList getActivitiesForCycleandElement(int var1, Integer var2, int var3);

	EntityList getActivityDetails(int var1);

	EntityList getActivityFullDetails(int var1);

	EntityList getAllVirementRequests();

	EntityList getAllVirementRequestGroups();

	EntityList getAllReportTypes();

	EntityList getAllReportTypeParams();

	EntityList getAllReportTypeParamsforType(int var1);

	EntityList getAllReportDefinitions();

	EntityList getAllReportDefinitionsForLoggedUser();

	EntityList getAllPublicReportByType(int var1);

	EntityList getReportDefinitionForVisId(String var1);

	EntityList getAllReportDefFormcByReportTemplateId(int var1);

	EntityList getAllReportDefFormcByModelId(int var1);

	EntityList getCheckFormIsUsed(int var1);

	EntityList getAllReportDefMappedExcelcByReportTemplateId(int var1);

	EntityList getAllReportDefMappedExcelcByModelId(int var1);

	EntityList getAllReportDefCalcByCCDeploymentId(int var1);

	EntityList getAllReportDefCalcByReportTemplateId(int var1);

	EntityList getAllReportDefCalcByModelId(int var1);

	EntityList getAllReportDefSummaryCalcByCCDeploymentId(int var1);

	EntityList getAllReportDefSummaryCalcByReportTemplateId(int var1);

	EntityList getAllReportDefSummaryCalcByModelId(int var1);

	EntityList getAllReportTemplates();

	EntityList getAllReportMappingTemplates();

	EntityList getAllExternalDestinations();

	EntityList getAllExternalDestinationDetails();

	EntityList getAllUsersForExternalDestinationId(int var1);

	EntityList getAllInternalDestinations();

	EntityList getAllInternalDestinationDetails();

	EntityList getAllUsersForInternalDestinationId(int var1);

	EntityList getAllInternalDestinationUsers();

	EntityList getCheckInternalDestinationUsers(int var1);

	EntityList getAllDistributions();

	EntityList getDistributionForVisId(String var1);

	EntityList getDistributionDetailsForVisId(String var1);

	EntityList getCheckInternalDestination(int var1);

	EntityList getCheckExternalDestination(int var1);

	EntityList getAllReportPacks();

	EntityList getReportDefDistList(String var1);

	EntityList getCheckReportDef(int var1);

	EntityList getCheckReportDistribution(int var1);

	EntityList getAllWeightingProfiles();
	
	EntityList getAllWeightingProfilesForLoggedUser();

	EntityList getAllWeightingDeployments();

	EntityList getAllWeightingDeploymentsForLoggedUser();

	EntityList getAllTidyTasks();

	EntityList getAllImportTasks();

	EntityList getAllRecalculateBatchTasks();

	EntityList getOrderedChildren(int var1);

	EntityList getAllMappedModels();
	
	EntityList getAllMappedModelsForLoggedUser();

	EntityList getAllGlobalMappedModels2();
	
	EntityList getAllGlobalMappedModelsForLoggedUser();

	EntityList getMappedFinanceCubes(int var1);

	EntityList getAllExtendedAttachments();

	EntityList getExtendedAttachmentsForId(int var1);

	EntityList getAllImageExtendedAttachments();

	EntityList getAllExternalSystems();

	EntityList getAllGenericExternalSystems();

	EntityList getAllExternalSystemCompaines();

	EntityList getAllAmmModels();
	
	EntityList getAllAmmModelsForLoggedUser();

	EntityList getAllTaskGroups();

	EntityList getTaskGroupRICheck(int var1);

	EntityList getAllAuthenticationPolicys();

	EntityList getActiveAuthenticationPolicys();

	EntityList getActiveAuthenticationPolicyForLogon();

	EntityList getAllLogonHistorys();

	EntityList getAllPasswordHistorys();

	EntityList getUserPasswordHistory(int var1);

	EntityList getAllFormRebuilds();
	
	EntityList getAllFormRebuildsForLoggedUser();

	EntityList getAllBudgetCyclesInRebuilds();

	EntityList getAllPackagesForFinanceCube(int var1);

	EntityList getAllCubeFormulas();

	EntityList getCubeFormulaeForFinanceCube(int var1);

	ModelRef getModelEntityRef(Object var1);

	FinanceCubeRef getFinanceCubeEntityRef(Object var1);

	BudgetCycleRef getBudgetCycleEntityRef(Object var1);

	ResponsibilityAreaRef getResponsibilityAreaEntityRef(Object var1);

	BudgetUserRef getBudgetUserEntityRef(Object var1);

	DataTypeRef getDataTypeEntityRef(Object var1);

	CurrencyRef getCurrencyEntityRef(Object var1);

	DimensionRef getDimensionEntityRef(Object var1);
	
	DimensionElementRef getDimensionElementEntityRef(Object var1);

	HierarchyRef getHierarchyEntityRef(Object var1);

	UserRef getUserEntityRef(Object var1);

	RoleRef getRoleEntityRef(Object var1);

	BudgetInstructionRef getBudgetInstructionEntityRef(Object var1);

	SystemPropertyRef getSystemPropertyEntityRef(Object var1);

	XmlFormRef getXmlFormEntityRef(Object var1);

	XmlReportRef getXmlReportEntityRef(Object var1);

	XmlReportFolderRef getXmlReportFolderEntityRef(Object var1);

	DataEntryProfileRef getDataEntryProfileEntityRef(Object var1);

	UdefLookupRef getUdefLookupEntityRef(Object var1);

	SecurityRangeRef getSecurityRangeEntityRef(Object var1);

	SecurityAccessDefRef getSecurityAccessDefEntityRef(Object var1);

	SecurityGroupRef getSecurityGroupEntityRef(Object var1);

	CcDeploymentRef getCcDeploymentEntityRef(Object var1);

	CellCalcRef getCellCalcEntityRef(Object var1);

	ChangeMgmtRef getChangeMgmtEntityRef(Object var1);

	ImpExpHdrRef getImpExpHdrEntityRef(Object var1);

	ReportRef getReportEntityRef(Object var1);

	VirementCategoryRef getVirementCategoryEntityRef(Object var1);

	BudgetLimitRef getBudgetLimitEntityRef(Object var1);

	PerfTestRef getPerfTestEntityRef(Object var1);

	PerfTestRunRef getPerfTestRunEntityRef(Object var1);

	PerfTestRunResultRef getPerfTestRunResultEntityRef(Object var1);

	MessageRef getMessageEntityRef(Object var1);

	RechargeRef getRechargeEntityRef(Object var1);

	RechargeGroupRef getRechargeGroupEntityRef(Object var1);

	VirementRequestRef getVirementRequestEntityRef(Object var1);

	ReportTypeRef getReportTypeEntityRef(Object var1);

	ReportDefinitionRef getReportDefinitionEntityRef(Object var1);

	ReportTemplateRef getReportTemplateEntityRef(Object var1);

	ReportMappingTemplateRef getReportMappingTemplateEntityRef(Object var1);

	ExternalDestinationRef getExternalDestinationEntityRef(Object var1);

	InternalDestinationRef getInternalDestinationEntityRef(Object var1);

	DistributionRef getDistributionEntityRef(Object var1);

	ReportPackRef getReportPackEntityRef(Object var1);

	WeightingProfileRef getWeightingProfileEntityRef(Object var1);

	WeightingDeploymentRef getWeightingDeploymentEntityRef(Object var1);

	TidyTaskRef getTidyTaskEntityRef(Object var1);

	ExtendedAttachmentRef getExtendedAttachmentEntityRef(Object var1);

	ExternalSystemRef getExternalSystemEntityRef(Object var1);

	AmmModelRef getAmmModelEntityRef(Object var1);

	TaskGroupRef getTaskGroupEntityRef(Object var1);

	AuthenticationPolicyRef getAuthenticationPolicyEntityRef(Object var1);

	LogonHistoryRef getLogonHistoryEntityRef(Object var1);

	PasswordHistoryRef getPasswordHistoryEntityRef(Object var1);

	FormRebuildRef getFormRebuildEntityRef(Object var1);

	CubeFormulaRef getCubeFormulaEntityRef(Object var1);

	EntityRef getEntityRef(Object var1, String var2);

	EntityList getBudgetDetailsForUser(int var1);

	EntityList getBudgetDetailsForUser(int var1, int var2);

	EntityList getBudgetDetailsForUser(int var1, boolean var2, int var3, int var4);

	EntityList getBudgetUserDetails(int var1, int[] var2);

	EntityList getBudgetUserDetailsNodeDown(int var1, int var2, int var3);

	EntityList getBudgetUserAuthDetailsNodeUp(int var1, int var2, int var3);

	EntityList getImmediateChildren(Object var1);

	EntityList getStructureElementValues(Object var1);

	EntityList getStructureElementIdFromModel(int var1);

	EntityList getPickerStartUpDetails(int var1, int[] var2, int var3);

	EntityList getAllPublicXmlReportFolders();

	EntityList getAllXmlReportFoldersForUser(int var1);

	EntityList getTreeInfoForDimTypeInModel(int var1, int var2);

	EntityList getTreeInfoForModel(int var1);

	EntityList getTreeInfoForModelDimTypes(int var1, int[] var2);

	EntityList getTreeInfoForModelDimSeq(int var1, int[] var2);

	EntityList getTreeInfoForModelRA(int var1);

	EntityList getPickerDataTypesWeb(int[] var1, boolean var2);

	EntityList getPickerDataTypesWeb(int var1, int[] var2, boolean var3);

	EntityList getCellCalcAccesDefs(int var1);

	EntityList doElementPickerSearch(int var1, String var2);

	EntityList getContactLocations(int var1, int var2);

	EntityList getModernWelcomeDetails(int var1, int var2);

	boolean hasUserAccessToRespArea(int var1, int var2, int var3);

	EntityList getAllFinanceXmlFormsForModelAndUser(int var1, int budgetCycleId, int var2, boolean var3);

	EntityList getSummaryUnreadMessagesForUser(String var1);

	EntityList getDistinctInternalDestinationUsers(String[] var1);

	EntityList getDistinctExternalDestinationUsers(String[] var1);

	List getLookupTableData(String var1, List var2);

	EntityList getTaskEvents(int var1);

	EntityList getHierarcyDetailsFromDimId(DimensionRef var1);

	EntityList getStructureElementParents(StructureElementRef var1);

	EntityList getChildrenForParent(StructureElementRef var1);

	EntityList getAllAmmModelsExceptThis(Object var1);

	EntityList getAllLogonHistorysReport(String var1, Timestamp var2, int var3);

	EntityList getAllTaskGroups(Object var1);

	EntityList getAllXmlFormsAndProfiles(String var1, String var2, String var3);

	List<Integer> getReadOnlyRaAccessPositions(int var1, int var2);

	EntityList getUserMessageAttributesForMultipleIds(String[] paramArrayOfString);

	EntityList getMailDetailForUser(String paramString, int paramInt1, int paramInt2, int paramInt3);

	EntityList getAllMasterQuestions();

	EntityList getQuestionByID(int param1);

	EntityList getAllChallengeQuestions();

	EntityList getAllQuestionsAndAnswersByUserID(int param1);

	EntityList getAllUserResetLinks();

	EntityList getLinkByUserID(int param1);
	
	EntityList getChallengeWord(int userId);
	
	void setChallengeWord(int userId, String word);

	MasterQuestionRef getMasterQuestionEntityRef(Object paramObject);

	ChallengeQuestionRef getChallengeQuestionEntityRef(Object paramObject);

	UserResetLinkRef getUserResetLinkEntityRef(Object paramObject);

	EntityList getModelUserSecurity();

	EntityList getUserModelSecurity();
	
	List<UserModelElementAssignment> getRespAreaAccess();
	
	EntityList getDataEntryProfile(int bcId, int modelId);

	String getCPContextId(Object context);

	Object getCPContext(String id);
	
	void removeContext(Object context);
	
	void removeContextByContextId(List<String> contextIds);
    
    void removeContextByUserName(List<String> userNames);
	
	Map getContextSnapShot();
	
	EntityList getAllLoggedInUsers();
	
	EntityList getAllUdefLookupsForLoggedUser();
	
	EntityList getAllMassVirementXmlFormsForLoggedUser();
	
	ArrayList<Object[]> getNotesForCostCenters(ArrayList<Integer> costCenters, int financeCubeId, String fromDate, String toDate);
	
	HashMap<String, ArrayList<HierarchyRef>> getCalendarForModels(HashSet<String> models);
}
