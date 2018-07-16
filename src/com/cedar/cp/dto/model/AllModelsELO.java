// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:54
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.model;

import com.cedar.cp.api.model.ModelRef;
import com.cedar.cp.dto.base.AbstractELO;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class AllModelsELO extends AbstractELO implements Serializable {

   private static final String[] mEntity = new String[]{"Model", "FinanceCube", "FinanceCubeDataType", "BudgetLimit", "RollUpRule", "CubeFormula", "FormulaDeploymentLine", "FormulaDeploymentEntry", "FormulaDeploymentDt", "CubeFormulaPackage", "ModelDimensionRel", "ModelProperty", "BudgetCycle", "BudgetState", "BudgetStateHistory", "LevelDate", "BudgetUser", "SecurityGroup", "SecurityGroupUserRel", "SecurityAccessDef", "SecurityAccRngRel", "CellCalc", "CellCalcAssoc", "VirementCategory", "VirementLocation", "VirementAccount", "Recharge", "RechargeCells", "BudgetActivity", "BudgetActivityLink", "VirementRequest", "VirementRequestGroup", "VirementRequestLine", "VirementLineSpread", "VirementAuthPoint", "VirementAuthorisers", "VirementAuthPointLink", "ResponsibilityArea", "WeightingProfile", "WeightingProfileLine", "WeightingDeployment", "WeightingDeploymentLine", "CcDeployment", "CcDeploymentLine", "CcDeploymentEntry", "CcDeploymentDataType", "CcMappingLine", "CcMappingEntry", "FormRebuild", "ImportGrid", "Currency", "Dimension", "Dimension", "Hierarchy", "BudgetInstructionAssignment", "ChangeMgmt", "MappedModel", "AmmModel", "AmmModel"};
   private transient ModelRef mModelEntityRef;
   private transient String mDescription;
   private transient int mBudgetHierarchyId;
   private transient Boolean mGlobal;

   public AllModelsELO() {
      super(new String[]{"Model", "Description", "BudgetHierarchyId", "Global"});
   }

   public void add(ModelRef eRefModel, String col1, int col2, Boolean global) {
      ArrayList l = new ArrayList();
      l.add(eRefModel);
      l.add(col1);
      l.add(new Integer(col2));
      l.add(global);
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
      this.mDescription = (String)l.get(var4++);
      this.mBudgetHierarchyId = ((Integer)l.get(var4++)).intValue();
      this.mGlobal = (Boolean)l.get(var4++);
   }

   public ModelRef getModelEntityRef() {
      return this.mModelEntityRef;
   }

   public String getDescription() {
      return this.mDescription;
   }

   public int getBudgetHierarchyId() {
      return this.mBudgetHierarchyId;
   }
  
   public Boolean isGlobal() {
      return this.mGlobal;
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
