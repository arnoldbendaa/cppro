// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:29:00
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.utc.struts.workbench;

import com.cedar.cp.utc.struts.homepage.ModelDTO;
import com.cedar.cp.utc.struts.workbench.BaseWorkBenchForm;

public class ModelWorkBenchForm extends BaseWorkBenchForm {

   private ModelDTO mRoot;


   public ModelDTO getRoot() {
      return this.mRoot;
   }

   public void setRoot(ModelDTO root) {
      this.mRoot = root;
   }
}
