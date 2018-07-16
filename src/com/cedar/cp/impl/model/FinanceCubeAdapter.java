// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:12
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.impl.model;

import com.cedar.cp.api.base.EntityList;
import com.cedar.cp.api.datatype.DataTypeRef;
import com.cedar.cp.api.dimension.DimensionRef;
import com.cedar.cp.api.model.FinanceCube;
import com.cedar.cp.api.model.ModelRef;
import com.cedar.cp.api.model.RollUpRuleLine;
import com.cedar.cp.dto.model.FinanceCubeImpl;
import com.cedar.cp.dto.model.FinanceCubePK;
import com.cedar.cp.dto.model.ModelRefImpl;
import com.cedar.cp.impl.base.BusinessProcessImpl;
import com.cedar.cp.impl.model.FinanceCubeEditorSessionImpl;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

public class FinanceCubeAdapter implements FinanceCube {

   private FinanceCubeImpl mEditorData;
   private FinanceCubeEditorSessionImpl mEditorSessionImpl;


   public FinanceCubeAdapter(FinanceCubeEditorSessionImpl e, FinanceCubeImpl editorData) {
      this.mEditorData = editorData;
      this.mEditorSessionImpl = e;
   }

   public void setPrimaryKey(Object key) {
      this.mEditorData.setPrimaryKey(key);
   }

   protected FinanceCubeEditorSessionImpl getEditorSessionImpl() {
      return this.mEditorSessionImpl;
   }

   protected FinanceCubeImpl getEditorData() {
      return this.mEditorData;
   }

   public Object getPrimaryKey() {
      return this.mEditorData.getPrimaryKey();
   }

   void setPrimaryKey(FinanceCubePK paramKey) {
      this.mEditorData.setPrimaryKey(paramKey);
   }

   public String getVisId() {
      return this.mEditorData.getVisId();
   }

   public String getDescription() {
      return this.mEditorData.getDescription();
   }

   public Integer getLockedByTaskId() {
      return this.mEditorData.getLockedByTaskId();
   }

   public boolean isHasData() {
      return this.mEditorData.isHasData();
   }

   public boolean isAudited() {
      return this.mEditorData.isAudited();
   }

   public boolean isCubeFormulaEnabled() {
      return this.mEditorData.isCubeFormulaEnabled();
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

   public void setVisId(String p) {
      this.mEditorData.setVisId(p);
   }

   public void setDescription(String p) {
      this.mEditorData.setDescription(p);
   }

   public void setLockedByTaskId(Integer p) {
      this.mEditorData.setLockedByTaskId(p);
   }

   public void setHasData(boolean p) {
      this.mEditorData.setHasData(p);
   }

   public void setAudited(boolean p) {
      this.mEditorData.setAudited(p);
   }

   public void setCubeFormulaEnabled(boolean p) {
      this.mEditorData.setCubeFormulaEnabled(p);
   }

   public Map<DataTypeRef, Timestamp> getSelectedDataTypeRefs() {
      return this.mEditorData.getSelectedDataTypeRefs();
   }

   public List<RollUpRuleLine> getRollUpRuleLines() {
      return this.mEditorData.getRollUpRuleLines();
   }

   public RollUpRuleLine getRollUpRuleLine(DataTypeRef dataType) {
      return this.mEditorData.getRollUpRuleLine(dataType);
   }

   public DimensionRef[] getDimensions() {
      if(this.mEditorData.getDimensions() == null) {
         int modelId = ((ModelRefImpl)this.mEditorData.getModelRef()).getModelPK().getModelId();
         EntityList dimensions = this.mEditorSessionImpl.getConnection().getModelsProcess().getModelDimensions(modelId);
         DimensionRef[] result = new DimensionRef[dimensions.getNumRows()];

         for(int row = 0; row < dimensions.getNumRows(); ++row) {
            result[row] = (DimensionRef)dimensions.getValueAt(row, "Dimension");
         }

         this.mEditorData.setDimensions(result);
      }

      return this.mEditorData.getDimensions();
   }

   public boolean isNew() {
      return this.mEditorData.isNew();
   }

   public boolean isChangeManagementOutstanding() {
      return this.mEditorData.isChangeManagementOutstanding();
   }

   public Timestamp getUpdatedTime() {
      return this.mEditorData.getUpdatedTime();
   }
}
