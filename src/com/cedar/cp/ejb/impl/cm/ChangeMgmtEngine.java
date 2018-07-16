package com.cedar.cp.ejb.impl.cm;

import java.io.StringReader;
import java.io.StringWriter;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.ejb.EJBException;
import javax.naming.InitialContext;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.transform.stream.StreamSource;

import com.cedar.cp.api.base.CPConnection;
import com.cedar.cp.api.base.CPConnection.ConnectionContext;
import com.cedar.cp.api.base.CPException;
import com.cedar.cp.api.base.DriverManager;
import com.cedar.cp.api.base.EntityList;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.cubeformula.CubeFormulaRef;
import com.cedar.cp.api.datatype.DataTypeRef;
import com.cedar.cp.api.dimension.DimensionRef;
import com.cedar.cp.api.model.FinanceCubeRef;
import com.cedar.cp.api.model.ModelRef;
import com.cedar.cp.dto.base.EntityEventMessage;
import com.cedar.cp.dto.base.PrimaryKey;
import com.cedar.cp.dto.cm.AllChangeMgmtsForModelELO;
import com.cedar.cp.dto.cm.ChangeManagementTaskRequest;
import com.cedar.cp.dto.cm.ChangeMgmtEditorSessionSSO;
import com.cedar.cp.dto.cm.ChangeMgmtImpl;
import com.cedar.cp.dto.cm.ChangeMgmtPK;
import com.cedar.cp.dto.cm.ChangeMgmtRefImpl;
import com.cedar.cp.dto.cubeformula.CubeFormulaRefImpl;
import com.cedar.cp.dto.datatype.DataTypeRefImpl;
import com.cedar.cp.dto.dimension.AllDimensionsELO;
import com.cedar.cp.dto.dimension.DimensionPK;
import com.cedar.cp.dto.model.AllModelsELO;
import com.cedar.cp.dto.model.FinanceCubeCK;
import com.cedar.cp.dto.model.FinanceCubePK;
import com.cedar.cp.dto.model.FinanceCubeRefImpl;
import com.cedar.cp.dto.model.FinanceCubesForModelELO;
import com.cedar.cp.dto.model.ModelDimensionsELO;
import com.cedar.cp.dto.model.ModelPK;
import com.cedar.cp.dto.model.ModelRefImpl;
import com.cedar.cp.dto.task.TaskMessageLogger;
import com.cedar.cp.dto.user.UserPK;
import com.cedar.cp.ejb.api.cm.ChangeMgmtEditorSessionServer;
import com.cedar.cp.ejb.base.TaskReportWriter;
import com.cedar.cp.ejb.base.async.cm.ChangeManagementCheckPoint;
import com.cedar.cp.ejb.base.async.cm.ChangeManagementCheckPoint.FinanceCubeDataTypeUpdate;
import com.cedar.cp.ejb.base.async.cm.ChangeManagementCheckPoint.FinanceCubeUpdate;
import com.cedar.cp.ejb.base.async.cm.ExtSysDimensionComparator;
import com.cedar.cp.ejb.base.common.util.SystemPropertyHelper;
import com.cedar.cp.ejb.base.cube.CubeUpdateEngine;
import com.cedar.cp.ejb.base.cube.formula.FormulaCompiler;
import com.cedar.cp.ejb.base.cube.formula.FormulaDAO;
import com.cedar.cp.ejb.base.cube.formula.tablemeta.MetaTableManager;
import com.cedar.cp.ejb.impl.base.AbstractDAO;
import com.cedar.cp.ejb.impl.cm.frmwrk.CMMetaDataUpdateKernel;
import com.cedar.cp.ejb.impl.cm.frmwrk.CalendarLeafLevelUpdate;
import com.cedar.cp.ejb.impl.cm.xml.ChangeManagementEvent;
import com.cedar.cp.ejb.impl.cm.xml.ChangeManagementType;
import com.cedar.cp.ejb.impl.cm.xml.ObjectFactory;
import com.cedar.cp.ejb.impl.cubeformula.CubeFormulaDAO;
import com.cedar.cp.ejb.impl.cubeformula.CubeFormulaPackageDAO;
import com.cedar.cp.ejb.impl.datatype.DataTypeDAO;
import com.cedar.cp.ejb.impl.dimension.DimensionAccessor;
import com.cedar.cp.ejb.impl.dimension.DimensionUtilsDAO;
import com.cedar.cp.ejb.impl.extsys.ExternalSystemDAO;
import com.cedar.cp.ejb.impl.model.FinanceCubeDAO;
import com.cedar.cp.ejb.impl.model.ModelAccessor;
import com.cedar.cp.ejb.impl.model.ModelDAO;
import com.cedar.cp.ejb.impl.model.ModelEVO;
import com.cedar.cp.ejb.impl.systemproperty.SystemPropertyDAO;
import com.cedar.cp.ejb.impl.user.UserDAO;
import com.cedar.cp.ejb.impl.user.UserEVO;
import com.cedar.cp.util.Cryptography;
import com.cedar.cp.util.Log;
import com.cedar.cp.util.Timer;
import com.cedar.cp.util.common.JmsConnectionImpl;
import com.cedar.cp.util.task.TaskMessageFactory;

public class ChangeMgmtEngine extends AbstractDAO {

   private UserEVO mUserEVO;
   private int mTaskId;
   private int mUserId;
   private Integer mExternalSystemType;
   private InitialContext mInitialContext;
   private ModelAccessor mModelAccessor;
   private CPConnection mCPConnection;
   private DimensionAccessor mDimensionAccessor;
   private ChangeMgmtAccessor mChangeMgmtAccessor;
   private transient ChangeManagementCheckPoint mCheckpoint;
   private transient ChangeManagementTaskRequest mRequest;
   private TaskMessageLogger mTaskLog;
   private TaskReportWriter mReportWriter;
   private Log mLog = new Log(this.getClass());
   private int companyId;

