// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:27:20
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.impl.report.template;

import com.cedar.cp.dto.report.template.ReportTemplatePK;
import com.cedar.cp.ejb.impl.report.template.ReportTemplateEVO;
import com.cedar.cp.ejb.impl.report.template.ReportTemplateRemote;
import java.rmi.RemoteException;
import javax.ejb.CreateException;
import javax.ejb.EJBException;
import javax.ejb.EJBHome;
import javax.ejb.FinderException;

public interface ReportTemplateHome extends EJBHome {

   ReportTemplateRemote create(ReportTemplateEVO var1) throws EJBException, CreateException, RemoteException;

   ReportTemplateRemote findByPrimaryKey(ReportTemplatePK var1) throws EJBException, FinderException, RemoteException;
}
