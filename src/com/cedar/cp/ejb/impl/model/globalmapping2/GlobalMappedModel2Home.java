package com.cedar.cp.ejb.impl.model.globalmapping2;

import com.cedar.cp.dto.model.globalmapping2.GlobalMappedModel2PK;
import com.cedar.cp.ejb.impl.model.globalmapping2.GlobalMappedModel2EVO;
import com.cedar.cp.ejb.impl.model.globalmapping2.GlobalMappedModel2Remote;
import java.rmi.RemoteException;
import javax.ejb.CreateException;
import javax.ejb.EJBException;
import javax.ejb.EJBHome;
import javax.ejb.FinderException;

public interface GlobalMappedModel2Home extends EJBHome {

   GlobalMappedModel2Remote create(GlobalMappedModel2EVO var1) throws EJBException, CreateException, RemoteException;

   GlobalMappedModel2Remote findByPrimaryKey(GlobalMappedModel2PK var1) throws EJBException, FinderException, RemoteException;
}