   public ChangeMgmtEngine(InitialContext initialContext, ChangeManagementTaskRequest request, ChangeManagementCheckPoint checkpoint, TaskMessageLogger msgLogger, TaskReportWriter reportWriter) {
      this.mInitialContext = initialContext;
      this.mRequest = request;
      this.mCheckpoint = checkpoint;
      this.mTaskLog = msgLogger;
      this.mReportWriter = reportWriter;
   }

   public ChangeManagementCheckPoint runChangeManagement() throws Exception {
      switch(this.mCheckpoint.getPhaseNumber()) {
      case 0:
         this.processMetaDataPhase();
         break;
      case 1:
         this.processRebuildRuntime();
         break;
      case 2:
         this.processFinanceCubeLeafLevelChanges();
         break;
      case 3:
         this.processCubeFormulaUpdates();
         break;
      case 4:
         this.processFinanceCubeUpdates();
         break;
      case 5:
         this.processRebuildFinanceCubes();
         break;
      case 6:
         this.processImportFinanceCube();
         break;
      case 7:
         this.processRecreateExportViews();
         break;
      case 8:
         this.processTerminatePhase();
         this.mCheckpoint = null;
         break;
      default:
         throw new IllegalStateException("Unknown phase number: " + this.mCheckpoint.getPhaseNumber());
      }

      if(this.mCheckpoint != null) {
         this.mCheckpoint.nextPhaseOrStep();
      }

      return this.mCheckpoint;
   }

	public ChangeManagementCheckPoint runGlobalChangeManagement( Map<String, Integer> company ) throws Exception {
		this.companyId = company.get("companyId");
		
		switch (this.mCheckpoint.getPhaseNumber()) {
		case 0:
			this.processMetaDataPhase();
			break;
		case 1:
			this.processRebuildRuntime();
			break;
		case 2:
			this.processFinanceCubeLeafLevelChanges();
			break;
		case 3:
			this.processCubeFormulaUpdates();
			break;
		case 4:
			this.processFinanceCubeUpdates();
			break;
		case 5:
			this.processRebuildFinanceCubes();
			break;
		case 6:
			this.processImportGlobalFinanceCube( company );
			break;
		case 7:
			this.processRecreateExportViews();
			break;
		case 8:
			this.processTerminatePhase();
			this.mCheckpoint = null;
			break;
		default:
			throw new IllegalStateException("Unknown phase number: " + this.mCheckpoint.getPhaseNumber());
		}

		if (this.mCheckpoint != null) {
			this.mCheckpoint.nextPhaseOrStep();
		}

		return this.mCheckpoint;
	}
	
	public ChangeManagementCheckPoint runGlobal2ChangeManagement( int company ) throws Exception {
		this.companyId = company;
		
		switch (this.mCheckpoint.getPhaseNumber()) {
		case 0:
			this.processMetaDataPhase();
			break;
		case 1:
			this.processRebuildRuntime();
			break;
		case 2:
			this.processFinanceCubeLeafLevelChanges();
			break;
		case 3:
			this.processCubeFormulaUpdates();
			break;
		case 4:
			this.processFinanceCubeUpdates();
			break;
		case 5:
			this.processRebuildFinanceCubes( company );
			break;
		case 6:
			this.processImportFinanceCube( company );
			break;
		case 7:
			this.processRecreateExportViewsGlobal();
			break;
		case 8:
			this.processTerminatePhase();
			this.mCheckpoint = null;
			break;
		default:
			throw new IllegalStateException("Unknown phase number: " + this.mCheckpoint.getPhaseNumber());
		}

		if (this.mCheckpoint != null) {
			this.mCheckpoint.nextPhaseOrStep();
		}

		return this.mCheckpoint;
	}
   
    public ChangeManagementCheckPoint processRecreateExportViewsGlobal() throws Exception {
        FinanceCubePK fcPK = this.mCheckpoint.queryRecreateExportView();
        if (fcPK != null) {
            ExternalSystemDAO dao = new ExternalSystemDAO();
            dao.createExternalViewsGlobal(this.getTaskId(), fcPK.getFinanceCubeId());
        }

        return this.mCheckpoint;
    }

    public void setTaskId(int taskId) {
        this.mTaskId = taskId;
    }

   private int getTaskId() {
      return this.mTaskId;
   }

   public void setExternalSystemType(Integer extSysType) {
      this.mExternalSystemType = extSysType;
   }

   private Integer getExternalSystemType() {
      return this.mExternalSystemType;
   }

   public String getEntityName() {
      return "ChangeMgmtEngine";
   }

