package com.cedar.cp.ejb.impl.base;

import com.cedar.cp.api.base.CPException;
import com.cedar.cp.api.base.EntityList;
import com.cedar.cp.api.base.EntityRef;
import com.cedar.cp.api.budgetlocation.UserModelElementAssignment;
import com.cedar.cp.api.dimension.HierarchyRef;
import com.cedar.cp.api.user.UserRef;
import com.cedar.cp.dto.admin.tidytask.AllTidyTasksELO;
import com.cedar.cp.dto.admin.tidytask.OrderedChildrenELO;
import com.cedar.cp.dto.admin.tidytask.TidyTaskCK;
import com.cedar.cp.dto.admin.tidytask.TidyTaskLinkCK;
import com.cedar.cp.dto.admin.tidytask.TidyTaskLinkPK;
import com.cedar.cp.dto.admin.tidytask.TidyTaskPK;
import com.cedar.cp.dto.authenticationpolicy.ActiveAuthenticationPolicyForLogonELO;
import com.cedar.cp.dto.authenticationpolicy.ActiveAuthenticationPolicysELO;
import com.cedar.cp.dto.authenticationpolicy.AllAuthenticationPolicysELO;
import com.cedar.cp.dto.authenticationpolicy.AuthenticationPolicyCK;
import com.cedar.cp.dto.authenticationpolicy.AuthenticationPolicyPK;
import com.cedar.cp.dto.base.CompositeKey;
import com.cedar.cp.dto.base.PrimaryKey;
import com.cedar.cp.dto.budgetinstruction.AllBudgetInstructionAssignmentsELO;
import com.cedar.cp.dto.budgetinstruction.AllBudgetInstructionsELO;
import com.cedar.cp.dto.budgetinstruction.AllBudgetInstructionsForCycleELO;
import com.cedar.cp.dto.budgetinstruction.AllBudgetInstructionsForLocationELO;
import com.cedar.cp.dto.budgetinstruction.AllBudgetInstructionsForModelELO;
import com.cedar.cp.dto.budgetinstruction.AllBudgetInstructionsWebELO;
import com.cedar.cp.dto.budgetinstruction.BudgetInstructionAssignmentCK;
import com.cedar.cp.dto.budgetinstruction.BudgetInstructionAssignmentPK;
import com.cedar.cp.dto.budgetinstruction.BudgetInstructionCK;
import com.cedar.cp.dto.budgetinstruction.BudgetInstructionPK;
import com.cedar.cp.dto.cm.AllChangeMgmtsELO;
import com.cedar.cp.dto.cm.AllChangeMgmtsForModelELO;
import com.cedar.cp.dto.cm.AllChangeMgmtsForModelWithXMLELO;
import com.cedar.cp.dto.cm.ChangeMgmtCK;
import com.cedar.cp.dto.cm.ChangeMgmtPK;
import com.cedar.cp.dto.cubeformula.AllCubeFormulasELO;
import com.cedar.cp.dto.cubeformula.AllPackagesForFinanceCubeELO;
import com.cedar.cp.dto.cubeformula.CubeFormulaCK;
import com.cedar.cp.dto.cubeformula.CubeFormulaPK;
import com.cedar.cp.dto.cubeformula.CubeFormulaPackageCK;
import com.cedar.cp.dto.cubeformula.CubeFormulaPackagePK;
import com.cedar.cp.dto.cubeformula.CubeFormulaeForFinanceCubeELO;
import com.cedar.cp.dto.cubeformula.FormulaDeploymentDtCK;
import com.cedar.cp.dto.cubeformula.FormulaDeploymentDtPK;
import com.cedar.cp.dto.cubeformula.FormulaDeploymentEntryCK;
import com.cedar.cp.dto.cubeformula.FormulaDeploymentEntryPK;
import com.cedar.cp.dto.cubeformula.FormulaDeploymentLineCK;
import com.cedar.cp.dto.cubeformula.FormulaDeploymentLinePK;
import com.cedar.cp.dto.currency.AllCurrencysELO;
import com.cedar.cp.dto.currency.CurrencyCK;
import com.cedar.cp.dto.currency.CurrencyPK;
import com.cedar.cp.dto.datatype.AllDataTypeForFinanceCubeELO;
import com.cedar.cp.dto.datatype.AllDataTypesELO;
import com.cedar.cp.dto.datatype.AllDataTypesForModelELO;
import com.cedar.cp.dto.datatype.AllDataTypesWebELO;
import com.cedar.cp.dto.datatype.DataTypeCK;
import com.cedar.cp.dto.datatype.DataTypeDependenciesELO;
import com.cedar.cp.dto.datatype.DataTypeDetailsForVisIDELO;
import com.cedar.cp.dto.datatype.DataTypePK;
import com.cedar.cp.dto.datatype.DataTypeRelCK;
import com.cedar.cp.dto.datatype.DataTypeRelPK;
import com.cedar.cp.dto.datatype.DataTypesByTypeELO;
import com.cedar.cp.dto.datatype.DataTypesByTypeWriteableELO;
import com.cedar.cp.dto.datatype.DataTypesForImpExpELO;
import com.cedar.cp.dto.defaultuserpref.DefaultUserPrefCK;
import com.cedar.cp.dto.defaultuserpref.DefaultUserPrefPK;
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
import com.cedar.cp.dto.dimension.AugHierarchyElementCK;
import com.cedar.cp.dto.dimension.AugHierarchyElementPK;
import com.cedar.cp.dto.dimension.AvailableDimensionsELO;
import com.cedar.cp.dto.dimension.BudgetHierarchyElementELO;
import com.cedar.cp.dto.dimension.BudgetLocationElementForModelELO;
import com.cedar.cp.dto.dimension.CalendarForFinanceCubeELO;
import com.cedar.cp.dto.dimension.CalendarForModelELO;
import com.cedar.cp.dto.dimension.CalendarForModelVisIdELO;
import com.cedar.cp.dto.dimension.CalendarSpecCK;
import com.cedar.cp.dto.dimension.CalendarSpecPK;
import com.cedar.cp.dto.dimension.CalendarYearSpecCK;
import com.cedar.cp.dto.dimension.CalendarYearSpecPK;
import com.cedar.cp.dto.dimension.CheckLeafELO;
import com.cedar.cp.dto.dimension.ChildrenForParentELO;
import com.cedar.cp.dto.dimension.DimensionCK;
import com.cedar.cp.dto.dimension.DimensionElementCK;
import com.cedar.cp.dto.dimension.DimensionElementPK;
import com.cedar.cp.dto.dimension.DimensionPK;
import com.cedar.cp.dto.dimension.HierarachyElementELO;
import com.cedar.cp.dto.dimension.HierarchyCK;
import com.cedar.cp.dto.dimension.HierarchyElementCK;
import com.cedar.cp.dto.dimension.HierarchyElementFeedCK;
import com.cedar.cp.dto.dimension.HierarchyElementFeedPK;
import com.cedar.cp.dto.dimension.HierarchyElementPK;
import com.cedar.cp.dto.dimension.HierarchyPK;
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
import com.cedar.cp.dto.dimension.SecurityRangeCK;
import com.cedar.cp.dto.dimension.SecurityRangePK;
import com.cedar.cp.dto.dimension.SecurityRangeRowCK;
import com.cedar.cp.dto.dimension.SecurityRangeRowPK;
import com.cedar.cp.dto.dimension.SelectedHierarchysELO;
import com.cedar.cp.dto.dimension.StructureElementByVisIdELO;
import com.cedar.cp.dto.dimension.StructureElementCK;
import com.cedar.cp.dto.dimension.StructureElementELO;
import com.cedar.cp.dto.dimension.StructureElementForIdsELO;
import com.cedar.cp.dto.dimension.StructureElementPK;
import com.cedar.cp.dto.dimension.StructureElementParentsELO;
import com.cedar.cp.dto.dimension.StructureElementParentsFromVisIdELO;
import com.cedar.cp.dto.dimension.StructureElementParentsReversedELO;
import com.cedar.cp.dto.dimension.StructureElementValuesELO;
import com.cedar.cp.dto.extendedattachment.AllExtendedAttachmentsELO;
import com.cedar.cp.dto.extendedattachment.AllImageExtendedAttachmentsELO;
import com.cedar.cp.dto.extendedattachment.ExtendedAttachmentCK;
import com.cedar.cp.dto.extendedattachment.ExtendedAttachmentPK;
import com.cedar.cp.dto.extendedattachment.ExtendedAttachmentsForIdELO;
import com.cedar.cp.dto.extsys.AllExternalSystemCompainesELO;
import com.cedar.cp.dto.extsys.AllExternalSystemsELO;
import com.cedar.cp.dto.extsys.AllGenericExternalSystemsELO;
import com.cedar.cp.dto.extsys.ExtSysCalElementCK;
import com.cedar.cp.dto.extsys.ExtSysCalElementPK;
import com.cedar.cp.dto.extsys.ExtSysCalendarYearCK;
import com.cedar.cp.dto.extsys.ExtSysCalendarYearPK;
import com.cedar.cp.dto.extsys.ExtSysCompanyCK;
import com.cedar.cp.dto.extsys.ExtSysCompanyPK;
import com.cedar.cp.dto.extsys.ExtSysCurrencyCK;
import com.cedar.cp.dto.extsys.ExtSysCurrencyPK;
import com.cedar.cp.dto.extsys.ExtSysDimElementCK;
import com.cedar.cp.dto.extsys.ExtSysDimElementPK;
import com.cedar.cp.dto.extsys.ExtSysDimensionCK;
import com.cedar.cp.dto.extsys.ExtSysDimensionPK;
import com.cedar.cp.dto.extsys.ExtSysHierElemFeedCK;
import com.cedar.cp.dto.extsys.ExtSysHierElemFeedPK;
import com.cedar.cp.dto.extsys.ExtSysHierElementCK;
import com.cedar.cp.dto.extsys.ExtSysHierElementPK;
import com.cedar.cp.dto.extsys.ExtSysHierarchyCK;
import com.cedar.cp.dto.extsys.ExtSysHierarchyPK;
import com.cedar.cp.dto.extsys.ExtSysLedgerCK;
import com.cedar.cp.dto.extsys.ExtSysLedgerPK;
import com.cedar.cp.dto.extsys.ExtSysPropertyCK;
import com.cedar.cp.dto.extsys.ExtSysPropertyPK;
import com.cedar.cp.dto.extsys.ExtSysValueTypeCK;
import com.cedar.cp.dto.extsys.ExtSysValueTypePK;
import com.cedar.cp.dto.extsys.ExternalSystemCK;
import com.cedar.cp.dto.extsys.ExternalSystemPK;
import com.cedar.cp.dto.formnotes.AllFormNotesForBudgetLocationELO;
import com.cedar.cp.dto.formnotes.AllFormNotesForFormAndBudgetLocationELO;
import com.cedar.cp.dto.formnotes.FormNotesCK;
import com.cedar.cp.dto.formnotes.FormNotesPK;
import com.cedar.cp.dto.impexp.AllImpExpHdrsELO;
import com.cedar.cp.dto.impexp.ImpExpHdrCK;
import com.cedar.cp.dto.impexp.ImpExpHdrPK;
import com.cedar.cp.dto.impexp.ImpExpRowCK;
import com.cedar.cp.dto.impexp.ImpExpRowPK;
import com.cedar.cp.dto.importtask.AllImportTasksELO;
import com.cedar.cp.dto.logonhistory.AllLogonHistorysELO;
import com.cedar.cp.dto.logonhistory.AllLogonHistorysReportELO;
import com.cedar.cp.dto.logonhistory.LogonHistoryCK;
import com.cedar.cp.dto.logonhistory.LogonHistoryPK;
import com.cedar.cp.dto.masterquestion.AllMasterQuestionsELO;
import com.cedar.cp.dto.masterquestion.MasterQuestionCK;
import com.cedar.cp.dto.masterquestion.MasterQuestionPK;
import com.cedar.cp.dto.masterquestion.QuestionByIDELO;
import com.cedar.cp.dto.message.AllMessagesELO;
import com.cedar.cp.dto.message.AllMessagesToUserELO;
import com.cedar.cp.dto.message.AttatchmentForMessageELO;
import com.cedar.cp.dto.message.InBoxForUserELO;
import com.cedar.cp.dto.message.MessageAttatchCK;
import com.cedar.cp.dto.message.MessageAttatchPK;
import com.cedar.cp.dto.message.MessageCK;
import com.cedar.cp.dto.message.MessageCountELO;
import com.cedar.cp.dto.message.MessageForIdELO;
import com.cedar.cp.dto.message.MessageForIdSentItemELO;
import com.cedar.cp.dto.message.MessageFromUserELO;
import com.cedar.cp.dto.message.MessagePK;
import com.cedar.cp.dto.message.MessageToUserELO;
import com.cedar.cp.dto.message.MessageUserCK;
import com.cedar.cp.dto.message.MessageUserPK;
import com.cedar.cp.dto.message.SentItemsForUserELO;
import com.cedar.cp.dto.message.UnreadInBoxForUserELO;
import com.cedar.cp.dto.model.*;
import com.cedar.cp.dto.model.act.ActivitiesForCycleandElementELO;
import com.cedar.cp.dto.model.act.ActivityDetailsELO;
import com.cedar.cp.dto.model.act.ActivityFullDetailsELO;
import com.cedar.cp.dto.model.act.BudgetActivityCK;
import com.cedar.cp.dto.model.act.BudgetActivityLinkCK;
import com.cedar.cp.dto.model.act.BudgetActivityLinkPK;
import com.cedar.cp.dto.model.act.BudgetActivityPK;
import com.cedar.cp.dto.model.amm.AllAmmModelsELO;
import com.cedar.cp.dto.model.amm.AmmDataTypeCK;
import com.cedar.cp.dto.model.amm.AmmDataTypePK;
import com.cedar.cp.dto.model.amm.AmmDimensionCK;
import com.cedar.cp.dto.model.amm.AmmDimensionElementCK;
import com.cedar.cp.dto.model.amm.AmmDimensionElementPK;
import com.cedar.cp.dto.model.amm.AmmDimensionPK;
import com.cedar.cp.dto.model.amm.AmmFinanceCubeCK;
import com.cedar.cp.dto.model.amm.AmmFinanceCubePK;
import com.cedar.cp.dto.model.amm.AmmModelCK;
import com.cedar.cp.dto.model.amm.AmmModelPK;
import com.cedar.cp.dto.model.amm.AmmSrcStructureElementCK;
import com.cedar.cp.dto.model.amm.AmmSrcStructureElementPK;
import com.cedar.cp.dto.model.budgetlimit.AllBudgetLimitsELO;
import com.cedar.cp.dto.model.budgetlimit.BudgetLimitCK;
import com.cedar.cp.dto.model.budgetlimit.BudgetLimitPK;
import com.cedar.cp.dto.model.cc.AllCcDeploymentsELO;
import com.cedar.cp.dto.model.cc.CcDeploymentCK;
import com.cedar.cp.dto.model.cc.CcDeploymentCellPickerInfoELO;
import com.cedar.cp.dto.model.cc.CcDeploymentDataTypeCK;
import com.cedar.cp.dto.model.cc.CcDeploymentDataTypePK;
import com.cedar.cp.dto.model.cc.CcDeploymentEntryCK;
import com.cedar.cp.dto.model.cc.CcDeploymentEntryPK;
import com.cedar.cp.dto.model.cc.CcDeploymentLineCK;
import com.cedar.cp.dto.model.cc.CcDeploymentLinePK;
import com.cedar.cp.dto.model.cc.CcDeploymentPK;
import com.cedar.cp.dto.model.cc.CcDeploymentXMLFormTypeELO;
import com.cedar.cp.dto.model.cc.CcDeploymentsForLookupTableELO;
import com.cedar.cp.dto.model.cc.CcDeploymentsForModelELO;
import com.cedar.cp.dto.model.cc.CcDeploymentsForXmlFormELO;
import com.cedar.cp.dto.model.cc.CcMappingEntryCK;
import com.cedar.cp.dto.model.cc.CcMappingEntryPK;
import com.cedar.cp.dto.model.cc.CcMappingLineCK;
import com.cedar.cp.dto.model.cc.CcMappingLinePK;
import com.cedar.cp.dto.model.cc.imp.dyn.ImportGridCK;
import com.cedar.cp.dto.model.cc.imp.dyn.ImportGridCellCK;
import com.cedar.cp.dto.model.cc.imp.dyn.ImportGridCellPK;
import com.cedar.cp.dto.model.cc.imp.dyn.ImportGridPK;
import com.cedar.cp.dto.model.globalmapping2.GlobalMappedModel2CK;
import com.cedar.cp.dto.model.globalmapping2.GlobalMappedModel2PK;
import com.cedar.cp.dto.model.globalmapping2.AllGlobalMappedModels2ELO;
import com.cedar.cp.dto.model.mapping.AllMappedModelsELO;
import com.cedar.cp.dto.model.mapping.MappedCalendarElementCK;
import com.cedar.cp.dto.model.mapping.MappedCalendarElementPK;
import com.cedar.cp.dto.model.mapping.MappedCalendarYearCK;
import com.cedar.cp.dto.model.mapping.MappedCalendarYearPK;
import com.cedar.cp.dto.model.mapping.MappedDataTypeCK;
import com.cedar.cp.dto.model.mapping.MappedDataTypePK;
import com.cedar.cp.dto.model.mapping.MappedDimensionCK;
import com.cedar.cp.dto.model.mapping.MappedDimensionElementCK;
import com.cedar.cp.dto.model.mapping.MappedDimensionElementPK;
import com.cedar.cp.dto.model.mapping.MappedDimensionPK;
import com.cedar.cp.dto.model.mapping.MappedFinanceCubeCK;
import com.cedar.cp.dto.model.mapping.MappedFinanceCubePK;
import com.cedar.cp.dto.model.mapping.MappedFinanceCubesELO;
import com.cedar.cp.dto.model.mapping.MappedHierarchyCK;
import com.cedar.cp.dto.model.mapping.MappedHierarchyPK;
import com.cedar.cp.dto.model.mapping.MappedModelCK;
import com.cedar.cp.dto.model.mapping.MappedModelPK;
import com.cedar.cp.dto.model.ra.AllResponsibilityAreasELO;
import com.cedar.cp.dto.model.ra.ResponsibilityAreaCK;
import com.cedar.cp.dto.model.ra.ResponsibilityAreaPK;
import com.cedar.cp.dto.model.recharge.AllRechargeGroupsELO;
import com.cedar.cp.dto.model.recharge.AllRechargesELO;
import com.cedar.cp.dto.model.recharge.AllRechargesWithModelELO;
import com.cedar.cp.dto.model.recharge.RechargeCK;
import com.cedar.cp.dto.model.recharge.RechargeCellsCK;
import com.cedar.cp.dto.model.recharge.RechargeCellsPK;
import com.cedar.cp.dto.model.recharge.RechargeGroupCK;
import com.cedar.cp.dto.model.recharge.RechargeGroupPK;
import com.cedar.cp.dto.model.recharge.RechargePK;
import com.cedar.cp.dto.model.recharge.SingleRechargeELO;
import com.cedar.cp.dto.model.udwp.AllWeightingDeploymentsELO;
import com.cedar.cp.dto.model.udwp.AllWeightingProfilesELO;
import com.cedar.cp.dto.model.udwp.WeightingDeploymentCK;
import com.cedar.cp.dto.model.udwp.WeightingDeploymentLineCK;
import com.cedar.cp.dto.model.udwp.WeightingDeploymentLinePK;
import com.cedar.cp.dto.model.udwp.WeightingDeploymentPK;
import com.cedar.cp.dto.model.udwp.WeightingProfileCK;
import com.cedar.cp.dto.model.udwp.WeightingProfileLineCK;
import com.cedar.cp.dto.model.udwp.WeightingProfileLinePK;
import com.cedar.cp.dto.model.udwp.WeightingProfilePK;
import com.cedar.cp.dto.model.virement.AccountsForCategoryELO;
import com.cedar.cp.dto.model.virement.AllVirementCategorysELO;
import com.cedar.cp.dto.model.virement.AllVirementRequestGroupsELO;
import com.cedar.cp.dto.model.virement.AllVirementRequestsELO;
import com.cedar.cp.dto.model.virement.LocationsForCategoryELO;
import com.cedar.cp.dto.model.virement.VirementAccountCK;
import com.cedar.cp.dto.model.virement.VirementAccountPK;
import com.cedar.cp.dto.model.virement.VirementAuthPointCK;
import com.cedar.cp.dto.model.virement.VirementAuthPointLinkCK;
import com.cedar.cp.dto.model.virement.VirementAuthPointLinkPK;
import com.cedar.cp.dto.model.virement.VirementAuthPointPK;
import com.cedar.cp.dto.model.virement.VirementAuthorisersCK;
import com.cedar.cp.dto.model.virement.VirementAuthorisersPK;
import com.cedar.cp.dto.model.virement.VirementCategoryCK;
import com.cedar.cp.dto.model.virement.VirementCategoryPK;
import com.cedar.cp.dto.model.virement.VirementLineSpreadCK;
import com.cedar.cp.dto.model.virement.VirementLineSpreadPK;
import com.cedar.cp.dto.model.virement.VirementLocationCK;
import com.cedar.cp.dto.model.virement.VirementLocationPK;
import com.cedar.cp.dto.model.virement.VirementRequestCK;
import com.cedar.cp.dto.model.virement.VirementRequestGroupCK;
import com.cedar.cp.dto.model.virement.VirementRequestGroupPK;
import com.cedar.cp.dto.model.virement.VirementRequestLineCK;
import com.cedar.cp.dto.model.virement.VirementRequestLinePK;
import com.cedar.cp.dto.model.virement.VirementRequestPK;
import com.cedar.cp.dto.passwordhistory.AllPasswordHistorysELO;
import com.cedar.cp.dto.passwordhistory.PasswordHistoryCK;
import com.cedar.cp.dto.passwordhistory.PasswordHistoryPK;
import com.cedar.cp.dto.passwordhistory.UserPasswordHistoryELO;
import com.cedar.cp.dto.perftest.AllPerfTestsELO;
import com.cedar.cp.dto.perftest.PerfTestCK;
import com.cedar.cp.dto.perftest.PerfTestPK;
import com.cedar.cp.dto.perftestrun.AllPerfTestRunResultsELO;
import com.cedar.cp.dto.perftestrun.AllPerfTestRunsELO;
import com.cedar.cp.dto.perftestrun.PerfTestRunCK;
import com.cedar.cp.dto.perftestrun.PerfTestRunPK;
import com.cedar.cp.dto.perftestrun.PerfTestRunResultCK;
import com.cedar.cp.dto.perftestrun.PerfTestRunResultPK;
import com.cedar.cp.dto.recalculate.AllRecalculateBatchTasksELO;
import com.cedar.cp.dto.rechargegroup.RechargeGroupRelCK;
import com.cedar.cp.dto.rechargegroup.RechargeGroupRelPK;
import com.cedar.cp.dto.report.AllReportsELO;
import com.cedar.cp.dto.report.AllReportsForAdminELO;
import com.cedar.cp.dto.report.AllReportsForUserELO;
import com.cedar.cp.dto.report.ReportCK;
import com.cedar.cp.dto.report.ReportPK;
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
import com.cedar.cp.dto.report.definition.ReportDefCalculatorCK;
import com.cedar.cp.dto.report.definition.ReportDefCalculatorPK;
import com.cedar.cp.dto.report.definition.ReportDefFormCK;
import com.cedar.cp.dto.report.definition.ReportDefFormPK;
import com.cedar.cp.dto.report.definition.ReportDefMappedExcelCK;
import com.cedar.cp.dto.report.definition.ReportDefMappedExcelPK;
import com.cedar.cp.dto.report.definition.ReportDefSummaryCalcCK;
import com.cedar.cp.dto.report.definition.ReportDefSummaryCalcPK;
import com.cedar.cp.dto.report.definition.ReportDefinitionCK;
import com.cedar.cp.dto.report.definition.ReportDefinitionForVisIdELO;
import com.cedar.cp.dto.report.definition.ReportDefinitionPK;
import com.cedar.cp.dto.report.destination.external.AllExternalDestinationDetailsELO;
import com.cedar.cp.dto.report.destination.external.AllExternalDestinationsELO;
import com.cedar.cp.dto.report.destination.external.AllUsersForExternalDestinationIdELO;
import com.cedar.cp.dto.report.destination.external.ExternalDestinationCK;
import com.cedar.cp.dto.report.destination.external.ExternalDestinationPK;
import com.cedar.cp.dto.report.destination.external.ExternalDestinationUsersCK;
import com.cedar.cp.dto.report.destination.external.ExternalDestinationUsersPK;
import com.cedar.cp.dto.report.destination.internal.AllInternalDestinationDetailsELO;
import com.cedar.cp.dto.report.destination.internal.AllInternalDestinationUsersELO;
import com.cedar.cp.dto.report.destination.internal.AllInternalDestinationsELO;
import com.cedar.cp.dto.report.destination.internal.AllUsersForInternalDestinationIdELO;
import com.cedar.cp.dto.report.destination.internal.CheckInternalDestinationUsersELO;
import com.cedar.cp.dto.report.destination.internal.InternalDestinationCK;
import com.cedar.cp.dto.report.destination.internal.InternalDestinationPK;
import com.cedar.cp.dto.report.destination.internal.InternalDestinationUsersCK;
import com.cedar.cp.dto.report.destination.internal.InternalDestinationUsersPK;
import com.cedar.cp.dto.report.distribution.AllDistributionsELO;
import com.cedar.cp.dto.report.distribution.CheckExternalDestinationELO;
import com.cedar.cp.dto.report.distribution.CheckInternalDestinationELO;
import com.cedar.cp.dto.report.distribution.DistributionCK;
import com.cedar.cp.dto.report.distribution.DistributionDetailsForVisIdELO;
import com.cedar.cp.dto.report.distribution.DistributionForVisIdELO;
import com.cedar.cp.dto.report.distribution.DistributionLinkCK;
import com.cedar.cp.dto.report.distribution.DistributionLinkPK;
import com.cedar.cp.dto.report.distribution.DistributionPK;
import com.cedar.cp.dto.report.mappingtemplate.AllReportMappingTemplatesELO;
import com.cedar.cp.dto.report.mappingtemplate.ReportMappingTemplateCK;
import com.cedar.cp.dto.report.mappingtemplate.ReportMappingTemplatePK;
import com.cedar.cp.dto.report.pack.AllReportPacksELO;
import com.cedar.cp.dto.report.pack.CheckReportDefELO;
import com.cedar.cp.dto.report.pack.CheckReportDistributionELO;
import com.cedar.cp.dto.report.pack.ReportDefDistListELO;
import com.cedar.cp.dto.report.pack.ReportPackCK;
import com.cedar.cp.dto.report.pack.ReportPackLinkCK;
import com.cedar.cp.dto.report.pack.ReportPackLinkPK;
import com.cedar.cp.dto.report.pack.ReportPackPK;
import com.cedar.cp.dto.report.task.ReportGroupingCK;
import com.cedar.cp.dto.report.task.ReportGroupingFileCK;
import com.cedar.cp.dto.report.task.ReportGroupingFilePK;
import com.cedar.cp.dto.report.task.ReportGroupingPK;
import com.cedar.cp.dto.report.template.AllReportTemplatesELO;
import com.cedar.cp.dto.report.template.ReportTemplateCK;
import com.cedar.cp.dto.report.template.ReportTemplatePK;
import com.cedar.cp.dto.report.tran.CubePendingTranCK;
import com.cedar.cp.dto.report.tran.CubePendingTranPK;
import com.cedar.cp.dto.report.type.AllReportTypesELO;
import com.cedar.cp.dto.report.type.ReportTypeCK;
import com.cedar.cp.dto.report.type.ReportTypePK;
import com.cedar.cp.dto.report.type.param.AllReportTypeParamsELO;
import com.cedar.cp.dto.report.type.param.AllReportTypeParamsforTypeELO;
import com.cedar.cp.dto.report.type.param.ReportTypeParamCK;
import com.cedar.cp.dto.report.type.param.ReportTypeParamPK;
import com.cedar.cp.dto.reset.AllChallengeQuestionsELO;
import com.cedar.cp.dto.reset.AllQuestionsAndAnswersByUserIDELO;
import com.cedar.cp.dto.reset.AllUserResetLinksELO;
import com.cedar.cp.dto.reset.ChallengeQuestionCK;
import com.cedar.cp.dto.reset.ChallengeQuestionPK;
import com.cedar.cp.dto.reset.LinkByUserIDELO;
import com.cedar.cp.dto.reset.UserResetLinkCK;
import com.cedar.cp.dto.reset.UserResetLinkPK;
import com.cedar.cp.dto.role.AllHiddenRolesELO;
import com.cedar.cp.dto.role.AllRolesELO;
import com.cedar.cp.dto.role.AllRolesForUserELO;
import com.cedar.cp.dto.role.AllSecurityRolesELO;
import com.cedar.cp.dto.role.AllSecurityRolesForRoleELO;
import com.cedar.cp.dto.role.RoleCK;
import com.cedar.cp.dto.role.RolePK;
import com.cedar.cp.dto.role.RoleSecurityCK;
import com.cedar.cp.dto.role.RoleSecurityPK;
import com.cedar.cp.dto.role.RoleSecurityRelCK;
import com.cedar.cp.dto.role.RoleSecurityRelPK;
import com.cedar.cp.dto.systemproperty.AllMailPropsELO;
import com.cedar.cp.dto.systemproperty.AllSystemPropertysELO;
import com.cedar.cp.dto.systemproperty.AllSystemPropertysUncachedELO;
import com.cedar.cp.dto.systemproperty.SystemPropertyCK;
import com.cedar.cp.dto.systemproperty.SystemPropertyELO;
import com.cedar.cp.dto.systemproperty.SystemPropertyPK;
import com.cedar.cp.dto.systemproperty.WebSystemPropertyELO;
import com.cedar.cp.dto.task.AllWebTasksELO;
import com.cedar.cp.dto.task.AllWebTasksForUserELO;
import com.cedar.cp.dto.task.TaskCK;
import com.cedar.cp.dto.task.TaskPK;
import com.cedar.cp.dto.task.WebTasksDetailsELO;
import com.cedar.cp.dto.task.group.AllTaskGroupsELO;
import com.cedar.cp.dto.task.group.TaskGroupCK;
import com.cedar.cp.dto.task.group.TaskGroupPK;
import com.cedar.cp.dto.task.group.TaskGroupRICheckELO;
import com.cedar.cp.dto.task.group.TgRowCK;
import com.cedar.cp.dto.task.group.TgRowPK;
import com.cedar.cp.dto.task.group.TgRowParamCK;
import com.cedar.cp.dto.task.group.TgRowParamPK;
import com.cedar.cp.dto.udeflookup.AllUdefLookupsELO;
import com.cedar.cp.dto.udeflookup.UdefLookupCK;
import com.cedar.cp.dto.udeflookup.UdefLookupColumnDefCK;
import com.cedar.cp.dto.udeflookup.UdefLookupColumnDefPK;
import com.cedar.cp.dto.udeflookup.UdefLookupPK;
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
import com.cedar.cp.dto.user.DataEntryProfileCK;
import com.cedar.cp.dto.user.DataEntryProfileHistoryCK;
import com.cedar.cp.dto.user.DataEntryProfileHistoryPK;
import com.cedar.cp.dto.user.DataEntryProfilePK;
import com.cedar.cp.dto.user.DefaultDataEntryProfileELO;
import com.cedar.cp.dto.user.FinanceSystemUserNameELO;
import com.cedar.cp.dto.user.SecurityStringsForUserELO;
import com.cedar.cp.dto.user.UserCK;
import com.cedar.cp.dto.user.UserMessageAttributesELO;
import com.cedar.cp.dto.user.UserMessageAttributesForIdELO;
import com.cedar.cp.dto.user.UserMessageAttributesForNameELO;
import com.cedar.cp.dto.user.UserPK;
import com.cedar.cp.dto.user.UserPreferenceCK;
import com.cedar.cp.dto.user.UserPreferencePK;
import com.cedar.cp.dto.user.UserPreferencesForUserELO;
import com.cedar.cp.dto.user.UserRoleCK;
import com.cedar.cp.dto.user.UserRolePK;
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
import com.cedar.cp.dto.xmlform.XmlFormCK;
import com.cedar.cp.dto.xmlform.XmlFormPK;
import com.cedar.cp.dto.xmlform.XmlFormUserLinkCK;
import com.cedar.cp.dto.xmlform.XmlFormUserLinkPK;
import com.cedar.cp.dto.xmlform.rebuild.AllBudgetCyclesInRebuildsELO;
import com.cedar.cp.dto.xmlform.rebuild.AllFormRebuildsELO;
import com.cedar.cp.dto.xmlform.rebuild.FormRebuildCK;
import com.cedar.cp.dto.xmlform.rebuild.FormRebuildPK;
import com.cedar.cp.dto.xmlreport.AllPublicXmlReportsELO;
import com.cedar.cp.dto.xmlreport.AllXmlReportsELO;
import com.cedar.cp.dto.xmlreport.AllXmlReportsForUserELO;
import com.cedar.cp.dto.xmlreport.SingleXmlReportELO;
import com.cedar.cp.dto.xmlreport.XmlReportCK;
import com.cedar.cp.dto.xmlreport.XmlReportPK;
import com.cedar.cp.dto.xmlreport.XmlReportsForFolderELO;
import com.cedar.cp.dto.xmlreportfolder.AllPublicXmlReportFoldersELO;
import com.cedar.cp.dto.xmlreportfolder.AllXmlReportFoldersELO;
import com.cedar.cp.dto.xmlreportfolder.AllXmlReportFoldersForUserELO;
import com.cedar.cp.dto.xmlreportfolder.DecendentFoldersELO;
import com.cedar.cp.dto.xmlreportfolder.ReportFolderWithIdELO;
import com.cedar.cp.dto.xmlreportfolder.XmlReportFolderCK;
import com.cedar.cp.dto.xmlreportfolder.XmlReportFolderPK;
import com.cedar.cp.ejb.impl.admin.tidytask.TidyTaskAccessor;
import com.cedar.cp.ejb.impl.admin.tidytask.TidyTaskDAO;
import com.cedar.cp.ejb.impl.admin.tidytask.TidyTaskLinkDAO;
import com.cedar.cp.ejb.impl.authenticationpolicy.AuthenticationPolicyAccessor;
import com.cedar.cp.ejb.impl.authenticationpolicy.AuthenticationPolicyDAO;
import com.cedar.cp.ejb.impl.base.AbstractSession;
import com.cedar.cp.ejb.impl.budgetinstruction.BudgetInstructionAccessor;
import com.cedar.cp.ejb.impl.budgetinstruction.BudgetInstructionAssignmentDAO;
import com.cedar.cp.ejb.impl.budgetinstruction.BudgetInstructionDAO;
import com.cedar.cp.ejb.impl.cm.ChangeMgmtAccessor;
import com.cedar.cp.ejb.impl.cm.ChangeMgmtDAO;
import com.cedar.cp.ejb.impl.cubeformula.CubeFormulaDAO;
import com.cedar.cp.ejb.impl.cubeformula.CubeFormulaPackageDAO;
import com.cedar.cp.ejb.impl.cubeformula.FormulaDeploymentDtDAO;
import com.cedar.cp.ejb.impl.cubeformula.FormulaDeploymentEntryDAO;
import com.cedar.cp.ejb.impl.cubeformula.FormulaDeploymentLineDAO;
import com.cedar.cp.ejb.impl.currency.CurrencyAccessor;
import com.cedar.cp.ejb.impl.currency.CurrencyDAO;
import com.cedar.cp.ejb.impl.datatype.DataTypeAccessor;
import com.cedar.cp.ejb.impl.datatype.DataTypeDAO;
import com.cedar.cp.ejb.impl.datatype.DataTypeRelDAO;
import com.cedar.cp.ejb.impl.defaultuserpref.DefaultUserPrefDAO;
import com.cedar.cp.ejb.impl.dimension.AugHierarchyElementDAO;
import com.cedar.cp.ejb.impl.dimension.CalendarSpecDAO;
import com.cedar.cp.ejb.impl.dimension.CalendarYearSpecDAO;
import com.cedar.cp.ejb.impl.dimension.DimensionAccessor;
import com.cedar.cp.ejb.impl.dimension.DimensionDAO;
import com.cedar.cp.ejb.impl.dimension.DimensionElementDAO;
import com.cedar.cp.ejb.impl.dimension.HierarchyDAO;
import com.cedar.cp.ejb.impl.dimension.HierarchyElementDAO;
import com.cedar.cp.ejb.impl.dimension.HierarchyElementFeedDAO;
import com.cedar.cp.ejb.impl.dimension.SecurityRangeDAO;
import com.cedar.cp.ejb.impl.dimension.SecurityRangeRowDAO;
import com.cedar.cp.ejb.impl.dimension.StructureElementAccessor;
import com.cedar.cp.ejb.impl.dimension.StructureElementDAO;
import com.cedar.cp.ejb.impl.extendedattachment.ExtendedAttachmentAccessor;
import com.cedar.cp.ejb.impl.extendedattachment.ExtendedAttachmentDAO;
import com.cedar.cp.ejb.impl.extsys.ExtSysCalElementDAO;
import com.cedar.cp.ejb.impl.extsys.ExtSysCalendarYearDAO;
import com.cedar.cp.ejb.impl.extsys.ExtSysCompanyDAO;
import com.cedar.cp.ejb.impl.extsys.ExtSysCurrencyDAO;
import com.cedar.cp.ejb.impl.extsys.ExtSysDimElementDAO;
import com.cedar.cp.ejb.impl.extsys.ExtSysDimensionDAO;
import com.cedar.cp.ejb.impl.extsys.ExtSysHierElemFeedDAO;
import com.cedar.cp.ejb.impl.extsys.ExtSysHierElementDAO;
import com.cedar.cp.ejb.impl.extsys.ExtSysHierarchyDAO;
import com.cedar.cp.ejb.impl.extsys.ExtSysLedgerDAO;
import com.cedar.cp.ejb.impl.extsys.ExtSysPropertyDAO;
import com.cedar.cp.ejb.impl.extsys.ExtSysValueTypeDAO;
import com.cedar.cp.ejb.impl.extsys.ExternalSystemAccessor;
import com.cedar.cp.ejb.impl.extsys.ExternalSystemDAO;
import com.cedar.cp.ejb.impl.formnotes.FormNotesAccessor;
import com.cedar.cp.ejb.impl.formnotes.FormNotesDAO;
import com.cedar.cp.ejb.impl.impexp.ImpExpHdrAccessor;
import com.cedar.cp.ejb.impl.impexp.ImpExpHdrDAO;
import com.cedar.cp.ejb.impl.impexp.ImpExpRowDAO;
import com.cedar.cp.ejb.impl.importtask.ImportTaskAccessor;
//import com.cedar.cp.ejb.impl.logonhistory.LogonHistoryAccessor;
//import com.cedar.cp.ejb.impl.logonhistory.LogonHistoryDAO;
import com.cedar.cp.ejb.impl.masterquestion.MasterQuestionAccessor;
import com.cedar.cp.ejb.impl.masterquestion.MasterQuestionDAO;
import com.cedar.cp.ejb.impl.message.MessageAccessor;
import com.cedar.cp.ejb.impl.message.MessageAttatchDAO;
import com.cedar.cp.ejb.impl.message.MessageDAO;
import com.cedar.cp.ejb.impl.message.MessageUserDAO;
import com.cedar.cp.ejb.impl.model.BudgetCycleDAO;
import com.cedar.cp.ejb.impl.model.BudgetCycleLinkDAO;
import com.cedar.cp.ejb.impl.model.BudgetStateDAO;
import com.cedar.cp.ejb.impl.model.BudgetStateHistoryDAO;
import com.cedar.cp.ejb.impl.model.BudgetUserDAO;
import com.cedar.cp.ejb.impl.model.CellCalcAssocDAO;
import com.cedar.cp.ejb.impl.model.CellCalcDAO;
import com.cedar.cp.ejb.impl.model.FinanceCubeDAO;
import com.cedar.cp.ejb.impl.model.FinanceCubeDataTypeDAO;
import com.cedar.cp.ejb.impl.model.LevelDateDAO;
import com.cedar.cp.ejb.impl.model.ModelAccessor;
import com.cedar.cp.ejb.impl.model.ModelDAO;
import com.cedar.cp.ejb.impl.model.ModelDimensionRelDAO;
import com.cedar.cp.ejb.impl.model.ModelPropertyDAO;
import com.cedar.cp.ejb.impl.model.RollUpRuleDAO;
import com.cedar.cp.ejb.impl.model.SecurityAccRngRelDAO;
import com.cedar.cp.ejb.impl.model.SecurityAccessDefDAO;
import com.cedar.cp.ejb.impl.model.SecurityGroupDAO;
import com.cedar.cp.ejb.impl.model.SecurityGroupUserRelDAO;
import com.cedar.cp.ejb.impl.model.act.BudgetActivityDAO;
import com.cedar.cp.ejb.impl.model.act.BudgetActivityLinkDAO;
import com.cedar.cp.ejb.impl.model.amm.AmmDataTypeDAO;
import com.cedar.cp.ejb.impl.model.amm.AmmDimensionDAO;
import com.cedar.cp.ejb.impl.model.amm.AmmDimensionElementDAO;
import com.cedar.cp.ejb.impl.model.amm.AmmFinanceCubeDAO;
import com.cedar.cp.ejb.impl.model.amm.AmmModelAccessor;
import com.cedar.cp.ejb.impl.model.amm.AmmModelDAO;
import com.cedar.cp.ejb.impl.model.amm.AmmSrcStructureElementDAO;
import com.cedar.cp.ejb.impl.model.budgetlimit.BudgetLimitDAO;
import com.cedar.cp.ejb.impl.model.cc.CcDeploymentDAO;
import com.cedar.cp.ejb.impl.model.cc.CcDeploymentDataTypeDAO;
import com.cedar.cp.ejb.impl.model.cc.CcDeploymentEntryDAO;
import com.cedar.cp.ejb.impl.model.cc.CcDeploymentLineDAO;
import com.cedar.cp.ejb.impl.model.cc.CcMappingEntryDAO;
import com.cedar.cp.ejb.impl.model.cc.CcMappingLineDAO;
import com.cedar.cp.ejb.impl.model.cc.imp.dyn.ImportGridCellDAO;
import com.cedar.cp.ejb.impl.model.cc.imp.dyn.ImportGridDAO;
import com.cedar.cp.ejb.impl.model.globalmapping2.GlobalMappedModel2Accessor;
import com.cedar.cp.ejb.impl.model.globalmapping2.GlobalMappedModel2DAO;
import com.cedar.cp.ejb.impl.model.mapping.MappedCalendarElementDAO;
import com.cedar.cp.ejb.impl.model.mapping.MappedCalendarYearDAO;
import com.cedar.cp.ejb.impl.model.mapping.MappedDataTypeDAO;
import com.cedar.cp.ejb.impl.model.mapping.MappedDimensionDAO;
import com.cedar.cp.ejb.impl.model.mapping.MappedDimensionElementDAO;
import com.cedar.cp.ejb.impl.model.mapping.MappedFinanceCubeDAO;
import com.cedar.cp.ejb.impl.model.mapping.MappedHierarchyDAO;
import com.cedar.cp.ejb.impl.model.mapping.MappedModelAccessor;
import com.cedar.cp.ejb.impl.model.mapping.MappedModelDAO;
import com.cedar.cp.ejb.impl.model.ra.ResponsibilityAreaDAO;
import com.cedar.cp.ejb.impl.model.recharge.RechargeCellsDAO;
import com.cedar.cp.ejb.impl.model.recharge.RechargeDAO;
import com.cedar.cp.ejb.impl.model.recharge.RechargeGroupAccessor;
import com.cedar.cp.ejb.impl.model.recharge.RechargeGroupDAO;
import com.cedar.cp.ejb.impl.model.udwp.WeightingDeploymentDAO;
import com.cedar.cp.ejb.impl.model.udwp.WeightingDeploymentLineDAO;
import com.cedar.cp.ejb.impl.model.udwp.WeightingProfileDAO;
import com.cedar.cp.ejb.impl.model.udwp.WeightingProfileLineDAO;
import com.cedar.cp.ejb.impl.model.virement.VirementAccountDAO;
import com.cedar.cp.ejb.impl.model.virement.VirementAuthPointDAO;
import com.cedar.cp.ejb.impl.model.virement.VirementAuthPointLinkDAO;
import com.cedar.cp.ejb.impl.model.virement.VirementAuthorisersDAO;
import com.cedar.cp.ejb.impl.model.virement.VirementCategoryDAO;
import com.cedar.cp.ejb.impl.model.virement.VirementLineSpreadDAO;
import com.cedar.cp.ejb.impl.model.virement.VirementLocationDAO;
import com.cedar.cp.ejb.impl.model.virement.VirementRequestDAO;
import com.cedar.cp.ejb.impl.model.virement.VirementRequestGroupDAO;
import com.cedar.cp.ejb.impl.model.virement.VirementRequestLineDAO;
import com.cedar.cp.ejb.impl.passwordhistory.PasswordHistoryAccessor;
import com.cedar.cp.ejb.impl.passwordhistory.PasswordHistoryDAO;
import com.cedar.cp.ejb.impl.perftest.PerfTestAccessor;
import com.cedar.cp.ejb.impl.perftest.PerfTestDAO;
import com.cedar.cp.ejb.impl.perftestrun.PerfTestRunAccessor;
import com.cedar.cp.ejb.impl.perftestrun.PerfTestRunDAO;
import com.cedar.cp.ejb.impl.perftestrun.PerfTestRunResultDAO;
import com.cedar.cp.ejb.impl.recalculate.RecalculateBatchTaskAccessor;
import com.cedar.cp.ejb.impl.rechargegroup.RechargeGroupRelDAO;
import com.cedar.cp.ejb.impl.report.ReportAccessor;
import com.cedar.cp.ejb.impl.report.ReportDAO;
import com.cedar.cp.ejb.impl.report.definition.ReportDefCalculatorDAO;
import com.cedar.cp.ejb.impl.report.definition.ReportDefFormDAO;
import com.cedar.cp.ejb.impl.report.definition.ReportDefMappedExcelDAO;
import com.cedar.cp.ejb.impl.report.definition.ReportDefSummaryCalcDAO;
import com.cedar.cp.ejb.impl.report.definition.ReportDefinitionAccessor;
import com.cedar.cp.ejb.impl.report.definition.ReportDefinitionDAO;
import com.cedar.cp.ejb.impl.report.destination.external.ExternalDestinationAccessor;
import com.cedar.cp.ejb.impl.report.destination.external.ExternalDestinationDAO;
import com.cedar.cp.ejb.impl.report.destination.external.ExternalDestinationUsersDAO;
import com.cedar.cp.ejb.impl.report.destination.internal.InternalDestinationAccessor;
import com.cedar.cp.ejb.impl.report.destination.internal.InternalDestinationDAO;
import com.cedar.cp.ejb.impl.report.destination.internal.InternalDestinationUsersDAO;
import com.cedar.cp.ejb.impl.report.distribution.DistributionAccessor;
import com.cedar.cp.ejb.impl.report.distribution.DistributionDAO;
import com.cedar.cp.ejb.impl.report.distribution.DistributionLinkDAO;
import com.cedar.cp.ejb.impl.report.mappingtemplate.ReportMappingTemplateAccessor;
import com.cedar.cp.ejb.impl.report.mappingtemplate.ReportMappingTemplateDAO;
import com.cedar.cp.ejb.impl.report.pack.ReportPackAccessor;
import com.cedar.cp.ejb.impl.report.pack.ReportPackDAO;
import com.cedar.cp.ejb.impl.report.pack.ReportPackLinkDAO;
import com.cedar.cp.ejb.impl.report.task.ReportGroupingDAO;
import com.cedar.cp.ejb.impl.report.task.ReportGroupingFileDAO;
import com.cedar.cp.ejb.impl.report.template.ReportTemplateAccessor;
import com.cedar.cp.ejb.impl.report.template.ReportTemplateDAO;
import com.cedar.cp.ejb.impl.report.tran.CubePendingTranDAO;
import com.cedar.cp.ejb.impl.report.type.ReportTypeAccessor;
import com.cedar.cp.ejb.impl.report.type.ReportTypeDAO;
import com.cedar.cp.ejb.impl.report.type.param.ReportTypeParamDAO;
import com.cedar.cp.ejb.impl.reset.ChallengeQuestionDAO;
import com.cedar.cp.ejb.impl.reset.UserResetLinkDAO;
import com.cedar.cp.ejb.impl.role.RoleAccessor;
import com.cedar.cp.ejb.impl.role.RoleDAO;
import com.cedar.cp.ejb.impl.role.RoleSecurityAccessor;
import com.cedar.cp.ejb.impl.role.RoleSecurityDAO;
import com.cedar.cp.ejb.impl.role.RoleSecurityRelDAO;
import com.cedar.cp.ejb.impl.systemproperty.SystemPropertyAccessor;
import com.cedar.cp.ejb.impl.systemproperty.SystemPropertyDAO;
import com.cedar.cp.ejb.impl.task.TaskAccessor;
import com.cedar.cp.ejb.impl.task.TaskDAO;
import com.cedar.cp.ejb.impl.task.group.TaskGroupAccessor;
import com.cedar.cp.ejb.impl.task.group.TaskGroupDAO;
import com.cedar.cp.ejb.impl.task.group.TgRowDAO;
import com.cedar.cp.ejb.impl.task.group.TgRowParamDAO;
import com.cedar.cp.ejb.impl.udeflookup.UdefLookupAccessor;
import com.cedar.cp.ejb.impl.udeflookup.UdefLookupColumnDefDAO;
import com.cedar.cp.ejb.impl.udeflookup.UdefLookupDAO;
import com.cedar.cp.ejb.impl.user.DataEntryProfileDAO;
import com.cedar.cp.ejb.impl.user.DataEntryProfileHistoryDAO;
import com.cedar.cp.ejb.impl.user.UserAccessor;
import com.cedar.cp.ejb.impl.user.UserDAO;
import com.cedar.cp.ejb.impl.user.UserPreferenceDAO;
import com.cedar.cp.ejb.impl.user.UserRoleDAO;
import com.cedar.cp.ejb.impl.user.loggedinusers.CPContextCache;
import com.cedar.cp.ejb.impl.xmlform.XmlFormAccessor;
import com.cedar.cp.ejb.impl.xmlform.XmlFormDAO;
import com.cedar.cp.ejb.impl.xmlform.XmlFormUserLinkDAO;
import com.cedar.cp.ejb.impl.xmlform.rebuild.FormRebuildDAO;
import com.cedar.cp.ejb.impl.xmlreport.XmlReportAccessor;
import com.cedar.cp.ejb.impl.xmlreport.XmlReportDAO;
import com.cedar.cp.ejb.impl.xmlreportfolder.XmlReportFolderAccessor;
import com.cedar.cp.ejb.impl.xmlreportfolder.XmlReportFolderDAO;
import com.cedar.cp.util.Log;
import com.cedar.cp.util.Timer;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import javax.ejb.EJBException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class ListSEJB extends AbstractSession implements SessionBean {

    private transient Log _log = new Log(this.getClass());
    private SessionContext mSessionContext;
    ModelAccessor modelAccessor = null;
    public ListSEJB(){
    	try {
    		modelAccessor = new ModelAccessor(new InitialContext());
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

    public UserMessageAttributesForIdELO getUserMessageAttributesForMultiplIds(String[] params) {
        UserDAO dao = new UserDAO();
        return dao.getUserMessageAttributesForMultipleIds(params);
    }

    public EntityList getList(String tableName) throws EJBException {
        EntityList result = null;

        try {
            result = this.getTableList(tableName);
            return result;
        } catch (Exception var4) {
            throw new EJBException(var4.getMessage(), var4);
        }
    }

    private String getListInfo(EntityList el) {
        StringBuffer sb = new StringBuffer();
        if (el != null) {
            sb.append("rows=");
            sb.append(String.valueOf(el.getNumRows()));
            sb.append(" cols=");

            for (int i = 0; i < el.getHeadings().length; ++i) {
                sb.append('|' + el.getHeadings()[i]);
            }

            if (sb.length() > 0) {
                sb.append('|');
            }
        }

        return sb.toString();
    }

    private EntityList getTableList(String tableName) throws Exception {
        if (tableName.equals("Model")) {
            return (new ModelAccessor(new InitialContext())).getAllModels();
        } else if (tableName.equals("FinanceCube")) {
            return (new ModelAccessor(new InitialContext())).getAllFinanceCubes();
        } else if (tableName.equals("BudgetCycle")) {
            return (new ModelAccessor(new InitialContext())).getAllBudgetCycles();
        } else if (tableName.equals("ResponsibilityArea")) {
            return (new ModelAccessor(new InitialContext())).getAllResponsibilityAreas();
        } else if (tableName.equals("BudgetUser")) {
            return (new ModelAccessor(new InitialContext())).getAllBudgetUsers();
        } else if (tableName.equals("DataType")) {
            return (new DataTypeAccessor(new InitialContext())).getAllDataTypes();
        } else if (tableName.equals("Currency")) {
            return (new CurrencyAccessor(new InitialContext())).getAllCurrencys();
        } else if (tableName.equals("Dimension")) {
            return (new DimensionAccessor(new InitialContext())).getAllDimensions();
        } else if (tableName.equals("Hierarchy")) {
            return (new DimensionAccessor(new InitialContext())).getAllHierarchys();
        } else if (tableName.equals("User")) {
            return (new UserAccessor(new InitialContext())).getAllUsers();
        } else if (tableName.equals("Role")) {
            return (new RoleAccessor(new InitialContext())).getAllRoles();
        } else if (tableName.equals("BudgetInstruction")) {
            return (new BudgetInstructionAccessor(new InitialContext())).getAllBudgetInstructions();
        } else if (tableName.equals("SystemProperty")) {
            return (new SystemPropertyAccessor(new InitialContext())).getAllSystemPropertys();
        } else if (tableName.equals("XmlForm")) {
            return (new XmlFormAccessor(new InitialContext())).getAllXmlForms();
        } else if (tableName.equals("XmlReport")) {
            return (new XmlReportAccessor(new InitialContext())).getAllXmlReports();
        } else if (tableName.equals("XmlReportFolder")) {
            return (new XmlReportFolderAccessor(new InitialContext())).getAllXmlReportFolders();
        } else if (tableName.equals("DataEntryProfile")) {
            return (new UserAccessor(new InitialContext())).getAllDataEntryProfiles();
        } else if (tableName.equals("UdefLookup")) {
            return (new UdefLookupAccessor(new InitialContext())).getAllUdefLookups();
        } else if (tableName.equals("SecurityRange")) {
            return (new DimensionAccessor(new InitialContext())).getAllSecurityRanges();
        } else if (tableName.equals("SecurityAccessDef")) {
            return (new ModelAccessor(new InitialContext())).getAllSecurityAccessDefs();
        } else if (tableName.equals("SecurityGroup")) {
            return (new ModelAccessor(new InitialContext())).getAllSecurityGroups();
        } else if (tableName.equals("CcDeployment")) {
            return (new ModelAccessor(new InitialContext())).getAllCcDeployments();
        } else if (tableName.equals("CellCalc")) {
            return (new ModelAccessor(new InitialContext())).getAllCellCalcs();
        } else if (tableName.equals("ChangeMgmt")) {
            return (new ChangeMgmtAccessor(new InitialContext())).getAllChangeMgmts();
        } else if (tableName.equals("ImpExpHdr")) {
            return (new ImpExpHdrAccessor(new InitialContext())).getAllImpExpHdrs();
        } else if (tableName.equals("Report")) {
            return (new ReportAccessor(new InitialContext())).getAllReports();
        } else if (tableName.equals("VirementCategory")) {
            return (new ModelAccessor(new InitialContext())).getAllVirementCategorys();
        } else if (tableName.equals("BudgetLimit")) {
            return (new ModelAccessor(new InitialContext())).getAllBudgetLimits();
        } else if (tableName.equals("PerfTest")) {
            return (new PerfTestAccessor(new InitialContext())).getAllPerfTests();
        } else if (tableName.equals("PerfTestRun")) {
            return (new PerfTestRunAccessor(new InitialContext())).getAllPerfTestRuns();
        } else if (tableName.equals("PerfTestRunResult")) {
            return (new PerfTestRunAccessor(new InitialContext())).getAllPerfTestRunResults();
        } else if (tableName.equals("Message")) {
            return (new MessageAccessor(new InitialContext())).getAllMessages();
        } else if (tableName.equals("Recharge")) {
            return (new ModelAccessor(new InitialContext())).getAllRecharges();
        } else if (tableName.equals("RechargeGroup")) {
            return (new RechargeGroupAccessor(new InitialContext())).getAllRechargeGroups();
        } else if (tableName.equals("VirementRequest")) {
            return (new ModelAccessor(new InitialContext())).getAllVirementRequests();
        } else if (tableName.equals("ReportType")) {
            return (new ReportTypeAccessor(new InitialContext())).getAllReportTypes();
        } else if (tableName.equals("ReportDefinition")) {
            return (new ReportDefinitionAccessor(new InitialContext())).getAllReportDefinitions();
        } else if (tableName.equals("ReportTemplate")) {
            return (new ReportTemplateAccessor(new InitialContext())).getAllReportTemplates();
        } else if (tableName.equals("ReportMappingTemplate")) {
            return (new ReportMappingTemplateAccessor(new InitialContext())).getAllReportMappingTemplates();
        } else if (tableName.equals("ExternalDestination")) {
            return (new ExternalDestinationAccessor(new InitialContext())).getAllExternalDestinations();
        } else if (tableName.equals("InternalDestination")) {
            return (new InternalDestinationAccessor(new InitialContext())).getAllInternalDestinations();
        } else if (tableName.equals("Distribution")) {
            return (new DistributionAccessor(new InitialContext())).getAllDistributions();
        } else if (tableName.equals("ReportPack")) {
            return (new ReportPackAccessor(new InitialContext())).getAllReportPacks();
        } else if (tableName.equals("WeightingProfile")) {
            return (new ModelAccessor(new InitialContext())).getAllWeightingProfiles();
        } else if (tableName.equals("WeightingDeployment")) {
            return (new ModelAccessor(new InitialContext())).getAllWeightingDeployments();
        } else if (tableName.equals("TidyTask")) {
            return (new TidyTaskAccessor(new InitialContext())).getAllTidyTasks();
        } else if (tableName.equals("ImportTask")) {
            return (new ImportTaskAccessor(new InitialContext())).getAllImportTasks();
        } else if (tableName.equals("RecalculateBatchTask")) {
            return (new RecalculateBatchTaskAccessor(new InitialContext())).getAllRecalculateBatchTasks();
        } else if (tableName.equals("MappedModel")) {
            return (new MappedModelAccessor(new InitialContext())).getAllMappedModels();
        } else if (tableName.equals("GlobalMappedModel2")) {
            return (new GlobalMappedModel2Accessor(new InitialContext())).getAllGlobalMappedModels2();
        } else if (tableName.equals("ExtendedAttachment")) {
            return (new ExtendedAttachmentAccessor(new InitialContext())).getAllExtendedAttachments();
        } else if (tableName.equals("ExternalSystem")) {
            return (new ExternalSystemAccessor(new InitialContext())).getAllExternalSystems();
        } else if (tableName.equals("AmmModel")) {
            return (new AmmModelAccessor(new InitialContext())).getAllAmmModels();
        } else if (tableName.equals("TaskGroup")) {
            return (new TaskGroupAccessor(new InitialContext())).getAllTaskGroups();
        } else if (tableName.equals("AuthenticationPolicy")) {
            return (new AuthenticationPolicyAccessor(new InitialContext())).getAllAuthenticationPolicys();
        } else if (tableName.equals("LogonHistory")) {
        	return null;//arnold
//            return (new LogonHistoryAccessor(new InitialContext())).getAllLogonHistorys();
        } else if (tableName.equals("PasswordHistory")) {
            return (new PasswordHistoryAccessor(new InitialContext())).getAllPasswordHistorys();
        } else if (tableName.equals("FormRebuild")) {
            return (new ModelAccessor(new InitialContext())).getAllFormRebuilds();
        } else if (tableName.equals("CubeFormula")) {
            return (new ModelAccessor(new InitialContext())).getAllCubeFormulas();
        } else if (tableName.equals("MasterQuestion")) {
            return new MasterQuestionAccessor(new InitialContext()).getAllMasterQuestions();
        } else if (tableName.equals("ChallengeQuestion")) {
            return new UserAccessor(new InitialContext()).getAllChallengeQuestions();
        } else if (tableName.equals("UserResetLink")) {
            return new UserAccessor(new InitialContext()).getAllUserResetLinks();
        } else {
            throw new IllegalArgumentException("unexpected table name: " + tableName);
        }
    }

    /*      */public EntityRef getEntityRef(Object pk) throws EJBException
    /*      */{
        /* 597 */if ((pk instanceof CompositeKey))
            /* 598 */return getEntityRef(((CompositeKey) pk).getPK());
        /* 599 */if ((pk instanceof EntityRef))
            /* 600 */return getEntityRef(((EntityRef) pk).getPrimaryKey());
        /* 601 */if ((pk instanceof String))
        /*      */{
            /* 603 */if (((String) pk).startsWith("ModelCK|"))
                /* 604 */return getEntityRef(ModelCK.getKeyFromTokens((String) pk));
            /* 605 */if (((String) pk).startsWith("ModelPK|"))
                /* 606 */return getEntityRef(ModelPK.getKeyFromTokens((String) pk));
            /* 607 */if (((String) pk).startsWith("ModelPropertyCK|"))
                /* 608 */return getEntityRef(ModelPropertyCK.getKeyFromTokens((String) pk));
            /* 609 */if (((String) pk).startsWith("ModelPropertyPK|"))
                /* 610 */return getEntityRef(ModelPropertyPK.getKeyFromTokens((String) pk));
            /* 611 */if (((String) pk).startsWith("SecurityGroupUserRelCK|"))
                /* 612 */return getEntityRef(SecurityGroupUserRelCK.getKeyFromTokens((String) pk));
            /* 613 */if (((String) pk).startsWith("SecurityGroupUserRelPK|"))
                /* 614 */return getEntityRef(SecurityGroupUserRelPK.getKeyFromTokens((String) pk));
            /* 615 */if (((String) pk).startsWith("ModelDimensionRelCK|"))
                /* 616 */return getEntityRef(ModelDimensionRelCK.getKeyFromTokens((String) pk));
            /* 617 */if (((String) pk).startsWith("ModelDimensionRelPK|"))
                /* 618 */return getEntityRef(ModelDimensionRelPK.getKeyFromTokens((String) pk));
            /* 619 */if (((String) pk).startsWith("FinanceCubeCK|"))
                /* 620 */return getEntityRef(FinanceCubeCK.getKeyFromTokens((String) pk));
            /* 621 */if (((String) pk).startsWith("FinanceCubePK|"))
                /* 622 */return getEntityRef(FinanceCubePK.getKeyFromTokens((String) pk));
            /* 623 */if (((String) pk).startsWith("RollUpRuleCK|"))
                /* 624 */return getEntityRef(RollUpRuleCK.getKeyFromTokens((String) pk));
            /* 625 */if (((String) pk).startsWith("RollUpRulePK|"))
                /* 626 */return getEntityRef(RollUpRulePK.getKeyFromTokens((String) pk));
            /* 627 */if (((String) pk).startsWith("FinanceCubeDataTypeCK|"))
                /* 628 */return getEntityRef(FinanceCubeDataTypeCK.getKeyFromTokens((String) pk));
            /* 629 */if (((String) pk).startsWith("FinanceCubeDataTypePK|"))
                /* 630 */return getEntityRef(FinanceCubeDataTypePK.getKeyFromTokens((String) pk));
            /* 631 */if (((String) pk).startsWith("BudgetCycleCK|"))
                /* 632 */return getEntityRef(BudgetCycleCK.getKeyFromTokens((String) pk));
            /* 633 */if (((String) pk).startsWith("BudgetCyclePK|"))
                /* 634 */return getEntityRef(BudgetCyclePK.getKeyFromTokens((String) pk));
            /* 635 */if (((String) pk).startsWith("LevelDateCK|"))
                /* 636 */return getEntityRef(LevelDateCK.getKeyFromTokens((String) pk));
            /* 637 */if (((String) pk).startsWith("LevelDatePK|"))
                /* 638 */return getEntityRef(LevelDatePK.getKeyFromTokens((String) pk));
            /* 639 */if (((String) pk).startsWith("BudgetStateCK|"))
                /* 640 */return getEntityRef(BudgetStateCK.getKeyFromTokens((String) pk));
            /* 641 */if (((String) pk).startsWith("BudgetStatePK|"))
                /* 642 */return getEntityRef(BudgetStatePK.getKeyFromTokens((String) pk));
            /* 643 */if (((String) pk).startsWith("BudgetStateHistoryCK|"))
                /* 644 */return getEntityRef(BudgetStateHistoryCK.getKeyFromTokens((String) pk));
            /* 645 */if (((String) pk).startsWith("BudgetStateHistoryPK|"))
                /* 646 */return getEntityRef(BudgetStateHistoryPK.getKeyFromTokens((String) pk));
            /* 647 */if (((String) pk).startsWith("ResponsibilityAreaCK|"))
                /* 648 */return getEntityRef(ResponsibilityAreaCK.getKeyFromTokens((String) pk));
            /* 649 */if (((String) pk).startsWith("ResponsibilityAreaPK|"))
                /* 650 */return getEntityRef(ResponsibilityAreaPK.getKeyFromTokens((String) pk));
            /* 651 */if (((String) pk).startsWith("BudgetUserCK|"))
                /* 652 */return getEntityRef(BudgetUserCK.getKeyFromTokens((String) pk));
            /* 653 */if (((String) pk).startsWith("BudgetUserPK|"))
                /* 654 */return getEntityRef(BudgetUserPK.getKeyFromTokens((String) pk));
            /* 655 */if (((String) pk).startsWith("DataTypeCK|"))
                /* 656 */return getEntityRef(DataTypeCK.getKeyFromTokens((String) pk));
            /* 657 */if (((String) pk).startsWith("DataTypePK|"))
                /* 658 */return getEntityRef(DataTypePK.getKeyFromTokens((String) pk));
            /* 659 */if (((String) pk).startsWith("DataTypeRelCK|"))
                /* 660 */return getEntityRef(DataTypeRelCK.getKeyFromTokens((String) pk));
            /* 661 */if (((String) pk).startsWith("DataTypeRelPK|"))
                /* 662 */return getEntityRef(DataTypeRelPK.getKeyFromTokens((String) pk));
            /* 663 */if (((String) pk).startsWith("CurrencyCK|"))
                /* 664 */return getEntityRef(CurrencyCK.getKeyFromTokens((String) pk));
            /* 665 */if (((String) pk).startsWith("CurrencyPK|"))
                /* 666 */return getEntityRef(CurrencyPK.getKeyFromTokens((String) pk));
            /* 667 */if (((String) pk).startsWith("StructureElementCK|"))
                /* 668 */return getEntityRef(StructureElementCK.getKeyFromTokens((String) pk));
            /* 669 */if (((String) pk).startsWith("StructureElementPK|"))
                /* 670 */return getEntityRef(StructureElementPK.getKeyFromTokens((String) pk));
            /* 671 */if (((String) pk).startsWith("DimensionCK|"))
                /* 672 */return getEntityRef(DimensionCK.getKeyFromTokens((String) pk));
            /* 673 */if (((String) pk).startsWith("DimensionPK|"))
                /* 674 */return getEntityRef(DimensionPK.getKeyFromTokens((String) pk));
            /* 675 */if (((String) pk).startsWith("DimensionElementCK|"))
                /* 676 */return getEntityRef(DimensionElementCK.getKeyFromTokens((String) pk));
            /* 677 */if (((String) pk).startsWith("DimensionElementPK|"))
                /* 678 */return getEntityRef(DimensionElementPK.getKeyFromTokens((String) pk));
            /* 679 */if (((String) pk).startsWith("CalendarSpecCK|"))
                /* 680 */return getEntityRef(CalendarSpecCK.getKeyFromTokens((String) pk));
            /* 681 */if (((String) pk).startsWith("CalendarSpecPK|"))
                /* 682 */return getEntityRef(CalendarSpecPK.getKeyFromTokens((String) pk));
            /* 683 */if (((String) pk).startsWith("CalendarYearSpecCK|"))
                /* 684 */return getEntityRef(CalendarYearSpecCK.getKeyFromTokens((String) pk));
            /* 685 */if (((String) pk).startsWith("CalendarYearSpecPK|"))
                /* 686 */return getEntityRef(CalendarYearSpecPK.getKeyFromTokens((String) pk));
            /* 687 */if (((String) pk).startsWith("HierarchyCK|"))
                /* 688 */return getEntityRef(HierarchyCK.getKeyFromTokens((String) pk));
            /* 689 */if (((String) pk).startsWith("HierarchyPK|"))
                /* 690 */return getEntityRef(HierarchyPK.getKeyFromTokens((String) pk));
            /* 691 */if (((String) pk).startsWith("HierarchyElementCK|"))
                /* 692 */return getEntityRef(HierarchyElementCK.getKeyFromTokens((String) pk));
            /* 693 */if (((String) pk).startsWith("HierarchyElementPK|"))
                /* 694 */return getEntityRef(HierarchyElementPK.getKeyFromTokens((String) pk));
            /* 695 */if (((String) pk).startsWith("AugHierarchyElementCK|"))
                /* 696 */return getEntityRef(AugHierarchyElementCK.getKeyFromTokens((String) pk));
            /* 697 */if (((String) pk).startsWith("AugHierarchyElementPK|"))
                /* 698 */return getEntityRef(AugHierarchyElementPK.getKeyFromTokens((String) pk));
            /* 699 */if (((String) pk).startsWith("HierarchyElementFeedCK|"))
                /* 700 */return getEntityRef(HierarchyElementFeedCK.getKeyFromTokens((String) pk));
            /* 701 */if (((String) pk).startsWith("HierarchyElementFeedPK|"))
                /* 702 */return getEntityRef(HierarchyElementFeedPK.getKeyFromTokens((String) pk));
            /* 703 */if (((String) pk).startsWith("DefaultUserPrefCK|"))
                /* 704 */return getEntityRef(DefaultUserPrefCK.getKeyFromTokens((String) pk));
            /* 705 */if (((String) pk).startsWith("DefaultUserPrefPK|"))
                /* 706 */return getEntityRef(DefaultUserPrefPK.getKeyFromTokens((String) pk));
            /* 707 */if (((String) pk).startsWith("UserCK|"))
                /* 708 */return getEntityRef(UserCK.getKeyFromTokens((String) pk));
            /* 709 */if (((String) pk).startsWith("UserPK|"))
                /* 710 */return getEntityRef(UserPK.getKeyFromTokens((String) pk));
            /* 711 */if (((String) pk).startsWith("UserRoleCK|"))
                /* 712 */return getEntityRef(UserRoleCK.getKeyFromTokens((String) pk));
            /* 713 */if (((String) pk).startsWith("UserRolePK|"))
                /* 714 */return getEntityRef(UserRolePK.getKeyFromTokens((String) pk));
            /* 715 */if (((String) pk).startsWith("RoleCK|"))
                /* 716 */return getEntityRef(RoleCK.getKeyFromTokens((String) pk));
            /* 717 */if (((String) pk).startsWith("RolePK|"))
                /* 718 */return getEntityRef(RolePK.getKeyFromTokens((String) pk));
            /* 719 */if (((String) pk).startsWith("RoleSecurityRelCK|"))
                /* 720 */return getEntityRef(RoleSecurityRelCK.getKeyFromTokens((String) pk));
            /* 721 */if (((String) pk).startsWith("RoleSecurityRelPK|"))
                /* 722 */return getEntityRef(RoleSecurityRelPK.getKeyFromTokens((String) pk));
            /* 723 */if (((String) pk).startsWith("RoleSecurityCK|"))
                /* 724 */return getEntityRef(RoleSecurityCK.getKeyFromTokens((String) pk));
            /* 725 */if (((String) pk).startsWith("RoleSecurityPK|"))
                /* 726 */return getEntityRef(RoleSecurityPK.getKeyFromTokens((String) pk));
            /* 727 */if (((String) pk).startsWith("UserPreferenceCK|"))
                /* 728 */return getEntityRef(UserPreferenceCK.getKeyFromTokens((String) pk));
            /* 729 */if (((String) pk).startsWith("UserPreferencePK|"))
                /* 730 */return getEntityRef(UserPreferencePK.getKeyFromTokens((String) pk));
            /* 731 */if (((String) pk).startsWith("BudgetInstructionCK|"))
                /* 732 */return getEntityRef(BudgetInstructionCK.getKeyFromTokens((String) pk));
            /* 733 */if (((String) pk).startsWith("BudgetInstructionPK|"))
                /* 734 */return getEntityRef(BudgetInstructionPK.getKeyFromTokens((String) pk));
            /* 735 */if (((String) pk).startsWith("BudgetInstructionAssignmentCK|"))
                /* 736 */return getEntityRef(BudgetInstructionAssignmentCK.getKeyFromTokens((String) pk));
            /* 737 */if (((String) pk).startsWith("BudgetInstructionAssignmentPK|"))
                /* 738 */return getEntityRef(BudgetInstructionAssignmentPK.getKeyFromTokens((String) pk));
            /* 739 */if (((String) pk).startsWith("TaskCK|"))
                /* 740 */return getEntityRef(TaskCK.getKeyFromTokens((String) pk));
            /* 741 */if (((String) pk).startsWith("TaskPK|"))
                /* 742 */return getEntityRef(TaskPK.getKeyFromTokens((String) pk));
            /* 743 */if (((String) pk).startsWith("SystemPropertyCK|"))
                /* 744 */return getEntityRef(SystemPropertyCK.getKeyFromTokens((String) pk));
            /* 745 */if (((String) pk).startsWith("SystemPropertyPK|"))
                /* 746 */return getEntityRef(SystemPropertyPK.getKeyFromTokens((String) pk));
            /* 747 */if (((String) pk).startsWith("XmlFormCK|"))
                /* 748 */return getEntityRef(XmlFormCK.getKeyFromTokens((String) pk));
            /* 749 */if (((String) pk).startsWith("XmlFormPK|"))
                /* 750 */return getEntityRef(XmlFormPK.getKeyFromTokens((String) pk));
            /* 751 */if (((String) pk).startsWith("XmlFormUserLinkCK|"))
                /* 752 */return getEntityRef(XmlFormUserLinkCK.getKeyFromTokens((String) pk));
            /* 753 */if (((String) pk).startsWith("XmlFormUserLinkPK|"))
                /* 754 */return getEntityRef(XmlFormUserLinkPK.getKeyFromTokens((String) pk));
            /* 755 */if (((String) pk).startsWith("FormNotesCK|"))
                /* 756 */return getEntityRef(FormNotesCK.getKeyFromTokens((String) pk));
            /* 757 */if (((String) pk).startsWith("FormNotesPK|"))
                /* 758 */return getEntityRef(FormNotesPK.getKeyFromTokens((String) pk));
            /* 759 */if (((String) pk).startsWith("XmlReportCK|"))
                /* 760 */return getEntityRef(XmlReportCK.getKeyFromTokens((String) pk));
            /* 761 */if (((String) pk).startsWith("XmlReportPK|"))
                /* 762 */return getEntityRef(XmlReportPK.getKeyFromTokens((String) pk));
            /* 763 */if (((String) pk).startsWith("XmlReportFolderCK|"))
                /* 764 */return getEntityRef(XmlReportFolderCK.getKeyFromTokens((String) pk));
            /* 765 */if (((String) pk).startsWith("XmlReportFolderPK|"))
                /* 766 */return getEntityRef(XmlReportFolderPK.getKeyFromTokens((String) pk));
            /* 767 */if (((String) pk).startsWith("DataEntryProfileCK|"))
                /* 768 */return getEntityRef(DataEntryProfileCK.getKeyFromTokens((String) pk));
            /* 769 */if (((String) pk).startsWith("DataEntryProfilePK|"))
                /* 770 */return getEntityRef(DataEntryProfilePK.getKeyFromTokens((String) pk));
            /* 771 */if (((String) pk).startsWith("DataEntryProfileHistoryCK|"))
                /* 772 */return getEntityRef(DataEntryProfileHistoryCK.getKeyFromTokens((String) pk));
            /* 773 */if (((String) pk).startsWith("DataEntryProfileHistoryPK|"))
                /* 774 */return getEntityRef(DataEntryProfileHistoryPK.getKeyFromTokens((String) pk));
            /* 775 */if (((String) pk).startsWith("UdefLookupCK|"))
                /* 776 */return getEntityRef(UdefLookupCK.getKeyFromTokens((String) pk));
            /* 777 */if (((String) pk).startsWith("UdefLookupPK|"))
                /* 778 */return getEntityRef(UdefLookupPK.getKeyFromTokens((String) pk));
            /* 779 */if (((String) pk).startsWith("UdefLookupColumnDefCK|"))
                /* 780 */return getEntityRef(UdefLookupColumnDefCK.getKeyFromTokens((String) pk));
            /* 781 */if (((String) pk).startsWith("UdefLookupColumnDefPK|"))
                /* 782 */return getEntityRef(UdefLookupColumnDefPK.getKeyFromTokens((String) pk));
            /* 783 */if (((String) pk).startsWith("SecurityRangeCK|"))
                /* 784 */return getEntityRef(SecurityRangeCK.getKeyFromTokens((String) pk));
            /* 785 */if (((String) pk).startsWith("SecurityRangePK|"))
                /* 786 */return getEntityRef(SecurityRangePK.getKeyFromTokens((String) pk));
            /* 787 */if (((String) pk).startsWith("SecurityRangeRowCK|"))
                /* 788 */return getEntityRef(SecurityRangeRowCK.getKeyFromTokens((String) pk));
            /* 789 */if (((String) pk).startsWith("SecurityRangeRowPK|"))
                /* 790 */return getEntityRef(SecurityRangeRowPK.getKeyFromTokens((String) pk));
            /* 791 */if (((String) pk).startsWith("SecurityAccessDefCK|"))
                /* 792 */return getEntityRef(SecurityAccessDefCK.getKeyFromTokens((String) pk));
            /* 793 */if (((String) pk).startsWith("SecurityAccessDefPK|"))
                /* 794 */return getEntityRef(SecurityAccessDefPK.getKeyFromTokens((String) pk));
            /* 795 */if (((String) pk).startsWith("SecurityAccRngRelCK|"))
                /* 796 */return getEntityRef(SecurityAccRngRelCK.getKeyFromTokens((String) pk));
            /* 797 */if (((String) pk).startsWith("SecurityAccRngRelPK|"))
                /* 798 */return getEntityRef(SecurityAccRngRelPK.getKeyFromTokens((String) pk));
            /* 799 */if (((String) pk).startsWith("SecurityGroupCK|"))
                /* 800 */return getEntityRef(SecurityGroupCK.getKeyFromTokens((String) pk));
            /* 801 */if (((String) pk).startsWith("SecurityGroupPK|"))
                /* 802 */return getEntityRef(SecurityGroupPK.getKeyFromTokens((String) pk));
            /* 803 */if (((String) pk).startsWith("CcDeploymentCK|"))
                /* 804 */return getEntityRef(CcDeploymentCK.getKeyFromTokens((String) pk));
            /* 805 */if (((String) pk).startsWith("CcDeploymentPK|"))
                /* 806 */return getEntityRef(CcDeploymentPK.getKeyFromTokens((String) pk));
            /* 807 */if (((String) pk).startsWith("CcDeploymentLineCK|"))
                /* 808 */return getEntityRef(CcDeploymentLineCK.getKeyFromTokens((String) pk));
            /* 809 */if (((String) pk).startsWith("CcDeploymentLinePK|"))
                /* 810 */return getEntityRef(CcDeploymentLinePK.getKeyFromTokens((String) pk));
            /* 811 */if (((String) pk).startsWith("CcDeploymentEntryCK|"))
                /* 812 */return getEntityRef(CcDeploymentEntryCK.getKeyFromTokens((String) pk));
            /* 813 */if (((String) pk).startsWith("CcDeploymentEntryPK|"))
                /* 814 */return getEntityRef(CcDeploymentEntryPK.getKeyFromTokens((String) pk));
            /* 815 */if (((String) pk).startsWith("CcDeploymentDataTypeCK|"))
                /* 816 */return getEntityRef(CcDeploymentDataTypeCK.getKeyFromTokens((String) pk));
            /* 817 */if (((String) pk).startsWith("CcDeploymentDataTypePK|"))
                /* 818 */return getEntityRef(CcDeploymentDataTypePK.getKeyFromTokens((String) pk));
            /* 819 */if (((String) pk).startsWith("CcMappingLineCK|"))
                /* 820 */return getEntityRef(CcMappingLineCK.getKeyFromTokens((String) pk));
            /* 821 */if (((String) pk).startsWith("CcMappingLinePK|"))
                /* 822 */return getEntityRef(CcMappingLinePK.getKeyFromTokens((String) pk));
            /* 823 */if (((String) pk).startsWith("CcMappingEntryCK|"))
                /* 824 */return getEntityRef(CcMappingEntryCK.getKeyFromTokens((String) pk));
            /* 825 */if (((String) pk).startsWith("CcMappingEntryPK|"))
                /* 826 */return getEntityRef(CcMappingEntryPK.getKeyFromTokens((String) pk));
            /* 827 */if (((String) pk).startsWith("CellCalcCK|"))
                /* 828 */return getEntityRef(CellCalcCK.getKeyFromTokens((String) pk));
            /* 829 */if (((String) pk).startsWith("CellCalcPK|"))
                /* 830 */return getEntityRef(CellCalcPK.getKeyFromTokens((String) pk));
            /* 831 */if (((String) pk).startsWith("CellCalcAssocCK|"))
                /* 832 */return getEntityRef(CellCalcAssocCK.getKeyFromTokens((String) pk));
            /* 833 */if (((String) pk).startsWith("CellCalcAssocPK|"))
                /* 834 */return getEntityRef(CellCalcAssocPK.getKeyFromTokens((String) pk));
            /* 835 */if (((String) pk).startsWith("ChangeMgmtCK|"))
                /* 836 */return getEntityRef(ChangeMgmtCK.getKeyFromTokens((String) pk));
            /* 837 */if (((String) pk).startsWith("ChangeMgmtPK|"))
                /* 838 */return getEntityRef(ChangeMgmtPK.getKeyFromTokens((String) pk));
            /* 839 */if (((String) pk).startsWith("ImpExpHdrCK|"))
                /* 840 */return getEntityRef(ImpExpHdrCK.getKeyFromTokens((String) pk));
            /* 841 */if (((String) pk).startsWith("ImpExpHdrPK|"))
                /* 842 */return getEntityRef(ImpExpHdrPK.getKeyFromTokens((String) pk));
            /* 843 */if (((String) pk).startsWith("ImpExpRowCK|"))
                /* 844 */return getEntityRef(ImpExpRowCK.getKeyFromTokens((String) pk));
            /* 845 */if (((String) pk).startsWith("ImpExpRowPK|"))
                /* 846 */return getEntityRef(ImpExpRowPK.getKeyFromTokens((String) pk));
            /* 847 */if (((String) pk).startsWith("ReportCK|"))
                /* 848 */return getEntityRef(ReportCK.getKeyFromTokens((String) pk));
            /* 849 */if (((String) pk).startsWith("ReportPK|"))
                /* 850 */return getEntityRef(ReportPK.getKeyFromTokens((String) pk));
            /* 851 */if (((String) pk).startsWith("CubePendingTranCK|"))
                /* 852 */return getEntityRef(CubePendingTranCK.getKeyFromTokens((String) pk));
            /* 853 */if (((String) pk).startsWith("CubePendingTranPK|"))
                /* 854 */return getEntityRef(CubePendingTranPK.getKeyFromTokens((String) pk));
            /* 855 */if (((String) pk).startsWith("VirementCategoryCK|"))
                /* 856 */return getEntityRef(VirementCategoryCK.getKeyFromTokens((String) pk));
            /* 857 */if (((String) pk).startsWith("VirementCategoryPK|"))
                /* 858 */return getEntityRef(VirementCategoryPK.getKeyFromTokens((String) pk));
            /* 859 */if (((String) pk).startsWith("VirementLocationCK|"))
                /* 860 */return getEntityRef(VirementLocationCK.getKeyFromTokens((String) pk));
            /* 861 */if (((String) pk).startsWith("VirementLocationPK|"))
                /* 862 */return getEntityRef(VirementLocationPK.getKeyFromTokens((String) pk));
            /* 863 */if (((String) pk).startsWith("VirementAccountCK|"))
                /* 864 */return getEntityRef(VirementAccountCK.getKeyFromTokens((String) pk));
            /* 865 */if (((String) pk).startsWith("VirementAccountPK|"))
                /* 866 */return getEntityRef(VirementAccountPK.getKeyFromTokens((String) pk));
            /* 867 */if (((String) pk).startsWith("BudgetLimitCK|"))
                /* 868 */return getEntityRef(BudgetLimitCK.getKeyFromTokens((String) pk));
            /* 869 */if (((String) pk).startsWith("BudgetLimitPK|"))
                /* 870 */return getEntityRef(BudgetLimitPK.getKeyFromTokens((String) pk));
            /* 871 */if (((String) pk).startsWith("PerfTestCK|"))
                /* 872 */return getEntityRef(PerfTestCK.getKeyFromTokens((String) pk));
            /* 873 */if (((String) pk).startsWith("PerfTestPK|"))
                /* 874 */return getEntityRef(PerfTestPK.getKeyFromTokens((String) pk));
            /* 875 */if (((String) pk).startsWith("PerfTestRunCK|"))
                /* 876 */return getEntityRef(PerfTestRunCK.getKeyFromTokens((String) pk));
            /* 877 */if (((String) pk).startsWith("PerfTestRunPK|"))
                /* 878 */return getEntityRef(PerfTestRunPK.getKeyFromTokens((String) pk));
            /* 879 */if (((String) pk).startsWith("PerfTestRunResultCK|"))
                /* 880 */return getEntityRef(PerfTestRunResultCK.getKeyFromTokens((String) pk));
            /* 881 */if (((String) pk).startsWith("PerfTestRunResultPK|"))
                /* 882 */return getEntityRef(PerfTestRunResultPK.getKeyFromTokens((String) pk));
            /* 883 */if (((String) pk).startsWith("MessageCK|"))
                /* 884 */return getEntityRef(MessageCK.getKeyFromTokens((String) pk));
            /* 885 */if (((String) pk).startsWith("MessagePK|"))
                /* 886 */return getEntityRef(MessagePK.getKeyFromTokens((String) pk));
            /* 887 */if (((String) pk).startsWith("MessageAttatchCK|"))
                /* 888 */return getEntityRef(MessageAttatchCK.getKeyFromTokens((String) pk));
            /* 889 */if (((String) pk).startsWith("MessageAttatchPK|"))
                /* 890 */return getEntityRef(MessageAttatchPK.getKeyFromTokens((String) pk));
            /* 891 */if (((String) pk).startsWith("MessageUserCK|"))
                /* 892 */return getEntityRef(MessageUserCK.getKeyFromTokens((String) pk));
            /* 893 */if (((String) pk).startsWith("MessageUserPK|"))
                /* 894 */return getEntityRef(MessageUserPK.getKeyFromTokens((String) pk));
            /* 895 */if (((String) pk).startsWith("RechargeCK|"))
                /* 896 */return getEntityRef(RechargeCK.getKeyFromTokens((String) pk));
            /* 897 */if (((String) pk).startsWith("RechargePK|"))
                /* 898 */return getEntityRef(RechargePK.getKeyFromTokens((String) pk));
            /* 899 */if (((String) pk).startsWith("RechargeCellsCK|"))
                /* 900 */return getEntityRef(RechargeCellsCK.getKeyFromTokens((String) pk));
            /* 901 */if (((String) pk).startsWith("RechargeCellsPK|"))
                /* 902 */return getEntityRef(RechargeCellsPK.getKeyFromTokens((String) pk));
            /* 903 */if (((String) pk).startsWith("RechargeGroupCK|"))
                /* 904 */return getEntityRef(RechargeGroupCK.getKeyFromTokens((String) pk));
            /* 905 */if (((String) pk).startsWith("RechargeGroupPK|"))
                /* 906 */return getEntityRef(RechargeGroupPK.getKeyFromTokens((String) pk));
            /* 907 */if (((String) pk).startsWith("RechargeGroupRelCK|"))
                /* 908 */return getEntityRef(RechargeGroupRelCK.getKeyFromTokens((String) pk));
            /* 909 */if (((String) pk).startsWith("RechargeGroupRelPK|"))
                /* 910 */return getEntityRef(RechargeGroupRelPK.getKeyFromTokens((String) pk));
            /* 911 */if (((String) pk).startsWith("BudgetActivityCK|"))
                /* 912 */return getEntityRef(BudgetActivityCK.getKeyFromTokens((String) pk));
            /* 913 */if (((String) pk).startsWith("BudgetActivityPK|"))
                /* 914 */return getEntityRef(BudgetActivityPK.getKeyFromTokens((String) pk));
            /* 915 */if (((String) pk).startsWith("BudgetActivityLinkCK|"))
                /* 916 */return getEntityRef(BudgetActivityLinkCK.getKeyFromTokens((String) pk));
            /* 917 */if (((String) pk).startsWith("BudgetActivityLinkPK|"))
                /* 918 */return getEntityRef(BudgetActivityLinkPK.getKeyFromTokens((String) pk));
            /* 919 */if (((String) pk).startsWith("VirementRequestCK|"))
                /* 920 */return getEntityRef(VirementRequestCK.getKeyFromTokens((String) pk));
            /* 921 */if (((String) pk).startsWith("VirementRequestPK|"))
                /* 922 */return getEntityRef(VirementRequestPK.getKeyFromTokens((String) pk));
            /* 923 */if (((String) pk).startsWith("VirementRequestGroupCK|"))
                /* 924 */return getEntityRef(VirementRequestGroupCK.getKeyFromTokens((String) pk));
            /* 925 */if (((String) pk).startsWith("VirementRequestGroupPK|"))
                /* 926 */return getEntityRef(VirementRequestGroupPK.getKeyFromTokens((String) pk));
            /* 927 */if (((String) pk).startsWith("VirementRequestLineCK|"))
                /* 928 */return getEntityRef(VirementRequestLineCK.getKeyFromTokens((String) pk));
            /* 929 */if (((String) pk).startsWith("VirementRequestLinePK|"))
                /* 930 */return getEntityRef(VirementRequestLinePK.getKeyFromTokens((String) pk));
            /* 931 */if (((String) pk).startsWith("VirementLineSpreadCK|"))
                /* 932 */return getEntityRef(VirementLineSpreadCK.getKeyFromTokens((String) pk));
            /* 933 */if (((String) pk).startsWith("VirementLineSpreadPK|"))
                /* 934 */return getEntityRef(VirementLineSpreadPK.getKeyFromTokens((String) pk));
            /* 935 */if (((String) pk).startsWith("VirementAuthPointCK|"))
                /* 936 */return getEntityRef(VirementAuthPointCK.getKeyFromTokens((String) pk));
            /* 937 */if (((String) pk).startsWith("VirementAuthPointPK|"))
                /* 938 */return getEntityRef(VirementAuthPointPK.getKeyFromTokens((String) pk));
            /* 939 */if (((String) pk).startsWith("VirementAuthPointLinkCK|"))
                /* 940 */return getEntityRef(VirementAuthPointLinkCK.getKeyFromTokens((String) pk));
            /* 941 */if (((String) pk).startsWith("VirementAuthPointLinkPK|"))
                /* 942 */return getEntityRef(VirementAuthPointLinkPK.getKeyFromTokens((String) pk));
            /* 943 */if (((String) pk).startsWith("VirementAuthorisersCK|"))
                /* 944 */return getEntityRef(VirementAuthorisersCK.getKeyFromTokens((String) pk));
            /* 945 */if (((String) pk).startsWith("VirementAuthorisersPK|"))
                /* 946 */return getEntityRef(VirementAuthorisersPK.getKeyFromTokens((String) pk));
            /* 947 */if (((String) pk).startsWith("ReportTypeCK|"))
                /* 948 */return getEntityRef(ReportTypeCK.getKeyFromTokens((String) pk));
            /* 949 */if (((String) pk).startsWith("ReportTypePK|"))
                /* 950 */return getEntityRef(ReportTypePK.getKeyFromTokens((String) pk));
            /* 951 */if (((String) pk).startsWith("ReportTypeParamCK|"))
                /* 952 */return getEntityRef(ReportTypeParamCK.getKeyFromTokens((String) pk));
            /* 953 */if (((String) pk).startsWith("ReportTypeParamPK|"))
                /* 954 */return getEntityRef(ReportTypeParamPK.getKeyFromTokens((String) pk));
            /* 955 */if (((String) pk).startsWith("ReportDefinitionCK|"))
                /* 956 */return getEntityRef(ReportDefinitionCK.getKeyFromTokens((String) pk));
            /* 957 */if (((String) pk).startsWith("ReportDefinitionPK|"))
                /* 958 */return getEntityRef(ReportDefinitionPK.getKeyFromTokens((String) pk));
            /* 959 */if (((String) pk).startsWith("ReportDefFormCK|"))
                /* 960 */return getEntityRef(ReportDefFormCK.getKeyFromTokens((String) pk));
            /* 961 */if (((String) pk).startsWith("ReportDefFormPK|"))
                /* 962 */return getEntityRef(ReportDefFormPK.getKeyFromTokens((String) pk));
            /* 963 */if (((String) pk).startsWith("ReportDefMappedExcelCK|"))
                /* 964 */return getEntityRef(ReportDefMappedExcelCK.getKeyFromTokens((String) pk));
            /* 965 */if (((String) pk).startsWith("ReportDefMappedExcelPK|"))
                /* 966 */return getEntityRef(ReportDefMappedExcelPK.getKeyFromTokens((String) pk));
            /* 967 */if (((String) pk).startsWith("ReportDefCalculatorCK|"))
                /* 968 */return getEntityRef(ReportDefCalculatorCK.getKeyFromTokens((String) pk));
            /* 969 */if (((String) pk).startsWith("ReportDefCalculatorPK|"))
                /* 970 */return getEntityRef(ReportDefCalculatorPK.getKeyFromTokens((String) pk));
            /* 971 */if (((String) pk).startsWith("ReportDefSummaryCalcCK|"))
                /* 972 */return getEntityRef(ReportDefSummaryCalcCK.getKeyFromTokens((String) pk));
            /* 973 */if (((String) pk).startsWith("ReportDefSummaryCalcPK|"))
                /* 974 */return getEntityRef(ReportDefSummaryCalcPK.getKeyFromTokens((String) pk));
            /* 975 */if (((String) pk).startsWith("ReportTemplateCK|"))
                /* 976 */return getEntityRef(ReportTemplateCK.getKeyFromTokens((String) pk));
            /* 977 */if (((String) pk).startsWith("ReportTemplatePK|"))
                /* 978 */return getEntityRef(ReportTemplatePK.getKeyFromTokens((String) pk));
            /* 979 */if (((String) pk).startsWith("ReportMappingTemplateCK|"))
                /* 980 */return getEntityRef(ReportMappingTemplateCK.getKeyFromTokens((String) pk));
            /* 981 */if (((String) pk).startsWith("ReportMappingTemplatePK|"))
                /* 982 */return getEntityRef(ReportMappingTemplatePK.getKeyFromTokens((String) pk));
            /* 983 */if (((String) pk).startsWith("ExternalDestinationCK|"))
                /* 984 */return getEntityRef(ExternalDestinationCK.getKeyFromTokens((String) pk));
            /* 985 */if (((String) pk).startsWith("ExternalDestinationPK|"))
                /* 986 */return getEntityRef(ExternalDestinationPK.getKeyFromTokens((String) pk));
            /* 987 */if (((String) pk).startsWith("ExternalDestinationUsersCK|"))
                /* 988 */return getEntityRef(ExternalDestinationUsersCK.getKeyFromTokens((String) pk));
            /* 989 */if (((String) pk).startsWith("ExternalDestinationUsersPK|"))
                /* 990 */return getEntityRef(ExternalDestinationUsersPK.getKeyFromTokens((String) pk));
            /* 991 */if (((String) pk).startsWith("InternalDestinationCK|"))
                /* 992 */return getEntityRef(InternalDestinationCK.getKeyFromTokens((String) pk));
            /* 993 */if (((String) pk).startsWith("InternalDestinationPK|"))
                /* 994 */return getEntityRef(InternalDestinationPK.getKeyFromTokens((String) pk));
            /* 995 */if (((String) pk).startsWith("InternalDestinationUsersCK|"))
                /* 996 */return getEntityRef(InternalDestinationUsersCK.getKeyFromTokens((String) pk));
            /* 997 */if (((String) pk).startsWith("InternalDestinationUsersPK|"))
                /* 998 */return getEntityRef(InternalDestinationUsersPK.getKeyFromTokens((String) pk));
            /* 999 */if (((String) pk).startsWith("DistributionCK|"))
                /* 1000 */return getEntityRef(DistributionCK.getKeyFromTokens((String) pk));
            /* 1001 */if (((String) pk).startsWith("DistributionPK|"))
                /* 1002 */return getEntityRef(DistributionPK.getKeyFromTokens((String) pk));
            /* 1003 */if (((String) pk).startsWith("DistributionLinkCK|"))
                /* 1004 */return getEntityRef(DistributionLinkCK.getKeyFromTokens((String) pk));
            /* 1005 */if (((String) pk).startsWith("DistributionLinkPK|"))
                /* 1006 */return getEntityRef(DistributionLinkPK.getKeyFromTokens((String) pk));
            /* 1007 */if (((String) pk).startsWith("ReportPackCK|"))
                /* 1008 */return getEntityRef(ReportPackCK.getKeyFromTokens((String) pk));
            /* 1009 */if (((String) pk).startsWith("ReportPackPK|"))
                /* 1010 */return getEntityRef(ReportPackPK.getKeyFromTokens((String) pk));
            /* 1011 */if (((String) pk).startsWith("ReportPackLinkCK|"))
                /* 1012 */return getEntityRef(ReportPackLinkCK.getKeyFromTokens((String) pk));
            /* 1013 */if (((String) pk).startsWith("ReportPackLinkPK|"))
                /* 1014 */return getEntityRef(ReportPackLinkPK.getKeyFromTokens((String) pk));
            /* 1015 */if (((String) pk).startsWith("ReportGroupingCK|"))
                /* 1016 */return getEntityRef(ReportGroupingCK.getKeyFromTokens((String) pk));
            /* 1017 */if (((String) pk).startsWith("ReportGroupingPK|"))
                /* 1018 */return getEntityRef(ReportGroupingPK.getKeyFromTokens((String) pk));
            /* 1019 */if (((String) pk).startsWith("ReportGroupingFileCK|"))
                /* 1020 */return getEntityRef(ReportGroupingFileCK.getKeyFromTokens((String) pk));
            /* 1021 */if (((String) pk).startsWith("ReportGroupingFilePK|"))
                /* 1022 */return getEntityRef(ReportGroupingFilePK.getKeyFromTokens((String) pk));
            /* 1023 */if (((String) pk).startsWith("WeightingProfileCK|"))
                /* 1024 */return getEntityRef(WeightingProfileCK.getKeyFromTokens((String) pk));
            /* 1025 */if (((String) pk).startsWith("WeightingProfilePK|"))
                /* 1026 */return getEntityRef(WeightingProfilePK.getKeyFromTokens((String) pk));
            /* 1027 */if (((String) pk).startsWith("WeightingProfileLineCK|"))
                /* 1028 */return getEntityRef(WeightingProfileLineCK.getKeyFromTokens((String) pk));
            /* 1029 */if (((String) pk).startsWith("WeightingProfileLinePK|"))
                /* 1030 */return getEntityRef(WeightingProfileLinePK.getKeyFromTokens((String) pk));
            /* 1031 */if (((String) pk).startsWith("WeightingDeploymentCK|"))
                /* 1032 */return getEntityRef(WeightingDeploymentCK.getKeyFromTokens((String) pk));
            /* 1033 */if (((String) pk).startsWith("WeightingDeploymentPK|"))
                /* 1034 */return getEntityRef(WeightingDeploymentPK.getKeyFromTokens((String) pk));
            /* 1035 */if (((String) pk).startsWith("WeightingDeploymentLineCK|"))
                /* 1036 */return getEntityRef(WeightingDeploymentLineCK.getKeyFromTokens((String) pk));
            /* 1037 */if (((String) pk).startsWith("WeightingDeploymentLinePK|"))
                /* 1038 */return getEntityRef(WeightingDeploymentLinePK.getKeyFromTokens((String) pk));
            /* 1039 */if (((String) pk).startsWith("TidyTaskCK|"))
                /* 1040 */return getEntityRef(TidyTaskCK.getKeyFromTokens((String) pk));
            /* 1041 */if (((String) pk).startsWith("TidyTaskPK|"))
                /* 1042 */return getEntityRef(TidyTaskPK.getKeyFromTokens((String) pk));
            /* 1043 */if (((String) pk).startsWith("TidyTaskLinkCK|"))
                /* 1044 */return getEntityRef(TidyTaskLinkCK.getKeyFromTokens((String) pk));
            /* 1045 */if (((String) pk).startsWith("TidyTaskLinkPK|"))
                /* 1046 */return getEntityRef(TidyTaskLinkPK.getKeyFromTokens((String) pk));
            /* 1047 */if (((String) pk).startsWith("MappedModelCK|"))
                /* 1048 */return getEntityRef(MappedModelCK.getKeyFromTokens((String) pk));
            /* 1049 */if (((String) pk).startsWith("MappedModelPK|"))
                /* 1050 */return getEntityRef(MappedModelPK.getKeyFromTokens((String) pk));
            if (((String) pk).startsWith("GlobalMappedModel2CK|"))
                return getEntityRef(GlobalMappedModel2CK.getKeyFromTokens((String) pk));
            if (((String) pk).startsWith("GlobalMappedModel2PK|"))
                return getEntityRef(GlobalMappedModel2PK.getKeyFromTokens((String) pk));
            /* 1051 */if (((String) pk).startsWith("MappedFinanceCubeCK|"))
                /* 1052 */return getEntityRef(MappedFinanceCubeCK.getKeyFromTokens((String) pk));
            /* 1053 */if (((String) pk).startsWith("MappedFinanceCubePK|"))
                /* 1054 */return getEntityRef(MappedFinanceCubePK.getKeyFromTokens((String) pk));
            /* 1055 */if (((String) pk).startsWith("MappedDataTypeCK|"))
                /* 1056 */return getEntityRef(MappedDataTypeCK.getKeyFromTokens((String) pk));
            /* 1057 */if (((String) pk).startsWith("MappedDataTypePK|"))
                /* 1058 */return getEntityRef(MappedDataTypePK.getKeyFromTokens((String) pk));
            /* 1059 */if (((String) pk).startsWith("MappedCalendarYearCK|"))
                /* 1060 */return getEntityRef(MappedCalendarYearCK.getKeyFromTokens((String) pk));
            /* 1061 */if (((String) pk).startsWith("MappedCalendarYearPK|"))
                /* 1062 */return getEntityRef(MappedCalendarYearPK.getKeyFromTokens((String) pk));
            /* 1063 */if (((String) pk).startsWith("MappedCalendarElementCK|"))
                /* 1064 */return getEntityRef(MappedCalendarElementCK.getKeyFromTokens((String) pk));
            /* 1065 */if (((String) pk).startsWith("MappedCalendarElementPK|"))
                /* 1066 */return getEntityRef(MappedCalendarElementPK.getKeyFromTokens((String) pk));
            /* 1067 */if (((String) pk).startsWith("MappedDimensionCK|"))
                /* 1068 */return getEntityRef(MappedDimensionCK.getKeyFromTokens((String) pk));
            /* 1069 */if (((String) pk).startsWith("MappedDimensionPK|"))
                /* 1070 */return getEntityRef(MappedDimensionPK.getKeyFromTokens((String) pk));
            /* 1071 */if (((String) pk).startsWith("MappedDimensionElementCK|"))
                /* 1072 */return getEntityRef(MappedDimensionElementCK.getKeyFromTokens((String) pk));
            /* 1073 */if (((String) pk).startsWith("MappedDimensionElementPK|"))
                /* 1074 */return getEntityRef(MappedDimensionElementPK.getKeyFromTokens((String) pk));
            /* 1075 */if (((String) pk).startsWith("MappedHierarchyCK|"))
                /* 1076 */return getEntityRef(MappedHierarchyCK.getKeyFromTokens((String) pk));
            /* 1077 */if (((String) pk).startsWith("MappedHierarchyPK|"))
                /* 1078 */return getEntityRef(MappedHierarchyPK.getKeyFromTokens((String) pk));
            /* 1079 */if (((String) pk).startsWith("ExtendedAttachmentCK|"))
                /* 1080 */return getEntityRef(ExtendedAttachmentCK.getKeyFromTokens((String) pk));
            /* 1081 */if (((String) pk).startsWith("ExtendedAttachmentPK|"))
                /* 1082 */return getEntityRef(ExtendedAttachmentPK.getKeyFromTokens((String) pk));
            /* 1083 */if (((String) pk).startsWith("ExternalSystemCK|"))
                /* 1084 */return getEntityRef(ExternalSystemCK.getKeyFromTokens((String) pk));
            /* 1085 */if (((String) pk).startsWith("ExternalSystemPK|"))
                /* 1086 */return getEntityRef(ExternalSystemPK.getKeyFromTokens((String) pk));
            /* 1087 */if (((String) pk).startsWith("ExtSysPropertyCK|"))
                /* 1088 */return getEntityRef(ExtSysPropertyCK.getKeyFromTokens((String) pk));
            /* 1089 */if (((String) pk).startsWith("ExtSysPropertyPK|"))
                /* 1090 */return getEntityRef(ExtSysPropertyPK.getKeyFromTokens((String) pk));
            /* 1091 */if (((String) pk).startsWith("ExtSysCompanyCK|"))
                /* 1092 */return getEntityRef(ExtSysCompanyCK.getKeyFromTokens((String) pk));
            /* 1093 */if (((String) pk).startsWith("ExtSysCompanyPK|"))
                /* 1094 */return getEntityRef(ExtSysCompanyPK.getKeyFromTokens((String) pk));
            /* 1095 */if (((String) pk).startsWith("ExtSysCalendarYearCK|"))
                /* 1096 */return getEntityRef(ExtSysCalendarYearCK.getKeyFromTokens((String) pk));
            /* 1097 */if (((String) pk).startsWith("ExtSysCalendarYearPK|"))
                /* 1098 */return getEntityRef(ExtSysCalendarYearPK.getKeyFromTokens((String) pk));
            /* 1099 */if (((String) pk).startsWith("ExtSysCalElementCK|"))
                /* 1100 */return getEntityRef(ExtSysCalElementCK.getKeyFromTokens((String) pk));
            /* 1101 */if (((String) pk).startsWith("ExtSysCalElementPK|"))
                /* 1102 */return getEntityRef(ExtSysCalElementPK.getKeyFromTokens((String) pk));
            /* 1103 */if (((String) pk).startsWith("ExtSysLedgerCK|"))
                /* 1104 */return getEntityRef(ExtSysLedgerCK.getKeyFromTokens((String) pk));
            /* 1105 */if (((String) pk).startsWith("ExtSysLedgerPK|"))
                /* 1106 */return getEntityRef(ExtSysLedgerPK.getKeyFromTokens((String) pk));
            /* 1107 */if (((String) pk).startsWith("ExtSysValueTypeCK|"))
                /* 1108 */return getEntityRef(ExtSysValueTypeCK.getKeyFromTokens((String) pk));
            /* 1109 */if (((String) pk).startsWith("ExtSysValueTypePK|"))
                /* 1110 */return getEntityRef(ExtSysValueTypePK.getKeyFromTokens((String) pk));
            /* 1111 */if (((String) pk).startsWith("ExtSysCurrencyCK|"))
                /* 1112 */return getEntityRef(ExtSysCurrencyCK.getKeyFromTokens((String) pk));
            /* 1113 */if (((String) pk).startsWith("ExtSysCurrencyPK|"))
                /* 1114 */return getEntityRef(ExtSysCurrencyPK.getKeyFromTokens((String) pk));
            /* 1115 */if (((String) pk).startsWith("ExtSysDimensionCK|"))
                /* 1116 */return getEntityRef(ExtSysDimensionCK.getKeyFromTokens((String) pk));
            /* 1117 */if (((String) pk).startsWith("ExtSysDimensionPK|"))
                /* 1118 */return getEntityRef(ExtSysDimensionPK.getKeyFromTokens((String) pk));
            /* 1119 */if (((String) pk).startsWith("ExtSysDimElementCK|"))
                /* 1120 */return getEntityRef(ExtSysDimElementCK.getKeyFromTokens((String) pk));
            /* 1121 */if (((String) pk).startsWith("ExtSysDimElementPK|"))
                /* 1122 */return getEntityRef(ExtSysDimElementPK.getKeyFromTokens((String) pk));
            /* 1123 */if (((String) pk).startsWith("ExtSysHierarchyCK|"))
                /* 1124 */return getEntityRef(ExtSysHierarchyCK.getKeyFromTokens((String) pk));
            /* 1125 */if (((String) pk).startsWith("ExtSysHierarchyPK|"))
                /* 1126 */return getEntityRef(ExtSysHierarchyPK.getKeyFromTokens((String) pk));
            /* 1127 */if (((String) pk).startsWith("ExtSysHierElementCK|"))
                /* 1128 */return getEntityRef(ExtSysHierElementCK.getKeyFromTokens((String) pk));
            /* 1129 */if (((String) pk).startsWith("ExtSysHierElementPK|"))
                /* 1130 */return getEntityRef(ExtSysHierElementPK.getKeyFromTokens((String) pk));
            /* 1131 */if (((String) pk).startsWith("ExtSysHierElemFeedCK|"))
                /* 1132 */return getEntityRef(ExtSysHierElemFeedCK.getKeyFromTokens((String) pk));
            /* 1133 */if (((String) pk).startsWith("ExtSysHierElemFeedPK|"))
                /* 1134 */return getEntityRef(ExtSysHierElemFeedPK.getKeyFromTokens((String) pk));
            /* 1135 */if (((String) pk).startsWith("AmmModelCK|"))
                /* 1136 */return getEntityRef(AmmModelCK.getKeyFromTokens((String) pk));
            /* 1137 */if (((String) pk).startsWith("AmmModelPK|"))
                /* 1138 */return getEntityRef(AmmModelPK.getKeyFromTokens((String) pk));
            /* 1139 */if (((String) pk).startsWith("AmmDimensionCK|"))
                /* 1140 */return getEntityRef(AmmDimensionCK.getKeyFromTokens((String) pk));
            /* 1141 */if (((String) pk).startsWith("AmmDimensionPK|"))
                /* 1142 */return getEntityRef(AmmDimensionPK.getKeyFromTokens((String) pk));
            /* 1143 */if (((String) pk).startsWith("AmmDimensionElementCK|"))
                /* 1144 */return getEntityRef(AmmDimensionElementCK.getKeyFromTokens((String) pk));
            /* 1145 */if (((String) pk).startsWith("AmmDimensionElementPK|"))
                /* 1146 */return getEntityRef(AmmDimensionElementPK.getKeyFromTokens((String) pk));
            /* 1147 */if (((String) pk).startsWith("AmmSrcStructureElementCK|"))
                /* 1148 */return getEntityRef(AmmSrcStructureElementCK.getKeyFromTokens((String) pk));
            /* 1149 */if (((String) pk).startsWith("AmmSrcStructureElementPK|"))
                /* 1150 */return getEntityRef(AmmSrcStructureElementPK.getKeyFromTokens((String) pk));
            /* 1151 */if (((String) pk).startsWith("AmmFinanceCubeCK|"))
                /* 1152 */return getEntityRef(AmmFinanceCubeCK.getKeyFromTokens((String) pk));
            /* 1153 */if (((String) pk).startsWith("AmmFinanceCubePK|"))
                /* 1154 */return getEntityRef(AmmFinanceCubePK.getKeyFromTokens((String) pk));
            /* 1155 */if (((String) pk).startsWith("AmmDataTypeCK|"))
                /* 1156 */return getEntityRef(AmmDataTypeCK.getKeyFromTokens((String) pk));
            /* 1157 */if (((String) pk).startsWith("AmmDataTypePK|"))
                /* 1158 */return getEntityRef(AmmDataTypePK.getKeyFromTokens((String) pk));
            /* 1159 */if (((String) pk).startsWith("TaskGroupCK|"))
                /* 1160 */return getEntityRef(TaskGroupCK.getKeyFromTokens((String) pk));
            /* 1161 */if (((String) pk).startsWith("TaskGroupPK|"))
                /* 1162 */return getEntityRef(TaskGroupPK.getKeyFromTokens((String) pk));
            /* 1163 */if (((String) pk).startsWith("TgRowCK|"))
                /* 1164 */return getEntityRef(TgRowCK.getKeyFromTokens((String) pk));
            /* 1165 */if (((String) pk).startsWith("TgRowPK|"))
                /* 1166 */return getEntityRef(TgRowPK.getKeyFromTokens((String) pk));
            /* 1167 */if (((String) pk).startsWith("TgRowParamCK|"))
                /* 1168 */return getEntityRef(TgRowParamCK.getKeyFromTokens((String) pk));
            /* 1169 */if (((String) pk).startsWith("TgRowParamPK|"))
                /* 1170 */return getEntityRef(TgRowParamPK.getKeyFromTokens((String) pk));
            /* 1171 */if (((String) pk).startsWith("AuthenticationPolicyCK|"))
                /* 1172 */return getEntityRef(AuthenticationPolicyCK.getKeyFromTokens((String) pk));
            /* 1173 */if (((String) pk).startsWith("AuthenticationPolicyPK|"))
                /* 1174 */return getEntityRef(AuthenticationPolicyPK.getKeyFromTokens((String) pk));
            /* 1175 */if (((String) pk).startsWith("LogonHistoryCK|"))
                /* 1176 */return getEntityRef(LogonHistoryCK.getKeyFromTokens((String) pk));
            /* 1177 */if (((String) pk).startsWith("LogonHistoryPK|"))
                /* 1178 */return getEntityRef(LogonHistoryPK.getKeyFromTokens((String) pk));
            /* 1179 */if (((String) pk).startsWith("PasswordHistoryCK|"))
                /* 1180 */return getEntityRef(PasswordHistoryCK.getKeyFromTokens((String) pk));
            /* 1181 */if (((String) pk).startsWith("PasswordHistoryPK|"))
                /* 1182 */return getEntityRef(PasswordHistoryPK.getKeyFromTokens((String) pk));
            /* 1183 */if (((String) pk).startsWith("FormRebuildCK|"))
                /* 1184 */return getEntityRef(FormRebuildCK.getKeyFromTokens((String) pk));
            /* 1185 */if (((String) pk).startsWith("FormRebuildPK|"))
                /* 1186 */return getEntityRef(FormRebuildPK.getKeyFromTokens((String) pk));
            /* 1187 */if (((String) pk).startsWith("CubeFormulaPackageCK|"))
                /* 1188 */return getEntityRef(CubeFormulaPackageCK.getKeyFromTokens((String) pk));
            /* 1189 */if (((String) pk).startsWith("CubeFormulaPackagePK|"))
                /* 1190 */return getEntityRef(CubeFormulaPackagePK.getKeyFromTokens((String) pk));
            /* 1191 */if (((String) pk).startsWith("CubeFormulaCK|"))
                /* 1192 */return getEntityRef(CubeFormulaCK.getKeyFromTokens((String) pk));
            /* 1193 */if (((String) pk).startsWith("CubeFormulaPK|"))
                /* 1194 */return getEntityRef(CubeFormulaPK.getKeyFromTokens((String) pk));
            /* 1195 */if (((String) pk).startsWith("FormulaDeploymentLineCK|"))
                /* 1196 */return getEntityRef(FormulaDeploymentLineCK.getKeyFromTokens((String) pk));
            /* 1197 */if (((String) pk).startsWith("FormulaDeploymentLinePK|"))
                /* 1198 */return getEntityRef(FormulaDeploymentLinePK.getKeyFromTokens((String) pk));
            /* 1199 */if (((String) pk).startsWith("FormulaDeploymentEntryCK|"))
                /* 1200 */return getEntityRef(FormulaDeploymentEntryCK.getKeyFromTokens((String) pk));
            /* 1201 */if (((String) pk).startsWith("FormulaDeploymentEntryPK|"))
                /* 1202 */return getEntityRef(FormulaDeploymentEntryPK.getKeyFromTokens((String) pk));
            /* 1203 */if (((String) pk).startsWith("FormulaDeploymentDtCK|"))
                /* 1204 */return getEntityRef(FormulaDeploymentDtCK.getKeyFromTokens((String) pk));
            /* 1205 */if (((String) pk).startsWith("FormulaDeploymentDtPK|"))
                /* 1206 */return getEntityRef(FormulaDeploymentDtPK.getKeyFromTokens((String) pk));
            /* 1207 */if (((String) pk).startsWith("ImportGridCK|"))
                /* 1208 */return getEntityRef(ImportGridCK.getKeyFromTokens((String) pk));
            /* 1209 */if (((String) pk).startsWith("ImportGridPK|"))
                /* 1210 */return getEntityRef(ImportGridPK.getKeyFromTokens((String) pk));
            /* 1211 */if (((String) pk).startsWith("ImportGridCellCK|"))
                /* 1212 */return getEntityRef(ImportGridCellCK.getKeyFromTokens((String) pk));
            /* 1213 */if (((String) pk).startsWith("ImportGridCellPK|"))
                /* 1214 */return getEntityRef(ImportGridCellPK.getKeyFromTokens((String) pk));
            if (((String) pk).startsWith("MasterQuestionCK|"))
                return getEntityRef(MasterQuestionCK.getKeyFromTokens((String) pk));
            if (((String) pk).startsWith("MasterQuestionPK|"))
                return getEntityRef(MasterQuestionPK.getKeyFromTokens((String) pk));
            if (((String) pk).startsWith("ChallengeQuestionCK|"))
                return getEntityRef(ChallengeQuestionCK.getKeyFromTokens((String) pk));
            if (((String) pk).startsWith("ChallengeQuestionPK|"))
                return getEntityRef(ChallengeQuestionPK.getKeyFromTokens((String) pk));
            if (((String) pk).startsWith("UserResetLinkCK|"))
                return getEntityRef(UserResetLinkCK.getKeyFromTokens((String) pk));
            if (((String) pk).startsWith("UserResetLinkPK|"))
                return getEntityRef(UserResetLinkPK.getKeyFromTokens((String) pk));
            if (((String) pk).startsWith("BudgetCycleLinkCK|"))
                return getEntityRef(BudgetCycleLinkCK.getKeyFromTokens((String) pk));
            if (((String) pk).startsWith("BudgetCycleLinkPK|"))
                return getEntityRef(BudgetCycleLinkPK.getKeyFromTokens((String) pk));
            throw new IllegalArgumentException(new StringBuilder().append("couldn't recognise key name in ").append(pk).toString());
        }

        Timer timer = new Timer(this._log);
        EntityRef result = null;
        try {
            result = getTableEntityRef(pk);
            EntityRef localEntityRef1 = result;
            return localEntityRef1;
        } catch (Exception e) {
            throw new EJBException(e.getMessage(), e);
        } finally {
            if (result != null)
                timer.logDebug("getEntityRef", result != null ? pk : "");
        }
    }

    private EntityRef getTableEntityRef(Object pk) throws Exception {
        if (pk instanceof ModelPK) {
            return (new ModelDAO()).getRef((ModelPK) pk);
        } else if (pk instanceof ModelPropertyPK) {
            return (new ModelPropertyDAO()).getRef((ModelPropertyPK) pk);
        } else if (pk instanceof SecurityGroupUserRelPK) {
            return (new SecurityGroupUserRelDAO()).getRef((SecurityGroupUserRelPK) pk);
        } else if (pk instanceof ModelDimensionRelPK) {
            return (new ModelDimensionRelDAO()).getRef((ModelDimensionRelPK) pk);
        } else if (pk instanceof FinanceCubePK) {
            return (new FinanceCubeDAO()).getRef((FinanceCubePK) pk);
        } else if (pk instanceof RollUpRulePK) {
            return (new RollUpRuleDAO()).getRef((RollUpRulePK) pk);
        } else if (pk instanceof FinanceCubeDataTypePK) {
            return (new FinanceCubeDataTypeDAO()).getRef((FinanceCubeDataTypePK) pk);
        } else if (pk instanceof BudgetCyclePK) {
            return (new BudgetCycleDAO()).getRef((BudgetCyclePK) pk);
        } else if (pk instanceof BudgetCycleLinkPK) {
            return (new BudgetCycleLinkDAO()).getRef((BudgetCycleLinkPK) pk);
        } else if (pk instanceof LevelDatePK) {
            return (new LevelDateDAO()).getRef((LevelDatePK) pk);
        } else if (pk instanceof BudgetStatePK) {
            return (new BudgetStateDAO()).getRef((BudgetStatePK) pk);
        } else if (pk instanceof BudgetStateHistoryPK) {
            return (new BudgetStateHistoryDAO()).getRef((BudgetStateHistoryPK) pk);
        } else if (pk instanceof ResponsibilityAreaPK) {
            return (new ResponsibilityAreaDAO()).getRef((ResponsibilityAreaPK) pk);
        } else if (pk instanceof BudgetUserPK) {
            return (new BudgetUserDAO()).getRef((BudgetUserPK) pk);
        } else if (pk instanceof DataTypePK) {
            return (new DataTypeDAO()).getRef((DataTypePK) pk);
        } else if (pk instanceof DataTypeRelPK) {
            return (new DataTypeRelDAO()).getRef((DataTypeRelPK) pk);
        } else if (pk instanceof CurrencyPK) {
            return (new CurrencyDAO()).getRef((CurrencyPK) pk);
        } else if (pk instanceof StructureElementPK) {
            return (new StructureElementDAO()).getRef((StructureElementPK) pk);
        } else if (pk instanceof DimensionPK) {
            return (new DimensionDAO()).getRef((DimensionPK) pk);
        } else if (pk instanceof DimensionElementPK) {
            return (new DimensionElementDAO()).getRef((DimensionElementPK) pk);
        } else if (pk instanceof CalendarSpecPK) {
            return (new CalendarSpecDAO()).getRef((CalendarSpecPK) pk);
        } else if (pk instanceof CalendarYearSpecPK) {
            return (new CalendarYearSpecDAO()).getRef((CalendarYearSpecPK) pk);
        } else if (pk instanceof HierarchyPK) {
            return (new HierarchyDAO()).getRef((HierarchyPK) pk);
        } else if (pk instanceof HierarchyElementPK) {
            return (new HierarchyElementDAO()).getRef((HierarchyElementPK) pk);
        } else if (pk instanceof AugHierarchyElementPK) {
            return (new AugHierarchyElementDAO()).getRef((AugHierarchyElementPK) pk);
        } else if (pk instanceof HierarchyElementFeedPK) {
            return (new HierarchyElementFeedDAO()).getRef((HierarchyElementFeedPK) pk);
        } else if (pk instanceof DefaultUserPrefPK) {
            return (new DefaultUserPrefDAO()).getRef((DefaultUserPrefPK) pk);
        } else if (pk instanceof UserPK) {
            return (new UserDAO()).getRef((UserPK) pk);
        } else if (pk instanceof UserRolePK) {
            return (new UserRoleDAO()).getRef((UserRolePK) pk);
        } else if (pk instanceof RolePK) {
            return (new RoleDAO()).getRef((RolePK) pk);
        } else if (pk instanceof RoleSecurityRelPK) {
            return (new RoleSecurityRelDAO()).getRef((RoleSecurityRelPK) pk);
        } else if (pk instanceof RoleSecurityPK) {
            return (new RoleSecurityDAO()).getRef((RoleSecurityPK) pk);
        } else if (pk instanceof UserPreferencePK) {
            return (new UserPreferenceDAO()).getRef((UserPreferencePK) pk);
        } else if (pk instanceof BudgetInstructionPK) {
            return (new BudgetInstructionDAO()).getRef((BudgetInstructionPK) pk);
        } else if (pk instanceof BudgetInstructionAssignmentPK) {
            return (new BudgetInstructionAssignmentDAO()).getRef((BudgetInstructionAssignmentPK) pk);
        } else if (pk instanceof TaskPK) {
            return (new TaskDAO()).getRef((TaskPK) pk);
        } else if (pk instanceof SystemPropertyPK) {
            return (new SystemPropertyDAO()).getRef((SystemPropertyPK) pk);
        } else if (pk instanceof XmlFormPK) {
            return (new XmlFormDAO()).getRef((XmlFormPK) pk);
        } else if (pk instanceof XmlFormUserLinkPK) {
            return (new XmlFormUserLinkDAO()).getRef((XmlFormUserLinkPK) pk);
        } else if (pk instanceof FormNotesPK) {
            return (new FormNotesDAO()).getRef((FormNotesPK) pk);
        } else if (pk instanceof XmlReportPK) {
            return (new XmlReportDAO()).getRef((XmlReportPK) pk);
        } else if (pk instanceof XmlReportFolderPK) {
            return (new XmlReportFolderDAO()).getRef((XmlReportFolderPK) pk);
        } else if (pk instanceof DataEntryProfilePK) {
            return (new DataEntryProfileDAO()).getRef((DataEntryProfilePK) pk);
        } else if (pk instanceof DataEntryProfileHistoryPK) {
            return (new DataEntryProfileHistoryDAO()).getRef((DataEntryProfileHistoryPK) pk);
        } else if (pk instanceof UdefLookupPK) {
            return (new UdefLookupDAO()).getRef((UdefLookupPK) pk);
        } else if (pk instanceof UdefLookupColumnDefPK) {
            return (new UdefLookupColumnDefDAO()).getRef((UdefLookupColumnDefPK) pk);
        } else if (pk instanceof SecurityRangePK) {
            return (new SecurityRangeDAO()).getRef((SecurityRangePK) pk);
        } else if (pk instanceof SecurityRangeRowPK) {
            return (new SecurityRangeRowDAO()).getRef((SecurityRangeRowPK) pk);
        } else if (pk instanceof SecurityAccessDefPK) {
            return (new SecurityAccessDefDAO()).getRef((SecurityAccessDefPK) pk);
        } else if (pk instanceof SecurityAccRngRelPK) {
            return (new SecurityAccRngRelDAO()).getRef((SecurityAccRngRelPK) pk);
        } else if (pk instanceof SecurityGroupPK) {
            return (new SecurityGroupDAO()).getRef((SecurityGroupPK) pk);
        } else if (pk instanceof CcDeploymentPK) {
            return (new CcDeploymentDAO()).getRef((CcDeploymentPK) pk);
        } else if (pk instanceof CcDeploymentLinePK) {
            return (new CcDeploymentLineDAO()).getRef((CcDeploymentLinePK) pk);
        } else if (pk instanceof CcDeploymentEntryPK) {
            return (new CcDeploymentEntryDAO()).getRef((CcDeploymentEntryPK) pk);
        } else if (pk instanceof CcDeploymentDataTypePK) {
            return (new CcDeploymentDataTypeDAO()).getRef((CcDeploymentDataTypePK) pk);
        } else if (pk instanceof CcMappingLinePK) {
            return (new CcMappingLineDAO()).getRef((CcMappingLinePK) pk);
        } else if (pk instanceof CcMappingEntryPK) {
            return (new CcMappingEntryDAO()).getRef((CcMappingEntryPK) pk);
        } else if (pk instanceof CellCalcPK) {
            return (new CellCalcDAO()).getRef((CellCalcPK) pk);
        } else if (pk instanceof CellCalcAssocPK) {
            return (new CellCalcAssocDAO()).getRef((CellCalcAssocPK) pk);
        } else if (pk instanceof ChangeMgmtPK) {
            return (new ChangeMgmtDAO()).getRef((ChangeMgmtPK) pk);
        } else if (pk instanceof ImpExpHdrPK) {
            return (new ImpExpHdrDAO()).getRef((ImpExpHdrPK) pk);
        } else if (pk instanceof ImpExpRowPK) {
            return (new ImpExpRowDAO()).getRef((ImpExpRowPK) pk);
        } else if (pk instanceof ReportPK) {
            return (new ReportDAO()).getRef((ReportPK) pk);
        } else if (pk instanceof CubePendingTranPK) {
            return (new CubePendingTranDAO()).getRef((CubePendingTranPK) pk);
        } else if (pk instanceof VirementCategoryPK) {
            return (new VirementCategoryDAO()).getRef((VirementCategoryPK) pk);
        } else if (pk instanceof VirementLocationPK) {
            return (new VirementLocationDAO()).getRef((VirementLocationPK) pk);
        } else if (pk instanceof VirementAccountPK) {
            return (new VirementAccountDAO()).getRef((VirementAccountPK) pk);
        } else if (pk instanceof BudgetLimitPK) {
            return (new BudgetLimitDAO()).getRef((BudgetLimitPK) pk);
        } else if (pk instanceof PerfTestPK) {
            return (new PerfTestDAO()).getRef((PerfTestPK) pk);
        } else if (pk instanceof PerfTestRunPK) {
            return (new PerfTestRunDAO()).getRef((PerfTestRunPK) pk);
        } else if (pk instanceof PerfTestRunResultPK) {
            return (new PerfTestRunResultDAO()).getRef((PerfTestRunResultPK) pk);
        } else if (pk instanceof MessagePK) {
            return (new MessageDAO()).getRef((MessagePK) pk);
        } else if (pk instanceof MessageAttatchPK) {
            return (new MessageAttatchDAO()).getRef((MessageAttatchPK) pk);
        } else if (pk instanceof MessageUserPK) {
            return (new MessageUserDAO()).getRef((MessageUserPK) pk);
        } else if (pk instanceof RechargePK) {
            return (new RechargeDAO()).getRef((RechargePK) pk);
        } else if (pk instanceof RechargeCellsPK) {
            return (new RechargeCellsDAO()).getRef((RechargeCellsPK) pk);
        } else if (pk instanceof RechargeGroupPK) {
            return (new RechargeGroupDAO()).getRef((RechargeGroupPK) pk);
        } else if (pk instanceof RechargeGroupRelPK) {
            return (new RechargeGroupRelDAO()).getRef((RechargeGroupRelPK) pk);
        } else if (pk instanceof BudgetActivityPK) {
            return (new BudgetActivityDAO()).getRef((BudgetActivityPK) pk);
        } else if (pk instanceof BudgetActivityLinkPK) {
            return (new BudgetActivityLinkDAO()).getRef((BudgetActivityLinkPK) pk);
        } else if (pk instanceof VirementRequestPK) {
            return (new VirementRequestDAO()).getRef((VirementRequestPK) pk);
        } else if (pk instanceof VirementRequestGroupPK) {
            return (new VirementRequestGroupDAO()).getRef((VirementRequestGroupPK) pk);
        } else if (pk instanceof VirementRequestLinePK) {
            return (new VirementRequestLineDAO()).getRef((VirementRequestLinePK) pk);
        } else if (pk instanceof VirementLineSpreadPK) {
            return (new VirementLineSpreadDAO()).getRef((VirementLineSpreadPK) pk);
        } else if (pk instanceof VirementAuthPointPK) {
            return (new VirementAuthPointDAO()).getRef((VirementAuthPointPK) pk);
        } else if (pk instanceof VirementAuthPointLinkPK) {
            return (new VirementAuthPointLinkDAO()).getRef((VirementAuthPointLinkPK) pk);
        } else if (pk instanceof VirementAuthorisersPK) {
            return (new VirementAuthorisersDAO()).getRef((VirementAuthorisersPK) pk);
        } else if (pk instanceof ReportTypePK) {
            return (new ReportTypeDAO()).getRef((ReportTypePK) pk);
        } else if (pk instanceof ReportTypeParamPK) {
            return (new ReportTypeParamDAO()).getRef((ReportTypeParamPK) pk);
        } else if (pk instanceof ReportDefinitionPK) {
            return (new ReportDefinitionDAO()).getRef((ReportDefinitionPK) pk);
        } else if (pk instanceof ReportDefFormPK) {
            return (new ReportDefFormDAO()).getRef((ReportDefFormPK) pk);
        } else if (pk instanceof ReportDefMappedExcelPK) {
            return (new ReportDefMappedExcelDAO()).getRef((ReportDefMappedExcelPK) pk);
        } else if (pk instanceof ReportDefCalculatorPK) {
            return (new ReportDefCalculatorDAO()).getRef((ReportDefCalculatorPK) pk);
        } else if (pk instanceof ReportDefSummaryCalcPK) {
            return (new ReportDefSummaryCalcDAO()).getRef((ReportDefSummaryCalcPK) pk);
        } else if (pk instanceof ReportTemplatePK) {
            return (new ReportTemplateDAO()).getRef((ReportTemplatePK) pk);
        } else if (pk instanceof ReportMappingTemplatePK) {
            return (new ReportMappingTemplateDAO()).getRef((ReportMappingTemplatePK) pk);
        } else if (pk instanceof ExternalDestinationPK) {
            return (new ExternalDestinationDAO()).getRef((ExternalDestinationPK) pk);
        } else if (pk instanceof ExternalDestinationUsersPK) {
            return (new ExternalDestinationUsersDAO()).getRef((ExternalDestinationUsersPK) pk);
        } else if (pk instanceof InternalDestinationPK) {
            return (new InternalDestinationDAO()).getRef((InternalDestinationPK) pk);
        } else if (pk instanceof InternalDestinationUsersPK) {
            return (new InternalDestinationUsersDAO()).getRef((InternalDestinationUsersPK) pk);
        } else if (pk instanceof DistributionPK) {
            return (new DistributionDAO()).getRef((DistributionPK) pk);
        } else if (pk instanceof DistributionLinkPK) {
            return (new DistributionLinkDAO()).getRef((DistributionLinkPK) pk);
        } else if (pk instanceof ReportPackPK) {
            return (new ReportPackDAO()).getRef((ReportPackPK) pk);
        } else if (pk instanceof ReportPackLinkPK) {
            return (new ReportPackLinkDAO()).getRef((ReportPackLinkPK) pk);
        } else if (pk instanceof ReportGroupingPK) {
            return (new ReportGroupingDAO()).getRef((ReportGroupingPK) pk);
        } else if (pk instanceof ReportGroupingFilePK) {
            return (new ReportGroupingFileDAO()).getRef((ReportGroupingFilePK) pk);
        } else if (pk instanceof WeightingProfilePK) {
            return (new WeightingProfileDAO()).getRef((WeightingProfilePK) pk);
        } else if (pk instanceof WeightingProfileLinePK) {
            return (new WeightingProfileLineDAO()).getRef((WeightingProfileLinePK) pk);
        } else if (pk instanceof WeightingDeploymentPK) {
            return (new WeightingDeploymentDAO()).getRef((WeightingDeploymentPK) pk);
        } else if (pk instanceof WeightingDeploymentLinePK) {
            return (new WeightingDeploymentLineDAO()).getRef((WeightingDeploymentLinePK) pk);
        } else if (pk instanceof TidyTaskPK) {
            return (new TidyTaskDAO()).getRef((TidyTaskPK) pk);
        } else if (pk instanceof TidyTaskLinkPK) {
            return (new TidyTaskLinkDAO()).getRef((TidyTaskLinkPK) pk);
        } else if (pk instanceof MappedModelPK) {
            return (new MappedModelDAO()).getRef((MappedModelPK) pk);
        } else if (pk instanceof GlobalMappedModel2PK) {
            return (new GlobalMappedModel2DAO()).getRef((GlobalMappedModel2PK) pk);
        } else if (pk instanceof MappedFinanceCubePK) {
            return (new MappedFinanceCubeDAO()).getRef((MappedFinanceCubePK) pk);
        } else if (pk instanceof MappedDataTypePK) {
            return (new MappedDataTypeDAO()).getRef((MappedDataTypePK) pk);
        } else if (pk instanceof MappedCalendarYearPK) {
            return (new MappedCalendarYearDAO()).getRef((MappedCalendarYearPK) pk);
        } else if (pk instanceof MappedCalendarElementPK) {
            return (new MappedCalendarElementDAO()).getRef((MappedCalendarElementPK) pk);
        } else if (pk instanceof MappedDimensionPK) {
            return (new MappedDimensionDAO()).getRef((MappedDimensionPK) pk);
        } else if (pk instanceof MappedDimensionElementPK) {
            return (new MappedDimensionElementDAO()).getRef((MappedDimensionElementPK) pk);
        } else if (pk instanceof MappedHierarchyPK) {
            return (new MappedHierarchyDAO()).getRef((MappedHierarchyPK) pk);
        } else if (pk instanceof ExtendedAttachmentPK) {
            return (new ExtendedAttachmentDAO()).getRef((ExtendedAttachmentPK) pk);
        } else if (pk instanceof ExternalSystemPK) {
            return (new ExternalSystemDAO()).getRef((ExternalSystemPK) pk);
        } else if (pk instanceof ExtSysPropertyPK) {
            return (new ExtSysPropertyDAO()).getRef((ExtSysPropertyPK) pk);
        } else if (pk instanceof ExtSysCompanyPK) {
            return (new ExtSysCompanyDAO()).getRef((ExtSysCompanyPK) pk);
        } else if (pk instanceof ExtSysCalendarYearPK) {
            return (new ExtSysCalendarYearDAO()).getRef((ExtSysCalendarYearPK) pk);
        } else if (pk instanceof ExtSysCalElementPK) {
            return (new ExtSysCalElementDAO()).getRef((ExtSysCalElementPK) pk);
        } else if (pk instanceof ExtSysLedgerPK) {
            return (new ExtSysLedgerDAO()).getRef((ExtSysLedgerPK) pk);
        } else if (pk instanceof ExtSysValueTypePK) {
            return (new ExtSysValueTypeDAO()).getRef((ExtSysValueTypePK) pk);
        } else if (pk instanceof ExtSysCurrencyPK) {
            return (new ExtSysCurrencyDAO()).getRef((ExtSysCurrencyPK) pk);
        } else if (pk instanceof ExtSysDimensionPK) {
            return (new ExtSysDimensionDAO()).getRef((ExtSysDimensionPK) pk);
        } else if (pk instanceof ExtSysDimElementPK) {
            return (new ExtSysDimElementDAO()).getRef((ExtSysDimElementPK) pk);
        } else if (pk instanceof ExtSysHierarchyPK) {
            return (new ExtSysHierarchyDAO()).getRef((ExtSysHierarchyPK) pk);
        } else if (pk instanceof ExtSysHierElementPK) {
            return (new ExtSysHierElementDAO()).getRef((ExtSysHierElementPK) pk);
        } else if (pk instanceof ExtSysHierElemFeedPK) {
            return (new ExtSysHierElemFeedDAO()).getRef((ExtSysHierElemFeedPK) pk);
        } else if (pk instanceof AmmModelPK) {
            return (new AmmModelDAO()).getRef((AmmModelPK) pk);
        } else if (pk instanceof AmmDimensionPK) {
            return (new AmmDimensionDAO()).getRef((AmmDimensionPK) pk);
        } else if (pk instanceof AmmDimensionElementPK) {
            return (new AmmDimensionElementDAO()).getRef((AmmDimensionElementPK) pk);
        } else if (pk instanceof AmmSrcStructureElementPK) {
            return (new AmmSrcStructureElementDAO()).getRef((AmmSrcStructureElementPK) pk);
        } else if (pk instanceof AmmFinanceCubePK) {
            return (new AmmFinanceCubeDAO()).getRef((AmmFinanceCubePK) pk);
        } else if (pk instanceof AmmDataTypePK) {
            return (new AmmDataTypeDAO()).getRef((AmmDataTypePK) pk);
        } else if (pk instanceof TaskGroupPK) {
            return (new TaskGroupDAO()).getRef((TaskGroupPK) pk);
        } else if (pk instanceof TgRowPK) {
            return (new TgRowDAO()).getRef((TgRowPK) pk);
        } else if (pk instanceof TgRowParamPK) {
            return (new TgRowParamDAO()).getRef((TgRowParamPK) pk);
        } else if (pk instanceof AuthenticationPolicyPK) {
            return (new AuthenticationPolicyDAO()).getRef((AuthenticationPolicyPK) pk);
        } else if (pk instanceof LogonHistoryPK) {
        	return null;
//            return (new LogonHistoryDAO()).getRef((LogonHistoryPK) pk);
        } else if (pk instanceof PasswordHistoryPK) {
            return (new PasswordHistoryDAO()).getRef((PasswordHistoryPK) pk);
        } else if (pk instanceof FormRebuildPK) {
            return (new FormRebuildDAO()).getRef((FormRebuildPK) pk);
        } else if (pk instanceof CubeFormulaPackagePK) {
            return (new CubeFormulaPackageDAO()).getRef((CubeFormulaPackagePK) pk);
        } else if (pk instanceof CubeFormulaPK) {
            return (new CubeFormulaDAO()).getRef((CubeFormulaPK) pk);
        } else if (pk instanceof FormulaDeploymentLinePK) {
            return (new FormulaDeploymentLineDAO()).getRef((FormulaDeploymentLinePK) pk);
        } else if (pk instanceof FormulaDeploymentEntryPK) {
            return (new FormulaDeploymentEntryDAO()).getRef((FormulaDeploymentEntryPK) pk);
        } else if (pk instanceof FormulaDeploymentDtPK) {
            return (new FormulaDeploymentDtDAO()).getRef((FormulaDeploymentDtPK) pk);
        } else if (pk instanceof ImportGridPK) {
            return (new ImportGridDAO()).getRef((ImportGridPK) pk);
        } else if (pk instanceof ImportGridCellPK) {
            return (new ImportGridCellDAO()).getRef((ImportGridCellPK) pk);
        } else if ((pk instanceof MasterQuestionPK)) {
            return new MasterQuestionDAO().getRef((MasterQuestionPK) pk);
        } else if ((pk instanceof ChallengeQuestionPK)) {
            return new ChallengeQuestionDAO().getRef((ChallengeQuestionPK) pk);
        } else if ((pk instanceof UserResetLinkPK)) {
            return new UserResetLinkDAO().getRef((UserResetLinkPK) pk);
        } else {
            throw new IllegalArgumentException("unexpected parameter: " + pk.getClass().getName());
        }
    }

    public AllModelsELO getAllModels() throws EJBException {
        AllModelsELO elo = null;

        try {
            ////ModelAccessor e = new ModelAccessor(new InitialContext());
            elo = modelAccessor.getAllModels();
            return elo;
        } catch (Exception var3) {
            throw new EJBException(var3.getMessage(), var3);
        }
    }

    public AllModelsELO getAllModelsForLoggedUser(int userId) throws EJBException {
        AllModelsELO elo = null;

        try {
            ////ModelAccessor e = new ModelAccessor(new InitialContext());
            elo = modelAccessor.getAllModelsForLoggedUser(userId);
            return elo;
        } catch (Exception var3) {
            throw new EJBException(var3.getMessage(), var3);
        }
    }

    public Map<String, String> getPropertiesForModelVisId(String modelVisId) throws EJBException {
    	Map<String, String> elo = null;

        try {
            ModelAccessor modelAccessor = new ModelAccessor(new InitialContext());
            elo = modelAccessor.getPropertiesForModelVisId(modelVisId);
            return elo;
        } catch (Exception e) {
            throw new EJBException(e.getMessage(), e);
        }
    }
    
    public Map<String, String> getPropertiesForModelId(int modelId) throws EJBException {
    	Map<String, String> elo = null;

        try {
            ModelAccessor modelAccessor = new ModelAccessor(new InitialContext());
            elo = modelAccessor.getPropertiesForModelId(modelId);
            return elo;
        } catch (Exception e) {
            throw new EJBException(e.getMessage(), e);
        }
    }
    
    public AllModelsELO getAllModelsForGlobalMappedModel(int modelId) throws EJBException {
        AllModelsELO elo = null;

        try {
            ModelAccessor modelAccessor = new ModelAccessor(new InitialContext());
            elo = modelAccessor.getAllModelsForGlobalMappedModel(modelId);
            return elo;
        } catch (Exception e) {
            throw new EJBException(e.getMessage(), e);
        }
    }

    public AllModelsWebELO getAllModelsWeb() throws EJBException {
        AllModelsWebELO elo = null;

        try {
            ////ModelAccessor e = new ModelAccessor(new InitialContext());
            elo = modelAccessor.getAllModelsWeb();
            return elo;
        } catch (Exception var3) {
            throw new EJBException(var3.getMessage(), var3);
        }
    }

    public AllModelsWebForUserELO getAllModelsWebForUser(int param1) throws EJBException {
        AllModelsWebForUserELO elo = null;

        try {
            ////ModelAccessor e = new ModelAccessor(new InitialContext());
            elo = modelAccessor.getAllModelsWebForUser(param1);
            return elo;
        } catch (Exception var4) {
            throw new EJBException(var4.getMessage(), var4);
        }
    }

    public AllModelsWithActiveCycleForUserELO getAllModelsWithActiveCycleForUser(int param1) throws EJBException {
        AllModelsWithActiveCycleForUserELO elo = null;

        try {
            ////ModelAccessor e = new ModelAccessor(new InitialContext());
            elo = modelAccessor.getAllModelsWithActiveCycleForUser(param1);
            return elo;
        } catch (Exception var4) {
            throw new EJBException(var4.getMessage(), var4);
        }
    }

    public AllBudgetHierarchiesELO getAllBudgetHierarchies() throws EJBException {
        AllBudgetHierarchiesELO elo = null;

        try {
            ////ModelAccessor e = new ModelAccessor(new InitialContext());
            elo = modelAccessor.getAllBudgetHierarchies();
            return elo;
        } catch (Exception var3) {
            throw new EJBException(var3.getMessage(), var3);
        }
    }

    public ModelForDimensionELO getModelForDimension(int param1) throws EJBException {
        ModelForDimensionELO elo = null;

        try {
            ////ModelAccessor e = new ModelAccessor(new InitialContext());
            elo = modelAccessor.getModelForDimension(param1);
            return elo;
        } catch (Exception var4) {
            throw new EJBException(var4.getMessage(), var4);
        }
    }

    public ModelDimensionsELO getModelDimensions(int param1) throws EJBException {
        ModelDimensionsELO elo = null;

        try {
            ////ModelAccessor e = new ModelAccessor(new InitialContext());
            elo = modelAccessor.getModelDimensions(param1);
            return elo;
        } catch (Exception var4) {
            throw new EJBException(var4.getMessage(), var4);
        }
    }

    public ModelDimensionseExcludeCallELO getModelDimensionseExcludeCall(int param1) throws EJBException {
        ModelDimensionseExcludeCallELO elo = null;

        try {
            ////ModelAccessor e = new ModelAccessor(new InitialContext());
            elo = modelAccessor.getModelDimensionseExcludeCall(param1);
            return elo;
        } catch (Exception var4) {
            throw new EJBException(var4.getMessage(), var4);
        }
    }

    public ModelDetailsWebELO getModelDetailsWeb(int param1) throws EJBException {
        ModelDetailsWebELO elo = null;

        try {
            ////ModelAccessor e = new ModelAccessor(new InitialContext());
            elo = modelAccessor.getModelDetailsWeb(param1);
            return elo;
        } catch (Exception var4) {
            throw new EJBException(var4.getMessage(), var4);
        }
    }

    public AllRootsForModelELO getAllRootsForModel(int param1) throws EJBException {
        AllRootsForModelELO elo = null;

        try {
            ////ModelAccessor e = new ModelAccessor(new InitialContext());
            elo = modelAccessor.getAllRootsForModel(param1);
            return elo;
        } catch (Exception var4) {
            throw new EJBException(var4.getMessage(), var4);
        }
    }

    public BudgetHierarchyRootNodeForModelELO getBudgetHierarchyRootNodeForModel(int param1) throws EJBException {
        BudgetHierarchyRootNodeForModelELO elo = null;

        try {
            ////ModelAccessor e = new ModelAccessor(new InitialContext());
            elo = modelAccessor.getBudgetHierarchyRootNodeForModel(param1);
            return elo;
        } catch (Exception var4) {
            throw new EJBException(var4.getMessage(), var4);
        }
    }

    public BudgetCyclesToFixStateELO getBudgetCyclesToFixState(int param1) throws EJBException {
        BudgetCyclesToFixStateELO elo = null;

        try {
            ////ModelAccessor e = new ModelAccessor(new InitialContext());
            elo = modelAccessor.getBudgetCyclesToFixState(param1);
            return elo;
        } catch (Exception var4) {
            throw new EJBException(var4.getMessage(), var4);
        }
    }

    public MaxDepthForBudgetHierarchyELO getMaxDepthForBudgetHierarchy(int param1) throws EJBException {
        MaxDepthForBudgetHierarchyELO elo = null;

        try {
            ////ModelAccessor e = new ModelAccessor(new InitialContext());
            elo = modelAccessor.getMaxDepthForBudgetHierarchy(param1);
            return elo;
        } catch (Exception var4) {
            throw new EJBException(var4.getMessage(), var4);
        }
    }

    public CalendarSpecForModelELO getCalendarSpecForModel(int param1) throws EJBException {
        CalendarSpecForModelELO elo = null;

        try {
            ////ModelAccessor e = new ModelAccessor(new InitialContext());
            elo = modelAccessor.getCalendarSpecForModel(param1);
            return elo;
        } catch (Exception var4) {
            throw new EJBException(var4.getMessage(), var4);
        }
    }

    public HierarchiesForModelELO getHierarchiesForModel(int param1) throws EJBException {
        HierarchiesForModelELO elo = null;

        try {
            ////ModelAccessor e = new ModelAccessor(new InitialContext());
            elo = modelAccessor.getHierarchiesForModel(param1);
            return elo;
        } catch (Exception var4) {
            throw new EJBException(var4.getMessage(), var4);
        }
    }

    public AllUsersForASecurityGroupELO getAllUsersForASecurityGroup(int param1) throws EJBException {
        AllUsersForASecurityGroupELO elo = null;

        try {
            ////ModelAccessor e = new ModelAccessor(new InitialContext());
            elo = modelAccessor.getAllUsersForASecurityGroup(param1);
            return elo;
        } catch (Exception var4) {
            throw new EJBException(var4.getMessage(), var4);
        }
    }

    public AllModelBusinessDimensionsELO getAllModelBusinessDimensions() throws EJBException {
        AllModelBusinessDimensionsELO elo = null;

        try {
            ////ModelAccessor e = new ModelAccessor(new InitialContext());
            elo = modelAccessor.getAllModelBusinessDimensions();
            return elo;
        } catch (Exception var3) {
            throw new EJBException(var3.getMessage(), var3);
        }
    }

    public AllModelBusAndAccDimensionsELO getAllModelBusAndAccDimensions() throws EJBException {
        AllModelBusAndAccDimensionsELO elo = null;

        try {
            ////ModelAccessor e = new ModelAccessor(new InitialContext());
            elo = modelAccessor.getAllModelBusAndAccDimensions();
            return elo;
        } catch (Exception var3) {
            throw new EJBException(var3.getMessage(), var3);
        }
    }

    public BudgetDimensionIdForModelELO getBudgetDimensionIdForModel(int param1, int param2) throws EJBException {
        BudgetDimensionIdForModelELO elo = null;

        try {
            ////ModelAccessor e = new ModelAccessor(new InitialContext());
            elo = modelAccessor.getBudgetDimensionIdForModel(param1, param2);
            return elo;
        } catch (Exception var5) {
            throw new EJBException(var5.getMessage(), var5);
        }
    }

    public DimensionIdForModelDimTypeELO getDimensionIdForModelDimType(int param1, int param2) throws EJBException {
        DimensionIdForModelDimTypeELO elo = null;

        try {
            ////ModelAccessor e = new ModelAccessor(new InitialContext());
            elo = modelAccessor.getDimensionIdForModelDimType(param1, param2);
            return elo;
        } catch (Exception var5) {
            throw new EJBException(var5.getMessage(), var5);
        }
    }

    public AllFinanceCubesELO getAllFinanceCubes() throws EJBException {
        AllFinanceCubesELO elo = null;

        try {
            ////ModelAccessor e = new ModelAccessor(new InitialContext());
            elo = modelAccessor.getAllFinanceCubes();
            return elo;
        } catch (Exception var3) {
            throw new EJBException(var3.getMessage(), var3);
        }
    }

    public AllFinanceCubesELO getAllFinanceCubesForLoggedUser(int userId) throws EJBException {
        AllFinanceCubesELO elo = null;

        try {
            ////ModelAccessor e = new ModelAccessor(new InitialContext());
            elo = modelAccessor.getAllFinanceCubesForLoggedUser(userId);
            return elo;
        } catch (Exception var3) {
            throw new EJBException(var3.getMessage(), var3);
        }
    }

    public AllCubeFormulasELO getAllCubeFormulasForLoggedUser(int userId) throws EJBException {
        AllCubeFormulasELO elo = null;
        try {
            ////ModelAccessor e = new ModelAccessor(new InitialContext());
            elo = modelAccessor.getAllCubeFormulasForLoggedUser(userId);
            return elo;
        } catch (Exception var3) {
            throw new EJBException(var3.getMessage(), var3);
        }
    }

    public AllSimpleFinanceCubesELO getAllSimpleFinanceCubes(int userId) throws EJBException {
        AllSimpleFinanceCubesELO elo = null;

        try {
            ////ModelAccessor e = new ModelAccessor(new InitialContext());
            elo = modelAccessor.getAllSimpleFinanceCubes(userId);
            return elo;
        } catch (Exception var3) {
            throw new EJBException(var3.getMessage(), var3);
        }
    }

    public AllDataTypesAttachedToFinanceCubeForModelELO getAllDataTypesAttachedToFinanceCubeForModel(int param1) throws EJBException {
        AllDataTypesAttachedToFinanceCubeForModelELO elo = null;

        try {
            ////ModelAccessor e = new ModelAccessor(new InitialContext());
            elo = modelAccessor.getAllDataTypesAttachedToFinanceCubeForModel(param1);
            return elo;
        } catch (Exception var4) {
            throw new EJBException(var4.getMessage(), var4);
        }
    }

    public FinanceCubesForModelELO getFinanceCubesForModel(int param1) throws EJBException {
        FinanceCubesForModelELO elo = null;

        try {
            ////ModelAccessor e = new ModelAccessor(new InitialContext());
            elo = modelAccessor.getFinanceCubesForModel(param1);
            return elo;
        } catch (Exception var4) {
            throw new EJBException(var4.getMessage(), var4);
        }
    }

    public FinanceCubeDimensionsAndHierachiesELO getFinanceCubeDimensionsAndHierachies(int param1) throws EJBException {
        FinanceCubeDimensionsAndHierachiesELO elo = null;

        try {
            ////ModelAccessor e = new ModelAccessor(new InitialContext());
            elo = modelAccessor.getFinanceCubeDimensionsAndHierachies(param1);
            return elo;
        } catch (Exception var4) {
            throw new EJBException(var4.getMessage(), var4);
        }
    }

    public FinanceCubeAllDimensionsAndHierachiesELO getFinanceCubeAllDimensionsAndHierachies(int param1) throws EJBException {
        FinanceCubeAllDimensionsAndHierachiesELO elo = null;

        try {
            ////ModelAccessor e = new ModelAccessor(new InitialContext());
            elo = modelAccessor.getFinanceCubeAllDimensionsAndHierachies(param1);
            return elo;
        } catch (Exception var4) {
            throw new EJBException(var4.getMessage(), var4);
        }
    }

    public AllFinanceCubesWebELO getAllFinanceCubesWeb() throws EJBException {
        AllFinanceCubesWebELO elo = null;

        try {
            ////ModelAccessor e = new ModelAccessor(new InitialContext());
            elo = modelAccessor.getAllFinanceCubesWeb();
            return elo;
        } catch (Exception var3) {
            throw new EJBException(var3.getMessage(), var3);
        }
    }

    public AllFinanceCubesWebForModelELO getAllFinanceCubesWebForModel(int param1) throws EJBException {
        AllFinanceCubesWebForModelELO elo = null;

        try {
            ////ModelAccessor e = new ModelAccessor(new InitialContext());
            elo = modelAccessor.getAllFinanceCubesWebForModel(param1);
            return elo;
        } catch (Exception var4) {
            throw new EJBException(var4.getMessage(), var4);
        }
    }

    public AllFinanceCubesWebForUserELO getAllFinanceCubesWebForUser(int param1) throws EJBException {
        AllFinanceCubesWebForUserELO elo = null;

        try {
            ////ModelAccessor e = new ModelAccessor(new InitialContext());
            elo = modelAccessor.getAllFinanceCubesWebForUser(param1);
            return elo;
        } catch (Exception var4) {
            throw new EJBException(var4.getMessage(), var4);
        }
    }

    public FinanceCubeDetailsELO getFinanceCubeDetails(int param1) throws EJBException {
        FinanceCubeDetailsELO elo = null;

        try {
            ////ModelAccessor e = new ModelAccessor(new InitialContext());
            elo = modelAccessor.getFinanceCubeDetails(param1);
            return elo;
        } catch (Exception var4) {
            throw new EJBException(var4.getMessage(), var4);
        }
    }

    public FinanceCubesUsingDataTypeELO getFinanceCubesUsingDataType(short param1) throws EJBException {
        FinanceCubesUsingDataTypeELO elo = null;

        try {
            ////ModelAccessor e = new ModelAccessor(new InitialContext());
            elo = modelAccessor.getFinanceCubesUsingDataType(param1);
            return elo;
        } catch (Exception var4) {
            throw new EJBException(var4.getMessage(), var4);
        }
    }

    public AllFinanceCubeDataTypesELO getAllFinanceCubeDataTypes() throws EJBException {
        AllFinanceCubeDataTypesELO elo = null;

        try {
            ////ModelAccessor e = new ModelAccessor(new InitialContext());
            elo = modelAccessor.getAllFinanceCubeDataTypes();
            return elo;
        } catch (Exception var3) {
            throw new EJBException(var3.getMessage(), var3);
        }
    }

    public ImportableFinanceCubeDataTypesELO getImportableFinanceCubeDataTypes() throws EJBException {
        ImportableFinanceCubeDataTypesELO elo = null;

        try {
            ////ModelAccessor e = new ModelAccessor(new InitialContext());
            elo = modelAccessor.getImportableFinanceCubeDataTypes();
            return elo;
        } catch (Exception var3) {
            throw new EJBException(var3.getMessage(), var3);
        }
    }

    public AllAttachedDataTypesForFinanceCubeELO getAllAttachedDataTypesForFinanceCube(int param1) throws EJBException {
        AllAttachedDataTypesForFinanceCubeELO elo = null;

        try {
            ////ModelAccessor e = new ModelAccessor(new InitialContext());
            elo = modelAccessor.getAllAttachedDataTypesForFinanceCube(param1);
            return elo;
        } catch (Exception var4) {
            throw new EJBException(var4.getMessage(), var4);
        }
    }

    public AllDataTypesForFinanceCubeELO getAllDataTypesForFinanceCube(int param1) throws EJBException {
        AllDataTypesForFinanceCubeELO elo = null;

        try {
            ////ModelAccessor e = new ModelAccessor(new InitialContext());
            elo = modelAccessor.getAllDataTypesForFinanceCube(param1);
            return elo;
        } catch (Exception var4) {
            throw new EJBException(var4.getMessage(), var4);
        }
    }

    public AllFinanceCubesForDataTypeELO getAllFinanceCubesForDataType(short param1) throws EJBException {
        AllFinanceCubesForDataTypeELO elo = null;

        try {
            ////ModelAccessor e = new ModelAccessor(new InitialContext());
            elo = modelAccessor.getAllFinanceCubesForDataType(param1);
            return elo;
        } catch (Exception var4) {
            throw new EJBException(var4.getMessage(), var4);
        }
    }

    public AllBudgetCyclesELO getAllBudgetCycles() throws EJBException {
        AllBudgetCyclesELO elo = null;

        try {
            ////ModelAccessor e = new ModelAccessor(new InitialContext());
            elo = modelAccessor.getAllBudgetCycles();
            return elo;
        } catch (Exception var3) {
            throw new EJBException(var3.getMessage(), var3);
        }
    }

    public AllBudgetCyclesWebELO getAllBudgetCyclesWeb() throws EJBException {
        AllBudgetCyclesWebELO elo = null;

        try {
            ////ModelAccessor e = new ModelAccessor(new InitialContext());
            elo = modelAccessor.getAllBudgetCyclesWeb();
            return elo;
        } catch (Exception var3) {
            throw new EJBException(var3.getMessage(), var3);
        }
    }

    public AllBudgetCyclesELO getAllBudgetCyclesForUser(int userId) throws EJBException {
        AllBudgetCyclesELO elo = null;

        try {
            ////ModelAccessor e = new ModelAccessor(new InitialContext());
            elo = modelAccessor.getAllBudgetCyclesForUser(userId);
            return elo;
        } catch (Exception var3) {
            throw new EJBException(var3.getMessage(), var3);
        }
    }

    public AllBudgetCyclesWebDetailedELO getAllBudgetCyclesWebDetailed() throws EJBException {
        AllBudgetCyclesWebDetailedELO elo = null;

        try {
            ////ModelAccessor e = new ModelAccessor(new InitialContext());
            elo = modelAccessor.getAllBudgetCyclesWebDetailed();
            return elo;
        } catch (Exception var3) {
            throw new EJBException(var3.getMessage(), var3);
        }
    }

    public BudgetCyclesForModelELO getBudgetCyclesForModel(int param1) throws EJBException {
        BudgetCyclesForModelELO elo = null;

        try {
            ////ModelAccessor e = new ModelAccessor(new InitialContext());
            elo = modelAccessor.getBudgetCyclesForModel(param1);
            return elo;
        } catch (Exception var4) {
            throw new EJBException(var4.getMessage(), var4);
        }
    }

    public BudgetCyclesForModelWithStateELO getBudgetCyclesForModelWithState(int param1, int param2) throws EJBException {
        BudgetCyclesForModelWithStateELO elo = null;

        try {
            ////ModelAccessor e = new ModelAccessor(new InitialContext());
            elo = modelAccessor.getBudgetCyclesForModelWithState(param1, param2);
            return elo;
        } catch (Exception var5) {
            throw new EJBException(var5.getMessage(), var5);
        }
    }

    public BudgetCycleIntegrityELO getBudgetCycleIntegrity() throws EJBException {
        BudgetCycleIntegrityELO elo = null;

        try {
            ////ModelAccessor e = new ModelAccessor(new InitialContext());
            elo = modelAccessor.getBudgetCycleIntegrity();
            return elo;
        } catch (Exception var3) {
            throw new EJBException(var3.getMessage(), var3);
        }
    }

    public BudgetCycleDetailedForIdELO getBudgetCycleDetailedForId(int param1) throws EJBException {
        BudgetCycleDetailedForIdELO elo = null;

        try {
            ////ModelAccessor e = new ModelAccessor(new InitialContext());
            elo = modelAccessor.getBudgetCycleDetailedForId(param1);
            return elo;
        } catch (Exception var4) {
            throw new EJBException(var4.getMessage(), var4);
        }
    }

    public BudgetCycleDetailedForIdELO getBudgetCycleXmlFormsForId(int param1, int userid) throws EJBException {
        BudgetCycleDetailedForIdELO elo = null;

        try {
            ////ModelAccessor e = new ModelAccessor(new InitialContext());
            elo = modelAccessor.getBudgetCycleXmlFormsForId(param1, userid);
            return elo;
        } catch (Exception var4) {
            throw new EJBException(var4.getMessage(), var4);
        }
    }

    public BudgetTransferBudgetCyclesELO getBudgetTransferBudgetCycles() throws EJBException {
        BudgetTransferBudgetCyclesELO elo = null;

        try {
            ////ModelAccessor e = new ModelAccessor(new InitialContext());
            elo = modelAccessor.getBudgetTransferBudgetCycles();
            return elo;
        } catch (Exception var3) {
            throw new EJBException(var3.getMessage(), var3);
        }
    }

    public CheckIfHasStateELO getCheckIfHasState(int param1, int param2) throws EJBException {
        CheckIfHasStateELO elo = null;

        try {
            ////ModelAccessor e = new ModelAccessor(new InitialContext());
            elo = modelAccessor.getCheckIfHasState(param1, param2);
            return elo;
        } catch (Exception var5) {
            throw new EJBException(var5.getMessage(), var5);
        }
    }

    public CycleStateDetailsELO getCycleStateDetails(int param1) throws EJBException {
        CycleStateDetailsELO elo = null;

        try {
            ////ModelAccessor e = new ModelAccessor(new InitialContext());
            elo = modelAccessor.getCycleStateDetails(param1);
            return elo;
        } catch (Exception var4) {
            throw new EJBException(var4.getMessage(), var4);
        }
    }

    public AllResponsibilityAreasELO getAllResponsibilityAreas() throws EJBException {
        AllResponsibilityAreasELO elo = null;

        try {
            ////ModelAccessor e = new ModelAccessor(new InitialContext());
            elo = modelAccessor.getAllResponsibilityAreas();
            return elo;
        } catch (Exception var3) {
            throw new EJBException(var3.getMessage(), var3);
        }
    }

    public AllBudgetUsersELO getAllBudgetUsers() throws EJBException {
        AllBudgetUsersELO elo = null;

        try {
            ////ModelAccessor e = new ModelAccessor(new InitialContext());
            elo = modelAccessor.getAllBudgetUsers();
            return elo;
        } catch (Exception var3) {
            throw new EJBException(var3.getMessage(), var3);
        }
    }

    public CheckUserAccessToModelELO getCheckUserAccessToModel(int param1, int param2) throws EJBException {
        CheckUserAccessToModelELO elo = null;

        try {
            ////ModelAccessor e = new ModelAccessor(new InitialContext());
            elo = modelAccessor.getCheckUserAccessToModel(param1, param2);
            return elo;
        } catch (Exception var5) {
            throw new EJBException(var5.getMessage(), var5);
        }
    }

    public CheckUserAccessELO getCheckUserAccess(int param1, int param2) throws EJBException {
        CheckUserAccessELO elo = null;

        try {
            ////ModelAccessor e = new ModelAccessor(new InitialContext());
            elo = modelAccessor.getCheckUserAccess(param1, param2);
            return elo;
        } catch (Exception var5) {
            throw new EJBException(var5.getMessage(), var5);
        }
    }

    public CheckUserELO getCheckUser(int param1) throws EJBException {
        CheckUserELO elo = null;

        try {
            ////ModelAccessor e = new ModelAccessor(new InitialContext());
            elo = modelAccessor.getCheckUser(param1);
            return elo;
        } catch (Exception var4) {
            throw new EJBException(var4.getMessage(), var4);
        }
    }

    public BudgetUsersForNodeELO getBudgetUsersForNode(int param1, int param2) throws EJBException {
        BudgetUsersForNodeELO elo = null;

        try {
            ////ModelAccessor e = new ModelAccessor(new InitialContext());
            elo = modelAccessor.getBudgetUsersForNode(param1, param2);
            return elo;
        } catch (Exception var5) {
            throw new EJBException(var5.getMessage(), var5);
        }
    }

    public NodesForUserAndCycleELO getNodesForUserAndCycle(int param1, int param2) throws EJBException {
        NodesForUserAndCycleELO elo = null;

        try {
            ////ModelAccessor e = new ModelAccessor(new InitialContext());
            elo = modelAccessor.getNodesForUserAndCycle(param1, param2);
            return elo;
        } catch (Exception var5) {
            throw new EJBException(var5.getMessage(), var5);
        }
    }

    public NodesForUserAndModelELO getNodesForUserAndModel(int param1, int param2) throws EJBException {
        NodesForUserAndModelELO elo = null;

        try {
            ////ModelAccessor e = new ModelAccessor(new InitialContext());
            elo = modelAccessor.getNodesForUserAndModel(param1, param2);
            return elo;
        } catch (Exception var5) {
            throw new EJBException(var5.getMessage(), var5);
        }
    }

    public UsersForModelAndElementELO getUsersForModelAndElement(int param1, int param2) throws EJBException {
        UsersForModelAndElementELO elo = null;

        try {
            ////ModelAccessor e = new ModelAccessor(new InitialContext());
            elo = modelAccessor.getUsersForModelAndElement(param1, param2);
            return elo;
        } catch (Exception var5) {
            throw new EJBException(var5.getMessage(), var5);
        }
    }

    public AllDataTypesELO getAllDataTypes() throws EJBException {
        AllDataTypesELO elo = null;

        try {
            DataTypeAccessor e = new DataTypeAccessor(new InitialContext());
            elo = e.getAllDataTypes();
            return elo;
        } catch (Exception var3) {
            throw new EJBException(var3.getMessage(), var3);
        }
    }

    public AllDataTypesWebELO getAllDataTypesWeb() throws EJBException {
        AllDataTypesWebELO elo = null;

        try {
            DataTypeAccessor e = new DataTypeAccessor(new InitialContext());
            elo = e.getAllDataTypesWeb();
            return elo;
        } catch (Exception var3) {
            throw new EJBException(var3.getMessage(), var3);
        }
    }

    public AllDataTypeForFinanceCubeELO getAllDataTypeForFinanceCube(int param1) throws EJBException {
        AllDataTypeForFinanceCubeELO elo = null;

        try {
            DataTypeAccessor e = new DataTypeAccessor(new InitialContext());
            elo = e.getAllDataTypeForFinanceCube(param1);
            return elo;
        } catch (Exception var4) {
            throw new EJBException(var4.getMessage(), var4);
        }
    }

    public AllDataTypesForModelELO getAllDataTypesForModel(int param1) throws EJBException {
        AllDataTypesForModelELO elo = null;

        try {
            DataTypeAccessor e = new DataTypeAccessor(new InitialContext());
            elo = e.getAllDataTypesForModel(param1);
            return elo;
        } catch (Exception var4) {
            throw new EJBException(var4.getMessage(), var4);
        }
    }

    public DataTypesByTypeELO getDataTypesByType(int param1) throws EJBException {
        DataTypesByTypeELO elo = null;

        try {
            DataTypeAccessor e = new DataTypeAccessor(new InitialContext());
            elo = e.getDataTypesByType(param1);
            return elo;
        } catch (Exception var4) {
            throw new EJBException(var4.getMessage(), var4);
        }
    }

    public DataTypesByTypeWriteableELO getDataTypesByTypeWriteable(int param1) throws EJBException {
        DataTypesByTypeWriteableELO elo = null;

        try {
            DataTypeAccessor e = new DataTypeAccessor(new InitialContext());
            elo = e.getDataTypesByTypeWriteable(param1);
            return elo;
        } catch (Exception var4) {
            throw new EJBException(var4.getMessage(), var4);
        }
    }

    public DataTypeDependenciesELO getDataTypeDependencies(short param1) throws EJBException {
        DataTypeDependenciesELO elo = null;

        try {
            DataTypeAccessor e = new DataTypeAccessor(new InitialContext());
            elo = e.getDataTypeDependencies(param1);
            return elo;
        } catch (Exception var4) {
            throw new EJBException(var4.getMessage(), var4);
        }
    }

    public DataTypesForImpExpELO getDataTypesForImpExp() throws EJBException {
        DataTypesForImpExpELO elo = null;

        try {
            DataTypeAccessor e = new DataTypeAccessor(new InitialContext());
            elo = e.getDataTypesForImpExp();
            return elo;
        } catch (Exception var3) {
            throw new EJBException(var3.getMessage(), var3);
        }
    }

    public DataTypeDetailsForVisIDELO getDataTypeDetailsForVisID(String param1) throws EJBException {
        DataTypeDetailsForVisIDELO elo = null;

        try {
            DataTypeAccessor e = new DataTypeAccessor(new InitialContext());
            elo = e.getDataTypeDetailsForVisID(param1);
            return elo;
        } catch (Exception var4) {
            throw new EJBException(var4.getMessage(), var4);
        }
    }

    public AllCurrencysELO getAllCurrencys() throws EJBException {
        AllCurrencysELO elo = null;

        try {
            CurrencyAccessor e = new CurrencyAccessor(new InitialContext());
            elo = e.getAllCurrencys();
            return elo;
        } catch (Exception var3) {
            throw new EJBException(var3.getMessage(), var3);
        }
    }

    public AllStructureElementsELO getAllStructureElements(int param1) throws EJBException {
        AllStructureElementsELO elo = null;

        try {
            StructureElementAccessor e = new StructureElementAccessor(new InitialContext());
            elo = e.getAllStructureElements(param1);
            return elo;
        } catch (Exception var4) {
            throw new EJBException(var4.getMessage(), var4);
        }
    }

    public AllLeafStructureElementsELO getAllLeafStructureElements(int param1) throws EJBException {
        AllLeafStructureElementsELO elo = null;

        try {
            StructureElementAccessor e = new StructureElementAccessor(new InitialContext());
            elo = e.getAllLeafStructureElements(param1);
            return elo;
        } catch (Exception var4) {
            throw new EJBException(var4.getMessage(), var4);
        }
    }

    public LeafPlannableBudgetLocationsForModelELO getLeafPlannableBudgetLocationsForModel(int param1) throws EJBException {
        LeafPlannableBudgetLocationsForModelELO elo = null;

        try {
            StructureElementAccessor e = new StructureElementAccessor(new InitialContext());
            elo = e.getLeafPlannableBudgetLocationsForModel(param1);
            return elo;
        } catch (Exception var4) {
            throw new EJBException(var4.getMessage(), var4);
        }
    }

    public StructureElementParentsELO getStructureElementParents(int param1, int param2) throws EJBException {
        StructureElementParentsELO elo = null;

        try {
            StructureElementAccessor e = new StructureElementAccessor(new InitialContext());
            elo = e.getStructureElementParents(param1, param2);
            return elo;
        } catch (Exception var5) {
            throw new EJBException(var5.getMessage(), var5);
        }
    }

    public StructureElementParentsReversedELO getStructureElementParentsReversed(int param1, int param2) throws EJBException {
        StructureElementParentsReversedELO elo = null;

        try {
            StructureElementAccessor e = new StructureElementAccessor(new InitialContext());
            elo = e.getStructureElementParentsReversed(param1, param2);
            return elo;
        } catch (Exception var5) {
            throw new EJBException(var5.getMessage(), var5);
        }
    }

    public StructureElementParentsFromVisIdELO getStructureElementParentsFromVisId(String param1, int param2) throws EJBException {
        StructureElementParentsFromVisIdELO elo = null;

        try {
            StructureElementAccessor e = new StructureElementAccessor(new InitialContext());
            elo = e.getStructureElementParentsFromVisId(param1, param2);
            return elo;
        } catch (Exception var5) {
            throw new EJBException(var5.getMessage(), var5);
        }
    }

    public ImmediateChildrenELO getImmediateChildren(int param1, int param2) throws EJBException {
        ImmediateChildrenELO elo = null;

        try {
            StructureElementAccessor e = new StructureElementAccessor(new InitialContext());
            elo = e.getImmediateChildren(param1, param2);
            return elo;
        } catch (Exception var5) {
            throw new EJBException(var5.getMessage(), var5);
        }
    }

    public MassStateImmediateChildrenELO getMassStateImmediateChildren(int param1, int param2) throws EJBException {
        MassStateImmediateChildrenELO elo = null;

        try {
            StructureElementAccessor e = new StructureElementAccessor(new InitialContext());
            elo = e.getMassStateImmediateChildren(param1, param2);
            return elo;
        } catch (Exception var5) {
            throw new EJBException(var5.getMessage(), var5);
        }
    }

    public StructureElementValuesELO getStructureElementValues(int param1, int param2) throws EJBException {
        StructureElementValuesELO elo = null;

        try {
            StructureElementAccessor e = new StructureElementAccessor(new InitialContext());
            elo = e.getStructureElementValues(param1, param2);
            return elo;
        } catch (Exception var5) {
            throw new EJBException(var5.getMessage(), var5);
        }
    }

    public CheckLeafELO getCheckLeaf(int param1) throws EJBException {
        CheckLeafELO elo = null;

        try {
            StructureElementAccessor e = new StructureElementAccessor(new InitialContext());
            elo = e.getCheckLeaf(param1);
            return elo;
        } catch (Exception var4) {
            throw new EJBException(var4.getMessage(), var4);
        }
    }

    public StructureElementELO getStructureElement(int param1) throws EJBException {
        StructureElementELO elo = null;

        try {
            StructureElementAccessor e = new StructureElementAccessor(new InitialContext());
            elo = e.getStructureElement(param1);
            return elo;
        } catch (Exception var4) {
            throw new EJBException(var4.getMessage(), var4);
        }
    }

    public StructureElementForIdsELO getStructureElementForIds(int param1, int param2) throws EJBException {
        StructureElementForIdsELO elo = null;

        try {
            StructureElementAccessor e = new StructureElementAccessor(new InitialContext());
            elo = e.getStructureElementForIds(param1, param2);
            return elo;
        } catch (Exception var5) {
            throw new EJBException(var5.getMessage(), var5);
        }
    }

    public StructureElementByVisIdELO getStructureElementByVisId(String param1, int param2) throws EJBException {
        StructureElementByVisIdELO elo = null;

        try {
            StructureElementAccessor e = new StructureElementAccessor(new InitialContext());
            elo = e.getStructureElementByVisId(param1, param2);
            return elo;
        } catch (Exception var5) {
            throw new EJBException(var5.getMessage(), var5);
        }
    }

    public RespAreaImmediateChildrenELO getRespAreaImmediateChildren(int param1, int param2) throws EJBException {
        RespAreaImmediateChildrenELO elo = null;

        try {
            StructureElementAccessor e = new StructureElementAccessor(new InitialContext());
            elo = e.getRespAreaImmediateChildren(param1, param2);
            return elo;
        } catch (Exception var5) {
            throw new EJBException(var5.getMessage(), var5);
        }
    }

    public AllDisabledLeafELO getAllDisabledLeaf(int param1) throws EJBException {
        AllDisabledLeafELO elo = null;

        try {
            StructureElementAccessor e = new StructureElementAccessor(new InitialContext());
            elo = e.getAllDisabledLeaf(param1);
            return elo;
        } catch (Exception var4) {
            throw new EJBException(var4.getMessage(), var4);
        }
    }

    public AllNotPlannableELO getAllNotPlannable(int param1) throws EJBException {
        AllNotPlannableELO elo = null;

        try {
            StructureElementAccessor e = new StructureElementAccessor(new InitialContext());
            elo = e.getAllNotPlannable(param1);
            return elo;
        } catch (Exception var4) {
            throw new EJBException(var4.getMessage(), var4);
        }
    }

    public AllDisabledLeafandNotPlannableELO getAllDisabledLeafandNotPlannable(int param1) throws EJBException {
        AllDisabledLeafandNotPlannableELO elo = null;

        try {
            StructureElementAccessor e = new StructureElementAccessor(new InitialContext());
            elo = e.getAllDisabledLeafandNotPlannable(param1);
            return elo;
        } catch (Exception var4) {
            throw new EJBException(var4.getMessage(), var4);
        }
    }

    public LeavesForParentELO getLeavesForParent(int param1, int param2, int param3, int param4, int param5) throws EJBException {
        LeavesForParentELO elo = null;

        try {
            StructureElementAccessor e = new StructureElementAccessor(new InitialContext());
            elo = e.getLeavesForParent(param1, param2, param3, param4, param5);
            return elo;
        } catch (Exception var8) {
            throw new EJBException(var8.getMessage(), var8);
        }
    }

    public ChildrenForParentELO getChildrenForParent(int param1, int param2, int param3, int param4, int param5) throws EJBException {
        ChildrenForParentELO elo = null;

        try {
            StructureElementAccessor e = new StructureElementAccessor(new InitialContext());
            elo = e.getChildrenForParent(param1, param2, param3, param4, param5);
            return elo;
        } catch (Exception var8) {
            throw new EJBException(var8.getMessage(), var8);
        }
    }

    public ReportLeavesForParentELO getReportLeavesForParent(int param1, int param2, int param3, int param4, int param5) throws EJBException {
        ReportLeavesForParentELO elo = null;

        try {
            StructureElementAccessor e = new StructureElementAccessor(new InitialContext());
            elo = e.getReportLeavesForParent(param1, param2, param3, param4, param5);
            return elo;
        } catch (Exception var8) {
            throw new EJBException(var8.getMessage(), var8);
        }
    }

    public ReportChildrenForParentToRelativeDepthELO getReportChildrenForParentToRelativeDepth(int param1, int param2, int param3, int param4, int param5, int param6, int param7, int param8) throws EJBException {
        ReportChildrenForParentToRelativeDepthELO elo = null;

        try {
            StructureElementAccessor e = new StructureElementAccessor(new InitialContext());
            elo = e.getReportChildrenForParentToRelativeDepth(param1, param2, param3, param4, param5, param6, param7, param8);
            return elo;
        } catch (Exception var11) {
            throw new EJBException(var11.getMessage(), var11);
        }
    }

    public BudgetHierarchyElementELO getBudgetHierarchyElement(int param1) throws EJBException {
        BudgetHierarchyElementELO elo = null;

        try {
            StructureElementAccessor e = new StructureElementAccessor(new InitialContext());
            elo = e.getBudgetHierarchyElement(param1);
            return elo;
        } catch (Exception var4) {
            throw new EJBException(var4.getMessage(), var4);
        }
    }

    public BudgetLocationElementForModelELO getBudgetLocationElementForModel(int param1, int param2) throws EJBException {
        BudgetLocationElementForModelELO elo = null;

        try {
            StructureElementAccessor e = new StructureElementAccessor(new InitialContext());
            elo = e.getBudgetLocationElementForModel(param1, param2);
            return elo;
        } catch (Exception var5) {
            throw new EJBException(var5.getMessage(), var5);
        }
    }

    public AllDimensionsELO getAllDimensions() throws EJBException {
        AllDimensionsELO elo = null;

        try {
            DimensionAccessor e = new DimensionAccessor(new InitialContext());
            elo = e.getAllDimensions();
            return elo;
        } catch (Exception var3) {
            throw new EJBException(var3.getMessage(), var3);
        }
    }

    public AllDimensionsELO getDimensionsForLoggedUser(int userId) throws EJBException {
        AllDimensionsELO elo = null;

        try {
            DimensionAccessor e = new DimensionAccessor(new InitialContext());
            elo = e.getDimensionsForLoggedUser(userId);
            return elo;
        } catch (Exception var3) {
            throw new EJBException(var3.getMessage(), var3);
        }
    }

    public AvailableDimensionsELO getAvailableDimensions() throws EJBException {
        AvailableDimensionsELO elo = null;

        try {
            DimensionAccessor e = new DimensionAccessor(new InitialContext());
            elo = e.getAvailableDimensions();
            return elo;
        } catch (Exception var3) {
            throw new EJBException(var3.getMessage(), var3);
        }
    }

    public ImportableDimensionsELO getImportableDimensions() throws EJBException {
        ImportableDimensionsELO elo = null;

        try {
            DimensionAccessor e = new DimensionAccessor(new InitialContext());
            elo = e.getImportableDimensions();
            return elo;
        } catch (Exception var3) {
            throw new EJBException(var3.getMessage(), var3);
        }
    }

    public AllDimensionsForModelELO getAllDimensionsForModel(int param1) throws EJBException {
        AllDimensionsForModelELO elo = null;

        try {
            DimensionAccessor e = new DimensionAccessor(new InitialContext());
            elo = e.getAllDimensionsForModel(param1);
            return elo;
        } catch (Exception var4) {
            throw new EJBException(var4.getMessage(), var4);
        }
    }

    public AllDimensionElementsELO getAllDimensionElements() throws EJBException {
        AllDimensionElementsELO elo = null;

        try {
            DimensionAccessor e = new DimensionAccessor(new InitialContext());
            elo = e.getAllDimensionElements();
            return elo;
        } catch (Exception var3) {
            throw new EJBException(var3.getMessage(), var3);
        }
    }

    public AllDimensionElementsForDimensionELO getAllDimensionElementsForDimension(int param1) throws EJBException {
        AllDimensionElementsForDimensionELO elo = null;

        try {
            DimensionAccessor e = new DimensionAccessor(new InitialContext());
            elo = e.getAllDimensionElementsForDimension(param1);
            return elo;
        } catch (Exception var4) {
            throw new EJBException(var4.getMessage(), var4);
        }
    }

    public AllDimensionElementsForModelELO getAllDimensionElementsForModels(List<Integer> modelIds) throws EJBException {
        AllDimensionElementsForModelELO elo = null;
        try {
            DimensionAccessor e = new DimensionAccessor(new InitialContext());
            elo = e.getAllDimensionElementsForModels(modelIds);
            return elo;
        } catch (Exception var4) {
            throw new EJBException(var4.getMessage(), var4);
        }
    }

    public AllHierarchysELO getAllHierarchys() throws EJBException {
        AllHierarchysELO elo = null;

        try {
            DimensionAccessor e = new DimensionAccessor(new InitialContext());
            elo = e.getAllHierarchys();
            return elo;
        } catch (Exception var3) {
            throw new EJBException(var3.getMessage(), var3);
        }
    }

    public AllHierarchysELO getHierarchiesForLoggedUser(int userId) throws EJBException {
        try {
            return new HierarchyDAO().getHierarchiesForLoggedUser(userId);
        } catch (Exception var3) {
            throw new EJBException(var3.getMessage(), var3);
        }
    }

    public SelectedHierarchysELO getSelectedHierarchys() throws EJBException {
        SelectedHierarchysELO elo = null;

        try {
            DimensionAccessor e = new DimensionAccessor(new InitialContext());
            elo = e.getSelectedHierarchys();
            return elo;
        } catch (Exception var3) {
            throw new EJBException(var3.getMessage(), var3);
        }
    }

    public ImportableHierarchiesELO getImportableHierarchies(int param1) throws EJBException {
        ImportableHierarchiesELO elo = null;

        try {
            DimensionAccessor e = new DimensionAccessor(new InitialContext());
            elo = e.getImportableHierarchies(param1);
            return elo;
        } catch (Exception var4) {
            throw new EJBException(var4.getMessage(), var4);
        }
    }

    public HierarcyDetailsFromDimIdELO getHierarcyDetailsFromDimId(int param1) throws EJBException {
        HierarcyDetailsFromDimIdELO elo = null;

        try {
            DimensionAccessor e = new DimensionAccessor(new InitialContext());
            elo = e.getHierarcyDetailsFromDimId(param1);
            return elo;
        } catch (Exception var4) {
            throw new EJBException(var4.getMessage(), var4);
        }
    }

    public HierarcyDetailsIncRootNodeFromDimIdELO getHierarcyDetailsIncRootNodeFromDimId(int param1) throws EJBException {
        HierarcyDetailsIncRootNodeFromDimIdELO elo = null;

        try {
            DimensionAccessor e = new DimensionAccessor(new InitialContext());
            elo = e.getHierarcyDetailsIncRootNodeFromDimId(param1);
            return elo;
        } catch (Exception var4) {
            throw new EJBException(var4.getMessage(), var4);
        }
    }

    public CalendarForModelELO getCalendarForModel(int param1) throws EJBException {
        CalendarForModelELO elo = null;

        try {
            DimensionAccessor e = new DimensionAccessor(new InitialContext());
            elo = e.getCalendarForModel(param1);
            return elo;
        } catch (Exception var4) {
            throw new EJBException(var4.getMessage(), var4);
        }
    }

    public CalendarForModelVisIdELO getCalendarForModelVisId(String param1) throws EJBException {
        CalendarForModelVisIdELO elo = null;

        try {
            DimensionAccessor e = new DimensionAccessor(new InitialContext());
            elo = e.getCalendarForModelVisId(param1);
            return elo;
        } catch (Exception var4) {
            throw new EJBException(var4.getMessage(), var4);
        }
    }

    public CalendarForFinanceCubeELO getCalendarForFinanceCube(int param1) throws EJBException {
        CalendarForFinanceCubeELO elo = null;

        try {
            DimensionAccessor e = new DimensionAccessor(new InitialContext());
            elo = e.getCalendarForFinanceCube(param1);
            return elo;
        } catch (Exception var4) {
            throw new EJBException(var4.getMessage(), var4);
        }
    }

    public HierarachyElementELO getHierarachyElement(int param1) throws EJBException {
        HierarachyElementELO elo = null;

        try {
            DimensionAccessor e = new DimensionAccessor(new InitialContext());
            elo = e.getHierarachyElement(param1);
            return elo;
        } catch (Exception var4) {
            throw new EJBException(var4.getMessage(), var4);
        }
    }

    public AugHierarachyElementELO getAugHierarachyElement(int param1) throws EJBException {
        AugHierarachyElementELO elo = null;

        try {
            DimensionAccessor e = new DimensionAccessor(new InitialContext());
            elo = e.getAugHierarachyElement(param1);
            return elo;
        } catch (Exception var4) {
            throw new EJBException(var4.getMessage(), var4);
        }
    }

    public AllFeedsForDimensionElementELO getAllFeedsForDimensionElement(int param1) throws EJBException {
        AllFeedsForDimensionElementELO elo = null;

        try {
            DimensionAccessor e = new DimensionAccessor(new InitialContext());
            elo = e.getAllFeedsForDimensionElement(param1);
            return elo;
        } catch (Exception var4) {
            throw new EJBException(var4.getMessage(), var4);
        }
    }

    public AllUsersELO getAllUsers() throws EJBException {
        AllUsersELO elo = null;

        try {
            UserAccessor e = new UserAccessor(new InitialContext());
            elo = e.getAllUsers();
            return elo;
        } catch (Exception var3) {
            throw new EJBException(var3.getMessage(), var3);
        }
    }
    
    public AllRevisionsELO getAllRevisions() throws EJBException {
        AllRevisionsELO elo = null;

        try {
            UserAccessor e = new UserAccessor(new InitialContext());
            elo = e.getAllRevisions();
            return elo;
        } catch (Exception var3) {
            throw new EJBException(var3.getMessage(), var3);
        }
    }

    public SecurityStringsForUserELO getSecurityStringsForUser(int param1) throws EJBException {
        SecurityStringsForUserELO elo = null;

        try {
            UserAccessor e = new UserAccessor(new InitialContext());
            elo = e.getSecurityStringsForUser(param1);
            return elo;
        } catch (Exception var4) {
            throw new EJBException(var4.getMessage(), var4);
        }
    }

    public AllUsersExportELO getAllUsersExport() throws EJBException {
        AllUsersExportELO elo = null;

        try {
            UserAccessor e = new UserAccessor(new InitialContext());
            elo = e.getAllUsersExport();
            return elo;
        } catch (Exception var3) {
            throw new EJBException(var3.getMessage(), var3);
        }
    }

    public AllUserAttributesELO getAllUserAttributes() throws EJBException {
        AllUserAttributesELO elo = null;

        try {
            UserAccessor e = new UserAccessor(new InitialContext());
            elo = e.getAllUserAttributes();
            return elo;
        } catch (Exception var3) {
            throw new EJBException(var3.getMessage(), var3);
        }
    }

    public AllNonDisabledUsersELO getAllNonDisabledUsers() throws EJBException {
        AllNonDisabledUsersELO elo = null;

        try {
            UserAccessor e = new UserAccessor(new InitialContext());
            elo = e.getAllNonDisabledUsers();
            return elo;
        } catch (Exception var3) {
            throw new EJBException(var3.getMessage(), var3);
        }
    }

    public UserMessageAttributesELO getUserMessageAttributes() throws EJBException {
        UserMessageAttributesELO elo = null;

        try {
            UserAccessor e = new UserAccessor(new InitialContext());
            elo = e.getUserMessageAttributes();
            return elo;
        } catch (Exception var3) {
            throw new EJBException(var3.getMessage(), var3);
        }
    }

    public UserMessageAttributesForIdELO getUserMessageAttributesForId(int param1) throws EJBException {
        UserMessageAttributesForIdELO elo = null;

        try {
            UserAccessor e = new UserAccessor(new InitialContext());
            elo = e.getUserMessageAttributesForId(param1);
            return elo;
        } catch (Exception var4) {
            throw new EJBException(var4.getMessage(), var4);
        }
    }

    public UserMessageAttributesForNameELO getUserMessageAttributesForName(String param1) throws EJBException {
        UserMessageAttributesForNameELO elo = null;

        try {
            UserAccessor e = new UserAccessor(new InitialContext());
            elo = e.getUserMessageAttributesForName(param1);
            return elo;
        } catch (Exception var4) {
            throw new EJBException(var4.getMessage(), var4);
        }
    }

    public FinanceSystemUserNameELO getFinanceSystemUserName(int param1) throws EJBException {
        FinanceSystemUserNameELO elo = null;

        try {
            UserAccessor e = new UserAccessor(new InitialContext());
            elo = e.getFinanceSystemUserName(param1);
            return elo;
        } catch (Exception var4) {
            throw new EJBException(var4.getMessage(), var4);
        }
    }

    public UsersWithSecurityStringELO getUsersWithSecurityString(String param1) throws EJBException {
        UsersWithSecurityStringELO elo = null;

        try {
            UserAccessor e = new UserAccessor(new InitialContext());
            elo = e.getUsersWithSecurityString(param1);
            return elo;
        } catch (Exception var4) {
            throw new EJBException(var4.getMessage(), var4);
        }
    }

    public AllRolesForUsersELO getAllRolesForUsers() throws EJBException {
        AllRolesForUsersELO elo = null;

        try {
            UserAccessor e = new UserAccessor(new InitialContext());
            elo = e.getAllRolesForUsers();
            return elo;
        } catch (Exception var3) {
            throw new EJBException(var3.getMessage(), var3);
        }
    }

    public AllRolesELO getAllRoles() throws EJBException {
        AllRolesELO elo = null;

        try {
            RoleAccessor e = new RoleAccessor(new InitialContext());
            elo = e.getAllRoles();
            return elo;
        } catch (Exception var3) {
            throw new EJBException(var3.getMessage(), var3);
        }
    }

    public AllHiddenRolesELO getAllHiddenRoles() throws EJBException {
        AllHiddenRolesELO elo = null;

        try {
            RoleAccessor e = new RoleAccessor(new InitialContext());
            elo = e.getAllHiddenRoles();
            return elo;
        } catch (Exception var3) {
            throw new EJBException(var3.getMessage(), var3);
        }
    }

    public AllRolesForUserELO getAllRolesForUser(int param1) throws EJBException {
        AllRolesForUserELO elo = null;

        try {
            RoleAccessor e = new RoleAccessor(new InitialContext());
            elo = e.getAllRolesForUser(param1);
            return elo;
        } catch (Exception var4) {
            throw new EJBException(var4.getMessage(), var4);
        }
    }

    public AllRolesForUserELO getAllHiddenRolesForUser(int param1) throws EJBException {
        AllRolesForUserELO elo = null;

        try {
            RoleAccessor e = new RoleAccessor(new InitialContext());
            elo = e.getAllHiddenRolesForUser(param1);
            return elo;
        } catch (Exception var4) {
            throw new EJBException(var4.getMessage(), var4);
        }
    }

    public AllSecurityRolesELO getAllSecurityRoles() throws EJBException {
        AllSecurityRolesELO elo = null;

        try {
            RoleSecurityAccessor e = new RoleSecurityAccessor(new InitialContext());
            elo = e.getAllSecurityRoles();
            return elo;
        } catch (Exception var3) {
            throw new EJBException(var3.getMessage(), var3);
        }
    }

    public AllSecurityRolesForRoleELO getAllSecurityRolesForRole(int param1) throws EJBException {
        AllSecurityRolesForRoleELO elo = null;

        try {
            RoleSecurityAccessor e = new RoleSecurityAccessor(new InitialContext());
            elo = e.getAllSecurityRolesForRole(param1);
            return elo;
        } catch (Exception var4) {
            throw new EJBException(var4.getMessage(), var4);
        }
    }

    public UserPreferencesForUserELO getUserPreferencesForUser(int param1) throws EJBException {
        UserPreferencesForUserELO elo = null;

        try {
            UserAccessor e = new UserAccessor(new InitialContext());
            elo = e.getUserPreferencesForUser(param1);
            return elo;
        } catch (Exception var4) {
            throw new EJBException(var4.getMessage(), var4);
        }
    }

    public AllBudgetInstructionsELO getAllBudgetInstructions() throws EJBException {
        AllBudgetInstructionsELO elo = null;

        try {
            BudgetInstructionAccessor e = new BudgetInstructionAccessor(new InitialContext());
            elo = e.getAllBudgetInstructions();
            return elo;
        } catch (Exception var3) {
            throw new EJBException(var3.getMessage(), var3);
        }
    }

    public AllBudgetInstructionsWebELO getAllBudgetInstructionsWeb() throws EJBException {
        AllBudgetInstructionsWebELO elo = null;

        try {
            BudgetInstructionAccessor e = new BudgetInstructionAccessor(new InitialContext());
            elo = e.getAllBudgetInstructionsWeb();
            return elo;
        } catch (Exception var3) {
            throw new EJBException(var3.getMessage(), var3);
        }
    }

    public AllBudgetInstructionsForModelELO getAllBudgetInstructionsForModel(int param1) throws EJBException {
        AllBudgetInstructionsForModelELO elo = null;

        try {
            BudgetInstructionAccessor e = new BudgetInstructionAccessor(new InitialContext());
            elo = e.getAllBudgetInstructionsForModel(param1);
            return elo;
        } catch (Exception var4) {
            throw new EJBException(var4.getMessage(), var4);
        }
    }

    public AllBudgetInstructionsForCycleELO getAllBudgetInstructionsForCycle(int param1) throws EJBException {
        AllBudgetInstructionsForCycleELO elo = null;

        try {
            BudgetInstructionAccessor e = new BudgetInstructionAccessor(new InitialContext());
            elo = e.getAllBudgetInstructionsForCycle(param1);
            return elo;
        } catch (Exception var4) {
            throw new EJBException(var4.getMessage(), var4);
        }
    }

    public AllBudgetInstructionsForLocationELO getAllBudgetInstructionsForLocation(int param1) throws EJBException {
        AllBudgetInstructionsForLocationELO elo = null;

        try {
            BudgetInstructionAccessor e = new BudgetInstructionAccessor(new InitialContext());
            elo = e.getAllBudgetInstructionsForLocation(param1);
            return elo;
        } catch (Exception var4) {
            throw new EJBException(var4.getMessage(), var4);
        }
    }

    public AllBudgetInstructionAssignmentsELO getAllBudgetInstructionAssignments() throws EJBException {
        AllBudgetInstructionAssignmentsELO elo = null;

        try {
            BudgetInstructionAccessor e = new BudgetInstructionAccessor(new InitialContext());
            elo = e.getAllBudgetInstructionAssignments();
            return elo;
        } catch (Exception var3) {
            throw new EJBException(var3.getMessage(), var3);
        }
    }

    public AllWebTasksELO getAllWebTasks() throws EJBException {
        AllWebTasksELO elo = null;

        try {
            TaskAccessor e = new TaskAccessor(new InitialContext());
            elo = e.getAllWebTasks();
            return elo;
        } catch (Exception var3) {
            throw new EJBException(var3.getMessage(), var3);
        }
    }

    public AllWebTasksForUserELO getAllWebTasksForUser(String param1) throws EJBException {
        AllWebTasksForUserELO elo = null;

        try {
            TaskAccessor e = new TaskAccessor(new InitialContext());
            elo = e.getAllWebTasksForUser(param1);
            return elo;
        } catch (Exception var4) {
            throw new EJBException(var4.getMessage(), var4);
        }
    }

    public WebTasksDetailsELO getWebTasksDetails(int param1) throws EJBException {
        WebTasksDetailsELO elo = null;

        try {
            TaskAccessor e = new TaskAccessor(new InitialContext());
            elo = e.getWebTasksDetails(param1);
            return elo;
        } catch (Exception var4) {
            throw new EJBException(var4.getMessage(), var4);
        }
    }

    public AllSystemPropertysELO getAllSystemPropertys() throws EJBException {
        AllSystemPropertysELO elo = null;

        try {
            SystemPropertyAccessor e = new SystemPropertyAccessor(new InitialContext());
            elo = e.getAllSystemPropertys();
            return elo;
        } catch (Exception var3) {
            throw new EJBException(var3.getMessage(), var3);
        }
    }

    public AllSystemPropertysUncachedELO getAllSystemPropertysUncached() throws EJBException {
        AllSystemPropertysUncachedELO elo = null;

        try {
            SystemPropertyAccessor e = new SystemPropertyAccessor(new InitialContext());
            elo = e.getAllSystemPropertysUncached();
            return elo;
        } catch (Exception var3) {
            throw new EJBException(var3.getMessage(), var3);
        }
    }

    public AllMailPropsELO getAllMailProps() throws EJBException {
        AllMailPropsELO elo = null;

        try {
            SystemPropertyAccessor e = new SystemPropertyAccessor(new InitialContext());
            elo = e.getAllMailProps();
            return elo;
        } catch (Exception var3) {
            throw new EJBException(var3.getMessage(), var3);
        }
    }

    public SystemPropertyELO getSystemProperty(String param1) throws EJBException {
        SystemPropertyELO elo = null;

        try {
            SystemPropertyAccessor e = new SystemPropertyAccessor(new InitialContext());
            elo = e.getSystemProperty(param1);
            return elo;
        } catch (Exception var4) {
            throw new EJBException(var4.getMessage(), var4);
        }
    }

    public WebSystemPropertyELO getWebSystemProperty(String param1) throws EJBException {
        WebSystemPropertyELO elo = null;

        try {
            SystemPropertyAccessor e = new SystemPropertyAccessor(new InitialContext());
            elo = e.getWebSystemProperty(param1);
            return elo;
        } catch (Exception var4) {
            throw new EJBException(var4.getMessage(), var4);
        }
    }

    public AllXmlFormsELO getAllXmlForms() throws EJBException {
        AllXmlFormsELO elo = null;

        try {
            XmlFormAccessor e = new XmlFormAccessor(new InitialContext());
            elo = e.getAllXmlForms();
            return elo;
        } catch (Exception var3) {
            throw new EJBException(var3.getMessage(), var3);
        }
    }

    public AllXmlFormsELO getAllXmlFormsForLoggedUser(int userId) throws EJBException {
        AllXmlFormsELO elo = null;

        try {
            XmlFormAccessor e = new XmlFormAccessor(new InitialContext());
            elo = e.getAllXmlFormsForLoggedUser(userId);
            return elo;
        } catch (Exception var3) {
            throw new EJBException(var3.getMessage(), var3);
        }
    }

    public AllFinXmlFormsELO getAllFinXmlForms(int userId) throws EJBException {
        AllFinXmlFormsELO elo = null;

        try {
            XmlFormAccessor e = new XmlFormAccessor(new InitialContext());
            elo = e.getAllFinXmlForms(userId);
            return elo;
        } catch (Exception var3) {
            throw new EJBException(var3.getMessage(), var3);
        }
    }

    public AllFFXmlFormsELO getAllFFXmlForms() throws EJBException {
        AllFFXmlFormsELO elo = null;

        try {
            XmlFormAccessor e = new XmlFormAccessor(new InitialContext());
            elo = e.getAllFFXmlForms();
            return elo;
        } catch (Exception var3) {
            throw new EJBException(var3.getMessage(), var3);
        }
    }

    public AllFFXmlFormsELO getAllFFXmlFormsForLoggedUser(int userId) throws EJBException {
        AllFFXmlFormsELO elo = null;

        try {
            XmlFormAccessor e = new XmlFormAccessor(new InitialContext());
            elo = e.getAllFFXmlFormsForLoggedUser(userId);
            return elo;
        } catch (Exception var3) {
            throw new EJBException(var3.getMessage(), var3);
        }
    }

    public AllXcellXmlFormsELO getAllXcellXmlForms() throws EJBException {
        AllXcellXmlFormsELO elo = null;

        try {
            XmlFormAccessor e = new XmlFormAccessor(new InitialContext());
            elo = e.getAllXcellXmlForms();
            return elo;
        } catch (Exception var3) {
            throw new EJBException(var3.getMessage(), var3);
        }
    }

    public AllXcellXmlFormsELO getAllXcellXmlFormsForLoggedUser(int userId) throws EJBException {
        AllXcellXmlFormsELO elo = null;

        try {
            XmlFormAccessor e = new XmlFormAccessor(new InitialContext());
            elo = e.getAllXcellXmlFormsForLoggedUser(userId);
            return elo;
        } catch (Exception var3) {
            throw new EJBException(var3.getMessage(), var3);
        }
    }

    public AllMassVirementXmlFormsELO getAllMassVirementXmlForms() throws EJBException {
        AllMassVirementXmlFormsELO elo = null;

        try {
            XmlFormAccessor e = new XmlFormAccessor(new InitialContext());
            elo = e.getAllMassVirementXmlForms();
            return elo;
        } catch (Exception var3) {
            throw new EJBException(var3.getMessage(), var3);
        }
    }

    public AllMassVirementXmlFormsELO getAllMassVirementXmlFormsForLoggedUser(int userId) throws EJBException {
        AllMassVirementXmlFormsELO elo = null;

        try {
            XmlFormAccessor e = new XmlFormAccessor(new InitialContext());
            elo = e.getAllMassVirementXmlFormsForLoggedUser(userId);
            return elo;
        } catch (Exception var3) {
            throw new EJBException(var3.getMessage(), var3);
        }
    }

    public AllFinanceXmlFormsELO getAllFinanceXmlForms() throws EJBException {
        AllFinanceXmlFormsELO elo = null;

        try {
            XmlFormAccessor e = new XmlFormAccessor(new InitialContext());
            elo = e.getAllFinanceXmlForms();
            return elo;
        } catch (Exception var3) {
            throw new EJBException(var3.getMessage(), var3);
        }
    }

    public AllFinanceAndFlatFormsELO getAllFinanceAndFlatForms() throws EJBException {
        AllFinanceAndFlatFormsELO elo = null;

        try {
            XmlFormAccessor e = new XmlFormAccessor(new InitialContext());
            elo = e.getAllFinanceAndFlatForms();
            return elo;
        } catch (Exception var3) {
            throw new EJBException(var3.getMessage(), var3);
        }
    }

    public AllFinanceXmlFormsForModelELO getAllFinanceXmlFormsForModel(int param1) throws EJBException {
        AllFinanceXmlFormsForModelELO elo = null;

        try {
            XmlFormAccessor e = new XmlFormAccessor(new InitialContext());
            elo = e.getAllFinanceXmlFormsForModel(param1);
            return elo;
        } catch (Exception var4) {
            throw new EJBException(var4.getMessage(), var4);
        }
    }

    public AllFinanceAndFlatFormsForModelELO getAllFinanceAndFlatFormsForModel(int param1) throws EJBException {
        AllFinanceAndFlatFormsForModelELO elo = null;

        try {
            XmlFormAccessor e = new XmlFormAccessor(new InitialContext());
            elo = e.getAllFinanceAndFlatFormsForModel(param1);
            return elo;
        } catch (Exception var4) {
            throw new EJBException(var4.getMessage(), var4);
        }
    }

    public AllFinanceXmlFormsAndDataTypesForModelELO getAllFinanceXmlFormsAndDataTypesForModel(int param1) throws EJBException {
        AllFinanceXmlFormsAndDataTypesForModelELO elo = null;

        try {
            XmlFormAccessor e = new XmlFormAccessor(new InitialContext());
            elo = e.getAllFinanceXmlFormsAndDataTypesForModel(param1);
            return elo;
        } catch (Exception var4) {
            throw new EJBException(var4.getMessage(), var4);
        }
    }

    public AllXmlFormsELO getAllXmlFormsForModel(int modelId) throws EJBException {
        AllXmlFormsELO elo = null;

        try {
            XmlFormAccessor e = new XmlFormAccessor(new InitialContext());
            elo = e.getAllXmlFormsForModel(modelId);
            return elo;
        } catch (Exception e) {
            throw new EJBException(e.getMessage(), e);
        }
    }

    public AllFixedXMLFormsELO getAllFixedXMLForms() throws EJBException {
        AllFixedXMLFormsELO elo = null;

        try {
            XmlFormAccessor e = new XmlFormAccessor(new InitialContext());
            elo = e.getAllFixedXMLForms();
            return elo;
        } catch (Exception var3) {
            throw new EJBException(var3.getMessage(), var3);
        }
    }

    public AllDynamicXMLFormsELO getAllDynamicXMLForms() throws EJBException {
        AllDynamicXMLFormsELO elo = null;

        try {
            XmlFormAccessor e = new XmlFormAccessor(new InitialContext());
            elo = e.getAllDynamicXMLForms();
            return elo;
        } catch (Exception var3) {
            throw new EJBException(var3.getMessage(), var3);
        }
    }

    public AllFlatXMLFormsELO getAllFlatXMLForms() throws EJBException {
        AllFlatXMLFormsELO elo = null;

        try {
            XmlFormAccessor e = new XmlFormAccessor(new InitialContext());
            elo = e.getAllFlatXMLForms();
            return elo;
        } catch (Exception var3) {
            throw new EJBException(var3.getMessage(), var3);
        }
    }

    public XMLFormDefinitionELO getXMLFormDefinition(int param1) throws EJBException {
        XMLFormDefinitionELO elo = null;

        try {
            XmlFormAccessor e = new XmlFormAccessor(new InitialContext());
            elo = e.getXMLFormDefinition(param1);
            return elo;
        } catch (Exception var4) {
            throw new EJBException(var4.getMessage(), var4);
        }
    }

    public XMLFormCellPickerInfoELO getXMLFormCellPickerInfo(int param1) throws EJBException {
        XMLFormCellPickerInfoELO elo = null;

        try {
            XmlFormAccessor e = new XmlFormAccessor(new InitialContext());
            elo = e.getXMLFormCellPickerInfo(param1);
            return elo;
        } catch (Exception var4) {
            throw new EJBException(var4.getMessage(), var4);
        }
    }

    public AllXMLFormUserLinkELO getAllXMLFormUserLink() throws EJBException {
        AllXMLFormUserLinkELO elo = null;

        try {
            XmlFormAccessor e = new XmlFormAccessor(new InitialContext());
            elo = e.getAllXMLFormUserLink();
            return elo;
        } catch (Exception var3) {
            throw new EJBException(var3.getMessage(), var3);
        }
    }

    public CheckXMLFormUserLinkELO getCheckXMLFormUserLink(int param1) throws EJBException {
        CheckXMLFormUserLinkELO elo = null;

        try {
            XmlFormAccessor e = new XmlFormAccessor(new InitialContext());
            elo = e.getCheckXMLFormUserLink(param1);
            return elo;
        } catch (Exception var4) {
            throw new EJBException(var4.getMessage(), var4);
        }
    }

    public AllFormNotesForBudgetLocationELO getAllFormNotesForBudgetLocation(int param1) throws EJBException {
        AllFormNotesForBudgetLocationELO elo = null;

        try {
            FormNotesAccessor e = new FormNotesAccessor(new InitialContext());
            elo = e.getAllFormNotesForBudgetLocation(param1);
            return elo;
        } catch (Exception var4) {
            throw new EJBException(var4.getMessage(), var4);
        }
    }

    public AllFormNotesForFormAndBudgetLocationELO getAllFormNotesForFormAndBudgetLocation(int param1, int param2) throws EJBException {
        AllFormNotesForFormAndBudgetLocationELO elo = null;

        try {
            FormNotesAccessor e = new FormNotesAccessor(new InitialContext());
            elo = e.getAllFormNotesForFormAndBudgetLocation(param1, param2);
            return elo;
        } catch (Exception var5) {
            throw new EJBException(var5.getMessage(), var5);
        }
    }

    public AllXmlReportsELO getAllXmlReports() throws EJBException {
        AllXmlReportsELO elo = null;

        try {
            XmlReportAccessor e = new XmlReportAccessor(new InitialContext());
            elo = e.getAllXmlReports();
            return elo;
        } catch (Exception var3) {
            throw new EJBException(var3.getMessage(), var3);
        }
    }

    public AllPublicXmlReportsELO getAllPublicXmlReports() throws EJBException {
        AllPublicXmlReportsELO elo = null;

        try {
            XmlReportAccessor e = new XmlReportAccessor(new InitialContext());
            elo = e.getAllPublicXmlReports();
            return elo;
        } catch (Exception var3) {
            throw new EJBException(var3.getMessage(), var3);
        }
    }

    public AllXmlReportsForUserELO getAllXmlReportsForUser(int param1) throws EJBException {
        AllXmlReportsForUserELO elo = null;

        try {
            XmlReportAccessor e = new XmlReportAccessor(new InitialContext());
            elo = e.getAllXmlReportsForUser(param1);
            return elo;
        } catch (Exception var4) {
            throw new EJBException(var4.getMessage(), var4);
        }
    }

    public XmlReportsForFolderELO getXmlReportsForFolder(int param1) throws EJBException {
        XmlReportsForFolderELO elo = null;

        try {
            XmlReportAccessor e = new XmlReportAccessor(new InitialContext());
            elo = e.getXmlReportsForFolder(param1);
            return elo;
        } catch (Exception var4) {
            throw new EJBException(var4.getMessage(), var4);
        }
    }

    public SingleXmlReportELO getSingleXmlReport(int param1, String param2) throws EJBException {
        SingleXmlReportELO elo = null;

        try {
            XmlReportAccessor e = new XmlReportAccessor(new InitialContext());
            elo = e.getSingleXmlReport(param1, param2);
            return elo;
        } catch (Exception var5) {
            throw new EJBException(var5.getMessage(), var5);
        }
    }

    public AllXmlReportFoldersELO getAllXmlReportFolders() throws EJBException {
        AllXmlReportFoldersELO elo = null;

        try {
            XmlReportFolderAccessor e = new XmlReportFolderAccessor(new InitialContext());
            elo = e.getAllXmlReportFolders();
            return elo;
        } catch (Exception var3) {
            throw new EJBException(var3.getMessage(), var3);
        }
    }

    public DecendentFoldersELO getDecendentFolders(int param1) throws EJBException {
        DecendentFoldersELO elo = null;

        try {
            XmlReportFolderAccessor e = new XmlReportFolderAccessor(new InitialContext());
            elo = e.getDecendentFolders(param1);
            return elo;
        } catch (Exception var4) {
            throw new EJBException(var4.getMessage(), var4);
        }
    }

    public ReportFolderWithIdELO getReportFolderWithId(int param1) throws EJBException {
        ReportFolderWithIdELO elo = null;

        try {
            XmlReportFolderAccessor e = new XmlReportFolderAccessor(new InitialContext());
            elo = e.getReportFolderWithId(param1);
            return elo;
        } catch (Exception var4) {
            throw new EJBException(var4.getMessage(), var4);
        }
    }

    public AllDataEntryProfilesELO getAllDataEntryProfiles() throws EJBException {
        AllDataEntryProfilesELO elo = null;

        try {
            UserAccessor e = new UserAccessor(new InitialContext());
            elo = e.getAllDataEntryProfiles();
            return elo;
        } catch (Exception var3) {
            throw new EJBException(var3.getMessage(), var3);
        }
    }

    public AllDataEntryProfilesForUserELO getAllDataEntryProfilesForUser(int param1, int param2, int budgetCycleId) throws EJBException {
        AllDataEntryProfilesForUserELO elo = null;

        try {
            UserAccessor e = new UserAccessor(new InitialContext());
            elo = e.getAllDataEntryProfilesForUser(param1, param2, budgetCycleId);
            return elo;
        } catch (Exception var5) {
            throw new EJBException(var5.getMessage(), var5);
        }
    }

    public AllUsersForDataEntryProfilesForModelELO getAllUsersForDataEntryProfilesForModel(int param1) throws EJBException {
        AllUsersForDataEntryProfilesForModelELO elo = null;

        try {
            UserAccessor e = new UserAccessor(new InitialContext());
            elo = e.getAllUsersForDataEntryProfilesForModel(param1);
            return elo;
        } catch (Exception var4) {
            throw new EJBException(var4.getMessage(), var4);
        }
    }

    public AllDataEntryProfilesForFormELO getAllDataEntryProfilesForForm(int param1) throws EJBException {
        AllDataEntryProfilesForFormELO elo = null;

        try {
            UserAccessor e = new UserAccessor(new InitialContext());
            elo = e.getAllDataEntryProfilesForForm(param1);
            return elo;
        } catch (Exception var4) {
            throw new EJBException(var4.getMessage(), var4);
        }
    }

    public DefaultDataEntryProfileELO getDefaultDataEntryProfile(int param1, int param2, int param3, int param4) throws EJBException {
        DefaultDataEntryProfileELO elo = null;

        try {
            UserAccessor e = new UserAccessor(new InitialContext());
            elo = e.getDefaultDataEntryProfile(param1, param2, param3, param4);
            return elo;
        } catch (Exception var6) {
            throw new EJBException(var6.getMessage(), var6);
        }
    }

    public AllDataEntryProfileHistorysELO getAllDataEntryProfileHistorys() throws EJBException {
        AllDataEntryProfileHistorysELO elo = null;

        try {
            UserAccessor e = new UserAccessor(new InitialContext());
            elo = e.getAllDataEntryProfileHistorys();
            return elo;
        } catch (Exception var3) {
            throw new EJBException(var3.getMessage(), var3);
        }
    }

    public AllUdefLookupsELO getAllUdefLookups() throws EJBException {
        AllUdefLookupsELO elo = null;

        try {
            UdefLookupAccessor e = new UdefLookupAccessor(new InitialContext());
            elo = e.getAllUdefLookups();
            return elo;
        } catch (Exception var3) {
            throw new EJBException(var3.getMessage(), var3);
        }
    }

    public AllUdefLookupsELO getAllUdefLookupsForLoggedUser(int userId) throws EJBException {
        AllUdefLookupsELO elo = null;

        try {
            UdefLookupAccessor e = new UdefLookupAccessor(new InitialContext());
            elo = e.getAllUdefLookupsForLoggedUser(userId);
            return elo;
        } catch (Exception var3) {
            throw new EJBException(var3.getMessage(), var3);
        }
    }

    public AllSecurityRangesELO getAllSecurityRanges() throws EJBException {
        AllSecurityRangesELO elo = null;

        try {
            DimensionAccessor e = new DimensionAccessor(new InitialContext());
            elo = e.getAllSecurityRanges();
            return elo;
        } catch (Exception var3) {
            throw new EJBException(var3.getMessage(), var3);
        }
    }

    public AllSecurityRangesForModelELO getAllSecurityRangesForModel(int param1) throws EJBException {
        AllSecurityRangesForModelELO elo = null;

        try {
            DimensionAccessor e = new DimensionAccessor(new InitialContext());
            elo = e.getAllSecurityRangesForModel(param1);
            return elo;
        } catch (Exception var4) {
            throw new EJBException(var4.getMessage(), var4);
        }
    }

    public AllSecurityAccessDefsELO getAllSecurityAccessDefs() throws EJBException {
        AllSecurityAccessDefsELO elo = null;

        try {
            ////ModelAccessor e = new ModelAccessor(new InitialContext());
            elo = modelAccessor.getAllSecurityAccessDefs();
            return elo;
        } catch (Exception var3) {
            throw new EJBException(var3.getMessage(), var3);
        }
    }

    public AllSecurityAccessDefsForModelELO getAllSecurityAccessDefsForModel(int param1) throws EJBException {
        AllSecurityAccessDefsForModelELO elo = null;

        try {
            ////ModelAccessor e = new ModelAccessor(new InitialContext());
            elo = modelAccessor.getAllSecurityAccessDefsForModel(param1);
            return elo;
        } catch (Exception var4) {
            throw new EJBException(var4.getMessage(), var4);
        }
    }

    public AllAccessDefsUsingRangeELO getAllAccessDefsUsingRange(int param1) throws EJBException {
        AllAccessDefsUsingRangeELO elo = null;

        try {
            ////ModelAccessor e = new ModelAccessor(new InitialContext());
            elo = modelAccessor.getAllAccessDefsUsingRange(param1);
            return elo;
        } catch (Exception var4) {
            throw new EJBException(var4.getMessage(), var4);
        }
    }

    public AllSecurityGroupsELO getAllSecurityGroups() throws EJBException {
        AllSecurityGroupsELO elo = null;

        try {
            ////ModelAccessor e = new ModelAccessor(new InitialContext());
            elo = modelAccessor.getAllSecurityGroups();
            return elo;
        } catch (Exception var3) {
            throw new EJBException(var3.getMessage(), var3);
        }
    }

    public AllSecurityGroupsUsingAccessDefELO getAllSecurityGroupsUsingAccessDef(int param1) throws EJBException {
        AllSecurityGroupsUsingAccessDefELO elo = null;

        try {
            ////ModelAccessor e = new ModelAccessor(new InitialContext());
            elo = modelAccessor.getAllSecurityGroupsUsingAccessDef(param1);
            return elo;
        } catch (Exception var4) {
            throw new EJBException(var4.getMessage(), var4);
        }
    }

    public AllSecurityGroupsForUserELO getAllSecurityGroupsForUser(int param1) throws EJBException {
        AllSecurityGroupsForUserELO elo = null;

        try {
            ////ModelAccessor e = new ModelAccessor(new InitialContext());
            elo = modelAccessor.getAllSecurityGroupsForUser(param1);
            return elo;
        } catch (Exception var4) {
            throw new EJBException(var4.getMessage(), var4);
        }
    }

    public AllCcDeploymentsELO getAllCcDeployments() throws EJBException {
        AllCcDeploymentsELO elo = null;

        try {
            ////ModelAccessor e = new ModelAccessor(new InitialContext());
            elo = modelAccessor.getAllCcDeployments();
            return elo;
        } catch (Exception var3) {
            throw new EJBException(var3.getMessage(), var3);
        }
    }

    public CcDeploymentsForLookupTableELO getCcDeploymentsForLookupTable(String param1) throws EJBException {
        CcDeploymentsForLookupTableELO elo = null;

        try {
            //ModelAccessor e = new ModelAccessor(new InitialContext());
            elo = modelAccessor.getCcDeploymentsForLookupTable(param1);
            return elo;
        } catch (Exception var4) {
            throw new EJBException(var4.getMessage(), var4);
        }
    }

    public CcDeploymentsForXmlFormELO getCcDeploymentsForXmlForm(int param1) throws EJBException {
        CcDeploymentsForXmlFormELO elo = null;

        try {
            //ModelAccessor e = new ModelAccessor(new InitialContext());
            elo = modelAccessor.getCcDeploymentsForXmlForm(param1);
            return elo;
        } catch (Exception var4) {
            throw new EJBException(var4.getMessage(), var4);
        }
    }

    public CcDeploymentsForModelELO getCcDeploymentsForModel(int param1) throws EJBException {
        CcDeploymentsForModelELO elo = null;

        try {
            //ModelAccessor e = new ModelAccessor(new InitialContext());
            elo = modelAccessor.getCcDeploymentsForModel(param1);
            return elo;
        } catch (Exception var4) {
            throw new EJBException(var4.getMessage(), var4);
        }
    }

    public CcDeploymentCellPickerInfoELO getCcDeploymentCellPickerInfo(int param1) throws EJBException {
        CcDeploymentCellPickerInfoELO elo = null;

        try {
            //ModelAccessor e = new ModelAccessor(new InitialContext());
            elo = modelAccessor.getCcDeploymentCellPickerInfo(param1);
            return elo;
        } catch (Exception var4) {
            throw new EJBException(var4.getMessage(), var4);
        }
    }

    public CcDeploymentXMLFormTypeELO getCcDeploymentXMLFormType(int param1) throws EJBException {
        CcDeploymentXMLFormTypeELO elo = null;

        try {
            //ModelAccessor e = new ModelAccessor(new InitialContext());
            elo = modelAccessor.getCcDeploymentXMLFormType(param1);
            return elo;
        } catch (Exception var4) {
            throw new EJBException(var4.getMessage(), var4);
        }
    }

    public AllCellCalcsELO getAllCellCalcs() throws EJBException {
        AllCellCalcsELO elo = null;

        try {
            //ModelAccessor e = new ModelAccessor(new InitialContext());
            elo = modelAccessor.getAllCellCalcs();
            return elo;
        } catch (Exception var3) {
            throw new EJBException(var3.getMessage(), var3);
        }
    }

    public CellCalcIntegrityELO getCellCalcIntegrity() throws EJBException {
        CellCalcIntegrityELO elo = null;

        try {
            //ModelAccessor e = new ModelAccessor(new InitialContext());
            elo = modelAccessor.getCellCalcIntegrity();
            return elo;
        } catch (Exception var3) {
            throw new EJBException(var3.getMessage(), var3);
        }
    }

    public AllCellCalcAssocsELO getAllCellCalcAssocs() throws EJBException {
        AllCellCalcAssocsELO elo = null;

        try {
            //ModelAccessor e = new ModelAccessor(new InitialContext());
            elo = modelAccessor.getAllCellCalcAssocs();
            return elo;
        } catch (Exception var3) {
            throw new EJBException(var3.getMessage(), var3);
        }
    }

    public AllChangeMgmtsELO getAllChangeMgmts() throws EJBException {
        AllChangeMgmtsELO elo = null;

        try {
            ChangeMgmtAccessor e = new ChangeMgmtAccessor(new InitialContext());
            elo = e.getAllChangeMgmts();
            return elo;
        } catch (Exception var3) {
            throw new EJBException(var3.getMessage(), var3);
        }
    }

    public AllChangeMgmtsForModelELO getAllChangeMgmtsForModel(int param1) throws EJBException {
        AllChangeMgmtsForModelELO elo = null;

        try {
            ChangeMgmtAccessor e = new ChangeMgmtAccessor(new InitialContext());
            elo = e.getAllChangeMgmtsForModel(param1);
            return elo;
        } catch (Exception var4) {
            throw new EJBException(var4.getMessage(), var4);
        }
    }

    public AllChangeMgmtsForModelWithXMLELO getAllChangeMgmtsForModelWithXML(int param1) throws EJBException {
        AllChangeMgmtsForModelWithXMLELO elo = null;

        try {
            ChangeMgmtAccessor e = new ChangeMgmtAccessor(new InitialContext());
            elo = e.getAllChangeMgmtsForModelWithXML(param1);
            return elo;
        } catch (Exception var4) {
            throw new EJBException(var4.getMessage(), var4);
        }
    }

    public AllImpExpHdrsELO getAllImpExpHdrs() throws EJBException {
        AllImpExpHdrsELO elo = null;

        try {
            ImpExpHdrAccessor e = new ImpExpHdrAccessor(new InitialContext());
            elo = e.getAllImpExpHdrs();
            return elo;
        } catch (Exception var3) {
            throw new EJBException(var3.getMessage(), var3);
        }
    }

    public AllReportsELO getAllReports() throws EJBException {
        AllReportsELO elo = null;

        try {
            ReportAccessor e = new ReportAccessor(new InitialContext());
            elo = e.getAllReports();
            return elo;
        } catch (Exception var3) {
            throw new EJBException(var3.getMessage(), var3);
        }
    }

    public AllReportsForUserELO getAllReportsForUser(int param1) throws EJBException {
        AllReportsForUserELO elo = null;

        try {
            ReportAccessor e = new ReportAccessor(new InitialContext());
            elo = e.getAllReportsForUser(param1);
            return elo;
        } catch (Exception var4) {
            throw new EJBException(var4.getMessage(), var4);
        }
    }

    public AllReportsForAdminELO getAllReportsForAdmin() throws EJBException {
        AllReportsForAdminELO elo = null;

        try {
            ReportAccessor e = new ReportAccessor(new InitialContext());
            elo = e.getAllReportsForAdmin();
            return elo;
        } catch (Exception var3) {
            throw new EJBException(var3.getMessage(), var3);
        }
    }

    public WebReportDetailsELO getWebReportDetails(int param1) throws EJBException {
        WebReportDetailsELO elo = null;

        try {
            ReportAccessor e = new ReportAccessor(new InitialContext());
            elo = e.getWebReportDetails(param1);
            return elo;
        } catch (Exception var4) {
            throw new EJBException(var4.getMessage(), var4);
        }
    }

    public AllVirementCategorysELO getAllVirementCategorys() throws EJBException {
        AllVirementCategorysELO elo = null;

        try {
            //ModelAccessor e = new ModelAccessor(new InitialContext());
            elo = modelAccessor.getAllVirementCategorys();
            return elo;
        } catch (Exception var3) {
            throw new EJBException(var3.getMessage(), var3);
        }
    }

    public LocationsForCategoryELO getLocationsForCategory(int param1) throws EJBException {
        LocationsForCategoryELO elo = null;

        try {
            //ModelAccessor e = new ModelAccessor(new InitialContext());
            elo = modelAccessor.getLocationsForCategory(param1);
            return elo;
        } catch (Exception var4) {
            throw new EJBException(var4.getMessage(), var4);
        }
    }

    public AccountsForCategoryELO getAccountsForCategory(int param1) throws EJBException {
        AccountsForCategoryELO elo = null;

        try {
            //ModelAccessor e = new ModelAccessor(new InitialContext());
            elo = modelAccessor.getAccountsForCategory(param1);
            return elo;
        } catch (Exception var4) {
            throw new EJBException(var4.getMessage(), var4);
        }
    }

    public AllBudgetLimitsELO getAllBudgetLimits() throws EJBException {
        AllBudgetLimitsELO elo = null;

        try {
            //ModelAccessor e = new ModelAccessor(new InitialContext());
            elo = modelAccessor.getAllBudgetLimits();
            return elo;
        } catch (Exception var3) {
            throw new EJBException(var3.getMessage(), var3);
        }
    }

    public AllPerfTestsELO getAllPerfTests() throws EJBException {
        AllPerfTestsELO elo = null;

        try {
            PerfTestAccessor e = new PerfTestAccessor(new InitialContext());
            elo = e.getAllPerfTests();
            return elo;
        } catch (Exception var3) {
            throw new EJBException(var3.getMessage(), var3);
        }
    }

    public AllPerfTestRunsELO getAllPerfTestRuns() throws EJBException {
        AllPerfTestRunsELO elo = null;

        try {
            PerfTestRunAccessor e = new PerfTestRunAccessor(new InitialContext());
            elo = e.getAllPerfTestRuns();
            return elo;
        } catch (Exception var3) {
            throw new EJBException(var3.getMessage(), var3);
        }
    }

    public AllPerfTestRunResultsELO getAllPerfTestRunResults() throws EJBException {
        AllPerfTestRunResultsELO elo = null;

        try {
            PerfTestRunAccessor e = new PerfTestRunAccessor(new InitialContext());
            elo = e.getAllPerfTestRunResults();
            return elo;
        } catch (Exception var3) {
            throw new EJBException(var3.getMessage(), var3);
        }
    }

    public AllMessagesELO getAllMessages() throws EJBException {
        AllMessagesELO elo = null;

        try {
            MessageAccessor e = new MessageAccessor(new InitialContext());
            elo = e.getAllMessages();
            return elo;
        } catch (Exception var3) {
            throw new EJBException(var3.getMessage(), var3);
        }
    }

    public InBoxForUserELO getInBoxForUser(String param1) throws EJBException {
        InBoxForUserELO elo = null;

        try {
            MessageAccessor e = new MessageAccessor(new InitialContext());
            elo = e.getInBoxForUser(param1);
            return elo;
        } catch (Exception var4) {
            throw new EJBException(var4.getMessage(), var4);
        }
    }

    public UnreadInBoxForUserELO getUnreadInBoxForUser(String param1) throws EJBException {
        UnreadInBoxForUserELO elo = null;

        try {
            MessageAccessor e = new MessageAccessor(new InitialContext());
            elo = e.getUnreadInBoxForUser(param1);
            return elo;
        } catch (Exception var4) {
            throw new EJBException(var4.getMessage(), var4);
        }
    }

    public SentItemsForUserELO getSentItemsForUser(String param1) throws EJBException {
        SentItemsForUserELO elo = null;

        try {
            MessageAccessor e = new MessageAccessor(new InitialContext());
            elo = e.getSentItemsForUser(param1);
            return elo;
        } catch (Exception var4) {
            throw new EJBException(var4.getMessage(), var4);
        }
    }

    public MessageForIdELO getMessageForId(long param1, String param2) throws EJBException {
        MessageForIdELO elo = null;

        try {
            MessageAccessor e = new MessageAccessor(new InitialContext());
            elo = e.getMessageForId(param1, param2);
            return elo;
        } catch (Exception var6) {
            throw new EJBException(var6.getMessage(), var6);
        }
    }

    public MessageForIdSentItemELO getMessageForIdSentItem(long param1, String param2) throws EJBException {
        MessageForIdSentItemELO elo = null;

        try {
            MessageAccessor e = new MessageAccessor(new InitialContext());
            elo = e.getMessageForIdSentItem(param1, param2);
            return elo;
        } catch (Exception var6) {
            throw new EJBException(var6.getMessage(), var6);
        }
    }

    public MessageCountELO getMessageCount(long param1) throws EJBException {
        MessageCountELO elo = null;

        try {
            MessageAccessor e = new MessageAccessor(new InitialContext());
            elo = e.getMessageCount(param1);
            return elo;
        } catch (Exception var5) {
            throw new EJBException(var5.getMessage(), var5);
        }
    }

    public AttatchmentForMessageELO getAttatchmentForMessage(long param1) throws EJBException {
        AttatchmentForMessageELO elo = null;

        try {
            MessageAccessor e = new MessageAccessor(new InitialContext());
            elo = e.getAttatchmentForMessage(param1);
            return elo;
        } catch (Exception var5) {
            throw new EJBException(var5.getMessage(), var5);
        }
    }

    public MessageFromUserELO getMessageFromUser(long param1) throws EJBException {
        MessageFromUserELO elo = null;

        try {
            MessageAccessor e = new MessageAccessor(new InitialContext());
            elo = e.getMessageFromUser(param1);
            return elo;
        } catch (Exception var5) {
            throw new EJBException(var5.getMessage(), var5);
        }
    }

    public MessageToUserELO getMessageToUser(long param1) throws EJBException {
        MessageToUserELO elo = null;

        try {
            MessageAccessor e = new MessageAccessor(new InitialContext());
            elo = e.getMessageToUser(param1);
            return elo;
        } catch (Exception var5) {
            throw new EJBException(var5.getMessage(), var5);
        }
    }

    public AllMessagesToUserELO getAllMessagesToUser(long param1) throws EJBException {
        AllMessagesToUserELO elo = null;

        try {
            MessageAccessor e = new MessageAccessor(new InitialContext());
            elo = e.getAllMessagesToUser(param1);
            return elo;
        } catch (Exception var5) {
            throw new EJBException(var5.getMessage(), var5);
        }
    }

    public AllRechargesELO getAllRecharges() throws EJBException {
        AllRechargesELO elo = null;

        try {
            //ModelAccessor e = new ModelAccessor(new InitialContext());
            elo = modelAccessor.getAllRecharges();
            return elo;
        } catch (Exception var3) {
            throw new EJBException(var3.getMessage(), var3);
        }
    }

    public AllRechargesWithModelELO getAllRechargesWithModel(int param1) throws EJBException {
        AllRechargesWithModelELO elo = null;

        try {
            //ModelAccessor e = new ModelAccessor(new InitialContext());
            elo = modelAccessor.getAllRechargesWithModel(param1);
            return elo;
        } catch (Exception var4) {
            throw new EJBException(var4.getMessage(), var4);
        }
    }

    public SingleRechargeELO getSingleRecharge(int param1) throws EJBException {
        SingleRechargeELO elo = null;

        try {
            //ModelAccessor e = new ModelAccessor(new InitialContext());
            elo = modelAccessor.getSingleRecharge(param1);
            return elo;
        } catch (Exception var4) {
            throw new EJBException(var4.getMessage(), var4);
        }
    }

    public AllRechargeGroupsELO getAllRechargeGroups() throws EJBException {
        AllRechargeGroupsELO elo = null;

        try {
            RechargeGroupAccessor e = new RechargeGroupAccessor(new InitialContext());
            elo = e.getAllRechargeGroups();
            return elo;
        } catch (Exception var3) {
            throw new EJBException(var3.getMessage(), var3);
        }
    }

    public ActivitiesForCycleandElementELO getActivitiesForCycleandElement(int param1, Integer param2, int param3) throws EJBException {
        ActivitiesForCycleandElementELO elo = null;

        try {
            //ModelAccessor e = new ModelAccessor(new InitialContext());
            elo = modelAccessor.getActivitiesForCycleandElement(param1, param2, param3);
            return elo;
        } catch (Exception var6) {
            throw new EJBException(var6.getMessage(), var6);
        }
    }

    public ActivityDetailsELO getActivityDetails(int param1) throws EJBException {
        ActivityDetailsELO elo = null;

        try {
            //ModelAccessor e = new ModelAccessor(new InitialContext());
            elo = modelAccessor.getActivityDetails(param1);
            return elo;
        } catch (Exception var4) {
            throw new EJBException(var4.getMessage(), var4);
        }
    }

    public ActivityFullDetailsELO getActivityFullDetails(int param1) throws EJBException {
        ActivityFullDetailsELO elo = null;

        try {
            //ModelAccessor e = new ModelAccessor(new InitialContext());
            elo = modelAccessor.getActivityFullDetails(param1);
            return elo;
        } catch (Exception var4) {
            throw new EJBException(var4.getMessage(), var4);
        }
    }

    public AllVirementRequestsELO getAllVirementRequests() throws EJBException {
        AllVirementRequestsELO elo = null;

        try {
            //ModelAccessor e = new ModelAccessor(new InitialContext());
            elo = modelAccessor.getAllVirementRequests();
            return elo;
        } catch (Exception var3) {
            throw new EJBException(var3.getMessage(), var3);
        }
    }

    public AllVirementRequestGroupsELO getAllVirementRequestGroups() throws EJBException {
        AllVirementRequestGroupsELO elo = null;

        try {
            //ModelAccessor e = new ModelAccessor(new InitialContext());
            elo = modelAccessor.getAllVirementRequestGroups();
            return elo;
        } catch (Exception var3) {
            throw new EJBException(var3.getMessage(), var3);
        }
    }

    public AllReportTypesELO getAllReportTypes() throws EJBException {
        AllReportTypesELO elo = null;

        try {
            ReportTypeAccessor e = new ReportTypeAccessor(new InitialContext());
            elo = e.getAllReportTypes();
            return elo;
        } catch (Exception var3) {
            throw new EJBException(var3.getMessage(), var3);
        }
    }

    public AllReportTypeParamsELO getAllReportTypeParams() throws EJBException {
        AllReportTypeParamsELO elo = null;

        try {
            ReportTypeAccessor e = new ReportTypeAccessor(new InitialContext());
            elo = e.getAllReportTypeParams();
            return elo;
        } catch (Exception var3) {
            throw new EJBException(var3.getMessage(), var3);
        }
    }

    public AllReportTypeParamsforTypeELO getAllReportTypeParamsforType(int param1) throws EJBException {
        AllReportTypeParamsforTypeELO elo = null;

        try {
            ReportTypeAccessor e = new ReportTypeAccessor(new InitialContext());
            elo = e.getAllReportTypeParamsforType(param1);
            return elo;
        } catch (Exception var4) {
            throw new EJBException(var4.getMessage(), var4);
        }
    }

    public AllReportDefinitionsELO getAllReportDefinitions() throws EJBException {
        AllReportDefinitionsELO elo = null;

        try {
            ReportDefinitionAccessor e = new ReportDefinitionAccessor(new InitialContext());
            elo = e.getAllReportDefinitions();
            return elo;
        } catch (Exception var3) {
            throw new EJBException(var3.getMessage(), var3);
        }
    }

    public AllReportDefinitionsELO getAllReportDefinitionsForLoggedUser(int userId) throws EJBException {
        AllReportDefinitionsELO elo = null;

        try {
            ReportDefinitionAccessor e = new ReportDefinitionAccessor(new InitialContext());
            elo = e.getAllReportDefinitionsForLoggedUser(userId);
            return elo;
        } catch (Exception var3) {
            throw new EJBException(var3.getMessage(), var3);
        }
    }

    public AllPublicReportByTypeELO getAllPublicReportByType(int param1) throws EJBException {
        AllPublicReportByTypeELO elo = null;

        try {
            ReportDefinitionAccessor e = new ReportDefinitionAccessor(new InitialContext());
            elo = e.getAllPublicReportByType(param1);
            return elo;
        } catch (Exception var4) {
            throw new EJBException(var4.getMessage(), var4);
        }
    }

    public ReportDefinitionForVisIdELO getReportDefinitionForVisId(String param1) throws EJBException {
        ReportDefinitionForVisIdELO elo = null;

        try {
            ReportDefinitionAccessor e = new ReportDefinitionAccessor(new InitialContext());
            elo = e.getReportDefinitionForVisId(param1);
            return elo;
        } catch (Exception var4) {
            throw new EJBException(var4.getMessage(), var4);
        }
    }

    public AllReportDefFormcByReportTemplateIdELO getAllReportDefFormcByReportTemplateId(int param1) throws EJBException {
        AllReportDefFormcByReportTemplateIdELO elo = null;

        try {
            ReportDefinitionAccessor e = new ReportDefinitionAccessor(new InitialContext());
            elo = e.getAllReportDefFormcByReportTemplateId(param1);
            return elo;
        } catch (Exception var4) {
            throw new EJBException(var4.getMessage(), var4);
        }
    }

    public AllReportDefFormcByModelIdELO getAllReportDefFormcByModelId(int param1) throws EJBException {
        AllReportDefFormcByModelIdELO elo = null;

        try {
            ReportDefinitionAccessor e = new ReportDefinitionAccessor(new InitialContext());
            elo = e.getAllReportDefFormcByModelId(param1);
            return elo;
        } catch (Exception var4) {
            throw new EJBException(var4.getMessage(), var4);
        }
    }

    public CheckFormIsUsedELO getCheckFormIsUsed(int param1) throws EJBException {
        CheckFormIsUsedELO elo = null;

        try {
            ReportDefinitionAccessor e = new ReportDefinitionAccessor(new InitialContext());
            elo = e.getCheckFormIsUsed(param1);
            return elo;
        } catch (Exception var4) {
            throw new EJBException(var4.getMessage(), var4);
        }
    }

    public AllReportDefMappedExcelcByReportTemplateIdELO getAllReportDefMappedExcelcByReportTemplateId(int param1) throws EJBException {
        AllReportDefMappedExcelcByReportTemplateIdELO elo = null;

        try {
            ReportDefinitionAccessor e = new ReportDefinitionAccessor(new InitialContext());
            elo = e.getAllReportDefMappedExcelcByReportTemplateId(param1);
            return elo;
        } catch (Exception var4) {
            throw new EJBException(var4.getMessage(), var4);
        }
    }

    public AllReportDefMappedExcelcByModelIdELO getAllReportDefMappedExcelcByModelId(int param1) throws EJBException {
        AllReportDefMappedExcelcByModelIdELO elo = null;

        try {
            ReportDefinitionAccessor e = new ReportDefinitionAccessor(new InitialContext());
            elo = e.getAllReportDefMappedExcelcByModelId(param1);
            return elo;
        } catch (Exception var4) {
            throw new EJBException(var4.getMessage(), var4);
        }
    }

    public AllReportDefCalcByCCDeploymentIdELO getAllReportDefCalcByCCDeploymentId(int param1) throws EJBException {
        AllReportDefCalcByCCDeploymentIdELO elo = null;

        try {
            ReportDefinitionAccessor e = new ReportDefinitionAccessor(new InitialContext());
            elo = e.getAllReportDefCalcByCCDeploymentId(param1);
            return elo;
        } catch (Exception var4) {
            throw new EJBException(var4.getMessage(), var4);
        }
    }

    public AllReportDefCalcByReportTemplateIdELO getAllReportDefCalcByReportTemplateId(int param1) throws EJBException {
        AllReportDefCalcByReportTemplateIdELO elo = null;

        try {
            ReportDefinitionAccessor e = new ReportDefinitionAccessor(new InitialContext());
            elo = e.getAllReportDefCalcByReportTemplateId(param1);
            return elo;
        } catch (Exception var4) {
            throw new EJBException(var4.getMessage(), var4);
        }
    }

    public AllReportDefCalcByModelIdELO getAllReportDefCalcByModelId(int param1) throws EJBException {
        AllReportDefCalcByModelIdELO elo = null;

        try {
            ReportDefinitionAccessor e = new ReportDefinitionAccessor(new InitialContext());
            elo = e.getAllReportDefCalcByModelId(param1);
            return elo;
        } catch (Exception var4) {
            throw new EJBException(var4.getMessage(), var4);
        }
    }

    public AllReportDefSummaryCalcByCCDeploymentIdELO getAllReportDefSummaryCalcByCCDeploymentId(int param1) throws EJBException {
        AllReportDefSummaryCalcByCCDeploymentIdELO elo = null;

        try {
            ReportDefinitionAccessor e = new ReportDefinitionAccessor(new InitialContext());
            elo = e.getAllReportDefSummaryCalcByCCDeploymentId(param1);
            return elo;
        } catch (Exception var4) {
            throw new EJBException(var4.getMessage(), var4);
        }
    }

    public AllReportDefSummaryCalcByReportTemplateIdELO getAllReportDefSummaryCalcByReportTemplateId(int param1) throws EJBException {
        AllReportDefSummaryCalcByReportTemplateIdELO elo = null;

        try {
            ReportDefinitionAccessor e = new ReportDefinitionAccessor(new InitialContext());
            elo = e.getAllReportDefSummaryCalcByReportTemplateId(param1);
            return elo;
        } catch (Exception var4) {
            throw new EJBException(var4.getMessage(), var4);
        }
    }

    public AllReportDefSummaryCalcByModelIdELO getAllReportDefSummaryCalcByModelId(int param1) throws EJBException {
        AllReportDefSummaryCalcByModelIdELO elo = null;

        try {
            ReportDefinitionAccessor e = new ReportDefinitionAccessor(new InitialContext());
            elo = e.getAllReportDefSummaryCalcByModelId(param1);
            return elo;
        } catch (Exception var4) {
            throw new EJBException(var4.getMessage(), var4);
        }
    }

    public AllReportTemplatesELO getAllReportTemplates() throws EJBException {
        AllReportTemplatesELO elo = null;

        try {
            ReportTemplateAccessor e = new ReportTemplateAccessor(new InitialContext());
            elo = e.getAllReportTemplates();
            return elo;
        } catch (Exception var3) {
            throw new EJBException(var3.getMessage(), var3);
        }
    }

    public AllReportMappingTemplatesELO getAllReportMappingTemplates() throws EJBException {
        AllReportMappingTemplatesELO elo = null;

        try {
            ReportMappingTemplateAccessor e = new ReportMappingTemplateAccessor(new InitialContext());
            elo = e.getAllReportMappingTemplates();
            return elo;
        } catch (Exception var3) {
            throw new EJBException(var3.getMessage(), var3);
        }
    }

    public AllExternalDestinationsELO getAllExternalDestinations() throws EJBException {
        AllExternalDestinationsELO elo = null;

        try {
            ExternalDestinationAccessor e = new ExternalDestinationAccessor(new InitialContext());
            elo = e.getAllExternalDestinations();
            return elo;
        } catch (Exception var3) {
            throw new EJBException(var3.getMessage(), var3);
        }
    }

    public AllExternalDestinationDetailsELO getAllExternalDestinationDetails() throws EJBException {
        AllExternalDestinationDetailsELO elo = null;

        try {
            ExternalDestinationAccessor e = new ExternalDestinationAccessor(new InitialContext());
            elo = e.getAllExternalDestinationDetails();
            return elo;
        } catch (Exception var3) {
            throw new EJBException(var3.getMessage(), var3);
        }
    }

    public AllUsersForExternalDestinationIdELO getAllUsersForExternalDestinationId(int param1) throws EJBException {
        AllUsersForExternalDestinationIdELO elo = null;

        try {
            ExternalDestinationAccessor e = new ExternalDestinationAccessor(new InitialContext());
            elo = e.getAllUsersForExternalDestinationId(param1);
            return elo;
        } catch (Exception var4) {
            throw new EJBException(var4.getMessage(), var4);
        }
    }

    public AllInternalDestinationsELO getAllInternalDestinations() throws EJBException {
        AllInternalDestinationsELO elo = null;

        try {
            InternalDestinationAccessor e = new InternalDestinationAccessor(new InitialContext());
            elo = e.getAllInternalDestinations();
            return elo;
        } catch (Exception var3) {
            throw new EJBException(var3.getMessage(), var3);
        }
    }

    public AllInternalDestinationDetailsELO getAllInternalDestinationDetails() throws EJBException {
        AllInternalDestinationDetailsELO elo = null;

        try {
            InternalDestinationAccessor e = new InternalDestinationAccessor(new InitialContext());
            elo = e.getAllInternalDestinationDetails();
            return elo;
        } catch (Exception var3) {
            throw new EJBException(var3.getMessage(), var3);
        }
    }

    public AllUsersForInternalDestinationIdELO getAllUsersForInternalDestinationId(int param1) throws EJBException {
        AllUsersForInternalDestinationIdELO elo = null;

        try {
            InternalDestinationAccessor e = new InternalDestinationAccessor(new InitialContext());
            elo = e.getAllUsersForInternalDestinationId(param1);
            return elo;
        } catch (Exception var4) {
            throw new EJBException(var4.getMessage(), var4);
        }
    }

    public AllInternalDestinationUsersELO getAllInternalDestinationUsers() throws EJBException {
        AllInternalDestinationUsersELO elo = null;

        try {
            InternalDestinationAccessor e = new InternalDestinationAccessor(new InitialContext());
            elo = e.getAllInternalDestinationUsers();
            return elo;
        } catch (Exception var3) {
            throw new EJBException(var3.getMessage(), var3);
        }
    }

    public CheckInternalDestinationUsersELO getCheckInternalDestinationUsers(int param1) throws EJBException {
        CheckInternalDestinationUsersELO elo = null;

        try {
            InternalDestinationAccessor e = new InternalDestinationAccessor(new InitialContext());
            elo = e.getCheckInternalDestinationUsers(param1);
            return elo;
        } catch (Exception var4) {
            throw new EJBException(var4.getMessage(), var4);
        }
    }

    public AllDistributionsELO getAllDistributions() throws EJBException {
        AllDistributionsELO elo = null;

        try {
            DistributionAccessor e = new DistributionAccessor(new InitialContext());
            elo = e.getAllDistributions();
            return elo;
        } catch (Exception var3) {
            throw new EJBException(var3.getMessage(), var3);
        }
    }

    public DistributionForVisIdELO getDistributionForVisId(String param1) throws EJBException {
        DistributionForVisIdELO elo = null;

        try {
            DistributionAccessor e = new DistributionAccessor(new InitialContext());
            elo = e.getDistributionForVisId(param1);
            return elo;
        } catch (Exception var4) {
            throw new EJBException(var4.getMessage(), var4);
        }
    }

    public DistributionDetailsForVisIdELO getDistributionDetailsForVisId(String param1) throws EJBException {
        DistributionDetailsForVisIdELO elo = null;

        try {
            DistributionAccessor e = new DistributionAccessor(new InitialContext());
            elo = e.getDistributionDetailsForVisId(param1);
            return elo;
        } catch (Exception var4) {
            throw new EJBException(var4.getMessage(), var4);
        }
    }

    public CheckInternalDestinationELO getCheckInternalDestination(int param1) throws EJBException {
        CheckInternalDestinationELO elo = null;

        try {
            DistributionAccessor e = new DistributionAccessor(new InitialContext());
            elo = e.getCheckInternalDestination(param1);
            return elo;
        } catch (Exception var4) {
            throw new EJBException(var4.getMessage(), var4);
        }
    }

    public CheckExternalDestinationELO getCheckExternalDestination(int param1) throws EJBException {
        CheckExternalDestinationELO elo = null;

        try {
            DistributionAccessor e = new DistributionAccessor(new InitialContext());
            elo = e.getCheckExternalDestination(param1);
            return elo;
        } catch (Exception var4) {
            throw new EJBException(var4.getMessage(), var4);
        }
    }

    public AllReportPacksELO getAllReportPacks() throws EJBException {
        AllReportPacksELO elo = null;

        try {
            ReportPackAccessor e = new ReportPackAccessor(new InitialContext());
            elo = e.getAllReportPacks();
            return elo;
        } catch (Exception var3) {
            throw new EJBException(var3.getMessage(), var3);
        }
    }

    public ReportDefDistListELO getReportDefDistList(String param1) throws EJBException {
        ReportDefDistListELO elo = null;

        try {
            ReportPackAccessor e = new ReportPackAccessor(new InitialContext());
            elo = e.getReportDefDistList(param1);
            return elo;
        } catch (Exception var4) {
            throw new EJBException(var4.getMessage(), var4);
        }
    }

    public CheckReportDefELO getCheckReportDef(int param1) throws EJBException {
        CheckReportDefELO elo = null;

        try {
            ReportPackAccessor e = new ReportPackAccessor(new InitialContext());
            elo = e.getCheckReportDef(param1);
            return elo;
        } catch (Exception var4) {
            throw new EJBException(var4.getMessage(), var4);
        }
    }

    public CheckReportDistributionELO getCheckReportDistribution(int param1) throws EJBException {
        CheckReportDistributionELO elo = null;

        try {
            ReportPackAccessor e = new ReportPackAccessor(new InitialContext());
            elo = e.getCheckReportDistribution(param1);
            return elo;
        } catch (Exception var4) {
            throw new EJBException(var4.getMessage(), var4);
        }
    }

    public AllWeightingProfilesELO getAllWeightingProfiles() throws EJBException {
        AllWeightingProfilesELO elo = null;

        try {
            //ModelAccessor e = new ModelAccessor(new InitialContext());
            elo = modelAccessor.getAllWeightingProfiles();
            return elo;
        } catch (Exception var3) {
            throw new EJBException(var3.getMessage(), var3);
        }
    }

    public AllWeightingProfilesELO getAllWeightingProfilesForLoggedUser(int userId) throws EJBException {
        AllWeightingProfilesELO elo = null;
        try {
            //ModelAccessor e = new ModelAccessor(new InitialContext());
            elo = modelAccessor.getAllWeightingProfilesForLoggedUser(userId);
            return elo;
        } catch (Exception var3) {
            throw new EJBException(var3.getMessage(), var3);
        }
    }

    public AllWeightingDeploymentsELO getAllWeightingDeployments() throws EJBException {
        AllWeightingDeploymentsELO elo = null;
        try {
            //ModelAccessor e = new ModelAccessor(new InitialContext());
            elo = modelAccessor.getAllWeightingDeployments();
            return elo;
        } catch (Exception var3) {
            throw new EJBException(var3.getMessage(), var3);
        }
    }

    public EntityList getAllWeightingDeploymentsForLoggedUser(int userId) throws EJBException {
        EntityList elo = null;
        try {
            //ModelAccessor e = new ModelAccessor(new InitialContext());
            elo = modelAccessor.getAllWeightingDeploymentsForLoggedUser(userId);
            return elo;
        } catch (Exception var3) {
            throw new EJBException(var3.getMessage(), var3);
        }
    }

    public AllTidyTasksELO getAllTidyTasks() throws EJBException {
        AllTidyTasksELO elo = null;

        try {
            TidyTaskAccessor e = new TidyTaskAccessor(new InitialContext());
            elo = e.getAllTidyTasks();
            return elo;
        } catch (Exception var3) {
            throw new EJBException(var3.getMessage(), var3);
        }
    }

    public AllImportTasksELO getAllImportTasks() throws EJBException {
        AllImportTasksELO elo = null;

        try {
            ImportTaskAccessor e = new ImportTaskAccessor(new InitialContext());
            elo = e.getAllImportTasks();
            return elo;
        } catch (Exception var3) {
            throw new EJBException(var3.getMessage(), var3);
        }
    }

    public AllRecalculateBatchTasksELO getAllRecalculateBatchTasks() throws EJBException {
        AllRecalculateBatchTasksELO elo = null;

        try {
            RecalculateBatchTaskAccessor e = new RecalculateBatchTaskAccessor(new InitialContext());
            elo = e.getAllRecalculateBatchTasks();
            return elo;
        } catch (Exception var3) {
            throw new EJBException(var3.getMessage(), var3);
        }
    }

    public OrderedChildrenELO getOrderedChildren(int param1) throws EJBException {
        OrderedChildrenELO elo = null;

        try {
            TidyTaskAccessor e = new TidyTaskAccessor(new InitialContext());
            elo = e.getOrderedChildren(param1);
            return elo;
        } catch (Exception var4) {
            throw new EJBException(var4.getMessage(), var4);
        }
    }

    public AllMappedModelsELO getAllMappedModels() throws EJBException {
        AllMappedModelsELO elo = null;

        try {
            MappedModelAccessor e = new MappedModelAccessor(new InitialContext());
            elo = e.getAllMappedModels();
            return elo;
        } catch (Exception var3) {
            throw new EJBException(var3.getMessage(), var3);
        }
    }

    public AllMappedModelsELO getAllMappedModelsForLoggedUser(int userId) throws EJBException {
        AllMappedModelsELO elo = null;

        try {
            MappedModelAccessor e = new MappedModelAccessor(new InitialContext());
            elo = e.getAllMappedModelsForLoggedUser(userId);
            return elo;
        } catch (Exception var3) {
            throw new EJBException(var3.getMessage(), var3);
        }
    }

    public AllGlobalMappedModels2ELO getAllGlobalMappedModels2() throws EJBException {
        AllGlobalMappedModels2ELO elo = null;

        try {
            GlobalMappedModel2Accessor accessor = new GlobalMappedModel2Accessor(new InitialContext());
            elo = accessor.getAllGlobalMappedModels2();
            return elo;
        } catch (Exception e) {
            throw new EJBException(e.getMessage(), e);
        }
    }

    public AllGlobalMappedModels2ELO getAllGlobalMappedModelsForLoggedUser(int userId) throws EJBException {
        AllGlobalMappedModels2ELO elo = null;

        try {
            GlobalMappedModel2Accessor accessor = new GlobalMappedModel2Accessor(new InitialContext());
            elo = accessor.getAllGlobalMappedModelsForLoggedUser(userId);
            return elo;
        } catch (Exception e) {
            throw new EJBException(e.getMessage(), e);
        }
    }

    public MappedFinanceCubesELO getMappedFinanceCubes(int param1) throws EJBException {
        MappedFinanceCubesELO elo = null;

        try {
            MappedModelAccessor e = new MappedModelAccessor(new InitialContext());
            elo = e.getMappedFinanceCubes(param1);
            return elo;
        } catch (Exception var4) {
            throw new EJBException(var4.getMessage(), var4);
        }
    }

    public AllExtendedAttachmentsELO getAllExtendedAttachments() throws EJBException {
        AllExtendedAttachmentsELO elo = null;

        try {
            ExtendedAttachmentAccessor e = new ExtendedAttachmentAccessor(new InitialContext());
            elo = e.getAllExtendedAttachments();
            return elo;
        } catch (Exception var3) {
            throw new EJBException(var3.getMessage(), var3);
        }
    }

    public ExtendedAttachmentsForIdELO getExtendedAttachmentsForId(int param1) throws EJBException {
        ExtendedAttachmentsForIdELO elo = null;

        try {
            ExtendedAttachmentAccessor e = new ExtendedAttachmentAccessor(new InitialContext());
            elo = e.getExtendedAttachmentsForId(param1);
            return elo;
        } catch (Exception var4) {
            throw new EJBException(var4.getMessage(), var4);
        }
    }

    public AllImageExtendedAttachmentsELO getAllImageExtendedAttachments() throws EJBException {
        AllImageExtendedAttachmentsELO elo = null;

        try {
            ExtendedAttachmentAccessor e = new ExtendedAttachmentAccessor(new InitialContext());
            elo = e.getAllImageExtendedAttachments();
            return elo;
        } catch (Exception var3) {
            throw new EJBException(var3.getMessage(), var3);
        }
    }

    public AllExternalSystemsELO getAllExternalSystems() throws EJBException {
        AllExternalSystemsELO elo = null;

        try {
            ExternalSystemAccessor e = new ExternalSystemAccessor(new InitialContext());
            elo = e.getAllExternalSystems();
            return elo;
        } catch (Exception var3) {
            throw new EJBException(var3.getMessage(), var3);
        }
    }

    public AllGenericExternalSystemsELO getAllGenericExternalSystems() throws EJBException {
        AllGenericExternalSystemsELO elo = null;

        try {
            ExternalSystemAccessor e = new ExternalSystemAccessor(new InitialContext());
            elo = e.getAllGenericExternalSystems();
            return elo;
        } catch (Exception var3) {
            throw new EJBException(var3.getMessage(), var3);
        }
    }

    public AllExternalSystemCompainesELO getAllExternalSystemCompaines() throws EJBException {
        AllExternalSystemCompainesELO elo = null;

        try {
            ExternalSystemAccessor e = new ExternalSystemAccessor(new InitialContext());
            elo = e.getAllExternalSystemCompaines();
            return elo;
        } catch (Exception var3) {
            throw new EJBException(var3.getMessage(), var3);
        }
    }

    public AllAmmModelsELO getAllAmmModels() throws EJBException {
        AllAmmModelsELO elo = null;

        try {
            AmmModelAccessor e = new AmmModelAccessor(new InitialContext());
            elo = e.getAllAmmModels();
            return elo;
        } catch (Exception var3) {
            throw new EJBException(var3.getMessage(), var3);
        }
    }

    public AllAmmModelsELO getAllAmmModelsForLoggedUser(int userId) throws EJBException {
        AllAmmModelsELO elo = null;

        try {
            AmmModelAccessor e = new AmmModelAccessor(new InitialContext());
            elo = e.getAllAmmModelsForLoggedUser(userId);
            return elo;
        } catch (Exception var3) {
            throw new EJBException(var3.getMessage(), var3);
        }
    }

    public AllTaskGroupsELO getAllTaskGroups() throws EJBException {
        AllTaskGroupsELO elo = null;

        try {
            TaskGroupAccessor e = new TaskGroupAccessor(new InitialContext());
            elo = e.getAllTaskGroups();
            return elo;
        } catch (Exception var3) {
            throw new EJBException(var3.getMessage(), var3);
        }
    }

    public TaskGroupRICheckELO getTaskGroupRICheck(int param1) throws EJBException {
        TaskGroupRICheckELO elo = null;

        try {
            TaskGroupAccessor e = new TaskGroupAccessor(new InitialContext());
            elo = e.getTaskGroupRICheck(param1);
            return elo;
        } catch (Exception var4) {
            throw new EJBException(var4.getMessage(), var4);
        }
    }

    public AllAuthenticationPolicysELO getAllAuthenticationPolicys() throws EJBException {
        AllAuthenticationPolicysELO elo = null;

        try {
            AuthenticationPolicyAccessor e = new AuthenticationPolicyAccessor(new InitialContext());
            elo = e.getAllAuthenticationPolicys();
            return elo;
        } catch (Exception var3) {
            throw new EJBException(var3.getMessage(), var3);
        }
    }

    public ActiveAuthenticationPolicysELO getActiveAuthenticationPolicys() throws EJBException {
        ActiveAuthenticationPolicysELO elo = null;

        try {
            AuthenticationPolicyAccessor e = new AuthenticationPolicyAccessor(new InitialContext());
            elo = e.getActiveAuthenticationPolicys();
            return elo;
        } catch (Exception var3) {
            throw new EJBException(var3.getMessage(), var3);
        }
    }

    public ActiveAuthenticationPolicyForLogonELO getActiveAuthenticationPolicyForLogon() throws EJBException {
        ActiveAuthenticationPolicyForLogonELO elo = null;

        try {
            AuthenticationPolicyAccessor e = new AuthenticationPolicyAccessor(new InitialContext());
            elo = e.getActiveAuthenticationPolicyForLogon();
            return elo;
        } catch (Exception var3) {
            throw new EJBException(var3.getMessage(), var3);
        }
    }

    public AllLogonHistorysELO getAllLogonHistorys() throws EJBException {
        AllLogonHistorysELO elo = null;

        try {
//            LogonHistoryAccessor e = new LogonHistoryAccessor(new InitialContext());
//            elo = e.getAllLogonHistorys();
//            return elo;
        	return null;
        } catch (Exception var3) {
            throw new EJBException(var3.getMessage(), var3);
        }
    }

    public AllPasswordHistorysELO getAllPasswordHistorys() throws EJBException {
        AllPasswordHistorysELO elo = null;

        try {
            PasswordHistoryAccessor e = new PasswordHistoryAccessor(new InitialContext());
            elo = e.getAllPasswordHistorys();
            return elo;
        } catch (Exception var3) {
            throw new EJBException(var3.getMessage(), var3);
        }
    }

    public UserPasswordHistoryELO getUserPasswordHistory(int param1) throws EJBException {
        UserPasswordHistoryELO elo = null;

        try {
            PasswordHistoryAccessor e = new PasswordHistoryAccessor(new InitialContext());
            elo = e.getUserPasswordHistory(param1);
            return elo;
        } catch (Exception var4) {
            throw new EJBException(var4.getMessage(), var4);
        }
    }

    public AllFormRebuildsELO getAllFormRebuilds() throws EJBException {
        AllFormRebuildsELO elo = null;

        try {
            //ModelAccessor e = new ModelAccessor(new InitialContext());
            elo = modelAccessor.getAllFormRebuilds();
            return elo;
        } catch (Exception var3) {
            throw new EJBException(var3.getMessage(), var3);
        }
    }

    public AllFormRebuildsELO getAllFormRebuildsForLoggedUser(int userId) throws EJBException {
        AllFormRebuildsELO elo = null;

        try {
            //ModelAccessor e = new ModelAccessor(new InitialContext());
            elo = modelAccessor.getAllFormRebuildsForLoggedUser(userId);
            return elo;
        } catch (Exception var3) {
            throw new EJBException(var3.getMessage(), var3);
        }
    }

    public AllBudgetCyclesInRebuildsELO getAllBudgetCyclesInRebuilds() throws EJBException {
        AllBudgetCyclesInRebuildsELO elo = null;

        try {
            //ModelAccessor e = new ModelAccessor(new InitialContext());
            elo = modelAccessor.getAllBudgetCyclesInRebuilds();
            return elo;
        } catch (Exception var3) {
            throw new EJBException(var3.getMessage(), var3);
        }
    }

    public AllPackagesForFinanceCubeELO getAllPackagesForFinanceCube(int param1) throws EJBException {
        AllPackagesForFinanceCubeELO elo = null;

        try {
            //ModelAccessor e = new ModelAccessor(new InitialContext());
            elo = modelAccessor.getAllPackagesForFinanceCube(param1);
            return elo;
        } catch (Exception var4) {
            throw new EJBException(var4.getMessage(), var4);
        }
    }

    public AllCubeFormulasELO getAllCubeFormulas() throws EJBException {
        AllCubeFormulasELO elo = null;

        try {
            //ModelAccessor e = new ModelAccessor(new InitialContext());
            elo = modelAccessor.getAllCubeFormulas();
            return elo;
        } catch (Exception var3) {
            throw new EJBException(var3.getMessage(), var3);
        }
    }

    public CubeFormulaeForFinanceCubeELO getCubeFormulaeForFinanceCube(int param1) throws EJBException {
        CubeFormulaeForFinanceCubeELO elo = null;

        try {
            //ModelAccessor e = new ModelAccessor(new InitialContext());
            elo = modelAccessor.getCubeFormulaeForFinanceCube(param1);
            return elo;
        } catch (Exception var4) {
            throw new EJBException(var4.getMessage(), var4);
        }
    }

    public void ejbCreate() throws EJBException {
    }

    public void ejbRemove() {
    }

    public void setSessionContext(SessionContext context) {
        this.mSessionContext = context;
    }

    public void ejbActivate() {
    }

    public void ejbPassivate() {
    }

    public EntityList getUserPreferencesForUser(UserRef userRef) throws EJBException {
        Timer timer = this._log.isInfoEnabled() ? new Timer(this._log) : null;
        UserPreferencesForUserELO elo = null;

        try {
            UserAccessor e = new UserAccessor(new InitialContext());
            elo = e.getUserPreferencesForUser(((UserPK) userRef.getPrimaryKey()).getUserId());
            if (timer != null) {
                timer.logInfo("getUserPreferencesForUser", "items=" + (elo != null ? String.valueOf(elo.size()) : "?"));
            }

            return elo;
        } catch (Exception var5) {
            throw new EJBException(var5.getMessage());
        }
    }

    public BudgetDetailsForUserELO getBudgetDetailsForUser(int userId, int modelId) throws EJBException {
        Timer timer = this._log.isInfoEnabled() ? new Timer(this._log) : null;
        BudgetDetailsForUserELO elo = null;

        try {
            BudgetUserDAO e = new BudgetUserDAO();
            elo = e.getBudgetDetailsForUser(userId, modelId);
            if (timer != null) {
                timer.logInfo("getBudgetDetailsForUser", "items=" + (elo != null ? String.valueOf(elo.size()) : "?"));
            }

            return elo;
        } catch (Exception var6) {
            throw new EJBException(var6.getMessage());
        }
    }

    public BudgetDetailsForUserELO getBudgetDetailsForUser(int userId, boolean detailedSelection, int locationId, int cycleId) throws EJBException {
        Timer timer = this._log.isInfoEnabled() ? new Timer(this._log) : null;
        BudgetDetailsForUserELO elo = null;

        try {
            //ModelAccessor e = new ModelAccessor(new InitialContext());
            elo = modelAccessor.getBudgetDetailsForUser(userId, detailedSelection, locationId, cycleId);
            if (timer != null) {
                timer.logInfo("getBudgetDetailsForUser", "items=" + (elo != null ? String.valueOf(elo.size()) : "?"));
            }

            return elo;
        } catch (Exception var8) {
            throw new EJBException(var8.getMessage());
        }
    }

    public EntityList getBudgetUserDetails(int bcId, int[] structureElementId) throws EJBException {
        Timer timer = this._log.isInfoEnabled() ? new Timer(this._log) : null;
        EntityList elo = null;

        try {
            //ModelAccessor e = new ModelAccessor(new InitialContext());
            elo = modelAccessor.getBudgetUserDetails(bcId, structureElementId);
            if (timer != null) {
                timer.logInfo("getBudgetUserDetails", "");
            }

            return elo;
        } catch (Exception var6) {
            throw new EJBException(var6.getMessage());
        }
    }

    public EntityList getBudgetUserDetailsNodeDown(int bcId, int structureElementId, int structureId) throws EJBException {
        Timer timer = this._log.isInfoEnabled() ? new Timer(this._log) : null;
        EntityList elo = null;

        try {
            //ModelAccessor e = new ModelAccessor(new InitialContext());
            elo = modelAccessor.getBudgetUserDetailsNodeDown(bcId, structureElementId, structureId);
            if (timer != null) {
                timer.logInfo("getBudgetUserDetailsNodeDown", "");
            }

            return elo;
        } catch (Exception var7) {
            throw new EJBException(var7.getMessage());
        }
    }

    public EntityList getBudgetUserAuthDetailsNodeUp(int bcId, int structureElementId, int structureId) throws EJBException {
        Timer timer = this._log.isInfoEnabled() ? new Timer(this._log) : null;
        EntityList elo = null;

        try {
            //ModelAccessor e = new ModelAccessor(new InitialContext());
            elo = modelAccessor.getBudgetUserAuthDetailsNodeUp(bcId, structureElementId, structureId);
            if (timer != null) {
                timer.logInfo("getBudgetUserAuthDetailsNodeUp", "");
            }

            return elo;
        } catch (Exception var7) {
            throw new EJBException(var7.getMessage());
        }
    }

    public StructureElementValuesELO getStructureElementIdFromModel(int modelId) {
        StructureElementDAO seDAO = new StructureElementDAO();
        return seDAO.getStructureElementIdFromModel(modelId);
    }

    public EntityList getPickerStartUpDetails(int modelid, int[] structureElementId, int userId) {
        try {
            this.setUserId(userId);
            boolean e = this.getCPConnection().getUserContext().isAdmin();
            BudgetUserDAO dao = new BudgetUserDAO();
            EntityList var6 = dao.getPickerStartUpData(modelid, structureElementId, userId, e);
            return var6;
        } catch (Exception var10) {
            ;
        } finally {
            this.setUserId(-1);
        }

        return null;
    }

    public AllPublicXmlReportFoldersELO getAllPublicXmlReportFolders() throws EJBException {
        Timer timer = this._log.isInfoEnabled() ? new Timer(this._log) : null;
        AllPublicXmlReportFoldersELO elo = null;

        try {
            XmlReportFolderAccessor e = new XmlReportFolderAccessor(new InitialContext());
            elo = e.getAllPublicXmlReportFolders();
            if (timer != null) {
                timer.logInfo("getAllPublicXmlReportFolders", "items=" + (elo != null ? String.valueOf(elo.size()) : "?"));
            }

            return elo;
        } catch (Exception var4) {
            throw new EJBException(var4.getMessage(), var4);
        }
    }

    public AllXmlReportFoldersForUserELO getAllXmlReportFoldersForUser(int param1) throws EJBException {
        Timer timer = this._log.isInfoEnabled() ? new Timer(this._log) : null;
        AllXmlReportFoldersForUserELO elo = null;

        try {
            XmlReportFolderAccessor e = new XmlReportFolderAccessor(new InitialContext());
            elo = e.getAllXmlReportFoldersForUser(param1);
            if (timer != null) {
                timer.logInfo("getAllXmlReportFoldersForUser", "items=" + (elo != null ? String.valueOf(elo.size()) : "?"));
            }

            return elo;
        } catch (Exception var5) {
            throw new EJBException(var5.getMessage(), var5);
        }
    }

    public EntityList getTreeInfoForDimTypeInModel(int modelId, int dimType) {
        ModelDAO dao = new ModelDAO();
        return dao.getPickerDataForDimInModel(modelId, dimType);
    }

    public EntityList getTreeInfoForModel(int modelId) {
        ModelDAO dao = new ModelDAO();
        return dao.getPickerDataForModel(modelId);
    }

    public EntityList getTreeInfoForModelDimTypes(int modelId, int[] type) {
        ModelDAO dao = new ModelDAO();
        return dao.getPickerDataForModelDimTypes(modelId, type);
    }

    public EntityList getTreeInfoForModelDimSeq(int modelId, int[] seq) {
        ModelDAO dao = new ModelDAO();
        return dao.getPickerDataForModelDimSeq(modelId, seq);
    }

    public EntityList getTreeInfoForModelRA(int modelId) {
        ModelDAO dao = new ModelDAO();
        return dao.getPickerDataForModelRA(modelId);
    }

    public EntityList getCellCalcAccesDefs(int modelId) {
        SecurityAccessDefDAO dao = new SecurityAccessDefDAO();
        return dao.getCellCalcAccesDefs(modelId);
    }

    public EntityList doElementPickerSearch(int dimensionId, String visId) {
        StructureElementDAO dao = new StructureElementDAO();
        return dao.getSearchTree(dimensionId, visId);
    }

    public EntityList getAllUserAssignments(String pName, String pFullName, String pModel, String pLocation) {
        UserDAO dao = new UserDAO();
        return dao.getAllRespAreaAssignments(pName, pFullName, pModel, pLocation);
    }

    public EntityList getContactLocations(int budgetCycleId, int daysUntilDue) {
        BudgetStateHistoryDAO dao = new BudgetStateHistoryDAO();
        return dao.getContactLocations(budgetCycleId, daysUntilDue);
    }

    public EntityList getModernWelcomeDetails(int userId, int daysUntilDue) {
        BudgetStateHistoryDAO dao = new BudgetStateHistoryDAO();
        return dao.getModernWelcomeInfo(userId, daysUntilDue);
    }

    public boolean hasUserAccessToRespArea(int userId, int modelId, int structureElementId) {
        StructureElementDAO dao = new StructureElementDAO();
        return dao.hasUserAccessToRespArea(userId, modelId, structureElementId);
    }

    public AllFinanceXmlFormsForModelELO getAllFinanceXmlFormsForModelAndUser(int param1, int budgetCycleId, int userId, boolean hasDesignModeSecurity) throws EJBException {
        Timer timer = new Timer(this._log);
        AllFinanceXmlFormsForModelELO elo = null;

        try {
            XmlFormAccessor e = new XmlFormAccessor(new InitialContext());
            elo = e.getAllFinanceXmlFormsForModelAndUser(param1, budgetCycleId, userId, hasDesignModeSecurity);
            timer.logDebug("getAllFinanceXmlFormsForModelAndUser", "items=" + (elo != null ? String.valueOf(elo.size()) : "?"));
            return elo;
        } catch (Exception var7) {
            throw new EJBException(var7.getMessage(), var7);
        }
    }

    public UnreadInBoxForUserELO getSummaryUnreadMessagesForUser(String userId) {
        MessageDAO dao = new MessageDAO();
        return dao.getSummaryUnreadMessagesForUser(userId);
    }

    public AllNonDisabledUsersELO getDistinctInternalDestinationUsers(String[] ids) {
        InternalDestinationDAO dao = new InternalDestinationDAO();
        return dao.getDistinctInternalDestinationUsers(ids);
    }

    public AllNonDisabledUsersELO getDistinctExternalDestinationUsers(String[] ids) {
        ExternalDestinationDAO dao = new ExternalDestinationDAO();
        return dao.getDistinctExternalDestinationUsers(ids);
    }

    public List getLookupTableData(String tableName, List columnDef) {
        UdefLookupDAO dao = new UdefLookupDAO();
        return dao.getTableData(tableName, columnDef);
    }

    public EntityList getPickerDataTypesWeb(int[] subTypes, boolean writeable) {
        DataTypeDAO dao = new DataTypeDAO();
        return dao.getPickerDataTypesWeb(subTypes, writeable);
    }

    public EntityList getPickerDataTypesWeb(int financeCubeId, int[] subTypes, boolean writeable) {
        DataTypeDAO dao = new DataTypeDAO();
        return dao.getPickerDataTypesWeb(financeCubeId, subTypes, writeable);
    }

    public EntityList getAllUserDetailsReport(String name, String fullname, String email, boolean disabled) {
        UserDAO dao = new UserDAO();
        return dao.getAllUserDetailsReport(name, fullname, email, disabled);
    }

    public AllLogonHistorysReportELO getAllLogonHistorysReport(String param1, Timestamp param2, int param3) throws EJBException {
        AllLogonHistorysReportELO elo = null;

        try {
//            LogonHistoryAccessor e = new LogonHistoryAccessor(new InitialContext());
//            Object eventDate = null;
//            elo = e.getAllLogonHistorysReport(param1, param2, param3);
//            return elo;
        	return null;
        } catch (Exception var7) {
            throw new EJBException(var7.getMessage(), var7);
        }
    }

    public EntityList getAllAmmModelsExceptThis(Object pk) {
        AmmModelDAO dao = new AmmModelDAO();
        return dao.getAllAmmModelsExceptThis(pk);
    }

    public EntityList getAllTaskGroups(Object key) {
        TaskGroupDAO dao = new TaskGroupDAO();
        return dao.getAllTaskGroups(key);
    }

    public AllXmlFormsAndProfilesELO getAllXmlFormsAndProfiles(String param1, String param2, String param3) throws EJBException {
        AllXmlFormsAndProfilesELO elo = null;

        try {
            XmlFormAccessor e = new XmlFormAccessor(new InitialContext());
            elo = e.getAllXmlFormsAndProfiles(param1, param2, param3);
            return elo;
        } catch (Exception var6) {
            throw new EJBException(var6.getMessage(), var6);
        }
    }

    public List<Integer> getReadOnlyRaAccessPositions(int modelId, int userId) {
        ModelDAO dao = new ModelDAO();

        try {
            return dao.getReadOnlyRaAccessPositions(modelId, userId);
        } catch (Exception var5) {
            var5.printStackTrace();
            return Collections.EMPTY_LIST;
        }
    }

    public EntityList getNodeAndUpUserAssignments(int structureElementId, int structureId) {
        BudgetUserDAO dao = new BudgetUserDAO();
        return dao.getNodeAndUpUserAssignments(structureElementId, structureId);
    }

    public UserMessageAttributesForIdELO getUserMessageAttributesForMultipleIds(String[] params) throws EJBException {
        UserMessageAttributesForIdELO elo = null;
        try {
            UserAccessor accessor = new UserAccessor(new InitialContext());

            return accessor.getUserMessageAttributesForMultiplIds(params);
        } catch (Exception e) {
            throw new EJBException(e.getMessage(), e);
        }
    }

    public EntityList getMailDetailForUser(String userName, int type, int from, int to) {
        EntityList returnValue = null;
        MessageDAO dao = new MessageDAO();
        try {
            returnValue = dao.getMailDetailForUser(userName, type, from, to);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return returnValue;
    }

    public AllMasterQuestionsELO getAllMasterQuestions() throws EJBException {
        AllMasterQuestionsELO elo = null;
        try {
            MasterQuestionAccessor accessor = new MasterQuestionAccessor(new InitialContext());

            return accessor.getAllMasterQuestions();
        } catch (Exception e) {
            throw new EJBException(e.getMessage(), e);
        }
    }

    public QuestionByIDELO getQuestionByID(int param1) throws EJBException {
        QuestionByIDELO elo = null;
        try {
            MasterQuestionAccessor accessor = new MasterQuestionAccessor(new InitialContext());

            return accessor.getQuestionByID(param1);
        } catch (Exception e) {
            throw new EJBException(e.getMessage(), e);
        }
    }

    public AllChallengeQuestionsELO getAllChallengeQuestions() throws EJBException {
        AllChallengeQuestionsELO elo = null;
        try {
            UserAccessor accessor = new UserAccessor(new InitialContext());

            return accessor.getAllChallengeQuestions();
        } catch (Exception e) {
            throw new EJBException(e.getMessage(), e);
        }
    }

    public AllQuestionsAndAnswersByUserIDELO getAllQuestionsAndAnswersByUserID(int param1) throws EJBException {
        AllQuestionsAndAnswersByUserIDELO elo = null;
        try {
            UserAccessor accessor = new UserAccessor(new InitialContext());

            return accessor.getAllQuestionsAndAnswersByUserID(param1);
        } catch (Exception e) {
            throw new EJBException(e.getMessage(), e);
        }
    }

    public AllQuestionsAndAnswersByUserIDELO getChallengeWord(int userId) throws EJBException {
        AllQuestionsAndAnswersByUserIDELO elo = null;
        try {
            UserAccessor accessor = new UserAccessor(new InitialContext());

            return accessor.getChallengeWord(userId);
        } catch (Exception e) {
            throw new EJBException(e.getMessage(), e);
        }
    }

    public void setChallengeWord(int userId, String word) throws EJBException {
        AllQuestionsAndAnswersByUserIDELO elo = null;
        try {
            UserAccessor accessor = new UserAccessor(new InitialContext());
            accessor.setChallengeWord(userId, word);
        } catch (Exception e) {
            throw new EJBException(e.getMessage(), e);
        }
    }

    public AllUserResetLinksELO getAllUserResetLinks() throws EJBException {
        AllUserResetLinksELO elo = null;
        try {
            UserAccessor accessor = new UserAccessor(new InitialContext());

            return accessor.getAllUserResetLinks();
        } catch (Exception e) {
            throw new EJBException(e.getMessage(), e);
        }
    }

    public LinkByUserIDELO getLinkByUserID(int param1) throws EJBException {
        LinkByUserIDELO elo = null;
        try {
            UserAccessor accessor = new UserAccessor(new InitialContext());

            return accessor.getLinkByUserID(param1);
        } catch (Exception e) {
            throw new EJBException(e.getMessage(), e);
        }
    }

    public EntityList getModelUserSecurity() {
        EntityList returnValue = null;
        ModelDAO dao = new ModelDAO();
        try {
            returnValue = dao.getModelUserSecurity();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return returnValue;
    }

    public EntityList getUserModelSecurity() {
        EntityList returnValue = null;
        BudgetUserDAO dao = new BudgetUserDAO();
        try {
            returnValue = dao.getUserModelSecurity();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return returnValue;
    }

    public List<UserModelElementAssignment> getRespAreaAccess(PrimaryKey pk) throws EJBException {
        List<UserModelElementAssignment> returnValue = null;
        BudgetUserDAO dao = new BudgetUserDAO();
        try {
            returnValue = dao.getRespAreaAccess(pk);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return returnValue;
    }

    public List<Object[]> getDataEntryProfileForBcAndUser(int bcId, int userId) throws EJBException {
        List<Object[]> returnValue = null;
        DataEntryProfileDAO dao = new DataEntryProfileDAO();

        try {
            returnValue = dao.getDataProfileForBudgetCycleAndUser(userId, bcId);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return returnValue;
    }

    public String getCPContextId(Object context) throws EJBException {
        String returnValue = null;

        try {
            returnValue = CPContextCache.getCPContextId(context);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return returnValue;
    }

    public Object getCPContext(String id) throws EJBException {
        Object returnValue = null;

        try {
            returnValue = CPContextCache.getCPContext(id);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return returnValue;
    }

    public void removeContext(Object context) {
        try {
            CPContextCache.remove(context);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void removeContextByContextId(List<String> contextIds) {
        try {
            CPContextCache.removeContextByContextId(contextIds);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void removeContextByUserName(List<String> userNames) {
        try {
            CPContextCache.removeContextByUserName(userNames);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Map getContextSnapShot() {
        Map returnValue = null;
        try {
            returnValue = CPContextCache.getContextSnapShot();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return returnValue;
    }

    public EntityList getAllLoggedInUsers() {
        EntityList returnValue = null;
        try {
            returnValue = (EntityList) CPContextCache.getAllLoggedInUsers();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return returnValue;
    }

    public EntityList getModelsAndRAHierarchies() {
        EntityList returnValue = null;
        try {
            returnValue = new BudgetUserDAO().getModelsAndRAHierarchies();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return returnValue;
    }

    public AllXmlFormsELO getXcellXmlFormsForUser(int userId) throws EJBException {
        AllXmlFormsELO elo = null;

        try {
            XmlFormAccessor e = new XmlFormAccessor(new InitialContext());
            elo = e.getXcellXmlFormsForUser(userId);
            return elo;
        } catch (Exception var3) {
            throw new EJBException(var3.getMessage(), var3);
        }
    }

    public ArrayList<Object[]> getNotesForCostCenters(ArrayList<Integer> costCenters, int financeCubeId, String fromDate, String toDate) {
        ArrayList<Object[]> returnValue = null;
        try {
            FormNotesDAO dao = new FormNotesDAO();
            returnValue = dao.getNotesForCostCenters(costCenters, financeCubeId, fromDate, toDate);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return returnValue;
    }

    public HashMap<String, ArrayList<HierarchyRef>> getCalendarForModels(HashSet<String> models) {
        HashMap<String, ArrayList<HierarchyRef>> returnValue = null;
        try {
            HierarchyDAO dao = new HierarchyDAO();
            returnValue = dao.getCalendarForModels(models);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return returnValue;
    }
    

    public AllDashboardsForUserELO getDashboardForms(Integer userId, boolean isAdmin) throws EJBException {
        AllDashboardsForUserELO elo = null;
        try {
            UserAccessor e = new UserAccessor(new InitialContext());
            elo = e.getDashboardForms(userId, isAdmin);
            return elo;
        } catch (Exception var3) {
            throw new EJBException(var3.getMessage(), var3);
        }
    }
}
