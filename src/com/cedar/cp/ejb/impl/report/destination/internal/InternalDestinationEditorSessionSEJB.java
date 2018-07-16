// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:27:13
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.impl.report.destination.internal;

import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.base.VersionValidationException;
import com.cedar.cp.api.user.UserRef;
import com.cedar.cp.dto.base.EntityEventMessage;
import com.cedar.cp.dto.base.PrimaryKey;
import com.cedar.cp.dto.report.destination.internal.InternalDestinationEditorSessionCSO;
import com.cedar.cp.dto.report.destination.internal.InternalDestinationEditorSessionSSO;
import com.cedar.cp.dto.report.destination.internal.InternalDestinationImpl;
import com.cedar.cp.dto.report.destination.internal.InternalDestinationPK;
import com.cedar.cp.dto.report.distribution.CheckInternalDestinationELO;
import com.cedar.cp.dto.user.AllUsersELO;
import com.cedar.cp.dto.user.UserPK;
import com.cedar.cp.ejb.impl.base.AbstractSession;
import com.cedar.cp.ejb.impl.report.destination.internal.InternalDestinationAccessor;
import com.cedar.cp.ejb.impl.report.destination.internal.InternalDestinationEVO;
import com.cedar.cp.ejb.impl.report.destination.internal.InternalDestinationUsersEVO;
import com.cedar.cp.ejb.impl.report.distribution.DistributionLinkDAO;
import com.cedar.cp.ejb.impl.user.UserDAO;
import com.cedar.cp.util.Log;
import com.cedar.cp.util.Timer;
import com.cedar.cp.util.common.JmsConnectionImpl;
import java.util.ArrayList;
import java.util.Iterator;
import javax.ejb.EJBException;
import javax.ejb.SessionContext;

public class InternalDestinationEditorSessionSEJB extends AbstractSession {

   private static final String DEPENDANTS_FOR_GET_ITEM_DATA = "<0>";
   private static final String DEPENDANTS_FOR_INSERT = "";
   private static final String DEPENDANTS_FOR_COPY = "<0>";
   private static final String DEPENDANTS_FOR_UPDATE = "<0>";
   private static final String DEPENDANTS_FOR_DELETE = "<0>";
   private transient Log mLog = new Log(this.getClass());
   private transient SessionContext mSessionContext;
   private transient InternalDestinationAccessor mInternalDestinationAccessor;
   private InternalDestinationEditorSessionSSO mSSO;
   private InternalDestinationPK mThisTableKey;
   private InternalDestinationEVO mInternalDestinationEVO;


