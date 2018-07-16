// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:04:16
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.api.admin.tidytask;

import com.cedar.cp.api.admin.tidytask.TidyTaskEditor;
import com.cedar.cp.api.base.BusinessSession;

public interface TidyTaskEditorSession extends BusinessSession {

   TidyTaskEditor getTidyTaskEditor();
}
