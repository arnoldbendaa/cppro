// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:04:18
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.api.extsys;

import com.cedar.cp.api.base.BusinessProcess;
import com.cedar.cp.api.base.EntityList;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.model.FinanceCubeRef;
import java.util.List;

public interface ExtSysE5DB2PushProcess extends BusinessProcess {

   boolean isAvailable();

   EntityList queryDataForPushSubmission() throws ValidationException;

   int issuePushTask(List<FinanceCubeRef> var1) throws ValidationException;
}
