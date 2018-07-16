// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:12
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.impl.model;

import com.cedar.cp.api.base.EntityList;
import com.cedar.cp.api.base.EntityRef;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.model.CellCalc;
import com.cedar.cp.api.model.CellCalcEditor;
import com.cedar.cp.api.model.ModelRef;
import com.cedar.cp.dto.model.CellCalcEditorSessionSSO;
import com.cedar.cp.dto.model.CellCalcImpl;
import com.cedar.cp.dto.model.ModelPK;
import com.cedar.cp.impl.base.BusinessEditorImpl;
import com.cedar.cp.impl.model.CellCalcAdapter;
import com.cedar.cp.impl.model.CellCalcEditorSessionImpl;
import com.cedar.cp.util.StringUtils;

public class CellCalcEditorImpl extends BusinessEditorImpl implements CellCalcEditor {

   private CellCalcEditorSessionSSO mServerSessionData;
   private CellCalcImpl mEditorData;
   private CellCalcAdapter mEditorDataAdapter;


   public CellCalcEditorImpl(CellCalcEditorSessionImpl session, CellCalcEditorSessionSSO serverSessionData, CellCalcImpl editorData) {
      super(session);
      this.mServerSessionData = serverSessionData;
      this.mEditorData = editorData;
   }

   public void updateEditorData(CellCalcEditorSessionSSO serverSessionData, CellCalcImpl editorData) {
      this.mServerSessionData = serverSessionData;
      this.mEditorData = editorData;
   }

   public void setModelId(int newModelId) throws ValidationException {
      this.validateModelId(newModelId);
      if(this.mEditorData.getModelId() != newModelId) {
         this.setContentModified();
         this.mEditorData.setModelId(newModelId);
      }
   }

   public void setXmlformId(int newXmlformId) throws ValidationException {
      this.validateXmlformId(newXmlformId);
      if(this.mEditorData.getXmlformId() != newXmlformId) {
         this.setContentModified();
         this.mEditorData.setXmlformId(newXmlformId);
      }
   }

   public void setAccessDefinitionId(int newAccessDefinitionId) throws ValidationException {
      this.validateAccessDefinitionId(newAccessDefinitionId);
      if(this.mEditorData.getAccessDefinitionId() != newAccessDefinitionId) {
         this.setContentModified();
         this.mEditorData.setAccessDefinitionId(newAccessDefinitionId);
      }
   }

   public void setDataTypeId(int newDataTypeId) throws ValidationException {
      this.validateDataTypeId(newDataTypeId);
      if(this.mEditorData.getDataTypeId() != newDataTypeId) {
         this.setContentModified();
         this.mEditorData.setDataTypeId(newDataTypeId);
      }
   }

   public void setSummaryPeriodAssociation(boolean newSummaryPeriodAssociation) throws ValidationException {
      this.validateSummaryPeriodAssociation(newSummaryPeriodAssociation);
      if(this.mEditorData.isSummaryPeriodAssociation() != newSummaryPeriodAssociation) {
         this.setContentModified();
         this.mEditorData.setSummaryPeriodAssociation(newSummaryPeriodAssociation);
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

   public void validateModelId(int newModelId) throws ValidationException {}

   public void validateVisId(String newVisId) throws ValidationException {
      if(newVisId != null && newVisId.length() > 20) {
         throw new ValidationException("length (" + newVisId.length() + ") of VisId must not exceed 20 on a CellCalc");
      }
   }

   public void validateDescription(String newDescription) throws ValidationException {
      if(newDescription != null && newDescription.length() > 128) {
         throw new ValidationException("length (" + newDescription.length() + ") of Description must not exceed 128 on a CellCalc");
      }
   }

   public void validateXmlformId(int newXmlformId) throws ValidationException {}

   public void validateAccessDefinitionId(int newAccessDefinitionId) throws ValidationException {}

   public void validateDataTypeId(int newDataTypeId) throws ValidationException {}

   public void validateSummaryPeriodAssociation(boolean newSummaryPeriodAssociation) throws ValidationException {}

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
      this.mEditorData.setModelId(((ModelPK)actualRef.getPrimaryKey()).getModelId());
   }

   public EntityList getOwnershipRefs() {
      return ((CellCalcEditorSessionImpl)this.getBusinessSession()).getOwnershipRefs();
   }

   public CellCalc getCellCalc() {
      if(this.mEditorDataAdapter == null) {
         this.mEditorDataAdapter = new CellCalcAdapter((CellCalcEditorSessionImpl)this.getBusinessSession(), this.mEditorData);
      }

      return this.mEditorDataAdapter;
   }

   public void saveModifications() throws ValidationException {
      this.saveValidation();
   }

   private void saveValidation() throws ValidationException {}

   public void addAccountElementAssociation(EntityRef ref, String id) {
      this.mEditorData.addTableData(ref, id);
      this.setContentModified();
   }

   public void removeAccountElementAssociation(EntityRef ref) {
      this.mEditorData.removeTableData(ref);
      this.setContentModified();
   }
}
