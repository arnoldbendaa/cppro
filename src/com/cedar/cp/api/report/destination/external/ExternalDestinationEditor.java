// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:04:20
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.api.report.destination.external;

import com.cedar.cp.api.base.BusinessEditor;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.report.destination.external.ExternalDestination;

public interface ExternalDestinationEditor extends BusinessEditor {

   void setVisId(String var1) throws ValidationException;

   void setDescription(String var1) throws ValidationException;

   ExternalDestination getExternalDestination();

   void addUser(String var1);
}
