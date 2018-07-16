// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:08:00
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.impl.admin.tidytask;

import com.cedar.cp.dto.admin.tidytask.TidyTaskPK;
import com.cedar.cp.ejb.impl.admin.tidytask.TidyTaskEVO;
import com.cedar.cp.ejb.impl.admin.tidytask.TidyTaskRemote;
import java.rmi.RemoteException;
import javax.ejb.CreateException;
import javax.ejb.EJBException;
import javax.ejb.EJBHome;
import javax.ejb.FinderException;

public interface TidyTaskHome extends EJBHome {

   TidyTaskRemote create(TidyTaskEVO var1) throws EJBException, CreateException, RemoteException;

   TidyTaskRemote findByPrimaryKey(TidyTaskPK var1) throws EJBException, FinderException, RemoteException;
}
