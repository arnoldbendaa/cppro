// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:07:04
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.base.cube.formula;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.antlr.runtime.ANTLRStringStream;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.tree.CommonTree;
import org.antlr.runtime.tree.CommonTreeNodeStream;
import org.antlr.stringtemplate.StringTemplate;
import org.antlr.stringtemplate.StringTemplateGroup;

import com.cedar.cp.api.base.EntityList;
import com.cedar.cp.api.base.TextValidationException;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.dto.cubeformula.AllPackagesForFinanceCubeELO;
import com.cedar.cp.dto.cubeformula.CubeFormulaPackageCK;
import com.cedar.cp.dto.cubeformula.CubeFormulaPackagePK;
import com.cedar.cp.dto.cubeformula.CubeFormulaPackageRefImpl;
import com.cedar.cp.dto.model.FinanceCubeCK;
import com.cedar.cp.dto.model.FinanceCubePK;
import com.cedar.cp.dto.model.ModelCK;
import com.cedar.cp.dto.model.ModelPK;
//import com.cedar.cp.ejb.base.cube.formula.parser.CubeCellRangeRef;
//import com.cedar.cp.ejb.base.cube.formula.parser.CubeCellRef;
//import com.cedar.cp.ejb.base.cube.formula.parser.CubeFormulaLexer;
//import com.cedar.cp.ejb.base.cube.formula.parser.CubeFormulaParser;
//import com.cedar.cp.ejb.base.cube.formula.parser.CubeFormulaTree;
//import com.cedar.cp.ejb.base.cube.formula.parser.FormulaNodeAdapter;
import com.cedar.cp.ejb.base.cube.formula.tablemeta.MetaTable;
import com.cedar.cp.ejb.base.cube.formula.tablemeta.MetaTableManager;
import com.cedar.cp.ejb.impl.cubeformula.CubeFormulaDAO;
import com.cedar.cp.ejb.impl.cubeformula.CubeFormulaPackageDAO;
import com.cedar.cp.ejb.impl.cubeformula.CubeFormulaPackageEVO;
import com.cedar.cp.ejb.impl.datatype.DataTypeDAO;
import com.cedar.cp.ejb.impl.datatype.DataTypeEVO;
import com.cedar.cp.ejb.impl.model.FinanceCubeDAO;
import com.cedar.cp.ejb.impl.model.FinanceCubeEVO;
import com.cedar.cp.ejb.impl.model.ModelDAO;
import com.cedar.cp.ejb.impl.model.ModelEVO;
import com.cedar.cp.ejb.impl.systemproperty.SystemPropertyDAO;
import com.cedar.cp.util.Log;

public class FormulaCompiler {

   private static final String sCOMPILER_TEMPLATE_GROUP_FILE = "parser/cubeFormula.stg";
   private DataTypeDAO mDataTypeDAO;
   private Map<String, DataTypeEVO> mDataTypeMap = new HashMap();
   private FormulaDAO mFormulaDAO;
   private CubeFormulaDAO mCubeFormulaDAO;
   private CubeFormulaPackageDAO mCubeFormulaPackageDAO;
   private FinanceCubeDAO mFinanceCubeDAO;
   private ModelDAO mModelDAO;
   private SystemPropertyDAO mSystemPropertyDAO;
   private MetaTableManager mMetaTableManager;
   private static final String sPKG_KERNEL_HEADER_KEY = "packageKernelHeaderKey";
   private static final String sPKG_KERNEL_BODY_KEY = "packageKernelBodyKey";
   private static final String sPKG_GROUP_HEADER_KEY = "packageGroupHeaderKey";
   private static final String sPKG_GROUP_BODY_KEY = "packageGroupBodyKey";
   private Log mLog = new Log(FormulaCompiler.class);


   public FormulaCompiler(FormulaDAO formulaDAO, CubeFormulaDAO cubeFormulaDAO, CubeFormulaPackageDAO cubeFormulaPackage, ModelDAO modelDAO, FinanceCubeDAO financeCubeDAO, DataTypeDAO dataTypeDAO, SystemPropertyDAO systemPropertyDAO, MetaTableManager metaTableManager) {
      this.mFormulaDAO = formulaDAO;
      this.mCubeFormulaDAO = cubeFormulaDAO;
      this.mCubeFormulaPackageDAO = cubeFormulaPackage;
      this.mModelDAO = modelDAO;
      this.mFinanceCubeDAO = financeCubeDAO;
      this.mDataTypeDAO = dataTypeDAO;
      this.mSystemPropertyDAO = systemPropertyDAO;
      this.mMetaTableManager = metaTableManager;
   }

   public void dropAllPackages(int financeCubeId) throws ValidationException {
      EntityList activePackages = this.getCubeFormulaPackageDAO().queryActivePackages(financeCubeId);

      for(int i = 0; i < activePackages.getNumRows(); ++i) {
         int package_group_index = ((Integer)activePackages.getValueAt(i, "package_group_index")).intValue();
         this.getFormulaDAO().dropPackage(this.queryFormulaPackageName(financeCubeId, package_group_index));
      }

      this.getCubeFormulaPackageDAO().delete(financeCubeId);
      this.dropPackageKernel(financeCubeId);
   }

