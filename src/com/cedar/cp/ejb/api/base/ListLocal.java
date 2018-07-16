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
import com.cedar.cp.dto.xmlform.AllXcellXmlFormsELO;
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
import javax.ejb.EJBLocalObject;

public interface ListLocal extends EJBLocalObject {

   AllModelsELO getAllModels() throws EJBException;

   AllModelsWebELO getAllModelsWeb() throws EJBException;

   AllModelsWebForUserELO getAllModelsWebForUser(int var1) throws EJBException;

   AllModelsWithActiveCycleForUserELO getAllModelsWithActiveCycleForUser(int var1) throws EJBException;

   AllBudgetHierarchiesELO getAllBudgetHierarchies() throws EJBException;

   ModelForDimensionELO getModelForDimension(int var1) throws EJBException;

   ModelDimensionsELO getModelDimensions(int var1) throws EJBException;

   ModelDimensionseExcludeCallELO getModelDimensionseExcludeCall(int var1) throws EJBException;

   ModelDetailsWebELO getModelDetailsWeb(int var1) throws EJBException;

   AllRootsForModelELO getAllRootsForModel(int var1) throws EJBException;

   BudgetHierarchyRootNodeForModelELO getBudgetHierarchyRootNodeForModel(int var1) throws EJBException;

   BudgetCyclesToFixStateELO getBudgetCyclesToFixState(int var1) throws EJBException;

   MaxDepthForBudgetHierarchyELO getMaxDepthForBudgetHierarchy(int var1) throws EJBException;

   CalendarSpecForModelELO getCalendarSpecForModel(int var1) throws EJBException;

   HierarchiesForModelELO getHierarchiesForModel(int var1) throws EJBException;

   AllUsersForASecurityGroupELO getAllUsersForASecurityGroup(int var1) throws EJBException;

   AllModelBusinessDimensionsELO getAllModelBusinessDimensions() throws EJBException;

   AllModelBusAndAccDimensionsELO getAllModelBusAndAccDimensions() throws EJBException;

   BudgetDimensionIdForModelELO getBudgetDimensionIdForModel(int var1, int var2) throws EJBException;

   DimensionIdForModelDimTypeELO getDimensionIdForModelDimType(int var1, int var2) throws EJBException;

   AllFinanceCubesELO getAllFinanceCubes() throws EJBException;

   AllSimpleFinanceCubesELO getAllSimpleFinanceCubes(int userId) throws EJBException;

   AllDataTypesAttachedToFinanceCubeForModelELO getAllDataTypesAttachedToFinanceCubeForModel(int var1) throws EJBException;

   FinanceCubesForModelELO getFinanceCubesForModel(int var1) throws EJBException;

   FinanceCubeDimensionsAndHierachiesELO getFinanceCubeDimensionsAndHierachies(int var1) throws EJBException;

   FinanceCubeAllDimensionsAndHierachiesELO getFinanceCubeAllDimensionsAndHierachies(int var1) throws EJBException;

   AllFinanceCubesWebELO getAllFinanceCubesWeb() throws EJBException;

   AllFinanceCubesWebForModelELO getAllFinanceCubesWebForModel(int var1) throws EJBException;

   AllFinanceCubesWebForUserELO getAllFinanceCubesWebForUser(int var1) throws EJBException;

   FinanceCubeDetailsELO getFinanceCubeDetails(int var1) throws EJBException;

   FinanceCubesUsingDataTypeELO getFinanceCubesUsingDataType(short var1) throws EJBException;

   AllFinanceCubeDataTypesELO getAllFinanceCubeDataTypes() throws EJBException;

   ImportableFinanceCubeDataTypesELO getImportableFinanceCubeDataTypes() throws EJBException;

   AllAttachedDataTypesForFinanceCubeELO getAllAttachedDataTypesForFinanceCube(int var1) throws EJBException;

   AllDataTypesForFinanceCubeELO getAllDataTypesForFinanceCube(int var1) throws EJBException;

   AllFinanceCubesForDataTypeELO getAllFinanceCubesForDataType(short var1) throws EJBException;

   AllBudgetCyclesELO getAllBudgetCycles() throws EJBException;

   AllBudgetCyclesWebELO getAllBudgetCyclesWeb() throws EJBException;

   AllBudgetCyclesWebDetailedELO getAllBudgetCyclesWebDetailed() throws EJBException;

   BudgetCyclesForModelELO getBudgetCyclesForModel(int var1) throws EJBException;

   BudgetCyclesForModelWithStateELO getBudgetCyclesForModelWithState(int var1, int var2) throws EJBException;

