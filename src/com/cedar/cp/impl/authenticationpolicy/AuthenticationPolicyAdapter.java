// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:03
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.impl.authenticationpolicy;

import com.cedar.cp.api.authenticationpolicy.AuthenticationPolicy;
import com.cedar.cp.dto.authenticationpolicy.AuthenticationPolicyImpl;
import com.cedar.cp.dto.authenticationpolicy.AuthenticationPolicyPK;
import com.cedar.cp.impl.authenticationpolicy.AuthenticationPolicyEditorSessionImpl;

public class AuthenticationPolicyAdapter implements AuthenticationPolicy {

   private AuthenticationPolicyImpl mEditorData;
   private AuthenticationPolicyEditorSessionImpl mEditorSessionImpl;


   public AuthenticationPolicyAdapter(AuthenticationPolicyEditorSessionImpl e, AuthenticationPolicyImpl editorData) {
      this.mEditorData = editorData;
      this.mEditorSessionImpl = e;
   }

   public void setPrimaryKey(Object key) {
      this.mEditorData.setPrimaryKey(key);
   }

   protected AuthenticationPolicyEditorSessionImpl getEditorSessionImpl() {
      return this.mEditorSessionImpl;
   }

   protected AuthenticationPolicyImpl getEditorData() {
      return this.mEditorData;
   }

   public Object getPrimaryKey() {
      return this.mEditorData.getPrimaryKey();
   }

   void setPrimaryKey(AuthenticationPolicyPK paramKey) {
      this.mEditorData.setPrimaryKey(paramKey);
   }

   public String getVisId() {
      return this.mEditorData.getVisId();
   }

   public String getDescription() {
      return this.mEditorData.getDescription();
   }

   public int getAuthenticationTechnique() {
      return this.mEditorData.getAuthenticationTechnique();
   }

   public int getSecurityAdministrator() {
      return this.mEditorData.getSecurityAdministrator();
   }

   public int getMinimumPasswordLength() {
      return this.mEditorData.getMinimumPasswordLength();
   }

   public int getMinimumAlphas() {
      return this.mEditorData.getMinimumAlphas();
   }

   public int getMinimumDigits() {
      return this.mEditorData.getMinimumDigits();
   }

   public int getMaximumRepetition() {
      return this.mEditorData.getMaximumRepetition();
   }

   public int getMinimumChanges() {
      return this.mEditorData.getMinimumChanges();
   }

   public boolean isPasswordUseridDiffer() {
      return this.mEditorData.isPasswordUseridDiffer();
   }

   public String getPasswordMask() {
      return this.mEditorData.getPasswordMask();
   }

   public int getPasswordReuseDelta() {
      return this.mEditorData.getPasswordReuseDelta();
   }

   public int getMaximumLogonAttempts() {
      return this.mEditorData.getMaximumLogonAttempts();
   }

   public int getPasswordExpiry() {
      return this.mEditorData.getPasswordExpiry();
   }

   public int getSecurityLog() {
      return this.mEditorData.getSecurityLog();
   }

   public boolean isActive() {
      return this.mEditorData.isActive();
   }

   public String getJaasEntryName() {
      return this.mEditorData.getJaasEntryName();
   }

   public String getCosignConfigurationFile() {
      return this.mEditorData.getCosignConfigurationFile();
   }

   public String getNtlmNetbiosWins() {
      return this.mEditorData.getNtlmNetbiosWins();
   }

   public String getNtlmDomain() {
      return this.mEditorData.getNtlmDomain();
   }

   public String getNtlmDomainController() {
      return this.mEditorData.getNtlmDomainController();
   }

   public int getNtlmLogLevel() {
      return this.mEditorData.getNtlmLogLevel();
   }

   public void setVisId(String p) {
      this.mEditorData.setVisId(p);
   }

   public void setDescription(String p) {
      this.mEditorData.setDescription(p);
   }

   public void setAuthenticationTechnique(int p) {
      this.mEditorData.setAuthenticationTechnique(p);
   }

   public void setSecurityAdministrator(int p) {
      this.mEditorData.setSecurityAdministrator(p);
   }

   public void setMinimumPasswordLength(int p) {
      this.mEditorData.setMinimumPasswordLength(p);
   }

   public void setMinimumAlphas(int p) {
      this.mEditorData.setMinimumAlphas(p);
   }

   public void setMinimumDigits(int p) {
      this.mEditorData.setMinimumDigits(p);
   }

   public void setMaximumRepetition(int p) {
      this.mEditorData.setMaximumRepetition(p);
   }

   public void setMinimumChanges(int p) {
      this.mEditorData.setMinimumChanges(p);
   }

   public void setPasswordUseridDiffer(boolean p) {
      this.mEditorData.setPasswordUseridDiffer(p);
   }

   public void setPasswordMask(String p) {
      this.mEditorData.setPasswordMask(p);
   }

   public void setPasswordReuseDelta(int p) {
      this.mEditorData.setPasswordReuseDelta(p);
   }

   public void setMaximumLogonAttempts(int p) {
      this.mEditorData.setMaximumLogonAttempts(p);
   }

   public void setPasswordExpiry(int p) {
      this.mEditorData.setPasswordExpiry(p);
   }

   public void setSecurityLog(int p) {
      this.mEditorData.setSecurityLog(p);
   }

   public void setActive(boolean p) {
      this.mEditorData.setActive(p);
   }

   public void setJaasEntryName(String p) {
      this.mEditorData.setJaasEntryName(p);
   }

   public void setCosignConfigurationFile(String p) {
      this.mEditorData.setCosignConfigurationFile(p);
   }

   public void setNtlmNetbiosWins(String p) {
      this.mEditorData.setNtlmNetbiosWins(p);
   }

   public void setNtlmDomain(String p) {
      this.mEditorData.setNtlmDomain(p);
   }

   public void setNtlmDomainController(String p) {
      this.mEditorData.setNtlmDomainController(p);
   }

   public void setNtlmLogLevel(int p) {
      this.mEditorData.setNtlmLogLevel(p);
   }
}
