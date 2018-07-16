// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:04:16
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.api.authenticationpolicy;

import com.cedar.cp.api.authenticationpolicy.AuthenticationPolicy;
import com.cedar.cp.api.base.BusinessEditor;
import com.cedar.cp.api.base.ValidationException;

public interface AuthenticationPolicyEditor extends BusinessEditor {

   void setAuthenticationTechnique(int var1) throws ValidationException;

   void setSecurityAdministrator(int var1) throws ValidationException;

   void setMinimumPasswordLength(int var1) throws ValidationException;

   void setMinimumAlphas(int var1) throws ValidationException;

   void setMinimumDigits(int var1) throws ValidationException;

   void setMaximumRepetition(int var1) throws ValidationException;

   void setMinimumChanges(int var1) throws ValidationException;

   void setPasswordUseridDiffer(boolean var1) throws ValidationException;

   void setPasswordReuseDelta(int var1) throws ValidationException;

   void setMaximumLogonAttempts(int var1) throws ValidationException;

   void setPasswordExpiry(int var1) throws ValidationException;

   void setSecurityLog(int var1) throws ValidationException;

   void setActive(boolean var1) throws ValidationException;

   void setNtlmLogLevel(int var1) throws ValidationException;

   void setVisId(String var1) throws ValidationException;

   void setDescription(String var1) throws ValidationException;

   void setPasswordMask(String var1) throws ValidationException;

   void setJaasEntryName(String var1) throws ValidationException;

   void setCosignConfigurationFile(String var1) throws ValidationException;

   void setNtlmNetbiosWins(String var1) throws ValidationException;

   void setNtlmDomain(String var1) throws ValidationException;

   void setNtlmDomainController(String var1) throws ValidationException;

   AuthenticationPolicy getAuthenticationPolicy();
}
