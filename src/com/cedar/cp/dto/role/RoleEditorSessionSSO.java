// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:06:09
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.role;

import com.cedar.cp.dto.role.RoleImpl;
import java.io.Serializable;

public class RoleEditorSessionSSO implements Serializable {

   private RoleImpl mEditorData;


   public RoleEditorSessionSSO() {}

   public RoleEditorSessionSSO(RoleImpl paramEditorData) {
      this.mEditorData = paramEditorData;
   }

   public void setEditorData(RoleImpl paramEditorData) {
      this.mEditorData = paramEditorData;
   }

   public RoleImpl getEditorData() {
      return this.mEditorData;
   }
}
