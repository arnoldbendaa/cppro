// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:28:56
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.utc.struts.message;

import com.cedar.cp.utc.common.CPForm;
import java.util.Collections;
import java.util.List;

public class MessageDistributionListForm extends CPForm {

   private List mInternalDistributions;
   private List mExternalDistributions;


   public List getInternalDistributions() {
      return this.mInternalDistributions == null?Collections.EMPTY_LIST:this.mInternalDistributions;
   }

   public void setInternalDistributions(List internalDistributions) {
      this.mInternalDistributions = internalDistributions;
   }

   public List getExternalDistributions() {
      return this.mExternalDistributions == null?Collections.EMPTY_LIST:this.mExternalDistributions;
   }

   public void setExternalDistributions(List externalDistributions) {
      this.mExternalDistributions = externalDistributions;
   }

   public int getInternalListCount() {
      return this.getInternalDistributions().size();
   }

   public int getExternalListCount() {
      return this.getExternalDistributions().size();
   }
}
