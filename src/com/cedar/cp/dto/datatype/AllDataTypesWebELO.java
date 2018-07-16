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

public class AllDataTypesWebELO extends AbstractELO implements Serializable {

   private static final String[] mEntity = new String[]{"DataType", "DataTypeRel", "FinanceCubeDataType"};
   private transient DataTypeRef mDataTypeEntityRef;
   private transient short mDataTypeId;
   private transient String mVisId;
   private transient String mDescription;


   public AllDataTypesWebELO() {
      super(new String[]{"DataType", "DataTypeId", "VisId", "Description"});
   }

   public void add(DataTypeRef eRefDataType, short col1, String col2, String col3) {
      ArrayList l = new ArrayList();
      l.add(eRefDataType);
      l.add(new Short(col1));
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
      this.mDataTypeEntityRef = (DataTypeRef)l.get(index);
      this.mDataTypeId = ((Short)l.get(var4++)).shortValue();
      this.mVisId = (String)l.get(var4++);
      this.mDescription = (String)l.get(var4++);
   }

   public DataTypeRef getDataTypeEntityRef() {
      return this.mDataTypeEntityRef;
   }

   public short getDataTypeId() {
      return this.mDataTypeId;
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