   public void registerUpdateRequest(ChangeManagementType cm) throws ValidationException {
      try {
         ObjectFactory e = new ObjectFactory();
         JAXBContext jc = JAXBContext.newInstance("com.cedar.cp.ejb.impl.cm.xml");
         XMLGregorianCalendar cmTimeStamp = cm.getExtractDateTime();
         String sourceSystem = cm.getSourceSystemName();
         List cmEvents = cm.getEvent();
         HashMap requestMap = new HashMap();
         Iterator cmIter = cmEvents.iterator();

         while(cmIter.hasNext()) {
            ChangeManagementEvent reqIter = (ChangeManagementEvent)cmIter.next();
            String reqMapEntry = reqIter.getType().value();
            String myCm = reqIter.getAction().value();
            String m = reqIter.getVisId();
            ModelRef modelRef;
            CMRequestMap reqMap;
            if(!reqMapEntry.equalsIgnoreCase("dimension") && !reqMapEntry.equalsIgnoreCase("calendar")) {
               if(reqMapEntry.equalsIgnoreCase("import-values")) {
                  if(!myCm.equalsIgnoreCase("import")) {
                     throw new ValidationException("Unknown action type for financial-import event" + myCm);
                  }

                  try {
                     int var22 = Integer.parseInt(m);
                     modelRef = this.validateValueImport(var22);
                     reqMap = (CMRequestMap)requestMap.get(modelRef.getPrimaryKey());
                     if(reqMap == null) {
                        reqMap = new CMRequestMap(this, modelRef);
                        requestMap.put(modelRef.getPrimaryKey(), reqMap);
                     }

                     reqMap.addEvent(reqIter);
                  } catch (NumberFormatException var16) {
                     throw new ValidationException("Invalid finance cube visId on financial-import event:" + m);
                  }
               } else if(reqMapEntry.equalsIgnoreCase("finance-cube") && (myCm.equalsIgnoreCase("amend") || myCm.equalsIgnoreCase("export-views"))) {
                  String var25 = reqIter.getModel();
                  if(var25 == null) {
                     throw new ValidationException("Expected model attribute for finance-cube change management");
                  }

                  modelRef = this.validateFinanceCubeChangeManagement(var25, m);
                  reqMap = (CMRequestMap)requestMap.get(modelRef.getPrimaryKey());
                  if(reqMap == null) {
                     reqMap = new CMRequestMap(this, modelRef);
                     requestMap.put(modelRef.getPrimaryKey(), reqMap);
                  }

                  reqMap.addEvent(reqIter);
               }
            } else {
               if(!myCm.equalsIgnoreCase("amend")) {
                  throw new ValidationException("Unknown action type for dimension event:" + myCm);
               }

               Object[] sw = this.validateDimensionAmend(m);
               modelRef = (ModelRef)sw[0];
               reqMap = (CMRequestMap)requestMap.get(modelRef.getPrimaryKey());
               if(reqMap == null) {
                  reqMap = new CMRequestMap(this, modelRef);
                  requestMap.put(modelRef.getPrimaryKey(), reqMap);
               }

               reqMap.addEvent(reqIter);
            }
         }

         Iterator var18 = requestMap.values().iterator();

         while(var18.hasNext()) {
            CMRequestMap var20 = (CMRequestMap)var18.next();
            ChangeManagementType var19 = e.createChangeManagementType();
            var19.setExtractDateTime(cmTimeStamp);
            var19.setSourceSystemName(sourceSystem);

            for(int var21 = 0; var21 < var20.getEvents().size(); ++var21) {
               ChangeManagementEvent var23 = (ChangeManagementEvent)var20.getEvents().get(var21);
               var19.getEvent().add(var23);
            }

            Marshaller var24 = jc.createMarshaller();
            var24.setProperty("jaxb.formatted.output", Boolean.TRUE);
            StringWriter var26 = new StringWriter();
            var24.marshal(var19, var26);
            this.insertRequest(var20.getModelRef(), cmTimeStamp, sourceSystem, var26.getBuffer().toString());
         }

      } catch (JAXBException var17) {
         var17.printStackTrace();
         throw new ValidationException(var17.getMessage());
      }
   }

   public void registerUpdateRequest(String xmlRequest) throws ValidationException {
      try {
         StreamSource e = new StreamSource(new StringReader(xmlRequest));
         JAXBContext jc = JAXBContext.newInstance("com.cedar.cp.ejb.impl.cm.xml");
         Unmarshaller u = jc.createUnmarshaller();
         JAXBElement cmElement = (JAXBElement)u.unmarshal(e);
         ChangeManagementType cm = (ChangeManagementType)cmElement.getValue();
         this.registerUpdateRequest(cm);
      } catch (JAXBException var7) {
         var7.printStackTrace();
         throw new ValidationException(var7.getMessage());
      }
   }

	public int issueUpdateTask(UserPK userPK, ModelRef model) throws ValidationException, Exception {
		List changeMangementPKs = queryChangeRequestPKs(model);
		if (changeMangementPKs.size() == 0) {
			throw new ValidationException("No requests outstanding for model " + model.getNarrative());
		}

		ModelDimensionsELO dims = getModelAccessor().getModelDimensions(((ModelPK) model.getPrimaryKey()).getModelId());
		ChangeManagementTaskRequest cmtr = new ChangeManagementTaskRequest(model, dims.getNumRows(), changeMangementPKs);
		AllChangeMgmtsForModelELO elo = this.getChangeMgmtAccessor().getAllChangeMgmtsForModel(((ModelPK) model.getPrimaryKey()).getModelId());
		elo.reset();
		while (elo.hasNext()) {
			elo.next();
			if (elo.getTaskId() > 0) {
				throw new ValidationException("ChangeManagement for model " + model.getNarrative() + " has already been issued (TaskId=" + elo.getTaskId() + ")");
			}
		}

		int taskId = TaskMessageFactory.issueNewTask(getInitialContext(), false, cmtr, userPK.getUserId());
		this.setChangeMgmtTaskIds(changeMangementPKs, taskId);
		return taskId;
	}

   public void setChangeMgmtTaskIds(List changeMangementPKs, int taskId) {
      try {
         ChangeMgmtDAO dao = new ChangeMgmtDAO(this.getConnection());

         ChangeMgmtPK cmPK;
         for(Iterator iter = changeMangementPKs.iterator(); iter.hasNext(); this.sendEntityEventMessage("ChangeMgmt", cmPK, 3)) {
            cmPK = (ChangeMgmtPK)iter.next();

            try {
               ChangeMgmtEVO e = dao.getDetails(cmPK, "");
               e.setTaskId(taskId);
               dao.setDetails(e);
               dao.store();
            } catch (Exception var10) {
               throw new CPException(var10.getMessage(), var10);
            }
         }
      } finally {
         this.closeConnection();
      }

   }

   public static List queryChangeRequestPKs(ModelRef model) {
      int modelId = ((ModelPK)model.getPrimaryKey()).getModelId();
      AllChangeMgmtsForModelELO requests = (new ChangeMgmtDAO()).getAllChangeMgmtsForModel(modelId);
      ArrayList pks = new ArrayList();

      for(int row = 0; row < requests.getNumRows(); ++row) {
         ChangeMgmtRefImpl changeMgmtRef = (ChangeMgmtRefImpl)requests.getValueAt(row, "ChangeMgmt");
         pks.add(changeMgmtRef.getChangeMgmtPK());
      }

      return pks;
   }

