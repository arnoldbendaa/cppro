// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:27:30
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.impl.udeflookup;

import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.dto.udeflookup.UdefLookupCK;
import com.cedar.cp.dto.udeflookup.UdefLookupPK;
import com.cedar.cp.ejb.impl.udeflookup.UdefLookupEVO;
import java.rmi.RemoteException;
import javax.ejb.EJBObject;

public interface UdefLookupRemote extends EJBObject {

   UdefLookupEVO getDetails(String var1) throws ValidationException, RemoteException;

   UdefLookupEVO getDetails(UdefLookupCK var1, String var2) throws ValidationException, RemoteException;

   UdefLookupPK generateKeys();

   void setDetails(UdefLookupEVO var1) throws RemoteException;

   UdefLookupEVO setAndGetDetails(UdefLookupEVO var1, String var2) throws RemoteException;
}