   public void removeAll(int financeCubeId, boolean deleteRuntimeData) throws ValidationException {
      FinanceCubeInfo financeCubeInfo = this.getFinanceCubeDAO().getFinanceCubeInfo(financeCubeId);
      if(deleteRuntimeData) {
         List packages = this.getFormulaDAO().queryDeployedFormula(financeCubeId);
         Iterator i = packages.iterator();

         while(i.hasNext()) {
            Integer packageIndex = (Integer)i.next();
            this.getFormulaDAO().removeFormula(financeCubeId, packageIndex.intValue());
         }
      }

      AllPackagesForFinanceCubeELO var8 = this.getCubeFormulaPackageDAO().getAllPackagesForFinanceCube(financeCubeId);

      for(int var9 = 0; var9 < var8.getNumRows(); ++var9) {
         int var10 = ((Integer)var8.getValueAt(var9, "PackageGroupIndex")).intValue();
         CubeFormulaPackageRefImpl cfpRef = (CubeFormulaPackageRefImpl)var8.getValueAt(var9, "CubeFormulaPackage");
         this.dropPackageGroup(financeCubeId, var10);
         this.deleteCubeFormulaPackage(financeCubeInfo, this.loadCubeFormulaPackage(financeCubeInfo, cfpRef.getCubeFormulaPackagePK().getCubeFormulaPackageId()));
      }

      this.dropPackageKernel(financeCubeId);
   }

   public void remove(int financeCubeId, Set<Integer> cubeFormulaIdsToRemove, boolean updateCubeFormulaDeployedInd) throws ValidationException {
      FinanceCubeInfo financeCubeInfo = this.getFinanceCubeDAO().getFinanceCubeInfo(financeCubeId);
      HashSet removedFormula = new HashSet();

      while(removedFormula.size() < cubeFormulaIdsToRemove.size()) {
         int cubeFormulaId = ((Integer)cubeFormulaIdsToRemove.iterator().next()).intValue();
         int packageId = this.getFormulaDAO().queryFormulaPackageId(financeCubeId, cubeFormulaId);
         this.getFormulaDAO().removeFormula(financeCubeId, cubeFormulaId);
         removedFormula.add(Integer.valueOf(cubeFormulaId));
         cubeFormulaIdsToRemove.remove(Integer.valueOf(cubeFormulaId));
         if(updateCubeFormulaDeployedInd) {
            this.getCubeFormulaDAO().updateDeployedIndicator(cubeFormulaId, false);
         }

         if(packageId != -1) {
            Set formulaInPackage = this.getFormulaDAO().queryFormulaForPackageId(financeCubeId, packageId);
            Iterator packageSourceMap = formulaInPackage.iterator();

            while(packageSourceMap.hasNext()) {
               int cfpCK = ((Integer)packageSourceMap.next()).intValue();
               if(cubeFormulaIdsToRemove.contains(Integer.valueOf(cfpCK))) {
                  this.getFormulaDAO().removeFormula(financeCubeId, cfpCK);
                  removedFormula.add(Integer.valueOf(cfpCK));
                  cubeFormulaIdsToRemove.remove(Integer.valueOf(cfpCK));
                  if(updateCubeFormulaDeployedInd) {
                     this.getCubeFormulaDAO().updateDeployedIndicator(cfpCK, false);
                  }
               }
            }

            HashMap packageSourceMap1 = new HashMap();
            CubeFormulaPackageCK cfpCK1 = new CubeFormulaPackageCK(new ModelPK(financeCubeInfo.getModelId()), new FinanceCubePK(financeCubeId), new CubeFormulaPackagePK(packageId));
            CubeFormulaPackageEVO cubeFormulaPackageEVO = this.getCubeFormulaPackageDAO().getDetails(cfpCK1, "");
            int packageIndex = cubeFormulaPackageEVO.getPackageGroupIndex();
            StringTemplateGroup templateGroup = this.loadCubeFormulaTemplate();
            this.generatePackage(financeCubeInfo, cubeFormulaPackageEVO.getPackageGroupIndex(), templateGroup, packageSourceMap1);
            int lineCount = this.lineCount((String)packageSourceMap1.get("packageGroupBodyKey"));
            if(lineCount < 5) {
               this.dropPackageGroup(financeCubeInfo.getFinanceCubeId(), cubeFormulaPackageEVO.getPackageGroupIndex());
               this.deleteCubeFormulaPackage(financeCubeInfo, cubeFormulaPackageEVO);
            } else {
               cubeFormulaPackageEVO.setLineCount(lineCount);
               this.updateCubeFormulaPackage(financeCubeInfo, cubeFormulaPackageEVO);
               String kernelPackageName = this.queryFormulaPackageKernelName(financeCubeInfo.getFinanceCubeId());
               String kernelPackageHeaderSrc = (String)packageSourceMap1.get("packageKernelHeaderKey");
               String kernelPackageBodySrc = (String)packageSourceMap1.get("packageKernelBodyKey");
               this.getFormulaDAO().definePackage(kernelPackageName, kernelPackageHeaderSrc, true);
               this.getFormulaDAO().definePackage(kernelPackageName, kernelPackageBodySrc, false);
               String groupPackageName = this.queryFormulaPackageName(financeCubeInfo.getFinanceCubeId(), packageIndex);
               String groupPackageHeaderSrc = (String)packageSourceMap1.get("packageGroupHeaderKey");
               String groupPackageBodySrc = (String)packageSourceMap1.get("packageGroupBodyKey");
               this.getFormulaDAO().definePackage(groupPackageName, groupPackageHeaderSrc, true);
               this.getFormulaDAO().definePackage(groupPackageName, groupPackageBodySrc, false);
            }
         }
      }

   }

