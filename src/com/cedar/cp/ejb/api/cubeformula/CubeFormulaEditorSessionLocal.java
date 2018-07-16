// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:06:17
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.api.cubeformula;

import com.cedar.cp.api.base.EntityList;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.model.FinanceCubeRef;
import com.cedar.cp.api.model.ModelRef;
import com.cedar.cp.dto.cubeformula.CubeFormulaCK;
import com.cedar.cp.dto.cubeformula.CubeFormulaEditorSessionCSO;
import com.cedar.cp.dto.cubeformula.CubeFormulaEditorSessionSSO;
import com.cedar.cp.dto.cubeformula.CubeFormulaRefImpl;
import java.util.List;
import javax.ejb.EJBException;
import javax.ejb.EJBLocalObject;

public interface CubeFormulaEditorSessionLocal extends EJBLocalObject {

   CubeFormulaEditorSessionSSO getItemData(int var1, Object var2) throws ValidationException, EJBException;

   CubeFormulaEditorSessionSSO getNewItemData(int var1) throws EJBException;

   CubeFormulaCK insert(CubeFormulaEditorSessionCSO var1) throws ValidationException, EJBException;

   EntityList getOwnershipData(int var1, Object var2) throws EJBException;

   CubeFormulaCK copy(CubeFormulaEditorSessionCSO var1) throws ValidationException, EJBException;

   void update(CubeFormulaEditorSessionCSO var1) throws ValidationException, EJBException;

   void delete(int var1, Object var2) throws ValidationException, EJBException;

   void testCompileFormula(int var1, int var2, String var3, int var4) throws ValidationException, EJBException;

   int issueRebuildTask(int var1, ModelRef var2, FinanceCubeRef var3, List<CubeFormulaRefImpl> var4) throws EJBException;
}