   private void lockModel(ModelRef model) throws ValidationException, Exception {
      ModelEVO modelEVO = this.getModelAccessor().getDetails(model.getPrimaryKey(), "");
      modelEVO.setLocked(true);
      this.getModelAccessor().setDetails(modelEVO);
      this.sendEntityEventMessage("Model", (ModelPK)model.getPrimaryKey(), 3);
   }

   private void unlockModel(ModelRef model) throws ValidationException, Exception {
      ModelEVO modelEVO = this.getModelAccessor().getDetails(model.getPrimaryKey(), "");
      modelEVO.setLocked(false);
      this.getModelAccessor().setDetails(modelEVO);
      this.sendEntityEventMessage("Model", (ModelPK)model.getPrimaryKey(), 3);
   }

   public ChangeManagementCheckPoint processMetaDataPhase() throws Exception {
      CMMetaDataUpdateKernel updateKernel = new CMMetaDataUpdateKernel(this);
      updateKernel.setTaskId(Integer.valueOf(this.getTaskId()));
      this.lockModel(this.mRequest.getModelRef());
      List requests = this.mRequest.getRequests();
      Iterator requestIter = requests.iterator();

      while(requestIter.hasNext()) {
         ChangeMgmtPK pk = (ChangeMgmtPK)requestIter.next();
         ChangeMgmtAccessor accessor = this.getChangeMgmtAccessor();
         ChangeMgmtEVO evo = accessor.getDetails(pk, "");
         String XMLRequest = evo.getXmlText();
         StreamSource ss = new StreamSource(new StringReader(XMLRequest));
         JAXBContext jc = JAXBContext.newInstance("com.cedar.cp.ejb.impl.cm.xml");
         Unmarshaller u = jc.createUnmarshaller();
         ChangeManagementType cm = (ChangeManagementType)u.unmarshal(ss);
         updateKernel.process(this.mRequest.getModelRef(), cm);
      }

      return this.mCheckpoint;
   }

   public ChangeManagementCheckPoint processRebuildRuntime() throws Exception {
      try {
         DimensionUtilsDAO dao = new DimensionUtilsDAO(this.getConnection());
         Iterator dIter = this.mCheckpoint.getDimensionPKs().iterator();

         while(dIter.hasNext()) {
            DimensionPK dimPK = (DimensionPK)dIter.next();
            dao.initDimensionUtils(this.getTaskId(), dimPK.getDimensionId());
         }
      } finally {
         this.closeConnection();
      }

      return this.mCheckpoint;
   }

   public ChangeManagementCheckPoint processFinanceCubeLeafLevelChanges() throws Exception {
      CalendarLeafLevelUpdate cllu = this.mCheckpoint.queryCalendarLeafLevelUpdate();
      FinanceCubePK fcPK = this.mCheckpoint.queryCalendarLeafLevelUpdateFinanceCube();
      if(fcPK != null && cllu != null) {
         this.mLog.info("processFinanceCubeLeafLevelChanges", "Processing calendar leaf level update:" + cllu);
         this.mLog.info("processFinanceCubeLeafLevelChanges", "FinanceCubePK:" + fcPK);
         if(cllu.getNewCalendarLevel() != -1) {
            this.applyCalendarLeafLevelUpdate(cllu, fcPK);
         }
      }

      return this.mCheckpoint;
   }

   private void applyCalendarLeafLevelUpdate(CalendarLeafLevelUpdate cllu, FinanceCubePK fcPK) throws Exception {
      this.insertCalendarSpreadDataIntoGlobalTemporary(cllu);
      Timer timer = this.mLog.isDebugEnabled()?new Timer(this.mLog):null;
      CallableStatement stmt = null;

      try {
         if(cllu.isAllocation()) {
            stmt = this.getConnection().prepareCall("{ call calendar_utils.allocate(?,?) }");
         } else {
            stmt = this.getConnection().prepareCall("{ call calendar_utils.summarise(?,?) }");
         }

         stmt.setInt(1, this.getTaskId());
         stmt.setInt(2, fcPK.getFinanceCubeId());
         stmt.execute();
      } catch (SQLException var9) {
         var9.printStackTrace();
         throw new RuntimeException(var9);
      } finally {
         if(timer != null) {
            timer.logDebug("applyCalendarLeafLevelUpdate");
         }

         this.closeStatement(stmt);
         this.closeConnection();
      }

   }

   private void insertCalendarSpreadDataIntoGlobalTemporary(CalendarLeafLevelUpdate cllu) throws Exception {
      PreparedStatement ps = null;

      try {
         ps = this.getConnection().prepareStatement("insert into cal_leaf_map (src_cell_id,target_cell_id,spread_count,idx)  values(?,?,?,?)");

         for(int result = 0; result < cllu.getIds().length; ++result) {
            int[] ids = cllu.getIds()[result];
            ps.setInt(1, ids[0]);
            ps.setInt(2, ids[1]);
            ps.setInt(3, ids[2]);
            ps.setInt(4, result + 1);
            ps.addBatch();
            this.mLog.debug("insert into cal_leaf_map values(" + ids[0] + "," + ids[1] + "," + ids[2] + "," + (result + 1) + ")");
         }

         int[] var8 = ps.executeBatch();
      } finally {
         this.closeStatement(ps);
         this.closeConnection();
      }
   }

