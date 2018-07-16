// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:48
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.datatype;

import com.cedar.cp.api.datatype.DataType;
import com.cedar.cp.api.datatype.DataTypeRef;
import com.cedar.cp.dto.datatype.DataTypePK;
import com.cedar.cp.dto.datatype.DataTypeRefImpl;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class DataTypeImpl implements DataType, Serializable, Cloneable {

   private transient DataTypeRefImpl mDataTypeRef;
   private List mDataTypeRefs = new ArrayList();
   private boolean mDeployed;
   private boolean[] mPropagationRules;
   private Object mPrimaryKey;
   private String mVisId;
   private String mDescription;
   private boolean mReadOnlyFlag;
   private boolean mAvailableForImport;
   private boolean mAvailableForExport;
   private int mSubType;
   private String mFormulaExpr;
   private Integer mMeasureClass;
   private Integer mMeasureLength;
   private Integer mMeasureScale;
   private String mMeasureValidation;
   private int mVersionNum;


   public DataTypeImpl(Object paramKey) {
      this.mPrimaryKey = paramKey;
      this.mVisId = "";
      this.mDescription = "";
      this.mReadOnlyFlag = false;
      this.mAvailableForImport = false;
      this.mAvailableForExport = false;
      this.mSubType = 0;
      this.mFormulaExpr = "";
      this.mMeasureClass = null;
      this.mMeasureLength = null;
      this.mMeasureScale = null;
      this.mMeasureValidation = "";
   }

   public Object getPrimaryKey() {
      return this.mPrimaryKey;
   }

   public void setPrimaryKey(Object paramKey) {
      this.mPrimaryKey = (DataTypePK)paramKey;
   }

   public String getVisId() {
      return this.mVisId;
   }

   public String getDescription() {
      return this.mDescription;
   }

   public boolean isReadOnlyFlag() {
      return this.mReadOnlyFlag;
   }

   public boolean isAvailableForImport() {
      return this.mAvailableForImport;
   }

   public boolean isAvailableForExport() {
      return this.mAvailableForExport;
   }

   public int getSubType() {
      return this.mSubType;
   }

   public String getFormulaExpr() {
      return this.mFormulaExpr;
   }

   public Integer getMeasureClass() {
      return this.mMeasureClass;
   }

   public Integer getMeasureLength() {
      return this.mMeasureLength;
   }

   public Integer getMeasureScale() {
      return this.mMeasureScale;
   }

   public String getMeasureValidation() {
      return this.mMeasureValidation;
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

   public void setReadOnlyFlag(boolean paramReadOnlyFlag) {
      this.mReadOnlyFlag = paramReadOnlyFlag;
   }

   public void setAvailableForImport(boolean paramAvailableForImport) {
      this.mAvailableForImport = paramAvailableForImport;
   }

   public void setAvailableForExport(boolean paramAvailableForExport) {
      this.mAvailableForExport = paramAvailableForExport;
   }

   public void setSubType(int paramSubType) {
      this.mSubType = paramSubType;
   }

   public void setFormulaExpr(String paramFormulaExpr) {
      this.mFormulaExpr = paramFormulaExpr;
   }

   public void setMeasureClass(Integer paramMeasureClass) {
      this.mMeasureClass = paramMeasureClass;
   }

   public void setMeasureLength(Integer paramMeasureLength) {
      this.mMeasureLength = paramMeasureLength;
   }

   public void setMeasureScale(Integer paramMeasureScale) {
      this.mMeasureScale = paramMeasureScale;
   }

   public void setMeasureValidation(String paramMeasureValidation) {
      this.mMeasureValidation = paramMeasureValidation;
   }

   public void setDataTypeRefs(List dataTypeRefs) {
      this.mDataTypeRefs = dataTypeRefs;
   }

   public List getDataTypeRefs() {
      return this.mDataTypeRefs;
   }

   public boolean isDeployed() {
      return this.mDeployed;
   }

   public void setDeployed(boolean deployed) {
      this.mDeployed = deployed;
   }

   public DataTypeRef getDataTypeRef() {
      if(this.mDataTypeRef == null) {
         this.mDataTypeRef = new DataTypeRefImpl((DataTypePK)this.mPrimaryKey, this.mVisId, this.mDescription, this.mSubType, this.mMeasureClass, this.mMeasureLength);
      }

      return this.mDataTypeRef;
   }

   public String toString() {
      return this.mVisId;
   }

   public boolean propagatesInDimension(int dimIndex) throws IllegalStateException {
      if(this.mSubType != 4) {
         return true;
      } else if(!this.isMeasureNumeric()) {
         return false;
      } else if(this.mPropagationRules == null) {
         throw new IllegalStateException("The data type was not created within the context of a finance cube");
      } else {
         return this.mPropagationRules[dimIndex];
      }
   }

   public void setPropagationRules(boolean[] rules) {
      this.mPropagationRules = rules;
   }

   public boolean isFinanceValue() {
      return this.getSubType() == 0 || this.getSubType() == 1 || this.getSubType() == 2;
   }

   public boolean isMeasure() {
      return this.getSubType() == 4;
   }

   public boolean isMeasureNumeric() {
      return this.isMeasure() && this.getMeasureClass().equals(Integer.valueOf(1));
   }

   public boolean isMeasureString() {
      return this.isMeasure() && this.getMeasureClass().equals(Integer.valueOf(0));
   }

   public boolean isMeasureBoolean() {
      return this.isMeasure() && this.getMeasureClass().equals(Integer.valueOf(5));
   }

   public boolean isMeasureDate() {
      return this.isMeasure() && this.getMeasureClass().equals(Integer.valueOf(3));
   }

   public boolean isMeasureDateTime() {
      return this.isMeasure() && this.getMeasureClass().equals(Integer.valueOf(4));
   }

   public boolean isMeasureTime() {
      return this.isMeasure() && this.getMeasureClass().equals(Integer.valueOf(2));
   }
}