   public void compileAll(int financeCubeId) throws ValidationException {
      EntityList activeFormula = this.getFormulaDAO().queryActiveFormula(financeCubeId);

      for(int financeCubeInfo = 0; financeCubeInfo < activeFormula.getNumRows(); ++financeCubeInfo) {
         int templateGroup = ((Integer)activeFormula.getValueAt(financeCubeInfo, "cube_formula_id")).intValue();
         String packageSourceMap = (String)activeFormula.getValueAt(financeCubeInfo, "formula_text");
         int kernelPackageName = ((Integer)activeFormula.getValueAt(financeCubeInfo, "formula_type")).intValue();
         this.compile(financeCubeId, templateGroup, packageSourceMap, kernelPackageName, false);
      }

      if(activeFormula.getNumRows() == 0) {
         FinanceCubeInfo var9 = this.getFinanceCubeDAO().getFinanceCubeInfo(financeCubeId);
         StringTemplateGroup var10 = this.loadCubeFormulaTemplate();
         HashMap var11 = new HashMap();
         this.generatePackageKernel(var9, var10, Collections.EMPTY_LIST, var11);
         String var12 = this.queryFormulaPackageKernelName(var9.getFinanceCubeId());
         String kernelPackageHeaderSrc = (String)var11.get("packageKernelHeaderKey");
         String kernelPackageBodySrc = (String)var11.get("packageKernelBodyKey");
         this.getFormulaDAO().definePackage(var12, kernelPackageHeaderSrc, true);
         this.getFormulaDAO().definePackage(var12, kernelPackageBodySrc, false);
      }

   }

