// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:08:22
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.impl.dimension;

import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.dto.dimension.DimensionCK;
import com.cedar.cp.dto.dimension.DimensionPK;
import com.cedar.cp.ejb.impl.dimension.DimensionEVO;
import java.rmi.RemoteException;
import javax.ejb.EJBObject;

public interface DimensionRemote extends EJBObject {

   DimensionEVO getDetails(String var1) throws ValidationException, RemoteException;

   DimensionEVO getDetails(DimensionCK var1, String var2) throws ValidationException, RemoteException;

   DimensionPK generateKeys();

   void setDetails(DimensionEVO var1) throws RemoteException;

   DimensionEVO setAndGetDetails(DimensionEVO var1, String var2) throws RemoteException;
}
