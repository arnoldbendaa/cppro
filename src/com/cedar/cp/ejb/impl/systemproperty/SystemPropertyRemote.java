// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:27:25
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.impl.systemproperty;

import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.dto.systemproperty.SystemPropertyPK;
import com.cedar.cp.ejb.impl.systemproperty.SystemPropertyEVO;
import java.rmi.RemoteException;
import javax.ejb.EJBObject;

public interface SystemPropertyRemote extends EJBObject {

   SystemPropertyEVO getDetails(String var1) throws ValidationException, RemoteException;

   SystemPropertyPK generateKeys();

   void setDetails(SystemPropertyEVO var1) throws RemoteException;

   SystemPropertyEVO setAndGetDetails(SystemPropertyEVO var1, String var2) throws RemoteException;
}
