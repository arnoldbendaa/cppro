// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:27:31
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.impl.user;

import com.cedar.cp.api.user.DataEntryProfileRef;
import com.cedar.cp.dto.user.DataEntryProfileCK;
import com.cedar.cp.dto.user.DataEntryProfileHistoryPK;
import com.cedar.cp.dto.user.DataEntryProfilePK;
import com.cedar.cp.dto.user.DataEntryProfileRefImpl;
import com.cedar.cp.ejb.impl.user.DataEntryProfileHistoryEVO;
import com.cedar.cp.ejb.impl.user.UserEVO;
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

public class DataEntryProfileEVO implements Serializable {

   private transient DataEntryProfilePK mPK;
   private int mDataEntryProfileId;
   private String mVisId;
   private int mUserId;
   private int mModelId;
   private int mBudgetCycleId;
   private int mAutoOpenDepth;
   private String mDescription;
   private int mXmlformId;
   private boolean mFillDisplayArea;
   private boolean mShowBoldSummaries;
   private boolean mShowHorizontalLines;
   private int mStructureId0;
   private int mStructureId1;
   private int mStructureId2;
   private int mStructureId3;
   private int mStructureId4;
   private int mStructureId5;
   private int mStructureId6;
   private int mStructureId7;
   private int mStructureId8;
   private int mStructureElementId0;
   private int mStructureElementId1;
   private int mStructureElementId2;
   private int mStructureElementId3;
   private int mStructureElementId4;
   private int mStructureElementId5;
   private int mStructureElementId6;
   private int mStructureElementId7;
   private int mStructureElementId8;
   private String mElementLabel0;
   private String mElementLabel1;
   private String mElementLabel2;
   private String mElementLabel3;
   private String mElementLabel4;
   private String mElementLabel5;
   private String mElementLabel6;
   private String mElementLabel7;
   private String mElementLabel8;
   private String mDataType;
   private int mVersionNum;
   private int mUpdatedByUserId;
   private Timestamp mUpdatedTime;
   private Timestamp mCreatedTime;
   private Map<DataEntryProfileHistoryPK, DataEntryProfileHistoryEVO> mDataEntryProfilesHistory;
   protected boolean mDataEntryProfilesHistoryAllItemsLoaded;
   private boolean mInsertPending;
   private boolean mDeletePending;
   private boolean mModified;
   private char mobile;

public DataEntryProfileEVO() {}

   public DataEntryProfileEVO(int newDataEntryProfileId, String newVisId, int newUserId, int newModelId, int newBudgetCycleId, int newAutoOpenDepth, String newDescription, int newXmlformId, boolean newFillDisplayArea, boolean newShowBoldSummaries, boolean newShowHorizontalLines, int newStructureId0, int newStructureId1, int newStructureId2, int newStructureId3, int newStructureId4, int newStructureId5, int newStructureId6, int newStructureId7, int newStructureId8, int newStructureElementId0, int newStructureElementId1, int newStructureElementId2, int newStructureElementId3, int newStructureElementId4, int newStructureElementId5, int newStructureElementId6, int newStructureElementId7, int newStructureElementId8, String newElementLabel0, String newElementLabel1, String newElementLabel2, String newElementLabel3, String newElementLabel4, String newElementLabel5, String newElementLabel6, String newElementLabel7, String newElementLabel8, String newDataType, int newVersionNum, Collection newDataEntryProfilesHistory) {
      this.mDataEntryProfileId = newDataEntryProfileId;
      this.mVisId = newVisId;
      this.mUserId = newUserId;
      this.mModelId = newModelId;
      this.mBudgetCycleId = newBudgetCycleId;
      this.mAutoOpenDepth = newAutoOpenDepth;
      this.mDescription = newDescription;
      this.mXmlformId = newXmlformId;
      this.mFillDisplayArea = newFillDisplayArea;
      this.mShowBoldSummaries = newShowBoldSummaries;
      this.mShowHorizontalLines = newShowHorizontalLines;
      this.mStructureId0 = newStructureId0;
      this.mStructureId1 = newStructureId1;
      this.mStructureId2 = newStructureId2;
      this.mStructureId3 = newStructureId3;
      this.mStructureId4 = newStructureId4;
      this.mStructureId5 = newStructureId5;
      this.mStructureId6 = newStructureId6;
      this.mStructureId7 = newStructureId7;
      this.mStructureId8 = newStructureId8;
      this.mStructureElementId0 = newStructureElementId0;
      this.mStructureElementId1 = newStructureElementId1;
      this.mStructureElementId2 = newStructureElementId2;
      this.mStructureElementId3 = newStructureElementId3;
      this.mStructureElementId4 = newStructureElementId4;
      this.mStructureElementId5 = newStructureElementId5;
      this.mStructureElementId6 = newStructureElementId6;
      this.mStructureElementId7 = newStructureElementId7;
      this.mStructureElementId8 = newStructureElementId8;
      this.mElementLabel0 = newElementLabel0;
      this.mElementLabel1 = newElementLabel1;
      this.mElementLabel2 = newElementLabel2;
      this.mElementLabel3 = newElementLabel3;
      this.mElementLabel4 = newElementLabel4;
      this.mElementLabel5 = newElementLabel5;
      this.mElementLabel6 = newElementLabel6;
      this.mElementLabel7 = newElementLabel7;
      this.mElementLabel8 = newElementLabel8;
      this.mDataType = newDataType;
      this.mVersionNum = newVersionNum;
      this.setDataEntryProfilesHistory(newDataEntryProfilesHistory);
   }

