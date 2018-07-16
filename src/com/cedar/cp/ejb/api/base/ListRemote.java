package com.cedar.cp.ejb.api.base;

import com.cedar.cp.api.base.EntityList;
import com.cedar.cp.api.base.EntityRef;
import com.cedar.cp.api.budgetlocation.UserModelElementAssignment;
import com.cedar.cp.api.dimension.HierarchyRef;
import com.cedar.cp.api.user.UserRef;
import com.cedar.cp.dto.admin.tidytask.AllTidyTasksELO;
import com.cedar.cp.dto.admin.tidytask.OrderedChildrenELO;
import com.cedar.cp.dto.authenticationpolicy.ActiveAuthenticationPolicyForLogonELO;
import com.cedar.cp.dto.authenticationpolicy.ActiveAuthenticationPolicysELO;
import com.cedar.cp.dto.authenticationpolicy.AllAuthenticationPolicysELO;
import com.cedar.cp.dto.base.PrimaryKey;
import com.cedar.cp.dto.budgetinstruction.AllBudgetInstructionAssignmentsELO;
import com.cedar.cp.dto.budgetinstruction.AllBudgetInstructionsELO;
import com.cedar.cp.dto.budgetinstruction.AllBudgetInstructionsForCycleELO;
import com.cedar.cp.dto.budgetinstruction.AllBudgetInstructionsForLocationELO;
import com.cedar.cp.dto.budgetinstruction.AllBudgetInstructionsForModelELO;
import com.cedar.cp.dto.budgetinstruction.AllBudgetInstructionsWebELO;
import com.cedar.cp.dto.cm.AllChangeMgmtsELO;
import com.cedar.cp.dto.cm.AllChangeMgmtsForModelELO;
import com.cedar.cp.dto.cm.AllChangeMgmtsForModelWithXMLELO;
import com.cedar.cp.dto.cubeformula.AllCubeFormulasELO;
import com.cedar.cp.dto.cubeformula.AllPackagesForFinanceCubeELO;
import com.cedar.cp.dto.cubeformula.CubeFormulaeForFinanceCubeELO;
import com.cedar.cp.dto.currency.AllCurrencysELO;
import com.cedar.cp.dto.datatype.AllDataTypeForFinanceCubeELO;
import com.cedar.cp.dto.datatype.AllDataTypesELO;
import com.cedar.cp.dto.datatype.AllDataTypesForModelELO;
import com.cedar.cp.dto.datatype.AllDataTypesWebELO;
import com.cedar.cp.dto.datatype.DataTypeDependenciesELO;
import com.cedar.cp.dto.datatype.DataTypeDetailsForVisIDELO;
import com.cedar.cp.dto.datatype.DataTypesByTypeELO;
import com.cedar.cp.dto.datatype.DataTypesByTypeWriteableELO;
import com.cedar.cp.dto.datatype.DataTypesForImpExpELO;
import com.cedar.cp.dto.dimension.AllDimensionElementsELO;
import com.cedar.cp.dto.dimension.AllDimensionElementsForDimensionELO;
import com.cedar.cp.dto.dimension.AllDimensionElementsForModelELO;
import com.cedar.cp.dto.dimension.AllDimensionsELO;
import com.cedar.cp.dto.dimension.AllDimensionsForModelELO;
import com.cedar.cp.dto.dimension.AllDisabledLeafELO;
import com.cedar.cp.dto.dimension.AllDisabledLeafandNotPlannableELO;
import com.cedar.cp.dto.dimension.AllFeedsForDimensionElementELO;
import com.cedar.cp.dto.dimension.AllHierarchysELO;
import com.cedar.cp.dto.dimension.AllLeafStructureElementsELO;
import com.cedar.cp.dto.dimension.AllNotPlannableELO;
import com.cedar.cp.dto.dimension.AllSecurityRangesELO;
import com.cedar.cp.dto.dimension.AllSecurityRangesForModelELO;
import com.cedar.cp.dto.dimension.AllStructureElementsELO;
import com.cedar.cp.dto.dimension.AugHierarachyElementELO;
import com.cedar.cp.dto.dimension.AvailableDimensionsELO;
import com.cedar.cp.dto.dimension.BudgetHierarchyElementELO;
import com.cedar.cp.dto.dimension.BudgetLocationElementForModelELO;
import com.cedar.cp.dto.dimension.CalendarForFinanceCubeELO;
import com.cedar.cp.dto.dimension.CalendarForModelELO;
import com.cedar.cp.dto.dimension.CalendarForModelVisIdELO;
import com.cedar.cp.dto.dimension.CheckLeafELO;
import com.cedar.cp.dto.dimension.ChildrenForParentELO;
import com.cedar.cp.dto.dimension.HierarachyElementELO;
import com.cedar.cp.dto.dimension.HierarcyDetailsFromDimIdELO;
import com.cedar.cp.dto.dimension.HierarcyDetailsIncRootNodeFromDimIdELO;
import com.cedar.cp.dto.dimension.ImmediateChildrenELO;
import com.cedar.cp.dto.dimension.ImportableDimensionsELO;
import com.cedar.cp.dto.dimension.ImportableHierarchiesELO;
import com.cedar.cp.dto.dimension.LeafPlannableBudgetLocationsForModelELO;
import com.cedar.cp.dto.dimension.LeavesForParentELO;
import com.cedar.cp.dto.dimension.MassStateImmediateChildrenELO;
import com.cedar.cp.dto.dimension.ReportChildrenForParentToRelativeDepthELO;
import com.cedar.cp.dto.dimension.ReportLeavesForParentELO;
import com.cedar.cp.dto.dimension.RespAreaImmediateChildrenELO;
import com.cedar.cp.dto.dimension.SelectedHierarchysELO;
import com.cedar.cp.dto.dimension.StructureElementByVisIdELO;
import com.cedar.cp.dto.dimension.StructureElementELO;
import com.cedar.cp.dto.dimension.StructureElementForIdsELO;
import com.cedar.cp.dto.dimension.StructureElementParentsELO;
import com.cedar.cp.dto.dimension.StructureElementParentsFromVisIdELO;
import com.cedar.cp.dto.dimension.StructureElementParentsReversedELO;
import com.cedar.cp.dto.dimension.StructureElementValuesELO;
import com.cedar.cp.dto.extendedattachment.AllExtendedAttachmentsELO;
import com.cedar.cp.dto.extendedattachment.AllImageExtendedAttachmentsELO;
import com.cedar.cp.dto.extendedattachment.ExtendedAttachmentsForIdELO;
import com.cedar.cp.dto.extsys.AllExternalSystemCompainesELO;
import com.cedar.cp.dto.extsys.AllExternalSystemsELO;
import com.cedar.cp.dto.extsys.AllGenericExternalSystemsELO;
import com.cedar.cp.dto.formnotes.AllFormNotesForBudgetLocationELO;
import com.cedar.cp.dto.formnotes.AllFormNotesForFormAndBudgetLocationELO;
import com.cedar.cp.dto.impexp.AllImpExpHdrsELO;
import com.cedar.cp.dto.importtask.AllImportTasksELO;
import com.cedar.cp.dto.logonhistory.AllLogonHistorysELO;
import com.cedar.cp.dto.logonhistory.AllLogonHistorysReportELO;
import com.cedar.cp.dto.masterquestion.AllMasterQuestionsELO;
import com.cedar.cp.dto.masterquestion.QuestionByIDELO;
import com.cedar.cp.dto.message.AllMessagesELO;
import com.cedar.cp.dto.message.AllMessagesToUserELO;
import com.cedar.cp.dto.message.AttatchmentForMessageELO;
import com.cedar.cp.dto.message.InBoxForUserELO;
import com.cedar.cp.dto.message.MessageCountELO;
import com.cedar.cp.dto.message.MessageForIdELO;
import com.cedar.cp.dto.message.MessageForIdSentItemELO;
import com.cedar.cp.dto.message.MessageFromUserELO;
import com.cedar.cp.dto.message.MessageToUserELO;
import com.cedar.cp.dto.message.SentItemsForUserELO;
import com.cedar.cp.dto.message.UnreadInBoxForUserELO;
import com.cedar.cp.dto.model.AllAccessDefsUsingRangeELO;
import com.cedar.cp.dto.model.AllAttachedDataTypesForFinanceCubeELO;
import com.cedar.cp.dto.model.AllBudgetCyclesELO;
import com.cedar.cp.dto.model.AllBudgetCyclesWebDetailedELO;
import com.cedar.cp.dto.model.AllBudgetCyclesWebELO;
import com.cedar.cp.dto.model.AllBudgetHierarchiesELO;
import com.cedar.cp.dto.model.AllBudgetUsersELO;
import com.cedar.cp.dto.model.AllCellCalcAssocsELO;
import com.cedar.cp.dto.model.AllCellCalcsELO;
import com.cedar.cp.dto.model.AllDataTypesAttachedToFinanceCubeForModelELO;
import com.cedar.cp.dto.model.AllDataTypesForFinanceCubeELO;
import com.cedar.cp.dto.model.AllFinanceCubeDataTypesELO;
import com.cedar.cp.dto.model.AllFinanceCubesELO;
import com.cedar.cp.dto.model.AllFinanceCubesForDataTypeELO;
import com.cedar.cp.dto.model.AllFinanceCubesWebELO;
import com.cedar.cp.dto.model.AllFinanceCubesWebForModelELO;
import com.cedar.cp.dto.model.AllFinanceCubesWebForUserELO;
import com.cedar.cp.dto.model.AllModelBusAndAccDimensionsELO;
import com.cedar.cp.dto.model.AllModelBusinessDimensionsELO;
import com.cedar.cp.dto.model.AllModelsELO;
import com.cedar.cp.dto.model.AllModelsWebELO;
import com.cedar.cp.dto.model.AllModelsWebForUserELO;
import com.cedar.cp.dto.model.AllModelsWithActiveCycleForUserELO;
import com.cedar.cp.dto.model.AllRootsForModelELO;
import com.cedar.cp.dto.model.AllSecurityAccessDefsELO;
import com.cedar.cp.dto.model.AllSecurityAccessDefsForModelELO;
import com.cedar.cp.dto.model.AllSecurityGroupsELO;
import com.cedar.cp.dto.model.AllSecurityGroupsForUserELO;
import com.cedar.cp.dto.model.AllSecurityGroupsUsingAccessDefELO;
import com.cedar.cp.dto.model.AllSimpleFinanceCubesELO;
import com.cedar.cp.dto.model.AllUsersForASecurityGroupELO;
import com.cedar.cp.dto.model.BudgetCycleDetailedForIdELO;
import com.cedar.cp.dto.model.BudgetCycleIntegrityELO;
import com.cedar.cp.dto.model.BudgetCyclesForModelELO;
import com.cedar.cp.dto.model.BudgetCyclesForModelWithStateELO;
import com.cedar.cp.dto.model.BudgetCyclesToFixStateELO;
import com.cedar.cp.dto.model.BudgetDetailsForUserELO;
import com.cedar.cp.dto.model.BudgetDimensionIdForModelELO;
import com.cedar.cp.dto.model.BudgetHierarchyRootNodeForModelELO;
import com.cedar.cp.dto.model.BudgetTransferBudgetCyclesELO;
import com.cedar.cp.dto.model.BudgetUsersForNodeELO;
import com.cedar.cp.dto.model.CalendarSpecForModelELO;
import com.cedar.cp.dto.model.CellCalcIntegrityELO;
import com.cedar.cp.dto.model.CheckIfHasStateELO;
import com.cedar.cp.dto.model.CheckUserAccessELO;
import com.cedar.cp.dto.model.CheckUserAccessToModelELO;
import com.cedar.cp.dto.model.CheckUserELO;
import com.cedar.cp.dto.model.CycleStateDetailsELO;
import com.cedar.cp.dto.model.DimensionIdForModelDimTypeELO;
import com.cedar.cp.dto.model.FinanceCubeAllDimensionsAndHierachiesELO;
import com.cedar.cp.dto.model.FinanceCubeDetailsELO;
import com.cedar.cp.dto.model.FinanceCubeDimensionsAndHierachiesELO;
import com.cedar.cp.dto.model.FinanceCubesForModelELO;
import com.cedar.cp.dto.model.FinanceCubesUsingDataTypeELO;
import com.cedar.cp.dto.model.HierarchiesForModelELO;
import com.cedar.cp.dto.model.ImportableFinanceCubeDataTypesELO;
import com.cedar.cp.dto.model.MaxDepthForBudgetHierarchyELO;
import com.cedar.cp.dto.model.ModelDetailsWebELO;
import com.cedar.cp.dto.model.ModelDimensionsELO;
import com.cedar.cp.dto.model.ModelDimensionseExcludeCallELO;
import com.cedar.cp.dto.model.ModelForDimensionELO;
import com.cedar.cp.dto.model.NodesForUserAndCycleELO;
import com.cedar.cp.dto.model.NodesForUserAndModelELO;
import com.cedar.cp.dto.model.UsersForModelAndElementELO;
import com.cedar.cp.dto.model.act.ActivitiesForCycleandElementELO;
import com.cedar.cp.dto.model.act.ActivityDetailsELO;
import com.cedar.cp.dto.model.act.ActivityFullDetailsELO;
import com.cedar.cp.dto.model.amm.AllAmmModelsELO;
import com.cedar.cp.dto.model.budgetlimit.AllBudgetLimitsELO;
import com.cedar.cp.dto.model.cc.AllCcDeploymentsELO;
import com.cedar.cp.dto.model.cc.CcDeploymentCellPickerInfoELO;
import com.cedar.cp.dto.model.cc.CcDeploymentXMLFormTypeELO;
import com.cedar.cp.dto.model.cc.CcDeploymentsForLookupTableELO;
import com.cedar.cp.dto.model.cc.CcDeploymentsForModelELO;
import com.cedar.cp.dto.model.cc.CcDeploymentsForXmlFormELO;
import com.cedar.cp.dto.model.globalmapping2.AllGlobalMappedModels2ELO;
import com.cedar.cp.dto.model.mapping.AllMappedModelsELO;
import com.cedar.cp.dto.model.mapping.MappedFinanceCubesELO;
import com.cedar.cp.dto.model.ra.AllResponsibilityAreasELO;
import com.cedar.cp.dto.model.recharge.AllRechargeGroupsELO;
import com.cedar.cp.dto.model.recharge.AllRechargesELO;
import com.cedar.cp.dto.model.recharge.AllRechargesWithModelELO;
import com.cedar.cp.dto.model.recharge.SingleRechargeELO;
import com.cedar.cp.dto.model.udwp.AllWeightingDeploymentsELO;
import com.cedar.cp.dto.model.udwp.AllWeightingProfilesELO;
import com.cedar.cp.dto.model.virement.AccountsForCategoryELO;
import com.cedar.cp.dto.model.virement.AllVirementCategorysELO;
import com.cedar.cp.dto.model.virement.AllVirementRequestGroupsELO;
import com.cedar.cp.dto.model.virement.AllVirementRequestsELO;
import com.cedar.cp.dto.model.virement.LocationsForCategoryELO;
import com.cedar.cp.dto.passwordhistory.AllPasswordHistorysELO;
import com.cedar.cp.dto.passwordhistory.UserPasswordHistoryELO;
import com.cedar.cp.dto.perftest.AllPerfTestsELO;
import com.cedar.cp.dto.perftestrun.AllPerfTestRunResultsELO;
import com.cedar.cp.dto.perftestrun.AllPerfTestRunsELO;
import com.cedar.cp.dto.recalculate.AllRecalculateBatchTasksELO;
import com.cedar.cp.dto.report.AllReportsELO;
import com.cedar.cp.dto.report.AllReportsForAdminELO;
import com.cedar.cp.dto.report.AllReportsForUserELO;
import com.cedar.cp.dto.report.WebReportDetailsELO;
import com.cedar.cp.dto.report.definition.AllPublicReportByTypeELO;
import com.cedar.cp.dto.report.definition.AllReportDefCalcByCCDeploymentIdELO;
import com.cedar.cp.dto.report.definition.AllReportDefCalcByModelIdELO;
import com.cedar.cp.dto.report.definition.AllReportDefCalcByReportTemplateIdELO;
import com.cedar.cp.dto.report.definition.AllReportDefFormcByModelIdELO;
import com.cedar.cp.dto.report.definition.AllReportDefFormcByReportTemplateIdELO;
import com.cedar.cp.dto.report.definition.AllReportDefMappedExcelcByModelIdELO;
import com.cedar.cp.dto.report.definition.AllReportDefMappedExcelcByReportTemplateIdELO;
import com.cedar.cp.dto.report.definition.AllReportDefSummaryCalcByCCDeploymentIdELO;
import com.cedar.cp.dto.report.definition.AllReportDefSummaryCalcByModelIdELO;
import com.cedar.cp.dto.report.definition.AllReportDefSummaryCalcByReportTemplateIdELO;
import com.cedar.cp.dto.report.definition.AllReportDefinitionsELO;
import com.cedar.cp.dto.report.definition.CheckFormIsUsedELO;
import com.cedar.cp.dto.report.definition.ReportDefinitionForVisIdELO;
import com.cedar.cp.dto.report.destination.external.AllExternalDestinationDetailsELO;
import com.cedar.cp.dto.report.destination.external.AllExternalDestinationsELO;
import com.cedar.cp.dto.report.destination.external.AllUsersForExternalDestinationIdELO;
import com.cedar.cp.dto.report.destination.internal.AllInternalDestinationDetailsELO;
import com.cedar.cp.dto.report.destination.internal.AllInternalDestinationUsersELO;
import com.cedar.cp.dto.report.destination.internal.AllInternalDestinationsELO;
import com.cedar.cp.dto.report.destination.internal.AllUsersForInternalDestinationIdELO;
import com.cedar.cp.dto.report.destination.internal.CheckInternalDestinationUsersELO;
import com.cedar.cp.dto.report.distribution.AllDistributionsELO;
import com.cedar.cp.dto.report.distribution.CheckExternalDestinationELO;
import com.cedar.cp.dto.report.distribution.CheckInternalDestinationELO;
import com.cedar.cp.dto.report.distribution.DistributionDetailsForVisIdELO;
import com.cedar.cp.dto.report.distribution.DistributionForVisIdELO;
import com.cedar.cp.dto.report.mappingtemplate.AllReportMappingTemplatesELO;
import com.cedar.cp.dto.report.pack.AllReportPacksELO;
import com.cedar.cp.dto.report.pack.CheckReportDefELO;
import com.cedar.cp.dto.report.pack.CheckReportDistributionELO;
import com.cedar.cp.dto.report.pack.ReportDefDistListELO;
import com.cedar.cp.dto.report.template.AllReportTemplatesELO;
import com.cedar.cp.dto.report.type.AllReportTypesELO;
import com.cedar.cp.dto.report.type.param.AllReportTypeParamsELO;
import com.cedar.cp.dto.report.type.param.AllReportTypeParamsforTypeELO;
import com.cedar.cp.dto.reset.AllChallengeQuestionsELO;
import com.cedar.cp.dto.reset.AllQuestionsAndAnswersByUserIDELO;
import com.cedar.cp.dto.reset.AllUserResetLinksELO;
import com.cedar.cp.dto.reset.LinkByUserIDELO;
import com.cedar.cp.dto.role.AllHiddenRolesELO;
import com.cedar.cp.dto.role.AllRolesELO;
import com.cedar.cp.dto.role.AllRolesForUserELO;
import com.cedar.cp.dto.role.AllSecurityRolesELO;
import com.cedar.cp.dto.role.AllSecurityRolesForRoleELO;
import com.cedar.cp.dto.systemproperty.AllMailPropsELO;
import com.cedar.cp.dto.systemproperty.AllSystemPropertysELO;
import com.cedar.cp.dto.systemproperty.AllSystemPropertysUncachedELO;
import com.cedar.cp.dto.systemproperty.SystemPropertyELO;
import com.cedar.cp.dto.systemproperty.WebSystemPropertyELO;
import com.cedar.cp.dto.task.AllWebTasksELO;
import com.cedar.cp.dto.task.AllWebTasksForUserELO;
import com.cedar.cp.dto.task.WebTasksDetailsELO;
import com.cedar.cp.dto.task.group.AllTaskGroupsELO;
import com.cedar.cp.dto.task.group.TaskGroupRICheckELO;
import com.cedar.cp.dto.udeflookup.AllUdefLookupsELO;
import com.cedar.cp.dto.user.AllDashboardsForUserELO;
import com.cedar.cp.dto.user.AllDataEntryProfileHistorysELO;
import com.cedar.cp.dto.user.AllDataEntryProfilesELO;
import com.cedar.cp.dto.user.AllDataEntryProfilesForFormELO;
import com.cedar.cp.dto.user.AllDataEntryProfilesForUserELO;
import com.cedar.cp.dto.user.AllNonDisabledUsersELO;
import com.cedar.cp.dto.user.AllRevisionsELO;
import com.cedar.cp.dto.user.AllRolesForUsersELO;
import com.cedar.cp.dto.user.AllUserAttributesELO;
import com.cedar.cp.dto.user.AllUsersELO;
import com.cedar.cp.dto.user.AllUsersExportELO;
import com.cedar.cp.dto.user.AllUsersForDataEntryProfilesForModelELO;
import com.cedar.cp.dto.user.DefaultDataEntryProfileELO;
import com.cedar.cp.dto.user.FinanceSystemUserNameELO;
import com.cedar.cp.dto.user.SecurityStringsForUserELO;
import com.cedar.cp.dto.user.UserMessageAttributesELO;
import com.cedar.cp.dto.user.UserMessageAttributesForIdELO;
import com.cedar.cp.dto.user.UserMessageAttributesForNameELO;
import com.cedar.cp.dto.user.UserPreferencesForUserELO;
import com.cedar.cp.dto.user.UsersWithSecurityStringELO;
import com.cedar.cp.dto.xmlform.AllDynamicXMLFormsELO;
import com.cedar.cp.dto.xmlform.AllFFXmlFormsELO;
import com.cedar.cp.dto.xmlform.AllFinXmlFormsELO;
import com.cedar.cp.dto.xmlform.AllFinanceAndFlatFormsELO;
import com.cedar.cp.dto.xmlform.AllFinanceAndFlatFormsForModelELO;
import com.cedar.cp.dto.xmlform.AllFinanceXmlFormsAndDataTypesForModelELO;
import com.cedar.cp.dto.xmlform.AllFinanceXmlFormsELO;
import com.cedar.cp.dto.xmlform.AllFinanceXmlFormsForModelELO;
import com.cedar.cp.dto.xmlform.AllFixedXMLFormsELO;
import com.cedar.cp.dto.xmlform.AllFlatXMLFormsELO;
import com.cedar.cp.dto.xmlform.AllMassVirementXmlFormsELO;
import com.cedar.cp.dto.xmlform.AllXMLFormUserLinkELO;
import com.cedar.cp.dto.xmlform.AllXcellXmlFormsELO;
import com.cedar.cp.dto.xmlform.AllXmlFormsAndProfilesELO;
import com.cedar.cp.dto.xmlform.AllXmlFormsELO;
import com.cedar.cp.dto.xmlform.CheckXMLFormUserLinkELO;
import com.cedar.cp.dto.xmlform.XMLFormCellPickerInfoELO;
import com.cedar.cp.dto.xmlform.XMLFormDefinitionELO;
import com.cedar.cp.dto.xmlform.rebuild.AllBudgetCyclesInRebuildsELO;
import com.cedar.cp.dto.xmlform.rebuild.AllFormRebuildsELO;
import com.cedar.cp.dto.xmlreport.AllPublicXmlReportsELO;
import com.cedar.cp.dto.xmlreport.AllXmlReportsELO;
import com.cedar.cp.dto.xmlreport.AllXmlReportsForUserELO;
import com.cedar.cp.dto.xmlreport.SingleXmlReportELO;
import com.cedar.cp.dto.xmlreport.XmlReportsForFolderELO;
import com.cedar.cp.dto.xmlreportfolder.AllPublicXmlReportFoldersELO;
import com.cedar.cp.dto.xmlreportfolder.AllXmlReportFoldersELO;
import com.cedar.cp.dto.xmlreportfolder.AllXmlReportFoldersForUserELO;
import com.cedar.cp.dto.xmlreportfolder.DecendentFoldersELO;
import com.cedar.cp.dto.xmlreportfolder.ReportFolderWithIdELO;

