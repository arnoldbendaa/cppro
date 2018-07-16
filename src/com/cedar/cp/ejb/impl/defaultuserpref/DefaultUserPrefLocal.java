// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:08:19
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.impl.defaultuserpref;

import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.dto.defaultuserpref.DefaultUserPrefPK;
import com.cedar.cp.ejb.impl.defaultuserpref.DefaultUserPrefEVO;
import javax.ejb.EJBLocalObject;

public interface DefaultUserPrefLocal extends EJBLocalObject {

   DefaultUserPrefEVO getDetails(String var1) throws ValidationException;

   DefaultUserPrefPK generateKeys();

   void setDetails(DefaultUserPrefEVO var1);

   DefaultUserPrefEVO setAndGetDetails(DefaultUserPrefEVO var1, String var2);
}
