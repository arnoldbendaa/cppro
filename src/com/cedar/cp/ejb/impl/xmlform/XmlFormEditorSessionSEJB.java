// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:27:37
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.impl.xmlform;

import com.cedar.cp.api.base.EntityList;
import com.cedar.cp.api.base.EntityRef;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.base.VersionValidationException;
import com.cedar.cp.api.model.cc.CcDeploymentRef;
import com.cedar.cp.api.user.UserRef;
import com.cedar.cp.api.xmlform.FormDeploymentData;
import com.cedar.cp.api.xmlform.XmlFormWizardParameters;
import com.cedar.cp.dto.base.EntityEventMessage;
import com.cedar.cp.dto.base.PrimaryKey;
import com.cedar.cp.dto.datatype.PickerDataTypesFinCubeELO;
import com.cedar.cp.dto.dimension.calendar.CalendarInfoImpl;
import com.cedar.cp.dto.model.BudgetCycleIntegrityELO;
import com.cedar.cp.dto.model.CellCalcIntegrityELO;
import com.cedar.cp.dto.model.CellCalcRebuildTaskRequest;
import com.cedar.cp.dto.model.cc.CcDeploymentCK;
import com.cedar.cp.dto.model.cc.CcDeploymentsForXmlFormELO;
import com.cedar.cp.dto.report.definition.CheckFormIsUsedELO;
import com.cedar.cp.dto.user.AllDataEntryProfilesForFormELO;
import com.cedar.cp.dto.user.AllUsersELO;
import com.cedar.cp.dto.user.UserPK;
import com.cedar.cp.dto.xmlform.FormDeploymentTaskRequest;
import com.cedar.cp.dto.xmlform.XmlFormEditorSessionCSO;
import com.cedar.cp.dto.xmlform.XmlFormEditorSessionSSO;
import com.cedar.cp.dto.xmlform.XmlFormImpl;
import com.cedar.cp.dto.xmlform.XmlFormPK;
import com.cedar.cp.dto.xmlform.XmlFormWizardParametersImpl;
import com.cedar.cp.ejb.impl.base.AbstractSession;
import com.cedar.cp.ejb.impl.dataentry.DataEntryContextDAO;
import com.cedar.cp.ejb.impl.datatype.DataTypeDAO;
import com.cedar.cp.ejb.impl.dimension.StructureElementDAO;
import com.cedar.cp.ejb.impl.message.MessageAccessor;
import com.cedar.cp.ejb.impl.message.ServerSideUserMailer;
import com.cedar.cp.ejb.impl.model.BudgetCycleDAO;
import com.cedar.cp.ejb.impl.model.CellCalcDAO;
import com.cedar.cp.ejb.impl.model.cc.CcDeploymentDAO;
import com.cedar.cp.ejb.impl.report.definition.ReportDefFormDAO;
import com.cedar.cp.ejb.impl.user.DataEntryProfileDAO;
import com.cedar.cp.ejb.impl.user.UserAccessor;
import com.cedar.cp.ejb.impl.user.UserDAO;
import com.cedar.cp.ejb.impl.xmlform.XmlFormAccessor;
import com.cedar.cp.ejb.impl.xmlform.XmlFormDAO;
import com.cedar.cp.ejb.impl.xmlform.XmlFormEVO;
import com.cedar.cp.ejb.impl.xmlform.XmlFormUserLinkEVO;
import com.cedar.cp.util.DefaultValueMapping;
import com.cedar.cp.util.Log;
import com.cedar.cp.util.Timer;
import com.cedar.cp.util.ValueMapping;
import com.cedar.cp.util.XmlUtils;
import com.cedar.cp.util.common.JmsConnectionImpl;
import com.cedar.cp.util.performance.PerformanceDatumImpl;
import com.cedar.cp.util.task.TaskMessageFactory;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.ejb.EJBException;
import javax.ejb.SessionContext;
import javax.naming.InitialContext;

public class XmlFormEditorSessionSEJB extends AbstractSession {

