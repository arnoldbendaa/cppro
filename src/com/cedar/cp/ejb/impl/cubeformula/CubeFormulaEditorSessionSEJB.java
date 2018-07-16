// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:08:11
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.impl.cubeformula;

import com.cedar.cp.api.base.EntityList;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.cubeformula.FormulaDeploymentLine;
import com.cedar.cp.api.datatype.DataTypeRef;
import com.cedar.cp.api.dimension.DimensionRef;
import com.cedar.cp.api.model.FinanceCubeRef;
import com.cedar.cp.api.model.ModelRef;
import com.cedar.cp.dto.base.EntityEventMessage;
import com.cedar.cp.dto.base.PrimaryKey;
import com.cedar.cp.dto.cubeformula.CubeFormulaCK;
import com.cedar.cp.dto.cubeformula.CubeFormulaEditorSessionCSO;
import com.cedar.cp.dto.cubeformula.CubeFormulaEditorSessionSSO;
import com.cedar.cp.dto.cubeformula.CubeFormulaImpl;
import com.cedar.cp.dto.cubeformula.CubeFormulaRefImpl;
import com.cedar.cp.dto.cubeformula.FormulaDeploymentLineImpl;
import com.cedar.cp.dto.cubeformula.FormulaRebuildTaskRequest;
import com.cedar.cp.dto.datatype.AllDataTypesELO;
import com.cedar.cp.dto.datatype.DataTypeRefImpl;
import com.cedar.cp.dto.dimension.StructureElementKeyImpl;
import com.cedar.cp.dto.dimension.StructureElementPK;
import com.cedar.cp.dto.dimension.StructureElementRefImpl;
import com.cedar.cp.dto.model.AllFinanceCubesELO;
import com.cedar.cp.dto.model.FinanceCubeCK;
import com.cedar.cp.dto.model.FinanceCubePK;
import com.cedar.cp.dto.model.FinanceCubeRefImpl;
import com.cedar.cp.dto.model.ModelDimensionsELO;
import com.cedar.cp.dto.model.ModelRefImpl;
import com.cedar.cp.ejb.base.cube.formula.FormulaCompiler;
import com.cedar.cp.ejb.base.cube.formula.FormulaDAO;
import com.cedar.cp.ejb.base.cube.formula.tablemeta.MetaTableManager;
import com.cedar.cp.ejb.impl.base.AbstractSession;
import com.cedar.cp.ejb.impl.cubeformula.CubeFormulaDAO;
import com.cedar.cp.ejb.impl.cubeformula.CubeFormulaEVO;
import com.cedar.cp.ejb.impl.cubeformula.CubeFormulaPackageDAO;
import com.cedar.cp.ejb.impl.cubeformula.FormulaDeploymentDtEVO;
import com.cedar.cp.ejb.impl.cubeformula.FormulaDeploymentEntryEVO;
import com.cedar.cp.ejb.impl.cubeformula.FormulaDeploymentLineEVO;
import com.cedar.cp.ejb.impl.datatype.DataTypeDAO;
import com.cedar.cp.ejb.impl.dimension.StructureElementDAO;
import com.cedar.cp.ejb.impl.model.FinanceCubeDAO;
import com.cedar.cp.ejb.impl.model.FinanceCubeEVO;
import com.cedar.cp.ejb.impl.model.ModelAccessor;
import com.cedar.cp.ejb.impl.model.ModelDAO;
import com.cedar.cp.ejb.impl.model.ModelEVO;
import com.cedar.cp.ejb.impl.systemproperty.SystemPropertyDAO;
import com.cedar.cp.util.Log;
import com.cedar.cp.util.Pair;
import com.cedar.cp.util.Timer;
import com.cedar.cp.util.common.JmsConnectionImpl;
import com.cedar.cp.util.task.TaskMessageFactory;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import javax.ejb.EJBException;
import javax.ejb.SessionContext;

public class CubeFormulaEditorSessionSEJB extends AbstractSession {

   private static final String DEPENDANTS_FOR_GET_ITEM_DATA = "<5><6><7>";
   private static final String DEPENDANTS_FOR_INSERT = "";
   private static final String DEPENDANTS_FOR_COPY = "<5><6><7>";
   private static final String DEPENDANTS_FOR_UPDATE = "<5><6><7>";
   private static final String DEPENDANTS_FOR_DELETE = "<5><6><7>";
   private int mNextId = -1;
   private transient Log mLog = new Log(this.getClass());
   private transient SessionContext mSessionContext;
   private transient ModelAccessor mModelAccessor;
   private CubeFormulaEditorSessionSSO mSSO;
   private CubeFormulaCK mThisTableKey;
   private ModelEVO mModelEVO;
   private FinanceCubeEVO mFinanceCubeEVO;
   private CubeFormulaEVO mCubeFormulaEVO;


