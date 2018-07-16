// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:08:10
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.impl.cm.frmwrk;

import com.cedar.cp.api.base.CPException;
import com.cedar.cp.api.base.EntityList;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.cubeformula.CubeFormulaRef;
import com.cedar.cp.api.datatype.DataTypeRef;
import com.cedar.cp.api.dimension.DimensionRef;
import com.cedar.cp.api.model.FinanceCube;
import com.cedar.cp.api.model.FinanceCubeEditor;
import com.cedar.cp.api.model.FinanceCubeEditorSession;
import com.cedar.cp.api.model.FinanceCubeRef;
import com.cedar.cp.api.model.FinanceCubesProcess;
import com.cedar.cp.api.model.ModelRef;
import com.cedar.cp.dto.base.EntityEventMessage;
import com.cedar.cp.dto.base.PrimaryKey;
import com.cedar.cp.dto.datatype.AllDataTypesELO;
import com.cedar.cp.dto.datatype.DataTypeRefImpl;
import com.cedar.cp.dto.dimension.AllDimensionsELO;
import com.cedar.cp.dto.dimension.DimensionElementEvent;
import com.cedar.cp.dto.dimension.DimensionElementPK;
import com.cedar.cp.dto.dimension.DimensionElementRefImpl;
import com.cedar.cp.dto.dimension.DimensionPK;
import com.cedar.cp.dto.dimension.DimensionRefImpl;
import com.cedar.cp.dto.dimension.HierarchyElementCK;
import com.cedar.cp.dto.dimension.HierarchyElementFeedCK;
import com.cedar.cp.dto.dimension.HierarchyElementFeedPK;
import com.cedar.cp.dto.dimension.HierarchyElementPK;
import com.cedar.cp.dto.dimension.calendar.CalendarYearSpecImpl;
import com.cedar.cp.dto.dimension.event.HierarchyElementEvent;
import com.cedar.cp.dto.dimension.event.InsertDimensionElementEvent;
import com.cedar.cp.dto.dimension.event.InsertHierarchyElementEvent;
import com.cedar.cp.dto.dimension.event.InsertHierarchyElementFeedEvent;
import com.cedar.cp.dto.dimension.event.InsertHierarchyEvent;
import com.cedar.cp.dto.dimension.event.MoveHierarchyElementEvent;
import com.cedar.cp.dto.dimension.event.MoveHierarchyElementFeedEvent;
import com.cedar.cp.dto.dimension.event.RemoveDimensionElementEvent;
import com.cedar.cp.dto.dimension.event.RemoveHierarchyElementEvent;
import com.cedar.cp.dto.dimension.event.RemoveHierarchyElementFeedEvent;
import com.cedar.cp.dto.dimension.event.RemoveHierarchyEvent;
import com.cedar.cp.dto.dimension.event.UpdateDimensionElementEvent;
import com.cedar.cp.dto.dimension.event.UpdateHierarchyElementEvent;
import com.cedar.cp.dto.model.AllFinanceCubesWebForModelELO;
import com.cedar.cp.dto.model.FinanceCubeCK;
import com.cedar.cp.dto.model.FinanceCubePK;
import com.cedar.cp.dto.model.FinanceCubeRefImpl;
import com.cedar.cp.dto.model.ModelDimensionsELO;
import com.cedar.cp.dto.model.ModelPK;
import com.cedar.cp.dto.task.TaskMessageLogger;
import com.cedar.cp.ejb.api.model.mapping.MappedModelEditorSessionServer;
import com.cedar.cp.ejb.base.common.cache.DAGContext;
import com.cedar.cp.ejb.base.cube.CubeQueryEngine;
import com.cedar.cp.ejb.base.cube.RtCubeCMModule;
import com.cedar.cp.ejb.impl.cm.BudgetCycleChanges;
import com.cedar.cp.ejb.impl.cm.BudgetInstructionChanges;
import com.cedar.cp.ejb.impl.cm.BudgetStateChanges;
import com.cedar.cp.ejb.impl.cm.ChangeMgmtEngine;
import com.cedar.cp.ejb.impl.cm.event.CalendarEvent;
import com.cedar.cp.ejb.impl.cm.event.InsertCalendarYearEvent;
import com.cedar.cp.ejb.impl.cm.event.InsertedDimensionElementEvent;
import com.cedar.cp.ejb.impl.cm.event.InsertedHierarchyElementEvent;
import com.cedar.cp.ejb.impl.cm.event.InsertedHierarchyElementFeedEvent;
import com.cedar.cp.ejb.impl.cm.event.MovedHierarchyElementEvent;
import com.cedar.cp.ejb.impl.cm.event.MovedHierarchyElementFeedEvent;
import com.cedar.cp.ejb.impl.cm.event.PostUpdateDimensionElementEvent;
import com.cedar.cp.ejb.impl.cm.event.PostUpdateHierarchyElementEvent;
import com.cedar.cp.ejb.impl.cm.event.PostUpdateHierarchyElementFeedEvent;
import com.cedar.cp.ejb.impl.cm.event.RemoveCalendarYearEvent;
import com.cedar.cp.ejb.impl.cm.event.RemovedDimensionElementEvent;
import com.cedar.cp.ejb.impl.cm.event.RemovedHierarchyElementEvent;
import com.cedar.cp.ejb.impl.cm.event.RemovedHierarchyElementFeedEvent;
import com.cedar.cp.ejb.impl.cm.event.UpdateCalendarYearEvent;
import com.cedar.cp.ejb.impl.cm.event.UpdatedDimensionElementEvent;
import com.cedar.cp.ejb.impl.cm.event.UpdatedHierarchyElementEvent;
import com.cedar.cp.ejb.impl.cm.frmwrk.CMEventFactory;
import com.cedar.cp.ejb.impl.cm.frmwrk.CMMetaDataController;
import com.cedar.cp.ejb.impl.cm.frmwrk.CMUpdateListener;
import com.cedar.cp.ejb.impl.cm.frmwrk.CalendarLeafLevelUpdate;
import com.cedar.cp.ejb.impl.cm.xml.ChangeManagementEvent;
import com.cedar.cp.ejb.impl.cm.xml.ChangeManagementType;
import com.cedar.cp.ejb.impl.cm.xml.CubeEvent;
import com.cedar.cp.ejb.impl.cm.xml.DimensionEvent;
import com.cedar.cp.ejb.impl.cm.xml.HierarchyEvent;
import com.cedar.cp.ejb.impl.cm.xml.YearSpecType;
import com.cedar.cp.ejb.impl.cubeformula.CubeFormulaCMModule;
import com.cedar.cp.ejb.impl.datatype.DataTypeDAO;
import com.cedar.cp.ejb.impl.dimension.CalendarYearSpecDAG;
import com.cedar.cp.ejb.impl.dimension.DimensionAccessor;
import com.cedar.cp.ejb.impl.dimension.DimensionDAG;
import com.cedar.cp.ejb.impl.dimension.DimensionEVO;
import com.cedar.cp.ejb.impl.dimension.DimensionEditorEngine;
import com.cedar.cp.ejb.impl.dimension.DimensionElementDAG;
import com.cedar.cp.ejb.impl.dimension.HierarchyDAG;
import com.cedar.cp.ejb.impl.dimension.HierarchyEVO;
import com.cedar.cp.ejb.impl.dimension.HierarchyEditorEngine;
import com.cedar.cp.ejb.impl.dimension.HierarchyElementDAG;
import com.cedar.cp.ejb.impl.dimension.HierarchyElementFeedDAG;
import com.cedar.cp.ejb.impl.dimension.HierarchyNodeDAG;
import com.cedar.cp.ejb.impl.dimension.calendar.CalendarEditorEngine;
import com.cedar.cp.ejb.impl.model.FinanceCubeDAO;
import com.cedar.cp.ejb.impl.model.ModelAccessor;
import com.cedar.cp.ejb.impl.model.ModelEVO;
import com.cedar.cp.ejb.impl.model.cm.AmmModelCMModule;
import com.cedar.cp.ejb.impl.model.cm.BudgetLocationCMModule;
import com.cedar.cp.ejb.impl.model.cm.CellCalculationCMModule;
import com.cedar.cp.ejb.impl.model.cm.FinanceCubeCMModule;
import com.cedar.cp.ejb.impl.model.cm.ModelMappingCMModule;
import com.cedar.cp.ejb.impl.model.virement.VirementCMModule;
import com.cedar.cp.util.Log;
import com.cedar.cp.util.Timer;
import com.cedar.cp.util.common.JmsConnectionImpl;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import javax.naming.InitialContext;
import javax.xml.datatype.XMLGregorianCalendar;

