// Decompiled by:       Fernflower v0.8.6
// Date:                11.08.2012 20:22:03
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.util;

import com.cedar.cp.util.CPInstallProperties;
import com.cedar.cp.util.CPInstallProperties$OraclePasswordProperty;
import com.cedar.cp.util.CPInstallProperties$OracleServiceProperty;

class CPInstallProperties$EfinPasswordProperty extends CPInstallProperties$OraclePasswordProperty {

   // $FF: synthetic field
   final CPInstallProperties this$0;


   public CPInstallProperties$EfinPasswordProperty(CPInstallProperties var1, String prompt) {
      super(var1, prompt, "efin.db.service", "efin.db.user", "efin.db.schema");
      this.this$0 = var1;
   }

   public boolean checkProperty(String newValue) {
      if(((CPInstallProperties$OracleServiceProperty)this.this$0.getProperty(this.mServiceNameProperty)).getHost() == null) {
         return true;
      } else if(!super.checkProperty(newValue)) {
         return false;
      } else {
         this.this$0.checkSchema(this, newValue, "GL_CONTROLS");
         if(CPInstallProperties.accessMethod400(this.this$0) == null) {
            return true;
         } else if(!CPInstallProperties.accessMethod400(this.this$0).checkCPToFmsLink("efin", "efindatabase", this.this$0.getPropertyValue("efin.db.user"), this.this$0.getPropertyValue("efin.db.password"), this.this$0.getPropertyValue("efin.db.service"), "oracle", this)) {
            return false;
         } else {
            CPInstallProperties.accessMethod400(this.this$0).checkEfinLinkToCP(this);
            return true;
         }
      }
   }
}
