// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:08:00
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.impl.admin.tidytask;

import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.dto.admin.tidytask.TidyTaskCK;
import com.cedar.cp.dto.admin.tidytask.TidyTaskPK;
import com.cedar.cp.ejb.impl.admin.tidytask.TidyTaskEVO;
import java.rmi.RemoteException;
import javax.ejb.EJBObject;

public interface TidyTaskRemote extends EJBObject {

   TidyTaskEVO getDetails(String var1) throws ValidationException, RemoteException;

   TidyTaskEVO getDetails(TidyTaskCK var1, String var2) throws ValidationException, RemoteException;

   TidyTaskPK generateKeys();

   void setDetails(TidyTaskEVO var1) throws RemoteException;

   TidyTaskEVO setAndGetDetails(TidyTaskEVO var1, String var2) throws RemoteException;
}