public class CMMetaDataUpdateKernel implements CMMetaDataController {

   private ModelEVO mModelEVO;
   private int mNextCalendarYearSpecId = -1;
   private InitialContext mInitialContext;
   private DAGContext mDAGContext;
   private ModelAccessor mModelAccessor;
   private DimensionAccessor mDimensionAccessor;
   private MappedModelEditorSessionServer mMappedModelEditorSessionServer;
   private DimensionEVO mDimensionEVO;
   private DimensionDAG mDimensionDAG;
   private CalendarEditorEngine mCalendarEditorEngine;
   private DimensionEditorEngine mDimensionEditorEngine;
   private HierarchyEditorEngine mHierarchyEditorEngine;
   private HierarchyEVO mHierarchyEVO;
   private HierarchyDAG mHierarchyDAG;
   private List mListeners = new ArrayList();
   private Log mLog = new Log(this.getClass());
   private ChangeMgmtEngine mChangeMgmtEngine;
   private EntityList mModelFinanceCubes;
   private CMEventFactory mFactory;
   private Integer mTaskId;


   public CMMetaDataUpdateKernel(ChangeMgmtEngine engine) {
      this.mChangeMgmtEngine = engine;
      this.mFactory = new CMEventFactory();
      this.registerModuleListener(new FinanceCubeCMModule(this));
      this.registerModuleListener(new BudgetLocationCMModule(this));
      this.registerModuleListener(new VirementCMModule(this));
      this.registerModuleListener(new CellCalculationCMModule(this));
      this.registerModuleListener(new BudgetStateChanges(this));
      this.registerModuleListener(new BudgetCycleChanges());
      this.registerModuleListener(new BudgetInstructionChanges());
      this.registerModuleListener(new ModelMappingCMModule(this));
      this.registerModuleListener(new AmmModelCMModule(this));
      this.registerModuleListener(new RtCubeCMModule(this));
      this.registerModuleListener(new CubeFormulaCMModule(this));
   }

   public DimensionDAG getDimension() {
      return this.mDimensionDAG;
   }

   public DimensionElementDAG getDimensionElementDAG(DimensionElementPK key) {
      return this.mDimensionDAG != null?this.mDimensionDAG.findElement(key):null;
   }

   public HierarchyDAG getHierarchy() {
      return this.mHierarchyDAG;
   }

   public HierarchyNodeDAG getHierarchyNode(Object key) {
      int id;
      if(key instanceof HierarchyElementPK) {
         id = ((HierarchyElementPK)key).getHierarchyElementId();
      } else if(key instanceof HierarchyElementCK) {
         id = ((HierarchyElementCK)key).getHierarchyElementPK().getHierarchyElementId();
      } else if(key instanceof HierarchyElementFeedPK) {
         id = ((HierarchyElementFeedPK)key).getDimensionElementId();
      } else {
         if(!(key instanceof HierarchyElementFeedCK)) {
            throw new IllegalArgumentException("Unknown hierarchy element key:" + key.getClass());
         }

         id = ((HierarchyElementFeedCK)key).getHierarchyElementFeedPK().getDimensionElementId();
      }

      return this.mHierarchyDAG != null?this.mHierarchyDAG.find(id):null;
   }

   public ModelRef getModelRef() {
      return this.mChangeMgmtEngine.getModelRef();
   }

   public EntityList getFinanceCubes() {
      if(this.mModelFinanceCubes == null) {
         int modelId = ((ModelPK)this.getModelRef().getPrimaryKey()).getModelId();
         this.mModelFinanceCubes = (new FinanceCubeDAO()).getFinanceCubesForModel(modelId);
      }

      return this.mModelFinanceCubes;
   }

   public ModelEVO getModelEVO() {
      if(this.mModelEVO == null) {
         try {
            this.mModelEVO = this.getModelAccessor().getDetails(this.getModelRef().getPrimaryKey(), "");
         } catch (Exception var2) {
            throw new RuntimeException("Failed to query model evo:" + var2.getMessage(), var2);
         }
      }

      return this.mModelEVO;
   }

   public void registerFinanceCubeForRebuild(FinanceCubePK cubePK) {
      try {
         this.mChangeMgmtEngine.registerFinanceCubeForRebuild(cubePK);
      } catch (Exception var3) {
         throw new RuntimeException(var3);
      }
   }

   public boolean isFinanceCubeRegisteredForRebuild(FinanceCubePK cubePK) {
      return this.mChangeMgmtEngine.isFinanceCubeRegisteredForRebuild(cubePK);
   }

   public void registerAllFinanceCubesForRebuild() {
      EntityList financeCubes = this.getFinanceCubes();

      for(int row = 0; row < financeCubes.getNumRows(); ++row) {
         FinanceCubeRef fcRef = (FinanceCubeRef)financeCubes.getValueAt(row, "FinanceCube");
         this.registerFinanceCubeForRebuild(((FinanceCubeCK)fcRef.getPrimaryKey()).getFinanceCubePK());
      }

   }

   public void registerModuleListener(CMUpdateListener listener) {
      this.mListeners.add(listener);
   }

