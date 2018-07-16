// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:27:12
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.impl.report.destination.external;

import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.base.VersionValidationException;
import com.cedar.cp.dto.base.EntityEventMessage;
import com.cedar.cp.dto.base.PrimaryKey;
import com.cedar.cp.dto.report.destination.external.ExternalDestinationEditorSessionCSO;
import com.cedar.cp.dto.report.destination.external.ExternalDestinationEditorSessionSSO;
import com.cedar.cp.dto.report.destination.external.ExternalDestinationImpl;
import com.cedar.cp.dto.report.destination.external.ExternalDestinationPK;
import com.cedar.cp.dto.report.distribution.CheckExternalDestinationELO;
import com.cedar.cp.ejb.impl.base.AbstractSession;
import com.cedar.cp.ejb.impl.report.destination.external.ExternalDestinationAccessor;
import com.cedar.cp.ejb.impl.report.destination.external.ExternalDestinationEVO;
import com.cedar.cp.ejb.impl.report.destination.external.ExternalDestinationUsersEVO;
import com.cedar.cp.ejb.impl.report.distribution.DistributionLinkDAO;
import com.cedar.cp.util.Log;
import com.cedar.cp.util.Timer;
import com.cedar.cp.util.common.JmsConnectionImpl;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.ejb.EJBException;
import javax.ejb.SessionContext;

public class ExternalDestinationEditorSessionSEJB extends AbstractSession {

   private static final String DEPENDANTS_FOR_GET_ITEM_DATA = "<0>";
   private static final String DEPENDANTS_FOR_INSERT = "";
   private static final String DEPENDANTS_FOR_COPY = "<0>";
   private static final String DEPENDANTS_FOR_UPDATE = "<0>";
   private static final String DEPENDANTS_FOR_DELETE = "<0>";
   private transient Log mLog = new Log(this.getClass());
   private transient SessionContext mSessionContext;
   private transient ExternalDestinationAccessor mExternalDestinationAccessor;
   private ExternalDestinationEditorSessionSSO mSSO;
   private ExternalDestinationPK mThisTableKey;
   private ExternalDestinationEVO mExternalDestinationEVO;


