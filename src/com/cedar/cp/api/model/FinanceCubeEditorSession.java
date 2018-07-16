// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:04:18
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.api.model;

import com.cedar.cp.api.base.BusinessSession;
import com.cedar.cp.api.base.EntityList;
import com.cedar.cp.api.datatype.DataTypeRef;
import com.cedar.cp.api.model.FinanceCubeEditor;

public interface FinanceCubeEditorSession extends BusinessSession {

   FinanceCubeEditor getFinanceCubeEditor();

   EntityList getAvailableModels();

   EntityList getOwnershipRefs();

   DataTypeRef[] getAvailableDataTypeRefs();

   EntityList getAvailableDataTypes();
   
   FinanceCube getEditorData();
}
