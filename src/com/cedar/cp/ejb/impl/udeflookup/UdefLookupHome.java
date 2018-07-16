// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:27:30
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.impl.udeflookup;

import com.cedar.cp.dto.udeflookup.UdefLookupPK;
import com.cedar.cp.ejb.impl.udeflookup.UdefLookupEVO;
import com.cedar.cp.ejb.impl.udeflookup.UdefLookupRemote;
import java.rmi.RemoteException;
import javax.ejb.CreateException;
import javax.ejb.EJBException;
import javax.ejb.EJBHome;
import javax.ejb.FinderException;

public interface UdefLookupHome extends EJBHome {

   UdefLookupRemote create(UdefLookupEVO var1) throws EJBException, CreateException, RemoteException;

   UdefLookupRemote findByPrimaryKey(UdefLookupPK var1) throws EJBException, FinderException, RemoteException;
}
