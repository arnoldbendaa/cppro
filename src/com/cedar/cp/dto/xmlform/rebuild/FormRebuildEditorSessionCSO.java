// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:06:13
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.xmlform.rebuild;

import com.cedar.cp.dto.xmlform.rebuild.FormRebuildImpl;
import java.io.Serializable;

public class FormRebuildEditorSessionCSO implements Serializable {

   private int mUserId;
   private FormRebuildImpl mEditorData;


   public FormRebuildEditorSessionCSO(int userId, FormRebuildImpl editorData) {
      this.mUserId = userId;
      this.mEditorData = editorData;
   }

   public FormRebuildImpl getEditorData() {
      return this.mEditorData;
   }

   public int getUserId() {
      return this.mUserId;
   }
}
