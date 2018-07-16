// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:27:13
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.impl.report.destination.internal;

import com.cedar.cp.dto.report.destination.internal.InternalDestinationPK;
import com.cedar.cp.ejb.impl.report.destination.internal.InternalDestinationEVO;
import com.cedar.cp.ejb.impl.report.destination.internal.InternalDestinationRemote;
import java.rmi.RemoteException;
import javax.ejb.CreateException;
import javax.ejb.EJBException;
import javax.ejb.EJBHome;
import javax.ejb.FinderException;

public interface InternalDestinationHome extends EJBHome {

   InternalDestinationRemote create(InternalDestinationEVO var1) throws EJBException, CreateException, RemoteException;

   InternalDestinationRemote findByPrimaryKey(InternalDestinationPK var1) throws EJBException, FinderException, RemoteException;
}
