// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:47
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.cubeformula;

import com.cedar.cp.api.cubeformula.CubeFormulaPackageRef;
import com.cedar.cp.api.model.FinanceCubeRef;
import com.cedar.cp.api.model.ModelRef;
import com.cedar.cp.dto.base.AbstractELO;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class AllPackagesForFinanceCubeELO extends AbstractELO implements Serializable {

   private static final String[] mEntity = new String[]{"CubeFormulaPackage", "FinanceCube", "Model"};
   private transient CubeFormulaPackageRef mCubeFormulaPackageEntityRef;
   private transient FinanceCubeRef mFinanceCubeEntityRef;
   private transient ModelRef mModelEntityRef;
   private transient int mPackageGroupIndex;


   public AllPackagesForFinanceCubeELO() {
      super(new String[]{"CubeFormulaPackage", "FinanceCube", "Model", "PackageGroupIndex"});
   }

   public void add(CubeFormulaPackageRef eRefCubeFormulaPackage, FinanceCubeRef eRefFinanceCube, ModelRef eRefModel, int col1) {
      ArrayList l = new ArrayList();
      l.add(eRefCubeFormulaPackage);
      l.add(eRefFinanceCube);
      l.add(eRefModel);
      l.add(new Integer(col1));
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
      this.mCubeFormulaPackageEntityRef = (CubeFormulaPackageRef)l.get(index);
      this.mFinanceCubeEntityRef = (FinanceCubeRef)l.get(var4++);
      this.mModelEntityRef = (ModelRef)l.get(var4++);
      this.mPackageGroupIndex = ((Integer)l.get(var4++)).intValue();
   }

   public CubeFormulaPackageRef getCubeFormulaPackageEntityRef() {
      return this.mCubeFormulaPackageEntityRef;
   }

   public FinanceCubeRef getFinanceCubeEntityRef() {
      return this.mFinanceCubeEntityRef;
   }

   public ModelRef getModelEntityRef() {
      return this.mModelEntityRef;
   }

   public int getPackageGroupIndex() {
      return this.mPackageGroupIndex;
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
