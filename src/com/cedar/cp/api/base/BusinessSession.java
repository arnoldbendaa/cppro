// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:04:16
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.api.base;

import com.cedar.cp.api.base.BusinessProcess;
import com.cedar.cp.api.base.ValidationException;
import java.util.Collection;

public interface BusinessSession {

   Object getPrimaryKey();

   BusinessProcess getBusinessProcess();

   boolean hasSecurity(String var1);

   Collection getActiveEditors();

   boolean isContentModified();

   boolean isReadOnly();

   Object commit(boolean var1) throws ValidationException;
   
}