   BudgetCycleIntegrityELO getBudgetCycleIntegrity() throws EJBException;

   BudgetCycleDetailedForIdELO getBudgetCycleDetailedForId(int var1) throws EJBException;
   
   BudgetCycleDetailedForIdELO getBudgetCycleXmlFormsForId(int var1,int userId) throws EJBException;
   
   BudgetTransferBudgetCyclesELO getBudgetTransferBudgetCycles() throws EJBException;

   CheckIfHasStateELO getCheckIfHasState(int var1, int var2) throws EJBException;

   CycleStateDetailsELO getCycleStateDetails(int var1) throws EJBException;

   AllResponsibilityAreasELO getAllResponsibilityAreas() throws EJBException;

   AllBudgetUsersELO getAllBudgetUsers() throws EJBException;

   CheckUserAccessToModelELO getCheckUserAccessToModel(int var1, int var2) throws EJBException;

   CheckUserAccessELO getCheckUserAccess(int var1, int var2) throws EJBException;

   CheckUserELO getCheckUser(int var1) throws EJBException;

   BudgetUsersForNodeELO getBudgetUsersForNode(int var1, int var2) throws EJBException;

   NodesForUserAndCycleELO getNodesForUserAndCycle(int var1, int var2) throws EJBException;

   NodesForUserAndModelELO getNodesForUserAndModel(int var1, int var2) throws EJBException;

   UsersForModelAndElementELO getUsersForModelAndElement(int var1, int var2) throws EJBException;

   AllDataTypesELO getAllDataTypes() throws EJBException;

   AllDataTypesWebELO getAllDataTypesWeb() throws EJBException;

   AllDataTypeForFinanceCubeELO getAllDataTypeForFinanceCube(int var1) throws EJBException;

   AllDataTypesForModelELO getAllDataTypesForModel(int var1) throws EJBException;

   DataTypesByTypeELO getDataTypesByType(int var1) throws EJBException;

   DataTypesByTypeWriteableELO getDataTypesByTypeWriteable(int var1) throws EJBException;

   DataTypeDependenciesELO getDataTypeDependencies(short var1) throws EJBException;

   DataTypesForImpExpELO getDataTypesForImpExp() throws EJBException;

   DataTypeDetailsForVisIDELO getDataTypeDetailsForVisID(String var1) throws EJBException;

   AllCurrencysELO getAllCurrencys() throws EJBException;

   AllStructureElementsELO getAllStructureElements(int var1) throws EJBException;

   AllLeafStructureElementsELO getAllLeafStructureElements(int var1) throws EJBException;

   LeafPlannableBudgetLocationsForModelELO getLeafPlannableBudgetLocationsForModel(int var1) throws EJBException;

   StructureElementParentsELO getStructureElementParents(int var1, int var2) throws EJBException;

   StructureElementParentsReversedELO getStructureElementParentsReversed(int var1, int var2) throws EJBException;

   StructureElementParentsFromVisIdELO getStructureElementParentsFromVisId(String var1, int var2) throws EJBException;

   ImmediateChildrenELO getImmediateChildren(int var1, int var2) throws EJBException;

   MassStateImmediateChildrenELO getMassStateImmediateChildren(int var1, int var2) throws EJBException;

   StructureElementValuesELO getStructureElementValues(int var1, int var2) throws EJBException;

   CheckLeafELO getCheckLeaf(int var1) throws EJBException;

   StructureElementELO getStructureElement(int var1) throws EJBException;

   StructureElementForIdsELO getStructureElementForIds(int var1, int var2) throws EJBException;

   StructureElementByVisIdELO getStructureElementByVisId(String var1, int var2) throws EJBException;

   RespAreaImmediateChildrenELO getRespAreaImmediateChildren(int var1, int var2) throws EJBException;

   AllDisabledLeafELO getAllDisabledLeaf(int var1) throws EJBException;

   AllNotPlannableELO getAllNotPlannable(int var1) throws EJBException;

   AllDisabledLeafandNotPlannableELO getAllDisabledLeafandNotPlannable(int var1) throws EJBException;

   LeavesForParentELO getLeavesForParent(int var1, int var2, int var3, int var4, int var5) throws EJBException;

   ChildrenForParentELO getChildrenForParent(int var1, int var2, int var3, int var4, int var5) throws EJBException;

   ReportLeavesForParentELO getReportLeavesForParent(int var1, int var2, int var3, int var4, int var5) throws EJBException;

   ReportChildrenForParentToRelativeDepthELO getReportChildrenForParentToRelativeDepth(int var1, int var2, int var3, int var4, int var5, int var6, int var7, int var8) throws EJBException;

