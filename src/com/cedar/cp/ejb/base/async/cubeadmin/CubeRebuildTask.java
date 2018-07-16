// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:07:01
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.base.async.cubeadmin;

import com.cedar.cp.api.base.EntityRef;
import com.cedar.cp.api.task.TaskCheckpoint;
import com.cedar.cp.dto.model.CubeRebuildTaskRequest;
import com.cedar.cp.dto.model.FinanceCubeCK;
import com.cedar.cp.ejb.base.async.AbstractTask;
import com.cedar.cp.ejb.base.async.cubeadmin.CubeRebuildTask$CubeRebuildCheckpoint;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.naming.InitialContext;

public class CubeRebuildTask extends AbstractTask {

   public int getReportType() {
      return 0;
   }

   public String getEntityName() {
      return "CubeRebuildTask";
   }

   public void runUnitOfWork(InitialContext initialContext) throws Exception {
      if(this.getCheckpoint() == null) {
         this.setCheckpoint(new CubeRebuildTask$CubeRebuildCheckpoint());
         this.getMyCheckpoint().setRebuildList(this.getMyRequest().getRebuildList());
         this.getMyCheckpoint().setRebuildCubeIndex(0);
      } else {
         this.performStep(this.getMyCheckpoint().getRebuildCubeIndex(), this.getMyCheckpoint().getRebuildStep());
         this.getMyCheckpoint().setRebuildStep(this.getMyCheckpoint().getRebuildStep() + 1);
         if(this.getMyCheckpoint().getRebuildStep() == 4) {
            this.getMyCheckpoint().setRebuildCubeIndex(this.getMyCheckpoint().getRebuildCubeIndex() + 1);
            if(this.getMyCheckpoint().getRebuildCubeIndex() == this.getMyRequest().getRebuildList().size()) {
               this.setCheckpoint((TaskCheckpoint)null);
            }
         }
      }

   }

   private void performStep(int cubeIndex, int step) {
      CallableStatement stmt = null;
      int financeCubeId = ((FinanceCubeCK)((EntityRef)this.getMyRequest().getRebuildList().get(cubeIndex)).getPrimaryKey()).getFinanceCubePK().getFinanceCubeId();
      
      boolean globalMappedModel = isGlobalMappedModel(financeCubeId);
      try {
          if (globalMappedModel) {
              stmt = this.getConnection().prepareCall("{ call cube_rebuild.runStepGlobal(?,?,?) }");
         } else {
             stmt = this.getConnection().prepareCall("{ call cube_rebuild.runStep(?,?,?) }");
         }
         stmt.setInt(1, step);
         stmt.setInt(2, this.getTaskId());
         stmt.setInt(3, financeCubeId);
         stmt.execute();
      } catch (SQLException var8) {
         var8.printStackTrace();
         throw new RuntimeException(var8);
      } finally {
         this.closeStatement(stmt);
         this.closeConnection();
      }

   }
   
   private boolean isGlobalMappedModel(int financeCubeId) {
       PreparedStatement stmt = null;
       ResultSet resultSet = null;
       boolean returnValue;
       try {
           stmt = this.getConnection().prepareStatement("select mm.GLOBAL   from   MAPPED_MODEL mm, MAPPED_FINANCE_CUBE mfc   where   mm.MAPPED_MODEL_ID=mfc.MAPPED_MODEL_ID and mfc.FINANCE_CUBE_ID = ?");
           stmt.setInt(1, financeCubeId);
           resultSet = stmt.executeQuery();

           if (!resultSet.next()) {
               returnValue = false;
           } else {
               returnValue = resultSet.getString(1).equalsIgnoreCase("y") ? true : false;
           }
       } catch (SQLException sqle) {
           throw handleSQLException("select mm.GLOBAL   from   MAPPED_MODEL mm, MAPPED_FINANCE_CUBE mfc   where   mm.MAPPED_MODEL_ID=mfc.MAPPED_MODEL_ID and mfc.FINANCE_CUBE_ID = ?", sqle);
       } finally {
           closeResultSet(resultSet);
           closeStatement(stmt);
           closeConnection();
       }
       return returnValue;
   }
   
   private CubeRebuildTask$CubeRebuildCheckpoint getMyCheckpoint() {
      return (CubeRebuildTask$CubeRebuildCheckpoint)this.getCheckpoint();
   }

   private CubeRebuildTaskRequest getMyRequest() {
      return (CubeRebuildTaskRequest)this.getRequest();
   }
}
