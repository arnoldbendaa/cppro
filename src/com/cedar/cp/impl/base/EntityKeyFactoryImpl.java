/*     */ package com.cedar.cp.impl.base;
/*     */ 
/*     */ import com.cedar.cp.api.base.EntityKeyFactory;
/*     */ import com.cedar.cp.dto.admin.tidytask.TidyTaskCK;
/*     */ import com.cedar.cp.dto.admin.tidytask.TidyTaskLinkCK;
/*     */ import com.cedar.cp.dto.admin.tidytask.TidyTaskLinkPK;
/*     */ import com.cedar.cp.dto.admin.tidytask.TidyTaskPK;
/*     */ import com.cedar.cp.dto.authenticationpolicy.AuthenticationPolicyCK;
/*     */ import com.cedar.cp.dto.authenticationpolicy.AuthenticationPolicyPK;
/*     */ import com.cedar.cp.dto.budgetinstruction.BudgetInstructionAssignmentCK;
/*     */ import com.cedar.cp.dto.budgetinstruction.BudgetInstructionAssignmentPK;
/*     */ import com.cedar.cp.dto.budgetinstruction.BudgetInstructionCK;
/*     */ import com.cedar.cp.dto.budgetinstruction.BudgetInstructionPK;
/*     */ import com.cedar.cp.dto.cm.ChangeMgmtCK;
/*     */ import com.cedar.cp.dto.cm.ChangeMgmtPK;
/*     */ import com.cedar.cp.dto.cubeformula.CubeFormulaCK;
/*     */ import com.cedar.cp.dto.cubeformula.CubeFormulaPK;
/*     */ import com.cedar.cp.dto.cubeformula.CubeFormulaPackageCK;
/*     */ import com.cedar.cp.dto.cubeformula.CubeFormulaPackagePK;
/*     */ import com.cedar.cp.dto.cubeformula.FormulaDeploymentDtCK;
/*     */ import com.cedar.cp.dto.cubeformula.FormulaDeploymentDtPK;
/*     */ import com.cedar.cp.dto.cubeformula.FormulaDeploymentEntryCK;
/*     */ import com.cedar.cp.dto.cubeformula.FormulaDeploymentEntryPK;
/*     */ import com.cedar.cp.dto.cubeformula.FormulaDeploymentLineCK;
/*     */ import com.cedar.cp.dto.cubeformula.FormulaDeploymentLinePK;
/*     */ import com.cedar.cp.dto.currency.CurrencyCK;
/*     */ import com.cedar.cp.dto.currency.CurrencyPK;
/*     */ import com.cedar.cp.dto.datatype.DataTypeCK;
/*     */ import com.cedar.cp.dto.datatype.DataTypePK;
/*     */ import com.cedar.cp.dto.datatype.DataTypeRelCK;
/*     */ import com.cedar.cp.dto.datatype.DataTypeRelPK;
/*     */ import com.cedar.cp.dto.defaultuserpref.DefaultUserPrefCK;
/*     */ import com.cedar.cp.dto.defaultuserpref.DefaultUserPrefPK;
/*     */ import com.cedar.cp.dto.dimension.AugHierarchyElementCK;
/*     */ import com.cedar.cp.dto.dimension.AugHierarchyElementPK;
/*     */ import com.cedar.cp.dto.dimension.CalendarSpecCK;
/*     */ import com.cedar.cp.dto.dimension.CalendarSpecPK;
/*     */ import com.cedar.cp.dto.dimension.CalendarYearSpecCK;
/*     */ import com.cedar.cp.dto.dimension.CalendarYearSpecPK;
/*     */ import com.cedar.cp.dto.dimension.DimensionCK;
/*     */ import com.cedar.cp.dto.dimension.DimensionElementCK;
/*     */ import com.cedar.cp.dto.dimension.DimensionElementPK;
/*     */ import com.cedar.cp.dto.dimension.DimensionPK;
/*     */ import com.cedar.cp.dto.dimension.HierarchyCK;
/*     */ import com.cedar.cp.dto.dimension.HierarchyElementCK;
/*     */ import com.cedar.cp.dto.dimension.HierarchyElementFeedCK;
/*     */ import com.cedar.cp.dto.dimension.HierarchyElementFeedPK;
/*     */ import com.cedar.cp.dto.dimension.HierarchyElementPK;
/*     */ import com.cedar.cp.dto.dimension.HierarchyPK;
/*     */ import com.cedar.cp.dto.dimension.SecurityRangeCK;
/*     */ import com.cedar.cp.dto.dimension.SecurityRangePK;
/*     */ import com.cedar.cp.dto.dimension.SecurityRangeRowCK;
/*     */ import com.cedar.cp.dto.dimension.SecurityRangeRowPK;
/*     */ import com.cedar.cp.dto.dimension.StructureElementCK;
/*     */ import com.cedar.cp.dto.dimension.StructureElementPK;
/*     */ import com.cedar.cp.dto.extendedattachment.ExtendedAttachmentCK;
/*     */ import com.cedar.cp.dto.extendedattachment.ExtendedAttachmentPK;
/*     */ import com.cedar.cp.dto.extsys.ExtSysCalElementCK;
/*     */ import com.cedar.cp.dto.extsys.ExtSysCalElementPK;
/*     */ import com.cedar.cp.dto.extsys.ExtSysCalendarYearCK;
/*     */ import com.cedar.cp.dto.extsys.ExtSysCalendarYearPK;
/*     */ import com.cedar.cp.dto.extsys.ExtSysCompanyCK;
/*     */ import com.cedar.cp.dto.extsys.ExtSysCompanyPK;
/*     */ import com.cedar.cp.dto.extsys.ExtSysCurrencyCK;
/*     */ import com.cedar.cp.dto.extsys.ExtSysCurrencyPK;
/*     */ import com.cedar.cp.dto.extsys.ExtSysDimElementCK;
/*     */ import com.cedar.cp.dto.extsys.ExtSysDimElementPK;
/*     */ import com.cedar.cp.dto.extsys.ExtSysDimensionCK;
/*     */ import com.cedar.cp.dto.extsys.ExtSysDimensionPK;
/*     */ import com.cedar.cp.dto.extsys.ExtSysHierElemFeedCK;
/*     */ import com.cedar.cp.dto.extsys.ExtSysHierElemFeedPK;
/*     */ import com.cedar.cp.dto.extsys.ExtSysHierElementCK;
/*     */ import com.cedar.cp.dto.extsys.ExtSysHierElementPK;
/*     */ import com.cedar.cp.dto.extsys.ExtSysHierarchyCK;
/*     */ import com.cedar.cp.dto.extsys.ExtSysHierarchyPK;
/*     */ import com.cedar.cp.dto.extsys.ExtSysLedgerCK;
/*     */ import com.cedar.cp.dto.extsys.ExtSysLedgerPK;
/*     */ import com.cedar.cp.dto.extsys.ExtSysPropertyCK;
/*     */ import com.cedar.cp.dto.extsys.ExtSysPropertyPK;
/*     */ import com.cedar.cp.dto.extsys.ExtSysValueTypeCK;
/*     */ import com.cedar.cp.dto.extsys.ExtSysValueTypePK;
/*     */ import com.cedar.cp.dto.extsys.ExternalSystemCK;
/*     */ import com.cedar.cp.dto.extsys.ExternalSystemPK;
/*     */ import com.cedar.cp.dto.formnotes.FormNotesCK;
/*     */ import com.cedar.cp.dto.formnotes.FormNotesPK;
/*     */ import com.cedar.cp.dto.impexp.ImpExpHdrCK;
/*     */ import com.cedar.cp.dto.impexp.ImpExpHdrPK;
/*     */ import com.cedar.cp.dto.impexp.ImpExpRowCK;
/*     */ import com.cedar.cp.dto.impexp.ImpExpRowPK;
/*     */ import com.cedar.cp.dto.logonhistory.LogonHistoryCK;
/*     */ import com.cedar.cp.dto.logonhistory.LogonHistoryPK;
import com.cedar.cp.dto.masterquestion.MasterQuestionCK;
import com.cedar.cp.dto.masterquestion.MasterQuestionPK;
/*     */ import com.cedar.cp.dto.message.MessageAttatchCK;
/*     */ import com.cedar.cp.dto.message.MessageAttatchPK;
/*     */ import com.cedar.cp.dto.message.MessageCK;
/*     */ import com.cedar.cp.dto.message.MessagePK;
/*     */ import com.cedar.cp.dto.message.MessageUserCK;
/*     */ import com.cedar.cp.dto.message.MessageUserPK;
/*     */ import com.cedar.cp.dto.model.BudgetCycleCK;
import com.cedar.cp.dto.model.BudgetCycleLinkCK;
import com.cedar.cp.dto.model.BudgetCycleLinkPK;
/*     */ import com.cedar.cp.dto.model.BudgetCyclePK;
/*     */ import com.cedar.cp.dto.model.BudgetStateCK;
/*     */ import com.cedar.cp.dto.model.BudgetStateHistoryCK;
/*     */ import com.cedar.cp.dto.model.BudgetStateHistoryPK;
/*     */ import com.cedar.cp.dto.model.BudgetStatePK;
/*     */ import com.cedar.cp.dto.model.BudgetUserCK;
/*     */ import com.cedar.cp.dto.model.BudgetUserPK;
/*     */ import com.cedar.cp.dto.model.CellCalcAssocCK;
/*     */ import com.cedar.cp.dto.model.CellCalcAssocPK;
/*     */ import com.cedar.cp.dto.model.CellCalcCK;
/*     */ import com.cedar.cp.dto.model.CellCalcPK;
/*     */ import com.cedar.cp.dto.model.FinanceCubeCK;
/*     */ import com.cedar.cp.dto.model.FinanceCubeDataTypeCK;
/*     */ import com.cedar.cp.dto.model.FinanceCubeDataTypePK;
/*     */ import com.cedar.cp.dto.model.FinanceCubePK;
/*     */ import com.cedar.cp.dto.model.LevelDateCK;
/*     */ import com.cedar.cp.dto.model.LevelDatePK;
/*     */ import com.cedar.cp.dto.model.ModelCK;
/*     */ import com.cedar.cp.dto.model.ModelDimensionRelCK;
/*     */ import com.cedar.cp.dto.model.ModelDimensionRelPK;
/*     */ import com.cedar.cp.dto.model.ModelPK;
/*     */ import com.cedar.cp.dto.model.ModelPropertyCK;
/*     */ import com.cedar.cp.dto.model.ModelPropertyPK;
/*     */ import com.cedar.cp.dto.model.RollUpRuleCK;
/*     */ import com.cedar.cp.dto.model.RollUpRulePK;
/*     */ import com.cedar.cp.dto.model.SecurityAccRngRelCK;
/*     */ import com.cedar.cp.dto.model.SecurityAccRngRelPK;
/*     */ import com.cedar.cp.dto.model.SecurityAccessDefCK;
/*     */ import com.cedar.cp.dto.model.SecurityAccessDefPK;
/*     */ import com.cedar.cp.dto.model.SecurityGroupCK;
/*     */ import com.cedar.cp.dto.model.SecurityGroupPK;
/*     */ import com.cedar.cp.dto.model.SecurityGroupUserRelCK;
/*     */ import com.cedar.cp.dto.model.SecurityGroupUserRelPK;
/*     */ import com.cedar.cp.dto.model.act.BudgetActivityCK;
/*     */ import com.cedar.cp.dto.model.act.BudgetActivityLinkCK;
/*     */ import com.cedar.cp.dto.model.act.BudgetActivityLinkPK;
/*     */ import com.cedar.cp.dto.model.act.BudgetActivityPK;
/*     */ import com.cedar.cp.dto.model.amm.AmmDataTypeCK;
/*     */ import com.cedar.cp.dto.model.amm.AmmDataTypePK;
/*     */ import com.cedar.cp.dto.model.amm.AmmDimensionCK;
/*     */ import com.cedar.cp.dto.model.amm.AmmDimensionElementCK;
/*     */ import com.cedar.cp.dto.model.amm.AmmDimensionElementPK;
/*     */ import com.cedar.cp.dto.model.amm.AmmDimensionPK;
/*     */ import com.cedar.cp.dto.model.amm.AmmFinanceCubeCK;
/*     */ import com.cedar.cp.dto.model.amm.AmmFinanceCubePK;
/*     */ import com.cedar.cp.dto.model.amm.AmmModelCK;
/*     */ import com.cedar.cp.dto.model.amm.AmmModelPK;
/*     */ import com.cedar.cp.dto.model.amm.AmmSrcStructureElementCK;
/*     */ import com.cedar.cp.dto.model.amm.AmmSrcStructureElementPK;
/*     */ import com.cedar.cp.dto.model.budgetlimit.BudgetLimitCK;
/*     */ import com.cedar.cp.dto.model.budgetlimit.BudgetLimitPK;
/*     */ import com.cedar.cp.dto.model.cc.CcDeploymentCK;
/*     */ import com.cedar.cp.dto.model.cc.CcDeploymentDataTypeCK;
/*     */ import com.cedar.cp.dto.model.cc.CcDeploymentDataTypePK;
/*     */ import com.cedar.cp.dto.model.cc.CcDeploymentEntryCK;
/*     */ import com.cedar.cp.dto.model.cc.CcDeploymentEntryPK;
/*     */ import com.cedar.cp.dto.model.cc.CcDeploymentLineCK;
/*     */ import com.cedar.cp.dto.model.cc.CcDeploymentLinePK;
/*     */ import com.cedar.cp.dto.model.cc.CcDeploymentPK;
/*     */ import com.cedar.cp.dto.model.cc.CcMappingEntryCK;
/*     */ import com.cedar.cp.dto.model.cc.CcMappingEntryPK;
/*     */ import com.cedar.cp.dto.model.cc.CcMappingLineCK;
/*     */ import com.cedar.cp.dto.model.cc.CcMappingLinePK;
/*     */ import com.cedar.cp.dto.model.cc.imp.dyn.ImportGridCK;
/*     */ import com.cedar.cp.dto.model.cc.imp.dyn.ImportGridCellCK;
/*     */ import com.cedar.cp.dto.model.cc.imp.dyn.ImportGridCellPK;
/*     */ import com.cedar.cp.dto.model.cc.imp.dyn.ImportGridPK;
/*     */ import com.cedar.cp.dto.model.mapping.MappedCalendarElementCK;
/*     */ import com.cedar.cp.dto.model.mapping.MappedCalendarElementPK;
/*     */ import com.cedar.cp.dto.model.mapping.MappedCalendarYearCK;
/*     */ import com.cedar.cp.dto.model.mapping.MappedCalendarYearPK;
/*     */ import com.cedar.cp.dto.model.mapping.MappedDataTypeCK;
/*     */ import com.cedar.cp.dto.model.mapping.MappedDataTypePK;
/*     */ import com.cedar.cp.dto.model.mapping.MappedDimensionCK;
/*     */ import com.cedar.cp.dto.model.mapping.MappedDimensionElementCK;
/*     */ import com.cedar.cp.dto.model.mapping.MappedDimensionElementPK;
/*     */ import com.cedar.cp.dto.model.mapping.MappedDimensionPK;
/*     */ import com.cedar.cp.dto.model.mapping.MappedFinanceCubeCK;
/*     */ import com.cedar.cp.dto.model.mapping.MappedFinanceCubePK;
/*     */ import com.cedar.cp.dto.model.mapping.MappedHierarchyCK;
/*     */ import com.cedar.cp.dto.model.mapping.MappedHierarchyPK;
/*     */ import com.cedar.cp.dto.model.mapping.MappedModelCK;
/*     */ import com.cedar.cp.dto.model.mapping.MappedModelPK;
/*     */ import com.cedar.cp.dto.model.ra.ResponsibilityAreaCK;
/*     */ import com.cedar.cp.dto.model.ra.ResponsibilityAreaPK;
/*     */ import com.cedar.cp.dto.model.recharge.RechargeCK;
/*     */ import com.cedar.cp.dto.model.recharge.RechargeCellsCK;
/*     */ import com.cedar.cp.dto.model.recharge.RechargeCellsPK;
/*     */ import com.cedar.cp.dto.model.recharge.RechargeGroupCK;
/*     */ import com.cedar.cp.dto.model.recharge.RechargeGroupPK;
/*     */ import com.cedar.cp.dto.model.recharge.RechargePK;
/*     */ import com.cedar.cp.dto.model.udwp.WeightingDeploymentCK;
/*     */ import com.cedar.cp.dto.model.udwp.WeightingDeploymentLineCK;
/*     */ import com.cedar.cp.dto.model.udwp.WeightingDeploymentLinePK;
/*     */ import com.cedar.cp.dto.model.udwp.WeightingDeploymentPK;
/*     */ import com.cedar.cp.dto.model.udwp.WeightingProfileCK;
/*     */ import com.cedar.cp.dto.model.udwp.WeightingProfileLineCK;
/*     */ import com.cedar.cp.dto.model.udwp.WeightingProfileLinePK;
/*     */ import com.cedar.cp.dto.model.udwp.WeightingProfilePK;
/*     */ import com.cedar.cp.dto.model.virement.VirementAccountCK;
/*     */ import com.cedar.cp.dto.model.virement.VirementAccountPK;
/*     */ import com.cedar.cp.dto.model.virement.VirementAuthPointCK;
/*     */ import com.cedar.cp.dto.model.virement.VirementAuthPointLinkCK;
/*     */ import com.cedar.cp.dto.model.virement.VirementAuthPointLinkPK;
/*     */ import com.cedar.cp.dto.model.virement.VirementAuthPointPK;
/*     */ import com.cedar.cp.dto.model.virement.VirementAuthorisersCK;
/*     */ import com.cedar.cp.dto.model.virement.VirementAuthorisersPK;
/*     */ import com.cedar.cp.dto.model.virement.VirementCategoryCK;
/*     */ import com.cedar.cp.dto.model.virement.VirementCategoryPK;
/*     */ import com.cedar.cp.dto.model.virement.VirementLineSpreadCK;
/*     */ import com.cedar.cp.dto.model.virement.VirementLineSpreadPK;
/*     */ import com.cedar.cp.dto.model.virement.VirementLocationCK;
/*     */ import com.cedar.cp.dto.model.virement.VirementLocationPK;
/*     */ import com.cedar.cp.dto.model.virement.VirementRequestCK;
/*     */ import com.cedar.cp.dto.model.virement.VirementRequestGroupCK;
/*     */ import com.cedar.cp.dto.model.virement.VirementRequestGroupPK;
/*     */ import com.cedar.cp.dto.model.virement.VirementRequestLineCK;
/*     */ import com.cedar.cp.dto.model.virement.VirementRequestLinePK;
/*     */ import com.cedar.cp.dto.model.virement.VirementRequestPK;
/*     */ import com.cedar.cp.dto.passwordhistory.PasswordHistoryCK;
/*     */ import com.cedar.cp.dto.passwordhistory.PasswordHistoryPK;
/*     */ import com.cedar.cp.dto.perftest.PerfTestCK;
/*     */ import com.cedar.cp.dto.perftest.PerfTestPK;
/*     */ import com.cedar.cp.dto.perftestrun.PerfTestRunCK;
/*     */ import com.cedar.cp.dto.perftestrun.PerfTestRunPK;
/*     */ import com.cedar.cp.dto.perftestrun.PerfTestRunResultCK;
/*     */ import com.cedar.cp.dto.perftestrun.PerfTestRunResultPK;
/*     */ import com.cedar.cp.dto.rechargegroup.RechargeGroupRelCK;
/*     */ import com.cedar.cp.dto.rechargegroup.RechargeGroupRelPK;
/*     */ import com.cedar.cp.dto.report.ReportCK;
/*     */ import com.cedar.cp.dto.report.ReportPK;
/*     */ import com.cedar.cp.dto.report.definition.ReportDefCalculatorCK;
/*     */ import com.cedar.cp.dto.report.definition.ReportDefCalculatorPK;
/*     */ import com.cedar.cp.dto.report.definition.ReportDefFormCK;
/*     */ import com.cedar.cp.dto.report.definition.ReportDefFormPK;
/*     */ import com.cedar.cp.dto.report.definition.ReportDefMappedExcelCK;
/*     */ import com.cedar.cp.dto.report.definition.ReportDefMappedExcelPK;
/*     */ import com.cedar.cp.dto.report.definition.ReportDefSummaryCalcCK;
/*     */ import com.cedar.cp.dto.report.definition.ReportDefSummaryCalcPK;
/*     */ import com.cedar.cp.dto.report.definition.ReportDefinitionCK;
/*     */ import com.cedar.cp.dto.report.definition.ReportDefinitionPK;
/*     */ import com.cedar.cp.dto.report.destination.external.ExternalDestinationCK;
/*     */ import com.cedar.cp.dto.report.destination.external.ExternalDestinationPK;
/*     */ import com.cedar.cp.dto.report.destination.external.ExternalDestinationUsersCK;
/*     */ import com.cedar.cp.dto.report.destination.external.ExternalDestinationUsersPK;
/*     */ import com.cedar.cp.dto.report.destination.internal.InternalDestinationCK;
/*     */ import com.cedar.cp.dto.report.destination.internal.InternalDestinationPK;
/*     */ import com.cedar.cp.dto.report.destination.internal.InternalDestinationUsersCK;
/*     */ import com.cedar.cp.dto.report.destination.internal.InternalDestinationUsersPK;
/*     */ import com.cedar.cp.dto.report.distribution.DistributionCK;
/*     */ import com.cedar.cp.dto.report.distribution.DistributionLinkCK;
/*     */ import com.cedar.cp.dto.report.distribution.DistributionLinkPK;
/*     */ import com.cedar.cp.dto.report.distribution.DistributionPK;
/*     */ import com.cedar.cp.dto.report.mappingtemplate.ReportMappingTemplateCK;
/*     */ import com.cedar.cp.dto.report.mappingtemplate.ReportMappingTemplatePK;
/*     */ import com.cedar.cp.dto.report.pack.ReportPackCK;
/*     */ import com.cedar.cp.dto.report.pack.ReportPackLinkCK;
/*     */ import com.cedar.cp.dto.report.pack.ReportPackLinkPK;
/*     */ import com.cedar.cp.dto.report.pack.ReportPackPK;
/*     */ import com.cedar.cp.dto.report.task.ReportGroupingCK;
/*     */ import com.cedar.cp.dto.report.task.ReportGroupingFileCK;
/*     */ import com.cedar.cp.dto.report.task.ReportGroupingFilePK;
/*     */ import com.cedar.cp.dto.report.task.ReportGroupingPK;
/*     */ import com.cedar.cp.dto.report.template.ReportTemplateCK;
/*     */ import com.cedar.cp.dto.report.template.ReportTemplatePK;
/*     */ import com.cedar.cp.dto.report.tran.CubePendingTranCK;
/*     */ import com.cedar.cp.dto.report.tran.CubePendingTranPK;
/*     */ import com.cedar.cp.dto.report.type.ReportTypeCK;
/*     */ import com.cedar.cp.dto.report.type.ReportTypePK;
/*     */ import com.cedar.cp.dto.report.type.param.ReportTypeParamCK;
/*     */ import com.cedar.cp.dto.report.type.param.ReportTypeParamPK;
import com.cedar.cp.dto.reset.ChallengeQuestionCK;
import com.cedar.cp.dto.reset.ChallengeQuestionPK;
import com.cedar.cp.dto.reset.UserResetLinkCK;
import com.cedar.cp.dto.reset.UserResetLinkPK;
/*     */ import com.cedar.cp.dto.role.RoleCK;
/*     */ import com.cedar.cp.dto.role.RolePK;
/*     */ import com.cedar.cp.dto.role.RoleSecurityCK;
/*     */ import com.cedar.cp.dto.role.RoleSecurityPK;
/*     */ import com.cedar.cp.dto.role.RoleSecurityRelCK;
/*     */ import com.cedar.cp.dto.role.RoleSecurityRelPK;
/*     */ import com.cedar.cp.dto.systemproperty.SystemPropertyCK;
/*     */ import com.cedar.cp.dto.systemproperty.SystemPropertyPK;
/*     */ import com.cedar.cp.dto.task.TaskCK;
/*     */ import com.cedar.cp.dto.task.TaskPK;
/*     */ import com.cedar.cp.dto.task.group.TaskGroupCK;
/*     */ import com.cedar.cp.dto.task.group.TaskGroupPK;
/*     */ import com.cedar.cp.dto.task.group.TgRowCK;
/*     */ import com.cedar.cp.dto.task.group.TgRowPK;
/*     */ import com.cedar.cp.dto.task.group.TgRowParamCK;
/*     */ import com.cedar.cp.dto.task.group.TgRowParamPK;
/*     */ import com.cedar.cp.dto.udeflookup.UdefLookupCK;
/*     */ import com.cedar.cp.dto.udeflookup.UdefLookupColumnDefCK;
/*     */ import com.cedar.cp.dto.udeflookup.UdefLookupColumnDefPK;
/*     */ import com.cedar.cp.dto.udeflookup.UdefLookupPK;
/*     */ import com.cedar.cp.dto.user.DataEntryProfileCK;
/*     */ import com.cedar.cp.dto.user.DataEntryProfileHistoryCK;
/*     */ import com.cedar.cp.dto.user.DataEntryProfileHistoryPK;
/*     */ import com.cedar.cp.dto.user.DataEntryProfilePK;
/*     */ import com.cedar.cp.dto.user.UserCK;
/*     */ import com.cedar.cp.dto.user.UserPK;
/*     */ import com.cedar.cp.dto.user.UserPreferenceCK;
/*     */ import com.cedar.cp.dto.user.UserPreferencePK;
/*     */ import com.cedar.cp.dto.user.UserRoleCK;
/*     */ import com.cedar.cp.dto.user.UserRolePK;
/*     */ import com.cedar.cp.dto.xmlform.XmlFormCK;
/*     */ import com.cedar.cp.dto.xmlform.XmlFormPK;
/*     */ import com.cedar.cp.dto.xmlform.XmlFormUserLinkCK;
/*     */ import com.cedar.cp.dto.xmlform.XmlFormUserLinkPK;
/*     */ import com.cedar.cp.dto.xmlform.rebuild.FormRebuildCK;
/*     */ import com.cedar.cp.dto.xmlform.rebuild.FormRebuildPK;
/*     */ import com.cedar.cp.dto.xmlreport.XmlReportCK;
/*     */ import com.cedar.cp.dto.xmlreport.XmlReportPK;
/*     */ import com.cedar.cp.dto.xmlreportfolder.XmlReportFolderCK;
import com.cedar.cp.dto.xmlreportfolder.XmlReportFolderPK;
/*     */ 
/*     */ public class EntityKeyFactoryImpl
/*     */   implements EntityKeyFactory
/*     */ {
/*     */   public Object getKeyFromTokens(String extKey)
/*     */   {
/*  13 */     if (extKey.startsWith("ModelCK|"))
/*  14 */       return ModelCK.getKeyFromTokens(extKey);
/*  15 */     if (extKey.startsWith("ModelPK|"))
/*  16 */       return ModelPK.getKeyFromTokens(extKey);
/*  17 */     if (extKey.startsWith("ModelPropertyCK|"))
/*  18 */       return ModelPropertyCK.getKeyFromTokens(extKey);
/*  19 */     if (extKey.startsWith("ModelPropertyPK|"))
/*  20 */       return ModelPropertyPK.getKeyFromTokens(extKey);
/*  21 */     if (extKey.startsWith("SecurityGroupUserRelCK|"))
/*  22 */       return SecurityGroupUserRelCK.getKeyFromTokens(extKey);
/*  23 */     if (extKey.startsWith("SecurityGroupUserRelPK|"))
/*  24 */       return SecurityGroupUserRelPK.getKeyFromTokens(extKey);
/*  25 */     if (extKey.startsWith("ModelDimensionRelCK|"))
/*  26 */       return ModelDimensionRelCK.getKeyFromTokens(extKey);
/*  27 */     if (extKey.startsWith("ModelDimensionRelPK|"))
/*  28 */       return ModelDimensionRelPK.getKeyFromTokens(extKey);
/*  29 */     if (extKey.startsWith("FinanceCubeCK|"))
/*  30 */       return FinanceCubeCK.getKeyFromTokens(extKey);
/*  31 */     if (extKey.startsWith("FinanceCubePK|"))
/*  32 */       return FinanceCubePK.getKeyFromTokens(extKey);
/*  33 */     if (extKey.startsWith("RollUpRuleCK|"))
/*  34 */       return RollUpRuleCK.getKeyFromTokens(extKey);
/*  35 */     if (extKey.startsWith("RollUpRulePK|"))
/*  36 */       return RollUpRulePK.getKeyFromTokens(extKey);
/*  37 */     if (extKey.startsWith("FinanceCubeDataTypeCK|"))
/*  38 */       return FinanceCubeDataTypeCK.getKeyFromTokens(extKey);
/*  39 */     if (extKey.startsWith("FinanceCubeDataTypePK|"))
/*  40 */       return FinanceCubeDataTypePK.getKeyFromTokens(extKey);
/*  41 */     if (extKey.startsWith("BudgetCycleCK|"))
/*  42 */       return BudgetCycleCK.getKeyFromTokens(extKey);
/*  43 */     if (extKey.startsWith("BudgetCyclePK|"))
/*  44 */       return BudgetCyclePK.getKeyFromTokens(extKey);
/*  45 */     if (extKey.startsWith("LevelDateCK|"))
/*  46 */       return LevelDateCK.getKeyFromTokens(extKey);
/*  47 */     if (extKey.startsWith("LevelDatePK|"))
/*  48 */       return LevelDatePK.getKeyFromTokens(extKey);
/*  49 */     if (extKey.startsWith("BudgetStateCK|"))
/*  50 */       return BudgetStateCK.getKeyFromTokens(extKey);
/*  51 */     if (extKey.startsWith("BudgetStatePK|"))
/*  52 */       return BudgetStatePK.getKeyFromTokens(extKey);
/*  53 */     if (extKey.startsWith("BudgetStateHistoryCK|"))
/*  54 */       return BudgetStateHistoryCK.getKeyFromTokens(extKey);
/*  55 */     if (extKey.startsWith("BudgetStateHistoryPK|"))
/*  56 */       return BudgetStateHistoryPK.getKeyFromTokens(extKey);
/*  57 */     if (extKey.startsWith("ResponsibilityAreaCK|"))
/*  58 */       return ResponsibilityAreaCK.getKeyFromTokens(extKey);
/*  59 */     if (extKey.startsWith("ResponsibilityAreaPK|"))
/*  60 */       return ResponsibilityAreaPK.getKeyFromTokens(extKey);
/*  61 */     if (extKey.startsWith("BudgetUserCK|"))
/*  62 */       return BudgetUserCK.getKeyFromTokens(extKey);
/*  63 */     if (extKey.startsWith("BudgetUserPK|"))
/*  64 */       return BudgetUserPK.getKeyFromTokens(extKey);
/*  65 */     if (extKey.startsWith("DataTypeCK|"))
/*  66 */       return DataTypeCK.getKeyFromTokens(extKey);
/*  67 */     if (extKey.startsWith("DataTypePK|"))
/*  68 */       return DataTypePK.getKeyFromTokens(extKey);
/*  69 */     if (extKey.startsWith("DataTypeRelCK|"))
/*  70 */       return DataTypeRelCK.getKeyFromTokens(extKey);
/*  71 */     if (extKey.startsWith("DataTypeRelPK|"))
/*  72 */       return DataTypeRelPK.getKeyFromTokens(extKey);
/*  73 */     if (extKey.startsWith("CurrencyCK|"))
/*  74 */       return CurrencyCK.getKeyFromTokens(extKey);
/*  75 */     if (extKey.startsWith("CurrencyPK|"))
/*  76 */       return CurrencyPK.getKeyFromTokens(extKey);
/*  77 */     if (extKey.startsWith("StructureElementCK|"))
/*  78 */       return StructureElementCK.getKeyFromTokens(extKey);
/*  79 */     if (extKey.startsWith("StructureElementPK|"))
/*  80 */       return StructureElementPK.getKeyFromTokens(extKey);
/*  81 */     if (extKey.startsWith("DimensionCK|"))
/*  82 */       return DimensionCK.getKeyFromTokens(extKey);
/*  83 */     if (extKey.startsWith("DimensionPK|"))
/*  84 */       return DimensionPK.getKeyFromTokens(extKey);
/*  85 */     if (extKey.startsWith("DimensionElementCK|"))
/*  86 */       return DimensionElementCK.getKeyFromTokens(extKey);
/*  87 */     if (extKey.startsWith("DimensionElementPK|"))
/*  88 */       return DimensionElementPK.getKeyFromTokens(extKey);
/*  89 */     if (extKey.startsWith("CalendarSpecCK|"))
/*  90 */       return CalendarSpecCK.getKeyFromTokens(extKey);
/*  91 */     if (extKey.startsWith("CalendarSpecPK|"))
/*  92 */       return CalendarSpecPK.getKeyFromTokens(extKey);
/*  93 */     if (extKey.startsWith("CalendarYearSpecCK|"))
/*  94 */       return CalendarYearSpecCK.getKeyFromTokens(extKey);
/*  95 */     if (extKey.startsWith("CalendarYearSpecPK|"))
/*  96 */       return CalendarYearSpecPK.getKeyFromTokens(extKey);
/*  97 */     if (extKey.startsWith("HierarchyCK|"))
/*  98 */       return HierarchyCK.getKeyFromTokens(extKey);
/*  99 */     if (extKey.startsWith("HierarchyPK|"))
/* 100 */       return HierarchyPK.getKeyFromTokens(extKey);
/* 101 */     if (extKey.startsWith("HierarchyElementCK|"))
/* 102 */       return HierarchyElementCK.getKeyFromTokens(extKey);
/* 103 */     if (extKey.startsWith("HierarchyElementPK|"))
/* 104 */       return HierarchyElementPK.getKeyFromTokens(extKey);
/* 105 */     if (extKey.startsWith("AugHierarchyElementCK|"))
/* 106 */       return AugHierarchyElementCK.getKeyFromTokens(extKey);
/* 107 */     if (extKey.startsWith("AugHierarchyElementPK|"))
/* 108 */       return AugHierarchyElementPK.getKeyFromTokens(extKey);
/* 109 */     if (extKey.startsWith("HierarchyElementFeedCK|"))
/* 110 */       return HierarchyElementFeedCK.getKeyFromTokens(extKey);
/* 111 */     if (extKey.startsWith("HierarchyElementFeedPK|"))
/* 112 */       return HierarchyElementFeedPK.getKeyFromTokens(extKey);
/* 113 */     if (extKey.startsWith("DefaultUserPrefCK|"))
/* 114 */       return DefaultUserPrefCK.getKeyFromTokens(extKey);
/* 115 */     if (extKey.startsWith("DefaultUserPrefPK|"))
/* 116 */       return DefaultUserPrefPK.getKeyFromTokens(extKey);
/* 117 */     if (extKey.startsWith("UserCK|"))
/* 118 */       return UserCK.getKeyFromTokens(extKey);
/* 119 */     if (extKey.startsWith("UserPK|"))
/* 120 */       return UserPK.getKeyFromTokens(extKey);
/* 121 */     if (extKey.startsWith("UserRoleCK|"))
/* 122 */       return UserRoleCK.getKeyFromTokens(extKey);
/* 123 */     if (extKey.startsWith("UserRolePK|"))
/* 124 */       return UserRolePK.getKeyFromTokens(extKey);
/* 125 */     if (extKey.startsWith("RoleCK|"))
/* 126 */       return RoleCK.getKeyFromTokens(extKey);
/* 127 */     if (extKey.startsWith("RolePK|"))
/* 128 */       return RolePK.getKeyFromTokens(extKey);
/* 129 */     if (extKey.startsWith("RoleSecurityRelCK|"))
/* 130 */       return RoleSecurityRelCK.getKeyFromTokens(extKey);
/* 131 */     if (extKey.startsWith("RoleSecurityRelPK|"))
/* 132 */       return RoleSecurityRelPK.getKeyFromTokens(extKey);
/* 133 */     if (extKey.startsWith("RoleSecurityCK|"))
/* 134 */       return RoleSecurityCK.getKeyFromTokens(extKey);
/* 135 */     if (extKey.startsWith("RoleSecurityPK|"))
/* 136 */       return RoleSecurityPK.getKeyFromTokens(extKey);
/* 137 */     if (extKey.startsWith("UserPreferenceCK|"))
/* 138 */       return UserPreferenceCK.getKeyFromTokens(extKey);
/* 139 */     if (extKey.startsWith("UserPreferencePK|"))
/* 140 */       return UserPreferencePK.getKeyFromTokens(extKey);
/* 141 */     if (extKey.startsWith("BudgetInstructionCK|"))
/* 142 */       return BudgetInstructionCK.getKeyFromTokens(extKey);
/* 143 */     if (extKey.startsWith("BudgetInstructionPK|"))
/* 144 */       return BudgetInstructionPK.getKeyFromTokens(extKey);
/* 145 */     if (extKey.startsWith("BudgetInstructionAssignmentCK|"))
/* 146 */       return BudgetInstructionAssignmentCK.getKeyFromTokens(extKey);
/* 147 */     if (extKey.startsWith("BudgetInstructionAssignmentPK|"))
/* 148 */       return BudgetInstructionAssignmentPK.getKeyFromTokens(extKey);
/* 149 */     if (extKey.startsWith("TaskCK|"))
/* 150 */       return TaskCK.getKeyFromTokens(extKey);
/* 151 */     if (extKey.startsWith("TaskPK|"))
/* 152 */       return TaskPK.getKeyFromTokens(extKey);
/* 153 */     if (extKey.startsWith("SystemPropertyCK|"))
/* 154 */       return SystemPropertyCK.getKeyFromTokens(extKey);
/* 155 */     if (extKey.startsWith("SystemPropertyPK|"))
/* 156 */       return SystemPropertyPK.getKeyFromTokens(extKey);
/* 157 */     if (extKey.startsWith("XmlFormCK|"))
/* 158 */       return XmlFormCK.getKeyFromTokens(extKey);
/* 159 */     if (extKey.startsWith("XmlFormPK|"))
/* 160 */       return XmlFormPK.getKeyFromTokens(extKey);
/* 161 */     if (extKey.startsWith("XmlFormUserLinkCK|"))
/* 162 */       return XmlFormUserLinkCK.getKeyFromTokens(extKey);
/* 163 */     if (extKey.startsWith("XmlFormUserLinkPK|"))
/* 164 */       return XmlFormUserLinkPK.getKeyFromTokens(extKey);
/* 165 */     if (extKey.startsWith("FormNotesCK|"))
/* 166 */       return FormNotesCK.getKeyFromTokens(extKey);
/* 167 */     if (extKey.startsWith("FormNotesPK|"))
/* 168 */       return FormNotesPK.getKeyFromTokens(extKey);
/* 169 */     if (extKey.startsWith("XmlReportCK|"))
/* 170 */       return XmlReportCK.getKeyFromTokens(extKey);
/* 171 */     if (extKey.startsWith("XmlReportPK|"))
/* 172 */       return XmlReportPK.getKeyFromTokens(extKey);
/* 173 */     if (extKey.startsWith("XmlReportFolderCK|"))
/* 174 */       return XmlReportFolderCK.getKeyFromTokens(extKey);
/* 175 */     if (extKey.startsWith("XmlReportFolderPK|"))
/* 176 */       return XmlReportFolderPK.getKeyFromTokens(extKey);
/* 177 */     if (extKey.startsWith("DataEntryProfileCK|"))
/* 178 */       return DataEntryProfileCK.getKeyFromTokens(extKey);
/* 179 */     if (extKey.startsWith("DataEntryProfilePK|"))
/* 180 */       return DataEntryProfilePK.getKeyFromTokens(extKey);
/* 181 */     if (extKey.startsWith("DataEntryProfileHistoryCK|"))
/* 182 */       return DataEntryProfileHistoryCK.getKeyFromTokens(extKey);
/* 183 */     if (extKey.startsWith("DataEntryProfileHistoryPK|"))
/* 184 */       return DataEntryProfileHistoryPK.getKeyFromTokens(extKey);
/* 185 */     if (extKey.startsWith("UdefLookupCK|"))
/* 186 */       return UdefLookupCK.getKeyFromTokens(extKey);
/* 187 */     if (extKey.startsWith("UdefLookupPK|"))
/* 188 */       return UdefLookupPK.getKeyFromTokens(extKey);
/* 189 */     if (extKey.startsWith("UdefLookupColumnDefCK|"))
/* 190 */       return UdefLookupColumnDefCK.getKeyFromTokens(extKey);
/* 191 */     if (extKey.startsWith("UdefLookupColumnDefPK|"))
/* 192 */       return UdefLookupColumnDefPK.getKeyFromTokens(extKey);
/* 193 */     if (extKey.startsWith("SecurityRangeCK|"))
/* 194 */       return SecurityRangeCK.getKeyFromTokens(extKey);
/* 195 */     if (extKey.startsWith("SecurityRangePK|"))
/* 196 */       return SecurityRangePK.getKeyFromTokens(extKey);
/* 197 */     if (extKey.startsWith("SecurityRangeRowCK|"))
/* 198 */       return SecurityRangeRowCK.getKeyFromTokens(extKey);
/* 199 */     if (extKey.startsWith("SecurityRangeRowPK|"))
/* 200 */       return SecurityRangeRowPK.getKeyFromTokens(extKey);
/* 201 */     if (extKey.startsWith("SecurityAccessDefCK|"))
/* 202 */       return SecurityAccessDefCK.getKeyFromTokens(extKey);
/* 203 */     if (extKey.startsWith("SecurityAccessDefPK|"))
/* 204 */       return SecurityAccessDefPK.getKeyFromTokens(extKey);
/* 205 */     if (extKey.startsWith("SecurityAccRngRelCK|"))
/* 206 */       return SecurityAccRngRelCK.getKeyFromTokens(extKey);
/* 207 */     if (extKey.startsWith("SecurityAccRngRelPK|"))
/* 208 */       return SecurityAccRngRelPK.getKeyFromTokens(extKey);
/* 209 */     if (extKey.startsWith("SecurityGroupCK|"))
/* 210 */       return SecurityGroupCK.getKeyFromTokens(extKey);
/* 211 */     if (extKey.startsWith("SecurityGroupPK|"))
/* 212 */       return SecurityGroupPK.getKeyFromTokens(extKey);
/* 213 */     if (extKey.startsWith("CcDeploymentCK|"))
/* 214 */       return CcDeploymentCK.getKeyFromTokens(extKey);
/* 215 */     if (extKey.startsWith("CcDeploymentPK|"))
/* 216 */       return CcDeploymentPK.getKeyFromTokens(extKey);
/* 217 */     if (extKey.startsWith("CcDeploymentLineCK|"))
/* 218 */       return CcDeploymentLineCK.getKeyFromTokens(extKey);
/* 219 */     if (extKey.startsWith("CcDeploymentLinePK|"))
/* 220 */       return CcDeploymentLinePK.getKeyFromTokens(extKey);
/* 221 */     if (extKey.startsWith("CcDeploymentEntryCK|"))
/* 222 */       return CcDeploymentEntryCK.getKeyFromTokens(extKey);
/* 223 */     if (extKey.startsWith("CcDeploymentEntryPK|"))
/* 224 */       return CcDeploymentEntryPK.getKeyFromTokens(extKey);
/* 225 */     if (extKey.startsWith("CcDeploymentDataTypeCK|"))
/* 226 */       return CcDeploymentDataTypeCK.getKeyFromTokens(extKey);
/* 227 */     if (extKey.startsWith("CcDeploymentDataTypePK|"))
/* 228 */       return CcDeploymentDataTypePK.getKeyFromTokens(extKey);
/* 229 */     if (extKey.startsWith("CcMappingLineCK|"))
/* 230 */       return CcMappingLineCK.getKeyFromTokens(extKey);
/* 231 */     if (extKey.startsWith("CcMappingLinePK|"))
/* 232 */       return CcMappingLinePK.getKeyFromTokens(extKey);
/* 233 */     if (extKey.startsWith("CcMappingEntryCK|"))
/* 234 */       return CcMappingEntryCK.getKeyFromTokens(extKey);
/* 235 */     if (extKey.startsWith("CcMappingEntryPK|"))
/* 236 */       return CcMappingEntryPK.getKeyFromTokens(extKey);
/* 237 */     if (extKey.startsWith("CellCalcCK|"))
/* 238 */       return CellCalcCK.getKeyFromTokens(extKey);
/* 239 */     if (extKey.startsWith("CellCalcPK|"))
/* 240 */       return CellCalcPK.getKeyFromTokens(extKey);
/* 241 */     if (extKey.startsWith("CellCalcAssocCK|"))
/* 242 */       return CellCalcAssocCK.getKeyFromTokens(extKey);
/* 243 */     if (extKey.startsWith("CellCalcAssocPK|"))
/* 244 */       return CellCalcAssocPK.getKeyFromTokens(extKey);
/* 245 */     if (extKey.startsWith("ChangeMgmtCK|"))
/* 246 */       return ChangeMgmtCK.getKeyFromTokens(extKey);
/* 247 */     if (extKey.startsWith("ChangeMgmtPK|"))
/* 248 */       return ChangeMgmtPK.getKeyFromTokens(extKey);
/* 249 */     if (extKey.startsWith("ImpExpHdrCK|"))
/* 250 */       return ImpExpHdrCK.getKeyFromTokens(extKey);
/* 251 */     if (extKey.startsWith("ImpExpHdrPK|"))
/* 252 */       return ImpExpHdrPK.getKeyFromTokens(extKey);
/* 253 */     if (extKey.startsWith("ImpExpRowCK|"))
/* 254 */       return ImpExpRowCK.getKeyFromTokens(extKey);
/* 255 */     if (extKey.startsWith("ImpExpRowPK|"))
/* 256 */       return ImpExpRowPK.getKeyFromTokens(extKey);
/* 257 */     if (extKey.startsWith("ReportCK|"))
/* 258 */       return ReportCK.getKeyFromTokens(extKey);
/* 259 */     if (extKey.startsWith("ReportPK|"))
/* 260 */       return ReportPK.getKeyFromTokens(extKey);
/* 261 */     if (extKey.startsWith("CubePendingTranCK|"))
/* 262 */       return CubePendingTranCK.getKeyFromTokens(extKey);
/* 263 */     if (extKey.startsWith("CubePendingTranPK|"))
/* 264 */       return CubePendingTranPK.getKeyFromTokens(extKey);
/* 265 */     if (extKey.startsWith("VirementCategoryCK|"))
/* 266 */       return VirementCategoryCK.getKeyFromTokens(extKey);
/* 267 */     if (extKey.startsWith("VirementCategoryPK|"))
/* 268 */       return VirementCategoryPK.getKeyFromTokens(extKey);
/* 269 */     if (extKey.startsWith("VirementLocationCK|"))
/* 270 */       return VirementLocationCK.getKeyFromTokens(extKey);
/* 271 */     if (extKey.startsWith("VirementLocationPK|"))
/* 272 */       return VirementLocationPK.getKeyFromTokens(extKey);
/* 273 */     if (extKey.startsWith("VirementAccountCK|"))
/* 274 */       return VirementAccountCK.getKeyFromTokens(extKey);
/* 275 */     if (extKey.startsWith("VirementAccountPK|"))
/* 276 */       return VirementAccountPK.getKeyFromTokens(extKey);
/* 277 */     if (extKey.startsWith("BudgetLimitCK|"))
/* 278 */       return BudgetLimitCK.getKeyFromTokens(extKey);
/* 279 */     if (extKey.startsWith("BudgetLimitPK|"))
/* 280 */       return BudgetLimitPK.getKeyFromTokens(extKey);
/* 281 */     if (extKey.startsWith("PerfTestCK|"))
/* 282 */       return PerfTestCK.getKeyFromTokens(extKey);
/* 283 */     if (extKey.startsWith("PerfTestPK|"))
/* 284 */       return PerfTestPK.getKeyFromTokens(extKey);
/* 285 */     if (extKey.startsWith("PerfTestRunCK|"))
/* 286 */       return PerfTestRunCK.getKeyFromTokens(extKey);
/* 287 */     if (extKey.startsWith("PerfTestRunPK|"))
/* 288 */       return PerfTestRunPK.getKeyFromTokens(extKey);
/* 289 */     if (extKey.startsWith("PerfTestRunResultCK|"))
/* 290 */       return PerfTestRunResultCK.getKeyFromTokens(extKey);
/* 291 */     if (extKey.startsWith("PerfTestRunResultPK|"))
/* 292 */       return PerfTestRunResultPK.getKeyFromTokens(extKey);
/* 293 */     if (extKey.startsWith("MessageCK|"))
/* 294 */       return MessageCK.getKeyFromTokens(extKey);
/* 295 */     if (extKey.startsWith("MessagePK|"))
/* 296 */       return MessagePK.getKeyFromTokens(extKey);
/* 297 */     if (extKey.startsWith("MessageAttatchCK|"))
/* 298 */       return MessageAttatchCK.getKeyFromTokens(extKey);
/* 299 */     if (extKey.startsWith("MessageAttatchPK|"))
/* 300 */       return MessageAttatchPK.getKeyFromTokens(extKey);
/* 301 */     if (extKey.startsWith("MessageUserCK|"))
/* 302 */       return MessageUserCK.getKeyFromTokens(extKey);
/* 303 */     if (extKey.startsWith("MessageUserPK|"))
/* 304 */       return MessageUserPK.getKeyFromTokens(extKey);
/* 305 */     if (extKey.startsWith("RechargeCK|"))
/* 306 */       return RechargeCK.getKeyFromTokens(extKey);
/* 307 */     if (extKey.startsWith("RechargePK|"))
/* 308 */       return RechargePK.getKeyFromTokens(extKey);
/* 309 */     if (extKey.startsWith("RechargeCellsCK|"))
/* 310 */       return RechargeCellsCK.getKeyFromTokens(extKey);
/* 311 */     if (extKey.startsWith("RechargeCellsPK|"))
/* 312 */       return RechargeCellsPK.getKeyFromTokens(extKey);
/* 313 */     if (extKey.startsWith("RechargeGroupCK|"))
/* 314 */       return RechargeGroupCK.getKeyFromTokens(extKey);
/* 315 */     if (extKey.startsWith("RechargeGroupPK|"))
/* 316 */       return RechargeGroupPK.getKeyFromTokens(extKey);
/* 317 */     if (extKey.startsWith("RechargeGroupRelCK|"))
/* 318 */       return RechargeGroupRelCK.getKeyFromTokens(extKey);
/* 319 */     if (extKey.startsWith("RechargeGroupRelPK|"))
/* 320 */       return RechargeGroupRelPK.getKeyFromTokens(extKey);
/* 321 */     if (extKey.startsWith("BudgetActivityCK|"))
/* 322 */       return BudgetActivityCK.getKeyFromTokens(extKey);
/* 323 */     if (extKey.startsWith("BudgetActivityPK|"))
/* 324 */       return BudgetActivityPK.getKeyFromTokens(extKey);
/* 325 */     if (extKey.startsWith("BudgetActivityLinkCK|"))
/* 326 */       return BudgetActivityLinkCK.getKeyFromTokens(extKey);
/* 327 */     if (extKey.startsWith("BudgetActivityLinkPK|"))
/* 328 */       return BudgetActivityLinkPK.getKeyFromTokens(extKey);
/* 329 */     if (extKey.startsWith("VirementRequestCK|"))
/* 330 */       return VirementRequestCK.getKeyFromTokens(extKey);
/* 331 */     if (extKey.startsWith("VirementRequestPK|"))
/* 332 */       return VirementRequestPK.getKeyFromTokens(extKey);
/* 333 */     if (extKey.startsWith("VirementRequestGroupCK|"))
/* 334 */       return VirementRequestGroupCK.getKeyFromTokens(extKey);
/* 335 */     if (extKey.startsWith("VirementRequestGroupPK|"))
/* 336 */       return VirementRequestGroupPK.getKeyFromTokens(extKey);
/* 337 */     if (extKey.startsWith("VirementRequestLineCK|"))
/* 338 */       return VirementRequestLineCK.getKeyFromTokens(extKey);
/* 339 */     if (extKey.startsWith("VirementRequestLinePK|"))
/* 340 */       return VirementRequestLinePK.getKeyFromTokens(extKey);
/* 341 */     if (extKey.startsWith("VirementLineSpreadCK|"))
/* 342 */       return VirementLineSpreadCK.getKeyFromTokens(extKey);
/* 343 */     if (extKey.startsWith("VirementLineSpreadPK|"))
/* 344 */       return VirementLineSpreadPK.getKeyFromTokens(extKey);
/* 345 */     if (extKey.startsWith("VirementAuthPointCK|"))
/* 346 */       return VirementAuthPointCK.getKeyFromTokens(extKey);
/* 347 */     if (extKey.startsWith("VirementAuthPointPK|"))
/* 348 */       return VirementAuthPointPK.getKeyFromTokens(extKey);
/* 349 */     if (extKey.startsWith("VirementAuthPointLinkCK|"))
/* 350 */       return VirementAuthPointLinkCK.getKeyFromTokens(extKey);
/* 351 */     if (extKey.startsWith("VirementAuthPointLinkPK|"))
/* 352 */       return VirementAuthPointLinkPK.getKeyFromTokens(extKey);
/* 353 */     if (extKey.startsWith("VirementAuthorisersCK|"))
/* 354 */       return VirementAuthorisersCK.getKeyFromTokens(extKey);
/* 355 */     if (extKey.startsWith("VirementAuthorisersPK|"))
/* 356 */       return VirementAuthorisersPK.getKeyFromTokens(extKey);
/* 357 */     if (extKey.startsWith("ReportTypeCK|"))
/* 358 */       return ReportTypeCK.getKeyFromTokens(extKey);
/* 359 */     if (extKey.startsWith("ReportTypePK|"))
/* 360 */       return ReportTypePK.getKeyFromTokens(extKey);
/* 361 */     if (extKey.startsWith("ReportTypeParamCK|"))
/* 362 */       return ReportTypeParamCK.getKeyFromTokens(extKey);
/* 363 */     if (extKey.startsWith("ReportTypeParamPK|"))
/* 364 */       return ReportTypeParamPK.getKeyFromTokens(extKey);
/* 365 */     if (extKey.startsWith("ReportDefinitionCK|"))
/* 366 */       return ReportDefinitionCK.getKeyFromTokens(extKey);
/* 367 */     if (extKey.startsWith("ReportDefinitionPK|"))
/* 368 */       return ReportDefinitionPK.getKeyFromTokens(extKey);
/* 369 */     if (extKey.startsWith("ReportDefFormCK|"))
/* 370 */       return ReportDefFormCK.getKeyFromTokens(extKey);
/* 371 */     if (extKey.startsWith("ReportDefFormPK|"))
/* 372 */       return ReportDefFormPK.getKeyFromTokens(extKey);
/* 373 */     if (extKey.startsWith("ReportDefMappedExcelCK|"))
/* 374 */       return ReportDefMappedExcelCK.getKeyFromTokens(extKey);
/* 375 */     if (extKey.startsWith("ReportDefMappedExcelPK|"))
/* 376 */       return ReportDefMappedExcelPK.getKeyFromTokens(extKey);
/* 377 */     if (extKey.startsWith("ReportDefCalculatorCK|"))
/* 378 */       return ReportDefCalculatorCK.getKeyFromTokens(extKey);
/* 379 */     if (extKey.startsWith("ReportDefCalculatorPK|"))
/* 380 */       return ReportDefCalculatorPK.getKeyFromTokens(extKey);
/* 381 */     if (extKey.startsWith("ReportDefSummaryCalcCK|"))
/* 382 */       return ReportDefSummaryCalcCK.getKeyFromTokens(extKey);
/* 383 */     if (extKey.startsWith("ReportDefSummaryCalcPK|"))
/* 384 */       return ReportDefSummaryCalcPK.getKeyFromTokens(extKey);
/* 385 */     if (extKey.startsWith("ReportTemplateCK|"))
/* 386 */       return ReportTemplateCK.getKeyFromTokens(extKey);
/* 387 */     if (extKey.startsWith("ReportTemplatePK|"))
/* 388 */       return ReportTemplatePK.getKeyFromTokens(extKey);
/* 389 */     if (extKey.startsWith("ReportMappingTemplateCK|"))
/* 390 */       return ReportMappingTemplateCK.getKeyFromTokens(extKey);
/* 391 */     if (extKey.startsWith("ReportMappingTemplatePK|"))
/* 392 */       return ReportMappingTemplatePK.getKeyFromTokens(extKey);
/* 393 */     if (extKey.startsWith("ExternalDestinationCK|"))
/* 394 */       return ExternalDestinationCK.getKeyFromTokens(extKey);
/* 395 */     if (extKey.startsWith("ExternalDestinationPK|"))
/* 396 */       return ExternalDestinationPK.getKeyFromTokens(extKey);
/* 397 */     if (extKey.startsWith("ExternalDestinationUsersCK|"))
/* 398 */       return ExternalDestinationUsersCK.getKeyFromTokens(extKey);
/* 399 */     if (extKey.startsWith("ExternalDestinationUsersPK|"))
/* 400 */       return ExternalDestinationUsersPK.getKeyFromTokens(extKey);
/* 401 */     if (extKey.startsWith("InternalDestinationCK|"))
/* 402 */       return InternalDestinationCK.getKeyFromTokens(extKey);
/* 403 */     if (extKey.startsWith("InternalDestinationPK|"))
/* 404 */       return InternalDestinationPK.getKeyFromTokens(extKey);
/* 405 */     if (extKey.startsWith("InternalDestinationUsersCK|"))
/* 406 */       return InternalDestinationUsersCK.getKeyFromTokens(extKey);
/* 407 */     if (extKey.startsWith("InternalDestinationUsersPK|"))
/* 408 */       return InternalDestinationUsersPK.getKeyFromTokens(extKey);
/* 409 */     if (extKey.startsWith("DistributionCK|"))
/* 410 */       return DistributionCK.getKeyFromTokens(extKey);
/* 411 */     if (extKey.startsWith("DistributionPK|"))
/* 412 */       return DistributionPK.getKeyFromTokens(extKey);
/* 413 */     if (extKey.startsWith("DistributionLinkCK|"))
/* 414 */       return DistributionLinkCK.getKeyFromTokens(extKey);
/* 415 */     if (extKey.startsWith("DistributionLinkPK|"))
/* 416 */       return DistributionLinkPK.getKeyFromTokens(extKey);
/* 417 */     if (extKey.startsWith("ReportPackCK|"))
/* 418 */       return ReportPackCK.getKeyFromTokens(extKey);
/* 419 */     if (extKey.startsWith("ReportPackPK|"))
/* 420 */       return ReportPackPK.getKeyFromTokens(extKey);
/* 421 */     if (extKey.startsWith("ReportPackLinkCK|"))
/* 422 */       return ReportPackLinkCK.getKeyFromTokens(extKey);
/* 423 */     if (extKey.startsWith("ReportPackLinkPK|"))
/* 424 */       return ReportPackLinkPK.getKeyFromTokens(extKey);
/* 425 */     if (extKey.startsWith("ReportGroupingCK|"))
/* 426 */       return ReportGroupingCK.getKeyFromTokens(extKey);
/* 427 */     if (extKey.startsWith("ReportGroupingPK|"))
/* 428 */       return ReportGroupingPK.getKeyFromTokens(extKey);
/* 429 */     if (extKey.startsWith("ReportGroupingFileCK|"))
/* 430 */       return ReportGroupingFileCK.getKeyFromTokens(extKey);
/* 431 */     if (extKey.startsWith("ReportGroupingFilePK|"))
/* 432 */       return ReportGroupingFilePK.getKeyFromTokens(extKey);
/* 433 */     if (extKey.startsWith("WeightingProfileCK|"))
/* 434 */       return WeightingProfileCK.getKeyFromTokens(extKey);
/* 435 */     if (extKey.startsWith("WeightingProfilePK|"))
/* 436 */       return WeightingProfilePK.getKeyFromTokens(extKey);
/* 437 */     if (extKey.startsWith("WeightingProfileLineCK|"))
/* 438 */       return WeightingProfileLineCK.getKeyFromTokens(extKey);
/* 439 */     if (extKey.startsWith("WeightingProfileLinePK|"))
/* 440 */       return WeightingProfileLinePK.getKeyFromTokens(extKey);
/* 441 */     if (extKey.startsWith("WeightingDeploymentCK|"))
/* 442 */       return WeightingDeploymentCK.getKeyFromTokens(extKey);
/* 443 */     if (extKey.startsWith("WeightingDeploymentPK|"))
/* 444 */       return WeightingDeploymentPK.getKeyFromTokens(extKey);
/* 445 */     if (extKey.startsWith("WeightingDeploymentLineCK|"))
/* 446 */       return WeightingDeploymentLineCK.getKeyFromTokens(extKey);
/* 447 */     if (extKey.startsWith("WeightingDeploymentLinePK|"))
/* 448 */       return WeightingDeploymentLinePK.getKeyFromTokens(extKey);
/* 449 */     if (extKey.startsWith("TidyTaskCK|"))
/* 450 */       return TidyTaskCK.getKeyFromTokens(extKey);
/* 451 */     if (extKey.startsWith("TidyTaskPK|"))
/* 452 */       return TidyTaskPK.getKeyFromTokens(extKey);
/* 453 */     if (extKey.startsWith("TidyTaskLinkCK|"))
/* 454 */       return TidyTaskLinkCK.getKeyFromTokens(extKey);
/* 455 */     if (extKey.startsWith("TidyTaskLinkPK|"))
/* 456 */       return TidyTaskLinkPK.getKeyFromTokens(extKey);
/* 457 */     if (extKey.startsWith("MappedModelCK|"))
/* 458 */       return MappedModelCK.getKeyFromTokens(extKey);
/* 459 */     if (extKey.startsWith("MappedModelPK|"))
/* 460 */       return MappedModelPK.getKeyFromTokens(extKey);
/* 461 */     if (extKey.startsWith("MappedFinanceCubeCK|"))
/* 462 */       return MappedFinanceCubeCK.getKeyFromTokens(extKey);
/* 463 */     if (extKey.startsWith("MappedFinanceCubePK|"))
/* 464 */       return MappedFinanceCubePK.getKeyFromTokens(extKey);
/* 465 */     if (extKey.startsWith("MappedDataTypeCK|"))
/* 466 */       return MappedDataTypeCK.getKeyFromTokens(extKey);
/* 467 */     if (extKey.startsWith("MappedDataTypePK|"))
/* 468 */       return MappedDataTypePK.getKeyFromTokens(extKey);
/* 469 */     if (extKey.startsWith("MappedCalendarYearCK|"))
/* 470 */       return MappedCalendarYearCK.getKeyFromTokens(extKey);
/* 471 */     if (extKey.startsWith("MappedCalendarYearPK|"))
/* 472 */       return MappedCalendarYearPK.getKeyFromTokens(extKey);
/* 473 */     if (extKey.startsWith("MappedCalendarElementCK|"))
/* 474 */       return MappedCalendarElementCK.getKeyFromTokens(extKey);
/* 475 */     if (extKey.startsWith("MappedCalendarElementPK|"))
/* 476 */       return MappedCalendarElementPK.getKeyFromTokens(extKey);
/* 477 */     if (extKey.startsWith("MappedDimensionCK|"))
/* 478 */       return MappedDimensionCK.getKeyFromTokens(extKey);
/* 479 */     if (extKey.startsWith("MappedDimensionPK|"))
/* 480 */       return MappedDimensionPK.getKeyFromTokens(extKey);
/* 481 */     if (extKey.startsWith("MappedDimensionElementCK|"))
/* 482 */       return MappedDimensionElementCK.getKeyFromTokens(extKey);
/* 483 */     if (extKey.startsWith("MappedDimensionElementPK|"))
/* 484 */       return MappedDimensionElementPK.getKeyFromTokens(extKey);
/* 485 */     if (extKey.startsWith("MappedHierarchyCK|"))
/* 486 */       return MappedHierarchyCK.getKeyFromTokens(extKey);
/* 487 */     if (extKey.startsWith("MappedHierarchyPK|"))
/* 488 */       return MappedHierarchyPK.getKeyFromTokens(extKey);
/* 489 */     if (extKey.startsWith("ExtendedAttachmentCK|"))
/* 490 */       return ExtendedAttachmentCK.getKeyFromTokens(extKey);
/* 491 */     if (extKey.startsWith("ExtendedAttachmentPK|"))
/* 492 */       return ExtendedAttachmentPK.getKeyFromTokens(extKey);
/* 493 */     if (extKey.startsWith("ExternalSystemCK|"))
/* 494 */       return ExternalSystemCK.getKeyFromTokens(extKey);
/* 495 */     if (extKey.startsWith("ExternalSystemPK|"))
/* 496 */       return ExternalSystemPK.getKeyFromTokens(extKey);
/* 497 */     if (extKey.startsWith("ExtSysPropertyCK|"))
/* 498 */       return ExtSysPropertyCK.getKeyFromTokens(extKey);
/* 499 */     if (extKey.startsWith("ExtSysPropertyPK|"))
/* 500 */       return ExtSysPropertyPK.getKeyFromTokens(extKey);
/* 501 */     if (extKey.startsWith("ExtSysCompanyCK|"))
/* 502 */       return ExtSysCompanyCK.getKeyFromTokens(extKey);
/* 503 */     if (extKey.startsWith("ExtSysCompanyPK|"))
/* 504 */       return ExtSysCompanyPK.getKeyFromTokens(extKey);
/* 505 */     if (extKey.startsWith("ExtSysCalendarYearCK|"))
/* 506 */       return ExtSysCalendarYearCK.getKeyFromTokens(extKey);
/* 507 */     if (extKey.startsWith("ExtSysCalendarYearPK|"))
/* 508 */       return ExtSysCalendarYearPK.getKeyFromTokens(extKey);
/* 509 */     if (extKey.startsWith("ExtSysCalElementCK|"))
/* 510 */       return ExtSysCalElementCK.getKeyFromTokens(extKey);
/* 511 */     if (extKey.startsWith("ExtSysCalElementPK|"))
/* 512 */       return ExtSysCalElementPK.getKeyFromTokens(extKey);
/* 513 */     if (extKey.startsWith("ExtSysLedgerCK|"))
/* 514 */       return ExtSysLedgerCK.getKeyFromTokens(extKey);
/* 515 */     if (extKey.startsWith("ExtSysLedgerPK|"))
/* 516 */       return ExtSysLedgerPK.getKeyFromTokens(extKey);
/* 517 */     if (extKey.startsWith("ExtSysValueTypeCK|"))
/* 518 */       return ExtSysValueTypeCK.getKeyFromTokens(extKey);
/* 519 */     if (extKey.startsWith("ExtSysValueTypePK|"))
/* 520 */       return ExtSysValueTypePK.getKeyFromTokens(extKey);
/* 521 */     if (extKey.startsWith("ExtSysCurrencyCK|"))
/* 522 */       return ExtSysCurrencyCK.getKeyFromTokens(extKey);
/* 523 */     if (extKey.startsWith("ExtSysCurrencyPK|"))
/* 524 */       return ExtSysCurrencyPK.getKeyFromTokens(extKey);
/* 525 */     if (extKey.startsWith("ExtSysDimensionCK|"))
/* 526 */       return ExtSysDimensionCK.getKeyFromTokens(extKey);
/* 527 */     if (extKey.startsWith("ExtSysDimensionPK|"))
/* 528 */       return ExtSysDimensionPK.getKeyFromTokens(extKey);
/* 529 */     if (extKey.startsWith("ExtSysDimElementCK|"))
/* 530 */       return ExtSysDimElementCK.getKeyFromTokens(extKey);
/* 531 */     if (extKey.startsWith("ExtSysDimElementPK|"))
/* 532 */       return ExtSysDimElementPK.getKeyFromTokens(extKey);
/* 533 */     if (extKey.startsWith("ExtSysHierarchyCK|"))
/* 534 */       return ExtSysHierarchyCK.getKeyFromTokens(extKey);
/* 535 */     if (extKey.startsWith("ExtSysHierarchyPK|"))
/* 536 */       return ExtSysHierarchyPK.getKeyFromTokens(extKey);
/* 537 */     if (extKey.startsWith("ExtSysHierElementCK|"))
/* 538 */       return ExtSysHierElementCK.getKeyFromTokens(extKey);
/* 539 */     if (extKey.startsWith("ExtSysHierElementPK|"))
/* 540 */       return ExtSysHierElementPK.getKeyFromTokens(extKey);
/* 541 */     if (extKey.startsWith("ExtSysHierElemFeedCK|"))
/* 542 */       return ExtSysHierElemFeedCK.getKeyFromTokens(extKey);
/* 543 */     if (extKey.startsWith("ExtSysHierElemFeedPK|"))
/* 544 */       return ExtSysHierElemFeedPK.getKeyFromTokens(extKey);
/* 545 */     if (extKey.startsWith("AmmModelCK|"))
/* 546 */       return AmmModelCK.getKeyFromTokens(extKey);
/* 547 */     if (extKey.startsWith("AmmModelPK|"))
/* 548 */       return AmmModelPK.getKeyFromTokens(extKey);
/* 549 */     if (extKey.startsWith("AmmDimensionCK|"))
/* 550 */       return AmmDimensionCK.getKeyFromTokens(extKey);
/* 551 */     if (extKey.startsWith("AmmDimensionPK|"))
/* 552 */       return AmmDimensionPK.getKeyFromTokens(extKey);
/* 553 */     if (extKey.startsWith("AmmDimensionElementCK|"))
/* 554 */       return AmmDimensionElementCK.getKeyFromTokens(extKey);
/* 555 */     if (extKey.startsWith("AmmDimensionElementPK|"))
/* 556 */       return AmmDimensionElementPK.getKeyFromTokens(extKey);
/* 557 */     if (extKey.startsWith("AmmSrcStructureElementCK|"))
/* 558 */       return AmmSrcStructureElementCK.getKeyFromTokens(extKey);
/* 559 */     if (extKey.startsWith("AmmSrcStructureElementPK|"))
/* 560 */       return AmmSrcStructureElementPK.getKeyFromTokens(extKey);
/* 561 */     if (extKey.startsWith("AmmFinanceCubeCK|"))
/* 562 */       return AmmFinanceCubeCK.getKeyFromTokens(extKey);
/* 563 */     if (extKey.startsWith("AmmFinanceCubePK|"))
/* 564 */       return AmmFinanceCubePK.getKeyFromTokens(extKey);
/* 565 */     if (extKey.startsWith("AmmDataTypeCK|"))
/* 566 */       return AmmDataTypeCK.getKeyFromTokens(extKey);
/* 567 */     if (extKey.startsWith("AmmDataTypePK|"))
/* 568 */       return AmmDataTypePK.getKeyFromTokens(extKey);
/* 569 */     if (extKey.startsWith("TaskGroupCK|"))
/* 570 */       return TaskGroupCK.getKeyFromTokens(extKey);
/* 571 */     if (extKey.startsWith("TaskGroupPK|"))
/* 572 */       return TaskGroupPK.getKeyFromTokens(extKey);
/* 573 */     if (extKey.startsWith("TgRowCK|"))
/* 574 */       return TgRowCK.getKeyFromTokens(extKey);
/* 575 */     if (extKey.startsWith("TgRowPK|"))
/* 576 */       return TgRowPK.getKeyFromTokens(extKey);
/* 577 */     if (extKey.startsWith("TgRowParamCK|"))
/* 578 */       return TgRowParamCK.getKeyFromTokens(extKey);
/* 579 */     if (extKey.startsWith("TgRowParamPK|"))
/* 580 */       return TgRowParamPK.getKeyFromTokens(extKey);
/* 581 */     if (extKey.startsWith("AuthenticationPolicyCK|"))
/* 582 */       return AuthenticationPolicyCK.getKeyFromTokens(extKey);
/* 583 */     if (extKey.startsWith("AuthenticationPolicyPK|"))
/* 584 */       return AuthenticationPolicyPK.getKeyFromTokens(extKey);
/* 585 */     if (extKey.startsWith("LogonHistoryCK|"))
/* 586 */       return LogonHistoryCK.getKeyFromTokens(extKey);
/* 587 */     if (extKey.startsWith("LogonHistoryPK|"))
/* 588 */       return LogonHistoryPK.getKeyFromTokens(extKey);
/* 589 */     if (extKey.startsWith("PasswordHistoryCK|"))
/* 590 */       return PasswordHistoryCK.getKeyFromTokens(extKey);
/* 591 */     if (extKey.startsWith("PasswordHistoryPK|"))
/* 592 */       return PasswordHistoryPK.getKeyFromTokens(extKey);
/* 593 */     if (extKey.startsWith("FormRebuildCK|"))
/* 594 */       return FormRebuildCK.getKeyFromTokens(extKey);
/* 595 */     if (extKey.startsWith("FormRebuildPK|"))
/* 596 */       return FormRebuildPK.getKeyFromTokens(extKey);
/* 597 */     if (extKey.startsWith("CubeFormulaPackageCK|"))
/* 598 */       return CubeFormulaPackageCK.getKeyFromTokens(extKey);
/* 599 */     if (extKey.startsWith("CubeFormulaPackagePK|"))
/* 600 */       return CubeFormulaPackagePK.getKeyFromTokens(extKey);
/* 601 */     if (extKey.startsWith("CubeFormulaCK|"))
/* 602 */       return CubeFormulaCK.getKeyFromTokens(extKey);
/* 603 */     if (extKey.startsWith("CubeFormulaPK|"))
/* 604 */       return CubeFormulaPK.getKeyFromTokens(extKey);
/* 605 */     if (extKey.startsWith("FormulaDeploymentLineCK|"))
/* 606 */       return FormulaDeploymentLineCK.getKeyFromTokens(extKey);
/* 607 */     if (extKey.startsWith("FormulaDeploymentLinePK|"))
/* 608 */       return FormulaDeploymentLinePK.getKeyFromTokens(extKey);
/* 609 */     if (extKey.startsWith("FormulaDeploymentEntryCK|"))
/* 610 */       return FormulaDeploymentEntryCK.getKeyFromTokens(extKey);
/* 611 */     if (extKey.startsWith("FormulaDeploymentEntryPK|"))
/* 612 */       return FormulaDeploymentEntryPK.getKeyFromTokens(extKey);
/* 613 */     if (extKey.startsWith("FormulaDeploymentDtCK|"))
/* 614 */       return FormulaDeploymentDtCK.getKeyFromTokens(extKey);
/* 615 */     if (extKey.startsWith("FormulaDeploymentDtPK|"))
/* 616 */       return FormulaDeploymentDtPK.getKeyFromTokens(extKey);
/* 617 */     if (extKey.startsWith("ImportGridCK|"))
/* 618 */       return ImportGridCK.getKeyFromTokens(extKey);
/* 619 */     if (extKey.startsWith("ImportGridPK|"))
/* 620 */       return ImportGridPK.getKeyFromTokens(extKey);
/* 621 */     if (extKey.startsWith("ImportGridCellCK|"))
/* 622 */       return ImportGridCellCK.getKeyFromTokens(extKey);
/* 623 */     if (extKey.startsWith("ImportGridCellPK|"))
/* 624 */       return ImportGridCellPK.getKeyFromTokens(extKey);
		if (extKey.startsWith("MasterQuestionCK|"))
			return MasterQuestionCK.getKeyFromTokens(extKey);
		if (extKey.startsWith("MasterQuestionPK|"))
			return MasterQuestionPK.getKeyFromTokens(extKey);
		if (extKey.startsWith("ChallengeQuestionCK|"))
			return ChallengeQuestionCK.getKeyFromTokens(extKey);
		if (extKey.startsWith("ChallengeQuestionPK|"))
			return ChallengeQuestionPK.getKeyFromTokens(extKey);
		if (extKey.startsWith("UserResetLinkCK|"))
			return UserResetLinkCK.getKeyFromTokens(extKey);
		if (extKey.startsWith("UserResetLinkPK|"))
			return UserResetLinkPK.getKeyFromTokens(extKey);
		if (extKey.startsWith("BudgetCycleLinkCK|"))
			return BudgetCycleLinkCK.getKeyFromTokens(extKey);
		if (extKey.startsWith("BudgetCycleLinkPK|"))
			return BudgetCycleLinkPK.getKeyFromTokens(extKey);
		throw new IllegalArgumentException("couldn't recognise key name in " + extKey);
	}
}