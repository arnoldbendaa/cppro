// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:57
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.model;

import com.cedar.cp.api.base.EntityRef;
import com.cedar.cp.dto.model.ModelImpl;
import java.io.Serializable;
import java.util.ArrayList;

public class ModelEditorSessionSSO implements Serializable {

   private boolean mAccountAmendable = true;
   private boolean mCalendarAmendable = true;
   private boolean mBudgetHierarchyAmendable = true;
   private ArrayList mImmutableBusinessDimensions = new ArrayList();
   private ModelImpl mEditorData;


   public ModelEditorSessionSSO() {}

   public ModelEditorSessionSSO(ModelImpl paramEditorData) {
      this.mEditorData = paramEditorData;
   }

   public void setEditorData(ModelImpl paramEditorData) {
      this.mEditorData = paramEditorData;
   }

   public ModelImpl getEditorData() {
      return this.mEditorData;
   }

   public boolean isAccountAmendable() {
      return this.mAccountAmendable;
   }

   public boolean isCalendarAmendable() {
      return this.mCalendarAmendable;
   }

   public boolean isBusinessDimensionAmendable(EntityRef ref) {
      return !this.mImmutableBusinessDimensions.contains(ref);
   }

   public void setAccountAmendable(boolean amend) {
      this.mAccountAmendable = amend;
   }

   public void setCalendarAmendable(boolean amend) {
      this.mCalendarAmendable = amend;
   }

   public void setBudgetHierachyAmendable(boolean amend) {
      this.mBudgetHierarchyAmendable = amend;
   }

   public void setImmutableBusinessDimensions(ArrayList dims) {
      this.mImmutableBusinessDimensions.clear();
      this.mImmutableBusinessDimensions.addAll(dims);
   }

   public boolean isBudgetHierarchyAmendable() {
      return this.mBudgetHierarchyAmendable;
   }
}
