// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:54
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.model;

import com.cedar.cp.api.model.FinanceCubeDataTypeRef;
import com.cedar.cp.api.model.FinanceCubeRef;
import com.cedar.cp.api.model.ModelRef;
import com.cedar.cp.dto.base.AbstractELO;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class AllFinanceCubesForDataTypeELO extends AbstractELO implements Serializable {

   private static final String[] mEntity = new String[]{"FinanceCubeDataType", "FinanceCube", "Model", "DataType"};
   private transient FinanceCubeDataTypeRef mFinanceCubeDataTypeEntityRef;
   private transient FinanceCubeRef mFinanceCubeEntityRef;
   private transient ModelRef mModelEntityRef;
   private transient int mFinanceCubeId;


   public AllFinanceCubesForDataTypeELO() {
      super(new String[]{"FinanceCubeDataType", "FinanceCube", "Model", "FinanceCubeId"});
   }

   public void add(FinanceCubeDataTypeRef eRefFinanceCubeDataType, FinanceCubeRef eRefFinanceCube, ModelRef eRefModel, int col1) {
      ArrayList l = new ArrayList();
      l.add(eRefFinanceCubeDataType);
      l.add(eRefFinanceCube);
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
      this.mFinanceCubeDataTypeEntityRef = (FinanceCubeDataTypeRef)l.get(index);
      this.mFinanceCubeEntityRef = (FinanceCubeRef)l.get(var4++);
      this.mModelEntityRef = (ModelRef)l.get(var4++);
      this.mFinanceCubeId = ((Integer)l.get(var4++)).intValue();
   }

   public FinanceCubeDataTypeRef getFinanceCubeDataTypeEntityRef() {
      return this.mFinanceCubeDataTypeEntityRef;
   }

   public FinanceCubeRef getFinanceCubeEntityRef() {
      return this.mFinanceCubeEntityRef;
   }

   public ModelRef getModelEntityRef() {
      return this.mModelEntityRef;
   }

   public int getFinanceCubeId() {
      return this.mFinanceCubeId;
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
