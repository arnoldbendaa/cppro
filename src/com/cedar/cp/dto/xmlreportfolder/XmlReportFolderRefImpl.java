// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:06:13
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.xmlreportfolder;

import com.cedar.cp.api.xmlreportfolder.XmlReportFolderRef;
import com.cedar.cp.dto.base.EntityRefImpl;
import com.cedar.cp.dto.xmlreportfolder.XmlReportFolderPK;
import java.io.Serializable;

public class XmlReportFolderRefImpl extends EntityRefImpl implements XmlReportFolderRef, Serializable {

   public XmlReportFolderRefImpl(XmlReportFolderPK key, String narrative) {
      super(key, narrative);
   }

   public XmlReportFolderPK getXmlReportFolderPK() {
      return (XmlReportFolderPK)this.mKey;
   }
}
