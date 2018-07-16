// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 18:23:03
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.tc.apps.metadataimpexp.util.digester.lookuptable;

import com.cedar.cp.tc.apps.metadataimpexp.util.digester.CommonElement;

public class ImpExpColumn extends CommonElement {

   private String mName;
   private String mIndex;
   private String mTitle;
   private int mType;
   private int mSize;
   private int mDp;
   private boolean mOptional;


   public String getName() {
      return this.mName;
   }

   public void setName(String name) {
      this.mName = name;
   }

   public String getIndex() {
      return this.mIndex;
   }

   public void setIndex(String index) {
      this.mIndex = index;
   }

   public String getTitle() {
      return this.mTitle;
   }

   public void setTitle(String title) {
      this.mTitle = title;
   }

   public int getType() {
      return this.mType;
   }

   public void setType(int type) {
      this.mType = type;
   }

   public int getSize() {
      return this.mSize;
   }

   public void setSize(int size) {
      this.mSize = size;
   }

   public int getDp() {
      return this.mDp;
   }

   public void setDp(int dp) {
      this.mDp = dp;
   }

   public boolean getOptional() {
      return this.mOptional;
   }

   public void setOptional(boolean optional) {
      this.mOptional = optional;
   }

   public String toXML() {
      StringBuffer strBuf = new StringBuffer();
      strBuf.append("<column>");
      strBuf.append("<name>").append(this.mName).append("</name>");
      strBuf.append("<index>").append(this.mIndex).append("</index>");
      strBuf.append("<title>").append(this.mTitle).append("</title>");
      strBuf.append("<type>").append(this.mType).append("</type>");
      strBuf.append("<size>").append(this.mSize).append("</size>");
      strBuf.append("<dp>").append(this.mDp).append("</dp>");
      strBuf.append("<optional>").append(this.mOptional).append("</optional>");
      strBuf.append("</column>");
      return strBuf.toString();
   }
}
