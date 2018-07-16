// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:26:53
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.impl.model.recharge;

import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.dto.model.recharge.RechargeGroupCK;
import com.cedar.cp.dto.model.recharge.RechargeGroupPK;
import com.cedar.cp.ejb.impl.model.recharge.RechargeGroupEVO;
import javax.ejb.EJBLocalObject;

public interface RechargeGroupLocal extends EJBLocalObject {

   RechargeGroupEVO getDetails(String var1) throws ValidationException;

   RechargeGroupEVO getDetails(RechargeGroupCK var1, String var2) throws ValidationException;

   RechargeGroupPK generateKeys();

   void setDetails(RechargeGroupEVO var1);

   RechargeGroupEVO setAndGetDetails(RechargeGroupEVO var1, String var2);
}
