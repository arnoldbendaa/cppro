// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:04:21
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.api.xmlreportfolder;

import com.cedar.cp.api.base.BusinessProcess;
import com.cedar.cp.api.base.EntityList;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.xmlreportfolder.XmlReportFolderEditorSession;

public interface XmlReportFoldersProcess extends BusinessProcess {

   EntityList getAllXmlReportFolders();

   EntityList getDecendentFolders(int var1);

   EntityList getReportFolderWithId(int var1);

   XmlReportFolderEditorSession getXmlReportFolderEditorSession(Object var1) throws ValidationException;

   EntityList getAllPublicXmlReportFolders();

   EntityList getAllXmlReportFoldersForUser(int var1);
}
