// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:04:20
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.api.report.destination.external;

import com.cedar.cp.api.base.BusinessProcess;
import com.cedar.cp.api.base.EntityList;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.report.destination.external.ExternalDestinationEditorSession;

public interface ExternalDestinationsProcess extends BusinessProcess {

   EntityList getAllExternalDestinations();

   EntityList getAllExternalDestinationDetails();

   EntityList getAllUsersForExternalDestinationId(int var1);

   ExternalDestinationEditorSession getExternalDestinationEditorSession(Object var1) throws ValidationException;
}
