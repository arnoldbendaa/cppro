// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:06:13
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.api.authenticationpolicy;

import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.dto.authenticationpolicy.AuthenticationPolicyEditorSessionCSO;
import com.cedar.cp.dto.authenticationpolicy.AuthenticationPolicyEditorSessionSSO;
import com.cedar.cp.dto.authenticationpolicy.AuthenticationPolicyPK;
import javax.ejb.EJBException;
import javax.ejb.EJBLocalObject;

public interface AuthenticationPolicyEditorSessionLocal extends EJBLocalObject {

   AuthenticationPolicyEditorSessionSSO getItemData(int var1, Object var2) throws ValidationException, EJBException;

   AuthenticationPolicyEditorSessionSSO getNewItemData(int var1) throws EJBException;

   AuthenticationPolicyPK insert(AuthenticationPolicyEditorSessionCSO var1) throws ValidationException, EJBException;

   AuthenticationPolicyPK copy(AuthenticationPolicyEditorSessionCSO var1) throws ValidationException, EJBException;

   void update(AuthenticationPolicyEditorSessionCSO var1) throws ValidationException, EJBException;

   void delete(int var1, Object var2) throws ValidationException, EJBException;
}