   public void compile(int financeCubeId, int cubeFormulaId, String formula, int formulaType, boolean testCompile) throws ValidationException {
      FinanceCubeInfo financeCubeInfo = this.getFinanceCubeDAO().getFinanceCubeInfo(financeCubeId);
      StringTemplateGroup templateGroup = this.loadCubeFormulaTemplate();
//      String formulaRoutineSrc = this.compileFormula(cubeFormulaId, templateGroup, formula, formulaType, 0, financeCubeInfo, false);
//      if(this.mLog.isDebugEnabled()) {
//         this.mLog.debug("compile", formulaRoutineSrc);
//      }

      int maxPackageLines = this.queryMaximumPackageLines();
      int packageIndex;
      HashMap packageSourceMap;
      String cubeFormulaPackageEVO;
      String kernelPackageHeaderSrc;
      String kernelPackageName;
      if(testCompile) {
         packageIndex = Integer.MAX_VALUE;

         try {
            ArrayList packageId = new ArrayList();
            ArrayList isNewPackage = new ArrayList();
//            packageId.add(formulaRoutineSrc);
            isNewPackage.add("formula" + cubeFormulaId);
            packageSourceMap = new HashMap();
            this.generatePackageGroup(packageIndex, financeCubeInfo, templateGroup, isNewPackage, packageId, packageSourceMap);
            cubeFormulaPackageEVO = this.queryFormulaPackageName(financeCubeInfo.getFinanceCubeId(), packageIndex);
            String lineCount = (String)packageSourceMap.get("packageGroupHeaderKey");
            kernelPackageName = this.queryFormulaPackageName(financeCubeInfo.getFinanceCubeId(), packageIndex);
            kernelPackageHeaderSrc = (String)packageSourceMap.get("packageGroupBodyKey");
            if(this.lineCount(kernelPackageHeaderSrc) > maxPackageLines) {
               throw new ValidationException("Formula too large for package - see system property \'SYS: Max Package Lines\'");
            }

            if(financeCubeInfo.isCubeFormulaEnabled()) {
               this.getFormulaDAO().definePackage(cubeFormulaPackageEVO, lineCount, true);
               this.getFormulaDAO().definePackage(kernelPackageName, kernelPackageHeaderSrc, false);
            }
         } finally {
            try {
               this.dropPackageGroup(financeCubeInfo.getFinanceCubeId(), packageIndex);
            } catch (Throwable var25) {
               this.mLog.error("compile", "Failed to drop test compile package:" + var25.getMessage());
            }

         }
      } else {
         byte packageIndex1 = 0;
         boolean packageId1 = false;
         boolean isNewPackage1 = false;
         packageSourceMap = new HashMap();
         int packageId2 = this.getFormulaDAO().queryFormulaPackageId(financeCubeId, cubeFormulaId);
         cubeFormulaPackageEVO = null;
         boolean lineCount1 = false;
         int lineCount2;
         CubeFormulaPackageCK kernelPackageHeaderSrc1;
         CubeFormulaPackageEVO cubeFormulaPackageEVO1;
         int kernelPackageName1;
         if(packageId2 < 0) {
            kernelPackageName1 = this.getCubeFormulaPackageDAO().queryLowestLineCountPackageForFinanceCube(financeCubeId);
            if(kernelPackageName1 <= 0) {
               kernelPackageName1 = this.createNewCubeFormulaPackage(financeCubeInfo);
               isNewPackage1 = true;
            }

//            this.compileFormula(cubeFormulaId, templateGroup, formula, formulaType, packageIndex1, financeCubeInfo, true);

            while(true) {
               kernelPackageHeaderSrc1 = new CubeFormulaPackageCK(new ModelPK(financeCubeInfo.getModelId()), new FinanceCubePK(financeCubeId), new CubeFormulaPackagePK(kernelPackageName1));
               cubeFormulaPackageEVO1 = this.getCubeFormulaPackageDAO().getDetails(kernelPackageHeaderSrc1, "");
               packageIndex = cubeFormulaPackageEVO1.getPackageGroupIndex();
               packageId2 = cubeFormulaPackageEVO1.getCubeFormulaPackageId();
               this.getFormulaDAO().updateFormula(financeCubeId, cubeFormulaId, packageIndex, packageId2);
               this.generatePackage(financeCubeInfo, cubeFormulaPackageEVO1.getPackageGroupIndex(), templateGroup, packageSourceMap);
               lineCount2 = this.lineCount((String)packageSourceMap.get("packageGroupBodyKey"));
               if(lineCount2 <= maxPackageLines) {
                  break;
               }

               if(isNewPackage1) {
                  throw new ValidationException("Formula too large for package - see system property \'SYS: Max Package Lines\'");
               }

               kernelPackageName1 = this.createNewCubeFormulaPackage(financeCubeInfo);
               isNewPackage1 = true;
            }
         } else {
            kernelPackageName1 = packageId2;

            while(true) {
               kernelPackageHeaderSrc1 = new CubeFormulaPackageCK(new ModelPK(financeCubeInfo.getModelId()), new FinanceCubePK(financeCubeId), new CubeFormulaPackagePK(kernelPackageName1));
               cubeFormulaPackageEVO1 = this.getCubeFormulaPackageDAO().getDetails(kernelPackageHeaderSrc1, "");
               packageIndex = cubeFormulaPackageEVO1.getPackageGroupIndex();
               packageId2 = cubeFormulaPackageEVO1.getCubeFormulaPackageId();
               this.getFormulaDAO().updateFormula(financeCubeId, cubeFormulaId, packageIndex, packageId2);
               this.generatePackage(financeCubeInfo, cubeFormulaPackageEVO1.getPackageGroupIndex(), templateGroup, packageSourceMap);
               lineCount2 = this.lineCount((String)packageSourceMap.get("packageGroupBodyKey"));
               if(lineCount2 <= maxPackageLines) {
                  break;
               }

               if(isNewPackage1) {
                  throw new ValidationException("Formula too large for package - see system property \'SYS: Max Package Lines\'");
               }

               kernelPackageName1 = this.getCubeFormulaPackageDAO().queryLowestLineCountPackageForFinanceCube(financeCubeId);
               if(kernelPackageName1 <= 0 || kernelPackageName1 == packageId2) {
                  kernelPackageName1 = this.createNewCubeFormulaPackage(financeCubeInfo);
                  isNewPackage1 = true;
               }
            }
         }

         cubeFormulaPackageEVO1.setLineCount(lineCount2);
         this.updateCubeFormulaPackage(financeCubeInfo, cubeFormulaPackageEVO1);
         kernelPackageName = this.queryFormulaPackageKernelName(financeCubeInfo.getFinanceCubeId());
         kernelPackageHeaderSrc = (String)packageSourceMap.get("packageKernelHeaderKey");
         String kernelPackageBodySrc = (String)packageSourceMap.get("packageKernelBodyKey");
         this.getFormulaDAO().definePackage(kernelPackageName, kernelPackageHeaderSrc, true);
         this.getFormulaDAO().definePackage(kernelPackageName, kernelPackageBodySrc, false);
         String groupPackageName = this.queryFormulaPackageName(financeCubeInfo.getFinanceCubeId(), packageIndex);
         String groupPackageHeaderSrc = (String)packageSourceMap.get("packageGroupHeaderKey");
         String groupPackageBodySrc = (String)packageSourceMap.get("packageGroupBodyKey");
         this.getFormulaDAO().definePackage(groupPackageName, groupPackageHeaderSrc, true);
         this.getFormulaDAO().definePackage(groupPackageName, groupPackageBodySrc, false);
      }

   }

