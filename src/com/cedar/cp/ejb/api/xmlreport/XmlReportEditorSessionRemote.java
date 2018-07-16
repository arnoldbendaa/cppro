// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:06:23
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.api.xmlreport;

import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.dto.xmlreport.XmlReportEditorSessionCSO;
import com.cedar.cp.dto.xmlreport.XmlReportEditorSessionSSO;
import com.cedar.cp.dto.xmlreport.XmlReportPK;
import java.rmi.RemoteException;
import javax.ejb.EJBObject;

public interface XmlReportEditorSessionRemote extends EJBObject {

   XmlReportEditorSessionSSO getItemData(int var1, Object var2) throws ValidationException, RemoteException;

   XmlReportEditorSessionSSO getNewItemData(int var1) throws RemoteException;

   XmlReportPK insert(XmlReportEditorSessionCSO var1) throws ValidationException, RemoteException;

   XmlReportPK copy(XmlReportEditorSessionCSO var1) throws ValidationException, RemoteException;

   void update(XmlReportEditorSessionCSO var1) throws ValidationException, RemoteException;

   void delete(int var1, Object var2) throws ValidationException, RemoteException;
}
