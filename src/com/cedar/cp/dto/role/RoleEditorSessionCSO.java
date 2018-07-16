// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:06:09
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.role;

import com.cedar.cp.dto.role.RoleImpl;
import java.io.Serializable;

public class RoleEditorSessionCSO implements Serializable {

   private int mUserId;
   private RoleImpl mEditorData;


   public RoleEditorSessionCSO(int userId, RoleImpl editorData) {
      this.mUserId = userId;
      this.mEditorData = editorData;
   }

   public RoleImpl getEditorData() {
      return this.mEditorData;
   }

   public int getUserId() {
      return this.mUserId;
   }
}