   public boolean cubeHasBalancesForElement(FinanceCubeRef fcRef, DimensionRef dimension, int dimensionElementId) {
      int financeCubeId = fcRef.getPrimaryKey() instanceof FinanceCubePK?((FinanceCubePK)fcRef.getPrimaryKey()).getFinanceCubeId():(fcRef.getPrimaryKey() instanceof FinanceCubeCK?((FinanceCubeCK)fcRef.getPrimaryKey()).getFinanceCubePK().getFinanceCubeId():-1);
      if(financeCubeId == -1) {
         throw new IllegalArgumentException("Unexpected key for finance cube:" + fcRef.getPrimaryKey().getClass());
      } else {
         int dimensionId;
         if(dimension != null) {
            dimensionId = ((DimensionPK)dimension.getPrimaryKey()).getDimensionId();
         } else {
            if(this.mLog.isDebugEnabled()) {
               this.mLog.debug("cubeHasBalancesForElement", "dimension not supplied - defaulted to " + this.mDimensionEVO.getPK());
            }

            dimensionId = this.mDimensionEVO.getDimensionId();
         }

         CubeQueryEngine cubeQueryEngine = new CubeQueryEngine();
         cubeQueryEngine.setFinanceCubeId(financeCubeId);
         return cubeQueryEngine.isCarryingBalancesForElement(dimensionId, dimensionElementId);
      }
   }

   public int getDimensionIndex(int dimensionId) {
      try {
         ModelDimensionsELO e = this.getModelAccessor().getModelDimensions(this.getModelEVO().getModelId());

         for(int index = 0; index < e.getNumRows(); ++index) {
            DimensionRefImpl dimRef = (DimensionRefImpl)e.getValueAt(index, "Dimension");
            if(dimRef.getDimensionPK().getDimensionId() == dimensionId) {
               return index;
            }
         }

         throw new IllegalStateException("Failed to locate dimension index for dimension id:" + dimensionId);
      } catch (Exception var5) {
         var5.printStackTrace();
         throw new CPException("Failed to get model accessor:" + var5.getMessage());
      }
   }

   public int getDimensionCount() {
      try {
         ModelDimensionsELO e = this.getModelAccessor().getModelDimensions(this.getModelEVO().getModelId());
         return e.getNumRows();
      } catch (Exception var2) {
         var2.printStackTrace();
         throw new CPException("Failed to get model accessor:" + var2.getMessage());
      }
   }

   public void process(ModelRef modelRef, ChangeManagementType cm) throws Exception {
      XMLGregorianCalendar cmTimeStamp = cm.getExtractDateTime();
      String sourceSystem = cm.getSourceSystemName();
      DateFormat df = DateFormat.getDateTimeInstance();
      if(this.mLog.isDebugEnabled() && cmTimeStamp != null) {
         this.mLog.debug("process(ChangeManagement)", "ChangeManagement( sourceSystem=" + sourceSystem + " timeStamp=" + df.format(Integer.valueOf(cmTimeStamp.getMillisecond())) + " )");
      }

      this.dispatchInitProcessing(modelRef);
      Iterator eIter = cm.getEvent().iterator();

      while(eIter.hasNext()) {
         ChangeManagementEvent event = (ChangeManagementEvent)eIter.next();
         this.process(event, sourceSystem);
      }

      this.getModelAccessor().flush((ModelPK)modelRef.getPrimaryKey());
      this.sendEntityEventMessage("Model", (ModelPK)modelRef.getPrimaryKey(), 3);
      this.dispatchTerminateProcessing(modelRef);
   }

   private void process(ChangeManagementEvent cmEvent, String sourceSystem) throws Exception {
      if(this.mLog.isDebugEnabled()) {
         this.mLog.debug("process(ChangeManagementEvent)", "ChangeManagementEvent( action=" + cmEvent.getAction() + " type=" + cmEvent.getType() + " visId=" + cmEvent.getVisId() + " )");
      }

      int action;
      if(cmEvent.getType().value().equalsIgnoreCase("dimension")) {
         if(!cmEvent.getAction().value().equalsIgnoreCase("amend")) {
            throw new ValidationException("Only the amend action is supported in a dimension event");
         }

         this.mChangeMgmtEngine.writeReportLine("Updating Dimension " + cmEvent.getVisId());
         this.mDimensionDAG = this.loadDimension(cmEvent.getVisId());
         if(this.mDimensionDAG == null) {
            throw new ValidationException("Unable to locate dimension:" + cmEvent.getVisId());
         }

         this.dispatchInitProcessing(this.mDimensionDAG);
         this.mDimensionEditorEngine = new DimensionEditorEngine(this.getInitialContext(), this.getDAGContext(), true);
         this.mDimensionEditorEngine.setDimensionDAG(this.mDimensionDAG);
         this.mDimensionEditorEngine.setDAOValidationDisabled(true);
         this.processDimensionEvents(cmEvent.getVisId(), cmEvent.getEvent().iterator(), sourceSystem);
         if(sourceSystem != null && !sourceSystem.equalsIgnoreCase("CP") && this.mDimensionEVO.getExternalSystemRef() != null && this.mDimensionEVO.getExternalSystemRef().intValue() != 0) {
            action = this.mDimensionDAG.getPK().getDimensionId();
            int i$ = this.mDimensionEVO.getExternalSystemRef().intValue();
            this.mChangeMgmtEngine.getTaskMessageLogger().log("importing dimension " + cmEvent.getVisId() + " from " + sourceSystem);
            ChangeManagementEvent cllu = this.mChangeMgmtEngine.compareDimWithExternalSystem(action, i$);
            if(cllu != null) {
               this.processDimensionEvents(cmEvent.getVisId(), cllu.getEvent().iterator(), sourceSystem);
            }
         }

         this.saveDimension();
         this.flushDimensionUpdates(this.mDimensionDAG.getPK());
         this.sendEntityEventMessage("Dimension", this.mDimensionEVO.getPK(), 3);
         this.dispatchTermProcessing(this.mDimensionDAG);
         this.mDimensionDAG = null;
      } else if(cmEvent.getType().value().equalsIgnoreCase("calendar")) {
         if(!cmEvent.getAction().value().equalsIgnoreCase("amend")) {
            throw new ValidationException("Only the amend action supported in a calendar event");
         }

         this.mChangeMgmtEngine.writeReportLine("Updating Calendar " + cmEvent.getVisId());
         this.mDimensionDAG = this.loadDimension(cmEvent.getVisId());
         if(this.mDimensionDAG == null) {
            throw new ValidationException("Unable to locate calendar:" + cmEvent.getVisId());
         }

         this.dispatchInitProcessing(this.mDimensionDAG);
         this.mChangeMgmtEngine.registerDimension(this.mDimensionDAG.getPK());
         this.mCalendarEditorEngine = new CalendarEditorEngine(this.getInitialContext(), true);
         this.mCalendarEditorEngine.setDimension(this.mDimensionDAG);
         this.mHierarchyDAG = this.loadHierarchy(cmEvent.getVisId());
         this.mCalendarEditorEngine.setHierarchy(this.mHierarchyDAG);
         Iterator action1 = cmEvent.getEvent().iterator();

         while(action1.hasNext()) {
            DimensionEvent i$1 = (DimensionEvent)action1.next();
            String cllu1 = i$1.getType().value();
            if(!cllu1.equalsIgnoreCase("year")) {
               throw new ValidationException("Expected element type \'year\' under calendar event");
            }

            this.processCalendarElementEvent(i$1);
         }

         this.saveCalendar();
         this.flushDimensionUpdates(this.mDimensionDAG.getPK());
         this.sendEntityEventMessage("Dimension", this.mDimensionEVO.getPK(), 3);
         this.sendEntityEventMessage("Hierarchy", this.mHierarchyEVO.getPK(), 3);
         this.sendEntityEventMessage("StructureElement", this.mHierarchyEVO.getPK(), 3);
         if(!this.mCalendarEditorEngine.getLeafLevelUpdates().isEmpty()) {
            this.mDimensionDAG = this.loadDimension(this.mDimensionDAG.getVisId());
            this.mCalendarEditorEngine.setDimension(this.mDimensionDAG);
            this.mHierarchyDAG = this.loadHierarchy(this.mHierarchyDAG.getVisId());
            this.mCalendarEditorEngine.setHierarchy(this.mHierarchyDAG);
            this.mCalendarEditorEngine.primeLeafLevelUpdates();
            Iterator i$2 = this.mCalendarEditorEngine.getLeafLevelUpdates().iterator();

            while(i$2.hasNext()) {
               CalendarLeafLevelUpdate cllu2 = (CalendarLeafLevelUpdate)i$2.next();
               this.mChangeMgmtEngine.registerCalendarLeafLevelUpdate(cllu2);
            }
         }

         this.dispatchTermProcessing(this.mDimensionDAG);
         this.mCalendarEditorEngine = null;
         this.mHierarchyDAG = null;
         this.mDimensionDAG = null;
      } else if(cmEvent.getType().value().equalsIgnoreCase("import-values")) {
         if(!cmEvent.getAction().value().equalsIgnoreCase("import")) {
            throw new ValidationException("Unexpected action on financial-import event type:" + cmEvent.getAction());
         }

         action = Integer.decode(cmEvent.getVisId()).intValue();
         if(this.mLog.isDebugEnabled()) {
            this.mLog.debug("import-values", action + " " + sourceSystem);
         }

         this.mChangeMgmtEngine.registerExternalImport(new FinanceCubePK(action), sourceSystem);
      } else {
         if(!cmEvent.getType().value().equalsIgnoreCase("finance-cube")) {
            throw new ValidationException("Unknown type for cm event:" + cmEvent.getType());
         }

         String action2 = cmEvent.getAction().value();
         if(!action2.equalsIgnoreCase("amend") && !action2.equalsIgnoreCase("export-views")) {
            throw new ValidationException("Unexpected action type for finanec cube action:" + action2);
         }

         this.processCubeEvent(cmEvent);
      }

   }

