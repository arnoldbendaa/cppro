// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:04:17
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.api.dimension;

import com.cedar.cp.api.base.BusinessProcess;
import com.cedar.cp.api.base.EntityList;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.dimension.SecurityRangeEditorSession;

public interface SecurityRangesProcess extends BusinessProcess {

   EntityList getAllSecurityRanges();

   EntityList getAllSecurityRangesForModel(int var1);

   SecurityRangeEditorSession getSecurityRangeEditorSession(Object var1) throws ValidationException;
}