   public CubeFormulaEditorSessionSSO getItemData(int userId, Object paramKey) throws ValidationException, EJBException {
      this.setUserId(userId);
      if(this.mLog.isDebugEnabled()) {
         this.mLog.debug("getItemData", paramKey);
      }

      Timer timer = this.mLog.isInfoEnabled()?new Timer(this.mLog):null;
      this.mThisTableKey = (CubeFormulaCK)paramKey;

      CubeFormulaEditorSessionSSO e;
      try {
         this.mModelEVO = this.getModelAccessor().getDetails(this.mThisTableKey, "<5><6><7>");
         this.mFinanceCubeEVO = this.mModelEVO.getFinanceCubesItem(this.mThisTableKey.getFinanceCubePK());
         this.mCubeFormulaEVO = this.mFinanceCubeEVO.getCubeFormulaItem(this.mThisTableKey.getCubeFormulaPK());
         this.makeItemData();
         e = this.mSSO;
      } catch (ValidationException var10) {
         throw var10;
      } catch (EJBException var11) {
         if(var11.getCause() instanceof ValidationException) {
            throw (ValidationException)var11.getCause();
         }

         throw var11;
      } catch (Exception var12) {
         var12.printStackTrace();
         throw new EJBException(var12.getMessage(), var12);
      } finally {
         this.setUserId(0);
         if(timer != null) {
            timer.logInfo("getItemData", this.mThisTableKey);
         }

      }

      return e;
   }

   private void makeItemData() throws Exception {
      this.mSSO = new CubeFormulaEditorSessionSSO();
      CubeFormulaImpl editorData = this.buildCubeFormulaEditData(this.mThisTableKey);
      this.completeGetItemData(editorData);
      this.mSSO.setEditorData(editorData);
   }

   private void completeGetItemData(CubeFormulaImpl editorData) throws Exception {}

   private CubeFormulaImpl buildCubeFormulaEditData(Object thisKey) throws Exception {
      CubeFormulaImpl editorData = new CubeFormulaImpl(thisKey);
      editorData.setVisId(this.mCubeFormulaEVO.getVisId());
      editorData.setDescription(this.mCubeFormulaEVO.getDescription());
      editorData.setFormulaText(this.mCubeFormulaEVO.getFormulaText());
      editorData.setDeploymentInd(this.mCubeFormulaEVO.getDeploymentInd());
      editorData.setFormulaType(this.mCubeFormulaEVO.getFormulaType());
      editorData.setFinanceCubeRef(new FinanceCubeRefImpl(this.mFinanceCubeEVO.getPK(), this.mFinanceCubeEVO.getVisId()));
      editorData.setModelRef(new ModelRefImpl(this.mModelEVO.getPK(), this.mModelEVO.getVisId()));
      this.completeCubeFormulaEditData(editorData);
      return editorData;
   }

   private void completeCubeFormulaEditData(CubeFormulaImpl editorData) throws Exception {
      this.load(this.mCubeFormulaEVO, editorData);
      editorData.setFinanceCubeFormulaEnabled(this.mFinanceCubeEVO.getCubeFormulaEnabled());
   }

   public CubeFormulaEditorSessionSSO getNewItemData(int userId) throws EJBException {
      this.mLog.debug("getNewItemData");
      this.setUserId(userId);
      Timer timer = this.mLog.isInfoEnabled()?new Timer(this.mLog):null;

      CubeFormulaEditorSessionSSO var4;
      try {
         this.mSSO = new CubeFormulaEditorSessionSSO();
         CubeFormulaImpl e = new CubeFormulaImpl((Object)null);
         this.completeGetNewItemData(e);
         this.mSSO.setEditorData(e);
         var4 = this.mSSO;
      } catch (EJBException var9) {
         throw var9;
      } catch (Exception var10) {
         var10.printStackTrace();
         throw new EJBException(var10.getMessage(), var10);
      } finally {
         this.setUserId(0);
         if(timer != null) {
            timer.logInfo("getNewItemData", "");
         }

      }

      return var4;
   }

   private void completeGetNewItemData(CubeFormulaImpl editorData) throws Exception {}

