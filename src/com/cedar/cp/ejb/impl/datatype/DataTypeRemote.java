// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:08:18
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.impl.datatype;

import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.dto.datatype.DataTypeCK;
import com.cedar.cp.dto.datatype.DataTypePK;
import com.cedar.cp.ejb.impl.datatype.DataTypeEVO;
import java.rmi.RemoteException;
import javax.ejb.EJBObject;

public interface DataTypeRemote extends EJBObject {

   DataTypeEVO getDetails(String var1) throws ValidationException, RemoteException;

   DataTypeEVO getDetails(DataTypeCK var1, String var2) throws ValidationException, RemoteException;

   DataTypePK generateKeys();

   void setDetails(DataTypeEVO var1) throws RemoteException;

   DataTypeEVO setAndGetDetails(DataTypeEVO var1, String var2) throws RemoteException;
}