   private void processDimensionEvents(String visId, Iterator eIter, String sourceSystem) throws Exception {
      if(eIter.hasNext()) {
         this.mChangeMgmtEngine.getTaskMessageLogger().log("changes found for dimension " + visId);
         this.mChangeMgmtEngine.registerDimension(this.mDimensionDAG.getPK());
      }

      while(eIter.hasNext()) {
         DimensionEvent event = (DimensionEvent)eIter.next();
         String type = event.getType().value();
         if(type.equalsIgnoreCase("dimension-element")) {
            this.processDimensionElementEvent(event);
         } else if(type.equalsIgnoreCase("hierarchy")) {
            this.processDimensionHierarchyEvent(event);
         }
      }

   }

   private void processCubeEvent(ChangeManagementEvent cmEvent) throws Exception {
      if(this.mLog.isDebugEnabled()) {
         this.mLog.debug("process(ChangeManagementEvent)", "ChangeManagementEvent( action=" + cmEvent.getAction() + " type=" + cmEvent.getType() + " visId=" + cmEvent.getVisId() + " )");
      }

      FinanceCubesProcess process = this.mChangeMgmtEngine.getCPConnection().getFinanceCubesProcess();
      FinanceCubeRefImpl fcRef = this.getFinanceCubeRef(cmEvent.getVisId());
      if(cmEvent.getAction().value().equalsIgnoreCase("export-views")) {
         if(this.mLog.isDebugEnabled()) {
            this.mLog.debug("export-views", ((FinanceCubeCK)fcRef.getPrimaryKey()).getFinanceCubePK());
         }

         this.mChangeMgmtEngine.registerExportView(((FinanceCubeCK)fcRef.getPrimaryKey()).getFinanceCubePK());
      } else {
         FinanceCubeEditorSession session = process.getFinanceCubeEditorSession(fcRef.getPrimaryKey());
         FinanceCubeEditor editor = session.getFinanceCubeEditor();
         FinanceCube financeCube = editor.getFinanceCube();
         Map dataTypeMap = this.queryDataTypes();
         Iterator cIter = cmEvent.getCubeEvent().iterator();

         while(cIter.hasNext()) {
            CubeEvent cEvent = (CubeEvent)cIter.next();
            String type = cEvent.getType().value();
            if(type == null || !type.equalsIgnoreCase("data-type")) {
               throw new ValidationException("Unexpected type in CubeEvent:" + type);
            }

            String action = cEvent.getAction().value();
            String visId = cEvent.getVisId();
            DataTypeRef dtRef = (DataTypeRef)dataTypeMap.get(visId);
            if(dtRef == null) {
               throw new ValidationException("Unable to locate data type[" + visId + "]");
            }

            if(action.equalsIgnoreCase("insert")) {
               editor.addSelectedDataTypeRef(dtRef);
               if(dtRef.allowsConfigrableRollUp() && cEvent.getRollUpRules() != null) {
                  this.applyRollUpRules(editor, dtRef, cEvent.getRollUpRules());
               }

               this.mChangeMgmtEngine.registerFinanceCubeDataTypeUpdate(fcRef, 1, dtRef, this.convertToBoolean(cEvent.getRollUpRules()));
            } else if(action.equalsIgnoreCase("amend")) {
               if(dtRef.allowsConfigrableRollUp() && cEvent.getRollUpRules() != null) {
                  this.applyRollUpRules(editor, dtRef, cEvent.getRollUpRules());
                  this.mChangeMgmtEngine.registerFinanceCubeDataTypeUpdate(fcRef, 2, dtRef, this.convertToBoolean(cEvent.getRollUpRules()));
               }
            } else {
               if(!action.equalsIgnoreCase("delete")) {
                  throw new ValidationException("Unexpected action for CubeEvent:" + action);
               }

               editor.removeSelectedDataTypeRef(dtRef);
               this.mChangeMgmtEngine.registerFinanceCubeDataTypeUpdate(fcRef, 3, dtRef, (boolean[])null);
            }
         }

         editor.commit();
         session.commit(false);
      }
   }

   private boolean[] convertToBoolean(String rollUpRules) {
      if(rollUpRules == null) {
         return null;
      } else {
         ArrayList rollUpRuleList = new ArrayList();
         StringTokenizer st = new StringTokenizer(rollUpRules, ",", false);

         while(st.hasMoreTokens()) {
            rollUpRuleList.add(Boolean.valueOf(Boolean.parseBoolean(st.nextToken())));
         }

         boolean[] result = new boolean[rollUpRuleList.size()];

         for(int i = 0; i < rollUpRuleList.size(); ++i) {
            result[i] = ((Boolean)rollUpRuleList.get(i)).booleanValue();
         }

         return result;
      }
   }

