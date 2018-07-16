// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:26:59
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.impl.model.virement;

import com.cedar.cp.api.base.CPException;
import com.cedar.cp.api.base.EntityList;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.base.VersionValidationException;
import com.cedar.cp.api.model.FinanceCubeRef;
import com.cedar.cp.api.model.ModelRef;
import com.cedar.cp.dto.base.EntityEventMessage;
import com.cedar.cp.dto.base.PrimaryKey;
import com.cedar.cp.dto.dimension.DimensionRefImpl;
import com.cedar.cp.dto.dimension.StructureElementByVisIdELO;
import com.cedar.cp.dto.dimension.StructureElementRefImpl;
import com.cedar.cp.dto.model.AllModelsELO;
import com.cedar.cp.dto.model.FinanceCubeRefImpl;
import com.cedar.cp.dto.model.ModelDimensionsELO;
import com.cedar.cp.dto.model.ModelPK;
import com.cedar.cp.dto.model.ModelRefImpl;
import com.cedar.cp.dto.model.virement.VirementAccountImpl;
import com.cedar.cp.dto.model.virement.VirementAccountPK;
import com.cedar.cp.dto.model.virement.VirementAccountRefImpl;
import com.cedar.cp.dto.model.virement.VirementCategoryCK;
import com.cedar.cp.dto.model.virement.VirementCategoryEditorSessionCSO;
import com.cedar.cp.dto.model.virement.VirementCategoryEditorSessionSSO;
import com.cedar.cp.dto.model.virement.VirementCategoryImpl;
import com.cedar.cp.dto.model.virement.VirementLocationCK;
import com.cedar.cp.dto.model.virement.VirementLocationImpl;
import com.cedar.cp.dto.model.virement.VirementLocationRefImpl;
import com.cedar.cp.ejb.impl.base.AbstractSession;
import com.cedar.cp.ejb.impl.dimension.StructureElementDAO;
import com.cedar.cp.ejb.impl.model.ModelAccessor;
import com.cedar.cp.ejb.impl.model.ModelDAO;
import com.cedar.cp.ejb.impl.model.ModelEVO;
import com.cedar.cp.ejb.impl.model.virement.VirementAccountDAO;
import com.cedar.cp.ejb.impl.model.virement.VirementAccountEVO;
import com.cedar.cp.ejb.impl.model.virement.VirementCategoryEVO;
import com.cedar.cp.ejb.impl.model.virement.VirementLocationDAO;
import com.cedar.cp.ejb.impl.model.virement.VirementLocationEVO;
import com.cedar.cp.ejb.impl.virement.VirementUpdateEngine;
import com.cedar.cp.util.GeneralUtils;
import com.cedar.cp.util.Log;
import com.cedar.cp.util.Timer;
import com.cedar.cp.util.common.JmsConnectionImpl;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import javax.ejb.EJBException;
import javax.ejb.SessionContext;

public class VirementCategoryEditorSessionSEJB extends AbstractSession {

   private static final String DEPENDANTS_FOR_GET_ITEM_DATA = "<23><24>";
   private static final String DEPENDANTS_FOR_INSERT = "";
   private static final String DEPENDANTS_FOR_COPY = "<23><24>";
   private static final String DEPENDANTS_FOR_UPDATE = "<23><24>";
   private static final String DEPENDANTS_FOR_DELETE = "<23><24>";
   private transient Log mLog = new Log(this.getClass());
   private transient SessionContext mSessionContext;
   private transient ModelAccessor mModelAccessor;
   private VirementCategoryEditorSessionSSO mSSO;
   private VirementCategoryCK mThisTableKey;
   private ModelEVO mModelEVO;
   private VirementCategoryEVO mVirementCategoryEVO;


