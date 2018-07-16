package com.cedar.cp.utc.de.profiles;

import com.cedar.cp.api.user.DataEntryProfile;
import com.cedar.cp.api.user.UserRef;
import com.cedar.cp.api.xmlform.XmlFormRef;
import java.io.Serializable;

public class DataEntryProfileVO implements DataEntryProfile, Serializable {

   private String mVisId;
   private int mUserId;
   private int mAutoOpenDepth;
   private String mDescription;
   private int mXmlformId;
   private boolean mFillDisplayArea;
   private boolean mShowBoldSummaries;
   private boolean mShowHorizontalLines;
   private int mVersionNum;
   private UserRef mUserRef;
   private Object mPrimaryKey;
   private XmlFormRef mXmlFormRef;
   private int mModelId;
   private int mBudgetCycleId;
   private int mFinanceCubeId;
   private String mDataType;
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


   public Object getPrimaryKey() {
      return this.mPrimaryKey;
   }

   public void setPrimaryKey(Object primaryKey) {
      this.mPrimaryKey = primaryKey;
   }

   public String getVisId() {
      return this.mVisId;
   }

   public void setVisId(String visId) {
      this.mVisId = visId;
   }

   public int getUserId() {
      return this.mUserId;
   }

   public void setUserId(int userId) {
      this.mUserId = userId;
   }

   public int getAutoOpenDepth() {
      return this.mAutoOpenDepth;
   }

   public void setAutoOpenDepth(int autoOpenDepth) {
      this.mAutoOpenDepth = autoOpenDepth;
   }

   public String getDescription() {
      return this.mDescription;
   }

   public void setDescription(String description) {
      this.mDescription = description;
   }

   public int getXmlformId() {
      return this.mXmlformId;
   }

   public void setXmlformId(int xmlformId) {
      this.mXmlformId = xmlformId;
   }

   public boolean isFillDisplayArea() {
      return this.mFillDisplayArea;
   }

   public void setFillDisplayArea(boolean fillDisplayArea) {
      this.mFillDisplayArea = fillDisplayArea;
   }

   public boolean isShowBoldSummaries() {
      return this.mShowBoldSummaries;
   }

   public void setShowBoldSummaries(boolean showBoldSummaries) {
      this.mShowBoldSummaries = showBoldSummaries;
   }

   public boolean isShowHorizontalLines() {
      return this.mShowHorizontalLines;
   }

   public void setShowHorizontalLines(boolean showHorizontalLines) {
      this.mShowHorizontalLines = showHorizontalLines;
   }

   public int getVersionNum() {
      return this.mVersionNum;
   }

   public void setVersionNum(int versionNum) {
      this.mVersionNum = versionNum;
   }

   public UserRef getUserRef() {
      return this.mUserRef;
   }

   public void setUserRef(UserRef userRef) {
      this.mUserRef = userRef;
   }

   public XmlFormRef getXmlFormRef() {
      return this.mXmlFormRef;
   }

   public void setXmlFormRef(XmlFormRef xmlFormRef) {
      this.mXmlFormRef = xmlFormRef;
   }

   public int getFinanceCubeId() {
      return this.mFinanceCubeId;
   }

   public void setFinanceCubeId(int financeCubeId) {
      this.mFinanceCubeId = financeCubeId;
   }

   public int getModelId() {
      return this.mModelId;
   }

   public void setModelId(int modelId) {
      this.mModelId = modelId;
   }

	public int getBudgetCycleId() {
		return this.mBudgetCycleId;
	}

	public void setBudgetCycleId(int budgetCycleId) {
		this.mBudgetCycleId = budgetCycleId;
	}
	   
   public String getDataType() {
      return this.mDataType;
   }

   public void setDataType(String dataType) {
      this.mDataType = dataType;
   }

   public int getStructureId0() {
      return this.mStructureId0;
   }

   public void setStructureId0(int structureId0) {
      this.mStructureId0 = structureId0;
   }

   public int getStructureId1() {
      return this.mStructureId1;
   }

   public void setStructureId1(int structureId1) {
      this.mStructureId1 = structureId1;
   }

   public int getStructureId2() {
      return this.mStructureId2;
   }

   public void setStructureId2(int structureId2) {
      this.mStructureId2 = structureId2;
   }

   public int getStructureId3() {
      return this.mStructureId3;
   }

   public void setStructureId3(int structureId3) {
      this.mStructureId3 = structureId3;
   }

   public int getStructureId4() {
      return this.mStructureId4;
   }

   public void setStructureId4(int structureId4) {
      this.mStructureId4 = structureId4;
   }

   public int getStructureId5() {
      return this.mStructureId5;
   }

   public void setStructureId5(int structureId5) {
      this.mStructureId5 = structureId5;
   }

   public int getStructureId6() {
      return this.mStructureId6;
   }

