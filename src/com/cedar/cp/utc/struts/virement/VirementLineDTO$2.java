// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:35:15
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.utc.struts.virement;

import com.cedar.cp.utc.struts.virement.DataTypeDTO;
import com.cedar.cp.utc.struts.virement.VirementLineDTO;
import org.apache.commons.collections.Factory;

class VirementLineDTO$2 implements Factory {

   // $FF: synthetic field
   final VirementLineDTO this$0;


   VirementLineDTO$2(VirementLineDTO var1) {
      this.this$0 = var1;
   }

   public Object create() {
      return new DataTypeDTO();
   }
}
