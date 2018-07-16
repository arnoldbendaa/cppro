// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:06:02
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.model.udwp;

import com.cedar.cp.api.model.ModelRef;
import com.cedar.cp.api.model.udwp.WeightingDeploymentRef;
import com.cedar.cp.api.model.udwp.WeightingProfileRef;
import com.cedar.cp.dto.base.AbstractELO;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class AllWeightingDeploymentsELO extends AbstractELO implements Serializable {

   private static final String[] mEntity = new String[]{"WeightingDeployment", "WeightingProfile", "Model", "WeightingDeploymentLine"};
   private transient WeightingDeploymentRef mWeightingDeploymentEntityRef;
   private transient WeightingProfileRef mWeightingProfileEntityRef;
   private transient ModelRef mModelEntityRef;


   public AllWeightingDeploymentsELO() {
      super(new String[]{"WeightingDeployment", "WeightingProfile", "Model"});
   }

   public void add(WeightingDeploymentRef eRefWeightingDeployment, WeightingProfileRef eRefWeightingProfile, ModelRef eRefModel) {
      ArrayList l = new ArrayList();
      l.add(eRefWeightingDeployment);
      l.add(eRefWeightingProfile);
      l.add(eRefModel);
      this.mCollection.add(l);
   }

   public void next() {
      if(this.mIterator == null) {
         this.reset();
      }

      ++this.mCurrRowIndex;
      List l = (List)this.mIterator.next();
      byte index = 0;
      int var4 = index + 1;
      this.mWeightingDeploymentEntityRef = (WeightingDeploymentRef)l.get(index);
      this.mWeightingProfileEntityRef = (WeightingProfileRef)l.get(var4++);
      this.mModelEntityRef = (ModelRef)l.get(var4++);
   }

   public WeightingDeploymentRef getWeightingDeploymentEntityRef() {
      return this.mWeightingDeploymentEntityRef;
   }

   public WeightingProfileRef getWeightingProfileEntityRef() {
      return this.mWeightingProfileEntityRef;
   }

   public ModelRef getModelEntityRef() {
      return this.mModelEntityRef;
   }

   public boolean includesEntity(String name) {
      for(int i = 0; i < mEntity.length; ++i) {
         if(name.equals(mEntity[i])) {
            return true;
         }
      }

      return false;
   }

}
