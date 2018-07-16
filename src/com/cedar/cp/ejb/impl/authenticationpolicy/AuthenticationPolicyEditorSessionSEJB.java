// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:08:01
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.impl.authenticationpolicy;

import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.base.VersionValidationException;
import com.cedar.cp.dto.authenticationpolicy.AuthenticationPolicyEditorSessionCSO;
import com.cedar.cp.dto.authenticationpolicy.AuthenticationPolicyEditorSessionSSO;
import com.cedar.cp.dto.authenticationpolicy.AuthenticationPolicyImpl;
import com.cedar.cp.dto.authenticationpolicy.AuthenticationPolicyPK;
import com.cedar.cp.dto.base.EntityEventMessage;
import com.cedar.cp.dto.base.PrimaryKey;
import com.cedar.cp.ejb.impl.authenticationpolicy.AuthenticationPolicyAccessor;
import com.cedar.cp.ejb.impl.authenticationpolicy.AuthenticationPolicyEVO;
import com.cedar.cp.ejb.impl.base.AbstractSession;
import com.cedar.cp.util.Log;
import com.cedar.cp.util.Timer;
import com.cedar.cp.util.common.JmsConnectionImpl;
import javax.ejb.EJBException;
import javax.ejb.SessionContext;

public class AuthenticationPolicyEditorSessionSEJB extends AbstractSession {

   private static final String DEPENDANTS_FOR_GET_ITEM_DATA = "";
   private static final String DEPENDANTS_FOR_INSERT = "";
   private static final String DEPENDANTS_FOR_COPY = "";
   private static final String DEPENDANTS_FOR_UPDATE = "";
   private static final String DEPENDANTS_FOR_DELETE = "";
   private transient Log mLog = new Log(this.getClass());
   private transient SessionContext mSessionContext;
   private transient AuthenticationPolicyAccessor mAuthenticationPolicyAccessor;
   private AuthenticationPolicyEditorSessionSSO mSSO;
   private AuthenticationPolicyPK mThisTableKey;
   private AuthenticationPolicyEVO mAuthenticationPolicyEVO;


   public AuthenticationPolicyEditorSessionSSO getItemData(int userId, Object paramKey) throws ValidationException, EJBException {
      this.setUserId(userId);
      if(this.mLog.isDebugEnabled()) {
         this.mLog.debug("getItemData", paramKey);
      }

      Timer timer = this.mLog.isInfoEnabled()?new Timer(this.mLog):null;
      this.mThisTableKey = (AuthenticationPolicyPK)paramKey;

      AuthenticationPolicyEditorSessionSSO e;
      try {
         this.mAuthenticationPolicyEVO = this.getAuthenticationPolicyAccessor().getDetails(this.mThisTableKey, "");
         this.makeItemData();
         e = this.mSSO;
      } catch (ValidationException var10) {
         throw var10;
      } catch (EJBException var11) {
         if(var11.getCause() instanceof ValidationException) {
            throw (ValidationException)var11.getCause();
         }

         throw var11;
      } catch (Exception var12) {
         var12.printStackTrace();
         throw new EJBException(var12.getMessage(), var12);
      } finally {
         this.setUserId(0);
         if(timer != null) {
            timer.logInfo("getItemData", this.mThisTableKey);
         }

      }

      return e;
   }

   private void makeItemData() throws Exception {
      this.mSSO = new AuthenticationPolicyEditorSessionSSO();
      AuthenticationPolicyImpl editorData = this.buildAuthenticationPolicyEditData(this.mThisTableKey);
      this.completeGetItemData(editorData);
      this.mSSO.setEditorData(editorData);
   }

   private void completeGetItemData(AuthenticationPolicyImpl editorData) throws Exception {}

