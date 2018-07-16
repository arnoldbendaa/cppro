// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:58
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.model.amm;

import com.cedar.cp.api.model.amm.AmmModelRef;
import com.cedar.cp.dto.base.AbstractELO;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class AllAmmModelsELO extends AbstractELO implements Serializable {

   private static final String[] mEntity = new String[]{"AmmModel", "AmmDimension", "AmmDimensionElement", "AmmSrcStructureElement", "AmmFinanceCube", "AmmDataType", "Model", "Model"};
   private transient AmmModelRef mAmmModelEntityRef;
   private transient int mModelId;
   private transient String mCol2;
   private transient String mCol3;
   private transient int mSrcModelId;
   private transient String mCol5;
   private transient String mCol6;
   private transient Integer mInvalidatedByTaskId;


   public AllAmmModelsELO() {
      super(new String[]{"AmmModel", "ModelId", "col2", "col3", "SrcModelId", "col5", "col6", "InvalidatedByTaskId"});
   }

   public void add(AmmModelRef eRefAmmModel, int col1, String col2, String col3, int col4, String col5, String col6, Integer col7) {
      ArrayList l = new ArrayList();
      l.add(eRefAmmModel);
      l.add(new Integer(col1));
      l.add(col2);
      l.add(col3);
      l.add(new Integer(col4));
      l.add(col5);
      l.add(col6);
      l.add(col7);
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
      this.mAmmModelEntityRef = (AmmModelRef)l.get(index);
      this.mModelId = ((Integer)l.get(var4++)).intValue();
      this.mCol2 = (String)l.get(var4++);
      this.mCol3 = (String)l.get(var4++);
      this.mSrcModelId = ((Integer)l.get(var4++)).intValue();
      this.mCol5 = (String)l.get(var4++);
      this.mCol6 = (String)l.get(var4++);
      this.mInvalidatedByTaskId = (Integer)l.get(var4++);
   }

   public AmmModelRef getAmmModelEntityRef() {
      return this.mAmmModelEntityRef;
   }

   public int getModelId() {
      return this.mModelId;
   }

   public String getCol2() {
      return this.mCol2;
   }

   public String getCol3() {
      return this.mCol3;
   }

   public int getSrcModelId() {
      return this.mSrcModelId;
   }

   public String getCol5() {
      return this.mCol5;
   }

   public String getCol6() {
      return this.mCol6;
   }

   public Integer getInvalidatedByTaskId() {
      return this.mInvalidatedByTaskId;
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
