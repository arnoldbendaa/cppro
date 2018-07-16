// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:06:20
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.api.model.recharge;

import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.dto.model.recharge.RechargeGroupEditorSessionCSO;
import com.cedar.cp.dto.model.recharge.RechargeGroupEditorSessionSSO;
import com.cedar.cp.dto.model.recharge.RechargeGroupPK;
import javax.ejb.EJBException;
import javax.ejb.EJBLocalObject;

public interface RechargeGroupEditorSessionLocal extends EJBLocalObject {

   RechargeGroupEditorSessionSSO getItemData(int var1, Object var2) throws ValidationException, EJBException;

   RechargeGroupEditorSessionSSO getNewItemData(int var1) throws EJBException;

   RechargeGroupPK insert(RechargeGroupEditorSessionCSO var1) throws ValidationException, EJBException;

   RechargeGroupPK copy(RechargeGroupEditorSessionCSO var1) throws ValidationException, EJBException;

   void update(RechargeGroupEditorSessionCSO var1) throws ValidationException, EJBException;

   void delete(int var1, Object var2) throws ValidationException, EJBException;
}
