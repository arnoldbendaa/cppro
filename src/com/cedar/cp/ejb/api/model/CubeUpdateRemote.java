// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:06:19
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.api.model;

import java.rmi.RemoteException;
import javax.ejb.EJBObject;

public interface CubeUpdateRemote extends EJBObject {

   void executeCubeUpdate(String var1) throws RemoteException;

   void executeFlatFormUpdate(String var1) throws RemoteException;
}
