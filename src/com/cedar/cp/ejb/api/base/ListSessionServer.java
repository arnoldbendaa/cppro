package com.cedar.cp.ejb.api.base;

import com.cedar.cp.api.base.CPConnection;
import com.cedar.cp.api.base.CPException;
import com.cedar.cp.api.base.EntityList;
import com.cedar.cp.api.base.EntityRef;
import com.cedar.cp.api.budgetlocation.UserModelElementAssignment;
import com.cedar.cp.api.dimension.HierarchyRef;
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
import com.cedar.cp.ejb.api.base.AbstractSession;
import com.cedar.cp.ejb.api.base.ListHome;
import com.cedar.cp.ejb.api.base.ListLocal;
import com.cedar.cp.ejb.api.base.ListLocalHome;
import com.cedar.cp.ejb.api.base.ListRemote;
import com.cedar.cp.ejb.impl.base.ListSEJB;

import java.rmi.RemoteException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import javax.ejb.CreateException;
import javax.naming.Context;

public class ListSessionServer extends AbstractSession {
	ListSEJB listEjb = new ListSEJB();
    public ListSessionServer(CPConnection conn_) throws CPException {
        super(conn_);
    }

    public ListSessionServer(Context context_, boolean remote) {
        super(context_, remote);
    }

    public ListSEJB getRemote() throws CPException {
        while (true) {
            
			return listEjb;
//                ListHome e = (ListHome) this.getHome("ejb/ListRemoteHome", ListHome.class);
//                return e.create();
        }
    }

    private ListLocal getLocal() throws CPException {
        try {
            ListLocalHome e = (ListLocalHome) this.getLocalHome("ejb/ListLocalHome");
            return e.create();
        } catch (CreateException var2) {
            throw new CPException("can\'t create local session for ejb/ListLocalHome", var2);
        }
    }

    public AllModelsELO getAllModels() throws CPException {
        try {
            AllModelsELO e = null;
            if (this.isRemoteConnection()) {
                e = this.getRemote().getAllModels();
            } else {
                e = this.getLocal().getAllModels();
            }

            return e;
        } catch (Exception var2) {
            throw new CPException("error in getAllModels", var2);
        }
    }

    public AllModelsELO getAllModelsForLoggedUser(int userId) {
        try {
            AllModelsELO e = null;
            if (this.isRemoteConnection()) {
                e = this.getRemote().getAllModelsForLoggedUser(userId);
            } else {
                e = this.getLocal().getAllModelsForLoggedUser(userId);
            }

            return e;
        } catch (Exception var2) {
            throw new CPException("error in getAllModelsForLoggedUser", var2);
        }
    }

    public AllModelsELO getAllModelsForLoggedUser() {
        try {
            AllModelsELO e = null;
            if (this.isRemoteConnection()) {
                e = this.getRemote().getAllModelsForLoggedUser(getConnection().getUserContext().getUserId());
            } else {
                e = this.getLocal().getAllModelsForLoggedUser(getConnection().getUserContext().getUserId());
            }

            return e;
        } catch (Exception var2) {
            throw new CPException("error in getAllModelsForLoggedUser", var2);
        }
    }
    
    public Map<String, String> getPropertiesForModelVisId(String modelVisId){
        try {
        	Map<String, String> elo = null;
            if (this.isRemoteConnection()) {
                elo = this.getRemote().getPropertiesForModelVisId(modelVisId);
            } else {
                elo = this.getLocal().getPropertiesForModelVisId(modelVisId);
            }

            return elo;
        } catch (Exception e) {
            throw new CPException("error in getPropertiesForModelVisId", e);
        }
    }
    
    public Map<String, String> getPropertiesForModelId(int modelId){
        try {
        	Map<String, String> elo = null;
            if (this.isRemoteConnection()) {
                elo = this.getRemote().getPropertiesForModelId(modelId);
            } else {
                elo = this.getLocal().getPropertiesForModelId(modelId);
            }

            return elo;
        } catch (Exception e) {
            throw new CPException("error in getPropertiesForModelId", e);
        }
    }
    public AllModelsELO getAllModelsForGlobalMappedModel(int modelId) {
        try {
            AllModelsELO elo = null;
            if (this.isRemoteConnection()) {
                elo = this.getRemote().getAllModelsForGlobalMappedModel(modelId);
            } else {
                elo = this.getLocal().getAllModelsForGlobalMappedModel(modelId);
            }

            return elo;
        } catch (Exception e) {
            throw new CPException("error in getAllModelsForGlobalMappedModel", e);
        }
    }

    public AllModelsWebELO getAllModelsWeb() throws CPException {
        try {
            AllModelsWebELO e = null;
            if (this.isRemoteConnection()) {
                e = this.getRemote().getAllModelsWeb();
            } else {
                e = this.getLocal().getAllModelsWeb();
            }

            return e;
        } catch (Exception var2) {
            throw new CPException("error in getAllModelsWeb", var2);
        }
    }

    public AllModelsWebForUserELO getAllModelsWebForUser(int param1) throws CPException {
        try {
            AllModelsWebForUserELO e = null;
            if (this.isRemoteConnection()) {
                e = this.getRemote().getAllModelsWebForUser(param1);
            } else {
                e = this.getLocal().getAllModelsWebForUser(param1);
            }

            return e;
        } catch (Exception var3) {
            throw new CPException("error in getAllModelsWebForUser", var3);
        }
    }

    public AllModelsWithActiveCycleForUserELO getAllModelsWithActiveCycleForUser(int param1) throws CPException {
        try {
            AllModelsWithActiveCycleForUserELO e = null;
            if (this.isRemoteConnection()) {
                e = this.getRemote().getAllModelsWithActiveCycleForUser(param1);
            } else {
                e = this.getLocal().getAllModelsWithActiveCycleForUser(param1);
            }

            return e;
        } catch (Exception var3) {
            throw new CPException("error in getAllModelsWithActiveCycleForUser", var3);
        }
    }

    public AllBudgetHierarchiesELO getAllBudgetHierarchies() throws CPException {
        try {
            AllBudgetHierarchiesELO e = null;
            if (this.isRemoteConnection()) {
                e = this.getRemote().getAllBudgetHierarchies();
            } else {
                e = this.getLocal().getAllBudgetHierarchies();
            }

            return e;
        } catch (Exception var2) {
            throw new CPException("error in getAllBudgetHierarchies", var2);
        }
    }

    public ModelForDimensionELO getModelForDimension(int param1) throws CPException {
        try {
            ModelForDimensionELO e = null;
            if (this.isRemoteConnection()) {
                e = this.getRemote().getModelForDimension(param1);
            } else {
                e = this.getLocal().getModelForDimension(param1);
            }

            return e;
        } catch (Exception var3) {
            throw new CPException("error in getModelForDimension", var3);
        }
    }

    public ModelDimensionsELO getModelDimensions(int param1) throws CPException {
        try {
            ModelDimensionsELO e = null;
            if (this.isRemoteConnection()) {
                e = this.getRemote().getModelDimensions(param1);
            } else {
                e = this.getLocal().getModelDimensions(param1);
            }

            return e;
        } catch (Exception var3) {
            throw new CPException("error in getModelDimensions", var3);
        }
    }

    public ModelDimensionseExcludeCallELO getModelDimensionseExcludeCall(int param1) throws CPException {
        try {
            ModelDimensionseExcludeCallELO e = null;
            if (this.isRemoteConnection()) {
                e = this.getRemote().getModelDimensionseExcludeCall(param1);
            } else {
                e = this.getLocal().getModelDimensionseExcludeCall(param1);
            }

            return e;
        } catch (Exception var3) {
            throw new CPException("error in getModelDimensionseExcludeCall", var3);
        }
    }

    public ModelDetailsWebELO getModelDetailsWeb(int param1) throws CPException {
        try {
            ModelDetailsWebELO e = null;
            if (this.isRemoteConnection()) {
                e = this.getRemote().getModelDetailsWeb(param1);
            } else {
                e = this.getLocal().getModelDetailsWeb(param1);
            }

            return e;
        } catch (Exception var3) {
            throw new CPException("error in getModelDetailsWeb", var3);
        }
    }

    public AllRootsForModelELO getAllRootsForModel(int param1) throws CPException {
        try {
            AllRootsForModelELO e = null;
            if (this.isRemoteConnection()) {
                e = this.getRemote().getAllRootsForModel(param1);
            } else {
                e = this.getLocal().getAllRootsForModel(param1);
            }

            return e;
        } catch (Exception var3) {
            throw new CPException("error in getAllRootsForModel", var3);
        }
    }

    public BudgetHierarchyRootNodeForModelELO getBudgetHierarchyRootNodeForModel(int param1) throws CPException {
        try {
            BudgetHierarchyRootNodeForModelELO e = null;
            if (this.isRemoteConnection()) {
                e = this.getRemote().getBudgetHierarchyRootNodeForModel(param1);
            } else {
                e = this.getLocal().getBudgetHierarchyRootNodeForModel(param1);
            }

            return e;
        } catch (Exception var3) {
            throw new CPException("error in getBudgetHierarchyRootNodeForModel", var3);
        }
    }

    public BudgetCyclesToFixStateELO getBudgetCyclesToFixState(int param1) throws CPException {
        try {
            BudgetCyclesToFixStateELO e = null;
            if (this.isRemoteConnection()) {
                e = this.getRemote().getBudgetCyclesToFixState(param1);
            } else {
                e = this.getLocal().getBudgetCyclesToFixState(param1);
            }

            return e;
        } catch (Exception var3) {
            throw new CPException("error in getBudgetCyclesToFixState", var3);
        }
    }

    public MaxDepthForBudgetHierarchyELO getMaxDepthForBudgetHierarchy(int param1) throws CPException {
        try {
            MaxDepthForBudgetHierarchyELO e = null;
            if (this.isRemoteConnection()) {
                e = this.getRemote().getMaxDepthForBudgetHierarchy(param1);
            } else {
                e = this.getLocal().getMaxDepthForBudgetHierarchy(param1);
            }

            return e;
        } catch (Exception var3) {
            throw new CPException("error in getMaxDepthForBudgetHierarchy", var3);
        }
    }

    public CalendarSpecForModelELO getCalendarSpecForModel(int param1) throws CPException {
        try {
            CalendarSpecForModelELO e = null;
            if (this.isRemoteConnection()) {
                e = this.getRemote().getCalendarSpecForModel(param1);
            } else {
                e = this.getLocal().getCalendarSpecForModel(param1);
            }

            return e;
        } catch (Exception var3) {
            throw new CPException("error in getCalendarSpecForModel", var3);
        }
    }

    public HierarchiesForModelELO getHierarchiesForModel(int param1) throws CPException {
        try {
            HierarchiesForModelELO e = null;
            if (this.isRemoteConnection()) {
                e = this.getRemote().getHierarchiesForModel(param1);
            } else {
                e = this.getLocal().getHierarchiesForModel(param1);
            }

            return e;
        } catch (Exception var3) {
            throw new CPException("error in getHierarchiesForModel", var3);
        }
    }

    public AllUsersForASecurityGroupELO getAllUsersForASecurityGroup(int param1) throws CPException {
        try {
            AllUsersForASecurityGroupELO e = null;
            if (this.isRemoteConnection()) {
                e = this.getRemote().getAllUsersForASecurityGroup(param1);
            } else {
                e = this.getLocal().getAllUsersForASecurityGroup(param1);
            }

            return e;
        } catch (Exception var3) {
            throw new CPException("error in getAllUsersForASecurityGroup", var3);
        }
    }

    public AllModelBusinessDimensionsELO getAllModelBusinessDimensions() throws CPException {
        try {
            AllModelBusinessDimensionsELO e = null;
            if (this.isRemoteConnection()) {
                e = this.getRemote().getAllModelBusinessDimensions();
            } else {
                e = this.getLocal().getAllModelBusinessDimensions();
            }

            return e;
        } catch (Exception var2) {
            throw new CPException("error in getAllModelBusinessDimensions", var2);
        }
    }

    public AllModelBusAndAccDimensionsELO getAllModelBusAndAccDimensions() throws CPException {
        try {
            AllModelBusAndAccDimensionsELO e = null;
            if (this.isRemoteConnection()) {
                e = this.getRemote().getAllModelBusAndAccDimensions();
            } else {
                e = this.getLocal().getAllModelBusAndAccDimensions();
            }

            return e;
        } catch (Exception var2) {
            throw new CPException("error in getAllModelBusAndAccDimensions", var2);
        }
    }

    public BudgetDimensionIdForModelELO getBudgetDimensionIdForModel(int param1, int param2) throws CPException {
        try {
            BudgetDimensionIdForModelELO e = null;
            if (this.isRemoteConnection()) {
                e = this.getRemote().getBudgetDimensionIdForModel(param1, param2);
            } else {
                e = this.getLocal().getBudgetDimensionIdForModel(param1, param2);
            }

            return e;
        } catch (Exception var4) {
            throw new CPException("error in getBudgetDimensionIdForModel", var4);
        }
    }

    public DimensionIdForModelDimTypeELO getDimensionIdForModelDimType(int param1, int param2) throws CPException {
        try {
            DimensionIdForModelDimTypeELO e = null;
            if (this.isRemoteConnection()) {
                e = this.getRemote().getDimensionIdForModelDimType(param1, param2);
            } else {
                e = this.getLocal().getDimensionIdForModelDimType(param1, param2);
            }

            return e;
        } catch (Exception var4) {
            throw new CPException("error in getDimensionIdForModelDimType", var4);
        }
    }

    public AllFinanceCubesELO getAllFinanceCubes() throws CPException {
        try {
            AllFinanceCubesELO e = null;
            if (this.isRemoteConnection()) {
                e = this.getRemote().getAllFinanceCubes();
            } else {
                e = this.getLocal().getAllFinanceCubes();
            }

            return e;
        } catch (Exception var2) {
            throw new CPException("error in getAllFinanceCubes", var2);
        }
    }

    public AllFinanceCubesELO getAllFinanceCubesForLoggedUser() throws CPException {
        try {
            AllFinanceCubesELO e = null;
            if (this.isRemoteConnection()) {
                e = this.getRemote().getAllFinanceCubesForLoggedUser(getConnection().getUserContext().getUserId());
            } else {
                e = this.getLocal().getAllFinanceCubesForLoggedUser(getConnection().getUserContext().getUserId());
            }

            return e;
        } catch (Exception var2) {
            throw new CPException("error in getAllFinanceCubes", var2);
        }
    }

    public AllCubeFormulasELO getAllCubeFormulasForLoggedUser() throws CPException {
        try {
            AllCubeFormulasELO e = null;
            if (this.isRemoteConnection()) {
                e = this.getRemote().getAllCubeFormulasForLoggedUser(getConnection().getUserContext().getUserId());
            } else {
                e = this.getLocal().getAllCubeFormulasForLoggedUser(getConnection().getUserContext().getUserId());
            }

            return e;
        } catch (Exception var2) {
            throw new CPException("error in getAllFinanceCubes", var2);
        }
    }

    public AllSimpleFinanceCubesELO getAllSimpleFinanceCubes() throws CPException {
        try {
            AllSimpleFinanceCubesELO e = null;
            if (this.isRemoteConnection()) {
                e = this.getRemote().getAllSimpleFinanceCubes(getConnection().getUserContext().getUserId());
            } else {
                e = this.getLocal().getAllSimpleFinanceCubes(getConnection().getUserContext().getUserId());
            }

            return e;
        } catch (Exception var2) {
            throw new CPException("error in getAllSimpleFinanceCubes", var2);
        }
    }

    public AllDataTypesAttachedToFinanceCubeForModelELO getAllDataTypesAttachedToFinanceCubeForModel(int param1) throws CPException {
        try {
            AllDataTypesAttachedToFinanceCubeForModelELO e = null;
            if (this.isRemoteConnection()) {
                e = this.getRemote().getAllDataTypesAttachedToFinanceCubeForModel(param1);
            } else {
                e = this.getLocal().getAllDataTypesAttachedToFinanceCubeForModel(param1);
            }

            return e;
        } catch (Exception var3) {
            throw new CPException("error in getAllDataTypesAttachedToFinanceCubeForModel", var3);
        }
    }

    public FinanceCubesForModelELO getFinanceCubesForModel(int param1) throws CPException {
        try {
            FinanceCubesForModelELO e = null;
            if (this.isRemoteConnection()) {
                e = this.getRemote().getFinanceCubesForModel(param1);
            } else {
                e = this.getLocal().getFinanceCubesForModel(param1);
            }

            return e;
        } catch (Exception var3) {
            throw new CPException("error in getFinanceCubesForModel", var3);
        }
    }

    public FinanceCubeDimensionsAndHierachiesELO getFinanceCubeDimensionsAndHierachies(int param1) throws CPException {
        try {
            FinanceCubeDimensionsAndHierachiesELO e = null;
            if (this.isRemoteConnection()) {
                e = this.getRemote().getFinanceCubeDimensionsAndHierachies(param1);
            } else {
                e = this.getLocal().getFinanceCubeDimensionsAndHierachies(param1);
            }

            return e;
        } catch (Exception var3) {
            throw new CPException("error in getFinanceCubeDimensionsAndHierachies", var3);
        }
    }

    public FinanceCubeAllDimensionsAndHierachiesELO getFinanceCubeAllDimensionsAndHierachies(int param1) throws CPException {
        try {
            FinanceCubeAllDimensionsAndHierachiesELO e = null;
            if (this.isRemoteConnection()) {
                e = this.getRemote().getFinanceCubeAllDimensionsAndHierachies(param1);
            } else {
                e = this.getLocal().getFinanceCubeAllDimensionsAndHierachies(param1);
            }

            return e;
        } catch (Exception var3) {
            throw new CPException("error in getFinanceCubeAllDimensionsAndHierachies", var3);
        }
    }

    public AllFinanceCubesWebELO getAllFinanceCubesWeb() throws CPException {
        try {
            AllFinanceCubesWebELO e = null;
            if (this.isRemoteConnection()) {
                e = this.getRemote().getAllFinanceCubesWeb();
            } else {
                e = this.getLocal().getAllFinanceCubesWeb();
            }

            return e;
        } catch (Exception var2) {
            throw new CPException("error in getAllFinanceCubesWeb", var2);
        }
    }

    public AllFinanceCubesWebForModelELO getAllFinanceCubesWebForModel(int param1) throws CPException {
        try {
            AllFinanceCubesWebForModelELO e = null;
            if (this.isRemoteConnection()) {
                e = this.getRemote().getAllFinanceCubesWebForModel(param1);
            } else {
                e = this.getLocal().getAllFinanceCubesWebForModel(param1);
            }

            return e;
        } catch (Exception var3) {
            throw new CPException("error in getAllFinanceCubesWebForModel", var3);
        }
    }

    public AllFinanceCubesWebForUserELO getAllFinanceCubesWebForUser(int param1) throws CPException {
        try {
            AllFinanceCubesWebForUserELO e = null;
            if (this.isRemoteConnection()) {
                e = this.getRemote().getAllFinanceCubesWebForUser(param1);
            } else {
                e = this.getLocal().getAllFinanceCubesWebForUser(param1);
            }

            return e;
        } catch (Exception var3) {
            throw new CPException("error in getAllFinanceCubesWebForUser", var3);
        }
    }

    public FinanceCubeDetailsELO getFinanceCubeDetails(int param1) throws CPException {
        try {
            FinanceCubeDetailsELO e = null;
            if (this.isRemoteConnection()) {
                e = this.getRemote().getFinanceCubeDetails(param1);
            } else {
                e = this.getLocal().getFinanceCubeDetails(param1);
            }

            return e;
        } catch (Exception var3) {
            throw new CPException("error in getFinanceCubeDetails", var3);
        }
    }

    public FinanceCubesUsingDataTypeELO getFinanceCubesUsingDataType(short param1) throws CPException {
        try {
            FinanceCubesUsingDataTypeELO e = null;
            if (this.isRemoteConnection()) {
                e = this.getRemote().getFinanceCubesUsingDataType(param1);
            } else {
                e = this.getLocal().getFinanceCubesUsingDataType(param1);
            }

            return e;
        } catch (Exception var3) {
            throw new CPException("error in getFinanceCubesUsingDataType", var3);
        }
    }

    public AllFinanceCubeDataTypesELO getAllFinanceCubeDataTypes() throws CPException {
        try {
            AllFinanceCubeDataTypesELO e = null;
            if (this.isRemoteConnection()) {
                e = this.getRemote().getAllFinanceCubeDataTypes();
            } else {
                e = this.getLocal().getAllFinanceCubeDataTypes();
            }

            return e;
        } catch (Exception var2) {
            throw new CPException("error in getAllFinanceCubeDataTypes", var2);
        }
    }

    public ImportableFinanceCubeDataTypesELO getImportableFinanceCubeDataTypes() throws CPException {
        try {
            ImportableFinanceCubeDataTypesELO e = null;
            if (this.isRemoteConnection()) {
                e = this.getRemote().getImportableFinanceCubeDataTypes();
            } else {
                e = this.getLocal().getImportableFinanceCubeDataTypes();
            }

            return e;
        } catch (Exception var2) {
            throw new CPException("error in getImportableFinanceCubeDataTypes", var2);
        }
    }

    public AllAttachedDataTypesForFinanceCubeELO getAllAttachedDataTypesForFinanceCube(int param1) throws CPException {
        try {
            AllAttachedDataTypesForFinanceCubeELO e = null;
            if (this.isRemoteConnection()) {
                e = this.getRemote().getAllAttachedDataTypesForFinanceCube(param1);
            } else {
                e = this.getLocal().getAllAttachedDataTypesForFinanceCube(param1);
            }

            return e;
        } catch (Exception var3) {
            throw new CPException("error in getAllAttachedDataTypesForFinanceCube", var3);
        }
    }

