// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:06:00
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.model.cc;

import com.cedar.cp.api.datatype.DataTypeRef;
import com.cedar.cp.api.dimension.DimensionRef;
import com.cedar.cp.api.dimension.StructureElementRef;
import com.cedar.cp.api.model.cc.CcDeploymentLine;
import com.cedar.cp.api.model.cc.CcMappingLine;
import com.cedar.cp.dto.model.cc.CcMappingLinePK;
import java.util.ArrayList;
import java.util.List;

public class CcMappingLineImpl implements CcMappingLine, Cloneable {

   private CcMappingLinePK mKey;
   private DataTypeRef mDataTypeRef;
   private CcDeploymentLine mDeploymentLine;
   private String mFormField;
   private List<StructureElementRef> mEntries;


   public CcMappingLineImpl(CcMappingLinePK key) {
      this.mKey = key;
      this.mEntries = new ArrayList();
   }

   public CcMappingLineImpl(CcMappingLinePK key, DataTypeRef dataTypeRef, CcDeploymentLine deploymentLine, String formField, List<StructureElementRef> entries) {
      this.mKey = key;
      this.mDataTypeRef = dataTypeRef;
      this.mDeploymentLine = deploymentLine;
      this.mFormField = formField;
      this.mEntries = entries;
   }

   public Object getKey() {
      return this.mKey;
   }

   public DataTypeRef getDataType() {
      return this.mDataTypeRef;
   }

   public CcDeploymentLine getDeploymentLine() {
      return this.mDeploymentLine;
   }

   public String getFormField() {
      return this.mFormField;
   }

   public void setDataTypeRef(DataTypeRef dataTypeRef) {
      this.mDataTypeRef = dataTypeRef;
   }

   public void setFormField(String formField) {
      this.mFormField = formField;
   }

   public void setDeploymentLine(CcDeploymentLine deploymentLine) {
      this.mDeploymentLine = deploymentLine;
   }

   public void setEntries(List<StructureElementRef> entries) {
      this.mEntries = entries;
   }

   public List<StructureElementRef> getEntries() {
      return this.mEntries;
   }

   public DimensionRef[] getExplicitMappingDimensionRefs() {
      return this.mDeploymentLine.getDeployment().getExplicitMappingDimensionRefs();
   }

   public Object clone() throws CloneNotSupportedException {
      CcMappingLineImpl copy = (CcMappingLineImpl)super.clone();
      copy.mKey = this.mKey;
      copy.mDataTypeRef = this.mDataTypeRef;
      copy.mDeploymentLine = this.mDeploymentLine;
      copy.mFormField = this.mFormField;
      copy.mEntries = new ArrayList(this.mEntries);
      return copy;
   }
}
