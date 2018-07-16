// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:35:15
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.utc.struts.virement;

import com.cedar.cp.utc.struts.virement.DataTypeDTO;
import com.cedar.cp.utc.struts.virement.VirementDataEntryDTO;
import org.apache.commons.collections.Factory;

class VirementDataEntryDTO$1 implements Factory {

   // $FF: synthetic field
   final VirementDataEntryDTO this$0;


   VirementDataEntryDTO$1(VirementDataEntryDTO var1) {
      this.this$0 = var1;
   }

   public Object create() {
      return new DataTypeDTO();
   }
}
