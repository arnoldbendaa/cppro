// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:07:01
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.base.async.formula;

import com.cedar.cp.api.task.TaskCheckpoint;
import com.cedar.cp.dto.cubeformula.FormulaRebuildTaskRequest;
import com.cedar.cp.ejb.base.async.AbstractTask;
import com.cedar.cp.ejb.impl.model.CubeUpdateDAO;
import java.util.List;
import javax.naming.InitialContext;

public class FormulaRebuildTask extends AbstractTask {

   private InitialContext mInitialContext;


   public int getReportType() {
      return 7;
   }

   public String getEntityName() {
      return "CubeFormulaRebuildTask";
   }

   public void runUnitOfWork(InitialContext initialContext) throws Exception {
      this.mInitialContext = initialContext;
      FormulaRebuildTaskRequest req = (FormulaRebuildTaskRequest)this.getRequest();
      CubeUpdateDAO cubeUpdateDAO = new CubeUpdateDAO();
      int financeCubeId = req.getFinanceCubeRef().getFinanceCubePK().getFinanceCubeId();
      List formula = req.getFormula();
      String deltaTableName = this.getDeltaTableName(financeCubeId);
      String workTableName = this.getWorkTableName(financeCubeId);
      cubeUpdateDAO.rebuildFormula(financeCubeId, formula, deltaTableName, workTableName, false);
      this.setCheckpoint((TaskCheckpoint)null);
   }

   public String getDeltaTableName(int financeCubeId) {
      return "TX1_" + financeCubeId;
   }

   private String getWorkTableName(int financeCubeId) {
      return "TX2_" + financeCubeId;
   }
}
