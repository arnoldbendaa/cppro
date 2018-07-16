// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:06:17
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.api.cm;

import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.dto.cm.ChangeMgmtEditorSessionCSO;
import com.cedar.cp.dto.cm.ChangeMgmtEditorSessionSSO;
import com.cedar.cp.dto.cm.ChangeMgmtPK;
import java.rmi.RemoteException;
import javax.ejb.EJBObject;

public interface ChangeMgmtEditorSessionRemote extends EJBObject {

   ChangeMgmtEditorSessionSSO getItemData(int var1, Object var2) throws ValidationException, RemoteException;

   ChangeMgmtEditorSessionSSO getNewItemData(int var1) throws RemoteException;

   ChangeMgmtPK insert(ChangeMgmtEditorSessionCSO var1) throws ValidationException, RemoteException;

   ChangeMgmtPK copy(ChangeMgmtEditorSessionCSO var1) throws ValidationException, RemoteException;

   void update(ChangeMgmtEditorSessionCSO var1) throws ValidationException, RemoteException;

   void delete(int var1, Object var2) throws ValidationException, RemoteException;

   void tidyBudgetState(int var1) throws RemoteException;
}
