// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:27:10
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.impl.report.definition;

import com.cedar.cp.api.report.definition.ReportDefinitionRef;
import com.cedar.cp.dto.report.definition.ReportDefCalculatorPK;
import com.cedar.cp.dto.report.definition.ReportDefFormPK;
import com.cedar.cp.dto.report.definition.ReportDefMappedExcelPK;
import com.cedar.cp.dto.report.definition.ReportDefSummaryCalcPK;
import com.cedar.cp.dto.report.definition.ReportDefinitionPK;
import com.cedar.cp.dto.report.definition.ReportDefinitionRefImpl;
import com.cedar.cp.ejb.impl.report.definition.ReportDefCalculatorEVO;
import com.cedar.cp.ejb.impl.report.definition.ReportDefFormEVO;
import com.cedar.cp.ejb.impl.report.definition.ReportDefMappedExcelEVO;
import com.cedar.cp.ejb.impl.report.definition.ReportDefSummaryCalcEVO;
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

public class ReportDefinitionEVO implements Serializable {

   private transient ReportDefinitionPK mPK;
   private int mReportDefinitionId;
   private String mVisId;
   private String mDescription;
   private int mReportTypeId;
   private boolean mIsPublic;
   private int mVersionNum;
   private int mUpdatedByUserId;
   private Timestamp mUpdatedTime;
   private Timestamp mCreatedTime;
   private Map<ReportDefFormPK, ReportDefFormEVO> mReportForm;
   protected boolean mReportFormAllItemsLoaded;
   private Map<ReportDefMappedExcelPK, ReportDefMappedExcelEVO> mReportMappedExcel;
   protected boolean mReportMappedExcelAllItemsLoaded;
   private Map<ReportDefCalculatorPK, ReportDefCalculatorEVO> mReportCalculator;
   protected boolean mReportCalculatorAllItemsLoaded;
   private Map<ReportDefSummaryCalcPK, ReportDefSummaryCalcEVO> mSummaryCalcReport;
   protected boolean mSummaryCalcReportAllItemsLoaded;
   private boolean mModified;


   public ReportDefinitionEVO() {}

   public ReportDefinitionEVO(int newReportDefinitionId, String newVisId, String newDescription, int newReportTypeId, boolean newIsPublic, int newVersionNum, Collection newReportForm, Collection newReportMappedExcel, Collection newReportCalculator, Collection newSummaryCalcReport) {
      this.mReportDefinitionId = newReportDefinitionId;
      this.mVisId = newVisId;
      this.mDescription = newDescription;
      this.mReportTypeId = newReportTypeId;
      this.mIsPublic = newIsPublic;
      this.mVersionNum = newVersionNum;
      this.setReportForm(newReportForm);
      this.setReportMappedExcel(newReportMappedExcel);
      this.setReportCalculator(newReportCalculator);
      this.setSummaryCalcReport(newSummaryCalcReport);
   }

   public void setReportForm(Collection<ReportDefFormEVO> items) {
      if(items != null) {
         if(this.mReportForm == null) {
            this.mReportForm = new HashMap();
         } else {
            this.mReportForm.clear();
         }

         Iterator i$ = items.iterator();

         while(i$.hasNext()) {
            ReportDefFormEVO child = (ReportDefFormEVO)i$.next();
            this.mReportForm.put(child.getPK(), child);
         }
      } else {
         this.mReportForm = null;
      }

   }

   public void setReportMappedExcel(Collection<ReportDefMappedExcelEVO> items) {
      if(items != null) {
         if(this.mReportMappedExcel == null) {
            this.mReportMappedExcel = new HashMap();
         } else {
            this.mReportMappedExcel.clear();
         }

         Iterator i$ = items.iterator();

         while(i$.hasNext()) {
            ReportDefMappedExcelEVO child = (ReportDefMappedExcelEVO)i$.next();
            this.mReportMappedExcel.put(child.getPK(), child);
         }
      } else {
         this.mReportMappedExcel = null;
      }

   }

   public void setReportCalculator(Collection<ReportDefCalculatorEVO> items) {
      if(items != null) {
         if(this.mReportCalculator == null) {
            this.mReportCalculator = new HashMap();
         } else {
            this.mReportCalculator.clear();
         }

         Iterator i$ = items.iterator();

         while(i$.hasNext()) {
            ReportDefCalculatorEVO child = (ReportDefCalculatorEVO)i$.next();
            this.mReportCalculator.put(child.getPK(), child);
         }
      } else {
         this.mReportCalculator = null;
      }

   }