   public void setStructureId6(int structureId6) {
      this.mStructureId6 = structureId6;
   }

   public int getStructureId7() {
      return this.mStructureId7;
   }

   public void setStructureId7(int structureId7) {
      this.mStructureId7 = structureId7;
   }

   public int getStructureId8() {
      return this.mStructureId8;
   }

   public void setStructureId8(int structureId8) {
      this.mStructureId8 = structureId8;
   }

   public int getStructureElementId0() {
      return this.mStructureElementId0;
   }

   public void setStructureElementId0(int structureElementId0) {
      this.mStructureElementId0 = structureElementId0;
   }

   public int getStructureElementId1() {
      return this.mStructureElementId1;
   }

   public void setStructureElementId1(int structureElementId1) {
      this.mStructureElementId1 = structureElementId1;
   }

   public int getStructureElementId2() {
      return this.mStructureElementId2;
   }

   public void setStructureElementId2(int structureElementId2) {
      this.mStructureElementId2 = structureElementId2;
   }

   public int getStructureElementId3() {
      return this.mStructureElementId3;
   }

   public void setStructureElementId3(int structureElementId3) {
      this.mStructureElementId3 = structureElementId3;
   }

   public int getStructureElementId4() {
      return this.mStructureElementId4;
   }

   public void setStructureElementId4(int structureElementId4) {
      this.mStructureElementId4 = structureElementId4;
   }

   public int getStructureElementId5() {
      return this.mStructureElementId5;
   }

   public void setStructureElementId5(int structureElementId5) {
      this.mStructureElementId5 = structureElementId5;
   }

   public int getStructureElementId6() {
      return this.mStructureElementId6;
   }

   public void setStructureElementId6(int structureElementId6) {
      this.mStructureElementId6 = structureElementId6;
   }

   public int getStructureElementId7() {
      return this.mStructureElementId7;
   }

   public void setStructureElementId7(int structureElementId7) {
      this.mStructureElementId7 = structureElementId7;
   }

   public int getStructureElementId8() {
      return this.mStructureElementId8;
   }

   public void setStructureElementId8(int structureElementId8) {
      this.mStructureElementId8 = structureElementId8;
   }

   public String getElementLabel0() {
      return this.mElementLabel0;
   }

   public void setElementLabel0(String elementLabel0) {
      this.mElementLabel0 = elementLabel0;
   }

   public String getElementLabel1() {
      return this.mElementLabel1;
   }

   public void setElementLabel1(String elementLabel1) {
      this.mElementLabel1 = elementLabel1;
   }

   public String getElementLabel2() {
      return this.mElementLabel2;
   }

   public void setElementLabel2(String elementLabel2) {
      this.mElementLabel2 = elementLabel2;
   }

   public String getElementLabel3() {
      return this.mElementLabel3;
   }

   public void setElementLabel3(String elementLabel3) {
      this.mElementLabel3 = elementLabel3;
   }

   public String getElementLabel4() {
      return this.mElementLabel4;
   }

   public void setElementLabel4(String elementLabel4) {
      this.mElementLabel4 = elementLabel4;
   }

   public String getElementLabel5() {
      return this.mElementLabel5;
   }

   public void setElementLabel5(String elementLabel5) {
      this.mElementLabel5 = elementLabel5;
   }

   public String getElementLabel6() {
      return this.mElementLabel6;
   }

   public void setElementLabel6(String elementLabel6) {
      this.mElementLabel6 = elementLabel6;
   }

   public String getElementLabel7() {
      return this.mElementLabel7;
   }

   public void setElementLabel7(String elementLabel7) {
      this.mElementLabel7 = elementLabel7;
   }

   public String getElementLabel8() {
      return this.mElementLabel8;
   }

   public void setElementLabel8(String elementLabel8) {
      this.mElementLabel8 = elementLabel8;
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

   public int[] getStructureIdArray() {
      return new int[]{this.getStructureId0(), this.getStructureId1(), this.getStructureId2(), this.getStructureId3(), this.getStructureId4(), this.getStructureId5(), this.getStructureId6(), this.getStructureId7(), this.getStructureId8()};
   }

   public int[] getStructureElementIdArray() {
      return new int[]{this.getStructureElementId0(), this.getStructureElementId1(), this.getStructureElementId2(), this.getStructureElementId3(), this.getStructureElementId4(), this.getStructureElementId5(), this.getStructureElementId6(), this.getStructureElementId7(), this.getStructureElementId8()};
   }

   public String[] getElementLabelArray() {
      return new String[]{this.getElementLabel0(), this.getElementLabel1(), this.getElementLabel2(), this.getElementLabel3(), this.getElementLabel4(), this.getElementLabel5(), this.getElementLabel6(), this.getElementLabel7(), this.getElementLabel8()};
   }
}
