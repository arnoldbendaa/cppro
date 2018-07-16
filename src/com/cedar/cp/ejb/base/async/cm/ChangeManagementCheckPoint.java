// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:07:01
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.base.async.cm;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.cedar.cp.api.cubeformula.CubeFormulaRef;
import com.cedar.cp.api.model.FinanceCubeRef;
import com.cedar.cp.dto.datatype.DataTypeRefImpl;
import com.cedar.cp.dto.dimension.DimensionPK;
import com.cedar.cp.dto.model.FinanceCubePK;
import com.cedar.cp.dto.model.FinanceCubeRefImpl;
import com.cedar.cp.ejb.base.async.AbstractTaskCheckpoint;
import com.cedar.cp.ejb.impl.cm.frmwrk.CalendarLeafLevelUpdate;

public class ChangeManagementCheckPoint extends AbstractTaskCheckpoint {

	/*     */   public static enum FormulaUpdateType
	/*     */   {
	/* 666 */     UNDEPLOY, REBUILD;
	/*     */   }
	
   public static final int PHASE_NOT_SET = -1;
   public static final int PHASE_PROCESS_META_DATA = 0;
   public static final int PHASE_REBUILD_RUNTIME_STRUCTURES = 1;
   public static final int PHASE_PROCESS_CALENDAR_LEVEL_CHANGES = 2;
   public static final int PHASE_PROCESS_FORMULA_CHANGES = 3;
   public static final int PHASE_PROCESS_FINANCE_CUBE_UPDATES = 4;
   public static final int PHASE_REBUILD_FINANCE_CUBES = 5;
   public static final int PHASE_EXTERNAL_IMPORT = 6;
   public static final int PHASE_RECREATE_EXPORT_VIEWS = 7;
   public static final int PHASE_TERMINATE = 8;
   public static final int MAX_PHASE_NUMBER = 8;
   private static final String[] CUBE_UPDATES_NARRATIVE = new String[]{"remove data types", "check temp tables", "create temp tables", "rebuild measure parents", "tidy temp tables"};
   public static final int CUBE_UPDATES_DELETE_DATA_TYPES = 0;
   public static final int CUBE_UPDATES_CHECK_TEMP_TABLES = 1;
   public static final int CUBE_UPDATES_CREATE_TEMP_TABLES = 2;
   public static final int CUBE_UPDATES_REBUILD_MEASURE_PARENTS = 3;
   public static final int CUBE_UPDATES_TIDY_TEMP_TABLES = 4;
   public static final int MAX_CUBE_UPDATES_STEP = 4;
   private static final String[] RECREATE_EXPORT_VIEWS_NARRATIVE = new String[]{"create export views"};
   public static final int RECREATE_VIEWS_ACTION = 0;
   public static final int MAX_RECREATE_VIEWS_STEP = 0;
   private static final String[] CUBE_REBUILD_NARRATIVE = new String[]{"check temp tables", "create temp tables", "rebuild parents", "tidy temp tables"};
   public static final int CUBE_REBUILD_CHECK_TEMP_TABLES = 0;
   public static final int CUBE_REBUILD_CREATE_TEMP_TABLES = 1;
   public static final int CUBE_REBUILD_MAIN = 2;
   public static final int CUBE_REBUILD_TIDY_TEMP_TABLES = 3;
   public static final int MAX_CUBE_REBUILD_STEP = 3;
   private static final String[] CUBE_IMPORT_NARRATIVE = new String[]{"check temp objects", "create temp objects", "import", "tidy temp objects"};
   public static final int CUBE_IMPORT_CHECK = 0;
   public static final int CUBE_IMPORT_CREATE_TEMP_TABLES = 1;
   public static final int CUBE_IMPORT = 2;
   public static final int CUBE_IMPORT_TERM = 3;
   public static final int MAX_CUBE_IMPORT_STEP = 3;
   private int mCheckpointNumber;
   private int mPhaseNumber;
   private int mCubeIndex;
   private int mMaxCube;
   private int mStepNumber;
   private int mMaxStep;
   private List<DimensionPK> mDimensionPKs = new ArrayList();
   private List<FinanceCubePK> mRebuildCubes = new ArrayList();
   private List<FinanceCubePK> mImportCubes = new ArrayList();
   private List<FinanceCubePK> mRecreateViews = new ArrayList();
   private List<String> mImportSourceSystems = new ArrayList();
   private List<CalendarLeafLevelUpdate> mCalendarLeafLevelUpdates = new ArrayList();
   private List<FinanceCubeUpdate> mFinanceCubeUpdates = new ArrayList();
   private List<FormulaUpdate> mFormulaUpdates = new ArrayList();


