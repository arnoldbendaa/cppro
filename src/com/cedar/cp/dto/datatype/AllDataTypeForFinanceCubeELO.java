// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:48
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.datatype;

import com.cedar.cp.api.datatype.DataTypeRef;
import com.cedar.cp.api.model.FinanceCubeDataTypeRef;
import com.cedar.cp.dto.base.AbstractELO;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class AllDataTypeForFinanceCubeELO extends AbstractELO implements Serializable {

   private static final String[] mEntity = new String[]{"DataType", "DataTypeRel", "FinanceCubeDataType", "FinanceCubeDataType"};
   private transient DataTypeRef mDataTypeEntityRef;
   private transient FinanceCubeDataTypeRef mFinanceCubeDataTypeEntityRef;
   private transient boolean mReadOnlyFlag;
   private transient int mSubType;
   private transient Integer mMeasureClass;
   private transient Integer mMeasureScale;
   private transient Integer mMeasureLength;


   public AllDataTypeForFinanceCubeELO() {
      super(new String[]{"DataType", "FinanceCubeDataType", "ReadOnlyFlag", "SubType", "MeasureClass", "MeasureScale", "MeasureLength"});
   }

   public void add(DataTypeRef eRefDataType, FinanceCubeDataTypeRef eRefFinanceCubeDataType, boolean col1, int col2, Integer col3, Integer col4, Integer col5) {
      ArrayList l = new ArrayList();
      l.add(eRefDataType);
      l.add(eRefFinanceCubeDataType);
      l.add(new Boolean(col1));
      l.add(new Integer(col2));
      l.add(col3);
      l.add(col4);
      l.add(col5);
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
      this.mFinanceCubeDataTypeEntityRef = (FinanceCubeDataTypeRef)l.get(var4++);
      this.mReadOnlyFlag = ((Boolean)l.get(var4++)).booleanValue();
      this.mSubType = ((Integer)l.get(var4++)).intValue();
      this.mMeasureClass = (Integer)l.get(var4++);
      this.mMeasureScale = (Integer)l.get(var4++);
      this.mMeasureLength = (Integer)l.get(var4++);
   }

   public DataTypeRef getDataTypeEntityRef() {
      return this.mDataTypeEntityRef;
   }

   public FinanceCubeDataTypeRef getFinanceCubeDataTypeEntityRef() {
      return this.mFinanceCubeDataTypeEntityRef;
   }

   public boolean getReadOnlyFlag() {
      return this.mReadOnlyFlag;
   }

   public int getSubType() {
      return this.mSubType;
   }

   public Integer getMeasureClass() {
      return this.mMeasureClass;
   }

   public Integer getMeasureScale() {
      return this.mMeasureScale;
   }

   public Integer getMeasureLength() {
      return this.mMeasureLength;
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