   private void generatePackage(FinanceCubeInfo financeCubeInfo, int sourceGroupIndex, StringTemplateGroup templateGroup, Map<String, String> packageSourceMap) throws ValidationException {
      EntityList formulaeInGroup = this.getFormulaDAO().queryFormulaeForSourceGroup(financeCubeInfo.getFinanceCubeId(), sourceGroupIndex);
      ArrayList formulaProcedures = new ArrayList();
      ArrayList formulaRoutineNames = new ArrayList();

      for(int i = 0; i < formulaeInGroup.getNumRows(); ++i) {
         int formulaId = ((Integer)formulaeInGroup.getValueAt(i, "formula_id")).intValue();
         String formulaText = (String)formulaeInGroup.getValueAt(i, "formula_text");
         int formulaType = ((Integer)formulaeInGroup.getValueAt(i, "formula_type")).intValue();
//         formulaProcedures.add(this.compileFormula(formulaId, templateGroup, formulaText, formulaType, sourceGroupIndex, financeCubeInfo, true));
         formulaRoutineNames.add("formula" + formulaId);
      }

      this.getFormulaDAO().checkForCyclicFormulaDeployments(financeCubeInfo.getFinanceCubeId(), financeCubeInfo.getNumDims(), financeCubeInfo.getCalHierId());
      this.generatePackageKernel(financeCubeInfo, templateGroup, formulaProcedures, packageSourceMap);
      this.generatePackageGroup(sourceGroupIndex, financeCubeInfo, templateGroup, formulaRoutineNames, formulaProcedures, packageSourceMap);
   }

   private void generatePackageKernel(FinanceCubeInfo financeCubeInfo, StringTemplateGroup templateGroup, List<String> formulaProcedures, Map<String, String> packageSourceMap) throws ValidationException {
      StringTemplate packageTemplate = templateGroup.getTemplateDefinition("formulaPackageKernelHeader");
      packageTemplate.reset();
      packageTemplate.setAttribute("financeCubeId", financeCubeInfo.getFinanceCubeId());
      packageTemplate.setAttribute("calHierId", financeCubeInfo.getCalHierId());
      packageTemplate.setAttribute("dims", financeCubeInfo.getDims());
      packageTemplate.setAttribute("odims", financeCubeInfo.getODims());
      packageTemplate.setAttribute("cal", financeCubeInfo.getNumDims() - 1);
      packageTemplate.setAttribute("formulaRoutines", formulaProcedures);
      packageSourceMap.put("packageKernelHeaderKey", packageTemplate.toString());
      packageTemplate = templateGroup.getTemplateDefinition("formulaPackageKernelBody");
      packageTemplate.reset();
      packageTemplate.setAttribute("financeCubeId", financeCubeInfo.getFinanceCubeId());
      packageTemplate.setAttribute("calHierId", financeCubeInfo.getCalHierId());
      packageTemplate.setAttribute("dims", financeCubeInfo.getDims());
      packageTemplate.setAttribute("odims", financeCubeInfo.getODims());
      packageTemplate.setAttribute("cal", financeCubeInfo.getNumDims() - 1);
      packageTemplate.setAttribute("formulaRoutines", formulaProcedures);
      packageSourceMap.put("packageKernelBodyKey", packageTemplate.toString());
   }

   private void generatePackageGroup(int sourceGroupIndex, FinanceCubeInfo financeCubeInfo, StringTemplateGroup templateGroup, List<String> formulaRoutineNames, List<String> formulaProcedures, Map<String, String> packageSourceMap) throws ValidationException {
      StringTemplate packageTemplate = templateGroup.getTemplateDefinition("formulaGroupHeader");
      packageTemplate.reset();
      packageTemplate.setAttribute("financeCubeId", financeCubeInfo.getFinanceCubeId());
      packageTemplate.setAttribute("calHierId", financeCubeInfo.getCalHierId());
      packageTemplate.setAttribute("dims", financeCubeInfo.getDims());
      packageTemplate.setAttribute("odims", financeCubeInfo.getODims());
      packageTemplate.setAttribute("cal", financeCubeInfo.getNumDims() - 1);
      packageTemplate.setAttribute("formulaRoutineNames", formulaRoutineNames);
      packageTemplate.setAttribute("groupNo", sourceGroupIndex);
      String packageHeaderText = packageTemplate.toString();
      packageSourceMap.put("packageGroupHeaderKey", packageHeaderText);
      packageTemplate = templateGroup.getTemplateDefinition("formulaGroupBody");
      packageTemplate.reset();
      packageTemplate.setAttribute("financeCubeId", financeCubeInfo.getFinanceCubeId());
      packageTemplate.setAttribute("calHierId", financeCubeInfo.getCalHierId());
      packageTemplate.setAttribute("dims", financeCubeInfo.getDims());
      packageTemplate.setAttribute("odims", financeCubeInfo.getODims());
      packageTemplate.setAttribute("cal", financeCubeInfo.getNumDims() - 1);
      packageTemplate.setAttribute("formulaRoutines", formulaProcedures);
      packageTemplate.setAttribute("groupNo", sourceGroupIndex);
      String packageBodyText = packageTemplate.toString();
      packageSourceMap.put("packageGroupBodyKey", packageBodyText);
   }

