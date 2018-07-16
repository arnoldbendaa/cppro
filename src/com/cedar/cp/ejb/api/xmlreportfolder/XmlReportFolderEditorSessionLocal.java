// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:06:23
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.api.xmlreportfolder;

import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.dto.xmlreportfolder.XmlReportFolderEditorSessionCSO;
import com.cedar.cp.dto.xmlreportfolder.XmlReportFolderEditorSessionSSO;
import com.cedar.cp.dto.xmlreportfolder.XmlReportFolderPK;
import javax.ejb.EJBException;
import javax.ejb.EJBLocalObject;

public interface XmlReportFolderEditorSessionLocal extends EJBLocalObject {

   XmlReportFolderEditorSessionSSO getItemData(int var1, Object var2) throws ValidationException, EJBException;

   XmlReportFolderEditorSessionSSO getNewItemData(int var1) throws EJBException;

   XmlReportFolderPK insert(XmlReportFolderEditorSessionCSO var1) throws ValidationException, EJBException;

   XmlReportFolderPK copy(XmlReportFolderEditorSessionCSO var1) throws ValidationException, EJBException;

   void update(XmlReportFolderEditorSessionCSO var1) throws ValidationException, EJBException;

   void delete(int var1, Object var2) throws ValidationException, EJBException;
}
