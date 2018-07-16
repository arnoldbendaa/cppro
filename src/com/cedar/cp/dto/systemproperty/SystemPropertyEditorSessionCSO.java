// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:06:09
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.systemproperty;

import com.cedar.cp.dto.systemproperty.SystemPropertyImpl;
import java.io.Serializable;

public class SystemPropertyEditorSessionCSO implements Serializable {

   private int mUserId;
   private SystemPropertyImpl mEditorData;


   public SystemPropertyEditorSessionCSO(int userId, SystemPropertyImpl editorData) {
      this.mUserId = userId;
      this.mEditorData = editorData;
   }

   public SystemPropertyImpl getEditorData() {
      return this.mEditorData;
   }

   public int getUserId() {
      return this.mUserId;
   }
}
