// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:27:04
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.impl.passwordhistory;

import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.base.VersionValidationException;
import com.cedar.cp.dto.base.EntityEventMessage;
import com.cedar.cp.dto.base.PrimaryKey;
import com.cedar.cp.dto.passwordhistory.PasswordHistoryEditorSessionCSO;
import com.cedar.cp.dto.passwordhistory.PasswordHistoryEditorSessionSSO;
import com.cedar.cp.dto.passwordhistory.PasswordHistoryImpl;
import com.cedar.cp.dto.passwordhistory.PasswordHistoryPK;
import com.cedar.cp.ejb.impl.base.AbstractSession;
import com.cedar.cp.ejb.impl.passwordhistory.PasswordHistoryAccessor;
import com.cedar.cp.ejb.impl.passwordhistory.PasswordHistoryEVO;
import com.cedar.cp.util.Log;
import com.cedar.cp.util.Timer;
import com.cedar.cp.util.common.JmsConnectionImpl;
import javax.ejb.EJBException;
import javax.ejb.SessionContext;

public class PasswordHistoryEditorSessionSEJB extends AbstractSession {

   private static final String DEPENDANTS_FOR_GET_ITEM_DATA = "";
   private static final String DEPENDANTS_FOR_INSERT = "";
   private static final String DEPENDANTS_FOR_COPY = "";
   private static final String DEPENDANTS_FOR_UPDATE = "";
   private static final String DEPENDANTS_FOR_DELETE = "";
   private transient Log mLog = new Log(this.getClass());
   private transient SessionContext mSessionContext;
   private transient PasswordHistoryAccessor mPasswordHistoryAccessor;
   private PasswordHistoryEditorSessionSSO mSSO;
   private PasswordHistoryPK mThisTableKey;
   private PasswordHistoryEVO mPasswordHistoryEVO;


