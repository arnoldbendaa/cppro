// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:08:52
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.impl.model;

import com.cedar.cp.api.base.EntityList;
import com.cedar.cp.api.base.EntityRef;
import com.cedar.cp.dto.base.CompositeKey;
import com.cedar.cp.dto.base.PrimaryKey;
import com.cedar.cp.dto.cubeformula.AllCubeFormulasELO;
import com.cedar.cp.dto.cubeformula.AllPackagesForFinanceCubeELO;
import com.cedar.cp.dto.cubeformula.CubeFormulaeForFinanceCubeELO;
import com.cedar.cp.dto.model.AllAccessDefsUsingRangeELO;
import com.cedar.cp.dto.model.AllAttachedDataTypesForFinanceCubeELO;
import com.cedar.cp.dto.model.AllBudgetCyclesELO;
import com.cedar.cp.dto.model.AllBudgetCyclesWebDetailedELO;
import com.cedar.cp.dto.model.AllBudgetCyclesWebELO;
import com.cedar.cp.dto.model.AllBudgetHierarchiesELO;
import com.cedar.cp.dto.model.AllBudgetUsersELO;
import com.cedar.cp.dto.model.AllCellCalcAssocsELO;
import com.cedar.cp.dto.model.AllCellCalcsELO;
import com.cedar.cp.dto.model.AllDataTypesAttachedToFinanceCubeForModelELO;
import com.cedar.cp.dto.model.AllDataTypesForFinanceCubeELO;
import com.cedar.cp.dto.model.AllFinanceCubeDataTypesELO;
import com.cedar.cp.dto.model.AllFinanceCubesELO;
import com.cedar.cp.dto.model.AllFinanceCubesForDataTypeELO;
import com.cedar.cp.dto.model.AllFinanceCubesWebELO;
import com.cedar.cp.dto.model.AllFinanceCubesWebForModelELO;
import com.cedar.cp.dto.model.AllFinanceCubesWebForUserELO;
import com.cedar.cp.dto.model.AllModelBusAndAccDimensionsELO;
import com.cedar.cp.dto.model.AllModelBusinessDimensionsELO;
import com.cedar.cp.dto.model.AllModelsELO;
import com.cedar.cp.dto.model.AllModelsWebELO;
import com.cedar.cp.dto.model.AllModelsWebForUserELO;
import com.cedar.cp.dto.model.AllModelsWithActiveCycleForUserELO;
import com.cedar.cp.dto.model.AllRootsForModelELO;
import com.cedar.cp.dto.model.AllSecurityAccessDefsELO;
import com.cedar.cp.dto.model.AllSecurityAccessDefsForModelELO;
import com.cedar.cp.dto.model.AllSecurityGroupsELO;
import com.cedar.cp.dto.model.AllSecurityGroupsForUserELO;
import com.cedar.cp.dto.model.AllSecurityGroupsUsingAccessDefELO;
import com.cedar.cp.dto.model.AllSimpleFinanceCubesELO;
import com.cedar.cp.dto.model.AllUsersForASecurityGroupELO;
import com.cedar.cp.dto.model.BudgetCycleCK;
import com.cedar.cp.dto.model.BudgetCycleDetailedForIdELO;
import com.cedar.cp.dto.model.BudgetCycleIntegrityELO;
import com.cedar.cp.dto.model.BudgetCyclePK;
import com.cedar.cp.dto.model.BudgetCyclesForModelELO;
import com.cedar.cp.dto.model.BudgetCyclesForModelWithStateELO;
import com.cedar.cp.dto.model.BudgetCyclesToFixStateELO;
import com.cedar.cp.dto.model.BudgetDetailsForUserELO;
import com.cedar.cp.dto.model.BudgetDimensionIdForModelELO;
import com.cedar.cp.dto.model.BudgetHierarchyRootNodeForModelELO;
import com.cedar.cp.dto.model.BudgetTransferBudgetCyclesELO;
import com.cedar.cp.dto.model.BudgetUsersForNodeELO;
import com.cedar.cp.dto.model.CalendarSpecForModelELO;
import com.cedar.cp.dto.model.CellCalcIntegrityELO;
import com.cedar.cp.dto.model.CheckIfHasStateELO;
import com.cedar.cp.dto.model.CheckUserAccessELO;
import com.cedar.cp.dto.model.CheckUserAccessToModelELO;
import com.cedar.cp.dto.model.CheckUserELO;
import com.cedar.cp.dto.model.CycleStateDetailsELO;
import com.cedar.cp.dto.model.DimensionIdForModelDimTypeELO;
import com.cedar.cp.dto.model.FinanceCubeAllDimensionsAndHierachiesELO;
import com.cedar.cp.dto.model.FinanceCubeCK;
import com.cedar.cp.dto.model.FinanceCubeDetailsELO;
import com.cedar.cp.dto.model.FinanceCubeDimensionsAndHierachiesELO;
import com.cedar.cp.dto.model.FinanceCubePK;
import com.cedar.cp.dto.model.FinanceCubesForModelELO;
import com.cedar.cp.dto.model.FinanceCubesUsingDataTypeELO;
import com.cedar.cp.dto.model.HierarchiesForModelELO;
import com.cedar.cp.dto.model.ImportableFinanceCubeDataTypesELO;
import com.cedar.cp.dto.model.MaxDepthForBudgetHierarchyELO;
import com.cedar.cp.dto.model.ModelCK;
import com.cedar.cp.dto.model.ModelDetailsWebELO;
import com.cedar.cp.dto.model.ModelDimensionsELO;
import com.cedar.cp.dto.model.ModelDimensionseExcludeCallELO;
import com.cedar.cp.dto.model.ModelForDimensionELO;
import com.cedar.cp.dto.model.ModelPK;
import com.cedar.cp.dto.model.NodesForUserAndCycleELO;
import com.cedar.cp.dto.model.NodesForUserAndModelELO;
import com.cedar.cp.dto.model.UsersForModelAndElementELO;
import com.cedar.cp.dto.model.act.ActivitiesForCycleandElementELO;
import com.cedar.cp.dto.model.act.ActivityDetailsELO;
import com.cedar.cp.dto.model.act.ActivityFullDetailsELO;
import com.cedar.cp.dto.model.budgetlimit.AllBudgetLimitsELO;
import com.cedar.cp.dto.model.cc.AllCcDeploymentsELO;
import com.cedar.cp.dto.model.cc.CcDeploymentCellPickerInfoELO;
import com.cedar.cp.dto.model.cc.CcDeploymentXMLFormTypeELO;
import com.cedar.cp.dto.model.cc.CcDeploymentsForLookupTableELO;
import com.cedar.cp.dto.model.cc.CcDeploymentsForModelELO;
import com.cedar.cp.dto.model.cc.CcDeploymentsForXmlFormELO;
import com.cedar.cp.dto.model.cc.imp.dyn.ImportGridCK;
import com.cedar.cp.dto.model.cc.imp.dyn.ImportGridPK;
import com.cedar.cp.dto.model.ra.AllResponsibilityAreasELO;
import com.cedar.cp.dto.model.recharge.AllRechargesELO;
import com.cedar.cp.dto.model.recharge.AllRechargesWithModelELO;
import com.cedar.cp.dto.model.recharge.SingleRechargeELO;
import com.cedar.cp.dto.model.udwp.AllWeightingDeploymentsELO;
import com.cedar.cp.dto.model.udwp.AllWeightingProfilesELO;
import com.cedar.cp.dto.model.virement.AccountsForCategoryELO;
import com.cedar.cp.dto.model.virement.AllVirementCategorysELO;
import com.cedar.cp.dto.model.virement.AllVirementRequestGroupsELO;
import com.cedar.cp.dto.model.virement.AllVirementRequestsELO;
import com.cedar.cp.dto.model.virement.LocationsForCategoryELO;
import com.cedar.cp.dto.xmlform.rebuild.AllBudgetCyclesInRebuildsELO;
import com.cedar.cp.dto.xmlform.rebuild.AllFormRebuildsELO;
import com.cedar.cp.ejb.impl.cubeformula.CubeFormulaDAO;
import com.cedar.cp.ejb.impl.cubeformula.CubeFormulaPackageDAO;
import com.cedar.cp.ejb.impl.model.BudgetCycleDAO;
import com.cedar.cp.ejb.impl.model.BudgetStateDAO;
import com.cedar.cp.ejb.impl.model.BudgetUserDAO;
import com.cedar.cp.ejb.impl.model.CellCalcAssocDAO;
import com.cedar.cp.ejb.impl.model.CellCalcDAO;
import com.cedar.cp.ejb.impl.model.FinanceCubeDAO;
import com.cedar.cp.ejb.impl.model.FinanceCubeDataTypeDAO;
import com.cedar.cp.ejb.impl.model.ModelDAO;
import com.cedar.cp.ejb.impl.model.ModelDimensionRelDAO;
import com.cedar.cp.ejb.impl.model.ModelEVO;
import com.cedar.cp.ejb.impl.model.ModelLocal;
import com.cedar.cp.ejb.impl.model.ModelLocalHome;
import com.cedar.cp.ejb.impl.model.SecurityAccRngRelDAO;
import com.cedar.cp.ejb.impl.model.SecurityAccessDefDAO;
import com.cedar.cp.ejb.impl.model.SecurityGroupDAO;
import com.cedar.cp.ejb.impl.model.SecurityGroupUserRelDAO;
import com.cedar.cp.ejb.impl.model.act.BudgetActivityDAO;
import com.cedar.cp.ejb.impl.model.budgetlimit.BudgetLimitDAO;
import com.cedar.cp.ejb.impl.model.cc.CcDeploymentDAO;
import com.cedar.cp.ejb.impl.model.cc.imp.dyn.ImportGridDAO;
import com.cedar.cp.ejb.impl.model.ra.ResponsibilityAreaDAO;
import com.cedar.cp.ejb.impl.model.recharge.RechargeDAO;
import com.cedar.cp.ejb.impl.model.udwp.WeightingDeploymentDAO;
import com.cedar.cp.ejb.impl.model.udwp.WeightingProfileDAO;
import com.cedar.cp.ejb.impl.model.virement.VirementAccountDAO;
import com.cedar.cp.ejb.impl.model.virement.VirementCategoryDAO;
import com.cedar.cp.ejb.impl.model.virement.VirementLocationDAO;
import com.cedar.cp.ejb.impl.model.virement.VirementRequestDAO;
import com.cedar.cp.ejb.impl.model.virement.VirementRequestGroupDAO;
import com.cedar.cp.ejb.impl.xmlform.rebuild.FormRebuildDAO;

