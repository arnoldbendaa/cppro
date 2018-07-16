// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:08:30
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.impl.extendedattachment;

import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.dto.base.EntityEventMessage;
import com.cedar.cp.dto.base.PrimaryKey;
import com.cedar.cp.dto.extendedattachment.ExtendedAttachmentEditorSessionCSO;
import com.cedar.cp.dto.extendedattachment.ExtendedAttachmentEditorSessionSSO;
import com.cedar.cp.dto.extendedattachment.ExtendedAttachmentImpl;
import com.cedar.cp.dto.extendedattachment.ExtendedAttachmentPK;
import com.cedar.cp.ejb.impl.base.AbstractSession;
import com.cedar.cp.ejb.impl.extendedattachment.ExtendedAttachmentAccessor;
import com.cedar.cp.ejb.impl.extendedattachment.ExtendedAttachmentEVO;
import com.cedar.cp.util.Log;
import com.cedar.cp.util.Timer;
import com.cedar.cp.util.common.JmsConnectionImpl;
import javax.ejb.EJBException;
import javax.ejb.SessionContext;

public class ExtendedAttachmentEditorSessionSEJB extends AbstractSession {

   private static final String DEPENDANTS_FOR_GET_ITEM_DATA = "";
   private static final String DEPENDANTS_FOR_INSERT = "";
   private static final String DEPENDANTS_FOR_COPY = "";
   private static final String DEPENDANTS_FOR_UPDATE = "";
   private static final String DEPENDANTS_FOR_DELETE = "";
   private transient Log mLog = new Log(this.getClass());
   private transient SessionContext mSessionContext;
   private transient ExtendedAttachmentAccessor mExtendedAttachmentAccessor;
   private ExtendedAttachmentEditorSessionSSO mSSO;
   private ExtendedAttachmentPK mThisTableKey;
   private ExtendedAttachmentEVO mExtendedAttachmentEVO;


