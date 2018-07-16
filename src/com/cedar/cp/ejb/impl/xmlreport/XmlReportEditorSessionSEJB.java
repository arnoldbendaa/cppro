// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:27:40
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.impl.xmlreport;

import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.base.VersionValidationException;
import com.cedar.cp.dto.base.EntityEventMessage;
import com.cedar.cp.dto.base.PrimaryKey;
import com.cedar.cp.dto.xmlreport.XmlReportEditorSessionCSO;
import com.cedar.cp.dto.xmlreport.XmlReportEditorSessionSSO;
import com.cedar.cp.dto.xmlreport.XmlReportImpl;
import com.cedar.cp.dto.xmlreport.XmlReportPK;
import com.cedar.cp.ejb.impl.base.AbstractSession;
import com.cedar.cp.ejb.impl.xmlreport.XmlReportAccessor;
import com.cedar.cp.ejb.impl.xmlreport.XmlReportEVO;
import com.cedar.cp.util.Log;
import com.cedar.cp.util.Timer;
import com.cedar.cp.util.common.JmsConnectionImpl;
import javax.ejb.EJBException;
import javax.ejb.SessionContext;

public class XmlReportEditorSessionSEJB extends AbstractSession {

   private static final String DEPENDANTS_FOR_GET_ITEM_DATA = "";
   private static final String DEPENDANTS_FOR_INSERT = "";
   private static final String DEPENDANTS_FOR_COPY = "";
   private static final String DEPENDANTS_FOR_UPDATE = "";
   private static final String DEPENDANTS_FOR_DELETE = "";
   private transient Log mLog = new Log(this.getClass());
   private transient SessionContext mSessionContext;
   private transient XmlReportAccessor mXmlReportAccessor;
   private XmlReportEditorSessionSSO mSSO;
   private XmlReportPK mThisTableKey;
   private XmlReportEVO mXmlReportEVO;