   private void applyRollUpRules(FinanceCubeEditor editor, DataTypeRef dtRef, String rollUpRules) throws ValidationException {
      StringTokenizer st = new StringTokenizer(rollUpRules, ",", false);

      for(int dimIndex = 0; st.hasMoreTokens(); ++dimIndex) {
         Boolean rollUp = Boolean.valueOf(Boolean.parseBoolean(st.nextToken()));
         editor.setRollUpRule(dtRef, editor.getFinanceCube().getDimensions()[dimIndex], rollUp.booleanValue());
      }

   }

   private DataTypeRef locateDataTypeRef(String visId, DataTypeRef[] refs) {
      DataTypeRef[] arr$ = refs;
      int len$ = refs.length;

      for(int i$ = 0; i$ < len$; ++i$) {
         DataTypeRef dtRef = arr$[i$];
         if(dtRef.getNarrative().equals(visId)) {
            return dtRef;
         }
      }

      return null;
   }

   private FinanceCubeRefImpl getFinanceCubeRef(String visId) throws ValidationException {
      AllFinanceCubesWebForModelELO financeCubesELO = (new FinanceCubeDAO()).getAllFinanceCubesWebForModel(this.getModelEVO().getModelId());
      FinanceCubeRefImpl fcRef = null;

      for(int i = 0; i < financeCubesELO.getNumRows(); ++i) {
         fcRef = (FinanceCubeRefImpl)financeCubesELO.getValueAt(i, "FinanceCube");
         if(fcRef.getNarrative().equals(visId)) {
            break;
         }

         fcRef = null;
      }

      if(fcRef == null) {
         throw new ValidationException("Unable to locate finance cube [" + visId + "]");
      } else {
         return fcRef;
      }
   }

   private void processDimensionElementEvent(DimensionEvent dEvent) throws Exception {
      if(this.mLog.isDebugEnabled()) {
         this.mLog.debug("process(ChangeManagementEvent)", "ChangeManagementEvent( action=" + dEvent.getAction() + " type=" + dEvent.getType() + " visId=" + dEvent.getVisId() + " )");
      }

      DimensionElementDAG preElementDAG = this.mDimensionDAG.findElement(dEvent.getAction().value().equalsIgnoreCase("amend") && dEvent.getOrigVisId() != null?dEvent.getOrigVisId():dEvent.getVisId());
      if((dEvent.getAction().value().equalsIgnoreCase("amend") || dEvent.getAction().value().equalsIgnoreCase("delete")) && preElementDAG == null) {
         throw new ValidationException("Unable to locate dimension element with visid[" + dEvent.getVisId() + "]");
      } else {
         DimensionElementPK elementPK = preElementDAG != null?preElementDAG.getPK():null;
         DimensionElementEvent deEvent = this.mFactory.createDimensionElementEvent(dEvent, elementPK);
         this.dispatch(deEvent);
         this.mDimensionEditorEngine.processEvents((com.cedar.cp.api.dimension.DimensionEvent)deEvent);
         DimensionElementDAG postElementDAG = this.mDimensionDAG.findElement(dEvent.getVisId());
         PostUpdateDimensionElementEvent postDeEvent = this.mFactory.createPostUpdateDimensionElementEvent(deEvent, preElementDAG, postElementDAG);
         this.dispatch(postDeEvent);
      }
   }

   private void processCalendarElementEvent(DimensionEvent dEvent) throws Exception {
      if(this.mLog.isDebugEnabled()) {
         this.mLog.debug("processCalendarElementEvent(DimensionEvent)", "DimensionEvent( action=" + dEvent.getAction() + " type=" + dEvent.getType() + " visId=" + dEvent.getVisId() + " )");
      }

      String action = dEvent.getAction().value();
      YearSpecType yearSpec = dEvent.getYearSpec();
      ArrayList oldYearSpecs = new ArrayList(this.mDimensionDAG.getYearSpecs());
      CalendarYearSpecImpl nys = new CalendarYearSpecImpl(--this.mNextCalendarYearSpecId, yearSpec.getYear(), this.toBoolArray(yearSpec));
      CalendarYearSpecDAG oys = null;

      int index;
      for(index = 0; index < oldYearSpecs.size(); ++index) {
         CalendarYearSpecDAG preEvent = (CalendarYearSpecDAG)oldYearSpecs.get(index);
         if(preEvent.getCalendarYear() == nys.getYear()) {
            oys = preEvent;
            break;
         }

         if(preEvent.getCalendarYear() > nys.getYear()) {
            break;
         }
      }

      CalendarEvent var9 = this.mFactory.createCalendarEvent(dEvent, oys, nys);
      this.dispatch(var9);
      if(action.equalsIgnoreCase("insert")) {
         this.mCalendarEditorEngine.addYear(nys, index != 0, this.mDimensionDAG.getCalendarSpec());
      } else if(action.equalsIgnoreCase("amend")) {
         this.mCalendarEditorEngine.updateYear(oys, index, nys, this.mDimensionDAG.getCalendarSpec());
      } else {
         if(!action.equalsIgnoreCase("delete")) {
            throw new ValidationException("Unexpected action type:" + action + " in processCalendarElementEvent");
         }

         this.mCalendarEditorEngine.removeYear(oys, index);
      }

   }

   private boolean[] toBoolArray(YearSpecType ys) {
      boolean[] spec = new boolean[]{true, ys.isHalfYear(), ys.isQuarter(), ys.isMonth(), ys.isWeek(), ys.isDay(), ys.isOpening(), ys.isAdjustment(), ys.isPeriod13(), ys.isPeriod14()};
      return spec;
   }

