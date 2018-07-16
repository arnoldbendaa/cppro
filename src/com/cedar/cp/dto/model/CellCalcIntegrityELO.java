// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:56
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.model;

import com.cedar.cp.api.model.CellCalcRef;
import com.cedar.cp.api.model.ModelRef;
import com.cedar.cp.dto.base.AbstractELO;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class CellCalcIntegrityELO extends AbstractELO implements Serializable {

   private static final String[] mEntity = new String[]{"CellCalc", "Model", "CellCalcAssoc"};
   private transient CellCalcRef mCellCalcEntityRef;
   private transient ModelRef mModelEntityRef;
   private transient int mModelId;
   private transient int mXmlformId;
   private transient int mAccessDefinitionId;
   private transient int mDataTypeId;


   public CellCalcIntegrityELO() {
      super(new String[]{"CellCalc", "Model", "ModelId", "XmlformId", "AccessDefinitionId", "DataTypeId"});
   }

   public void add(CellCalcRef eRefCellCalc, ModelRef eRefModel, int col1, int col2, int col3, int col4) {
      ArrayList l = new ArrayList();
      l.add(eRefCellCalc);
      l.add(eRefModel);
      l.add(new Integer(col1));
      l.add(new Integer(col2));
      l.add(new Integer(col3));
      l.add(new Integer(col4));
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
      this.mCellCalcEntityRef = (CellCalcRef)l.get(index);
      this.mModelEntityRef = (ModelRef)l.get(var4++);
      this.mModelId = ((Integer)l.get(var4++)).intValue();
      this.mXmlformId = ((Integer)l.get(var4++)).intValue();
      this.mAccessDefinitionId = ((Integer)l.get(var4++)).intValue();
      this.mDataTypeId = ((Integer)l.get(var4++)).intValue();
   }

   public CellCalcRef getCellCalcEntityRef() {
      return this.mCellCalcEntityRef;
   }

   public ModelRef getModelEntityRef() {
      return this.mModelEntityRef;
   }

   public int getModelId() {
      return this.mModelId;
   }

   public int getXmlformId() {
      return this.mXmlformId;
   }

   public int getAccessDefinitionId() {
      return this.mAccessDefinitionId;
   }

   public int getDataTypeId() {
      return this.mDataTypeId;
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
