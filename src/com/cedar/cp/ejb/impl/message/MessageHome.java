// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:08:43
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.impl.message;

import com.cedar.cp.dto.message.MessagePK;
import com.cedar.cp.ejb.impl.message.MessageEVO;
import com.cedar.cp.ejb.impl.message.MessageRemote;
import java.rmi.RemoteException;
import javax.ejb.CreateException;
import javax.ejb.EJBException;
import javax.ejb.EJBHome;
import javax.ejb.FinderException;

public interface MessageHome extends EJBHome {

   MessageRemote create(MessageEVO var1) throws EJBException, CreateException, RemoteException;

   MessageRemote findByPrimaryKey(MessagePK var1) throws EJBException, FinderException, RemoteException;
}
