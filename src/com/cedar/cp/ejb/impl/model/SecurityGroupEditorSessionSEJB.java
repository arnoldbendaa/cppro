// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:26:30
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.impl.model;

import com.cedar.cp.api.base.EntityList;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.base.VersionValidationException;
import com.cedar.cp.dto.base.EntityEventMessage;
import com.cedar.cp.dto.base.PrimaryKey;
import com.cedar.cp.dto.model.AllModelsELO;
import com.cedar.cp.dto.model.ModelPK;
import com.cedar.cp.dto.model.ModelRefImpl;
import com.cedar.cp.dto.model.SecurityAccessDefPK;
import com.cedar.cp.dto.model.SecurityAccessDefRefImpl;
import com.cedar.cp.dto.model.SecurityGroupCK;
import com.cedar.cp.dto.model.SecurityGroupEditorSessionCSO;
import com.cedar.cp.dto.model.SecurityGroupEditorSessionSSO;
import com.cedar.cp.dto.model.SecurityGroupImpl;
import com.cedar.cp.dto.model.SecurityGroupUserRelPK;
import com.cedar.cp.dto.user.AllUsersELO;
import com.cedar.cp.dto.user.UserPK;
import com.cedar.cp.dto.user.UserRefImpl;
import com.cedar.cp.ejb.impl.base.AbstractSession;
import com.cedar.cp.ejb.impl.model.ModelAccessor;
import com.cedar.cp.ejb.impl.model.ModelEVO;
import com.cedar.cp.ejb.impl.model.SecurityAccessDefDAO;
import com.cedar.cp.ejb.impl.model.SecurityAccessDefEVO;
import com.cedar.cp.ejb.impl.model.SecurityGroupEVO;
import com.cedar.cp.ejb.impl.model.SecurityGroupUserRelEVO;
import com.cedar.cp.ejb.impl.user.UserAccessor;
import com.cedar.cp.util.Log;
import com.cedar.cp.util.Timer;
import com.cedar.cp.util.common.JmsConnectionImpl;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import javax.ejb.EJBException;
import javax.ejb.SessionContext;

public class SecurityGroupEditorSessionSEJB extends AbstractSession {

   private static final String DEPENDANTS_FOR_GET_ITEM_DATA = "<17>";
   private static final String DEPENDANTS_FOR_INSERT = "";
   private static final String DEPENDANTS_FOR_COPY = "<17>";
   private static final String DEPENDANTS_FOR_UPDATE = "<17>";
   private static final String DEPENDANTS_FOR_DELETE = "<17>";
   private transient Log mLog = new Log(this.getClass());
   private transient SessionContext mSessionContext;
   private transient ModelAccessor mModelAccessor;
   private SecurityGroupEditorSessionSSO mSSO;
   private SecurityGroupCK mThisTableKey;
   private ModelEVO mModelEVO;
   private SecurityGroupEVO mSecurityGroupEVO;


