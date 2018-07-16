// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:04:17
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.api.cubeformula;

import com.cedar.cp.api.base.BusinessEditor;
import com.cedar.cp.api.base.EntityList;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.cubeformula.CubeFormula;
import com.cedar.cp.api.cubeformula.FormulaDeploymentLineEditor;
import com.cedar.cp.api.dimension.DimensionRef;
import com.cedar.cp.api.model.FinanceCubeRef;
import com.cedar.cp.api.model.ModelRef;

public interface CubeFormulaEditor extends BusinessEditor {

   void setDeploymentInd(boolean var1) throws ValidationException;

   void setFormulaType(int var1) throws ValidationException;

   void setVisId(String var1) throws ValidationException;

   void setDescription(String var1) throws ValidationException;

   void setFormulaText(String var1) throws ValidationException;

   void setFinanceCubeRef(FinanceCubeRef var1) throws ValidationException;

   void setModelRef(ModelRef var1) throws ValidationException;

   EntityList getOwnershipRefs();

   CubeFormula getCubeFormula();

   FormulaDeploymentLineEditor getFormulaDeploymentLineEditor(Object var1) throws ValidationException;

   void removeFormulaDeploymentLine(Object var1) throws ValidationException;

   DimensionRef[] getDimensionRefs();

   String testCompile() throws ValidationException;
}
