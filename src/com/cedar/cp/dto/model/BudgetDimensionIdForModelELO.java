// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:55
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.model;

import com.cedar.cp.api.model.ModelDimensionRelRef;
import com.cedar.cp.api.model.ModelRef;
import com.cedar.cp.dto.base.AbstractELO;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class BudgetDimensionIdForModelELO extends AbstractELO implements Serializable {

   private static final String[] mEntity = new String[]{"ModelDimensionRel", "Model"};
   private transient ModelDimensionRelRef mModelDimensionRelEntityRef;
   private transient ModelRef mModelEntityRef;
   private transient int mDimensionId;


   public BudgetDimensionIdForModelELO() {
      super(new String[]{"ModelDimensionRel", "Model", "DimensionId"});
   }

   public void add(ModelDimensionRelRef eRefModelDimensionRel, ModelRef eRefModel, int col1) {
      ArrayList l = new ArrayList();
      l.add(eRefModelDimensionRel);
      l.add(eRefModel);
      l.add(new Integer(col1));
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
      this.mModelDimensionRelEntityRef = (ModelDimensionRelRef)l.get(index);
      this.mModelEntityRef = (ModelRef)l.get(var4++);
      this.mDimensionId = ((Integer)l.get(var4++)).intValue();
   }

   public ModelDimensionRelRef getModelDimensionRelEntityRef() {
      return this.mModelDimensionRelEntityRef;
   }

   public ModelRef getModelEntityRef() {
      return this.mModelEntityRef;
   }

   public int getDimensionId() {
      return this.mDimensionId;
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
