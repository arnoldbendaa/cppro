// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:06:07
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.report.distribution;

import com.cedar.cp.api.report.distribution.DistributionDetail;
import com.cedar.cp.api.report.distribution.DistributionDetails;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class DistributionDetailsImpl implements DistributionDetails, Serializable {

   private List mDistributionList;
   private String mBudgetLocation;
   private String mServerRoot;


   public List getDistributionList() {
      if(this.mDistributionList == null) {
         this.mDistributionList = new ArrayList();
      }

      return this.mDistributionList;
   }

   public void setDistributionList(List distributionList) {
      this.mDistributionList = distributionList;
   }

   public void addDistributionDetail(DistributionDetail details) {
      if(!details.getUsers().isEmpty()) {
         this.getDistributionList().add(details);
      }

   }

   public String getBudgetLocation() {
      return this.mBudgetLocation;
   }

   public void setBudgetLocation(String budgetLocation) {
      this.mBudgetLocation = budgetLocation;
   }

   public String getServerRoot() {
      return this.mServerRoot;
   }

   public void setServerRoot(String serverRoot) {
      this.mServerRoot = serverRoot;
   }
}
