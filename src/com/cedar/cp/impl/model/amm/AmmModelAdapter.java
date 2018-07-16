// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:13
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.impl.model.amm;

import com.cedar.cp.api.model.ModelRef;
import com.cedar.cp.api.model.amm.AmmDimensionMapping;
import com.cedar.cp.api.model.amm.AmmFinanceCubeMapping;
import com.cedar.cp.api.model.amm.AmmModel;
import com.cedar.cp.dto.model.amm.AmmModelImpl;
import com.cedar.cp.dto.model.amm.AmmModelPK;
import com.cedar.cp.impl.base.BusinessProcessImpl;
import com.cedar.cp.impl.model.amm.AmmModelEditorSessionImpl;
import com.cedar.cp.util.xmlform.CalendarInfo;
import java.util.List;

public class AmmModelAdapter implements AmmModel {

   private AmmModelImpl mEditorData;
   private AmmModelEditorSessionImpl mEditorSessionImpl;


   public AmmModelAdapter(AmmModelEditorSessionImpl e, AmmModelImpl editorData) {
      this.mEditorData = editorData;
      this.mEditorSessionImpl = e;
   }

   public void setPrimaryKey(Object key) {
      this.mEditorData.setPrimaryKey(key);
   }

   protected AmmModelEditorSessionImpl getEditorSessionImpl() {
      return this.mEditorSessionImpl;
   }

   protected AmmModelImpl getEditorData() {
      return this.mEditorData;
   }

   public Object getPrimaryKey() {
      return this.mEditorData.getPrimaryKey();
   }

   void setPrimaryKey(AmmModelPK paramKey) {
      this.mEditorData.setPrimaryKey(paramKey);
   }

   public int getModelId() {
      return this.mEditorData.getModelId();
   }

   public int getSrcModelId() {
      return this.mEditorData.getSrcModelId();
   }

   public Integer getInvalidatedByTaskId() {
      return this.mEditorData.getInvalidatedByTaskId();
   }

   public ModelRef getTargetModelRef() {
      if(this.mEditorData.getTargetModelRef() != null) {
         try {
            ModelRef e = ((BusinessProcessImpl)this.getEditorSessionImpl().getBusinessProcess()).getConnection().getListHelper().getModelEntityRef(this.mEditorData.getTargetModelRef());
            this.mEditorData.setTargetModelRef(e);
            return e;
         } catch (Exception var2) {
            var2.printStackTrace();
            throw new RuntimeException(var2.getMessage());
         }
      } else {
         return null;
      }
   }

   public ModelRef getSourceModelRef() {
      if(this.mEditorData.getSourceModelRef() != null) {
         try {
            ModelRef e = ((BusinessProcessImpl)this.getEditorSessionImpl().getBusinessProcess()).getConnection().getListHelper().getModelEntityRef(this.mEditorData.getSourceModelRef());
            this.mEditorData.setSourceModelRef(e);
            return e;
         } catch (Exception var2) {
            var2.printStackTrace();
            throw new RuntimeException(var2.getMessage());
         }
      } else {
         return null;
      }
   }

   public void setTargetModelRef(ModelRef ref) {
      this.mEditorData.setTargetModelRef(ref);
   }

   public void setSourceModelRef(ModelRef ref) {
      this.mEditorData.setSourceModelRef(ref);
   }

   public void setModelId(int p) {
      this.mEditorData.setModelId(p);
   }

   public void setSrcModelId(int p) {
      this.mEditorData.setSrcModelId(p);
   }

   public void setInvalidatedByTaskId(Integer p) {
      this.mEditorData.setInvalidatedByTaskId(p);
   }

   public boolean isNew() {
      return this.mEditorData.isNew();
   }

   public boolean isModelLocked() {
      return this.mEditorData.isModelLocked();
   }

   public boolean isDimsLocked() {
      return this.mEditorData.isDimsLocked();
   }

   public int getMappingIndex() {
      return this.mEditorData.getMappingIndex();
   }

   public AmmDimensionMapping getMapping(int index) {
      return this.mEditorData.getMapping(index);
   }

   public AmmDimensionMapping getNextMapping() {
      return this.mEditorData.getNextMapping();
   }

   public List<AmmDimensionMapping> getDimMappings() {
      return this.mEditorData.getDimMappings();
   }

   public List<AmmFinanceCubeMapping> getFinanceCubeMappings() {
      return this.mEditorData.getFinanceCubeMappings();
   }

   public CalendarInfo getSourceInfo() {
      return this.mEditorData.getSourceInfo();
   }
}
