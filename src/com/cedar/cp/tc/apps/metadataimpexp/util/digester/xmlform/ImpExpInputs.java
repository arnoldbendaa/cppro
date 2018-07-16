// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 18:23:03
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.tc.apps.metadataimpexp.util.digester.xmlform;

import com.cedar.cp.tc.apps.metadataimpexp.util.digester.CommonElement;
import com.cedar.cp.tc.apps.metadataimpexp.util.digester.xmlform.CommonInput;
import com.cedar.cp.tc.apps.metadataimpexp.util.digester.xmlform.FinanceCubeInput;
import java.util.ArrayList;
import java.util.List;

public class ImpExpInputs extends CommonElement {

   private List<CommonInput> mInputs = new ArrayList();


   public void addInput(CommonInput input) {
      this.mInputs.add(input);
   }

   public List<CommonInput> getInputs() {
      return this.mInputs;
   }

   public FinanceCubeInput getFinanceCubeInput() {
      if(this.mInputs == null) {
         return null;
      } else {
         for(int i = 0; i < this.mInputs.size(); ++i) {
            Object o = this.mInputs.get(i);
            if(o instanceof FinanceCubeInput) {
               return (FinanceCubeInput)o;
            }
         }

         return null;
      }
   }
}
