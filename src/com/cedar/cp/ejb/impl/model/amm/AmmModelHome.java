// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:26:34
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.impl.model.amm;

import com.cedar.cp.dto.model.amm.AmmModelPK;
import com.cedar.cp.ejb.impl.model.amm.AmmModelEVO;
import com.cedar.cp.ejb.impl.model.amm.AmmModelRemote;
import java.rmi.RemoteException;
import javax.ejb.CreateException;
import javax.ejb.EJBException;
import javax.ejb.EJBHome;
import javax.ejb.FinderException;

public interface AmmModelHome extends EJBHome {

   AmmModelRemote create(AmmModelEVO var1) throws EJBException, CreateException, RemoteException;

   AmmModelRemote findByPrimaryKey(AmmModelPK var1) throws EJBException, FinderException, RemoteException;
}
