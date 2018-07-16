// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:07:03
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.base.cube.flatform;

import com.cedar.cp.ejb.base.cube.flatform.StructureElementLookup;
import java.util.HashMap;
import java.util.Map;

public class HierarchyLookup {

   private int mHierarchyId;
   private String mHierarchyVisId;
   private Map<String, StructureElementLookup> mStructureElements = new HashMap();


   public int getHierarchyId() {
      return this.mHierarchyId;
   }

   public void setHierarchyId(int hierarchyId) {
      this.mHierarchyId = hierarchyId;
   }

   public String getHierarchyVisId() {
      return this.mHierarchyVisId;
   }

   public void setHierarchyVisId(String hierarchyVisId) {
      this.mHierarchyVisId = hierarchyVisId;
   }

   public Map<String, StructureElementLookup> getStructureElements() {
      return this.mStructureElements;
   }

   public void setStructureElements(Map<String, StructureElementLookup> structureElements) {
      this.mStructureElements = structureElements;
   }

   public void registerStructureElementLookup(String seVisId) {
      StructureElementLookup structureElementLookup = (StructureElementLookup)this.mStructureElements.get(seVisId);
      if(structureElementLookup == null) {
         structureElementLookup = new StructureElementLookup();
         structureElementLookup.setStructureElementVisId(seVisId);
         this.mStructureElements.put(seVisId, structureElementLookup);
      }

   }

   public void setElementInfo(int hierarchyId, String structureElementVisId, String structureElementDescription, int structureElementId, boolean leaf) {
      this.mHierarchyId = hierarchyId;
      StructureElementLookup structureElementLookup = (StructureElementLookup)this.mStructureElements.get(structureElementVisId);
      if(structureElementLookup != null) {
         structureElementLookup.setStructureElementId(structureElementId);
         structureElementLookup.setStructureElementDescription(structureElementDescription);
         structureElementLookup.setLeaf(leaf);
      }

   }

   public int getStructureElementId(String structureElementVisId) {
      StructureElementLookup structureElementLookup = (StructureElementLookup)this.mStructureElements.get(structureElementVisId);
      return structureElementLookup == null?-1:structureElementLookup.getStructureElementId();
   }

   public int getLeafStructureElementId(String structureElementVisId) {
      StructureElementLookup structureElementLookup = (StructureElementLookup)this.mStructureElements.get(structureElementVisId);
      return structureElementLookup == null?-1:(structureElementLookup.isLeaf()?structureElementLookup.getStructureElementId():-2);
   }
}
