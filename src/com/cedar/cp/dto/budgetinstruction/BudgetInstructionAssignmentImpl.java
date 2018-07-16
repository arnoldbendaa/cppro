// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:46
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.budgetinstruction;

import com.cedar.cp.api.budgetinstruction.BudgetInstructionAssignment;
import com.cedar.cp.api.dimension.StructureElementRef;
import com.cedar.cp.api.model.BudgetCycleRef;
import com.cedar.cp.api.model.FinanceCubeRef;
import com.cedar.cp.api.model.ModelRef;
import com.cedar.cp.dto.budgetinstruction.BudgetInstructionAssignmentPK;
import java.io.Serializable;
import java.util.List;

public class BudgetInstructionAssignmentImpl implements BudgetInstructionAssignment, Serializable, Cloneable {

   private Object mPrimaryKey;
   private int mVersionNum;
   private ModelRef mOwningModelRef;
   private FinanceCubeRef mOwningFinanceCubeRef;
   private BudgetCycleRef mOwningBudgetCycleRef;
   private StructureElementRef mOwningBudgetLocationRef;
   private boolean mSelectChildren;
   private List mLocationParents;


   public BudgetInstructionAssignmentImpl(Object paramKey) {
      this.mPrimaryKey = paramKey;
   }

   public Object getPrimaryKey() {
      return this.mPrimaryKey;
   }

   public void setPrimaryKey(Object paramKey) {
      this.mPrimaryKey = (BudgetInstructionAssignmentPK)paramKey;
   }

   public int getVersionNum() {
      return this.mVersionNum;
   }

   public void setVersionNum(int n) {
      this.mVersionNum = n;
   }

   public ModelRef getOwningModelRef() {
      return this.mOwningModelRef;
   }

   public FinanceCubeRef getOwningFinanceCubeRef() {
      return this.mOwningFinanceCubeRef;
   }

   public BudgetCycleRef getOwningBudgetCycleRef() {
      return this.mOwningBudgetCycleRef;
   }

   public StructureElementRef getOwningBudgetLocationRef() {
      return this.mOwningBudgetLocationRef;
   }

   public void setOwningModelRef(ModelRef ref) {
      this.mOwningModelRef = ref;
   }

   public void setOwningFinanceCubeRef(FinanceCubeRef ref) {
      this.mOwningFinanceCubeRef = ref;
   }

   public void setOwningBudgetCycleRef(BudgetCycleRef ref) {
      this.mOwningBudgetCycleRef = ref;
   }

   public void setOwningBudgetLocationRef(StructureElementRef ref) {
      this.mOwningBudgetLocationRef = ref;
   }

   public Object clone() throws CloneNotSupportedException {
      BudgetInstructionAssignmentImpl copy = (BudgetInstructionAssignmentImpl)super.clone();
      return copy;
   }

   public int getId() {
      BudgetInstructionAssignmentPK pk = (BudgetInstructionAssignmentPK)this.getPrimaryKey();
      return pk.getAssignmentId();
   }

   public void setSelectChildren(boolean b) {
      this.mSelectChildren = b;
   }

   public boolean getSelectChildren() {
      return this.mSelectChildren;
   }

   public void setParents(List path) {
      this.mLocationParents = path;
   }

   public List getParents() {
      return this.mLocationParents;
   }
}
