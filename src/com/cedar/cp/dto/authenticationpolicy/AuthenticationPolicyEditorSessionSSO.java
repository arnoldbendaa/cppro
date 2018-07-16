// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:46
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.authenticationpolicy;

import com.cedar.cp.dto.authenticationpolicy.AuthenticationPolicyImpl;
import java.io.Serializable;

public class AuthenticationPolicyEditorSessionSSO implements Serializable {

   private AuthenticationPolicyImpl mEditorData;


   public AuthenticationPolicyEditorSessionSSO() {}

   public AuthenticationPolicyEditorSessionSSO(AuthenticationPolicyImpl paramEditorData) {
      this.mEditorData = paramEditorData;
   }

   public void setEditorData(AuthenticationPolicyImpl paramEditorData) {
      this.mEditorData = paramEditorData;
   }

   public AuthenticationPolicyImpl getEditorData() {
      return this.mEditorData;
   }
}
