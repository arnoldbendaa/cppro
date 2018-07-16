// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:08:18
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.impl.datatype;

import com.cedar.cp.api.datatype.DataTypeRef;
import com.cedar.cp.dto.datatype.DataTypePK;
import com.cedar.cp.dto.datatype.DataTypeRefImpl;
import com.cedar.cp.dto.datatype.DataTypeRelPK;
import com.cedar.cp.ejb.impl.datatype.DataTypeRelEVO;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class DataTypeEVO implements Serializable {

   private String mRollups;
   private transient DataTypePK mPK;
   private short mDataTypeId;
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
   private int mUpdatedByUserId;
   private Timestamp mUpdatedTime;
   private Timestamp mCreatedTime;
   private Map<DataTypeRelPK, DataTypeRelEVO> mDataTypeDependencies;
   protected boolean mDataTypeDependenciesAllItemsLoaded;
   private boolean mModified;
   public static final int FINANCIAL_VALUE = 0;
   public static final int TEMPORARY_VIREMENT = 1;
   public static final int PERMANENT_VIREMENT = 2;
   public static final int VIRTUAL = 3;
   public static final int MEASURE = 4;
   public static final Integer STRING_CLASS = Integer.valueOf(0);
   public static final Integer NUMERIC_CLASS = Integer.valueOf(1);
   public static final Integer TIME_CLASS = Integer.valueOf(2);
   public static final Integer DATE_CLASS = Integer.valueOf(3);
   public static final Integer DATE_TIME_CLASS = Integer.valueOf(4);
   public static final Integer BOOLEAN_CLASS = Integer.valueOf(5);


   public DataTypeEVO() {}

   public DataTypeEVO(short newDataTypeId, String newVisId, String newDescription, boolean newReadOnlyFlag, boolean newAvailableForImport, boolean newAvailableForExport, int newSubType, String newFormulaExpr, Integer newMeasureClass, Integer newMeasureLength, Integer newMeasureScale, String newMeasureValidation, int newVersionNum, Collection newDataTypeDependencies) {
      this.mDataTypeId = newDataTypeId;
      this.mVisId = newVisId;
      this.mDescription = newDescription;
      this.mReadOnlyFlag = newReadOnlyFlag;
      this.mAvailableForImport = newAvailableForImport;
      this.mAvailableForExport = newAvailableForExport;
      this.mSubType = newSubType;
      this.mFormulaExpr = newFormulaExpr;
      this.mMeasureClass = newMeasureClass;
      this.mMeasureLength = newMeasureLength;
      this.mMeasureScale = newMeasureScale;
      this.mMeasureValidation = newMeasureValidation;
      this.mVersionNum = newVersionNum;
      this.setDataTypeDependencies(newDataTypeDependencies);
   }

   public void setDataTypeDependencies(Collection<DataTypeRelEVO> items) {
      if(items != null) {
         if(this.mDataTypeDependencies == null) {
            this.mDataTypeDependencies = new HashMap();
         } else {
            this.mDataTypeDependencies.clear();
         }

         Iterator i$ = items.iterator();

         while(i$.hasNext()) {
            DataTypeRelEVO child = (DataTypeRelEVO)i$.next();
            this.mDataTypeDependencies.put(child.getPK(), child);
         }
      } else {
         this.mDataTypeDependencies = null;
      }

   }

   public DataTypePK getPK() {
      if(this.mPK == null) {
         this.mPK = new DataTypePK(this.mDataTypeId);
      }

      return this.mPK;
   }

   public boolean isModified() {
      return this.mModified;
   }

   public short getDataTypeId() {
      return this.mDataTypeId;
   }

   public String getVisId() {
      return this.mVisId;
   }

   public String getDescription() {
      return this.mDescription;
   }

   public boolean getReadOnlyFlag() {
      return this.mReadOnlyFlag;
   }

   public boolean getAvailableForImport() {
      return this.mAvailableForImport;
   }

   public boolean getAvailableForExport() {
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

   public int getVersionNum() {
      return this.mVersionNum;
   }

   public int getUpdatedByUserId() {
      return this.mUpdatedByUserId;
   }

   public Timestamp getUpdatedTime() {
      return this.mUpdatedTime;
   }

   public Timestamp getCreatedTime() {
      return this.mCreatedTime;
   }

   public void setDataTypeId(short newDataTypeId) {
      if(this.mDataTypeId != newDataTypeId) {
         this.mModified = true;
         this.mDataTypeId = newDataTypeId;
         this.mPK = null;
      }
   }

   public void setReadOnlyFlag(boolean newReadOnlyFlag) {
      if(this.mReadOnlyFlag != newReadOnlyFlag) {
         this.mModified = true;
         this.mReadOnlyFlag = newReadOnlyFlag;
      }
   }

   public void setAvailableForImport(boolean newAvailableForImport) {
      if(this.mAvailableForImport != newAvailableForImport) {
         this.mModified = true;
         this.mAvailableForImport = newAvailableForImport;
      }
   }

   public void setAvailableForExport(boolean newAvailableForExport) {
      if(this.mAvailableForExport != newAvailableForExport) {
         this.mModified = true;
         this.mAvailableForExport = newAvailableForExport;
      }
   }

   public void setSubType(int newSubType) {
      if(this.mSubType != newSubType) {
         this.mModified = true;
         this.mSubType = newSubType;
      }
   }

   public void setVersionNum(int newVersionNum) {
      if(this.mVersionNum != newVersionNum) {
         this.mModified = true;
         this.mVersionNum = newVersionNum;
      }
   }

   public void setUpdatedByUserId(int newUpdatedByUserId) {
      this.mUpdatedByUserId = newUpdatedByUserId;
   }

   public void setVisId(String newVisId) {
      if(this.mVisId != null && newVisId == null || this.mVisId == null && newVisId != null || this.mVisId != null && newVisId != null && !this.mVisId.equals(newVisId)) {
         this.mVisId = newVisId;
         this.mModified = true;
      }

   }

   public void setDescription(String newDescription) {
      if(this.mDescription != null && newDescription == null || this.mDescription == null && newDescription != null || this.mDescription != null && newDescription != null && !this.mDescription.equals(newDescription)) {
         this.mDescription = newDescription;
         this.mModified = true;
      }

   }

   public void setFormulaExpr(String newFormulaExpr) {
      if(this.mFormulaExpr != null && newFormulaExpr == null || this.mFormulaExpr == null && newFormulaExpr != null || this.mFormulaExpr != null && newFormulaExpr != null && !this.mFormulaExpr.equals(newFormulaExpr)) {
         this.mFormulaExpr = newFormulaExpr;
         this.mModified = true;
      }

   }

   public void setMeasureClass(Integer newMeasureClass) {
      if(this.mMeasureClass != null && newMeasureClass == null || this.mMeasureClass == null && newMeasureClass != null || this.mMeasureClass != null && newMeasureClass != null && !this.mMeasureClass.equals(newMeasureClass)) {
         this.mMeasureClass = newMeasureClass;
         this.mModified = true;
      }

   }

   public void setMeasureLength(Integer newMeasureLength) {
      if(this.mMeasureLength != null && newMeasureLength == null || this.mMeasureLength == null && newMeasureLength != null || this.mMeasureLength != null && newMeasureLength != null && !this.mMeasureLength.equals(newMeasureLength)) {
         this.mMeasureLength = newMeasureLength;
         this.mModified = true;
      }

   }

   public void setMeasureScale(Integer newMeasureScale) {
      if(this.mMeasureScale != null && newMeasureScale == null || this.mMeasureScale == null && newMeasureScale != null || this.mMeasureScale != null && newMeasureScale != null && !this.mMeasureScale.equals(newMeasureScale)) {
         this.mMeasureScale = newMeasureScale;
         this.mModified = true;
      }

   }

   public void setMeasureValidation(String newMeasureValidation) {
      if(this.mMeasureValidation != null && newMeasureValidation == null || this.mMeasureValidation == null && newMeasureValidation != null || this.mMeasureValidation != null && newMeasureValidation != null && !this.mMeasureValidation.equals(newMeasureValidation)) {
         this.mMeasureValidation = newMeasureValidation;
         this.mModified = true;
      }

   }

   protected void setUpdatedTime(Timestamp newUpdatedTime) {
      this.mUpdatedTime = newUpdatedTime;
   }

   protected void setCreatedTime(Timestamp newCreatedTime) {
      this.mCreatedTime = newCreatedTime;
   }

   public void setDetails(DataTypeEVO newDetails) {
      this.setDataTypeId(newDetails.getDataTypeId());
      this.setVisId(newDetails.getVisId());
      this.setDescription(newDetails.getDescription());
      this.setReadOnlyFlag(newDetails.getReadOnlyFlag());
      this.setAvailableForImport(newDetails.getAvailableForImport());
      this.setAvailableForExport(newDetails.getAvailableForExport());
      this.setSubType(newDetails.getSubType());
      this.setFormulaExpr(newDetails.getFormulaExpr());
      this.setMeasureClass(newDetails.getMeasureClass());
      this.setMeasureLength(newDetails.getMeasureLength());
      this.setMeasureScale(newDetails.getMeasureScale());
      this.setMeasureValidation(newDetails.getMeasureValidation());
      this.setVersionNum(newDetails.getVersionNum());
      this.setUpdatedByUserId(newDetails.getUpdatedByUserId());
      this.setUpdatedTime(newDetails.getUpdatedTime());
      this.setCreatedTime(newDetails.getCreatedTime());
   }

   public DataTypeEVO deepClone() {
      DataTypeEVO cloned = null;

      try {
         ByteArrayOutputStream e = new ByteArrayOutputStream();
         ObjectOutputStream oos = new ObjectOutputStream(e);
         oos.writeObject(this);
         oos.flush();
         oos.close();
         ByteArrayInputStream bis = new ByteArrayInputStream(e.toByteArray());
         ObjectInputStream ois = new ObjectInputStream(bis);
         cloned = (DataTypeEVO)ois.readObject();
         ois.close();
         return cloned;
      } catch (Exception var6) {
         throw new RuntimeException(var6);
      }
   }

   public void prepareForInsert() {
      boolean newKey = false;
      if(this.mDataTypeId > 0) {
         newKey = true;
         this.mDataTypeId = 0;
      } else if(this.mDataTypeId < 1) {
         newKey = true;
      }

      this.setVersionNum(0);
      DataTypeRelEVO item;
      if(this.mDataTypeDependencies != null) {
         for(Iterator iter = (new ArrayList(this.mDataTypeDependencies.values())).iterator(); iter.hasNext(); item.prepareForInsert(this)) {
            item = (DataTypeRelEVO)iter.next();
            if(newKey) {
               item.setInsertPending();
            }
         }
      }

   }

   public int getInsertCount(int startCount) {
      int returnCount = startCount;
      if(this.mDataTypeId < 1) {
         returnCount = startCount + 1;
      }

      DataTypeRelEVO item;
      if(this.mDataTypeDependencies != null) {
         for(Iterator iter = this.mDataTypeDependencies.values().iterator(); iter.hasNext(); returnCount = item.getInsertCount(returnCount)) {
            item = (DataTypeRelEVO)iter.next();
         }
      }

      return returnCount;
   }

   public int assignNextKey(int startKey) {
      int nextKey = startKey;
      if(this.mDataTypeId < 1) {
         this.mDataTypeId = (short)startKey;
         nextKey = startKey + 1;
      }

      DataTypeRelEVO item;
      if(this.mDataTypeDependencies != null) {
         for(Iterator iter = (new ArrayList(this.mDataTypeDependencies.values())).iterator(); iter.hasNext(); nextKey = item.assignNextKey(this, nextKey)) {
            item = (DataTypeRelEVO)iter.next();
            this.changeKey(item, this.mDataTypeId, item.getRefDataTypeId());
         }
      }

      return nextKey;
   }

   public Collection<DataTypeRelEVO> getDataTypeDependencies() {
      return this.mDataTypeDependencies != null?this.mDataTypeDependencies.values():null;
   }

   public Map<DataTypeRelPK, DataTypeRelEVO> getDataTypeDependenciesMap() {
      return this.mDataTypeDependencies;
   }

   public void loadDataTypeDependenciesItem(DataTypeRelEVO newItem) {
      if(this.mDataTypeDependencies == null) {
         this.mDataTypeDependencies = new HashMap();
      }

      this.mDataTypeDependencies.put(newItem.getPK(), newItem);
   }

   public void addDataTypeDependenciesItem(DataTypeRelEVO newItem) {
      if(this.mDataTypeDependencies == null) {
         this.mDataTypeDependencies = new HashMap();
      }

      DataTypeRelPK newPK = newItem.getPK();
      if(this.getDataTypeDependenciesItem(newPK) != null) {
         throw new RuntimeException("addDataTypeDependenciesItem: key already in list");
      } else {
         newItem.setInsertPending();
         this.mDataTypeDependencies.put(newPK, newItem);
      }
   }

   public void changeDataTypeDependenciesItem(DataTypeRelEVO changedItem) {
      if(this.mDataTypeDependencies == null) {
         throw new RuntimeException("changeDataTypeDependenciesItem: no items in collection");
      } else {
         DataTypeRelPK changedPK = changedItem.getPK();
         DataTypeRelEVO listItem = this.getDataTypeDependenciesItem(changedPK);
         if(listItem == null) {
            throw new RuntimeException("changeDataTypeDependenciesItem: item not in list");
         } else {
            listItem.setDetails(changedItem);
         }
      }
   }

   public void deleteDataTypeDependenciesItem(DataTypeRelPK removePK) {
      DataTypeRelEVO listItem = this.getDataTypeDependenciesItem(removePK);
      if(listItem == null) {
         throw new RuntimeException("removeDataTypeDependenciesItem: item not in list");
      } else {
         listItem.setDeletePending();
      }
   }

   public DataTypeRelEVO getDataTypeDependenciesItem(DataTypeRelPK pk) {
      return (DataTypeRelEVO)this.mDataTypeDependencies.get(pk);
   }

   public DataTypeRelEVO getDataTypeDependenciesItem() {
      if(this.mDataTypeDependencies.size() != 1) {
         throw new RuntimeException("should be 1 item but size=" + this.mDataTypeDependencies.size());
      } else {
         Iterator iter = this.mDataTypeDependencies.values().iterator();
         return (DataTypeRelEVO)iter.next();
      }
   }

   protected void reset() {
      this.mModified = false;
   }

   public DataTypeRef getEntityRef() {
      return new DataTypeRefImpl(this.getPK(), this.mVisId, this.mDescription, this.mSubType, this.mMeasureClass, this.mMeasureLength);
   }

   public void postCreateInit() {
      this.mDataTypeDependenciesAllItemsLoaded = true;
      if(this.mDataTypeDependencies == null) {
         this.mDataTypeDependencies = new HashMap();
      }

   }

   public String toString() {
      StringBuffer sb = new StringBuffer();
      sb.append("DataTypeId=");
      sb.append(String.valueOf(this.mDataTypeId));
      sb.append(' ');
      sb.append("VisId=");
      sb.append(String.valueOf(this.mVisId));
      sb.append(' ');
      sb.append("Description=");
      sb.append(String.valueOf(this.mDescription));
      sb.append(' ');
      sb.append("ReadOnlyFlag=");
      sb.append(String.valueOf(this.mReadOnlyFlag));
      sb.append(' ');
      sb.append("AvailableForImport=");
      sb.append(String.valueOf(this.mAvailableForImport));
      sb.append(' ');
      sb.append("AvailableForExport=");
      sb.append(String.valueOf(this.mAvailableForExport));
      sb.append(' ');
      sb.append("SubType=");
      sb.append(String.valueOf(this.mSubType));
      sb.append(' ');
      sb.append("FormulaExpr=");
      sb.append(String.valueOf(this.mFormulaExpr));
      sb.append(' ');
      sb.append("MeasureClass=");
      sb.append(String.valueOf(this.mMeasureClass));
      sb.append(' ');
      sb.append("MeasureLength=");
      sb.append(String.valueOf(this.mMeasureLength));
      sb.append(' ');
      sb.append("MeasureScale=");
      sb.append(String.valueOf(this.mMeasureScale));
      sb.append(' ');
      sb.append("MeasureValidation=");
      sb.append(String.valueOf(this.mMeasureValidation));
      sb.append(' ');
      sb.append("VersionNum=");
      sb.append(String.valueOf(this.mVersionNum));
      sb.append(' ');
      sb.append("UpdatedByUserId=");
      sb.append(String.valueOf(this.mUpdatedByUserId));
      sb.append(' ');
      sb.append("UpdatedTime=");
      sb.append(String.valueOf(this.mUpdatedTime));
      sb.append(' ');
      sb.append("CreatedTime=");
      sb.append(String.valueOf(this.mCreatedTime));
      sb.append(' ');
      if(this.mModified) {
         sb.append("modified ");
      }

      return sb.toString();
   }

   public String print() {
      return this.print(0);
   }

   public String print(int indent) {
      StringBuffer sb = new StringBuffer();

      for(int i$ = 0; i$ < indent; ++i$) {
         sb.append(' ');
      }

      sb.append("DataType: ");
      sb.append(this.toString());
      if(this.mDataTypeDependenciesAllItemsLoaded || this.mDataTypeDependencies != null) {
         sb.delete(indent, sb.length());
         sb.append(" - DataTypeDependencies: allItemsLoaded=");
         sb.append(String.valueOf(this.mDataTypeDependenciesAllItemsLoaded));
         sb.append(" items=");
         if(this.mDataTypeDependencies == null) {
            sb.append("null");
         } else {
            sb.append(String.valueOf(this.mDataTypeDependencies.size()));
         }
      }

      if(this.mDataTypeDependencies != null) {
         Iterator var5 = this.mDataTypeDependencies.values().iterator();

         while(var5.hasNext()) {
            DataTypeRelEVO listItem = (DataTypeRelEVO)var5.next();
            listItem.print(indent + 4);
         }
      }

      return sb.toString();
   }

   public void changeKey(DataTypeRelEVO child, short newDataTypeId, short newRefDataTypeId) {
      if(this.getDataTypeDependenciesItem(child.getPK()) != child) {
         throw new IllegalStateException("changeKey child not found in parent");
      } else {
         this.mDataTypeDependencies.remove(child.getPK());
         child.setDataTypeId(newDataTypeId);
         child.setRefDataTypeId(newRefDataTypeId);
         this.mDataTypeDependencies.put(child.getPK(), child);
      }
   }

   public boolean isFinanceValue() {
      return this.getSubType() == 0 || this.getSubType() == 1 || this.getSubType() == 2;
   }

   public boolean isMeasure() {
      return this.getSubType() == 4;
   }

   public boolean isMeasureNumeric() {
      return this.isMeasure() && this.getMeasureClass().equals(NUMERIC_CLASS);
   }

   public boolean isMeasureString() {
      return this.isMeasure() && this.getMeasureClass().equals(STRING_CLASS);
   }

   public boolean isMeasureBoolean() {
      return this.isMeasure() && this.getMeasureClass().equals(BOOLEAN_CLASS);
   }

   public boolean isMeasureDate() {
      return this.isMeasure() && this.getMeasureClass().equals(DATE_CLASS);
   }

   public boolean isMeasureDateTime() {
      return this.isMeasure() && this.getMeasureClass().equals(DATE_TIME_CLASS);
   }

   public boolean isMeasureTime() {
      return this.isMeasure() && this.getMeasureClass().equals(TIME_CLASS);
   }

   public void setRollups(String rollups) {
      this.mRollups = rollups;
   }

   public String getRollups() {
      return this.mRollups;
   }

   public void setDataTypeDependenciesAllItemsLoaded(boolean allItemsLoaded) {
      this.mDataTypeDependenciesAllItemsLoaded = allItemsLoaded;
   }

   public boolean isDataTypeDependenciesAllItemsLoaded() {
      return this.mDataTypeDependenciesAllItemsLoaded;
   }

}