    public AllDataTypesForFinanceCubeELO getAllDataTypesForFinanceCube(int param1) throws CPException {
        try {
            AllDataTypesForFinanceCubeELO e = null;
            if (this.isRemoteConnection()) {
                e = this.getRemote().getAllDataTypesForFinanceCube(param1);
            } else {
                e = this.getLocal().getAllDataTypesForFinanceCube(param1);
            }

            return e;
        } catch (Exception var3) {
            throw new CPException("error in getAllDataTypesForFinanceCube", var3);
        }
    }

    public AllFinanceCubesForDataTypeELO getAllFinanceCubesForDataType(short param1) throws CPException {
        try {
            AllFinanceCubesForDataTypeELO e = null;
            if (this.isRemoteConnection()) {
                e = this.getRemote().getAllFinanceCubesForDataType(param1);
            } else {
                e = this.getLocal().getAllFinanceCubesForDataType(param1);
            }

            return e;
        } catch (Exception var3) {
            throw new CPException("error in getAllFinanceCubesForDataType", var3);
        }
    }

    public AllBudgetCyclesELO getAllBudgetCycles() throws CPException {
        try {
            AllBudgetCyclesELO e = null;
            if (this.isRemoteConnection()) {
                e = this.getRemote().getAllBudgetCycles();
            } else {
                e = this.getLocal().getAllBudgetCycles();
            }

            return e;
        } catch (Exception var2) {
            throw new CPException("error in getAllBudgetCycles", var2);
        }
    }

    public AllBudgetCyclesWebELO getAllBudgetCyclesWeb() throws CPException {
        try {
            AllBudgetCyclesWebELO e = null;
            if (this.isRemoteConnection()) {
                e = this.getRemote().getAllBudgetCyclesWeb();
            } else {
                e = this.getLocal().getAllBudgetCyclesWeb();
            }

            return e;
        } catch (Exception var2) {
            throw new CPException("error in getAllBudgetCyclesWeb", var2);
        }
    }

    public AllBudgetCyclesELO getAllBudgetCyclesForLoggedUser() throws CPException {
        try {
            AllBudgetCyclesELO e = null;
            if (this.isRemoteConnection()) {
                e = this.getRemote().getAllBudgetCyclesForUser(getConnection().getUserContext().getUserId());
            } else {
                e = this.getLocal().getAllBudgetCyclesForUser(getConnection().getUserContext().getUserId());
            }

            return e;
        } catch (Exception var2) {
            throw new CPException("error in getAllBudgetCyclesForLoggedUser", var2);
        }
    }

    public AllBudgetCyclesWebDetailedELO getAllBudgetCyclesWebDetailed() throws CPException {
        try {
            AllBudgetCyclesWebDetailedELO e = null;
            if (this.isRemoteConnection()) {
                e = this.getRemote().getAllBudgetCyclesWebDetailed();
            } else {
                e = this.getLocal().getAllBudgetCyclesWebDetailed();
            }

            return e;
        } catch (Exception var2) {
            throw new CPException("error in getAllBudgetCyclesWebDetailed", var2);
        }
    }

    public BudgetCyclesForModelELO getBudgetCyclesForModel(int param1) throws CPException {
        try {
            BudgetCyclesForModelELO e = null;
            if (this.isRemoteConnection()) {
                e = this.getRemote().getBudgetCyclesForModel(param1);
            } else {
                e = this.getLocal().getBudgetCyclesForModel(param1);
            }

            return e;
        } catch (Exception var3) {
            throw new CPException("error in getBudgetCyclesForModel", var3);
        }
    }

    public BudgetCyclesForModelWithStateELO getBudgetCyclesForModelWithState(int param1, int param2) throws CPException {
        try {
            BudgetCyclesForModelWithStateELO e = null;
            if (this.isRemoteConnection()) {
                e = this.getRemote().getBudgetCyclesForModelWithState(param1, param2);
            } else {
                e = this.getLocal().getBudgetCyclesForModelWithState(param1, param2);
            }

            return e;
        } catch (Exception var4) {
            throw new CPException("error in getBudgetCyclesForModelWithState", var4);
        }
    }

    public BudgetCycleIntegrityELO getBudgetCycleIntegrity() throws CPException {
        try {
            BudgetCycleIntegrityELO e = null;
            if (this.isRemoteConnection()) {
                e = this.getRemote().getBudgetCycleIntegrity();
            } else {
                e = this.getLocal().getBudgetCycleIntegrity();
            }

            return e;
        } catch (Exception var2) {
            throw new CPException("error in getBudgetCycleIntegrity", var2);
        }
    }

    public BudgetCycleDetailedForIdELO getBudgetCycleDetailedForId(int param1) throws CPException {
        try {
            BudgetCycleDetailedForIdELO e = null;
            if (this.isRemoteConnection()) {
                e = this.getRemote().getBudgetCycleDetailedForId(param1);
            } else {
                e = this.getLocal().getBudgetCycleDetailedForId(param1);
            }

            return e;
        } catch (Exception var3) {
            throw new CPException("error in getBudgetCycleDetailedForId", var3);
        }
    }

    public BudgetCycleDetailedForIdELO getBudgetCycleXmlFormsForId(int param1, int userId) throws CPException {
        try {
            BudgetCycleDetailedForIdELO e = null;
            if (this.isRemoteConnection()) {
                e = this.getRemote().getBudgetCycleXmlFormsForId(param1, userId);
            } else {
                e = this.getLocal().getBudgetCycleXmlFormsForId(param1, userId);
            }

            return e;
        } catch (Exception var3) {
            throw new CPException("error in getBudgetCycleXmlFormsForId", var3);
        }
    }

    public BudgetTransferBudgetCyclesELO getBudgetTransferBudgetCycles() throws CPException {
        try {
            BudgetTransferBudgetCyclesELO e = null;
            if (this.isRemoteConnection()) {
                e = this.getRemote().getBudgetTransferBudgetCycles();
            } else {
                e = this.getLocal().getBudgetTransferBudgetCycles();
            }

            return e;
        } catch (Exception var2) {
            throw new CPException("error in getBudgetTransferBudgetCycles", var2);
        }
    }

    public CheckIfHasStateELO getCheckIfHasState(int param1, int param2) throws CPException {
        try {
            CheckIfHasStateELO e = null;
            if (this.isRemoteConnection()) {
                e = this.getRemote().getCheckIfHasState(param1, param2);
            } else {
                e = this.getLocal().getCheckIfHasState(param1, param2);
            }

            return e;
        } catch (Exception var4) {
            throw new CPException("error in getCheckIfHasState", var4);
        }
    }

    public CycleStateDetailsELO getCycleStateDetails(int param1) throws CPException {
        try {
            CycleStateDetailsELO e = null;
            if (this.isRemoteConnection()) {
                e = this.getRemote().getCycleStateDetails(param1);
            } else {
                e = this.getLocal().getCycleStateDetails(param1);
            }

            return e;
        } catch (Exception var3) {
            throw new CPException("error in getCycleStateDetails", var3);
        }
    }

    public AllResponsibilityAreasELO getAllResponsibilityAreas() throws CPException {
        try {
            AllResponsibilityAreasELO e = null;
            if (this.isRemoteConnection()) {
                e = this.getRemote().getAllResponsibilityAreas();
            } else {
                e = this.getLocal().getAllResponsibilityAreas();
            }

            return e;
        } catch (Exception var2) {
            throw new CPException("error in getAllResponsibilityAreas", var2);
        }
    }

    public AllBudgetUsersELO getAllBudgetUsers() throws CPException {
        try {
            AllBudgetUsersELO e = null;
            if (this.isRemoteConnection()) {
                e = this.getRemote().getAllBudgetUsers();
            } else {
                e = this.getLocal().getAllBudgetUsers();
            }

            return e;
        } catch (Exception var2) {
            throw new CPException("error in getAllBudgetUsers", var2);
        }
    }

    public CheckUserAccessToModelELO getCheckUserAccessToModel(int param1, int param2) throws CPException {
        try {
            CheckUserAccessToModelELO e = null;
            if (this.isRemoteConnection()) {
                e = this.getRemote().getCheckUserAccessToModel(param1, param2);
            } else {
                e = this.getLocal().getCheckUserAccessToModel(param1, param2);
            }

            return e;
        } catch (Exception var4) {
            throw new CPException("error in getCheckUserAccessToModel", var4);
        }
    }

    public CheckUserAccessELO getCheckUserAccess(int param1, int param2) throws CPException {
        try {
            CheckUserAccessELO e = null;
            if (this.isRemoteConnection()) {
                e = this.getRemote().getCheckUserAccess(param1, param2);
            } else {
                e = this.getLocal().getCheckUserAccess(param1, param2);
            }

            return e;
        } catch (Exception var4) {
            throw new CPException("error in getCheckUserAccess", var4);
        }
    }

    public CheckUserELO getCheckUser(int param1) throws CPException {
        try {
            CheckUserELO e = null;
            if (this.isRemoteConnection()) {
                e = this.getRemote().getCheckUser(param1);
            } else {
                e = this.getLocal().getCheckUser(param1);
            }

            return e;
        } catch (Exception var3) {
            throw new CPException("error in getCheckUser", var3);
        }
    }

    public BudgetUsersForNodeELO getBudgetUsersForNode(int param1, int param2) throws CPException {
        try {
            BudgetUsersForNodeELO e = null;
            if (this.isRemoteConnection()) {
                e = this.getRemote().getBudgetUsersForNode(param1, param2);
            } else {
                e = this.getLocal().getBudgetUsersForNode(param1, param2);
            }

            return e;
        } catch (Exception var4) {
            throw new CPException("error in getBudgetUsersForNode", var4);
        }
    }

    public NodesForUserAndCycleELO getNodesForUserAndCycle(int param1, int param2) throws CPException {
        try {
            NodesForUserAndCycleELO e = null;
            if (this.isRemoteConnection()) {
                e = this.getRemote().getNodesForUserAndCycle(param1, param2);
            } else {
                e = this.getLocal().getNodesForUserAndCycle(param1, param2);
            }

            return e;
        } catch (Exception var4) {
            throw new CPException("error in getNodesForUserAndCycle", var4);
        }
    }

    public NodesForUserAndModelELO getNodesForUserAndModel(int param1, int param2) throws CPException {
        try {
            NodesForUserAndModelELO e = null;
            if (this.isRemoteConnection()) {
                e = this.getRemote().getNodesForUserAndModel(param1, param2);
            } else {
                e = this.getLocal().getNodesForUserAndModel(param1, param2);
            }

            return e;
        } catch (Exception var4) {
            throw new CPException("error in getNodesForUserAndModel", var4);
        }
    }

    public UsersForModelAndElementELO getUsersForModelAndElement(int param1, int param2) throws CPException {
        try {
            UsersForModelAndElementELO e = null;
            if (this.isRemoteConnection()) {
                e = this.getRemote().getUsersForModelAndElement(param1, param2);
            } else {
                e = this.getLocal().getUsersForModelAndElement(param1, param2);
            }

            return e;
        } catch (Exception var4) {
            throw new CPException("error in getUsersForModelAndElement", var4);
        }
    }

    public AllDataTypesELO getAllDataTypes() throws CPException {
        try {
            AllDataTypesELO e = null;
            if (this.isRemoteConnection()) {
                e = this.getRemote().getAllDataTypes();
            } else {
                e = this.getLocal().getAllDataTypes();
            }

            return e;
        } catch (Exception var2) {
            throw new CPException("error in getAllDataTypes", var2);
        }
    }

    public AllDataTypesWebELO getAllDataTypesWeb() throws CPException {
        try {
            AllDataTypesWebELO e = null;
            if (this.isRemoteConnection()) {
                e = this.getRemote().getAllDataTypesWeb();
            } else {
                e = this.getLocal().getAllDataTypesWeb();
            }

            return e;
        } catch (Exception var2) {
            throw new CPException("error in getAllDataTypesWeb", var2);
        }
    }

    public AllDataTypeForFinanceCubeELO getAllDataTypeForFinanceCube(int param1) throws CPException {
        try {
            AllDataTypeForFinanceCubeELO e = null;
            if (this.isRemoteConnection()) {
                e = this.getRemote().getAllDataTypeForFinanceCube(param1);
            } else {
                e = this.getLocal().getAllDataTypeForFinanceCube(param1);
            }

            return e;
        } catch (Exception var3) {
            throw new CPException("error in getAllDataTypeForFinanceCube", var3);
        }
    }

    public AllDataTypesForModelELO getAllDataTypesForModel(int param1) throws CPException {
        try {
            AllDataTypesForModelELO e = null;
            if (this.isRemoteConnection()) {
                e = this.getRemote().getAllDataTypesForModel(param1);
            } else {
                e = this.getLocal().getAllDataTypesForModel(param1);
            }

            return e;
        } catch (Exception var3) {
            throw new CPException("error in getAllDataTypesForModel", var3);
        }
    }

    public DataTypesByTypeELO getDataTypesByType(int param1) throws CPException {
        try {
            DataTypesByTypeELO e = null;
            if (this.isRemoteConnection()) {
                e = this.getRemote().getDataTypesByType(param1);
            } else {
                e = this.getLocal().getDataTypesByType(param1);
            }

            return e;
        } catch (Exception var3) {
            throw new CPException("error in getDataTypesByType", var3);
        }
    }

    public DataTypesByTypeWriteableELO getDataTypesByTypeWriteable(int param1) throws CPException {
        try {
            DataTypesByTypeWriteableELO e = null;
            if (this.isRemoteConnection()) {
                e = this.getRemote().getDataTypesByTypeWriteable(param1);
            } else {
                e = this.getLocal().getDataTypesByTypeWriteable(param1);
            }

            return e;
        } catch (Exception var3) {
            throw new CPException("error in getDataTypesByTypeWriteable", var3);
        }
    }

    public DataTypeDependenciesELO getDataTypeDependencies(short param1) throws CPException {
        try {
            DataTypeDependenciesELO e = null;
            if (this.isRemoteConnection()) {
                e = this.getRemote().getDataTypeDependencies(param1);
            } else {
                e = this.getLocal().getDataTypeDependencies(param1);
            }

            return e;
        } catch (Exception var3) {
            throw new CPException("error in getDataTypeDependencies", var3);
        }
    }

    public DataTypesForImpExpELO getDataTypesForImpExp() throws CPException {
        try {
            DataTypesForImpExpELO e = null;
            if (this.isRemoteConnection()) {
                e = this.getRemote().getDataTypesForImpExp();
            } else {
                e = this.getLocal().getDataTypesForImpExp();
            }

            return e;
        } catch (Exception var2) {
            throw new CPException("error in getDataTypesForImpExp", var2);
        }
    }

    public DataTypeDetailsForVisIDELO getDataTypeDetailsForVisID(String param1) throws CPException {
        try {
            DataTypeDetailsForVisIDELO e = null;
            if (this.isRemoteConnection()) {
                e = this.getRemote().getDataTypeDetailsForVisID(param1);
            } else {
                e = this.getLocal().getDataTypeDetailsForVisID(param1);
            }

            return e;
        } catch (Exception var3) {
            throw new CPException("error in getDataTypeDetailsForVisID", var3);
        }
    }

    public AllCurrencysELO getAllCurrencys() throws CPException {
        try {
            AllCurrencysELO e = null;
            if (this.isRemoteConnection()) {
                e = this.getRemote().getAllCurrencys();
            } else {
                e = this.getLocal().getAllCurrencys();
            }

            return e;
        } catch (Exception var2) {
            throw new CPException("error in getAllCurrencys", var2);
        }
    }

    public AllStructureElementsELO getAllStructureElements(int param1) throws CPException {
        try {
            AllStructureElementsELO e = null;
            if (this.isRemoteConnection()) {
                e = this.getRemote().getAllStructureElements(param1);
            } else {
                e = this.getLocal().getAllStructureElements(param1);
            }

            return e;
        } catch (Exception var3) {
            throw new CPException("error in getAllStructureElements", var3);
        }
    }

    public AllLeafStructureElementsELO getAllLeafStructureElements(int param1) throws CPException {
        try {
            AllLeafStructureElementsELO e = null;
            if (this.isRemoteConnection()) {
                e = this.getRemote().getAllLeafStructureElements(param1);
            } else {
                e = this.getLocal().getAllLeafStructureElements(param1);
            }

            return e;
        } catch (Exception var3) {
            throw new CPException("error in getAllLeafStructureElements", var3);
        }
    }

    public LeafPlannableBudgetLocationsForModelELO getLeafPlannableBudgetLocationsForModel(int param1) throws CPException {
        try {
            LeafPlannableBudgetLocationsForModelELO e = null;
            if (this.isRemoteConnection()) {
                e = this.getRemote().getLeafPlannableBudgetLocationsForModel(param1);
            } else {
                e = this.getLocal().getLeafPlannableBudgetLocationsForModel(param1);
            }

            return e;
        } catch (Exception var3) {
            throw new CPException("error in getLeafPlannableBudgetLocationsForModel", var3);
        }
    }

    public StructureElementParentsELO getStructureElementParents(int param1, int param2) throws CPException {
        try {
            StructureElementParentsELO e = null;
            if (this.isRemoteConnection()) {
                e = this.getRemote().getStructureElementParents(param1, param2);
            } else {
                e = this.getLocal().getStructureElementParents(param1, param2);
            }

            return e;
        } catch (Exception var4) {
            throw new CPException("error in getStructureElementParents", var4);
        }
    }

    public StructureElementParentsReversedELO getStructureElementParentsReversed(int param1, int param2) throws CPException {
        try {
            StructureElementParentsReversedELO e = null;
            if (this.isRemoteConnection()) {
                e = this.getRemote().getStructureElementParentsReversed(param1, param2);
            } else {
                e = this.getLocal().getStructureElementParentsReversed(param1, param2);
            }

            return e;
        } catch (Exception var4) {
            throw new CPException("error in getStructureElementParentsReversed", var4);
        }
    }

    public StructureElementParentsFromVisIdELO getStructureElementParentsFromVisId(String param1, int param2) throws CPException {
        try {
            StructureElementParentsFromVisIdELO e = null;
            if (this.isRemoteConnection()) {
                e = this.getRemote().getStructureElementParentsFromVisId(param1, param2);
            } else {
                e = this.getLocal().getStructureElementParentsFromVisId(param1, param2);
            }

            return e;
        } catch (Exception var4) {
            throw new CPException("error in getStructureElementParentsFromVisId", var4);
        }
    }

    public ImmediateChildrenELO getImmediateChildren(int param1, int param2) throws CPException {
        try {
            ImmediateChildrenELO e = null;
            if (this.isRemoteConnection()) {
                e = this.getRemote().getImmediateChildren(param1, param2);
            } else {
                e = this.getLocal().getImmediateChildren(param1, param2);
            }

            return e;
        } catch (Exception var4) {
            throw new CPException("error in getImmediateChildren", var4);
        }
    }

    public MassStateImmediateChildrenELO getMassStateImmediateChildren(int param1, int param2) throws CPException {
        try {
            MassStateImmediateChildrenELO e = null;
            if (this.isRemoteConnection()) {
                e = this.getRemote().getMassStateImmediateChildren(param1, param2);
            } else {
                e = this.getLocal().getMassStateImmediateChildren(param1, param2);
            }

            return e;
        } catch (Exception var4) {
            throw new CPException("error in getMassStateImmediateChildren", var4);
        }
    }

    public StructureElementValuesELO getStructureElementValues(int param1, int param2) throws CPException {
        try {
            StructureElementValuesELO e = null;
            if (this.isRemoteConnection()) {
                e = this.getRemote().getStructureElementValues(param1, param2);
            } else {
                e = this.getLocal().getStructureElementValues(param1, param2);
            }

            return e;
        } catch (Exception var4) {
            throw new CPException("error in getStructureElementValues", var4);
        }
    }

    public CheckLeafELO getCheckLeaf(int param1) throws CPException {
        try {
            CheckLeafELO e = null;
            if (this.isRemoteConnection()) {
                e = this.getRemote().getCheckLeaf(param1);
            } else {
                e = this.getLocal().getCheckLeaf(param1);
            }

            return e;
        } catch (Exception var3) {
            throw new CPException("error in getCheckLeaf", var3);
        }
    }

    public StructureElementELO getStructureElement(int param1) throws CPException {
        try {
            StructureElementELO e = null;
            if (this.isRemoteConnection()) {
                e = this.getRemote().getStructureElement(param1);
            } else {
                e = this.getLocal().getStructureElement(param1);
            }

            return e;
        } catch (Exception var3) {
            throw new CPException("error in getStructureElement", var3);
        }
    }

    public StructureElementForIdsELO getStructureElementForIds(int param1, int param2) throws CPException {
        try {
            StructureElementForIdsELO e = null;
            if (this.isRemoteConnection()) {
                e = this.getRemote().getStructureElementForIds(param1, param2);
            } else {
                e = this.getLocal().getStructureElementForIds(param1, param2);
            }

            return e;
        } catch (Exception var4) {
            throw new CPException("error in getStructureElementForIds", var4);
        }
    }

    public StructureElementByVisIdELO getStructureElementByVisId(String param1, int param2) throws CPException {
        try {
            StructureElementByVisIdELO e = null;
            if (this.isRemoteConnection()) {
                e = this.getRemote().getStructureElementByVisId(param1, param2);
            } else {
                e = this.getLocal().getStructureElementByVisId(param1, param2);
            }

            return e;
        } catch (Exception var4) {
            throw new CPException("error in getStructureElementByVisId", var4);
        }
    }

