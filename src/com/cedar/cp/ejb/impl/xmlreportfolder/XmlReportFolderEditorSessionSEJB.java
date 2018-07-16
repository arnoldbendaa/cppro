// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:27:41
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.impl.xmlreportfolder;

import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.base.VersionValidationException;
import com.cedar.cp.api.xmlreport.XmlReportRef;
import com.cedar.cp.dto.base.EntityEventMessage;
import com.cedar.cp.dto.base.PrimaryKey;
import com.cedar.cp.dto.xmlreport.XmlReportPK;
import com.cedar.cp.dto.xmlreport.XmlReportsForFolderELO;
import com.cedar.cp.dto.xmlreportfolder.DecendentFoldersELO;
import com.cedar.cp.dto.xmlreportfolder.XmlReportFolderEditorSessionCSO;
import com.cedar.cp.dto.xmlreportfolder.XmlReportFolderEditorSessionSSO;
import com.cedar.cp.dto.xmlreportfolder.XmlReportFolderImpl;
import com.cedar.cp.dto.xmlreportfolder.XmlReportFolderPK;
import com.cedar.cp.ejb.impl.base.AbstractSession;
import com.cedar.cp.ejb.impl.xmlreport.XmlReportAccessor;
import com.cedar.cp.ejb.impl.xmlreport.XmlReportDAO;
import com.cedar.cp.ejb.impl.xmlreportfolder.XmlReportFolderAccessor;
import com.cedar.cp.ejb.impl.xmlreportfolder.XmlReportFolderDAO;
import com.cedar.cp.ejb.impl.xmlreportfolder.XmlReportFolderEVO;
import com.cedar.cp.util.Log;
import com.cedar.cp.util.Timer;
import com.cedar.cp.util.common.JmsConnectionImpl;
import java.util.Iterator;
import javax.ejb.EJBException;
import javax.ejb.SessionContext;

public class XmlReportFolderEditorSessionSEJB extends AbstractSession {

   private static final String DEPENDANTS_FOR_GET_ITEM_DATA = "";
   private static final String DEPENDANTS_FOR_INSERT = "";
   private static final String DEPENDANTS_FOR_COPY = "";
   private static final String DEPENDANTS_FOR_UPDATE = "";
   private static final String DEPENDANTS_FOR_DELETE = "";
   private transient Log mLog = new Log(this.getClass());
   private transient SessionContext mSessionContext;
   private transient XmlReportFolderAccessor mXmlReportFolderAccessor;
   private XmlReportFolderEditorSessionSSO mSSO;
   private XmlReportFolderPK mThisTableKey;
   private XmlReportFolderEVO mXmlReportFolderEVO;


