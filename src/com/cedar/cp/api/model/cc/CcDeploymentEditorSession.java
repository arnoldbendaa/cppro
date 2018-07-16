// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:04:19
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.api.model.cc;

import com.cedar.cp.api.base.BusinessSession;
import com.cedar.cp.api.base.EntityList;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.dimension.StructureElementRef;
import com.cedar.cp.api.model.cc.CcDeploymentEditor;

public interface CcDeploymentEditorSession extends BusinessSession {

   CcDeploymentEditor getCcDeploymentEditor();

   EntityList getAvailableModels();

   EntityList getOwnershipRefs();

   String testDeployment(StructureElementRef[] var1, boolean[] var2) throws ValidationException;
   
   EntityList getAllModelsForLoggedUser();
}
