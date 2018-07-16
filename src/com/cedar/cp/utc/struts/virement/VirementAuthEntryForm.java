// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:35:15
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.utc.struts.virement;

import com.cedar.cp.utc.struts.virement.VirementDataEntryForm;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionMapping;

public class VirementAuthEntryForm extends VirementDataEntryForm {

   private String mParentPage;
   private String mCurrentAuthPointKey;


   public void reset(ActionMapping actionMapping, HttpServletRequest httpServletRequest) {
      super.reset(actionMapping, httpServletRequest);
      this.mCurrentAuthPointKey = null;
   }

   public String getCurrentAuthPointKey() {
      return this.mCurrentAuthPointKey;
   }

   public void setCurrentAuthPointKey(String currentAuthPointKey) {
      this.mCurrentAuthPointKey = currentAuthPointKey;
   }

   public String getParentPage() {
      return this.mParentPage;
   }

   public void setParentPage(String parentPage) {
      this.mParentPage = parentPage;
   }
}
