// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:26:53
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.impl.model.recharge;

import com.cedar.cp.dto.model.recharge.RechargeGroupPK;
import com.cedar.cp.ejb.impl.model.recharge.RechargeGroupEVO;
import com.cedar.cp.ejb.impl.model.recharge.RechargeGroupLocal;
import javax.ejb.CreateException;
import javax.ejb.EJBException;
import javax.ejb.EJBLocalHome;
import javax.ejb.FinderException;

public interface RechargeGroupLocalHome extends EJBLocalHome {

   RechargeGroupLocal create(RechargeGroupEVO var1) throws EJBException, CreateException;

   RechargeGroupLocal findByPrimaryKey(RechargeGroupPK var1) throws FinderException;
}
