// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:06:12
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.xmlform;

import com.cedar.cp.api.xmlform.XmlFormRef;
import com.cedar.cp.dto.base.EntityRefImpl;
import com.cedar.cp.dto.xmlform.XmlFormPK;
import java.io.Serializable;

public class XmlFormRefImpl extends EntityRefImpl implements XmlFormRef, Serializable {

   public XmlFormRefImpl(XmlFormPK key, String narrative) {
      super(key, narrative);
   }

   public XmlFormPK getXmlFormPK() {
      return (XmlFormPK)this.mKey;
   }
}