   private static final String DEPENDANTS_FOR_GET_ITEM_DATA = "<0>";
   private static final String DEPENDANTS_FOR_INSERT = "";
   private static final String DEPENDANTS_FOR_COPY = "<0>";
   private static final String DEPENDANTS_FOR_UPDATE = "<0>";
   private static final String DEPENDANTS_FOR_DELETE = "<0>";
   private transient UserAccessor mUserccessor;
   private transient MessageAccessor mMessageAccessor;
   private transient Log mLog = new Log(this.getClass());
   private transient SessionContext mSessionContext;
   private transient XmlFormAccessor mXmlFormAccessor;
   private XmlFormEditorSessionSSO mSSO;
   private XmlFormPK mThisTableKey;
   private XmlFormEVO mXmlFormEVO;


   public XmlFormEditorSessionSSO getItemData(int userId, Object paramKey) throws ValidationException, EJBException {
      this.setUserId(userId);
      if(this.mLog.isDebugEnabled()) {
         this.mLog.debug("getItemData", paramKey);
      }

      Timer timer = this.mLog.isInfoEnabled()?new Timer(this.mLog):null;
      this.mThisTableKey = (XmlFormPK)paramKey;

      XmlFormEditorSessionSSO e;
      try {
         this.mXmlFormEVO = this.getXmlFormAccessor().getDetails(this.mThisTableKey, "<0>");
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
      this.mSSO = new XmlFormEditorSessionSSO();
      XmlFormImpl editorData = this.buildXmlFormEditData(this.mThisTableKey);
      this.completeGetItemData(editorData);
      this.mSSO.setEditorData(editorData);
   }

   private void completeGetItemData(XmlFormImpl editorData) throws Exception {}

   private XmlFormImpl buildXmlFormEditData(Object thisKey) throws Exception {
      XmlFormImpl editorData = new XmlFormImpl(thisKey);
      editorData.setVisId(this.mXmlFormEVO.getVisId());
      editorData.setDescription(this.mXmlFormEVO.getDescription());
      editorData.setType(this.mXmlFormEVO.getType());
      editorData.setDesignMode(this.mXmlFormEVO.getDesignMode());
      editorData.setFinanceCubeId(this.mXmlFormEVO.getFinanceCubeId());
      editorData.setDefinition(this.mXmlFormEVO.getDefinition());
      editorData.setExcelFile(this.mXmlFormEVO.getExcelFile());
      editorData.setJsonForm(this.mXmlFormEVO.getJsonForm());
      editorData.setSecurityAccess(this.mXmlFormEVO.getSecurityAccess());
      editorData.setVersionNum(this.mXmlFormEVO.getVersionNum());
      this.completeXmlFormEditData(editorData);
      return editorData;
   }

   private void completeXmlFormEditData(XmlFormImpl editorData) throws Exception {
      //editorData.setDefinition(XmlUtils.prettyPrint(editorData.getDefinition()));
      ArrayList users = new ArrayList();
      ArrayList names = new ArrayList();
      UserDAO userDAO = new UserDAO();
      Map userMap = userDAO.getMapOfAllUsers();
      Iterator iter = this.mXmlFormEVO.getUserList().iterator();

      while(iter.hasNext()) {
         XmlFormUserLinkEVO evo = (XmlFormUserLinkEVO)iter.next();
         UserPK userPK = new UserPK(evo.getUserId());
         AllUsersELO allUserELO = (AllUsersELO)userMap.get(userPK);
         if(allUserELO != null) {
            allUserELO.reset();
            allUserELO.next();
            //users.add(new UserRefImpl(userPK, allUserELO.getFullName()));
            users.add(allUserELO.getUserEntityRef());
            names.add(allUserELO.getFullName());
         }
      }

      editorData.setUserList(users);
      editorData.setNamesList(names);
   }

   public XmlFormEditorSessionSSO getNewItemData(int userId) throws EJBException {
      this.mLog.debug("getNewItemData");
      this.setUserId(userId);
      Timer timer = this.mLog.isInfoEnabled()?new Timer(this.mLog):null;

      XmlFormEditorSessionSSO var4;
      try {
         this.mSSO = new XmlFormEditorSessionSSO();
         XmlFormImpl e = new XmlFormImpl((Object)null);
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

   private void completeGetNewItemData(XmlFormImpl editorData) throws Exception {}

   public XmlFormPK insert(XmlFormEditorSessionCSO cso) throws ValidationException, EJBException {
      this.mLog.debug("insert");
      Timer timer = this.mLog.isInfoEnabled()?new Timer(this.mLog):null;
      this.setUserId(cso.getUserId());
      XmlFormImpl editorData = cso.getEditorData();

      XmlFormPK e;
      try {
         this.mXmlFormEVO = new XmlFormEVO();
         this.mXmlFormEVO.setVisId(editorData.getVisId());
         this.mXmlFormEVO.setDescription(editorData.getDescription());
         this.mXmlFormEVO.setType(editorData.getType());
         this.mXmlFormEVO.setDesignMode(editorData.isDesignMode());
         this.mXmlFormEVO.setFinanceCubeId(editorData.getFinanceCubeId());
         this.mXmlFormEVO.setDefinition(editorData.getDefinition());
         this.mXmlFormEVO.setExcelFile(editorData.getExcelFile());
         this.mXmlFormEVO.setJsonForm(editorData.getJsonForm());
         this.mXmlFormEVO.setSecurityAccess(editorData.isSecurityAccess());
         this.updateXmlFormRelationships(editorData);
         this.completeInsertSetup(editorData);
         this.validateInsert();
         this.mXmlFormEVO = this.getXmlFormAccessor().create(this.mXmlFormEVO);
         this.insertIntoAdditionalTables(editorData, true);
         this.sendEntityEventMessage("XmlForm", this.mXmlFormEVO.getPK(), 1);
         e = this.mXmlFormEVO.getPK();
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

   private void updateXmlFormRelationships(XmlFormImpl editorData) throws ValidationException {}

   private void completeInsertSetup(XmlFormImpl editorData) throws Exception {
      try {
         if(editorData.getType() < 4) {
            XmlUtils.validateFinanceForm(editorData.getDefinition());
            editorData.setDefinition(XmlUtils.normaliseDoc(editorData.getDefinition()));
         }
      } catch (Exception var6) {
         throw new ValidationException(var6.getMessage());
      }

      Iterator iter = editorData.getUserList().iterator();

      while(iter.hasNext()) {
         UserRef user = (UserRef)iter.next();
         UserPK pk = (UserPK)user.getPrimaryKey();
         XmlFormUserLinkEVO evo = new XmlFormUserLinkEVO();
         evo.setUserId(pk.getUserId());
         this.mXmlFormEVO.addUserListItem(evo);
      }

   }

   private void insertIntoAdditionalTables(XmlFormImpl editorData, boolean isInsert) throws Exception {}

   private void validateInsert() throws ValidationException {}

   public XmlFormPK copy(XmlFormEditorSessionCSO cso) throws ValidationException, EJBException {
      this.mLog.debug("copy");
      Timer timer = this.mLog.isInfoEnabled()?new Timer(this.mLog):null;
      this.setUserId(cso.getUserId());
      XmlFormImpl editorData = cso.getEditorData();
      this.mThisTableKey = (XmlFormPK)editorData.getPrimaryKey();

      XmlFormPK var5;
      try {
         XmlFormEVO e = this.getXmlFormAccessor().getDetails(this.mThisTableKey, "<0>");
         this.mXmlFormEVO = e.deepClone();
         this.mXmlFormEVO.setVisId(editorData.getVisId());
         this.mXmlFormEVO.setDescription(editorData.getDescription());
         this.mXmlFormEVO.setType(editorData.getType());
         this.mXmlFormEVO.setDesignMode(editorData.isDesignMode());
         this.mXmlFormEVO.setFinanceCubeId(editorData.getFinanceCubeId());
         this.mXmlFormEVO.setDefinition(editorData.getDefinition());
         this.mXmlFormEVO.setExcelFile(editorData.getExcelFile());
         this.mXmlFormEVO.setJsonForm(editorData.getJsonForm());
         this.mXmlFormEVO.setSecurityAccess(editorData.isSecurityAccess());
         this.mXmlFormEVO.setVersionNum(0);
         this.updateXmlFormRelationships(editorData);
         this.completeCopySetup(editorData);
         this.validateCopy();
         this.mXmlFormEVO.prepareForInsert();
         this.mXmlFormEVO = this.getXmlFormAccessor().create(this.mXmlFormEVO);
         this.mThisTableKey = this.mXmlFormEVO.getPK();
         this.insertIntoAdditionalTables(editorData, false);
         this.sendEntityEventMessage("XmlForm", this.mXmlFormEVO.getPK(), 1);
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

   private void completeCopySetup(XmlFormImpl editorData) throws Exception {}

   public void update(XmlFormEditorSessionCSO cso) throws ValidationException, EJBException {
      this.mLog.debug("update");
      Timer timer = this.mLog.isInfoEnabled()?new Timer(this.mLog):null;
      this.setUserId(cso.getUserId());
      XmlFormImpl editorData = cso.getEditorData();
      this.mThisTableKey = (XmlFormPK)editorData.getPrimaryKey();

      try {
         this.mXmlFormEVO = this.getXmlFormAccessor().getDetails(this.mThisTableKey, "<0>");
         this.preValidateUpdate(editorData);
         this.mXmlFormEVO.setVisId(editorData.getVisId());
         this.mXmlFormEVO.setDescription(editorData.getDescription());
         this.mXmlFormEVO.setType(editorData.getType());
         this.mXmlFormEVO.setDesignMode(editorData.isDesignMode());
         this.mXmlFormEVO.setFinanceCubeId(editorData.getFinanceCubeId());
         this.mXmlFormEVO.setDefinition(editorData.getDefinition());
         this.mXmlFormEVO.setExcelFile(editorData.getExcelFile());
         this.mXmlFormEVO.setJsonForm(editorData.getJsonForm());
         this.mXmlFormEVO.setSecurityAccess(editorData.isSecurityAccess());
         if(editorData.getVersionNum() != this.mXmlFormEVO.getVersionNum()) {
            throw new VersionValidationException(this.mThisTableKey + " expected:" + editorData.getVersionNum() + " found:" + this.mXmlFormEVO.getVersionNum());
         }

         this.updateXmlFormRelationships(editorData);
         this.completeUpdateSetup(editorData);
         this.postValidateUpdate();
         this.getXmlFormAccessor().setDetails(this.mXmlFormEVO);
         this.updateAdditionalTables(editorData);
         this.sendEntityEventMessage("XmlForm", this.mXmlFormEVO.getPK(), 3);
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

   private void preValidateUpdate(XmlFormImpl editorData) throws ValidationException {
      try {
         if(editorData.getType() < 4) {
            XmlUtils.validateFinanceForm(editorData.getDefinition());
            editorData.setDefinition(XmlUtils.normaliseDoc(editorData.getDefinition()));
         }

      } catch (Exception var3) {
         throw new ValidationException(var3.getMessage());
      }
   }

   private void postValidateUpdate() throws ValidationException {}

   private void completeUpdateSetup(XmlFormImpl editorData) throws Exception {
      boolean add = true;
      Iterator iter = editorData.getUserList().iterator();

      XmlFormUserLinkEVO userEvo1;
      XmlFormUserLinkEVO userEvo2;
      Iterator innerIter;
      while(iter.hasNext()) {
         UserRef remove = (UserRef)iter.next();
         UserPK ref = (UserPK)remove.getPrimaryKey();
         userEvo1 = new XmlFormUserLinkEVO(this.mXmlFormEVO.getXmlFormId(), ref.getUserId());
         innerIter = this.mXmlFormEVO.getUserList().iterator();
         add = true;

         while(true) {
            if(innerIter.hasNext()) {
               userEvo2 = (XmlFormUserLinkEVO)innerIter.next();
               if(userEvo1.getUserId() != userEvo2.getUserId()) {
                  continue;
               }

               add = false;
            }

            if(add) {
               this.mXmlFormEVO.addUserListItem(userEvo1);
            }
            break;
         }
      }

      boolean remove1 = true;
      iter = this.mXmlFormEVO.getUserList().iterator();

      while(iter.hasNext()) {
         userEvo1 = (XmlFormUserLinkEVO)iter.next();
         innerIter = editorData.getUserList().iterator();
         remove1 = true;

         while(true) {
            if(innerIter.hasNext()) {
               UserRef ref1 = (UserRef)innerIter.next();
               UserPK pk = (UserPK)ref1.getPrimaryKey();
               userEvo2 = new XmlFormUserLinkEVO(this.mXmlFormEVO.getXmlFormId(), pk.getUserId());
               if(userEvo1.getUserId() != userEvo2.getUserId()) {
                  continue;
               }

               remove1 = false;
            }

            if(remove1) {
               this.mXmlFormEVO.deleteUserListItem(userEvo1.getPK());
            }
            break;
         }
      }

   }

   private void updateAdditionalTables(XmlFormImpl editorData) throws Exception {
      this.checkXmlFormChanges();
   }

   public void delete(int userId, Object paramKey) throws ValidationException, EJBException {
      if(this.mLog.isDebugEnabled()) {
         this.mLog.debug("delete", paramKey);
      }

      Timer timer = this.mLog.isInfoEnabled()?new Timer(this.mLog):null;
      this.setUserId(userId);
      this.mThisTableKey = (XmlFormPK)paramKey;

      try {
         this.mXmlFormEVO = this.getXmlFormAccessor().getDetails(this.mThisTableKey, "<0>");
         this.validateDelete();
         this.deleteDataFromOtherTables();
         this.mXmlFormAccessor.remove(this.mThisTableKey);
         this.sendEntityEventMessage("XmlForm", this.mThisTableKey, 2);
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
      if(this.mXmlFormEVO.isForceDelete()) {
         DataEntryProfileDAO dao = new DataEntryProfileDAO();
         dao.deleteFormsforXMLFormId(this.mXmlFormEVO.getXmlFormId());
      }

   }

   private void validateDelete() throws ValidationException, Exception {
      int myId = this.mXmlFormEVO.getPK().getXmlFormId();
      CellCalcDAO ccdao = new CellCalcDAO();
      CellCalcIntegrityELO elo = ccdao.getCellCalcIntegrity();
      int numRows = elo.getNumRows();

      for(int bcDAO = 0; bcDAO < numRows; ++bcDAO) {
         Integer rdfDAO = (Integer)elo.getValueAt(bcDAO, "XmlformId");
         if(myId == rdfDAO.intValue()) {
            throw new ValidationException("XmlForm is in use by Cell Calculation");
         }
      }

      BudgetCycleDAO var11 = new BudgetCycleDAO();
      BudgetCycleIntegrityELO var8 = var11.getBudgetCycleIntegrity();
      numRows = var8.getNumRows();

      for(int var12 = 0; var12 < numRows; ++var12) {
         Integer depDao = (Integer)var8.getValueAt(var12, "XmlFormId");
         if(myId == depDao.intValue()) {
            throw new ValidationException("XmlForm is in use by Budget Cycle");
         }
      }

      ReportDefFormDAO var14 = new ReportDefFormDAO();
      CheckFormIsUsedELO var9 = var14.getCheckFormIsUsed(myId);
      numRows = var9.getNumRows();
      if(numRows > 0) {
         throw new ValidationException("XmlForm is in use by ReportDefinition");
      } else {
         if(!this.mXmlFormEVO.isForceDelete()) {
            DataEntryProfileDAO var13 = new DataEntryProfileDAO();
            AllDataEntryProfilesForFormELO var10 = var13.getAllDataEntryProfilesForForm(myId);
            if(var10.getNumRows() > 0) {
               throw new ValidationException("XmlForm is in use by a Data Entry Profile");
            }
         }

      }
   }

   public void ejbCreate() throws EJBException {}

   public void ejbRemove() {}

   public void setSessionContext(SessionContext context) {
      this.mSessionContext = context;
   }

   public void ejbActivate() {}

   public void ejbPassivate() {}

   private XmlFormAccessor getXmlFormAccessor() throws Exception {
      if(this.mXmlFormAccessor == null) {
         this.mXmlFormAccessor = new XmlFormAccessor(this.getInitialContext());
      }

      return this.mXmlFormAccessor;
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

   public Map invokeOnServer(List inputs) throws EJBException {
      try {
         DataEntryContextDAO e = new DataEntryContextDAO(Collections.EMPTY_MAP);
         return e.getLookupInputs((PerformanceDatumImpl)null, inputs);
      } catch (Exception var3) {
         throw new EJBException(var3);
      }
   }

   public XmlFormWizardParameters getFinanceXMLFormWizardData(int financeCubeId, int userId) {
      this.setUserId(userId);
      StructureElementDAO structureDao = new StructureElementDAO();
      CalendarInfoImpl calInfo = structureDao.getCalendarInfoForFinanceCube(financeCubeId);
      XmlFormWizardParametersImpl params = new XmlFormWizardParametersImpl();
      params.setAccountDimensionIndex(this.getAccountDimensionIndex(financeCubeId));
      int secondaryIndex = this.getSecondaryDimensionIndex(financeCubeId);
      params.setSecondaryDimensionIndex(secondaryIndex);
      params.setDataTypes(this.getDataTypes(financeCubeId));
      params.setAccountHierarchies(this.getAccountHierarchies(financeCubeId));
      if(secondaryIndex != -1) {
         params.setSecondaryHierarchies(this.getSecondaryHierarchies(financeCubeId, secondaryIndex));
      }

      params.setCalendarInfo(calInfo);
      return params;
   }

   private int getAccountDimensionIndex(int financeCubeId) {
      XmlFormDAO xmlFormDao = new XmlFormDAO();
      return xmlFormDao.getAccountDimensionIndexForFinanceCube(financeCubeId);
   }

   private int getSecondaryDimensionIndex(int financeCubeId) {
      XmlFormDAO xmlFormDao = new XmlFormDAO();
      return xmlFormDao.getSecondaryDimensionIndexForFinanceCube(financeCubeId);
   }

   private ValueMapping getAccountHierarchies(int financeCubeId) {
      XmlFormDAO xmlFormDao = new XmlFormDAO();
      return xmlFormDao.getAccountHierarchiesFinanceCube(financeCubeId);
   }

   private ValueMapping getSecondaryHierarchies(int financeCubeId, int dimensionId) {
      XmlFormDAO xmlFormDao = new XmlFormDAO();
      return xmlFormDao.getSecondaryHierarchiesFinanceCube(financeCubeId, dimensionId);
   }

   private ValueMapping getPeriodLeaves(int financeCubeId) {
      XmlFormDAO xmlFormDao = new XmlFormDAO();
      return xmlFormDao.getPeriodLeavesForFinanceCube(financeCubeId);
   }

   private ValueMapping getDataTypes(int financeCubeId) {
      DataTypeDAO dataTypeDao = new DataTypeDAO();
      PickerDataTypesFinCubeELO list = dataTypeDao.getPickerDataTypesWeb(financeCubeId, new int[]{0, 1, 2, 4}, false);
      String[] literals = new String[list.getNumRows()];
      String[] values = new String[list.getNumRows()];

      for(int i = 0; i < list.getNumRows(); ++i) {
         String visId = (String)list.getValueAt(i, "VisId");
         String descr = (String)list.getValueAt(i, "Description");
         literals[i] = visId + " - " + descr;
         values[i] = visId;
      }

      return new DefaultValueMapping(literals, values);
   }

   private void checkXmlFormChanges() {
      if(this.mXmlFormEVO.isModified() && this.mXmlFormEVO.getType() < 3) {
         try {
            XmlFormDAO e = new XmlFormDAO();
            e.load(this.mXmlFormEVO.getPK());
            if(!this.mXmlFormEVO.getDefinition().equals(e.mDetails.getDefinition())) {
               CcDeploymentDAO ccDeploymentDao = new CcDeploymentDAO();
               CcDeploymentsForXmlFormELO cellCalcs = ccDeploymentDao.getCcDeploymentsForXmlForm(this.mXmlFormEVO.getPK().getXmlFormId());

               for(int i = 0; i < cellCalcs.getNumRows(); ++i) {
                  CcDeploymentRef deploymentRef = (CcDeploymentRef)cellCalcs.getValueAt(i, "CcDeployment");
                  String description = (String)cellCalcs.getValueAt(i, "Description");
                  CellCalcRebuildTaskRequest request = new CellCalcRebuildTaskRequest(((CcDeploymentCK)deploymentRef.getPrimaryKey()).getModelPK().getModelId(), (CcDeploymentCK)deploymentRef.getPrimaryKey(), deploymentRef.getNarrative(), description);

                  try {
                     int e1 = TaskMessageFactory.issueNewTask(new InitialContext(), false, request, this.getUserId());
                     this.mLog.debug("issueCellCalcRebuild", "taskId=" + e1);
                  } catch (Exception var9) {
                     var9.printStackTrace();
                     throw new EJBException(var9);
                  }
               }
            }
         } catch (Exception var10) {
            this.mLog.warn("checkXmlFormChanges", "Unexpected error " + var10);
         }
      }

   }

   public void deleteFormAndProfiles(int userId, Object paramKey) throws ValidationException, EJBException {
      if(this.mLog.isDebugEnabled()) {
         this.mLog.debug("deleteFormAndProfiles", paramKey);
      }

      Timer timer = this.mLog.isInfoEnabled()?new Timer(this.mLog):null;
      this.setUserId(userId);
      this.mThisTableKey = (XmlFormPK)paramKey;

      try {
         this.mXmlFormEVO = this.getXmlFormAccessor().getDetails(this.mThisTableKey, "<0>");
         this.mXmlFormEVO.setForceDelete(true);
         this.validateDelete();
         this.deleteDataFromOtherTables();
         this.mXmlFormAccessor.remove(this.mThisTableKey);
         this.sendEntityEventMessage("XmlForm", this.mThisTableKey, 2);
      } catch (ValidationException var10) {
         throw var10;
      } catch (EJBException var11) {
         throw var11;
      } catch (Exception var12) {
         var12.printStackTrace();
         throw new EJBException(var12.getMessage());
      } finally {
         this.setUserId(0);
         if(timer != null) {
            timer.logInfo("deleteFormAndProfiles", this.mThisTableKey);
         }

      }

   }
   
	public void deleteFormProfiles(int userId, Object paramKey, String subject, String messageText) throws ValidationException, EJBException {
		if (mLog.isDebugEnabled()) {
			mLog.debug("deleteFormAndProfiles", paramKey);
		}
		Timer timer = mLog.isInfoEnabled() ? new Timer(mLog) : null;

		setUserId(userId);
		mThisTableKey = ((XmlFormPK) paramKey);
		try
		{
			mXmlFormEVO = getXmlFormAccessor().getDetails(mThisTableKey, "<0>");

			DataEntryProfileDAO dao = new DataEntryProfileDAO();

			List userIds = dao.selectProfileUserIdforXMLFormId(mXmlFormEVO.getXmlFormId());
			dao.deleteFormsforXMLFormId(mXmlFormEVO.getXmlFormId());

			if (!userIds.isEmpty())
			{
				ServerSideUserMailer ssum = new ServerSideUserMailer(getUserId());
				ssum.sendUsersMailMessage(userIds, subject, messageText);
			}
		} catch (ValidationException ve)
		{
			throw ve;
		} catch (EJBException e)
		{
			throw e;
		} catch (Exception e)
		{
			e.printStackTrace();
			throw new EJBException(e.getMessage());
		} finally
		{
			setUserId(0);
			if (timer != null)
				timer.logInfo("deleteFormAndProfiles", mThisTableKey);
		}
	}

    public void deleteFormProfiles(int userId, Object paramKey, String subject, String messageText, Boolean mobile) throws ValidationException, EJBException {
        if (mLog.isDebugEnabled()) {
            mLog.debug("deleteFormAndProfiles", paramKey);
        }
        Timer timer = mLog.isInfoEnabled() ? new Timer(mLog) : null;

        setUserId(userId);
        mThisTableKey = ((XmlFormPK) paramKey);
        try
        {
            mXmlFormEVO = getXmlFormAccessor().getDetails(mThisTableKey, "<0>");

            DataEntryProfileDAO dao = new DataEntryProfileDAO();

            List userIds = dao.selectProfileUserIdforXMLFormId(mXmlFormEVO.getXmlFormId());
            dao.deleteFormsforXMLFormId(mXmlFormEVO.getXmlFormId(), mobile);

            if (!userIds.isEmpty())
            {
                ServerSideUserMailer ssum = new ServerSideUserMailer(getUserId());
                ssum.sendUsersMailMessage(userIds, subject, messageText);
            }
        } catch (ValidationException ve)
        {
            throw ve;
        } catch (EJBException e)
        {
            throw e;
        } catch (Exception e)
        {
            e.printStackTrace();
            throw new EJBException(e.getMessage());
        } finally
        {
            setUserId(0);
            if (timer != null)
                timer.logInfo("deleteFormAndProfiles", mThisTableKey);
        }
    }
	
   public int processFormDeployment(int userId, FormDeploymentData data) throws ValidationException, EJBException {
      this.mThisTableKey = (XmlFormPK)data.getKey();

      try {
         this.mXmlFormEVO = this.getXmlFormAccessor().getDetails(this.mThisTableKey, "<0>");
         FormDeploymentTaskRequest e = new FormDeploymentTaskRequest();
         e.setXmlFormId(this.mXmlFormEVO.getXmlFormId());
         e.setData(data);
         return TaskMessageFactory.issueNewTask(new InitialContext(), false, e, userId);
      } catch (Exception var4) {
         var4.printStackTrace();
         throw new EJBException(var4);
      }
   }

   public int[] issueCellCalcRebuildTask(int userId, List<EntityRef> rebuildList) throws ValidationException {
      ArrayList taskIdList = new ArrayList();

      for(int taskIds = 0; taskIds < rebuildList.size(); ++taskIds) {
         XmlFormPK i = (XmlFormPK)((EntityRef)rebuildList.get(taskIds)).getPrimaryKey();
         CcDeploymentDAO ccDeploymentDao = new CcDeploymentDAO();
         CcDeploymentsForXmlFormELO cellCalcs = ccDeploymentDao.getCcDeploymentsForXmlForm(i.getXmlFormId());

         for(int j = 0; j < cellCalcs.getNumRows(); ++j) {
            CcDeploymentRef deploymentRef = (CcDeploymentRef)cellCalcs.getValueAt(j, "CcDeployment");
            String description = (String)cellCalcs.getValueAt(j, "Description");
            CellCalcRebuildTaskRequest request = new CellCalcRebuildTaskRequest(((CcDeploymentCK)deploymentRef.getPrimaryKey()).getModelPK().getModelId(), (CcDeploymentCK)deploymentRef.getPrimaryKey(), deploymentRef.getNarrative(), description);

            try {
               int e = TaskMessageFactory.issueNewTask(new InitialContext(), false, request, userId);
               taskIdList.add(Integer.valueOf(e));
               this.mLog.info("issueCellCalcRebuildTask", "taskId=" + e + " " + deploymentRef.getNarrative());
            } catch (Exception var13) {
               var13.printStackTrace();
               throw new EJBException(var13);
            }
         }
      }

      int[] var14 = new int[taskIdList.size()];

      for(int var15 = 0; var15 < taskIdList.size(); ++var15) {
         var14[var15] = ((Integer)taskIdList.get(var15)).intValue();
      }

      return var14;
   }
   
   public Object[] getExcelFile(Object pk) throws EJBException {
	   XmlFormDAO dao = new XmlFormDAO();
	   return dao.getExcelFile(pk);
   }
   
   public boolean saveJsonForm(Object pk, String json, int versionNumber, int userId) throws EJBException {
	   XmlFormDAO dao = new XmlFormDAO();
	   return dao.saveJsonForm(pk, json, versionNumber, userId);
   }

   private UserAccessor getUserAccessor() throws Exception {
      if(this.mUserccessor == null) {
         this.mUserccessor = new UserAccessor(this.getInitialContext());
      }

      return this.mUserccessor;
   }

   private MessageAccessor getMessageAccessor() throws Exception {
      if(this.mMessageAccessor == null) {
         this.mMessageAccessor = new MessageAccessor(this.getInitialContext());
      }

      return this.mMessageAccessor;
   }
}