   private void processDimensionHierarchyEvent(DimensionEvent dEvent) throws Exception {
      if(this.mLog.isDebugEnabled()) {
         this.mLog.debug("processDimensionHierarchyEvent(DimensionEvent)", "DimensionEvent( action=" + dEvent.getAction() + " type=" + dEvent.getType() + " visId=" + dEvent.getVisId() + " )");
      }

      this.mChangeMgmtEngine.writeReportLine("Processing Hierarchy " + dEvent.getVisId());
      String action = dEvent.getAction().value();
      this.mHierarchyEditorEngine = new HierarchyEditorEngine(this.getInitialContext(), this.getDAGContext(), true);
      this.mHierarchyEditorEngine.setAugmentMode(dEvent.isAugment());
      Iterator visId;
      HierarchyEvent deleted;
      if(action.equalsIgnoreCase("insert")) {
         this.mHierarchyEditorEngine.setDimensionDAG(this.mDimensionDAG);
         this.mHierarchyDAG = this.mHierarchyEditorEngine.insertNewHierarchy(dEvent.getVisId(), dEvent.getDescription());
         this.mHierarchyEditorEngine.setHierarchyDAG(this.mHierarchyDAG);
         this.dispatchInitProcessing(this.mHierarchyDAG);
         this.dispatch((com.cedar.cp.api.dimension.HierarchyEvent)(new InsertHierarchyEvent(dEvent.getVisId(), dEvent.getDescription())));
         visId = dEvent.getEvent().iterator();

         while(visId.hasNext()) {
            deleted = (HierarchyEvent)visId.next();
            this.process(deleted);
         }

         this.dispatchTermProcessing(this.mHierarchyDAG);
         this.saveHierarchy();
      } else if(action.equalsIgnoreCase("amend")) {
         this.mHierarchyDAG = this.loadHierarchy(dEvent.getVisId());
         if(this.mHierarchyDAG == null) {
            throw new ValidationException("Unable to locate hierarchy:" + dEvent.getVisId());
         }

         this.dispatchInitProcessing(this.mHierarchyDAG);
         this.mHierarchyEditorEngine.setDimensionDAG(this.mDimensionDAG);
         this.mHierarchyEditorEngine.setHierarchyDAG(this.mHierarchyDAG);
         visId = dEvent.getEvent().iterator();

         while(visId.hasNext()) {
            deleted = (HierarchyEvent)visId.next();
            this.process(deleted);
         }

         this.dispatchTermProcessing(this.mHierarchyDAG);
         this.saveHierarchy();
      } else {
         if(!action.equalsIgnoreCase("delete")) {
            throw new ValidationException("Unknown action type[" + action + "] in hierarchy type event");
         }

         String visId1 = dEvent.getVisId();
         this.mHierarchyDAG = this.loadHierarchy(visId1);
         if(this.mHierarchyDAG == null) {
            throw new ValidationException("Unable to locate hierarchy:" + dEvent.getVisId());
         }

         this.dispatchInitProcessing(this.mHierarchyDAG);
         this.mHierarchyEditorEngine.setDimensionDAG(this.mDimensionDAG);
         this.mHierarchyEditorEngine.setHierarchyDAG(this.mHierarchyDAG);
         boolean deleted1 = false;
         Iterator hIter = this.mDimensionEVO.getHierarchies() != null?this.mDimensionEVO.getHierarchies().iterator():Collections.EMPTY_LIST.iterator();

         while(hIter.hasNext()) {
            HierarchyEVO hEVO = (HierarchyEVO)hIter.next();
            if(hEVO.getVisId().equals(visId1)) {
               this.dispatch((com.cedar.cp.api.dimension.HierarchyEvent)(new RemoveHierarchyEvent(hEVO.getPK())));
               this.mDimensionEVO.deleteHierarchiesItem(hEVO.getPK());
               deleted1 = true;
               break;
            }
         }

         if(!deleted1) {
            throw new ValidationException("Failed to find hierarchy for deletion:" + visId1);
         }

         this.dispatchTermProcessing(this.mHierarchyDAG);
      }

      this.mHierarchyEVO = null;
   }

   private void process(HierarchyEvent hEvent) throws Exception {
      String type = hEvent.getType().value();
      String action = hEvent.getAction().value();
      String visId = action.equalsIgnoreCase("amend") && hEvent.getOrigVisId() != null?hEvent.getOrigVisId():hEvent.getVisId();
      if(this.mLog.isDebugEnabled()) {
         this.mLog.debug("process(HierarchyEvent)", "HierarchyEvent( action=" + action + " type=" + type + " visId=" + visId + " )");
      }

      Object elementPK;
      HierarchyElementDAG parent;
      Object parentPK;
      if(type.equalsIgnoreCase("hierarchy-element")) {
         HierarchyElementDAG preElement = (HierarchyElementDAG)this.mHierarchyDAG.findElement(visId);
         if(!this.mHierarchyEditorEngine.isAugmentMode() && preElement != null && preElement.isAugmentElement()) {
            preElement = null;
         }

         elementPK = preElement != null?preElement.getEntityRef().getPrimaryKey():null;
         parent = null;
         parentPK = null;
         int dimensionElementRef = preElement != null?preElement.getIndex():0;
         if(action.equalsIgnoreCase("insert") || action.equalsIgnoreCase("move")) {
            String origIndex = hEvent.getParent();
            parent = (HierarchyElementDAG)this.mHierarchyDAG.findElement(origIndex);
            if(!this.mHierarchyEditorEngine.isAugmentMode() && parent != null && parent.isAugmentElement()) {
               parent = null;
            }

            if(parent == null && this.mHierarchyDAG.getRoot() != null) {
               throw new ValidationException("Unable to locate parent hierarchy element:" + origIndex);
            }

            parentPK = parent != null?parent.getEntityRef().getPrimaryKey():null;
         }

         HierarchyElementEvent origIndex1 = this.mFactory.createHierarchyElementEvent(hEvent, elementPK, parentPK);
         this.dispatch(origIndex1);
         this.mHierarchyEditorEngine.processEvents((com.cedar.cp.api.dimension.HierarchyEvent)origIndex1);
         HierarchyElementDAG event = (HierarchyElementDAG)this.mHierarchyDAG.findElement(visId);
         PostUpdateHierarchyElementEvent postElement = this.mFactory.createPostUpdateHierarchyElementEvent(origIndex1, preElement, event, parent, dimensionElementRef);
         this.dispatch(postElement);
      } else if(type.equalsIgnoreCase("hierarchy-element-feed")) {
         HierarchyNodeDAG preElement1 = this.mHierarchyDAG.findElement(visId);
         if(!this.mHierarchyEditorEngine.isAugmentMode() && preElement1 != null && preElement1.isAugmentElement()) {
            preElement1 = null;
         }

         elementPK = preElement1 != null?preElement1.getPrimaryKey():null;
         parent = null;
         parentPK = null;
         DimensionElementRefImpl dimensionElementRef1 = null;
         int origIndex2 = preElement1 != null?preElement1.getIndex():0;
         if(action.equalsIgnoreCase("insert") || action.equalsIgnoreCase("move")) {
            String event1 = hEvent.getParent();
            parent = (HierarchyElementDAG)this.mHierarchyDAG.findElement(event1);
            if(!this.mHierarchyEditorEngine.isAugmentMode() && parent != null && parent.isAugmentElement()) {
               parent = null;
            }

            if(parent == null) {
               throw new ValidationException("Unable to locate parent preElement:[" + event1 + "]");
            }

            parentPK = parent.getEntityRef().getPrimaryKey();
         }

         if(action.equalsIgnoreCase("insert")) {
            DimensionElementDAG event2 = this.mDimensionDAG.findElement(hEvent.getVisId());
            if(event2 == null) {
               throw new ValidationException("Unable to locate dimension element on hef insert:" + hEvent.getVisId());
            }

            dimensionElementRef1 = event2.getEntityRef();
         }

         HierarchyElementEvent event3 = this.mFactory.createHierarchyElementFeedEvent(hEvent, elementPK, parentPK, dimensionElementRef1);
         this.dispatch(event3);
         this.mHierarchyEditorEngine.processEvents((com.cedar.cp.api.dimension.HierarchyEvent)event3);
         HierarchyElementFeedDAG postElement1 = (HierarchyElementFeedDAG)this.mHierarchyDAG.findElement(visId);
         HierarchyElementFeedDAG origFeedElement = preElement1 != null && preElement1.isFeeder()?(HierarchyElementFeedDAG)preElement1:null;
         PostUpdateHierarchyElementFeedEvent postEvent = this.mFactory.createPostUpdateHierarchyElementFeedEvent(event3, origFeedElement, postElement1, parent, origIndex2);
         this.dispatch(postEvent);
      }

   }

