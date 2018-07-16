// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:27:23
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.impl.role;

import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.base.VersionValidationException;
import com.cedar.cp.api.role.RoleSecurityRef;
import com.cedar.cp.api.role.RoleSecuritySelection;
import com.cedar.cp.dto.base.EntityEventMessage;
import com.cedar.cp.dto.base.PrimaryKey;
import com.cedar.cp.dto.role.RoleEditorSessionCSO;
import com.cedar.cp.dto.role.RoleEditorSessionSSO;
import com.cedar.cp.dto.role.RoleImpl;
import com.cedar.cp.dto.role.RolePK;
import com.cedar.cp.dto.role.RoleSecurityPK;
import com.cedar.cp.dto.role.RoleSecurityRelPK;
import com.cedar.cp.dto.user.AllRolesForUsersELO;
import com.cedar.cp.ejb.impl.base.AbstractSession;
import com.cedar.cp.ejb.impl.role.RoleAccessor;
import com.cedar.cp.ejb.impl.role.RoleEVO;
import com.cedar.cp.ejb.impl.role.RoleSecurityRelEVO;
import com.cedar.cp.ejb.impl.user.UserRoleDAO;
import com.cedar.cp.util.Log;
import com.cedar.cp.util.Timer;
import com.cedar.cp.util.common.JmsConnectionImpl;
import java.util.Enumeration;
import java.util.Iterator;
import javax.ejb.EJBException;
import javax.ejb.SessionContext;
import javax.swing.tree.DefaultMutableTreeNode;

public class RoleEditorSessionSEJB extends AbstractSession {

   private static final String DEPENDANTS_FOR_GET_ITEM_DATA = "<0>";
   private static final String DEPENDANTS_FOR_INSERT = "";
   private static final String DEPENDANTS_FOR_COPY = "<0>";
   private static final String DEPENDANTS_FOR_UPDATE = "<0>";
   private static final String DEPENDANTS_FOR_DELETE = "<0>";
   private transient Log mLog = new Log(this.getClass());
   private transient SessionContext mSessionContext;
   private transient RoleAccessor mRoleAccessor;
   private RoleEditorSessionSSO mSSO;
   private RolePK mThisTableKey;
   private RoleEVO mRoleEVO;


