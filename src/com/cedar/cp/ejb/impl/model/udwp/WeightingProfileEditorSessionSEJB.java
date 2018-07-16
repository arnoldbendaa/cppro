// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:26:55
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.impl.model.udwp;

import com.cedar.cp.api.base.EntityList;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.base.VersionValidationException;
import com.cedar.cp.api.model.ModelRef;
import com.cedar.cp.api.model.udwp.Profile;
import com.cedar.cp.dto.base.EntityEventMessage;
import com.cedar.cp.dto.base.PrimaryKey;
import com.cedar.cp.dto.datatype.DataTypePK;
import com.cedar.cp.dto.dimension.DimensionRefImpl;
import com.cedar.cp.dto.dimension.HierarchyElementImpl;
import com.cedar.cp.dto.dimension.HierarcyDetailsIncRootNodeFromDimIdELO;
import com.cedar.cp.dto.dimension.LeavesForParentELO;
import com.cedar.cp.dto.dimension.calendar.CalendarEditorUtils;
import com.cedar.cp.dto.model.AllModelsELO;
import com.cedar.cp.dto.model.ModelDimensionsELO;
import com.cedar.cp.dto.model.ModelPK;
import com.cedar.cp.dto.model.ModelRefImpl;
import com.cedar.cp.dto.model.udwp.WeightingProfileCK;
import com.cedar.cp.dto.model.udwp.WeightingProfileEditorSessionCSO;
import com.cedar.cp.dto.model.udwp.WeightingProfileEditorSessionSSO;
import com.cedar.cp.dto.model.udwp.WeightingProfileImpl;
import com.cedar.cp.dto.model.udwp.WeightingProfileLinePK;
import com.cedar.cp.ejb.impl.base.AbstractSession;
import com.cedar.cp.ejb.impl.datatype.DataTypeDAO;
import com.cedar.cp.ejb.impl.datatype.DataTypeEVO;
import com.cedar.cp.ejb.impl.dimension.HierarchyDAO;
import com.cedar.cp.ejb.impl.dimension.StructureElementDAO;
import com.cedar.cp.ejb.impl.model.ModelAccessor;
import com.cedar.cp.ejb.impl.model.ModelDAO;
import com.cedar.cp.ejb.impl.model.ModelEVO;
import com.cedar.cp.ejb.impl.model.udwp.WeightingProfileDAO;
import com.cedar.cp.ejb.impl.model.udwp.WeightingProfileEVO;
import com.cedar.cp.ejb.impl.model.udwp.WeightingProfileLineEVO;
import com.cedar.cp.util.Log;
import com.cedar.cp.util.Timer;
import com.cedar.cp.util.common.JmsConnectionImpl;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.ejb.EJBException;
import javax.ejb.SessionContext;

public class WeightingProfileEditorSessionSEJB extends AbstractSession {

   private static final String DEPENDANTS_FOR_GET_ITEM_DATA = "<38><39><40>";
   private static final String DEPENDANTS_FOR_INSERT = "";
   private static final String DEPENDANTS_FOR_COPY = "<38><39><40>";
   private static final String DEPENDANTS_FOR_UPDATE = "<38><39><40>";
   private static final String DEPENDANTS_FOR_DELETE = "<38><39><40>";
   private WeightingProfileDAO mWeightingProfileDAO;
   private transient Log mLog = new Log(this.getClass());
   private transient SessionContext mSessionContext;
   private transient ModelAccessor mModelAccessor;
   private WeightingProfileEditorSessionSSO mSSO;
   private WeightingProfileCK mThisTableKey;
   private ModelEVO mModelEVO;
   private WeightingProfileEVO mWeightingProfileEVO;


