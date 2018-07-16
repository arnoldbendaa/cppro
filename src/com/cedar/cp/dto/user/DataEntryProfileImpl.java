// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:06:11
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.user;

import com.cedar.cp.api.user.DataEntryProfile;
import com.cedar.cp.api.user.UserRef;
import com.cedar.cp.api.xmlform.XmlFormRef;
import com.cedar.cp.dto.user.DataEntryProfileCK;
import com.cedar.cp.dto.user.DataEntryProfilePK;
import java.io.Serializable;

public class DataEntryProfileImpl implements DataEntryProfile, Serializable, Cloneable {

   private Object mPrimaryKey;
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
   private UserRef mUserRef;
   private XmlFormRef mXmlFormRef;

   public DataEntryProfileImpl(Object paramKey) {
      this.mPrimaryKey = paramKey;
      this.mVisId = "";
      this.mUserId = 0;
      this.mModelId = 0;
      this.mBudgetCycleId = 0;
      this.mAutoOpenDepth = 0;
      this.mDescription = "";
      this.mXmlformId = 0;
      this.mFillDisplayArea = false;
      this.mShowBoldSummaries = false;
      this.mShowHorizontalLines = false;
      this.mStructureId0 = 0;
      this.mStructureId1 = 0;
      this.mStructureId2 = 0;
      this.mStructureId3 = 0;
      this.mStructureId4 = 0;
      this.mStructureId5 = 0;
      this.mStructureId6 = 0;
      this.mStructureId7 = 0;
      this.mStructureId8 = 0;
      this.mStructureElementId0 = 0;
      this.mStructureElementId1 = 0;
      this.mStructureElementId2 = 0;
      this.mStructureElementId3 = 0;
      this.mStructureElementId4 = 0;
      this.mStructureElementId5 = 0;
      this.mStructureElementId6 = 0;
      this.mStructureElementId7 = 0;
      this.mStructureElementId8 = 0;
      this.mElementLabel0 = "";
      this.mElementLabel1 = "";
      this.mElementLabel2 = "";
      this.mElementLabel3 = "";
      this.mElementLabel4 = "";
      this.mElementLabel5 = "";
      this.mElementLabel6 = "";
      this.mElementLabel7 = "";
      this.mElementLabel8 = "";
      this.mDataType = "";      
   }

   public Object getPrimaryKey() {
      return this.mPrimaryKey;
   }

   public void setPrimaryKey(Object paramKey) {
      this.mPrimaryKey = (DataEntryProfilePK)paramKey;
   }

