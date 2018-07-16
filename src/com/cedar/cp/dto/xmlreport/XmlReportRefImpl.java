// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:06:13
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.xmlreport;

import com.cedar.cp.api.xmlreport.XmlReportRef;
import com.cedar.cp.dto.base.EntityRefImpl;
import com.cedar.cp.dto.xmlreport.XmlReportPK;
import java.io.Serializable;

public class XmlReportRefImpl extends EntityRefImpl implements XmlReportRef, Serializable {

   public XmlReportRefImpl(XmlReportPK key, String narrative) {
      super(key, narrative);
   }

   public XmlReportPK getXmlReportPK() {
      return (XmlReportPK)this.mKey;
   }
}