   public ChangeManagementCheckPoint() {
      this.setPhaseNumber(0);
      this.setCubeIndex(-1);
      this.setMaxCube(0);
      this.setStepNumber(0);
      this.setMaxStep(0);
   }

   public List toDisplay() {
      String phaseName = null;
      switch(this.getPhaseNumber()) {
      case 0:
         phaseName = "CM dimension metadata";
         break;
      case 1:
         phaseName = "CM rebuild runtime structures";
         break;
      case 2:
         phaseName = "CM calendar level update number:" + this.getStepNumber() + " " + this.queryCalendarLeafLevelUpdateFinanceCube();
         break;
      case 3:
         phaseName = "CM formula updates";
         break;
      case 4:
         phaseName = "CM cube dataTypes - " + this.getCubeUpdatesStepNarrative();
         break;
      case 5:
         phaseName = "CM rebuild - " + this.getCubeRebuildStepNarrative();
         break;
      case 6:
         phaseName = "CM value import - " + this.getCubeImportStepNarrative();
         break;
      case 7:
         phaseName = "CM value import - " + this.getExportViewsStepNarrative();
         break;
      case 8:
         phaseName = "CM terminate";
      }

      ArrayList l = new ArrayList();
      l.add(phaseName);
      return l;
   }

   public int setCheckpointNumberUp() {
      ++this.mCheckpointNumber;
      return this.mCheckpointNumber;
   }

   public void setCheckpointNumber(int checkpointNumber) {
      this.mCheckpointNumber = checkpointNumber;
   }

   public int getCheckpointNumber() {
      return this.mCheckpointNumber;
   }

   public int getPhaseNumber() {
      return this.mPhaseNumber;
   }

   public void setPhaseNumber(int phaseNumber) {
      this.mPhaseNumber = phaseNumber;
   }

   public int getCubeIndex() {
      return this.mCubeIndex;
   }

   public void setCubeIndex(int cubeIndex) {
      this.mCubeIndex = cubeIndex;
   }

   private int getMaxCube() {
      return this.mMaxCube;
   }

   public void setMaxCube(int numCubes) {
      this.mMaxCube = numCubes;
   }

   public int getStepNumber() {
      return this.mStepNumber;
   }

   public int getMaxStep() {
      return this.mMaxStep;
   }

   public void setMaxStep(int maxStep) {
      this.mMaxStep = maxStep;
   }

