// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:06:18
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.api.impexp;

import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.dataEntry.DataExtract;
import java.rmi.RemoteException;
import javax.ejb.EJBObject;

public interface ImportExportRemote extends EJBObject {

   int issueImportTask(int var1, DataExtract var2) throws ValidationException, RemoteException;
}
