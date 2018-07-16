// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:35:15
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.utc.struts.virement;

import com.cedar.cp.utc.struts.virement.VirementFinanceCubeDTO;
import com.cedar.cp.utc.struts.virement.VirementQueryForm;
import org.apache.commons.collections.Factory;

class VirementQueryForm$1 implements Factory {

   // $FF: synthetic field
   final VirementQueryForm this$0;


   VirementQueryForm$1(VirementQueryForm var1) {
      this.this$0 = var1;
   }

   public Object create() {
      return new VirementFinanceCubeDTO();
   }
}