   private void dropPackageGroup(int financeCubeId, int sourceGroupIndex) {
      try {
         this.getFormulaDAO().dropPackage(this.queryFormulaPackageName(financeCubeId, sourceGroupIndex));
      } catch (ValidationException var4) {
         var4.printStackTrace();
         this.mLog.error("dropPackageGroup", var4.getMessage());
      }

   }

   private void dropPackageKernel(int financeCubeId) {
      try {
         this.getFormulaDAO().dropPackage(this.queryFormulaPackageKernelName(financeCubeId));
      } catch (ValidationException var3) {
         var3.printStackTrace();
         this.mLog.error("dropPackageKernel", var3.getMessage());
      }

   }

   private String queryFormulaPackageName(int financeCubeId, int sourceGroupIndex) {
      return "FOR" + financeCubeId + "_GRP" + sourceGroupIndex;
   }

   private String queryFormulaPackageKernelName(int financeCubeId) {
      return "FOR" + financeCubeId + "_KERNEL";
   }

   private String compileFormula(int formulaId, StringTemplateGroup templateGroup, String formula, int formulaType, int packageIndex, FinanceCubeInfo financeCubeInfo, boolean deploy) throws ValidationException, RecognitionException {
      Set targetDataTypes = this.getFormulaDAO().queryDeploymentDataTypes(formulaId);
      this.queryDataTypeMapForFinanceCube(financeCubeInfo.getFinanceCubeId());
      FormulaCompiler$1 semanticChecker = new FormulaCompiler$1(this);
      ANTLRStringStream input = new ANTLRStringStream(formula);
//      CubeFormulaLexer lexer = new CubeFormulaLexer(input);
//      CommonTokenStream tokens = new CommonTokenStream(lexer);
//      CubeFormulaParser parser = new CubeFormulaParser(tokens);
//      parser.setNumDims(financeCubeInfo.getNumDims());
//      parser.setTreeAdaptor(new FormulaNodeAdapter());
//      parser.setSemanticChecker(semanticChecker);
//
//      CubeFormulaParser.translation_unit_return tur;
      try {
//         tur = parser.translation_unit();
      } catch (Throwable var23) {
         var23.printStackTrace();
         throw new ValidationException(var23.getMessage());
      }
	return formula;

//      if(parser.getCubeCellRefs().isEmpty() && parser.getCubeCellRangeRefs().isEmpty()) {
//         throw new ValidationException("The formula must contain at least one cell or range reference expression.");
//      } else {
//         Iterator commonTree = parser.getCubeCellRefs().iterator();
//
//         CubeCellRef nodes2;
//         do {
//            if(!commonTree.hasNext()) {
//               commonTree = parser.getCubeCellRangeRefs().iterator();
//
//               CubeCellRangeRef nodes1;
//               do {
//                  if(!commonTree.hasNext()) {
//                     try {
//                        if(deploy) {
//                           this.getFormulaDAO().persistFormula(financeCubeInfo.getModelId(), financeCubeInfo.getFinanceCubeId(), formulaId, formulaType, financeCubeInfo.getNumDims(), financeCubeInfo.getCalHierId(), packageIndex, parser.getCubeCellRefs(), parser.getCubeCellRangeRefs(), parser.getLookups());
//                        }
//                     } catch (ValidationException var24) {
//                        throw var24;
//                     } catch (Throwable var25) {
//                        var25.printStackTrace();
//                        throw new ValidationException("Failed to deploy formula:" + var25.getMessage());
//                     }
//
//                     CommonTree commonTree1 = (CommonTree)tur.getTree();
//                     CommonTreeNodeStream nodes3 = new CommonTreeNodeStream(commonTree1);
//                     nodes3.setTokenStream(tokens);
//                     CubeFormulaTree treeWalker1 = new CubeFormulaTree(nodes3);
//                     treeWalker1.setCubeCellRefs(new ArrayList(parser.getCubeCellRefs()));
//                     treeWalker1.setCubeCellRangeRefs(new ArrayList(parser.getCubeCellRangeRefs()));
//                     treeWalker1.setLookups(new ArrayList(parser.getLookups()));
//                     treeWalker1.setNumDims(parser.getNumDims());
//                     treeWalker1.setCalHierId(financeCubeInfo.getCalHierId());
//                     treeWalker1.setFinanceCubeId(financeCubeInfo.getFinanceCubeId());
//                     treeWalker1.setFormula(parser.getFormula());
//                     treeWalker1.setFormulaId(formulaId);
//                     treeWalker1.setTemplateLib(templateGroup);
//                     treeWalker1.setSemanticChecker(semanticChecker);
//
//                     CubeFormulaTree.translation_unit_return result;
//                     try {
//                        result = treeWalker1.translation_unit();
//                     } catch (RecognitionException var20) {
//                        var20.printStackTrace();
//                        throw new TextValidationException(var20.getMessage(), var20.index, var20.line, var20.charPositionInLine);
//                     } catch (Throwable var21) {
//                        var21.printStackTrace();
//                        throw new ValidationException(var21.getMessage());
//                     }
//
//                     StringTemplate st = (StringTemplate)result.getTemplate();
//                     return st.toString(250);
//                  }
//
//                  nodes1 = (CubeCellRangeRef)commonTree.next();
//               } while(!nodes1.isNonCalDimsContextual() || nodes1.getDataType() != null && !targetDataTypes.contains(nodes1.getDataType()) || nodes1.isDateOffset() || !nodes1.isCalDimContextual());
//
//               throw new ValidationException("Cell range reference \'" + nodes1.getFormula() + "\' is illegal since it will cause a cyclic condition. ");
//            }
//
//            nodes2 = (CubeCellRef)commonTree.next();
//         } while(!nodes2.isNonCalDimsContextual() || nodes2.getDataType() != null && !targetDataTypes.contains(nodes2.getDataType()) || nodes2.isDateOffset() || !nodes2.isCalDimContextual());

//         throw new ValidationException("Cell reference \'" + nodes2.getFormula() + "\' is illegal since it will cause a cyclic condition. ");
//      }
   }

