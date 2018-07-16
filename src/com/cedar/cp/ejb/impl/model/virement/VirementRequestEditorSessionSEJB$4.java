// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:27:01
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.impl.model.virement;

import com.cedar.cp.ejb.impl.model.virement.VirementRequestEditorSessionSEJB;
import com.cedar.cp.ejb.impl.task.UserMailerOwner;

class VirementRequestEditorSessionSEJB$4 implements UserMailerOwner {

   // $FF: synthetic field
   final VirementRequestEditorSessionSEJB this$0;


   VirementRequestEditorSessionSEJB$4(VirementRequestEditorSessionSEJB var1) {
      this.this$0 = var1;
   }

   public int getUserId() {
      return VirementRequestEditorSessionSEJB.accessMethod000(this.this$0);
   }
}
