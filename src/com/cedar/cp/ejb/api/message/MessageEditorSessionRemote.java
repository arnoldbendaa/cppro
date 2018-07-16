// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:06:18
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.api.message;

import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.dto.message.MessageEditorSessionCSO;
import com.cedar.cp.dto.message.MessageEditorSessionSSO;
import com.cedar.cp.dto.message.MessagePK;
import java.rmi.RemoteException;
import javax.ejb.EJBObject;

public interface MessageEditorSessionRemote extends EJBObject {

   MessageEditorSessionSSO getItemData(int var1, Object var2) throws ValidationException, RemoteException;

   MessageEditorSessionSSO getNewItemData(int var1) throws RemoteException;

   MessagePK insert(MessageEditorSessionCSO var1) throws ValidationException, RemoteException;

   MessagePK copy(MessageEditorSessionCSO var1) throws ValidationException, RemoteException;

   void update(MessageEditorSessionCSO var1) throws ValidationException, RemoteException;

   void delete(int var1, Object var2) throws ValidationException, RemoteException;

   MessagePK insertBackDoor(MessageEditorSessionCSO var1) throws ValidationException, RemoteException;
}
