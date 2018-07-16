// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:48
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.datatype;

import com.cedar.cp.api.datatype.DataTypeRef;
import com.cedar.cp.dto.base.AbstractELO;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class DataTypesByTypeELO extends AbstractELO implements Serializable {

   private static final String[] mEntity = new String[]{"DataType", "DataTypeRel", "FinanceCubeDataType"};
   private transient DataTypeRef mDataTypeEntityRef;
   private transient String mDescription;
   private transient int mSubType;
   private transient short mDataTypeId;
   private transient String mVisId;


   public DataTypesByTypeELO() {
      super(new String[]{"DataType", "Description", "SubType", "DataTypeId", "VisId"});
   }

   public void add(DataTypeRef eRefDataType, String col1, int col2, short col3, String col4) {
      ArrayList l = new ArrayList();
      l.add(eRefDataType);
      l.add(col1);
      l.add(new Integer(col2));
      l.add(new Short(col3));
      l.add(col4);
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
      this.mDescription = (String)l.get(var4++);
      this.mSubType = ((Integer)l.get(var4++)).intValue();
      this.mDataTypeId = ((Short)l.get(var4++)).shortValue();
      this.mVisId = (String)l.get(var4++);
   }

   public DataTypeRef getDataTypeEntityRef() {
      return this.mDataTypeEntityRef;
   }

   public String getDescription() {
      return this.mDescription;
   }

   public int getSubType() {
      return this.mSubType;
   }

   public short getDataTypeId() {
      return this.mDataTypeId;
   }

   public String getVisId() {
      return this.mVisId;
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
