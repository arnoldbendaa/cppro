// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:47
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.cubeformula;

import com.cedar.cp.api.cubeformula.CubeFormula;
import com.cedar.cp.api.cubeformula.FormulaDeploymentLine;
import com.cedar.cp.api.dimension.DimensionRef;
import com.cedar.cp.api.model.FinanceCubeRef;
import com.cedar.cp.api.model.ModelRef;
import com.cedar.cp.dto.cubeformula.CubeFormulaCK;
import com.cedar.cp.dto.cubeformula.CubeFormulaPK;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class CubeFormulaImpl implements CubeFormula, Serializable, Cloneable {

   private List<FormulaDeploymentLine> mDeploymentLines = new ArrayList();
   public DimensionRef[] mDimensionRefs;
   private boolean mFinanceCubeFormulaEnabled;
   private Object mPrimaryKey;
   private String mVisId;
   private String mDescription;
   private String mFormulaText;
   private boolean mDeploymentInd;
   private int mFormulaType;
   private FinanceCubeRef mFinanceCubeRef;
   private ModelRef mModelRef;


   public CubeFormulaImpl(Object paramKey) {
      this.mPrimaryKey = paramKey;
      this.mVisId = "";
      this.mDescription = "";
      this.mFormulaText = "";
      this.mDeploymentInd = false;
      this.mFormulaType = 0;
   }

   public Object getPrimaryKey() {
      return this.mPrimaryKey;
   }

   public void setPrimaryKey(Object paramKey) {
      this.mPrimaryKey = (CubeFormulaPK)paramKey;
   }

   public void setPrimaryKey(CubeFormulaCK paramKey) {
      this.mPrimaryKey = paramKey;
   }

   public String getVisId() {
      return this.mVisId;
   }

   public String getDescription() {
      return this.mDescription;
   }

   public String getFormulaText() {
      return this.mFormulaText;
   }

   public boolean isDeploymentInd() {
      return this.mDeploymentInd;
   }

   public int getFormulaType() {
      return this.mFormulaType;
   }

   public FinanceCubeRef getFinanceCubeRef() {
      return this.mFinanceCubeRef;
   }

   public ModelRef getModelRef() {
      return this.mModelRef;
   }

   public void setFinanceCubeRef(FinanceCubeRef ref) {
      this.mFinanceCubeRef = ref;
   }

   public void setModelRef(ModelRef ref) {
      this.mModelRef = ref;
   }

   public void setVisId(String paramVisId) {
      this.mVisId = paramVisId;
   }

   public void setDescription(String paramDescription) {
      this.mDescription = paramDescription;
   }

   public void setFormulaText(String paramFormulaText) {
      this.mFormulaText = paramFormulaText;
   }

   public void setDeploymentInd(boolean paramDeploymentInd) {
      this.mDeploymentInd = paramDeploymentInd;
   }

   public void setFormulaType(int paramFormulaType) {
      this.mFormulaType = paramFormulaType;
   }

   public FormulaDeploymentLine getFormulaDeploymentLine(Object key) {
      Iterator i$ = this.mDeploymentLines.iterator();

      FormulaDeploymentLine line;
      do {
         if(!i$.hasNext()) {
            return null;
         }

         line = (FormulaDeploymentLine)i$.next();
      } while(!line.getKey().equals(key));

      return line;
   }

   public boolean removeFormulaDeploymentLine(Object key) {
      Iterator i$ = this.mDeploymentLines.iterator();

      FormulaDeploymentLine line;
      do {
         if(!i$.hasNext()) {
            return false;
         }

         line = (FormulaDeploymentLine)i$.next();
      } while(!line.getKey().equals(key));

      this.mDeploymentLines.remove(line);
      return true;
   }

   public List<FormulaDeploymentLine> getDeploymentLines() {
      return this.mDeploymentLines;
   }

   public void setDeploymentLines(List<FormulaDeploymentLine> deploymentLines) {
      this.mDeploymentLines = deploymentLines;
   }

   public DimensionRef[] getDimensionRefs() {
      return this.mDimensionRefs;
   }

   public void setDimensionRefs(DimensionRef[] dimensionRefs) {
      this.mDimensionRefs = dimensionRefs;
   }

   public boolean isFinanceCubeFormulaEnabled() {
      return this.mFinanceCubeFormulaEnabled;
   }

   public void setFinanceCubeFormulaEnabled(boolean financeCubeFormulaEnabled) {
      this.mFinanceCubeFormulaEnabled = financeCubeFormulaEnabled;
   }
}
