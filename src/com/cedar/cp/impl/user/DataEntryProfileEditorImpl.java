// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:19
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.impl.user;

import com.cedar.cp.api.base.EntityList;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.user.DataEntryProfile;
import com.cedar.cp.api.user.DataEntryProfileEditor;
import com.cedar.cp.api.user.UserRef;
import com.cedar.cp.api.xmlform.XmlFormRef;
import com.cedar.cp.dto.user.DataEntryProfileEditorSessionSSO;
import com.cedar.cp.dto.user.DataEntryProfileImpl;
import com.cedar.cp.impl.base.BusinessEditorImpl;
import com.cedar.cp.impl.user.DataEntryProfileAdapter;
import com.cedar.cp.impl.user.DataEntryProfileEditorSessionImpl;
import com.cedar.cp.util.StringUtils;

public class DataEntryProfileEditorImpl extends BusinessEditorImpl implements DataEntryProfileEditor {

   private DataEntryProfileEditorSessionSSO mServerSessionData;
   private DataEntryProfileImpl mEditorData;
   private DataEntryProfileAdapter mEditorDataAdapter;


   public DataEntryProfileEditorImpl(DataEntryProfileEditorSessionImpl session, DataEntryProfileEditorSessionSSO serverSessionData, DataEntryProfileImpl editorData) {
      super(session);
      this.mServerSessionData = serverSessionData;
      this.mEditorData = editorData;
   }

   public void updateEditorData(DataEntryProfileEditorSessionSSO serverSessionData, DataEntryProfileImpl editorData) {
      this.mServerSessionData = serverSessionData;
      this.mEditorData = editorData;
   }

