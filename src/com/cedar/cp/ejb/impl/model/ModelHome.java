// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:26:27
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.impl.model;

import com.cedar.cp.dto.model.ModelPK;
import com.cedar.cp.ejb.impl.model.ModelEVO;
import com.cedar.cp.ejb.impl.model.ModelRemote;
import java.rmi.RemoteException;
import javax.ejb.CreateException;
import javax.ejb.EJBException;
import javax.ejb.EJBHome;
import javax.ejb.FinderException;

public interface ModelHome extends EJBHome {

   ModelRemote create(ModelEVO var1) throws EJBException, CreateException, RemoteException;

   ModelRemote findByPrimaryKey(ModelPK var1) throws EJBException, FinderException, RemoteException;
}
