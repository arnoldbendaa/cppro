package com.cedar.cp.ejb.impl.model.globalmapping2;

import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.dto.model.globalmapping2.GlobalMappedModel2CK;
import com.cedar.cp.dto.model.globalmapping2.GlobalMappedModel2PK;
import com.cedar.cp.ejb.impl.model.globalmapping2.GlobalMappedModel2EVO;
import java.rmi.RemoteException;
import javax.ejb.EJBObject;

public interface GlobalMappedModel2Remote extends EJBObject {

   GlobalMappedModel2EVO getDetails(String var1) throws ValidationException, RemoteException;

   GlobalMappedModel2EVO getDetails(GlobalMappedModel2CK var1, String var2) throws ValidationException, RemoteException;

   GlobalMappedModel2PK generateKeys();

   void setDetails(GlobalMappedModel2EVO var1) throws RemoteException;

   GlobalMappedModel2EVO setAndGetDetails(GlobalMappedModel2EVO var1, String var2) throws RemoteException;
}
