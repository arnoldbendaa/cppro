// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:48
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.datatype;

import com.cedar.cp.api.datatype.DataTypeRef;
import com.cedar.cp.api.model.FinanceCubeDataTypeRef;
import com.cedar.cp.api.model.FinanceCubeRef;
import com.cedar.cp.dto.base.AbstractELO;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class AllDataTypesForModelELO extends AbstractELO implements Serializable {

   private static final String[] mEntity = new String[]{"DataType", "DataTypeRel", "FinanceCube", "FinanceCubeDataType", "FinanceCubeDataType"};
   private transient DataTypeRef mDataTypeEntityRef;
   private transient FinanceCubeRef mFinanceCubeEntityRef;
   private transient FinanceCubeDataTypeRef mFinanceCubeDataTypeEntityRef;
   private transient int mSubType;
   private transient Integer mMeasureClass;


   public AllDataTypesForModelELO() {
      super(new String[]{"DataType", "FinanceCube", "FinanceCubeDataType", "SubType", "MeasureClass"});
   }

   public void add(DataTypeRef eRefDataType, FinanceCubeRef eRefFinanceCube, FinanceCubeDataTypeRef eRefFinanceCubeDataType, int col1, Integer col2) {
      ArrayList l = new ArrayList();
      l.add(eRefDataType);
      l.add(eRefFinanceCube);
      l.add(eRefFinanceCubeDataType);
      l.add(new Integer(col1));
      l.add(col2);
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
      this.mDataTypeEntityRef = (DataTypeRef)l.get(index);
      this.mFinanceCubeEntityRef = (FinanceCubeRef)l.get(var4++);
      this.mFinanceCubeDataTypeEntityRef = (FinanceCubeDataTypeRef)l.get(var4++);
      this.mSubType = ((Integer)l.get(var4++)).intValue();
      this.mMeasureClass = (Integer)l.get(var4++);
   }

   public DataTypeRef getDataTypeEntityRef() {
      return this.mDataTypeEntityRef;
   }

   public FinanceCubeRef getFinanceCubeEntityRef() {
      return this.mFinanceCubeEntityRef;
   }

   public FinanceCubeDataTypeRef getFinanceCubeDataTypeEntityRef() {
      return this.mFinanceCubeDataTypeEntityRef;
   }

   public int getSubType() {
      return this.mSubType;
   }

   public Integer getMeasureClass() {
      return this.mMeasureClass;
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
