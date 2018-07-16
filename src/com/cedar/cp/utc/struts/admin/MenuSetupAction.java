// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:28:51
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.utc.struts.admin;

import com.cedar.cp.api.base.UserContext;
import com.cedar.cp.utc.common.CPAction;
import com.cedar.cp.utc.common.CPContext;
import com.cedar.cp.utc.struts.admin.MenuForm;
import com.cedar.cp.utc.struts.admin.MenuOptionDTO;
import com.cedar.cp.utc.struts.admin.MenuRoleOptionDTO;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class MenuSetupAction extends CPAction {

   private static List<MenuOptionDTO> mAvailable = new ArrayList();


   public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception {
      MenuForm myForm = (MenuForm)actionForm;
      CPContext conn = this.getCPContext(httpServletRequest);
      UserContext userCtx = conn.getUserContext();
      Set roles = userCtx.getUserRoles();
      Map roleDescriptions = userCtx.getRoleDescriptions();
      List<MenuRoleOptionDTO> validOptions = new ArrayList();
      Iterator i$ = roles.iterator();

      while(i$.hasNext()) {
         String role = (String)i$.next();
         ArrayList options = new ArrayList();
         Set securityStrings = userCtx.getUserRoleSecurityStrings(role);
         if(securityStrings != null) {
            Iterator i$1 = mAvailable.iterator();

            while(i$1.hasNext()) {
               MenuOptionDTO aAvailable = (MenuOptionDTO)i$1.next();
               if(securityStrings.contains(aAvailable.getSecurityString())) {
                  options.add(aAvailable);
                  Collections.sort(options);
               }
            }
         }

         if(!options.isEmpty()) {
            validOptions.add(new MenuRoleOptionDTO(role, (String)roleDescriptions.get(role), options));
         }
      }
      
      /*
       * wykasowanie tabu Admin Access jesli juz jest tab System Aministrator
       */
      boolean deleteRole = false;
      int indexToDelete = -1;
      for (int i=0;i<validOptions.size();i++){
          if (validOptions.get(i).getTitle().equals("SystemAdministrator")){
              deleteRole = true;
          }
          if (validOptions.get(i).getTitle().equals("AdminAccess")){
              indexToDelete = i;
          }
      }
      if (deleteRole && indexToDelete>=0){
          validOptions.remove(indexToDelete); 
      }
      
      myForm.setActions(validOptions);
      return actionMapping.findForward("success");
   }

   static {
	    mAvailable.add(new MenuOptionDTO("loadAddress('adminPanel/', '_blank')", "Admin Application", "Launch the admin application.", "WEB_PROCESS.SystemAdmin"));
	    mAvailable.add(new MenuOptionDTO("loadAddress('propertyList.do')", "System Properties", "Alter the system properties.", "SYSTEM_PROPERTIES_PROCESS.Save"));
	    mAvailable.add(new MenuOptionDTO("loadAddress('assignment.do')", "User Assignments", "Check the User Budget Responsibility Area Assignments.", "WEB_PROCESS.UserAssignments"));
	    mAvailable.add(new MenuOptionDTO("loadAddress('userReport.do')", "User Settings", "Check the User role settings.", "WEB_PROCESS.UserSettings"));
	    mAvailable.add(new MenuOptionDTO("loadAddress('supportTabs.do')", "Support Help", "Perform tasks to help Advanced Business Solutions locate and fix problems.", "WEB_PROCESS.SystemAdmin"));
	    mAvailable.add(new MenuOptionDTO("loadAddress('entityLists.do')", "Diagnostic Lists", "Additional lists for diagnostics.", "WEB_PROCESS.SystemAdmin"));
	    mAvailable.add(new MenuOptionDTO("loadAddress('budgetCycles.do')", "Budget Cycles", "Access a list of budget cycles.", "WEB_PROCESS.BudgetCycles"));
	    mAvailable.add(new MenuOptionDTO("loadAddress('virementsGUI.do', 'virement')", "Budget Transfers", "Maintain budget transfers.", "WEB_PROCESS.Virements"));
	    mAvailable.add(new MenuOptionDTO("loadAddress('virements.do')", "Budget Transfers", "Authorise budget transfers.", "WEB_PROCESS.Virements"));
	    mAvailable.add(new MenuOptionDTO("loadAddress('virementQuery.do')", "Budget Transfers Query", "Query budget transfers.", "WEB_PROCESS.Virements"));
	    mAvailable.add(new MenuOptionDTO("loadAddress('massUpdate.do?passedAction=new')", "Top Down Budgeting", "Apply rule based changes to cells.", "WEB_PROCESS.TopDown"));
	    mAvailable.add(new MenuOptionDTO("loadAddress('budgetLimit.do')", "Budget Limits", "Set / View Budget Limits.", "WEB_PROCESS.BudgetLimits"));
	    mAvailable.add(new MenuOptionDTO("loadAddress('xmlReports.do', 'xmlReport')", "Analysis", "Access the analysis tool.", "WEB_PROCESS.XMLReports"));
	    mAvailable.add(new MenuOptionDTO("loadAddress('tasks.do')", "Tasks", "Show the list of background tasks.", "WEB_PROCESS.Tasks"));
	    mAvailable.add(new MenuOptionDTO("loadAddress('reportDef.do')", "Report Definitions", "Show the list of Report Definitions.", "WEB_PROCESS.ReportDefinition"));
	    mAvailable.add(new MenuOptionDTO("loadAddress('reports.do')", "Task Reports", "Show the list of background task reports.", "WEB_PROCESS.TaskReports"));
	    mAvailable.add(new MenuOptionDTO("loadAddress('logonHistoryList.do')", "Logon History", "Show the logon history.", "WEB_PROCESS.LogonHistory"));
	    mAvailable.add(new MenuOptionDTO("loadAddress('formReport.do')", "Form Report", "Show list of finance forms.", "WEB_PROCESS.FormReport"));
   }
}
