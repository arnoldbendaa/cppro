// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:04:21
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.api.user;

import com.cedar.cp.api.base.BusinessSession;
import com.cedar.cp.api.base.EntityList;
import com.cedar.cp.api.user.DataEntryProfileEditor;
import com.cedar.cp.api.xmlform.XmlFormRef;

public interface DataEntryProfileEditorSession extends BusinessSession {

   DataEntryProfileEditor getDataEntryProfileEditor();

   EntityList getAvailableUsers();

   XmlFormRef[] getAvailableXmlFormRefs();

   EntityList getOwnershipRefs();
}
