// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:26:27
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.impl.model;

import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.dto.model.ModelCK;
import com.cedar.cp.dto.model.ModelPK;
import com.cedar.cp.ejb.impl.model.ModelEVO;
import java.rmi.RemoteException;
import javax.ejb.EJBObject;

public interface ModelRemote extends EJBObject {

   ModelEVO getDetails(String var1) throws ValidationException, RemoteException;

   ModelEVO getDetails(ModelCK var1, String var2) throws ValidationException, RemoteException;

   ModelPK generateKeys();

   void setDetails(ModelEVO var1) throws RemoteException;

   ModelEVO setAndGetDetails(ModelEVO var1, String var2) throws RemoteException;
}
