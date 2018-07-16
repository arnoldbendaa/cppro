// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:08:01
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.impl.authenticationpolicy;

import com.cedar.cp.api.authenticationpolicy.AuthenticationPolicyRef;
import com.cedar.cp.dto.authenticationpolicy.AuthenticationPolicyPK;
import com.cedar.cp.dto.authenticationpolicy.AuthenticationPolicyRefImpl;
import java.io.Serializable;
import java.sql.Timestamp;

public class AuthenticationPolicyEVO implements Serializable {

   private transient AuthenticationPolicyPK mPK;
   private int mAuthenticationPolicyId;
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
   private int mUpdatedByUserId;
   private Timestamp mUpdatedTime;
   private Timestamp mCreatedTime;
   private boolean mModified;
   public static final int AUTHENTICATE_VIA_INTERNAL = 1;
   public static final int AUTHENTICATE_VIA_LOGON_MODULE = 2;
   public static final int AUTHENTICATE_VIA_COSIGN = 3;
   public static final int AUTHENTICATE_VIA_NTLM = 4;
   public static final int AUTHENTICATE_VIA_SSO = 5;
   public static final int NONE = 1;
   public static final int FAILED = 2;
   public static final int FAILED_AND_SUCCESSFUL = 3;
   public static final int ALL = 4;


   public AuthenticationPolicyEVO() {}

   public AuthenticationPolicyEVO(int newAuthenticationPolicyId, String newVisId, String newDescription, int newAuthenticationTechnique, int newSecurityAdministrator, int newMinimumPasswordLength, int newMinimumAlphas, int newMinimumDigits, int newMaximumRepetition, int newMinimumChanges, boolean newPasswordUseridDiffer, String newPasswordMask, int newPasswordReuseDelta, int newMaximumLogonAttempts, int newPasswordExpiry, int newSecurityLog, boolean newActive, String newJaasEntryName, String newCosignConfigurationFile, String newNtlmNetbiosWins, String newNtlmDomain, String newNtlmDomainController, int newNtlmLogLevel, int newVersionNum) {
      this.mAuthenticationPolicyId = newAuthenticationPolicyId;
      this.mVisId = newVisId;
      this.mDescription = newDescription;
      this.mAuthenticationTechnique = newAuthenticationTechnique;
      this.mSecurityAdministrator = newSecurityAdministrator;
      this.mMinimumPasswordLength = newMinimumPasswordLength;
      this.mMinimumAlphas = newMinimumAlphas;
      this.mMinimumDigits = newMinimumDigits;
      this.mMaximumRepetition = newMaximumRepetition;
      this.mMinimumChanges = newMinimumChanges;
      this.mPasswordUseridDiffer = newPasswordUseridDiffer;
      this.mPasswordMask = newPasswordMask;
      this.mPasswordReuseDelta = newPasswordReuseDelta;
      this.mMaximumLogonAttempts = newMaximumLogonAttempts;
      this.mPasswordExpiry = newPasswordExpiry;
      this.mSecurityLog = newSecurityLog;
      this.mActive = newActive;
      this.mJaasEntryName = newJaasEntryName;
      this.mCosignConfigurationFile = newCosignConfigurationFile;
      this.mNtlmNetbiosWins = newNtlmNetbiosWins;
      this.mNtlmDomain = newNtlmDomain;
      this.mNtlmDomainController = newNtlmDomainController;
      this.mNtlmLogLevel = newNtlmLogLevel;
      this.mVersionNum = newVersionNum;
   }

   public AuthenticationPolicyPK getPK() {
      if(this.mPK == null) {
         this.mPK = new AuthenticationPolicyPK(this.mAuthenticationPolicyId);
      }

      return this.mPK;
   }

   public boolean isModified() {
      return this.mModified;
   }