   public ExtendedAttachmentEditorSessionSSO getItemData(int userId, Object paramKey) throws ValidationException, EJBException {
      this.setUserId(userId);
      if(this.mLog.isDebugEnabled()) {
         this.mLog.debug("getItemData", paramKey);
      }

      Timer timer = this.mLog.isInfoEnabled()?new Timer(this.mLog):null;
      this.mThisTableKey = (ExtendedAttachmentPK)paramKey;

      ExtendedAttachmentEditorSessionSSO e;
      try {
         this.mExtendedAttachmentEVO = this.getExtendedAttachmentAccessor().getDetails(this.mThisTableKey, "");
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
      this.mSSO = new ExtendedAttachmentEditorSessionSSO();
      ExtendedAttachmentImpl editorData = this.buildExtendedAttachmentEditData(this.mThisTableKey);
      this.completeGetItemData(editorData);
      this.mSSO.setEditorData(editorData);
   }

   private void completeGetItemData(ExtendedAttachmentImpl editorData) throws Exception {}

   private ExtendedAttachmentImpl buildExtendedAttachmentEditData(Object thisKey) throws Exception {
      ExtendedAttachmentImpl editorData = new ExtendedAttachmentImpl(thisKey);
      editorData.setFileName(this.mExtendedAttachmentEVO.getFileName());
      editorData.setAttatch(this.mExtendedAttachmentEVO.getAttatch());
      this.completeExtendedAttachmentEditData(editorData);
      return editorData;
   }

   private void completeExtendedAttachmentEditData(ExtendedAttachmentImpl editorData) throws Exception {}

   public ExtendedAttachmentEditorSessionSSO getNewItemData(int userId) throws EJBException {
      this.mLog.debug("getNewItemData");
      this.setUserId(userId);
      Timer timer = this.mLog.isInfoEnabled()?new Timer(this.mLog):null;

      ExtendedAttachmentEditorSessionSSO var4;
      try {
         this.mSSO = new ExtendedAttachmentEditorSessionSSO();
         ExtendedAttachmentImpl e = new ExtendedAttachmentImpl((Object)null);
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

   private void completeGetNewItemData(ExtendedAttachmentImpl editorData) throws Exception {}

   public ExtendedAttachmentPK insert(ExtendedAttachmentEditorSessionCSO cso) throws ValidationException, EJBException {
      this.mLog.debug("insert");
      Timer timer = this.mLog.isInfoEnabled()?new Timer(this.mLog):null;
      this.setUserId(cso.getUserId());
      ExtendedAttachmentImpl editorData = cso.getEditorData();

      ExtendedAttachmentPK e;
      try {
         this.mExtendedAttachmentEVO = new ExtendedAttachmentEVO();
         this.mExtendedAttachmentEVO.setFileName(editorData.getFileName());
         this.mExtendedAttachmentEVO.setAttatch(editorData.getAttatch());
         this.updateExtendedAttachmentRelationships(editorData);
         this.completeInsertSetup(editorData);
         this.validateInsert();
         this.mExtendedAttachmentEVO = this.getExtendedAttachmentAccessor().create(this.mExtendedAttachmentEVO);
         this.insertIntoAdditionalTables(editorData, true);
         this.sendEntityEventMessage("ExtendedAttachment", this.mExtendedAttachmentEVO.getPK(), 1);
         e = this.mExtendedAttachmentEVO.getPK();
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

   private void updateExtendedAttachmentRelationships(ExtendedAttachmentImpl editorData) throws ValidationException {}

   private void completeInsertSetup(ExtendedAttachmentImpl editorData) throws Exception {}

   private void insertIntoAdditionalTables(ExtendedAttachmentImpl editorData, boolean isInsert) throws Exception {}

   private void validateInsert() throws ValidationException {}

   public ExtendedAttachmentPK copy(ExtendedAttachmentEditorSessionCSO cso) throws ValidationException, EJBException {
      this.mLog.debug("copy");
      Timer timer = this.mLog.isInfoEnabled()?new Timer(this.mLog):null;
      this.setUserId(cso.getUserId());
      ExtendedAttachmentImpl editorData = cso.getEditorData();
      this.mThisTableKey = (ExtendedAttachmentPK)editorData.getPrimaryKey();

      ExtendedAttachmentPK var5;
      try {
         ExtendedAttachmentEVO e = this.getExtendedAttachmentAccessor().getDetails(this.mThisTableKey, "");
         this.mExtendedAttachmentEVO = e.deepClone();
         this.mExtendedAttachmentEVO.setFileName(editorData.getFileName());
         this.mExtendedAttachmentEVO.setAttatch(editorData.getAttatch());
         this.updateExtendedAttachmentRelationships(editorData);
         this.completeCopySetup(editorData);
         this.validateCopy();
         this.mExtendedAttachmentEVO.prepareForInsert();
         this.mExtendedAttachmentEVO = this.getExtendedAttachmentAccessor().create(this.mExtendedAttachmentEVO);
         this.mThisTableKey = this.mExtendedAttachmentEVO.getPK();
         this.insertIntoAdditionalTables(editorData, false);
         this.sendEntityEventMessage("ExtendedAttachment", this.mExtendedAttachmentEVO.getPK(), 1);
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

   private void completeCopySetup(ExtendedAttachmentImpl editorData) throws Exception {}

   public void update(ExtendedAttachmentEditorSessionCSO cso) throws ValidationException, EJBException {
      this.mLog.debug("update");
      Timer timer = this.mLog.isInfoEnabled()?new Timer(this.mLog):null;
      this.setUserId(cso.getUserId());
      ExtendedAttachmentImpl editorData = cso.getEditorData();
      this.mThisTableKey = (ExtendedAttachmentPK)editorData.getPrimaryKey();

      try {
         this.mExtendedAttachmentEVO = this.getExtendedAttachmentAccessor().getDetails(this.mThisTableKey, "");
         this.preValidateUpdate(editorData);
         this.mExtendedAttachmentEVO.setFileName(editorData.getFileName());
         this.mExtendedAttachmentEVO.setAttatch(editorData.getAttatch());
         this.updateExtendedAttachmentRelationships(editorData);
         this.completeUpdateSetup(editorData);
         this.postValidateUpdate();
         this.getExtendedAttachmentAccessor().setDetails(this.mExtendedAttachmentEVO);
         this.updateAdditionalTables(editorData);
         this.sendEntityEventMessage("ExtendedAttachment", this.mExtendedAttachmentEVO.getPK(), 3);
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

   private void preValidateUpdate(ExtendedAttachmentImpl editorData) throws ValidationException {}

   private void postValidateUpdate() throws ValidationException {}

   private void completeUpdateSetup(ExtendedAttachmentImpl editorData) throws Exception {}

   private void updateAdditionalTables(ExtendedAttachmentImpl editorData) throws Exception {}

   public void delete(int userId, Object paramKey) throws ValidationException, EJBException {
      if(this.mLog.isDebugEnabled()) {
         this.mLog.debug("delete", paramKey);
      }

      Timer timer = this.mLog.isInfoEnabled()?new Timer(this.mLog):null;
      this.setUserId(userId);
      this.mThisTableKey = (ExtendedAttachmentPK)paramKey;

      try {
         this.mExtendedAttachmentEVO = this.getExtendedAttachmentAccessor().getDetails(this.mThisTableKey, "");
         this.validateDelete();
         this.deleteDataFromOtherTables();
         this.mExtendedAttachmentAccessor.remove(this.mThisTableKey);
         this.sendEntityEventMessage("ExtendedAttachment", this.mThisTableKey, 2);
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

   private ExtendedAttachmentAccessor getExtendedAttachmentAccessor() throws Exception {
      if(this.mExtendedAttachmentAccessor == null) {
         this.mExtendedAttachmentAccessor = new ExtendedAttachmentAccessor(this.getInitialContext());
      }

      return this.mExtendedAttachmentAccessor;
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
