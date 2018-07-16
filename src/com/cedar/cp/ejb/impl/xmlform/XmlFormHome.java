// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:27:37
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.impl.xmlform;

import com.cedar.cp.dto.xmlform.XmlFormPK;
import com.cedar.cp.ejb.impl.xmlform.XmlFormEVO;
import com.cedar.cp.ejb.impl.xmlform.XmlFormRemote;
import java.rmi.RemoteException;
import javax.ejb.CreateException;
import javax.ejb.EJBException;
import javax.ejb.EJBHome;
import javax.ejb.FinderException;

public interface XmlFormHome extends EJBHome {

   XmlFormRemote create(XmlFormEVO var1) throws EJBException, CreateException, RemoteException;

   XmlFormRemote findByPrimaryKey(XmlFormPK var1) throws EJBException, FinderException, RemoteException;
}