   BudgetHierarchyElementELO getBudgetHierarchyElement(int var1) throws EJBException;

   BudgetLocationElementForModelELO getBudgetLocationElementForModel(int var1, int var2) throws EJBException;

   AllDimensionsELO getAllDimensions() throws EJBException;

   AvailableDimensionsELO getAvailableDimensions() throws EJBException;

   ImportableDimensionsELO getImportableDimensions() throws EJBException;

   AllDimensionsForModelELO getAllDimensionsForModel(int var1) throws EJBException;

   AllDimensionElementsELO getAllDimensionElements() throws EJBException;

   AllDimensionElementsForDimensionELO getAllDimensionElementsForDimension(int var1) throws EJBException;
   
   AllDimensionElementsForModelELO getAllDimensionElementsForModels(List<Integer> modelIds) throws EJBException;

   AllHierarchysELO getAllHierarchys() throws EJBException;
   
   SelectedHierarchysELO getSelectedHierarchys() throws EJBException;

   ImportableHierarchiesELO getImportableHierarchies(int var1) throws EJBException;

   HierarcyDetailsFromDimIdELO getHierarcyDetailsFromDimId(int var1) throws EJBException;

   HierarcyDetailsIncRootNodeFromDimIdELO getHierarcyDetailsIncRootNodeFromDimId(int var1) throws EJBException;

   CalendarForModelELO getCalendarForModel(int var1) throws EJBException;

   CalendarForModelVisIdELO getCalendarForModelVisId(String var1) throws EJBException;

   CalendarForFinanceCubeELO getCalendarForFinanceCube(int var1) throws EJBException;

   HierarachyElementELO getHierarachyElement(int var1) throws EJBException;

   AugHierarachyElementELO getAugHierarachyElement(int var1) throws EJBException;

   AllFeedsForDimensionElementELO getAllFeedsForDimensionElement(int var1) throws EJBException;

   AllUsersELO getAllUsers() throws EJBException;
   
   AllRevisionsELO getAllRevisions() throws EJBException;
   
   AllDashboardsForUserELO getDashboardForms(Integer userId, boolean isAdmin) throws EJBException;

   SecurityStringsForUserELO getSecurityStringsForUser(int var1) throws EJBException;

   AllUsersExportELO getAllUsersExport() throws EJBException;

   AllUserAttributesELO getAllUserAttributes() throws EJBException;

   AllNonDisabledUsersELO getAllNonDisabledUsers() throws EJBException;

   UserMessageAttributesELO getUserMessageAttributes() throws EJBException;

   UserMessageAttributesForIdELO getUserMessageAttributesForId(int var1) throws EJBException;

   UserMessageAttributesForNameELO getUserMessageAttributesForName(String var1) throws EJBException;

   FinanceSystemUserNameELO getFinanceSystemUserName(int var1) throws EJBException;

   UsersWithSecurityStringELO getUsersWithSecurityString(String var1) throws EJBException;

   AllRolesForUsersELO getAllRolesForUsers() throws EJBException;

   AllRolesELO getAllRoles() throws EJBException;

   AllRolesForUserELO getAllRolesForUser(int var1) throws EJBException;
   
   AllRolesForUserELO getAllHiddenRolesForUser(int var1) throws EJBException;

   AllSecurityRolesELO getAllSecurityRoles() throws EJBException;

   AllSecurityRolesForRoleELO getAllSecurityRolesForRole(int var1) throws EJBException;

   UserPreferencesForUserELO getUserPreferencesForUser(int var1) throws EJBException;

   AllBudgetInstructionsELO getAllBudgetInstructions() throws EJBException;

   AllBudgetInstructionsWebELO getAllBudgetInstructionsWeb() throws EJBException;

   AllBudgetInstructionsForModelELO getAllBudgetInstructionsForModel(int var1) throws EJBException;

   AllBudgetInstructionsForCycleELO getAllBudgetInstructionsForCycle(int var1) throws EJBException;

   AllBudgetInstructionsForLocationELO getAllBudgetInstructionsForLocation(int var1) throws EJBException;

   AllBudgetInstructionAssignmentsELO getAllBudgetInstructionAssignments() throws EJBException;

   AllWebTasksELO getAllWebTasks() throws EJBException;

   AllWebTasksForUserELO getAllWebTasksForUser(String var1) throws EJBException;

   WebTasksDetailsELO getWebTasksDetails(int var1) throws EJBException;

   AllSystemPropertysELO getAllSystemPropertys() throws EJBException;