import java.rmi.RemoteException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import javax.ejb.EJBException;
import javax.ejb.EJBObject;

public interface ListRemote extends EJBObject {

   AllModelsELO getAllModels() throws RemoteException;

   AllModelsWebELO getAllModelsWeb() throws RemoteException;

   AllModelsWebForUserELO getAllModelsWebForUser(int var1) throws RemoteException;

   AllModelsWithActiveCycleForUserELO getAllModelsWithActiveCycleForUser(int var1) throws RemoteException;

   AllBudgetHierarchiesELO getAllBudgetHierarchies() throws RemoteException;

   ModelForDimensionELO getModelForDimension(int var1) throws RemoteException;

   ModelDimensionsELO getModelDimensions(int var1) throws RemoteException;

   ModelDimensionseExcludeCallELO getModelDimensionseExcludeCall(int var1) throws RemoteException;

   ModelDetailsWebELO getModelDetailsWeb(int var1) throws RemoteException;

   AllRootsForModelELO getAllRootsForModel(int var1) throws RemoteException;

   BudgetHierarchyRootNodeForModelELO getBudgetHierarchyRootNodeForModel(int var1) throws RemoteException;

   BudgetCyclesToFixStateELO getBudgetCyclesToFixState(int var1) throws RemoteException;

   MaxDepthForBudgetHierarchyELO getMaxDepthForBudgetHierarchy(int var1) throws RemoteException;

