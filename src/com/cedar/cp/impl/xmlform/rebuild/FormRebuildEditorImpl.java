// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:20
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.impl.xmlform.rebuild;

import com.cedar.cp.api.base.EntityList;
import com.cedar.cp.api.base.EntityRef;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.datatype.DataTypeRef;
import com.cedar.cp.api.dimension.StructureElementRef;
import com.cedar.cp.api.model.BudgetCycleRef;
import com.cedar.cp.api.model.ModelRef;
import com.cedar.cp.api.xmlform.XmlFormRef;
import com.cedar.cp.api.xmlform.rebuild.FormRebuild;
import com.cedar.cp.api.xmlform.rebuild.FormRebuildEditor;
import com.cedar.cp.dto.dimension.StructureElementPK;
import com.cedar.cp.dto.model.BudgetCycleCK;
import com.cedar.cp.dto.xmlform.XmlFormPK;
import com.cedar.cp.dto.xmlform.rebuild.FormRebuildEditorSessionSSO;
import com.cedar.cp.dto.xmlform.rebuild.FormRebuildImpl;
import com.cedar.cp.impl.base.BusinessEditorImpl;
import com.cedar.cp.impl.xmlform.rebuild.FormRebuildAdapter;
import com.cedar.cp.impl.xmlform.rebuild.FormRebuildEditorSessionImpl;
import com.cedar.cp.util.StringUtils;
import java.lang.reflect.Method;
import java.sql.Timestamp;

public class FormRebuildEditorImpl extends BusinessEditorImpl implements FormRebuildEditor {

   private FormRebuildEditorSessionSSO mServerSessionData;
   private FormRebuildImpl mEditorData;
   private FormRebuildAdapter mEditorDataAdapter;


   public FormRebuildEditorImpl(FormRebuildEditorSessionImpl session, FormRebuildEditorSessionSSO serverSessionData, FormRebuildImpl editorData) {
      super(session);
      this.mServerSessionData = serverSessionData;
      this.mEditorData = editorData;
   }

   public void updateEditorData(FormRebuildEditorSessionSSO serverSessionData, FormRebuildImpl editorData) {
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

   public void setXmlformId(int newXmlformId) throws ValidationException {
      this.validateXmlformId(newXmlformId);
      if(this.mEditorData.getXmlformId() != newXmlformId) {
         this.setContentModified();
         this.mEditorData.setXmlformId(newXmlformId);
      }
   }

   public void setBudgetCycleId(int newBudgetCycleId) throws ValidationException {
      this.validateBudgetCycleId(newBudgetCycleId);
      if(this.mEditorData.getBudgetCycleId() != newBudgetCycleId) {
         this.setContentModified();
         this.mEditorData.setBudgetCycleId(newBudgetCycleId);
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

   public void setLastSubmit(Timestamp newLastSubmit) throws ValidationException {
      this.validateLastSubmit(newLastSubmit);
      if(this.mEditorData.getLastSubmit() == null || !this.mEditorData.getLastSubmit().equals(newLastSubmit)) {
         this.setContentModified();
         this.mEditorData.setLastSubmit(newLastSubmit);
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
      if(newVisId != null && newVisId.length() > 20) {
         throw new ValidationException("length (" + newVisId.length() + ") of VisId must not exceed 20 on a FormRebuild");
      }
   }

   public void validateDescription(String newDescription) throws ValidationException {
      if(newDescription != null && newDescription.length() > 128) {
         throw new ValidationException("length (" + newDescription.length() + ") of Description must not exceed 128 on a FormRebuild");
      }
   }

   public void validateLastSubmit(Timestamp newLastSubmit) throws ValidationException {}

   public void validateXmlformId(int newXmlformId) throws ValidationException {}

   public void validateBudgetCycleId(int newBudgetCycleId) throws ValidationException {}

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

   public void validateDataType(String newDataType) throws ValidationException {
      if(newDataType != null && newDataType.length() > 2) {
         throw new ValidationException("length (" + newDataType.length() + ") of DataType must not exceed 2 on a FormRebuild");
      }
   }

   public void setModelRef(ModelRef ref) throws ValidationException {
      ModelRef actualRef = ref;
      if(ref != null) {
         try {
            actualRef = this.getConnection().getListHelper().getModelEntityRef(ref);
         } catch (Exception var4) {
            throw new ValidationException(var4.getMessage());
         }
      }

      if(this.mEditorData.getModelRef() == null) {
         if(actualRef == null) {
            return;
         }
      } else if(actualRef != null && this.mEditorData.getModelRef().getPrimaryKey().equals(actualRef.getPrimaryKey())) {
         return;
      }

      this.mEditorData.setModelRef(actualRef);
      this.setContentModified();
   }

   public EntityList getOwnershipRefs() {
      return ((FormRebuildEditorSessionImpl)this.getBusinessSession()).getOwnershipRefs();
   }

   public FormRebuild getFormRebuild() {
      if(this.mEditorDataAdapter == null) {
         this.mEditorDataAdapter = new FormRebuildAdapter((FormRebuildEditorSessionImpl)this.getBusinessSession(), this.mEditorData);
      }

      return this.mEditorDataAdapter;
   }

   public void saveModifications() throws ValidationException {
      this.saveValidation();
   }

   private void saveValidation() throws ValidationException {}

   public void setBudgetCycle(EntityRef ref) {
      BudgetCycleCK ck = (BudgetCycleCK)ref.getPrimaryKey();
      this.mEditorData.setBudgetCycleRef((BudgetCycleRef)ref);
      this.mEditorData.setBudgetCycleId(ck.getBudgetCyclePK().getBudgetCycleId());
      this.setContentModified();
   }

   public void setXmlForm(EntityRef ref) {
      XmlFormPK pk = (XmlFormPK)ref.getPrimaryKey();
      this.mEditorData.setXmlFormRef((XmlFormRef)ref);
      this.mEditorData.setXmlformId(pk.getXmlFormId());
      this.setContentModified();
   }

   public void setStructures(EntityRef[] refs) {
      this.mEditorData.setSelection(refs);
      int size = refs.length;

      for(int i = 0; i < size; ++i) {
         if(i == size - 1) {
            DataTypeRef sRef = (DataTypeRef)refs[i];
            this.mEditorData.setDataType(sRef.getNarrative());
         } else {
            StructureElementRef var12 = (StructureElementRef)refs[i];
            StructureElementPK sPk = (StructureElementPK)var12.getPrimaryKey();
            String structureMethodName = "setStructureId" + i;
            String structureElementMethodName = "setStructureElementId" + i;

            try {
               Class e = this.mEditorData.getClass();
               Method set1 = e.getMethod(structureMethodName, new Class[]{Integer.TYPE});
               Method set2 = e.getMethod(structureElementMethodName, new Class[]{Integer.TYPE});
               set1.invoke(this.mEditorData, new Object[]{Integer.valueOf(sPk.getStructureId())});
               set2.invoke(this.mEditorData, new Object[]{Integer.valueOf(sPk.getStructureElementId())});
            } catch (Exception var11) {
               var11.printStackTrace();
            }
         }
      }

   }
}
