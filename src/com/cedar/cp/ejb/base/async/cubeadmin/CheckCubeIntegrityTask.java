// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:07:01
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.base.async.cubeadmin;

import com.cedar.cp.api.base.EntityRef;
import com.cedar.cp.api.task.TaskCheckpoint;
import com.cedar.cp.dto.model.CheckCubeIntegrityTaskRequest;
import com.cedar.cp.dto.model.FinanceCubeCK;
import com.cedar.cp.dto.model.FinanceCubePK;
import com.cedar.cp.ejb.base.async.AbstractTask;
import com.cedar.cp.ejb.base.async.cubeadmin.CheckCubeIntegrityTask$MyCheckpoint;
import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import javax.naming.InitialContext;

public class CheckCubeIntegrityTask extends AbstractTask {

   public int getReportType() {
      return 0;
   }

   public String getEntityName() {
      return "CheckCubeIntegrityTask";
   }

   public void runUnitOfWork(InitialContext initialContext) throws Exception {
      if(this.getCheckpoint() == null) {
         this.setCheckpoint(new CheckCubeIntegrityTask$MyCheckpoint());
      }

      FinanceCubePK pk = ((FinanceCubeCK)((EntityRef)this.getMyRequest().getCubeList().get(this.getCheckpointNumber())).getPrimaryKey()).getFinanceCubePK();
      int financeCubeId = pk.getFinanceCubeId();
      this.log("checking " + pk);
      this.performStep(financeCubeId);
      if(this.getCheckpointNumber() == this.getMyRequest().getCubeList().size() - 1) {
         this.setCheckpoint((TaskCheckpoint)null);
      }

   }

   private void performStep(int financeCubeId) {
      boolean exceptionLimit = true;
      CallableStatement stmt = null;
      ResultSet resultSet1 = null;
      ResultSet resultSet2 = null;

      try {
         stmt = this.getConnection().prepareCall("{ call cube_rebuild.checkIntegrity(?,?,?) }");
         stmt.setInt(1, financeCubeId);
         stmt.registerOutParameter(2, -10);
         stmt.registerOutParameter(3, -10);
         stmt.execute();
         resultSet1 = (ResultSet)stmt.getObject(2);
         resultSet2 = (ResultSet)stmt.getObject(3);
         ResultSetMetaData e = resultSet1.getMetaData();
         int numDims = (e.getColumnCount() - 3) / 3;
         int exceptionCount = 0;
         this.log(" ");

         while(resultSet1.next()) {
            ++exceptionCount;
            if(exceptionCount == 1) {
               this.log("Exception Details");
               this.log("[visIds][dataType][internalIds]value  message");
            }

            StringBuilder cellCalcVisId = new StringBuilder();
            StringBuilder internalIds = new StringBuilder();

            for(int dataType = 0; dataType < numDims; ++dataType) {
               if(cellCalcVisId.length() > 0) {
                  cellCalcVisId.append(',');
                  internalIds.append(',');
               }

               String value = resultSet1.getString("VIS_ID" + dataType);
               if(value != null) {
                  cellCalcVisId.append(value);
               }

               internalIds.append(resultSet1.getObject("DIM" + dataType).toString());
            }

            String var21 = resultSet1.getString("DATA_TYPE");
            BigDecimal var20 = resultSet1.getBigDecimal("NUMBER_VALUE");
            String message = resultSet1.getString("REASON");
            if(exceptionCount < 251) {
               this.log("[" + cellCalcVisId + "]" + "[" + var21 + "]" + "[" + internalIds + "]" + var20 + "  " + message);
            } else if(exceptionCount == 251) {
               this.log("more than 251 exceptions logged - not logging remainder");
            }
         }

         this.log(" ");
         if(exceptionCount == 0) {
            this.log("no exceptions found");
         } else {
            this.logInfo(exceptionCount + " exceptions found.");
            this.logInfo("A Cube Rebuild may be needed - please consult your COA representative.");
         }

         this.log(" ");
         exceptionCount = 0;

         while(resultSet2.next()) {
            ++exceptionCount;
            if(exceptionCount == 1) {
               this.log("Cell Calculations affected by the exceptions above:");
            }

            String var19 = resultSet2.getObject(1).toString();
            this.log(var19);
         }

         if(exceptionCount > 0) {
            this.log(" ");
            this.logInfo(exceptionCount + " Cell Calculations affected.");
            this.logInfo("These must be rebuilt after the Cube Rebuild mentioned above");
            this.log(" ");
         }
      } catch (SQLException var17) {
         var17.printStackTrace();
         throw new RuntimeException(var17);
      } finally {
         this.closeResultSet(resultSet1);
         this.closeStatement(stmt);
         this.closeConnection();
      }

   }

   private CheckCubeIntegrityTaskRequest getMyRequest() {
      return (CheckCubeIntegrityTaskRequest)this.getRequest();
   }
}
