// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:08:41
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.impl.logonhistory;

import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.base.VersionValidationException;
import com.cedar.cp.dto.base.EntityEventMessage;
import com.cedar.cp.dto.base.PrimaryKey;
import com.cedar.cp.dto.logonhistory.LogonHistoryEditorSessionCSO;
import com.cedar.cp.dto.logonhistory.LogonHistoryEditorSessionSSO;
import com.cedar.cp.dto.logonhistory.LogonHistoryImpl;
import com.cedar.cp.dto.logonhistory.LogonHistoryPK;
import com.cedar.cp.ejb.impl.base.AbstractSession;
import com.cedar.cp.ejb.impl.logonhistory.LogonHistoryAccessor;
import com.cedar.cp.ejb.impl.logonhistory.LogonHistoryEVO;
import com.cedar.cp.util.Log;
import com.cedar.cp.util.Timer;
import com.cedar.cp.util.common.JmsConnectionImpl;
import javax.ejb.EJBException;
import javax.ejb.SessionContext;

public class LogonHistoryEditorSessionSEJB extends AbstractSession {

   private static final String DEPENDANTS_FOR_GET_ITEM_DATA = "";
   private static final String DEPENDANTS_FOR_INSERT = "";
   private static final String DEPENDANTS_FOR_COPY = "";
   private static final String DEPENDANTS_FOR_UPDATE = "";
   private static final String DEPENDANTS_FOR_DELETE = "";
   private transient Log mLog = new Log(this.getClass());
   private transient SessionContext mSessionContext;
   private transient LogonHistoryAccessor mLogonHistoryAccessor;
   private LogonHistoryEditorSessionSSO mSSO;
   private LogonHistoryPK mThisTableKey;
   private LogonHistoryEVO mLogonHistoryEVO;


