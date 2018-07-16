// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:34:59
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.utc.common;

import org.apache.struts.action.ActionForm;

public class CPSearchForm extends ActionForm {

   private int mMaxResult = 100;
   private int mPotentialSize;
   private boolean mDoScroll = true;


   public int getMaxResult() {
      return this.mMaxResult;
   }

   public void setMaxResult(int maxResult) {
      this.mMaxResult = maxResult;
   }

   public int getPotentialSize() {
      return this.mPotentialSize;
   }

   public void setPotentialSize(int potentialSize) {
      this.mPotentialSize = potentialSize;
   }

   public boolean isDoScroll() {
      return this.mDoScroll;
   }

   public void setDoScroll(boolean doScroll) {
      this.mDoScroll = doScroll;
   }
}