   CalendarSpecForModelELO getCalendarSpecForModel(int var1) throws RemoteException;

   HierarchiesForModelELO getHierarchiesForModel(int var1) throws RemoteException;

   AllUsersForASecurityGroupELO getAllUsersForASecurityGroup(int var1) throws RemoteException;

   AllModelBusinessDimensionsELO getAllModelBusinessDimensions() throws RemoteException;

   AllModelBusAndAccDimensionsELO getAllModelBusAndAccDimensions() throws RemoteException;

   BudgetDimensionIdForModelELO getBudgetDimensionIdForModel(int var1, int var2) throws RemoteException;

   DimensionIdForModelDimTypeELO getDimensionIdForModelDimType(int var1, int var2) throws RemoteException;

   AllFinanceCubesELO getAllFinanceCubes() throws RemoteException;

   AllSimpleFinanceCubesELO getAllSimpleFinanceCubes(int userId) throws RemoteException;

   AllDataTypesAttachedToFinanceCubeForModelELO getAllDataTypesAttachedToFinanceCubeForModel(int var1) throws RemoteException;

   FinanceCubesForModelELO getFinanceCubesForModel(int var1) throws RemoteException;

   FinanceCubeDimensionsAndHierachiesELO getFinanceCubeDimensionsAndHierachies(int var1) throws RemoteException;

