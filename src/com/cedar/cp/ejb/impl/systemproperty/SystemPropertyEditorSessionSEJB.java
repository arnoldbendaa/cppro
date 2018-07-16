// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:27:25
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.impl.systemproperty;

import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.base.VersionValidationException;
import com.cedar.cp.dto.base.EntityEventMessage;
import com.cedar.cp.dto.base.PrimaryKey;
import com.cedar.cp.dto.systemproperty.SystemPropertyEditorSessionCSO;
import com.cedar.cp.dto.systemproperty.SystemPropertyEditorSessionSSO;
import com.cedar.cp.dto.systemproperty.SystemPropertyImpl;
import com.cedar.cp.dto.systemproperty.SystemPropertyPK;
import com.cedar.cp.ejb.impl.base.AbstractSession;
import com.cedar.cp.ejb.impl.systemproperty.SystemPropertyAccessor;
import com.cedar.cp.ejb.impl.systemproperty.SystemPropertyEVO;
import com.cedar.cp.util.Log;
import com.cedar.cp.util.Timer;
import com.cedar.cp.util.common.JmsConnectionImpl;
import javax.ejb.EJBException;
import javax.ejb.SessionContext;

public class SystemPropertyEditorSessionSEJB extends AbstractSession {

   private static final String DEPENDANTS_FOR_GET_ITEM_DATA = "";
   private static final String DEPENDANTS_FOR_INSERT = "";
   private static final String DEPENDANTS_FOR_COPY = "";
   private static final String DEPENDANTS_FOR_UPDATE = "";
   private static final String DEPENDANTS_FOR_DELETE = "";
   private transient Log mLog = new Log(this.getClass());
   private transient SessionContext mSessionContext;
   private transient SystemPropertyAccessor mSystemPropertyAccessor;
   private SystemPropertyEditorSessionSSO mSSO;
   private SystemPropertyPK mThisTableKey;
   private SystemPropertyEVO mSystemPropertyEVO;


