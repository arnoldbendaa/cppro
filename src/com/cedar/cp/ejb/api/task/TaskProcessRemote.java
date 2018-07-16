// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:06:22
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.api.task;

import com.cedar.cp.api.base.DuplicateNameValidationException;
import com.cedar.cp.api.base.EntityList;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.base.VersionValidationException;
import com.cedar.cp.api.task.TaskRequest;
import com.cedar.cp.dto.task.TaskDetails;
import com.cedar.cp.dto.task.TaskPK;
import java.rmi.RemoteException;
import javax.ejb.EJBObject;

public interface TaskProcessRemote extends EJBObject {

   EntityList getPageTasks(int page, int offset) throws RemoteException;

   EntityList getTasks() throws RemoteException;

   EntityList getTaskEvents(int var1) throws RemoteException;

   TaskDetails getTask(TaskPK var1) throws ValidationException, RemoteException;

   void setServiceStep(TaskPK var1, String var2) throws RemoteException, DuplicateNameValidationException, VersionValidationException, ValidationException;

   void delete(TaskPK var1) throws RemoteException, ValidationException;

   int getNewTaskId() throws RemoteException;

   void failTask(TaskPK var1) throws ValidationException, RemoteException;

   void unsafeDeleteTask(int var1, TaskPK var2) throws ValidationException, RemoteException;

   void restartTask(int var1) throws ValidationException, RemoteException;

   void wakeUpDespatcher() throws RemoteException;

   int newTask(int var1, int var2, TaskRequest var3, long var4, int var6) throws RemoteException;
}
