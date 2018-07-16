// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:18
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.impl.systemproperty;

import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.systemproperty.SystemProperty;
import com.cedar.cp.api.systemproperty.SystemPropertyEditor;
import com.cedar.cp.dto.systemproperty.SystemPropertyEditorSessionSSO;
import com.cedar.cp.dto.systemproperty.SystemPropertyImpl;
import com.cedar.cp.impl.base.BusinessEditorImpl;
import com.cedar.cp.impl.systemproperty.SystemPropertyAdapter;
import com.cedar.cp.impl.systemproperty.SystemPropertyEditorSessionImpl;
import com.cedar.cp.util.StringUtils;

public class SystemPropertyEditorImpl extends BusinessEditorImpl implements SystemPropertyEditor {

   private SystemPropertyEditorSessionSSO mServerSessionData;
   private SystemPropertyImpl mEditorData;
   private SystemPropertyAdapter mEditorDataAdapter;


   public SystemPropertyEditorImpl(SystemPropertyEditorSessionImpl session, SystemPropertyEditorSessionSSO serverSessionData, SystemPropertyImpl editorData) {
      super(session);
      this.mServerSessionData = serverSessionData;
      this.mEditorData = editorData;
   }

   public void updateEditorData(SystemPropertyEditorSessionSSO serverSessionData, SystemPropertyImpl editorData) {
      this.mServerSessionData = serverSessionData;
      this.mEditorData = editorData;
   }

   public void setProperty(String newProperty) throws ValidationException {
      if(newProperty != null) {
         newProperty = StringUtils.rtrim(newProperty);
      }

      this.validateProperty(newProperty);
      if(this.mEditorData.getProperty() == null || !this.mEditorData.getProperty().equals(newProperty)) {
         this.setContentModified();
         this.mEditorData.setProperty(newProperty);
      }
   }

   public void setValue(String newValue) throws ValidationException {
      if(newValue != null) {
         newValue = StringUtils.rtrim(newValue);
      }

      this.validateValue(newValue);
      if(this.mEditorData.getValue() == null || !this.mEditorData.getValue().equals(newValue)) {
         this.setContentModified();
         this.mEditorData.setValue(newValue);
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

   public void setValidateExp(String newValidateExp) throws ValidationException {
      if(newValidateExp != null) {
         newValidateExp = StringUtils.rtrim(newValidateExp);
      }

      this.validateValidateExp(newValidateExp);
      if(this.mEditorData.getValidateExp() == null || !this.mEditorData.getValidateExp().equals(newValidateExp)) {
         this.setContentModified();
         this.mEditorData.setValidateExp(newValidateExp);
      }
   }

   public void setValidateTxt(String newValidateTxt) throws ValidationException {
      if(newValidateTxt != null) {
         newValidateTxt = StringUtils.rtrim(newValidateTxt);
      }

      this.validateValidateTxt(newValidateTxt);
      if(this.mEditorData.getValidateTxt() == null || !this.mEditorData.getValidateTxt().equals(newValidateTxt)) {
         this.setContentModified();
         this.mEditorData.setValidateTxt(newValidateTxt);
      }
   }

   public void validateProperty(String newProperty) throws ValidationException {
      if(newProperty != null && newProperty.length() > 128) {
         throw new ValidationException("length (" + newProperty.length() + ") of Property must not exceed 128 on a SystemProperty");
      }
   }

   public void validateValue(String newValue) throws ValidationException {
      if(newValue != null && newValue.length() > 128) {
         throw new ValidationException("length (" + newValue.length() + ") of Value must not exceed 128 on a SystemProperty");
      }
   }

   public void validateDescription(String newDescription) throws ValidationException {
      if(newDescription != null && newDescription.length() > 256) {
         throw new ValidationException("length (" + newDescription.length() + ") of Description must not exceed 256 on a SystemProperty");
      }
   }

   public void validateValidateExp(String newValidateExp) throws ValidationException {
      if(newValidateExp != null && newValidateExp.length() > 128) {
         throw new ValidationException("length (" + newValidateExp.length() + ") of ValidateExp must not exceed 128 on a SystemProperty");
      }
   }

   public void validateValidateTxt(String newValidateTxt) throws ValidationException {
      if(newValidateTxt != null && newValidateTxt.length() > 256) {
         throw new ValidationException("length (" + newValidateTxt.length() + ") of ValidateTxt must not exceed 256 on a SystemProperty");
      }
   }

   public SystemProperty getSystemProperty() {
      if(this.mEditorDataAdapter == null) {
         this.mEditorDataAdapter = new SystemPropertyAdapter((SystemPropertyEditorSessionImpl)this.getBusinessSession(), this.mEditorData);
      }

      return this.mEditorDataAdapter;
   }

   public void saveModifications() throws ValidationException {
      this.saveValidation();
   }

   private void saveValidation() throws ValidationException {}
}
