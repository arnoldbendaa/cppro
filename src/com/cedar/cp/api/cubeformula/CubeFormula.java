// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:04:17
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.api.cubeformula;

import com.cedar.cp.api.cubeformula.FormulaDeploymentLine;
import com.cedar.cp.api.dimension.DimensionRef;
import com.cedar.cp.api.model.FinanceCubeRef;
import com.cedar.cp.api.model.ModelRef;
import java.util.List;

public interface CubeFormula {

   int AUTOMATIC = 0;
   int MANUAL = 1;


   Object getPrimaryKey();

   String getVisId();

   String getDescription();

   String getFormulaText();

   boolean isDeploymentInd();

   int getFormulaType();

   FinanceCubeRef getFinanceCubeRef();

   ModelRef getModelRef();

   List<FormulaDeploymentLine> getDeploymentLines();

   FormulaDeploymentLine getFormulaDeploymentLine(Object var1);

   DimensionRef[] getDimensionRefs();

   boolean isFinanceCubeFormulaEnabled();
}
