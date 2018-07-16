// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:26:37
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.impl.model.cc;

import com.cedar.cp.api.model.cc.CcDeploymentLine;
import com.cedar.cp.ejb.impl.model.cc.CcDeploymentEditorSessionSEJB;
import java.util.Comparator;

class CcDeploymentEditorSessionSEJB$1 implements Comparator<CcDeploymentLine> {

   // $FF: synthetic field
   final CcDeploymentEditorSessionSEJB this$0;


   CcDeploymentEditorSessionSEJB$1(CcDeploymentEditorSessionSEJB var1) {
      this.this$0 = var1;
   }

   public int compare(CcDeploymentLine o1, CcDeploymentLine o2) {
      return o1.getIndex() - o2.getIndex();
   }
}
