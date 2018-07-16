// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:08:40
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.impl.impexp;

import com.cedar.cp.dto.impexp.ImpExpHdrPK;
import com.cedar.cp.ejb.impl.impexp.ImpExpHdrEVO;
import com.cedar.cp.ejb.impl.impexp.ImpExpHdrRemote;
import java.rmi.RemoteException;
import javax.ejb.CreateException;
import javax.ejb.EJBException;
import javax.ejb.EJBHome;
import javax.ejb.FinderException;

public interface ImpExpHdrHome extends EJBHome {

   ImpExpHdrRemote create(ImpExpHdrEVO var1) throws EJBException, CreateException, RemoteException;

   ImpExpHdrRemote findByPrimaryKey(ImpExpHdrPK var1) throws EJBException, FinderException, RemoteException;
}
