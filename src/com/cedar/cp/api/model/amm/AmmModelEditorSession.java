// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:04:19
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.api.model.amm;

import com.cedar.cp.api.base.BusinessSession;
import com.cedar.cp.api.model.ModelRef;
import com.cedar.cp.api.model.amm.AmmModelEditor;

public interface AmmModelEditorSession extends BusinessSession {

   AmmModelEditor getAmmModelEditor();

   ModelRef[] getAvailableTargetModelRefs();

   ModelRef[] getAvailableSourceModelRefs();
}
