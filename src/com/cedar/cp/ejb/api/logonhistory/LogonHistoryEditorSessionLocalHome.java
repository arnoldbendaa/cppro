// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:06:18
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.api.logonhistory;

import com.cedar.cp.ejb.api.logonhistory.LogonHistoryEditorSessionLocal;
import javax.ejb.CreateException;
import javax.ejb.EJBLocalHome;

public interface LogonHistoryEditorSessionLocalHome extends EJBLocalHome {

   LogonHistoryEditorSessionLocal create() throws CreateException;
}
