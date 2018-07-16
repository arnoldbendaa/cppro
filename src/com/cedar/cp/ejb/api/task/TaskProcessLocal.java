// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:06:22
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.api.task;

import java.rmi.RemoteException;

import com.cedar.cp.api.base.DuplicateNameValidationException;
import com.cedar.cp.api.base.EntityList;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.base.VersionValidationException;
import com.cedar.cp.api.task.TaskRequest;
import com.cedar.cp.dto.task.TaskDetails;
import com.cedar.cp.dto.task.TaskPK;

import javax.ejb.EJBException;
import javax.ejb.EJBLocalObject;

public interface TaskProcessLocal extends EJBLocalObject {

   EntityList getPageTasks(int page, int offset) throws EJBException;

   EntityList getTasks() throws EJBException;

   EntityList getTaskEvents(int var1) throws ValidationException, EJBException;

   TaskDetails getTask(TaskPK var1) throws ValidationException, EJBException;

   void setServiceStep(TaskPK var1, String var2) throws EJBException, DuplicateNameValidationException, VersionValidationException, ValidationException;

   void delete(TaskPK var1) throws EJBException, ValidationException;

   int getNewTaskId() throws EJBException;

   void failTask(TaskPK var1) throws ValidationException, EJBException;

   void unsafeDeleteTask(int var1, TaskPK var2) throws ValidationException, EJBException;

   void restartTask(int var1) throws ValidationException, EJBException;

   void wakeUpDespatcher() throws EJBException;

   int newTask(int var1, int var2, TaskRequest var3, long var4, int var6) throws EJBException;
}
