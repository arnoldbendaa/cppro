// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:07:01
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.base.async.cubeadmin;

import com.cedar.cp.api.base.CPException;
import com.cedar.cp.api.base.EntityList;
import com.cedar.cp.api.task.TaskCheckpoint;
import com.cedar.cp.dto.model.GlobalCubeAdminTaskRequest;
import com.cedar.cp.ejb.base.async.AbstractTask;
import com.cedar.cp.ejb.base.common.util.SystemPropertyHelper;
import com.cedar.cp.ejb.base.cube.formula.FormulaCompiler;
import com.cedar.cp.ejb.base.cube.formula.FormulaDAO;
import com.cedar.cp.ejb.base.cube.formula.tablemeta.MetaTableManager;
import com.cedar.cp.ejb.impl.cubeformula.CubeFormulaDAO;
import com.cedar.cp.ejb.impl.cubeformula.CubeFormulaPackageDAO;
import com.cedar.cp.ejb.impl.datatype.DataTypeDAO;
import com.cedar.cp.ejb.impl.model.FinanceCubeDAO;
import com.cedar.cp.ejb.impl.model.FinanceCubeDDLDAO;
import com.cedar.cp.ejb.impl.model.ModelDAO;
import com.cedar.cp.ejb.impl.systemproperty.SystemPropertyDAO;
import javax.naming.InitialContext;

public class GlobalCubeAdminTask extends AbstractTask {

   static final String[] mNames = new String[]{"PFT", "DFT", "AFT", "CFT", "SFT", "UFT", "RTCUBE", "FORMULA", "TX1_", "TX2_", "VIEWS"};
   static final int sCUBE_FORMULA_INDEX = 7;
   private String mSchemaName;


   public void runUnitOfWork(InitialContext initialContext) throws Exception {
      if(this.getCheckpoint() == null) {
         this.setCheckpoint(new GlobalCubeAdminTask$MyCheckpoint(this.getRequest().getAction()));
         this.log("financeCubeId=" + this.getRequest().getFinanceCubeId() + " visId=" + this.getRequest().getVisId() + " description=" + this.getRequest().getDescription() + (this.getRequest().getAction() == 0?" - create":(this.getRequest().getAction() == 1?" - delete":(this.getRequest().getAction() == 2?" - recreate views":""))));
         System.out.println( "financeCubeId=" + this.getRequest().getFinanceCubeId() + " visId=" + this.getRequest().getVisId() + " description=" + this.getRequest().getDescription() + (this.getRequest().getAction() == 0?" - create":(this.getRequest().getAction() == 1?" - delete":(this.getRequest().getAction() == 2?" - recreate views":""))) );
      }

      FinanceCubeDDLDAO ddldao = new FinanceCubeDDLDAO(Integer.valueOf(this.getTaskId()), this.getRequest().getFinanceCubeId(), this.getRequest().getNumDims());
      GlobalCubeAdminTask$MyCheckpoint checkpoint = (GlobalCubeAdminTask$MyCheckpoint)this.getCheckpoint();
      switch(this.getRequest().getAction()) {
      case 0:
         if(checkpoint.getCheckpointNumber() == 7) {
            if(this.isCubeFormulaEnabledInFinanceCube()) {
               ddldao.maintainCubeObjectGlobal(mNames[this.getCheckpoint().getCheckpointNumber()], " ");
            }
         } else {
            ddldao.maintainCubeObjectGlobal(mNames[this.getCheckpoint().getCheckpointNumber()], " ");
         }
         break;
      case 1:
         if(checkpoint.getCheckpointNumber() == 7) {
            (new FormulaCompiler(new FormulaDAO(), new CubeFormulaDAO(), new CubeFormulaPackageDAO(), new ModelDAO(), new FinanceCubeDAO(), new DataTypeDAO(), new SystemPropertyDAO(), new MetaTableManager())).dropAllPackages(this.getRequest().getFinanceCubeId());
            ddldao.maintainCubeObjectGlobal(mNames[this.getCheckpoint().getCheckpointNumber()], "Y");
         } else {
            ddldao.maintainCubeObjectGlobal(mNames[this.getCheckpoint().getCheckpointNumber()], "Y");
         }
         break;
      case 2:
         ddldao.maintainCubeObjectGlobal("VIEWS", " ");
         this.setCheckpoint((TaskCheckpoint)null);
         return;
      case 3:
         ddldao.maintainCubeObjectGlobal(mNames[7], " ");
         (new FormulaCompiler(new FormulaDAO(), new CubeFormulaDAO(), new CubeFormulaPackageDAO(), new ModelDAO(), new FinanceCubeDAO(), new DataTypeDAO(), new SystemPropertyDAO(), new MetaTableManager())).compileAll(this.getRequest().getFinanceCubeId());
         this.setCheckpoint((TaskCheckpoint)null);
         return;
      case 4:
         if(checkpoint.getCheckpointNumber() == 0) {
            (new FormulaCompiler(new FormulaDAO(), new CubeFormulaDAO(), new CubeFormulaPackageDAO(), new ModelDAO(), new FinanceCubeDAO(), new DataTypeDAO(), new SystemPropertyDAO(), new MetaTableManager())).removeAll(this.getRequest().getFinanceCubeId(), true);
         } else if(checkpoint.getCheckpointNumber() == 1) {
            ddldao.maintainCubeObjectGlobal(mNames[7], "Y");
            this.setCheckpoint((TaskCheckpoint)null);
         }

         return;
      default:
         throw new CPException("Unexpected request action code:" + this.getRequest().getAction());
      }

      if(this.getCheckpoint().getCheckpointNumber() >= mNames.length - 1) {
         this.setCheckpoint((TaskCheckpoint)null);
      }

   }

   private boolean isCubeFormulaEnabledInFinanceCube() {
      EntityList fcDetails = this.getCPConnection().getFinanceCubesProcess().getFinanceCubeDetails(this.getRequest().getFinanceCubeId());
      if(fcDetails.getNumRows() != 1) {
         throw new IllegalStateException("Failed to query finance cube details for finanec cube id:" + this.getRequest().getFinanceCubeId());
      } else {
         return ((Boolean)fcDetails.getValueAt(0, "CubeFormulaEnabled")).booleanValue();
      }
   }

   public String getEntityName() {
      return "GlobalCubeAdminTask";
   }

   public GlobalCubeAdminTaskRequest getRequest() {
      return (GlobalCubeAdminTaskRequest)super.getRequest();
   }

   public int getReportType() {
      return 0;
   }

   public String getSchemaName() {
      if(this.mSchemaName == null) {
         this.mSchemaName = SystemPropertyHelper.queryStringSystemProperty(this.getConnection(), "SYS: CPSCHEMA", "<noSchemaProperty!>");
      }

      return this.mSchemaName;
   }

}
