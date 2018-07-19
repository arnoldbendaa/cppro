// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:06:59
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.base.async;

import com.cedar.cp.api.base.EntityList;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.task.TaskCheckpoint;
import com.cedar.cp.dto.cm.ChangeManagementTaskRequest;
import com.cedar.cp.dto.datatype.DataTypePK;
import com.cedar.cp.dto.dimension.DimensionPK;
import com.cedar.cp.dto.dimension.HierarchyPK;
import com.cedar.cp.dto.extsys.ExternalSystemPK;
import com.cedar.cp.dto.model.FinanceCubePK;
import com.cedar.cp.dto.model.ModelPK;
import com.cedar.cp.dto.model.mapping.ImportMappedModelTaskRequest;
import com.cedar.cp.dto.model.mapping.MappedModelPK;
import com.cedar.cp.ejb.base.async.AbstractTask;
import com.cedar.cp.ejb.base.async.ImportMappedModelTask$1;
import com.cedar.cp.ejb.base.async.ImportMappedModelTask$2;
import com.cedar.cp.ejb.base.async.ImportMappedModelTask$3;
import com.cedar.cp.ejb.base.async.ImportMappedModelTask$4;
import com.cedar.cp.ejb.base.async.ImportMappedModelTask$5;
import com.cedar.cp.ejb.base.async.ImportMappedModelTask$MyCheckpoint;
import com.cedar.cp.ejb.base.async.cm.ChangeManagementCheckPoint;
import com.cedar.cp.ejb.impl.cm.ChangeMgmtEngine;
import com.cedar.cp.ejb.impl.cm.xml.ChangeManagementActionType;
import com.cedar.cp.ejb.impl.cm.xml.ChangeManagementEvent;
import com.cedar.cp.ejb.impl.cm.xml.ChangeManagementEventType;
import com.cedar.cp.ejb.impl.cm.xml.ChangeManagementType;
import com.cedar.cp.ejb.impl.datatype.DataTypeAccessor;
import com.cedar.cp.ejb.impl.datatype.DataTypeEVO;
import com.cedar.cp.ejb.impl.dimension.DimensionAccessor;
import com.cedar.cp.ejb.impl.dimension.DimensionEVO;
import com.cedar.cp.ejb.impl.dimension.HierarchyEVO;
import com.cedar.cp.ejb.impl.extsys.ExternalSystemAccessor;
import com.cedar.cp.ejb.impl.extsys.ExternalSystemDAO;
import com.cedar.cp.ejb.impl.extsys.ExternalSystemEVO;
import com.cedar.cp.ejb.impl.model.FinanceCubeEVO;
import com.cedar.cp.ejb.impl.model.ModelAccessor;
import com.cedar.cp.ejb.impl.model.ModelEVO;
import com.cedar.cp.ejb.impl.model.mapping.MappedCalendarElementEVO;
import com.cedar.cp.ejb.impl.model.mapping.MappedCalendarYearEVO;
import com.cedar.cp.ejb.impl.model.mapping.MappedDataTypeEVO;
import com.cedar.cp.ejb.impl.model.mapping.MappedDimensionEVO;
import com.cedar.cp.ejb.impl.model.mapping.MappedDimensionElementEVO;
import com.cedar.cp.ejb.impl.model.mapping.MappedFinanceCubeEVO;
import com.cedar.cp.ejb.impl.model.mapping.MappedHierarchyEVO;
import com.cedar.cp.ejb.impl.model.mapping.MappedModelAccessor;
import com.cedar.cp.ejb.impl.model.mapping.MappedModelEVO;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;
import javax.naming.InitialContext;
import javax.xml.datatype.DatatypeFactory;

public class ImportMappedModelTask extends AbstractTask {

   private transient InitialContext mInitialContext;
   private MappedModelAccessor mMappedModelAccessor;
   private ExternalSystemAccessor mExternalSystemAccessor;
   private ModelAccessor mModelAccessor;
   private DimensionAccessor mDimensionAccessor;
   private DataTypeAccessor mDataTypeAccessor;


