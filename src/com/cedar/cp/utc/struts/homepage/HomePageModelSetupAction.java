package com.cedar.cp.utc.struts.homepage;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.cedar.cp.api.base.CPConnection;
import com.cedar.cp.api.base.EntityList;
import com.cedar.cp.api.model.BudgetUsersProcess;
import com.cedar.cp.utc.common.BrowserUtil;
import com.cedar.cp.utc.common.CPContext;

public class HomePageModelSetupAction extends BaseHomePageAction {

    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        CPContext context = this.getCPContext(request);
        this.removeWebProcess(request);
        if (BrowserUtil.isUnsupportedBrowser(request)) {
            return mapping.findForward("unsupportedBrowser");
//        } else if ((context == null || context.getUserContext().userMustChangePassword() || context.isPortalUser()) && (context == null || !context.isSingleSignon() && !context.isCosignSignon() && !context.isPortalSignon() && !context.isNtlmSignon())) {
//            return mapping.findForward("notLoggedOn");
        } else {
            this.populateHomeForm(form, request, context);
            return mapping.findForward("loggedOn");
        }
    }

    protected void populateHomeForm(ActionForm form, HttpServletRequest request, CPContext context) {
        CPConnection cnx = context.getCPConnection();
        HomePageForm myForm = (HomePageForm) form;
        ArrayList modelList = null;
        modelList = new ArrayList();
        BudgetUsersProcess budgetUserProcess = cnx.getBudgetUsersProcess();
        int userId = cnx.getUserContext().getUserId();
        int pModelId = Integer.parseInt(request.getParameter("modelId"));
        EntityList cycles = budgetUserProcess.getBudgetDetailsForUser(userId, pModelId);
        int rows = cycles.getNumRows();

        Set<String> setRoles = context.getUserContext().getUserRoles();

        for (int i = 0; i < rows; ++i) {
            ModelDTO modelDTO = new ModelDTO();
            Object modelref = cycles.getValueAt(i, "Model");
            Integer modelId = (Integer) cycles.getValueAt(i, "ModelId");
            modelDTO.setName(modelref);
            modelDTO.setModelId(modelId.intValue());
            EntityList budgetCycleEntityList = (EntityList) cycles.getValueAt(i, "BudgetCycles");
            int noCycles = budgetCycleEntityList.getNumRows();
            ArrayList<BudgetCycleDTO> budgetCycleList = new ArrayList<BudgetCycleDTO>();

            for (int j = 0; j < noCycles; ++j) {

                BudgetCycleDTO bcDTO = new BudgetCycleDTO();
                bcDTO.setBudgetCycle(budgetCycleEntityList.getValueAt(j, "BudgetCycle"));
                bcDTO.setModelId(((Integer) budgetCycleEntityList.getValueAt(j, "ModelId")).intValue());
                bcDTO.setBudgetCycleId(((Integer) budgetCycleEntityList.getValueAt(j, "BudgetCycleId")).intValue());
                bcDTO.setCategory((String) budgetCycleEntityList.getValueAt(j, "Category"));

                EntityList cycleInstructionEntityList = (EntityList) budgetCycleEntityList.getValueAt(j, "CycleInstructions");
                int noOfCycleInst = cycleInstructionEntityList.getNumRows();
                ArrayList budgetCycleInstructions = new ArrayList();
                for (int budgetLocationEntityList = 0; budgetLocationEntityList < noOfCycleInst; ++budgetLocationEntityList) {
                    BudgetInstructionDTO noLocations = new BudgetInstructionDTO();
                    noLocations.setId(((Integer) cycleInstructionEntityList.getValueAt(budgetLocationEntityList, "BudgetInstructionId")).intValue());
                    noLocations.setIdentifier((String) cycleInstructionEntityList.getValueAt(budgetLocationEntityList, "VisId"));
                    budgetCycleInstructions.add(noLocations);
                }
                bcDTO.setBudgetCycleInstructions(budgetCycleInstructions);

                EntityList var39 = (EntityList) budgetCycleEntityList.getValueAt(j, "BudgetLocations");
                int var40 = var39.getNumRows();
                ArrayList budgetLocationList = new ArrayList();

                for (int k = 0; k < var40; ++k) {
                    BudgetLocationDTO blDTO = new BudgetLocationDTO();
                    Object blState = var39.getValueAt(k, "State");
                    int blStateValue = 0;
                    if (blState != null && blState instanceof Integer) {
                        blStateValue = ((Integer) blState).intValue();
                    }

                    blDTO.setState(blStateValue);
                    blDTO.setStructureElementId(((Integer) var39.getValueAt(k, "StructureElementId")).intValue());
                    blDTO.setIdentifier((String) var39.getValueAt(k, "VisId"));
                    blDTO.setDescription((String) var39.getValueAt(k, "Description"));
                    blDTO.setDepth(((Integer) var39.getValueAt(k, "Depth")).intValue());
                    blDTO.setEndDate((Timestamp) var39.getValueAt(k, "EndDate"));
                    blDTO.setLateDate(this.getCPSystemProperties(request).getStatusWarningDate());
                    blDTO.setFullRights(((Boolean) var39.getValueAt(k, "FullRights")).booleanValue());

                    EntityList children = (EntityList) var39.getValueAt(k, "ChildLocations");
                    int noChildren = children.getNumRows();
                    ArrayList budgetLocationChildList = new ArrayList();

                    for (int locationInstructionEntityList = 0; locationInstructionEntityList < noChildren; ++locationInstructionEntityList) {
                        BLChildDTO noLocationInstructions = new BLChildDTO();
                        Object l = children.getValueAt(locationInstructionEntityList, "State");
                        int biDTO = 0;
                        if (l != null && l instanceof Integer) {
                            biDTO = ((Integer) l).intValue();
                        }

                        noLocationInstructions.setState(biDTO);
                        noLocationInstructions.setStructureElementId(((Integer) children.getValueAt(locationInstructionEntityList, "StructureElementId")).intValue());
                        noLocationInstructions.setIdentifier((String) children.getValueAt(locationInstructionEntityList, "ElementVisId"));

                        noLocationInstructions.setDescription((String) children.getValueAt(locationInstructionEntityList, "Description"));
                        noLocationInstructions.setFullRights(((Boolean) children.getValueAt(locationInstructionEntityList, "FullRights")).booleanValue());
                        noLocationInstructions.setLastUpdateById(((Integer) children.getValueAt(locationInstructionEntityList, "LastUpdateById")).intValue());

                        noLocationInstructions.setUserCount(((Integer) children.getValueAt(locationInstructionEntityList, "UserCount")).intValue());
                        noLocationInstructions.setEndDate((Timestamp) children.getValueAt(locationInstructionEntityList, "EndDate"));
                        noLocationInstructions.setLateDate(this.getCPSystemProperties(request).getStatusWarningDate());
                        noLocationInstructions.setParent(blDTO);

                        budgetLocationChildList.add(noLocationInstructions);
                    }

                    blDTO.setChildren(budgetLocationChildList);
                    EntityList var42 = (EntityList) var39.getValueAt(k, "BudgetInstructions");
                    int var41 = var42.getNumRows();
                    ArrayList budgetLocationInstructions = new ArrayList();

                    for (int var44 = 0; var44 < var41; ++var44) {
                        BudgetInstructionDTO var43 = new BudgetInstructionDTO();
                        var43.setId(((Integer) var42.getValueAt(var44, "BudgetInstructionId")).intValue());
                        var43.setIdentifier((String) var42.getValueAt(var44, "VisId"));
                        budgetLocationInstructions.add(var43);
                    }

                    blDTO.setBudgetInstruction(budgetLocationInstructions);
                    budgetLocationList.add(blDTO);
                }

                bcDTO.setBudgetLocations(budgetLocationList);
                
                if (bcDTO.getCategory().equalsIgnoreCase("B") && setRoles.contains("Budget Cycle - Budget") || bcDTO.getCategory().equalsIgnoreCase("F") && setRoles.contains("Budget Cycle - Forecast") || bcDTO.getCategory().equalsIgnoreCase("M") && setRoles.contains("Budget Cycle - Management Accounts")) {
                    budgetCycleList.add(bcDTO);
                }
            }
            //Collections.sort(budgetCycleList, new BudgetCycleComparatorUtil().BudgetCategoryComparator);
            modelDTO.setBudgetCycle(budgetCycleList);
            modelList.add(modelDTO);
        }

        myForm.setModel(modelList);
    }
}
