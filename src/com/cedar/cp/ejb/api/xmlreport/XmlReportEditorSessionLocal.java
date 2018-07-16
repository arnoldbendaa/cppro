// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:06:23
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.api.xmlreport;

import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.dto.xmlreport.XmlReportEditorSessionCSO;
import com.cedar.cp.dto.xmlreport.XmlReportEditorSessionSSO;
import com.cedar.cp.dto.xmlreport.XmlReportPK;
import javax.ejb.EJBException;
import javax.ejb.EJBLocalObject;

public interface XmlReportEditorSessionLocal extends EJBLocalObject {

   XmlReportEditorSessionSSO getItemData(int var1, Object var2) throws ValidationException, EJBException;

   XmlReportEditorSessionSSO getNewItemData(int var1) throws EJBException;

   XmlReportPK insert(XmlReportEditorSessionCSO var1) throws ValidationException, EJBException;

   XmlReportPK copy(XmlReportEditorSessionCSO var1) throws ValidationException, EJBException;

   void update(XmlReportEditorSessionCSO var1) throws ValidationException, EJBException;

   void delete(int var1, Object var2) throws ValidationException, EJBException;
}
