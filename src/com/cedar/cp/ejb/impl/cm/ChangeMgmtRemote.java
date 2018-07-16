// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:08:09
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.impl.cm;

import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.dto.cm.ChangeMgmtPK;
import com.cedar.cp.ejb.impl.cm.ChangeMgmtEVO;
import java.rmi.RemoteException;
import javax.ejb.EJBObject;

public interface ChangeMgmtRemote extends EJBObject {

   ChangeMgmtEVO getDetails(String var1) throws ValidationException, RemoteException;

   ChangeMgmtPK generateKeys();

   void setDetails(ChangeMgmtEVO var1) throws RemoteException;

   ChangeMgmtEVO setAndGetDetails(ChangeMgmtEVO var1, String var2) throws RemoteException;
}
