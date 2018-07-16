// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:07:00
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.base.async;

import com.cedar.cp.api.base.CPException;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.task.TaskCheckpoint;
import com.cedar.cp.dto.model.FinanceCubeCK;
import com.cedar.cp.dto.model.FinanceCubePK;
import com.cedar.cp.dto.model.ModelPK;
import com.cedar.cp.dto.report.ReportPK;
import com.cedar.cp.dto.report.ReportUpdateTaskRequest;
import com.cedar.cp.dto.user.UserPK;
import com.cedar.cp.ejb.base.async.AbstractTask;
import com.cedar.cp.ejb.base.async.ReportUpdateTask$ReportUpdateCheckpoint;
import com.cedar.cp.ejb.impl.model.ModelDAO;
import com.cedar.cp.ejb.impl.model.ModelEVO;
import com.cedar.cp.ejb.impl.model.act.BudgetActivityEVO;
import com.cedar.cp.ejb.impl.report.ReportDAO;
import com.cedar.cp.ejb.impl.user.UserDAO;
import java.sql.CallableStatement;
import java.sql.SQLException;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class ReportUpdateTask extends AbstractTask {

   private static final int NUMBER_OF_STEPS = 3;


   public int getReportType() {
      return 9;
   }

   public String getEntityName() {
      return "Report Update";
   }

   public void runUnitOfWork(InitialContext initialContext) throws Exception {
      if(this.getCheckpoint() == null) {
         this.initialiseTask();
      }

      this.performStep();
      if(this.getCheckpointNumber() >= 2) {
         this.finaliseTask();
      }

   }

   private void initialiseTask() {
      this.setCheckpoint(new ReportUpdateTask$ReportUpdateCheckpoint());
   }

   private void performStep() throws ValidationException {
      this.runPLSQLStep(this.getCheckpointNumber());

      try {
         this.sendEntityEventMessage(new InitialContext(), "Report", new ReportPK(this.getMyRequest().getReportId()), 3);
      } catch (NamingException var2) {
         var2.printStackTrace();
         this.log("Warning: failed to send entity update event message for report:" + var2.getMessage());
      }

      if(this.getCheckpointNumber() == 1) {
         this.writeBudgetActvityEntry();
      }

   }

   private void runPLSQLStep(int step) {
      CallableStatement stmt = null;

      try {
         this.mLog.info("runPLSQLStep", " taskId: " + this.getTaskId() + ", reportId:" + this.getMyRequest().getReportId());
         stmt = this.getConnection().prepareCall("{ call report_update.runStep(?,?,?,?) }");
         byte e = 0;
         int var9 = e + 1;
         stmt.setInt(var9, step);
         ++var9;
         stmt.setInt(var9, this.getTaskId());
         ++var9;
         stmt.setInt(var9, this.getMyRequest().getReportId());
         ++var9;
         stmt.setInt(var9, this.getMyRequest().getFinanceCubeId());
         stmt.execute();
      } catch (SQLException var7) {
         var7.printStackTrace();
         throw new RuntimeException(var7);
      } finally {
         this.closeStatement(stmt);
         this.closeConnection();
      }

   }

   private void writeBudgetActvityEntry() throws ValidationException {
      ModelDAO modelDAO = new ModelDAO();
      FinanceCubeCK fcCK = modelDAO.getFinanceCubeCK(new FinanceCubePK(this.getMyRequest().getFinanceCubeId()));
      int modelId = fcCK.getModelPK().getModelId();
      String userId = (new UserDAO()).getDetails(new UserPK(this.getUserId()), "").getName();
      BudgetActivityEVO baEVO = (new ReportDAO()).createBudgetActvity(modelId, this.getMyRequest().getFinanceCubeId(), this.getMyRequest().getReportId(), userId);
      ModelEVO modelEVO = modelDAO.getDetails(new ModelPK(modelId), "");
      modelEVO.addBudgetActivitiesItem(baEVO);
      modelDAO.setDetails(modelEVO);

      try {
         modelDAO.store();
      } catch (Exception var8) {
         throw new CPException("Failed to store model/budget actvity:" + var8.getMessage(), var8);
      }
   }

   private void finaliseTask() {
      this.setCheckpoint((TaskCheckpoint)null);
   }

   private ReportUpdateTaskRequest getMyRequest() {
      return (ReportUpdateTaskRequest)this.getRequest();
   }
}