   public PasswordHistoryEditorSessionSSO getItemData(int userId, Object paramKey) throws ValidationException, EJBException {
      this.setUserId(userId);
      if(this.mLog.isDebugEnabled()) {
         this.mLog.debug("getItemData", paramKey);
      }

      Timer timer = this.mLog.isInfoEnabled()?new Timer(this.mLog):null;
      this.mThisTableKey = (PasswordHistoryPK)paramKey;

      PasswordHistoryEditorSessionSSO e;
      try {
         this.mPasswordHistoryEVO = this.getPasswordHistoryAccessor().getDetails(this.mThisTableKey, "");
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
      this.mSSO = new PasswordHistoryEditorSessionSSO();
      PasswordHistoryImpl editorData = this.buildPasswordHistoryEditData(this.mThisTableKey);
      this.completeGetItemData(editorData);
      this.mSSO.setEditorData(editorData);
   }

   private void completeGetItemData(PasswordHistoryImpl editorData) throws Exception {}

   private PasswordHistoryImpl buildPasswordHistoryEditData(Object thisKey) throws Exception {
      PasswordHistoryImpl editorData = new PasswordHistoryImpl(thisKey);
      editorData.setUserId(this.mPasswordHistoryEVO.getUserId());
      editorData.setPasswordBytes(this.mPasswordHistoryEVO.getPasswordBytes());
      editorData.setPasswordDate(this.mPasswordHistoryEVO.getPasswordDate());
      editorData.setVersionNum(this.mPasswordHistoryEVO.getVersionNum());
      this.completePasswordHistoryEditData(editorData);
      return editorData;
   }

   private void completePasswordHistoryEditData(PasswordHistoryImpl editorData) throws Exception {}

   public PasswordHistoryEditorSessionSSO getNewItemData(int userId) throws EJBException {
      this.mLog.debug("getNewItemData");
      this.setUserId(userId);
      Timer timer = this.mLog.isInfoEnabled()?new Timer(this.mLog):null;

      PasswordHistoryEditorSessionSSO var4;
      try {
         this.mSSO = new PasswordHistoryEditorSessionSSO();
         PasswordHistoryImpl e = new PasswordHistoryImpl((Object)null);
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

   private void completeGetNewItemData(PasswordHistoryImpl editorData) throws Exception {}

   public PasswordHistoryPK insert(PasswordHistoryEditorSessionCSO cso) throws ValidationException, EJBException {
      this.mLog.debug("insert");
      Timer timer = this.mLog.isInfoEnabled()?new Timer(this.mLog):null;
      this.setUserId(cso.getUserId());
      PasswordHistoryImpl editorData = cso.getEditorData();

      PasswordHistoryPK e;
      try {
         this.mPasswordHistoryEVO = new PasswordHistoryEVO();
         this.mPasswordHistoryEVO.setUserId(editorData.getUserId());
         this.mPasswordHistoryEVO.setPasswordBytes(editorData.getPasswordBytes());
         this.mPasswordHistoryEVO.setPasswordDate(editorData.getPasswordDate());
         this.updatePasswordHistoryRelationships(editorData);
         this.completeInsertSetup(editorData);
         this.validateInsert();
         this.mPasswordHistoryEVO = this.getPasswordHistoryAccessor().create(this.mPasswordHistoryEVO);
         this.insertIntoAdditionalTables(editorData, true);
         this.sendEntityEventMessage("PasswordHistory", this.mPasswordHistoryEVO.getPK(), 1);
         e = this.mPasswordHistoryEVO.getPK();
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

   private void updatePasswordHistoryRelationships(PasswordHistoryImpl editorData) throws ValidationException {}

   private void completeInsertSetup(PasswordHistoryImpl editorData) throws Exception {}

   private void insertIntoAdditionalTables(PasswordHistoryImpl editorData, boolean isInsert) throws Exception {}

   private void validateInsert() throws ValidationException {}

   public PasswordHistoryPK copy(PasswordHistoryEditorSessionCSO cso) throws ValidationException, EJBException {
      this.mLog.debug("copy");
      Timer timer = this.mLog.isInfoEnabled()?new Timer(this.mLog):null;
      this.setUserId(cso.getUserId());
      PasswordHistoryImpl editorData = cso.getEditorData();
      this.mThisTableKey = (PasswordHistoryPK)editorData.getPrimaryKey();

      PasswordHistoryPK var5;
      try {
         PasswordHistoryEVO e = this.getPasswordHistoryAccessor().getDetails(this.mThisTableKey, "");
         this.mPasswordHistoryEVO = e.deepClone();
         this.mPasswordHistoryEVO.setUserId(editorData.getUserId());
         this.mPasswordHistoryEVO.setPasswordBytes(editorData.getPasswordBytes());
         this.mPasswordHistoryEVO.setPasswordDate(editorData.getPasswordDate());
         this.mPasswordHistoryEVO.setVersionNum(0);
         this.updatePasswordHistoryRelationships(editorData);
         this.completeCopySetup(editorData);
         this.validateCopy();
         this.mPasswordHistoryEVO.prepareForInsert();
         this.mPasswordHistoryEVO = this.getPasswordHistoryAccessor().create(this.mPasswordHistoryEVO);
         this.mThisTableKey = this.mPasswordHistoryEVO.getPK();
         this.insertIntoAdditionalTables(editorData, false);
         this.sendEntityEventMessage("PasswordHistory", this.mPasswordHistoryEVO.getPK(), 1);
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

   private void completeCopySetup(PasswordHistoryImpl editorData) throws Exception {}

   public void update(PasswordHistoryEditorSessionCSO cso) throws ValidationException, EJBException {
      this.mLog.debug("update");
      Timer timer = this.mLog.isInfoEnabled()?new Timer(this.mLog):null;
      this.setUserId(cso.getUserId());
      PasswordHistoryImpl editorData = cso.getEditorData();
      this.mThisTableKey = (PasswordHistoryPK)editorData.getPrimaryKey();

      try {
         this.mPasswordHistoryEVO = this.getPasswordHistoryAccessor().getDetails(this.mThisTableKey, "");
         this.preValidateUpdate(editorData);
         this.mPasswordHistoryEVO.setUserId(editorData.getUserId());
         this.mPasswordHistoryEVO.setPasswordBytes(editorData.getPasswordBytes());
         this.mPasswordHistoryEVO.setPasswordDate(editorData.getPasswordDate());
         if(editorData.getVersionNum() != this.mPasswordHistoryEVO.getVersionNum()) {
            throw new VersionValidationException(this.mThisTableKey + " expected:" + editorData.getVersionNum() + " found:" + this.mPasswordHistoryEVO.getVersionNum());
         }

         this.updatePasswordHistoryRelationships(editorData);
         this.completeUpdateSetup(editorData);
         this.postValidateUpdate();
         this.getPasswordHistoryAccessor().setDetails(this.mPasswordHistoryEVO);
         this.updateAdditionalTables(editorData);
         this.sendEntityEventMessage("PasswordHistory", this.mPasswordHistoryEVO.getPK(), 3);
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

   private void preValidateUpdate(PasswordHistoryImpl editorData) throws ValidationException {}

   private void postValidateUpdate() throws ValidationException {}

   private void completeUpdateSetup(PasswordHistoryImpl editorData) throws Exception {}

   private void updateAdditionalTables(PasswordHistoryImpl editorData) throws Exception {}

   public void delete(int userId, Object paramKey) throws ValidationException, EJBException {
      if(this.mLog.isDebugEnabled()) {
         this.mLog.debug("delete", paramKey);
      }

      Timer timer = this.mLog.isInfoEnabled()?new Timer(this.mLog):null;
      this.setUserId(userId);
      this.mThisTableKey = (PasswordHistoryPK)paramKey;

      try {
         this.mPasswordHistoryEVO = this.getPasswordHistoryAccessor().getDetails(this.mThisTableKey, "");
         this.validateDelete();
         this.deleteDataFromOtherTables();
         this.mPasswordHistoryAccessor.remove(this.mThisTableKey);
         this.sendEntityEventMessage("PasswordHistory", this.mThisTableKey, 2);
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

   private void validateDelete() throws ValidationException, Exception {}

   public void ejbCreate() throws EJBException {}

   public void ejbRemove() {}

   public void setSessionContext(SessionContext context) {
      this.mSessionContext = context;
   }

   public void ejbActivate() {}

   public void ejbPassivate() {}

   private PasswordHistoryAccessor getPasswordHistoryAccessor() throws Exception {
      if(this.mPasswordHistoryAccessor == null) {
         this.mPasswordHistoryAccessor = new PasswordHistoryAccessor(this.getInitialContext());
      }

      return this.mPasswordHistoryAccessor;
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
}
