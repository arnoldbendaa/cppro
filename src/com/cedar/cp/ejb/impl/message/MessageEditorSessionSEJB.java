// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:08:43
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.impl.message;

import com.cedar.cp.api.base.CPFileWrapper;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.base.VersionValidationException;
import com.cedar.cp.dto.base.EntityEventMessage;
import com.cedar.cp.dto.base.PrimaryKey;
import com.cedar.cp.dto.message.MessageEditorSessionCSO;
import com.cedar.cp.dto.message.MessageEditorSessionSSO;
import com.cedar.cp.dto.message.MessageImpl;
import com.cedar.cp.dto.message.MessagePK;
import com.cedar.cp.ejb.impl.base.AbstractSession;
import com.cedar.cp.ejb.impl.message.MessageAccessor;
import com.cedar.cp.ejb.impl.message.MessageAttatchEVO;
import com.cedar.cp.ejb.impl.message.MessageEVO;
import com.cedar.cp.ejb.impl.message.MessageUserEVO;
import com.cedar.cp.util.Log;
import com.cedar.cp.util.Timer;
import com.cedar.cp.util.common.JmsConnectionImpl;
import java.util.Iterator;
import javax.ejb.EJBException;
import javax.ejb.SessionContext;

public class MessageEditorSessionSEJB extends AbstractSession {

   private static final String DEPENDANTS_FOR_GET_ITEM_DATA = "<0><1>";
   private static final String DEPENDANTS_FOR_INSERT = "";
   private static final String DEPENDANTS_FOR_COPY = "<0><1>";
   private static final String DEPENDANTS_FOR_UPDATE = "<0><1>";
   private static final String DEPENDANTS_FOR_DELETE = "<0><1>";
   private transient boolean mInsertValid = false;
   private transient Log mLog = new Log(this.getClass());
   private transient SessionContext mSessionContext;
   private transient MessageAccessor mMessageAccessor;
   private MessageEditorSessionSSO mSSO;
   private MessagePK mThisTableKey;
   private MessageEVO mMessageEVO;


