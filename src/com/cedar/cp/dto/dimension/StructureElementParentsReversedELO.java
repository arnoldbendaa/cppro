// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:50
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.dimension;

import com.cedar.cp.api.dimension.StructureElementRef;
import com.cedar.cp.dto.base.AbstractELO;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class StructureElementParentsReversedELO extends AbstractELO implements Serializable {

   private static final String[] mEntity = new String[]{"StructureElement", "BudgetInstructionAssignment", "VirementAuthPoint", "ResponsibilityArea"};
   private transient StructureElementRef mStructureElementEntityRef;
   private transient int mStructureId;
   private transient int mStructureElementId;
   private transient String mDescription;


   public StructureElementParentsReversedELO() {
      super(new String[]{"StructureElement", "StructureId", "StructureElementId", "Description"});
   }

   public void add(StructureElementRef eRefStructureElement, int col1, int col2, String col3) {
      ArrayList l = new ArrayList();
      l.add(eRefStructureElement);
      l.add(new Integer(col1));
      l.add(new Integer(col2));
      l.add(col3);
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
      this.mStructureId = ((Integer)l.get(var4++)).intValue();
      this.mStructureElementId = ((Integer)l.get(var4++)).intValue();
      this.mDescription = (String)l.get(var4++);
   }

   public StructureElementRef getStructureElementEntityRef() {
      return this.mStructureElementEntityRef;
   }

   public int getStructureId() {
      return this.mStructureId;
   }

   public int getStructureElementId() {
      return this.mStructureElementId;
   }

   public String getDescription() {
      return this.mDescription;
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
