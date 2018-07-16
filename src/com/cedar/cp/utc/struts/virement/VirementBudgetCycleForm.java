// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:35:15
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.utc.struts.virement;

import com.cedar.cp.utc.common.CPForm;
import com.cedar.cp.utc.struts.virement.VirementBudgetCycleDTO;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletRequest;
import org.apache.struts.action.ActionMapping;

public class VirementBudgetCycleForm extends CPForm {

   private List<VirementBudgetCycleDTO> mBudgetCycles = new ArrayList();


   public void reset(ActionMapping mapping, ServletRequest servletRequest) {
      if(this.mBudgetCycles != null) {
         this.mBudgetCycles.clear();
      }

      super.reset(mapping, servletRequest);
   }

   public int getSize() {
      return this.getBudgetCycles().size();
   }

   public List<VirementBudgetCycleDTO> getBudgetCycles() {
      return this.mBudgetCycles;
   }

   public void setBudgetCycles(List<VirementBudgetCycleDTO> budgetCycles) {
      this.mBudgetCycles = budgetCycles;
   }
}
