// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:07:03
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.base.cube.flatform;

import com.cedar.cp.ejb.base.cube.flatform.HierarchyLookup;
import java.util.HashMap;
import java.util.Map;

public class DimensionLookup {

   private int mDimensionId;
   private String mDimensionVisId;
   private Map<String, HierarchyLookup> mHierarchies = new HashMap();


   public int getDimensionId() {
      return this.mDimensionId;
   }

   public void setDimensionId(int dimensionId) {
      this.mDimensionId = dimensionId;
   }

   public String getDimensionVisId() {
      return this.mDimensionVisId;
   }

   public void setDimensionVisId(String dimensionVisId) {
      this.mDimensionVisId = dimensionVisId;
   }

   public Map<String, HierarchyLookup> getHierarchies() {
      return this.mHierarchies;
   }

   public void setHierarchies(Map<String, HierarchyLookup> hierarchies) {
      this.mHierarchies = hierarchies;
   }

   public HierarchyLookup getHierarchyLookup(String hierarchyName, boolean create) {
      HierarchyLookup hierarchyLookup = (HierarchyLookup)this.mHierarchies.get(hierarchyName);
      if(create && hierarchyLookup == null) {
         hierarchyLookup = new HierarchyLookup();
         hierarchyLookup.setHierarchyVisId(hierarchyName);
         this.mHierarchies.put(hierarchyLookup.getHierarchyVisId(), hierarchyLookup);
      }

      return hierarchyLookup;
   }

   public void setElementInfo(int dimensionId, String hierarchyVisId, int hierarchyId, String structureElementVisId, String structureElementDescription, int structureElementId, boolean leaf) {
      this.mDimensionId = dimensionId;
      HierarchyLookup hierarchyLookup = (HierarchyLookup)this.mHierarchies.get(hierarchyVisId);
      if(hierarchyLookup != null) {
         hierarchyLookup.setElementInfo(hierarchyId, structureElementVisId, structureElementDescription, structureElementId, leaf);
      }

   }

   public int getStructureElementId(String hierVisId, String structureElementVisId) {
      HierarchyLookup hierLookup = (HierarchyLookup)this.mHierarchies.get(hierVisId);
      return hierLookup == null?-1:hierLookup.getStructureElementId(structureElementVisId);
   }

   public int getLeafStructureElementId(String hierVisId, String structureElementVisId) {
      HierarchyLookup hierLookup = (HierarchyLookup)this.mHierarchies.get(hierVisId);
      return hierLookup == null?-1:hierLookup.getLeafStructureElementId(structureElementVisId);
   }
}
