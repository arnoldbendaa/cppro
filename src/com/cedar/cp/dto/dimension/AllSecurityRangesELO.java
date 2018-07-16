// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:49
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.dimension;

import com.cedar.cp.api.dimension.DimensionRef;
import com.cedar.cp.api.dimension.SecurityRangeRef;
import com.cedar.cp.api.model.ModelDimensionRelRef;
import com.cedar.cp.api.model.ModelRef;
import com.cedar.cp.dto.base.AbstractELO;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class AllSecurityRangesELO extends AbstractELO implements Serializable {

   private static final String[] mEntity = new String[]{"SecurityRange", "Dimension", "SecurityRangeRow", "ModelDimensionRel", "Model"};
   private transient SecurityRangeRef mSecurityRangeEntityRef;
   private transient DimensionRef mDimensionEntityRef;
   private transient ModelDimensionRelRef mModelDimensionRelEntityRef;
   private transient ModelRef mModelEntityRef;
   private transient String mDescription;


   public AllSecurityRangesELO() {
      super(new String[]{"SecurityRange", "Dimension", "ModelDimensionRel", "Model", "Description"});
   }

   public void add(SecurityRangeRef eRefSecurityRange, DimensionRef eRefDimension, ModelDimensionRelRef eRefModelDimensionRel, ModelRef eRefModel, String col1) {
      ArrayList l = new ArrayList();
      l.add(eRefSecurityRange);
      l.add(eRefDimension);
      l.add(eRefModelDimensionRel);
      l.add(eRefModel);
      l.add(col1);
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
      this.mSecurityRangeEntityRef = (SecurityRangeRef)l.get(index);
      this.mDimensionEntityRef = (DimensionRef)l.get(var4++);
      this.mModelDimensionRelEntityRef = (ModelDimensionRelRef)l.get(var4++);
      this.mModelEntityRef = (ModelRef)l.get(var4++);
      this.mDescription = (String)l.get(var4++);
   }

   public SecurityRangeRef getSecurityRangeEntityRef() {
      return this.mSecurityRangeEntityRef;
   }

   public DimensionRef getDimensionEntityRef() {
      return this.mDimensionEntityRef;
   }

   public ModelDimensionRelRef getModelDimensionRelEntityRef() {
      return this.mModelDimensionRelEntityRef;
   }

   public ModelRef getModelEntityRef() {
      return this.mModelEntityRef;
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