   public WeightingProfileEditorSessionSSO getItemData(int userId, Object paramKey) throws ValidationException, EJBException {
      this.setUserId(userId);
      if(this.mLog.isDebugEnabled()) {
         this.mLog.debug("getItemData", paramKey);
      }

      Timer timer = this.mLog.isInfoEnabled()?new Timer(this.mLog):null;
      this.mThisTableKey = (WeightingProfileCK)paramKey;

      WeightingProfileEditorSessionSSO e;
      try {
         this.mModelEVO = this.getModelAccessor().getDetails(this.mThisTableKey, "<38><39><40>");
         this.mWeightingProfileEVO = this.mModelEVO.getUserDefinedWeightingProfilesItem(this.mThisTableKey.getWeightingProfilePK());
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
      this.mSSO = new WeightingProfileEditorSessionSSO();
      WeightingProfileImpl editorData = this.buildWeightingProfileEditData(this.mThisTableKey);
      this.completeGetItemData(editorData);
      this.mSSO.setEditorData(editorData);
   }

   private void completeGetItemData(WeightingProfileImpl editorData) throws Exception {
      this.loadProfileLines(this.mWeightingProfileEVO, editorData);
   }

   private WeightingProfileImpl buildWeightingProfileEditData(Object thisKey) throws Exception {
      WeightingProfileImpl editorData = new WeightingProfileImpl(thisKey);
      editorData.setModelId(this.mWeightingProfileEVO.getModelId());
      editorData.setVisId(this.mWeightingProfileEVO.getVisId());
      editorData.setDescription(this.mWeightingProfileEVO.getDescription());
      editorData.setStartLevel(this.mWeightingProfileEVO.getStartLevel());
      editorData.setLeafLevel(this.mWeightingProfileEVO.getLeafLevel());
      editorData.setProfileType(this.mWeightingProfileEVO.getProfileType());
      editorData.setDynamicOffset(this.mWeightingProfileEVO.getDynamicOffset());
      editorData.setDynamicDataTypeId(this.mWeightingProfileEVO.getDynamicDataTypeId());
      editorData.setDynamicEsIfWfbtoz(this.mWeightingProfileEVO.getDynamicEsIfWfbtoz());
      editorData.setVersionNum(this.mWeightingProfileEVO.getVersionNum());
      editorData.setModelRef(new ModelRefImpl(this.mModelEVO.getPK(), this.mModelEVO.getVisId()));
      this.completeWeightingProfileEditData(editorData);
      return editorData;
   }

   private void completeWeightingProfileEditData(WeightingProfileImpl editorData) throws Exception {}

   public WeightingProfileEditorSessionSSO getNewItemData(int userId) throws EJBException {
      this.mLog.debug("getNewItemData");
      this.setUserId(userId);
      Timer timer = this.mLog.isInfoEnabled()?new Timer(this.mLog):null;

      WeightingProfileEditorSessionSSO var4;
      try {
         this.mSSO = new WeightingProfileEditorSessionSSO();
         WeightingProfileImpl e = new WeightingProfileImpl((Object)null);
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

   private void completeGetNewItemData(WeightingProfileImpl editorData) throws Exception {}

   public WeightingProfileCK insert(WeightingProfileEditorSessionCSO cso) throws ValidationException, EJBException {
      this.mLog.debug("insert");
      Timer timer = this.mLog.isInfoEnabled()?new Timer(this.mLog):null;
      this.setUserId(cso.getUserId());
      WeightingProfileImpl editorData = cso.getEditorData();

      try {
         this.mModelEVO = this.getModelAccessor().getDetails(editorData.getModelRef(), "");
         this.mWeightingProfileEVO = new WeightingProfileEVO();
         this.mWeightingProfileEVO.setModelId(editorData.getModelId());
         this.mWeightingProfileEVO.setVisId(editorData.getVisId());
         this.mWeightingProfileEVO.setDescription(editorData.getDescription());
         this.mWeightingProfileEVO.setStartLevel(editorData.getStartLevel());
         this.mWeightingProfileEVO.setLeafLevel(editorData.getLeafLevel());
         this.mWeightingProfileEVO.setProfileType(editorData.getProfileType());
         this.mWeightingProfileEVO.setDynamicOffset(editorData.getDynamicOffset());
         this.mWeightingProfileEVO.setDynamicDataTypeId(editorData.getDynamicDataTypeId());
         this.mWeightingProfileEVO.setDynamicEsIfWfbtoz(editorData.getDynamicEsIfWfbtoz());
         this.updateWeightingProfileRelationships(editorData);
         this.completeInsertSetup(editorData);
         this.validateInsert();
         this.mModelEVO.addUserDefinedWeightingProfilesItem(this.mWeightingProfileEVO);
         this.mModelEVO = this.getModelAccessor().setAndGetDetails(this.mModelEVO, "<37>");
         Iterator e = this.mModelEVO.getUserDefinedWeightingProfiles().iterator();

         while(true) {
            if(e.hasNext()) {
               this.mWeightingProfileEVO = (WeightingProfileEVO)e.next();
               if(!this.mWeightingProfileEVO.insertPending()) {
                  continue;
               }
            }

            this.insertIntoAdditionalTables(editorData, true);
            this.sendEntityEventMessage("WeightingProfile", this.mWeightingProfileEVO.getPK(), 1);
            WeightingProfileCK var5 = new WeightingProfileCK(this.mModelEVO.getPK(), this.mWeightingProfileEVO.getPK());
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

   private void updateWeightingProfileRelationships(WeightingProfileImpl editorData) throws ValidationException {}

   private void completeInsertSetup(WeightingProfileImpl editorData) throws Exception {
      this.updateEVO(this.mWeightingProfileEVO, editorData);
   }

   private void insertIntoAdditionalTables(WeightingProfileImpl editorData, boolean isInsert) throws Exception {}

   private void validateInsert() throws ValidationException {}

   public WeightingProfileCK copy(WeightingProfileEditorSessionCSO cso) throws ValidationException, EJBException {
      this.mLog.debug("copy");
      Timer timer = this.mLog.isInfoEnabled()?new Timer(this.mLog):null;
      this.setUserId(cso.getUserId());
      WeightingProfileImpl editorData = cso.getEditorData();
      this.mThisTableKey = (WeightingProfileCK)editorData.getPrimaryKey();

      try {
         this.mModelEVO = this.getModelAccessor().getDetails(this.mThisTableKey, "<38><39><40>");
         WeightingProfileEVO e = this.mModelEVO.getUserDefinedWeightingProfilesItem(this.mThisTableKey.getWeightingProfilePK());
         this.mWeightingProfileEVO = e.deepClone();
         this.mWeightingProfileEVO.setModelId(editorData.getModelId());
         this.mWeightingProfileEVO.setVisId(editorData.getVisId());
         this.mWeightingProfileEVO.setDescription(editorData.getDescription());
         this.mWeightingProfileEVO.setStartLevel(editorData.getStartLevel());
         this.mWeightingProfileEVO.setLeafLevel(editorData.getLeafLevel());
         this.mWeightingProfileEVO.setProfileType(editorData.getProfileType());
         this.mWeightingProfileEVO.setDynamicOffset(editorData.getDynamicOffset());
         this.mWeightingProfileEVO.setDynamicDataTypeId(editorData.getDynamicDataTypeId());
         this.mWeightingProfileEVO.setDynamicEsIfWfbtoz(editorData.getDynamicEsIfWfbtoz());
         this.mWeightingProfileEVO.setVersionNum(0);
         this.updateWeightingProfileRelationships(editorData);
         this.completeCopySetup(editorData);
         this.validateCopy();
         ModelPK parentKey = (ModelPK)editorData.getModelRef().getPrimaryKey();
         if(!parentKey.equals(this.mModelEVO.getPK())) {
            this.mModelEVO = this.getModelAccessor().getDetails(parentKey, "");
         }

         this.mWeightingProfileEVO.prepareForInsert((ModelEVO)null);
         this.mModelEVO.addUserDefinedWeightingProfilesItem(this.mWeightingProfileEVO);
         this.mModelEVO = this.getModelAccessor().setAndGetDetails(this.mModelEVO, "<37><38><39><40>");
         Iterator iter = this.mModelEVO.getUserDefinedWeightingProfiles().iterator();

         while(true) {
            if(iter.hasNext()) {
               this.mWeightingProfileEVO = (WeightingProfileEVO)iter.next();
               if(!this.mWeightingProfileEVO.insertPending()) {
                  continue;
               }
            }

            this.mThisTableKey = new WeightingProfileCK(this.mModelEVO.getPK(), this.mWeightingProfileEVO.getPK());
            this.insertIntoAdditionalTables(editorData, false);
            this.sendEntityEventMessage("WeightingProfile", this.mWeightingProfileEVO.getPK(), 1);
            WeightingProfileCK var7 = this.mThisTableKey;
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

   private void completeCopySetup(WeightingProfileImpl editorData) throws Exception {
      this.updateEVO(this.mWeightingProfileEVO, editorData);
   }

   public void update(WeightingProfileEditorSessionCSO cso) throws ValidationException, EJBException {
      this.mLog.debug("update");
      Timer timer = this.mLog.isInfoEnabled()?new Timer(this.mLog):null;
      this.setUserId(cso.getUserId());
      WeightingProfileImpl editorData = cso.getEditorData();
      this.mThisTableKey = (WeightingProfileCK)editorData.getPrimaryKey();

      try {
         this.mModelEVO = this.getModelAccessor().getDetails(this.mThisTableKey, "<38><39><40>");
         this.mWeightingProfileEVO = this.mModelEVO.getUserDefinedWeightingProfilesItem(this.mThisTableKey.getWeightingProfilePK());
         this.preValidateUpdate(editorData);
         this.mWeightingProfileEVO.setModelId(editorData.getModelId());
         this.mWeightingProfileEVO.setVisId(editorData.getVisId());
         this.mWeightingProfileEVO.setDescription(editorData.getDescription());
         this.mWeightingProfileEVO.setStartLevel(editorData.getStartLevel());
         this.mWeightingProfileEVO.setLeafLevel(editorData.getLeafLevel());
         this.mWeightingProfileEVO.setProfileType(editorData.getProfileType());
         this.mWeightingProfileEVO.setDynamicOffset(editorData.getDynamicOffset());
         this.mWeightingProfileEVO.setDynamicDataTypeId(editorData.getDynamicDataTypeId());
         this.mWeightingProfileEVO.setDynamicEsIfWfbtoz(editorData.getDynamicEsIfWfbtoz());
         if(editorData.getVersionNum() != this.mWeightingProfileEVO.getVersionNum()) {
            throw new VersionValidationException(this.mThisTableKey + " expected:" + editorData.getVersionNum() + " found:" + this.mWeightingProfileEVO.getVersionNum());
         }

         this.updateWeightingProfileRelationships(editorData);
         this.completeUpdateSetup(editorData);
         this.postValidateUpdate();
         this.getModelAccessor().setDetails(this.mModelEVO);
         this.updateAdditionalTables(editorData);
         this.sendEntityEventMessage("WeightingProfile", this.mWeightingProfileEVO.getPK(), 3);
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

   private void preValidateUpdate(WeightingProfileImpl editorData) throws ValidationException {}

   private void postValidateUpdate() throws ValidationException {}

   private void completeUpdateSetup(WeightingProfileImpl editorData) throws Exception {
      this.updateEVO(this.mWeightingProfileEVO, editorData);
   }

   public EntityList getOwnershipData(int userId, Object paramKey) throws EJBException {
      this.mLog.debug("getOwnershipData");
      Timer timer = this.mLog.isInfoEnabled()?new Timer(this.mLog):null;
      this.setUserId(userId);
      this.mThisTableKey = (WeightingProfileCK)paramKey;

      AllModelsELO e;
      try {
         //e = this.getModelAccessor().getAllModelsForLoggedUser(userId);
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

   private void updateAdditionalTables(WeightingProfileImpl editorData) throws Exception {}

   public void delete(int userId, Object paramKey) throws ValidationException, EJBException {
      if(this.mLog.isDebugEnabled()) {
         this.mLog.debug("delete", paramKey);
      }

      Timer timer = this.mLog.isInfoEnabled()?new Timer(this.mLog):null;
      this.setUserId(userId);
      this.mThisTableKey = (WeightingProfileCK)paramKey;

      try {
         this.mModelEVO = this.getModelAccessor().getDetails(this.mThisTableKey, "<38><39><40>");
         this.mWeightingProfileEVO = this.mModelEVO.getUserDefinedWeightingProfilesItem(this.mThisTableKey.getWeightingProfilePK());
         this.validateDelete();
         this.deleteDataFromOtherTables();
         this.mModelEVO.deleteUserDefinedWeightingProfilesItem(this.mThisTableKey.getWeightingProfilePK());
         this.getModelAccessor().setDetails(this.mModelEVO);
         this.sendEntityEventMessage("WeightingProfile", this.mThisTableKey.getWeightingProfilePK(), 2);
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

   public Object[][] queryWeightingInfo(int userId, ModelRef modelRef) throws EJBException {
      ModelDAO modelDAO = new ModelDAO();
      ModelDimensionsELO modelDims = modelDAO.getModelDimensions(((ModelRefImpl)modelRef).getModelPK().getModelId());
      int calRow = modelDims.getNumRows() - 1;
      DimensionRefImpl calDimRef = (DimensionRefImpl)modelDims.getValueAt(calRow, "Dimension");
      HierarchyDAO hierDAO = new HierarchyDAO();
      HierarcyDetailsIncRootNodeFromDimIdELO hierInfo = hierDAO.getHierarcyDetailsIncRootNodeFromDimId(calDimRef.getDimensionPK().getDimensionId());
      int calStructureId = ((Integer)hierInfo.getValueAt(0, "HierarchyId")).intValue();
      int calRootElementId = ((Integer)hierInfo.getValueAt(0, "StructureElementId")).intValue();
      StructureElementDAO seDAO = new StructureElementDAO();
      LeavesForParentELO leavesELO = seDAO.getLeavesForParent(calStructureId, calStructureId, calRootElementId, calStructureId, calRootElementId);
      Object[][] result = new Object[leavesELO.getNumRows()][];

      for(int row = 0; row < leavesELO.getNumRows(); ++row) {
         result[row] = new Object[]{leavesELO.getValueAt(row, "VisId"), leavesELO.getValueAt(row, "Description"), new Integer(0)};
      }

      return result;
   }

   private void updateEVO(WeightingProfileEVO evo, WeightingProfileImpl impl) {
      evo.setDynamicDataTypeId(impl.getDynamicDataType() == null?null:new Integer(((DataTypePK)impl.getDynamicDataType().getPrimaryKey()).getDataTypeId()));
      ArrayList existingEVOs = evo.getWeightingLines() == null?new ArrayList():new ArrayList(evo.getWeightingLines());
      int profileId = evo.getProfileId();

      for(int eIter = 0; eIter < impl.getNumWeightingRows(); ++eIter) {
         WeightingProfileLinePK lineEVO = new WeightingProfileLinePK(profileId, eIter);
         WeightingProfileLineEVO lineEVO1 = evo.getWeightingLines() != null?evo.getWeightingLinesItem(lineEVO):null;
         if(lineEVO1 != null) {
            lineEVO1.setWeighting(impl.getWeighting(eIter));
            existingEVOs.remove(lineEVO1);
         } else {
            evo.addWeightingLinesItem(new WeightingProfileLineEVO(profileId, eIter, impl.getWeighting(eIter)));
         }
      }

      Iterator var8 = existingEVOs.iterator();

      while(var8.hasNext()) {
         WeightingProfileLineEVO var9 = (WeightingProfileLineEVO)var8.next();
         evo.deleteWeightingLinesItem(var9.getPK());
      }

   }

   private void loadProfileLines(WeightingProfileEVO evo, WeightingProfileImpl impl) throws ValidationException {
      CalendarEditorUtils ceu = new CalendarEditorUtils();
      EntityList calSpecForModel = this.getCPConnection().getListHelper().getCalendarSpecForModel(evo.getModelId());
      if(calSpecForModel.getNumRows() != 1) {
         throw new ValidationException("Unable to locate calendar spec from model:" + evo.getModelId());
      } else {
         impl.setYearStartMonth(((Integer)calSpecForModel.getValueAt(0, "YearStartMonth")).intValue());
         List leaves = ceu.queryWeightingElements(impl.getStartLevel(), impl.getLeafLevel(), impl.getYearStartMonth());
         Object[][] weightingData = new Object[leaves.size()][3];

         for(int dataTypeId = 0; dataTypeId < leaves.size(); ++dataTypeId) {
            HierarchyElementImpl dataTypeEVO = (HierarchyElementImpl)leaves.get(dataTypeId);
            weightingData[dataTypeId][0] = dataTypeEVO.getVisId();
            weightingData[dataTypeId][1] = dataTypeEVO.getDescription();
            weightingData[dataTypeId][2] = Integer.valueOf(0);
         }

         impl.setWeightingInfo(weightingData);
         if(evo.getWeightingLines() != null) {
            Iterator var10 = evo.getWeightingLines().iterator();

            while(var10.hasNext()) {
               WeightingProfileLineEVO var12 = (WeightingProfileLineEVO)var10.next();
               impl.setWeighting(var12.getLineIdx(), var12.getWeighting());
            }
         }

         if(impl.getDynamicDataTypeId() != null) {
            short var9 = impl.getDynamicDataTypeId().shortValue();
            DataTypeEVO var11 = (new DataTypeDAO()).getDetails(new DataTypePK(var9), "");
            impl.setDynamicDataType(var11.getEntityRef());
         }

      }
   }

   public EntityList queryProfiles(int modelId, int fromLevel, int toLevel, int acctStructureElementId, int busStructureElementId, String dataTypeId) throws EJBException {
      return this.getWeightingProfileDAO().queryProfiles(modelId, fromLevel, toLevel, acctStructureElementId, busStructureElementId, dataTypeId);
   }

   public EntityList queryProfiles(int modelId, int dataTypeId, int acctStructureId, int acctStructureElementId, int busStructureId, int busStructureElementId, int calStructureId, int calStructureElementId) throws EJBException {
      return this.getWeightingProfileDAO().queryProfiles(modelId, dataTypeId, acctStructureId, acctStructureElementId, busStructureId, busStructureElementId, calStructureId, calStructureElementId);
   }

   public int[] getWeightingProfile(int profileId) throws EJBException {
      return this.getWeightingProfileDAO().getWeightingProfile(profileId);
   }

   public Profile getWeightingProfileDetail(int profileId) throws EJBException {
      return this.getWeightingProfileDAO().getWeightingProfileDetail(profileId);
   }

   public Profile getWeightingProfileDetail(int profileId, int calendar_structure_element_id) throws EJBException {
      return this.getWeightingProfileDAO().getWeightingProfileDetail(profileId, calendar_structure_element_id);
   }

   public EntityList queryDynamicWeightingFactors(int financeCubeId, int[] structureElementIds, int targetElementId, String dataType, int targetFromLevel, int targetToLevel, int calDimId) throws EJBException {
      return this.getWeightingProfileDAO().queryDynamicWeightingFactors(financeCubeId, structureElementIds, targetElementId, dataType, targetFromLevel, targetToLevel, calDimId);
   }

   public Profile getProfileDetail(int financeCubeId, int profileId, int[] address, String dataType) throws ValidationException, EJBException {
      return this.getWeightingProfileDAO().getProfileDetail(financeCubeId, profileId, address, dataType);
   }

   private WeightingProfileDAO getWeightingProfileDAO() {
      if(this.mWeightingProfileDAO == null) {
         this.mWeightingProfileDAO = new WeightingProfileDAO();
      }

      return this.mWeightingProfileDAO;
   }
}
