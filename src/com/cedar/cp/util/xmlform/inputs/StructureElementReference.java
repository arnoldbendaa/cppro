// Decompiled by:       Fernflower v0.8.6
// Date:                11.08.2012 20:22:32
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.util.xmlform.inputs;

import java.io.Serializable;

public class StructureElementReference implements Serializable {

   private Object mKey;
   private Object mId;
   private String mLabel;
   private boolean mIsCredit;
   private boolean mDisabled;
   private boolean mIsLeaf;
   private boolean mNotPlannable;
   private String mVisId;
   private String mDisplayText;
   private int mDepth;
   private int mPosition;


   public StructureElementReference(Object id, String label, String isCredit, String disabled, String notPlannable, int position) {
      this.mId = id;
      this.mLabel = label;
      this.mDisplayText = label;
      this.mIsCredit = isCredit != null && isCredit.toUpperCase().startsWith("Y");
      this.mDisabled = disabled != null && disabled.equalsIgnoreCase("Y");
      this.mNotPlannable = notPlannable != null && notPlannable.equalsIgnoreCase("Y");
      this.mPosition = position;
   }

   public void setKey(Object key) {
      this.mKey = key;
   }

   public Object getKey() {
      return this.mKey;
   }

   public Object getId() {
      return this.mId;
   }

   public String getLabel() {
      return this.mLabel;
   }

   public boolean isCredit() {
      return this.mIsCredit;
   }

   public boolean isDisabled() {
      return this.mDisabled;
   }

   public boolean isNotPlannable() {
      return this.mNotPlannable;
   }

   public boolean isLeaf() {
      return this.mIsLeaf;
   }

   public void setIsLeaf(String isLeaf) {
      this.mIsLeaf = isLeaf != null && isLeaf.equalsIgnoreCase("Y");
   }

   public String getVisId() {
      return this.mVisId;
   }

   public void setVisId(String visId) {
      this.mVisId = visId;
      this.mDisplayText = this.mVisId + " " + this.mLabel;
   }

   public void setVisIdOnly(String visId) {
      this.mVisId = visId;
   }

   public String getDisplayText() {
      return this.mDisplayText;
   }

   public int getDepth() {
      return this.mDepth;
   }

   public void setDepth(int depth) {
      this.mDepth = depth;
   }

   public String toString() {
      return this.mDisplayText;
   }

   public boolean equals(Object obj) {
      if(obj instanceof StructureElementReference) {
         StructureElementReference other = (StructureElementReference)obj;
         return this.mId.equals(other.mId);
      } else {
         return false;
      }
   }

   public int hashCode() {
      return this.mId.hashCode();
   }

   public int getPosition() {
      return this.mPosition;
   }
}
