// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:07:03
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.base.cube.flatform;


public class StructureElementLookup {

   private int mStructureElementId = -1;
   private String mStructureElementVisId;
   private String mStructureElementDescription;
   private boolean mLeaf;


   public int getStructureElementId() {
      return this.mStructureElementId;
   }

   public void setStructureElementId(int structureElementId) {
      this.mStructureElementId = structureElementId;
   }

   public String getStructureElementVisId() {
      return this.mStructureElementVisId;
   }

   public void setStructureElementVisId(String structureElementVisId) {
      this.mStructureElementVisId = structureElementVisId;
   }

   public boolean isLeaf() {
      return this.mLeaf;
   }

   public void setLeaf(boolean leaf) {
      this.mLeaf = leaf;
   }

   public String getStructureElementDescription() {
      return this.mStructureElementDescription;
   }

   public void setStructureElementDescription(String structureElementDescription) {
      this.mStructureElementDescription = structureElementDescription;
   }
}