   FinanceCubeAllDimensionsAndHierachiesELO getFinanceCubeAllDimensionsAndHierachies(int var1) throws RemoteException;

   AllFinanceCubesWebELO getAllFinanceCubesWeb() throws RemoteException;

   AllFinanceCubesWebForModelELO getAllFinanceCubesWebForModel(int var1) throws RemoteException;

   AllFinanceCubesWebForUserELO getAllFinanceCubesWebForUser(int var1) throws RemoteException;

   FinanceCubeDetailsELO getFinanceCubeDetails(int var1) throws RemoteException;

   FinanceCubesUsingDataTypeELO getFinanceCubesUsingDataType(short var1) throws RemoteException;

   AllFinanceCubeDataTypesELO getAllFinanceCubeDataTypes() throws RemoteException;

   ImportableFinanceCubeDataTypesELO getImportableFinanceCubeDataTypes() throws RemoteException;

   AllAttachedDataTypesForFinanceCubeELO getAllAttachedDataTypesForFinanceCube(int var1) throws RemoteException;

   AllDataTypesForFinanceCubeELO getAllDataTypesForFinanceCube(int var1) throws RemoteException;

   AllFinanceCubesForDataTypeELO getAllFinanceCubesForDataType(short var1) throws RemoteException;

   AllBudgetCyclesELO getAllBudgetCycles() throws RemoteException;

   AllBudgetCyclesWebELO getAllBudgetCyclesWeb() throws RemoteException;

   AllBudgetCyclesWebDetailedELO getAllBudgetCyclesWebDetailed() throws RemoteException;

   BudgetCyclesForModelELO getBudgetCyclesForModel(int var1) throws RemoteException;

   BudgetCyclesForModelWithStateELO getBudgetCyclesForModelWithState(int var1, int var2) throws RemoteException;

   BudgetCycleIntegrityELO getBudgetCycleIntegrity() throws RemoteException;

   BudgetCycleDetailedForIdELO getBudgetCycleDetailedForId(int var1) throws RemoteException;
   
   BudgetCycleDetailedForIdELO getBudgetCycleXmlFormsForId(int var1,int userId) throws RemoteException;
   
   BudgetTransferBudgetCyclesELO getBudgetTransferBudgetCycles() throws RemoteException;

   CheckIfHasStateELO getCheckIfHasState(int var1, int var2) throws RemoteException;

   CycleStateDetailsELO getCycleStateDetails(int var1) throws RemoteException;

   AllResponsibilityAreasELO getAllResponsibilityAreas() throws RemoteException;

   AllBudgetUsersELO getAllBudgetUsers() throws RemoteException;

   CheckUserAccessToModelELO getCheckUserAccessToModel(int var1, int var2) throws RemoteException;

   CheckUserAccessELO getCheckUserAccess(int var1, int var2) throws RemoteException;

   CheckUserELO getCheckUser(int var1) throws RemoteException;

   BudgetUsersForNodeELO getBudgetUsersForNode(int var1, int var2) throws RemoteException;

   NodesForUserAndCycleELO getNodesForUserAndCycle(int var1, int var2) throws RemoteException;

   NodesForUserAndModelELO getNodesForUserAndModel(int var1, int var2) throws RemoteException;

   UsersForModelAndElementELO getUsersForModelAndElement(int var1, int var2) throws RemoteException;

   AllDataTypesELO getAllDataTypes() throws RemoteException;

