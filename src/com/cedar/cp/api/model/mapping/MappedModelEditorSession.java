// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:04:19
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.api.model.mapping;

import com.cedar.cp.api.base.BusinessSession;
import com.cedar.cp.api.extsys.ExternalSystemRef;
import com.cedar.cp.api.model.ModelRef;
import com.cedar.cp.api.model.mapping.MappedModelEditor;

public interface MappedModelEditorSession extends BusinessSession {

   MappedModelEditor getMappedModelEditor();

   ModelRef[] getAvailableOwningModelRefs();

   ExternalSystemRef[] getAvailableExternalSystemRefs();
}
