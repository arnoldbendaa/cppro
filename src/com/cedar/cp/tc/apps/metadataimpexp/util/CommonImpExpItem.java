// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 18:23:03
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.tc.apps.metadataimpexp.util;


public class CommonImpExpItem {

   public static final int TREENODE_TYPE_FOLDER = 0;
   public static final int TREENODE_TYPE_LOOKUP_TABLE = 1;
   public static final int TREENODE_TYPE_XML_FORM = 2;
   public static final int TREENODE_TYPE_FINANCE_CUBE = 3;
   private int mId;
   private String mItemName = "";
   private boolean mIsSelected = false;
   private String mDescription = "";
   private boolean autoSubmit = false;
   private int treeNodeType = 0;
   private boolean isIgnore = false;
   private boolean isOverwrite = false;
   private boolean hasAlternativeName = false;
   private String alternativeName = null;
   private String importFileName = null;
   private boolean hasError = false;
   private String errorMsg = null;


   public String getErrorMsg() {
      return this.errorMsg;
   }

   public void setErrorMsg(String errorMsg) {
      this.errorMsg = errorMsg;
   }

   public boolean hasError() {
      return this.hasError;
   }

   public void setHasError(boolean hasError) {
      this.hasError = hasError;
   }

   public boolean isAutoSubmit() {
      return this.autoSubmit;
   }

   public void setAutoSubmit(boolean autoSubmit) {
      this.autoSubmit = autoSubmit;
   }

   public String getImportFileName() {
      return this.importFileName;
   }

   public void setImportFileName(String importFileName) {
      this.importFileName = importFileName;
   }

   public String getAlternativeName() {
      return this.alternativeName;
   }

   public void setAlternativeName(String alternativeName) {
      this.alternativeName = alternativeName;
   }

   public int getTreeNodeType() {
      return this.treeNodeType;
   }

   public void setTreeNodeType(int type) {
      this.treeNodeType = type;
   }

   public int getId() {
      return this.mId;
   }

   public void setId(int id) {
      this.mId = id;
   }

   public String getItemName() {
      return this.mItemName;
   }

   public void setItemName(String itemName) {
      this.mItemName = itemName;
   }

   public boolean isSelected() {
      return this.mIsSelected;
   }

   public void setSelected(boolean isSelected) {
      this.mIsSelected = isSelected;
   }

   public String toString() {
      String displayName = this.getItemName();
      if(this.getDescription() != null && this.getDescription().trim().length() > 0) {
         displayName = displayName + " - " + this.getDescription();
      }

      return displayName;
   }

   public int hashCode() {
      return this.mItemName != null && this.mItemName.trim().length() > 0?this.mItemName.hashCode():0;
   }

   public String getDescription() {
      return this.mDescription;
   }

   public void setDescription(String description) {
      this.mDescription = description;
   }

   public boolean isIgnore() {
      return this.isIgnore;
   }

   public void setIgnore(boolean isIgnore) {
      this.isIgnore = isIgnore;
   }

   public boolean isOverwrite() {
      return this.isOverwrite;
   }

   public void setOverwrite(boolean isOverwrite) {
      this.isOverwrite = isOverwrite;
   }

   public boolean hasAlternativeName() {
      return this.hasAlternativeName;
   }

   public void setHasAlternativeName(boolean hasAlternativeName) {
      this.hasAlternativeName = hasAlternativeName;
   }

   public boolean equals(Object object) {
      if(object != null && object instanceof CommonImpExpItem) {
         CommonImpExpItem commonObj = (CommonImpExpItem)object;
         if(this.mItemName.equals(commonObj.getItemName()) && this.treeNodeType == commonObj.getTreeNodeType()) {
            return true;
         }
      }

      return false;
   }
}
