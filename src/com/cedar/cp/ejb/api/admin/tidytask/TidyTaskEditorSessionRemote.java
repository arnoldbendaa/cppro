// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:06:13
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.api.admin.tidytask;

import com.cedar.cp.api.base.EntityRef;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.dto.admin.tidytask.TidyTaskEditorSessionCSO;
import com.cedar.cp.dto.admin.tidytask.TidyTaskEditorSessionSSO;
import com.cedar.cp.dto.admin.tidytask.TidyTaskPK;
import java.rmi.RemoteException;
import javax.ejb.EJBObject;

public interface TidyTaskEditorSessionRemote extends EJBObject {

   TidyTaskEditorSessionSSO getItemData(int var1, Object var2) throws ValidationException, RemoteException;

   TidyTaskEditorSessionSSO getNewItemData(int var1) throws RemoteException;

   TidyTaskPK insert(TidyTaskEditorSessionCSO var1) throws ValidationException, RemoteException;

   TidyTaskPK copy(TidyTaskEditorSessionCSO var1) throws ValidationException, RemoteException;

   void update(TidyTaskEditorSessionCSO var1) throws ValidationException, RemoteException;

   void delete(int var1, Object var2) throws ValidationException, RemoteException;

   int issueTidyTask(EntityRef var1, int var2, int var3) throws ValidationException, RemoteException;

   int issueTestTask(Integer var1, int var2) throws ValidationException, RemoteException;

   int issueTestRollbackTask(int var1) throws ValidationException, RemoteException;
}
