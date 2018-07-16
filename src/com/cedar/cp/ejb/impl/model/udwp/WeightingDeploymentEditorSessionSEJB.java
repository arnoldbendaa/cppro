// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:26:54
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.impl.model.udwp;

import com.cedar.cp.api.base.EntityList;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.dto.base.EntityEventMessage;
import com.cedar.cp.dto.base.PrimaryKey;
import com.cedar.cp.dto.datatype.AllDataTypesELO;
import com.cedar.cp.dto.datatype.DataTypePK;
import com.cedar.cp.dto.dimension.StructureElementKeyImpl;
import com.cedar.cp.dto.model.ModelRefImpl;
import com.cedar.cp.dto.model.udwp.AllWeightingProfilesELO;
import com.cedar.cp.dto.model.udwp.WeightingDeploymentCK;
import com.cedar.cp.dto.model.udwp.WeightingDeploymentEditorSessionCSO;
import com.cedar.cp.dto.model.udwp.WeightingDeploymentEditorSessionSSO;
import com.cedar.cp.dto.model.udwp.WeightingDeploymentImpl;
import com.cedar.cp.dto.model.udwp.WeightingProfileCK;
import com.cedar.cp.dto.model.udwp.WeightingProfileRefImpl;
import com.cedar.cp.ejb.impl.base.AbstractSession;
import com.cedar.cp.ejb.impl.datatype.DataTypeDAO;
import com.cedar.cp.ejb.impl.model.ModelAccessor;
import com.cedar.cp.ejb.impl.model.ModelEVO;
import com.cedar.cp.ejb.impl.model.udwp.WeightingDeploymentDAO;
import com.cedar.cp.ejb.impl.model.udwp.WeightingDeploymentEVO;
import com.cedar.cp.ejb.impl.model.udwp.WeightingDeploymentLineEVO;
import com.cedar.cp.ejb.impl.model.udwp.WeightingProfileEVO;
import com.cedar.cp.util.Log;
import com.cedar.cp.util.Timer;
import com.cedar.cp.util.common.JmsConnectionImpl;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map.Entry;
import javax.ejb.EJBException;
import javax.ejb.SessionContext;

public class WeightingDeploymentEditorSessionSEJB extends AbstractSession {

   private static final String DEPENDANTS_FOR_GET_ITEM_DATA = "<40>";
   private static final String DEPENDANTS_FOR_INSERT = "";
   private static final String DEPENDANTS_FOR_COPY = "<40>";
   private static final String DEPENDANTS_FOR_UPDATE = "<40>";
   private static final String DEPENDANTS_FOR_DELETE = "<40>";
   private transient Log mLog = new Log(this.getClass());
   private transient SessionContext mSessionContext;
   private transient ModelAccessor mModelAccessor;
   private WeightingDeploymentEditorSessionSSO mSSO;
   private WeightingDeploymentCK mThisTableKey;
   private ModelEVO mModelEVO;
   private WeightingProfileEVO mWeightingProfileEVO;
   private WeightingDeploymentEVO mWeightingDeploymentEVO;


