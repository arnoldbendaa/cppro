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
import javax.ejb.EJBException;
import javax.ejb.EJBLocalObject;

public interface TaskGroupEditorSessionLocal extends EJBLocalObject {

   TaskGroupEditorSessionSSO getItemData(int var1, Object var2) throws ValidationException, EJBException;

   TaskGroupEditorSessionSSO getNewItemData(int var1) throws EJBException;

   TaskGroupPK insert(TaskGroupEditorSessionCSO var1) throws ValidationException, EJBException;

   TaskGroupPK copy(TaskGroupEditorSessionCSO var1) throws ValidationException, EJBException;

   void update(TaskGroupEditorSessionCSO var1) throws ValidationException, EJBException;

   void delete(int var1, Object var2) throws ValidationException, EJBException;

   int submitGroup(EntityRef var1, int var2, int var3) throws ValidationException, EJBException;
}