   public void runUnitOfWork(InitialContext context) throws Exception {
      this.mInitialContext = context;
      if(this.getCheckpoint() == null) {
         this.firstTime();
      } else if(this.getCheckpoint().getCMCheckpoint() != null) {
         this.doChangeManagement();
      }

   }

   public boolean mustComplete() {
      return this.getCheckpoint() == null?false:this.getCheckpoint().getCMCheckpoint() != null;
   }

   public void tidyActions() {
      super.tidyActions();
      this._log.info("tidyActions", "deleting change managements for task " + this.getTaskId());
      String l = "delete from CHANGE_MGMT where TASK_ID = ?";
      PreparedStatement i$ = null;

      try {
         i$ = this.getConnection().prepareStatement(l);
         i$.setInt(1, this.getTaskId());
         int cubePK = i$.executeUpdate();
         if(cubePK > 0) {
            this._log.info("tidyActions", "deleted changeMgmts=" + cubePK);
         }
      } catch (SQLException var8) {
         throw new RuntimeException("unable to delete change managements for task", var8);
      } finally {
         this.closeStatement(i$);
         this.closeConnection();
      }

      if(this.getCheckpoint() != null && this.getCheckpoint().getCMCheckpoint() != null) {
         List l1 = this.getCheckpoint().getCMCheckpoint().getImportCubes();
         Iterator i$1 = l1.iterator();

         while(i$1.hasNext()) {
            FinanceCubePK cubePK1 = (FinanceCubePK)i$1.next();
            ExternalSystemDAO dao = new ExternalSystemDAO();
            int extSysType = dao.getExtSysTypeForCube(cubePK1.getFinanceCubeId());
            this._log.info("tidyActions", "import_utils tidy (type=" + extSysType);
            dao.importValues(extSysType, 3, this.getTaskId(), cubePK1.getFinanceCubeId());
         }
      }

   }