   private void setNextPhase() {
      for(int i = this.getPhaseNumber() + 1; i < 9; ++i) {
         this.setPhaseNumber(i);
         this.setCubeIndex(0);
         this.setStepNumber(0);
         switch(i) {
         case 1:
            if(!this.getDimensionPKs().isEmpty()) {
               this.setMaxCube(0);
               this.setMaxStep(0);
               return;
            }
            break;
         case 2:
            if(!this.mCalendarLeafLevelUpdates.isEmpty()) {
               this.setMaxCube(this.mRebuildCubes.size());
               this.setMaxStep(this.mCalendarLeafLevelUpdates.size() - 1);
               this.maybeOverrideNextStep();
               if(this.getStepNumber() < this.getMaxStep() + 1) {
                  return;
               }
            }
            break;
         case 3:
            if(!this.mFormulaUpdates.isEmpty()) {
               this.setMaxCube(0);
               this.setMaxStep(this.mFormulaUpdates.size() - 1);
               this.maybeOverrideNextStep();
               if(this.getStepNumber() < this.getMaxStep() + 1) {
                  return;
               }
            }
            break;
         case 4:
            if(!this.getFinanceCubeUpdates().isEmpty()) {
               this.setMaxCube(this.getFinanceCubeUpdates().size());
               this.setMaxStep(4);
               this.maybeOverrideNextStep();
               if(this.getStepNumber() < this.getMaxStep() + 1) {
                  return;
               }
            }
            break;
         case 5:
            if(!this.mRebuildCubes.isEmpty()) {
               this.setMaxCube(this.mRebuildCubes.size());
               this.setMaxStep(3);
               this.maybeOverrideNextStep();
               if(this.getStepNumber() < this.getMaxStep() + 1) {
                  return;
               }
            }
            break;
         case 6:
            if(!this.mImportCubes.isEmpty()) {
               this.setMaxCube(this.mImportCubes.size());
               this.setMaxStep(3);
               this.maybeOverrideNextStep();
               if(this.getStepNumber() < this.getMaxStep() + 1) {
                  return;
               }
            }
            break;
         case 7:
            if(!this.mRecreateViews.isEmpty()) {
               this.setMaxCube(this.mRecreateViews.size());
               this.setMaxStep(0);
               this.maybeOverrideNextStep();
               if(this.getStepNumber() < this.getMaxStep() + 1) {
                  return;
               }
            }
            break;
         case 8:
            return;
         }
      }

      throw new IllegalStateException("unexpected phase number: " + this.getPhaseNumber());
   }

   public void setStepNumber(int stepNumber) {
      this.mStepNumber = stepNumber;
   }

   public void nextPhaseOrStep() {
      this.setStepNumber(this.getStepNumber() + 1);
      if(this.getStepNumber() < this.getMaxStep() + 1) {
         this.maybeOverrideNextStep();
         if(this.getStepNumber() < this.getMaxStep() + 1) {
            return;
         }
      }

      this.setCubeIndex(this.getCubeIndex() + 1);
      this.setStepNumber(0);
      if(this.getCubeIndex() < this.getMaxCube()) {
         this.maybeOverrideNextStep();
         if(this.getStepNumber() < this.getMaxStep() + 1) {
            return;
         }
      }

      this.setNextPhase();
   }

   private void maybeOverrideNextStep() {
      if(this.getPhaseNumber() == 4) {
         if(this.getStepNumber() == 0) {
            if(((FinanceCubeUpdate)this.getFinanceCubeUpdates().get(this.getCubeIndex())).hasDeletes()) {
               return;
            }

            this.setStepNumber(1);
         }

         if(this.getStepNumber() >= 1 && this.getStepNumber() <= 4) {
            FinanceCubeUpdate fcu = (FinanceCubeUpdate)this.mFinanceCubeUpdates.get(this.getCubeIndex());
            if(((FinanceCubeUpdate)this.getFinanceCubeUpdates().get(this.getCubeIndex())).hasRollupRuleChanges() && !this.isFinanceCubeRebuildPending(fcu.getFinanceCubeRef().getFinanceCubePK())) {
               return;
            }
         }

         this.setStepNumber(5);
      }

   }

   public void addDimensionPK(DimensionPK dimPK) {
      if(!this.mDimensionPKs.contains(dimPK)) {
         this.mDimensionPKs.add(dimPK);
      }

   }

   public List getDimensionPKs() {
      return this.mDimensionPKs;
   }

   public List<FinanceCubePK> getImportCubes() {
      return this.mRebuildCubes;
   }

   public List<FinanceCubePK> getRebuildCubes() {
      return this.mRebuildCubes;
   }

   public FinanceCubePK queryFinanceCubeToRebuild() {
      return this.getCubeIndex() < this.mRebuildCubes.size()?(FinanceCubePK)this.mRebuildCubes.get(this.getCubeIndex()):null;
   }

   public boolean isFinanceCubeRebuildPending(FinanceCubePK cubePk) {
      return this.mRebuildCubes.contains(cubePk);
   }

   public void incrementFinanceCubeRebuildCount() {
      this.setCubeIndex(this.getCubeIndex() + 1);
   }

   public void registerFinanceCubeForRebuild(FinanceCubePK financeCube) {
      if(!this.mRebuildCubes.contains(financeCube)) {
         this.mRebuildCubes.add(financeCube);
      }

   }

