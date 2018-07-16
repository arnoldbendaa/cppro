// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:06:13
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.api.authenticationpolicy;

import com.cedar.cp.ejb.api.authenticationpolicy.AuthenticationPolicyEditorSessionRemote;
import java.rmi.RemoteException;
import javax.ejb.CreateException;
import javax.ejb.EJBHome;

public interface AuthenticationPolicyEditorSessionHome extends EJBHome {

   AuthenticationPolicyEditorSessionRemote create() throws RemoteException, CreateException;
}