   private void firstTime() throws Exception {
      this.setCheckpoint(new ImportMappedModelTask$MyCheckpoint());
      this.log("Import Details:");
      ModelEVO modelEvo = null;

      int dimensionCount;
      for(dimensionCount = 0; dimensionCount < ((ImportMappedModelTaskRequest)this.getRequest()).getMappedModelIds().length; ++dimensionCount) {
         int cmRequestPKs = ((ImportMappedModelTaskRequest)this.getRequest()).getMappedModelIds()[dimensionCount];
         MappedModelEVO cmtr = this.getMappedModelAccessor().getDetails(new MappedModelPK(cmRequestPKs), "<0><1><2><3><4><5><6>");
         ExternalSystemEVO cmcp = this.getExternalSystemAccessor().getDetails(new ExternalSystemPK(cmtr.getExternalSystemId()), "");
         this.getCheckpoint().setExternalSystemType(cmcp.getSystemType());
         ChangeManagementType myCm = null;

         try {
            DatatypeFactory engine = DatatypeFactory.newInstance();
            myCm = new ChangeManagementType();
            myCm.setExtractDateTime(engine.newXMLGregorianCalendar(new GregorianCalendar()));
            myCm.setSourceSystemName(cmcp.getVisId());
         } catch (Exception var19) {
            var19.printStackTrace();
         }

         modelEvo = this.getModelAccessor().getDetails(new ModelPK(cmtr.getModelId()), "<0><9>");
         this.log("  Model=" + modelEvo.getVisId() + " (id=" + modelEvo.getModelId() + ")");
         this.log("    System=" + cmcp.getVisId());
         this.log("    Location=<" + cmcp.getLocation() + ">");
         this.log("   " + (cmtr.getCompanyVisId() == null?"":" Company=" + cmtr.getCompanyVisId()) + " Ledger=" + cmtr.getLedgerVisId());
         ArrayList yearRows = new ArrayList();
         yearRows.addAll(cmtr.getMappedCalendarYears());
         Collections.sort(yearRows, new ImportMappedModelTask$1(this));
         this.log("    Years/Periods:");
         StringBuffer sb = new StringBuffer();
         Iterator dimRows = yearRows.iterator();

         while(dimRows.hasNext()) {
            MappedCalendarYearEVO engine1 = (MappedCalendarYearEVO)dimRows.next();
            int mfcEvo = 0;
            Iterator fcEvo = engine1.getMappedCalendarElements().iterator();

            while(fcEvo.hasNext()) {
               MappedCalendarElementEVO isCubeImportable = (MappedCalendarElementEVO)fcEvo.next();
               if(isCubeImportable.getDimensionElementId() != null) {
                  ++mfcEvo;
               }
            }

            sb.append('[');
            sb.append(String.valueOf(engine1.getYear()));
            sb.append('/');
            sb.append(String.valueOf(mfcEvo));
            sb.append("] ");
         }

         this.log("      " + sb.toString());
         ArrayList var25 = new ArrayList();
         var25.addAll(cmtr.getMappedDimensions());
         Collections.sort(var25, new ImportMappedModelTask$2(this));
         Iterator var24 = var25.iterator();

         ArrayList dtRows;
         Iterator ivEvent;
         while(var24.hasNext()) {
            MappedDimensionEVO var26 = (MappedDimensionEVO)var24.next();
            DimensionEVO var30 = this.getDimensionAccessor().getDetails(new DimensionPK(var26.getDimensionId()), "<3>");
            this.log("    Dimension=" + var30.getVisId() + " (id=" + var30.getDimensionId() + ") excludeDisabledLeafs=" + var26.getExcludeDisabledLeafNodes());
            ChangeManagementEvent var31 = new ChangeManagementEvent();
            var31.setAction(ChangeManagementActionType.fromValue("amend"));
            var31.setType(ChangeManagementEventType.fromValue("dimension"));
            var31.setVisId(var30.getVisId());
            myCm.getEvent().add(var31);
            dtRows = new ArrayList();
            dtRows.addAll(var26.getMappedHierarchys());
            Collections.sort(dtRows, new ImportMappedModelTask$3(this));
            ivEvent = dtRows.iterator();

            while(ivEvent.hasNext()) {
               MappedHierarchyEVO mftEvo = (MappedHierarchyEVO)ivEvent.next();
               HierarchyEVO dtEvo = var30.getHierarchiesItem(new HierarchyPK(mftEvo.getHierarchyId()));
               if(dtEvo != null) {
                  this.log("      Hierarchy=" + dtEvo.getVisId() + " (id=" + dtEvo.getHierarchyId() + ")");
               }
            }

            this.log("      Dimension Element mappings:");
            ArrayList var33 = new ArrayList();
            var33.addAll(var26.getMappedDimensionElements());
            Collections.sort(var33, new ImportMappedModelTask$4(this));
            this.displayMappedDimElems(var30, var33, 0);
            this.displayMappedDimElems(var30, var33, 1);
            this.displayMappedDimElems(var30, var33, 2);
            this.displayMappedDimElems(var30, var33, 3);
         }

         var24 = cmtr.getMappedFinanceCubes().iterator();

         while(var24.hasNext()) {
            MappedFinanceCubeEVO var29 = (MappedFinanceCubeEVO)var24.next();
            
            FinanceCubeEVO var28 = modelEvo.getFinanceCubesItem(new FinanceCubePK(var29.getFinanceCubeId()));
            if(var28==null){
            	System.out.println(var29.toString());
            }
            this.log("    FinanceCube=" + var28.getVisId() + " (id=" + var28.getFinanceCubeId() + ")");
            boolean var32 = false;
            dtRows = new ArrayList();
            dtRows.addAll(var29.getMappedDataTypes());
            Collections.sort(dtRows, new ImportMappedModelTask$5(this));
            ivEvent = dtRows.iterator();

            while(ivEvent.hasNext()) {
               MappedDataTypeEVO var34 = (MappedDataTypeEVO)ivEvent.next();
               DataTypeEVO var36 = this.getDataTypeAccessor().getDetails(new DataTypePK((short)var34.getDataTypeId()), "");
               this.log("      valueType=" + var34.getValueTypeVisId1() + (var34.getValueTypeVisId2() == null?"":"/" + var34.getValueTypeVisId2()) + (var34.getValueTypeVisId3() == null?"":"/" + var34.getValueTypeVisId3()) + " dataType=" + var36.getVisId() + " importable=" + var36.getAvailableForImport() + " impExpStatus=" + (var34.getImpExpStatus() == 1?" export_only":(var34.getImpExpStatus() == 2?" import_export":(var34.getImpExpStatus() == 0?" import_only":"?"))));
               this.log("        yearOffsets: import from=" + var34.getImpStartYearOffset() + " to=" + var34.getImpEndYearOffset() + " export from=" + var34.getExpStartYearOffset() + " to=" + var34.getExpEndYearOffset());
               if(var36.getAvailableForImport() && var34.getImpExpStatus() != 1) {
                  var32 = true;
               }
            }

            if(var32) {
               ChangeManagementEvent var35 = new ChangeManagementEvent();
               var35.setAction(ChangeManagementActionType.fromValue("import"));
               var35.setType(ChangeManagementEventType.fromValue("import-values"));
               var35.setVisId(String.valueOf(var28.getFinanceCubeId()));
               myCm.getEvent().add(var35);
            }
         }

         if(((ImportMappedModelTaskRequest)this.getRequest()).isSafeMode() && !this.checkUnmappedDimElements(cmtr, var25)) {
            this.setCompletionExceptionMessage("Import terminated (no data has been changed).");
            this.setCheckpoint((TaskCheckpoint)null);
            return;
         }

         ChangeMgmtEngine var27 = new ChangeMgmtEngine(this.mInitialContext, (ChangeManagementTaskRequest)null, (ChangeManagementCheckPoint)null, this, this);
         var27.registerUpdateRequest(myCm);
         var27.setChangeMgmtTaskIds(ChangeMgmtEngine.queryChangeRequestPKs(modelEvo.getEntityRef()), this.getTaskId());
      }

      dimensionCount = modelEvo.getModelDimensionRels().size();
      List var20 = ChangeMgmtEngine.queryChangeRequestPKs(modelEvo.getEntityRef());
      ChangeManagementTaskRequest var21 = new ChangeManagementTaskRequest(modelEvo.getEntityRef(), dimensionCount, var20);
      ChangeManagementCheckPoint var22 = new ChangeManagementCheckPoint();
      ChangeMgmtEngine var23 = new ChangeMgmtEngine(this.mInitialContext, var21, var22, this, this);
      var23.setTaskId(this.getTaskId());
      var23.setUserId(this.getUserId());
      var23.setChangeMgmtTaskIds(var20, this.getTaskId());
      this.getCheckpoint().setCMRequest(var21);
      this.getCheckpoint().setCMCheckpoint(var22);
   }

