// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:16
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.impl.model.udwp;

import com.cedar.cp.api.model.ModelRef;
import com.cedar.cp.api.model.udwp.WeightingDeployment;
import com.cedar.cp.api.model.udwp.WeightingProfileRef;
import com.cedar.cp.dto.model.udwp.WeightingDeploymentImpl;
import com.cedar.cp.dto.model.udwp.WeightingDeploymentPK;
import com.cedar.cp.impl.base.BusinessProcessImpl;
import com.cedar.cp.impl.model.udwp.WeightingDeploymentEditorSessionImpl;
import java.util.Map;
import java.util.Set;

public class WeightingDeploymentAdapter implements WeightingDeployment {

   private WeightingDeploymentImpl mEditorData;
   private WeightingDeploymentEditorSessionImpl mEditorSessionImpl;


   public WeightingDeploymentAdapter(WeightingDeploymentEditorSessionImpl e, WeightingDeploymentImpl editorData) {
      this.mEditorData = editorData;
      this.mEditorSessionImpl = e;
   }

   public void setPrimaryKey(Object key) {
      this.mEditorData.setPrimaryKey(key);
   }

   protected WeightingDeploymentEditorSessionImpl getEditorSessionImpl() {
      return this.mEditorSessionImpl;
   }

   protected WeightingDeploymentImpl getEditorData() {
      return this.mEditorData;
   }

   public Object getPrimaryKey() {
      return this.mEditorData.getPrimaryKey();
   }

   void setPrimaryKey(WeightingDeploymentPK paramKey) {
      this.mEditorData.setPrimaryKey(paramKey);
   }

   public int getProfileId() {
      return this.mEditorData.getProfileId();
   }

   public boolean isAnyAccount() {
      return this.mEditorData.isAnyAccount();
   }

   public boolean isAnyBusiness() {
      return this.mEditorData.isAnyBusiness();
   }

   public boolean isAnyDataType() {
      return this.mEditorData.isAnyDataType();
   }

   public int getWeighting() {
      return this.mEditorData.getWeighting();
   }

   public WeightingProfileRef getWeightingProfileRef() {
      if(this.mEditorData.getWeightingProfileRef() != null) {
         if(this.mEditorData.getWeightingProfileRef().getNarrative() != null && this.mEditorData.getWeightingProfileRef().getNarrative().length() > 0) {
            return this.mEditorData.getWeightingProfileRef();
         } else {
            try {
               WeightingProfileRef e = ((BusinessProcessImpl)this.getEditorSessionImpl().getBusinessProcess()).getConnection().getListHelper().getWeightingProfileEntityRef(this.mEditorData.getWeightingProfileRef());
               this.mEditorData.setWeightingProfileRef(e);
               return e;
            } catch (Exception var2) {
               throw new RuntimeException(var2.getMessage());
            }
         }
      } else {
         return null;
      }
   }

   public ModelRef getModelRef() {
      if(this.mEditorData.getModelRef() != null) {
         if(this.mEditorData.getModelRef().getNarrative() != null && this.mEditorData.getModelRef().getNarrative().length() > 0) {
            return this.mEditorData.getModelRef();
         } else {
            try {
               ModelRef e = ((BusinessProcessImpl)this.getEditorSessionImpl().getBusinessProcess()).getConnection().getListHelper().getModelEntityRef(this.mEditorData.getModelRef());
               this.mEditorData.setModelRef(e);
               return e;
            } catch (Exception var2) {
               throw new RuntimeException(var2.getMessage());
            }
         }
      } else {
         return null;
      }
   }

   public void setWeightingProfileRef(WeightingProfileRef ref) {
      this.mEditorData.setWeightingProfileRef(ref);
   }

   public void setModelRef(ModelRef ref) {
      this.mEditorData.setModelRef(ref);
   }

   public void setProfileId(int p) {
      this.mEditorData.setProfileId(p);
   }

   public void setAnyAccount(boolean p) {
      this.mEditorData.setAnyAccount(p);
   }

   public void setAnyBusiness(boolean p) {
      this.mEditorData.setAnyBusiness(p);
   }

   public void setAnyDataType(boolean p) {
      this.mEditorData.setAnyDataType(p);
   }

   public void setWeighting(int p) {
      this.mEditorData.setWeighting(p);
   }

   public Map getAccountElements() {
      return this.mEditorData.getAccountElements();
   }

   public Map getBusinessElements() {
      return this.mEditorData.getBusinessElements();
   }

   public Set getDataTypes() {
      return this.mEditorData.getDataTypes();
   }

   public String getProfileDescription() {
      return this.mEditorData.getProfileDescription();
   }
}
