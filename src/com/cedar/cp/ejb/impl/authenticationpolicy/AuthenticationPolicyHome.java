// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:08:01
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.impl.authenticationpolicy;

import com.cedar.cp.dto.authenticationpolicy.AuthenticationPolicyPK;
import com.cedar.cp.ejb.impl.authenticationpolicy.AuthenticationPolicyEVO;
import com.cedar.cp.ejb.impl.authenticationpolicy.AuthenticationPolicyRemote;
import java.rmi.RemoteException;
import javax.ejb.CreateException;
import javax.ejb.EJBException;
import javax.ejb.EJBHome;
import javax.ejb.FinderException;

public interface AuthenticationPolicyHome extends EJBHome {

   AuthenticationPolicyRemote create(AuthenticationPolicyEVO var1) throws EJBException, CreateException, RemoteException;

   AuthenticationPolicyRemote findByPrimaryKey(AuthenticationPolicyPK var1) throws EJBException, FinderException, RemoteException;
}
