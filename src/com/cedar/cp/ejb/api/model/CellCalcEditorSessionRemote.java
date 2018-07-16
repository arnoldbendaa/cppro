// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:06:18
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.api.model;

import com.cedar.cp.api.base.EntityList;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.dto.model.CellCalcCK;
import com.cedar.cp.dto.model.CellCalcEditorSessionCSO;
import com.cedar.cp.dto.model.CellCalcEditorSessionSSO;
import java.rmi.RemoteException;
import javax.ejb.EJBObject;

public interface CellCalcEditorSessionRemote extends EJBObject {

   CellCalcEditorSessionSSO getItemData(int var1, Object var2) throws ValidationException, RemoteException;

   CellCalcEditorSessionSSO getNewItemData(int var1) throws RemoteException;

   CellCalcCK insert(CellCalcEditorSessionCSO var1) throws ValidationException, RemoteException;

   EntityList getOwnershipData(int var1, Object var2) throws RemoteException;

   CellCalcCK copy(CellCalcEditorSessionCSO var1) throws ValidationException, RemoteException;

   void update(CellCalcEditorSessionCSO var1) throws ValidationException, RemoteException;

   void delete(int var1, Object var2) throws ValidationException, RemoteException;
}