   AllDataTypesWebELO getAllDataTypesWeb() throws RemoteException;

   AllDataTypeForFinanceCubeELO getAllDataTypeForFinanceCube(int var1) throws RemoteException;

   AllDataTypesForModelELO getAllDataTypesForModel(int var1) throws RemoteException;

   DataTypesByTypeELO getDataTypesByType(int var1) throws RemoteException;

   DataTypesByTypeWriteableELO getDataTypesByTypeWriteable(int var1) throws RemoteException;

   DataTypeDependenciesELO getDataTypeDependencies(short var1) throws RemoteException;

   DataTypesForImpExpELO getDataTypesForImpExp() throws RemoteException;

   DataTypeDetailsForVisIDELO getDataTypeDetailsForVisID(String var1) throws RemoteException;

   AllCurrencysELO getAllCurrencys() throws RemoteException;

   AllStructureElementsELO getAllStructureElements(int var1) throws RemoteException;

   AllLeafStructureElementsELO getAllLeafStructureElements(int var1) throws RemoteException;

   LeafPlannableBudgetLocationsForModelELO getLeafPlannableBudgetLocationsForModel(int var1) throws RemoteException;

   StructureElementParentsELO getStructureElementParents(int var1, int var2) throws RemoteException;

   StructureElementParentsReversedELO getStructureElementParentsReversed(int var1, int var2) throws RemoteException;

   StructureElementParentsFromVisIdELO getStructureElementParentsFromVisId(String var1, int var2) throws RemoteException;

   ImmediateChildrenELO getImmediateChildren(int var1, int var2) throws RemoteException;

   MassStateImmediateChildrenELO getMassStateImmediateChildren(int var1, int var2) throws RemoteException;

   StructureElementValuesELO getStructureElementValues(int var1, int var2) throws RemoteException;

   CheckLeafELO getCheckLeaf(int var1) throws RemoteException;

   StructureElementELO getStructureElement(int var1) throws RemoteException;

   StructureElementForIdsELO getStructureElementForIds(int var1, int var2) throws RemoteException;

   StructureElementByVisIdELO getStructureElementByVisId(String var1, int var2) throws RemoteException;

   RespAreaImmediateChildrenELO getRespAreaImmediateChildren(int var1, int var2) throws RemoteException;

   AllDisabledLeafELO getAllDisabledLeaf(int var1) throws RemoteException;

   AllNotPlannableELO getAllNotPlannable(int var1) throws RemoteException;

   AllDisabledLeafandNotPlannableELO getAllDisabledLeafandNotPlannable(int var1) throws RemoteException;

   LeavesForParentELO getLeavesForParent(int var1, int var2, int var3, int var4, int var5) throws RemoteException;

   ChildrenForParentELO getChildrenForParent(int var1, int var2, int var3, int var4, int var5) throws RemoteException;

   ReportLeavesForParentELO getReportLeavesForParent(int var1, int var2, int var3, int var4, int var5) throws RemoteException;

   ReportChildrenForParentToRelativeDepthELO getReportChildrenForParentToRelativeDepth(int var1, int var2, int var3, int var4, int var5, int var6, int var7, int var8) throws RemoteException;

   BudgetHierarchyElementELO getBudgetHierarchyElement(int var1) throws RemoteException;

   BudgetLocationElementForModelELO getBudgetLocationElementForModel(int var1, int var2) throws RemoteException;

   AllDimensionsELO getAllDimensions() throws RemoteException;

   AvailableDimensionsELO getAvailableDimensions() throws RemoteException;

   ImportableDimensionsELO getImportableDimensions() throws RemoteException;

   AllDimensionsForModelELO getAllDimensionsForModel(int var1) throws RemoteException;

   AllDimensionElementsELO getAllDimensionElements() throws RemoteException;
   
   AllDimensionElementsForDimensionELO getAllDimensionElementsForDimension(int var1) throws RemoteException;
   
   AllDimensionElementsForModelELO getAllDimensionElementsForModels(List<Integer> modelIds) throws RemoteException;

   AllHierarchysELO getAllHierarchys() throws RemoteException;
   
   SelectedHierarchysELO getSelectedHierarchys() throws RemoteException;

   ImportableHierarchiesELO getImportableHierarchies(int var1) throws RemoteException;

   HierarcyDetailsFromDimIdELO getHierarcyDetailsFromDimId(int var1) throws RemoteException;

   HierarcyDetailsIncRootNodeFromDimIdELO getHierarcyDetailsIncRootNodeFromDimId(int var1) throws RemoteException;

   CalendarForModelELO getCalendarForModel(int var1) throws RemoteException;

   CalendarForModelVisIdELO getCalendarForModelVisId(String var1) throws RemoteException;

   CalendarForFinanceCubeELO getCalendarForFinanceCube(int var1) throws RemoteException;

   HierarachyElementELO getHierarachyElement(int var1) throws RemoteException;

   AugHierarachyElementELO getAugHierarachyElement(int var1) throws RemoteException;

   AllFeedsForDimensionElementELO getAllFeedsForDimensionElement(int var1) throws RemoteException;

   AllUsersELO getAllUsers() throws RemoteException;
   
   AllRevisionsELO getAllRevisions() throws RemoteException;
   
   AllDashboardsForUserELO getDashboardForms(Integer userId, boolean isAdmin) throws RemoteException;

   SecurityStringsForUserELO getSecurityStringsForUser(int var1) throws RemoteException;

   AllUsersExportELO getAllUsersExport() throws RemoteException;

   AllUserAttributesELO getAllUserAttributes() throws RemoteException;

   AllNonDisabledUsersELO getAllNonDisabledUsers() throws RemoteException;

   UserMessageAttributesELO getUserMessageAttributes() throws RemoteException;

   UserMessageAttributesForIdELO getUserMessageAttributesForId(int var1) throws RemoteException;

   UserMessageAttributesForNameELO getUserMessageAttributesForName(String var1) throws RemoteException;

   FinanceSystemUserNameELO getFinanceSystemUserName(int var1) throws RemoteException;

   UsersWithSecurityStringELO getUsersWithSecurityString(String var1) throws RemoteException;

   AllRolesForUsersELO getAllRolesForUsers() throws RemoteException;

   AllRolesELO getAllRoles() throws RemoteException;

   AllRolesForUserELO getAllRolesForUser(int var1) throws RemoteException;
   
   AllRolesForUserELO getAllHiddenRolesForUser(int var1) throws RemoteException;

   AllSecurityRolesELO getAllSecurityRoles() throws RemoteException;

   AllSecurityRolesForRoleELO getAllSecurityRolesForRole(int var1) throws RemoteException;

   UserPreferencesForUserELO getUserPreferencesForUser(int var1) throws RemoteException;

   AllBudgetInstructionsELO getAllBudgetInstructions() throws RemoteException;

   AllBudgetInstructionsWebELO getAllBudgetInstructionsWeb() throws RemoteException;

   AllBudgetInstructionsForModelELO getAllBudgetInstructionsForModel(int var1) throws RemoteException;

   AllBudgetInstructionsForCycleELO getAllBudgetInstructionsForCycle(int var1) throws RemoteException;

   AllBudgetInstructionsForLocationELO getAllBudgetInstructionsForLocation(int var1) throws RemoteException;

   AllBudgetInstructionAssignmentsELO getAllBudgetInstructionAssignments() throws RemoteException;

   AllWebTasksELO getAllWebTasks() throws RemoteException;

   AllWebTasksForUserELO getAllWebTasksForUser(String var1) throws RemoteException;

   WebTasksDetailsELO getWebTasksDetails(int var1) throws RemoteException;

   AllSystemPropertysELO getAllSystemPropertys() throws RemoteException;

