// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:06:22
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.api.sqlmon;

import com.cedar.cp.api.base.EntityList;
import com.cedar.cp.api.sqlmon.SqlDetails;
import javax.ejb.EJBException;
import javax.ejb.EJBLocalObject;

public interface SqlMonitorProcessLocal extends EJBLocalObject {

   EntityList getAllOracleSessions() throws EJBException;

   SqlDetails getSqlDetails(Object var1) throws EJBException;
}
