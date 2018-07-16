// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:08:08
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.impl.cm;

import com.cedar.cp.api.model.BudgetCycleRef;
import com.cedar.cp.api.model.ModelRef;
import com.cedar.cp.dto.model.BudgetCycleCK;
import com.cedar.cp.dto.model.BudgetCyclesForModelELO;
import com.cedar.cp.dto.model.ModelRefImpl;
import com.cedar.cp.ejb.impl.cm.frmwrk.CMUpdateAdapter;
import com.cedar.cp.ejb.impl.dimension.StructureElementDAO;
import com.cedar.cp.ejb.impl.model.BudgetCycleDAO;
import com.cedar.cp.util.Log;
import com.cedar.cp.util.Timer;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;

public class BudgetCycleChanges extends CMUpdateAdapter {

   protected transient Log mLog = new Log(this.getClass());


   public void terminateProcessing(ModelRef modelRef) {
      this.fixBudgetCycles(((ModelRefImpl)modelRef).getModelPK().getModelId());
   }

   private void fixBudgetCycles(int modelId) {
      Timer timer = this.mLog.isDebugEnabled()?new Timer(this.mLog):null;
      StructureElementDAO strucElemDao = new StructureElementDAO();
      BudgetCycleDAO bcDao = new BudgetCycleDAO();
      int structureDepth = strucElemDao.getDepthOfBudgetHierarchy(modelId);
      BudgetCyclesForModelELO budgetCycles = bcDao.getBudgetCyclesForModel(modelId);
      int size = budgetCycles.getNumRows();

      for(int i = 0; i < size; ++i) {
         BudgetCycleRef bcRef = (BudgetCycleRef)budgetCycles.getValueAt(i, "BudgetCycle");
         int bcId = ((BudgetCycleCK)bcRef.getPrimaryKey()).getBudgetCyclePK().getBudgetCycleId();
         int bcDepth = bcDao.getDepthOfLevelDates(bcId);
         if(bcDepth != structureDepth) {
            this.mLog.info("fixBudgetCycles", bcRef + " needs fixing ");
            Timestamp endDate = (Timestamp)budgetCycles.getValueAt(i, "PlannedEndDate");
            this.resetLevels(bcId, structureDepth, endDate, bcDao);
         } else {
            this.mLog.info("fixBudgetCycles", bcRef + " is Ok ");
         }
      }

      if(timer != null) {
         timer.logDebug("fixBudgetCycles", "complete");
      }

   }

   private void resetLevels(int bcId, int depth, Timestamp plannedEndDate, BudgetCycleDAO dao) {
      ++depth;
      int maxDepth = depth;
      long today = System.currentTimeMillis();
      long planned = plannedEndDate.getTime();
      long dif = planned - today;
      long millsPerDay = 86400000L;
      Long numberOfDays = Long.valueOf(dif / millsPerDay);
      Long intervalDays = Long.valueOf(numberOfDays.longValue() / (long)depth);
      Long intervalDaysMillis = Long.valueOf(intervalDays.longValue() * millsPerDay);
      ArrayList splitDays = new ArrayList(depth);

      for(int i = 0; i < maxDepth; ++i) {
         if(i == 0) {
            splitDays.add(new Date(planned));
         } else {
            long subTime = intervalDaysMillis.longValue() * (long)i;
            splitDays.add(new Date(planned - subTime));
         }
      }

      dao.insertNewLevelDates(bcId, splitDays);
   }
}