   AllSystemPropertysUncachedELO getAllSystemPropertysUncached() throws RemoteException;

   AllMailPropsELO getAllMailProps() throws RemoteException;

   SystemPropertyELO getSystemProperty(String var1) throws RemoteException;

   WebSystemPropertyELO getWebSystemProperty(String var1) throws RemoteException;

   AllXmlFormsELO getAllXmlForms() throws RemoteException;

   AllFinXmlFormsELO getAllFinXmlForms(int userId) throws RemoteException;

   AllFFXmlFormsELO getAllFFXmlForms() throws RemoteException;
   
   AllXcellXmlFormsELO getAllXcellXmlForms() throws RemoteException;
   
   AllXmlFormsELO getXcellXmlFormsForUser(int userId) throws RemoteException;

   AllMassVirementXmlFormsELO getAllMassVirementXmlForms() throws RemoteException;

   AllFinanceXmlFormsELO getAllFinanceXmlForms() throws RemoteException;

   AllFinanceAndFlatFormsELO getAllFinanceAndFlatForms() throws RemoteException;

   AllFinanceXmlFormsForModelELO getAllFinanceXmlFormsForModel(int var1) throws RemoteException;

   AllFinanceAndFlatFormsForModelELO getAllFinanceAndFlatFormsForModel(int var1) throws RemoteException;

   AllFinanceXmlFormsAndDataTypesForModelELO getAllFinanceXmlFormsAndDataTypesForModel(int var1) throws RemoteException;
   
   AllXmlFormsELO getAllXmlFormsForModel(int modelId) throws RemoteException;
   
   AllFixedXMLFormsELO getAllFixedXMLForms() throws RemoteException;

   AllDynamicXMLFormsELO getAllDynamicXMLForms() throws RemoteException;

   AllFlatXMLFormsELO getAllFlatXMLForms() throws RemoteException;

   XMLFormDefinitionELO getXMLFormDefinition(int var1) throws RemoteException;

   XMLFormCellPickerInfoELO getXMLFormCellPickerInfo(int var1) throws RemoteException;

   AllXMLFormUserLinkELO getAllXMLFormUserLink() throws RemoteException;

   CheckXMLFormUserLinkELO getCheckXMLFormUserLink(int var1) throws RemoteException;

   AllFormNotesForBudgetLocationELO getAllFormNotesForBudgetLocation(int var1) throws RemoteException;

   AllFormNotesForFormAndBudgetLocationELO getAllFormNotesForFormAndBudgetLocation(int var1, int var2) throws RemoteException;

   AllXmlReportsELO getAllXmlReports() throws RemoteException;

   AllPublicXmlReportsELO getAllPublicXmlReports() throws RemoteException;

   AllXmlReportsForUserELO getAllXmlReportsForUser(int var1) throws RemoteException;

   XmlReportsForFolderELO getXmlReportsForFolder(int var1) throws RemoteException;

   SingleXmlReportELO getSingleXmlReport(int var1, String var2) throws RemoteException;

   AllXmlReportFoldersELO getAllXmlReportFolders() throws RemoteException;

   DecendentFoldersELO getDecendentFolders(int var1) throws RemoteException;

   ReportFolderWithIdELO getReportFolderWithId(int var1) throws RemoteException;

   AllDataEntryProfilesELO getAllDataEntryProfiles() throws RemoteException;

   AllDataEntryProfilesForUserELO getAllDataEntryProfilesForUser(int var1, int var2, int budgetCycleId) throws RemoteException;

   AllUsersForDataEntryProfilesForModelELO getAllUsersForDataEntryProfilesForModel(int var1) throws RemoteException;

   AllDataEntryProfilesForFormELO getAllDataEntryProfilesForForm(int var1) throws RemoteException;

   DefaultDataEntryProfileELO getDefaultDataEntryProfile(int var1, int var2, int var3, int var4) throws RemoteException;

   AllDataEntryProfileHistorysELO getAllDataEntryProfileHistorys() throws RemoteException;

   AllUdefLookupsELO getAllUdefLookups() throws RemoteException;

   AllSecurityRangesELO getAllSecurityRanges() throws RemoteException;

   AllSecurityRangesForModelELO getAllSecurityRangesForModel(int var1) throws RemoteException;

   AllSecurityAccessDefsELO getAllSecurityAccessDefs() throws RemoteException;

   AllSecurityAccessDefsForModelELO getAllSecurityAccessDefsForModel(int var1) throws RemoteException;

   AllAccessDefsUsingRangeELO getAllAccessDefsUsingRange(int var1) throws RemoteException;

   AllSecurityGroupsELO getAllSecurityGroups() throws RemoteException;

   AllSecurityGroupsUsingAccessDefELO getAllSecurityGroupsUsingAccessDef(int var1) throws RemoteException;

   AllSecurityGroupsForUserELO getAllSecurityGroupsForUser(int var1) throws RemoteException;

   AllCcDeploymentsELO getAllCcDeployments() throws RemoteException;

   CcDeploymentsForLookupTableELO getCcDeploymentsForLookupTable(String var1) throws RemoteException;

   CcDeploymentsForXmlFormELO getCcDeploymentsForXmlForm(int var1) throws RemoteException;

   CcDeploymentsForModelELO getCcDeploymentsForModel(int var1) throws RemoteException;

   CcDeploymentCellPickerInfoELO getCcDeploymentCellPickerInfo(int var1) throws RemoteException;

   CcDeploymentXMLFormTypeELO getCcDeploymentXMLFormType(int var1) throws RemoteException;

   AllCellCalcsELO getAllCellCalcs() throws RemoteException;

   CellCalcIntegrityELO getCellCalcIntegrity() throws RemoteException;

   AllCellCalcAssocsELO getAllCellCalcAssocs() throws RemoteException;

   AllChangeMgmtsELO getAllChangeMgmts() throws RemoteException;

   AllChangeMgmtsForModelELO getAllChangeMgmtsForModel(int var1) throws RemoteException;

   AllChangeMgmtsForModelWithXMLELO getAllChangeMgmtsForModelWithXML(int var1) throws RemoteException;

   AllImpExpHdrsELO getAllImpExpHdrs() throws RemoteException;

   AllReportsELO getAllReports() throws RemoteException;

   AllReportsForUserELO getAllReportsForUser(int var1) throws RemoteException;

   AllReportsForAdminELO getAllReportsForAdmin() throws RemoteException;

   WebReportDetailsELO getWebReportDetails(int var1) throws RemoteException;

   AllVirementCategorysELO getAllVirementCategorys() throws RemoteException;

   LocationsForCategoryELO getLocationsForCategory(int var1) throws RemoteException;

   AccountsForCategoryELO getAccountsForCategory(int var1) throws RemoteException;

   AllBudgetLimitsELO getAllBudgetLimits() throws RemoteException;

   AllPerfTestsELO getAllPerfTests() throws RemoteException;

   AllPerfTestRunsELO getAllPerfTestRuns() throws RemoteException;

   AllPerfTestRunResultsELO getAllPerfTestRunResults() throws RemoteException;

   AllMessagesELO getAllMessages() throws RemoteException;

   InBoxForUserELO getInBoxForUser(String var1) throws RemoteException;

   UnreadInBoxForUserELO getUnreadInBoxForUser(String var1) throws RemoteException;

   SentItemsForUserELO getSentItemsForUser(String var1) throws RemoteException;

   MessageForIdELO getMessageForId(long var1, String var3) throws RemoteException;

   MessageForIdSentItemELO getMessageForIdSentItem(long var1, String var3) throws RemoteException;

   MessageCountELO getMessageCount(long var1) throws RemoteException;

   AttatchmentForMessageELO getAttatchmentForMessage(long var1) throws RemoteException;

