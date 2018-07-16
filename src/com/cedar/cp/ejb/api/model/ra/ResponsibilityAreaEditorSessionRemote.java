// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:06:20
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.api.model.ra;

import com.cedar.cp.api.base.EntityList;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.dto.model.ra.ResponsibilityAreaCK;
import com.cedar.cp.dto.model.ra.ResponsibilityAreaEditorSessionCSO;
import com.cedar.cp.dto.model.ra.ResponsibilityAreaEditorSessionSSO;
import java.rmi.RemoteException;
import javax.ejb.EJBObject;

public interface ResponsibilityAreaEditorSessionRemote extends EJBObject {

   ResponsibilityAreaEditorSessionSSO getItemData(int var1, Object var2) throws ValidationException, RemoteException;

   ResponsibilityAreaEditorSessionSSO getNewItemData(int var1) throws RemoteException;

   ResponsibilityAreaCK insert(ResponsibilityAreaEditorSessionCSO var1) throws ValidationException, RemoteException;

   EntityList getOwnershipData(int var1, Object var2) throws RemoteException;

   ResponsibilityAreaCK copy(ResponsibilityAreaEditorSessionCSO var1) throws ValidationException, RemoteException;

   void update(ResponsibilityAreaEditorSessionCSO var1) throws ValidationException, RemoteException;

   void delete(int var1, Object var2) throws ValidationException, RemoteException;
}
