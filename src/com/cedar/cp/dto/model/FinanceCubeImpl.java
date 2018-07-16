// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:56
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.model;

import com.cedar.cp.api.base.EntityRef;
import com.cedar.cp.api.datatype.DataTypeRef;
import com.cedar.cp.api.dimension.DimensionRef;
import com.cedar.cp.api.model.FinanceCube;
import com.cedar.cp.api.model.ModelRef;
import com.cedar.cp.api.model.RollUpRuleLine;
import com.cedar.cp.dto.datatype.DataTypeRefImpl;
import com.cedar.cp.dto.model.FinanceCubeCK;
import com.cedar.cp.dto.model.FinanceCubePK;
import com.cedar.cp.dto.model.RollUpRuleLineImpl;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

public class FinanceCubeImpl implements FinanceCube, Serializable, Cloneable {

   private boolean mCreateCubeFomrulaRuntime;
   private boolean mDropCubeFormulaRuntime;
   private HashMap<DataTypeRef, Timestamp> mDataTypes;
   private Set<DataTypeRef> mMappedDataTypes;
   private Set<DataTypeRef> mAggregatedDataTypes;
   private List<RollUpRuleLine> mRollUpRuleLines = new ArrayList();
   private DimensionRef[] mDimensions;
   private boolean mSubmitChangeManagement;
   private boolean mInsideChangeManagement;
   private boolean mChangeManagementOutstanding;
   private Timestamp mUpdatedTime;
   private Object mPrimaryKey;
   private String mVisId;
   private String mDescription;
   private Integer mLockedByTaskId;
   private boolean mHasData;
   private boolean mAudited;
   private boolean mCubeFormulaEnabled;
   private int mVersionNum;
   private ModelRef mModelRef;
   private boolean isGlobal = false;

   public FinanceCubeImpl(Object paramKey) {
      this.mPrimaryKey = paramKey;
      this.mVisId = "";
      this.mDescription = "";
      this.mLockedByTaskId = null;
      this.mHasData = false;
      this.mAudited = false;
      this.mCubeFormulaEnabled = false;
      this.mDataTypes = new HashMap();
      this.mMappedDataTypes = new TreeSet();
      this.mAggregatedDataTypes = new TreeSet();
   }

   public Object getPrimaryKey() {
      return this.mPrimaryKey;
   }

   public void setPrimaryKey(Object paramKey) {
      this.mPrimaryKey = (FinanceCubePK)paramKey;
   }

   public void setPrimaryKey(FinanceCubeCK paramKey) {
      this.mPrimaryKey = paramKey;
   }

   public String getVisId() {
      return this.mVisId;
   }

   public String getDescription() {
      return this.mDescription;
   }

   public Integer getLockedByTaskId() {
      return this.mLockedByTaskId;
   }

   public boolean isHasData() {
      return this.mHasData;
   }

   public boolean isAudited() {
      return this.mAudited;
   }