   private AuthenticationPolicyImpl buildAuthenticationPolicyEditData(Object thisKey) throws Exception {
      AuthenticationPolicyImpl editorData = new AuthenticationPolicyImpl(thisKey);
      editorData.setVisId(this.mAuthenticationPolicyEVO.getVisId());
      editorData.setDescription(this.mAuthenticationPolicyEVO.getDescription());
      editorData.setAuthenticationTechnique(this.mAuthenticationPolicyEVO.getAuthenticationTechnique());
      editorData.setSecurityAdministrator(this.mAuthenticationPolicyEVO.getSecurityAdministrator());
      editorData.setMinimumPasswordLength(this.mAuthenticationPolicyEVO.getMinimumPasswordLength());
      editorData.setMinimumAlphas(this.mAuthenticationPolicyEVO.getMinimumAlphas());
      editorData.setMinimumDigits(this.mAuthenticationPolicyEVO.getMinimumDigits());
      editorData.setMaximumRepetition(this.mAuthenticationPolicyEVO.getMaximumRepetition());
      editorData.setMinimumChanges(this.mAuthenticationPolicyEVO.getMinimumChanges());
      editorData.setPasswordUseridDiffer(this.mAuthenticationPolicyEVO.getPasswordUseridDiffer());
      editorData.setPasswordMask(this.mAuthenticationPolicyEVO.getPasswordMask());
      editorData.setPasswordReuseDelta(this.mAuthenticationPolicyEVO.getPasswordReuseDelta());
      editorData.setMaximumLogonAttempts(this.mAuthenticationPolicyEVO.getMaximumLogonAttempts());
      editorData.setPasswordExpiry(this.mAuthenticationPolicyEVO.getPasswordExpiry());
      editorData.setSecurityLog(this.mAuthenticationPolicyEVO.getSecurityLog());
      editorData.setActive(this.mAuthenticationPolicyEVO.getActive());
      editorData.setJaasEntryName(this.mAuthenticationPolicyEVO.getJaasEntryName());
      editorData.setCosignConfigurationFile(this.mAuthenticationPolicyEVO.getCosignConfigurationFile());
      editorData.setNtlmNetbiosWins(this.mAuthenticationPolicyEVO.getNtlmNetbiosWins());
      editorData.setNtlmDomain(this.mAuthenticationPolicyEVO.getNtlmDomain());
      editorData.setNtlmDomainController(this.mAuthenticationPolicyEVO.getNtlmDomainController());
      editorData.setNtlmLogLevel(this.mAuthenticationPolicyEVO.getNtlmLogLevel());
      editorData.setVersionNum(this.mAuthenticationPolicyEVO.getVersionNum());
      this.completeAuthenticationPolicyEditData(editorData);
      return editorData;
   }

   private void completeAuthenticationPolicyEditData(AuthenticationPolicyImpl editorData) throws Exception {}

   public AuthenticationPolicyEditorSessionSSO getNewItemData(int userId) throws EJBException {
      this.mLog.debug("getNewItemData");
      this.setUserId(userId);
      Timer timer = this.mLog.isInfoEnabled()?new Timer(this.mLog):null;

      AuthenticationPolicyEditorSessionSSO var4;
      try {
         this.mSSO = new AuthenticationPolicyEditorSessionSSO();
         AuthenticationPolicyImpl e = new AuthenticationPolicyImpl((Object)null);
         this.completeGetNewItemData(e);
         this.mSSO.setEditorData(e);
         var4 = this.mSSO;
      } catch (EJBException var9) {
         throw var9;
      } catch (Exception var10) {
         var10.printStackTrace();
         throw new EJBException(var10.getMessage(), var10);
      } finally {
         this.setUserId(0);
         if(timer != null) {
            timer.logInfo("getNewItemData", "");
         }

      }

      return var4;
   }

   private void completeGetNewItemData(AuthenticationPolicyImpl editorData) throws Exception {}

