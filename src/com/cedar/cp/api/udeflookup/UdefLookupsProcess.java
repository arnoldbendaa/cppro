// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:04:21
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.api.udeflookup;

import com.cedar.cp.api.base.BusinessProcess;
import com.cedar.cp.api.base.EntityList;
import com.cedar.cp.api.base.EntityRef;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.udeflookup.UdefLookupEditorSession;

public interface UdefLookupsProcess extends BusinessProcess {

   EntityList getAllUdefLookups();
   
   EntityList getAllUdefLookupsForLoggedUser();

   UdefLookupEditorSession getUdefLookupEditorSession(Object var1) throws ValidationException;

   UdefLookupEditorSession getUdefLookupEditorSessionRef(EntityRef var1) throws ValidationException;

   EntityList getUdefForms(Object var1);
}
