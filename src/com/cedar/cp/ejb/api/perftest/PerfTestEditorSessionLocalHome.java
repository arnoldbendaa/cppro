// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:06:20
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.api.perftest;

import com.cedar.cp.ejb.api.perftest.PerfTestEditorSessionLocal;
import javax.ejb.CreateException;
import javax.ejb.EJBLocalHome;

public interface PerfTestEditorSessionLocalHome extends EJBLocalHome {

   PerfTestEditorSessionLocal create() throws CreateException;
}
