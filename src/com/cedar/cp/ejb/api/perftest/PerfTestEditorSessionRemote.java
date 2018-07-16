// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:06:20
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.api.perftest;

import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.dto.perftest.PerfTestEditorSessionCSO;
import com.cedar.cp.dto.perftest.PerfTestEditorSessionSSO;
import com.cedar.cp.dto.perftest.PerfTestPK;
import java.rmi.RemoteException;
import javax.ejb.EJBObject;

public interface PerfTestEditorSessionRemote extends EJBObject {

   PerfTestEditorSessionSSO getItemData(int var1, Object var2) throws ValidationException, RemoteException;

   PerfTestEditorSessionSSO getNewItemData(int var1) throws RemoteException;

   PerfTestPK insert(PerfTestEditorSessionCSO var1) throws ValidationException, RemoteException;

   PerfTestPK copy(PerfTestEditorSessionCSO var1) throws ValidationException, RemoteException;

   void update(PerfTestEditorSessionCSO var1) throws ValidationException, RemoteException;

   void delete(int var1, Object var2) throws ValidationException, RemoteException;
}
