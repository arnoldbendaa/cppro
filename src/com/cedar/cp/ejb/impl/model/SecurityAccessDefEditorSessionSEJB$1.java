// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:26:28
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.impl.model;

import com.cedar.cp.dto.model.SecurityAccessParserListener;
import com.cedar.cp.ejb.impl.model.SecurityAccessDefEditorSessionSEJB;
import java.text.ParseException;
import java.util.Set;

class SecurityAccessDefEditorSessionSEJB$1 implements SecurityAccessParserListener {

   // $FF: synthetic field
   final Set val$usrRangeSet;
   // $FF: synthetic field
   final SecurityAccessDefEditorSessionSEJB this$0;


   SecurityAccessDefEditorSessionSEJB$1(SecurityAccessDefEditorSessionSEJB var1, Set var2) {
      this.this$0 = var1;
      this.val$usrRangeSet = var2;
   }

   public void registerRangeRefernce(String rangeName) throws ParseException {
      this.val$usrRangeSet.add(rangeName);
   }
}