   AllSystemPropertysUncachedELO getAllSystemPropertysUncached() throws EJBException;

   AllMailPropsELO getAllMailProps() throws EJBException;

   SystemPropertyELO getSystemProperty(String var1) throws EJBException;

   WebSystemPropertyELO getWebSystemProperty(String var1) throws EJBException;

   AllXmlFormsELO getAllXmlForms() throws EJBException;

   AllFinXmlFormsELO getAllFinXmlForms(int userId) throws EJBException;

   AllFFXmlFormsELO getAllFFXmlForms() throws EJBException;

   AllXcellXmlFormsELO getAllXcellXmlForms() throws EJBException;
   
   AllXmlFormsELO getXcellXmlFormsForUser(int userId) throws EJBException;
   
   AllMassVirementXmlFormsELO getAllMassVirementXmlForms() throws EJBException;

   AllFinanceXmlFormsELO getAllFinanceXmlForms() throws EJBException;

   AllFinanceAndFlatFormsELO getAllFinanceAndFlatForms() throws EJBException;

   AllFinanceXmlFormsForModelELO getAllFinanceXmlFormsForModel(int var1) throws EJBException;

   AllFinanceAndFlatFormsForModelELO getAllFinanceAndFlatFormsForModel(int var1) throws EJBException;

   AllFinanceXmlFormsAndDataTypesForModelELO getAllFinanceXmlFormsAndDataTypesForModel(int var1) throws EJBException;
   
   AllXmlFormsELO getAllXmlFormsForModel(int modelId) throws EJBException;

   AllFixedXMLFormsELO getAllFixedXMLForms() throws EJBException;

   AllDynamicXMLFormsELO getAllDynamicXMLForms() throws EJBException;

   AllFlatXMLFormsELO getAllFlatXMLForms() throws EJBException;

   XMLFormDefinitionELO getXMLFormDefinition(int var1) throws EJBException;

   XMLFormCellPickerInfoELO getXMLFormCellPickerInfo(int var1) throws EJBException;

   AllXMLFormUserLinkELO getAllXMLFormUserLink() throws EJBException;

   CheckXMLFormUserLinkELO getCheckXMLFormUserLink(int var1) throws EJBException;

   AllFormNotesForBudgetLocationELO getAllFormNotesForBudgetLocation(int var1) throws EJBException;

   AllFormNotesForFormAndBudgetLocationELO getAllFormNotesForFormAndBudgetLocation(int var1, int var2) throws EJBException;

   AllXmlReportsELO getAllXmlReports() throws EJBException;

   AllPublicXmlReportsELO getAllPublicXmlReports() throws EJBException;

   AllXmlReportsForUserELO getAllXmlReportsForUser(int var1) throws EJBException;

   XmlReportsForFolderELO getXmlReportsForFolder(int var1) throws EJBException;

   SingleXmlReportELO getSingleXmlReport(int var1, String var2) throws EJBException;

   AllXmlReportFoldersELO getAllXmlReportFolders() throws EJBException;

   DecendentFoldersELO getDecendentFolders(int var1) throws EJBException;

   ReportFolderWithIdELO getReportFolderWithId(int var1) throws EJBException;

   AllDataEntryProfilesELO getAllDataEntryProfiles() throws EJBException;

   AllDataEntryProfilesForUserELO getAllDataEntryProfilesForUser(int var1, int var2, int budgetCycleId) throws EJBException;

   AllUsersForDataEntryProfilesForModelELO getAllUsersForDataEntryProfilesForModel(int var1) throws EJBException;

   AllDataEntryProfilesForFormELO getAllDataEntryProfilesForForm(int var1) throws EJBException;

   DefaultDataEntryProfileELO getDefaultDataEntryProfile(int var1, int var2, int var3, int var4) throws EJBException;

   AllDataEntryProfileHistorysELO getAllDataEntryProfileHistorys() throws EJBException;

   AllUdefLookupsELO getAllUdefLookups() throws EJBException;

   AllSecurityRangesELO getAllSecurityRanges() throws EJBException;

   AllSecurityRangesForModelELO getAllSecurityRangesForModel(int var1) throws EJBException;

   AllSecurityAccessDefsELO getAllSecurityAccessDefs() throws EJBException;

   AllSecurityAccessDefsForModelELO getAllSecurityAccessDefsForModel(int var1) throws EJBException;

   AllAccessDefsUsingRangeELO getAllAccessDefsUsingRange(int var1) throws EJBException;

   AllSecurityGroupsELO getAllSecurityGroups() throws EJBException;

