// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:54
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.model;

import com.cedar.cp.api.model.FinanceCubeRef;
import com.cedar.cp.api.model.ModelRef;
import com.cedar.cp.dto.base.AbstractELO;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class AllFinanceCubesWebForModelELO extends AbstractELO implements Serializable {

   private static final String[] mEntity = new String[]{"FinanceCube", "Model", "FinanceCubeDataType", "BudgetLimit", "RollUpRule", "CubeFormula", "FormulaDeploymentLine", "FormulaDeploymentEntry", "FormulaDeploymentDt", "CubeFormulaPackage", "BudgetInstructionAssignment"};
   private transient FinanceCubeRef mFinanceCubeEntityRef;
   private transient ModelRef mModelEntityRef;
   private transient int mFinanceCubeId;
   private transient String mVisId;
   private transient String mDescription;


   public AllFinanceCubesWebForModelELO() {
      super(new String[]{"FinanceCube", "Model", "FinanceCubeId", "VisId", "Description"});
   }

   public void add(FinanceCubeRef eRefFinanceCube, ModelRef eRefModel, int col1, String col2, String col3) {
      ArrayList l = new ArrayList();
      l.add(eRefFinanceCube);
      l.add(eRefModel);
      l.add(new Integer(col1));
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
      this.mFinanceCubeEntityRef = (FinanceCubeRef)l.get(index);
      this.mModelEntityRef = (ModelRef)l.get(var4++);
      this.mFinanceCubeId = ((Integer)l.get(var4++)).intValue();
      this.mVisId = (String)l.get(var4++);
      this.mDescription = (String)l.get(var4++);
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
