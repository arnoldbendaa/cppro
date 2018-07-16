// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:26:40
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.impl.model.cc.imp;

import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.model.cc.RuntimeCubeDeployment;
import com.cedar.cp.dto.datatype.DataTypeDetailsForVisIDELO;
import com.cedar.cp.dto.datatype.DataTypeRefImpl;
import com.cedar.cp.dto.dimension.CalendarForModelELO;
import com.cedar.cp.dto.dimension.DimensionRefImpl;
import com.cedar.cp.dto.dimension.StructureElementByVisIdELO;
import com.cedar.cp.dto.dimension.StructureElementRefImpl;
import com.cedar.cp.dto.model.AllModelsELO;
import com.cedar.cp.dto.model.BudgetCycleRefImpl;
import com.cedar.cp.dto.model.BudgetCyclesForModelELO;
import com.cedar.cp.dto.model.FinanceCubeRefImpl;
import com.cedar.cp.dto.model.FinanceCubesForModelELO;
import com.cedar.cp.dto.model.ModelDimensionsELO;
import com.cedar.cp.dto.model.ModelRefImpl;
import com.cedar.cp.ejb.impl.base.AbstractDAO;
import com.cedar.cp.ejb.impl.datatype.DataTypeDAO;
import com.cedar.cp.ejb.impl.dimension.DimensionElementDAO;
import com.cedar.cp.ejb.impl.dimension.HierarchyDAO;
import com.cedar.cp.ejb.impl.dimension.StructureElementDAO;
import com.cedar.cp.ejb.impl.model.BudgetCycleDAO;
import com.cedar.cp.ejb.impl.model.FinanceCubeDAO;
import com.cedar.cp.ejb.impl.model.ModelDAO;
import com.cedar.cp.ejb.impl.model.cc.CcDeploymentDAO;
import com.cedar.cp.ejb.impl.model.cc.imp.CellCalcUtils;
import com.cedar.cp.util.Log;
import com.cedar.cp.util.Pair;
import com.cedar.cp.util.xmlform.CalendarElementNode;
import com.cedar.cp.util.xmlform.CalendarInfo;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public abstract class AbstractCellCalcImportEngine extends AbstractDAO {

   private int mNextCellCalcShortId = -500000001;
   public static final int NEW_CELL_CALCULATION_OFFSET = -500000001;
   private CellCalcUtils mCellCalcUtils;
   private HierarchyDAO mHierarchyDAO;
   protected int mUserId;
   protected int mTaskId;
   protected int mAbsAllocThreshold;
   protected int mBatchStartIndex;
   protected int mCellCalcStartIndex;
   protected int mCellCalcBatchSize;
   protected int mCurrentBatchIndex = -1;
   protected int mCurrentCalcIndex;
   protected int mLastBatchProcessedIndex;
   protected int mLastCalcProcessedIndex;
   private ModelDAO mModelDAO;
   private FinanceCubeDAO mFinanceCubeDAO;
   private BudgetCycleDAO mBudgetCycleDAO;
   private StructureElementDAO mStructureElementDAO;
   private DimensionElementDAO mDimensionElementDAO;
   private DataTypeDAO mDataTypeDAO;
   private CcDeploymentDAO mCcDeploymentDAO;
   private Log mLog = new Log(this.getClass());


   public AbstractCellCalcImportEngine() {}

   public AbstractCellCalcImportEngine(Connection connection) {
      super(connection);
   }

   protected InputStream getInputStream(String importSource) throws ValidationException {
      if(importSource != null && importSource.trim().length() != 0) {
         try {
            return new BufferedInputStream((new URL(importSource)).openStream());
         } catch (MalformedURLException var3) {
            throw new ValidationException("Malformed URL:" + importSource);
         } catch (IOException var4) {
            throw new ValidationException("File not found:" + importSource);
         }
      } else {
         throw new ValidationException("No import source.");
      }
   }

   protected FinanceCubeRefImpl queryFinanceCubeRef(ModelRefImpl modelRef, String financeCubeVisId) {
      FinanceCubesForModelELO financeCubes = this.getFinanceCubeDAO().getFinanceCubesForModel(modelRef.getModelPK().getModelId());

      for(int i = 0; i < financeCubes.getNumRows(); ++i) {
         FinanceCubeRefImpl financeCubeRef = (FinanceCubeRefImpl)financeCubes.getValueAt(i, "FinanceCube");
         if(financeCubeRef.getNarrative().equals(financeCubeVisId)) {
            return financeCubeRef;
         }
      }

      return null;
   }

   protected ModelRefImpl queryModelRef(String modelVisId) {
      AllModelsELO allModels = this.getModelDAO().getAllModels();

      for(int i = 0; i < allModels.getNumRows(); ++i) {
         ModelRefImpl modelRef = (ModelRefImpl)allModels.getValueAt(i, "Model");
         if(modelRef.getNarrative().equals(modelVisId)) {
            return modelRef;
         }
      }

      return null;
   }

   protected BudgetCycleRefImpl queryBudgetCycleRef(ModelRefImpl modelRef, String budgetCycleVisId) {
      BudgetCyclesForModelELO budgetCycles = this.getBudgetCycleDAO().getBudgetCyclesForModel(modelRef.getModelPK().getModelId());

      for(int i = 0; i < budgetCycles.getNumRows(); ++i) {
         BudgetCycleRefImpl budgetCycleRef = (BudgetCycleRefImpl)budgetCycles.getValueAt(i, "BudgetCycle");
         if(budgetCycleRef.getNarrative().equals(budgetCycleVisId)) {
            return budgetCycleRef;
         }
      }

      return null;
   }

   protected DimensionRefImpl[] queryModelDimensions(ModelRefImpl modelRefImpl) {
      ModelDimensionsELO modelDimensions = this.getModelDAO().getModelDimensions(modelRefImpl.getModelPK().getModelId());
      DimensionRefImpl[] dimRefs = new DimensionRefImpl[modelDimensions.getNumRows()];

      for(int i = 0; i < dimRefs.length; ++i) {
         dimRefs[i] = (DimensionRefImpl)modelDimensions.getValueAt(i, "Dimension");
      }

      return dimRefs;
   }

   protected Pair<StructureElementRefImpl[], int[]> queryAddressDetails(List<String> address, DimensionRefImpl[] dimensionRefs, CalendarInfo calendarInfo) throws ValidationException {
      int[] positions = new int[address.size()];
      StructureElementRefImpl[] elements = new StructureElementRefImpl[address.size()];

      for(int calendarElementVisId = 0; calendarElementVisId < dimensionRefs.length - 1; ++calendarElementVisId) {
         StructureElementByVisIdELO calNode = this.getStructureElementDAO().getStructureElementByVisId((String)address.get(calendarElementVisId), dimensionRefs[calendarElementVisId].getDimensionPK().getDimensionId());
         if(calNode.getNumRows() == 0) {
            throw new ValidationException("Unable to locate dimension element [" + (String)address.get(calendarElementVisId) + "] in dimension " + dimensionRefs[calendarElementVisId]);
         }

         StructureElementRefImpl seRef = (StructureElementRefImpl)calNode.getValueAt(0, "StructureElement");
         if(!((Boolean)calNode.getValueAt(0, "Leaf")).booleanValue()) {
            throw new ValidationException("Element " + seRef + " is not a leaf element in dimension " + dimensionRefs[calendarElementVisId]);
         }

         elements[calendarElementVisId] = seRef;
         positions[calendarElementVisId] = calNode.getPosition();
      }

      String var9 = (String)address.get(address.size() - 1);
      CalendarElementNode var10 = calendarInfo.findElement(var9);
      if(var10 == null) {
         throw new ValidationException("Failed to locate calendar node : " + var9);
      } else {
         elements[elements.length - 1] = (StructureElementRefImpl)var10.getStructureElementRef();
         positions[positions.length - 1] = var10.getPosition();
         return new Pair(elements, positions);
      }
   }

   protected DataTypeRefImpl queryDataTypeRef(String dataType) {
      DataTypeDetailsForVisIDELO details = this.getDataTypeDAO().getDataTypeDetailsForVisID(dataType);
      return details.getNumRows() == 0?null:(DataTypeRefImpl)details.getValueAt(0, "DataType");
   }

   protected CalendarInfo queryCalendareInfo(ModelRefImpl modelRef) {
      CalendarForModelELO calDetails = this.getHierarchyDAO().getCalendarForModel(modelRef.getModelPK().getModelId());
      if(calDetails.getNumRows() == 0) {
         return null;
      } else {
         int hierarchyId = ((Integer)calDetails.getValueAt(0, "HierarchyId")).intValue();
         return this.getStructureElementDAO().getCalendarInfo(hierarchyId);
      }
   }

   public List<RuntimeCubeDeployment> queryCellCalcAtCellAddress(int model, int financeCubeId, StructureElementRefImpl[] cellAddress, String dataType) throws ValidationException {
      int[] seIds = new int[cellAddress.length - 1];
      ArrayList dynamicDimensions = new ArrayList();

      for(int dataTypes = 0; dataTypes < seIds.length; ++dataTypes) {
         seIds[dataTypes] = cellAddress[dataTypes].getStructureElementPK().getStructureElementId();
      }

      HashSet var9 = new HashSet();
      var9.add(dataType);
      CcDeploymentDAO dao = this.getCcDeploymentDAO();
      return dao.getRelevantCubeDeployments(model, financeCubeId, seIds, var9, dynamicDimensions, false);
   }

   public FinanceCubeDAO getFinanceCubeDAO() {
      if(this.mFinanceCubeDAO == null) {
         this.mFinanceCubeDAO = new FinanceCubeDAO();
      }

      return this.mFinanceCubeDAO;
   }

   public void setFinanceCubeDAO(FinanceCubeDAO financeCubeDAO) {
      this.mFinanceCubeDAO = financeCubeDAO;
   }

   public ModelDAO getModelDAO() {
      if(this.mModelDAO == null) {
         this.mModelDAO = new ModelDAO();
      }

      return this.mModelDAO;
   }

   public void setModelDAO(ModelDAO modelDAO) {
      this.mModelDAO = modelDAO;
   }

   public BudgetCycleDAO getBudgetCycleDAO() {
      if(this.mBudgetCycleDAO == null) {
         this.mBudgetCycleDAO = new BudgetCycleDAO();
      }

      return this.mBudgetCycleDAO;
   }

   public void setBudgetCycleDAO(BudgetCycleDAO budgetCycleDAO) {
      this.mBudgetCycleDAO = budgetCycleDAO;
   }

   public StructureElementDAO getStructureElementDAO() {
      if(this.mStructureElementDAO == null) {
         this.mStructureElementDAO = new StructureElementDAO();
      }

      return this.mStructureElementDAO;
   }

   public void setStructureElementDAO(StructureElementDAO structureElementDAO) {
      this.mStructureElementDAO = structureElementDAO;
   }

   public DimensionElementDAO getDimensionElementDAO() {
      if(this.mDimensionElementDAO == null) {
         this.mDimensionElementDAO = new DimensionElementDAO();
      }

      return this.mDimensionElementDAO;
   }

   public void setDimensionElementDAO(DimensionElementDAO dimensionElementDAO) {
      this.mDimensionElementDAO = dimensionElementDAO;
   }

   public DataTypeDAO getDataTypeDAO() {
      if(this.mDataTypeDAO == null) {
         this.mDataTypeDAO = new DataTypeDAO();
      }

      return this.mDataTypeDAO;
   }

   public void setDataTypeDAO(DataTypeDAO dataTypeDAO) {
      this.mDataTypeDAO = dataTypeDAO;
   }

   public CcDeploymentDAO getCcDeploymentDAO() {
      if(this.mCcDeploymentDAO == null) {
         this.mCcDeploymentDAO = new CcDeploymentDAO();
      }

      return this.mCcDeploymentDAO;
   }

   public void setCcDeploymentDAO(CcDeploymentDAO ccDeploymentDAO) {
      this.mCcDeploymentDAO = ccDeploymentDAO;
   }

   public HierarchyDAO getHierarchyDAO() {
      if(this.mHierarchyDAO == null) {
         this.mHierarchyDAO = new HierarchyDAO();
      }

      return this.mHierarchyDAO;
   }

   public void setHierarchyDAO(HierarchyDAO hierarchyDAO) {
      this.mHierarchyDAO = hierarchyDAO;
   }

   protected int getNewCellCalcShort() {
      return this.mNextCellCalcShortId--;
   }

   public int getUserId() {
      return this.mUserId;
   }

   public void setUserId(int userId) {
      this.mUserId = userId;
   }

   public int getTaskId() {
      return this.mTaskId;
   }

   public void setTaskId(int taskId) {
      this.mTaskId = taskId;
   }

   public int getAbsAllocThreshold() {
      return this.mAbsAllocThreshold;
   }

   public void setAbsAllocThreshold(int absAllocThreshold) {
      this.mAbsAllocThreshold = absAllocThreshold;
   }

   public int getBatchStartIndex() {
      return this.mBatchStartIndex;
   }

   public void setBatchStartIndex(int batchStartIndex) {
      this.mBatchStartIndex = batchStartIndex;
   }

   public int getCellCalcStartIndex() {
      return this.mCellCalcStartIndex;
   }

   public void setCellCalcStartIndex(int cellCalcStartIndex) {
      this.mCellCalcStartIndex = cellCalcStartIndex;
   }

   public int getCellCalcBatchSize() {
      return this.mCellCalcBatchSize;
   }

   public void setCellCalcBatchSize(int cellCalcBatchSize) {
      this.mCellCalcBatchSize = cellCalcBatchSize;
   }

   public int getLastBatchProcessedIndex() {
      return this.mLastBatchProcessedIndex;
   }

   public int getLastCalcProcessedIndex() {
      return this.mLastCalcProcessedIndex;
   }

   public CellCalcUtils getCellCalcUtils() {
      if(this.mCellCalcUtils == null) {
         this.mCellCalcUtils = new CellCalcUtils();
      }

      return this.mCellCalcUtils;
   }

   public void setCellCalcUtils(CellCalcUtils cellCalcUtils) {
      this.mCellCalcUtils = cellCalcUtils;
   }

   protected boolean isInBatchWindow() {
      return this.mCurrentBatchIndex == this.mBatchStartIndex && this.mCellCalcStartIndex <= this.mCurrentCalcIndex && this.mCurrentCalcIndex < this.mCellCalcStartIndex + this.mCellCalcBatchSize;
   }

   public boolean noWorkLeftToDo() {
      return this.mLastBatchProcessedIndex == this.mCurrentBatchIndex && this.mLastCalcProcessedIndex == this.mCurrentCalcIndex - 1;
   }
}
