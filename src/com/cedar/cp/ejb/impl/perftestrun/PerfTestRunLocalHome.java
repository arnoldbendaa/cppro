// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:27:05
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.impl.perftestrun;

import com.cedar.cp.dto.perftestrun.PerfTestRunPK;
import com.cedar.cp.ejb.impl.perftestrun.PerfTestRunEVO;
import com.cedar.cp.ejb.impl.perftestrun.PerfTestRunLocal;
import javax.ejb.CreateException;
import javax.ejb.EJBException;
import javax.ejb.EJBLocalHome;
import javax.ejb.FinderException;

public interface PerfTestRunLocalHome extends EJBLocalHome {

   PerfTestRunLocal create(PerfTestRunEVO var1) throws EJBException, CreateException;

   PerfTestRunLocal findByPrimaryKey(PerfTestRunPK var1) throws FinderException;
}