    public RespAreaImmediateChildrenELO getRespAreaImmediateChildren(int param1, int param2) throws CPException {
        try {
            RespAreaImmediateChildrenELO e = null;
            if (this.isRemoteConnection()) {
                e = this.getRemote().getRespAreaImmediateChildren(param1, param2);
            } else {
                e = this.getLocal().getRespAreaImmediateChildren(param1, param2);
            }

            return e;
        } catch (Exception var4) {
            throw new CPException("error in getRespAreaImmediateChildren", var4);
        }
    }

    public AllDisabledLeafELO getAllDisabledLeaf(int param1) throws CPException {
        try {
            AllDisabledLeafELO e = null;
            if (this.isRemoteConnection()) {
                e = this.getRemote().getAllDisabledLeaf(param1);
            } else {
                e = this.getLocal().getAllDisabledLeaf(param1);
            }

            return e;
        } catch (Exception var3) {
            throw new CPException("error in getAllDisabledLeaf", var3);
        }
    }

    public AllNotPlannableELO getAllNotPlannable(int param1) throws CPException {
        try {
            AllNotPlannableELO e = null;
            if (this.isRemoteConnection()) {
                e = this.getRemote().getAllNotPlannable(param1);
            } else {
                e = this.getLocal().getAllNotPlannable(param1);
            }

            return e;
        } catch (Exception var3) {
            throw new CPException("error in getAllNotPlannable", var3);
        }
    }

    public AllDisabledLeafandNotPlannableELO getAllDisabledLeafandNotPlannable(int param1) throws CPException {
        try {
            AllDisabledLeafandNotPlannableELO e = null;
            if (this.isRemoteConnection()) {
                e = this.getRemote().getAllDisabledLeafandNotPlannable(param1);
            } else {
                e = this.getLocal().getAllDisabledLeafandNotPlannable(param1);
            }

            return e;
        } catch (Exception var3) {
            throw new CPException("error in getAllDisabledLeafandNotPlannable", var3);
        }
    }

    public LeavesForParentELO getLeavesForParent(int param1, int param2, int param3, int param4, int param5) throws CPException {
        try {
            LeavesForParentELO e = null;
            if (this.isRemoteConnection()) {
                e = this.getRemote().getLeavesForParent(param1, param2, param3, param4, param5);
            } else {
                e = this.getLocal().getLeavesForParent(param1, param2, param3, param4, param5);
            }

            return e;
        } catch (Exception var7) {
            throw new CPException("error in getLeavesForParent", var7);
        }
    }

    public ChildrenForParentELO getChildrenForParent(int param1, int param2, int param3, int param4, int param5) throws CPException {
        try {
            ChildrenForParentELO e = null;
            if (this.isRemoteConnection()) {
                e = this.getRemote().getChildrenForParent(param1, param2, param3, param4, param5);
            } else {
                e = this.getLocal().getChildrenForParent(param1, param2, param3, param4, param5);
            }

            return e;
        } catch (Exception var7) {
            throw new CPException("error in getChildrenForParent", var7);
        }
    }

    public ReportLeavesForParentELO getReportLeavesForParent(int param1, int param2, int param3, int param4, int param5) throws CPException {
        try {
            ReportLeavesForParentELO e = null;
            if (this.isRemoteConnection()) {
                e = this.getRemote().getReportLeavesForParent(param1, param2, param3, param4, param5);
            } else {
                e = this.getLocal().getReportLeavesForParent(param1, param2, param3, param4, param5);
            }

            return e;
        } catch (Exception var7) {
            throw new CPException("error in getReportLeavesForParent", var7);
        }
    }

    public ReportChildrenForParentToRelativeDepthELO getReportChildrenForParentToRelativeDepth(int param1, int param2, int param3, int param4, int param5, int param6, int param7, int param8) throws CPException {
        try {
            ReportChildrenForParentToRelativeDepthELO e = null;
            if (this.isRemoteConnection()) {
                e = this.getRemote().getReportChildrenForParentToRelativeDepth(param1, param2, param3, param4, param5, param6, param7, param8);
            } else {
                e = this.getLocal().getReportChildrenForParentToRelativeDepth(param1, param2, param3, param4, param5, param6, param7, param8);
            }

            return e;
        } catch (Exception var10) {
            throw new CPException("error in getReportChildrenForParentToRelativeDepth", var10);
        }
    }

    public BudgetHierarchyElementELO getBudgetHierarchyElement(int param1) throws CPException {
        try {
            BudgetHierarchyElementELO e = null;
            if (this.isRemoteConnection()) {
                e = this.getRemote().getBudgetHierarchyElement(param1);
            } else {
                e = this.getLocal().getBudgetHierarchyElement(param1);
            }

            return e;
        } catch (Exception var3) {
            throw new CPException("error in getBudgetHierarchyElement", var3);
        }
    }

    public BudgetLocationElementForModelELO getBudgetLocationElementForModel(int param1, int param2) throws CPException {
        try {
            BudgetLocationElementForModelELO e = null;
            if (this.isRemoteConnection()) {
                e = this.getRemote().getBudgetLocationElementForModel(param1, param2);
            } else {
                e = this.getLocal().getBudgetLocationElementForModel(param1, param2);
            }

            return e;
        } catch (Exception var4) {
            throw new CPException("error in getBudgetLocationElementForModel", var4);
        }
    }

    public AllDimensionsELO getAllDimensions() throws CPException {
        try {
            AllDimensionsELO e = null;
            if (this.isRemoteConnection()) {
                e = this.getRemote().getAllDimensions();
            } else {
                e = this.getLocal().getAllDimensions();
            }

            return e;
        } catch (Exception var2) {
            throw new CPException("error in getAllDimensions", var2);
        }
    }

    public AllDimensionsELO getDimensionsForLoggedUser() throws CPException {
        try {
            AllDimensionsELO e = null;
            int userId = getConnection().getUserContext().getUserId();
            if (this.isRemoteConnection()) {
                e = this.getRemote().getDimensionsForLoggedUser(userId);
            } else {
                e = this.getLocal().getDimensionsForLoggedUser(userId);
            }

            return e;
        } catch (Exception var2) {
            throw new CPException("error in getDimensionsForLoggedUser", var2);
        }
    }

    public AvailableDimensionsELO getAvailableDimensions() throws CPException {
        try {
            AvailableDimensionsELO e = null;
            if (this.isRemoteConnection()) {
                e = this.getRemote().getAvailableDimensions();
            } else {
                e = this.getLocal().getAvailableDimensions();
            }

            return e;
        } catch (Exception var2) {
            throw new CPException("error in getAvailableDimensions", var2);
        }
    }

    public ImportableDimensionsELO getImportableDimensions() throws CPException {
        try {
            ImportableDimensionsELO e = null;
            if (this.isRemoteConnection()) {
                e = this.getRemote().getImportableDimensions();
            } else {
                e = this.getLocal().getImportableDimensions();
            }

            return e;
        } catch (Exception var2) {
            throw new CPException("error in getImportableDimensions", var2);
        }
    }

    public AllDimensionsForModelELO getAllDimensionsForModel(int param1) throws CPException {
        try {
            AllDimensionsForModelELO e = null;
            if (this.isRemoteConnection()) {
                e = this.getRemote().getAllDimensionsForModel(param1);
            } else {
                e = this.getLocal().getAllDimensionsForModel(param1);
            }

            return e;
        } catch (Exception var3) {
            throw new CPException("error in getAllDimensionsForModel", var3);
        }
    }

    public AllDimensionElementsELO getAllDimensionElements() throws CPException {
        try {
            AllDimensionElementsELO e = null;
            if (this.isRemoteConnection()) {
                e = this.getRemote().getAllDimensionElements();
            } else {
                e = this.getLocal().getAllDimensionElements();
            }

            return e;
        } catch (Exception var2) {
            throw new CPException("error in getAllDimensionElements", var2);
        }
    }

    public AllDimensionElementsForDimensionELO getAllDimensionElementsForDimension(int param1) throws CPException {
        try {
            AllDimensionElementsForDimensionELO e = null;
            if (this.isRemoteConnection()) {
                e = this.getRemote().getAllDimensionElementsForDimension(param1);
            } else {
                e = this.getLocal().getAllDimensionElementsForDimension(param1);
            }

            return e;
        } catch (Exception var3) {
            throw new CPException("error in getAllDimensionElementsForDimension", var3);
        }
    }
    
