// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:04:21
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.api.udeflookup;

import com.cedar.cp.api.base.BusinessSession;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.udeflookup.UdefLookupEditor;

public interface UdefLookupEditorSession extends BusinessSession {

   UdefLookupEditor getUdefLookupEditor();

   void saveTableData() throws ValidationException;

   void issueRebuild(int var1) throws ValidationException;
}
