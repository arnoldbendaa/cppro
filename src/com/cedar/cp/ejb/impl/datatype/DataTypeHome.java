// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:08:18
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.impl.datatype;

import com.cedar.cp.dto.datatype.DataTypePK;
import com.cedar.cp.ejb.impl.datatype.DataTypeEVO;
import com.cedar.cp.ejb.impl.datatype.DataTypeRemote;
import java.rmi.RemoteException;
import javax.ejb.CreateException;
import javax.ejb.EJBException;
import javax.ejb.EJBHome;
import javax.ejb.FinderException;

public interface DataTypeHome extends EJBHome {

   DataTypeRemote create(DataTypeEVO var1) throws EJBException, CreateException, RemoteException;

   DataTypeRemote findByPrimaryKey(DataTypePK var1) throws EJBException, FinderException, RemoteException;
}