   public CubeFormulaCK insert(CubeFormulaEditorSessionCSO cso) throws ValidationException, EJBException {
      this.mLog.debug("insert");
      Timer timer = this.mLog.isInfoEnabled()?new Timer(this.mLog):null;
      this.setUserId(cso.getUserId());
      CubeFormulaImpl editorData = cso.getEditorData();

      try {
         this.mModelEVO = this.getModelAccessor().getDetails(editorData.getFinanceCubeRef(), "");
         this.mFinanceCubeEVO = this.mModelEVO.getFinanceCubesItem(((FinanceCubeCK)editorData.getFinanceCubeRef().getPrimaryKey()).getFinanceCubePK());
         this.mCubeFormulaEVO = new CubeFormulaEVO();
         this.mCubeFormulaEVO.setVisId(editorData.getVisId());
         this.mCubeFormulaEVO.setDescription(editorData.getDescription());
         this.mCubeFormulaEVO.setFormulaText(editorData.getFormulaText());
         this.mCubeFormulaEVO.setDeploymentInd(editorData.isDeploymentInd());
         this.mCubeFormulaEVO.setFormulaType(editorData.getFormulaType());
         this.updateCubeFormulaRelationships(editorData);
         this.completeInsertSetup(editorData);
         this.validateInsert();
         this.mFinanceCubeEVO.addCubeFormulaItem(this.mCubeFormulaEVO);
         this.mModelEVO = this.getModelAccessor().setAndGetDetails(this.mModelEVO, "<0><4>");
         this.mFinanceCubeEVO = this.mModelEVO.getFinanceCubesItem(((FinanceCubeCK)editorData.getFinanceCubeRef().getPrimaryKey()).getFinanceCubePK());
         Iterator e = this.mFinanceCubeEVO.getCubeFormula().iterator();

         while(true) {
            if(e.hasNext()) {
               this.mCubeFormulaEVO = (CubeFormulaEVO)e.next();
               if(!this.mCubeFormulaEVO.insertPending()) {
                  continue;
               }
            }

            this.insertIntoAdditionalTables(editorData, true);
            this.sendEntityEventMessage("CubeFormula", this.mCubeFormulaEVO.getPK(), 1);
            CubeFormulaCK var5 = new CubeFormulaCK(this.mModelEVO.getPK(), this.mFinanceCubeEVO.getPK(), this.mCubeFormulaEVO.getPK());
            return var5;
         }
      } catch (ValidationException var11) {
         throw new EJBException(var11);
      } catch (EJBException var12) {
         throw var12;
      } catch (Exception var13) {
         var13.printStackTrace();
         throw new EJBException(var13.getMessage(), var13);
      } finally {
         this.setUserId(0);
         if(timer != null) {
            timer.logInfo("insert", "");
         }

      }
   }

   private void updateCubeFormulaRelationships(CubeFormulaImpl editorData) throws ValidationException {}

   private void completeInsertSetup(CubeFormulaImpl editorData) throws Exception {
      this.update(editorData, this.mCubeFormulaEVO);
   }

   private void insertIntoAdditionalTables(CubeFormulaImpl editorData, boolean isInsert) throws Exception {
      this.getModelAccessor().flush(this.mModelEVO.getPK());
      if(editorData.isDeploymentInd()) {
         FinanceCubeRefImpl fcRef = (FinanceCubeRefImpl)editorData.getFinanceCubeRef();
         if(!editorData.isDeploymentInd()) {
            this.testCompileFormula(fcRef.getFinanceCubePK().getFinanceCubeId(), this.mCubeFormulaEVO.getPK().getCubeFormulaId(), editorData.getFormulaText(), editorData.getFormulaType());
         } else {
            this.compileFormula(fcRef.getFinanceCubePK().getFinanceCubeId(), this.mCubeFormulaEVO.getPK().getCubeFormulaId(), editorData.getFormulaText(), editorData.getFormulaType());
         }
      }

   }

   private void validateInsert() throws ValidationException {}

   public CubeFormulaCK copy(CubeFormulaEditorSessionCSO cso) throws ValidationException, EJBException {
      this.mLog.debug("copy");
      Timer timer = this.mLog.isInfoEnabled()?new Timer(this.mLog):null;
      this.setUserId(cso.getUserId());
      CubeFormulaImpl editorData = cso.getEditorData();
      this.mThisTableKey = (CubeFormulaCK)editorData.getPrimaryKey();

      try {
         this.mModelEVO = this.getModelAccessor().getDetails(this.mThisTableKey, "<5><6><7>");
         this.mFinanceCubeEVO = this.mModelEVO.getFinanceCubesItem(this.mThisTableKey.getFinanceCubePK());
         CubeFormulaEVO e = this.mFinanceCubeEVO.getCubeFormulaItem(this.mThisTableKey.getCubeFormulaPK());
         this.mCubeFormulaEVO = e.deepClone();
         this.mCubeFormulaEVO.setVisId(editorData.getVisId());
         this.mCubeFormulaEVO.setDescription(editorData.getDescription());
         this.mCubeFormulaEVO.setFormulaText(editorData.getFormulaText());
         this.mCubeFormulaEVO.setDeploymentInd(editorData.isDeploymentInd());
         this.mCubeFormulaEVO.setFormulaType(editorData.getFormulaType());
         this.updateCubeFormulaRelationships(editorData);
         this.completeCopySetup(editorData);
         this.validateCopy();
         FinanceCubeCK parentKey = (FinanceCubeCK)editorData.getFinanceCubeRef().getPrimaryKey();
         if(!parentKey.getFinanceCubePK().equals(this.mFinanceCubeEVO.getPK())) {
            this.mModelEVO = this.getModelAccessor().getDetails(parentKey, "<0>");
            this.mFinanceCubeEVO = this.mModelEVO.getFinanceCubesItem(parentKey.getFinanceCubePK());
         }

         this.mCubeFormulaEVO.prepareForInsert((FinanceCubeEVO)null);
         this.mFinanceCubeEVO.addCubeFormulaItem(this.mCubeFormulaEVO);
         this.mModelEVO = this.getModelAccessor().setAndGetDetails(this.mModelEVO, "<0><4><5><6><7>");
         this.mFinanceCubeEVO = this.mModelEVO.getFinanceCubesItem(parentKey.getFinanceCubePK());
         Iterator iter = this.mFinanceCubeEVO.getCubeFormula().iterator();

         while(true) {
            if(iter.hasNext()) {
               this.mCubeFormulaEVO = (CubeFormulaEVO)iter.next();
               if(!this.mCubeFormulaEVO.insertPending()) {
                  continue;
               }
            }

            this.mThisTableKey = new CubeFormulaCK(this.mModelEVO.getPK(), this.mFinanceCubeEVO.getPK(), this.mCubeFormulaEVO.getPK());
            this.insertIntoAdditionalTables(editorData, false);
            this.sendEntityEventMessage("CubeFormula", this.mCubeFormulaEVO.getPK(), 1);
            CubeFormulaCK var7 = this.mThisTableKey;
            return var7;
         }
      } catch (ValidationException var13) {
         throw new EJBException(var13);
      } catch (EJBException var14) {
         throw var14;
      } catch (Exception var15) {
         var15.printStackTrace();
         throw new EJBException(var15);
      } finally {
         this.setUserId(0);
         if(timer != null) {
            timer.logInfo("copy", this.mThisTableKey);
         }

      }
   }

