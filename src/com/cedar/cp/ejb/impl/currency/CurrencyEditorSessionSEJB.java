// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:08:13
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.impl.currency;

import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.base.VersionValidationException;
import com.cedar.cp.dto.base.EntityEventMessage;
import com.cedar.cp.dto.base.PrimaryKey;
import com.cedar.cp.dto.currency.CurrencyEditorSessionCSO;
import com.cedar.cp.dto.currency.CurrencyEditorSessionSSO;
import com.cedar.cp.dto.currency.CurrencyImpl;
import com.cedar.cp.dto.currency.CurrencyPK;
import com.cedar.cp.ejb.impl.base.AbstractSession;
import com.cedar.cp.ejb.impl.currency.CurrencyAccessor;
import com.cedar.cp.ejb.impl.currency.CurrencyEVO;
import com.cedar.cp.util.Log;
import com.cedar.cp.util.Timer;
import com.cedar.cp.util.common.JmsConnectionImpl;
import javax.ejb.EJBException;
import javax.ejb.SessionContext;

public class CurrencyEditorSessionSEJB extends AbstractSession {

   private static final String DEPENDANTS_FOR_GET_ITEM_DATA = "";
   private static final String DEPENDANTS_FOR_INSERT = "";
   private static final String DEPENDANTS_FOR_COPY = "";
   private static final String DEPENDANTS_FOR_UPDATE = "";
   private static final String DEPENDANTS_FOR_DELETE = "";
   private transient Log mLog = new Log(this.getClass());
   private transient SessionContext mSessionContext;
   private transient CurrencyAccessor mCurrencyAccessor;
   private CurrencyEditorSessionSSO mSSO;
   private CurrencyPK mThisTableKey;
   private CurrencyEVO mCurrencyEVO;


