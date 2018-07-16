// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:56
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.model;

import com.cedar.cp.api.base.EntityList;
import com.cedar.cp.api.base.EntityRef;
import com.cedar.cp.api.model.CellCalc;
import com.cedar.cp.api.model.ModelRef;
import com.cedar.cp.dto.model.CellCalcCK;
import com.cedar.cp.dto.model.CellCalcPK;
import com.cedar.cp.util.ValueMapping;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class CellCalcImpl implements CellCalc, Serializable, Cloneable {

   private ValueMapping mFormMapping;
   private ValueMapping mAccessMapping;
   private ValueMapping mDataTypeMpping;
   private Map mFieldIds;
   private EntityList mAccountDimensionElements;
   private Object mPrimaryKey;
   private int mModelId;
   private String mVisId;
   private String mDescription;
   private int mXmlformId;
   private int mAccessDefinitionId;
   private int mDataTypeId;
   private boolean mSummaryPeriodAssociation;
   private int mVersionNum;
   private ModelRef mModelRef;


   public CellCalcImpl(Object paramKey) {
      this.mPrimaryKey = paramKey;
      this.mModelId = 0;
      this.mVisId = "";
      this.mDescription = "";
      this.mXmlformId = 0;
      this.mAccessDefinitionId = 0;
      this.mDataTypeId = 0;
      this.mSummaryPeriodAssociation = false;
      this.mFieldIds = new HashMap();
   }

   public Object getPrimaryKey() {
      return this.mPrimaryKey;
   }

   public void setPrimaryKey(Object paramKey) {
      this.mPrimaryKey = (CellCalcPK)paramKey;
   }

   public void setPrimaryKey(CellCalcCK paramKey) {
      this.mPrimaryKey = paramKey;
   }

   public int getModelId() {
      return this.mModelId;
   }

   public String getVisId() {
      return this.mVisId;
   }

   public String getDescription() {
      return this.mDescription;
   }

   public int getXmlformId() {
      return this.mXmlformId;
   }

   public int getAccessDefinitionId() {
      return this.mAccessDefinitionId;
   }

   public int getDataTypeId() {
      return this.mDataTypeId;
   }

   public boolean isSummaryPeriodAssociation() {
      return this.mSummaryPeriodAssociation;
   }

   public ModelRef getModelRef() {
      return this.mModelRef;
   }

   public void setModelRef(ModelRef ref) {
      this.mModelRef = ref;
   }

   public void setVersionNum(int p) {
      this.mVersionNum = p;
   }

   public int getVersionNum() {
      return this.mVersionNum;
   }

   public void setModelId(int paramModelId) {
      this.mModelId = paramModelId;
   }

   public void setVisId(String paramVisId) {
      this.mVisId = paramVisId;
   }

   public void setDescription(String paramDescription) {
      this.mDescription = paramDescription;
   }

   public void setXmlformId(int paramXmlformId) {
      this.mXmlformId = paramXmlformId;
   }

   public void setAccessDefinitionId(int paramAccessDefinitionId) {
      this.mAccessDefinitionId = paramAccessDefinitionId;
   }

   public void setDataTypeId(int paramDataTypeId) {
      this.mDataTypeId = paramDataTypeId;
   }

   public void setSummaryPeriodAssociation(boolean paramSummaryPeriodAssociation) {
      this.mSummaryPeriodAssociation = paramSummaryPeriodAssociation;
   }

   public ValueMapping getFormMapping() {
      return this.mFormMapping;
   }

   public void setFormMapping(ValueMapping mapping) {
      this.mFormMapping = mapping;
   }

   public ValueMapping getAccessDef(int modelId) {
      return this.mAccessMapping;
   }

   public void setAccessMapping(ValueMapping accessMapping) {
      this.mAccessMapping = accessMapping;
   }

   public ValueMapping getDataTypes(int modelId) {
      return this.mDataTypeMpping;
   }

   public void setDataTypeMpping(ValueMapping dataTypeMpping) {
      this.mDataTypeMpping = dataTypeMpping;
   }

   public void addTableData(EntityRef ref, String id) {
      this.mFieldIds.put(ref, id);
   }

   public void removeTableData(EntityRef ref) {
      this.mFieldIds.remove(ref);
   }

   public Map getTableData() {
      return this.mFieldIds;
   }

   public EntityList getAccountDimensionElements() {
      return this.mAccountDimensionElements;
   }

   public void setAccountDimensionElements(EntityList elements) {
      this.mAccountDimensionElements = elements;
   }
}
