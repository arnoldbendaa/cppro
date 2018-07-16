// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:08:10
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.impl.cubeformula;

import com.cedar.cp.api.base.EntityList;
import com.cedar.cp.api.base.EntityRef;
import com.cedar.cp.api.cubeformula.CubeFormulaRef;
import com.cedar.cp.api.model.ModelRef;
import com.cedar.cp.dto.model.FinanceCubeRefImpl;
import com.cedar.cp.ejb.base.cube.formula.FormulaDAO;
import com.cedar.cp.ejb.impl.cm.event.InsertedHierarchyElementFeedEvent;
import com.cedar.cp.ejb.impl.cm.event.MovedHierarchyElementEvent;
import com.cedar.cp.ejb.impl.cm.event.MovedHierarchyElementFeedEvent;
import com.cedar.cp.ejb.impl.cm.event.RemovedHierarchyElementEvent;
import com.cedar.cp.ejb.impl.cm.event.RemovedHierarchyElementFeedEvent;
import com.cedar.cp.ejb.impl.cm.frmwrk.CMMetaDataController;
import com.cedar.cp.ejb.impl.cm.frmwrk.CMUpdateAdapter;
import com.cedar.cp.ejb.impl.cubeformula.FormulaDeploymentEntryDAO;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class CubeFormulaCMModule extends CMUpdateAdapter {

   private boolean mMovedHierarchyElements;
   private boolean mRemovedHierarchyElements;
   private boolean mInsertedHierarcyElementFeeds;
   private boolean mMovedHierarchyElementFeeds;
   private boolean mRemovedHierarchyElementFeeds;
   private Set<CubeFormulaRef> mFormulaToUndeploy = new HashSet();
   private Set<EntityRef> mFormulaToRebuild = new HashSet();
   private CMMetaDataController mController;


   public CubeFormulaCMModule(CMMetaDataController controller) {
      this.mController = controller;
   }

   public void initProcessing(ModelRef modelRef) {
      this.mMovedHierarchyElements = this.mRemovedHierarchyElements = this.mInsertedHierarcyElementFeeds = this.mMovedHierarchyElementFeeds = this.mRemovedHierarchyElementFeeds = false;
   }

   public void terminateProcessing(ModelRef modelRef) {
      if(this.mMovedHierarchyElements || this.mRemovedHierarchyElements || this.mInsertedHierarcyElementFeeds || this.mMovedHierarchyElementFeeds || this.mRemovedHierarchyElementFeeds) {
         this.updateModelCubeFormula(modelRef);
      }

   }

   public void processEvent(MovedHierarchyElementEvent event) {
      this.mMovedHierarchyElements = true;
   }

   public void processEvent(RemovedHierarchyElementEvent event) {
      this.mRemovedHierarchyElements = true;
   }

   public void processEvent(InsertedHierarchyElementFeedEvent event) {
      this.mInsertedHierarcyElementFeeds = true;
   }

   public void processEvent(MovedHierarchyElementFeedEvent event) {
      this.mMovedHierarchyElementFeeds = true;
   }

   public void processEvent(RemovedHierarchyElementFeedEvent event) {
      this.mRemovedHierarchyElementFeeds = true;
   }

   private void updateModelCubeFormula(ModelRef modelRef) {
      EntityList finaneCubes = this.mController.getFinanceCubes();

      for(int fcIndex = 0; fcIndex < finaneCubes.getNumRows(); ++fcIndex) {
         FinanceCubeRefImpl financeCubeRef = (FinanceCubeRefImpl)finaneCubes.getValueAt(fcIndex, "FinanceCube");
         Boolean cubeFormulaEnabled = (Boolean)finaneCubes.getValueAt(fcIndex, "CubeFormulaEnabled");
         if(cubeFormulaEnabled.booleanValue()) {
            int financeCubeId = financeCubeRef.getFinanceCubePK().getFinanceCubeId();
            EntityList invalidDeployments = (new FormulaDeploymentEntryDAO()).queryInvalidFormulaDeployments(financeCubeId);

            int i$;
            for(i$ = 0; i$ < invalidDeployments.getNumRows(); ++i$) {
               this.mFormulaToUndeploy.add((CubeFormulaRef)invalidDeployments.getValueAt(i$, "cubeFormula"));
            }

            invalidDeployments = (new FormulaDAO()).queryInvalidRuntimeDeployments(financeCubeId);

            for(i$ = 0; i$ < invalidDeployments.getNumRows(); ++i$) {
               this.mFormulaToUndeploy.add((CubeFormulaRef)invalidDeployments.getValueAt(i$, "cubeFormula"));
            }

            invalidDeployments = (new FormulaDAO()).queryInvalidRuntimeCellReferences(financeCubeId, this.mController.getDimensionCount());

            for(i$ = 0; i$ < invalidDeployments.getNumRows(); ++i$) {
               this.mFormulaToUndeploy.add((CubeFormulaRef)invalidDeployments.getValueAt(i$, "cubeFormula"));
            }

            invalidDeployments = (new FormulaDAO()).queryInvalidRuntimeCellRangeReferences(financeCubeId, this.mController.getDimensionCount());

            for(i$ = 0; i$ < invalidDeployments.getNumRows(); ++i$) {
               this.mFormulaToUndeploy.add((CubeFormulaRef)invalidDeployments.getValueAt(i$, "cubeFormula"));
            }

            (new FormulaDAO()).updateRuntimeDeploymentPositions(financeCubeId);
            (new FormulaDAO()).updateRuntimeRangeReferencesPositions(financeCubeId, this.mController.getDimensionCount());
            (new FormulaDAO()).updateRuntimeCellReferencePositions(financeCubeId, this.mController.getDimensionCount());
            Iterator var10 = this.mFormulaToUndeploy.iterator();

            while(var10.hasNext()) {
               CubeFormulaRef cubeFormulaRef = (CubeFormulaRef)var10.next();
               this.mController.registerFormulaForUndeploy(financeCubeRef, cubeFormulaRef);
            }
         }
      }

   }
}
