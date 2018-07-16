// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:08:01
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.impl.authenticationpolicy;

import com.cedar.cp.dto.authenticationpolicy.AuthenticationPolicyPK;
import com.cedar.cp.ejb.impl.authenticationpolicy.AuthenticationPolicyEVO;
import com.cedar.cp.ejb.impl.authenticationpolicy.AuthenticationPolicyLocal;
import javax.ejb.CreateException;
import javax.ejb.EJBException;
import javax.ejb.EJBLocalHome;
import javax.ejb.FinderException;

public interface AuthenticationPolicyLocalHome extends EJBLocalHome {

   AuthenticationPolicyLocal create(AuthenticationPolicyEVO var1) throws EJBException, CreateException;

   AuthenticationPolicyLocal findByPrimaryKey(AuthenticationPolicyPK var1) throws FinderException;
}