   public ChangeManagementCheckPoint processCubeFormulaUpdates() throws Exception {
      ChangeManagementCheckPoint.FormulaUpdate fu = this.mCheckpoint.queryFormulaUpdate();
      int financeCubeId = ((FinanceCubeRefImpl)fu.getFinanceCubeRef()).getFinanceCubePK().getFinanceCubeId();
      int formulaId = ((CubeFormulaRefImpl)fu.getFormulaRef()).getCubeFormulaPK().getCubeFormulaId();
      switch(ChangeMgmtEngine$1.$SwitchMap$com$cedar$cp$ejb$base$async$cm$ChangeManagementCheckPoint$FormulaUpdateType[fu.getUpdateType().ordinal()]) {
      case 1:
         List allCubeFormulaToUnDeploy = this.mCheckpoint.getAllCubeFormulaToUndeployForFinanceCube(fu.getFinanceCubeRef());
         HashSet allCubeFormulaIdsToUndeploy = new HashSet();
         Iterator i$ = allCubeFormulaToUnDeploy.iterator();

         while(i$.hasNext()) {
            CubeFormulaRef cubeFormulaRef = (CubeFormulaRef)i$.next();
            allCubeFormulaIdsToUndeploy.add(Integer.valueOf(((CubeFormulaRefImpl)cubeFormulaRef).getCubeFormulaPK().getCubeFormulaId()));
            this.getTaskMessageLogger().logInfo("Undeploying invalidated formula=" + cubeFormulaRef + " in finance cube=" + fu.getFinanceCubeRef());
         }

         (new FormulaCompiler(new FormulaDAO(), new CubeFormulaDAO(), new CubeFormulaPackageDAO(), new ModelDAO(), new FinanceCubeDAO(), new DataTypeDAO(), new SystemPropertyDAO(), new MetaTableManager())).remove(financeCubeId, allCubeFormulaIdsToUndeploy, true);
         this.mCheckpoint.setStepNumber(this.mCheckpoint.getFormulaUpdates().size());
      case 2:
      default:
         return this.mCheckpoint;
      }
   }

   public ChangeManagementCheckPoint processFinanceCubeUpdates() throws Exception {
      FinanceCubeUpdate fcu = this.mCheckpoint.queryFinanceCubeToUpdate();
      int financeCubeId = fcu.getFinanceCubeRef().getFinanceCubePK().getFinanceCubeId();
      switch(this.mCheckpoint.getStepNumber()) {
      case 0:
         this.deleteUnusedDataTypes(financeCubeId);
         break;
      case 1:
         this.rebuildFinanceCube(financeCubeId, 0);
         break;
      case 2:
         this.rebuildFinanceCube(financeCubeId, 1);
         break;
      case 3:
         this.rebuildMeasureParents(financeCubeId, fcu.getDataTypeUpdateActions());
         break;
      case 4:
         this.rebuildFinanceCube(financeCubeId, 3);
      }

      return this.mCheckpoint;
   }

   public ChangeManagementCheckPoint processRebuildFinanceCubes() throws Exception {
	   return this.processRebuildFinanceCubes( 0 );
   }
   public ChangeManagementCheckPoint processRebuildFinanceCubes( int companyId ) throws Exception {
      FinanceCubePK fcPK = this.mCheckpoint.queryFinanceCubeToRebuild();
      if(fcPK != null) {
         this.rebuildFinanceCube(fcPK.getFinanceCubeId(), this.mCheckpoint.getStepNumber(), companyId );
      }

      return this.mCheckpoint;
   }

   private void rebuildFinanceCube(int financeCubeId, int stepNumber) {
	   this.rebuildFinanceCube( financeCubeId, stepNumber, 0);
   }
   private void rebuildFinanceCube(int financeCubeId, int stepNumber, int companyId) {
      boolean globalMappedModel = isGlobalMappedModel(financeCubeId);
       
      Timer timer = this.mLog.isDebugEnabled()?new Timer(this.mLog):null;
      CallableStatement stmt = null;
      try {         
         if (companyId > 0 || globalMappedModel) {
              stmt = this.getConnection().prepareCall("{ call cube_rebuild.runStepGlobal(?,?,?) }");
         } else {
             stmt = this.getConnection().prepareCall("{ call cube_rebuild.runStep(?,?,?) }");
         }
         stmt.setInt(1, stepNumber);
         stmt.setInt(2, this.getTaskId());
         stmt.setInt(3, financeCubeId);
         stmt.execute();
      } catch (SQLException var9) {
         var9.printStackTrace();
         throw new RuntimeException(var9);
      } finally {
         if(timer != null) {
            timer.logDebug("rebuildFinanceCube");
         }

         this.closeStatement(stmt);
         this.closeConnection();
      }

   }

   private void deleteUnusedDataTypes(int financeCubeId) {
      Timer timer = this.mLog.isDebugEnabled()?new Timer(this.mLog):null;
      CallableStatement stmt = null;

      try {
         stmt = this.getConnection().prepareCall("{ call cube_rebuild.deleteUnusedDataTypes(?,?) }");
         stmt.setInt(1, this.getTaskId());
         stmt.setInt(2, financeCubeId);
         stmt.execute();
      } catch (SQLException var8) {
         var8.printStackTrace();
         throw new RuntimeException(var8);
      } finally {
         if(timer != null) {
            timer.logDebug("deleteUnusedDataTypes");
         }

         this.closeStatement(stmt);
         this.closeConnection();
      }

   }

   private void rebuildMeasureParents(int financeCubeId, List<FinanceCubeDataTypeUpdate> dataTypeUpdates) {
      Timer timer = this.mLog.isDebugEnabled()?new Timer(this.mLog):null;
      StringBuffer dataTypeIds = new StringBuffer();
      Iterator stmt = dataTypeUpdates.iterator();

      while(stmt.hasNext()) {
         FinanceCubeDataTypeUpdate e = (FinanceCubeDataTypeUpdate)stmt.next();
         if(e.getAction() == 2) {
            if(dataTypeIds.length() > 0) {
               dataTypeIds.append(' ');
            }

            dataTypeIds.append(String.valueOf(e.getDataTypeRef().getDataTypePK().getDataTypeId()));
         }
      }

      this.mLog.debug("rebuildMeasureParents", "dataTypeIds=" + dataTypeIds.toString());
      CallableStatement stmt1 = null;

      try {
         stmt1 = this.getConnection().prepareCall("{ call cube_rebuild.rebuildMeasureParents(?,?,?) }");
         stmt1.setInt(1, this.getTaskId());
         stmt1.setInt(2, financeCubeId);
         stmt1.setString(3, dataTypeIds.toString());
         stmt1.execute();
      } catch (SQLException var10) {
         var10.printStackTrace();
         throw new RuntimeException(var10);
      } finally {
         if(timer != null) {
            timer.logDebug("rebuildMeasureParents");
         }

         this.closeStatement(stmt1);
         this.closeConnection();
      }

   }