   AllSecurityGroupsUsingAccessDefELO getAllSecurityGroupsUsingAccessDef(int var1) throws EJBException;

   AllSecurityGroupsForUserELO getAllSecurityGroupsForUser(int var1) throws EJBException;

   AllCcDeploymentsELO getAllCcDeployments() throws EJBException;

   CcDeploymentsForLookupTableELO getCcDeploymentsForLookupTable(String var1) throws EJBException;

   CcDeploymentsForXmlFormELO getCcDeploymentsForXmlForm(int var1) throws EJBException;

   CcDeploymentsForModelELO getCcDeploymentsForModel(int var1) throws EJBException;

   CcDeploymentCellPickerInfoELO getCcDeploymentCellPickerInfo(int var1) throws EJBException;

   CcDeploymentXMLFormTypeELO getCcDeploymentXMLFormType(int var1) throws EJBException;

   AllCellCalcsELO getAllCellCalcs() throws EJBException;

   CellCalcIntegrityELO getCellCalcIntegrity() throws EJBException;

   AllCellCalcAssocsELO getAllCellCalcAssocs() throws EJBException;

   AllChangeMgmtsELO getAllChangeMgmts() throws EJBException;

   AllChangeMgmtsForModelELO getAllChangeMgmtsForModel(int var1) throws EJBException;

   AllChangeMgmtsForModelWithXMLELO getAllChangeMgmtsForModelWithXML(int var1) throws EJBException;

   AllImpExpHdrsELO getAllImpExpHdrs() throws EJBException;

   AllReportsELO getAllReports() throws EJBException;

   AllReportsForUserELO getAllReportsForUser(int var1) throws EJBException;

   AllReportsForAdminELO getAllReportsForAdmin() throws EJBException;

   WebReportDetailsELO getWebReportDetails(int var1) throws EJBException;

   AllVirementCategorysELO getAllVirementCategorys() throws EJBException;

   LocationsForCategoryELO getLocationsForCategory(int var1) throws EJBException;

   AccountsForCategoryELO getAccountsForCategory(int var1) throws EJBException;

   AllBudgetLimitsELO getAllBudgetLimits() throws EJBException;

   AllPerfTestsELO getAllPerfTests() throws EJBException;

   AllPerfTestRunsELO getAllPerfTestRuns() throws EJBException;

   AllPerfTestRunResultsELO getAllPerfTestRunResults() throws EJBException;

   AllMessagesELO getAllMessages() throws EJBException;

   InBoxForUserELO getInBoxForUser(String var1) throws EJBException;

   UnreadInBoxForUserELO getUnreadInBoxForUser(String var1) throws EJBException;

   SentItemsForUserELO getSentItemsForUser(String var1) throws EJBException;

   MessageForIdELO getMessageForId(long var1, String var3) throws EJBException;

   MessageForIdSentItemELO getMessageForIdSentItem(long var1, String var3) throws EJBException;

   MessageCountELO getMessageCount(long var1) throws EJBException;

   AttatchmentForMessageELO getAttatchmentForMessage(long var1) throws EJBException;

   MessageFromUserELO getMessageFromUser(long var1) throws EJBException;

   MessageToUserELO getMessageToUser(long var1) throws EJBException;

   AllMessagesToUserELO getAllMessagesToUser(long var1) throws EJBException;

   AllRechargesELO getAllRecharges() throws EJBException;

   AllRechargesWithModelELO getAllRechargesWithModel(int var1) throws EJBException;

   SingleRechargeELO getSingleRecharge(int var1) throws EJBException;

   AllRechargeGroupsELO getAllRechargeGroups() throws EJBException;

   ActivitiesForCycleandElementELO getActivitiesForCycleandElement(int var1, Integer var2, int var3) throws EJBException;

   ActivityDetailsELO getActivityDetails(int var1) throws EJBException;

   ActivityFullDetailsELO getActivityFullDetails(int var1) throws EJBException;

   AllVirementRequestsELO getAllVirementRequests() throws EJBException;

   AllVirementRequestGroupsELO getAllVirementRequestGroups() throws EJBException;

   AllReportTypesELO getAllReportTypes() throws EJBException;

   AllReportTypeParamsELO getAllReportTypeParams() throws EJBException;

   AllReportTypeParamsforTypeELO getAllReportTypeParamsforType(int var1) throws EJBException;

   AllReportDefinitionsELO getAllReportDefinitions() throws EJBException;

   AllPublicReportByTypeELO getAllPublicReportByType(int var1) throws EJBException;

   ReportDefinitionForVisIdELO getReportDefinitionForVisId(String var1) throws EJBException;

