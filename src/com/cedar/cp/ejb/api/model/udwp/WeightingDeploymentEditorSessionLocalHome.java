// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:06:20
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.api.model.udwp;

import com.cedar.cp.ejb.api.model.udwp.WeightingDeploymentEditorSessionLocal;
import javax.ejb.CreateException;
import javax.ejb.EJBLocalHome;

public interface WeightingDeploymentEditorSessionLocalHome extends EJBLocalHome {

   WeightingDeploymentEditorSessionLocal create() throws CreateException;
}
