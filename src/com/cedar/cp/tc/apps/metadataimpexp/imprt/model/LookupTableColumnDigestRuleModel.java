// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 18:23:02
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.tc.apps.metadataimpexp.imprt.model;


public class LookupTableColumnDigestRuleModel {

   private String name = null;
   private int index = 0;
   private String title = null;
   private int type = 0;
   private int size = 0;
   private int dp = 0;
   private boolean optional = false;


   public String getName() {
      return this.name;
   }

   public void setName(String name) {
      this.name = name;
   }

   public int getIndex() {
      return this.index;
   }

   public void setIndex(int index) {
      this.index = index;
   }

   public String getTitle() {
      return this.title;
   }

   public void setTitle(String title) {
      this.title = title;
   }

   public int getType() {
      return this.type;
   }

   public void setType(int type) {
      this.type = type;
   }

   public int getSize() {
      return this.size;
   }

   public void setSize(int size) {
      this.size = size;
   }

   public int getDp() {
      return this.dp;
   }

   public void setDp(int dp) {
      this.dp = dp;
   }

   public boolean isOptional() {
      return this.optional;
   }

   public void setOptional(boolean optional) {
      this.optional = optional;
   }
}
