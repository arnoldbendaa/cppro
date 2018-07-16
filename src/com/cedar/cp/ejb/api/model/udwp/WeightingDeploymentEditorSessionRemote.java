// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:06:20
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.api.model.udwp;

import com.cedar.cp.api.base.EntityList;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.dto.model.udwp.WeightingDeploymentCK;
import com.cedar.cp.dto.model.udwp.WeightingDeploymentEditorSessionCSO;
import com.cedar.cp.dto.model.udwp.WeightingDeploymentEditorSessionSSO;
import java.rmi.RemoteException;
import javax.ejb.EJBObject;

public interface WeightingDeploymentEditorSessionRemote extends EJBObject {

   WeightingDeploymentEditorSessionSSO getItemData(int var1, Object var2) throws ValidationException, RemoteException;

   WeightingDeploymentEditorSessionSSO getNewItemData(int var1) throws RemoteException;

   WeightingDeploymentCK insert(WeightingDeploymentEditorSessionCSO var1) throws ValidationException, RemoteException;

   EntityList getOwnershipData(int var1, Object var2) throws RemoteException;

   WeightingDeploymentCK copy(WeightingDeploymentEditorSessionCSO var1) throws ValidationException, RemoteException;

   void update(WeightingDeploymentEditorSessionCSO var1) throws ValidationException, RemoteException;

   void delete(int var1, Object var2) throws ValidationException, RemoteException;

   EntityList queryDeployments() throws RemoteException;
}
