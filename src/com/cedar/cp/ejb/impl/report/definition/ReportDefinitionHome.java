// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:27:11
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.impl.report.definition;

import com.cedar.cp.dto.report.definition.ReportDefinitionPK;
import com.cedar.cp.ejb.impl.report.definition.ReportDefinitionEVO;
import com.cedar.cp.ejb.impl.report.definition.ReportDefinitionRemote;
import java.rmi.RemoteException;
import javax.ejb.CreateException;
import javax.ejb.EJBException;
import javax.ejb.EJBHome;
import javax.ejb.FinderException;

public interface ReportDefinitionHome extends EJBHome {

   ReportDefinitionRemote create(ReportDefinitionEVO var1) throws EJBException, CreateException, RemoteException;

   ReportDefinitionRemote findByPrimaryKey(ReportDefinitionPK var1) throws EJBException, FinderException, RemoteException;
}
