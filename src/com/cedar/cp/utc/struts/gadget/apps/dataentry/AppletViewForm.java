// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:28:55
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.utc.struts.gadget.apps.dataentry;

import com.cedar.cp.utc.struts.gadget.apps.GadgetForm;

public class AppletViewForm extends GadgetForm {

   String mNoDecoration;
   String mProfileRef;
   String mProfileVisId;


   public String getNoDecoration() {
      return this.mNoDecoration;
   }

   public void setNoDecoration(boolean noDecoration) {
      if(noDecoration) {
         this.setNoDecoration("true");
      }

   }

   public void setNoDecoration(String noDecoration) {
      this.mNoDecoration = noDecoration;
   }

   public String getProfileRef() {
      return this.mProfileRef == null?"":this.mProfileRef;
   }

   public void setProfileRef(String profileRef) {
      this.mProfileRef = profileRef;
   }

   public String getProfileVisId() {
      return this.mProfileVisId;
   }

   public void setProfileVisId(String profileVisId) {
      this.mProfileVisId = profileVisId;
   }
}
