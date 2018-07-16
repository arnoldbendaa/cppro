// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:53
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.extsys;

import com.cedar.cp.dto.extsys.ExternalSystemImpl;
import java.io.Serializable;

public class ExternalSystemEditorSessionCSO implements Serializable {

   private int mUserId;
   private ExternalSystemImpl mEditorData;


   public ExternalSystemEditorSessionCSO(int userId, ExternalSystemImpl editorData) {
      this.mUserId = userId;
      this.mEditorData = editorData;
   }

   public ExternalSystemImpl getEditorData() {
      return this.mEditorData;
   }

   public int getUserId() {
      return this.mUserId;
   }
}
