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

public class DataTypesForImpExpELO extends AbstractELO implements Serializable {

   private static final String[] mEntity = new String[]{"DataType", "DataTypeRel", "FinanceCubeDataType"};
   private transient DataTypeRef mDataTypeEntityRef;
   private transient short mDataTypeId;
   private transient String mVisId;
   private transient String mDescription;
   private transient boolean mReadOnlyFlag;
   private transient boolean mAvailableForImport;
   private transient boolean mAvailableForExport;
   private transient int mSubType;
   private transient String mFormulaExpr;


   public DataTypesForImpExpELO() {
      super(new String[]{"DataType", "DataTypeId", "VisId", "Description", "ReadOnlyFlag", "AvailableForImport", "AvailableForExport", "SubType", "FormulaExpr"});
   }

   public void add(DataTypeRef eRefDataType, short col1, String col2, String col3, boolean col4, boolean col5, boolean col6, int col7, String col8) {
      ArrayList l = new ArrayList();
      l.add(eRefDataType);
      l.add(new Short(col1));
      l.add(col2);
      l.add(col3);
      l.add(new Boolean(col4));
      l.add(new Boolean(col5));
      l.add(new Boolean(col6));
      l.add(new Integer(col7));
      l.add(col8);
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
      this.mReadOnlyFlag = ((Boolean)l.get(var4++)).booleanValue();
      this.mAvailableForImport = ((Boolean)l.get(var4++)).booleanValue();
      this.mAvailableForExport = ((Boolean)l.get(var4++)).booleanValue();
      this.mSubType = ((Integer)l.get(var4++)).intValue();
      this.mFormulaExpr = (String)l.get(var4++);
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

   public boolean getReadOnlyFlag() {
      return this.mReadOnlyFlag;
   }

   public boolean getAvailableForImport() {
      return this.mAvailableForImport;
   }

   public boolean getAvailableForExport() {
      return this.mAvailableForExport;
   }

   public int getSubType() {
      return this.mSubType;
   }

   public String getFormulaExpr() {
      return this.mFormulaExpr;
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
