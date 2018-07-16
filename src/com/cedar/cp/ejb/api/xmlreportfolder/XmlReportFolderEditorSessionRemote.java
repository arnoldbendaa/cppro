// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:06:23
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.api.xmlreportfolder;

import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.dto.xmlreportfolder.XmlReportFolderEditorSessionCSO;
import com.cedar.cp.dto.xmlreportfolder.XmlReportFolderEditorSessionSSO;
import com.cedar.cp.dto.xmlreportfolder.XmlReportFolderPK;
import java.rmi.RemoteException;
import javax.ejb.EJBObject;

public interface XmlReportFolderEditorSessionRemote extends EJBObject {

   XmlReportFolderEditorSessionSSO getItemData(int var1, Object var2) throws ValidationException, RemoteException;

   XmlReportFolderEditorSessionSSO getNewItemData(int var1) throws RemoteException;

   XmlReportFolderPK insert(XmlReportFolderEditorSessionCSO var1) throws ValidationException, RemoteException;

   XmlReportFolderPK copy(XmlReportFolderEditorSessionCSO var1) throws ValidationException, RemoteException;

   void update(XmlReportFolderEditorSessionCSO var1) throws ValidationException, RemoteException;

   void delete(int var1, Object var2) throws ValidationException, RemoteException;
}
