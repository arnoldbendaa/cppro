// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:35:15
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.utc.struts.virement;

import com.cedar.cp.api.base.CPConnection;
import com.cedar.cp.api.base.EntityList;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.model.ModelRef;
import com.cedar.cp.api.model.virement.VirementRequestRef;
import com.cedar.cp.api.model.virement.VirementRequestsProcess;
import com.cedar.cp.api.user.UserRef;
import com.cedar.cp.utc.common.CPAction;
import com.cedar.cp.utc.common.CPContext;
import com.cedar.cp.utc.struts.virement.VirementSummaryDTO;
import com.cedar.cp.utc.struts.virement.VirementsForm;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;

public class VirementListSetupAction extends CPAction {

   private static DateFormat sDateFormat;


   public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
      CPContext context = this.getCPContext(request);
      CPConnection connection = context.getCPConnection();
      VirementsForm vForm = (VirementsForm)form;
      VirementRequestsProcess process = connection.getVirementRequestsProcess();
      if(vForm.getUserAction() != null && vForm.getUserAction().equals("delete") && vForm.getRequestId() != null) {
         try {
            Object userVirements = connection.getEntityKeyFactory().getKeyFromTokens(vForm.getRequestId());
            process.deleteObject(userVirements);
         } catch (ValidationException var18) {
            ActionErrors virementsToAuthorise = new ActionErrors();
            virementsToAuthorise.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("cp.virement.validation.error", var18.getMessage()));
            this.addErrors(request, virementsToAuthorise);
         } finally {
            vForm.setUserAction((String)null);
            vForm.setRequestId((String)null);
         }
      } else {
         if(vForm.getUserAction() != null && vForm.getUserAction().equalsIgnoreCase("view")) {
            vForm.setUserAction((String)null);
            return mapping.findForward("view");
         }

         if(vForm.getUserAction() != null && vForm.getUserAction().equalsIgnoreCase("edit")) {
            vForm.setUserAction((String)null);
            return mapping.findForward("edit");
         }

         if(vForm.getUserAction() != null && vForm.getUserAction().equalsIgnoreCase("auth")) {
            vForm.setUserAction((String)null);
            return mapping.findForward("auth");
         }
      }

      ArrayList var20 = new ArrayList();
      ArrayList var21 = new ArrayList();
      EntityList virements = process.queryVirementRequests(vForm.isIncludeChildRespAreas());

      for(int row = 0; row < virements.getNumRows(); ++row) {
         ArrayList authorisers = new ArrayList();

         for(int summaryDTO = 0; summaryDTO < 10; ++summaryDTO) {
            UserRef ownerId = (UserRef)virements.getValueAt(row, "authoriser" + (summaryDTO + 1));
            if(ownerId != null && ownerId.getNarrative() != null) {
               authorisers.add(ownerId);
            }
         }

         VirementSummaryDTO var23 = new VirementSummaryDTO((VirementRequestRef)virements.getValueAt(row, "request"), (UserRef)virements.getValueAt(row, "owner"), authorisers, this.formatDate((Date)virements.getValueAt(row, "createdTime")), this.formatDate((Date)virements.getValueAt(row, "dateSubmitted")), (ModelRef)virements.getValueAt(row, "model"), (String)virements.getValueAt(row, "reason"), (String)virements.getValueAt(row, "reference"), ((Integer)virements.getValueAt(row, "requestId")).intValue(), ((Integer)virements.getValueAt(row, "requestStatus")).intValue());
         int var22 = ((Integer)virements.getValueAt(row, "ownerId")).intValue();
         if(var22 == context.getIntUserId()) {
            var20.add(var23);
         } else {
            var21.add(var23);
         }
      }

      vForm.setAuthorisableVirements(var21);
      vForm.setUserVirements(var20);
      if(vForm.getCurrentTabAsInt() > 1) {
         vForm.setCurrentTab("1");
      }

      if(vForm.getCurrentTabAsInt() > 0 && vForm.getAuthorisableVirements().isEmpty()) {
         vForm.setCurrentTab("0");
      }

      vForm.setUserAction((String)null);
      return mapping.findForward("success");
   }

   private String formatDate(Date date) {
      return date == null?"":this.getDateFormat().format(date);
   }

   private DateFormat getDateFormat() {
      if(sDateFormat == null) {
         sDateFormat = new SimpleDateFormat("dd/MM/yyyy : kk:mm");
      }

      return sDateFormat;
   }
}
