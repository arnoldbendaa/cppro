// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:27:37
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.impl.xmlform;

import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.dto.xmlform.XmlFormCK;
import com.cedar.cp.dto.xmlform.XmlFormPK;
import com.cedar.cp.ejb.impl.xmlform.XmlFormEVO;
import javax.ejb.EJBLocalObject;

public interface XmlFormLocal extends EJBLocalObject {

   XmlFormEVO getDetails(String var1) throws ValidationException;

   XmlFormEVO getDetails(XmlFormCK var1, String var2) throws ValidationException;

   XmlFormPK generateKeys();

   void setDetails(XmlFormEVO var1);

   XmlFormEVO setAndGetDetails(XmlFormEVO var1, String var2);
}
