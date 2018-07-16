// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:50
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.dimension;

import com.cedar.cp.api.dimension.StructureElementRef;
import com.cedar.cp.api.model.ModelRef;
import com.cedar.cp.dto.base.AbstractELO;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class LeafPlannableBudgetLocationsForModelELO extends AbstractELO implements Serializable {

   private static final String[] mEntity = new String[]{"StructureElement", "Model", "BudgetInstructionAssignment", "VirementAuthPoint", "ResponsibilityArea"};
   private transient StructureElementRef mStructureElementEntityRef;
   private transient ModelRef mModelEntityRef;
   private transient int mParentId;
   private transient String mDescription;
   private transient int mStructureElementId;


   public LeafPlannableBudgetLocationsForModelELO() {
      super(new String[]{"StructureElement", "Model", "ParentId", "Description", "StructureElementId"});
   }

   public void add(StructureElementRef eRefStructureElement, ModelRef eRefModel, int col1, String col2, int col3) {
      ArrayList l = new ArrayList();
      l.add(eRefStructureElement);
      l.add(eRefModel);
      l.add(new Integer(col1));
      l.add(col2);
      l.add(new Integer(col3));
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
      this.mModelEntityRef = (ModelRef)l.get(var4++);
      this.mParentId = ((Integer)l.get(var4++)).intValue();
      this.mDescription = (String)l.get(var4++);
      this.mStructureElementId = ((Integer)l.get(var4++)).intValue();
   }

   public StructureElementRef getStructureElementEntityRef() {
      return this.mStructureElementEntityRef;
   }

   public ModelRef getModelEntityRef() {
      return this.mModelEntityRef;
   }

   public int getParentId() {
      return this.mParentId;
   }

   public String getDescription() {
      return this.mDescription;
   }

   public int getStructureElementId() {
      return this.mStructureElementId;
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