   MessageFromUserELO getMessageFromUser(long var1) throws RemoteException;

   MessageToUserELO getMessageToUser(long var1) throws RemoteException;

   AllMessagesToUserELO getAllMessagesToUser(long var1) throws RemoteException;

   AllRechargesELO getAllRecharges() throws RemoteException;

   AllRechargesWithModelELO getAllRechargesWithModel(int var1) throws RemoteException;

   SingleRechargeELO getSingleRecharge(int var1) throws RemoteException;

   AllRechargeGroupsELO getAllRechargeGroups() throws RemoteException;

   ActivitiesForCycleandElementELO getActivitiesForCycleandElement(int var1, Integer var2, int var3) throws RemoteException;

   ActivityDetailsELO getActivityDetails(int var1) throws RemoteException;

   ActivityFullDetailsELO getActivityFullDetails(int var1) throws RemoteException;

   AllVirementRequestsELO getAllVirementRequests() throws RemoteException;

   AllVirementRequestGroupsELO getAllVirementRequestGroups() throws RemoteException;

   AllReportTypesELO getAllReportTypes() throws RemoteException;

   AllReportTypeParamsELO getAllReportTypeParams() throws RemoteException;

   AllReportTypeParamsforTypeELO getAllReportTypeParamsforType(int var1) throws RemoteException;

   AllReportDefinitionsELO getAllReportDefinitions() throws RemoteException;

   AllPublicReportByTypeELO getAllPublicReportByType(int var1) throws RemoteException;

   ReportDefinitionForVisIdELO getReportDefinitionForVisId(String var1) throws RemoteException;

   AllReportDefFormcByReportTemplateIdELO getAllReportDefFormcByReportTemplateId(int var1) throws RemoteException;

   AllReportDefFormcByModelIdELO getAllReportDefFormcByModelId(int var1) throws RemoteException;

   CheckFormIsUsedELO getCheckFormIsUsed(int var1) throws RemoteException;

   AllReportDefMappedExcelcByReportTemplateIdELO getAllReportDefMappedExcelcByReportTemplateId(int var1) throws RemoteException;

   AllReportDefMappedExcelcByModelIdELO getAllReportDefMappedExcelcByModelId(int var1) throws RemoteException;

   AllReportDefCalcByCCDeploymentIdELO getAllReportDefCalcByCCDeploymentId(int var1) throws RemoteException;

   AllReportDefCalcByReportTemplateIdELO getAllReportDefCalcByReportTemplateId(int var1) throws RemoteException;

   AllReportDefCalcByModelIdELO getAllReportDefCalcByModelId(int var1) throws RemoteException;

   AllReportDefSummaryCalcByCCDeploymentIdELO getAllReportDefSummaryCalcByCCDeploymentId(int var1) throws RemoteException;

   AllReportDefSummaryCalcByReportTemplateIdELO getAllReportDefSummaryCalcByReportTemplateId(int var1) throws RemoteException;

   AllReportDefSummaryCalcByModelIdELO getAllReportDefSummaryCalcByModelId(int var1) throws RemoteException;

   AllReportTemplatesELO getAllReportTemplates() throws RemoteException;

   AllReportMappingTemplatesELO getAllReportMappingTemplates() throws RemoteException;

   AllExternalDestinationsELO getAllExternalDestinations() throws RemoteException;

   AllExternalDestinationDetailsELO getAllExternalDestinationDetails() throws RemoteException;

   AllUsersForExternalDestinationIdELO getAllUsersForExternalDestinationId(int var1) throws RemoteException;

   AllInternalDestinationsELO getAllInternalDestinations() throws RemoteException;

   AllInternalDestinationDetailsELO getAllInternalDestinationDetails() throws RemoteException;

   AllUsersForInternalDestinationIdELO getAllUsersForInternalDestinationId(int var1) throws RemoteException;

   AllInternalDestinationUsersELO getAllInternalDestinationUsers() throws RemoteException;

   CheckInternalDestinationUsersELO getCheckInternalDestinationUsers(int var1) throws RemoteException;

   AllDistributionsELO getAllDistributions() throws RemoteException;

   DistributionForVisIdELO getDistributionForVisId(String var1) throws RemoteException;

   DistributionDetailsForVisIdELO getDistributionDetailsForVisId(String var1) throws RemoteException;

   CheckInternalDestinationELO getCheckInternalDestination(int var1) throws RemoteException;

   CheckExternalDestinationELO getCheckExternalDestination(int var1) throws RemoteException;

   AllReportPacksELO getAllReportPacks() throws RemoteException;

   ReportDefDistListELO getReportDefDistList(String var1) throws RemoteException;

   CheckReportDefELO getCheckReportDef(int var1) throws RemoteException;

   CheckReportDistributionELO getCheckReportDistribution(int var1) throws RemoteException;

   AllWeightingProfilesELO getAllWeightingProfiles() throws RemoteException;

   AllWeightingDeploymentsELO getAllWeightingDeployments() throws RemoteException;

   AllTidyTasksELO getAllTidyTasks() throws RemoteException;
   
   AllImportTasksELO getAllImportTasks() throws RemoteException;
   
   AllRecalculateBatchTasksELO getAllRecalculateBatchTasks() throws RemoteException;

   OrderedChildrenELO getOrderedChildren(int var1) throws RemoteException;

   AllMappedModelsELO getAllMappedModels() throws RemoteException;
   
   AllGlobalMappedModels2ELO getAllGlobalMappedModels2() throws RemoteException;

   MappedFinanceCubesELO getMappedFinanceCubes(int var1) throws RemoteException;

   AllExtendedAttachmentsELO getAllExtendedAttachments() throws RemoteException;

   ExtendedAttachmentsForIdELO getExtendedAttachmentsForId(int var1) throws RemoteException;

   AllImageExtendedAttachmentsELO getAllImageExtendedAttachments() throws RemoteException;

   AllExternalSystemsELO getAllExternalSystems() throws RemoteException;

   AllGenericExternalSystemsELO getAllGenericExternalSystems() throws RemoteException;

   AllExternalSystemCompainesELO getAllExternalSystemCompaines() throws RemoteException;

   AllAmmModelsELO getAllAmmModels() throws RemoteException;

   AllTaskGroupsELO getAllTaskGroups() throws RemoteException;

   TaskGroupRICheckELO getTaskGroupRICheck(int var1) throws RemoteException;

   AllAuthenticationPolicysELO getAllAuthenticationPolicys() throws RemoteException;

   ActiveAuthenticationPolicysELO getActiveAuthenticationPolicys() throws RemoteException;

   ActiveAuthenticationPolicyForLogonELO getActiveAuthenticationPolicyForLogon() throws RemoteException;

   AllLogonHistorysELO getAllLogonHistorys() throws RemoteException;

   AllPasswordHistorysELO getAllPasswordHistorys() throws RemoteException;

   UserPasswordHistoryELO getUserPasswordHistory(int var1) throws RemoteException;

   AllFormRebuildsELO getAllFormRebuilds() throws RemoteException;

   AllBudgetCyclesInRebuildsELO getAllBudgetCyclesInRebuilds() throws RemoteException;

   AllPackagesForFinanceCubeELO getAllPackagesForFinanceCube(int var1) throws RemoteException;

   AllCubeFormulasELO getAllCubeFormulas() throws RemoteException;

   CubeFormulaeForFinanceCubeELO getCubeFormulaeForFinanceCube(int var1) throws RemoteException;

   EntityRef getEntityRef(Object var1) throws RemoteException;

   EntityList getList(String var1) throws RemoteException;

   EntityList getUserPreferencesForUser(UserRef var1) throws RemoteException;

   BudgetDetailsForUserELO getBudgetDetailsForUser(int var1, int var2) throws RemoteException;

