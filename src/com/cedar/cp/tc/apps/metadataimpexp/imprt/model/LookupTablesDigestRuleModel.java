// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 18:23:02
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.tc.apps.metadataimpexp.imprt.model;

import com.cedar.cp.tc.apps.metadataimpexp.imprt.model.LookupTableDigestRuleModel;
import java.util.ArrayList;
import java.util.List;

public class LookupTablesDigestRuleModel {

   private List<LookupTableDigestRuleModel> lookupTableModelList = new ArrayList();


   public List<LookupTableDigestRuleModel> getLookupTableModelList() {
      return this.lookupTableModelList;
   }

   public void setLookupTableModelList(List<LookupTableDigestRuleModel> lookupTableModelList) {
      this.lookupTableModelList = lookupTableModelList;
   }

   public void addLookupTable(LookupTableDigestRuleModel model) {
      this.lookupTableModelList.add(model);
   }
}