   public AuthenticationPolicyPK insert(AuthenticationPolicyEditorSessionCSO cso) throws ValidationException, EJBException {
      this.mLog.debug("insert");
      Timer timer = this.mLog.isInfoEnabled()?new Timer(this.mLog):null;
      this.setUserId(cso.getUserId());
      AuthenticationPolicyImpl editorData = cso.getEditorData();

      AuthenticationPolicyPK e;
      try {
         this.preValidateUpdate(editorData);
         this.mAuthenticationPolicyEVO = new AuthenticationPolicyEVO();
         this.mAuthenticationPolicyEVO.setVisId(editorData.getVisId());
         this.mAuthenticationPolicyEVO.setDescription(editorData.getDescription());
         this.mAuthenticationPolicyEVO.setAuthenticationTechnique(editorData.getAuthenticationTechnique());
         this.mAuthenticationPolicyEVO.setSecurityAdministrator(editorData.getSecurityAdministrator());
         this.mAuthenticationPolicyEVO.setMinimumPasswordLength(editorData.getMinimumPasswordLength());
         this.mAuthenticationPolicyEVO.setMinimumAlphas(editorData.getMinimumAlphas());
         this.mAuthenticationPolicyEVO.setMinimumDigits(editorData.getMinimumDigits());
         this.mAuthenticationPolicyEVO.setMaximumRepetition(editorData.getMaximumRepetition());
         this.mAuthenticationPolicyEVO.setMinimumChanges(editorData.getMinimumChanges());
         this.mAuthenticationPolicyEVO.setPasswordUseridDiffer(editorData.isPasswordUseridDiffer());
         this.mAuthenticationPolicyEVO.setPasswordMask(editorData.getPasswordMask());
         this.mAuthenticationPolicyEVO.setPasswordReuseDelta(editorData.getPasswordReuseDelta());
         this.mAuthenticationPolicyEVO.setMaximumLogonAttempts(editorData.getMaximumLogonAttempts());
         this.mAuthenticationPolicyEVO.setPasswordExpiry(editorData.getPasswordExpiry());
         this.mAuthenticationPolicyEVO.setSecurityLog(editorData.getSecurityLog());
         this.mAuthenticationPolicyEVO.setActive(editorData.isActive());
         this.mAuthenticationPolicyEVO.setJaasEntryName(editorData.getJaasEntryName());
         this.mAuthenticationPolicyEVO.setCosignConfigurationFile(editorData.getCosignConfigurationFile());
         this.mAuthenticationPolicyEVO.setNtlmNetbiosWins(editorData.getNtlmNetbiosWins());
         this.mAuthenticationPolicyEVO.setNtlmDomain(editorData.getNtlmDomain());
         this.mAuthenticationPolicyEVO.setNtlmDomainController(editorData.getNtlmDomainController());
         this.mAuthenticationPolicyEVO.setNtlmLogLevel(editorData.getNtlmLogLevel());
         this.updateAuthenticationPolicyRelationships(editorData);
         this.completeInsertSetup(editorData);
         this.validateInsert();
         this.mAuthenticationPolicyEVO = this.getAuthenticationPolicyAccessor().create(this.mAuthenticationPolicyEVO);
         this.insertIntoAdditionalTables(editorData, true);
         this.sendEntityEventMessage("AuthenticationPolicy", this.mAuthenticationPolicyEVO.getPK(), 1);
         e = this.mAuthenticationPolicyEVO.getPK();
      } catch (ValidationException var10) {
         throw new EJBException(var10);
      } catch (EJBException var11) {
         throw var11;
      } catch (Exception var12) {
         var12.printStackTrace();
         throw new EJBException(var12.getMessage(), var12);
      } finally {
         this.setUserId(0);
         if(timer != null) {
            timer.logInfo("insert", "");
         }

      }

      return e;
   }

   private void updateAuthenticationPolicyRelationships(AuthenticationPolicyImpl editorData) throws ValidationException {}

   private void completeInsertSetup(AuthenticationPolicyImpl editorData) throws Exception {}

   private void insertIntoAdditionalTables(AuthenticationPolicyImpl editorData, boolean isInsert) throws Exception {}

   private void validateInsert() throws ValidationException {}