   AllReportDefFormcByReportTemplateIdELO getAllReportDefFormcByReportTemplateId(int var1) throws EJBException;

   AllReportDefFormcByModelIdELO getAllReportDefFormcByModelId(int var1) throws EJBException;

   CheckFormIsUsedELO getCheckFormIsUsed(int var1) throws EJBException;

   AllReportDefMappedExcelcByReportTemplateIdELO getAllReportDefMappedExcelcByReportTemplateId(int var1) throws EJBException;

   AllReportDefMappedExcelcByModelIdELO getAllReportDefMappedExcelcByModelId(int var1) throws EJBException;

   AllReportDefCalcByCCDeploymentIdELO getAllReportDefCalcByCCDeploymentId(int var1) throws EJBException;

   AllReportDefCalcByReportTemplateIdELO getAllReportDefCalcByReportTemplateId(int var1) throws EJBException;

   AllReportDefCalcByModelIdELO getAllReportDefCalcByModelId(int var1) throws EJBException;

   AllReportDefSummaryCalcByCCDeploymentIdELO getAllReportDefSummaryCalcByCCDeploymentId(int var1) throws EJBException;

   AllReportDefSummaryCalcByReportTemplateIdELO getAllReportDefSummaryCalcByReportTemplateId(int var1) throws EJBException;

   AllReportDefSummaryCalcByModelIdELO getAllReportDefSummaryCalcByModelId(int var1) throws EJBException;

   AllReportTemplatesELO getAllReportTemplates() throws EJBException;

   AllReportMappingTemplatesELO getAllReportMappingTemplates() throws EJBException;

   AllExternalDestinationsELO getAllExternalDestinations() throws EJBException;

   AllExternalDestinationDetailsELO getAllExternalDestinationDetails() throws EJBException;

   AllUsersForExternalDestinationIdELO getAllUsersForExternalDestinationId(int var1) throws EJBException;

   AllInternalDestinationsELO getAllInternalDestinations() throws EJBException;

   AllInternalDestinationDetailsELO getAllInternalDestinationDetails() throws EJBException;

   AllUsersForInternalDestinationIdELO getAllUsersForInternalDestinationId(int var1) throws EJBException;

   AllInternalDestinationUsersELO getAllInternalDestinationUsers() throws EJBException;

   CheckInternalDestinationUsersELO getCheckInternalDestinationUsers(int var1) throws EJBException;

   AllDistributionsELO getAllDistributions() throws EJBException;

   DistributionForVisIdELO getDistributionForVisId(String var1) throws EJBException;

   DistributionDetailsForVisIdELO getDistributionDetailsForVisId(String var1) throws EJBException;

   CheckInternalDestinationELO getCheckInternalDestination(int var1) throws EJBException;

   CheckExternalDestinationELO getCheckExternalDestination(int var1) throws EJBException;

   AllReportPacksELO getAllReportPacks() throws EJBException;

   ReportDefDistListELO getReportDefDistList(String var1) throws EJBException;

   CheckReportDefELO getCheckReportDef(int var1) throws EJBException;

   CheckReportDistributionELO getCheckReportDistribution(int var1) throws EJBException;

   AllWeightingProfilesELO getAllWeightingProfiles() throws EJBException;

   AllWeightingDeploymentsELO getAllWeightingDeployments() throws EJBException;

   AllTidyTasksELO getAllTidyTasks() throws EJBException;
   
   AllImportTasksELO getAllImportTasks() throws EJBException;
   
   AllRecalculateBatchTasksELO getAllRecalculateBatchTasks() throws EJBException;

   OrderedChildrenELO getOrderedChildren(int var1) throws EJBException;

   AllMappedModelsELO getAllMappedModels() throws EJBException;
   
   AllGlobalMappedModels2ELO getAllGlobalMappedModels2() throws EJBException;
   
   MappedFinanceCubesELO getMappedFinanceCubes(int var1) throws EJBException;

   AllExtendedAttachmentsELO getAllExtendedAttachments() throws EJBException;

   ExtendedAttachmentsForIdELO getExtendedAttachmentsForId(int var1) throws EJBException;

   AllImageExtendedAttachmentsELO getAllImageExtendedAttachments() throws EJBException;

   AllExternalSystemsELO getAllExternalSystems() throws EJBException;

   AllGenericExternalSystemsELO getAllGenericExternalSystems() throws EJBException;

   AllExternalSystemCompainesELO getAllExternalSystemCompaines() throws EJBException;

   AllAmmModelsELO getAllAmmModels() throws EJBException;

