// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:06:12
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.xmlform;

import com.cedar.cp.api.xmlform.XmlFormUserLinkRef;
import com.cedar.cp.dto.base.EntityRefImpl;
import com.cedar.cp.dto.xmlform.XmlFormUserLinkCK;
import com.cedar.cp.dto.xmlform.XmlFormUserLinkPK;
import java.io.Serializable;

public class XmlFormUserLinkRefImpl extends EntityRefImpl implements XmlFormUserLinkRef, Serializable {

   public XmlFormUserLinkRefImpl(XmlFormUserLinkCK key, String narrative) {
      super(key, narrative);
   }

   public XmlFormUserLinkRefImpl(XmlFormUserLinkPK key, String narrative) {
      super(key, narrative);
   }

   public XmlFormUserLinkPK getXmlFormUserLinkPK() {
      return this.mKey instanceof XmlFormUserLinkCK?((XmlFormUserLinkCK)this.mKey).getXmlFormUserLinkPK():(XmlFormUserLinkPK)this.mKey;
   }
}
