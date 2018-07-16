// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:35:14
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.utc.struts.virement;

import com.cedar.cp.api.user.UserRef;

public class UserDTO {

   private String mKey;
   private String mNarrative;


   public UserDTO() {}

   public UserDTO(UserRef userRef) {
      if(userRef != null) {
         this.mKey = userRef.getTokenizedKey();
         this.mNarrative = userRef.getNarrative();
      }

   }

   public String getKey() {
      return this.mKey;
   }

   public void setKey(String key) {
      this.mKey = key;
   }

   public String getNarrative() {
      return this.mNarrative;
   }

   public void setNarrative(String narrative) {
      this.mNarrative = narrative;
   }
}