   private boolean checkUnmappedDimElements(MappedModelEVO mmEvo, List<MappedDimensionEVO> dimRows) throws Exception {
      String warning = null;
      Iterator i$ = dimRows.iterator();

      while(i$.hasNext()) {
         MappedDimensionEVO mdEvo = (MappedDimensionEVO)i$.next();
         DimensionEVO dimEvo = this.getDimensionAccessor().getDetails(new DimensionPK(mdEvo.getDimensionId()), "<3>");
         this.log(" ");
         this.log("checking removed elements in dimension " + dimEvo.getVisId());
         ExternalSystemDAO xsdao = new ExternalSystemDAO();
         EntityList diffs = xsdao.importDimension(this.getTaskId(), this.getCheckpoint().getExternalSystemType(), mdEvo.getDimensionId());
         int unmappedCount = 0;
         int batchCount = 0;
         PreparedStatement batchStmt = null;

         try {
            batchStmt = this.getConnection().prepareStatement("insert into FS_DE\n(VIS_ID, FS_DESCR, FS_IS_CREDIT, FS_NOT_PLANNABLE, FS_DISABLED)\nvalues\n(?, nvl(?,\' \'), nvl(?,\' \'), nvl(?,\' \'), nvl(?,\' \'))");

            for(int stmt = 0; stmt < diffs.getNumRows(); ++stmt) {
               String sqle = (String)diffs.getValueAt(stmt, "TYPE");
               String action = (String)diffs.getValueAt(stmt, "ACTION");
               String visId = (String)diffs.getValueAt(stmt, "VIS_ID");
               String descr = (String)diffs.getValueAt(stmt, "DESCR");
               String notPlannableStr = (String)diffs.getValueAt(stmt, "NOT_PLANNABLE");
               String disabledStr = (String)diffs.getValueAt(stmt, "DISABLED");
               String isCreditStr = (String)diffs.getValueAt(stmt, "IS_CREDIT");
               if(sqle.equals("dimension-element") && action.equals("delete")) {
                  batchStmt.setString(1, visId);
                  batchStmt.setString(2, descr);
                  batchStmt.setString(3, isCreditStr);
                  batchStmt.setString(4, notPlannableStr);
                  batchStmt.setString(5, disabledStr);
                  batchStmt.addBatch();
                  ++unmappedCount;
                  ++batchCount;
                  if(batchCount > 100) {
                     batchStmt.executeBatch();
                     batchCount = 0;
                  }
               }
            }

            if(batchCount > 0) {
               batchStmt.executeBatch();
            }
         } catch (SQLException var32) {
            throw new RuntimeException(this.getEntityName() + " checkUnmappedElements", var32);
         } finally {
            this.closeStatement(batchStmt);
            this.closeConnection();
         }

         if(unmappedCount != 0) {
            CallableStatement var34 = null;

            try {
               var34 = this.getConnection().prepareCall("begin import_utils.checkUnmappedDimElems(?,?,?,?); end;");
               var34.setInt(1, this.getTaskId());
               var34.setInt(2, mmEvo.getModelId());
               var34.setInt(3, mdEvo.getDimensionId());
               var34.registerOutParameter(4, 12);
               var34.execute();
               if(var34.getString(4) != null && var34.getString(4).equals("Y")) {
                  warning = "Y";
               }
            } catch (SQLException var30) {
               throw new RuntimeException(this.getEntityName() + " checkUnmappedElements", var30);
            } finally {
               this.closeStatement(var34);
               this.closeConnection();
            }
         }
      }

      if(warning != null && warning.equals("Y")) {
         this.log(" ");
         this.logInfo("Non-readonly data would be lost due to unmapped dimension elements.");
         this.log(" ");
         this.logInfo("Either:");
         this.logInfo("  (a) change the mapping to include the unmapped dimension elements");
         this.logInfo("  (b) to import regardless, use the Import (skip validation) menu option");
         return false;
      } else {
         return true;
      }
   }