   private void dispatch(CalendarEvent event) {
      for(int i = 0; i < this.mListeners.size(); ++i) {
         CMUpdateListener listener = (CMUpdateListener)this.mListeners.get(i);
         if(event instanceof InsertCalendarYearEvent) {
            listener.processEvent((InsertCalendarYearEvent)event);
         } else if(event instanceof UpdateCalendarYearEvent) {
            listener.processEvent((UpdateCalendarYearEvent)event);
         } else {
            if(!(event instanceof RemoveCalendarYearEvent)) {
               throw new IllegalArgumentException("Unexpected subclass of CalendarEvent:" + event.getClass());
            }

            listener.processEvent((RemoveCalendarYearEvent)event);
         }
      }

   }

   private void dispatch(com.cedar.cp.api.dimension.HierarchyEvent event) {
      for(int i = 0; i < this.mListeners.size(); ++i) {
         CMUpdateListener listener = (CMUpdateListener)this.mListeners.get(i);
         if(event instanceof InsertHierarchyEvent) {
            listener.processEvent((InsertHierarchyEvent)event);
         } else {
            if(!(event instanceof RemoveHierarchyEvent)) {
               throw new IllegalArgumentException("Unexpected subclass of HierarchyEvent:" + event.getClass());
            }

            listener.processEvent((RemoveHierarchyEvent)event);
         }
      }

   }

   private void dispatch(HierarchyElementEvent event) {
      for(int i = 0; i < this.mListeners.size(); ++i) {
         CMUpdateListener listener = (CMUpdateListener)this.mListeners.get(i);
         if(event instanceof InsertHierarchyElementEvent) {
            listener.processEvent((InsertHierarchyElementEvent)event);
         } else if(event instanceof UpdateHierarchyElementEvent) {
            listener.processEvent((UpdateHierarchyElementEvent)event);
         } else if(event instanceof RemoveHierarchyElementEvent) {
            listener.processEvent((RemoveHierarchyElementEvent)event);
         } else if(event instanceof MoveHierarchyElementEvent) {
            listener.processEvent((MoveHierarchyElementEvent)event);
         } else if(event instanceof InsertHierarchyElementFeedEvent) {
            listener.processEvent((InsertHierarchyElementFeedEvent)event);
         } else if(event instanceof RemoveHierarchyElementFeedEvent) {
            listener.processEvent((RemoveHierarchyElementFeedEvent)event);
         } else {
            if(!(event instanceof MoveHierarchyElementFeedEvent)) {
               throw new IllegalStateException("Unkown subclass of HierarchyElementEvent:" + event.getClass());
            }

            listener.processEvent((MoveHierarchyElementFeedEvent)event);
         }
      }

   }

   private void dispatch(DimensionElementEvent event) {
      for(int i = 0; i < this.mListeners.size(); ++i) {
         CMUpdateListener listener = (CMUpdateListener)this.mListeners.get(i);
         if(event instanceof InsertDimensionElementEvent) {
            listener.processEvent((InsertDimensionElementEvent)event);
         } else if(event instanceof UpdateDimensionElementEvent) {
            listener.processEvent((UpdateDimensionElementEvent)event);
         } else {
            if(!(event instanceof RemoveDimensionElementEvent)) {
               throw new IllegalStateException("Unkown subclass of DimensionElementEvent:" + event.getClass());
            }

            listener.processEvent((RemoveDimensionElementEvent)event);
         }
      }

   }

   private void dispatch(PostUpdateDimensionElementEvent event) {
      for(int i = 0; i < this.mListeners.size(); ++i) {
         CMUpdateListener listener = (CMUpdateListener)this.mListeners.get(i);
         if(event instanceof InsertedDimensionElementEvent) {
            listener.processEvent((InsertedDimensionElementEvent)event);
         } else if(event instanceof UpdatedDimensionElementEvent) {
            listener.processEvent((UpdatedDimensionElementEvent)event);
         } else {
            if(!(event instanceof RemovedDimensionElementEvent)) {
               throw new IllegalStateException("Unkown subclass of PostUpdateDimensionElementEvent:" + event.getClass());
            }

            listener.processEvent((RemovedDimensionElementEvent)event);
         }
      }

   }

   private void dispatch(PostUpdateHierarchyElementEvent event) {
      for(int i = 0; i < this.mListeners.size(); ++i) {
         CMUpdateListener listener = (CMUpdateListener)this.mListeners.get(i);
         if(event instanceof InsertedHierarchyElementEvent) {
            listener.processEvent((InsertedHierarchyElementEvent)event);
         } else if(event instanceof UpdatedHierarchyElementEvent) {
            listener.processEvent((UpdatedHierarchyElementEvent)event);
         } else if(event instanceof RemovedHierarchyElementEvent) {
            listener.processEvent((RemovedHierarchyElementEvent)event);
         } else {
            if(!(event instanceof MovedHierarchyElementEvent)) {
               throw new IllegalStateException("Unkown subclass of PostUpdateHierarchyElementEvent:" + event.getClass());
            }

            listener.processEvent((MovedHierarchyElementEvent)event);
         }
      }

   }

   private void dispatch(PostUpdateHierarchyElementFeedEvent event) {
      for(int i = 0; i < this.mListeners.size(); ++i) {
         CMUpdateListener listener = (CMUpdateListener)this.mListeners.get(i);
         if(event instanceof InsertedHierarchyElementFeedEvent) {
            listener.processEvent((InsertedHierarchyElementFeedEvent)event);
         } else if(event instanceof RemovedHierarchyElementFeedEvent) {
            listener.processEvent((RemovedHierarchyElementFeedEvent)event);
         } else {
            if(!(event instanceof MovedHierarchyElementFeedEvent)) {
               throw new IllegalStateException("Unkown subclass of HierarchyElementEvent:" + event.getClass());
            }

            listener.processEvent((MovedHierarchyElementFeedEvent)event);
         }
      }

   }

   private void dispatchInitProcessing(DimensionDAG dimension) {
      for(int i = 0; i < this.mListeners.size(); ++i) {
         CMUpdateListener listener = (CMUpdateListener)this.mListeners.get(i);
         listener.initProcessing(dimension);
      }

   }

   private void dispatchTermProcessing(DimensionDAG dimension) {
      for(int i = 0; i < this.mListeners.size(); ++i) {
         CMUpdateListener listener = (CMUpdateListener)this.mListeners.get(i);
         listener.terminateProcessing(dimension);
      }

   }

   private void dispatchInitProcessing(HierarchyDAG hierarchy) {
      for(int i = 0; i < this.mListeners.size(); ++i) {
         CMUpdateListener listener = (CMUpdateListener)this.mListeners.get(i);
         listener.initProcessing(hierarchy);
      }

   }

   private void dispatchInitProcessing(ModelRef modelRef) {
      for(int i = 0; i < this.mListeners.size(); ++i) {
         CMUpdateListener listener = (CMUpdateListener)this.mListeners.get(i);
         listener.initProcessing(modelRef);
      }

   }

