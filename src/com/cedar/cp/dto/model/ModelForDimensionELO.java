// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:57
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.model;

import com.cedar.cp.api.model.ModelDimensionRelRef;
import com.cedar.cp.api.model.ModelRef;
import com.cedar.cp.dto.base.AbstractELO;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ModelForDimensionELO extends AbstractELO implements Serializable {

   private static final String[] mEntity = new String[]{"Model", "FinanceCube", "FinanceCubeDataType", "BudgetLimit", "RollUpRule", "CubeFormula", "FormulaDeploymentLine", "FormulaDeploymentEntry", "FormulaDeploymentDt", "CubeFormulaPackage", "ModelDimensionRel", "ModelProperty", "BudgetCycle", "BudgetState", "BudgetStateHistory", "LevelDate", "BudgetUser", "SecurityGroup", "SecurityGroupUserRel", "SecurityAccessDef", "SecurityAccRngRel", "CellCalc", "CellCalcAssoc", "VirementCategory", "VirementLocation", "VirementAccount", "Recharge", "RechargeCells", "BudgetActivity", "BudgetActivityLink", "VirementRequest", "VirementRequestGroup", "VirementRequestLine", "VirementLineSpread", "VirementAuthPoint", "VirementAuthorisers", "VirementAuthPointLink", "ResponsibilityArea", "WeightingProfile", "WeightingProfileLine", "WeightingDeployment", "WeightingDeploymentLine", "CcDeployment", "CcDeploymentLine", "CcDeploymentEntry", "CcDeploymentDataType", "CcMappingLine", "CcMappingEntry", "FormRebuild", "ImportGrid", "ModelDimensionRel", "Currency", "Dimension", "Dimension", "Hierarchy", "BudgetInstructionAssignment", "ChangeMgmt", "MappedModel", "AmmModel", "AmmModel"};
   private transient ModelRef mModelEntityRef;
   private transient ModelDimensionRelRef mModelDimensionRelEntityRef;


   public ModelForDimensionELO() {
      super(new String[]{"Model", "ModelDimensionRel"});
   }

   public void add(ModelRef eRefModel, ModelDimensionRelRef eRefModelDimensionRel) {
      ArrayList l = new ArrayList();
      l.add(eRefModel);
      l.add(eRefModelDimensionRel);
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
      this.mModelEntityRef = (ModelRef)l.get(index);
      this.mModelDimensionRelEntityRef = (ModelDimensionRelRef)l.get(var4++);
   }

   public ModelRef getModelEntityRef() {
      return this.mModelEntityRef;
   }

   public ModelDimensionRelRef getModelDimensionRelEntityRef() {
      return this.mModelDimensionRelEntityRef;
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
