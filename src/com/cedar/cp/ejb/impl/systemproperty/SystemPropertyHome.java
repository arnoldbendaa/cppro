// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:27:25
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.impl.systemproperty;

import com.cedar.cp.dto.systemproperty.SystemPropertyPK;
import com.cedar.cp.ejb.impl.systemproperty.SystemPropertyEVO;
import com.cedar.cp.ejb.impl.systemproperty.SystemPropertyRemote;
import java.rmi.RemoteException;
import javax.ejb.CreateException;
import javax.ejb.EJBException;
import javax.ejb.EJBHome;
import javax.ejb.FinderException;

public interface SystemPropertyHome extends EJBHome {

   SystemPropertyRemote create(SystemPropertyEVO var1) throws EJBException, CreateException, RemoteException;

   SystemPropertyRemote findByPrimaryKey(SystemPropertyPK var1) throws EJBException, FinderException, RemoteException;
}
