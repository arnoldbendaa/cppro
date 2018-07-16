// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:56
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.model;

import com.cedar.cp.api.model.ChangeBudgetStateResult;
import java.io.Serializable;
import java.util.Collections;
import java.util.List;

public class ChangeBudgetStateResultImpl implements ChangeBudgetStateResult, Serializable {

   private boolean mValid = true;
   private String mMessage;
   private Exception mException;
   private boolean mForceMessage;
   private List mBudgetLimitViolations;


   public boolean isValid() {
      return this.mValid;
   }

   public String getMessage() {
      return this.mMessage;
   }

   public Exception getException() {
      return this.mException;
   }

   public void setValid(boolean valid) {
      this.mValid = valid;
   }

   public void setMessage(String message) {
      this.mMessage = message;
   }

   public void setException(Exception exception) {
      this.mException = exception;
   }

   public List getBudgetLimitViolations() {
      return this.mBudgetLimitViolations == null?Collections.EMPTY_LIST:this.mBudgetLimitViolations;
   }

   public void setBudgetLimitViolations(List budgetLimitViolations) {
      this.mBudgetLimitViolations = budgetLimitViolations;
   }

   public boolean isForceMessage() {
      return this.mForceMessage;
   }

   public void setForceMessage(boolean forceMessage) {
      this.mForceMessage = forceMessage;
   }
}