   public boolean isCubeFormulaEnabled() {
      return this.mCubeFormulaEnabled;
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

   public void setVisId(String paramVisId) {
      this.mVisId = paramVisId;
   }

   public void setDescription(String paramDescription) {
      this.mDescription = paramDescription;
   }

   public void setLockedByTaskId(Integer paramLockedByTaskId) {
      this.mLockedByTaskId = paramLockedByTaskId;
   }

   public void setHasData(boolean paramHasData) {
      this.mHasData = paramHasData;
   }

   public void setAudited(boolean paramAudited) {
      this.mAudited = paramAudited;
   }

   public void setCubeFormulaEnabled(boolean paramCubeFormulaEnabled) {
      this.mCubeFormulaEnabled = paramCubeFormulaEnabled;
   }

   public void addSelectedDataTypeRef(DataTypeRef datatypeRef, Timestamp cubeLastUpdated) {
      this.mDataTypes.put(datatypeRef, cubeLastUpdated);
   }

   public void removeSelectedDatatypeRef(DataTypeRef datatypeRef) {
      this.mDataTypes.remove(datatypeRef);
   }

   public Map<DataTypeRef, Timestamp> getSelectedDataTypeRefs() {
      return this.mDataTypes;
   }

   public boolean hasDataType(DataTypeRef dt) {
      return this.mDataTypes.containsKey(dt);
   }

   public Set getMappedDataTypes() {
      return this.mMappedDataTypes;
   }

   public Set getAggregatedDataTypes() {
      return this.mAggregatedDataTypes;
   }

   public void setMappedDataTypes(Set mappedDataTypes) {
      this.mMappedDataTypes = mappedDataTypes;
   }

   public boolean isMappedDataType(EntityRef dtr) {
      Iterator i$ = this.mMappedDataTypes.iterator();

      DataTypeRef mdtr;
      do {
         if(!i$.hasNext()) {
            return false;
         }

         mdtr = (DataTypeRef)i$.next();
      } while(!mdtr.equals(dtr));

      return true;
   }

   public boolean isAggregatedDataType(EntityRef dtr) {
      Iterator i$ = this.mAggregatedDataTypes.iterator();

      DataTypeRef mdtr;
      do {
         if(!i$.hasNext()) {
            return false;
         }

         mdtr = (DataTypeRef)i$.next();
      } while(!mdtr.equals(dtr));

      return true;
   }

   public void addRollUpRuleLine(RollUpRuleLineImpl line) {
      this.mRollUpRuleLines.add(line);
   }

   public boolean removeRollUpRuleLine(RollUpRuleLineImpl line) {
      return this.mRollUpRuleLines.remove(line);
   }

   public RollUpRuleLine getRollUpRuleLine(DataTypeRef dataType) {
      Iterator i$ = this.mRollUpRuleLines.iterator();

      RollUpRuleLine line;
      do {
         if(!i$.hasNext()) {
            return null;
         }

         line = (RollUpRuleLine)i$.next();
      } while(!line.getDataType().equals(dataType));

      return line;
   }

   public RollUpRuleLine getRollUpRuleLine(int dataTypeId) {
      Iterator i$ = this.mRollUpRuleLines.iterator();

      RollUpRuleLine line;
      do {
         if(!i$.hasNext()) {
            return null;
         }

         line = (RollUpRuleLine)i$.next();
      } while(((DataTypeRefImpl)line.getDataType()).getDataTypePK().getDataTypeId() != dataTypeId);

      return line;
   }

   public List<RollUpRuleLine> getRollUpRuleLines() {
      return this.mRollUpRuleLines;
   }

   public void setDimensions(DimensionRef[] dimensions) {
      this.mDimensions = dimensions;
   }

   public DimensionRef[] getDimensions() {
      return this.mDimensions;
   }

   public boolean isSubmitChangeManagement() {
      return this.mSubmitChangeManagement;
   }

   public void setSubmitChangeManagement(boolean submitChangeManagement) {
      this.mSubmitChangeManagement = submitChangeManagement;
   }

   public boolean isInsideChangeManagement() {
      return this.mInsideChangeManagement;
   }

   public void setInsideChangeManagement(boolean insideChangeManagement) {
      this.mInsideChangeManagement = insideChangeManagement;
   }

   public boolean isNew() {
      return this.mPrimaryKey == null;
   }

   public boolean isChangeManagementOutstanding() {
      return this.mChangeManagementOutstanding;
   }

   public Timestamp getUpdatedTime() {
      return this.mUpdatedTime;
   }

   public void setUpdatedTime(Timestamp t) {
      this.mUpdatedTime = t;
   }

   public void setChangeManagementOutstanding(boolean changeManagementOutstanding) {
      this.mChangeManagementOutstanding = changeManagementOutstanding;
   }

   public boolean isCreateCubeFomrulaRuntime() {
      return this.mCreateCubeFomrulaRuntime;
   }

   public void setCreateCubeFomrulaRuntime(boolean createCubeFomrulaRuntime) {
      this.mCreateCubeFomrulaRuntime = createCubeFomrulaRuntime;
   }

   public boolean isDropCubeFormulaRuntime() {
      return this.mDropCubeFormulaRuntime;
   }

   public void setDropCubeFormulaRuntime(boolean dropCubeFormulaRuntime) {
      this.mDropCubeFormulaRuntime = dropCubeFormulaRuntime;
   }
   
	public boolean isGlobal() {
		return this.isGlobal;
	}

	public void setIsGlobal(boolean global) {
		this.isGlobal = global;
	}
}
