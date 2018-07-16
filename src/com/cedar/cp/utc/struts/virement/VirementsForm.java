// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:35:16
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.utc.struts.virement;

import com.cedar.cp.utc.common.CPForm;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionMapping;

public class VirementsForm extends CPForm {

   private boolean mIncludeChildRespAreas;
   private String mCurrentTab;
   private String mUserAction;
   private String mRequestId;
   private List mUserVirements;
   private List mAuthorisableVirements;


   public void reset(ActionMapping actionMapping, HttpServletRequest httpServletRequest) {
      super.reset(actionMapping, httpServletRequest);
      this.mIncludeChildRespAreas = false;
      this.mUserAction = null;
      this.mCurrentTab = "0";
   }

   public List getAuthorisableVirements() {
      return this.mAuthorisableVirements;
   }

   public void setAuthorisableVirements(List authorisableVirements) {
      this.mAuthorisableVirements = authorisableVirements;
   }

   public List getUserVirements() {
      return this.mUserVirements;
   }

   public void setUserVirements(List userVirements) {
      this.mUserVirements = userVirements;
   }

   public String getUserAction() {
      return this.mUserAction;
   }

   public void setUserAction(String userAction) {
      this.mUserAction = userAction;
   }

   public String getRequestId() {
      return this.mRequestId;
   }

   public void setRequestId(String requestId) {
      this.mRequestId = requestId;
   }

   public String getCurrentTab() {
      return this.mCurrentTab;
   }

   public void setCurrentTab(String currentTab) {
      this.mCurrentTab = currentTab;
   }

   public int getCurrentTabAsInt() {
      try {
         return Integer.parseInt(this.mCurrentTab);
      } catch (NumberFormatException var2) {
         this.mCurrentTab = "0";
         return 0;
      }
   }

   public boolean isIncludeChildRespAreas() {
      return this.mIncludeChildRespAreas;
   }

   public void setIncludeChildRespAreas(boolean includeChildRespAreas) {
      this.mIncludeChildRespAreas = includeChildRespAreas;
   }
}