   public void setDataEntryProfilesHistory(Collection<DataEntryProfileHistoryEVO> items) {
      if(items != null) {
         if(this.mDataEntryProfilesHistory == null) {
            this.mDataEntryProfilesHistory = new HashMap();
         } else {
            this.mDataEntryProfilesHistory.clear();
         }

         Iterator i$ = items.iterator();

         while(i$.hasNext()) {
            DataEntryProfileHistoryEVO child = (DataEntryProfileHistoryEVO)i$.next();
            this.mDataEntryProfilesHistory.put(child.getPK(), child);
         }
      } else {
         this.mDataEntryProfilesHistory = null;
      }

   }

   public DataEntryProfilePK getPK() {
      if(this.mPK == null) {
         this.mPK = new DataEntryProfilePK(this.mDataEntryProfileId);
      }

      return this.mPK;
   }

   public boolean isModified() {
      return this.mModified;
   }

   public int getDataEntryProfileId() {
      return this.mDataEntryProfileId;
   }

   public String getVisId() {
      return this.mVisId;
   }

   public int getUserId() {
      return this.mUserId;
   }

   public int getModelId() {
      return this.mModelId;
   }
   
   public int getBudgetCycleId() {
	   return this.mBudgetCycleId;
   }
   
   public int getAutoOpenDepth() {
      return this.mAutoOpenDepth;
   }

   public String getDescription() {
      return this.mDescription;
   }

   public int getXmlformId() {
      return this.mXmlformId;
   }

   public boolean getFillDisplayArea() {
      return this.mFillDisplayArea;
   }

   public boolean getShowBoldSummaries() {
      return this.mShowBoldSummaries;
   }

   public boolean getShowHorizontalLines() {
      return this.mShowHorizontalLines;
   }

   public int[] getStructureIdArray() {
      return new int[]{this.getStructureId0(), this.getStructureId1(), this.getStructureId2(), this.getStructureId3(), this.getStructureId4(), this.getStructureId5(), this.getStructureId6(), this.getStructureId7(), this.getStructureId8()};
   }

   public void setStructureIdArray(int[] p) {
      this.setStructureId0(p[0]);
      this.setStructureId1(p[1]);
      this.setStructureId2(p[2]);
      this.setStructureId3(p[3]);
      this.setStructureId4(p[4]);
      this.setStructureId5(p[5]);
      this.setStructureId6(p[6]);
      this.setStructureId7(p[7]);
      this.setStructureId8(p[8]);
   }

   public int getStructureId0() {
      return this.mStructureId0;
   }

   public int getStructureId1() {
      return this.mStructureId1;
   }

   public int getStructureId2() {
      return this.mStructureId2;
   }

   public int getStructureId3() {
      return this.mStructureId3;
   }

   public int getStructureId4() {
      return this.mStructureId4;
   }

   public int getStructureId5() {
      return this.mStructureId5;
   }

   public int getStructureId6() {
      return this.mStructureId6;
   }

   public int getStructureId7() {
      return this.mStructureId7;
   }

   public int getStructureId8() {
      return this.mStructureId8;
   }

   public int[] getStructureElementIdArray() {
      return new int[]{this.getStructureElementId0(), this.getStructureElementId1(), this.getStructureElementId2(), this.getStructureElementId3(), this.getStructureElementId4(), this.getStructureElementId5(), this.getStructureElementId6(), this.getStructureElementId7(), this.getStructureElementId8()};
   }

   public void setStructureElementIdArray(int[] p) {
      this.setStructureElementId0(p[0]);
      this.setStructureElementId1(p[1]);
      this.setStructureElementId2(p[2]);
      this.setStructureElementId3(p[3]);
      this.setStructureElementId4(p[4]);
      this.setStructureElementId5(p[5]);
      this.setStructureElementId6(p[6]);
      this.setStructureElementId7(p[7]);
      this.setStructureElementId8(p[8]);
   }

   public int getStructureElementId0() {
      return this.mStructureElementId0;
   }