   public VirementCategoryEditorSessionSSO getItemData(int userId, Object paramKey) throws ValidationException, EJBException {
      this.setUserId(userId);
      if(this.mLog.isDebugEnabled()) {
         this.mLog.debug("getItemData", paramKey);
      }

      Timer timer = this.mLog.isInfoEnabled()?new Timer(this.mLog):null;
      this.mThisTableKey = (VirementCategoryCK)paramKey;

      VirementCategoryEditorSessionSSO e;
      try {
         this.mModelEVO = this.getModelAccessor().getDetails(this.mThisTableKey, "<23><24>");
         this.mVirementCategoryEVO = this.mModelEVO.getVirementGroupsItem(this.mThisTableKey.getVirementCategoryPK());
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
      this.mSSO = new VirementCategoryEditorSessionSSO();
      VirementCategoryImpl editorData = this.buildVirementCategoryEditData(this.mThisTableKey);
      this.completeGetItemData(editorData);
      this.mSSO.setEditorData(editorData);
   }

   private void completeGetItemData(VirementCategoryImpl editorData) throws Exception {
      this.loadListModelsFromEvo(editorData);
   }

   private VirementCategoryImpl buildVirementCategoryEditData(Object thisKey) throws Exception {
      VirementCategoryImpl editorData = new VirementCategoryImpl(thisKey);
      editorData.setVisId(this.mVirementCategoryEVO.getVisId());
      editorData.setDescription(this.mVirementCategoryEVO.getDescription());
      editorData.setTranLimit(this.mVirementCategoryEVO.getTranLimit());
      editorData.setTotalLimitIn(this.mVirementCategoryEVO.getTotalLimitIn());
      editorData.setTotalLimitOut(this.mVirementCategoryEVO.getTotalLimitOut());
      editorData.setVersionNum(this.mVirementCategoryEVO.getVersionNum());
      editorData.setModelRef(new ModelRefImpl(this.mModelEVO.getPK(), this.mModelEVO.getVisId()));
      this.completeVirementCategoryEditData(editorData);
      return editorData;
   }

   private void completeVirementCategoryEditData(VirementCategoryImpl editorData) throws Exception {}

   public VirementCategoryEditorSessionSSO getNewItemData(int userId) throws EJBException {
      this.mLog.debug("getNewItemData");
      this.setUserId(userId);
      Timer timer = this.mLog.isInfoEnabled()?new Timer(this.mLog):null;

      VirementCategoryEditorSessionSSO var4;
      try {
         this.mSSO = new VirementCategoryEditorSessionSSO();
         VirementCategoryImpl e = new VirementCategoryImpl((Object)null);
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

   private void completeGetNewItemData(VirementCategoryImpl editorData) throws Exception {}

   public VirementCategoryCK insert(VirementCategoryEditorSessionCSO cso) throws ValidationException, EJBException {
      this.mLog.debug("insert");
      Timer timer = this.mLog.isInfoEnabled()?new Timer(this.mLog):null;
      this.setUserId(cso.getUserId());
      VirementCategoryImpl editorData = cso.getEditorData();

      try {
         this.mModelEVO = this.getModelAccessor().getDetails(editorData.getModelRef(), "");
         this.mVirementCategoryEVO = new VirementCategoryEVO();
         this.mVirementCategoryEVO.setVisId(editorData.getVisId());
         this.mVirementCategoryEVO.setDescription(editorData.getDescription());
         this.mVirementCategoryEVO.setTranLimit(editorData.getTranLimit());
         this.mVirementCategoryEVO.setTotalLimitIn(editorData.getTotalLimitIn());
         this.mVirementCategoryEVO.setTotalLimitOut(editorData.getTotalLimitOut());
         this.updateVirementCategoryRelationships(editorData);
         this.completeInsertSetup(editorData);
         this.validateInsert();
         this.mModelEVO.addVirementGroupsItem(this.mVirementCategoryEVO);
         this.mModelEVO = this.getModelAccessor().setAndGetDetails(this.mModelEVO, "<22>");
         Iterator e = this.mModelEVO.getVirementGroups().iterator();

         while(true) {
            if(e.hasNext()) {
               this.mVirementCategoryEVO = (VirementCategoryEVO)e.next();
               if(!this.mVirementCategoryEVO.insertPending()) {
                  continue;
               }
            }

            this.insertIntoAdditionalTables(editorData, true);
            this.sendEntityEventMessage("VirementCategory", this.mVirementCategoryEVO.getPK(), 1);
            VirementCategoryCK var5 = new VirementCategoryCK(this.mModelEVO.getPK(), this.mVirementCategoryEVO.getPK());
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

   private void updateVirementCategoryRelationships(VirementCategoryImpl editorData) throws ValidationException {}

   private void completeInsertSetup(VirementCategoryImpl editorData) throws Exception {
      this.updateVirementLocationEVOs(editorData);
      this.updateVirementAccountEVOs(editorData);
   }

   private void insertIntoAdditionalTables(VirementCategoryImpl editorData, boolean isInsert) throws Exception {}

   private void validateInsert() throws ValidationException {}

   public VirementCategoryCK copy(VirementCategoryEditorSessionCSO cso) throws ValidationException, EJBException {
      this.mLog.debug("copy");
      Timer timer = this.mLog.isInfoEnabled()?new Timer(this.mLog):null;
      this.setUserId(cso.getUserId());
      VirementCategoryImpl editorData = cso.getEditorData();
      this.mThisTableKey = (VirementCategoryCK)editorData.getPrimaryKey();

      try {
         this.mModelEVO = this.getModelAccessor().getDetails(this.mThisTableKey, "<23><24>");
         VirementCategoryEVO e = this.mModelEVO.getVirementGroupsItem(this.mThisTableKey.getVirementCategoryPK());
         this.mVirementCategoryEVO = e.deepClone();
         this.mVirementCategoryEVO.setVisId(editorData.getVisId());
         this.mVirementCategoryEVO.setDescription(editorData.getDescription());
         this.mVirementCategoryEVO.setTranLimit(editorData.getTranLimit());
         this.mVirementCategoryEVO.setTotalLimitIn(editorData.getTotalLimitIn());
         this.mVirementCategoryEVO.setTotalLimitOut(editorData.getTotalLimitOut());
         this.mVirementCategoryEVO.setVersionNum(0);
         this.updateVirementCategoryRelationships(editorData);
         this.completeCopySetup(editorData);
         this.validateCopy();
         ModelPK parentKey = (ModelPK)editorData.getModelRef().getPrimaryKey();
         if(!parentKey.equals(this.mModelEVO.getPK())) {
            this.mModelEVO = this.getModelAccessor().getDetails(parentKey, "");
         }

         this.mVirementCategoryEVO.prepareForInsert((ModelEVO)null);
         this.mModelEVO.addVirementGroupsItem(this.mVirementCategoryEVO);
         this.mModelEVO = this.getModelAccessor().setAndGetDetails(this.mModelEVO, "<22><23><24>");
         Iterator iter = this.mModelEVO.getVirementGroups().iterator();

         while(true) {
            if(iter.hasNext()) {
               this.mVirementCategoryEVO = (VirementCategoryEVO)iter.next();
               if(!this.mVirementCategoryEVO.insertPending()) {
                  continue;
               }
            }

            this.mThisTableKey = new VirementCategoryCK(this.mModelEVO.getPK(), this.mVirementCategoryEVO.getPK());
            this.insertIntoAdditionalTables(editorData, false);
            this.sendEntityEventMessage("VirementCategory", this.mVirementCategoryEVO.getPK(), 1);
            VirementCategoryCK var7 = this.mThisTableKey;
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

   private void completeCopySetup(VirementCategoryImpl editorData) throws Exception {}

   public void update(VirementCategoryEditorSessionCSO cso) throws ValidationException, EJBException {
      this.mLog.debug("update");
      Timer timer = this.mLog.isInfoEnabled()?new Timer(this.mLog):null;
      this.setUserId(cso.getUserId());
      VirementCategoryImpl editorData = cso.getEditorData();
      this.mThisTableKey = (VirementCategoryCK)editorData.getPrimaryKey();

      try {
         this.mModelEVO = this.getModelAccessor().getDetails(this.mThisTableKey, "<23><24>");
         this.mVirementCategoryEVO = this.mModelEVO.getVirementGroupsItem(this.mThisTableKey.getVirementCategoryPK());
         this.preValidateUpdate(editorData);
         this.mVirementCategoryEVO.setVisId(editorData.getVisId());
         this.mVirementCategoryEVO.setDescription(editorData.getDescription());
         this.mVirementCategoryEVO.setTranLimit(editorData.getTranLimit());
         this.mVirementCategoryEVO.setTotalLimitIn(editorData.getTotalLimitIn());
         this.mVirementCategoryEVO.setTotalLimitOut(editorData.getTotalLimitOut());
         if(editorData.getVersionNum() != this.mVirementCategoryEVO.getVersionNum()) {
            throw new VersionValidationException(this.mThisTableKey + " expected:" + editorData.getVersionNum() + " found:" + this.mVirementCategoryEVO.getVersionNum());
         }

         this.updateVirementCategoryRelationships(editorData);
         this.completeUpdateSetup(editorData);
         this.postValidateUpdate();
         this.getModelAccessor().setDetails(this.mModelEVO);
         this.updateAdditionalTables(editorData);
         this.sendEntityEventMessage("VirementCategory", this.mVirementCategoryEVO.getPK(), 3);
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

   private void preValidateUpdate(VirementCategoryImpl editorData) throws ValidationException {}

   private void postValidateUpdate() throws ValidationException {}

   private void completeUpdateSetup(VirementCategoryImpl editorData) throws Exception {
      this.updateVirementLocationEVOs(editorData);
      this.updateVirementAccountEVOs(editorData);
   }

   public EntityList getOwnershipData(int userId, Object paramKey) throws EJBException {
      this.mLog.debug("getOwnershipData");
      Timer timer = this.mLog.isInfoEnabled()?new Timer(this.mLog):null;
      this.setUserId(userId);
      this.mThisTableKey = (VirementCategoryCK)paramKey;

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

   private void updateAdditionalTables(VirementCategoryImpl editorData) throws Exception {}

   public void delete(int userId, Object paramKey) throws ValidationException, EJBException {
      if(this.mLog.isDebugEnabled()) {
         this.mLog.debug("delete", paramKey);
      }

      Timer timer = this.mLog.isInfoEnabled()?new Timer(this.mLog):null;
      this.setUserId(userId);
      this.mThisTableKey = (VirementCategoryCK)paramKey;

      try {
         this.mModelEVO = this.getModelAccessor().getDetails(this.mThisTableKey, "<23><24>");
         this.mVirementCategoryEVO = this.mModelEVO.getVirementGroupsItem(this.mThisTableKey.getVirementCategoryPK());
         this.validateDelete();
         this.deleteDataFromOtherTables();
         this.mModelEVO.deleteVirementGroupsItem(this.mThisTableKey.getVirementCategoryPK());
         this.getModelAccessor().setDetails(this.mModelEVO);
         this.sendEntityEventMessage("VirementCategory", this.mThisTableKey.getVirementCategoryPK(), 2);
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

   private EntityList getLocationsForCategoryELO() {
      VirementLocationDAO dao = new VirementLocationDAO();
      return dao.getLocationsForCategory(this.mVirementCategoryEVO.getVirementCategoryId());
   }

   private EntityList getAccountsForCategoryELO() {
      VirementAccountDAO dao = new VirementAccountDAO();
      return dao.getAccountsForCategory(this.mVirementCategoryEVO.getVirementCategoryId());
   }

   private void loadListModelsFromEvo(VirementCategoryImpl editorData) {
      ArrayList l = new ArrayList();
      EntityList el = this.getLocationsForCategoryELO();

      int row;
      StructureElementRefImpl structureElementRef;
      String description;
      for(row = 0; row < el.getNumRows(); ++row) {
         VirementLocationRefImpl virementAccountRef = (VirementLocationRefImpl)el.getValueAt(row, "VirementLocation");
         structureElementRef = (StructureElementRefImpl)el.getValueAt(row, "StructureElement");
         String vaPK = (String)el.getValueAt(row, "Description");
         description = (String)el.getValueAt(row, "VisId");
         VirementLocationImpl visId = new VirementLocationImpl(((VirementLocationCK)virementAccountRef.getPrimaryKey()).getVirementLocationPK(), description, vaPK);
         l.add(visId);
      }

      editorData.setResponisbilityAreaList(l);
      l = new ArrayList();
      el = this.getAccountsForCategoryELO();

      for(row = 0; row < el.getNumRows(); ++row) {
         VirementAccountRefImpl var19 = (VirementAccountRefImpl)el.getValueAt(row, "VirementAccount");
         structureElementRef = (StructureElementRefImpl)el.getValueAt(row, "StructureElement");
         VirementAccountPK var20 = var19.getVirementAccountPK();
         description = (String)el.getValueAt(row, "Description");
         String var21 = (String)el.getValueAt(row, "VisId");
         long tranLimit = ((Long)el.getValueAt(row, "TranLimit")).longValue();
         long totalLimitIn = ((Long)el.getValueAt(row, "TotalLimitIn")).longValue();
         long totalLimitOut = ((Long)el.getValueAt(row, "TotalLimitOut")).longValue();
         boolean inFlag = ((Boolean)el.getValueAt(row, "InFlag")).booleanValue();
         boolean outFlag = ((Boolean)el.getValueAt(row, "OutFlag")).booleanValue();
         VirementAccountImpl acct = new VirementAccountImpl(var20, var21, description, tranLimit, totalLimitIn, totalLimitOut, outFlag, inFlag);
         l.add(acct);
      }

      editorData.setAccountAreaList(l);
   }

   private Collection getExistingResponsibilityAreas() {
      return (Collection)(this.mVirementCategoryEVO.getVirementResponsibilityAreas() != null?this.mVirementCategoryEVO.getVirementResponsibilityAreas():Collections.EMPTY_LIST);
   }

   private Collection getExistingAccounts() {
      return (Collection)(this.mVirementCategoryEVO.getVirementAccounts() != null?this.mVirementCategoryEVO.getVirementAccounts():Collections.EMPTY_LIST);
   }

   private void updateVirementLocationEVOs(VirementCategoryImpl editorData) {
      Collection evos = this.getExistingResponsibilityAreas();
      Iterator i = evos.iterator();

      VirementLocationImpl locImpl;
      while(i.hasNext()) {
         VirementLocationEVO virementCategoryId = (VirementLocationEVO)i.next();
         locImpl = editorData.findVirementLocation(virementCategoryId.getStructureElementId());

         try {
            if(locImpl != null) {
               editorData.removeResponsibilityArea(locImpl);
            } else {
               this.mVirementCategoryEVO.deleteVirementResponsibilityAreasItem(virementCategoryId.getPK());
            }
         } catch (ValidationException var7) {
            throw new CPException(var7.getMessage(), var7);
         }
      }

      int virementCategoryId1 = this.mVirementCategoryEVO.getPK() != null?this.mVirementCategoryEVO.getPK().getVirementCategoryId():-1;
      i = editorData.getResponsibilityAreas().iterator();

      while(i.hasNext()) {
         locImpl = (VirementLocationImpl)i.next();
         VirementLocationEVO loc = new VirementLocationEVO(virementCategoryId1, locImpl.getKey().getStructureId(), locImpl.getKey().getStructureElementId(), 0);
         this.mVirementCategoryEVO.addVirementResponsibilityAreasItem(loc);
      }

   }

   private void updateVirementAccountEVOs(VirementCategoryImpl editorData) {
      Collection evos = this.getExistingAccounts();
      Iterator i = evos.iterator();

      VirementAccountImpl acctImpl;
      while(i.hasNext()) {
         VirementAccountEVO virementCategoryId = (VirementAccountEVO)i.next();
         acctImpl = editorData.findVirementAccount(virementCategoryId.getStructureElementId());
         if(acctImpl != null) {
            virementCategoryId.setTranLimit(acctImpl.getTranLimitAsLong());
            virementCategoryId.setTotalLimitIn(acctImpl.getTotalLimitInAsLong());
            virementCategoryId.setTotalLimitOut(acctImpl.getTotalLimitOutAsLong());
            virementCategoryId.setInFlag(acctImpl.isInputAllowed());
            virementCategoryId.setOutFlag(acctImpl.isOutputAllowed());

            try {
               editorData.removeVirementAccount(acctImpl);
            } catch (ValidationException var7) {
               throw new CPException(var7.getMessage(), var7);
            }
         } else {
            this.mVirementCategoryEVO.deleteVirementAccountsItem(virementCategoryId.getPK());
         }
      }

      int virementCategoryId1 = this.mVirementCategoryEVO.getPK() != null?this.mVirementCategoryEVO.getPK().getVirementCategoryId():-1;
      i = editorData.getAccounts().iterator();

      while(i.hasNext()) {
         acctImpl = (VirementAccountImpl)i.next();
         VirementAccountEVO evo = new VirementAccountEVO(virementCategoryId1, acctImpl.getKey().getStructureId(), acctImpl.getKey().getStructureElementId(), acctImpl.getTranLimitAsLong(), acctImpl.getTotalLimitInAsLong(), acctImpl.getTotalLimitOutAsLong(), acctImpl.isInputAllowed(), acctImpl.isOutputAllowed(), 0);
         this.mVirementCategoryEVO.addVirementAccountsItem(evo);
      }

   }

   public void validateVirementPosting(boolean to, ModelRef modelRef, FinanceCubeRef financeCubeRef, String respobsibilityAreaVisId, String accountElementVisId, double temporaryVirement, double permanentVirement) throws ValidationException, EJBException {
      VirementUpdateEngine engine = new VirementUpdateEngine();
      long value = GeneralUtils.convertFinancialValueToDB(temporaryVirement) + GeneralUtils.convertFinancialValueToDB(permanentVirement);
      ModelDAO modelDAO = new ModelDAO();
      StructureElementDAO seDAO = new StructureElementDAO();
      ModelDimensionsELO mdELO = modelDAO.getModelDimensions(((ModelRefImpl)modelRef).getModelPK().getModelId());
      int raDimId = ((DimensionRefImpl)mdELO.getValueAt(0, "Dimension")).getDimensionPK().getDimensionId();
      int acctDimId = ((DimensionRefImpl)mdELO.getValueAt(mdELO.getNumRows() - 2, "Dimension")).getDimensionPK().getDimensionId();
      StructureElementByVisIdELO seELO = seDAO.getStructureElementByVisId(respobsibilityAreaVisId, raDimId);
      if(seELO.getNumRows() == 0) {
         throw new ValidationException("Responsibility Area Element [" + respobsibilityAreaVisId + "] not found");
      } else {
         StructureElementRefImpl raRef = (StructureElementRefImpl)seELO.getValueAt(0, "StructureElement");
         seELO = seDAO.getStructureElementByVisId(accountElementVisId, acctDimId);
         if(seELO.getNumRows() == 0) {
            throw new ValidationException("Account Element [" + accountElementVisId + "] not found");
         } else {
            StructureElementRefImpl acctRef = (StructureElementRefImpl)seELO.getValueAt(0, "StructureElement");

            try {
               engine.checkVirement(((ModelRefImpl)modelRef).getModelPK().getModelId(), ((FinanceCubeRefImpl)financeCubeRef).getFinanceCubePK().getFinanceCubeId(), raRef, acctRef, to, value);
            } catch (SQLException var22) {
               var22.printStackTrace();
               throw new EJBException(var22);
            }
         }
      }
   }
}
