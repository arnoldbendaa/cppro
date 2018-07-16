// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:50
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.dimension;

import com.cedar.cp.api.dimension.HierarchyRef;
import com.cedar.cp.api.dimension.StructureElementRef;
import com.cedar.cp.dto.base.AbstractELO;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class StructureElementByVisIdELO extends AbstractELO implements Serializable {

   private static final String[] mEntity = new String[]{"StructureElement", "Hierarchy", "BudgetInstructionAssignment", "VirementAuthPoint", "ResponsibilityArea"};
   private transient StructureElementRef mStructureElementEntityRef;
   private transient HierarchyRef mHierarchyEntityRef;
   private transient int mStructureId;
   private transient int mStructureElementId;
   private transient String mVisId;
   private transient String mDescription;
   private transient int mDepth;
   private transient int mParentId;
   private transient boolean mLeaf;
   private transient int mPosition;


   public StructureElementByVisIdELO() {
      super(new String[]{"StructureElement", "Hierarchy", "StructureId", "StructureElementId", "VisId", "Description", "Depth", "ParentId", "Leaf", "Position"});
   }

   public void add(StructureElementRef eRefStructureElement, HierarchyRef eRefHierarchy, int col1, int col2, String col3, String col4, int col5, int col6, boolean col7, int col8) {
      ArrayList l = new ArrayList();
      l.add(eRefStructureElement);
      l.add(eRefHierarchy);
      l.add(new Integer(col1));
      l.add(new Integer(col2));
      l.add(col3);
      l.add(col4);
      l.add(new Integer(col5));
      l.add(new Integer(col6));
      l.add(new Boolean(col7));
      l.add(new Integer(col8));
      this.mCollection.add(l);
   }

   public void next() {
      if(this.mIterator == null) {
         this.reset();
      }

      ++this.mCurrRowIndex;
      List l = (List)this.mIterator.next();
      byte index = 0;
      int var4 = index + 1;
      this.mStructureElementEntityRef = (StructureElementRef)l.get(index);
      this.mHierarchyEntityRef = (HierarchyRef)l.get(var4++);
      this.mStructureId = ((Integer)l.get(var4++)).intValue();
      this.mStructureElementId = ((Integer)l.get(var4++)).intValue();
      this.mVisId = (String)l.get(var4++);
      this.mDescription = (String)l.get(var4++);
      this.mDepth = ((Integer)l.get(var4++)).intValue();
      this.mParentId = ((Integer)l.get(var4++)).intValue();
      this.mLeaf = ((Boolean)l.get(var4++)).booleanValue();
      this.mPosition = ((Integer)l.get(var4++)).intValue();
   }

   public StructureElementRef getStructureElementEntityRef() {
      return this.mStructureElementEntityRef;
   }

   public HierarchyRef getHierarchyEntityRef() {
      return this.mHierarchyEntityRef;
   }

   public int getStructureId() {
      return this.mStructureId;
   }

   public int getStructureElementId() {
      return this.mStructureElementId;
   }

   public String getVisId() {
      return this.mVisId;
   }

   public String getDescription() {
      return this.mDescription;
   }

   public int getDepth() {
      return this.mDepth;
   }

   public int getParentId() {
      return this.mParentId;
   }

   public boolean getLeaf() {
      return this.mLeaf;
   }

   public int getPosition() {
      return this.mPosition;
   }

   public boolean includesEntity(String name) {
      for(int i = 0; i < mEntity.length; ++i) {
         if(name.equals(mEntity[i])) {
            return true;
         }
      }

      return false;
   }

}