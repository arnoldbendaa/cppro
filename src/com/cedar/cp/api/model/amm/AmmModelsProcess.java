// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:04:19
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.api.model.amm;

import com.cedar.cp.api.base.BusinessProcess;
import com.cedar.cp.api.base.EntityList;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.model.amm.AmmModelEditorSession;
import java.util.List;
import javax.swing.tree.TreeModel;

public interface AmmModelsProcess extends BusinessProcess {

   EntityList getAllAmmModels();
   
   EntityList getAllAmmModelsForLoggedUser();

   AmmModelEditorSession getAmmModelEditorSession(Object var1) throws ValidationException;

   int issueRebuildTask(List var1) throws ValidationException;

   TreeModel getAmmTreeModel() throws ValidationException;
}
