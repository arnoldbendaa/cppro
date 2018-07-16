// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:06:21
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.api.perftestrun;

import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.dto.perftestrun.PerfTestRunEditorSessionCSO;
import com.cedar.cp.dto.perftestrun.PerfTestRunEditorSessionSSO;
import com.cedar.cp.dto.perftestrun.PerfTestRunPK;
import java.rmi.RemoteException;
import javax.ejb.EJBObject;

public interface PerfTestRunEditorSessionRemote extends EJBObject {

   PerfTestRunEditorSessionSSO getItemData(int var1, Object var2) throws ValidationException, RemoteException;

   PerfTestRunEditorSessionSSO getNewItemData(int var1) throws RemoteException;

   PerfTestRunPK insert(PerfTestRunEditorSessionCSO var1) throws ValidationException, RemoteException;

   PerfTestRunPK copy(PerfTestRunEditorSessionCSO var1) throws ValidationException, RemoteException;

   void update(PerfTestRunEditorSessionCSO var1) throws ValidationException, RemoteException;

   void delete(int var1, Object var2) throws ValidationException, RemoteException;
}
