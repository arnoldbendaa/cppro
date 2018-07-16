// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:27:04
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.impl.passwordhistory;

import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.dto.passwordhistory.PasswordHistoryPK;
import com.cedar.cp.ejb.impl.passwordhistory.PasswordHistoryEVO;
import javax.ejb.EJBLocalObject;

public interface PasswordHistoryLocal extends EJBLocalObject {

   PasswordHistoryEVO getDetails(String var1) throws ValidationException;

   PasswordHistoryPK generateKeys();

   void setDetails(PasswordHistoryEVO var1);

   PasswordHistoryEVO setAndGetDetails(PasswordHistoryEVO var1, String var2);
}
