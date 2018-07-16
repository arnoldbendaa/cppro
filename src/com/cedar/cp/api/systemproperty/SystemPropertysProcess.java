// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:04:20
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.api.systemproperty;

import com.cedar.cp.api.base.BusinessProcess;
import com.cedar.cp.api.base.EntityList;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.systemproperty.SystemPropertyEditorSession;

public interface SystemPropertysProcess extends BusinessProcess {

   EntityList getAllSystemPropertys();

   EntityList getAllSystemPropertysUncached();

   EntityList getAllMailProps();

   EntityList getSystemProperty(String var1);

   EntityList getWebSystemProperty(String var1);

   SystemPropertyEditorSession getSystemPropertyEditorSession(Object var1) throws ValidationException;
}