   private void validateCopy() throws ValidationException {}

   private void completeCopySetup(CubeFormulaImpl editorData) throws Exception {}

   public void update(CubeFormulaEditorSessionCSO cso) throws ValidationException, EJBException {
      this.mLog.debug("update");
      Timer timer = this.mLog.isInfoEnabled()?new Timer(this.mLog):null;
      this.setUserId(cso.getUserId());
      CubeFormulaImpl editorData = cso.getEditorData();
      this.mThisTableKey = (CubeFormulaCK)editorData.getPrimaryKey();

      try {
         this.mModelEVO = this.getModelAccessor().getDetails(this.mThisTableKey, "<5><6><7>");
         this.mFinanceCubeEVO = this.mModelEVO.getFinanceCubesItem(this.mThisTableKey.getFinanceCubePK());
         this.mCubeFormulaEVO = this.mFinanceCubeEVO.getCubeFormulaItem(this.mThisTableKey.getCubeFormulaPK());
         this.preValidateUpdate(editorData);
         this.mCubeFormulaEVO.setVisId(editorData.getVisId());
         this.mCubeFormulaEVO.setDescription(editorData.getDescription());
         this.mCubeFormulaEVO.setFormulaText(editorData.getFormulaText());
         this.mCubeFormulaEVO.setDeploymentInd(editorData.isDeploymentInd());
         this.mCubeFormulaEVO.setFormulaType(editorData.getFormulaType());
         this.updateCubeFormulaRelationships(editorData);
         this.completeUpdateSetup(editorData);
         this.postValidateUpdate();
         this.getModelAccessor().setDetails(this.mModelEVO);
         this.updateAdditionalTables(editorData);
         this.sendEntityEventMessage("CubeFormula", this.mCubeFormulaEVO.getPK(), 3);
      } catch (ValidationException var10) {
         throw new EJBException(var10);
      } catch (EJBException var11) {
         throw var11;
      } catch (Exception var12) {
         var12.printStackTrace();
         throw new EJBException(var12.getMessage(), var12);
      } finally {
         this.setUserId(0);
         if(timer != null) {
            timer.logInfo("update", this.mThisTableKey);
         }

      }

   }

   private void preValidateUpdate(CubeFormulaImpl editorData) throws ValidationException {}

   private void postValidateUpdate() throws ValidationException {}

   private void completeUpdateSetup(CubeFormulaImpl editorData) throws Exception {
      this.update(editorData, this.mCubeFormulaEVO);
   }

   public EntityList getOwnershipData(int userId, Object paramKey) throws EJBException {
      this.mLog.debug("getOwnershipData");
      Timer timer = this.mLog.isInfoEnabled()?new Timer(this.mLog):null;
      this.setUserId(userId);
      this.mThisTableKey = (CubeFormulaCK)paramKey;

      AllFinanceCubesELO e;
      try {
         e = this.getModelAccessor().getAllFinanceCubesForLoggedUser(userId);
      } catch (Exception var8) {
         var8.printStackTrace();
         throw new EJBException(var8.getMessage(), var8);
      } finally {
         this.setUserId(0);
         if(timer != null) {
            timer.logInfo("getOwnershipData", "");
         }

      }

      return e;
   }

   private void updateAdditionalTables(CubeFormulaImpl editorData) throws Exception {
      this.getModelAccessor().flush(this.mModelEVO.getPK());
      FinanceCubeRefImpl fcRef = (FinanceCubeRefImpl)editorData.getFinanceCubeRef();
      if(editorData.isDeploymentInd()) {
         if(editorData.isFinanceCubeFormulaEnabled()) {
            this.compileFormula(fcRef.getFinanceCubePK().getFinanceCubeId(), this.mCubeFormulaEVO.getPK().getCubeFormulaId(), editorData.getFormulaText(), editorData.getFormulaType());
         } else {
            this.testCompileFormula(fcRef.getFinanceCubePK().getFinanceCubeId(), this.mCubeFormulaEVO.getPK().getCubeFormulaId(), editorData.getFormulaText(), editorData.getFormulaType());
         }
      } else if(editorData.isFinanceCubeFormulaEnabled()) {
         this.removeFormula(fcRef.getFinanceCubePK().getFinanceCubeId(), this.mCubeFormulaEVO.getPK().getCubeFormulaId());
      }

   }