   BudgetDetailsForUserELO getBudgetDetailsForUser(int var1, boolean var2, int var3, int var4) throws RemoteException;

   EntityList getBudgetUserDetails(int var1, int[] var2) throws RemoteException;

   EntityList getBudgetUserDetailsNodeDown(int var1, int var2, int var3) throws RemoteException;

   EntityList getBudgetUserAuthDetailsNodeUp(int var1, int var2, int var3) throws RemoteException;

   StructureElementValuesELO getStructureElementIdFromModel(int var1) throws RemoteException;

   EntityList getPickerStartUpDetails(int var1, int[] var2, int var3) throws RemoteException;

   AllPublicXmlReportFoldersELO getAllPublicXmlReportFolders() throws RemoteException;

   AllXmlReportFoldersForUserELO getAllXmlReportFoldersForUser(int var1) throws RemoteException;

   EntityList getTreeInfoForDimTypeInModel(int var1, int var2) throws RemoteException;

   EntityList getTreeInfoForModel(int var1) throws RemoteException;

   EntityList getTreeInfoForModelDimTypes(int var1, int[] var2) throws RemoteException;

   EntityList getTreeInfoForModelDimSeq(int var1, int[] var2) throws RemoteException;

   EntityList getTreeInfoForModelRA(int var1) throws RemoteException;

   EntityList getCellCalcAccesDefs(int var1) throws RemoteException;

   EntityList doElementPickerSearch(int var1, String var2) throws RemoteException;

   EntityList getAllUserAssignments(String var1, String var2, String var3, String var4) throws RemoteException;

   EntityList getContactLocations(int var1, int var2) throws RemoteException;

   EntityList getModernWelcomeDetails(int var1, int var2) throws RemoteException;

   boolean hasUserAccessToRespArea(int var1, int var2, int var3) throws RemoteException;

   AllFinanceXmlFormsForModelELO getAllFinanceXmlFormsForModelAndUser(int var1, int budgetCycleId, int var2, boolean var3) throws RemoteException;

   UnreadInBoxForUserELO getSummaryUnreadMessagesForUser(String var1) throws RemoteException;

   AllNonDisabledUsersELO getDistinctInternalDestinationUsers(String[] var1) throws RemoteException;

   AllNonDisabledUsersELO getDistinctExternalDestinationUsers(String[] var1) throws RemoteException;

   List getLookupTableData(String var1, List var2) throws RemoteException;

   EntityList getPickerDataTypesWeb(int[] var1, boolean var2) throws RemoteException;

   EntityList getPickerDataTypesWeb(int var1, int[] var2, boolean var3) throws RemoteException;

   AllLogonHistorysReportELO getAllLogonHistorysReport(String var1, Timestamp var2, int var3) throws RemoteException;

   EntityList getAllUserDetailsReport(String var1, String var2, String var3, boolean var4) throws RemoteException;

   EntityList getAllAmmModelsExceptThis(Object var1) throws RemoteException;

   EntityList getAllTaskGroups(Object var1) throws RemoteException;

   AllXmlFormsAndProfilesELO getAllXmlFormsAndProfiles(String var1, String var2, String var3) throws RemoteException;

   List<Integer> getReadOnlyRaAccessPositions(int var1, int var2) throws RemoteException;
   
   EntityList getNodeAndUpUserAssignments(int paramInt1, int paramInt2) throws RemoteException;
   
   UserMessageAttributesForIdELO getUserMessageAttributesForMultipleIds(String[] paramArrayOfString) throws RemoteException;
   
   EntityList getMailDetailForUser(String paramString, int paramInt1, int paramInt2, int paramInt3) throws RemoteException;
   
	AllMasterQuestionsELO getAllMasterQuestions() throws RemoteException;

	QuestionByIDELO getQuestionByID(int paramInt) throws RemoteException;

	AllChallengeQuestionsELO getAllChallengeQuestions() throws RemoteException;

	AllQuestionsAndAnswersByUserIDELO getAllQuestionsAndAnswersByUserID(int paramInt) throws RemoteException;

	AllUserResetLinksELO getAllUserResetLinks() throws RemoteException;

	LinkByUserIDELO getLinkByUserID(int paramInt) throws RemoteException;

	EntityList getModelUserSecurity() throws RemoteException;

	EntityList getUserModelSecurity() throws RemoteException;

	List<UserModelElementAssignment> getRespAreaAccess(PrimaryKey pk) throws RemoteException;

	List<Object[]> getDataEntryProfileForBcAndUser(int bcId, int userId) throws RemoteException;

	String getCPContextId(Object context) throws RemoteException;

	Object getCPContext(String id) throws RemoteException;
	
	void removeContext(Object context) throws RemoteException;
	
	void removeContextByContextId(List<String> contextIds) throws RemoteException;
    
    void removeContextByUserName(List<String> userNames) throws RemoteException;
	
	Map getContextSnapShot() throws RemoteException;
	
	EntityList getAllLoggedInUsers() throws RemoteException;

	EntityList getModelsAndRAHierarchies() throws RemoteException;

	AllHiddenRolesELO getAllHiddenRoles() throws RemoteException;

	AllBudgetCyclesELO getAllBudgetCyclesForUser(int userId) throws RemoteException;

	AllModelsELO getAllModelsForLoggedUser(int userId) throws RemoteException;

    AllModelsELO getAllModelsForGlobalMappedModel(int modelId) throws RemoteException;
    
    Map<String, String> getPropertiesForModelVisId(String modelVisId) throws RemoteException;
    
    Map<String, String> getPropertiesForModelId(int modelId) throws RemoteException;

	AllDimensionsELO getDimensionsForLoggedUser(int userId) throws RemoteException;

	AllHierarchysELO getHierarchiesForLoggedUser(int userId) throws RemoteException;

	AllFinanceCubesELO getAllFinanceCubesForLoggedUser(int userId) throws RemoteException;

	AllCubeFormulasELO getAllCubeFormulasForLoggedUser(int userId) throws RemoteException;

	AllAmmModelsELO getAllAmmModelsForLoggedUser(int userId) throws RemoteException;

	AllGlobalMappedModels2ELO getAllGlobalMappedModelsForLoggedUser(int userId) throws RemoteException;

	AllMappedModelsELO getAllMappedModelsForLoggedUser(int userId) throws RemoteException;

	AllWeightingProfilesELO getAllWeightingProfilesForLoggedUser(int userId) throws RemoteException;

	EntityList getAllWeightingDeploymentsForLoggedUser(int userId) throws RemoteException;

	AllXmlFormsELO getAllXmlFormsForLoggedUser(int userId) throws RemoteException;

	AllFFXmlFormsELO getAllFFXmlFormsForLoggedUser(int userId) throws RemoteException;

	AllXcellXmlFormsELO getAllXcellXmlFormsForLoggedUser(int userId) throws RemoteException;

	AllFormRebuildsELO getAllFormRebuildsForLoggedUser(int userId) throws RemoteException;

	AllMassVirementXmlFormsELO getAllMassVirementXmlFormsForLoggedUser(int userId) throws RemoteException;

	AllUdefLookupsELO getAllUdefLookupsForLoggedUser(int userId) throws RemoteException;

	AllReportDefinitionsELO getAllReportDefinitionsForLoggedUser(int userId) throws RemoteException;

	ArrayList<Object[]> getNotesForCostCenters(ArrayList<Integer> costCenters, int financeCubeId, String fromDate, String toDate) throws RemoteException;

	HashMap<String, ArrayList<HierarchyRef>> getCalendarForModels(HashSet<String> models) throws RemoteException;

	AllQuestionsAndAnswersByUserIDELO getChallengeWord(int userId) throws RemoteException;

	void setChallengeWord(int userId, String word) throws RemoteException;

}
