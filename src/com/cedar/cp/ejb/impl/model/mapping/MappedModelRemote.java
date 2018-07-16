// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:26:49
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.impl.model.mapping;

import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.dto.model.mapping.MappedModelCK;
import com.cedar.cp.dto.model.mapping.MappedModelPK;
import com.cedar.cp.ejb.impl.model.mapping.MappedModelEVO;
import java.rmi.RemoteException;
import javax.ejb.EJBObject;

public interface MappedModelRemote extends EJBObject {

   MappedModelEVO getDetails(String var1) throws ValidationException, RemoteException;

   MappedModelEVO getDetails(MappedModelCK var1, String var2) throws ValidationException, RemoteException;

   MappedModelPK generateKeys();

   void setDetails(MappedModelEVO var1) throws RemoteException;

   MappedModelEVO setAndGetDetails(MappedModelEVO var1, String var2) throws RemoteException;
}
