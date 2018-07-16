// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:50
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.dimension;

import com.cedar.cp.dto.dimension.SecurityRangeImpl;
import java.io.Serializable;

public class SecurityRangeEditorSessionCSO implements Serializable {

   private int mUserId;
   private SecurityRangeImpl mEditorData;


   public SecurityRangeEditorSessionCSO(int userId, SecurityRangeImpl editorData) {
      this.mUserId = userId;
      this.mEditorData = editorData;
   }

   public SecurityRangeImpl getEditorData() {
      return this.mEditorData;
   }

   public int getUserId() {
      return this.mUserId;
   }
}
