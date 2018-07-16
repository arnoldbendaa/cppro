// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:06:21
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.api.report.destination.internal;

import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.dto.report.destination.internal.InternalDestinationEditorSessionCSO;
import com.cedar.cp.dto.report.destination.internal.InternalDestinationEditorSessionSSO;
import com.cedar.cp.dto.report.destination.internal.InternalDestinationPK;
import java.rmi.RemoteException;
import javax.ejb.EJBObject;

public interface InternalDestinationEditorSessionRemote extends EJBObject {

   InternalDestinationEditorSessionSSO getItemData(int var1, Object var2) throws ValidationException, RemoteException;

   InternalDestinationEditorSessionSSO getNewItemData(int var1) throws RemoteException;

   InternalDestinationPK insert(InternalDestinationEditorSessionCSO var1) throws ValidationException, RemoteException;

   InternalDestinationPK copy(InternalDestinationEditorSessionCSO var1) throws ValidationException, RemoteException;

   void update(InternalDestinationEditorSessionCSO var1) throws ValidationException, RemoteException;

   void delete(int var1, Object var2) throws ValidationException, RemoteException;
}