   private void createExternalViews(int financeCubeId) {
      Timer timer = this.mLog.isDebugEnabled()?new Timer(this.mLog):null;
      CallableStatement stmt = null;

      try {
         stmt = this.getConnection().prepareCall("{ call extSysAccessor.createExternalViews(?,?) }");
         stmt.setInt(1, this.getTaskId());
         stmt.setInt(2, financeCubeId);
         stmt.execute();
      } catch (SQLException var8) {
         var8.printStackTrace();
         throw new RuntimeException(var8);
      } finally {
         if(timer != null) {
            timer.logDebug("createExternalViews");
         }

         this.closeStatement(stmt);
         this.closeConnection();
      }

   }

   public ChangeManagementCheckPoint processImportFinanceCube() throws Exception {
      FinanceCubePK fcPK = this.mCheckpoint.queryFinanceCubeToImport();
      if(fcPK != null) {
         ExternalSystemDAO dao = new ExternalSystemDAO();
         int externalSystemType = dao.getExtSysTypeForCube(fcPK.getFinanceCubeId());
         if(this.mCheckpoint.getStepNumber() == 0) {
            CubeUpdateEngine engine = new CubeUpdateEngine();
            engine.setFinanceCubeId(fcPK.getFinanceCubeId());
            engine.setFinanceCubeHasDataFlag();
         }

         dao.importValues(externalSystemType, this.mCheckpoint.getStepNumber(), this.getTaskId(), fcPK.getFinanceCubeId());
      }

      return this.mCheckpoint;
   }

   public ChangeManagementCheckPoint processRecreateExportViews() throws Exception {
      FinanceCubePK fcPK = this.mCheckpoint.queryRecreateExportView();
      if(fcPK != null) {
         ExternalSystemDAO dao = new ExternalSystemDAO();
         dao.createExternalViews(this.getTaskId(), fcPK.getFinanceCubeId());
      }

      return this.mCheckpoint;
   }

   public ChangeManagementCheckPoint processTerminatePhase() throws Exception {
      this.mLog.info("processTerminatePhase", "BEGIN");
      this.eraseCMRequests();
      this.unlockModel(this.mRequest.getModelRef());
      this.mLog.info("processTerminatePhase", "END");
      return null;
   }

   private ChangeMgmtPK insertRequest(ModelRef model, XMLGregorianCalendar timestamp, String sourceSystem, String xmlRequest) throws ValidationException, EJBException {
      ChangeMgmtEditorSessionServer server = new ChangeMgmtEditorSessionServer(this.getInitialContext(), true);
      ChangeMgmtEditorSessionSSO sso = server.getNewItemData();
      ChangeMgmtImpl cm = sso.getEditorData();
      cm.setCreatedTime(new Timestamp(timestamp.toGregorianCalendar().getTime().getTime()));
      cm.setRelatedModelRef(model);
      cm.setSourceSystem(sourceSystem);
      cm.setXmlText(xmlRequest);
      Object cmPK = null;

      try {
         server.insert(cm);
      } catch (Exception var10) {
         throw new CPException(var10.getMessage(), var10);
      }

      this.sendEntityEventMessage("ChangeMgmt", (PrimaryKey)cmPK, 1);
      return (ChangeMgmtPK)cmPK;
   }

   private Object[] validateDimensionAmend(String visId) throws ValidationException {
      AllDimensionsELO allDimensions = this.getDimensionAccessor().getAllDimensions();
      EntityList row = allDimensions.getRowData(visId, "Dimension");
      if(row == null) {
         throw new ValidationException("Unable to locate Dimension[" + visId + "]");
      } else {
         DimensionRef dimension = (DimensionRef)row.getValueAt(0, "Dimension");
         ModelRef model = (ModelRef)row.getValueAt(0, "Model");
         if(model == null) {
            throw new ValidationException("Dimension [" + visId + "] is not associated with a Model");
         } else {
            return new Object[]{model, dimension};
         }
      }
   }

   private ModelRef validateValueImport(int financeCubeId) throws ValidationException {
      ModelDAO modelDAO = new ModelDAO();
      FinanceCubeCK fcCK = modelDAO.getFinanceCubeCK(new FinanceCubePK(financeCubeId));
      if(fcCK == null) {
         throw new ValidationException("Unable to locate model for financeCubeId=" + financeCubeId);
      } else {
         ModelEVO modelEVO = modelDAO.getDetails(fcCK.getModelPK(), "");
         return modelEVO.getEntityRef();
      }
   }

   private ModelRef validateFinanceCubeChangeManagement(String modelVisId, String financeCubeVisId) throws ValidationException {
      ModelDAO modelDAO = new ModelDAO();
      AllModelsELO allModelsELO = modelDAO.getAllModels();
      ModelRef modelRef = null;

      for(int fcDAO = 0; fcDAO < allModelsELO.getNumRows(); ++fcDAO) {
         if(((ModelRef)allModelsELO.getValueAt(fcDAO, "Model")).getNarrative().equals(modelVisId)) {
            modelRef = (ModelRef)allModelsELO.getValueAt(fcDAO, "Model");
            break;
         }
      }

      if(modelRef == null) {
         throw new ValidationException("Unable to locate model:" + modelVisId);
      } else {
         FinanceCubeDAO var10 = new FinanceCubeDAO();
         FinanceCubesForModelELO cubes = var10.getFinanceCubesForModel(((ModelRefImpl)modelRef).getModelPK().getModelId());
         FinanceCubeRef financeCubeRef = null;

         for(int row = 0; row < cubes.getNumRows(); ++row) {
            if(((FinanceCubeRef)cubes.getValueAt(row, "FinanceCube")).getNarrative().equals(financeCubeVisId)) {
               financeCubeRef = (FinanceCubeRef)cubes.getValueAt(row, "FinanceCube");
               break;
            }
         }

         if(financeCubeRef == null) {
            throw new ValidationException("Unable to locate finance cube[" + financeCubeVisId + "] for model[" + modelVisId + "]");
         } else {
            return modelRef;
         }
      }
   }