   AllTaskGroupsELO getAllTaskGroups() throws EJBException;

   TaskGroupRICheckELO getTaskGroupRICheck(int var1) throws EJBException;

   AllAuthenticationPolicysELO getAllAuthenticationPolicys() throws EJBException;

   ActiveAuthenticationPolicysELO getActiveAuthenticationPolicys() throws EJBException;

   ActiveAuthenticationPolicyForLogonELO getActiveAuthenticationPolicyForLogon() throws EJBException;

   AllLogonHistorysELO getAllLogonHistorys() throws EJBException;

   AllPasswordHistorysELO getAllPasswordHistorys() throws EJBException;

   UserPasswordHistoryELO getUserPasswordHistory(int var1) throws EJBException;

   AllFormRebuildsELO getAllFormRebuilds() throws EJBException;

   AllBudgetCyclesInRebuildsELO getAllBudgetCyclesInRebuilds() throws EJBException;

   AllPackagesForFinanceCubeELO getAllPackagesForFinanceCube(int var1) throws EJBException;

   AllCubeFormulasELO getAllCubeFormulas() throws EJBException;

   CubeFormulaeForFinanceCubeELO getCubeFormulaeForFinanceCube(int var1) throws EJBException;

   EntityRef getEntityRef(Object var1) throws EJBException;

   EntityList getList(String var1) throws EJBException;

   EntityList getUserPreferencesForUser(UserRef var1) throws EJBException;

   BudgetDetailsForUserELO getBudgetDetailsForUser(int var1, int var2) throws EJBException;

   BudgetDetailsForUserELO getBudgetDetailsForUser(int var1, boolean var2, int var3, int var4) throws EJBException;

   EntityList getBudgetUserDetails(int var1, int[] var2) throws EJBException;

   EntityList getBudgetUserDetailsNodeDown(int var1, int var2, int var3) throws EJBException;

   EntityList getBudgetUserAuthDetailsNodeUp(int var1, int var2, int var3) throws EJBException;

   StructureElementValuesELO getStructureElementIdFromModel(int var1) throws EJBException;

   EntityList getPickerStartUpDetails(int var1, int[] var2, int var3) throws EJBException;

   AllPublicXmlReportFoldersELO getAllPublicXmlReportFolders() throws EJBException;

   AllXmlReportFoldersForUserELO getAllXmlReportFoldersForUser(int var1) throws EJBException;

   EntityList getTreeInfoForDimTypeInModel(int var1, int var2) throws EJBException;

   EntityList getTreeInfoForModel(int var1) throws EJBException;

   EntityList getTreeInfoForModelDimTypes(int var1, int[] var2) throws EJBException;

   EntityList getTreeInfoForModelDimSeq(int var1, int[] var2) throws EJBException;

   EntityList getTreeInfoForModelRA(int var1) throws EJBException;

   EntityList getCellCalcAccesDefs(int var1) throws EJBException;

   EntityList doElementPickerSearch(int var1, String var2) throws EJBException;

   EntityList getAllUserAssignments(String var1, String var2, String var3, String var4) throws EJBException;

   EntityList getContactLocations(int var1, int var2) throws EJBException;

   EntityList getModernWelcomeDetails(int var1, int var2) throws EJBException;

   boolean hasUserAccessToRespArea(int var1, int var2, int var3) throws EJBException;

   AllFinanceXmlFormsForModelELO getAllFinanceXmlFormsForModelAndUser(int var1, int budgetCycleId, int var2, boolean var3) throws EJBException;

   UnreadInBoxForUserELO getSummaryUnreadMessagesForUser(String var1) throws EJBException;

   AllNonDisabledUsersELO getDistinctInternalDestinationUsers(String[] var1) throws EJBException;

   AllNonDisabledUsersELO getDistinctExternalDestinationUsers(String[] var1) throws EJBException;

   List getLookupTableData(String var1, List var2) throws EJBException;

   EntityList getPickerDataTypesWeb(int[] var1, boolean var2) throws EJBException;

   EntityList getPickerDataTypesWeb(int var1, int[] var2, boolean var3) throws EJBException;

   EntityList getAllUserDetailsReport(String var1, String var2, String var3, boolean var4) throws EJBException;

   AllLogonHistorysReportELO getAllLogonHistorysReport(String var1, Timestamp var2, int var3) throws EJBException;

   EntityList getAllAmmModelsExceptThis(Object var1) throws EJBException;

   EntityList getAllTaskGroups(Object var1) throws EJBException;

   AllXmlFormsAndProfilesELO getAllXmlFormsAndProfiles(String var1, String var2, String var3) throws EJBException;

