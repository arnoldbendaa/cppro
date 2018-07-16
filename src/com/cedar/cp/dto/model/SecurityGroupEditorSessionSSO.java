// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:58
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.model;

import com.cedar.cp.dto.model.SecurityGroupImpl;
import java.io.Serializable;

public class SecurityGroupEditorSessionSSO implements Serializable {

   private SecurityGroupImpl mEditorData;


   public SecurityGroupEditorSessionSSO() {}

   public SecurityGroupEditorSessionSSO(SecurityGroupImpl paramEditorData) {
      this.mEditorData = paramEditorData;
   }

   public void setEditorData(SecurityGroupImpl paramEditorData) {
      this.mEditorData = paramEditorData;
   }

   public SecurityGroupImpl getEditorData() {
      return this.mEditorData;
   }
}
