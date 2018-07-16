// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:26:34
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.impl.model.amm;

import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.dto.model.amm.AmmModelCK;
import com.cedar.cp.dto.model.amm.AmmModelPK;
import com.cedar.cp.ejb.impl.model.amm.AmmModelEVO;
import java.rmi.RemoteException;
import javax.ejb.EJBObject;

public interface AmmModelRemote extends EJBObject {

   AmmModelEVO getDetails(String var1) throws ValidationException, RemoteException;

   AmmModelEVO getDetails(AmmModelCK var1, String var2) throws ValidationException, RemoteException;

   AmmModelPK generateKeys();

   void setDetails(AmmModelEVO var1) throws RemoteException;

   AmmModelEVO setAndGetDetails(AmmModelEVO var1, String var2) throws RemoteException;
}
