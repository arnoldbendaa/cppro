// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:16
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.impl.model.udwp;

import com.cedar.cp.api.datatype.DataTypeNode;
import com.cedar.cp.impl.model.udwp.WeightingDeploymentEditorImpl;
import java.util.Comparator;

class WeightingDeploymentEditorImpl$1 implements Comparator {

   // $FF: synthetic field
   final WeightingDeploymentEditorImpl this$0;


   WeightingDeploymentEditorImpl$1(WeightingDeploymentEditorImpl var1) {
      this.this$0 = var1;
   }

   public int compare(Object o1, Object o2) {
      if(o1 instanceof DataTypeNode) {
         o1 = WeightingDeploymentEditorImpl.accessMethod000(this.this$0, o1);
      }

      if(o2 instanceof DataTypeNode) {
         o2 = WeightingDeploymentEditorImpl.accessMethod000(this.this$0, o2);
      }

      return o1 != null && o2 != null && o1.equals(o2)?0:1;
   }
}