   public MessageEditorSessionSSO getItemData(int userId, Object paramKey) throws ValidationException, EJBException {
      this.setUserId(userId);
      if(this.mLog.isDebugEnabled()) {
         this.mLog.debug("getItemData", paramKey);
      }

      Timer timer = this.mLog.isInfoEnabled()?new Timer(this.mLog):null;
      this.mThisTableKey = (MessagePK)paramKey;

      MessageEditorSessionSSO e;
      try {
         this.mMessageEVO = this.getMessageAccessor().getDetails(this.mThisTableKey, "<0><1>");
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
      this.mSSO = new MessageEditorSessionSSO();
      MessageImpl editorData = this.buildMessageEditData(this.mThisTableKey);
      this.completeGetItemData(editorData);
      this.mSSO.setEditorData(editorData);
   }

   private void completeGetItemData(MessageImpl editorData) throws Exception {
      MessageUserEVO messageUserEVO = null;
      Iterator iter = this.mMessageEVO.getMessageUsers().iterator();

      while(iter.hasNext()) {
         messageUserEVO = (MessageUserEVO)iter.next();
         editorData.addMessageUser(messageUserEVO);
      }

      iter = this.mMessageEVO.getMessageAttatchments().iterator();
      MessageAttatchEVO attachEVO = null;

      while(iter.hasNext()) {
         attachEVO = (MessageAttatchEVO)iter.next();
         CPFileWrapper attatch = new CPFileWrapper(attachEVO.getAttatch(), attachEVO.getAttatchName());
         editorData.addAttachment(attatch);
      }

   }

   private MessageImpl buildMessageEditData(Object thisKey) throws Exception {
      MessageImpl editorData = new MessageImpl(thisKey);
      editorData.setSubject(this.mMessageEVO.getSubject());
      editorData.setContent(this.mMessageEVO.getContent());
      editorData.setMessageType(this.mMessageEVO.getMessageType());
      editorData.setVersionNum(this.mMessageEVO.getVersionNum());
      this.completeMessageEditData(editorData);
      return editorData;
   }

   private void completeMessageEditData(MessageImpl editorData) throws Exception {}

   public MessageEditorSessionSSO getNewItemData(int userId) throws EJBException {
      this.mLog.debug("getNewItemData");
      this.setUserId(userId);
      Timer timer = this.mLog.isInfoEnabled()?new Timer(this.mLog):null;

      MessageEditorSessionSSO var4;
      try {
         this.mSSO = new MessageEditorSessionSSO();
         MessageImpl e = new MessageImpl((Object)null);
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

   private void completeGetNewItemData(MessageImpl editorData) throws Exception {}

   public MessagePK insert(MessageEditorSessionCSO cso) throws ValidationException, EJBException {
      this.mLog.debug("insert");
      Timer timer = this.mLog.isInfoEnabled()?new Timer(this.mLog):null;
      this.setUserId(cso.getUserId());
      MessageImpl editorData = cso.getEditorData();

      MessagePK e;
      try {
         if(!this.mInsertValid) {
            throw new ValidationException("wrong method used");
         }

         this.mMessageEVO = new MessageEVO();
         this.mMessageEVO.setSubject(editorData.getSubject());
         this.mMessageEVO.setContent(editorData.getContent());
         this.mMessageEVO.setMessageType(editorData.getMessageType());
         this.updateMessageRelationships(editorData);
         this.completeInsertSetup(editorData);
         this.validateInsert();
         this.mMessageEVO = this.getMessageAccessor().create(this.mMessageEVO);
         this.insertIntoAdditionalTables(editorData, true);
         this.sendEntityEventMessage("Message", this.mMessageEVO.getPK(), 1);
         e = this.mMessageEVO.getPK();
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

   private void updateMessageRelationships(MessageImpl editorData) throws ValidationException {}

   private void completeInsertSetup(MessageImpl editorData) throws Exception {
      int userID = 0;
      Iterator iter = editorData.getFromUsers().iterator();

      MessageUserEVO evo;
      String id;
      while(iter.hasNext()) {
         id = (String)iter.next();
         evo = new MessageUserEVO();
         evo.setMessageUserId((long)(userID--));
         evo.setUserId(id);
         if(id.equalsIgnoreCase("System")) {
            evo.setDeleted(true);
         }

         evo.setType(1);
         evo.setRead(false);
         this.mMessageEVO.addMessageUsersItem(evo);
      }

      iter = editorData.getToUsers().iterator();

      while(iter.hasNext()) {
         id = (String)iter.next();
         evo = new MessageUserEVO();
         evo.setMessageUserId((long)(userID--));
         evo.setUserId(id);
         evo.setType(0);
         evo.setRead(false);
         this.mMessageEVO.addMessageUsersItem(evo);
      }

      int attatchId = 0;
      iter = editorData.getAttachments().iterator();

      while(iter.hasNext()) {
         CPFileWrapper file = (CPFileWrapper)iter.next();
         MessageAttatchEVO attatchEVO = new MessageAttatchEVO();
         attatchEVO.setAttatch(file.getData());
         attatchEVO.setAttatchName(file.getName());
         attatchEVO.setMessageAttatchId((long)(attatchId--));
         if(file.getData().length > 0) {
            this.mMessageEVO.addMessageAttatchmentsItem(attatchEVO);
         }
      }

   }

   private void insertIntoAdditionalTables(MessageImpl editorData, boolean isInsert) throws Exception {}

   private void validateInsert() throws ValidationException {}

   public MessagePK copy(MessageEditorSessionCSO cso) throws ValidationException, EJBException {
      this.mLog.debug("copy");
      Timer timer = this.mLog.isInfoEnabled()?new Timer(this.mLog):null;
      this.setUserId(cso.getUserId());
      MessageImpl editorData = cso.getEditorData();
      this.mThisTableKey = (MessagePK)editorData.getPrimaryKey();

      MessagePK var5;
      try {
         MessageEVO e = this.getMessageAccessor().getDetails(this.mThisTableKey, "<0><1>");
         this.mMessageEVO = e.deepClone();
         this.mMessageEVO.setSubject(editorData.getSubject());
         this.mMessageEVO.setContent(editorData.getContent());
         this.mMessageEVO.setMessageType(editorData.getMessageType());
         this.mMessageEVO.setVersionNum(0);
         this.updateMessageRelationships(editorData);
         this.completeCopySetup(editorData);
         this.validateCopy();
         this.mMessageEVO.prepareForInsert();
         this.mMessageEVO = this.getMessageAccessor().create(this.mMessageEVO);
         this.mThisTableKey = this.mMessageEVO.getPK();
         this.insertIntoAdditionalTables(editorData, false);
         this.sendEntityEventMessage("Message", this.mMessageEVO.getPK(), 1);
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

   private void completeCopySetup(MessageImpl editorData) throws Exception {}

   public void update(MessageEditorSessionCSO cso) throws ValidationException, EJBException {
      this.mLog.debug("update");
      Timer timer = this.mLog.isInfoEnabled()?new Timer(this.mLog):null;
      this.setUserId(cso.getUserId());
      MessageImpl editorData = cso.getEditorData();
      this.mThisTableKey = (MessagePK)editorData.getPrimaryKey();

      try {
         this.mMessageEVO = this.getMessageAccessor().getDetails(this.mThisTableKey, "<0><1>");
         this.preValidateUpdate(editorData);
         this.mMessageEVO.setSubject(editorData.getSubject());
         this.mMessageEVO.setContent(editorData.getContent());
         this.mMessageEVO.setMessageType(editorData.getMessageType());
         if(editorData.getVersionNum() != this.mMessageEVO.getVersionNum()) {
            throw new VersionValidationException(this.mThisTableKey + " expected:" + editorData.getVersionNum() + " found:" + this.mMessageEVO.getVersionNum());
         }

         this.updateMessageRelationships(editorData);
         this.completeUpdateSetup(editorData);
         this.postValidateUpdate();
         this.getMessageAccessor().setDetails(this.mMessageEVO);
         this.updateAdditionalTables(editorData);
         this.sendEntityEventMessage("Message", this.mMessageEVO.getPK(), 3);
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

   private void preValidateUpdate(MessageImpl editorData) throws ValidationException {}

   private void postValidateUpdate() throws ValidationException {}

   private void completeUpdateSetup(MessageImpl editorData) throws Exception {
      Iterator iter = this.mMessageEVO.getMessageUsers().iterator();
      MessageUserEVO evo = null;

      while(iter.hasNext()) {
         evo = (MessageUserEVO)iter.next();
         if(evo.getPK().getMessageUserId() == editorData.getUserKey()) {
            evo.setRead(editorData.isState());
         }
      }

      if(editorData.getDeleteUserKey() != 0L) {
         iter = this.mMessageEVO.getMessageUsers().iterator();

         while(iter.hasNext()) {
            evo = (MessageUserEVO)iter.next();
            if(evo.getPK().getMessageUserId() == editorData.getDeleteUserKey()) {
               evo.setDeleted(true);
               break;
            }
         }
      }

   }

   private void updateAdditionalTables(MessageImpl editorData) throws Exception {}

   public void delete(int userId, Object paramKey) throws ValidationException, EJBException {
      if(this.mLog.isDebugEnabled()) {
         this.mLog.debug("delete", paramKey);
      }

      Timer timer = this.mLog.isInfoEnabled()?new Timer(this.mLog):null;
      this.setUserId(userId);
      this.mThisTableKey = (MessagePK)paramKey;

      try {
         this.mMessageEVO = this.getMessageAccessor().getDetails(this.mThisTableKey, "<0><1>");
         this.validateDelete();
         this.deleteDataFromOtherTables();
         this.mMessageAccessor.remove(this.mThisTableKey);
         this.sendEntityEventMessage("Message", this.mThisTableKey, 2);
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

   private MessageAccessor getMessageAccessor() throws Exception {
      if(this.mMessageAccessor == null) {
         this.mMessageAccessor = new MessageAccessor(this.getInitialContext());
      }

      return this.mMessageAccessor;
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

   public MessagePK insertBackDoor(MessageEditorSessionCSO cso) throws ValidationException, EJBException {
      this.mInsertValid = true;

      MessagePK var2;
      try {
         var2 = this.insert(cso);
      } finally {
         this.mInsertValid = false;
      }

      return var2;
   }
}
