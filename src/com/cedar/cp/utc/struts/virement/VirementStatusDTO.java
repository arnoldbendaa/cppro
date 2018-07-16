// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:35:16
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.utc.struts.virement;

import java.io.Serializable;

public class VirementStatusDTO implements Serializable {

   private int mId;
   private String mName;


   public VirementStatusDTO(int id, String name) {
      this.mId = id;
      this.mName = name;
   }

   public int getId() {
      return this.mId;
   }

   public String getName() {
      return this.mName;
   }
}
