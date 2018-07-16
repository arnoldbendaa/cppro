// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:27:01
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.impl.model.virement;

import com.cedar.cp.ejb.impl.model.virement.VirementRequestEditorSessionSEJB;
import com.cedar.cp.ejb.impl.model.virement.VirementRequestLineEVO;
import java.util.Comparator;

class VirementRequestEditorSessionSEJB$2 implements Comparator<VirementRequestLineEVO> {

   // $FF: synthetic field
   final VirementRequestEditorSessionSEJB this$0;


   VirementRequestEditorSessionSEJB$2(VirementRequestEditorSessionSEJB var1) {
      this.this$0 = var1;
   }

   public int compare(VirementRequestLineEVO o1, VirementRequestLineEVO o2) {
      return o1.getLineIdx() - o2.getLineIdx();
   }
}
