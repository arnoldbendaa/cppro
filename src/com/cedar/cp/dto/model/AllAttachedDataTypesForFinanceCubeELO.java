// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:54
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.model;

import com.cedar.cp.dto.base.AbstractELO;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class AllAttachedDataTypesForFinanceCubeELO extends AbstractELO implements Serializable {

   private static final String[] mEntity = new String[]{"FinanceCubeDataType", "FinanceCube", "Model", "DataType", "DataType"};
   private transient short mDataTypeId;
   private transient String mVisId;
   private transient String mDescription;
   private transient boolean mReadOnlyFlag;
   private transient int mSubType;
   private transient String mFormulaExpr;


   public AllAttachedDataTypesForFinanceCubeELO() {
      super(new String[]{"DataTypeId", "VisId", "Description", "ReadOnlyFlag", "SubType", "FormulaExpr"});
   }

   public void add(short col1, String col2, String col3, boolean col4, int col5, String col6) {
      ArrayList l = new ArrayList();
      l.add(new Short(col1));
      l.add(col2);
      l.add(col3);
      l.add(new Boolean(col4));
      l.add(new Integer(col5));
      l.add(col6);
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
      this.mDataTypeId = ((Short)l.get(index)).shortValue();
      this.mVisId = (String)l.get(var4++);
      this.mDescription = (String)l.get(var4++);
      this.mReadOnlyFlag = ((Boolean)l.get(var4++)).booleanValue();
      this.mSubType = ((Integer)l.get(var4++)).intValue();
      this.mFormulaExpr = (String)l.get(var4++);
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
