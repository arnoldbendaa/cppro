// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:57
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.model;

import com.cedar.cp.api.model.ReviewBudgetSelection;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ReviewBudgetSelectionImpl implements ReviewBudgetSelection, Serializable {

   private String mLabel;
   private List mOptions;
   private int mSelected;


   public ReviewBudgetSelectionImpl() {
      this.mLabel = "not set";
      this.mOptions = new ArrayList();
      this.mSelected = -1;
   }

   public ReviewBudgetSelectionImpl(String label, List options, int selected) {
      this.mLabel = label;
      this.mOptions = options;
      this.mSelected = selected;
   }

   public void setLabel(String label) {
      this.mLabel = label;
   }

   public void setCurrentOption(int selected) {
      this.mSelected = selected;
   }

   public void setSelectOptions(List options) {
      this.mOptions = options;
   }

   public String getLabel() {
      return this.mLabel;
   }

   public List getSelectOptions() {
      return this.mOptions;
   }

   public int getCurrentOption() {
      return this.mSelected;
   }

   public int getCurrentOption(int i) {
      return this.mSelected;
   }
}
