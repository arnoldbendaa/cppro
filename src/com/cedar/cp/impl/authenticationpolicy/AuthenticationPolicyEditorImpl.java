// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:03
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.impl.authenticationpolicy;

import com.cedar.cp.api.authenticationpolicy.AuthenticationPolicy;
import com.cedar.cp.api.authenticationpolicy.AuthenticationPolicyEditor;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.dto.authenticationpolicy.AuthenticationPolicyEditorSessionSSO;
import com.cedar.cp.dto.authenticationpolicy.AuthenticationPolicyImpl;
import com.cedar.cp.impl.authenticationpolicy.AuthenticationPolicyAdapter;
import com.cedar.cp.impl.authenticationpolicy.AuthenticationPolicyEditorSessionImpl;
import com.cedar.cp.impl.base.BusinessEditorImpl;
import com.cedar.cp.util.StringUtils;

public class AuthenticationPolicyEditorImpl extends BusinessEditorImpl implements AuthenticationPolicyEditor {

   private AuthenticationPolicyEditorSessionSSO mServerSessionData;
   private AuthenticationPolicyImpl mEditorData;
   private AuthenticationPolicyAdapter mEditorDataAdapter;


   public AuthenticationPolicyEditorImpl(AuthenticationPolicyEditorSessionImpl session, AuthenticationPolicyEditorSessionSSO serverSessionData, AuthenticationPolicyImpl editorData) {
      super(session);
      this.mServerSessionData = serverSessionData;
      this.mEditorData = editorData;
   }

   public void updateEditorData(AuthenticationPolicyEditorSessionSSO serverSessionData, AuthenticationPolicyImpl editorData) {
      this.mServerSessionData = serverSessionData;
      this.mEditorData = editorData;
   }

   public void setAuthenticationTechnique(int newAuthenticationTechnique) throws ValidationException {
      this.validateAuthenticationTechnique(newAuthenticationTechnique);
      if(this.mEditorData.getAuthenticationTechnique() != newAuthenticationTechnique) {
         this.setContentModified();
         this.mEditorData.setAuthenticationTechnique(newAuthenticationTechnique);
      }
   }

   public void setSecurityAdministrator(int newSecurityAdministrator) throws ValidationException {
      this.validateSecurityAdministrator(newSecurityAdministrator);
      if(this.mEditorData.getSecurityAdministrator() != newSecurityAdministrator) {
         this.setContentModified();
         this.mEditorData.setSecurityAdministrator(newSecurityAdministrator);
      }
   }

   public void setMinimumPasswordLength(int newMinimumPasswordLength) throws ValidationException {
      this.validateMinimumPasswordLength(newMinimumPasswordLength);
      if(this.mEditorData.getMinimumPasswordLength() != newMinimumPasswordLength) {
         this.setContentModified();
         this.mEditorData.setMinimumPasswordLength(newMinimumPasswordLength);
      }
   }

   public void setMinimumAlphas(int newMinimumAlphas) throws ValidationException {
      this.validateMinimumAlphas(newMinimumAlphas);
      if(this.mEditorData.getMinimumAlphas() != newMinimumAlphas) {
         this.setContentModified();
         this.mEditorData.setMinimumAlphas(newMinimumAlphas);
      }
   }

   public void setMinimumDigits(int newMinimumDigits) throws ValidationException {
      this.validateMinimumDigits(newMinimumDigits);
      if(this.mEditorData.getMinimumDigits() != newMinimumDigits) {
         this.setContentModified();
         this.mEditorData.setMinimumDigits(newMinimumDigits);
      }
   }

   public void setMaximumRepetition(int newMaximumRepetition) throws ValidationException {
      this.validateMaximumRepetition(newMaximumRepetition);
      if(this.mEditorData.getMaximumRepetition() != newMaximumRepetition) {
         this.setContentModified();
         this.mEditorData.setMaximumRepetition(newMaximumRepetition);
      }
   }

   public void setMinimumChanges(int newMinimumChanges) throws ValidationException {
      this.validateMinimumChanges(newMinimumChanges);
      if(this.mEditorData.getMinimumChanges() != newMinimumChanges) {
         this.setContentModified();
         this.mEditorData.setMinimumChanges(newMinimumChanges);
      }
   }

   public void setPasswordUseridDiffer(boolean newPasswordUseridDiffer) throws ValidationException {
      this.validatePasswordUseridDiffer(newPasswordUseridDiffer);
      if(this.mEditorData.isPasswordUseridDiffer() != newPasswordUseridDiffer) {
         this.setContentModified();
         this.mEditorData.setPasswordUseridDiffer(newPasswordUseridDiffer);
      }
   }

