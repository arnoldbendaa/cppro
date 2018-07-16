// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:49
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.dimension;

import com.cedar.cp.api.dimension.StructureElementRef;
import com.cedar.cp.dto.base.AbstractELO;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class CheckLeafELO extends AbstractELO implements Serializable {

   private static final String[] mEntity = new String[]{"StructureElement", "BudgetInstructionAssignment", "VirementAuthPoint", "ResponsibilityArea"};
   private transient StructureElementRef mStructureElementEntityRef;
   private transient String mVisId;
   private transient boolean mLeaf;
   private transient boolean mDisabled;


   public CheckLeafELO() {
      super(new String[]{"StructureElement", "VisId", "Leaf", "Disabled"});
   }

   public void add(StructureElementRef eRefStructureElement, String col1, boolean col2, boolean col3) {
      ArrayList l = new ArrayList();
      l.add(eRefStructureElement);
      l.add(col1);
      l.add(new Boolean(col2));
      l.add(new Boolean(col3));
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
      this.mVisId = (String)l.get(var4++);
      this.mLeaf = ((Boolean)l.get(var4++)).booleanValue();
      this.mDisabled = ((Boolean)l.get(var4++)).booleanValue();
   }

   public StructureElementRef getStructureElementEntityRef() {
      return this.mStructureElementEntityRef;
   }

   public String getVisId() {
      return this.mVisId;
   }

   public boolean getLeaf() {
      return this.mLeaf;
   }

   public boolean getDisabled() {
      return this.mDisabled;
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
