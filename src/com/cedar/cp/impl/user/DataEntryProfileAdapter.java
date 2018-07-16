// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:19
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.impl.user;

import com.cedar.cp.api.user.DataEntryProfile;
import com.cedar.cp.api.user.UserRef;
import com.cedar.cp.api.xmlform.XmlFormRef;
import com.cedar.cp.dto.user.DataEntryProfileImpl;
import com.cedar.cp.dto.user.DataEntryProfilePK;
import com.cedar.cp.impl.base.BusinessProcessImpl;
import com.cedar.cp.impl.user.DataEntryProfileEditorSessionImpl;

public class DataEntryProfileAdapter implements DataEntryProfile {

   private DataEntryProfileImpl mEditorData;
   private DataEntryProfileEditorSessionImpl mEditorSessionImpl;


   public DataEntryProfileAdapter(DataEntryProfileEditorSessionImpl e, DataEntryProfileImpl editorData) {
      this.mEditorData = editorData;
      this.mEditorSessionImpl = e;
   }

   public void setPrimaryKey(Object key) {
      this.mEditorData.setPrimaryKey(key);
   }

   protected DataEntryProfileEditorSessionImpl getEditorSessionImpl() {
      return this.mEditorSessionImpl;
   }

   protected DataEntryProfileImpl getEditorData() {
      return this.mEditorData;
   }

   public Object getPrimaryKey() {
      return this.mEditorData.getPrimaryKey();
   }

   void setPrimaryKey(DataEntryProfilePK paramKey) {
      this.mEditorData.setPrimaryKey(paramKey);
   }

   public String getVisId() {
      return this.mEditorData.getVisId();
   }

   public int getUserId() {
      return this.mEditorData.getUserId();
   }

   public int getModelId() {
      return this.mEditorData.getModelId();
   }
   
   public int getBudgetCycleId() {
	  return this.mEditorData.getBudgetCycleId();
   }

   public int getAutoOpenDepth() {
      return this.mEditorData.getAutoOpenDepth();
   }

   public String getDescription() {
      return this.mEditorData.getDescription();
   }

   public int getXmlformId() {
      return this.mEditorData.getXmlformId();
   }

   public boolean isFillDisplayArea() {
      return this.mEditorData.isFillDisplayArea();
   }

   public boolean isShowBoldSummaries() {
      return this.mEditorData.isShowBoldSummaries();
   }

   public boolean isShowHorizontalLines() {
      return this.mEditorData.isShowHorizontalLines();
   }

   public int[] getStructureIdArray() {
      return this.mEditorData.getStructureIdArray();
   }

   public void setStructureIdArray(int[] p) {
      this.mEditorData.setStructureIdArray(p);
   }

   public int getStructureId0() {
      return this.mEditorData.getStructureId0();
   }

   public int getStructureId1() {
      return this.mEditorData.getStructureId1();
   }

   public int getStructureId2() {
      return this.mEditorData.getStructureId2();
   }

   public int getStructureId3() {
      return this.mEditorData.getStructureId3();
   }

   public int getStructureId4() {
      return this.mEditorData.getStructureId4();
   }

   public int getStructureId5() {
      return this.mEditorData.getStructureId5();
   }

   public int getStructureId6() {
      return this.mEditorData.getStructureId6();
   }

   public int getStructureId7() {
      return this.mEditorData.getStructureId7();
   }

   public int getStructureId8() {
      return this.mEditorData.getStructureId8();
   }

   public int[] getStructureElementIdArray() {
      return this.mEditorData.getStructureElementIdArray();
   }

   public void setStructureElementIdArray(int[] p) {
      this.mEditorData.setStructureElementIdArray(p);
   }

   public int getStructureElementId0() {
      return this.mEditorData.getStructureElementId0();
   }

   public int getStructureElementId1() {
      return this.mEditorData.getStructureElementId1();
   }

   public int getStructureElementId2() {
      return this.mEditorData.getStructureElementId2();
   }

   public int getStructureElementId3() {
      return this.mEditorData.getStructureElementId3();
   }

   public int getStructureElementId4() {
      return this.mEditorData.getStructureElementId4();
   }

   public int getStructureElementId5() {
      return this.mEditorData.getStructureElementId5();
   }

   public int getStructureElementId6() {
      return this.mEditorData.getStructureElementId6();
   }

   public int getStructureElementId7() {
      return this.mEditorData.getStructureElementId7();
   }

   public int getStructureElementId8() {
      return this.mEditorData.getStructureElementId8();
   }

   public String[] getElementLabelArray() {
      return this.mEditorData.getElementLabelArray();
   }

   public void setElementLabelArray(String[] p) {
      this.mEditorData.setElementLabelArray(p);
   }

   public String getElementLabel0() {
      return this.mEditorData.getElementLabel0();
   }

   public String getElementLabel1() {
      return this.mEditorData.getElementLabel1();
   }

   public String getElementLabel2() {
      return this.mEditorData.getElementLabel2();
   }

   public String getElementLabel3() {
      return this.mEditorData.getElementLabel3();
   }

   public String getElementLabel4() {
      return this.mEditorData.getElementLabel4();
   }

   public String getElementLabel5() {
      return this.mEditorData.getElementLabel5();
   }

   public String getElementLabel6() {
      return this.mEditorData.getElementLabel6();
   }

   public String getElementLabel7() {
      return this.mEditorData.getElementLabel7();
   }

