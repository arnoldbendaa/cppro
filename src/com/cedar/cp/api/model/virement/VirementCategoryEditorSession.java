// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:04:20
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.api.model.virement;

import com.cedar.cp.api.base.BusinessSession;
import com.cedar.cp.api.base.EntityList;
import com.cedar.cp.api.model.virement.VirementCategoryEditor;

public interface VirementCategoryEditorSession extends BusinessSession {

   VirementCategoryEditor getVirementCategoryEditor();

   EntityList getAvailableModels();

   EntityList getOwnershipRefs();
}
