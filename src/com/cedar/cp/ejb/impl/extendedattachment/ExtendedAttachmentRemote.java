// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:08:30
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.impl.extendedattachment;

import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.dto.extendedattachment.ExtendedAttachmentPK;
import com.cedar.cp.ejb.impl.extendedattachment.ExtendedAttachmentEVO;
import java.rmi.RemoteException;
import javax.ejb.EJBObject;

public interface ExtendedAttachmentRemote extends EJBObject {

   ExtendedAttachmentEVO getDetails(String var1) throws ValidationException, RemoteException;

   ExtendedAttachmentPK generateKeys();

   void setDetails(ExtendedAttachmentEVO var1) throws RemoteException;

   ExtendedAttachmentEVO setAndGetDetails(ExtendedAttachmentEVO var1, String var2) throws RemoteException;
}
