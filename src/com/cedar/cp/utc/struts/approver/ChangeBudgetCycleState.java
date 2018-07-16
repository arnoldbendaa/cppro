// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:28:54
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.utc.struts.approver;

//import com.adv.stats.perf.J2EEPerformanceLogger;
import com.cedar.cp.api.base.BudgetStatusException;
import com.cedar.cp.api.base.EntityList;
import com.cedar.cp.api.model.BudgetCyclesProcess;
import com.cedar.cp.api.model.ChangeBudgetStateResult;
import com.cedar.cp.api.model.budgetlimit.BudgetLimitCheck;
import com.cedar.cp.utc.common.CPAction;
import com.cedar.cp.utc.common.CPContext;
import com.cedar.cp.utc.message.CPSystemMessage;
import com.cedar.cp.utc.struts.approver.ChangeBudgetCycleForm;
import com.cedar.cp.utc.struts.helper.ListHelper;
import com.cedar.cp.utc.struts.message.MessageDTO;
import com.cedar.cp.utc.struts.topdown.LimitsDTO;
import com.cedar.cp.util.Timer;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;

public class ChangeBudgetCycleState extends CPAction {

   public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
      return mapping.findForward(this.doStatusChange(form, request));
   }

   protected BudgetCyclesProcess getProcess(CPContext conext) {
      return conext.getCPConnection().getBudgetCyclesProcess();
   }

   protected int getUserId(CPContext context) {
      return context.getCPConnection().getUserContext().getUserId();
   }

   protected String doStatusChange(ActionForm form, HttpServletRequest request) throws Exception {
      if(!(form instanceof ChangeBudgetCycleForm)) {
         return null;
      } else {
         Timer timer = new Timer();
         timer.start();
         ChangeBudgetCycleForm myForm = (ChangeBudgetCycleForm)form;
         CPContext ctxt = this.getCPContext(request);
         BudgetCyclesProcess process = this.getProcess(ctxt);
         int userId = this.getUserId(ctxt);
         BudgetLimitCheck check = (BudgetLimitCheck)request.getSession().getAttribute("BudgetLimitCheck");
         if(check == null) {
            check = new BudgetLimitCheck();
         }

         ChangeBudgetStateResult result;
         try {
            result = process.setBudgetState(myForm.getModelId(), userId, myForm.getBcId(), myForm.getSeId(), myForm.getState(), check, myForm.getFromState(), myForm.getToState());
         } catch (BudgetStatusException var29) {
            result = var29.getResult();
         }

         myForm.setChangeStateResult(result);
         if(result.isValid()) {
            if(this.getCPSystemProperties(request).isStatusAlert()) {
               try {
                  boolean errors = false;
                  boolean error = false;
                  boolean reject = false;
                  EntityList list = null;
                  int i;
                  if(myForm.getState() == 3) {
                     errors = true;
                     error = true;
                     boolean name = false;
                     int id = myForm.getStructureId();
                     int email_to_add = myForm.getSeId();

                     while(!name) {
                        list = ctxt.getCPConnection().getListHelper().getStructureElementForIds(id, email_to_add);
                        i = ((Integer)list.getValueAt(0, "ParentId")).intValue();
                        int dto = ((Integer)list.getValueAt(0, "Depth")).intValue();
                        list = ctxt.getCPConnection().getListHelper().getBudgetUsersForNode(myForm.getModelId(), i);
                        if(list.getNumRows() <= 0 && dto != 0) {
                           email_to_add = i;
                        } else {
                           name = true;
                        }
                     }
                  } else if(myForm.getState() == 2) {
                     errors = true;
                     reject = true;
                     list = ctxt.getCPConnection().getListHelper().getBudgetUsersForNode(myForm.getModelId(), myForm.getSeId());
                  }

                  if(errors) {
                     int noOfUsers = list.getNumRows();

                     for(i = 0; i < noOfUsers; ++i) {
                        String var33 = (String)list.getValueAt(i, "Name");
                        String var34 = (String)list.getValueAt(i, "FullName");
                        String var35 = (String)list.getValueAt(i, "EMailAddress");
                        MessageDTO var36 = new MessageDTO();
                        var36.setToUser(var33);
                        var36.setToUser_VisID(var33);
                        var36.setToUser(var34);
                        var36.setToUserEmailAddress(var35);
                        var36.setFromUserEmailAddress(this.getCPSystemProperties(request).getStatusAlertAddress());
                        var36.setMessageType(this.getCPSystemProperties(request).getIntStatusAlertMessageType());
                        var36.setFromUser("System");
                        var36.setFromUser_VisID("System");
                        EntityList strucList = ctxt.getCPConnection().getListHelper().getStructureElement(myForm.getSeId());
                        String budgetLocationName = strucList.getValueAt(0, "VisId") + " / " + strucList.getValueAt(0, "Description");
                        StringBuffer content = new StringBuffer(400);
                        if(var36.isHTML()) {
                           content.append("<pre>");
                        }

                        content.append("You have had ");
                        if(reject) {
                           var36.setSubject("Auto alert of rejected budget (" + budgetLocationName + ")");
                           content.append("your figures rejected ");
                        } else if(error) {
                           var36.setSubject("Auto alert of submitted budget (" + budgetLocationName + ")");
                           content.append("figures submitted ");
                        }

                        content.append("for the following");
                        content.append("\n");
                        content.append("\n");
                        content.append("\tModel : ");
                        String modelVisId = myForm.getSubmitModelName();
                        EntityList modelDetail = ctxt.getCPConnection().getModelsProcess().getModelDetailsWeb(myForm.getModelId());
                        if(modelDetail != null) {
                           content.append(modelVisId);
                           content.append("-");
                           content.append(modelDetail.getValueAt(0, "Description"));
                        }

                        content.append("\n");
                        content.append("\tBudgetCycle : ");
                        content.append(myForm.getSubmitCycleName());
                        content.append("\n");
                        content.append("\tBudget Location : ");
                        content.append(budgetLocationName);
                        content.append("\n");
                        content.append("\n");
                        content.append("by user ");
                        content.append(ctxt.getUserName());
                        String reason = check.getOriginalReason();
                        if(reason == null) {
                           reason = "";
                        }

                        if(!reason.equals("")) {
                           if(check.isForceMessage()) {
                              content.append("\n");
                              content.append("\n");
                              content.append("User Message : ");
                              content.append(reason);
                              content.append("\n");
                           } else if(check.getViolations() != null) {
                              content.append("\n");
                              content.append("\n");
                              content.append("Violations were ignored because : ");
                              content.append(reason);
                              content.append("\n");
                              List system_sender = ListHelper.getListOfLimitsFromList(check.getViolations());
                              Iterator iter = system_sender.iterator();

                              while(iter.hasNext()) {
                                 LimitsDTO violation = (LimitsDTO)iter.next();
                                 content.append("\t BudgetLimit Max : ");
                                 content.append(violation.getMaxString());
                                 content.append("\t Min : ");
                                 content.append(violation.getMinString());
                                 content.append("\n");
                                 content.append("\t Location   : ");
                                 content.append(violation.getRespArea());
                                 content.append("\n");
                                 content.append("\t Account    : ");
                                 content.append(violation.getAccount());
                                 content.append("\n");
                                 if(!violation.getOtherDims().equals("")) {
                                    content.append("\t Other Dims : ");
                                    content.append(violation.getOtherDims());
                                    content.append("\n");
                                 }

                                 content.append("\t Calendar   : ");
                                 content.append(violation.getCal());
                                 content.append("\n");
                                 content.append("\t DataType   : ");
                                 content.append(violation.getDataType());
                                 content.append("\n");
                                 content.append(" Budget Amount submitted : ");
                                 content.append(violation.getCurrentValue());
                                 content.append("\n");
                              }
                           }
                        }

                        content.append("\n");
                        if(var36.isHTML()) {
                           content.append("</pre>");
                        }

                        content.append("\n");
                        content.append(this.createLinkBack(this.getCPSystemProperties(request).getRootUrl(), myForm, var36));
                        var36.setContent(content.toString());
                        if(var36.getMessageType() == 0 || var36.getMessageType() == 2) {
                           CPSystemMessage var37 = new CPSystemMessage(ctxt);
                           var37.send(var36, false);
                        }
                     }
                  }
               } catch (Exception var30) {
                  var30.printStackTrace();
               }
            }

//            (new J2EEPerformanceLogger()).log(ctxt.getUserId(), "CP", "ChangeBudgetStatus", 0, timer.stop(), System.currentTimeMillis());
         } else {
            if(!result.getBudgetLimitViolations().isEmpty()) {
               myForm.setStructureElementId(myForm.getSeId());
               request.getSession().setAttribute("BudgetViolations", result.getBudgetLimitViolations());
               return "limitViolation";
            }

            if(result.isForceMessage()) {
               myForm.setStructureElementId(myForm.getSeId());
               return "statusMessage";
            }

            ActionErrors var31 = new ActionErrors();
            ActionMessage var32 = new ActionMessage("cp.approver.status.error", result.getMessage());
            var31.add("error.submit", var32);
            request.setAttribute("org.apache.struts.action.ERROR", var31);
         }

         request.getSession().removeAttribute("BudgetLimitCheck");
         return "approve";
      }
   }

   private String createLinkBack(String root, ChangeBudgetCycleForm form, MessageDTO message) throws Exception {
      StringBuffer link = new StringBuffer();
      if(message.isHTML()) {
         link.append("<a target=\"_blank\" href=\"");
      }

      link.append(root);
      link.append("/reviewBudget.do?");
      link.append("submitModelName=");
      link.append(URLEncoder.encode(form.getSubmitModelName(), "UTF-8"));
      link.append("&submitCycleName=");
      link.append(URLEncoder.encode(form.getSubmitCycleName(), "UTF-8"));
      link.append("&modelId=");
      link.append(form.getModelId());
      link.append("&budgetCycleId=");
      link.append(form.getBudgetCycleId());
      link.append("&structureId=");
      link.append(form.getStructureId());
      link.append("&structureElementId=");
      link.append(form.getSeId());
      link.append("&selectedStructureElementId=");
      link.append(form.getSeId());
      if(message.isHTML()) {
         link.append("\">Review Budget</a>");
      }

      return link.toString();
   }
}