   public void setPrimaryKey(DataEntryProfileCK paramKey) {
      this.mPrimaryKey = paramKey;
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

   public int getAutoOpenDepth() {
      return this.mAutoOpenDepth;
   }

   public String getDescription() {
      return this.mDescription;
   }

   public int getXmlformId() {
      return this.mXmlformId;
   }

   public boolean isFillDisplayArea() {
      return this.mFillDisplayArea;
   }

   public boolean isShowBoldSummaries() {
      return this.mShowBoldSummaries;
   }

   public boolean isShowHorizontalLines() {
      return this.mShowHorizontalLines;
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

   public UserRef getUserRef() {
      return this.mUserRef;
   }

   public XmlFormRef getXmlFormRef() {
      return this.mXmlFormRef;
   }

   public void setUserRef(UserRef ref) {
      this.mUserRef = ref;
   }

   public void setXmlFormRef(XmlFormRef ref) {
      this.mXmlFormRef = ref;
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

   public void setUserId(int paramUserId) {
      this.mUserId = paramUserId;
   }

   public void setModelId(int paramModelId) {
      this.mModelId = paramModelId;
   }

   public void setAutoOpenDepth(int paramAutoOpenDepth) {
      this.mAutoOpenDepth = paramAutoOpenDepth;
   }

   public void setDescription(String paramDescription) {
      this.mDescription = paramDescription;
   }

   public void setXmlformId(int paramXmlformId) {
      this.mXmlformId = paramXmlformId;
   }

   public void setFillDisplayArea(boolean paramFillDisplayArea) {
      this.mFillDisplayArea = paramFillDisplayArea;
   }

   public void setShowBoldSummaries(boolean paramShowBoldSummaries) {
      this.mShowBoldSummaries = paramShowBoldSummaries;
   }

   public void setShowHorizontalLines(boolean paramShowHorizontalLines) {
      this.mShowHorizontalLines = paramShowHorizontalLines;
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

   public void setStructureId0(int paramStructureId0) {
      this.mStructureId0 = paramStructureId0;
   }

   public void setStructureId1(int paramStructureId1) {
      this.mStructureId1 = paramStructureId1;
   }

   public void setStructureId2(int paramStructureId2) {
      this.mStructureId2 = paramStructureId2;
   }

   public void setStructureId3(int paramStructureId3) {
      this.mStructureId3 = paramStructureId3;
   }

   public void setStructureId4(int paramStructureId4) {
      this.mStructureId4 = paramStructureId4;
   }

   public void setStructureId5(int paramStructureId5) {
      this.mStructureId5 = paramStructureId5;
   }

   public void setStructureId6(int paramStructureId6) {
      this.mStructureId6 = paramStructureId6;
   }

   public void setStructureId7(int paramStructureId7) {
      this.mStructureId7 = paramStructureId7;
   }

   public void setStructureId8(int paramStructureId8) {
      this.mStructureId8 = paramStructureId8;
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

   public void setStructureElementId0(int paramStructureElementId0) {
      this.mStructureElementId0 = paramStructureElementId0;
   }

   public void setStructureElementId1(int paramStructureElementId1) {
      this.mStructureElementId1 = paramStructureElementId1;
   }

   public void setStructureElementId2(int paramStructureElementId2) {
      this.mStructureElementId2 = paramStructureElementId2;
   }

   public void setStructureElementId3(int paramStructureElementId3) {
      this.mStructureElementId3 = paramStructureElementId3;
   }

   public void setStructureElementId4(int paramStructureElementId4) {
      this.mStructureElementId4 = paramStructureElementId4;
   }

   public void setStructureElementId5(int paramStructureElementId5) {
      this.mStructureElementId5 = paramStructureElementId5;
   }

   public void setStructureElementId6(int paramStructureElementId6) {
      this.mStructureElementId6 = paramStructureElementId6;
   }

   public void setStructureElementId7(int paramStructureElementId7) {
      this.mStructureElementId7 = paramStructureElementId7;
   }

   public void setStructureElementId8(int paramStructureElementId8) {
      this.mStructureElementId8 = paramStructureElementId8;
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

   public void setElementLabel0(String paramElementLabel0) {
      this.mElementLabel0 = paramElementLabel0;
   }

   public void setElementLabel1(String paramElementLabel1) {
      this.mElementLabel1 = paramElementLabel1;
   }

   public void setElementLabel2(String paramElementLabel2) {
      this.mElementLabel2 = paramElementLabel2;
   }

   public void setElementLabel3(String paramElementLabel3) {
      this.mElementLabel3 = paramElementLabel3;
   }

   public void setElementLabel4(String paramElementLabel4) {
      this.mElementLabel4 = paramElementLabel4;
   }

   public void setElementLabel5(String paramElementLabel5) {
      this.mElementLabel5 = paramElementLabel5;
   }

   public void setElementLabel6(String paramElementLabel6) {
      this.mElementLabel6 = paramElementLabel6;
   }

   public void setElementLabel7(String paramElementLabel7) {
      this.mElementLabel7 = paramElementLabel7;
   }

   public void setElementLabel8(String paramElementLabel8) {
      this.mElementLabel8 = paramElementLabel8;
   }

   public void setDataType(String paramDataType) {
      this.mDataType = paramDataType;
   }

	public int getBudgetCycleId() {
		return mBudgetCycleId;
	}
	
	public void setBudgetCycleId(int mBudgetCycleId) {
		this.mBudgetCycleId = mBudgetCycleId;
	}
}