import java.io.Serializable;
import java.util.Hashtable;
import java.util.Map;

import javax.ejb.EJBException;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class ModelAccessor implements Serializable {

   private ModelLocalHome mLocalHome;
   private Hashtable mLocals = new Hashtable();
   private transient InitialContext mInitialContext;
   public static final String GET_FINANCE_CUBES = "<0>";
   public static final String GET_FINANCE_CUBE_DATA_TYPES = "<1>";
   public static final String GET_BUDGET_LIMITS = "<2>";
   public static final String GET_ROLL_UP_RULES = "<3>";
   public static final String GET_CUBE_FORMULA = "<4>";
   public static final String GET_DEPLOYMENTS = "<5>";
   public static final String GET_DEPLOYMENT_ENTRIES = "<6>";
   public static final String GET_DEPLOYMENT_DATA_TYPES = "<7>";
   public static final String GET_CUBE_FORMULA_PACKAGES = "<8>";
   public static final String GET_MODEL_DIMENSION_RELS = "<9>";
   public static final String GET_MODEL_PROPERTIES = "<10>";
   public static final String GET_BUDGET_CYCLES = "<11>";
   public static final String GET_BUDGET_CYCLE_STATES = "<12>";
   public static final String GET_BUDGET_CYCLE_HISTORY = "<13>";
   public static final String GET_BUDGET_CYCLE_LEVL_DATES = "<14>";
   public static final String GET_BUDGET_USERS = "<15>";
   public static final String GET_SECURITY_GROUPS = "<16>";
   public static final String GET_USERS_IN_GROUP = "<17>";
   public static final String GET_SECURITY_ACCESS_DEFS = "<18>";
   public static final String GET_SECURITY_RANGES_FOR_ACCESS_DEF = "<19>";
   public static final String GET_CELL_CALCULATIONS = "<20>";
   public static final String GET_CELL_CALCULATION_ACCOUNTS = "<21>";
   public static final String GET_VIREMENT_GROUPS = "<22>";
   public static final String GET_VIREMENT_RESPONSIBILITY_AREAS = "<23>";
   public static final String GET_VIREMENT_ACCOUNTS = "<24>";
   public static final String GET_RECHARGE = "<25>";
   public static final String GET_RECHARGE_CELLS = "<26>";
   public static final String GET_BUDGET_ACTIVITIES = "<27>";
   public static final String GET_ACTIVITY_LINKS = "<28>";
   public static final String GET_VIREMENT_REQUESTS = "<29>";
   public static final String GET_GROUPS = "<30>";
   public static final String GET_LINES = "<31>";
   public static final String GET_SPREADS = "<32>";
   public static final String GET_AUTH_POINTS = "<33>";
   public static final String GET_AUTH_USERS = "<34>";
   public static final String GET_AUTH_POINT_LINKS = "<35>";
   public static final String GET_RESPONSIBILITY_AREAS = "<36>";
   public static final String GET_USER_DEFINED_WEIGHTING_PROFILES = "<37>";
   public static final String GET_WEIGHTING_LINES = "<38>";
   public static final String GET_WEIGHTING_DEPLOYMENTS = "<39>";
   public static final String GET_DEPLOYMENT_LINES = "<40>";
   public static final String GET_CELL_CALC_DEPLOYMENTS = "<41>";
   public static final String GET_C_C_DEPLOYMENT_LINES = "<42>";
   public static final String GET_C_C_DEPLOYMENT_ENTRIES = "<43>";
   public static final String GET_C_C_DEPLOYMENT_DATA_TYPES = "<44>";
   public static final String GET_C_C_MAPPING_LINES = "<45>";
   public static final String GET_C_C_MAPPING_ENTRIES = "<46>";
   public static final String GET_FORM_REBUILDS = "<47>";
   public static final String GET_ASSOC_IMPORT_GRIDS = "<48>";
   public static final String GET_ALL_DEPENDANTS = "<0><1><2><3><4><5><6><7><8><9><10><11><12><13><14><15><16><17><18><19><20><21><22><23><24><25><26><27><28><29><30><31><32><33><34><35><36><37><38><39><40><41><42><43><44><45><46><47><48>";
   public static ModelEEJB modelEjb = new ModelEEJB();
   FinanceCubeDAO financeDao = new FinanceCubeDAO();
   public ModelAccessor(InitialContext ctx) {
      this.mInitialContext = ctx;
   }

   private ModelLocalHome getLocalHome() {
      try {
         if(this.mLocalHome != null) {
            return this.mLocalHome;
         } else {
            this.mLocalHome = (ModelLocalHome)this.mInitialContext.lookup("java:comp/env/ejb/ModelLocalHome");
            return this.mLocalHome;
         }
      } catch (NamingException var2) {
         throw new RuntimeException("error looking up ModelLocalHome", var2);
      }
   }

   private ModelEEJB getLocal(ModelPK pk) throws Exception {
//      ModelLocal local = (ModelLocal)this.mLocals.get(pk);
//      if(local == null) {
//         local = (ModelLocal) this.getLocalHome().findByPrimaryKey(pk);
//         this.mLocals.put(pk, local);
//      }
//
//      return local;
	   return this.modelEjb;
   }

   public ModelEVO create(ModelEVO evo) throws Exception {
//      ModelLocal local = (ModelLocal) this.getLocalHome().create(evo);
	   modelEjb.ejbCreate(evo);
      ModelEVO newevo = modelEjb.getDetails("<UseLoadedEVOs>");
      ModelPK pk = newevo.getPK();
      this.mLocals.put(pk, modelEjb);
      return newevo;
   }

   public void remove(ModelPK pk) throws Exception {
      this.getLocal(pk).ejbRemove();
   }

   public ModelEVO getDetails(Object paramKey, String dependants) throws Exception {
      Object key = paramKey;
      if(paramKey instanceof EntityRef) {
         key = ((EntityRef)paramKey).getPrimaryKey();
      }

      if(key instanceof FinanceCubePK) {
         key = (FinanceCubeCK)this.getCKForDependantPK((PrimaryKey)key);
      }

      if(key instanceof BudgetCyclePK) {
         key = (BudgetCycleCK)this.getCKForDependantPK((PrimaryKey)key);
      }

      if(key instanceof ImportGridPK) {
         key = (ImportGridCK)this.getCKForDependantPK((PrimaryKey)key);
      }

      if(key instanceof ModelCK) {
         ModelPK pk = ((ModelCK)key).getModelPK();
//         return this.getLocal(pk).getDetails((ModelCK)key, dependants);
         return modelEjb.getDetails((ModelCK)key, dependants);
         
      } else {
//         return key instanceof ModelPK?this.getLocal((ModelPK)key).getDetails(dependants):null;
    	  ModelPK modelPk = (ModelPK)key;
    	  ModelCK modelCk = new ModelCK(modelPk);
    	  if(key instanceof ModelPK)
    		  return modelEjb.getDetails(modelCk,dependants);
    	  else 
    		  return null;
      }
   }

   public CompositeKey getCKForDependantPK(PrimaryKey key) {
      if(key instanceof FinanceCubePK) {
         FinanceCubeDAO dao2 = new FinanceCubeDAO();
         return (CompositeKey)dao2.getRef((FinanceCubePK)key).getPrimaryKey();
      } else if(key instanceof BudgetCyclePK) {
         BudgetCycleDAO dao1 = new BudgetCycleDAO();
         return (CompositeKey)dao1.getRef((BudgetCyclePK)key).getPrimaryKey();
      } else if(key instanceof ImportGridPK) {
         ImportGridDAO dao = new ImportGridDAO();
         return (CompositeKey)dao.getRef((ImportGridPK)key).getPrimaryKey();
      } else {
         return null;
      }
   }

   public void setDetails(ModelEVO evo) throws Exception {
	      ModelPK pk = evo.getPK();
	      modelEjb.setDetails(evo);
//	      modelEjb.ejbStore();
//	      this.getLocal(pk).setDetails(evo);
	   }
   public void setDetails(ModelEVO evo,boolean store) throws Exception {
	   if(store){
		      ModelPK pk = evo.getPK();
		      modelEjb.setDetails(evo);
		      modelEjb.ejbStore();
	   }
//	      this.getLocal(pk).setDetails(evo);
	   }

   public ModelEVO setAndGetDetails(ModelEVO evo, String dependants) throws Exception {
	   
	   ModelPK pk = evo.getPK();
	   modelEjb.ejbFindByPrimaryKey(pk);
	   ModelEVO result = modelEjb.setAndGetDetails(evo, dependants);
	   return result;
	   
	   
//      return this.getLocal(evo.getPK()).setAndGetDetails(evo, dependants);
   }

   public ModelPK generateKeys(ModelPK pk) throws Exception {
      return this.getLocal(pk).generateKeys();
   }

   public AllModelsELO getAllModels() {
      ModelDAO dao = new ModelDAO();
      return dao.getAllModels();
   }

   public AllModelsWebELO getAllModelsWeb() {
      ModelDAO dao = new ModelDAO();
      return dao.getAllModelsWeb();
   }

   public AllModelsWebForUserELO getAllModelsWebForUser(int param1) {
      ModelDAO dao = new ModelDAO();
      return dao.getAllModelsWebForUser(param1);
   }

   public AllModelsWithActiveCycleForUserELO getAllModelsWithActiveCycleForUser(int param1) {
      ModelDAO dao = new ModelDAO();
      return dao.getAllModelsWithActiveCycleForUser(param1);
   }

   public AllBudgetHierarchiesELO getAllBudgetHierarchies() {
      ModelDAO dao = new ModelDAO();
      return dao.getAllBudgetHierarchies();
   }

   public ModelForDimensionELO getModelForDimension(int param1) {
      ModelDAO dao = new ModelDAO();
      return dao.getModelForDimension(param1);
   }

   public ModelDimensionsELO getModelDimensions(int param1) {
      ModelDAO dao = new ModelDAO();
      return dao.getModelDimensions(param1);
   }

   public ModelDimensionseExcludeCallELO getModelDimensionseExcludeCall(int param1) {
      ModelDAO dao = new ModelDAO();
      return dao.getModelDimensionseExcludeCall(param1);
   }

   public ModelDetailsWebELO getModelDetailsWeb(int param1) {
      ModelDAO dao = new ModelDAO();
      return dao.getModelDetailsWeb(param1);
   }

   public AllRootsForModelELO getAllRootsForModel(int param1) {
      ModelDAO dao = new ModelDAO();
      return dao.getAllRootsForModel(param1);
   }

   public BudgetHierarchyRootNodeForModelELO getBudgetHierarchyRootNodeForModel(int param1) {
      ModelDAO dao = new ModelDAO();
      return dao.getBudgetHierarchyRootNodeForModel(param1);
   }

   public BudgetCyclesToFixStateELO getBudgetCyclesToFixState(int param1) {
      ModelDAO dao = new ModelDAO();
      return dao.getBudgetCyclesToFixState(param1);
   }

   public MaxDepthForBudgetHierarchyELO getMaxDepthForBudgetHierarchy(int param1) {
      ModelDAO dao = new ModelDAO();
      return dao.getMaxDepthForBudgetHierarchy(param1);
   }

   public CalendarSpecForModelELO getCalendarSpecForModel(int param1) {
      ModelDAO dao = new ModelDAO();
      return dao.getCalendarSpecForModel(param1);
   }

   public HierarchiesForModelELO getHierarchiesForModel(int param1) {
      ModelDAO dao = new ModelDAO();
      return dao.getHierarchiesForModel(param1);
   }

   public AllFinanceCubesELO getAllFinanceCubes() {
       //FinanceCubeDAO financeDao = new FinanceCubeDAO();
      return financeDao.getAllFinanceCubes();
   }

   public AllSimpleFinanceCubesELO getAllSimpleFinanceCubes(int userId) {
       //FinanceCubeDAO financeDao = new FinanceCubeDAO();
      return financeDao.getAllSimpleFinanceCubes(userId);
   }

   public AllDataTypesAttachedToFinanceCubeForModelELO getAllDataTypesAttachedToFinanceCubeForModel(int param1) {
       //FinanceCubeDAO financeDao = new FinanceCubeDAO();
      return financeDao.getAllDataTypesAttachedToFinanceCubeForModel(param1);
   }

   public FinanceCubesForModelELO getFinanceCubesForModel(int param1) {
       //FinanceCubeDAO financeDao = new FinanceCubeDAO();
      return financeDao.getFinanceCubesForModel(param1);
   }

   public FinanceCubeDimensionsAndHierachiesELO getFinanceCubeDimensionsAndHierachies(int param1) {
       //FinanceCubeDAO financeDao = new FinanceCubeDAO();
      return financeDao.getFinanceCubeDimensionsAndHierachies(param1);
   }

   public FinanceCubeAllDimensionsAndHierachiesELO getFinanceCubeAllDimensionsAndHierachies(int param1) {
       //FinanceCubeDAO financeDao = new FinanceCubeDAO();
      return financeDao.getFinanceCubeAllDimensionsAndHierachies(param1);
   }

   public AllFinanceCubesWebELO getAllFinanceCubesWeb() {
       //FinanceCubeDAO financeDao = new FinanceCubeDAO();
      return financeDao.getAllFinanceCubesWeb();
   }

   public AllFinanceCubesWebForModelELO getAllFinanceCubesWebForModel(int param1) {
       //FinanceCubeDAO financeDao = new FinanceCubeDAO();
      return financeDao.getAllFinanceCubesWebForModel(param1);
   }

   public AllFinanceCubesWebForUserELO getAllFinanceCubesWebForUser(int param1) {
       //FinanceCubeDAO financeDao = new FinanceCubeDAO();
      return financeDao.getAllFinanceCubesWebForUser(param1);
   }

   public FinanceCubeDetailsELO getFinanceCubeDetails(int param1) {
       //FinanceCubeDAO financeDao = new FinanceCubeDAO();
      return financeDao.getFinanceCubeDetails(param1);
   }

   public FinanceCubesUsingDataTypeELO getFinanceCubesUsingDataType(short param1) {
       //FinanceCubeDAO financeDao = new FinanceCubeDAO();
      return financeDao.getFinanceCubesUsingDataType(param1);
   }

   public AllFinanceCubeDataTypesELO getAllFinanceCubeDataTypes() {
      FinanceCubeDataTypeDAO dao = new FinanceCubeDataTypeDAO();
      return dao.getAllFinanceCubeDataTypes();
   }

   public ImportableFinanceCubeDataTypesELO getImportableFinanceCubeDataTypes() {
      FinanceCubeDataTypeDAO dao = new FinanceCubeDataTypeDAO();
      return dao.getImportableFinanceCubeDataTypes();
   }

   public AllAttachedDataTypesForFinanceCubeELO getAllAttachedDataTypesForFinanceCube(int param1) {
      FinanceCubeDataTypeDAO dao = new FinanceCubeDataTypeDAO();
      return dao.getAllAttachedDataTypesForFinanceCube(param1);
   }

   public AllDataTypesForFinanceCubeELO getAllDataTypesForFinanceCube(int param1) {
      FinanceCubeDataTypeDAO dao = new FinanceCubeDataTypeDAO();
      return dao.getAllDataTypesForFinanceCube(param1);
   }

   public AllFinanceCubesForDataTypeELO getAllFinanceCubesForDataType(short param1) {
      FinanceCubeDataTypeDAO dao = new FinanceCubeDataTypeDAO();
      return dao.getAllFinanceCubesForDataType(param1);
   }

   public AllBudgetLimitsELO getAllBudgetLimits() {
      BudgetLimitDAO dao = new BudgetLimitDAO();
      return dao.getAllBudgetLimits();
   }

   public AllCubeFormulasELO getAllCubeFormulas() {
      CubeFormulaDAO dao = new CubeFormulaDAO();
      return dao.getAllCubeFormulas();
   }

   public CubeFormulaeForFinanceCubeELO getCubeFormulaeForFinanceCube(int param1) {
      CubeFormulaDAO dao = new CubeFormulaDAO();
      return dao.getCubeFormulaeForFinanceCube(param1);
   }

   public AllPackagesForFinanceCubeELO getAllPackagesForFinanceCube(int param1) {
      CubeFormulaPackageDAO dao = new CubeFormulaPackageDAO();
      return dao.getAllPackagesForFinanceCube(param1);
   }

   public AllModelBusinessDimensionsELO getAllModelBusinessDimensions() {
      ModelDimensionRelDAO dao = new ModelDimensionRelDAO();
      return dao.getAllModelBusinessDimensions();
   }

   public AllModelBusAndAccDimensionsELO getAllModelBusAndAccDimensions() {
      ModelDimensionRelDAO dao = new ModelDimensionRelDAO();
      return dao.getAllModelBusAndAccDimensions();
   }

   public BudgetDimensionIdForModelELO getBudgetDimensionIdForModel(int param1, int param2) {
      ModelDimensionRelDAO dao = new ModelDimensionRelDAO();
      return dao.getBudgetDimensionIdForModel(param1, param2);
   }

   public DimensionIdForModelDimTypeELO getDimensionIdForModelDimType(int param1, int param2) {
      ModelDimensionRelDAO dao = new ModelDimensionRelDAO();
      return dao.getDimensionIdForModelDimType(param1, param2);
   }

   public AllBudgetCyclesELO getAllBudgetCycles() {
      BudgetCycleDAO dao = new BudgetCycleDAO();
      return dao.getAllBudgetCycles();
   }

   public AllBudgetCyclesWebELO getAllBudgetCyclesWeb() {
      BudgetCycleDAO dao = new BudgetCycleDAO();
      return dao.getAllBudgetCyclesWeb();
   }
   
   public AllBudgetCyclesELO getAllBudgetCyclesForUser(int userId) {
      BudgetCycleDAO dao = new BudgetCycleDAO();
      return dao.getAllBudgetCyclesForUser(userId);
   }

   public AllBudgetCyclesWebDetailedELO getAllBudgetCyclesWebDetailed() {
      BudgetCycleDAO dao = new BudgetCycleDAO();
      return dao.getAllBudgetCyclesWebDetailed();
   }

   public BudgetCyclesForModelELO getBudgetCyclesForModel(int param1) {
      BudgetCycleDAO dao = new BudgetCycleDAO();
      return dao.getBudgetCyclesForModel(param1);
   }

   public BudgetCyclesForModelWithStateELO getBudgetCyclesForModelWithState(int param1, int param2) {
      BudgetCycleDAO dao = new BudgetCycleDAO();
      return dao.getBudgetCyclesForModelWithState(param1, param2);
   }

   public BudgetCycleIntegrityELO getBudgetCycleIntegrity() {
      BudgetCycleDAO dao = new BudgetCycleDAO();
      return dao.getBudgetCycleIntegrity();
   }

   public BudgetCycleDetailedForIdELO getBudgetCycleDetailedForId(int param1) {
      BudgetCycleDAO dao = new BudgetCycleDAO();
      return dao.getBudgetCycleDetailedForId(param1);
   }
   
   public BudgetCycleDetailedForIdELO getBudgetCycleXmlFormsForId(int param1, int userid) {
      BudgetCycleDAO dao = new BudgetCycleDAO();
      return dao.getBudgetCycleXmlFormsForId(param1, userid);
   }
   
   public BudgetTransferBudgetCyclesELO getBudgetTransferBudgetCycles() {
      BudgetCycleDAO dao = new BudgetCycleDAO();
      return dao.getBudgetTransferBudgetCycles();
   }

   public CheckIfHasStateELO getCheckIfHasState(int param1, int param2) {
      BudgetStateDAO dao = new BudgetStateDAO();
      return dao.getCheckIfHasState(param1, param2);
   }

   public CycleStateDetailsELO getCycleStateDetails(int param1) {
      BudgetStateDAO dao = new BudgetStateDAO();
      return dao.getCycleStateDetails(param1);
   }

   public AllBudgetUsersELO getAllBudgetUsers() {
      BudgetUserDAO dao = new BudgetUserDAO();
      return dao.getAllBudgetUsers();
   }

   public CheckUserAccessToModelELO getCheckUserAccessToModel(int param1, int param2) {
      BudgetUserDAO dao = new BudgetUserDAO();
      return dao.getCheckUserAccessToModel(param1, param2);
   }

   public CheckUserAccessELO getCheckUserAccess(int param1, int param2) {
      BudgetUserDAO dao = new BudgetUserDAO();
      return dao.getCheckUserAccess(param1, param2);
   }

   public CheckUserELO getCheckUser(int param1) {
      BudgetUserDAO dao = new BudgetUserDAO();
      return dao.getCheckUser(param1);
   }

   public BudgetUsersForNodeELO getBudgetUsersForNode(int param1, int param2) {
      BudgetUserDAO dao = new BudgetUserDAO();
      return dao.getBudgetUsersForNode(param1, param2);
   }

   public NodesForUserAndCycleELO getNodesForUserAndCycle(int param1, int param2) {
      BudgetUserDAO dao = new BudgetUserDAO();
      return dao.getNodesForUserAndCycle(param1, param2);
   }

   public NodesForUserAndModelELO getNodesForUserAndModel(int param1, int param2) {
      BudgetUserDAO dao = new BudgetUserDAO();
      return dao.getNodesForUserAndModel(param1, param2);
   }

   public UsersForModelAndElementELO getUsersForModelAndElement(int param1, int param2) {
      BudgetUserDAO dao = new BudgetUserDAO();
      return dao.getUsersForModelAndElement(param1, param2);
   }

   public AllSecurityGroupsELO getAllSecurityGroups() {
      SecurityGroupDAO dao = new SecurityGroupDAO();
      return dao.getAllSecurityGroups();
   }

   public AllSecurityGroupsUsingAccessDefELO getAllSecurityGroupsUsingAccessDef(int param1) {
      SecurityGroupDAO dao = new SecurityGroupDAO();
      return dao.getAllSecurityGroupsUsingAccessDef(param1);
   }

   public AllSecurityGroupsForUserELO getAllSecurityGroupsForUser(int param1) {
      SecurityGroupDAO dao = new SecurityGroupDAO();
      return dao.getAllSecurityGroupsForUser(param1);
   }

   public AllUsersForASecurityGroupELO getAllUsersForASecurityGroup(int param1) {
      SecurityGroupUserRelDAO dao = new SecurityGroupUserRelDAO();
      return dao.getAllUsersForASecurityGroup(param1);
   }

   public AllSecurityAccessDefsELO getAllSecurityAccessDefs() {
      SecurityAccessDefDAO dao = new SecurityAccessDefDAO();
      return dao.getAllSecurityAccessDefs();
   }

   public AllSecurityAccessDefsForModelELO getAllSecurityAccessDefsForModel(int param1) {
      SecurityAccessDefDAO dao = new SecurityAccessDefDAO();
      return dao.getAllSecurityAccessDefsForModel(param1);
   }

   public AllAccessDefsUsingRangeELO getAllAccessDefsUsingRange(int param1) {
      SecurityAccRngRelDAO dao = new SecurityAccRngRelDAO();
      return dao.getAllAccessDefsUsingRange(param1);
   }

   public AllCellCalcsELO getAllCellCalcs() {
      CellCalcDAO dao = new CellCalcDAO();
      return dao.getAllCellCalcs();
   }

   public CellCalcIntegrityELO getCellCalcIntegrity() {
      CellCalcDAO dao = new CellCalcDAO();
      return dao.getCellCalcIntegrity();
   }

   public AllCellCalcAssocsELO getAllCellCalcAssocs() {
      CellCalcAssocDAO dao = new CellCalcAssocDAO();
      return dao.getAllCellCalcAssocs();
   }

   public AllVirementCategorysELO getAllVirementCategorys() {
      VirementCategoryDAO dao = new VirementCategoryDAO();
      return dao.getAllVirementCategorys();
   }

   public LocationsForCategoryELO getLocationsForCategory(int param1) {
      VirementLocationDAO dao = new VirementLocationDAO();
      return dao.getLocationsForCategory(param1);
   }

   public AccountsForCategoryELO getAccountsForCategory(int param1) {
      VirementAccountDAO dao = new VirementAccountDAO();
      return dao.getAccountsForCategory(param1);
   }

   public AllRechargesELO getAllRecharges() {
      RechargeDAO dao = new RechargeDAO();
      return dao.getAllRecharges();
   }

   public AllRechargesWithModelELO getAllRechargesWithModel(int param1) {
      RechargeDAO dao = new RechargeDAO();
      return dao.getAllRechargesWithModel(param1);
   }

   public SingleRechargeELO getSingleRecharge(int param1) {
      RechargeDAO dao = new RechargeDAO();
      return dao.getSingleRecharge(param1);
   }

   public ActivitiesForCycleandElementELO getActivitiesForCycleandElement(int param1, Integer param2, int param3) {
      BudgetActivityDAO dao = new BudgetActivityDAO();
      return dao.getActivitiesForCycleandElement(param1, param2, param3);
   }

   public ActivityDetailsELO getActivityDetails(int param1) {
      BudgetActivityDAO dao = new BudgetActivityDAO();
      return dao.getActivityDetails(param1);
   }

   public ActivityFullDetailsELO getActivityFullDetails(int param1) {
      BudgetActivityDAO dao = new BudgetActivityDAO();
      return dao.getActivityFullDetails(param1);
   }

   public AllVirementRequestsELO getAllVirementRequests() {
      VirementRequestDAO dao = new VirementRequestDAO();
      return dao.getAllVirementRequests();
   }

   public AllVirementRequestGroupsELO getAllVirementRequestGroups() {
      VirementRequestGroupDAO dao = new VirementRequestGroupDAO();
      return dao.getAllVirementRequestGroups();
   }

   public AllResponsibilityAreasELO getAllResponsibilityAreas() {
      ResponsibilityAreaDAO dao = new ResponsibilityAreaDAO();
      return dao.getAllResponsibilityAreas();
   }

   public AllWeightingProfilesELO getAllWeightingProfiles() {
      WeightingProfileDAO dao = new WeightingProfileDAO();
      return dao.getAllWeightingProfiles();
   }

   public AllWeightingDeploymentsELO getAllWeightingDeployments() {
      WeightingDeploymentDAO dao = new WeightingDeploymentDAO();
      return dao.getAllWeightingDeployments();
   }

   public AllCcDeploymentsELO getAllCcDeployments() {
      CcDeploymentDAO dao = new CcDeploymentDAO();
      return dao.getAllCcDeployments();
   }

   public CcDeploymentsForLookupTableELO getCcDeploymentsForLookupTable(String param1) {
      CcDeploymentDAO dao = new CcDeploymentDAO();
      return dao.getCcDeploymentsForLookupTable(param1);
   }

   public CcDeploymentsForXmlFormELO getCcDeploymentsForXmlForm(int param1) {
      CcDeploymentDAO dao = new CcDeploymentDAO();
      return dao.getCcDeploymentsForXmlForm(param1);
   }

   public CcDeploymentsForModelELO getCcDeploymentsForModel(int param1) {
      CcDeploymentDAO dao = new CcDeploymentDAO();
      return dao.getCcDeploymentsForModel(param1);
   }

   public CcDeploymentCellPickerInfoELO getCcDeploymentCellPickerInfo(int param1) {
      CcDeploymentDAO dao = new CcDeploymentDAO();
      return dao.getCcDeploymentCellPickerInfo(param1);
   }

   public CcDeploymentXMLFormTypeELO getCcDeploymentXMLFormType(int param1) {
      CcDeploymentDAO dao = new CcDeploymentDAO();
      return dao.getCcDeploymentXMLFormType(param1);
   }

   public AllFormRebuildsELO getAllFormRebuilds() {
      FormRebuildDAO dao = new FormRebuildDAO();
      return dao.getAllFormRebuilds();
   }

   public AllBudgetCyclesInRebuildsELO getAllBudgetCyclesInRebuilds() {
      FormRebuildDAO dao = new FormRebuildDAO();
      return dao.getAllBudgetCyclesInRebuilds();
   }

   public BudgetDetailsForUserELO getBudgetDetailsForUser(int userId, boolean detailedSelection, int locationId, int cycleId) {
      BudgetUserDAO dao = new BudgetUserDAO();
      return dao.getBudgetDetailsForUser(userId, detailedSelection, locationId, cycleId);
   }

   public EntityList getBudgetUserDetails(int bcId, int[] structureElementId) {
      BudgetUserDAO dao = new BudgetUserDAO();
      return dao.getBudgetUserDetails(bcId, structureElementId);
   }

   public EntityList getBudgetUserDetailsNodeDown(int bcId, int structureElementId, int structureId) {
      BudgetUserDAO dao = new BudgetUserDAO();
      return dao.getBudgetUserDetailsNodeDown(bcId, structureElementId, structureId);
   }

   public EntityList getBudgetUserAuthDetailsNodeUp(int bcId, int structureElementId, int structureId) {
      BudgetUserDAO dao = new BudgetUserDAO();
      return dao.getBudgetUserAuthDetailsNodeUp(bcId, structureElementId, structureId);
   }

   public void flush(ModelPK pk) throws Exception {
      this.getLocal(pk).flush();
   }

	public AllModelsELO getAllModelsForLoggedUser(int userId) {
	      ModelDAO dao = new ModelDAO();
	      return dao.getAllModelsForLoggedUser(userId);
	}

	public Map<String, String> getPropertiesForModelVisId(String modelVisId) {
		ModelDAO dao = new ModelDAO();
		return dao.getPropertiesForModelVisId(modelVisId);
	}

	public Map<String, String> getPropertiesForModelId(int modelId){
		ModelDAO dao = new ModelDAO();
		return dao.getPropertiesForModelId(modelId);
	}
    
	public AllModelsELO getAllModelsForGlobalMappedModel(int modelId) {
        ModelDAO dao = new ModelDAO();
        return dao.getAllModelsForGlobalMappedModel(modelId);
    }

	public AllFinanceCubesELO getAllFinanceCubesForLoggedUser(int userId) {
	       //FinanceCubeDAO financeDao = new FinanceCubeDAO();
	      return financeDao.getAllFinanceCubesForLoggedUser(userId);
	}

	public AllCubeFormulasELO getAllCubeFormulasForLoggedUser(int userId) {
		CubeFormulaDAO dao = new CubeFormulaDAO();
	    return dao.getAllCubeFormulasForLoggedUser(userId);
	}

	public AllModelsELO getModelsForLoggedUser(int userId) {
		ModelDAO dao = new ModelDAO();
	    return dao.getAllModelsForLoggedUser(userId);
	}

	public AllWeightingProfilesELO getAllWeightingProfilesForLoggedUser(int userId) {
		WeightingProfileDAO dao = new WeightingProfileDAO();
	      return dao.getAllWeightingProfilesForLoggedUser(userId);
	}

	public EntityList getAllWeightingDeploymentsForLoggedUser(int userId) {
		WeightingDeploymentDAO dao = new WeightingDeploymentDAO();
	      return dao.getAllWeightingDeploymentsForLoggedUser(userId);
	}

	public AllFormRebuildsELO getAllFormRebuildsForLoggedUser(int userId) {
		FormRebuildDAO dao = new FormRebuildDAO();
	    return dao.getAllFormRebuildsForLoggedUser(userId);
	}

}
