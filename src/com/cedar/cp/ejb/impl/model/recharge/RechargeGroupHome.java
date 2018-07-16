// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:26:53
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.impl.model.recharge;

import com.cedar.cp.dto.model.recharge.RechargeGroupPK;
import com.cedar.cp.ejb.impl.model.recharge.RechargeGroupEVO;
import com.cedar.cp.ejb.impl.model.recharge.RechargeGroupRemote;
import java.rmi.RemoteException;
import javax.ejb.CreateException;
import javax.ejb.EJBException;
import javax.ejb.EJBHome;
import javax.ejb.FinderException;

public interface RechargeGroupHome extends EJBHome {

   RechargeGroupRemote create(RechargeGroupEVO var1) throws EJBException, CreateException, RemoteException;

   RechargeGroupRemote findByPrimaryKey(RechargeGroupPK var1) throws EJBException, FinderException, RemoteException;
}
