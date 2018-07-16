// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:28:51
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.utc.struts.admin;

import com.cedar.cp.utc.struts.admin.MenuOptionDTO;
import java.util.List;

public class MenuRoleOptionDTO {

   private String mTitle;
   private String mDescription;
   private List<MenuOptionDTO> mOptions;


   public MenuRoleOptionDTO(String title, String description, List<MenuOptionDTO> options) {
      this.mTitle = title;
      this.mDescription = description;
      this.mOptions = options;
   }

   public String getLabel() {
      return this.mTitle + " - " + this.mDescription;
   }

   public List<MenuOptionDTO> getOptions() {
      return this.mOptions;
   }

   public String toString() {
      return this.getLabel();
   }

   public String getTitle() {
      return this.mTitle;
   }

   public void setTitle(String title) {
      this.mTitle = title;
   }

   public String getDescription() {
      return this.mDescription;
   }

   public void setDescription(String description) {
      this.mDescription = description;
   }
}
