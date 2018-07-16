// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:14
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.impl.model.cc;

import com.cedar.cp.api.base.EntityList;
import com.cedar.cp.api.dimension.DimensionRef;
import com.cedar.cp.api.model.ModelRef;
import com.cedar.cp.api.model.cc.CcDeployment;
import com.cedar.cp.api.model.cc.CcDeploymentLine;
import com.cedar.cp.api.model.cc.CcMappingLine;
import com.cedar.cp.dto.model.ModelRefImpl;
import com.cedar.cp.dto.model.cc.CcDeploymentImpl;
import com.cedar.cp.dto.model.cc.CcDeploymentPK;
import com.cedar.cp.impl.base.BusinessProcessImpl;
import com.cedar.cp.impl.model.cc.CcDeploymentEditorSessionImpl;
import com.cedar.cp.util.DefaultValueMapping;
import com.cedar.cp.util.ValueMapping;
import java.util.ArrayList;
import java.util.List;

public class CcDeploymentAdapter implements CcDeployment {

   private CcDeploymentImpl mEditorData;
   private CcDeploymentEditorSessionImpl mEditorSessionImpl;


   public CcDeploymentAdapter(CcDeploymentEditorSessionImpl e, CcDeploymentImpl editorData) {
      this.mEditorData = editorData;
      this.mEditorSessionImpl = e;
   }

   public void setPrimaryKey(Object key) {
      this.mEditorData.setPrimaryKey(key);
   }

   protected CcDeploymentEditorSessionImpl getEditorSessionImpl() {
      return this.mEditorSessionImpl;
   }

   protected CcDeploymentImpl getEditorData() {
      return this.mEditorData;
   }

   public Object getPrimaryKey() {
      return this.mEditorData.getPrimaryKey();
   }

   void setPrimaryKey(CcDeploymentPK paramKey) {
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

   public Integer[] getDimContextArray() {
      return this.mEditorData.getDimContextArray();
   }

   public void setDimContextArray(Integer[] p) {
      this.mEditorData.setDimContextArray(p);
   }

   public Integer getDimContext0() {
      return this.mEditorData.getDimContext0();
   }

   public Integer getDimContext1() {
      return this.mEditorData.getDimContext1();
   }

   public Integer getDimContext2() {
      return this.mEditorData.getDimContext2();
   }

   public Integer getDimContext3() {
      return this.mEditorData.getDimContext3();
   }

   public Integer getDimContext4() {
      return this.mEditorData.getDimContext4();
   }

   public Integer getDimContext5() {
      return this.mEditorData.getDimContext5();
   }

   public Integer getDimContext6() {
      return this.mEditorData.getDimContext6();
   }

   public Integer getDimContext7() {
      return this.mEditorData.getDimContext7();
   }

   public Integer getDimContext8() {
      return this.mEditorData.getDimContext8();
   }

   public Integer getDimContext9() {
      return this.mEditorData.getDimContext9();
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

   public void setDimContext0(Integer p) {
      this.mEditorData.setDimContext0(p);
   }

   public void setDimContext1(Integer p) {
      this.mEditorData.setDimContext1(p);
   }

   public void setDimContext2(Integer p) {
      this.mEditorData.setDimContext2(p);
   }

   public void setDimContext3(Integer p) {
      this.mEditorData.setDimContext3(p);
   }

   public void setDimContext4(Integer p) {
      this.mEditorData.setDimContext4(p);
   }

   public void setDimContext5(Integer p) {
      this.mEditorData.setDimContext5(p);
   }

   public void setDimContext6(Integer p) {
      this.mEditorData.setDimContext6(p);
   }

   public void setDimContext7(Integer p) {
      this.mEditorData.setDimContext7(p);
   }

   public void setDimContext8(Integer p) {
      this.mEditorData.setDimContext8(p);
   }

   public void setDimContext9(Integer p) {
      this.mEditorData.setDimContext9(p);
   }

   public List<CcDeploymentLine> getDeploymentLines() {
      return this.mEditorData.getDeploymentLines();
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

   public DimensionRef[] getDimensionRefs() {
      if(this.mEditorData.getDimensionRefs() == null) {
         int modelId = ((ModelRefImpl)this.mEditorData.getModelRef()).getModelPK().getModelId();
         EntityList dimensions = this.getEditorSessionImpl().getConnection().getModelsProcess().getModelDimensions(modelId);
         ArrayList dimensionRefs = new ArrayList();

         for(int i = 0; i < dimensions.getNumRows(); ++i) {
            dimensionRefs.add((DimensionRef)dimensions.getValueAt(i, "Dimension"));
         }

         this.mEditorData.setDimensionRefs((DimensionRef[])dimensionRefs.toArray(new DimensionRef[0]));
      }

      return this.mEditorData.getDimensionRefs();
   }

   public DimensionRef[] getExplicitMappingDimensionRefs() {
      DimensionRef[] allDimensionRefs = this.getDimensionRefs();
      Integer[] dimContext = this.mEditorData.getDimContextArray();
      ArrayList mappingDimensions = new ArrayList();

      for(int i = 0; i < dimContext.length; ++i) {
         if(dimContext[i] != null && dimContext[i].intValue() == 1) {
            mappingDimensions.add(allDimensionRefs[i]);
         }
      }

      return (DimensionRef[])mappingDimensions.toArray(new DimensionRef[0]);
   }

   public DimensionRef[] getMappingDimensions() {
      DimensionRef[] allDimensionRefs = this.mEditorData.getDimensionRefs();
      Integer[] dimContext = this.mEditorData.getDimContextArray();
      ArrayList mappingDimensions = new ArrayList();

      for(int i = 0; i < dimContext.length; ++i) {
         if(dimContext[i].intValue() == 1) {
            mappingDimensions.add(allDimensionRefs[i]);
         }
      }

      return (DimensionRef[])mappingDimensions.toArray(new DimensionRef[0]);
   }

   public List<CcMappingLine> getAllMappingLines() {
      return this.mEditorData.getAllMappingLines();
   }
}
