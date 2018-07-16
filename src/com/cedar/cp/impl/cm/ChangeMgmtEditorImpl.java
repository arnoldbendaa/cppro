// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:10
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.impl.cm;

import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.cm.ChangeMgmt;
import com.cedar.cp.api.cm.ChangeMgmtEditor;
import com.cedar.cp.api.model.ModelRef;
import com.cedar.cp.dto.cm.ChangeMgmtEditorSessionSSO;
import com.cedar.cp.dto.cm.ChangeMgmtImpl;
import com.cedar.cp.impl.base.BusinessEditorImpl;
import com.cedar.cp.impl.cm.ChangeMgmtAdapter;
import com.cedar.cp.impl.cm.ChangeMgmtEditorSessionImpl;
import com.cedar.cp.util.StringUtils;
import java.sql.Timestamp;

public class ChangeMgmtEditorImpl extends BusinessEditorImpl implements ChangeMgmtEditor {

   private ChangeMgmtEditorSessionSSO mServerSessionData;
   private ChangeMgmtImpl mEditorData;
   private ChangeMgmtAdapter mEditorDataAdapter;


   public ChangeMgmtEditorImpl(ChangeMgmtEditorSessionImpl session, ChangeMgmtEditorSessionSSO serverSessionData, ChangeMgmtImpl editorData) {
      super(session);
      this.mServerSessionData = serverSessionData;
      this.mEditorData = editorData;
   }

   public void updateEditorData(ChangeMgmtEditorSessionSSO serverSessionData, ChangeMgmtImpl editorData) {
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

   public void setTaskId(int newTaskId) throws ValidationException {
      this.validateTaskId(newTaskId);
      if(this.mEditorData.getTaskId() != newTaskId) {
         this.setContentModified();
         this.mEditorData.setTaskId(newTaskId);
      }
   }

   public void setCreatedTime(Timestamp newCreatedTime) throws ValidationException {
      this.validateCreatedTime(newCreatedTime);
      if(this.mEditorData.getCreatedTime() == null || !this.mEditorData.getCreatedTime().equals(newCreatedTime)) {
         this.setContentModified();
         this.mEditorData.setCreatedTime(newCreatedTime);
      }
   }

   public void setSourceSystem(String newSourceSystem) throws ValidationException {
      if(newSourceSystem != null) {
         newSourceSystem = StringUtils.rtrim(newSourceSystem);
      }

      this.validateSourceSystem(newSourceSystem);
      if(this.mEditorData.getSourceSystem() == null || !this.mEditorData.getSourceSystem().equals(newSourceSystem)) {
         this.setContentModified();
         this.mEditorData.setSourceSystem(newSourceSystem);
      }
   }

   public void setXmlText(String newXmlText) throws ValidationException {
      if(newXmlText != null) {
         newXmlText = StringUtils.rtrim(newXmlText);
      }

      this.validateXmlText(newXmlText);
      if(this.mEditorData.getXmlText() == null || !this.mEditorData.getXmlText().equals(newXmlText)) {
         this.setContentModified();
         this.mEditorData.setXmlText(newXmlText);
      }
   }

   public void validateModelId(int newModelId) throws ValidationException {}

   public void validateCreatedTime(Timestamp newCreatedTime) throws ValidationException {}

   public void validateTaskId(int newTaskId) throws ValidationException {}

   public void validateSourceSystem(String newSourceSystem) throws ValidationException {
      if(newSourceSystem != null && newSourceSystem.length() > 256) {
         throw new ValidationException("length (" + newSourceSystem.length() + ") of SourceSystem must not exceed 256 on a ChangeMgmt");
      }
   }

   public void validateXmlText(String newXmlText) throws ValidationException {}

   public void setRelatedModelRef(ModelRef ref) throws ValidationException {
      ModelRef actualRef = ref;
      if(ref != null) {
         try {
            actualRef = this.getConnection().getListHelper().getModelEntityRef(ref);
         } catch (Exception var4) {
            throw new ValidationException(var4.getMessage());
         }
      }

      if(this.mEditorData.getRelatedModelRef() == null) {
         if(actualRef == null) {
            return;
         }
      } else if(actualRef != null && this.mEditorData.getRelatedModelRef().getPrimaryKey().equals(actualRef.getPrimaryKey())) {
         return;
      }

      this.mEditorData.setRelatedModelRef(actualRef);
      this.setContentModified();
   }

   public ChangeMgmt getChangeMgmt() {
      if(this.mEditorDataAdapter == null) {
         this.mEditorDataAdapter = new ChangeMgmtAdapter((ChangeMgmtEditorSessionImpl)this.getBusinessSession(), this.mEditorData);
      }

      return this.mEditorDataAdapter;
   }

   public void saveModifications() throws ValidationException {
      this.saveValidation();
   }

   private void saveValidation() throws ValidationException {}
}
