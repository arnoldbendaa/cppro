// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:16
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.impl.model.udwp;

import com.cedar.cp.api.datatype.DataTypeRef;
import com.cedar.cp.api.model.ModelRef;
import com.cedar.cp.api.model.udwp.WeightingProfile;
import com.cedar.cp.dto.model.udwp.WeightingProfileImpl;
import com.cedar.cp.dto.model.udwp.WeightingProfilePK;
import com.cedar.cp.impl.base.BusinessProcessImpl;
import com.cedar.cp.impl.model.udwp.WeightingProfileEditorSessionImpl;

public class WeightingProfileAdapter implements WeightingProfile {

   private WeightingProfileImpl mEditorData;
   private WeightingProfileEditorSessionImpl mEditorSessionImpl;


   public WeightingProfileAdapter(WeightingProfileEditorSessionImpl e, WeightingProfileImpl editorData) {
      this.mEditorData = editorData;
      this.mEditorSessionImpl = e;
   }

   public void setPrimaryKey(Object key) {
      this.mEditorData.setPrimaryKey(key);
   }

   protected WeightingProfileEditorSessionImpl getEditorSessionImpl() {
      return this.mEditorSessionImpl;
   }

   protected WeightingProfileImpl getEditorData() {
      return this.mEditorData;
   }

   public Object getPrimaryKey() {
      return this.mEditorData.getPrimaryKey();
   }

   void setPrimaryKey(WeightingProfilePK paramKey) {
      this.mEditorData.setPrimaryKey(paramKey);
   }

   public int getModelId() {
      return this.mEditorData.getModelId();
   }

   public String getVisId() {
      return this.mEditorData.getVisId();
   }

   public String getDescription() {
      return this.mEditorData.getDescription();
   }

   public int getStartLevel() {
      return this.mEditorData.getStartLevel();
   }

   public int getLeafLevel() {
      return this.mEditorData.getLeafLevel();
   }

   public int getProfileType() {
      return this.mEditorData.getProfileType();
   }

   public int getDynamicOffset() {
      return this.mEditorData.getDynamicOffset();
   }

   public Integer getDynamicDataTypeId() {
      return this.mEditorData.getDynamicDataTypeId();
   }

   public Boolean getDynamicEsIfWfbtoz() {
      return this.mEditorData.getDynamicEsIfWfbtoz();
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

   public void setModelRef(ModelRef ref) {
      this.mEditorData.setModelRef(ref);
   }

   public void setModelId(int p) {
      this.mEditorData.setModelId(p);
   }

   public void setVisId(String p) {
      this.mEditorData.setVisId(p);
   }

   public void setDescription(String p) {
      this.mEditorData.setDescription(p);
   }

   public void setStartLevel(int p) {
      this.mEditorData.setStartLevel(p);
   }

   public void setLeafLevel(int p) {
      this.mEditorData.setLeafLevel(p);
   }

   public void setProfileType(int p) {
      this.mEditorData.setProfileType(p);
   }

   public void setDynamicOffset(int p) {
      this.mEditorData.setDynamicOffset(p);
   }

   public void setDynamicDataTypeId(Integer p) {
      this.mEditorData.setDynamicDataTypeId(p);
   }

   public void setDynamicEsIfWfbtoz(Boolean p) {
      this.mEditorData.setDynamicEsIfWfbtoz(p);
   }

   public int getNumWeightingRows() {
      return this.mEditorData.getNumWeightingRows();
   }

   public String getStructureElementVisId(int index) {
      return this.mEditorData.getStructureElementVisId(index);
   }

   public String getStructureElementDescription(int index) {
      return this.mEditorData.getStructureElementDescription(index);
   }

   public int getWeighting(int index) {
      return this.mEditorData.getWeighting(index);
   }

   public DataTypeRef getDynamicDataType() {
      return this.mEditorData.getDynamicDataType();
   }
}
