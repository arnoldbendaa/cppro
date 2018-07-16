// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:04:21
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.api.xmlform;

import com.cedar.cp.api.base.BusinessSession;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.xmlform.XmlFormEditor;
import com.cedar.cp.api.xmlform.XmlFormWizardParameters;
import java.util.List;
import java.util.Map;

public interface XmlFormEditorSession extends BusinessSession {

   XmlFormEditor getXmlFormEditor();

   Map invokeOnServer(List var1) throws ValidationException;

   XmlFormWizardParameters getFinanceXMLFormWizardData(int var1) throws ValidationException;
}
