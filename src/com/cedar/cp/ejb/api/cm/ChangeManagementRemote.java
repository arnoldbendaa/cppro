// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:06:17
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.api.cm;

import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.model.ModelRef;
import com.cedar.cp.dto.user.UserPK;
import java.rmi.RemoteException;
import javax.ejb.EJBObject;

public interface ChangeManagementRemote extends EJBObject {

   void registerUpdateRequest(String var1) throws ValidationException, RemoteException;

   int issueUpdateTask(UserPK var1, ModelRef var2) throws ValidationException, RemoteException;
}