   public WeightingDeploymentEditorSessionSSO getItemData(int userId, Object paramKey) throws ValidationException, EJBException {
      this.setUserId(userId);
      if(this.mLog.isDebugEnabled()) {
         this.mLog.debug("getItemData", paramKey);
      }

      Timer timer = this.mLog.isInfoEnabled()?new Timer(this.mLog):null;
      this.mThisTableKey = (WeightingDeploymentCK)paramKey;

      WeightingDeploymentEditorSessionSSO e;
      try {
         this.mModelEVO = this.getModelAccessor().getDetails(this.mThisTableKey, "<40>");
         this.mWeightingProfileEVO = this.mModelEVO.getUserDefinedWeightingProfilesItem(this.mThisTableKey.getWeightingProfilePK());
         this.mWeightingDeploymentEVO = this.mWeightingProfileEVO.getWeightingDeploymentsItem(this.mThisTableKey.getWeightingDeploymentPK());
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
      this.mSSO = new WeightingDeploymentEditorSessionSSO();
      WeightingDeploymentImpl editorData = this.buildWeightingDeploymentEditData(this.mThisTableKey);
      this.completeGetItemData(editorData);
      this.mSSO.setEditorData(editorData);
   }

   private void completeGetItemData(WeightingDeploymentImpl editorData) throws Exception {
      this.updateImpl(this.mWeightingDeploymentEVO, editorData);
   }

   private WeightingDeploymentImpl buildWeightingDeploymentEditData(Object thisKey) throws Exception {
      WeightingDeploymentImpl editorData = new WeightingDeploymentImpl(thisKey);
      editorData.setProfileId(this.mWeightingDeploymentEVO.getProfileId());
      editorData.setAnyAccount(this.mWeightingDeploymentEVO.getAnyAccount());
      editorData.setAnyBusiness(this.mWeightingDeploymentEVO.getAnyBusiness());
      editorData.setAnyDataType(this.mWeightingDeploymentEVO.getAnyDataType());
      editorData.setWeighting(this.mWeightingDeploymentEVO.getWeighting());
      editorData.setWeightingProfileRef(new WeightingProfileRefImpl(this.mWeightingProfileEVO.getPK(), this.mWeightingProfileEVO.getVisId()));
      editorData.setModelRef(new ModelRefImpl(this.mModelEVO.getPK(), this.mModelEVO.getVisId()));
      this.completeWeightingDeploymentEditData(editorData);
      return editorData;
   }

   private void completeWeightingDeploymentEditData(WeightingDeploymentImpl editorData) throws Exception {
      editorData.setProfileDescription(this.mWeightingProfileEVO.getDescription());
      this.updateImpl(this.mWeightingDeploymentEVO, editorData);
   }

   public WeightingDeploymentEditorSessionSSO getNewItemData(int userId) throws EJBException {
      this.mLog.debug("getNewItemData");
      this.setUserId(userId);
      Timer timer = this.mLog.isInfoEnabled()?new Timer(this.mLog):null;

      WeightingDeploymentEditorSessionSSO var4;
      try {
         this.mSSO = new WeightingDeploymentEditorSessionSSO();
         WeightingDeploymentImpl e = new WeightingDeploymentImpl((Object)null);
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

   private void completeGetNewItemData(WeightingDeploymentImpl editorData) throws Exception {}

   public WeightingDeploymentCK insert(WeightingDeploymentEditorSessionCSO cso) throws ValidationException, EJBException {
      this.mLog.debug("insert");
      Timer timer = this.mLog.isInfoEnabled()?new Timer(this.mLog):null;
      this.setUserId(cso.getUserId());
      WeightingDeploymentImpl editorData = cso.getEditorData();

      try {
         this.mModelEVO = this.getModelAccessor().getDetails(editorData.getWeightingProfileRef(), "");
         this.mWeightingProfileEVO = this.mModelEVO.getUserDefinedWeightingProfilesItem(((WeightingProfileCK)editorData.getWeightingProfileRef().getPrimaryKey()).getWeightingProfilePK());
         this.mWeightingDeploymentEVO = new WeightingDeploymentEVO();
         this.mWeightingDeploymentEVO.setProfileId(editorData.getProfileId());
         this.mWeightingDeploymentEVO.setAnyAccount(editorData.isAnyAccount());
         this.mWeightingDeploymentEVO.setAnyBusiness(editorData.isAnyBusiness());
         this.mWeightingDeploymentEVO.setAnyDataType(editorData.isAnyDataType());
         this.mWeightingDeploymentEVO.setWeighting(editorData.getWeighting());
         this.updateWeightingDeploymentRelationships(editorData);
         this.completeInsertSetup(editorData);
         this.validateInsert();
         this.mWeightingProfileEVO.addWeightingDeploymentsItem(this.mWeightingDeploymentEVO);
         this.mModelEVO = this.getModelAccessor().setAndGetDetails(this.mModelEVO, "<37><39>");
         this.mWeightingProfileEVO = this.mModelEVO.getUserDefinedWeightingProfilesItem(((WeightingProfileCK)editorData.getWeightingProfileRef().getPrimaryKey()).getWeightingProfilePK());
         Iterator e = this.mWeightingProfileEVO.getWeightingDeployments().iterator();

         while(true) {
            if(e.hasNext()) {
               this.mWeightingDeploymentEVO = (WeightingDeploymentEVO)e.next();
               if(!this.mWeightingDeploymentEVO.insertPending()) {
                  continue;
               }
            }

            this.insertIntoAdditionalTables(editorData, true);
            this.sendEntityEventMessage("WeightingDeployment", this.mWeightingDeploymentEVO.getPK(), 1);
            WeightingDeploymentCK var5 = new WeightingDeploymentCK(this.mModelEVO.getPK(), this.mWeightingProfileEVO.getPK(), this.mWeightingDeploymentEVO.getPK());
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

   private void updateWeightingDeploymentRelationships(WeightingDeploymentImpl editorData) throws ValidationException {}

   private void completeInsertSetup(WeightingDeploymentImpl editorData) throws Exception {
      WeightingProfileRefImpl profileRefImpl = (WeightingProfileRefImpl)editorData.getWeightingProfileRef();
      if(profileRefImpl == null) {
         throw new ValidationException("A weighting profile parent must be selected first.");
      } else {
         this.mWeightingDeploymentEVO.setProfileId(profileRefImpl.getWeightingProfilePK().getProfileId());
         this.updateEVO(this.mWeightingDeploymentEVO, editorData);
      }
   }

   private void insertIntoAdditionalTables(WeightingDeploymentImpl editorData, boolean isInsert) throws Exception {}

   private void validateInsert() throws ValidationException {}

   public WeightingDeploymentCK copy(WeightingDeploymentEditorSessionCSO cso) throws ValidationException, EJBException {
      this.mLog.debug("copy");
      Timer timer = this.mLog.isInfoEnabled()?new Timer(this.mLog):null;
      this.setUserId(cso.getUserId());
      WeightingDeploymentImpl editorData = cso.getEditorData();
      this.mThisTableKey = (WeightingDeploymentCK)editorData.getPrimaryKey();

      try {
         this.mModelEVO = this.getModelAccessor().getDetails(this.mThisTableKey, "<40>");
         this.mWeightingProfileEVO = this.mModelEVO.getUserDefinedWeightingProfilesItem(this.mThisTableKey.getWeightingProfilePK());
         WeightingDeploymentEVO e = this.mWeightingProfileEVO.getWeightingDeploymentsItem(this.mThisTableKey.getWeightingDeploymentPK());
         this.mWeightingDeploymentEVO = e.deepClone();
         this.mWeightingDeploymentEVO.setProfileId(editorData.getProfileId());
         this.mWeightingDeploymentEVO.setAnyAccount(editorData.isAnyAccount());
         this.mWeightingDeploymentEVO.setAnyBusiness(editorData.isAnyBusiness());
         this.mWeightingDeploymentEVO.setAnyDataType(editorData.isAnyDataType());
         this.mWeightingDeploymentEVO.setWeighting(editorData.getWeighting());
         this.updateWeightingDeploymentRelationships(editorData);
         this.completeCopySetup(editorData);
         this.validateCopy();
         WeightingProfileCK parentKey = (WeightingProfileCK)editorData.getWeightingProfileRef().getPrimaryKey();
         if(!parentKey.getWeightingProfilePK().equals(this.mWeightingProfileEVO.getPK())) {
            this.mModelEVO = this.getModelAccessor().getDetails(parentKey, "<37>");
            this.mWeightingProfileEVO = this.mModelEVO.getUserDefinedWeightingProfilesItem(parentKey.getWeightingProfilePK());
         }

         this.mWeightingDeploymentEVO.prepareForInsert((WeightingProfileEVO)null);
         this.mWeightingProfileEVO.addWeightingDeploymentsItem(this.mWeightingDeploymentEVO);
         this.mModelEVO = this.getModelAccessor().setAndGetDetails(this.mModelEVO, "<37><39><40>");
         this.mWeightingProfileEVO = this.mModelEVO.getUserDefinedWeightingProfilesItem(parentKey.getWeightingProfilePK());
         Iterator iter = this.mWeightingProfileEVO.getWeightingDeployments().iterator();

         while(true) {
            if(iter.hasNext()) {
               this.mWeightingDeploymentEVO = (WeightingDeploymentEVO)iter.next();
               if(!this.mWeightingDeploymentEVO.insertPending()) {
                  continue;
               }
            }

            this.mThisTableKey = new WeightingDeploymentCK(this.mModelEVO.getPK(), this.mWeightingProfileEVO.getPK(), this.mWeightingDeploymentEVO.getPK());
            this.insertIntoAdditionalTables(editorData, false);
            this.sendEntityEventMessage("WeightingDeployment", this.mWeightingDeploymentEVO.getPK(), 1);
            WeightingDeploymentCK var7 = this.mThisTableKey;
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

   private void completeCopySetup(WeightingDeploymentImpl editorData) throws Exception {
      this.updateEVO(this.mWeightingDeploymentEVO, editorData);
   }

   public void update(WeightingDeploymentEditorSessionCSO cso) throws ValidationException, EJBException {
      this.mLog.debug("update");
      Timer timer = this.mLog.isInfoEnabled()?new Timer(this.mLog):null;
      this.setUserId(cso.getUserId());
      WeightingDeploymentImpl editorData = cso.getEditorData();
      this.mThisTableKey = (WeightingDeploymentCK)editorData.getPrimaryKey();

      try {
         this.mModelEVO = this.getModelAccessor().getDetails(this.mThisTableKey, "<40>");
         this.mWeightingProfileEVO = this.mModelEVO.getUserDefinedWeightingProfilesItem(this.mThisTableKey.getWeightingProfilePK());
         this.mWeightingDeploymentEVO = this.mWeightingProfileEVO.getWeightingDeploymentsItem(this.mThisTableKey.getWeightingDeploymentPK());
         this.preValidateUpdate(editorData);
         this.mWeightingDeploymentEVO.setProfileId(editorData.getProfileId());
         this.mWeightingDeploymentEVO.setAnyAccount(editorData.isAnyAccount());
         this.mWeightingDeploymentEVO.setAnyBusiness(editorData.isAnyBusiness());
         this.mWeightingDeploymentEVO.setAnyDataType(editorData.isAnyDataType());
         this.mWeightingDeploymentEVO.setWeighting(editorData.getWeighting());
         this.updateWeightingDeploymentRelationships(editorData);
         this.completeUpdateSetup(editorData);
         this.postValidateUpdate();
         this.getModelAccessor().setDetails(this.mModelEVO);
         this.updateAdditionalTables(editorData);
         this.sendEntityEventMessage("WeightingDeployment", this.mWeightingDeploymentEVO.getPK(), 3);
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

   private void preValidateUpdate(WeightingDeploymentImpl editorData) throws ValidationException {}

   private void postValidateUpdate() throws ValidationException {}

   private void completeUpdateSetup(WeightingDeploymentImpl editorData) throws Exception {
      this.updateEVO(this.mWeightingDeploymentEVO, editorData);
   }

   public EntityList getOwnershipData(int userId, Object paramKey) throws EJBException {
      this.mLog.debug("getOwnershipData");
      Timer timer = this.mLog.isInfoEnabled()?new Timer(this.mLog):null;
      this.setUserId(userId);
      this.mThisTableKey = (WeightingDeploymentCK)paramKey;

      AllWeightingProfilesELO e;
      try {
         //e = this.getModelAccessor().getAllWeightingProfilesForLoggedUser(userId);
    	  e = this.getModelAccessor().getAllWeightingProfiles();
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

   private void updateAdditionalTables(WeightingDeploymentImpl editorData) throws Exception {}

   public void delete(int userId, Object paramKey) throws ValidationException, EJBException {
      if(this.mLog.isDebugEnabled()) {
         this.mLog.debug("delete", paramKey);
      }

      Timer timer = this.mLog.isInfoEnabled()?new Timer(this.mLog):null;
      this.setUserId(userId);
      this.mThisTableKey = (WeightingDeploymentCK)paramKey;

      try {
         this.mModelEVO = this.getModelAccessor().getDetails(this.mThisTableKey, "<40>");
         this.mWeightingProfileEVO = this.mModelEVO.getUserDefinedWeightingProfilesItem(this.mThisTableKey.getWeightingProfilePK());
         this.mWeightingDeploymentEVO = this.mWeightingProfileEVO.getWeightingDeploymentsItem(this.mThisTableKey.getWeightingDeploymentPK());
         this.validateDelete();
         this.deleteDataFromOtherTables();
         this.mWeightingProfileEVO.deleteWeightingDeploymentsItem(this.mThisTableKey.getWeightingDeploymentPK());
         this.getModelAccessor().setDetails(this.mModelEVO);
         this.sendEntityEventMessage("WeightingDeployment", this.mThisTableKey.getWeightingDeploymentPK(), 2);
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

   public EntityList queryDeployments() throws EJBException {
      return (new WeightingDeploymentDAO()).queryAllDeployments();
   }

   public void updateImpl(WeightingDeploymentEVO evo, WeightingDeploymentImpl impl) {
      if(evo.getDeploymentLines() != null) {
         DataTypeDAO dtDAO = new DataTypeDAO();
         AllDataTypesELO allDataTypes = dtDAO.getAllDataTypes();
         Iterator iter = evo.getDeploymentLines().iterator();

         while(iter.hasNext()) {
            WeightingDeploymentLineEVO lineEVO = (WeightingDeploymentLineEVO)iter.next();
            StructureElementKeyImpl key;
            if(lineEVO.getAccountStructureId() != null) {
               key = new StructureElementKeyImpl(lineEVO.getAccountStructureId().intValue(), lineEVO.getAccountStructureElementId().intValue());
               impl.addAccountElement(key, lineEVO.getAccountSelectionFlag());
            }

            if(lineEVO.getBusinessStructureId() != null) {
               key = new StructureElementKeyImpl(lineEVO.getBusinessStructureId().intValue(), lineEVO.getBusinessStructureElementId().intValue());
               impl.addBusinessElement(key, lineEVO.getBusinessSelectionFlag());
            }

            if(lineEVO.getDataTypeId() != null) {
               impl.addDataType(new DataTypePK((short)lineEVO.getDataTypeId().intValue()));
            }
         }
      }

   }

   public void updateEVO(WeightingDeploymentEVO evo, WeightingDeploymentImpl impl) {
      ArrayList existingItems = evo.getDeploymentLines() != null?new ArrayList(evo.getDeploymentLines()):new ArrayList();
      evo.dumpLines("Before line nulling");
      int deploymentId = evo.getDeploymentId();
      Iterator iter = existingItems.iterator();

      WeightingDeploymentLineEVO lineEVO;
      StructureElementKeyImpl acctNull;
      label113:
      while(iter.hasNext()) {
         lineEVO = (WeightingDeploymentLineEVO)iter.next();

         while(true) {
            if(lineEVO.getAccountStructureId() != null) {
               acctNull = new StructureElementKeyImpl(lineEVO.getAccountStructureId().intValue(), lineEVO.getAccountStructureElementId().intValue());
               if(impl.getAccountElements().get(acctNull) == null) {
                  evo.removeAccountRef(lineEVO);
                  continue;
               }
            }

            while(lineEVO.getBusinessStructureId() != null) {
               acctNull = new StructureElementKeyImpl(lineEVO.getBusinessStructureId().intValue(), lineEVO.getBusinessStructureElementId().intValue());
               if(impl.getBusinessElements().get(acctNull) != null) {
                  break;
               }

               evo.removeBusinessRef(lineEVO);
            }

            while(true) {
               if(lineEVO.getDataTypeId() == null) {
                  continue label113;
               }

               DataTypePK acctNull1 = new DataTypePK((short)lineEVO.getDataTypeId().intValue());
               if(impl.getDataTypes().contains(acctNull1)) {
                  continue label113;
               }

               evo.removeDataTypeRef(lineEVO);
            }
         }
      }

      evo.dumpLines("After line nulling");

      Boolean busNull;
      WeightingDeploymentLineEVO dtNull;
      Entry lineEVO1;
      for(iter = impl.getAccountElements().entrySet().iterator(); iter.hasNext(); dtNull.setAccountSelectionFlag(busNull)) {
         lineEVO1 = (Entry)iter.next();
         acctNull = (StructureElementKeyImpl)lineEVO1.getKey();
         busNull = (Boolean)lineEVO1.getValue();
         dtNull = evo.findLineForAccountElement(acctNull.getStructureId(), acctNull.getStructureElementId());
         if(dtNull == null) {
            dtNull = evo.findFreeLineForAccount();
            if(dtNull == null) {
               dtNull = new WeightingDeploymentLineEVO(deploymentId, evo.queryNextDeploymentLineIdx(), new Integer(acctNull.getStructureId()), new Integer(acctNull.getStructureElementId()), busNull, (Integer)null, (Integer)null, (Boolean)null, (Integer)null);
               evo.addDeploymentLinesItem(dtNull);
            } else {
               dtNull.setAccountStructureId(new Integer(acctNull.getStructureId()));
               dtNull.setAccountStructureElementId(new Integer(acctNull.getStructureElementId()));
               dtNull.setAccountSelectionFlag(Boolean.valueOf(true));
            }
         }
      }

      evo.dumpLines("After line account updates");

      for(iter = impl.getBusinessElements().entrySet().iterator(); iter.hasNext(); dtNull.setBusinessSelectionFlag(busNull)) {
         lineEVO1 = (Entry)iter.next();
         acctNull = (StructureElementKeyImpl)lineEVO1.getKey();
         busNull = (Boolean)lineEVO1.getValue();
         dtNull = evo.findLineForBusinessElement(acctNull.getStructureId(), acctNull.getStructureElementId());
         if(dtNull == null) {
            dtNull = evo.findFreeLineForBusiness();
            if(dtNull == null) {
               dtNull = new WeightingDeploymentLineEVO(deploymentId, evo.queryNextDeploymentLineIdx(), (Integer)null, (Integer)null, (Boolean)null, Integer.valueOf(acctNull.getStructureId()), Integer.valueOf(acctNull.getStructureElementId()), busNull, (Integer)null);
               evo.addDeploymentLinesItem(dtNull);
            } else {
               dtNull.setBusinessStructureId(Integer.valueOf(acctNull.getStructureId()));
               dtNull.setBusinessStructureElementId(Integer.valueOf(acctNull.getStructureElementId()));
               dtNull.setBusinessSelectionFlag(Boolean.valueOf(true));
            }
         }
      }

      evo.dumpLines("After business updates");
      iter = impl.getDataTypes().iterator();

      while(iter.hasNext()) {
         short lineEVO2 = ((DataTypePK)iter.next()).getDataTypeId();
         WeightingDeploymentLineEVO acctNull2 = evo.findLineForDataType(lineEVO2);
         if(acctNull2 == null) {
            acctNull2 = evo.findFreeLineForDataType();
            if(acctNull2 == null) {
               acctNull2 = new WeightingDeploymentLineEVO(deploymentId, evo.queryNextDeploymentLineIdx(), (Integer)null, (Integer)null, (Boolean)null, (Integer)null, (Integer)null, (Boolean)null, new Integer(lineEVO2));
               evo.addDeploymentLinesItem(acctNull2);
            } else {
               acctNull2.setDataTypeId(new Integer(lineEVO2));
            }
         }
      }

      evo.dumpLines("After data type updates");
      iter = existingItems.iterator();

      while(iter.hasNext()) {
         lineEVO = (WeightingDeploymentLineEVO)iter.next();
         boolean acctNull3 = lineEVO.getAccountStructureId() == null;
         boolean busNull1 = lineEVO.getBusinessStructureId() == null;
         boolean dtNull1 = lineEVO.getDataTypeId() == null;
         if(acctNull3 && busNull1 && dtNull1) {
            lineEVO.setDeletePending();
         }
      }

      evo.dumpLines("EXITING");
   }
}
