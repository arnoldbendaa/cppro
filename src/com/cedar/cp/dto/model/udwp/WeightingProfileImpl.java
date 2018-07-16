// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:06:03
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.model.udwp;

import com.cedar.cp.api.datatype.DataTypeRef;
import com.cedar.cp.api.model.ModelRef;
import com.cedar.cp.api.model.udwp.WeightingProfile;
import com.cedar.cp.dto.model.udwp.WeightingProfileCK;
import com.cedar.cp.dto.model.udwp.WeightingProfilePK;
import java.io.Serializable;

public class WeightingProfileImpl implements WeightingProfile, Serializable, Cloneable {

   private static final int VIS_ID_COL_NO = 0;
   private static final int DESCR_COL_NO = 1;
   private static final int WEIGHTING_COL_NO = 2;
   private Object[][] mWeigthingInfo;
   private int mYearStartMonth;
   private DataTypeRef mDynamicDataType;
   private Object mPrimaryKey;
   private int mModelId;
   private String mVisId;
   private String mDescription;
   private int mStartLevel;
   private int mLeafLevel;
   private int mProfileType;
   private int mDynamicOffset;
   private Integer mDynamicDataTypeId;
   private Boolean mDynamicEsIfWfbtoz;
   private int mVersionNum;
   private ModelRef mModelRef;


   public WeightingProfileImpl(Object paramKey) {
      this.mPrimaryKey = paramKey;
      this.mModelId = 0;
      this.mVisId = "";
      this.mDescription = "";
      this.mStartLevel = 0;
      this.mLeafLevel = 0;
      this.mProfileType = 0;
      this.mDynamicOffset = 0;
      this.mDynamicDataTypeId = null;
      this.mDynamicEsIfWfbtoz = null;
      this.mStartLevel = 0;
      this.mLeafLevel = 3;
      this.mProfileType = 0;
      this.mYearStartMonth = 0;
   }

   public Object getPrimaryKey() {
      return this.mPrimaryKey;
   }

   public void setPrimaryKey(Object paramKey) {
      this.mPrimaryKey = (WeightingProfilePK)paramKey;
   }

   public void setPrimaryKey(WeightingProfileCK paramKey) {
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

   public int getStartLevel() {
      return this.mStartLevel;
   }

   public int getLeafLevel() {
      return this.mLeafLevel;
   }

   public int getProfileType() {
      return this.mProfileType;
   }

   public int getDynamicOffset() {
      return this.mDynamicOffset;
   }

   public Integer getDynamicDataTypeId() {
      return this.mDynamicDataTypeId;
   }

   public Boolean getDynamicEsIfWfbtoz() {
      return this.mDynamicEsIfWfbtoz;
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

   public void setStartLevel(int paramStartLevel) {
      this.mStartLevel = paramStartLevel;
   }

   public void setLeafLevel(int paramLeafLevel) {
      this.mLeafLevel = paramLeafLevel;
   }

   public void setProfileType(int paramProfileType) {
      this.mProfileType = paramProfileType;
   }

   public void setDynamicOffset(int paramDynamicOffset) {
      this.mDynamicOffset = paramDynamicOffset;
   }

   public void setDynamicDataTypeId(Integer paramDynamicDataTypeId) {
      this.mDynamicDataTypeId = paramDynamicDataTypeId;
   }

   public void setDynamicEsIfWfbtoz(Boolean paramDynamicEsIfWfbtoz) {
      this.mDynamicEsIfWfbtoz = paramDynamicEsIfWfbtoz;
   }

   public int getNumWeightingRows() {
      return this.mWeigthingInfo != null?this.mWeigthingInfo.length:0;
   }

   public String getStructureElementVisId(int index) {
      return this.mWeigthingInfo != null?(String)this.mWeigthingInfo[index][0]:null;
   }

   public String getStructureElementDescription(int index) {
      return this.mWeigthingInfo != null?(String)this.mWeigthingInfo[index][1]:null;
   }

   public int getWeighting(int index) {
      return this.mWeigthingInfo != null?((Integer)this.mWeigthingInfo[index][2]).intValue():0;
   }

   public void setWeighting(int index, int value) {
      if(index >= 0 && index < this.mWeigthingInfo.length) {
         this.mWeigthingInfo[index][2] = new Integer(value);
      }

   }

   public void setWeightingInfo(Object[][] weightingInfo) {
      this.mWeigthingInfo = weightingInfo;
   }

   public int getYearStartMonth() {
      return this.mYearStartMonth;
   }

   public void setYearStartMonth(int yearStartMonth) {
      this.mYearStartMonth = yearStartMonth;
   }

   public DataTypeRef getDynamicDataType() {
      return this.mDynamicDataType;
   }

   public void setDynamicDataType(DataTypeRef dynamicDataType) {
      this.mDynamicDataType = dynamicDataType;
   }
}
