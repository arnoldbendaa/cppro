// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:56
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.model;

import com.cedar.cp.api.datatype.DataTypeRef;
import com.cedar.cp.api.model.FinanceCubeDataTypeRef;
import com.cedar.cp.api.model.FinanceCubeRef;
import com.cedar.cp.api.model.ModelRef;
import com.cedar.cp.dto.base.AbstractELO;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ImportableFinanceCubeDataTypesELO extends AbstractELO implements Serializable {

   private static final String[] mEntity = new String[]{"FinanceCubeDataType", "FinanceCube", "Model", "DataType", "DataType"};
   private transient FinanceCubeDataTypeRef mFinanceCubeDataTypeEntityRef;
   private transient FinanceCubeRef mFinanceCubeEntityRef;
   private transient ModelRef mModelEntityRef;
   private transient DataTypeRef mDataTypeEntityRef;
   private transient boolean mAvailableForImport;
   private transient boolean mAvailableForExport;


   public ImportableFinanceCubeDataTypesELO() {
      super(new String[]{"FinanceCubeDataType", "FinanceCube", "Model", "DataType", "AvailableForImport", "AvailableForExport"});
   }

   public void add(FinanceCubeDataTypeRef eRefFinanceCubeDataType, FinanceCubeRef eRefFinanceCube, ModelRef eRefModel, DataTypeRef eRefDataType, boolean col1, boolean col2) {
      ArrayList l = new ArrayList();
      l.add(eRefFinanceCubeDataType);
      l.add(eRefFinanceCube);
      l.add(eRefModel);
      l.add(eRefDataType);
      l.add(new Boolean(col1));
      l.add(new Boolean(col2));
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
      this.mDataTypeEntityRef = (DataTypeRef)l.get(var4++);
      this.mAvailableForImport = ((Boolean)l.get(var4++)).booleanValue();
      this.mAvailableForExport = ((Boolean)l.get(var4++)).booleanValue();
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

   public DataTypeRef getDataTypeEntityRef() {
      return this.mDataTypeEntityRef;
   }

   public boolean getAvailableForImport() {
      return this.mAvailableForImport;
   }

   public boolean getAvailableForExport() {
      return this.mAvailableForExport;
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
