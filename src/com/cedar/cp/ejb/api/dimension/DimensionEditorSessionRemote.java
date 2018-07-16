// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:06:17
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.api.dimension;

import com.cedar.cp.api.base.CPException;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.dto.dimension.DimensionEditorSessionCSO;
import com.cedar.cp.dto.dimension.DimensionEditorSessionSSO;
import com.cedar.cp.dto.dimension.DimensionPK;
import java.rmi.RemoteException;
import java.util.List;
import javax.ejb.EJBObject;

public interface DimensionEditorSessionRemote extends EJBObject {

   DimensionEditorSessionSSO getItemData(int var1, Object var2) throws ValidationException, RemoteException;

   DimensionEditorSessionSSO getNewItemData(int var1) throws RemoteException;

   DimensionPK insert(DimensionEditorSessionCSO var1) throws ValidationException, RemoteException;

   DimensionPK copy(DimensionEditorSessionCSO var1) throws ValidationException, RemoteException;

   void update(DimensionEditorSessionCSO var1) throws ValidationException, RemoteException;

   void delete(int var1, Object var2) throws ValidationException, RemoteException;

   List processEvents(List var1) throws ValidationException, CPException, RemoteException;
}