   private void eraseCMRequests() throws Exception {
      ChangeMgmtDAO dao = new ChangeMgmtDAO();
      Iterator cqIter = this.mRequest.getRequests().iterator();

      while(cqIter.hasNext()) {
         ChangeMgmtPK pk = (ChangeMgmtPK)cqIter.next();
         if(dao.exists(pk)) {
            this.getChangeMgmtAccessor().remove(pk);
            this.sendEntityEventMessage("ChangeMgmt", pk, 2);
         }
      }

   }

   public void registerDimension(DimensionPK dimensionPK) throws Exception {
      this.mCheckpoint.addDimensionPK(dimensionPK);
   }

   public void registerFinanceCubeForRebuild(FinanceCubePK financeCubePK) throws Exception {
      if(!this.mCheckpoint.getRebuildCubes().contains(financeCubePK)) {
         this.mTaskLog.log("rebuild needed for " + financeCubePK);
         this.mCheckpoint.registerFinanceCubeForRebuild(financeCubePK);
      }

   }

   public void registerFinanceCubeDataTypeUpdate(FinanceCubeRef fcRef, int updateType, DataTypeRef dataTypeRef, boolean[] rollUpRules) throws Exception {
      FinanceCubeUpdate fcu = null;
      Iterator finCubeUpdate = this.mCheckpoint.getFinanceCubeUpdates().iterator();

      while(finCubeUpdate.hasNext()) {
         FinanceCubeUpdate o = (FinanceCubeUpdate)finCubeUpdate.next();
         if(o.getFinanceCubeRef().equals(fcRef)) {
            fcu = o;
            break;
         }
      }

      if(fcu == null) {
         fcu = new FinanceCubeUpdate((FinanceCubeRefImpl)fcRef);
         this.mCheckpoint.getFinanceCubeUpdates().add(fcu);
         this.mTaskLog.log("dataType changes for " + fcu.getFinanceCubeRef().getFinanceCubePK());
      }

      FinanceCubeDataTypeUpdate finCubeUpdate1 = new FinanceCubeDataTypeUpdate((DataTypeRefImpl)dataTypeRef, updateType, rollUpRules);
      fcu.getDataTypeUpdateActions().add(finCubeUpdate1);
   }

   public boolean isFinanceCubeRegisteredForRebuild(FinanceCubePK cubePK) {
      return this.mCheckpoint.isFinanceCubeRegisteredForRebuild(cubePK);
   }

   public void registerExternalImport(FinanceCubePK financeCubePK, String sourceSystem) throws Exception {
      this.mTaskLog.log("import needed for " + financeCubePK + " extSys=" + sourceSystem);
      this.mCheckpoint.addExternalImport(financeCubePK, sourceSystem);
   }

   public void registerExportView(FinanceCubePK financeCubePK) throws Exception {
      this.mTaskLog.log("export view needed for " + financeCubePK);
      this.mCheckpoint.addRecreateView(financeCubePK);
   }

   private ChangeMgmtAccessor getChangeMgmtAccessor() {
      if(this.mChangeMgmtAccessor == null) {
         this.mChangeMgmtAccessor = new ChangeMgmtAccessor(this.getInitialContext());
      }

      return this.mChangeMgmtAccessor;
   }

   private DimensionAccessor getDimensionAccessor() {
      if(this.mDimensionAccessor == null) {
         this.mDimensionAccessor = new DimensionAccessor(this.getInitialContext());
      }

      return this.mDimensionAccessor;
   }

   private ModelAccessor getModelAccessor() {
      if(this.mModelAccessor == null) {
         this.mModelAccessor = new ModelAccessor(this.getInitialContext());
      }

      return this.mModelAccessor;
   }

   private InitialContext getInitialContext() {
      return this.mInitialContext;
   }

   public void setInitialContext(InitialContext initialContext) {
      this.mInitialContext = initialContext;
   }

   public ModelRef getModelRef() {
      return this.mRequest.getModelRef();
   }

   private void sendEntityEventMessage(String tableName, PrimaryKey pk, int changeType) {
      try {
         JmsConnectionImpl e = new JmsConnectionImpl(this.mInitialContext, 3, "entityEventTopic");
         e.createSession();
         EntityEventMessage em = new EntityEventMessage(tableName, pk, changeType, this.getClass().getName());
         this.mLog.debug("update", "sending event message: " + em.toString());
         e.send(em);
         e.closeSession();
         e.closeConnection();
      } catch (CPException var6) {
         var6.printStackTrace();
      }

   }

   public ChangeManagementEvent compareDimWithExternalSystem(int dimensionId, int extSysType) throws Exception {
	   if( this.companyId == 0 )
		   return (new ExtSysDimensionComparator(this.getTaskId(), this.mLog, this.mTaskLog)).run(dimensionId, extSysType, 0);
	   else
		   return (new ExtSysDimensionComparator(this.getTaskId(), this.mLog, this.mTaskLog)).run(dimensionId, extSysType, this.companyId );
   }

   public void writeReportLine(String msg) {
      this.mReportWriter.getTaskReport().addPlain(msg);
   }

   public int getReportType() {
      return this.mReportWriter.getReportType();
   }

   public void registerCalendarLeafLevelUpdate(CalendarLeafLevelUpdate cllu) {
      this.mCheckpoint.registerCalendarLeafLevelUpdate(cllu);
   }

