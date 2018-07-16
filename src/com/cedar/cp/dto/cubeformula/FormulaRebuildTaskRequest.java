// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:47
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.cubeformula;

import com.cedar.cp.api.model.ModelRef;
import com.cedar.cp.dto.base.AbstractTaskRequest;
import com.cedar.cp.dto.cubeformula.CubeFormulaRefImpl;
import com.cedar.cp.dto.model.FinanceCubeRefImpl;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class FormulaRebuildTaskRequest extends AbstractTaskRequest {

   private FinanceCubeRefImpl mFinanceCubeRef;
   private ModelRef mModelRef;
   private List<CubeFormulaRefImpl> mFormula = new ArrayList();


   public FormulaRebuildTaskRequest(FinanceCubeRefImpl financeCubeRef, ModelRef modelRef, List<CubeFormulaRefImpl> formula) {
      this.mFinanceCubeRef = financeCubeRef;
      this.mModelRef = modelRef;
      this.mFormula = formula;
   }

   public List toDisplay() {
      ArrayList l = new ArrayList();
      l.add("Cube Formula Rebuild");
      l.add("Model:" + this.mModelRef);
      l.add("Finance Cube:" + this.mFinanceCubeRef.getNarrative());
      if(this.mFormula.isEmpty()) {
         l.add("All Formula");
      } else {
         Iterator i$ = this.mFormula.iterator();

         while(i$.hasNext()) {
            CubeFormulaRefImpl cubeFormulaRef = (CubeFormulaRefImpl)i$.next();
            l.add("Formula:" + cubeFormulaRef.getNarrative());
         }
      }

      return l;
   }

   public String getService() {
      return "com.cedar.cp.ejb.base.async.formula.FormulaRebuildTask";
   }

   public FinanceCubeRefImpl getFinanceCubeRef() {
      return this.mFinanceCubeRef;
   }

   public ModelRef getModelRef() {
      return this.mModelRef;
   }

   public List<CubeFormulaRefImpl> getFormula() {
      return this.mFormula;
   }
}
