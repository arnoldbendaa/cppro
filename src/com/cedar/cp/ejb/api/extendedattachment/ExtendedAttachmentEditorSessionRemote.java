// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:06:18
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.api.extendedattachment;

import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.dto.extendedattachment.ExtendedAttachmentEditorSessionCSO;
import com.cedar.cp.dto.extendedattachment.ExtendedAttachmentEditorSessionSSO;
import com.cedar.cp.dto.extendedattachment.ExtendedAttachmentPK;
import java.rmi.RemoteException;
import javax.ejb.EJBObject;

public interface ExtendedAttachmentEditorSessionRemote extends EJBObject {

   ExtendedAttachmentEditorSessionSSO getItemData(int var1, Object var2) throws ValidationException, RemoteException;

   ExtendedAttachmentEditorSessionSSO getNewItemData(int var1) throws RemoteException;

   ExtendedAttachmentPK insert(ExtendedAttachmentEditorSessionCSO var1) throws ValidationException, RemoteException;

   ExtendedAttachmentPK copy(ExtendedAttachmentEditorSessionCSO var1) throws ValidationException, RemoteException;

   void update(ExtendedAttachmentEditorSessionCSO var1) throws ValidationException, RemoteException;

   void delete(int var1, Object var2) throws ValidationException, RemoteException;
}
