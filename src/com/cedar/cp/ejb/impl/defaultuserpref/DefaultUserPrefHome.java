// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:08:19
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.impl.defaultuserpref;

import com.cedar.cp.dto.defaultuserpref.DefaultUserPrefPK;
import com.cedar.cp.ejb.impl.defaultuserpref.DefaultUserPrefEVO;
import com.cedar.cp.ejb.impl.defaultuserpref.DefaultUserPrefRemote;
import java.rmi.RemoteException;
import javax.ejb.CreateException;
import javax.ejb.EJBException;
import javax.ejb.EJBHome;
import javax.ejb.FinderException;

public interface DefaultUserPrefHome extends EJBHome {

   DefaultUserPrefRemote create(DefaultUserPrefEVO var1) throws EJBException, CreateException, RemoteException;

   DefaultUserPrefRemote findByPrimaryKey(DefaultUserPrefPK var1) throws EJBException, FinderException, RemoteException;
}
