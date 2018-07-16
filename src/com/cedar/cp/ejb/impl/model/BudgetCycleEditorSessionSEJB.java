// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:08:44
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.impl.model;

import com.cedar.cp.api.base.EntityList;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.base.VersionValidationException;
import com.cedar.cp.api.dimension.StructureElementRef;
import com.cedar.cp.dto.base.EntityEventMessage;
import com.cedar.cp.dto.base.PrimaryKey;
import com.cedar.cp.dto.dimension.AllDisabledLeafandNotPlannableELO;
import com.cedar.cp.dto.dimension.HierarcyDetailsFromDimIdELO;
import com.cedar.cp.dto.dimension.StructureElementValuesELO;
import com.cedar.cp.dto.model.AllModelsELO;
import com.cedar.cp.dto.model.BudgetCycleCK;
import com.cedar.cp.dto.model.BudgetCycleEditorSessionCSO;
import com.cedar.cp.dto.model.BudgetCycleEditorSessionSSO;
import com.cedar.cp.dto.model.BudgetCycleImpl;
import com.cedar.cp.dto.model.BudgetCycleLinkPK;
import com.cedar.cp.dto.model.BudgetCyclePK;
import com.cedar.cp.dto.model.BudgetHierarchyRootNodeForModelELO;
import com.cedar.cp.dto.model.MaxDepthForBudgetHierarchyELO;
import com.cedar.cp.dto.model.ModelPK;
import com.cedar.cp.dto.model.ModelRefImpl;
import com.cedar.cp.dto.xmlform.rebuild.AllBudgetCyclesInRebuildsELO;
import com.cedar.cp.ejb.impl.base.AbstractSession;
import com.cedar.cp.ejb.impl.dimension.HierarchyDAO;
import com.cedar.cp.ejb.impl.dimension.StructureElementDAO;
import com.cedar.cp.ejb.impl.model.BudgetCycleEVO;
import com.cedar.cp.ejb.impl.model.BudgetStateEVO;
import com.cedar.cp.ejb.impl.model.BudgetStateHistoryEVO;
import com.cedar.cp.ejb.impl.model.LevelDateEVO;
import com.cedar.cp.ejb.impl.model.ModelAccessor;
import com.cedar.cp.ejb.impl.model.ModelDAO;
import com.cedar.cp.ejb.impl.model.ModelEVO;
import com.cedar.cp.ejb.impl.xmlform.rebuild.FormRebuildDAO;
import com.cedar.cp.util.Log;
import com.cedar.cp.util.Timer;
import com.cedar.cp.util.common.JmsConnectionImpl;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import javax.ejb.EJBException;
import javax.ejb.SessionContext;

public class BudgetCycleEditorSessionSEJB extends AbstractSession {

   private static final String DEPENDANTS_FOR_GET_ITEM_DATA = "<14><15>";
   private static final String DEPENDANTS_FOR_INSERT = "";
   private static final String DEPENDANTS_FOR_COPY = "<12><13><14><15>";
   private static final String DEPENDANTS_FOR_UPDATE = "<12><13><14><15>";
   private static final String DEPENDANTS_FOR_DELETE = "<12><13><14><15>";
   private transient Log mLog = new Log(this.getClass());
   private transient SessionContext mSessionContext;
   private transient ModelAccessor mModelAccessor;
   private BudgetCycleEditorSessionSSO mSSO;
   private BudgetCycleCK mThisTableKey;
   private ModelEVO mModelEVO;
   private BudgetCycleEVO mBudgetCycleEVO;
   
   public BudgetCycleEditorSessionSEJB(){
	   
   }


