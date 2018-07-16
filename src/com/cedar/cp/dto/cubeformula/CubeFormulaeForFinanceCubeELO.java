// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:47
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.cubeformula;

import com.cedar.cp.api.cubeformula.CubeFormulaRef;
import com.cedar.cp.api.model.FinanceCubeRef;
import com.cedar.cp.api.model.ModelRef;
import com.cedar.cp.dto.base.AbstractELO;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class CubeFormulaeForFinanceCubeELO extends AbstractELO implements Serializable {

   private static final String[] mEntity = new String[]{"CubeFormula", "FinanceCube", "Model", "FormulaDeploymentLine", "FormulaDeploymentEntry", "FormulaDeploymentDt"};
   private transient CubeFormulaRef mCubeFormulaEntityRef;
   private transient FinanceCubeRef mFinanceCubeEntityRef;
   private transient ModelRef mModelEntityRef;
   private transient String mVisId;
   private transient String mDescription;
   private transient int mFormulaType;
   private transient boolean mDeploymentInd;


   public CubeFormulaeForFinanceCubeELO() {
      super(new String[]{"CubeFormula", "FinanceCube", "Model", "VisId", "Description", "FormulaType", "DeploymentInd"});
   }

   public void add(CubeFormulaRef eRefCubeFormula, FinanceCubeRef eRefFinanceCube, ModelRef eRefModel, String col1, String col2, int col3, boolean col4) {
      ArrayList l = new ArrayList();
      l.add(eRefCubeFormula);
      l.add(eRefFinanceCube);
      l.add(eRefModel);
      l.add(col1);
      l.add(col2);
      l.add(new Integer(col3));
      l.add(new Boolean(col4));
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
      this.mCubeFormulaEntityRef = (CubeFormulaRef)l.get(index);
      this.mFinanceCubeEntityRef = (FinanceCubeRef)l.get(var4++);
      this.mModelEntityRef = (ModelRef)l.get(var4++);
      this.mVisId = (String)l.get(var4++);
      this.mDescription = (String)l.get(var4++);
      this.mFormulaType = ((Integer)l.get(var4++)).intValue();
      this.mDeploymentInd = ((Boolean)l.get(var4++)).booleanValue();
   }

   public CubeFormulaRef getCubeFormulaEntityRef() {
      return this.mCubeFormulaEntityRef;
   }

   public FinanceCubeRef getFinanceCubeEntityRef() {
      return this.mFinanceCubeEntityRef;
   }

   public ModelRef getModelEntityRef() {
      return this.mModelEntityRef;
   }

   public String getVisId() {
      return this.mVisId;
   }

   public String getDescription() {
      return this.mDescription;
   }

   public int getFormulaType() {
      return this.mFormulaType;
   }

   public boolean getDeploymentInd() {
      return this.mDeploymentInd;
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