   public FinanceCubeUpdate queryFinanceCubeToUpdate() {
      return this.getCubeIndex() < this.mFinanceCubeUpdates.size()?(FinanceCubeUpdate)this.mFinanceCubeUpdates.get(this.getCubeIndex()):null;
   }

   public boolean isFinanceCubeRegisteredForRebuild(FinanceCubePK cubePK) {
      return this.mRebuildCubes.contains(cubePK);
   }

   public String getCubeRebuildStepNarrative() {
      return CUBE_REBUILD_NARRATIVE[this.getStepNumber()] + "  " + this.queryFinanceCubeToRebuild();
   }

   public String getCubeImportStepNarrative() {
      return CUBE_IMPORT_NARRATIVE[this.getStepNumber()] + " " + this.queryFinanceCubeToImport();
   }

   public String getCubeUpdatesStepNarrative() {
      return CUBE_UPDATES_NARRATIVE[this.getStepNumber()] + " " + this.queryFinanceCubeToUpdate().getFinanceCubeRef().getFinanceCubePK();
   }

   public String getExportViewsStepNarrative() {
      return RECREATE_EXPORT_VIEWS_NARRATIVE[this.getStepNumber()] + " " + this.queryRecreateExportView();
   }

   public void addExternalImport(FinanceCubePK financeCubePK, String sourceSystem) {
      if(!this.mImportCubes.contains(financeCubePK)) {
         this.mImportCubes.add(financeCubePK);
         this.mImportSourceSystems.add(sourceSystem);
      }

   }

   public FinanceCubePK queryFinanceCubeToImport() {
      return this.getCubeIndex() > -1 && this.getCubeIndex() < this.mImportCubes.size()?(FinanceCubePK)this.mImportCubes.get(this.getCubeIndex()):null;
   }

   public void addRecreateView(FinanceCubePK financeCubePK) {
      if(!this.mRecreateViews.contains(financeCubePK)) {
         this.mRecreateViews.add(financeCubePK);
      }

   }

   public FinanceCubePK queryRecreateExportView() {
      return this.getCubeIndex() > -1 && this.getCubeIndex() < this.mRecreateViews.size()?(FinanceCubePK)this.mRecreateViews.get(this.getCubeIndex()):null;
   }

   public String queryExternalImportSourceSystem() {
      return this.getCubeIndex() > -1 && this.getCubeIndex() < this.mImportSourceSystems.size()?(String)this.mImportSourceSystems.get(this.getCubeIndex()):null;
   }

   public void registerCalendarLeafLevelUpdate(CalendarLeafLevelUpdate cllu) {
      if(this.mCalendarLeafLevelUpdates == null) {
         this.mCalendarLeafLevelUpdates = new ArrayList();
      }

      this.mCalendarLeafLevelUpdates.add(cllu);
   }

   public void registerFormulaUpdate(FormulaUpdate formulaUpdate) {
      if(this.mFormulaUpdates == null) {
         this.mFormulaUpdates = new ArrayList();
      }

      this.mFormulaUpdates.add(formulaUpdate);
   }

   public CalendarLeafLevelUpdate queryCalendarLeafLevelUpdate() {
      return this.mCalendarLeafLevelUpdates != null && this.getStepNumber() < this.mCalendarLeafLevelUpdates.size()?(CalendarLeafLevelUpdate)this.mCalendarLeafLevelUpdates.get(this.getStepNumber()):null;
   }

   public FinanceCubePK queryCalendarLeafLevelUpdateFinanceCube() {
      return this.getCubeIndex() < this.mRebuildCubes.size()?(FinanceCubePK)this.mRebuildCubes.get(this.getCubeIndex()):null;
   }

   public List<CalendarLeafLevelUpdate> getCalendarLeafLevelUpdates() {
      return this.mCalendarLeafLevelUpdates;
   }

   public List<FinanceCubeUpdate> getFinanceCubeUpdates() {
      return this.mFinanceCubeUpdates;
   }

   public FormulaUpdate queryFormulaUpdate() {
      return this.mFormulaUpdates != null && this.getStepNumber() < this.mFormulaUpdates.size()?(FormulaUpdate)this.mFormulaUpdates.get(this.getStepNumber()):null;
   }

