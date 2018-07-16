// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:04:20
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.api.model.virement;

import com.cedar.cp.api.base.BusinessSession;
import com.cedar.cp.api.base.EntityList;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.model.virement.VirementRequestEditor;
import com.cedar.cp.api.user.UserRef;

public interface VirementRequestEditorSession extends BusinessSession {

   VirementRequestEditor getVirementRequestEditor();

   EntityList getAvailableModels();

   UserRef[] getAvailableOwningUserRefs();

   EntityList getOwnershipRefs();

   int saveAndSubmit() throws ValidationException;
}
