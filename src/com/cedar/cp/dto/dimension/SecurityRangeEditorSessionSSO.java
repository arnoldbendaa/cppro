// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:50
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.dimension;

import com.cedar.cp.dto.dimension.SecurityRangeImpl;
import java.io.Serializable;

public class SecurityRangeEditorSessionSSO implements Serializable {

   private SecurityRangeImpl mEditorData;


   public SecurityRangeEditorSessionSSO() {}

   public SecurityRangeEditorSessionSSO(SecurityRangeImpl paramEditorData) {
      this.mEditorData = paramEditorData;
   }

   public void setEditorData(SecurityRangeImpl paramEditorData) {
      this.mEditorData = paramEditorData;
   }

   public SecurityRangeImpl getEditorData() {
      return this.mEditorData;
   }
}