   public void setSummaryCalcReport(Collection<ReportDefSummaryCalcEVO> items) {
      if(items != null) {
         if(this.mSummaryCalcReport == null) {
            this.mSummaryCalcReport = new HashMap();
         } else {
            this.mSummaryCalcReport.clear();
         }

         Iterator i$ = items.iterator();

         while(i$.hasNext()) {
            ReportDefSummaryCalcEVO child = (ReportDefSummaryCalcEVO)i$.next();
            this.mSummaryCalcReport.put(child.getPK(), child);
         }
      } else {
         this.mSummaryCalcReport = null;
      }

   }

   public ReportDefinitionPK getPK() {
      if(this.mPK == null) {
         this.mPK = new ReportDefinitionPK(this.mReportDefinitionId);
      }

      return this.mPK;
   }

   public boolean isModified() {
      return this.mModified;
   }

   public int getReportDefinitionId() {
      return this.mReportDefinitionId;
   }

   public String getVisId() {
      return this.mVisId;
   }

   public String getDescription() {
      return this.mDescription;
   }

   public int getReportTypeId() {
      return this.mReportTypeId;
   }

   public boolean getIsPublic() {
      return this.mIsPublic;
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

   public void setReportDefinitionId(int newReportDefinitionId) {
      if(this.mReportDefinitionId != newReportDefinitionId) {
         this.mModified = true;
         this.mReportDefinitionId = newReportDefinitionId;
         this.mPK = null;
      }
   }

   public void setReportTypeId(int newReportTypeId) {
      if(this.mReportTypeId != newReportTypeId) {
         this.mModified = true;
         this.mReportTypeId = newReportTypeId;
      }
   }

   public void setIsPublic(boolean newIsPublic) {
      if(this.mIsPublic != newIsPublic) {
         this.mModified = true;
         this.mIsPublic = newIsPublic;
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

   protected void setUpdatedTime(Timestamp newUpdatedTime) {
      this.mUpdatedTime = newUpdatedTime;
   }

   protected void setCreatedTime(Timestamp newCreatedTime) {
      this.mCreatedTime = newCreatedTime;
   }

   public void setDetails(ReportDefinitionEVO newDetails) {
      this.setReportDefinitionId(newDetails.getReportDefinitionId());
      this.setVisId(newDetails.getVisId());
      this.setDescription(newDetails.getDescription());
      this.setReportTypeId(newDetails.getReportTypeId());
      this.setIsPublic(newDetails.getIsPublic());
      this.setVersionNum(newDetails.getVersionNum());
      this.setUpdatedByUserId(newDetails.getUpdatedByUserId());
      this.setUpdatedTime(newDetails.getUpdatedTime());
      this.setCreatedTime(newDetails.getCreatedTime());
   }

   public ReportDefinitionEVO deepClone() {
      ReportDefinitionEVO cloned = null;

      try {
         ByteArrayOutputStream e = new ByteArrayOutputStream();
         ObjectOutputStream oos = new ObjectOutputStream(e);
         oos.writeObject(this);
         oos.flush();
         oos.close();
         ByteArrayInputStream bis = new ByteArrayInputStream(e.toByteArray());
         ObjectInputStream ois = new ObjectInputStream(bis);
         cloned = (ReportDefinitionEVO)ois.readObject();
         ois.close();
         return cloned;
      } catch (Exception var6) {
         throw new RuntimeException(var6);
      }
   }

   public void prepareForInsert() {
      boolean newKey = false;
      if(this.mReportDefinitionId > 0) {
         newKey = true;
         this.mReportDefinitionId = 0;
      } else if(this.mReportDefinitionId < 1) {
         newKey = true;
      }

      this.setVersionNum(0);
      Iterator iter;
      ReportDefFormEVO item;
      if(this.mReportForm != null) {
         for(iter = (new ArrayList(this.mReportForm.values())).iterator(); iter.hasNext(); item.prepareForInsert(this)) {
            item = (ReportDefFormEVO)iter.next();
            if(newKey) {
               item.setInsertPending();
            }
         }
      }

      ReportDefMappedExcelEVO item1;
      if(this.mReportMappedExcel != null) {
         for(iter = (new ArrayList(this.mReportMappedExcel.values())).iterator(); iter.hasNext(); item1.prepareForInsert(this)) {
            item1 = (ReportDefMappedExcelEVO)iter.next();
            if(newKey) {
               item1.setInsertPending();
            }
         }
      }

      ReportDefCalculatorEVO item2;
      if(this.mReportCalculator != null) {
         for(iter = (new ArrayList(this.mReportCalculator.values())).iterator(); iter.hasNext(); item2.prepareForInsert(this)) {
            item2 = (ReportDefCalculatorEVO)iter.next();
            if(newKey) {
               item2.setInsertPending();
            }
         }
      }

      ReportDefSummaryCalcEVO item3;
      if(this.mSummaryCalcReport != null) {
         for(iter = (new ArrayList(this.mSummaryCalcReport.values())).iterator(); iter.hasNext(); item3.prepareForInsert(this)) {
            item3 = (ReportDefSummaryCalcEVO)iter.next();
            if(newKey) {
               item3.setInsertPending();
            }
         }
      }

   }

   public int getInsertCount(int startCount) {
      int returnCount = startCount;
      if(this.mReportDefinitionId < 1) {
         returnCount = startCount + 1;
      }

      Iterator iter;
      ReportDefFormEVO item;
      if(this.mReportForm != null) {
         for(iter = this.mReportForm.values().iterator(); iter.hasNext(); returnCount = item.getInsertCount(returnCount)) {
            item = (ReportDefFormEVO)iter.next();
         }
      }

      ReportDefMappedExcelEVO item1;
      if(this.mReportMappedExcel != null) {
         for(iter = this.mReportMappedExcel.values().iterator(); iter.hasNext(); returnCount = item1.getInsertCount(returnCount)) {
            item1 = (ReportDefMappedExcelEVO)iter.next();
         }
      }

      ReportDefCalculatorEVO item2;
      if(this.mReportCalculator != null) {
         for(iter = this.mReportCalculator.values().iterator(); iter.hasNext(); returnCount = item2.getInsertCount(returnCount)) {
            item2 = (ReportDefCalculatorEVO)iter.next();
         }
      }

      ReportDefSummaryCalcEVO item3;
      if(this.mSummaryCalcReport != null) {
         for(iter = this.mSummaryCalcReport.values().iterator(); iter.hasNext(); returnCount = item3.getInsertCount(returnCount)) {
            item3 = (ReportDefSummaryCalcEVO)iter.next();
         }
      }

      return returnCount;
   }

   public int assignNextKey(int startKey) {
      int nextKey = startKey;
      if(this.mReportDefinitionId < 1) {
         this.mReportDefinitionId = startKey;
         nextKey = startKey + 1;
      }

      Iterator iter;
      ReportDefFormEVO item;
      if(this.mReportForm != null) {
         for(iter = (new ArrayList(this.mReportForm.values())).iterator(); iter.hasNext(); nextKey = item.assignNextKey(this, nextKey)) {
            item = (ReportDefFormEVO)iter.next();
            this.changeKey(item, this.mReportDefinitionId);
         }
      }

      ReportDefMappedExcelEVO item1;
      if(this.mReportMappedExcel != null) {
         for(iter = (new ArrayList(this.mReportMappedExcel.values())).iterator(); iter.hasNext(); nextKey = item1.assignNextKey(this, nextKey)) {
            item1 = (ReportDefMappedExcelEVO)iter.next();
            this.changeKey(item1, this.mReportDefinitionId);
         }
      }

      ReportDefCalculatorEVO item2;
      if(this.mReportCalculator != null) {
         for(iter = (new ArrayList(this.mReportCalculator.values())).iterator(); iter.hasNext(); nextKey = item2.assignNextKey(this, nextKey)) {
            item2 = (ReportDefCalculatorEVO)iter.next();
            this.changeKey(item2, this.mReportDefinitionId);
         }
      }

      ReportDefSummaryCalcEVO item3;
      if(this.mSummaryCalcReport != null) {
         for(iter = (new ArrayList(this.mSummaryCalcReport.values())).iterator(); iter.hasNext(); nextKey = item3.assignNextKey(this, nextKey)) {
            item3 = (ReportDefSummaryCalcEVO)iter.next();
            this.changeKey(item3, this.mReportDefinitionId);
         }
      }

      return nextKey;
   }

   public Collection<ReportDefFormEVO> getReportForm() {
      return this.mReportForm != null?this.mReportForm.values():null;
   }

   public Map<ReportDefFormPK, ReportDefFormEVO> getReportFormMap() {
      return this.mReportForm;
   }

   public void loadReportFormItem(ReportDefFormEVO newItem) {
      if(this.mReportForm == null) {
         this.mReportForm = new HashMap();
      }

      this.mReportForm.put(newItem.getPK(), newItem);
   }

   public void addReportFormItem(ReportDefFormEVO newItem) {
      if(this.mReportForm == null) {
         this.mReportForm = new HashMap();
      }

      ReportDefFormPK newPK = newItem.getPK();
      if(this.getReportFormItem(newPK) != null) {
         throw new RuntimeException("addReportFormItem: key already in list");
      } else {
         newItem.setInsertPending();
         this.mReportForm.put(newPK, newItem);
      }
   }

   public void changeReportFormItem(ReportDefFormEVO changedItem) {
      if(this.mReportForm == null) {
         throw new RuntimeException("changeReportFormItem: no items in collection");
      } else {
         ReportDefFormPK changedPK = changedItem.getPK();
         ReportDefFormEVO listItem = this.getReportFormItem(changedPK);
         if(listItem == null) {
            throw new RuntimeException("changeReportFormItem: item not in list");
         } else {
            listItem.setDetails(changedItem);
         }
      }
   }

   public void deleteReportFormItem(ReportDefFormPK removePK) {
      ReportDefFormEVO listItem = this.getReportFormItem(removePK);
      if(listItem == null) {
         throw new RuntimeException("removeReportFormItem: item not in list");
      } else {
         listItem.setDeletePending();
      }
   }

   public ReportDefFormEVO getReportFormItem(ReportDefFormPK pk) {
      return (ReportDefFormEVO)this.mReportForm.get(pk);
   }

   public ReportDefFormEVO getReportFormItem() {
      if(this.mReportForm.size() != 1) {
         throw new RuntimeException("should be 1 item but size=" + this.mReportForm.size());
      } else {
         Iterator iter = this.mReportForm.values().iterator();
         return (ReportDefFormEVO)iter.next();
      }
   }

   public Collection<ReportDefMappedExcelEVO> getReportMappedExcel() {
      return this.mReportMappedExcel != null?this.mReportMappedExcel.values():null;
   }

   public Map<ReportDefMappedExcelPK, ReportDefMappedExcelEVO> getReportMappedExcelMap() {
      return this.mReportMappedExcel;
   }

   public void loadReportMappedExcelItem(ReportDefMappedExcelEVO newItem) {
      if(this.mReportMappedExcel == null) {
         this.mReportMappedExcel = new HashMap();
      }

      this.mReportMappedExcel.put(newItem.getPK(), newItem);
   }

   public void addReportMappedExcelItem(ReportDefMappedExcelEVO newItem) {
      if(this.mReportMappedExcel == null) {
         this.mReportMappedExcel = new HashMap();
      }

      ReportDefMappedExcelPK newPK = newItem.getPK();
      if(this.getReportMappedExcelItem(newPK) != null) {
         throw new RuntimeException("addReportMappedExcelItem: key already in list");
      } else {
         newItem.setInsertPending();
         this.mReportMappedExcel.put(newPK, newItem);
      }
   }

   public void changeReportMappedExcelItem(ReportDefMappedExcelEVO changedItem) {
      if(this.mReportMappedExcel == null) {
         throw new RuntimeException("changeReportMappedExcelItem: no items in collection");
      } else {
         ReportDefMappedExcelPK changedPK = changedItem.getPK();
         ReportDefMappedExcelEVO listItem = this.getReportMappedExcelItem(changedPK);
         if(listItem == null) {
            throw new RuntimeException("changeReportMappedExcelItem: item not in list");
         } else {
            listItem.setDetails(changedItem);
         }
      }
   }

   public void deleteReportMappedExcelItem(ReportDefMappedExcelPK removePK) {
      ReportDefMappedExcelEVO listItem = this.getReportMappedExcelItem(removePK);
      if(listItem == null) {
         throw new RuntimeException("removeReportMappedExcelItem: item not in list");
      } else {
         listItem.setDeletePending();
      }
   }

   public ReportDefMappedExcelEVO getReportMappedExcelItem(ReportDefMappedExcelPK pk) {
      return (ReportDefMappedExcelEVO)this.mReportMappedExcel.get(pk);
   }

   public ReportDefMappedExcelEVO getReportMappedExcelItem() {
      if(this.mReportMappedExcel.size() != 1) {
         throw new RuntimeException("should be 1 item but size=" + this.mReportMappedExcel.size());
      } else {
         Iterator iter = this.mReportMappedExcel.values().iterator();
         return (ReportDefMappedExcelEVO)iter.next();
      }
   }

   public Collection<ReportDefCalculatorEVO> getReportCalculator() {
      return this.mReportCalculator != null?this.mReportCalculator.values():null;
   }

   public Map<ReportDefCalculatorPK, ReportDefCalculatorEVO> getReportCalculatorMap() {
      return this.mReportCalculator;
   }

   public void loadReportCalculatorItem(ReportDefCalculatorEVO newItem) {
      if(this.mReportCalculator == null) {
         this.mReportCalculator = new HashMap();
      }

      this.mReportCalculator.put(newItem.getPK(), newItem);
   }

   public void addReportCalculatorItem(ReportDefCalculatorEVO newItem) {
      if(this.mReportCalculator == null) {
         this.mReportCalculator = new HashMap();
      }

      ReportDefCalculatorPK newPK = newItem.getPK();
      if(this.getReportCalculatorItem(newPK) != null) {
         throw new RuntimeException("addReportCalculatorItem: key already in list");
      } else {
         newItem.setInsertPending();
         this.mReportCalculator.put(newPK, newItem);
      }
   }

   public void changeReportCalculatorItem(ReportDefCalculatorEVO changedItem) {
      if(this.mReportCalculator == null) {
         throw new RuntimeException("changeReportCalculatorItem: no items in collection");
      } else {
         ReportDefCalculatorPK changedPK = changedItem.getPK();
         ReportDefCalculatorEVO listItem = this.getReportCalculatorItem(changedPK);
         if(listItem == null) {
            throw new RuntimeException("changeReportCalculatorItem: item not in list");
         } else {
            listItem.setDetails(changedItem);
         }
      }
   }

   public void deleteReportCalculatorItem(ReportDefCalculatorPK removePK) {
      ReportDefCalculatorEVO listItem = this.getReportCalculatorItem(removePK);
      if(listItem == null) {
         throw new RuntimeException("removeReportCalculatorItem: item not in list");
      } else {
         listItem.setDeletePending();
      }
   }

   public ReportDefCalculatorEVO getReportCalculatorItem(ReportDefCalculatorPK pk) {
      return (ReportDefCalculatorEVO)this.mReportCalculator.get(pk);
   }

   public ReportDefCalculatorEVO getReportCalculatorItem() {
      if(this.mReportCalculator.size() != 1) {
         throw new RuntimeException("should be 1 item but size=" + this.mReportCalculator.size());
      } else {
         Iterator iter = this.mReportCalculator.values().iterator();
         return (ReportDefCalculatorEVO)iter.next();
      }
   }

   public Collection<ReportDefSummaryCalcEVO> getSummaryCalcReport() {
      return this.mSummaryCalcReport != null?this.mSummaryCalcReport.values():null;
   }

   public Map<ReportDefSummaryCalcPK, ReportDefSummaryCalcEVO> getSummaryCalcReportMap() {
      return this.mSummaryCalcReport;
   }

   public void loadSummaryCalcReportItem(ReportDefSummaryCalcEVO newItem) {
      if(this.mSummaryCalcReport == null) {
         this.mSummaryCalcReport = new HashMap();
      }

      this.mSummaryCalcReport.put(newItem.getPK(), newItem);
   }

   public void addSummaryCalcReportItem(ReportDefSummaryCalcEVO newItem) {
      if(this.mSummaryCalcReport == null) {
         this.mSummaryCalcReport = new HashMap();
      }

      ReportDefSummaryCalcPK newPK = newItem.getPK();
      if(this.getSummaryCalcReportItem(newPK) != null) {
         throw new RuntimeException("addSummaryCalcReportItem: key already in list");
      } else {
         newItem.setInsertPending();
         this.mSummaryCalcReport.put(newPK, newItem);
      }
   }

   public void changeSummaryCalcReportItem(ReportDefSummaryCalcEVO changedItem) {
      if(this.mSummaryCalcReport == null) {
         throw new RuntimeException("changeSummaryCalcReportItem: no items in collection");
      } else {
         ReportDefSummaryCalcPK changedPK = changedItem.getPK();
         ReportDefSummaryCalcEVO listItem = this.getSummaryCalcReportItem(changedPK);
         if(listItem == null) {
            throw new RuntimeException("changeSummaryCalcReportItem: item not in list");
         } else {
            listItem.setDetails(changedItem);
         }
      }
   }

   public void deleteSummaryCalcReportItem(ReportDefSummaryCalcPK removePK) {
      ReportDefSummaryCalcEVO listItem = this.getSummaryCalcReportItem(removePK);
      if(listItem == null) {
         throw new RuntimeException("removeSummaryCalcReportItem: item not in list");
      } else {
         listItem.setDeletePending();
      }
   }

   public ReportDefSummaryCalcEVO getSummaryCalcReportItem(ReportDefSummaryCalcPK pk) {
      return (ReportDefSummaryCalcEVO)this.mSummaryCalcReport.get(pk);
   }

   public ReportDefSummaryCalcEVO getSummaryCalcReportItem() {
      if(this.mSummaryCalcReport.size() != 1) {
         throw new RuntimeException("should be 1 item but size=" + this.mSummaryCalcReport.size());
      } else {
         Iterator iter = this.mSummaryCalcReport.values().iterator();
         return (ReportDefSummaryCalcEVO)iter.next();
      }
   }

   protected void reset() {
      this.mModified = false;
   }

   public ReportDefinitionRef getEntityRef() {
      return new ReportDefinitionRefImpl(this.getPK(), this.mVisId);
   }

   public void postCreateInit() {
      this.mReportFormAllItemsLoaded = true;
      if(this.mReportForm == null) {
         this.mReportForm = new HashMap();
      }

      this.mReportMappedExcelAllItemsLoaded = true;
      if(this.mReportMappedExcel == null) {
         this.mReportMappedExcel = new HashMap();
      }

      this.mReportCalculatorAllItemsLoaded = true;
      if(this.mReportCalculator == null) {
         this.mReportCalculator = new HashMap();
      }

      this.mSummaryCalcReportAllItemsLoaded = true;
      if(this.mSummaryCalcReport == null) {
         this.mSummaryCalcReport = new HashMap();
      }

   }

   public String toString() {
      StringBuffer sb = new StringBuffer();
      sb.append("ReportDefinitionId=");
      sb.append(String.valueOf(this.mReportDefinitionId));
      sb.append(' ');
      sb.append("VisId=");
      sb.append(String.valueOf(this.mVisId));
      sb.append(' ');
      sb.append("Description=");
      sb.append(String.valueOf(this.mDescription));
      sb.append(' ');
      sb.append("ReportTypeId=");
      sb.append(String.valueOf(this.mReportTypeId));
      sb.append(' ');
      sb.append("IsPublic=");
      sb.append(String.valueOf(this.mIsPublic));
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

      sb.append("ReportDefinition: ");
      sb.append(this.toString());
      if(this.mReportFormAllItemsLoaded || this.mReportForm != null) {
         sb.delete(indent, sb.length());
         sb.append(" - ReportForm: allItemsLoaded=");
         sb.append(String.valueOf(this.mReportFormAllItemsLoaded));
         sb.append(" items=");
         if(this.mReportForm == null) {
            sb.append("null");
         } else {
            sb.append(String.valueOf(this.mReportForm.size()));
         }
      }

      if(this.mReportMappedExcelAllItemsLoaded || this.mReportMappedExcel != null) {
         sb.delete(indent, sb.length());
         sb.append(" - ReportMappedExcel: allItemsLoaded=");
         sb.append(String.valueOf(this.mReportMappedExcelAllItemsLoaded));
         sb.append(" items=");
         if(this.mReportMappedExcel == null) {
            sb.append("null");
         } else {
            sb.append(String.valueOf(this.mReportMappedExcel.size()));
         }
      }

      if(this.mReportCalculatorAllItemsLoaded || this.mReportCalculator != null) {
         sb.delete(indent, sb.length());
         sb.append(" - ReportCalculator: allItemsLoaded=");
         sb.append(String.valueOf(this.mReportCalculatorAllItemsLoaded));
         sb.append(" items=");
         if(this.mReportCalculator == null) {
            sb.append("null");
         } else {
            sb.append(String.valueOf(this.mReportCalculator.size()));
         }
      }

      if(this.mSummaryCalcReportAllItemsLoaded || this.mSummaryCalcReport != null) {
         sb.delete(indent, sb.length());
         sb.append(" - SummaryCalcReport: allItemsLoaded=");
         sb.append(String.valueOf(this.mSummaryCalcReportAllItemsLoaded));
         sb.append(" items=");
         if(this.mSummaryCalcReport == null) {
            sb.append("null");
         } else {
            sb.append(String.valueOf(this.mSummaryCalcReport.size()));
         }
      }

      Iterator var5;
      if(this.mReportForm != null) {
         var5 = this.mReportForm.values().iterator();

         while(var5.hasNext()) {
            ReportDefFormEVO listItem = (ReportDefFormEVO)var5.next();
            listItem.print(indent + 4);
         }
      }

      if(this.mReportMappedExcel != null) {
         var5 = this.mReportMappedExcel.values().iterator();

         while(var5.hasNext()) {
            ReportDefMappedExcelEVO var6 = (ReportDefMappedExcelEVO)var5.next();
            var6.print(indent + 4);
         }
      }

      if(this.mReportCalculator != null) {
         var5 = this.mReportCalculator.values().iterator();

         while(var5.hasNext()) {
            ReportDefCalculatorEVO var7 = (ReportDefCalculatorEVO)var5.next();
            var7.print(indent + 4);
         }
      }

      if(this.mSummaryCalcReport != null) {
         var5 = this.mSummaryCalcReport.values().iterator();

         while(var5.hasNext()) {
            ReportDefSummaryCalcEVO var8 = (ReportDefSummaryCalcEVO)var5.next();
            var8.print(indent + 4);
         }
      }

      return sb.toString();
   }

   public void changeKey(ReportDefFormEVO child, int newReportDefinitionId) {
      if(this.getReportFormItem(child.getPK()) != child) {
         throw new IllegalStateException("changeKey child not found in parent");
      } else {
         this.mReportForm.remove(child.getPK());
         child.setReportDefinitionId(newReportDefinitionId);
         this.mReportForm.put(child.getPK(), child);
      }
   }

   public void changeKey(ReportDefMappedExcelEVO child, int newReportDefinitionId) {
      if(this.getReportMappedExcelItem(child.getPK()) != child) {
         throw new IllegalStateException("changeKey child not found in parent");
      } else {
         this.mReportMappedExcel.remove(child.getPK());
         child.setReportDefinitionId(newReportDefinitionId);
         this.mReportMappedExcel.put(child.getPK(), child);
      }
   }

   public void changeKey(ReportDefCalculatorEVO child, int newReportDefinitionId) {
      if(this.getReportCalculatorItem(child.getPK()) != child) {
         throw new IllegalStateException("changeKey child not found in parent");
      } else {
         this.mReportCalculator.remove(child.getPK());
         child.setReportDefinitionId(newReportDefinitionId);
         this.mReportCalculator.put(child.getPK(), child);
      }
   }

   public void changeKey(ReportDefSummaryCalcEVO child, int newReportDefinitionId) {
      if(this.getSummaryCalcReportItem(child.getPK()) != child) {
         throw new IllegalStateException("changeKey child not found in parent");
      } else {
         this.mSummaryCalcReport.remove(child.getPK());
         child.setReportDefinitionId(newReportDefinitionId);
         this.mSummaryCalcReport.put(child.getPK(), child);
      }
   }

   public void setReportFormAllItemsLoaded(boolean allItemsLoaded) {
      this.mReportFormAllItemsLoaded = allItemsLoaded;
   }

   public boolean isReportFormAllItemsLoaded() {
      return this.mReportFormAllItemsLoaded;
   }

   public void setReportMappedExcelAllItemsLoaded(boolean allItemsLoaded) {
      this.mReportMappedExcelAllItemsLoaded = allItemsLoaded;
   }

   public boolean isReportMappedExcelAllItemsLoaded() {
      return this.mReportMappedExcelAllItemsLoaded;
   }

   public void setReportCalculatorAllItemsLoaded(boolean allItemsLoaded) {
      this.mReportCalculatorAllItemsLoaded = allItemsLoaded;
   }

   public boolean isReportCalculatorAllItemsLoaded() {
      return this.mReportCalculatorAllItemsLoaded;
   }

   public void setSummaryCalcReportAllItemsLoaded(boolean allItemsLoaded) {
      this.mSummaryCalcReportAllItemsLoaded = allItemsLoaded;
   }

   public boolean isSummaryCalcReportAllItemsLoaded() {
      return this.mSummaryCalcReportAllItemsLoaded;
   }
}