   public void delete(int userId, Object paramKey) throws ValidationException, EJBException {
      if(this.mLog.isDebugEnabled()) {
         this.mLog.debug("delete", paramKey);
      }

      Timer timer = this.mLog.isInfoEnabled()?new Timer(this.mLog):null;
      this.setUserId(userId);
      this.mThisTableKey = (CubeFormulaCK)paramKey;

      try {
         this.mModelEVO = this.getModelAccessor().getDetails(this.mThisTableKey, "<5><6><7>");
         this.mFinanceCubeEVO = this.mModelEVO.getFinanceCubesItem(this.mThisTableKey.getFinanceCubePK());
         this.mCubeFormulaEVO = this.mFinanceCubeEVO.getCubeFormulaItem(this.mThisTableKey.getCubeFormulaPK());
         this.validateDelete();
         this.deleteDataFromOtherTables();
         this.mFinanceCubeEVO.deleteCubeFormulaItem(this.mThisTableKey.getCubeFormulaPK());
         this.getModelAccessor().setDetails(this.mModelEVO);
         this.sendEntityEventMessage("CubeFormula", this.mThisTableKey.getCubeFormulaPK(), 2);
         this.removeFormula();
      } catch (ValidationException var10) {
         throw var10;
      } catch (EJBException var11) {
         throw var11;
      } catch (Exception var12) {
         var12.printStackTrace();
         throw new EJBException(var12.getMessage(), var12);
      } finally {
         this.setUserId(0);
         if(timer != null) {
            timer.logInfo("delete", this.mThisTableKey);
         }

      }

   }

   private void deleteDataFromOtherTables() throws Exception {}

   private void validateDelete() throws ValidationException, Exception {}

   public void ejbCreate() throws EJBException {}

   public void ejbRemove() {}

   public void setSessionContext(SessionContext context) {
      this.mSessionContext = context;
   }

   public void ejbActivate() {}

   public void ejbPassivate() {}

   private ModelAccessor getModelAccessor() throws Exception {
      if(this.mModelAccessor == null) {
         this.mModelAccessor = new ModelAccessor(this.getInitialContext());
      }

      return this.mModelAccessor;
   }

   private void sendEntityEventMessage(String tableName, PrimaryKey pk, int changeType) {
      try {
         JmsConnectionImpl e = new JmsConnectionImpl(this.getInitialContext(), 3, "entityEventTopic");
         e.createSession();
         EntityEventMessage em = new EntityEventMessage(tableName, pk, changeType, this.getClass().getName());
         this.mLog.debug("update", "sending event message: " + em.toString());
         e.send(em);
         e.closeSession();
         e.closeConnection();
      } catch (Exception var6) {
         var6.printStackTrace();
      }

   }

   public int issueRebuildTask(int userId, ModelRef modelRef, FinanceCubeRef targetFinanceCube, List<CubeFormulaRefImpl> cubeFormula) throws EJBException {
      try {
         FormulaRebuildTaskRequest e = new FormulaRebuildTaskRequest((FinanceCubeRefImpl)targetFinanceCube, (ModelRefImpl)modelRef, cubeFormula);
         int taskNum = TaskMessageFactory.issueNewTask(this.getInitialContext(), true, e, userId);
         this.mLog.info("issueRebuildTask", "taskId=" + taskNum);
         return taskNum;
      } catch (Exception var7) {
         var7.printStackTrace();
         this.mLog.warn("issueRebuildTask", "unable to issue task");
         throw new EJBException(var7);
      }
   }