   public String getElementLabel8() {
      return this.mEditorData.getElementLabel8();
   }

   public String getDataType() {
      return this.mEditorData.getDataType();
   }

   public UserRef getUserRef() {
      if(this.mEditorData.getUserRef() != null) {
         if(this.mEditorData.getUserRef().getNarrative() != null && this.mEditorData.getUserRef().getNarrative().length() > 0) {
            return this.mEditorData.getUserRef();
         } else {
            try {
               UserRef e = ((BusinessProcessImpl)this.getEditorSessionImpl().getBusinessProcess()).getConnection().getListHelper().getUserEntityRef(this.mEditorData.getUserRef());
               this.mEditorData.setUserRef(e);
               return e;
            } catch (Exception var2) {
               throw new RuntimeException(var2.getMessage());
            }
         }
      } else {
         return null;
      }
   }

   public XmlFormRef getXmlFormRef() {
      if(this.mEditorData.getXmlFormRef() != null) {
         try {
            XmlFormRef e = ((BusinessProcessImpl)this.getEditorSessionImpl().getBusinessProcess()).getConnection().getListHelper().getXmlFormEntityRef(this.mEditorData.getXmlFormRef());
            this.mEditorData.setXmlFormRef(e);
            return e;
         } catch (Exception var2) {
            var2.printStackTrace();
            throw new RuntimeException(var2.getMessage());
         }
      } else {
         return null;
      }
   }

   public void setUserRef(UserRef ref) {
      this.mEditorData.setUserRef(ref);
   }

   public void setXmlFormRef(XmlFormRef ref) {
      this.mEditorData.setXmlFormRef(ref);
   }

   public void setVisId(String p) {
      this.mEditorData.setVisId(p);
   }

   public void setUserId(int p) {
      this.mEditorData.setUserId(p);
   }

   public void setModelId(int p) {
      this.mEditorData.setModelId(p);
   }
   
	public void setBudgetCycleId(int p) {
		this.mEditorData.setBudgetCycleId(p);
	}

   public void setAutoOpenDepth(int p) {
      this.mEditorData.setAutoOpenDepth(p);
   }

   public void setDescription(String p) {
      this.mEditorData.setDescription(p);
   }

   public void setXmlformId(int p) {
      this.mEditorData.setXmlformId(p);
   }

   public void setFillDisplayArea(boolean p) {
      this.mEditorData.setFillDisplayArea(p);
   }

   public void setShowBoldSummaries(boolean p) {
      this.mEditorData.setShowBoldSummaries(p);
   }

   public void setShowHorizontalLines(boolean p) {
      this.mEditorData.setShowHorizontalLines(p);
   }

   public void setStructureId0(int p) {
      this.mEditorData.setStructureId0(p);
   }

   public void setStructureId1(int p) {
      this.mEditorData.setStructureId1(p);
   }

   public void setStructureId2(int p) {
      this.mEditorData.setStructureId2(p);
   }

   public void setStructureId3(int p) {
      this.mEditorData.setStructureId3(p);
   }

   public void setStructureId4(int p) {
      this.mEditorData.setStructureId4(p);
   }

   public void setStructureId5(int p) {
      this.mEditorData.setStructureId5(p);
   }

   public void setStructureId6(int p) {
      this.mEditorData.setStructureId6(p);
   }

   public void setStructureId7(int p) {
      this.mEditorData.setStructureId7(p);
   }

   public void setStructureId8(int p) {
      this.mEditorData.setStructureId8(p);
   }

   public void setStructureElementId0(int p) {
      this.mEditorData.setStructureElementId0(p);
   }

   public void setStructureElementId1(int p) {
      this.mEditorData.setStructureElementId1(p);
   }

   public void setStructureElementId2(int p) {
      this.mEditorData.setStructureElementId2(p);
   }

   public void setStructureElementId3(int p) {
      this.mEditorData.setStructureElementId3(p);
   }

   public void setStructureElementId4(int p) {
      this.mEditorData.setStructureElementId4(p);
   }

   public void setStructureElementId5(int p) {
      this.mEditorData.setStructureElementId5(p);
   }

   public void setStructureElementId6(int p) {
      this.mEditorData.setStructureElementId6(p);
   }

   public void setStructureElementId7(int p) {
      this.mEditorData.setStructureElementId7(p);
   }

   public void setStructureElementId8(int p) {
      this.mEditorData.setStructureElementId8(p);
   }

   public void setElementLabel0(String p) {
      this.mEditorData.setElementLabel0(p);
   }

   public void setElementLabel1(String p) {
      this.mEditorData.setElementLabel1(p);
   }

   public void setElementLabel2(String p) {
      this.mEditorData.setElementLabel2(p);
   }

   public void setElementLabel3(String p) {
      this.mEditorData.setElementLabel3(p);
   }

   public void setElementLabel4(String p) {
      this.mEditorData.setElementLabel4(p);
   }

   public void setElementLabel5(String p) {
      this.mEditorData.setElementLabel5(p);
   }

   public void setElementLabel6(String p) {
      this.mEditorData.setElementLabel6(p);
   }

   public void setElementLabel7(String p) {
      this.mEditorData.setElementLabel7(p);
   }

   public void setElementLabel8(String p) {
      this.mEditorData.setElementLabel8(p);
   }

   public void setDataType(String p) {
      this.mEditorData.setDataType(p);
   }
}