   private int createNewCubeFormulaPackage(FinanceCubeInfo financeCubeInfo) throws ValidationException {
      FinanceCubeCK fcCK = new FinanceCubeCK(new ModelPK(financeCubeInfo.getModelId()), new FinanceCubePK(financeCubeInfo.getFinanceCubeId()));
      ModelEVO modelEVO = this.getModelDAO().getDetails((ModelCK)fcCK, "<0><8>");
      FinanceCubeEVO financeCubeEVO = modelEVO.getFinanceCubesItem(new FinanceCubePK(financeCubeInfo.getFinanceCubeId()));
      int packageIndex = 0;
      if(financeCubeEVO.getCubeFormulaPackages() != null) {
         Iterator cubeFormulaPackageEVO = financeCubeEVO.getCubeFormulaPackages().iterator();

         while(cubeFormulaPackageEVO.hasNext()) {
            CubeFormulaPackageEVO i$ = (CubeFormulaPackageEVO)cubeFormulaPackageEVO.next();
            if(packageIndex <= i$.getPackageGroupIndex()) {
               packageIndex = i$.getPackageGroupIndex() + 1;
            }
         }
      }

      CubeFormulaPackageEVO cubeFormulaPackageEVO1 = new CubeFormulaPackageEVO(-1, financeCubeInfo.getFinanceCubeId(), packageIndex, 0);
      financeCubeEVO.addCubeFormulaPackagesItem(cubeFormulaPackageEVO1);
      modelEVO = this.getModelDAO().setAndGetDetails(modelEVO, "<0><8>");
      financeCubeEVO = modelEVO.getFinanceCubesItem(new FinanceCubePK(financeCubeInfo.getFinanceCubeId()));
      Iterator i$1 = financeCubeEVO.getCubeFormulaPackages().iterator();

      while(i$1.hasNext()) {
         CubeFormulaPackageEVO pkgEVO = (CubeFormulaPackageEVO)i$1.next();
         if(pkgEVO.getPackageGroupIndex() == packageIndex) {
            cubeFormulaPackageEVO1 = pkgEVO;
            break;
         }
      }

      this.getModelDAO().store();
      return cubeFormulaPackageEVO1.getPK().getCubeFormulaPackageId();
   }

   private void updateCubeFormulaPackage(FinanceCubeInfo financeCubeInfo, CubeFormulaPackageEVO srcCubeFormulaPackageEVO) throws ValidationException {
      FinanceCubeCK fcCK = new FinanceCubeCK(new ModelPK(financeCubeInfo.getModelId()), new FinanceCubePK(financeCubeInfo.getFinanceCubeId()));
      ModelEVO modelEVO = this.getModelDAO().getDetails((ModelCK)fcCK, "<0><8>");
      FinanceCubeEVO financeCubeEVO = modelEVO.getFinanceCubesItem(new FinanceCubePK(financeCubeInfo.getFinanceCubeId()));
      CubeFormulaPackageEVO cubeFormulaPackageEVO = financeCubeEVO.getCubeFormulaPackagesItem(srcCubeFormulaPackageEVO.getPK());
      cubeFormulaPackageEVO.setPackageGroupIndex(srcCubeFormulaPackageEVO.getPackageGroupIndex());
      cubeFormulaPackageEVO.setLineCount(srcCubeFormulaPackageEVO.getLineCount());
      this.getModelDAO().setDetails(modelEVO);
      this.getModelDAO().store();
   }

   private void deleteCubeFormulaPackage(FinanceCubeInfo financeCubeInfo, CubeFormulaPackageEVO srcCubeFormulaPackageEVO) throws ValidationException {
      FinanceCubeCK fcCK = new FinanceCubeCK(new ModelPK(financeCubeInfo.getModelId()), new FinanceCubePK(financeCubeInfo.getFinanceCubeId()));
      ModelEVO modelEVO = this.getModelDAO().getDetails((ModelCK)fcCK, "<0><8>");
      FinanceCubeEVO financeCubeEVO = modelEVO.getFinanceCubesItem(new FinanceCubePK(financeCubeInfo.getFinanceCubeId()));
      financeCubeEVO.deleteCubeFormulaPackagesItem(srcCubeFormulaPackageEVO.getPK());
      this.getModelDAO().setDetails(modelEVO);
      this.getModelDAO().store();
   }

