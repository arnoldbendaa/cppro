// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:55
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.model;

import com.cedar.cp.api.dimension.DimensionRef;
import com.cedar.cp.api.dimension.HierarchyRef;
import com.cedar.cp.api.dimension.StructureElementRef;
import com.cedar.cp.api.model.ModelDimensionRelRef;
import com.cedar.cp.api.model.ModelRef;
import com.cedar.cp.dto.base.AbstractELO;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class BudgetHierarchyRootNodeForModelELO extends AbstractELO implements Serializable {

   private static final String[] mEntity = new String[]{"Model", "FinanceCube", "FinanceCubeDataType", "BudgetLimit", "RollUpRule", "CubeFormula", "FormulaDeploymentLine", "FormulaDeploymentEntry", "FormulaDeploymentDt", "CubeFormulaPackage", "ModelDimensionRel", "ModelProperty", "BudgetCycle", "BudgetState", "BudgetStateHistory", "LevelDate", "BudgetUser", "SecurityGroup", "SecurityGroupUserRel", "SecurityAccessDef", "SecurityAccRngRel", "CellCalc", "CellCalcAssoc", "VirementCategory", "VirementLocation", "VirementAccount", "Recharge", "RechargeCells", "BudgetActivity", "BudgetActivityLink", "VirementRequest", "VirementRequestGroup", "VirementRequestLine", "VirementLineSpread", "VirementAuthPoint", "VirementAuthorisers", "VirementAuthPointLink", "ResponsibilityArea", "WeightingProfile", "WeightingProfileLine", "WeightingDeployment", "WeightingDeploymentLine", "CcDeployment", "CcDeploymentLine", "CcDeploymentEntry", "CcDeploymentDataType", "CcMappingLine", "CcMappingEntry", "FormRebuild", "ImportGrid", "StructureElement", "ModelDimensionRel", "Dimension", "Hierarchy", "Currency", "Dimension", "Dimension", "Hierarchy", "BudgetInstructionAssignment", "ChangeMgmt", "MappedModel", "AmmModel", "AmmModel"};
   private transient ModelRef mModelEntityRef;
   private transient StructureElementRef mStructureElementEntityRef;
   private transient ModelDimensionRelRef mModelDimensionRelEntityRef;
   private transient DimensionRef mDimensionEntityRef;
   private transient HierarchyRef mHierarchyEntityRef;
   private transient String mVisId;
   private transient int mStructureId;
   private transient int mStructureElementId;
   private transient String mDescription;
   private transient int mParentId;
   private transient int mChildIndex;
   private transient int mDepth;
   private transient int mPosition;
   private transient boolean mLeaf;
   private transient boolean mIsCredit;
   private transient boolean mDisabled;
   private transient int mBudgetHierarchyId;


   public BudgetHierarchyRootNodeForModelELO() {
      super(new String[]{"Model", "StructureElement", "ModelDimensionRel", "Dimension", "Hierarchy", "VisId", "StructureId", "StructureElementId", "Description", "ParentId", "ChildIndex", "Depth", "Position", "Leaf", "IsCredit", "Disabled", "BudgetHierarchyId"});
   }

   public void add(ModelRef eRefModel, StructureElementRef eRefStructureElement, ModelDimensionRelRef eRefModelDimensionRel, DimensionRef eRefDimension, HierarchyRef eRefHierarchy, String col1, int col2, int col3, String col4, int col5, int col6, int col7, int col8, boolean col9, boolean col10, boolean col11, int col12) {
      ArrayList l = new ArrayList();
      l.add(eRefModel);
      l.add(eRefStructureElement);
      l.add(eRefModelDimensionRel);
      l.add(eRefDimension);
      l.add(eRefHierarchy);
      l.add(col1);
      l.add(new Integer(col2));
      l.add(new Integer(col3));
      l.add(col4);
      l.add(new Integer(col5));
      l.add(new Integer(col6));
      l.add(new Integer(col7));
      l.add(new Integer(col8));
      l.add(new Boolean(col9));
      l.add(new Boolean(col10));
      l.add(new Boolean(col11));
      l.add(new Integer(col12));
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
      this.mStructureElementEntityRef = (StructureElementRef)l.get(var4++);
      this.mModelDimensionRelEntityRef = (ModelDimensionRelRef)l.get(var4++);
      this.mDimensionEntityRef = (DimensionRef)l.get(var4++);
      this.mHierarchyEntityRef = (HierarchyRef)l.get(var4++);
      this.mVisId = (String)l.get(var4++);
      this.mStructureId = ((Integer)l.get(var4++)).intValue();
      this.mStructureElementId = ((Integer)l.get(var4++)).intValue();
      this.mDescription = (String)l.get(var4++);
      this.mParentId = ((Integer)l.get(var4++)).intValue();
      this.mChildIndex = ((Integer)l.get(var4++)).intValue();
      this.mDepth = ((Integer)l.get(var4++)).intValue();
      this.mPosition = ((Integer)l.get(var4++)).intValue();
      this.mLeaf = ((Boolean)l.get(var4++)).booleanValue();
      this.mIsCredit = ((Boolean)l.get(var4++)).booleanValue();
      this.mDisabled = ((Boolean)l.get(var4++)).booleanValue();
      this.mBudgetHierarchyId = ((Integer)l.get(var4++)).intValue();
   }

   public ModelRef getModelEntityRef() {
      return this.mModelEntityRef;
   }

   public StructureElementRef getStructureElementEntityRef() {
      return this.mStructureElementEntityRef;
   }

   public ModelDimensionRelRef getModelDimensionRelEntityRef() {
      return this.mModelDimensionRelEntityRef;
   }

   public DimensionRef getDimensionEntityRef() {
      return this.mDimensionEntityRef;
   }

   public HierarchyRef getHierarchyEntityRef() {
      return this.mHierarchyEntityRef;
   }

   public String getVisId() {
      return this.mVisId;
   }

   public int getStructureId() {
      return this.mStructureId;
   }

   public int getStructureElementId() {
      return this.mStructureElementId;
   }

   public String getDescription() {
      return this.mDescription;
   }

   public int getParentId() {
      return this.mParentId;
   }

   public int getChildIndex() {
      return this.mChildIndex;
   }

   public int getDepth() {
      return this.mDepth;
   }

   public int getPosition() {
      return this.mPosition;
   }

   public boolean getLeaf() {
      return this.mLeaf;
   }

   public boolean getIsCredit() {
      return this.mIsCredit;
   }

   public boolean getDisabled() {
      return this.mDisabled;
   }

   public int getBudgetHierarchyId() {
      return this.mBudgetHierarchyId;
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
