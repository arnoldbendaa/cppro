// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:12
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.impl.model;

import com.cedar.cp.api.base.EntityList;
import com.cedar.cp.api.dimension.DimensionRef;
import com.cedar.cp.api.model.CellCalc;
import com.cedar.cp.api.model.ModelRef;
import com.cedar.cp.dto.dimension.DimensionPK;
import com.cedar.cp.dto.model.CellCalcImpl;
import com.cedar.cp.dto.model.CellCalcPK;
import com.cedar.cp.impl.base.BusinessProcessImpl;
import com.cedar.cp.impl.model.CellCalcEditorSessionImpl;
import com.cedar.cp.util.DefaultValueMapping;
import com.cedar.cp.util.ValueMapping;
import java.util.Map;

public class CellCalcAdapter implements CellCalc {

   private CellCalcImpl mEditorData;
   private CellCalcEditorSessionImpl mEditorSessionImpl;


   public CellCalcAdapter(CellCalcEditorSessionImpl e, CellCalcImpl editorData) {
      this.mEditorData = editorData;
      this.mEditorSessionImpl = e;
   }

   public void setPrimaryKey(Object key) {
      this.mEditorData.setPrimaryKey(key);
   }

   protected CellCalcEditorSessionImpl getEditorSessionImpl() {
      return this.mEditorSessionImpl;
   }

   protected CellCalcImpl getEditorData() {
      return this.mEditorData;
   }

   public Object getPrimaryKey() {
      return this.mEditorData.getPrimaryKey();
   }

   void setPrimaryKey(CellCalcPK paramKey) {
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

   public int getXmlformId() {
      return this.mEditorData.getXmlformId();
   }

   public int getAccessDefinitionId() {
      return this.mEditorData.getAccessDefinitionId();
   }

   public int getDataTypeId() {
      return this.mEditorData.getDataTypeId();
   }

   public boolean isSummaryPeriodAssociation() {
      return this.mEditorData.isSummaryPeriodAssociation();
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

   public void setXmlformId(int p) {
      this.mEditorData.setXmlformId(p);
   }

   public void setAccessDefinitionId(int p) {
      this.mEditorData.setAccessDefinitionId(p);
   }

   public void setDataTypeId(int p) {
      this.mEditorData.setDataTypeId(p);
   }

   public void setSummaryPeriodAssociation(boolean p) {
      this.mEditorData.setSummaryPeriodAssociation(p);
   }

   public ValueMapping getFormMapping() {
      Object def = this.mEditorData.getFormMapping();
      if(def == null) {
         EntityList fixed = ((BusinessProcessImpl)this.getEditorSessionImpl().getBusinessProcess()).getConnection().getListHelper().getAllFixedXMLForms();
         EntityList dyn = ((BusinessProcessImpl)this.getEditorSessionImpl().getBusinessProcess()).getConnection().getListHelper().getAllDynamicXMLForms();
         int fixedsize = fixed.getNumRows();
         int dynsize = dyn.getNumRows();
         int totalsize = fixedsize + dynsize;
         String[] lits = new String[totalsize + 1];
         Object[] values = new Object[totalsize + 1];
         lits[0] = "";
         values[0] = new Integer(0);
         int pos = 0;

         int i;
         for(i = 0; i < fixedsize; ++i) {
            ++pos;
            lits[pos] = fixed.getValueAt(i, "XmlForm").toString();
            values[pos] = (Integer)fixed.getValueAt(i, "XmlFormId");
         }

         for(i = 0; i < dynsize; ++i) {
            ++pos;
            lits[pos] = dyn.getValueAt(i, "XmlForm").toString();
            values[pos] = (Integer)dyn.getValueAt(i, "XmlFormId");
         }

         def = new DefaultValueMapping(lits, values);
      }

      return (ValueMapping)def;
   }

   public ValueMapping getAccessDef(int modelId) {
      Object def = this.mEditorData.getAccessDef(modelId);
      if(def == null) {
         EntityList access = ((BusinessProcessImpl)this.getEditorSessionImpl().getBusinessProcess()).getConnection().getListHelper().getCellCalcAccesDefs(modelId);
         int size = access.getNumRows();
         String[] lits = new String[size + 1];
         Object[] values = new Object[size + 1];
         lits[0] = "";
         values[0] = new Integer(0);

         for(int i = 0; i < size; ++i) {
            lits[i + 1] = access.getValueAt(i, "SecurityAccessDef").toString();
            values[i + 1] = access.getValueAt(i, "SecurityAccessDefId");
         }

         def = new DefaultValueMapping(lits, values);
         this.mEditorData.setAccessMapping((ValueMapping)def);
      }

      return (ValueMapping)def;
   }

   public ValueMapping getDataTypes(int modelId) {
      Object def = this.mEditorData.getDataTypes(modelId);
      if(def == null) {
         EntityList datatype = ((BusinessProcessImpl)this.getEditorSessionImpl().getBusinessProcess()).getConnection().getListHelper().getAllDataTypesAttachedToFinanceCubeForModel(modelId);
         int size = datatype.getNumRows();
         String[] lits = new String[size + 1];
         Object[] values = new Object[size + 1];
         lits[0] = "";
         values[0] = new Integer(0);

         for(int i = 0; i < size; ++i) {
            lits[i + 1] = datatype.getValueAt(i, "VisId").toString();
            values[i + 1] = new Integer(((Short)datatype.getValueAt(i, "DataTypeId")).intValue());
         }

         def = new DefaultValueMapping(lits, values);
         this.mEditorData.setDataTypeMpping((ValueMapping)def);
      }

      return (ValueMapping)def;
   }

   public Map getTableData() {
      return this.mEditorData.getTableData();
   }

   public EntityList getAccountDimensionElements() {
      EntityList list = this.mEditorData.getAccountDimensionElements();
      if(list == null) {
         EntityList dimension = ((BusinessProcessImpl)this.getEditorSessionImpl().getBusinessProcess()).getConnection().getListHelper().getModelDimensions(this.getModelId());
         int size = dimension.getNumRows();
         DimensionRef dim = null;
         boolean accountDimensionId = false;
         boolean type = false;

         for(int i = 0; i < size; ++i) {
            int var9 = ((Integer)dimension.getValueAt(i, "DimensionType")).intValue();
            if(var9 == 1) {
               dim = (DimensionRef)dimension.getValueAt(i, "Dimension");
               break;
            }
         }

         int var8 = ((DimensionPK)dim.getPrimaryKey()).getDimensionId();
         list = ((BusinessProcessImpl)this.getEditorSessionImpl().getBusinessProcess()).getConnection().getListHelper().getAllDimensionElementsForDimension(var8);
         this.mEditorData.setAccountDimensionElements(list);
      }

      return list;
   }
}