   public int getAuthenticationPolicyId() {
      return this.mAuthenticationPolicyId;
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

   public boolean getPasswordUseridDiffer() {
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

   public boolean getActive() {
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

   public int getVersionNum() {
      return this.mVersionNum;
   }

   public int getUpdatedByUserId() {
      return this.mUpdatedByUserId;
   }

   public Timestamp getUpdatedTime() {
      return this.mUpdatedTime;
   }

   public Timestamp getCreatedTime() {
      return this.mCreatedTime;
   }

   public void setAuthenticationPolicyId(int newAuthenticationPolicyId) {
      if(this.mAuthenticationPolicyId != newAuthenticationPolicyId) {
         this.mModified = true;
         this.mAuthenticationPolicyId = newAuthenticationPolicyId;
         this.mPK = null;
      }
   }

   public void setAuthenticationTechnique(int newAuthenticationTechnique) {
      if(this.mAuthenticationTechnique != newAuthenticationTechnique) {
         this.mModified = true;
         this.mAuthenticationTechnique = newAuthenticationTechnique;
      }
   }

   public void setSecurityAdministrator(int newSecurityAdministrator) {
      if(this.mSecurityAdministrator != newSecurityAdministrator) {
         this.mModified = true;
         this.mSecurityAdministrator = newSecurityAdministrator;
      }
   }

   public void setMinimumPasswordLength(int newMinimumPasswordLength) {
      if(this.mMinimumPasswordLength != newMinimumPasswordLength) {
         this.mModified = true;
         this.mMinimumPasswordLength = newMinimumPasswordLength;
      }
   }

   public void setMinimumAlphas(int newMinimumAlphas) {
      if(this.mMinimumAlphas != newMinimumAlphas) {
         this.mModified = true;
         this.mMinimumAlphas = newMinimumAlphas;
      }
   }

   public void setMinimumDigits(int newMinimumDigits) {
      if(this.mMinimumDigits != newMinimumDigits) {
         this.mModified = true;
         this.mMinimumDigits = newMinimumDigits;
      }
   }

   public void setMaximumRepetition(int newMaximumRepetition) {
      if(this.mMaximumRepetition != newMaximumRepetition) {
         this.mModified = true;
         this.mMaximumRepetition = newMaximumRepetition;
      }
   }

   public void setMinimumChanges(int newMinimumChanges) {
      if(this.mMinimumChanges != newMinimumChanges) {
         this.mModified = true;
         this.mMinimumChanges = newMinimumChanges;
      }
   }

   public void setPasswordUseridDiffer(boolean newPasswordUseridDiffer) {
      if(this.mPasswordUseridDiffer != newPasswordUseridDiffer) {
         this.mModified = true;
         this.mPasswordUseridDiffer = newPasswordUseridDiffer;
      }
   }

   public void setPasswordReuseDelta(int newPasswordReuseDelta) {
      if(this.mPasswordReuseDelta != newPasswordReuseDelta) {
         this.mModified = true;
         this.mPasswordReuseDelta = newPasswordReuseDelta;
      }
   }

   public void setMaximumLogonAttempts(int newMaximumLogonAttempts) {
      if(this.mMaximumLogonAttempts != newMaximumLogonAttempts) {
         this.mModified = true;
         this.mMaximumLogonAttempts = newMaximumLogonAttempts;
      }
   }

   public void setPasswordExpiry(int newPasswordExpiry) {
      if(this.mPasswordExpiry != newPasswordExpiry) {
         this.mModified = true;
         this.mPasswordExpiry = newPasswordExpiry;
      }
   }

   public void setSecurityLog(int newSecurityLog) {
      if(this.mSecurityLog != newSecurityLog) {
         this.mModified = true;
         this.mSecurityLog = newSecurityLog;
      }
   }

   public void setActive(boolean newActive) {
      if(this.mActive != newActive) {
         this.mModified = true;
         this.mActive = newActive;
      }
   }

   public void setNtlmLogLevel(int newNtlmLogLevel) {
      if(this.mNtlmLogLevel != newNtlmLogLevel) {
         this.mModified = true;
         this.mNtlmLogLevel = newNtlmLogLevel;
      }
   }

   public void setVersionNum(int newVersionNum) {
      if(this.mVersionNum != newVersionNum) {
         this.mModified = true;
         this.mVersionNum = newVersionNum;
      }
   }

   public void setUpdatedByUserId(int newUpdatedByUserId) {
      this.mUpdatedByUserId = newUpdatedByUserId;
   }

   public void setVisId(String newVisId) {
      if(this.mVisId != null && newVisId == null || this.mVisId == null && newVisId != null || this.mVisId != null && newVisId != null && !this.mVisId.equals(newVisId)) {
         this.mVisId = newVisId;
         this.mModified = true;
      }

   }

   public void setDescription(String newDescription) {
      if(this.mDescription != null && newDescription == null || this.mDescription == null && newDescription != null || this.mDescription != null && newDescription != null && !this.mDescription.equals(newDescription)) {
         this.mDescription = newDescription;
         this.mModified = true;
      }

   }

   public void setPasswordMask(String newPasswordMask) {
      if(this.mPasswordMask != null && newPasswordMask == null || this.mPasswordMask == null && newPasswordMask != null || this.mPasswordMask != null && newPasswordMask != null && !this.mPasswordMask.equals(newPasswordMask)) {
         this.mPasswordMask = newPasswordMask;
         this.mModified = true;
      }

   }

   public void setJaasEntryName(String newJaasEntryName) {
      if(this.mJaasEntryName != null && newJaasEntryName == null || this.mJaasEntryName == null && newJaasEntryName != null || this.mJaasEntryName != null && newJaasEntryName != null && !this.mJaasEntryName.equals(newJaasEntryName)) {
         this.mJaasEntryName = newJaasEntryName;
         this.mModified = true;
      }

   }

   public void setCosignConfigurationFile(String newCosignConfigurationFile) {
      if(this.mCosignConfigurationFile != null && newCosignConfigurationFile == null || this.mCosignConfigurationFile == null && newCosignConfigurationFile != null || this.mCosignConfigurationFile != null && newCosignConfigurationFile != null && !this.mCosignConfigurationFile.equals(newCosignConfigurationFile)) {
         this.mCosignConfigurationFile = newCosignConfigurationFile;
         this.mModified = true;
      }

   }

   public void setNtlmNetbiosWins(String newNtlmNetbiosWins) {
      if(this.mNtlmNetbiosWins != null && newNtlmNetbiosWins == null || this.mNtlmNetbiosWins == null && newNtlmNetbiosWins != null || this.mNtlmNetbiosWins != null && newNtlmNetbiosWins != null && !this.mNtlmNetbiosWins.equals(newNtlmNetbiosWins)) {
         this.mNtlmNetbiosWins = newNtlmNetbiosWins;
         this.mModified = true;
      }

   }

   public void setNtlmDomain(String newNtlmDomain) {
      if(this.mNtlmDomain != null && newNtlmDomain == null || this.mNtlmDomain == null && newNtlmDomain != null || this.mNtlmDomain != null && newNtlmDomain != null && !this.mNtlmDomain.equals(newNtlmDomain)) {
         this.mNtlmDomain = newNtlmDomain;
         this.mModified = true;
      }

   }

   public void setNtlmDomainController(String newNtlmDomainController) {
      if(this.mNtlmDomainController != null && newNtlmDomainController == null || this.mNtlmDomainController == null && newNtlmDomainController != null || this.mNtlmDomainController != null && newNtlmDomainController != null && !this.mNtlmDomainController.equals(newNtlmDomainController)) {
         this.mNtlmDomainController = newNtlmDomainController;
         this.mModified = true;
      }

   }

   protected void setUpdatedTime(Timestamp newUpdatedTime) {
      this.mUpdatedTime = newUpdatedTime;
   }

   protected void setCreatedTime(Timestamp newCreatedTime) {
      this.mCreatedTime = newCreatedTime;
   }

   public void setDetails(AuthenticationPolicyEVO newDetails) {
      this.setAuthenticationPolicyId(newDetails.getAuthenticationPolicyId());
      this.setVisId(newDetails.getVisId());
      this.setDescription(newDetails.getDescription());
      this.setAuthenticationTechnique(newDetails.getAuthenticationTechnique());
      this.setSecurityAdministrator(newDetails.getSecurityAdministrator());
      this.setMinimumPasswordLength(newDetails.getMinimumPasswordLength());
      this.setMinimumAlphas(newDetails.getMinimumAlphas());
      this.setMinimumDigits(newDetails.getMinimumDigits());
      this.setMaximumRepetition(newDetails.getMaximumRepetition());
      this.setMinimumChanges(newDetails.getMinimumChanges());
      this.setPasswordUseridDiffer(newDetails.getPasswordUseridDiffer());
      this.setPasswordMask(newDetails.getPasswordMask());
      this.setPasswordReuseDelta(newDetails.getPasswordReuseDelta());
      this.setMaximumLogonAttempts(newDetails.getMaximumLogonAttempts());
      this.setPasswordExpiry(newDetails.getPasswordExpiry());
      this.setSecurityLog(newDetails.getSecurityLog());
      this.setActive(newDetails.getActive());
      this.setJaasEntryName(newDetails.getJaasEntryName());
      this.setCosignConfigurationFile(newDetails.getCosignConfigurationFile());
      this.setNtlmNetbiosWins(newDetails.getNtlmNetbiosWins());
      this.setNtlmDomain(newDetails.getNtlmDomain());
      this.setNtlmDomainController(newDetails.getNtlmDomainController());
      this.setNtlmLogLevel(newDetails.getNtlmLogLevel());
      this.setVersionNum(newDetails.getVersionNum());
      this.setUpdatedByUserId(newDetails.getUpdatedByUserId());
      this.setUpdatedTime(newDetails.getUpdatedTime());
      this.setCreatedTime(newDetails.getCreatedTime());
   }

   public AuthenticationPolicyEVO deepClone() {
      AuthenticationPolicyEVO cloned = new AuthenticationPolicyEVO();
      cloned.mModified = this.mModified;
      cloned.mAuthenticationPolicyId = this.mAuthenticationPolicyId;
      cloned.mAuthenticationTechnique = this.mAuthenticationTechnique;
      cloned.mSecurityAdministrator = this.mSecurityAdministrator;
      cloned.mMinimumPasswordLength = this.mMinimumPasswordLength;
      cloned.mMinimumAlphas = this.mMinimumAlphas;
      cloned.mMinimumDigits = this.mMinimumDigits;
      cloned.mMaximumRepetition = this.mMaximumRepetition;
      cloned.mMinimumChanges = this.mMinimumChanges;
      cloned.mPasswordUseridDiffer = this.mPasswordUseridDiffer;
      cloned.mPasswordReuseDelta = this.mPasswordReuseDelta;
      cloned.mMaximumLogonAttempts = this.mMaximumLogonAttempts;
      cloned.mPasswordExpiry = this.mPasswordExpiry;
      cloned.mSecurityLog = this.mSecurityLog;
      cloned.mActive = this.mActive;
      cloned.mNtlmLogLevel = this.mNtlmLogLevel;
      cloned.mVersionNum = this.mVersionNum;
      cloned.mUpdatedByUserId = this.mUpdatedByUserId;
      if(this.mVisId != null) {
         cloned.mVisId = this.mVisId;
      }

      if(this.mDescription != null) {
         cloned.mDescription = this.mDescription;
      }

      if(this.mPasswordMask != null) {
         cloned.mPasswordMask = this.mPasswordMask;
      }

      if(this.mJaasEntryName != null) {
         cloned.mJaasEntryName = this.mJaasEntryName;
      }

      if(this.mCosignConfigurationFile != null) {
         cloned.mCosignConfigurationFile = this.mCosignConfigurationFile;
      }

      if(this.mNtlmNetbiosWins != null) {
         cloned.mNtlmNetbiosWins = this.mNtlmNetbiosWins;
      }

      if(this.mNtlmDomain != null) {
         cloned.mNtlmDomain = this.mNtlmDomain;
      }

      if(this.mNtlmDomainController != null) {
         cloned.mNtlmDomainController = this.mNtlmDomainController;
      }

      if(this.mUpdatedTime != null) {
         cloned.mUpdatedTime = Timestamp.valueOf(this.mUpdatedTime.toString());
      }

      if(this.mCreatedTime != null) {
         cloned.mCreatedTime = Timestamp.valueOf(this.mCreatedTime.toString());
      }

      return cloned;
   }

   public void prepareForInsert() {
      boolean newKey = false;
      if(this.mAuthenticationPolicyId > 0) {
         newKey = true;
         this.mAuthenticationPolicyId = 0;
      } else if(this.mAuthenticationPolicyId < 1) {
         newKey = true;
      }

      this.setVersionNum(0);
   }

   public int getInsertCount(int startCount) {
      int returnCount = startCount;
      if(this.mAuthenticationPolicyId < 1) {
         returnCount = startCount + 1;
      }

      return returnCount;
   }

   public int assignNextKey(int startKey) {
      int nextKey = startKey;
      if(this.mAuthenticationPolicyId < 1) {
         this.mAuthenticationPolicyId = startKey;
         nextKey = startKey + 1;
      }

      return nextKey;
   }

   protected void reset() {
      this.mModified = false;
   }

   public AuthenticationPolicyRef getEntityRef() {
      return new AuthenticationPolicyRefImpl(this.getPK(), this.mVisId);
   }

   public String toString() {
      StringBuffer sb = new StringBuffer();
      sb.append("AuthenticationPolicyId=");
      sb.append(String.valueOf(this.mAuthenticationPolicyId));
      sb.append(' ');
      sb.append("VisId=");
      sb.append(String.valueOf(this.mVisId));
      sb.append(' ');
      sb.append("Description=");
      sb.append(String.valueOf(this.mDescription));
      sb.append(' ');
      sb.append("AuthenticationTechnique=");
      sb.append(String.valueOf(this.mAuthenticationTechnique));
      sb.append(' ');
      sb.append("SecurityAdministrator=");
      sb.append(String.valueOf(this.mSecurityAdministrator));
      sb.append(' ');
      sb.append("MinimumPasswordLength=");
      sb.append(String.valueOf(this.mMinimumPasswordLength));
      sb.append(' ');
      sb.append("MinimumAlphas=");
      sb.append(String.valueOf(this.mMinimumAlphas));
      sb.append(' ');
      sb.append("MinimumDigits=");
      sb.append(String.valueOf(this.mMinimumDigits));
      sb.append(' ');
      sb.append("MaximumRepetition=");
      sb.append(String.valueOf(this.mMaximumRepetition));
      sb.append(' ');
      sb.append("MinimumChanges=");
      sb.append(String.valueOf(this.mMinimumChanges));
      sb.append(' ');
      sb.append("PasswordUseridDiffer=");
      sb.append(String.valueOf(this.mPasswordUseridDiffer));
      sb.append(' ');
      sb.append("PasswordMask=");
      sb.append(String.valueOf(this.mPasswordMask));
      sb.append(' ');
      sb.append("PasswordReuseDelta=");
      sb.append(String.valueOf(this.mPasswordReuseDelta));
      sb.append(' ');
      sb.append("MaximumLogonAttempts=");
      sb.append(String.valueOf(this.mMaximumLogonAttempts));
      sb.append(' ');
      sb.append("PasswordExpiry=");
      sb.append(String.valueOf(this.mPasswordExpiry));
      sb.append(' ');
      sb.append("SecurityLog=");
      sb.append(String.valueOf(this.mSecurityLog));
      sb.append(' ');
      sb.append("Active=");
      sb.append(String.valueOf(this.mActive));
      sb.append(' ');
      sb.append("JaasEntryName=");
      sb.append(String.valueOf(this.mJaasEntryName));
      sb.append(' ');
      sb.append("CosignConfigurationFile=");
      sb.append(String.valueOf(this.mCosignConfigurationFile));
      sb.append(' ');
      sb.append("NtlmNetbiosWins=");
      sb.append(String.valueOf(this.mNtlmNetbiosWins));
      sb.append(' ');
      sb.append("NtlmDomain=");
      sb.append(String.valueOf(this.mNtlmDomain));
      sb.append(' ');
      sb.append("NtlmDomainController=");
      sb.append(String.valueOf(this.mNtlmDomainController));
      sb.append(' ');
      sb.append("NtlmLogLevel=");
      sb.append(String.valueOf(this.mNtlmLogLevel));
      sb.append(' ');
      sb.append("VersionNum=");
      sb.append(String.valueOf(this.mVersionNum));
      sb.append(' ');
      sb.append("UpdatedByUserId=");
      sb.append(String.valueOf(this.mUpdatedByUserId));
      sb.append(' ');
      sb.append("UpdatedTime=");
      sb.append(String.valueOf(this.mUpdatedTime));
      sb.append(' ');
      sb.append("CreatedTime=");
      sb.append(String.valueOf(this.mCreatedTime));
      sb.append(' ');
      if(this.mModified) {
         sb.append("modified ");
      }

      return sb.toString();
   }

   public String print() {
      return this.print(0);
   }

   public String print(int indent) {
      StringBuffer sb = new StringBuffer();

      for(int i = 0; i < indent; ++i) {
         sb.append(' ');
      }

      sb.append("AuthenticationPolicy: ");
      sb.append(this.toString());
      return sb.toString();
   }
}
