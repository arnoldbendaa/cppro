// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:09
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.impl.base;

import com.cedar.cp.api.admin.tidytask.TidyTaskRef;
import com.cedar.cp.api.authenticationpolicy.AuthenticationPolicyRef;
import com.cedar.cp.api.base.CPConnection;
import com.cedar.cp.api.base.EntityList;
import com.cedar.cp.api.base.EntityRef;
import com.cedar.cp.api.base.ListHelper;
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
import com.cedar.cp.api.model.mapping.MappedModelRef;
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
import com.cedar.cp.api.user.DataEntryProfilesProcess;
import com.cedar.cp.api.user.UserRef;
import com.cedar.cp.api.xmlform.XmlFormRef;
import com.cedar.cp.api.xmlform.rebuild.FormRebuildRef;
import com.cedar.cp.api.xmlreport.XmlReportRef;
import com.cedar.cp.api.xmlreportfolder.XmlReportFolderRef;
import com.cedar.cp.dto.recalculate.AllRecalculateBatchTasksELO;
import com.cedar.cp.dto.admin.tidytask.AllTidyTasksELO;
import com.cedar.cp.dto.admin.tidytask.OrderedChildrenELO;
import com.cedar.cp.dto.authenticationpolicy.ActiveAuthenticationPolicyForLogonELO;
import com.cedar.cp.dto.authenticationpolicy.ActiveAuthenticationPolicysELO;
import com.cedar.cp.dto.authenticationpolicy.AllAuthenticationPolicysELO;
import com.cedar.cp.dto.base.CompositeKey;
import com.cedar.cp.dto.base.EntityRefImpl;
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
import com.cedar.cp.dto.dimension.DimensionPK;
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
import com.cedar.cp.dto.dimension.StructureElementPK;
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
import com.cedar.cp.dto.user.UserPK;
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
import com.cedar.cp.ejb.api.base.ListSessionServer;
import com.cedar.cp.ejb.api.task.TaskProcessServer;
import com.cedar.cp.util.Log;
import com.cedar.cp.util.Timer;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

public class ListHelperImpl implements ListHelper {

	private CPConnection mConnection;
	private Log mLog = new Log(this.getClass());

	public ListHelperImpl(CPConnection conn_) {
		this.mConnection = conn_;
	}

	private CPConnection getConnection() {
		return this.mConnection;
	}

	public EntityList getAllModels() {
		StringBuffer key = new StringBuffer();
		key.append("AllModels");
		AllModelsELO coll = (AllModelsELO) this.mConnection.getClientCache().get(key.toString());
		if (coll == null) {
			Timer timer = this.mLog.isDebugEnabled() ? new Timer(this.mLog) : null;

			try {
				coll = (new ListSessionServer(this.getConnection())).getAllModels();
			} catch (Exception var5) {
				throw new RuntimeException("unable to get Model AllModels", var5);
			}

			this.mConnection.getClientCache().put(key.toString(), coll);
			if (timer != null) {
				timer.logDebug("getAllModels", "");
			}
		} else {
			this.mLog.debug("getAllModels", "obtained " + key + " from cache");
		}

		return coll;
	}

	public EntityList getAllModelsWeb() {
		StringBuffer key = new StringBuffer();
		key.append("AllModelsWeb");
		AllModelsWebELO coll = (AllModelsWebELO) this.mConnection.getClientCache().get(key.toString());
		if (coll == null) {
			Timer timer = this.mLog.isDebugEnabled() ? new Timer(this.mLog) : null;

			try {
				coll = (new ListSessionServer(this.getConnection())).getAllModelsWeb();
			} catch (Exception var5) {
				throw new RuntimeException("unable to get Model AllModelsWeb", var5);
			}

			this.mConnection.getClientCache().put(key.toString(), coll);
			if (timer != null) {
				timer.logDebug("getAllModelsWeb", "");
			}
		} else {
			this.mLog.debug("getAllModelsWeb", "obtained " + key + " from cache");
		}

		return coll;
	}

	public EntityList getAllModelsWebForUser(int param1) {
		StringBuffer key = new StringBuffer();
		key.append("AllModelsWebForUser");
		key.append('.');
		key.append(String.valueOf(param1));
		AllModelsWebForUserELO coll = (AllModelsWebForUserELO) this.mConnection.getClientCache().get(key.toString());
		if (coll == null) {
			Timer timer = this.mLog.isDebugEnabled() ? new Timer(this.mLog) : null;

			try {
				coll = (new ListSessionServer(this.getConnection())).getAllModelsWebForUser(param1);
			} catch (Exception var6) {
				throw new RuntimeException("unable to get Model AllModelsWebForUser", var6);
			}

			this.mConnection.getClientCache().put(key.toString(), coll);
			if (timer != null) {
				timer.logDebug("getAllModelsWebForUser", "");
			}
		} else {
			this.mLog.debug("getAllModelsWebForUser", "obtained " + key + " from cache");
		}

		return coll;
	}

	public EntityList getAllModelsForLoggedUser() {
		StringBuffer key = new StringBuffer();
		key.append("getAllModelsForLoggedUser");
		key.append('.');
		AllModelsELO coll = (AllModelsELO) this.mConnection.getClientCache().get(key.toString());
		if (coll == null) {
			Timer timer = this.mLog.isDebugEnabled() ? new Timer(this.mLog) : null;

			try {
				coll = (new ListSessionServer(this.getConnection())).getAllModelsForLoggedUser(getConnection().getUserContext().getUserId());
			} catch (Exception var6) {
				throw new RuntimeException("unable to get Model getAllModelsForLoggedUser", var6);
			}

			this.mConnection.getClientCache().put(key.toString(), coll);
			if (timer != null) {
				timer.logDebug("getAllModelsWebForUser", "");
			}
		} else {
			this.mLog.debug("getAllModelsWebForUser", "obtained " + key + " from cache");
		}

		return coll;
	}
    public Map<String, String> getPropertiesForModelVisId(String modelVisId){
        StringBuffer key = new StringBuffer();
        key.append("getPropertiesForModelVisId");
        key.append('.');
        Map<String, String> coll = (Map<String, String>) this.mConnection.getClientCache().get(key.toString());
        if (coll == null) {
            Timer timer = this.mLog.isDebugEnabled() ? new Timer(this.mLog) : null;

            try {
                coll = (new ListSessionServer(this.getConnection())).getPropertiesForModelVisId(modelVisId);
            } catch (Exception var6) {
                throw new RuntimeException("unable to get properties getPropertiesForModelVisId", var6);
            }

            this.mConnection.getClientCache().put(key.toString(), coll);
            if (timer != null) {
                timer.logDebug("getPropertiesForModelVisId", "");
            }
        } else {
            this.mLog.debug("getPropertiesForModelVisId", "obtained " + key + " from cache");
        }

        return coll;
    }
    
    public Map<String, String> getPropertiesForModelId(int modelId){
        StringBuffer key = new StringBuffer();
        key.append("getPropertiesForModelId");
        key.append('.');
        Map<String, String> coll = (Map<String, String>) this.mConnection.getClientCache().get(key.toString());
        if (coll == null) {
            Timer timer = this.mLog.isDebugEnabled() ? new Timer(this.mLog) : null;

            try {
                coll = (new ListSessionServer(this.getConnection())).getPropertiesForModelId(modelId);
            } catch (Exception var6) {
                throw new RuntimeException("unable to get properties getPropertiesForModelId", var6);
            }

            this.mConnection.getClientCache().put(key.toString(), coll);
            if (timer != null) {
                timer.logDebug("getPropertiesForModelId", "");
            }
        } else {
            this.mLog.debug("getPropertiesForModelId", "obtained " + key + " from cache");
        }

        return coll;
    }
    
    public EntityList getAllModelsForGlobalMappedModel(int modelId) {
        StringBuffer key = new StringBuffer();
        key.append("getAllModelsForGlobalMappedModel");
        key.append('.');
        AllModelsELO coll = (AllModelsELO) this.mConnection.getClientCache().get(key.toString());
        if (coll == null) {
            Timer timer = this.mLog.isDebugEnabled() ? new Timer(this.mLog) : null;

            try {
                coll = (new ListSessionServer(this.getConnection())).getAllModelsForGlobalMappedModel(modelId);
            } catch (Exception var6) {
                throw new RuntimeException("unable to get Model getAllModelsForGlobalMappedModel", var6);
            }

            this.mConnection.getClientCache().put(key.toString(), coll);
            if (timer != null) {
                timer.logDebug("getAllModelsForGlobalMappedModel", "");
            }
        } else {
            this.mLog.debug("getAllModelsForGlobalMappedModel", "obtained " + key + " from cache");
        }

        return coll;
    }

	public EntityList getAllModelsWithActiveCycleForUser(int param1) {
		StringBuffer key = new StringBuffer();
		key.append("AllModelsWithActiveCycleForUser");
		key.append('.');
		key.append(String.valueOf(param1));
		AllModelsWithActiveCycleForUserELO coll = (AllModelsWithActiveCycleForUserELO) this.mConnection.getClientCache().get(key.toString());
		if (coll == null) {
			Timer timer = this.mLog.isDebugEnabled() ? new Timer(this.mLog) : null;

			try {
				coll = (new ListSessionServer(this.getConnection())).getAllModelsWithActiveCycleForUser(param1);
			} catch (Exception var6) {
				throw new RuntimeException("unable to get Model AllModelsWithActiveCycleForUser", var6);
			}

			this.mConnection.getClientCache().put(key.toString(), coll);
			if (timer != null) {
				timer.logDebug("getAllModelsWithActiveCycleForUser", "");
			}
		} else {
			this.mLog.debug("getAllModelsWithActiveCycleForUser", "obtained " + key + " from cache");
		}

		return coll;
	}

	public EntityList getAllBudgetHierarchies() {
		StringBuffer key = new StringBuffer();
		key.append("AllBudgetHierarchies");
		AllBudgetHierarchiesELO coll = (AllBudgetHierarchiesELO) this.mConnection.getClientCache().get(key.toString());
		if (coll == null) {
			Timer timer = this.mLog.isDebugEnabled() ? new Timer(this.mLog) : null;

			try {
				coll = (new ListSessionServer(this.getConnection())).getAllBudgetHierarchies();
			} catch (Exception var5) {
				throw new RuntimeException("unable to get Model AllBudgetHierarchies", var5);
			}

			this.mConnection.getClientCache().put(key.toString(), coll);
			if (timer != null) {
				timer.logDebug("getAllBudgetHierarchies", "");
			}
		} else {
			this.mLog.debug("getAllBudgetHierarchies", "obtained " + key + " from cache");
		}

		return coll;
	}

	public EntityList getModelForDimension(int param1) {
		StringBuffer key = new StringBuffer();
		key.append("ModelForDimension");
		key.append('.');
		key.append(String.valueOf(param1));
		ModelForDimensionELO coll = (ModelForDimensionELO) this.mConnection.getClientCache().get(key.toString());
		if (coll == null) {
			Timer timer = this.mLog.isDebugEnabled() ? new Timer(this.mLog) : null;

			try {
				coll = (new ListSessionServer(this.getConnection())).getModelForDimension(param1);
			} catch (Exception var6) {
				throw new RuntimeException("unable to get Model ModelForDimension", var6);
			}

			this.mConnection.getClientCache().put(key.toString(), coll);
			if (timer != null) {
				timer.logDebug("getModelForDimension", "");
			}
		} else {
			this.mLog.debug("getModelForDimension", "obtained " + key + " from cache");
		}

		return coll;
	}

	public EntityList getModelDimensions(int param1) {
		StringBuffer key = new StringBuffer();
		key.append("ModelDimensions");
		key.append('.');
		key.append(String.valueOf(param1));
		ModelDimensionsELO coll = (ModelDimensionsELO) this.mConnection.getClientCache().get(key.toString());
		if (coll == null) {
			Timer timer = this.mLog.isDebugEnabled() ? new Timer(this.mLog) : null;

			try {
				coll = (new ListSessionServer(this.getConnection())).getModelDimensions(param1);
			} catch (Exception var6) {
				throw new RuntimeException("unable to get Model ModelDimensions", var6);
			}

			this.mConnection.getClientCache().put(key.toString(), coll);
			if (timer != null) {
				timer.logDebug("getModelDimensions", "");
			}
		} else {
			this.mLog.debug("getModelDimensions", "obtained " + key + " from cache");
		}

		return coll;
	}

	public EntityList getModelDimensionseExcludeCall(int param1) {
		StringBuffer key = new StringBuffer();
		key.append("ModelDimensionseExcludeCall");
		key.append('.');
		key.append(String.valueOf(param1));
		ModelDimensionseExcludeCallELO coll = (ModelDimensionseExcludeCallELO) this.mConnection.getClientCache().get(key.toString());
		if (coll == null) {
			Timer timer = this.mLog.isDebugEnabled() ? new Timer(this.mLog) : null;

			try {
				coll = (new ListSessionServer(this.getConnection())).getModelDimensionseExcludeCall(param1);
			} catch (Exception var6) {
				throw new RuntimeException("unable to get Model ModelDimensionseExcludeCall", var6);
			}

			this.mConnection.getClientCache().put(key.toString(), coll);
			if (timer != null) {
				timer.logDebug("getModelDimensionseExcludeCall", "");
			}
		} else {
			this.mLog.debug("getModelDimensionseExcludeCall", "obtained " + key + " from cache");
		}

		return coll;
	}

	public EntityList getModelDetailsWeb(int param1) {
		StringBuffer key = new StringBuffer();
		key.append("ModelDetailsWeb");
		key.append('.');
		key.append(String.valueOf(param1));
		ModelDetailsWebELO coll = (ModelDetailsWebELO) this.mConnection.getClientCache().get(key.toString());
		if (coll == null) {
			Timer timer = this.mLog.isDebugEnabled() ? new Timer(this.mLog) : null;

			try {
				coll = (new ListSessionServer(this.getConnection())).getModelDetailsWeb(param1);
			} catch (Exception var6) {
				throw new RuntimeException("unable to get Model ModelDetailsWeb", var6);
			}

			this.mConnection.getClientCache().put(key.toString(), coll);
			if (timer != null) {
				timer.logDebug("getModelDetailsWeb", "");
			}
		} else {
			this.mLog.debug("getModelDetailsWeb", "obtained " + key + " from cache");
		}

		return coll;
	}

	public EntityList getAllRootsForModel(int param1) {
		StringBuffer key = new StringBuffer();
		key.append("AllRootsForModel");
		key.append('.');
		key.append(String.valueOf(param1));
		AllRootsForModelELO coll = (AllRootsForModelELO) this.mConnection.getClientCache().get(key.toString());
		if (coll == null) {
			Timer timer = this.mLog.isDebugEnabled() ? new Timer(this.mLog) : null;

			try {
				coll = (new ListSessionServer(this.getConnection())).getAllRootsForModel(param1);
			} catch (Exception var6) {
				throw new RuntimeException("unable to get Model AllRootsForModel", var6);
			}

			this.mConnection.getClientCache().put(key.toString(), coll);
			if (timer != null) {
				timer.logDebug("getAllRootsForModel", "");
			}
		} else {
			this.mLog.debug("getAllRootsForModel", "obtained " + key + " from cache");
		}

		return coll;
	}

	public EntityList getBudgetHierarchyRootNodeForModel(int param1) {
		StringBuffer key = new StringBuffer();
		key.append("BudgetHierarchyRootNodeForModel");
		key.append('.');
		key.append(String.valueOf(param1));
		BudgetHierarchyRootNodeForModelELO coll = (BudgetHierarchyRootNodeForModelELO) this.mConnection.getClientCache().get(key.toString());
		if (coll == null) {
			Timer timer = this.mLog.isDebugEnabled() ? new Timer(this.mLog) : null;

			try {
				coll = (new ListSessionServer(this.getConnection())).getBudgetHierarchyRootNodeForModel(param1);
			} catch (Exception var6) {
				throw new RuntimeException("unable to get Model BudgetHierarchyRootNodeForModel", var6);
			}

			this.mConnection.getClientCache().put(key.toString(), coll);
			if (timer != null) {
				timer.logDebug("getBudgetHierarchyRootNodeForModel", "");
			}
		} else {
			this.mLog.debug("getBudgetHierarchyRootNodeForModel", "obtained " + key + " from cache");
		}

		return coll;
	}

	public EntityList getBudgetCyclesToFixState(int param1) {
		StringBuffer key = new StringBuffer();
		key.append("BudgetCyclesToFixState");
		key.append('.');
		key.append(String.valueOf(param1));
		BudgetCyclesToFixStateELO coll = (BudgetCyclesToFixStateELO) this.mConnection.getClientCache().get(key.toString());
		if (coll == null) {
			Timer timer = this.mLog.isDebugEnabled() ? new Timer(this.mLog) : null;

			try {
				coll = (new ListSessionServer(this.getConnection())).getBudgetCyclesToFixState(param1);
			} catch (Exception var6) {
				throw new RuntimeException("unable to get Model BudgetCyclesToFixState", var6);
			}

			this.mConnection.getClientCache().put(key.toString(), coll);
			if (timer != null) {
				timer.logDebug("getBudgetCyclesToFixState", "");
			}
		} else {
			this.mLog.debug("getBudgetCyclesToFixState", "obtained " + key + " from cache");
		}

		return coll;
	}

	public EntityList getMaxDepthForBudgetHierarchy(int param1) {
		StringBuffer key = new StringBuffer();
		key.append("MaxDepthForBudgetHierarchy");
		key.append('.');
		key.append(String.valueOf(param1));
		MaxDepthForBudgetHierarchyELO coll = (MaxDepthForBudgetHierarchyELO) this.mConnection.getClientCache().get(key.toString());
		if (coll == null) {
			Timer timer = this.mLog.isDebugEnabled() ? new Timer(this.mLog) : null;

			try {
				coll = (new ListSessionServer(this.getConnection())).getMaxDepthForBudgetHierarchy(param1);
			} catch (Exception var6) {
				throw new RuntimeException("unable to get Model MaxDepthForBudgetHierarchy", var6);
			}

			this.mConnection.getClientCache().put(key.toString(), coll);
			if (timer != null) {
				timer.logDebug("getMaxDepthForBudgetHierarchy", "");
			}
		} else {
			this.mLog.debug("getMaxDepthForBudgetHierarchy", "obtained " + key + " from cache");
		}

		return coll;
	}

	public EntityList getCalendarSpecForModel(int param1) {
		StringBuffer key = new StringBuffer();
		key.append("CalendarSpecForModel");
		key.append('.');
		key.append(String.valueOf(param1));
		CalendarSpecForModelELO coll = (CalendarSpecForModelELO) this.mConnection.getClientCache().get(key.toString());
		if (coll == null) {
			Timer timer = this.mLog.isDebugEnabled() ? new Timer(this.mLog) : null;

			try {
				coll = (new ListSessionServer(this.getConnection())).getCalendarSpecForModel(param1);
			} catch (Exception var6) {
				throw new RuntimeException("unable to get Model CalendarSpecForModel", var6);
			}

			this.mConnection.getClientCache().put(key.toString(), coll);
			if (timer != null) {
				timer.logDebug("getCalendarSpecForModel", "");
			}
		} else {
			this.mLog.debug("getCalendarSpecForModel", "obtained " + key + " from cache");
		}

		return coll;
	}

	public EntityList getHierarchiesForModel(int param1) {
		StringBuffer key = new StringBuffer();
		key.append("HierarchiesForModel");
		key.append('.');
		key.append(String.valueOf(param1));
		HierarchiesForModelELO coll = (HierarchiesForModelELO) this.mConnection.getClientCache().get(key.toString());
		if (coll == null) {
			Timer timer = this.mLog.isDebugEnabled() ? new Timer(this.mLog) : null;

			try {
				coll = (new ListSessionServer(this.getConnection())).getHierarchiesForModel(param1);
			} catch (Exception var6) {
				throw new RuntimeException("unable to get Model HierarchiesForModel", var6);
			}

			this.mConnection.getClientCache().put(key.toString(), coll);
			if (timer != null) {
				timer.logDebug("getHierarchiesForModel", "");
			}
		} else {
			this.mLog.debug("getHierarchiesForModel", "obtained " + key + " from cache");
		}

		return coll;
	}

	public EntityList getAllUsersForASecurityGroup(int param1) {
		StringBuffer key = new StringBuffer();
		key.append("AllUsersForASecurityGroup");
		key.append('.');
		key.append(String.valueOf(param1));
		AllUsersForASecurityGroupELO coll = (AllUsersForASecurityGroupELO) this.mConnection.getClientCache().get(key.toString());
		if (coll == null) {
			Timer timer = this.mLog.isDebugEnabled() ? new Timer(this.mLog) : null;

			try {
				coll = (new ListSessionServer(this.getConnection())).getAllUsersForASecurityGroup(param1);
			} catch (Exception var6) {
				throw new RuntimeException("unable to get SecurityGroupUserRel AllUsersForASecurityGroup", var6);
			}

			this.mConnection.getClientCache().put(key.toString(), coll);
			if (timer != null) {
				timer.logDebug("getAllUsersForASecurityGroup", "");
			}
		} else {
			this.mLog.debug("getAllUsersForASecurityGroup", "obtained " + key + " from cache");
		}

		return coll;
	}

	public EntityList getAllModelBusinessDimensions() {
		StringBuffer key = new StringBuffer();
		key.append("AllModelBusinessDimensions");
		AllModelBusinessDimensionsELO coll = (AllModelBusinessDimensionsELO) this.mConnection.getClientCache().get(key.toString());
		if (coll == null) {
			Timer timer = this.mLog.isDebugEnabled() ? new Timer(this.mLog) : null;

			try {
				coll = (new ListSessionServer(this.getConnection())).getAllModelBusinessDimensions();
			} catch (Exception var5) {
				throw new RuntimeException("unable to get ModelDimensionRel AllModelBusinessDimensions", var5);
			}

			this.mConnection.getClientCache().put(key.toString(), coll);
			if (timer != null) {
				timer.logDebug("getAllModelBusinessDimensions", "");
			}
		} else {
			this.mLog.debug("getAllModelBusinessDimensions", "obtained " + key + " from cache");
		}

		return coll;
	}

	public EntityList getAllModelBusAndAccDimensions() {
		StringBuffer key = new StringBuffer();
		key.append("AllModelBusAndAccDimensions");
		AllModelBusAndAccDimensionsELO coll = (AllModelBusAndAccDimensionsELO) this.mConnection.getClientCache().get(key.toString());
		if (coll == null) {
			Timer timer = this.mLog.isDebugEnabled() ? new Timer(this.mLog) : null;

			try {
				coll = (new ListSessionServer(this.getConnection())).getAllModelBusAndAccDimensions();
			} catch (Exception var5) {
				throw new RuntimeException("unable to get ModelDimensionRel AllModelBusAndAccDimensions", var5);
			}

			this.mConnection.getClientCache().put(key.toString(), coll);
			if (timer != null) {
				timer.logDebug("getAllModelBusAndAccDimensions", "");
			}
		} else {
			this.mLog.debug("getAllModelBusAndAccDimensions", "obtained " + key + " from cache");
		}

		return coll;
	}

	public EntityList getBudgetDimensionIdForModel(int param1, int param2) {
		StringBuffer key = new StringBuffer();
		key.append("BudgetDimensionIdForModel");
		key.append('.');
		key.append(String.valueOf(param1));
		key.append('.');
		key.append(String.valueOf(param2));
		BudgetDimensionIdForModelELO coll = (BudgetDimensionIdForModelELO) this.mConnection.getClientCache().get(key.toString());
		if (coll == null) {
			Timer timer = this.mLog.isDebugEnabled() ? new Timer(this.mLog) : null;

			try {
				coll = (new ListSessionServer(this.getConnection())).getBudgetDimensionIdForModel(param1, param2);
			} catch (Exception var7) {
				throw new RuntimeException("unable to get ModelDimensionRel BudgetDimensionIdForModel", var7);
			}

			this.mConnection.getClientCache().put(key.toString(), coll);
			if (timer != null) {
				timer.logDebug("getBudgetDimensionIdForModel", "");
			}
		} else {
			this.mLog.debug("getBudgetDimensionIdForModel", "obtained " + key + " from cache");
		}

		return coll;
	}

	public EntityList getDimensionIdForModelDimType(int param1, int param2) {
		StringBuffer key = new StringBuffer();
		key.append("DimensionIdForModelDimType");
		key.append('.');
		key.append(String.valueOf(param1));
		key.append('.');
		key.append(String.valueOf(param2));
		DimensionIdForModelDimTypeELO coll = (DimensionIdForModelDimTypeELO) this.mConnection.getClientCache().get(key.toString());
		if (coll == null) {
			Timer timer = this.mLog.isDebugEnabled() ? new Timer(this.mLog) : null;

			try {
				coll = (new ListSessionServer(this.getConnection())).getDimensionIdForModelDimType(param1, param2);
			} catch (Exception var7) {
				throw new RuntimeException("unable to get ModelDimensionRel DimensionIdForModelDimType", var7);
			}

			this.mConnection.getClientCache().put(key.toString(), coll);
			if (timer != null) {
				timer.logDebug("getDimensionIdForModelDimType", "");
			}
		} else {
			this.mLog.debug("getDimensionIdForModelDimType", "obtained " + key + " from cache");
		}

		return coll;
	}

	public EntityList getAllFinanceCubes() {
		StringBuffer key = new StringBuffer();
		key.append("AllFinanceCubes");
		AllFinanceCubesELO coll = (AllFinanceCubesELO) this.mConnection.getClientCache().get(key.toString());
		if (coll == null) {
			Timer timer = this.mLog.isDebugEnabled() ? new Timer(this.mLog) : null;

			try {
				coll = (new ListSessionServer(this.getConnection())).getAllFinanceCubes();
			} catch (Exception var5) {
				throw new RuntimeException("unable to get FinanceCube AllFinanceCubes", var5);
			}

			this.mConnection.getClientCache().put(key.toString(), coll);
			if (timer != null) {
				timer.logDebug("getAllFinanceCubes", "");
			}
		} else {
			this.mLog.debug("getAllFinanceCubes", "obtained " + key + " from cache");
		}

		return coll;
	}

	public EntityList getAllFinanceCubesForLoggedUser() {
		Timer timer = this.mLog.isDebugEnabled() ? new Timer(this.mLog) : null;
		AllFinanceCubesELO coll;
		try {
			coll = (new ListSessionServer(this.getConnection())).getAllFinanceCubesForLoggedUser();
		} catch (Exception var5) {
			throw new RuntimeException("unable to get FinanceCube AllFinanceCubesForLoggedUser", var5);
		}
		return coll;
	}

	public EntityList getAllCubeFormulasForLoggedUser() {
		StringBuffer key = new StringBuffer();
		key.append("getAllCubeFormulasForLoggedUser");
		AllCubeFormulasELO coll = (AllCubeFormulasELO) this.mConnection.getClientCache().get(key.toString());
		if (coll == null) {
			Timer timer = this.mLog.isDebugEnabled() ? new Timer(this.mLog) : null;

			try {
				coll = (new ListSessionServer(this.getConnection())).getAllCubeFormulasForLoggedUser();
			} catch (Exception var5) {
				throw new RuntimeException("unable to get FinanceCube Formula getAllCubeFormulasForLoggedUser", var5);
			}

			this.mConnection.getClientCache().put(key.toString(), coll);
			if (timer != null) {
				timer.logDebug("getAllCubeFormulasForLoggedUser", "");
			}
		} else {
			this.mLog.debug("getAllCubeFormulasForLoggedUser", "obtained " + key + " from cache");
		}

		return coll;
	}

	public EntityList getAllSimpleFinanceCubes() {
		StringBuffer key = new StringBuffer();
		key.append("AllSimpleFinanceCubes");
		AllSimpleFinanceCubesELO coll = (AllSimpleFinanceCubesELO) this.mConnection.getClientCache().get(key.toString());
		if (coll == null) {
			Timer timer = this.mLog.isDebugEnabled() ? new Timer(this.mLog) : null;

			try {
				coll = (new ListSessionServer(this.getConnection())).getAllSimpleFinanceCubes();
			} catch (Exception var5) {
				throw new RuntimeException("unable to get FinanceCube AllSimpleFinanceCubes", var5);
			}

			this.mConnection.getClientCache().put(key.toString(), coll);
			if (timer != null) {
				timer.logDebug("getAllSimpleFinanceCubes", "");
			}
		} else {
			this.mLog.debug("getAllSimpleFinanceCubes", "obtained " + key + " from cache");
		}

		return coll;
	}

	public EntityList getAllDataTypesAttachedToFinanceCubeForModel(int param1) {
		StringBuffer key = new StringBuffer();
		key.append("AllDataTypesAttachedToFinanceCubeForModel");
		key.append('.');
		key.append(String.valueOf(param1));
		AllDataTypesAttachedToFinanceCubeForModelELO coll = (AllDataTypesAttachedToFinanceCubeForModelELO) this.mConnection.getClientCache().get(key.toString());
		if (coll == null) {
			Timer timer = this.mLog.isDebugEnabled() ? new Timer(this.mLog) : null;

			try {
				coll = (new ListSessionServer(this.getConnection())).getAllDataTypesAttachedToFinanceCubeForModel(param1);
			} catch (Exception var6) {
				throw new RuntimeException("unable to get FinanceCube AllDataTypesAttachedToFinanceCubeForModel", var6);
			}

			this.mConnection.getClientCache().put(key.toString(), coll);
			if (timer != null) {
				timer.logDebug("getAllDataTypesAttachedToFinanceCubeForModel", "");
			}
		} else {
			this.mLog.debug("getAllDataTypesAttachedToFinanceCubeForModel", "obtained " + key + " from cache");
		}

		return coll;
	}

	public EntityList getFinanceCubesForModel(int param1) {
		StringBuffer key = new StringBuffer();
		key.append("FinanceCubesForModel");
		key.append('.');
		key.append(String.valueOf(param1));
		FinanceCubesForModelELO coll = (FinanceCubesForModelELO) this.mConnection.getClientCache().get(key.toString());
		if (coll == null) {
			Timer timer = this.mLog.isDebugEnabled() ? new Timer(this.mLog) : null;

			try {
				coll = (new ListSessionServer(this.getConnection())).getFinanceCubesForModel(param1);
			} catch (Exception var6) {
				throw new RuntimeException("unable to get FinanceCube FinanceCubesForModel", var6);
			}

			this.mConnection.getClientCache().put(key.toString(), coll);
			if (timer != null) {
				timer.logDebug("getFinanceCubesForModel", "");
			}
		} else {
			this.mLog.debug("getFinanceCubesForModel", "obtained " + key + " from cache");
		}

		return coll;
	}

	public EntityList getFinanceCubeDimensionsAndHierachies(int param1) {
		StringBuffer key = new StringBuffer();
		key.append("FinanceCubeDimensionsAndHierachies");
		key.append('.');
		key.append(String.valueOf(param1));
		FinanceCubeDimensionsAndHierachiesELO coll = (FinanceCubeDimensionsAndHierachiesELO) this.mConnection.getClientCache().get(key.toString());
		if (coll == null) {
			Timer timer = this.mLog.isDebugEnabled() ? new Timer(this.mLog) : null;

			try {
				coll = (new ListSessionServer(this.getConnection())).getFinanceCubeDimensionsAndHierachies(param1);
			} catch (Exception var6) {
				throw new RuntimeException("unable to get FinanceCube FinanceCubeDimensionsAndHierachies", var6);
			}

			this.mConnection.getClientCache().put(key.toString(), coll);
			if (timer != null) {
				timer.logDebug("getFinanceCubeDimensionsAndHierachies", "");
			}
		} else {
			this.mLog.debug("getFinanceCubeDimensionsAndHierachies", "obtained " + key + " from cache");
		}

		return coll;
	}

	public EntityList getFinanceCubeAllDimensionsAndHierachies(int param1) {
		StringBuffer key = new StringBuffer();
		key.append("FinanceCubeAllDimensionsAndHierachies");
		key.append('.');
		key.append(String.valueOf(param1));
		FinanceCubeAllDimensionsAndHierachiesELO coll = (FinanceCubeAllDimensionsAndHierachiesELO) this.mConnection.getClientCache().get(key.toString());
		if (coll == null) {
			Timer timer = this.mLog.isDebugEnabled() ? new Timer(this.mLog) : null;

			try {
				coll = (new ListSessionServer(this.getConnection())).getFinanceCubeAllDimensionsAndHierachies(param1);
			} catch (Exception var6) {
				throw new RuntimeException("unable to get FinanceCube FinanceCubeAllDimensionsAndHierachies", var6);
			}

			this.mConnection.getClientCache().put(key.toString(), coll);
			if (timer != null) {
				timer.logDebug("getFinanceCubeAllDimensionsAndHierachies", "");
			}
		} else {
			this.mLog.debug("getFinanceCubeAllDimensionsAndHierachies", "obtained " + key + " from cache");
		}

		return coll;
	}

	public EntityList getAllFinanceCubesWeb() {
		StringBuffer key = new StringBuffer();
		key.append("AllFinanceCubesWeb");
		AllFinanceCubesWebELO coll = (AllFinanceCubesWebELO) this.mConnection.getClientCache().get(key.toString());
		if (coll == null) {
			Timer timer = this.mLog.isDebugEnabled() ? new Timer(this.mLog) : null;

			try {
				coll = (new ListSessionServer(this.getConnection())).getAllFinanceCubesWeb();
			} catch (Exception var5) {
				throw new RuntimeException("unable to get FinanceCube AllFinanceCubesWeb", var5);
			}

			this.mConnection.getClientCache().put(key.toString(), coll);
			if (timer != null) {
				timer.logDebug("getAllFinanceCubesWeb", "");
			}
		} else {
			this.mLog.debug("getAllFinanceCubesWeb", "obtained " + key + " from cache");
		}

		return coll;
	}

	public EntityList getAllFinanceCubesWebForModel(int param1) {
		StringBuffer key = new StringBuffer();
		key.append("AllFinanceCubesWebForModel");
		key.append('.');
		key.append(String.valueOf(param1));
		AllFinanceCubesWebForModelELO coll = (AllFinanceCubesWebForModelELO) this.mConnection.getClientCache().get(key.toString());
		if (coll == null) {
			Timer timer = this.mLog.isDebugEnabled() ? new Timer(this.mLog) : null;

			try {
				coll = (new ListSessionServer(this.getConnection())).getAllFinanceCubesWebForModel(param1);
			} catch (Exception var6) {
				throw new RuntimeException("unable to get FinanceCube AllFinanceCubesWebForModel", var6);
			}

			this.mConnection.getClientCache().put(key.toString(), coll);
			if (timer != null) {
				timer.logDebug("getAllFinanceCubesWebForModel", "");
			}
		} else {
			this.mLog.debug("getAllFinanceCubesWebForModel", "obtained " + key + " from cache");
		}

		return coll;
	}

	public EntityList getAllFinanceCubesWebForUser(int param1) {
		StringBuffer key = new StringBuffer();
		key.append("AllFinanceCubesWebForUser");
		key.append('.');
		key.append(String.valueOf(param1));
		AllFinanceCubesWebForUserELO coll = (AllFinanceCubesWebForUserELO) this.mConnection.getClientCache().get(key.toString());
		if (coll == null) {
			Timer timer = this.mLog.isDebugEnabled() ? new Timer(this.mLog) : null;

			try {
				coll = (new ListSessionServer(this.getConnection())).getAllFinanceCubesWebForUser(param1);
			} catch (Exception var6) {
				throw new RuntimeException("unable to get FinanceCube AllFinanceCubesWebForUser", var6);
			}

			this.mConnection.getClientCache().put(key.toString(), coll);
			if (timer != null) {
				timer.logDebug("getAllFinanceCubesWebForUser", "");
			}
		} else {
			this.mLog.debug("getAllFinanceCubesWebForUser", "obtained " + key + " from cache");
		}

		return coll;
	}

	public EntityList getFinanceCubeDetails(int param1) {
		StringBuffer key = new StringBuffer();
		key.append("FinanceCubeDetails");
		key.append('.');
		key.append(String.valueOf(param1));
		FinanceCubeDetailsELO coll = (FinanceCubeDetailsELO) this.mConnection.getClientCache().get(key.toString());
		if (coll == null) {
			Timer timer = this.mLog.isDebugEnabled() ? new Timer(this.mLog) : null;

			try {
				coll = (new ListSessionServer(this.getConnection())).getFinanceCubeDetails(param1);
			} catch (Exception var6) {
				throw new RuntimeException("unable to get FinanceCube FinanceCubeDetails", var6);
			}

			this.mConnection.getClientCache().put(key.toString(), coll);
			if (timer != null) {
				timer.logDebug("getFinanceCubeDetails", "");
			}
		} else {
			this.mLog.debug("getFinanceCubeDetails", "obtained " + key + " from cache");
		}

		return coll;
	}

	public EntityList getFinanceCubesUsingDataType(short param1) {
		StringBuffer key = new StringBuffer();
		key.append("FinanceCubesUsingDataType");
		key.append('.');
		key.append(String.valueOf(param1));
		FinanceCubesUsingDataTypeELO coll = (FinanceCubesUsingDataTypeELO) this.mConnection.getClientCache().get(key.toString());
		if (coll == null) {
			Timer timer = this.mLog.isDebugEnabled() ? new Timer(this.mLog) : null;

			try {
				coll = (new ListSessionServer(this.getConnection())).getFinanceCubesUsingDataType(param1);
			} catch (Exception var6) {
				throw new RuntimeException("unable to get FinanceCube FinanceCubesUsingDataType", var6);
			}

			this.mConnection.getClientCache().put(key.toString(), coll);
			if (timer != null) {
				timer.logDebug("getFinanceCubesUsingDataType", "");
			}
		} else {
			this.mLog.debug("getFinanceCubesUsingDataType", "obtained " + key + " from cache");
		}

		return coll;
	}

	public EntityList getAllFinanceCubeDataTypes() {
		StringBuffer key = new StringBuffer();
		key.append("AllFinanceCubeDataTypes");
		AllFinanceCubeDataTypesELO coll = (AllFinanceCubeDataTypesELO) this.mConnection.getClientCache().get(key.toString());
		if (coll == null) {
			Timer timer = this.mLog.isDebugEnabled() ? new Timer(this.mLog) : null;

			try {
				coll = (new ListSessionServer(this.getConnection())).getAllFinanceCubeDataTypes();
			} catch (Exception var5) {
				throw new RuntimeException("unable to get FinanceCubeDataType AllFinanceCubeDataTypes", var5);
			}

			this.mConnection.getClientCache().put(key.toString(), coll);
			if (timer != null) {
				timer.logDebug("getAllFinanceCubeDataTypes", "");
			}
		} else {
			this.mLog.debug("getAllFinanceCubeDataTypes", "obtained " + key + " from cache");
		}

		return coll;
	}

	public EntityList getImportableFinanceCubeDataTypes() {
		Timer timer = this.mLog.isDebugEnabled() ? new Timer(this.mLog) : null;
		ImportableFinanceCubeDataTypesELO coll = null;

		try {
			coll = (new ListSessionServer(this.getConnection())).getImportableFinanceCubeDataTypes();
		} catch (Exception var4) {
			throw new RuntimeException("unable to get FinanceCubeDataType ImportableFinanceCubeDataTypes", var4);
		}

		if (timer != null) {
			timer.logDebug("getImportableFinanceCubeDataTypes", "");
		}

		return coll;
	}

	public EntityList getAllAttachedDataTypesForFinanceCube(int param1) {
		StringBuffer key = new StringBuffer();
		key.append("AllAttachedDataTypesForFinanceCube");
		key.append('.');
		key.append(String.valueOf(param1));
		AllAttachedDataTypesForFinanceCubeELO coll = (AllAttachedDataTypesForFinanceCubeELO) this.mConnection.getClientCache().get(key.toString());
		if (coll == null) {
			Timer timer = this.mLog.isDebugEnabled() ? new Timer(this.mLog) : null;

			try {
				coll = (new ListSessionServer(this.getConnection())).getAllAttachedDataTypesForFinanceCube(param1);
			} catch (Exception var6) {
				throw new RuntimeException("unable to get FinanceCubeDataType AllAttachedDataTypesForFinanceCube", var6);
			}

			this.mConnection.getClientCache().put(key.toString(), coll);
			if (timer != null) {
				timer.logDebug("getAllAttachedDataTypesForFinanceCube", "");
			}
		} else {
			this.mLog.debug("getAllAttachedDataTypesForFinanceCube", "obtained " + key + " from cache");
		}

		return coll;
	}

	public EntityList getAllDataTypesForFinanceCube(int param1) {
		StringBuffer key = new StringBuffer();
		key.append("AllDataTypesForFinanceCube");
		key.append('.');
		key.append(String.valueOf(param1));
		AllDataTypesForFinanceCubeELO coll = (AllDataTypesForFinanceCubeELO) this.mConnection.getClientCache().get(key.toString());
		if (coll == null) {
			Timer timer = this.mLog.isDebugEnabled() ? new Timer(this.mLog) : null;

			try {
				coll = (new ListSessionServer(this.getConnection())).getAllDataTypesForFinanceCube(param1);
			} catch (Exception var6) {
				throw new RuntimeException("unable to get FinanceCubeDataType AllDataTypesForFinanceCube", var6);
			}

			this.mConnection.getClientCache().put(key.toString(), coll);
			if (timer != null) {
				timer.logDebug("getAllDataTypesForFinanceCube", "");
			}
		} else {
			this.mLog.debug("getAllDataTypesForFinanceCube", "obtained " + key + " from cache");
		}

		return coll;
	}

	public EntityList getAllFinanceCubesForDataType(short param1) {
		StringBuffer key = new StringBuffer();
		key.append("AllFinanceCubesForDataType");
		key.append('.');
		key.append(String.valueOf(param1));
		AllFinanceCubesForDataTypeELO coll = (AllFinanceCubesForDataTypeELO) this.mConnection.getClientCache().get(key.toString());
		if (coll == null) {
			Timer timer = this.mLog.isDebugEnabled() ? new Timer(this.mLog) : null;

			try {
				coll = (new ListSessionServer(this.getConnection())).getAllFinanceCubesForDataType(param1);
			} catch (Exception var6) {
				throw new RuntimeException("unable to get FinanceCubeDataType AllFinanceCubesForDataType", var6);
			}

			this.mConnection.getClientCache().put(key.toString(), coll);
			if (timer != null) {
				timer.logDebug("getAllFinanceCubesForDataType", "");
			}
		} else {
			this.mLog.debug("getAllFinanceCubesForDataType", "obtained " + key + " from cache");
		}

		return coll;
	}

	public EntityList getAllBudgetCycles() {
		StringBuffer key = new StringBuffer();
		key.append("AllBudgetCycles");
		AllBudgetCyclesELO coll = (AllBudgetCyclesELO) this.mConnection.getClientCache().get(key.toString());
		if (coll == null) {
			Timer timer = this.mLog.isDebugEnabled() ? new Timer(this.mLog) : null;

			try {
				coll = (new ListSessionServer(this.getConnection())).getAllBudgetCycles();
			} catch (Exception var5) {
				throw new RuntimeException("unable to get BudgetCycle AllBudgetCycles", var5);
			}

			this.mConnection.getClientCache().put(key.toString(), coll);
			if (timer != null) {
				timer.logDebug("getAllBudgetCycles", "");
			}
		} else {
			this.mLog.debug("getAllBudgetCycles", "obtained " + key + " from cache");
		}

		return coll;
	}

	public EntityList getAllBudgetCyclesWeb() {
		StringBuffer key = new StringBuffer();
		key.append("AllBudgetCyclesWeb");
		AllBudgetCyclesWebELO coll = (AllBudgetCyclesWebELO) this.mConnection.getClientCache().get(key.toString());
		if (coll == null) {
			Timer timer = this.mLog.isDebugEnabled() ? new Timer(this.mLog) : null;

			try {
				coll = (new ListSessionServer(this.getConnection())).getAllBudgetCyclesWeb();
			} catch (Exception var5) {
				throw new RuntimeException("unable to get BudgetCycle AllBudgetCyclesWeb", var5);
			}

			this.mConnection.getClientCache().put(key.toString(), coll);
			if (timer != null) {
				timer.logDebug("getAllBudgetCyclesWeb", "");
			}
		} else {
			this.mLog.debug("getAllBudgetCyclesWeb", "obtained " + key + " from cache");
		}

		return coll;
	}

	public EntityList getAllBudgetCyclesForLoggedUser() {
		StringBuffer key = new StringBuffer();
		key.append("AllBudgetCyclesForLoggedUser");
		AllBudgetCyclesELO coll = (AllBudgetCyclesELO) this.mConnection.getClientCache().get(key.toString());
		if (coll == null) {
			Timer timer = this.mLog.isDebugEnabled() ? new Timer(this.mLog) : null;

			try {
				coll = (new ListSessionServer(this.getConnection())).getAllBudgetCyclesForLoggedUser();
			} catch (Exception var5) {
				throw new RuntimeException("unable to get BudgetCycle AllBudgetCyclesForLoggedUser", var5);
			}

			this.mConnection.getClientCache().put(key.toString(), coll);
			if (timer != null) {
				timer.logDebug("getAllBudgetCyclesWebForLoggedUser", "");
			}
		} else {
			this.mLog.debug("getAllBudgetCyclesWebForLoggedUser", "obtained " + key + " from cache");
		}

		return coll;
	}

	public EntityList getAllBudgetCyclesWebDetailed() {
		StringBuffer key = new StringBuffer();
		key.append("AllBudgetCyclesWebDetailed");
		AllBudgetCyclesWebDetailedELO coll = (AllBudgetCyclesWebDetailedELO) this.mConnection.getClientCache().get(key.toString());
		if (coll == null) {
			Timer timer = this.mLog.isDebugEnabled() ? new Timer(this.mLog) : null;

			try {
				coll = (new ListSessionServer(this.getConnection())).getAllBudgetCyclesWebDetailed();
			} catch (Exception var5) {
				throw new RuntimeException("unable to get BudgetCycle AllBudgetCyclesWebDetailed", var5);
			}

			this.mConnection.getClientCache().put(key.toString(), coll);
			if (timer != null) {
				timer.logDebug("getAllBudgetCyclesWebDetailed", "");
			}
		} else {
			this.mLog.debug("getAllBudgetCyclesWebDetailed", "obtained " + key + " from cache");
		}

		return coll;
	}

	public EntityList getBudgetCyclesForModel(int param1) {
		StringBuffer key = new StringBuffer();
		key.append("BudgetCyclesForModel");
		key.append('.');
		key.append(String.valueOf(param1));
		BudgetCyclesForModelELO coll = (BudgetCyclesForModelELO) this.mConnection.getClientCache().get(key.toString());
		if (coll == null) {
			Timer timer = this.mLog.isDebugEnabled() ? new Timer(this.mLog) : null;

			try {
				coll = (new ListSessionServer(this.getConnection())).getBudgetCyclesForModel(param1);
			} catch (Exception var6) {
				throw new RuntimeException("unable to get BudgetCycle BudgetCyclesForModel", var6);
			}

			this.mConnection.getClientCache().put(key.toString(), coll);
			if (timer != null) {
				timer.logDebug("getBudgetCyclesForModel", "");
			}
		} else {
			this.mLog.debug("getBudgetCyclesForModel", "obtained " + key + " from cache");
		}

		return coll;
	}

	public EntityList getBudgetCyclesForModelWithState(int param1, int param2) {
		StringBuffer key = new StringBuffer();
		key.append("BudgetCyclesForModelWithState");
		key.append('.');
		key.append(String.valueOf(param1));
		key.append('.');
		key.append(String.valueOf(param2));
		BudgetCyclesForModelWithStateELO coll = (BudgetCyclesForModelWithStateELO) this.mConnection.getClientCache().get(key.toString());
		if (coll == null) {
			Timer timer = this.mLog.isDebugEnabled() ? new Timer(this.mLog) : null;

			try {
				coll = (new ListSessionServer(this.getConnection())).getBudgetCyclesForModelWithState(param1, param2);
			} catch (Exception var7) {
				throw new RuntimeException("unable to get BudgetCycle BudgetCyclesForModelWithState", var7);
			}

			this.mConnection.getClientCache().put(key.toString(), coll);
			if (timer != null) {
				timer.logDebug("getBudgetCyclesForModelWithState", "");
			}
		} else {
			this.mLog.debug("getBudgetCyclesForModelWithState", "obtained " + key + " from cache");
		}

		return coll;
	}

	public EntityList getBudgetCycleIntegrity() {
		StringBuffer key = new StringBuffer();
		key.append("BudgetCycleIntegrity");
		BudgetCycleIntegrityELO coll = (BudgetCycleIntegrityELO) this.mConnection.getClientCache().get(key.toString());
		if (coll == null) {
			Timer timer = this.mLog.isDebugEnabled() ? new Timer(this.mLog) : null;

			try {
				coll = (new ListSessionServer(this.getConnection())).getBudgetCycleIntegrity();
			} catch (Exception var5) {
				throw new RuntimeException("unable to get BudgetCycle BudgetCycleIntegrity", var5);
			}

			this.mConnection.getClientCache().put(key.toString(), coll);
			if (timer != null) {
				timer.logDebug("getBudgetCycleIntegrity", "");
			}
		} else {
			this.mLog.debug("getBudgetCycleIntegrity", "obtained " + key + " from cache");
		}

		return coll;
	}

	public EntityList getBudgetCycleDetailedForId(int param1) {
		StringBuffer key = new StringBuffer();
		key.append("BudgetCycleDetailedForId");
		key.append('.');
		key.append(String.valueOf(param1));
		BudgetCycleDetailedForIdELO coll = (BudgetCycleDetailedForIdELO) this.mConnection.getClientCache().get(key.toString());
		if (coll == null) {
			Timer timer = this.mLog.isDebugEnabled() ? new Timer(this.mLog) : null;

			try {
				coll = (new ListSessionServer(this.getConnection())).getBudgetCycleDetailedForId(param1);
			} catch (Exception var6) {
				throw new RuntimeException("unable to get BudgetCycle BudgetCycleDetailedForId", var6);
			}

			this.mConnection.getClientCache().put(key.toString(), coll);
			if (timer != null) {
				timer.logDebug("getBudgetCycleDetailedForId", "");
			}
		} else {
			this.mLog.debug("getBudgetCycleDetailedForId", "obtained " + key + " from cache");
		}

		return coll;
	}

	public EntityList getBudgetCycleXmlFormsForId(int param1, int userId) {
		StringBuffer key = new StringBuffer();
		key.append("BudgetCycleXmlFormsForId");
		key.append('.');
		key.append(String.valueOf(param1));
		BudgetCycleDetailedForIdELO coll = (BudgetCycleDetailedForIdELO) this.mConnection.getClientCache().get(key.toString());
		if (coll == null) {
			Timer timer = this.mLog.isDebugEnabled() ? new Timer(this.mLog) : null;

			try {
				coll = (new ListSessionServer(this.getConnection())).getBudgetCycleXmlFormsForId(param1, userId);
			} catch (Exception var6) {
				throw new RuntimeException("unable to get BudgetCycle BudgetCycleXmlFormsForId", var6);
			}

			this.mConnection.getClientCache().put(key.toString(), coll);
			if (timer != null) {
				timer.logDebug("BudgetCycleXmlFormsForId", "");
			}
		} else {
			this.mLog.debug("BudgetCycleXmlFormsForId", "obtained " + key + " from cache");
		}

		return coll;
	}

	public EntityList getBudgetTransferBudgetCycles() {
		StringBuffer key = new StringBuffer();
		key.append("BudgetTransferBudgetCycles");
		BudgetTransferBudgetCyclesELO coll = (BudgetTransferBudgetCyclesELO) this.mConnection.getClientCache().get(key.toString());
		if (coll == null) {
			Timer timer = this.mLog.isDebugEnabled() ? new Timer(this.mLog) : null;

			try {
				coll = (new ListSessionServer(this.getConnection())).getBudgetTransferBudgetCycles();
			} catch (Exception var5) {
				throw new RuntimeException("unable to get BudgetCycle BudgetTransferBudgetCycles", var5);
			}

			this.mConnection.getClientCache().put(key.toString(), coll);
			if (timer != null) {
				timer.logDebug("getBudgetTransferBudgetCycles", "");
			}
		} else {
			this.mLog.debug("getBudgetTransferBudgetCycles", "obtained " + key + " from cache");
		}

		return coll;
	}

	public EntityList getCheckIfHasState(int param1, int param2) {
		Timer timer = this.mLog.isDebugEnabled() ? new Timer(this.mLog) : null;
		CheckIfHasStateELO coll = null;

		try {
			coll = (new ListSessionServer(this.getConnection())).getCheckIfHasState(param1, param2);
		} catch (Exception var6) {
			throw new RuntimeException("unable to get BudgetState CheckIfHasState", var6);
		}

		if (timer != null) {
			timer.logDebug("getCheckIfHasState", "");
		}

		return coll;
	}

	public EntityList getCycleStateDetails(int param1) {
		StringBuffer key = new StringBuffer();
		key.append("CycleStateDetails");
		key.append('.');
		key.append(String.valueOf(param1));
		CycleStateDetailsELO coll = (CycleStateDetailsELO) this.mConnection.getClientCache().get(key.toString());
		if (coll == null) {
			Timer timer = this.mLog.isDebugEnabled() ? new Timer(this.mLog) : null;

			try {
				coll = (new ListSessionServer(this.getConnection())).getCycleStateDetails(param1);
			} catch (Exception var6) {
				throw new RuntimeException("unable to get BudgetState CycleStateDetails", var6);
			}

			this.mConnection.getClientCache().put(key.toString(), coll);
			if (timer != null) {
				timer.logDebug("getCycleStateDetails", "");
			}
		} else {
			this.mLog.debug("getCycleStateDetails", "obtained " + key + " from cache");
		}

		return coll;
	}

	public EntityList getAllResponsibilityAreas() {
		StringBuffer key = new StringBuffer();
		key.append("AllResponsibilityAreas");
		AllResponsibilityAreasELO coll = (AllResponsibilityAreasELO) this.mConnection.getClientCache().get(key.toString());
		if (coll == null) {
			Timer timer = this.mLog.isDebugEnabled() ? new Timer(this.mLog) : null;

			try {
				coll = (new ListSessionServer(this.getConnection())).getAllResponsibilityAreas();
			} catch (Exception var5) {
				throw new RuntimeException("unable to get ResponsibilityArea AllResponsibilityAreas", var5);
			}

			this.mConnection.getClientCache().put(key.toString(), coll);
			if (timer != null) {
				timer.logDebug("getAllResponsibilityAreas", "");
			}
		} else {
			this.mLog.debug("getAllResponsibilityAreas", "obtained " + key + " from cache");
		}

		return coll;
	}

	public EntityList getAllBudgetUsers() {
		StringBuffer key = new StringBuffer();
		key.append("AllBudgetUsers");
		AllBudgetUsersELO coll = (AllBudgetUsersELO) this.mConnection.getClientCache().get(key.toString());
		if (coll == null) {
			Timer timer = this.mLog.isDebugEnabled() ? new Timer(this.mLog) : null;

			try {
				coll = (new ListSessionServer(this.getConnection())).getAllBudgetUsers();
			} catch (Exception var5) {
				throw new RuntimeException("unable to get BudgetUser AllBudgetUsers", var5);
			}

			this.mConnection.getClientCache().put(key.toString(), coll);
			if (timer != null) {
				timer.logDebug("getAllBudgetUsers", "");
			}
		} else {
			this.mLog.debug("getAllBudgetUsers", "obtained " + key + " from cache");
		}

		return coll;
	}

	public EntityList getCheckUserAccessToModel(int param1, int param2) {
		StringBuffer key = new StringBuffer();
		key.append("CheckUserAccessToModel");
		key.append('.');
		key.append(String.valueOf(param1));
		key.append('.');
		key.append(String.valueOf(param2));
		CheckUserAccessToModelELO coll = (CheckUserAccessToModelELO) this.mConnection.getClientCache().get(key.toString());
		if (coll == null) {
			Timer timer = this.mLog.isDebugEnabled() ? new Timer(this.mLog) : null;

			try {
				coll = (new ListSessionServer(this.getConnection())).getCheckUserAccessToModel(param1, param2);
			} catch (Exception var7) {
				throw new RuntimeException("unable to get BudgetUser CheckUserAccessToModel", var7);
			}

			this.mConnection.getClientCache().put(key.toString(), coll);
			if (timer != null) {
				timer.logDebug("getCheckUserAccessToModel", "");
			}
		} else {
			this.mLog.debug("getCheckUserAccessToModel", "obtained " + key + " from cache");
		}

		return coll;
	}

	public EntityList getCheckUserAccess(int param1, int param2) {
		StringBuffer key = new StringBuffer();
		key.append("CheckUserAccess");
		key.append('.');
		key.append(String.valueOf(param1));
		key.append('.');
		key.append(String.valueOf(param2));
		CheckUserAccessELO coll = (CheckUserAccessELO) this.mConnection.getClientCache().get(key.toString());
		if (coll == null) {
			Timer timer = this.mLog.isDebugEnabled() ? new Timer(this.mLog) : null;

			try {
				coll = (new ListSessionServer(this.getConnection())).getCheckUserAccess(param1, param2);
			} catch (Exception var7) {
				throw new RuntimeException("unable to get BudgetUser CheckUserAccess", var7);
			}

			this.mConnection.getClientCache().put(key.toString(), coll);
			if (timer != null) {
				timer.logDebug("getCheckUserAccess", "");
			}
		} else {
			this.mLog.debug("getCheckUserAccess", "obtained " + key + " from cache");
		}

		return coll;
	}

	public EntityList getCheckUser(int param1) {
		StringBuffer key = new StringBuffer();
		key.append("CheckUser");
		key.append('.');
		key.append(String.valueOf(param1));
		CheckUserELO coll = (CheckUserELO) this.mConnection.getClientCache().get(key.toString());
		if (coll == null) {
			Timer timer = this.mLog.isDebugEnabled() ? new Timer(this.mLog) : null;

			try {
				coll = (new ListSessionServer(this.getConnection())).getCheckUser(param1);
			} catch (Exception var6) {
				throw new RuntimeException("unable to get BudgetUser CheckUser", var6);
			}

			this.mConnection.getClientCache().put(key.toString(), coll);
			if (timer != null) {
				timer.logDebug("getCheckUser", "");
			}
		} else {
			this.mLog.debug("getCheckUser", "obtained " + key + " from cache");
		}

		return coll;
	}

	public EntityList getBudgetUsersForNode(int param1, int param2) {
		StringBuffer key = new StringBuffer();
		key.append("BudgetUsersForNode");
		key.append('.');
		key.append(String.valueOf(param1));
		key.append('.');
		key.append(String.valueOf(param2));
		BudgetUsersForNodeELO coll = (BudgetUsersForNodeELO) this.mConnection.getClientCache().get(key.toString());
		if (coll == null) {
			Timer timer = this.mLog.isDebugEnabled() ? new Timer(this.mLog) : null;

			try {
				coll = (new ListSessionServer(this.getConnection())).getBudgetUsersForNode(param1, param2);
			} catch (Exception var7) {
				throw new RuntimeException("unable to get BudgetUser BudgetUsersForNode", var7);
			}

			this.mConnection.getClientCache().put(key.toString(), coll);
			if (timer != null) {
				timer.logDebug("getBudgetUsersForNode", "");
			}
		} else {
			this.mLog.debug("getBudgetUsersForNode", "obtained " + key + " from cache");
		}

		return coll;
	}

	public EntityList getNodesForUserAndCycle(int param1, int param2) {
		StringBuffer key = new StringBuffer();
		key.append("NodesForUserAndCycle");
		key.append('.');
		key.append(String.valueOf(param1));
		key.append('.');
		key.append(String.valueOf(param2));
		NodesForUserAndCycleELO coll = (NodesForUserAndCycleELO) this.mConnection.getClientCache().get(key.toString());
		if (coll == null) {
			Timer timer = this.mLog.isDebugEnabled() ? new Timer(this.mLog) : null;

			try {
				coll = (new ListSessionServer(this.getConnection())).getNodesForUserAndCycle(param1, param2);
			} catch (Exception var7) {
				throw new RuntimeException("unable to get BudgetUser NodesForUserAndCycle", var7);
			}

			this.mConnection.getClientCache().put(key.toString(), coll);
			if (timer != null) {
				timer.logDebug("getNodesForUserAndCycle", "");
			}
		} else {
			this.mLog.debug("getNodesForUserAndCycle", "obtained " + key + " from cache");
		}

		return coll;
	}

	public EntityList getNodesForUserAndModel(int param1, int param2) {
		StringBuffer key = new StringBuffer();
		key.append("NodesForUserAndModel");
		key.append('.');
		key.append(String.valueOf(param1));
		key.append('.');
		key.append(String.valueOf(param2));
		NodesForUserAndModelELO coll = (NodesForUserAndModelELO) this.mConnection.getClientCache().get(key.toString());
		if (coll == null) {
			Timer timer = this.mLog.isDebugEnabled() ? new Timer(this.mLog) : null;

			try {
				coll = (new ListSessionServer(this.getConnection())).getNodesForUserAndModel(param1, param2);
			} catch (Exception var7) {
				throw new RuntimeException("unable to get BudgetUser NodesForUserAndModel", var7);
			}

			this.mConnection.getClientCache().put(key.toString(), coll);
			if (timer != null) {
				timer.logDebug("getNodesForUserAndModel", "");
			}
		} else {
			this.mLog.debug("getNodesForUserAndModel", "obtained " + key + " from cache");
		}

		return coll;
	}

	public EntityList getUsersForModelAndElement(int param1, int param2) {
		StringBuffer key = new StringBuffer();
		key.append("UsersForModelAndElement");
		key.append('.');
		key.append(String.valueOf(param1));
		key.append('.');
		key.append(String.valueOf(param2));
		UsersForModelAndElementELO coll = (UsersForModelAndElementELO) this.mConnection.getClientCache().get(key.toString());
		if (coll == null) {
			Timer timer = this.mLog.isDebugEnabled() ? new Timer(this.mLog) : null;

			try {
				coll = (new ListSessionServer(this.getConnection())).getUsersForModelAndElement(param1, param2);
			} catch (Exception var7) {
				throw new RuntimeException("unable to get BudgetUser UsersForModelAndElement", var7);
			}

			this.mConnection.getClientCache().put(key.toString(), coll);
			if (timer != null) {
				timer.logDebug("getUsersForModelAndElement", "");
			}
		} else {
			this.mLog.debug("getUsersForModelAndElement", "obtained " + key + " from cache");
		}

		return coll;
	}

	public EntityList getAllDataTypes() {
		StringBuffer key = new StringBuffer();
		key.append("AllDataTypes");
		AllDataTypesELO coll = (AllDataTypesELO) this.mConnection.getClientCache().get(key.toString());
		if (coll == null) {
			Timer timer = this.mLog.isDebugEnabled() ? new Timer(this.mLog) : null;

			try {
				coll = (new ListSessionServer(this.getConnection())).getAllDataTypes();
			} catch (Exception var5) {
				throw new RuntimeException("unable to get DataType AllDataTypes", var5);
			}

			this.mConnection.getClientCache().put(key.toString(), coll);
			if (timer != null) {
				timer.logDebug("getAllDataTypes", "");
			}
		} else {
			this.mLog.debug("getAllDataTypes", "obtained " + key + " from cache");
		}

		return coll;
	}

	public EntityList getAllDataTypesWeb() {
		StringBuffer key = new StringBuffer();
		key.append("AllDataTypesWeb");
		AllDataTypesWebELO coll = (AllDataTypesWebELO) this.mConnection.getClientCache().get(key.toString());
		if (coll == null) {
			Timer timer = this.mLog.isDebugEnabled() ? new Timer(this.mLog) : null;

			try {
				coll = (new ListSessionServer(this.getConnection())).getAllDataTypesWeb();
			} catch (Exception var5) {
				throw new RuntimeException("unable to get DataType AllDataTypesWeb", var5);
			}

			this.mConnection.getClientCache().put(key.toString(), coll);
			if (timer != null) {
				timer.logDebug("getAllDataTypesWeb", "");
			}
		} else {
			this.mLog.debug("getAllDataTypesWeb", "obtained " + key + " from cache");
		}

		return coll;
	}

	public EntityList getAllDataTypeForFinanceCube(int param1) {
		StringBuffer key = new StringBuffer();
		key.append("AllDataTypeForFinanceCube");
		key.append('.');
		key.append(String.valueOf(param1));
		AllDataTypeForFinanceCubeELO coll = (AllDataTypeForFinanceCubeELO) this.mConnection.getClientCache().get(key.toString());
		if (coll == null) {
			Timer timer = this.mLog.isDebugEnabled() ? new Timer(this.mLog) : null;

			try {
				coll = (new ListSessionServer(this.getConnection())).getAllDataTypeForFinanceCube(param1);
			} catch (Exception var6) {
				throw new RuntimeException("unable to get DataType AllDataTypeForFinanceCube", var6);
			}

			this.mConnection.getClientCache().put(key.toString(), coll);
			if (timer != null) {
				timer.logDebug("getAllDataTypeForFinanceCube", "");
			}
		} else {
			this.mLog.debug("getAllDataTypeForFinanceCube", "obtained " + key + " from cache");
		}

		return coll;
	}

	public EntityList getAllDataTypesForModel(int param1) {
		StringBuffer key = new StringBuffer();
		key.append("AllDataTypesForModel");
		key.append('.');
		key.append(String.valueOf(param1));
		AllDataTypesForModelELO coll = (AllDataTypesForModelELO) this.mConnection.getClientCache().get(key.toString());
		if (coll == null) {
			Timer timer = this.mLog.isDebugEnabled() ? new Timer(this.mLog) : null;

			try {
				coll = (new ListSessionServer(this.getConnection())).getAllDataTypesForModel(param1);
			} catch (Exception var6) {
				throw new RuntimeException("unable to get DataType AllDataTypesForModel", var6);
			}

			this.mConnection.getClientCache().put(key.toString(), coll);
			if (timer != null) {
				timer.logDebug("getAllDataTypesForModel", "");
			}
		} else {
			this.mLog.debug("getAllDataTypesForModel", "obtained " + key + " from cache");
		}

		return coll;
	}

	public EntityList getDataTypesByType(int param1) {
		StringBuffer key = new StringBuffer();
		key.append("DataTypesByType");
		key.append('.');
		key.append(String.valueOf(param1));
		DataTypesByTypeELO coll = (DataTypesByTypeELO) this.mConnection.getClientCache().get(key.toString());
		if (coll == null) {
			Timer timer = this.mLog.isDebugEnabled() ? new Timer(this.mLog) : null;

			try {
				coll = (new ListSessionServer(this.getConnection())).getDataTypesByType(param1);
			} catch (Exception var6) {
				throw new RuntimeException("unable to get DataType DataTypesByType", var6);
			}

			this.mConnection.getClientCache().put(key.toString(), coll);
			if (timer != null) {
				timer.logDebug("getDataTypesByType", "");
			}
		} else {
			this.mLog.debug("getDataTypesByType", "obtained " + key + " from cache");
		}

		return coll;
	}

	public EntityList getDataTypesByTypeWriteable(int param1) {
		StringBuffer key = new StringBuffer();
		key.append("DataTypesByTypeWriteable");
		key.append('.');
		key.append(String.valueOf(param1));
		DataTypesByTypeWriteableELO coll = (DataTypesByTypeWriteableELO) this.mConnection.getClientCache().get(key.toString());
		if (coll == null) {
			Timer timer = this.mLog.isDebugEnabled() ? new Timer(this.mLog) : null;

			try {
				coll = (new ListSessionServer(this.getConnection())).getDataTypesByTypeWriteable(param1);
			} catch (Exception var6) {
				throw new RuntimeException("unable to get DataType DataTypesByTypeWriteable", var6);
			}

			this.mConnection.getClientCache().put(key.toString(), coll);
			if (timer != null) {
				timer.logDebug("getDataTypesByTypeWriteable", "");
			}
		} else {
			this.mLog.debug("getDataTypesByTypeWriteable", "obtained " + key + " from cache");
		}

		return coll;
	}

	public EntityList getDataTypeDependencies(short param1) {
		StringBuffer key = new StringBuffer();
		key.append("DataTypeDependencies");
		key.append('.');
		key.append(String.valueOf(param1));
		DataTypeDependenciesELO coll = (DataTypeDependenciesELO) this.mConnection.getClientCache().get(key.toString());
		if (coll == null) {
			Timer timer = this.mLog.isDebugEnabled() ? new Timer(this.mLog) : null;

			try {
				coll = (new ListSessionServer(this.getConnection())).getDataTypeDependencies(param1);
			} catch (Exception var6) {
				throw new RuntimeException("unable to get DataType DataTypeDependencies", var6);
			}

			this.mConnection.getClientCache().put(key.toString(), coll);
			if (timer != null) {
				timer.logDebug("getDataTypeDependencies", "");
			}
		} else {
			this.mLog.debug("getDataTypeDependencies", "obtained " + key + " from cache");
		}

		return coll;
	}

	public EntityList getDataTypesForImpExp() {
		StringBuffer key = new StringBuffer();
		key.append("DataTypesForImpExp");
		DataTypesForImpExpELO coll = (DataTypesForImpExpELO) this.mConnection.getClientCache().get(key.toString());
		if (coll == null) {
			Timer timer = this.mLog.isDebugEnabled() ? new Timer(this.mLog) : null;

			try {
				coll = (new ListSessionServer(this.getConnection())).getDataTypesForImpExp();
			} catch (Exception var5) {
				throw new RuntimeException("unable to get DataType DataTypesForImpExp", var5);
			}

			this.mConnection.getClientCache().put(key.toString(), coll);
			if (timer != null) {
				timer.logDebug("getDataTypesForImpExp", "");
			}
		} else {
			this.mLog.debug("getDataTypesForImpExp", "obtained " + key + " from cache");
		}

		return coll;
	}

	public EntityList getDataTypeDetailsForVisID(String param1) {
		StringBuffer key = new StringBuffer();
		key.append("DataTypeDetailsForVisID");
		key.append('.');
		key.append(param1.toString());
		DataTypeDetailsForVisIDELO coll = (DataTypeDetailsForVisIDELO) this.mConnection.getClientCache().get(key.toString());
		if (coll == null) {
			Timer timer = this.mLog.isDebugEnabled() ? new Timer(this.mLog) : null;

			try {
				coll = (new ListSessionServer(this.getConnection())).getDataTypeDetailsForVisID(param1);
			} catch (Exception var6) {
				throw new RuntimeException("unable to get DataType DataTypeDetailsForVisID", var6);
			}

			this.mConnection.getClientCache().put(key.toString(), coll);
			if (timer != null) {
				timer.logDebug("getDataTypeDetailsForVisID", "");
			}
		} else {
			this.mLog.debug("getDataTypeDetailsForVisID", "obtained " + key + " from cache");
		}

		return coll;
	}

	public EntityList getAllCurrencys() {
		StringBuffer key = new StringBuffer();
		key.append("AllCurrencys");
		AllCurrencysELO coll = (AllCurrencysELO) this.mConnection.getClientCache().get(key.toString());
		if (coll == null) {
			Timer timer = this.mLog.isDebugEnabled() ? new Timer(this.mLog) : null;

			try {
				coll = (new ListSessionServer(this.getConnection())).getAllCurrencys();
			} catch (Exception var5) {
				throw new RuntimeException("unable to get Currency AllCurrencys", var5);
			}

			this.mConnection.getClientCache().put(key.toString(), coll);
			if (timer != null) {
				timer.logDebug("getAllCurrencys", "");
			}
		} else {
			this.mLog.debug("getAllCurrencys", "obtained " + key + " from cache");
		}

		return coll;
	}

	public EntityList getAllStructureElements(int param1) {
		StringBuffer key = new StringBuffer();
		key.append("AllStructureElements");
		key.append('.');
		key.append(String.valueOf(param1));
		AllStructureElementsELO coll = (AllStructureElementsELO) this.mConnection.getClientCache().get(key.toString());
		if (coll == null) {
			Timer timer = this.mLog.isDebugEnabled() ? new Timer(this.mLog) : null;

			try {
				coll = (new ListSessionServer(this.getConnection())).getAllStructureElements(param1);
			} catch (Exception var6) {
				throw new RuntimeException("unable to get StructureElement AllStructureElements", var6);
			}

			this.mConnection.getClientCache().put(key.toString(), coll);
			if (timer != null) {
				timer.logDebug("getAllStructureElements", "");
			}
		} else {
			this.mLog.debug("getAllStructureElements", "obtained " + key + " from cache");
		}

		return coll;
	}

	public EntityList getAllLeafStructureElements(int param1) {
		StringBuffer key = new StringBuffer();
		key.append("AllLeafStructureElements");
		key.append('.');
		key.append(String.valueOf(param1));
		AllLeafStructureElementsELO coll = (AllLeafStructureElementsELO) this.mConnection.getClientCache().get(key.toString());
		if (coll == null) {
			Timer timer = this.mLog.isDebugEnabled() ? new Timer(this.mLog) : null;

			try {
				coll = (new ListSessionServer(this.getConnection())).getAllLeafStructureElements(param1);
			} catch (Exception var6) {
				throw new RuntimeException("unable to get StructureElement AllLeafStructureElements", var6);
			}

			this.mConnection.getClientCache().put(key.toString(), coll);
			if (timer != null) {
				timer.logDebug("getAllLeafStructureElements", "");
			}
		} else {
			this.mLog.debug("getAllLeafStructureElements", "obtained " + key + " from cache");
		}

		return coll;
	}

	public EntityList getLeafPlannableBudgetLocationsForModel(int param1) {
		StringBuffer key = new StringBuffer();
		key.append("LeafPlannableBudgetLocationsForModel");
		key.append('.');
		key.append(String.valueOf(param1));
		LeafPlannableBudgetLocationsForModelELO coll = (LeafPlannableBudgetLocationsForModelELO) this.mConnection.getClientCache().get(key.toString());
		if (coll == null) {
			Timer timer = this.mLog.isDebugEnabled() ? new Timer(this.mLog) : null;

			try {
				coll = (new ListSessionServer(this.getConnection())).getLeafPlannableBudgetLocationsForModel(param1);
			} catch (Exception var6) {
				throw new RuntimeException("unable to get StructureElement LeafPlannableBudgetLocationsForModel", var6);
			}

			this.mConnection.getClientCache().put(key.toString(), coll);
			if (timer != null) {
				timer.logDebug("getLeafPlannableBudgetLocationsForModel", "");
			}
		} else {
			this.mLog.debug("getLeafPlannableBudgetLocationsForModel", "obtained " + key + " from cache");
		}

		return coll;
	}

	public EntityList getStructureElementParents(int param1, int param2) {
		StringBuffer key = new StringBuffer();
		key.append("StructureElementParents");
		key.append('.');
		key.append(String.valueOf(param1));
		key.append('.');
		key.append(String.valueOf(param2));
		StructureElementParentsELO coll = (StructureElementParentsELO) this.mConnection.getClientCache().get(key.toString());
		if (coll == null) {
			Timer timer = this.mLog.isDebugEnabled() ? new Timer(this.mLog) : null;

			try {
				coll = (new ListSessionServer(this.getConnection())).getStructureElementParents(param1, param2);
			} catch (Exception var7) {
				throw new RuntimeException("unable to get StructureElement StructureElementParents", var7);
			}

			this.mConnection.getClientCache().put(key.toString(), coll);
			if (timer != null) {
				timer.logDebug("getStructureElementParents", "");
			}
		} else {
			this.mLog.debug("getStructureElementParents", "obtained " + key + " from cache");
		}

		return coll;
	}

	public EntityList getStructureElementParentsReversed(int param1, int param2) {
		StringBuffer key = new StringBuffer();
		key.append("StructureElementParentsReversed");
		key.append('.');
		key.append(String.valueOf(param1));
		key.append('.');
		key.append(String.valueOf(param2));
		StructureElementParentsReversedELO coll = (StructureElementParentsReversedELO) this.mConnection.getClientCache().get(key.toString());
		if (coll == null) {
			Timer timer = this.mLog.isDebugEnabled() ? new Timer(this.mLog) : null;

			try {
				coll = (new ListSessionServer(this.getConnection())).getStructureElementParentsReversed(param1, param2);
			} catch (Exception var7) {
				throw new RuntimeException("unable to get StructureElement StructureElementParentsReversed", var7);
			}

			this.mConnection.getClientCache().put(key.toString(), coll);
			if (timer != null) {
				timer.logDebug("getStructureElementParentsReversed", "");
			}
		} else {
			this.mLog.debug("getStructureElementParentsReversed", "obtained " + key + " from cache");
		}

		return coll;
	}

	public EntityList getStructureElementParentsFromVisId(String param1, int param2) {
		StringBuffer key = new StringBuffer();
		key.append("StructureElementParentsFromVisId");
		key.append('.');
		key.append(param1.toString());
		key.append('.');
		key.append(String.valueOf(param2));
		StructureElementParentsFromVisIdELO coll = (StructureElementParentsFromVisIdELO) this.mConnection.getClientCache().get(key.toString());
		if (coll == null) {
			Timer timer = this.mLog.isDebugEnabled() ? new Timer(this.mLog) : null;

			try {
				coll = (new ListSessionServer(this.getConnection())).getStructureElementParentsFromVisId(param1, param2);
			} catch (Exception var7) {
				throw new RuntimeException("unable to get StructureElement StructureElementParentsFromVisId", var7);
			}

			this.mConnection.getClientCache().put(key.toString(), coll);
			if (timer != null) {
				timer.logDebug("getStructureElementParentsFromVisId", "");
			}
		} else {
			this.mLog.debug("getStructureElementParentsFromVisId", "obtained " + key + " from cache");
		}

		return coll;
	}

	public EntityList getImmediateChildren(int param1, int param2) {
		Timer timer = this.mLog.isDebugEnabled() ? new Timer(this.mLog) : null;
		ImmediateChildrenELO coll = null;

		try {
			coll = (new ListSessionServer(this.getConnection())).getImmediateChildren(param1, param2);
		} catch (Exception var6) {
			throw new RuntimeException("unable to get StructureElement ImmediateChildren", var6);
		}

		if (timer != null) {
			timer.logDebug("getImmediateChildren", "");
		}

		return coll;
	}

	public EntityList getMassStateImmediateChildren(int param1, int param2) {
		Timer timer = this.mLog.isDebugEnabled() ? new Timer(this.mLog) : null;
		MassStateImmediateChildrenELO coll = null;

		try {
			coll = (new ListSessionServer(this.getConnection())).getMassStateImmediateChildren(param1, param2);
		} catch (Exception var6) {
			throw new RuntimeException("unable to get StructureElement MassStateImmediateChildren", var6);
		}

		if (timer != null) {
			timer.logDebug("getMassStateImmediateChildren", "");
		}

		return coll;
	}

	public EntityList getStructureElementValues(int param1, int param2) {
		Timer timer = this.mLog.isDebugEnabled() ? new Timer(this.mLog) : null;
		StructureElementValuesELO coll = null;

		try {
			coll = (new ListSessionServer(this.getConnection())).getStructureElementValues(param1, param2);
		} catch (Exception var6) {
			throw new RuntimeException("unable to get StructureElement StructureElementValues", var6);
		}

		if (timer != null) {
			timer.logDebug("getStructureElementValues", "");
		}

		return coll;
	}

	public EntityList getCheckLeaf(int param1) {
		StringBuffer key = new StringBuffer();
		key.append("CheckLeaf");
		key.append('.');
		key.append(String.valueOf(param1));
		CheckLeafELO coll = (CheckLeafELO) this.mConnection.getClientCache().get(key.toString());
		if (coll == null) {
			Timer timer = this.mLog.isDebugEnabled() ? new Timer(this.mLog) : null;

			try {
				coll = (new ListSessionServer(this.getConnection())).getCheckLeaf(param1);
			} catch (Exception var6) {
				throw new RuntimeException("unable to get StructureElement CheckLeaf", var6);
			}

			this.mConnection.getClientCache().put(key.toString(), coll);
			if (timer != null) {
				timer.logDebug("getCheckLeaf", "");
			}
		} else {
			this.mLog.debug("getCheckLeaf", "obtained " + key + " from cache");
		}

		return coll;
	}

	public EntityList getStructureElement(int param1) {
		StringBuffer key = new StringBuffer();
		key.append("StructureElement");
		key.append('.');
		key.append(String.valueOf(param1));
		StructureElementELO coll = (StructureElementELO) this.mConnection.getClientCache().get(key.toString());
		if (coll == null) {
			Timer timer = this.mLog.isDebugEnabled() ? new Timer(this.mLog) : null;

			try {
				coll = (new ListSessionServer(this.getConnection())).getStructureElement(param1);
			} catch (Exception var6) {
				throw new RuntimeException("unable to get StructureElement StructureElement", var6);
			}

			this.mConnection.getClientCache().put(key.toString(), coll);
			if (timer != null) {
				timer.logDebug("getStructureElement", "");
			}
		} else {
			this.mLog.debug("getStructureElement", "obtained " + key + " from cache");
		}

		return coll;
	}

	public EntityList getStructureElementForIds(int param1, int param2) {
		StringBuffer key = new StringBuffer();
		key.append("StructureElementForIds");
		key.append('.');
		key.append(String.valueOf(param1));
		key.append('.');
		key.append(String.valueOf(param2));
		StructureElementForIdsELO coll = (StructureElementForIdsELO) this.mConnection.getClientCache().get(key.toString());
		if (coll == null) {
			Timer timer = this.mLog.isDebugEnabled() ? new Timer(this.mLog) : null;

			try {
				coll = (new ListSessionServer(this.getConnection())).getStructureElementForIds(param1, param2);
			} catch (Exception var7) {
				throw new RuntimeException("unable to get StructureElement StructureElementForIds", var7);
			}

			this.mConnection.getClientCache().put(key.toString(), coll);
			if (timer != null) {
				timer.logDebug("getStructureElementForIds", "");
			}
		} else {
			this.mLog.debug("getStructureElementForIds", "obtained " + key + " from cache");
		}

		return coll;
	}

	public EntityList getStructureElementByVisId(String param1, int param2) {
		StringBuffer key = new StringBuffer();
		key.append("StructureElementByVisId");
		key.append('.');
		key.append(param1.toString());
		key.append('.');
		key.append(String.valueOf(param2));
		StructureElementByVisIdELO coll = (StructureElementByVisIdELO) this.mConnection.getClientCache().get(key.toString());
		if (coll == null) {
			Timer timer = this.mLog.isDebugEnabled() ? new Timer(this.mLog) : null;

			try {
				coll = (new ListSessionServer(this.getConnection())).getStructureElementByVisId(param1, param2);
			} catch (Exception var7) {
				throw new RuntimeException("unable to get StructureElement StructureElementByVisId", var7);
			}

			this.mConnection.getClientCache().put(key.toString(), coll);
			if (timer != null) {
				timer.logDebug("getStructureElementByVisId", "");
			}
		} else {
			this.mLog.debug("getStructureElementByVisId", "obtained " + key + " from cache");
		}

		return coll;
	}

	public EntityList getRespAreaImmediateChildren(int param1, int param2) {
		Timer timer = this.mLog.isDebugEnabled() ? new Timer(this.mLog) : null;
		RespAreaImmediateChildrenELO coll = null;

		try {
			coll = (new ListSessionServer(this.getConnection())).getRespAreaImmediateChildren(param1, param2);
		} catch (Exception var6) {
			throw new RuntimeException("unable to get StructureElement RespAreaImmediateChildren", var6);
		}

		if (timer != null) {
			timer.logDebug("getRespAreaImmediateChildren", "");
		}

		return coll;
	}

	public EntityList getAllDisabledLeaf(int param1) {
		StringBuffer key = new StringBuffer();
		key.append("AllDisabledLeaf");
		key.append('.');
		key.append(String.valueOf(param1));
		AllDisabledLeafELO coll = (AllDisabledLeafELO) this.mConnection.getClientCache().get(key.toString());
		if (coll == null) {
			Timer timer = this.mLog.isDebugEnabled() ? new Timer(this.mLog) : null;

			try {
				coll = (new ListSessionServer(this.getConnection())).getAllDisabledLeaf(param1);
			} catch (Exception var6) {
				throw new RuntimeException("unable to get StructureElement AllDisabledLeaf", var6);
			}

			this.mConnection.getClientCache().put(key.toString(), coll);
			if (timer != null) {
				timer.logDebug("getAllDisabledLeaf", "");
			}
		} else {
			this.mLog.debug("getAllDisabledLeaf", "obtained " + key + " from cache");
		}

		return coll;
	}

	public EntityList getAllNotPlannable(int param1) {
		StringBuffer key = new StringBuffer();
		key.append("AllNotPlannable");
		key.append('.');
		key.append(String.valueOf(param1));
		AllNotPlannableELO coll = (AllNotPlannableELO) this.mConnection.getClientCache().get(key.toString());
		if (coll == null) {
			Timer timer = this.mLog.isDebugEnabled() ? new Timer(this.mLog) : null;

			try {
				coll = (new ListSessionServer(this.getConnection())).getAllNotPlannable(param1);
			} catch (Exception var6) {
				throw new RuntimeException("unable to get StructureElement AllNotPlannable", var6);
			}

			this.mConnection.getClientCache().put(key.toString(), coll);
			if (timer != null) {
				timer.logDebug("getAllNotPlannable", "");
			}
		} else {
			this.mLog.debug("getAllNotPlannable", "obtained " + key + " from cache");
		}

		return coll;
	}

	public EntityList getAllDisabledLeafandNotPlannable(int param1) {
		StringBuffer key = new StringBuffer();
		key.append("AllDisabledLeafandNotPlannable");
		key.append('.');
		key.append(String.valueOf(param1));
		AllDisabledLeafandNotPlannableELO coll = (AllDisabledLeafandNotPlannableELO) this.mConnection.getClientCache().get(key.toString());
		if (coll == null) {
			Timer timer = this.mLog.isDebugEnabled() ? new Timer(this.mLog) : null;

			try {
				coll = (new ListSessionServer(this.getConnection())).getAllDisabledLeafandNotPlannable(param1);
			} catch (Exception var6) {
				throw new RuntimeException("unable to get StructureElement AllDisabledLeafandNotPlannable", var6);
			}

			this.mConnection.getClientCache().put(key.toString(), coll);
			if (timer != null) {
				timer.logDebug("getAllDisabledLeafandNotPlannable", "");
			}
		} else {
			this.mLog.debug("getAllDisabledLeafandNotPlannable", "obtained " + key + " from cache");
		}

		return coll;
	}

	public EntityList getLeavesForParent(int param1, int param2, int param3, int param4, int param5) {
		StringBuffer key = new StringBuffer();
		key.append("LeavesForParent");
		key.append('.');
		key.append(String.valueOf(param1));
		key.append('.');
		key.append(String.valueOf(param2));
		key.append('.');
		key.append(String.valueOf(param3));
		key.append('.');
		key.append(String.valueOf(param4));
		key.append('.');
		key.append(String.valueOf(param5));
		LeavesForParentELO coll = (LeavesForParentELO) this.mConnection.getClientCache().get(key.toString());
		if (coll == null) {
			Timer timer = this.mLog.isDebugEnabled() ? new Timer(this.mLog) : null;

			try {
				coll = (new ListSessionServer(this.getConnection())).getLeavesForParent(param1, param2, param3, param4, param5);
			} catch (Exception var10) {
				throw new RuntimeException("unable to get StructureElement LeavesForParent", var10);
			}

			this.mConnection.getClientCache().put(key.toString(), coll);
			if (timer != null) {
				timer.logDebug("getLeavesForParent", "");
			}
		} else {
			this.mLog.debug("getLeavesForParent", "obtained " + key + " from cache");
		}

		return coll;
	}

	public EntityList getChildrenForParent(int param1, int param2, int param3, int param4, int param5) {
		StringBuffer key = new StringBuffer();
		key.append("ChildrenForParent");
		key.append('.');
		key.append(String.valueOf(param1));
		key.append('.');
		key.append(String.valueOf(param2));
		key.append('.');
		key.append(String.valueOf(param3));
		key.append('.');
		key.append(String.valueOf(param4));
		key.append('.');
		key.append(String.valueOf(param5));
		ChildrenForParentELO coll = (ChildrenForParentELO) this.mConnection.getClientCache().get(key.toString());
		if (coll == null) {
			Timer timer = this.mLog.isDebugEnabled() ? new Timer(this.mLog) : null;

			try {
				coll = (new ListSessionServer(this.getConnection())).getChildrenForParent(param1, param2, param3, param4, param5);
			} catch (Exception var10) {
				throw new RuntimeException("unable to get StructureElement ChildrenForParent", var10);
			}

			this.mConnection.getClientCache().put(key.toString(), coll);
			if (timer != null) {
				timer.logDebug("getChildrenForParent", "");
			}
		} else {
			this.mLog.debug("getChildrenForParent", "obtained " + key + " from cache");
		}

		return coll;
	}

	public EntityList getReportLeavesForParent(int param1, int param2, int param3, int param4, int param5) {
		StringBuffer key = new StringBuffer();
		key.append("ReportLeavesForParent");
		key.append('.');
		key.append(String.valueOf(param1));
		key.append('.');
		key.append(String.valueOf(param2));
		key.append('.');
		key.append(String.valueOf(param3));
		key.append('.');
		key.append(String.valueOf(param4));
		key.append('.');
		key.append(String.valueOf(param5));
		ReportLeavesForParentELO coll = (ReportLeavesForParentELO) this.mConnection.getClientCache().get(key.toString());
		if (coll == null) {
			Timer timer = this.mLog.isDebugEnabled() ? new Timer(this.mLog) : null;

			try {
				coll = (new ListSessionServer(this.getConnection())).getReportLeavesForParent(param1, param2, param3, param4, param5);
			} catch (Exception var10) {
				throw new RuntimeException("unable to get StructureElement ReportLeavesForParent", var10);
			}

			this.mConnection.getClientCache().put(key.toString(), coll);
			if (timer != null) {
				timer.logDebug("getReportLeavesForParent", "");
			}
		} else {
			this.mLog.debug("getReportLeavesForParent", "obtained " + key + " from cache");
		}

		return coll;
	}

	public EntityList getReportChildrenForParentToRelativeDepth(int param1, int param2, int param3, int param4, int param5, int param6, int param7, int param8) {
		StringBuffer key = new StringBuffer();
		key.append("ReportChildrenForParentToRelativeDepth");
		key.append('.');
		key.append(String.valueOf(param1));
		key.append('.');
		key.append(String.valueOf(param2));
		key.append('.');
		key.append(String.valueOf(param3));
		key.append('.');
		key.append(String.valueOf(param4));
		key.append('.');
		key.append(String.valueOf(param5));
		key.append('.');
		key.append(String.valueOf(param6));
		key.append('.');
		key.append(String.valueOf(param7));
		key.append('.');
		key.append(String.valueOf(param8));
		ReportChildrenForParentToRelativeDepthELO coll = (ReportChildrenForParentToRelativeDepthELO) this.mConnection.getClientCache().get(key.toString());
		if (coll == null) {
			Timer timer = this.mLog.isDebugEnabled() ? new Timer(this.mLog) : null;

			try {
				coll = (new ListSessionServer(this.getConnection())).getReportChildrenForParentToRelativeDepth(param1, param2, param3, param4, param5, param6, param7, param8);
			} catch (Exception var13) {
				throw new RuntimeException("unable to get StructureElement ReportChildrenForParentToRelativeDepth", var13);
			}

			this.mConnection.getClientCache().put(key.toString(), coll);
			if (timer != null) {
				timer.logDebug("getReportChildrenForParentToRelativeDepth", "");
			}
		} else {
			this.mLog.debug("getReportChildrenForParentToRelativeDepth", "obtained " + key + " from cache");
		}

		return coll;
	}

	public EntityList getBudgetHierarchyElement(int param1) {
		StringBuffer key = new StringBuffer();
		key.append("BudgetHierarchyElement");
		key.append('.');
		key.append(String.valueOf(param1));
		BudgetHierarchyElementELO coll = (BudgetHierarchyElementELO) this.mConnection.getClientCache().get(key.toString());
		if (coll == null) {
			Timer timer = this.mLog.isDebugEnabled() ? new Timer(this.mLog) : null;

			try {
				coll = (new ListSessionServer(this.getConnection())).getBudgetHierarchyElement(param1);
			} catch (Exception var6) {
				throw new RuntimeException("unable to get StructureElement BudgetHierarchyElement", var6);
			}

			this.mConnection.getClientCache().put(key.toString(), coll);
			if (timer != null) {
				timer.logDebug("getBudgetHierarchyElement", "");
			}
		} else {
			this.mLog.debug("getBudgetHierarchyElement", "obtained " + key + " from cache");
		}

		return coll;
	}

	public EntityList getBudgetLocationElementForModel(int param1, int param2) {
		StringBuffer key = new StringBuffer();
		key.append("BudgetLocationElementForModel");
		key.append('.');
		key.append(String.valueOf(param1));
		key.append('.');
		key.append(String.valueOf(param2));
		BudgetLocationElementForModelELO coll = (BudgetLocationElementForModelELO) this.mConnection.getClientCache().get(key.toString());
		if (coll == null) {
			Timer timer = this.mLog.isDebugEnabled() ? new Timer(this.mLog) : null;

			try {
				coll = (new ListSessionServer(this.getConnection())).getBudgetLocationElementForModel(param1, param2);
			} catch (Exception var7) {
				throw new RuntimeException("unable to get StructureElement BudgetLocationElementForModel", var7);
			}

			this.mConnection.getClientCache().put(key.toString(), coll);
			if (timer != null) {
				timer.logDebug("getBudgetLocationElementForModel", "");
			}
		} else {
			this.mLog.debug("getBudgetLocationElementForModel", "obtained " + key + " from cache");
		}

		return coll;
	}

	public EntityList getAllDimensions() {
		StringBuffer key = new StringBuffer();
		key.append("AllDimensions");
		AllDimensionsELO coll = (AllDimensionsELO) this.mConnection.getClientCache().get(key.toString());
		if (coll == null) {
			Timer timer = this.mLog.isDebugEnabled() ? new Timer(this.mLog) : null;

			try {
				coll = (new ListSessionServer(this.getConnection())).getAllDimensions();
			} catch (Exception var5) {
				throw new RuntimeException("unable to get Dimension AllDimensions", var5);
			}

			this.mConnection.getClientCache().put(key.toString(), coll);
			if (timer != null) {
				timer.logDebug("getAllDimensions", "");
			}
		} else {
			this.mLog.debug("getAllDimensions", "obtained " + key + " from cache");
		}

		return coll;
	}

	public EntityList getDimensionsForLoggedUser() {
		StringBuffer key = new StringBuffer();
		key.append("getDimensionsForLoggedUser");
		AllDimensionsELO coll = (AllDimensionsELO) this.mConnection.getClientCache().get(key.toString());
		if (coll == null) {
			Timer timer = this.mLog.isDebugEnabled() ? new Timer(this.mLog) : null;

			try {
				coll = (new ListSessionServer(this.getConnection())).getDimensionsForLoggedUser();
			} catch (Exception var5) {
				throw new RuntimeException("unable to get DimensionsForLoggedUser", var5);
			}

			this.mConnection.getClientCache().put(key.toString(), coll);
			if (timer != null) {
				timer.logDebug("getDimensionsForLoggedUser", "");
			}
		} else {
			this.mLog.debug("getDimensionsForLoggedUser", "obtained " + key + " from cache");
		}

		return coll;
	}

	public EntityList getAvailableDimensions() {
		Timer timer = this.mLog.isDebugEnabled() ? new Timer(this.mLog) : null;
		AvailableDimensionsELO coll = null;

		try {
			coll = (new ListSessionServer(this.getConnection())).getAvailableDimensions();
		} catch (Exception var4) {
			throw new RuntimeException("unable to get Dimension AvailableDimensions", var4);
		}

		if (timer != null) {
			timer.logDebug("getAvailableDimensions", "");
		}

		return coll;
	}

	public EntityList getImportableDimensions() {
		Timer timer = this.mLog.isDebugEnabled() ? new Timer(this.mLog) : null;
		ImportableDimensionsELO coll = null;

		try {
			coll = (new ListSessionServer(this.getConnection())).getImportableDimensions();
		} catch (Exception var4) {
			throw new RuntimeException("unable to get Dimension ImportableDimensions", var4);
		}

		if (timer != null) {
			timer.logDebug("getImportableDimensions", "");
		}

		return coll;
	}

	public EntityList getAllDimensionsForModel(int param1) {
		StringBuffer key = new StringBuffer();
		key.append("AllDimensionsForModel");
		key.append('.');
		key.append(String.valueOf(param1));
		AllDimensionsForModelELO coll = (AllDimensionsForModelELO) this.mConnection.getClientCache().get(key.toString());
		if (coll == null) {
			Timer timer = this.mLog.isDebugEnabled() ? new Timer(this.mLog) : null;

			try {
				coll = (new ListSessionServer(this.getConnection())).getAllDimensionsForModel(param1);
			} catch (Exception var6) {
				throw new RuntimeException("unable to get Dimension AllDimensionsForModel", var6);
			}

			this.mConnection.getClientCache().put(key.toString(), coll);
			if (timer != null) {
				timer.logDebug("getAllDimensionsForModel", "");
			}
		} else {
			this.mLog.debug("getAllDimensionsForModel", "obtained " + key + " from cache");
		}

		return coll;
	}

	public EntityList getAllDimensionElements() {
		StringBuffer key = new StringBuffer();
		key.append("AllDimensionElements");
		AllDimensionElementsELO coll = (AllDimensionElementsELO) this.mConnection.getClientCache().get(key.toString());
		if (coll == null) {
			Timer timer = this.mLog.isDebugEnabled() ? new Timer(this.mLog) : null;

			try {
				coll = (new ListSessionServer(this.getConnection())).getAllDimensionElements();
			} catch (Exception var5) {
				throw new RuntimeException("unable to get DimensionElement AllDimensionElements", var5);
			}

			this.mConnection.getClientCache().put(key.toString(), coll);
			if (timer != null) {
				timer.logDebug("getAllDimensionElements", "");
			}
		} else {
			this.mLog.debug("getAllDimensionElements", "obtained " + key + " from cache");
		}

		return coll;
	}

	public EntityList getAllDimensionElementsForDimension(int param1) {
		StringBuffer key = new StringBuffer();
		key.append("AllDimensionElementsForDimension");
		key.append('.');
		key.append(String.valueOf(param1));
		AllDimensionElementsForDimensionELO coll = (AllDimensionElementsForDimensionELO) this.mConnection.getClientCache().get(key.toString());
		if (coll == null) {
			Timer timer = this.mLog.isDebugEnabled() ? new Timer(this.mLog) : null;

			try {
				coll = (new ListSessionServer(this.getConnection())).getAllDimensionElementsForDimension(param1);
			} catch (Exception var6) {
				throw new RuntimeException("unable to get DimensionElement AllDimensionElementsForDimension", var6);
			}

			this.mConnection.getClientCache().put(key.toString(), coll);
			if (timer != null) {
				timer.logDebug("getAllDimensionElementsForDimension", "");
			}
		} else {
			this.mLog.debug("getAllDimensionElementsForDimension", "obtained " + key + " from cache");
		}

		return coll;
	}

	public EntityList getAllHierarchys() {
		StringBuffer key = new StringBuffer();
		key.append("AllHierarchys");
		AllHierarchysELO coll = (AllHierarchysELO) this.mConnection.getClientCache().get(key.toString());
		if (coll == null) {
			Timer timer = this.mLog.isDebugEnabled() ? new Timer(this.mLog) : null;

			try {
				coll = (new ListSessionServer(this.getConnection())).getAllHierarchys();
			} catch (Exception var5) {
				throw new RuntimeException("unable to get Hierarchy AllHierarchys", var5);
			}

			this.mConnection.getClientCache().put(key.toString(), coll);
			if (timer != null) {
				timer.logDebug("getAllHierarchys", "");
			}
		} else {
			this.mLog.debug("getAllHierarchys", "obtained " + key + " from cache");
		}

		return coll;
	}

	public EntityList getHierarchiesForLoggedUser() {
		StringBuffer key = new StringBuffer();
		key.append("getHierarchiesForLoggedUser");
		AllHierarchysELO coll = (AllHierarchysELO) this.mConnection.getClientCache().get(key.toString());
		if (coll == null) {
			Timer timer = this.mLog.isDebugEnabled() ? new Timer(this.mLog) : null;

			try {
				coll = (new ListSessionServer(this.getConnection())).getHierarchiesForLoggedUser();
			} catch (Exception var5) {
				throw new RuntimeException("unable to get Hierarchy HierarchiesForLoggedUser", var5);
			}

			this.mConnection.getClientCache().put(key.toString(), coll);
			if (timer != null) {
				timer.logDebug("getHierarchiesForLoggedUser", "");
			}
		} else {
			this.mLog.debug("getHierarchiesForLoggedUser", "obtained " + key + " from cache");
		}

		return coll;
	}

	public EntityList getSelectedHierarchys() {
		StringBuffer key = new StringBuffer();
		key.append("SelectedHierarchys");
		SelectedHierarchysELO coll = (SelectedHierarchysELO) this.mConnection.getClientCache().get(key.toString());
		if (coll == null) {
			Timer timer = this.mLog.isDebugEnabled() ? new Timer(this.mLog) : null;

			try {
				coll = (new ListSessionServer(this.getConnection())).getSelectedHierarchys();
			} catch (Exception var5) {
				throw new RuntimeException("unable to get Hierarchy SelectedHierarchys", var5);
			}

			this.mConnection.getClientCache().put(key.toString(), coll);
			if (timer != null) {
				timer.logDebug("getSelectedHierarchys", "");
			}
		} else {
			this.mLog.debug("getSelectedHierarchys", "obtained " + key + " from cache");
		}

		return coll;
	}

	public EntityList getImportableHierarchies(int param1) {
		Timer timer = this.mLog.isDebugEnabled() ? new Timer(this.mLog) : null;
		ImportableHierarchiesELO coll = null;

		try {
			coll = (new ListSessionServer(this.getConnection())).getImportableHierarchies(param1);
		} catch (Exception var5) {
			throw new RuntimeException("unable to get Hierarchy ImportableHierarchies", var5);
		}

		if (timer != null) {
			timer.logDebug("getImportableHierarchies", "");
		}

		return coll;
	}

	public EntityList getHierarcyDetailsFromDimId(int param1) {
		StringBuffer key = new StringBuffer();
		key.append("HierarcyDetailsFromDimId");
		key.append('.');
		key.append(String.valueOf(param1));
		HierarcyDetailsFromDimIdELO coll = (HierarcyDetailsFromDimIdELO) this.mConnection.getClientCache().get(key.toString());
		if (coll == null) {
			Timer timer = this.mLog.isDebugEnabled() ? new Timer(this.mLog) : null;

			try {
				coll = (new ListSessionServer(this.getConnection())).getHierarcyDetailsFromDimId(param1);
			} catch (Exception var6) {
				throw new RuntimeException("unable to get Hierarchy HierarcyDetailsFromDimId", var6);
			}

			this.mConnection.getClientCache().put(key.toString(), coll);
			if (timer != null) {
				timer.logDebug("getHierarcyDetailsFromDimId", "");
			}
		} else {
			this.mLog.debug("getHierarcyDetailsFromDimId", "obtained " + key + " from cache");
		}

		return coll;
	}

	public EntityList getHierarcyDetailsIncRootNodeFromDimId(int param1) {
		StringBuffer key = new StringBuffer();
		key.append("HierarcyDetailsIncRootNodeFromDimId");
		key.append('.');
		key.append(String.valueOf(param1));
		HierarcyDetailsIncRootNodeFromDimIdELO coll = (HierarcyDetailsIncRootNodeFromDimIdELO) this.mConnection.getClientCache().get(key.toString());
		if (coll == null) {
			Timer timer = this.mLog.isDebugEnabled() ? new Timer(this.mLog) : null;

			try {
				coll = (new ListSessionServer(this.getConnection())).getHierarcyDetailsIncRootNodeFromDimId(param1);
			} catch (Exception var6) {
				throw new RuntimeException("unable to get Hierarchy HierarcyDetailsIncRootNodeFromDimId", var6);
			}

			this.mConnection.getClientCache().put(key.toString(), coll);
			if (timer != null) {
				timer.logDebug("getHierarcyDetailsIncRootNodeFromDimId", "");
			}
		} else {
			this.mLog.debug("getHierarcyDetailsIncRootNodeFromDimId", "obtained " + key + " from cache");
		}

		return coll;
	}

	public EntityList getCalendarForModel(int param1) {
		StringBuffer key = new StringBuffer();
		key.append("CalendarForModel");
		key.append('.');
		key.append(String.valueOf(param1));
		CalendarForModelELO coll = (CalendarForModelELO) this.mConnection.getClientCache().get(key.toString());
		if (coll == null) {
			Timer timer = this.mLog.isDebugEnabled() ? new Timer(this.mLog) : null;

			try {
				coll = (new ListSessionServer(this.getConnection())).getCalendarForModel(param1);
			} catch (Exception var6) {
				throw new RuntimeException("unable to get Hierarchy CalendarForModel", var6);
			}

			this.mConnection.getClientCache().put(key.toString(), coll);
			if (timer != null) {
				timer.logDebug("getCalendarForModel", "");
			}
		} else {
			this.mLog.debug("getCalendarForModel", "obtained " + key + " from cache");
		}

		return coll;
	}

	public EntityList getCalendarForModelVisId(String param1) {
		StringBuffer key = new StringBuffer();
		key.append("CalendarForModelVisId");
		key.append('.');
		key.append(param1.toString());
		CalendarForModelVisIdELO coll = (CalendarForModelVisIdELO) this.mConnection.getClientCache().get(key.toString());
		if (coll == null) {
			Timer timer = this.mLog.isDebugEnabled() ? new Timer(this.mLog) : null;

			try {
				coll = (new ListSessionServer(this.getConnection())).getCalendarForModelVisId(param1);
			} catch (Exception var6) {
				throw new RuntimeException("unable to get Hierarchy CalendarForModelVisId", var6);
			}

			this.mConnection.getClientCache().put(key.toString(), coll);
			if (timer != null) {
				timer.logDebug("getCalendarForModelVisId", "");
			}
		} else {
			this.mLog.debug("getCalendarForModelVisId", "obtained " + key + " from cache");
		}

		return coll;
	}

	public EntityList getCalendarForFinanceCube(int param1) {
		StringBuffer key = new StringBuffer();
		key.append("CalendarForFinanceCube");
		key.append('.');
		key.append(String.valueOf(param1));
		CalendarForFinanceCubeELO coll = (CalendarForFinanceCubeELO) this.mConnection.getClientCache().get(key.toString());
		if (coll == null) {
			Timer timer = this.mLog.isDebugEnabled() ? new Timer(this.mLog) : null;

			try {
				coll = (new ListSessionServer(this.getConnection())).getCalendarForFinanceCube(param1);
			} catch (Exception var6) {
				throw new RuntimeException("unable to get Hierarchy CalendarForFinanceCube", var6);
			}

			this.mConnection.getClientCache().put(key.toString(), coll);
			if (timer != null) {
				timer.logDebug("getCalendarForFinanceCube", "");
			}
		} else {
			this.mLog.debug("getCalendarForFinanceCube", "obtained " + key + " from cache");
		}

		return coll;
	}

	public EntityList getHierarachyElement(int param1) {
		StringBuffer key = new StringBuffer();
		key.append("HierarachyElement");
		key.append('.');
		key.append(String.valueOf(param1));
		HierarachyElementELO coll = (HierarachyElementELO) this.mConnection.getClientCache().get(key.toString());
		if (coll == null) {
			Timer timer = this.mLog.isDebugEnabled() ? new Timer(this.mLog) : null;

			try {
				coll = (new ListSessionServer(this.getConnection())).getHierarachyElement(param1);
			} catch (Exception var6) {
				throw new RuntimeException("unable to get HierarchyElement HierarachyElement", var6);
			}

			this.mConnection.getClientCache().put(key.toString(), coll);
			if (timer != null) {
				timer.logDebug("getHierarachyElement", "");
			}
		} else {
			this.mLog.debug("getHierarachyElement", "obtained " + key + " from cache");
		}

		return coll;
	}

	public EntityList getAugHierarachyElement(int param1) {
		StringBuffer key = new StringBuffer();
		key.append("AugHierarachyElement");
		key.append('.');
		key.append(String.valueOf(param1));
		AugHierarachyElementELO coll = (AugHierarachyElementELO) this.mConnection.getClientCache().get(key.toString());
		if (coll == null) {
			Timer timer = this.mLog.isDebugEnabled() ? new Timer(this.mLog) : null;

			try {
				coll = (new ListSessionServer(this.getConnection())).getAugHierarachyElement(param1);
			} catch (Exception var6) {
				throw new RuntimeException("unable to get AugHierarchyElement AugHierarachyElement", var6);
			}

			this.mConnection.getClientCache().put(key.toString(), coll);
			if (timer != null) {
				timer.logDebug("getAugHierarachyElement", "");
			}
		} else {
			this.mLog.debug("getAugHierarachyElement", "obtained " + key + " from cache");
		}

		return coll;
	}

	public EntityList getAllFeedsForDimensionElement(int param1) {
		StringBuffer key = new StringBuffer();
		key.append("AllFeedsForDimensionElement");
		key.append('.');
		key.append(String.valueOf(param1));
		AllFeedsForDimensionElementELO coll = (AllFeedsForDimensionElementELO) this.mConnection.getClientCache().get(key.toString());
		if (coll == null) {
			Timer timer = this.mLog.isDebugEnabled() ? new Timer(this.mLog) : null;

			try {
				coll = (new ListSessionServer(this.getConnection())).getAllFeedsForDimensionElement(param1);
			} catch (Exception var6) {
				throw new RuntimeException("unable to get HierarchyElementFeed AllFeedsForDimensionElement", var6);
			}

			this.mConnection.getClientCache().put(key.toString(), coll);
			if (timer != null) {
				timer.logDebug("getAllFeedsForDimensionElement", "");
			}
		} else {
			this.mLog.debug("getAllFeedsForDimensionElement", "obtained " + key + " from cache");
		}

		return coll;
	}

	public EntityList getAllUsers() {
		StringBuffer key = new StringBuffer();
		key.append("AllUsers");
		AllUsersELO coll = (AllUsersELO) this.mConnection.getClientCache().get(key.toString());
		if (coll == null) {
			Timer timer = this.mLog.isDebugEnabled() ? new Timer(this.mLog) : null;

			try {
				coll = (new ListSessionServer(this.getConnection())).getAllUsers();
			} catch (Exception var5) {
				throw new RuntimeException("unable to get User AllUsers", var5);
			}

			this.mConnection.getClientCache().put(key.toString(), coll);
			if (timer != null) {
				timer.logDebug("getAllUsers", "");
			}
		} else {
			this.mLog.debug("getAllUsers", "obtained " + key + " from cache");
		}

		return coll;
	}
	
	public EntityList getAllRevisions() {
        StringBuffer key = new StringBuffer();
        key.append("AllRevisions");
        
        // I THINK THAT IT IS BAD IDEA TO CACHE THIS OBJECT BECAUSE IT WILL NOT SEE DATABASE CHANGES 
        
        //AllRevisionsELO coll = (AllRevisionsELO) this.mConnection.getClientCache().get(key.toString());
        //if (coll == null) {
            Timer timer = this.mLog.isDebugEnabled() ? new Timer(this.mLog) : null;
            AllRevisionsELO coll = null;
            try {
                coll = (new ListSessionServer(this.getConnection())).getAllRevisions();
            } catch (Exception var5) {
                throw new RuntimeException("unable to get Revision AllRevisions", var5);
            }

            //this.mConnection.getClientCache().put(key.toString(), coll);
            if (timer != null) {
                timer.logDebug("getAllRevisions", "");
            }
        //} else {
        //    this.mLog.debug("getAllRevisions", "obtained " + key + " from cache");
        //}

        return coll;
    }
	
	public EntityList getDashboardForms(Integer userId, boolean isAdmin){
	    AllDashboardsForUserELO coll = null;	    
	    Timer timer = this.mLog.isDebugEnabled() ? new Timer(this.mLog) : null;
        try {
            coll = (new ListSessionServer(this.getConnection())).getDashboardForms(userId, isAdmin);
        } catch (Exception var5) {
            throw new RuntimeException("unable to get Dashboard Forms", var5);
        }
        if (timer != null) {
            timer.logDebug("getDashboardForms", "");
        }
	    return coll;
	}
	
	public EntityList getSecurityStringsForUser(int param1) {
		StringBuffer key = new StringBuffer();
		key.append("SecurityStringsForUser");
		key.append('.');
		key.append(String.valueOf(param1));
		SecurityStringsForUserELO coll = (SecurityStringsForUserELO) this.mConnection.getClientCache().get(key.toString());
		if (coll == null) {
			Timer timer = this.mLog.isDebugEnabled() ? new Timer(this.mLog) : null;

			try {
				coll = (new ListSessionServer(this.getConnection())).getSecurityStringsForUser(param1);
			} catch (Exception var6) {
				throw new RuntimeException("unable to get User SecurityStringsForUser", var6);
			}

			this.mConnection.getClientCache().put(key.toString(), coll);
			if (timer != null) {
				timer.logDebug("getSecurityStringsForUser", "");
			}
		} else {
			this.mLog.debug("getSecurityStringsForUser", "obtained " + key + " from cache");
		}

		return coll;
	}

	public EntityList getAllUsersExport() {
		StringBuffer key = new StringBuffer();
		key.append("AllUsersExport");
		AllUsersExportELO coll = (AllUsersExportELO) this.mConnection.getClientCache().get(key.toString());
		if (coll == null) {
			Timer timer = this.mLog.isDebugEnabled() ? new Timer(this.mLog) : null;

			try {
				coll = (new ListSessionServer(this.getConnection())).getAllUsersExport();
			} catch (Exception var5) {
				throw new RuntimeException("unable to get User AllUsersExport", var5);
			}

			this.mConnection.getClientCache().put(key.toString(), coll);
			if (timer != null) {
				timer.logDebug("getAllUsersExport", "");
			}
		} else {
			this.mLog.debug("getAllUsersExport", "obtained " + key + " from cache");
		}

		return coll;
	}

	public EntityList getAllUserAttributes() {
		StringBuffer key = new StringBuffer();
		key.append("AllUserAttributes");
		AllUserAttributesELO coll = (AllUserAttributesELO) this.mConnection.getClientCache().get(key.toString());
		if (coll == null) {
			Timer timer = this.mLog.isDebugEnabled() ? new Timer(this.mLog) : null;

			try {
				coll = (new ListSessionServer(this.getConnection())).getAllUserAttributes();
			} catch (Exception var5) {
				throw new RuntimeException("unable to get User AllUserAttributes", var5);
			}

			this.mConnection.getClientCache().put(key.toString(), coll);
			if (timer != null) {
				timer.logDebug("getAllUserAttributes", "");
			}
		} else {
			this.mLog.debug("getAllUserAttributes", "obtained " + key + " from cache");
		}

		return coll;
	}

	public EntityList getAllNonDisabledUsers() {
		StringBuffer key = new StringBuffer();
		key.append("AllNonDisabledUsers");
		AllNonDisabledUsersELO coll = (AllNonDisabledUsersELO) this.mConnection.getClientCache().get(key.toString());
		if (coll == null) {
			Timer timer = this.mLog.isDebugEnabled() ? new Timer(this.mLog) : null;

			try {
				coll = (new ListSessionServer(this.getConnection())).getAllNonDisabledUsers();
			} catch (Exception var5) {
				throw new RuntimeException("unable to get User AllNonDisabledUsers", var5);
			}

			this.mConnection.getClientCache().put(key.toString(), coll);
			if (timer != null) {
				timer.logDebug("getAllNonDisabledUsers", "");
			}
		} else {
			this.mLog.debug("getAllNonDisabledUsers", "obtained " + key + " from cache");
		}

		return coll;
	}

	public EntityList getUserMessageAttributes() {
		StringBuffer key = new StringBuffer();
		key.append("UserMessageAttributes");
		UserMessageAttributesELO coll = (UserMessageAttributesELO) this.mConnection.getClientCache().get(key.toString());
		if (coll == null) {
			Timer timer = this.mLog.isDebugEnabled() ? new Timer(this.mLog) : null;

			try {
				coll = (new ListSessionServer(this.getConnection())).getUserMessageAttributes();
			} catch (Exception var5) {
				throw new RuntimeException("unable to get User UserMessageAttributes", var5);
			}

			this.mConnection.getClientCache().put(key.toString(), coll);
			if (timer != null) {
				timer.logDebug("getUserMessageAttributes", "");
			}
		} else {
			this.mLog.debug("getUserMessageAttributes", "obtained " + key + " from cache");
		}

		return coll;
	}

	public EntityList getUserMessageAttributesForId(int param1) {
		StringBuffer key = new StringBuffer();
		key.append("UserMessageAttributesForId");
		key.append('.');
		key.append(String.valueOf(param1));
		UserMessageAttributesForIdELO coll = (UserMessageAttributesForIdELO) this.mConnection.getClientCache().get(key.toString());
		if (coll == null) {
			Timer timer = this.mLog.isDebugEnabled() ? new Timer(this.mLog) : null;

			try {
				coll = (new ListSessionServer(this.getConnection())).getUserMessageAttributesForId(param1);
			} catch (Exception var6) {
				throw new RuntimeException("unable to get User UserMessageAttributesForId", var6);
			}

			this.mConnection.getClientCache().put(key.toString(), coll);
			if (timer != null) {
				timer.logDebug("getUserMessageAttributesForId", "");
			}
		} else {
			this.mLog.debug("getUserMessageAttributesForId", "obtained " + key + " from cache");
		}

		return coll;
	}

	public EntityList getUserMessageAttributesForName(String param1) {
		StringBuffer key = new StringBuffer();
		key.append("UserMessageAttributesForName");
		key.append('.');
		key.append(param1.toString());
		UserMessageAttributesForNameELO coll = (UserMessageAttributesForNameELO) this.mConnection.getClientCache().get(key.toString());
		if (coll == null) {
			Timer timer = this.mLog.isDebugEnabled() ? new Timer(this.mLog) : null;

			try {
				coll = (new ListSessionServer(this.getConnection())).getUserMessageAttributesForName(param1);
			} catch (Exception var6) {
				throw new RuntimeException("unable to get User UserMessageAttributesForName", var6);
			}

			this.mConnection.getClientCache().put(key.toString(), coll);
			if (timer != null) {
				timer.logDebug("getUserMessageAttributesForName", "");
			}
		} else {
			this.mLog.debug("getUserMessageAttributesForName", "obtained " + key + " from cache");
		}

		return coll;
	}

	public EntityList getFinanceSystemUserName(int param1) {
		StringBuffer key = new StringBuffer();
		key.append("FinanceSystemUserName");
		key.append('.');
		key.append(String.valueOf(param1));
		FinanceSystemUserNameELO coll = (FinanceSystemUserNameELO) this.mConnection.getClientCache().get(key.toString());
		if (coll == null) {
			Timer timer = this.mLog.isDebugEnabled() ? new Timer(this.mLog) : null;

			try {
				coll = (new ListSessionServer(this.getConnection())).getFinanceSystemUserName(param1);
			} catch (Exception var6) {
				throw new RuntimeException("unable to get User FinanceSystemUserName", var6);
			}

			this.mConnection.getClientCache().put(key.toString(), coll);
			if (timer != null) {
				timer.logDebug("getFinanceSystemUserName", "");
			}
		} else {
			this.mLog.debug("getFinanceSystemUserName", "obtained " + key + " from cache");
		}

		return coll;
	}

	public EntityList getUsersWithSecurityString(String param1) {
		StringBuffer key = new StringBuffer();
		key.append("UsersWithSecurityString");
		key.append('.');
		key.append(param1.toString());
		UsersWithSecurityStringELO coll = (UsersWithSecurityStringELO) this.mConnection.getClientCache().get(key.toString());
		if (coll == null) {
			Timer timer = this.mLog.isDebugEnabled() ? new Timer(this.mLog) : null;

			try {
				coll = (new ListSessionServer(this.getConnection())).getUsersWithSecurityString(param1);
			} catch (Exception var6) {
				throw new RuntimeException("unable to get User UsersWithSecurityString", var6);
			}

			this.mConnection.getClientCache().put(key.toString(), coll);
			if (timer != null) {
				timer.logDebug("getUsersWithSecurityString", "");
			}
		} else {
			this.mLog.debug("getUsersWithSecurityString", "obtained " + key + " from cache");
		}

		return coll;
	}

	public EntityList getAllRolesForUsers() {
		StringBuffer key = new StringBuffer();
		key.append("AllRolesForUsers");
		AllRolesForUsersELO coll = (AllRolesForUsersELO) this.mConnection.getClientCache().get(key.toString());
		if (coll == null) {
			Timer timer = this.mLog.isDebugEnabled() ? new Timer(this.mLog) : null;

			try {
				coll = (new ListSessionServer(this.getConnection())).getAllRolesForUsers();
			} catch (Exception var5) {
				throw new RuntimeException("unable to get UserRole AllRolesForUsers", var5);
			}

			this.mConnection.getClientCache().put(key.toString(), coll);
			if (timer != null) {
				timer.logDebug("getAllRolesForUsers", "");
			}
		} else {
			this.mLog.debug("getAllRolesForUsers", "obtained " + key + " from cache");
		}

		return coll;
	}

	public EntityList getAllRoles() {
		StringBuffer key = new StringBuffer();
		key.append("AllRoles");
		AllRolesELO coll = (AllRolesELO) this.mConnection.getClientCache().get(key.toString());
		if (coll == null) {
			Timer timer = this.mLog.isDebugEnabled() ? new Timer(this.mLog) : null;

			try {
				coll = (new ListSessionServer(this.getConnection())).getAllRoles();
			} catch (Exception var5) {
				throw new RuntimeException("unable to get Role AllRoles", var5);
			}

			this.mConnection.getClientCache().put(key.toString(), coll);
			if (timer != null) {
				timer.logDebug("getAllRoles", "");
			}
		} else {
			this.mLog.debug("getAllRoles", "obtained " + key + " from cache");
		}

		return coll;
	}

	public EntityList getAllHiddenRoles() {
		StringBuffer key = new StringBuffer();
		key.append("AllHiddenRoles");
		EntityList coll = (EntityList) this.mConnection.getClientCache().get(key.toString());
		if (coll == null) {
			Timer timer = this.mLog.isDebugEnabled() ? new Timer(this.mLog) : null;

			try {
				coll = (new ListSessionServer(this.getConnection())).getAllHiddenRoles();
			} catch (Exception var5) {
				throw new RuntimeException("unable to get Role All Hidden Roles", var5);
			}

			this.mConnection.getClientCache().put(key.toString(), coll);
			if (timer != null) {
				timer.logDebug("getAllHiddenRoles", "");
			}
		} else {
			this.mLog.debug("getAllHiddenRoles", "obtained " + key + " from cache");
		}

		return coll;
	}

	public EntityList getAllRolesForUser(int param1) {
		StringBuffer key = new StringBuffer();
		key.append("AllRolesForUser");
		key.append('.');
		key.append(String.valueOf(param1));
		AllRolesForUserELO coll = (AllRolesForUserELO) this.mConnection.getClientCache().get(key.toString());
		if (coll == null) {
			Timer timer = this.mLog.isDebugEnabled() ? new Timer(this.mLog) : null;

			try {
				coll = (new ListSessionServer(this.getConnection())).getAllRolesForUser(param1);
			} catch (Exception var6) {
				throw new RuntimeException("unable to get Role AllRolesForUser", var6);
			}

			this.mConnection.getClientCache().put(key.toString(), coll);
			if (timer != null) {
				timer.logDebug("getAllRolesForUser", "");
			}
		} else {
			this.mLog.debug("getAllRolesForUser", "obtained " + key + " from cache");
		}

		return coll;
	}

	public EntityList getAllHiddenRolesForUser(int param1) {
		StringBuffer key = new StringBuffer();
		key.append("AllHiddenRolesForUser");
		key.append('.');
		key.append(String.valueOf(param1));
		AllRolesForUserELO coll = (AllRolesForUserELO) this.mConnection.getClientCache().get(key.toString());
		if (coll == null) {
			Timer timer = this.mLog.isDebugEnabled() ? new Timer(this.mLog) : null;

			try {
				coll = (new ListSessionServer(this.getConnection())).getAllHiddenRolesForUser(param1);
			} catch (Exception var6) {
				throw new RuntimeException("unable to get Role AllHiddenRolesForUser", var6);
			}

			this.mConnection.getClientCache().put(key.toString(), coll);
			if (timer != null) {
				timer.logDebug("getAllHiddenRolesForUser", "");
			}
		} else {
			this.mLog.debug("getAllHiddenRolesForUser", "obtained " + key + " from cache");
		}

		return coll;
	}

	public EntityList getAllSecurityRoles() {
		StringBuffer key = new StringBuffer();
		key.append("AllSecurityRoles");
		AllSecurityRolesELO coll = (AllSecurityRolesELO) this.mConnection.getClientCache().get(key.toString());
		if (coll == null) {
			Timer timer = this.mLog.isDebugEnabled() ? new Timer(this.mLog) : null;

			try {
				coll = (new ListSessionServer(this.getConnection())).getAllSecurityRoles();
			} catch (Exception var5) {
				throw new RuntimeException("unable to get RoleSecurity AllSecurityRoles", var5);
			}

			this.mConnection.getClientCache().put(key.toString(), coll);
			if (timer != null) {
				timer.logDebug("getAllSecurityRoles", "");
			}
		} else {
			this.mLog.debug("getAllSecurityRoles", "obtained " + key + " from cache");
		}

		return coll;
	}

	public EntityList getAllSecurityRolesForRole(int param1) {
		Timer timer = this.mLog.isDebugEnabled() ? new Timer(this.mLog) : null;
		AllSecurityRolesForRoleELO coll = null;

		try {
			coll = (new ListSessionServer(this.getConnection())).getAllSecurityRolesForRole(param1);
		} catch (Exception var5) {
			throw new RuntimeException("unable to get RoleSecurity AllSecurityRolesForRole", var5);
		}

		if (timer != null) {
			timer.logDebug("getAllSecurityRolesForRole", "");
		}

		return coll;
	}

	public EntityList getUserPreferencesForUser(int param1) {
		StringBuffer key = new StringBuffer();
		key.append("UserPreferencesForUser");
		key.append('.');
		key.append(String.valueOf(param1));
		UserPreferencesForUserELO coll = (UserPreferencesForUserELO) this.mConnection.getClientCache().get(key.toString());
		if (coll == null) {
			Timer timer = this.mLog.isDebugEnabled() ? new Timer(this.mLog) : null;

			try {
				coll = (new ListSessionServer(this.getConnection())).getUserPreferencesForUser(param1);
			} catch (Exception var6) {
				throw new RuntimeException("unable to get UserPreference UserPreferencesForUser", var6);
			}

			this.mConnection.getClientCache().put(key.toString(), coll);
			if (timer != null) {
				timer.logDebug("getUserPreferencesForUser", "");
			}
		} else {
			this.mLog.debug("getUserPreferencesForUser", "obtained " + key + " from cache");
		}

		return coll;
	}

	public EntityList getAllBudgetInstructions() {
		StringBuffer key = new StringBuffer();
		key.append("AllBudgetInstructions");
		AllBudgetInstructionsELO coll = (AllBudgetInstructionsELO) this.mConnection.getClientCache().get(key.toString());
		if (coll == null) {
			Timer timer = this.mLog.isDebugEnabled() ? new Timer(this.mLog) : null;

			try {
				coll = (new ListSessionServer(this.getConnection())).getAllBudgetInstructions();
			} catch (Exception var5) {
				throw new RuntimeException("unable to get BudgetInstruction AllBudgetInstructions", var5);
			}

			this.mConnection.getClientCache().put(key.toString(), coll);
			if (timer != null) {
				timer.logDebug("getAllBudgetInstructions", "");
			}
		} else {
			this.mLog.debug("getAllBudgetInstructions", "obtained " + key + " from cache");
		}

		return coll;
	}

	public EntityList getAllBudgetInstructionsWeb() {
		StringBuffer key = new StringBuffer();
		key.append("AllBudgetInstructionsWeb");
		AllBudgetInstructionsWebELO coll = (AllBudgetInstructionsWebELO) this.mConnection.getClientCache().get(key.toString());
		if (coll == null) {
			Timer timer = this.mLog.isDebugEnabled() ? new Timer(this.mLog) : null;

			try {
				coll = (new ListSessionServer(this.getConnection())).getAllBudgetInstructionsWeb();
			} catch (Exception var5) {
				throw new RuntimeException("unable to get BudgetInstruction AllBudgetInstructionsWeb", var5);
			}

			this.mConnection.getClientCache().put(key.toString(), coll);
			if (timer != null) {
				timer.logDebug("getAllBudgetInstructionsWeb", "");
			}
		} else {
			this.mLog.debug("getAllBudgetInstructionsWeb", "obtained " + key + " from cache");
		}

		return coll;
	}

	public EntityList getAllBudgetInstructionsForModel(int param1) {
		StringBuffer key = new StringBuffer();
		key.append("AllBudgetInstructionsForModel");
		key.append('.');
		key.append(String.valueOf(param1));
		AllBudgetInstructionsForModelELO coll = (AllBudgetInstructionsForModelELO) this.mConnection.getClientCache().get(key.toString());
		if (coll == null) {
			Timer timer = this.mLog.isDebugEnabled() ? new Timer(this.mLog) : null;

			try {
				coll = (new ListSessionServer(this.getConnection())).getAllBudgetInstructionsForModel(param1);
			} catch (Exception var6) {
				throw new RuntimeException("unable to get BudgetInstruction AllBudgetInstructionsForModel", var6);
			}

			this.mConnection.getClientCache().put(key.toString(), coll);
			if (timer != null) {
				timer.logDebug("getAllBudgetInstructionsForModel", "");
			}
		} else {
			this.mLog.debug("getAllBudgetInstructionsForModel", "obtained " + key + " from cache");
		}

		return coll;
	}

	public EntityList getAllBudgetInstructionsForCycle(int param1) {
		StringBuffer key = new StringBuffer();
		key.append("AllBudgetInstructionsForCycle");
		key.append('.');
		key.append(String.valueOf(param1));
		AllBudgetInstructionsForCycleELO coll = (AllBudgetInstructionsForCycleELO) this.mConnection.getClientCache().get(key.toString());
		if (coll == null) {
			Timer timer = this.mLog.isDebugEnabled() ? new Timer(this.mLog) : null;

			try {
				coll = (new ListSessionServer(this.getConnection())).getAllBudgetInstructionsForCycle(param1);
			} catch (Exception var6) {
				throw new RuntimeException("unable to get BudgetInstruction AllBudgetInstructionsForCycle", var6);
			}

			this.mConnection.getClientCache().put(key.toString(), coll);
			if (timer != null) {
				timer.logDebug("getAllBudgetInstructionsForCycle", "");
			}
		} else {
			this.mLog.debug("getAllBudgetInstructionsForCycle", "obtained " + key + " from cache");
		}

		return coll;
	}

	public EntityList getAllBudgetInstructionsForLocation(int param1) {
		StringBuffer key = new StringBuffer();
		key.append("AllBudgetInstructionsForLocation");
		key.append('.');
		key.append(String.valueOf(param1));
		AllBudgetInstructionsForLocationELO coll = (AllBudgetInstructionsForLocationELO) this.mConnection.getClientCache().get(key.toString());
		if (coll == null) {
			Timer timer = this.mLog.isDebugEnabled() ? new Timer(this.mLog) : null;

			try {
				coll = (new ListSessionServer(this.getConnection())).getAllBudgetInstructionsForLocation(param1);
			} catch (Exception var6) {
				throw new RuntimeException("unable to get BudgetInstruction AllBudgetInstructionsForLocation", var6);
			}

			this.mConnection.getClientCache().put(key.toString(), coll);
			if (timer != null) {
				timer.logDebug("getAllBudgetInstructionsForLocation", "");
			}
		} else {
			this.mLog.debug("getAllBudgetInstructionsForLocation", "obtained " + key + " from cache");
		}

		return coll;
	}

	public EntityList getAllBudgetInstructionAssignments() {
		StringBuffer key = new StringBuffer();
		key.append("AllBudgetInstructionAssignments");
		AllBudgetInstructionAssignmentsELO coll = (AllBudgetInstructionAssignmentsELO) this.mConnection.getClientCache().get(key.toString());
		if (coll == null) {
			Timer timer = this.mLog.isDebugEnabled() ? new Timer(this.mLog) : null;

			try {
				coll = (new ListSessionServer(this.getConnection())).getAllBudgetInstructionAssignments();
			} catch (Exception var5) {
				throw new RuntimeException("unable to get BudgetInstructionAssignment AllBudgetInstructionAssignments", var5);
			}

			this.mConnection.getClientCache().put(key.toString(), coll);
			if (timer != null) {
				timer.logDebug("getAllBudgetInstructionAssignments", "");
			}
		} else {
			this.mLog.debug("getAllBudgetInstructionAssignments", "obtained " + key + " from cache");
		}

		return coll;
	}

	public EntityList getAllWebTasks() {
		Timer timer = this.mLog.isDebugEnabled() ? new Timer(this.mLog) : null;
		AllWebTasksELO coll = null;

		try {
			coll = (new ListSessionServer(this.getConnection())).getAllWebTasks();
		} catch (Exception var4) {
			throw new RuntimeException("unable to get Task AllWebTasks", var4);
		}

		if (timer != null) {
			timer.logDebug("getAllWebTasks", "");
		}

		return coll;
	}

	public EntityList getAllWebTasksForUser(String param1) {
		Timer timer = this.mLog.isDebugEnabled() ? new Timer(this.mLog) : null;
		AllWebTasksForUserELO coll = null;

		try {
			coll = (new ListSessionServer(this.getConnection())).getAllWebTasksForUser(param1);
		} catch (Exception var5) {
			throw new RuntimeException("unable to get Task AllWebTasksForUser", var5);
		}

		if (timer != null) {
			timer.logDebug("getAllWebTasksForUser", "");
		}

		return coll;
	}

	public EntityList getWebTasksDetails(int param1) {
		Timer timer = this.mLog.isDebugEnabled() ? new Timer(this.mLog) : null;
		WebTasksDetailsELO coll = null;

		try {
			coll = (new ListSessionServer(this.getConnection())).getWebTasksDetails(param1);
		} catch (Exception var5) {
			throw new RuntimeException("unable to get Task WebTasksDetails", var5);
		}

		if (timer != null) {
			timer.logDebug("getWebTasksDetails", "");
		}

		return coll;
	}

	public EntityList getAllSystemPropertys() {
		StringBuffer key = new StringBuffer();
		key.append("AllSystemPropertys");
		AllSystemPropertysELO coll = (AllSystemPropertysELO) this.mConnection.getClientCache().get(key.toString());
		if (coll == null) {
			Timer timer = this.mLog.isDebugEnabled() ? new Timer(this.mLog) : null;

			try {
				coll = (new ListSessionServer(this.getConnection())).getAllSystemPropertys();
			} catch (Exception var5) {
				throw new RuntimeException("unable to get SystemProperty AllSystemPropertys", var5);
			}

			this.mConnection.getClientCache().put(key.toString(), coll);
			if (timer != null) {
				timer.logDebug("getAllSystemPropertys", "");
			}
		} else {
			this.mLog.debug("getAllSystemPropertys", "obtained " + key + " from cache");
		}

		return coll;
	}

	public EntityList getAllSystemPropertysUncached() {
		Timer timer = this.mLog.isDebugEnabled() ? new Timer(this.mLog) : null;
		AllSystemPropertysUncachedELO coll = null;

		try {
			coll = (new ListSessionServer(this.getConnection())).getAllSystemPropertysUncached();
		} catch (Exception var4) {
			throw new RuntimeException("unable to get SystemProperty AllSystemPropertysUncached", var4);
		}

		if (timer != null) {
			timer.logDebug("getAllSystemPropertysUncached", "");
		}

		return coll;
	}

	public EntityList getAllMailProps() {
		StringBuffer key = new StringBuffer();
		key.append("AllMailProps");
		AllMailPropsELO coll = (AllMailPropsELO) this.mConnection.getClientCache().get(key.toString());
		if (coll == null) {
			Timer timer = this.mLog.isDebugEnabled() ? new Timer(this.mLog) : null;

			try {
				coll = (new ListSessionServer(this.getConnection())).getAllMailProps();
			} catch (Exception var5) {
				throw new RuntimeException("unable to get SystemProperty AllMailProps", var5);
			}

			this.mConnection.getClientCache().put(key.toString(), coll);
			if (timer != null) {
				timer.logDebug("getAllMailProps", "");
			}
		} else {
			this.mLog.debug("getAllMailProps", "obtained " + key + " from cache");
		}

		return coll;
	}

	public EntityList getSystemProperty(String param1) {
		StringBuffer key = new StringBuffer();
		key.append("SystemProperty");
		key.append('.');
		key.append(param1.toString());
		SystemPropertyELO coll = (SystemPropertyELO) this.mConnection.getClientCache().get(key.toString());
		if (coll == null) {
			Timer timer = this.mLog.isDebugEnabled() ? new Timer(this.mLog) : null;

			try {
				coll = (new ListSessionServer(this.getConnection())).getSystemProperty(param1);
			} catch (Exception var6) {
				throw new RuntimeException("unable to get SystemProperty SystemProperty", var6);
			}

			this.mConnection.getClientCache().put(key.toString(), coll);
			if (timer != null) {
				timer.logDebug("getSystemProperty", "");
			}
		} else {
			this.mLog.debug("getSystemProperty", "obtained " + key + " from cache");
		}

		return coll;
	}

	public EntityList getWebSystemProperty(String param1) {
		Timer timer = this.mLog.isDebugEnabled() ? new Timer(this.mLog) : null;
		WebSystemPropertyELO coll = null;

		try {
			coll = (new ListSessionServer(this.getConnection())).getWebSystemProperty(param1);
		} catch (Exception var5) {
			throw new RuntimeException("unable to get SystemProperty WebSystemProperty", var5);
		}

		if (timer != null) {
			timer.logDebug("getWebSystemProperty", "");
		}

		return coll;
	}

	public EntityList getAllXmlForms() {
		StringBuffer key = new StringBuffer();
		key.append("AllXmlForms");
		AllXmlFormsELO coll = (AllXmlFormsELO) this.mConnection.getClientCache().get(key.toString());
		if (coll == null) {
			Timer timer = this.mLog.isDebugEnabled() ? new Timer(this.mLog) : null;

			try {
				coll = (new ListSessionServer(this.getConnection())).getAllXmlForms();
			} catch (Exception var5) {
				throw new RuntimeException("unable to get XmlForm AllXmlForms", var5);
			}

			this.mConnection.getClientCache().put(key.toString(), coll);
			if (timer != null) {
				timer.logDebug("getAllXmlForms", "");
			}
		} else {
			this.mLog.debug("getAllXmlForms", "obtained " + key + " from cache");
		}

		return coll;
	}

	public EntityList getAllXmlFormsForLoggedUser() {
		StringBuffer key = new StringBuffer();
		key.append("AllXmlFormsForLoggedUser");
		AllXmlFormsELO coll = (AllXmlFormsELO) this.mConnection.getClientCache().get(key.toString());
		if (coll == null) {
			Timer timer = this.mLog.isDebugEnabled() ? new Timer(this.mLog) : null;

			try {
				coll = (new ListSessionServer(this.getConnection())).getAllXmlFormsForLoggedUser();
			} catch (Exception var5) {
				throw new RuntimeException("unable to get XmlForm AllXmlFormsForLoggedUser", var5);
			}

			this.mConnection.getClientCache().put(key.toString(), coll);
			if (timer != null) {
				timer.logDebug("getAllXmlFormsForLoggedUser", "");
			}
		} else {
			this.mLog.debug("getAllXmlFormsForLoggedUser", "obtained " + key + " from cache");
		}

		return coll;
	}

	public EntityList getAllFinXmlForms() {
		StringBuffer key = new StringBuffer();
		key.append("AllFinXmlForms");
		AllFinXmlFormsELO coll = (AllFinXmlFormsELO) this.mConnection.getClientCache().get(key.toString());
		if (coll == null) {
			Timer timer = this.mLog.isDebugEnabled() ? new Timer(this.mLog) : null;

			try {
				coll = (new ListSessionServer(this.getConnection())).getAllFinXmlForms();
			} catch (Exception var5) {
				throw new RuntimeException("unable to get XmlForm AllFinXmlForms", var5);
			}

			this.mConnection.getClientCache().put(key.toString(), coll);
			if (timer != null) {
				timer.logDebug("getAllFinXmlForms", "");
			}
		} else {
			this.mLog.debug("getAllFinXmlForms", "obtained " + key + " from cache");
		}

		return coll;
	}

	public EntityList getAllFFXmlForms() {
		StringBuffer key = new StringBuffer();
		key.append("AllFFXmlForms");
		AllFFXmlFormsELO coll = (AllFFXmlFormsELO) this.mConnection.getClientCache().get(key.toString());
		if (coll == null) {
			Timer timer = this.mLog.isDebugEnabled() ? new Timer(this.mLog) : null;

			try {
				coll = (new ListSessionServer(this.getConnection())).getAllFFXmlForms();
			} catch (Exception var5) {
				throw new RuntimeException("unable to get XmlForm AllFFXmlForms", var5);
			}

			this.mConnection.getClientCache().put(key.toString(), coll);
			if (timer != null) {
				timer.logDebug("getAllFFXmlForms", "");
			}
		} else {
			this.mLog.debug("getAllFFXmlForms", "obtained " + key + " from cache");
		}

		return coll;
	}

	public EntityList getAllFFXmlFormsForLoggedUser() {

		EntityList coll;
		try {
			coll = (new ListSessionServer(this.getConnection())).getAllFFXmlFormsForLoggedUser();
		} catch (Exception var5) {
			throw new RuntimeException("unable to get XmlForm AllFFXmlFormsForLoggedUser", var5);
		}

		return coll;
	}

	public EntityList getAllXcellXmlForms() {

		AllXcellXmlFormsELO coll = null;
		Timer timer = this.mLog.isDebugEnabled() ? new Timer(this.mLog) : null;

		try {
			coll = (new ListSessionServer(this.getConnection())).getAllXcellXmlForms();
		} catch (Exception var5) {
			throw new RuntimeException("unable to get XmlForm AllXcellXmlForms", var5);
		}
		if (timer != null) {
			timer.logDebug("getAllXcellXmlForms", "");
		}

		return coll;
	}

	public EntityList getAllXcellXmlFormsForLoggedUser() {
		EntityList coll;
		try {
			coll = (new ListSessionServer(this.getConnection())).getAllXcellXmlFormsForLoggedUser();
		} catch (Exception var5) {
			throw new RuntimeException("unable to get XmlForm AllXcellXmlFormsForLoggedUser", var5);
		}
		return coll;
	}

	public EntityList getAllMassVirementXmlForms() {
		StringBuffer key = new StringBuffer();
		key.append("AllMassVirementXmlForms");
		AllMassVirementXmlFormsELO coll = (AllMassVirementXmlFormsELO) this.mConnection.getClientCache().get(key.toString());
		if (coll == null) {
			Timer timer = this.mLog.isDebugEnabled() ? new Timer(this.mLog) : null;

			try {
				coll = (new ListSessionServer(this.getConnection())).getAllMassVirementXmlForms();
			} catch (Exception var5) {
				throw new RuntimeException("unable to get XmlForm AllMassVirementXmlForms", var5);
			}

			this.mConnection.getClientCache().put(key.toString(), coll);
			if (timer != null) {
				timer.logDebug("getAllMassVirementXmlForms", "");
			}
		} else {
			this.mLog.debug("getAllMassVirementXmlForms", "obtained " + key + " from cache");
		}

		return coll;
	}

	public EntityList getAllMassVirementXmlFormsForLoggedUser() {
		AllMassVirementXmlFormsELO coll;
		Timer timer = this.mLog.isDebugEnabled() ? new Timer(this.mLog) : null;

		try {
			coll = (new ListSessionServer(this.getConnection())).getAllMassVirementXmlFormsForLoggedUser();
		} catch (Exception var5) {
			throw new RuntimeException("unable to get XmlForm AllMassVirementXmlFormsForLoggedUser", var5);
		}

		return coll;
	}

	public EntityList getAllFinanceXmlForms() {
		StringBuffer key = new StringBuffer();
		key.append("AllFinanceXmlForms");
		AllFinanceXmlFormsELO coll = (AllFinanceXmlFormsELO) this.mConnection.getClientCache().get(key.toString());
		if (coll == null) {
			Timer timer = this.mLog.isDebugEnabled() ? new Timer(this.mLog) : null;

			try {
				coll = (new ListSessionServer(this.getConnection())).getAllFinanceXmlForms();
			} catch (Exception var5) {
				throw new RuntimeException("unable to get XmlForm AllFinanceXmlForms", var5);
			}

			this.mConnection.getClientCache().put(key.toString(), coll);
			if (timer != null) {
				timer.logDebug("getAllFinanceXmlForms", "");
			}
		} else {
			this.mLog.debug("getAllFinanceXmlForms", "obtained " + key + " from cache");
		}

		return coll;
	}

	public EntityList getAllFinanceAndFlatForms() {
		StringBuffer key = new StringBuffer();
		key.append("AllFinanceAndFlatForms");
		AllFinanceAndFlatFormsELO coll = (AllFinanceAndFlatFormsELO) this.mConnection.getClientCache().get(key.toString());
		if (coll == null) {
			Timer timer = this.mLog.isDebugEnabled() ? new Timer(this.mLog) : null;

			try {
				coll = (new ListSessionServer(this.getConnection())).getAllFinanceAndFlatForms();
			} catch (Exception var5) {
				throw new RuntimeException("unable to get XmlForm AllFinanceAndFlatForms", var5);
			}

			this.mConnection.getClientCache().put(key.toString(), coll);
			if (timer != null) {
				timer.logDebug("getAllFinanceAndFlatForms", "");
			}
		} else {
			this.mLog.debug("getAllFinanceAndFlatForms", "obtained " + key + " from cache");
		}

		return coll;
	}

	public EntityList getAllFinanceXmlFormsForModel(int param1) {
		StringBuffer key = new StringBuffer();
		key.append("AllFinanceXmlFormsForModel");
		key.append('.');
		key.append(String.valueOf(param1));
		AllFinanceXmlFormsForModelELO coll = (AllFinanceXmlFormsForModelELO) this.mConnection.getClientCache().get(key.toString());
		if (coll == null) {
			Timer timer = this.mLog.isDebugEnabled() ? new Timer(this.mLog) : null;

			try {
				coll = (new ListSessionServer(this.getConnection())).getAllFinanceXmlFormsForModel(param1);
			} catch (Exception var6) {
				throw new RuntimeException("unable to get XmlForm AllFinanceXmlFormsForModel", var6);
			}

			this.mConnection.getClientCache().put(key.toString(), coll);
			if (timer != null) {
				timer.logDebug("getAllFinanceXmlFormsForModel", "");
			}
		} else {
			this.mLog.debug("getAllFinanceXmlFormsForModel", "obtained " + key + " from cache");
		}

		return coll;
	}

	public EntityList getAllFinanceAndFlatFormsForModel(int param1) {
		StringBuffer key = new StringBuffer();
		key.append("AllFinanceAndFlatFormsForModel");
		key.append('.');
		key.append(String.valueOf(param1));
		AllFinanceAndFlatFormsForModelELO coll = (AllFinanceAndFlatFormsForModelELO) this.mConnection.getClientCache().get(key.toString());
		if (coll == null) {
			Timer timer = this.mLog.isDebugEnabled() ? new Timer(this.mLog) : null;

			try {
				coll = (new ListSessionServer(this.getConnection())).getAllFinanceAndFlatFormsForModel(param1);
			} catch (Exception var6) {
				throw new RuntimeException("unable to get XmlForm AllFinanceAndFlatFormsForModel", var6);
			}

			this.mConnection.getClientCache().put(key.toString(), coll);
			if (timer != null) {
				timer.logDebug("getAllFinanceAndFlatFormsForModel", "");
			}
		} else {
			this.mLog.debug("getAllFinanceAndFlatFormsForModel", "obtained " + key + " from cache");
		}

		return coll;
	}

	public EntityList getAllFinanceXmlFormsAndDataTypesForModel(int param1) {
		StringBuffer key = new StringBuffer();
		key.append("AllFinanceXmlFormsAndDataTypesForModel");
		key.append('.');
		key.append(String.valueOf(param1));
		AllFinanceXmlFormsAndDataTypesForModelELO coll = (AllFinanceXmlFormsAndDataTypesForModelELO) this.mConnection.getClientCache().get(key.toString());
		if (coll == null) {
			Timer timer = this.mLog.isDebugEnabled() ? new Timer(this.mLog) : null;

			try {
				coll = (new ListSessionServer(this.getConnection())).getAllFinanceXmlFormsAndDataTypesForModel(param1);
			} catch (Exception var6) {
				throw new RuntimeException("unable to get XmlForm AllFinanceXmlFormsAndDataTypesForModel", var6);
			}

			this.mConnection.getClientCache().put(key.toString(), coll);
			if (timer != null) {
				timer.logDebug("getAllFinanceXmlFormsAndDataTypesForModel", "");
			}
		} else {
			this.mLog.debug("getAllFinanceXmlFormsAndDataTypesForModel", "obtained " + key + " from cache");
		}

		return coll;
	}

	public EntityList getAllXmlFormsForModel(int modelId) {
		StringBuffer key = new StringBuffer();
		key.append("AllXmlFormsForModel");
		key.append('.');
		key.append(String.valueOf(modelId));
		AllXmlFormsELO coll = (AllXmlFormsELO) this.mConnection.getClientCache().get(key.toString());
		if (coll == null) {
			Timer timer = this.mLog.isDebugEnabled() ? new Timer(this.mLog) : null;

			try {
				coll = (new ListSessionServer(this.getConnection())).getAllXmlFormsForModel(modelId);
			} catch (Exception e) {
				throw new RuntimeException("unable to get XmlForm AllXmlFormsForModel", e);
			}

			this.mConnection.getClientCache().put(key.toString(), coll);
			if (timer != null) {
				timer.logDebug("getAllXmlFormsForModel", "");
			}
		} else {
			this.mLog.debug("getAllXmlFormsForModel", "obtained " + key + " from cache");
		}

		return coll;
	}

	public EntityList getAllFixedXMLForms() {
		StringBuffer key = new StringBuffer();
		key.append("AllFixedXMLForms");
		AllFixedXMLFormsELO coll = (AllFixedXMLFormsELO) this.mConnection.getClientCache().get(key.toString());
		if (coll == null) {
			Timer timer = this.mLog.isDebugEnabled() ? new Timer(this.mLog) : null;

			try {
				coll = (new ListSessionServer(this.getConnection())).getAllFixedXMLForms();
			} catch (Exception var5) {
				throw new RuntimeException("unable to get XmlForm AllFixedXMLForms", var5);
			}

			this.mConnection.getClientCache().put(key.toString(), coll);
			if (timer != null) {
				timer.logDebug("getAllFixedXMLForms", "");
			}
		} else {
			this.mLog.debug("getAllFixedXMLForms", "obtained " + key + " from cache");
		}

		return coll;
	}

	public EntityList getAllDynamicXMLForms() {
		StringBuffer key = new StringBuffer();
		key.append("AllDynamicXMLForms");
		AllDynamicXMLFormsELO coll = (AllDynamicXMLFormsELO) this.mConnection.getClientCache().get(key.toString());
		if (coll == null) {
			Timer timer = this.mLog.isDebugEnabled() ? new Timer(this.mLog) : null;

			try {
				coll = (new ListSessionServer(this.getConnection())).getAllDynamicXMLForms();
			} catch (Exception var5) {
				throw new RuntimeException("unable to get XmlForm AllDynamicXMLForms", var5);
			}

			this.mConnection.getClientCache().put(key.toString(), coll);
			if (timer != null) {
				timer.logDebug("getAllDynamicXMLForms", "");
			}
		} else {
			this.mLog.debug("getAllDynamicXMLForms", "obtained " + key + " from cache");
		}

		return coll;
	}

	public EntityList getAllFlatXMLForms() {
		StringBuffer key = new StringBuffer();
		key.append("AllFlatXMLForms");
		AllFlatXMLFormsELO coll = (AllFlatXMLFormsELO) this.mConnection.getClientCache().get(key.toString());
		if (coll == null) {
			Timer timer = this.mLog.isDebugEnabled() ? new Timer(this.mLog) : null;

			try {
				coll = (new ListSessionServer(this.getConnection())).getAllFlatXMLForms();
			} catch (Exception var5) {
				throw new RuntimeException("unable to get XmlForm AllFlatXMLForms", var5);
			}

			this.mConnection.getClientCache().put(key.toString(), coll);
			if (timer != null) {
				timer.logDebug("getAllFlatXMLForms", "");
			}
		} else {
			this.mLog.debug("getAllFlatXMLForms", "obtained " + key + " from cache");
		}

		return coll;
	}

	public EntityList getXMLFormDefinition(int param1) {
		StringBuffer key = new StringBuffer();
		key.append("XMLFormDefinition");
		key.append('.');
		key.append(String.valueOf(param1));
		XMLFormDefinitionELO coll = (XMLFormDefinitionELO) this.mConnection.getClientCache().get(key.toString());
		if (coll == null) {
			Timer timer = this.mLog.isDebugEnabled() ? new Timer(this.mLog) : null;

			try {
				coll = (new ListSessionServer(this.getConnection())).getXMLFormDefinition(param1);
			} catch (Exception var6) {
				throw new RuntimeException("unable to get XmlForm XMLFormDefinition", var6);
			}

			this.mConnection.getClientCache().put(key.toString(), coll);
			if (timer != null) {
				timer.logDebug("getXMLFormDefinition", "");
			}
		} else {
			this.mLog.debug("getXMLFormDefinition", "obtained " + key + " from cache");
		}

		return coll;
	}

	public EntityList getXMLFormCellPickerInfo(int param1) {
		StringBuffer key = new StringBuffer();
		key.append("XMLFormCellPickerInfo");
		key.append('.');
		key.append(String.valueOf(param1));
		XMLFormCellPickerInfoELO coll = (XMLFormCellPickerInfoELO) this.mConnection.getClientCache().get(key.toString());
		if (coll == null) {
			Timer timer = this.mLog.isDebugEnabled() ? new Timer(this.mLog) : null;

			try {
				coll = (new ListSessionServer(this.getConnection())).getXMLFormCellPickerInfo(param1);
			} catch (Exception var6) {
				throw new RuntimeException("unable to get XmlForm XMLFormCellPickerInfo", var6);
			}

			this.mConnection.getClientCache().put(key.toString(), coll);
			if (timer != null) {
				timer.logDebug("getXMLFormCellPickerInfo", "");
			}
		} else {
			this.mLog.debug("getXMLFormCellPickerInfo", "obtained " + key + " from cache");
		}

		return coll;
	}

	public EntityList getAllXMLFormUserLink() {
		StringBuffer key = new StringBuffer();
		key.append("AllXMLFormUserLink");
		AllXMLFormUserLinkELO coll = (AllXMLFormUserLinkELO) this.mConnection.getClientCache().get(key.toString());
		if (coll == null) {
			Timer timer = this.mLog.isDebugEnabled() ? new Timer(this.mLog) : null;

			try {
				coll = (new ListSessionServer(this.getConnection())).getAllXMLFormUserLink();
			} catch (Exception var5) {
				throw new RuntimeException("unable to get XmlFormUserLink AllXMLFormUserLink", var5);
			}

			this.mConnection.getClientCache().put(key.toString(), coll);
			if (timer != null) {
				timer.logDebug("getAllXMLFormUserLink", "");
			}
		} else {
			this.mLog.debug("getAllXMLFormUserLink", "obtained " + key + " from cache");
		}

		return coll;
	}

	public EntityList getCheckXMLFormUserLink(int param1) {
		StringBuffer key = new StringBuffer();
		key.append("CheckXMLFormUserLink");
		key.append('.');
		key.append(String.valueOf(param1));
		CheckXMLFormUserLinkELO coll = (CheckXMLFormUserLinkELO) this.mConnection.getClientCache().get(key.toString());
		if (coll == null) {
			Timer timer = this.mLog.isDebugEnabled() ? new Timer(this.mLog) : null;

			try {
				coll = (new ListSessionServer(this.getConnection())).getCheckXMLFormUserLink(param1);
			} catch (Exception var6) {
				throw new RuntimeException("unable to get XmlFormUserLink CheckXMLFormUserLink", var6);
			}

			this.mConnection.getClientCache().put(key.toString(), coll);
			if (timer != null) {
				timer.logDebug("getCheckXMLFormUserLink", "");
			}
		} else {
			this.mLog.debug("getCheckXMLFormUserLink", "obtained " + key + " from cache");
		}

		return coll;
	}

	public EntityList getAllFormNotesForBudgetLocation(int param1) {
		StringBuffer key = new StringBuffer();
		key.append("AllFormNotesForBudgetLocation");
		key.append('.');
		key.append(String.valueOf(param1));
		AllFormNotesForBudgetLocationELO coll = (AllFormNotesForBudgetLocationELO) this.mConnection.getClientCache().get(key.toString());
		if (coll == null) {
			Timer timer = this.mLog.isDebugEnabled() ? new Timer(this.mLog) : null;

			try {
				coll = (new ListSessionServer(this.getConnection())).getAllFormNotesForBudgetLocation(param1);
			} catch (Exception var6) {
				throw new RuntimeException("unable to get FormNotes AllFormNotesForBudgetLocation", var6);
			}

			this.mConnection.getClientCache().put(key.toString(), coll);
			if (timer != null) {
				timer.logDebug("getAllFormNotesForBudgetLocation", "");
			}
		} else {
			this.mLog.debug("getAllFormNotesForBudgetLocation", "obtained " + key + " from cache");
		}

		return coll;
	}

	public EntityList getAllFormNotesForFormAndBudgetLocation(int param1, int param2) {
		StringBuffer key = new StringBuffer();
		key.append("AllFormNotesForFormAndBudgetLocation");
		key.append('.');
		key.append(String.valueOf(param1));
		key.append('.');
		key.append(String.valueOf(param2));
		AllFormNotesForFormAndBudgetLocationELO coll = (AllFormNotesForFormAndBudgetLocationELO) this.mConnection.getClientCache().get(key.toString());
		if (coll == null) {
			Timer timer = this.mLog.isDebugEnabled() ? new Timer(this.mLog) : null;

			try {
				coll = (new ListSessionServer(this.getConnection())).getAllFormNotesForFormAndBudgetLocation(param1, param2);
			} catch (Exception var7) {
				throw new RuntimeException("unable to get FormNotes AllFormNotesForFormAndBudgetLocation", var7);
			}

			this.mConnection.getClientCache().put(key.toString(), coll);
			if (timer != null) {
				timer.logDebug("getAllFormNotesForFormAndBudgetLocation", "");
			}
		} else {
			this.mLog.debug("getAllFormNotesForFormAndBudgetLocation", "obtained " + key + " from cache");
		}

		return coll;
	}

	public EntityList getAllXmlReports() {
		Timer timer = this.mLog.isDebugEnabled() ? new Timer(this.mLog) : null;
		AllXmlReportsELO coll = null;

		try {
			coll = (new ListSessionServer(this.getConnection())).getAllXmlReports();
		} catch (Exception var4) {
			throw new RuntimeException("unable to get XmlReport AllXmlReports", var4);
		}

		if (timer != null) {
			timer.logDebug("getAllXmlReports", "");
		}

		return coll;
	}

	public EntityList getAllPublicXmlReports() {
		Timer timer = this.mLog.isDebugEnabled() ? new Timer(this.mLog) : null;
		AllPublicXmlReportsELO coll = null;

		try {
			coll = (new ListSessionServer(this.getConnection())).getAllPublicXmlReports();
		} catch (Exception var4) {
			throw new RuntimeException("unable to get XmlReport AllPublicXmlReports", var4);
		}

		if (timer != null) {
			timer.logDebug("getAllPublicXmlReports", "");
		}

		return coll;
	}

	public EntityList getAllXmlReportsForUser(int param1) {
		Timer timer = this.mLog.isDebugEnabled() ? new Timer(this.mLog) : null;
		AllXmlReportsForUserELO coll = null;

		try {
			coll = (new ListSessionServer(this.getConnection())).getAllXmlReportsForUser(param1);
		} catch (Exception var5) {
			throw new RuntimeException("unable to get XmlReport AllXmlReportsForUser", var5);
		}

		if (timer != null) {
			timer.logDebug("getAllXmlReportsForUser", "");
		}

		return coll;
	}

	public EntityList getXmlReportsForFolder(int param1) {
		Timer timer = this.mLog.isDebugEnabled() ? new Timer(this.mLog) : null;
		XmlReportsForFolderELO coll = null;

		try {
			coll = (new ListSessionServer(this.getConnection())).getXmlReportsForFolder(param1);
		} catch (Exception var5) {
			throw new RuntimeException("unable to get XmlReport XmlReportsForFolder", var5);
		}

		if (timer != null) {
			timer.logDebug("getXmlReportsForFolder", "");
		}

		return coll;
	}

	public EntityList getSingleXmlReport(int param1, String param2) {
		Timer timer = this.mLog.isDebugEnabled() ? new Timer(this.mLog) : null;
		SingleXmlReportELO coll = null;

		try {
			coll = (new ListSessionServer(this.getConnection())).getSingleXmlReport(param1, param2);
		} catch (Exception var6) {
			throw new RuntimeException("unable to get XmlReport SingleXmlReport", var6);
		}

		if (timer != null) {
			timer.logDebug("getSingleXmlReport", "");
		}

		return coll;
	}

	public EntityList getAllXmlReportFolders() {
		Timer timer = this.mLog.isDebugEnabled() ? new Timer(this.mLog) : null;
		AllXmlReportFoldersELO coll = null;

		try {
			coll = (new ListSessionServer(this.getConnection())).getAllXmlReportFolders();
		} catch (Exception var4) {
			throw new RuntimeException("unable to get XmlReportFolder AllXmlReportFolders", var4);
		}

		if (timer != null) {
			timer.logDebug("getAllXmlReportFolders", "");
		}

		return coll;
	}

	public EntityList getDecendentFolders(int param1) {
		Timer timer = this.mLog.isDebugEnabled() ? new Timer(this.mLog) : null;
		DecendentFoldersELO coll = null;

		try {
			coll = (new ListSessionServer(this.getConnection())).getDecendentFolders(param1);
		} catch (Exception var5) {
			throw new RuntimeException("unable to get XmlReportFolder DecendentFolders", var5);
		}

		if (timer != null) {
			timer.logDebug("getDecendentFolders", "");
		}

		return coll;
	}

	public EntityList getReportFolderWithId(int param1) {
		Timer timer = this.mLog.isDebugEnabled() ? new Timer(this.mLog) : null;
		ReportFolderWithIdELO coll = null;

		try {
			coll = (new ListSessionServer(this.getConnection())).getReportFolderWithId(param1);
		} catch (Exception var5) {
			throw new RuntimeException("unable to get XmlReportFolder ReportFolderWithId", var5);
		}

		if (timer != null) {
			timer.logDebug("getReportFolderWithId", "");
		}

		return coll;
	}

	public EntityList getAllDataEntryProfiles() {
		StringBuffer key = new StringBuffer();
		key.append("AllDataEntryProfiles");
		AllDataEntryProfilesELO coll = (AllDataEntryProfilesELO) this.mConnection.getClientCache().get(key.toString());
		if (coll == null) {
			Timer timer = this.mLog.isDebugEnabled() ? new Timer(this.mLog) : null;

			try {
				coll = (new ListSessionServer(this.getConnection())).getAllDataEntryProfiles();
			} catch (Exception var5) {
				throw new RuntimeException("unable to get DataEntryProfile AllDataEntryProfiles", var5);
			}

			this.mConnection.getClientCache().put(key.toString(), coll);
			if (timer != null) {
				timer.logDebug("getAllDataEntryProfiles", "");
			}
		} else {
			this.mLog.debug("getAllDataEntryProfiles", "obtained " + key + " from cache");
		}

		return coll;
	}

	public EntityList getAllDataEntryProfilesForUser(int param1, int param2, int budgetCycleId) {
		Timer timer = this.mLog.isDebugEnabled() ? new Timer(this.mLog) : null;
		AllDataEntryProfilesForUserELO coll = null;

		try {
			coll = (new ListSessionServer(this.getConnection())).getAllDataEntryProfilesForUser(param1, param2, budgetCycleId);
		} catch (Exception var6) {
			throw new RuntimeException("unable to get DataEntryProfile AllDataEntryProfilesForUser", var6);
		}

		if (timer != null) {
			timer.logDebug("getAllDataEntryProfilesForUser", "");
		}

		return coll;
	}

	public EntityList getAllUsersForDataEntryProfilesForModel(int param1) {
		Timer timer = this.mLog.isDebugEnabled() ? new Timer(this.mLog) : null;
		AllUsersForDataEntryProfilesForModelELO coll = null;

		try {
			coll = (new ListSessionServer(this.getConnection())).getAllUsersForDataEntryProfilesForModel(param1);
		} catch (Exception var5) {
			throw new RuntimeException("unable to get DataEntryProfile AllUsersForDataEntryProfilesForModel", var5);
		}

		if (timer != null) {
			timer.logDebug("getAllUsersForDataEntryProfilesForModel", "");
		}

		return coll;
	}

	public EntityList getAllDataEntryProfilesForForm(int param1) {
		Timer timer = this.mLog.isDebugEnabled() ? new Timer(this.mLog) : null;
		AllDataEntryProfilesForFormELO coll = null;

		try {
			coll = (new ListSessionServer(this.getConnection())).getAllDataEntryProfilesForForm(param1);
		} catch (Exception var5) {
			throw new RuntimeException("unable to get DataEntryProfile AllDataEntryProfilesForForm", var5);
		}

		if (timer != null) {
			timer.logDebug("getAllDataEntryProfilesForForm", "");
		}

		return coll;
	}

	public EntityList getDefaultDataEntryProfile(int param1, int param2, int param3, int param4) {
		Timer timer = this.mLog.isDebugEnabled() ? new Timer(this.mLog) : null;
		DefaultDataEntryProfileELO coll = null;

		try {
			coll = (new ListSessionServer(this.getConnection())).getDefaultDataEntryProfile(param1, param2, param3, param4);
		} catch (Exception var7) {
			throw new RuntimeException("unable to get DataEntryProfile DefaultDataEntryProfile", var7);
		}

		if (timer != null) {
			timer.logDebug("getDefaultDataEntryProfile", "");
		}

		return coll;
	}

	public EntityList getAllDataEntryProfileHistorys() {
		StringBuffer key = new StringBuffer();
		key.append("AllDataEntryProfileHistorys");
		AllDataEntryProfileHistorysELO coll = (AllDataEntryProfileHistorysELO) this.mConnection.getClientCache().get(key.toString());
		if (coll == null) {
			Timer timer = this.mLog.isDebugEnabled() ? new Timer(this.mLog) : null;

			try {
				coll = (new ListSessionServer(this.getConnection())).getAllDataEntryProfileHistorys();
			} catch (Exception var5) {
				throw new RuntimeException("unable to get DataEntryProfileHistory AllDataEntryProfileHistorys", var5);
			}

			this.mConnection.getClientCache().put(key.toString(), coll);
			if (timer != null) {
				timer.logDebug("getAllDataEntryProfileHistorys", "");
			}
		} else {
			this.mLog.debug("getAllDataEntryProfileHistorys", "obtained " + key + " from cache");
		}

		return coll;
	}

	public EntityList getAllUdefLookups() {
		StringBuffer key = new StringBuffer();
		key.append("AllUdefLookups");
		AllUdefLookupsELO coll = (AllUdefLookupsELO) this.mConnection.getClientCache().get(key.toString());
		if (coll == null) {
			Timer timer = this.mLog.isDebugEnabled() ? new Timer(this.mLog) : null;

			try {
				coll = (new ListSessionServer(this.getConnection())).getAllUdefLookups();
			} catch (Exception var5) {
				throw new RuntimeException("unable to get UdefLookup AllUdefLookups", var5);
			}

			this.mConnection.getClientCache().put(key.toString(), coll);
			if (timer != null) {
				timer.logDebug("getAllUdefLookups", "");
			}
		} else {
			this.mLog.debug("getAllUdefLookups", "obtained " + key + " from cache");
		}

		return coll;
	}

	public EntityList getAllUdefLookupsForLoggedUser() {
		AllUdefLookupsELO coll;
		Timer timer = this.mLog.isDebugEnabled() ? new Timer(this.mLog) : null;

		try {
			coll = (new ListSessionServer(this.getConnection())).getAllUdefLookupsForLoggedUser();
		} catch (Exception var5) {
			throw new RuntimeException("unable to get UdefLookup AllUdefLookupsForLoggedUser", var5);
		}
		return coll;
	}

	public EntityList getAllSecurityRanges() {
		StringBuffer key = new StringBuffer();
		key.append("AllSecurityRanges");
		AllSecurityRangesELO coll = (AllSecurityRangesELO) this.mConnection.getClientCache().get(key.toString());
		if (coll == null) {
			Timer timer = this.mLog.isDebugEnabled() ? new Timer(this.mLog) : null;

			try {
				coll = (new ListSessionServer(this.getConnection())).getAllSecurityRanges();
			} catch (Exception var5) {
				throw new RuntimeException("unable to get SecurityRange AllSecurityRanges", var5);
			}

			this.mConnection.getClientCache().put(key.toString(), coll);
			if (timer != null) {
				timer.logDebug("getAllSecurityRanges", "");
			}
		} else {
			this.mLog.debug("getAllSecurityRanges", "obtained " + key + " from cache");
		}

		return coll;
	}

	public EntityList getAllSecurityRangesForModel(int param1) {
		StringBuffer key = new StringBuffer();
		key.append("AllSecurityRangesForModel");
		key.append('.');
		key.append(String.valueOf(param1));
		AllSecurityRangesForModelELO coll = (AllSecurityRangesForModelELO) this.mConnection.getClientCache().get(key.toString());
		if (coll == null) {
			Timer timer = this.mLog.isDebugEnabled() ? new Timer(this.mLog) : null;

			try {
				coll = (new ListSessionServer(this.getConnection())).getAllSecurityRangesForModel(param1);
			} catch (Exception var6) {
				throw new RuntimeException("unable to get SecurityRange AllSecurityRangesForModel", var6);
			}

			this.mConnection.getClientCache().put(key.toString(), coll);
			if (timer != null) {
				timer.logDebug("getAllSecurityRangesForModel", "");
			}
		} else {
			this.mLog.debug("getAllSecurityRangesForModel", "obtained " + key + " from cache");
		}

		return coll;
	}

	public EntityList getAllSecurityAccessDefs() {
		StringBuffer key = new StringBuffer();
		key.append("AllSecurityAccessDefs");
		AllSecurityAccessDefsELO coll = (AllSecurityAccessDefsELO) this.mConnection.getClientCache().get(key.toString());
		if (coll == null) {
			Timer timer = this.mLog.isDebugEnabled() ? new Timer(this.mLog) : null;

			try {
				coll = (new ListSessionServer(this.getConnection())).getAllSecurityAccessDefs();
			} catch (Exception var5) {
				throw new RuntimeException("unable to get SecurityAccessDef AllSecurityAccessDefs", var5);
			}

			this.mConnection.getClientCache().put(key.toString(), coll);
			if (timer != null) {
				timer.logDebug("getAllSecurityAccessDefs", "");
			}
		} else {
			this.mLog.debug("getAllSecurityAccessDefs", "obtained " + key + " from cache");
		}

		return coll;
	}

	public EntityList getAllSecurityAccessDefsForModel(int param1) {
		StringBuffer key = new StringBuffer();
		key.append("AllSecurityAccessDefsForModel");
		key.append('.');
		key.append(String.valueOf(param1));
		AllSecurityAccessDefsForModelELO coll = (AllSecurityAccessDefsForModelELO) this.mConnection.getClientCache().get(key.toString());
		if (coll == null) {
			Timer timer = this.mLog.isDebugEnabled() ? new Timer(this.mLog) : null;

			try {
				coll = (new ListSessionServer(this.getConnection())).getAllSecurityAccessDefsForModel(param1);
			} catch (Exception var6) {
				throw new RuntimeException("unable to get SecurityAccessDef AllSecurityAccessDefsForModel", var6);
			}

			this.mConnection.getClientCache().put(key.toString(), coll);
			if (timer != null) {
				timer.logDebug("getAllSecurityAccessDefsForModel", "");
			}
		} else {
			this.mLog.debug("getAllSecurityAccessDefsForModel", "obtained " + key + " from cache");
		}

		return coll;
	}

	public EntityList getAllAccessDefsUsingRange(int param1) {
		StringBuffer key = new StringBuffer();
		key.append("AllAccessDefsUsingRange");
		key.append('.');
		key.append(String.valueOf(param1));
		AllAccessDefsUsingRangeELO coll = (AllAccessDefsUsingRangeELO) this.mConnection.getClientCache().get(key.toString());
		if (coll == null) {
			Timer timer = this.mLog.isDebugEnabled() ? new Timer(this.mLog) : null;

			try {
				coll = (new ListSessionServer(this.getConnection())).getAllAccessDefsUsingRange(param1);
			} catch (Exception var6) {
				throw new RuntimeException("unable to get SecurityAccRngRel AllAccessDefsUsingRange", var6);
			}

			this.mConnection.getClientCache().put(key.toString(), coll);
			if (timer != null) {
				timer.logDebug("getAllAccessDefsUsingRange", "");
			}
		} else {
			this.mLog.debug("getAllAccessDefsUsingRange", "obtained " + key + " from cache");
		}

		return coll;
	}

	public EntityList getAllSecurityGroups() {
		StringBuffer key = new StringBuffer();
		key.append("AllSecurityGroups");
		AllSecurityGroupsELO coll = (AllSecurityGroupsELO) this.mConnection.getClientCache().get(key.toString());
		if (coll == null) {
			Timer timer = this.mLog.isDebugEnabled() ? new Timer(this.mLog) : null;

			try {
				coll = (new ListSessionServer(this.getConnection())).getAllSecurityGroups();
			} catch (Exception var5) {
				throw new RuntimeException("unable to get SecurityGroup AllSecurityGroups", var5);
			}

			this.mConnection.getClientCache().put(key.toString(), coll);
			if (timer != null) {
				timer.logDebug("getAllSecurityGroups", "");
			}
		} else {
			this.mLog.debug("getAllSecurityGroups", "obtained " + key + " from cache");
		}

		return coll;
	}

	public EntityList getAllSecurityGroupsUsingAccessDef(int param1) {
		StringBuffer key = new StringBuffer();
		key.append("AllSecurityGroupsUsingAccessDef");
		key.append('.');
		key.append(String.valueOf(param1));
		AllSecurityGroupsUsingAccessDefELO coll = (AllSecurityGroupsUsingAccessDefELO) this.mConnection.getClientCache().get(key.toString());
		if (coll == null) {
			Timer timer = this.mLog.isDebugEnabled() ? new Timer(this.mLog) : null;

			try {
				coll = (new ListSessionServer(this.getConnection())).getAllSecurityGroupsUsingAccessDef(param1);
			} catch (Exception var6) {
				throw new RuntimeException("unable to get SecurityGroup AllSecurityGroupsUsingAccessDef", var6);
			}

			this.mConnection.getClientCache().put(key.toString(), coll);
			if (timer != null) {
				timer.logDebug("getAllSecurityGroupsUsingAccessDef", "");
			}
		} else {
			this.mLog.debug("getAllSecurityGroupsUsingAccessDef", "obtained " + key + " from cache");
		}

		return coll;
	}

	public EntityList getAllSecurityGroupsForUser(int param1) {
		StringBuffer key = new StringBuffer();
		key.append("AllSecurityGroupsForUser");
		key.append('.');
		key.append(String.valueOf(param1));
		AllSecurityGroupsForUserELO coll = (AllSecurityGroupsForUserELO) this.mConnection.getClientCache().get(key.toString());
		if (coll == null) {
			Timer timer = this.mLog.isDebugEnabled() ? new Timer(this.mLog) : null;

			try {
				coll = (new ListSessionServer(this.getConnection())).getAllSecurityGroupsForUser(param1);
			} catch (Exception var6) {
				throw new RuntimeException("unable to get SecurityGroup AllSecurityGroupsForUser", var6);
			}

			this.mConnection.getClientCache().put(key.toString(), coll);
			if (timer != null) {
				timer.logDebug("getAllSecurityGroupsForUser", "");
			}
		} else {
			this.mLog.debug("getAllSecurityGroupsForUser", "obtained " + key + " from cache");
		}

		return coll;
	}

	public EntityList getAllCcDeployments() {
		StringBuffer key = new StringBuffer();
		key.append("AllCcDeployments");
		AllCcDeploymentsELO coll = (AllCcDeploymentsELO) this.mConnection.getClientCache().get(key.toString());
		if (coll == null) {
			Timer timer = this.mLog.isDebugEnabled() ? new Timer(this.mLog) : null;

			try {
				coll = (new ListSessionServer(this.getConnection())).getAllCcDeployments();
			} catch (Exception var5) {
				throw new RuntimeException("unable to get CcDeployment AllCcDeployments", var5);
			}

			this.mConnection.getClientCache().put(key.toString(), coll);
			if (timer != null) {
				timer.logDebug("getAllCcDeployments", "");
			}
		} else {
			this.mLog.debug("getAllCcDeployments", "obtained " + key + " from cache");
		}

		return coll;
	}

	public EntityList getCcDeploymentsForLookupTable(String param1) {
		StringBuffer key = new StringBuffer();
		key.append("CcDeploymentsForLookupTable");
		key.append('.');
		key.append(param1.toString());
		CcDeploymentsForLookupTableELO coll = (CcDeploymentsForLookupTableELO) this.mConnection.getClientCache().get(key.toString());
		if (coll == null) {
			Timer timer = this.mLog.isDebugEnabled() ? new Timer(this.mLog) : null;

			try {
				coll = (new ListSessionServer(this.getConnection())).getCcDeploymentsForLookupTable(param1);
			} catch (Exception var6) {
				throw new RuntimeException("unable to get CcDeployment CcDeploymentsForLookupTable", var6);
			}

			this.mConnection.getClientCache().put(key.toString(), coll);
			if (timer != null) {
				timer.logDebug("getCcDeploymentsForLookupTable", "");
			}
		} else {
			this.mLog.debug("getCcDeploymentsForLookupTable", "obtained " + key + " from cache");
		}

		return coll;
	}

	public EntityList getCcDeploymentsForXmlForm(int param1) {
		StringBuffer key = new StringBuffer();
		key.append("CcDeploymentsForXmlForm");
		key.append('.');
		key.append(String.valueOf(param1));
		CcDeploymentsForXmlFormELO coll = (CcDeploymentsForXmlFormELO) this.mConnection.getClientCache().get(key.toString());
		if (coll == null) {
			Timer timer = this.mLog.isDebugEnabled() ? new Timer(this.mLog) : null;

			try {
				coll = (new ListSessionServer(this.getConnection())).getCcDeploymentsForXmlForm(param1);
			} catch (Exception var6) {
				throw new RuntimeException("unable to get CcDeployment CcDeploymentsForXmlForm", var6);
			}

			this.mConnection.getClientCache().put(key.toString(), coll);
			if (timer != null) {
				timer.logDebug("getCcDeploymentsForXmlForm", "");
			}
		} else {
			this.mLog.debug("getCcDeploymentsForXmlForm", "obtained " + key + " from cache");
		}

		return coll;
	}

	public EntityList getCcDeploymentsForModel(int param1) {
		StringBuffer key = new StringBuffer();
		key.append("CcDeploymentsForModel");
		key.append('.');
		key.append(String.valueOf(param1));
		CcDeploymentsForModelELO coll = (CcDeploymentsForModelELO) this.mConnection.getClientCache().get(key.toString());
		if (coll == null) {
			Timer timer = this.mLog.isDebugEnabled() ? new Timer(this.mLog) : null;

			try {
				coll = (new ListSessionServer(this.getConnection())).getCcDeploymentsForModel(param1);
			} catch (Exception var6) {
				throw new RuntimeException("unable to get CcDeployment CcDeploymentsForModel", var6);
			}

			this.mConnection.getClientCache().put(key.toString(), coll);
			if (timer != null) {
				timer.logDebug("getCcDeploymentsForModel", "");
			}
		} else {
			this.mLog.debug("getCcDeploymentsForModel", "obtained " + key + " from cache");
		}

		return coll;
	}

	public EntityList getCcDeploymentCellPickerInfo(int param1) {
		StringBuffer key = new StringBuffer();
		key.append("CcDeploymentCellPickerInfo");
		key.append('.');
		key.append(String.valueOf(param1));
		CcDeploymentCellPickerInfoELO coll = (CcDeploymentCellPickerInfoELO) this.mConnection.getClientCache().get(key.toString());
		if (coll == null) {
			Timer timer = this.mLog.isDebugEnabled() ? new Timer(this.mLog) : null;

			try {
				coll = (new ListSessionServer(this.getConnection())).getCcDeploymentCellPickerInfo(param1);
			} catch (Exception var6) {
				throw new RuntimeException("unable to get CcDeployment CcDeploymentCellPickerInfo", var6);
			}

			this.mConnection.getClientCache().put(key.toString(), coll);
			if (timer != null) {
				timer.logDebug("getCcDeploymentCellPickerInfo", "");
			}
		} else {
			this.mLog.debug("getCcDeploymentCellPickerInfo", "obtained " + key + " from cache");
		}

		return coll;
	}

	public EntityList getCcDeploymentXMLFormType(int param1) {
		StringBuffer key = new StringBuffer();
		key.append("CcDeploymentXMLFormType");
		key.append('.');
		key.append(String.valueOf(param1));
		CcDeploymentXMLFormTypeELO coll = (CcDeploymentXMLFormTypeELO) this.mConnection.getClientCache().get(key.toString());
		if (coll == null) {
			Timer timer = this.mLog.isDebugEnabled() ? new Timer(this.mLog) : null;

			try {
				coll = (new ListSessionServer(this.getConnection())).getCcDeploymentXMLFormType(param1);
			} catch (Exception var6) {
				throw new RuntimeException("unable to get CcDeployment CcDeploymentXMLFormType", var6);
			}

			this.mConnection.getClientCache().put(key.toString(), coll);
			if (timer != null) {
				timer.logDebug("getCcDeploymentXMLFormType", "");
			}
		} else {
			this.mLog.debug("getCcDeploymentXMLFormType", "obtained " + key + " from cache");
		}

		return coll;
	}

	public EntityList getAllCellCalcs() {
		StringBuffer key = new StringBuffer();
		key.append("AllCellCalcs");
		AllCellCalcsELO coll = (AllCellCalcsELO) this.mConnection.getClientCache().get(key.toString());
		if (coll == null) {
			Timer timer = this.mLog.isDebugEnabled() ? new Timer(this.mLog) : null;

			try {
				coll = (new ListSessionServer(this.getConnection())).getAllCellCalcs();
			} catch (Exception var5) {
				throw new RuntimeException("unable to get CellCalc AllCellCalcs", var5);
			}

			this.mConnection.getClientCache().put(key.toString(), coll);
			if (timer != null) {
				timer.logDebug("getAllCellCalcs", "");
			}
		} else {
			this.mLog.debug("getAllCellCalcs", "obtained " + key + " from cache");
		}

		return coll;
	}

	public EntityList getCellCalcIntegrity() {
		StringBuffer key = new StringBuffer();
		key.append("CellCalcIntegrity");
		CellCalcIntegrityELO coll = (CellCalcIntegrityELO) this.mConnection.getClientCache().get(key.toString());
		if (coll == null) {
			Timer timer = this.mLog.isDebugEnabled() ? new Timer(this.mLog) : null;

			try {
				coll = (new ListSessionServer(this.getConnection())).getCellCalcIntegrity();
			} catch (Exception var5) {
				throw new RuntimeException("unable to get CellCalc CellCalcIntegrity", var5);
			}

			this.mConnection.getClientCache().put(key.toString(), coll);
			if (timer != null) {
				timer.logDebug("getCellCalcIntegrity", "");
			}
		} else {
			this.mLog.debug("getCellCalcIntegrity", "obtained " + key + " from cache");
		}

		return coll;
	}

	public EntityList getAllCellCalcAssocs() {
		StringBuffer key = new StringBuffer();
		key.append("AllCellCalcAssocs");
		AllCellCalcAssocsELO coll = (AllCellCalcAssocsELO) this.mConnection.getClientCache().get(key.toString());
		if (coll == null) {
			Timer timer = this.mLog.isDebugEnabled() ? new Timer(this.mLog) : null;

			try {
				coll = (new ListSessionServer(this.getConnection())).getAllCellCalcAssocs();
			} catch (Exception var5) {
				throw new RuntimeException("unable to get CellCalcAssoc AllCellCalcAssocs", var5);
			}

			this.mConnection.getClientCache().put(key.toString(), coll);
			if (timer != null) {
				timer.logDebug("getAllCellCalcAssocs", "");
			}
		} else {
			this.mLog.debug("getAllCellCalcAssocs", "obtained " + key + " from cache");
		}

		return coll;
	}

	public EntityList getAllChangeMgmts() {
		StringBuffer key = new StringBuffer();
		key.append("AllChangeMgmts");
		AllChangeMgmtsELO coll = (AllChangeMgmtsELO) this.mConnection.getClientCache().get(key.toString());
		if (coll == null) {
			Timer timer = this.mLog.isDebugEnabled() ? new Timer(this.mLog) : null;

			try {
				coll = (new ListSessionServer(this.getConnection())).getAllChangeMgmts();
			} catch (Exception var5) {
				throw new RuntimeException("unable to get ChangeMgmt AllChangeMgmts", var5);
			}

			this.mConnection.getClientCache().put(key.toString(), coll);
			if (timer != null) {
				timer.logDebug("getAllChangeMgmts", "");
			}
		} else {
			this.mLog.debug("getAllChangeMgmts", "obtained " + key + " from cache");
		}

		return coll;
	}

	public EntityList getAllChangeMgmtsForModel(int param1) {
		StringBuffer key = new StringBuffer();
		key.append("AllChangeMgmtsForModel");
		key.append('.');
		key.append(String.valueOf(param1));
		AllChangeMgmtsForModelELO coll = (AllChangeMgmtsForModelELO) this.mConnection.getClientCache().get(key.toString());
		if (coll == null) {
			Timer timer = this.mLog.isDebugEnabled() ? new Timer(this.mLog) : null;

			try {
				coll = (new ListSessionServer(this.getConnection())).getAllChangeMgmtsForModel(param1);
			} catch (Exception var6) {
				throw new RuntimeException("unable to get ChangeMgmt AllChangeMgmtsForModel", var6);
			}

			this.mConnection.getClientCache().put(key.toString(), coll);
			if (timer != null) {
				timer.logDebug("getAllChangeMgmtsForModel", "");
			}
		} else {
			this.mLog.debug("getAllChangeMgmtsForModel", "obtained " + key + " from cache");
		}

		return coll;
	}

	public EntityList getAllChangeMgmtsForModelWithXML(int param1) {
		StringBuffer key = new StringBuffer();
		key.append("AllChangeMgmtsForModelWithXML");
		key.append('.');
		key.append(String.valueOf(param1));
		AllChangeMgmtsForModelWithXMLELO coll = (AllChangeMgmtsForModelWithXMLELO) this.mConnection.getClientCache().get(key.toString());
		if (coll == null) {
			Timer timer = this.mLog.isDebugEnabled() ? new Timer(this.mLog) : null;

			try {
				coll = (new ListSessionServer(this.getConnection())).getAllChangeMgmtsForModelWithXML(param1);
			} catch (Exception var6) {
				throw new RuntimeException("unable to get ChangeMgmt AllChangeMgmtsForModelWithXML", var6);
			}

			this.mConnection.getClientCache().put(key.toString(), coll);
			if (timer != null) {
				timer.logDebug("getAllChangeMgmtsForModelWithXML", "");
			}
		} else {
			this.mLog.debug("getAllChangeMgmtsForModelWithXML", "obtained " + key + " from cache");
		}

		return coll;
	}

	public EntityList getAllImpExpHdrs() {
		StringBuffer key = new StringBuffer();
		key.append("AllImpExpHdrs");
		AllImpExpHdrsELO coll = (AllImpExpHdrsELO) this.mConnection.getClientCache().get(key.toString());
		if (coll == null) {
			Timer timer = this.mLog.isDebugEnabled() ? new Timer(this.mLog) : null;

			try {
				coll = (new ListSessionServer(this.getConnection())).getAllImpExpHdrs();
			} catch (Exception var5) {
				throw new RuntimeException("unable to get ImpExpHdr AllImpExpHdrs", var5);
			}

			this.mConnection.getClientCache().put(key.toString(), coll);
			if (timer != null) {
				timer.logDebug("getAllImpExpHdrs", "");
			}
		} else {
			this.mLog.debug("getAllImpExpHdrs", "obtained " + key + " from cache");
		}

		return coll;
	}

	public EntityList getAllReports() {
		Timer timer = this.mLog.isDebugEnabled() ? new Timer(this.mLog) : null;
		AllReportsELO coll = null;

		try {
			coll = (new ListSessionServer(this.getConnection())).getAllReports();
		} catch (Exception var4) {
			throw new RuntimeException("unable to get Report AllReports", var4);
		}

		if (timer != null) {
			timer.logDebug("getAllReports", "");
		}

		return coll;
	}

	public EntityList getAllReportsForUser(int param1) {
		Timer timer = this.mLog.isDebugEnabled() ? new Timer(this.mLog) : null;
		AllReportsForUserELO coll = null;

		try {
			coll = (new ListSessionServer(this.getConnection())).getAllReportsForUser(param1);
		} catch (Exception var5) {
			throw new RuntimeException("unable to get Report AllReportsForUser", var5);
		}

		if (timer != null) {
			timer.logDebug("getAllReportsForUser", "");
		}

		return coll;
	}

	public EntityList getAllReportsForAdmin() {
		Timer timer = this.mLog.isDebugEnabled() ? new Timer(this.mLog) : null;
		AllReportsForAdminELO coll = null;

		try {
			coll = (new ListSessionServer(this.getConnection())).getAllReportsForAdmin();
		} catch (Exception var4) {
			throw new RuntimeException("unable to get Report AllReportsForAdmin", var4);
		}

		if (timer != null) {
			timer.logDebug("getAllReportsForAdmin", "");
		}

		return coll;
	}

	public EntityList getWebReportDetails(int param1) {
		Timer timer = this.mLog.isDebugEnabled() ? new Timer(this.mLog) : null;
		WebReportDetailsELO coll = null;

		try {
			coll = (new ListSessionServer(this.getConnection())).getWebReportDetails(param1);
		} catch (Exception var5) {
			throw new RuntimeException("unable to get Report WebReportDetails", var5);
		}

		if (timer != null) {
			timer.logDebug("getWebReportDetails", "");
		}

		return coll;
	}

	public EntityList getAllVirementCategorys() {
		StringBuffer key = new StringBuffer();
		key.append("AllVirementCategorys");
		AllVirementCategorysELO coll = (AllVirementCategorysELO) this.mConnection.getClientCache().get(key.toString());
		if (coll == null) {
			Timer timer = this.mLog.isDebugEnabled() ? new Timer(this.mLog) : null;

			try {
				coll = (new ListSessionServer(this.getConnection())).getAllVirementCategorys();
			} catch (Exception var5) {
				throw new RuntimeException("unable to get VirementCategory AllVirementCategorys", var5);
			}

			this.mConnection.getClientCache().put(key.toString(), coll);
			if (timer != null) {
				timer.logDebug("getAllVirementCategorys", "");
			}
		} else {
			this.mLog.debug("getAllVirementCategorys", "obtained " + key + " from cache");
		}

		return coll;
	}

	public EntityList getLocationsForCategory(int param1) {
		StringBuffer key = new StringBuffer();
		key.append("LocationsForCategory");
		key.append('.');
		key.append(String.valueOf(param1));
		LocationsForCategoryELO coll = (LocationsForCategoryELO) this.mConnection.getClientCache().get(key.toString());
		if (coll == null) {
			Timer timer = this.mLog.isDebugEnabled() ? new Timer(this.mLog) : null;

			try {
				coll = (new ListSessionServer(this.getConnection())).getLocationsForCategory(param1);
			} catch (Exception var6) {
				throw new RuntimeException("unable to get VirementLocation LocationsForCategory", var6);
			}

			this.mConnection.getClientCache().put(key.toString(), coll);
			if (timer != null) {
				timer.logDebug("getLocationsForCategory", "");
			}
		} else {
			this.mLog.debug("getLocationsForCategory", "obtained " + key + " from cache");
		}

		return coll;
	}

	public EntityList getAccountsForCategory(int param1) {
		StringBuffer key = new StringBuffer();
		key.append("AccountsForCategory");
		key.append('.');
		key.append(String.valueOf(param1));
		AccountsForCategoryELO coll = (AccountsForCategoryELO) this.mConnection.getClientCache().get(key.toString());
		if (coll == null) {
			Timer timer = this.mLog.isDebugEnabled() ? new Timer(this.mLog) : null;

			try {
				coll = (new ListSessionServer(this.getConnection())).getAccountsForCategory(param1);
			} catch (Exception var6) {
				throw new RuntimeException("unable to get VirementAccount AccountsForCategory", var6);
			}

			this.mConnection.getClientCache().put(key.toString(), coll);
			if (timer != null) {
				timer.logDebug("getAccountsForCategory", "");
			}
		} else {
			this.mLog.debug("getAccountsForCategory", "obtained " + key + " from cache");
		}

		return coll;
	}

	public EntityList getAllBudgetLimits() {
		StringBuffer key = new StringBuffer();
		key.append("AllBudgetLimits");
		AllBudgetLimitsELO coll = (AllBudgetLimitsELO) this.mConnection.getClientCache().get(key.toString());
		if (coll == null) {
			Timer timer = this.mLog.isDebugEnabled() ? new Timer(this.mLog) : null;

			try {
				coll = (new ListSessionServer(this.getConnection())).getAllBudgetLimits();
			} catch (Exception var5) {
				throw new RuntimeException("unable to get BudgetLimit AllBudgetLimits", var5);
			}

			this.mConnection.getClientCache().put(key.toString(), coll);
			if (timer != null) {
				timer.logDebug("getAllBudgetLimits", "");
			}
		} else {
			this.mLog.debug("getAllBudgetLimits", "obtained " + key + " from cache");
		}

		return coll;
	}

	public EntityList getAllPerfTests() {
		StringBuffer key = new StringBuffer();
		key.append("AllPerfTests");
		AllPerfTestsELO coll = (AllPerfTestsELO) this.mConnection.getClientCache().get(key.toString());
		if (coll == null) {
			Timer timer = this.mLog.isDebugEnabled() ? new Timer(this.mLog) : null;

			try {
				coll = (new ListSessionServer(this.getConnection())).getAllPerfTests();
			} catch (Exception var5) {
				throw new RuntimeException("unable to get PerfTest AllPerfTests", var5);
			}

			this.mConnection.getClientCache().put(key.toString(), coll);
			if (timer != null) {
				timer.logDebug("getAllPerfTests", "");
			}
		} else {
			this.mLog.debug("getAllPerfTests", "obtained " + key + " from cache");
		}

		return coll;
	}

	public EntityList getAllPerfTestRuns() {
		Timer timer = this.mLog.isDebugEnabled() ? new Timer(this.mLog) : null;
		AllPerfTestRunsELO coll = null;

		try {
			coll = (new ListSessionServer(this.getConnection())).getAllPerfTestRuns();
		} catch (Exception var4) {
			throw new RuntimeException("unable to get PerfTestRun AllPerfTestRuns", var4);
		}

		if (timer != null) {
			timer.logDebug("getAllPerfTestRuns", "");
		}

		return coll;
	}

	public EntityList getAllPerfTestRunResults() {
		StringBuffer key = new StringBuffer();
		key.append("AllPerfTestRunResults");
		AllPerfTestRunResultsELO coll = (AllPerfTestRunResultsELO) this.mConnection.getClientCache().get(key.toString());
		if (coll == null) {
			Timer timer = this.mLog.isDebugEnabled() ? new Timer(this.mLog) : null;

			try {
				coll = (new ListSessionServer(this.getConnection())).getAllPerfTestRunResults();
			} catch (Exception var5) {
				throw new RuntimeException("unable to get PerfTestRunResult AllPerfTestRunResults", var5);
			}

			this.mConnection.getClientCache().put(key.toString(), coll);
			if (timer != null) {
				timer.logDebug("getAllPerfTestRunResults", "");
			}
		} else {
			this.mLog.debug("getAllPerfTestRunResults", "obtained " + key + " from cache");
		}

		return coll;
	}

	public EntityList getAllMessages() {
		StringBuffer key = new StringBuffer();
		key.append("AllMessages");
		AllMessagesELO coll = (AllMessagesELO) this.mConnection.getClientCache().get(key.toString());
		if (coll == null) {
			Timer timer = this.mLog.isDebugEnabled() ? new Timer(this.mLog) : null;

			try {
				coll = (new ListSessionServer(this.getConnection())).getAllMessages();
			} catch (Exception var5) {
				throw new RuntimeException("unable to get Message AllMessages", var5);
			}

			this.mConnection.getClientCache().put(key.toString(), coll);
			if (timer != null) {
				timer.logDebug("getAllMessages", "");
			}
		} else {
			this.mLog.debug("getAllMessages", "obtained " + key + " from cache");
		}

		return coll;
	}

	public EntityList getInBoxForUser(String param1) {
		Timer timer = this.mLog.isDebugEnabled() ? new Timer(this.mLog) : null;
		InBoxForUserELO coll = null;

		try {
			coll = (new ListSessionServer(this.getConnection())).getInBoxForUser(param1);
		} catch (Exception var5) {
			throw new RuntimeException("unable to get Message InBoxForUser", var5);
		}

		if (timer != null) {
			timer.logDebug("getInBoxForUser", "");
		}

		return coll;
	}

	public EntityList getUnreadInBoxForUser(String param1) {
		Timer timer = this.mLog.isDebugEnabled() ? new Timer(this.mLog) : null;
		UnreadInBoxForUserELO coll = null;

		try {
			coll = (new ListSessionServer(this.getConnection())).getUnreadInBoxForUser(param1);
		} catch (Exception var5) {
			throw new RuntimeException("unable to get Message UnreadInBoxForUser", var5);
		}

		if (timer != null) {
			timer.logDebug("getUnreadInBoxForUser", "");
		}

		return coll;
	}

	public EntityList getSentItemsForUser(String param1) {
		Timer timer = this.mLog.isDebugEnabled() ? new Timer(this.mLog) : null;
		SentItemsForUserELO coll = null;

		try {
			coll = (new ListSessionServer(this.getConnection())).getSentItemsForUser(param1);
		} catch (Exception var5) {
			throw new RuntimeException("unable to get Message SentItemsForUser", var5);
		}

		if (timer != null) {
			timer.logDebug("getSentItemsForUser", "");
		}

		return coll;
	}

	public EntityList getMessageForId(long param1, String param2) {
		StringBuffer key = new StringBuffer();
		key.append("MessageForId");
		key.append('.');
		key.append(String.valueOf(param1));
		key.append('.');
		key.append(param2.toString());
		MessageForIdELO coll = (MessageForIdELO) this.mConnection.getClientCache().get(key.toString());
		if (coll == null) {
			Timer timer = this.mLog.isDebugEnabled() ? new Timer(this.mLog) : null;

			try {
				coll = (new ListSessionServer(this.getConnection())).getMessageForId(param1, param2);
			} catch (Exception var8) {
				throw new RuntimeException("unable to get Message MessageForId", var8);
			}

			this.mConnection.getClientCache().put(key.toString(), coll);
			if (timer != null) {
				timer.logDebug("getMessageForId", "");
			}
		} else {
			this.mLog.debug("getMessageForId", "obtained " + key + " from cache");
		}

		return coll;
	}

	public EntityList getMessageForIdSentItem(long param1, String param2) {
		StringBuffer key = new StringBuffer();
		key.append("MessageForIdSentItem");
		key.append('.');
		key.append(String.valueOf(param1));
		key.append('.');
		key.append(param2.toString());
		MessageForIdSentItemELO coll = (MessageForIdSentItemELO) this.mConnection.getClientCache().get(key.toString());
		if (coll == null) {
			Timer timer = this.mLog.isDebugEnabled() ? new Timer(this.mLog) : null;

			try {
				coll = (new ListSessionServer(this.getConnection())).getMessageForIdSentItem(param1, param2);
			} catch (Exception var8) {
				throw new RuntimeException("unable to get Message MessageForIdSentItem", var8);
			}

			this.mConnection.getClientCache().put(key.toString(), coll);
			if (timer != null) {
				timer.logDebug("getMessageForIdSentItem", "");
			}
		} else {
			this.mLog.debug("getMessageForIdSentItem", "obtained " + key + " from cache");
		}

		return coll;
	}

	public EntityList getMessageCount(long param1) {
		StringBuffer key = new StringBuffer();
		key.append("MessageCount");
		key.append('.');
		key.append(String.valueOf(param1));
		MessageCountELO coll = (MessageCountELO) this.mConnection.getClientCache().get(key.toString());
		if (coll == null) {
			Timer timer = this.mLog.isDebugEnabled() ? new Timer(this.mLog) : null;

			try {
				coll = (new ListSessionServer(this.getConnection())).getMessageCount(param1);
			} catch (Exception var7) {
				throw new RuntimeException("unable to get Message MessageCount", var7);
			}

			this.mConnection.getClientCache().put(key.toString(), coll);
			if (timer != null) {
				timer.logDebug("getMessageCount", "");
			}
		} else {
			this.mLog.debug("getMessageCount", "obtained " + key + " from cache");
		}

		return coll;
	}

	public EntityList getAttatchmentForMessage(long param1) {
		StringBuffer key = new StringBuffer();
		key.append("AttatchmentForMessage");
		key.append('.');
		key.append(String.valueOf(param1));
		AttatchmentForMessageELO coll = (AttatchmentForMessageELO) this.mConnection.getClientCache().get(key.toString());
		if (coll == null) {
			Timer timer = this.mLog.isDebugEnabled() ? new Timer(this.mLog) : null;

			try {
				coll = (new ListSessionServer(this.getConnection())).getAttatchmentForMessage(param1);
			} catch (Exception var7) {
				throw new RuntimeException("unable to get MessageAttatch AttatchmentForMessage", var7);
			}

			this.mConnection.getClientCache().put(key.toString(), coll);
			if (timer != null) {
				timer.logDebug("getAttatchmentForMessage", "");
			}
		} else {
			this.mLog.debug("getAttatchmentForMessage", "obtained " + key + " from cache");
		}

		return coll;
	}

	public EntityList getMessageFromUser(long param1) {
		StringBuffer key = new StringBuffer();
		key.append("MessageFromUser");
		key.append('.');
		key.append(String.valueOf(param1));
		MessageFromUserELO coll = (MessageFromUserELO) this.mConnection.getClientCache().get(key.toString());
		if (coll == null) {
			Timer timer = this.mLog.isDebugEnabled() ? new Timer(this.mLog) : null;

			try {
				coll = (new ListSessionServer(this.getConnection())).getMessageFromUser(param1);
			} catch (Exception var7) {
				throw new RuntimeException("unable to get MessageUser MessageFromUser", var7);
			}

			this.mConnection.getClientCache().put(key.toString(), coll);
			if (timer != null) {
				timer.logDebug("getMessageFromUser", "");
			}
		} else {
			this.mLog.debug("getMessageFromUser", "obtained " + key + " from cache");
		}

		return coll;
	}

	public EntityList getMessageToUser(long param1) {
		StringBuffer key = new StringBuffer();
		key.append("MessageToUser");
		key.append('.');
		key.append(String.valueOf(param1));
		MessageToUserELO coll = (MessageToUserELO) this.mConnection.getClientCache().get(key.toString());
		if (coll == null) {
			Timer timer = this.mLog.isDebugEnabled() ? new Timer(this.mLog) : null;

			try {
				coll = (new ListSessionServer(this.getConnection())).getMessageToUser(param1);
			} catch (Exception var7) {
				throw new RuntimeException("unable to get MessageUser MessageToUser", var7);
			}

			this.mConnection.getClientCache().put(key.toString(), coll);
			if (timer != null) {
				timer.logDebug("getMessageToUser", "");
			}
		} else {
			this.mLog.debug("getMessageToUser", "obtained " + key + " from cache");
		}

		return coll;
	}

	public EntityList getAllMessagesToUser(long param1) {
		StringBuffer key = new StringBuffer();
		key.append("AllMessagesToUser");
		key.append('.');
		key.append(String.valueOf(param1));
		AllMessagesToUserELO coll = (AllMessagesToUserELO) this.mConnection.getClientCache().get(key.toString());
		if (coll == null) {
			Timer timer = this.mLog.isDebugEnabled() ? new Timer(this.mLog) : null;

			try {
				coll = (new ListSessionServer(this.getConnection())).getAllMessagesToUser(param1);
			} catch (Exception var7) {
				throw new RuntimeException("unable to get MessageUser AllMessagesToUser", var7);
			}

			this.mConnection.getClientCache().put(key.toString(), coll);
			if (timer != null) {
				timer.logDebug("getAllMessagesToUser", "");
			}
		} else {
			this.mLog.debug("getAllMessagesToUser", "obtained " + key + " from cache");
		}

		return coll;
	}

	public EntityList getAllRecharges() {
		StringBuffer key = new StringBuffer();
		key.append("AllRecharges");
		AllRechargesELO coll = (AllRechargesELO) this.mConnection.getClientCache().get(key.toString());
		if (coll == null) {
			Timer timer = this.mLog.isDebugEnabled() ? new Timer(this.mLog) : null;

			try {
				coll = (new ListSessionServer(this.getConnection())).getAllRecharges();
			} catch (Exception var5) {
				throw new RuntimeException("unable to get Recharge AllRecharges", var5);
			}

			this.mConnection.getClientCache().put(key.toString(), coll);
			if (timer != null) {
				timer.logDebug("getAllRecharges", "");
			}
		} else {
			this.mLog.debug("getAllRecharges", "obtained " + key + " from cache");
		}

		return coll;
	}

	public EntityList getAllRechargesWithModel(int param1) {
		StringBuffer key = new StringBuffer();
		key.append("AllRechargesWithModel");
		key.append('.');
		key.append(String.valueOf(param1));
		AllRechargesWithModelELO coll = (AllRechargesWithModelELO) this.mConnection.getClientCache().get(key.toString());
		if (coll == null) {
			Timer timer = this.mLog.isDebugEnabled() ? new Timer(this.mLog) : null;

			try {
				coll = (new ListSessionServer(this.getConnection())).getAllRechargesWithModel(param1);
			} catch (Exception var6) {
				throw new RuntimeException("unable to get Recharge AllRechargesWithModel", var6);
			}

			this.mConnection.getClientCache().put(key.toString(), coll);
			if (timer != null) {
				timer.logDebug("getAllRechargesWithModel", "");
			}
		} else {
			this.mLog.debug("getAllRechargesWithModel", "obtained " + key + " from cache");
		}

		return coll;
	}

	public EntityList getSingleRecharge(int param1) {
		StringBuffer key = new StringBuffer();
		key.append("SingleRecharge");
		key.append('.');
		key.append(String.valueOf(param1));
		SingleRechargeELO coll = (SingleRechargeELO) this.mConnection.getClientCache().get(key.toString());
		if (coll == null) {
			Timer timer = this.mLog.isDebugEnabled() ? new Timer(this.mLog) : null;

			try {
				coll = (new ListSessionServer(this.getConnection())).getSingleRecharge(param1);
			} catch (Exception var6) {
				throw new RuntimeException("unable to get Recharge SingleRecharge", var6);
			}

			this.mConnection.getClientCache().put(key.toString(), coll);
			if (timer != null) {
				timer.logDebug("getSingleRecharge", "");
			}
		} else {
			this.mLog.debug("getSingleRecharge", "obtained " + key + " from cache");
		}

		return coll;
	}

	public EntityList getAllRechargeGroups() {
		StringBuffer key = new StringBuffer();
		key.append("AllRechargeGroups");
		AllRechargeGroupsELO coll = (AllRechargeGroupsELO) this.mConnection.getClientCache().get(key.toString());
		if (coll == null) {
			Timer timer = this.mLog.isDebugEnabled() ? new Timer(this.mLog) : null;

			try {
				coll = (new ListSessionServer(this.getConnection())).getAllRechargeGroups();
			} catch (Exception var5) {
				throw new RuntimeException("unable to get RechargeGroup AllRechargeGroups", var5);
			}

			this.mConnection.getClientCache().put(key.toString(), coll);
			if (timer != null) {
				timer.logDebug("getAllRechargeGroups", "");
			}
		} else {
			this.mLog.debug("getAllRechargeGroups", "obtained " + key + " from cache");
		}

		return coll;
	}

	public EntityList getActivitiesForCycleandElement(int param1, Integer param2, int param3) {
		Timer timer = this.mLog.isDebugEnabled() ? new Timer(this.mLog) : null;
		ActivitiesForCycleandElementELO coll = null;

		try {
			coll = (new ListSessionServer(this.getConnection())).getActivitiesForCycleandElement(param1, param2, param3);
		} catch (Exception var7) {
			throw new RuntimeException("unable to get BudgetActivity ActivitiesForCycleandElement", var7);
		}

		if (timer != null) {
			timer.logDebug("getActivitiesForCycleandElement", "");
		}

		return coll;
	}

	public EntityList getActivityDetails(int param1) {
		StringBuffer key = new StringBuffer();
		key.append("ActivityDetails");
		key.append('.');
		key.append(String.valueOf(param1));
		ActivityDetailsELO coll = (ActivityDetailsELO) this.mConnection.getClientCache().get(key.toString());
		if (coll == null) {
			Timer timer = this.mLog.isDebugEnabled() ? new Timer(this.mLog) : null;

			try {
				coll = (new ListSessionServer(this.getConnection())).getActivityDetails(param1);
			} catch (Exception var6) {
				throw new RuntimeException("unable to get BudgetActivity ActivityDetails", var6);
			}

			this.mConnection.getClientCache().put(key.toString(), coll);
			if (timer != null) {
				timer.logDebug("getActivityDetails", "");
			}
		} else {
			this.mLog.debug("getActivityDetails", "obtained " + key + " from cache");
		}

		return coll;
	}

	public EntityList getActivityFullDetails(int param1) {
		StringBuffer key = new StringBuffer();
		key.append("ActivityFullDetails");
		key.append('.');
		key.append(String.valueOf(param1));
		ActivityFullDetailsELO coll = (ActivityFullDetailsELO) this.mConnection.getClientCache().get(key.toString());
		if (coll == null) {
			Timer timer = this.mLog.isDebugEnabled() ? new Timer(this.mLog) : null;

			try {
				coll = (new ListSessionServer(this.getConnection())).getActivityFullDetails(param1);
			} catch (Exception var6) {
				throw new RuntimeException("unable to get BudgetActivity ActivityFullDetails", var6);
			}

			this.mConnection.getClientCache().put(key.toString(), coll);
			if (timer != null) {
				timer.logDebug("getActivityFullDetails", "");
			}
		} else {
			this.mLog.debug("getActivityFullDetails", "obtained " + key + " from cache");
		}

		return coll;
	}

	public EntityList getAllVirementRequests() {
		StringBuffer key = new StringBuffer();
		key.append("AllVirementRequests");
		AllVirementRequestsELO coll = (AllVirementRequestsELO) this.mConnection.getClientCache().get(key.toString());
		if (coll == null) {
			Timer timer = this.mLog.isDebugEnabled() ? new Timer(this.mLog) : null;

			try {
				coll = (new ListSessionServer(this.getConnection())).getAllVirementRequests();
			} catch (Exception var5) {
				throw new RuntimeException("unable to get VirementRequest AllVirementRequests", var5);
			}

			this.mConnection.getClientCache().put(key.toString(), coll);
			if (timer != null) {
				timer.logDebug("getAllVirementRequests", "");
			}
		} else {
			this.mLog.debug("getAllVirementRequests", "obtained " + key + " from cache");
		}

		return coll;
	}

	public EntityList getAllVirementRequestGroups() {
		StringBuffer key = new StringBuffer();
		key.append("AllVirementRequestGroups");
		AllVirementRequestGroupsELO coll = (AllVirementRequestGroupsELO) this.mConnection.getClientCache().get(key.toString());
		if (coll == null) {
			Timer timer = this.mLog.isDebugEnabled() ? new Timer(this.mLog) : null;

			try {
				coll = (new ListSessionServer(this.getConnection())).getAllVirementRequestGroups();
			} catch (Exception var5) {
				throw new RuntimeException("unable to get VirementRequestGroup AllVirementRequestGroups", var5);
			}

			this.mConnection.getClientCache().put(key.toString(), coll);
			if (timer != null) {
				timer.logDebug("getAllVirementRequestGroups", "");
			}
		} else {
			this.mLog.debug("getAllVirementRequestGroups", "obtained " + key + " from cache");
		}

		return coll;
	}

	public EntityList getAllReportTypes() {
		StringBuffer key = new StringBuffer();
		key.append("AllReportTypes");
		AllReportTypesELO coll = (AllReportTypesELO) this.mConnection.getClientCache().get(key.toString());
		if (coll == null) {
			Timer timer = this.mLog.isDebugEnabled() ? new Timer(this.mLog) : null;

			try {
				coll = (new ListSessionServer(this.getConnection())).getAllReportTypes();
			} catch (Exception var5) {
				throw new RuntimeException("unable to get ReportType AllReportTypes", var5);
			}

			this.mConnection.getClientCache().put(key.toString(), coll);
			if (timer != null) {
				timer.logDebug("getAllReportTypes", "");
			}
		} else {
			this.mLog.debug("getAllReportTypes", "obtained " + key + " from cache");
		}

		return coll;
	}

	public EntityList getAllReportTypeParams() {
		StringBuffer key = new StringBuffer();
		key.append("AllReportTypeParams");
		AllReportTypeParamsELO coll = (AllReportTypeParamsELO) this.mConnection.getClientCache().get(key.toString());
		if (coll == null) {
			Timer timer = this.mLog.isDebugEnabled() ? new Timer(this.mLog) : null;

			try {
				coll = (new ListSessionServer(this.getConnection())).getAllReportTypeParams();
			} catch (Exception var5) {
				throw new RuntimeException("unable to get ReportTypeParam AllReportTypeParams", var5);
			}

			this.mConnection.getClientCache().put(key.toString(), coll);
			if (timer != null) {
				timer.logDebug("getAllReportTypeParams", "");
			}
		} else {
			this.mLog.debug("getAllReportTypeParams", "obtained " + key + " from cache");
		}

		return coll;
	}

	public EntityList getAllReportTypeParamsforType(int param1) {
		StringBuffer key = new StringBuffer();
		key.append("AllReportTypeParamsforType");
		key.append('.');
		key.append(String.valueOf(param1));
		AllReportTypeParamsforTypeELO coll = (AllReportTypeParamsforTypeELO) this.mConnection.getClientCache().get(key.toString());
		if (coll == null) {
			Timer timer = this.mLog.isDebugEnabled() ? new Timer(this.mLog) : null;

			try {
				coll = (new ListSessionServer(this.getConnection())).getAllReportTypeParamsforType(param1);
			} catch (Exception var6) {
				throw new RuntimeException("unable to get ReportTypeParam AllReportTypeParamsforType", var6);
			}

			this.mConnection.getClientCache().put(key.toString(), coll);
			if (timer != null) {
				timer.logDebug("getAllReportTypeParamsforType", "");
			}
		} else {
			this.mLog.debug("getAllReportTypeParamsforType", "obtained " + key + " from cache");
		}

		return coll;
	}

	public EntityList getAllReportDefinitions() {
		StringBuffer key = new StringBuffer();
		key.append("AllReportDefinitions");
		AllReportDefinitionsELO coll = (AllReportDefinitionsELO) this.mConnection.getClientCache().get(key.toString());
		if (coll == null) {
			Timer timer = this.mLog.isDebugEnabled() ? new Timer(this.mLog) : null;

			try {
				coll = (new ListSessionServer(this.getConnection())).getAllReportDefinitions();
			} catch (Exception var5) {
				throw new RuntimeException("unable to get ReportDefinition AllReportDefinitions", var5);
			}

			this.mConnection.getClientCache().put(key.toString(), coll);
			if (timer != null) {
				timer.logDebug("getAllReportDefinitions", "");
			}
		} else {
			this.mLog.debug("getAllReportDefinitions", "obtained " + key + " from cache");
		}

		return coll;
	}

	public EntityList getAllReportDefinitionsForLoggedUser() {
		EntityList coll;
		try {
			coll = (new ListSessionServer(this.getConnection())).getAllReportDefinitionsForLoggedUser();
		} catch (Exception var5) {
			throw new RuntimeException("unable to get ReportDefinition AllReportDefinitionsForLoggedUser", var5);
		}

		return coll;
	}

	public EntityList getAllPublicReportByType(int param1) {
		StringBuffer key = new StringBuffer();
		key.append("AllPublicReportByType");
		key.append('.');
		key.append(String.valueOf(param1));
		AllPublicReportByTypeELO coll = (AllPublicReportByTypeELO) this.mConnection.getClientCache().get(key.toString());
		if (coll == null) {
			Timer timer = this.mLog.isDebugEnabled() ? new Timer(this.mLog) : null;

			try {
				coll = (new ListSessionServer(this.getConnection())).getAllPublicReportByType(param1);
			} catch (Exception var6) {
				throw new RuntimeException("unable to get ReportDefinition AllPublicReportByType", var6);
			}

			this.mConnection.getClientCache().put(key.toString(), coll);
			if (timer != null) {
				timer.logDebug("getAllPublicReportByType", "");
			}
		} else {
			this.mLog.debug("getAllPublicReportByType", "obtained " + key + " from cache");
		}

		return coll;
	}

	public EntityList getReportDefinitionForVisId(String param1) {
		StringBuffer key = new StringBuffer();
		key.append("ReportDefinitionForVisId");
		key.append('.');
		key.append(param1.toString());
		ReportDefinitionForVisIdELO coll = (ReportDefinitionForVisIdELO) this.mConnection.getClientCache().get(key.toString());
		if (coll == null) {
			Timer timer = this.mLog.isDebugEnabled() ? new Timer(this.mLog) : null;

			try {
				coll = (new ListSessionServer(this.getConnection())).getReportDefinitionForVisId(param1);
			} catch (Exception var6) {
				throw new RuntimeException("unable to get ReportDefinition ReportDefinitionForVisId", var6);
			}

			this.mConnection.getClientCache().put(key.toString(), coll);
			if (timer != null) {
				timer.logDebug("getReportDefinitionForVisId", "");
			}
		} else {
			this.mLog.debug("getReportDefinitionForVisId", "obtained " + key + " from cache");
		}

		return coll;
	}

	public EntityList getAllReportDefFormcByReportTemplateId(int param1) {
		StringBuffer key = new StringBuffer();
		key.append("AllReportDefFormcByReportTemplateId");
		key.append('.');
		key.append(String.valueOf(param1));
		AllReportDefFormcByReportTemplateIdELO coll = (AllReportDefFormcByReportTemplateIdELO) this.mConnection.getClientCache().get(key.toString());
		if (coll == null) {
			Timer timer = this.mLog.isDebugEnabled() ? new Timer(this.mLog) : null;

			try {
				coll = (new ListSessionServer(this.getConnection())).getAllReportDefFormcByReportTemplateId(param1);
			} catch (Exception var6) {
				throw new RuntimeException("unable to get ReportDefForm AllReportDefFormcByReportTemplateId", var6);
			}

			this.mConnection.getClientCache().put(key.toString(), coll);
			if (timer != null) {
				timer.logDebug("getAllReportDefFormcByReportTemplateId", "");
			}
		} else {
			this.mLog.debug("getAllReportDefFormcByReportTemplateId", "obtained " + key + " from cache");
		}

		return coll;
	}

	public EntityList getAllReportDefFormcByModelId(int param1) {
		StringBuffer key = new StringBuffer();
		key.append("AllReportDefFormcByModelId");
		key.append('.');
		key.append(String.valueOf(param1));
		AllReportDefFormcByModelIdELO coll = (AllReportDefFormcByModelIdELO) this.mConnection.getClientCache().get(key.toString());
		if (coll == null) {
			Timer timer = this.mLog.isDebugEnabled() ? new Timer(this.mLog) : null;

			try {
				coll = (new ListSessionServer(this.getConnection())).getAllReportDefFormcByModelId(param1);
			} catch (Exception var6) {
				throw new RuntimeException("unable to get ReportDefForm AllReportDefFormcByModelId", var6);
			}

			this.mConnection.getClientCache().put(key.toString(), coll);
			if (timer != null) {
				timer.logDebug("getAllReportDefFormcByModelId", "");
			}
		} else {
			this.mLog.debug("getAllReportDefFormcByModelId", "obtained " + key + " from cache");
		}

		return coll;
	}

	public EntityList getCheckFormIsUsed(int param1) {
		StringBuffer key = new StringBuffer();
		key.append("CheckFormIsUsed");
		key.append('.');
		key.append(String.valueOf(param1));
		CheckFormIsUsedELO coll = (CheckFormIsUsedELO) this.mConnection.getClientCache().get(key.toString());
		if (coll == null) {
			Timer timer = this.mLog.isDebugEnabled() ? new Timer(this.mLog) : null;

			try {
				coll = (new ListSessionServer(this.getConnection())).getCheckFormIsUsed(param1);
			} catch (Exception var6) {
				throw new RuntimeException("unable to get ReportDefForm CheckFormIsUsed", var6);
			}

			this.mConnection.getClientCache().put(key.toString(), coll);
			if (timer != null) {
				timer.logDebug("getCheckFormIsUsed", "");
			}
		} else {
			this.mLog.debug("getCheckFormIsUsed", "obtained " + key + " from cache");
		}

		return coll;
	}

	public EntityList getAllReportDefMappedExcelcByReportTemplateId(int param1) {
		StringBuffer key = new StringBuffer();
		key.append("AllReportDefMappedExcelcByReportTemplateId");
		key.append('.');
		key.append(String.valueOf(param1));
		AllReportDefMappedExcelcByReportTemplateIdELO coll = (AllReportDefMappedExcelcByReportTemplateIdELO) this.mConnection.getClientCache().get(key.toString());
		if (coll == null) {
			Timer timer = this.mLog.isDebugEnabled() ? new Timer(this.mLog) : null;

			try {
				coll = (new ListSessionServer(this.getConnection())).getAllReportDefMappedExcelcByReportTemplateId(param1);
			} catch (Exception var6) {
				throw new RuntimeException("unable to get ReportDefMappedExcel AllReportDefMappedExcelcByReportTemplateId", var6);
			}

			this.mConnection.getClientCache().put(key.toString(), coll);
			if (timer != null) {
				timer.logDebug("getAllReportDefMappedExcelcByReportTemplateId", "");
			}
		} else {
			this.mLog.debug("getAllReportDefMappedExcelcByReportTemplateId", "obtained " + key + " from cache");
		}

		return coll;
	}

	public EntityList getAllReportDefMappedExcelcByModelId(int param1) {
		StringBuffer key = new StringBuffer();
		key.append("AllReportDefMappedExcelcByModelId");
		key.append('.');
		key.append(String.valueOf(param1));
		AllReportDefMappedExcelcByModelIdELO coll = (AllReportDefMappedExcelcByModelIdELO) this.mConnection.getClientCache().get(key.toString());
		if (coll == null) {
			Timer timer = this.mLog.isDebugEnabled() ? new Timer(this.mLog) : null;

			try {
				coll = (new ListSessionServer(this.getConnection())).getAllReportDefMappedExcelcByModelId(param1);
			} catch (Exception var6) {
				throw new RuntimeException("unable to get ReportDefMappedExcel AllReportDefMappedExcelcByModelId", var6);
			}

			this.mConnection.getClientCache().put(key.toString(), coll);
			if (timer != null) {
				timer.logDebug("getAllReportDefMappedExcelcByModelId", "");
			}
		} else {
			this.mLog.debug("getAllReportDefMappedExcelcByModelId", "obtained " + key + " from cache");
		}

		return coll;
	}

	public EntityList getAllReportDefCalcByCCDeploymentId(int param1) {
		StringBuffer key = new StringBuffer();
		key.append("AllReportDefCalcByCCDeploymentId");
		key.append('.');
		key.append(String.valueOf(param1));
		AllReportDefCalcByCCDeploymentIdELO coll = (AllReportDefCalcByCCDeploymentIdELO) this.mConnection.getClientCache().get(key.toString());
		if (coll == null) {
			Timer timer = this.mLog.isDebugEnabled() ? new Timer(this.mLog) : null;

			try {
				coll = (new ListSessionServer(this.getConnection())).getAllReportDefCalcByCCDeploymentId(param1);
			} catch (Exception var6) {
				throw new RuntimeException("unable to get ReportDefCalculator AllReportDefCalcByCCDeploymentId", var6);
			}

			this.mConnection.getClientCache().put(key.toString(), coll);
			if (timer != null) {
				timer.logDebug("getAllReportDefCalcByCCDeploymentId", "");
			}
		} else {
			this.mLog.debug("getAllReportDefCalcByCCDeploymentId", "obtained " + key + " from cache");
		}

		return coll;
	}

	public EntityList getAllReportDefCalcByReportTemplateId(int param1) {
		StringBuffer key = new StringBuffer();
		key.append("AllReportDefCalcByReportTemplateId");
		key.append('.');
		key.append(String.valueOf(param1));
		AllReportDefCalcByReportTemplateIdELO coll = (AllReportDefCalcByReportTemplateIdELO) this.mConnection.getClientCache().get(key.toString());
		if (coll == null) {
			Timer timer = this.mLog.isDebugEnabled() ? new Timer(this.mLog) : null;

			try {
				coll = (new ListSessionServer(this.getConnection())).getAllReportDefCalcByReportTemplateId(param1);
			} catch (Exception var6) {
				throw new RuntimeException("unable to get ReportDefCalculator AllReportDefCalcByReportTemplateId", var6);
			}

			this.mConnection.getClientCache().put(key.toString(), coll);
			if (timer != null) {
				timer.logDebug("getAllReportDefCalcByReportTemplateId", "");
			}
		} else {
			this.mLog.debug("getAllReportDefCalcByReportTemplateId", "obtained " + key + " from cache");
		}

		return coll;
	}

	public EntityList getAllReportDefCalcByModelId(int param1) {
		StringBuffer key = new StringBuffer();
		key.append("AllReportDefCalcByModelId");
		key.append('.');
		key.append(String.valueOf(param1));
		AllReportDefCalcByModelIdELO coll = (AllReportDefCalcByModelIdELO) this.mConnection.getClientCache().get(key.toString());
		if (coll == null) {
			Timer timer = this.mLog.isDebugEnabled() ? new Timer(this.mLog) : null;

			try {
				coll = (new ListSessionServer(this.getConnection())).getAllReportDefCalcByModelId(param1);
			} catch (Exception var6) {
				throw new RuntimeException("unable to get ReportDefCalculator AllReportDefCalcByModelId", var6);
			}

			this.mConnection.getClientCache().put(key.toString(), coll);
			if (timer != null) {
				timer.logDebug("getAllReportDefCalcByModelId", "");
			}
		} else {
			this.mLog.debug("getAllReportDefCalcByModelId", "obtained " + key + " from cache");
		}

		return coll;
	}

	public EntityList getAllReportDefSummaryCalcByCCDeploymentId(int param1) {
		StringBuffer key = new StringBuffer();
		key.append("AllReportDefSummaryCalcByCCDeploymentId");
		key.append('.');
		key.append(String.valueOf(param1));
		AllReportDefSummaryCalcByCCDeploymentIdELO coll = (AllReportDefSummaryCalcByCCDeploymentIdELO) this.mConnection.getClientCache().get(key.toString());
		if (coll == null) {
			Timer timer = this.mLog.isDebugEnabled() ? new Timer(this.mLog) : null;

			try {
				coll = (new ListSessionServer(this.getConnection())).getAllReportDefSummaryCalcByCCDeploymentId(param1);
			} catch (Exception var6) {
				throw new RuntimeException("unable to get ReportDefSummaryCalc AllReportDefSummaryCalcByCCDeploymentId", var6);
			}

			this.mConnection.getClientCache().put(key.toString(), coll);
			if (timer != null) {
				timer.logDebug("getAllReportDefSummaryCalcByCCDeploymentId", "");
			}
		} else {
			this.mLog.debug("getAllReportDefSummaryCalcByCCDeploymentId", "obtained " + key + " from cache");
		}

		return coll;
	}

	public EntityList getAllReportDefSummaryCalcByReportTemplateId(int param1) {
		StringBuffer key = new StringBuffer();
		key.append("AllReportDefSummaryCalcByReportTemplateId");
		key.append('.');
		key.append(String.valueOf(param1));
		AllReportDefSummaryCalcByReportTemplateIdELO coll = (AllReportDefSummaryCalcByReportTemplateIdELO) this.mConnection.getClientCache().get(key.toString());
		if (coll == null) {
			Timer timer = this.mLog.isDebugEnabled() ? new Timer(this.mLog) : null;

			try {
				coll = (new ListSessionServer(this.getConnection())).getAllReportDefSummaryCalcByReportTemplateId(param1);
			} catch (Exception var6) {
				throw new RuntimeException("unable to get ReportDefSummaryCalc AllReportDefSummaryCalcByReportTemplateId", var6);
			}

			this.mConnection.getClientCache().put(key.toString(), coll);
			if (timer != null) {
				timer.logDebug("getAllReportDefSummaryCalcByReportTemplateId", "");
			}
		} else {
			this.mLog.debug("getAllReportDefSummaryCalcByReportTemplateId", "obtained " + key + " from cache");
		}

		return coll;
	}

	public EntityList getAllReportDefSummaryCalcByModelId(int param1) {
		StringBuffer key = new StringBuffer();
		key.append("AllReportDefSummaryCalcByModelId");
		key.append('.');
		key.append(String.valueOf(param1));
		AllReportDefSummaryCalcByModelIdELO coll = (AllReportDefSummaryCalcByModelIdELO) this.mConnection.getClientCache().get(key.toString());
		if (coll == null) {
			Timer timer = this.mLog.isDebugEnabled() ? new Timer(this.mLog) : null;

			try {
				coll = (new ListSessionServer(this.getConnection())).getAllReportDefSummaryCalcByModelId(param1);
			} catch (Exception var6) {
				throw new RuntimeException("unable to get ReportDefSummaryCalc AllReportDefSummaryCalcByModelId", var6);
			}

			this.mConnection.getClientCache().put(key.toString(), coll);
			if (timer != null) {
				timer.logDebug("getAllReportDefSummaryCalcByModelId", "");
			}
		} else {
			this.mLog.debug("getAllReportDefSummaryCalcByModelId", "obtained " + key + " from cache");
		}

		return coll;
	}

	public EntityList getAllReportTemplates() {
		StringBuffer key = new StringBuffer();
		key.append("AllReportTemplates");
		AllReportTemplatesELO coll = (AllReportTemplatesELO) this.mConnection.getClientCache().get(key.toString());
		if (coll == null) {
			Timer timer = this.mLog.isDebugEnabled() ? new Timer(this.mLog) : null;

			try {
				coll = (new ListSessionServer(this.getConnection())).getAllReportTemplates();
			} catch (Exception var5) {
				throw new RuntimeException("unable to get ReportTemplate AllReportTemplates", var5);
			}

			this.mConnection.getClientCache().put(key.toString(), coll);
			if (timer != null) {
				timer.logDebug("getAllReportTemplates", "");
			}
		} else {
			this.mLog.debug("getAllReportTemplates", "obtained " + key + " from cache");
		}

		return coll;
	}

	public EntityList getAllReportMappingTemplates() {
		StringBuffer key = new StringBuffer();
		key.append("AllReportMappingTemplates");
		AllReportMappingTemplatesELO coll = (AllReportMappingTemplatesELO) this.mConnection.getClientCache().get(key.toString());
		if (coll == null) {
			Timer timer = this.mLog.isDebugEnabled() ? new Timer(this.mLog) : null;

			try {
				coll = (new ListSessionServer(this.getConnection())).getAllReportMappingTemplates();
			} catch (Exception var5) {
				throw new RuntimeException("unable to get ReportMappingTemplate AllReportMappingTemplates", var5);
			}

			this.mConnection.getClientCache().put(key.toString(), coll);
			if (timer != null) {
				timer.logDebug("getAllReportMappingTemplates", "");
			}
		} else {
			this.mLog.debug("getAllReportMappingTemplates", "obtained " + key + " from cache");
		}

		return coll;
	}

	public EntityList getAllExternalDestinations() {
		StringBuffer key = new StringBuffer();
		key.append("AllExternalDestinations");
		AllExternalDestinationsELO coll = (AllExternalDestinationsELO) this.mConnection.getClientCache().get(key.toString());
		if (coll == null) {
			Timer timer = this.mLog.isDebugEnabled() ? new Timer(this.mLog) : null;

			try {
				coll = (new ListSessionServer(this.getConnection())).getAllExternalDestinations();
			} catch (Exception var5) {
				throw new RuntimeException("unable to get ExternalDestination AllExternalDestinations", var5);
			}

			this.mConnection.getClientCache().put(key.toString(), coll);
			if (timer != null) {
				timer.logDebug("getAllExternalDestinations", "");
			}
		} else {
			this.mLog.debug("getAllExternalDestinations", "obtained " + key + " from cache");
		}

		return coll;
	}

	public EntityList getAllExternalDestinationDetails() {
		StringBuffer key = new StringBuffer();
		key.append("AllExternalDestinationDetails");
		AllExternalDestinationDetailsELO coll = (AllExternalDestinationDetailsELO) this.mConnection.getClientCache().get(key.toString());
		if (coll == null) {
			Timer timer = this.mLog.isDebugEnabled() ? new Timer(this.mLog) : null;

			try {
				coll = (new ListSessionServer(this.getConnection())).getAllExternalDestinationDetails();
			} catch (Exception var5) {
				throw new RuntimeException("unable to get ExternalDestination AllExternalDestinationDetails", var5);
			}

			this.mConnection.getClientCache().put(key.toString(), coll);
			if (timer != null) {
				timer.logDebug("getAllExternalDestinationDetails", "");
			}
		} else {
			this.mLog.debug("getAllExternalDestinationDetails", "obtained " + key + " from cache");
		}

		return coll;
	}

	public EntityList getAllUsersForExternalDestinationId(int param1) {
		StringBuffer key = new StringBuffer();
		key.append("AllUsersForExternalDestinationId");
		key.append('.');
		key.append(String.valueOf(param1));
		AllUsersForExternalDestinationIdELO coll = (AllUsersForExternalDestinationIdELO) this.mConnection.getClientCache().get(key.toString());
		if (coll == null) {
			Timer timer = this.mLog.isDebugEnabled() ? new Timer(this.mLog) : null;

			try {
				coll = (new ListSessionServer(this.getConnection())).getAllUsersForExternalDestinationId(param1);
			} catch (Exception var6) {
				throw new RuntimeException("unable to get ExternalDestination AllUsersForExternalDestinationId", var6);
			}

			this.mConnection.getClientCache().put(key.toString(), coll);
			if (timer != null) {
				timer.logDebug("getAllUsersForExternalDestinationId", "");
			}
		} else {
			this.mLog.debug("getAllUsersForExternalDestinationId", "obtained " + key + " from cache");
		}

		return coll;
	}

	public EntityList getAllInternalDestinations() {
		StringBuffer key = new StringBuffer();
		key.append("AllInternalDestinations");
		AllInternalDestinationsELO coll = (AllInternalDestinationsELO) this.mConnection.getClientCache().get(key.toString());
		if (coll == null) {
			Timer timer = this.mLog.isDebugEnabled() ? new Timer(this.mLog) : null;

			try {
				coll = (new ListSessionServer(this.getConnection())).getAllInternalDestinations();
			} catch (Exception var5) {
				throw new RuntimeException("unable to get InternalDestination AllInternalDestinations", var5);
			}

			this.mConnection.getClientCache().put(key.toString(), coll);
			if (timer != null) {
				timer.logDebug("getAllInternalDestinations", "");
			}
		} else {
			this.mLog.debug("getAllInternalDestinations", "obtained " + key + " from cache");
		}

		return coll;
	}

	public EntityList getAllInternalDestinationDetails() {
		StringBuffer key = new StringBuffer();
		key.append("AllInternalDestinationDetails");
		AllInternalDestinationDetailsELO coll = (AllInternalDestinationDetailsELO) this.mConnection.getClientCache().get(key.toString());
		if (coll == null) {
			Timer timer = this.mLog.isDebugEnabled() ? new Timer(this.mLog) : null;

			try {
				coll = (new ListSessionServer(this.getConnection())).getAllInternalDestinationDetails();
			} catch (Exception var5) {
				throw new RuntimeException("unable to get InternalDestination AllInternalDestinationDetails", var5);
			}

			this.mConnection.getClientCache().put(key.toString(), coll);
			if (timer != null) {
				timer.logDebug("getAllInternalDestinationDetails", "");
			}
		} else {
			this.mLog.debug("getAllInternalDestinationDetails", "obtained " + key + " from cache");
		}

		return coll;
	}

	public EntityList getAllUsersForInternalDestinationId(int param1) {
		StringBuffer key = new StringBuffer();
		key.append("AllUsersForInternalDestinationId");
		key.append('.');
		key.append(String.valueOf(param1));
		AllUsersForInternalDestinationIdELO coll = (AllUsersForInternalDestinationIdELO) this.mConnection.getClientCache().get(key.toString());
		if (coll == null) {
			Timer timer = this.mLog.isDebugEnabled() ? new Timer(this.mLog) : null;

			try {
				coll = (new ListSessionServer(this.getConnection())).getAllUsersForInternalDestinationId(param1);
			} catch (Exception var6) {
				throw new RuntimeException("unable to get InternalDestination AllUsersForInternalDestinationId", var6);
			}

			this.mConnection.getClientCache().put(key.toString(), coll);
			if (timer != null) {
				timer.logDebug("getAllUsersForInternalDestinationId", "");
			}
		} else {
			this.mLog.debug("getAllUsersForInternalDestinationId", "obtained " + key + " from cache");
		}

		return coll;
	}

	public EntityList getAllInternalDestinationUsers() {
		StringBuffer key = new StringBuffer();
		key.append("AllInternalDestinationUsers");
		AllInternalDestinationUsersELO coll = (AllInternalDestinationUsersELO) this.mConnection.getClientCache().get(key.toString());
		if (coll == null) {
			Timer timer = this.mLog.isDebugEnabled() ? new Timer(this.mLog) : null;

			try {
				coll = (new ListSessionServer(this.getConnection())).getAllInternalDestinationUsers();
			} catch (Exception var5) {
				throw new RuntimeException("unable to get InternalDestinationUsers AllInternalDestinationUsers", var5);
			}

			this.mConnection.getClientCache().put(key.toString(), coll);
			if (timer != null) {
				timer.logDebug("getAllInternalDestinationUsers", "");
			}
		} else {
			this.mLog.debug("getAllInternalDestinationUsers", "obtained " + key + " from cache");
		}

		return coll;
	}

	public EntityList getCheckInternalDestinationUsers(int param1) {
		StringBuffer key = new StringBuffer();
		key.append("CheckInternalDestinationUsers");
		key.append('.');
		key.append(String.valueOf(param1));
		CheckInternalDestinationUsersELO coll = (CheckInternalDestinationUsersELO) this.mConnection.getClientCache().get(key.toString());
		if (coll == null) {
			Timer timer = this.mLog.isDebugEnabled() ? new Timer(this.mLog) : null;

			try {
				coll = (new ListSessionServer(this.getConnection())).getCheckInternalDestinationUsers(param1);
			} catch (Exception var6) {
				throw new RuntimeException("unable to get InternalDestinationUsers CheckInternalDestinationUsers", var6);
			}

			this.mConnection.getClientCache().put(key.toString(), coll);
			if (timer != null) {
				timer.logDebug("getCheckInternalDestinationUsers", "");
			}
		} else {
			this.mLog.debug("getCheckInternalDestinationUsers", "obtained " + key + " from cache");
		}

		return coll;
	}

	public EntityList getAllDistributions() {
		StringBuffer key = new StringBuffer();
		key.append("AllDistributions");
		AllDistributionsELO coll = (AllDistributionsELO) this.mConnection.getClientCache().get(key.toString());
		if (coll == null) {
			Timer timer = this.mLog.isDebugEnabled() ? new Timer(this.mLog) : null;

			try {
				coll = (new ListSessionServer(this.getConnection())).getAllDistributions();
			} catch (Exception var5) {
				throw new RuntimeException("unable to get Distribution AllDistributions", var5);
			}

			this.mConnection.getClientCache().put(key.toString(), coll);
			if (timer != null) {
				timer.logDebug("getAllDistributions", "");
			}
		} else {
			this.mLog.debug("getAllDistributions", "obtained " + key + " from cache");
		}

		return coll;
	}

	public EntityList getDistributionForVisId(String param1) {
		StringBuffer key = new StringBuffer();
		key.append("DistributionForVisId");
		key.append('.');
		key.append(param1.toString());
		DistributionForVisIdELO coll = (DistributionForVisIdELO) this.mConnection.getClientCache().get(key.toString());
		if (coll == null) {
			Timer timer = this.mLog.isDebugEnabled() ? new Timer(this.mLog) : null;

			try {
				coll = (new ListSessionServer(this.getConnection())).getDistributionForVisId(param1);
			} catch (Exception var6) {
				throw new RuntimeException("unable to get Distribution DistributionForVisId", var6);
			}

			this.mConnection.getClientCache().put(key.toString(), coll);
			if (timer != null) {
				timer.logDebug("getDistributionForVisId", "");
			}
		} else {
			this.mLog.debug("getDistributionForVisId", "obtained " + key + " from cache");
		}

		return coll;
	}

	public EntityList getDistributionDetailsForVisId(String param1) {
		StringBuffer key = new StringBuffer();
		key.append("DistributionDetailsForVisId");
		key.append('.');
		key.append(param1.toString());
		DistributionDetailsForVisIdELO coll = (DistributionDetailsForVisIdELO) this.mConnection.getClientCache().get(key.toString());
		if (coll == null) {
			Timer timer = this.mLog.isDebugEnabled() ? new Timer(this.mLog) : null;

			try {
				coll = (new ListSessionServer(this.getConnection())).getDistributionDetailsForVisId(param1);
			} catch (Exception var6) {
				throw new RuntimeException("unable to get Distribution DistributionDetailsForVisId", var6);
			}

			this.mConnection.getClientCache().put(key.toString(), coll);
			if (timer != null) {
				timer.logDebug("getDistributionDetailsForVisId", "");
			}
		} else {
			this.mLog.debug("getDistributionDetailsForVisId", "obtained " + key + " from cache");
		}

		return coll;
	}

	public EntityList getCheckInternalDestination(int param1) {
		StringBuffer key = new StringBuffer();
		key.append("CheckInternalDestination");
		key.append('.');
		key.append(String.valueOf(param1));
		CheckInternalDestinationELO coll = (CheckInternalDestinationELO) this.mConnection.getClientCache().get(key.toString());
		if (coll == null) {
			Timer timer = this.mLog.isDebugEnabled() ? new Timer(this.mLog) : null;

			try {
				coll = (new ListSessionServer(this.getConnection())).getCheckInternalDestination(param1);
			} catch (Exception var6) {
				throw new RuntimeException("unable to get DistributionLink CheckInternalDestination", var6);
			}

			this.mConnection.getClientCache().put(key.toString(), coll);
			if (timer != null) {
				timer.logDebug("getCheckInternalDestination", "");
			}
		} else {
			this.mLog.debug("getCheckInternalDestination", "obtained " + key + " from cache");
		}

		return coll;
	}

	public EntityList getCheckExternalDestination(int param1) {
		StringBuffer key = new StringBuffer();
		key.append("CheckExternalDestination");
		key.append('.');
		key.append(String.valueOf(param1));
		CheckExternalDestinationELO coll = (CheckExternalDestinationELO) this.mConnection.getClientCache().get(key.toString());
		if (coll == null) {
			Timer timer = this.mLog.isDebugEnabled() ? new Timer(this.mLog) : null;

			try {
				coll = (new ListSessionServer(this.getConnection())).getCheckExternalDestination(param1);
			} catch (Exception var6) {
				throw new RuntimeException("unable to get DistributionLink CheckExternalDestination", var6);
			}

			this.mConnection.getClientCache().put(key.toString(), coll);
			if (timer != null) {
				timer.logDebug("getCheckExternalDestination", "");
			}
		} else {
			this.mLog.debug("getCheckExternalDestination", "obtained " + key + " from cache");
		}

		return coll;
	}

	public EntityList getAllReportPacks() {
		StringBuffer key = new StringBuffer();
		key.append("AllReportPacks");
		AllReportPacksELO coll = (AllReportPacksELO) this.mConnection.getClientCache().get(key.toString());
		if (coll == null) {
			Timer timer = this.mLog.isDebugEnabled() ? new Timer(this.mLog) : null;

			try {
				coll = (new ListSessionServer(this.getConnection())).getAllReportPacks();
			} catch (Exception var5) {
				throw new RuntimeException("unable to get ReportPack AllReportPacks", var5);
			}

			this.mConnection.getClientCache().put(key.toString(), coll);
			if (timer != null) {
				timer.logDebug("getAllReportPacks", "");
			}
		} else {
			this.mLog.debug("getAllReportPacks", "obtained " + key + " from cache");
		}

		return coll;
	}

	public EntityList getReportDefDistList(String param1) {
		StringBuffer key = new StringBuffer();
		key.append("ReportDefDistList");
		key.append('.');
		key.append(param1.toString());
		ReportDefDistListELO coll = (ReportDefDistListELO) this.mConnection.getClientCache().get(key.toString());
		if (coll == null) {
			Timer timer = this.mLog.isDebugEnabled() ? new Timer(this.mLog) : null;

			try {
				coll = (new ListSessionServer(this.getConnection())).getReportDefDistList(param1);
			} catch (Exception var6) {
				throw new RuntimeException("unable to get ReportPack ReportDefDistList", var6);
			}

			this.mConnection.getClientCache().put(key.toString(), coll);
			if (timer != null) {
				timer.logDebug("getReportDefDistList", "");
			}
		} else {
			this.mLog.debug("getReportDefDistList", "obtained " + key + " from cache");
		}

		return coll;
	}

	public EntityList getCheckReportDef(int param1) {
		StringBuffer key = new StringBuffer();
		key.append("CheckReportDef");
		key.append('.');
		key.append(String.valueOf(param1));
		CheckReportDefELO coll = (CheckReportDefELO) this.mConnection.getClientCache().get(key.toString());
		if (coll == null) {
			Timer timer = this.mLog.isDebugEnabled() ? new Timer(this.mLog) : null;

			try {
				coll = (new ListSessionServer(this.getConnection())).getCheckReportDef(param1);
			} catch (Exception var6) {
				throw new RuntimeException("unable to get ReportPackLink CheckReportDef", var6);
			}

			this.mConnection.getClientCache().put(key.toString(), coll);
			if (timer != null) {
				timer.logDebug("getCheckReportDef", "");
			}
		} else {
			this.mLog.debug("getCheckReportDef", "obtained " + key + " from cache");
		}

		return coll;
	}

	public EntityList getCheckReportDistribution(int param1) {
		StringBuffer key = new StringBuffer();
		key.append("CheckReportDistribution");
		key.append('.');
		key.append(String.valueOf(param1));
		CheckReportDistributionELO coll = (CheckReportDistributionELO) this.mConnection.getClientCache().get(key.toString());
		if (coll == null) {
			Timer timer = this.mLog.isDebugEnabled() ? new Timer(this.mLog) : null;

			try {
				coll = (new ListSessionServer(this.getConnection())).getCheckReportDistribution(param1);
			} catch (Exception var6) {
				throw new RuntimeException("unable to get ReportPackLink CheckReportDistribution", var6);
			}

			this.mConnection.getClientCache().put(key.toString(), coll);
			if (timer != null) {
				timer.logDebug("getCheckReportDistribution", "");
			}
		} else {
			this.mLog.debug("getCheckReportDistribution", "obtained " + key + " from cache");
		}

		return coll;
	}

	public EntityList getAllWeightingProfiles() {
		StringBuffer key = new StringBuffer();
		key.append("AllWeightingProfiles");
		AllWeightingProfilesELO coll = (AllWeightingProfilesELO) this.mConnection.getClientCache().get(key.toString());
		if (coll == null) {
			Timer timer = this.mLog.isDebugEnabled() ? new Timer(this.mLog) : null;

			try {
				coll = (new ListSessionServer(this.getConnection())).getAllWeightingProfiles();
			} catch (Exception var5) {
				throw new RuntimeException("unable to get WeightingProfile AllWeightingProfiles", var5);
			}

			this.mConnection.getClientCache().put(key.toString(), coll);
			if (timer != null) {
				timer.logDebug("getAllWeightingProfiles", "");
			}
		} else {
			this.mLog.debug("getAllWeightingProfiles", "obtained " + key + " from cache");
		}

		return coll;
	}

	public EntityList getAllWeightingProfilesForLoggedUser() {
		StringBuffer key = new StringBuffer();
		key.append("getAllWeightingProfilesForLoggedUser");
		AllWeightingProfilesELO coll = (AllWeightingProfilesELO) this.mConnection.getClientCache().get(key.toString());
		if (coll == null) {
			Timer timer = this.mLog.isDebugEnabled() ? new Timer(this.mLog) : null;

			try {
				coll = (new ListSessionServer(this.getConnection())).getAllWeightingProfilesForLoggedUser();
			} catch (Exception var5) {
				throw new RuntimeException("unable to getAllWeightingProfilesForLoggedUsers", var5);
			}

			this.mConnection.getClientCache().put(key.toString(), coll);
			if (timer != null) {
				timer.logDebug("getAllWeightingProfilesForLoggedUser", "");
			}
		} else {
			this.mLog.debug("getAllWeightingProfilesForLoggedUser", "obtained " + key + " from cache");
		}

		return coll;
	}

	public EntityList getAllWeightingDeployments() {
		StringBuffer key = new StringBuffer();
		key.append("AllWeightingDeployments");
		AllWeightingDeploymentsELO coll = (AllWeightingDeploymentsELO) this.mConnection.getClientCache().get(key.toString());
		if (coll == null) {
			Timer timer = this.mLog.isDebugEnabled() ? new Timer(this.mLog) : null;

			try {
				coll = (new ListSessionServer(this.getConnection())).getAllWeightingDeployments();
			} catch (Exception var5) {
				throw new RuntimeException("unable to get WeightingDeployment AllWeightingDeployments", var5);
			}

			this.mConnection.getClientCache().put(key.toString(), coll);
			if (timer != null) {
				timer.logDebug("getAllWeightingDeployments", "");
			}
		} else {
			this.mLog.debug("getAllWeightingDeployments", "obtained " + key + " from cache");
		}

		return coll;
	}

	public EntityList getAllWeightingDeploymentsForLoggedUser() {
		StringBuffer key = new StringBuffer();
		key.append("AllWeightingDeploymentsForLoggedUser");
		EntityList coll = (EntityList) this.mConnection.getClientCache().get(key.toString());
		if (coll == null) {
			Timer timer = this.mLog.isDebugEnabled() ? new Timer(this.mLog) : null;

			try {
				coll = (new ListSessionServer(this.getConnection())).getAllWeightingDeploymentsForLoggedUser();
			} catch (Exception var5) {
				throw new RuntimeException("unable to get WeightingDeployment AllWeightingDeploymentsForLoggedUser", var5);
			}

			this.mConnection.getClientCache().put(key.toString(), coll);
			if (timer != null) {
				timer.logDebug("getAllWeightingDeploymentsForLoggedUser", "");
			}
		} else {
			this.mLog.debug("getAllWeightingDeploymentsForLoggedUser", "obtained " + key + " from cache");
		}

		return coll;
	}

	public EntityList getAllTidyTasks() {
		StringBuffer key = new StringBuffer();
		key.append("AllTidyTasks");
		AllTidyTasksELO coll = (AllTidyTasksELO) this.mConnection.getClientCache().get(key.toString());
		if (coll == null) {
			Timer timer = this.mLog.isDebugEnabled() ? new Timer(this.mLog) : null;

			try {
				coll = (new ListSessionServer(this.getConnection())).getAllTidyTasks();
			} catch (Exception var5) {
				throw new RuntimeException("unable to get TidyTask AllTidyTasks", var5);
			}

			this.mConnection.getClientCache().put(key.toString(), coll);
			if (timer != null) {
				timer.logDebug("getAllTidyTasks", "");
			}
		} else {
			this.mLog.debug("getAllTidyTasks", "obtained " + key + " from cache");
		}

		return coll;
	}

	public EntityList getAllImportTasks() {
		StringBuffer key = new StringBuffer();
		key.append("AllImportTasks");
		AllImportTasksELO coll = (AllImportTasksELO) this.mConnection.getClientCache().get(key.toString());
		if (coll == null) {
			Timer timer = this.mLog.isDebugEnabled() ? new Timer(this.mLog) : null;

			try {
				coll = (new ListSessionServer(this.getConnection())).getAllImportTasks();
			} catch (Exception var5) {
				throw new RuntimeException("unable to get ImportTask AllImportTasks", var5);
			}

			this.mConnection.getClientCache().put(key.toString(), coll);
			if (timer != null) {
				timer.logDebug("getAllImportTasks", "");
			}
		} else {
			this.mLog.debug("getAllImportTasks", "obtained " + key + " from cache");
		}

		return coll;
	}

	public EntityList getAllRecalculateBatchTasks() {
		StringBuffer key = new StringBuffer();
		key.append("AllRecalculateBatchTasks");
		AllRecalculateBatchTasksELO coll = (AllRecalculateBatchTasksELO) this.mConnection.getClientCache().get(key.toString());
		if (coll == null) {
			Timer timer = this.mLog.isDebugEnabled() ? new Timer(this.mLog) : null;

			try {
				coll = (new ListSessionServer(this.getConnection())).getAllRecalculateBatchTasks();
			} catch (Exception var5) {
				throw new RuntimeException("unable to get RecalculateBatchTask AllRecalculateBatchTasks", var5);
			}

			this.mConnection.getClientCache().put(key.toString(), coll);
			if (timer != null) {
				timer.logDebug("getAllRecalculateBatchTasks", "");
			}
		} else {
			this.mLog.debug("getAllRecalculateBatchTasks", "obtained " + key + " from cache");
		}

		return coll;
	}

	public EntityList getOrderedChildren(int param1) {
		StringBuffer key = new StringBuffer();
		key.append("OrderedChildren");
		key.append('.');
		key.append(String.valueOf(param1));
		OrderedChildrenELO coll = (OrderedChildrenELO) this.mConnection.getClientCache().get(key.toString());
		if (coll == null) {
			Timer timer = this.mLog.isDebugEnabled() ? new Timer(this.mLog) : null;

			try {
				coll = (new ListSessionServer(this.getConnection())).getOrderedChildren(param1);
			} catch (Exception var6) {
				throw new RuntimeException("unable to get TidyTaskLink OrderedChildren", var6);
			}

			this.mConnection.getClientCache().put(key.toString(), coll);
			if (timer != null) {
				timer.logDebug("getOrderedChildren", "");
			}
		} else {
			this.mLog.debug("getOrderedChildren", "obtained " + key + " from cache");
		}

		return coll;
	}

	public EntityList getAllMappedModels() {
		StringBuffer key = new StringBuffer();
		key.append("AllMappedModels");
		AllMappedModelsELO coll = (AllMappedModelsELO) this.mConnection.getClientCache().get(key.toString());
		if (coll == null) {
			Timer timer = this.mLog.isDebugEnabled() ? new Timer(this.mLog) : null;

			try {
				coll = (new ListSessionServer(this.getConnection())).getAllMappedModels();
			} catch (Exception var5) {
				throw new RuntimeException("unable to get MappedModel AllMappedModels", var5);
			}

			this.mConnection.getClientCache().put(key.toString(), coll);
			if (timer != null) {
				timer.logDebug("getAllMappedModels", "");
			}
		} else {
			this.mLog.debug("getAllMappedModels", "obtained " + key + " from cache");
		}

		return coll;
	}

	public EntityList getAllMappedModelsForLoggedUser() {
		StringBuffer key = new StringBuffer();
		key.append("getAllMappedModelsForLoggedUser");
		AllMappedModelsELO coll = (AllMappedModelsELO) this.mConnection.getClientCache().get(key.toString());
		if (coll == null) {
			Timer timer = this.mLog.isDebugEnabled() ? new Timer(this.mLog) : null;

			try {
				coll = (new ListSessionServer(this.getConnection())).getAllMappedModelsForLoggedUser();
			} catch (Exception var5) {
				throw new RuntimeException("unable to get MappedModel getAllMappedModelsForLoggedUser", var5);
			}

			this.mConnection.getClientCache().put(key.toString(), coll);
			if (timer != null) {
				timer.logDebug("getAllMappedModelsForLoggedUser", "");
			}
		} else {
			this.mLog.debug("getAllMappedModelsForLoggedUser", "obtained " + key + " from cache");
		}

		return coll;
	}

	public EntityList getAllGlobalMappedModels2() {
		StringBuffer key = new StringBuffer();
		key.append("AllGlobalMappedModels2");
		AllGlobalMappedModels2ELO coll = (AllGlobalMappedModels2ELO) this.mConnection.getClientCache().get(key.toString());
		if (coll == null) {
			Timer timer = this.mLog.isDebugEnabled() ? new Timer(this.mLog) : null;

			try {
				coll = (new ListSessionServer(this.getConnection())).getAllGlobalMappedModels2();
			} catch (Exception var5) {
				throw new RuntimeException("unable to get GlobalMappedModel AllGlobalMappedModels2", var5);
			}

			this.mConnection.getClientCache().put(key.toString(), coll);
			if (timer != null) {
				timer.logDebug("getAllGlobalMappedModels2", "");
			}
		} else {
			this.mLog.debug("getAllGlobalMappedModels2", "obtained " + key + " from cache");
		}

		return coll;
	}

	public EntityList getAllGlobalMappedModelsForLoggedUser() {
		StringBuffer key = new StringBuffer();
		key.append("getAllGlobalMappedModelsForLoggedUser");
		AllGlobalMappedModels2ELO coll = (AllGlobalMappedModels2ELO) this.mConnection.getClientCache().get(key.toString());
		if (coll == null) {
			Timer timer = this.mLog.isDebugEnabled() ? new Timer(this.mLog) : null;

			try {
				coll = (new ListSessionServer(this.getConnection())).getAllGlobalMappedModelsForLoggedUser();
			} catch (Exception var5) {
				throw new RuntimeException("unable to get GlobalMappedModel getAllGlobalMappedModelsForLoggedUser", var5);
			}

			this.mConnection.getClientCache().put(key.toString(), coll);
			if (timer != null) {
				timer.logDebug("getAllGlobalMappedModelsForLoggedUser", "");
			}
		} else {
			this.mLog.debug("getAllGlobalMappedModelsForLoggedUser", "obtained " + key + " from cache");
		}

		return coll;
	}

	public EntityList getMappedFinanceCubes(int param1) {
		StringBuffer key = new StringBuffer();
		key.append("MappedFinanceCubes");
		key.append('.');
		key.append(String.valueOf(param1));
		MappedFinanceCubesELO coll = (MappedFinanceCubesELO) this.mConnection.getClientCache().get(key.toString());
		if (coll == null) {
			Timer timer = this.mLog.isDebugEnabled() ? new Timer(this.mLog) : null;

			try {
				coll = (new ListSessionServer(this.getConnection())).getMappedFinanceCubes(param1);
			} catch (Exception var6) {
				throw new RuntimeException("unable to get MappedModel MappedFinanceCubes", var6);
			}

			this.mConnection.getClientCache().put(key.toString(), coll);
			if (timer != null) {
				timer.logDebug("getMappedFinanceCubes", "");
			}
		} else {
			this.mLog.debug("getMappedFinanceCubes", "obtained " + key + " from cache");
		}

		return coll;
	}

	public EntityList getAllExtendedAttachments() {
		StringBuffer key = new StringBuffer();
		key.append("AllExtendedAttachments");
		AllExtendedAttachmentsELO coll = (AllExtendedAttachmentsELO) this.mConnection.getClientCache().get(key.toString());
		if (coll == null) {
			Timer timer = this.mLog.isDebugEnabled() ? new Timer(this.mLog) : null;

			try {
				coll = (new ListSessionServer(this.getConnection())).getAllExtendedAttachments();
			} catch (Exception var5) {
				throw new RuntimeException("unable to get ExtendedAttachment AllExtendedAttachments", var5);
			}

			this.mConnection.getClientCache().put(key.toString(), coll);
			if (timer != null) {
				timer.logDebug("getAllExtendedAttachments", "");
			}
		} else {
			this.mLog.debug("getAllExtendedAttachments", "obtained " + key + " from cache");
		}

		return coll;
	}

	public EntityList getExtendedAttachmentsForId(int param1) {
		StringBuffer key = new StringBuffer();
		key.append("ExtendedAttachmentsForId");
		key.append('.');
		key.append(String.valueOf(param1));
		ExtendedAttachmentsForIdELO coll = (ExtendedAttachmentsForIdELO) this.mConnection.getClientCache().get(key.toString());
		if (coll == null) {
			Timer timer = this.mLog.isDebugEnabled() ? new Timer(this.mLog) : null;

			try {
				coll = (new ListSessionServer(this.getConnection())).getExtendedAttachmentsForId(param1);
			} catch (Exception var6) {
				throw new RuntimeException("unable to get ExtendedAttachment ExtendedAttachmentsForId", var6);
			}

			this.mConnection.getClientCache().put(key.toString(), coll);
			if (timer != null) {
				timer.logDebug("getExtendedAttachmentsForId", "");
			}
		} else {
			this.mLog.debug("getExtendedAttachmentsForId", "obtained " + key + " from cache");
		}

		return coll;
	}

	public EntityList getAllImageExtendedAttachments() {
		StringBuffer key = new StringBuffer();
		key.append("AllImageExtendedAttachments");
		AllImageExtendedAttachmentsELO coll = (AllImageExtendedAttachmentsELO) this.mConnection.getClientCache().get(key.toString());
		if (coll == null) {
			Timer timer = this.mLog.isDebugEnabled() ? new Timer(this.mLog) : null;

			try {
				coll = (new ListSessionServer(this.getConnection())).getAllImageExtendedAttachments();
			} catch (Exception var5) {
				throw new RuntimeException("unable to get ExtendedAttachment AllImageExtendedAttachments", var5);
			}

			this.mConnection.getClientCache().put(key.toString(), coll);
			if (timer != null) {
				timer.logDebug("getAllImageExtendedAttachments", "");
			}
		} else {
			this.mLog.debug("getAllImageExtendedAttachments", "obtained " + key + " from cache");
		}

		return coll;
	}

	public EntityList getAllExternalSystems() {
		StringBuffer key = new StringBuffer();
		key.append("AllExternalSystems");
		AllExternalSystemsELO coll = (AllExternalSystemsELO) this.mConnection.getClientCache().get(key.toString());
		if (coll == null) {
			Timer timer = this.mLog.isDebugEnabled() ? new Timer(this.mLog) : null;

			try {
				coll = (new ListSessionServer(this.getConnection())).getAllExternalSystems();
			} catch (Exception var5) {
				throw new RuntimeException("unable to get ExternalSystem AllExternalSystems", var5);
			}

			this.mConnection.getClientCache().put(key.toString(), coll);
			if (timer != null) {
				timer.logDebug("getAllExternalSystems", "");
			}
		} else {
			this.mLog.debug("getAllExternalSystems", "obtained " + key + " from cache");
		}

		return coll;
	}

	public EntityList getAllGenericExternalSystems() {
		StringBuffer key = new StringBuffer();
		key.append("AllGenericExternalSystems");
		AllGenericExternalSystemsELO coll = (AllGenericExternalSystemsELO) this.mConnection.getClientCache().get(key.toString());
		if (coll == null) {
			Timer timer = this.mLog.isDebugEnabled() ? new Timer(this.mLog) : null;

			try {
				coll = (new ListSessionServer(this.getConnection())).getAllGenericExternalSystems();
			} catch (Exception var5) {
				throw new RuntimeException("unable to get ExternalSystem AllGenericExternalSystems", var5);
			}

			this.mConnection.getClientCache().put(key.toString(), coll);
			if (timer != null) {
				timer.logDebug("getAllGenericExternalSystems", "");
			}
		} else {
			this.mLog.debug("getAllGenericExternalSystems", "obtained " + key + " from cache");
		}

		return coll;
	}

	public EntityList getAllExternalSystemCompaines() {
		StringBuffer key = new StringBuffer();
		key.append("AllExternalSystemCompaines");
		AllExternalSystemCompainesELO coll = (AllExternalSystemCompainesELO) this.mConnection.getClientCache().get(key.toString());
		if (coll == null) {
			Timer timer = this.mLog.isDebugEnabled() ? new Timer(this.mLog) : null;

			try {
				coll = (new ListSessionServer(this.getConnection())).getAllExternalSystemCompaines();
			} catch (Exception var5) {
				throw new RuntimeException("unable to get ExtSysCompany AllExternalSystemCompaines", var5);
			}

			this.mConnection.getClientCache().put(key.toString(), coll);
			if (timer != null) {
				timer.logDebug("getAllExternalSystemCompaines", "");
			}
		} else {
			this.mLog.debug("getAllExternalSystemCompaines", "obtained " + key + " from cache");
		}

		return coll;
	}

	public EntityList getAllAmmModels() {
		StringBuffer key = new StringBuffer();
		key.append("AllAmmModels");
		AllAmmModelsELO coll = (AllAmmModelsELO) this.mConnection.getClientCache().get(key.toString());
		if (coll == null) {
			Timer timer = this.mLog.isDebugEnabled() ? new Timer(this.mLog) : null;

			try {
				coll = (new ListSessionServer(this.getConnection())).getAllAmmModels();
			} catch (Exception var5) {
				throw new RuntimeException("unable to get AmmModel AllAmmModels", var5);
			}

			this.mConnection.getClientCache().put(key.toString(), coll);
			if (timer != null) {
				timer.logDebug("getAllAmmModels", "");
			}
		} else {
			this.mLog.debug("getAllAmmModels", "obtained " + key + " from cache");
		}

		return coll;
	}

	public EntityList getAllAmmModelsForLoggedUser() {
		StringBuffer key = new StringBuffer();
		key.append("getAllAmmModelsForLoggedUser");
		AllAmmModelsELO coll = (AllAmmModelsELO) this.mConnection.getClientCache().get(key.toString());
		if (coll == null) {
			Timer timer = this.mLog.isDebugEnabled() ? new Timer(this.mLog) : null;

			try {
				coll = (new ListSessionServer(this.getConnection())).getAllAmmModelsForLoggedUser();
			} catch (Exception var5) {
				throw new RuntimeException("unable to get AmmModel getAllAmmModelsForLoggedUser", var5);
			}

			this.mConnection.getClientCache().put(key.toString(), coll);
			if (timer != null) {
				timer.logDebug("getAllAmmModelsForLoggedUser", "");
			}
		} else {
			this.mLog.debug("getAllAmmModelsForLoggedUser", "obtained " + key + " from cache");
		}

		return coll;
	}

	public EntityList getAllTaskGroups() {
		StringBuffer key = new StringBuffer();
		key.append("AllTaskGroups");
		AllTaskGroupsELO coll = (AllTaskGroupsELO) this.mConnection.getClientCache().get(key.toString());
		if (coll == null) {
			Timer timer = this.mLog.isDebugEnabled() ? new Timer(this.mLog) : null;

			try {
				coll = (new ListSessionServer(this.getConnection())).getAllTaskGroups();
			} catch (Exception var5) {
				throw new RuntimeException("unable to get TaskGroup AllTaskGroups", var5);
			}

			this.mConnection.getClientCache().put(key.toString(), coll);
			if (timer != null) {
				timer.logDebug("getAllTaskGroups", "");
			}
		} else {
			this.mLog.debug("getAllTaskGroups", "obtained " + key + " from cache");
		}

		return coll;
	}

	public EntityList getTaskGroupRICheck(int param1) {
		StringBuffer key = new StringBuffer();
		key.append("TaskGroupRICheck");
		key.append('.');
		key.append(String.valueOf(param1));
		TaskGroupRICheckELO coll = (TaskGroupRICheckELO) this.mConnection.getClientCache().get(key.toString());
		if (coll == null) {
			Timer timer = this.mLog.isDebugEnabled() ? new Timer(this.mLog) : null;

			try {
				coll = (new ListSessionServer(this.getConnection())).getTaskGroupRICheck(param1);
			} catch (Exception var6) {
				throw new RuntimeException("unable to get TaskGroup TaskGroupRICheck", var6);
			}

			this.mConnection.getClientCache().put(key.toString(), coll);
			if (timer != null) {
				timer.logDebug("getTaskGroupRICheck", "");
			}
		} else {
			this.mLog.debug("getTaskGroupRICheck", "obtained " + key + " from cache");
		}

		return coll;
	}

	public EntityList getAllAuthenticationPolicys() {
		StringBuffer key = new StringBuffer();
		key.append("AllAuthenticationPolicys");
		AllAuthenticationPolicysELO coll = (AllAuthenticationPolicysELO) this.mConnection.getClientCache().get(key.toString());
		if (coll == null) {
			Timer timer = this.mLog.isDebugEnabled() ? new Timer(this.mLog) : null;

			try {
				coll = (new ListSessionServer(this.getConnection())).getAllAuthenticationPolicys();
			} catch (Exception var5) {
				throw new RuntimeException("unable to get AuthenticationPolicy AllAuthenticationPolicys", var5);
			}

			this.mConnection.getClientCache().put(key.toString(), coll);
			if (timer != null) {
				timer.logDebug("getAllAuthenticationPolicys", "");
			}
		} else {
			this.mLog.debug("getAllAuthenticationPolicys", "obtained " + key + " from cache");
		}

		return coll;
	}

	public EntityList getActiveAuthenticationPolicys() {
		StringBuffer key = new StringBuffer();
		key.append("ActiveAuthenticationPolicys");
		ActiveAuthenticationPolicysELO coll = (ActiveAuthenticationPolicysELO) this.mConnection.getClientCache().get(key.toString());
		if (coll == null) {
			Timer timer = this.mLog.isDebugEnabled() ? new Timer(this.mLog) : null;

			try {
				coll = (new ListSessionServer(this.getConnection())).getActiveAuthenticationPolicys();
			} catch (Exception var5) {
				throw new RuntimeException("unable to get AuthenticationPolicy ActiveAuthenticationPolicys", var5);
			}

			this.mConnection.getClientCache().put(key.toString(), coll);
			if (timer != null) {
				timer.logDebug("getActiveAuthenticationPolicys", "");
			}
		} else {
			this.mLog.debug("getActiveAuthenticationPolicys", "obtained " + key + " from cache");
		}

		return coll;
	}

	public EntityList getActiveAuthenticationPolicyForLogon() {
		StringBuffer key = new StringBuffer();
		key.append("ActiveAuthenticationPolicyForLogon");
		ActiveAuthenticationPolicyForLogonELO coll = (ActiveAuthenticationPolicyForLogonELO) this.mConnection.getClientCache().get(key.toString());
		if (coll == null) {
			Timer timer = this.mLog.isDebugEnabled() ? new Timer(this.mLog) : null;

			try {
				coll = (new ListSessionServer(this.getConnection())).getActiveAuthenticationPolicyForLogon();
			} catch (Exception var5) {
				throw new RuntimeException("unable to get AuthenticationPolicy ActiveAuthenticationPolicyForLogon", var5);
			}

			this.mConnection.getClientCache().put(key.toString(), coll);
			if (timer != null) {
				timer.logDebug("getActiveAuthenticationPolicyForLogon", "");
			}
		} else {
			this.mLog.debug("getActiveAuthenticationPolicyForLogon", "obtained " + key + " from cache");
		}

		return coll;
	}

	public EntityList getAllLogonHistorys() {
		StringBuffer key = new StringBuffer();
		key.append("AllLogonHistorys");
		AllLogonHistorysELO coll = (AllLogonHistorysELO) this.mConnection.getClientCache().get(key.toString());
		if (coll == null) {
			Timer timer = this.mLog.isDebugEnabled() ? new Timer(this.mLog) : null;

			try {
				coll = (new ListSessionServer(this.getConnection())).getAllLogonHistorys();
			} catch (Exception var5) {
				throw new RuntimeException("unable to get LogonHistory AllLogonHistorys", var5);
			}

			this.mConnection.getClientCache().put(key.toString(), coll);
			if (timer != null) {
				timer.logDebug("getAllLogonHistorys", "");
			}
		} else {
			this.mLog.debug("getAllLogonHistorys", "obtained " + key + " from cache");
		}

		return coll;
	}

	public EntityList getAllPasswordHistorys() {
		StringBuffer key = new StringBuffer();
		key.append("AllPasswordHistorys");
		AllPasswordHistorysELO coll = (AllPasswordHistorysELO) this.mConnection.getClientCache().get(key.toString());
		if (coll == null) {
			Timer timer = this.mLog.isDebugEnabled() ? new Timer(this.mLog) : null;

			try {
				coll = (new ListSessionServer(this.getConnection())).getAllPasswordHistorys();
			} catch (Exception var5) {
				throw new RuntimeException("unable to get PasswordHistory AllPasswordHistorys", var5);
			}

			this.mConnection.getClientCache().put(key.toString(), coll);
			if (timer != null) {
				timer.logDebug("getAllPasswordHistorys", "");
			}
		} else {
			this.mLog.debug("getAllPasswordHistorys", "obtained " + key + " from cache");
		}

		return coll;
	}

	public EntityList getUserPasswordHistory(int param1) {
		StringBuffer key = new StringBuffer();
		key.append("UserPasswordHistory");
		key.append('.');
		key.append(String.valueOf(param1));
		UserPasswordHistoryELO coll = (UserPasswordHistoryELO) this.mConnection.getClientCache().get(key.toString());
		if (coll == null) {
			Timer timer = this.mLog.isDebugEnabled() ? new Timer(this.mLog) : null;

			try {
				coll = (new ListSessionServer(this.getConnection())).getUserPasswordHistory(param1);
			} catch (Exception var6) {
				throw new RuntimeException("unable to get PasswordHistory UserPasswordHistory", var6);
			}

			this.mConnection.getClientCache().put(key.toString(), coll);
			if (timer != null) {
				timer.logDebug("getUserPasswordHistory", "");
			}
		} else {
			this.mLog.debug("getUserPasswordHistory", "obtained " + key + " from cache");
		}

		return coll;
	}

	public EntityList getAllFormRebuilds() {
		Timer timer = this.mLog.isDebugEnabled() ? new Timer(this.mLog) : null;
		AllFormRebuildsELO coll = null;

		try {
			coll = (new ListSessionServer(this.getConnection())).getAllFormRebuilds();
		} catch (Exception var4) {
			throw new RuntimeException("unable to get FormRebuild AllFormRebuilds", var4);
		}

		if (timer != null) {
			timer.logDebug("getAllFormRebuilds", "");
		}

		return coll;
	}

	public EntityList getAllFormRebuildsForLoggedUser() {
		AllFormRebuildsELO coll = null;
		try {
			coll = (new ListSessionServer(this.getConnection())).getAllFormRebuildsForLoggedUser();
		} catch (Exception var4) {
			throw new RuntimeException("unable to get FormRebuild AllFormRebuildsForLoggedUser", var4);
		}

		return coll;
	}

	public EntityList getAllBudgetCyclesInRebuilds() {
		StringBuffer key = new StringBuffer();
		key.append("AllBudgetCyclesInRebuilds");
		AllBudgetCyclesInRebuildsELO coll = (AllBudgetCyclesInRebuildsELO) this.mConnection.getClientCache().get(key.toString());
		if (coll == null) {
			Timer timer = this.mLog.isDebugEnabled() ? new Timer(this.mLog) : null;

			try {
				coll = (new ListSessionServer(this.getConnection())).getAllBudgetCyclesInRebuilds();
			} catch (Exception var5) {
				throw new RuntimeException("unable to get FormRebuild AllBudgetCyclesInRebuilds", var5);
			}

			this.mConnection.getClientCache().put(key.toString(), coll);
			if (timer != null) {
				timer.logDebug("getAllBudgetCyclesInRebuilds", "");
			}
		} else {
			this.mLog.debug("getAllBudgetCyclesInRebuilds", "obtained " + key + " from cache");
		}

		return coll;
	}

	public EntityList getAllPackagesForFinanceCube(int param1) {
		StringBuffer key = new StringBuffer();
		key.append("AllPackagesForFinanceCube");
		key.append('.');
		key.append(String.valueOf(param1));
		AllPackagesForFinanceCubeELO coll = (AllPackagesForFinanceCubeELO) this.mConnection.getClientCache().get(key.toString());
		if (coll == null) {
			Timer timer = this.mLog.isDebugEnabled() ? new Timer(this.mLog) : null;

			try {
				coll = (new ListSessionServer(this.getConnection())).getAllPackagesForFinanceCube(param1);
			} catch (Exception var6) {
				throw new RuntimeException("unable to get CubeFormulaPackage AllPackagesForFinanceCube", var6);
			}

			this.mConnection.getClientCache().put(key.toString(), coll);
			if (timer != null) {
				timer.logDebug("getAllPackagesForFinanceCube", "");
			}
		} else {
			this.mLog.debug("getAllPackagesForFinanceCube", "obtained " + key + " from cache");
		}

		return coll;
	}

	public EntityList getAllCubeFormulas() {
		StringBuffer key = new StringBuffer();
		key.append("AllCubeFormulas");
		AllCubeFormulasELO coll = (AllCubeFormulasELO) this.mConnection.getClientCache().get(key.toString());
		if (coll == null) {
			Timer timer = this.mLog.isDebugEnabled() ? new Timer(this.mLog) : null;

			try {
				coll = (new ListSessionServer(this.getConnection())).getAllCubeFormulas();
			} catch (Exception var5) {
				throw new RuntimeException("unable to get CubeFormula AllCubeFormulas", var5);
			}

			this.mConnection.getClientCache().put(key.toString(), coll);
			if (timer != null) {
				timer.logDebug("getAllCubeFormulas", "");
			}
		} else {
			this.mLog.debug("getAllCubeFormulas", "obtained " + key + " from cache");
		}

		return coll;
	}

	public EntityList getCubeFormulaeForFinanceCube(int param1) {
		StringBuffer key = new StringBuffer();
		key.append("CubeFormulaeForFinanceCube");
		key.append('.');
		key.append(String.valueOf(param1));
		CubeFormulaeForFinanceCubeELO coll = (CubeFormulaeForFinanceCubeELO) this.mConnection.getClientCache().get(key.toString());
		if (coll == null) {
			Timer timer = this.mLog.isDebugEnabled() ? new Timer(this.mLog) : null;

			try {
				coll = (new ListSessionServer(this.getConnection())).getCubeFormulaeForFinanceCube(param1);
			} catch (Exception var6) {
				throw new RuntimeException("unable to get CubeFormula CubeFormulaeForFinanceCube", var6);
			}

			this.mConnection.getClientCache().put(key.toString(), coll);
			if (timer != null) {
				timer.logDebug("getCubeFormulaeForFinanceCube", "");
			}
		} else {
			this.mLog.debug("getCubeFormulaeForFinanceCube", "obtained " + key + " from cache");
		}

		return coll;
	}

	public ModelRef getModelEntityRef(Object paramKey) {
		return (ModelRef) this.getEntityRef(paramKey, "Model");
	}

	public FinanceCubeRef getFinanceCubeEntityRef(Object paramKey) {
		return (FinanceCubeRef) this.getEntityRef(paramKey, "FinanceCube");
	}

	public BudgetCycleRef getBudgetCycleEntityRef(Object paramKey) {
		return (BudgetCycleRef) this.getEntityRef(paramKey, "BudgetCycle");
	}

	public ResponsibilityAreaRef getResponsibilityAreaEntityRef(Object paramKey) {
		return (ResponsibilityAreaRef) this.getEntityRef(paramKey, "ResponsibilityArea");
	}

	public BudgetUserRef getBudgetUserEntityRef(Object paramKey) {
		return (BudgetUserRef) this.getEntityRef(paramKey, "BudgetUser");
	}

	public DataTypeRef getDataTypeEntityRef(Object paramKey) {
		return (DataTypeRef) this.getEntityRef(paramKey, "DataType");
	}

	public CurrencyRef getCurrencyEntityRef(Object paramKey) {
		return (CurrencyRef) this.getEntityRef(paramKey, "Currency");
	}

	public DimensionRef getDimensionEntityRef(Object paramKey) {
		return (DimensionRef) this.getEntityRef(paramKey, "Dimension");
	}

	public DimensionElementRef getDimensionElementEntityRef(Object paramKey) {
		return (DimensionElementRef) this.getEntityRef(paramKey, "Dimension");
	}

	public HierarchyRef getHierarchyEntityRef(Object paramKey) {
		return (HierarchyRef) this.getEntityRef(paramKey, "Hierarchy");
	}

	public UserRef getUserEntityRef(Object paramKey) {
		return (UserRef) this.getEntityRef(paramKey, "User");
	}

	public RoleRef getRoleEntityRef(Object paramKey) {
		return (RoleRef) this.getEntityRef(paramKey, "Role");
	}

	public BudgetInstructionRef getBudgetInstructionEntityRef(Object paramKey) {
		return (BudgetInstructionRef) this.getEntityRef(paramKey, "BudgetInstruction");
	}

	public SystemPropertyRef getSystemPropertyEntityRef(Object paramKey) {
		return (SystemPropertyRef) this.getEntityRef(paramKey, "SystemProperty");
	}

	public XmlFormRef getXmlFormEntityRef(Object paramKey) {
		return (XmlFormRef) this.getEntityRef(paramKey, "XmlForm");
	}

	public XmlReportRef getXmlReportEntityRef(Object paramKey) {
		return (XmlReportRef) this.getEntityRef(paramKey, "XmlReport");
	}

	public XmlReportFolderRef getXmlReportFolderEntityRef(Object paramKey) {
		return (XmlReportFolderRef) this.getEntityRef(paramKey, "XmlReportFolder");
	}

	public DataEntryProfileRef getDataEntryProfileEntityRef(Object paramKey) {
		return (DataEntryProfileRef) this.getEntityRef(paramKey, "DataEntryProfile");
	}

	public UdefLookupRef getUdefLookupEntityRef(Object paramKey) {
		return (UdefLookupRef) this.getEntityRef(paramKey, "UdefLookup");
	}

	public SecurityRangeRef getSecurityRangeEntityRef(Object paramKey) {
		return (SecurityRangeRef) this.getEntityRef(paramKey, "SecurityRange");
	}

	public SecurityAccessDefRef getSecurityAccessDefEntityRef(Object paramKey) {
		return (SecurityAccessDefRef) this.getEntityRef(paramKey, "SecurityAccessDef");
	}

	public SecurityGroupRef getSecurityGroupEntityRef(Object paramKey) {
		return (SecurityGroupRef) this.getEntityRef(paramKey, "SecurityGroup");
	}

	public CcDeploymentRef getCcDeploymentEntityRef(Object paramKey) {
		return (CcDeploymentRef) this.getEntityRef(paramKey, "CcDeployment");
	}

	public CellCalcRef getCellCalcEntityRef(Object paramKey) {
		return (CellCalcRef) this.getEntityRef(paramKey, "CellCalc");
	}

	public ChangeMgmtRef getChangeMgmtEntityRef(Object paramKey) {
		return (ChangeMgmtRef) this.getEntityRef(paramKey, "ChangeMgmt");
	}

	public ImpExpHdrRef getImpExpHdrEntityRef(Object paramKey) {
		return (ImpExpHdrRef) this.getEntityRef(paramKey, "ImpExpHdr");
	}

	public ReportRef getReportEntityRef(Object paramKey) {
		return (ReportRef) this.getEntityRef(paramKey, "Report");
	}

	public VirementCategoryRef getVirementCategoryEntityRef(Object paramKey) {
		return (VirementCategoryRef) this.getEntityRef(paramKey, "VirementCategory");
	}

	public BudgetLimitRef getBudgetLimitEntityRef(Object paramKey) {
		return (BudgetLimitRef) this.getEntityRef(paramKey, "BudgetLimit");
	}

	public PerfTestRef getPerfTestEntityRef(Object paramKey) {
		return (PerfTestRef) this.getEntityRef(paramKey, "PerfTest");
	}

	public PerfTestRunRef getPerfTestRunEntityRef(Object paramKey) {
		return (PerfTestRunRef) this.getEntityRef(paramKey, "PerfTestRun");
	}

	public PerfTestRunResultRef getPerfTestRunResultEntityRef(Object paramKey) {
		return (PerfTestRunResultRef) this.getEntityRef(paramKey, "PerfTestRunResult");
	}

	public MessageRef getMessageEntityRef(Object paramKey) {
		return (MessageRef) this.getEntityRef(paramKey, "Message");
	}

	public RechargeRef getRechargeEntityRef(Object paramKey) {
		return (RechargeRef) this.getEntityRef(paramKey, "Recharge");
	}

	public RechargeGroupRef getRechargeGroupEntityRef(Object paramKey) {
		return (RechargeGroupRef) this.getEntityRef(paramKey, "RechargeGroup");
	}

	public VirementRequestRef getVirementRequestEntityRef(Object paramKey) {
		return (VirementRequestRef) this.getEntityRef(paramKey, "VirementRequest");
	}

	public ReportTypeRef getReportTypeEntityRef(Object paramKey) {
		return (ReportTypeRef) this.getEntityRef(paramKey, "ReportType");
	}

	public ReportDefinitionRef getReportDefinitionEntityRef(Object paramKey) {
		return (ReportDefinitionRef) this.getEntityRef(paramKey, "ReportDefinition");
	}

	public ReportTemplateRef getReportTemplateEntityRef(Object paramKey) {
		return (ReportTemplateRef) this.getEntityRef(paramKey, "ReportTemplate");
	}

	public ReportMappingTemplateRef getReportMappingTemplateEntityRef(Object paramKey) {
		return (ReportMappingTemplateRef) this.getEntityRef(paramKey, "ReportMappingTemplate");
	}

	public ExternalDestinationRef getExternalDestinationEntityRef(Object paramKey) {
		return (ExternalDestinationRef) this.getEntityRef(paramKey, "ExternalDestination");
	}

	public InternalDestinationRef getInternalDestinationEntityRef(Object paramKey) {
		return (InternalDestinationRef) this.getEntityRef(paramKey, "InternalDestination");
	}

	public DistributionRef getDistributionEntityRef(Object paramKey) {
		return (DistributionRef) this.getEntityRef(paramKey, "Distribution");
	}

	public ReportPackRef getReportPackEntityRef(Object paramKey) {
		return (ReportPackRef) this.getEntityRef(paramKey, "ReportPack");
	}

	public WeightingProfileRef getWeightingProfileEntityRef(Object paramKey) {
		return (WeightingProfileRef) this.getEntityRef(paramKey, "WeightingProfile");
	}

	public WeightingDeploymentRef getWeightingDeploymentEntityRef(Object paramKey) {
		return (WeightingDeploymentRef) this.getEntityRef(paramKey, "WeightingDeployment");
	}

	public TidyTaskRef getTidyTaskEntityRef(Object paramKey) {
		return (TidyTaskRef) this.getEntityRef(paramKey, "TidyTask");
	}

	public MappedModelRef getMappedModelEntityRef(Object paramKey) {
		return (MappedModelRef) this.getEntityRef(paramKey, "MappedModel");
	}

	public ExtendedAttachmentRef getExtendedAttachmentEntityRef(Object paramKey) {
		return (ExtendedAttachmentRef) this.getEntityRef(paramKey, "ExtendedAttachment");
	}

	public ExternalSystemRef getExternalSystemEntityRef(Object paramKey) {
		return (ExternalSystemRef) this.getEntityRef(paramKey, "ExternalSystem");
	}

	public AmmModelRef getAmmModelEntityRef(Object paramKey) {
		return (AmmModelRef) this.getEntityRef(paramKey, "AmmModel");
	}

	public TaskGroupRef getTaskGroupEntityRef(Object paramKey) {
		return (TaskGroupRef) this.getEntityRef(paramKey, "TaskGroup");
	}

	public AuthenticationPolicyRef getAuthenticationPolicyEntityRef(Object paramKey) {
		return (AuthenticationPolicyRef) this.getEntityRef(paramKey, "AuthenticationPolicy");
	}

	public LogonHistoryRef getLogonHistoryEntityRef(Object paramKey) {
		return (LogonHistoryRef) this.getEntityRef(paramKey, "LogonHistory");
	}

	public PasswordHistoryRef getPasswordHistoryEntityRef(Object paramKey) {
		return (PasswordHistoryRef) this.getEntityRef(paramKey, "PasswordHistory");
	}

	public FormRebuildRef getFormRebuildEntityRef(Object paramKey) {
		return (FormRebuildRef) this.getEntityRef(paramKey, "FormRebuild");
	}

	public CubeFormulaRef getCubeFormulaEntityRef(Object paramKey) {
		return (CubeFormulaRef) this.getEntityRef(paramKey, "CubeFormula");
	}

	public MasterQuestionRef getMasterQuestionEntityRef(Object paramKey) {
		return (MasterQuestionRef) getEntityRef(paramKey, "MasterQuestion");
	}

	public ChallengeQuestionRef getChallengeQuestionEntityRef(Object paramKey) {
		return (ChallengeQuestionRef) getEntityRef(paramKey, "ChallengeQuestion");
	}

	public UserResetLinkRef getUserResetLinkEntityRef(Object paramKey) {
		return (UserResetLinkRef) getEntityRef(paramKey, "UserResetLink");
	}

	public EntityRef getEntityRef(Object paramKey, String listName) {
		Object key = paramKey;
		if (paramKey instanceof EntityRef) {
			key = ((EntityRef) paramKey).getPrimaryKey();
		}

		PrimaryKey pk = null;
		if (key instanceof CompositeKey) {
			pk = ((CompositeKey) key).getPK();
		} else {
			pk = (PrimaryKey) key;
		}

		EntityRefImpl er = null;
		String cacheKey = "All" + listName + "s";
		EntityList list = (EntityList) this.mConnection.getClientCache().get(cacheKey);
		if (list != null) {
			for (int timer = 0; timer < list.getNumRows(); ++timer) {
				er = (EntityRefImpl) list.getValueAt(timer, listName);
				Object e = er.getPrimaryKey();
				if (pk.equals(e)) {
					this.mLog.debug("getEntityRef", "obtained " + er + " from " + listName);
					return er;
				}
			}
		}

		Timer var12 = this.mLog.isDebugEnabled() ? new Timer(this.mLog) : null;

		EntityRef var11;
		try {
			var11 = (new ListSessionServer(this.getConnection())).getEntityRef(paramKey);
		} catch (Exception var10) {
			throw new RuntimeException("unable to get " + listName + " list", var10);
		}

		if (var12 != null) {
			var12.logDebug("getEntityRef", paramKey);
		}

		return var11;
	}

	private EntityList getList(String listName) {
		String key = "All" + listName + "s";
		EntityList coll = (EntityList) this.mConnection.getClientCache().get(key);
		if (coll == null) {
			Timer timer = this.mLog.isDebugEnabled() ? new Timer(this.mLog) : null;

			try {
				coll = (new ListSessionServer(this.getConnection())).getList(listName);
			} catch (Exception var6) {
				throw new RuntimeException("unable to get " + listName + " list", var6);
			}

			this.mConnection.getClientCache().put(key, coll);
			if (timer != null) {
				timer.logDebug("getList", listName);
			}
		} else {
			this.mLog.debug("getList", "obtained " + listName + " list from cache");
		}

		return coll;
	}

	public EntityList getBudgetDetailsForUser(int userId) {
		return this.getBudgetDetailsForUser(userId, false, 0, 0);
	}

	public EntityList getBudgetDetailsForUser(int userId, int modelId) {
		BudgetDetailsForUserELO coll = null;
		Timer timer = this.mLog.isDebugEnabled() ? new Timer(this.mLog) : null;

		try {
			coll = (new ListSessionServer(this.getConnection())).getBudgetDetailsForUser(userId, modelId);
		} catch (Exception var6) {
			throw new RuntimeException("unable to get BudgetUser BudgetDetailsForUser", var6);
		}

		if (timer != null) {
			timer.logDebug("getBudgetDetailsForUser", "");
		}

		return coll;
	}

	public EntityList getBudgetDetailsForUser(int userId, boolean detailedSelection, int locationId, int cycleId) {
		BudgetDetailsForUserELO coll = null;
		Timer timer = this.mLog.isDebugEnabled() ? new Timer(this.mLog) : null;

		try {
			coll = (new ListSessionServer(this.getConnection())).getBudgetDetailsForUser(userId, detailedSelection, locationId, cycleId);
		} catch (Exception var8) {
			throw new RuntimeException("unable to get BudgetUser BudgetDetailsForUser", var8);
		}

		if (timer != null) {
			timer.logDebug("getBudgetDetailsForUser", "");
		}

		return coll;
	}

	public EntityList getBudgetUserDetails(int bcId, int[] structureElementId) {
		EntityList coll = null;
		Timer timer = this.mLog.isDebugEnabled() ? new Timer(this.mLog) : null;

		try {
			coll = (new ListSessionServer(this.getConnection())).getBudgetUserDetails(bcId, structureElementId);
		} catch (Exception var6) {
			throw new RuntimeException("unable to get BudgetUser BudgetDetailsForUser", var6);
		}

		if (timer != null) {
			timer.logDebug("getBudgetDetailsForUser", "");
		}

		return coll;
	}

	public EntityList getBudgetUserDetailsNodeDown(int bcId, int structureElementId, int structureId) {
		EntityList coll = null;
		Timer timer = this.mLog.isDebugEnabled() ? new Timer(this.mLog) : null;

		try {
			coll = (new ListSessionServer(this.getConnection())).getBudgetUserDetailsNodeDown(bcId, structureElementId, structureId);
		} catch (Exception var7) {
			throw new RuntimeException("unable to get BudgetUser getBudgetUserDetailsNodeDown", var7);
		}

		if (timer != null) {
			timer.logDebug("getBudgetUserDetailsNodeDown", "");
		}

		return coll;
	}

	public EntityList getBudgetUserAuthDetailsNodeUp(int bcId, int structureElementId, int structureId) {
		EntityList coll = null;
		Timer timer = this.mLog.isDebugEnabled() ? new Timer(this.mLog) : null;

		try {
			coll = (new ListSessionServer(this.getConnection())).getBudgetUserAuthDetailsNodeUp(bcId, structureElementId, structureId);
		} catch (Exception var7) {
			throw new RuntimeException("unable to get BudgetUser getBudgetUserAuthDetailsNodeUp", var7);
		}

		if (timer != null) {
			timer.logDebug("getBudgetUserAuthDetailsNodeUp", "");
		}

		return coll;
	}

	public EntityList getImmediateChildren(Object key) {
		int structureId = ((StructureElementPK) key).getStructureId();
		int elementId = ((StructureElementPK) key).getStructureElementId();
		return this.getImmediateChildren(structureId, elementId);
	}

	public EntityList getStructureElementValues(Object key) {
		StructureElementRef ref = (StructureElementRef) key;
		StructureElementPK pk = (StructureElementPK) ref.getPrimaryKey();
		int structureId = pk.getStructureId();
		int structureElementid = pk.getStructureElementId();
		return this.getStructureElementValues(structureId, structureElementid);
	}

	public EntityList getStructureElementIdFromModel(int modelId) {
		return (new ListSessionServer(this.getConnection())).getStructureElementIdFromModel(modelId);
	}

	public EntityList getPickerStartUpDetails(int modelid, int[] structureElementId, int userId) {
		return (new ListSessionServer(this.getConnection())).getPickerStartUpDetails(modelid, structureElementId, userId);
	}

	public EntityList getAllPublicXmlReportFolders() {
		Timer timer = this.mLog.isDebugEnabled() ? new Timer(this.mLog) : null;
		AllPublicXmlReportFoldersELO coll = null;

		try {
			coll = (new ListSessionServer(this.getConnection())).getAllPublicXmlReportFolders();
		} catch (Exception var4) {
			throw new RuntimeException("unable to get XmlReportFolder AllPublicXmlReportFolders", var4);
		}

		if (timer != null) {
			timer.logDebug("getAllPublicXmlReportFolders", "");
		}

		return coll;
	}

	public EntityList getAllXmlReportFoldersForUser(int param1) {
		Timer timer = this.mLog.isDebugEnabled() ? new Timer(this.mLog) : null;
		AllXmlReportFoldersForUserELO coll = null;

		try {
			coll = (new ListSessionServer(this.getConnection())).getAllXmlReportFoldersForUser(param1);
		} catch (Exception var5) {
			throw new RuntimeException("unable to get XmlReportFolder AllXmlReportFoldersForUser", var5);
		}

		if (timer != null) {
			timer.logDebug("getAllXmlReportFoldersForUser", "");
		}

		return coll;
	}

	public EntityList getTreeInfoForDimTypeInModel(int modelId, int dimType) {
		return (new ListSessionServer(this.getConnection())).getTreeInfoForDimTypeInModel(modelId, dimType);
	}

	public EntityList getTreeInfoForModel(int modelId) {
		return (new ListSessionServer(this.getConnection())).getTreeInfoForModel(modelId);
	}

	public EntityList getTreeInfoForModelDimTypes(int modelId, int[] type) {
		return (new ListSessionServer(this.getConnection())).getTreeInfoForModelDimTypes(modelId, type);
	}

	public EntityList getTreeInfoForModelDimSeq(int modelId, int[] seq) {
		return (new ListSessionServer(this.getConnection())).getTreeInfoForModelDimSeq(modelId, seq);
	}

	public EntityList getTreeInfoForModelRA(int modelId) {
		return (new ListSessionServer(this.getConnection())).getTreeInfoForModelRA(modelId);
	}

	public EntityList getCellCalcAccesDefs(int modelId) {
		return (new ListSessionServer(this.getConnection())).getCellCalcAccesDefs(modelId);
	}

	public EntityList doElementPickerSearch(int dimansionId, String visId) {
		return (new ListSessionServer(this.getConnection())).doElementPickerSearch(dimansionId, visId);
	}

	public EntityList getContactLocations(int budgetCycleId, int daysUntilDue) {
		return (new ListSessionServer(this.getConnection())).getContactLocations(budgetCycleId, daysUntilDue);
	}

	public EntityList getModernWelcomeDetails(int userId, int daysUntilDue) {
		return (new ListSessionServer(this.getConnection())).getModernWelcomeDetails(userId, daysUntilDue);
	}

	public boolean hasUserAccessToRespArea(int userId, int modelId, int structureElementId) {
		return (new ListSessionServer(this.getConnection())).hasUserAccessToRespArea(userId, modelId, structureElementId);
	}

	public EntityList getAllFinanceXmlFormsForModelAndUser(int modelId, int budgetCycleId, int userId, boolean hasDesignModeSecurity) {
		return (new ListSessionServer(this.getConnection())).getAllFinanceXmlFormsForModelAndUser(modelId, budgetCycleId, userId, hasDesignModeSecurity);
	}

	public EntityList getSummaryUnreadMessagesForUser(String userId) {
		return (new ListSessionServer(this.getConnection())).getSummaryUnreadMessagesForUser(userId);
	}

	public EntityList getDistinctInternalDestinationUsers(String[] ids) {
		return (new ListSessionServer(this.getConnection())).getDistinctInternalDestinationUsers(ids);
	}

	public EntityList getDistinctExternalDestinationUsers(String[] ids) {
		return (new ListSessionServer(this.getConnection())).getDistinctExternalDestinationUsers(ids);
	}

	public List getLookupTableData(String tableName, List columnDef) {
		return (new ListSessionServer(this.getConnection())).getLookupTableData(tableName, columnDef);
	}

	public EntityList getTaskEvents(int taskId) {
		try {
			return (new TaskProcessServer(this.getConnection())).getTaskEvents(taskId);
		} catch (Throwable var3) {
			throw new RuntimeException(var3);
		}
	}

	public EntityList getPickerDataTypesWeb(int[] subTypes, boolean writeable) {
		return (new ListSessionServer(this.getConnection())).getPickerDataTypesWeb(subTypes, writeable);
	}

	public EntityList getPickerDataTypesWeb(int financeCubeId, int[] subTypes, boolean writeable) {
		return (new ListSessionServer(this.getConnection())).getPickerDataTypesWeb(financeCubeId, subTypes, writeable);
	}

	public EntityList getHierarcyDetailsFromDimId(DimensionRef ref) {
		DimensionPK pk = (DimensionPK) ref.getPrimaryKey();
		return this.getHierarcyDetailsFromDimId(pk.getDimensionId());
	}

	public EntityList getStructureElementParents(StructureElementRef ref) {
		StructureElementPK key = (StructureElementPK) ref.getPrimaryKey();
		return this.getStructureElementParents(key.getStructureId(), key.getStructureElementId());
	}

	public EntityList getChildrenForParent(StructureElementRef ref) {
		StructureElementPK key = (StructureElementPK) ref.getPrimaryKey();
		return this.getChildrenForParent(key.getStructureId(), key.getStructureId(), key.getStructureElementId(), key.getStructureId(), key.getStructureElementId());
	}

	public EntityList getAllLogonHistorysReport(String param1, Timestamp param2, int param3) {
		return (new ListSessionServer(this.getConnection())).getAllLogonHistorysReport(param1, param2, param3);
	}

	public EntityList getAllAmmModelsExceptThis(Object pk) {
		return (new ListSessionServer(this.getConnection())).getAllAmmModelsExceptThis(pk);
	}

	public EntityList getAllTaskGroups(Object key) {
		return (new ListSessionServer(this.getConnection())).getAllTaskGroups(key);
	}

	public EntityList getAllXmlFormsAndProfiles(String param1, String param2, String param3) {
		try {
			return (new ListSessionServer(this.getConnection())).getAllXmlFormsAndProfiles(param1, param2, param3);
		} catch (Exception var5) {
			var5.printStackTrace();
			throw new RuntimeException("can\'t get AllUserDetailsReport", var5);
		}
	}

	public List<Integer> getReadOnlyRaAccessPositions(int modelId, int userId) {
		Timer timer = this.mLog.isDebugEnabled() ? new Timer(this.mLog) : null;
		List<Integer> coll = null;

		try {
			coll = (new ListSessionServer(this.getConnection())).getReadOnlyRaAccessPositions(modelId, userId);
		} catch (Exception var6) {
			throw new RuntimeException("unable to get getReadOnlyRaAccessPositions", var6);
		}

		if (timer != null) {
			timer.logDebug("getReadOnlyRaAccessPositions", "");
		}

		return coll;
	}

	public EntityList getUserMessageAttributesForMultipleIds(String[] params) {
		StringBuffer key = new StringBuffer();
		StringBuilder sbparams = new StringBuilder();
		int n = 0;
		for (String i : params) {
			sbparams.append(i);
			n++;
			if (n < params.length)
				sbparams.append(",");
		}
		sbparams.substring(0, sbparams.length());

		key.append("UserMessageAttributesForId");
		key.append('.');
		key.append(String.valueOf(sbparams.toString()));
		UserMessageAttributesForIdELO coll = (UserMessageAttributesForIdELO) mConnection.getClientCache().get(key.toString());
		if (coll == null) {
			Timer timer = mLog.isDebugEnabled() ? new Timer(mLog) : null;
			try {
				coll = new ListSessionServer(getConnection()).getUserMessageAttributesForMultipleIds(params);
			} catch (Exception e) {
				throw new RuntimeException("unable to get User getUserMessageAttributesForMultipleIds", e);
			}
			mConnection.getClientCache().put(key.toString(), coll);
			if (timer != null)
				timer.logDebug("getUserMessageAttributesForMultipleIds", "");
		} else {
			mLog.debug("getUserMessageAttributesForMultipleIds", new StringBuilder().append("obtained ").append(key).append(" from cache").toString());
		}
		return coll;
	}

	public EntityList getMailDetailForUser(String userName, int type, int from, int to) {
		Timer timer = mLog.isDebugEnabled() ? new Timer(mLog) : null;
		EntityList coll = null;
		try {
			coll = new ListSessionServer(getConnection()).getMailDetailForUser(userName, type, from, to);
		} catch (Exception e) {
			throw new RuntimeException("unable to get Message InBoxForUser", e);
		}
		if (timer != null) {
			timer.logDebug("getInBoxDetailForUser", "");
		}
		return coll;
	}

	public EntityList getAllMasterQuestions() {
		StringBuffer key = new StringBuffer();
		key.append("AllMasterQuestions");
		AllMasterQuestionsELO coll = (AllMasterQuestionsELO) mConnection.getClientCache().get(key.toString());
		if (coll == null) {
			Timer timer = mLog.isDebugEnabled() ? new Timer(mLog) : null;
			try {
				coll = new ListSessionServer(getConnection()).getAllMasterQuestions();
			} catch (Exception e) {
				throw new RuntimeException("unable to get MasterQuestion AllMasterQuestions", e);
			}
			if (!mConnection.isCluster())
				mConnection.getClientCache().put(key.toString(), coll);
			if (timer != null)
				timer.logDebug("getAllMasterQuestions", "");
		} else {
			mLog.debug("getAllMasterQuestions", new StringBuilder().append("obtained ").append(key).append(" from cache").toString());
		}
		return coll;
	}

	public EntityList getQuestionByID(int param1) {
		StringBuffer key = new StringBuffer();
		key.append("QuestionByID");
		key.append('.');
		key.append(String.valueOf(param1));
		QuestionByIDELO coll = (QuestionByIDELO) mConnection.getClientCache().get(key.toString());
		if (coll == null) {
			Timer timer = mLog.isDebugEnabled() ? new Timer(mLog) : null;
			try {
				coll = new ListSessionServer(getConnection()).getQuestionByID(param1);
			} catch (Exception e) {
				throw new RuntimeException("unable to get MasterQuestion QuestionByID", e);
			}
			if (!mConnection.isCluster())
				mConnection.getClientCache().put(key.toString(), coll);
			if (timer != null)
				timer.logDebug("getQuestionByID", "");
		} else {
			mLog.debug("getQuestionByID", new StringBuilder().append("obtained ").append(key).append(" from cache").toString());
		}
		return coll;
	}

	public EntityList getAllChallengeQuestions() {
		StringBuffer key = new StringBuffer();
		key.append("AllChallengeQuestions");
		AllChallengeQuestionsELO coll = (AllChallengeQuestionsELO) mConnection.getClientCache().get(key.toString());
		if (coll == null) {
			Timer timer = mLog.isDebugEnabled() ? new Timer(mLog) : null;
			try {
				coll = new ListSessionServer(getConnection()).getAllChallengeQuestions();
			} catch (Exception e) {
				throw new RuntimeException("unable to get ChallengeQuestion AllChallengeQuestions", e);
			}
			if (!mConnection.isCluster())
				mConnection.getClientCache().put(key.toString(), coll);
			if (timer != null)
				timer.logDebug("getAllChallengeQuestions", "");
		} else {
			mLog.debug("getAllChallengeQuestions", new StringBuilder().append("obtained ").append(key).append(" from cache").toString());
		}
		return coll;
	}

	public EntityList getAllQuestionsAndAnswersByUserID(int param1) {
		StringBuffer key = new StringBuffer();
		key.append("AllQuestionsAndAnswersByUserID");
		key.append('.');
		key.append(String.valueOf(param1));
		AllQuestionsAndAnswersByUserIDELO coll = (AllQuestionsAndAnswersByUserIDELO) mConnection.getClientCache().get(key.toString());
		if (coll == null) {
			Timer timer = mLog.isDebugEnabled() ? new Timer(mLog) : null;
			try {
				coll = new ListSessionServer(getConnection()).getAllQuestionsAndAnswersByUserID(param1);
			} catch (Exception e) {
				throw new RuntimeException("unable to get ChallengeQuestion AllQuestionsAndAnswersByUserID", e);
			}
			if (!mConnection.isCluster())
				mConnection.getClientCache().put(key.toString(), coll);
			if (timer != null)
				timer.logDebug("getAllQuestionsAndAnswersByUserID", "");
		} else {
			mLog.debug("getAllQuestionsAndAnswersByUserID", new StringBuilder().append("obtained ").append(key).append(" from cache").toString());
		}
		return coll;
	}
	
	public EntityList getChallengeWord(int userId) {
		StringBuffer key = new StringBuffer();
		key.append("getChallengeWord");
		key.append('.');
		key.append(String.valueOf(userId));
		AllQuestionsAndAnswersByUserIDELO coll = (AllQuestionsAndAnswersByUserIDELO) mConnection.getClientCache().get(key.toString());
		if (coll == null) {
			Timer timer = mLog.isDebugEnabled() ? new Timer(mLog) : null;
			try {
				coll = new ListSessionServer(getConnection()).getChallengeWord(userId);
			} catch (Exception e) {
				throw new RuntimeException("unable to getChallengeWord", e);
			}
			if (!mConnection.isCluster())
				mConnection.getClientCache().put(key.toString(), coll);
			if (timer != null)
				timer.logDebug("getChallengeWord", "");
		} else {
			mLog.debug("getChallengeWord", new StringBuilder().append("obtained ").append(key).append(" from cache").toString());
		}
		return coll;
	}
	
	public void setChallengeWord(int userId, String word) {

		Timer timer = this.mLog.isDebugEnabled() ? new Timer(this.mLog) : null;

		try {
			new ListSessionServer(this.getConnection()).setChallengeWord(userId, word);
		} catch (Exception var5) {
			throw new RuntimeException("unable to set ChallengeWord", var5);
		}

		if (timer != null) {
			timer.logDebug("ChallengeWord", "");
		}
	}

	public EntityList getAllUserResetLinks() {
		StringBuffer key = new StringBuffer();
		key.append("AllUserResetLinks");
		AllUserResetLinksELO coll = (AllUserResetLinksELO) mConnection.getClientCache().get(key.toString());
		if (coll == null) {
			Timer timer = mLog.isDebugEnabled() ? new Timer(mLog) : null;
			try {
				coll = new ListSessionServer(getConnection()).getAllUserResetLinks();
			} catch (Exception e) {
				throw new RuntimeException("unable to get UserResetLink AllUserResetLinks", e);
			}
			if (!mConnection.isCluster())
				mConnection.getClientCache().put(key.toString(), coll);
			if (timer != null)
				timer.logDebug("getAllUserResetLinks", "");
		} else {
			mLog.debug("getAllUserResetLinks", new StringBuilder().append("obtained ").append(key).append(" from cache").toString());
		}
		return coll;
	}

	public EntityList getLinkByUserID(int param1) {
		StringBuffer key = new StringBuffer();
		key.append("LinkByUserID");
		key.append('.');
		key.append(String.valueOf(param1));
		LinkByUserIDELO coll = (LinkByUserIDELO) mConnection.getClientCache().get(key.toString());
		if (coll == null) {
			Timer timer = mLog.isDebugEnabled() ? new Timer(mLog) : null;
			try {
				coll = new ListSessionServer(getConnection()).getLinkByUserID(param1);
			} catch (Exception e) {
				throw new RuntimeException("unable to get UserResetLink LinkByUserID", e);
			}
			if (!mConnection.isCluster())
				mConnection.getClientCache().put(key.toString(), coll);
			if (timer != null)
				timer.logDebug("getLinkByUserID", "");
		} else {
			mLog.debug("getLinkByUserID", new StringBuilder().append("obtained ").append(key).append(" from cache").toString());
		}
		return coll;
	}

	public EntityList getModelUserSecurity() {
		Timer timer = mLog.isDebugEnabled() ? new Timer(mLog) : null;
		EntityList coll = null;
		try {
			coll = new ListSessionServer(getConnection()).getModelUserSecurity();
		} catch (Exception e) {
			throw new RuntimeException("unable to get ModelUserSecurity", e);
		}
		if (timer != null) {
			timer.logDebug("getModelUserSecurity", "");
		}
		return coll;
	}

	public EntityList getUserModelSecurity() {
		Timer timer = mLog.isDebugEnabled() ? new Timer(mLog) : null;
		EntityList coll = null;
		try {
			coll = new ListSessionServer(getConnection()).getUserModelSecurity();
		} catch (Exception e) {
			throw new RuntimeException("unable to get UserModelSecurity", e);
		}
		if (timer != null) {
			timer.logDebug("getUserModelSecurity", "");
		}
		return coll;
	}

	public List<UserModelElementAssignment> getRespAreaAccess() {
		Timer timer = mLog.isDebugEnabled() ? new Timer(mLog) : null;
		List<UserModelElementAssignment> coll = null;
		try {
			UserPK userPk = (UserPK) ((UserRef) getConnection().getUserContext().getUserRef()).getPrimaryKey();
			coll = new ListSessionServer(getConnection()).getRespAreaAccess(userPk);
		} catch (Exception e) {
			throw new RuntimeException("unable to get ModelAndHierarchies", e);
		}
		if (timer != null) {
			timer.logDebug("getModelAndHierarchies", "");
		}
		return coll;
	}

	public EntityList getDataEntryProfile(int bcId, int modelId) {
		Timer timer = mLog.isDebugEnabled() ? new Timer(mLog) : null;
		EntityList dataEntryProfiles;
		try {
			DataEntryProfilesProcess process = getConnection().getDataEntryProfilesProcess();
			dataEntryProfiles = process.getAllDataEntryProfilesForUser(getConnection().getUserContext().getUserId(), modelId, bcId);
		} catch (Exception e) {
			throw new RuntimeException("unable to get DataEntryProfileForBcAndUser", e);
		}
		if (timer != null) {
			timer.logDebug("getDataEntryProfileForBcAndUser", "");
		}
		return dataEntryProfiles;
	}

	public EntityList getAllLoggedInUsers() {
		EntityList coll = null;
		if (coll == null) {
			Timer timer = this.mLog.isDebugEnabled() ? new Timer(this.mLog) : null;

			try {
				coll = (new ListSessionServer(this.getConnection())).getAllLoggedInUsers();
			} catch (Exception var5) {
				throw new RuntimeException("unable to get AllLoggedInUsers", var5);
			}
			if (timer != null) {
				timer.logDebug("getAllRecalculateBatchTasks", "");
			}
		}
		return coll;
	}

	public String getCPContextId(Object context) {

		String coll = null;
		Timer timer = this.mLog.isDebugEnabled() ? new Timer(this.mLog) : null;

		try {
			coll = (new ListSessionServer(this.getConnection())).getCPContextId(context);
		} catch (Exception var5) {
			throw new RuntimeException("unable to get CPContextId", var5);
		}

		if (timer != null) {
			timer.logDebug("getCPContextId", "");
		}
		return coll;
	}

	public Object getCPContext(String id) {

		Object coll = null;
		Timer timer = this.mLog.isDebugEnabled() ? new Timer(this.mLog) : null;

		try {
			coll = (new ListSessionServer(this.getConnection())).getCPContext(id);
		} catch (Exception var5) {
			throw new RuntimeException("unable to get CPContext", var5);
		}

		if (timer != null) {
			timer.logDebug("getCPContext", "");
		}
		return coll;
	}

	public void removeContext(Object context) {

		Timer timer = this.mLog.isDebugEnabled() ? new Timer(this.mLog) : null;

		try {
			new ListSessionServer(this.getConnection()).removeContext(context);
		} catch (Exception var5) {
			throw new RuntimeException("unable to remove Context", var5);
		}

		if (timer != null) {
			timer.logDebug("removeContext", "");
		}
	}

	public void removeContextByContextId(List<String> contextIds) {

		Timer timer = this.mLog.isDebugEnabled() ? new Timer(this.mLog) : null;

		try {
			new ListSessionServer(this.getConnection()).removeContextByContextId(contextIds);
		} catch (Exception var5) {
			throw new RuntimeException("unable to remove Context", var5);
		}

		if (timer != null) {
			timer.logDebug("removeContext", "");
		}
	}
	
	public void removeContextByUserName(List<String> userNames) {

        Timer timer = this.mLog.isDebugEnabled() ? new Timer(this.mLog) : null;

        try {
            new ListSessionServer(this.getConnection()).removeContextByUserName(userNames);
        } catch (Exception var5) {
            throw new RuntimeException("unable to remove Context", var5);
        }

        if (timer != null) {
            timer.logDebug("removeContext", "");
        }
	}

	public Map getContextSnapShot() {

		Map coll = null;
		Timer timer = this.mLog.isDebugEnabled() ? new Timer(this.mLog) : null;

		try {
			coll = (new ListSessionServer(this.getConnection())).getContextSnapShot();
		} catch (Exception var5) {
			throw new RuntimeException("unable to get ContextSnapShot", var5);
		}
		if (timer != null) {
			timer.logDebug("getContextSnapShot", "");
		}
		return coll;
	}

	/**
	 * Get all XML Forms for current User
	 */

	public EntityList getXcellXmlFormsForUser(int userId) {
		StringBuffer key = new StringBuffer();
		key.append("XcellXmlFormsForUser");
		AllXmlFormsELO coll = (AllXmlFormsELO) this.mConnection.getClientCache().get(key.toString());
		if (coll == null) {
			Timer timer = this.mLog.isDebugEnabled() ? new Timer(this.mLog) : null;

			try {
				coll = (new ListSessionServer(this.getConnection())).getXcellXmlFormsForUser(userId);
			} catch (Exception var5) {
				throw new RuntimeException("unable to get XmlForms for user id: " + userId, var5);
			}

			this.mConnection.getClientCache().put(key.toString(), coll);
			if (timer != null) {
				timer.logDebug("getXcellXmlFormsForUser", "");
			}
		} else {
			this.mLog.debug("getXcellXmlFormsForUser", "obtained " + key + " from cache");
		}

		return coll;
	}
	
	public ArrayList<Object[]> getNotesForCostCenters(ArrayList<Integer> costCenters, int financeCubeId, String fromDate, String toDate) {
		ArrayList<Object[]> coll;

			try {
				coll = (new ListSessionServer(this.getConnection())).getNotesForCostCenters(costCenters, financeCubeId, fromDate, toDate);
			} catch (Exception var5) {
				throw new RuntimeException("unable to get Notes For Cost Centers", var5);
			}

		return coll;
	}
	
	public HashMap<String, ArrayList<HierarchyRef>> getCalendarForModels(HashSet<String> models) {
		StringBuffer key = new StringBuffer();
		key.append("CalendarForModels");
		HashMap<String, ArrayList<HierarchyRef>> coll = (HashMap<String, ArrayList<HierarchyRef>>) this.mConnection.getClientCache().get(key.toString());
		if (coll == null) {
			Timer timer = this.mLog.isDebugEnabled() ? new Timer(this.mLog) : null;
			try {
				coll = (new ListSessionServer(this.getConnection())).getCalendarForModels(models);
			} catch (Exception var5) {
				throw new RuntimeException("unable to get Notes For Cost Centers", var5);
			}
		} else {
			this.mLog.debug("getCalendarForModels", "obtained " + key + " from cache");
		}
		return coll;
	}

}
