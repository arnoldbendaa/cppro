// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:26:49
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.impl.model.mapping;

import com.cedar.cp.dto.model.mapping.MappedModelPK;
import com.cedar.cp.ejb.impl.model.mapping.MappedModelEVO;
import com.cedar.cp.ejb.impl.model.mapping.MappedModelRemote;
import java.rmi.RemoteException;
import javax.ejb.CreateException;
import javax.ejb.EJBException;
import javax.ejb.EJBHome;
import javax.ejb.FinderException;

public interface MappedModelHome extends EJBHome {

   MappedModelRemote create(MappedModelEVO var1) throws EJBException, CreateException, RemoteException;

   MappedModelRemote findByPrimaryKey(MappedModelPK var1) throws EJBException, FinderException, RemoteException;
}