   public AuthenticationPolicyPK copy(AuthenticationPolicyEditorSessionCSO cso) throws ValidationException, EJBException {
      this.mLog.debug("copy");
      Timer timer = this.mLog.isInfoEnabled()?new Timer(this.mLog):null;
      this.setUserId(cso.getUserId());
      AuthenticationPolicyImpl editorData = cso.getEditorData();
      this.mThisTableKey = (AuthenticationPolicyPK)editorData.getPrimaryKey();

      AuthenticationPolicyPK var5;
      try {
         AuthenticationPolicyEVO e = this.getAuthenticationPolicyAccessor().getDetails(this.mThisTableKey, "");
         this.mAuthenticationPolicyEVO = e.deepClone();
         this.mAuthenticationPolicyEVO.setVisId(editorData.getVisId());
         this.mAuthenticationPolicyEVO.setDescription(editorData.getDescription());
         this.mAuthenticationPolicyEVO.setAuthenticationTechnique(editorData.getAuthenticationTechnique());
         this.mAuthenticationPolicyEVO.setSecurityAdministrator(editorData.getSecurityAdministrator());
         this.mAuthenticationPolicyEVO.setMinimumPasswordLength(editorData.getMinimumPasswordLength());
         this.mAuthenticationPolicyEVO.setMinimumAlphas(editorData.getMinimumAlphas());
         this.mAuthenticationPolicyEVO.setMinimumDigits(editorData.getMinimumDigits());
         this.mAuthenticationPolicyEVO.setMaximumRepetition(editorData.getMaximumRepetition());
         this.mAuthenticationPolicyEVO.setMinimumChanges(editorData.getMinimumChanges());
         this.mAuthenticationPolicyEVO.setPasswordUseridDiffer(editorData.isPasswordUseridDiffer());
         this.mAuthenticationPolicyEVO.setPasswordMask(editorData.getPasswordMask());
         this.mAuthenticationPolicyEVO.setPasswordReuseDelta(editorData.getPasswordReuseDelta());
         this.mAuthenticationPolicyEVO.setMaximumLogonAttempts(editorData.getMaximumLogonAttempts());
         this.mAuthenticationPolicyEVO.setPasswordExpiry(editorData.getPasswordExpiry());
         this.mAuthenticationPolicyEVO.setSecurityLog(editorData.getSecurityLog());
         this.mAuthenticationPolicyEVO.setActive(editorData.isActive());
         this.mAuthenticationPolicyEVO.setJaasEntryName(editorData.getJaasEntryName());
         this.mAuthenticationPolicyEVO.setCosignConfigurationFile(editorData.getCosignConfigurationFile());
         this.mAuthenticationPolicyEVO.setNtlmNetbiosWins(editorData.getNtlmNetbiosWins());
         this.mAuthenticationPolicyEVO.setNtlmDomain(editorData.getNtlmDomain());
         this.mAuthenticationPolicyEVO.setNtlmDomainController(editorData.getNtlmDomainController());
         this.mAuthenticationPolicyEVO.setNtlmLogLevel(editorData.getNtlmLogLevel());
         this.mAuthenticationPolicyEVO.setVersionNum(0);
         this.updateAuthenticationPolicyRelationships(editorData);
         this.completeCopySetup(editorData);
         this.validateCopy();
         this.mAuthenticationPolicyEVO.prepareForInsert();
         this.mAuthenticationPolicyEVO = this.getAuthenticationPolicyAccessor().create(this.mAuthenticationPolicyEVO);
         this.mThisTableKey = this.mAuthenticationPolicyEVO.getPK();
         this.insertIntoAdditionalTables(editorData, false);
         this.sendEntityEventMessage("AuthenticationPolicy", this.mAuthenticationPolicyEVO.getPK(), 1);
         var5 = this.mThisTableKey;
      } catch (ValidationException var11) {
         throw new EJBException(var11);
      } catch (EJBException var12) {
         throw var12;
      } catch (Exception var13) {
         var13.printStackTrace();
         throw new EJBException(var13);
      } finally {
         this.setUserId(0);
         if(timer != null) {
            timer.logInfo("copy", this.mThisTableKey);
         }

      }

      return var5;
   }

   private void validateCopy() throws ValidationException {}

   private void completeCopySetup(AuthenticationPolicyImpl editorData) throws Exception {}

