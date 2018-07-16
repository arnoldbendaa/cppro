// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:29:00
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.utc.struts.workbench;

import com.cedar.cp.api.base.CPConnection;
import com.cedar.cp.api.base.EntityList;
import com.cedar.cp.api.base.EntityRef;
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

public class BudgetCycleDetailsSetupAction extends BaseWorkBenchAction {

   public void populateForm(WorkBenchForm myForm, CPContext context, HttpServletRequest request) {
      CPConnection cnx = context.getCPConnection();
      ModelDTO modelDTO = new ModelDTO();
      BudgetUsersProcess budgetUserProcess = cnx.getBudgetUsersProcess();
      int userId = cnx.getUserContext().getUserId();
      EntityList cycles = budgetUserProcess.getBudgetDetailsForUser(userId);
      int rows = cycles.getNumRows();

      for(int i = 0; i < rows; ++i) {
         EntityRef modelref = (EntityRef)cycles.getValueAt(i, "Model");
         Object modelPK = modelref.getPrimaryKey();
         if(modelPK.toString().endsWith(String.valueOf(myForm.getModelId()))) {
            modelDTO.setName(modelref);
            EntityList budgetCycleEntityList = (EntityList)cycles.getValueAt(i, "BudgetCycles");
            int noCycles = budgetCycleEntityList.getNumRows();
            ArrayList budgetCycleList = new ArrayList();

            for(int j = 0; j < noCycles; ++j) {
               BudgetCycleDTO bcDTO = new BudgetCycleDTO();
               bcDTO.setBudgetCycle(budgetCycleEntityList.getValueAt(j, "BudgetCycle"));
               bcDTO.setModelId(((Integer)budgetCycleEntityList.getValueAt(j, "ModelId")).intValue());
               bcDTO.setBudgetCycleId(((Integer)budgetCycleEntityList.getValueAt(j, "BudgetCycleId")).intValue());
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
                  blDTO.setIdentifier((String)budgetLocationEntityList.getValueAt(k, "VisId"));
                  blDTO.setDescription((String)budgetLocationEntityList.getValueAt(k, "Description"));
                  blDTO.setDepth(((Integer)budgetLocationEntityList.getValueAt(k, "Depth")).intValue());
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

                     childDTO.setState(childStateValue);
                     childDTO.setStructureElementId(((Integer)children.getValueAt(l, "StructureElementId")).intValue());
                     childDTO.setIdentifier((String)children.getValueAt(l, "ElementVisId"));
                     childDTO.setUserCount(((Integer)children.getValueAt(l, "UserCount")).intValue());
                     childDTO.setEndDate((Timestamp)children.getValueAt(l, "EndDate"));
                     childDTO.setLateDate(this.getCPSystemProperties(request).getStatusWarningDate());
                     budgetLocationChildList.add(childDTO);
                  }

                  blDTO.setChildren(budgetLocationChildList);
                  budgetLocationList.add(blDTO);
               }

               bcDTO.setBudgetLocations(budgetLocationList);
               if(myForm.getBudgetCycleId() == bcDTO.getBudgetCycleId()) {
                  budgetCycleList.add(bcDTO);
               }
            }

            modelDTO.setBudgetCycle(budgetCycleList);
         }
      }

      myForm.setRoot(modelDTO);
   }
}