   public void setPasswordReuseDelta(int newPasswordReuseDelta) throws ValidationException {
      this.validatePasswordReuseDelta(newPasswordReuseDelta);
      if(this.mEditorData.getPasswordReuseDelta() != newPasswordReuseDelta) {
         this.setContentModified();
         this.mEditorData.setPasswordReuseDelta(newPasswordReuseDelta);
      }
   }

   public void setMaximumLogonAttempts(int newMaximumLogonAttempts) throws ValidationException {
      this.validateMaximumLogonAttempts(newMaximumLogonAttempts);
      if(this.mEditorData.getMaximumLogonAttempts() != newMaximumLogonAttempts) {
         this.setContentModified();
         this.mEditorData.setMaximumLogonAttempts(newMaximumLogonAttempts);
      }
   }

   public void setPasswordExpiry(int newPasswordExpiry) throws ValidationException {
      this.validatePasswordExpiry(newPasswordExpiry);
      if(this.mEditorData.getPasswordExpiry() != newPasswordExpiry) {
         this.setContentModified();
         this.mEditorData.setPasswordExpiry(newPasswordExpiry);
      }
   }

   public void setSecurityLog(int newSecurityLog) throws ValidationException {
      this.validateSecurityLog(newSecurityLog);
      if(this.mEditorData.getSecurityLog() != newSecurityLog) {
         this.setContentModified();
         this.mEditorData.setSecurityLog(newSecurityLog);
      }
   }

   public void setActive(boolean newActive) throws ValidationException {
      this.validateActive(newActive);
      if(this.mEditorData.isActive() != newActive) {
         this.setContentModified();
         this.mEditorData.setActive(newActive);
      }
   }

