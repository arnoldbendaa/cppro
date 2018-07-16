// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:04:18
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.api.model;

import com.cedar.cp.api.model.FinanceCubeRef;
import java.io.Serializable;

public class FinanceCubeDTO implements Serializable {

   private FinanceCubeRef mFinanceRef;
   private int mFinanceCubeId;
   private String mDescription;


   public FinanceCubeRef getFinanceRef() {
      return this.mFinanceRef;
   }

   public void setFinanceRef(FinanceCubeRef financeRef) {
      this.mFinanceRef = financeRef;
   }

   public int getFinanceCubeId() {
      return this.mFinanceCubeId;
   }

   public void setFinanceCubeId(int financeCubeId) {
      this.mFinanceCubeId = financeCubeId;
   }

   public String getDescription() {
      return this.mDescription;
   }

   public void setDescription(String description) {
      this.mDescription = description;
   }

   public String toString() {
      StringBuilder sb = new StringBuilder();
      if(this.getFinanceRef() != null) {
         sb.append(this.getFinanceRef());
      }

      return sb.toString();
   }
}
