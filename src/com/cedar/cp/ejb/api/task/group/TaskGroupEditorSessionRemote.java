// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:06:22
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.api.task.group;

import com.cedar.cp.api.base.EntityRef;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.dto.task.group.TaskGroupEditorSessionCSO;
import com.cedar.cp.dto.task.group.TaskGroupEditorSessionSSO;
import com.cedar.cp.dto.task.group.TaskGroupPK;
import java.rmi.RemoteException;
import javax.ejb.EJBObject;

public interface TaskGroupEditorSessionRemote extends EJBObject {

   TaskGroupEditorSessionSSO getItemData(int var1, Object var2) throws ValidationException, RemoteException;

   TaskGroupEditorSessionSSO getNewItemData(int var1) throws RemoteException;

   TaskGroupPK insert(TaskGroupEditorSessionCSO var1) throws ValidationException, RemoteException;

   TaskGroupPK copy(TaskGroupEditorSessionCSO var1) throws ValidationException, RemoteException;

   void update(TaskGroupEditorSessionCSO var1) throws ValidationException, RemoteException;

   void delete(int var1, Object var2) throws ValidationException, RemoteException;

   int submitGroup(EntityRef var1, int var2, int var3) throws ValidationException, RemoteException;
}