    //FIXME getAllDimensionElementsForModels!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    public AllDimensionElementsForModelELO getAllDimensionElementsForModels(List<Integer> modelIds) throws CPException {
        try {
            AllDimensionElementsForModelELO e = null;
            if (this.isRemoteConnection()) {
                e = this.getRemote().getAllDimensionElementsForModels(modelIds);
            } else {
                e = this.getLocal().getAllDimensionElementsForModels(modelIds);
            }

            return e;
        } catch (Exception ex) {
            throw new CPException("error in getAllDimensionElementsForModels", ex);
        }
    }

    public AllHierarchysELO getAllHierarchys() throws CPException {
        try {
            AllHierarchysELO e = null;
            if (this.isRemoteConnection()) {
                e = this.getRemote().getAllHierarchys();
            } else {
                e = this.getLocal().getAllHierarchys();
            }

            return e;
        } catch (Exception var2) {
            throw new CPException("error in getAllHierarchys", var2);
        }
    }

    public AllHierarchysELO getHierarchiesForLoggedUser() throws CPException {
        try {
            AllHierarchysELO e = null;
            if (this.isRemoteConnection()) {
                e = this.getRemote().getHierarchiesForLoggedUser(getConnection().getUserContext().getUserId());
            } else {
                e = this.getLocal().getHierarchiesForLoggedUser(getConnection().getUserContext().getUserId());
            }

            return e;
        } catch (Exception var2) {
            throw new CPException("error in getAllHierarchys", var2);
        }
    }

    public SelectedHierarchysELO getSelectedHierarchys() throws CPException {
        try {
            SelectedHierarchysELO e = null;
            if (this.isRemoteConnection()) {
                e = this.getRemote().getSelectedHierarchys();
            } else {
                e = this.getLocal().getSelectedHierarchys();
            }

            return e;
        } catch (Exception var2) {
            throw new CPException("error in getSelectedHierarchys", var2);
        }
    }

    public ImportableHierarchiesELO getImportableHierarchies(int param1) throws CPException {
        try {
            ImportableHierarchiesELO e = null;
            if (this.isRemoteConnection()) {
                e = this.getRemote().getImportableHierarchies(param1);
            } else {
                e = this.getLocal().getImportableHierarchies(param1);
            }

            return e;
        } catch (Exception var3) {
            throw new CPException("error in getImportableHierarchies", var3);
        }
    }

    public HierarcyDetailsFromDimIdELO getHierarcyDetailsFromDimId(int param1) throws CPException {
        try {
            HierarcyDetailsFromDimIdELO e = null;
            if (this.isRemoteConnection()) {
                e = this.getRemote().getHierarcyDetailsFromDimId(param1);
            } else {
                e = this.getLocal().getHierarcyDetailsFromDimId(param1);
            }

            return e;
        } catch (Exception var3) {
            throw new CPException("error in getHierarcyDetailsFromDimId", var3);
        }
    }

    public HierarcyDetailsIncRootNodeFromDimIdELO getHierarcyDetailsIncRootNodeFromDimId(int param1) throws CPException {
        try {
            HierarcyDetailsIncRootNodeFromDimIdELO e = null;
            if (this.isRemoteConnection()) {
                e = this.getRemote().getHierarcyDetailsIncRootNodeFromDimId(param1);
            } else {
                e = this.getLocal().getHierarcyDetailsIncRootNodeFromDimId(param1);
            }

            return e;
        } catch (Exception var3) {
            throw new CPException("error in getHierarcyDetailsIncRootNodeFromDimId", var3);
        }
    }

    public CalendarForModelELO getCalendarForModel(int param1) throws CPException {
        try {
            CalendarForModelELO e = null;
            if (this.isRemoteConnection()) {
                e = this.getRemote().getCalendarForModel(param1);
            } else {
                e = this.getLocal().getCalendarForModel(param1);
            }

            return e;
        } catch (Exception var3) {
            throw new CPException("error in getCalendarForModel", var3);
        }
    }

    public CalendarForModelVisIdELO getCalendarForModelVisId(String param1) throws CPException {
        try {
            CalendarForModelVisIdELO e = null;
            if (this.isRemoteConnection()) {
                e = this.getRemote().getCalendarForModelVisId(param1);
            } else {
                e = this.getLocal().getCalendarForModelVisId(param1);
            }

            return e;
        } catch (Exception var3) {
            throw new CPException("error in getCalendarForModelVisId", var3);
        }
    }

    public CalendarForFinanceCubeELO getCalendarForFinanceCube(int param1) throws CPException {
        try {
            CalendarForFinanceCubeELO e = null;
            if (this.isRemoteConnection()) {
                e = this.getRemote().getCalendarForFinanceCube(param1);
            } else {
                e = this.getLocal().getCalendarForFinanceCube(param1);
            }

            return e;
        } catch (Exception var3) {
            throw new CPException("error in getCalendarForFinanceCube", var3);
        }
    }

    public HierarachyElementELO getHierarachyElement(int param1) throws CPException {
        try {
            HierarachyElementELO e = null;
            if (this.isRemoteConnection()) {
                e = this.getRemote().getHierarachyElement(param1);
            } else {
                e = this.getLocal().getHierarachyElement(param1);
            }

            return e;
        } catch (Exception var3) {
            throw new CPException("error in getHierarachyElement", var3);
        }
    }

    public AugHierarachyElementELO getAugHierarachyElement(int param1) throws CPException {
        try {
            AugHierarachyElementELO e = null;
            if (this.isRemoteConnection()) {
                e = this.getRemote().getAugHierarachyElement(param1);
            } else {
                e = this.getLocal().getAugHierarachyElement(param1);
            }

            return e;
        } catch (Exception var3) {
            throw new CPException("error in getAugHierarachyElement", var3);
        }
    }

    public AllFeedsForDimensionElementELO getAllFeedsForDimensionElement(int param1) throws CPException {
        try {
            AllFeedsForDimensionElementELO e = null;
            if (this.isRemoteConnection()) {
                e = this.getRemote().getAllFeedsForDimensionElement(param1);
            } else {
                e = this.getLocal().getAllFeedsForDimensionElement(param1);
            }

            return e;
        } catch (Exception var3) {
            throw new CPException("error in getAllFeedsForDimensionElement", var3);
        }
    }

    public AllUsersELO getAllUsers() throws CPException {
        try {
            AllUsersELO e = null;
            if (this.isRemoteConnection()) {
                e = this.getRemote().getAllUsers();
            } else {
                e = this.getLocal().getAllUsers();
            }

            return e;
        } catch (Exception var2) {
            throw new CPException("error in getAllUsers", var2);
        }
    }
    
    public AllRevisionsELO getAllRevisions() throws CPException {
        try {
            AllRevisionsELO e = null;
            if (this.isRemoteConnection()) {
                e = this.getRemote().getAllRevisions();
            } else {
                e = this.getLocal().getAllRevisions();
            }

            return e;
        } catch (Exception var2) {
            throw new CPException("error in getAllRevisions", var2);
        }
    }

    public SecurityStringsForUserELO getSecurityStringsForUser(int param1) throws CPException {
        try {
            SecurityStringsForUserELO e = null;
            if (this.isRemoteConnection()) {
                e = this.getRemote().getSecurityStringsForUser(param1);
            } else {
                e = this.getLocal().getSecurityStringsForUser(param1);
            }

            return e;
        } catch (Exception var3) {
            throw new CPException("error in getSecurityStringsForUser", var3);
        }
    }

    public AllUsersExportELO getAllUsersExport() throws CPException {
        try {
            AllUsersExportELO e = null;
            if (this.isRemoteConnection()) {
                e = this.getRemote().getAllUsersExport();
            } else {
                e = this.getLocal().getAllUsersExport();
            }

            return e;
        } catch (Exception var2) {
            throw new CPException("error in getAllUsersExport", var2);
        }
    }

    public AllUserAttributesELO getAllUserAttributes() throws CPException {
        try {
            AllUserAttributesELO e = null;
            if (this.isRemoteConnection()) {
                e = this.getRemote().getAllUserAttributes();
            } else {
                e = this.getLocal().getAllUserAttributes();
            }

            return e;
        } catch (Exception var2) {
            throw new CPException("error in getAllUserAttributes", var2);
        }
    }

    public AllNonDisabledUsersELO getAllNonDisabledUsers() throws CPException {
        try {
            AllNonDisabledUsersELO e = null;
            if (this.isRemoteConnection()) {
                e = this.getRemote().getAllNonDisabledUsers();
            } else {
                e = this.getLocal().getAllNonDisabledUsers();
            }

            return e;
        } catch (Exception var2) {
            throw new CPException("error in getAllNonDisabledUsers", var2);
        }
    }

    public UserMessageAttributesELO getUserMessageAttributes() throws CPException {
        try {
            UserMessageAttributesELO e = null;
            if (this.isRemoteConnection()) {
                e = this.getRemote().getUserMessageAttributes();
            } else {
                e = this.getLocal().getUserMessageAttributes();
            }

            return e;
        } catch (Exception var2) {
            throw new CPException("error in getUserMessageAttributes", var2);
        }
    }

    public UserMessageAttributesForIdELO getUserMessageAttributesForId(int param1) throws CPException {
        try {
            UserMessageAttributesForIdELO e = null;
            if (this.isRemoteConnection()) {
                e = this.getRemote().getUserMessageAttributesForId(param1);
            } else {
                e = this.getLocal().getUserMessageAttributesForId(param1);
            }

            return e;
        } catch (Exception var3) {
            throw new CPException("error in getUserMessageAttributesForId", var3);
        }
    }

    public UserMessageAttributesForNameELO getUserMessageAttributesForName(String param1) throws CPException {
        try {
            UserMessageAttributesForNameELO e = null;
            if (this.isRemoteConnection()) {
                e = this.getRemote().getUserMessageAttributesForName(param1);
            } else {
                e = this.getLocal().getUserMessageAttributesForName(param1);
            }

            return e;
        } catch (Exception var3) {
            throw new CPException("error in getUserMessageAttributesForName", var3);
        }
    }

    public FinanceSystemUserNameELO getFinanceSystemUserName(int param1) throws CPException {
        try {
            FinanceSystemUserNameELO e = null;
            if (this.isRemoteConnection()) {
                e = this.getRemote().getFinanceSystemUserName(param1);
            } else {
                e = this.getLocal().getFinanceSystemUserName(param1);
            }

            return e;
        } catch (Exception var3) {
            throw new CPException("error in getFinanceSystemUserName", var3);
        }
    }

    public UsersWithSecurityStringELO getUsersWithSecurityString(String param1) throws CPException {
        try {
            UsersWithSecurityStringELO e = null;
            if (this.isRemoteConnection()) {
                e = this.getRemote().getUsersWithSecurityString(param1);
            } else {
                e = this.getLocal().getUsersWithSecurityString(param1);
            }

            return e;
        } catch (Exception var3) {
            throw new CPException("error in getUsersWithSecurityString", var3);
        }
    }

    public AllRolesForUsersELO getAllRolesForUsers() throws CPException {
        try {
            AllRolesForUsersELO e = null;
            if (this.isRemoteConnection()) {
                e = this.getRemote().getAllRolesForUsers();
            } else {
                e = this.getLocal().getAllRolesForUsers();
            }

            return e;
        } catch (Exception var2) {
            throw new CPException("error in getAllRolesForUsers", var2);
        }
    }

    public AllRolesELO getAllRoles() throws CPException {
        try {
            AllRolesELO e = null;
            if (this.isRemoteConnection()) {
                e = this.getRemote().getAllRoles();
            } else {
                e = this.getLocal().getAllRoles();
            }

            return e;
        } catch (Exception var2) {
            throw new CPException("error in getAllRoles", var2);
        }
    }

    public AllHiddenRolesELO getAllHiddenRoles() throws CPException {
        try {
            AllHiddenRolesELO e = null;
            if (this.isRemoteConnection()) {
                e = this.getRemote().getAllHiddenRoles();
            } else {
                e = this.getLocal().getAllHiddenRoles();
            }

            return e;
        } catch (Exception var2) {
            throw new CPException("error in getAllHiddenRoles", var2);
        }
    }

    public AllRolesForUserELO getAllRolesForUser(int param1) throws CPException {
        try {
            AllRolesForUserELO e = null;
            if (this.isRemoteConnection()) {
                e = this.getRemote().getAllRolesForUser(param1);
            } else {
                e = this.getLocal().getAllRolesForUser(param1);
            }

            return e;
        } catch (Exception var3) {
            throw new CPException("error in getAllRolesForUser", var3);
        }
    }

    public AllRolesForUserELO getAllHiddenRolesForUser(int param1) throws CPException {
        try {
            AllRolesForUserELO e = null;
            if (this.isRemoteConnection()) {
                e = this.getRemote().getAllHiddenRolesForUser(param1);
            } else {
                e = this.getLocal().getAllHiddenRolesForUser(param1);
            }

            return e;
        } catch (Exception var3) {
            throw new CPException("error in getAllRolesForUser", var3);
        }
    }

    public AllSecurityRolesELO getAllSecurityRoles() throws CPException {
        try {
            AllSecurityRolesELO e = null;
            if (this.isRemoteConnection()) {
                e = this.getRemote().getAllSecurityRoles();
            } else {
                e = this.getLocal().getAllSecurityRoles();
            }

            return e;
        } catch (Exception var2) {
            throw new CPException("error in getAllSecurityRoles", var2);
        }
    }

    public AllSecurityRolesForRoleELO getAllSecurityRolesForRole(int param1) throws CPException {
        try {
            AllSecurityRolesForRoleELO e = null;
            if (this.isRemoteConnection()) {
                e = this.getRemote().getAllSecurityRolesForRole(param1);
            } else {
                e = this.getLocal().getAllSecurityRolesForRole(param1);
            }

            return e;
        } catch (Exception var3) {
            throw new CPException("error in getAllSecurityRolesForRole", var3);
        }
    }

    public UserPreferencesForUserELO getUserPreferencesForUser(int param1) throws CPException {
        try {
            UserPreferencesForUserELO e = null;
            if (this.isRemoteConnection()) {
                e = this.getRemote().getUserPreferencesForUser(param1);
            } else {
                e = this.getLocal().getUserPreferencesForUser(param1);
            }

            return e;
        } catch (Exception var3) {
            throw new CPException("error in getUserPreferencesForUser", var3);
        }
    }

    public AllBudgetInstructionsELO getAllBudgetInstructions() throws CPException {
        try {
            AllBudgetInstructionsELO e = null;
            if (this.isRemoteConnection()) {
                e = this.getRemote().getAllBudgetInstructions();
            } else {
                e = this.getLocal().getAllBudgetInstructions();
            }

            return e;
        } catch (Exception var2) {
            throw new CPException("error in getAllBudgetInstructions", var2);
        }
    }

    public AllBudgetInstructionsWebELO getAllBudgetInstructionsWeb() throws CPException {
        try {
            AllBudgetInstructionsWebELO e = null;
            if (this.isRemoteConnection()) {
                e = this.getRemote().getAllBudgetInstructionsWeb();
            } else {
                e = this.getLocal().getAllBudgetInstructionsWeb();
            }

            return e;
        } catch (Exception var2) {
            throw new CPException("error in getAllBudgetInstructionsWeb", var2);
        }
    }

    public AllBudgetInstructionsForModelELO getAllBudgetInstructionsForModel(int param1) throws CPException {
        try {
            AllBudgetInstructionsForModelELO e = null;
            if (this.isRemoteConnection()) {
                e = this.getRemote().getAllBudgetInstructionsForModel(param1);
            } else {
                e = this.getLocal().getAllBudgetInstructionsForModel(param1);
            }

            return e;
        } catch (Exception var3) {
            throw new CPException("error in getAllBudgetInstructionsForModel", var3);
        }
    }

    public AllBudgetInstructionsForCycleELO getAllBudgetInstructionsForCycle(int param1) throws CPException {
        try {
            AllBudgetInstructionsForCycleELO e = null;
            if (this.isRemoteConnection()) {
                e = this.getRemote().getAllBudgetInstructionsForCycle(param1);
            } else {
                e = this.getLocal().getAllBudgetInstructionsForCycle(param1);
            }

            return e;
        } catch (Exception var3) {
            throw new CPException("error in getAllBudgetInstructionsForCycle", var3);
        }
    }

    public AllBudgetInstructionsForLocationELO getAllBudgetInstructionsForLocation(int param1) throws CPException {
        try {
            AllBudgetInstructionsForLocationELO e = null;
            if (this.isRemoteConnection()) {
                e = this.getRemote().getAllBudgetInstructionsForLocation(param1);
            } else {
                e = this.getLocal().getAllBudgetInstructionsForLocation(param1);
            }

            return e;
        } catch (Exception var3) {
            throw new CPException("error in getAllBudgetInstructionsForLocation", var3);
        }
    }

    public AllBudgetInstructionAssignmentsELO getAllBudgetInstructionAssignments() throws CPException {
        try {
            AllBudgetInstructionAssignmentsELO e = null;
            if (this.isRemoteConnection()) {
                e = this.getRemote().getAllBudgetInstructionAssignments();
            } else {
                e = this.getLocal().getAllBudgetInstructionAssignments();
            }

            return e;
        } catch (Exception var2) {
            throw new CPException("error in getAllBudgetInstructionAssignments", var2);
        }
    }

    public AllWebTasksELO getAllWebTasks() throws CPException {
        try {
            AllWebTasksELO e = null;
            if (this.isRemoteConnection()) {
                e = this.getRemote().getAllWebTasks();
            } else {
                e = this.getLocal().getAllWebTasks();
            }

            return e;
        } catch (Exception var2) {
            throw new CPException("error in getAllWebTasks", var2);
        }
    }

    public AllWebTasksForUserELO getAllWebTasksForUser(String param1) throws CPException {
        try {
            AllWebTasksForUserELO e = null;
            if (this.isRemoteConnection()) {
                e = this.getRemote().getAllWebTasksForUser(param1);
            } else {
                e = this.getLocal().getAllWebTasksForUser(param1);
            }

            return e;
        } catch (Exception var3) {
            throw new CPException("error in getAllWebTasksForUser", var3);
        }
    }

    public WebTasksDetailsELO getWebTasksDetails(int param1) throws CPException {
        try {
            WebTasksDetailsELO e = null;
            if (this.isRemoteConnection()) {
                e = this.getRemote().getWebTasksDetails(param1);
            } else {
                e = this.getLocal().getWebTasksDetails(param1);
            }

            return e;
        } catch (Exception var3) {
            throw new CPException("error in getWebTasksDetails", var3);
        }
    }

    public AllSystemPropertysELO getAllSystemPropertys() throws CPException {
        try {
            AllSystemPropertysELO e = null;
            if (this.isRemoteConnection()) {
                e = this.getRemote().getAllSystemPropertys();
            } else {
                e = this.getLocal().getAllSystemPropertys();
            }

            return e;
        } catch (Exception var2) {
            throw new CPException("error in getAllSystemPropertys", var2);
        }
    }

    public AllSystemPropertysUncachedELO getAllSystemPropertysUncached() throws CPException {
        try {
            AllSystemPropertysUncachedELO e = null;
            if (this.isRemoteConnection()) {
                e = this.getRemote().getAllSystemPropertysUncached();
            } else {
                e = this.getLocal().getAllSystemPropertysUncached();
            }

            return e;
        } catch (Exception var2) {
            throw new CPException("error in getAllSystemPropertysUncached", var2);
        }
    }

    public AllMailPropsELO getAllMailProps() throws CPException {
        try {
            AllMailPropsELO e = null;
            if (this.isRemoteConnection()) {
                e = this.getRemote().getAllMailProps();
            } else {
                e = this.getLocal().getAllMailProps();
            }

            return e;
        } catch (Exception var2) {
            throw new CPException("error in getAllMailProps", var2);
        }
    }

    public SystemPropertyELO getSystemProperty(String param1) throws CPException {
        try {
            SystemPropertyELO e = null;
            if (this.isRemoteConnection()) {
                e = this.getRemote().getSystemProperty(param1);
            } else {
                e = this.getLocal().getSystemProperty(param1);
            }

            return e;
        } catch (Exception var3) {
            throw new CPException("error in getSystemProperty", var3);
        }
    }

    public WebSystemPropertyELO getWebSystemProperty(String param1) throws CPException {
        try {
            WebSystemPropertyELO e = null;
            if (this.isRemoteConnection()) {
                e = this.getRemote().getWebSystemProperty(param1);
            } else {
                e = this.getLocal().getWebSystemProperty(param1);
            }

            return e;
        } catch (Exception var3) {
            throw new CPException("error in getWebSystemProperty", var3);
        }
    }

    public AllXmlFormsELO getAllXmlForms() throws CPException {
        try {
            AllXmlFormsELO e = null;
            if (this.isRemoteConnection()) {
                e = this.getRemote().getAllXmlForms();
            } else {
                e = this.getLocal().getAllXmlForms();
            }

            return e;
        } catch (Exception var2) {
            throw new CPException("error in getAllXmlForms", var2);
        }
    }

    public AllXmlFormsELO getAllXmlFormsForLoggedUser() throws CPException {
        try {
            AllXmlFormsELO e = null;
            if (this.isRemoteConnection()) {
                e = this.getRemote().getAllXmlFormsForLoggedUser(getConnection().getUserContext().getUserId());
            } else {
                e = this.getLocal().getAllXmlFormsForLoggedUser(getConnection().getUserContext().getUserId());
            }

            return e;
        } catch (Exception var2) {
            throw new CPException("error in getAllXmlFormsForLoggedUser", var2);
        }
    }

    public AllFinXmlFormsELO getAllFinXmlForms() throws CPException {
        try {
            AllFinXmlFormsELO e = null;
            if (this.isRemoteConnection()) {
                e = this.getRemote().getAllFinXmlForms(getConnection().getUserContext().getUserId());
            } else {
                e = this.getLocal().getAllFinXmlForms(getConnection().getUserContext().getUserId());
            }

            return e;
        } catch (Exception var2) {
            throw new CPException("error in getAllFinXmlForms", var2);
        }
    }

    public AllFFXmlFormsELO getAllFFXmlForms() throws CPException {
        try {
            AllFFXmlFormsELO e = null;
            if (this.isRemoteConnection()) {
                e = this.getRemote().getAllFFXmlForms();
            } else {
                e = this.getLocal().getAllFFXmlForms();
            }

            return e;
        } catch (Exception var2) {
            throw new CPException("error in getAllFFXmlForms", var2);
        }
    }

    public AllFFXmlFormsELO getAllFFXmlFormsForLoggedUser() throws CPException {
        try {
            AllFFXmlFormsELO e = null;
            if (this.isRemoteConnection()) {
                e = this.getRemote().getAllFFXmlFormsForLoggedUser(getConnection().getUserContext().getUserId());
            } else {
                e = this.getLocal().getAllFFXmlFormsForLoggedUser(getConnection().getUserContext().getUserId());
            }

            return e;
        } catch (Exception var2) {
            throw new CPException("error in getAllFFXmlFormsForLoggedUser", var2);
        }
    }

    public AllXcellXmlFormsELO getAllXcellXmlForms() throws CPException {
        try {
            AllXcellXmlFormsELO e = null;
            if (this.isRemoteConnection()) {
                e = this.getRemote().getAllXcellXmlForms();
            } else {
                e = this.getLocal().getAllXcellXmlForms();
            }

            return e;
        } catch (Exception var2) {
            throw new CPException("error in getAllXcellXmlForms", var2);
        }
    }

    public AllXcellXmlFormsELO getAllXcellXmlFormsForLoggedUser() throws CPException {
        try {
            AllXcellXmlFormsELO e = null;
            if (this.isRemoteConnection()) {
                e = this.getRemote().getAllXcellXmlFormsForLoggedUser(getConnection().getUserContext().getUserId());
            } else {
                e = this.getLocal().getAllXcellXmlFormsForLoggedUser(getConnection().getUserContext().getUserId());
            }

            return e;
        } catch (Exception var2) {
            throw new CPException("error in getAllXcellXmlForms", var2);
        }
    }

    public AllMassVirementXmlFormsELO getAllMassVirementXmlForms() throws CPException {
        try {
            AllMassVirementXmlFormsELO e = null;
            if (this.isRemoteConnection()) {
                e = this.getRemote().getAllMassVirementXmlForms();
            } else {
                e = this.getLocal().getAllMassVirementXmlForms();
            }

            return e;
        } catch (Exception var2) {
            throw new CPException("error in getAllMassVirementXmlForms", var2);
        }
    }

    public AllMassVirementXmlFormsELO getAllMassVirementXmlFormsForLoggedUser() throws CPException {
        try {
            AllMassVirementXmlFormsELO e = null;
            if (this.isRemoteConnection()) {
                e = this.getRemote().getAllMassVirementXmlFormsForLoggedUser(getConnection().getUserContext().getUserId());
            } else {
                e = this.getLocal().getAllMassVirementXmlFormsForLoggedUser(getConnection().getUserContext().getUserId());
            }

            return e;
        } catch (Exception var2) {
            throw new CPException("error in getAllMassVirementXmlForms", var2);
        }
    }

    public AllFinanceXmlFormsELO getAllFinanceXmlForms() throws CPException {
        try {
            AllFinanceXmlFormsELO e = null;
            if (this.isRemoteConnection()) {
                e = this.getRemote().getAllFinanceXmlForms();
            } else {
                e = this.getLocal().getAllFinanceXmlForms();
            }

            return e;
        } catch (Exception var2) {
            throw new CPException("error in getAllFinanceXmlForms", var2);
        }
    }

    public AllFinanceAndFlatFormsELO getAllFinanceAndFlatForms() throws CPException {
        try {
            AllFinanceAndFlatFormsELO e = null;
            if (this.isRemoteConnection()) {
                e = this.getRemote().getAllFinanceAndFlatForms();
            } else {
                e = this.getLocal().getAllFinanceAndFlatForms();
            }

            return e;
        } catch (Exception var2) {
            throw new CPException("error in getAllFinanceAndFlatForms", var2);
        }
    }

    public AllFinanceXmlFormsForModelELO getAllFinanceXmlFormsForModel(int param1) throws CPException {
        try {
            AllFinanceXmlFormsForModelELO e = null;
            if (this.isRemoteConnection()) {
                e = this.getRemote().getAllFinanceXmlFormsForModel(param1);
            } else {
                e = this.getLocal().getAllFinanceXmlFormsForModel(param1);
            }

            return e;
        } catch (Exception var3) {
            throw new CPException("error in getAllFinanceXmlFormsForModel", var3);
        }
    }

    public AllFinanceAndFlatFormsForModelELO getAllFinanceAndFlatFormsForModel(int param1) throws CPException {
        try {
            AllFinanceAndFlatFormsForModelELO e = null;
            if (this.isRemoteConnection()) {
                e = this.getRemote().getAllFinanceAndFlatFormsForModel(param1);
            } else {
                e = this.getLocal().getAllFinanceAndFlatFormsForModel(param1);
            }

            return e;
        } catch (Exception var3) {
            throw new CPException("error in getAllFinanceAndFlatFormsForModel", var3);
        }
    }

    public AllFinanceXmlFormsAndDataTypesForModelELO getAllFinanceXmlFormsAndDataTypesForModel(int param1) throws CPException {
        try {
            AllFinanceXmlFormsAndDataTypesForModelELO e = null;
            if (this.isRemoteConnection()) {
                e = this.getRemote().getAllFinanceXmlFormsAndDataTypesForModel(param1);
            } else {
                e = this.getLocal().getAllFinanceXmlFormsAndDataTypesForModel(param1);
            }

            return e;
        } catch (Exception var3) {
            throw new CPException("error in getAllFinanceXmlFormsAndDataTypesForModel", var3);
        }
    }

    public AllXmlFormsELO getAllXmlFormsForModel(int modelId) throws CPException {
        try {
            AllXmlFormsELO e = null;
            if (this.isRemoteConnection()) {
                e = this.getRemote().getAllXmlFormsForModel(modelId);
            } else {
                e = this.getLocal().getAllXmlFormsForModel(modelId);
            }

            return e;
        } catch (Exception e) {
            throw new CPException("error in getAllXmlFormsForModel", e);
        }
    }

    public AllXmlFormsELO getXcellXmlFormsForUser(int userId) throws CPException {
        try {
            AllXmlFormsELO e = null;
            if (this.isRemoteConnection()) {
                e = this.getRemote().getXcellXmlFormsForUser(userId);
            } else {
                e = this.getLocal().getXcellXmlFormsForUser(userId);
            }

            return e;
        } catch (Exception var2) {
            throw new CPException("error in getXcellXmlFormsForUser", var2);
        }
    }

    public AllFixedXMLFormsELO getAllFixedXMLForms() throws CPException {
        try {
            AllFixedXMLFormsELO e = null;
            if (this.isRemoteConnection()) {
                e = this.getRemote().getAllFixedXMLForms();
            } else {
                e = this.getLocal().getAllFixedXMLForms();
            }

            return e;
        } catch (Exception var2) {
            throw new CPException("error in getAllFixedXMLForms", var2);
        }
    }

    public AllDynamicXMLFormsELO getAllDynamicXMLForms() throws CPException {
        try {
            AllDynamicXMLFormsELO e = null;
            if (this.isRemoteConnection()) {
                e = this.getRemote().getAllDynamicXMLForms();
            } else {
                e = this.getLocal().getAllDynamicXMLForms();
            }

            return e;
        } catch (Exception var2) {
            throw new CPException("error in getAllDynamicXMLForms", var2);
        }
    }

    public AllFlatXMLFormsELO getAllFlatXMLForms() throws CPException {
        try {
            AllFlatXMLFormsELO e = null;
            if (this.isRemoteConnection()) {
                e = this.getRemote().getAllFlatXMLForms();
            } else {
                e = this.getLocal().getAllFlatXMLForms();
            }

            return e;
        } catch (Exception var2) {
            throw new CPException("error in getAllFlatXMLForms", var2);
        }
    }

    public XMLFormDefinitionELO getXMLFormDefinition(int param1) throws CPException {
        try {
            XMLFormDefinitionELO e = null;
            if (this.isRemoteConnection()) {
                e = this.getRemote().getXMLFormDefinition(param1);
            } else {
                e = this.getLocal().getXMLFormDefinition(param1);
            }

            return e;
        } catch (Exception var3) {
            throw new CPException("error in getXMLFormDefinition", var3);
        }
    }

    public XMLFormCellPickerInfoELO getXMLFormCellPickerInfo(int param1) throws CPException {
        try {
            XMLFormCellPickerInfoELO e = null;
            if (this.isRemoteConnection()) {
                e = this.getRemote().getXMLFormCellPickerInfo(param1);
            } else {
                e = this.getLocal().getXMLFormCellPickerInfo(param1);
            }

            return e;
        } catch (Exception var3) {
            throw new CPException("error in getXMLFormCellPickerInfo", var3);
        }
    }

    public AllXMLFormUserLinkELO getAllXMLFormUserLink() throws CPException {
        try {
            AllXMLFormUserLinkELO e = null;
            if (this.isRemoteConnection()) {
                e = this.getRemote().getAllXMLFormUserLink();
            } else {
                e = this.getLocal().getAllXMLFormUserLink();
            }

            return e;
        } catch (Exception var2) {
            throw new CPException("error in getAllXMLFormUserLink", var2);
        }
    }

    public CheckXMLFormUserLinkELO getCheckXMLFormUserLink(int param1) throws CPException {
        try {
            CheckXMLFormUserLinkELO e = null;
            if (this.isRemoteConnection()) {
                e = this.getRemote().getCheckXMLFormUserLink(param1);
            } else {
                e = this.getLocal().getCheckXMLFormUserLink(param1);
            }

            return e;
        } catch (Exception var3) {
            throw new CPException("error in getCheckXMLFormUserLink", var3);
        }
    }

    public AllFormNotesForBudgetLocationELO getAllFormNotesForBudgetLocation(int param1) throws CPException {
        try {
            AllFormNotesForBudgetLocationELO e = null;
            if (this.isRemoteConnection()) {
                e = this.getRemote().getAllFormNotesForBudgetLocation(param1);
            } else {
                e = this.getLocal().getAllFormNotesForBudgetLocation(param1);
            }

            return e;
        } catch (Exception var3) {
            throw new CPException("error in getAllFormNotesForBudgetLocation", var3);
        }
    }

    public AllFormNotesForFormAndBudgetLocationELO getAllFormNotesForFormAndBudgetLocation(int param1, int param2) throws CPException {
        try {
            AllFormNotesForFormAndBudgetLocationELO e = null;
            if (this.isRemoteConnection()) {
                e = this.getRemote().getAllFormNotesForFormAndBudgetLocation(param1, param2);
            } else {
                e = this.getLocal().getAllFormNotesForFormAndBudgetLocation(param1, param2);
            }

            return e;
        } catch (Exception var4) {
            throw new CPException("error in getAllFormNotesForFormAndBudgetLocation", var4);
        }
    }

    public AllXmlReportsELO getAllXmlReports() throws CPException {
        try {
            AllXmlReportsELO e = null;
            if (this.isRemoteConnection()) {
                e = this.getRemote().getAllXmlReports();
            } else {
                e = this.getLocal().getAllXmlReports();
            }

            return e;
        } catch (Exception var2) {
            throw new CPException("error in getAllXmlReports", var2);
        }
    }

    public AllPublicXmlReportsELO getAllPublicXmlReports() throws CPException {
        try {
            AllPublicXmlReportsELO e = null;
            if (this.isRemoteConnection()) {
                e = this.getRemote().getAllPublicXmlReports();
            } else {
                e = this.getLocal().getAllPublicXmlReports();
            }

            return e;
        } catch (Exception var2) {
            throw new CPException("error in getAllPublicXmlReports", var2);
        }
    }

    public AllXmlReportsForUserELO getAllXmlReportsForUser(int param1) throws CPException {
        try {
            AllXmlReportsForUserELO e = null;
            if (this.isRemoteConnection()) {
                e = this.getRemote().getAllXmlReportsForUser(param1);
            } else {
                e = this.getLocal().getAllXmlReportsForUser(param1);
            }

            return e;
        } catch (Exception var3) {
            throw new CPException("error in getAllXmlReportsForUser", var3);
        }
    }

    public XmlReportsForFolderELO getXmlReportsForFolder(int param1) throws CPException {
        try {
            XmlReportsForFolderELO e = null;
            if (this.isRemoteConnection()) {
                e = this.getRemote().getXmlReportsForFolder(param1);
            } else {
                e = this.getLocal().getXmlReportsForFolder(param1);
            }

            return e;
        } catch (Exception var3) {
            throw new CPException("error in getXmlReportsForFolder", var3);
        }
    }

    public SingleXmlReportELO getSingleXmlReport(int param1, String param2) throws CPException {
        try {
            SingleXmlReportELO e = null;
            if (this.isRemoteConnection()) {
                e = this.getRemote().getSingleXmlReport(param1, param2);
            } else {
                e = this.getLocal().getSingleXmlReport(param1, param2);
            }

            return e;
        } catch (Exception var4) {
            throw new CPException("error in getSingleXmlReport", var4);
        }
    }

    public AllXmlReportFoldersELO getAllXmlReportFolders() throws CPException {
        try {
            AllXmlReportFoldersELO e = null;
            if (this.isRemoteConnection()) {
                e = this.getRemote().getAllXmlReportFolders();
            } else {
                e = this.getLocal().getAllXmlReportFolders();
            }

            return e;
        } catch (Exception var2) {
            throw new CPException("error in getAllXmlReportFolders", var2);
        }
    }

    public DecendentFoldersELO getDecendentFolders(int param1) throws CPException {
        try {
            DecendentFoldersELO e = null;
            if (this.isRemoteConnection()) {
                e = this.getRemote().getDecendentFolders(param1);
            } else {
                e = this.getLocal().getDecendentFolders(param1);
            }

            return e;
        } catch (Exception var3) {
            throw new CPException("error in getDecendentFolders", var3);
        }
    }

    public ReportFolderWithIdELO getReportFolderWithId(int param1) throws CPException {
        try {
            ReportFolderWithIdELO e = null;
            if (this.isRemoteConnection()) {
                e = this.getRemote().getReportFolderWithId(param1);
            } else {
                e = this.getLocal().getReportFolderWithId(param1);
            }

            return e;
        } catch (Exception var3) {
            throw new CPException("error in getReportFolderWithId", var3);
        }
    }

    public AllDataEntryProfilesELO getAllDataEntryProfiles() throws CPException {
        try {
            AllDataEntryProfilesELO e = null;
            if (this.isRemoteConnection()) {
                e = this.getRemote().getAllDataEntryProfiles();
            } else {
                e = this.getLocal().getAllDataEntryProfiles();
            }

            return e;
        } catch (Exception var2) {
            throw new CPException("error in getAllDataEntryProfiles", var2);
        }
    }

    public AllDataEntryProfilesForUserELO getAllDataEntryProfilesForUser(int param1, int param2, int budgetCycleId) throws CPException {
        try {
            AllDataEntryProfilesForUserELO e = null;
            if (this.isRemoteConnection()) {
                e = this.getRemote().getAllDataEntryProfilesForUser(param1, param2, budgetCycleId);
            } else {
                e = this.getLocal().getAllDataEntryProfilesForUser(param1, param2, budgetCycleId);
            }

            return e;
        } catch (Exception var4) {
            throw new CPException("error in getAllDataEntryProfilesForUser", var4);
        }
    }

    public AllUsersForDataEntryProfilesForModelELO getAllUsersForDataEntryProfilesForModel(int param1) throws CPException {
        try {
            AllUsersForDataEntryProfilesForModelELO e = null;
            if (this.isRemoteConnection()) {
                e = this.getRemote().getAllUsersForDataEntryProfilesForModel(param1);
            } else {
                e = this.getLocal().getAllUsersForDataEntryProfilesForModel(param1);
            }

            return e;
        } catch (Exception var3) {
            throw new CPException("error in getAllUsersForDataEntryProfilesForModel", var3);
        }
    }

    public AllDataEntryProfilesForFormELO getAllDataEntryProfilesForForm(int param1) throws CPException {
        try {
            AllDataEntryProfilesForFormELO e = null;
            if (this.isRemoteConnection()) {
                e = this.getRemote().getAllDataEntryProfilesForForm(param1);
            } else {
                e = this.getLocal().getAllDataEntryProfilesForForm(param1);
            }

            return e;
        } catch (Exception var3) {
            throw new CPException("error in getAllDataEntryProfilesForForm", var3);
        }
    }

    public DefaultDataEntryProfileELO getDefaultDataEntryProfile(int param1, int param2, int param3, int param4) throws CPException {
        try {
            DefaultDataEntryProfileELO e = null;
            if (this.isRemoteConnection()) {
                e = this.getRemote().getDefaultDataEntryProfile(param1, param2, param3, param4);
            } else {
                e = this.getLocal().getDefaultDataEntryProfile(param1, param2, param3, param4);
            }

            return e;
        } catch (Exception var5) {
            throw new CPException("error in getDefaultDataEntryProfile", var5);
        }
    }

    public AllDataEntryProfileHistorysELO getAllDataEntryProfileHistorys() throws CPException {
        try {
            AllDataEntryProfileHistorysELO e = null;
            if (this.isRemoteConnection()) {
                e = this.getRemote().getAllDataEntryProfileHistorys();
            } else {
                e = this.getLocal().getAllDataEntryProfileHistorys();
            }

            return e;
        } catch (Exception var2) {
            throw new CPException("error in getAllDataEntryProfileHistorys", var2);
        }
    }

    public AllUdefLookupsELO getAllUdefLookups() throws CPException {
        try {
            AllUdefLookupsELO e = null;
            if (this.isRemoteConnection()) {
                e = this.getRemote().getAllUdefLookups();
            } else {
                e = this.getLocal().getAllUdefLookups();
            }

            return e;
        } catch (Exception var2) {
            throw new CPException("error in getAllUdefLookups", var2);
        }
    }

    public AllUdefLookupsELO getAllUdefLookupsForLoggedUser() throws CPException {
        try {
            AllUdefLookupsELO e = null;
            if (this.isRemoteConnection()) {
                e = this.getRemote().getAllUdefLookupsForLoggedUser(getConnection().getUserContext().getUserId());
            } else {
                e = this.getLocal().getAllUdefLookupsForLoggedUser(getConnection().getUserContext().getUserId());
            }

            return e;
        } catch (Exception var2) {
            throw new CPException("error in getAllUdefLookups", var2);
        }
    }

    public AllSecurityRangesELO getAllSecurityRanges() throws CPException {
        try {
            AllSecurityRangesELO e = null;
            if (this.isRemoteConnection()) {
                e = this.getRemote().getAllSecurityRanges();
            } else {
                e = this.getLocal().getAllSecurityRanges();
            }

            return e;
        } catch (Exception var2) {
            throw new CPException("error in getAllSecurityRanges", var2);
        }
    }

    public AllSecurityRangesForModelELO getAllSecurityRangesForModel(int param1) throws CPException {
        try {
            AllSecurityRangesForModelELO e = null;
            if (this.isRemoteConnection()) {
                e = this.getRemote().getAllSecurityRangesForModel(param1);
            } else {
                e = this.getLocal().getAllSecurityRangesForModel(param1);
            }

            return e;
        } catch (Exception var3) {
            throw new CPException("error in getAllSecurityRangesForModel", var3);
        }
    }

    public AllSecurityAccessDefsELO getAllSecurityAccessDefs() throws CPException {
        try {
            AllSecurityAccessDefsELO e = null;
            if (this.isRemoteConnection()) {
                e = this.getRemote().getAllSecurityAccessDefs();
            } else {
                e = this.getLocal().getAllSecurityAccessDefs();
            }

            return e;
        } catch (Exception var2) {
            throw new CPException("error in getAllSecurityAccessDefs", var2);
        }
    }

    public AllSecurityAccessDefsForModelELO getAllSecurityAccessDefsForModel(int param1) throws CPException {
        try {
            AllSecurityAccessDefsForModelELO e = null;
            if (this.isRemoteConnection()) {
                e = this.getRemote().getAllSecurityAccessDefsForModel(param1);
            } else {
                e = this.getLocal().getAllSecurityAccessDefsForModel(param1);
            }

            return e;
        } catch (Exception var3) {
            throw new CPException("error in getAllSecurityAccessDefsForModel", var3);
        }
    }

    public AllAccessDefsUsingRangeELO getAllAccessDefsUsingRange(int param1) throws CPException {
        try {
            AllAccessDefsUsingRangeELO e = null;
            if (this.isRemoteConnection()) {
                e = this.getRemote().getAllAccessDefsUsingRange(param1);
            } else {
                e = this.getLocal().getAllAccessDefsUsingRange(param1);
            }

            return e;
        } catch (Exception var3) {
            throw new CPException("error in getAllAccessDefsUsingRange", var3);
        }
    }

    public AllSecurityGroupsELO getAllSecurityGroups() throws CPException {
        try {
            AllSecurityGroupsELO e = null;
            if (this.isRemoteConnection()) {
                e = this.getRemote().getAllSecurityGroups();
            } else {
                e = this.getLocal().getAllSecurityGroups();
            }

            return e;
        } catch (Exception var2) {
            throw new CPException("error in getAllSecurityGroups", var2);
        }
    }

    public AllSecurityGroupsUsingAccessDefELO getAllSecurityGroupsUsingAccessDef(int param1) throws CPException {
        try {
            AllSecurityGroupsUsingAccessDefELO e = null;
            if (this.isRemoteConnection()) {
                e = this.getRemote().getAllSecurityGroupsUsingAccessDef(param1);
            } else {
                e = this.getLocal().getAllSecurityGroupsUsingAccessDef(param1);
            }

            return e;
        } catch (Exception var3) {
            throw new CPException("error in getAllSecurityGroupsUsingAccessDef", var3);
        }
    }

    public AllSecurityGroupsForUserELO getAllSecurityGroupsForUser(int param1) throws CPException {
        try {
            AllSecurityGroupsForUserELO e = null;
            if (this.isRemoteConnection()) {
                e = this.getRemote().getAllSecurityGroupsForUser(param1);
            } else {
                e = this.getLocal().getAllSecurityGroupsForUser(param1);
            }

            return e;
        } catch (Exception var3) {
            throw new CPException("error in getAllSecurityGroupsForUser", var3);
        }
    }

    public AllCcDeploymentsELO getAllCcDeployments() throws CPException {
        try {
            AllCcDeploymentsELO e = null;
            if (this.isRemoteConnection()) {
                e = this.getRemote().getAllCcDeployments();
            } else {
                e = this.getLocal().getAllCcDeployments();
            }

            return e;
        } catch (Exception var2) {
            throw new CPException("error in getAllCcDeployments", var2);
        }
    }

    public CcDeploymentsForLookupTableELO getCcDeploymentsForLookupTable(String param1) throws CPException {
        try {
            CcDeploymentsForLookupTableELO e = null;
            if (this.isRemoteConnection()) {
                e = this.getRemote().getCcDeploymentsForLookupTable(param1);
            } else {
                e = this.getLocal().getCcDeploymentsForLookupTable(param1);
            }

            return e;
        } catch (Exception var3) {
            throw new CPException("error in getCcDeploymentsForLookupTable", var3);
        }
    }

    public CcDeploymentsForXmlFormELO getCcDeploymentsForXmlForm(int param1) throws CPException {
        try {
            CcDeploymentsForXmlFormELO e = null;
            if (this.isRemoteConnection()) {
                e = this.getRemote().getCcDeploymentsForXmlForm(param1);
            } else {
                e = this.getLocal().getCcDeploymentsForXmlForm(param1);
            }

            return e;
        } catch (Exception var3) {
            throw new CPException("error in getCcDeploymentsForXmlForm", var3);
        }
    }

    public CcDeploymentsForModelELO getCcDeploymentsForModel(int param1) throws CPException {
        try {
            CcDeploymentsForModelELO e = null;
            if (this.isRemoteConnection()) {
                e = this.getRemote().getCcDeploymentsForModel(param1);
            } else {
                e = this.getLocal().getCcDeploymentsForModel(param1);
            }

            return e;
        } catch (Exception var3) {
            throw new CPException("error in getCcDeploymentsForModel", var3);
        }
    }

    public CcDeploymentCellPickerInfoELO getCcDeploymentCellPickerInfo(int param1) throws CPException {
        try {
            CcDeploymentCellPickerInfoELO e = null;
            if (this.isRemoteConnection()) {
                e = this.getRemote().getCcDeploymentCellPickerInfo(param1);
            } else {
                e = this.getLocal().getCcDeploymentCellPickerInfo(param1);
            }

            return e;
        } catch (Exception var3) {
            throw new CPException("error in getCcDeploymentCellPickerInfo", var3);
        }
    }

    public CcDeploymentXMLFormTypeELO getCcDeploymentXMLFormType(int param1) throws CPException {
        try {
            CcDeploymentXMLFormTypeELO e = null;
            if (this.isRemoteConnection()) {
                e = this.getRemote().getCcDeploymentXMLFormType(param1);
            } else {
                e = this.getLocal().getCcDeploymentXMLFormType(param1);
            }

            return e;
        } catch (Exception var3) {
            throw new CPException("error in getCcDeploymentXMLFormType", var3);
        }
    }

    public AllCellCalcsELO getAllCellCalcs() throws CPException {
        try {
            AllCellCalcsELO e = null;
            if (this.isRemoteConnection()) {
                e = this.getRemote().getAllCellCalcs();
            } else {
                e = this.getLocal().getAllCellCalcs();
            }

            return e;
        } catch (Exception var2) {
            throw new CPException("error in getAllCellCalcs", var2);
        }
    }

    public CellCalcIntegrityELO getCellCalcIntegrity() throws CPException {
        try {
            CellCalcIntegrityELO e = null;
            if (this.isRemoteConnection()) {
                e = this.getRemote().getCellCalcIntegrity();
            } else {
                e = this.getLocal().getCellCalcIntegrity();
            }

            return e;
        } catch (Exception var2) {
            throw new CPException("error in getCellCalcIntegrity", var2);
        }
    }

    public AllCellCalcAssocsELO getAllCellCalcAssocs() throws CPException {
        try {
            AllCellCalcAssocsELO e = null;
            if (this.isRemoteConnection()) {
                e = this.getRemote().getAllCellCalcAssocs();
            } else {
                e = this.getLocal().getAllCellCalcAssocs();
            }

            return e;
        } catch (Exception var2) {
            throw new CPException("error in getAllCellCalcAssocs", var2);
        }
    }

    public AllChangeMgmtsELO getAllChangeMgmts() throws CPException {
        try {
            AllChangeMgmtsELO e = null;
            if (this.isRemoteConnection()) {
                e = this.getRemote().getAllChangeMgmts();
            } else {
                e = this.getLocal().getAllChangeMgmts();
            }

            return e;
        } catch (Exception var2) {
            throw new CPException("error in getAllChangeMgmts", var2);
        }
    }

    public AllChangeMgmtsForModelELO getAllChangeMgmtsForModel(int param1) throws CPException {
        try {
            AllChangeMgmtsForModelELO e = null;
            if (this.isRemoteConnection()) {
                e = this.getRemote().getAllChangeMgmtsForModel(param1);
            } else {
                e = this.getLocal().getAllChangeMgmtsForModel(param1);
            }

            return e;
        } catch (Exception var3) {
            throw new CPException("error in getAllChangeMgmtsForModel", var3);
        }
    }

    public AllChangeMgmtsForModelWithXMLELO getAllChangeMgmtsForModelWithXML(int param1) throws CPException {
        try {
            AllChangeMgmtsForModelWithXMLELO e = null;
            if (this.isRemoteConnection()) {
                e = this.getRemote().getAllChangeMgmtsForModelWithXML(param1);
            } else {
                e = this.getLocal().getAllChangeMgmtsForModelWithXML(param1);
            }

            return e;
        } catch (Exception var3) {
            throw new CPException("error in getAllChangeMgmtsForModelWithXML", var3);
        }
    }

    public AllImpExpHdrsELO getAllImpExpHdrs() throws CPException {
        try {
            AllImpExpHdrsELO e = null;
            if (this.isRemoteConnection()) {
                e = this.getRemote().getAllImpExpHdrs();
            } else {
                e = this.getLocal().getAllImpExpHdrs();
            }

            return e;
        } catch (Exception var2) {
            throw new CPException("error in getAllImpExpHdrs", var2);
        }
    }

    public AllReportsELO getAllReports() throws CPException {
        try {
            AllReportsELO e = null;
            if (this.isRemoteConnection()) {
                e = this.getRemote().getAllReports();
            } else {
                e = this.getLocal().getAllReports();
            }

            return e;
        } catch (Exception var2) {
            throw new CPException("error in getAllReports", var2);
        }
    }

    public AllReportsForUserELO getAllReportsForUser(int param1) throws CPException {
        try {
            AllReportsForUserELO e = null;
            if (this.isRemoteConnection()) {
                e = this.getRemote().getAllReportsForUser(param1);
            } else {
                e = this.getLocal().getAllReportsForUser(param1);
            }

            return e;
        } catch (Exception var3) {
            throw new CPException("error in getAllReportsForUser", var3);
        }
    }

    public AllReportsForAdminELO getAllReportsForAdmin() throws CPException {
        try {
            AllReportsForAdminELO e = null;
            if (this.isRemoteConnection()) {
                e = this.getRemote().getAllReportsForAdmin();
            } else {
                e = this.getLocal().getAllReportsForAdmin();
            }

            return e;
        } catch (Exception var2) {
            throw new CPException("error in getAllReportsForAdmin", var2);
        }
    }

    public WebReportDetailsELO getWebReportDetails(int param1) throws CPException {
        try {
            WebReportDetailsELO e = null;
            if (this.isRemoteConnection()) {
                e = this.getRemote().getWebReportDetails(param1);
            } else {
                e = this.getLocal().getWebReportDetails(param1);
            }

            return e;
        } catch (Exception var3) {
            throw new CPException("error in getWebReportDetails", var3);
        }
    }

    public AllVirementCategorysELO getAllVirementCategorys() throws CPException {
        try {
            AllVirementCategorysELO e = null;
            if (this.isRemoteConnection()) {
                e = this.getRemote().getAllVirementCategorys();
            } else {
                e = this.getLocal().getAllVirementCategorys();
            }

            return e;
        } catch (Exception var2) {
            throw new CPException("error in getAllVirementCategorys", var2);
        }
    }

    public LocationsForCategoryELO getLocationsForCategory(int param1) throws CPException {
        try {
            LocationsForCategoryELO e = null;
            if (this.isRemoteConnection()) {
                e = this.getRemote().getLocationsForCategory(param1);
            } else {
                e = this.getLocal().getLocationsForCategory(param1);
            }

            return e;
        } catch (Exception var3) {
            throw new CPException("error in getLocationsForCategory", var3);
        }
    }

    public AccountsForCategoryELO getAccountsForCategory(int param1) throws CPException {
        try {
            AccountsForCategoryELO e = null;
            if (this.isRemoteConnection()) {
                e = this.getRemote().getAccountsForCategory(param1);
            } else {
                e = this.getLocal().getAccountsForCategory(param1);
            }

            return e;
        } catch (Exception var3) {
            throw new CPException("error in getAccountsForCategory", var3);
        }
    }

    public AllBudgetLimitsELO getAllBudgetLimits() throws CPException {
        try {
            AllBudgetLimitsELO e = null;
            if (this.isRemoteConnection()) {
                e = this.getRemote().getAllBudgetLimits();
            } else {
                e = this.getLocal().getAllBudgetLimits();
            }

            return e;
        } catch (Exception var2) {
            throw new CPException("error in getAllBudgetLimits", var2);
        }
    }

    public AllPerfTestsELO getAllPerfTests() throws CPException {
        try {
            AllPerfTestsELO e = null;
            if (this.isRemoteConnection()) {
                e = this.getRemote().getAllPerfTests();
            } else {
                e = this.getLocal().getAllPerfTests();
            }

            return e;
        } catch (Exception var2) {
            throw new CPException("error in getAllPerfTests", var2);
        }
    }

    public AllPerfTestRunsELO getAllPerfTestRuns() throws CPException {
        try {
            AllPerfTestRunsELO e = null;
            if (this.isRemoteConnection()) {
                e = this.getRemote().getAllPerfTestRuns();
            } else {
                e = this.getLocal().getAllPerfTestRuns();
            }

            return e;
        } catch (Exception var2) {
            throw new CPException("error in getAllPerfTestRuns", var2);
        }
    }

    public AllPerfTestRunResultsELO getAllPerfTestRunResults() throws CPException {
        try {
            AllPerfTestRunResultsELO e = null;
            if (this.isRemoteConnection()) {
                e = this.getRemote().getAllPerfTestRunResults();
            } else {
                e = this.getLocal().getAllPerfTestRunResults();
            }

            return e;
        } catch (Exception var2) {
            throw new CPException("error in getAllPerfTestRunResults", var2);
        }
    }

    public AllMessagesELO getAllMessages() throws CPException {
        try {
            AllMessagesELO e = null;
            if (this.isRemoteConnection()) {
                e = this.getRemote().getAllMessages();
            } else {
                e = this.getLocal().getAllMessages();
            }

            return e;
        } catch (Exception var2) {
            throw new CPException("error in getAllMessages", var2);
        }
    }

    public InBoxForUserELO getInBoxForUser(String param1) throws CPException {
        try {
            InBoxForUserELO e = null;
            if (this.isRemoteConnection()) {
                e = this.getRemote().getInBoxForUser(param1);
            } else {
                e = this.getLocal().getInBoxForUser(param1);
            }

            return e;
        } catch (Exception var3) {
            throw new CPException("error in getInBoxForUser", var3);
        }
    }

    public UnreadInBoxForUserELO getUnreadInBoxForUser(String param1) throws CPException {
        try {
            UnreadInBoxForUserELO e = null;
            if (this.isRemoteConnection()) {
                e = this.getRemote().getUnreadInBoxForUser(param1);
            } else {
                e = this.getLocal().getUnreadInBoxForUser(param1);
            }

            return e;
        } catch (Exception var3) {
            throw new CPException("error in getUnreadInBoxForUser", var3);
        }
    }

    public SentItemsForUserELO getSentItemsForUser(String param1) throws CPException {
        try {
            SentItemsForUserELO e = null;
            if (this.isRemoteConnection()) {
                e = this.getRemote().getSentItemsForUser(param1);
            } else {
                e = this.getLocal().getSentItemsForUser(param1);
            }

            return e;
        } catch (Exception var3) {
            throw new CPException("error in getSentItemsForUser", var3);
        }
    }

    public MessageForIdELO getMessageForId(long param1, String param2) throws CPException {
        try {
            MessageForIdELO e = null;
            if (this.isRemoteConnection()) {
                e = this.getRemote().getMessageForId(param1, param2);
            } else {
                e = this.getLocal().getMessageForId(param1, param2);
            }

            return e;
        } catch (Exception var5) {
            throw new CPException("error in getMessageForId", var5);
        }
    }

    public MessageForIdSentItemELO getMessageForIdSentItem(long param1, String param2) throws CPException {
        try {
            MessageForIdSentItemELO e = null;
            if (this.isRemoteConnection()) {
                e = this.getRemote().getMessageForIdSentItem(param1, param2);
            } else {
                e = this.getLocal().getMessageForIdSentItem(param1, param2);
            }

            return e;
        } catch (Exception var5) {
            throw new CPException("error in getMessageForIdSentItem", var5);
        }
    }

    public MessageCountELO getMessageCount(long param1) throws CPException {
        try {
            MessageCountELO e = null;
            if (this.isRemoteConnection()) {
                e = this.getRemote().getMessageCount(param1);
            } else {
                e = this.getLocal().getMessageCount(param1);
            }

            return e;
        } catch (Exception var4) {
            throw new CPException("error in getMessageCount", var4);
        }
    }

    public AttatchmentForMessageELO getAttatchmentForMessage(long param1) throws CPException {
        try {
            AttatchmentForMessageELO e = null;
            if (this.isRemoteConnection()) {
                e = this.getRemote().getAttatchmentForMessage(param1);
            } else {
                e = this.getLocal().getAttatchmentForMessage(param1);
            }

            return e;
        } catch (Exception var4) {
            throw new CPException("error in getAttatchmentForMessage", var4);
        }
    }

    public MessageFromUserELO getMessageFromUser(long param1) throws CPException {
        try {
            MessageFromUserELO e = null;
            if (this.isRemoteConnection()) {
                e = this.getRemote().getMessageFromUser(param1);
            } else {
                e = this.getLocal().getMessageFromUser(param1);
            }

            return e;
        } catch (Exception var4) {
            throw new CPException("error in getMessageFromUser", var4);
        }
    }

    public MessageToUserELO getMessageToUser(long param1) throws CPException {
        try {
            MessageToUserELO e = null;
            if (this.isRemoteConnection()) {
                e = this.getRemote().getMessageToUser(param1);
            } else {
                e = this.getLocal().getMessageToUser(param1);
            }

            return e;
        } catch (Exception var4) {
            throw new CPException("error in getMessageToUser", var4);
        }
    }

    public AllMessagesToUserELO getAllMessagesToUser(long param1) throws CPException {
        try {
            AllMessagesToUserELO e = null;
            if (this.isRemoteConnection()) {
                e = this.getRemote().getAllMessagesToUser(param1);
            } else {
                e = this.getLocal().getAllMessagesToUser(param1);
            }

            return e;
        } catch (Exception var4) {
            throw new CPException("error in getAllMessagesToUser", var4);
        }
    }

    public AllRechargesELO getAllRecharges() throws CPException {
        try {
            AllRechargesELO e = null;
            if (this.isRemoteConnection()) {
                e = this.getRemote().getAllRecharges();
            } else {
                e = this.getLocal().getAllRecharges();
            }

            return e;
        } catch (Exception var2) {
            throw new CPException("error in getAllRecharges", var2);
        }
    }

    public AllRechargesWithModelELO getAllRechargesWithModel(int param1) throws CPException {
        try {
            AllRechargesWithModelELO e = null;
            if (this.isRemoteConnection()) {
                e = this.getRemote().getAllRechargesWithModel(param1);
            } else {
                e = this.getLocal().getAllRechargesWithModel(param1);
            }

            return e;
        } catch (Exception var3) {
            throw new CPException("error in getAllRechargesWithModel", var3);
        }
    }

    public SingleRechargeELO getSingleRecharge(int param1) throws CPException {
        try {
            SingleRechargeELO e = null;
            if (this.isRemoteConnection()) {
                e = this.getRemote().getSingleRecharge(param1);
            } else {
                e = this.getLocal().getSingleRecharge(param1);
            }

            return e;
        } catch (Exception var3) {
            throw new CPException("error in getSingleRecharge", var3);
        }
    }

    public AllRechargeGroupsELO getAllRechargeGroups() throws CPException {
        try {
            AllRechargeGroupsELO e = null;
            if (this.isRemoteConnection()) {
                e = this.getRemote().getAllRechargeGroups();
            } else {
                e = this.getLocal().getAllRechargeGroups();
            }

            return e;
        } catch (Exception var2) {
            throw new CPException("error in getAllRechargeGroups", var2);
        }
    }

    public ActivitiesForCycleandElementELO getActivitiesForCycleandElement(int param1, Integer param2, int param3) throws CPException {
        try {
            ActivitiesForCycleandElementELO e = null;
            if (this.isRemoteConnection()) {
                e = this.getRemote().getActivitiesForCycleandElement(param1, param2, param3);
            } else {
                e = this.getLocal().getActivitiesForCycleandElement(param1, param2, param3);
            }

            return e;
        } catch (Exception var5) {
            throw new CPException("error in getActivitiesForCycleandElement", var5);
        }
    }

    public ActivityDetailsELO getActivityDetails(int param1) throws CPException {
        try {
            ActivityDetailsELO e = null;
            if (this.isRemoteConnection()) {
                e = this.getRemote().getActivityDetails(param1);
            } else {
                e = this.getLocal().getActivityDetails(param1);
            }

            return e;
        } catch (Exception var3) {
            throw new CPException("error in getActivityDetails", var3);
        }
    }

    public ActivityFullDetailsELO getActivityFullDetails(int param1) throws CPException {
        try {
            ActivityFullDetailsELO e = null;
            if (this.isRemoteConnection()) {
                e = this.getRemote().getActivityFullDetails(param1);
            } else {
                e = this.getLocal().getActivityFullDetails(param1);
            }

            return e;
        } catch (Exception var3) {
            throw new CPException("error in getActivityFullDetails", var3);
        }
    }

    public AllVirementRequestsELO getAllVirementRequests() throws CPException {
        try {
            AllVirementRequestsELO e = null;
            if (this.isRemoteConnection()) {
                e = this.getRemote().getAllVirementRequests();
            } else {
                e = this.getLocal().getAllVirementRequests();
            }

            return e;
        } catch (Exception var2) {
            throw new CPException("error in getAllVirementRequests", var2);
        }
    }

    public AllVirementRequestGroupsELO getAllVirementRequestGroups() throws CPException {
        try {
            AllVirementRequestGroupsELO e = null;
            if (this.isRemoteConnection()) {
                e = this.getRemote().getAllVirementRequestGroups();
            } else {
                e = this.getLocal().getAllVirementRequestGroups();
            }

            return e;
        } catch (Exception var2) {
            throw new CPException("error in getAllVirementRequestGroups", var2);
        }
    }

    public AllReportTypesELO getAllReportTypes() throws CPException {
        try {
            AllReportTypesELO e = null;
            if (this.isRemoteConnection()) {
                e = this.getRemote().getAllReportTypes();
            } else {
                e = this.getLocal().getAllReportTypes();
            }

            return e;
        } catch (Exception var2) {
            throw new CPException("error in getAllReportTypes", var2);
        }
    }

    public AllReportTypeParamsELO getAllReportTypeParams() throws CPException {
        try {
            AllReportTypeParamsELO e = null;
            if (this.isRemoteConnection()) {
                e = this.getRemote().getAllReportTypeParams();
            } else {
                e = this.getLocal().getAllReportTypeParams();
            }

            return e;
        } catch (Exception var2) {
            throw new CPException("error in getAllReportTypeParams", var2);
        }
    }

    public AllReportTypeParamsforTypeELO getAllReportTypeParamsforType(int param1) throws CPException {
        try {
            AllReportTypeParamsforTypeELO e = null;
            if (this.isRemoteConnection()) {
                e = this.getRemote().getAllReportTypeParamsforType(param1);
            } else {
                e = this.getLocal().getAllReportTypeParamsforType(param1);
            }

            return e;
        } catch (Exception var3) {
            throw new CPException("error in getAllReportTypeParamsforType", var3);
        }
    }

    public AllReportDefinitionsELO getAllReportDefinitions() throws CPException {
        try {
            AllReportDefinitionsELO e = null;
            if (this.isRemoteConnection()) {
                e = this.getRemote().getAllReportDefinitions();
            } else {
                e = this.getLocal().getAllReportDefinitions();
            }

            return e;
        } catch (Exception var2) {
            throw new CPException("error in getAllReportDefinitions", var2);
        }
    }

    public AllReportDefinitionsELO getAllReportDefinitionsForLoggedUser() throws CPException {
        try {
            AllReportDefinitionsELO e = null;
            if (this.isRemoteConnection()) {
                e = this.getRemote().getAllReportDefinitionsForLoggedUser(getConnection().getUserContext().getUserId());
            } else {
                e = this.getLocal().getAllReportDefinitionsForLoggedUser(getConnection().getUserContext().getUserId());
            }

            return e;
        } catch (Exception var2) {
            throw new CPException("error in getAllReportDefinitions", var2);
        }
    }

    public AllPublicReportByTypeELO getAllPublicReportByType(int param1) throws CPException {
        try {
            AllPublicReportByTypeELO e = null;
            if (this.isRemoteConnection()) {
                e = this.getRemote().getAllPublicReportByType(param1);
            } else {
                e = this.getLocal().getAllPublicReportByType(param1);
            }

            return e;
        } catch (Exception var3) {
            throw new CPException("error in getAllPublicReportByType", var3);
        }
    }

    public ReportDefinitionForVisIdELO getReportDefinitionForVisId(String param1) throws CPException {
        try {
            ReportDefinitionForVisIdELO e = null;
            if (this.isRemoteConnection()) {
                e = this.getRemote().getReportDefinitionForVisId(param1);
            } else {
                e = this.getLocal().getReportDefinitionForVisId(param1);
            }

            return e;
        } catch (Exception var3) {
            throw new CPException("error in getReportDefinitionForVisId", var3);
        }
    }

    public AllReportDefFormcByReportTemplateIdELO getAllReportDefFormcByReportTemplateId(int param1) throws CPException {
        try {
            AllReportDefFormcByReportTemplateIdELO e = null;
            if (this.isRemoteConnection()) {
                e = this.getRemote().getAllReportDefFormcByReportTemplateId(param1);
            } else {
                e = this.getLocal().getAllReportDefFormcByReportTemplateId(param1);
            }

            return e;
        } catch (Exception var3) {
            throw new CPException("error in getAllReportDefFormcByReportTemplateId", var3);
        }
    }

    public AllReportDefFormcByModelIdELO getAllReportDefFormcByModelId(int param1) throws CPException {
        try {
            AllReportDefFormcByModelIdELO e = null;
            if (this.isRemoteConnection()) {
                e = this.getRemote().getAllReportDefFormcByModelId(param1);
            } else {
                e = this.getLocal().getAllReportDefFormcByModelId(param1);
            }

            return e;
        } catch (Exception var3) {
            throw new CPException("error in getAllReportDefFormcByModelId", var3);
        }
    }

    public CheckFormIsUsedELO getCheckFormIsUsed(int param1) throws CPException {
        try {
            CheckFormIsUsedELO e = null;
            if (this.isRemoteConnection()) {
                e = this.getRemote().getCheckFormIsUsed(param1);
            } else {
                e = this.getLocal().getCheckFormIsUsed(param1);
            }

            return e;
        } catch (Exception var3) {
            throw new CPException("error in getCheckFormIsUsed", var3);
        }
    }

    public AllReportDefMappedExcelcByReportTemplateIdELO getAllReportDefMappedExcelcByReportTemplateId(int param1) throws CPException {
        try {
            AllReportDefMappedExcelcByReportTemplateIdELO e = null;
            if (this.isRemoteConnection()) {
                e = this.getRemote().getAllReportDefMappedExcelcByReportTemplateId(param1);
            } else {
                e = this.getLocal().getAllReportDefMappedExcelcByReportTemplateId(param1);
            }

            return e;
        } catch (Exception var3) {
            throw new CPException("error in getAllReportDefMappedExcelcByReportTemplateId", var3);
        }
    }

    public AllReportDefMappedExcelcByModelIdELO getAllReportDefMappedExcelcByModelId(int param1) throws CPException {
        try {
            AllReportDefMappedExcelcByModelIdELO e = null;
            if (this.isRemoteConnection()) {
                e = this.getRemote().getAllReportDefMappedExcelcByModelId(param1);
            } else {
                e = this.getLocal().getAllReportDefMappedExcelcByModelId(param1);
            }

            return e;
        } catch (Exception var3) {
            throw new CPException("error in getAllReportDefMappedExcelcByModelId", var3);
        }
    }

    public AllReportDefCalcByCCDeploymentIdELO getAllReportDefCalcByCCDeploymentId(int param1) throws CPException {
        try {
            AllReportDefCalcByCCDeploymentIdELO e = null;
            if (this.isRemoteConnection()) {
                e = this.getRemote().getAllReportDefCalcByCCDeploymentId(param1);
            } else {
                e = this.getLocal().getAllReportDefCalcByCCDeploymentId(param1);
            }

            return e;
        } catch (Exception var3) {
            throw new CPException("error in getAllReportDefCalcByCCDeploymentId", var3);
        }
    }

    public AllReportDefCalcByReportTemplateIdELO getAllReportDefCalcByReportTemplateId(int param1) throws CPException {
        try {
            AllReportDefCalcByReportTemplateIdELO e = null;
            if (this.isRemoteConnection()) {
                e = this.getRemote().getAllReportDefCalcByReportTemplateId(param1);
            } else {
                e = this.getLocal().getAllReportDefCalcByReportTemplateId(param1);
            }

            return e;
        } catch (Exception var3) {
            throw new CPException("error in getAllReportDefCalcByReportTemplateId", var3);
        }
    }

    public AllReportDefCalcByModelIdELO getAllReportDefCalcByModelId(int param1) throws CPException {
        try {
            AllReportDefCalcByModelIdELO e = null;
            if (this.isRemoteConnection()) {
                e = this.getRemote().getAllReportDefCalcByModelId(param1);
            } else {
                e = this.getLocal().getAllReportDefCalcByModelId(param1);
            }

            return e;
        } catch (Exception var3) {
            throw new CPException("error in getAllReportDefCalcByModelId", var3);
        }
    }

    public AllReportDefSummaryCalcByCCDeploymentIdELO getAllReportDefSummaryCalcByCCDeploymentId(int param1) throws CPException {
        try {
            AllReportDefSummaryCalcByCCDeploymentIdELO e = null;
            if (this.isRemoteConnection()) {
                e = this.getRemote().getAllReportDefSummaryCalcByCCDeploymentId(param1);
            } else {
                e = this.getLocal().getAllReportDefSummaryCalcByCCDeploymentId(param1);
            }

            return e;
        } catch (Exception var3) {
            throw new CPException("error in getAllReportDefSummaryCalcByCCDeploymentId", var3);
        }
    }

    public AllReportDefSummaryCalcByReportTemplateIdELO getAllReportDefSummaryCalcByReportTemplateId(int param1) throws CPException {
        try {
            AllReportDefSummaryCalcByReportTemplateIdELO e = null;
            if (this.isRemoteConnection()) {
                e = this.getRemote().getAllReportDefSummaryCalcByReportTemplateId(param1);
            } else {
                e = this.getLocal().getAllReportDefSummaryCalcByReportTemplateId(param1);
            }

            return e;
        } catch (Exception var3) {
            throw new CPException("error in getAllReportDefSummaryCalcByReportTemplateId", var3);
        }
    }

    public AllReportDefSummaryCalcByModelIdELO getAllReportDefSummaryCalcByModelId(int param1) throws CPException {
        try {
            AllReportDefSummaryCalcByModelIdELO e = null;
            if (this.isRemoteConnection()) {
                e = this.getRemote().getAllReportDefSummaryCalcByModelId(param1);
            } else {
                e = this.getLocal().getAllReportDefSummaryCalcByModelId(param1);
            }

            return e;
        } catch (Exception var3) {
            throw new CPException("error in getAllReportDefSummaryCalcByModelId", var3);
        }
    }

    public AllReportTemplatesELO getAllReportTemplates() throws CPException {
        try {
            AllReportTemplatesELO e = null;
            if (this.isRemoteConnection()) {
                e = this.getRemote().getAllReportTemplates();
            } else {
                e = this.getLocal().getAllReportTemplates();
            }

            return e;
        } catch (Exception var2) {
            throw new CPException("error in getAllReportTemplates", var2);
        }
    }

    public AllReportMappingTemplatesELO getAllReportMappingTemplates() throws CPException {
        try {
            AllReportMappingTemplatesELO e = null;
            if (this.isRemoteConnection()) {
                e = this.getRemote().getAllReportMappingTemplates();
            } else {
                e = this.getLocal().getAllReportMappingTemplates();
            }

            return e;
        } catch (Exception var2) {
            throw new CPException("error in getAllReportMappingTemplates", var2);
        }
    }

    public AllExternalDestinationsELO getAllExternalDestinations() throws CPException {
        try {
            AllExternalDestinationsELO e = null;
            if (this.isRemoteConnection()) {
                e = this.getRemote().getAllExternalDestinations();
            } else {
                e = this.getLocal().getAllExternalDestinations();
            }

            return e;
        } catch (Exception var2) {
            throw new CPException("error in getAllExternalDestinations", var2);
        }
    }

    public AllExternalDestinationDetailsELO getAllExternalDestinationDetails() throws CPException {
        try {
            AllExternalDestinationDetailsELO e = null;
            if (this.isRemoteConnection()) {
                e = this.getRemote().getAllExternalDestinationDetails();
            } else {
                e = this.getLocal().getAllExternalDestinationDetails();
            }

            return e;
        } catch (Exception var2) {
            throw new CPException("error in getAllExternalDestinationDetails", var2);
        }
    }

    public AllUsersForExternalDestinationIdELO getAllUsersForExternalDestinationId(int param1) throws CPException {
        try {
            AllUsersForExternalDestinationIdELO e = null;
            if (this.isRemoteConnection()) {
                e = this.getRemote().getAllUsersForExternalDestinationId(param1);
            } else {
                e = this.getLocal().getAllUsersForExternalDestinationId(param1);
            }

            return e;
        } catch (Exception var3) {
            throw new CPException("error in getAllUsersForExternalDestinationId", var3);
        }
    }

    public AllInternalDestinationsELO getAllInternalDestinations() throws CPException {
        try {
            AllInternalDestinationsELO e = null;
            if (this.isRemoteConnection()) {
                e = this.getRemote().getAllInternalDestinations();
            } else {
                e = this.getLocal().getAllInternalDestinations();
            }

            return e;
        } catch (Exception var2) {
            throw new CPException("error in getAllInternalDestinations", var2);
        }
    }

    public AllInternalDestinationDetailsELO getAllInternalDestinationDetails() throws CPException {
        try {
            AllInternalDestinationDetailsELO e = null;
            if (this.isRemoteConnection()) {
                e = this.getRemote().getAllInternalDestinationDetails();
            } else {
                e = this.getLocal().getAllInternalDestinationDetails();
            }

            return e;
        } catch (Exception var2) {
            throw new CPException("error in getAllInternalDestinationDetails", var2);
        }
    }

    public AllUsersForInternalDestinationIdELO getAllUsersForInternalDestinationId(int param1) throws CPException {
        try {
            AllUsersForInternalDestinationIdELO e = null;
            if (this.isRemoteConnection()) {
                e = this.getRemote().getAllUsersForInternalDestinationId(param1);
            } else {
                e = this.getLocal().getAllUsersForInternalDestinationId(param1);
            }

            return e;
        } catch (Exception var3) {
            throw new CPException("error in getAllUsersForInternalDestinationId", var3);
        }
    }

    public AllInternalDestinationUsersELO getAllInternalDestinationUsers() throws CPException {
        try {
            AllInternalDestinationUsersELO e = null;
            if (this.isRemoteConnection()) {
                e = this.getRemote().getAllInternalDestinationUsers();
            } else {
                e = this.getLocal().getAllInternalDestinationUsers();
            }

            return e;
        } catch (Exception var2) {
            throw new CPException("error in getAllInternalDestinationUsers", var2);
        }
    }

    public CheckInternalDestinationUsersELO getCheckInternalDestinationUsers(int param1) throws CPException {
        try {
            CheckInternalDestinationUsersELO e = null;
            if (this.isRemoteConnection()) {
                e = this.getRemote().getCheckInternalDestinationUsers(param1);
            } else {
                e = this.getLocal().getCheckInternalDestinationUsers(param1);
            }

            return e;
        } catch (Exception var3) {
            throw new CPException("error in getCheckInternalDestinationUsers", var3);
        }
    }

    public AllDistributionsELO getAllDistributions() throws CPException {
        try {
            AllDistributionsELO e = null;
            if (this.isRemoteConnection()) {
                e = this.getRemote().getAllDistributions();
            } else {
                e = this.getLocal().getAllDistributions();
            }

            return e;
        } catch (Exception var2) {
            throw new CPException("error in getAllDistributions", var2);
        }
    }

    public DistributionForVisIdELO getDistributionForVisId(String param1) throws CPException {
        try {
            DistributionForVisIdELO e = null;
            if (this.isRemoteConnection()) {
                e = this.getRemote().getDistributionForVisId(param1);
            } else {
                e = this.getLocal().getDistributionForVisId(param1);
            }

            return e;
        } catch (Exception var3) {
            throw new CPException("error in getDistributionForVisId", var3);
        }
    }

    public DistributionDetailsForVisIdELO getDistributionDetailsForVisId(String param1) throws CPException {
        try {
            DistributionDetailsForVisIdELO e = null;
            if (this.isRemoteConnection()) {
                e = this.getRemote().getDistributionDetailsForVisId(param1);
            } else {
                e = this.getLocal().getDistributionDetailsForVisId(param1);
            }

            return e;
        } catch (Exception var3) {
            throw new CPException("error in getDistributionDetailsForVisId", var3);
        }
    }

    public CheckInternalDestinationELO getCheckInternalDestination(int param1) throws CPException {
        try {
            CheckInternalDestinationELO e = null;
            if (this.isRemoteConnection()) {
                e = this.getRemote().getCheckInternalDestination(param1);
            } else {
                e = this.getLocal().getCheckInternalDestination(param1);
            }

            return e;
        } catch (Exception var3) {
            throw new CPException("error in getCheckInternalDestination", var3);
        }
    }

    public CheckExternalDestinationELO getCheckExternalDestination(int param1) throws CPException {
        try {
            CheckExternalDestinationELO e = null;
            if (this.isRemoteConnection()) {
                e = this.getRemote().getCheckExternalDestination(param1);
            } else {
                e = this.getLocal().getCheckExternalDestination(param1);
            }

            return e;
        } catch (Exception var3) {
            throw new CPException("error in getCheckExternalDestination", var3);
        }
    }

    public AllReportPacksELO getAllReportPacks() throws CPException {
        try {
            AllReportPacksELO e = null;
            if (this.isRemoteConnection()) {
                e = this.getRemote().getAllReportPacks();
            } else {
                e = this.getLocal().getAllReportPacks();
            }

            return e;
        } catch (Exception var2) {
            throw new CPException("error in getAllReportPacks", var2);
        }
    }

    public ReportDefDistListELO getReportDefDistList(String param1) throws CPException {
        try {
            ReportDefDistListELO e = null;
            if (this.isRemoteConnection()) {
                e = this.getRemote().getReportDefDistList(param1);
            } else {
                e = this.getLocal().getReportDefDistList(param1);
            }

            return e;
        } catch (Exception var3) {
            throw new CPException("error in getReportDefDistList", var3);
        }
    }

    public CheckReportDefELO getCheckReportDef(int param1) throws CPException {
        try {
            CheckReportDefELO e = null;
            if (this.isRemoteConnection()) {
                e = this.getRemote().getCheckReportDef(param1);
            } else {
                e = this.getLocal().getCheckReportDef(param1);
            }

            return e;
        } catch (Exception var3) {
            throw new CPException("error in getCheckReportDef", var3);
        }
    }

    public CheckReportDistributionELO getCheckReportDistribution(int param1) throws CPException {
        try {
            CheckReportDistributionELO e = null;
            if (this.isRemoteConnection()) {
                e = this.getRemote().getCheckReportDistribution(param1);
            } else {
                e = this.getLocal().getCheckReportDistribution(param1);
            }

            return e;
        } catch (Exception var3) {
            throw new CPException("error in getCheckReportDistribution", var3);
        }
    }

    public AllWeightingProfilesELO getAllWeightingProfiles() throws CPException {
        try {
            AllWeightingProfilesELO e = null;
            if (this.isRemoteConnection()) {
                e = this.getRemote().getAllWeightingProfiles();
            } else {
                e = this.getLocal().getAllWeightingProfiles();
            }

            return e;
        } catch (Exception var2) {
            throw new CPException("error in getAllWeightingProfiles", var2);
        }
    }

    public AllWeightingProfilesELO getAllWeightingProfilesForLoggedUser() throws CPException {
        try {
            AllWeightingProfilesELO e = null;
            if (this.isRemoteConnection()) {
                e = this.getRemote().getAllWeightingProfilesForLoggedUser(getConnection().getUserContext().getUserId());
            } else {
                e = this.getLocal().getAllWeightingProfilesForLoggedUser(getConnection().getUserContext().getUserId());
            }

            return e;
        } catch (Exception var2) {
            throw new CPException("error in getAllWeightingProfilesForLoggedUser", var2);
        }
    }

    public AllWeightingDeploymentsELO getAllWeightingDeployments() throws CPException {
        try {
            AllWeightingDeploymentsELO e = null;
            if (this.isRemoteConnection()) {
                e = this.getRemote().getAllWeightingDeployments();
            } else {
                e = this.getLocal().getAllWeightingDeployments();
            }

            return e;
        } catch (Exception var2) {
            throw new CPException("error in getAllWeightingDeployments", var2);
        }
    }

    public EntityList getAllWeightingDeploymentsForLoggedUser() throws CPException {
        try {
            EntityList e = null;
            if (this.isRemoteConnection()) {
                e = this.getRemote().getAllWeightingDeploymentsForLoggedUser(getConnection().getUserContext().getUserId());
            } else {
                e = this.getLocal().getAllWeightingDeploymentsForLoggedUser(getConnection().getUserContext().getUserId());
            }

            return e;
        } catch (Exception var2) {
            throw new CPException("error in getAllWeightingDeployments", var2);
        }
    }

    public AllTidyTasksELO getAllTidyTasks() throws CPException {
        try {
            AllTidyTasksELO e = null;
            if (this.isRemoteConnection()) {
                e = this.getRemote().getAllTidyTasks();
            } else {
                e = this.getLocal().getAllTidyTasks();
            }

            return e;
        } catch (Exception var2) {
            throw new CPException("error in getAllTidyTasks", var2);
        }
    }

    public AllImportTasksELO getAllImportTasks() throws CPException {
        try {
            AllImportTasksELO e = null;
            if (this.isRemoteConnection()) {
                e = this.getRemote().getAllImportTasks();
            } else {
                e = this.getLocal().getAllImportTasks();
            }

            return e;
        } catch (Exception var2) {
            throw new CPException("error in getAllImportTasks", var2);
        }
    }

    public AllRecalculateBatchTasksELO getAllRecalculateBatchTasks() throws CPException {
        try {
            AllRecalculateBatchTasksELO e = null;
            if (this.isRemoteConnection()) {
                e = this.getRemote().getAllRecalculateBatchTasks();
            } else {
                e = this.getLocal().getAllRecalculateBatchTasks();
            }

            return e;
        } catch (Exception var2) {
            throw new CPException("error in getAllImportTasks", var2);
        }
    }

    public OrderedChildrenELO getOrderedChildren(int param1) throws CPException {
        try {
            OrderedChildrenELO e = null;
            if (this.isRemoteConnection()) {
                e = this.getRemote().getOrderedChildren(param1);
            } else {
                e = this.getLocal().getOrderedChildren(param1);
            }

            return e;
        } catch (Exception var3) {
            throw new CPException("error in getOrderedChildren", var3);
        }
    }

    public AllMappedModelsELO getAllMappedModels() throws CPException {
        try {
            AllMappedModelsELO e = null;
            if (this.isRemoteConnection()) {
                e = this.getRemote().getAllMappedModels();
            } else {
                e = this.getLocal().getAllMappedModels();
            }

            return e;
        } catch (Exception var2) {
            throw new CPException("error in getAllMappedModels", var2);
        }
    }

    public AllMappedModelsELO getAllMappedModelsForLoggedUser() throws CPException {
        try {
            AllMappedModelsELO e = null;
            if (this.isRemoteConnection()) {
                e = this.getRemote().getAllMappedModelsForLoggedUser(getConnection().getUserContext().getUserId());
            } else {
                e = this.getLocal().getAllMappedModelsForLoggedUser(getConnection().getUserContext().getUserId());
            }

            return e;
        } catch (Exception var2) {
            throw new CPException("error in getAllMappedModelsForLoggedUser", var2);
        }
    }

    public AllGlobalMappedModels2ELO getAllGlobalMappedModels2() throws CPException {
        try {
            AllGlobalMappedModels2ELO elo = null;
            if (this.isRemoteConnection()) {
                elo = this.getRemote().getAllGlobalMappedModels2();
            } else {
                elo = this.getLocal().getAllGlobalMappedModels2();
            }

            return elo;
        } catch (Exception e) {
            throw new CPException("error in getAllGlobalMappedModels2", e);
        }
    }

    public AllGlobalMappedModels2ELO getAllGlobalMappedModelsForLoggedUser() throws CPException {
        try {
            AllGlobalMappedModels2ELO elo = null;
            if (this.isRemoteConnection()) {
                elo = this.getRemote().getAllGlobalMappedModelsForLoggedUser(getConnection().getUserContext().getUserId());
            } else {
                elo = this.getLocal().getAllGlobalMappedModelsForLoggedUser(getConnection().getUserContext().getUserId());
            }

            return elo;
        } catch (Exception e) {
            throw new CPException("error in getAllGlobalMappedModels2", e);
        }
    }

    public MappedFinanceCubesELO getMappedFinanceCubes(int param1) throws CPException {
        try {
            MappedFinanceCubesELO e = null;
            if (this.isRemoteConnection()) {
                e = this.getRemote().getMappedFinanceCubes(param1);
            } else {
                e = this.getLocal().getMappedFinanceCubes(param1);
            }

            return e;
        } catch (Exception var3) {
            throw new CPException("error in getMappedFinanceCubes", var3);
        }
    }

    public AllExtendedAttachmentsELO getAllExtendedAttachments() throws CPException {
        try {
            AllExtendedAttachmentsELO e = null;
            if (this.isRemoteConnection()) {
                e = this.getRemote().getAllExtendedAttachments();
            } else {
                e = this.getLocal().getAllExtendedAttachments();
            }

            return e;
        } catch (Exception var2) {
            throw new CPException("error in getAllExtendedAttachments", var2);
        }
    }

    public ExtendedAttachmentsForIdELO getExtendedAttachmentsForId(int param1) throws CPException {
        try {
            ExtendedAttachmentsForIdELO e = null;
            if (this.isRemoteConnection()) {
                e = this.getRemote().getExtendedAttachmentsForId(param1);
            } else {
                e = this.getLocal().getExtendedAttachmentsForId(param1);
            }

            return e;
        } catch (Exception var3) {
            throw new CPException("error in getExtendedAttachmentsForId", var3);
        }
    }

    public AllImageExtendedAttachmentsELO getAllImageExtendedAttachments() throws CPException {
        try {
            AllImageExtendedAttachmentsELO e = null;
            if (this.isRemoteConnection()) {
                e = this.getRemote().getAllImageExtendedAttachments();
            } else {
                e = this.getLocal().getAllImageExtendedAttachments();
            }

            return e;
        } catch (Exception var2) {
            throw new CPException("error in getAllImageExtendedAttachments", var2);
        }
    }

    public AllExternalSystemsELO getAllExternalSystems() throws CPException {
        try {
            AllExternalSystemsELO e = null;
            if (this.isRemoteConnection()) {
                e = this.getRemote().getAllExternalSystems();
            } else {
                e = this.getLocal().getAllExternalSystems();
            }

            return e;
        } catch (Exception var2) {
            throw new CPException("error in getAllExternalSystems", var2);
        }
    }

    public AllGenericExternalSystemsELO getAllGenericExternalSystems() throws CPException {
        try {
            AllGenericExternalSystemsELO e = null;
            if (this.isRemoteConnection()) {
                e = this.getRemote().getAllGenericExternalSystems();
            } else {
                e = this.getLocal().getAllGenericExternalSystems();
            }

            return e;
        } catch (Exception var2) {
            throw new CPException("error in getAllGenericExternalSystems", var2);
        }
    }

    public AllExternalSystemCompainesELO getAllExternalSystemCompaines() throws CPException {
        try {
            AllExternalSystemCompainesELO e = null;
            if (this.isRemoteConnection()) {
                e = this.getRemote().getAllExternalSystemCompaines();
            } else {
                e = this.getLocal().getAllExternalSystemCompaines();
            }

            return e;
        } catch (Exception var2) {
            throw new CPException("error in getAllExternalSystemCompaines", var2);
        }
    }

    public AllAmmModelsELO getAllAmmModels() throws CPException {
        try {
            AllAmmModelsELO e = null;
            if (this.isRemoteConnection()) {
                e = this.getRemote().getAllAmmModels();
            } else {
                e = this.getLocal().getAllAmmModels();
            }

            return e;
        } catch (Exception var2) {
            throw new CPException("error in getAllAmmModels", var2);
        }
    }

    public AllAmmModelsELO getAllAmmModelsForLoggedUser() throws CPException {
        try {
            AllAmmModelsELO e = null;
            if (this.isRemoteConnection()) {
                e = this.getRemote().getAllAmmModelsForLoggedUser(getConnection().getUserContext().getUserId());
            } else {
                e = this.getLocal().getAllAmmModelsForLoggedUser(getConnection().getUserContext().getUserId());
            }

            return e;
        } catch (Exception var2) {
            throw new CPException("error in getAllAmmModels", var2);
        }
    }

    public AllTaskGroupsELO getAllTaskGroups() throws CPException {
        try {
            AllTaskGroupsELO e = null;
            if (this.isRemoteConnection()) {
                e = this.getRemote().getAllTaskGroups();
            } else {
                e = this.getLocal().getAllTaskGroups();
            }

            return e;
        } catch (Exception var2) {
            throw new CPException("error in getAllTaskGroups", var2);
        }
    }

    public TaskGroupRICheckELO getTaskGroupRICheck(int param1) throws CPException {
        try {
            TaskGroupRICheckELO e = null;
            if (this.isRemoteConnection()) {
                e = this.getRemote().getTaskGroupRICheck(param1);
            } else {
                e = this.getLocal().getTaskGroupRICheck(param1);
            }

            return e;
        } catch (Exception var3) {
            throw new CPException("error in getTaskGroupRICheck", var3);
        }
    }

    public AllAuthenticationPolicysELO getAllAuthenticationPolicys() throws CPException {
        try {
            AllAuthenticationPolicysELO e = null;
            if (this.isRemoteConnection()) {
                e = this.getRemote().getAllAuthenticationPolicys();
            } else {
                e = this.getLocal().getAllAuthenticationPolicys();
            }

            return e;
        } catch (Exception var2) {
            throw new CPException("error in getAllAuthenticationPolicys", var2);
        }
    }

    public ActiveAuthenticationPolicysELO getActiveAuthenticationPolicys() throws CPException {
        try {
            ActiveAuthenticationPolicysELO e = null;
            if (this.isRemoteConnection()) {
                e = this.getRemote().getActiveAuthenticationPolicys();
            } else {
                e = this.getLocal().getActiveAuthenticationPolicys();
            }

            return e;
        } catch (Exception var2) {
            throw new CPException("error in getActiveAuthenticationPolicys", var2);
        }
    }

    public ActiveAuthenticationPolicyForLogonELO getActiveAuthenticationPolicyForLogon() throws CPException {
        try {
            ActiveAuthenticationPolicyForLogonELO e = null;
            if (this.isRemoteConnection()) {
                e = this.getRemote().getActiveAuthenticationPolicyForLogon();
            } else {
                e = this.getLocal().getActiveAuthenticationPolicyForLogon();
            }

            return e;
        } catch (Exception var2) {
            throw new CPException("error in getActiveAuthenticationPolicyForLogon", var2);
        }
    }

    public AllLogonHistorysELO getAllLogonHistorys() throws CPException {
        try {
            AllLogonHistorysELO e = null;
            if (this.isRemoteConnection()) {
                e = this.getRemote().getAllLogonHistorys();
            } else {
                e = this.getLocal().getAllLogonHistorys();
            }

            return e;
        } catch (Exception var2) {
            throw new CPException("error in getAllLogonHistorys", var2);
        }
    }

    public AllPasswordHistorysELO getAllPasswordHistorys() throws CPException {
        try {
            AllPasswordHistorysELO e = null;
            if (this.isRemoteConnection()) {
                e = this.getRemote().getAllPasswordHistorys();
            } else {
                e = this.getLocal().getAllPasswordHistorys();
            }

            return e;
        } catch (Exception var2) {
            throw new CPException("error in getAllPasswordHistorys", var2);
        }
    }

    public UserPasswordHistoryELO getUserPasswordHistory(int param1) throws CPException {
        try {
            UserPasswordHistoryELO e = null;
            if (this.isRemoteConnection()) {
                e = this.getRemote().getUserPasswordHistory(param1);
            } else {
                e = this.getLocal().getUserPasswordHistory(param1);
            }

            return e;
        } catch (Exception var3) {
            throw new CPException("error in getUserPasswordHistory", var3);
        }
    }

    public AllFormRebuildsELO getAllFormRebuilds() throws CPException {
        try {
            AllFormRebuildsELO e = null;
            if (this.isRemoteConnection()) {
                e = this.getRemote().getAllFormRebuilds();
            } else {
                e = this.getLocal().getAllFormRebuilds();
            }

            return e;
        } catch (Exception var2) {
            throw new CPException("error in getAllFormRebuilds", var2);
        }
    }

    public AllFormRebuildsELO getAllFormRebuildsForLoggedUser() throws CPException {
        try {
            AllFormRebuildsELO e = null;
            if (this.isRemoteConnection()) {
                e = this.getRemote().getAllFormRebuildsForLoggedUser(getConnection().getUserContext().getUserId());
            } else {
                e = this.getLocal().getAllFormRebuildsForLoggedUser(getConnection().getUserContext().getUserId());
            }

            return e;
        } catch (Exception var2) {
            throw new CPException("error in getAllFormRebuilds", var2);
        }
    }

    public AllBudgetCyclesInRebuildsELO getAllBudgetCyclesInRebuilds() throws CPException {
        try {
            AllBudgetCyclesInRebuildsELO e = null;
            if (this.isRemoteConnection()) {
                e = this.getRemote().getAllBudgetCyclesInRebuilds();
            } else {
                e = this.getLocal().getAllBudgetCyclesInRebuilds();
            }

            return e;
        } catch (Exception var2) {
            throw new CPException("error in getAllBudgetCyclesInRebuilds", var2);
        }
    }

    public AllPackagesForFinanceCubeELO getAllPackagesForFinanceCube(int param1) throws CPException {
        try {
            AllPackagesForFinanceCubeELO e = null;
            if (this.isRemoteConnection()) {
                e = this.getRemote().getAllPackagesForFinanceCube(param1);
            } else {
                e = this.getLocal().getAllPackagesForFinanceCube(param1);
            }

            return e;
        } catch (Exception var3) {
            throw new CPException("error in getAllPackagesForFinanceCube", var3);
        }
    }

    public AllCubeFormulasELO getAllCubeFormulas() throws CPException {
        try {
            AllCubeFormulasELO e = null;
            if (this.isRemoteConnection()) {
                e = this.getRemote().getAllCubeFormulas();
            } else {
                e = this.getLocal().getAllCubeFormulas();
            }

            return e;
        } catch (Exception var2) {
            throw new CPException("error in getAllCubeFormulas", var2);
        }
    }

    public CubeFormulaeForFinanceCubeELO getCubeFormulaeForFinanceCube(int param1) throws CPException {
        try {
            CubeFormulaeForFinanceCubeELO e = null;
            if (this.isRemoteConnection()) {
                e = this.getRemote().getCubeFormulaeForFinanceCube(param1);
            } else {
                e = this.getLocal().getCubeFormulaeForFinanceCube(param1);
            }

            return e;
        } catch (Exception var3) {
            throw new CPException("error in getCubeFormulaeForFinanceCube", var3);
        }
    }

    public EntityList getList(String table_) throws CPException {
        try {
            EntityList e = null;
            if (this.isRemoteConnection()) {
                e = this.getRemote().getList(table_);
            } else {
                e = this.getLocal().getList(table_);
            }

            return e;
        } catch (Exception var3) {
            throw new CPException("getting list of " + table_, var3);
        }
    }

    public EntityRef getEntityRef(Object pk) throws CPException {
        try {
            EntityRef e = null;
            if (this.isRemoteConnection()) {
                e = this.getRemote().getEntityRef(pk);
            } else {
                e = this.getLocal().getEntityRef(pk);
            }

            return e;
        } catch (Exception var3) {
            throw new CPException("getting entity ref for " + pk, var3);
        }
    }

    public BudgetDetailsForUserELO getBudgetDetailsForUser(int userId, int modelId) throws CPException {
        try {
        	ListSEJB e = this.getRemote();
            BudgetDetailsForUserELO ret = e.getBudgetDetailsForUser(userId, modelId);
            return ret;
        } catch (Exception var5) {
            throw new CPException("error in getBudgetDetailsForUser", var5);
        }
    }

    public BudgetDetailsForUserELO getBudgetDetailsForUser(int userId, boolean detailedSelection, int locationId, int cycleId) throws CPException {
        try {
        	ListSEJB e = this.getRemote();
            BudgetDetailsForUserELO ret = e.getBudgetDetailsForUser(userId, detailedSelection, locationId, cycleId);
            return ret;
        } catch (Exception var7) {
            throw new CPException("error in getBudgetDetailsForUser", var7);
        }
    }

    public EntityList getBudgetUserDetails(int bcId, int[] structureElementId) throws CPException {
        try {
        	ListSEJB e = this.getRemote();
            EntityList ret = e.getBudgetUserDetails(bcId, structureElementId);
            return ret;
        } catch (Exception var5) {
            throw new CPException("error in getBudgetUserDetails", var5);
        }
    }

    public EntityList getBudgetUserDetailsNodeDown(int bcId, int structureElementId, int structureId) throws CPException {
        try {
        	ListSEJB e = this.getRemote();
            EntityList ret = e.getBudgetUserDetailsNodeDown(bcId, structureElementId, structureId);
            return ret;
        } catch (Exception var6) {
            throw new CPException("error in getBudgetUserDetailsNodeDown", var6);
        }
    }

    public EntityList getBudgetUserAuthDetailsNodeUp(int bcId, int structureElementId, int structureId) throws CPException {
        try {
        	ListSEJB e = this.getRemote();
            EntityList ret = e.getBudgetUserAuthDetailsNodeUp(bcId, structureElementId, structureId);
            return ret;
        } catch (Exception var6) {
            throw new CPException("error in getBudgetUserAuthDetailsNodeUp", var6);
        }
    }

    public StructureElementValuesELO getStructureElementIdFromModel(int modelId) throws CPException {
        ListSEJB e = this.getRemote();
		return e.getStructureElementIdFromModel(modelId);
    }

    public EntityList getPickerStartUpDetails(int modelid, int[] structureElementId, int userId) {
        ListSEJB e = this.getRemote();
		return e.getPickerStartUpDetails(modelid, structureElementId, userId);
    }

    public AllPublicXmlReportFoldersELO getAllPublicXmlReportFolders() throws CPException {
        try {
            AllPublicXmlReportFoldersELO e = null;
            if (this.isRemoteConnection()) {
                e = this.getRemote().getAllPublicXmlReportFolders();
            } else {
                e = this.getLocal().getAllPublicXmlReportFolders();
            }

            return e;
        } catch (Exception var2) {
            throw new CPException("error in getAllPublicXmlReportFolders", var2);
        }
    }

    public AllXmlReportFoldersForUserELO getAllXmlReportFoldersForUser(int param1) throws CPException {
        try {
            AllXmlReportFoldersForUserELO e = null;
            if (this.isRemoteConnection()) {
                e = this.getRemote().getAllXmlReportFoldersForUser(param1);
            } else {
                e = this.getLocal().getAllXmlReportFoldersForUser(param1);
            }

            return e;
        } catch (Exception var3) {
            throw new CPException("error in getAllXmlReportFoldersForUser", var3);
        }
    }

    public EntityList getTreeInfoForDimTypeInModel(int modelId, int dimType) {
        try {
            return this.isRemoteConnection() ? this.getRemote().getTreeInfoForDimTypeInModel(modelId, dimType) : this.getLocal().getTreeInfoForDimTypeInModel(modelId, dimType);
        } catch (Exception var4) {
            throw new CPException("error in getAllXmlReportFoldersForUser", var4);
        }
    }

    public EntityList getTreeInfoForModel(int modelId) {
        try {
            return this.isRemoteConnection() ? this.getRemote().getTreeInfoForModel(modelId) : this.getLocal().getTreeInfoForModel(modelId);
        } catch (Exception var3) {
            throw new CPException("error in getTreeInfoForModel", var3);
        }
    }

    public EntityList getTreeInfoForModelDimTypes(int modelId, int[] type) {
        try {
            return this.isRemoteConnection() ? this.getRemote().getTreeInfoForModelDimTypes(modelId, type) : this.getLocal().getTreeInfoForModelDimTypes(modelId, type);
        } catch (Exception var4) {
            throw new CPException("error in getTreeInfoForModel", var4);
        }
    }

    public EntityList getTreeInfoForModelDimSeq(int modelId, int[] seq) {
        try {
            return this.isRemoteConnection() ? this.getRemote().getTreeInfoForModelDimSeq(modelId, seq) : this.getLocal().getTreeInfoForModelDimSeq(modelId, seq);
        } catch (Exception var4) {
            throw new CPException("error in getTreeInfoForModel", var4);
        }
    }

    public EntityList getTreeInfoForModelRA(int modelId) {
        try {
            return this.isRemoteConnection() ? this.getRemote().getTreeInfoForModelRA(modelId) : this.getLocal().getTreeInfoForModelRA(modelId);
        } catch (Exception var3) {
            throw new CPException("error in getTreeInfoForModel", var3);
        }
    }

    public EntityList getCellCalcAccesDefs(int modelId) {
        try {
            return this.isRemoteConnection() ? this.getRemote().getCellCalcAccesDefs(modelId) : this.getLocal().getCellCalcAccesDefs(modelId);
        } catch (Exception var3) {
            throw new CPException("error in getCellCalcAccesDefs", var3);
        }
    }

    public EntityList doElementPickerSearch(int dimensionId, String visId) {
        try {
            return this.isRemoteConnection() ? this.getRemote().doElementPickerSearch(dimensionId, visId) : this.getLocal().doElementPickerSearch(dimensionId, visId);
        } catch (Exception var4) {
            throw new CPException("error in getCellCalcAccesDefs", var4);
        }
    }

    public EntityList getAllUserAssignments(String pName, String pFullName, String pModel, String pLocation) {
        try {
            return this.isRemoteConnection() ? this.getRemote().getAllUserAssignments(pName, pFullName, pModel, pLocation) : this.getLocal().getAllUserAssignments(pName, pFullName, pModel, pLocation);
        } catch (Exception var6) {
            throw new CPException("error in getAllUserAssignments");
        }
    }

    public EntityList getContactLocations(int budgetCycleId, int daysUntilDue) {
        try {
            return this.isRemoteConnection() ? this.getRemote().getContactLocations(budgetCycleId, daysUntilDue) : this.getLocal().getContactLocations(budgetCycleId, daysUntilDue);
        } catch (Exception var4) {
            throw new CPException("error in getAllUserAssignments");
        }
    }

    public EntityList getModernWelcomeDetails(int userId, int daysUntilDue) {
        try {
            return this.isRemoteConnection() ? this.getRemote().getModernWelcomeDetails(userId, daysUntilDue) : this.getLocal().getModernWelcomeDetails(userId, daysUntilDue);
        } catch (Exception var4) {
            throw new CPException("error in getModernWelcomeDetails");
        }
    }

    public boolean hasUserAccessToRespArea(int userId, int modelId, int structureElementId) {
        try {
            return this.isRemoteConnection() ? this.getRemote().hasUserAccessToRespArea(userId, modelId, structureElementId) : this.getLocal().hasUserAccessToRespArea(userId, modelId, structureElementId);
        } catch (Exception var5) {
            throw new CPException("error in hasUserAccessToRespArea");
        }
    }

    public AllFinanceXmlFormsForModelELO getAllFinanceXmlFormsForModelAndUser(int modelId, int budgetCycleId, int userId, boolean hasDesignModeSecurity) {
        try {
            return this.isRemoteConnection() ? this.getRemote().getAllFinanceXmlFormsForModelAndUser(modelId, budgetCycleId, userId, hasDesignModeSecurity) : this.getLocal().getAllFinanceXmlFormsForModelAndUser(modelId, budgetCycleId, userId, hasDesignModeSecurity);
        } catch (Exception var5) {
            throw new CPException("error in getAllFinanceXmlFormsForModelAndUser");
        }
    }

    public UnreadInBoxForUserELO getSummaryUnreadMessagesForUser(String userId) {
        try {
            return this.isRemoteConnection() ? this.getRemote().getSummaryUnreadMessagesForUser(userId) : this.getLocal().getSummaryUnreadMessagesForUser(userId);
        } catch (Exception var3) {
            throw new CPException("error in getSummaryUnreadMessagesForUser");
        }
    }

    public AllNonDisabledUsersELO getDistinctInternalDestinationUsers(String[] ids) {
        try {
            return this.isRemoteConnection() ? this.getRemote().getDistinctInternalDestinationUsers(ids) : this.getLocal().getDistinctInternalDestinationUsers(ids);
        } catch (Exception var3) {
            throw new CPException("error in getDistinctInternalDestinationUsers");
        }
    }

    public AllNonDisabledUsersELO getDistinctExternalDestinationUsers(String[] ids) {
        try {
            return this.isRemoteConnection() ? this.getRemote().getDistinctExternalDestinationUsers(ids) : this.getLocal().getDistinctExternalDestinationUsers(ids);
        } catch (Exception var3) {
            throw new CPException("error in getDistinctExternalDestinationUsers");
        }
    }

    public List getLookupTableData(String tableName, List columnDef) {
        try {
            return this.isRemoteConnection() ? this.getRemote().getLookupTableData(tableName, columnDef) : this.getLocal().getLookupTableData(tableName, columnDef);
        } catch (Exception var4) {
            throw new CPException("error in getLookupTableData", var4);
        }
    }

    public EntityList getPickerDataTypesWeb(int[] subTypes, boolean writeable) {
        try {
            return this.isRemoteConnection() ? this.getRemote().getPickerDataTypesWeb(subTypes, writeable) : this.getLocal().getPickerDataTypesWeb(subTypes, writeable);
        } catch (Exception var4) {
            throw new CPException("error in getPickerDataTypesWeb");
        }
    }

    public EntityList getPickerDataTypesWeb(int financeCubeId, int[] subTypes, boolean writeable) {
        try {
            return this.isRemoteConnection() ? this.getRemote().getPickerDataTypesWeb(financeCubeId, subTypes, writeable) : this.getLocal().getPickerDataTypesWeb(financeCubeId, subTypes, writeable);
        } catch (Exception var5) {
            throw new CPException("error in getPickerDataTypesWeb");
        }
    }

    public EntityList getAllAmmModelsExceptThis(Object pk) {
        try {
            return this.isRemoteConnection() ? this.getRemote().getAllAmmModelsExceptThis(pk) : this.getLocal().getAllAmmModelsExceptThis(pk);
        } catch (Exception var3) {
            throw new CPException("Error in lister");
        }
    }

    public EntityList getAllTaskGroups(Object pk) {
        try {
            return this.isRemoteConnection() ? this.getRemote().getAllTaskGroups(pk) : this.getLocal().getAllTaskGroups(pk);
        } catch (Exception var3) {
            throw new CPException("Error in lister");
        }
    }

    public EntityList getAllUserDetailsReport(String param1, String param2, String param3, boolean param4) throws CPException {
        try {
            EntityList e = null;
            if (this.isRemoteConnection()) {
                e = this.getRemote().getAllUserDetailsReport(param1, param2, param3, param4);
            } else {
                e = this.getLocal().getAllUserDetailsReport(param1, param2, param3, param4);
            }

            return e;
        } catch (Exception var6) {
            throw new CPException("error in getAllUserDetailsReport", var6);
        }
    }

    public AllLogonHistorysReportELO getAllLogonHistorysReport(String param1, Timestamp param2, int param3) throws CPException {
        try {
            AllLogonHistorysReportELO e = null;
            if (this.isRemoteConnection()) {
                e = this.getRemote().getAllLogonHistorysReport(param1, param2, param3);
            } else {
                e = this.getLocal().getAllLogonHistorysReport(param1, param2, param3);
            }

            return e;
        } catch (Exception var5) {
            throw new CPException("error in getAllLogonHistorysReport", var5);
        }
    }

    public AllXmlFormsAndProfilesELO getAllXmlFormsAndProfiles(String param1, String param2, String param3) throws CPException {
        try {
            AllXmlFormsAndProfilesELO e = null;
            if (this.isRemoteConnection()) {
                e = this.getRemote().getAllXmlFormsAndProfiles(param1, param2, param3);
            } else {
                e = this.getLocal().getAllXmlFormsAndProfiles(param1, param2, param3);
            }

            return e;
        } catch (Exception var5) {
            throw new CPException("error in getAllXmlFormsAndProfiles", var5);
        }
    }

    public List<Integer> getReadOnlyRaAccessPositions(int modelId, int userId) {
        try {
            return this.isRemoteConnection() ? this.getRemote().getReadOnlyRaAccessPositions(modelId, userId) : this.getLocal().getReadOnlyRaAccessPositions(modelId, userId);
        } catch (Exception var4) {
            throw new CPException("error in getReadOnlyRaAccessPositions", var4);
        }
    }

    public EntityList getNodeAndUpUserAssignments(int structureElementId, int structureId) {
        try {
            if (isRemoteConnection()) {
                return getRemote().getNodeAndUpUserAssignments(structureElementId, structureId);
            }
            return getLocal().getNodeAndUpUserAssignments(structureElementId, structureId);
        } catch (Exception e) {
        }
        throw new CPException("error in getAllUserAssignments");
    }

    public UserMessageAttributesForIdELO getUserMessageAttributesForMultipleIds(String[] params) throws CPException {
        try {
            UserMessageAttributesForIdELO ret = null;
            if (isRemoteConnection()) {
                ret = getRemote().getUserMessageAttributesForMultipleIds(params);
            } else
                ret = getLocal().getUserMessageAttributesForMultipleIds(params);

            return ret;
        } catch (Exception e) {
            throw new CPException("error in getUserMessageAttributesForId", e);
        }
    }

    public EntityList getMailDetailForUser(String userName, int type, int from, int to) throws CPException {
        try {
            EntityList ret = null;
            if (isRemoteConnection()) {
                ret = getRemote().getMailDetailForUser(userName, type, from, to);
            } else
                ret = getLocal().getMailDetailForUser(userName, type, from, to);

            return ret;
        } catch (Exception e) {
            throw new CPException("error in getInBoxDetailForUser", e);
        }
    }

    public AllMasterQuestionsELO getAllMasterQuestions() throws CPException {
        try {
            AllMasterQuestionsELO ret = null;
            if (isRemoteConnection()) {
                ret = getRemote().getAllMasterQuestions();
            } else
                ret = getLocal().getAllMasterQuestions();

            return ret;
        } catch (Exception e) {
            throw new CPException("error in getAllMasterQuestions", e);
        }
    }

    public QuestionByIDELO getQuestionByID(int param1) throws CPException {
        try {
            QuestionByIDELO ret = null;
            if (isRemoteConnection()) {
                ret = getRemote().getQuestionByID(param1);
            } else
                ret = getLocal().getQuestionByID(param1);

            return ret;
        } catch (Exception e) {
            throw new CPException("error in getQuestionByID", e);
        }
    }

    public AllChallengeQuestionsELO getAllChallengeQuestions() throws CPException {
        try {
            AllChallengeQuestionsELO ret = null;
            if (isRemoteConnection()) {
                ret = getRemote().getAllChallengeQuestions();
            } else
                ret = getLocal().getAllChallengeQuestions();

            return ret;
        } catch (Exception e) {
            throw new CPException("error in getAllChallengeQuestions", e);
        }
    }

    public AllQuestionsAndAnswersByUserIDELO getAllQuestionsAndAnswersByUserID(int param1) throws CPException {
        try {
            AllQuestionsAndAnswersByUserIDELO ret = null;
            if (isRemoteConnection()) {
                ret = getRemote().getAllQuestionsAndAnswersByUserID(param1);
            } else
                ret = getLocal().getAllQuestionsAndAnswersByUserID(param1);

            return ret;
        } catch (Exception e) {
            throw new CPException("error in getAllQuestionsAndAnswersByUserID", e);
        }
    }

    public AllQuestionsAndAnswersByUserIDELO getChallengeWord(int userId) throws CPException {
        try {
            AllQuestionsAndAnswersByUserIDELO ret = null;
            if (isRemoteConnection()) {
                ret = getRemote().getChallengeWord(userId);
            } else
                ret = getLocal().getChallengeWord(userId);

            return ret;
        } catch (Exception e) {
            throw new CPException("error in getAllQuestionsAndAnswersByUserID", e);
        }
    }

    public void setChallengeWord(int userId, String word) throws CPException {
        try {
            if (isRemoteConnection()) {
                getRemote().setChallengeWord(userId, word);
            } else {
                getLocal().setChallengeWord(userId, word);
            }
        } catch (Exception e) {
            throw new CPException("error in setChallengeWord", e);
        }
    }

    public AllUserResetLinksELO getAllUserResetLinks() throws CPException {
        try {
            AllUserResetLinksELO ret = null;
            if (isRemoteConnection()) {
                ret = getRemote().getAllUserResetLinks();
            } else
                ret = getLocal().getAllUserResetLinks();

            return ret;
        } catch (Exception e) {
            throw new CPException("error in getAllUserResetLinks", e);
        }
    }

    public LinkByUserIDELO getLinkByUserID(int param1) throws CPException {
        try {
            LinkByUserIDELO ret = null;
            if (isRemoteConnection()) {
                ret = getRemote().getLinkByUserID(param1);
            } else
                ret = getLocal().getLinkByUserID(param1);

            return ret;
        } catch (Exception e) {
            throw new CPException("error in getLinkByUserID", e);
        }
    }

    public EntityList getModelUserSecurity() {
        try {
            EntityList ret = null;
            if (isRemoteConnection()) {
                ret = getRemote().getModelUserSecurity();
            } else {
                ret = getLocal().getModelUserSecurity();
            }
            return ret;
        } catch (Exception e) {
            throw new CPException("error in getModelUserSecurity", e);
        }
    }

    public EntityList getUserModelSecurity() {
        try {
            EntityList ret = null;
            if (isRemoteConnection()) {
                ret = getRemote().getUserModelSecurity();
            } else {
                ret = getLocal().getUserModelSecurity();
            }
            return ret;
        } catch (Exception e) {
            throw new CPException("error in getUserModelSecurity", e);
        }
    }

    public List<UserModelElementAssignment> getRespAreaAccess(PrimaryKey pk) throws CPException {
        try {
            List<UserModelElementAssignment> ret = null;
            if (isRemoteConnection()) {
                ret = getRemote().getRespAreaAccess(pk);
            } else {
                ret = getLocal().getRespAreaAccess(pk);
            }
            return ret;
        } catch (Exception e) {
            throw new CPException("error in getModelAndHierarchies", e);
        }
    }

    public List<Object[]> getDataEntryProfileForBcAndUser(int bcId, int userId) {
        try {
            List<Object[]> ret = null;
            if (isRemoteConnection()) {
                ret = getRemote().getDataEntryProfileForBcAndUser(bcId, userId);
            } else {
                ret = getLocal().getDataEntryProfileForBcAndUser(bcId, userId);
            }
            return ret;
        } catch (Exception e) {
            throw new CPException("error in getUserModelSecurity", e);
        }
    }

    public String getCPContextId(Object context) {
        try {
            String ret = null;
            if (isRemoteConnection()) {
                ret = getRemote().getCPContextId(context);
            } else {
                ret = getLocal().getCPContextId(context);
            }
            return ret;
        } catch (Exception e) {
            throw new CPException("error in getCPContextId", e);
        }
    }

    public Object getCPContext(String id) {
        try {
            Object ret = null;
            if (isRemoteConnection()) {
                ret = getRemote().getCPContext(id);
            } else {
                ret = getLocal().getCPContext(id);
            }
            return ret;
        } catch (Exception e) {
            throw new CPException("error in getCPContext", e);
        }
    }

    public void removeContext(Object context) {
        try {
            if (isRemoteConnection()) {
                getRemote().removeContext(context);
            } else {
                getLocal().removeContext(context);
            }
        } catch (Exception e) {
            throw new CPException("error in removeCPContext", e);
        }
    }

    public void removeContextByContextId(List<String> contextIds) {
        try {
            if (isRemoteConnection()) {
                getRemote().removeContextByContextId(contextIds);
            } else {
                getLocal().removeContextByContextId(contextIds);
            }
        } catch (Exception e) {
            throw new CPException("error in removeCPContext", e);
        }
    }

    public void removeContextByUserName(List<String> userNames) {
        try {
            if (isRemoteConnection()) {
                getRemote().removeContextByUserName(userNames);
            } else {
                getLocal().removeContextByUserName(userNames);
            }
        } catch (Exception e) {
            throw new CPException("error in removeCPContext", e);
        }
    }

    public Map getContextSnapShot() {
        try {
            Map ret = null;
            if (isRemoteConnection()) {
                ret = getRemote().getContextSnapShot();
            } else {
                ret = getLocal().getContextSnapShot();
            }
            return ret;
        } catch (Exception e) {
            throw new CPException("error in getContextSnapShot", e);
        }
    }

    public EntityList getAllLoggedInUsers() {
        try {
            EntityList ret = null;
            if (isRemoteConnection()) {
                ret = getRemote().getAllLoggedInUsers();
            } else {
                ret = getLocal().getAllLoggedInUsers();
            }
            return ret;
        } catch (Exception e) {
            throw new CPException("error in getAllLoggedInUsers", e);
        }
    }

    public EntityList getModelsAndRAHierarchies() {
        try {
            EntityList ret = null;
            if (isRemoteConnection()) {
                ret = getRemote().getModelsAndRAHierarchies();
            } else {
                ret = getLocal().getModelsAndRAHierarchies();
            }
            return ret;
        } catch (Exception e) {
            throw new CPException("error in getModelsAndRAHierarchies", e);
        }
    }

    public ArrayList<Object[]> getNotesForCostCenters(ArrayList<Integer> costCenters, int financeCubeId, String fromDate, String toDate) {
        try {
            ArrayList<Object[]> ret = null;
            if (isRemoteConnection()) {
                ret = getRemote().getNotesForCostCenters(costCenters, financeCubeId, fromDate, toDate);
            } else {
                ret = getLocal().getNotesForCostCenters(costCenters, financeCubeId, fromDate, toDate);
            }
            return ret;
        } catch (Exception e) {
            throw new CPException("error in getNotesForCostCenters", e);
        }
    }

    public HashMap<String, ArrayList<HierarchyRef>> getCalendarForModels(HashSet<String> models) {
        HashMap<String, ArrayList<HierarchyRef>> ret;
        try {
            if (isRemoteConnection()) {
                ret = getRemote().getCalendarForModels(models);
            } else {
                ret = getLocal().getCalendarForModels(models);
            }
            return ret;
        } catch (Exception e) {
            throw new CPException("error in getCalendarForModels", e);
        }
    }

    public AllDashboardsForUserELO getDashboardForms(Integer userId, boolean isAdmin) {
        try {
            AllDashboardsForUserELO e = null;
            if (this.isRemoteConnection()) {
                e = this.getRemote().getDashboardForms(userId, isAdmin);
            } else {
                e = this.getLocal().getDashboardForms(userId, isAdmin);
            }

            return e;
        } catch (Exception var2) {
            throw new CPException("error in getDashboardForms", var2);
        }
    

    }
}
