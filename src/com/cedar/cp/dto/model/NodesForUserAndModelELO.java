// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:57
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.model;

import com.cedar.cp.api.dimension.StructureElementRef;
import com.cedar.cp.api.model.BudgetUserRef;
import com.cedar.cp.api.model.ModelRef;
import com.cedar.cp.dto.base.AbstractELO;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class NodesForUserAndModelELO extends AbstractELO implements Serializable {

   private static final String[] mEntity = new String[]{"BudgetUser", "Model", "StructureElement"};
   private transient BudgetUserRef mBudgetUserEntityRef;
   private transient ModelRef mModelEntityRef;
   private transient StructureElementRef mStructureElementEntityRef;
   private transient int mStructureElementId;
   private transient String mVisId;
   private transient String mDescription;


   public NodesForUserAndModelELO() {
      super(new String[]{"BudgetUser", "Model", "StructureElement", "StructureElementId", "VisId", "Description"});
   }

   public void add(BudgetUserRef eRefBudgetUser, ModelRef eRefModel, StructureElementRef eRefStructureElement, int col1, String col2, String col3) {
      ArrayList l = new ArrayList();
      l.add(eRefBudgetUser);
      l.add(eRefModel);
      l.add(eRefStructureElement);
      l.add(new Integer(col1));
      l.add(col2);
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
      this.mBudgetUserEntityRef = (BudgetUserRef)l.get(index);
      this.mModelEntityRef = (ModelRef)l.get(var4++);
      this.mStructureElementEntityRef = (StructureElementRef)l.get(var4++);
      this.mStructureElementId = ((Integer)l.get(var4++)).intValue();
      this.mVisId = (String)l.get(var4++);
      this.mDescription = (String)l.get(var4++);
   }

   public BudgetUserRef getBudgetUserEntityRef() {
      return this.mBudgetUserEntityRef;
   }

   public ModelRef getModelEntityRef() {
      return this.mModelEntityRef;
   }

   public StructureElementRef getStructureElementEntityRef() {
      return this.mStructureElementEntityRef;
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

   public boolean includesEntity(String name) {
      for(int i = 0; i < mEntity.length; ++i) {
         if(name.equals(mEntity[i])) {
            return true;
         }
      }

      return false;
   }

}
