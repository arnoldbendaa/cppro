// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:04:16
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.api.base;

import com.cedar.cp.api.base.BusinessSession;
import com.cedar.cp.api.base.CPConnection;
import com.cedar.cp.api.base.ValidationException;
import java.util.Collection;

public interface BusinessProcess {

   CPConnection getConnection();

   boolean hasSecurity(String var1);

   void deleteObject(Object var1) throws ValidationException;

   Collection getActiveSessions();

   String getProcessName();

   void terminateSession(BusinessSession var1);
   
   public void destroy();
}