   public SystemPropertyEditorSessionSSO getItemData(int userId, Object paramKey) throws ValidationException, EJBException {
      this.setUserId(userId);
      if(this.mLog.isDebugEnabled()) {
         this.mLog.debug("getItemData", paramKey);
      }

      Timer timer = this.mLog.isInfoEnabled()?new Timer(this.mLog):null;
      this.mThisTableKey = (SystemPropertyPK)paramKey;

      SystemPropertyEditorSessionSSO e;
      try {
         this.mSystemPropertyEVO = this.getSystemPropertyAccessor().getDetails(this.mThisTableKey, "");
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
      this.mSSO = new SystemPropertyEditorSessionSSO();
      SystemPropertyImpl editorData = this.buildSystemPropertyEditData(this.mThisTableKey);
      this.completeGetItemData(editorData);
      this.mSSO.setEditorData(editorData);
   }

   private void completeGetItemData(SystemPropertyImpl editorData) throws Exception {}

   private SystemPropertyImpl buildSystemPropertyEditData(Object thisKey) throws Exception {
      SystemPropertyImpl editorData = new SystemPropertyImpl(thisKey);
      editorData.setProperty(this.mSystemPropertyEVO.getProperty());
      editorData.setValue(this.mSystemPropertyEVO.getValue());
      editorData.setDescription(this.mSystemPropertyEVO.getDescription());
      editorData.setValidateExp(this.mSystemPropertyEVO.getValidateExp());
      editorData.setValidateTxt(this.mSystemPropertyEVO.getValidateTxt());
      editorData.setVersionNum(this.mSystemPropertyEVO.getVersionNum());
      this.completeSystemPropertyEditData(editorData);
      return editorData;
   }

   private void completeSystemPropertyEditData(SystemPropertyImpl editorData) throws Exception {}

   public SystemPropertyEditorSessionSSO getNewItemData(int userId) throws EJBException {
      this.mLog.debug("getNewItemData");
      this.setUserId(userId);
      Timer timer = this.mLog.isInfoEnabled()?new Timer(this.mLog):null;

      SystemPropertyEditorSessionSSO var4;
      try {
         this.mSSO = new SystemPropertyEditorSessionSSO();
         SystemPropertyImpl e = new SystemPropertyImpl((Object)null);
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

   private void completeGetNewItemData(SystemPropertyImpl editorData) throws Exception {}

   public SystemPropertyPK insert(SystemPropertyEditorSessionCSO cso) throws ValidationException, EJBException {
      this.mLog.debug("insert");
      Timer timer = this.mLog.isInfoEnabled()?new Timer(this.mLog):null;
      this.setUserId(cso.getUserId());
      SystemPropertyImpl editorData = cso.getEditorData();

      SystemPropertyPK e;
      try {
         this.mSystemPropertyEVO = new SystemPropertyEVO();
         this.mSystemPropertyEVO.setProperty(editorData.getProperty());
         this.mSystemPropertyEVO.setValue(editorData.getValue());
         this.mSystemPropertyEVO.setDescription(editorData.getDescription());
         this.mSystemPropertyEVO.setValidateExp(editorData.getValidateExp());
         this.mSystemPropertyEVO.setValidateTxt(editorData.getValidateTxt());
         this.updateSystemPropertyRelationships(editorData);
         this.completeInsertSetup(editorData);
         this.validateInsert();
         this.mSystemPropertyEVO = this.getSystemPropertyAccessor().create(this.mSystemPropertyEVO);
         this.insertIntoAdditionalTables(editorData, true);
         this.sendEntityEventMessage("SystemProperty", this.mSystemPropertyEVO.getPK(), 1);
         e = this.mSystemPropertyEVO.getPK();
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

   private void updateSystemPropertyRelationships(SystemPropertyImpl editorData) throws ValidationException {}

   private void completeInsertSetup(SystemPropertyImpl editorData) throws Exception {}

   private void insertIntoAdditionalTables(SystemPropertyImpl editorData, boolean isInsert) throws Exception {}

   private void validateInsert() throws ValidationException {}

   public SystemPropertyPK copy(SystemPropertyEditorSessionCSO cso) throws ValidationException, EJBException {
      this.mLog.debug("copy");
      Timer timer = this.mLog.isInfoEnabled()?new Timer(this.mLog):null;
      this.setUserId(cso.getUserId());
      SystemPropertyImpl editorData = cso.getEditorData();
      this.mThisTableKey = (SystemPropertyPK)editorData.getPrimaryKey();

      SystemPropertyPK var5;
      try {
         SystemPropertyEVO e = this.getSystemPropertyAccessor().getDetails(this.mThisTableKey, "");
         this.mSystemPropertyEVO = e.deepClone();
         this.mSystemPropertyEVO.setProperty(editorData.getProperty());
         this.mSystemPropertyEVO.setValue(editorData.getValue());
         this.mSystemPropertyEVO.setDescription(editorData.getDescription());
         this.mSystemPropertyEVO.setValidateExp(editorData.getValidateExp());
         this.mSystemPropertyEVO.setValidateTxt(editorData.getValidateTxt());
         this.mSystemPropertyEVO.setVersionNum(0);
         this.updateSystemPropertyRelationships(editorData);
         this.completeCopySetup(editorData);
         this.validateCopy();
         this.mSystemPropertyEVO.prepareForInsert();
         this.mSystemPropertyEVO = this.getSystemPropertyAccessor().create(this.mSystemPropertyEVO);
         this.mThisTableKey = this.mSystemPropertyEVO.getPK();
         this.insertIntoAdditionalTables(editorData, false);
         this.sendEntityEventMessage("SystemProperty", this.mSystemPropertyEVO.getPK(), 1);
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

   private void completeCopySetup(SystemPropertyImpl editorData) throws Exception {}

   public void update(SystemPropertyEditorSessionCSO cso) throws ValidationException, EJBException {
      this.mLog.debug("update");
      Timer timer = this.mLog.isInfoEnabled()?new Timer(this.mLog):null;
      this.setUserId(cso.getUserId());
      SystemPropertyImpl editorData = cso.getEditorData();
      this.mThisTableKey = (SystemPropertyPK)editorData.getPrimaryKey();

      try {
         this.mSystemPropertyEVO = this.getSystemPropertyAccessor().getDetails(this.mThisTableKey, "");
         this.preValidateUpdate(editorData);
         this.mSystemPropertyEVO.setProperty(editorData.getProperty());
         this.mSystemPropertyEVO.setValue(editorData.getValue());
         this.mSystemPropertyEVO.setDescription(editorData.getDescription());
         this.mSystemPropertyEVO.setValidateExp(editorData.getValidateExp());
         this.mSystemPropertyEVO.setValidateTxt(editorData.getValidateTxt());
         if(editorData.getVersionNum() != this.mSystemPropertyEVO.getVersionNum()) {
            throw new VersionValidationException(this.mThisTableKey + " expected:" + editorData.getVersionNum() + " found:" + this.mSystemPropertyEVO.getVersionNum());
         }

         this.updateSystemPropertyRelationships(editorData);
         this.completeUpdateSetup(editorData);
         this.postValidateUpdate();
         this.getSystemPropertyAccessor().setDetails(this.mSystemPropertyEVO);
         this.updateAdditionalTables(editorData);
         this.sendEntityEventMessage("SystemProperty", this.mSystemPropertyEVO.getPK(), 3);
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

   private void preValidateUpdate(SystemPropertyImpl editorData) throws ValidationException {}

   private void postValidateUpdate() throws ValidationException {}

   private void completeUpdateSetup(SystemPropertyImpl editorData) throws Exception {}

   private void updateAdditionalTables(SystemPropertyImpl editorData) throws Exception {}

   public void delete(int userId, Object paramKey) throws ValidationException, EJBException {
      if(this.mLog.isDebugEnabled()) {
         this.mLog.debug("delete", paramKey);
      }

      Timer timer = this.mLog.isInfoEnabled()?new Timer(this.mLog):null;
      this.setUserId(userId);
      this.mThisTableKey = (SystemPropertyPK)paramKey;

      try {
         this.mSystemPropertyEVO = this.getSystemPropertyAccessor().getDetails(this.mThisTableKey, "");
         this.validateDelete();
         this.deleteDataFromOtherTables();
         this.mSystemPropertyAccessor.remove(this.mThisTableKey);
         this.sendEntityEventMessage("SystemProperty", this.mThisTableKey, 2);
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

   private SystemPropertyAccessor getSystemPropertyAccessor() throws Exception {
      if(this.mSystemPropertyAccessor == null) {
         this.mSystemPropertyAccessor = new SystemPropertyAccessor(this.getInitialContext());
      }

      return this.mSystemPropertyAccessor;
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
