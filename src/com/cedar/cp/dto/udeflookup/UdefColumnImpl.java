// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:06:10
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.udeflookup;

import com.cedar.cp.api.udeflookup.UdefColumn;
import java.io.Serializable;

public class UdefColumnImpl implements UdefColumn, Comparable, Serializable {

   private Object mKey;
   private int mIndex;
   private String mName;
   private String mColumnName;
   private String mTitle;
   private int mType;
   private Integer mSize;
   private Integer mDP;
   private boolean mOptional;
   private int mState;


   public Object getKey() {
      return this.mKey;
   }

   public void setKey(Object key) {
      this.mKey = key;
   }

   public Integer getIndex() {
      return Integer.valueOf(this.mIndex);
   }

   public void setIndex(int index) {
      this.mIndex = index;
   }

   public String getName() {
      return this.mName;
   }

   public void setName(String name) {
      this.mName = name;
   }

   public String getColumnName() {
      if(this.mColumnName == null) {
         StringBuilder sb = new StringBuilder();
         if(this.getName() != null) {
            sb.append("X_").append(this.getName());
         }

         this.setColumnName(sb.toString());
      }

      return this.mColumnName;
   }

   public void setColumnName(String columnName) {
      this.mColumnName = columnName;
   }

   public String getTitle() {
      return this.mTitle != null && this.mTitle.length() != 0?this.mTitle:this.getName();
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

   public Integer getSize() {
      return this.mSize;
   }

   public void setSize(Integer size) {
      this.mSize = size;
   }

   public Integer getDP() {
      return this.mDP;
   }

   public void setDP(Integer DP) {
      this.mDP = DP;
   }

   public boolean isOptional() {
      return this.mOptional;
   }

   public void setOptional(boolean optional) {
      this.mOptional = optional;
   }

   public int getState() {
      return this.mState;
   }

   public void setState(int state) {
      this.mState = state;
   }

   public int compareTo(Object o) {
      UdefColumnImpl comp = (UdefColumnImpl)o;
      return comp.getIndex() == null?0:(this.getIndex() == null?1:this.getIndex().compareTo(comp.getIndex()));
   }

   public boolean isTimeAwareColumn() {
      return this.getName().equals("TA_DATE") || this.getName().equals("TA_END_DATE");
   }

   public boolean isRangeStart() {
      return this.getName().equals("TA_DATE");
   }

   public boolean isRangeEnd() {
      return this.getName().equals("TA_END_DATE");
   }
}