   List<Integer> getReadOnlyRaAccessPositions(int var1, int var2) throws EJBException;
   
   EntityList getNodeAndUpUserAssignments(int paramInt1, int paramInt2) throws EJBException;
   
   UserMessageAttributesForIdELO getUserMessageAttributesForMultipleIds(String[] paramArrayOfString) throws EJBException;
   
   EntityList getMailDetailForUser(String paramString, int paramInt1, int paramInt2, int paramInt3) throws EJBException;
   
	AllMasterQuestionsELO getAllMasterQuestions() throws EJBException;

	QuestionByIDELO getQuestionByID(int paramInt) throws EJBException;

	AllChallengeQuestionsELO getAllChallengeQuestions() throws EJBException;

	AllQuestionsAndAnswersByUserIDELO getAllQuestionsAndAnswersByUserID(int paramInt) throws EJBException;

	AllUserResetLinksELO getAllUserResetLinks() throws EJBException;

	LinkByUserIDELO getLinkByUserID(int paramInt) throws EJBException;

	EntityList getModelUserSecurity() throws EJBException;

	EntityList getUserModelSecurity() throws EJBException;

	List<UserModelElementAssignment> getRespAreaAccess(PrimaryKey pk) throws EJBException;

	List<Object[]> getDataEntryProfileForBcAndUser(int bcId, int userId) throws EJBException;

	String getCPContextId(Object context) throws EJBException;

	Object getCPContext(String id) throws EJBException;

	void removeContext(Object context) throws EJBException;
    
    void removeContextByContextId(List<String> contextIds) throws EJBException;
    
    void removeContextByUserName(List<String> userNames) throws EJBException;
	
	Map getContextSnapShot() throws EJBException;
	
	EntityList getAllLoggedInUsers() throws EJBException;

	EntityList getModelsAndRAHierarchies() throws EJBException;

	AllHiddenRolesELO getAllHiddenRoles() throws EJBException;

	AllBudgetCyclesELO getAllBudgetCyclesForUser(int userId) throws EJBException;

	AllModelsELO getAllModelsForLoggedUser(int userId) throws EJBException;

    AllModelsELO getAllModelsForGlobalMappedModel(int modelId) throws EJBException;
    
    Map<String, String> getPropertiesForModelVisId(String modelVisId) throws EJBException;
    
    Map<String, String> getPropertiesForModelId(int modelId) throws EJBException;

	AllDimensionsELO getDimensionsForLoggedUser(int userId) throws EJBException;

	AllHierarchysELO getHierarchiesForLoggedUser(int userId) throws EJBException;

	AllFinanceCubesELO getAllFinanceCubesForLoggedUser(int userId) throws EJBException;

	AllCubeFormulasELO getAllCubeFormulasForLoggedUser(int userId) throws EJBException;

	AllAmmModelsELO getAllAmmModelsForLoggedUser(int userId) throws EJBException;

	AllGlobalMappedModels2ELO getAllGlobalMappedModelsForLoggedUser(int userId) throws EJBException;

	AllMappedModelsELO getAllMappedModelsForLoggedUser(int userId) throws EJBException;

	AllWeightingProfilesELO getAllWeightingProfilesForLoggedUser(int userId) throws EJBException;

	EntityList getAllWeightingDeploymentsForLoggedUser(int userId) throws EJBException;

	AllXmlFormsELO getAllXmlFormsForLoggedUser(int userId) throws EJBException;

	AllFFXmlFormsELO getAllFFXmlFormsForLoggedUser(int userId) throws EJBException;

	AllXcellXmlFormsELO getAllXcellXmlFormsForLoggedUser(int userId) throws EJBException;

	AllFormRebuildsELO getAllFormRebuildsForLoggedUser(int userId) throws EJBException;

	AllMassVirementXmlFormsELO getAllMassVirementXmlFormsForLoggedUser(int userId) throws EJBException;

	AllUdefLookupsELO getAllUdefLookupsForLoggedUser(int userId) throws EJBException;

	AllReportDefinitionsELO getAllReportDefinitionsForLoggedUser(int userId) throws EJBException;

	ArrayList<Object[]> getNotesForCostCenters(ArrayList<Integer> costCenters, int financeCubeId, String fromDate, String toDate) throws EJBException;

	HashMap<String, ArrayList<HierarchyRef>> getCalendarForModels(HashSet<String> models) throws EJBException;

	AllQuestionsAndAnswersByUserIDELO getChallengeWord(int userId) throws EJBException;

	void setChallengeWord(int userId, String word) throws EJBException;
	
}