   public void update(AuthenticationPolicyEditorSessionCSO cso) throws ValidationException, EJBException {
      this.mLog.debug("update");
      Timer timer = this.mLog.isInfoEnabled()?new Timer(this.mLog):null;
      this.setUserId(cso.getUserId());
      AuthenticationPolicyImpl editorData = cso.getEditorData();
      this.mThisTableKey = (AuthenticationPolicyPK)editorData.getPrimaryKey();

      try {
         this.mAuthenticationPolicyEVO = this.getAuthenticationPolicyAccessor().getDetails(this.mThisTableKey, "");
         this.preValidateUpdate(editorData);
         this.mAuthenticationPolicyEVO.setVisId(editorData.getVisId());
         this.mAuthenticationPolicyEVO.setDescription(editorData.getDescription());
         this.mAuthenticationPolicyEVO.setAuthenticationTechnique(editorData.getAuthenticationTechnique());
         this.mAuthenticationPolicyEVO.setSecurityAdministrator(editorData.getSecurityAdministrator());
         this.mAuthenticationPolicyEVO.setMinimumPasswordLength(editorData.getMinimumPasswordLength());
         this.mAuthenticationPolicyEVO.setMinimumAlphas(editorData.getMinimumAlphas());
         this.mAuthenticationPolicyEVO.setMinimumDigits(editorData.getMinimumDigits());
         this.mAuthenticationPolicyEVO.setMaximumRepetition(editorData.getMaximumRepetition());
         this.mAuthenticationPolicyEVO.setMinimumChanges(editorData.getMinimumChanges());
         this.mAuthenticationPolicyEVO.setPasswordUseridDiffer(editorData.isPasswordUseridDiffer());
         this.mAuthenticationPolicyEVO.setPasswordMask(editorData.getPasswordMask());
         this.mAuthenticationPolicyEVO.setPasswordReuseDelta(editorData.getPasswordReuseDelta());
         this.mAuthenticationPolicyEVO.setMaximumLogonAttempts(editorData.getMaximumLogonAttempts());
         this.mAuthenticationPolicyEVO.setPasswordExpiry(editorData.getPasswordExpiry());
         this.mAuthenticationPolicyEVO.setSecurityLog(editorData.getSecurityLog());
         this.mAuthenticationPolicyEVO.setActive(editorData.isActive());
         this.mAuthenticationPolicyEVO.setJaasEntryName(editorData.getJaasEntryName());
         this.mAuthenticationPolicyEVO.setCosignConfigurationFile(editorData.getCosignConfigurationFile());
         this.mAuthenticationPolicyEVO.setNtlmNetbiosWins(editorData.getNtlmNetbiosWins());
         this.mAuthenticationPolicyEVO.setNtlmDomain(editorData.getNtlmDomain());
         this.mAuthenticationPolicyEVO.setNtlmDomainController(editorData.getNtlmDomainController());
         this.mAuthenticationPolicyEVO.setNtlmLogLevel(editorData.getNtlmLogLevel());
         if(editorData.getVersionNum() != this.mAuthenticationPolicyEVO.getVersionNum()) {
            //throw new VersionValidationException(this.mThisTableKey + " expected:" + editorData.getVersionNum() + " found:" + this.mAuthenticationPolicyEVO.getVersionNum());
            throw new VersionValidationException("Version update failure. The entity you have been editing has been updated by another user.");
         }

         this.updateAuthenticationPolicyRelationships(editorData);
         this.completeUpdateSetup(editorData);
         this.postValidateUpdate();
         this.getAuthenticationPolicyAccessor().setDetails(this.mAuthenticationPolicyEVO);
         this.updateAdditionalTables(editorData);
         this.sendEntityEventMessage("AuthenticationPolicy", this.mAuthenticationPolicyEVO.getPK(), 3);
      } catch (ValidationException var10) {
         throw new EJBException(var10);
      } catch (EJBException var11) {
         throw var11;
      } catch (Exception var12) {
         var12.printStackTrace();
         throw new EJBException(var12.getMessage(), var12);
      } finally {
         this.setUserId(0);
         if(timer != null) {
            timer.logInfo("update", this.mThisTableKey);
         }

      }

   }

   private void preValidateUpdate(AuthenticationPolicyImpl editorData) throws ValidationException {
      if(editorData.getAuthenticationTechnique() == 1) {
         int minimumPasswordLength = editorData.getMinimumPasswordLength();
         if(minimumPasswordLength < 0) {
            throw new ValidationException("Password length must be greater than 0 ");
         }

         int minimumAlphas = editorData.getMinimumAlphas();
         if(minimumAlphas < 0) {
            throw new ValidationException("Number of alphas must be greater than 0 ");
         }

         int minimumDigits = editorData.getMinimumDigits();
         if(minimumDigits < 0) {
            throw new ValidationException("Number of digits must be greater than 0 ");
         }

         int pRepeation = editorData.getMaximumRepetition();
         if(pRepeation < 0) {
            throw new ValidationException("Number of repeat character must not be less than 0 ");
         }

         int pChange = editorData.getMinimumChanges();
         if(pChange < 0) {
            throw new ValidationException("Number of password change must not be less than 0 ");
         }

         String pMask = editorData.getPasswordMask();
         int pReuse;
         int pLogonAttempt;
         if(pMask != null && pMask.length() > 0) {
            if(!pMask.matches("(A|X|N|a|x|n)*")) {
               throw new ValidationException("Password mask syntax error ");
            }

            if(pMask.length() < minimumPasswordLength) {
               throw new ValidationException("Password mask must be greater or equal to minimum password length");
            }

            if(pChange > pMask.length()) {
               throw new ValidationException("Minimum Password Changes must be less than Password Mask\'s length");
            }

            pReuse = this.countInPasswordMask(pMask, "A");
            if(pReuse < minimumAlphas) {
               throw new ValidationException("Number of Alphas in Password Mask (i.e.: A) must be greater or equal Minimum Alphas");
            }

            pLogonAttempt = this.countInPasswordMask(pMask, "N");
            if(pLogonAttempt < minimumDigits) {
               throw new ValidationException("Number of Digits in Password Mask (i.e.: N) must be greater or equal Minimum Digits");
            }
         }

         pReuse = editorData.getPasswordReuseDelta();
         if(pReuse < 0) {
            //throw new ValidationException("Number of password changes must not be less than 0 ");
            throw new ValidationException("Count of password reuse must not be less than 0  ");
         }

         pLogonAttempt = editorData.getMaximumLogonAttempts();
         if(pLogonAttempt < 0) {
            throw new ValidationException("Number of login attempts must not be less than 0 ");
         }
      }

   }

