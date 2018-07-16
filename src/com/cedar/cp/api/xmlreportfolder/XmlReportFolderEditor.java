// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:04:21
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.api.xmlreportfolder;

import com.cedar.cp.api.base.BusinessEditor;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.xmlreportfolder.XmlReportFolder;

public interface XmlReportFolderEditor extends BusinessEditor {

   void setParentFolderId(int var1) throws ValidationException;

   void setUserId(int var1) throws ValidationException;

   void setVisId(String var1) throws ValidationException;

   XmlReportFolder getXmlReportFolder();
}