   public XmlReportFolderEditorSessionSSO getItemData(int userId, Object paramKey) throws ValidationException, EJBException {
      this.setUserId(userId);
      if(this.mLog.isDebugEnabled()) {
         this.mLog.debug("getItemData", paramKey);
      }

      Timer timer = this.mLog.isInfoEnabled()?new Timer(this.mLog):null;
      this.mThisTableKey = (XmlReportFolderPK)paramKey;

      XmlReportFolderEditorSessionSSO e;
      try {
         this.mXmlReportFolderEVO = this.getXmlReportFolderAccessor().getDetails(this.mThisTableKey, "");
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
      this.mSSO = new XmlReportFolderEditorSessionSSO();
      XmlReportFolderImpl editorData = this.buildXmlReportFolderEditData(this.mThisTableKey);
      this.completeGetItemData(editorData);
      this.mSSO.setEditorData(editorData);
   }

   private void completeGetItemData(XmlReportFolderImpl editorData) throws Exception {}

   private XmlReportFolderImpl buildXmlReportFolderEditData(Object thisKey) throws Exception {
      XmlReportFolderImpl editorData = new XmlReportFolderImpl(thisKey);
      editorData.setParentFolderId(this.mXmlReportFolderEVO.getParentFolderId());
      editorData.setVisId(this.mXmlReportFolderEVO.getVisId());
      editorData.setUserId(this.mXmlReportFolderEVO.getUserId());
      editorData.setVersionNum(this.mXmlReportFolderEVO.getVersionNum());
      this.completeXmlReportFolderEditData(editorData);
      return editorData;
   }

   private void completeXmlReportFolderEditData(XmlReportFolderImpl editorData) throws Exception {}

   public XmlReportFolderEditorSessionSSO getNewItemData(int userId) throws EJBException {
      this.mLog.debug("getNewItemData");
      this.setUserId(userId);
      Timer timer = this.mLog.isInfoEnabled()?new Timer(this.mLog):null;

      XmlReportFolderEditorSessionSSO var4;
      try {
         this.mSSO = new XmlReportFolderEditorSessionSSO();
         XmlReportFolderImpl e = new XmlReportFolderImpl((Object)null);
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

   private void completeGetNewItemData(XmlReportFolderImpl editorData) throws Exception {}

   public XmlReportFolderPK insert(XmlReportFolderEditorSessionCSO cso) throws ValidationException, EJBException {
      this.mLog.debug("insert");
      Timer timer = this.mLog.isInfoEnabled()?new Timer(this.mLog):null;
      this.setUserId(cso.getUserId());
      XmlReportFolderImpl editorData = cso.getEditorData();

      XmlReportFolderPK e;
      try {
         this.mXmlReportFolderEVO = new XmlReportFolderEVO();
         this.mXmlReportFolderEVO.setParentFolderId(editorData.getParentFolderId());
         this.mXmlReportFolderEVO.setVisId(editorData.getVisId());
         this.mXmlReportFolderEVO.setUserId(editorData.getUserId());
         this.updateXmlReportFolderRelationships(editorData);
         this.completeInsertSetup(editorData);
         this.validateInsert();
         this.mXmlReportFolderEVO = this.getXmlReportFolderAccessor().create(this.mXmlReportFolderEVO);
         this.insertIntoAdditionalTables(editorData, true);
         this.sendEntityEventMessage("XmlReportFolder", this.mXmlReportFolderEVO.getPK(), 1);
         e = this.mXmlReportFolderEVO.getPK();
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

   private void updateXmlReportFolderRelationships(XmlReportFolderImpl editorData) throws ValidationException {}

   private void completeInsertSetup(XmlReportFolderImpl editorData) throws Exception {}

   private void insertIntoAdditionalTables(XmlReportFolderImpl editorData, boolean isInsert) throws Exception {}

   private void validateInsert() throws ValidationException {}

   public XmlReportFolderPK copy(XmlReportFolderEditorSessionCSO cso) throws ValidationException, EJBException {
      this.mLog.debug("copy");
      Timer timer = this.mLog.isInfoEnabled()?new Timer(this.mLog):null;
      this.setUserId(cso.getUserId());
      XmlReportFolderImpl editorData = cso.getEditorData();
      this.mThisTableKey = (XmlReportFolderPK)editorData.getPrimaryKey();

      XmlReportFolderPK var5;
      try {
         XmlReportFolderEVO e = this.getXmlReportFolderAccessor().getDetails(this.mThisTableKey, "");
         this.mXmlReportFolderEVO = e.deepClone();
         this.mXmlReportFolderEVO.setParentFolderId(editorData.getParentFolderId());
         this.mXmlReportFolderEVO.setVisId(editorData.getVisId());
         this.mXmlReportFolderEVO.setUserId(editorData.getUserId());
         this.mXmlReportFolderEVO.setVersionNum(0);
         this.updateXmlReportFolderRelationships(editorData);
         this.completeCopySetup(editorData);
         this.validateCopy();
         this.mXmlReportFolderEVO.prepareForInsert();
         this.mXmlReportFolderEVO = this.getXmlReportFolderAccessor().create(this.mXmlReportFolderEVO);
         this.mThisTableKey = this.mXmlReportFolderEVO.getPK();
         this.insertIntoAdditionalTables(editorData, false);
         this.sendEntityEventMessage("XmlReportFolder", this.mXmlReportFolderEVO.getPK(), 1);
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

   private void completeCopySetup(XmlReportFolderImpl editorData) throws Exception {}

   public void update(XmlReportFolderEditorSessionCSO cso) throws ValidationException, EJBException {
      this.mLog.debug("update");
      Timer timer = this.mLog.isInfoEnabled()?new Timer(this.mLog):null;
      this.setUserId(cso.getUserId());
      XmlReportFolderImpl editorData = cso.getEditorData();
      this.mThisTableKey = (XmlReportFolderPK)editorData.getPrimaryKey();

      try {
         this.mXmlReportFolderEVO = this.getXmlReportFolderAccessor().getDetails(this.mThisTableKey, "");
         this.preValidateUpdate(editorData);
         this.mXmlReportFolderEVO.setParentFolderId(editorData.getParentFolderId());
         this.mXmlReportFolderEVO.setVisId(editorData.getVisId());
         this.mXmlReportFolderEVO.setUserId(editorData.getUserId());
         if(editorData.getVersionNum() != this.mXmlReportFolderEVO.getVersionNum()) {
            throw new VersionValidationException(this.mThisTableKey + " expected:" + editorData.getVersionNum() + " found:" + this.mXmlReportFolderEVO.getVersionNum());
         }

         this.updateXmlReportFolderRelationships(editorData);
         this.completeUpdateSetup(editorData);
         this.postValidateUpdate();
         this.getXmlReportFolderAccessor().setDetails(this.mXmlReportFolderEVO);
         this.updateAdditionalTables(editorData);
         this.sendEntityEventMessage("XmlReportFolder", this.mXmlReportFolderEVO.getPK(), 3);
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

   private void preValidateUpdate(XmlReportFolderImpl editorData) throws ValidationException {}

   private void postValidateUpdate() throws ValidationException {}

   private void completeUpdateSetup(XmlReportFolderImpl editorData) throws Exception {}

   private void updateAdditionalTables(XmlReportFolderImpl editorData) throws Exception {}

   public void delete(int userId, Object paramKey) throws ValidationException, EJBException {
      if(this.mLog.isDebugEnabled()) {
         this.mLog.debug("delete", paramKey);
      }

      Timer timer = this.mLog.isInfoEnabled()?new Timer(this.mLog):null;
      this.setUserId(userId);
      this.mThisTableKey = (XmlReportFolderPK)paramKey;

      try {
         this.mXmlReportFolderEVO = this.getXmlReportFolderAccessor().getDetails(this.mThisTableKey, "");
         this.validateDelete();
         this.deleteDataFromOtherTables();
         this.mXmlReportFolderAccessor.remove(this.mThisTableKey);
         this.sendEntityEventMessage("XmlReportFolder", this.mThisTableKey, 2);
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

   private void deleteDataFromOtherTables() throws Exception {
      this.mLog.debug("Would do nested deletes");
      XmlReportAccessor reportAccessor = new XmlReportAccessor(this.getInitialContext());
      int thisFolderId = this.mThisTableKey.getXmlReportFolderId();
      XmlReportFolderDAO folderDAO = new XmlReportFolderDAO();
      DecendentFoldersELO nestedFolders = folderDAO.getDecendentFolders(thisFolderId);
      Iterator iter = nestedFolders.iterator();

      while(iter.hasNext()) {
         iter.next();
         int folderId = nestedFolders.getXmlReportFolderId();
         XmlReportFolderPK folderKey = new XmlReportFolderPK(folderId);
         this.mXmlReportFolderAccessor.remove(folderKey);
         this.sendEntityEventMessage("XmlReportFolder", folderKey, 2);
         this.removeReportsForFolder(reportAccessor, folderId);
      }

      this.removeReportsForFolder(reportAccessor, thisFolderId);
   }

   private void validateDelete() throws ValidationException, Exception {}

   public void ejbCreate() throws EJBException {}

   public void ejbRemove() {}

   public void setSessionContext(SessionContext context) {
      this.mSessionContext = context;
   }

   public void ejbActivate() {}

   public void ejbPassivate() {}

   private XmlReportFolderAccessor getXmlReportFolderAccessor() throws Exception {
      if(this.mXmlReportFolderAccessor == null) {
         this.mXmlReportFolderAccessor = new XmlReportFolderAccessor(this.getInitialContext());
      }

      return this.mXmlReportFolderAccessor;
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

   private void removeReportsForFolder(XmlReportAccessor reportAccessor, int folderId) throws Exception {
      XmlReportDAO reportDAO = new XmlReportDAO();
      XmlReportsForFolderELO reports = reportDAO.getXmlReportsForFolder(folderId);
      Iterator iter2 = reports.iterator();

      while(iter2.hasNext()) {
         iter2.next();
         XmlReportRef ref = reports.getXmlReportEntityRef();
         XmlReportPK reportPK = (XmlReportPK)ref.getPrimaryKey();
         reportAccessor.remove(reportPK);
         this.sendEntityEventMessage("XmlReport", reportPK, 2);
      }

   }
}
