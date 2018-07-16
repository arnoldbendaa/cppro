// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:04:20
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.api.report.destination.internal;

import com.cedar.cp.api.base.BusinessProcess;
import com.cedar.cp.api.base.EntityList;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.report.destination.internal.InternalDestinationEditorSession;

public interface InternalDestinationsProcess extends BusinessProcess {

   EntityList getAllInternalDestinations();

   EntityList getAllInternalDestinationDetails();

   EntityList getAllUsersForInternalDestinationId(int var1);

   InternalDestinationEditorSession getInternalDestinationEditorSession(Object var1) throws ValidationException;

   EntityList getDistinctInternalDestinationUsers(String[] var1);
}
