// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:04:21
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.api.ws;

import java.rmi.Remote;
import java.rmi.RemoteException;
import javax.jws.WebService;

@WebService(
   targetNamespace = "http://com.cedar.cp.ws/"
)
public interface Hello2 extends Remote {

   String echo2(String var1) throws RemoteException;
}
