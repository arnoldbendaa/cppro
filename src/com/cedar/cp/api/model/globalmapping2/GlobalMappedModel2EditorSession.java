package com.cedar.cp.api.model.globalmapping2;

import com.cedar.cp.api.base.BusinessSession;
import com.cedar.cp.api.extsys.ExternalSystemRef;
import com.cedar.cp.api.model.ModelRef;
import com.cedar.cp.api.model.globalmapping2.GlobalMappedModel2Editor;

public interface GlobalMappedModel2EditorSession extends BusinessSession {

   GlobalMappedModel2Editor getMappedModelEditor();

   ModelRef[] getAvailableOwningModelRefs();

   ExternalSystemRef[] getAvailableExternalSystemRefs();
}