   private void postValidateUpdate() throws ValidationException {}

   private void completeUpdateSetup(AuthenticationPolicyImpl editorData) throws Exception {}

   private void updateAdditionalTables(AuthenticationPolicyImpl editorData) throws Exception {}

   public void delete(int userId, Object paramKey) throws ValidationException, EJBException {
      if(this.mLog.isDebugEnabled()) {
         this.mLog.debug("delete", paramKey);
      }

      Timer timer = this.mLog.isInfoEnabled()?new Timer(this.mLog):null;
      this.setUserId(userId);
      this.mThisTableKey = (AuthenticationPolicyPK)paramKey;

      try {
         this.mAuthenticationPolicyEVO = this.getAuthenticationPolicyAccessor().getDetails(this.mThisTableKey, "");
         this.validateDelete();
         this.deleteDataFromOtherTables();
         this.mAuthenticationPolicyAccessor.remove(this.mThisTableKey);
         this.sendEntityEventMessage("AuthenticationPolicy", this.mThisTableKey, 2);
      } catch (ValidationException var10) {
         throw var10;
      } catch (EJBException var11) {
         throw var11;
      } catch (Exception var12) {
         var12.printStackTrace();
         throw new EJBException(var12.getMessage(), var12);
      } finally {
         this.setUserId(0);
         if(timer != null) {
            timer.logInfo("delete", this.mThisTableKey);
         }

      }

   }

   private void deleteDataFromOtherTables() throws Exception {}

   private void validateDelete() throws ValidationException, Exception {
      if(this.mAuthenticationPolicyEVO.getActive()) {
         throw new ValidationException("Cannot delete active Authentication Policy");
      }
   }

   public void ejbCreate() throws EJBException {}

   public void ejbRemove() {}

   public void setSessionContext(SessionContext context) {
      this.mSessionContext = context;
   }

   public void ejbActivate() {}

   public void ejbPassivate() {}

   private AuthenticationPolicyAccessor getAuthenticationPolicyAccessor() throws Exception {
      if(this.mAuthenticationPolicyAccessor == null) {
         this.mAuthenticationPolicyAccessor = new AuthenticationPolicyAccessor(this.getInitialContext());
      }

      return this.mAuthenticationPolicyAccessor;
   }

   private void sendEntityEventMessage(String tableName, PrimaryKey pk, int changeType) {
      try {
         JmsConnectionImpl e = new JmsConnectionImpl(this.getInitialContext(), 3, "entityEventTopic");
         e.createSession();
         EntityEventMessage em = new EntityEventMessage(tableName, pk, changeType, this.getClass().getName());
         this.mLog.debug("update", "sending event message: " + em.toString());
         e.send(em);
         e.closeSession();
         e.closeConnection();
      } catch (Exception var6) {
         var6.printStackTrace();
      }

   }

   private int countInPasswordMask(String passwordMask, String charToCount) {
      int count = 0;
      if(passwordMask != null && charToCount != null) {
         for(int i = 0; i < passwordMask.length(); ++i) {
            if(charToCount.equalsIgnoreCase(passwordMask.substring(i, i + 1))) {
               ++count;
            }
         }
      }

      return count;
   }
}
