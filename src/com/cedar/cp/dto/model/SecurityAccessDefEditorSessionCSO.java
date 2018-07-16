// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:58
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.model;

import com.cedar.cp.dto.model.SecurityAccessDefImpl;
import java.io.Serializable;

public class SecurityAccessDefEditorSessionCSO implements Serializable {

   private int mUserId;
   private SecurityAccessDefImpl mEditorData;


   public SecurityAccessDefEditorSessionCSO(int userId, SecurityAccessDefImpl editorData) {
      this.mUserId = userId;
      this.mEditorData = editorData;
   }

   public SecurityAccessDefImpl getEditorData() {
      return this.mEditorData;
   }

   public int getUserId() {
      return this.mUserId;
   }
}
