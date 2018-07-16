// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:54
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.model;

import com.cedar.cp.api.model.CellCalcAssocRef;
import com.cedar.cp.api.model.CellCalcRef;
import com.cedar.cp.api.model.ModelRef;
import com.cedar.cp.dto.base.AbstractELO;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class AllCellCalcAssocsELO extends AbstractELO implements Serializable {

   private static final String[] mEntity = new String[]{"CellCalcAssoc", "CellCalc", "Model"};
   private transient CellCalcAssocRef mCellCalcAssocEntityRef;
   private transient CellCalcRef mCellCalcEntityRef;
   private transient ModelRef mModelEntityRef;
   private transient int mCellCalcId;
   private transient int mAccountElementId;


   public AllCellCalcAssocsELO() {
      super(new String[]{"CellCalcAssoc", "CellCalc", "Model", "CellCalcId", "AccountElementId"});
   }

   public void add(CellCalcAssocRef eRefCellCalcAssoc, CellCalcRef eRefCellCalc, ModelRef eRefModel, int col1, int col2) {
      ArrayList l = new ArrayList();
      l.add(eRefCellCalcAssoc);
      l.add(eRefCellCalc);
      l.add(eRefModel);
      l.add(new Integer(col1));
      l.add(new Integer(col2));
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
      this.mCellCalcAssocEntityRef = (CellCalcAssocRef)l.get(index);
      this.mCellCalcEntityRef = (CellCalcRef)l.get(var4++);
      this.mModelEntityRef = (ModelRef)l.get(var4++);
      this.mCellCalcId = ((Integer)l.get(var4++)).intValue();
      this.mAccountElementId = ((Integer)l.get(var4++)).intValue();
   }

   public CellCalcAssocRef getCellCalcAssocEntityRef() {
      return this.mCellCalcAssocEntityRef;
   }

   public CellCalcRef getCellCalcEntityRef() {
      return this.mCellCalcEntityRef;
   }

   public ModelRef getModelEntityRef() {
      return this.mModelEntityRef;
   }

   public int getCellCalcId() {
      return this.mCellCalcId;
   }

   public int getAccountElementId() {
      return this.mAccountElementId;
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
