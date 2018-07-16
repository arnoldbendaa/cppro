// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:04:19
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.api.model.budgetlimit;

import com.cedar.cp.api.base.EntityList;
import java.io.Serializable;
import java.util.Collections;
import java.util.List;

public class BudgetLimitCheck implements Serializable {

   private boolean mPerformCheck = true;
   private String mReason;
   private String mOriginalReason;
   private List mViolations;
   private EntityList mViolationHeadings;
   private boolean mForceMessage = false;


   public boolean isPerformCheck() {
      return this.mPerformCheck;
   }

   public void setPerformCheck(boolean performCheck) {
      this.mPerformCheck = performCheck;
   }

   public String getReason() {
      return this.mReason;
   }

   public void setReason(String reason) {
      this.mReason = reason;
   }

   public String getOriginalReason() {
      return this.mOriginalReason;
   }

   public void setOriginalReason(String originalReason) {
      this.mOriginalReason = originalReason;
   }

   public List getViolations() {
      return this.mViolations == null?Collections.EMPTY_LIST:this.mViolations;
   }

   public void setViolations(List violations) {
      this.mViolations = violations;
   }

   public EntityList getViolationHeadings() {
      return this.mViolationHeadings;
   }

   public void setViolationHeadings(EntityList violationHeadings) {
      this.mViolationHeadings = violationHeadings;
   }

   public boolean isForceMessage() {
      return this.mForceMessage;
   }

   public void setForceMessage(boolean forceMessage) {
      this.mForceMessage = forceMessage;
   }
}
