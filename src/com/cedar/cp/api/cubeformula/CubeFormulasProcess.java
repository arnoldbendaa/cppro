// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:04:17
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.api.cubeformula;

import com.cedar.cp.api.base.BusinessProcess;
import com.cedar.cp.api.base.EntityList;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.cubeformula.CubeFormulaEditorSession;
import com.cedar.cp.api.cubeformula.CubeFormulaRef;
import com.cedar.cp.api.model.FinanceCubeRef;
import com.cedar.cp.api.model.ModelRef;
import java.util.List;

public interface CubeFormulasProcess extends BusinessProcess {

   EntityList getAllCubeFormulas();
   
   EntityList getAllCubeFormulasForLoggedUser();

   EntityList getCubeFormulaeForFinanceCube(int var1);

   CubeFormulaEditorSession getCubeFormulaEditorSession(Object var1) throws ValidationException;

   int issueCubeFormulaRebuildTask(ModelRef var1, FinanceCubeRef var2, List<CubeFormulaRef> var3) throws ValidationException;
}
