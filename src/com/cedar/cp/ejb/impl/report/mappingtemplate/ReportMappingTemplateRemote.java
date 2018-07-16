// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:27:16
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.impl.report.mappingtemplate;

import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.dto.report.mappingtemplate.ReportMappingTemplatePK;
import com.cedar.cp.ejb.impl.report.mappingtemplate.ReportMappingTemplateEVO;
import java.rmi.RemoteException;
import javax.ejb.EJBObject;

public interface ReportMappingTemplateRemote extends EJBObject {

   ReportMappingTemplateEVO getDetails(String var1) throws ValidationException, RemoteException;

   ReportMappingTemplatePK generateKeys();

   void setDetails(ReportMappingTemplateEVO var1) throws RemoteException;

   ReportMappingTemplateEVO setAndGetDetails(ReportMappingTemplateEVO var1, String var2) throws RemoteException;
}
