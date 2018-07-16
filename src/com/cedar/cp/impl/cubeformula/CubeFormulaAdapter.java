// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:10
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.impl.cubeformula;

import com.cedar.cp.api.cubeformula.CubeFormula;
import com.cedar.cp.api.cubeformula.FormulaDeploymentLine;
import com.cedar.cp.api.dimension.DimensionRef;
import com.cedar.cp.api.model.FinanceCubeRef;
import com.cedar.cp.api.model.ModelRef;
import com.cedar.cp.dto.cubeformula.CubeFormulaImpl;
import com.cedar.cp.dto.cubeformula.CubeFormulaPK;
import com.cedar.cp.impl.base.BusinessProcessImpl;
import com.cedar.cp.impl.cubeformula.CubeFormulaEditorSessionImpl;
import java.util.List;

public class CubeFormulaAdapter implements CubeFormula {

   private CubeFormulaImpl mEditorData;
   private CubeFormulaEditorSessionImpl mEditorSessionImpl;


   public CubeFormulaAdapter(CubeFormulaEditorSessionImpl e, CubeFormulaImpl editorData) {
      this.mEditorData = editorData;
      this.mEditorSessionImpl = e;
   }

   public void setPrimaryKey(Object key) {
      this.mEditorData.setPrimaryKey(key);
   }

   protected CubeFormulaEditorSessionImpl getEditorSessionImpl() {
      return this.mEditorSessionImpl;
   }

   protected CubeFormulaImpl getEditorData() {
      return this.mEditorData;
   }

   public Object getPrimaryKey() {
      return this.mEditorData.getPrimaryKey();
   }

   void setPrimaryKey(CubeFormulaPK paramKey) {
      this.mEditorData.setPrimaryKey(paramKey);
   }

   public String getVisId() {
      return this.mEditorData.getVisId();
   }

   public String getDescription() {
      return this.mEditorData.getDescription();
   }

   public String getFormulaText() {
      return this.mEditorData.getFormulaText();
   }

   public boolean isDeploymentInd() {
      return this.mEditorData.isDeploymentInd();
   }

   public int getFormulaType() {
      return this.mEditorData.getFormulaType();
   }

   public FinanceCubeRef getFinanceCubeRef() {
      if(this.mEditorData.getFinanceCubeRef() != null) {
         if(this.mEditorData.getFinanceCubeRef().getNarrative() != null && this.mEditorData.getFinanceCubeRef().getNarrative().length() > 0) {
            return this.mEditorData.getFinanceCubeRef();
         } else {
            try {
               FinanceCubeRef e = ((BusinessProcessImpl)this.getEditorSessionImpl().getBusinessProcess()).getConnection().getListHelper().getFinanceCubeEntityRef(this.mEditorData.getFinanceCubeRef());
               this.mEditorData.setFinanceCubeRef(e);
               return e;
            } catch (Exception var2) {
               throw new RuntimeException(var2.getMessage());
            }
         }
      } else {
         return null;
      }
   }

   public ModelRef getModelRef() {
      if(this.mEditorData.getModelRef() != null) {
         if(this.mEditorData.getModelRef().getNarrative() != null && this.mEditorData.getModelRef().getNarrative().length() > 0) {
            return this.mEditorData.getModelRef();
         } else {
            try {
               ModelRef e = ((BusinessProcessImpl)this.getEditorSessionImpl().getBusinessProcess()).getConnection().getListHelper().getModelEntityRef(this.mEditorData.getModelRef());
               this.mEditorData.setModelRef(e);
               return e;
            } catch (Exception var2) {
               throw new RuntimeException(var2.getMessage());
            }
         }
      } else {
         return null;
      }
   }

   public void setFinanceCubeRef(FinanceCubeRef ref) {
      this.mEditorData.setFinanceCubeRef(ref);
   }

   public void setModelRef(ModelRef ref) {
      this.mEditorData.setModelRef(ref);
   }

   public void setVisId(String p) {
      this.mEditorData.setVisId(p);
   }

   public void setDescription(String p) {
      this.mEditorData.setDescription(p);
   }

   public void setFormulaText(String p) {
      this.mEditorData.setFormulaText(p);
   }

   public void setDeploymentInd(boolean p) {
      this.mEditorData.setDeploymentInd(p);
   }

   public void setFormulaType(int p) {
      this.mEditorData.setFormulaType(p);
   }

   public List<FormulaDeploymentLine> getDeploymentLines() {
      return this.mEditorData.getDeploymentLines();
   }

   public FormulaDeploymentLine getFormulaDeploymentLine(Object key) {
      return this.mEditorData.getFormulaDeploymentLine(key);
   }

   public DimensionRef[] getDimensionRefs() {
      return this.mEditorData.getDimensionRefs();
   }

   public boolean isFinanceCubeFormulaEnabled() {
      return this.mEditorData.isFinanceCubeFormulaEnabled();
   }
}