   public CurrencyEditorSessionSSO getItemData(int userId, Object paramKey) throws ValidationException, EJBException {
      this.setUserId(userId);
      if(this.mLog.isDebugEnabled()) {
         this.mLog.debug("getItemData", paramKey);
      }

      Timer timer = this.mLog.isInfoEnabled()?new Timer(this.mLog):null;
      this.mThisTableKey = (CurrencyPK)paramKey;

      CurrencyEditorSessionSSO e;
      try {
         this.mCurrencyEVO = this.getCurrencyAccessor().getDetails(this.mThisTableKey, "");
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
      this.mSSO = new CurrencyEditorSessionSSO();
      CurrencyImpl editorData = this.buildCurrencyEditData(this.mThisTableKey);
      this.completeGetItemData(editorData);
      this.mSSO.setEditorData(editorData);
   }

   private void completeGetItemData(CurrencyImpl editorData) throws Exception {}

   private CurrencyImpl buildCurrencyEditData(Object thisKey) throws Exception {
      CurrencyImpl editorData = new CurrencyImpl(thisKey);
      editorData.setVisId(this.mCurrencyEVO.getVisId());
      editorData.setDescription(this.mCurrencyEVO.getDescription());
      editorData.setVersionNum(this.mCurrencyEVO.getVersionNum());
      this.completeCurrencyEditData(editorData);
      return editorData;
   }

   private void completeCurrencyEditData(CurrencyImpl editorData) throws Exception {}

   public CurrencyEditorSessionSSO getNewItemData(int userId) throws EJBException {
      this.mLog.debug("getNewItemData");
      this.setUserId(userId);
      Timer timer = this.mLog.isInfoEnabled()?new Timer(this.mLog):null;

      CurrencyEditorSessionSSO var4;
      try {
         this.mSSO = new CurrencyEditorSessionSSO();
         CurrencyImpl e = new CurrencyImpl((Object)null);
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

   private void completeGetNewItemData(CurrencyImpl editorData) throws Exception {}

   public CurrencyPK insert(CurrencyEditorSessionCSO cso) throws ValidationException, EJBException {
      this.mLog.debug("insert");
      Timer timer = this.mLog.isInfoEnabled()?new Timer(this.mLog):null;
      this.setUserId(cso.getUserId());
      CurrencyImpl editorData = cso.getEditorData();

      CurrencyPK e;
      try {
         this.mCurrencyEVO = new CurrencyEVO();
         this.mCurrencyEVO.setVisId(editorData.getVisId());
         this.mCurrencyEVO.setDescription(editorData.getDescription());
         this.updateCurrencyRelationships(editorData);
         this.completeInsertSetup(editorData);
         this.validateInsert();
         this.mCurrencyEVO = this.getCurrencyAccessor().create(this.mCurrencyEVO);
         this.insertIntoAdditionalTables(editorData, true);
         this.sendEntityEventMessage("Currency", this.mCurrencyEVO.getPK(), 1);
         e = this.mCurrencyEVO.getPK();
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

   private void updateCurrencyRelationships(CurrencyImpl editorData) throws ValidationException {}

   private void completeInsertSetup(CurrencyImpl editorData) throws Exception {}

   private void insertIntoAdditionalTables(CurrencyImpl editorData, boolean isInsert) throws Exception {}

   private void validateInsert() throws ValidationException {}

   public CurrencyPK copy(CurrencyEditorSessionCSO cso) throws ValidationException, EJBException {
      this.mLog.debug("copy");
      Timer timer = this.mLog.isInfoEnabled()?new Timer(this.mLog):null;
      this.setUserId(cso.getUserId());
      CurrencyImpl editorData = cso.getEditorData();
      this.mThisTableKey = (CurrencyPK)editorData.getPrimaryKey();

      CurrencyPK var5;
      try {
         CurrencyEVO e = this.getCurrencyAccessor().getDetails(this.mThisTableKey, "");
         this.mCurrencyEVO = e.deepClone();
         this.mCurrencyEVO.setVisId(editorData.getVisId());
         this.mCurrencyEVO.setDescription(editorData.getDescription());
         this.mCurrencyEVO.setVersionNum(0);
         this.updateCurrencyRelationships(editorData);
         this.completeCopySetup(editorData);
         this.validateCopy();
         this.mCurrencyEVO.prepareForInsert();
         this.mCurrencyEVO = this.getCurrencyAccessor().create(this.mCurrencyEVO);
         this.mThisTableKey = this.mCurrencyEVO.getPK();
         this.insertIntoAdditionalTables(editorData, false);
         this.sendEntityEventMessage("Currency", this.mCurrencyEVO.getPK(), 1);
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

   private void completeCopySetup(CurrencyImpl editorData) throws Exception {}

   public void update(CurrencyEditorSessionCSO cso) throws ValidationException, EJBException {
      this.mLog.debug("update");
      Timer timer = this.mLog.isInfoEnabled()?new Timer(this.mLog):null;
      this.setUserId(cso.getUserId());
      CurrencyImpl editorData = cso.getEditorData();
      this.mThisTableKey = (CurrencyPK)editorData.getPrimaryKey();

      try {
         this.mCurrencyEVO = this.getCurrencyAccessor().getDetails(this.mThisTableKey, "");
         this.preValidateUpdate(editorData);
         this.mCurrencyEVO.setVisId(editorData.getVisId());
         this.mCurrencyEVO.setDescription(editorData.getDescription());
         if(editorData.getVersionNum() != this.mCurrencyEVO.getVersionNum()) {
            throw new VersionValidationException(this.mThisTableKey + " expected:" + editorData.getVersionNum() + " found:" + this.mCurrencyEVO.getVersionNum());
         }

         this.updateCurrencyRelationships(editorData);
         this.completeUpdateSetup(editorData);
         this.postValidateUpdate();
         this.getCurrencyAccessor().setDetails(this.mCurrencyEVO);
         this.updateAdditionalTables(editorData);
         this.sendEntityEventMessage("Currency", this.mCurrencyEVO.getPK(), 3);
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

   private void preValidateUpdate(CurrencyImpl editorData) throws ValidationException {}

   private void postValidateUpdate() throws ValidationException {}

   private void completeUpdateSetup(CurrencyImpl editorData) throws Exception {}

   private void updateAdditionalTables(CurrencyImpl editorData) throws Exception {}

   public void delete(int userId, Object paramKey) throws ValidationException, EJBException {
      if(this.mLog.isDebugEnabled()) {
         this.mLog.debug("delete", paramKey);
      }

      Timer timer = this.mLog.isInfoEnabled()?new Timer(this.mLog):null;
      this.setUserId(userId);
      this.mThisTableKey = (CurrencyPK)paramKey;

      try {
         this.mCurrencyEVO = this.getCurrencyAccessor().getDetails(this.mThisTableKey, "");
         this.validateDelete();
         this.deleteDataFromOtherTables();
         this.mCurrencyAccessor.remove(this.mThisTableKey);
         this.sendEntityEventMessage("Currency", this.mThisTableKey, 2);
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

   private CurrencyAccessor getCurrencyAccessor() throws Exception {
      if(this.mCurrencyAccessor == null) {
         this.mCurrencyAccessor = new CurrencyAccessor(this.getInitialContext());
      }

      return this.mCurrencyAccessor;
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
