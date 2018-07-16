// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:27:20
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.impl.report.template;

import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.dto.report.template.ReportTemplatePK;
import com.cedar.cp.ejb.impl.report.template.ReportTemplateEVO;
import java.rmi.RemoteException;
import javax.ejb.EJBObject;

public interface ReportTemplateRemote extends EJBObject {

   ReportTemplateEVO getDetails(String var1) throws ValidationException, RemoteException;

   ReportTemplatePK generateKeys();

   void setDetails(ReportTemplateEVO var1) throws RemoteException;

   ReportTemplateEVO setAndGetDetails(ReportTemplateEVO var1, String var2) throws RemoteException;
}
