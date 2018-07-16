// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:15
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.impl.model.mapping;

import com.cedar.cp.api.base.MappingKey;
import com.cedar.cp.api.extsys.ExternalSystemRef;
import com.cedar.cp.api.model.ModelRef;
import com.cedar.cp.api.model.mapping.MappedCalendar;
import com.cedar.cp.api.model.mapping.MappedDimension;
import com.cedar.cp.api.model.mapping.MappedFinanceCube;
import com.cedar.cp.api.model.mapping.MappedModel;
import com.cedar.cp.dto.model.mapping.MappedModelImpl;
import com.cedar.cp.dto.model.mapping.MappedModelPK;
import com.cedar.cp.impl.base.BusinessProcessImpl;
import com.cedar.cp.impl.model.mapping.MappedModelEditorSessionImpl;
import java.io.IOException;
import java.io.Writer;
import java.util.List;

public class MappedModelAdapter implements MappedModel {

   private MappedModelImpl mEditorData;
   private MappedModelEditorSessionImpl mEditorSessionImpl;


   public MappedModelAdapter(MappedModelEditorSessionImpl e, MappedModelImpl editorData) {
      this.mEditorData = editorData;
      this.mEditorSessionImpl = e;
   }

   public void setPrimaryKey(Object key) {
      this.mEditorData.setPrimaryKey(key);
   }

   protected MappedModelEditorSessionImpl getEditorSessionImpl() {
      return this.mEditorSessionImpl;
   }

   protected MappedModelImpl getEditorData() {
      return this.mEditorData;
   }

   public Object getPrimaryKey() {
      return this.mEditorData.getPrimaryKey();
   }

   void setPrimaryKey(MappedModelPK paramKey) {
      this.mEditorData.setPrimaryKey(paramKey);
   }

   public int getModelId() {
      return this.mEditorData.getModelId();
   }

   public int getExternalSystemId() {
      return this.mEditorData.getExternalSystemId();
   }

   public String getCompanyVisId() {
      return this.mEditorData.getCompanyVisId();
   }

   public String getLedgerVisId() {
      return this.mEditorData.getLedgerVisId();
   }

   public ModelRef getOwningModelRef() {
      if(this.mEditorData.getOwningModelRef() != null) {
         try {
            ModelRef e = ((BusinessProcessImpl)this.getEditorSessionImpl().getBusinessProcess()).getConnection().getListHelper().getModelEntityRef(this.mEditorData.getOwningModelRef());
            this.mEditorData.setOwningModelRef(e);
            return e;
         } catch (Exception var2) {
            var2.printStackTrace();
            throw new RuntimeException(var2.getMessage());
         }
      } else {
         return null;
      }
   }

   public ExternalSystemRef getExternalSystemRef() {
      if(this.mEditorData.getExternalSystemRef() != null) {
         try {
            ExternalSystemRef e = ((BusinessProcessImpl)this.getEditorSessionImpl().getBusinessProcess()).getConnection().getListHelper().getExternalSystemEntityRef(this.mEditorData.getExternalSystemRef());
            this.mEditorData.setExternalSystemRef(e);
            return e;
         } catch (Exception var2) {
            var2.printStackTrace();
            throw new RuntimeException(var2.getMessage());
         }
      } else {
         return null;
      }
   }

   public void setOwningModelRef(ModelRef ref) {
      this.mEditorData.setOwningModelRef(ref);
   }

   public void setExternalSystemRef(ExternalSystemRef ref) {
      this.mEditorData.setExternalSystemRef(ref);
   }

   public void setModelId(int p) {
      this.mEditorData.setModelId(p);
   }

   public void setExternalSystemId(int p) {
      this.mEditorData.setExternalSystemId(p);
   }

   public void setCompanyVisId(String p) {
      this.mEditorData.setCompanyVisId(p);
   }

   public void setLedgerVisId(String p) {
      this.mEditorData.setLedgerVisId(p);
   }

   public boolean isNew() {
      return this.mEditorData.isNew();
   }

   public String getModelVisId() {
      return this.mEditorData.getModelVisId();
   }

   public String getModelDescription() {
      return this.mEditorData.getModelDescription();
   }

   public List<MappedDimension> getDimensionMappings() {
      return this.mEditorData.getDimensionMappings();
   }

   public MappedDimension findMappedDimension(MappingKey financeDimensionKey) {
      return this.mEditorData.findMappedDimension(financeDimensionKey);
   }

   public MappedCalendar getMappedCalendar() {
      return this.mEditorData.getMappedCalendar();
   }

   public List<MappedFinanceCube> getMappedFinanceCubes() {
      return this.mEditorData.getMappedFinanceCubes();
   }

   public boolean isRespAreaHierarchySelected() {
      return this.mEditorData.isRespAreaHierarchySelected();
   }

   public void writeXml(Writer out) throws IOException {
      this.mEditorData.writeXml(out);
   }

   public MappingKey getFinanceCompanyKey() {
      return this.mEditorData.getFinanceCompanyKey();
   }

   public MappingKey getFinanceLedgerKey() {
      return this.mEditorData.getFinanceLedgerKey();
   }
}
