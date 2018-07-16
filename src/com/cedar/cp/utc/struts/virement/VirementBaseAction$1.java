// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:35:15
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.utc.struts.virement;

import com.cedar.cp.utc.struts.virement.DataTypeDTO;
import com.cedar.cp.utc.struts.virement.VirementBaseAction;
import java.util.Comparator;

class VirementBaseAction$1 implements Comparator<DataTypeDTO> {

   // $FF: synthetic field
   final VirementBaseAction this$0;


   VirementBaseAction$1(VirementBaseAction var1) {
      this.this$0 = var1;
   }

   public int compare(DataTypeDTO o1, DataTypeDTO o2) {
      return o1.getNarrative().compareTo(o2.getNarrative());
   }
}