   private CubeFormulaPackageEVO loadCubeFormulaPackage(FinanceCubeInfo financeCubeInfo, int cubeFormulaPackageId) throws ValidationException {
      CubeFormulaPackageCK ck = new CubeFormulaPackageCK(new ModelPK(financeCubeInfo.getModelId()), new FinanceCubePK(financeCubeInfo.getFinanceCubeId()), new CubeFormulaPackagePK(cubeFormulaPackageId));
      return this.getCubeFormulaPackageDAO().getDetails(ck, "");
   }

   private StringTemplateGroup loadCubeFormulaTemplate() throws ValidationException {
      try {
         return new StringTemplateGroup(this.getReader("parser/cubeFormula.stg"));
      } catch (IOException var2) {
         throw new ValidationException("Failed to load parser/cubeFormula.stg - " + var2.getMessage());
      }
   }

   private Reader getReader(String resourceName) throws IOException {
      InputStream is = this.getClass().getResourceAsStream(resourceName);
      if(is != null) {
         return new InputStreamReader(is);
      } else {
         File f = new File(resourceName);
         return new FileReader(f);
      }
   }

   private int queryMaximumPackageLines() {
      String value = this.getSystemPropertyDAO().getValue("SYS: Max Package Lines");

      try {
         return Integer.parseInt(value);
      } catch (NumberFormatException var3) {
         this.mLog.error("queryMaximumPackageLines", "Failed to parse system property \'SYS: Max Package Lines\'");
         return 1000;
      }
   }

   private int lineCount(String text) {
      int lineCount = 0;
      int length = text.length();

      for(int i = 0; i < length; ++i) {
         if(text.charAt(i) == 10) {
            ++lineCount;
         }
      }

      return lineCount;
   }

   public MetaTable queryLookupTable(String lookupTableName) {
      try {
         return this.getMetaTableManager().getTable(lookupTableName);
      } catch (ValidationException var3) {
         return null;
      }
   }

   public int getDataTypeScaling(String dataType) {
      DataTypeEVO dataTypeEVO = this.getDataTypeEVO(dataType);
      if(dataTypeEVO != null) {
         switch(dataTypeEVO.getSubType()) {
         case 0:
         case 1:
         case 2:
         case 3:
            return 10000;
         case 4:
            if(dataTypeEVO.getMeasureClass().intValue() == 1) {
               return (int)Math.pow(10.0D, (double)dataTypeEVO.getMeasureScale().intValue());
            }

            return 0;
         default:
            return 0;
         }
      } else {
         throw new IllegalArgumentException("Failed to locate details for data type:" + dataType);
      }
   }

   public void setFormulaDAO(FormulaDAO formulaDAO) {
      this.mFormulaDAO = formulaDAO;
   }

   public FormulaDAO getFormulaDAO() {
      return this.mFormulaDAO;
   }

   public CubeFormulaPackageDAO getCubeFormulaPackageDAO() {
      return this.mCubeFormulaPackageDAO;
   }

   public CubeFormulaDAO getCubeFormulaDAO() {
      return this.mCubeFormulaDAO;
   }

   public void setCubeFormulaDAO(CubeFormulaDAO cubeFormulaDAO) {
      this.mCubeFormulaDAO = cubeFormulaDAO;
   }

   public void setCubeFormulaPackageDAO(CubeFormulaPackageDAO cubeFormulaPackageDAO) {
      this.mCubeFormulaPackageDAO = cubeFormulaPackageDAO;
   }

   public FinanceCubeDAO getFinanceCubeDAO() {
      return this.mFinanceCubeDAO;
   }

   public void setFinanceCubeDAO(FinanceCubeDAO financeCubeDAO) {
      this.mFinanceCubeDAO = financeCubeDAO;
   }

   public SystemPropertyDAO getSystemPropertyDAO() {
      return this.mSystemPropertyDAO;
   }

   public void setSystemPropertyDAO(SystemPropertyDAO systemPropertyDAO) {
      this.mSystemPropertyDAO = systemPropertyDAO;
   }

   public ModelDAO getModelDAO() {
      return this.mModelDAO;
   }

   public void setModelDAO(ModelDAO modelDAO) {
      this.mModelDAO = modelDAO;
   }

   public MetaTableManager getMetaTableManager() {
      return this.mMetaTableManager;
   }

   public void setMetaTableManager(MetaTableManager metaTableManager) {
      this.mMetaTableManager = metaTableManager;
   }

   private void queryDataTypeMapForFinanceCube(int financeCubeId) {
      this.mDataTypeMap = this.getDataTypeDAO().getAllForFinanceCube(financeCubeId);
   }

   private DataTypeEVO getDataTypeEVO(String dataType) {
      return (DataTypeEVO)this.mDataTypeMap.get(dataType);
   }

   public DataTypeDAO getDataTypeDAO() {
      return this.mDataTypeDAO;
   }

   public void setDataTypeDAO(DataTypeDAO dataTypeDAO) {
      this.mDataTypeDAO = dataTypeDAO;
   }
}
