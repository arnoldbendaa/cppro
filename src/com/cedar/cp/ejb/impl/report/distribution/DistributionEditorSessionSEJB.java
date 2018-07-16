// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:27:15
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.impl.report.distribution;

import com.cedar.cp.api.base.EntityRef;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.base.VersionValidationException;
import com.cedar.cp.api.report.destination.external.ExternalDestinationRef;
import com.cedar.cp.api.report.destination.internal.InternalDestinationRef;
import com.cedar.cp.api.report.distribution.DistributionDetails;
import com.cedar.cp.dto.base.EntityEventMessage;
import com.cedar.cp.dto.base.PrimaryKey;
import com.cedar.cp.dto.dimension.BudgetLocationElementForModelELO;
import com.cedar.cp.dto.model.BudgetUsersForNodeELO;
import com.cedar.cp.dto.report.destination.external.AllExternalDestinationsELO;
import com.cedar.cp.dto.report.destination.external.AllUsersForExternalDestinationIdELO;
import com.cedar.cp.dto.report.destination.external.ExternalDestinationPK;
import com.cedar.cp.dto.report.destination.internal.AllInternalDestinationsELO;
import com.cedar.cp.dto.report.destination.internal.AllUsersForInternalDestinationIdELO;
import com.cedar.cp.dto.report.destination.internal.InternalDestinationPK;
import com.cedar.cp.dto.report.distribution.DistributionDetailImpl;
import com.cedar.cp.dto.report.distribution.DistributionDetailsForVisIdELO;
import com.cedar.cp.dto.report.distribution.DistributionDetailsImpl;
import com.cedar.cp.dto.report.distribution.DistributionEditorSessionCSO;
import com.cedar.cp.dto.report.distribution.DistributionEditorSessionSSO;
import com.cedar.cp.dto.report.distribution.DistributionImpl;
import com.cedar.cp.dto.report.distribution.DistributionPK;
import com.cedar.cp.dto.report.pack.CheckReportDistributionELO;
import com.cedar.cp.dto.systemproperty.SystemPropertyELO;
import com.cedar.cp.ejb.impl.base.AbstractSession;
import com.cedar.cp.ejb.impl.dimension.StructureElementDAO;
import com.cedar.cp.ejb.impl.model.BudgetUserDAO;
import com.cedar.cp.ejb.impl.report.destination.external.ExternalDestinationDAO;
import com.cedar.cp.ejb.impl.report.destination.internal.InternalDestinationDAO;
import com.cedar.cp.ejb.impl.report.distribution.DistributionAccessor;
import com.cedar.cp.ejb.impl.report.distribution.DistributionDAO;
import com.cedar.cp.ejb.impl.report.distribution.DistributionEVO;
import com.cedar.cp.ejb.impl.report.distribution.DistributionLinkEVO;
import com.cedar.cp.ejb.impl.report.pack.ReportPackLinkDAO;
import com.cedar.cp.ejb.impl.systemproperty.SystemPropertyDAO;
import com.cedar.cp.util.Log;
import com.cedar.cp.util.Timer;
import com.cedar.cp.util.common.JmsConnectionImpl;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.ejb.EJBException;
import javax.ejb.SessionContext;

public class DistributionEditorSessionSEJB extends AbstractSession {

   private static final String DEPENDANTS_FOR_GET_ITEM_DATA = "<0>";
   private static final String DEPENDANTS_FOR_INSERT = "";
   private static final String DEPENDANTS_FOR_COPY = "<0>";
   private static final String DEPENDANTS_FOR_UPDATE = "<0>";
   private static final String DEPENDANTS_FOR_DELETE = "<0>";
   private transient Log mLog = new Log(this.getClass());
   private transient SessionContext mSessionContext;
   private transient DistributionAccessor mDistributionAccessor;
   private DistributionEditorSessionSSO mSSO;
   private DistributionPK mThisTableKey;
   private DistributionEVO mDistributionEVO;


