// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:06:17
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.api.cm;

import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.model.ModelRef;
import com.cedar.cp.dto.user.UserPK;
import javax.ejb.EJBException;
import javax.ejb.EJBLocalObject;

public interface ChangeManagementLocal extends EJBLocalObject {

   void registerUpdateRequest(String var1) throws ValidationException, EJBException;

   int issueUpdateTask(UserPK var1, ModelRef var2) throws ValidationException, EJBException;
}