   public void setNtlmLogLevel(int newNtlmLogLevel) throws ValidationException {
      this.validateNtlmLogLevel(newNtlmLogLevel);
      if(this.mEditorData.getNtlmLogLevel() != newNtlmLogLevel) {
         this.setContentModified();
         this.mEditorData.setNtlmLogLevel(newNtlmLogLevel);
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

   public void setPasswordMask(String newPasswordMask) throws ValidationException {
      if(newPasswordMask != null) {
         newPasswordMask = StringUtils.rtrim(newPasswordMask);
      }

      this.validatePasswordMask(newPasswordMask);
      if(this.mEditorData.getPasswordMask() == null || !this.mEditorData.getPasswordMask().equals(newPasswordMask)) {
         this.setContentModified();
         this.mEditorData.setPasswordMask(newPasswordMask);
      }
   }

   public void setJaasEntryName(String newJaasEntryName) throws ValidationException {
      if(newJaasEntryName != null) {
         newJaasEntryName = StringUtils.rtrim(newJaasEntryName);
      }

      this.validateJaasEntryName(newJaasEntryName);
      if(this.mEditorData.getJaasEntryName() == null || !this.mEditorData.getJaasEntryName().equals(newJaasEntryName)) {
         this.setContentModified();
         this.mEditorData.setJaasEntryName(newJaasEntryName);
      }
   }

   public void setCosignConfigurationFile(String newCosignConfigurationFile) throws ValidationException {
      if(newCosignConfigurationFile != null) {
         newCosignConfigurationFile = StringUtils.rtrim(newCosignConfigurationFile);
      }

      this.validateCosignConfigurationFile(newCosignConfigurationFile);
      if(this.mEditorData.getCosignConfigurationFile() == null || !this.mEditorData.getCosignConfigurationFile().equals(newCosignConfigurationFile)) {
         this.setContentModified();
         this.mEditorData.setCosignConfigurationFile(newCosignConfigurationFile);
      }
   }

   public void setNtlmNetbiosWins(String newNtlmNetbiosWins) throws ValidationException {
      if(newNtlmNetbiosWins != null) {
         newNtlmNetbiosWins = StringUtils.rtrim(newNtlmNetbiosWins);
      }

      this.validateNtlmNetbiosWins(newNtlmNetbiosWins);
      if(this.mEditorData.getNtlmNetbiosWins() == null || !this.mEditorData.getNtlmNetbiosWins().equals(newNtlmNetbiosWins)) {
         this.setContentModified();
         this.mEditorData.setNtlmNetbiosWins(newNtlmNetbiosWins);
      }
   }

   public void setNtlmDomain(String newNtlmDomain) throws ValidationException {
      if(newNtlmDomain != null) {
         newNtlmDomain = StringUtils.rtrim(newNtlmDomain);
      }

      this.validateNtlmDomain(newNtlmDomain);
      if(this.mEditorData.getNtlmDomain() == null || !this.mEditorData.getNtlmDomain().equals(newNtlmDomain)) {
         this.setContentModified();
         this.mEditorData.setNtlmDomain(newNtlmDomain);
      }
   }

   public void setNtlmDomainController(String newNtlmDomainController) throws ValidationException {
      if(newNtlmDomainController != null) {
         newNtlmDomainController = StringUtils.rtrim(newNtlmDomainController);
      }

      this.validateNtlmDomainController(newNtlmDomainController);
      if(this.mEditorData.getNtlmDomainController() == null || !this.mEditorData.getNtlmDomainController().equals(newNtlmDomainController)) {
         this.setContentModified();
         this.mEditorData.setNtlmDomainController(newNtlmDomainController);
      }
   }

   public void validateVisId(String newVisId) throws ValidationException {
      if(newVisId != null && newVisId.length() > 20) {
         throw new ValidationException("length (" + newVisId.length() + ") of VisId must not exceed 20 on a AuthenticationPolicy");
      }
   }

   public void validateDescription(String newDescription) throws ValidationException {
      if(newDescription != null && newDescription.length() > 128) {
         throw new ValidationException("length (" + newDescription.length() + ") of Description must not exceed 128 on a AuthenticationPolicy");
      }
   }

   public void validateAuthenticationTechnique(int newAuthenticationTechnique) throws ValidationException {}

   public void validateSecurityAdministrator(int newSecurityAdministrator) throws ValidationException {}

   public void validateMinimumPasswordLength(int newMinimumPasswordLength) throws ValidationException {}

   public void validateMinimumAlphas(int newMinimumAlphas) throws ValidationException {}

   public void validateMinimumDigits(int newMinimumDigits) throws ValidationException {}

   public void validateMaximumRepetition(int newMaximumRepetition) throws ValidationException {}

   public void validateMinimumChanges(int newMinimumChanges) throws ValidationException {}

   public void validatePasswordUseridDiffer(boolean newPasswordUseridDiffer) throws ValidationException {}

   public void validatePasswordMask(String newPasswordMask) throws ValidationException {
      if(newPasswordMask != null && newPasswordMask.length() > 18) {
         throw new ValidationException("length (" + newPasswordMask.length() + ") of PasswordMask must not exceed 18 on a AuthenticationPolicy");
      }
   }

   public void validatePasswordReuseDelta(int newPasswordReuseDelta) throws ValidationException {}

   public void validateMaximumLogonAttempts(int newMaximumLogonAttempts) throws ValidationException {}

   public void validatePasswordExpiry(int newPasswordExpiry) throws ValidationException {}

   public void validateSecurityLog(int newSecurityLog) throws ValidationException {}

   public void validateActive(boolean newActive) throws ValidationException {}

   public void validateJaasEntryName(String newJaasEntryName) throws ValidationException {
      if(newJaasEntryName != null && newJaasEntryName.length() > 12) {
         throw new ValidationException("length (" + newJaasEntryName.length() + ") of JaasEntryName must not exceed 12 on a AuthenticationPolicy");
      }
   }

   public void validateCosignConfigurationFile(String newCosignConfigurationFile) throws ValidationException {
      if(newCosignConfigurationFile != null && newCosignConfigurationFile.length() > 127) {
         throw new ValidationException("length (" + newCosignConfigurationFile.length() + ") of CosignConfigurationFile must not exceed 127 on a AuthenticationPolicy");
      }
   }

   public void validateNtlmNetbiosWins(String newNtlmNetbiosWins) throws ValidationException {
      if(newNtlmNetbiosWins != null && newNtlmNetbiosWins.length() > 20) {
         throw new ValidationException("length (" + newNtlmNetbiosWins.length() + ") of NtlmNetbiosWins must not exceed 20 on a AuthenticationPolicy");
      }
   }

   public void validateNtlmDomain(String newNtlmDomain) throws ValidationException {
      if(newNtlmDomain != null && newNtlmDomain.length() > 127) {
         throw new ValidationException("length (" + newNtlmDomain.length() + ") of NtlmDomain must not exceed 127 on a AuthenticationPolicy");
      }
   }

   public void validateNtlmDomainController(String newNtlmDomainController) throws ValidationException {
      if(newNtlmDomainController != null && newNtlmDomainController.length() > 20) {
         throw new ValidationException("length (" + newNtlmDomainController.length() + ") of NtlmDomainController must not exceed 20 on a AuthenticationPolicy");
      }
   }

   public void validateNtlmLogLevel(int newNtlmLogLevel) throws ValidationException {}

   public AuthenticationPolicy getAuthenticationPolicy() {
      if(this.mEditorDataAdapter == null) {
         this.mEditorDataAdapter = new AuthenticationPolicyAdapter((AuthenticationPolicyEditorSessionImpl)this.getBusinessSession(), this.mEditorData);
      }

      return this.mEditorDataAdapter;
   }

   public void saveModifications() throws ValidationException {
      this.saveValidation();
   }

   private void saveValidation() throws ValidationException {}
}
