// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:04:18
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.api.impexp;

import com.cedar.cp.api.base.BusinessProcess;
import com.cedar.cp.api.base.CPException;
import com.cedar.cp.api.base.EntityList;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.dataEntry.DataExtract;

public interface ImpExpHdrsProcess extends BusinessProcess {

   EntityList getAllImpExpHdrs();

   int issueImportTask(int var1, DataExtract var2) throws CPException, ValidationException;
}