   public InternalDestinationEditorSessionSSO getItemData(int userId, Object paramKey) throws ValidationException, EJBException {
      this.setUserId(userId);
      if(this.mLog.isDebugEnabled()) {
         this.mLog.debug("getItemData", paramKey);
      }

      Timer timer = this.mLog.isInfoEnabled()?new Timer(this.mLog):null;
      this.mThisTableKey = (InternalDestinationPK)paramKey;

      InternalDestinationEditorSessionSSO e;
      try {
         this.mInternalDestinationEVO = this.getInternalDestinationAccessor().getDetails(this.mThisTableKey, "<0>");
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
      this.mSSO = new InternalDestinationEditorSessionSSO();
      InternalDestinationImpl editorData = this.buildInternalDestinationEditData(this.mThisTableKey);
      this.completeGetItemData(editorData);
      this.mSSO.setEditorData(editorData);
   }

   private void completeGetItemData(InternalDestinationImpl editorData) throws Exception {}

   private InternalDestinationImpl buildInternalDestinationEditData(Object thisKey) throws Exception {
      InternalDestinationImpl editorData = new InternalDestinationImpl(thisKey);
      editorData.setVisId(this.mInternalDestinationEVO.getVisId());
      editorData.setDescription(this.mInternalDestinationEVO.getDescription());
      editorData.setMessageType(this.mInternalDestinationEVO.getMessageType());
      editorData.setVersionNum(this.mInternalDestinationEVO.getVersionNum());
      this.completeInternalDestinationEditData(editorData);
      return editorData;
   }

   private void completeInternalDestinationEditData(InternalDestinationImpl editorData) throws Exception {
      ArrayList users = new ArrayList();
      UserDAO uDAO = new UserDAO();
      Iterator iter = this.mInternalDestinationEVO.getInternalUserList().iterator();

      while(iter.hasNext()) {
         InternalDestinationUsersEVO evo = (InternalDestinationUsersEVO)iter.next();
         AllUsersELO cpusers = uDAO.getAllUsers();
         cpusers.reset();

         while(cpusers.hasNext()) {
            cpusers.next();
            UserPK pk = (UserPK)cpusers.getUserEntityRef().getPrimaryKey();
            if(pk.getUserId() == evo.getUserId()) {
               users.add(cpusers.getUserEntityRef());
               break;
            }
         }
      }

      editorData.setUserList(users);
   }

   public InternalDestinationEditorSessionSSO getNewItemData(int userId) throws EJBException {
      this.mLog.debug("getNewItemData");
      this.setUserId(userId);
      Timer timer = this.mLog.isInfoEnabled()?new Timer(this.mLog):null;

      InternalDestinationEditorSessionSSO var4;
      try {
         this.mSSO = new InternalDestinationEditorSessionSSO();
         InternalDestinationImpl e = new InternalDestinationImpl((Object)null);
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

   private void completeGetNewItemData(InternalDestinationImpl editorData) throws Exception {}

   public InternalDestinationPK insert(InternalDestinationEditorSessionCSO cso) throws ValidationException, EJBException {
      this.mLog.debug("insert");
      Timer timer = this.mLog.isInfoEnabled()?new Timer(this.mLog):null;
      this.setUserId(cso.getUserId());
      InternalDestinationImpl editorData = cso.getEditorData();

      InternalDestinationPK e;
      try {
         this.mInternalDestinationEVO = new InternalDestinationEVO();
         this.mInternalDestinationEVO.setVisId(editorData.getVisId());
         this.mInternalDestinationEVO.setDescription(editorData.getDescription());
         this.mInternalDestinationEVO.setMessageType(editorData.getMessageType());
         this.updateInternalDestinationRelationships(editorData);
         this.completeInsertSetup(editorData);
         this.validateInsert();
         this.mInternalDestinationEVO = this.getInternalDestinationAccessor().create(this.mInternalDestinationEVO);
         this.insertIntoAdditionalTables(editorData, true);
         this.sendEntityEventMessage("InternalDestination", this.mInternalDestinationEVO.getPK(), 1);
         e = this.mInternalDestinationEVO.getPK();
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

   private void updateInternalDestinationRelationships(InternalDestinationImpl editorData) throws ValidationException {}

   private void completeInsertSetup(InternalDestinationImpl editorData) throws Exception {
      Iterator iter = editorData.getUserList().iterator();

      while(iter.hasNext()) {
         UserRef user = (UserRef)iter.next();
         UserPK pk = (UserPK)user.getPrimaryKey();
         InternalDestinationUsersEVO evo = new InternalDestinationUsersEVO();
         evo.setUserId(pk.getUserId());
         this.mInternalDestinationEVO.addInternalUserListItem(evo);
      }

   }

   private void insertIntoAdditionalTables(InternalDestinationImpl editorData, boolean isInsert) throws Exception {}

   private void validateInsert() throws ValidationException {
      if(this.mInternalDestinationEVO.getInternalUserList() == null || this.mInternalDestinationEVO.getInternalUserList().isEmpty()) {
         throw new ValidationException("User list can not be empty");
      }
   }

   public InternalDestinationPK copy(InternalDestinationEditorSessionCSO cso) throws ValidationException, EJBException {
      this.mLog.debug("copy");
      Timer timer = this.mLog.isInfoEnabled()?new Timer(this.mLog):null;
      this.setUserId(cso.getUserId());
      InternalDestinationImpl editorData = cso.getEditorData();
      this.mThisTableKey = (InternalDestinationPK)editorData.getPrimaryKey();

      InternalDestinationPK var5;
      try {
         InternalDestinationEVO e = this.getInternalDestinationAccessor().getDetails(this.mThisTableKey, "<0>");
         this.mInternalDestinationEVO = e.deepClone();
         this.mInternalDestinationEVO.setVisId(editorData.getVisId());
         this.mInternalDestinationEVO.setDescription(editorData.getDescription());
         this.mInternalDestinationEVO.setMessageType(editorData.getMessageType());
         this.mInternalDestinationEVO.setVersionNum(0);
         this.updateInternalDestinationRelationships(editorData);
         this.completeCopySetup(editorData);
         this.validateCopy();
         this.mInternalDestinationEVO.prepareForInsert();
         this.mInternalDestinationEVO = this.getInternalDestinationAccessor().create(this.mInternalDestinationEVO);
         this.mThisTableKey = this.mInternalDestinationEVO.getPK();
         this.insertIntoAdditionalTables(editorData, false);
         this.sendEntityEventMessage("InternalDestination", this.mInternalDestinationEVO.getPK(), 1);
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

   private void completeCopySetup(InternalDestinationImpl editorData) throws Exception {}

   public void update(InternalDestinationEditorSessionCSO cso) throws ValidationException, EJBException {
      this.mLog.debug("update");
      Timer timer = this.mLog.isInfoEnabled()?new Timer(this.mLog):null;
      this.setUserId(cso.getUserId());
      InternalDestinationImpl editorData = cso.getEditorData();
      this.mThisTableKey = (InternalDestinationPK)editorData.getPrimaryKey();

      try {
         this.mInternalDestinationEVO = this.getInternalDestinationAccessor().getDetails(this.mThisTableKey, "<0>");
         this.preValidateUpdate(editorData);
         this.mInternalDestinationEVO.setVisId(editorData.getVisId());
         this.mInternalDestinationEVO.setDescription(editorData.getDescription());
         this.mInternalDestinationEVO.setMessageType(editorData.getMessageType());
         if(editorData.getVersionNum() != this.mInternalDestinationEVO.getVersionNum()) {
            //throw new VersionValidationException(this.mThisTableKey + " expected:" + editorData.getVersionNum() + " found:" + this.mInternalDestinationEVO.getVersionNum());
            throw new VersionValidationException("Version update failure. The entity you have been editing has been updated by another user.");
         }

         this.updateInternalDestinationRelationships(editorData);
         this.completeUpdateSetup(editorData);
         this.postValidateUpdate();
         this.getInternalDestinationAccessor().setDetails(this.mInternalDestinationEVO);
         this.updateAdditionalTables(editorData);
         this.sendEntityEventMessage("InternalDestination", this.mInternalDestinationEVO.getPK(), 3);
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

   private void preValidateUpdate(InternalDestinationImpl editorData) throws ValidationException {}

   private void postValidateUpdate() throws ValidationException {}

   private void completeUpdateSetup(InternalDestinationImpl editorData) throws Exception {
      boolean add = true;
      Iterator iter = editorData.getUserList().iterator();

      InternalDestinationUsersEVO idEvo1;
      InternalDestinationUsersEVO idEvo2;
      Iterator innerIter;
      while(iter.hasNext()) {
         UserRef remove = (UserRef)iter.next();
         UserPK ref = (UserPK)remove.getPrimaryKey();
         idEvo1 = new InternalDestinationUsersEVO(this.mInternalDestinationEVO.getInternalDestinationId(), ref.getUserId());
         innerIter = this.mInternalDestinationEVO.getInternalUserList().iterator();
         add = true;

         while(true) {
            if(innerIter.hasNext()) {
               idEvo2 = (InternalDestinationUsersEVO)innerIter.next();
               if(idEvo1.getUserId() != idEvo2.getUserId()) {
                  continue;
               }

               add = false;
            }

            if(add) {
               this.mInternalDestinationEVO.addInternalUserListItem(idEvo1);
            }
            break;
         }
      }

      boolean remove1 = true;
      iter = this.mInternalDestinationEVO.getInternalUserList().iterator();

      while(iter.hasNext()) {
         idEvo1 = (InternalDestinationUsersEVO)iter.next();
         innerIter = editorData.getUserList().iterator();
         remove1 = true;

         while(true) {
            if(innerIter.hasNext()) {
               UserRef ref1 = (UserRef)innerIter.next();
               UserPK pk = (UserPK)ref1.getPrimaryKey();
               idEvo2 = new InternalDestinationUsersEVO(this.mInternalDestinationEVO.getInternalDestinationId(), pk.getUserId());
               if(idEvo1.getUserId() != idEvo2.getUserId()) {
                  continue;
               }

               remove1 = false;
            }

            if(remove1) {
               this.mInternalDestinationEVO.deleteInternalUserListItem(idEvo1.getPK());
            }
            break;
         }
      }

   }

   private void updateAdditionalTables(InternalDestinationImpl editorData) throws Exception {}

   public void delete(int userId, Object paramKey) throws ValidationException, EJBException {
      if(this.mLog.isDebugEnabled()) {
         this.mLog.debug("delete", paramKey);
      }

      Timer timer = this.mLog.isInfoEnabled()?new Timer(this.mLog):null;
      this.setUserId(userId);
      this.mThisTableKey = (InternalDestinationPK)paramKey;

      try {
         this.mInternalDestinationEVO = this.getInternalDestinationAccessor().getDetails(this.mThisTableKey, "<0>");
         this.validateDelete();
         this.deleteDataFromOtherTables();
         this.mInternalDestinationAccessor.remove(this.mThisTableKey);
         this.sendEntityEventMessage("InternalDestination", this.mThisTableKey, 2);
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
      CheckInternalDestinationELO eList = dlDao.getCheckInternalDestination(this.mInternalDestinationEVO.getInternalDestinationId());
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

   private InternalDestinationAccessor getInternalDestinationAccessor() throws Exception {
      if(this.mInternalDestinationAccessor == null) {
         this.mInternalDestinationAccessor = new InternalDestinationAccessor(this.getInitialContext());
      }

      return this.mInternalDestinationAccessor;
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