   public BudgetCycleEditorSessionSSO getItemData(int userId, Object paramKey) throws ValidationException, EJBException {
      this.setUserId(userId);
      if(this.mLog.isDebugEnabled()) {
         this.mLog.debug("getItemData", paramKey);
      }

      Timer timer = this.mLog.isInfoEnabled()?new Timer(this.mLog):null;
      this.mThisTableKey = (BudgetCycleCK)paramKey;

      BudgetCycleEditorSessionSSO e;
      try {
         this.mModelEVO = this.getModelAccessor().getDetails(this.mThisTableKey, "<14><15>");
         this.mBudgetCycleEVO = this.mModelEVO.getBudgetCyclesItem(this.mThisTableKey.getBudgetCyclePK());
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
   
   public void clearCache(int userId, Object key) throws ValidationException, EJBException {
	   this.setUserId(userId);
	   if (mModelEVO != null) {
		   if (this.mModelEVO.getBudgetCyclesItem(((BudgetCycleCK) key).getBudgetCyclePK()) != null) {
			   try {
				   	mModelEVO.getBudgetCyclesMap().remove(((BudgetCycleCK) key).getBudgetCyclePK());
				   	this.getModelAccessor().setDetails(mModelEVO);
				} catch (Exception e) {
					e.printStackTrace();
				}
		   }
	   }
   }

   private void makeItemData() throws Exception {
      this.mSSO = new BudgetCycleEditorSessionSSO();
      BudgetCycleImpl editorData = this.buildBudgetCycleEditData(this.mThisTableKey);
      this.completeGetItemData(editorData);
      this.mSSO.setEditorData(editorData);
   }

   private void completeGetItemData(BudgetCycleImpl editorData) throws Exception {}

   private BudgetCycleImpl buildBudgetCycleEditData(Object thisKey) throws Exception {
      BudgetCycleImpl editorData = new BudgetCycleImpl(thisKey);
      editorData.setModelId(this.mBudgetCycleEVO.getModelId());
      editorData.setVisId(this.mBudgetCycleEVO.getVisId());
      editorData.setDescription(this.mBudgetCycleEVO.getDescription());
      editorData.setType(this.mBudgetCycleEVO.getType());
      editorData.setXmlFormId(this.mBudgetCycleEVO.getXmlFormId());
      editorData.setXmlFormDataType(this.mBudgetCycleEVO.getXmlFormDataType());
      editorData.setPeriodId(this.mBudgetCycleEVO.getPeriodId());
      editorData.setPlannedEndDate(this.mBudgetCycleEVO.getPlannedEndDate());
      editorData.setStartDate(this.mBudgetCycleEVO.getStartDate());
      editorData.setEndDate(this.mBudgetCycleEVO.getEndDate());
      editorData.setStatus(this.mBudgetCycleEVO.getStatus());
      editorData.setVersionNum(this.mBudgetCycleEVO.getVersionNum());
      editorData.setModelRef(new ModelRefImpl(this.mModelEVO.getPK(), this.mModelEVO.getVisId()));
      editorData.setPeriodIdTo( this.mBudgetCycleEVO.getPeriodIdTo() );
      editorData.setPeriodFromVisId(this.mBudgetCycleEVO.getPeriodFromVisId());
      editorData.setPeriodToVisId(this.mBudgetCycleEVO.getPeriodToVisId());
      editorData.setCategory(this.mBudgetCycleEVO.getCategory());
      this.completeBudgetCycleEditData(editorData);
      return editorData;
   }

   private void completeBudgetCycleEditData(BudgetCycleImpl editorData) throws Exception {
	   List xmlForms = new ArrayList();
	   Iterator budgetCycleLinksIterator = this.mBudgetCycleEVO.getBudgetCycleLinks().iterator();
       while(budgetCycleLinksIterator.hasNext()) {
    	  BudgetCycleLinkEVO link = (BudgetCycleLinkEVO)budgetCycleLinksIterator.next();
    	  xmlForms.add(new Object[]{link.getXmlFormId(), link.getXmlFormDataType()});
       }
       editorData.setXmlForms(xmlForms);
	   
	  StructureElementDAO seDAO = new StructureElementDAO();
      StructureElementValuesELO seELO = seDAO.getStructureElementIdFromModel(this.mModelEVO.getModelId());
      StructureElementRef ref = null;
      if(seELO.hasNext()) {
         seELO.next();
         ref = seELO.getStructureElementEntityRef();
         editorData.setRootElementEntityRef(ref);
      }

      HierarchyDAO hDao = new HierarchyDAO();
      HierarcyDetailsFromDimIdELO hELO = hDao.getHierarcyDetailsFromDimId(this.mModelEVO.getCalendarId());
      if(hELO.hasNext()) {
         hELO.next();
         seELO = seDAO.getStructureElementValues(hELO.getHierarchyId(), editorData.getPeriodId());
         if(seELO.hasNext()) {
            seELO.next();
            ref = seELO.getStructureElementEntityRef();
            editorData.setPeriodRef(ref);
         }
         
         seELO = seDAO.getStructureElementValues( hELO.getHierarchyId(), editorData.getPeriodIdTo() );
         if(seELO.hasNext()) {
            seELO.next();
            ref = seELO.getStructureElementEntityRef();
            editorData.setPeriodToRef(ref);
         }
      }

      this.mLog.debug("root element of hierarchy " + ref.getPrimaryKey() + " is " + ref.getNarrative());
      int size = this.mBudgetCycleEVO.getBudgetCycleLevlDates().size();
      Date dummy = new Date();
      ArrayList levels = new ArrayList(size);

      for(int i$ = 0; i$ < size; ++i$) {
         levels.add(dummy);
      }

      Iterator var14 = this.mBudgetCycleEVO.getBudgetCycleLevlDates().iterator();

      while(var14.hasNext()) {
         LevelDateEVO myLevel = (LevelDateEVO)var14.next();
         LevelDateEVO level = (LevelDateEVO)myLevel;
         int depth = level.getDepth();
         levels.add(depth, level.getPlannedEndDate());
         levels.remove(depth + 1);
      }

      editorData.setLevelDates(levels);
   }

   public BudgetCycleEditorSessionSSO getNewItemData(int userId) throws EJBException {
      this.mLog.debug("getNewItemData");
      this.setUserId(userId);
      Timer timer = this.mLog.isInfoEnabled()?new Timer(this.mLog):null;

      BudgetCycleEditorSessionSSO var4;
      try {
         this.mSSO = new BudgetCycleEditorSessionSSO();
         BudgetCycleImpl e = new BudgetCycleImpl((Object)null);
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

   private void completeGetNewItemData(BudgetCycleImpl editorData) throws Exception {}

   public BudgetCycleCK insert(BudgetCycleEditorSessionCSO cso) throws ValidationException, EJBException {
      this.mLog.debug("insert");
      Timer timer = this.mLog.isInfoEnabled()?new Timer(this.mLog):null;
      this.setUserId(cso.getUserId());
      BudgetCycleImpl editorData = cso.getEditorData();

      try {
         this.mModelEVO = this.getModelAccessor().getDetails(editorData.getModelRef(), "");
         this.mBudgetCycleEVO = new BudgetCycleEVO();
         this.mBudgetCycleEVO.setModelId(editorData.getModelId());
         this.mBudgetCycleEVO.setVisId(editorData.getVisId());
         this.mBudgetCycleEVO.setDescription(editorData.getDescription());
         this.mBudgetCycleEVO.setType(editorData.getType());
         this.mBudgetCycleEVO.setXmlFormId(editorData.getXmlFormId());
         this.mBudgetCycleEVO.setXmlFormDataType(editorData.getXmlFormDataType());
         this.mBudgetCycleEVO.setPeriodId(editorData.getPeriodId());
         this.mBudgetCycleEVO.setPeriodIdTo( editorData.getPeriodIdTo() );
         this.mBudgetCycleEVO.setPeriodFromVisId(editorData.getPeriodFromVisId());
         this.mBudgetCycleEVO.setPeriodToVisId(editorData.getPeriodToVisId());
         this.mBudgetCycleEVO.setPlannedEndDate(editorData.getPlannedEndDate());
         this.mBudgetCycleEVO.setStartDate(editorData.getStartDate());
         this.mBudgetCycleEVO.setEndDate(editorData.getEndDate());
         this.mBudgetCycleEVO.setStatus(editorData.getStatus());
         this.mBudgetCycleEVO.setCategory(editorData.getCategory());
         this.updateBudgetCycleRelationships(editorData);
         this.completeInsertSetup(editorData);
         this.validateInsert();
         this.mModelEVO.addBudgetCyclesItem(this.mBudgetCycleEVO);
         this.mModelEVO = this.getModelAccessor().setAndGetDetails(this.mModelEVO, "<11>");
         Iterator e = this.mModelEVO.getBudgetCycles().iterator();

         while(true) {
            if(e.hasNext()) {
               this.mBudgetCycleEVO = (BudgetCycleEVO)e.next();
               if(!this.mBudgetCycleEVO.insertPending()) {
                  continue;
               }
            }

            this.insertIntoAdditionalTables(editorData, true);
            this.sendEntityEventMessage("BudgetCycle", this.mBudgetCycleEVO.getPK(), 1);
            BudgetCycleCK var5 = new BudgetCycleCK(this.mModelEVO.getPK(), this.mBudgetCycleEVO.getPK());
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

   private void updateBudgetCycleRelationships(BudgetCycleImpl editorData) throws ValidationException {}

   private void completeInsertSetup(BudgetCycleImpl editorData) throws Exception {
      if(editorData.getStartDate() == null && editorData.getStatus() == 1) {
         this.mBudgetCycleEVO.setStartDate(new Timestamp((new Date()).getTime()));
      }

      if(editorData.getLevelDates() == null || editorData.isDateChanged()) {
         editorData.setLevelDates(this.getLevelDates(editorData));
      }

      Iterator iter = editorData.getLevelDates().iterator();
      int count = 0;

      while(iter.hasNext()) {
         Date myDate = (Date)iter.next();
         LevelDateEVO evo = new LevelDateEVO();
         evo.setDepth(count++);
         evo.setPlannedEndDate(new Timestamp(myDate.getTime()));
         this.mBudgetCycleEVO.addBudgetCycleLevlDatesItem(evo);
      }
      
      this.budgetCycleLinkUpdate(editorData);
   }

   private void insertIntoAdditionalTables(BudgetCycleImpl editorData, boolean isInsert) throws Exception {
      ModelDAO modelDao = new ModelDAO();
      BudgetHierarchyRootNodeForModelELO elo = modelDao.getBudgetHierarchyRootNodeForModel(this.mBudgetCycleEVO.getModelId());
      elo.next();
      StructureElementDAO strucDao = new StructureElementDAO();
      AllDisabledLeafandNotPlannableELO strucList = strucDao.getAllDisabledLeafandNotPlannable(elo.getStructureId());
      int size = strucList.getNumRows();
      int budgetCycleId = this.mBudgetCycleEVO.getBudgetCycleId();
      boolean disabled = true;
      Timestamp timestamp = new Timestamp(System.currentTimeMillis());

      for(int i = 0; i < size; ++i) {
         int structureElementId = ((Integer)strucList.getValueAt(i, "StructureElementId")).intValue();
         disabled = ((Boolean)strucList.getValueAt(i, "Disabled")).booleanValue();
         BudgetStateEVO evo = new BudgetStateEVO();
         evo.setBudgetCycleId(budgetCycleId);
         evo.setStructureElementId(structureElementId);
         if(disabled) {
            evo.setState(5);
         } else {
            evo.setState(6);
         }

         BudgetStateHistoryEVO historyEVO = new BudgetStateHistoryEVO();
         historyEVO.setBudgetCycleId(budgetCycleId);
         historyEVO.setStructureElementId(structureElementId);
         historyEVO.setChangedTime(timestamp);
         historyEVO.setPreviousState(0);
         if(disabled) {
            historyEVO.setNewState(5);
         } else {
            historyEVO.setNewState(6);
         }

         evo.addBudgetCycleHistoryItem(historyEVO);
         this.mBudgetCycleEVO.addBudgetCycleStatesItem(evo);
      }

      this.getModelAccessor().setDetails(this.mModelEVO);
   }

   private void validateInsert() throws ValidationException {
      if(this.mBudgetCycleEVO.getVisId() != null && this.mBudgetCycleEVO.getVisId().length() >= 1) {
         if(this.mBudgetCycleEVO.getPeriodId() == 0) {
            throw new ValidationException("Period element must be added");
         } else if(this.mBudgetCycleEVO.getPlannedEndDate() != null && !this.mBudgetCycleEVO.getPlannedEndDate().before(new Date())) {
            if(this.mBudgetCycleEVO.getXmlFormId() == 0) {
               throw new ValidationException("XMLForm must be set");
            } else if(this.mBudgetCycleEVO.getXmlFormDataType() != null && this.mBudgetCycleEVO.getXmlFormDataType().length() != 0) {
               if(this.mBudgetCycleEVO.getStatus() < 0 || this.mBudgetCycleEVO.getStatus() > 1) {
                  throw new ValidationException("Status must be set to either 0:initiated or 1:started");
               }
            } else {
               throw new ValidationException("A data type must be set");
            }
         } else {
            throw new ValidationException("Planned end date must be in the future");
         }
      } else {
         throw new ValidationException("Budget Cycle identifier must be set");
      }
   }

   public BudgetCycleCK copy(BudgetCycleEditorSessionCSO cso) throws ValidationException, EJBException {
      this.mLog.debug("copy");
      Timer timer = this.mLog.isInfoEnabled()?new Timer(this.mLog):null;
      this.setUserId(cso.getUserId());
      BudgetCycleImpl editorData = cso.getEditorData();
      this.mThisTableKey = (BudgetCycleCK)editorData.getPrimaryKey();

      try {
         this.mModelEVO = this.getModelAccessor().getDetails(this.mThisTableKey, "<12><13><14><15>");
         BudgetCycleEVO e = this.mModelEVO.getBudgetCyclesItem(this.mThisTableKey.getBudgetCyclePK());
         this.mBudgetCycleEVO = e.deepClone();
         this.mBudgetCycleEVO.setModelId(editorData.getModelId());
         this.mBudgetCycleEVO.setVisId(editorData.getVisId());
         this.mBudgetCycleEVO.setDescription(editorData.getDescription());
         this.mBudgetCycleEVO.setType(editorData.getType());
         this.mBudgetCycleEVO.setXmlFormId(editorData.getXmlFormId());
         this.mBudgetCycleEVO.setXmlFormDataType(editorData.getXmlFormDataType());
         this.mBudgetCycleEVO.setPeriodId(editorData.getPeriodId());
         this.mBudgetCycleEVO.setPlannedEndDate(editorData.getPlannedEndDate());
         this.mBudgetCycleEVO.setStartDate(editorData.getStartDate());
         this.mBudgetCycleEVO.setEndDate(editorData.getEndDate());
         this.mBudgetCycleEVO.setStatus(editorData.getStatus());
         this.mBudgetCycleEVO.setCategory(editorData.getCategory());
         this.mBudgetCycleEVO.setVersionNum(0);
         this.updateBudgetCycleRelationships(editorData);
         this.completeCopySetup(editorData);
         this.validateCopy();
         ModelPK parentKey = (ModelPK)editorData.getModelRef().getPrimaryKey();
         if(!parentKey.equals(this.mModelEVO.getPK())) {
            this.mModelEVO = this.getModelAccessor().getDetails(parentKey, "");
         }

         this.mBudgetCycleEVO.prepareForInsert((ModelEVO)null);
         this.mModelEVO.addBudgetCyclesItem(this.mBudgetCycleEVO);
         this.mModelEVO = this.getModelAccessor().setAndGetDetails(this.mModelEVO, "<11><14><15>");
         Iterator iter = this.mModelEVO.getBudgetCycles().iterator();

         while(true) {
            if(iter.hasNext()) {
               this.mBudgetCycleEVO = (BudgetCycleEVO)iter.next();
               if(!this.mBudgetCycleEVO.insertPending()) {
                  continue;
               }
            }

            this.mThisTableKey = new BudgetCycleCK(this.mModelEVO.getPK(), this.mBudgetCycleEVO.getPK());
            this.insertIntoAdditionalTables(editorData, false);
            this.sendEntityEventMessage("BudgetCycle", this.mBudgetCycleEVO.getPK(), 1);
            BudgetCycleCK var7 = this.mThisTableKey;
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

   private void validateCopy() throws ValidationException {
      throw new ValidationException("copy not allowed");
   }

   private void completeCopySetup(BudgetCycleImpl editorData) throws Exception {
	   this.budgetCycleLinkUpdate(editorData);
   }

   public void update(BudgetCycleEditorSessionCSO cso) throws ValidationException, EJBException {
      this.mLog.debug("update");
      Timer timer = this.mLog.isInfoEnabled()?new Timer(this.mLog):null;
      this.setUserId(cso.getUserId());
      BudgetCycleImpl editorData = cso.getEditorData();
      this.mThisTableKey = (BudgetCycleCK)editorData.getPrimaryKey();

      try {
         this.mModelEVO = this.getModelAccessor().getDetails(this.mThisTableKey, "<12><13><14><15>");
         this.mBudgetCycleEVO = this.mModelEVO.getBudgetCyclesItem(this.mThisTableKey.getBudgetCyclePK());
         this.preValidateUpdate(editorData);
         this.mBudgetCycleEVO.setModelId(editorData.getModelId());
         this.mBudgetCycleEVO.setVisId(editorData.getVisId());
         this.mBudgetCycleEVO.setDescription(editorData.getDescription());
         this.mBudgetCycleEVO.setType(editorData.getType());
         this.mBudgetCycleEVO.setXmlFormId(editorData.getXmlFormId());
         this.mBudgetCycleEVO.setXmlFormDataType(editorData.getXmlFormDataType());
         this.mBudgetCycleEVO.setPeriodId(editorData.getPeriodId());
         this.mBudgetCycleEVO.setPeriodIdTo( editorData.getPeriodIdTo() );
         this.mBudgetCycleEVO.setPeriodFromVisId(editorData.getPeriodFromVisId());
         this.mBudgetCycleEVO.setPeriodToVisId(editorData.getPeriodToVisId());
         this.mBudgetCycleEVO.setPlannedEndDate(editorData.getPlannedEndDate());
         this.mBudgetCycleEVO.setStartDate(editorData.getStartDate());
         this.mBudgetCycleEVO.setEndDate(editorData.getEndDate());
         this.mBudgetCycleEVO.setStatus(editorData.getStatus());
         this.mBudgetCycleEVO.setCategory(editorData.getCategory());
         
         if(editorData.getVersionNum() != this.mBudgetCycleEVO.getVersionNum()) {
            throw new VersionValidationException(this.mThisTableKey + " expected:" + editorData.getVersionNum() + " found:" + this.mBudgetCycleEVO.getVersionNum());
         }

         this.updateBudgetCycleRelationships(editorData);
         this.completeUpdateSetup(editorData);
         this.postValidateUpdate();
         this.getModelAccessor().setDetails(this.mModelEVO,true);
         this.updateAdditionalTables(editorData);
         this.sendEntityEventMessage("BudgetCycle", this.mBudgetCycleEVO.getPK(), 3);
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

   private void preValidateUpdate(BudgetCycleImpl editorData) throws ValidationException {
      if(editorData.getStatus() >= 0 && editorData.getStatus() <= 2) {
         if(editorData.getEndDate() == null && editorData.getStatus() == 2) {
            FormRebuildDAO frDAO = new FormRebuildDAO();
            AllBudgetCyclesInRebuildsELO bcELO = frDAO.getAllBudgetCyclesInRebuilds();

            while(bcELO.hasNext()) {
               bcELO.next();
               BudgetCycleCK ck = (BudgetCycleCK)editorData.getPrimaryKey();
               BudgetCyclePK pk = ck.getBudgetCyclePK();
               if(bcELO.getBudgetCycleId() == pk.getBudgetCycleId()) {
                  throw new ValidationException("This cycle is in use in FormRebuild " + bcELO.getFormRebuildEntityRef().getNarrative() + " and cannot be complete until it has been removed");
               }
            }
         }

      } else {
         throw new ValidationException("Status must be set to either 0:initiated, 1:started, 2:complete");
      }
   }

   private void postValidateUpdate() throws ValidationException {}

   private void completeUpdateSetup(BudgetCycleImpl editorData) throws Exception {
      if(editorData.getStartDate() == null && editorData.getStatus() == 1) {
         this.mBudgetCycleEVO.setStartDate(new Timestamp((new Date()).getTime()));
      }

      if(editorData.getEndDate() == null && editorData.getStatus() == 2) {
         this.mBudgetCycleEVO.setEndDate(new Timestamp((new Date()).getTime()));
      }

      if(editorData.isDateChanged()) {
         editorData.setLevelDates(this.getLevelDates(editorData));
      }

      List dates = editorData.getLevelDates();
      Iterator iter = this.mBudgetCycleEVO.getBudgetCycleLevlDates().iterator();
      int depth = 0;

      int count;
      for(count = 0; iter.hasNext(); ++count) {
         LevelDateEVO i = (LevelDateEVO)iter.next();
         depth = i.getDepth();
         Date evo = (Date)dates.get(depth);
         if(evo.getTime() != i.getPlannedEndDate().getTime()) {
            i.setPlannedEndDate(new Timestamp(evo.getTime()));
            this.mBudgetCycleEVO.changeBudgetCycleLevlDatesItem(i);
         }
      }

      if(count < dates.size()) {
         for(int var8 = depth + 1; var8 < dates.size(); ++var8) {
            LevelDateEVO var9 = new LevelDateEVO();
            var9.setBudgetCycleId(this.mBudgetCycleEVO.getBudgetCycleId());
            var9.setDepth(var8);
            var9.setPlannedEndDate(new Timestamp(((Date)dates.get(var8)).getTime()));
            this.mBudgetCycleEVO.addBudgetCycleLevlDatesItem(var9);
         }
      }
      
      this.budgetCycleLinkUpdate(editorData);
   }

   public EntityList getOwnershipData(int userId, Object paramKey) throws EJBException {
      this.mLog.debug("getOwnershipData");
      Timer timer = this.mLog.isInfoEnabled()?new Timer(this.mLog):null;
      this.setUserId(userId);
      this.mThisTableKey = (BudgetCycleCK)paramKey;

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

   private void updateAdditionalTables(BudgetCycleImpl editorData) throws Exception {}

   public void delete(int userId, Object paramKey) throws ValidationException, EJBException {
      if(this.mLog.isDebugEnabled()) {
         this.mLog.debug("delete", paramKey);
      }

      Timer timer = this.mLog.isInfoEnabled()?new Timer(this.mLog):null;
      this.setUserId(userId);
      this.mThisTableKey = (BudgetCycleCK)paramKey;

      try {
         this.mModelEVO = this.getModelAccessor().getDetails(this.mThisTableKey, "<12><13><14><15>");
         this.mBudgetCycleEVO = this.mModelEVO.getBudgetCyclesItem(this.mThisTableKey.getBudgetCyclePK());
         this.validateDelete();
         this.deleteDataFromOtherTables();
         this.mModelEVO.deleteBudgetCyclesItem(this.mThisTableKey.getBudgetCyclePK());
         this.getModelAccessor().setDetails(this.mModelEVO);
         this.sendEntityEventMessage("BudgetCycle", this.mThisTableKey.getBudgetCyclePK(), 2);
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
      if(this.mBudgetCycleEVO.getStatus() != 2) {
         throw new ValidationException("Budget Cycle is in use must be complete to delete");
      }
   }

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

   public List getLevelDates(BudgetCycleImpl editorData) {
      ModelDAO dao = new ModelDAO();
      MaxDepthForBudgetHierarchyELO list = dao.getMaxDepthForBudgetHierarchy(((ModelPK)editorData.getModelRef().getPrimaryKey()).getModelId());
      Integer maxDepth = Integer.valueOf(0);
      if(list.getNumRows() > 0) {
         maxDepth = (Integer)list.getValueAt(0, "col2");
      }

      maxDepth = Integer.valueOf(maxDepth.intValue() + 1);
      long today = System.currentTimeMillis();
      long planned = editorData.getPlannedEndDate().getTime();
      long dif = planned - today;
      long millsPerDay = 86400000L;
      Long numberOfDays = Long.valueOf(dif / millsPerDay);
      Long intervalDays = Long.valueOf(numberOfDays.longValue() / (long)maxDepth.intValue());
      Long intervalDaysMillis = Long.valueOf(intervalDays.longValue() * millsPerDay);
      ArrayList splitDays = new ArrayList(maxDepth.intValue());

      for(int i = 0; i < maxDepth.intValue(); ++i) {
         if(i == 0) {
            splitDays.add(new Date(planned));
         } else {
            long subTime = intervalDaysMillis.longValue() * (long)(i + 1);
            splitDays.add(new Date(planned - subTime));
         }
      }

      return splitDays;
   }
   
	private void budgetCycleLinkUpdate(BudgetCycleImpl editorData) throws Exception {
		List budgetCycleLinkEVOs = new ArrayList();
		if (this.mBudgetCycleEVO.getBudgetCycleLinks() != null) {
			budgetCycleLinkEVOs.addAll(this.mBudgetCycleEVO.getBudgetCycleLinks());
		}
		Iterator xmlFormsIterator = editorData.getXmlForms().iterator();
		while (xmlFormsIterator.hasNext()) {
			Object[] object = (Object[]) xmlFormsIterator.next();
			if (object != null) {
				BudgetCycleLinkPK linkPK = new BudgetCycleLinkPK(this.mBudgetCycleEVO.getBudgetCycleId(), (Integer)object[0]);
				BudgetCycleLinkEVO budgetCycleLinkEVO = this.mBudgetCycleEVO.getBudgetCycleLinks() != null ? this.mBudgetCycleEVO.getBudgetCycleLinkItem(linkPK) : null;
				
				if ((budgetCycleLinkEVO != null) && (!budgetCycleLinkEVO.getXmlFormDataType().equals((String)object[1]))) { // Update budget cycle link
					budgetCycleLinkEVO.setXmlFormDataType((String)object[1]);
					this.mBudgetCycleEVO.changeBudgetCycleLinkItem(budgetCycleLinkEVO);
					budgetCycleLinkEVOs.remove(budgetCycleLinkEVO);
				} else if (budgetCycleLinkEVO == null) {
					budgetCycleLinkEVO = new BudgetCycleLinkEVO(this.mBudgetCycleEVO.getBudgetCycleId(), (Integer)object[0], (String)object[1]);
					budgetCycleLinkEVO.prepareForInsert(this.mBudgetCycleEVO);
					budgetCycleLinkEVO.setInsertPending();
					this.mBudgetCycleEVO.addBudgetCycleLinkItem(budgetCycleLinkEVO);
				} else {
					budgetCycleLinkEVOs.remove(budgetCycleLinkEVO);
				}
			}
		}
		for (int i = 0; i < budgetCycleLinkEVOs.size(); i++) {
			BudgetCycleLinkEVO budgetCycleLinkEVO = (BudgetCycleLinkEVO) budgetCycleLinkEVOs.get(i);
			this.mBudgetCycleEVO.deleteBudgetCycleLinkItem(budgetCycleLinkEVO.getPK());
		}
	}
}