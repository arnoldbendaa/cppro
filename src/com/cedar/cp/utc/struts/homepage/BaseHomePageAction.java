// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:28:55
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.utc.struts.homepage;

import com.cedar.cp.api.base.CPConnection;
import com.cedar.cp.api.base.EntityList;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.model.BudgetUsersProcess;
import com.cedar.cp.utc.common.CPAction;
import com.cedar.cp.utc.common.CPContext;
import com.cedar.cp.utc.struts.homepage.BLChildDTO;
import com.cedar.cp.utc.struts.homepage.BudgetCycleDTO;
import com.cedar.cp.utc.struts.homepage.BudgetInstructionDTO;
import com.cedar.cp.utc.struts.homepage.BudgetLocationDTO;
import com.cedar.cp.utc.struts.homepage.HomePageForm;
import com.cedar.cp.utc.struts.homepage.ModelDTO;
import java.sql.Timestamp;
import java.util.ArrayList;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionForm;

public class BaseHomePageAction extends CPAction {

   protected void populateHomeForm(ActionForm form, HttpServletRequest request, CPContext context) {
      CPConnection cnx = context.getCPConnection();
      HomePageForm myForm = (HomePageForm)form;
      ArrayList modelList = null;
      if(myForm.getRefresh().equals("false") || modelList == null) {
         modelList = new ArrayList();
         BudgetUsersProcess budgetUserProcess = cnx.getBudgetUsersProcess();
         int userId = cnx.getUserContext().getUserId();
         EntityList cycles = budgetUserProcess.getBudgetDetailsForUser(userId);
         int rows = cycles.getNumRows();

         for(int i = 0; i < rows; ++i) {
            ModelDTO modelDTO = new ModelDTO();
            Object modelref = cycles.getValueAt(i, "Model");
            Integer modelId = (Integer)cycles.getValueAt(i, "ModelId");
            modelDTO.setName(modelref);
            modelDTO.setModelId(modelId.intValue());
            EntityList budgetCycleEntityList = (EntityList)cycles.getValueAt(i, "BudgetCycles");
            int noCycles = budgetCycleEntityList.getNumRows();
            ArrayList budgetCycleList = new ArrayList();

            for(int j = 0; j < noCycles; ++j) {
               BudgetCycleDTO bcDTO = new BudgetCycleDTO();
               bcDTO.setBudgetCycle(budgetCycleEntityList.getValueAt(j, "BudgetCycle"));
               bcDTO.setModelId(((Integer)budgetCycleEntityList.getValueAt(j, "ModelId")).intValue());
               bcDTO.setBudgetCycleId(((Integer)budgetCycleEntityList.getValueAt(j, "BudgetCycleId")).intValue());
               EntityList cycleInstructionEntityList = (EntityList)budgetCycleEntityList.getValueAt(j, "CycleInstructions");
               int noOfCycleInst = cycleInstructionEntityList.getNumRows();
               ArrayList budgetCycleInstructions = new ArrayList();

               for(int budgetLocationEntityList = 0; budgetLocationEntityList < noOfCycleInst; ++budgetLocationEntityList) {
                  BudgetInstructionDTO noLocations = new BudgetInstructionDTO();
                  noLocations.setId(((Integer)cycleInstructionEntityList.getValueAt(budgetLocationEntityList, "BudgetInstructionId")).intValue());
                  noLocations.setIdentifier((String)cycleInstructionEntityList.getValueAt(budgetLocationEntityList, "VisId"));
                  budgetCycleInstructions.add(noLocations);
               }

               bcDTO.setBudgetCycleInstructions(budgetCycleInstructions);
               EntityList var39 = (EntityList)budgetCycleEntityList.getValueAt(j, "BudgetLocations");
               int var40 = var39.getNumRows();
               ArrayList budgetLocationList = new ArrayList();

               for(int k = 0; k < var40; ++k) {
                  BudgetLocationDTO blDTO = new BudgetLocationDTO();
                  Object blState = var39.getValueAt(k, "State");
                  int blStateValue = 0;
                  if(blState != null && blState instanceof Integer) {
                     blStateValue = ((Integer)blState).intValue();
                  }

                  blDTO.setState(blStateValue);
                  blDTO.setStructureElementId(((Integer)var39.getValueAt(k, "StructureElementId")).intValue());
                  blDTO.setIdentifier((String)var39.getValueAt(k, "VisId"));
                  blDTO.setDescription((String)var39.getValueAt(k, "Description"));
                  blDTO.setDepth(((Integer)var39.getValueAt(k, "Depth")).intValue());
                  blDTO.setEndDate((Timestamp)var39.getValueAt(k, "EndDate"));
                  blDTO.setLateDate(this.getCPSystemProperties(request).getStatusWarningDate());
                  EntityList children = (EntityList)var39.getValueAt(k, "ChildLocations");
                  int noChildren = children.getNumRows();
                  ArrayList budgetLocationChildList = new ArrayList();

                  for(int locationInstructionEntityList = 0; locationInstructionEntityList < noChildren; ++locationInstructionEntityList) {
                     BLChildDTO noLocationInstructions = new BLChildDTO();
                     Object l = children.getValueAt(locationInstructionEntityList, "State");
                     int biDTO = 0;
                     if(l != null && l instanceof Integer) {
                        biDTO = ((Integer)l).intValue();
                     }

                     noLocationInstructions.setState(biDTO);
                     noLocationInstructions.setStructureElementId(((Integer)children.getValueAt(locationInstructionEntityList, "StructureElementId")).intValue());
                     noLocationInstructions.setIdentifier((String)children.getValueAt(locationInstructionEntityList, "ElementVisId"));
                     noLocationInstructions.setUserCount(((Integer)children.getValueAt(locationInstructionEntityList, "UserCount")).intValue());
                     noLocationInstructions.setEndDate((Timestamp)children.getValueAt(locationInstructionEntityList, "EndDate"));
                     noLocationInstructions.setLateDate(this.getCPSystemProperties(request).getStatusWarningDate());
                     budgetLocationChildList.add(noLocationInstructions);
                  }

                  blDTO.setChildren(budgetLocationChildList);
                  EntityList var42 = (EntityList)var39.getValueAt(k, "BudgetInstructions");
                  int var41 = var42.getNumRows();
                  ArrayList budgetLocationInstructions = new ArrayList();

                  for(int var44 = 0; var44 < var41; ++var44) {
                     BudgetInstructionDTO var43 = new BudgetInstructionDTO();
                     var43.setId(((Integer)var42.getValueAt(var44, "BudgetInstructionId")).intValue());
                     var43.setIdentifier((String)var42.getValueAt(var44, "VisId"));
                     budgetLocationInstructions.add(var43);
                  }

                  blDTO.setBudgetInstruction(budgetLocationInstructions);
                  budgetLocationList.add(blDTO);
               }

               bcDTO.setBudgetLocations(budgetLocationList);
               budgetCycleList.add(bcDTO);
            }

            modelDTO.setBudgetCycle(budgetCycleList);
            modelList.add(modelDTO);
         }
      }

      myForm.setModel(modelList);
      this.populateVirementStatus(myForm, context);
   }

   protected void populateVirementStatus(HomePageForm form, CPContext context) {
      try {
         form.setVirementsToAuthorise(context.getCPConnection().getVirementRequestsProcess().haveVirementsWhichRequireAuthorisation());
      } catch (ValidationException var4) {
         this.mLog.error("populateVirementStatus", var4.getMessage());
      }

   }
}