   public void registerFormulaForUndeploy(FinanceCubeRef financeCubeRef, CubeFormulaRef cubeFormulaRef) {
      ChangeManagementCheckPoint.FormulaUpdate update = new ChangeManagementCheckPoint.FormulaUpdate(ChangeManagementCheckPoint.FormulaUpdateType.UNDEPLOY, financeCubeRef, cubeFormulaRef);
      this.mCheckpoint.registerFormulaUpdate(update);
   }

   public CPConnection getCPConnection() {
      if(this.mCPConnection == null) {
         try {
            UserEVO e = this.getUserEVO();
            String connectionURL = SystemPropertyHelper.queryStringSystemProperty(this.getConnection(), "WEB: Connection URL", (String)null);
            String password = Cryptography.decrypt(e.getPasswordBytes(), "fc30");
            this.mCPConnection = DriverManager.getConnection(connectionURL, e.getName(), password, true, false, ConnectionContext.CHANGE_MANAGEMENT);
         } catch (Exception var4) {
            throw new CPException(var4.getMessage(), var4);
         }
      }

      return this.mCPConnection;
   }

   private UserEVO getUserEVO() throws ValidationException {
      if(this.mUserEVO == null) {
         this.mUserEVO = (new UserDAO()).getDetails(new UserPK(this.getUserId()), "");
      }

      return this.mUserEVO;
   }

   public void closeCPConnection() {
      if(this.mCPConnection != null) {
         this.mCPConnection.close();
         this.mCPConnection = null;
      }

   }

   public int getUserId() {
      return this.mUserId;
   }

   public void setUserId(int userId) {
      this.mUserId = userId;
   }

   public TaskMessageLogger getTaskMessageLogger() {
      return this.mTaskLog;
   }
   
   public static class CMRequestMap {

	   private ModelRef mModelRef;
	   private List mEvents;
	   // $FF: synthetic field
	   final ChangeMgmtEngine this$0;


	   public CMRequestMap(ChangeMgmtEngine var1, ModelRef modelRef) {
	      this.this$0 = var1;
	      this.mEvents = new ArrayList();
	      this.mModelRef = modelRef;
	   }

	   public ModelRef getModelRef() {
	      return this.mModelRef;
	   }

	   public List getEvents() {
	      return this.mEvents;
	   }

	   public void addEvent(ChangeManagementEvent event) {
	      this.mEvents.add(event);
	   }
	}
   
   public static class ChangeMgmtEngine$1 {

	   // $FF: synthetic field
	   static final int[] $SwitchMap$com$cedar$cp$ejb$base$async$cm$ChangeManagementCheckPoint$FormulaUpdateType = new int[ChangeManagementCheckPoint.FormulaUpdateType.values().length];


	   static {
	      try {
	         $SwitchMap$com$cedar$cp$ejb$base$async$cm$ChangeManagementCheckPoint$FormulaUpdateType[ChangeManagementCheckPoint.FormulaUpdateType.UNDEPLOY.ordinal()] = 1;
	      } catch (NoSuchFieldError var2) {
	         ;
	      }

	      try {
	         $SwitchMap$com$cedar$cp$ejb$base$async$cm$ChangeManagementCheckPoint$FormulaUpdateType[ChangeManagementCheckPoint.FormulaUpdateType.REBUILD.ordinal()] = 2;
	      } catch (NoSuchFieldError var1) {
	         ;
	      }

	   }
	}
   
   public ArrayList<String> getAllFinanceCubeIdsGlobalImp(int mappedModelId) {
	   ExternalSystemDAO dao = new ExternalSystemDAO();
	   ArrayList<String> idsList = dao.getAllFinanceCubeIdsGlobalImp( mappedModelId );

	   return idsList;
   }
   
	public ChangeManagementCheckPoint processImportGlobalFinanceCube( Map<String, Integer> company ) throws Exception {
		FinanceCubePK fcPK = this.mCheckpoint.queryFinanceCubeToImport();
		if (fcPK != null) {
			ExternalSystemDAO dao = new ExternalSystemDAO();
			int externalSystemType = dao.getExtSysTypeForCube( company.get("financeCubeId") ); //fcPK.getFinanceCubeId()
			if (this.mCheckpoint.getStepNumber() == 0) {
				CubeUpdateEngine engine = new CubeUpdateEngine();
				engine.setFinanceCubeId( company.get("financeCubeId") );
				engine.setFinanceCubeHasDataFlag();
			}

			dao.importValues(externalSystemType, this.mCheckpoint.getStepNumber(), this.getTaskId(), company.get("financeCubeId") , company.get("companyId") );
		}

		return this.mCheckpoint;
	}
	
    public ChangeManagementCheckPoint processImportFinanceCube(int company) throws Exception {
        FinanceCubePK fcPK = this.mCheckpoint.queryFinanceCubeToImport();
        if (fcPK != null) {
            ExternalSystemDAO dao = new ExternalSystemDAO();
            int externalSystemType = dao.getExtSysTypeForCube(fcPK.getFinanceCubeId());
            if (this.mCheckpoint.getStepNumber() == 0) {
                CubeUpdateEngine engine = new CubeUpdateEngine();
                engine.setFinanceCubeId(fcPK.getFinanceCubeId());
                engine.setFinanceCubeHasDataFlag();
            }

            dao.importValuesGlobal(externalSystemType, this.mCheckpoint.getStepNumber(), this.getTaskId(), fcPK.getFinanceCubeId(), company);
        }

        return this.mCheckpoint;
    }
    
    private boolean isGlobalMappedModel(int financeCubeId) {
        PreparedStatement stmt = null;
        ResultSet resultSet = null;
        boolean returnValue;
        try {
            stmt = getConnection().prepareStatement("select mm.GLOBAL   from   MAPPED_MODEL mm, MAPPED_FINANCE_CUBE mfc   where   mm.MAPPED_MODEL_ID=mfc.MAPPED_MODEL_ID and mfc.FINANCE_CUBE_ID = ?");
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
}
