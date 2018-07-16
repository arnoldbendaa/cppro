// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:28:55
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.utc.struts.homepage;

import com.cedar.cp.utc.picker.ElementDTO;
import java.io.Serializable;
import java.util.List;

public class ModelDTO extends ElementDTO implements Serializable {

   private Object mName;
   private int mModelId;
   private List mBudgetCycle;


   public String getName() {
      String result = this.mName.toString();
      if(result == null) {
         result = "No Description";
      }

      return result;
   }

   public void setName(Object name) {
      this.mName = name;
   }

   public List getBudgetCycle() {
      return this.mBudgetCycle;
   }

   public void setBudgetCycle(List mBudgetCycle) {
      this.mBudgetCycle = mBudgetCycle;
   }

   public int getModelId() {
      return this.mModelId;
   }

   public void setModelId(int modelId) {
      this.mModelId = modelId;
   }

   public String getFullName() {
      return this.toString();
   }
}