   public SecurityGroupEditorSessionSSO getItemData(int userId, Object paramKey) throws ValidationException, EJBException {
      this.setUserId(userId);
      if(this.mLog.isDebugEnabled()) {
         this.mLog.debug("getItemData", paramKey);
      }

      Timer timer = this.mLog.isInfoEnabled()?new Timer(this.mLog):null;
      this.mThisTableKey = (SecurityGroupCK)paramKey;

      SecurityGroupEditorSessionSSO e;
      try {
         this.mModelEVO = this.getModelAccessor().getDetails(this.mThisTableKey, "<17>");
         this.mSecurityGroupEVO = this.mModelEVO.getSecurityGroupsItem(this.mThisTableKey.getSecurityGroupPK());
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
      this.mSSO = new SecurityGroupEditorSessionSSO();
      SecurityGroupImpl editorData = this.buildSecurityGroupEditData(this.mThisTableKey);
      this.completeGetItemData(editorData);
      this.mSSO.setEditorData(editorData);
   }

   private void completeGetItemData(SecurityGroupImpl editorData) throws Exception {}

   private SecurityGroupImpl buildSecurityGroupEditData(Object thisKey) throws Exception {
      SecurityGroupImpl editorData = new SecurityGroupImpl(thisKey);
      editorData.setVisId(this.mSecurityGroupEVO.getVisId());
      editorData.setDescription(this.mSecurityGroupEVO.getDescription());
      editorData.setVersionNum(this.mSecurityGroupEVO.getVersionNum());
      editorData.setModelRef(new ModelRefImpl(this.mModelEVO.getPK(), this.mModelEVO.getVisId()));
      this.completeSecurityGroupEditData(editorData);
      return editorData;
   }

   private void completeSecurityGroupEditData(SecurityGroupImpl editorData) throws Exception {
      editorData.setUsers(this.queryUserRefs());
      int updateAccessDef = this.mSecurityGroupEVO.getUpdateAccessId();
      SecurityAccessDefDAO dao = new SecurityAccessDefDAO();
      dao.doLoad(new SecurityAccessDefPK(updateAccessDef));
      SecurityAccessDefEVO evo = dao.getDetails("n/a");
      editorData.setSecurityAccessDef(evo.getEntityRef(this.mModelEVO));
   }

   public SecurityGroupEditorSessionSSO getNewItemData(int userId) throws EJBException {
      this.mLog.debug("getNewItemData");
      this.setUserId(userId);
      Timer timer = this.mLog.isInfoEnabled()?new Timer(this.mLog):null;

      SecurityGroupEditorSessionSSO var4;
      try {
         this.mSSO = new SecurityGroupEditorSessionSSO();
         SecurityGroupImpl e = new SecurityGroupImpl((Object)null);
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

   private void completeGetNewItemData(SecurityGroupImpl editorData) throws Exception {}

   public SecurityGroupCK insert(SecurityGroupEditorSessionCSO cso) throws ValidationException, EJBException {
      this.mLog.debug("insert");
      Timer timer = this.mLog.isInfoEnabled()?new Timer(this.mLog):null;
      this.setUserId(cso.getUserId());
      SecurityGroupImpl editorData = cso.getEditorData();

      try {
         this.mModelEVO = this.getModelAccessor().getDetails(editorData.getModelRef(), "");
         this.mSecurityGroupEVO = new SecurityGroupEVO();
         this.mSecurityGroupEVO.setVisId(editorData.getVisId());
         this.mSecurityGroupEVO.setDescription(editorData.getDescription());
         this.updateSecurityGroupRelationships(editorData);
         this.completeInsertSetup(editorData);
         this.validateInsert();
         this.mModelEVO.addSecurityGroupsItem(this.mSecurityGroupEVO);
         this.mModelEVO = this.getModelAccessor().setAndGetDetails(this.mModelEVO, "<16>");
         Iterator e = this.mModelEVO.getSecurityGroups().iterator();

         while(true) {
            if(e.hasNext()) {
               this.mSecurityGroupEVO = (SecurityGroupEVO)e.next();
               if(!this.mSecurityGroupEVO.insertPending()) {
                  continue;
               }
            }

            this.insertIntoAdditionalTables(editorData, true);
            this.sendEntityEventMessage("SecurityGroup", this.mSecurityGroupEVO.getPK(), 1);
            SecurityGroupCK var5 = new SecurityGroupCK(this.mModelEVO.getPK(), this.mSecurityGroupEVO.getPK());
            return var5;
         }
      } catch (ValidationException var11) {
         throw new EJBException(var11);
      } catch (EJBException var12) {
         throw var12;
      } catch (Exception var13) {
         var13.printStackTrace();
         throw new EJBException(var13.getMessage(), var13);
      } finally {
         this.setUserId(0);
         if(timer != null) {
            timer.logInfo("insert", "");
         }

      }
   }

   private void updateSecurityGroupRelationships(SecurityGroupImpl editorData) throws ValidationException {}

   private void completeInsertSetup(SecurityGroupImpl editorData) throws Exception {
      this.updateUsersInGroup(editorData.getUsers());
      this.mSecurityGroupEVO.setUpdateAccessId(((SecurityAccessDefRefImpl)editorData.getSecurityAccessDef()).getSecurityAccessDefPK().getSecurityAccessDefId());
   }

   private void insertIntoAdditionalTables(SecurityGroupImpl editorData, boolean isInsert) throws Exception {}

   private void validateInsert() throws ValidationException {}

   public SecurityGroupCK copy(SecurityGroupEditorSessionCSO cso) throws ValidationException, EJBException {
      this.mLog.debug("copy");
      Timer timer = this.mLog.isInfoEnabled()?new Timer(this.mLog):null;
      this.setUserId(cso.getUserId());
      SecurityGroupImpl editorData = cso.getEditorData();
      this.mThisTableKey = (SecurityGroupCK)editorData.getPrimaryKey();

      try {
         this.mModelEVO = this.getModelAccessor().getDetails(this.mThisTableKey, "<17>");
         SecurityGroupEVO e = this.mModelEVO.getSecurityGroupsItem(this.mThisTableKey.getSecurityGroupPK());
         this.mSecurityGroupEVO = e.deepClone();
         this.mSecurityGroupEVO.setVisId(editorData.getVisId());
         this.mSecurityGroupEVO.setDescription(editorData.getDescription());
         this.mSecurityGroupEVO.setVersionNum(0);
         this.updateSecurityGroupRelationships(editorData);
         this.completeCopySetup(editorData);
         this.validateCopy();
         ModelPK parentKey = (ModelPK)editorData.getModelRef().getPrimaryKey();
         if(!parentKey.equals(this.mModelEVO.getPK())) {
            this.mModelEVO = this.getModelAccessor().getDetails(parentKey, "");
         }

         this.mSecurityGroupEVO.prepareForInsert((ModelEVO)null);
         this.mModelEVO.addSecurityGroupsItem(this.mSecurityGroupEVO);
         this.mModelEVO = this.getModelAccessor().setAndGetDetails(this.mModelEVO, "<16><17>");
         Iterator iter = this.mModelEVO.getSecurityGroups().iterator();

         while(true) {
            if(iter.hasNext()) {
               this.mSecurityGroupEVO = (SecurityGroupEVO)iter.next();
               if(!this.mSecurityGroupEVO.insertPending()) {
                  continue;
               }
            }

            this.mThisTableKey = new SecurityGroupCK(this.mModelEVO.getPK(), this.mSecurityGroupEVO.getPK());
            this.insertIntoAdditionalTables(editorData, false);
            this.sendEntityEventMessage("SecurityGroup", this.mSecurityGroupEVO.getPK(), 1);
            SecurityGroupCK var7 = this.mThisTableKey;
            return var7;
         }
      } catch (ValidationException var13) {
         throw new EJBException(var13);
      } catch (EJBException var14) {
         throw var14;
      } catch (Exception var15) {
         var15.printStackTrace();
         throw new EJBException(var15);
      } finally {
         this.setUserId(0);
         if(timer != null) {
            timer.logInfo("copy", this.mThisTableKey);
         }

      }
   }

   private void validateCopy() throws ValidationException {}

   private void completeCopySetup(SecurityGroupImpl editorData) throws Exception {
      this.updateUsersInGroup(editorData.getUsers());
      this.mSecurityGroupEVO.setUpdateAccessId(((SecurityAccessDefRefImpl)editorData.getSecurityAccessDef()).getSecurityAccessDefPK().getSecurityAccessDefId());
   }

   public void update(SecurityGroupEditorSessionCSO cso) throws ValidationException, EJBException {
      this.mLog.debug("update");
      Timer timer = this.mLog.isInfoEnabled()?new Timer(this.mLog):null;
      this.setUserId(cso.getUserId());
      SecurityGroupImpl editorData = cso.getEditorData();
      this.mThisTableKey = (SecurityGroupCK)editorData.getPrimaryKey();

      try {
         this.mModelEVO = this.getModelAccessor().getDetails(this.mThisTableKey, "<17>");
         this.mSecurityGroupEVO = this.mModelEVO.getSecurityGroupsItem(this.mThisTableKey.getSecurityGroupPK());
         this.preValidateUpdate(editorData);
         this.mSecurityGroupEVO.setVisId(editorData.getVisId());
         this.mSecurityGroupEVO.setDescription(editorData.getDescription());
         if(editorData.getVersionNum() != this.mSecurityGroupEVO.getVersionNum()) {
            throw new VersionValidationException(this.mThisTableKey + " expected:" + editorData.getVersionNum() + " found:" + this.mSecurityGroupEVO.getVersionNum());
         }

         this.updateSecurityGroupRelationships(editorData);
         this.completeUpdateSetup(editorData);
         this.postValidateUpdate();
         this.getModelAccessor().setDetails(this.mModelEVO);
         this.updateAdditionalTables(editorData);
         this.sendEntityEventMessage("SecurityGroup", this.mSecurityGroupEVO.getPK(), 3);
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

   private void preValidateUpdate(SecurityGroupImpl editorData) throws ValidationException {}

   private void postValidateUpdate() throws ValidationException {}

   private void completeUpdateSetup(SecurityGroupImpl editorData) throws Exception {
      this.updateUsersInGroup(editorData.getUsers());
      int updateAccessDefId = ((SecurityAccessDefRefImpl)editorData.getSecurityAccessDef()).getSecurityAccessDefPK().getSecurityAccessDefId();
      this.mSecurityGroupEVO.setUpdateAccessId(updateAccessDefId);
   }

   public EntityList getOwnershipData(int userId, Object paramKey) throws EJBException {
      this.mLog.debug("getOwnershipData");
      Timer timer = this.mLog.isInfoEnabled()?new Timer(this.mLog):null;
      this.setUserId(userId);
      this.mThisTableKey = (SecurityGroupCK)paramKey;

      AllModelsELO e;
      try {
         e = this.getModelAccessor().getAllModels();
      } catch (Exception var8) {
         var8.printStackTrace();
         throw new EJBException(var8.getMessage(), var8);
      } finally {
         this.setUserId(0);
         if(timer != null) {
            timer.logInfo("getOwnershipData", "");
         }

      }

      return e;
   }

   private void updateAdditionalTables(SecurityGroupImpl editorData) throws Exception {}

   public void delete(int userId, Object paramKey) throws ValidationException, EJBException {
      if(this.mLog.isDebugEnabled()) {
         this.mLog.debug("delete", paramKey);
      }

      Timer timer = this.mLog.isInfoEnabled()?new Timer(this.mLog):null;
      this.setUserId(userId);
      this.mThisTableKey = (SecurityGroupCK)paramKey;

      try {
         this.mModelEVO = this.getModelAccessor().getDetails(this.mThisTableKey, "<17>");
         this.mSecurityGroupEVO = this.mModelEVO.getSecurityGroupsItem(this.mThisTableKey.getSecurityGroupPK());
         this.validateDelete();
         this.deleteDataFromOtherTables();
         this.mModelEVO.deleteSecurityGroupsItem(this.mThisTableKey.getSecurityGroupPK());
         this.getModelAccessor().setDetails(this.mModelEVO);
         this.sendEntityEventMessage("SecurityGroup", this.mThisTableKey.getSecurityGroupPK(), 2);
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

   private ModelAccessor getModelAccessor() throws Exception {
      if(this.mModelAccessor == null) {
         this.mModelAccessor = new ModelAccessor(this.getInitialContext());
      }

      return this.mModelAccessor;
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

   private List queryUserRefs() throws Exception {
      UserAccessor userAccessor = new UserAccessor(this.getInitialContext());
      AllUsersELO allUsers = userAccessor.getAllUsers();
      HashMap userMap = new HashMap();

      for(int users = 0; users < allUsers.size(); ++users) {
         UserRefImpl iter = (UserRefImpl)allUsers.getValueAt(users, "User");
         userMap.put(iter.getUserPK(), iter);
      }

      ArrayList var8 = new ArrayList();
      if(this.mSecurityGroupEVO.getUsersInGroup() != null) {
         Iterator var9 = this.mSecurityGroupEVO.getUsersInGroup().iterator();

         while(var9.hasNext()) {
            SecurityGroupUserRelEVO evo = (SecurityGroupUserRelEVO)var9.next();
            UserPK userPK = new UserPK(evo.getUserId());
            var8.add(userMap.get(userPK));
         }
      }

      return var8;
   }

   private void updateUsersInGroup(List userRefs) {
      Object oldList = this.mSecurityGroupEVO.getUsersInGroup() == null?Collections.EMPTY_LIST:new ArrayList(this.mSecurityGroupEVO.getUsersInGroup());
      Iterator iter = userRefs.iterator();

      while(iter.hasNext()) {
         UserRefImpl evo = (UserRefImpl)iter.next();
         SecurityGroupUserRelPK pk = new SecurityGroupUserRelPK(this.mSecurityGroupEVO.getPK().getSecurityGroupId(), evo.getUserPK().getUserId());
         SecurityGroupUserRelEVO evo1 = this.mSecurityGroupEVO.getUsersInGroup() != null?this.mSecurityGroupEVO.getUsersInGroupItem(pk):null;
         if(evo1 == null) {
            evo1 = new SecurityGroupUserRelEVO(this.mSecurityGroupEVO.getPK().getSecurityGroupId(), evo.getUserPK().getUserId());
            this.mSecurityGroupEVO.addUsersInGroupItem(evo1);
         } else {
            ((List)oldList).remove(evo1);
         }
      }

      iter = ((List)oldList).iterator();

      while(iter.hasNext()) {
         SecurityGroupUserRelEVO evo2 = (SecurityGroupUserRelEVO)iter.next();
         this.mSecurityGroupEVO.deleteUsersInGroupItem(evo2.getPK());
      }

   }
}
