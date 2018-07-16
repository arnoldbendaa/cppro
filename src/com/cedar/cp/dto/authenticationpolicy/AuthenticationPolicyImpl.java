// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:46
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.authenticationpolicy;

import com.cedar.cp.api.authenticationpolicy.AuthenticationPolicy;
import com.cedar.cp.dto.authenticationpolicy.AuthenticationPolicyPK;
import java.io.Serializable;

public class AuthenticationPolicyImpl implements AuthenticationPolicy, Serializable, Cloneable {

   private Object mPrimaryKey;
   private String mVisId;
   private String mDescription;
   private int mAuthenticationTechnique;
   private int mSecurityAdministrator;
   private int mMinimumPasswordLength;
   private int mMinimumAlphas;
   private int mMinimumDigits;
   private int mMaximumRepetition;
   private int mMinimumChanges;
   private boolean mPasswordUseridDiffer;
   private String mPasswordMask;
   private int mPasswordReuseDelta;
   private int mMaximumLogonAttempts;
   private int mPasswordExpiry;
   private int mSecurityLog;
   private boolean mActive;
   private String mJaasEntryName;
   private String mCosignConfigurationFile;
   private String mNtlmNetbiosWins;
   private String mNtlmDomain;
   private String mNtlmDomainController;
   private int mNtlmLogLevel;
   private int mVersionNum;


   public AuthenticationPolicyImpl(Object paramKey) {
      this.mPrimaryKey = paramKey;
      this.mVisId = "";
      this.mDescription = "";
      this.mAuthenticationTechnique = 0;
      this.mSecurityAdministrator = 0;
      this.mMinimumPasswordLength = 0;
      this.mMinimumAlphas = 0;
      this.mMinimumDigits = 0;
      this.mMaximumRepetition = 0;
      this.mMinimumChanges = 0;
      this.mPasswordUseridDiffer = false;
      this.mPasswordMask = "";
      this.mPasswordReuseDelta = 0;
      this.mMaximumLogonAttempts = 0;
      this.mPasswordExpiry = 0;
      this.mSecurityLog = 0;
      this.mActive = false;
      this.mJaasEntryName = "";
      this.mCosignConfigurationFile = "";
      this.mNtlmNetbiosWins = "";
      this.mNtlmDomain = "";
      this.mNtlmDomainController = "";
      this.mNtlmLogLevel = 0;
   }

   public Object getPrimaryKey() {
      return this.mPrimaryKey;
   }

   public void setPrimaryKey(Object paramKey) {
      this.mPrimaryKey = (AuthenticationPolicyPK)paramKey;
   }

   public String getVisId() {
      return this.mVisId;
   }

   public String getDescription() {
      return this.mDescription;
   }

   public int getAuthenticationTechnique() {
      return this.mAuthenticationTechnique;
   }

   public int getSecurityAdministrator() {
      return this.mSecurityAdministrator;
   }

   public int getMinimumPasswordLength() {
      return this.mMinimumPasswordLength;
   }

   public int getMinimumAlphas() {
      return this.mMinimumAlphas;
   }

   public int getMinimumDigits() {
      return this.mMinimumDigits;
   }

   public int getMaximumRepetition() {
      return this.mMaximumRepetition;
   }

   public int getMinimumChanges() {
      return this.mMinimumChanges;
   }

   public boolean isPasswordUseridDiffer() {
      return this.mPasswordUseridDiffer;
   }

   public String getPasswordMask() {
      return this.mPasswordMask;
   }

   public int getPasswordReuseDelta() {
      return this.mPasswordReuseDelta;
   }

   public int getMaximumLogonAttempts() {
      return this.mMaximumLogonAttempts;
   }

   public int getPasswordExpiry() {
      return this.mPasswordExpiry;
   }

   public int getSecurityLog() {
      return this.mSecurityLog;
   }

   public boolean isActive() {
      return this.mActive;
   }

   public String getJaasEntryName() {
      return this.mJaasEntryName;
   }

   public String getCosignConfigurationFile() {
      return this.mCosignConfigurationFile;
   }

   public String getNtlmNetbiosWins() {
      return this.mNtlmNetbiosWins;
   }

   public String getNtlmDomain() {
      return this.mNtlmDomain;
   }

   public String getNtlmDomainController() {
      return this.mNtlmDomainController;
   }

   public int getNtlmLogLevel() {
      return this.mNtlmLogLevel;
   }

   public void setVersionNum(int p) {
      this.mVersionNum = p;
   }

   public int getVersionNum() {
      return this.mVersionNum;
   }

   public void setVisId(String paramVisId) {
      this.mVisId = paramVisId;
   }

   public void setDescription(String paramDescription) {
      this.mDescription = paramDescription;
   }

   public void setAuthenticationTechnique(int paramAuthenticationTechnique) {
      this.mAuthenticationTechnique = paramAuthenticationTechnique;
   }

   public void setSecurityAdministrator(int paramSecurityAdministrator) {
      this.mSecurityAdministrator = paramSecurityAdministrator;
   }

   public void setMinimumPasswordLength(int paramMinimumPasswordLength) {
      this.mMinimumPasswordLength = paramMinimumPasswordLength;
   }

   public void setMinimumAlphas(int paramMinimumAlphas) {
      this.mMinimumAlphas = paramMinimumAlphas;
   }

   public void setMinimumDigits(int paramMinimumDigits) {
      this.mMinimumDigits = paramMinimumDigits;
   }

   public void setMaximumRepetition(int paramMaximumRepetition) {
      this.mMaximumRepetition = paramMaximumRepetition;
   }

   public void setMinimumChanges(int paramMinimumChanges) {
      this.mMinimumChanges = paramMinimumChanges;
   }

   public void setPasswordUseridDiffer(boolean paramPasswordUseridDiffer) {
      this.mPasswordUseridDiffer = paramPasswordUseridDiffer;
   }

   public void setPasswordMask(String paramPasswordMask) {
      this.mPasswordMask = paramPasswordMask;
   }

   public void setPasswordReuseDelta(int paramPasswordReuseDelta) {
      this.mPasswordReuseDelta = paramPasswordReuseDelta;
   }

   public void setMaximumLogonAttempts(int paramMaximumLogonAttempts) {
      this.mMaximumLogonAttempts = paramMaximumLogonAttempts;
   }

   public void setPasswordExpiry(int paramPasswordExpiry) {
      this.mPasswordExpiry = paramPasswordExpiry;
   }

   public void setSecurityLog(int paramSecurityLog) {
      this.mSecurityLog = paramSecurityLog;
   }

   public void setActive(boolean paramActive) {
      this.mActive = paramActive;
   }

   public void setJaasEntryName(String paramJaasEntryName) {
      this.mJaasEntryName = paramJaasEntryName;
   }

   public void setCosignConfigurationFile(String paramCosignConfigurationFile) {
      this.mCosignConfigurationFile = paramCosignConfigurationFile;
   }

   public void setNtlmNetbiosWins(String paramNtlmNetbiosWins) {
      this.mNtlmNetbiosWins = paramNtlmNetbiosWins;
   }

   public void setNtlmDomain(String paramNtlmDomain) {
      this.mNtlmDomain = paramNtlmDomain;
   }

   public void setNtlmDomainController(String paramNtlmDomainController) {
      this.mNtlmDomainController = paramNtlmDomainController;
   }

   public void setNtlmLogLevel(int paramNtlmLogLevel) {
      this.mNtlmLogLevel = paramNtlmLogLevel;
   }
}
