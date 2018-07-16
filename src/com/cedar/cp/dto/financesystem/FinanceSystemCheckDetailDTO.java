// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:53
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.financesystem;

import com.cedar.cp.api.financesystem.FinanceSystemCheckDetail;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

public class FinanceSystemCheckDetailDTO implements FinanceSystemCheckDetail {

   private boolean mValid = true;
   private List mValidList;
   private String mErrorType;
   private List mErrorMessages = new ArrayList();
   private boolean mOverRide = false;
   private transient Set mValidSet;


   public Set getValidSet() {
      return this.mValidSet == null?Collections.EMPTY_SET:this.mValidSet;
   }

   public void setValidSet(Set validSet) {
      this.mValidSet = validSet;
   }

   public String getErrorType() {
      return this.mErrorType;
   }

   public void setErrorType(String errorType) {
      this.mErrorType = errorType;
   }

   public List getErrorMessages() {
      return this.mErrorMessages;
   }

   public void setErrorMessages(List errorMessages) {
      this.mErrorMessages = errorMessages;
   }

   public void addErrorMessage(String message) {
      this.mErrorMessages.add(message);
   }

   public boolean isValid() {
      return this.mErrorMessages.size() > 0?false:this.mValid;
   }

   public void setValid(boolean valid) {
      this.mValid = valid;
   }

   public void setValidList(List validList) {
      this.mValidList = validList;
   }

   public List getList() {
      return this.mValidList == null?Collections.EMPTY_LIST:this.mValidList;
   }

   public boolean isOverRide() {
      return this.mOverRide;
   }

   public void setOverRide(boolean overRide) {
      this.mOverRide = overRide;
   }
}
