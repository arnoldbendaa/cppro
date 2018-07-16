// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:28:55
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.utc.struts.homepage;

import java.io.Serializable;

public class BudgetInstructionDTO implements Serializable {

   private int id;
   private String identifier;


   public int getId() {
      return this.id;
   }

   public void setId(int id) {
      this.id = id;
   }

   public String getIdentifier() {
      return this.identifier;
   }

   public void setIdentifier(String identifier) {
      this.identifier = identifier;
   }
}
