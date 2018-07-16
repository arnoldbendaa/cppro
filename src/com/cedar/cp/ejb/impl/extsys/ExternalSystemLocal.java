// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:08:38
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.impl.extsys;

import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.dto.extsys.ExternalSystemCK;
import com.cedar.cp.dto.extsys.ExternalSystemPK;
import com.cedar.cp.ejb.impl.extsys.ExternalSystemEVO;
import javax.ejb.EJBLocalObject;

public interface ExternalSystemLocal extends EJBLocalObject {

   ExternalSystemEVO getDetails(String var1) throws ValidationException;

   ExternalSystemEVO getDetails(ExternalSystemCK var1, String var2) throws ValidationException;

   ExternalSystemPK generateKeys();

   void setDetails(ExternalSystemEVO var1);

   ExternalSystemEVO setAndGetDetails(ExternalSystemEVO var1, String var2);
}