   public LogonHistoryEditorSessionSSO getItemData(int userId, Object paramKey) throws ValidationException, EJBException {
      this.setUserId(userId);
      if(this.mLog.isDebugEnabled()) {
         this.mLog.debug("getItemData", paramKey);
      }

      Timer timer = this.mLog.isInfoEnabled()?new Timer(this.mLog):null;
      this.mThisTableKey = (LogonHistoryPK)paramKey;

      LogonHistoryEditorSessionSSO e;
      try {
         this.mLogonHistoryEVO = this.getLogonHistoryAccessor().getDetails(this.mThisTableKey, "");
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
      this.mSSO = new LogonHistoryEditorSessionSSO();
      LogonHistoryImpl editorData = this.buildLogonHistoryEditData(this.mThisTableKey);
      this.completeGetItemData(editorData);
      this.mSSO.setEditorData(editorData);
   }

   private void completeGetItemData(LogonHistoryImpl editorData) throws Exception {}

   private LogonHistoryImpl buildLogonHistoryEditData(Object thisKey) throws Exception {
      LogonHistoryImpl editorData = new LogonHistoryImpl(thisKey);
      editorData.setUserName(this.mLogonHistoryEVO.getUserName());
      editorData.setEventDate(this.mLogonHistoryEVO.getEventDate());
      editorData.setEventType(this.mLogonHistoryEVO.getEventType());
      editorData.setVersionNum(this.mLogonHistoryEVO.getVersionNum());
      this.completeLogonHistoryEditData(editorData);
      return editorData;
   }

   private void completeLogonHistoryEditData(LogonHistoryImpl editorData) throws Exception {}

   public LogonHistoryEditorSessionSSO getNewItemData(int userId) throws EJBException {
      this.mLog.debug("getNewItemData");
      this.setUserId(userId);
      Timer timer = this.mLog.isInfoEnabled()?new Timer(this.mLog):null;

      LogonHistoryEditorSessionSSO var4;
      try {
         this.mSSO = new LogonHistoryEditorSessionSSO();
         LogonHistoryImpl e = new LogonHistoryImpl((Object)null);
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

   private void completeGetNewItemData(LogonHistoryImpl editorData) throws Exception {}

   public LogonHistoryPK insert(LogonHistoryEditorSessionCSO cso) throws ValidationException, EJBException {
      this.mLog.debug("insert");
      Timer timer = this.mLog.isInfoEnabled()?new Timer(this.mLog):null;
      this.setUserId(cso.getUserId());
      LogonHistoryImpl editorData = cso.getEditorData();

      LogonHistoryPK e;
      try {
         this.mLogonHistoryEVO = new LogonHistoryEVO();
         this.mLogonHistoryEVO.setUserName(editorData.getUserName());
         this.mLogonHistoryEVO.setEventDate(editorData.getEventDate());
         this.mLogonHistoryEVO.setEventType(editorData.getEventType());
         this.updateLogonHistoryRelationships(editorData);
         this.completeInsertSetup(editorData);
         this.validateInsert();
         this.mLogonHistoryEVO = this.getLogonHistoryAccessor().create(this.mLogonHistoryEVO);
         this.insertIntoAdditionalTables(editorData, true);
         this.sendEntityEventMessage("LogonHistory", this.mLogonHistoryEVO.getPK(), 1);
         e = this.mLogonHistoryEVO.getPK();
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

   private void updateLogonHistoryRelationships(LogonHistoryImpl editorData) throws ValidationException {}

   private void completeInsertSetup(LogonHistoryImpl editorData) throws Exception {}

   private void insertIntoAdditionalTables(LogonHistoryImpl editorData, boolean isInsert) throws Exception {}

   private void validateInsert() throws ValidationException {}

   public LogonHistoryPK copy(LogonHistoryEditorSessionCSO cso) throws ValidationException, EJBException {
      this.mLog.debug("copy");
      Timer timer = this.mLog.isInfoEnabled()?new Timer(this.mLog):null;
      this.setUserId(cso.getUserId());
      LogonHistoryImpl editorData = cso.getEditorData();
      this.mThisTableKey = (LogonHistoryPK)editorData.getPrimaryKey();

      LogonHistoryPK var5;
      try {
         LogonHistoryEVO e = this.getLogonHistoryAccessor().getDetails(this.mThisTableKey, "");
         this.mLogonHistoryEVO = e.deepClone();
         this.mLogonHistoryEVO.setUserName(editorData.getUserName());
         this.mLogonHistoryEVO.setEventDate(editorData.getEventDate());
         this.mLogonHistoryEVO.setEventType(editorData.getEventType());
         this.mLogonHistoryEVO.setVersionNum(0);
         this.updateLogonHistoryRelationships(editorData);
         this.completeCopySetup(editorData);
         this.validateCopy();
         this.mLogonHistoryEVO.prepareForInsert();
         this.mLogonHistoryEVO = this.getLogonHistoryAccessor().create(this.mLogonHistoryEVO);
         this.mThisTableKey = this.mLogonHistoryEVO.getPK();
         this.insertIntoAdditionalTables(editorData, false);
         this.sendEntityEventMessage("LogonHistory", this.mLogonHistoryEVO.getPK(), 1);
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

   private void completeCopySetup(LogonHistoryImpl editorData) throws Exception {}

   public void update(LogonHistoryEditorSessionCSO cso) throws ValidationException, EJBException {
      this.mLog.debug("update");
      Timer timer = this.mLog.isInfoEnabled()?new Timer(this.mLog):null;
      this.setUserId(cso.getUserId());
      LogonHistoryImpl editorData = cso.getEditorData();
      this.mThisTableKey = (LogonHistoryPK)editorData.getPrimaryKey();

      try {
         this.mLogonHistoryEVO = this.getLogonHistoryAccessor().getDetails(this.mThisTableKey, "");
         this.preValidateUpdate(editorData);
         this.mLogonHistoryEVO.setUserName(editorData.getUserName());
         this.mLogonHistoryEVO.setEventDate(editorData.getEventDate());
         this.mLogonHistoryEVO.setEventType(editorData.getEventType());
         if(editorData.getVersionNum() != this.mLogonHistoryEVO.getVersionNum()) {
            throw new VersionValidationException(this.mThisTableKey + " expected:" + editorData.getVersionNum() + " found:" + this.mLogonHistoryEVO.getVersionNum());
         }

         this.updateLogonHistoryRelationships(editorData);
         this.completeUpdateSetup(editorData);
         this.postValidateUpdate();
         this.getLogonHistoryAccessor().setDetails(this.mLogonHistoryEVO);
         this.updateAdditionalTables(editorData);
         this.sendEntityEventMessage("LogonHistory", this.mLogonHistoryEVO.getPK(), 3);
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

   private void preValidateUpdate(LogonHistoryImpl editorData) throws ValidationException {}

   private void postValidateUpdate() throws ValidationException {}

   private void completeUpdateSetup(LogonHistoryImpl editorData) throws Exception {}

   private void updateAdditionalTables(LogonHistoryImpl editorData) throws Exception {}

   public void delete(int userId, Object paramKey) throws ValidationException, EJBException {
      if(this.mLog.isDebugEnabled()) {
         this.mLog.debug("delete", paramKey);
      }

      Timer timer = this.mLog.isInfoEnabled()?new Timer(this.mLog):null;
      this.setUserId(userId);
      this.mThisTableKey = (LogonHistoryPK)paramKey;

      try {
         this.mLogonHistoryEVO = this.getLogonHistoryAccessor().getDetails(this.mThisTableKey, "");
         this.validateDelete();
         this.deleteDataFromOtherTables();
         this.mLogonHistoryAccessor.remove(this.mThisTableKey);
         this.sendEntityEventMessage("LogonHistory", this.mThisTableKey, 2);
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

   private LogonHistoryAccessor getLogonHistoryAccessor() throws Exception {
      if(this.mLogonHistoryAccessor == null) {
         this.mLogonHistoryAccessor = new LogonHistoryAccessor(this.getInitialContext());
      }

      return this.mLogonHistoryAccessor;
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