   private void displayMappedDimElems(DimensionEVO dimEvo, List<MappedDimensionElementEVO> deRows, int mappingType) {
      StringBuffer sb = new StringBuffer();
      boolean firstOfType = true;
      Iterator i$ = deRows.iterator();

      while(i$.hasNext()) {
         MappedDimensionElementEVO mdeEvo = (MappedDimensionElementEVO)i$.next();
         if(mdeEvo.getMappingType() == mappingType) {
            if(firstOfType) {
               if(mappingType == 0) {
                  this.log("        specific names [Name" + (dimEvo.getType() == 1?",Type":"") + "]:");
               } else if(mappingType == 1) {
                  this.log("        prefixes [Prefix" + (dimEvo.getType() == 1?",Type":"") + "]:");
               } else if(mappingType == 2) {
                  this.log("        ranges [From,To" + (dimEvo.getType() == 1?",Type":"") + "]:");
               } else {
                  this.log("        hierarchy elements: {HierarchyElement,Hierarchy,HierarchyType]");
               }
            }

            firstOfType = false;
            if(sb.length() > 60) {
               this.log("        " + sb.toString());
               sb.setLength(0);
            }

            sb.append('[');
            sb.append(mdeEvo.getElementVisId1());
            if(mdeEvo.getMappingType() == 2 || mdeEvo.getMappingType() == 3) {
               sb.append(',');
               sb.append(mdeEvo.getElementVisId2());
            }

            if(mdeEvo.getElementVisId3() != null && !mdeEvo.getElementVisId3().equals(" ")) {
               sb.append(',');
               sb.append(mdeEvo.getElementVisId3());
            }

            sb.append("] ");
         }
      }

      if(sb.length() > 0) {
         this.log("        " + sb.toString());
      }

   }

