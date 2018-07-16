// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:06:02
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.model.ra;

import com.cedar.cp.dto.model.ra.ResponsibilityAreaImpl;
import java.io.Serializable;

public class ResponsibilityAreaEditorSessionCSO implements Serializable {

   private int mUserId;
   private ResponsibilityAreaImpl mEditorData;


   public ResponsibilityAreaEditorSessionCSO(int userId, ResponsibilityAreaImpl editorData) {
      this.mUserId = userId;
      this.mEditorData = editorData;
   }

   public ResponsibilityAreaImpl getEditorData() {
      return this.mEditorData;
   }

   public int getUserId() {
      return this.mUserId;
   }
}
