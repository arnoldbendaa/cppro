// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:08:22
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.impl.dimension;

import com.cedar.cp.dto.dimension.DimensionPK;
import com.cedar.cp.ejb.impl.dimension.DimensionEVO;
import com.cedar.cp.ejb.impl.dimension.DimensionRemote;
import java.rmi.RemoteException;
import java.util.Collection;
import javax.ejb.CreateException;
import javax.ejb.EJBException;
import javax.ejb.EJBHome;
import javax.ejb.FinderException;

public interface DimensionHome extends EJBHome {

   DimensionRemote create(DimensionEVO var1) throws EJBException, CreateException, RemoteException;

   DimensionRemote findByPrimaryKey(DimensionPK var1) throws EJBException, FinderException, RemoteException;

   Collection findAll() throws FinderException, EJBException, RemoteException;
}