   private void createCMUpdateRequest(ExternalSystemEVO xsEvo, ModelEVO modelEvo, Object event) throws ValidationException {
      ChangeManagementType myCm = null;

      try {
         DatatypeFactory factory = DatatypeFactory.newInstance();
         myCm = new ChangeManagementType();
         myCm.setExtractDateTime(factory.newXMLGregorianCalendar(new GregorianCalendar()));
         myCm.setSourceSystemName(xsEvo.getVisId());
         myCm.getEvent().add((ChangeManagementEvent)event);
      } catch (Exception var7) {
         var7.printStackTrace();
      }

      ChangeMgmtEngine engine = new ChangeMgmtEngine(this.mInitialContext, (ChangeManagementTaskRequest)null, (ChangeManagementCheckPoint)null, this, this);
      engine.registerUpdateRequest(myCm);
      engine.setChangeMgmtTaskIds(ChangeMgmtEngine.queryChangeRequestPKs(modelEvo.getEntityRef()), this.getTaskId());
   }

   private void doChangeManagement() throws Exception {
      ChangeMgmtEngine engine = new ChangeMgmtEngine(this.mInitialContext, this.getCheckpoint().getCMRequest(), this.getCheckpoint().getCMCheckpoint(), this, this);
      engine.setTaskId(this.getTaskId());
      engine.setUserId(this.getUserId());
      ChangeManagementCheckPoint cmCheckpoint = engine.runChangeManagement();
      if(cmCheckpoint != null) {
         this.getCheckpoint().setCMCheckpoint(cmCheckpoint);
      } else {
         this.getCheckpoint().setCMRequest((ChangeManagementTaskRequest)null);
         this.getCheckpoint().setCMCheckpoint((ChangeManagementCheckPoint)null);
         this.setCheckpoint((TaskCheckpoint)null);
      }

   }

   public int getReportType() {
      return 5;
   }

   public ImportMappedModelTask$MyCheckpoint getCheckpoint() {
      return (ImportMappedModelTask$MyCheckpoint)super.getCheckpoint();
   }

   public String getEntityName() {
      return "ImportMappedModelTask";
   }

   private static String decodeNullableYNValue(String value) {
      return value != null?(value.equalsIgnoreCase("Y")?"true":"false"):null;
   }

   private MappedModelAccessor getMappedModelAccessor() {
      if(this.mMappedModelAccessor == null) {
         this.mMappedModelAccessor = new MappedModelAccessor(this.mInitialContext);
      }

      return this.mMappedModelAccessor;
   }

   private ExternalSystemAccessor getExternalSystemAccessor() {
      if(this.mExternalSystemAccessor == null) {
         this.mExternalSystemAccessor = new ExternalSystemAccessor(this.mInitialContext);
      }

      return this.mExternalSystemAccessor;
   }

   private ModelAccessor getModelAccessor() {
      if(this.mModelAccessor == null) {
         this.mModelAccessor = new ModelAccessor(this.mInitialContext);
      }

      return this.mModelAccessor;
   }

   private DimensionAccessor getDimensionAccessor() {
      if(this.mDimensionAccessor == null) {
         this.mDimensionAccessor = new DimensionAccessor(this.mInitialContext);
      }

      return this.mDimensionAccessor;
   }

   private DataTypeAccessor getDataTypeAccessor() {
      if(this.mDataTypeAccessor == null) {
         this.mDataTypeAccessor = new DataTypeAccessor(this.mInitialContext);
      }

      return this.mDataTypeAccessor;
   }
}
