// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:04:20
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.api.report.distribution;

import com.cedar.cp.api.base.BusinessProcess;
import com.cedar.cp.api.base.EntityList;
import com.cedar.cp.api.base.EntityRef;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.report.distribution.DistributionDetails;
import com.cedar.cp.api.report.distribution.DistributionEditorSession;

public interface DistributionsProcess extends BusinessProcess {

   EntityList getAllDistributions();

   EntityList getDistributionForVisId(String var1);

   EntityList getDistributionDetailsForVisId(String var1);

   DistributionEditorSession getDistributionEditorSession(Object var1) throws ValidationException;

   DistributionDetails getDistributionDetailList(String var1, int var2, int var3, EntityRef var4) throws ValidationException;
}
