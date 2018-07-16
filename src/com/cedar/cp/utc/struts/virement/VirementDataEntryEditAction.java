// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:35:15
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.utc.struts.virement;

import com.cedar.cp.api.base.CPConnection;
import com.cedar.cp.api.base.EntityList;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.model.virement.VirementRequestsProcess;
import com.cedar.cp.utc.picker.ElementDTO;
import com.cedar.cp.utc.struts.virement.VirementBaseAction;
import com.cedar.cp.utc.struts.virement.VirementDataEntryDTO;
import com.cedar.cp.utc.struts.virement.VirementDataEntryDTO$SessionMgr;
import com.cedar.cp.utc.struts.virement.VirementDataEntryForm;
import com.cedar.cp.utc.struts.virement.VirementGroupDTO;
import com.cedar.cp.utc.struts.virement.VirementLineDTO;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;

public class VirementDataEntryEditAction extends VirementBaseAction {

   public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception {
      VirementDataEntryForm form = (VirementDataEntryForm)actionForm;
      HttpSession session = httpServletRequest.getSession();
      VirementDataEntryDTO dto = VirementDataEntryDTO$SessionMgr.load(session, form.getData().getKey());
      if(form.isHasData()) {
         dto.update(form.getData());
      }

      form.setdTO(dto);
      if(form.getData().getModelId() != 0 && form.getData().getFinanceCubeId() != 0 && form.getData().getBudgetCycleId() != 0) {
         form.getData().setDimensionHeaders(this.queryDimensionHeaders(httpServletRequest, form.getData().getModelId()));
         this.queryVirementDataTypes(httpServletRequest, form.getData());
      }

      List var21;
      List var25;
      VirementGroupDTO var24;
      if(form.getUserAction() != null && form.getUserAction().equals("deleteLine")) {
         var21 = (List)form.getData().getGroups();
         var24 = (VirementGroupDTO)var21.get(form.getCurrentGroup());
         var25 = (List)var24.getLines();
         var25.remove(form.getCurrentLine());
         if(var25.isEmpty()) {
            var21.remove(var24);
         }

         form.setUserAction("");
      } else {
         VirementLineDTO var30;
         if(form.getUserAction() != null && form.getUserAction().equalsIgnoreCase("toggleLine")) {
            var21 = (List)form.getData().getGroups();
            var24 = (VirementGroupDTO)var21.get(form.getCurrentGroup());
            var25 = (List)var24.getLines();
            var30 = (VirementLineDTO)var25.get(form.getCurrentLine());
            var30.setTo(!var30.isTo());
            form.setUserAction("");
         } else {
            if(form.getData().getDimensionHeaders() != null && !form.getData().getDimensionHeaders().isEmpty() && !this.validateStructureElements(this.getCPContext(httpServletRequest).getCPConnection(), httpServletRequest, form)) {
               return actionMapping.findForward("success");
            }

            if(form.getUserAction() != null && form.getUserAction().equalsIgnoreCase("showSpreadPage")) {
               form.setUserAction("");
               var21 = (List)form.getData().getGroups();
               var24 = (VirementGroupDTO)var21.get(form.getCurrentGroup());
               var25 = (List)var24.getLines();
               var30 = (VirementLineDTO)var25.get(form.getCurrentLine());
               if(!var30.isSummaryLine()) {
                  return actionMapping.findForward("success");
               }

               VirementDataEntryDTO$SessionMgr.save(session, form.getData());
               httpServletRequest.setAttribute("requestId", form.getData().getKey());
               return actionMapping.findForward("spread");
            }

            Object key;
            if(form.getUserAction() != null && form.getUserAction().equalsIgnoreCase("addBlankLine")) {
               CPConnection var22 = this.getCPContext(httpServletRequest).getCPConnection();
               EntityList var23 = var22.getListHelper().getAllDimensionsForModel(form.getData().getModelId());
               var25 = (List)form.getData().getGroups();
               key = null;
               VirementGroupDTO var28;
               if(form.getCurrentGroup() >= 0) {
                  var28 = (VirementGroupDTO)var25.get(form.getCurrentGroup());
               } else {
                  var28 = new VirementGroupDTO();
                  var25.add(var28);
               }

               List var27 = (List)var28.getLines();
               VirementLineDTO var29 = new VirementLineDTO();

               for(int i = 0; i < var23.getNumRows(); ++i) {
                  ElementDTO element = new ElementDTO();
                  element.setDescription("");
                  element.setIdentifier("");
                  element.setStructureId(0);
                  var29.getCells().add(element);
               }

               var27.add(var29);
               var29.setSummaryLine(true);
               var29.setAllocationThreshold(String.valueOf(this.queryAllocationThreshold(httpServletRequest)));
               form.setUserAction("");
            } else if(form.getUserAction() != null && (form.getUserAction().equalsIgnoreCase("submit") || form.getUserAction().equalsIgnoreCase("save"))) {
               VirementDataEntryDTO vdeDTO = form.getData();
               CPConnection con = this.getCPContext(httpServletRequest).getCPConnection();
               VirementRequestsProcess process = con.getVirementRequestsProcess();
               key = null;

               ActionForward e;
               try {
                  key = this.saveRequest(vdeDTO, httpServletRequest);
                  if(form.getUserAction().equalsIgnoreCase("submit")) {
                     int var26 = process.submitVirementRequest(key);
                     httpServletRequest.setAttribute("TaskId", new Integer(var26));
                     ActionForward var31 = actionMapping.findForward("submitted");
                     return var31;
                  }

                  e = actionMapping.findForward("saved");
               } catch (ValidationException var19) {
                  if(key != null) {
                     form.setData(this.loadRequest(key, httpServletRequest));
                  }

                  ActionErrors errors = new ActionErrors();
                  errors.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("cp.virement.validation.error", var19.getMessage()));
                  this.addErrors(httpServletRequest, errors);
                  return actionMapping.findForward("success");
               } finally {
                  form.setUserAction("");
               }

               return e;
            }
         }
      }

      return actionMapping.findForward("success");
   }
}
