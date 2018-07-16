// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:27:27
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.impl.task.group;

import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.dto.task.group.TaskGroupCK;
import com.cedar.cp.dto.task.group.TaskGroupPK;
import com.cedar.cp.ejb.impl.task.group.TaskGroupEVO;
import java.rmi.RemoteException;
import javax.ejb.EJBObject;

public interface TaskGroupRemote extends EJBObject {

   TaskGroupEVO getDetails(String var1) throws ValidationException, RemoteException;

   TaskGroupEVO getDetails(TaskGroupCK var1, String var2) throws ValidationException, RemoteException;

   TaskGroupPK generateKeys();

   void setDetails(TaskGroupEVO var1) throws RemoteException;

   TaskGroupEVO setAndGetDetails(TaskGroupEVO var1, String var2) throws RemoteException;
}