   public List<FormulaUpdate> getFormulaUpdates() {
      return this.mFormulaUpdates;
   }

   public List<CubeFormulaRef> getAllCubeFormulaToUndeployForFinanceCube(FinanceCubeRef financeCubeRef) {
      ArrayList result = new ArrayList();
      if(this.mFormulaUpdates != null && this.getStepNumber() < this.mFormulaUpdates.size()) {
         for(int i = this.getStepNumber(); i < this.mFormulaUpdates.size(); ++i) {
            FormulaUpdate formulaUpdate = (FormulaUpdate)this.mFormulaUpdates.get(i);
            if(formulaUpdate.getUpdateType() == FormulaUpdateType.UNDEPLOY && formulaUpdate.getFinanceCubeRef().equals(financeCubeRef)) {
               result.add(formulaUpdate.getFormulaRef());
            }
         }
      }

      return result;
   }

   public static class FormulaUpdate implements Serializable {

	   private FormulaUpdateType mUpdateType;
	   private FinanceCubeRef mFinanceCubeRef;
	   private CubeFormulaRef mFormulaRef;


	   public FormulaUpdate(FormulaUpdateType updateType, FinanceCubeRef financeCubeRef, CubeFormulaRef formulaRef) {
	      this.mUpdateType = updateType;
	      this.mFinanceCubeRef = financeCubeRef;
	      this.mFormulaRef = formulaRef;
	   }

	   public FormulaUpdateType getUpdateType() {
	      return this.mUpdateType;
	   }

	   public FinanceCubeRef getFinanceCubeRef() {
	      return this.mFinanceCubeRef;
	   }

	   public CubeFormulaRef getFormulaRef() {
	      return this.mFormulaRef;
	   }
	}
   
   public static class FinanceCubeDataTypeUpdate implements Serializable {

	   public static final int DATA_TYPE_ADDED = 1;
	   public static final int DATA_TYPE_UPDATED = 2;
	   public static final int DATA_TYPE_REMOVED = 3;
	   private DataTypeRefImpl mDataTypeRef;
	   private int mAction;
	   private boolean[] mRollUpRules;


	   public FinanceCubeDataTypeUpdate(DataTypeRefImpl dataTypeRef, int action, boolean[] rollUpRules) {
	      this.mDataTypeRef = dataTypeRef;
	      this.mAction = action;
	      this.mRollUpRules = rollUpRules;
	   }

	   public DataTypeRefImpl getDataTypeRef() {
	      return this.mDataTypeRef;
	   }

	   public int getAction() {
	      return this.mAction;
	   }

	   public boolean[] getRollUpRules() {
	      return this.mRollUpRules;
	   }
	}
   
   public static class FinanceCubeUpdate implements Serializable {

	   private FinanceCubeRefImpl mFinanceCubeRef;
	   private List<FinanceCubeDataTypeUpdate> mDataTypeUpdateActions;


	   public FinanceCubeUpdate(FinanceCubeRefImpl financeCubeRef) {
	      this.mFinanceCubeRef = financeCubeRef;
	      this.mDataTypeUpdateActions = new ArrayList();
	   }

	   public FinanceCubeRefImpl getFinanceCubeRef() {
	      return this.mFinanceCubeRef;
	   }

	   public List<FinanceCubeDataTypeUpdate> getDataTypeUpdateActions() {
	      return this.mDataTypeUpdateActions;
	   }

	   public boolean hasUpdateAction(int type) {
	      Iterator i$ = this.getDataTypeUpdateActions().iterator();

	      FinanceCubeDataTypeUpdate dtu;
	      do {
	         if(!i$.hasNext()) {
	            return false;
	         }

	         dtu = (FinanceCubeDataTypeUpdate)i$.next();
	      } while(dtu.getAction() != type);

	      return true;
	   }

	   public boolean hasAdds() {
	      return this.hasUpdateAction(1);
	   }

	   public boolean hasRollupRuleChanges() {
	      return this.hasUpdateAction(2);
	   }

	   public boolean hasDeletes() {
	      return this.hasUpdateAction(3);
	   }
	}
}
