// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:35:14
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.utc.picker;

import com.cedar.cp.api.base.EntityRef;
import com.cedar.cp.api.dimension.StructureElementKey;
import java.io.Serializable;

public class ElementDTO implements StructureElementKey, Serializable {

   private ElementDTO mParent;
   private Object mKey;
   private int mId;
   private String mIdentifier;
   private String mDescription;
   private boolean mLeaf;
   private Integer mPosition;
   private int mCalendarType;
   private int mStructureId;


   public ElementDTO() {}

   public ElementDTO(ElementDTO parent) {
      this.mParent = parent;
   }

   public ElementDTO(EntityRef ref) {
      this.setIdentifier(ref.getNarrative());
      this.setKey(ref.getPrimaryKey());
   }

   public Object getKey() {
      return this.mKey;
   }

   public void setKey(Object key) {
      this.mKey = key;
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

   public boolean isLeaf() {
      return this.mLeaf;
   }

   public void setLeaf(boolean leaf) {
      this.mLeaf = leaf;
   }

   public Integer getPosition() {
      return this.mPosition;
   }

   public void setPosition(Integer position) {
      this.mPosition = position;
   }

   public int getStructureId() {
      return this.mStructureId;
   }

   public void setStructureId(int structureId) {
      this.mStructureId = structureId;
   }

   public int getCalendarType() {
      return this.mCalendarType;
   }

   public void setCalendarType(int calendarType) {
      this.mCalendarType = calendarType;
   }

   public String getFullPathIdentifier() {
      return this.getFullPathIdentifier((String)null);
   }

   public String getFullPathIdentifier(String text) {
      StringBuilder sb = new StringBuilder();
      sb.append(this.getIdentifier());
      if(text != null) {
         sb.append("/");
         sb.append(text);
      }

      return this.mParent != null && this.mParent.getCalendarType() != 100?this.mParent.getFullPathIdentifier(sb.toString()):sb.toString();
   }

   public String toString() {
      StringBuffer sb = new StringBuffer();
      if(this.getIdentifier() != null) {
         sb.append(this.getIdentifier().trim());
      }

      if(this.getIdentifier() != null && this.getDescription() != null) {
         sb.append(" - ");
      }

      if(this.getDescription() != null) {
         sb.append(this.getDescription().trim());
      }

      return sb.toString();
   }
}