   private void load(CubeFormulaEVO evo, CubeFormulaImpl impl) {
      DimensionRef[] dimensionRefs = this.getFinanceCubeDimensionRefs(evo.getFinanceCubeId());
      HashSet seKeys = new HashSet();
      Map dataTypesMap = this.getAllDataTypes();
      impl.setDimensionRefs(dimensionRefs);
      if(evo.getDeployments() != null) {
         Iterator seDAO = evo.getDeployments().iterator();

         int deploymentDataTypes;
         while(seDAO.hasNext()) {
            FormulaDeploymentLineEVO seList = (FormulaDeploymentLineEVO)seDAO.next();
            if(seList.getDeploymentEntries() != null) {
               Iterator seVisIds = seList.getDeploymentEntries().iterator();

               while(seVisIds.hasNext()) {
                  FormulaDeploymentEntryEVO i$ = (FormulaDeploymentEntryEVO)seVisIds.next();
                  int lineEVO = i$.getStructureId();
                  int deploymentEntries = i$.getStartSeId();
                  seKeys.add(new StructureElementKeyImpl(lineEVO, deploymentEntries));
                  if(i$.getEndSeId() != null) {
                     deploymentDataTypes = i$.getEndSeId().intValue();
                     seKeys.add(new StructureElementKeyImpl(lineEVO, deploymentDataTypes));
                  }
               }
            }
         }

         StructureElementDAO var25 = new StructureElementDAO();
         EntityList var26 = var25.queryPathToRoots(seKeys);
         HashMap var27 = new HashMap();

         for(int var28 = 0; var28 < var26.getNumRows(); ++var28) {
            var27.put(new StructureElementKeyImpl(((Integer)var26.getValueAt(var28, "StructureId")).intValue(), ((Integer)var26.getValueAt(var28, "StructureElementId")).intValue()), (String)var26.getValueAt(var28, "VisId"));
         }

         FormulaDeploymentLineImpl lineImpl;
         for(Iterator var29 = evo.getDeployments().iterator(); var29.hasNext(); impl.getDeploymentLines().add(lineImpl)) {
            FormulaDeploymentLineEVO var31 = (FormulaDeploymentLineEVO)var29.next();
            HashMap var30 = new HashMap();

            for(deploymentDataTypes = 0; deploymentDataTypes < dimensionRefs.length; ++deploymentDataTypes) {
               var30.put(dimensionRefs[deploymentDataTypes], new HashMap());
            }

            HashSet var32 = new HashSet();
            lineImpl = new FormulaDeploymentLineImpl(dimensionRefs, var31.getPK(), var31.getContext(), var31.getLineIndex(), var30, var32);
            Iterator i$1;
            DimensionRef dataTypeRef;
            boolean selectedInd;
            StructureElementRefImpl startSeRef;
            StructureElementRefImpl endSeRef;
            if(var31.getDeploymentEntries() != null) {
               for(i$1 = var31.getDeploymentEntries().iterator(); i$1.hasNext(); ((Map)var30.get(dataTypeRef)).put(new Pair(startSeRef, endSeRef), Boolean.valueOf(selectedInd))) {
                  FormulaDeploymentEntryEVO dataTypeEVO = (FormulaDeploymentEntryEVO)i$1.next();
                  dataTypeRef = dimensionRefs[dataTypeEVO.getDimSeq()];
                  int structureId = dataTypeEVO.getStructureId();
                  int startSeId = dataTypeEVO.getStartSeId();
                  selectedInd = dataTypeEVO.getSelectedInd();
                  StructureElementKeyImpl startSeKey = new StructureElementKeyImpl(structureId, startSeId);
                  startSeRef = new StructureElementRefImpl(new StructureElementPK(structureId, startSeId), (String)var27.get(startSeKey));
                  endSeRef = null;
                  if(dataTypeEVO.getEndSeId() != null) {
                     int endSeId = dataTypeEVO.getEndSeId().intValue();
                     StructureElementKeyImpl endSeKey = new StructureElementKeyImpl(structureId, endSeId);
                     endSeRef = new StructureElementRefImpl(new StructureElementPK(structureId, endSeId), (String)var27.get(endSeKey));
                  }
               }
            }

            if(var31.getDeploymentDataTypes() != null) {
               i$1 = var31.getDeploymentDataTypes().iterator();

               while(i$1.hasNext()) {
                  FormulaDeploymentDtEVO var33 = (FormulaDeploymentDtEVO)i$1.next();
                  DataTypeRef var34 = (DataTypeRef)dataTypesMap.get(Integer.valueOf(var33.getDataTypeId()));
                  if(var34 == null) {
                     throw new IllegalStateException("Failed to locate deployment data type id=" + var33.getDataTypeId());
                  }

                  var32.add(var34);
               }
            }
         }
      }

   }