   private void dispatchTerminateProcessing(ModelRef modelRef) {
      for(int i = 0; i < this.mListeners.size(); ++i) {
         CMUpdateListener listener = (CMUpdateListener)this.mListeners.get(i);
         listener.terminateProcessing(modelRef);
      }

   }

   private void dispatchTermProcessing(HierarchyDAG hierarchy) {
      for(int i = 0; i < this.mListeners.size(); ++i) {
         CMUpdateListener listener = (CMUpdateListener)this.mListeners.get(i);
         listener.terminateProcessing(hierarchy);
      }

   }

   private DimensionDAG loadDimension(String dimVisId) throws Exception {
      Timer timer = this.mLog.isInfoEnabled()?new Timer(this.mLog):null;
      DimensionPK dimPK = this.queryDimensionPK(dimVisId);
      this.mDimensionEVO = this.getDimensionAccessor().getDetails(dimPK, "<0><1><2><3><4><5><6><7><8>");
      this.mDimensionDAG = new DimensionDAG(this.getDAGContext(), this.mDimensionEVO);
      if(timer != null) {
         timer.logInfo("loadDimension", "");
      }

      return this.mDimensionDAG;
   }

   private void saveDimension() throws Exception {
      Timer timer = this.mLog.isInfoEnabled()?new Timer(this.mLog):null;
      this.mDimensionDAG.updateEVO(this.mDimensionEVO);
      this.getDimensionAccessor().setDetails(this.mDimensionEVO);
      if(timer != null) {
         timer.logInfo("saveDimension", "");
      }

   }

   private void saveHierarchy() throws Exception {
      Timer timer = this.mLog.isInfoEnabled()?new Timer(this.mLog):null;
      if(this.mHierarchyEVO == null) {
         this.mHierarchyEVO = this.mHierarchyDAG.createEVO();
         this.mDimensionEVO.addHierarchiesItem(this.mHierarchyEVO);
      } else {
         this.mHierarchyDAG.updateEVO(this.mHierarchyEVO);
         this.mHierarchyEditorEngine.updateEVOFromRemovedElementList(this.mHierarchyEVO);
      }

      if(timer != null) {
         timer.logInfo("saveHierarchy", "");
      }

   }

   private void saveCalendar() throws Exception {
      Timer timer = this.mLog.isInfoEnabled()?new Timer(this.mLog):null;
      this.mCalendarEditorEngine.updateEVOs(this.mDimensionEVO, this.mHierarchyEVO);
      this.getDimensionAccessor().setDetails(this.mDimensionEVO);
      if(timer != null) {
         timer.logInfo("saveCalendar", "");
      }

   }

   private DimensionPK queryDimensionPK(String visid) throws Exception {
      AllDimensionsELO dimensions = this.getDimensionAccessor().getAllDimensions();
      Object[] dimRefs = dimensions.getValues("Dimension");

      for(int i = 0; i < dimRefs.length; ++i) {
         DimensionRef dimRef = (DimensionRef)dimRefs[i];
         if(dimRef.getNarrative().equals(visid)) {
            return (DimensionPK)dimRef.getPrimaryKey();
         }
      }

      return null;
   }

   private HierarchyDAG loadHierarchy(String visId) throws Exception {
      Timer timer = this.mLog.isInfoEnabled()?new Timer(this.mLog):null;
      HierarchyDAG result = null;
      Iterator hIter = this.mDimensionEVO.getHierarchies().iterator();

      while(hIter.hasNext()) {
         HierarchyEVO hEVO = (HierarchyEVO)hIter.next();
         if(hEVO.getVisId().equals(visId)) {
            this.mHierarchyEVO = hEVO;
            result = new HierarchyDAG(this.getDAGContext(), this.mDimensionDAG, this.mHierarchyEVO);
            break;
         }
      }

      if(timer != null) {
         timer.logInfo("loadHierarchy", "");
      }

      return result;
   }

   private void flushDimensionUpdates(DimensionPK dimensionPK) throws Exception {
      this.getDimensionAccessor().flush(dimensionPK);
   }

   private Map<String, DataTypeRef> queryDataTypes() {
      AllDataTypesELO allDataTypes = (new DataTypeDAO()).getAllDataTypes();
      HashMap dataTypeMap = new HashMap();

      for(int i = 0; i < allDataTypes.getNumRows(); ++i) {
         DataTypeRefImpl dtRefImpl = (DataTypeRefImpl)allDataTypes.getValueAt(i, "DataType");
         dataTypeMap.put(dtRefImpl.getNarrative(), dtRefImpl);
      }

      return dataTypeMap;
   }

   private void sendEntityEventMessage(String tableName, PrimaryKey pk, int changeType) {
      try {
         JmsConnectionImpl t = new JmsConnectionImpl(this.getInitialContext(), 3, "entityEventTopic");
         t.createSession();
         EntityEventMessage em = new EntityEventMessage(tableName, pk, changeType, this.getClass().getName());
         this.mLog.debug("update", "sending event message: " + em.toString());
         t.send(em);
         t.closeSession();
         t.closeConnection();
      } catch (Throwable var6) {
         System.err.println("Failed to send JMS message inside cm update kernel:");
         var6.printStackTrace();
      }

   }

   public int getUserId() {
      return this.mChangeMgmtEngine.getUserId();
   }

   private DimensionAccessor getDimensionAccessor() throws Exception {
      if(this.mDimensionAccessor == null) {
         this.mDimensionAccessor = new DimensionAccessor(this.getInitialContext());
      }

      return this.mDimensionAccessor;
   }

   private ModelAccessor getModelAccessor() throws Exception {
      if(this.mModelAccessor == null) {
         this.mModelAccessor = new ModelAccessor(this.getInitialContext());
      }

      return this.mModelAccessor;
   }

   public MappedModelEditorSessionServer getMappedModelEditorSessionServer() throws Exception {
      if(this.mMappedModelEditorSessionServer == null) {
         this.mMappedModelEditorSessionServer = new MappedModelEditorSessionServer(this.getInitialContext(), false);
      }

      return this.mMappedModelEditorSessionServer;
   }

   public void registerFormulaForUndeploy(FinanceCubeRef financeCubeRef, CubeFormulaRef cubeFormulaRef) {
      this.mChangeMgmtEngine.registerFormulaForUndeploy(financeCubeRef, cubeFormulaRef);
   }

   private InitialContext getInitialContext() throws Exception {
      if(this.mInitialContext == null) {
         this.mInitialContext = new InitialContext();
      }

      return this.mInitialContext;
   }

   private DAGContext getDAGContext() throws Exception {
      if(this.mDAGContext == null) {
         this.mDAGContext = new DAGContext(this.getInitialContext());
      }

      return this.mDAGContext;
   }

   public TaskMessageLogger getTaskMessageLogger() {
      return this.mChangeMgmtEngine.getTaskMessageLogger();
   }

   public void setTaskId(Integer taskId) {
      this.mTaskId = taskId;
   }

   public Integer getTaskId() {
      return this.mTaskId;
   }
}