   public RoleEditorSessionSSO getItemData(int userId, Object paramKey) throws ValidationException, EJBException {
      this.setUserId(userId);
      if(this.mLog.isDebugEnabled()) {
         this.mLog.debug("getItemData", paramKey);
      }

      Timer timer = this.mLog.isInfoEnabled()?new Timer(this.mLog):null;
      this.mThisTableKey = (RolePK)paramKey;

      RoleEditorSessionSSO e;
      try {
         this.mRoleEVO = this.getRoleAccessor().getDetails(this.mThisTableKey, "<0>");
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
      this.mSSO = new RoleEditorSessionSSO();
      RoleImpl editorData = this.buildRoleEditData(this.mThisTableKey);
      this.completeGetItemData(editorData);
      this.mSSO.setEditorData(editorData);
   }

   private void completeGetItemData(RoleImpl editorData) throws Exception {}

   private RoleImpl buildRoleEditData(Object thisKey) throws Exception {
      RoleImpl editorData = new RoleImpl(thisKey);
      editorData.setVisId(this.mRoleEVO.getVisId());
      editorData.setDescription(this.mRoleEVO.getDescription());
      editorData.setVersionNum(this.mRoleEVO.getVersionNum());
      this.completeRoleEditData(editorData);
      return editorData;
   }

   private void completeRoleEditData(RoleImpl editorData) throws Exception {}

   public RoleEditorSessionSSO getNewItemData(int userId) throws EJBException {
      this.mLog.debug("getNewItemData");
      this.setUserId(userId);
      Timer timer = this.mLog.isInfoEnabled()?new Timer(this.mLog):null;

      RoleEditorSessionSSO var4;
      try {
         this.mSSO = new RoleEditorSessionSSO();
         RoleImpl e = new RoleImpl((Object)null);
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

   private void completeGetNewItemData(RoleImpl editorData) throws Exception {}

   public RolePK insert(RoleEditorSessionCSO cso) throws ValidationException, EJBException {
      this.mLog.debug("insert");
      Timer timer = this.mLog.isInfoEnabled()?new Timer(this.mLog):null;
      this.setUserId(cso.getUserId());
      RoleImpl editorData = cso.getEditorData();

      RolePK e;
      try {
         if(!this.isRoleSecuritySelected((DefaultMutableTreeNode)editorData.getTreeRoot())) {
            throw new ValidationException("No security string is selected. Select at leat one security string");
         }

         this.mRoleEVO = new RoleEVO();
         this.mRoleEVO.setVisId(editorData.getVisId());
         this.mRoleEVO.setDescription(editorData.getDescription());
         this.updateRoleRelationships(editorData);
         this.completeInsertSetup(editorData);
         this.validateInsert();
         this.mRoleEVO = this.getRoleAccessor().create(this.mRoleEVO);
         this.insertIntoAdditionalTables(editorData, true);
         this.sendEntityEventMessage("Role", this.mRoleEVO.getPK(), 1);
         e = this.mRoleEVO.getPK();
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

   private void updateRoleRelationships(RoleImpl editorData) throws ValidationException {}

   private void completeInsertSetup(RoleImpl editorData) throws Exception {
      this.doInsert((DefaultMutableTreeNode)editorData.getTreeRoot());
   }

   private void insertIntoAdditionalTables(RoleImpl editorData, boolean isInsert) throws Exception {}

   private void validateInsert() throws ValidationException {}

   public RolePK copy(RoleEditorSessionCSO cso) throws ValidationException, EJBException {
      this.mLog.debug("copy");
      Timer timer = this.mLog.isInfoEnabled()?new Timer(this.mLog):null;
      this.setUserId(cso.getUserId());
      RoleImpl editorData = cso.getEditorData();
      this.mThisTableKey = (RolePK)editorData.getPrimaryKey();

      RolePK var5;
      try {
         RoleEVO e = this.getRoleAccessor().getDetails(this.mThisTableKey, "<0>");
         this.mRoleEVO = e.deepClone();
         this.mRoleEVO.setVisId(editorData.getVisId());
         this.mRoleEVO.setDescription(editorData.getDescription());
         this.mRoleEVO.setVersionNum(0);
         this.updateRoleRelationships(editorData);
         this.completeCopySetup(editorData);
         this.validateCopy();
         this.mRoleEVO.prepareForInsert();
         this.mRoleEVO = this.getRoleAccessor().create(this.mRoleEVO);
         this.mThisTableKey = this.mRoleEVO.getPK();
         this.insertIntoAdditionalTables(editorData, false);
         this.sendEntityEventMessage("Role", this.mRoleEVO.getPK(), 1);
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

   private void completeCopySetup(RoleImpl editorData) throws Exception {
      this.completeUpdateSetup(editorData);
   }

   public void update(RoleEditorSessionCSO cso) throws ValidationException, EJBException {
      this.mLog.debug("update");
      Timer timer = this.mLog.isInfoEnabled()?new Timer(this.mLog):null;
      this.setUserId(cso.getUserId());
      RoleImpl editorData = cso.getEditorData();
      this.mThisTableKey = (RolePK)editorData.getPrimaryKey();

      try {
         this.mRoleEVO = this.getRoleAccessor().getDetails(this.mThisTableKey, "<0>");
         this.preValidateUpdate(editorData);
         this.mRoleEVO.setVisId(editorData.getVisId());
         this.mRoleEVO.setDescription(editorData.getDescription());
         if(editorData.getVersionNum() != this.mRoleEVO.getVersionNum()) {
            throw new VersionValidationException(this.mThisTableKey + " expected:" + editorData.getVersionNum() + " found:" + this.mRoleEVO.getVersionNum());
         }

         this.updateRoleRelationships(editorData);
         this.completeUpdateSetup(editorData);
         this.postValidateUpdate();
         this.getRoleAccessor().setDetails(this.mRoleEVO);
         this.updateAdditionalTables(editorData);
         this.sendEntityEventMessage("Role", this.mRoleEVO.getPK(), 3);
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

   private void preValidateUpdate(RoleImpl editorData) throws ValidationException {
      if(!this.isRoleSecuritySelected((DefaultMutableTreeNode)editorData.getTreeRoot())) {
         throw new ValidationException("No security string is selected. Select at leat one security string");
      }
   }

   private void postValidateUpdate() throws ValidationException {}

   private void completeUpdateSetup(RoleImpl editorData) throws Exception {
      this.doUpdate((DefaultMutableTreeNode)editorData.getTreeRoot());
   }

   private void updateAdditionalTables(RoleImpl editorData) throws Exception {}

   public void delete(int userId, Object paramKey) throws ValidationException, EJBException {
      if(this.mLog.isDebugEnabled()) {
         this.mLog.debug("delete", paramKey);
      }

      Timer timer = this.mLog.isInfoEnabled()?new Timer(this.mLog):null;
      this.setUserId(userId);
      this.mThisTableKey = (RolePK)paramKey;

      try {
         this.mRoleEVO = this.getRoleAccessor().getDetails(this.mThisTableKey, "<0>");
         this.validateDelete();
         this.deleteDataFromOtherTables();
         this.mRoleAccessor.remove(this.mThisTableKey);
         this.sendEntityEventMessage("Role", this.mThisTableKey, 2);
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
      if(!this.mRoleEVO.getVisId().equals("SystemAdministrator") && !this.mRoleEVO.getVisId().equals("BudgetController") && !this.mRoleEVO.getVisId().equals("BudgetApprover") && !this.mRoleEVO.getVisId().equals("BudgetHolder")) {
         UserRoleDAO dao = new UserRoleDAO();
         AllRolesForUsersELO elo = dao.getAllRolesForUsers();
         int numRows = elo.getNumRows();

         for(int i = 0; i < numRows; ++i) {
            Integer id = (Integer)elo.getValueAt(i, "RoleId");
            if(this.mRoleEVO.getPK().getRoleId() == id.intValue()) {
               throw new ValidationException("Role is in use by at least one User");
            }
         }

      } else {
         throw new ValidationException("Shipped Roles may not be removed");
      }
   }

   public void ejbCreate() throws EJBException {}

   public void ejbRemove() {}

   public void setSessionContext(SessionContext context) {
      this.mSessionContext = context;
   }

   public void ejbActivate() {}

   public void ejbPassivate() {}

   private RoleAccessor getRoleAccessor() throws Exception {
      if(this.mRoleAccessor == null) {
         this.mRoleAccessor = new RoleAccessor(this.getInitialContext());
      }

      return this.mRoleAccessor;
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

   private boolean isSecurityIdInUse(int id) {
      if(this.mRoleEVO.getRoleSecurity() == null) {
         return false;
      } else {
         Iterator current = this.mRoleEVO.getRoleSecurity().iterator();

         RoleSecurityRelEVO evo;
         do {
            if(!current.hasNext()) {
               return false;
            }

            evo = (RoleSecurityRelEVO)current.next();
         } while(evo.getRoleSecurityId() != id);

         return true;
      }
   }

   private void doUpdate(DefaultMutableTreeNode parentNode) {
      Enumeration e = parentNode.children();
      RoleSecurityRelEVO relEVO = null;
      RoleSecurityRelPK relPK = null;

      while(e.hasMoreElements()) {
         DefaultMutableTreeNode childNode = (DefaultMutableTreeNode)e.nextElement();
         if(childNode.isLeaf()) {
            if(childNode.getUserObject() instanceof RoleSecuritySelection) {
               RoleSecuritySelection nodeObject = (RoleSecuritySelection)childNode.getUserObject();
               RoleSecurityRef ref = (RoleSecurityRef)nodeObject.getRoleSecurity();
               RoleSecurityPK refPK = (RoleSecurityPK)ref.getPrimaryKey();
               if(nodeObject.isSelected()) {
                  if(!this.isSecurityIdInUse(refPK.getRoleSecurityId())) {
                     relEVO = new RoleSecurityRelEVO();
                     relEVO.setRoleId(this.mRoleEVO.getRoleId());
                     relEVO.setRoleSecurityId(refPK.getRoleSecurityId());
                     this.mRoleEVO.addRoleSecurityItem(relEVO);
                  }
               } else if(this.isSecurityIdInUse(refPK.getRoleSecurityId())) {
                  relPK = new RoleSecurityRelPK(this.mRoleEVO.getRoleId(), refPK.getRoleSecurityId());
                  this.mRoleEVO.deleteRoleSecurityItem(relPK);
               }
            }
         } else {
            this.doUpdate(childNode);
         }
      }

   }

   private void doInsert(DefaultMutableTreeNode parentNode) {
      Enumeration e = parentNode.preorderEnumeration();

      while(e.hasMoreElements()) {
         DefaultMutableTreeNode childNode = (DefaultMutableTreeNode)e.nextElement();
         if(childNode.isLeaf() && childNode.getUserObject() instanceof RoleSecuritySelection) {
            RoleSecuritySelection nodeObject = (RoleSecuritySelection)childNode.getUserObject();
            if(nodeObject.isSelected()) {
               RoleSecurityRelEVO evo = new RoleSecurityRelEVO();
               RoleSecurityRef ref = (RoleSecurityRef)nodeObject.getRoleSecurity();
               RoleSecurityPK refPK = (RoleSecurityPK)ref.getPrimaryKey();
               evo.setRoleId(this.mRoleEVO.getRoleId());
               evo.setRoleSecurityId(refPK.getRoleSecurityId());
               this.mRoleEVO.addRoleSecurityItem(evo);
            }
         }
      }

   }

   private boolean isRoleSecuritySelected(DefaultMutableTreeNode parentNode) {
      Enumeration e = parentNode.preorderEnumeration();

      while(e.hasMoreElements()) {
         DefaultMutableTreeNode childNode = (DefaultMutableTreeNode)e.nextElement();
         if(childNode.isLeaf() && childNode.getUserObject() instanceof RoleSecuritySelection) {
            RoleSecuritySelection nodeObject = (RoleSecuritySelection)childNode.getUserObject();
            if(nodeObject.isSelected()) {
               return true;
            }
         }
      }

      return false;
   }
}