   public void setStructureIdArray(int[] p) throws ValidationException {
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

   public void setStructureElementIdArray(int[] p) throws ValidationException {
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

   public void setElementLabelArray(String[] p) throws ValidationException {
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

   public void setUserId(int newUserId) throws ValidationException {
      this.validateUserId(newUserId);
      if(this.mEditorData.getUserId() != newUserId) {
         this.setContentModified();
         this.mEditorData.setUserId(newUserId);
      }
   }

   public void setModelId(int newModelId) throws ValidationException {
      this.validateModelId(newModelId);
      if(this.mEditorData.getModelId() != newModelId) {
         this.setContentModified();
         this.mEditorData.setModelId(newModelId);
      }
   }
   
	public void setBudgetCycleId(int newBudgetCycleId) throws ValidationException {
		this.validateBudgetCycleId(newBudgetCycleId);
		if (this.mEditorData.getBudgetCycleId() != newBudgetCycleId) {
			this.setContentModified();
			this.mEditorData.setBudgetCycleId(newBudgetCycleId);
		}
	}

   public void setAutoOpenDepth(int newAutoOpenDepth) throws ValidationException {
      this.validateAutoOpenDepth(newAutoOpenDepth);
      if(this.mEditorData.getAutoOpenDepth() != newAutoOpenDepth) {
         this.setContentModified();
         this.mEditorData.setAutoOpenDepth(newAutoOpenDepth);
      }
   }

   public void setXmlformId(int newXmlformId) throws ValidationException {
      this.validateXmlformId(newXmlformId);
      if(this.mEditorData.getXmlformId() != newXmlformId) {
         this.setContentModified();
         this.mEditorData.setXmlformId(newXmlformId);
      }
   }

   public void setFillDisplayArea(boolean newFillDisplayArea) throws ValidationException {
      this.validateFillDisplayArea(newFillDisplayArea);
      if(this.mEditorData.isFillDisplayArea() != newFillDisplayArea) {
         this.setContentModified();
         this.mEditorData.setFillDisplayArea(newFillDisplayArea);
      }
   }

   public void setShowBoldSummaries(boolean newShowBoldSummaries) throws ValidationException {
      this.validateShowBoldSummaries(newShowBoldSummaries);
      if(this.mEditorData.isShowBoldSummaries() != newShowBoldSummaries) {
         this.setContentModified();
         this.mEditorData.setShowBoldSummaries(newShowBoldSummaries);
      }
   }

   public void setShowHorizontalLines(boolean newShowHorizontalLines) throws ValidationException {
      this.validateShowHorizontalLines(newShowHorizontalLines);
      if(this.mEditorData.isShowHorizontalLines() != newShowHorizontalLines) {
         this.setContentModified();
         this.mEditorData.setShowHorizontalLines(newShowHorizontalLines);
      }
   }

   public void setStructureId0(int newStructureId0) throws ValidationException {
      this.validateStructureId0(newStructureId0);
      if(this.mEditorData.getStructureId0() != newStructureId0) {
         this.setContentModified();
         this.mEditorData.setStructureId0(newStructureId0);
      }
   }

   public void setStructureId1(int newStructureId1) throws ValidationException {
      this.validateStructureId1(newStructureId1);
      if(this.mEditorData.getStructureId1() != newStructureId1) {
         this.setContentModified();
         this.mEditorData.setStructureId1(newStructureId1);
      }
   }

   public void setStructureId2(int newStructureId2) throws ValidationException {
      this.validateStructureId2(newStructureId2);
      if(this.mEditorData.getStructureId2() != newStructureId2) {
         this.setContentModified();
         this.mEditorData.setStructureId2(newStructureId2);
      }
   }

   public void setStructureId3(int newStructureId3) throws ValidationException {
      this.validateStructureId3(newStructureId3);
      if(this.mEditorData.getStructureId3() != newStructureId3) {
         this.setContentModified();
         this.mEditorData.setStructureId3(newStructureId3);
      }
   }

   public void setStructureId4(int newStructureId4) throws ValidationException {
      this.validateStructureId4(newStructureId4);
      if(this.mEditorData.getStructureId4() != newStructureId4) {
         this.setContentModified();
         this.mEditorData.setStructureId4(newStructureId4);
      }
   }

   public void setStructureId5(int newStructureId5) throws ValidationException {
      this.validateStructureId5(newStructureId5);
      if(this.mEditorData.getStructureId5() != newStructureId5) {
         this.setContentModified();
         this.mEditorData.setStructureId5(newStructureId5);
      }
   }

   public void setStructureId6(int newStructureId6) throws ValidationException {
      this.validateStructureId6(newStructureId6);
      if(this.mEditorData.getStructureId6() != newStructureId6) {
         this.setContentModified();
         this.mEditorData.setStructureId6(newStructureId6);
      }
   }

   public void setStructureId7(int newStructureId7) throws ValidationException {
      this.validateStructureId7(newStructureId7);
      if(this.mEditorData.getStructureId7() != newStructureId7) {
         this.setContentModified();
         this.mEditorData.setStructureId7(newStructureId7);
      }
   }

   public void setStructureId8(int newStructureId8) throws ValidationException {
      this.validateStructureId8(newStructureId8);
      if(this.mEditorData.getStructureId8() != newStructureId8) {
         this.setContentModified();
         this.mEditorData.setStructureId8(newStructureId8);
      }
   }

   public void setStructureElementId0(int newStructureElementId0) throws ValidationException {
      this.validateStructureElementId0(newStructureElementId0);
      if(this.mEditorData.getStructureElementId0() != newStructureElementId0) {
         this.setContentModified();
         this.mEditorData.setStructureElementId0(newStructureElementId0);
      }
   }

   public void setStructureElementId1(int newStructureElementId1) throws ValidationException {
      this.validateStructureElementId1(newStructureElementId1);
      if(this.mEditorData.getStructureElementId1() != newStructureElementId1) {
         this.setContentModified();
         this.mEditorData.setStructureElementId1(newStructureElementId1);
      }
   }

   public void setStructureElementId2(int newStructureElementId2) throws ValidationException {
      this.validateStructureElementId2(newStructureElementId2);
      if(this.mEditorData.getStructureElementId2() != newStructureElementId2) {
         this.setContentModified();
         this.mEditorData.setStructureElementId2(newStructureElementId2);
      }
   }

   public void setStructureElementId3(int newStructureElementId3) throws ValidationException {
      this.validateStructureElementId3(newStructureElementId3);
      if(this.mEditorData.getStructureElementId3() != newStructureElementId3) {
         this.setContentModified();
         this.mEditorData.setStructureElementId3(newStructureElementId3);
      }
   }

   public void setStructureElementId4(int newStructureElementId4) throws ValidationException {
      this.validateStructureElementId4(newStructureElementId4);
      if(this.mEditorData.getStructureElementId4() != newStructureElementId4) {
         this.setContentModified();
         this.mEditorData.setStructureElementId4(newStructureElementId4);
      }
   }

   public void setStructureElementId5(int newStructureElementId5) throws ValidationException {
      this.validateStructureElementId5(newStructureElementId5);
      if(this.mEditorData.getStructureElementId5() != newStructureElementId5) {
         this.setContentModified();
         this.mEditorData.setStructureElementId5(newStructureElementId5);
      }
   }

   public void setStructureElementId6(int newStructureElementId6) throws ValidationException {
      this.validateStructureElementId6(newStructureElementId6);
      if(this.mEditorData.getStructureElementId6() != newStructureElementId6) {
         this.setContentModified();
         this.mEditorData.setStructureElementId6(newStructureElementId6);
      }
   }

   public void setStructureElementId7(int newStructureElementId7) throws ValidationException {
      this.validateStructureElementId7(newStructureElementId7);
      if(this.mEditorData.getStructureElementId7() != newStructureElementId7) {
         this.setContentModified();
         this.mEditorData.setStructureElementId7(newStructureElementId7);
      }
   }

   public void setStructureElementId8(int newStructureElementId8) throws ValidationException {
      this.validateStructureElementId8(newStructureElementId8);
      if(this.mEditorData.getStructureElementId8() != newStructureElementId8) {
         this.setContentModified();
         this.mEditorData.setStructureElementId8(newStructureElementId8);
      }
   }

   public void setVisId(String newVisId) throws ValidationException {
      if(newVisId != null) {
         newVisId = StringUtils.rtrim(newVisId);
      }

      this.validateVisId(newVisId);
      if(this.mEditorData.getVisId() == null || !this.mEditorData.getVisId().equals(newVisId)) {
         this.setContentModified();
         this.mEditorData.setVisId(newVisId);
      }
   }

   public void setDescription(String newDescription) throws ValidationException {
      if(newDescription != null) {
         newDescription = StringUtils.rtrim(newDescription);
      }

      this.validateDescription(newDescription);
      if(this.mEditorData.getDescription() == null || !this.mEditorData.getDescription().equals(newDescription)) {
         this.setContentModified();
         this.mEditorData.setDescription(newDescription);
      }
   }

   public void setElementLabel0(String newElementLabel0) throws ValidationException {
      if(newElementLabel0 != null) {
         newElementLabel0 = StringUtils.rtrim(newElementLabel0);
      }

      this.validateElementLabel0(newElementLabel0);
      if(this.mEditorData.getElementLabel0() == null || !this.mEditorData.getElementLabel0().equals(newElementLabel0)) {
         this.setContentModified();
         this.mEditorData.setElementLabel0(newElementLabel0);
      }
   }

   public void setElementLabel1(String newElementLabel1) throws ValidationException {
      if(newElementLabel1 != null) {
         newElementLabel1 = StringUtils.rtrim(newElementLabel1);
      }

      this.validateElementLabel1(newElementLabel1);
      if(this.mEditorData.getElementLabel1() == null || !this.mEditorData.getElementLabel1().equals(newElementLabel1)) {
         this.setContentModified();
         this.mEditorData.setElementLabel1(newElementLabel1);
      }
   }

   public void setElementLabel2(String newElementLabel2) throws ValidationException {
      if(newElementLabel2 != null) {
         newElementLabel2 = StringUtils.rtrim(newElementLabel2);
      }

      this.validateElementLabel2(newElementLabel2);
      if(this.mEditorData.getElementLabel2() == null || !this.mEditorData.getElementLabel2().equals(newElementLabel2)) {
         this.setContentModified();
         this.mEditorData.setElementLabel2(newElementLabel2);
      }
   }

   public void setElementLabel3(String newElementLabel3) throws ValidationException {
      if(newElementLabel3 != null) {
         newElementLabel3 = StringUtils.rtrim(newElementLabel3);
      }

      this.validateElementLabel3(newElementLabel3);
      if(this.mEditorData.getElementLabel3() == null || !this.mEditorData.getElementLabel3().equals(newElementLabel3)) {
         this.setContentModified();
         this.mEditorData.setElementLabel3(newElementLabel3);
      }
   }

   public void setElementLabel4(String newElementLabel4) throws ValidationException {
      if(newElementLabel4 != null) {
         newElementLabel4 = StringUtils.rtrim(newElementLabel4);
      }

      this.validateElementLabel4(newElementLabel4);
      if(this.mEditorData.getElementLabel4() == null || !this.mEditorData.getElementLabel4().equals(newElementLabel4)) {
         this.setContentModified();
         this.mEditorData.setElementLabel4(newElementLabel4);
      }
   }

   public void setElementLabel5(String newElementLabel5) throws ValidationException {
      if(newElementLabel5 != null) {
         newElementLabel5 = StringUtils.rtrim(newElementLabel5);
      }

      this.validateElementLabel5(newElementLabel5);
      if(this.mEditorData.getElementLabel5() == null || !this.mEditorData.getElementLabel5().equals(newElementLabel5)) {
         this.setContentModified();
         this.mEditorData.setElementLabel5(newElementLabel5);
      }
   }

   public void setElementLabel6(String newElementLabel6) throws ValidationException {
      if(newElementLabel6 != null) {
         newElementLabel6 = StringUtils.rtrim(newElementLabel6);
      }

      this.validateElementLabel6(newElementLabel6);
      if(this.mEditorData.getElementLabel6() == null || !this.mEditorData.getElementLabel6().equals(newElementLabel6)) {
         this.setContentModified();
         this.mEditorData.setElementLabel6(newElementLabel6);
      }
   }

   public void setElementLabel7(String newElementLabel7) throws ValidationException {
      if(newElementLabel7 != null) {
         newElementLabel7 = StringUtils.rtrim(newElementLabel7);
      }

      this.validateElementLabel7(newElementLabel7);
      if(this.mEditorData.getElementLabel7() == null || !this.mEditorData.getElementLabel7().equals(newElementLabel7)) {
         this.setContentModified();
         this.mEditorData.setElementLabel7(newElementLabel7);
      }
   }

   public void setElementLabel8(String newElementLabel8) throws ValidationException {
      if(newElementLabel8 != null) {
         newElementLabel8 = StringUtils.rtrim(newElementLabel8);
      }

      this.validateElementLabel8(newElementLabel8);
      if(this.mEditorData.getElementLabel8() == null || !this.mEditorData.getElementLabel8().equals(newElementLabel8)) {
         this.setContentModified();
         this.mEditorData.setElementLabel8(newElementLabel8);
      }
   }

   public void setDataType(String newDataType) throws ValidationException {
      if(newDataType != null) {
         newDataType = StringUtils.rtrim(newDataType);
      }

      this.validateDataType(newDataType);
      if(this.mEditorData.getDataType() == null || !this.mEditorData.getDataType().equals(newDataType)) {
         this.setContentModified();
         this.mEditorData.setDataType(newDataType);
      }
   }

   public void validateVisId(String newVisId) throws ValidationException {
      if(newVisId != null && newVisId.length() > 120) {
         throw new ValidationException("length (" + newVisId.length() + ") of VisId must not exceed 120 on a DataEntryProfile");
      }
   }

   public void validateUserId(int newUserId) throws ValidationException {}

   public void validateModelId(int newModelId) throws ValidationException {}

   public void validateAutoOpenDepth(int newAutoOpenDepth) throws ValidationException {}

   public void validateDescription(String newDescription) throws ValidationException {
      if(newDescription != null && newDescription.length() > 128) {
         throw new ValidationException("length (" + newDescription.length() + ") of Description must not exceed 128 on a DataEntryProfile");
      }
   }

   public void validateXmlformId(int newXmlformId) throws ValidationException {}
   
   public void validateBudgetCycleId(int newBudgetCycleId) throws ValidationException {}

   public void validateFillDisplayArea(boolean newFillDisplayArea) throws ValidationException {}

   public void validateShowBoldSummaries(boolean newShowBoldSummaries) throws ValidationException {}

   public void validateShowHorizontalLines(boolean newShowHorizontalLines) throws ValidationException {}

   public void validateStructureId0(int newStructureId0) throws ValidationException {}

   public void validateStructureId1(int newStructureId1) throws ValidationException {}

   public void validateStructureId2(int newStructureId2) throws ValidationException {}

   public void validateStructureId3(int newStructureId3) throws ValidationException {}

   public void validateStructureId4(int newStructureId4) throws ValidationException {}

   public void validateStructureId5(int newStructureId5) throws ValidationException {}

   public void validateStructureId6(int newStructureId6) throws ValidationException {}

   public void validateStructureId7(int newStructureId7) throws ValidationException {}

   public void validateStructureId8(int newStructureId8) throws ValidationException {}

   public void validateStructureElementId0(int newStructureElementId0) throws ValidationException {}

   public void validateStructureElementId1(int newStructureElementId1) throws ValidationException {}

   public void validateStructureElementId2(int newStructureElementId2) throws ValidationException {}

   public void validateStructureElementId3(int newStructureElementId3) throws ValidationException {}

   public void validateStructureElementId4(int newStructureElementId4) throws ValidationException {}

   public void validateStructureElementId5(int newStructureElementId5) throws ValidationException {}

   public void validateStructureElementId6(int newStructureElementId6) throws ValidationException {}

   public void validateStructureElementId7(int newStructureElementId7) throws ValidationException {}

   public void validateStructureElementId8(int newStructureElementId8) throws ValidationException {}

   public void validateElementLabel0(String newElementLabel0) throws ValidationException {
      if(newElementLabel0 != null && newElementLabel0.length() > 255) {
         throw new ValidationException("length (" + newElementLabel0.length() + ") of ElementLabel0 must not exceed 255 on a DataEntryProfile");
      }
   }

   public void validateElementLabel1(String newElementLabel1) throws ValidationException {
      if(newElementLabel1 != null && newElementLabel1.length() > 255) {
         throw new ValidationException("length (" + newElementLabel1.length() + ") of ElementLabel1 must not exceed 255 on a DataEntryProfile");
      }
   }

   public void validateElementLabel2(String newElementLabel2) throws ValidationException {
      if(newElementLabel2 != null && newElementLabel2.length() > 255) {
         throw new ValidationException("length (" + newElementLabel2.length() + ") of ElementLabel2 must not exceed 255 on a DataEntryProfile");
      }
   }

   public void validateElementLabel3(String newElementLabel3) throws ValidationException {
      if(newElementLabel3 != null && newElementLabel3.length() > 255) {
         throw new ValidationException("length (" + newElementLabel3.length() + ") of ElementLabel3 must not exceed 255 on a DataEntryProfile");
      }
   }

   public void validateElementLabel4(String newElementLabel4) throws ValidationException {
      if(newElementLabel4 != null && newElementLabel4.length() > 255) {
         throw new ValidationException("length (" + newElementLabel4.length() + ") of ElementLabel4 must not exceed 255 on a DataEntryProfile");
      }
   }

   public void validateElementLabel5(String newElementLabel5) throws ValidationException {
      if(newElementLabel5 != null && newElementLabel5.length() > 255) {
         throw new ValidationException("length (" + newElementLabel5.length() + ") of ElementLabel5 must not exceed 255 on a DataEntryProfile");
      }
   }

   public void validateElementLabel6(String newElementLabel6) throws ValidationException {
      if(newElementLabel6 != null && newElementLabel6.length() > 255) {
         throw new ValidationException("length (" + newElementLabel6.length() + ") of ElementLabel6 must not exceed 255 on a DataEntryProfile");
      }
   }

   public void validateElementLabel7(String newElementLabel7) throws ValidationException {
      if(newElementLabel7 != null && newElementLabel7.length() > 255) {
         throw new ValidationException("length (" + newElementLabel7.length() + ") of ElementLabel7 must not exceed 255 on a DataEntryProfile");
      }
   }

   public void validateElementLabel8(String newElementLabel8) throws ValidationException {
      if(newElementLabel8 != null && newElementLabel8.length() > 255) {
         throw new ValidationException("length (" + newElementLabel8.length() + ") of ElementLabel8 must not exceed 255 on a DataEntryProfile");
      }
   }

   public void validateDataType(String newDataType) throws ValidationException {
      if(newDataType != null && newDataType.length() > 2) {
         throw new ValidationException("length (" + newDataType.length() + ") of DataType must not exceed 2 on a DataEntryProfile");
      }
   }

   public void setUserRef(UserRef ref) throws ValidationException {
      UserRef actualRef = ref;
      if(ref != null) {
         try {
            actualRef = this.getConnection().getListHelper().getUserEntityRef(ref);
         } catch (Exception var4) {
            throw new ValidationException(var4.getMessage());
         }
      }

      if(this.mEditorData.getUserRef() == null) {
         if(actualRef == null) {
            return;
         }
      } else if(actualRef != null && this.mEditorData.getUserRef().getPrimaryKey().equals(actualRef.getPrimaryKey())) {
         return;
      }

      this.mEditorData.setUserRef(actualRef);
      this.setContentModified();
   }

   public void setXmlFormRef(XmlFormRef ref) throws ValidationException {
      XmlFormRef actualRef = ref;
      if(ref != null) {
         try {
            actualRef = this.getConnection().getListHelper().getXmlFormEntityRef(ref);
         } catch (Exception var4) {
            throw new ValidationException(var4.getMessage());
         }
      }

      if(this.mEditorData.getXmlFormRef() == null) {
         if(actualRef == null) {
            return;
         }
      } else if(actualRef != null && this.mEditorData.getXmlFormRef().getPrimaryKey().equals(actualRef.getPrimaryKey())) {
         return;
      }

      this.mEditorData.setXmlFormRef(actualRef);
      this.setContentModified();
   }

   public EntityList getOwnershipRefs() {
      return ((DataEntryProfileEditorSessionImpl)this.getBusinessSession()).getOwnershipRefs();
   }

   public DataEntryProfile getDataEntryProfile() {
      if(this.mEditorDataAdapter == null) {
         this.mEditorDataAdapter = new DataEntryProfileAdapter((DataEntryProfileEditorSessionImpl)this.getBusinessSession(), this.mEditorData);
      }

      return this.mEditorDataAdapter;
   }

   public void saveModifications() throws ValidationException {
      this.saveValidation();
   }

   private void saveValidation() throws ValidationException {}
}
