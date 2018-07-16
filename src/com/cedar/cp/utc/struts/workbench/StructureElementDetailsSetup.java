// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:29:00
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.utc.struts.workbench;

import com.cedar.cp.api.base.CPConnection;
import com.cedar.cp.api.base.EntityList;
import com.cedar.cp.api.base.UserContext;
import com.cedar.cp.api.model.BudgetUsersProcess;
import com.cedar.cp.utc.common.CPContext;
import com.cedar.cp.utc.struts.homepage.BLChildDTO;
import com.cedar.cp.utc.struts.homepage.BudgetCycleDTO;
import com.cedar.cp.utc.struts.homepage.BudgetLocationDTO;
import com.cedar.cp.utc.struts.homepage.ModelDTO;
import com.cedar.cp.utc.struts.workbench.BaseWorkBenchAction;
import com.cedar.cp.utc.struts.workbench.WorkBenchForm;
import java.sql.Timestamp;
import java.util.ArrayList;
import javax.servlet.http.HttpServletRequest;

public class StructureElementDetailsSetup extends BaseWorkBenchAction {

   public void populateForm(WorkBenchForm myForm, CPContext context, HttpServletRequest request) {
      CPConnection cnx = context.getCPConnection();
      UserContext userCnx = cnx.getUserContext();
      String locationIdentifier = "";
      String description = "";
      ModelDTO modelDTO = new ModelDTO();
      BudgetUsersProcess process = cnx.getBudgetUsersProcess();
      int userId = userCnx.getUserId();
      EntityList cycles = process.getBudgetDetailsForUser(userId, true, myForm.getStructureElementId(), myForm.getBudgetCycleId());
      int rows = cycles.getNumRows();

      for(int i = 0; i < rows; ++i) {
         if(rows > 1) {
            this.getLogger().debug("Dont think this should happen");
         }

         Object modelref = cycles.getValueAt(i, "Model");
         modelDTO.setName(modelref);
         EntityList budgetCycleEntityList = (EntityList)cycles.getValueAt(i, "BudgetCycles");
         int noCycles = budgetCycleEntityList.getNumRows();
         ArrayList budgetCycleList = new ArrayList();

         for(int j = 0; j < noCycles; ++j) {
            BudgetCycleDTO bcDTO = new BudgetCycleDTO();
            bcDTO.setBudgetCycle(budgetCycleEntityList.getValueAt(j, "BudgetCycle"));
            bcDTO.setModelId(((Integer)budgetCycleEntityList.getValueAt(j, "ModelId")).intValue());
            bcDTO.setBudgetCycleId(((Integer)budgetCycleEntityList.getValueAt(j, "BudgetCycleId")).intValue());
            bcDTO.setHierachyId(((Integer)budgetCycleEntityList.getValueAt(j, "HierarchyId")).intValue());
            EntityList budgetLocationEntityList = (EntityList)budgetCycleEntityList.getValueAt(j, "BudgetLocations");
            int noLocations = budgetLocationEntityList.getNumRows();
            ArrayList budgetLocationList = new ArrayList();

            for(int k = 0; k < noLocations; ++k) {
               BudgetLocationDTO blDTO = new BudgetLocationDTO();
               Object blState = budgetLocationEntityList.getValueAt(k, "State");
               int blStateValue = 0;
               if(blState != null && blState instanceof Integer) {
                  blStateValue = ((Integer)blState).intValue();
               }

               blDTO.setState(blStateValue);
               blDTO.setStructureElementId(((Integer)budgetLocationEntityList.getValueAt(k, "StructureElementId")).intValue());
               locationIdentifier = (String)budgetLocationEntityList.getValueAt(k, "VisId");
               blDTO.setIdentifier(locationIdentifier);
               description = (String)budgetLocationEntityList.getValueAt(k, "Description");
               blDTO.setDescription(description);
               blDTO.setDepth(((Integer)budgetLocationEntityList.getValueAt(k, "Depth")).intValue() + 1);
               blDTO.setEndDate((Timestamp)budgetLocationEntityList.getValueAt(k, "EndDate"));
               blDTO.setLateDate(this.getCPSystemProperties(request).getStatusWarningDate());
               EntityList children = (EntityList)budgetLocationEntityList.getValueAt(k, "ChildLocations");
               int noChildren = children.getNumRows();
               ArrayList budgetLocationChildList = new ArrayList();

               for(int l = 0; l < noChildren; ++l) {
                  BLChildDTO childDTO = new BLChildDTO();
                  Object childState = children.getValueAt(l, "State");
                  int childStateValue = 0;
                  if(childState != null && childState instanceof Integer) {
                     childStateValue = ((Integer)childState).intValue();
                  }

                  if((myForm.getStateFilter() != 1 || childStateValue == 0) && (myForm.getStateFilter() != 2 || childStateValue == 2) && (myForm.getStateFilter() != 3 || childStateValue == 3) && (myForm.getStateFilter() != 4 || childStateValue == 4)) {
                     childDTO.setState(childStateValue);
                     childDTO.setStructureElementId(((Integer)children.getValueAt(l, "StructureElementId")).intValue());
                     childDTO.setIdentifier((String)children.getValueAt(l, "ElementVisId"));
                     childDTO.setDescription((String)children.getValueAt(l, "Description"));
                     childDTO.setUserCount(((Integer)children.getValueAt(l, "UserCount")).intValue());
                     childDTO.setOtherUserCount(((Integer)children.getValueAt(l, "OtherUserCount")).intValue());
                     childDTO.setSubmitable(((Boolean)children.getValueAt(l, "Submitable")).booleanValue());
                     childDTO.setRejectable(((Boolean)children.getValueAt(l, "Rejectable")).booleanValue());
                     childDTO.setEndDate((Timestamp)children.getValueAt(l, "EndDate"));
                     childDTO.setLateDate(this.getCPSystemProperties(request).getStatusWarningDate());
                     childDTO.setParent(blDTO);
                     budgetLocationChildList.add(childDTO);
                  }
               }

               blDTO.setChildren(budgetLocationChildList);
               blDTO.setUserCount(((Integer)budgetLocationEntityList.getValueAt(k, "UserCount")).intValue());
               blDTO.setController(myForm.isController());
               blDTO.setSubmitable(((Boolean)budgetLocationEntityList.getValueAt(k, "Submitable")).booleanValue());
               blDTO.setRejectable(((Boolean)budgetLocationEntityList.getValueAt(k, "Rejectable")).booleanValue());
               if(myForm.getOldUserCount() == 0 && blDTO.getUserCount() > 0) {
                  myForm.setOldUserCount(blDTO.getUserCount());
               } else if(myForm.getOldUserCount() > 0) {
                  blDTO.setUserCount(1);
                  blDTO.setAgreeable(myForm.getOldDepth() > 0);
               }

               budgetLocationList.add(blDTO);
            }

            bcDTO.setBudgetLocations(budgetLocationList);
            budgetCycleList.add(bcDTO);
         }

         modelDTO.setBudgetCycle(budgetCycleList);
      }

      myForm.setRoot(modelDTO);
      this.doCrumbs(myForm, locationIdentifier, description);
   }
}
