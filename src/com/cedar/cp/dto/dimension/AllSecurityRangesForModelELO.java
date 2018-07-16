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

public class AllSecurityRangesForModelELO extends AbstractELO implements Serializable {

   private static final String[] mEntity = new String[]{"SecurityRange", "Dimension", "SecurityRangeRow", "Model", "ModelDimensionRel"};
   private transient SecurityRangeRef mSecurityRangeEntityRef;
   private transient DimensionRef mDimensionEntityRef;
   private transient ModelRef mModelEntityRef;
   private transient ModelDimensionRelRef mModelDimensionRelEntityRef;
   private transient int mModelId;
   private transient int mDimensionId;
   private transient String mDescription;


   public AllSecurityRangesForModelELO() {
      super(new String[]{"SecurityRange", "Dimension", "Model", "ModelDimensionRel", "ModelId", "DimensionId", "Description"});
   }

   public void add(SecurityRangeRef eRefSecurityRange, DimensionRef eRefDimension, ModelRef eRefModel, ModelDimensionRelRef eRefModelDimensionRel, int col1, int col2, String col3) {
      ArrayList l = new ArrayList();
      l.add(eRefSecurityRange);
      l.add(eRefDimension);
      l.add(eRefModel);
      l.add(eRefModelDimensionRel);
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
      this.mSecurityRangeEntityRef = (SecurityRangeRef)l.get(index);
      this.mDimensionEntityRef = (DimensionRef)l.get(var4++);
      this.mModelEntityRef = (ModelRef)l.get(var4++);
      this.mModelDimensionRelEntityRef = (ModelDimensionRelRef)l.get(var4++);
      this.mModelId = ((Integer)l.get(var4++)).intValue();
      this.mDimensionId = ((Integer)l.get(var4++)).intValue();
      this.mDescription = (String)l.get(var4++);
   }

   public SecurityRangeRef getSecurityRangeEntityRef() {
      return this.mSecurityRangeEntityRef;
   }

   public DimensionRef getDimensionEntityRef() {
      return this.mDimensionEntityRef;
   }

   public ModelRef getModelEntityRef() {
      return this.mModelEntityRef;
   }

   public ModelDimensionRelRef getModelDimensionRelEntityRef() {
      return this.mModelDimensionRelEntityRef;
   }

   public int getModelId() {
      return this.mModelId;
   }

   public int getDimensionId() {
      return this.mDimensionId;
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
