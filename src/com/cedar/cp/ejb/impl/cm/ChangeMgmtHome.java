// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:08:09
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.impl.cm;

import com.cedar.cp.dto.cm.ChangeMgmtPK;
import com.cedar.cp.ejb.impl.cm.ChangeMgmtEVO;
import com.cedar.cp.ejb.impl.cm.ChangeMgmtRemote;
import java.rmi.RemoteException;
import javax.ejb.CreateException;
import javax.ejb.EJBException;
import javax.ejb.EJBHome;
import javax.ejb.FinderException;

public interface ChangeMgmtHome extends EJBHome {

   ChangeMgmtRemote create(ChangeMgmtEVO var1) throws EJBException, CreateException, RemoteException;

   ChangeMgmtRemote findByPrimaryKey(ChangeMgmtPK var1) throws EJBException, FinderException, RemoteException;
}
