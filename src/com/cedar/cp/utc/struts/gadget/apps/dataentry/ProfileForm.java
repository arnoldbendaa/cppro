// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:28:55
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.utc.struts.gadget.apps.dataentry;

import com.cedar.cp.api.base.EntityList;
import com.cedar.cp.utc.struts.gadget.apps.GadgetForm;

public class ProfileForm extends GadgetForm {

   private EntityList mProfiles;


   public EntityList getProfiles() {
      return this.mProfiles;
   }

   public void setProfiles(EntityList profiles) {
      this.mProfiles = profiles;
   }
}