   private void update(CubeFormulaImpl impl, CubeFormulaEVO evo) {
      DimensionRef[] dimensionRefs = this.getFinanceCubeDimensionRefs(((FinanceCubeRefImpl)impl.getFinanceCubeRef()).getFinanceCubePK().getFinanceCubeId());
      ArrayList newDeploymentLines = new ArrayList(impl.getDeploymentLines());
      ArrayList existingDeploymentLines = evo.getDeployments() != null?new ArrayList(evo.getDeployments()):new ArrayList();
      Iterator i$ = existingDeploymentLines.iterator();

      while(i$.hasNext()) {
         FormulaDeploymentLineEVO lineImpl = (FormulaDeploymentLineEVO)i$.next();
         FormulaDeploymentLineImpl deploymentLineId = (FormulaDeploymentLineImpl)impl.getFormulaDeploymentLine(lineImpl.getPK());
         if(deploymentLineId != null) {
            this.update(deploymentLineId, lineImpl, dimensionRefs);
            newDeploymentLines.remove(deploymentLineId);
         } else {
            evo.deleteDeploymentsItem(lineImpl.getPK());
         }
      }

      i$ = newDeploymentLines.iterator();

      while(i$.hasNext()) {
         FormulaDeploymentLine var18 = (FormulaDeploymentLine)i$.next();
         int var19 = this.getNextId();
         FormulaDeploymentLineEVO lineEVO = new FormulaDeploymentLineEVO(var19, evo.getCubeFormulaId(), var18.getLineIndex(), var18.getContext(), new ArrayList(), new ArrayList());

         for(int i$1 = 0; i$1 < dimensionRefs.length; ++i$1) {
            DimensionRef dataTypeRef = dimensionRefs[i$1];
            Map dataTypeRefImpl = (Map)var18.getDeploymentEntries().get(dataTypeRef);
            if(dataTypeRefImpl != null) {
               Iterator deploymentDataTypeEVO = dataTypeRefImpl.entrySet().iterator();

               while(deploymentDataTypeEVO.hasNext()) {
                  Entry entry = (Entry)deploymentDataTypeEVO.next();
                  StructureElementRefImpl startSeRef = (StructureElementRefImpl)((Pair)entry.getKey()).getChild1();
                  StructureElementRefImpl endSeRef = (StructureElementRefImpl)((Pair)entry.getKey()).getChild2();
                  FormulaDeploymentEntryEVO deployEntryEVO = new FormulaDeploymentEntryEVO(this.getNextId(), var19, i$1, startSeRef.getStructureElementPK().getStructureId(), startSeRef.getStructureElementPK().getStructureElementId(), ((Boolean)entry.getValue()).booleanValue(), endSeRef != null?Integer.valueOf(endSeRef.getStructureElementPK().getStructureElementId()):null);
                  lineEVO.addDeploymentEntriesItem(deployEntryEVO);
               }
            }
         }

         Iterator var21 = var18.getDeploymentDataTypes().iterator();

         while(var21.hasNext()) {
            DataTypeRef var20 = (DataTypeRef)var21.next();
            DataTypeRefImpl var22 = (DataTypeRefImpl)var20;
            FormulaDeploymentDtEVO var23 = new FormulaDeploymentDtEVO();
            var23.setFormulaDeploymentDtId(this.getNextId());
            var23.setFormulaDeploymentLineId(lineEVO.getFormulaDeploymentLineId());
            var23.setDataTypeId(var22.getDataTypePK().getDataTypeId());
            lineEVO.addDeploymentDataTypesItem(var23);
         }

         evo.addDeploymentsItem(lineEVO);
      }

   }

   private void update(FormulaDeploymentLineImpl lineImpl, FormulaDeploymentLineEVO lineEVO, DimensionRef[] dimensionRefs) {
      for(int existingDeploymentDataTypeEVOs = 0; existingDeploymentDataTypeEVOs < dimensionRefs.length; ++existingDeploymentDataTypeEVOs) {
         DimensionRef newDeploymentDataTypeRefs = dimensionRefs[existingDeploymentDataTypeEVOs];
         Map i$ = (Map)lineImpl.getDeploymentEntries().get(newDeploymentDataTypeRefs);
         HashSet newDeploymentDataType = new HashSet();
         Iterator dataTypeDeploymentEVO;
         FormulaDeploymentEntryEVO deployEntryEVO1;
         if(i$ != null) {
            for(dataTypeDeploymentEVO = i$.entrySet().iterator(); dataTypeDeploymentEVO.hasNext(); newDeploymentDataType.add(deployEntryEVO1.getPK())) {
               Entry deployEntryEVO = (Entry)dataTypeDeploymentEVO.next();
               StructureElementRefImpl startSeRef = (StructureElementRefImpl)((Pair)deployEntryEVO.getKey()).getChild1();
               StructureElementRefImpl endSeRef = (StructureElementRefImpl)((Pair)deployEntryEVO.getKey()).getChild2();
               deployEntryEVO1 = lineEVO.findFormulaDeploymentEntry(existingDeploymentDataTypeEVOs, startSeRef.getStructureElementPK().getStructureId(), startSeRef.getStructureElementPK().getStructureElementId(), endSeRef != null?Integer.valueOf(endSeRef.getStructureElementPK().getStructureElementId()):null);
               if(deployEntryEVO1 == null) {
                  deployEntryEVO1 = new FormulaDeploymentEntryEVO(this.getNextId(), lineEVO.getFormulaDeploymentLineId(), existingDeploymentDataTypeEVOs, startSeRef.getStructureElementPK().getStructureId(), startSeRef.getStructureElementPK().getStructureElementId(), ((Boolean)deployEntryEVO.getValue()).booleanValue(), endSeRef != null?Integer.valueOf(endSeRef.getStructureElementPK().getStructureElementId()):null);
                  lineEVO.addDeploymentEntriesItem(deployEntryEVO1);
               } else {
                  deployEntryEVO1.setSelectedInd(((Boolean)deployEntryEVO.getValue()).booleanValue());
               }
            }
         }

         if(lineEVO.getDeploymentEntries() != null) {
            dataTypeDeploymentEVO = lineEVO.getDeploymentEntries().iterator();

            while(dataTypeDeploymentEVO.hasNext()) {
               FormulaDeploymentEntryEVO var19 = (FormulaDeploymentEntryEVO)dataTypeDeploymentEVO.next();
               if(var19.getDimSeq() == existingDeploymentDataTypeEVOs && !newDeploymentDataType.contains(var19.getPK())) {
                  lineEVO.deleteDeploymentEntriesItem(var19.getPK());
               }
            }
         }

         newDeploymentDataType.clear();
      }

      ArrayList var13 = lineEVO.getDeploymentDataTypes() != null?new ArrayList(lineEVO.getDeploymentDataTypes()):new ArrayList();
      HashSet var14 = new HashSet(lineImpl.getDeploymentDataTypes());
      Iterator var15 = var13.iterator();

      while(var15.hasNext()) {
         FormulaDeploymentDtEVO var16 = (FormulaDeploymentDtEVO)var15.next();
         DataTypeRef var18 = lineImpl.findDataTypeDeployment(var16.getDataTypeId());
         if(var18 != null) {
            var14.remove(var18);
         } else {
            lineEVO.deleteDeploymentDataTypesItem(var16.getPK());
         }
      }

      var15 = var14.iterator();

      while(var15.hasNext()) {
         DataTypeRef var17 = (DataTypeRef)var15.next();
         FormulaDeploymentDtEVO var20 = new FormulaDeploymentDtEVO(this.getNextId(), lineEVO.getFormulaDeploymentLineId(), ((DataTypeRefImpl)var17).getDataTypePK().getDataTypeId());
         lineEVO.addDeploymentDataTypesItem(var20);
      }

   }

