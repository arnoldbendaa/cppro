// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:27:26
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.impl.task;

import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.dto.task.TaskPK;
import com.cedar.cp.ejb.impl.task.TaskEVO;
import java.rmi.RemoteException;
import javax.ejb.EJBObject;

public interface TaskRemote extends EJBObject {

   TaskEVO getDetails(String var1) throws ValidationException, RemoteException;

   TaskPK generateKeys();

   void setDetails(TaskEVO var1) throws RemoteException;

   TaskEVO setAndGetDetails(TaskEVO var1, String var2) throws RemoteException;
}
