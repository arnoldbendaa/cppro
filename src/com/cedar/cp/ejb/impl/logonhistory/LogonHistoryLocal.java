// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:08:41
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.impl.logonhistory;

import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.dto.logonhistory.LogonHistoryPK;
import com.cedar.cp.ejb.impl.logonhistory.LogonHistoryEVO;
import javax.ejb.EJBLocalObject;

public interface LogonHistoryLocal extends EJBLocalObject {

   LogonHistoryEVO getDetails(String var1) throws ValidationException;

   LogonHistoryPK generateKeys();

   void setDetails(LogonHistoryEVO var1);

   LogonHistoryEVO setAndGetDetails(LogonHistoryEVO var1, String var2);
}