   public DistributionEditorSessionSSO getItemData(int userId, Object paramKey) throws ValidationException, EJBException {
      this.setUserId(userId);
      if(this.mLog.isDebugEnabled()) {
         this.mLog.debug("getItemData", paramKey);
      }

      Timer timer = this.mLog.isInfoEnabled()?new Timer(this.mLog):null;
      this.mThisTableKey = (DistributionPK)paramKey;

      DistributionEditorSessionSSO e;
      try {
         this.mDistributionEVO = this.getDistributionAccessor().getDetails(this.mThisTableKey, "<0>");
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
      this.mSSO = new DistributionEditorSessionSSO();
      DistributionImpl editorData = this.buildDistributionEditData(this.mThisTableKey);
      this.completeGetItemData(editorData);
      this.mSSO.setEditorData(editorData);
   }

   private void completeGetItemData(DistributionImpl editorData) throws Exception {}

   private DistributionImpl buildDistributionEditData(Object thisKey) throws Exception {
      DistributionImpl editorData = new DistributionImpl(thisKey);
      editorData.setVisId(this.mDistributionEVO.getVisId());
      editorData.setDescription(this.mDistributionEVO.getDescription());
      editorData.setRaDistribution(this.mDistributionEVO.getRaDistribution());
      editorData.setDirRoot(this.mDistributionEVO.getDirRoot());
      editorData.setVersionNum(this.mDistributionEVO.getVersionNum());
      this.completeDistributionEditData(editorData);
      return editorData;
   }

   private void completeDistributionEditData(DistributionImpl editorData) throws Exception {
      ArrayList intDist = new ArrayList();
      ArrayList extDist = new ArrayList();
      Iterator iter = this.mDistributionEVO.getDistributionDestinationList().iterator();
      InternalDestinationDAO intDAO = new InternalDestinationDAO();
      ExternalDestinationDAO extDAO = new ExternalDestinationDAO();

      while(iter.hasNext()) {
         DistributionLinkEVO evo = (DistributionLinkEVO)iter.next();
         int i;
         if(evo.getDestinationType().equals(DistributionLinkEVO.INTERNAL)) {
            InternalDestinationPK var12 = new InternalDestinationPK(evo.getDestinationId());
            AllInternalDestinationsELO var13 = intDAO.getAllInternalDestinations();

            for(i = 0; i < var13.getNumRows(); ++i) {
               InternalDestinationRef var14 = (InternalDestinationRef)var13.getValueAt(i, "InternalDestination");
               if(var14.getPrimaryKey().equals(var12)) {
                  intDist.add(var14);
                  break;
               }
            }
         } else {
            ExternalDestinationPK pk = new ExternalDestinationPK(evo.getDestinationId());
            AllExternalDestinationsELO eList = extDAO.getAllExternalDestinations();

            for(i = 0; i < eList.getNumRows(); ++i) {
               ExternalDestinationRef ref = (ExternalDestinationRef)eList.getValueAt(i, "ExternalDestination");
               if(ref.getPrimaryKey().equals(pk)) {
                  extDist.add(ref);
                  break;
               }
            }
         }
      }

      editorData.setInternalDestinationList(intDist);
      editorData.setExternalDestinationList(extDist);
   }

   public DistributionEditorSessionSSO getNewItemData(int userId) throws EJBException {
      this.mLog.debug("getNewItemData");
      this.setUserId(userId);
      Timer timer = this.mLog.isInfoEnabled()?new Timer(this.mLog):null;

      DistributionEditorSessionSSO var4;
      try {
         this.mSSO = new DistributionEditorSessionSSO();
         DistributionImpl e = new DistributionImpl((Object)null);
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

   private void completeGetNewItemData(DistributionImpl editorData) throws Exception {}

   public DistributionPK insert(DistributionEditorSessionCSO cso) throws ValidationException, EJBException {
      this.mLog.debug("insert");
      Timer timer = this.mLog.isInfoEnabled()?new Timer(this.mLog):null;
      this.setUserId(cso.getUserId());
      DistributionImpl editorData = cso.getEditorData();

      DistributionPK e;
      try {
         this.mDistributionEVO = new DistributionEVO();
         this.mDistributionEVO.setVisId(editorData.getVisId());
         this.mDistributionEVO.setDescription(editorData.getDescription());
         this.mDistributionEVO.setRaDistribution(editorData.isRaDistribution());
         this.mDistributionEVO.setDirRoot(editorData.getDirRoot());
         this.updateDistributionRelationships(editorData);
         this.completeInsertSetup(editorData);
         this.validateInsert();
         this.mDistributionEVO = this.getDistributionAccessor().create(this.mDistributionEVO);
         this.insertIntoAdditionalTables(editorData, true);
         this.sendEntityEventMessage("Distribution", this.mDistributionEVO.getPK(), 1);
         e = this.mDistributionEVO.getPK();
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

   private void updateDistributionRelationships(DistributionImpl editorData) throws ValidationException {}

   private void completeInsertSetup(DistributionImpl editorData) throws Exception {
      List dist = editorData.getInternalDestinationList();
      int distId = 0;

      int i;
      DistributionLinkEVO evo;
      for(i = 0; i < dist.size(); ++i) {
         evo = new DistributionLinkEVO();
         InternalDestinationRef ref = (InternalDestinationRef)dist.get(i);
         InternalDestinationPK pk = (InternalDestinationPK)ref.getPrimaryKey();
         --distId;
         evo.setDistributionLinkId(distId);
         evo.setDestinationId(pk.getInternalDestinationId());
         evo.setDestinationType(Integer.valueOf(0));
         this.mDistributionEVO.addDistributionDestinationListItem(evo);
      }

      dist = editorData.getExternalDestinationList();

      for(i = 0; i < dist.size(); ++i) {
         evo = new DistributionLinkEVO();
         ExternalDestinationRef var8 = (ExternalDestinationRef)dist.get(i);
         ExternalDestinationPK var9 = (ExternalDestinationPK)var8.getPrimaryKey();
         --distId;
         evo.setDistributionLinkId(distId);
         evo.setDestinationId(var9.getExternalDestinationId());
         evo.setDestinationType(Integer.valueOf(1));
         this.mDistributionEVO.addDistributionDestinationListItem(evo);
      }

   }

   private void insertIntoAdditionalTables(DistributionImpl editorData, boolean isInsert) throws Exception {}

   private void validateInsert() throws ValidationException {
      if((this.mDistributionEVO.getDirRoot() == null || this.mDistributionEVO.getDirRoot().length() == 0) && (this.mDistributionEVO.getDistributionDestinationList() == null || this.mDistributionEVO.getDistributionDestinationList().isEmpty()) && !this.mDistributionEVO.getRaDistribution()) {
         throw new ValidationException("No distribution chosen");
      }
   }

   public DistributionPK copy(DistributionEditorSessionCSO cso) throws ValidationException, EJBException {
      this.mLog.debug("copy");
      Timer timer = this.mLog.isInfoEnabled()?new Timer(this.mLog):null;
      this.setUserId(cso.getUserId());
      DistributionImpl editorData = cso.getEditorData();
      this.mThisTableKey = (DistributionPK)editorData.getPrimaryKey();

      DistributionPK var5;
      try {
         DistributionEVO e = this.getDistributionAccessor().getDetails(this.mThisTableKey, "<0>");
         this.mDistributionEVO = e.deepClone();
         this.mDistributionEVO.setVisId(editorData.getVisId());
         this.mDistributionEVO.setDescription(editorData.getDescription());
         this.mDistributionEVO.setRaDistribution(editorData.isRaDistribution());
         this.mDistributionEVO.setDirRoot(editorData.getDirRoot());
         this.mDistributionEVO.setVersionNum(0);
         this.updateDistributionRelationships(editorData);
         this.completeCopySetup(editorData);
         this.validateCopy();
         this.mDistributionEVO.prepareForInsert();
         this.mDistributionEVO = this.getDistributionAccessor().create(this.mDistributionEVO);
         this.mThisTableKey = this.mDistributionEVO.getPK();
         this.insertIntoAdditionalTables(editorData, false);
         this.sendEntityEventMessage("Distribution", this.mDistributionEVO.getPK(), 1);
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

   private void completeCopySetup(DistributionImpl editorData) throws Exception {}

   public void update(DistributionEditorSessionCSO cso) throws ValidationException, EJBException {
      this.mLog.debug("update");
      Timer timer = this.mLog.isInfoEnabled()?new Timer(this.mLog):null;
      this.setUserId(cso.getUserId());
      DistributionImpl editorData = cso.getEditorData();
      this.mThisTableKey = (DistributionPK)editorData.getPrimaryKey();

      try {
         this.mDistributionEVO = this.getDistributionAccessor().getDetails(this.mThisTableKey, "<0>");
         this.preValidateUpdate(editorData);
         this.mDistributionEVO.setVisId(editorData.getVisId());
         this.mDistributionEVO.setDescription(editorData.getDescription());
         this.mDistributionEVO.setRaDistribution(editorData.isRaDistribution());
         this.mDistributionEVO.setDirRoot(editorData.getDirRoot());
         if(editorData.getVersionNum() != this.mDistributionEVO.getVersionNum()) {
            throw new VersionValidationException(this.mThisTableKey + " expected:" + editorData.getVersionNum() + " found:" + this.mDistributionEVO.getVersionNum());
         }

         this.updateDistributionRelationships(editorData);
         this.completeUpdateSetup(editorData);
         this.postValidateUpdate();
         this.getDistributionAccessor().setDetails(this.mDistributionEVO);
         this.updateAdditionalTables(editorData);
         this.sendEntityEventMessage("Distribution", this.mDistributionEVO.getPK(), 3);
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

   private void preValidateUpdate(DistributionImpl editorData) throws ValidationException {}

   private void postValidateUpdate() throws ValidationException {}

   private void completeUpdateSetup(DistributionImpl editorData) throws Exception {
      Iterator iter = this.mDistributionEVO.getDistributionDestinationList().iterator();

      while(iter.hasNext()) {
         DistributionLinkEVO evo = (DistributionLinkEVO)iter.next();
         this.mDistributionEVO.deleteDistributionDestinationListItem(evo.getPK());
      }

      this.completeInsertSetup(editorData);
   }

   private void updateAdditionalTables(DistributionImpl editorData) throws Exception {}

   public void delete(int userId, Object paramKey) throws ValidationException, EJBException {
      if(this.mLog.isDebugEnabled()) {
         this.mLog.debug("delete", paramKey);
      }

      Timer timer = this.mLog.isInfoEnabled()?new Timer(this.mLog):null;
      this.setUserId(userId);
      this.mThisTableKey = (DistributionPK)paramKey;

      try {
         this.mDistributionEVO = this.getDistributionAccessor().getDetails(this.mThisTableKey, "<0>");
         this.validateDelete();
         this.deleteDataFromOtherTables();
         this.mDistributionAccessor.remove(this.mThisTableKey);
         this.sendEntityEventMessage("Distribution", this.mThisTableKey, 2);
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
      ReportPackLinkDAO rplDAO = new ReportPackLinkDAO();
      CheckReportDistributionELO eList = rplDAO.getCheckReportDistribution(this.mDistributionEVO.getDistributionId());
      if(eList.getNumRows() > 0) {
         throw new ValidationException("Distribution is in use by report pack");
      }
   }

   public void ejbCreate() throws EJBException {}

   public void ejbRemove() {}

   public void setSessionContext(SessionContext context) {
      this.mSessionContext = context;
   }

   public void ejbActivate() {}

   public void ejbPassivate() {}

   private DistributionAccessor getDistributionAccessor() throws Exception {
      if(this.mDistributionAccessor == null) {
         this.mDistributionAccessor = new DistributionAccessor(this.getInitialContext());
      }

      return this.mDistributionAccessor;
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

   public DistributionDetails getDistributionDetailList(int modelId, int budgetLocation, EntityRef ref) {
      DistributionDetailsImpl details = new DistributionDetailsImpl();
      StructureElementDAO strucDAO = new StructureElementDAO();
      BudgetLocationElementForModelELO eList = strucDAO.getBudgetLocationElementForModel(modelId, budgetLocation);
      StringBuilder blString = new StringBuilder();
      blString.append(eList.getValueAt(0, "VisId"));
      if(blString.length() > 0 && eList.getValueAt(0, "Description") != null) {
         blString.append(" - ");
      }

      blString.append(eList.getValueAt(0, "Description"));
      details.setBudgetLocation(blString.toString());
      DistributionDAO dDAO = new DistributionDAO();
      DistributionDetailsForVisIdELO dELO = dDAO.getDistributionDetailsForVisId(ref.getNarrative());
      boolean ra = false;
      String serverRootDir = "";

      DistributionDetailImpl detail;
      int i;
      while(dELO.hasNext()) {
         dELO.next();
         ra = dELO.getRaDistribution();
         serverRootDir = dELO.getDirRoot();
         if(serverRootDir != null && serverRootDir.length() > 0) {
            details.setServerRoot(serverRootDir);
         }

         detail = new DistributionDetailImpl();
         boolean spDAO = true;
         if(dELO.getDestinationType() == null) {
            spDAO = false;
         } else if(DistributionLinkEVO.INTERNAL.equals(dELO.getDestinationType())) {
            detail.setDistributionType(0);
            InternalDestinationDAO dao = new InternalDestinationDAO();
            AllUsersForInternalDestinationIdELO var17 = dao.getAllUsersForInternalDestinationId(dELO.getDestinationId());

            for(i = 0; i < var17.getNumRows(); ++i) {
               if(i == 0) {
                  detail.setMessageType(((Integer)var17.getValueAt(i, "MessageType")).intValue());
               }

               detail.addUser(var17.getValueAt(i, "User").toString());
            }
         } else if(DistributionLinkEVO.EXTERNAL.equals(dELO.getDestinationType())) {
            detail.setDistributionType(1);
            ExternalDestinationDAO var21 = new ExternalDestinationDAO();
            AllUsersForExternalDestinationIdELO var16 = var21.getAllUsersForExternalDestinationId(dELO.getDestinationId());

            for(i = 0; i < var16.getNumRows(); ++i) {
               detail.addUser(var16.getValueAt(i, "EmailAddress").toString());
            }
         }

         if(spDAO) {
            details.addDistributionDetail(detail);
         }
      }

      if(ra) {
         detail = new DistributionDetailImpl();
         detail.setDistributionType(2);
         SystemPropertyDAO var20 = new SystemPropertyDAO();
         SystemPropertyELO var19 = var20.getSystemProperty("WEB: Alert message type");
         detail.setMessageType(Integer.parseInt(var19.getValueAt(0, "Value").toString()));
         BudgetUserDAO var22 = new BudgetUserDAO();
         BudgetUsersForNodeELO var18 = var22.getBudgetUsersForNode(modelId, budgetLocation);

         for(i = 0; i < var18.getNumRows(); ++i) {
            detail.addUser(var18.getValueAt(i, "User").toString());
         }

         details.addDistributionDetail(detail);
      }

      return details;
   }
}
