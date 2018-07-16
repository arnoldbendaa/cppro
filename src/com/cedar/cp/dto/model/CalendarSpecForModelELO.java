// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:56
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.model;

import com.cedar.cp.api.dimension.CalendarSpecRef;
import com.cedar.cp.api.model.ModelDimensionRelRef;
import com.cedar.cp.api.model.ModelRef;
import com.cedar.cp.dto.base.AbstractELO;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class CalendarSpecForModelELO extends AbstractELO implements Serializable {

   private static final String[] mEntity = new String[]{"Model", "FinanceCube", "FinanceCubeDataType", "BudgetLimit", "RollUpRule", "CubeFormula", "FormulaDeploymentLine", "FormulaDeploymentEntry", "FormulaDeploymentDt", "CubeFormulaPackage", "ModelDimensionRel", "ModelProperty", "BudgetCycle", "BudgetState", "BudgetStateHistory", "LevelDate", "BudgetUser", "SecurityGroup", "SecurityGroupUserRel", "SecurityAccessDef", "SecurityAccRngRel", "CellCalc", "CellCalcAssoc", "VirementCategory", "VirementLocation", "VirementAccount", "Recharge", "RechargeCells", "BudgetActivity", "BudgetActivityLink", "VirementRequest", "VirementRequestGroup", "VirementRequestLine", "VirementLineSpread", "VirementAuthPoint", "VirementAuthorisers", "VirementAuthPointLink", "ResponsibilityArea", "WeightingProfile", "WeightingProfileLine", "WeightingDeployment", "WeightingDeploymentLine", "CcDeployment", "CcDeploymentLine", "CcDeploymentEntry", "CcDeploymentDataType", "CcMappingLine", "CcMappingEntry", "FormRebuild", "ImportGrid", "CalendarSpec", "ModelDimensionRel", "Currency", "Dimension", "Dimension", "Hierarchy", "BudgetInstructionAssignment", "ChangeMgmt", "MappedModel", "AmmModel", "AmmModel"};
   private transient ModelRef mModelEntityRef;
   private transient CalendarSpecRef mCalendarSpecEntityRef;
   private transient ModelDimensionRelRef mModelDimensionRelEntityRef;
   private transient int mYearStartMonth;


   public CalendarSpecForModelELO() {
      super(new String[]{"Model", "CalendarSpec", "ModelDimensionRel", "YearStartMonth"});
   }

   public void add(ModelRef eRefModel, CalendarSpecRef eRefCalendarSpec, ModelDimensionRelRef eRefModelDimensionRel, int col1) {
      ArrayList l = new ArrayList();
      l.add(eRefModel);
      l.add(eRefCalendarSpec);
      l.add(eRefModelDimensionRel);
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
      this.mModelEntityRef = (ModelRef)l.get(index);
      this.mCalendarSpecEntityRef = (CalendarSpecRef)l.get(var4++);
      this.mModelDimensionRelEntityRef = (ModelDimensionRelRef)l.get(var4++);
      this.mYearStartMonth = ((Integer)l.get(var4++)).intValue();
   }

   public ModelRef getModelEntityRef() {
      return this.mModelEntityRef;
   }

   public CalendarSpecRef getCalendarSpecEntityRef() {
      return this.mCalendarSpecEntityRef;
   }

   public ModelDimensionRelRef getModelDimensionRelEntityRef() {
      return this.mModelDimensionRelEntityRef;
   }

   public int getYearStartMonth() {
      return this.mYearStartMonth;
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
