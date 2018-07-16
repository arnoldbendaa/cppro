// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:04:21
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.api.ws;

import java.rmi.Remote;
import java.rmi.RemoteException;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.ParameterStyle;
import javax.jws.soap.SOAPBinding.Style;

@WebService(
   targetNamespace = "http://com.cedar.cp.ws/"
)
@SOAPBinding(
   style = Style.RPC,
   parameterStyle = ParameterStyle.BARE
)
public interface Hello extends Remote {

   @WebMethod(
      operationName = "echo"
   )
   @WebResult(
      name = "result"
   )
   String echo(
      @WebParam(
         name = "name"
      ) String var1) throws RemoteException;
}