   public int getStructureElementId1() {
      return this.mStructureElementId1;
   }

   public int getStructureElementId2() {
      return this.mStructureElementId2;
   }

   public int getStructureElementId3() {
      return this.mStructureElementId3;
   }

   public int getStructureElementId4() {
      return this.mStructureElementId4;
   }

   public int getStructureElementId5() {
      return this.mStructureElementId5;
   }

   public int getStructureElementId6() {
      return this.mStructureElementId6;
   }

   public int getStructureElementId7() {
      return this.mStructureElementId7;
   }

   public int getStructureElementId8() {
      return this.mStructureElementId8;
   }

   public String[] getElementLabelArray() {
      return new String[]{this.getElementLabel0(), this.getElementLabel1(), this.getElementLabel2(), this.getElementLabel3(), this.getElementLabel4(), this.getElementLabel5(), this.getElementLabel6(), this.getElementLabel7(), this.getElementLabel8()};
   }

   public void setElementLabelArray(String[] p) {
      this.setElementLabel0(p[0]);
      this.setElementLabel1(p[1]);
      this.setElementLabel2(p[2]);
      this.setElementLabel3(p[3]);
      this.setElementLabel4(p[4]);
      this.setElementLabel5(p[5]);
      this.setElementLabel6(p[6]);
      this.setElementLabel7(p[7]);
      this.setElementLabel8(p[8]);
   }

   public String getElementLabel0() {
      return this.mElementLabel0;
   }

   public String getElementLabel1() {
      return this.mElementLabel1;
   }

   public String getElementLabel2() {
      return this.mElementLabel2;
   }

   public String getElementLabel3() {
      return this.mElementLabel3;
   }

   public String getElementLabel4() {
      return this.mElementLabel4;
   }

   public String getElementLabel5() {
      return this.mElementLabel5;
   }

   public String getElementLabel6() {
      return this.mElementLabel6;
   }

   public String getElementLabel7() {
      return this.mElementLabel7;
   }

   public String getElementLabel8() {
      return this.mElementLabel8;
   }