   private int getDimIndex(DimensionRef targetDimRef, DimensionRef[] dimensionRefs) {
      for(int i = 0; i < dimensionRefs.length; ++i) {
         if(dimensionRefs[i].equals(targetDimRef)) {
            return i;
         }
      }

      return -1;
   }

   private DimensionRef[] getFinanceCubeDimensionRefs(int financeCubeId) {
      ModelDAO modelDAO = new ModelDAO();
      FinanceCubeCK financeCubeCK = modelDAO.getFinanceCubeCK(new FinanceCubePK(financeCubeId));
      ModelDimensionsELO modelDimensions = modelDAO.getModelDimensions(financeCubeCK.getModelPK().getModelId());
      DimensionRef[] dimRefs = new DimensionRef[modelDimensions.size()];

      for(int i = 0; i < dimRefs.length; ++i) {
         dimRefs[i] = (DimensionRef)modelDimensions.getValueAt(i, "Dimension");
      }

      return dimRefs;
   }

   private Map<Integer, DataTypeRef> getAllDataTypes() {
      DataTypeDAO dtDAO = new DataTypeDAO();
      AllDataTypesELO dataTypes = dtDAO.getAllDataTypes();
      HashMap dtMap = new HashMap();

      for(int i = 0; i < dataTypes.getNumRows(); ++i) {
         DataTypeRefImpl dtRef = (DataTypeRefImpl)dataTypes.getValueAt(i, "DataType");
         dtMap.put(Integer.valueOf(dtRef.getDataTypePK().getDataTypeId()), dtRef);
      }

      return dtMap;
   }

   public void compileFormula(int financeCubeId, int cubeFormulaId, String formulaText, int formulaType) throws ValidationException, EJBException {
      this.testCompileFormula(financeCubeId, cubeFormulaId, formulaText, formulaType);
      (new FormulaCompiler(new FormulaDAO(), new CubeFormulaDAO(), new CubeFormulaPackageDAO(), new ModelDAO(), new FinanceCubeDAO(), new DataTypeDAO(), new SystemPropertyDAO(), new MetaTableManager())).compile(financeCubeId, cubeFormulaId, formulaText, formulaType, false);
   }

   public void testCompileFormula(int financeCubeId, int cubeFormulaId, String formulaText, int formulaType) throws ValidationException, EJBException {
      (new FormulaCompiler(new FormulaDAO(), new CubeFormulaDAO(), new CubeFormulaPackageDAO(), new ModelDAO(), new FinanceCubeDAO(), new DataTypeDAO(), new SystemPropertyDAO(), new MetaTableManager())).compile(financeCubeId, cubeFormulaId, formulaText, formulaType, true);
   }

   private void removeFormula(int financeCubeId, int cubeFormulaId) throws ValidationException {
      HashSet formulaIds = new HashSet();
      formulaIds.add(Integer.valueOf(cubeFormulaId));
      (new FormulaCompiler(new FormulaDAO(), new CubeFormulaDAO(), new CubeFormulaPackageDAO(), new ModelDAO(), new FinanceCubeDAO(), new DataTypeDAO(), new SystemPropertyDAO(), new MetaTableManager())).remove(financeCubeId, formulaIds, false);
   }

   private void removeFormula() throws Exception {
      this.getModelAccessor().flush(this.mModelEVO.getPK());
      if(this.mFinanceCubeEVO.getCubeFormulaEnabled() && this.mCubeFormulaEVO.getDeploymentInd()) {
         this.removeFormula(this.mCubeFormulaEVO.getFinanceCubeId(), this.mCubeFormulaEVO.getCubeFormulaId());
      }

   }

   private int getNextId() {
      return this.mNextId--;
   }
}
