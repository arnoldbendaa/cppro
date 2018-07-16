// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:28:53
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.utc.struts.approver;

import com.cedar.cp.api.base.CPConnection;
import com.cedar.cp.api.base.EntityList;
import com.cedar.cp.utc.common.CPContext;
import com.cedar.cp.utc.picker.DimensionDTO;
import com.cedar.cp.utc.struts.approver.ChangeBudgetCycleForm;
import com.cedar.cp.utc.struts.helper.ListHelper;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

public class BudgetViolationConfirmForm extends ChangeBudgetCycleForm {

   private String mViolationAction;
   private String mReason;
   private List mViolationHeading;
   private List mViolations;
   public static final String sVIOLATION_KEY = "BudgetViolations";
   public static final String sCHECK_KEY = "BudgetLimitCheck";
   public static final String sVIOLATION_HEADING = "BudgetViolationHeadings";


   public String getViolationAction() {
      return this.mViolationAction;
   }

   public void setViolationAction(String violationAction) {
      this.mViolationAction = violationAction;
   }

   public String getReason() {
      return this.mReason;
   }

   public void setReason(String reason) {
      this.mReason = reason;
   }

   public List getViolations() {
      return this.mViolations;
   }

   public void setViolations(List violations) {
      this.mViolations = violations;
   }

   public List getViolationHeading() {
      return this.mViolationHeading;
   }

   public void setViolationHeading(List violationHeading) {
      this.mViolationHeading = violationHeading;
   }

   public void reset(ActionMapping actionMapping, HttpServletRequest httpServletRequest) {}

   public ActionErrors validate(ActionMapping actionMapping, HttpServletRequest httpServletRequest) {
      ActionErrors errors = new ActionErrors();
      String s = this.getViolationAction();
      if(s == null) {
         s = "";
      }

      if(s.equals("Ok")) {
         this.checkForEmpty("reason", "reason", this.mReason, errors);
      }

      if(errors.size() > 0) {
         this.populateLists(httpServletRequest);
      }

      return errors;
   }

   public void populateLists(HttpServletRequest httpServletRequest) {
      EntityList list = null;
      boolean size = false;
      CPContext cntx = this.getCPContext(httpServletRequest);
      CPConnection conn = null;
      List violations = (List)httpServletRequest.getSession().getAttribute("BudgetViolations");
      conn = cntx.getCPConnection();
      ArrayList headers = new ArrayList();
      list = conn.getListHelper().getAllDimensionsForModel(this.getModelId());
      int var10 = list.getNumRows();
      DimensionDTO dimDTO = null;

      for(int i = 1; i < var10; ++i) {
         dimDTO = new DimensionDTO();
         dimDTO.setDimensionType(((Integer)list.getValueAt(i, "Type")).intValue());
         dimDTO.setId(((Integer)list.getValueAt(i, "DimensionId")).intValue());
         dimDTO.setIdentifier((String)list.getValueAt(i, "VisId"));
         dimDTO.setDescription((String)list.getValueAt(i, "Description"));
         headers.add(dimDTO);
      }

      this.setViolationHeading(headers);
      httpServletRequest.getSession().setAttribute("BudgetViolationHeadings", list);
      this.setViolations(ListHelper.getListOfLimitsFromList(violations));
   }
}
