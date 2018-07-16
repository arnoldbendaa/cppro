// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:08:43
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.impl.message;

import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.dto.message.MessageCK;
import com.cedar.cp.dto.message.MessagePK;
import com.cedar.cp.ejb.impl.message.MessageEVO;
import java.rmi.RemoteException;
import javax.ejb.EJBObject;

public interface MessageRemote extends EJBObject {

   MessageEVO getDetails(String var1) throws ValidationException, RemoteException;

   MessageEVO getDetails(MessageCK var1, String var2) throws ValidationException, RemoteException;

   MessagePK generateKeys();

   void setDetails(MessageEVO var1) throws RemoteException;

   MessageEVO setAndGetDetails(MessageEVO var1, String var2) throws RemoteException;
}