   public XmlReportEditorSessionSSO getItemData(int userId, Object paramKey) throws ValidationException, EJBException {
      this.setUserId(userId);
      if(this.mLog.isDebugEnabled()) {
         this.mLog.debug("getItemData", paramKey);
      }

      Timer timer = this.mLog.isInfoEnabled()?new Timer(this.mLog):null;
      this.mThisTableKey = (XmlReportPK)paramKey;

      XmlReportEditorSessionSSO e;
      try {
         this.mXmlReportEVO = this.getXmlReportAccessor().getDetails(this.mThisTableKey, "");
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
      this.mSSO = new XmlReportEditorSessionSSO();
      XmlReportImpl editorData = this.buildXmlReportEditData(this.mThisTableKey);
      this.completeGetItemData(editorData);
      this.mSSO.setEditorData(editorData);
   }

   private void completeGetItemData(XmlReportImpl editorData) throws Exception {}

   private XmlReportImpl buildXmlReportEditData(Object thisKey) throws Exception {
      XmlReportImpl editorData = new XmlReportImpl(thisKey);
      editorData.setXmlReportFolderId(this.mXmlReportEVO.getXmlReportFolderId());
      editorData.setVisId(this.mXmlReportEVO.getVisId());
      editorData.setUserId(this.mXmlReportEVO.getUserId());
      editorData.setDefinition(this.mXmlReportEVO.getDefinition());
      editorData.setVersionNum(this.mXmlReportEVO.getVersionNum());
      this.completeXmlReportEditData(editorData);
      return editorData;
   }

   private void completeXmlReportEditData(XmlReportImpl editorData) throws Exception {}

   public XmlReportEditorSessionSSO getNewItemData(int userId) throws EJBException {
      this.mLog.debug("getNewItemData");
      this.setUserId(userId);
      Timer timer = this.mLog.isInfoEnabled()?new Timer(this.mLog):null;

      XmlReportEditorSessionSSO var4;
      try {
         this.mSSO = new XmlReportEditorSessionSSO();
         XmlReportImpl e = new XmlReportImpl((Object)null);
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

   private void completeGetNewItemData(XmlReportImpl editorData) throws Exception {}

   public XmlReportPK insert(XmlReportEditorSessionCSO cso) throws ValidationException, EJBException {
      this.mLog.debug("insert");
      Timer timer = this.mLog.isInfoEnabled()?new Timer(this.mLog):null;
      this.setUserId(cso.getUserId());
      XmlReportImpl editorData = cso.getEditorData();

      XmlReportPK e;
      try {
         this.mXmlReportEVO = new XmlReportEVO();
         this.mXmlReportEVO.setXmlReportFolderId(editorData.getXmlReportFolderId());
         this.mXmlReportEVO.setVisId(editorData.getVisId());
         this.mXmlReportEVO.setUserId(editorData.getUserId());
         this.mXmlReportEVO.setDefinition(editorData.getDefinition());
         this.updateXmlReportRelationships(editorData);
         this.completeInsertSetup(editorData);
         this.validateInsert();
         this.mXmlReportEVO = this.getXmlReportAccessor().create(this.mXmlReportEVO);
         this.insertIntoAdditionalTables(editorData, true);
         this.sendEntityEventMessage("XmlReport", this.mXmlReportEVO.getPK(), 1);
         e = this.mXmlReportEVO.getPK();
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

   private void updateXmlReportRelationships(XmlReportImpl editorData) throws ValidationException {}

   private void completeInsertSetup(XmlReportImpl editorData) throws Exception {}

   private void insertIntoAdditionalTables(XmlReportImpl editorData, boolean isInsert) throws Exception {}

   private void validateInsert() throws ValidationException {}

   public XmlReportPK copy(XmlReportEditorSessionCSO cso) throws ValidationException, EJBException {
      this.mLog.debug("copy");
      Timer timer = this.mLog.isInfoEnabled()?new Timer(this.mLog):null;
      this.setUserId(cso.getUserId());
      XmlReportImpl editorData = cso.getEditorData();
      this.mThisTableKey = (XmlReportPK)editorData.getPrimaryKey();

      XmlReportPK var5;
      try {
         XmlReportEVO e = this.getXmlReportAccessor().getDetails(this.mThisTableKey, "");
         this.mXmlReportEVO = e.deepClone();
         this.mXmlReportEVO.setXmlReportFolderId(editorData.getXmlReportFolderId());
         this.mXmlReportEVO.setVisId(editorData.getVisId());
         this.mXmlReportEVO.setUserId(editorData.getUserId());
         this.mXmlReportEVO.setDefinition(editorData.getDefinition());
         this.mXmlReportEVO.setVersionNum(0);
         this.updateXmlReportRelationships(editorData);
         this.completeCopySetup(editorData);
         this.validateCopy();
         this.mXmlReportEVO.prepareForInsert();
         this.mXmlReportEVO = this.getXmlReportAccessor().create(this.mXmlReportEVO);
         this.mThisTableKey = this.mXmlReportEVO.getPK();
         this.insertIntoAdditionalTables(editorData, false);
         this.sendEntityEventMessage("XmlReport", this.mXmlReportEVO.getPK(), 1);
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

   private void completeCopySetup(XmlReportImpl editorData) throws Exception {}

   public void update(XmlReportEditorSessionCSO cso) throws ValidationException, EJBException {
      this.mLog.debug("update");
      Timer timer = this.mLog.isInfoEnabled()?new Timer(this.mLog):null;
      this.setUserId(cso.getUserId());
      XmlReportImpl editorData = cso.getEditorData();
      this.mThisTableKey = (XmlReportPK)editorData.getPrimaryKey();

      try {
         this.mXmlReportEVO = this.getXmlReportAccessor().getDetails(this.mThisTableKey, "");
         this.preValidateUpdate(editorData);
         this.mXmlReportEVO.setXmlReportFolderId(editorData.getXmlReportFolderId());
         this.mXmlReportEVO.setVisId(editorData.getVisId());
         this.mXmlReportEVO.setUserId(editorData.getUserId());
         this.mXmlReportEVO.setDefinition(editorData.getDefinition());
         if(editorData.getVersionNum() != this.mXmlReportEVO.getVersionNum()) {
            throw new VersionValidationException(this.mThisTableKey + " expected:" + editorData.getVersionNum() + " found:" + this.mXmlReportEVO.getVersionNum());
         }

         this.updateXmlReportRelationships(editorData);
         this.completeUpdateSetup(editorData);
         this.postValidateUpdate();
         this.getXmlReportAccessor().setDetails(this.mXmlReportEVO);
         this.updateAdditionalTables(editorData);
         this.sendEntityEventMessage("XmlReport", this.mXmlReportEVO.getPK(), 3);
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

   private void preValidateUpdate(XmlReportImpl editorData) throws ValidationException {}

   private void postValidateUpdate() throws ValidationException {}

   private void completeUpdateSetup(XmlReportImpl editorData) throws Exception {}

   private void updateAdditionalTables(XmlReportImpl editorData) throws Exception {}

   public void delete(int userId, Object paramKey) throws ValidationException, EJBException {
      if(this.mLog.isDebugEnabled()) {
         this.mLog.debug("delete", paramKey);
      }

      Timer timer = this.mLog.isInfoEnabled()?new Timer(this.mLog):null;
      this.setUserId(userId);
      this.mThisTableKey = (XmlReportPK)paramKey;

      try {
         this.mXmlReportEVO = this.getXmlReportAccessor().getDetails(this.mThisTableKey, "");
         this.validateDelete();
         this.deleteDataFromOtherTables();
         this.mXmlReportAccessor.remove(this.mThisTableKey);
         this.sendEntityEventMessage("XmlReport", this.mThisTableKey, 2);
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

   private XmlReportAccessor getXmlReportAccessor() throws Exception {
      if(this.mXmlReportAccessor == null) {
         this.mXmlReportAccessor = new XmlReportAccessor(this.getInitialContext());
      }

      return this.mXmlReportAccessor;
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
