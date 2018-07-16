// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:34:59
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.utc.common;


public class CPAuthenticationPolicy {

   private int mAuthenticationTechnique;
   private String mCosignConfigurationFile;
   private String mNtlmNetbiosWins;
   private String mNtlmDomain;
   private String mNtlmDomainController;
   private int mNtlmLogLevel;
   private int mMaximumLogonAttempts = 0;
   private String mSSOMask;
   private String mSSOGroupStoreMask;


   public int getMaximumLogonAttempts() {
      return this.mMaximumLogonAttempts;
   }

   public void setMaximumLogonAttempts(int maximumLogonAttempts) {
      this.mMaximumLogonAttempts = maximumLogonAttempts;
   }

   public int getAuthenticationTechnique() {
      return this.mAuthenticationTechnique;
   }

   public void setAuthenticationTechnique(int authenticationTechnique) {
      this.mAuthenticationTechnique = authenticationTechnique;
   }

   public String getCosignConfigurationFile() {
      return this.mCosignConfigurationFile;
   }

   public void setCosignConfigurationFile(String cosignConfigurationFile) {
      this.mCosignConfigurationFile = cosignConfigurationFile;
   }

   public String getNtlmNetbiosWins() {
      return this.mNtlmNetbiosWins;
   }

   public void setNtlmNetbiosWins(String ntlmNetbiosWins) {
      this.mNtlmNetbiosWins = ntlmNetbiosWins;
   }

   public String getNtlmDomain() {
      return this.mNtlmDomain;
   }

   public void setNtlmDomain(String ntlmDomain) {
      this.mNtlmDomain = ntlmDomain;
   }

   public String getNtlmDomainController() {
      return this.mNtlmDomainController;
   }

   public void setNtlmDomainController(String ntlmDomainController) {
      this.mNtlmDomainController = ntlmDomainController;
   }

   public int getNtlmLogLevel() {
      return this.mNtlmLogLevel;
   }

   public void setNtlmLogLevel(int ntlmLogLevel) {
      this.mNtlmLogLevel = ntlmLogLevel;
   }

   public boolean isInternalTechnique() {
      return this.mAuthenticationTechnique == 1;
   }

   public boolean isCosignTechnique() {
      return this.mAuthenticationTechnique == 3;
   }

   public boolean isNtlmTechnique() {
      return this.mAuthenticationTechnique == 4;
   }

   public boolean isSSOTechnique() {
      return this.mAuthenticationTechnique == 5;
   }

   public String getSSOMask() {
      return this.mSSOMask;
   }

   public void setSSOMask(String SSOMask) {
      this.mSSOMask = SSOMask;
   }

   public String getSSOGroupStoreMask() {
      return this.mSSOGroupStoreMask;
   }

   public void setSSOGroupStoreMask(String SSOGroupStoreMask) {
      this.mSSOGroupStoreMask = SSOGroupStoreMask;
   }
}