   public ExternalDestinationEditorSessionSSO getItemData(int userId, Object paramKey) throws ValidationException, EJBException {
      this.setUserId(userId);
      if(this.mLog.isDebugEnabled()) {
         this.mLog.debug("getItemData", paramKey);
      }

      Timer timer = this.mLog.isInfoEnabled()?new Timer(this.mLog):null;
      this.mThisTableKey = (ExternalDestinationPK)paramKey;

      ExternalDestinationEditorSessionSSO e;
      try {
         this.mExternalDestinationEVO = this.getExternalDestinationAccessor().getDetails(this.mThisTableKey, "<0>");
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
      this.mSSO = new ExternalDestinationEditorSessionSSO();
      ExternalDestinationImpl editorData = this.buildExternalDestinationEditData(this.mThisTableKey);
      this.completeGetItemData(editorData);
      this.mSSO.setEditorData(editorData);
   }

   private void completeGetItemData(ExternalDestinationImpl editorData) throws Exception {}

   private ExternalDestinationImpl buildExternalDestinationEditData(Object thisKey) throws Exception {
      ExternalDestinationImpl editorData = new ExternalDestinationImpl(thisKey);
      editorData.setVisId(this.mExternalDestinationEVO.getVisId());
      editorData.setDescription(this.mExternalDestinationEVO.getDescription());
      editorData.setVersionNum(this.mExternalDestinationEVO.getVersionNum());
      this.completeExternalDestinationEditData(editorData);
      return editorData;
   }

   private void completeExternalDestinationEditData(ExternalDestinationImpl editorData) throws Exception {
      ArrayList data = new ArrayList();
      ExternalDestinationUsersEVO evo = null;
      Iterator iter = this.mExternalDestinationEVO.getExternalUserList().iterator();

      while(iter.hasNext()) {
         evo = (ExternalDestinationUsersEVO)iter.next();
         data.add(evo.getEmailAddress());
      }

      editorData.setUserList(data);
   }

   public ExternalDestinationEditorSessionSSO getNewItemData(int userId) throws EJBException {
      this.mLog.debug("getNewItemData");
      this.setUserId(userId);
      Timer timer = this.mLog.isInfoEnabled()?new Timer(this.mLog):null;

      ExternalDestinationEditorSessionSSO var4;
      try {
         this.mSSO = new ExternalDestinationEditorSessionSSO();
         ExternalDestinationImpl e = new ExternalDestinationImpl((Object)null);
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

   private void completeGetNewItemData(ExternalDestinationImpl editorData) throws Exception {
      editorData.setUserList(new ArrayList());
   }

   public ExternalDestinationPK insert(ExternalDestinationEditorSessionCSO cso) throws ValidationException, EJBException {
      this.mLog.debug("insert");
      Timer timer = this.mLog.isInfoEnabled()?new Timer(this.mLog):null;
      this.setUserId(cso.getUserId());
      ExternalDestinationImpl editorData = cso.getEditorData();

      ExternalDestinationPK e;
      try {
         this.mExternalDestinationEVO = new ExternalDestinationEVO();
         this.mExternalDestinationEVO.setVisId(editorData.getVisId());
         this.mExternalDestinationEVO.setDescription(editorData.getDescription());
         this.updateExternalDestinationRelationships(editorData);
         this.completeInsertSetup(editorData);
         this.validateInsert();
         this.mExternalDestinationEVO = this.getExternalDestinationAccessor().create(this.mExternalDestinationEVO);
         this.insertIntoAdditionalTables(editorData, true);
         this.sendEntityEventMessage("ExternalDestination", this.mExternalDestinationEVO.getPK(), 1);
         e = this.mExternalDestinationEVO.getPK();
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

   private void updateExternalDestinationRelationships(ExternalDestinationImpl editorData) throws ValidationException {}

   private void completeInsertSetup(ExternalDestinationImpl editorData) throws Exception {
      List addresses = editorData.getUserList();
      int userId = 0;

      for(int i = 0; i < addresses.size(); ++i) {
         ExternalDestinationUsersEVO evo = new ExternalDestinationUsersEVO();
         --userId;
         evo.setExternalDestinationUsersId(userId);
         evo.setEmailAddress(addresses.get(i).toString());
         this.mExternalDestinationEVO.addExternalUserListItem(evo);
      }

   }

   private void insertIntoAdditionalTables(ExternalDestinationImpl editorData, boolean isInsert) throws Exception {}

   private void validateInsert() throws ValidationException {
      if(this.mExternalDestinationEVO.getExternalUserList() == null || this.mExternalDestinationEVO.getExternalUserList().isEmpty()) {
         throw new ValidationException("User list can not be empty");
      }
   }

   public ExternalDestinationPK copy(ExternalDestinationEditorSessionCSO cso) throws ValidationException, EJBException {
      this.mLog.debug("copy");
      Timer timer = this.mLog.isInfoEnabled()?new Timer(this.mLog):null;
      this.setUserId(cso.getUserId());
      ExternalDestinationImpl editorData = cso.getEditorData();
      this.mThisTableKey = (ExternalDestinationPK)editorData.getPrimaryKey();

      ExternalDestinationPK var5;
      try {
         ExternalDestinationEVO e = this.getExternalDestinationAccessor().getDetails(this.mThisTableKey, "<0>");
         this.mExternalDestinationEVO = e.deepClone();
         this.mExternalDestinationEVO.setVisId(editorData.getVisId());
         this.mExternalDestinationEVO.setDescription(editorData.getDescription());
         this.mExternalDestinationEVO.setVersionNum(0);
         this.updateExternalDestinationRelationships(editorData);
         this.completeCopySetup(editorData);
         this.validateCopy();
         this.mExternalDestinationEVO.prepareForInsert();
         this.mExternalDestinationEVO = this.getExternalDestinationAccessor().create(this.mExternalDestinationEVO);
         this.mThisTableKey = this.mExternalDestinationEVO.getPK();
         this.insertIntoAdditionalTables(editorData, false);
         this.sendEntityEventMessage("ExternalDestination", this.mExternalDestinationEVO.getPK(), 1);
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

   private void completeCopySetup(ExternalDestinationImpl editorData) throws Exception {}

   public void update(ExternalDestinationEditorSessionCSO cso) throws ValidationException, EJBException {
      this.mLog.debug("update");
      Timer timer = this.mLog.isInfoEnabled()?new Timer(this.mLog):null;
      this.setUserId(cso.getUserId());
      ExternalDestinationImpl editorData = cso.getEditorData();
      this.mThisTableKey = (ExternalDestinationPK)editorData.getPrimaryKey();

      try {
         this.mExternalDestinationEVO = this.getExternalDestinationAccessor().getDetails(this.mThisTableKey, "<0>");
         this.preValidateUpdate(editorData);
         this.mExternalDestinationEVO.setVisId(editorData.getVisId());
         this.mExternalDestinationEVO.setDescription(editorData.getDescription());
         if(editorData.getVersionNum() != this.mExternalDestinationEVO.getVersionNum()) {
            throw new VersionValidationException(this.mThisTableKey + " expected:" + editorData.getVersionNum() + " found:" + this.mExternalDestinationEVO.getVersionNum());
         }

         this.updateExternalDestinationRelationships(editorData);
         this.completeUpdateSetup(editorData);
         this.postValidateUpdate();
         this.getExternalDestinationAccessor().setDetails(this.mExternalDestinationEVO);
         this.updateAdditionalTables(editorData);
         this.sendEntityEventMessage("ExternalDestination", this.mExternalDestinationEVO.getPK(), 3);
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

   private void preValidateUpdate(ExternalDestinationImpl editorData) throws ValidationException {}

   private void postValidateUpdate() throws ValidationException {}

   private void completeUpdateSetup(ExternalDestinationImpl editorData) throws Exception {
      Iterator iter = this.mExternalDestinationEVO.getExternalUserList().iterator();

      while(iter.hasNext()) {
         ExternalDestinationUsersEVO evo = (ExternalDestinationUsersEVO)iter.next();
         this.mExternalDestinationEVO.deleteExternalUserListItem(evo.getPK());
      }

      this.completeInsertSetup(editorData);
   }

   private void updateAdditionalTables(ExternalDestinationImpl editorData) throws Exception {}

   public void delete(int userId, Object paramKey) throws ValidationException, EJBException {
      if(this.mLog.isDebugEnabled()) {
         this.mLog.debug("delete", paramKey);
      }

      Timer timer = this.mLog.isInfoEnabled()?new Timer(this.mLog):null;
      this.setUserId(userId);
      this.mThisTableKey = (ExternalDestinationPK)paramKey;

      try {
         this.mExternalDestinationEVO = this.getExternalDestinationAccessor().getDetails(this.mThisTableKey, "<0>");
         this.validateDelete();
         this.deleteDataFromOtherTables();
         this.mExternalDestinationAccessor.remove(this.mThisTableKey);
         this.sendEntityEventMessage("ExternalDestination", this.mThisTableKey, 2);
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
      DistributionLinkDAO dlDao = new DistributionLinkDAO();
      CheckExternalDestinationELO eList = dlDao.getCheckExternalDestination(this.mExternalDestinationEVO.getExternalDestinationId());
      if(eList.getNumRows() > 0) {
         throw new ValidationException("Destination is in use by distribution list");
      }
   }

   public void ejbCreate() throws EJBException {}

   public void ejbRemove() {}

   public void setSessionContext(SessionContext context) {
      this.mSessionContext = context;
   }

   public void ejbActivate() {}

   public void ejbPassivate() {}

   private ExternalDestinationAccessor getExternalDestinationAccessor() throws Exception {
      if(this.mExternalDestinationAccessor == null) {
         this.mExternalDestinationAccessor = new ExternalDestinationAccessor(this.getInitialContext());
      }

      return this.mExternalDestinationAccessor;
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
