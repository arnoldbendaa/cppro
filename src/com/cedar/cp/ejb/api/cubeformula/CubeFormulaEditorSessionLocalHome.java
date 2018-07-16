// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:06:17
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.api.cubeformula;

import com.cedar.cp.ejb.api.cubeformula.CubeFormulaEditorSessionLocal;
import javax.ejb.CreateException;
import javax.ejb.EJBLocalHome;

public interface CubeFormulaEditorSessionLocalHome extends EJBLocalHome {

   CubeFormulaEditorSessionLocal create() throws CreateException;
}
