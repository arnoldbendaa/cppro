// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:06:11
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.user;

import com.cedar.cp.dto.user.UserImpl;
import java.io.Serializable;

public class UserEditorSessionSSO implements Serializable {

   private UserImpl mEditorData;


   public UserEditorSessionSSO() {}

   public UserEditorSessionSSO(UserImpl paramEditorData) {
      this.mEditorData = paramEditorData;
   }

   public void setEditorData(UserImpl paramEditorData) {
      this.mEditorData = paramEditorData;
   }

   public UserImpl getEditorData() {
      return this.mEditorData;
   }
}