   public String getDataType() {
      return this.mDataType;
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

   public char getMobile() {
       return mobile;
   }

   public void setMobile(char mobile) {
       this.mobile = mobile;
   }
   
   public void setDataEntryProfileId(int newDataEntryProfileId) {
      if(this.mDataEntryProfileId != newDataEntryProfileId) {
         this.mModified = true;
         this.mDataEntryProfileId = newDataEntryProfileId;
         this.mPK = null;
      }
   }

   public void setUserId(int newUserId) {
      if(this.mUserId != newUserId) {
         this.mModified = true;
         this.mUserId = newUserId;
      }
   }
   
   public void setModelId(int newModelI) {
      if(this.mModelId != newModelI) {
         this.mModified = true;
         this.mModelId = newModelI;
      }
   }
   
   public void setBudgetCycleId(int newBudgetCycleId) {
      if(this.mBudgetCycleId != newBudgetCycleId) {
         this.mModified = true;
         this.mBudgetCycleId = newBudgetCycleId;
      }
   }

   public void setAutoOpenDepth(int newAutoOpenDepth) {
      if(this.mAutoOpenDepth != newAutoOpenDepth) {
         this.mModified = true;
         this.mAutoOpenDepth = newAutoOpenDepth;
      }
   }

   public void setXmlformId(int newXmlformId) {
      if(this.mXmlformId != newXmlformId) {
         this.mModified = true;
         this.mXmlformId = newXmlformId;
      }
   }

   public void setFillDisplayArea(boolean newFillDisplayArea) {
      if(this.mFillDisplayArea != newFillDisplayArea) {
         this.mModified = true;
         this.mFillDisplayArea = newFillDisplayArea;
      }
   }

   public void setShowBoldSummaries(boolean newShowBoldSummaries) {
      if(this.mShowBoldSummaries != newShowBoldSummaries) {
         this.mModified = true;
         this.mShowBoldSummaries = newShowBoldSummaries;
      }
   }

   public void setShowHorizontalLines(boolean newShowHorizontalLines) {
      if(this.mShowHorizontalLines != newShowHorizontalLines) {
         this.mModified = true;
         this.mShowHorizontalLines = newShowHorizontalLines;
      }
   }

   public void setStructureId0(int newStructureId0) {
      if(this.mStructureId0 != newStructureId0) {
         this.mModified = true;
         this.mStructureId0 = newStructureId0;
      }
   }

   public void setStructureId1(int newStructureId1) {
      if(this.mStructureId1 != newStructureId1) {
         this.mModified = true;
         this.mStructureId1 = newStructureId1;
      }
   }

   public void setStructureId2(int newStructureId2) {
      if(this.mStructureId2 != newStructureId2) {
         this.mModified = true;
         this.mStructureId2 = newStructureId2;
      }
   }

   public void setStructureId3(int newStructureId3) {
      if(this.mStructureId3 != newStructureId3) {
         this.mModified = true;
         this.mStructureId3 = newStructureId3;
      }
   }

   public void setStructureId4(int newStructureId4) {
      if(this.mStructureId4 != newStructureId4) {
         this.mModified = true;
         this.mStructureId4 = newStructureId4;
      }
   }

   public void setStructureId5(int newStructureId5) {
      if(this.mStructureId5 != newStructureId5) {
         this.mModified = true;
         this.mStructureId5 = newStructureId5;
      }
   }

   public void setStructureId6(int newStructureId6) {
      if(this.mStructureId6 != newStructureId6) {
         this.mModified = true;
         this.mStructureId6 = newStructureId6;
      }
   }

   public void setStructureId7(int newStructureId7) {
      if(this.mStructureId7 != newStructureId7) {
         this.mModified = true;
         this.mStructureId7 = newStructureId7;
      }
   }

   public void setStructureId8(int newStructureId8) {
      if(this.mStructureId8 != newStructureId8) {
         this.mModified = true;
         this.mStructureId8 = newStructureId8;
      }
   }

   public void setStructureElementId0(int newStructureElementId0) {
      if(this.mStructureElementId0 != newStructureElementId0) {
         this.mModified = true;
         this.mStructureElementId0 = newStructureElementId0;
      }
   }

   public void setStructureElementId1(int newStructureElementId1) {
      if(this.mStructureElementId1 != newStructureElementId1) {
         this.mModified = true;
         this.mStructureElementId1 = newStructureElementId1;
      }
   }

   public void setStructureElementId2(int newStructureElementId2) {
      if(this.mStructureElementId2 != newStructureElementId2) {
         this.mModified = true;
         this.mStructureElementId2 = newStructureElementId2;
      }
   }

   public void setStructureElementId3(int newStructureElementId3) {
      if(this.mStructureElementId3 != newStructureElementId3) {
         this.mModified = true;
         this.mStructureElementId3 = newStructureElementId3;
      }
   }

   public void setStructureElementId4(int newStructureElementId4) {
      if(this.mStructureElementId4 != newStructureElementId4) {
         this.mModified = true;
         this.mStructureElementId4 = newStructureElementId4;
      }
   }

   public void setStructureElementId5(int newStructureElementId5) {
      if(this.mStructureElementId5 != newStructureElementId5) {
         this.mModified = true;
         this.mStructureElementId5 = newStructureElementId5;
      }
   }

   public void setStructureElementId6(int newStructureElementId6) {
      if(this.mStructureElementId6 != newStructureElementId6) {
         this.mModified = true;
         this.mStructureElementId6 = newStructureElementId6;
      }
   }

   public void setStructureElementId7(int newStructureElementId7) {
      if(this.mStructureElementId7 != newStructureElementId7) {
         this.mModified = true;
         this.mStructureElementId7 = newStructureElementId7;
      }
   }

   public void setStructureElementId8(int newStructureElementId8) {
      if(this.mStructureElementId8 != newStructureElementId8) {
         this.mModified = true;
         this.mStructureElementId8 = newStructureElementId8;
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

   public void setElementLabel0(String newElementLabel0) {
      if(this.mElementLabel0 != null && newElementLabel0 == null || this.mElementLabel0 == null && newElementLabel0 != null || this.mElementLabel0 != null && newElementLabel0 != null && !this.mElementLabel0.equals(newElementLabel0)) {
         this.mElementLabel0 = newElementLabel0;
         this.mModified = true;
      }

   }

   public void setElementLabel1(String newElementLabel1) {
      if(this.mElementLabel1 != null && newElementLabel1 == null || this.mElementLabel1 == null && newElementLabel1 != null || this.mElementLabel1 != null && newElementLabel1 != null && !this.mElementLabel1.equals(newElementLabel1)) {
         this.mElementLabel1 = newElementLabel1;
         this.mModified = true;
      }

   }

   public void setElementLabel2(String newElementLabel2) {
      if(this.mElementLabel2 != null && newElementLabel2 == null || this.mElementLabel2 == null && newElementLabel2 != null || this.mElementLabel2 != null && newElementLabel2 != null && !this.mElementLabel2.equals(newElementLabel2)) {
         this.mElementLabel2 = newElementLabel2;
         this.mModified = true;
      }

   }

   public void setElementLabel3(String newElementLabel3) {
      if(this.mElementLabel3 != null && newElementLabel3 == null || this.mElementLabel3 == null && newElementLabel3 != null || this.mElementLabel3 != null && newElementLabel3 != null && !this.mElementLabel3.equals(newElementLabel3)) {
         this.mElementLabel3 = newElementLabel3;
         this.mModified = true;
      }

   }

   public void setElementLabel4(String newElementLabel4) {
      if(this.mElementLabel4 != null && newElementLabel4 == null || this.mElementLabel4 == null && newElementLabel4 != null || this.mElementLabel4 != null && newElementLabel4 != null && !this.mElementLabel4.equals(newElementLabel4)) {
         this.mElementLabel4 = newElementLabel4;
         this.mModified = true;
      }

   }

   public void setElementLabel5(String newElementLabel5) {
      if(this.mElementLabel5 != null && newElementLabel5 == null || this.mElementLabel5 == null && newElementLabel5 != null || this.mElementLabel5 != null && newElementLabel5 != null && !this.mElementLabel5.equals(newElementLabel5)) {
         this.mElementLabel5 = newElementLabel5;
         this.mModified = true;
      }

   }

   public void setElementLabel6(String newElementLabel6) {
      if(this.mElementLabel6 != null && newElementLabel6 == null || this.mElementLabel6 == null && newElementLabel6 != null || this.mElementLabel6 != null && newElementLabel6 != null && !this.mElementLabel6.equals(newElementLabel6)) {
         this.mElementLabel6 = newElementLabel6;
         this.mModified = true;
      }

   }

   public void setElementLabel7(String newElementLabel7) {
      if(this.mElementLabel7 != null && newElementLabel7 == null || this.mElementLabel7 == null && newElementLabel7 != null || this.mElementLabel7 != null && newElementLabel7 != null && !this.mElementLabel7.equals(newElementLabel7)) {
         this.mElementLabel7 = newElementLabel7;
         this.mModified = true;
      }

   }

   public void setElementLabel8(String newElementLabel8) {
      if(this.mElementLabel8 != null && newElementLabel8 == null || this.mElementLabel8 == null && newElementLabel8 != null || this.mElementLabel8 != null && newElementLabel8 != null && !this.mElementLabel8.equals(newElementLabel8)) {
         this.mElementLabel8 = newElementLabel8;
         this.mModified = true;
      }

   }

   public void setDataType(String newDataType) {
      if(this.mDataType != null && newDataType == null || this.mDataType == null && newDataType != null || this.mDataType != null && newDataType != null && !this.mDataType.equals(newDataType)) {
         this.mDataType = newDataType;
         this.mModified = true;
      }

   }

   protected void setUpdatedTime(Timestamp newUpdatedTime) {
      this.mUpdatedTime = newUpdatedTime;
   }

   protected void setCreatedTime(Timestamp newCreatedTime) {
      this.mCreatedTime = newCreatedTime;
   }

   public void setDetails(DataEntryProfileEVO newDetails) {
      this.setDataEntryProfileId(newDetails.getDataEntryProfileId());
      this.setVisId(newDetails.getVisId());
      this.setUserId(newDetails.getUserId());
      this.setModelId(newDetails.getModelId());
      this.setBudgetCycleId(newDetails.getBudgetCycleId());
      this.setAutoOpenDepth(newDetails.getAutoOpenDepth());
      this.setDescription(newDetails.getDescription());
      this.setXmlformId(newDetails.getXmlformId());
      this.setFillDisplayArea(newDetails.getFillDisplayArea());
      this.setShowBoldSummaries(newDetails.getShowBoldSummaries());
      this.setShowHorizontalLines(newDetails.getShowHorizontalLines());
      this.setStructureId0(newDetails.getStructureId0());
      this.setStructureId1(newDetails.getStructureId1());
      this.setStructureId2(newDetails.getStructureId2());
      this.setStructureId3(newDetails.getStructureId3());
      this.setStructureId4(newDetails.getStructureId4());
      this.setStructureId5(newDetails.getStructureId5());
      this.setStructureId6(newDetails.getStructureId6());
      this.setStructureId7(newDetails.getStructureId7());
      this.setStructureId8(newDetails.getStructureId8());
      this.setStructureElementId0(newDetails.getStructureElementId0());
      this.setStructureElementId1(newDetails.getStructureElementId1());
      this.setStructureElementId2(newDetails.getStructureElementId2());
      this.setStructureElementId3(newDetails.getStructureElementId3());
      this.setStructureElementId4(newDetails.getStructureElementId4());
      this.setStructureElementId5(newDetails.getStructureElementId5());
      this.setStructureElementId6(newDetails.getStructureElementId6());
      this.setStructureElementId7(newDetails.getStructureElementId7());
      this.setStructureElementId8(newDetails.getStructureElementId8());
      this.setElementLabel0(newDetails.getElementLabel0());
      this.setElementLabel1(newDetails.getElementLabel1());
      this.setElementLabel2(newDetails.getElementLabel2());
      this.setElementLabel3(newDetails.getElementLabel3());
      this.setElementLabel4(newDetails.getElementLabel4());
      this.setElementLabel5(newDetails.getElementLabel5());
      this.setElementLabel6(newDetails.getElementLabel6());
      this.setElementLabel7(newDetails.getElementLabel7());
      this.setElementLabel8(newDetails.getElementLabel8());
      this.setDataType(newDetails.getDataType());
      this.setVersionNum(newDetails.getVersionNum());
      this.setUpdatedByUserId(newDetails.getUpdatedByUserId());
      this.setUpdatedTime(newDetails.getUpdatedTime());
      this.setCreatedTime(newDetails.getCreatedTime());
   }

   public DataEntryProfileEVO deepClone() {
      DataEntryProfileEVO cloned = null;

      try {
         ByteArrayOutputStream e = new ByteArrayOutputStream();
         ObjectOutputStream oos = new ObjectOutputStream(e);
         oos.writeObject(this);
         oos.flush();
         oos.close();
         ByteArrayInputStream bis = new ByteArrayInputStream(e.toByteArray());
         ObjectInputStream ois = new ObjectInputStream(bis);
         cloned = (DataEntryProfileEVO)ois.readObject();
         ois.close();
         return cloned;
      } catch (Exception var6) {
         throw new RuntimeException(var6);
      }
   }

   public void prepareForInsert(UserEVO parent) {
      boolean newKey = this.insertPending();
      if(this.mDataEntryProfileId > 0) {
         newKey = true;
         if(parent == null) {
            this.setDataEntryProfileId(-this.mDataEntryProfileId);
         } else {
            parent.changeKey(this, -this.mDataEntryProfileId);
         }
      } else if(this.mDataEntryProfileId < 1) {
         newKey = true;
      }

      this.setVersionNum(0);
      DataEntryProfileHistoryEVO item;
      if(this.mDataEntryProfilesHistory != null) {
         for(Iterator iter = (new ArrayList(this.mDataEntryProfilesHistory.values())).iterator(); iter.hasNext(); item.prepareForInsert(this)) {
            item = (DataEntryProfileHistoryEVO)iter.next();
            if(newKey) {
               item.setInsertPending();
            }
         }
      }

   }

   public int getInsertCount(int startCount) {
      int returnCount = startCount;
      if(this.mDataEntryProfileId < 1) {
         returnCount = startCount + 1;
      }

      DataEntryProfileHistoryEVO item;
      if(this.mDataEntryProfilesHistory != null) {
         for(Iterator iter = this.mDataEntryProfilesHistory.values().iterator(); iter.hasNext(); returnCount = item.getInsertCount(returnCount)) {
            item = (DataEntryProfileHistoryEVO)iter.next();
         }
      }

      return returnCount;
   }

   public int assignNextKey(UserEVO parent, int startKey) {
      int nextKey = startKey;
      if(this.mDataEntryProfileId < 1) {
         parent.changeKey(this, startKey);
         nextKey = startKey + 1;
      }

      DataEntryProfileHistoryEVO item;
      if(this.mDataEntryProfilesHistory != null) {
         for(Iterator iter = (new ArrayList(this.mDataEntryProfilesHistory.values())).iterator(); iter.hasNext(); nextKey = item.assignNextKey(this, nextKey)) {
            item = (DataEntryProfileHistoryEVO)iter.next();
            item.setDataEntryProfileId(this.mDataEntryProfileId);
         }
      }

      return nextKey;
   }

   public Collection<DataEntryProfileHistoryEVO> getDataEntryProfilesHistory() {
      return this.mDataEntryProfilesHistory != null?this.mDataEntryProfilesHistory.values():null;
   }

   public Map<DataEntryProfileHistoryPK, DataEntryProfileHistoryEVO> getDataEntryProfilesHistoryMap() {
      return this.mDataEntryProfilesHistory;
   }

   public void loadDataEntryProfilesHistoryItem(DataEntryProfileHistoryEVO newItem) {
      if(this.mDataEntryProfilesHistory == null) {
         this.mDataEntryProfilesHistory = new HashMap();
      }

      this.mDataEntryProfilesHistory.put(newItem.getPK(), newItem);
   }

   public void addDataEntryProfilesHistoryItem(DataEntryProfileHistoryEVO newItem) {
      if(this.mDataEntryProfilesHistory == null) {
         this.mDataEntryProfilesHistory = new HashMap();
      }

      DataEntryProfileHistoryPK newPK = newItem.getPK();
      if(this.getDataEntryProfilesHistoryItem(newPK) != null) {
         throw new RuntimeException("addDataEntryProfilesHistoryItem: key already in list");
      } else {
         newItem.setInsertPending();
         this.mDataEntryProfilesHistory.put(newPK, newItem);
      }
   }

   public void changeDataEntryProfilesHistoryItem(DataEntryProfileHistoryEVO changedItem) {
      if(this.mDataEntryProfilesHistory == null) {
         throw new RuntimeException("changeDataEntryProfilesHistoryItem: no items in collection");
      } else {
         DataEntryProfileHistoryPK changedPK = changedItem.getPK();
         DataEntryProfileHistoryEVO listItem = this.getDataEntryProfilesHistoryItem(changedPK);
         if(listItem == null) {
            throw new RuntimeException("changeDataEntryProfilesHistoryItem: item not in list");
         } else {
            listItem.setDetails(changedItem);
         }
      }
   }

   public void deleteDataEntryProfilesHistoryItem(DataEntryProfileHistoryPK removePK) {
      DataEntryProfileHistoryEVO listItem = this.getDataEntryProfilesHistoryItem(removePK);
      if(listItem == null) {
         throw new RuntimeException("removeDataEntryProfilesHistoryItem: item not in list");
      } else {
         listItem.setDeletePending();
      }
   }

   public DataEntryProfileHistoryEVO getDataEntryProfilesHistoryItem(DataEntryProfileHistoryPK pk) {
      return (DataEntryProfileHistoryEVO)this.mDataEntryProfilesHistory.get(pk);
   }

   public DataEntryProfileHistoryEVO getDataEntryProfilesHistoryItem() {
      if(this.mDataEntryProfilesHistory.size() != 1) {
         throw new RuntimeException("should be 1 item but size=" + this.mDataEntryProfilesHistory.size());
      } else {
         Iterator iter = this.mDataEntryProfilesHistory.values().iterator();
         return (DataEntryProfileHistoryEVO)iter.next();
      }
   }

   public void setInsertPending() {
      this.mInsertPending = true;
   }

   public boolean insertPending() {
      return this.mInsertPending;
   }

   public void setDeletePending() {
      this.mDeletePending = true;
   }

   public boolean deletePending() {
      return this.mDeletePending;
   }

   protected void reset() {
      this.mModified = false;
      this.mInsertPending = false;
   }

   public DataEntryProfileRef getEntityRef(UserEVO evoUser) {
      return new DataEntryProfileRefImpl(new DataEntryProfileCK(evoUser.getPK(), this.getPK()), this.mVisId);
   }

   public DataEntryProfileCK getCK(UserEVO evoUser) {
      return new DataEntryProfileCK(evoUser.getPK(), this.getPK());
   }

   public void postCreateInit() {
      this.mDataEntryProfilesHistoryAllItemsLoaded = true;
      if(this.mDataEntryProfilesHistory == null) {
         this.mDataEntryProfilesHistory = new HashMap();
      }

   }

   public String toString() {
      StringBuffer sb = new StringBuffer();
      sb.append("DataEntryProfileId=");
      sb.append(String.valueOf(this.mDataEntryProfileId));
      sb.append(' ');
      sb.append("VisId=");
      sb.append(String.valueOf(this.mVisId));
      sb.append(' ');
      sb.append("UserId=");
      sb.append(String.valueOf(this.mUserId));
      sb.append(' ');
      sb.append("ModelId=");
      sb.append(String.valueOf(this.mModelId));
      sb.append(' ');
      sb.append("BudgetCycleId=");
      sb.append(String.valueOf(this.mBudgetCycleId));
      sb.append(' ');
      sb.append("AutoOpenDepth=");
      sb.append(String.valueOf(this.mAutoOpenDepth));
      sb.append(' ');
      sb.append("Description=");
      sb.append(String.valueOf(this.mDescription));
      sb.append(' ');
      sb.append("XmlformId=");
      sb.append(String.valueOf(this.mXmlformId));
      sb.append(' ');
      sb.append("FillDisplayArea=");
      sb.append(String.valueOf(this.mFillDisplayArea));
      sb.append(' ');
      sb.append("ShowBoldSummaries=");
      sb.append(String.valueOf(this.mShowBoldSummaries));
      sb.append(' ');
      sb.append("ShowHorizontalLines=");
      sb.append(String.valueOf(this.mShowHorizontalLines));
      sb.append(' ');
      sb.append("StructureId0=");
      sb.append(String.valueOf(this.mStructureId0));
      sb.append(' ');
      sb.append("StructureId1=");
      sb.append(String.valueOf(this.mStructureId1));
      sb.append(' ');
      sb.append("StructureId2=");
      sb.append(String.valueOf(this.mStructureId2));
      sb.append(' ');
      sb.append("StructureId3=");
      sb.append(String.valueOf(this.mStructureId3));
      sb.append(' ');
      sb.append("StructureId4=");
      sb.append(String.valueOf(this.mStructureId4));
      sb.append(' ');
      sb.append("StructureId5=");
      sb.append(String.valueOf(this.mStructureId5));
      sb.append(' ');
      sb.append("StructureId6=");
      sb.append(String.valueOf(this.mStructureId6));
      sb.append(' ');
      sb.append("StructureId7=");
      sb.append(String.valueOf(this.mStructureId7));
      sb.append(' ');
      sb.append("StructureId8=");
      sb.append(String.valueOf(this.mStructureId8));
      sb.append(' ');
      sb.append("StructureElementId0=");
      sb.append(String.valueOf(this.mStructureElementId0));
      sb.append(' ');
      sb.append("StructureElementId1=");
      sb.append(String.valueOf(this.mStructureElementId1));
      sb.append(' ');
      sb.append("StructureElementId2=");
      sb.append(String.valueOf(this.mStructureElementId2));
      sb.append(' ');
      sb.append("StructureElementId3=");
      sb.append(String.valueOf(this.mStructureElementId3));
      sb.append(' ');
      sb.append("StructureElementId4=");
      sb.append(String.valueOf(this.mStructureElementId4));
      sb.append(' ');
      sb.append("StructureElementId5=");
      sb.append(String.valueOf(this.mStructureElementId5));
      sb.append(' ');
      sb.append("StructureElementId6=");
      sb.append(String.valueOf(this.mStructureElementId6));
      sb.append(' ');
      sb.append("StructureElementId7=");
      sb.append(String.valueOf(this.mStructureElementId7));
      sb.append(' ');
      sb.append("StructureElementId8=");
      sb.append(String.valueOf(this.mStructureElementId8));
      sb.append(' ');
      sb.append("ElementLabel0=");
      sb.append(String.valueOf(this.mElementLabel0));
      sb.append(' ');
      sb.append("ElementLabel1=");
      sb.append(String.valueOf(this.mElementLabel1));
      sb.append(' ');
      sb.append("ElementLabel2=");
      sb.append(String.valueOf(this.mElementLabel2));
      sb.append(' ');
      sb.append("ElementLabel3=");
      sb.append(String.valueOf(this.mElementLabel3));
      sb.append(' ');
      sb.append("ElementLabel4=");
      sb.append(String.valueOf(this.mElementLabel4));
      sb.append(' ');
      sb.append("ElementLabel5=");
      sb.append(String.valueOf(this.mElementLabel5));
      sb.append(' ');
      sb.append("ElementLabel6=");
      sb.append(String.valueOf(this.mElementLabel6));
      sb.append(' ');
      sb.append("ElementLabel7=");
      sb.append(String.valueOf(this.mElementLabel7));
      sb.append(' ');
      sb.append("ElementLabel8=");
      sb.append(String.valueOf(this.mElementLabel8));
      sb.append(' ');
      sb.append("DataType=");
      sb.append(String.valueOf(this.mDataType));
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

      if(this.mInsertPending) {
         sb.append("insertPending ");
      }

      if(this.mDeletePending) {
         sb.append("deletePending ");
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

      sb.append("DataEntryProfile: ");
      sb.append(this.toString());
      if(this.mDataEntryProfilesHistoryAllItemsLoaded || this.mDataEntryProfilesHistory != null) {
         sb.delete(indent, sb.length());
         sb.append(" - DataEntryProfilesHistory: allItemsLoaded=");
         sb.append(String.valueOf(this.mDataEntryProfilesHistoryAllItemsLoaded));
         sb.append(" items=");
         if(this.mDataEntryProfilesHistory == null) {
            sb.append("null");
         } else {
            sb.append(String.valueOf(this.mDataEntryProfilesHistory.size()));
         }
      }

      if(this.mDataEntryProfilesHistory != null) {
         Iterator var5 = this.mDataEntryProfilesHistory.values().iterator();

         while(var5.hasNext()) {
            DataEntryProfileHistoryEVO listItem = (DataEntryProfileHistoryEVO)var5.next();
            listItem.print(indent + 4);
         }
      }

      return sb.toString();
   }

   public void changeKey(DataEntryProfileHistoryEVO child, int newUserId, int newModelId, int newBudgetLocationElementId) {
      if(this.getDataEntryProfilesHistoryItem(child.getPK()) != child) {
         throw new IllegalStateException("changeKey child not found in parent");
      } else {
         this.mDataEntryProfilesHistory.remove(child.getPK());
         child.setUserId(newUserId);
         child.setModelId(newModelId);
         child.setBudgetLocationElementId(newBudgetLocationElementId);
         this.mDataEntryProfilesHistory.put(child.getPK(), child);
      }
   }

   public void setDataEntryProfilesHistoryAllItemsLoaded(boolean allItemsLoaded) {
      this.mDataEntryProfilesHistoryAllItemsLoaded = allItemsLoaded;
   }

   public boolean isDataEntryProfilesHistoryAllItemsLoaded() {
      return this.mDataEntryProfilesHistoryAllItemsLoaded;
   }
}
