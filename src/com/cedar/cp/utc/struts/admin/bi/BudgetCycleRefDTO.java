// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:28:51
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.utc.struts.admin.bi;

import com.cedar.cp.utc.struts.admin.bi.ModelRefDTO;

public class BudgetCycleRefDTO {

   private ModelRefDTO mParent = new ModelRefDTO();
   private int mId;
   private String mIdentifier;
   private String mDescription;


   public ModelRefDTO getParent() {
      return this.mParent;
   }

   public void setParent(ModelRefDTO parent) {
      this.mParent = parent;
   }

   public int getId() {
      return this.mId;
   }

   public void setId(int id) {
      this.mId = id;
   }

   public String getIdentifier() {
      return this.mIdentifier;
   }

   public void setIdentifier(String identifier) {
      this.mIdentifier = identifier;
   }

   public String getDescription() {
      return this.mDescription;
   }

   public void setDescription(String description) {
      this.mDescription = description;
   }
}
