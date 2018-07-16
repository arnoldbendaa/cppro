// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:04:20
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.api.model.ra;

import com.cedar.cp.api.base.BusinessSession;
import com.cedar.cp.api.base.EntityList;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.model.ra.ResponsibilityAreaEditor;
import com.cedar.cp.api.model.virement.ResponsibilityAreasEditor;

public interface ResponsibilityAreaEditorSession extends BusinessSession {

   ResponsibilityAreaEditor getResponsibilityAreaEditor();

   EntityList getAvailableModels();

   EntityList getOwnershipRefs();

   ResponsibilityAreasEditor getResponsibilityAreasEditor(Object var1) throws ValidationException;
}
